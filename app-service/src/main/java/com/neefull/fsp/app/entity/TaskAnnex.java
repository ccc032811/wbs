package com.neefull.fsp.app.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;

@TableName("t_task_annex")
public class TaskAnnex implements Serializable {

    @TableId(type = IdType.AUTO)
    private long id;
    @TableField("user_id")
    private long userId;
    @TableField("project_id")
    private long projectId;
    @TableField("annex_address")
    private String annexAddress;
    @TableField("annex_desc")
    private String annexDesc;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private java.sql.Timestamp createTime;
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


    public String getAnnexAddress() {
        return annexAddress;
    }

    public void setAnnexAddress(String annexAddress) {
        this.annexAddress = annexAddress;
    }


    public String getAnnexDesc() {
        return annexDesc;
    }

    public void setAnnexDesc(String annexDesc) {
        this.annexDesc = annexDesc;
    }


    public java.sql.Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(java.sql.Timestamp createTime) {
        this.createTime = createTime;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
