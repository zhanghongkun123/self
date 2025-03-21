package com.ruijie.rcos.rcdc.rco.module.impl.datasync.strategy.user.impl;

import org.springframework.stereotype.Service;

import com.ruijie.rcos.gss.sdk.iac.module.def.enums.IacUserTypeEnum;
import com.ruijie.rcos.sk.base.log.Logger;
import com.ruijie.rcos.sk.base.log.LoggerFactory;

/**
 * Description:
 * Copyright: Copyright (c)
 * Company: Ruijie Co., Ltd.
 * Create Time: 2023/09/20 20:08
 *
 * @author coderLee23
 */
@Service
public class ThirdPartyUserSyncDataStrategy extends AbstractNormalAndThirdPartyUserSyncDataStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThirdPartyUserSyncDataStrategy.class);

    @Override
    public IacUserTypeEnum getCbbUserType() {
        return IacUserTypeEnum.THIRD_PARTY;
    }

}
