package cn.itcast.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cn.itcast.dao.IApproveInfoDao;
import cn.itcast.domain.ApproveInfo;

/**
 * 审批处理Dao
 * @author zhaoqx
 *
 */
@Repository
public class ApproveInfoDaoImpl implements IApproveInfoDao{
	@Resource
	private HibernateTemplate hibernateTemplate;

	public List<ApproveInfo> findListByApplicationId(Integer applicationId) {
		String hql = "FROM ApproveInfo ai WHERE ai.application.id = ? ORDER BY ai.approveDate ASC";
		return hibernateTemplate.find(hql, applicationId);
	}

	public void save(ApproveInfo ai) {
		hibernateTemplate.save(ai);
	}
}
