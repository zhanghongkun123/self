package com.ruijie.rcos.rcdc.rco.module.web.ctrl.terminal;

import com.google.common.collect.ImmutableMap;
import com.ruijie.rcos.rcdc.rco.module.def.api.*;
import com.ruijie.rcos.gss.base.iac.module.annotation.EnableAuthority;
import com.ruijie.rcos.gss.log.module.def.api.BaseAuditLogAPI;
import com.ruijie.rcos.rcdc.rco.module.def.api.AdminDataPermissionAPI;
import com.ruijie.rcos.rcdc.rco.module.def.api.RcoGlobalParameterAPI;
import com.ruijie.rcos.rcdc.rco.module.def.api.TerminalManagePwdAPI;
import com.ruijie.rcos.rcdc.rco.module.def.api.UserTerminalMgmtAPI;
import com.ruijie.rcos.rcdc.rco.module.def.api.WifiWhitelistAPI;
import com.ruijie.rcos.rcdc.rco.module.def.api.dto.MatchEqual;
import com.ruijie.rcos.rcdc.rco.module.def.api.dto.TerminalDTO;
import com.ruijie.rcos.rcdc.rco.module.def.api.dto.TerminalDeskInfoDTO;
import com.ruijie.rcos.rcdc.rco.module.def.api.request.PageSearchRequest;
import com.ruijie.rcos.rcdc.rco.module.def.api.request.TerminalPageSearchRequest;
import com.ruijie.rcos.rcdc.rco.module.def.api.request.aaa.ListTerminalGroupIdRequest;
import com.ruijie.rcos.rcdc.rco.module.def.api.response.aaa.ListTerminalGroupIdResponse;
import com.ruijie.rcos.rcdc.rco.module.def.utils.PermissionHelper;
import com.ruijie.rcos.rcdc.rco.module.web.Constants;
import com.ruijie.rcos.rcdc.rco.module.web.config.annotation.ApiVersion;
import com.ruijie.rcos.rcdc.rco.module.web.config.annotation.ApiVersions;
import com.ruijie.rcos.rcdc.rco.module.web.config.annotation.Version;
import com.ruijie.rcos.rcdc.rco.module.web.ctrl.common.utils.TerminalIdMappingUtils;
import com.ruijie.rcos.rcdc.rco.module.web.ctrl.terminal.batchtask.ChangeIdvServerIpBatchTaskHandler;
import com.ruijie.rcos.rcdc.rco.module.web.ctrl.terminal.batchtask.ConfigNicWorkModeHandler;
import com.ruijie.rcos.rcdc.rco.module.web.ctrl.terminal.request.*;
import com.ruijie.rcos.rcdc.rco.module.web.ctrl.terminal.vo.NicWorkModeInfoVO;
import com.ruijie.rcos.rcdc.rco.module.web.ctrl.user.UserBusinessKey;
import com.ruijie.rcos.rcdc.rco.module.web.ctrl.user.bactchtask.DeleteTerminalBatchTaskHandler;
import com.ruijie.rcos.rcdc.rco.module.web.ctrl.user.request.UpdateTerminalGroupWebRequest;
import com.ruijie.rcos.rcdc.rco.module.web.ctrl.user.vo.TerminalDetailVO;
import com.ruijie.rcos.rcdc.rco.module.web.factory.clouddesktop.UpdateTerminalGroupHandlerFactory;
import com.ruijie.rcos.rcdc.rco.module.web.response.CommonWebResponse;
import com.ruijie.rcos.rcdc.rco.module.web.service.TerminalGroupHelper;
import com.ruijie.rcos.rcdc.terminal.module.def.api.CbbTerminalGroupMgmtAPI;
import com.ruijie.rcos.rcdc.terminal.module.def.api.CbbTerminalOperatorAPI;
import com.ruijie.rcos.rcdc.terminal.module.def.api.dto.CbbModifyTerminalDTO;
import com.ruijie.rcos.rcdc.terminal.module.def.api.dto.CbbTerminalBasicInfoDTO;
import com.ruijie.rcos.rcdc.terminal.module.def.api.enums.CbbNicWorkModeEnums;
import com.ruijie.rcos.rcdc.terminal.module.def.api.enums.CbbTerminalStateEnums;
import com.ruijie.rcos.rcdc.terminal.module.def.enums.CbbTerminalPlatformEnums;
import com.ruijie.rcos.sk.base.batch.BatchTaskBuilder;
import com.ruijie.rcos.sk.base.batch.BatchTaskSubmitResult;
import com.ruijie.rcos.sk.base.batch.DefaultBatchTaskItem;
import com.ruijie.rcos.sk.base.exception.BusinessException;
import com.ruijie.rcos.sk.base.log.Logger;
import com.ruijie.rcos.sk.base.log.LoggerFactory;
import com.ruijie.rcos.sk.base.validation.EnableCustomValidate;
import com.ruijie.rcos.sk.modulekit.api.comm.DefaultPageResponse;
import com.ruijie.rcos.sk.webmvc.api.request.PageWebRequest;
import com.ruijie.rcos.sk.webmvc.api.response.DefaultWebResponse;
import com.ruijie.rcos.sk.webmvc.api.session.SessionContext;
import com.ruijie.rcos.sk.webmvc.api.vo.ExactMatch;
import com.ruijie.rcos.sk.webmvc.api.vo.IdLabelEntry;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Description: 终端管理
 * Copyright: Copyright (c) 2018
 * Company: Ruijie Co., Ltd.
 * Create Time: 2018年12月24日
 *
 * @author nt
 */
@Api(tags = "终端管理")
@Controller
@RequestMapping("/rco/terminal/idv")
@EnableCustomValidate(enable = false)
public class IDVTerminalController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IDVTerminalController.class);

    @Autowired
    private UserTerminalMgmtAPI userTerminalMgmtAPI;

    @Autowired
    private CbbTerminalOperatorAPI cbbTerminalOperatorAPI;

    @Autowired
    private UpdateTerminalGroupHandlerFactory updateTerminalGroupHandlerFactory;

    private static final String EXACT_MATCH_PARAM_ID = "id";

    @Autowired
    private AdminDataPermissionAPI adminDataPermissionAPI;

    @Autowired
    private PermissionHelper permissionHelper;

    @Autowired
    private WifiWhitelistAPI wifiWhitelistAPI;

    @Autowired
    private CbbTerminalGroupMgmtAPI cbbTerminalGroupMgmtAPI;

    @Autowired
    private TerminalManagePwdAPI terminalManagePwdAPI;

    @Autowired
    private BaseAuditLogAPI auditLogAPI;

    @Autowired
    private RcoGlobalParameterAPI rcoGlobalParameterAPI;

    /**
     * @apiIgnore
     * @api {POST} /rco/terminal/list
     * @apiName /list
     * @apiGroup /rco/terminal
     * @apiDescription 获取终端信息
     * @apiParam (请求体字段说明) {Integer} page
     * @apiParam (请求体字段说明) {Integer} limit
     * @apiParam (请求体字段说明) {String} searchKeyword
     * @apiParam (请求体字段说明) {ExactMatch[]} exactMatchArr
     * @apiParam (请求体字段说明) {String} exactMatchArr.name
     * @apiParam (请求体字段说明) {String[]} exactMatchArr.valueArr
     * @apiParam (请求体字段说明) {Sort} sort
     * @apiParam (请求体字段说明) {String} sort.sortField
     * @apiParamExample {json} 请求体示例
     *                  {
     *                  "exactMatchArr":[{
     *                  "name":"name",
     *                  "valueArr":["test"],
     *                  "valueArrWithSpecialCharTransfer":["test"]
     *                  }],
     *                  "limit":1,
     *                  "page":1,
     *                  "searchKeyword":"searchKeyword",
     *                  "sort":{
     *                  "sortField":"sortField"
     *                  }
     *                  }
     * @apiSuccess (响应字段说明) {Object} content 响应信息
     * @apiSuccess (响应字段说明) {String} message 提示信息
     * @apiSuccess (响应字段说明) {String[]} msgArgArr 消息参数填充
     * @apiSuccess (响应字段说明) {String} msgKey 消息参数填充
     * @apiSuccess (响应字段说明) {String} status 状态信息
     * @apiSuccess (响应字段说明) {String} itemArr.id 终端id
     * @apiSuccess (响应字段说明) {String} itemArr.terminalName 终端名称
     * @apiSuccess (响应字段说明) {UUID} itemArr.terminalGroupId 分组id
     * @apiSuccess (响应字段说明) {String} itemArr.terminalGroupName 分组名称
     * @apiSuccess (响应字段说明) {String[]} itemArr.terminalGroupNameArr 分组名称集合
     * @apiSuccess (响应字段说明) {String} itemArr.rainOsVersion 终端系统版本号
     * @apiSuccess (响应字段说明) {String} itemArr.hardwareVersion 硬件版本号
     * @apiSuccess (响应字段说明) {String} itemArr.rainUpgradeVersion 软件版本号，指组件升级包的版本号
     * @apiSuccess (响应字段说明) {String} itemArr.terminalOsType 终端系统类型
     * @apiSuccess (响应字段说明) {Date} itemArr.lastOfflineTime 终端最近一次离线的时间点
     * @apiSuccess (响应字段说明) {Date} itemArr.lastOnlineTime 终端最近接入的时间点
     * @apiSuccess (响应字段说明) {String} itemArr.macAddr 终端最近接入的时间点
     * @apiSuccess (响应字段说明) {Long} itemArr.diskSize 磁盘大小，单位kb
     * @apiSuccess (响应字段说明) {String} itemArr.cpuMode cup型号
     * @apiSuccess (响应字段说明) {String} itemArr.ip 终端ip地址
     * @apiSuccess (响应字段说明) {Long} itemArr.memorySize 内存大小，单位kb
     * @apiSuccess (响应字段说明) {Integer} itemArr.ipConflict ip是否冲突
     * @apiSuccess (响应字段说明) {Integer} itemArr.accessInternet 外网访问是否正常
     * @apiSuccess (响应字段说明) {Date} itemArr.detectTime 检测时间
     * @apiSuccess (响应字段说明) {String} itemArr.productType productType
     * @apiSuccess (响应字段说明) {String} itemArr.productId productId
     * @apiSuccess (响应字段说明) {Integer} itemArr.delayThreshold 时延阈值
     * @apiSuccess (响应字段说明) {String} itemArr.detectState 终端检测状态
     * @apiSuccess (响应字段说明) {UUID} itemArr.bindUserId 绑定用户id
     * @apiSuccess (响应字段说明) {String} itemArr.bindUserName 绑定用户名称
     * @apiSuccess (响应字段说明) {UUID} itemArr.bindDeskId 绑定桌面id
     * @apiSuccess (响应字段说明) {String} itemArr.wirelessIp 无线IP
     * @apiSuccess (响应字段说明) {String} itemArr.wirelessMacAddr 无线mac地址
     * @apiSuccess (响应字段说明) {String} itemArr.ssid wifi名称
     * @apiSuccess (响应字段说明) {Boolean} itemArr.enableVisitorLogin 是否启用访客模式
     * @apiSuccess (响应字段说明) {Boolean} itemArr.enableAutoLogin 是否启用自动登录
     * @apiSuccess (响应字段说明) {Long} itemArr.onlineTime 在线时间
     * @apiSuccess (响应字段说明) {String} itemArr.desktopName 桌面名称
     * @apiSuccess (响应字段说明) {Date} itemArr.createTime 创建时间
     * @apiSuccess (响应字段说明) {String} itemArr.serialNumber SN序列号
     * @apiSuccess (响应字段说明) {TerminalCloudDesktopDTO} itemArr.terminalCloudDesktop 云桌面信息
     * @apiSuccess (响应字段说明) {UUID} itemArr.terminalCloudDesktop.desktopId 桌面ID
     * @apiSuccess (响应字段说明) {String} itemArr.terminalCloudDesktop.imageTemplateName 镜像名称
     * @apiSuccess (响应字段说明) {String} itemArr.terminalCloudDesktop.deskIp 云桌面Ip
     * @apiSuccess (响应字段说明) {Integer} itemArr.terminalCloudDesktop.systemSize 系统盘大小
     * @apiSuccess (响应字段说明) {Integer} itemArr.terminalCloudDesktop.personSize 个人盘大小
     * @apiSuccess (响应字段说明) {Boolean} itemArr.terminalCloudDesktop.isAllowLocalDisk 是否开启本地盘
     * @apiSuccess (响应字段说明) {String} itemArr.terminalCloudDesktop.deskMac 云桌面mac
     * @apiSuccess (响应字段说明) {Boolean} itemArr.terminalCloudDesktop.autoDhcp 云桌面ip获取方式
     * @apiSuccess (响应字段说明) {String} itemArr.terminalCloudDesktop.gateWay 云桌面网关
     * @apiSuccess (响应字段说明) {String} itemArr.terminalCloudDesktop.mask 云桌面掩码
     * @apiSuccess (响应字段说明) {Boolean} itemArr.terminalCloudDesktop.autoDns 云桌面dns获取方式
     * @apiSuccess (响应字段说明) {String} itemArr.terminalCloudDesktop.dnsPrimary 云桌面首选DNS
     * @apiSuccess (响应字段说明) {String} itemArr.terminalCloudDesktop.dnsSecondary 云桌面备选DNS
     * @apiSuccessExample {json} 成功响应
     *                    {
     *                    "content":{
     *                    "itemArr":[{
     *                    "accessInternet":1,
     *                    "bindDeskId":"434b2b01-c0ac-40c1-afa3-67175ef17350",
     *                    "bindUserId":"434b2b01-c0ac-40c1-afa3-67175ef17350",
     *                    "bindUserName":"bindUserName",
     *                    "cpuMode":"cpuMode",
     *                    "createTime":1597921756645,
     *                    "delayThreshold":1,
     *                    "desktopName":"desktopName",
     *                    "detectState":"detectState",
     *                    "detectTime":1597921756645,
     *                    "diskSize":1,
     *                    "enableAutoLogin":false,
     *                    "enableVisitorLogin":false,
     *                    "getIpMode":"AUTO",
     *                    "hardwareVersion":"hardwareVersion",
     *                    "id":"id",
     *                    "ip":"ip",
     *                    "ipConflict":1,
     *                    "lastOfflineTime":1597921756645,
     *                    "lastOnlineTime":1597921756645,
     *                    "macAddr":"macAddr",
     *                    "memorySize":1,
     *                    "networkAccessMode":"WIRED",
     *                    "onlineTime":1,
     *                    "platform":"IDV",
     *                    "productId":"productId",
     *                    "productType":"productType",
     *                    "rainOsVersion":"rainOsVersion",
     *                    "rainUpgradeVersion":"rainUpgradeVersion",
     *                    "serialNumber":"serialNumber",
     *                    "ssid":"ssid",
     *                    "terminalCloudDesktop":{
     *                    "allowLocalDisk":false,
     *                    "autoDhcp":false,
     *                    "autoDns":false,
     *                    "deskIp":"deskIp",
     *                    "deskMac":"deskMac",
     *                    "desktopId":"434b2b01-c0ac-40c1-afa3-67175ef17350",
     *                    "dnsPrimary":"dnsPrimary",
     *                    "dnsSecondary":"dnsSecondary",
     *                    "gateWay":"gateWay",
     *                    "imageTemplateName":"imageTemplateName",
     *                    "mask":"mask",
     *                    "personSize":1,
     *                    "systemSize":1
     *                    },
     *                    "terminalGroupId":"434b2b01-c0ac-40c1-afa3-67175ef17350",
     *                    "terminalGroupName":"terminalGroupName",
     *                    "terminalGroupNameArr":["test"],
     *                    "terminalName":"terminalName",
     *                    "terminalOsType":"terminalOsType",
     *                    "terminalState":"ONLINE",
     *                    "wirelessIp":"wirelessIp",
     *                    "wirelessMacAddr":"wirelessMacAddr"
     *                    }],
     *                    "total":1
     *                    },
     *                    "message":"",
     *                    "msgArgArr":[],
     *                    "msgKey":"",
     *                    "status":"SUCCESS"
     *                    }
     * @apiErrorExample {json} 异常响应
     *                  {
     *                  "message":"异常信息",
     *                  "msgArgArr":[],
     *                  "msgKey":"",
     *                  "status":"ERROR"
     *                  }
     */
    /**
     * 终端列表
     *
     * @param request        PageWebRequest
     * @param sessionContext SessionContext
     * @return DefaultWebResponse
     * @throws BusinessException 业务异常
     */
    @ApiOperation("终端列表")
    @ApiVersions({@ApiVersion(value = Version.V3_1_1, descriptions = {"分级分权-区分权限-终端列表"})})
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public CommonWebResponse<DefaultPageResponse> list(PageWebRequest request, SessionContext sessionContext) throws BusinessException {
        Assert.notNull(request, "request can not be null");
        Assert.notNull(sessionContext, "sessionContext can not be null");

        PageSearchRequest apiRequest = new TerminalPageSearchRequest(request);

        if (!permissionHelper.isAllGroupPermission(sessionContext)) {
            List<String> terminalGroupIdStrList = getPermissionTerminalGroupIdList(sessionContext.getUserId());
            String terminalGroupId = getTerminalGroupId(request);
            if (StringUtils.isEmpty(terminalGroupId)) {
                appendTerminalGroupIdMatchEqual(apiRequest, terminalGroupIdStrList);
            } else {
                if (!terminalGroupIdStrList.contains(terminalGroupId)) {
                    DefaultPageResponse response = new DefaultPageResponse();
                    response.setItemArr(Collections.emptyList().toArray());
                    return CommonWebResponse.success(response);
                }
            }
        }
        return CommonWebResponse.success(userTerminalMgmtAPI.pageQuery(apiRequest));
    }

    /**
     * 当前前端只会上传一个groupId进行查询
     */
    private String getTerminalGroupId(PageWebRequest request) {
        if (ArrayUtils.isNotEmpty(request.getExactMatchArr())) {
            for (ExactMatch exactMatch : request.getExactMatchArr()) {
                if (exactMatch.getName().equals("groupId") && exactMatch.getValueArr().length > 0) {
                    return exactMatch.getValueArr()[0];
                }
            }
        }
        return "";
    }

    private List<String> getPermissionTerminalGroupIdList(UUID adminId) throws BusinessException {
        ListTerminalGroupIdRequest listTerminalGroupIdRequest = new ListTerminalGroupIdRequest();
        listTerminalGroupIdRequest.setAdminId(adminId);
        ListTerminalGroupIdResponse listTerminalGroupIdResponse = adminDataPermissionAPI.listTerminalGroupIdByAdminId(listTerminalGroupIdRequest);
        return listTerminalGroupIdResponse.getTerminalGroupIdList();
    }

    private void appendTerminalGroupIdMatchEqual(PageSearchRequest request, List<String> userGroupIdStrList) throws BusinessException {
        List<UUID> uuidList = userGroupIdStrList.stream().filter(idStr -> !idStr.equals(TerminalGroupHelper.TERMINAL_GROUP_ROOT_ID))
                .map(UUID::fromString).collect(Collectors.toList());
        UUID[] uuidArr = uuidList.toArray(new UUID[uuidList.size()]);
        request.appendCustomMatchEqual(new MatchEqual("terminalGroupId", uuidArr));
    }

    /**
     * @apiIgnore
     * @api {POST} /rco/terminal/getInfo
     * @apiName /getInfo
     * @apiGroup /rco/terminal
     * @apiDescription 终端详细信息
     * @apiParam (请求体字段说明) {String} id 终端ID
     * @apiParamExample {json} 请求体示例
     *                  {
     *                  "id":"id"
     *                  }
     * @apiSuccess (响应字段说明) {Object} content 响应信息
     * @apiSuccess (响应字段说明) {String} message 提示信息
     * @apiSuccess (响应字段说明) {String[]} msgArgArr 消息参数填充
     * @apiSuccess (响应字段说明) {String} msgKey 消息参数填充
     * @apiSuccess (响应字段说明) {String} status 状态信息
     * @apiSuccess (响应字段说明) {String} content.id 终端id
     * @apiSuccess (响应字段说明) {String} content.terminalName 终端名称
     * @apiSuccess (响应字段说明) {UUID} content.terminalGroupId 分组id
     * @apiSuccess (响应字段说明) {String} content.terminalGroupName 分组名称
     * @apiSuccess (响应字段说明) {String[]} content.terminalGroupNameArr 分组名称集合
     * @apiSuccess (响应字段说明) {String} content.rainOsVersion 终端系统版本号
     * @apiSuccess (响应字段说明) {String} content.hardwareVersion 硬件版本号
     * @apiSuccess (响应字段说明) {String} content.rainUpgradeVersion 软件版本号，指组件升级包的版本号
     * @apiSuccess (响应字段说明) {String} content.terminalOsType 终端系统类型
     * @apiSuccess (响应字段说明) {Date} content.lastOfflineTime 终端最近一次离线的时间点
     * @apiSuccess (响应字段说明) {Date} content.lastOnlineTime 终端最近接入的时间点
     * @apiSuccess (响应字段说明) {String} content.macAddr 终端最近接入的时间点
     * @apiSuccess (响应字段说明) {Long} content.diskSize 磁盘大小，单位kb
     * @apiSuccess (响应字段说明) {String} content.cpuMode cup型号
     * @apiSuccess (响应字段说明) {String} content.ip 终端ip地址
     * @apiSuccess (响应字段说明) {Long} content.memorySize 内存大小，单位kb
     * @apiSuccess (响应字段说明) {Integer} content.ipConflict ip是否冲突
     * @apiSuccess (响应字段说明) {Integer} content.accessInternet 外网访问是否正常
     * @apiSuccess (响应字段说明) {Date} content.detectTime 检测时间
     * @apiSuccess (响应字段说明) {String} content.productType productType
     * @apiSuccess (响应字段说明) {String} content.productId productId
     * @apiSuccess (响应字段说明) {Integer} content.delayThreshold 时延阈值
     * @apiSuccess (响应字段说明) {String} content.detectState 终端检测状态
     * @apiSuccess (响应字段说明) {UUID} content.bindUserId 绑定用户id
     * @apiSuccess (响应字段说明) {String} content.bindUserName 绑定用户名称
     * @apiSuccess (响应字段说明) {UUID} content.bindDeskId 绑定桌面id
     * @apiSuccess (响应字段说明) {String} content.wirelessIp 无线IP
     * @apiSuccess (响应字段说明) {String} content.wirelessMacAddr 无线mac地址
     * @apiSuccess (响应字段说明) {String} content.ssid wifi名称
     * @apiSuccess (响应字段说明) {Boolean} content.enableVisitorLogin 是否启用访客模式
     * @apiSuccess (响应字段说明) {Boolean} content.enableAutoLogin 是否启用自动登录
     * @apiSuccess (响应字段说明) {Long} content.onlineTime 在线时间
     * @apiSuccess (响应字段说明) {String} content.desktopName 桌面名称
     * @apiSuccess (响应字段说明) {Date} content.createTime 创建时间
     * @apiSuccess (响应字段说明) {String} content.serialNumber SN序列号
     * @apiSuccess (响应字段说明) {TerminalCloudDesktopDTO} content.terminalCloudDesktop 云桌面信息
     * @apiSuccess (响应字段说明) {UUID} content.terminalCloudDesktop.desktopId 桌面ID
     * @apiSuccess (响应字段说明) {String} content.terminalCloudDesktop.imageTemplateName 镜像名称
     * @apiSuccess (响应字段说明) {String} content.terminalCloudDesktop.deskIp 云桌面Ip
     * @apiSuccess (响应字段说明) {Integer} content.terminalCloudDesktop.systemSize 系统盘大小
     * @apiSuccess (响应字段说明) {Integer} content.terminalCloudDesktop.personSize 个人盘大小
     * @apiSuccess (响应字段说明) {Boolean} content.terminalCloudDesktop.isAllowLocalDisk 是否开启本地盘
     * @apiSuccess (响应字段说明) {String} content.terminalCloudDesktop.deskMac 云桌面mac
     * @apiSuccess (响应字段说明) {Boolean} content.terminalCloudDesktop.autoDhcp 云桌面ip获取方式
     * @apiSuccess (响应字段说明) {String} content.terminalCloudDesktop.gateWay 云桌面网关
     * @apiSuccess (响应字段说明) {String} content.terminalCloudDesktop.mask 云桌面掩码
     * @apiSuccess (响应字段说明) {Boolean} content.terminalCloudDesktop.autoDns 云桌面dns获取方式
     * @apiSuccess (响应字段说明) {String} content.terminalCloudDesktop.dnsPrimary 云桌面首选DNS
     * @apiSuccess (响应字段说明) {String} content.terminalCloudDesktop.dnsSecondary 云桌面备选DNS
     * @apiSuccessExample {json} 成功响应
     *                    {
     *                    "content":{
     *                    "accessInternet":1,
     *                    "bandwidthThreshold":20.0,
     *                    "bindDeskId":"8dadded1-4995-4d95-babf-8b1386ef5d0d",
     *                    "bindUserId":"8dadded1-4995-4d95-babf-8b1386ef5d0d",
     *                    "bindUserName":"bindUserName",
     *                    "cpuMode":"cpuMode",
     *                    "createTime":1597922433047,
     *                    "delayThreshold":20,
     *                    "desktopName":"desktopName",
     *                    "detectState":"detectState",
     *                    "detectTime":1597922433047,
     *                    "diskSize":1,
     *                    "enableAutoLogin":false,
     *                    "enableVisitorLogin":false,
     *                    "getIpMode":"AUTO",
     *                    "hardwareVersion":"hardwareVersion",
     *                    "id":"id",
     *                    "ip":"ip",
     *                    "ipConflict":1,
     *                    "lastOfflineTime":1597922433047,
     *                    "lastOnlineTime":1597922433047,
     *                    "macAddr":"macAddr",
     *                    "memorySize":1,
     *                    "networkAccessMode":"WIRED",
     *                    "onlineTime":1,
     *                    "packetLossRateThreshold":0.1,
     *                    "platform":"IDV",
     *                    "productId":"productId",
     *                    "productType":"productType",
     *                    "rainOsVersion":"rainOsVersion",
     *                    "rainUpgradeVersion":"rainUpgradeVersion",
     *                    "serialNumber":"serialNumber",
     *                    "ssid":"ssid",
     *                    "terminalCloudDesktop":{
     *                    "allowLocalDisk":false,
     *                    "autoDhcp":false,
     *                    "autoDns":false,
     *                    "deskIp":"deskIp",
     *                    "deskMac":"deskMac",
     *                    "desktopId":"8dadded1-4995-4d95-babf-8b1386ef5d0d",
     *                    "dnsPrimary":"dnsPrimary",
     *                    "dnsSecondary":"dnsSecondary",
     *                    "gateWay":"gateWay",
     *                    "imageTemplateName":"imageTemplateName",
     *                    "mask":"mask",
     *                    "personSize":1,
     *                    "systemSize":1
     *                    },
     *                    "terminalGroupId":"8dadded1-4995-4d95-babf-8b1386ef5d0d",
     *                    "terminalGroupName":"terminalGroupName",
     *                    "terminalGroupNameArr":["test"],
     *                    "terminalName":"terminalName",
     *                    "terminalOsType":"terminalOsType",
     *                    "terminalState":"ONLINE",
     *                    "wirelessIp":"wirelessIp",
     *                    "wirelessMacAddr":"wirelessMacAddr"
     *                    },
     *                    "message":"",
     *                    "msgArgArr":[],
     *                    "msgKey":"",
     *                    "status":"SUCCESS"
     *                    }
     * @apiErrorExample {json} 异常响应
     *                  {
     *                  "message":"",
     *                  "msgArgArr":[],
     *                  "msgKey":"",
     *                  "status":"ERROR"
     *                  }
     */
    /**
     * 终端详情
     *
     * @param request SimpleTerminalIdWebRequest
     * @return DefaultWebResponse
     * @throws BusinessException 业务异常
     */
    @ApiOperation("终端详情")
    @ApiVersions({@ApiVersion(value = Version.V3_1_1, descriptions = {"分级分权-区分权限-终端详情"})})
    @RequestMapping(value = "getInfo", method = RequestMethod.POST)
    public CommonWebResponse<TerminalDTO> getInfo(SimpleTerminalIdWebRequest request) throws BusinessException {
        Assert.notNull(request, "request can not be null");

        TerminalDTO terminalDTO = userTerminalMgmtAPI.getTerminalById(request.getId());
        // 返回带宽、丢包率、网络时延 阈值
        terminalDTO.setBandwidthThreshold(Constants.TERMINAL_DETECT_BINDWIDTH_NORM);
        terminalDTO.setPacketLossRateThreshold(Constants.TERMINAL_DETECT_PACKET_LOSS_RATE);
        terminalDTO.setDelayThreshold(Constants.TERMINAL_DETECT_DELAY_NORM);

        return CommonWebResponse.success(terminalDTO);
    }

    /**
     * 获取磁盘信息
     *
     * @param request SimpleTerminalIdWebRequest
     * @return CommonWebResponse
     * @throws BusinessException BusinessException
     */
    @ApiOperation("获取磁盘信息")
    @RequestMapping(value = "/deskInfo/list", method = RequestMethod.POST)
    @ApiVersions({@ApiVersion(value = Version.V3_1_1, descriptions = {"分级分权-区分权限-获取磁盘信息"})})
    public CommonWebResponse<DefaultPageResponse> getDeskInfo(SimpleTerminalIdWebRequest request) throws BusinessException {
        Assert.notNull(request, "request can not be null");

        DefaultPageResponse<TerminalDeskInfoDTO> response = userTerminalMgmtAPI.getTerminalDeskInfoList(request.getId());

        return CommonWebResponse.success(response);
    }

    /**
     * 获取终端网络信息
     *
     * @param request 请求参数
     * @return 终端网络信息
     * @throws BusinessException 业务异常
     */
    @ApiOperation("获取终端网络信息")
    @RequestMapping(value = "/networkCard/list")
    @ApiVersions({@ApiVersion(value = Version.V3_1_1, descriptions = {"分级分权-区分权限-获取终端网络信息"})})
    public DefaultWebResponse listNetworkCardInfo(SimpleTerminalIdWebRequest request) throws BusinessException {
        Assert.notNull(request, "request can not be null");

        CbbTerminalBasicInfoDTO basicInfoResponse = cbbTerminalOperatorAPI.findBasicInfoByTerminalId(request.getId());
        return DefaultWebResponse.Builder.success(ImmutableMap.of("itemArr", basicInfoResponse.getNetworkInfoArr()));
    }

    /**
     * 获取终端网络信息
     *
     * @param request 请求参数
     * @return 终端网络信息
     * @throws BusinessException 业务异常
     */
    @ApiOperation("获取终端网络信息")
    @RequestMapping(value = "/network/list")
    @ApiVersions({@ApiVersion(value = Version.V3_1_1, descriptions = {"分级分权-区分权限-获取终端网络信息"})})
    public DefaultWebResponse listNetworkInfo(PageWebRequest request) throws BusinessException {
        Assert.notNull(request, "request can not be null");

        CbbTerminalBasicInfoDTO basicInfoResponse = cbbTerminalOperatorAPI.findBasicInfoByTerminalId(getTerminalIdFromRequest(request));
        return DefaultWebResponse.Builder.success(ImmutableMap.of("itemArr", basicInfoResponse.getNetworkInfoArr()));
    }

    private String getTerminalIdFromRequest(PageWebRequest request) throws BusinessException {
        ExactMatch[] exactMatchArr = request.getExactMatchArr();
        if (ArrayUtils.isEmpty(exactMatchArr) || exactMatchArr.length != 1) {
            throw new BusinessException(TerminalBusinessKey.RCDC_COMMON_REQUEST_PARAM_ERROR);
        }

        ExactMatch terminalIdExactMatch = exactMatchArr[0];
        if (!EXACT_MATCH_PARAM_ID.equals(terminalIdExactMatch.getName())) {
            throw new BusinessException(TerminalBusinessKey.RCDC_COMMON_REQUEST_PARAM_ERROR);
        }

        String[] valueArr = terminalIdExactMatch.getValueArr();
        if (ArrayUtils.isEmpty(valueArr) || valueArr.length != 1) {
            throw new BusinessException(TerminalBusinessKey.RCDC_COMMON_REQUEST_PARAM_ERROR);
        }
        String terminalId = valueArr[0];
        if (StringUtils.isBlank(terminalId)) {
            throw new BusinessException(TerminalBusinessKey.RCDC_COMMON_REQUEST_PARAM_ERROR);
        }

        return terminalId;
    }

    /**
     * 获取终端编辑详情
     *
     * @param request 请求参数
     * @return 终端编辑详情
     * @throws BusinessException 业务异常
     */
    @ApiOperation("终端详情")
    @RequestMapping(value = "detail")
    @ApiVersions({@ApiVersion(value = Version.V3_1_1, descriptions = {"分级分权-区分权限-终端详情"})})
    public DefaultWebResponse detail(SimpleTerminalIdWebRequest request) throws BusinessException {
        Assert.notNull(request, "request can not be null");

        TerminalDTO terminalDTO = userTerminalMgmtAPI.getTerminalById(request.getId());
        TerminalDetailVO detailVO = buildDetailVO(terminalDTO);
        return DefaultWebResponse.Builder.success(detailVO);
    }

    /**
     * 构造终端编辑详情
     *
     * @param detailDTO 详情对象
     * @return
     */
    private TerminalDetailVO buildDetailVO(TerminalDTO detailDTO) {
        TerminalDetailVO detailVO = new TerminalDetailVO();
        detailVO.setId(detailDTO.getId());
        detailVO.setTerminalName(detailDTO.getTerminalName());
        detailVO.setIdvTerminalMode(detailDTO.getIdvTerminalMode());
        detailVO.setEnableVisitorLogin(detailDTO.getEnableVisitorLogin());
        detailVO.setEnableAutoLogin(detailDTO.getEnableAutoLogin());
        IdLabelEntry idLabelEntry = new IdLabelEntry();
        idLabelEntry.setId(detailDTO.getTerminalGroupId());
        idLabelEntry.setLabel(detailDTO.getTerminalGroupName());
        detailVO.setTerminalGroup(idLabelEntry);
        IdLabelEntry bindUserIdLabelEntry = new IdLabelEntry();
        bindUserIdLabelEntry.setId(detailDTO.getBindUserId());
        bindUserIdLabelEntry.setLabel(detailDTO.getBindUserName());
        detailVO.setBindUser(bindUserIdLabelEntry);
        return detailVO;
    }

    /**
     * @apiIgnore
     * @api {POST} /rco/terminal/edit
     * @apiName /edit
     * @apiGroup /rco/terminal
     * @apiDescription 编辑终端
     * @apiParam (请求体字段说明) {String} id 终端Id
     * @apiParam (请求体字段说明) {String} terminalName 终端名称
     * @apiParam (请求体字段说明) {IdLabelEntry} terminalGroup 终端组
     * @apiParam (请求体字段说明) {UUID} terminalGroup.id
     * @apiParam (请求体字段说明) {String} terminalGroup.label
     * @apiParamExample {json} 请求体示例
     *                  {
     *                  "id":"id",
     *                  "terminalGroup":{
     *                  "id":"01fe8677-0444-4a02-8d29-b710bcf27f77",
     *                  "label":"label"
     *                  },
     *                  "terminalName":"terminalName"
     *                  }
     * @apiSuccess (响应字段说明) {Object} content 响应信息
     * @apiSuccess (响应字段说明) {String} message 提示信息
     * @apiSuccess (响应字段说明) {String[]} msgArgArr 消息参数填充
     * @apiSuccess (响应字段说明) {String} msgKey 消息参数填充
     * @apiSuccess (响应字段说明) {String} status 状态信息
     * @apiSuccessExample {json} 成功响应
     *                    {
     *                    "message":"操作成功",
     *                    "msgArgArr":[],
     *                    "msgKey":"rcdc_rco_module_operate_success",
     *                    "status":"SUCCESS"
     *                    }
     * @apiErrorExample {json} 异常响应
     *                  {
     *                  "message":"操作失败，失败原因：",
     *                  "msgArgArr":[],
     *                  "msgKey":"rcdc_rco_module_operate_fail",
     *                  "status":"ERROR"
     *                  }
     */
    /**
     * 终端编辑
     *
     * @param request        EditTerminalWebRequest
     * @return DefaultWebResponse
     * @throws BusinessException 业务异常
     */
    @ApiOperation("终端编辑")
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    @ApiVersions({@ApiVersion(value = Version.V3_1_1, descriptions = {"分级分权-区分权限-终端编辑"})})

    @EnableAuthority
    public CommonWebResponse edit(EditTerminalWebRequest request) throws BusinessException {
        Assert.notNull(request, "request can not be null");

        String terminalIdentification = request.getId();
        try {
            CbbTerminalBasicInfoDTO response = cbbTerminalOperatorAPI.findBasicInfoByTerminalId(request.getId());
            LOGGER.info("终端信息{}", response);
            terminalIdentification = response.getUpperMacAddrOrTerminalId();
            UUID targetGroupId = request.getTerminalGroup().getId();
            String terminalId = request.getId();
            updateTerminalInfo(terminalId, targetGroupId, request.getTerminalName());
            // 白名单 只属于IDV 更新IDV 终端WIFI白名单
            if (response.getTerminalPlatform() == CbbTerminalPlatformEnums.IDV) {
                wifiWhitelistAPI.notifyIdvIfTargetGroupHasWhitelist(targetGroupId, terminalId);
            }
            auditLogAPI.recordLog(UserBusinessKey.RCDC_RCO_TERMINAL_EDIT_TERMINAL_SUCCESS_LOG, terminalIdentification);
            return CommonWebResponse.success(UserBusinessKey.RCDC_RCO_MODULE_OPERATE_SUCCESS, new String[]{});
        } catch (BusinessException ex) {
            auditLogAPI.recordLog(UserBusinessKey.RCDC_RCO_TERMINAL_EDIT_TERMINAL_FAIL_LOG, terminalIdentification, ex.getI18nMessage());
            return CommonWebResponse.fail(UserBusinessKey.RCDC_RCO_MODULE_OPERATE_FAIL, new String[]{ex.getI18nMessage()});
        }
    }


    /**
     * 删除终端
     *
     * @param request        请求参数
     * @param builder        批量操作任务对象
     * @return 请求结果
     * @throws BusinessException 业务异常
     */
    @RequestMapping(value = "delete")
    @ApiOperation("删除")
    @ApiVersions({@ApiVersion(value = Version.V3_1_1, descriptions = {"分级分权-区分权限-删除"})})

    @EnableAuthority
    public DefaultWebResponse delete(DelTerminalWebRequest request, BatchTaskBuilder builder) throws BusinessException {
        Assert.notNull(request, "request can not be null");
        Assert.notNull(builder, "builder can not be null");

        String[] terminalIdArr = request.getIdArr();

        if (terminalIdArr.length == 1) {
            return deleteSingleTerminal(terminalIdArr[0]);
        }

        Map<UUID, String> idMap = TerminalIdMappingUtils.mapping(terminalIdArr);
        UUID[] idArr = TerminalIdMappingUtils.extractUUID(idMap);
        final Iterator<DefaultBatchTaskItem> iterator = Stream.of(idArr).distinct().map(id -> DefaultBatchTaskItem.builder().itemId(id)
                .itemName(UserBusinessKey.RCDC_RCO_TERMINAL_DELETE_TERMINAL_ITEM_NAME, new String[]{}).build()).iterator();
        final DeleteTerminalBatchTaskHandler handler = new DeleteTerminalBatchTaskHandler(this.userTerminalMgmtAPI, idMap, iterator, auditLogAPI);
        handler.setCbbTerminalBasicInfoAPI(cbbTerminalOperatorAPI);
        handler.setTerminalManagePwdAPI(terminalManagePwdAPI);

        BatchTaskSubmitResult result = builder.setTaskName(UserBusinessKey.RCDC_RCO_TERMINAL_DELETE_TERMINAL_TASK_NAME)
                .setTaskDesc(UserBusinessKey.RCDC_RCO_TERMINAL_DELETE_TERMINAL_TASK_DESC)//
                .registerHandler(handler).start();

        return DefaultWebResponse.Builder.success(result);
    }

    private DefaultWebResponse deleteSingleTerminal(String terminalId) throws BusinessException {
        String terminalIdentification = terminalId;

        try {
            CbbTerminalBasicInfoDTO response = cbbTerminalOperatorAPI.findBasicInfoByTerminalId(terminalId);
            terminalIdentification = response.getUpperMacAddrOrTerminalId();
            userTerminalMgmtAPI.delete(terminalId);
            terminalManagePwdAPI.deleteAuthenticationByTerminalId(response.getId());
            auditLogAPI.recordLog(UserBusinessKey.RCDC_RCO_TERMINAL_DELETE_TERMINAL_SUCCESS_LOG, terminalIdentification);
            return DefaultWebResponse.Builder.success(UserBusinessKey.RCDC_RCO_TERMINAL_DELETE_TERMINAL_SUCCESS, new String[]{});
        } catch (BusinessException e) {
            LOGGER.error("删除终端失败", e);
            auditLogAPI.recordLog(UserBusinessKey.RCDC_RCO_TERMINAL_DELETE_TERMINAL_FAIL_LOG, terminalIdentification, e.getI18nMessage());
            throw new BusinessException(UserBusinessKey.RCDC_RCO_TERMINAL_DELETE_TERMINAL_FAIL, e, e.getI18nMessage());
        }
    }

    /**
     * 批量更新终端分组
     *
     * @param request        请求参数
     * @param builder        任务 builder
     * @return 请求结果
     * @throws BusinessException 业务异常
     */
    @ApiOperation("移动分组")
    @RequestMapping(value = "updateGroup", method = RequestMethod.POST)
    @ApiVersions({@ApiVersion(value = Version.V3_1_1, descriptions = {"分级分权-区分权限-移动分组"})})
    @EnableAuthority
    public CommonWebResponse<BatchTaskSubmitResult> updateGroup(UpdateTerminalGroupWebRequest request,
                                                                BatchTaskBuilder builder) throws BusinessException {
        Assert.notNull(request, "request can not be null");
        Assert.notNull(builder, "builder can not be null");

        String[] terminalIdArr = request.getIdArr();

        userTerminalMgmtAPI.verifyTerminalIdExist(terminalIdArr);

        final UUID terminalGroupId = request.getTerminalGroupId();

        cbbTerminalGroupMgmtAPI.loadById(terminalGroupId);

        BatchTaskSubmitResult result = builder.setTaskName(UserBusinessKey.RCDC_RCO_TERMINAL_UPDATE_TERMINAL_GROUP_TASK_NAME)
                .setTaskDesc(UserBusinessKey.RCDC_RCO_TERMINAL_UPDATE_TERMINAL_GROUP_TASK_DESC)//
                .registerHandler(updateTerminalGroupHandlerFactory.createHandler(terminalIdArr, terminalGroupId)).start();

        return CommonWebResponse.success(result);
    }


    private void updateTerminalInfo(String terminalId, UUID terminalGroupId, String terminalName) throws BusinessException {
        CbbModifyTerminalDTO terminalRequest = new CbbModifyTerminalDTO();
        terminalRequest.setCbbTerminalId(terminalId);
        terminalRequest.setGroupId(terminalGroupId);
        terminalRequest.setTerminalName(terminalName);
        cbbTerminalOperatorAPI.modifyTerminal(terminalRequest);
    }

    /**
     * 修改IDV终端网卡工作模式
     *
     * @param request 请求
     * @param builder 批处理Builder
     * @return 处理结果
     * @throws BusinessException 业务异常
     */
    @ApiOperation("修改IDV终端网卡工作模式")
    @RequestMapping(value = "configNicWorkMode", method = RequestMethod.POST)
    @EnableAuthority
    public DefaultWebResponse configNicWorkMode(ConfigNicWorkModeRequest request, BatchTaskBuilder builder)
            throws BusinessException {
        Assert.notNull(request, "request must not be null");
        Assert.notNull(builder, "builder must not be null");

        String[] terminalIdArr = request.getIdArr();
        Map<UUID, String> idMap = TerminalIdMappingUtils.mapping(terminalIdArr);
        UUID[] idArr = TerminalIdMappingUtils.extractUUID(idMap);
        final Iterator<DefaultBatchTaskItem> iterator = Stream.of(idArr).distinct().map(id -> DefaultBatchTaskItem.builder().itemId(id)
                .itemName(TerminalBusinessKey.RCDC_TERMINAL_MODIFY_NIC_WORK_MODE_ITEM_NAME, new String[]{}).build()).iterator();
        ConfigNicWorkModeHandler handler = new ConfigNicWorkModeHandler(idMap, iterator);
        handler.setAuditLogAPI(auditLogAPI);
        handler.setNicWorkMode(request.getNicWorkMode());
        handler.setUserTerminalMgmtAPI(userTerminalMgmtAPI);
        handler.setCbbTerminalOperatorAPI(cbbTerminalOperatorAPI);

        BatchTaskSubmitResult result = executeConfigNicWorkMode(builder, idArr, handler);

        return DefaultWebResponse.Builder.success(result);
    }

    private BatchTaskSubmitResult executeConfigNicWorkMode(BatchTaskBuilder builder, UUID[] idArr, final ConfigNicWorkModeHandler handler)
            throws BusinessException {
        BatchTaskSubmitResult result;
        if (idArr.length == 1) {
            result = builder.setTaskName(TerminalBusinessKey.RCDC_TERMINAL_MODIFY_NIC_WORK_MODE_ITEM_NAME)
                    .setTaskDesc(TerminalBusinessKey.RCDC_TERMINAL_MODIFY_NIC_WORK_MODE_SINGLE_TASK_DESC).registerHandler(handler).start();
        } else {
            result = builder.setTaskName(TerminalBusinessKey.RCDC_TERMINAL_MODIFY_NIC_WORK_MODE_TASK_NAME)
                    .setTaskDesc(TerminalBusinessKey.RCDC_TERMINAL_MODIFY_NIC_WORK_MODE_TASK_DESC).enableParallel().registerHandler(handler).start();
        }

        return result;
    }

    /**
     * 获取单台终端的网卡工作模式
     *
     * @param request 请求
     * @return 网卡工作模式
     * @throws BusinessException 业务异常
     */
    @ApiOperation("获取IDV终端网卡工作模式")
    @RequestMapping(value = "getNicWorkMode", method = RequestMethod.POST)
    public DefaultWebResponse getNicWorkMode(SimpleTerminalIdWebRequest request) throws BusinessException {
        Assert.notNull(request, "request must not be null");

        CbbNicWorkModeEnums workMode = cbbTerminalOperatorAPI.findBasicInfoByTerminalId(request.getId()).getNicWorkMode();
        return DefaultWebResponse.Builder.success(new NicWorkModeInfoVO(workMode));
    }

    /**
     * 用户变更
     *
     * @param request 请求入参
     * @return 处理结果
     * @throws BusinessException 业务异常
     */
    @ApiOperation("用户变更")
    @RequestMapping(value = "bindUser", method = RequestMethod.POST)
    @EnableAuthority
    public DefaultWebResponse bindUser(BindUserRequest request) throws BusinessException {
        Assert.notNull(request, "request must not be null");
        userTerminalMgmtAPI.bindUser(request.getId(), request.getBindUserId());
        return DefaultWebResponse.Builder.success(UserBusinessKey.RCDC_RCO_MODULE_OPERATE_SUCCESS, new String[]{});
    }

    /**
     * idv终端修改云服务器地址
     *
     * @param request        请求参数
     * @param builder        批量任务创建对象
     * @return 请求结果
     * @throws BusinessException 业务异常
     */
    @ApiOperation("批量修改终端云服务器地址")
    @RequestMapping(value = "/changeServerIp", method = RequestMethod.POST)
    public CommonWebResponse<BatchTaskSubmitResult> idvChangeServerIp(ChangeServerIpWebRequest request,
                                                                      BatchTaskBuilder builder) throws BusinessException {
        Assert.notNull(request, "TerminalIdWebRequest不能为null");
        Assert.notNull(builder, "builder不能为null");
        String serverIp = request.getServerIp();
        Assert.notNull(serverIp, "serverIp不能为null");

        List<TerminalDTO> terminalDTOList =
                userTerminalMgmtAPI.queryListByPlatformAndState(CbbTerminalPlatformEnums.IDV, CbbTerminalStateEnums.ONLINE);
        if (CollectionUtils.isEmpty(terminalDTOList)) {
            LOGGER.info("没有在线的IDV终端，不下发修改终端云服务器地址");
            return CommonWebResponse.fail(TerminalBusinessKey.RCDC_TERMINAL_CHANGE_SERVER_IP_NO_ONLINE, new String[]{});
        }

        Set<String> terminalIdSet = terminalDTOList.stream() //
                .map(TerminalDTO::getId) //
                .collect(Collectors.toSet());

        Map<UUID, String> idMap = new HashMap<>();
        final Iterator<DefaultBatchTaskItem> iterator = terminalIdSet.stream().map(id -> {
            UUID itemId = UUID.randomUUID();
            idMap.put(itemId, id);
            return DefaultBatchTaskItem.builder()
                    .itemId(itemId) //
                    .itemName(TerminalBusinessKey.RCDC_TERMINAL_CHANGE_SERVER_IP_ITEM_NAME, new String[]{}) //
                    .build();
        }).iterator();
        ChangeIdvServerIpBatchTaskHandler handler =
                new ChangeIdvServerIpBatchTaskHandler(iterator, idMap, userTerminalMgmtAPI, auditLogAPI, serverIp);

        BatchTaskSubmitResult result = builder.setTaskName(TerminalBusinessKey.RCDC_TERMINAL_CHANGE_SERVER_IP_ITEM_NAME)
                .setTaskDesc(TerminalBusinessKey.RCDC_TERMINAL_CHANGE_SERVER_IP_ITEM_DESC).registerHandler(handler).start();
        return CommonWebResponse.success(result);
    }
}
