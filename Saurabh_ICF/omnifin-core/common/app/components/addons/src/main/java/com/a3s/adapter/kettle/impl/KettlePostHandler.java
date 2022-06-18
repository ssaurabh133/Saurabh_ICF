package com.a3s.adapter.kettle.impl;

import java.io.File;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.a3s.adapter.PostHandler;
import com.a3s.adapter.dto.Task;
import com.a3s.adapter.dto.TaskResult;
import com.a3s.adapter.util.AdapterLock;

/**
 * This class responsible for handling all activities performed after processing
 * the KTR file.
 * 
 * @author Ritesh Srivastava
 * 
 */
public class KettlePostHandler implements PostHandler {
	private Log log = LogFactory.getLog(KettlePostHandler.class);
	private SimpleDateFormat renameDateFormatter = null;
	private SimpleDateFormat renameErrorDateFormatter = null;
	private static File ftrg = null;
	private static boolean _filestatus;

	public KettlePostHandler() {
		this.renameDateFormatter = new SimpleDateFormat(
				"'.doneAt'yyyyMMMdd'_'HHmmss");
		this.renameErrorDateFormatter = new SimpleDateFormat(
				"'.ERROR.doneat'yyyyMMMdd'-'HH:mm:ss");
	}

	@Override
	public void handle(Task job, TaskResult result) {
		String _renameDate = null;
		if (result.isError()) {
			_renameDate = renameErrorDateFormatter.format(new Date(),
					new StringBuffer(), new FieldPosition(0)).toString();
			log.info("\n******* processing of file has been failed *******");
			_filestatus = false;
		} else {
			_renameDate = renameDateFormatter.format(new Date(),
					new StringBuffer(), new FieldPosition(0)).toString();
			log.info("\n******* processing of file has been finished successfully *******");
			_filestatus = true;
		}
		File fsrc = new File(job.getParams().get("INPUT_FILE_LOCATION"));
		String _newFilename = fsrc.getAbsolutePath() + _renameDate;
		ftrg = new File(_newFilename);
		fsrc.renameTo(ftrg);
		AdapterLock adapterlock = AdapterLock.getInstance();

		synchronized (adapterlock) {
			adapterlock.notifyAll();
		}
	}

	/**
	 * @return renamed file
	 */
	public static File getFtrg() {
		return ftrg;
	}

	/**
	 * @return file status
	 */
	public static boolean getFileStatus() {
		return _filestatus;
	}

}