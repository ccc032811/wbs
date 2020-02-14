package com.neefull.fsp.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author pei.wang
 */
@SpringBootApplication
@EnableAsync
@EnableTransactionManagement
@MapperScan("com.neefull.fsp.web.*.mapper")
@ComponentScan(basePackages = {"com.neefull.fsp"})
public class AdminApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(AdminApplication.class).run(args);
    }

}
