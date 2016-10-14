package com.changwen.activiti.b_processDefinition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import com.changwen.activiti.service.ActivitiProcess;
import com.changwen.activiti.service.impl.ActivitiProcessImpl;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class ProcessDefinitionTest {
	private ActivitiProcess activitiProcess = new ActivitiProcessImpl() ;

	/**部署流程定义（从classpath）*/
	@Test
	public void deploymentProcessDefinition_classpath(){
		activitiProcess.deploymentProcessDefinition_classpath("helloworld入门程序", "diagrams/helloworld.bpmn", "diagrams/helloworld.png");

	}
	
	/**部署流程定义（从zip）*/
	@Test
	public void deploymentProcessDefinition_zip(){
		activitiProcess.deploymentProcessDefinition_zip("流程定义", "diagrams/helloworld.zip");
	}
	
	/**查询流程定义*/
	@Test
	public void findProcessDefinition(){
		ProcessDefinitionQuery processDefinitionQuery = activitiProcess.findProcessDefinitionQuery();
		List<ProcessDefinition> list = processDefinitionQuery
						/**指定查询条件,where条件*/
//						.deploymentId(deploymentId)//使用部署对象ID查询
//						.processDefinitionId(processDefinitionId)//使用流程定义ID查询
//						.processDefinitionKey(processDefinitionKey)//使用流程定义的key查询
//						.processDefinitionNameLike(processDefinitionNameLike)//使用流程定义的名称模糊查询
						
						/**排序*/
						.orderByProcessDefinitionVersion().asc()//按照版本的升序排列
//						.orderByProcessDefinitionName().desc()//按照流程定义的名称降序排列
						
						/**返回的结果集*/
						.list();//返回一个集合列表，封装流程定义
//						.singleResult();//返回惟一结果集
//						.count();//返回结果集数量
//						.listPage(firstResult, maxResults);//分页查询
		if(list!=null && list.size()>0){
			for(ProcessDefinition pd:list){
				System.out.println("流程定义ID:"+pd.getId());//流程定义的key+版本+随机生成数
				System.out.println("流程定义的名称:"+pd.getName());//对应helloworld.bpmn文件中的name属性值
				System.out.println("流程定义的key:"+pd.getKey());//对应helloworld.bpmn文件中的id属性值
				System.out.println("流程定义的版本:"+pd.getVersion());//当流程定义的key值相同的相同下，版本升级，默认1
				System.out.println("资源名称bpmn文件:"+pd.getResourceName());
				System.out.println("资源名称png文件:"+pd.getDiagramResourceName());
				System.out.println("部署对象ID："+pd.getDeploymentId());
				System.out.println("#########################################################");
			}
		}			
	}
	
	/**删除流程定义*/
	@Test
	public void deleteProcessDefinition(){
		//使用部署ID，完成删除
		String deploymentId = "601";
		activitiProcess.deleteProcessDefinition(deploymentId,true);
	}
	
	/**查看流程图
	 * @throws IOException */
	@Test
	public void viewPic() throws IOException{
		/**将生成图片放到文件夹下*/
		String deploymentId = "801";
		activitiProcess.viewPic(deploymentId, "D:/");
	}
	
	/***附加功能：查询最新版本的流程定义*/
	@Test
	public void findLastVersionProcessDefinition(){
		List<ProcessDefinition> pdList = activitiProcess.findLastVersionProcessDefinition();

		if(pdList!=null && pdList.size()>0){
			for(ProcessDefinition pd:pdList){
				System.out.println("流程定义ID:"+pd.getId());//流程定义的key+版本+随机生成数
				System.out.println("流程定义的名称:"+pd.getName());//对应helloworld.bpmn文件中的name属性值
				System.out.println("流程定义的key:"+pd.getKey());//对应helloworld.bpmn文件中的id属性值
				System.out.println("流程定义的版本:"+pd.getVersion());//当流程定义的key值相同的相同下，版本升级，默认1
				System.out.println("资源名称bpmn文件:"+pd.getResourceName());
				System.out.println("资源名称png文件:"+pd.getDiagramResourceName());
				System.out.println("部署对象ID："+pd.getDeploymentId());
				System.out.println("#########################################################");
			}
		}	
	}
	
	/**附加功能：删除流程定义（删除key相同的所有不同版本的流程定义）*/
	@Test
	public void deleteProcessDefinitionByKey(){
		//流程定义的key
		String processDefinitionKey = "helloworld";

		activitiProcess.deleteProcessDefinitionByKey(processDefinitionKey);
	}
}
