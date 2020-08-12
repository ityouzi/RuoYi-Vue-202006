package com.ityouzi.project.system.mapper;

import com.ityouzi.project.system.domain.SysRoleMenu;

import java.util.List;

/**
 * @Author lizhen
 * @Date 2020/8/12 11:31
 * @Version 1.0
 *
 * 角色与菜单关联表，数据层
 */
public interface SysRoleMenuMapper {


    /**
     * 批量新增角色与菜单关联信息
     *
     * @param roleMenuList 角色菜单列表
     * @return
     */

    int batchRoleMenu(List<SysRoleMenu> roleMenuList);

    /**
     * 通过角色ID删除角色和菜单关联
     *
     * @param roleId 角色ID
     * @return 结果
     */
    int deleteRoleMenuByRoleId(Long roleId);
}
