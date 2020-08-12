package com.ityouzi.framework.config;

import com.ityouzi.common.constant.Constants;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Auther: lizhen
 * @Date: 2020-06-28 15:24
 * @Description: 通用配置
 *
 */
@Configuration
public class ResourcesConfig implements WebMvcConfigurer {

    
    
    /**
     * 重写本地资源处理方法
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        /** 本地文件上传路径,处理显示本地图片方法 */
        registry.addResourceHandler(Constants.RESOURCE_PREFIX + "/**")
                .addResourceLocations("file:" + RuoYiConfig.getProfile() + "/");

    }


}
