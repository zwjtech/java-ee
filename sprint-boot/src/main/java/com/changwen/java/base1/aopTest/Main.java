package com.changwen.java.base1.aopTest;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author changwen on 2017/7/18.
 */
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AopConfig.class);

        DemoAnnotationService demoAnnotationService = context.getBean(DemoAnnotationService.class);
        demoAnnotationService.add();

        DemoMethodService methodService = context.getBean(DemoMethodService.class);
        methodService.add();

        context.close();
    }

//    注解式拦截：注解式拦截的add操作
//    方法规则式拦截：add
}
