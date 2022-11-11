package com.amy.common.design;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author xuqingxin
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class WorkPunish {
    @Test
    public void test() {
        String state = "late";
        punish(state);
    }

    //重构后的处罚逻辑
    public void punish(String state) {
        //静态工厂类获取处罚对象
        IPunish punish = PunishFactory.getPunish(state);
        // 执行处罚逻辑
        punish.exePunish();
    }

}
