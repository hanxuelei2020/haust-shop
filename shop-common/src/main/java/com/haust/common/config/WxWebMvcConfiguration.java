package com.haust.common.config;

import com.haust.common.handler.LoginUserHandlerMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/** 
* @Description: sprig boot来配置webmvc的配置文件，将登录的参数校验器加载进来
* @Author: 韩雪磊
*/ 
@Configuration
public class WxWebMvcConfiguration implements WebMvcConfigurer {
	@Autowired
	private LoginUserHandlerMethodArgumentResolver resolver;
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(resolver);
	}
}
