package com.ls.application.fund_strategy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author ls
 * @date 2021/12/22
 * @desc
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.ls.*"})
@MapperScan(basePackages = {"com.ls.*.*.mapper"})
public class FundStrategyApplication {
    public static void main(String[] args) {
        SpringApplication.run(FundStrategyApplication.class,args);
    }
}
