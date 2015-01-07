package cn.itcast.web.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;

import org.activiti.engine.repository.ProcessDefinition;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.service.IProcessDefinitionService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 流程定义管理Action
 * @author zhaoqx
 *
 */
@Controller
@Scope("prototype")
public class ProcessDefinitionAction extends ActionSupport {
	private String key;//属性驱动，流程定义的key
	private File resource;//属性驱动，用于文件上传
	private String id;//属性驱动，流程定义id
	//注入流程定义管理Service
	@Resource
	private IProcessDefinitionService processDefinitionService;
	
	/**
	 *	查询最新版本的流程定义列表
	 * @return
	 */
	public String list(){
		List<ProcessDefinition> list = processDefinitionService.findNewList();
		ActionContext.getContext().getValueStack().set("list", list);
		return "list";
	}

	/**
	 * 根据流程定义key删除流程定义
	 * @return
	 */
	public String deleteByKey(){
		processDefinitionService.deleteByKey(key);
		return "toList";
	}
	
	/**
	 * 跳转到部署流程定义页面
	 * @return
	 */
	public String deployUI(){
		return "deployUI";
	}
	
	/**
	 * 使用zip文件，部署流程定义
	 * @return
	 */
	public String deploy(){
		processDefinitionService.deploy(resource);
		return "toList";
	}
	
	/**
	 * 查看流程定义图片
	 * @return
	 * @throws IOException 
	 */
	public String showImg() throws IOException{
		InputStream imgStream = processDefinitionService.getImgStream(id);
		ActionContext.getContext().put("imgStream", imgStream);
		
		//使用一个输出流，将上面的输入流写到浏览器中
		/*ServletOutputStream out = ServletActionContext.getResponse().getOutputStream();
		byte[] b = new byte[1024];
		int len = 0;
		while((len = in.read(b)) != -1){
			out.write(b,0,len);
		}
		out.flush();
		out.close();
		in.close();*/
		return "showImg";
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public File getResource() {
		return resource;
	}

	public void setResource(File resource) {
		this.resource = resource;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
