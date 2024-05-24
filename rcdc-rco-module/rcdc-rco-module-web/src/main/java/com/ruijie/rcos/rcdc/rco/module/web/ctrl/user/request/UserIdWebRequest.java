package com.ruijie.rcos.rcdc.rco.module.web.ctrl.user.request;

import com.ruijie.rcos.sk.base.annotation.NotNull;
import com.ruijie.rcos.sk.webmvc.api.request.WebRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.UUID;

/**
 * Description: Function Description
 * Copyright: Copyright (c) 2018
 * Company: Ruijie Co., Ltd.
 * Create Time: 2018/12/28
 *
 * @author Jarman
 */
@ApiModel("获取用户信息请求体")
public class UserIdWebRequest implements WebRequest {

    @ApiModelProperty(value = "用户ID",required = true)
    @NotNull
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
