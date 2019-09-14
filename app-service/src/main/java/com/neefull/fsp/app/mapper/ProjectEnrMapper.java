package com.neefull.fsp.app.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neefull.fsp.app.entity.Project;
import com.neefull.fsp.app.entity.ProjectEnrollment;
import com.neefull.fsp.app.entity.QueryProjectEncroll;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author pei.wang
 */
public interface ProjectEnrMapper extends BaseMapper<ProjectEnrollment> {

    /**
     * @param page    分页对象
     * @param project 项目对象，用于传递查询条件
     * @return Ipage
     */
    IPage<Project> personalHome(Page page, @Param("project") Project project);

    List<QueryProjectEncroll> queryFreelencerEnrollment(@Param("queryProjectEncroll") QueryProjectEncroll queryProjectEncroll);

    List<QueryProjectEncroll> querySignUser(@Param("queryProjectEncroll") QueryProjectEncroll queryProjectEncroll);

    /**
     * 确认用户报名
     * @param projectEnrollment
     * @return
     */
    @Update("UPDATE t_project_enrollment t SET t.current_state=#{projectEnrollment.currentState} WHERE project_id=#{projectEnrollment.projectId} AND user_id=#{projectEnrollment.userId}")
    int confirmSignUser(@Param("projectEnrollment") ProjectEnrollment projectEnrollment);
}
