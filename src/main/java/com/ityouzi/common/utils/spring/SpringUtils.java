package com.ityouzi.common.utils.spring;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * @Auther: Liberal-World
 * @Date: 2020-06-14 14:12
 * @Description: spring 工具类， 方便在 非 spring 管理环境中获取bean
 * @Version 1.0
 */

@Component
public class SpringUtils implements BeanFactoryPostProcessor {


    /** spring 应用上下文环境 */
    private static ConfigurableListableBeanFactory beanFactory;


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        SpringUtils.beanFactory = beanFactory;
    }


    /**
     * 获取对象
     *
     * @param name
     * @return Object 一个以所给名字注册的bean的实例
     * @throws org.springframework.beans.BeansException
     *
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException{
        return (T) beanFactory.getBean(name);
    }


    public static <T> T getBean(Class<T> clz) throws BeansException{
        T result = beanFactory.getBean(clz);
        return result;
    }

    /**
     * 获取aop代理对象
     *
     * @param invoker
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getAopProxy(T invoker)
    {
        return (T) AopContext.currentProxy();
    }

}
