package com.neefull.fsp.web;




import com.neefull.fsp.web.sms.utils.ScanComment;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
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
public class WBSApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(WBSApplication.class,args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WBSApplication.class);
    }




}
