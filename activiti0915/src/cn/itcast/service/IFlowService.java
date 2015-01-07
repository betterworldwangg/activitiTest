package cn.itcast.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;

import cn.itcast.domain.Application;
import cn.itcast.domain.ApproveInfo;
import cn.itcast.domain.TaskView;
import cn.itcast.domain.User;

public interface IFlowService {

	public void submit(Application application);

	public Task findTaskByApplicationId(Integer applicationId);

	public ProcessDefinition findProcessDefinitionByTask(Task task);

	public InputStream findImgStream(String deploymentId, String imageName);

	public Map<String, Object> findCoordingByTask(Task task);

	public List<TaskView> findTaskViewList(User loginUser);

	public void approve(ApproveInfo ai, String taskId);

}
