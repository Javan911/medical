package com.gxuwz.medical.exception;

/**
 * 行政区域找不到异常类
 * @ClassName: AreaNotFoundException
 * @author SunYi
 * @Date 2019年4月5日下午2:18:24
 */
public class AreaNotFoundException extends Exception{

	private static final long serialVersionUID = 4310306719045275796L;

	public AreaNotFoundException(String message) {
		super(message);
	}

	public AreaNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
