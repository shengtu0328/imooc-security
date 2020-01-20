/**
 * 
 */
package com.imooc.security.core.validate.code;

import org.springframework.security.core.AuthenticationException;

/**
 * @author zhailiang
 *
 */
//验证码异常 AuthenticationException是所有身份验证异常的基类
public class ValidateCodeException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7285211528095468156L;

	public ValidateCodeException(String msg) {
		super(msg);
	}

}
