package com.haust.shop.third.service.storage;

import com.haust.common.util.CharUtil;
import com.haust.service.domain.third.DtsStorage;
import com.haust.service.service.third.DtsStorageService;
import com.haust.service.service.third.Storage;
import com.haust.service.service.third.StorageService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * 提供存储服务类，所有存储服务均由该类对外提供
 */
public class StorageServiceImpl implements StorageService {
	private String active;
	private Storage storage;
	@Autowired
	private DtsStorageService dtsStorageService;

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public Storage getStorage() {
		return storage;
	}

	public void setStorage(Storage storage) {
		this.storage = storage;
	}

	/**
	 * 存储一个文件对象
	 *
	 * @param inputStream
	 *            文件输入流
	 * @param contentLength
	 *            文件长度
	 * @param contentType
	 *            文件类型
	 * @param fileName
	 *            文件索引名
	 */
	public String store(InputStream inputStream, long contentLength, String contentType, String fileName) {
		String key = generateKey(fileName);
		storage.store(inputStream, contentLength, contentType, key);

		String url = generateUrl(key);
		DtsStorage storageInfo = new DtsStorage();
		storageInfo.setName(fileName);
		storageInfo.setSize((int) contentLength);
		storageInfo.setType(contentType);
		storageInfo.setKey(key);
		storageInfo.setUrl(url);
		dtsStorageService.add(storageInfo);

		return url;
	}

	private String generateKey(String originalFilename) {
		int index = originalFilename.lastIndexOf('.');
		String suffix = originalFilename.substring(index);

		String key = null;
		DtsStorage storageInfo = null;

		do {
			key = CharUtil.getRandomString(20) + suffix;
			storageInfo = dtsStorageService.findByKey(key);
		} while (storageInfo != null);

		return key;
	}

	public Stream<Path> loadAll() {
		return storage.loadAll();
	}

	public Path load(String keyName) {
		return storage.load(keyName);
	}

	public Resource loadAsResource(String keyName) {
		return storage.loadAsResource(keyName);
	}

	public void delete(String keyName) {
		storage.delete(keyName);
	}

	private String generateUrl(String keyName) {
		return storage.generateUrl(keyName);
	}
}
