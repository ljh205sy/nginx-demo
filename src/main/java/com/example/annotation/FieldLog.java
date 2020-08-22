package com.example.annotation;

import java.lang.annotation.*;

/**
 * @Author: liujinhui
 * @Date: 2019/9/27 13:38
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.FIELD)
public @interface FieldLog {
    String value() default "";
}

