package com.ityouzi.project.monitor.service.impl;

import com.ityouzi.project.monitor.domain.SysLogininfor;
import com.ityouzi.project.monitor.mapper.SysLogininforMapper;
import com.ityouzi.project.monitor.service.ISysLogininforService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @Auther: lizhen
 * @Date: 2020-06-14 15:24
 * @Description: 系统访问日志情况信息 服务层处理
 */
@Service
public class SysLogininforServiceImpl implements ISysLogininforService {

    @Autowired
    private SysLogininforMapper logininforMapper;


    /**
     * 新增系统登录日志
     *
     * @param logininfor 访问日志对象
     */
    @Override
    public void insertLogininfor(SysLogininfor logininfor) {
        logininforMapper.insertLogininfor(logininfor);
    }
}
