package com.changwen.activiti.a_helloworld;

import java.util.List;

import com.changwen.activiti.service.ActivitiProcess;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class HelloWorld {
	private ActivitiProcess activitiProcess;

	/**部署流程定义*/
	@Test
	public void deploymentProcessDefinition(){
		activitiProcess.deploymentProcessDefinition("helloworld入门程序", "diagrams/helloworld.bpmn", "diagrams/helloworld.png");
	}
	
	/**启动流程实例*/
	@Test
	public void startProcessInstance(){
		activitiProcess.startProcessInstance("helloworld");
	}
	
	
	/**查询当前人的个人任务*/
	@Test
	public void findMyPersonalTask(){
		String assignee = "王五";
		List<Task> list = activitiProcess.findMyPersonalTask(assignee);
		if(list!=null && list.size()>0){
			for(Task task:list){
				System.out.println("任务ID: "+task.getId());
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
	public void completeMyPersonalTask() {
		//任务ID
		String taskId = "302";
		activitiProcess.completeMyPersonalTask(taskId);
	}
	
}
