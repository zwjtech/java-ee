package com.changwen.java.base1.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 2.事件监听器
 * @author changwen on 2017/7/18.
 */
@Component
public class DemoListener implements ApplicationListener<DemoEvent> {

    // 该方法对消息进行接受处理
    public void onApplicationEvent(DemoEvent demoEvent) {
        String msg = demoEvent.getMsg();

        System.out.println("我DemoListener 接收到了 demoPublisher发布的消息：" + msg);
    }
}
