package com.amy.common.design.demo.controller;

import com.amy.common.design.demo.annotation.MyAnnotation;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

/**
 * @author xuqingxin
 */
@Component
@Controller
public class LogController {

    @MyAnnotation(desc = "这是结合spring aop知识，讲解自定义注解应用的一个案例")
    public void testLogAspect() {
        System.out.println("墙头马上遥相顾");
    }
}