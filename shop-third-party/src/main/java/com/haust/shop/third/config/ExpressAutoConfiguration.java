package com.haust.shop.third.config;

import com.haust.shop.third.service.impl.ExpressServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 是否接入第三方的接口，即快递查询之类的
 */
@Configuration
@EnableConfigurationProperties(ExpressProperties.class)
public class ExpressAutoConfiguration {

	@Autowired
	private ExpressProperties properties;

	public ExpressAutoConfiguration(ExpressProperties properties) {
		this.properties = properties;
	}

	@Bean
	public ExpressServiceImpl expressService() {
		ExpressServiceImpl expressService = new ExpressServiceImpl();
		expressService.setProperties(properties);
		return expressService;
	}

}
