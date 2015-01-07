package cn.itcast.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import sun.misc.BASE64Encoder;

import cn.itcast.domain.Application;
import cn.itcast.domain.ApproveInfo;
import cn.itcast.domain.TaskView;
import cn.itcast.domain.Template;
import cn.itcast.domain.User;
import cn.itcast.service.IApplicationService;
import cn.itcast.service.IApproveInfoService;
import cn.itcast.service.IFlowService;
import cn.itcast.service.ITemplateService;
import cn.itcast.utils.FileUploadUtils;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 流程管理Action
 * @author zhaoqx
 *
 */
@Controller
@Scope("prototype")
public class FlowAction extends ActionSupport{
	private Integer templateId;//属性驱动，表单模板id
	private File resource;//用于文件上传的属性
	private String status;//属性驱动，申请的状态
	private Integer applicationId;//属性驱动，申请id
	private String taskId;//属性驱动，任务id
	private Boolean approval;//是否通过
	private String comment;//审批意见
	private InputStream imgStream;//用于文件下载的输入流
	private InputStream docStream;//用于文件下载输入流
	private String deploymentId;//部署id
	private String imageName;//png图片名称
	
	@Autowired
	private ITemplateService templateService;
	@Autowired
	private IFlowService flowService;
	@Autowired
	private IApplicationService applicationService;
	@Autowired
	private IApproveInfoService approveInfoService;
	/**
	 * 查询表单模板列表
	 */
	public String templateList(){
		List<Template> templateList = templateService.findList();
		ActionContext.getContext().put("templateList", templateList);
		return "templateList";
	}
	
	/**
	 * 跳转到提交申请页面
	 */
	public String submitUI(){
		return "submitUI";
	}

	/**
	 * 提交申请操作 
	 */
	public String submit(){
		//根据流程定义的key启动一个流程实例，同时设置流程变量，指定申请人作为第一个任务的办理人
		
		//根据模板id查询模板对象
		Template template = templateService.findById(templateId);
		
		//0.处理上传文件
		String filePath = FileUploadUtils.uploadFile(resource);
		
		//包装一个申请实体对象
		Application application  = new Application();
		application.setApplicant(getLoginUser());//设置申请人为当前登录用户
		application.setApplyDate(new Date());//申请申请时间为当前的系统时间
		application.setFilePath(filePath);//设置当前申请对应的doc文件的存储路径
		application.setStatus(Application.STATUS_APPROVING);//设置当前申请的状态为"审批中"
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String format = sdf.format(application.getApplyDate());
		String title = template.getName() + "_" + getLoginUser().getName() + "_" + format;
		application.setTitle(title);//设置申请的标题,格式：{表单模板名称}_{申请人姓名}_{申请日期}
		application.setTemplate(template);//设置当前申请使用的模板对象
		
		flowService.submit(application);
		
		return "toTemplateList";
	}
	
	/**
	 *  我的申请查询列表
	 */
	public String myApplicationList(){
		//离线条件查询对象
		DetachedCriteria dc = DetachedCriteria.forClass(Application.class);
		
		//添加排序条件：根据申请的日期进行降序排列
		dc.addOrder(Order.desc("applyDate"));
		
		//添加过滤条件：根据当前登录人进行过滤
		dc.add(Restrictions.eq("applicant", getLoginUser()));
		if(status != null && status.trim().length() > 0){
			//添加过滤条件：根据申请的状态进行过滤
			dc.add(Restrictions.eq("status", status));
		}
		
		List<Application> applicationList = applicationService.findMyApplicationList(dc);
		ActionContext.getContext().put("applicationList", applicationList);
		return "myApplicationList";
	}
	
	/**
	 * 查看当前流程图片
	 */
	public String getCurrentImg(){
		//根据申请的id查询对应的任务id
		Task task = flowService.findTaskByApplicationId(applicationId);
		ProcessDefinition pd = flowService.findProcessDefinitionByTask(task);
		//提供部署的id和png图片的名称
		String deploymentId = pd.getDeploymentId();
		String imageName = pd.getDiagramResourceName();
		ActionContext.getContext().put("deploymentId", deploymentId);
		ActionContext.getContext().put("imageName", imageName);
		
		//准备当前任务节点的坐标数据
		Map<String,Object> map = flowService.findCoordingByTask(task);
		/*Map<String, Object> map = new HashMap<String, Object>();
		map.put("x", 1);
		map.put("y", 300);
		map.put("height", 20);
		map.put("width", 50);*/
		
		ActionContext.getContext().put("acs", map);
		return "img";
	}
	
	/**
	 * 查询png图片对应的输入流
	 */
	public String viewImage(){
		imgStream = flowService.findImgStream(deploymentId,imageName);
		return "viewImage";
	}
	
	/**
	 * 下载申请对应的doc文件
	 * @throws Exception 
	 */
	public String download() throws Exception{
		Application application = applicationService.findById(applicationId);
		String filePath = application.getFilePath();
		docStream = new FileInputStream(new File(filePath));
		
		String fileName = application.getTitle() + ".doc";
		String agent = ServletActionContext.getRequest().getHeader("user-agent");
		fileName = this.encodeDownloadFilename(fileName, agent );
		ActionContext.getContext().put("fileName", fileName);
		
		return "download";
	}
	
	/**
	 * 查看流转记录
	 */
	public String findApproveInfosByApplicationId(){
		List<ApproveInfo> approveInfos = approveInfoService.findListByApplicationId(applicationId);
		ActionContext.getContext().put("approveInfos", approveInfos);
		return "approveInfos";
	}
	
	/**
	 * 查询我的任务列表
	 */
	public String myTaskList(){
		List<TaskView> taskViewList = flowService.findTaskViewList(getLoginUser());
		ActionContext.getContext().put("taskViewList", taskViewList);
		return "myTaskList";
	}
	
	/**
	 * 跳转到审批处理页面
	 */
	public String approveUI(){
		return "approveUI";
	}
	
	/**
	 * 审批处理
	 */
	public String approve(){
		//构造一个审批实体
		ApproveInfo ai = new ApproveInfo();
		ai.setApplication(applicationService.findById(applicationId));
		ai.setApproval(approval);
		ai.setApproveDate(new Date());
		ai.setApprover(getLoginUser());
		ai.setComment(comment);
		
		flowService.approve(ai,taskId);
		return "toMyTaskList";
	}
	
	/**
	 * 下载文件时，针对不同浏览器，进行附件名的编码
	 * 
	 * @param filename
	 *            下载文件名
	 * @param agent
	 *            客户端浏览器(通过request.getHeader("user-agent")获得)
	 * @return 编码后的下载附件名
	 * @throws IOException
	 */
	public String encodeDownloadFilename(String filename, String agent)
			throws IOException {
		if (agent.contains("Firefox")) { // 火狐浏览器
			filename = "=?UTF-8?B?"
					+ new BASE64Encoder().encode(filename.getBytes("utf-8"))
					+ "?=";
		} else { // IE及其他浏览器
			filename = URLEncoder.encode(filename, "utf-8");
		}
		return filename;
	}
	
	/**
	 * 获取当前登录用户
	 * @return
	 */
	private User getLoginUser() {
		return (User) ActionContext.getContext().getSession().get("loginUser");
	}

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public File getResource() {
		return resource;
	}

	public void setResource(File resource) {
		this.resource = resource;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Integer applicationId) {
		this.applicationId = applicationId;
	}

	public InputStream getImgStream() {
		return imgStream;
	}

	public void setImgStream(InputStream imgStream) {
		this.imgStream = imgStream;
	}

	public String getDeploymentId() {
		return deploymentId;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public InputStream getDocStream() {
		return docStream;
	}

	public void setDocStream(InputStream docStream) {
		this.docStream = docStream;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Boolean getApproval() {
		return approval;
	}

	public void setApproval(Boolean approval) {
		this.approval = approval;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
