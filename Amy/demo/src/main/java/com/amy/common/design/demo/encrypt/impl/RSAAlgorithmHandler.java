package com.amy.common.design.demo.encrypt.impl;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.amy.common.design.demo.encrypt.AlgorithmHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RSAAlgorithmHandler implements AlgorithmHandler {

    @Value("${mybatis.encrypt.privateKey}")
    private String privateKey;

    @Value("${mybatis.encrypt.publicKey}")
    private String publicKey;

    private RSA rsa;

    @Override
    public String encryptStr(String password, String oldValue) {
        return rsa.encryptHex(oldValue, KeyType.PublicKey);
    }

    @Override
    public String decryptStr(String password, String oldValue) {
        return rsa.decryptStr(oldValue, KeyType.PrivateKey);
    }

    @PostConstruct
    public void initRSA() {
        this.rsa = new RSA(privateKey, publicKey);
    }
}

