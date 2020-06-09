package com.gxuwz.medical.web.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gxuwz.medical.dao.PersonDao;
import com.gxuwz.medical.database.DbUtil;
import com.gxuwz.medical.domain.area.Area;
import com.gxuwz.medical.domain.family.Family;
import com.gxuwz.medical.domain.person.Person;
import com.gxuwz.medical.domain.user.User;

/**
 * 家庭档案管理控制层
 * @ClassName: FamilyServlet
 * @author SunYi
 * @Date 2019年4月28日下午10:14:39
 */
public class FamilyServlet extends BaseServlet {

	private static final long serialVersionUID = 2156046758236335189L;

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
			process(req, resp, "/page/family/family_list.jsp");
		}else if("input".equals(m)){
			process(req, resp, "/page/family/family_add.jsp");
		}else if("get".equals(m)){
			process(req, resp, "/page/family/family_edit.jsp");
		}else if("add".equals(m)){
			add(req, resp);
			process(req, resp, "/page/family/family_list.jsp");
		}else if("del".equals(m)){
			del(req, resp);
		} else if("edit".equals(m)){
			edit(req, resp);
		} else if("detail".equals(m)){
			process(req, resp, "/page/family/family_detail.jsp");
		} else {
			error(req, resp);
		}
	}
	
	/**
	 * 添加家庭档案
	 * @author SunYi
	 * @Date 2019年4月28日下午11:24:18
	 * @return void
	 */
	private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		// 接收表单传过来的参数
		/** *****************************************家庭档案信息*****************************************  */
		String areacode = req.getParameter("areacode");
		// 将行政区域树的节点进行分割
		String[] familyAreacode = areacode.split(",");
		String countyCode = null;
		String townCode = null;
		String villageCode = null;
		String groupCode = null;
		// 家庭编号需要自动生成
		String familyCode = null;
		// 获取户主信息中的户主作为当前家庭档案的户主
		String houseHolder = req.getParameter("name");
		// 需要自动获取家庭人口数
		int familySize = 0;
		int agriculturalNum = Integer.parseInt(req.getParameter("agriculturalNum"));
		String householdType = req.getParameter("householdType");
		// 默认状态为启用
		int status = 1;
		String remark = req.getParameter("remark");
		
		/** *****************************************户主信息*****************************************  */
		// 需要自动生成农合证卡号
		String nongheCard = null;
		// 需要自动生成医疗证卡号
		String medicalCard = req.getParameter("medicalCard");
		// 关系为户主本人
		String relationship = "本人";
		String IdCard = req.getParameter("IdCard");
		String name = req.getParameter("name");
		String gender = req.getParameter("gender");
		// 需要进行格式转换
		String birthDateStr = req.getParameter("birthDate");
		String nation = req.getParameter("nation");
		String health = req.getParameter("health");
		String education = req.getParameter("education");
		String workType = req.getParameter("workType");
		String career = req.getParameter("career");
		String workUnit = req.getParameter("workUnit");
		String telephone = req.getParameter("telephone");
		String liveAddress = req.getParameter("liveAddress");
		// 获取连接
		Connection conn = DbUtil.getConn();
		try{
			// 开启手动提交事务
			conn.setAutoCommit(false);
			/** *********************************添加家庭档案**************************************/
			int isRural = Integer.parseInt(req.getParameter("isRural"));
			String address = "";
			// 遍历分割好的行政区域树数组，得到每个行政级别的编码
			for (int i = 0; i < familyAreacode.length; i++) {
				countyCode = familyAreacode[0];
				townCode = familyAreacode[1];
				villageCode = familyAreacode[2];
				groupCode = familyAreacode[3];
				
				// 根据选中的树节点获取家庭地址
				Area area = new Area(familyAreacode[i]);
				address += area.getAreaname();
			}
			// 获取当前用户作为登记员
			User user = (User) req.getSession().getAttribute("user");
			String registrar = user.getFullname();
			// 获取当前系统时间作为家庭档案创建时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date createTime = sdf.parse(sdf.format(new Date()));
			Family model = new Family(countyCode, townCode, villageCode, groupCode, familyCode, houseHolder, 
					familySize, agriculturalNum, address, createTime, registrar, householdType, isRural, status, remark);
			// 调用预添加方法
			model.toAddFamily();
			
			/** *********************************添加户主*****************************************/
			// 获取已生成的家庭编号
			familyCode = model.getFamilyCode();
			// 需要自动生成个人户内编号
			String houseNum = null;
			// 接收出生日期
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
			Date birthDatep = sd.parse(birthDateStr);
			String birthDateSdf = sd.format(birthDatep);
			Date birthDate = sd.parse(birthDateSdf);
			// 根据出生日期自动计算年龄
			PersonDao personDao = new PersonDao();
			int age = personDao.getAgeByBirthDay(birthDate);
			// 获取家庭档案中的户口性质作为户主的户口性质
			String isRuralHukou = req.getParameter("isRural");
			Person person = new Person(familyCode, nongheCard, medicalCard, houseNum, relationship, IdCard, 
					name, gender, age, birthDate, nation, health, education, workType, isRuralHukou, career,
					workUnit, telephone, liveAddress);
			// 调用预添加方法
			person.toAddPerson();
			// 提交事务
			conn.commit();
		}catch(Exception e){
			try {
				// 事务回滚
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			DbUtil.close(conn);
		}
	}
	
	/**
	 * 更新家庭档案信息
	 * @author SunYi
	 * @Date 2019年4月29日上午10:40:53
	 * @return void
	 */
	private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		// 接收表单传过来的参数
		String areacode = req.getParameter("areacode");
		String oldFamilyCode = req.getParameter("familyCode");
		String houseHolder = req.getParameter("houseHolder");
		/*String address = req.getParameter("address");*/
		String householdType = req.getParameter("householdType");
		String remark = req.getParameter("remark");
		// 家庭编号需要根据行政区域编号自动生成，家庭地址自动获取
		String countyCode = null;
		String townCode = null;
		String villageCode = null;
		String groupCode = null;
		String familyCode = null;
		String address = "";
		try {
			int agriculturalNum = Integer.parseInt(req.getParameter("agriculturalNum"));
			int isRural = Integer.parseInt(req.getParameter("isRural"));
			int status = Integer.parseInt(req.getParameter("status"));
			Family model = null;
			if(areacode != null && !"".equals(areacode)) {
				// 将行政区域树的节点进行分割
				String[] familyAreacode = areacode.split(",");
				// 遍历分割好的行政区域树数组，得到每个行政级别的编码
				for (int i = 0; i < familyAreacode.length; i++) {
					countyCode = familyAreacode[0];
					townCode = familyAreacode[1];
					villageCode = familyAreacode[2];
					groupCode = familyAreacode[3];
					// 根据选中的树节点获取家庭地址
					Area area = new Area(familyAreacode[i]);
					address += area.getAreaname();
				}
				// 实例化有家庭住址的对象
				model = new Family(countyCode, townCode, villageCode, groupCode, familyCode, houseHolder, agriculturalNum, address, householdType, isRural, status, remark);
			}else {
				// 实例化无家庭住址的对象
				model = new Family(houseHolder, agriculturalNum, householdType, isRural, status, remark);
			}
			// 调用预更新方法
			boolean flag = model.toUpdateFamily(oldFamilyCode);
			if(flag) {
				resp.getWriter().write("true");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除家庭档案信息
	 * @author SunYi
	 * @Date 2019年4月29日下午1:39:58
	 * @return void
	 */
	private void del(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		// 接收表单传过来的数据
		String familyCode = req.getParameter("familyCode");
		try {
			Family family = new Family();
			boolean flag = family.toDeleteFamily(familyCode);
			if(flag) {
				resp.getWriter().write("true");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
