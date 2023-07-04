package com.banma.bs.order.domain;

import com.banma.bs.book.domain.Book;

/**
 * 訂單條目類
 * @author WHASSUPMAN
 *
 */
public class OrderItem {
	private String iid;
	private int count;
	private double subtotal;//小計
	private Order order;//所屬訂單
	private Book book;//要購買的圖書
	
	public String getIid() {
		return iid;
	}
	public void setIid(String iid) {
		this.iid = iid;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	
	
}
