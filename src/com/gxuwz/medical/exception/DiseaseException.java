package com.gxuwz.medical.exception;

/**
 * 慢性病异常类
 * @ClassName: DiseaseException
 * @author SunYi
 * @Date 2019年4月12日下午1:33:45
 */
public class DiseaseException extends Exception {

	private static final long serialVersionUID = 8921617163638147377L;

	public DiseaseException(String message) {
		super(message);
	}

	public DiseaseException(String message, Throwable cause) {
		super(message, cause);
	}
}
