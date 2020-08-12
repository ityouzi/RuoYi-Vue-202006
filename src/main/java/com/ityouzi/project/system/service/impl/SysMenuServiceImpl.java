package com.ityouzi.project.system.service.impl;

import com.ityouzi.common.utils.SecurityUtils;
import com.ityouzi.common.utils.StringUtils;
import com.ityouzi.common.utils.TreeUtils;
import com.ityouzi.project.system.domain.SysMenu;
import com.ityouzi.project.system.domain.vo.MetaVo;
import com.ityouzi.project.system.domain.vo.RouterVo;
import com.ityouzi.project.system.mapper.SysMenuMapper;
import com.ityouzi.project.system.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Auther: lizhen
 * @Date: 2020-06-17 14:33
 * @Description: 菜单 业务处理层
 */
@Service
public class SysMenuServiceImpl implements ISysMenuService {
    @Autowired
    private SysMenuMapper menuMapper;



    /**
     * 根据用户ID查询权限
     *
     * @param userId
     */
    @Override
    public Set<String> selectMenuPermsByUserId(Long userId) {
        // 读取 SysMenu 的权限标识数组
        List<String> perms = menuMapper.selectMenuPermsByUserId(userId);
        // 逐个，按照“逗号” 分隔
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms){
            if (StringUtils.isNotEmpty(perm)){
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 根据用户ID查询菜单树信息
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    @Override
    public List<SysMenu> selectMenuTreeByUserId(Long userId) {
        List<SysMenu> menus = null;
        if(SecurityUtils.isAdmin(userId)) {
            menus = menuMapper.selectMenuTreeAll();
        } else {
            menus = menuMapper.selectMenuTreeByUserId(userId);
        }
        return new TreeUtils().getChildPerms(menus, 0);
    }

    /**
     * 构建前端路由所需要的菜单
     *
     * @param menus 菜单列表
     * @return 路由列表
     */
    @Override
    public List<RouterVo> buildMenus(List<SysMenu> menus) {
        List<RouterVo> routers = new LinkedList<>();
        for (SysMenu menu : menus){
            RouterVo router = new RouterVo();
            router.setName(StringUtils.capitalize(menu.getPath()));
            router.setPath(getRouterPath(menu));
            router.setComponent(StringUtils.isEmpty(menu.getComponent()) ? "Layout" : menu.getComponent());
            router.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon()));
            List<SysMenu> cMenus = menu.getChildren();
            if(!cMenus.isEmpty() && cMenus.size() > 0 && "M".equals(menu.getMenuType())) {
                router.setAlwaysShow(true);
                router.setRedirect("noRedirect");
                router.setChildren(buildMenus(cMenus));
            }
            routers.add(router);
        }
        return routers;
    }

    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return 路由地址
     */
    public String getRouterPath(SysMenu menu) {
        String routerPath = menu.getPath();
        // 非外链并且是一级目录
        if (0 == menu.getParentId() && "1".equals(menu.getIsFrame())) {
            routerPath = "/" + menu.getPath();
        }
        return routerPath;
    }

}
