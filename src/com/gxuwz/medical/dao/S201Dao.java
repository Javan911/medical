package com.gxuwz.medical.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.gxuwz.medical.domain.medical.*;
import java.util.List;

/**
 * 基础数据访问层
 * @ClassName: S201Dao
 * @author SunYi
 * @Date 2019年4月24日下午5:53:18
 */
public class S201Dao extends GenericDao<S201> {

	/**
	 * 实现ResultSet结果集转换为S201类型实例
	 * @author SunYi
	 * @Date 2019年4月24日下午5:53:59
	 */
	@Override
	protected S201 handle(ResultSet rs) throws SQLException {
		try {
			S201 model = new S201();
			model.setId(rs.getInt("id"));
			model.setItemcode(rs.getString("itemcode"));
			model.setItemname(rs.getString("itemname"));
			model.setType(rs.getString("type"));
			return model;
		}catch(SQLException e){
			throw new SQLException("结果集转为实例失败!",e);
		}
	}
	
	/**
	 * 通过类型查找s201表对应的内容
	 * @author SunYi
	 * @Date 2019年4月24日下午5:54:18
	 * @return List<S201>
	 */
	public List<S201> findListByType(String type) throws SQLException {
		String sql = "select * from s201 where type = ?";
		Object[] params = {type};
		return super.queryOjects(sql, params);
	}

}
