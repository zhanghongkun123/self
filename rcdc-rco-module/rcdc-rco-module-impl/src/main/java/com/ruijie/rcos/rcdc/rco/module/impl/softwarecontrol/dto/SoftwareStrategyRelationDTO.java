package com.ruijie.rcos.rcdc.rco.module.impl.softwarecontrol.dto;

import com.ruijie.rcos.rcdc.rco.module.def.softwarecontrol.enums.SoftwareRelationTypeEnum;

import java.util.Date;
import java.util.UUID;

/**
 * Description: 软件管控策略与其他对象，如桌面池等的绑定关系
 * Copyright: Copyright (c) 2021
 * Company: Ruijie Co., Ltd.
 * Create Time: 2021/12/31
 *
 * @author linke
 */
public class SoftwareStrategyRelationDTO {

    private UUID id;

    /**
     * 软件管控策略ID
     */
    private UUID softwareStrategyId;

    /**
     * 绑定对象类型：DESKTOP_POOL桌面池，其他待增加
     */
    private SoftwareRelationTypeEnum relationType;

    /**
     * 绑定对象ID
     */
    private UUID relationId;

    private Date createTime;

    private Date updateTime;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getSoftwareStrategyId() {
        return softwareStrategyId;
    }

    public void setSoftwareStrategyId(UUID softwareStrategyId) {
        this.softwareStrategyId = softwareStrategyId;
    }

    public SoftwareRelationTypeEnum getRelationType() {
        return relationType;
    }

    public void setRelationType(SoftwareRelationTypeEnum relationType) {
        this.relationType = relationType;
    }

    public UUID getRelationId() {
        return relationId;
    }

    public void setRelationId(UUID relationId) {
        this.relationId = relationId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
