package cn.itcast.dao;

import java.util.List;

import cn.itcast.domain.ApproveInfo;

public interface IApproveInfoDao {

	public List<ApproveInfo> findListByApplicationId(Integer applicationId);

	public void save(ApproveInfo ai);

}
