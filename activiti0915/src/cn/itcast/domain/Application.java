package cn.itcast.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 申请实体
 * @author zhaoqx
 *
 */
public class Application implements java.io.Serializable {
	private Integer id;
	private String title;//申请的标题, 格式：{表单模板名称}_{申请人姓名}_{申请日期}
	private Date applyDate;//申请日期
	private String status;//申请的状态，取值：审批中 未通过 已通过
	private String filePath;//当前申请对应的doc文件存储路径
	private Template template;//当前申请对应的模板对象
	private User applicant;//申请人
	private Set<ApproveInfo> approveInfos = new HashSet<ApproveInfo>();//当前申请对应的多个审批对象 
	
	public static final String STATUS_APPROVING = "审批中";
	public static final String STATUS_UNAPPROVED = "未通过";
	public static final String STATUS_APPROVED = "已通过";
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public User getApplicant() {
		return applicant;
	}
	public void setApplicant(User applicant) {
		this.applicant = applicant;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Template getTemplate() {
		return template;
	}
	public void setTemplate(Template template) {
		this.template = template;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public Set<ApproveInfo> getApproveInfos() {
		return approveInfos;
	}
	public void setApproveInfos(Set<ApproveInfo> approveInfos) {
		this.approveInfos = approveInfos;
	}
}
