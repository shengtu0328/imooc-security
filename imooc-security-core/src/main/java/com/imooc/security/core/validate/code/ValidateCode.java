/**
 *
 */
package com.imooc.security.core.validate.code;

import lombok.Data;

import java.time.LocalDateTime;


/**
 * @author zhailiang
 * 验证码对象
 */
@Data
public class ValidateCode {


    private String code;

    private LocalDateTime expireTime;//过期时间


    //expireIn 是多少秒后过期
    public ValidateCode(String code, int expireIn) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    public ValidateCode(String code, LocalDateTime expireTime) {
        this.code = code;
        this.expireTime = expireTime;
    }

    public boolean isExpried() {
        return LocalDateTime.now().isAfter(expireTime);
    }


}
