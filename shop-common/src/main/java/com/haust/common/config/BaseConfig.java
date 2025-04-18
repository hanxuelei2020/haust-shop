package com.haust.common.config;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 配置基类，该类实际持有所有的配置，子类只是提供代理访问方法
 */
public abstract class BaseConfig {

	// 所有的配置均保存在该 HashMap 中
	protected static Map<String, String> configs = new HashMap<>();

	/**
	 * 添加配置到公共Map中
	 *
	 * @param key
	 * @param value
	 */
	public static void addConfig(String key, String value) {
		configs.put(key, value);
	}

	/**
	 * 按String类型获取配置值
	 *
	 * @param keyName
	 * @return
	 */
	protected String getConfig(String keyName) {
		return configs.get(keyName);
	}


	/**
	 * 以BigDecimal类型获取配置值
	 *
	 * @param keyName
	 * @return
	 */
	protected BigDecimal getConfigBigDec(String keyName) {
		return new BigDecimal(configs.get(keyName));
	}

	/**
	 * 子类实现该方法，并告知父类配置前缀，该前缀用来索引配置组用于简化访问和按组重读配置
	 *
	 * @return
	 */
	abstract String getPrefix();
}
