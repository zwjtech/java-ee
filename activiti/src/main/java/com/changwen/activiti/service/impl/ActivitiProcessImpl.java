package com.changwen.activiti.service.impl;

import com.changwen.activiti.service.ActivitiProcess;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.zip.ZipInputStream;

/**
 * 一、流程定义
 *   1.部署流程定义（从classpath获取资源文件）
 *   2.部署流程定义（从zip）
 *   3.部署流程定义（从InputStream）
 *   4.获取所有的流程定义查询
 *   5.查询最新版本的流程定义
 *   6.查看流程图
 *   7.根据部署ID删除一个流程定义
 *   8.删除流程定义
 *
 * 二、流程实例
 *   1.动流程实例
 *   2.查询流程状态（判断流程正在执行，还是结束）
 *   3.查询当前人的个人任务
 *   4.完成我的任务
 *   5.查询指定人的历史任务
 *   6.查询历史流程实例
 * @author changwen on 2016/10/13.
 */
public class ActivitiProcessImpl implements ActivitiProcess {
    private  ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();


    /**
     * 部署流程定义涉及到的表
     * 1.流程部署表：act_re_deployment
     * 2.流程定义表：act_re_procdef
     * 3.资源文件表：act_ge_bytearray
     * 4.系统配置表：act_ge_property
     */

    /***************************************流程定义的CRUD***************************************/

    /**
     * 部署流程定义（从classpath获取资源文件）
     *
     * @param deployName 部署的名称
     * @param bpmnPath bpmn的路径
     * @param pngPath png的路径
     * @return 返回流程部署类，Deployment类对应数据表-->act_re_deployment
     */
    public Deployment deploymentProcessDefinition_classpath(String deployName, String bpmnPath, String pngPath){
        return processEngine.getRepositoryService()//与流程定义和部署对象相关的Service
                .createDeployment()//创建一个部署对象
                .name(deployName)//添加部署的名称
                .addClasspathResource(bpmnPath)//从classpath的资源中加载，一次只能加载一个文件
                .addClasspathResource(pngPath)//从classpath的资源中加载，一次只能加载一个文件
                .deploy();//完成部署
    }

    /**部署流程定义（从zip）*/
    public Deployment deploymentProcessDefinition_zip(String deployName, String zipPath){
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(zipPath);
        ZipInputStream zipInputStream = new ZipInputStream(in);

        return processEngine.getRepositoryService()//与流程定义和部署对象相关的Service
                .createDeployment()//创建一个部署对象
                .name(deployName)//添加部署的名称
                .addZipInputStream(zipInputStream)//指定zip格式的文件完成部署
                .deploy();//完成部署
    }

    /**部署流程定义（从InputStream）*/
    public Deployment deploymentProcessDefinition_inputStream(String deployName, String bpmnPath, String pngPath){
        InputStream inputStreambpmn = this.getClass().getResourceAsStream(bpmnPath);
        InputStream inputStreampng = this.getClass().getResourceAsStream(pngPath);

        return processEngine.getRepositoryService()//与流程定义和部署对象相关的Service
                .createDeployment()//创建一个部署对象
                .name(deployName)//添加部署的名称
                .addInputStream(spiltString(bpmnPath), inputStreambpmn)//使用资源文件的名称（要求：与资源文件的名称要一致），和输入流完成部署
                .addInputStream(spiltString(pngPath), inputStreampng)//使用资源文件的名称（要求：与资源文件的名称要一致），和输入流完成部署
                .deploy();//完成部署
    }

    /**
     * 获取所有的流程定义查询
     * 流程定义表: act_re_procdef
     */
    public ProcessDefinitionQuery findAllProcessDefinitionQuery(){
        return processEngine.getRepositoryService()//与流程定义和部署对象相关的Service
                .createProcessDefinitionQuery();
    }

    /***查询最新版本的流程定义*/
    public List<ProcessDefinition> findLastVersionProcessDefinition(){
        List<ProcessDefinition> list = processEngine.getRepositoryService()//
                .createProcessDefinitionQuery()//
                .orderByProcessDefinitionVersion().asc()//使用流程定义的版本升序排列
                .list();
        /*
         * Map<String,ProcessDefinition>
         map集合的key：流程定义的key
         map集合的value：流程定义的对象
         map集合的特点：当map集合key值相同的情况下，后一次的值将替换前一次的值
         */
        Map<String, ProcessDefinition> map = new LinkedHashMap<String, ProcessDefinition>();
        if(list!=null && list.size()>0){
            for(ProcessDefinition pd:list){
                map.put(pd.getKey(), pd);
            }
        }
        return new ArrayList<ProcessDefinition>(map.values());
    }

    /**查看流程图：资源文件表：act_ge_bytearray
     *  */
    public void viewPic(String deploymentId, String picPath) throws IOException {
        //获取图片资源名称
        List<String> list = processEngine.getRepositoryService()//
                .getDeploymentResourceNames(deploymentId);
        //定义图片资源的名称
        String resourceName = "";
        if(list!=null && list.size()>0){
            for(String name:list){
                if(name.indexOf(".png")>=0){
                    resourceName = name;
                }
            }
        }

        //获取图片的输入流
        InputStream in = processEngine.getRepositoryService()//
                .getResourceAsStream(deploymentId, resourceName);

        //将图片生成到D盘的目录下
//        File file = new File("D:/"+resourceName);
        File file = new File(picPath+resourceName);
        //将输入流的图片写到D盘下
        FileUtils.copyInputStreamToFile(in, file);
    }

    /**
     * 根据部署ID删除一个流程定义(act_re_procdef)
     * @param deploymentId 部署ID
     * @param isCascadeDelete 是否要级联删除，true为是
     *                        级联删除：不管流程是否启动，都能可以删除
     *                        不带级联的删除：只能删除没有启动的流程，如果流程启动，就会抛出异常
     */
    public boolean deleteProcessDefinition(String deploymentId, boolean isCascadeDelete){
        processEngine.getRepositoryService()//
                .deleteDeployment(deploymentId, isCascadeDelete);
        return true;
    }


    /**
     * 删除流程定义（删除key相同的所有不同版本的流程定义）
     * @param processDefinitionKey 流程定义的key
     */
    public void deleteProcessDefinitionByKey(String processDefinitionKey){
        //先使用流程定义的key查询流程定义，查询出所有的版本
        List<ProcessDefinition> list = processEngine.getRepositoryService()//
                .createProcessDefinitionQuery()//
                .processDefinitionKey(processDefinitionKey)//使用流程定义的key查询
                .list();
        //遍历，获取每个流程定义的部署ID
        if(list!=null && list.size()>0){
            for(ProcessDefinition pd:list){
                //获取部署ID
                String deploymentId = pd.getDeploymentId();
                processEngine.getRepositoryService()//
                        .deleteDeployment(deploymentId, true);
            }
        }
    }


    /**
     * 启动流程实例涉及到的表
     * 1.执行对象表: act_ru_execution
     * 2.身份联系表: act_ru_identitylink
     * 3.用户任务表: act_ru_task
     * 4.活动节点历史表: act_hi_actinst
     * 5.身份联系表 历史: act_hi_identitylink
     * 6.流程实例表 历史: act_hi_procinst
     * 7.历史任务表: act_hi_taskinst
     */
    //-------------------------------流程实例---------------------------------------

    /**
     * 启动流程实例：如果部署多个流程的processDefinitionKey一样，默认启动act_re_procdef表最新版本的流程定义启动
     *
     * @param processDefinitionKey 流流程定义的key，来自act_re_procdef
     * @return 返回流程实例，对应 act_ru_execution 这张表
     */
    public ProcessInstance startProcessInstance(String processDefinitionKey){
        return processEngine.getRuntimeService()//与正在执行的流程实例和执行对象相关的Service
                .startProcessInstanceByKey(processDefinitionKey);//使用流程定义的key启动流程实例，key来自act_re_procdef表，对应的helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动

    }

    /**
     * 查询流程状态（判断流程正在执行，还是结束）
     * @param processInstanceId 流程实例ID查询
     * @return true:流程已经结束, false 流程没有结束
     */
    public boolean isProcessEnd(String processInstanceId){
        ProcessInstance pi = processEngine.getRuntimeService()//表示正在执行的流程实例和执行对象
                .createProcessInstanceQuery()//创建流程实例查询
                .processInstanceId(processInstanceId)//使用流程实例ID查询:当流程结束后，流程实例在act_ru_execution被删除
                .singleResult();

        // true:流程已经结束, false 流程没有结束
        return pi == null;
    }

    /**
     * 查询当前人的个人任务：相当于查这张表act_ru_task
     * @param assignee 流程当前指定人
     * @return 返回任务相关信息，对应act_ru_task这张表
     */
    public List<Task>  findAssigneeTasks(String assignee){
        return processEngine.getTaskService()//与正在执行的任务管理相关的Service
                .createTaskQuery()//创建任务查询对象
                /*查询条件（where部分）*/
                .taskAssignee(assignee)//指定个人任务查询，指定办理人
//						.taskCandidateUser(candidateUser)//组任务的办理人查询
//						.processDefinitionId(processDefinitionId)//使用流程定义ID查询
//						.processInstanceId(processInstanceId)//使用流程实例ID查询
//						.executionId(executionId)//使用执行对象ID查询
                /*排序*/
//                .orderByTaskCreateTime().asc()//使用创建时间的升序排列
                /*返回结果集*/
//						.singleResult()//返回惟一结果集
//						.count()//返回结果集的数量
//						.listPage(firstResult, maxResults);//分页查询
                .list();//返回列表
    }

    /**
     * 完成我的任务
     * @param taskId  任务id 来自act_ru_task这张表的ID
     */
    public void completeAssigneeTask(String taskId ){
        processEngine.getTaskService()//与正在执行的任务管理相关的Service
                .complete(taskId);
        System.out.println("完成任务：任务ID："+taskId);
    }


    /**完成我的任务*/
    @Test
    public void completeAssigneeTask(String taskId , Map<String, Object> variables){
        //完成任务的同时，设置流程变量，使用流程变量用来指定完成任务后，下一个连线，对应sequenceFlow.bpmn文件中${message=='不重要'}
        processEngine.getTaskService()//与正在执行的任务管理相关的Service
                .complete(taskId,variables);
        System.out.println("完成任务：任务ID："+taskId);
    }

    /**查询指定人的历史任务：相当于查询这张表：act_hi_taskinst**/
    public List<HistoricTaskInstance> findHistoryTask(String taskAssignee){
        return processEngine.getHistoryService()//与历史数据（历史表）相关的Service
                .createHistoricTaskInstanceQuery()//创建历史任务实例查询
                .taskAssignee(taskAssignee)//指定历史任务的办理人
//                .orderByHistoricTaskInstanceStartTime().asc()
                .list();
    }

    /**查询历史流程实例，相当于查询这张表--》act_hi_procinst*/
    public HistoricProcessInstance findHistoryProcessInstance(String processInstanceId){
        return processEngine.getHistoryService()//与历史数据（历史表）相关的Service
                .createHistoricProcessInstanceQuery()//创建历史流程实例查询
                .processInstanceId(processInstanceId)//使用流程实例ID查询
                .orderByProcessInstanceStartTime().asc()
                .singleResult();
    }




//--------------------------------流程变量

    /**设置流程变量*/
    /**
     *
     * @param taskId 任务ID
     */
    public void setVariables(String taskId){
        //与任务（正在执行）
        TaskService taskService = processEngine.getTaskService();

        //一：设置流程变量，使用基本数据类型*/
//		taskService.setVariableLocal(taskId, "请假天数", 5);//与任务ID绑定
//		taskService.setVariable(taskId, "请假日期", new Date());
//		taskService.setVariable(taskId, "请假原因", "回家探亲，一起吃个饭");
        /**二：设置流程变量，使用javabean类型*/
        /**
         * 当一个javabean（实现序列号）放置到流程变量中，要求javabean的属性不能再发生变化
         *    * 如果发生变化，再获取的时候，抛出异常
         *
         * 解决方案：在Person对象中添加：
         * 		private static final long serialVersionUID = 6757393795687480331L;
         *      同时实现Serializable
         * */

    }

    /**获取流程变量*/
    @Test
    public void getVariables(){
        /**与任务（正在执行）*/
        TaskService taskService = processEngine.getTaskService();
        //任务ID
        String taskId = "2104";
        /**一：获取流程变量，使用基本数据类型*/
//		Integer days = (Integer) taskService.getVariable(taskId, "请假天数");
//		Date date = (Date) taskService.getVariable(taskId, "请假日期");
//		String resean = (String) taskService.getVariable(taskId, "请假原因");
//		System.out.println("请假天数："+days);
//		System.out.println("请假日期："+date);
//		System.out.println("请假原因："+resean);
        /**二：获取流程变量，使用javabean类型*/
//        Person p = (Person)taskService.getVariable(taskId, "人员信息(添加固定版本)");
//        System.out.println(p.getId()+"        "+p.getName());
    }


    /**模拟设置和获取流程变量的场景*/
    public void setAndGetVariables(){
        /**与流程实例，执行对象（正在执行）*/
        RuntimeService runtimeService = processEngine.getRuntimeService();
        /**与任务（正在执行）*/
        TaskService taskService = processEngine.getTaskService();

        /**设置流程变量*/
//		runtimeService.setVariable(executionId, variableName, value)//表示使用执行对象ID，和流程变量的名称，设置流程变量的值（一次只能设置一个值）
//		runtimeService.setVariables(executionId, variables)//表示使用执行对象ID，和Map集合设置流程变量，map集合的key就是流程变量的名称，map集合的value就是流程变量的值（一次设置多个值）

//		taskService.setVariable(taskId, variableName, value)//表示使用任务ID，和流程变量的名称，设置流程变量的值（一次只能设置一个值）
//		taskService.setVariables(taskId, variables)//表示使用任务ID，和Map集合设置流程变量，map集合的key就是流程变量的名称，map集合的value就是流程变量的值（一次设置多个值）

//		runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);//启动流程实例的同时，可以设置流程变量，用Map集合
//		taskService.complete(taskId, variables)//完成任务的同时，设置流程变量，用Map集合

        /**获取流程变量*/
//		runtimeService.getVariable(executionId, variableName);//使用执行对象ID和流程变量的名称，获取流程变量的值
//		runtimeService.getVariables(executionId);//使用执行对象ID，获取所有的流程变量，将流程变量放置到Map集合中，map集合的key就是流程变量的名称，map集合的value就是流程变量的值
//		runtimeService.getVariables(executionId, variableNames);//使用执行对象ID，获取流程变量的值，通过设置流程变量的名称存放到集合中，获取指定流程变量名称的流程变量的值，值存放到Map集合中

//		taskService.getVariable(taskId, variableName);//使用任务ID和流程变量的名称，获取流程变量的值
//		taskService.getVariables(taskId);//使用任务ID，获取所有的流程变量，将流程变量放置到Map集合中，map集合的key就是流程变量的名称，map集合的value就是流程变量的值
//		taskService.getVariables(taskId, variableNames);//使用任务ID，获取流程变量的值，通过设置流程变量的名称存放到集合中，获取指定流程变量名称的流程变量的值，值存放到Map集合中

    }


    /**查询流程变量的历史表，相当于查询act_hi_varinst*/
    public List<HistoricVariableInstance> findHistoryProcessVariables(String variableName){
        return processEngine.getHistoryService()//
                .createHistoricVariableInstanceQuery()//创建一个历史的流程变量查询对象
                .variableName(variableName)
                .list();
    }

    /**查询历史流程变量*/
    public List<HistoricVariableInstance>  findHistoryProcessVariables2(String processInstanceId){
        return processEngine.getHistoryService()//
                .createHistoricVariableInstanceQuery()//创建一个历史的流程变量查询对象
                .processInstanceId(processInstanceId)//
                .list();

    }






//-----------------------------历史---------------------
    /**查询历史活动：某一次流程的执行一共经历了多少个活动*/
    public List<HistoricActivityInstance> findHistoryActiviti(String processInstanceId){
         return processEngine.getHistoryService()//
                .createHistoricActivityInstanceQuery()//创建历史活动实例的查询
                .processInstanceId(processInstanceId)//
                .orderByHistoricActivityInstanceStartTime().asc()//
                .list();
    }

    /**查询历史任务:某一次流程的执行一共经历了多少个任务.相当于查询这张表：act_hi_taskinst*/
    public List<HistoricTaskInstance> findHistoryTaskByProcessInstanceId(String processInstanceId ){
         return processEngine.getHistoryService()//与历史数据（历史表）相关的Service
                .createHistoricTaskInstanceQuery()//创建历史任务实例查询
                .processInstanceId(processInstanceId)//
//                .orderByHistoricTaskInstanceStartTime().asc()
                .list();
    }

    /**查询历史流程变量:某一次流程的执行一共设置的流程变量*/
    public List<HistoricVariableInstance> findHistoryProcessVariablesByProcessInstanceId(String processInstanceId){
         return processEngine.getHistoryService()//
                .createHistoricVariableInstanceQuery()//创建一个历史的流程变量查询对象
                .processInstanceId(processInstanceId)//
                .list();
    }

    /**
     * 拆分字符串：用于获取资源文件的名称
     */
    private String spiltString(String souePath){
        //正则表达式判断是/(linux)还是\(windows)
        int len = souePath.split("/|\\\\").length;
        return souePath.split("/|\\\\")[len-1];
    }
}
