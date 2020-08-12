package com.ityouzi.framework.security.filter;

import com.ityouzi.common.utils.SecurityUtils;
import com.ityouzi.common.utils.StringUtils;
import com.ityouzi.framework.security.LoginUser;
import com.ityouzi.framework.security.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther: lizhen
 * @Date: 2020-06-14 15:36
 * @Description: token 过滤器 验证token有效性
 * 继承 OncePerRequestFilter 过滤器，实现了基于 Token 的认证
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 获得当前LoginUser
        LoginUser loginUser = tokenService.getLoginUser(request);
        // 如果存在LoginUser，并且认证通过
        if (StringUtils.isNotNull(loginUser) && StringUtils.isNull(SecurityUtils.getAuthentication())){
            // 校验 token 有效性
            tokenService.verifyToken(loginUser);
            // 创建 UsernamePasswordAuthenticationToken 对象，设置到 SecurityContextHolder 中
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginUser, null, loginUser.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        // 继续过滤
        filterChain.doFilter(request, response);
    }
}
