package com.ityouzi.project.system.service.impl;

import com.ityouzi.project.system.domain.SysPost;
import com.ityouzi.project.system.mapper.SysPostMapper;
import com.ityouzi.project.system.service.ISysPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: lizhen
 * @Date: 2020-07-13 12:17
 * @Description: 岗位信息 服务层处理
 */
@Service
public class SysPostServiceImpl implements ISysPostService {

    @Autowired
    private SysPostMapper postMapper;

    /**
     * 查询所有岗位
     *
     * @return 岗位列表
     */
    @Override
    public List<SysPost> selectPostAll() {
        return postMapper.selectPostAll();
    }

    /**
     * 根据用户ID获取岗位选择框列表
     *
     * @param userId 用户ID
     * @return 选中岗位ID列表
     */
    @Override
    public List<Integer> selectPostListByUserId(Long userId) {
        return postMapper.selectPostListByUserId(userId);
    }
}
