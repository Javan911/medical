package com.gxuwz.medical.exception;

/**
 * 角色不存在异常类
 * @ClassName: RoleNotFoundException
 * @author SunYi
 * @Date 2019年4月3日上午9:33:12
 */
public class RoleNotFoundException extends Exception {

	private static final long serialVersionUID = 1661231452957198677L;

	public RoleNotFoundException(String message) {
		super(message);
	}

	public RoleNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
