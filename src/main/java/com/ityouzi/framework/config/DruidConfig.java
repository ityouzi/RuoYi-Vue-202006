package com.ityouzi.framework.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.ityouzi.framework.aspectj.lang.enums.DataSourceType;
import com.ityouzi.framework.config.properties.DruidProperties;
import com.ityouzi.framework.datasource.DynamicDataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: lizhen
 * @Date: 2020-06-16 18:15
 * @Description: druid 配置多数据源
 */
@Configuration
public class DruidConfig {

    /**
     * 主数据源
     */
    @Bean
    @ConfigurationProperties("spring.datasource.druid.master")
    public DataSource masterDataSource(DruidProperties druidProperties){
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        return druidProperties.dataSource(dataSource);
    }


    /**
     * 从数据源
     */
    @Bean
    @ConfigurationProperties("spring.datasource.druid.slave")
    @ConditionalOnProperty(prefix = "spring.datasource.druid.slave", name = "enabled", havingValue = "true")
    public DataSource slaveDataSource(DruidProperties druidProperties){
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        return druidProperties.dataSource(dataSource);
    }

    @Bean(name = "dynamicDataSource")
    @Primary
    public DynamicDataSource dataSource(DataSource masterDataSource, DataSource slaveDataSource){
        Map<Object, Object> targetDataSource = new HashMap<>();
        targetDataSource.put(DataSourceType.MASTER.name(), masterDataSource);
        targetDataSource.put(DataSourceType.SLAVE.name(),slaveDataSource);
        return new DynamicDataSource(masterDataSource, targetDataSource);
    }




}
