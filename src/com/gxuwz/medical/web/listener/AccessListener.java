package com.gxuwz.medical.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 用户访问监听器
 * @ClassName: AccessListener
 * @author SunYi
 * @Date 2019年4月3日下午4:02:08
 */
public class AccessListener implements HttpSessionListener, ServletContextListener{

	
	// 关闭服务器时执行Application销毁
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}
		
	// 启动服务器时执行Application初始化
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// 1:创建计数器
		int count = 0;
		// 2:把计数器存进application对象里
		sce.getServletContext().setAttribute("count", count);
	}
	
	// 登录时执行session初始化
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		// 1:获取application对象存的计数器
		ServletContext sc = se.getSession().getServletContext();
		int count = (int) sc.getAttribute("count");
		// 2:计数器自增
		count++;
		// 3:再把计数器存进application对象里
		sc.setAttribute("count", count);
	}
	
	// 退出登录时执行session销毁
	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		// 1:获取application对象存的计数器
		ServletContext sc = se.getSession().getServletContext();
		int count = (int) sc.getAttribute("count");
		// 2:计数器自减
		count--;
		// 3:再把计数器存进application对象里
		sc.setAttribute("count", count);
	}

}
