package com.neefull.fsp.api.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_user_info")
public class UserInfo implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("user_id")
    private Long userId;
    @TableField("real_name")
    private String realName;
    @TableField("identity")
    private String identity;
    @TableField("birthday")
    private String birthday;
    @TableField("education")
    private String education;
    @TableField("city_life")
    private String cityLife;
    @TableField("city_work")
    private String cityWork;
    @TableField("job_type")
    private String jobType;
    @TableField("id_no")
    private String idNo;
    @TableField("id_image1")
    private String idImage1;
    @TableField("id_image2")
    private String idImage2;
    @TableField("card_No")
    private String cardNo;
    @TableField("card_Image1")
    private String cardImage1;
    @TableField("card_Image2")
    private String cardImage2;
    @TableField("link_no")
    private String linkNo;
    @TableField("personal_desc")
    private String personalDesc;
    @TableField("identity_auth")
    private String identityAuth;
    @TableField("card_auth")
    private String cardAuth;
    @TableField("create_time")
    private Date createTime;
    @TableField("motify_time")
    private Date motifyTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getCityLife() {
        return cityLife;
    }

    public void setCityLife(String cityLife) {
        this.cityLife = cityLife;
    }

    public String getCityWork() {
        return cityWork;
    }

    public void setCityWork(String cityWork) {
        this.cityWork = cityWork;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
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

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
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

    public String getLinkNo() {
        return linkNo;
    }

    public void setLinkNo(String linkNo) {
        this.linkNo = linkNo;
    }

    public String getPersonalDesc() {
        return personalDesc;
    }

    public void setPersonalDesc(String personalDesc) {
        this.personalDesc = personalDesc;
    }

    public String getIdentityAuth() {
        return identityAuth;
    }

    public void setIdentityAuth(String identityAuth) {
        this.identityAuth = identityAuth;
    }

    public String getCardAuth() {
        return cardAuth;
    }

    public void setCardAuth(String cardAuth) {
        this.cardAuth = cardAuth;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getMotifyTime() {
        return motifyTime;
    }

    public void setMotifyTime(Date motifyTime) {
        this.motifyTime = motifyTime;
    }
}
