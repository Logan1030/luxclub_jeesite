package com.footing.website.modules.luxclub.utils;
/**
 * 
 * <p>
 * Description:定义业务异常<br />
 * </p>
 * @title BusinessException.java
 * @package com.footing.website.modules.luxclub.utils 
 * @author yubin
 * @version 0.1 2016年3月17日
 */
@SuppressWarnings("serial")
public class BusinessException  extends Exception{

	public BusinessException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public BusinessException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public BusinessException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
  
	
}
