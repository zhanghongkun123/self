package com.ruijie.rcos.rcdc.rco.module.def.appcenter.dto;

import java.util.List;
import java.util.UUID;

import com.ruijie.rcos.rcdc.appcenter.module.def.enums.AppStatusEnum;
import com.ruijie.rcos.rcdc.appcenter.module.def.enums.DataSourceTypeEnum;
import com.ruijie.rcos.rcdc.clouddesktop.module.def.enums.CbbImageType;
import com.ruijie.rcos.rcdc.clouddesktop.module.def.enums.CbbOsType;
import com.ruijie.rcos.rcdc.hciadapter.module.def.enums.CloudPlatformStatus;
import com.ruijie.rcos.rcdc.rco.module.def.enums.RequestSourceEnum;

/**
 * Description:
 * Copyright: Copyright (c)
 * Company: Ruijie Co., Ltd.
 * Create Time: 2023/01/17 15:14
 *
 * @author coderLee23
 */
public class SearchAppDiskDTO extends BasePermissionDTO {


    /**
     * 应用id
     */
    private UUID id;

    /**
     * 支持应用名称模糊查询，忽略大小写
     */
    private String appName;

    /**
     * 过滤已被交付组添加的应用
     */
    private UUID filterGroupId;

    /**
     * 数据来源
     */
    private DataSourceTypeEnum dataSourceType;

    /**
     * 应用状态
     */
    private List<AppStatusEnum> appStatusList;

    /**
     * 镜像类型
     */
    private List<CbbImageType> cbbImageTypeList;

    /**
     * 操作系统类型
     */
    private List<CbbOsType> cbbOsTypeList;

    /**
     * 云平台状态
     */
    private List<CloudPlatformStatus> platformStatusList;

    /**
     * 请求源
     */
    private RequestSourceEnum requestSource;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public UUID getFilterGroupId() {
        return filterGroupId;
    }

    public void setFilterGroupId(UUID filterGroupId) {
        this.filterGroupId = filterGroupId;
    }

    public DataSourceTypeEnum getDataSourceType() {
        return dataSourceType;
    }

    public void setDataSourceType(DataSourceTypeEnum dataSourceType) {
        this.dataSourceType = dataSourceType;
    }

    public List<AppStatusEnum> getAppStatusList() {
        return appStatusList;
    }

    public void setAppStatusList(List<AppStatusEnum> appStatusList) {
        this.appStatusList = appStatusList;
    }

    public List<CbbImageType> getCbbImageTypeList() {
        return cbbImageTypeList;
    }

    public void setCbbImageTypeList(List<CbbImageType> cbbImageTypeList) {
        this.cbbImageTypeList = cbbImageTypeList;
    }

    public List<CbbOsType> getCbbOsTypeList() {
        return cbbOsTypeList;
    }

    public void setCbbOsTypeList(List<CbbOsType> cbbOsTypeList) {
        this.cbbOsTypeList = cbbOsTypeList;
    }

    public RequestSourceEnum getRequestSource() {
        return requestSource;
    }

    public void setRequestSource(RequestSourceEnum requestSource) {
        this.requestSource = requestSource;
    }

    public List<CloudPlatformStatus> getPlatformStatusList() {
        return platformStatusList;
    }

    public void setPlatformStatusList(List<CloudPlatformStatus> platformStatusList) {
        this.platformStatusList = platformStatusList;
    }
}
