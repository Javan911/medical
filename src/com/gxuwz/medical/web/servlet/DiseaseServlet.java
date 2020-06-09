package com.gxuwz.medical.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gxuwz.medical.domain.disease.Disease;

public class DiseaseServlet extends BaseServlet {

	private static final long serialVersionUID = -6890417744105693033L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 获取页面传过来的方法名称
		String m = req.getParameter("m");
		// 进行请求转发
		if("list".equals(m)) {
			list(req, resp);
		} else if("input".equals(m)) {
			process(req, resp, "/page/disease/disease_add.jsp");
		} else if("add".equals(m)) {
			add(req, resp);
		} else if("check".equals(m)) {
			check(req, resp);
		} else if("del".equals(m)) {
			del(req, resp);
		} else if("get".equals(m)) {
			process(req, resp, "/page/disease/disease_edit.jsp");
		} else if("edit".equals(m)) {
			edit(req, resp);
			list(req, resp);
		} else {
			error(req, resp);
		}
	}

	

	/**
	 * 请求转发至慢性病分类管理列表页面
	 * @author SunYi
	 * @Date 2019年4月12日下午1:48:29
	 * @return void
	 */
	private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp, "/page/disease/disease_list.jsp");
	}
	
	/**
	 * 添加慢性病
	 * @author SunYi
	 * @Date 2019年4月12日下午2:59:41
	 * @return void
	 */
	private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			// 接收表单数据
			String diseaseCode = req.getParameter("diseaseCode");
			String pinyinCode = req.getParameter("pinyinCode");
			String diseaseName = req.getParameter("diseaseName");
			// 执行预添加方法
			Disease dis = new Disease();
			boolean flag = check(req, resp);
			if(flag) {
				dis.toAddDisease(diseaseCode, pinyinCode, diseaseName);
				list(req, resp);
			}else {
				resp.getWriter().write("addFail");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 校验慢性病编码和名称是否已存在
	 * @author SunYi
	 * @Date 2019年4月12日下午3:28:46
	 * @return void
	 */
	private boolean check(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		boolean result = false;
		try {
			// 接收表单提交的数据
			String diseaseCode = req.getParameter("diseaseCode");
			String diseaseName = req.getParameter("diseaseName");
			
			// 1:判断慢性病编码是否已存在
			if(diseaseCode != null && !"".equals(diseaseCode)) {
				Disease dis = new Disease(diseaseCode);
				if(dis.getDiseaseCode() == diseaseCode) {
					resp.getWriter().write("falseCode");
					result = true;
				}else {
					resp.getWriter().write("trueCode");
					return false;
				}
			}
			
			// 2:判断慢性病名称是否已存在
			if(diseaseName != null && !"".equals(diseaseName)) {
				Disease dis = new Disease();
				boolean flag = dis.checkDiseaseName(diseaseName);
				if(flag) {
					resp.getWriter().write("trueName");
					return false;
				}else {
					resp.getWriter().write("falseName");
					result = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 删除慢性病信息
	 * @author SunYi
	 * @Date 2019年4月12日下午10:53:28
	 * @return void
	 */
	private void del(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			// 1:接收表单数据
			String diseaseCode = req.getParameter("diseaseCode");
			// 2:执行预删除操作
			Disease dis = new Disease();
			dis.toDeleteDisease(diseaseCode);
			resp.getWriter().write("true");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 更新慢性病信息
	 * @author SunYi
	 * @Date 2019年4月12日下午11:41:02
	 * @return void
	 */
	private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			// 接收表单数据
			int id = Integer.parseInt(req.getParameter("id"));	// 要修改的慢性病编码对应的序号
			String diseaseCode = req.getParameter("diseaseCode"); // 修改后的慢性病编码
			String pinyinCode = req.getParameter("pinyinCode");
			String diseaseName = req.getParameter("diseaseName");
			// 执行预更新方法
			Disease dis = new Disease();
			dis.toUpdateDisease(id, diseaseCode, pinyinCode, diseaseName);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
