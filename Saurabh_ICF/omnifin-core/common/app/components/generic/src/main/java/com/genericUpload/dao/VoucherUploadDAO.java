package com.genericUpload.dao;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.upload.FormFile;

//import com.cm.actionform.CMUploadForm;
import com.genericUpload.actionforms.ExcelSheetUploadForm;
import com.genericUpload.vo.VoucherUploadVO;

public interface VoucherUploadDAO
{
	
public String IDENTITY = "GENVUD";

public boolean uploadExcel(HttpServletRequest request,HttpServletResponse response,String strFileName,ExcelSheetUploadForm  excelForm);
public int saveUploadSummary(String uploadType,String upload_date,String maker_id,String file_name);
public int updateUploadSummary(String user_id,String batch_id);
public String updateBatchStatus(String batch_id,String userId);
public int sendBack(String batch_id,String userId,String comment);
public int reject(String batch_id,String userId,String comment);
public ArrayList<VoucherUploadVO> searchUploads(String user_id);
public ArrayList<VoucherUploadVO> searchUploads(ExcelSheetUploadForm form);
public ArrayList<VoucherUploadVO> searchData(String user_id);
public int deleteBatch(String batch_id,String userId);
public int forwardBatch(String batch_id,String userId);
public String saveVoucher(HttpServletRequest request,ExcelSheetUploadForm excelForm,String batch_id) ;
public String saveReceipt(HttpServletRequest request,ExcelSheetUploadForm excelForm) ;
public String saveBounce(HttpServletRequest request,ExcelSheetUploadForm excelForm) ;
public String saveManual(HttpServletRequest request,ExcelSheetUploadForm excelForm) ;
public String saveRate(HttpServletRequest request,ExcelSheetUploadForm excelForm) ;
public String saveNHB(HttpServletRequest request,ExcelSheetUploadForm excelForm) ;
public String saveStationary(HttpServletRequest request,ExcelSheetUploadForm excelForm) ;
public String saveBankStmt(HttpServletRequest request,ExcelSheetUploadForm excelForm) ;
public String save_NotepadUpload(HttpServletRequest request,ExcelSheetUploadForm excelForm);

public ArrayList reportAdHoc(ExcelSheetUploadForm excelForm,String functionId) ;
public ArrayList downloadDump(String batch);
public boolean docUploadForExcel(HttpServletRequest request, FormFile docFile);

public boolean uploadCsv(HttpServletRequest request,HttpServletResponse response,String batch_id,String strFileName,ExcelSheetUploadForm  excelForm);
public boolean uploadCsv_Bounce(HttpServletRequest request,HttpServletResponse response,String strFileName,ExcelSheetUploadForm  excelForm);
public boolean uploadCsv_Receipt(HttpServletRequest request,HttpServletResponse response,String strFileName,ExcelSheetUploadForm  excelForm);
public boolean uploadCsv_Manual(HttpServletRequest request,HttpServletResponse response,String strFileName,ExcelSheetUploadForm  excelForm);
public boolean uploadCsv_Rate(HttpServletRequest request,HttpServletResponse response,String strFileName,ExcelSheetUploadForm  excelForm);
public boolean uploadCsv_NHB(HttpServletRequest request,HttpServletResponse response,String strFileName,ExcelSheetUploadForm  excelForm);
public boolean uploadCsv_Stationary(HttpServletRequest request,HttpServletResponse response,String strFileName,ExcelSheetUploadForm  excelForm);
public boolean uploadCsv_Securitization(HttpServletRequest request,HttpServletResponse response,String strFileName,ExcelSheetUploadForm  excelForm);
public boolean uploadCsv_BantStmt(HttpServletRequest request,HttpServletResponse response,String strFileName,ExcelSheetUploadForm  excelForm);
public boolean uploadCsv_SectorPurpose(HttpServletRequest request,HttpServletResponse response, String strFileName,ExcelSheetUploadForm excelForm);
public String saveSectorPurpose(HttpServletRequest request,	ExcelSheetUploadForm excelForm);

public boolean uploadCsv_notepadUpload(HttpServletRequest request,HttpServletResponse response, String strFileName,ExcelSheetUploadForm excelForm);

public boolean uploadCsv_cmsUpload(HttpServletRequest request,HttpServletResponse response, String strFileName,ExcelSheetUploadForm excelForm);

public String save_CmsUpload(HttpServletRequest request,ExcelSheetUploadForm excelForm);
//public boolean uploadCsv_PoolProcess(HttpServletRequest request,HttpServletResponse response,String strFileName,CMUploadForm  excelForm);
//public String savePoolProcess(HttpServletRequest request,CMUploadForm excelForm);
//public ArrayList outfilequery(CMUploadForm excelForm) ;
boolean cmsInstrumentUpload(HttpServletRequest request,HttpServletResponse response,String strFileName,ExcelSheetUploadForm  excelForm);
String saveCmsInstrumentUpload(HttpServletRequest request,ExcelSheetUploadForm excelForm);

}
		         
		         
		     

		  
		   
		
	   
	    

	    
   
   
   