package com.ityouzi.common.exception.user;

/**
 * @Auther: lizhen
 * @Date: 2020-06-15 09:10
 *
 * @Description: 验证码错误异常类
 */
public class CaptchaException extends UserException{
    private static final long serialVersionUID = 1L;

    public CaptchaException() {
        super("user.jcaptcha.error", null);
    }
}
