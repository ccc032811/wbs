package com.neefull.fsp.app.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.sql.Timestamp;

@TableName("t_user_resume")
public class UserResume {

    @TableId(value = "id", type = IdType.AUTO)
    private long id;
    @TableField("user_id")
    private long userId;
    @TableField("real_name")
    private String realName;
    @TableField("identity")
    private String identity;
    @TableField("birthday")
    private String birthday;
    @TableField("link_no")
    private String linkNo;
    @TableField("education")
    private String education;
    @TableField("city_life")
    private String cityLife;
    @TableField("city_work")
    private String cityWork;
    @TableField("job_type")
    private String jobType;
    @TableField("personal_desc")
    private String personalDesc;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private java.sql.Timestamp createTime;
    @TableField(value = "modify_time", fill = FieldFill.UPDATE)
    private java.sql.Timestamp modifyTime;
    @TableField("mark")
    private String mark;
    @TableField("edu_desc")
    private String eduDesc;
    @TableField("worked_desc")
    private String workedDesc;
    @TableField("work_state")
    private int workState;
    @TableField(exist = false)
    private float integrity;


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


    public String getLinkNo() {
        return linkNo;
    }

    public void setLinkNo(String linkNo) {
        this.linkNo = linkNo;
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


    public String getPersonalDesc() {
        return personalDesc;
    }

    public void setPersonalDesc(String personalDesc) {
        this.personalDesc = personalDesc;
    }


    public java.sql.Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(java.sql.Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getEduDesc() {
        return eduDesc;
    }

    public void setEduDesc(String eduDesc) {
        this.eduDesc = eduDesc;
    }


    public String getWorkedDesc() {
        return workedDesc;
    }

    public void setWorkedDesc(String workedDesc) {
        this.workedDesc = workedDesc;
    }


    public int getWorkState() {
        return workState;
    }

    public void setWorkState(int workState) {
        this.workState = workState;
    }

    public float getIntegrity() {
        return integrity;
    }

    public void setIntegrity(float integrity) {
        this.integrity = integrity;
    }
}
