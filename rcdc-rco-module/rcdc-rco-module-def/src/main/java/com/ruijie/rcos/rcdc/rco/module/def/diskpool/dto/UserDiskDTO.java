package com.ruijie.rcos.rcdc.rco.module.def.diskpool.dto;

import java.util.Date;
import java.util.UUID;

/**
 * Description: 用户磁盘关联DTO
 * Copyright: Copyright (c) 2022
 * Company: Ruijie Co., Ltd.
 * Create Time: 2022/7/14
 *
 * @author TD
 */
public class UserDiskDTO {

    private UUID id;

    private UUID diskId;

    private UUID userId;

    private Integer version;

    private Date createTime;

    private Date latestUseTime;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getDiskId() {
        return diskId;
    }

    public void setDiskId(UUID diskId) {
        this.diskId = diskId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLatestUseTime() {
        return latestUseTime;
    }

    public void setLatestUseTime(Date latestUseTime) {
        this.latestUseTime = latestUseTime;
    }

    @Override
    public String toString() {
        return "UserDiskDTO{" +
                "id=" + id +
                ", diskId=" + diskId +
                ", userId=" + userId +
                ", version=" + version +
                ", createTime=" + createTime +
                ", latestUseTime=" + latestUseTime +
                '}';
    }
}
