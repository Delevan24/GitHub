package com.amy.common.designm.ifdemo;

/**
 * @author xuqingxin
 */
public class WorkPunish {
    public static void main(String[] agrs) {
        String state = "late";
        punish(state);
    }

    //重构后的处罚逻辑
    public static void punish(String state) {
        //静态工厂类获取处罚对象
        IPunish punish = PunishFactory.getPunish(state);
        // 执行处罚逻辑
        punish.exePunish();
    }

}
