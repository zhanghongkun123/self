package com.ruijie.rcos.rcdc.rco.module.web.ctrl.sysmanage.request.log4jconfig;

import java.util.UUID;

import com.ruijie.rcos.sk.base.annotation.NotNull;
import com.ruijie.rcos.sk.webmvc.api.request.WebRequest;

/**
 * Description: Function Description
 * Copyright: Copyright (c) 2019
 * Company: Ruijie Co., Ltd.
 * Create Time: 2019年02月20日
 *
 * @author GuoZhouYue
 */
public class BaseDetailLog4jConfigWebRequest implements WebRequest {

    @NotNull
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
