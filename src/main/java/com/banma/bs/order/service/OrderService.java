package com.banma.bs.order.service;

import java.sql.SQLException;
import java.util.List;

import com.banma.bs.order.dao.OrderDao;
import com.banma.bs.order.domain.Order;

import cn.itcast.jdbc.JdbcUtils;

public class OrderService {
	
	private OrderDao orderDao = new OrderDao();
	
	public void add(Order order) {
		try {
			//开启事务
			JdbcUtils.beginTransaction();
			orderDao.addOrder(order);//插入订单
			orderDao.addOrderItemList(order.getOrderItemList());//插入订单中的所有条目
			
			
			//提交事务
			JdbcUtils.commitTransaction();
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {
		
			}
			throw new RuntimeException(e);
		}
	}

	
	/**
	 * 我的订单
	 * @param uid
	 * @return
	 */
	public List<Order> myOrders(String uid) {
		return orderDao.findByUid(uid);
	}

	/**
	 * 加载订单
	 * @param oid
	 * @return
	 */
	public Order load(String oid) {
		return orderDao.load(oid);
	}
	
	/**
	 * 确认收货
	 * @param oid
	 * @throws OrderException
	 */
	public void confirm(String oid) throws OrderException {
		/*
		 * 1. 校验订单状态，如果不是3，抛出异常
		 */
		int state = orderDao.getStateByOid(oid);//获取订单状态
		if(state != 3) throw new OrderException("订单确认失败，您不是什么好东西！");
		
		/*
		 * 2. 修改订单状态为4，表示交易成功
		 */
		orderDao.updateState(oid, 4);
	}


	/**
	 * 支付方法
	 * @param oid
	 */
	public void zhiFu(String oid) {
		/*
		 * 1. 获取订单的状态
		 *   * 如果状态为1，那么执行下面代码
		 *   * 如果状态不为1，那么本方法什么都不做
		 */
		int state = orderDao.getStateByOid(oid);
		if(state == 1) {
			// 修改订单状态为2
			orderDao.updateState(oid, 2);
		}
	}
}
