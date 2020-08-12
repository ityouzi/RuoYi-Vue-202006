package com.ityouzi.project.system.service;

import com.ityouzi.project.system.domain.SysPost;

import java.util.List;

/**
 * 岗位信息 服务层
 */
public interface ISysPostService {

    /**
     * 查询所有岗位
     *
     * @return 岗位列表
     */
    List<SysPost> selectPostAll();

    /**
     * 根据用户ID获取岗位选择框列表
     *
     * @param userId 用户ID
     * @return 选中岗位ID列表
     */
    List<Integer> selectPostListByUserId(Long userId);
}
