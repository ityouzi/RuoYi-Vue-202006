package com.ityouzi.framework.web.exception;

import com.ityouzi.common.constant.HttpStatus;
import com.ityouzi.common.exception.BaseException;
import com.ityouzi.common.exception.CustomException;
import com.ityouzi.common.utils.StringUtils;
import com.ityouzi.framework.web.domain.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * @Auther: lizhen
 * @Date: 2020-06-17 17:18
 * @Description: 全局异常处理器
 *
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    /**
     * 基础异常
     */
    @ExceptionHandler(BaseException.class)
    public AjaxResult baseException(BaseException e){
        return AjaxResult.error(e.getMessage());
    }


    /**
     * 业务异常(用户自定义异常)
     */
    @ExceptionHandler(CustomException.class)
    public AjaxResult businessException(CustomException e){
        if(StringUtils.isNull(e.getCode())) {
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.error(e.getCode(), e.getMessage());
    }


    /**
     * 未找到处理程序异常
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public AjaxResult handlerNoFoundException(Exception e){
        log.error(e.getMessage(), e);
        return AjaxResult.error(HttpStatus.NOT_FOUND, "路径不存在，请检查路径是否正确");
    }

    /**
     * 处理权限校验不通过异常
     * 没有访问权限。使用 @PreAuthorize 校验权限不通过时，就会抛出 AccessDeniedException 异常
     */
    @ExceptionHandler(AccessDeniedException.class)
    public AjaxResult handlerAuthorizationException(AccessDeniedException e){
        log.error(e.getMessage());
        return AjaxResult.error(HttpStatus.FORBIDDEN, "没有权限，请联系管理员授权");
    }

    /**
     * 处理账号过期异常
     */
    @ExceptionHandler(AccountExpiredException.class)
    public AjaxResult handlerAccountExpiredException(AccountExpiredException e){
        log.error(e.getMessage(),e);
        return AjaxResult.error(e.getMessage());
    }

    /**
     * 处理用户名不存在异常
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public AjaxResult handlerUsernameNotFoundException(UsernameNotFoundException e){
        log.error(e.getMessage(),e);
        return AjaxResult.error(e.getMessage());
    }

    /**
     * 其他异常
     */
    @ExceptionHandler(Exception.class)
    public AjaxResult handlerException(Exception e){
        log.error(e.getMessage(),e);
        return AjaxResult.error(e.getMessage());
    }

    /**
     * 自定义验证异常(绑定异常)
     */
    @ExceptionHandler(BindException.class)
    public AjaxResult validatedBindException(BindException e){
        log.error(e.getMessage(),e);
        String msg = e.getAllErrors().get(0).getDefaultMessage();
        return AjaxResult.error(msg);
    }

    /**
     * 自定义验证异常(方法参数无效异常)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object validExceptionHandler(MethodArgumentNotValidException e){
        log.error(e.getMessage(),e);
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return AjaxResult.error(message);
    }

    /**
     * 演示模式异常
     */



}
