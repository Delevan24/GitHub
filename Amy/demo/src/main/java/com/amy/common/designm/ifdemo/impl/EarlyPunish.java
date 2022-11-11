package com.amy.common.designm.ifdemo.impl;

import com.amy.common.designm.ifdemo.IPunish;
import com.amy.common.designm.ifdemo.PunishFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author xuqingxin
 */
@Service
public class EarlyPunish implements IPunish, InitializingBean {
    @Override
    public void exePunish() {
        System.out.println("警告");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        PunishFactory.registerPunish("early", this);
    }
}
