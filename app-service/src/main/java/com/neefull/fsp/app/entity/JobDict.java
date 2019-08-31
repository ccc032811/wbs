package com.neefull.fsp.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("t_dict_jobs")
@Excel("职位字典")
public class JobDict implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private long id;
    @TableField("job_name")
    private String jobName;
    @TableField("parent_id")
    private long pid;
    @TableField("level")
    private int level;
    @TableField("popular")
    private int popular;
    @TableField("recommend")
    private int recommend;
    @TableField("status")
    private int status;
    @TableField("remark")
    private String remark;
    @TableField("job_req")
    private String jobReq;
    @TableField("job_content")
    private String jobContent;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getPopular() {
        return popular;
    }

    public void setPopular(int popular) {
        this.popular = popular;
    }

    public int getRecommend() {
        return recommend;
    }

    public void setRecommend(int recommend) {
        this.recommend = recommend;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getJobReq() {
        return jobReq;
    }

    public void setJobReq(String jobReq) {
        this.jobReq = jobReq;
    }

    public String getJobContent() {
        return jobContent;
    }

    public void setJobContent(String jobContent) {
        this.jobContent = jobContent;
    }
}
