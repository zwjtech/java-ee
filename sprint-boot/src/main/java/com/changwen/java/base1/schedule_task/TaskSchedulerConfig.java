package com.changwen.java.base1.schedule_task;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author changwen on 2017/7/21.
 */
@Configurable
@ComponentScan("com.changwen.java.base1.schedule_task")
@EnableScheduling  // 开启对计划任务的支持
public class TaskSchedulerConfig {
}
