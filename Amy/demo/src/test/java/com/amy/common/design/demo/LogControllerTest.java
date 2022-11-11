package com.amy.common.design.demo;

import com.amy.common.design.demo.controller.LogController;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class LogControllerTest extends BaseTestCase {

    @Autowired(required = true)
    private LogController logController;

    @Test
    public void testLogAspect() {
        logController.testLogAspect();
    }
}
