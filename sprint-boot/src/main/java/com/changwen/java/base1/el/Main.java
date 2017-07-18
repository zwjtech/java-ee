package com.changwen.java.base1.el;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author changwen on 2017/7/18.
 */
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ELConfig.class);
        ELConfig elConfig = context.getBean(ELConfig.class);
        elConfig.outputResource();
        context.close();
    }
}
