package com.ityouzi.framework.security.service;

import com.ityouzi.common.utils.ServletUtils;
import com.ityouzi.common.utils.StringUtils;
import com.ityouzi.framework.security.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Set;

/**
 * @Auther: lizhen
 * @Date: 2020-06-28 15:11
 * @Description: 自定义权限实现，ss取自SpringSecurity首字母
 */

@Service("ss")
public class PermissionService {


    /**
     * 所有权限标识
     */
    private static final String ALL_PERMISSION = "*:*:*";

    /**
     * 管理员角色权限标识
     */
    private static final String SUPER_ADMIN = "admin";

    private static final String ROLE_DELIMETER = ",";

    private static final String PERMISSION_DELIMETER = ",";

    @Autowired
    private TokenService tokenService;


    /**
     * 验证用户是否具备某些权限
     *
     * @param
     */
    public boolean hasPermi(String permission) {
        // 如果未设置需要的权限，强制不具备
        if(StringUtils.isEmpty(permission)) {
            return false;
        }
        // 获得当前 LoginUser
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        // 如果不存在，或者没有任何权限，说明权限验证不通过
        if(StringUtils.isNull(loginUser) || CollectionUtils.isEmpty(loginUser.getPermissions())) {
            return false;
        }
        // 判断是否包含权限
        return hasPermission(loginUser.getPermissions(), permission);
    }




    /**
     * 判断是否包含权限
     *
     * @param
     */
    private boolean hasPermission(Set<String> permissions, String permission) {
        return permissions.contains(ALL_PERMISSION) || permissions.contains(StringUtils.trim(permission));
    }


}
