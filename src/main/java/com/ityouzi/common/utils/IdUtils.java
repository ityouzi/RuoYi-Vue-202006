package com.ityouzi.common.utils;

import com.ityouzi.common.core.lang.UUID;

/**
 * @Auther: lizhen
 * @Date: 2020-06-15 09:52
 * @Description: ID生成器工具类
 */
public class IdUtils {




    /**
     * 获取随机UUID，使用性能更好的ThreadLocalRandom生成UUID
     *
     * @return 随机UUID
     */
    public static String fastUUID(){
        return UUID.fastUUID().toString();
    }



    /**
     * 简化的UUID, 去掉了横线
     *
     */
    public static String simpleUUID(){
        return UUID.randomUUID().toString(true);
    }

}
