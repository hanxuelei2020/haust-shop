package com.haust.shop.admin;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = { "com.haust.common",
		"com.haust.shop.admin" })
@MapperScan({"com.haust.common",
		"com.haust.shop.admin"})
@EnableTransactionManagement
@EnableScheduling
@EnableDubbo
public class AdminRpcApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminRpcApplication.class, args);
	}

}