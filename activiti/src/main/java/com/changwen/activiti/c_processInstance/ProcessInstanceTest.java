package com.changwen.activiti.c_processInstance;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import com.changwen.activiti.service.ActivitiProcess;
import com.changwen.activiti.service.impl.ActivitiProcessImpl;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class ProcessInstanceTest {

	private ActivitiProcess activitiProcess = new ActivitiProcessImpl() ;
	
	/**部署流程定义（从zip）*/
	@Test
	public void deploymentProcessDefinition_zip(){
		activitiProcess.deploymentProcessDefinition_zip("流程定义","diagrams/helloworld.zip");
	}
	
	/**启动流程实例*/
	@Test
	public void startProcessInstance(){
		//流程定义的key
		String processDefinitionKey = "helloworld";
		activitiProcess.startProcessInstance(processDefinitionKey);
	}
	
	/**查询当前人的个人任务*/
	@Test
	public void findMyPersonalTask(){
		String assignee = "张三";
		List<Task> list = activitiProcess.findMyPersonalTask(assignee);

		if(list!=null && list.size()>0){
			for(Task task:list){
				System.out.println("任务ID:"+task.getId());
				System.out.println("任务名称:"+task.getName());
				System.out.println("任务的创建时间:"+task.getCreateTime());
				System.out.println("任务的办理人:"+task.getAssignee());
				System.out.println("流程实例ID："+task.getProcessInstanceId());
				System.out.println("执行对象ID:"+task.getExecutionId());
				System.out.println("流程定义ID:"+task.getProcessDefinitionId());
				System.out.println("########################################################");
			}
		}
	}
	
	/**完成我的任务*/
	@Test
	public void completeMyPersonalTask(){
		//任务ID
		String taskId = "1202";
		activitiProcess.completeMyPersonalTask(taskId);
	}
	
	/**查询流程状态（判断流程正在执行，还是结束）*/
	@Test
	public void isProcessEnd(){
		String processInstanceId = "1001";
		activitiProcess.isProcessEnd(processInstanceId);
	}
	
	/**查询历史任务（后面讲）*/
	@Test
	public void findHistoryTask(){
		String taskAssignee = "张三";

		List<HistoricTaskInstance> list = activitiProcess.findHistoryTask(taskAssignee);
		if(list!=null && list.size()>0){
			for(HistoricTaskInstance hti:list){
				System.out.println(hti.getId()+"    "+hti.getName()+"    "+hti.getProcessInstanceId()+"   "+hti.getStartTime()+"   "+hti.getEndTime()+"   "+hti.getDurationInMillis());
				System.out.println("################################");
			}
		}
	}
	
	/**查询历史流程实例（后面讲）*/
	@Test
	public void findHistoryProcessInstance(){
		String processInstanceId = "1001";
		HistoricProcessInstance hpi = activitiProcess.findHistoryProcessInstance(processInstanceId);
		System.out.println(hpi.getId()+"    "+hpi.getProcessDefinitionId()+"    "+hpi.getStartTime()+"    "+hpi.getEndTime()+"     "+hpi.getDurationInMillis());
	}
}
