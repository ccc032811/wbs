package com.neefull.fsp.web.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.neefull.fsp.web.common.converter.CustomizeFieldWriteConverter;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_project_settlement")
@Excel("结算人员表")
public class ProjectSettlement implements Serializable {

    /**
     * 编号
     */
    @TableId(type = IdType.AUTO)
    private long id;

    /**
     * 公司名称
     */
    @TableField(exist = false)
    @ExcelField(value = "公司名称")
    private String corpName;

    /**
     * 项目编号
     */
    @TableField("project_id")
    @ExcelField(value = "项目编号",writeConverter = CustomizeFieldWriteConverter.class)
    private long projectId;

    /**
     * 项目名称
     */
    @TableField(exist = false)
    @ExcelField(value = "项目名称")
    private String projectName;

    /**
     * 结算用户
     */
    @TableField("user_id")
    @ExcelField(value = "用户id",writeConverter = CustomizeFieldWriteConverter.class)
    private long userId;

    /**
     * 用户真实姓名
     */
    @TableField(exist = false)
    @ExcelField(value = "用户真实姓名")
    private String userName;

    /**
     * 用户手机号
     */
    @TableField(exist = false)
    @ExcelField(value = "用户手机号")
    private String mobile;

    /**
     * 用户身份证
     */
    @TableField(exist = false)
    @ExcelField(value = "用户身份证")
    private String idNo;

    /**
     * 结算总金额
     */
    @TableField("settle_amount")
    @ExcelField(value = "结算总金额")
    private double settleAmount;

    /**
     * 实际支付金额
     */
    @TableField("real_amount")
    private double realAmount;

    /**
     * 发起结算时间-格式化
     */
    @TableField(exist = false)
    @ExcelField(value = "发起结算时间")
    private String settleTimeName;

    /**
     * 是否模板 0否 1是
     */
    @TableField("is_model")
    private int isModel;

    /**
     * 卡号
     */
    @TableField(exist = false)
    @ExcelField(value = "卡号")
    private String cardNo;

    /**
     * 备注
     */
    @TableField("reamark")
    @ExcelField(value = "备注")
    private String reamark;

    /**
     * 结算状态 0 待结算 1 结算完成
     */
    @TableField("state")
    @ExcelField(value = "结算状态[0:待结算]",writeConverter = CustomizeFieldWriteConverter.class)
    @Size(max = 3, message = "{noMoreThan}")
    private String state;

    /**
     * 发起结算时间
     */
    @TableField("settle_time")
    private Date settleTime;

    /**
     * 完成结算时间
     */
    @TableField("settled_time")
    private Date settledTime;

    /**
     * 发起结算人员
     */
    @TableField("settle_user")
    private long settleUser;

    /**
     * 结算状态中文 0 待结算 1 结算完成
     */
    @TableField(exist = false)
    private String stateName;
}
