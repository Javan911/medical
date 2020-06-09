package com.gxuwz.medical.domain.family;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.gxuwz.medical.dao.PersonDao;
import com.gxuwz.medical.database.DbUtil;
import com.gxuwz.medical.domain.person.Person;

/**
 * 家庭档案管理类
 * @ClassName: Family
 * @author SunYi
 * @Date 2019年4月27日下午11:16:50
 */
public class Family {
	
	// 县级编码
	private String countyCode;
	// 乡镇编码
	private String townCode;
	// 村编码
	private String villageCode;
	// 组编号
	private String groupCode;
	// 家庭编号
	private String familyCode;
	// 户主
	private String houseHolder;
	// 家庭人口数
	private int familySize;
	// 农业人口数
	private int agriculturalNum;
	// 家庭住址
	private String address;
	// 创建档案时间
	private Date createTime;
	// 登记员
	private String registrar;
	// 户属性
	private String householdType;
	// 是否农村户口(1:是，0:否)
	private int isRural;
	// 状态(1:启用, 0:禁用)
	private int status;
	// 备注
	private String remark;
	
	/*  ***********************************************实体类***********************************************  */
	
	public Family() {
		super();
	}
	public Family(String familyCode) throws Exception{
		this.familyCode = familyCode;
		getFamilyById();
	}
	public Family(String houseHolder, int agriculturalNum, String householdType, 
			int isRural, int status, String remark) {
		super();
		this.houseHolder = houseHolder;
		this.agriculturalNum = agriculturalNum;
		this.householdType = householdType;
		this.isRural = isRural;
		this.status = status;
		this.remark = remark;
	}
	public Family(String countyCode, String townCode, String villageCode, String groupCode,String familyCode,String houseHolder, int agriculturalNum, String address, String householdType,
			int isRural, int status, String remark) {
		super();
		this.countyCode = countyCode;
		this.townCode = townCode;
		this.villageCode = villageCode;
		this.groupCode = groupCode;
		this.familyCode = familyCode;
		this.houseHolder = houseHolder;
		this.agriculturalNum = agriculturalNum;
		this.address = address;
		this.householdType = householdType;
		this.isRural = isRural;
		this.status = status;
		this.remark = remark;
	}
	public Family(String countyCode, String townCode, String villageCode, String groupCode, String familyCode,
			String houseHolder, int familySize, int agriculturalNum, String address, Date createTime, String registrar,
			String householdType, int isRural, int status, String remark) {
		super();
		this.countyCode = countyCode;
		this.townCode = townCode;
		this.villageCode = villageCode;
		this.groupCode = groupCode;
		this.familyCode = familyCode;
		this.houseHolder = houseHolder;
		this.familySize = familySize;
		this.agriculturalNum = agriculturalNum;
		this.address = address;
		this.createTime = createTime;
		this.registrar = registrar;
		this.householdType = householdType;
		this.isRural = isRural;
		this.status = status;
		this.remark = remark;
	}
	public String getCountyCode() {
		return countyCode;
	}
	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}
	public String getTownCode() {
		return townCode;
	}
	public void setTownCode(String townCode) {
		this.townCode = townCode;
	}
	public String getVillageCode() {
		return villageCode;
	}
	public void setVillageCode(String villageCode) {
		this.villageCode = villageCode;
	}
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public String getFamilyCode() {
		return familyCode;
	}
	public void setFamilyCode(String familyCode) {
		this.familyCode = familyCode;
	}
	public String getHouseHolder() {
		return houseHolder;
	}
	public void setHouseHolder(String houseHolder) {
		this.houseHolder = houseHolder;
	}
	public int getFamilySize() {
		return familySize;
	}
	public void setFamilySize(int familySize) {
		this.familySize = familySize;
	}
	public int getAgriculturalNum() {
		return agriculturalNum;
	}
	public void setAgriculturalNum(int agriculturalNum) {
		this.agriculturalNum = agriculturalNum;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getRegistrar() {
		return registrar;
	}
	public void setRegistrar(String registrar) {
		this.registrar = registrar;
	}
	public String getHouseholdType() {
		return householdType;
	}
	public void setHouseholdType(String householdType) {
		this.householdType = householdType;
	}
	public int getIsRural() {
		return isRural;
	}
	public void setIsRural(int isRural) {
		this.isRural = isRural;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "Family [countyCode=" + countyCode + ", townCode=" + townCode + ", villageCode=" + villageCode
				+ ", groupCode=" + groupCode + ", familyCode=" + familyCode + ", houseHolder=" + houseHolder
				+ ", familySize=" + familySize + ", agriculturalNum=" + agriculturalNum + ", address=" + address
				+ ", createTime=" + createTime + ", registrar=" + registrar + ", householdType=" + householdType
				+ ", isRural=" + isRural + ", status=" + status + ", remark=" + remark + "]";
	}
	
	/*  ***********************************************方法体***********************************************  */
	
	/**
	 * 获取指定家庭编号的家庭档案信息
	 * @author SunYi
	 * @Date 2019年4月27日下午11:31:16
	 * @return void
	 */
	private void getFamilyById() throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			conn = DbUtil.getConn();
			StringBuffer sql = new StringBuffer("select * from t_family_archives where familyCode=? ");
			pstmt = conn.prepareStatement(sql.toString());
			int index = 1;
			// 把页面传过来的treeID的值赋给占位符
			pstmt.setString(index, this.familyCode);
			// 执行查询操作
			rs = pstmt.executeQuery();
			if(rs.next()){
				// 把查询到的结果赋给当前对象
				this.countyCode = rs.getString("countyCode");
				this.townCode = rs.getString("townCode");
				this.villageCode = rs.getString("villageCode");
				this.groupCode = rs.getString("groupCode");
				this.familyCode = rs.getString("familyCode");
				this.houseHolder = rs.getString("houseHolder");
				this.familySize = rs.getInt("familySize");
				this.agriculturalNum = rs.getInt("agriculturalNum");
				this.address = rs.getString("address");
				this.createTime = rs.getDate("createTime");
				this.registrar = rs.getString("registrar");
				this.householdType = rs.getString("householdType");
				this.isRural = rs.getInt("isRural");
				this.status = rs.getInt("status");
				this.remark = rs.getString("remark");
			}
		}catch(SQLException e){
			throw new SQLException("Family with id " + this.familyCode + " could not be loaded from the database.", e);
		}finally{
			DbUtil.close(rs, pstmt, conn);
		}
	}
	
	/**
	 * 预添加家庭档案
	 * @author SunYi
	 * @Date 2019年4月29日上午12:03:05
	 * @return void
	 */
	public void toAddFamily()throws Exception{
		Connection conn = null;
		try{
			// 1:自动生成家庭档案编号
			this.familyCode = createFamilyCode();
			conn = DbUtil.getConn();
			// 2:开启手动提交事务
			conn.setAutoCommit(false);
			// 3:保存家庭档案信息到数据库
			addFamily(conn);
			// 4:提交事务
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
	 * 添加家庭档案到数据库
	 * @author SunYi
	 * @Date 2019年4月29日上午12:01:50
	 * @return void
	 */
	private void addFamily(Connection conn)throws SQLException{
		  PreparedStatement pstmt = null;
		  try{
			  // 创建SQL语句
			  StringBuffer sqlBuff = new StringBuffer("insert into t_family_archives(countyCode, townCode, villageCode, "
			  		+ "groupCode,familyCode, houseHolder, agriculturalNum, "
			  		+ "address, createTime, registrar, householdType, isRural, status, remark )");
			  sqlBuff.append(" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			  pstmt = conn.prepareStatement(sqlBuff.toString());
			  // 把当前对象的值赋给占位符
			  int index = 1;
			  pstmt.setString(index++, this.countyCode);
			  pstmt.setString(index++, this.townCode);
			  pstmt.setString(index++, this.villageCode);
			  pstmt.setString(index++, this.groupCode);
			  pstmt.setString(index++, this.familyCode);
			  pstmt.setString(index++, this.houseHolder);
			  pstmt.setInt(index++, this.agriculturalNum);
			  pstmt.setString(index++, this.address);
			  pstmt.setDate(index++, new java.sql.Date(this.createTime.getTime()));
			  pstmt.setString(index++, this.registrar);
			  pstmt.setString(index++, this.householdType);
			  pstmt.setInt(index++, this.isRural);
			  pstmt.setInt(index++, this.status);
			  pstmt.setString(index++, this.remark);

			  // 执行添加操作
			  pstmt.executeUpdate(); 
		  }catch(SQLException e){
			  throw e;
		  }finally{
			  DbUtil.close(pstmt);
		  } 
	}
	
	 /**
	  * 自动生成家庭编号
	  * @author SunYi
	  * @Date 2019年4月29日上午8:53:58
	  * @return String
	  */
	 private String createFamilyCode() throws SQLException{
		 Connection conn = null;
		 PreparedStatement pstmt = null;
		 ResultSet rs = null;
		 try{
			 conn = DbUtil.getConn();
			 // 查询同一行政区域的最大家庭编号
			 String sql = "select max(familyCode) from t_family_archives where familyCode like '" +this.groupCode+ "%'";
			 pstmt = conn.prepareStatement(sql);
			 // 执行查询操作
			 rs = pstmt.executeQuery();
			 String maxcode = "";
			 Integer number = 1;
			 if(rs.next()){
			     maxcode = rs.getString(1);
		 	 }
			 if(maxcode != null){
			   int beginIndex = maxcode.length()-4;
			   String no = maxcode.substring(beginIndex);
			   number = Integer.parseInt(no);
			   ++number;
			   // 使用0补足位数
			   no = String.format("%04d", number);
			   maxcode = (this.groupCode + no);
			 }else{
				 String no = String.format("%04d", number);
				 maxcode = (this.groupCode + no);
			 }
				 return maxcode;
			 }catch(SQLException e){
				 throw e;
			 }finally{
				 DbUtil.close(rs, pstmt, conn);
			 }
	 }
	 
	 	/**
	 	 * 预更新家庭档案信息
	 	 * @author SunYi
	 	 * @Date 2019年4月29日上午10:44:10
	 	 * @return boolean
	 	 */
		public boolean toUpdateFamily(String oldFamilyCode) throws Exception {
			boolean flag = false;
			Connection conn = null;
			try{
				conn = DbUtil.getConn();
				// 1:开启手动提交事务
				conn.setAutoCommit(false);
				// 2:根据传过来的组编号是否为空，来判断是否需要更新家庭地址
				if(this.groupCode != null && !"".equals(this.groupCode)) {
					// 2.1:重新生成家庭编号
					this.familyCode = createFamilyCode();
					// 2.2:重新生成个人户内编号
					// 2.2.1:获取当前家庭编号下的所有用户
					PersonDao psDao = new PersonDao();
					List<Person> psList = psDao.getPersonByFamily(oldFamilyCode);
					// 2.2.2:循环更新当前家庭下的每个个人家庭编号和户内编号到数据库
					for(int i = 0; i < psList.size(); i++) {
						Person person = new Person();
						person.toUpdateHouseNum(psList.get(i).getIdCard(), this.familyCode);
					}
					// 3:更新家庭档案到数据库(更新家庭地址)
					updateFamilyCode(conn, oldFamilyCode);
				}else {
					// 3:更新家庭档案到数据库(不更新家庭地址)
					updateFamily(conn, oldFamilyCode);
				}
				flag = true;
				// 3:提交事务
				conn.commit();
			}catch (SQLException e) {
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
		 * 更新家庭档案信息到数据库(不更新家庭地址)
		 * @author SunYi
		 * @Date 2019年4月29日上午10:44:31
		 * @return void
		 */
		private void updateFamily(Connection conn, String oldFamilyCode) throws SQLException {
			PreparedStatement pstmt=null;
			try{
				// 创建SQL语句
				StringBuffer sql = new StringBuffer();
				sql.append("update t_family_archives set houseHolder=?, agriculturalNum=?, "
						+ "householdType=?, isRural=?, status=?, remark=? ");
				sql.append(" where familyCode=? ");
				pstmt=conn.prepareStatement(sql.toString());
				// 把当前对象的值赋给占位符
				int index = 1;
				pstmt.setString(index++, this.houseHolder);
				pstmt.setInt(index++, this.agriculturalNum);
				pstmt.setString(index++, this.householdType);
				pstmt.setInt(index++, this.isRural);
				pstmt.setInt(index++, this.status);
				pstmt.setString(index++, this.remark);
				pstmt.setString(index++, oldFamilyCode);

				// 执行更新操作
				pstmt.executeUpdate(); 
			}catch(SQLException e){
				throw e;
			}finally{
				DbUtil.close(pstmt);
			} 
		}
		
		/**
		 * 更新家庭档案信息到数据库(需要更新家庭地址)
		 * @author SunYi
		 * @Date 2019年4月29日上午10:44:31
		 * @return void
		 */
		private void updateFamilyCode(Connection conn, String oldFamilyCode) throws SQLException {
			PreparedStatement pstmt=null;
			try{
				// 创建SQL语句
				StringBuffer sql = new StringBuffer();
				sql.append("update t_family_archives set countyCode=?, townCode=?, villageCode=?, "
						+ "groupCode=?, familyCode=?, houseHolder=?, agriculturalNum=?, address=?, "
						+ "householdType=?, isRural=?, status=?, remark=? ");
				sql.append(" where familyCode=? ");
				pstmt=conn.prepareStatement(sql.toString());
				// 把当前对象的值赋给占位符
				int index = 1;
				pstmt.setString(index++, this.countyCode);
				pstmt.setString(index++, this.townCode);
				pstmt.setString(index++, this.villageCode);
				pstmt.setString(index++, this.groupCode);
				pstmt.setString(index++, this.familyCode);
				pstmt.setString(index++, this.houseHolder);
				pstmt.setInt(index++, this.agriculturalNum);
				pstmt.setString(index++, this.address);
				pstmt.setString(index++, this.householdType);
				pstmt.setInt(index++, this.isRural);
				pstmt.setInt(index++, this.status);
				pstmt.setString(index++, this.remark);
				pstmt.setString(index++, oldFamilyCode);

				// 执行更新操作
				pstmt.executeUpdate(); 
			}catch(SQLException e){
				throw e;
			}finally{
				DbUtil.close(pstmt);
			} 
		}
		
	
	/**
	 * 预删除家庭档案信息
	 * @author SunYi
	 * @Date 2019年4月29日下午1:42:13
	 * @return boolean
	 */
	public boolean toDeleteFamily(String familyCode) throws SQLException {
		this.familyCode = familyCode;
		boolean flag = false;
		Connection conn = null;
		try{
			conn = DbUtil.getConn();
			// 1:开启手动提交事务
			conn.setAutoCommit(false);
			// 2:从数据库中删除指定编号的家庭档案
			deleteFamily(conn);
			// 3:删除与该家庭档案关联的参合人员信息
			unbindPerson(conn);
			flag = true;
			// 4:提交事务
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
	 * 从数据库中删除家庭档案信息
	 * @author SunYi
	 * @Date 2019年4月29日下午1:45:38
	 * @return void
	 */
	private void deleteFamily(Connection conn) throws SQLException {
		PreparedStatement pstmt=null;
		try{
			// 创建SQL语句
			StringBuffer sqlBuff = new StringBuffer("delete from t_family_archives where familyCode=? ");
			pstmt=conn.prepareStatement(sqlBuff.toString());
			// 把当前对象的值赋给占位符
			pstmt.setString(1, this.familyCode);
			// 执行删除操作
			pstmt.executeUpdate(); 
		}catch(SQLException e){
			throw e;
		}finally{
			DbUtil.close(pstmt);
		} 
	}
	
	/**
	 * 删除与该家庭档案关联的参合人员信息
	 * @author SunYi
	 * @Date 2019年4月29日下午1:46:24
	 * @return void
	 */
	private void unbindPerson(Connection conn) throws SQLException {
		PreparedStatement pstmt=null;
		try{
			// 创建SQL语句
			StringBuffer sqlBuff = new StringBuffer("delete from t_person_archives where familyCode=? ");
			pstmt=conn.prepareStatement(sqlBuff.toString());
			// 把当前对象的值赋给占位符
			pstmt.setString(1, this.familyCode);
			// 执行删除操作
			pstmt.executeUpdate(); 
		}catch(SQLException e){
			throw e;
		}finally{
			DbUtil.close(pstmt);
		} 
	}
	 
}
