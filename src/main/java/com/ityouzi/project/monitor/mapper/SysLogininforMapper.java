package com.ityouzi.project.monitor.mapper;

import com.ityouzi.project.monitor.domain.SysLogininfor;



public interface SysLogininforMapper {
    /**
     * 新增系统登录日志
     *
     * @param logininfor 访问日志对象
     */
    public void insertLogininfor(SysLogininfor logininfor);
}
