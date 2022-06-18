package com.a3s;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.a3s.adapter.PreHandler;
import com.a3s.adapter.TaskExecutor;
import com.a3s.adapter.dto.Task;
import com.a3s.adapter.exception.AdapterException;
import com.a3s.adapter.util.ConfigFile;
import com.a3s.adapter.util.PropertyFile;

public class ExportImportToolManager {
	private static Log log = LogFactory.getLog(ExportImportToolManager.class);
	protected static final String OPTION_IMPORT = "i";
	protected static final String OPTION_EXPORT = "e";
	protected static final String OPTION_ENTITY = "entity";
	protected static final String OPTION_INPUT_FILE_LOCATION = "inputfile";
	protected static final String OPTION_OUTPUT_FILE_LOCATION = "outputdir";
	protected static final String OPTION_DATASOURCE_TYPE = "databasetype";

	protected void processCommandLine(CommandLine commandLine, Options options)
			throws AdapterException {
		PropertyFile propertyFile;
		try {
			propertyFile = new PropertyFile();
		} catch (IOException e) {
			log.error("failed to load the adapterCLI property file", e);
			throw new AdapterException(e);
		}
		TaskExecutor taskExecutor;
		PreHandler preHandler;
		try {
			taskExecutor = (TaskExecutor) Class.forName(
					propertyFile.getTaskExecuter()).newInstance();
			preHandler = (PreHandler) Class.forName(
					propertyFile.getPreHandler()).newInstance();

		} catch (Exception e) {
			log.info("could not load and initialize the kettle task executer",
					e);
			throw new AdapterException(e);
		}

		Map<String, String> params = prepareParams(propertyFile);
		if (commandLine.getOptionValue(OPTION_ENTITY) == null
				|| commandLine.getOptionValue(OPTION_ENTITY).equalsIgnoreCase(
						"")) {
			printUsage(options);
			return;
		}
		if (commandLine.hasOption(OPTION_IMPORT)) {
			if (commandLine.getOptionValue(OPTION_INPUT_FILE_LOCATION) == null) {
				printUsage(options);
				return;
			}
			String ktlFile = params.get(commandLine.getOptionValue(
					OPTION_ENTITY).toUpperCase()
					+ ".IMPORT.ktlfile");
			if (ktlFile != null) {
				ktlFile = ConfigFile.replaceLocationPathVariable(ktlFile);
				params.put(PropertyFile.PROP_KETTLE, ktlFile);
			} else {
				log.error("ktl file for given enitity is not found");
				throw new AdapterException();
			}
			params.put(PropertyFile.PROP_INPUT_FILE_LOCATION,
					commandLine.getOptionValue(OPTION_INPUT_FILE_LOCATION));
		}
		if (commandLine.hasOption(OPTION_EXPORT)) {
			if (commandLine.getOptionValue(OPTION_OUTPUT_FILE_LOCATION) == null) {
				printUsage(options);
				return;
			}
			String ktlFile = params.get(commandLine.getOptionValue(
					OPTION_ENTITY).toUpperCase()
					+ ".EXPORT.ktlfile");
			if (ktlFile != null) {
				ktlFile = ConfigFile.replaceLocationPathVariable(ktlFile);
				params.put(PropertyFile.PROP_KETTLE, ktlFile);
			} else {
				log.error("ktl file for given enitity is not found");
				throw new AdapterException();
			}
			params.put(PropertyFile.PROP_XML_OUTPUT_FILE_DIR,
					commandLine.getOptionValue(OPTION_OUTPUT_FILE_LOCATION));
		}
		if (commandLine.hasOption(OPTION_DATASOURCE_TYPE)) {
			if (commandLine.getOptionValue(OPTION_DATASOURCE_TYPE) == null) {
				printUsage(options);
				return;
			}
			String databaseType = commandLine.getOptionValue(
					OPTION_DATASOURCE_TYPE).toUpperCase();
			
			Boolean checkDatabase = new Boolean(propertyFile.getCheckDatabase());
			if(checkDatabase)
				checkDataBase(commandLine, propertyFile, databaseType);
		}

		Task task = new Task(params);
		preHandler.handle(task);
		try {
			taskExecutor.execute(task);
		} catch (AdapterException e) {
			log.error(e);
			throw new AdapterException(e);
		}
	}

	private void checkDataBase(CommandLine commandLine,
			PropertyFile propertyFile, String databaseType)
			throws AdapterException {
		Connection conn = null;
		String driver;
		String userName;
		String dbName;
		String password;
		String url;

		if (databaseType.equalsIgnoreCase("MYSQL")) {
			url = "jdbc:mysql://" +propertyFile.getProcessingDatabaseHost(databaseType)
					+ ":" +propertyFile.getProcessingDatabasePort(databaseType) + "/";
			dbName = propertyFile.getProcessingDatabaseName(databaseType);
			userName = propertyFile.getProcessingDatabaseUser(databaseType);
			password = propertyFile.getProcessingDatabasePassword(databaseType);
			driver = "com.mysql.jdbc.Driver";
		} else if (databaseType.equalsIgnoreCase("MSSQL")) {
			url = "jdbc:sqlserver://"+propertyFile.getProcessingDatabaseHost(databaseType)
					+ ":" +propertyFile.getProcessingDatabasePort(databaseType) + "/";
			dbName = propertyFile.getProcessingDatabaseName(databaseType);
			userName = propertyFile.getProcessingDatabaseUser(databaseType);
			password = propertyFile.getProcessingDatabasePassword(databaseType);
			driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		} else {
			throw new AdapterException("The Databasetype is not valid");
		}
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager
					.getConnection(url + dbName, userName, password);
			System.out.println("Connected to the database");
		} catch (Exception e) {
			log.error("Database does not exist", e);
			throw new AdapterException(e);
		}
		try {
			String query = "Select count(*) from "
					+ commandLine.getOptionValue(OPTION_ENTITY).toLowerCase();
			Statement st = conn.createStatement();
			st.executeQuery(query);
			st.close();
		} catch (Exception e) {
			log.error(commandLine.getOptionValue(OPTION_ENTITY).toLowerCase()
					+ " table does not exist in the database", e);
			throw new AdapterException(e);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private Map<String, String> prepareParams(Properties properties) {
		Map<String, String> params = new HashMap<String, String>();
		Set<Object> keys;
		keys = properties.keySet();
		Iterator<Object> iterator = keys.iterator();
		while (iterator.hasNext()) {
			String key = iterator.next().toString();
			String value = properties.getProperty(key);
			value = ConfigFile.replaceLocationPathVariable(value);
			params.put(key, value);
		}
		return params;
	}

	private void printUsage(Options options) {
		HelpFormatter helpFormatter = new HelpFormatter();
		helpFormatter.printHelp("ExportImportToolManager", options, true);
	}
}
