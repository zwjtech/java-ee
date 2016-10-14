package com.changwen.activiti.service;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author changwen on 2016/10/13.
 */
public interface ActivitiProcess {
    /**
     * 部署流程定义（从classpath获取资源文件）
     *
     * @param deployName 部署的名称
     * @param bpmnPath bpmn的路径
     * @param pngPath png的路径
     * @return 返回流程部署类，对应数据表-->act_re_deployment
     */
    Deployment deploymentProcessDefinition_classpath(String deployName, String bpmnPath, String pngPath);

    Deployment deploymentProcessDefinition_zip(String deployName, String zipPath);

    public Deployment deploymentProcessDefinition_inputStream(String deployName, InputStream inputStreambpmn,InputStream inputStreampng);

        /**
         * 获取流程定义查询
         */
    ProcessDefinitionQuery findProcessDefinitionQuery();

    /***附加功能：查询最新版本的流程定义*/
    List<ProcessDefinition> findLastVersionProcessDefinition();
    /**
     * 删除流程定义删除流程定义
     * @param deploymentId 部署ID
     * @param isCascadeDelete 是否要级联删除，true为是
     *                        级联删除：不管流程是否启动，都能可以删除
     *                        不带级联的删除：只能删除没有启动的流程，如果流程启动，就会抛出异常
     */
    void deleteProcessDefinition(String deploymentId, boolean isCascadeDelete);

    /**
     * 附加功能：删除流程定义（删除key相同的所有不同版本的流程定义）
     * @param processDefinitionKey 流程定义的key
     */
    void deleteProcessDefinitionByKey(String processDefinitionKey);

    /**查看流程图
     * @throws IOException */
    void viewPic(String deploymentId, String picPath) throws IOException;

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

    /**查询流程状态（判断流程正在执行，还是结束）*/
    public void isProcessEnd(String processInstanceId);

    /**查询历史任务（后面讲）*/
    public List<HistoricTaskInstance> findHistoryTask(String taskAssignee);

    public HistoricProcessInstance findHistoryProcessInstance(String processInstanceId);
}
