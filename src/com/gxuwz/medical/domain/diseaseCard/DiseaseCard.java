package com.gxuwz.medical.domain.diseaseCard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.gxuwz.medical.database.DbUtil;
import com.gxuwz.medical.domain.disease.Disease;
import com.gxuwz.medical.domain.person.Person;
import com.gxuwz.medical.exception.DiseaseException;

/**
 * 慢性病证管理控制模块
 * @ClassName: DiseaseCard
 * @author SunYi
 * @Date 2019年5月30日上午11:17:25
 */
/**
* @Description: TODO
* @Author 莫东林
* @date 2019年6月8日下午12:10:45
*/
public class DiseaseCard {
	
	// 慢性病证号
	private String diseaseCard;
	// 农合证号
	private String nongheCard;
	// 身份证号
	private String idCard;
	// 疾病编码
	private String diseaseCode;
	// 证明附件
	private String attachment;
	// 开始时间
	private Date startTime;
	// 结束时间
	private Date endTime;
	
	// 个人档案对象
	private Person person;
	// 疾病对象
	private Disease disease;
	
	
	/*  ***********************************************实体类***********************************************  */
	public DiseaseCard() {
		super();
	}
	// 获取指定农合证号和慢性病编码的慢性病证信息
	public DiseaseCard(String nongheCard, String diseaseCode) throws Exception {
		this.nongheCard = nongheCard;
		this.diseaseCode = diseaseCode;
		getByNongheCard();
	}
	public DiseaseCard(String diseaseCard, String nongheCard, String idCard, String diseaseCode, Date startTime,
			Date endTime) {
		super();
		this.diseaseCard = diseaseCard;
		this.nongheCard = nongheCard;
		this.idCard = idCard;
		this.diseaseCode = diseaseCode;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	public DiseaseCard(String diseaseCard, String nongheCard, String idCard, String diseaseCode, String attachment,
			Date startTime, Date endTime) {
		super();
		this.diseaseCard = diseaseCard;
		this.nongheCard = nongheCard;
		this.idCard = idCard;
		this.diseaseCode = diseaseCode;
		this.attachment = attachment;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	public String getDiseaseCard() {
		return diseaseCard;
	}
	public void setDiseaseCard(String diseaseCard) {
		this.diseaseCard = diseaseCard;
	}
	public String getNongheCard() {
		return nongheCard;
	}
	public void setNongheCard(String nongheCard) {
		this.nongheCard = nongheCard;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getDiseaseCode() {
		return diseaseCode;
	}
	public void setDiseaseCode(String diseaseCode) {
		this.diseaseCode = diseaseCode;
	}
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public Disease getDisease() {
		return disease;
	}
	public void setDisease(Disease disease) {
		this.disease = disease;
	}
	@Override
	public String toString() {
		return "diseaseCard [diseaseCard=" + diseaseCard + ", nongheCard=" + nongheCard + ", idCard=" + idCard
				+ ", diseaseCode=" + diseaseCode + ", attachment=" + attachment + ", startTime=" + startTime
				+ ", endTime=" + endTime + "]";
	}
	
	/*  ***********************************************方法体***********************************************  */
	
	/**
	 * 获取指定农合卡号的慢性病证信息
	 * @author SunYi
	 * @Date 2019年5月30日上午11:03:30
	 * @return void
	 */
	private void getByNongheCard() throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DbUtil.getConn();
			String sql = "select * from t_disease_card where nongheCard = ? and diseaseCode = ? ";
			pstmt = conn.prepareStatement(sql);
			int index = 1;
			pstmt.setString(index++, this.nongheCard);
			pstmt.setString(index++, this.diseaseCode);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				this.diseaseCard = rs.getString("diseaseCard");
				this.nongheCard = rs.getString("nongheCard");
				this.idCard = rs.getString("idCard");
				this.diseaseCode = rs.getString("diseaseCode");
				this.attachment = rs.getString("attachment");
				this.startTime = rs.getDate("startTime");
				this.endTime = rs.getDate("endTime");
			}
		}catch (SQLException e) {
			throw new SQLException("DiseaseCard with id " + this.diseaseCard + " could not be loaded from the database.", e);
		}finally {
			DbUtil.close(rs, pstmt, conn);
		}
	}
	
	/**
	 * 预添加慢性病证
	 * @author SunYi
	 * @Date 2019年6月6日下午2:26:05
	 * @return void
	 */
	public void toAdd(String nongheCard, String idCard, String diseaseCode, Date startTime, Date endTime, String path) throws Exception{
		Connection conn = null;
		try {
			this.diseaseCard = idCard + diseaseCode;
			this.nongheCard = nongheCard;
			this.idCard = idCard;
			this.diseaseCode = diseaseCode;
			this.attachment = path;
			this.startTime = startTime;
			this.endTime = endTime;
			conn = DbUtil.getConn();
			// 1:开启手动提交事务
			conn.setAutoCommit(false);
			// 2:执行添加方法
			add(conn);
			// 3:提交事务
			conn.commit();
		}catch (SQLException e) {
			// 事务回滚
			if(conn != null) {
				conn.rollback();
			}
			throw e;
		}finally {
			DbUtil.close(conn);
		}
	}
	
	/**
	 * 添加慢性病到数据库
	 * @author SunYi
	 * @Date 2019年4月12日下午3:16:52
	 * @return void
	 */
	private void add(Connection conn) throws DiseaseException{
		PreparedStatement pstmt = null;
		try {
			StringBuffer sql = new StringBuffer("insert into t_disease_card(diseaseCard, nongheCard, idCard, diseaseCode, attachment, startTime, endTime) ");
			sql.append("values(?,?,?,?,?,?,?)");
			pstmt = conn.prepareStatement(sql.toString());
			int index = 1;
			pstmt.setString(index++, this.diseaseCard);
			pstmt.setString(index++, this.nongheCard);
			pstmt.setString(index++, this.idCard);
			pstmt.setString(index++, this.diseaseCode);
			pstmt.setString(index++, this.attachment);
			pstmt.setDate(index++, new java.sql.Date(this.startTime.getTime()));
			pstmt.setDate(index++, new java.sql.Date(this.endTime.getTime()));
			pstmt.executeUpdate();
		}catch (SQLException e) {
			throw new DiseaseException("DiseaseCard with id " + this.nongheCard + " could not be add to the database.", e);
		}finally {
			DbUtil.close(pstmt);
		}
	}
	
}
