package com.neefull.fsp.app.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;

@TableName("t_msg_info")
public class MsgInfo implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private long id;
    @TableField("title")
    private String title;
    @TableField("content")
    private String content;
    @TableField("msg_type")
    private String msgType;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private java.sql.Timestamp createTime;
    @TableField(value = "modify_time", fill = FieldFill.UPDATE)
    private java.sql.Timestamp modifyTime;
    @TableField(exist = false)
    private long userId;
    @TableField(exist = false)
    private int readState;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
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

    public int getReadState() {
        return readState;
    }

    public void setReadState(int readState) {
        this.readState = readState;
    }
}
