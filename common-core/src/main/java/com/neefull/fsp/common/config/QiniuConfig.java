package com.neefull.fsp.common.config;

import com.neefull.fsp.common.oss.OssManager;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "qiniu")
@PropertySource(value = "classpath:qiniu.properties", ignoreResourceNotFound = true, encoding = "UTF-8")
public class QiniuConfig {

    private String accessKey;
    private String secretKey;
    private String bucket;
    private String acurl;
    private String domain;

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getAcurl() {
        return acurl;
    }

    public void setAcurl(String acurl) {
        this.acurl = acurl;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    @Bean
    public OssManager getOssManager() {
        return new OssManager();
    }
}
