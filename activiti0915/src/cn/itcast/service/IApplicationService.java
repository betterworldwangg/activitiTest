package cn.itcast.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import cn.itcast.domain.Application;

public interface IApplicationService {

	public List<Application> findMyApplicationList(DetachedCriteria dc);

	public Application findById(Integer applicationId);

}
