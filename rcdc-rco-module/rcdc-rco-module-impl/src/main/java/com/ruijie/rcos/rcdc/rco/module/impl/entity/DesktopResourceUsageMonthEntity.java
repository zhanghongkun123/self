package com.ruijie.rcos.rcdc.rco.module.impl.entity;

import com.ruijie.rcos.rcdc.rco.module.def.api.enums.ResourceTypeEnum;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import org.springframework.util.Assert;

/**
 * Title: ServerResourceUsageDayEntity
 * Description: 云桌面资源利用率（月）表实体类
 * Copyright: Ruijie Co., Ltd. (c) 2019
 *
 * @Author: zhangyichi
 * @Date: 2019/7/23 10:12
 */
@Entity
@Table(name = "t_rco_desktop_resource_usage_month")
public class DesktopResourceUsageMonthEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private Date createTime;

    private UUID desktopId;

    private Date statisticTime;

    private Double cpuUsage;

    private Double memoryUsage;

    private Double diskUsage;

    @Version
    private Integer version;

    /**
     * 获取对应资源类型的值
     * @param resourceType 资源类型
     * @return Double 对应资源类型的值
     */
    public Double getUsage(ResourceTypeEnum resourceType) {
        Assert.notNull(resourceType, "the resourceType is null");
        switch (resourceType) {
            case CPU:
                return getCpuUsage();
            case MEMORY:
                return getMemoryUsage();
            case DISK:
                return getDiskUsage();
            default:
                throw new IllegalArgumentException("the resourceType[" + resourceType + "] is illegal.");
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public UUID getDesktopId() {
        return desktopId;
    }

    public void setDesktopId(UUID desktopId) {
        this.desktopId = desktopId;
    }

    public Date getStatisticTime() {
        return statisticTime;
    }

    public void setStatisticTime(Date statisticTime) {
        this.statisticTime = statisticTime;
    }

    public Double getCpuUsage() {
        return cpuUsage;
    }

    public void setCpuUsage(Double cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    public Double getMemoryUsage() {
        return memoryUsage;
    }

    public void setMemoryUsage(Double memoryUsage) {
        this.memoryUsage = memoryUsage;
    }

    public Double getDiskUsage() {
        return diskUsage;
    }

    public void setDiskUsage(Double diskUsage) {
        this.diskUsage = diskUsage;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
