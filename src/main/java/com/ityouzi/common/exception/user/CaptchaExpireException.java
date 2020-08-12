package com.ityouzi.common.exception.user;

/**
 * @Auther: lizhen
 * @Date: 2020-06-15 09:11
 * @Description: 验证码失效异常类
 */
public class CaptchaExpireException extends UserException{
    private static final long serialVersionUID = 1L;

    public CaptchaExpireException(){
        super("user.jcaptcha.expire",null);
    }


}
