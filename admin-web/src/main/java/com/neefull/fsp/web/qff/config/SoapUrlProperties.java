package com.neefull.fsp.web.qff.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: chengchengchu
 * @Date: 2020/3/11  14:03
 */

@Component
@ConfigurationProperties(prefix = "qff.soap")
public class SoapUrlProperties {

    private String soapUrl;

    public String getSoapUrl() {
        return soapUrl;
    }

    public void setSoapUrl(String soapUrl) {
        this.soapUrl = soapUrl;
    }
}
