package com.genericUpload.dao;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.upload.FormFile;

import com.cm.actionform.CMUploadForm;
import com.genericUpload.actionforms.GenericUploadForm;
import com.genericUpload.dto.GenericUploadParametersDTO;
import com.genericUpload.vo.GenericUploadVO;
import com.genericUpload.vo.XMLBean;
import com.genericUpload.actionforms.ExcelSheetUploadForm;
import com.genericUpload.vo.VoucherUploadVO;

public interface GenericUploadDAO
{
	
public String IDENTITY = "GUD";

public boolean uploadExcel(HttpServletRequest request,HttpServletResponse response,String strFileName,ExcelSheetUploadForm  excelForm);
public int saveUploadSummary(String uploadType,String upload_date,String maker_id,String file_name);
public int updateUploadSummary(GenericUploadParametersDTO dto) throws Exception;
public String updateBatchStatus(String batch_id,String userId);
public int sendBack(String batch_id,String userId,String comment);
public int reject(String batch_id,String userId,String comment);
public ArrayList<GenericUploadVO> searchUploads(String user_id,XMLBean xmlbean);
public ArrayList<GenericUploadVO> searchUploads(GenericUploadForm form,XMLBean xmlbean);
public ArrayList<GenericUploadVO> searchData(String user_id,XMLBean xmlbean);
public int deleteBatch(String batch_id,String userId,XMLBean bean);
public int forwardBatch(String batch_id,String userId);
public String saveVoucher(HttpServletRequest request,ExcelSheetUploadForm excelForm,String batch_id) ;


public ArrayList reportAdHoc(GenericUploadForm excelForm,XMLBean bean) ;
public ArrayList downloadDump(String batch,XMLBean xmlbean);
public boolean docUploadForExcel(HttpServletRequest request, FormFile docFile);

public boolean uploadCsv(HttpServletRequest request,HttpServletResponse response,String batch_id,String strFileName,ExcelSheetUploadForm  excelForm);
public ArrayList downloadFormat(XMLBean xmlbean,String functionId);



}
		         
		         
		     

		  
		   
		
	   
	    

	    
   
   
   