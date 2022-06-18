package com.caps.dao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.upload.FormFile;

import com.caps.VO.CollUploadDataVO;

public interface CollUoloadDataDAO {

	String IDENTITY="COLLUPLOAD";
	boolean uploadData(HttpServletRequest request, FormFile uploadFileLoan);

	int countUploadDataLine(String string);

	boolean csvRead(HttpServletRequest request, HttpServletResponse response,
			CollUploadDataVO collUploadDataVo, String uploadFileLoan);

}
