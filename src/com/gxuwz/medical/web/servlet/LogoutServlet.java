package com.gxuwz.medical.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户退出登录
 * @ClassName: LogoutServlet
 * @author SunYi
 * @Date 2019年4月3日下午4:29:17
 */
public class LogoutServlet extends BaseServlet {

	private static final long serialVersionUID = 400062772531230333L;

	// 覆写doGet方法
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 把请求转给doPost方法处理
		this.doPost(request, response);
	}

	// 覆写doPost方法
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 设置请求转发页面
	    String path = "login.jsp";
	    // 获取并销毁session
	    request.getSession().invalidate();
	    // 执行请求转发操作
	    process(request, response, path);
	}
	
}
