package com.banma.bs.user.web.servlet;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.banma.bs.cart.domain.Cart;
import com.banma.bs.user.domain.User;
import com.banma.bs.user.service.UserException;
import com.banma.bs.user.service.UserService;

import cn.itcast.commons.CommonUtils;

import cn.itcast.servlet.BaseServlet;

/**
 * Servlet implementation class UserServlet
 */
/**
 * User表述层
 * 
 * @author WHASSUPMAN
 *
 */
@WebServlet("/UserServlet")
public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	private UserService userService = new UserService();

	/**
	 * 退出功能
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String quit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession().invalidate();
		return "r:/index.jsp";
		
	}
	
	
	
	/**
	 * 激活功能
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String active(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		//获取参数激活码
//		调用service方法完成激活
//		 错误 保存异常信息到request域 转发到msg.jsp
//		 成功 保存成功信息到request域 转发到msg.jsp
		String code = request.getParameter("code");
		try {
			userService.actice(code);
			request.setAttribute("msg", "恭喜，激活成功，请登录");
		} catch (UserException e) {
			request.setAttribute("msg", e.getMessage());
		}
		return "f:/jsps/msg.jsp";
	}

	public String login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
				//封装表单来的数据
				User form = CommonUtils.toBean(request.getParameterMap(), User.class);
		       
		        //校验
		        Map<String, String> errors = new HashMap<String, String>();
		        String username = form.getUsername();
				if (username == null || username.trim().isEmpty()) {
					errors.put("username", "用户名不能为空！");
				} else if (username.length() < 3 || username.length() > 10) {
					errors.put("username", "用户名长度必须在3-10之间");
				}

				String password = form.getPassword();
				if (password == null || password.trim().isEmpty()) {
					errors.put("password", "密码不能为空！");
				} else if (password.length() < 3 || password.length() > 10) {
					errors.put("password", "密码长度必须在3-10之间");
				}
				if (errors.size() > 0) {
					/**
					 * 1.保存错误信息 2.保存表单数据 3.转发到regist.jsp
					 */
					request.setAttribute("errors", errors);
					request.setAttribute("form", form);
					return "f:/jsps/user/regist.jsp";
				}
				
				//
		        
		        User user;
				try {
					user = userService.login(form);
					request.getSession().setAttribute("session_user", user);
					//登錄成功給用戶加一個購物車 也就是向session中保存一個cart對象
					request.getSession().setAttribute("cart", new Cart());		
					
			        return "r:/index.jsp";
			        
				} catch (UserException e) {
					request.setAttribute("msg", e.getMessage());
					request.setAttribute("form", form);
					return "f:/jsps/user/login.jsp";
				}
				
				 
		        
		        
	}
	
	
	
	
	
	
	/**
	 * 注册功能
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String regist(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/**
		 * 1.封装表单数据到对象中 2.补全uid 和code 3.输入校验 保存错误信息和form到request域 转发到regist.jsp
		 * 4.调用service方法完成注册 保存错误信息和form到request域 转发到regist.jsp 5.发邮件 6.保存成功信息转发到msg.jsp
		 */
		// 封装表单数据
		User form = CommonUtils.toBean(request.getParameterMap(), User.class);
		form.setUid(CommonUtils.uuid());
		form.setCode(CommonUtils.uuid() + CommonUtils.uuid());

		/**
		 * 输入校验 校验从表单传过来的信息符不符合规定 符合才进行注册 1.创建一个Map，用来封装错误信息，其中key为表单字段名称，值为错误信息
		 */
		Map<String, String> errors = new HashMap<String, String>();
		/**
		 * 2.获取form中的username password email进行校验
		 */
		String username = form.getUsername();
		if (username == null || username.trim().isEmpty()) {
			errors.put("username", "用户名不能为空！");
		} else if (username.length() < 3 || username.length() > 10) {
			errors.put("username", "用户名长度必须在3-10之间");
		}

		String password = form.getPassword();
		if (password == null || password.trim().isEmpty()) {
			errors.put("password", "密码不能为空！");
		} else if (password.length() < 3 || password.length() > 10) {
			errors.put("password", "密码长度必须在3-10之间");
		}

		String email = form.getEmail();
		if (email == null || email.trim().isEmpty()) {
			errors.put("email", "emil不能为空！");
		} else if (!email.matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$")) {
			errors.put("email", "email格式错误");
		}
		/**
		 * 3.判断是否存在错误信息
		 */
		if (errors.size() > 0) {
			/**
			 * 1.保存错误信息 2.保存表单数据 3.转发到regist.jsp
			 */
			request.setAttribute("errors", errors);
			request.setAttribute("form", form);
			return "f:/jsps/user/regist.jsp";
		}

		/**
		 * 调用service的regist()方法
		 */
		try {
			userService.regist(form);

		} catch (UserException e) {
			/**
			 * 1.保存错误信息 2.保存form 3.转发到regist.jsp
			 */
			request.setAttribute("msg", e.getMessage());
			request.setAttribute("form", form);
			return "f:/jsps/user/regist.jsp";
		}
//		/**
//		 * 发邮件
//		 * 1.准备配置文件
//		 * 2.获取配置文件内容
//		 */
//		Properties properties = new Properties();
//		properties.load(this.getClass().getResourceAsStream("email_template.properties"));
//		String host = properties.getProperty("host");
//		String uname = properties.getProperty("uname");
//		String pwd = properties.getProperty("pwd");
//		String from = properties.getProperty("from");//发件人
//		String to = form.getEmail();//收件人
//		String subject = properties.getProperty("subject");//邮件主题
//		String content = properties.getProperty("content");//邮件内容
//		content=MessageFormat.format(content,form.getCode());//配置文件里content设置了占位符，替换为code
//		Session session=MailUtils.createSession(host, username, pwd);
//		
//	
//		Mail mail = new Mail(from,to,subject,content);
//		try {
//		
//			MailUtils.send(session, mail);//发邮件
//		} catch (Exception e) {
//			// TODO: handle exception
//		}

		// 执行到这里说明userService执行成功 没有抛出异常
		// 保存成功信息
		// 转发到msg.jsp
		request.setAttribute("msg", "恭喜，注册成功！请马上到邮箱激活");
		return "f:/jsps/msg.jsp";

	}

}
