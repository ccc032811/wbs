package com.neefull.fsp.app.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;

@TableName("t_project")
public class Project implements Serializable {

    @TableId(type = IdType.AUTO)
    private long id;
    @TableId("user_id")
    private long userId;
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
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private java.sql.Timestamp createTime;
    @TableField("create_user")
    private long createUser;
    @TableField(value = "modify_time", fill = FieldFill.UPDATE)
    private java.sql.Timestamp modifyTime;
    @TableField("modify_user")
    private long modifyUser;
    @TableField("current_state")
    private char currentState;
    @TableField("remark")
    private String remark;
    @TableField("valid_date")
    private String validDate;
    @TableField("sign_num")
    private int signNum;
    @TableField(exist = false)
    private String signState;
    @TableField(exist = false)
    private long signUser;
    @TableField(exist = false)
    private String createUserName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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


    public java.sql.Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(java.sql.Timestamp createTime) {
        this.createTime = createTime;
    }


    public long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(long createUser) {
        this.createUser = createUser;
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


    public char getCurrentState() {
        return currentState;
    }

    public void setCurrentState(char currentState) {
        this.currentState = currentState;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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

    public String getSignState() {
        return signState;
    }

    public void setSignState(String signState) {
        this.signState = signState;
    }

    public long getSignUser() {
        return signUser;
    }

    public void setSignUser(long signUser) {
        this.signUser = signUser;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }
}
