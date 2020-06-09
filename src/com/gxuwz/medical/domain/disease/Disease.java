package com.gxuwz.medical.domain.disease;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gxuwz.medical.database.DbUtil;
import com.gxuwz.medical.exception.DiseaseException;

/**
 * 慢性病分类管理类
 * @ClassName: Disease
 * @author SunYi
 * @Date 2019年4月12日下午1:23:43
 */
public class Disease {
	
	// 标识Id
	private int id;
	// 疾病编码
	private String diseaseCode;
	// 拼音码
	private String pinyinCode;
	// 疾病名称
	private String diseaseName;
	
	
	
	/*  ***********************************************实体类***********************************************  */
	
	public Disease() {
		super();
	}
	// 获取指定编号的疾病信息
	public Disease(String diseaseCode) throws DiseaseException {
		this.diseaseCode = diseaseCode;
		getDiseaseById();
	}
	public Disease(String diseaseCode, String pinyinCode, String diseaseName) {
		super();
		this.diseaseCode = diseaseCode;
		this.pinyinCode = pinyinCode;
		this.diseaseName = diseaseName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDiseaseCode() {
		return diseaseCode;
	}
	public void setDiseaseCode(String diseaseCode) {
		this.diseaseCode = diseaseCode;
	}
	public String getPinyinCode() {
		return pinyinCode;
	}
	public void setPinyinCode(String pinyinCode) {
		this.pinyinCode = pinyinCode;
	}
	public String getDiseaseName() {
		return diseaseName;
	}
	public void setDiseaseName(String diseaseName) {
		this.diseaseName = diseaseName;
	}
	@Override
	public String toString() {
		return "Disease [diseaseCode=" + diseaseCode + ", pinyinCode=" + pinyinCode + ", diseaseName=" + diseaseName
				+ "]";
	}
	
	
	
	
	/*  ***********************************************方法体***********************************************  */
	
	/**
	 * 获取指定编号的疾病信息
	 * @author SunYi
	 * @Date 2019年4月12日下午1:41:32
	 * @return void
	 */
	private void getDiseaseById() throws DiseaseException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DbUtil.getConn();
			String sql = "select * from t_disease_type where diseaseCode = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, this.diseaseCode);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				this.id = rs.getInt("id");
				this.diseaseCode = rs.getString("diseaseCode");
				this.pinyinCode = rs.getString("pinyinCode");
				this.diseaseName = rs.getString("diseaseName");
			}
		}catch (SQLException e) {
			throw new DiseaseException("Disease with id " + this.diseaseCode + " could not be loaded from the database.", e);
		}finally {
			DbUtil.close(rs, pstmt, conn);
		}
	}
	
	/**
	 * 预添加慢性病
	 * @author SunYi
	 * @Date 2019年4月12日下午3:16:21
	 * @return void
	 */
	public void toAddDisease(String diseaseCode, String pinyinCode, String diseaseName) throws Exception{
		Connection conn = null;
		try {
			this.diseaseCode = diseaseCode;
			this.pinyinCode = pinyinCode;
			this.diseaseName = diseaseName;
			conn = DbUtil.getConn();
			// 1:开启手动提交事务
			conn.setAutoCommit(false);
			// 2:执行添加方法
			addDisease(conn);
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
	 * 添加慢性病到数据库
	 * @author SunYi
	 * @Date 2019年4月12日下午3:16:52
	 * @return void
	 */
	private void addDisease(Connection conn) throws DiseaseException{
		PreparedStatement pstmt = null;
		try {
			StringBuffer sql = new StringBuffer("insert into t_disease_type(diseaseCode, pinyinCode, diseaseName) ");
			sql.append("values(?,?,?)");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, this.diseaseCode);
			pstmt.setString(2, this.pinyinCode);
			pstmt.setString(3, this.diseaseName);
			pstmt.executeUpdate();
		}catch (SQLException e) {
			throw new DiseaseException("Disease with id " + this.diseaseCode + " could not be add to the database.", e);
		}finally {
			DbUtil.close(pstmt);
		}
	}
	
	/**
	 * 校验慢性病名称是否已存在
	 * @author SunYi
	 * @Date 2019年4月12日下午3:34:13
	 * @return boolean
	 */
	public boolean checkDiseaseName(String diseaseName) throws DiseaseException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DbUtil.getConn();
			StringBuffer sql = new StringBuffer("select * from t_disease_type where 1=1 ");
			if(diseaseName != null && !"".equals(diseaseName)) {
				sql.append("and diseaseName = '"+ diseaseName +"'");
			}
			pstmt = conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			return rs.next();
		}catch (SQLException e) {
			throw new DiseaseException("Disease with name "+ this.diseaseName +" could not be loaded from the database.", e);
		}finally {
			DbUtil.close(rs, pstmt, conn);
		}
	}
	
	
	/**
	 * 预删除慢性病信息
	 * @author SunYi
	 * @Date 2019年4月12日下午11:04:49
	 * @return void
	 */
	public void toDeleteDisease(String diseaseCode) throws Exception {
		Connection conn = null;
		try {
			this.diseaseCode = diseaseCode;
			conn = DbUtil.getConn();
			// 1:开启手动提交事务
			conn.setAutoCommit(false);
			// 2:执行删除方法
			deleteDisease(conn);
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
	 * 从数据库中删除慢性病信息
	 * @author SunYi
	 * @Date 2019年4月12日下午10:57:47
	 * @return void
	 */
	private void deleteDisease(Connection conn) throws DiseaseException {
		PreparedStatement pstmt = null;
		try {
			String sql = "delete from t_disease_type where diseaseCode = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, this.diseaseCode);
			// 执行删除操作
			pstmt.executeUpdate();
		}catch (SQLException e) {
			throw new DiseaseException("Disease with id '" + diseaseCode + "' could not be delete from the database. ", e);
		}finally {
			DbUtil.close(pstmt);
		}
	}
	
	/**
	 * 预更新慢性病信息
	 * @author SunYi
	 * @Date 2019年4月12日下午11:43:09
	 * @return void
	 */
	public void toUpdateDisease(int id, String diseaseCode, String pinyinCode, String diseaseName) throws Exception {
		Connection conn = null;
		try {
			this.id = id;
			this.diseaseCode = diseaseCode;
			this.pinyinCode = pinyinCode;
			this.diseaseName = diseaseName;
			conn = DbUtil.getConn();
			// 1:开启手动提交事务
			conn.setAutoCommit(false);
			// 2:执行更新方法
			updateDisease(conn);
			// 3:提交事务
			conn.commit();
		}catch (SQLException e) {
			// 事务回滚
			if(conn != null) {
				conn.commit();
			}
			throw e;
		}finally {
			DbUtil.close(conn);
		}
	}
	
	/**
	 * 更新慢性病信息到数据库
	 * @author SunYi
	 * @Date 2019年4月12日下午11:46:31
	 * @return void
	 */
	private void updateDisease(Connection conn) throws DiseaseException{
		PreparedStatement pstmt = null;
		try {
			StringBuffer sql = new StringBuffer("update t_disease_type set diseaseCode=?, pinyinCode=?, diseaseName=? ");
			sql.append("where id=?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, this.diseaseCode);
			pstmt.setString(2, this.pinyinCode);
			pstmt.setString(3, this.diseaseName);
			pstmt.setInt(4, this.id);
			// 执行更新操作
			pstmt.executeUpdate();
		}catch (SQLException e) {
			throw new DiseaseException("Disease with id '" + diseaseCode + "' could not be update to the database. ", e);
		}finally {
			DbUtil.close(pstmt);
		}
	}
	
}
