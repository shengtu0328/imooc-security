/**
 * 
 */
package com.imooc.security.core.validate.code;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imooc.security.core.properties.SecurityProperties;
import com.imooc.security.core.validate.code.image.ImageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import com.imooc.security.core.properties.SecurityConstants;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * @author zhailiang
 *
 */
@RestController
public class ValidateCodeController {

	public  static final  String SESSION_KEY="SESSION_KEY_IMAGE_CODE";

	private SessionStrategy sessionStrategy= new HttpSessionSessionStrategy();

	@Autowired
	private SecurityProperties securityProperties;

	@Autowired
	private ValidateCodeGenerator imageCodeGenerator;

	@GetMapping("/code/image")
	public void createCode(HttpServletRequest request, HttpServletResponse response)throws IOException {

		ImageCode imageCode= imageCodeGenerator.generate(new ServletWebRequest(request));
		sessionStrategy.setAttribute(new ServletWebRequest(request),SESSION_KEY,imageCode);//同过请求拿session  SESSION_KEY就是session里的key imageCode是SESSION_KEY的value
		ImageIO.write(imageCode.getImage(),"JPEG",response.getOutputStream());//将图片写出去
	}
}
