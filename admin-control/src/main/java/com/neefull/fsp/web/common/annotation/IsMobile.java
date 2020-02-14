package com.neefull.fsp.web.common.annotation;

import com.neefull.fsp.web.common.validator.MobileValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author pei.wang
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MobileValidator.class)
public @interface IsMobile {

    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
