package com.haust.shop.system.aspect;

import com.haust.common.config.BaseConfig;
import com.haust.common.config.RedisPrefix;
import com.haust.common.service.CacheService;
import com.haust.shop.system.model.DtsSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 该类用于自动初始化数据库配置到BaseConfig中，以便BaseConfig的子类调用
 */
@Component
public class ConfigService {
	private static ConfigService systemConfigService;
	@Autowired
	private DtsSystemConfigService dtsSystemConfigService;

	// 将数据存储进redis
	@Autowired
	private CacheService cacheService;

	// 不允许实例化
	private ConfigService() {

	}

	public static ConfigService getSystemConfigService() {
		return systemConfigService;
	}

	@PostConstruct
	public void inist() {
		systemConfigService = this;
		systemConfigService.inistConfigs();
	}

	/**
	 * 根据 prefix 重置该 prefix 下所有设置
	 *
	 * @param prefix
	 */
	public void reloadConfig(String prefix) {
		List<DtsSystem> list = dtsSystemConfigService.queryAll();
		for (DtsSystem item : list) {
			// 符合条件，添加
			if (item.getKeyName().startsWith(prefix))
				BaseConfig.addConfig(item.getKeyName(), item.getKeyValue());
		}
	}

	/**
	 * 读取全部配置
	 */
	private void inistConfigs() {
		// 构建 map
		Map<String, String> map = dtsSystemConfigService.queryAll().stream().filter(Objects::nonNull)
				.collect(Collectors.toMap(DtsSystem::getKeyName, DtsSystem::getKeyValue));
		cacheService.hMset(RedisPrefix.SYSTEM_CONFIG, map).block();
	}
}