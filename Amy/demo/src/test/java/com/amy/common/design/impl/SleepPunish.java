package com.amy.common.design.impl;

import com.amy.common.designm.ifdemo.IPunish;
import com.amy.common.designm.ifdemo.PunishFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @author xuqingxin
 */
@Component
public class SleepPunish implements IPunish, InitializingBean {
    @Override
    public void exePunish() {
        System.out.println("罚100");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        PunishFactory.registerPunish("sleep", this);
    }
}
