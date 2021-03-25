package com.ityouzi.project.system.service;


import com.ityouzi.framework.web.domain.TreeSelect;
import com.ityouzi.project.system.domain.SysMenu;
import com.ityouzi.project.system.domain.vo.RouterVo;

import java.util.List;
import java.util.Set;

/**
 * 菜单 业务层
 */
public interface ISysMenuService {

    /**
     * 根据用户查询系统菜单列表
     *
     * @param
     * @return
     */
    List<SysMenu> selectMenuList(Long userId);


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

    /**
     * 根据用户查询系统菜单列表
     *
     * @param menu 菜单信息
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<SysMenu> selectMenuList(SysMenu menu, Long userId);

    /**
     * 根据菜单ID查询信息
     *
     * @param menuId 菜单ID
     * @return 菜单详情
     */
    SysMenu selectMenuById(Long menuId);

    /**
     * 构建前端所需要的下拉树结构
     *
     * @param menus 菜单列表
     * @return 菜单下拉树结构
     */
    List<TreeSelect> buildMenuTreeSelect(List<SysMenu> menus);

    /**
     * 构建前端所需要的树结构
     *
     * @param
     * @return
     */
    List<SysMenu> buildMenuTree(List<SysMenu> menus);

    /**
     * 根据角色ID查询菜单树信息
     *
     * @param roleId 角色ID
     * @return 选中菜单列表
     */
    List<Integer> selectMenuListByRoleId(Long roleId);

    /**
     * 校验菜单名称是否唯一
     *
     * @param menu 菜单信息
     * @return 结果
     */
    String checkMenuNameUnique(SysMenu menu);

    /**
     * 新增保存菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    int insertMenu(SysMenu menu);

    /**
     * 修改保存菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    int updateMenu(SysMenu menu);
}
