package cn.itcast.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cn.itcast.dao.IUserDao;
import cn.itcast.domain.User;
/**
 * 用户操作Dao
 * @author zhaoqx
 *
 */
@Repository
public class UserDaoImpl implements IUserDao {
	@Autowired
	private HibernateTemplate hibernateTemplate;
	public User findUserByLoginNameAndPassword(String loginName, String pwd) {
		String hql = "FROM User u WHERE u.loginName = ? AND u.password = ?";
		List<User> list = hibernateTemplate.find(hql, loginName,pwd);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

}
