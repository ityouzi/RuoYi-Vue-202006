package com.ityouzi.project.system.mapper;

import com.ityouzi.project.system.domain.SysRoleDept;

import java.util.List;

/**
 * @Author lizhen
 * @Date 2020/8/12 13:06
 * @Version 1.0
 *
 * 角色与部门关联表 数据层
 */
public interface SysRoleDeptMapper {


    /**
     * 通过角色ID删除角色和部门关联
     *
     * @param roleId 角色ID
     * @return 结果
     */
    int deleteRoleDeptByRoleId(Long roleId);

    /**
     * 批量新增角色部门信息
     *
     * @param roleDeptList 角色部门列表
     * @return 结果
     */
    int batchRoleDept(List<SysRoleDept> roleDeptList);
}
