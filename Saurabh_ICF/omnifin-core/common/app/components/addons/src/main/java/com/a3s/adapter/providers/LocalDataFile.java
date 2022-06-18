package com.a3s.adapter.providers;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.a3s.adapter.AbstractDataFile;

/**
 * This class responsible for handling IO Files
 * @author Ritesh Srivastava
 *
 */
public class LocalDataFile extends AbstractDataFile {
	
	private Log log = LogFactory.getLog(LocalDataFile.class);
	private File localFile = null;
	private String destinationURL;
    private File renamedFile;
    private boolean filestatus = false;

	public LocalDataFile(File localFile,String destinationURL) {
		this.localFile = localFile;
		this.destinationURL = destinationURL;
	}

	@Override
	public String getName() {
		return localFile.getName();
	}

	@Override
	public File getFile() {
		return localFile;
	}

	@Override
	public long timeStamp() {
		return localFile.lastModified();
	}
	
	/* 
	 * After successful processing, IO file will be move to archive folder.
	 */
	@Override
	public boolean moveFileToArchive()  {
		boolean _flag= false;
		File outputfile=new File(renamedFile.toString());
		File destDir = new File(destinationURL);
		
		try {
			if(filestatus){
			FileUtils.moveFileToDirectory(outputfile,destDir,true);
			_flag = true;
			}
		} 
		catch (Exception e) {
			IOException _ioe = new IOException("file not found",e);
			log.info("\nfile not found",_ioe);
		}
		return _flag;
	}


	
	/* 
	 * After processing, IO file will be renamed.
	 */
	@Override
	public boolean renameFile(File renamedFile, boolean filestatus) {
		this.renamedFile = renamedFile;
		this.filestatus = filestatus;
		return true;
	}
   
}
