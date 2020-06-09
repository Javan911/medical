package com.gxuwz.medical.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gxuwz.medical.database.DbUtil;
import com.gxuwz.medical.domain.menu.Menu;
import com.gxuwz.medical.domain.vo.Page;

/**
 * 权限数据访问层
 * @ClassName: MenuDao
 * @author SunYi
 * @Date 2019年4月3日下午10:33:19
 */
public class MenuDao extends BaseDao {

	/**
	 * 实现ResultSet结果集转换为Menu类型实例
	 * @author SunYi
	 * @Date 2019年4月3日下午10:33:34
	 */
	@Override
	protected Menu handle(ResultSet rs) throws SQLException {
		// 实例化权限对象
		Menu menu=new Menu();
		// 取出ResultSet的值,存进Menu对象
		menu.setMenuid(rs.getString("menuid"));
		menu.setMenupid(rs.getString("menupid"));
		menu.setMenuname(rs.getString("menuname"));
		menu.setUrl(rs.getString("url"));
		menu.setLevel(rs.getInt("level"));
		// 返回权限对象
		return menu;
	}
	
	 /**
	  * 分页查询权限
	  * @author SunYi
	  * @Date 2019年4月3日下午10:34:40
	  * @return Page
	  */
	public Page getMenuByPage(String sql,Object[]params,int pageNo,int size) throws SQLException{
		Connection conn =null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		Page page=null;
		// 实例化权限集合
		List<Menu> menuList = new ArrayList<Menu>();
		try{
			// 计算得到start
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
				// 调用handle方法将查询得到的ResultSet转换成Menu对象
				Menu m = handle(rs);
				// 将Menu对象存进集合
				menuList.add(m);
			}
			// 统计SQL对应记录数
			int total = getCount(sql, params);
			// 构造分页
			page = new Page(total, pageNo, size, menuList);
			// 返回分页对象
			return page;
		}catch(SQLException e){
			throw new SQLException("执行SQL["+sql+"]失败",e);
		}
	}
		

}
