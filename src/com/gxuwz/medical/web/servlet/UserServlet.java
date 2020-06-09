package com.gxuwz.medical.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gxuwz.medical.config.Constant;
import com.gxuwz.medical.dao.UserDao;
import com.gxuwz.medical.domain.user.User;

/**
 * 用户管理控制模块
 * @ClassName: UserServlet
 * @author SunYi
 * @Date 2019年4月1日下午10:21:58
 */
public class UserServlet extends BaseServlet {
	
	private static final long serialVersionUID = 5638242492222088715L;
	
	// 覆写doGet方法
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 把请求转给doPost方法处理
		this.doPost(req, resp);
	}

	// 覆写doPost方法
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 获取页面表单提交的方法名称
		String m = req.getParameter("m");
		// 根据页面表单提交的方法名称请求转发到相应的页面
		if ("list".equals(m)) {
			process(req, resp, "/page/user/user_list.jsp");
		} else if ("input".equals(m)) {
			process(req, resp, "/page/user/user_add.jsp");
		} else if ("get".equals(m)) {
			process(req, resp, "/page/user/user_edit.jsp");
		} else if ("add".equals(m)) {
			add(req, resp);
			process(req, resp, "/page/user/user_list.jsp");
		} else if ("edit".equals(m)) {
			edit(req, resp);
			process(req, resp, "/page/user/user_list.jsp");
		} else if ("del".equals(m)) {
			del(req, resp);
		} else if ("search".equals(m)) {
			search(req, resp);
			process(req, resp, "/page/user/user_list.jsp");
		} else{
			error(req, resp);
		}  
	}
	
	/**
	 * 添加用户
	 * @author SunYi
	 * @Date 2019年4月1日下午11:38:43
	 * @return void
	 */
	protected void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 获取表单提交的数据
		String userid = req.getParameter("userid");
		String pwd = req.getParameter("pwd");
		String fullname = req.getParameter("fullname");
		String agencode = req.getParameter("agencode");
		String status = "1";	// 默认正常状态(1:正常    0:停用)
		// 获取页面提交的角色id数组
		String[] roleids = req.getParameterValues("roleids");
		// 1:实例化User
		User user =new User(userid, pwd, fullname, status, agencode);
		// 2:调用方法
		try{
			// 执行预添加用户方法
			user.toAddUser(roleids);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 更新用户
	 * @author SunYi
	 * @Date 2019年4月1日下午11:47:01
	 * @return void
	 */
	protected void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 接收表单提交的数据
		String userid = req.getParameter("userid");
		String pwd = req.getParameter("pwd");
		String fullname = req.getParameter("fullname");
		String agencode = req.getParameter("agencode");
		String status ="1";	//默认正常
		// 获取表单提交的角色id数组
		String[] roleids = req.getParameterValues("roleids");
		// 1:实例化User
		User user = new User(userid, pwd, fullname, status, agencode);
		// 2:调用方法
		try{
			// 执行预更新方法
			user.toUpdateUser(roleids);
			// 请求转发
			process(req, resp, "/page/user/user_list.jsp");
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
	
	/**
	 * 删除用户
	 * @author SunYi
	 * @Date 2019年4月1日下午11:48:20
	 * @return void
	 */
	protected void del(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 接收表单提交的数据
		String userid = req.getParameter("userid");
		// 1:实例化User
		User user =new User();
		// 2:调用方法
		try{
			// 执行预删除用户方法
			user.toDeleteUser(userid);
			resp.getWriter().write("true");
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
	
	 /**
	  * 多条件查询用户
	  * @author SunYi
	  * @Date 2019年4月6日下午3:31:30
	  * @return void
	  */
	protected void search(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 接收表单提交的数据
		String userid = req.getParameter("userid");
		String fullname = req.getParameter("fullname");
		// 1:实例化UserDao
		UserDao userDao =new UserDao();
		// 2:调用方法
		try{
			StringBuffer sql = new StringBuffer("select * from t_user where 1=1");
			if(userid != null && !"".equals(userid)) {
				sql.append("and userid like '%"+ userid +"%'");
			}
			if(fullname != null && !"".equals(fullname)) {
				sql.append("and fullname like '%"+ fullname +"%'");
			}
			Object[] params = {userid,fullname};
			userDao.getUserByPage(sql.toString(), params, 1, Constant.ROW);
			req.setAttribute("userid", userid);
			req.setAttribute("fullname", fullname);
		}catch(Exception e){
			e.printStackTrace();
		}	
	}

}
