package com.gxuwz.medical.domain.payStandard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.gxuwz.medical.database.DbUtil;

public class PayStandard {

	// 当前年度
	private String annual;
	// 参合需缴费金额
	private double account;
	// 参合缴费开始时间
	private Date startTime;		
	// 参合缴费结束时间
	private Date endTime;
	// 操作员
	private String operator;
	
	
	/*  ***********************************************实体类***********************************************  */
	
	public PayStandard() {
		super();
	}
	public PayStandard(String annual) throws SQLException {
		this.annual = annual;
		getByAnnual();
	}
	public PayStandard(String annual, double account, Date startTime, Date endTime, String operator) {
		super();
		this.annual = annual;
		this.account = account;
		this.startTime = startTime;
		this.endTime = endTime;
		this.operator = operator;
	}
	public String getAnnual() {
		return annual;
	}
	public void setAnnual(String annual) {
		this.annual = annual;
	}
	public double getAccount() {
		return account;
	}
	public void setAccount(double account) {
		this.account = account;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	@Override
	public String toString() {
		return "PayStandard [annual=" + annual + ", account=" + account + ", startTime=" + startTime + ", endTime="
				+ endTime + ", operator=" + operator + "]";
	}
		
	/*  ***********************************************方法体***********************************************  */
	
	/**
	 * 获取指定年度的缴费标准
	 * @author SunYi
	 * @Date 2019年5月10日上午10:27:33
	 * @return void
	 */
	private void getByAnnual() throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			conn = DbUtil.getConn();
			pstmt = conn.prepareStatement("select * from t_pay_standard where annual=? ");
			int index = 1;
			// 把页面传过来的treeID的值赋给占位符
			pstmt.setString(index, this.annual);
			// 执行查询操作
			rs = pstmt.executeQuery();
			if(rs.next()){
				this.annual = rs.getString("annual");
				this.account = rs.getDouble("account");
				this.startTime = rs.getDate("startTime");
				this.endTime = rs.getDate("endTime");
				this.operator = rs.getString("operator");
			}
		}catch(SQLException e){
			throw e;
		}finally{
			DbUtil.close(rs, pstmt, conn);
		}
	}
	
	/**
	 * 预添加当前年度的参合缴费标准
	 * @author SunYi
	 * @Date 2019年5月13日下午10:15:39
	 * @return void
	 */
	public void toAddPayStandard()throws Exception{
		Connection conn = null;
		try{
			conn = DbUtil.getConn();
			// 2:开启手动提交事务
			conn.setAutoCommit(false);
			// 3:保存家庭档案信息到数据库
			addPayStandard(conn);
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
	 * 添加参合当前年度缴费标准到数据库
	 * @author SunYi
	 * @Date 2019年5月12日下午4:25:21
	 * @return void
	 */
	private void addPayStandard(Connection conn)throws SQLException{
		  PreparedStatement pstmt = null;
		  try{
			  // 创建SQL语句
			  StringBuffer sqlBuff = new StringBuffer("insert into t_pay_standard(annual, account, "
			  		+ "startTime, endTime, operator)");
			  sqlBuff.append(" values(?,?,?,?,?)");
			  pstmt = conn.prepareStatement(sqlBuff.toString());
			  // 把当前对象的值赋给占位符
			  int index = 1;
			  pstmt.setString(index++, this.annual);
			  pstmt.setDouble(index++, this.account);
			  pstmt.setDate(index++, new java.sql.Date(this.startTime.getTime()));
			  pstmt.setDate(index++, new java.sql.Date(this.endTime.getTime()));
			  pstmt.setString(index++, this.operator);
			  // 执行添加操作
			  pstmt.executeUpdate(); 
		  }catch(SQLException e){
			  throw e;
		  }finally{
			  DbUtil.close(pstmt);
		  } 
	}
	
	/**
	 * 预更新参合缴费标准
	 * @author SunYi
	 * @Date 2019年5月13日上午11:39:21
	 * @return boolean
	 */
	public void toUpdatePayStandard() throws Exception {
		Connection conn = null;
		try{
			conn = DbUtil.getConn();
			// 1:开启手动提交事务
			conn.setAutoCommit(false);
			// 2:更新到数据库
			updatePayStandard(conn);
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
	}
	
	/**
	 * 更新参合缴费标准到数据库
	 * @author SunYi
	 * @Date 2019年5月13日上午11:41:58
	 * @return void
	 */
	private void updatePayStandard(Connection conn) throws SQLException {
		PreparedStatement pstmt=null;
		try{
			// 创建SQL语句
			StringBuffer sql = new StringBuffer();
			sql.append("update t_pay_standard set account=?, startTime=?, endTime=?, operator=? ");
			sql.append(" where annual=? ");
			pstmt=conn.prepareStatement(sql.toString());
			// 把当前对象的值赋给占位符
			int index = 1;
			 pstmt.setDouble(index++, this.account);
			 pstmt.setDate(index++, new java.sql.Date(this.startTime.getTime()));
			 pstmt.setDate(index++, new java.sql.Date(this.endTime.getTime()));
			 pstmt.setString(index++, this.operator);
			 pstmt.setString(index++, this.annual);
			// 执行更新操作
			pstmt.executeUpdate(); 
		}catch(SQLException e){
			throw e;
		}finally{
			DbUtil.close(pstmt);
		} 
	}
	
	/**
	 * 预删除缴费标准记录
	 * @author SunYi
	 * @Date 2019年5月13日下午12:01:05
	 * @return boolean
	 */
	public boolean toDeletePayStandard() throws SQLException {
		boolean flag = false;
		Connection conn = null;
		try{
			conn = DbUtil.getConn();
			// 1:开启手动提交事务
			conn.setAutoCommit(false);
			// 2:从数据库中删除
			deletePayStandard(conn);
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
	private void deletePayStandard(Connection conn) throws SQLException {
		PreparedStatement pstmt=null;
		try{
			// 创建SQL语句
			StringBuffer sqlBuff = new StringBuffer("delete from t_pay_standard where annual=? ");
			pstmt=conn.prepareStatement(sqlBuff.toString());
			// 把当前对象的值赋给占位符
			int index = 1;
			pstmt.setString(index++, this.annual);
			// 执行删除操作
			pstmt.executeUpdate(); 
		}catch(SQLException e){
			throw e;
		}finally{
			DbUtil.close(pstmt);
		} 
	}
}
