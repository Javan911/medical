package com.gxuwz.medical.domain.payRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import com.gxuwz.medical.database.DbUtil;

/**
 * 参合缴费记录管理模块
 * @ClassName: PayRecord
 * @author SunYi
 * @Date 2019年5月10日上午10:14:44
 */
public class PayRecord {
	
	// 家庭编号
	private String familyCode;
	// 户主
	private String houseHolder;
	// 个人户内编号
	private String houseNum;
	// 参合证号
	private String joinNum;
	// 参合发票号
	private String invoiceNum;
	// 参合个人姓名
	private String name;
	// 缴费金额
	private double payAccount;
	// 缴费年度
	private String payAnnual;
	// 缴费时间
	private Date payTime;
	// 操作员
	private String operator;
	
	
	/*  ***********************************************实体类***********************************************  */
	
	public PayRecord() {
		super();
	}
	public PayRecord(String houseNum, String payAnnual) throws SQLException {
		this.houseNum = houseNum;
		this.payAnnual = payAnnual;
		getPayRecord();
	}
	public PayRecord(String familyCode, String houseHolder, String houseNum, String joinNum, String invoiceNum,
			String name, double payAccount, String payAnnual, Date payTime, String operator) {
		super();
		this.familyCode = familyCode;
		this.houseHolder = houseHolder;
		this.houseNum = houseNum;
		this.joinNum = joinNum;
		this.invoiceNum = invoiceNum;
		this.name = name;
		this.payAccount = payAccount;
		this.payAnnual = payAnnual;
		this.payTime = payTime;
		this.operator = operator;
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
	public String getHouseNum() {
		return houseNum;
	}
	public void setHouseNum(String houseNum) {
		this.houseNum = houseNum;
	}
	public String getJoinNum() {
		return joinNum;
	}
	public void setJoinNum(String joinNum) {
		this.joinNum = joinNum;
	}
	public String getInvoiceNum() {
		return invoiceNum;
	}
	public void setInvoiceNum(String invoiceNum) {
		this.invoiceNum = invoiceNum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPayAccount() {
		return payAccount;
	}
	public void setPayAccount(double payAccount) {
		this.payAccount = payAccount;
	}
	public String getPayAnnual() {
		return payAnnual;
	}
	public void setPayAnnual(String payAnnual) {
		this.payAnnual = payAnnual;
	}
	public Date getPayTime() {
		return payTime;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	@Override
	public String toString() {
		return "PayRecord [familyCode=" + familyCode + ", houseHolder=" + houseHolder + ", houseNum=" + houseNum
				+ ", joinNum=" + joinNum + ", invoiceNum=" + invoiceNum + ", name=" + name + ", payAccount="
				+ payAccount + ", payAnnual=" + payAnnual + ", payTime=" + payTime + ", operator=" + operator + "]";
	}
	
	/*  ***********************************************方法体***********************************************  */
	
	/**
	 * 获取指定个人户内编号和缴费年度的参合缴费记录
	 * @author SunYi
	 * @Date 2019年5月10日上午9:54:38
	 * @return Person
	 */
	public String getPayRecord() throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String houseNum = null;
		try{
			conn = DbUtil.getConn();
			pstmt = conn.prepareStatement("select * from t_pay_record where houseNum=? and payAnnual=? ");
			int index = 1;
			pstmt.setString(index++, this.houseNum);
			pstmt.setString(index++, this.payAnnual);
			// 执行查询操作
			rs = pstmt.executeQuery();
			if(rs.next()) {
				houseNum = rs.getString("houseNum");
			}
			return houseNum;
		}catch(SQLException e){
			throw e;
		}finally{
			DbUtil.close(rs, pstmt, conn);
		}
	}
	
	/**
	 * 预添加缴费记录
	 * @author SunYi
	 * @Date 2019年5月12日下午3:32:25
	 * @return void
	 */
	public void toAddPayRecord()throws Exception{
		Connection conn = null;
		try{
			conn = DbUtil.getConn();
			// 2:开启手动提交事务
			conn.setAutoCommit(false);
			// 3:保存家庭档案信息到数据库
			addPayRecord(conn);
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
	 * 添加缴费记录到数据库
	 * @author SunYi
	 * @Date 2019年5月12日下午4:25:21
	 * @return void
	 */
	private void addPayRecord(Connection conn)throws SQLException{
		  PreparedStatement pstmt = null;
		  try{
			  // 创建SQL语句
			  StringBuffer sqlBuff = new StringBuffer("insert into t_pay_record(familyCode, houseHolder, houseNum, "
			  		+ "joinNum, invoiceNum, name, payAccount, payAnnual, payTime, operator )");
			  sqlBuff.append(" values(?,?,?,?,?,?,?,?,?,?)");
			  pstmt = conn.prepareStatement(sqlBuff.toString());
			  // 把当前对象的值赋给占位符
			  int index = 1;
			  pstmt.setString(index++, this.familyCode);
			  pstmt.setString(index++, this.houseHolder);
			  pstmt.setString(index++, this.houseNum);
			  pstmt.setString(index++, this.joinNum);
			  pstmt.setString(index++, this.invoiceNum);
			  pstmt.setString(index++, this.name);
			  pstmt.setDouble(index++, this.payAccount);
			  pstmt.setString(index++, this.payAnnual);
			  pstmt.setDate(index++, new java.sql.Date(this.payTime.getTime()));
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
	 * 预删除家庭缴费记录
	 * @author SunYi
	 * @Date 2019年5月13日下午10:07:08
	 * @return boolean
	 */
	public boolean toDeletePayRecord() throws SQLException {
		boolean flag = false;
		Connection conn = null;
		try{
			conn = DbUtil.getConn();
			// 1:开启手动提交事务
			conn.setAutoCommit(false);
			// 2:从数据库中删除
			deletePayRecord(conn);
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
	 * 从数据库中删除家庭缴费记录
	 * @author SunYi
	 * @Date 2019年5月13日下午10:07:36
	 * @return void
	 */
	private void deletePayRecord(Connection conn) throws SQLException {
		PreparedStatement pstmt=null;
		try{
			// 创建SQL语句
			StringBuffer sqlBuff = new StringBuffer("delete from t_pay_record where familyCode=? ");
			pstmt=conn.prepareStatement(sqlBuff.toString());
			// 把当前对象的值赋给占位符
			int index = 1;
			pstmt.setString(index++, this.familyCode);
			// 执行删除操作
			pstmt.executeUpdate(); 
		}catch(SQLException e){
			throw e;
		}finally{
			DbUtil.close(pstmt);
		} 
	}
}
