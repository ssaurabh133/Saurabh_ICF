package com.a3s.adapter.dto;

import java.io.File;

/**
 * This class responsible for keeping all results 
 * found while processing of a file.
 * @author  Ritesh Srivastava
 *
 */
public class TaskResult {

	private boolean error;
	private Throwable exception;
	private File outputFile;	
	private String outputFileName;
	
	public TaskResult() {}

	public boolean isError() {
		return error;
	}

	public Throwable getException() {
		return exception;
	}

	public void setException(Throwable exception) {
		this.error = true;
		this.exception = exception;
	}

	public File getOutputFile() {
		return outputFile;
	}

	public void setOutputFile(File outputFile) {
		this.outputFile = outputFile;
	}

	public String getOutputFileName() {
		return outputFileName;
	}

	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}
			
}
