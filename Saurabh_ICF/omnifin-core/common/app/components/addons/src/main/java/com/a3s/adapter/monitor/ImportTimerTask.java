package com.a3s.adapter.monitor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import com.a3s.adapter.AbstractProtocolProvider;
import com.a3s.adapter.IDataFile;
import com.a3s.adapter.IProtocol;
import com.a3s.adapter.PostHandler;
import com.a3s.adapter.PreHandler;
import com.a3s.adapter.TaskExecutor;
import com.a3s.adapter.dto.Task;
import com.a3s.adapter.dto.TaskResult;
import com.a3s.adapter.exception.AdapterException;
import com.a3s.adapter.util.ConfigFile;
import com.infodart.adapter.conf.EntityType;
import com.infodart.adapter.conf.ExecutorType;
import com.infodart.adapter.conf.ProtocolType;

public class ImportTimerTask extends KettleTimerTask {
	public ImportTimerTask() {

	}

	@Override
	public void run() {
		ProtocolType protocolType = null;
		protocolType = findProtocol(ConfigFile.getInstance().getProtocol(
				getMonitor()));

		Map<String, String> params = prepareMap(protocolType);
		params.put(ConfigFile.PARAM_MONITOR, getMonitor());

		IProtocol protocolProvider = AbstractProtocolProvider
				.getProtocolProvider(
						ConfigFile.getInstance().getProtocol(getMonitor()),
						params);

		List<IDataFile> _allFiles = protocolProvider.getList();
		List<IDataFile> _filteredFiles = super.getElligibleFiles(_allFiles);
		for (Iterator<IDataFile> iterator = _filteredFiles.iterator(); iterator
				.hasNext();) {
			IDataFile _file = iterator.next();
			PreHandler preHandler;
			PostHandler postHandler;
			TaskExecutor taskExecutor;
			ExecutorType executorType = null;
			EntityType entity = null;

			try {
				entity = findEntity(_file.getName());
				if (entity == null) {
					continue;
				}
				log.info("\nprocessing file ------ [ "
						+ _file.getFile().getAbsolutePath() + " ]");
				executorType = findExecutor(entity.getExecutor());
				preHandler = (PreHandler) Class.forName(
						ConfigFile.getInstance().getPrehandler(getMonitor()))
						.newInstance();
				postHandler = (PostHandler) Class.forName(
						ConfigFile.getInstance().getPosthandler(getMonitor()))
						.newInstance();
				taskExecutor = (TaskExecutor) Class.forName(
						executorType.getClass1()).newInstance();
			} catch (Exception e) {
				RuntimeException _rte = new RuntimeException(
						"Pre-Post handlers could not be loaded.", e);
				log.error("Error Loading Task Handlers", _rte);
				// renameUnsupportedFile(_file);
				continue;
			}

			params = rePopulatePrepareMap(executorType, entity, _file);
			Task task = new Task(params);
			try {
				boolean isEligible = isFileEligible(_file);
				if (isEligible) {
					preHandler.handle(task);
					TaskResult taskResult = taskExecutor.execute(task);
					taskResult.setOutputFileName(_file.getName());
					postHandler.handle(task, taskResult);
				} else {
					continue;
				}
			} catch (AdapterException e) {
				log.error(e);
				throw new RuntimeException(e);
			}

		}
	}

	private boolean isFileEligible(IDataFile _file) {
		Connection conn = getDatabaseConnection();
		PreparedStatement preparedStatement = null;
		try {
			String status = "success";
			String query = "select * from fileimport where file_name=? and status=?";
			preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, _file.getName());
			preparedStatement.setString(2, status);
			ResultSet rs = preparedStatement.executeQuery();
			if (!rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	finally{
		try {
			preparedStatement.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		}
	}

	private Connection getDatabaseConnection() {
		
		Connection conn = null;
		String url = "jdbc:mysql://"
				+ params.get(ConfigFile.PARAM_PROCESSING_DATABASE_HOST) + ":"
				+ params.get(ConfigFile.PARAM_PROCESSING_DATABASE_PORT) + "/";
		String dbName = params.get(ConfigFile.PARAM_PROCESSING_DATABASE_NAME);
		String driver = "com.mysql.jdbc.Driver";
		String userName = params.get(ConfigFile.PARAM_PROCESSING_DATABASE_USER);
		String password = params
				.get(ConfigFile.PARAM_PROCESSING_DATABASE_PASSWORD);
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager
					.getConnection(url + dbName, userName, password);
					return conn;
		} catch (Exception e) {
			log.error("Database does not exist", e);
			throw new RuntimeException();
		}
		/*String lookup="java:posDataSource";
		try {
		
		    Context ctx1 = new InitialContext();
		 
		    DataSource obj = (DataSource) ctx1.lookup(lookup);
		    return obj.getConnection();
		} catch (Exception e) {
			System.out.println("********ritesh*************");
			log.error("Failed to lookup posDataSource connection", e);
			throw new RuntimeException(e);
		}*/
	}
}