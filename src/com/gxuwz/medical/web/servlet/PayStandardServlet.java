package com.gxuwz.medical.web.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gxuwz.medical.domain.family.Family;
import com.gxuwz.medical.domain.payStandard.PayStandard;
import com.gxuwz.medical.domain.person.Person;
import com.gxuwz.medical.domain.user.User;

/**
 * 参合缴费标准管理控制层
 * @ClassName: PayStandardServlet
 * @author SunYi
 * @Date 2019年5月12日下午10:32:14
 */
public class PayStandardServlet extends BaseServlet {

	private static final long serialVersionUID = 620781449705643466L;
	
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
		if("list".equals(m)){
			process(req, resp, "/page/payStandard/payStandard_list.jsp");
		}else if("get".equals(m)){
			process(req, resp, "/page/payStandard/payStandard_edit.jsp");
		}else if("del".equals(m)){
			del(req, resp);
		} else if("edit".equals(m)){
			edit(req, resp);
		} else {
			error(req, resp);
		}
	}
	
	/**
	 * 参合缴费标准管理
	 * @author SunYi
	 * @Date 2019年5月12日下午11:39:19
	 * @return void
	 */
	private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		// 1:接收表单传过来的值
		String annual = req.getParameter("annual");
		String startTimeStr = req.getParameter("startTime");
		String endTimeStr = req.getParameter("endTime");
		try {
			double account = Double.parseDouble(req.getParameter("account"));
			// 将接收的时间转换成Date类型
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date startTime = sdf.parse(startTimeStr);
			Date endTime = sdf.parse(endTimeStr);
			// 将当前登录用户作为操作员
			User user = (User) req.getSession().getAttribute("user");
			String operator = user.getFullname();
			
			// 如果当前年度已存在则执行更新操作，否则执行添加操作
			PayStandard model = new PayStandard(annual);
			if(model.getAccount() == 0 || "".equals(model.getAccount())) {
				// 给参数赋值
				PayStandard psd = new PayStandard(annual, account, startTime, endTime, operator);
				// 执行预添加方法
				psd.toAddPayStandard();
			}else {
				// 给参数赋值
				PayStandard psd = new PayStandard(annual, account, startTime, endTime, operator);
				// 执行预更新方法
				psd.toUpdatePayStandard();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除参合缴费标准
	 * @author SunYi
	 * @Date 2019年5月13日上午11:50:22
	 * @return void
	 */
	private void del(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		// 接收表单传过来的数据
		String annual = req.getParameter("annual");
		try {
			PayStandard psd = new PayStandard(annual);
			boolean flag = psd.toDeletePayStandard();
			if(flag) {
				resp.getWriter().write("true");
			}else {
				resp.getWriter().write("false");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
