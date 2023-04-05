package com.haust.shop.wx.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.haust.shop.wx.annotation.support.LoginUserHandlerMethodArgumentResolver;

/** 
* @Description: sprig boot来配置webmvc的配置文件，将登录的参数校验器加载进来
* @Author: 韩雪磊
*/ 
@Configuration
public class WxWebMvcConfiguration implements WebMvcConfigurer {
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new LoginUserHandlerMethodArgumentResolver());
	}
}
