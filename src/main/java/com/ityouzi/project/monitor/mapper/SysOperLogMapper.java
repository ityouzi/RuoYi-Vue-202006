package com.ityouzi.project.monitor.mapper;


import com.ityouzi.project.monitor.domain.SysOperLog;

/**
 * 操作日志 数据层
 * 
 * @authro lizhen 
 */
public interface SysOperLogMapper {

    /**
     * 新增操作日志
     *
     * @param
     */
    public void insertOperLog(SysOperLog operLog);


}
