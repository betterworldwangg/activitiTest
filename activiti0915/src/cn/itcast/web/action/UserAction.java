package cn.itcast.web.action;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.domain.User;
import cn.itcast.service.IUserService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 用户操作Action
 * @author zhaoqx
 *
 */
@Controller
@Scope("prototype")
public class UserAction extends ActionSupport implements ModelDriven<User>{
	private User model =new User();
	public User getModel() {
		return model;
	}
	
	@Autowired
	private IUserService userService;
	
	/**
	 * 用户登录功能
	 */
	public String login(){
		User user = userService.login(model);
		if(user != null){
			//登录成功
			//将登录用户放入session
			ActionContext.getContext().getSession().put("loginUser", user);
			//跳转到系统首页面
			return "home";
		}else{
			//登录失败
			//设置错误消息
			this.addActionError("登录名或者密码不正确！");
			return "login";
		}
	}
	
	/**
	 * 用户注销功能
	 */
	public String logout(){
		ServletActionContext.getRequest().getSession().invalidate();
		return "login";
	}

}
