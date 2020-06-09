package com.gxuwz.medical.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.gxuwz.medical.domain.diseaseCard.DiseaseCard;

/**
 * 慢性病证管理数据访问层
 * @ClassName: DiseaseCardDao
 * @author SunYi
 * @Date 2019年5月30日上午11:16:11
 */
public class DiseaseCardDao extends GenericDao<DiseaseCard>{

	/**
	 * 将ResultSet对象转换成DiseaseCard对象实例
	 * @author SunYi
	 * @Date 2019年5月30日上午11:15:54
	 */
	@Override
	protected DiseaseCard handle(ResultSet rs) throws SQLException {
		try {
			DiseaseCard model = new DiseaseCard();
			model.setDiseaseCard(rs.getString("diseaseCard"));
			model.setNongheCard(rs.getString("nongheCard"));
			model.setIdCard(rs.getString("idCard"));
			model.setDiseaseCode(rs.getString("diseaseCode"));
			model.setAttachment(rs.getString("attachment"));
			model.setStartTime(rs.getDate("startTime"));
			model.setEndTime(rs.getDate("endTime"));
			return model;
		}catch (SQLException e) {
			throw new SQLException("结果集转为实例失败!",e);
		}
	}

}
