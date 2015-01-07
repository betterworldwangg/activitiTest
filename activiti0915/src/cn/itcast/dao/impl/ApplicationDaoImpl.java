package cn.itcast.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cn.itcast.dao.IApplicationDao;
import cn.itcast.domain.Application;

/**
 * 处理申请对象的Dao
 * @author zhaoqx
 *
 */
@Repository
public class ApplicationDaoImpl implements IApplicationDao{
	@Resource
	private HibernateTemplate hibernateTemplate;

	public void save(Application application) {
		hibernateTemplate.save(application);
	}

	public List<Application> findMyApplicationList(DetachedCriteria dc) {
		return hibernateTemplate.findByCriteria(dc);
	}

	public Application findById(Integer applicationId) {
		return hibernateTemplate.get(Application.class, applicationId);
	}
}
