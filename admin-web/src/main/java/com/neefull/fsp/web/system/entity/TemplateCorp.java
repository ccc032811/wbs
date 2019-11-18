package com.neefull.fsp.web.system.entity;

import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import com.wuwenze.poi.validator.EmailValidator;
import lombok.Data;

/**
 * @program: fsp
 * @author: xi.chen
 * @create: 2019-10-25 16:12
 **/
@Data
@Excel("企业用户模板")
public class TemplateCorp {

    /**
     * 用户类型(固定值0)
     */
    @ExcelField(value = "用户类型", required = true, maxLength = 5,
            comment = "提示：固定值，0")
    private String field1;

    /**
     * 用户名
     */
    @ExcelField(value = "用户名", required = true, maxLength = 20,
            comment = "提示：用于登录的账号")
    private String field2;

    /**
     * 邮箱
     */
    @ExcelField(value = "邮箱", maxLength = 50,
            comment = "提示：只能填写邮箱，长度不能超过50个字符", validator = EmailValidator.class)
    private String field3;

    /**
     * 手机
     */
    @ExcelField(value = "手机", maxLength = 15, regularExp = "[0-9]+",
            regularExpMessage = "必须是数字", comment = "提示: 只能填写数字")
    private String field4;

    /**
     * 法人名称
     */
    @ExcelField(value = "法人名称", maxLength = 15,  comment = "提示: 法人名称")
    private String field5;

    /**
     * 企业名称
     */
    @ExcelField(value = "企业名称", maxLength = 20,
            comment = "提示：企业名称")
    private String field6;

    /**
     * 社会信用代码
     */
    @ExcelField(value = "社会信用代码", maxLength = 30,
            comment = "提示：社会信用代码")
    private String field7;

    /**
     * 银行账号
     */
    @ExcelField(value = "银行账号", maxLength = 30,
            comment = "提示：银行账号")
    private String field8;

    /**
     * 开户支行
     */
    @ExcelField(value = "开户支行", maxLength = 30,
            comment = "提示：开户支行")
    private String field9;

    /**
     * 企业地址
     */
    @ExcelField(value = "企业地址", maxLength = 30,
            comment = "提示：企业地址")
    private String field10;

    /**
     * 联系电话
     */
    @ExcelField(value = "联系电话", maxLength = 15,
            comment = "提示：联系电话")
    private String field11;

    /**
     * 开户许可证
     */
    @ExcelField(value = "开户许可证", maxLength = 20,
            comment = "提示：开户许可证")
    private String field12;

}