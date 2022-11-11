package com.amy.common.core.annotation;

import com.amy.common.core.enumpkg.Algorithm;

import java.lang.annotation.*;

/**
 * @author xuqingxin
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface FieldEncrypt {

    String password() default "";

    Algorithm algorithm() default Algorithm.AES;
}
