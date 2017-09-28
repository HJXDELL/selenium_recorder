package com.star.recorder.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtils {

	public static Logger getLogger() {
		Logger logger = LoggerFactory.getLogger(getClassName());
		return logger;
	}

	public static void debug(Object msg) {
		Logger logger = LoggerFactory.getLogger(getClassName());
		logger.debug("Thread ID - " + Thread.currentThread().getId() + " - {}", msg);
	}
	
	public static void info(Object msg) {
		Logger logger = LoggerFactory.getLogger(getClassName());
		logger.info("Thread ID - " + Thread.currentThread().getId() + " - {}", msg);
	}

	public static void warn(Object msg) {
		Logger logger = LoggerFactory.getLogger(getClassName());
		logger.warn("Thread ID - " + Thread.currentThread().getId() + " - {}", msg);
	}

	public static void error(Object msg) {
		Logger logger = LoggerFactory.getLogger(getClassName());
		logger.error("Thread ID - " + Thread.currentThread().getId() + " - {}", msg);
	}

	public static void debug(String agr1, Object msg) {
		Logger logger = LoggerFactory.getLogger(getClassName());
		logger.debug("Thread ID - " + Thread.currentThread().getId() + " - {}{}", agr1, msg);
	}
	
	public static void info(String agr1, Object msg) {
		Logger logger = LoggerFactory.getLogger(getClassName());
		logger.info("Thread ID - " + Thread.currentThread().getId() + " - {}{}", agr1, msg);
	}

	public static void warn(String agr1, Object msg) {
		Logger logger = LoggerFactory.getLogger(getClassName());
		logger.warn("Thread ID - " + Thread.currentThread().getId() + " - {}{}", agr1, msg);
	}

	public static void error(String agr1, Object msg) {
		Logger logger = LoggerFactory.getLogger(getClassName());
		logger.error("Thread ID - " + Thread.currentThread().getId() + " - {}{}", agr1, msg);
	}

	/**
	 * @return
	 */
	private static String getClassName(){
		StackTraceElement stack[] = (new Throwable()).getStackTrace();
		String clsName = stack[2].getClassName();
		return clsName;
	}
}
