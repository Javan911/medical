package com.gxuwz.medical.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.gxuwz.medical.domain.menu.Menu;
import com.gxuwz.medical.domain.menu.MenuTree;

/**
 * 权限管理控制模块
 * @ClassName: MenuServlet
 * @author SunYi
 * @Date 2019年4月3日下午4:07:04
 */
public class MenuServlet extends BaseServlet {

	private static final long serialVersionUID = -6080124219956648275L;

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
		if ("tree".equals(m)) {
			tree(req, resp);
		} else if ("list".equals(m)) {
			process(req, resp, "/page/menu/menu_list.jsp");
		} else if ("input".equals(m)) {
			process(req, resp, "/page/menu/menu_add.jsp");
		} else if ("add".equals(m)) {
			add(req, resp);
			process(req, resp, "/page/menu/menu_list.jsp");
		} else if ("edit".equals(m)) {
			edit(req, resp);
			process(req, resp, "/page/menu/menu_list.jsp");
		} else if ("del".equals(m)) {
			del(req, resp);
		} else if ("get".equals(m)) {
			process(req, resp, "/page/menu/menu_edit.jsp");
		}
	}

	/**
	 * 构造权限树
	 * @author SunYi
	 * @Date 2019年4月3日下午4:08:47
	 * @return void
	 */
	protected void tree(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 接收页面提交的参数
		String treeID = req.getParameter("treeID");
		String roleid = req.getParameter("roleid");
		try {
			// 声明权限菜单树对象
			MenuTree tree = null;
			if (roleid == null || "".equals(roleid)) {
				// 构造根节点为treeID的权限菜单树
				tree = new MenuTree(treeID);
			} else {
				// 构造带角色的权限树
				tree = new MenuTree(treeID, roleid);
			}
			// 将获取到的子权限菜单节点集合转成Json格式
			String respBody = JSON.toJSONString(tree.getChildNodeList());
			// 设置响应编码格式
			resp.setCharacterEncoding("utf-8");
			PrintWriter out = resp.getWriter();
			// 响应Json格式的子权限菜单节点给页面
			out.print(respBody);
			// 清除缓冲区
			out.flush();
			// 关闭缓冲区
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	 /**
	  * 添加子一级权限菜单
	  * @author SunYi
	  * @Date 2019年4月3日下午4:15:30
	  * @return void
	  */
	protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// 包含ID+level组合的参数值
			String chooseid = request.getParameter("chooseid");
			String menupid = chooseid.split("-")[0];
			String plevel = chooseid.split("-")[1];
			String menuname = request.getParameter("menuname");
			String url = request.getParameter("url");
			// 实例化Menu
			int level = Integer.parseInt(plevel);
			Menu menu = new Menu(menuname, url);
			// 添加到父节点+级别
			menu.toAddMenu(menupid, level);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新权限
	 * @author SunYi
	 * @Date 2019年4月3日下午4:18:22
	 * @return void
	 */
	protected void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// 包含ID+level组合的参数值
			String menuid = request.getParameter("menuid");
			String menuname = request.getParameter("menuname");
			String url = request.getParameter("url");
			// 实例化Menu
			Menu menu = new Menu(menuid);
			// 更新权限树
			menu.toUpdateMenu(menuname, url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	 /**
	  * 删除权限
	  * @author SunYi
	  * @Date 2019年4月3日下午4:19:17
	  * @return void
	  */
	protected void del(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			// 获取页面提交的id
			String menuid = req.getParameter("menuid");
			// 实例化Menu
			Menu menu = new Menu();
			// 根据页面提交的id删除权限信息
			menu.toDeleteMenu(menuid);
			resp.getWriter().write("true");
			// 请求转发
			//process(req, resp, "/page/menu/menu_list.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
