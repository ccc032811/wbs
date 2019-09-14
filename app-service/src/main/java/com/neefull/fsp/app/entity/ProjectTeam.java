package com.neefull.fsp.app.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.sql.Timestamp;

@TableName("t_project_team")
public class ProjectTeam {

    @TableId(type = IdType.AUTO)
    private long id;
    @TableField("user_id")
    private long userId;
    @TableField("project_id")
    private long projectId;
    @TableField("team_name")
    private String teamName;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Timestamp createTime;
    @TableField(value = "modify_time", fill = FieldFill.UPDATE)
    private Timestamp modifyTime;
    @TableField("remark")
    private String remark;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
