package com.a3s.adapter;

import java.io.File;

/**
 * @author Ritesh Srivastava
 *
 */
public interface IDataFile {

	String getName();
	File getFile();
	long timeStamp();
	boolean moveFileToArchive();
	boolean renameFile(File renamedFile, boolean filestatus);
	
}
