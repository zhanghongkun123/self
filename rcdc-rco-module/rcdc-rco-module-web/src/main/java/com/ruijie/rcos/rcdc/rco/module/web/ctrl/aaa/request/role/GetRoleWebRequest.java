package com.ruijie.rcos.rcdc.rco.module.web.ctrl.aaa.request.role;

import java.util.UUID;

import com.ruijie.rcos.sk.base.annotation.NotNull;
import com.ruijie.rcos.sk.webmvc.api.request.WebRequest;

import io.swagger.annotations.ApiModelProperty;

/**
 * Description: Function Description
 * Copyright: Copyright (c) 2018
 * Company: Ruijie Co., Ltd.
 * Create Time: 2021年08月3日
 * 
 * @author linrenjian
 */
public class GetRoleWebRequest implements WebRequest {

    @ApiModelProperty(value = "管理员ID", required = true)
    @NotNull
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}
