package com.ityouzi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @Auther: Liberal-World
 * @Date: 2020-06-10 14:10
 * @Description: 启动程序
 * @Version 1.0
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class RuoyiVueApplication {
    public static void main(String[] args) {
        SpringApplication.run(RuoyiVueApplication.class, args);
    }
}
