package com.gxuwz.medical.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 用户访问过滤器
 * @ClassName: AuthorFilter
 * @author SunYi
 * @Date 2019年4月3日下午3:49:47
 */
public class AuthFilter implements Filter {
	
	/**
	 * 过滤器销毁
	 * @author SunYi
	 * @Date 2019年4月3日下午4:01:07
	 */
	public void destroy() {
		
	}

	/**
	 * 过滤器初始化
	 * @author SunYi
	 * @Date 2019年4月3日下午4:01:07
	 */
	public void init(FilterConfig config) throws ServletException {

	}
	
	/**
	 * 过滤器拦截
	 * @author SunYi
	 * @Date 2019年4月3日下午4:01:07
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		chain.doFilter(request, response);

	}

}
