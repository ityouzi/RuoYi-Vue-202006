package com.ityouzi.project.system.mapper;

import com.ityouzi.project.system.domain.SysUserPost;

import java.util.List;

/**
 * 用户与岗位关联表 数据层
 * 
 * @authro lizhen 
 */
public interface SysUserPostMapper {

    /**
     * 批量新增用户岗位信息
     *
     * @param userPostList 用户岗位列表
     * @return 结果
     */
    int batchUserPost(List<SysUserPost> userPostList);

    /**
     * 通过用户ID删除用户和岗位关联
     *
     * @param userId 用户ID
     */
    int deleteUserPostByUserId(Long userId);
}
