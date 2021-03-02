package com.yue.springsecurityjwtlogindemo2.exceptions;

import org.springframework.security.core.AuthenticationException;

/*
 * @Description 图形验证码不匹配
 * @Author YueLi/xiroiyuki@hotmail.com
 * @Time 2021/3/1 21:53
 */
public class CaptchaNotMatchException extends AuthenticationException {
    public CaptchaNotMatchException(String msg) {
        super(msg);
    }

    public CaptchaNotMatchException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
