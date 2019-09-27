package com.neefull.fsp.web.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neefull.fsp.web.common.entity.QueryRequest;
import com.neefull.fsp.web.system.entity.Project;
import com.neefull.fsp.web.system.entity.ProjectSettlement;

/**
 * @program: fsp
 * @author: xi.chen
 * @create: 2019-09-23 14:58
 **/
public interface IProjectService extends IService<Project> {

    /**
     * 项目列表页面检索
     * @param project 项目对象，用于传递查询条件
     * @param request 请求
     * @return page元素
     */
    IPage<Project> findProjectBySearch(Project project, QueryRequest request);

    /**
     * 查询某项目的结算人员列表
     * @param settlement
     * @param request
     * @return
     */
    IPage<ProjectSettlement> findProjectSettleByProjectId(ProjectSettlement settlement, QueryRequest request);

    /**
     * 根据项目Id查询项目信息
     * @param projectId 项目id
     * @return 项目信息
     */
    Project getProjectById(String projectId);
}