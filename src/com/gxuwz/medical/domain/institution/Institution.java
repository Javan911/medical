package com.gxuwz.medical.domain.institution;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gxuwz.medical.database.DbUtil;
import com.gxuwz.medical.exception.InstitutionException;

/**
 * 农合机构管理类
 * @ClassName: Institution
 * @author SunYi
 * @Date 2019年4月6日下午10:30:18
 */
public class Institution {

	// 所属行政编码
	private String areacode;
	// 农合机构编码
	private String institutionCode;
	// 农合机构名称
	private String institutionName;
	// 级别
	private int grade;
	
	
	
	/*  ***********************************************实体类***********************************************  */
	
	public Institution() {
		super();
	}
	// 获取指定行政编号的农合机构信息
	public Institution(String areacode) throws InstitutionException {
		this.areacode = areacode;
		getInstitutionById(areacode);
	}
	public Institution(String areacode, String institutionCode, String institutionName, int grade) {
		super();
		this.areacode = areacode;
		this.institutionCode = institutionCode;
		this.institutionName = institutionName;
		this.grade = grade;
	}
	public String getAreacode() {
		return areacode;
	}
	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}
	public String getInstitutionCode() {
		return institutionCode;
	}
	public void setInstitutionCode(String institutionCode) {
		this.institutionCode = institutionCode;
	}
	public String getInstitutionName() {
		return institutionName;
	}
	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	@Override
	public String toString() {
		return "Institution [areacode=" + areacode + ", institutionCode=" + institutionCode + ", institutionName="
				+ institutionName + ", grade=" + grade + "]";
	}
	
	
	
	/*  ***********************************************方法体***********************************************  */
	
	 /**
	  * 获取指定行政编码的农合机构
	  * @author SunYi
	  * @Date 2019年4月6日下午11:25:55
	  * @return void
	  */
	private void getInstitutionById(String areacode) throws InstitutionException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DbUtil.getConn();
			String sql = "select * from t_institution where areacode = ?";
			pstmt = conn.prepareStatement(sql);
			// 给占位符赋值
			pstmt.setString(1, areacode);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				// 把查询结果给当前对象赋值
				this.areacode = rs.getString("areacode");
				this.institutionCode = rs.getString("institutionCode");
				this.institutionName = rs.getString("institutionName");
				this.grade = rs.getInt("grade");
			}
		}catch(SQLException e) {
			throw new InstitutionException("Institution with areacode " + areacode + " could not be loaded from the database.",e);
		}finally {
			DbUtil.close(rs, pstmt, conn);
		}
	}
	
	 /**
	  * 校验农合机构名称是否已存在
	  * @author SunYi
	  * @Date 2019年4月7日下午5:50:16
	  * @return void
	  */
	public boolean checkInstitutionName(String institutionName) throws InstitutionException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DbUtil.getConn();
			StringBuffer sql = new StringBuffer("select * from t_institution where 1=1 ");
			if(institutionName != null && !"".equals(institutionName)) {
				sql.append("and institutionName = '"+ institutionName +"'");
			}
			pstmt = conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			return rs.next();
		}catch(SQLException e) {
			throw new InstitutionException("Institution with institutionName " + institutionName + " could not be loaded from the database.",e);
		}finally {
			DbUtil.close(rs, pstmt, conn);
		}
	}
	
	
	/**
	 * 预添加农合机构
	 * @author SunYi
	 * @Date 2019年4月10日下午2:24:58
	 * @return void
	 */
	public void toAddInstitution(String areacode, String institutionCode, String institutionName, int grade) throws Exception{
		Connection conn = null;
		try {
			// 1:把传过来的值赋给当前对象
			this.areacode = areacode;
			this.institutionCode = institutionCode;
			this.institutionName = institutionName;
			this.grade = grade;
			conn = DbUtil.getConn();
			// 2:开启手动提交事务
			conn.setAutoCommit(false);
			// 3:调用添加方法
			AddInstitution(conn);
			// 4:提交事务
			conn.commit();
		}catch (Exception e) {
			// 事务回滚
			if(conn != null) {
				conn.rollback();
			}
			throw e;
		}finally {
			DbUtil.close(conn);
		}
	}
	
	/**
	 * 添加农合机构到数据库
	 * @author SunYi
	 * @Date 2019年4月10日下午2:13:31
	 * @return void
	 */
	private void AddInstitution(Connection conn) throws InstitutionException {
		PreparedStatement pstmt = null;
		try {
			StringBuffer sql = new StringBuffer("insert into t_institution (areacode, institutionCode, institutionName, grade)");
			sql.append("values(?,?,?,?)");
			pstmt = conn.prepareStatement(sql.toString());
			// 给占位符赋值
			pstmt.setString(1, this.areacode);
			pstmt.setString(2, this.institutionCode);
			pstmt.setString(3, this.institutionName);
			pstmt.setInt(4, this.grade);
			// 执行添加操作
			pstmt.executeUpdate();
		}catch (SQLException e) {
			throw new InstitutionException("Institution with areacode " + areacode + " could not be add to the database.",e);
		}finally {
			DbUtil.close(pstmt);
		}
		
	}
	
	/**
	 * 预删除农合机构
	 * @author SunYi
	 * @Date 2019年4月11日下午4:49:01
	 * @return boolean
	 * @throws SQLException 
	 */
	public boolean toDeleteInstitution(String areacode) throws InstitutionException, SQLException {
		Connection conn = null;
		this.areacode = areacode;
		try {
			conn = DbUtil.getConn();
			// 1:开启手动提交事务
			conn.setAutoCommit(false);
			// 2:从数据库中删除
			deleteInstitution(conn);
			// 3:提交事务
			conn.commit();
			// 4:删除成功则返回true
			return true;
		}catch (SQLException e) {
			// 事务回滚
			if(conn != null) {
				conn.rollback();
			}
		}finally {
			DbUtil.close(conn);
		}
		return false;
	}
	
	/**
	 * 从数据库中删除农合机构
	 * @author SunYi
	 * @Date 2019年4月11日下午5:11:19
	 * @return void
	 */
	private void deleteInstitution(Connection conn) throws InstitutionException{
		PreparedStatement pstmt = null;
		try {
			StringBuffer sql = new StringBuffer("delete from t_institution where areacode = ?");
			pstmt = conn.prepareStatement(sql.toString());
			// 给占位符赋值
			pstmt.setString(1, this.areacode);
			// 执行删除操作
			pstmt.executeUpdate();
		}catch (SQLException e) {
			throw new InstitutionException("Institution with areacode " + areacode + " could not be detele from the database.",e);
		}finally {
			DbUtil.close(pstmt);
		}
	}
	
	/**
	 * 预更新农合机构信息
	 * @author SunYi
	 * @Date 2019年4月11日下午5:54:09
	 * @return void
	 */
	public void toUpdateInstitution(String areacode, String institutionCode, String institutionName, String oldAreacode) throws InstitutionException, SQLException{
		Connection conn = null;
		this.areacode = areacode;
		this.institutionCode = institutionCode;
		this.institutionName = institutionName;
		try {
			conn = DbUtil.getConn();
			// 1:开启手动提交事务
			conn.setAutoCommit(false);
			// 2:执行更新方法
			updateInstitution(conn, oldAreacode);
			// 3:提交事务
			conn.commit();
		}catch (SQLException e) {
			// 事务回滚
			if(conn != null) {
				conn.rollback();
			}
			throw e;
		}finally {
			DbUtil.close(conn);
		}
	}
	
	/**
	 * 将农合机构信息更新到数据库
	 * @author SunYi
	 * @Date 2019年4月11日下午5:54:54
	 * @return void
	 */
	private void updateInstitution(Connection conn, String oldAreacode) throws InstitutionException {
		PreparedStatement pstmt = null;
		try {
			StringBuffer sql = new StringBuffer("update t_institution t set t.areacode=?, t.institutionCode=?, t.institutionName=?");
			sql.append(" where t.areacode = ? ");
			pstmt = conn.prepareStatement(sql.toString());
			// 给占位符赋值
			pstmt.setString(1, this.areacode);
			pstmt.setString(2, this.institutionCode);
			pstmt.setString(3, this.institutionName);
			pstmt.setString(4, oldAreacode);
			// 执行更新操作
			pstmt.executeUpdate();
		}catch (SQLException e) {
			throw new InstitutionException("Institution with areacode " + areacode + " could not be update to the database.",e);
		}finally {
			DbUtil.close(pstmt);
		}
	}
	
	
}
