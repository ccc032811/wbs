package com.neefull.fsp.api;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.neefull.common.core.config.CardValidConfig;
import com.neefull.common.core.oss.config.QiniuConfig;
import com.neefull.fsp.api.config.SmsConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
@MapperScan("com.neefull.fsp.api.mapper")
@EnableConfigurationProperties({QiniuConfig.class, SmsConfig.class, CardValidConfig.class})
public class AppServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppServiceApplication.class, args);
    }

}
