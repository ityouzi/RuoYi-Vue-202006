package com.ityouzi.project.monitor.service;

import com.ityouzi.project.monitor.domain.SysLogininfor;

/**
 * 系统访问日志情况信息， 服务层
 *
 * 2020-06-14 15:24
 */
public interface ISysLogininforService {
    /**
     * 新增系统登录日志
     *
     * @param logininfor 访问日志对象
     */
    public void insertLogininfor(SysLogininfor logininfor);
}
