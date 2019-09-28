package com.neefull.fsp.app.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@TableName("t_task_annex")
public class TaskAnnex implements Serializable {

    @TableId(value = "annex_id", type = IdType.AUTO)
    private long annexId;
    @TableField("project_id")
    private long projectId;
    @TableField("user_id")
    private long userId;
    @TableField("annex_desc")
    private String annexDesc;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private java.sql.Timestamp createTime;
    @TableField(exist = false)
    private List<TaskAnnexDetail> taskAnnexDetailList;

    public long getAnnexId() {
        return annexId;
    }

    public void setAnnexId(long annexId) {
        this.annexId = annexId;
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

    public String getAnnexDesc() {
        return annexDesc;
    }

    public void setAnnexDesc(String annexDesc) {
        this.annexDesc = annexDesc;
    }

    public List<TaskAnnexDetail> getTaskAnnexDetailList() {
        return taskAnnexDetailList;
    }

    public void setTaskAnnexDetailList(List<TaskAnnexDetail> taskAnnexDetailList) {
        this.taskAnnexDetailList = taskAnnexDetailList;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
