package com.neefull.common.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "idcard")
@PropertySource(value = "classpath:openapi.properties", ignoreResourceNotFound = true, encoding = "UTF-8")
public class IDValidConfig {

    private String appCode;

    private String host;

    private String path;

    private String method;

    private String paraName;

    private String paraNo;


    private int on;

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public int getOn() {
        return on;
    }

    public void setOn(int on) {
        this.on = on;
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

    public String getParaName() {
        return paraName;
    }

    public void setParaName(String paraName) {
        this.paraName = paraName;
    }

    public String getParaNo() {
        return paraNo;
    }

    public void setParaNo(String paraNo) {
        this.paraNo = paraNo;
    }
}