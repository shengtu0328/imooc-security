/**
 *
 */
package com.imooc.security.core.validate.code;

import com.imooc.security.core.validate.code.image.ImageCode;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhailiang
 * 自定义验证码过滤器
 *
 */
@Data
@Component("validateCodeFilter")//实现了OncePerRequestFilter代表这个过滤器只会被调用一次
public class ValidateCodeFilter extends OncePerRequestFilter {

    //认证失败处理器
    private AuthenticationFailureHandler authenticationFailureHandler;


    /**
     * 操作session的工具类
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();


    //逻辑判断
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse reponse, FilterChain filterChain) throws ServletException, IOException {

        //  只在登录表单提交的时候使用这个过滤器
        if (StringUtils.equals("/authentication/form", request.getRequestURI())
                && StringUtils.equalsIgnoreCase(request.getMethod(), "post")) {


            try {
                validate(new ServletWebRequest(request));//session可以从 ServletWebRequest中拿
            } catch (ValidateCodeException e) {
                authenticationFailureHandler.onAuthenticationFailure(request, reponse, e);
                return;//return代表不走后面的过滤器
            }


        }

        //不是就往下走其他过滤器
        filterChain.doFilter(request, reponse);


    }

    private void validate(ServletWebRequest request) throws ServletRequestBindingException {

        //从session中将之前/code/image方法存入的验证码对象取出
        ImageCode codeInSession = (ImageCode) sessionStrategy.getAttribute(request, ValidateCodeController.SESSION_KEY);


        //与请求中接受到的验证码对象做比较
        String codeInRequest;
        codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "imageCode");

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException("验证码的值不能为空");
        }

        if (codeInSession == null) {
            throw new ValidateCodeException("验证码不存在");
        }

        if (codeInSession.isExpried()) {
            sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_KEY);
            throw new ValidateCodeException("验证码已过期");
        }

        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException("验证码不匹配");
        }

        //验证成功 移除session中的验证码
        sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_KEY);
    }
}
