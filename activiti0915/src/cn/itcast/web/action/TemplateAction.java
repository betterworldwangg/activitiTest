package cn.itcast.web.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.repository.ProcessDefinition;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import sun.misc.BASE64Encoder;
import cn.itcast.domain.Template;
import cn.itcast.service.IProcessDefinitionService;
import cn.itcast.service.ITemplateService;
import cn.itcast.utils.FileUploadUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 模板管理Action
 * 
 * @author zhaoqx
 * 
 */
@Controller
@Scope("prototype")
public class TemplateAction extends ActionSupport implements
		ModelDriven<Template> {
	private Template model = new Template();
	private File resource;// 属性驱动，用于文件上传
	private InputStream docStream;// 用于文件下载的输入流
	@Resource
	private ITemplateService templateService;
	@Resource
	private IProcessDefinitionService processDefinitionService;

	public Template getModel() {
		return model;
	}

	/**
	 * 查询模板列表
	 */
	public String list() {
		List<Template> list = templateService.findList();
		ActionContext.getContext().put("list", list);
		return "list";
	}

	/**
	 * 根据id删除模板数据，同时删除对应的doc文件
	 */
	public String deleteById() {
		templateService.deleteById(model.getId());
		return "toList";
	}

	/**
	 * 跳转到添加模板页面
	 */
	public String saveUI() {
		// 准备流程定义列表数据，用于填充下拉框
		List<ProcessDefinition> pdList = processDefinitionService.findNewList();
		ActionContext.getContext().put("pdList", pdList);
		return "saveUI";
	}

	/**
	 * 添加模板操作
	 */
	public String save() {
		// 完成将文件上传的临时文件保存到指定的目录中并返回文件的存储路径
		String filePath = FileUploadUtils.uploadFile(resource);
		model.setFilePath(filePath);
		templateService.save(model);
		return "toList";
	}

	/**
	 * 下载模板对应的doc文件
	 * 
	 * @return
	 * @throws Exception
	 */
	public String download() throws Exception {
		docStream = templateService.findDocStreamById(model.getId());
		String filename = templateService.findNameById(model.getId()) + ".doc";
		String agent = ServletActionContext.getRequest()
				.getHeader("user-agent");
		filename = encodeDownloadFilename(filename, agent);
		ActionContext.getContext().put("filename", filename);
		return "download";
	}

	/**
	 * 跳转到修改表单模板信息页面
	 */
	public String updateUI() {
		// 根据模板的id查询模板对象，压入值栈，用于修改页面的回显
		Template template = templateService.findById(model.getId());
		ActionContext.getContext().getValueStack().push(template);

		// 准备流程定义列表数据，用于填充下拉框
		List<ProcessDefinition> pdList = processDefinitionService.findNewList();
		ActionContext.getContext().put("pdList", pdList);
		return "updateUI";
	}
	
	/**
	 * 根据id修改模板信息
	 */
	public String update(){
		//根据id查询当前要修改的模板对象
		Template template = templateService.findById(model.getId());
		template.setName(model.getName());
		template.setKey(model.getKey());
		if(resource != null){//上传了新文件
			//将原始文件删除
			String filePath = template.getFilePath();
			//构造一个文件，指向原始文件
			File oldFile = new File(filePath);
			//如果原始文件存在，就删除
			if(oldFile.exists()){
				oldFile.delete();
			}
			
			//将删除的临时文件拷贝到指定的日期目录中
			filePath = FileUploadUtils.uploadFile(resource);
			//重新指定文件的存储路径
			template.setFilePath(filePath);
		}
		
		//更新
		templateService.update(template);
		return "toList";
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

	public File getResource() {
		return resource;
	}

	public void setResource(File resource) {
		this.resource = resource;
	}

	public InputStream getDocStream() {
		return docStream;
	}

	public void setDocStream(InputStream docStream) {
		this.docStream = docStream;
	}

}
