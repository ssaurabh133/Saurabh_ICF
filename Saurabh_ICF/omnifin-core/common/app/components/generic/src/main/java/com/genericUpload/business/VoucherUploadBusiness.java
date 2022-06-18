package com.genericUpload.business;

import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;

import com.genericUpload.actionforms.ExcelSheetUploadForm;
import com.genericUpload.vo.VoucherUploadVO;
import com.genericUpload.dao.VoucherUploadDAO;

public class VoucherUploadBusiness 
{
	
	private static final Logger logger = Logger.getLogger(VoucherUploadBusiness.class.getName());
	VoucherUploadDAO glDao = (VoucherUploadDAO)DaoImplInstanceFactory.getDaoImplInstance(VoucherUploadDAO.IDENTITY);
	
	 
	 DecimalFormat myFormatter = new DecimalFormat("###,###.####");
		public static String checkNull(Object requestString) 
		{
			if (requestString != null) {
				return requestString.toString();
			} else {
				return "";
			}
		
		}
		 

		
		
		public int saveUploadSummary(String uploadType,String upload_date,String maker_id,String file_name)
		{
			return glDao.saveUploadSummary(uploadType,upload_date,maker_id,file_name);
			
		}
		
		//called from makerAction
		public int updateUploadSummary(String user_id,String batch_id)
		{
			return glDao.updateUploadSummary(user_id,batch_id);
		}
		
		//called from authorAction
		public String updateBatchStatus(String batch_id,String userId)
		{
			
			return glDao.updateBatchStatus(batch_id,userId);
		}
		
		 public boolean uploadExcel(HttpServletRequest request,HttpServletResponse response,String strFileName,ExcelSheetUploadForm  excelForm)
		 { 
			 
			 logger.info("Inside uploadExcel() method of ExcelSheetUploadBusiness . ");
			 	boolean  message = glDao.uploadExcel(request,response,strFileName, excelForm);
		    	return message;	 
	      }


		public String saveBounce(HttpServletRequest request,ExcelSheetUploadForm excelForm) {
			 
				String message=glDao.saveBounce(request,excelForm);
			return message;
		}
		
		public String saveReceipt(HttpServletRequest request,ExcelSheetUploadForm excelForm) {
			
				String message=glDao.saveReceipt(request,excelForm);
			return message;
		}
		
		public String saveManual(HttpServletRequest request,ExcelSheetUploadForm excelForm) {
			 
				String message=glDao.saveManual(request,excelForm);
			return message;
		}
		
		public String saveRate(HttpServletRequest request,ExcelSheetUploadForm excelForm) {
			 
				String message=glDao.saveRate(request,excelForm);
			return message;
		}
		
		public String saveNHB(HttpServletRequest request,ExcelSheetUploadForm excelForm) {
			 
				String message=glDao.saveNHB(request,excelForm);
			return message;
		}
		
		public String saveStationary(HttpServletRequest request,ExcelSheetUploadForm excelForm) {
			
				String message=glDao.saveStationary(request,excelForm);
			return message;
		}
		
		public String saveBankStmt(HttpServletRequest request,ExcelSheetUploadForm excelForm) {
			 
				String message=glDao.saveBankStmt(request,excelForm);
			return message;
		}
		
		public String saveSectorPurpose(HttpServletRequest request,ExcelSheetUploadForm excelForm) {
			 
				String message=glDao.saveSectorPurpose(request,excelForm);
			return message;
		}
		
		
		
		
		public String saveVoucher(HttpServletRequest request,ExcelSheetUploadForm excelForm,String batch_id) {
			 
				String message=glDao.saveVoucher(request,excelForm,batch_id);
			return message;
		}
		
		public String save_NotepadUpload(HttpServletRequest request,ExcelSheetUploadForm excelForm) {
			 
				String message=glDao.save_NotepadUpload(request,excelForm);
			return message;
		}
		
		


		public ArrayList reportAdHoc(ExcelSheetUploadForm excelForm,String functionId) {
			
			ArrayList list=glDao.reportAdHoc(excelForm,functionId);
			return list;
		}

		public ArrayList downloadDump(String batch)
		{
			return glDao.downloadDump(batch);
		}

		public boolean docUploadForExcel(HttpServletRequest request,FormFile docFile) {
			boolean message=glDao.docUploadForExcel(request,docFile);
			return message;
		}

		 public boolean uploadCsv(HttpServletRequest request,HttpServletResponse response,String batch_id,String strFileName,ExcelSheetUploadForm  excelForm)
		 { 
			 
			 logger.info("Inside uploadCsv");
			 HttpSession session= request.getSession(false);
			 boolean  message = false;
			 
			 String functionId = CommonFunction.checkNull(session.getAttribute("functionId"));
			 logger.info("In uploadCsv(Function Id) is.. "+functionId);			 
			 
			
			 
			 if(functionId.equalsIgnoreCase("10000622"))
			 {	
				 logger.info("Upload for GL VOUCHER UPLOAD.... "+functionId);
				 message = glDao.uploadCsv(request,response,batch_id,strFileName, excelForm);
			 }
			 
			 	
			 functionId = null;
			 	
		     return message;	 
	      }


		public String save_CmsUpload(HttpServletRequest request,
				ExcelSheetUploadForm excelForm) {
			
			 logger.info("In save_CmsUpload () Implementation class: "+glDao.getClass());
				String message=glDao.save_CmsUpload(request,excelForm);
			return message;
		}
		public String saveCmsInstrumentUpload(HttpServletRequest request,
				ExcelSheetUploadForm excelForm) {
			
			 logger.info("In saveCmsInstrumentUpload () Implementation class: "+glDao.getClass());
				String message=glDao.saveCmsInstrumentUpload(request,excelForm);
			return message;
		}
		
		public ArrayList<VoucherUploadVO> searchUploads(String user_id)
		{
			return glDao.searchUploads(user_id);
		}
		
		public ArrayList<VoucherUploadVO> searchUploads(ExcelSheetUploadForm form)
		{
			return glDao.searchUploads(form);
		}
		
		public int sendBack(String batch_id,String userId,String comment)
		{
			return glDao.sendBack(batch_id,userId,comment);
		}
		
		public int reject(String batch_id,String userId,String comment)
		{
			return glDao.reject(batch_id,userId,comment);
		}
		
		public ArrayList<VoucherUploadVO> searchData(String user_id)
		{
			return glDao.searchData(user_id);
		}
		
		public int deleteBatch(String batch_id,String userId)
		{
			return glDao.deleteBatch(batch_id, userId);
		}
		
		public int forwardBatch(String batch_id,String userId)
		{
			return glDao.forwardBatch(batch_id, userId);
		}
}	   

	 


