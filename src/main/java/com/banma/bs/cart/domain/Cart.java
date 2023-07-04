package com.banma.bs.cart.domain;

import java.math.BigDecimal;

/**
 * 购物车类
 * @author WHASSUPMAN
 *
 */

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;



public class Cart {
	private Map<String, CartItem> map = new LinkedHashMap<String, CartItem>();
	
	/**
	 * 计算合计
	 * @return
	 */
	public double getTotal() {
		BigDecimal total=new BigDecimal("0");;
		//所有条目的小计之和
		for(CartItem cartItem: map.values()) {
			BigDecimal subtotal=new BigDecimal(""+ cartItem.getSubtotal());;
			total =total.add(subtotal);
		}
		return total.doubleValue();
	}

	/**
	 * 添加条目到车中
	 * @param cartItem
	 */
	public void add(CartItem cartItem) {
		if(map.containsKey(cartItem.getBook().getBid()) ) {//判断原本车中是否存在该条目
			CartItem _cartItem = map.get(cartItem.getBook().getBid());
			//如果存在  设置老条目的数量为 老条目数量+新条目数量
			_cartItem.setCount(_cartItem.getCount()+cartItem.getCount()); 
			
		}else {
			map.put(cartItem.getBook().getBid(), cartItem);
		}
		
	}

	/**
	 * 清空购物车条目
	 */
	public void clear() {
		map.clear();
	}

	/**
	 * 删除单个条目
	 * 
	 * @param bid     
	 */
	public void delete(String bid) {
		map.remove(bid);
	}

	
	/**
	 * 获取所有条目
	 * @return
	 */
	public Collection<CartItem> getCartItems() {
		return map.values();

	}
}
