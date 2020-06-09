package com.gxuwz.medical.web.servlet;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gxuwz.medical.dao.DiseaseDao;
import com.gxuwz.medical.dao.PersonDao;
import com.gxuwz.medical.domain.disease.Disease;
import com.gxuwz.medical.domain.diseaseCard.DiseaseCard;
import com.gxuwz.medical.domain.person.Person;

/**
 * 慢性病分类管理控制模块
 * @ClassName: DiseaseCardServlet
 * @author SunYi
 * @Date 2019年5月30日上午11:17:08
 */
public class DiseaseCardServlet extends BaseServlet {

	private static final long serialVersionUID = -6890417744105693033L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 获取页面传过来的方法名称
		String m = req.getParameter("m");
		// 进行请求转发
		if("list".equals(m)) {
			list(req, resp);
		} else if("search".equals(m)) {
			search(req, resp);
			process(req, resp, "/page/diseaseCard/diseaseCard_select.jsp");
		} else if("select".equals(m)) {
			getPerson(req, resp);
			process(req, resp, "/page/diseaseCard/diseaseCard_select.jsp");
		} else if("input".equals(m)) {
			getDisease(req, resp);
			process(req, resp, "/page/diseaseCard/diseaseCard_add.jsp");
		} else if("add".equals(m)) {
			add(req, resp);
			list(req, resp);
		} else if("check".equals(m)) {
			check(req, resp);
		} else if("del".equals(m)) {
			del(req, resp);
		} else if("get".equals(m)) {
			process(req, resp, "/page/diseaseCard/diseaseCard_edit.jsp");
		} else if("edit".equals(m)) {
			edit(req, resp);
			list(req, resp);
		} else {
			error(req, resp);
		}
	}

	
	/**
	 * 请求转发至慢性病证管理列表页面
	 * @author SunYi
	 * @Date 2019年5月30日上午11:18:37
	 * @return void
	 */
	private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp, "/page/diseaseCard/diseaseCard_list.jsp");
	}
	
	/**
	 * 查询指定用户名的用户档案信息
	 * @author SunYi
	 * @Date 2019年5月31日下午10:31:50
	 * @return void
	 */
	private void search(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 接收表单传过来的用户名
		String name = req.getParameter("name");
		req.getSession().setAttribute("name", name);
		try {
			Person person = new Person();
			List<Person> personList = person.getPersonByName(name);
			if(personList != null && !"".equals(personList)) {
				req.getSession().setAttribute("personList", personList);
			}else {
				resp.getWriter().write("false");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取所有的参合农民信息
	 * @author SunYi
	 * @Date 2019年6月6日上午10:09:45
	 * @return void
	 */
	private void getPerson(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			PersonDao personDao = new PersonDao();
			String sql = "select * from t_person_archives where 1=1 ";
			Object[] params = {};
			// 调用dao层方法
			List<Person> personList = personDao.queryOjects(sql, params);
			req.getSession().setAttribute("personList", personList);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取所有的慢性病信息
	 * @author SunYi
	 * @Date 2019年6月6日上午10:09:45
	 * @return void
	 */
	private void getDisease(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			DiseaseDao diseaseDao = new DiseaseDao();
			String sql = "select * from t_disease_type where 1=1 ";
			Object[] params = {};
			// 调用dao层方法
			List<Disease> diseaseList = diseaseDao.queryOjects(sql, params);
			req.getSession().setAttribute("diseaseList", diseaseList);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 添加慢性病证
	 * @author SunYi
	 * @Date 2019年4月12日下午2:59:41
	 * @return void
	 */
	private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			// 接收表单数据
			String nongheCard = req.getParameter("nongheCard");
			String idCard = req.getParameter("idCard");
			String diseaseCode = req.getParameter("diseaseCode");
			String startTimeStr = req.getParameter("startTime");
			String endTimeStr = req.getParameter("endTime");
			// 查询指定农合证号和慢性病编码的慢性病证是否已存在
			DiseaseCard disCard = new DiseaseCard(nongheCard, diseaseCode);
			System.out.println(disCard.toString());
			if (disCard != null && !"".equals(disCard)) {
				// 如果已存在则返回至页面
				resp.getWriter().write("exist");
				list(req, resp);
			} else {
				// String类型的时间转换成Date类型
				/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date startTimeParse = sdf.parse(startTimeStr);
				String startTimeSdf = sdf.format(startTimeParse);
				Date startTime = sdf.parse(startTimeSdf);
				
				Date endTimeParse = sdf.parse(endTimeStr);
				String endTimeSdf = sdf.format(endTimeParse);
				Date endTime = sdf.parse(endTimeSdf);*/
				
				Date startTime = new Date();
				Date endTime = new Date();
				
				System.out.println(startTime + "//" + endTime);
				
				// 设置附件存储位置
				String path = req.getSession().getServletContext().getRealPath("/attachment/'"+nongheCard+"'");
				// 如果目录不存在则创建
		        File uploadDir = new File(path);
		        if (!uploadDir.exists()) {
		            uploadDir.mkdir();
		        }
				// 执行预添加方法
				disCard.toAdd(nongheCard, idCard, diseaseCode, startTime, endTime, path);
				
			  /* *************************************************接收上传的医院证明附件并存进服务器根目录************************************************* */
			 /* req.setCharacterEncoding("utf-8");
			  String[] allowTypes = {"jpg","png"};
              DiskFileItemFactory factory = new DiskFileItemFactory();
              ServletFileUpload upload = new ServletFileUpload(factory);
              //String path = "D://workshop//upload_data";
              
              List<FileItem> items = (List<FileItem>) upload.parseRequest((RequestContext) req);
              Iterator itr = items.iterator();
              while (itr.hasNext()) {
                   FileItem item = (FileItem) itr.next();
                   if(item.isFormField()){
                       System.out.println("文本信息");
                   }else{
                        if (item.getName() != null && !item.getName().equals("")) {
                            String name = item.getName();  
                            String type = name.substring(name.lastIndexOf('.')+1);
                            boolean flag = false;
                            for(String at:allowTypes){            
                                if(at.equals(type)){
                                    flag = true;
                                }
                            }
                            if(!flag){
                                System.out.println("文件格式不支持");
                                req.setAttribute("message", "文件格式不支持");
                            }else{
                                int start = name.lastIndexOf("\\");
                                String filename = name.substring(start+1);  
                                File file = new File(path+"/"+filename);
                                item.write(file);
                                req.setAttribute("message", "文件上传成功");
                            }
                        }else{
                            System.out.println("请选择待上传文件");
                            req.setAttribute("message", "请选择待上传文件");
                        }
                   }
              }*/
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 校验慢性病证是否已存在
	 * @author SunYi
	 * @Date 2019年4月12日下午3:28:46
	 * @return void
	 */
	private boolean check(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		boolean result = false;
		try {
			// 接收表单提交的数据
			String diseaseCode = req.getParameter("diseaseCode");
			String diseaseName = req.getParameter("diseaseName");
			
			// 1:判断慢性病编码是否已存在
			if(diseaseCode != null && !"".equals(diseaseCode)) {
				Disease dis = new Disease(diseaseCode);
				if(dis.getDiseaseCode() == diseaseCode) {
					resp.getWriter().write("falseCode");
					result = true;
				}else {
					resp.getWriter().write("trueCode");
					return false;
				}
			}
			
			// 2:判断慢性病名称是否已存在
			if(diseaseName != null && !"".equals(diseaseName)) {
				Disease dis = new Disease();
				boolean flag = dis.checkDiseaseName(diseaseName);
				if(flag) {
					resp.getWriter().write("trueName");
					return false;
				}else {
					resp.getWriter().write("falseName");
					result = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 删除慢性病信息
	 * @author SunYi
	 * @Date 2019年4月12日下午10:53:28
	 * @return void
	 */
	private void del(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			// 1:接收表单数据
			String diseaseCode = req.getParameter("diseaseCode");
			// 2:执行预删除操作
			Disease dis = new Disease();
			dis.toDeleteDisease(diseaseCode);
			resp.getWriter().write("true");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 更新慢性病信息
	 * @author SunYi
	 * @Date 2019年4月12日下午11:41:02
	 * @return void
	 */
	private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			// 接收表单数据
			int id = Integer.parseInt(req.getParameter("id"));	// 要修改的慢性病编码对应的序号
			String diseaseCode = req.getParameter("diseaseCode"); // 修改后的慢性病编码
			String pinyinCode = req.getParameter("pinyinCode");
			String diseaseName = req.getParameter("diseaseName");
			// 执行预更新方法
			Disease dis = new Disease();
			dis.toUpdateDisease(id, diseaseCode, pinyinCode, diseaseName);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
