package com.gxuwz.medical.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gxuwz.medical.database.DbUtil;

/**
 * 数据访问层基类
 * @ClassName: BaseDao
 * @author SunYi
 * @Date 2019年4月3日下午4:34:49
 */
public abstract class BaseDao {
	
	/**
	 * 定义抽象方法实现ResultSet结果集转换为对应的Class类型实例
	 * @author SunYi
	 * @Date 2019年4月3日下午4:34:39
	 * @return Object
	 */
	protected abstract Object handle(ResultSet rs) throws SQLException;
	
	/**
	 * 统计记录数
	 * @author SunYi
	 * @Date 2019年4月3日下午4:35:08
	 * @return int
	 */
	public int getCount(String sql, Object[] params) throws SQLException{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// 初始化记录数
		int count = 0;
		try{
			conn = DbUtil.getConn();
			// 创建SQL语句,将参数sql语句进行查询
			String sql_count = "select count(*) from ("+sql+")as temp";
			pstmt = conn.prepareStatement(sql_count);
			int index = 1;
			if(params != null){
			  for(Object param:params){
				  // 判断param对象是否是String类的实例
				  if(param instanceof String){
					  pstmt.setString(index++, (String)param);
				  }
				  // 判断param对象是否是Integer类的实例
				  if(param instanceof Integer){
					  pstmt.setInt(index++, (Integer)param);
				  }
				  // 判断param对象是否是Float类的实例
				  if(param instanceof Float){
					  pstmt.setFloat(index++, (Float)param);
				  }
			  }
			}
			// 执行查询操作
			rs = pstmt.executeQuery();
			if(rs.next()){
				// 将查询结果赋值给count
				count = rs.getInt(1);
			}
			// 返回记录数
			return count;
		}catch(SQLException e){
			throw new SQLException("执行COUNTSQL失败",e);
		}finally{
			DbUtil.close(rs, pstmt, conn);
		}
	}

}
