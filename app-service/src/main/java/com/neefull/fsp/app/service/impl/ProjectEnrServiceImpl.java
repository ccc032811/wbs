package com.neefull.fsp.app.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.app.entity.Project;
import com.neefull.fsp.app.entity.ProjectEnrollment;
import com.neefull.fsp.app.entity.QueryProjectEncroll;
import com.neefull.fsp.app.entity.User;
import com.neefull.fsp.app.mapper.ProjectEnrMapper;
import com.neefull.fsp.app.service.IProjectEnrService;
import com.neefull.fsp.app.service.IProjectService;
import com.neefull.fsp.app.service.IUserService;
import com.neefull.fsp.common.entity.QueryRequest;
import com.neefull.fsp.common.util.SortUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author pei.wang
 */
@DS("master")
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ProjectEnrServiceImpl extends ServiceImpl<ProjectEnrMapper, ProjectEnrollment> implements IProjectEnrService {


    @Autowired
    IProjectService projectService;

    @Autowired
    IUserService userService;
    @Autowired
    ProjectEnrMapper projectEnrMapper;

    @Override
    @Transactional
    public int saveProjectEnrollment(ProjectEnrollment projectEnrollment) {
        //判断当前用户是否通过实名认证
        User currentUser = new User();
        currentUser.setUserId(projectEnrollment.getUserId());
        currentUser = userService.findUserById(currentUser);
        if (null == currentUser || currentUser.getAuthStatus() < 2) {
            //用户实名未通过，无法报名
            return -1;
        }
        if (this.baseMapper.insert(projectEnrollment) != -1) {
            //报名人数+1
            //查看当前报名人数
            if (projectService.updateProjectSignNum(projectEnrollment.getProjectId(), 1) != -1) {
                return 1;
            }
        }
        return 0;
    }

    @Override
    @Transactional
    public int updateProjectEnrollment(ProjectEnrollment projectEnrollment) {
        return this.projectEnrMapper.confirmSignUser(projectEnrollment);
    }

    @Override
    public IPage<ProjectEnrollment> getEnrollmentsByProjectId(long projectId) {
        return null;
    }

    @Override
    public IPage<QueryProjectEncroll> queryFreelencerEnrollment(QueryProjectEncroll projectEnrollment, QueryRequest request) {
        Page<Project> page = new Page<Project>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "sign_time", "desc", false);
        //LambdaQueryWrapper<ProjectEnrollment> lambdaQueryWrapper = new LambdaQueryWrapper<ProjectEnrollment>();
        /// lambdaQueryWrapper.eq(ProjectEnrollment::getProjectId, projectId);
        //lambdaQueryWrapper.orderByDesc(ProjectEnrollment::getCreateTime, ProjectEnrollment::getCurrentState);
        return this.baseMapper.queryFreelencerEnrollment(page, projectEnrollment);
    }


    @Override
    @Transactional
    public int cancelSignup(ProjectEnrollment projectEnrollment) {
        //先删除报名用户信息
        projectEnrollment.setCurrentState(-2);
        this.projectEnrMapper.cancelSignup(projectEnrollment);
        //报名人数+1
        //查看当前报名人数
        if (projectService.updateProjectSignNum(projectEnrollment.getProjectId(), -1) != -1) {
            return 1;
        }
        return 0;
    }
}