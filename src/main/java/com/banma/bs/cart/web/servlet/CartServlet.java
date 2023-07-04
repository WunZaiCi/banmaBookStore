package com.banma.bs.cart.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.banma.bs.book.domain.Book;
import com.banma.bs.book.service.BookService;
import com.banma.bs.cart.domain.Cart;
import com.banma.bs.cart.domain.CartItem;

import cn.itcast.servlet.BaseServlet;

/**
 * Servlet implementation class CartServlet
 */
@WebServlet("/CartServlet")
public class CartServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 添加购物条目
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 1.得到車
		Cart cart = (Cart) request.getSession().getAttribute("cart");

		// 2.得到條目 即圖書和數量
		String bid = request.getParameter("bid");
		Book book = new BookService().load(bid);
		int count = Integer.parseInt(request.getParameter("count"));

		// 把條目添加到車中
		CartItem cartItem = new CartItem();
		cartItem.setBook(book);
		cartItem.setCount(count);
		cart.add(cartItem);
		return "f://jsps/cart/list.jsp";

	}

	/**
	 * 清空
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String clear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//			// 1.得到車
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		cart.clear();
		return "f://jsps/cart/list.jsp";
	}

	/**
	 * 刪除
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 1.得到車
		Cart cart = (Cart) request.getSession().getAttribute("cart");

		// 2.得到條目 即圖書和數量
		String bid = request.getParameter("bid");
		cart.delete(bid);
		// 每次都回到list.jsp中
		return "f://jsps/cart/list.jsp";

	}

}
