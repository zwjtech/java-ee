package com.changwen.java.base1.aopTest;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 5.配置类
 * @author changwen on 2017/7/18.
 */
@Configurable
@ComponentScan("com.changwen.java")
@EnableAspectJAutoProxy   // 该注解开启spring 对AspectJ的支持
public class AopConfig {
}
