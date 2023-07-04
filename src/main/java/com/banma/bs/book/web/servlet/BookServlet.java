package com.banma.bs.book.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.banma.bs.book.service.BookService;

import cn.itcast.servlet.BaseServlet;

/**
 * Servlet implementation class BookServlet
 */
@WebServlet("/BookServlet")
public class BookServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
     private BookService bookService =new BookService();  
    
	/**
	 * 查询所有图书
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("BookList", bookService.findAll());
		return "f:/jsps/book/list.jsp";
	}
	
	/**
	 * 按分类查询
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cid= request.getParameter("cid");
		request.setAttribute("BookList", bookService.findByCategory(cid));
		return "f:/jsps/book/list.jsp";
	}
	
	/**
	 * 加载单本图书详情
	 */
	public String load(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String bid= request.getParameter("bid");
		request.setAttribute("book", bookService.load(bid));
		return "f:/jsps/book/desc.jsp";
	}

}
