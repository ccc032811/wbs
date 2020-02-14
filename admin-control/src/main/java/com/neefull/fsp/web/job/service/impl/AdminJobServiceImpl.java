package com.neefull.fsp.web.job.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.web.common.entity.FebsConstant;
import com.neefull.fsp.web.common.entity.QueryRequest;
import com.neefull.fsp.web.common.utils.SortUtil;
import com.neefull.fsp.web.job.entity.AdminJob;
import com.neefull.fsp.web.job.mapper.AdminJobMapper;
import com.neefull.fsp.web.job.service.IAdminJobService;
import com.neefull.fsp.web.job.util.ScheduleUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author pei.wang
 */
@Slf4j
@Service("JobService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AdminJobServiceImpl extends ServiceImpl<AdminJobMapper, AdminJob> implements IAdminJobService {

    @Autowired
    private Scheduler scheduler;


    /**
     * 项目启动时，初始化定时器
     */
    @PostConstruct
    public void init() {
        List<AdminJob> scheduleJobList = this.baseMapper.queryList();
        // 如果不存在，则创建
        scheduleJobList.forEach(scheduleJob -> {
            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobId());
            if (cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            } else {
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
        });
    }

    @Override
    public AdminJob findJob(Long jobId) {
        return this.getById(jobId);
    }

    @Override
    public IPage<AdminJob> findJobs(QueryRequest request, AdminJob job) {
        LambdaQueryWrapper<AdminJob> queryWrapper = new LambdaQueryWrapper<>();

        if (StringUtils.isNotBlank(job.getBeanName())) {
            queryWrapper.eq(AdminJob::getBeanName, job.getBeanName());
        }
        if (StringUtils.isNotBlank(job.getMethodName())) {
            queryWrapper.eq(AdminJob::getMethodName, job.getMethodName());
        }
        if (StringUtils.isNotBlank(job.getParams())) {
            queryWrapper.like(AdminJob::getParams, job.getParams());
        }
        if (StringUtils.isNotBlank(job.getRemark())) {
            queryWrapper.like(AdminJob::getRemark, job.getRemark());
        }
        if (StringUtils.isNotBlank(job.getStatus())) {
            queryWrapper.eq(AdminJob::getStatus, job.getStatus());
        }

        if (StringUtils.isNotBlank(job.getCreateTimeFrom()) && StringUtils.isNotBlank(job.getCreateTimeTo())) {
            queryWrapper
                    .ge(AdminJob::getCreateTime, job.getCreateTimeFrom())
                    .le(AdminJob::getCreateTime, job.getCreateTimeTo());
        }
        Page<AdminJob> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "createTime", FebsConstant.ORDER_DESC, true);
        return this.page(page, queryWrapper);
    }



    @Override
    @Transactional
    public void createJob(AdminJob job) {
        job.setCreateTime(new Date());
        job.setStatus(AdminJob.ScheduleStatus.PAUSE.getValue());
        job.setBeanName("monotor");
        job.setMethodName("monotor");
        job.setCronExpression("0/9 * * * * ?");
        job.setIsalive(AdminJob.ScheduleStatus.NORMAL.getValue());
        this.save(job);
        ScheduleUtils.createScheduleJob(scheduler, job);
        ScheduleUtils.resumeJob(scheduler, Long.valueOf(job.getJobId()));
    }



    @Override
    @Transactional
    public void updateJob(AdminJob job) {
        ScheduleUtils.updateScheduleJob(scheduler, job);
        this.baseMapper.updateById(job);
    }

    @Override
    @Transactional
    public void deleteJobs(String[] jobIds) {
        List<String> list = Arrays.asList(jobIds);
        list.forEach(jobId -> ScheduleUtils.deleteScheduleJob(scheduler, Long.valueOf(jobId)));
        this.baseMapper.deleteBatchIds(list);
    }

    @Override
    @Transactional
    public int updateBatch(String jobIds, String status) {
        List<String> list = Arrays.asList(jobIds.split(StringPool.COMMA));
        AdminJob job = new AdminJob();
        job.setStatus(status);
        return this.baseMapper.update(job, new LambdaQueryWrapper<AdminJob>().in(AdminJob::getJobId, list));
    }

    @Override
    @Transactional
    public void run(String jobIds) {
        String[] list = jobIds.split(StringPool.COMMA);
        Arrays.stream(list).forEach(jobId -> ScheduleUtils.run(scheduler, this.findJob(Long.valueOf(jobId))));
    }

    @Override
    @Transactional
    public void pause(String jobIds) {
        String[] list = jobIds.split(StringPool.COMMA);
        Arrays.stream(list).forEach(jobId -> ScheduleUtils.pauseJob(scheduler, Long.valueOf(jobId)));
        this.updateBatch(jobIds, AdminJob.ScheduleStatus.PAUSE.getValue());
    }

    @Override
    @Transactional
    public void resume(String jobIds) {
        String[] list = jobIds.split(StringPool.COMMA);
        Arrays.stream(list).forEach(jobId -> ScheduleUtils.resumeJob(scheduler, Long.valueOf(jobId)));
        this.updateBatch(jobIds, AdminJob.ScheduleStatus.NORMAL.getValue());
    }
}
