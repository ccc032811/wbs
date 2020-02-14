package com.neefull.fsp.web.job.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neefull.fsp.web.common.entity.QueryRequest;
import com.neefull.fsp.web.job.entity.AdminJob;

/**
 * @author pei.wang
 */
public interface IAdminJobService extends IService<AdminJob> {

    AdminJob findJob(Long jobId);

    IPage<AdminJob> findJobs(QueryRequest request, AdminJob job);

    void createJob(AdminJob job);

    void updateJob(AdminJob job);

    void deleteJobs(String[] jobIds);

    int updateBatch(String jobIds, String status);

    void run(String jobIds);

    void pause(String jobIds);

    void resume(String jobIds);

}
