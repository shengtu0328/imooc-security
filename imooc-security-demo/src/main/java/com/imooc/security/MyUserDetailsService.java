/**
 * 
 */
package com.imooc.security;

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
public class MyUserDetailsService implements UserDetailsService, SocialUserDetailsService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	/*
	 * 这个方法是在平时登录中获取用户信息与数据库中做比较完成认证，如果校验成功会把用户信息放在session里面
	 * 方法的返回值不一定要是UserDetails，只需要是UserDetails的自定义实现类即可
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("登录用户名:" + username);


       //这里可以是根据用户名去数据库查找用户信息的代码
		//BCryptPasswordEncoder每次加密都是不同的结果
		String password = passwordEncoder.encode("123456");
		logger.info("数据库密码是:" + password);


		//根据查找到的用户信息判断用户是否被冻结
        return new User(username,
				password,
				 true,//是否可用（是否被删了，删了一般就不能恢复了）
				 true,//是否过期
				 true,//密码是否过期
				true,//是否被锁定(锁定一般还可以恢复)
				 AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));

		//new User这一步ss会把用户信息组装，并且ss会自动把请求中的密码和这数据库里的密码进行是否匹配的基本操作,上面的new User也是同理
		//return new User(username,"123456",AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
	                                     //"123456"是数据库的密码   // 第三个参数是授权 也应该是数据库中查出来的权限
	}


	@Override
	public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {

		logger.info("社交登录用户名:" + userId);

		//这里可以是根据用户名去数据库查找用户信息的代码
		//BCryptPasswordEncoder每次加密都是不同的结果
		String password = passwordEncoder.encode("123456");
		logger.info("数据库密码是:" + password);


		//根据查找到的用户信息判断用户是否被冻结
		return new SocialUser(userId,
				password,
				true,//是否可用（是否被删了，删了一般就不能恢复了）
				true,//是否过期
				true,//密码是否过期
				true,//是否被锁定(锁定一般还可以恢复)
				AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));

		//new User这一步ss会把用户信息组装，并且ss会自动把请求中的密码和这数据库里的密码进行是否匹配的基本操作,上面的new User也是同理
		//return new User(username,"123456",AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
		//"123456"是数据库的密码   // 第三个参数是授权 也应该是数据库中查出来的权限
	}
}
