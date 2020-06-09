package com.gxuwz.medical.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gxuwz.medical.domain.menu.Menu;
import com.gxuwz.medical.domain.role.Role;
import com.gxuwz.medical.domain.user.User;
import com.gxuwz.medical.exception.UserNotFoundException;

/**
 * 登录验证控制模块
 * @ClassName: LoginServlet
 * @author SunYi
 * @Date 2019年4月3日下午2:35:49
 */
public class LoginServlet extends BaseServlet {

	private static final long serialVersionUID = 7279138098299110478L;
	
	// 覆写doGet方法
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 把请求转给doPost方法处理
		this.doPost(request, response);
	}

	// 覆写doPost方法
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 执行login方法
		login(request, response);
	}
	
	 /**
	  * 用户登录
	  * @author SunYi
	  * @Date 2019年4月3日下午4:22:20
	  * @return void
	  */
	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 设置默认请求转发页面
		String path = "login.jsp";
		try{
			// 接收页面表单提交的用户名和密码
			String userid = request.getParameter("userid");
			String pwd = request.getParameter("pwd");
			// 实例化User,根据当前获取到的userid和pwd进行查询
			User user = new User(userid,pwd);
			// 遍历当前用户的角色Set集合
			for(Role role:user.getRoles()){
				// 遍历当前用户角色对应的权限Set集合
				for(Menu menu:role.getMenus()){
					// 接收权限编号
					String name = menu.getMenuid();
					String value = menu.getMenuid();
					// 将遍历得到的权限编号存进session对象
					request.getSession().setAttribute(name, value);
				}
			}
			// 获取当前登录用户的姓名并存进session对象
			request.getSession().setAttribute("user", user);
			// 设置请求转发路径
			path = "index.jsp";
		}catch(UserNotFoundException e){
			// 设置请求转发路径
			path = "login.jsp";
		}
		 // 执行请求转发操作
		process(request, response, path);
	}


}
