package com.haust.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 这里是控制cors的，即关于跨域请求的配置
 * 因为ajax请求，如果访问的是本地的，会先给后端发送一个跨域请求码，如果跨域请求码成功之后，才会继续发送请求。
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
	@Override
	public void addCorsMappings(CorsRegistry registry){
		//设置允许跨域的路径
		registry.addMapping ("/**")
				//设置允许跨域请求的域名
				.allowedOriginPatterns ("*")
				//是否允许证书
				.allowCredentials (true)
				//设置允许的方法
				.allowedMethods ("*")
				//设置允许的header属性
				.allowedHeaders ("*")
				//允许跨域时间
				.maxAge (3600);
	}
}
