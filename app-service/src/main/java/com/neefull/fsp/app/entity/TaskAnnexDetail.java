package com.neefull.fsp.app.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.sql.Timestamp;

@TableName("t_task_annex_detail")
public class TaskAnnexDetail implements Serializable {

    @TableId(value = "detail_id", type = IdType.AUTO)
    private long detailId;
    @TableField("annex_id")
    private long annexId;
    @TableField("annex_address")
    private String annexAddress;
    @TableField("annex_desc")
    private String annexDesc;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private java.sql.Timestamp createTime;

    public long getDetailId() {
        return detailId;
    }

    public void setDetailId(long detailId) {
        this.detailId = detailId;
    }

    public String getAnnexAddress() {
        return annexAddress;
    }

    public void setAnnexAddress(String annexAddress) {
        this.annexAddress = annexAddress;
    }

    public String getAnnexDesc() {
        return annexDesc;
    }

    public void setAnnexDesc(String annexDesc) {
        this.annexDesc = annexDesc;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public long getAnnexId() {
        return annexId;
    }

    public void setAnnexId(long annexId) {
        this.annexId = annexId;
    }
}
