package com.ityouzi.project.system.controller;

import com.ityouzi.common.constant.Constants;
import com.ityouzi.common.utils.ServletUtils;
import com.ityouzi.framework.security.LoginBody;
import com.ityouzi.framework.security.LoginUser;
import com.ityouzi.framework.security.service.SysLoginService;
import com.ityouzi.framework.security.service.SysPermissionService;
import com.ityouzi.framework.security.service.TokenService;
import com.ityouzi.framework.web.domain.AjaxResult;
import com.ityouzi.project.system.domain.SysMenu;
import com.ityouzi.project.system.domain.SysUser;
import com.ityouzi.project.system.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * @Auther: lizhen
 * @Date: 2020-06-15 08:48
 * @Description: 登录验证
 */
@RestController
public class SysLoginController {


    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private TokenService tokenService;



    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return         结果
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody){
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(),
                loginBody.getPassword(), loginBody.getCode(), loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }
    
    
    /**
     * 获取登陆的用户信息
     * 
     * @param 
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo() {
        // 调用 TokenService 的 #getLoginUser(request) 方法，获得当前 LoginUser
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
        // 调用 PermissionService 的 #getRolePermission(SysUser user) 方法，获得 LoginUser 拥有的角色标识的集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        // 返回结果
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters(){
        // 调用 TokenService 的 #getLoginUser(request) 方法，获得当前 LoginUser
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        // 获得用户的 SysMenu 数组
        SysUser user = loginUser.getUser();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(user.getUserId());
        // 构建路由 routervo 数组。可用于Vue 构建管理后台的左边栏
        return AjaxResult.success(menuService.buildMenus(menus));
    }
}
