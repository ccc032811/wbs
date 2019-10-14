package com.neefull.fsp.web.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("flp_slave.t_msg_info")
public class MsgInfo implements Serializable {

    //消息类型  2结算
    public static final String MSG_TYPE_SETTLE = "2";
    //消息类型  3实名
    public static final String MSG_TYPE_AUTH = "3";

    /**
     * 主键编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private long id;

    /**
     * 消息标题
     */
    @TableField("title")
    private String title;

    /**
     * 消息内容
     */
    @TableField("content")
    private String content;

    /**
     * 消息类型 0公告 1 提醒 2结算 3实名
     */
    @TableField("msg_type")
    private String msgType;

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
