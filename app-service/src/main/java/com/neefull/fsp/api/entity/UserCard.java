package com.neefull.fsp.api.entity;


import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

@TableName("t_user_card")
public class UserCard implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private long id;
    @TableField("user_id")
    private long userId;
    @TableField("real_name")
    private String realName;
    @TableField("card_no")
    private String cardNo;
    @TableField("bank_name")
    private String bankName;
    @TableField("sub_branch")
    private String subBranch;
    @TableField("city")
    private String city;
    @TableField("link_no")
    private String linkNo;
    @TableField("auth_status")
    private long authStatus;
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


    public long getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(long authStatus) {
        this.authStatus = authStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
