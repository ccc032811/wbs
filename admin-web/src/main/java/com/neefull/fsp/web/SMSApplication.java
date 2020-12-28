package com.neefull.fsp.web;




import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author pei.wang
 */

@SpringBootApplication
@EnableAsync
@EnableTransactionManagement
@MapperScan("com.neefull.fsp.web.*.mapper")
@ComponentScan(basePackages = {"com.neefull.fsp"})
public class SMSApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SMSApplication.class,args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SMSApplication.class);
    }


}
