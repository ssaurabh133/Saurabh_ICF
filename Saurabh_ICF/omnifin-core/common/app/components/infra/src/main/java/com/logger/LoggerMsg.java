package com.logger;

import org.apache.log4j.Logger;

public class LoggerMsg {
	
	private static Logger log = Logger.getLogger(LoggerMsg.class);

	public static void info(String message) {
		log.info(message);
	}

	public static void debug(String message) {
		log.debug(message);
	}

	public static void error(String message) {
		log.error(message);
	}

}
