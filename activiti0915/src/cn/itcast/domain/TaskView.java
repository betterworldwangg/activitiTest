package cn.itcast.domain;

import org.activiti.engine.task.Task;

/**
 * 封装申请信息和任务信息
 * @author zhaoqx
 *
 */
public class TaskView {
	private Application application;
	private Task task;
	
	public TaskView() {}
	
	public TaskView(Application application, Task task) {
		this.application = application;
		this.task = task;
	}

	public Application getApplication() {
		return application;
	}
	public void setApplication(Application application) {
		this.application = application;
	}
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
	
}
