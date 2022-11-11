package com.amy.common.core.annotation;

import java.lang.annotation.*;

/**
 * 需要加解密的类注解
 *
 * @author xuqingxin
 */
@Documented
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EncryptDecryptClass {
}
