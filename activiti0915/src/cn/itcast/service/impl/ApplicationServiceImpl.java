package cn.itcast.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.dao.IApplicationDao;
import cn.itcast.domain.Application;
import cn.itcast.service.IApplicationService;

/**
 * 申请实体操作的service
 * @author zhaoqx
 *
 */
@Service
@Transactional
public class ApplicationServiceImpl implements IApplicationService{
	@Resource
	private IApplicationDao applicationDao;
	
	/**
	 * 根据条件查询对象进行查询申请集合
	 */
	public List<Application> findMyApplicationList(DetachedCriteria dc) {
		return applicationDao.findMyApplicationList(dc);
	}

	public Application findById(Integer applicationId) {
		return applicationDao.findById(applicationId);
	}

}
