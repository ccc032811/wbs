package com.neefull.fsp.app.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neefull.fsp.app.entity.ProjectTeam;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author pei.wang
 */
public interface ProjectTeamMapper extends BaseMapper<ProjectTeam> {

    /**
     * 改变项目的状态 0初始 1 生效 -1 已删除,已删除的不在进行计算
     *
     * @param projectId
     */
    @Update("update t_project_team set team_state=#{teamState} where project_id=#{projectId} and team_state =0")
    boolean changeProjectTeamState(@Param("projectId") long projectId, @Param("teamState") int teamState);

    /**
     * 根据用户类型，查询用户的所有团队信息
     *
     * @return
     */
    List<ProjectTeam> freelencerTeams(@Param("userId") long userId);

    /**
     * 根据用户类型，查询企业相关的所有团队信息
     *
     * @return
     */
    List<ProjectTeam> corpTeams(@Param("userId") long userId);

}
