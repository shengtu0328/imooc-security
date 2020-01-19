/**
 * 
 */
package com.imooc.security.core.properties;

import lombok.Data;

/**
 * @author zhailiang
 *
 */
@Data
public class BrowserProperties {

	private String loginPage = SecurityConstants.DEFAULT_LOGIN_PAGE_URL;

	private LoginResponseType loginType = LoginResponseType.JSON;

}
