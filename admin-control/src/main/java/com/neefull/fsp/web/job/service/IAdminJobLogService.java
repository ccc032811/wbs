package com.neefull.fsp.web.job.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neefull.fsp.web.common.entity.QueryRequest;
import com.neefull.fsp.web.job.entity.AdminJobLog;

/**
 * @author pei.wang
 */
public interface IAdminJobLogService extends IService<AdminJobLog> {

    IPage<AdminJobLog> findJobLogs(QueryRequest request, AdminJobLog jobLog);

    void saveJobLog(AdminJobLog log);

    void deleteJobLogs(String[] jobLogIds);
}
