package com.ruijie.rcos.rcdc.rco.module.web.ctrl.appcenter.request;

import java.util.List;
import java.util.UUID;

import org.springframework.lang.Nullable;

import com.alibaba.fastjson.annotation.JSONField;
import com.ruijie.rcos.rcdc.appcenter.module.def.enums.AppTypeEnum;
import com.ruijie.rcos.rcdc.appcenter.module.def.enums.DeliveryStatusEnum;
import com.ruijie.rcos.rcdc.appcenter.module.def.enums.ProgressStatusEnum;
import com.ruijie.rcos.sk.base.annotation.NotNull;
import com.ruijie.rcos.sk.webmvc.api.request.PageWebRequest;

import io.swagger.annotations.ApiModelProperty;

/**
 * Description:
 * Copyright: Copyright (c)
 * Company: Ruijie Co., Ltd.
 * Create Time: 2023/01/05 14:05
 *
 * @author coderLee23
 */
public class GetDeliveryObjectDetailPageWebRequest extends PageWebRequest {

    /**
     * 交付组id
     */
    @ApiModelProperty(value = " 交付组id", required = true)
    @NotNull
    private UUID deliveryGroupId;

    /**
     * 云桌面id
     */
    @ApiModelProperty(value = " 云桌面id", required = true)
    @NotNull
    private UUID cloudDesktopId;

    /**
     * 应用类型
     */
    @ApiModelProperty(value = " 应用类型", name = "appType")
    @Nullable
    @JSONField(name = "appType")
    private List<AppTypeEnum> appTypeList;

    /**
     * 交付状态
     */
    @ApiModelProperty(value = " 交付状态", name = "deliveryStatus")
    @Nullable
    @JSONField(name = "deliveryStatus")
    private List<DeliveryStatusEnum> deliveryStatusList;


    /**
     * 交付状态
     */
    @ApiModelProperty(value = " 交付进度", name = "progressStatus")
    @Nullable
    @JSONField(name = "progressStatus")
    private List<ProgressStatusEnum> progressStatusList;

    public UUID getDeliveryGroupId() {
        return deliveryGroupId;
    }

    public void setDeliveryGroupId(UUID deliveryGroupId) {
        this.deliveryGroupId = deliveryGroupId;
    }

    public UUID getCloudDesktopId() {
        return cloudDesktopId;
    }

    public void setCloudDesktopId(UUID cloudDesktopId) {
        this.cloudDesktopId = cloudDesktopId;
    }

    @Nullable
    public List<AppTypeEnum> getAppTypeList() {
        return appTypeList;
    }

    public void setAppTypeList(@Nullable List<AppTypeEnum> appTypeList) {
        this.appTypeList = appTypeList;
    }

    @Nullable
    public List<DeliveryStatusEnum> getDeliveryStatusList() {
        return deliveryStatusList;
    }

    public void setDeliveryStatusList(@Nullable List<DeliveryStatusEnum> deliveryStatusList) {
        this.deliveryStatusList = deliveryStatusList;
    }

    @Nullable
    public List<ProgressStatusEnum> getProgressStatusList() {
        return progressStatusList;
    }

    public void setProgressStatusList(@Nullable List<ProgressStatusEnum> progressStatusList) {
        this.progressStatusList = progressStatusList;
    }
}
