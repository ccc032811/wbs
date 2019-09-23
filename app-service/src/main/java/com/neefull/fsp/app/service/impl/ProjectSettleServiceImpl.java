package com.neefull.fsp.app.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.app.entity.ProjectSettlement;
import com.neefull.fsp.app.mapper.ProjectSettleMapper;
import com.neefull.fsp.app.service.IProjectSettleService;
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

    @Override
    public List<ProjectSettlement> querySettleUsers(ProjectSettlement projectSettlement) {
        return this.baseMapper.querySettleUsers(projectSettlement);
    }
}
