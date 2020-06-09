package com.gxuwz.medical.web.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gxuwz.medical.domain.medical.Medical;

/**
 * 医疗机构管理控制模块
 * @ClassName: MedicalServlet
 * @author SunYi
 * @Date 2019年4月24日下午5:44:25
 */
public class MedicalServlet extends BaseServlet {

	private static final long serialVersionUID = 4520134604702085369L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 接收表单提交的方法名
		String m = req.getParameter("m");
		// 根据方法名进行请求转发
		if("list".equals(m)){
			process(req, resp, "/page/medical/medical_list.jsp");
		}else if("input".equals(m)){
			process(req, resp, "/page/medical/medical_add.jsp");
		}else if("get".equals(m)){
			process(req, resp, "/page/medical/medical_edit.jsp");
		}else if("add".equals(m)){
			add(req, resp);
			process(req, resp, "/page/medical/medical_list.jsp");
		}else if("del".equals(m)){
			del(req, resp);
		} else if("edit".equals(m)){
			edit(req, resp);
		} else {
			error(req, resp);
		}
	}
	
	/**
	 * 添加医疗机构
	 * @author SunYi
	 * @Date 2019年4月24日下午6:23:33
	 * @return void
	 */
	private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		// 接收表单传过来的参数
		String jgbm = req.getParameter("jgbm");
		String zzjgbm = req.getParameter("zzjgbm");
		String jgmc = req.getParameter("jgmc");
		String dqbm = req.getParameter("dqbm");
		String areacode = req.getParameter("areacode");
		String lsgx = req.getParameter("lsgx");
		String jgjb = req.getParameter("jgjb");
		String sbddlx = req.getParameter("sbddlx");
		String pzddlx = req.getParameter("pzddlx");
		String ssjjlx = req.getParameter("ssjjlx");
		String wsjgdl = req.getParameter("wsjgdl");
		String wsjgxl = req.getParameter("wsjgxl");
		String zgdw = req.getParameter("zgdw");
		String kysjstr = req.getParameter("kysj");	// 日期
		String frdb = req.getParameter("frdb");
		String zczjstr = req.getParameter("zczj");	// 数值
		System.out.println("jgjb:"+jgjb);
		 try{
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			 // 将SimpleDateFormat格式的时间转换成Date类型
			 Date kysjdate = sdf.parse(kysjstr);
			 // 将接收的时间转换成SimpleDateFormat格式
			 String kysjsdf = sdf.format(kysjdate);
			 // 将String格式的时间转换成Date类型
			 Date kysj = sdf.parse(kysjsdf);
			 // 将接收的字符串转换成Double格式
			 double zczj = Double.parseDouble(zczjstr);
			 Medical model = new Medical(jgbm, zzjgbm, jgmc, dqbm, areacode, lsgx, jgjb, sbddlx, pzddlx, ssjjlx, wsjgdl, wsjgxl, zgdw, kysj, frdb, zczj);
			 // 调用预添加方法
			 model.toAddMedical();
		 }catch(Exception e){
			 e.printStackTrace();
		 }
	}
	
	/**
	 * 删除医疗机构
	 * @author SunYi
	 * @Date 2019年4月25日上午11:32:37
	 * @return void
	 */
	private void del(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		// 接收表单传过来的数据
		String jgbm = req.getParameter("jgbm");
		try {
			Medical medical = new Medical();
			boolean flag = medical.toDeleteMedical(jgbm);
			if(flag) {
				resp.getWriter().write("true");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 更新医疗机构信息
	 * @author SunYi
	 * @Date 2019年4月25日下午11:33:01
	 * @return void
	 */
	private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		// 接收表单传过来的参数
		String jgbm = req.getParameter("jgbm");
		String zzjgbm = req.getParameter("zzjgbm");
		String jgmc = req.getParameter("jgmc");
		String dqbm = req.getParameter("dqbm");
		String areacode = req.getParameter("areacode");
		String lsgx = req.getParameter("lsgx");
		String jgjb = req.getParameter("jgjb");
		String sbddlx = req.getParameter("sbddlx");
		String pzddlx = req.getParameter("pzddlx");
		String ssjjlx = req.getParameter("ssjjlx");
		String wsjgdl = req.getParameter("wsjgdl");
		String wsjgxl = req.getParameter("wsjgxl");
		String zgdw = req.getParameter("zgdw");
		String kysjstr = req.getParameter("kysj");	// 日期
		String frdb = req.getParameter("frdb");
		String zczjstr = req.getParameter("zczj");	// 数值
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			// 将SimpleDateFormat格式的时间转换成Date类型
			Date kysjdate = sdf.parse(kysjstr);
			// 将接收的时间转换成SimpleDateFormat格式
			String kysjsdf = sdf.format(kysjdate);
			// 将String格式的时间转换成Date类型
			Date kysj = sdf.parse(kysjsdf);
			// 将接收的字符串转换成Double格式
			double zczj = Double.parseDouble(zczjstr);
			Medical model = new Medical(jgbm, zzjgbm, jgmc, dqbm, areacode, lsgx, jgjb, sbddlx, pzddlx, ssjjlx, wsjgdl, wsjgxl, zgdw, kysj, frdb, zczj);
			boolean flag = model.toUpdateMedical();
			if(flag) {
				resp.getWriter().write("true");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
