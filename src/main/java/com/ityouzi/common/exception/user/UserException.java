package com.ityouzi.common.exception.user;

import com.ityouzi.common.exception.BaseException;

/**
 * @Auther: lizhen
 * @Date: 2020-06-15 09:11
 * @Description: 用户信息异常类
 */
public class UserException extends BaseException {
    private static final long serialVersionUID = 1L;

    public UserException(String code, Object[] args){
        super("user", code, args, null);
    }

}
