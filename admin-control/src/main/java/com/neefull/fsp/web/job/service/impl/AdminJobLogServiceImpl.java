package com.neefull.fsp.web.job.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.web.common.entity.FebsConstant;
import com.neefull.fsp.web.common.entity.QueryRequest;
import com.neefull.fsp.web.common.utils.SortUtil;
import com.neefull.fsp.web.job.entity.AdminJobLog;
import com.neefull.fsp.web.job.mapper.AdminJobLogMapper;
import com.neefull.fsp.web.job.service.IAdminJobLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * @author pei.wang
 */
@Slf4j
@Service("JobLogService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AdminJobLogServiceImpl extends ServiceImpl<AdminJobLogMapper, AdminJobLog> implements IAdminJobLogService {

    @Override
    public IPage<AdminJobLog> findJobLogs(QueryRequest request, AdminJobLog jobLog) {
        LambdaQueryWrapper<AdminJobLog> queryWrapper = new LambdaQueryWrapper<>();

        if (StringUtils.isNotBlank(jobLog.getBeanName())) {
            queryWrapper.eq(AdminJobLog::getBeanName, jobLog.getBeanName());
        }
        if (StringUtils.isNotBlank(jobLog.getMethodName())) {
            queryWrapper.eq(AdminJobLog::getMethodName, jobLog.getMethodName());
        }
        if (StringUtils.isNotBlank(jobLog.getStatus())) {
            queryWrapper.eq(AdminJobLog::getStatus, jobLog.getStatus());
        }
        Page<AdminJobLog> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "createTime", FebsConstant.ORDER_DESC, true);
        return this.page(page, queryWrapper);
    }

    @Override
    @Transactional
    public void saveJobLog(AdminJobLog log) {
        this.save(log);
    }

    @Override
    @Transactional
    public void deleteJobLogs(String[] jobLogIds) {
        List<String> list = Arrays.asList(jobLogIds);
        this.baseMapper.deleteBatchIds(list);
    }

}
