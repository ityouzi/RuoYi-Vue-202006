package com.ityouzi.framework.security.handle;

import com.alibaba.fastjson.JSON;
import com.ityouzi.common.constant.HttpStatus;
import com.ityouzi.common.utils.ServletUtils;
import com.ityouzi.common.utils.StringUtils;
import com.ityouzi.framework.web.domain.AjaxResult;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * @Auther: Liberal-World
 * @Date: 2020-06-10 15:36
 * @Description: 认证失败处理类 返回未授权
 * 实现 Spring Security AuthenticationEntryPoint 接口，处理认失败的 AuthenticationException 异常
 * @Version 1.0
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -8970718410437077606L;


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        // 相应认证不通过
        int code = HttpStatus.UNAUTHORIZED;
        String msg = StringUtils.format("请求访问: {}, 认证失败，无法访问系统资源", request.getRequestURI());
        ServletUtils.renderString(response, JSON.toJSONString(AjaxResult.error(code,msg)));
    }
}
