package com.haust.shop.wx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 服务启动类
 * 
 * @author 韩雪磊
 * @QQ:2407411399
 */
@SpringBootApplication(scanBasePackages = { "com.haust.shop.db", "com.haust.shop.core",
		"com.haust.shop.wx" })
@MapperScan({ "com.haust.shop.db.dao", "com.haust.shop.db.dao.ex" })
@EnableTransactionManagement
@EnableScheduling
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}