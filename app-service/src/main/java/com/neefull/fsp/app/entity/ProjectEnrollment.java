package com.neefull.fsp.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@TableName("t_project_enrollment")
public class ProjectEnrollment {

    @TableId(type = IdType.AUTO)
    private long id;
    @NotNull
    @TableField("project_id")
    private long projectId;
    @TableField("user_id")
    private long userId;
    @TableField("create_time")
    private java.sql.Timestamp createTime;
    @TableField("pass_time")
    private java.sql.Timestamp passTime;
    @TableField("pass_user")
    private java.sql.Timestamp passUser;
    @TableField("current_state")
    private int currentState;
    @TableField("modify_time")
    private java.sql.Timestamp modifyTime;
    @TableField("modify_user")
    private long modifyUser;
    @TableField("remark")
    private String remark;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public java.sql.Timestamp getPassTime() {
        return passTime;
    }

    public void setPassTime(java.sql.Timestamp passTime) {
        this.passTime = passTime;
    }


    public java.sql.Timestamp getPassUser() {
        return passUser;
    }

    public void setPassUser(java.sql.Timestamp passUser) {
        this.passUser = passUser;
    }


    public int getCurrentState() {
        return currentState;
    }

    public void setCurrentState(int currentState) {
        this.currentState = currentState;
    }


    public java.sql.Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(java.sql.Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }


    public long getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(long modifyUser) {
        this.modifyUser = modifyUser;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
