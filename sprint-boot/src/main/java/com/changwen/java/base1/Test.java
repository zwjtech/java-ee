package com.changwen.java.base1;

import com.changwen.java.base1.event.DemoPublisher;
import com.changwen.java.base1.event.EventConfig;
import org.junit.After;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author changwen on 2017/7/18.
 */
public class Test {
    private static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(EventConfig.class);

    @After
    public void after() {
        context.close();
    }

    @org.junit.Test
    public void testEvent() {

        DemoPublisher publisher = context.getBean(DemoPublisher.class);
        publisher.publish("hello application event");
    }
}
