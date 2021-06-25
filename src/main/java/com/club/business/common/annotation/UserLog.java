package com.club.business.common.annotation;

import java.lang.annotation.*;

/**
 * 用户操作日志注解拦截(用于controller)
 *
 * @author Tom
 * @date 2019-12-11
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.METHOD})
public @interface UserLog {
    String description() default "";
}