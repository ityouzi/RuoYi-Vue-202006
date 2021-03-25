package com.ityouzi.common.filter;


import com.ityouzi.common.utils.StringUtils;
import org.springframework.http.MediaType;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @description: 重复过滤
 * @author: lizhen created on 2021-03-24 14:11
 */
public class RepeatableFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ServletRequest requestWrapper = null;
        // 忽略大小写比较、
        if (request instanceof HttpServletRequest && StringUtils.equalsAnyIgnoreCase(request.getContentType(),
                MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE))
        {
//            requestWrapper = new
        }
    }

    @Override
    public void destroy() {

    }
}
