package com.neefull.fsp.web.common.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author pei.wang
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface FebsEndPoint {
    @AliasFor(annotation = Component.class)
    String value() default "";
}
