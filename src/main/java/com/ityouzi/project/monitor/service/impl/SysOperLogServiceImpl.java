package com.ityouzi.project.monitor.service.impl;

import com.ityouzi.project.monitor.domain.SysOperLog;
import com.ityouzi.project.monitor.mapper.SysOperLogMapper;
import com.ityouzi.project.monitor.service.ISysOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: lizhen
 * @Date: 2020-06-22 14:30
 * @Description: 操作日志 服务层处理
 *
 */
@Service
public class SysOperLogServiceImpl implements ISysOperLogService {

    @Autowired
    private SysOperLogMapper operLogMapper;


    /**
     * 新增操作日志记录
     *
     * @param operLog
     */
    @Override
    public void insertOperLog(SysOperLog operLog) {
        operLogMapper.insertOperLog(operLog);
    }
}
