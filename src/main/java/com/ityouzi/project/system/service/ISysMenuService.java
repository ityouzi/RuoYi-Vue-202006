package com.ityouzi.project.system.service;


import com.ityouzi.project.system.domain.SysMenu;
import com.ityouzi.project.system.domain.vo.RouterVo;

import java.util.List;
import java.util.Set;

/**
 * 菜单 业务层
 */
public interface ISysMenuService {


    /**
     * 根据用户ID查询权限
     *
     * @param
     */
    public Set<String> selectMenuPermsByUserId(Long userId);



    /**
     * 根据用户ID查询菜单树信息
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<SysMenu> selectMenuTreeByUserId(Long userId);


    /**
     * 构建前端路由所需要的菜单
     *
     * @param menus 菜单列表
     * @return 路由列表
     */
    List<RouterVo> buildMenus(List<SysMenu> menus);
}
