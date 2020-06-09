package com.gxuwz.medical.exception;

/**
 * 数据库异常类
 * @ClassName: DbException
 * @author SunYi
 * @Date 2019年4月3日上午9:33:45
 */
public class DbException extends Exception {

	private static final long serialVersionUID = 8127388419650118348L;

	public DbException(String message, Throwable cause) {
		super(message, cause);
	}

	public DbException(String message) {
		super(message);
	}

}
