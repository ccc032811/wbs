package com.neefull.fsp.web.qff.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: chengchengchu
 * @Date: 2019/12/11  15:03
 */

@Component
@ConfigurationProperties(prefix = "qff.send")
public class TemplateProperties {

    private String conserveTemplatePath;

    private String conserveDownLoadPath;

    private String recentTemplatePath;

    private String recentDownLoadPath;

    private String refundTemplatePath;

    private String refundDownLoadPath;

    private String rocheTemplatePath;

    private String rocheDownLoadPath;

    public String getConserveTemplatePath() {
        return conserveTemplatePath;
    }

    public void setConserveTemplatePath(String conserveTemplatePath) {
        this.conserveTemplatePath = conserveTemplatePath;
    }

    public String getConserveDownLoadPath() {
        return conserveDownLoadPath;
    }

    public void setConserveDownLoadPath(String conserveDownLoadPath) {
        this.conserveDownLoadPath = conserveDownLoadPath;
    }

    public String getRecentTemplatePath() {
        return recentTemplatePath;
    }

    public void setRecentTemplatePath(String recentTemplatePath) {
        this.recentTemplatePath = recentTemplatePath;
    }

    public String getRecentDownLoadPath() {
        return recentDownLoadPath;
    }

    public void setRecentDownLoadPath(String recentDownLoadPath) {
        this.recentDownLoadPath = recentDownLoadPath;
    }

    public String getRefundTemplatePath() {
        return refundTemplatePath;
    }

    public void setRefundTemplatePath(String refundTemplatePath) {
        this.refundTemplatePath = refundTemplatePath;
    }

    public String getRefundDownLoadPath() {
        return refundDownLoadPath;
    }

    public void setRefundDownLoadPath(String refundDownLoadPath) {
        this.refundDownLoadPath = refundDownLoadPath;
    }

    public String getRocheTemplatePath() {
        return rocheTemplatePath;
    }

    public void setRocheTemplatePath(String rocheTemplatePath) {
        this.rocheTemplatePath = rocheTemplatePath;
    }

    public String getRocheDownLoadPath() {
        return rocheDownLoadPath;
    }

    public void setRocheDownLoadPath(String rocheDownLoadPath) {
        this.rocheDownLoadPath = rocheDownLoadPath;
    }
}
