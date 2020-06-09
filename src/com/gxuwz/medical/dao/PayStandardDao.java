package com.gxuwz.medical.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.gxuwz.medical.domain.payStandard.PayStandard;

/**
 * 参合缴费标准数据访问层
 * @ClassName: AreaDao
 * @author SunYi
 * @Date 2019年4月3日下午11:59:33
 */
public class PayStandardDao extends GenericDao<PayStandard> {
	
	/**
	 * 实现ResultSet结果集转换为PayStandard类型实例
	 * @author SunYi
	 * @Date 2019年5月10日上午10:44:54
	 */
	@Override
	protected PayStandard handle(ResultSet rs) throws SQLException {
		try{
			// 实例化参合缴费记录对象
			PayStandard model = new PayStandard();
			// 取出ResultSet的值,存进PayStandard对象
			model.setAnnual(rs.getString("annual"));
			model.setAccount(rs.getDouble("account"));
			model.setStartTime(rs.getDate("startTime"));
			model.setEndTime(rs.getDate("endTime"));
			model.setOperator(rs.getString("operator"));
			// 返回参合缴费记录对象
			return model;
		}catch(SQLException e){
			throw new SQLException("结果集转为实例失败!",e);
		}
	}
	
}
