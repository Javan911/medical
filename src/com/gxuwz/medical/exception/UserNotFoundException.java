package com.gxuwz.medical.exception;

/**
 * 用户不存在异常类
 * @ClassName: UserNotFoundException
 * @author SunYi
 * @Date 2019年4月3日上午9:34:39
 */
public class UserNotFoundException extends Exception {
	
	private static final long serialVersionUID = 669563614752723350L;

	public UserNotFoundException(String message) {
		super(message);
	}

	public UserNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
