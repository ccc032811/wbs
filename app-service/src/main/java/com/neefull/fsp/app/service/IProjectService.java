package com.neefull.fsp.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neefull.fsp.app.entity.Project;

import java.util.List;

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
     * 根据条件查询所有企业认证信息
     *
     * @return
     */
    List<Project> getProjectsByUser(long userId);

    /**
     * 根据条件查询所有企业认证信息
     *
     * @return
     */
    List<Project> queryAllProjects(Project project);

}
