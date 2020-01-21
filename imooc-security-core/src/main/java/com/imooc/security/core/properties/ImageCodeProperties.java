/**
 * 
 */
package com.imooc.security.core.properties;

import lombok.Data;

/**
 * @author zhailiang
 * 验证码默认配置
 */
@Data
public class ImageCodeProperties extends  SmsCodeProperties{

	public ImageCodeProperties() {
		setLength(4);
	}

	private int width = 67; //验证码图片的宽
	private int height = 23;//验证码图片的高

}
