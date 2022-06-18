package com.scz.dao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.upload.FormFile;

import com.scz.vo.BuyBackUploadVO;

public interface BuyBackUploadDAO {

	boolean uploadCsvForBuyBack(HttpServletRequest request,HttpServletResponse response, String strFileName, BuyBackUploadVO  excelForm);
	boolean getFilePath(HttpServletRequest request, FormFile docFile);
}
