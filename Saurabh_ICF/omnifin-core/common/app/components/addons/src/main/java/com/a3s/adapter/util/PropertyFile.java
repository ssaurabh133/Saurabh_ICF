package com.a3s.adapter.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.a3s.ExportImportTool;

public class PropertyFile extends Properties {
	public static final String PROP_TASKEXECUTER = "TASKEXECUTER";
	public static final String PROP_PREHANDLER = "PREHANDLER";
	public static final String PROP_KETTLE = "ktlfile";
	public static final String PROP_INPUT_FILE_LOCATION = "INPUT_FILE_LOCATION";
	public static final String PROP_XML_OUTPUT_FILE_DIR = "XML_OUTPUT_FILE_DIR";
	public static final String PROP_PROCESSING_DATABASE_HOST = "PROCESSING_DATABASE_HOST";
	public static final String PROP_PROCESSING_DATABASE_PORT = "PROCESSING_DATABASE_PORT";
	public static final String PROP_PROCESSING_DATABASE_NAME = "PROCESSING_DATABASE_NAME";
	public static final String PROP_PROCESSING_DATABASE_USER = "PROCESSING_DATABASE_USER";
	public static final String PROP_PROCESSING_DATABASE_PASSWORD = "PROCESSING_DATABASE_PASSWORD";
	public static final String PROP_JNDI_DATABASE_RESOURCE="JNDI_DATABASE_RESOURCE";
	public static final String PROP_CHECK_DATABASE = "CHECK_DATABASE";
	
	public PropertyFile() throws IOException {
		InputStream inputStream = ExportImportTool.class
				.getResourceAsStream("adaptercli.properties");
		load(inputStream);
	}

	public String getTaskExecuter() {
		return getProperty(PropertyFile.PROP_TASKEXECUTER);
	}

	public String getProcessingDatabaseHost(String databaseType) {
		return getProperty(databaseType.toUpperCase()+"."+PropertyFile.PROP_PROCESSING_DATABASE_HOST);
	}

	public String getProcessingDatabasePort(String databaseType) {
		return getProperty(databaseType.toUpperCase()+"."+PropertyFile.PROP_PROCESSING_DATABASE_PORT);
	}

	public String getProcessingDatabaseName(String databaseType) {
		return getProperty(databaseType.toUpperCase()+"."+PropertyFile.PROP_PROCESSING_DATABASE_NAME);
	}

	public String getProcessingDatabaseUser(String databaseType) {
		return getProperty(databaseType.toUpperCase()+"."+PropertyFile.PROP_PROCESSING_DATABASE_USER);
	}

	public String getProcessingDatabasePassword(String databaseType) {
		return getProperty(databaseType.toUpperCase()+"."+PropertyFile.PROP_PROCESSING_DATABASE_PASSWORD);
	}

	public String getPreHandler() {
		return getProperty(PropertyFile.PROP_PREHANDLER);
	}
	
	public String getJndiDataResource(){
		return getProperty(PropertyFile.PROP_JNDI_DATABASE_RESOURCE);
	}
	
	public String getCheckDatabase(){
		return getProperty(PropertyFile.PROP_CHECK_DATABASE);
	}
}
