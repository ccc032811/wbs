package com.neefull.fsp.app.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neefull.fsp.app.entity.Project;
import org.apache.ibatis.annotations.Param;

/**
 * @author pei.wang
 */
public interface ProjectMapper extends BaseMapper<Project> {

    /**
     * 查找用户详细信息
     *
     * @param page 分页对象
     * @param project 项目对象，用于传递查询条件
     * @return Ipage
     */
    IPage<Project> personalHome(Page page, @Param("project") Project project);

}
