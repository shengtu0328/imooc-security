/**
 * 
 */
package com.imooc.security.browser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

/**
 * @author zhailiang
 *
 */
@Component
public class MyUserDetailsService implements UserDetailsService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
//	@Autowired
//	private PasswordEncoder passwordEncoder;

	/*
	 * 这一步是在平时登录中获取用户信息与数据库中做比较完成认证，如果校验成功会把用户信息放在session里面
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("登录用户名:" + username);


       //这里可以是根据用户名去数据库查找用户信息的代码

		//这一步ss会把用户信息组装，并且ss会自动把请求中的密码和这数据库里的密码完成是否匹配的操作
		return new User(username,"123456",AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
	                                     //"123456"是数据库的密码   // 第三个参数是授权 也应该是数据库中查出来的权限
	}



}
