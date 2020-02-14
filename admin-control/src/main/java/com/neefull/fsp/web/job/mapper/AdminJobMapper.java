package com.neefull.fsp.web.job.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neefull.fsp.web.job.entity.AdminJob;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author pei.wang
 */
@Component
public interface AdminJobMapper extends BaseMapper<AdminJob> {

    List<AdminJob> queryList();

    void updateJob(AdminJob job);
}