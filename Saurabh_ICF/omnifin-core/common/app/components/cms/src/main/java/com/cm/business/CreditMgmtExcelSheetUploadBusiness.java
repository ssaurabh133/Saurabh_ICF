package com.cm.business;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;



import com.cm.actionform.CreditMgmtExcelSheetUploadVoForm;
import com.cm.dao.CreditMgmtExcelSheetUploadDAO;
//import com.GL.DAO.VoucherUploadDAO;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;

public class CreditMgmtExcelSheetUploadBusiness 
{
	 private static final Logger logger = Logger.getLogger(CreditMgmtExcelSheetUploadBusiness.class.getName());
	 DecimalFormat myFormatter = new DecimalFormat("###,###.####");
		public static String checkNull(Object requestString) 
		{
			if (requestString != null) {
				return requestString.toString();
			} else {
				return "";
			}
		
		}
		//ExcelSheetUploadDAO glDao = new ExcelSheetUploadDAO(); 

		CreditMgmtExcelSheetUploadDAO glDao = (CreditMgmtExcelSheetUploadDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditMgmtExcelSheetUploadDAO.IDENTITY);
		
		//VoucherUploadDAO voucherDao = (VoucherUploadDAO)DaoImplInstanceFactory.getDaoImplInstance(ExcelSheetUploadDAO.IDENTITY);
		
		//space start by raj 
		public boolean checkPreviousFiles(String filename)
		  {
		    return this.glDao.checkPreviousFiles(filename);
		  }
		//space end by raj 
		
		//space start by raj 
		public String saveReceipt(HttpServletRequest request, CreditMgmtExcelSheetUploadVoForm excelForm) {
		    logger.info("In saveReceipt () Implementation class: " + this.glDao.getClass());
		    String message = this.glDao.saveReceipt(request, excelForm);
		    return message;
		  }
		//space end by raj 
		public boolean uploadExcel(HttpServletRequest request,HttpServletResponse response,String strFileName,CreditMgmtExcelSheetUploadVoForm  excelForm)
		 { 
			 logger.info("Implementation class: "+glDao.getClass());
			 logger.info("Inside uploadExcel() method of ExcelSheetUploadBusiness . ");
			 	boolean  message = glDao.uploadExcel(request,response,strFileName, excelForm);
		    	return message;	 
	      }


		public String saveVoucher(HttpServletRequest request,CreditMgmtExcelSheetUploadVoForm excelForm) {
			 logger.info("Implementation class: "+glDao.getClass());
				String message=glDao.saveVoucher(request,excelForm);
			return message;
		}


		public ArrayList reportAdHoc(CreditMgmtExcelSheetUploadVoForm excelForm,String functionId) {
			 logger.info("Implementation class: "+glDao.getClass());
			ArrayList list=glDao.reportAdHoc(excelForm,functionId);
			return list;
		}


		public boolean docUploadForExcel(HttpServletRequest request,FormFile docFile) {
			boolean message=glDao.docUploadForExcel(request,docFile);
			return message;
		}
		public String save_CmsUpload(HttpServletRequest request,
				CreditMgmtExcelSheetUploadVoForm excelForm) {
			
			 logger.info("In save_CmsUpload () Implementation class: "+glDao.getClass());
				String message=glDao.save_CmsUpload(request,excelForm);
			return message;
		}
		 public boolean uploadCsv(HttpServletRequest request,HttpServletResponse response,String strFileName,CreditMgmtExcelSheetUploadVoForm  excelForm)
		 { 
			 logger.info("Implementation class: "+glDao.getClass());
			 logger.info("Inside uploadExcel() method of ExcelSheetUploadBusiness . ");
			 boolean  message = false;
			 HttpSession session= request.getSession(false);
			 String functionId = CommonFunction.checkNull(session.getAttribute("functionId"));
			//add by Ashish
			// logger.info("FunctionId-->"+functionId);
			 if(functionId.equalsIgnoreCase("10000606")) {
				 logger.info("Upload for STATIONARY.... "+functionId);
				 message = glDao.uploadCsv_Stationary(request,response,strFileName, excelForm);
			 }
			 else if(functionId.equalsIgnoreCase("10000817")) {
				 logger.info("Upload for CMS UPLOAD.... "+functionId);
				 message = glDao.uploadCsv_recoveryUpload(request,response,strFileName, excelForm);
			 }
			 else if (functionId.equalsIgnoreCase("10000601")) {
			      logger.info("Upload for Receipt Upload.... " + functionId);
			      message = this.glDao.uploadCsv_Receipt(request, response, strFileName, excelForm);
			    }
			 else if (functionId.equalsIgnoreCase("10000602")) {
			      logger.info("Upload for Boucne Realization.... " + functionId);
			      message = this.glDao.uploadCsv_Bounce(request, response, strFileName, excelForm);
			    } 
				//Changes Start for Allocation Process
			 else if(functionId.equalsIgnoreCase("10000625")) {
				 logger.info("Upload for Allocation Process.... "+functionId);
				 message = glDao.uploadCsv_AllocationUpload(request,response,strFileName, excelForm);
			 }
			//Changes End for Allocation Process
			// end by Ashish
			 else if(functionId.equalsIgnoreCase("10000691")) {
				 logger.info("Upload for CMS UPLOAD.... "+functionId);
				 message = glDao.uploadCsv_cmsUpload(request,response,strFileName, excelForm);
			 }
			 else{
			 	 message = glDao.uploadCsv(request,response,strFileName, excelForm);
			 }
			 functionId = null;
			 	
		     return message;
	      }
		// add by Ashish
			
			public String recoveryUploadSave(HttpServletRequest request,CreditMgmtExcelSheetUploadVoForm excelForm) 
			{
				
				 logger.info("In recoveryUploadSave () Implementation class: "+glDao.getClass());
					String message=glDao.recoveryUploadSave(request,excelForm);
				return message;
			}
			
			public String saveStationary(HttpServletRequest request,CreditMgmtExcelSheetUploadVoForm excelForm) 
			{
				 logger.info("In saveStationary () Implementation class: "+glDao.getClass());
					String message=glDao.saveStationary(request,excelForm);
				return message;
			}
			//end by Ashish

		  
		 public boolean docUploadForExcelForBankUpload(HttpServletRequest request,FormFile docFile) {
			boolean message=glDao.docUploadForExcelForBankUpload(request,docFile);
			return message;
		}

		 public String saveBounce(HttpServletRequest request,CreditMgmtExcelSheetUploadVoForm excelForm) {
			 logger.info("In saveBounce () Implementation class: "+glDao.getClass());
				String message=glDao.saveBounce(request,excelForm);
			return message;
		}
			//Changes Start for Allocation Process
			public String AllocationProcessUploadSave(HttpServletRequest request,
					CreditMgmtExcelSheetUploadVoForm excelForm) {
				
				 logger.info("In recoveryUploadSave () Implementation class: "+glDao.getClass());
					String message=glDao.AllocationProcessUploadSave(request,excelForm);
				return message;
			}
			//Changes End for Allocation Process
}	   

	 


