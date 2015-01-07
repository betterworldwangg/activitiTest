package cn.itcast.dao;

import cn.itcast.domain.User;

public interface IUserDao {

	public User findUserByLoginNameAndPassword(String loginName, String pwd);

}
