package com.gxuwz.medical.database;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 数据库访问操作管理类
 * @ClassName: DbUtil
 * @author SunYi
 * @Date 2019年4月3日上午9:52:38
 */
public class DbUtil {
	private static final Logger LOG = LogManager.getLogger(DbUtil.class);
	//声明全局变量记录jdbc参数
	private static String driver;
	private static String url;
	private static String username;
	private static String password;
	//使用静态代码块，在类加载时即完成对属性文件的读取
	static{
		//动态获取属性配置文件的流对象
		InputStream in=DbUtil.class.getResourceAsStream("/jdbc.properties");
		//创建Properties对象
		Properties p=new Properties();
		//加载
		try {
			p.load(in);//会将属性配置文件的所有数据存储到Properties对象中
			//将读取的jdbc参数赋值给全局变量
			driver=p.getProperty("driver");
			url=p.getProperty("url");
			username=p.getProperty("username");
			password=p.getProperty("password");
			//加载驱动
			Class.forName(driver);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
		/**
		 * 获取数据库连接
		 * @author SunYi
		 * @Date 2019年4月3日上午9:53:39
		 * @return Connection
		 */
	  public static Connection getConn(){
		  Connection conn = null;
		  try{
			  // 反射加载驱动
			  Class.forName(driver);
			  // 获取数据库连接
			  conn = DriverManager.getConnection(url, username, password);
		  }catch (Exception e) {
			 e.printStackTrace();
		}
		  return conn;
	  }
	  
	  /**
	   * 数据库增删改操作
	   * @author SunYi
	   * @Date 2019年4月3日上午9:55:14
	   * @return void
	   */
	  public void executeUpdate(String sql)throws SQLException{
		  Connection conn = null;
		  Statement stmt = null;
		  try{
			  conn = getConn();
			  stmt = conn.createStatement();
			  int c = stmt.executeUpdate(sql);
		  }catch (SQLException e) {
			throw new SQLException("执行SQL更新失败:"+e.getMessage(),e);
		}finally{
			close(stmt, conn);
		}
	  }
	  
	  /**
	   * 数据库查询操作
	   * @author SunYi
	   * @Date 2019年4月3日上午9:56:16
	   * @return ResultSet
	   */
	  public ResultSet executeQuery(Statement stmt,String sql)throws SQLException{
		  ResultSet rs=null;
		  try{
			  rs=stmt.executeQuery(sql);
			  return rs;
		  }catch (SQLException e) {
			throw new SQLException("执行SQL查询失败"+e.getMessage(),e);
		}
	  }
	  
	  /**
	   * 关闭所有资源
	   * @author SunYi
	   * @Date 2019年4月3日上午9:56:55
	   * @return void
	   */
	  public static void close(ResultSet rs,Statement stmt,Connection conn){
		  try{
			  if(rs!=null){
				  rs.close();
				}
			   if(stmt!=null){
					stmt.close();
				}
				if(conn!=null){
					conn.close();
				}
		  }catch (SQLException e) {
			  e.printStackTrace();
		  }
		  
	  }
	  
	  /**
	   * 关闭statement对象和连接
	   * @author SunYi
	   * @Date 2019年4月3日上午9:57:10
	   * @return void
	   */
	  public static void close(Statement stmt,Connection conn){
		  close(null, stmt, conn);
	  }
	  
	  /**
	   * 关闭连接
	   * @author SunYi
	   * @Date 2019年4月3日上午9:57:48
	   * @return void
	   */
	  public static void close(Connection conn){
		  close(null, null, conn);
	  }
	  
	  /**
	   * 关闭statement对象
	   * @author SunYi
	   * @Date 2019年4月3日上午9:57:57
	   * @return void
	   */
	  public static void close(Statement stmt){
		  close(null, stmt, null);
	  }

}
