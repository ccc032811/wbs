package com.neefull.fsp.web.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_auth_freelancer")
public class AuthFreelancer implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private long id;

    /**
     * 用户编号
     */
    @TableField("user_id")
    private long userId;

    /**
     * 银行卡号
     */
    @TableField("card_no")
    private String cardNo;

    /**
     * 开户行
     */
    @TableField("bank_name")
    private String bankName;

    /**
     * 开户地点
     */
    @TableField("bank_city")
    private String bankCity;

    /**
     * 银行简称
     */
    @TableField("bank_abbr")
    private String bankAbbr;

    /**
     * 开户网点
     */
    @TableField("bank_leaf")
    private String bankLeaf;

    /**
     * 身份证号码
     */
    @TableField("id_no")
    private String idNo;

    /**
     * 真实姓名
     */
    @TableField("real_name")
    private String realName;

    /**
     * 预留手机号码
     */
    @TableField("mobile")
    private String mobile;

    /**
     * 身份证照片-正面
     */
    @TableField("id_image1")
    private String idImage1;

    /**
     * 身份证照片-反面
     */
    @TableField("id_image2")
    private String idImage2;

    /**
     * 卡照片-正面
     */
    @TableField("card_image1")
    private String cardImage1;

    /**
     * 卡照片-反面
     */
    @TableField("card_image2")
    private String cardImage2;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "modify_time", fill = FieldFill.UPDATE)
    private Date modifyTime;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 实名状态 0 待认证 1 认证通过 -1 认证失败
     */
    @TableField("auth_status")
    private int authStatus;

    /**
     * 实名状态-中文
     */
    @Transient
    private String authStatusName;
}
