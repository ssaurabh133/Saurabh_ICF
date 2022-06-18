package com.payout.adapter;

import java.io.File;

import org.apache.log4j.Logger;



/**
 * This class is responsible for handling of system level properties set during run time
 * @author Ritesh Srivastava
 * @created 23-Nov-2011, 8:07:18 PM	
 */
public class PropertyHome {

	static final String APP_DIR = "INFODART_APP_HOME";
	static final String DB_SERVER = "INFODART_DB_SERVER";
	private static final Logger log = Logger.getLogger(PropertyHome.class.getName());
	
	/**
	 * @return resource URL as a String 
	 */
	public static File getAppHome() {
		
		System.setProperty("INFODART_APP_HOME", "C:\\Payout_File");
		String appDirPath = System.getProperty(APP_DIR);
		//System.out.println(appDirPath+"**************appDirPath**************");
		File file = new File(appDirPath);
		if(file.isDirectory()){
			return file;
		}
		log.info(APP_DIR +"is not a directory");
		return null;			
	}
	public static String getDatabaseType() {
		String dB_Server = System.getProperty(DB_SERVER);
		if(dB_Server==null){
			log.info("database argument is not provided");
		}
		return dB_Server;			
	}
	
}
