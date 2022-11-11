package com.amy.common.core.annotation;

import java.lang.annotation.*;

/**
 * 加密字段注解
 *
 * @author xuqingxin
 */
@Documented
@Inherited
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EncryptDecryptField {

}