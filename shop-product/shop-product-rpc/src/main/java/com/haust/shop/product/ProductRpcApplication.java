package com.haust.shop.product;


import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
//扫描 common包
@ComponentScans({@ComponentScan({"com.haust.common",
        "com.haust.shop.product"})})
@MapperScan({"com.haust.common",
        "com.haust.shop.product.mapper"})
@EnableDubbo
public class ProductRpcApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductRpcApplication.class, args);
    }
}
