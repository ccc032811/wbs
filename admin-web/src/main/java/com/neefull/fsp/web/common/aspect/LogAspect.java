package com.neefull.fsp.web.common.aspect;

import com.alibaba.fastjson.JSON;
import com.neefull.fsp.web.common.properties.FebsProperties;
import com.neefull.fsp.web.common.utils.HttpContextUtil;
import com.neefull.fsp.web.common.utils.IPUtil;
import com.neefull.fsp.web.monitor.entity.Log;
import com.neefull.fsp.web.monitor.service.ILogService;
import com.neefull.fsp.web.system.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * AOP 记录用户操作日志
 *
 * @author pei.wang
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    @Autowired
    private FebsProperties febsProperties;

    @Autowired
    private ILogService logService;

    @Pointcut("@annotation(com.neefull.fsp.web.common.annotation.Log)")
    public void pointcut() {
        // do nothing
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result;
        long beginTime = System.currentTimeMillis();
        // 执行方法
        result = point.proceed();
        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        if (febsProperties.isOpenAopLog()) {
            // 保存日志
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            Log log = new Log();
            if (user != null)
                log.setUsername(user.getUsername());
            log.setResponse(JSON.toJSONString(result));
            log.setTime(time);
            logService.saveLog(point, log);
        }
        return result;
    }
}
