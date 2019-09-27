package com.neefull.fsp.app;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.neefull.fsp.common.config.CardValidConfig;
import com.neefull.fsp.common.config.QiniuConfig;
import com.neefull.fsp.common.config.SmsConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
@MapperScan("com.neefull.fsp.*.mapper")
@EnableConfigurationProperties({QiniuConfig.class,
        SmsConfig.class,
        CardValidConfig.class})
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.neefull.fsp"})
@EnableAsync
public class AppServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppServiceApplication.class, args);
    }

}
