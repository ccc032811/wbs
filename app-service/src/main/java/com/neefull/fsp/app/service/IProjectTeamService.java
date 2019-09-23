package com.neefull.fsp.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neefull.fsp.app.entity.ProjectTeam;

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
