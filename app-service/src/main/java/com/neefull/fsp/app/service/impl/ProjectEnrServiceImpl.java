package com.neefull.fsp.app.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.app.entity.ProjectEnrollment;
import com.neefull.fsp.app.mapper.ProjectEnrMapper;
import com.neefull.fsp.app.service.IProjectEnrService;
import com.neefull.fsp.app.service.IProjectService;
import com.neefull.fsp.common.entity.QueryRequest;
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
public class ProjectEnrServiceImpl extends ServiceImpl<ProjectEnrMapper, ProjectEnrollment> implements IProjectEnrService {


    @Autowired
    IProjectService projectService;

    @Override
    @Transactional
    public int saveProjectEnrollment(ProjectEnrollment projectEnrollment) {
        if (this.baseMapper.insert(projectEnrollment) > 0) {
            //报名人数+1
            //查看当前报名人数
            if (projectService.updateProjectSignNum(projectEnrollment.getProjectId()) > 0) {
                return 1;
            }
        }
        return 0;
    }

    @Override
    @Transactional
    public int updateProjectEnrollment(ProjectEnrollment projectEnrollment) {
        return 0;
    }

    @Override
    public List<ProjectEnrollment> getEnrollmentsByProjectId(long projectId) {
        LambdaQueryWrapper<ProjectEnrollment> lambdaQueryWrapper = new LambdaQueryWrapper<ProjectEnrollment>();
        lambdaQueryWrapper.eq(ProjectEnrollment::getProjectId, projectId);
        lambdaQueryWrapper.orderByDesc(ProjectEnrollment::getCreateTime, ProjectEnrollment::getCurrentState);
        return this.baseMapper.selectList(lambdaQueryWrapper);
    }

    @Override
    public IPage<ProjectEnrollment> getProjectEnroByUser(ProjectEnrollment projectEnrollment, QueryRequest request) {
        return null;
    }
}
