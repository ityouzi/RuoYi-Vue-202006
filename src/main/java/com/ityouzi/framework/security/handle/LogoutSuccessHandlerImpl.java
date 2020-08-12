package com.ityouzi.framework.security.handle;

import com.alibaba.fastjson.JSON;
import com.ityouzi.common.constant.Constants;
import com.ityouzi.common.constant.HttpStatus;
import com.ityouzi.common.utils.ServletUtils;
import com.ityouzi.common.utils.StringUtils;
import com.ityouzi.framework.manager.AsyncManager;
import com.ityouzi.framework.manager.factory.AsyncFactory;
import com.ityouzi.framework.security.LoginUser;
import com.ityouzi.framework.security.service.TokenService;
import com.ityouzi.framework.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther: Liberal-World
 * @Date: 2020-06-10 16:15
 * @Description: 自定义退出处理类 返回成功
 * 实现 Spring Security LogoutSuccessHandler 接口，自定义退出的处理，主动删除 LoginUser 在 Redis 中的缓存
 * @Version 1.0
 */
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {


    @Autowired
    private TokenService tokenService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 获得当前LoginUser
        LoginUser loginUser = tokenService.getLoginUser(request);
        // 如果有登录的情况下
        if (StringUtils.isNotNull(loginUser)){
            String userName = loginUser.getUsername();
            // 删除用户缓存记录
            tokenService.delLoginUser(loginUser.getToken());
            // 记录用户退出日志
            AsyncManager.me().execute(AsyncFactory.recordLoginInfo(userName, Constants.LOGOUT,"退出成功"));
        }
        // 响应退出成功
        ServletUtils.renderString(response, JSON.toJSONString(AjaxResult.error(HttpStatus.SUCCESS, "退出成功")));
    }
}
