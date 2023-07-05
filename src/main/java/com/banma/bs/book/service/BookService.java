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

	
	/**
	 * 删除图书 
	 * @param bid
	 */
	public void delete(String bid) {
		bookDao.delete(bid);
		
	}

	
	/**添加图书
	 * 
	 * @param book
	 */
	public void add(Book book) {
		bookDao.add(book);
		
	}

	/**
	 * 编辑图书
	 * @param book
	 */
	public void edit(Book book) {
		bookDao.edit(book);
		
	}
	

}
