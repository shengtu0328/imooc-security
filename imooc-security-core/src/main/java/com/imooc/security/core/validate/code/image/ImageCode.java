/**
 * 
 */
package com.imooc.security.core.validate.code.image;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

import com.imooc.security.core.validate.code.ValidateCode;
import lombok.Data;


/**
 * @author zhailiang
 * 图片验证码对象
 */
@Data
public class ImageCode extends ValidateCode {
	
	private BufferedImage image;


	//expireIn 是多少秒后过期
	public ImageCode(BufferedImage image, String code, int expireIn){
		super(code,expireIn);
		this.image = image;
	}
	
	public ImageCode(BufferedImage image, String code, LocalDateTime expireTime){
		super(code,expireTime);
		this.image = image;
	}


}
