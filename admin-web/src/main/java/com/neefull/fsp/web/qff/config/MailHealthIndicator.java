package com.neefull.fsp.web.qff.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * mail健康检查
 * @Author: chengchengchu
 * @Date: 2019/12/13  15:24
 */

@Component
public class MailHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        int errorCode = check();
        if (errorCode != 0) {
            return Health.down().withDetail("Error Code", errorCode).build();
        }
        return Health.up().build();
    }

    int check(){

        return 0;
    }
}
