package com.ityouzi.project.system.mapper;

import com.ityouzi.project.system.domain.SysUserRole;

import java.util.List;

/**
 * 用户与角色关联表 数据层
 * 
 * @authro lizhen 
 */
public interface SysUserRoleMapper {

    /**
     * 批量新增用户角色信息
     *
     * @param userRoleList 用户角色列表
     * @return 结果
     */
    int batchUserRole(List<SysUserRole> userRoleList);

    /**
     * 通过用户ID删除用户和角色关联
     *
     * @param userId 用户ID
     */
    int deleteUserRoleByUserId(Long userId);

    /**
     * 通过角色ID查询角色使用数量
     *
     * @param roleId 角色ID
     * @return 结果
     */
    int countUserRoleByRoleId(Long roleId);
}
