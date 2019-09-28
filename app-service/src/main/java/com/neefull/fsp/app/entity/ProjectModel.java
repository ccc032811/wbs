package com.neefull.fsp.app.entity;

import java.io.Serializable;

public class ProjectModel implements Serializable {

  private long id;
  private long userId;
  private String serviceType;
  private String serviceContent;
  private String salaryType;
  private String amount;
  private String title;
  private String des;
  private String content;
  private String requirement;
  private String place;
  private String reqSex;
  private String reqIdentity;
  private String reqEdu;
  private String reqAge;
  private int reqNum;
  private String validDate;
  private java.sql.Timestamp createTime;
  private int openState;
  private String currentState;


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


  public java.sql.Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(java.sql.Timestamp createTime) {
    this.createTime = createTime;
  }


  public int getOpenState() {
    return openState;
  }

  public void setOpenState(int openState) {
    this.openState = openState;
  }


  public String getCurrentState() {
    return currentState;
  }

  public void setCurrentState(String currentState) {
    this.currentState = currentState;
  }

}
