package com.changwen.java.base1.event;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author changwen on 2017/7/18.
 */
public class Main {
    /**
     * Spring的事件需要遵循如下流程：
     * 1.自定义事件，继承ApplicationEvent
     * 2.定义事件监听器，实现ApplicationListener
     * 3.使用容器发布事件。
     * @param args
     */
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(EventConfig.class);

        DemoPublisher publisher = context.getBean(DemoPublisher.class);
        publisher.publish("hello application event");

        context.close();
    }
}
