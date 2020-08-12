package com.ityouzi.project.monitor.controller;

import com.ityouzi.common.constant.Constants;
import com.ityouzi.common.utils.StringUtils;
import com.ityouzi.framework.aspectj.lang.annotation.Log;
import com.ityouzi.framework.aspectj.lang.enums.BusinessType;
import com.ityouzi.framework.redis.RedisCache;
import com.ityouzi.framework.security.LoginUser;
import com.ityouzi.framework.web.controller.BaseController;
import com.ityouzi.framework.web.domain.AjaxResult;
import com.ityouzi.framework.web.page.TableDataInfo;
import com.ityouzi.project.monitor.domain.SysUserOnline;
import com.ityouzi.project.monitor.service.ISysUserOnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @Auther: lizhen
 * @Date: 2020-06-30 10:31
 * @Description: 在线用户监控
 */
@RestController
@RequestMapping("/monitor/online")
public class SysUserOnlineController extends BaseController {

    @Autowired
    private ISysUserOnlineService userOnlineService;

    @Autowired
    private RedisCache redisCache;

    /**
     * 在线用户列表
     */
    @PreAuthorize("@ss.hasPermi('monitor:online:list')")    // 是否具备某种权限
    @GetMapping("/list")
    public TableDataInfo list(String ipaddr, String userName){
        // 获取token
        Collection<String> keys = redisCache.keys(Constants.LOGIN_TOKEN_KEY + "*");
        List<SysUserOnline> onlineList = new ArrayList<>();
        for (String key : keys){
            // 登录对象
            LoginUser user = redisCache.getCacheObject(key);
//            LoginUser user = JSON.toJavaObject(redisCache.getCacheObject(key), LoginUser.class);
//            LoginUser user = (LoginUser) JSON.toJSON(redisCache.getCacheObject(key));

            if(StringUtils.isNotNull(ipaddr) && StringUtils.isNotNull(userName)) {
                // 判断ip和用户名
                if(StringUtils.equals(ipaddr, user.getIpaddr()) && StringUtils.equals(userName, user.getUsername())) {
                    // 通过登录地址/用户名称查询信息
                    onlineList.add(userOnlineService.selectOnlineByInfo(ipaddr, userName, user));
                }
            }
            else if (StringUtils.isNotEmpty(ipaddr)) {
                if(StringUtils.equals(ipaddr, user.getIpaddr()) && StringUtils.equals(userName, user.getUsername())) {
                    // 通过登录地址查询信息
                    onlineList.add(userOnlineService.selectOnlineByIpaddr(ipaddr, user));
                }
            }
            else if (StringUtils.isNotEmpty(userName) && StringUtils.isNotNull(user.getUsername())) {
                if(StringUtils.equals(userName, user.getUsername())) {
                    // 通过用户名称查询信息
                    onlineList.add(userOnlineService.selectOnlineByUserName(userName, user));
                }
            }
            else {
                onlineList.add(userOnlineService.loginUserToUserOnline(user));
            }
        }
        Collections.reverse(onlineList);
        onlineList.removeAll(Collections.singleton(null));
        return getDataTable(onlineList);
    }

    /**
     * 强退用户
     */
    @PreAuthorize("@ss.hasPermi('monitor:online:forceLogout')")
    @Log(title = "在线用户", businessType = BusinessType.DELETE)    // 日志
    @DeleteMapping("/{tokenId}")
    public AjaxResult forceLogout(@PathVariable String tokenId){
        redisCache.deleteObject(Constants.LOGIN_TOKEN_KEY + tokenId);
        return AjaxResult.success();
    }
}
