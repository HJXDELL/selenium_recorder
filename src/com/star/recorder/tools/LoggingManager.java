package com.star.recorder.tools;

/**
 * 设计说明：
 * 1、根据配置的log4j.properties（必须打在jar包中或者放在bin目录下方可生效）打印日志；
 * 2、可直接记录Throwable、string，String记录分为info和error；
 * 3、该日志记录建议只用作框架运行信息记录，而不要直接应用于系统测试运行。
 * 
 * @author 测试仔刘毅
 **/

import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LoggingManager {

	private String className;
	private Properties property = new Properties();
	private Logger logger = null;
	private final String configSource = "/com/star/tools/log4j.properties";

	/**
	 * construct with set class name parameter.
	 * 
	 * @param clsName the name of your runtime class to be logged
	 * @throws RuntimeException
	 **/
	public LoggingManager(String clsName) {
		this.className = clsName;
	}

	/**
	 * record error info.
	 * 
	 * @param t Throwable:Exceptions and Errors
	 * @param userText user defined message to record
	 * @throws RuntimeException
	 **/
	public void error(Throwable t, String userText) {
		try {
			property.load(this.getClass().getResourceAsStream(configSource));
			PropertyConfigurator.configure(property);
			logger = Logger.getLogger("message");
			logger.info("#################################################################");
			logger.error(className + ":" + userText, t);
			logger.info("#################################################################\n");
		} catch (Exception ie) {
			throw new RuntimeException("can not load log4j.properties:" + ie.getMessage());
		}
	}

	/**
	 * orverride the error method with default user text null.
	 * 
	 * @param t Throwable:Exceptions and Errors
	 **/
	public void error(Throwable t) {
		error(t, null);
	}

	/**
	 * orverride the error method.
	 * 
	 * @param text user defined message to record
	 * @throws RuntimeException
	 **/
	public void error(String text) {
		try {
			property.load(this.getClass().getResourceAsStream(configSource));
			PropertyConfigurator.configure(property);
			logger = Logger.getLogger("message");
			logger.info("#################################################################");
			logger.info(className + ": ");
			logger.error(text);
			logger.info("#################################################################\n");
		} catch (Exception ie) {
			throw new RuntimeException("can not load log4j.properties:" + ie.getMessage());
		}
	}

	/**
	 * record user defined info message.
	 * 
	 * @param text user defined message to record
	 * @throws RuntimeException
	 **/
	public void info(String text) {
		try {
			property.load(this.getClass().getResourceAsStream(configSource));
			PropertyConfigurator.configure(property);
			logger = Logger.getLogger("message");
			logger.info(className + ": " + text + "\n");
		} catch (Exception ie) {
			throw new RuntimeException("can not load log4j.properties:" + ie.getMessage());
		}
	}
}