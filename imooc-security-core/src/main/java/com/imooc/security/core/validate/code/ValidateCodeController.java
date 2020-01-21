/**
 * 
 */
package com.imooc.security.core.validate.code;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imooc.security.core.properties.SecurityProperties;
import com.imooc.security.core.validate.code.image.ImageCode;
import com.imooc.security.core.validate.code.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;

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


	@Qualifier("imageValidateCodeGenerator")
	@Autowired
	private ValidateCodeGenerator imageCodeGenerator;//@Bean方式注入 都是按name


	@Qualifier("smsValidateCodeGenerator")
	@Autowired
	private ValidateCodeGenerator  smsCodeGenerator;//@Compent方式注入 都是按name


	@Autowired
	private SmsCodeSender smsCodeSender;

	@GetMapping("/code/image")
	public void createCode(HttpServletRequest request, HttpServletResponse response)throws IOException {
		ImageCode imageCode=(ImageCode) imageCodeGenerator.generate(new ServletWebRequest(request));//生成
		sessionStrategy.setAttribute(new ServletWebRequest(request),SESSION_KEY,imageCode);//通过请求拿session并将图片证码对象放进session里  SESSION_KEY就是session里的key imageCode是SESSION_KEY的value
		ImageIO.write(imageCode.getImage(),"JPEG",response.getOutputStream());//将图片发送
	}



	@GetMapping("/code/sms")
	public void createSmsCode(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletRequestBindingException {

		ValidateCode smsCode= smsCodeGenerator.generate(new ServletWebRequest(request));//生成
		sessionStrategy.setAttribute(new ServletWebRequest(request),SESSION_KEY,smsCode);//通过请求拿session并将短信验证码对象放进session里  SESSION_KEY就是session里的key imageCode是SESSION_KEY的value
        String mobile= ServletRequestUtils.getRequiredStringParameter(request,"mobile");
		smsCodeSender.send(mobile,smsCode.getCode());//发送
	}




}
