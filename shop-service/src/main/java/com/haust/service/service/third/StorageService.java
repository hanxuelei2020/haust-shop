package com.haust.service.service.third;

import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * 提供存储服务类，所有存储服务均由该类对外提供
 */
public interface StorageService {

	default String getActive() {
		return null;
	}

	default void setActive(String active) {

	}

	default Storage getStorage() {
		return null;
	}

	default void setStorage(Storage storage) {
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
	public String store(InputStream inputStream, long contentLength, String contentType, String fileName);


	public Stream<Path> loadAll();

	public Path load(String keyName);

	public Resource loadAsResource(String keyName);

	public void delete(String keyName);
}
