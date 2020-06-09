package com.gxuwz.medical.exception;

/**
 * 权限菜单异常类
 * @ClassName: MenuException
 * @author SunYi
 * @Date 2019年4月3日上午9:34:16
 */
public class MenuException extends Exception {

	private static final long serialVersionUID = 666630365377677481L;

	public MenuException(String message, Throwable cause) {
		super(message, cause);
	}

	public MenuException(String message) {
		super(message);
	}

}
