package com.a3s.adapter.providers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import com.a3s.adapter.AbstractDataFile;
import com.a3s.adapter.util.ConfigFile;

/**
 * This class responsible for handling FTP Files
 * 
 * @author Ritesh Srivastava
 * 
 */
public class FtpDataFile extends AbstractDataFile {

	private Log log = LogFactory.getLog(FtpDataFile.class);
	private FTPFile ftpFile = null;
	private String archiveFolder;
	private String tempDir;
	private String remoteFolder;
	private File renamedFile;
	private boolean filestatus = false;
	private Map<String, String> map;

	public FtpDataFile(FTPFile ftpFile, String archiveFolder, String tempDir,
			String remoteFolderUrl, Map<String, String> map) {
		this.archiveFolder = archiveFolder;
		this.remoteFolder = remoteFolderUrl;
		this.tempDir = tempDir;
		this.ftpFile = ftpFile;
		this.map = map;
	}

	@Override
	public String getName() {
		return ftpFile.getName();
	}
	
	@Override
	public long timeStamp() {
		return ftpFile.getTimestamp().getTimeInMillis();
	}

	/*
	 * Before Processing of FTP Files, it creates Temp Folder at given host
	 * location to store FTP Files
	 */
	@Override
	public File getFile() {

		File fileToWrite = null;
		FileOutputStream outputStream = null;
		new File(tempDir).mkdir();

		FtpConnection ftpconn = new FtpConnection();
		FTPClient ftpClient = getFtpClient(ftpconn);

		try {
			fileToWrite = new File(tempDir, ftpFile.getName());
			outputStream = new FileOutputStream(fileToWrite);
			ftpClient.retrieveFile(remoteFolder+ ftpFile.getName(), outputStream);
		
			outputStream.close();
		} catch (Exception e) {
			IOException _ioe = new IOException("file not found", e);
			log.info("\nfile not found", _ioe);
			throw new RuntimeException(_ioe);
		} finally {
			ftpconn.closeFTPConnection();
		}
		return fileToWrite;
	}

	private FTPClient getFtpClient(FtpConnection ftpconn) {
		String server = map.get(ConfigFile.PARAM_IP);
		int port = Integer.parseInt(map.get(ConfigFile.PARAM_PORT));
		String username = map.get(ConfigFile.PARAM_USERNAME);
		String password = map.get(ConfigFile.PARAM_PASSWORD);
		FTPClient ftpClient = ftpconn.getFTPConnection(server, port, username,
				password);
		return ftpClient;
	}

	/*
	 * After successful processing, FTP file will be move to archive folder.
	 */
	@Override
	public boolean moveFileToArchive() {
		boolean _flag = false;
		FileInputStream inputStream = null;
		File tempfile = new File(tempDir, renamedFile.getName());
		File movedFile = new File(tempDir, renamedFile.getName());

		FtpConnection ftpconn = new FtpConnection();
		FTPClient ftpClient = getFtpClient(ftpconn);

		try {
			if (filestatus) {
				ftpClient.makeDirectory(archiveFolder);
				inputStream = new FileInputStream(movedFile);
				ftpClient.storeFile(
						archiveFolder + renamedFile.getName(), inputStream);
				ftpClient.dele(remoteFolder+renamedFile.getName());
				_flag = true;
			}
		} catch (Exception e) {
			IOException _ioe = new IOException("file not found", e);
			log.info("\nfile not found", _ioe);
		} finally {
			ftpconn.closeFTPConnection();
		}
		tempfile.delete();
		return _flag;
	}

	/*
	 * After processing, FTP file will be renamed.
	 */
	@Override
	public boolean renameFile(File renamedFile, boolean filestatus) {
		boolean _filerenamed = false;
		this.renamedFile = renamedFile;
		this.filestatus = filestatus;

		FtpConnection ftpconn = new FtpConnection();
		FTPClient ftpClient = getFtpClient(ftpconn);

		try {
			if (ftpClient.rename(remoteFolder + ftpFile.getName(),remoteFolder + renamedFile.getName()))
				_filerenamed = true;
		} catch (Exception e) {
			IOException _ioe = new IOException(
					"Exception occured while renaming the file", e);
			log.info("\nException occured while renaming the file", _ioe);
		} finally {
			ftpconn.closeFTPConnection();
		}
		return _filerenamed;
	}

}
