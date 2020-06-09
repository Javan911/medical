package com.gxuwz.medical.domain.area;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gxuwz.medical.database.DbUtil;
import com.gxuwz.medical.exception.AreaNotFoundException;

/**
 * 行政区域管理模块
 * @ClassName: Area
 * @author SunYi
 * @Date 2019年4月3日下午11:16:17
 */
public class Area  {
	
	// 行政区域编码:县、镇、村、组
	private String areacode;
	// 上一级行政区域编码
	private String areapcode;
	// 上一级行政区域对象
	private Area parent;
	// 行政区域名称
	private String areaname;
	// 行政区域级别:1表示县级,2表示镇级,3表示村,4表示组
	private int grade;
	
	/*  ***********************************************实体类***********************************************  */
	
	
	public Area(){
		
	}
	// 调用构造方法获取指定编号的行政区域
	public Area(String treeID) throws AreaNotFoundException {
		this.areacode = treeID;
		getAreaById(treeID);
	}
	public String getAreapcode() {
		return areapcode;
	}
	public void setAreapcode(String areapcode) {
		this.areapcode = areapcode;
	}
	public Area getParent() {
		return parent;
	}
	public void setParent(Area parent) {
		this.parent = parent;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public String getAreacode() {
		return areacode;
	}
	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}
	public String getAreaname() {
		return areaname;
	}
	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}
	
	
	/*  ***********************************************方法体***********************************************  */
	
	/**
	 * 获取行政区域集合
	 * @author SunYi
	 * @Date 2019年4月3日下午11:21:18
	 * @return List<Area>
	 */
	public List<Area> getAreaList(){
		List<Area> areaList = new ArrayList<Area>();
		return areaList;
	}
	
	 /**
	  * 预添加子一级行政区域
	  * @author SunYi
	  * @Date 2019年4月3日下午11:22:36
	  * @return void
	  */
	public void toAddArea(String areacode,String areaname)throws Exception{
		Connection conn = null;
		try{
			// 加载父节点Id的对象
			this.parent = new Area(areacode);
			// 将传过来的值给当前对象赋值
			this.areaname = areaname;
			this.grade = this.parent.getGrade()+1;
			// 1:自动生成行政区域编号
			this.areacode = createAreaid();
			conn = DbUtil.getConn();
			// 2:开启手动提交事务
			conn.setAutoCommit(false);
			// 3:保存行政区域到数据库
			addArea(conn);
			// 4:提交事务
			conn.commit();
		}catch (Exception e) {
			// 事务回滚
			if(conn!=null){
				conn.rollback();
			}
			throw e;
		}finally{
			DbUtil.close(conn);
		}
	}
	
	 /**
	  * 预更新行政区域信息
	  * @author SunYi
	  * @Date 2019年4月5日下午4:28:49
	  * @return void
	  */
	public void toUpdateArea(String areacode, String areaname)throws Exception{
		Connection conn = null;
		try{
			// 1:将传过来的值给当前对象赋值
			this.areacode = areacode;
			this.areaname = areaname;
			conn = DbUtil.getConn();
			// 2:开启手动提交事务
			conn.setAutoCommit(false);
			// 3:更新行政区域信息到数据库
			updateArea(conn);
			// 4:提交事务
			conn.commit();
		}catch (Exception e) {
			if(conn!=null){
				// 事务回滚
				conn.rollback();
			}
			throw e;
		}finally{
			DbUtil.close(conn);
		}
	}
	
	 /**
	  * 预删除行政区域
	  * @author SunYi
	  * @Date 2019年4月5日下午5:31:08
	  * @return void
	  */
	public void toDeleteArea(String areacode)throws Exception{
		Connection conn = null;
		try{
			// 1:将传过来的值给当前对象赋值
			this.areacode = areacode;
			conn = DbUtil.getConn();
			// 2:开启手动提交事务
			conn.setAutoCommit(false);
			// 3:从数据库中删除指定编号的行政区域
			deleteArea(conn);
			// 5:提交事务
			conn.commit();
		}catch (Exception e) {
			if(conn!=null){
				// 事务回滚
				conn.rollback();
			}
			throw e;
		}finally{
			DbUtil.close(conn);
		}
	}
	
	 /**
	  * 添加行政区域到数据库
	  * @author SunYi
	  * @Date 2019年4月3日下午11:26:46
	  * @return void
	  */
	private void addArea(Connection conn)throws SQLException{
		  PreparedStatement pstmt=null;
		  try{
			  // 创建SQL语句
			  StringBuffer sqlBuff = new StringBuffer("insert into t_area(areacode,areapcode,areaname,grade)");
			  sqlBuff.append("values(?,?,?,?)");
			  pstmt=conn.prepareStatement(sqlBuff.toString());
			  // 把当前对象的值赋给占位符
			  pstmt.setString(1, this.areacode);
			  pstmt.setString(2, this.areapcode);
			  pstmt.setString(3, this.areaname);
			  pstmt.setInt(4, this.grade);
			  // 执行添加操作
			  pstmt.executeUpdate(); 
		  }catch(SQLException e){
			  throw e;
		  }finally{
			  DbUtil.close(pstmt);
		  } 
	}
	
	 /**
	  * 更新行政区域信息到数据库
	  * @author SunYi
	  * @Date 2019年4月5日下午4:32:11
	  * @return void
	  */
	private void updateArea(Connection conn)throws SQLException{
		  PreparedStatement pstmt=null;
		  try{
			  // 创建SQL语句
			  StringBuffer sqlBuff = new StringBuffer("update t_area t set t.areaname=? where t.areacode=?");
			  pstmt=conn.prepareStatement(sqlBuff.toString());
			  // 把当前对象的值赋给占位符
			  pstmt.setString(1, this.areaname);
			  pstmt.setString(2, this.areacode);
			  // 执行更新操作
			  pstmt.executeUpdate(); 
		  }catch(SQLException e){
			  throw e;
		  }finally{
			  DbUtil.close(pstmt);
		  } 
	}
	
	 /**
	  * 从数据库中删除指定行政区域
	  * @author SunYi
	  * @Date 2019年4月5日下午5:46:13
	  * @return void
	  */
	private void deleteArea(Connection conn)throws SQLException{
		  PreparedStatement pstmt=null;
		  try{
			  // 创建SQL语句
			  StringBuffer sqlBuff = new StringBuffer("delete from t_area where areacode=?");
			  pstmt=conn.prepareStatement(sqlBuff.toString());
			  // 把当前对象的值赋给占位符
			  pstmt.setString(1, this.areacode);
			  // 执行删除操作
			  pstmt.executeUpdate(); 
		  }catch(SQLException e){
			  throw e;
		  }finally{
			  DbUtil.close(pstmt);
		  } 
	}
	
	 /**
	  * 获取指定编号的行政区域信息
	  * @author SunYi
	  * @Date 2019年4月3日下午11:28:01
	  * @return void
	  */
	private void getAreaById(String treeID) throws AreaNotFoundException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			conn = DbUtil.getConn();
			pstmt = conn.prepareStatement("select * from t_area where areacode=?");
			int index = 1;
			// 把页面传过来的treeID的值赋给占位符
			pstmt.setString(index, treeID);
			// 执行查询操作
			rs = pstmt.executeQuery();
			if(rs.next()){
				// 把查询到的结果赋给当前对象
				this.areacode = rs.getString("areacode");
				this.areapcode = rs.getString("areapcode");
				this.areaname = rs.getString("areaname");
				this.grade = rs.getByte("grade");
			}
		}catch(SQLException e){
			throw new AreaNotFoundException("Area with id " + areacode + " could not be loaded from the database.", e);
		}finally{
			DbUtil.close(rs, pstmt, conn);
		}
	}
	
	 /**
	  * 判断指定编号的行政区域信息是否存在
	  * @author SunYi
	  * @Date 2019年4月3日下午11:28:01
	  * @return void
	  */
	public boolean hasAreacode(String areacode) throws AreaNotFoundException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			conn = DbUtil.getConn();
			pstmt = conn.prepareStatement("select * from t_area where areacode=? ");
			int index = 1;
			// 把页面传过来的treeID的值赋给占位符
			pstmt.setString(index, areacode);
			// 执行查询操作
			rs = pstmt.executeQuery();
			return rs.next();
		}catch(SQLException e){
			throw new AreaNotFoundException("Area with id " + areacode + " could not be loaded from the database.", e);
		}finally{
			DbUtil.close(rs, pstmt, conn);
		}
	}
	
	 /**
	  * 自动生成行政区域编号
	  * @author SunYi
	  * @Date 2019年4月3日下午11:30:30
	  * @return String
	  */
    private String createAreaid() throws SQLException{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// 获取当前对象的上一级行政区域编号
		String areapcode = this.parent.getAreacode();
		// 上一级行政区域级别+1得到当前行政区域级别
		int grade = this.parent.getGrade() + 1;
		try{
			conn = DbUtil.getConn();
			// 查询同一行政区域级别的最大行政区域编号
			String sql = "select max(areacode) from t_area where areacode like '" +areapcode+ "%' and grade=" + grade ;
			pstmt = conn.prepareStatement(sql);
			// 执行查询操作
			rs = pstmt.executeQuery();
			String maxcode = "";
			Integer number = 1;
			if(rs.next()){
			    maxcode = rs.getString(1);
			}
			if(maxcode!=null){
			  int beginIndex = maxcode.length()-2;
			  String no = maxcode.substring(beginIndex);
			  number = Integer.parseInt(no);
			  ++number;
			  // 使用0补足位数
			  no = String.format("%02d", number);
			  maxcode = this.parent.getAreacode()+no;
			  // 当前对象的上一级行政区域编号
			  this.areapcode = this.parent.getAreacode();
			}else{
				String no = String.format("%02d", number);
				maxcode = this.parent.getAreacode()+no;
				// 当前对象的上一级行政区域编号
				this.areapcode = this.parent.getAreacode();
			}
			return maxcode;
		}catch(SQLException e){
			throw e;
		}finally{
			DbUtil.close(rs, pstmt, conn);
		}
	}
    
     /**
      * 获取当前行政区域下的子行政区域集合
      * @author SunYi
      * @Date 2019年4月3日下午11:37:24
      * @return List<Area>
      */
	public List<Area> getAreaChildren() throws AreaNotFoundException{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Area area = null;
		List<Area> areaList = new ArrayList<Area>();
		try{
			conn = DbUtil.getConn();
			// 查询所有上一级编号等于当前对象编号的区域
			pstmt = conn.prepareStatement("select * from t_area where areapcode ="+this.areacode+" and grade="+(this.grade+1));
			// 执行查询操作
			rs = pstmt.executeQuery();
			while(rs.next()){
				// 实例化行政区域对象
				area = new Area();
				// 将查询结果赋值给新的行政区域对象
				area.setAreacode(rs.getString("areacode"));
				area.setAreapcode(rs.getString("areapcode"));
				area.setAreaname(rs.getString("areaname"));
				area.setGrade(rs.getInt("grade"));
				// 将行政区域对象存进行政区域集合
				areaList.add(area);
			}
		}catch(SQLException e){
			throw new AreaNotFoundException("查找下一级行政区域集合失败",e);
		}finally{
			DbUtil.close(rs, pstmt, conn);
		}
		return areaList;
	}
	
	 /**
	  * 校验行政区域名是否存在
	  * @author SunYi
	  * @Date 2019年4月5日下午5:09:27
	  * @return boolean
	  */
	public boolean checkAreaname(String areaname) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			conn = DbUtil.getConn();
			pstmt = conn.prepareStatement("select * from t_area where areaname=?");
			int index = 1;
			pstmt.setString(index, areaname);
			// 执行查询操作
			rs = pstmt.executeQuery();
			return rs.next();
		}catch(SQLException e){
			throw e;
		}finally{
			DbUtil.close(rs, pstmt, conn);
		}
	}
	
	
}
