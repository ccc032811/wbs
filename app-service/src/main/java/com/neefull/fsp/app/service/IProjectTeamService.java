package com.neefull.fsp.app.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neefull.fsp.app.entity.Project;
import com.neefull.fsp.app.entity.ProjectTeam;
import com.neefull.fsp.common.entity.QueryRequest;

/**
 * @author pei.wang
 */
public interface IProjectTeamService extends IService<ProjectTeam> {

    /**
     * 保存项目团队
     *
     * @param projectTeam
     */
    int saveProjectTeam(ProjectTeam projectTeam);

}
