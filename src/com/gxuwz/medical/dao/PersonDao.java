package com.gxuwz.medical.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.gxuwz.medical.database.DbUtil;
import com.gxuwz.medical.domain.person.Person;

/**
 * 参合人员档案管理数据访问层
 * @ClassName: PersonDao
 * @author SunYi
 * @Date 2019年4月28日上午11:37:24
 */
public class PersonDao extends GenericDao<Person> {
	
	/**
	 * 实现ResultSet结果集转换为Person类型实例
	 * @author SunYi
	 * @Date 2019年4月28日上午11:37:24
	 */
	@Override
	protected Person handle(ResultSet rs) throws SQLException {
		try{
			// 实例化参合人员档案对象
			Person model = new Person();
			// 取出ResultSet的值,存进Person对象
			model.setFamilyCode(rs.getString("familyCode"));
			model.setNongheCard(rs.getString("nongheCard"));
			model.setMedicalCard(rs.getString("medicalCard"));
			model.setHouseNum(rs.getString("houseNum"));
			model.setRelationship(rs.getString("relationship"));
			model.setIdCard(rs.getString("idCard"));
			model.setName(rs.getString("name"));
			model.setGender(rs.getString("gender"));
			model.setAge(rs.getInt("age"));
			model.setBirthDate(rs.getDate("birthDate"));
			model.setNation(rs.getString("nation"));
			model.setHealth(rs.getString("health"));
			model.setEducation(rs.getString("education"));
			model.setWorkType(rs.getString("workType"));
			model.setIsRuralHukou(rs.getString("isRuralHukou"));
			model.setCareer(rs.getString("career"));
			model.setWorkUnit(rs.getString("workUnit"));
			model.setTelephone(rs.getString("telephone"));
			model.setLiveAddress(rs.getString("liveAddress"));
			// 返回参合人员档案对象
			return model;
		}catch(SQLException e){
			throw new SQLException("结果集转为实例失败!",e);
		}
	}
	
	/**
	 * 通过出生日期自动计算年龄
	 * @author SunYi
	 * @Date 2019年5月4日上午11:29:26
	 * @return int
	 */
	public int getAgeByBirthDay(Date birthDay) throws ParseException {
        int age = 0;
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) { //出生日期晚于当前时间，无法计算
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);  //当前年份
        int monthNow = cal.get(Calendar.MONTH);  //当前月份
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //当前日期
        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        age = yearNow - yearBirth;   //计算整岁数
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;//当前日期在生日之前，年龄减一
            } else {
                age--;//当前月份在生日之前，年龄减一
            }
        }
        return age;
    }
	
	/**
	 * 获取指定家庭编号下的所有用户
	 * @author SunYi
	 * @Date 2019年5月5日下午8:23:32
	 * @return List<Person>
	 */
	public List<Person> getPersonByFamily(String familyCode) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Person> psList = new ArrayList<Person>();
		try{
			conn = DbUtil.getConn();
			pstmt = conn.prepareStatement("select * from t_person_archives where familyCode=? ");
			int index = 1;
			// 把页面传过来的treeID的值赋给占位符
			pstmt.setString(index++, familyCode);
			// 执行查询操作
			rs = pstmt.executeQuery();
			while(rs.next()){
				Person ps = new Person();
				ps.setIdCard(rs.getString("idCard"));
				psList.add(ps);
			}
		}catch(SQLException e){
			throw new SQLException("Person with id " + familyCode + " could not be loaded from the database.", e);
		}finally{
			DbUtil.close(rs, pstmt, conn);
		}
		return psList;
	}
	
}
