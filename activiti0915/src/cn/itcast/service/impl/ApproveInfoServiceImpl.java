package cn.itcast.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.dao.IApproveInfoDao;
import cn.itcast.domain.ApproveInfo;
import cn.itcast.service.IApproveInfoService;

/**
 * 审批处理操作Service
 * @author zhaoqx
 *
 */
@Service
@Transactional
public class ApproveInfoServiceImpl implements IApproveInfoService{
	@Resource
	private IApproveInfoDao approveInfoDao;

	public List<ApproveInfo> findListByApplicationId(Integer applicationId) {
		return approveInfoDao.findListByApplicationId(applicationId);
	}
}
