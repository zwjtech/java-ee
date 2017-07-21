package com.changwen.java.base1.composit_annotion;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;

import java.lang.annotation.*;

/**
 * @author changwen on 2017/7/21.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Configurable
@ComponentScan
public @interface WiselyConfiguration {
    String[] value() default {};  // 覆盖value参数
}
