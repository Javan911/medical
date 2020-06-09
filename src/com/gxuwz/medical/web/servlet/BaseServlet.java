package com.gxuwz.medical.web.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet抽象基类
 * @ClassName: BaseServlet
 * @author SunYi
 * @Date 2019年4月1日下午10:35:40
 */
public abstract class BaseServlet extends HttpServlet {

	private static final long serialVersionUID = 8196938314965941620L;
	
	/**
	 * 封装请求转发方法
	 * @author SunYi
	 * @Date 2019年4月1日下午10:32:08
	 * @return void
	 */
	protected void process(HttpServletRequest request,HttpServletResponse response, String path) throws ServletException,IOException {
		// 设置请求编码格式
		request.setCharacterEncoding("utf-8");
		// 设置响应编码格式
		response.setContentType("text/html;charset=utf-8");
		//	根据传过来的path进行请求转发
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);

	}
	
	/**
	 * 封装请求转发错误页面
	 * @author SunYi
	 * @Date 2019年4月1日下午10:34:12
	 * @return void
	 */
	protected void error(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		// 设置请求编码格式
		request.setCharacterEncoding("utf-8");
		// 设置响应编码格式
		response.setContentType("text/html;charset=utf-8");
		//	根据传过来的path进行请求转发
		// 请求转发到错误jsp页面
		RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
		rd.forward(request, response);
	}
	
}
