package com.haust.shop.third.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * 是否接入第三方的接口，即快递查询之类的
 */
@ConfigurationProperties(prefix = "dts.express")
@EnableConfigurationProperties(ExpressProperties.class)
public class ExpressProperties {
	private boolean enable;
	private String appId;
	private String appKey;
	private List<Map<String, String>> vendors = new ArrayList<>();

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public List<Map<String, String>> getVendors() {
		return vendors;
	}

	public void setVendors(List<Map<String, String>> vendors) {
		this.vendors = vendors;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}
}
