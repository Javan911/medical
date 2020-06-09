package com.gxuwz.medical.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gxuwz.medical.database.DbUtil;
import com.gxuwz.medical.domain.family.Family;

/**
 * 家庭档案管理数据访问层
 * @ClassName: FamilyDao
 * @author SunYi
 * @Date 2019年4月28日上午11:37:24
 */
public class FamilyDao extends GenericDao<Family> {
	
	/**
	 * 实现ResultSet结果集转换为Family类型实例
	 * @author SunYi
	 * @Date 2019年4月28日上午11:37:24
	 */
	@Override
	protected Family handle(ResultSet rs) throws SQLException {
		try{
			// 实例化行政区域对象
			Family model = new Family();
			// 取出ResultSet的值,存进Family对象
			model.setCountyCode(rs.getString("countyCode"));
			model.setTownCode(rs.getString("townCode"));
			model.setVillageCode(rs.getString("villageCode"));
			model.setGroupCode(rs.getString("groupCode"));
			model.setFamilyCode(rs.getString("familyCode"));
			model.setHouseHolder(rs.getString("houseHolder"));
			model.setFamilySize(rs.getInt("familySize"));
			model.setAgriculturalNum(rs.getInt("agriculturalNum"));
			model.setAddress(rs.getString("address"));
			model.setCreateTime(rs.getDate("createTime"));
			model.setRegistrar(rs.getString("registrar"));
			model.setHouseholdType(rs.getString("householdType"));
			model.setIsRural(rs.getInt("isRural"));
			model.setStatus(rs.getInt("status"));
			model.setRemark(rs.getString("remark"));
			// 返回家庭档案对象
			return model;
		}catch(SQLException e){
			throw new SQLException("结果集转为实例失败!",e);
		}
	}
	

	 /**
	  * 通过家庭编号自动计算家庭人口数
	  * @author SunYi
	  * @Date 2019年5月4日上午10:58:04
	  * @return void
	  */
	 public int getFamilySizeById(String familyCode) throws Exception {
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			int familySize = 0;
			try{
				conn = DbUtil.getConn();
				StringBuffer sql = new StringBuffer("select count(*) from t_person_archives where familyCode=? ");
				pstmt = conn.prepareStatement(sql.toString());
				int index = 1;
				pstmt.setString(index, familyCode);
				rs = pstmt.executeQuery();
				if(rs.next()){
					familySize = rs.getInt(1);
				}
			}catch(SQLException e){
				throw new SQLException("Family with id " + familyCode + " could not be loaded from the database.", e);
			}finally{
				DbUtil.close(rs, pstmt, conn);
			}
			return familySize;
		}

}
