package com.ityouzi.framework.config.properties;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: lizhen
 * @Date: 2020-06-16 18:17
 * @Description: druid 配置属性
 */


@Configuration
public class DruidProperties {

    /** 初始连接数 */
    @Value("${spring.datasource.druid.initialSize}")
    private int initialSize;

    /** 最小连接池数量 */
    @Value("${spring.datasource.druid.minIdle}")
    private int minIdle;
    
    /** 最大连接池数量 */
    @Value("${spring.datasource.druid.maxActive}")
    private int maxActive;

    /** 配置获取连接等待超时的时间 */
    @Value("${spring.datasource.druid.maxWait}")
    private int maxWait;

    /** 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒 */    
    @Value("${spring.datasource.druid.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;
    
    /** 配置一个连接在池中最小生存的时间，单位是毫秒 */
    @Value("${spring.datasource.druid.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;
    
    /** 配置一个连接在池中最大生存的时间，单位是毫秒 */
    @Value("${spring.datasource.druid.maxEvictableIdleTimeMillis}")
    private int maxEvictableIdleTimeMillis;

    /** 配置检测连接是否有效 */
    @Value("${spring.datasource.druid.validationQuery}")
    private String validationQuery;

    @Value("${spring.datasource.druid.testWhileIdle}")
    private boolean testWhileIdle;

    @Value("${spring.datasource.druid.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${spring.datasource.druid.testOnReturn}")
    private boolean testOnReturn;
    
        
    public DruidDataSource dataSource(DruidDataSource dataSource){
        /** 配置初始化大小、最小、最大 */
        dataSource.setInitialSize(initialSize);
        dataSource.setMaxActive(maxActive);
        dataSource.setMinIdle(minIdle);
        
        /** 配置获取连接等待超时的时间 */
        dataSource.setMaxWait(maxWait);
        
        /** 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒 */
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        
        /** 配置一个连接在池中最小、最大生存的时间，单位是毫秒 */    
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        dataSource.setMaxEvictableIdleTimeMillis(maxEvictableIdleTimeMillis);    
        
        /**
         * 用来检测连接是否有效的sql，要求是一个查询语句，常用select 'x'。
         * 如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会起作用。
         */
        dataSource.setValidationQuery(validationQuery);
        
        /** 建议配置为true，不影响性能，并且保证安全性。申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。 */
        dataSource.setTestWhileIdle(testWhileIdle);
        /** 申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。 */
        dataSource.setTestOnBorrow(testOnBorrow);
        /** 归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。 */
        dataSource.setTestOnReturn(testOnReturn);
        return dataSource;
    }




}
