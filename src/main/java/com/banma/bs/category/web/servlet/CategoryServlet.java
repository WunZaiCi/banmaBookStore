package com.banma.bs.category.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.banma.bs.category.service.CategoryService;

import cn.itcast.servlet.BaseServlet;

/**
 * Servlet implementation class CategoryServlet
 */
@WebServlet("/CategoryServlet")
public class CategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
    private CategoryService categoryService = new CategoryService(); 
    
	

	/**
	 * 查询所有分类
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("categoryList", categoryService.findAll());
		return "f:/jsps/left.jsp";
	}

	

}
