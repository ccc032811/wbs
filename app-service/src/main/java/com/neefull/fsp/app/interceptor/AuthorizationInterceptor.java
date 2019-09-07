package com.neefull.fsp.app.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.neefull.fsp.app.annotation.AuthToken;
import com.neefull.fsp.app.config.AppConstant;
import com.neefull.fsp.app.utils.RedisUtil;
import com.neefull.fsp.common.util.AuthUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;

@Slf4j
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {
    @Autowired
    RedisUtil redisUtil;
    Logger log = LoggerFactory.getLogger(AuthorizationInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //解析请求参数，token
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        boolean result = true;
        // 如果打上了AuthToken注解则需要验证token
        if (method.getAnnotation(AuthToken.class) != null || handlerMethod.getBeanType().getAnnotation(AuthToken.class) != null) {
            //解析Token
            String token = request.getHeader("auth_neefull");
            log.debug("Get token from request is {} ", token);
            JSONObject jsonObject = new JSONObject();
            PrintWriter out = null;
            if (StringUtils.isEmpty(token)) {
                jsonObject.put("code", "600");
                jsonObject.put("msg", "Token参数不存在");
                jsonObject.put("data", "");
                out = response.getWriter();
                response.setCharacterEncoding("utf-8");
                response.setContentType("application/json; charset=utf-8");
                out.println(jsonObject);
                return false;
            }
            long userId = AuthUtils.decryptPid(token, AppConstant.AES_KEY);
            //校验是否存在
            String key = "login" + userId;
            String tokenVo = (String) redisUtil.get(key);
            try {
                if (StringUtils.isEmpty(tokenVo) || !token.equals(tokenVo)) {
                    jsonObject.put("code", "600");
                    jsonObject.put("msg", "Token校验未通过");
                    jsonObject.put("data", "");
                    out = response.getWriter();
                    response.setCharacterEncoding("utf-8");
                    response.setContentType("application/json; charset=utf-8");
                    out.println(jsonObject);
                    result = false;
                } else {
                    request.setAttribute("userId", userId);
                    result = true;
                }
            } catch (Exception e) {
                throw new Exception("权限校验失败");
            } finally {
                if (null != out) {
                    out.flush();
                    out.close();
                }
            }

        }

        return result;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
