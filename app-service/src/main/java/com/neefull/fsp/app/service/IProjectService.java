package com.neefull.fsp.app.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neefull.fsp.app.entity.Project;
import com.neefull.fsp.common.entity.QueryRequest;

/**
 * @author pei.wang
 */
public interface IProjectService extends IService<Project> {

    /**
     * 保存项目发布
     *
     * @param project
     */
    int saveProject(Project project);

    /**
     * 修改项目信息
     *
     * @param project
     */
    int updateProject(Project project);

    /**
     * 查询项目信息
     *
     * @param project
     */
    Project queryProject(Project project);

    /**
     * 企业首页，根据项目状态和企业用户，查询企业相关项目信息
     *
     * @return
     */
    IPage<Project> corpHome(Project project, QueryRequest request);

    /**
     * 个人首页，分页展示
     *
     * @return
     */
    IPage<Project> personalHome(Project project, QueryRequest request);

    int updateProjectSignNum(long projectId, int num);

    Project queryProjectDetail(Project project);

    int openCloseProject(Project project);
}
