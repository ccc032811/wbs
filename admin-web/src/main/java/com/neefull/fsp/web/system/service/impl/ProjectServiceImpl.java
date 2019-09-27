package com.neefull.fsp.web.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.web.common.entity.FebsConstant;
import com.neefull.fsp.web.common.entity.QueryRequest;
import com.neefull.fsp.web.common.utils.SortUtil;
import com.neefull.fsp.web.system.entity.Project;
import com.neefull.fsp.web.system.entity.ProjectSettlement;
import com.neefull.fsp.web.system.mapper.ProjectMapper;
import com.neefull.fsp.web.system.service.IProjectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: fsp
 * @author: xi.chen
 * @create: 2019-09-23 14:59
 **/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements IProjectService {

    /**
     * 项目列表页面检索
     * @param project 项目对象，用于传递查询条件
     * @param request 请求
     * @return page元素
     */
    @Override
    public IPage<Project> findProjectBySearch(Project project, QueryRequest request) {
        Page<Project> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "id", FebsConstant.ORDER_ASC, false);
        return this.baseMapper.findProjectBySearch(page, project);
    }

    /**
     * 查询某项目的结算人员列表
     * @param settlement
     * @param request
     * @return
     */
    @Override
    public IPage<ProjectSettlement> findProjectSettleByProjectId(ProjectSettlement settlement, QueryRequest request) {
        Page<ProjectSettlement> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "settle_time", FebsConstant.ORDER_ASC, false);
        return this.baseMapper.findProjectSettleByProjectId(page, settlement);
    }

    /**
     * 根据项目Id查询项目信息
     * @param projectId 项目id
     * @return 项目信息
     */
    @Override
    public Project getProjectById(String projectId) {
        return this.baseMapper.getProjectById(projectId);
    }
}