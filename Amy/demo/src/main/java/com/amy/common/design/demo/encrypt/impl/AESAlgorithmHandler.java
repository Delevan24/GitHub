package com.amy.common.design.demo.encrypt.impl;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import com.amy.common.design.demo.encrypt.AlgorithmHandler;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AESAlgorithmHandler implements AlgorithmHandler {

    // @Value("${mybatis.encrypt.password}")
    private String password;

    @Override
    public String encryptStr(String password, String oldValue) {
        AES aes;
        if (StringUtils.isNotBlank(password)) {
            aes = SecureUtil.aes(password.getBytes());
        } else {
            aes = SecureUtil.aes(this.password.getBytes());
        }
        return aes.encryptHex(oldValue);
    }

    @Override
    public String decryptStr(String password, String oldValue) {
        AES aes;
        if (StringUtils.isNotBlank(password)) {
            aes = SecureUtil.aes(password.getBytes());
        } else {
            aes = SecureUtil.aes(this.password.getBytes());
        }
        return aes.decryptStr(oldValue);
    }

    public static void main(String[] args) {

        String a = "18311008178";
        String sey = ".O[3FWPV8)7J&XhW";

        AES aes = SecureUtil.aes(sey.getBytes());
        String s = aes.encryptBase64(a);
        System.out.println(s);
    }
}

