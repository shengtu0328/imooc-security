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
public class SmsCodeProperties {

    private int length = 6;//验证码位数
    private int expireIn = 60;//验证码有效时间 单位：秒

    private String url = "";

}
