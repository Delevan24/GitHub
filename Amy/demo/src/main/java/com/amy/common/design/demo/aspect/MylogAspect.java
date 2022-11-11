package com.amy.common.design.demo.aspect;

import com.amy.common.design.demo.annotation.MyAnnotation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Aspect;

/**
 * @author xuqingxin
 */
@Component
@Aspect
public class MylogAspect {
    private static final Logger logger = LoggerFactory.getLogger(MylogAspect.class);

    /**
     * 只要用到了com.huangting.annotation.Aop.MyLog这个注解的，就是目标类
     */
    @Pointcut("@annotation(com.amy.common.design.demo.annotation.MyAnnotation)")
    private void MyValid() {
    }

    @Before("MyValid()")
    public void before(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        MyAnnotation myLog = signature.getMethod().getAnnotation(MyAnnotation.class);
        System.out.println("[" + signature.getName() + " : start.....]");
        System.out.println("【目标对象方法被调用时候产生的日志，记录到日志表中】：" + myLog.desc());
    }
}
