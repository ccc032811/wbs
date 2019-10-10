package com.neefull.fsp.app.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.app.entity.Project;
import com.neefull.fsp.app.entity.ProjectSettlement;
import com.neefull.fsp.app.entity.ProjectSettlementWapper;
import com.neefull.fsp.app.entity.SettledRecord;
import com.neefull.fsp.app.mapper.ProjectEnrMapper;
import com.neefull.fsp.app.mapper.ProjectMapper;
import com.neefull.fsp.app.mapper.ProjectSettleMapper;
import com.neefull.fsp.app.service.IProjectService;
import com.neefull.fsp.app.service.IProjectSettleService;
import com.neefull.fsp.common.util.SerialNumberUtil;
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
public class ProjectSettleServiceImpl extends ServiceImpl<ProjectSettleMapper, ProjectSettlement> implements IProjectSettleService {

    @Autowired
    ProjectSettleMapper projectSettleMapper;
    @Autowired
    ProjectMapper projectMapper;
    @Autowired
    IProjectService projectService;
    @Autowired
    ProjectEnrMapper projectEnrMapper;

    @Override
    @Transactional
    public boolean submitSettlement(ProjectSettlementWapper projectSettlementWapper) {
        //Step1.更新待结算列表里面的结算信息，结算状态为支付中
        projectSettlementWapper.getProjectSettlementList().stream().forEach(o -> {
            o.setProjectId(projectSettlementWapper.getProjectId());
            o.setState(1);
            o.setIsModel(projectSettlementWapper.getIsModel());
        });
        updateBatchById(projectSettlementWapper.getProjectSettlementList());
        //step2.更新项目状态，为支付中
        Project projectParam = new Project();
        projectParam.setCurrentState('2');
        projectParam.setId(projectSettlementWapper.getProjectId());
        projectMapper.updateProjectState(projectParam);
        //step3.生成项目结算概要
        projectSettleMapper.saveSettleSummary(projectSettlementWapper);
        //step4.按照当前项目信息，生成新的项目
        Project newProject = projectMapper.getProjectInfo(projectSettlementWapper.getProjectId());
        newProject.setCurrentState('0');
        //查询最大项目编号
        long maxId = this.projectMapper.getMaxId();
        //设置项目编号
        String pno = SerialNumberUtil.getNewNo("PNO.", String.valueOf(maxId));
        newProject.setPno(pno);
        newProject.setModelId(projectSettlementWapper.getProjectId());
        projectService.saveProject(newProject);
        //step5.将报名列表里面的审核通过的人员，关联项目id改为新项目
        if (projectEnrMapper.updateProjectId(projectParam.getId(), newProject.getId())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<ProjectSettlement> querySettleUsers(ProjectSettlementWapper projectSettlementWapper) {
        return projectSettleMapper.querySettleUsers(projectSettlementWapper);
    }

    @Override
    public List<SettledRecord> queryCorpSettlement(SettledRecord settledRecord) {
        return projectSettleMapper.queryCorpSettlement(settledRecord);
    }

    @Override
    public List<SettledRecord> queryUserSettlement(SettledRecord settledRecord) {
        return projectSettleMapper.queryUserSettlement(settledRecord);
    }
}
