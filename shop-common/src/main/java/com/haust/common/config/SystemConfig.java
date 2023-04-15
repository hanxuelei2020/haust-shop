package com.haust.common.config;

import com.haust.common.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * 系统设置,其他配置请参考该类的实现
 */
@Configuration
public class SystemConfig extends BaseConfig {

	private CacheService cacheService;

	public SystemConfig() {
	}

	public CacheService getCacheService() {
		return cacheService;
	}

	@Autowired
	public void setCacheService(CacheService cacheService) {
		this.cacheService = cacheService;
	}

	public SystemConfig(CacheService cacheService) {
		this.cacheService = cacheService;
	}

	public static final String PRE_FIX = "dts.system.";

	public Integer getNewLimit() {
		return getConfigInt(PRE_FIX + "indexlimit.new");
	}

	public Integer getHotLimit() {
		return getConfigInt(PRE_FIX + "indexlimit.hot");
	}

	public Integer getBrandLimit() {
		return getConfigInt(PRE_FIX + "indexlimit.brand");
	}

	public Integer getTopicLimit() {
		return getConfigInt(PRE_FIX + "indexlimit.topic");
	}

	public Integer getCatlogListLimit() {
		return getConfigInt(PRE_FIX + "indexlimit.catloglist");
	}

	public Integer getCatlogMoreLimit() {
		return getConfigInt(PRE_FIX + "indexlimit.catloggood");
	}

	public String getHotBannerTitle() {
		return getConfig(PRE_FIX + "banner.hot.title");
	}

	public String getNewBannerTitle() {
		return getConfig(PRE_FIX + "banner.new.title");
	}

	public String getHotImageUrl() {
		return getConfig(PRE_FIX + "banner.hot.imageurl");
	}

	public String getNewImageUrl() {
		return getConfig(PRE_FIX + "banner.new.imageurl");
	}

	public BigDecimal getFreight() {
		return getConfigBigDec(PRE_FIX + "freight.value");
	}

	public BigDecimal getFreightLimit() {
		return getConfigBigDec(PRE_FIX + "freight.limit");
	}

	public String getMallName() {
		return getConfig(PRE_FIX + "mallname");
	}

	public boolean isAutoCreateShareImage() {
		int autoCreate = getConfigInt(PRE_FIX + "shareimage.autocreate");
		return autoCreate == 0 ? false : true;
	}

	/**
	 * 是否为多订单模式 dts.system.multi.order.model 1表示是, 0 表示不拆单
	 * 
	 * @return
	 */
	public boolean isMultiOrderModel() {
		int multiOrderModel = getConfigInt(PRE_FIX + "multi.order.model");
		return multiOrderModel == 0 ? false : true;
	}

	public Integer getConfigInt(String key) {
		return Integer.parseInt(Objects.requireNonNull(cacheService.hGet(RedisPrefix.SYSTEM_CONFIG, key).block()));
	}

	@Override
	public String getPrefix() {
		return PRE_FIX;
	}
}