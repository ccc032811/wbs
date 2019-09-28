package com.neefull.fsp.app.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;

@TableName("t_msg_user")
public class MsgUser implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private long id;
    @TableField("msg_id")
    private long msgId;
    @TableField("user_id")
    private long userId;
    @TableField("read_state")
    private int readState;
    @TableField("delete_state")
    private int deleteState;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private java.sql.Timestamp createTime;
    @TableField(value = "modify_time", fill = FieldFill.UPDATE)
    private java.sql.Timestamp modifyTime;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public long getMsgId() {
        return msgId;
    }

    public void setMsgId(long msgId) {
        this.msgId = msgId;
    }


    public int getReadState() {
        return readState;
    }

    public void setReadState(int readState) {
        this.readState = readState;
    }


    public int getDeleteState() {
        return deleteState;
    }

    public void setDeleteState(int deleteState) {
        this.deleteState = deleteState;
    }


    public java.sql.Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(java.sql.Timestamp createTime) {
        this.createTime = createTime;
    }


    public java.sql.Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(java.sql.Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
