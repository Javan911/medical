package com.gxuwz.medical.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.gxuwz.medical.domain.medical.Medical;

/**
 * 医疗机构数据访问层
 * @ClassName: MedicalDao
 * @author SunYi
 * @Date 2019年4月24日下午5:53:03
 */
public class MedicalDao extends GenericDao<Medical> {

	/**
	 * 实现ResultSet结果集转换为Medical类型实例
	 * @author SunYi
	 * @Date 2019年4月24日下午5:50:12
	 */
	@Override
	protected Medical handle(ResultSet rs) throws SQLException {
		try {
			Medical model = new Medical();
			model.setJgbm(rs.getString("jgbm"));
			model.setZzjgbm(rs.getString("zzjgbm"));
			model.setJgmc(rs.getString("jgmc"));
			model.setDqbm(rs.getString("dqbm"));
			model.setAreacode(rs.getString("areacode"));
			model.setLsgx(rs.getString("lsgx"));
			model.setJgjb(rs.getString("jgjb"));
			model.setSbddlx(rs.getString("sbddlx"));
			model.setPzddlx(rs.getString("pzddlx"));
			model.setSsjjlx(rs.getString("ssjjlx"));
			model.setWsjgdl(rs.getString("wsjgdl"));
			model.setWsjgxl(rs.getString("wsjgxl"));
			model.setZgdw(rs.getString("zgdw"));
			model.setKysj(rs.getDate("kysj"));
			model.setFrdb(rs.getString("frdb"));
			model.setZczj(rs.getDouble("zczj"));
			// 返回Medical对象
			return model;
		}catch(SQLException e){
			throw new SQLException("结果集转为实例失败!",e);
		}
	}

}
