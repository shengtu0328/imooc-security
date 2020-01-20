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
public class ImageCodeProperties {

	private int width = 67; //验证码图片的宽
	private int height = 23;//验证码图片的高
	private int length = 4;//验证码位数
	private int expireIn = 60;//验证码有效时间 单位：秒

	private String url="";

}
