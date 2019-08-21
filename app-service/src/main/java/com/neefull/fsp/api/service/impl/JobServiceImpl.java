package com.neefull.fsp.api.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.api.entity.Job;
import com.neefull.fsp.api.mapper.JobMapper;
import com.neefull.fsp.api.service.IJobService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author pei.wang
 */
@DS("slave")
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class JobServiceImpl extends ServiceImpl<JobMapper, Job> implements IJobService {


    @Override
    public List<Job> findByLevel(Job job) {
        LambdaQueryWrapper<Job> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Job::getPid,job.getPid());
        lambdaQueryWrapper.eq(Job::getStatus,0);
        lambdaQueryWrapper.orderByDesc(Job::getRecommend,Job::getPopular);
        return this.baseMapper.selectList(lambdaQueryWrapper);
    }
}
