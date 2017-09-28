package com.star.recorder.config;

/**
 * 说明：
 * 使用Properties加载配置文件，解析指定的key值返回
 * 
 * @author 测试仔刘毅
 **/

import java.io.File;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ParseProperties {

	private File file;
	Properties properties;
	private String fileName = "config/record_config.properties";

	/**
	 * @param configFile
	 *            whole path and name of config file
	 * @throws RuntimeException
	 **/
	public ParseProperties(String configFile) {
		if (configFile == null) {
			throw new RuntimeException("the parameter can not be null!");
		}

		this.fileName = configFile;
		this.file = new File("./" + fileName);
		this.properties = new Properties();
	}

	/**
	 * get specified key in config files
	 * 
	 * @param keyName
	 *            the key name to get value
	 * @throws RuntimeException
	 **/
	public String get(String keyName) {
		try {
			if (!file.exists()) {
				throw new FileNotFoundException("the config file [" + fileName + "] not exist!");
			}
			properties.load(new FileInputStream(file));
		} catch (Exception e) {
			properties.clear();
			throw new RuntimeException("load properties failed:" + e.getMessage());
		}
		if (properties.containsKey(keyName)) {
			return (String) properties.get(keyName);
		}
		return null;
	}
}