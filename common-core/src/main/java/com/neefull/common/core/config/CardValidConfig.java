package com.neefull.common.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "card")
@PropertySource(value = "classpath:openapi.properties", ignoreResourceNotFound = true, encoding = "UTF-8")
public class CardValidConfig {

    private String appCode;
    private String host;
    private String path;
    private String method;
    private String paraCName;
    private String paraCNo;

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParaCName() {
        return paraCName;
    }

    public void setParaCName(String paraCName) {
        this.paraCName = paraCName;
    }

    public String getParaCNo() {
        return paraCNo;
    }

    public void setParaCNo(String paraCNo) {
        this.paraCNo = paraCNo;
    }
}
