package com.ityouzi.common.utils;

import com.ityouzi.common.constant.Constants;
import com.ityouzi.common.utils.spring.SpringUtils;
import com.ityouzi.framework.redis.RedisCache;
import com.ityouzi.project.system.domain.SysDictData;

import java.util.List;

/**
 * @Auther: lizhen
 * @Date: 2020-06-29 08:59
 * @Description: 字典工具类
 */
public class DictUtils {

    /**
     * 设置字典缓存
     *
     * @param key 键
     * @param dictDatas 字典数据列表
     */
    public static void setDictCache(String key, List<SysDictData> dictDatas){
        SpringUtils.getBean(RedisCache.class).setCacheObject(getCacheKey(key), dictDatas);
    }





    /**
     * 获取字典缓存
     *
     * @param key 参数键
     * @return dictData 字典数据列表
     */
    public static List<SysDictData> getDictCache(String key){
        Object cacheObject = SpringUtils.getBean(RedisCache.class).getCacheObject(getCacheKey(key));
        if(StringUtils.isNotNull(cacheObject)) {
            List<SysDictData> dictData = StringUtils.cast(cacheObject);
            return dictData;
        }
        return null;
    }


    /**
     * 设置cache key
     *
     * @param
     */
    public static String getCacheKey(String configKey){
        return Constants.SYS_DICT_KEY + configKey;
    }
}
