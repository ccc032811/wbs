package com.neefull.fsp.api.config;

import java.util.HashMap;
import java.util.Map;

/**
 * 常量类
 */
public abstract class AppConstant {

    public static final String REG_SMS_TEMPLATE = "";
    public static final String AES_KEY = "30D67B0B52A14B8203910D1BD78AF96A";
    public static Map<String, String> SMS_TYPE = new HashMap<String, String>() {
        {
            //注册
            put("REG", "SMS_107205031");
            //修改密码
            put("MPW", "SMS_107205030");
            //信息变更
            put("MIF", "SMS_107205029");
            //结算确认
            put("SMC", "SMS_107205033");
        }
    };

    public static Map<String, String> IMG_TYPE = new HashMap<String, String>() {
        {
            //身份证正面
            put("0", "id_image1");
            //身份证反面
            put("1", "id_image2");
            //银行卡正面
            put("2", "card_image1");
            //银行卡反面
            put("3", "card_image2");
        }
    };

}
