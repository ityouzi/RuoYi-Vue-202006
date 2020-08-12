package com.ityouzi.project.system.mapper;


import com.ityouzi.project.system.domain.SysDept;

import java.util.List;

/**
 * 部门管理 数据层
 */
public interface SysDeptMapper {


    /**
     * 查询部门管理数据
     *
     * @param dept 部门信息
     * @return 部门信息集合
     */
    public List<SysDept> selectDeptList(SysDept dept);

}
