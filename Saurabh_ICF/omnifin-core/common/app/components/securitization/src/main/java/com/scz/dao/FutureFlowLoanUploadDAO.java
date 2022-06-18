package com.scz.dao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.upload.FormFile;

import com.scz.vo.FutureFlowLoanUploadVO;

public interface FutureFlowLoanUploadDAO {
	//boolean uploadCsv_LoanId(HttpServletRequest request,HttpServletResponse response,String strFileName,FutureFlowLoanUploadVO  excelForm);
	//String savePoolID(HttpServletRequest request, FutureFlowLoanUploadVO ffpuVO);
	
	boolean docUploadForExcel(HttpServletRequest request, FormFile docFile);
}
