package com.ityouzi.framework.security;

import lombok.Getter;
import lombok.Setter;

/**
 * @Auther: lizhen
 * @Date: 2020-06-30 09:59
 * @Description: 用户登录需要的参数对象
 */
@Getter
@Setter
public class LoginBody {

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 验证码
     */
    private String code;

    /**
     * 唯一标识UUID
     */
    private String uuid = "";

}
