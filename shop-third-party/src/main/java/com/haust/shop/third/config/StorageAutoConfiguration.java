package com.haust.shop.third.config;

import com.haust.service.service.third.StorageService;
import com.haust.shop.third.service.storage.*;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(StorageProperties.class)
public class StorageAutoConfiguration {

	@Autowired
	private StorageProperties properties;

	public StorageAutoConfiguration(StorageProperties properties) {
		properties = properties;
	}

	@Bean
	@DubboService
	public StorageService storageService() {
		StorageService storageService = new StorageServiceImpl();
		String active = properties.getActive();
		storageService.setActive(active);
		if (active.equals("local")) {
			storageService.setStorage(localStorage());
		} else if (active.equals("aliyun")) {
			storageService.setStorage(aliyunStorage());
		} else if (active.equals("tencent")) {
			storageService.setStorage(tencentStorage());
		} else if (active.equals("qiniu")) {
			storageService.setStorage(qiniuStorage());
		} else {
			throw new RuntimeException("当前存储模式 " + active + " 不支持");
		}

		return storageService;
	}

	@Bean
	@ConditionalOnProperty(
			prefix = "dts.storage",
			name = {"active"},
			havingValue = "local",
			matchIfMissing = false)
	@DubboService
	public LocalStorage localStorage() {
		LocalStorage localStorage = new LocalStorage();
		StorageProperties.Local local = properties.getLocal();
		localStorage.setAddress(local.getAddress());
		localStorage.setStoragePath(local.getStoragePath());
		return localStorage;
	}

	@Bean
	@ConditionalOnProperty(
			prefix = "dts.storage",
			name = {"active"},
			havingValue = "aliyun",
			matchIfMissing = false)
	@DubboService
	public AliyunStorage aliyunStorage() {
		AliyunStorage aliyunStorage = new AliyunStorage();
		StorageProperties.Aliyun aliyun = properties.getAliyun();
		aliyunStorage.setAccessKeyId(aliyun.getAccessKeyId());
		aliyunStorage.setAccessKeySecret(aliyun.getAccessKeySecret());
		aliyunStorage.setBucketName(aliyun.getBucketName());
		aliyunStorage.setEndpoint(aliyun.getEndpoint());
		return aliyunStorage;
	}

	@Bean
	@ConditionalOnProperty(
			prefix = "dts.storage",
			name = {"active"},
			havingValue = "tencent",
			matchIfMissing = false)
	@DubboService
	public TencentStorage tencentStorage() {
		TencentStorage tencentStorage = new TencentStorage();
		StorageProperties.Tencent tencent = properties.getTencent();
		tencentStorage.setSecretId(tencent.getSecretId());
		tencentStorage.setSecretKey(tencent.getSecretKey());
		tencentStorage.setBucketName(tencent.getBucketName());
		tencentStorage.setRegion(tencent.getRegion());
		return tencentStorage;
	}

	@Bean
	@ConditionalOnProperty(
			prefix = "dts.storage",
			name = {"active"},
			havingValue = "qiniu",
			matchIfMissing = false)
	@DubboService
	public QiniuStorage qiniuStorage() {
		QiniuStorage qiniuStorage = new QiniuStorage();
		StorageProperties.Qiniu qiniu = properties.getQiniu();
		qiniuStorage.setAccessKey(qiniu.getAccessKey());
		qiniuStorage.setSecretKey(qiniu.getSecretKey());
		qiniuStorage.setBucketName(qiniu.getBucketName());
		qiniuStorage.setEndpoint(qiniu.getEndpoint());
		return qiniuStorage;
	}
}
