package com.gxuwz.medical.domain.person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gxuwz.medical.database.DbUtil;
import com.gxuwz.medical.domain.family.Family;

/**
 * 参合人员档案管理类
 * @ClassName: Person
 * @author SunYi
 * @Date 2019年4月27日下午11:17:08
 */
public class Person {
	
	// 家庭编号
	private String familyCode;
	// 农合证号
	private String nongheCard;
	// 医疗证号
	private String medicalCard;
	// 户内编号
	private String houseNum;
	// 与户主关系
	private String relationship;
	// 身份证号
	private String idCard;
	// 姓名
	private String name;
	// 性别
	private String gender;
	// 年龄
	private int age;
	// 出生日期
	private Date birthDate;
	// 民族
	private String nation;
	// 健康状况
	private String health;
	// 文化程度
	private String education;
	// 人员属性(从事工作类型)
	private String workType;
	// 是否农村户口
	private String isRuralHukou;
	// 职业
	private String career;
	// 工作单位
	private String workUnit;
	// 联系电话
	private String telephone;
	// 常住地址
	private String liveAddress;
	
	
	/*  ***********************************************实体类***********************************************  */
	
	public Person() {
		super();
	}
	public Person(String idCard) throws Exception {
		this.idCard = idCard;
		getPersonById();
		getPersonByNum();
	}
	public Person(String familyCode, String name) throws Exception {
		this.familyCode = familyCode;
		this.name = name;
		getPersonByFamily();
	}
	public Person(String relationship, String idCard, String name, String gender, int age, Date birthDate,
			String nation, String health, String education, String workType, String career,
			String workUnit, String telephone, String liveAddress) {
		super();
		this.relationship = relationship;
		this.idCard = idCard;
		this.name = name;
		this.gender = gender;
		this.age = age;
		this.birthDate = birthDate;
		this.nation = nation;
		this.health = health;
		this.education = education;
		this.workType = workType;
		this.career = career;
		this.workUnit = workUnit;
		this.telephone = telephone;
		this.liveAddress = liveAddress;
	}
	public Person(String familyCode, String nongheCard, String medicalCard, String houseNum, String relationship,
			String idCard, String name, String gender, int age, Date birthDate, String nation, String health,
			String education, String workType, String isRuralHukou, String career, String workUnit, String telephone,
			String liveAddress) {
		super();
		this.familyCode = familyCode;
		this.nongheCard = nongheCard;
		this.medicalCard = medicalCard;
		this.houseNum = houseNum;
		this.relationship = relationship;
		this.idCard = idCard;
		this.name = name;
		this.gender = gender;
		this.age = age;
		this.birthDate = birthDate;
		this.nation = nation;
		this.health = health;
		this.education = education;
		this.workType = workType;
		this.isRuralHukou = isRuralHukou;
		this.career = career;
		this.workUnit = workUnit;
		this.telephone = telephone;
		this.liveAddress = liveAddress;
	}
	public String getFamilyCode() {
		return familyCode;
	}
	public void setFamilyCode(String familyCode) {
		this.familyCode = familyCode;
	}
	public String getNongheCard() {
		return nongheCard;
	}
	public void setNongheCard(String nongheCard) {
		this.nongheCard = nongheCard;
	}
	public String getMedicalCard() {
		return medicalCard;
	}
	public void setMedicalCard(String medicalCard) {
		this.medicalCard = medicalCard;
	}
	public String getHouseNum() {
		return houseNum;
	}
	public void setHouseNum(String houseNum) {
		this.houseNum = houseNum;
	}
	public String getRelationship() {
		return relationship;
	}
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getHealth() {
		return health;
	}
	public void setHealth(String health) {
		this.health = health;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getWorkType() {
		return workType;
	}
	public void setWorkType(String workType) {
		this.workType = workType;
	}
	public String getIsRuralHukou() {
		return isRuralHukou;
	}
	public void setIsRuralHukou(String isRuralHukou) {
		this.isRuralHukou = isRuralHukou;
	}
	public String getCareer() {
		return career;
	}
	public void setCareer(String career) {
		this.career = career;
	}
	public String getWorkUnit() {
		return workUnit;
	}
	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getLiveAddress() {
		return liveAddress;
	}
	public void setLiveAddress(String liveAddress) {
		this.liveAddress = liveAddress;
	}
	@Override
	public String toString() {
		return "Person [familyCode=" + familyCode + ", nongheCard=" + nongheCard + ", medicalCard=" + medicalCard
				+ ", houseNum=" + houseNum + ", relationship=" + relationship + ", idCard=" + idCard + ", name=" + name
				+ ", gender=" + gender + ", age=" + age + ", birthDate=" + birthDate + ", nation=" + nation
				+ ", health=" + health + ", education=" + education + ", workType=" + workType + ", isRuralHukou="
				+ isRuralHukou + ", career=" + career + ", workUnit=" + workUnit + ", telephone=" + telephone
				+ ", liveAddress=" + liveAddress + "]";
	}
	
	
	/*  ***********************************************方法体***********************************************  */
	
	/**
	 * 获取指定身份证号的参合人员信息
	 * @author SunYi
	 * @Date 2019年4月27日下午11:48:21
	 * @return void
	 */
	private void getPersonById() throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			conn = DbUtil.getConn();
			pstmt = conn.prepareStatement("select * from t_person_archives where IdCard=? ");
			int index = 1;
			// 把页面传过来的treeID的值赋给占位符
			pstmt.setString(index, this.idCard);
			// 执行查询操作
			rs = pstmt.executeQuery();
			if(rs.next()){
				// 把查询到的结果赋给当前对象
				this.familyCode = rs.getString("familyCode");
				this.nongheCard = rs.getString("nongheCard");
				this.medicalCard = rs.getString("medicalCard");
				this.houseNum = rs.getString("houseNum");
				this.relationship = rs.getString("relationship");
				this.idCard = rs.getString("idCard");
				this.name = rs.getString("name");
				this.gender = rs.getString("gender");
				this.age = rs.getInt("age");
				this.birthDate = rs.getDate("birthDate");
				this.nation = rs.getString("nation");
				this.health = rs.getString("health");
				this.education = rs.getString("education");
				this.workType = rs.getString("workType");
				this.isRuralHukou = rs.getString("isRuralHukou");
				this.career = rs.getString("career");
				this.workUnit = rs.getString("workUnit");
				this.telephone = rs.getString("telephone");
				this.liveAddress = rs.getString("liveAddress");
			}
		}catch(SQLException e){
			throw new SQLException("Person with id " + this.idCard + " could not be loaded from the database.", e);
		}finally{
			DbUtil.close(rs, pstmt, conn);
		}
	}
	
	/**
	 * 获取指定个人户内编号的个人信息
	 * @author SunYi
	 * @Date 2019年5月12日下午3:15:55
	 * @return void
	 */
	private void getPersonByNum() throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			conn = DbUtil.getConn();
			pstmt = conn.prepareStatement("select * from t_person_archives where houseNum=? ");
			int index = 1;
			// 把页面传过来的treeID的值赋给占位符
			pstmt.setString(index, this.idCard);
			// 执行查询操作
			rs = pstmt.executeQuery();
			if(rs.next()){
				// 把查询到的结果赋给当前对象
				this.familyCode = rs.getString("familyCode");
				this.nongheCard = rs.getString("nongheCard");
				this.medicalCard = rs.getString("medicalCard");
				this.houseNum = rs.getString("houseNum");
				this.relationship = rs.getString("relationship");
				this.idCard = rs.getString("idCard");
				this.name = rs.getString("name");
				this.gender = rs.getString("gender");
				this.age = rs.getInt("age");
				this.birthDate = rs.getDate("birthDate");
				this.nation = rs.getString("nation");
				this.health = rs.getString("health");
				this.education = rs.getString("education");
				this.workType = rs.getString("workType");
				this.isRuralHukou = rs.getString("isRuralHukou");
				this.career = rs.getString("career");
				this.workUnit = rs.getString("workUnit");
				this.telephone = rs.getString("telephone");
				this.liveAddress = rs.getString("liveAddress");
			}
		}catch(SQLException e){
			throw new SQLException("Person with id " + this.idCard + " could not be loaded from the database.", e);
		}finally{
			DbUtil.close(rs, pstmt, conn);
		}
	}
	
	/**
	 * 获取指定用户名的个人档案信息集合
	 * @author SunYi
	 * @Date 2019年5月31日下午10:35:03
	 * @return void
	 */
	public List<Person> getPersonByName(String name) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Person> personList = new ArrayList<Person>();
		try{
			conn = DbUtil.getConn();
			pstmt = conn.prepareStatement("select * from t_person_archives where name like? ");
			int index = 1;
			// 把页面传过来的treeID的值赋给占位符
			pstmt.setString(index, "%"+name+"%");
			// 执行查询操作
			rs = pstmt.executeQuery();
			while(rs.next()){
				Person model = new Person();
				// 把查询到的结果赋给当前对象
				model.setFamilyCode(rs.getString("familyCode"));
				model.setNongheCard(rs.getString("nongheCard"));
				model.setMedicalCard(rs.getString("medicalCard"));
				model.setHouseNum(rs.getString("houseNum"));
				model.setRelationship(rs.getString("relationship"));
				model.setIdCard(rs.getString("idCard"));
				model.setName(rs.getString("name"));
				model.setGender(rs.getString("gender"));
				model.setAge(rs.getInt("age"));
				model.setBirthDate(rs.getDate("birthDate"));
				model.setNation(rs.getString("nation"));
				model.setHealth(rs.getString("health"));
				model.setEducation(rs.getString("education"));
				model.setWorkType(rs.getString("workType"));
				model.setIsRuralHukou(rs.getString("isRuralHukou"));
				model.setCareer(rs.getString("career"));
				model.setWorkUnit(rs.getString("workUnit"));
				model.setTelephone(rs.getString("telephone"));
				model.setLiveAddress(rs.getString("liveAddress"));
				personList.add(model);
			}
		}catch(SQLException e){
			throw new SQLException("Person with id " + name + " could not be loaded from the database.", e);
		}finally{
			DbUtil.close(rs, pstmt, conn);
		}
		return personList;
	}
	
	/**
	 * 预添加参合农民档案
	 * @author SunYi
	 * @Date 2019年4月29日上午12:03:05
	 * @return void
	 */
	public void toAddPerson()throws Exception{
		Connection conn = null;
		try{
			// 自动生成个人户内编号
			this.houseNum = createHouseNum();
			// 农合证号为个人户内编号
			this.nongheCard = this.houseNum;
			conn = DbUtil.getConn();
			// 1:开启手动提交事务
			conn.setAutoCommit(false);
			// 2:保存农民档案信息到数据库
			addPerson(conn);
			// 3:提交事务
			conn.commit();
		}catch (Exception e) {
			// 事务回滚
			if(conn!=null){
				conn.rollback();
			}
			throw e;
		}finally{
			DbUtil.close(conn);
		}
	}
	
	/**
	 * 添加参合农民档案到数据库
	 * @author SunYi
	 * @Date 2019年4月29日上午12:01:50
	 * @return void
	 */
	private void addPerson(Connection conn)throws SQLException{
		  PreparedStatement pstmt = null;
		  try{
			  // 创建SQL语句
			  StringBuffer sqlBuff = new StringBuffer("insert into t_person_archives(familyCode, nongheCard, medicalCard, "
			  		+ "houseNum, relationship, idCard, name, gender, age, birthDate, nation, health, education, workType, "
			  		+ "isRuralHukou, career, workUnit, telephone, liveAddress)");
			  sqlBuff.append(" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			  pstmt = conn.prepareStatement(sqlBuff.toString());
			  // 把当前对象的值赋给占位符
			  int index = 1;
			  pstmt.setString(index++, this.familyCode);
			  pstmt.setString(index++, this.nongheCard);
			  pstmt.setString(index++, this.medicalCard);
			  pstmt.setString(index++, this.houseNum);
			  pstmt.setString(index++, this.relationship);
			  pstmt.setString(index++, this.idCard);
			  pstmt.setString(index++, this.name);
			  pstmt.setString(index++, this.gender);
			  pstmt.setInt(index++, this.age);
			  pstmt.setDate(index++, new java.sql.Date(this.birthDate.getTime()));
			  pstmt.setString(index++, this.nation);
			  pstmt.setString(index++, this.health);
			  pstmt.setString(index++, this.education);
			  pstmt.setString(index++, this.workType);
			  pstmt.setString(index++, this.isRuralHukou);
			  pstmt.setString(index++, this.career);
			  pstmt.setString(index++, this.workUnit);
			  pstmt.setString(index++, this.telephone);
			  pstmt.setString(index++, this.liveAddress);

			  // 执行添加操作
			  pstmt.executeUpdate(); 
		  }catch(SQLException e){
			  throw e;
		  }finally{
			  DbUtil.close(pstmt);
		  } 
	}
	
	/**
	 * 自动生成个人户内编号
	 * @author SunYi
	 * @Date 2019年5月4日下午12:49:43
	 * @return String
	 */
	private String createHouseNum() throws SQLException{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			conn = DbUtil.getConn();
			String sql = "select max(houseNum) from t_person_archives where houseNum like'"+ this.familyCode +"%'" ;
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			String maxcode = "";
			Integer number = 1;
			if(rs.next()){
			   maxcode = rs.getString(1);
			}
			if(maxcode != null){
			  int beginIndex = maxcode.length()-2;
			  String no = maxcode.substring(beginIndex);
			  number = Integer.parseInt(no);
			  ++number;
			  //使用0补足位数
			  no = String.format("%02d", number);
			  maxcode = this.familyCode + no;
			}else{
				String no = String.format("%02d", number);
				maxcode = this.familyCode + no;
			}
			return maxcode;
		}catch(SQLException e){
			throw new SQLException("生成个人编号失败:"+e.getMessage(), e);
		}finally{
			DbUtil.close(rs, pstmt, conn);
		}
	}
	
	/**
	 * 自动生成医疗证卡号
	 * @author SunYi
	 * @Date 2019年5月5日下午10:08:07
	 * @return String
	 */
	private String createMedicalCard() throws SQLException{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			conn = DbUtil.getConn();
			String sql = "select max(medicalCard) from t_person_archives where medicalCard like'"+ this.familyCode +"%'" ;
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			String maxcode = "";
			Integer number = 1;
			if(rs.next()){
			   maxcode = rs.getString(1);
			}
			if(maxcode != null){
			  int beginIndex = maxcode.length()-2;
			  String no = maxcode.substring(beginIndex);
			  number = Integer.parseInt(no);
			  ++number;
			  //使用0补足位数
			  no = String.format("%02d", number);
			  maxcode = this.familyCode + no;
			}else{
				String no = String.format("%02d", number);
				maxcode = this.familyCode + no;
			}
			return maxcode;
		}catch(SQLException e){
			throw new SQLException("生成个人编号失败:"+e.getMessage(), e);
		}finally{
			DbUtil.close(rs, pstmt, conn);
		}
	}
	
	/**
 	 * 预更新参合人员档案信息
 	 * @author SunYi
 	 * @Date 2019年4月29日上午10:44:10
 	 * @return boolean
	 * @throws Exception 
 	 */
	public boolean toUpdatePerson(String familyCode, String oldName) throws Exception {
		this.familyCode = familyCode;
		boolean flag = false;
		Connection conn = null;
		try{
			conn = DbUtil.getConn();
			// 1:开启手动提交事务
			conn.setAutoCommit(false);
			// 2:更新到数据库
			updatePerson(conn);
			// 3:判断当前修改的用户是否户主
			Family family = new Family(familyCode);
			if(family.getHouseHolder() == oldName || oldName.equals(family.getHouseHolder())) {
				// 4:更新关联的家庭档案的户主信息
				unbindFamily(conn);
			}
			flag = true;
			// 5:提交事务
			conn.commit();
		}catch (Exception e) {
			if(conn!=null){
				// 事务回滚
				conn.rollback();
			}
			throw e;
		}finally{
			DbUtil.close(conn);
		}
		return flag;
	}
	
	/**
	 * 更新参合人员档案信息到数据库
	 * @author SunYi
	 * @Date 2019年4月29日上午10:44:31
	 * @return void
	 */
	private void updatePerson(Connection conn) throws SQLException {
		PreparedStatement pstmt=null;
		try{
			// 创建SQL语句
			StringBuffer sql = new StringBuffer();
			sql.append("update t_person_archives set relationship=?, name=?, gender=?, age=?, birthDate=?, "
					+ "nation=?, health=?, education=?, workType=?, career=?, workUnit=?, telephone=?, "
					+ "liveAddress=? ");
			sql.append(" where idCard=? ");
			pstmt=conn.prepareStatement(sql.toString());
			// 把当前对象的值赋给占位符
			int index = 1;
			 pstmt.setString(index++, this.relationship);
			 pstmt.setString(index++, this.name);
			 pstmt.setString(index++, this.gender);
			 pstmt.setInt(index++, this.age);
			 pstmt.setDate(index++, new java.sql.Date(this.birthDate.getTime()));
			 pstmt.setString(index++, this.nation);
			 pstmt.setString(index++, this.health);
			 pstmt.setString(index++, this.education);
			 pstmt.setString(index++, this.workType);
			 pstmt.setString(index++, this.career);
			 pstmt.setString(index++, this.workUnit);
			 pstmt.setString(index++, this.telephone);
			 pstmt.setString(index++, this.liveAddress);
			 pstmt.setString(index++, this.idCard);

			// 执行更新操作
			pstmt.executeUpdate(); 
		}catch(SQLException e){
			throw e;
		}finally{
			DbUtil.close(pstmt);
		} 
	}
	
	/**
	 * 更新与当前用户关联的家庭档案信息
	 * @author SunYi
	 * @Date 2019年4月29日下午1:08:20
	 * @return void
	 */
	private void unbindFamily(Connection conn) throws SQLException {
		PreparedStatement pstmt=null;
		try{
			// 创建SQL语句
			StringBuffer sql = new StringBuffer();
			sql.append("update t_family_archives set houseHolder=? ");
			sql.append(" where familyCode=?");
			pstmt=conn.prepareStatement(sql.toString());
			// 把当前对象的值赋给占位符
			int index = 1;
			pstmt.setString(index++, this.name);
			pstmt.setString(index++, this.familyCode);
			// 执行更新操作
			pstmt.executeUpdate(); 
		}catch(SQLException e){
			throw e;
		}finally{
			DbUtil.close(pstmt);
		} 
	}
	
	/**
	 * 预更新个人户内编号
	 * @author SunYi
	 * @Date 2019年5月5日下午5:28:37
	 * @return void
	 */
	public void toUpdateHouseNum(String idCard, String familyCode) throws Exception {
		this.familyCode = familyCode;
		this.idCard = idCard;
		Connection conn = null;
		try{
			conn = DbUtil.getConn();
			// 1:开启手动提交事务
			conn.setAutoCommit(false);
			// 2:重新生成户内编号
			String houseNum = createHouseNum();
			this.houseNum = houseNum;
			// 3:更新到数据库
			updateHouseNum(conn);
			// 5:提交事务
			conn.commit();
		}catch (Exception e) {
			if(conn!=null){
				// 事务回滚
				conn.rollback();
			}
			throw e;
		}finally{
			DbUtil.close(conn);
		}
	}
	
	/**
	 * 更新个人户内编号到数据库
	 * @author SunYi
	 * @Date 2019年5月5日下午5:42:00
	 * @return void
	 */
	private void updateHouseNum(Connection conn) throws SQLException {
		PreparedStatement pstmt=null;
		try{
			// 创建SQL语句
			StringBuffer sql = new StringBuffer();
			sql.append("update t_person_archives set houseNum=?, familyCode=? ");
			sql.append(" where idCard=?");
			pstmt=conn.prepareStatement(sql.toString());
			// 把当前对象的值赋给占位符
			int index = 1;
			pstmt.setString(index++, this.houseNum);
			pstmt.setString(index++, this.familyCode);
			pstmt.setString(index++, this.idCard);
			// 执行更新操作
			pstmt.executeUpdate(); 
		}catch(SQLException e){
			throw e;
		}finally{
			DbUtil.close(pstmt);
		} 
	}
	
	/**
	 * 根据家庭编号和姓名获取参合人员信息
	 * @author SunYi
	 * @Date 2019年4月29日下午12:09:02
	 * @return void
	 */
	private void getPersonByFamily() throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			conn = DbUtil.getConn();
			pstmt = conn.prepareStatement("select * from t_person_archives where familyCode=? and name=? ");
			int index = 1;
			// 把页面传过来的treeID的值赋给占位符
			pstmt.setString(index++, this.familyCode);
			pstmt.setString(index++, this.name);
			// 执行查询操作
			rs = pstmt.executeQuery();
			if(rs.next()){
				// 把查询到的结果赋给当前对象
				this.familyCode = rs.getString("familyCode");
				this.nongheCard = rs.getString("nongheCard");
				this.medicalCard = rs.getString("medicalCard");
				this.houseNum = rs.getString("houseNum");
				this.relationship = rs.getString("relationship");
				this.idCard = rs.getString("idCard");
				this.name = rs.getString("name");
				this.gender = rs.getString("gender");
				this.age = rs.getInt("age");
				this.birthDate = rs.getDate("birthDate");
				this.nation = rs.getString("nation");
				this.health = rs.getString("health");
				this.education = rs.getString("education");
				this.workType = rs.getString("workType");
				this.isRuralHukou = rs.getString("isRuralHukou");
				this.career = rs.getString("career");
				this.workUnit = rs.getString("workUnit");
				this.telephone = rs.getString("telephone");
				this.liveAddress = rs.getString("liveAddress");
			}
		}catch(SQLException e){
			throw new SQLException("Person with id " + this.familyCode + " could not be loaded from the database.", e);
		}finally{
			DbUtil.close(rs, pstmt, conn);
		}
	}
	
	/**
	 * 预删除参合人员档案信息
	 * @author SunYi
	 * @Date 2019年4月29日下午1:42:13
	 * @return boolean
	 */
	public boolean toDeletePerson() throws SQLException {
		boolean flag = false;
		Connection conn = null;
		try{
			conn = DbUtil.getConn();
			// 1:开启手动提交事务
			conn.setAutoCommit(false);
			// 2:从数据库中删除指定编号的人员
			deletePerson(conn);
			flag = true;
			// 3:提交事务
			conn.commit();
		}catch (Exception e) {
			if(conn!=null){
				// 事务回滚
				conn.rollback();
			}
			throw e;
		}finally{
			DbUtil.close(conn);
		}
		return flag;
	}
	
	/**
	 * 从数据库中删除用户信息
	 * @author SunYi
	 * @Date 2019年4月29日下午1:45:38
	 * @return void
	 */
	private void deletePerson(Connection conn) throws SQLException {
		PreparedStatement pstmt=null;
		try{
			// 创建SQL语句
			StringBuffer sqlBuff = new StringBuffer("delete from t_person_archives where idCard=? ");
			pstmt=conn.prepareStatement(sqlBuff.toString());
			// 把当前对象的值赋给占位符
			int index = 1;
			pstmt.setString(index++, this.idCard);
			// 执行删除操作
			pstmt.executeUpdate(); 
		}catch(SQLException e){
			throw e;
		}finally{
			DbUtil.close(pstmt);
		} 
	}
	
}
