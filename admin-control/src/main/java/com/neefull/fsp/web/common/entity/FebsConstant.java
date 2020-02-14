package com.neefull.fsp.web.common.entity;

/**
 * 常量
 *
 * @author pei.wang
 */
public class FebsConstant {

    // 排序规则：降序
    public static final String ORDER_DESC = "desc";
    // 排序规则：升序
    public static final String ORDER_ASC = "asc";

    // 前端页面路径前缀
    public static final String VIEW_PREFIX = "febs/views/";

    // 验证码 Session Key
    public static final String CODE_PREFIX = "febs_captcha_";

    // 允许下载的文件类型，根据需求自己添加（小写）
    public static final String[] VALID_FILE_TYPE = {"xlsx", "zip"};

    public static final String AES_KEY = "30D67B0B52A14B8203910D1BD78AF96A";
}
