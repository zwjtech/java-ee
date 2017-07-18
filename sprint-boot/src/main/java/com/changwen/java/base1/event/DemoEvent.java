package com.changwen.java.base1.event;

import org.springframework.context.ApplicationEvent;

/**
 * 1.自定义事件
 * @author changwen on 2017/7/18.
 */
public class DemoEvent extends ApplicationEvent {
    private String msg;

    public DemoEvent(Object source, String msg) {
        super(source);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
