package com.banma.bs.order.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.banma.bs.book.domain.Book;
import com.banma.bs.order.domain.Order;
import com.banma.bs.order.domain.OrderItem;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;

public class OrderDao {
	private QueryRunner qr= new TxQueryRunner();
	
	
	/**
	 * 添加订单
	 * @param order
	 */
	public void addOrder(Order order) {
		try {
			String sql = "insert into orders values(?,?,?,?,?,?)";
			//处理util的Date转换成sql的timestamp
			Timestamp timestamp = new Timestamp(order.getOrdertime().getTime());
			
			Object[]params = {order.getOid(),timestamp,
					order.getTotal(),order.getState(),
					order.getOwner().getUid(),order.getAddress()};
			qr.update(sql,params);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	
	/**
	 * 插入订单条目
	 * @param orderItemList
	 */
	public void addOrderItemList(List<OrderItem> orderItemList) {
		/*
		 * QueryRunner类的batch（String sql,Object[][]params）
		 * params是多个一维数组
		 */
		try {
			String sql = "insert into orderitem values(?,?,?,?,?)";
			//把orderItemList转换成二维数组
			//把一个orderItem对象转换成一个一维数组
			
			
			Object[][]params = new Object[orderItemList.size()][];
			for (int i = 0; i < orderItemList.size(); i++) {
				OrderItem item =orderItemList.get(i);
				params[i]= new Object[] {item.getIid(),item.getCount(),
						item.getSubtotal(),item.getOrder().getOid(),
						item.getBook().getBid()};
			}		
			qr.batch(sql, params);
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}


	
	/**
	 * 按uid查询订单
	 * @param uid
	 * @return
	 */
	public List<Order> findByUid(String uid) {
		/**
		 * 1.通过uid查询出当前用户的所有List<Order>
		 * 2.循环遍历每个order 为其加载他的所有orderItem
		 */
		try {
			String sql = "select * from orders where uid=?";
			List<Order> orderList =qr.query(sql, new BeanListHandler<Order>(Order.class),uid);
			
			for(Order order :orderList ) {
				loadorderItems(order);//传入一个order 把他的订单条目添加进来
			}
			
			return orderList;//返回订单列表 
		
		} catch (SQLException e) {
			throw new RuntimeException();
		}
		
	
	}

	/**
	 * 加载指定的订单的条目
	 * @throws SQLException 
	 */
	private void loadorderItems(Order order) throws SQLException {
		/**
		 * 查询两张表 orderItem book
		 * 因为一行结果集对应的不再是一个javabean  不能使用beanlist 要用maplist
		 */
		String sql = "select * from orderitem i, book b where i.bid=b.bid and oid=?";
		
		//一个map对应一行结果集
		//用一个map生成两个对象 orderItem Book 然后在建立两者的关系 把book设置给orderItem
		 		
		List< Map<String,Object>> mapList = qr.query(sql, new MapListHandler(),order.getOid());
		List<OrderItem>orderItemList = toOderItemList(mapList);
		
		order.setOrderItemList(orderItemList);
		
	}

	/**
	 * 把mapList中每个Map转换成两个对象 并建立关系
	 * 把一堆map转换成一堆orderItem
	 * @param mapList
	 * @return
	 */
	private List<OrderItem> toOderItemList(List<Map<String, Object>> mapList) {
		List<OrderItem>orderItemList=new ArrayList<>();
		for(Map<String,Object> map : mapList) {
			OrderItem orderItem = toOderItem(map);
			orderItemList.add(orderItem);
		}
		return orderItemList;
			

	}

	/**
	 * 把一个map转换成一个orderItem
	 * @param map
	 * @return
	 */
	private OrderItem toOderItem(Map<String, Object> map) {
		OrderItem orderItem = CommonUtils.toBean(map, OrderItem.class);
		Book book=CommonUtils.toBean(map, Book.class);
		orderItem.setBook(book);
		return orderItem;
	}

	/**
	 * 加载订单
	 * @param oid
	 * @return
	 */
	public Order load(String oid) {
		try {
			/*
			 * 1. 得到当前用户的所有订单
			 */
			String sql = "select * from orders where oid=?";
			Order order = qr.query(sql, new BeanHandler<Order>(Order.class), oid);
			
			/*
			 * 2. 为order加载它的所有条目
			 */
			loadorderItems(order);

			
			/*
			 * 3. 返回订单列表
			 */
			return order;
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 *  通过oid查询订单状态
	 * @param oid
	 * @return
	 */
	public int getStateByOid(String oid) {
		try {
			String sql = "select state from orders where oid=?";
			return (Integer)qr.query(sql, new ScalarHandler(), oid);
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 修改订单状态
	 * @param oid
	 * @param state
	 * @return
	 */
	public void updateState(String oid, int state) {
		try {
			String sql = "update orders set state=? where oid=?";
			qr.update(sql, state, oid);
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
