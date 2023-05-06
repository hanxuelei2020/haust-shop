package com.haust.shop.order;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@EnableDubbo
//扫描 common包
@ComponentScans({@ComponentScan({"com.haust.common",
        "com.haust.shop.order"})})
@MapperScan({"com.haust.common",
        "com.haust.shop.order.mapper"})
public class OrderRpcApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderRpcApplication.class, args);
    }
}
