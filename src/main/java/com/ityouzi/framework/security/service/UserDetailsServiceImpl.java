package com.ityouzi.framework.security.service;

import com.ityouzi.common.enums.UserStatus;
import com.ityouzi.common.exception.BaseException;
import com.ityouzi.common.utils.StringUtils;
import com.ityouzi.framework.security.LoginUser;
import com.ityouzi.project.system.domain.SysUser;
import com.ityouzi.project.system.service.ISysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Auther: lizhen
 * @Date: 2020-06-15 14:06
 * @Description: 用户验证处理
 * 实现 Spring Security UserDetailsService 接口，
 * 实现了该接口定义的 #loadUserByUsername(String username) 方法，获得指定用户名对应的用户信息
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private ISysUserService userService;

    @Autowired
    private SysPermissionService permissionService;


    /**
     * 通过用户名加载用户
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询指定用户名对应的 SysUser
        SysUser user = userService.selectUserByUserName(username);
        // 各种校验
        if(StringUtils.isNull(user)) {  // 用户不存在
            log.info("登录用户：{} 不存在", username);
            throw new UsernameNotFoundException("登录用户：" + username + "不存在");
        } else if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) { // 用户以删除
            log.info("登录用户：{} 已被删除.", username);
            throw new BaseException("对不起，您的账号：" + username + "已被删除");
        } else if (UserStatus.DISABLE.getCode().equals(user.getStatus())) { // 用户已禁用
            log.info("登录用户：{} 已被停用.", username);
            throw new BaseException("对不起，您的账号：" + username + " 已停用");
        }

        // 创建 spring security UserDetails 用户明细
        return createLoginUser(user);
    }


    public UserDetails createLoginUser(SysUser user){
        return new LoginUser(user, permissionService.getMenuPermission(user));
    }

}
