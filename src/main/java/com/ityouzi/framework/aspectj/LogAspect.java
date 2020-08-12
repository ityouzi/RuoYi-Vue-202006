package com.ityouzi.framework.aspectj;

import com.alibaba.fastjson.JSON;
import com.ityouzi.common.utils.ServletUtils;
import com.ityouzi.common.utils.StringUtils;
import com.ityouzi.common.utils.ip.IpUtils;
import com.ityouzi.common.utils.spring.SpringUtils;
import com.ityouzi.framework.aspectj.lang.annotation.Log;
import com.ityouzi.framework.aspectj.lang.enums.BusinessStatus;
import com.ityouzi.framework.manager.AsyncManager;
import com.ityouzi.framework.manager.factory.AsyncFactory;
import com.ityouzi.framework.security.LoginUser;
import com.ityouzi.framework.security.service.TokenService;
import com.ityouzi.project.monitor.domain.SysOperLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @Auther: lizhen
 * @Date: 2020-06-21 17:01
 * @Description: 操作日志记录处理的切入点
 */

@Aspect
@Component
public class LogAspect {

    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);



    /**
     *1.配置日志织入点:log注解
     */
    @Pointcut("@annotation(com.ityouzi.framework.aspectj.lang.annotation.Log)")
    public void logPointCut(){}


    /**
     * 2.处理完请求后执行日志记录操作
     *
     * @param  joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Object jsonResult){
        handlerLog(joinPoint, null, jsonResult);    // 执行操作
    }

    /**
     * 拦截异常操作
     *
     * @param
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e){
        handlerLog(joinPoint, e, null);
    }

    /** 3.日志处理具体的方法 */
    protected void handlerLog(final JoinPoint joinPoint,
                              final Exception e,
                              Object jsonResout){
        try {

            // 获得注解
            Log controllerLog = getAnnotationLog(joinPoint);
            if(controllerLog == null) {
                return;
            }

            // 获取当前用户
            LoginUser loginUser = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest());

            /** 5.写入数据库日志 */
            SysOperLog operLog = new SysOperLog();
            operLog.setStatus(BusinessStatus.SUCCESS.ordinal());
            // 请求的地址
            String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
            operLog.setOperIp(ip);
            // 返回参数
            operLog.setJsonResult(JSON.toJSONString(jsonResout));
            // 请求方法URL
            operLog.setOperUrl(ServletUtils.getRequest().getRequestURI());
            if(loginUser != null) {
                operLog.setOperName(loginUser.getUsername());
            }
            if(e != null) {
                operLog.setStatus(BusinessStatus.FAIL.ordinal());
                operLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
            }
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operLog.setMethod(className + "." + methodName + "()");
            // 设置请求方式
            operLog.setRequestMethod(ServletUtils.getRequest().getMethod());
            // 处理设置在注解上的参数
            getControllerMethodDescription(joinPoint, controllerLog, operLog);
            /** 6.保存数据库（调用异步处理AsyncFactory.recordOper方法执行具体操作） */
            AsyncManager.me().execute(AsyncFactory.recordOper(operLog));    
        } catch (Exception e1) {
            log.error("=====前置通知异常=====");
            log.error("=====前置通知异常信息:{}", e1.getMessage());
            e1.printStackTrace();
        }
    }


    /**
     * 看方法上是否存在注解，如果存在就获取
     */
    private Log getAnnotationLog(JoinPoint joinPoint) throws Exception{
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if(method != null) {
            return method.getAnnotation(Log.class); // 调用获取方法的注解的方法
        }
        return null;
    }

    /**
     * 获取注解中对方法的描述信息，用于Controller层注解
     *
     * @param joinPoint 切入点
     * @param log 日志
     * @param operLog 操作日志
     * @exception Exception
     */
    public void getControllerMethodDescription(JoinPoint joinPoint, Log log, SysOperLog operLog) throws  Exception{
        // 设置action动作
        operLog.setBusinessType(log.businessType().ordinal());
        // 设置标题
        operLog.setTitle(log.title());
        // 设置操作人类别
        operLog.setOperatorType(log.operatorType().ordinal());
        // 是否需要保存request，参数和值
        if(log.isSaveRequestData()) {
            // 获取参数的信息，传入到数据库中
            setRequestValue(joinPoint, operLog);
        }

    }

    /**
     * 获取请求的参数，放到log中
     *
     * @param
     *
     */
    private void setRequestValue(JoinPoint joinPoint, SysOperLog operLog) throws Exception{
        String requestMethod = operLog.getRequestMethod();
        if(HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
            String params = argsArrayToString(joinPoint.getArgs());
            operLog.setOperParam(StringUtils.substring(params,0, 2000));
        } else {
            Map<?, ?> paramsMap = (Map<?, ?>) ServletUtils.getRequest().getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            operLog.setOperParam(StringUtils.substring(paramsMap.toString(),0, 2000));
        }
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray){
        String params = "";
        if(paramsArray != null && paramsArray.length > 0) {
            for (int i = 0; i < paramsArray.length; i++){
                if(!isFileterObject(paramsArray[i])) {
                    Object json = JSON.toJSON(paramsArray[i]);
                    params += json.toString() + " ";
                }
            }
        }
        return params.trim();
    }

    /**
     * 判断是否需要过滤的对象
     *
     * @param
     *
     */
    public boolean isFileterObject(final Object o){
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse;
    }








}
