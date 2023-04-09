package com.haust.shop.product;


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
        "com.haust.shop.product"})
public class ProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }
}
