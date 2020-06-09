package com.gxuwz.medical.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.gxuwz.medical.domain.area.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 行政区域管理控制模块
 * @ClassName: AreaServlet
 * @author SunYi
 * @Date 2019年4月3日下午11:44:49
 */
public class AreaServlet extends BaseServlet {

	private static final long serialVersionUID = -8371117544635964909L;

	// 覆写doGet方法
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 把请求转给doPost方法处理
		this.doPost(request, response);
	}

	// 覆写doPost方法
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 获取页面表单提交的方法名称
		String m = req.getParameter("m");
		// 根据页面表单提交的方法名称请求转发到相应的页面
		if("tree".equals(m)){
		    tree(req, resp);
		}else if ("list".equals(m)) {
			process(req, resp, "/page/area/area_list.jsp");
		} else if ("input".equals(m)) {
			process(req, resp, "/page/area/area_add.jsp");
		} else if ("get".equals(m)) {
			process(req, resp, "/page/area/area_edit.jsp");
		} else if ("edit".equals(m)) {
			edit(req, resp);
			process(req, resp, "/page/area/area_list.jsp");
		} else if("add".equals(m)){
		    add(req, resp);
			process(req, resp, "/page/area/area_list.jsp");
		} else if("check".equals(m)){
			check(req, resp);
		} else if("del".equals(m)){
		    del(req, resp);
		} else {
			error(req, resp);
		}
	}
	
	
	/**
	 * 构造行政区域树
	 * @author SunYi
	 * @Date 2019年4月4日下午4:08:51
	 * @return void
	 */
	protected void tree(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 接收页面提交的参数
		String treeID = req.getParameter("treeID");
		String areacode = req.getParameter("areacode");
		try {
			// 声明行政区域树对象
			AreaTree tree = null;
			if (areacode == null || "".equals(areacode)) {
				// 构造根节点为treeID的行政区域树
				tree = new AreaTree(treeID);
			} else {
				// 构造带指定行政区域编号的行政区域树
				tree = new AreaTree(treeID, areacode);
			}
			// 将获取到的子行政区域节点集合转成Json格式
			String respBody = JSON.toJSONString(tree.getChildNodeList());
			// 设置响应编码格式
			resp.setCharacterEncoding("utf-8");
			PrintWriter out = resp.getWriter();
			// 响应Json格式的子行政区域节点给页面
			out.print(respBody);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	 /**
	  * 添加行政区域
	  * @author SunYi
	  * @Date 2019年4月3日下午11:46:04
	  * @return void
	  */
	private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			// 接收行政区域编码+行政区域名称，行政区域名称有可能出现中文乱码
			String areaname = req.getParameter("areaname");
			// 接收页面权限复选框勾选的值
			String areacode = req.getParameter("areacode");
			boolean flag = check(req, resp);
			if(flag) {
				// 调用预添加子行政区域方法
				Area area = new Area();
				area.toAddArea(areacode, areaname);
			}else {
				resp.getWriter().write("addFail");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	 /**
	  * 更新行政区域信息
	  * @author SunYi
	  * @Date 2019年4月5日下午4:24:57
	  * @return void
	  */
	private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			// 接收行政区域编码+行政区域名称，行政区域名称有可能出现中文乱码
			String areacode = req.getParameter("areacode");
			String areaname = req.getParameter("areaname");
			
			boolean flag = check(req, resp);
			if(flag) {
				// 调用预更新行政区域方法
				Area area = new Area();
				area.toUpdateArea(areacode, areaname);
			}else {
				resp.getWriter().write("addFail");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	  * 校验行政区域名是否已存在
	  * @author SunYi
	  * @Date 2019年4月5日下午4:24:57
	  * @return void
	  */
	private boolean check(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		boolean result = false;
		try {
			// 接收页面传过来的行政区域名称
			String areaname = req.getParameter("areaname");
			// 执行校验行政区域名方法
			Area area = new Area();
			boolean flag = area.checkAreaname(areaname);
			if(flag) {
				resp.getWriter().write("trueName");
				return false;
			} else {
				resp.getWriter().write("falseName");
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	 /**
	  * 删除行政区域
	  * @author SunYi
	  * @Date 2019年4月5日下午5:29:30
	  * @return void
	  */
	private void del(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			// 设置请求编码格式
			req.setCharacterEncoding("utf-8");
			// 设置响应编码格式
			resp.setContentType("text/html;charset=utf-8");
			// 接收页面提交的行政区域编码
			String areacode = req.getParameter("areacode");
			Area area = new Area(areacode);
			// 1:获取当前行政区域的级别
			int grade = area.getGrade();
			// 2:查询该行政区域下的子行政区域
			List<Area> areaList = area.getAreaChildren();
			// 1_1:如果为顶级行政区域，则不能删除
			if(grade == 1) {
				resp.getWriter().write("gfalse");
			// 2_1:如果包含子行政区域，则不能删除
			} else if(areaList != null && !areaList.isEmpty()){
				resp.getWriter().write("afalse");
			} else {
				// 3:调用预删除行政区域方法
				area.toDeleteArea(areacode);
				resp.getWriter().write("true");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
