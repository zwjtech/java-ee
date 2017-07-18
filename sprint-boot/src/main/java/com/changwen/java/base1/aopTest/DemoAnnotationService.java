package com.changwen.java.base1.aopTest;

import org.springframework.stereotype.Service;

/**
 * 2.编写使用注解的被拦截类
 * @author changwen on 2017/7/18.
 */
@Service
public class DemoAnnotationService {

    @Action(name="注解式拦截的add操作")
    public void add(){
    }
}
