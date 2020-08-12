package com.ityouzi.project.system.service;

/**
 * 参数配置，服务层
 * 
 * @authro lizhen 
 */
public interface ISysConfigService {

    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数键名
     * @return 参数键值
     */
    public String selectConfigByKey(String configKey);
}
