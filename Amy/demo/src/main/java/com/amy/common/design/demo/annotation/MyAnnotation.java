package com.amy.common.design.demo.annotation;

import java.lang.annotation.*;

/**
 * @author xuqingxin
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {
    String desc();
}
