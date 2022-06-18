package com.cm.business;

import com.cm.actionform.ExcelSheetUploadFormCM;
import com.cm.dao.ExcelSheetUploadDAOCM;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

public class ExcelSheetUploadBusiness
{
  private static final Logger logger = Logger.getLogger(ExcelSheetUploadBusiness.class.getName());
  DecimalFormat myFormatter = new DecimalFormat("###,###.####");

  ExcelSheetUploadDAOCM glDao = (ExcelSheetUploadDAOCM)DaoImplInstanceFactory.getDaoImplInstance("CMESU");

  public static String checkNull(Object requestString)
  {
    if (requestString != null) {
      return requestString.toString();
    }
    return "";
  }

  public boolean checkPreviousFiles(String filename)
  {
    return this.glDao.checkPreviousFiles(filename);
  }

  public String saveReceipt(HttpServletRequest request, ExcelSheetUploadFormCM excelForm)
  {
    logger.info("In saveReceipt () Implementation class: " + this.glDao.getClass());
    String message = this.glDao.saveReceipt(request, excelForm);
    return message;
  }

  public boolean uploadExcel(HttpServletRequest request, HttpServletResponse response, String strFileName, ExcelSheetUploadFormCM excelForm)
  {
    logger.info("Implementation class: " + this.glDao.getClass());
    logger.info("Inside uploadExcel() method of ExcelSheetUploadBusiness . ");
   /* boolean message = this.glDao.uploadExcel(request, response, strFileName, excelForm);*/
    HttpSession session= request.getSession(false);
	 boolean  message=false;
	 String functionId = CommonFunction.checkNull(session.getAttribute("functionId"));
	
	 if(functionId.equalsIgnoreCase("4000452")){
		  message = glDao.readExcelforGenerateBacthUpload(request,response,strFileName, excelForm);
	 }else if(functionId.equalsIgnoreCase("10000640")){
		  message = glDao.readExcelforSPDCUpload(request,response,strFileName, excelForm);
	  }
	 else{
	 	  message = glDao.uploadExcel(request,response,strFileName, excelForm);
	  }
    return message;
  }

  public String saveVoucher(HttpServletRequest request, ExcelSheetUploadFormCM excelForm)
  {
    logger.info("Implementation class: " + this.glDao.getClass());
    String message = this.glDao.saveVoucher(request, excelForm);
    return message;
  }

  public ArrayList reportAdHoc(ExcelSheetUploadFormCM excelForm, String functionId)
  {
    logger.info("Implementation class: " + this.glDao.getClass());
    ArrayList list = this.glDao.reportAdHoc(excelForm, functionId);
    return list;
  }

  public boolean docUploadForExcel(HttpServletRequest request, FormFile docFile)
  {
    boolean message = this.glDao.docUploadForExcel(request, docFile);
    return message;
  }

  public String save_CmsUpload(HttpServletRequest request, ExcelSheetUploadFormCM excelForm)
  {
    logger.info("In save_CmsUpload () Implementation class: " + this.glDao.getClass());
    String message = this.glDao.save_CmsUpload(request, excelForm);
    return message;
  }

  public boolean uploadCsv(HttpServletRequest request, HttpServletResponse response, String strFileName, ExcelSheetUploadFormCM excelForm) {
    logger.info("Implementation class: " + this.glDao.getClass());
    logger.info("Inside uploadExcel() method of ExcelSheetUploadBusiness . ");
    boolean message = false;
    HttpSession session = request.getSession(false);
    String functionId = CommonFunction.checkNull(session.getAttribute("functionId"));

    if (functionId.equalsIgnoreCase("10000606")) {
      logger.info("Upload for STATIONARY.... " + functionId);
      message = this.glDao.uploadCsv_Stationary(request, response, strFileName, excelForm);
    }
    else if (functionId.equalsIgnoreCase("10000817")) {
      logger.info("Upload for CMS UPLOAD.... " + functionId);
      message = this.glDao.uploadCsv_recoveryUpload(request, response, strFileName, excelForm);
    }
    else if (functionId.equalsIgnoreCase("10000601")) {
      logger.info("Upload for Receipt Upload.... " + functionId);
      message = this.glDao.uploadCsv_Receipt(request, response, strFileName, excelForm);
    }
    else if (functionId.equalsIgnoreCase("10000602")) {
      logger.info("Upload for Boucne Realization.... " + functionId);
      message = this.glDao.uploadCsv_Bounce(request, response, strFileName, excelForm);
    }
    else if (functionId.equalsIgnoreCase("10000625")) {
      logger.info("Upload for Allocation Process.... " + functionId);
      message = this.glDao.uploadCsv_AllocationUpload(request, response, strFileName, excelForm);
    }
    else if (functionId.equalsIgnoreCase("10000691")) {
      logger.info("Upload for CMS UPLOAD.... " + functionId);
      message = this.glDao.uploadCsv_cmsUpload(request, response, strFileName, excelForm);
    }else if(functionId.equalsIgnoreCase("4000452")) {
		 logger.info("Upload for Multiple Batch upload.... "+functionId);
		 message = glDao.uploadCsv_MultipleBatchUploadDownload(request,response,strFileName, excelForm);
		 logger.info("message ........"+message);
	 }
    else if(functionId.equalsIgnoreCase("10000640")) {
		 logger.info("Upload for SPDC Batch upload.... "+functionId);
		 message = glDao.uploadCsv_SPDCBatchUploadDownload(request,response,strFileName, excelForm);
		 logger.info("message ........"+message);
	 }
    else {
      message = this.glDao.uploadCsv(request, response, strFileName, excelForm);
    }
    functionId = null;

    return message;
  }

  public String recoveryUploadSave(HttpServletRequest request, ExcelSheetUploadFormCM excelForm)
  {
    logger.info("In recoveryUploadSave () Implementation class: " + this.glDao.getClass());
    String message = this.glDao.recoveryUploadSave(request, excelForm);
    return message;
  }

  public String saveStationary(HttpServletRequest request, ExcelSheetUploadFormCM excelForm)
  {
    logger.info("In saveStationary () Implementation class: " + this.glDao.getClass());
    String message = this.glDao.saveStationary(request, excelForm);
    return message;
  }

  public boolean docUploadForExcelForBankUpload(HttpServletRequest request, FormFile docFile)
  {
    boolean message = this.glDao.docUploadForExcelForBankUpload(request, docFile);
    return message;
  }

  public String saveBounce(HttpServletRequest request, ExcelSheetUploadFormCM excelForm) {
    logger.info("In saveBounce () Implementation class: " + this.glDao.getClass());
    String message = this.glDao.saveBounce(request, excelForm);
    return message;
  }

  public String AllocationProcessUploadSave(HttpServletRequest request, ExcelSheetUploadFormCM excelForm)
  {
    logger.info("In recoveryUploadSave () Implementation class: " + this.glDao.getClass());
    String message = this.glDao.AllocationProcessUploadSave(request, excelForm);
    return message;
  }
  public String saveMultipleBatch(HttpServletRequest request,
		  ExcelSheetUploadFormCM excelForm) {
	    logger.info("In saveReceipt () Implementation class: " + this.glDao.getClass());
	    String message = this.glDao.saveMultipleBatch(request, excelForm);
	    return message;
	  }
  public String saveSPDCBatch(HttpServletRequest request,
		  ExcelSheetUploadFormCM excelForm) {
	    logger.info("In saveReceipt () Implementation class: " + this.glDao.getClass());
	    String message = this.glDao.saveSPDCBatch(request, excelForm);
	    return message;
	  }
}