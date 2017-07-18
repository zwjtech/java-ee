package com.changwen.java.base1.aopTest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 4。编写切面
 * @author changwen on 2017/7/18.
 */
@Aspect
@Component
public class LogAspect {

    // 这是注解式拦截, 必须为全称
    @Pointcut("@annotation(com.changwen.java.base1.aopTest.Action)")
    public void annotationPointCut(){}

    @After("annotationPointCut()")
    public void after(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Action action = method.getAnnotation(Action.class);
        System.out.println("注解式拦截-->：" + action.name());
    }

    // ---------------------------------
    // 方法规格拦截
    @Pointcut("execution(* com.changwen.java.base1.aopTest.DemoMethodService.*(..))")
    public void methodPointCut() {}

    @Before("methodPointCut()")
    public void before(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        System.out.println("方法规则式拦截：" + method.getName());
    }

}
