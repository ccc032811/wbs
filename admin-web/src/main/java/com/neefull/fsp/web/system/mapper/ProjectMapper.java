package com.neefull.fsp.web.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neefull.fsp.web.system.entity.Project;
import org.apache.ibatis.annotations.Param;

/**
 * @program: fsp
 * @author: xi.chen
 * @create: 2019-09-23 14:57
 **/
public interface ProjectMapper extends BaseMapper<Project> {

    /**
     * 项目列表页面检索
     * @param page page元素
     * @param project 项目对象，用于传递查询条件
     * @return 项目列表
     */
    IPage<Project> findProjectBySearch(Page page, @Param("project") Project project);

    /**
     * 根据项目Id查询项目信息
     * @param projectId 项目id
     * @return 项目信息
     */
    Project getProjectById(String projectId);
}