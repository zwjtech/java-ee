package com.changwen.activiti.service;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.List;

/**
 * @author changwen on 2016/10/13.
 */
public interface ActivitiProcess {
    //部署流程定义
    void deploymentProcessDefinition(String processName, String bpmnPath, String pngPath);
 //   Deployment deploymentProcessDefinition(String processName, String bpmnPath, String pngPath);

    /**
     * 启动流程实例
     * @param processDefinitionKey 流程定义的key
     */
    ProcessInstance startProcessInstance(String processDefinitionKey);

    /**查询当前人的个人任务*/
    List<Task> findMyPersonalTask(String assignee);

    /**
     * 完成我的任务
     * @param taskId  任务id
     */
    void completeMyPersonalTask(String taskId );
}
