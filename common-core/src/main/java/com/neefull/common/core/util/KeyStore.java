package com.neefull.common.core.util;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * key工具类  瑞银信提供代码示例
 */
@Component
public class KeyStore {

    private String privateKey;
    private String publicKey;

    /**
     * 类初始化执行，读取公钥和私钥文件
     *
     * @throws IOException
     */
    @PostConstruct
    public void init() throws IOException {
        InputStream isv = KeyStore.class.getClassLoader().getResourceAsStream("pkcs8_rsa_private_key_2048.pem");
        BufferedReader reader = new BufferedReader(new InputStreamReader(isv));
        StringBuilder sb = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
            sb.append(line);
            //sb.append('\r');
        }
        privateKey = sb.toString().trim();
        InputStream isp = KeyStore.class.getClassLoader().getResourceAsStream("ryx_public.pem");
        BufferedReader read = new BufferedReader(new InputStreamReader(isp));
        StringBuilder sb2 = new StringBuilder();
        while ((line = read.readLine()) != null) {
            sb2.append(line);
            //  sb2.append('\r');
        }
        publicKey = sb2.toString().trim();
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }
}

