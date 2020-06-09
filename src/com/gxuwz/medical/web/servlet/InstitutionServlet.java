package com.gxuwz.medical.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gxuwz.medical.domain.area.Area;
import com.gxuwz.medical.domain.institution.Institution;

public class InstitutionServlet extends BaseServlet {

	private static final long serialVersionUID = -7905661957873926643L;
	
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
		if("list".equals(m)) {
			list(req, resp);
		} else if("input".equals(m)) {
			process(req, resp, "/page/institution/institution_add.jsp");
		} else if("add".equals(m)) {
			add(req, resp);
			list(req, resp);
		} else if("check".equals(m)) {
			check(req, resp);
		} else if("del".equals(m)) {
			del(req, resp);
		} else if("get".equals(m)) {
			process(req, resp, "/page/institution/institution_edit.jsp");
		} else if("edit".equals(m)) {
			edit(req, resp);
			list(req, resp);
		} else {
			error(req, resp);
		}
	}
	

	/**
	  * 请求转发到农合机构列表页面
	  * @author SunYi
	  * @Date 2019年4月7日下午2:55:11
	  * @return void
	  */
	private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp, "/page/institution/institution_list.jsp");
	}
	
	 /**
	  * 添加农合机构
	  * @author SunYi
	  * @Date 2019年4月7日下午5:29:27
	  * @return void
	  */
	private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			// 接收表单提交的数据
			String areacode = req.getParameter("areacode");
			String institutionCode = req.getParameter("institutionCode");
			String institutionName = req.getParameter("institutionName");
			// 获取指定行政编号的行政区域级别
			Area area = new Area(areacode);
			int grade = area.getGrade();
			// 执行预添加操作
			boolean flag = check(req, resp);
			if(flag) {
				Institution ins = new Institution();
				ins.toAddInstitution(areacode, institutionCode, institutionName, grade);
			}else {
				resp.getWriter().write("addFail");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 校验农合机构名称和同一级别的农合机构是否存在
	 * @author SunYi
	 * @Date 2019年4月11日上午10:19:38
	 * @return void
	 */
	private boolean check(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		boolean result = false;
		try {
			// 接收表单提交的数据
			//String areacode = req.getParameter("areacode");
			String institutionName = req.getParameter("institutionName");
			// 判断农合机构名称是否已存在
			if(institutionName != null && !"".equals(institutionName)) {
				Institution ins = new Institution();
				boolean flag = ins.checkInstitutionName(institutionName);
				if(flag) {
					resp.getWriter().write("trueName");
					return false;
				}else {
					resp.getWriter().write("falseName");
					result = true;
				}
			}
			
			// 判断同一级别农合机构是否已存在
			/*if(areacode != null && !"".equals(areacode)) {
				Institution ins = new Institution(areacode);
				Area area = new Area(areacode);
				// 行政编号不为空
				if(ins.getAreacode() == areacode) {
					resp.getWriter().write("trueArea");
					return false;
				// 行政级别为1-2
				}else if(area.getGrade() != 0 && area.getGrade() < 3){
					resp.getWriter().write("falseArea");
					result = true;
				}else {
					resp.getWriter().write("failArea");
					return false;
				}
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 删除农合机构
	 * @author SunYi
	 * @Date 2019年4月11日下午4:39:51
	 * @return void
	 */
	private void del(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String areacode = req.getParameter("areacode");
			
			// 检验是否产生报销记录
			
			// 执行预删除操作
			Institution ins = new Institution();
			boolean flag = ins.toDeleteInstitution(areacode);
			if(flag) {
				resp.getWriter().write("true");
			}else {
				resp.getWriter().write("false");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	 /**
	  * 更新农合机构信息
	  * @author SunYi
	  * @Date 2019年4月11日下午5:45:37
	  * @return void
	  */
	 private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			try {
				// 接收表单数据
				String oldAreacode = req.getParameter("oldAreacode"); // 旧的行政编号
				String areacode = req.getParameter("areacode");	// 新的行政编号
				String institutionCode = req.getParameter("institutionCode");
				String institutionName = req.getParameter("institutionName");
				// 执行预更新方法
				Institution ins = new Institution();
				ins.toUpdateInstitution(areacode, institutionCode, institutionName, oldAreacode);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
}
