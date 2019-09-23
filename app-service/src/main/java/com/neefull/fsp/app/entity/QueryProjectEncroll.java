package com.neefull.fsp.app.entity;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.util.Date;

public class QueryProjectEncroll implements Serializable {

    @TableField("user_id")
    private long userId;
    @TableField("project_id")
    private long projectId;
    @TableField("project_owner")
    private long projectOwner;
    @TableField("signState")
    private String signState;
    @TableField("sign_time")
    private Date signTime;
    @TableField("pass_time")
    private Date passTime;
    @TableField("service_type")
    private String serviceType;
    @TableField("service_content")
    private String serviceContent;
    @TableField("settle_time")
    private String settleTime;
    @TableField("salary_type")
    private String salaryType;
    @TableField("amount")
    private String amount;
    @TableField("title")
    private String title;
    @TableField("des")
    private String des;
    @TableField("content")
    private String content;
    @TableField("requirement")
    private String requirement;
    @TableField("place")
    private String place;
    @TableField("req_sex")
    private String reqSex;
    @TableField("req_identity")
    private String reqIdentity;
    @TableField("req_edu")
    private String reqEdu;
    @TableField("req_age")
    private String reqAge;
    @TableField("req_num")
    private int reqNum;
    @TableField("valid_date")
    private String validDate;
    @TableField("sign_num")
    private int signNum;
    @TableField("real_name")
    private String realName;
    @TableField("mobile")
    private String mobile;
    @TableField("id_no")
    private String idNo;

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

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public Date getPassTime() {
        return passTime;
    }

    public void setPassTime(Date passTime) {
        this.passTime = passTime;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceContent() {
        return serviceContent;
    }

    public void setServiceContent(String serviceContent) {
        this.serviceContent = serviceContent;
    }

    public String getSettleTime() {
        return settleTime;
    }

    public void setSettleTime(String settleTime) {
        this.settleTime = settleTime;
    }

    public String getSalaryType() {
        return salaryType;
    }

    public void setSalaryType(String salaryType) {
        this.salaryType = salaryType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getReqSex() {
        return reqSex;
    }

    public void setReqSex(String reqSex) {
        this.reqSex = reqSex;
    }

    public String getReqIdentity() {
        return reqIdentity;
    }

    public void setReqIdentity(String reqIdentity) {
        this.reqIdentity = reqIdentity;
    }

    public String getReqEdu() {
        return reqEdu;
    }

    public void setReqEdu(String reqEdu) {
        this.reqEdu = reqEdu;
    }

    public String getReqAge() {
        return reqAge;
    }

    public void setReqAge(String reqAge) {
        this.reqAge = reqAge;
    }

    public int getReqNum() {
        return reqNum;
    }

    public void setReqNum(int reqNum) {
        this.reqNum = reqNum;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }

    public int getSignNum() {
        return signNum;
    }

    public void setSignNum(int signNum) {
        this.signNum = signNum;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public long getProjectOwner() {
        return projectOwner;
    }

    public void setProjectOwner(long projectOwner) {
        this.projectOwner = projectOwner;
    }

    public String getSignState() {
        return signState;
    }

    public void setSignState(String signState) {
        this.signState = signState;
    }
}
