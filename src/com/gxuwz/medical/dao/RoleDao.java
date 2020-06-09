package com.gxuwz.medical.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gxuwz.medical.database.DbUtil;
import com.gxuwz.medical.domain.role.Role;
import com.gxuwz.medical.domain.vo.Page;

/**
 * 角色数据访问层
 * @ClassName: RoleDao
 * @author SunYi
 * @Date 2019年4月3日下午10:18:16
 */
public class RoleDao extends BaseDao {
	
	public RoleDao(){
		
	}

	/**
	 * 实现ResultSet结果集转换为Role类型实例
	 * @author SunYi
	 * @Date 2019年4月3日下午10:19:12
	 */
	@Override
	protected Role handle(ResultSet rs) throws SQLException {
		try{
			// 实例化角色对象
			Role role = new Role();
			// 取出ResultSet的值,存进Role对象
			role.setRoleid(rs.getString("roleid"));
			role.setRoleName(rs.getString("rolename"));
			// 返回角色对象
			return role;
		}catch(SQLException e){
			throw new SQLException("结果集转为实例失败!",e);
		}
	}
	
	 /**
	  * 获取角色集合
	  * @author SunYi
	  * @Date 2019年4月3日下午10:20:31
	  * @return List<Role>
	  */
	public List<Role> getRoleList(String sql,Object[] params)throws SQLException{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// 实例化角色集合
		List<Role> roleList = new ArrayList<Role>();
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
			rs = pstmt.executeQuery();
			while(rs.next()){
				// 调用handle方法将查询得到的ResultSet转换成Role对象
				Role role = handle(rs);
				// 将转换得到的Role对象存进集合
				roleList.add(role);
			}
			// 返回角色集合
			return roleList;
		}catch(SQLException e){
			throw new SQLException("执行SQL["+sql+"]失败",e);
		}
	}
	
	 /**
	  * 分页查询角色
	  * @author SunYi
	  * @Date 2019年4月3日下午10:24:14
	  * @return Page
	  */
	public Page getRoleByPage(String sql,Object[] params,int pageNo,int size)throws SQLException{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Page page = null;
		// 实例化角色集合
		List<Role> roleList = new ArrayList<Role>();
		try{
			// 计算得出start
			int start = (pageNo-1)*size;
			conn=DbUtil.getConn();
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
				// 调用handle方法将查询得到的ResultSet转换成Role对象
				Role role = handle(rs);
				// 将Role对象存进集合
				roleList.add(role);
			}
			// 统计SQL对应记录数
			int total = getCount(sql, params);
			// 构造分页
			page = new Page(total, pageNo, size, roleList);
			// 返回分页对象
			return page;
		}catch(SQLException e){
			throw new SQLException("执行SQL["+sql+"]失败",e);
		}finally{
			DbUtil.close(rs, pstmt, conn);
		}
	}
	

}
