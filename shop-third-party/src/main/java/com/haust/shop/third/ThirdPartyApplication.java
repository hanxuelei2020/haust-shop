package com.haust.shop.third;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication(proxyBeanMethods = false)
@ComponentScans({@ComponentScan({"com.haust.common",
        "com.haust.shop.third"})})
@MapperScan({"com.haust.common",
        "com.haust.shop.third"})
@EnableDubbo
public class ThirdPartyApplication {
    public static void main(String[] args) {
        SpringApplication.run(ThirdPartyApplication.class, args);
    }
}