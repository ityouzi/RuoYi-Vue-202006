package com.ityouzi.framework.security.service;

import com.ityouzi.common.constant.Constants;
import com.ityouzi.common.exception.CustomException;
import com.ityouzi.common.exception.user.CaptchaException;
import com.ityouzi.common.exception.user.CaptchaExpireException;
import com.ityouzi.common.exception.user.UserPasswordNotMatchException;
import com.ityouzi.common.utils.MessageUtils;
import com.ityouzi.framework.manager.AsyncManager;
import com.ityouzi.framework.manager.factory.AsyncFactory;
import com.ityouzi.framework.redis.RedisCache;
import com.ityouzi.framework.security.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Auther: lizhen
 * @Date: 2020-06-15 08:49
 * @Description: 登录校验方法
 */
@Component
public class SysLoginService {

    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;


    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @param code     验证码
     * @param uuid     唯一标识
     * @return         结果
     */
    public String login(String username,
                        String password,
                        String code,
                        String uuid){
        // 验证图片验证码的正确性
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid; // uuid 的作用，是获得对应的图片验证码
        String captcha = redisCache.getCacheObject(verifyKey); // 从redis 中获得图片验证码
        redisCache.deleteObject(verifyKey); // 从redis中删除图片验证码

        // 图片验证码不存在
        if (captcha == null){
            AsyncManager.me().execute(AsyncFactory.recordLoginInfo(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
            throw new CaptchaExpireException();
        }
        // 图片验证码不正确
        if (!code.equalsIgnoreCase(captcha)){
            AsyncManager.me().execute(AsyncFactory.recordLoginInfo(username,Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire")));
            throw new CaptchaException();
        }
        // <2>用户验证
        Authentication authentication;

        try {
            // 该方法会去调用 UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username,password));
        } catch (Exception e) {
            // <2.1> 发生异常，说明验证不通过，记录相应的登陆失败日志
            if (e instanceof BadCredentialsException){
                AsyncManager.me().execute(AsyncFactory.recordLoginInfo(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match") ));
                throw new UserPasswordNotMatchException();
            } else {
                AsyncManager.me().execute(AsyncFactory.recordLoginInfo(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new CustomException(e.getMessage());
            }
        }
        // <2.2> 验证通过，记录相应的登陆成功日志
        AsyncManager.me().execute(AsyncFactory.recordLoginInfo(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        // <3> 生成 Token
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        return tokenService.createToken(loginUser);
    }

}

/**
 * <1> 处，该验证码会存储在 Redis 缓存中，通过 uuid 作为对应的标识。
 * 生成的逻辑，胖友自己看 CaptchaController 提供的 /captchaImage 接口。
 *
 * <2> 处，调用 Spring Security 的 AuthenticationManager 的 #authenticate(UsernamePasswordAuthenticationToken authentication) 方法，基于用户名与密码的登陆认证。
 * 在其内部，会调用我们定义的 UserDetailsServiceImpl 的 #loadUserByUsername(String username) 方法，获得指定用户名对应的用户信息
 *
 *
 *
 *
 *
 */


