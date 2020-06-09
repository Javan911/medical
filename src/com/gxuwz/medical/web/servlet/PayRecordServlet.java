package com.gxuwz.medical.web.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.gxuwz.medical.dao.FamilyDao;
import com.gxuwz.medical.dao.PayRecordDao;
import com.gxuwz.medical.domain.payRecord.PayRecord;
import com.gxuwz.medical.domain.payStandard.PayStandard;
import com.gxuwz.medical.domain.person.Person;
import com.gxuwz.medical.domain.user.User;

/**
 * 参合缴费记录管理控制层
 * @ClassName: PayRecordServlet
 * @author SunYi
 * @Date 2019年5月10日下午10:52:13
 */
public class PayRecordServlet extends BaseServlet {

	private static final long serialVersionUID = 8361579424381174630L;
	
	// 覆写doGet方法
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 把请求转给doPost方法处理
		this.doPost(request, response);
	}

	// 覆写doPost方法
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 获取页面表单提交的方法名称
		String m = req.getParameter("m");
		// 根据页面表单提交的方法名称请求转发到相应的页面
		if("list".equals(m)){
			process(req, resp, "/page/payRecord/payRecord_list.jsp");
		}else if("input".equals(m)){
			process(req, resp, "/page/family/family_add.jsp");
		}else if("calAccount".equals(m)){
			calAccount(req, resp);
		} else if("check".equals(m)){
			check(req, resp);
		} else if("add".equals(m)){
			add(req, resp);
			process(req, resp, "/page/family/payRecord_list.jsp");
		}else if("Excel".equals(m)){
			createExcel(req, resp);
		} else if("statistics".equals(m)){
			process(req, resp, "/page/payRecord/payRecord_statistics.jsp");
		} else if("detail".equals(m)){
			process(req, resp, "/page/payRecord/payRecord_add.jsp");
		} else if("details".equals(m)){
			process(req, resp, "/page/payRecord/payRecord_details.jsp");
		} else if("del".equals(m)){
			del(req, resp);
		} else {
			error(req, resp);
		}
	}
	
	
	/**
	 * 计算缴费总金额
	 * @author SunYi
	 * @Date 2019年5月11日上午9:14:37
	 * @return void
	 */
	private void calAccount(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		// 1:接收表单传过来的缴费人数
		int payNum = Integer.parseInt(req.getParameter("payNum"));
		try {
			// 2:获取当前年度的缴费标准金额
			Calendar date = Calendar.getInstance();
			String annual = String.valueOf(date.get(Calendar.YEAR));
			PayStandard psd = new PayStandard(annual);
			double account = psd.getAccount();
			// 3:执行计算方法
			PayRecordDao prd = new PayRecordDao();
			double amount = prd.calAccount(payNum, account);
			// 4:将计算得到的缴费总金额返回给页面
			resp.getWriter().write(""+amount);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 判断当前时间是否在缴费标准设置的时间段内
	 * @author SunYi
	 * @Date 2019年5月13日下午10:51:46
	 * @return void
	 */
	private void check(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		try {
			boolean flag = false;
			// 获取系统当前年度以及当前时间
			Calendar date = Calendar.getInstance();
			String annual = String.valueOf(date.get(Calendar.YEAR));
			Date payTime = date.getTime();
			// 获取当前年度的缴费标准规定时间范围
			PayStandard prd = new PayStandard(annual);
			
			// 设置当前时间
	        date.setTime(payTime);
	        // 设置缴费开始时间
	        Calendar begin = Calendar.getInstance();
	        begin.setTime(prd.getStartTime());
	        // 设置缴费结束时间
	        Calendar end = Calendar.getInstance();
	        end.setTime(prd.getEndTime());
	        
	        // 判断当前时间是否等于缴费标准规定时间
	        if(payTime == prd.getStartTime() || payTime == prd.getEndTime()) {
				flag = true;
			}
	        // 判断当前时间是否在缴费标准规定时间范围内
	        if (date.after(begin) && date.before(end)) {
	            flag = true;
	        } 
			if(flag) {
				resp.getWriter().write("true");
			}else {
				resp.getWriter().write("false");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 添加参合缴费记录
	 * @author SunYi
	 * @Date 2019年5月12日下午2:20:11
	 * @return void
	 */
	private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		// 1:接收表单传过来的值
		String familyCode = req.getParameter("familyCode");
		String houseHolder = req.getParameter("houseHolder");
		// 个人户内编号数组
		String[] houseNum = req.getParameterValues("ids");
		// 参合发票号
		String invoiceNum = req.getParameter("invoiceNum");
		try {
			// 家庭缴费总金额
			double amount = Double.parseDouble(req.getParameter("payAccount"));
			// 单个人缴费金额
			double payAccount = (amount / houseNum.length);
			// 2:获取系统当前年度以及当前时间
			Calendar date = Calendar.getInstance();
			String payAnnual = String.valueOf(date.get(Calendar.YEAR));
			Date payTime = date.getTime();
			// 3:获取当前用户作为操作员
			User user = (User) req.getSession().getAttribute("user");
			String operator = user.getFullname();
			// 遍历个人户内编号，循环执行预添加方法
			for(int i = 0; i < houseNum.length; i++) {
				// 4:根据个人户内编号获取个人姓名
				Person model = new Person(houseNum[i]);
				String name = model.getName();
				// 5:将参合证号设为个人户内编号
				String joinNum = houseNum[i];
				PayRecord prd = new PayRecord(familyCode, houseHolder, houseNum[i], joinNum, invoiceNum, 
						name, payAccount, payAnnual, payTime, operator);
				// 调用预添加方法
				prd.toAddPayRecord();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	 /**
	  * 导出Excel
	  * @author SunYi
	  * @Date 2019年5月13日下午2:05:05
	  * @return void
	 * @throws SQLException 
	  */
	 private void createExcel(HttpServletRequest req, HttpServletResponse response) {
		 
		PayRecordDao dao = new PayRecordDao();
		String sql = "select * from t_pay_record where houseHolder=name order by familyCode ";
		Object[] params = {};
		List<PayRecord> list = new ArrayList<PayRecord>();
		// 家庭总人口数
		int personCount = 0;
		// 家庭当前年度已缴费人数
		int payCount = 0;
		// 缴费总金额
		double amount = 0;
		try {
			list = dao.queryOjects(sql, params);
			
			FamilyDao familyDao = new FamilyDao();
			for(int i = 0; i< list.size(); i++) {
				PayRecord model = list.get(i);
				personCount = familyDao.getFamilySizeById(model.getFamilyCode());
				// 获取家庭当前年度已缴费人数
				payCount = dao.getByFamily(model.getFamilyCode(), model.getPayAnnual());
				// 获取当前年度的缴费标准金额
				PayStandard psd = new PayStandard(model.getPayAnnual());
				double account = psd.getAccount();
				amount = dao.calAccount(payCount, account);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
        // 创建一个Excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建一个工作表
        HSSFSheet sheet = workbook.createSheet("家庭参合缴费统计");
        
        CellRangeAddress region = new CellRangeAddress(0, // first row
                0, // last row
                0, // first column
                8 // last column
        );
        sheet.addMergedRegion(region);
        
        HSSFRow hssfRow = sheet.createRow(0);
        HSSFCell headCell = hssfRow.createCell(0);
        headCell.setCellValue("家庭参合缴费统计信息");
        
        // 设置单元格格式居中
        HSSFCellStyle cellStyle = workbook.createCellStyle();
    	cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        headCell.setCellStyle(cellStyle);
        
        
        // 添加表头行
        hssfRow = sheet.createRow(1);
        // 添加表头内容
        headCell = hssfRow.createCell(0);
        headCell.setCellValue("家庭编号");
        headCell.setCellStyle(cellStyle);

        headCell = hssfRow.createCell(1);
        headCell.setCellValue("户主");
        headCell.setCellStyle(cellStyle);

        headCell = hssfRow.createCell(2);
        headCell.setCellValue("家庭人口数");
        headCell.setCellStyle(cellStyle);
        
        headCell = hssfRow.createCell(3);
        headCell.setCellValue("已缴费人数");
        headCell.setCellStyle(cellStyle);

        headCell = hssfRow.createCell(4);
        headCell.setCellValue("缴费总金额");
        headCell.setCellStyle(cellStyle);

        headCell = hssfRow.createCell(5);
        headCell.setCellValue("参合发票号");
        headCell.setCellStyle(cellStyle);
        
        headCell = hssfRow.createCell(6);
        headCell.setCellValue("缴费年度");
        headCell.setCellStyle(cellStyle);
        
        headCell = hssfRow.createCell(7);
        headCell.setCellValue("缴费时间");
        headCell.setCellStyle(cellStyle);
        
        headCell = hssfRow.createCell(8);
        headCell.setCellValue("操作员");
        headCell.setCellStyle(cellStyle);

        // 添加数据内容
        for (int i = 0; i < list.size(); i++) {
            hssfRow = sheet.createRow((int) i + 2);
            PayRecord  model = list.get(i);

            // 获取缴费的日期
            Date date = model.getPayTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //转换成String类型的时间
            String payTime = sdf.format(date);
            
            // 创建单元格，并设置值
            HSSFCell cell = hssfRow.createCell(0);
            cell.setCellValue(model.getFamilyCode());
            cell.setCellStyle(cellStyle);

            cell = hssfRow.createCell(1);
            cell.setCellValue(model.getHouseHolder());
            cell.setCellStyle(cellStyle);

            cell = hssfRow.createCell(2);
            cell.setCellValue(personCount);
            cell.setCellStyle(cellStyle);
            
            cell = hssfRow.createCell(3);
            cell.setCellValue(payCount);
            cell.setCellStyle(cellStyle);
            
            cell = hssfRow.createCell(4);
            cell.setCellValue(amount);
            cell.setCellStyle(cellStyle);
            
            cell = hssfRow.createCell(5);
            cell.setCellValue(model.getInvoiceNum());
            cell.setCellStyle(cellStyle);

            cell = hssfRow.createCell(6);
            cell.setCellValue(model.getPayAnnual());
            cell.setCellStyle(cellStyle);

            cell = hssfRow.createCell(7);
            cell.setCellValue(payTime);
            cell.setCellStyle(cellStyle);
            
            cell = hssfRow.createCell(8);
            cell.setCellValue(model.getOperator());
            cell.setCellStyle(cellStyle);
            
        }
        // 保存Excel文件
        try {
        	response.setContentType("application/vnd.ms-excel");
        	response.setHeader("Content-disposition", "attachment;filename=payRecord.xls");//附件形式下载，文件名叫duty.xls
        	//OutputStream outputStream = new FileOutputStream("D:/payRecord.xls");//保存到本地（服务器端）
        	OutputStream outputStream = response.getOutputStream();	 //写到客户端       	
            workbook.write(outputStream);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	 /**
	  * 删除家庭缴费记录
	  * @author SunYi
	  * @Date 2019年5月13日下午10:05:29
	  * @return void
	  */
	 private void del(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		// 接收表单传过来的数据
		String familyCode = req.getParameter("familyCode");
		try {
			PayRecord prd = new PayRecord();
			prd.setFamilyCode(familyCode);
			boolean flag = prd.toDeletePayRecord();
			if(flag) {
				resp.getWriter().write("true");
			}else {
				resp.getWriter().write("false");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	 
	 
}
