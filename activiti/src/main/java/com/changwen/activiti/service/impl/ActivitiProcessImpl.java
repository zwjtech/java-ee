package com.changwen.activiti.service.impl;

import com.changwen.activiti.service.ActivitiProcess;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.List;

/**
 * @author changwen on 2016/10/13.
 */
public class ActivitiProcessImpl implements ActivitiProcess {

    private static ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    /**部署流程定义*/
    public void deploymentProcessDefinition(String processName, String bpmnPath, String pngPath){
        Deployment deployment = processEngine.getRepositoryService()//与流程定义和部署对象相关的Service
                .createDeployment()//创建一个部署对象
                .name(processName)//添加部署的名称
                .addClasspathResource(bpmnPath)//从classpath的资源中加载，一次只能加载一个文件
                .addClasspathResource(pngPath)//从classpath的资源中加载，一次只能加载一个文件
                .deploy();//完成部署
        System.out.println("部署ID："+deployment.getId());//1
        System.out.println("部署名称："+deployment.getName());//helloworld入门程序
    }


    /**
     * 启动流程实例
     * @param processDefinitionKey 流程定义的key
     */
    public ProcessInstance startProcessInstance(String processDefinitionKey){
        ProcessInstance pi = processEngine.getRuntimeService()//与正在执行的流程实例和执行对象相关的Service
                .startProcessInstanceByKey(processDefinitionKey);//使用流程定义的key启动流程实例，key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
        System.out.println("流程实例ID:"+pi.getId());//流程实例ID    101
        System.out.println("流程定义ID:"+pi.getProcessDefinitionId());//流程定义ID   helloworld:1:4
        return  pi;
    }

    /**查询当前人的个人任务*/
    public List<Task>  findMyPersonalTask(String assignee){
        return processEngine.getTaskService()//与正在执行的任务管理相关的Service
                .createTaskQuery()//创建任务查询对象
                .taskAssignee(assignee)//指定个人任务查询，指定办理人
                .list();
    }

    /**
     * 完成我的任务
     * @param taskId  任务id
     */
    public void completeMyPersonalTask(String taskId ){
        processEngine.getTaskService()//与正在执行的任务管理相关的Service
                .complete(taskId);
        System.out.println("完成任务：任务ID："+taskId);
    }
}
