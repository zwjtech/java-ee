package com.changwen.java.base1.composit_annotion;

import org.springframework.stereotype.Service;

/**
 * @author changwen on 2017/7/21.
 */
@Service
public class DemoService {
    public void outputResult() {
        System.out.println("从组合注解配置照样获得的Bean");
    }

}
