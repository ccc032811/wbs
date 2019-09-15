package com.neefull.fsp.app.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.app.entity.Project;
import com.neefull.fsp.app.entity.ProjectSettlement;
import com.neefull.fsp.app.mapper.ProjectMapper;
import com.neefull.fsp.app.mapper.ProjectSettleMapper;
import com.neefull.fsp.app.service.IProjectService;
import com.neefull.fsp.app.service.IProjectSettleService;
import com.neefull.fsp.common.entity.QueryRequest;
import com.neefull.fsp.common.util.SortUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author pei.wang
 */
@DS("master")
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ProjectSettleServiceImpl extends ServiceImpl<ProjectSettleMapper, ProjectSettlement> implements IProjectSettleService {

    @Override
    public List<ProjectSettlement> querySettleUsers(ProjectSettlement projectSettlement) {
        return this.baseMapper.querySettleUsers(projectSettlement);
    }
}
