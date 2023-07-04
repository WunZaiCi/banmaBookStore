package com.banma.bs.user.service;

/**
 * User业务层
 * @author WHASSUPMAN
 *
 */

import com.banma.bs.user.dao.UserDao;
import com.banma.bs.user.domain.User;

public class UserService {
	private UserDao userDao = new UserDao();

	/**
	 * 注册功能
	 * 
	 * @param form
	 * @throws UserException
	 */
	public void regist(User form) throws UserException {
		// 校验用户名
		User user = userDao.findByUsername(form.getUsername());
		if (user != null) {
			throw new UserException("用户名已被注册");
		}

		// 校验email
		user = userDao.findByEmail(form.getEmail());
		if (user != null) {
			throw new UserException("Email已被注册");
		}
		
		//插入用户到数据库
		userDao.add(form);
	}
	
	/**
	 * 激活功能
	 * @param code
	 * @throws UserException
	 */
	public void actice(String code) throws UserException {
		//1. 使用code查询数据库 得到user
		User user = userDao.findByUsername(code);
		
		//2. 如果user不存在 说明激活码错误
		if (user != null) {
			throw new UserException("激活码无效");
		}

		// 校验状态是否为未激活 如果已经激活 抛异常
		if (user.getState()) {
			throw new UserException("您已经激活过了");
		}
		
		//插入用户到数据库
		userDao.updateState(user.getUid(), true);;
	}

	public User login(User form) throws UserException{
		/**
		 * 1.使用username查询 得到user
		 * 2.如果user为null 用户不存在异常
		 * 3.比较form和user的密码，如果不同 密码错误异常
		 * 4.查看用户状态 若是为false 尚未激活异常
		 * 返回user
		 */
		User user = userDao.findByUsername(form.getUsername());
		if(user==null ) {
			throw new UserException("用户名不存在");
		}
		if(!user.getPassword().equals(form.getPassword())) {
			throw new UserException("密码错误");
		}
		if(!user.getState()) {
			throw new UserException("尚未激活");
		}
		return user;
	}
	
	
}
