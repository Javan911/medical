package com.gxuwz.medical.domain.role;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.gxuwz.medical.database.DbUtil;
import com.gxuwz.medical.domain.menu.Menu;
import com.gxuwz.medical.exception.RoleNotFoundException;

/**
 * 角色类
 * @ClassName: Role
 * @author SunYi
 * @Date 2019年4月2日下午8:53:42
 */
public class Role {
	
	// 角色编号
	private String roleid;
	// 角色名称
	private String roleName;
	// 对应的权限集合
	private Set<Menu> menus;
	
	
	
	/*  ***********************************************实体类***********************************************  */
	
	public Role(){
		
	}
	// 通过构造函数获取指定编号的角色信息
	public Role(String roleid)throws RoleNotFoundException{
		getRoleById(roleid);
	}
	public Role(String roleid,String rolename){
		this.roleid =roleid;
		this.roleName =rolename;
	}
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public void setMenus(Set<Menu> menus) {
		this.menus = menus;
	}
	
	
	/*  ***********************************************方法体***********************************************  */
	
	/**
	 * 预添加角色信息
	 * @author SunYi
	 * @Date 2019年4月2日下午10:08:48
	 * @return void
	 */
   public void toAddRole(String roleName,String[] menuids)throws SQLException{
	   Connection conn = null;
	   // 把传过来的roleName赋值给当前对象
	   this.roleName = roleName;
	   try{
		   conn = DbUtil.getConn();
		   // 1:开启手动提交事务
		   conn.setAutoCommit(false);
		   // 2:保存角色信息到数据库
		   addRole(conn);
		   // 3:循环关联菜单信息
		   if(menuids != null){
			   for(String m:menuids){
				   bindMenu(conn, m);
			   	}
		   }
		   // 4:提交事务
		   conn.commit();
	   }catch(SQLException e){
		   if(conn != null) {
			   // 事务回滚
			   conn.rollback();
		   }
		   throw e;
	   }finally{
		   DbUtil.close(conn);
	   }
   }
   
   /**
    * 预更新角色信息
    * @author SunYi
    * @Date 2019年4月2日下午10:14:11
    * @return void
    */
  public void toUpdateRole(String[]menuids)throws SQLException{
	   Connection conn = null;
	   try{
		   conn = DbUtil.getConn();
		   // 1：开启手动提交事务
		   conn.setAutoCommit(false);
		   // 2:更新角色信息到数据库
		   updateRole(conn);
		   // 3:删除原由角色关联
		   unbindMenu(conn);
		   // 4:循环关联菜单信息
		   for(String m:menuids){
			   bindMenu(conn, m);
		   }
		   // 4:提交事务
		   conn.commit();
	   }catch(SQLException e){
		   if(conn != null) {
			   // 事务回滚
			   conn.rollback();
		   }
		   throw e;
	   }finally{
		   DbUtil.close(conn);
	   }
  }
  
  /**
   * 预删除角色信息
   * @author SunYi
   * @Date 2019年4月2日下午11:28:55
   * @return void
   */
  public void toDeleteRole(String roleid)throws SQLException{
	   Connection conn = null;
	   // 把传过来的roleid赋给当前对象
	   this.roleid = roleid;
	   try{
		   conn = DbUtil.getConn();
		   // 1:开启手动提交事务
		   conn.setAutoCommit(false);
		   // 2:从数据库中删除角色信息
		   deleteRole(conn);
		   // 3:删除关联菜单信息
		   unbindMenu(conn);
		   // 4:提交事务
		   conn.commit();
	   }catch(SQLException e){
		   if(conn != null) {
			   // 事务回滚
			   conn.rollback();
		   }
		   throw e;
	   }finally{
		   DbUtil.close(conn);
	   }
  }
  
  /**
   * 从数据库中删除指定编号的角色
   * @author SunYi
   * @Date 2019年4月2日下午11:47:05
   * @return void
   */
  private void deleteRole(Connection conn)throws SQLException{
	  PreparedStatement pstmt = null;
	  try{
		  // 创建SQL语句
		  StringBuffer sqlBuff = new StringBuffer("delete from t_role where roleid=?");
		  pstmt = conn.prepareStatement(sqlBuff.toString());
		  // 把当前对象的roleid赋给占位符
		  pstmt.setString(1, this.roleid);
		  pstmt.executeUpdate(); 
	  }catch(SQLException e){
		  throw new SQLException("Failed to delete record from table !", e);
	  }finally{
		  DbUtil.close(pstmt);
	  }
  
   
}
 
  	/**
  	 * 添加角色到数据库
  	 * @author SunYi
  	 * @Date 2019年4月2日下午11:51:44
  	 * @return void
  	 */
   private void addRole(Connection conn)throws SQLException{
	  PreparedStatement pstmt = null;
	  try{
		  // 创建SQL语句
		  StringBuffer sqlBuff = new StringBuffer("insert into t_role(roleid,rolename)");
		  // 追加SQL语句
		  sqlBuff.append("values(?,?)");
		  pstmt = conn.prepareStatement(sqlBuff.toString());
		  // 把当前对象的值赋给占位符
		  pstmt.setString(1, this.roleid);
		  pstmt.setString(2, this.roleName);
		  // 执行添加操作
		  pstmt.executeUpdate(); 
	  }catch(SQLException e){
		  throw new SQLException("Failed to insert into table !", e);
	  }finally{
		  DbUtil.close(pstmt);
	  } 
   }
   
   	/**
   	 * 更新用户到数据库
   	 * @author SunYi
   	 * @Date 2019年4月2日下午11:54:31
   	 * @return void
   	 */
   private void updateRole(Connection conn)throws SQLException{
	  PreparedStatement pstmt=null;
	  try{
		  // 创建SQL语句
		  StringBuffer sqlBuff = new StringBuffer("update t_role t set t.rolename=? where t.roleid=?");
		  pstmt = conn.prepareStatement(sqlBuff.toString());
		  // 把当前对象的值赋给占位符
		  pstmt.setString(1, this.roleName);
		  pstmt.setString(2, this.roleid);
		  // 执行更新操作
		  pstmt.executeUpdate(); 
	  }catch(SQLException e){
		  throw new SQLException("Failed to update t_role !", e);
	  }finally{
		  DbUtil.close(pstmt);
	  }
   }
   
	/**
	 * 建立角色和对应权限的关联
	 * @author SunYi
	 * @Date 2019年4月2日下午11:55:50
	 * @return void
	 */
   private void bindMenu(Connection conn,String menuid)throws SQLException{
	  PreparedStatement pstmt = null;
	  try{
		  // 创建SQL语句
		  StringBuffer sqlBuff = new StringBuffer("insert into t_role_menu(roleid,menuid)");
		  // 追加SQL语句
		  sqlBuff.append("values(?,?)");
		  pstmt = conn.prepareStatement(sqlBuff.toString());
		  // 把当前对象的值赋给占位符
		  pstmt.setString(1, this.roleid);
		  pstmt.setString(2, menuid);
		  // 执行添加操作
		  pstmt.executeUpdate(); 
	  }catch(SQLException e){
		  throw new SQLException("Failed to bind to menu!", e);
	  }finally{
		  DbUtil.close(pstmt);
	  }
   }
   
	/**
	 * 解除角色与权限的关联
	 * @author SunYi
	 * @Date 2019年4月3日上午12:01:08
	 * @return void
	 */
   private void unbindMenu(Connection conn)throws SQLException{
	   PreparedStatement pstmt=null;
	   try{
		   // 创建SQL语句
		   StringBuffer sqlBuff = new StringBuffer("delete from t_role_menu where roleid=?");
		   // 预编译SQL语句
		   pstmt = conn.prepareStatement(sqlBuff.toString());
		   // 把当前对象的roldid赋值给占位符
		   pstmt.setString(1, this.roleid);
		   // 执行删除操作
		   pstmt.executeUpdate(); 
	   }catch(SQLException e){
		   throw new SQLException("Failed to unbind to menu!", e);
	   }finally{
		   DbUtil.close(pstmt);
	   }
   }
	  
	 /**
	  * 获取指定角色编号的角色信息
	  * @author SunYi
	  * @Date 2019年4月3日上午12:06:18
	  * @return void
	  */
	private void getRoleById(String roleid)throws RoleNotFoundException {
		Connection conn = null;
		PreparedStatement pstmt= null;
		ResultSet rs = null;
		try{
			conn = DbUtil.getConn();
			pstmt = conn.prepareStatement("select * from t_role where roleid=?");
			int index = 1;
			// 把当前对象的roleid赋给占位符
			pstmt.setString(index, roleid);
			// 执行查询操作
			rs = pstmt.executeQuery();
			if(rs.next()){
				// 给当前对象赋值
				this.roleid = rs.getString("roleid");
				this.roleName = rs.getString("rolename");
			}else{
				throw new RoleNotFoundException("Role with id " + roleid + " could not be loaded from the database.");
			}
		}catch(SQLException e){
			throw new RoleNotFoundException("Role with id " + roleid + " could not be loaded from the database.",e);
		}finally{
			DbUtil.close(rs, pstmt, conn);
		}
	}
	
	/**
	 * 获取指定角色编号的权限编号List集合
	 * @author SunYi
	 * @Date 2019年4月3日上午8:49:35
	 * @return List<String>
	 */
	private List<String> getMenuids() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// 实例化权限编号List集合
		List<String> menuids = new ArrayList<String>();
		try {
			conn = DbUtil.getConn();
			pstmt = conn.prepareStatement("select menuid from t_role_menu where roleid=?");
			int index = 1;
			pstmt.setString(index++, roleid);
			// 执行查询操作
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// 循环将查询结果存进集合
				menuids.add(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(rs, pstmt, conn);
		}
		return menuids;
	}

	/**
	 * 获取指定权限编号集合的权限Set集合
	 * @author SunYi
	 * @Date 2019年4月3日上午8:53:51
	 * @return Set<Menu>
	 */
	public Set<Menu> getMenus() {
		try {
			if (menus == null) {
				// 实例化权限Set集合
				menus= new HashSet<Menu>();
				// 调用方法获取权限编号List集合
				List<String> menuids = getMenuids();
				for (String menuid : menuids) {
					// 将权限编号的List集合传过去查询对应的权限信息
					Menu menu = new Menu(menuid);
					// 将得到的权限信息存进当前权限Set集合
					menus.add(menu);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return menus;
	}

}
