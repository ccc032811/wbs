package com.neefull.fsp.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Security;

public class EncryptUtil {

    private final static Logger log = LoggerFactory.getLogger(EncryptUtil.class.getName());

    public static String encrypt(String src, String key) {

        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            byte[] raw = key.getBytes("utf-8");
            SecretKeySpec keySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encrypted = cipher.doFinal(src.getBytes("utf-8"));
            return URLEncoder.encode(new String(Base64Utils.encode(encrypted)), "utf-8");
        } catch (Exception e) {
            log.error("encrypt failed" + e.getMessage());
            return null;
        }
    }

    public static String decrypt(String src, String key) {
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            src = URLDecoder.decode(src, "utf-8");
            byte[] raw = key.getBytes("utf-8");
            SecretKeySpec keySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] encrypted1 = Base64Utils.decode(src.getBytes());
            byte[] original = cipher.doFinal(encrypted1);
            return new String(original, "utf-8");

        } catch (Exception e) {
            log.error("decrypt failed" + e.getMessage());
            return null;
        }
    }

   /* public  static void main(String[]args)
    {
        log.error(decrypt("OEfCVDTaLikgBT%2FUWO%2BAomDFbo5S2i7cbu60ikzAnvLDVwtc3Yg5GqWcytNvH%2FXk", Constaint.AES_KEY));
    }*/
}
