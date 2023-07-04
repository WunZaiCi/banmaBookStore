package com.banma.bs.user.dao;

/**
 * User持久层
 * @author WHASSUPMAN
 *
 */

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.banma.bs.user.domain.User;

import cn.itcast.jdbc.TxQueryRunner;

public class UserDao {
	private QueryRunner qr = new TxQueryRunner();

	/**
	 * 按用户名查询
	 * @param username
	 * @return
	 */
	public User findByUsername(String username) {
		try {
			String sql= "select * from tb_user where username=?";
			return qr.query(sql, new BeanHandler<User>(User.class),username);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}

	
	/**
	 * 按邮件查询
	 * @param email
	 * @return
	 */
	public User findByEmail(String email) {
		try {
			String sql= "select * from tb_user where email=?";
			return qr.query(sql, new BeanHandler<User>(User.class),email);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 添加user到数据库
	 * @param user
	 */
	public void add(User user) {
		try {
			String sql= "insert into tb_user values(?,?,?,?,?,?)";
			Object[] params = {
					user.getUid(),user.getUsername(),user.getPassword(), user.getEmail(),
					user.getCode(),user.getState()};
			qr.update(sql,params);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 按激活码查询
	 * @param code
	 * @return
	 */
	public User findByCode(String code) {
		try {
			String sql= "select * from tb_user where code=?";
			return qr.query(sql, new BeanHandler<User>(User.class),code);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	
	/**
	 * 更新状态
	 * @param uid
	 * @param state
	 */
	public void updateState(String uid, boolean state) {
		try {
			String sql= "update tb_user set state=? where uid=?";
			
			qr.update(sql,state,uid);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
