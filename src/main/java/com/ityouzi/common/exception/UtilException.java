package com.ityouzi.common.exception;

/**
 * @Auther: lizhen
 * @Date: 2020-06-16 19:11
 * @Description: 工具类异常
 */
public class UtilException extends RuntimeException{

    private static final long serailVerSionUID = 8247610319171014183L;

    public UtilException(Throwable e){
        super(e.getMessage(),e);
    }

    public UtilException(String message){
        super(message);
    }

    public UtilException(String message, Throwable throwable){
        super(message, throwable);
    }


}
