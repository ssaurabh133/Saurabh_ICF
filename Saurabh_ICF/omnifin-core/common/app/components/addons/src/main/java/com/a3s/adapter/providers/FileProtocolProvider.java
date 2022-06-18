package com.a3s.adapter.providers;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.a3s.adapter.AbstractProtocolProvider;
import com.a3s.adapter.IDataFile;
import com.a3s.adapter.util.ConfigFile;

/**
 * This class responsible to read the files located on host location.
 * 
 * @author Ritesh Srivastava
 * 
 */
public class FileProtocolProvider extends AbstractProtocolProvider {

	private Log log = LogFactory.getLog(FileProtocolProvider.class);
	private String sourceFolder;
	private String destinationFolder;
	private Map<String, String> map;

	public FileProtocolProvider(Map<String, String> map) {
		this.map = map;
		this.setValues();
	}

	private void setValues() {
		Set paramsSet = map.entrySet();
		Iterator iterator = paramsSet.iterator();
		sourceFolder = ConfigFile.getInstance().getInputpath(
				map.get(ConfigFile.PARAM_MONITOR));

		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry) iterator.next();

			if (entry.getKey().equals("ARCHIVE_OUTPUT_FILE_DIR"))
				destinationFolder = entry.getValue();
		}
	}

	/*
	 * Get the list of Files located on host location.
	 */
	public List<IDataFile> getList() {
		
		File _dir = new File(this.sourceFolder);
		List<IDataFile> _dataFiles = new ArrayList<IDataFile>();

		if (!_dir.exists()) {
			log.info("Adapter : monitored location -- "
					+ _dir.getAbsolutePath());
			log.error("Adapter : monitored location doesn't exist.");
		} else {

			File[] _files = _dir.listFiles();
			if (!(_files.length > 0)) {
				// log.info("No such file to process...");
			} else {
				for (File _afile : _files) {
					_dataFiles
							.add(new LocalDataFile(_afile, destinationFolder));
				}
			}
			return _dataFiles;
		}
		return _dataFiles;
	}

}
