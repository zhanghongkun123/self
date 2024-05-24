package com.ruijie.rcos.rcdc.rco.module.def.api.request;

import com.ruijie.rcos.sk.base.annotation.NotBlank;
import com.ruijie.rcos.sk.base.annotation.NotNull;
import com.ruijie.rcos.sk.modulekit.api.comm.Request;
import com.ruijie.rcos.sk.webmvc.api.request.WebRequest;

/**
 * Description: Function Description
 * Copyright: Copyright (c) 2020
 * Company: Ruijie Co., Ltd.
 * Create Time: 2020年02月17日
 *
 * @author xiejian
 */
public class UpdateParameterRequest implements WebRequest {

    @NotBlank
    private String key;

    @NotNull
    private String value;

    public UpdateParameterRequest(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
