package com.ityouzi.project.monitor.service;


import com.ityouzi.project.monitor.domain.SysOperLog;

/**
 * 操作日志 服务层
 */
public interface ISysOperLogService {
    
    
    /**
     * 新增操作日志记录
     * 
     * @param 
     */
    public void insertOperLog(SysOperLog operLog);
    
}
