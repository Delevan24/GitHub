package com.amy.common.design.demo.encrypt.impl;

import com.amy.common.core.util.EncryptDecryptUtils;
import com.amy.common.design.demo.encrypt.IEncryptDecrypt;

import java.lang.reflect.Field;

/**
 * @author xuqingxin
 */
public class EncryptDecryptImpl implements IEncryptDecrypt {
    @Override
    public <T> T encrypt(Field[] declaredFields, T parameterObject) throws IllegalAccessException {
        return EncryptDecryptUtils.encrypt(declaredFields, parameterObject);
    }

    @Override
    public <T> T decrypt(T result) throws IllegalAccessException {
        return EncryptDecryptUtils.decrypt(result);
    }
}
