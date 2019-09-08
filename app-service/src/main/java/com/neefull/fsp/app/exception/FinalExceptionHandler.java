package com.neefull.fsp.app.exception;

import com.neefull.fsp.common.entity.FebsResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class FinalExceptionHandler implements ErrorController {
    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping(value = "/error")
    public String error(HttpServletResponse resp, HttpServletRequest req) {
        // 错误处理逻辑
        return new FebsResponse().error().message("请求地址不存在或者其他未知异常").data(null).toJson();
    }
}
