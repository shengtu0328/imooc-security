/**
 *
 */
package com.imooc.security.browser;

import com.imooc.security.core.properties.SecurityProperties;
import com.imooc.security.core.validate.code.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * @author zhailiang
 *
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private AuthenticationSuccessHandler imoocAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler imoocAuthenticationFailureHandler;


    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
//		tokenRepository.setCreateTableOnStartup(true);第一次启动时需要
        return tokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ValidateCodeFilter validateCodeFilter=new ValidateCodeFilter();
        validateCodeFilter.setAuthenticationFailureHandler(imoocAuthenticationFailureHandler);
        validateCodeFilter.setSecurityProperties(securityProperties);
        validateCodeFilter.afterPropertiesSet();

        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)//在过滤器链中添加自定义验证码过滤器 在UsernamePasswordAuthenticationFilter之前
                .formLogin()//表单登录进行身份认证
//        http.httpBasic()//httpBasic登录进行身份认证
//              .loginPage("/imooc-signIn.html")//配置认证不通过后的跳转的登录页面（但是这样写无论是浏览器还是接口调用都只会返回到页面上）
                .loginPage("/authentication/require")//配置认证不通过后跳转的登录方法
                .loginProcessingUrl("/authentication/form")//配置UsernamePasswordAuthenticationFilter（即ss表单登录校验实现）会处理的url，即这个url可以看做登录方法的url
                .successHandler(imoocAuthenticationSuccessHandler)//配置自定义登录成功处理器，登录成功后用这里配置的处理器
                .failureHandler(imoocAuthenticationFailureHandler)//配置自定义登录失败处理器，登录失败后用这里配置的处理器
                .and()
                .rememberMe()//记住我的配置
                .tokenRepository(persistentTokenRepository())//记住我的TokenRepository
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())//记住我的有效时间
                .userDetailsService(userDetailsService)//用这个去登录
                .and()
                .authorizeRequests()//对下面请求
                .antMatchers("/authentication/require"
                        ,securityProperties.getBrowser().getLoginPage()
                        ,"/code/*").permitAll()//对这些个请求不需要身份验证
                .anyRequest()//任何请求
                .authenticated()//都需要认证/登录
                .and()
                .csrf().disable();//暂时关闭 ss的csrf验证
        super.configure(http);
    }
}
