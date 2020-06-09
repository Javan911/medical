package com.gxuwz.medical.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.gxuwz.medical.domain.area.Area;

/**
 * 行政区域数据访问层
 * @ClassName: AreaDao
 * @author SunYi
 * @Date 2019年4月3日下午11:59:33
 */
public class AreaDao extends GenericDao<Area> {
	
	/**
	 * 实现ResultSet结果集转换为Area类型实例
	 * @author SunYi
	 * @Date 2019年4月4日上午12:00:44
	 */
	@Override
	protected Area handle(ResultSet rs) throws SQLException {
		try{
			// 实例化行政区域对象
			Area model=new Area();
			// 取出ResultSet的值,存进Area对象
			model.setAreacode(rs.getString("areacode"));
			model.setAreaname(rs.getString("areaname"));
			model.setGrade(rs.getInt("grade"));
			// 返回行政区域对象
			return model;
		}catch(SQLException e){
			throw new SQLException("结果集转为实例失败!",e);
		}
	}

}
