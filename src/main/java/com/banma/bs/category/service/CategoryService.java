package com.banma.bs.category.service;

import java.util.List;

import com.banma.bs.book.dao.BookDao;
import com.banma.bs.category.dao.CategoryDao;
import com.banma.bs.category.domain.Category;
import com.banma.bs.category.web.servlet.admin.CategoryException;

public class CategoryService {
	private CategoryDao categoryDao = new CategoryDao();
	private BookDao bookDao = new BookDao();

	/**
	 * 查询所有分类
	 * @return
	 */
	public List<Category> findAll() {
		
		return categoryDao.findAll();
	}
	
	/**
	 * 添加分类
	 * @param category
	 */
	public void add(Category category) {
		categoryDao.add(category);
		
	}

	/**
	 * 删除分类
	 * @param cid
	 * @throws CategoryException 
	 */
	public void delete(String cid) throws CategoryException {
		/**
		 * 获取该分类下图书的本书 
		 * 分类下存在图书的话抛出异常 不允许删除
		 */
		int count =  bookDao.getCountByCid(cid);
		if(count>0) {
			throw new CategoryException("该分类下还有图书 不能删除");
		}
		categoryDao.delete(cid);
	}

	
	/**
	 * 加载分类
	 * @param cid
	 * @return
	 */
	public Object load(String cid) {
		
		return categoryDao.load(cid);
	}

	
	/**
	 * 修改分类
	 * @param category
	 */
	public void edit(Category category) {
		categoryDao.edit(category);
		
	}
}
