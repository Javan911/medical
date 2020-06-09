package com.gxuwz.medical.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.gxuwz.medical.domain.institution.Institution;

public class InstitutionDao extends GenericDao<Institution>{

	/**
	 * 实现ResultSet结果集转换为Institution类型实例
	 * @author SunYi
	 * @Date 2019年4月4日上午12:00:44
	 */
	@Override
	protected Institution handle(ResultSet rs) throws SQLException {
		try {
			// 实例化农合机构对象
			Institution model = new Institution();
			// 取出ResultSet的值,存进Institution对象
			model.setAreacode(rs.getString("areacode"));
			model.setInstitutionCode(rs.getString("institutionCode"));
			model.setInstitutionName(rs.getString("institutionName"));
			model.setGrade(rs.getInt("grade"));
			// 返回农合机构对象
			return model;
		}catch(SQLException e){
			throw new SQLException("结果集转换成实例失败！", e);
		}
	}

}
