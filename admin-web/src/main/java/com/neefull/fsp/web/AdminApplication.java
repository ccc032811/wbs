package com.neefull.fsp.web;



import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author pei.wang
 */

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableAsync
@EnableTransactionManagement
@MapperScan("com.neefull.fsp.web.*.mapper")
@ComponentScan(basePackages = {"com.neefull.fsp"})
public class AdminApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class,args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AdminApplication.class);
    }


}
