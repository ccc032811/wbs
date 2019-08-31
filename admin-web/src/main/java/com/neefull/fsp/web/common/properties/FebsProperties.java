package com.neefull.fsp.web.common.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @author pei.wang
 */
@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:febs.properties"})
@ConfigurationProperties(prefix = "febs")
public class FebsProperties {

    private ShiroProperties shiro = new ShiroProperties();
    private boolean openAopLog = true;
}
