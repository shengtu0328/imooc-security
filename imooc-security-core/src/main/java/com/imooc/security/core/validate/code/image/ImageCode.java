/**
 * 
 */
package com.imooc.security.core.validate.code.image;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

import lombok.Data;


/**
 * @author zhailiang
 *
 */
@Data
public class ImageCode {
	
	private BufferedImage image;

	private String code;

	private LocalDateTime expireTime;//过期时间


	//expireIn 是多少秒后过期
	public ImageCode(BufferedImage image, String code, int expireIn){
		this.image = image;
		this.code = code;
		this.expireTime=LocalDateTime.now().plusSeconds(expireIn);
	}
	
	public ImageCode(BufferedImage image, String code, LocalDateTime expireTime){
		this.image = image;
		this.code = code;
		this.expireTime = expireTime;
	}
	

}
