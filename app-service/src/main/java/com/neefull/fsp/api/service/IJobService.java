package com.neefull.fsp.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neefull.fsp.api.entity.Job;

import java.util.List;

/**
 * @author pei.wang
 */
public interface IJobService extends IService<Job> {

    /**
     * 通过级别查找职位类型
     *
     * @return 用户
     */
    List<Job> findByLevel(Job job);

}
