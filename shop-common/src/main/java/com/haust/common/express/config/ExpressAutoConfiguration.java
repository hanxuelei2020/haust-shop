package com.haust.common.express.config;

import com.haust.common.express.ExpressService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 是否接入第三方的接口，即快递查询之类的
 */
@Configuration
@EnableConfigurationProperties(ExpressProperties.class)
public class ExpressAutoConfiguration {

	private final ExpressProperties properties;

	public ExpressAutoConfiguration(ExpressProperties properties) {
		this.properties = properties;
	}

	@Bean
	public ExpressService expressService() {
		ExpressService expressService = new ExpressService();
		expressService.setProperties(properties);
		return expressService;
	}

}
