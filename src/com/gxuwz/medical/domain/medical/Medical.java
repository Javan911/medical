package com.gxuwz.medical.domain.medical;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import com.gxuwz.medical.database.DbUtil;


/**
 * 医疗机构管理类
 * @ClassName: Medical
 * @author SunYi
 * @Date 2019年4月24日下午5:32:07
 */
public class Medical {
	
	// 机构编码
	private String jgbm;
	// 组织机构编码
	private String zzjgbm;
	// 机构名称
	private String jgmc;
	// 地区编码
	private String dqbm;
	// 行政区域编码
	private String areacode;
	// 隶属关系
	private String lsgx;
	// 机构级别
	private String jgjb;
	// 申报定点类型
	private String sbddlx;
	// 批准定点类型
	private String pzddlx;
	// 所属经济类型
	private String ssjjlx;
	// 卫生机构大类
	private String wsjgdl;
	// 卫生机构小类
	private String wsjgxl;
	// 主管单位
	private String zgdw;
	// 开业时间
	private java.util.Date kysj;
	// 法人代表
	private String frdb;
	// 注册资金
	private double zczj;
	
	
	
	
	/*  ***********************************************实体类***********************************************  */
	
	public Medical(){
		
	}
	// 获取指定机构编码的医疗机构
	public Medical(String jgbm) throws Exception{
		this.jgbm = jgbm;
		getMedicalById();
	}
	public Medical(String jgbm, String zzjgbm, String jgmc, String dqbm,
			String areacode, String lsgx, String jgjb, String sbddlx,
			String pzddlx, String ssjjlx, String wsjgdl, String wsjgxl,
			String zgdw, Date kysj, String frdb, double zczj) {
		super();
		this.jgbm = jgbm;
		this.zzjgbm = zzjgbm;
		this.jgmc = jgmc;
		this.dqbm = dqbm;
		this.areacode = areacode;
		this.lsgx = lsgx;
		this.jgjb = jgjb;
		this.sbddlx = sbddlx;
		this.pzddlx = pzddlx;
		this.ssjjlx = ssjjlx;
		this.wsjgdl = wsjgdl;
		this.wsjgxl = wsjgxl;
		this.zgdw = zgdw;
		this.kysj = kysj;
		this.frdb = frdb;
		this.zczj = zczj;
	}
	public String getJgbm() {
		return jgbm;
	}
	public void setJgbm(String jgbm) {
		this.jgbm = jgbm;
	}
	public String getZzjgbm() {
		return zzjgbm;
	}
	public void setZzjgbm(String zzjgbm) {
		this.zzjgbm = zzjgbm;
	}
	public String getJgmc() {
		return jgmc;
	}
	public void setJgmc(String jgmc) {
		this.jgmc = jgmc;
	}
	public String getDqbm() {
		return dqbm;
	}
	public void setDqbm(String dqbm) {
		this.dqbm = dqbm;
	}
	public String getAreacode() {
		return areacode;
	}
	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}
	public String getLsgx() {
		return lsgx;
	}
	public void setLsgx(String lsgx) {
		this.lsgx = lsgx;
	}
	public String getJgjb() {
		return jgjb;
	}
	public void setJgjb(String jgjb) {
		this.jgjb = jgjb;
	}
	public String getSbddlx() {
		return sbddlx;
	}
	public void setSbddlx(String sbddlx) {
		this.sbddlx = sbddlx;
	}
	public String getPzddlx() {
		return pzddlx;
	}
	public void setPzddlx(String pzddlx) {
		this.pzddlx = pzddlx;
	}
	public String getSsjjlx() {
		return ssjjlx;
	}
	public void setSsjjlx(String ssjjlx) {
		this.ssjjlx = ssjjlx;
	}
	public String getWsjgdl() {
		return wsjgdl;
	}
	public void setWsjgdl(String wsjgdl) {
		this.wsjgdl = wsjgdl;
	}
	public String getWsjgxl() {
		return wsjgxl;
	}
	public void setWsjgxl(String wsjgxl) {
		this.wsjgxl = wsjgxl;
	}
	public String getZgdw() {
		return zgdw;
	}
	public void setZgdw(String zgdw) {
		this.zgdw = zgdw;
	}
	public java.util.Date getKysj() {
		return kysj;
	}
	public void setKysj(java.util.Date kysj) {
		this.kysj = kysj;
	}
	public String getFrdb() {
		return frdb;
	}
	public void setFrdb(String frdb) {
		this.frdb = frdb;
	}
	public double getZczj() {
		return zczj;
	}
	public void setZczj(double zczj) {
		this.zczj = zczj;
	}
	@Override
	public String toString() {
		return "Medical [jgbm=" + jgbm + ", zzjgbm=" + zzjgbm + ", jgmc=" + jgmc + ", dqbm=" + dqbm + ", areacode="
				+ areacode + ", lsgx=" + lsgx + ", jgjb=" + jgjb + ", sbddlx=" + sbddlx + ", pzddlx=" + pzddlx
				+ ", ssjjlx=" + ssjjlx + ", wsjgdl=" + wsjgdl + ", wsjgxl=" + wsjgxl + ", zgdw=" + zgdw + ", kysj="
				+ kysj + ", frdb=" + frdb + ", zczj=" + zczj + "]";
	}
	
	
	
	/*  ***********************************************方法体***********************************************  */
	
	
	/**
	 * 获取指定编码以及类型的S201表对应名称
	 * @author SunYi
	 * @Date 2019年4月24日下午5:34:19
	 * @return String
	 */
	public String getItemName(String itemcode, String type) {
	    String itemname = "";
		if(this.lsgx != null){
			try{
			    S201 s201 = new S201(itemcode, type);
			    itemname = s201.getItemname();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return itemname;
	}
	
	/**
	 * 获取指定机构编码的医疗机构
	 * @author SunYi
	 * @Date 2019年4月25日下午11:11:57
	 * @return void
	 */
	private void getMedicalById() throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			conn = DbUtil.getConn();
			pstmt = conn.prepareStatement("select * from t_medical where jgbm=? ");
			int index = 1;
			// 把页面传过来的treeID的值赋给占位符
			pstmt.setString(index, this.jgbm);
			// 执行查询操作
			rs = pstmt.executeQuery();
			if(rs.next()){
				this.jgbm = rs.getString("jgbm");
				this.zzjgbm = rs.getString("zzjgbm");
				this.jgmc = rs.getString("jgmc");
				this.dqbm = rs.getString("dqbm");
				this.areacode = rs.getString("areacode");
				this.lsgx = rs.getString("lsgx");
				this.jgjb = rs.getString("jgjb");
				this.sbddlx = rs.getString("sbddlx");
				this.pzddlx = rs.getString("pzddlx");
				this.ssjjlx = rs.getString("ssjjlx");
				this.wsjgdl = rs.getString("wsjgdl");
				this.wsjgxl = rs.getString("wsjgxl");
				this.zgdw = rs.getString("zgdw");
				this.kysj = rs.getDate("kysj");
				this.frdb = rs.getString("frdb");
				this.zczj = rs.getDouble("zczj");
			}
		}catch(SQLException e){
			throw new SQLException("Medical with id " + this.jgbm + " could not be loaded from the database.", e);
		}finally{
			DbUtil.close(rs, pstmt, conn);
		}
	}
	
	/**
	 * 预添加医疗机构
	 * @author SunYi
	 * @Date 2019年4月24日下午5:33:15
	 * @return void
	 */
	public void toAddMedical() throws SQLException {
		Connection conn =null;
		try{
			 conn=DbUtil.getConn();
			// 1:开启手动提交事务
			conn.setAutoCommit(false);
			// 2:执行添加方法
			addMedical(conn);
			// 3:提交事务
			conn.commit();
		}catch(SQLException e){
			// 事务回滚
			if(conn != null) {
				conn.rollback();
			}
			e.printStackTrace();
			throw e;
		}finally{
			DbUtil.close(conn);
		}
	}
	
	/**
	 * 添加医疗机构到数据库
	 * @author SunYi
	 * @Date 2019年4月24日下午5:41:19
	 * @return void
	 */
	private void addMedical(Connection conn) throws SQLException {
		PreparedStatement pstmt = null;
		try{
			StringBuffer sql = new StringBuffer();
			sql.append("insert into t_medical( jgbm, zzjgbm, jgmc, dqbm, areacode, lsgx, jgjb, "
					+ "sbddlx, pzddlx, ssjjlx, wsjgdl, wsjgxl, zgdw, kysj , frdb,zczj)");
			sql.append("values");
			sql.append("(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			pstmt=conn.prepareStatement(sql.toString());
			// 设置动态参数的值
			int index = 1;
			pstmt.setString(index++, this.jgbm);
			pstmt.setString(index++, this.zzjgbm);
			pstmt.setString(index++, this.jgmc);
			pstmt.setString(index++, this.dqbm);
			pstmt.setString(index++, this.areacode);
			pstmt.setString(index++, this.lsgx);
			pstmt.setString(index++, this.jgjb);
			pstmt.setString(index++, this.sbddlx);
			pstmt.setString(index++, this.pzddlx);
			pstmt.setString(index++, this.ssjjlx);
			pstmt.setString(index++, this.wsjgdl);
			pstmt.setString(index++, this.wsjgxl);
			pstmt.setString(index++, this.zgdw);
			pstmt.setDate(index++, new java.sql.Date(this.kysj.getTime()));
			pstmt.setString(index++, this.frdb);
			pstmt.setDouble(index++, this.zczj);
			int count=pstmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
			throw e;
		}finally{
			DbUtil.close(pstmt);
		}
		
	}
	
	/**
	 * 预删除医疗机构
	 * @author SunYi
	 * @Date 2019年4月25日上午11:41:47
	 * @return void
	 * @throws SQLException 
	 */
	public boolean toDeleteMedical(String jgbm) throws SQLException {
		this.jgbm = jgbm;
		boolean flag = false;
		Connection conn = null;
		try{
			conn = DbUtil.getConn();
			// 1:开启手动提交事务
			conn.setAutoCommit(false);
			// 2:从数据库中删除指定编号的行政区域
			deleteMedical(conn);
			flag = true;
			// 3:提交事务
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
		return flag;
	}
	
	/**
	 * 从数据库中删除医疗机构
	 * @author SunYi
	 * @Date 2019年4月25日下午5:04:42
	 * @return void
	 */
	private void deleteMedical(Connection conn) throws SQLException {
		PreparedStatement pstmt=null;
		try{
			// 创建SQL语句
			StringBuffer sqlBuff = new StringBuffer("delete from t_medical where jgbm=? ");
			pstmt=conn.prepareStatement(sqlBuff.toString());
			// 把当前对象的值赋给占位符
			pstmt.setString(1, this.jgbm);
			// 执行删除操作
			pstmt.executeUpdate(); 
		}catch(SQLException e){
			throw e;
		}finally{
			DbUtil.close(pstmt);
		} 
	}
	
	/**
	 * 预更新医疗机构
	 * @author SunYi
	 * @Date 2019年4月25日下午11:29:20
	 * @return boolean
	 */
	public boolean toUpdateMedical() throws SQLException {
		boolean flag = false;
		Connection conn = null;
		try{
			conn = DbUtil.getConn();
			// 1:开启手动提交事务
			conn.setAutoCommit(false);
			// 2:从数据库中删除指定编号的行政区域
			updateMedical(conn);
			flag = true;
			// 3:提交事务
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
		return flag;
	}
	
	/**
	 * 更新医疗机构信息到数据库
	 * @author SunYi
	 * @Date 2019年4月25日下午11:30:46
	 * @return void
	 */
	private void updateMedical(Connection conn) throws SQLException {
		PreparedStatement pstmt=null;
		try{
			// 创建SQL语句
			StringBuffer sql = new StringBuffer();
			sql.append("update t_medical set zzjgbm=?, jgmc=?, dqbm=?, areacode=?, lsgx=?, jgjb=?, "
					+ "sbddlx=?, pzddlx=?, ssjjlx=?, wsjgdl=?, wsjgxl=?, zgdw=?, kysj=? , frdb=?, zczj=? ");
			sql.append(" where jgbm=? ");
			pstmt=conn.prepareStatement(sql.toString());
			// 把当前对象的值赋给占位符
			int index = 1;
			pstmt.setString(index++, this.zzjgbm);
			pstmt.setString(index++, this.jgmc);
			pstmt.setString(index++, this.dqbm);
			pstmt.setString(index++, this.areacode);
			pstmt.setString(index++, this.lsgx);
			pstmt.setString(index++, this.jgjb);
			pstmt.setString(index++, this.sbddlx);
			pstmt.setString(index++, this.pzddlx);
			pstmt.setString(index++, this.ssjjlx);
			pstmt.setString(index++, this.wsjgdl);
			pstmt.setString(index++, this.wsjgxl);
			pstmt.setString(index++, this.zgdw);
			pstmt.setDate(index++, new java.sql.Date(this.kysj.getTime()));
			pstmt.setString(index++, this.frdb);
			pstmt.setDouble(index++, this.zczj);
			pstmt.setString(index++, this.jgbm);
			// 执行删除操作
			pstmt.executeUpdate(); 
		}catch(SQLException e){
			throw e;
		}finally{
			DbUtil.close(pstmt);
		} 
	}
	
}
