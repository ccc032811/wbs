package com.neefull.fsp.app.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.app.entity.Project;
import com.neefull.fsp.app.mapper.ProjectMapper;
import com.neefull.fsp.app.service.IProjectService;
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
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements IProjectService {

    @Override
    @Transactional
    public int saveProject(Project project) {
        return this.baseMapper.insert(project);
    }

    @Override
    @Transactional
    public int updateProject(Project project) {
        LambdaUpdateWrapper<Project> lambdaUpdateWrapper = new LambdaUpdateWrapper<Project>();
        lambdaUpdateWrapper.eq(Project::getId, project.getId());
        return this.baseMapper.update(project, lambdaUpdateWrapper);
    }

    @Override
    public Project queryProject(Project project) {
        LambdaQueryWrapper<Project> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Project::getId, project.getId());
        return this.baseMapper.selectOne(lambdaQueryWrapper);
    }

    @Override
    public List<Project> getProjectsByUser(long userId) {
        LambdaQueryWrapper<Project> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Project::getUserId, userId);
        lambdaQueryWrapper.orderByDesc(Project::getCreateTime);
        /*queryWrapper.select("id","user_id","service_type","service_content","settle_time"
                ,"salary_type","amount","title","des","content","place","req_sex","req_identity","req_edu",
                )*/
        return this.baseMapper.selectList(lambdaQueryWrapper);
    }

    @Override
    public List<Project> queryAllProjects(Project project) {
        LambdaQueryWrapper<Project> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        return this.baseMapper.selectList(lambdaQueryWrapper);
    }
}
