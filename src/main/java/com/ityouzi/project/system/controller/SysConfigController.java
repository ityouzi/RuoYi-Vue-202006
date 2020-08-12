package com.ityouzi.project.system.controller;

import com.ityouzi.framework.web.controller.BaseController;
import com.ityouzi.framework.web.domain.AjaxResult;
import com.ityouzi.project.system.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: lizhen
 * @Date: 2020-06-29 09:48
 * @Description: 参数配置，信息操作处理
 */
@RestController
@RequestMapping("/system/config")
public class SysConfigController extends BaseController {

    @Autowired
    private ISysConfigService configService;


    /**
     * 根据参数键名查询参数值
     */
    @GetMapping(value = "/configKey/{configKey}")
    public AjaxResult getConfigKey(@PathVariable String configKey){
        return AjaxResult.success(configService.selectConfigByKey(configKey));
    }

}
