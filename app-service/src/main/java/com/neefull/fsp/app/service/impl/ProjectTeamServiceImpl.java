package com.neefull.fsp.app.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.app.entity.ProjectTeam;
import com.neefull.fsp.app.mapper.ProjectTeamMapper;
import com.neefull.fsp.app.service.IProjectTeamService;
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
public class ProjectTeamServiceImpl extends ServiceImpl<ProjectTeamMapper, ProjectTeam> implements IProjectTeamService {

    @Autowired
    ProjectTeamMapper projectTeamMapper;

    @Override
    public boolean saveProjectTeam(ProjectTeam projectTeam) {
        return save(projectTeam);
    }
}
