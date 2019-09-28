package com.neefull.fsp.app.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.app.entity.Project;
import com.neefull.fsp.app.entity.ProjectSettlement;
import com.neefull.fsp.app.entity.TaskAnnex;
import com.neefull.fsp.app.entity.TaskAnnexDetail;
import com.neefull.fsp.app.mapper.ProjectMapper;
import com.neefull.fsp.app.mapper.ProjectSettleMapper;
import com.neefull.fsp.app.mapper.TaskAnnexDetailMapper;
import com.neefull.fsp.app.mapper.TaskAnnexMapper;
import com.neefull.fsp.app.service.ITaskAnnexService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TaskAnnnexServiceImpl extends ServiceImpl<TaskAnnexDetailMapper, TaskAnnexDetail> implements ITaskAnnexService {

    @Autowired
    TaskAnnexMapper taskAnnexMapper;
    @Autowired
    TaskAnnexDetailMapper taskAnnexDetailMapper;
    @Autowired
    ProjectMapper projectMapper;
    @Autowired
    ProjectSettleMapper projectSettleMapper;

    @Override
    @Transactional
    public int completedTask(TaskAnnex taskAnnex) {
        int result = -1;
        //step1.保存任务主信息
        result = taskAnnexMapper.saveAnnex(taskAnnex);
        //step2.保存任务附件明细信息
        List<TaskAnnexDetail> taskAnnexDetailList = taskAnnex.getTaskAnnexDetailList();
        taskAnnexDetailList.stream().forEach(o -> o.setAnnexId(taskAnnex.getAnnexId()));
        if (!this.saveBatch(taskAnnexDetailList)) {
            result = -1;

        }
        //step3.如果项目状态是新建状态，则使项目进入待结算状态
        Project project = projectMapper.getProjectInfo(taskAnnex.getProjectId());
        if ('0' == project.getCurrentState()) {
            project.setCurrentState('1');
            projectMapper.updateProjectState(project);
        }
        //step4.加入当前人员到用户结算表中,如果结算项目有模板，则直接设置金额
        ProjectSettlement projectSettlemet = new ProjectSettlement();
        projectSettlemet.setSettleAmount(project.getAmount());
        projectSettlemet.setProjectId(project.getId());
        projectSettlemet.setUserId(taskAnnex.getUserId());
        ProjectSettlement projectSettlementModel = projectSettleMapper.querySettleModel(project.getModelId(), taskAnnex.getUserId());
        if (null != projectSettlementModel) {
            projectSettlemet.setRealAmount(projectSettlementModel.getRealAmount());
            projectSettlemet.setIsModel(projectSettlementModel.getIsModel());
        }
        projectSettleMapper.insert(projectSettlemet);

        return result;
    }

    @Override
    public TaskAnnex queryTaskAnnex(TaskAnnex annex) {
        //查询任务附件主表
        LambdaQueryWrapper<TaskAnnex> taskAnnexLambdaQueryWrapper = new LambdaQueryWrapper<>();
        taskAnnexLambdaQueryWrapper.eq(TaskAnnex::getProjectId, annex.getProjectId());
        taskAnnexLambdaQueryWrapper.eq(TaskAnnex::getUserId, annex.getUserId());
        annex = this.taskAnnexMapper.selectOne(taskAnnexLambdaQueryWrapper);
        //查询附件信息
        LambdaQueryWrapper<TaskAnnexDetail> taskAnnexDetailLambdaQueryWrapper = new LambdaQueryWrapper<>();
        taskAnnexDetailLambdaQueryWrapper.eq(TaskAnnexDetail::getAnnexId, annex.getAnnexId());
        List<TaskAnnexDetail> taskAnnexDetails = this.taskAnnexDetailMapper.selectList(taskAnnexDetailLambdaQueryWrapper);
        annex.setTaskAnnexDetailList(taskAnnexDetails);
        return annex;
    }
}
