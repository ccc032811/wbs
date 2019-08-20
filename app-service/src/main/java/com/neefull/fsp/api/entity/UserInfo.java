package com.neefull.fsp.api.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_user_info")
public class UserInfo implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("user_id")
    private Long userId;
    @TableField("real_name")
    private String realName;
    @TableField("identity")
    private String identity;
    @TableField("birthday")
    private String birthday;
    @TableField("education")
    private String education;
    @TableField("city_life")
    private String cityLife;
    @TableField("city_work")
    private String cityWork;
    @TableField("job_type")
    private String jobType;
    @TableField("id_no")
    private String idNo;
    @TableField("id_image1")
    private String idImage1;
    @TableField("id_image2")
    private String idImage2;
    @TableField("card_No")
    private String cardNo;
    @TableField("card_Image1")
    private String cardImage1;
    @TableField("card_Image2")
    private String cardImage2;
    @TableField("link_no")
    private String linkNo;
    @TableField("personal_desc")
    private String personalDesc;
    @TableField("identity_auth")
    private String identityAuth;
    @TableField("card_auth")
    private String cardAuth;
    @TableField("create_time")
    private Date createTime;
    @TableField("motify_time")
    private Date motifyTime;

}
