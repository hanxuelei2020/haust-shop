package com.haust.common.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.WxMaConfig;
import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置微信小程序的参数
 */
@Configuration
public class WxConfig {
	@Autowired
	private WxProperties properties;

	@Bean
	public WxMaConfig wxMaConfig() {
		WxMaInMemoryConfig config = new WxMaInMemoryConfig();
		config.setAppid(properties.getAppId());
		config.setSecret(properties.getAppSecret());
		return config;
	}

	@Bean
	public WxMaService wxMaService(WxMaConfig maConfig) {
		WxMaService service = new WxMaServiceImpl();
		service.setWxMaConfig(maConfig);
		return service;
	}

	/**
	 * 因为这里没有支付，所以功能不采用微信官方的支付，这里模拟支付即可
	 * @return
	 * @// TODO: 2022/1/8  稍后可以删掉，或者进行过滤
	 */
	@Bean
	public WxPayConfig wxPayConfig() {
		WxPayConfig payConfig = new WxPayConfig();
		payConfig.setAppId(properties.getAppId());
		payConfig.setMchId(properties.getMchId());
		payConfig.setMchKey(properties.getMchKey());
		payConfig.setNotifyUrl(properties.getNotifyUrl());
		payConfig.setKeyPath(properties.getKeyPath());
		payConfig.setTradeType("JSAPI");
		payConfig.setSignType("MD5");
		return payConfig;
	}

	@Bean
	public WxPayService wxPayService(WxPayConfig payConfig) {
		WxPayService wxPayService = new WxPayServiceImpl();
		wxPayService.setConfig(payConfig);
		return wxPayService;
	}
}