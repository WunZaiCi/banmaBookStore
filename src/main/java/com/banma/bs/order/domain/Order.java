package com.banma.bs.order.domain;

import java.util.Date;
import java.util.List;

import com.banma.bs.user.domain.User;

/**
 * 訂單類
 * @author WHASSUPMAN
 *
 */
public class Order {
	private String oid;
	private Date orderTime;//下單時間
	private double total;//合計
	private int state;//訂單狀態 1未付款 2已付款未發貨 3已發貨未收貨 4已收貨
	private User owner;
	private String address;
	private List<OrderItem> orderItemList;//当前订单下所有条目
	
	
	
	public List<OrderItem> getOrderItemList() {
		return orderItemList;
	}
	public void setOrderItemList(List<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public Date getOrdertime() {
		return orderTime;
	}
	public void setOrdertime(Date ordertime) {
		this.orderTime = ordertime;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
