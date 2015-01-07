package cn.itcast.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.dao.IApplicationDao;
import cn.itcast.dao.IApproveInfoDao;
import cn.itcast.domain.Application;
import cn.itcast.domain.ApproveInfo;
import cn.itcast.domain.TaskView;
import cn.itcast.domain.User;
import cn.itcast.service.IFlowService;

/**
 * 流程处理Service
 * 
 * @author zhaoqx
 * 
 */
@Service
@Transactional
public class FlowServiceImpl implements IFlowService {
	@Resource
	private IApplicationDao applicationDao;
	@Resource
	private IApproveInfoDao approveInfoDao;
	@Resource
	private RepositoryService repositoryService;
	@Resource
	private RuntimeService runtimeService;
	@Resource
	private TaskService taskService;

	/**
	 * 提交申请操作
	 */
	public void submit(Application application) {
		// 1.保存一个申请实体
		applicationDao.save(application);

		String key = application.getTemplate().getKey();
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("application", application);
		variables.put("applicationId", application.getId());
		// 2.启动一个流程实例，设置流程变量
		ProcessInstance processInstance = runtimeService
				.startProcessInstanceByKey(key, variables);

		TaskQuery query = taskService.createTaskQuery();
		// 根据当前登录用户进行过滤
		query.taskAssignee(application.getApplicant().getLoginName());
		// 根据当前流程实例进行过滤
		query.processInstanceId(processInstance.getId());
		Task task = query.singleResult();

		// 3.办理第一个申请的任务
		taskService.complete(task.getId());
	}

	/**
	 * 根据申请id获得对应的任务
	 */
	public Task findTaskByApplicationId(Integer applicationId) {
		TaskQuery query = taskService.createTaskQuery();
		// 根据流程变量进行过滤
		query.processVariableValueEquals("applicationId", applicationId);
		Task task = query.singleResult();
		return task;
	}

	public ProcessDefinition findProcessDefinitionByTask(Task task) {
		String processDefinitionId = task.getProcessDefinitionId();
		return repositoryService.createProcessDefinitionQuery()
				.processDefinitionId(processDefinitionId).singleResult();
	}

	public InputStream findImgStream(String deploymentId, String imageName) {
		return repositoryService.getResourceAsStream(deploymentId, imageName);
	}

	/**
	 * 根据当前任务获得当前任务获得对应的坐标
	 */
	public Map<String, Object> findCoordingByTask(Task task) {
		// 根据任务获得流程定义的id
		String processDefinitionId = task.getProcessDefinitionId();

		// 根据id获得流程定义对象,其中包装的信息为bpmn文件的内容
		ProcessDefinitionEntity pd = (ProcessDefinitionEntity) repositoryService
				.getProcessDefinition(processDefinitionId);

		// 根据任务获得流程实例的id
		String processInstanceId = task.getProcessInstanceId();

		// 根据流程实例的id查询流程实例对象
		ProcessInstanceQuery query = runtimeService
				.createProcessInstanceQuery();
		query.processInstanceId(processInstanceId);
		ProcessInstance processInstance = query.singleResult();

		// 获得当前流程实例的获得id
		String activityId = processInstance.getActivityId();

		// 包装的是坐标的信息
		ActivityImpl activityImpl = pd.findActivity(activityId);

		int x = activityImpl.getX();
		int y = activityImpl.getY();
		int height = activityImpl.getHeight();
		int width = activityImpl.getWidth();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("x", x);
		map.put("y", y);
		map.put("height", height);
		map.put("width", width);

		return map;
	}

	/**
	 * 查询当前登录人的任务列表，包装成List<TaskView>返回
	 */
	public List<TaskView> findTaskViewList(User loginUser) {
		TaskQuery query = taskService.createTaskQuery();
		query.taskAssignee(loginUser.getLoginName());
		query.orderByTaskCreateTime().desc();
		List<Task> taskList = query.list();

		List<TaskView> taskViewList = new ArrayList<TaskView>();

		for (Task task : taskList) {
			String taskId = task.getId();
			Application application = (Application) taskService.getVariable(
					taskId, "application");
			taskViewList.add(new TaskView(application, task));
		}
		return taskViewList;
	}

	/**
	 * 审批处理
	 */
	public void approve(ApproveInfo ai, String taskId) {
		//根据任务id查询任务对象
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		
		//根据任务对象查询流程实例的id
		String processInstanceId = task.getProcessInstanceId();
		
		//保存一个审批实体
		approveInfoDao.save(ai);//持久状态的对象
		
		// 办理当前的任务
		taskService.complete(taskId);
		
		//根据流程实例的id查询流程实例对象，如果能查询出来，当前流程实例没有结束
		ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();

		Boolean approval = ai.getApproval();
		
		Application application = ai.getApplication();//持久状态的对象
		if(approval){// 如果当前审批人审批结果为“通过”
			if(pi == null){
				// 如果当前审批人是最后一个办理人,将申请的状态改为“已通过”
				application.setStatus(Application.STATUS_APPROVED);
			}
		}else{// 如果当前审批人审批结果为“不通过”,将申请的状态改为“未通过”
			application.setStatus(Application.STATUS_UNAPPROVED);
			if(pi != null){
				// 如果当前办理人不是最后一个办理人，手动结束当前流程实例
				runtimeService.deleteProcessInstance(processInstanceId, Application.STATUS_UNAPPROVED);
			}
		}
	}
}
