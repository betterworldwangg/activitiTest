package cn.itcast.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import cn.itcast.domain.Application;

public interface IApplicationDao {

	public void save(Application application);

	public List<Application> findMyApplicationList(DetachedCriteria dc);

	public Application findById(Integer applicationId);

}
