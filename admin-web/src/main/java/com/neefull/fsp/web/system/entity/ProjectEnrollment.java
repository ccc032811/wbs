package com.neefull.fsp.web.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.wuwenze.poi.annotation.Excel;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@TableName("t_project_enrollment")
@Excel("项目注册用户表")
public class ProjectEnrollment {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private long id;

    /**
     * 项目编号
     */
    @NotNull
    @TableField("project_id")
    private long projectId;

    /**
     * 报名人员
     */
    @TableField("user_id")
    private long userId;

    /**
     * 创建时间（报名时间）
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Timestamp createTime;

    /**
     * 通过时间
     */
    @TableField("pass_time")
    private Timestamp passTime;

    /**
     * 审核通过人员
     */
    @TableField("pass_user")
    private Timestamp passUser;

    /**
     * 当前状态 0待审核 1通过 2 暂停 -1 移除
     */
    @TableField("current_state")
    private int currentState;

    /**
     * 修改时间
     */
    @TableField(value = "modify_time", fill = FieldFill.UPDATE)
    private Timestamp modifyTime;

    /**
     * 修改人员
     */
    @TableField("modify_user")
    private long modifyUser;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
}
