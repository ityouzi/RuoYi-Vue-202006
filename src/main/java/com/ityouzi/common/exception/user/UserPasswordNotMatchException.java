package com.ityouzi.common.exception.user;

/**
 * @Auther: lizhen
 * @Date: 2020-06-15 09:43
 * @Description: 用户密码不正确或不符合规范异常类
 */
public class UserPasswordNotMatchException extends UserException{
    private static final long serialVersionUID = 1L;

    public UserPasswordNotMatchException(){
        super("user.password.not.match",null);
    }
}
