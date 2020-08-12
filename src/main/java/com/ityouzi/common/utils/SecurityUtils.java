package com.ityouzi.common.utils;

import com.ityouzi.common.constant.HttpStatus;
import com.ityouzi.common.exception.CustomException;
import com.ityouzi.framework.security.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Auther: lizhen
 * @Date: 2020-06-14 15:41
 * @Description: 安全服务工具类
 */
public class SecurityUtils {


    /**
     * 获取用户账户
     */
    public static String getUserName(){
        try{
            return getLoginUser().getUsername();
        }
        catch (Exception e){
            // 未授权
            throw new CustomException("获取用户账户异常", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * 获取用户
     */
    public static LoginUser getLoginUser(){
        try{
            return (LoginUser) getAuthentication().getPrincipal();
        }
        catch (Exception e){
            throw new CustomException("获取用户信息异常", HttpStatus.UNAUTHORIZED);
        }
    }


    /**
     * 获取 Authentication
     */
    public static Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }


    /**
     * 生成BCryptPasswordEncoder密码
     *
     * @param password 要加密字符串
     * @return 加密后的密码
     */
    public static String encryptPassword(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }


    /**
     * 判断密码是否相同
     *
     * @param rawPassword 真实密码
     * @param encodePassword 加密后密码
     */
    public static boolean matchesPassword(String rawPassword, String encodePassword){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodePassword);
    }


    /**
     * 是否为管理员
     *
     * @param userId 用户ID
     * @return 结果
     */
    public static boolean isAdmin(Long userId){
        return userId != null && 1L == userId;
    }
}
