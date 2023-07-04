package com.banma.bs.cart.domain;
/**
 * 购物车条目类
 * @author WHASSUPMAN
 *
 */

import java.math.BigDecimal;

import com.banma.bs.book.domain.Book;

public class CartItem {
	private Book book;
	private int count;
	
	
	/**
	 * 处理了二进制误差问题
	 * @return
	 */
	public double getSubtotal() {//得到小计
		BigDecimal d1= new BigDecimal(book.getPrice()+"");//防止二进制加减乘除有误差
		BigDecimal d2= new BigDecimal(count+"");
		return d1.multiply(d2).doubleValue();
	}
	
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
}
