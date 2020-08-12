package com.ityouzi.framework.security.service;

import com.ityouzi.project.system.domain.SysUser;
import com.ityouzi.project.system.service.ISysMenuService;
import com.ityouzi.project.system.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @Auther: lizhen
 * @Date: 2020-06-17 10:15
 * @Description: 用户权限处理
 */
@Component
public class SysPermissionService {


    @Autowired
    private ISysRoleService roleService;


    @Autowired
    private ISysMenuService menuService;


    /**
     * 获取菜单数据权限
     *
     * @param
     */
    public Set<String> getMenuPermission(SysUser user){
        Set<String> roles = new HashSet<>();
        // 管理员拥有所有权限
        if (user.isAdmin()){
            roles.add("*:*:*"); // 所有模块
        } else {
            // 读取
            roles.addAll(menuService.selectMenuPermsByUserId(user.getUserId()));
        }
        return roles;
    }

    /**
     * 获取角色数据权限
     *
     * @param user 用户信息
     * @return 角色权限信息
     */
    public Set<String> getRolePermission(SysUser user) {
        Set<String> roles = new HashSet<>();
        // 管理员拥有所有权限
        if(user.isAdmin()) {    // 如果是管理员，强制添加admin角色
            roles.add("admin");
        } else {    // 如果非管理员，进行查询
            roles.addAll(roleService.selectRolePermissionByUserId(user.getUserId()));
        }
        return roles;
    }
}
