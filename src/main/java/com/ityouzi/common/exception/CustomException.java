package com.ityouzi.common.exception;

/**
 * @Auther: lizhen
 * @Date: 2020-06-15 09:45
 *
 * @Description: 自定义异常
 */
public class CustomException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    private Integer code;

    private String message;

    public CustomException(String message)
    {
        this.message = message;
    }

    public CustomException(String message, Integer code)
    {
        this.message = message;
        this.code = code;
    }

    public CustomException(String message, Throwable e)
    {
        super(message, e);
        this.message = message;
    }
    @Override
    public String getMessage()
    {
        return message;
    }

    public Integer getCode()
    {
        return code;
    }

}
