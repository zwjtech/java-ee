package com.changwen.java.base1.aopTest;

import java.lang.annotation.*;

/**
 * 1.定义注解
 * 注解本身是没有功能的，类似xml一样都是元数据。元数据即解释数据的数据，就是配置
 * @author changwen on 2017/7/18.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented  // Documented 注解表明这个注解应该被 javadoc工具记录. 默认情况下,javadoc是不包括注解的
public @interface Action {
    String name();
}
