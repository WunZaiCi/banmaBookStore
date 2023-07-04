package com.banma.bs.book.service;

import java.util.List;

import com.banma.bs.book.dao.BookDao;
import com.banma.bs.book.domain.Book;

public class BookService {
	private BookDao bookDao =new BookDao();
	
	/**
	 * 查询所有图书
	 * @return
	 */
	public List<Book> findAll(){
		return bookDao.findAll();
	}

	/**
	 * 按分类查询
	 * @param cid
	 * @return
	 */
	public Object findByCategory(String cid) {
		return bookDao.findByCategory(cid);
	}

	
	
	/**
	 * 加载单本图书详情
	 * @param bid
	 * @return
	 */
	public Book load(String bid) {
		return bookDao.findByBid(bid);
	}
	

}
