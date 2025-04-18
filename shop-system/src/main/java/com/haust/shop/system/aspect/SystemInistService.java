package com.haust.shop.system.aspect;

import com.haust.common.config.SystemConfig;
import com.haust.common.util.SystemInfoPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 系统启动服务，用于检查系统状态及打印系统信息
 */

@SuppressWarnings("unused")
@Component
class SystemInistService {
	private SystemInistService systemInistService;

	@Autowired
	private ConfigService configService;

	@Autowired
	private Environment environment;

	@Autowired
	private SystemConfig systemConfig;
	@PostConstruct
	private void inist() {
		systemInistService = this;

		try {
			SystemInfoPrinter.printInfo("dts 初始化信息", getSystemInfo());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Map<String, String> getSystemInfo() {

		Map<String, String> infos = new LinkedHashMap<>();

		infos.put(SystemInfoPrinter.CREATE_PART_COPPER + 0, "系统信息");
		// 测试获取application-db.yml配置信息
		infos.put("服务器端口", environment.getProperty("server.port"));
		infos.put("数据库USER", environment.getProperty("spring.datasource.druid.username"));
		infos.put("数据库地址", environment.getProperty("spring.datasource.druid.url"));
		infos.put("调试级别", environment.getProperty("logging.level.com.haust.shop.wx"));

		// 测试获取application-core.yml配置信息
		infos.put(SystemInfoPrinter.CREATE_PART_COPPER + 1, "模块状态");
		infos.put("邮件", environment.getProperty("dts.notify.mail.enable"));
		infos.put("短信", environment.getProperty("dts.notify.sms.enable"));
		infos.put("模版消息", environment.getProperty("dts.notify.wx.enable"));
		infos.put("快递信息", environment.getProperty("dts.express.enable"));
		infos.put("快递鸟ID", environment.getProperty("dts.express.appId"));
		infos.put("对象存储", environment.getProperty("dts.storage.active"));
		infos.put("本地对象存储路径", environment.getProperty("dts.storage.local.storagePath"));
		infos.put("本地对象访问地址", environment.getProperty("dts.storage.local.address"));
		infos.put("本地对象访问端口", environment.getProperty("dts.storage.local.port"));

		// 微信相关信息,屏蔽打印控制台
		/*
		 * infos.put(SystemInfoPrinter.CREATE_PART_COPPER + 2, "微信相关");
		 * infos.put("微信APP KEY", environment.getProperty("dts.wx.app-id"));
		 * infos.put("微信APP-SECRET", environment.getProperty("dts.wx.app-secret"));
		 * infos.put("微信支付MCH-ID", environment.getProperty("dts.wx.mch-id"));
		 * infos.put("微信支付MCH-KEY", environment.getProperty("dts.wx.mch-key"));
		 * infos.put("微信支付通知地址", environment.getProperty("dts.wx.notify-url"));
		 */

		// 测试获取System表配置信息
		infos.put(SystemInfoPrinter.CREATE_PART_COPPER + 3, "系统设置");
		infos.put("自动创建朋友圈分享图", Boolean.toString(systemConfig.isAutoCreateShareImage()));
		infos.put("商场名称", systemConfig.getMallName());
		infos.put("首页显示记录数：NEW,HOT,BRAND,TOPIC,CatlogList,CatlogMore",
				systemConfig.getNewLimit() + "," + systemConfig.getHotLimit() + "," + systemConfig.getBrandLimit() + ","
						+ systemConfig.getTopicLimit() + "," + systemConfig.getCatlogListLimit() + ","
						+ systemConfig.getCatlogMoreLimit());

		return infos;
	}
}
