/**
 *
 */
package com.imooc.security.browser;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author zhailiang
 *
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.formLogin()//表单登录进行身份认证
//        http.httpBasic()//httpBasic登录进行身份认证
                .loginPage("/imooc-signIn.html")//配置登录页面（但是这样写无论是浏览器还是接口调用都只会返回到页面上）
                .loginProcessingUrl("/authentication/form")//登录访问的url 即UsernamePasswordAuthenticationFilter会来处理这个请求
                .and()
                .authorizeRequests()//对下面请求
                .antMatchers("/imooc-signIn.html").permitAll()//对这个请求不需要身份验证
                .anyRequest()//任何请求
                .authenticated()//都需要认证/登录
                .and()
                .csrf().disable();

        super.configure(http);
    }
}
