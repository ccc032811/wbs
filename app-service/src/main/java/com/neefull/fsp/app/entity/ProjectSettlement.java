package com.neefull.fsp.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

@TableName("t_project_settlement")
public class ProjectSettlement implements Serializable {
    @TableId(type = IdType.AUTO)
    private long id;
    @TableField("project_id")
    private long projectId;
    @TableField("user_id")
    private long userId;
    @TableField(exist = false)
    private String userName;
    @TableField(exist = false)
    private String ssex;
    @TableField(exist = false)
    private String mobile;
    @TableField("settle_amount")
    private double settleAmount;
    @TableField("real_amount")
    private double realAmount;
    @TableField("is_model")
    private int isModel;
    @TableField("state")
    private String state;
    @TableField("settle_time")
    private java.sql.Timestamp settleTime;
    @TableField("settled_time")
    private java.sql.Timestamp settledTime;
    @TableField("settle_user")
    private long settleUser;
    @TableField("reamark")
    private String reamark;


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


    public double getSettleAmount() {
        return settleAmount;
    }

    public void setSettleAmount(double settleAmount) {
        this.settleAmount = settleAmount;
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    public java.sql.Timestamp getSettleTime() {
        return settleTime;
    }

    public void setSettleTime(java.sql.Timestamp settleTime) {
        this.settleTime = settleTime;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public double getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(double realAmount) {
        this.realAmount = realAmount;
    }

    public int getIsModel() {
        return isModel;
    }

    public void setIsModel(int isModel) {
        this.isModel = isModel;
    }

    public String getSsex() {
        return ssex;
    }

    public void setSsex(String ssex) {
        this.ssex = ssex;
    }
}
