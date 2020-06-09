package com.gxuwz.medical.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 字符集编码过滤器，默认设置为UTF-8
 * @ClassName: CharacterEncodingFilter
 * @author SunYi
 * @Date 2019年4月3日下午3:50:40
 */
public class CharacterEncodingFilter implements Filter {

	// 默认编码格式 (在web.xml默认配置为UTF-8)
	private String encoding;
	
	// 是否强制设置request的编码为encoding(默认false)
	private boolean forceRequestEncoding = false;
	
	// 是否强制设置response的编码为encoding(默认false)
	private boolean forceResponseEncoding = false;
	
	// 是否强制设置编码为encoding(默认false)
	private boolean forceEncoding = false;
	
	/**
	 * 初始化配置参数
	 * @author SunYi
	 * @Date 2019年4月3日下午3:52:50
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		// 加载web.xml文件中过滤器的配置参数
		encoding =filterConfig.getInitParameter("encoding");
	}

	/**
	 * 过滤器拦截
	 * @author SunYi
	 * @Date 2019年4月3日下午3:58:18
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		// 获取编码格式
		String encoding = getEncoding();
		if (encoding != null) {
			if (isForceRequestEncoding() || request.getCharacterEncoding() == null) {
				// 设置请求编码格式
				request.setCharacterEncoding(encoding);
			}
			if (isForceResponseEncoding()) {
				// 设置响应编码格式
				response.setCharacterEncoding(encoding);
			}
		}
		// 放行所有
		filterChain.doFilter(request, response);
	}

	/**
	 * 销毁过滤器
	 * @author SunYi
	 * @Date 2019年4月3日下午4:00:35
	 */
	public void destroy() {
		
	}

	
	
	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public boolean isForceRequestEncoding() {
		return forceRequestEncoding;
	}

	public void setForceRequestEncoding(boolean forceRequestEncoding) {
		this.forceRequestEncoding = forceRequestEncoding;
	}

	public boolean isForceResponseEncoding() {
		return forceResponseEncoding;
	}
	public boolean isForceEncoding() {
		return forceEncoding;
	}

	public void setForceEncoding(boolean forceEncoding) {
		this.forceEncoding = forceEncoding;
	}
	public void setForceResponseEncoding(boolean forceResponseEncoding) {
		this.forceResponseEncoding = forceResponseEncoding;
	}
	
}
