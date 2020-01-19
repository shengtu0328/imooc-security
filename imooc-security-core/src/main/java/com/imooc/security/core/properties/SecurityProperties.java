/**
 * 
 */
package com.imooc.security.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhailiang
 *
 */
@Data
@ConfigurationProperties(prefix = "imooc.security")
public class SecurityProperties {
	
	private BrowserProperties browser = new BrowserProperties();
	

}

