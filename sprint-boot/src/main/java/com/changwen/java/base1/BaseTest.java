package com.changwen.java.base1;

import com.changwen.java.base1.aopTest.AopConfig;
import com.changwen.java.base1.aopTest.DemoAnnotationService;
import com.changwen.java.base1.aopTest.DemoMethodService;
import com.changwen.java.base1.el.ELConfig;

import com.changwen.java.base1.event.DemoPublisher;
import com.changwen.java.base1.event.EventConfig;
import com.changwen.java.base1.spring_aware.AwareConfig;
import com.changwen.java.base1.spring_aware.AwareService;
import org.junit.After;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

/**
 * @author changwen on 2017/7/18.
 */
public class BaseTest {
    private static AnnotationConfigApplicationContext context;

    @After
    public void after() {
        context.close();
    }

    /**
     * Spring Aware的目的是为了让Bean获得Spring容器的服务。
     * 因为ApplicationContext接口集成了MessageSource接口、ApplicationEventPublisher接口和ResourceLoader接口，
     * 所以Bean继承ApplicationContextAware可以获得Spring容器的所有服务，但原则上我们还是用到什么接口，就实现什么接口。
     */
    @org.junit.Test
    public void testSpringAware() throws IOException {
        context = new AnnotationConfigApplicationContext(AwareConfig.class);

        AwareService awareService = context.getBean(AwareService.class);
        awareService.outputResult();

//        Bean 的名称：awareService
//        ResourceLoader加载的文件内容为：123
    }

    // -------------------------------------------
    /**
     * Spring的事件需要遵循如下流程：
     * 1.自定义事件，继承ApplicationEvent
     * 2.定义事件监听器，实现ApplicationListener
     * 3.使用容器发布事件。
     */
    @org.junit.Test
    public void testEvent() {
        context = new AnnotationConfigApplicationContext(EventConfig.class);

        DemoPublisher publisher = context.getBean(DemoPublisher.class);
        publisher.publish("hello application event");

        // 我DemoListener 接收到了 demoPublisher发布的消息：hello application event
    }

    @org.junit.Test
    public void testEL() {
        context = new AnnotationConfigApplicationContext(ELConfig.class);
        ELConfig elConfig = context.getBean(ELConfig.class);
        elConfig.outputResource();
    }

    @org.junit.Test
    public void testAopTest() {
        context = new AnnotationConfigApplicationContext(AopConfig.class);

        DemoAnnotationService demoAnnotationService = context.getBean(DemoAnnotationService.class);
        demoAnnotationService.add();

        DemoMethodService methodService = context.getBean(DemoMethodService.class);
        methodService.add();

        //    注解式拦截：注解式拦截的add操作
        //    方法规则式拦截：add
    }
}
