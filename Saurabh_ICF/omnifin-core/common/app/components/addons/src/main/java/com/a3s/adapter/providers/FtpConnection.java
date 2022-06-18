package com.a3s.adapter.providers;

import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPReply;

/**
 * This singleton class is used to establish FTP Connection to remote server
 * 
 * @author Ritesh Srivastava
 * 
 */
public class FtpConnection {

	private Log log = LogFactory.getLog(FtpConnection.class);

	public FtpConnection() {
	}

	/**
	 * This method used to get FTP Connection
	 * 
	 * @param server
	 * @param username
	 * @param password
	 * @return FTPClient
	 */
	FTPClient ftpClient = null;

	public FTPClient getFTPConnection(String server, int port, String username,
			String password) {

		try {
			ftpClient = new FTPClient();
			ftpClient.connect(server, port);
			ftpClient.login(username, password);
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			int reply = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				log.error("remote server refused connection.");
				return ftpClient;
			}
		} catch (Exception e) {
			IOException _ioe = new IOException(
					"unable to connect remote server.", e);
			log.error("unable to connect remote server", _ioe);
		}
		return ftpClient;
	}

	/**
	 * This method used to Close FTP Connection
	 */
	public boolean closeFTPConnection() {
		try {
			if (ftpClient.isConnected()) {
				ftpClient.disconnect();
				return true;
			}
		} catch (Exception e) {
			FTPConnectionClosedException _fce = new FTPConnectionClosedException(
					"Exception occured during connection close.");
			log.error("Exception occured during connection close.", _fce);
		}
		return false;
	}

}
