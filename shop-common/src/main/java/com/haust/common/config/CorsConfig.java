package com.haust.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 这里是控制cors的，即关于跨域请求的配置
 * 因为ajax请求，如果访问的是本地的，会先给后端发送一个跨域请求码，如果跨域请求码成功之后，才会继续发送请求。
 */
@Configuration
public class CorsConfig {
	private CorsConfiguration buildConfig() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.addAllowedOrigin("*"); // 1 设置访问源地址
		corsConfiguration.addAllowedHeader("*"); // 2 设置访问源请求头
		corsConfiguration.addAllowedMethod("*"); // 3 设置访问源请求方法
		return corsConfiguration;
	}

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", buildConfig()); // 4 对接口配置跨域设置
		return new CorsFilter(source);
	}
}