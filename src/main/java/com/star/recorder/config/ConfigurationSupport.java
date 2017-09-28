package com.star.recorder.config;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author 测试仔刘毅
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.swing.JTextArea;

import com.star.recorder.utils.LoggerUtils;

public class ConfigurationSupport {

	private Properties property = new Properties();
	private String fileName;

	/**
	 * construct with parameter intialize
	 * 
	 * @param fileName
	 *            whole path and name of config file
	 */
	public ConfigurationSupport(String fileName) {
		this.setFileName(fileName);
	}

	/**
	 * construct with no parameter *
	 */
	public ConfigurationSupport() {
	}

	/**
	 * Description: get the Properties object.
	 * 
	 * @return the Properties object.
	 */
	public Properties getProperties() {
		return this.property;
	}

	/**
	 * Description: get the properties key to a list.
	 * 
	 * @return the ArrayList contains property key names.
	 */
	public ArrayList<String> getPropertiesKey(Properties property) {
		ArrayList<String> keyList = new ArrayList<String>();
		Iterator<Object> it = property.keySet().iterator();
		while (it.hasNext()) {
			keyList.add(it.next().toString());
		}
		return keyList;
	}

	/**
	 * get specified key in config files
	 * 
	 * @param keyName
	 *            the key name to get value
	 */
	public String get(String keyName) {
		try {
			property.load(this.getClass().getResourceAsStream(fileName));
		} catch (IOException ioe) {
			LoggerUtils.error(ioe);
			throw new RuntimeException(ioe);
		}
		String keyValue = null;
		if (property.containsKey(keyName)) {
			keyValue = (String) property.get(keyName);
		}
		return keyValue;
	}

	/**
	 * Description: get the txt file content.
	 * 
	 * @return the txt file content list.
	 */

	public ArrayList<String> readTextToList(String fileName) {
		String line = null;
		BufferedReader br = null;
		ArrayList<String> list = new ArrayList<String>();

		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
			while (br.ready() && (line = br.readLine()) != null) {
				list.add(line);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * Description: add user defined config strings into Java text area.
	 */
	public void fillStringToForms(JTextArea tArea, String fileName) throws Exception {
		List<String> configs = readTextToList(fileName);

		for (int i = 0; i < configs.size(); i++) {
			if (i == configs.size() - 1) {
				tArea.append(configs.get(i));
			} else {
				tArea.append(configs.get(i) + "\r\n");
			}
		}
	}

	/**
	 * Description: set file name to parse properties.
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}