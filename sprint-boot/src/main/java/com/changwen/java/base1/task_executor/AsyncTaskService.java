package com.changwen.java.base1.task_executor;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author changwen on 2017/7/21.
 */
@Service
public class AsyncTaskService {

    /* 通过@Async，注解表明该方法是个异步方法，如果注解在类级别，则表明类所有的方法都是异步方法
     * 而这里的方法自动被注入使用ThreadPoolTaskExecutor作为TaskExecutor */
    @Async
    public void executeAsyncTask(Integer i) {
        System.out.println("执行异步任务：-->" + i);
    }

    @Async
    public void executeAsyncTaskPlus(Integer i) {
        System.out.println("执行异步任务 +1：" + (i + 1));
    }
}
