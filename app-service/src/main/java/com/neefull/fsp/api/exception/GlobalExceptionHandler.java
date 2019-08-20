package com.neefull.fsp.api.exception;

import com.neefull.common.core.entity.FebsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(BizException.class)
    public String exceptionHandle(Exception e) {
        log.info(e.getMessage());// 处理方法参数的异常类型
        return new FebsResponse().fail().message(e.getMessage()).toJson();
    }

    @ExceptionHandler(ErrorException.class)
    @ResponseBody
    public String runtimeHandle(Exception e) {
        log.info(e.getMessage());// 处理方法参数的异常类型
        return new FebsResponse().fail().message(e.getMessage()).toJson();
    }
}
