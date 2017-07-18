package com.changwen.java.base1.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 3.使用容器发布事件
 * @author changwen on 2017/7/18.
 */
@Component
public class DemoPublisher {
    @Autowired
    ApplicationContext applicationContext;

    public void publish(String msg) {
        // 发布
        applicationContext.publishEvent(new DemoEvent(this, msg));
    }
}
