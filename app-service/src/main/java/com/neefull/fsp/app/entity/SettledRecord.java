package com.neefull.fsp.app.entity;

import java.io.Serializable;

public class SettledRecord implements Serializable {

    private long projectId;
    private String title;
    private String projectNo;
    private long userId;
    private String userName;
    private String mobile;
    private String settledAount;
    private String settleTime;
    private String settledTime;

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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

    public String getSettledAount() {
        return settledAount;
    }

    public void setSettledAount(String settledAount) {
        this.settledAount = settledAount;
    }

    public String getSettleTime() {
        return settleTime;
    }

    public void setSettleTime(String settleTime) {
        this.settleTime = settleTime;
    }

    public String getSettledTime() {
        return settledTime;
    }

    public void setSettledTime(String settledTime) {
        this.settledTime = settledTime;
    }
}
