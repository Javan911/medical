package com.gxuwz.medical.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gxuwz.medical.domain.role.Role;

/**
 * 角色管理控制模块
 * @ClassName: RoleServlet
 * @author SunYi
 * @Date 2019年4月3日上午9:01:16
 */
public class RoleServlet extends BaseServlet {
  
	private static final long serialVersionUID = -1208034203288240913L;
	
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
		if("list".equals(m)){
			list(req, resp);
		}else if("add".equals(m)){
			add(req, resp);
		}else if("edit".equals(m)){
			edit(req, resp);
			list(req, resp);
		}else if("input".equals(m)){
			process(req, resp, "/page/role/role_add.jsp");
		}else if("get".equals(m)){
			process(req, resp, "/page/role/role_edit.jsp");
		}else if("del".equals(m)){
			del(req, resp);
		}
	}
	
	/**
	 * 请求转发到角色列表页面
	 * @author SunYi
	 * @Date 2019年4月3日上午9:02:00
	 * @return void
	 */
	private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		  process(req, resp, "/page/role/role_list.jsp");
	}
	
	/**
	 * 更新角色信息
	 * @author SunYi
	 * @Date 2019年4月3日上午9:02:37
	 * @return void
	 */
	private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		// 1:接收页面参数
		String roleid = req.getParameter("roleid");
		String rolename = req.getParameter("rolename");
		// 页面权限复选框勾选的值
		String fid = req.getParameter("fid");
		// 2:实例化角色Role
		try{
			// 创建权限编号数组
			String[] menuids = null;
			// 以逗号分割fid得到权限编号数组
			if(fid != null && !"".equals(fid)){
				menuids = fid.split(",");
			}
			// 根据页面传过来的值来实例化一个role对象
		    Role role = new Role(roleid,rolename);
		    // 用创建好的role对象去执行预更新操作
		    role.toUpdateRole(menuids);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	 /**
	  * 添加角色
	  * @author SunYi
	  * @Date 2019年4月3日上午9:09:19
	  * @return void
	  */
	private void add(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException{
		// 1:接收页面参数
		String roleid = req.getParameter("roleid");
		String roleName = req.getParameter("roleName");
		// 接收页面权限复选框勾选的值
		String fid = req.getParameter("fid");
		// 2:实例化角色Role
		try{
			// 创建权限编号数组
			String[] menuids = null;
			// 以逗号分割fid得到权限数组
			if(fid != null && !"".equals(fid)){
				menuids = fid.split(",");
			}
			// 根据页面传过来的值来实例化一个role对象
		    Role role=new Role(roleid,roleName);
		    // 用创建好的role对象去执行预添加操作
		    role.toAddRole(roleName, menuids);
		    // 请求转发到角色列表页面
		    list(req, resp);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	 /**
	  * 删除角色
	  * @author SunYi
	  * @Date 2019年4月3日上午9:12:26
	  * @return void
	  */
	private void del(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		try{
			// 1:接收页面提交的roleid
			String roleid = req.getParameter("roleid");
			// 2:实例化角色Role
			Role role = new Role();
			// 3:根据页面提交的roleid执行预删除操作
			role.toDeleteRole(roleid);
			resp.getWriter().write("true");
		}catch(Exception e){
			e.printStackTrace(); 
		}
	}
    
}
