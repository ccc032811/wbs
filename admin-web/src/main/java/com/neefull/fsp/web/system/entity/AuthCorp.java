package com.neefull.fsp.web.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_auth_corp")
public class AuthCorp implements Serializable {

    private static final long serialVersionUID = 1L;

    // 认证方式： 1 自动
    public static final String AUTH_TYPE_AUTOMATIC = "1";
    // 认证方式： 2 人工
    public static final String AUTH_TYPE_PERSON = "2";

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private long id;

    /**
     * 企业用户ID
     */
    @TableField("user_id")
    private long userId;

    /**
     * 法人名称
     */
    @TableField("legal_name")
    private String legalName;

    /**
     * 企业名称
     */
    @TableField("corp_name")
    private String corpName;

    /**
     * 社会信用代码、纳税人识别号
     */
    @TableField("credit_code")
    private String creditCode;

    /**
     * 银行账号
     */
    @TableField("bank_no")
    private String bankNo;

    /**
     * 开户支行
     */
    @TableField("bank_name")
    private String bankName;

    /**
     * 企业地址
     */
    @TableField("corp_address")
    private String corpAddress;

    /**
     * 联系电话
     */
    @TableField("link_no")
    private String linkNo;

    /**
     * 营业执照照片
     */
    @TableField("business_lience")
    private String businessLience;

    /**
     * 开户许可证
     */
    @TableField("open_lience")
    private String openLience;

    /**
     * 认证状态：0 认证失败  1 认证通过
     */
    @TableField("auth_status")
    private String authStatus;

    /**
     * 认证方式 1 自动 2 人工
     */
    @TableField("auth_type")
    private String authType;

    /**
     * 认证通过时间
     */
    @TableField("authpass_time")
    private Date authpassTime;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(value = "modify_time", fill = FieldFill.UPDATE)
    private Date modifyTime;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 认证人员id
     */
    @TableField("authpass_user")
    private long authpassUser;

    /**
     * 认证状态-中文
     */
    @Transient
    private String authStatusName;

    /**
     * 认证方式-中文
     */
    @Transient
    private String authTypeName;
    /**
     * 格式化后的认证通过时间
     */
    @Transient
    private String authpassTimeStr;
}
