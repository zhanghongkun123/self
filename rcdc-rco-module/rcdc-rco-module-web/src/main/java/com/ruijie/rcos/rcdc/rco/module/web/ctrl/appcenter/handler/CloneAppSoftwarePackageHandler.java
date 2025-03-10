package com.ruijie.rcos.rcdc.rco.module.web.ctrl.appcenter.handler;

import java.util.UUID;

import com.ruijie.rcos.gss.log.module.def.api.BaseAuditLogAPI;
import com.ruijie.rcos.rcdc.appcenter.module.def.api.CbbAppSoftwarePackageMgmtAPI;
import com.ruijie.rcos.rcdc.rco.module.web.ctrl.appcenter.AppCenterBusinessKey;
import com.ruijie.rcos.rcdc.rco.module.web.ctrl.appcenter.request.CloneAppSoftwarePackageRequest;
import com.ruijie.rcos.sk.base.batch.AbstractSingleTaskHandler;
import com.ruijie.rcos.sk.base.batch.BatchTaskFinishResult;
import com.ruijie.rcos.sk.base.batch.BatchTaskItem;
import com.ruijie.rcos.sk.base.batch.BatchTaskItemResult;
import com.ruijie.rcos.sk.base.batch.BatchTaskItemStatus;
import com.ruijie.rcos.sk.base.batch.BatchTaskStatus;
import com.ruijie.rcos.sk.base.batch.DefaultBatchTaskFinishResult;
import com.ruijie.rcos.sk.base.batch.DefaultBatchTaskItemResult;
import com.ruijie.rcos.sk.base.exception.BusinessException;
import com.ruijie.rcos.sk.base.log.Logger;
import com.ruijie.rcos.sk.base.log.LoggerFactory;

/**
 * Description: 复制应用软件包任务处理器
 * Copyright: Copyright (c) 2023
 * Company: Ruijie Co., Ltd.
 * Create Time: 2023年2月23日
 *
 * @author chenli
 */
public class CloneAppSoftwarePackageHandler extends AbstractSingleTaskHandler {

    protected static final Logger LOGGER = LoggerFactory.getLogger(CloneAppSoftwarePackageHandler.class);

    private BaseAuditLogAPI auditLogAPI;

    private CbbAppSoftwarePackageMgmtAPI cbbAppSoftwarePackageMgmtAPI;

    private CloneAppSoftwarePackageRequest cloneAppSoftwarePackageRequest;

    public CloneAppSoftwarePackageHandler(BatchTaskItem batchTaskItem, BaseAuditLogAPI auditLogAPI,
            CbbAppSoftwarePackageMgmtAPI cbbAppSoftwarePackageMgmtAPI, CloneAppSoftwarePackageRequest cloneAppSoftwarePackageRequest) {
        super(batchTaskItem);
        this.cbbAppSoftwarePackageMgmtAPI = cbbAppSoftwarePackageMgmtAPI;
        this.auditLogAPI = auditLogAPI;
        this.cloneAppSoftwarePackageRequest = cloneAppSoftwarePackageRequest;
    }

    @Override
    public BatchTaskItemResult processItem(BatchTaskItem item) throws BusinessException {
        UUID id = cloneAppSoftwarePackageRequest.getId();
        try {
            cbbAppSoftwarePackageMgmtAPI.copyAppSoftwarePackage(id, cloneAppSoftwarePackageRequest.convertToDTO());

            return DefaultBatchTaskItemResult.builder().batchTaskItemStatus(BatchTaskItemStatus.SUCCESS)
                    .msgKey(AppCenterBusinessKey.RCDC_APP_CENTER_APP_SOFTWARE_PACKAGE_COPY_TASK_SUCCESS)
                    .msgArgs(cloneAppSoftwarePackageRequest.getName()).build();
        } catch (BusinessException e) {
            LOGGER.error("复制应用软件包[{}]出错", id, e);
            String errorMsg = e.getI18nMessage();
            auditLogAPI.recordLog(AppCenterBusinessKey.RCDC_APP_CENTER_APP_SOFTWARE_PACKAGE_COPY_FAIL_LOG, e,
                    cloneAppSoftwarePackageRequest.getName(), e.getI18nMessage());
            throw new BusinessException(AppCenterBusinessKey.RCDC_APP_CENTER_APP_SOFTWARE_PACKAGE_COPY_FAIL_LOG, e,
                    cloneAppSoftwarePackageRequest.getName(), errorMsg);
        }
    }

    @Override
    public BatchTaskFinishResult onFinish(int successCount, int failCount) {
        if (failCount == 0) {
            return DefaultBatchTaskFinishResult.builder().batchTaskStatus(BatchTaskStatus.SUCCESS)
                    .msgKey(AppCenterBusinessKey.RCDC_APP_CENTER_APP_SOFTWARE_PACKAGE_COPY_TASK_SUCCESS)
                    .msgArgs(new String[] {cloneAppSoftwarePackageRequest.getName()}).build();
        } else {
            return DefaultBatchTaskFinishResult.builder().batchTaskStatus(BatchTaskStatus.FAILURE)
                    .msgKey(AppCenterBusinessKey.RCDC_APP_CENTER_APP_SOFTWARE_PACKAGE_COPY_TASK_FAIL)
                    .msgArgs(new String[] {cloneAppSoftwarePackageRequest.getName()}).build();
        }
    }
}
