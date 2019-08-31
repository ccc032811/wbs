package com.neefull.fsp.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neefull.fsp.app.entity.JobDict;

import java.util.List;

/**
 * @author pei.wang
 */
public interface IJobDictService extends IService<JobDict> {

    /**
     * 通过级别查找职位类型
     *
     * @return 用户
     */
    List<JobDict> findByLevel(JobDict job);

}
