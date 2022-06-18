package com.a3s.adapter.providers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import com.a3s.adapter.AbstractProtocolProvider;
import com.a3s.adapter.IDataFile;
import com.a3s.adapter.util.ConfigFile;

/**
 * This class responsible to read the files located on remote location.
 * 
 * @author Ritesh Srivastava
 * 
 */
public class FtpProtocolProvider extends AbstractProtocolProvider {

	private String remoteFolder;
	private String archiveFolder;
	private String tempDir;
	private String server;
	private String username;
	private String password;
	private String port;
	private Map<String, String> map;

	public FtpProtocolProvider(Map<String, String> map) {
		this.map = map;
		this.setValues();
	}

	private void setValues() {
		Set paramsSet = map.entrySet();
		Iterator iterator = paramsSet.iterator();
		remoteFolder = ConfigFile.getInstance().getInputpath(
				map.get(ConfigFile.PARAM_MONITOR));
		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry) iterator.next();
			if (entry.getKey().equals(ConfigFile.PARAM_ARCHIVE_DIR))
				archiveFolder = entry.getValue();
			if (entry.getKey().equals(ConfigFile.PARAM_TEMPDIR))
				tempDir = entry.getValue();
			if (entry.getKey().equals(ConfigFile.PARAM_IP))
				server = entry.getValue();
			if (entry.getKey().equals(ConfigFile.PARAM_USERNAME))
				username = entry.getValue();
			if (entry.getKey().equals(ConfigFile.PARAM_PASSWORD))
				password = entry.getValue();
			if (entry.getKey().equals(ConfigFile.PARAM_PORT))
				port = entry.getValue();
		}

	}

	/*
	 * This method is used to get the list of Files located at remote location.
	 */
	FTPFile[] _files = null;

	public List<IDataFile> getList() {

		FtpConnection ftpconn = new FtpConnection();
		FTPClient ftpClient = ftpconn.getFTPConnection(server,
				Integer.parseInt(port), username, password);

		try {
			try {
				_files = ftpClient.listFiles(this.remoteFolder);

			} catch (IOException e) {
				e.printStackTrace();
			}

			List<IDataFile> _dataFiles = new ArrayList<IDataFile>();
			if (_files == null) {
				// log.info("No such file to process...");
			} else {
				for (FTPFile _afile : _files) {
					_dataFiles.add(new FtpDataFile(_afile, archiveFolder,
							tempDir, remoteFolder, map));
				}
			}
			return _dataFiles;
		} finally {
			ftpconn.closeFTPConnection();
		}
	}

}
