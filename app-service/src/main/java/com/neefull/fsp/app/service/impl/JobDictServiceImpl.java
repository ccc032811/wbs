package com.neefull.fsp.app.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.app.entity.JobDict;
import com.neefull.fsp.app.mapper.ServiceMapper;
import com.neefull.fsp.app.service.IJobDictService;
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
public class JobDictServiceImpl extends ServiceImpl<ServiceMapper, JobDict> implements IJobDictService {
    @Override
    public List<JobDict> findByLevel(JobDict job) {
        LambdaQueryWrapper<JobDict> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(JobDict::getPid,job.getPid());
        lambdaQueryWrapper.eq(JobDict::getStatus,0);
        lambdaQueryWrapper.orderByDesc(JobDict::getRecommend, JobDict::getPopular);
        return this.baseMapper.selectList(lambdaQueryWrapper);
    }
}
