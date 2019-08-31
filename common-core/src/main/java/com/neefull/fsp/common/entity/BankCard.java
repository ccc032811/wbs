package com.neefull.fsp.common.entity;


import java.io.Serializable;


public class BankCard implements Serializable {

    private long userId;

    private String realName;
    private String certNo;

    private String cardNo;

    private String bankName;

    private String subBranch;

    private String city;

    private String linkNo;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getSubBranch() {
        return subBranch;
    }

    public void setSubBranch(String subBranch) {
        this.subBranch = subBranch;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLinkNo() {
        return linkNo;
    }

    public void setLinkNo(String linkNo) {
        this.linkNo = linkNo;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }
}
