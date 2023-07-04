package com.banma.bs.order.web.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.banma.bs.cart.domain.Cart;
import com.banma.bs.cart.domain.CartItem;
import com.banma.bs.cart.web.servlet.PaymentUtil;
import com.banma.bs.order.domain.Order;
import com.banma.bs.order.domain.OrderItem;
import com.banma.bs.order.service.OrderException;
import com.banma.bs.order.service.OrderService;
import com.banma.bs.user.domain.User;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

/**
 * Servlet implementation class OrderServlet
 */
@WebServlet("/OrderServlet")
public class OrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
    private OrderService orderService = new OrderService();
   
    /**
     * 我的订单
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String myOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    /**
     * 1.从session中得到当前user 得到uid
     * 2.使用uid调用orderservice的myOrders(uid)方法得到该用户的所有订单List<Order>
     **/
    	User user = (User) request.getSession().getAttribute("session_user");
    	List<Order> orderList=orderService.myOrders(user.getUid());
    	request.setAttribute("orderList", orderList);
    	return "/jsps/order/list.jsp";
    }
    
    
    /**
     * 添加订单
     * 把session中的车用来生成order对象
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
	public String add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/**
		 * ！！！cart对应order cartItem对象orderItem！！！！
		 * 1.从session中得到cart
		 * 2.使用cart生成order对象
		 * 3.orderservice方法完成添加订单
		 * 4.保存order到request域中 转发到jsps/order/desc.jsp
		 */
		//从session中获取cart	
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		//把cart转换成order对象
		Order order = new Order();
		order.setOid(CommonUtils.uuid());
		order.setOrdertime(new Date());
		order.setState(1);
		User user = (User) request.getSession().getAttribute("session_user");
		order.setOwner(user);
		order.setTotal(cart.getTotal());//从车中获取合计
		
		List< OrderItem> orderItemList = new ArrayList< OrderItem> ();
		//循环遍历cart中的所有cartItem
		//使用每一个cartItem对象创建orderItem对象 并添加到order中
		for(CartItem cartItem : cart.getCartItems()) {
			OrderItem oItem = new OrderItem();
			oItem.setBook(cartItem.getBook());
			oItem.setCount(cartItem.getCount());
			oItem.setSubtotal(cartItem.getSubtotal());
			oItem.setIid(CommonUtils.uuid());
			oItem.setOrder(order);  //设置所属订单order
			orderItemList.add(oItem);	
		}
		order.setOrderItemList(orderItemList);
		
	
		//清空购物车
		cart.clear();
		orderService.add(order);
		request.setAttribute("order", order);
		return "/jsps/order/desc.jsp";
		
		
	}

	/**
	 * 加载订单
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String load(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 1. 得到oid参数
		 * 2. 使用oid调用service方法得到Order
		 * 3. 保存到request域，转发到/jsps/order/desc.jsp
		 */
		request.setAttribute("order", orderService.load(request.getParameter("oid")));
		return "f:/jsps/order/desc.jsp";
	}
	
	/**
	 * 确认收货
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String confirm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 1. 获取oid参数
		 * 2. 调用service方法
		 *   > 如果有异常，保存异常信息，转发到msg.jsp
		 * 3. 保存成功信息，转发到msg.jsp
		 */
		String oid = request.getParameter("oid");
		try {
			orderService.confirm(oid);
			request.setAttribute("msg", "恭喜，交易成功！");
		} catch (OrderException e) {
			request.setAttribute("msg", e.getMessage());
		}
		return "f:/jsps/msg.jsp";
	}
	
	/**
	 * 支付之去银行
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String zhiFu(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Properties props = new Properties();
		InputStream input = this.getClass().getClassLoader()
				.getResourceAsStream("merchantInfo.properties");
		props.load(input);
		/*
		 * 准备13参数
		 */
		String p0_Cmd = "Buy";
		String p1_MerId = props.getProperty("p1_MerId");
		String p2_Order = request.getParameter("oid");
		String p3_Amt = "0.01";
		String p4_Cur = "CNY";
		String p5_Pid = "";
		String p6_Pcat = "";
		String p7_Pdesc = "";
		String p8_Url = props.getProperty("p8_Url");
		String p9_SAF = "";
		String pa_MP = "";
		String pd_FrpId = request.getParameter("pd_FrpId");
		String pr_NeedResponse = "1";

		/*
		 * 计算hmac
		 */
		String keyValue = props.getProperty("keyValue");
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
				p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
				pd_FrpId, pr_NeedResponse, keyValue);

		/*
		 * 连接易宝的网址和13+1个参数
		 */
		StringBuilder url = new StringBuilder(props.getProperty("url"));
		url.append("?p0_Cmd=").append(p0_Cmd);
		url.append("&p1_MerId=").append(p1_MerId);
		url.append("&p2_Order=").append(p2_Order);
		url.append("&p3_Amt=").append(p3_Amt);
		url.append("&p4_Cur=").append(p4_Cur);
		url.append("&p5_Pid=").append(p5_Pid);
		url.append("&p6_Pcat=").append(p6_Pcat);
		url.append("&p7_Pdesc=").append(p7_Pdesc);
		url.append("&p8_Url=").append(p8_Url);
		url.append("&p9_SAF=").append(p9_SAF);
		url.append("&pa_MP=").append(pa_MP);
		url.append("&pd_FrpId=").append(pd_FrpId);
		url.append("&pr_NeedResponse=").append(pr_NeedResponse);
		url.append("&hmac=").append(hmac);

		System.out.println(url);

		/*
		 * 重定向到易宝
		 */
		response.sendRedirect(url.toString());

		return null;
	}
	
	

	/**
	 * 这个方法是易宝回调方法 我们必须要判断调用本方法的是不是易宝！
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String back(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 1. 获取11 + 1
		 */
		String p1_MerId = request.getParameter("p1_MerId");
		String r0_Cmd = request.getParameter("r0_Cmd");
		String r1_Code = request.getParameter("r1_Code");
		String r2_TrxId = request.getParameter("r2_TrxId");
		String r3_Amt = request.getParameter("r3_Amt");
		String r4_Cur = request.getParameter("r4_Cur");
		String r5_Pid = request.getParameter("r5_Pid");
		String r6_Order = request.getParameter("r6_Order");
		String r7_Uid = request.getParameter("r7_Uid");
		String r8_MP = request.getParameter("r8_MP");
		String r9_BType = request.getParameter("r9_BType");

		String hmac = request.getParameter("hmac");

		/*
		 * 2. 校验访问者是否为易宝！
		 */
		Properties props = new Properties();
		InputStream input = this.getClass().getClassLoader()
				.getResourceAsStream("merchantInfo.properties");
		props.load(input);
		String keyValue = props.getProperty("keyValue");

		boolean bool = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd,
				r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid,
				r8_MP, r9_BType, keyValue);
		
		if(!bool) {//如果校验失败
			request.setAttribute("msg", "您不是什么好东西！");
			return "f:/jsps/msg.jsp";
		}
		
		/*
		 * 3. 获取状态订单，确定是否要修改订单状态，以及添加积分等业务操作
		 */
		orderService.zhiFu(r6_Order);//有可能对数据库进行操作，也可能不操作！
		
		/*
		 * 4. 判断当前回调方式
		 *   如果为点对点，需要回馈以success开头的字符串
		 */
		if(r9_BType.equals("2")) {
			response.getWriter().print("success");
		}
		
		/*
		 * 5. 保存成功信息，转发到msg.jsp
		 */
		request.setAttribute("msg", "支付成功！等待卖家发货！你慢慢等~");
		return "f:/jsps/msg.jsp";
	}
}
