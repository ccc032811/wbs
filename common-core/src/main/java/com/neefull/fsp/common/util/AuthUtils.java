package com.neefull.fsp.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class AuthUtils {
   // public static final String KEY = Constaint.AES_KEY;
    public static final String SALT = "1L-Q60CL5PSe5O@B";
    public static final int SESSION_TIME = 1 * 3600;//24 * 3600;
    public static final String SESSION_ID = "NEEFUUL_SESS";
    private static final Logger logger = LoggerFactory.getLogger(AuthUtils.class.getName());

    public static long decryptPid(String cookieValue,String key) {

        logger.info("cookieValue" + cookieValue);
        if (StringUtils.isEmpty(cookieValue)) {
            return 0;
        }
        String value = EncryptUtil.decrypt(cookieValue, key);
        if (StringUtils.isEmpty(value)) {
            return 0;
        }
        String[] array = value.split("#");
        if (array.length != 3 || !array[2].equals(SALT) || StringUtils.isEmpty(array[0])) {
            return 0;
        }
        if (Long.valueOf(array[1]) < System.currentTimeMillis()) {
            return 0;
        }
        return Long.valueOf(array[0]);
    }

    public static String encryptPid(long pid,String key) {
        String source = pid + "#" + (System.currentTimeMillis() + SESSION_TIME * 1000) + "#" + SALT;
        return EncryptUtil.encrypt(source, key);
    }

}
