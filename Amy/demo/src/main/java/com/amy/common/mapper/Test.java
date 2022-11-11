package com.amy.common.mapper;

import com.amy.common.core.util.JsonUtils;
import com.google.common.collect.Maps;

public class Test {
    public static void main(String[] args) {

        String s = JsonUtils.toJsonString(Maps.newHashMap());
        System.out.println(s);
    }
}
