package com.gxuwz.medical.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gxuwz.medical.database.DbUtil;
import com.gxuwz.medical.domain.payRecord.PayRecord;

/**
 * 参合缴费记录数据访问层
 * @ClassName: AreaDao
 * @author SunYi
 * @Date 2019年4月3日下午11:59:33
 */
public class PayRecordDao extends GenericDao<PayRecord> {
	
	/**
	 * 实现ResultSet结果集转换为PayRecord类型实例
	 * @author SunYi
	 * @Date 2019年5月10日上午10:44:54
	 */
	@Override
	protected PayRecord handle(ResultSet rs) throws SQLException {
		try{
			// 实例化参合缴费记录对象
			PayRecord model = new PayRecord();
			// 取出ResultSet的值,存进PayRecord对象
			model.setFamilyCode(rs.getString("familyCode"));
			model.setHouseHolder(rs.getString("houseHolder"));
			model.setHouseNum(rs.getString("houseNum"));
			model.setJoinNum(rs.getString("joinNum"));
			model.setInvoiceNum(rs.getString("invoiceNum"));
			model.setName(rs.getString("name"));
			model.setPayAccount(rs.getDouble("payAccount"));
			model.setPayAnnual(rs.getString("payAnnual"));
			model.setPayTime(rs.getDate("payTime"));
			model.setOperator(rs.getString("operator"));
			// 返回参合缴费记录对象
			return model;
		}catch(SQLException e){
			throw new SQLException("结果集转为实例失败!",e);
		}
	}
	
	/**
	 * 计算指定人数的缴费总金额
	 * @author SunYi
	 * @Date 2019年5月10日上午10:49:00
	 * @return double
	 */
	public double calAccount(int payNum, double payAccount) {
		double amount = 0.0;
		try {
			if(payNum !=0 && payAccount != 0) {
				amount = (payNum * payAccount);	
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return amount;
	}
	
	/**
	 * 获取指定家庭编号的当前年度参合缴费总人数
	 * @author SunYi
	 * @Date 2019年5月10日上午9:54:38
	 * @return Person
	 */
	public int getByFamily(String familyCode, String payAnnual) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		try{
			conn = DbUtil.getConn();
			pstmt = conn.prepareStatement("select count(*) from t_pay_record where familyCode=? and payAnnual=? ");
			int index = 1;
			pstmt.setString(index++, familyCode);
			pstmt.setString(index++, payAnnual);
			// 执行查询操作
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
			return count;
		}catch(SQLException e){
			throw e;
		}finally{
			DbUtil.close(rs, pstmt, conn);
		}
	}

}
