package com.gxuwz.medical.dao;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gxuwz.medical.database.DbUtil;
import com.gxuwz.medical.domain.user.User;
import com.gxuwz.medical.domain.vo.Page;

/**
 * 用户数据访问层
 * @ClassName: UserDao
 * @author SunYi
 * @Date 2019年4月3日下午4:32:59
 */
public class UserDao extends BaseDao {
	
	// 查询指定编号的用户信息
	private static final String SQL_SELECT_USERID = "select * from t_user where userid=?";

	/**
	 * 实现ResultSet结果集转换为User类型实例
	 * @author SunYi
	 * @Date 2019年4月3日下午9:57:25
	 */
	@Override
	protected User handle(ResultSet rs) throws SQLException {
		try{
			// 实例化用户对象
			User user=new User();
			// 取出ResultSet里的用户信息存进User对象
			user.setFullname(rs.getString("fullname"));
			user.setPwd(rs.getString("pwd"));
			user.setUserid(rs.getString("userid"));
			user.setAgencode(rs.getString("agencode"));
			user.setStatus(rs.getString("status"));
			// 返回用户对象
			return user;
		}catch(SQLException e){
			throw new SQLException("结果集转为实例失败!",e);
		}
	}
	
	 /**
	  * 获取对应的记录并转为对象实例
	  * @author SunYi
	  * @Date 2019年4月3日下午9:58:39
	  * @return User
	  */
	public User get(String userid)throws SQLException{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			conn = DbUtil.getConn();
			// 查询指定userid的用户信息
			pstmt = conn.prepareStatement(SQL_SELECT_USERID);
			int index = 1;
			pstmt.setString(index, userid);
			// 执行查询操作
			rs = pstmt.executeQuery();
			// 将结果集传去执行handle方法
			return handle(rs);
		}catch(SQLException e){
			throw new SQLException("读取userid记录失败",e);
		}
	}
	
	/**
	 * 获取用户集合
	 * @author SunYi
	 * @Date 2019年4月3日下午10:16:50
	 * @return List<User>
	 */
	public List<User> getUserList(String sql, Object[] params)throws SQLException{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// 实例化用户集合
		List<User> userList = new ArrayList<User>();
		try{
			conn = DbUtil.getConn();
			pstmt = conn.prepareStatement(sql);
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
			while(rs.next()){
				// 调用handle方法将查询得到的ResultSet转换成User对象
				User user = handle(rs);
				// 将转换得到的User对象存进集合
				userList.add(user);
			}
			// 返回用户集合
			return userList;
		}catch(SQLException e){
			throw new SQLException("执行SQL["+sql+"]失败",e);
		}
	}
	
	 /**
	  * 分页查询用户
	  * @author SunYi
	  * @Date 2019年4月3日下午10:08:59
	  * @return Page
	  */
	public Page getUserByPage(String sql,Object[] params,int pageNo,int size) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Page page = null;
		// 实例化用户集合
		List<User> userList = new ArrayList<User>();
		try{
			// 计算得出start数值
			int start = (pageNo-1)*size;
			conn = DbUtil.getConn();
			// 根据页面传过来的sql语句、start开始位置、size页面显示记录数进行分页查询
			pstmt = conn.prepareStatement(sql+" limit "+start+","+size);
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
			while(rs.next()){
				// 调用handle方法将查询得到的ResultSet转换成User对象
				User user = handle(rs);
				// 将转换得到的User对象存进集合
				userList.add(user);
			}
			// 统计SQL对应记录数
			int total = getCount(sql, params);
			// 构造分页
			page = new Page(total, pageNo, size, userList);
			// 返回分页对象
			return page;
		}catch(SQLException e){
			e.printStackTrace();
			throw e;
		}
	}
	
	

}
