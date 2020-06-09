package com.gxuwz.medical.domain.medical;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gxuwz.medical.database.DbUtil;

/**
 * 基础数据类
 * @ClassName: S201
 * @author SunYi
 * @Date 2019年4月24日下午6:12:01
 */
public class S201 {
	
	private int id;
	// 数据编码
	private String itemcode;
	// 数据名称
	private String itemname;
	// 数据类型
	private String type;
	
	
	/*  ***********************************************实体类***********************************************  */
	
	public S201(){
		
	}
	// 获取指定编码和类型的基础数据
	public S201(String itemcode, String type)throws Exception{
		this.itemcode = itemcode;
		this.type = type;
		if(itemcode ==null || type==null){
			throw new IllegalStateException("parameter can't not be null");
		}
		load();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getItemcode() {
		return itemcode;
	}
	public void setItemcode(String itemcode) {
		this.itemcode = itemcode;
	}
	public String getItemname() {
		return itemname;
	}
	public void setItemname(String itemname) {
		this.itemname = itemname;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	/*  ***********************************************方法体***********************************************  */
	
	/**
	 * 获取指定数据编号和类型的基础数据
	 * @author SunYi
	 * @Date 2019年4月24日下午6:16:33
	 * @return void
	 */
    public void load() throws SQLException {
    	Connection conn=null;
    	PreparedStatement pstmt=null;
    	ResultSet rs =null;
    	try{
    		String sql = "select * from s201 where itemcode = ? and type = ? ";
    		conn=DbUtil.getConn();
    		pstmt=conn.prepareStatement(sql);
    		pstmt.setString(1, this.itemcode);
    		pstmt.setString(2, this.type);
    		rs=pstmt.executeQuery();
    		if(rs.next()){
    			this.id = rs.getInt("id");
    			this.itemcode = rs.getString("itemcode");
    			this.itemname = rs.getString("itemname");
    		}
    	}catch(SQLException e){
    		e.printStackTrace();
    		throw e;
    	}
    }

}
