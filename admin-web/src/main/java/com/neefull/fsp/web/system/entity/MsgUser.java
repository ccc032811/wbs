package com.neefull.fsp.web.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("flp_slave.t_msg_user")
public class MsgUser implements Serializable {

    // 消息读取状态 => 0:未读
    public static final int READ_STATE_UNREAD = 0;
    //消息删除状态 => 0:未删除
    public static final int DELETE_STATE_UNDELETE = 0;

    /**
     * 主键编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private long id;

    /**
     * 消息编号
     */
    @TableField("msg_id")
    private long msgId;

    /**
     * 用户编号
     */
    @TableField("user_id")
    private long userId;

    /**
     * 1 已读
     */
    @TableField("read_state")
    private int readState;

    /**
     * 删除状态 0未删除 1 已删除
     */
    @TableField("delete_state")
    private int deleteState;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(value = "modify_time")
    private Date modifyTime;
}
