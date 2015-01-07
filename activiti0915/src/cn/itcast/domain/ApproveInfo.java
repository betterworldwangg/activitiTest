package cn.itcast.domain;

import java.util.Date;

/**
 * 审批实体
 * @author zhaoqx
 *
 */
public class ApproveInfo implements java.io.Serializable{
	private Integer id;
	private Date approveDate;//审批时间
	private Boolean approval;//是否通过
	private String comment;//审批意见
	private User approver;//审批人
	private Application application;//当前审批对应的申请对象
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public User getApprover() {
		return approver;
	}
	public void setApprover(User approver) {
		this.approver = approver;
	}
	public Date getApproveDate() {
		return approveDate;
	}
	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
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
	public Application getApplication() {
		return application;
	}
	public void setApplication(Application application) {
		this.application = application;
	}
	
}
