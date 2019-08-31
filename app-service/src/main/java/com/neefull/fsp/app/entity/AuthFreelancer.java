package com.neefull.fsp.app.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_auth_freelancer")
public class AuthFreelancer {

    @TableId(value = "id", type = IdType.AUTO)
    private long id;
    @TableField("user_id")
    private long userId;
    @TableField("card_no")
    private String cardNo;
    @TableField("bank_name")
    private String bankName;
    @TableField("bank_abbr")
    private String bankAbbr;
    @TableField("bank_leaf")
    private String bankLeaf;
    @TableField("id_no")
    private String idNo;
    @TableField("real_name")
    private String realName;
    @TableField("mobile")
    private String mobile;
    @TableField("id_image1")
    private String idImage1;
    @TableField("id_image2")
    private String idImage2;
    @TableField("card_image1")
    private String cardImage1;
    @TableField("card_image2")
    private String cardImage2;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(value = "modify_time", fill = FieldFill.UPDATE)
    private Date modifyTime;
    @TableField("remark")
    private String remark;
    @TableField("bank_city")
    private String bankCity;
    @TableField("auth_status")
    private int authStatus;


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


    public String getBankAbbr() {
        return bankAbbr;
    }

    public void setBankAbbr(String bankAbbr) {
        this.bankAbbr = bankAbbr;
    }


    public String getBankLeaf() {
        return bankLeaf;
    }

    public void setBankLeaf(String bankLeaf) {
        this.bankLeaf = bankLeaf;
    }


    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
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


    public String getIdImage1() {
        return idImage1;
    }

    public void setIdImage1(String idImage1) {
        this.idImage1 = idImage1;
    }


    public String getIdImage2() {
        return idImage2;
    }

    public void setIdImage2(String idImage2) {
        this.idImage2 = idImage2;
    }


    public String getCardImage1() {
        return cardImage1;
    }

    public void setCardImage1(String cardImage1) {
        this.cardImage1 = cardImage1;
    }


    public String getCardImage2() {
        return cardImage2;
    }

    public void setCardImage2(String cardImage2) {
        this.cardImage2 = cardImage2;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date midifyTime) {
        this.modifyTime = midifyTime;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBankCity() {
        return bankCity;
    }

    public void setBankCity(String bankCity) {
        this.bankCity = bankCity;
    }

    public int getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(int authStatus) {
        this.authStatus = authStatus;
    }
}
