package cn.itcast.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.service.IProcessDefinitionService;
/**
 * 流程定义管理Service
 * @author zhaoqx
 *
 */
@Service
@Transactional
public class ProcessDefinitionServiceImpl implements IProcessDefinitionService{
	@Resource
	private ProcessEngine processEngine;
	@Resource
	private RepositoryService repositoryService;
	/**
	 *	查询最新版本的流程定义列表
	 * @return
	 */
	public List<ProcessDefinition> findNewList() {
		ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
		//根据流程定义的version进行升序排列
		query.orderByProcessDefinitionVersion().asc();
		List<ProcessDefinition> list = query.list();
		
		Map<String, ProcessDefinition> map = new HashMap<String, ProcessDefinition>();
		
		for (ProcessDefinition pd : list) {
			map.put(pd.getKey(), pd);
		}
		
		return new ArrayList<ProcessDefinition>(map.values());
	}
	
	/**
	 * 根据流程定义key删除流程定义
	 * @return
	 */
	public void deleteByKey(String key) {
		//根据流程定义的key，查询流程定义对象
		ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
		//根据key过滤
		query.processDefinitionKey(key);
		List<ProcessDefinition> list = query.list();
		for (ProcessDefinition pd : list) {
			String deploymentId = pd.getDeploymentId();
			repositoryService.deleteDeployment(deploymentId);
		}
	}

	/**
	 * 部署流程定义
	 */
	public void deploy(File resource) {
		DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
		ZipInputStream zipInputStream;
		try {
			zipInputStream = new ZipInputStream(new FileInputStream(resource));
			deploymentBuilder.addZipInputStream(zipInputStream);
			deploymentBuilder.deploy();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据流程定义的id获取对应的png图片输入流
	 */
	public InputStream getImgStream(String id) {
		return processEngine.getRepositoryService().getProcessDiagram(id);
	}
}
