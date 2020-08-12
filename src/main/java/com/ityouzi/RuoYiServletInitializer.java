package com.ityouzi;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @Auther: lizhen
 * @Date: 2020-06-15 13:42
 * @Description: web容器中进行部署
 */
public class RuoYiServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
        return application.sources(RuoyiVueApplication.class);
    }
}
