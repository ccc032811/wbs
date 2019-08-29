package com.neefull.fsp.api.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_auth_corp")
public class AuthCorp {

    @TableId(type = IdType.AUTO)
    private long id;
    @TableField("user_id")
    private long userId;
    @TableField("legal_name")
    private String legalName;
    @TableField("corp_name")
    private String corpName;
    @TableField("credit_code")
    private String creditCode;
    @TableField("bank_no")
    private long bankNo;
    @TableField("bank_name")
    private String bankName;
    @TableField("corp_address")
    private String corpAddress;
    @TableField("link_no")
    private String linkNo;
    @TableField("business_lience")
    private String businessLience;
    @TableField("open_lience")
    private String openLience;
    @TableField("auth_status")
    private String authStatus;
    @TableField("auth_type")
    private String authType;
    @TableField("authpass_time")
    private Date authpassTime;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(value = "modify_time", fill = FieldFill.UPDATE)
    private Date modifyTime;
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


    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }


    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }


    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }


    public long getBankNo() {
        return bankNo;
    }

    public void setBankNo(long bankNo) {
        this.bankNo = bankNo;
    }


    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }


    public String getCorpAddress() {
        return corpAddress;
    }

    public void setCorpAddress(String corpAddress) {
        this.corpAddress = corpAddress;
    }


    public String getLinkNo() {
        return linkNo;
    }

    public void setLinkNo(String linkNo) {
        this.linkNo = linkNo;
    }


    public String getBusinessLience() {
        return businessLience;
    }

    public void setBusinessLience(String businessLience) {
        this.businessLience = businessLience;
    }


    public String getOpenLience() {
        return openLience;
    }

    public void setOpenLience(String openLience) {
        this.openLience = openLience;
    }


    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(String authStatus) {
        this.authStatus = authStatus;
    }


    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }


    public Date getAuthpassTime() {
        return authpassTime;
    }

    public void setAuthpassTime(Date authpassTime) {
        this.authpassTime = authpassTime;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(java.sql.Timestamp createTime) {
        this.createTime = createTime;
    }


    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(java.sql.Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
