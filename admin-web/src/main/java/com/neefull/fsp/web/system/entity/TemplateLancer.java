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
@Excel("自由职业者模板")
public class TemplateLancer {

    /**
     * 用户类型(固定值1)
     */
    @ExcelField(value = "用户类型", required = true, maxLength = 5,
            comment = "提示：固定值，1")
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
     * 身份证号
     */
    @ExcelField(value = "身份证号", maxLength = 18,  comment = "提示: 身份证号")
    private String field5;

    /**
     * 真实姓名
     */
    @ExcelField(value = "真实姓名", maxLength = 10,
            comment = "提示：真实姓名")
    private String field6;

    /**
     * 银行卡号
     */
    @ExcelField(value = "银行卡号", maxLength = 20,
            comment = "提示：银行卡号")
    private String field7;

    /**
     * 开户行
     */
    @ExcelField(value = "开户行", maxLength = 20,
            comment = "提示：开户行")
    private String field8;

    /**
     * 开户地点
     */
    @ExcelField(value = "开户地点", maxLength = 20,
            comment = "提示：开户地点")
    private String field9;

    /**
     * 银行简称
     */
    @ExcelField(value = "银行简称", maxLength = 20,
            comment = "提示：银行简称")
    private String field10;

    /**
     * 开户网点
     */
    @ExcelField(value = "开户网点", maxLength = 20,
            comment = "提示：开户网点")
    private String field11;

}