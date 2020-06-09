package com.gxuwz.medical.exception;

/**
 * 农合机构异常类
 * @ClassName: InstitutionException
 * @author SunYi
 * @Date 2019年4月6日下午11:22:17
 */
public class InstitutionException extends Exception {
	
	private static final long serialVersionUID = -2921636010407603345L;

	public InstitutionException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public InstitutionException(String message) {
		super(message);
	}

}
