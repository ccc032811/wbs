package com.neefull.fsp.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.sql.Timestamp;

@TableName("t_project_settlement")
public class ProjectSettlement {

    @TableId(value = "id", type = IdType.AUTO)
    private long id;
    @TableField("project_id")
    private long projectId;
    @TableField("user_id")
    private long userId;
    @TableField("settle_amount")
    private String settleAmount;
    @TableField("real_amount")
    private String realAmount;
    @TableField("state")
    private int state;
    @TableField("create_time")
    private java.sql.Timestamp createTime;
    @TableField("settled_time")
    private java.sql.Timestamp settledTime;
    @TableField("settle_user")
    private long settleUser;
    @TableField("is_model")
    private int isModel;
    @TableField("reamark")
    private String reamark;
    @TableField(exist = false)
    private String userName;
    @TableField(exist = false)
    private String ssex;
    @TableField(exist = false)
    private String mobile;

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


    public String getSettleAmount() {
        return settleAmount;
    }

    public void setSettleAmount(String settleAmount) {
        this.settleAmount = settleAmount;
    }


    public String getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(String realAmount) {
        this.realAmount = realAmount;
    }


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }


    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public java.sql.Timestamp getSettledTime() {
        return settledTime;
    }

    public void setSettledTime(java.sql.Timestamp settledTime) {
        this.settledTime = settledTime;
    }


    public long getSettleUser() {
        return settleUser;
    }

    public void setSettleUser(long settleUser) {
        this.settleUser = settleUser;
    }


    public int getIsModel() {
        return isModel;
    }

    public void setIsModel(int isModel) {
        this.isModel = isModel;
    }


    public String getReamark() {
        return reamark;
    }

    public void setReamark(String reamark) {
        this.reamark = reamark;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSsex() {
        return ssex;
    }

    public void setSsex(String ssex) {
        this.ssex = ssex;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
