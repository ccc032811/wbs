package com.neefull.fsp.api.config;

import java.util.HashMap;
import java.util.Map;

/**
 * 常量类
 */
public abstract class ServConstants {

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

}
