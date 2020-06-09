package com.gxuwz.medical.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.gxuwz.medical.domain.disease.Disease;

/**
 * 慢性病分类管理数据访问层
 * @ClassName: DiseaseDao
 * @author SunYi
 * @Date 2019年4月12日下午2:45:16
 */
public class DiseaseDao extends GenericDao<Disease>{

	/**
	 * 将ResultSet对象转换成Disease对象实例
	 * @author SunYi
	 * @Date 2019年4月12日下午2:35:23
	 */
	@Override
	protected Disease handle(ResultSet rs) throws SQLException {
		try {
			Disease model = new Disease();
			model.setDiseaseCode(rs.getString("diseaseCode"));
			model.setPinyinCode(rs.getString("pinyinCode"));
			model.setDiseaseName(rs.getString("diseaseName"));
			return model;
		}catch (SQLException e) {
			throw new SQLException("结果集转为实例失败!",e);
		}
	}

}
