package com.gxuwz.medical.web.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gxuwz.medical.dao.PersonDao;
import com.gxuwz.medical.domain.family.Family;
import com.gxuwz.medical.domain.person.Person;

public class PersonServlet extends BaseServlet {

	private static final long serialVersionUID = 6293112371910627151L;
	
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
		// 根据方法名进行请求转发
		if("list".equals(m)){
			process(req, resp, "/page/person/person_list.jsp");
		}else if("input".equals(m)){
			process(req, resp, "/page/person/person_add.jsp");
		}else if("get".equals(m)){
			process(req, resp, "/page/person/person_edit.jsp");
		}else if("add".equals(m)){
			add(req, resp);
			process(req, resp, "/page/person/person_list.jsp");
		}else if("del".equals(m)){
			del(req, resp);
		} else if("edit".equals(m)){
			edit(req, resp);
		} else {
			error(req, resp);
		}
	}
	
	/**
	 * 添加参合农民档案
	 * @author SunYi
	 * @Date 2019年4月29日上午9:29:09
	 * @return void
	 */
	private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		// 接收表单传过来的参数
		String familyCode = req.getParameter("familyCode");
		String nongheCard = req.getParameter("nongheCard");
		String medicalCard = req.getParameter("medicalCard");
		String relationship = req.getParameter("relationship");
		String idCard = req.getParameter("idCard");
		String name = req.getParameter("name");
		String gender = req.getParameter("gender");
		String birthDateStr = req.getParameter("birthDate");
		String nation = req.getParameter("nation");
		String health = req.getParameter("health");
		String education = req.getParameter("education");
		String workType = req.getParameter("workType");
		// 根据家庭户口性质判断是否为农村户口
		String isRuralHukou = req.getParameter("isRuralHukou");
		String career = req.getParameter("career");
		String workUnit = req.getParameter("workUnit");
		String telephone = req.getParameter("telephone");
		String liveAddress = req.getParameter("liveAddress");

		try{
			// 需要自动生成户内编号
			String houseNum = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			// 将SimpleDateFormat格式的时间转换成Date类型
			Date birthDatep = sdf.parse(birthDateStr);
			// 将接收的时间转换成SimpleDateFormat格式
			String birthDateSdf = sdf.format(birthDatep);
			// 将String格式的时间转换成Date类型
			Date birthDate = sdf.parse(birthDateSdf);
			// 根据出生日期自动计算年龄
			PersonDao personDao = new PersonDao();
			int age = personDao.getAgeByBirthDay(birthDate);
			Person model = new Person(familyCode, nongheCard, medicalCard, houseNum, relationship, idCard, 
					name, gender, age, birthDate, nation, health, education, workType, isRuralHukou, career, 
					workUnit, telephone, liveAddress);
			// 调用预添加方法
			model.toAddPerson();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 更新参合人员档案信息
	 * @author SunYi
	 * @Date 2019年4月29日上午11:27:03
	 * @return void
	 */
	private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		// 接收表单传过来的参数
		String familyCode = req.getParameter("familyCode");
		String oldName = req.getParameter("oldName");
		String relationship = req.getParameter("relationship");
		String idCard = req.getParameter("idCard");
		String name = req.getParameter("name");
		String gender = req.getParameter("gender");
		String birthDateStr = req.getParameter("birthDate");
		String nation = req.getParameter("nation");
		String health = req.getParameter("health");
		String education = req.getParameter("education");
		String workType = req.getParameter("workType");
		String career = req.getParameter("career");
		String workUnit = req.getParameter("workUnit");
		String telephone = req.getParameter("telephone");
		String liveAddress = req.getParameter("liveAddress");
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			// 将SimpleDateFormat格式的时间转换成Date类型
			Date birthDatep = sdf.parse(birthDateStr);
			// 将接收的时间转换成SimpleDateFormat格式
			String birthDateSdf = sdf.format(birthDatep);
			// 将String格式的时间转换成Date类型
			Date birthDate = sdf.parse(birthDateSdf);
			// 根据出生日期计算年龄
			PersonDao personDao = new PersonDao();
			int age = personDao.getAgeByBirthDay(birthDate);
			Person model = new Person(relationship, idCard, name, gender, age, birthDate, nation, health, 
					education, workType, career, workUnit, telephone, liveAddress);
			// 调用预更新方法
			boolean flag = model.toUpdatePerson(familyCode, oldName);
			if(flag) {
				resp.getWriter().write("true");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除参合人员信息
	 * @author SunYi
	 * @Date 2019年4月29日下午1:57:38
	 * @return void
	 */
	private void del(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		// 接收表单传过来的数据
		String idCard = req.getParameter("idCard");
		try {
			// 获取当前要删除的用户信息
			Person person = new Person(idCard);
			String familyCode = person.getFamilyCode();
			String name = person.getName();
			// 判断当前用户是否户主(户主不能直接删除)
			Family family = new Family(familyCode);
			if(family.getHouseHolder() == name || name.equals(family.getHouseHolder())) {
				resp.getWriter().write("pfalse");
			}else {
				boolean flag = person.toDeletePerson();
				if(flag) {
					resp.getWriter().write("true");
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
