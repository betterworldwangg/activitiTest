package cn.itcast.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.dao.IUserDao;
import cn.itcast.domain.User;
import cn.itcast.service.IUserService;
import cn.itcast.utils.MD5Utils;
/**
 * 用户操作Service
 * @author zhaoqx
 *
 */
@Service
@Transactional
public class UserServiceImpl implements IUserService {
	@Resource
	private IUserDao userDao;
	public User login(User model) {
		String pwd = MD5Utils.md5(model.getPassword());//使用md5进行加密
		User user = userDao.findUserByLoginNameAndPassword(model.getLoginName(),pwd);
		return user;
	}

}
