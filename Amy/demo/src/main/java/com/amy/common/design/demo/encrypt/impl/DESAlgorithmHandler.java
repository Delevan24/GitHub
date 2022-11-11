package com.amy.common.design.demo.encrypt.impl;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.DES;
import com.amy.common.design.demo.encrypt.AlgorithmHandler;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DESAlgorithmHandler implements AlgorithmHandler {

    @Value("${mybatis.encrypt.password}")
    private String password;

    @Override
    public String encryptStr(String password, String oldValue) {
        DES des;
        if (StringUtils.isNotBlank(password)) {
            des = SecureUtil.des(password.getBytes());
        } else {
            des = SecureUtil.des(this.password.getBytes());
        }
        return des.encryptHex(oldValue);
    }

    @Override
    public String decryptStr(String password, String oldValue) {
        DES des;
        if (StringUtils.isNotBlank(password)) {
            des = SecureUtil.des(password.getBytes());
        } else {
            des = SecureUtil.des(this.password.getBytes());
        }
        return des.decryptStr(oldValue);
    }
}

