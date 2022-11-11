package com.amy.common.designm.ifdemo;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xuqingxin
 */
public class PunishFactory {
    private static Map<String, IPunish> punishMap = new HashMap<>();

    private PunishFactory() {
    }

    private static final IPunish EMPTY = new EmptyPunish();

    //获取
    public static IPunish getPunish(String state) {
        IPunish result = punishMap.get(state);
        return result == null ? EMPTY : result;
    }

    //将处罚对象注册到这里
    public static void registerPunish(String state, IPunish o) {
        punishMap.put(state, o);
    }

    private static class EmptyPunish implements IPunish {
        @Override
        public void exePunish() {
            // Empty class
        }
    }
}
