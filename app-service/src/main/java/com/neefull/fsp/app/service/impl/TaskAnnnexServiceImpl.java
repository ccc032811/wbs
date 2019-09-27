package com.neefull.fsp.app.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.app.entity.TaskAnnex;
import com.neefull.fsp.app.mapper.TaskAnnexMapper;
import com.neefull.fsp.app.service.ITaskAnnexService;
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
public class TaskAnnnexServiceImpl extends ServiceImpl<TaskAnnexMapper, TaskAnnex> implements ITaskAnnexService {

    @Override
    @Transactional
    public boolean saveTaskAnnexBatch(List<TaskAnnex> annexs) {
        return saveBatch(annexs);
    }

    @Override
    public List<TaskAnnex> queryTaskAnnex(TaskAnnex annex) {
        LambdaQueryWrapper<TaskAnnex> taskAnnexLambdaQueryWrapper = new LambdaQueryWrapper<TaskAnnex>();
        taskAnnexLambdaQueryWrapper.eq(TaskAnnex::getUserId, annex.getUserId());
        taskAnnexLambdaQueryWrapper.eq(TaskAnnex::getProjectId, annex.getProjectId());
        return this.baseMapper.selectList(taskAnnexLambdaQueryWrapper);
    }
}
