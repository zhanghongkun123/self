package com.ruijie.rcos.rcdc.rco.module.web.ctrl.deskstrategy.request;

import com.ruijie.rcos.rcdc.clouddesktop.module.def.enums.CbbCloudDeskPattern;
import com.ruijie.rcos.rcdc.clouddesktop.module.def.enums.CbbKeyboardEmulationType;
import com.ruijie.rcos.rcdc.clouddesktop.module.def.enums.CbbSyncLoginAccountPermissionEnums;
import com.ruijie.rcos.rcdc.clouddesktop.module.def.enums.strategy.UsbStorageDeviceAccelerationEnum;
import com.ruijie.rcos.rcdc.rco.module.web.ctrl.clouddesktop.request.globalstrategy.EditWatermarkWebRequest;
import com.ruijie.rcos.sk.base.annotation.NotBlank;
import com.ruijie.rcos.sk.base.annotation.NotNull;
import com.ruijie.rcos.sk.base.annotation.Range;
import com.ruijie.rcos.sk.base.annotation.TextShort;
import com.ruijie.rcos.sk.webmvc.api.request.WebRequest;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.lang.Nullable;

import java.util.UUID;

/**
 * Description: Function Description
 * Copyright: Copyright (c) 2020
 * Company: Ruijie Co., Ltd.
 * Create Time: 2018年10月20日
 *
 * @author wjp
 */
public class CreateIDVDeskStrategyWebRequest extends AbstractDeskStrategyWebRequest implements WebRequest {

    @ApiModelProperty(value = "云桌面策略名称", required = true)
    @NotBlank
    @TextShort
    private String strategyName;

    @ApiModelProperty(value = "云桌面类型", required = true)
    @NotNull
    private CbbCloudDeskPattern desktopType;

    @ApiModelProperty(value = "系统盘（GB）", required = true)
    @Nullable
    @Range(min = "20", max = "1024")
    private Integer systemDisk;

    @ApiModelProperty(value = "计算机名前缀", required = false)
    @NotBlank
    private String computerName;

    @ApiModelProperty(value = "启用本地盘", required = true)
    @Nullable
    private Boolean enableAllowLocalDisk;

    @ApiModelProperty(value = "USB存储设备只读", required = true)
    @NotNull
    private Boolean enableUsbReadOnly;

    @ApiModelProperty(value = "启用云桌面重定向", required = true)
    @Nullable
    private Boolean enableOpenDesktopRedirect;

    @ApiModelProperty(value = "允许嵌套虚拟化", required = false)
    @Nullable
    private Boolean enableNested;

    @ApiModelProperty(value = "外设策略", required = true)
    @NotNull
    private UUID[] usbTypeIdArr;

    @ApiModelProperty(value = "AD域加入OU路径")
    @Nullable
    private String adOu;

    @ApiModelProperty(value = "是否开启系统盘自动扩容")
    @Nullable
    private Boolean enableFullSystemDisk;

    /**
     * 键盘模拟类型
     */
    @ApiModelProperty("键盘模拟类型")
    @Nullable
    private CbbKeyboardEmulationType keyboardEmulationType;

    /**
     * 水印配置内容
     */
    @ApiModelProperty("水印配置内容")
    @Nullable
    private EditWatermarkWebRequest watermarkInfo;


    /**
     * 桌面登录账号同步
     */
    @ApiModelProperty("桌面登录账号同步")
    @Nullable
    private Boolean desktopSyncLoginAccount;

    /**
     * 桌面登录密码同步
     */
    @ApiModelProperty("桌面登录密码同步")
    @Nullable
    private Boolean desktopSyncLoginPassword;

    /**
     * 桌面登录账号权限
     */
    @ApiModelProperty("桌面登录账号权限")
    @Nullable
    private CbbSyncLoginAccountPermissionEnums desktopSyncLoginAccountPermission;

    @ApiModelProperty("Usb存储设备加速")
    @Nullable
    private UsbStorageDeviceAccelerationEnum usbStorageDeviceAcceleration = UsbStorageDeviceAccelerationEnum.NO_ACCELERATION;

    @Nullable
    public String getAdOu() {
        return adOu;
    }

    public void setAdOu(@Nullable String adOu) {
        this.adOu = adOu;
    }

    public String getStrategyName() {
        return strategyName;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }

    public CbbCloudDeskPattern getDesktopType() {
        return desktopType;
    }

    public void setDesktopType(CbbCloudDeskPattern desktopType) {
        this.desktopType = desktopType;
    }

    public Integer getSystemDisk() {
        return systemDisk;
    }

    public void setSystemDisk(Integer systemDisk) {
        this.systemDisk = systemDisk;
    }

    public String getComputerName() {
        return computerName;
    }

    public void setComputerName(String computerName) {
        this.computerName = computerName;
    }

    public Boolean getEnableAllowLocalDisk() {
        return enableAllowLocalDisk;
    }

    public void setEnableAllowLocalDisk(Boolean enableAllowLocalDisk) {
        this.enableAllowLocalDisk = enableAllowLocalDisk;
    }

    public Boolean getEnableUsbReadOnly() {
        return enableUsbReadOnly;
    }

    public void setEnableUsbReadOnly(Boolean enableUsbReadOnly) {
        this.enableUsbReadOnly = enableUsbReadOnly;
    }

    public Boolean getEnableOpenDesktopRedirect() {
        return enableOpenDesktopRedirect;
    }

    public void setEnableOpenDesktopRedirect(Boolean enableOpenDesktopRedirect) {
        this.enableOpenDesktopRedirect = enableOpenDesktopRedirect;
    }

    public Boolean getEnableNested() {
        return enableNested;
    }

    public void setEnableNested(Boolean enableNested) {
        this.enableNested = enableNested;
    }

    public UUID[] getUsbTypeIdArr() {
        return usbTypeIdArr;
    }

    public void setUsbTypeIdArr(UUID[] usbTypeIdArr) {
        this.usbTypeIdArr = usbTypeIdArr;
    }

    @Nullable
    public Boolean getEnableFullSystemDisk() {
        return enableFullSystemDisk;
    }

    public void setEnableFullSystemDisk(@Nullable Boolean enableFullSystemDisk) {
        this.enableFullSystemDisk = enableFullSystemDisk;
    }

    @Nullable
    public CbbKeyboardEmulationType getKeyboardEmulationType() {
        return keyboardEmulationType;
    }

    public void setKeyboardEmulationType(@Nullable CbbKeyboardEmulationType keyboardEmulationType) {
        this.keyboardEmulationType = keyboardEmulationType;
    }

    @Nullable
    public EditWatermarkWebRequest getWatermarkInfo() {
        return watermarkInfo;
    }

    public void setWatermarkInfo(@Nullable EditWatermarkWebRequest watermarkInfo) {
        this.watermarkInfo = watermarkInfo;
    }

    @Nullable
    public Boolean getDesktopSyncLoginAccount() {
        return desktopSyncLoginAccount;
    }

    public void setDesktopSyncLoginAccount(@Nullable Boolean desktopSyncLoginAccount) {
        this.desktopSyncLoginAccount = desktopSyncLoginAccount;
    }

    @Nullable
    public Boolean getDesktopSyncLoginPassword() {
        return desktopSyncLoginPassword;
    }

    public void setDesktopSyncLoginPassword(@Nullable Boolean desktopSyncLoginPassword) {
        this.desktopSyncLoginPassword = desktopSyncLoginPassword;
    }

    @Nullable
    public CbbSyncLoginAccountPermissionEnums getDesktopSyncLoginAccountPermission() {
        return desktopSyncLoginAccountPermission;
    }

    public void setDesktopSyncLoginAccountPermission(@Nullable CbbSyncLoginAccountPermissionEnums desktopSyncLoginAccountPermission) {
        this.desktopSyncLoginAccountPermission = desktopSyncLoginAccountPermission;
    }

    @Nullable
    public UsbStorageDeviceAccelerationEnum getUsbStorageDeviceAcceleration() {
        return usbStorageDeviceAcceleration;
    }

    public void setUsbStorageDeviceAcceleration(@Nullable UsbStorageDeviceAccelerationEnum usbStorageDeviceAcceleration) {
        this.usbStorageDeviceAcceleration = usbStorageDeviceAcceleration;
    }

}
