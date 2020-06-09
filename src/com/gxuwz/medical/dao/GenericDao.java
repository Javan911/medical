package com.gxuwz.medical.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gxuwz.medical.database.DbUtil;
import com.gxuwz.medical.domain.vo.Page;

/**
 * 抽象泛型DAO类，封装提供分页查询方法、结果集与对象的转换方法
 * @ClassName: GenericDao
 * @author SunYi
 * @Date 2019年4月5日下午1:33:55
 */
public abstract class GenericDao<T> {

	/**
	 * 定义抽象方法实现ResultSet结果集转换为对应的Class类型实例
	 * @author SunYi
	 * @Date 2019年4月5日下午1:33:38
	 * @return T
	 */
	protected abstract T handle(ResultSet rs)throws SQLException;
	
	 /**
	  * 查询对象集合
	  * @author SunYi
	  * @Date 2019年4月5日下午1:33:25
	  * @return List<T>
	  */
	public List<T> queryOjects(String sql, Object[] params) throws SQLException {
		Connection conn =null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<T> results=new ArrayList<T>();
		try{
			conn=DbUtil.getConn();
			pstmt=conn.prepareStatement(sql);
			int index=1;
			if(params!=null){
			  for(Object param:params){
				  if(param instanceof String){
					  pstmt.setString(index++, (String)param);
				  }
				  if(param instanceof Integer){
					  pstmt.setInt(index++, (Integer)param);
				  }
				  if(param instanceof Float){
					  pstmt.setFloat(index++, (Float)param);
				  }
			  }
			}
			rs=pstmt.executeQuery();
			while(rs.next()){
				T t = handle(rs);
				results.add(t);
			}
			return results;
		}catch(SQLException e){
			throw new SQLException("执行SQL["+sql+"]失败",e);
		}
		
	}
	
	 /**
	  * 分页查询
	  * @author SunYi
	  * @Date 2019年4月5日下午1:33:04
	  * @return Page
	  */
	public Page queryOjectsByPage(String sql, Object[] params, int pageNo, int size) throws SQLException {
		Connection conn =null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		Page page=null;
		List<T> datas=new ArrayList<T>();
		try{
			int start=(pageNo-1)*size;
			conn=DbUtil.getConn();
			pstmt=conn.prepareStatement(sql+" limit "+start+","+size);
			int index=1;
			if(params!=null){
			  for(Object param:params){
				  if(param instanceof String){
					  pstmt.setString(index++, (String)param);
				  }
				  if(param instanceof Integer){
					  pstmt.setInt(index++, (Integer)param);
				  }
				  if(param instanceof Float){
					  pstmt.setFloat(index++, (Float)param);
				  }
			  }
			}
			rs=pstmt.executeQuery();
			while(rs.next()){
				T t=handle(rs);
				datas.add(t);
			}
			// 统计SQL对应记录数
			int total=count(sql, params);
			page=new Page(total, pageNo, size, datas);
			return page;
		}catch(SQLException e){
			throw new SQLException("执行SQL["+sql+"]失败",e);
		}
		
	}
	
	
	 /**
	  * 统计记录数
	  * @author SunYi
	  * @Date 2019年4月5日下午1:33:15
	  * @return int
	  */
	public int count(String sql,Object[] params)throws SQLException{
		Connection conn =null;
		PreparedStatement pstmt=null;
		ResultSet rs =null;
		int c=0;
		try{
			conn=DbUtil.getConn();
			String sql_count="select count(*) from ("+sql+")as temp";
			pstmt=conn.prepareStatement(sql_count);
			int index=1;
			if(params!=null){
				  for(Object param:params){
					  if(param instanceof String){
						  pstmt.setString(index++, (String)param);
					  }
					  if(param instanceof Integer){
						  pstmt.setInt(index++, (Integer)param);
					  }
					  if(param instanceof Float){
						  pstmt.setFloat(index++, (Float)param);
					  }
				  }
				}
				rs=pstmt.executeQuery();
				if(rs.next()){
					 c=rs.getInt(1);
				}
				return c;
		}catch(SQLException e){
			throw new SQLException("执行COUNTSQL失败",e);
		}finally{
			DbUtil.close(rs, pstmt, conn);
		}
	}



}
