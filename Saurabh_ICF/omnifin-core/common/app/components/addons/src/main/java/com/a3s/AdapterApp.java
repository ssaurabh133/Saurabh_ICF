package com.a3s;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This Main-Class responsible to recieve Config File,
 * and call ImportServiceManager to read the file.
 * 
 * @author Ritesh Srivastava
 * 
 */
public class AdapterApp {

	private static Log log = LogFactory.getLog(AdapterApp.class);

	public static void main(String[] args) {

		if (args.length < 1)
			log.error("Please provide the config file.");
		else
			new ImportServiceManager().sync(args);   
	}
}
