package com.ityouzi.project.system.mapper;


import com.ityouzi.project.system.domain.SysConfig;

/**
 * 参数配置，数据层
 * 
 * @authro lizhen 
 */
public interface SysConfigMapper {

    /**
     * 查询参数配置信息
     *
     * @param config 参数配置信息
     * @return 参数配置信息
     */
    public SysConfig selectConfig(SysConfig config);
}
