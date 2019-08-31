package com.neefull.fsp.web.job.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neefull.fsp.web.job.entity.Job;

import java.util.List;

/**
 * @author pei.wang
 */
public interface JobMapper extends BaseMapper<Job> {

    List<Job> queryList();
}