package cn.itcast.service;

import java.util.List;

import cn.itcast.domain.ApproveInfo;

public interface IApproveInfoService {

	public List<ApproveInfo> findListByApplicationId(Integer applicationId);

}
