package com.cm.dao;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.upload.FormFile;

import com.cm.actionform.CreditMgmtExcelSheetUploadVoForm;


public interface CreditMgmtExcelSheetUploadDAO
{
	
  public String IDENTITY = "CMEXLUPL";
 public boolean uploadExcel(HttpServletRequest request,HttpServletResponse response,String strFileName,CreditMgmtExcelSheetUploadVoForm  excelForm);
  public String saveVoucher(HttpServletRequest request,CreditMgmtExcelSheetUploadVoForm excelForm) ;
//  public ArrayList reportAdHoc(CreditMgmtExcelSheetUploadVoForm excelForm) ;
  public ArrayList reportAdHoc(CreditMgmtExcelSheetUploadVoForm excelForm, String functionId) ;
  public boolean docUploadForExcel(HttpServletRequest request, FormFile docFile);
  public boolean uploadCsv(HttpServletRequest request,HttpServletResponse response,String strFileName,CreditMgmtExcelSheetUploadVoForm  excelForm);
  public boolean uploadCsv_recoveryUpload(HttpServletRequest request,HttpServletResponse response, String strFileName,CreditMgmtExcelSheetUploadVoForm excelForm);
  public String recoveryUploadSave(HttpServletRequest request,CreditMgmtExcelSheetUploadVoForm excelForm);
  public boolean docUploadForExcelForBankUpload(HttpServletRequest request, FormFile docFile);
  //public boolean uploadCsv_Receipt(HttpServletRequest request,HttpServletResponse response,String strFileName,CreditMgmtExcelSheetUploadVoForm  excelForm);
  public boolean uploadCsv_Stationary(HttpServletRequest request,HttpServletResponse response, String strFileName,CreditMgmtExcelSheetUploadVoForm excelForm);
  public String saveStationary(HttpServletRequest request,CreditMgmtExcelSheetUploadVoForm excelForm);
  public boolean uploadCsv_Bounce(HttpServletRequest request,HttpServletResponse response,String strFileName,CreditMgmtExcelSheetUploadVoForm  excelForm);
  public abstract boolean checkPreviousFiles(String paramString);// add by raj
  public abstract String saveReceipt(HttpServletRequest paramHttpServletRequest, CreditMgmtExcelSheetUploadVoForm paramCreditMgmtExcelSheetUploadVoForm);
  public abstract boolean uploadCsv_Receipt(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString, CreditMgmtExcelSheetUploadVoForm paramCreditMgmtExcelSheetUploadVoForm);
  public abstract String saveBounce(HttpServletRequest paramHttpServletRequest, CreditMgmtExcelSheetUploadVoForm paramCreditMgmtExcelSheetUploadVoForm);
  public String AllocationProcessUploadSave(HttpServletRequest request,CreditMgmtExcelSheetUploadVoForm excelForm);
  public boolean uploadCsv_AllocationUpload(HttpServletRequest request,HttpServletResponse response, String strFileName,CreditMgmtExcelSheetUploadVoForm excelForm);
  public boolean uploadCsv_cmsUpload(HttpServletRequest request,HttpServletResponse response, String strFileName,CreditMgmtExcelSheetUploadVoForm excelForm);
  public String save_CmsUpload(HttpServletRequest request,CreditMgmtExcelSheetUploadVoForm excelForm);
}
		         
		         
		     

		  
		   
		
	   
	    

	    
   
   
   