package com.scz.daoHibImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.util.EnvUtil;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;

import com.scz.daoImplMYSQL.PoolIDDAOImpl;
import com.connect.CommonFunction;
import com.connect.ConnectionUploadDAO;
import com.login.roleManager.UserObject;
import com.scz.dao.FutureFlowLoanUploadDAO;
import com.scz.vo.FutureFlowLoanUploadVO;

public class FutureFlowLoanIDUploadHibImplService implements  FutureFlowLoanUploadDAO{
	private static final Logger logger = Logger.getLogger(PoolIDDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.utill");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	SimpleDateFormat fileNameFormat = new SimpleDateFormat ("yyyy-MM-dd'_'hh-mm-ss");
	 String dbType=resource.getString("lbl.dbType");
	
	
	 
	/*public boolean uploadCsv_LoanId(HttpServletRequest request, HttpServletResponse response, String strFileName, FutureFlowLoanUploadVO  loanUploadVO){
		
		   boolean status=false;
	 	   try { 
	 		
	 		   String delQuery = "delete from loan_id_upload where maker_id='"+CommonFunction.checkNull(loanUploadVO.getMakerID())+"'";
	 		   ConnectionUploadDAO.singleDelete(delQuery);
	 		   
	 		   String Query = "select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='UPLOAD_PATH'";
	 		   String rpt1 =  ConnectionUploadDAO.singleReturn(Query);
	 		 	  
	 	     	  KettleEnvironment.init(false); 
	 	     	  EnvUtil.environmentInit(); 
	 	     	  TransMeta transMeta = new TransMeta(rpt1.concat("\\LoanIdUpload.ktr")); 
	 	     	  Trans trans = new Trans(transMeta); 
	 	          	 trans.setParameterValue("filePathWithName",CommonFunction.checkNull(loanUploadVO.getFilePathWithName()));
	 		     	 trans.setParameterValue("maker_id",CommonFunction.checkNull(loanUploadVO.getMakerID()));
	 		     	 trans.setParameterValue("maker_date",CommonFunction.checkNull(loanUploadVO.getMakerDate()));
	 		     	 trans.execute(null);	
	 	     	     trans.waitUntilFinished(); 
	 	     	 
	 	     	  if (trans.getErrors() > 0){ 
	 	     		  status=false;
	 	     		  throw new RuntimeException( "There were errors during transformation execution."+trans.getErrors() ); 
	 	     	  } else{
	 	     		 status=true;
	 	     	  }
	 	     	  
	 	     	  	transMeta.clearCaches();
	 		     	transMeta.clear();
	 		     	rpt1=null;
	 		     	trans.clearParameters();
	 		     	trans.cleanup();
	 		 	
	    } 
	       catch (Exception e) {
	     	  logger.info("In uploadCsv_Securitization() Problem is ---->"+e.getMessage());
	 	}finally {
	 		
	 	} 

	 	return status;
		
	}*/
	
	public boolean docUploadForExcel(HttpServletRequest request, FormFile docFile) {
	 	boolean status=false;
	 	String fileName="";
	 	String filePath="";
	 	String message="";
	 	String filePathWithName="";
	 	String fileNameChange="";
	 	String user_Id="";
	 	
	 	HttpSession session=request.getSession(false);
	 	UserObject sessUser = (UserObject) session.getAttribute("userobject");
	 	
	 	try{
	 		if(sessUser!=null){
	 			user_Id = sessUser.getUserId();
	 		}
	 		String query="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='UPLOAD_PATH'";
	 		String rpt=ConnectionUploadDAO.singleReturn(query);
	 		query=null;
	 		File directory = new File(rpt+"/");
	 		boolean isDirectory = directory.isDirectory();
	 		
	 		Date dNow = new Date( );
	 		fileNameChange=fileNameFormat.format(dNow);
	 		if(isDirectory){
	 			fileName    = docFile.getFileName();
	 			logger.info("fileName is --->"+fileName);
	 			fileName=user_Id.concat("_"+fileNameChange.concat(fileName));
	 			logger.info("changed fileName is --->"+fileName);	
	 			
	 			filePath = directory.getAbsolutePath();
	 			filePathWithName=filePath.concat("\\").concat(fileName);
	 			
	 				if(!fileName.equals("")){  
	 						logger.info("Server path: filePath:==>>" +filePath);

	 						File fileToCreate = new File(filePath, fileName);
	 						int fileSize = docFile.getFileSize(); //26314400 bytes = 25 MB
	 						logger.info("docUpload :Size of file==>> "+fileSize);

	 							if(fileSize<26314400){
	 								FileOutputStream fileOutStream = new FileOutputStream(fileToCreate);
	 								fileOutStream.write(docFile.getFileData());
	 								fileOutStream.flush();
	 								fileOutStream.close();
	 								message="O";	 								
	 								status=true;	        		
	 							}else{
	 								message="E";
	 								logger.info("File size exceeds the upper limit of 25 MB.");
	 								status=false;
	 							}
	 				}else{
	 					message="S";
	 		        	status=false;
	 				}
	 		}else{
	 			message="E";
	 			logger.info("File Directory is empty");
	         	status=false;
	 		}
	 	}catch(Exception e){
	 		e.printStackTrace();
	 	}
	 	logger.info("fileName "+fileName);
	 	logger.info("filePath "+filePath);
	 	logger.info("message "+message);
	 	logger.info("filePathWithName "+filePathWithName);
	 	logger.info("status "+status);
	 	
	 	request.setAttribute("fileName", fileName);
	 	request.setAttribute("filePath", filePath);
	 	request.setAttribute("message",message);
	 	request.setAttribute("filePathWithName", filePathWithName);
	 	fileName=null;
	 	filePath=null;
	 	message=null;
	 	filePathWithName=null;
	 	fileNameChange=null;
	 	user_Id=null;	 	
	 	
	 	return status;		
	 }
}
