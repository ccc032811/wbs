package com.neefull.fsp.app.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neefull.fsp.app.entity.Project;
import com.neefull.fsp.app.entity.ProjectEnrollment;
import com.neefull.fsp.common.entity.QueryRequest;

import java.util.List;

/**
 * @author pei.wang
 */
public interface IProjectEnrService extends IService<ProjectEnrollment> {

    /**
     * 保存项目报名
     *
     * @param projectEnrollment
     */
    int saveProjectEnrollment(ProjectEnrollment projectEnrollment);

    /**
     * 更新项目报名信息，例如取消报名，更新相关状态等，用户和项目ID
     *
     * @param projectEnrollment
     */
    int updateProjectEnrollment(ProjectEnrollment projectEnrollment);

    /**
     * 根据项目ID查询报名信息
     *
     * @return
     */
    List<ProjectEnrollment> getEnrollmentsByProjectId(long projectId);

    /**
     * 根据用户和项目ID,查询相关报名信息。可分页查询
     *
     * @return
     */
    IPage<ProjectEnrollment> getProjectEnroByUser(ProjectEnrollment projectEnrollment, QueryRequest request);

}
