package com.ruijie.rcos.rcdc.rco.module.web.ctrl.clouddesktop.request.desknetwork;

import java.util.UUID;
import com.ruijie.rcos.sk.base.annotation.NotNull;
import com.ruijie.rcos.sk.webmvc.api.request.WebRequest;
import io.swagger.annotations.ApiModelProperty;

/**
 * Description: 编辑网络策略获取回填信息的Web请求
 * Copyright: Copyright (c) 2018
 * Company: Ruijie Co., Ltd.
 * Create Time: 2019年3月18日
 * 
 * @author huangxiaodan
 */
public class DetailDeskNetworkWebRequest implements WebRequest {

    @ApiModelProperty(value = "网络策略id", required = true)
    @NotNull
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
    
    
}
