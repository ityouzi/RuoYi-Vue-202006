package com.ityouzi.common.utils;

/**
 * @Auther: lizhen
 * @Date: 2020-06-14 15:13
 * @Description: 处理并记录日志文件
 */
public class LogUtils {
    public static String getBlock(Object msg){
        if (msg == null){
            msg = "";
        }
        return "[" + msg.toString() + "]";
    }

}
