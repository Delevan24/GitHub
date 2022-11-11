package com.amy.common.design.demo.encrypt;

public interface AlgorithmHandler {

    /**
     * 加密字符串
     *
     * @param password
     * @param oldValue
     *
     * @return
     */
    String encryptStr(String password, String oldValue);

    /**
     * 解密字符串
     *
     * @param password
     * @param oldValue
     *
     * @return
     */
    String decryptStr(String password, String oldValue);
}

