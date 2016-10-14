package com.changwen.activiti.service.impl;

import com.changwen.activiti.service.ActivitiProcess;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

/**
 * @author changwen on 2016/10/13.
 */
public class ActivitiProcessImpl implements ActivitiProcess {

    private  ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    /**
     * 部署流程定义（从classpath获取资源文件）
     *
     * @param deployName 部署的名称
     * @param bpmnPath bpmn的路径
     * @param pngPath png的路径
     * @return 返回流程部署类，对应数据表-->act_re_deployment
     */
    public Deployment deploymentProcessDefinition_classpath(String deployName, String bpmnPath, String pngPath){
        Deployment deployment = processEngine.getRepositoryService()//与流程定义和部署对象相关的Service
                .createDeployment()//创建一个部署对象
                .name(deployName)//添加部署的名称
                .addClasspathResource(bpmnPath)//从classpath的资源中加载，一次只能加载一个文件
                .addClasspathResource(pngPath)//从classpath的资源中加载，一次只能加载一个文件
                .deploy();//完成部署
        System.out.println("部署ID："+deployment.getId());//1
        System.out.println("部署名称："+deployment.getName());//helloworld入门程序
        return deployment;
    }

    /**部署流程定义（从zip）*/
    public Deployment deploymentProcessDefinition_zip(String deployName, String zipPath){
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(zipPath);
        ZipInputStream zipInputStream = new ZipInputStream(in);

        Deployment deployment = processEngine.getRepositoryService()//与流程定义和部署对象相关的Service
                .createDeployment()//创建一个部署对象
                .name(deployName)//添加部署的名称
                .addZipInputStream(zipInputStream)//指定zip格式的文件完成部署
                .deploy();//完成部署
        System.out.println("部署ID："+deployment.getId());//
        System.out.println("部署名称："+deployment.getName());//
        return deployment;
    }

    /**部署流程定义（从InputStream）*/
    public Deployment deploymentProcessDefinition_inputStream(String deployName, String bpmnPath, String pngPath){
        InputStream inputStreambpmn = this.getClass().getResourceAsStream("/diagrams/processVariables.bpmn");
        InputStream inputStreampng = this.getClass().getResourceAsStream("/diagrams/processVariables.png");
        Deployment deployment = processEngine.getRepositoryService()//与流程定义和部署对象相关的Service
                .createDeployment()//创建一个部署对象
                .name("流程定义")//添加部署的名称
                .addInputStream("processVariables.bpmn", inputStreambpmn)//使用资源文件的名称（要求：与资源文件的名称要一致），和输入流完成部署
                .addInputStream("processVariables.png", inputStreampng)//使用资源文件的名称（要求：与资源文件的名称要一致），和输入流完成部署
                .deploy();//完成部署
        System.out.println("部署ID："+deployment.getId());//
        System.out.println("部署名称："+deployment.getName());//
        return deployment;
    }

    /**
     * 获取流程定义查询
     * @return
     */
    public ProcessDefinitionQuery findProcessDefinitionQuery(){
        ProcessDefinitionQuery processDefinitionQuery = processEngine.getRepositoryService()//与流程定义和部署对象相关的Service
                .createProcessDefinitionQuery();
        return processDefinitionQuery;
    }

    /***附加功能：查询最新版本的流程定义*/
    public List<ProcessDefinition> findLastVersionProcessDefinition(){
        List<ProcessDefinition> list = processEngine.getRepositoryService()//
                .createProcessDefinitionQuery()//
                .orderByProcessDefinitionVersion().asc()//使用流程定义的版本升序排列
                .list();
        /**
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
    /**
     * 删除流程定义删除流程定义
     * @param deploymentId 部署ID
     * @param isCascadeDelete 是否要级联删除，true为是
     *                        级联删除：不管流程是否启动，都能可以删除
     *                        不带级联的删除：只能删除没有启动的流程，如果流程启动，就会抛出异常
     */
    public void deleteProcessDefinition(String deploymentId, boolean isCascadeDelete){
        processEngine.getRepositoryService()//
                .deleteDeployment(deploymentId, isCascadeDelete);
        System.out.println("删除成功！");
    }


    /**
     * 附加功能：删除流程定义（删除key相同的所有不同版本的流程定义）
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


    /**查看流程图
     * @throws IOException */
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
    //----------------------------------------------------------------------
    /**
     * 启动流程实例
     * @param processDefinitionKey 流流程定义的key，来自act_re_procdef
     * @return 返回流程实例，对应 act_ru_execution 这张表
     */
    public ProcessInstance startProcessInstance(String processDefinitionKey){
        ProcessInstance pi = processEngine.getRuntimeService()//与正在执行的流程实例和执行对象相关的Service
                .startProcessInstanceByKey(processDefinitionKey);//使用流程定义的key启动流程实例，key来自act_re_procdef表，对应的helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
        // 对应 act_ru_execution这张表
        System.out.println("流程实例ID:"+pi.getId());//流程实例ID    101
        System.out.println("流程定义ID:"+pi.getProcessDefinitionId());//流程定义ID   helloworld:1:4
        return  pi;
    }

    /**
     * 查询当前人的个人任务
     * @param assignee 流程当前人
     * @return 返回任务相关信息，对应act_ru_task这张表
     */
    public List<Task>  findMyPersonalTask(String assignee){
        return processEngine.getTaskService()//与正在执行的任务管理相关的Service
                .createTaskQuery()//创建任务查询对象
                .taskAssignee(assignee)//指定个人任务查询，指定办理人
                .list();
    }

    /**
     * 完成我的任务
     * @param taskId  任务id 来自act_ru_task这张表的ID
     */
    public void completeMyPersonalTask(String taskId ){
        processEngine.getTaskService()//与正在执行的任务管理相关的Service
                .complete(taskId);
        System.out.println("完成任务：任务ID："+taskId);
    }


    /**查询流程状态（判断流程正在执行，还是结束）*/
    public void isProcessEnd(String processInstanceId){
        ProcessInstance pi = processEngine.getRuntimeService()//表示正在执行的流程实例和执行对象
                .createProcessInstanceQuery()//创建流程实例查询
                .processInstanceId(processInstanceId)//使用流程实例ID查询
                .singleResult();
        if(pi==null){
            System.out.println("流程已经结束");
        }
        else{
            System.out.println("流程没有结束");
        }
    }

    /**查询历史任务（后面讲）*/
    public List<HistoricTaskInstance> findHistoryTask(String taskAssignee){
        return processEngine.getHistoryService()//与历史数据（历史表）相关的Service
                .createHistoricTaskInstanceQuery()//创建历史任务实例查询
                .taskAssignee(taskAssignee)//指定历史任务的办理人
                .list();
    }

    /**查询历史流程实例（后面讲）*/
    public HistoricProcessInstance findHistoryProcessInstance(String processInstanceId){
        return processEngine.getHistoryService()//与历史数据（历史表）相关的Service
                .createHistoricProcessInstanceQuery()//创建历史流程实例查询
                .processInstanceId(processInstanceId)//使用流程实例ID查询
                .singleResult();
    }
}
