package com.cm.dao;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

public interface UploadDocumentDAO {
	
	String IDENTITY="UPLOADDOCD";
	StringBuffer getUploadData(String userId);

	ArrayList getUploadSummary(String makerId,HttpServletRequest request);
	
}
