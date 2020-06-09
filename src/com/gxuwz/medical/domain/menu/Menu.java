package com.gxuwz.medical.domain.menu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gxuwz.medical.database.DbUtil;
import com.gxuwz.medical.exception.MenuException;

/**
 * 权限实体类
 * @ClassName: Menu
 * @author SunYi
 * @Date 2019年4月3日上午10:01:11
 */
public class Menu {
	
	// 获取指定menupid的权限信息
	private static final String SELECT_PARENT_MENU = "select * from t_menu where menupid=? order by menuid asc";
	// 获取指定menuid的权限信息
	private static final String SELECT_SELF_MENU = "select * from t_menu where menuid=? order by menuid asc";
	// 获取指定menuid和roleid的中间表权限信息
	private static final String SELECT_ROLE_MENU = "select * from t_role_menu where menuid=? and roleid=?";
	
	// 权限编号
	private String menuid;
	// 上一级权限编号
	private String menupid;
	// 权限名称
	private String menuname;
	// 权限URL路径
	private String url;
	// 上一级权限对象
	private Menu parent;
	//菜单级数
	private int level;
	
	
	/*  ***********************************************实体类***********************************************  */
	
	public Menu(){
			
	}
	public Menu(String menuname,String url)throws MenuException{
		this.menuname = menuname;
		this.url = url;
	}
	
	/**
	 * 通过构造函数构造菜单节点实例
	 * @param menuid
	 * @throws MenuException
	 */
	public Menu(String menuid)throws MenuException{
		this.menuid = menuid;
		this.menuname = "";
		if(!"0".equals(menuid)){
			getMenuById(menuid);
		}
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getMenuid() {
		return menuid;
	}
	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}
	public String getMenupid() {
		return menupid;
	}
	public void setMenupid(String menupid) {
		this.menupid = menupid;
	}
	public String getMenuname() {
		return menuname;
	}
	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
	/*  ***********************************************方法体***********************************************  */
	
	/**
	 * 通过当前上一级权限编号获取上一级权限对象
	 * @author SunYi
	 * @Date 2019年4月3日上午10:05:34
	 * @return Menu
	 */
	public Menu getMenuParent() {
		try{
			if(this.parent == null){
				this.parent = new Menu(this.menupid);
			}
		}catch(MenuException e){
			e.printStackTrace();
		}
		return this.parent;
	}
	
	   /**
	    * 预删除权限信息
	    * @author SunYi
	    * @Date 2019年4月3日上午10:09:50
	    * @return void
	    */
	  public void toDeleteMenu(String menuid)throws SQLException{
		   Connection conn = null;
		   // 将传过来的权限编号赋给当前对象
		   this.menuid = menuid;
		   try{
			   conn = DbUtil.getConn();
			   // 1:开启手动提交事务
			   conn.setAutoCommit(false);
			   // 2:从数据库中删除权限信息
			   deleteMenu(conn);
			   // 3:删除关联角色的权限信息
			   unbindRole(conn);
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
	   * 预添加权限信息
	   * @author SunYi
	   * @Date 2019年4月3日上午10:14:14
	   * @return void
	   */
	public void toAddMenu(String menupid,int plevel)throws SQLException{
		Connection conn = null;
		// 将传过来的值赋给当前对象
		this.menupid = menupid;
		this.level = plevel+1;
		try{
			conn =DbUtil.getConn();
			// 1:开启手动提交事务
			conn.setAutoCommit(false);
			// 2:自动生成权限菜单编号
			this.menuid = createMenuid();
		    // 3:添加权限到数据库
			addMenu(conn);
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
	   * 预更新权限菜单信息
	   * @author SunYi
	   * @Date 2019年4月3日上午10:19:14
	   * @return void
	   */
	public void toUpdateMenu(String menuname,String url)throws SQLException{
		Connection conn = null;
		// 将页面传过来的值赋给当前对象
		this.menuname = menuname;
		this.url = url;
		try{
			conn = DbUtil.getConn();
			// 1:开启手动提交事务
			conn.setAutoCommit(false);
		    // 2:更新权限信息到数据库
			updateMenu(conn);
			// 3:提交事务
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
	 * 从数据库中删除权限信息
	 * @author SunYi
	 * @Date 2019年4月3日上午10:17:39
	 * @return void
	 */
	private void deleteMenu(Connection conn)throws SQLException{
		PreparedStatement pstmt = null;
		try{
			StringBuffer sqlBuff = new StringBuffer("delete from t_menu where menuid=?");
			pstmt = conn.prepareStatement(sqlBuff.toString());
			// 将当前menuid给占位符赋值
			pstmt.setString(1, this.menuid);
			// 执行删除操作
			pstmt.executeUpdate(); 
		}catch(SQLException e){
			throw new SQLException("Failed to delete record from table !", e);
		}finally{
			DbUtil.close(pstmt);
		}
	}
	
	 /**
	  * 解除Menu与Role关联
	  * @author SunYi
	  * @Date 2019年4月3日上午10:25:09
	  * @return void
	  */
	public void unbindRole(Connection conn) throws SQLException{
		  PreparedStatement pstmt = null;
		  try{
			  StringBuffer sqlBuff = new StringBuffer("delete from t_role_menu where menuid=?");
			  pstmt = conn.prepareStatement(sqlBuff.toString());
			  // 把当前menuid赋给占位符
			  pstmt.setString(1, this.menuid);
			  // 执行删除操作
			  pstmt.executeUpdate(); 
		  }catch(SQLException e){
			  throw new SQLException("Failed to unbind to Role !", e);
		  }finally{
			  DbUtil.close(pstmt);
		  }
	}
	
	 /**
	  * 添加权限菜单信息到数据库
	  * @author SunYi
	  * @Date 2019年4月3日上午10:26:14
	  * @return void
	  */
	private void addMenu(Connection conn)throws SQLException{
		  PreparedStatement pstmt = null;
		  try{
			  // 创建SQL语句
			  StringBuffer sqlBuff = new StringBuffer("insert into t_menu(menuid,menuname,menupid,url,level)");
			  // 追加SQL语句
			  sqlBuff.append("values(?,?,?,?,?)");
			  pstmt = conn.prepareStatement(sqlBuff.toString());
			  // 将当前对象的值赋给占位符
			  pstmt.setString(1, this.menuid);
			  pstmt.setString(2, this.menuname);
			  pstmt.setString(3, this.menupid);
			  pstmt.setString(4, this.url);
			  pstmt.setInt(5, this.level);
			  // 执行添加操作
			  pstmt.executeUpdate(); 
		  }catch(SQLException e){
			  throw e;
		  }finally{
			  DbUtil.close(pstmt);
		  } 
	}
	
	 /**
	  * 更新权限菜单信息到数据库
	  * @author SunYi
	  * @Date 2019年4月3日上午10:27:53
	  * @return void
	  */
	private void updateMenu(Connection conn)throws SQLException{
		  PreparedStatement pstmt=null;
		  try{
			  // 创建SQL语句
			  StringBuffer sqlBuff = new StringBuffer("update t_menu t set t.menuname=?,t.url=? where t.menuid=?");
			  pstmt = conn.prepareStatement(sqlBuff.toString());
			  // 把当前对象的值赋给占位符
			  pstmt.setString(1, this.menuname);
			  pstmt.setString(2, this.url);
			  pstmt.setString(3, this.menuid);
			  // 执行更新操作
			  pstmt.executeUpdate(); 
		  }catch(SQLException e){
			  throw new SQLException("Failed to update t_menu !", e);
		  }finally{
			  DbUtil.close(pstmt);
		  } 
	}
	
	 /**
	  * 获取指定权限编号和角色编号的角色-权限中间表信息
	  * @author SunYi
	  * @Date 2019年4月3日上午10:29:18
	  * @return boolean
	  */
    public boolean hasRole(String roleid)throws MenuException{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			conn = DbUtil.getConn();
			pstmt = conn.prepareStatement(SELECT_ROLE_MENU);
			int index=1;
			// 将当前对象的menuid赋给占位符
			pstmt.setString(index++, this.menuid);
			// 将传过来的roldid赋给占位符
			pstmt.setString(index, roleid);
			// 执行查询操作
			rs = pstmt.executeQuery();
			// 返回查询结果(有值为true,无值为false)
			return rs.next();
		}catch(SQLException e){
			throw new MenuException("Failed to query roleid= "+roleid+" from database!",e);
		}finally{
			DbUtil.close(rs, pstmt, conn);
		}
    }
    
     /**
      * 自动生成菜单编号
      * @author SunYi
      * @Date 2019年4月3日上午10:35:26
      * @return String
      */
    private String createMenuid() throws SQLException{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			conn = DbUtil.getConn();
			// 创建SQL语句(查询上一级权限编号下的最大权限编号)
			String sql = "select max(menuid) from t_menu where menupid=?";
			pstmt = conn.prepareStatement(sql);
			int index = 1;
			// 将当前menupid赋给占位符
			pstmt.setString(index, this.menupid);
			// 执行查询操作
			rs=pstmt.executeQuery();
			String maxcode = "";
			if(rs.next()) {
			   // 接收查询结果得到最大权限编号
			   maxcode = rs.getString(1);
			}
			if(maxcode != null) {
			  // 删去最大权限编号后两位数得到新的开始位数
			  int beginIndex = maxcode.length()-2;
			  // 再用开始位数截取得到最大权限编号的最后两位数
			  String no = maxcode.substring(beginIndex);  // 也可以写成 String no = maxcode.substring(beginIndex, maxcode.length());
			  // 将得到的数值转换成int类型
			  Integer number = Integer.parseInt(no);
			  // 将数值自增
			  ++number;
			  // 使用0补足位数
			  no = String.format("%02d", number);
			  // 拼接上一级权限编号和截取并自增得到的数值得到新的权限菜单编号
			  maxcode = this.menupid + no;
			}else{
				// 拼接上一级权限编号和01得到第一个权限菜单编号
				maxcode = this.menupid + "01";
			}
			// 返回权限菜单编号
			return maxcode;
		}catch(SQLException e){
			throw new SQLException("创建父节点Id" + this.menuid + "失败!", e);
		}finally{
			DbUtil.close(rs, pstmt, conn);
		}
	}
    
     /**
      * 获取指定权限编号的权限菜单信息
      * @author SunYi
      * @Date 2019年4月3日上午11:02:25
      * @return void
      */
	private void getMenuById(String menuid)throws MenuException{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			conn = DbUtil.getConn();
			pstmt = conn.prepareStatement(SELECT_SELF_MENU);
			int index = 1;
			// 将传过来的menuid赋给占位符
			pstmt.setString(index, menuid);
			// 执行查询操作
			rs=pstmt.executeQuery();
			if(rs.next()){
				// 将查询结果赋值给当前对象
				this.menuid=rs.getString("menuid");
				this.menuname=rs.getString("menuname");
				this.menupid =rs.getString("menupid");
				this.url =rs.getString("url");
				this.level=rs.getInt("level");
			}
		}catch(SQLException e){
			throw new MenuException("Failed to load id"+menuid+" from database!",e);
		}finally{
			DbUtil.close(rs, pstmt, conn);
		}
	}
	
	 /**
	  * 获取当前权限编号的子权限菜单信息
	  * @author SunYi
	  * @Date 2019年4月3日上午11:05:41
	  * @return List<Menu>
	  */
	public List<Menu> getMenuChildren()throws MenuException{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// 实例化权限集合
		List<Menu> menuList = new ArrayList<Menu>();
		try{
			conn = DbUtil.getConn();
			pstmt = conn.prepareStatement(SELECT_PARENT_MENU);
			int index = 1;
			// 将当前对象的menuid赋给占位符
			pstmt.setString(index, this.menuid);
			// 执行查询操作
			rs=pstmt.executeQuery();
			while(rs.next()){
				// 实例化权限对象
				Menu menu=new Menu();
				// 将查询结果赋值给新的权限对象
				menu.setMenuid(rs.getString("menuid"));
				menu.setMenupid(rs.getString("menupid"));
				menu.setMenuname(rs.getString("menuname"));
				menu.setUrl(rs.getString("url"));
				menu.setLevel(rs.getInt("level"));
				// 将权限对象存进权限集合
				menuList.add(menu);
			}
		}catch(SQLException e){
			throw new MenuException("查找下一级菜单集合失败",e);
		}finally{
			DbUtil.close(rs, pstmt, conn);
		}
		return menuList;
	}
	
	
}
