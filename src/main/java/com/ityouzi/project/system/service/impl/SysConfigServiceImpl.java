package com.ityouzi.project.system.service.impl;

import com.ityouzi.common.constant.Constants;
import com.ityouzi.common.core.text.Convert;
import com.ityouzi.common.utils.StringUtils;
import com.ityouzi.framework.redis.RedisCache;
import com.ityouzi.project.system.domain.SysConfig;
import com.ityouzi.project.system.mapper.SysConfigMapper;
import com.ityouzi.project.system.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: lizhen
 * @Date: 2020-06-29 09:50
 * @Description: 参数配置，服务实现
 */
@Service
public class SysConfigServiceImpl implements ISysConfigService {

    @Autowired
    private SysConfigMapper configMapper;

    @Autowired
    private RedisCache redisCache;


    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数键名
     * @return 参数键值
     */
    @Override
    public String selectConfigByKey(String configKey) {
        String configValue = Convert.toStr(redisCache.getCacheObject(getCacheKey(configKey)));
        if(StringUtils.isNotEmpty(configKey)) {
            return configKey;
        }
        SysConfig config = new SysConfig();
        config.setConfigKey(configKey);
        SysConfig retConfig = configMapper.selectConfig(config);
        if(StringUtils.isNotNull(retConfig)) {
            redisCache.setCacheObject(getCacheKey(configKey), retConfig);
            return retConfig.getConfigValue();
        }
        return StringUtils.EMPTY;
    }


    /**
     * 设置cache key
     *
     * @param configKey 参数键
     * @return 缓存键key
     */
    private String getCacheKey(String configKey)
    {
        return Constants.SYS_CONFIG_KEY + configKey;
    }

}
