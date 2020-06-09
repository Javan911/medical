package com.gxuwz.medical.domain.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.gxuwz.medical.database.DbUtil;
import com.gxuwz.medical.domain.role.Role;
import com.gxuwz.medical.exception.UserNotFoundException;

/**
 * 用户实体类
 * @ClassName: User
 * @author SunYi
 * @Date 2019年3月15日下午10:33:05
 */
public class User {

	// 工号
	private String userid;
	// 密码
	private String pwd;
	// 姓名
	private String fullname;
	// 状态
	private String status;
	// 所在农合机构编码
	private String agencode;
	// 用户对应角色列表
	private Set<Role> roles;

	
	
	/*  ***********************************************实体类***********************************************  */
	
	public User() {

	}
	// 调用构造方法获取指定用户编号的用户信息
	public User(String userid) throws UserNotFoundException {
		getUserById(userid);
	}
	public User(String userid, String pwd, String fullname, String status,String agencode) {
		super();
		this.userid = userid;
		this.pwd = pwd;
		this.fullname = fullname;
		this.status = status;
		this.agencode = agencode;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAgencode() {
		return agencode;
	}
	public void setAgencode(String agencode) {
		this.agencode = agencode;
	}
	
	
	/*  ***********************************************方法体***********************************************  */

	/**
	 * 获取指定用户的信息
	 * @author SunYi
	 * @Date 2019年3月15日下午9:14:08
	 * @return void
	 */
	private void getUserById(String userid) throws UserNotFoundException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// 获取连接
			conn = DbUtil.getConn();
			// 创建SQL语句
			String sql = "select * from t_user where userid=? ";
			// 预编译SQL语句
			pstmt = conn.prepareStatement(sql);
			int index = 1;
			// 给占位符赋值
			pstmt.setString(index, userid);
			// 执行查询
			rs = pstmt.executeQuery();
			if (rs != null && rs.next()) {
				// 把查询出的结果给当前对象赋值
				this.fullname = rs.getString("fullname");
				this.pwd = rs.getString("pwd");
				this.userid=rs.getString("userid");
				this.agencode=rs.getString("agencode");
				this.status =rs.getString("status");
			}
		} catch (SQLException e) {
			throw new UserNotFoundException("User with id " + userid + " could not be loaded from the database.");
		} finally {	// 关闭资源
			DbUtil.close(rs, pstmt, conn);
		}
	}
	
	 /**
	  * 预删除用户以及用户关联的角色信息
	  * @author SunYi
	  * @Date 2019年4月1日下午9:26:14
	  * @return void
	  */
	 public void toDeleteUser(String userid)throws SQLException{
		 Connection conn=null;
		 // 把传过来的值赋给当前对象
		 this.userid =userid;
		 try{
			 conn =DbUtil.getConn();
			 // 1:开启手动提交事务
			 conn.setAutoCommit(false);
			 // 2:从数据库中删除用户信息(把当前连接传过去)
			 deleteUser(conn);
			 // 3:删除关联角色信息(把当前连接传过去)
			 unbindRole(conn);
			 // 4：提交事务
			 conn.commit();
		 }catch(SQLException e){
			 // 事务回滚
			 if(conn != null) {
			    conn.rollback();
			 }
			 throw e;
		 }finally{
			 DbUtil.close(conn);
		 }
	  }
	 	
	  /**
	   * 从数据库中删除指定编号的用户
	   * @author SunYi
	   * @Date 2019年4月1日下午9:36:37
	   * @return void
	   */
	 private void deleteUser(Connection conn)throws SQLException{
		 PreparedStatement pstmt=null;
		 try{
			 StringBuffer sqlBuff=new StringBuffer("delete from t_user where userid=?");
			 pstmt=conn.prepareStatement(sqlBuff.toString());
			 // 把当前对象的值赋给占位符
			 pstmt.setString(1, this.userid);
			 // 执行删除操作
			 pstmt.executeUpdate(); 
		 }catch(SQLException e){
			 throw new SQLException("Failed to delete record from table !", e);
		 }finally{
			 DbUtil.close(pstmt);
		 }
	 }

	 /**
	  * 用户构造函数里查询当前登录用户的信息
	  * @param userid
	  * @param pwd
	  * @throws UserNotFoundException
	  */
	public User(String userid, String pwd) throws UserNotFoundException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DbUtil.getConn();
			pstmt = conn.prepareStatement("select * from t_user where userid=? and pwd=?");
			int index = 1;
			// 给占位符赋值
			pstmt.setString(index++, userid);
			pstmt.setString(index, pwd);
			// 执行查询操作
			rs = pstmt.executeQuery();
			if (rs != null && rs.next()) {
				// 把查询出来的结果给当前对象赋值
				this.fullname = rs.getString("fullname");
				this.pwd = rs.getString("pwd");
				this.userid = rs.getString("userid");
			} else {
				throw new UserNotFoundException("User with id " + userid + " could not be loaded from the database.");
			}
		} catch (SQLException e) {
			throw new UserNotFoundException("User with id " + userid + " could not be loaded from the database.");
		} finally {
			DbUtil.close(rs, pstmt, conn);
		}
	}
	
	  /**
	   * 预添加用户
	   * @author SunYi
	   * @Date 2019年4月1日下午9:47:36
	   * @return void
	   */
     public void toAddUser(String[] roleids)throws SQLException{
    	Connection conn=null;
    	try{
    		conn=DbUtil.getConn();
    		// 开启手动提交事务
    		conn.setAutoCommit(false);
    		// 1:添加用户到数据库
    		addUser(conn);
    		// 2:建立用户与角色关联
    		if(roleids!=null){
    			for(String roleid:roleids){
        			bindRole(conn, roleid);
        		}
    		}
    		// 提交事务
    		conn.commit();
    	}catch(SQLException e){
    		// 事务回滚
    		if(conn != null) {
    		   conn.rollback();
    		}
    		throw e;
    	}finally{
    		DbUtil.close(conn);
    	}
     }
     	
       /**
        * 建立用户与角色关联
        * @author SunYi
        * @Date 2019年4月1日下午9:51:47
        * @return void
        */
	  private void bindRole(Connection conn,String roleid)throws SQLException{
		  PreparedStatement pstmt=null;
		  try{
			  // 创建SQL语句
			  StringBuffer sqlBuff=new StringBuffer("insert into t_user_role(userid,roleid)");
			  // 追加SQL条件
			  sqlBuff.append("values(?,?)");
			  pstmt=conn.prepareStatement(sqlBuff.toString());
			  // 把当前用户对象的userid赋给占位符
			  pstmt.setString(1, this.userid);
			  // 把传过来的roleid赋给占位符
			  pstmt.setString(2, roleid);
			  pstmt.executeUpdate(); 
		  }catch(SQLException e){
			  throw new SQLException("Failed to bind to Role!", e);
		  }finally{
			  DbUtil.close(pstmt);
		  }
	  }
	  
	  /**
	   * 预更新用户信息
	   * @param roleids
	   * @throws SQLException
	   */
	  public void toUpdateUser(String[] roleids)throws SQLException{
		   Connection conn=null;
		   try{
			   conn =DbUtil.getConn();
			   // 1:开启手动提交事务
			   conn.setAutoCommit(false);
			   // 2:更新用户信息到数据库
			   updateUser(conn);
			   // 3:删除原有角色关联
			   unbindRole(conn);
			   // 4:循环关联菜单信息
			   if(roleids != null) {
				   for(String roleid:roleids){
					   bindRole(conn, roleid);
				   }
			   }
			   // 4:提交事务
			   conn.commit();
		   }catch(SQLException e){
			   // 事务回滚
			   if(conn != null) {
				 conn.rollback();
			   }
			   throw e;
		   }finally{
			   DbUtil.close(conn);
		   }
	  }
	  
	  /**
	   * 解除User与Role的关联(根据传过来的Connection对象进行操作)
	   * @author SunYi
	   * @Date 2019年4月1日下午9:30:54
	   * @return void
	   */
	  private void unbindRole(Connection conn) throws SQLException{
		  PreparedStatement pstmt=null;
		  try{
			  // 创建SQL语句
			  StringBuffer sqlBuff=new StringBuffer("delete from t_user_role where userid=?");
			  // 预编译SQL语句
			  pstmt=conn.prepareStatement(sqlBuff.toString());
			  // 把当前对象的值赋给占位符
			  pstmt.setString(1, this.userid);
			  // 执行操作
			  pstmt.executeUpdate(); 
		  }catch(SQLException e){
			  throw new SQLException("Failed to unbind to Role !", e);
		  }finally{
			  DbUtil.close(pstmt);
		  }
	}
	  
	  /**
	   * 更新用户到数据库
	   * @author SunYi
	   * @Date 2019年4月1日下午9:55:03
	   * @return void
	   */
	  private void updateUser(Connection conn)throws SQLException{
		  PreparedStatement pstmt=null;
		  try{
			  StringBuffer sqlBuff=new StringBuffer("update t_user set fullname=?,pwd=?,agencode=?,status=? where userid=?");
			  pstmt=conn.prepareStatement(sqlBuff.toString());
			  // 把当前用户对象的值赋给占位符
			  pstmt.setString(1, this.fullname);
			  pstmt.setString(2, this.pwd);
			  pstmt.setString(3, this.agencode);
			  pstmt.setString(4, this.status);
			  pstmt.setString(5, this.userid);
			  pstmt.executeUpdate(); 
		  }catch(SQLException e){
			  throw new SQLException("Failed to update t_user  !", e);
		  }finally{
			  DbUtil.close(pstmt);
		  }
	  }
	  
	  /**
	   * 添加用户到数据库
	   * @author SunYi
	   * @Date 2019年4月1日下午10:00:56
	   * @return void
	   */
     private void addUser(Connection conn) throws SQLException {
		  PreparedStatement pstmt=null;
		  try{
			  StringBuffer sqlBuff=new StringBuffer();
			  // 追加SQL语句
			  sqlBuff.append("insert into t_user");
			  sqlBuff.append("(userid,fullname,pwd,agencode,status)");
			  sqlBuff.append("values(?,?,?,?,?)");
			  pstmt=conn.prepareStatement(sqlBuff.toString());
			  // 把当前用户对象的值赋给占位符
			  pstmt.setString(1, this.userid);
			  pstmt.setString(2, this.fullname);
			  pstmt.setString(3, this.pwd);
			  pstmt.setString(4, this.agencode);
			  pstmt.setString(5, this.status);
			  // 执行添加操作
			  pstmt.executeUpdate(); 
		  }catch(SQLException e){
			  throw e;
		  }finally{
			  DbUtil.close(pstmt);
		  } 
     }

     /**
      * 获取角色编号List集合
      * @author SunYi
      * @Date 2019年4月1日下午10:03:19
      * @return List<String>
      */
	private List<String> getRoleids() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// 实例化角色编号List集合
		List<String> roleids = new ArrayList<String>();
		try {
			conn = DbUtil.getConn();
			pstmt = conn.prepareStatement("select roleid from t_user_role where userid=?");
			int index = 1;
			pstmt.setString(index++, this.userid);
			// 执行查询操作
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// 循环把查询出的结果存进集合
				roleids.add(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(rs, pstmt, conn);
		}
		return roleids;
	}

	/**
	 * 获取角色Set集合
	 * @author SunYi
	 * @Date 2019年4月1日下午10:11:19
	 * @return Set<Role>
	 */
	public Set<Role> getRoles() {
		try {
			if (roles == null) {
				// 实例化角色Set集合
				roles = new HashSet<Role>();
				// 调用方法获取角色编号List集合
				List<String> roleids = getRoleids();
				// 遍历集合
				for (String roleid : roleids) {
					// 把遍历出的角色编号传过去查询角色信息,并把查询到的角色信息存进当前role对象
					Role role = new Role(roleid);
					// 把role对象存进当前用户的角色Set集合
					roles.add(role);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return roles;
	}

}
