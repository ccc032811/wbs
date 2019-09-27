package com.neefull.fsp.web.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.neefull.fsp.web.common.converter.TimeConverter;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_project")
@Excel("项目信息表")
public class Project implements Serializable {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private long id;

    /**
     * 用户ID
     */
    @TableId("user_id")
    private long userId;

    /**
     * 公司名称
     */
    @TableField(exist = false)
    @ExcelField(value = "公司名称")
    private String corpName;

    /**
     * 标题
     */
    @TableField("title")
    @ExcelField(value = "标题")
    private String title;

    /**
     * 服务类型
     */
    @TableField("service_type")
    @ExcelField(value = "服务类型")
    private String serviceType;

    /**
     * 服务内容
     */
    @TableField("service_content")
    @ExcelField(value = "服务内容")
    private String serviceContent;

    /**
     * 结算时间
     */
    @TableField("settle_time")
    private String settleTime;

    /**
     * 报酬类型
     */
    @TableField("salary_type")
    private String salaryType;

    /**
     * 报酬金额
     */
    @TableField("amount")
    @ExcelField(value = "报酬金额")
    private String amount;

    /**
     * 描述
     */
    @TableField("des")
    private String des;

    /**
     * 工作内容
     */
    @TableField("content")
    private String content;

    /**
     * 工作要求
     */
    @TableField("requirement")
    @ExcelField(value = "工作要求")
    private String requirement;

    /**
     * 工作地点
     */
    @TableField("place")
    @ExcelField(value = "工作地点")
    private String place;

    /**
     * 性别要求
     */
    @TableField("req_sex")
    @ExcelField(value = "性别要求")
    private String reqSex;

    /**
     * 身份要求
     */
    @TableField("req_identity")
    @ExcelField(value = "身份要求")
    private String reqIdentity;

    /**
     * 学历要求
     */
    @TableField("req_edu")
    @ExcelField(value = "学历要求")
    private String reqEdu;

    /**
     * 年龄要求
     */
    @TableField("req_age")
    @ExcelField(value = "年龄要求")
    private String reqAge;

    /**
     * 人数要求
     */
    @TableField("req_num")
    @ExcelField(value = "人数要求")
    private int reqNum;

    /**
     * 已报名数量
     */
    @TableField("sign_num")
    @ExcelField(value = "已报名数量")
    private int signNum;

    /**
     * 岗位有效期
     */
    @TableField("valid_date")
    @ExcelField(value = "岗位有效期")
    private String validDate;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @ExcelField(value = "创建时间", writeConverter = TimeConverter.class)
    private Date createTime;

    /**
     * 创建人
     */
    @TableField("create_user")
    private long createUser;

    /**
     * 修改时间
     */
    @TableField(value = "modify_time", fill = FieldFill.UPDATE)
    private Date modifyTime;

    /**
     * 修改人
     */
    @TableField("modify_user")
    private long modifyUser;

    /**
     * 当前状态 0新建 1 待结算  2进行中  3完结 -1已删除
     */
    @TableField("current_state")
    @ExcelField(value = "当前状态")
    private String currentState;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    @TableField(exist = false)
    private String createTimeFrom;
    @TableField(exist = false)
    private String createTimeTo;
    @TableField(exist = false)
    private String currentStateName;
}
