package com.ruijie.rcos.rcdc.rco.module.web.ctrl.terminal.batchtask;

import com.ruijie.rcos.gss.log.module.def.api.BaseAuditLogAPI;
import com.ruijie.rcos.rcdc.rco.module.web.ctrl.terminal.TerminalBusinessKey;
import com.ruijie.rcos.rcdc.terminal.module.def.api.CbbTerminalOperatorAPI;
import com.ruijie.rcos.rcdc.terminal.module.def.api.CbbTerminalSystemUpgradeAPI;
import com.ruijie.rcos.rcdc.terminal.module.def.api.dto.CbbTerminalBasicInfoDTO;
import com.ruijie.rcos.rcdc.terminal.module.def.api.dto.CbbUpgradeTerminalDTO;
import com.ruijie.rcos.sk.base.batch.*;
import com.ruijie.rcos.sk.base.exception.BusinessException;
import com.ruijie.rcos.sk.base.log.Logger;
import com.ruijie.rcos.sk.base.log.LoggerFactory;
import org.springframework.util.Assert;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

/**
 * 
 * Description: 批量升级终端任务处理器
 * Copyright: Copyright (c) 2018
 * Company: Ruijie Co., Ltd.
 * Create Time: 2019年1月28日
 * 
 * @author nt
 */
public class AddUpgradeTerminalBatchTaskHandler extends AbstractBatchTaskHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddUpgradeTerminalBatchTaskHandler.class);

    private BaseAuditLogAPI auditLogAPI;

    private Map<UUID, String> idMap;

    private CbbTerminalSystemUpgradeAPI cbbTerminalUpgradeAPI;

    private CbbTerminalOperatorAPI cbbTerminalOperatorAPI;

    public AddUpgradeTerminalBatchTaskHandler(CbbTerminalSystemUpgradeAPI cbbTerminalUpgradeAPI, Map<UUID, String> idMap,
                                              Iterator<? extends BatchTaskItem> iterator, BaseAuditLogAPI auditLogAPI,
                                              CbbTerminalOperatorAPI cbbTerminalOperatorAPI) {
        super(iterator);
        this.auditLogAPI = auditLogAPI;
        this.idMap = idMap;
        this.cbbTerminalUpgradeAPI = cbbTerminalUpgradeAPI;
        this.cbbTerminalOperatorAPI = cbbTerminalOperatorAPI;
    }

    @Override
    public BatchTaskItemResult processItem(BatchTaskItem taskItem) throws BusinessException {
        Assert.notNull(taskItem, "BatchTaskItem不能为null");

        TerminalUpgradeBatchTaskItem upgradeItem = (TerminalUpgradeBatchTaskItem) taskItem;
        String terminalId = idMap.get(upgradeItem.getItemID());
        UUID upgradeTaskId = upgradeItem.getUpgradeTaskId();

        return addUpgradeTaskAddOptLog(terminalId, upgradeTaskId);
    }

    private BatchTaskItemResult addUpgradeTaskAddOptLog(String terminalId, UUID upgradeTaskId) throws BusinessException {
        String terminalAddr = terminalId;
        try {
            CbbTerminalBasicInfoDTO terminalBasicInfoDTO = cbbTerminalOperatorAPI.findBasicInfoByTerminalId(terminalId);
            terminalAddr = terminalBasicInfoDTO.getUpperMacAddrOrTerminalId();
        } catch (BusinessException e) {
            LOGGER.debug("查询终端信息发生异常，终端id: [{}]", terminalId);
        }

        CbbUpgradeTerminalDTO addRequest = new CbbUpgradeTerminalDTO();
        addRequest.setTerminalId(terminalId);
        addRequest.setUpgradeTaskId(upgradeTaskId);
        try {
            cbbTerminalUpgradeAPI.addSystemUpgradeTerminal(addRequest);
            auditLogAPI.recordLog(TerminalBusinessKey.RCDC_ADD_UPGRADE_TERMINAL_SUCCESS_LOG, terminalAddr);
            return DefaultBatchTaskItemResult.builder().batchTaskItemStatus(BatchTaskItemStatus.SUCCESS)
                    .msgKey(TerminalBusinessKey.RCDC_ADD_UPGRADE_TERMINAL_RESULT_SUCCESS).msgArgs(new String[] {terminalAddr}).build();
        } catch (BusinessException e) {
            auditLogAPI.recordLog(TerminalBusinessKey.RCDC_ADD_UPGRADE_TERMINAL_FAIL_LOG, terminalAddr, e.getI18nMessage());
            throw new BusinessException(TerminalBusinessKey.RCDC_ADD_UPGRADE_TERMINAL_RESULT_FAIL, e, terminalAddr,
                    e.getI18nMessage());
        }
    }

    @Override
    public BatchTaskFinishResult onFinish(int successCount, int failCount) {
        return buildDefaultFinishResult(successCount, failCount, TerminalBusinessKey.RCDC_ADD_UPGRADE_TERMINAL_RESULT);
    }
}
