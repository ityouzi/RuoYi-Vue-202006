package com.ityouzi.framework.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @Auther: lizhen
 * @Date: 2020-06-16 19:22
 * @Description: 程序注解配置
 */
@Configuration
// 表示通过aop框架暴露该代理对象，AopContext能搞访问到
@EnableAspectJAutoProxy(exposeProxy = true)
// 指定要扫描的Mapper类的包的路径
@MapperScan("com.ityouzi.project.**.mapper")
public class ApplicationConfig {

}
