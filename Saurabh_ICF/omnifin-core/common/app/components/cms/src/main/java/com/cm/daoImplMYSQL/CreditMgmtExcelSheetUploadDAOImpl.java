package com.cm.daoImplMYSQL;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.util.EnvUtil;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;

import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.ConnectionUploadDAO;
import com.ibm.icu.text.DecimalFormat;
import com.login.roleManager.UserObject;
import com.cm.actionform.CreditMgmtExcelSheetUploadVoForm;
import com.cm.dao.CreditMgmtExcelSheetUploadDAO;

public class CreditMgmtExcelSheetUploadDAOImpl implements CreditMgmtExcelSheetUploadDAO
{
	private static final Logger logger = Logger.getLogger(CreditMgmtExcelSheetUploadDAOImpl.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	 String dateFormatWithTime = resource.getString("lbl.dateWithTimeInDao");
	 String dateFormat = resource.getString("lbl.dateInDao");
	 SimpleDateFormat fileNameFormat = new SimpleDateFormat ("yyyy-MM-dd'_'hh-mm-ss");
	 SimpleDateFormat dateFormatCheck = new SimpleDateFormat("dd-MM-yyyy");
	DecimalFormat myFormatter = new DecimalFormat("###,###.####");
	
	   Connection con = null;
	   PreparedStatement ptmt = null;
	   ResultSet rs = null;
	   Statement stmt = null;
	   CallableStatement csmt = null;
	   
   public boolean uploadExcel(HttpServletRequest request,HttpServletResponse response,String strFileName,CreditMgmtExcelSheetUploadVoForm  excelForm)
   {
	   
	   	logger.info("SessionId:========================="+excelForm.getSessionId());
	    logger.info("Inside  uploadExcel() method of ExcelSheetUploadDAO ");
	    
	    String session=CommonFunction.checkNull(excelForm.getSessionId());
	    
	    logger.info("============= session_id====================== "+session);
	    
	    StringBuffer debit=null;
	    StringBuffer credit=null;
	    StringBuffer dateCheck=null;
	    Connection con = null;
	    PreparedStatement ptmt = null;
	    ResultSet rs = null;
	    Statement stmt = null;
	   // int flag=0;
	   // CallableStatement cst=null;
	    StringBuffer  makerID	= null;
	   
	    ArrayList     alDeleteQuery  = new ArrayList(1);
	    ArrayList     dataList  = new ArrayList();
	    ArrayList     arr  = new ArrayList();
	    ArrayList     subList  = new ArrayList();
	    String sheetName[]=null;
	    StringBuffer sNo=null;
	    StringBuffer bID=null;
	    StringBuffer mID=null;
	    StringBuffer vDate=null;
	    StringBuffer vType=null;
	    StringBuffer pID=null;
	    StringBuffer dID=null;
	    StringBuffer chequeInNo=null;
	    StringBuffer refereneNo=null;
	    StringBuffer narration=null;
	    StringBuffer sID=null;
	    StringBuffer offBalance=null;
	    StringBuffer mVoucher=null;
	    StringBuffer reciptNO=null;
	    StringBuffer ledgerId=null;
	    StringBuffer subLedgerId=null;
	    StringBuffer ledgerRemarks=null;
	    StringBuffer seqNo=null;
	    File          objFile1		= null;      
	    
	    boolean status=false;
	   
	 //   StringBuffer message="";
	    try 
	    {
	    	makerID=new StringBuffer(CommonFunction.checkNull(excelForm.getMakerId()));

		  if(!strFileName.equals(""))
	      {
	    	  logger.info("In uploadExcel(): strFileName ==>>"+strFileName);
	    	  String query="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='BOUNCE_UPLOAD_PATH'";		
			  String strPath=ConnectionDAO.singleReturn(query);
			  strPath     = strPath + "/" + strFileName;
			  objFile1    = new File(strPath);
	          try 
	          {     	   
	        	 String strDeleteQuery = "DELETE FROM temp_gl_voucher_upload WHERE user_id = '"+makerID+"' ";
	        	  logger.info("In  uploadExcel(): delete query :-----"+strDeleteQuery);
	        	  alDeleteQuery.add(strDeleteQuery);
	        	  boolean status1=ConnectionDAO.sqlInsUpdDelete(alDeleteQuery);
	        	  logger.info("In readExcel for ExcelSheetUploadDao(): status1 ==>>"+status1);
	        	  if(status1) {
	        		  logger.info("Row is deleted....");
	        	  }else{
	        		  logger.info("Row is not deleted......");
	        	  }
	        	  
	        	  dataList=new ArrayList();
	        	  //Workbook workbook = Workbook.getWorkbook(new File(strPath));
	        	  Workbook workbook = Workbook.getWorkbook(objFile1 );
	           	  sheetName=workbook.getSheetNames();
	        	  Sheet sheet;
	        	  Cell xlsCell;
	        	  Cell[] cell;   	  

	        	  for(int p=0; p<sheetName.length; p++)
	        	  {
	        		  sheet = workbook.getSheet(p);
	        		  if(sheet.getRows()<=1000){
		        		  for(int i=0; i<sheet.getRows(); i++)
		        		  {
		        			  cell = sheet.getRow(i);
		        			  arr = new ArrayList();
		        			  for(int j=0; j<cell.length; j++)
		        			  {
		        				  xlsCell = sheet.getCell(j,i);
		        				  arr.add(xlsCell.getContents().toString());
		                      }  
		        			  dataList.add(arr);
		        			 // arr=null;
		        		 }
	        		  }else{
	        			  dateCheck=new StringBuffer("Only 1000 of rows can upload at a time ");
	        		  }
	              }       
	         }
	         catch(IOException e) 
	         { 
	        	 e.printStackTrace();
	         }
	         con = ConnectionDAO.getConnection();
	         try{
	        	 
				String query1="insert into temp_gl_voucher_upload  (Serial_no,VOUCHER_TYPE,VOUCHER_DATE,NARRATION,MODULE_ID,REFERENCE_NO,cheque_invoice_no,department," +
				" PRODUCT_ID,Manual_Voucher,STAGE_ID,off_balance,DR_AMOUNT,CR_AMOUNT,LEDGER_ID,SUBLEDGER_ID," +
				" SEQUENCE_NO,user_id,session_id,BRANCH_ID,RECIPT_NO,LEDGER_REMARKS) values" +
				" (?,?,str_to_date(?,'%d/%m/%Y'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
				 ptmt=con.prepareStatement(query1);

	         for(int i=1;i<dataList.size();i++)
	         {		
	     	    subList=(ArrayList)dataList.get(i);
	        	if(subList.size()==20) 
	        	{ 
	        		sNo=new StringBuffer(CommonFunction.checkNull(subList.get(0).toString()).trim());
	        		bID=new StringBuffer(CommonFunction.checkNull(subList.get(1).toString()).trim());
	        		mID=new StringBuffer(CommonFunction.checkNull(subList.get(2).toString()).trim());
	        		vDate=new StringBuffer(CommonFunction.checkNull(subList.get(3).toString()).trim());
	        		vType=new StringBuffer(CommonFunction.checkNull(subList.get(4).toString()).trim());
	        		pID=new StringBuffer(CommonFunction.checkNull(subList.get(5).toString()).trim());
	        		dID=new StringBuffer(CommonFunction.checkNull(subList.get(6).toString()).trim());
	        		
	        		if(CommonFunction.checkNull(subList.get(6).toString()).equalsIgnoreCase("")){
	        			dID=new StringBuffer("0");
	        		}else{
	        			dID=new StringBuffer(CommonFunction.checkNull(subList.get(6).toString()).trim());
	        		}
	        		
	        		chequeInNo=new StringBuffer(CommonFunction.checkNull(subList.get(7).toString()).trim());
	        		refereneNo=new StringBuffer(CommonFunction.checkNull(subList.get(8).toString()).trim());
	        		narration=new StringBuffer(CommonFunction.checkNull(subList.get(9).toString()).trim());
	        		sID=new StringBuffer(CommonFunction.checkNull(subList.get(10).toString()).trim());
	        		offBalance=new StringBuffer(CommonFunction.checkNull(subList.get(11).toString()).trim());
	        		mVoucher=new StringBuffer(CommonFunction.checkNull(subList.get(12).toString()).trim());
	        		//String type=CommonFunction.checkNull(subList.get(12));
	        		reciptNO=new StringBuffer(CommonFunction.checkNull(subList.get(13).toString()).trim());
	        		ledgerId=new StringBuffer(CommonFunction.checkNull(subList.get(14).toString()).trim());
	        		subLedgerId=new StringBuffer(CommonFunction.checkNull(subList.get(15).toString()).trim());
	        		ledgerRemarks=new StringBuffer(CommonFunction.checkNull(subList.get(16).toString()).trim());
	        		debit=new StringBuffer(CommonFunction.checkNull(subList.get(17).toString()).trim());
	        		credit=new StringBuffer(CommonFunction.checkNull(subList.get(18).toString()).trim());
	        		
	        		seqNo=new StringBuffer(CommonFunction.checkNull(subList.get(19).toString()).trim());
	        		    			
	        		if(sNo.toString().equalsIgnoreCase("") || bID.toString().equalsIgnoreCase("") || mID.toString().equalsIgnoreCase("") || vDate.toString().equalsIgnoreCase("") || vType.toString().equalsIgnoreCase("") || narration.toString().equalsIgnoreCase("") 
	        				|| offBalance.toString().equalsIgnoreCase("") || mVoucher.toString().equalsIgnoreCase("") || ledgerId.toString().equalsIgnoreCase("") || seqNo.toString().equalsIgnoreCase("") || debit.toString().equalsIgnoreCase("") 
	        				|| credit.toString().equalsIgnoreCase("") || (!mVoucher.toString().equalsIgnoreCase("") && !mVoucher.toString().equalsIgnoreCase("Y"))){
       			
	        			if(sNo.toString().equalsIgnoreCase("")){
	        				dateCheck =new StringBuffer("Please fill S.N");
	        			}else if(bID.toString().equalsIgnoreCase("")){
	        				dateCheck =new StringBuffer("Please fill Branch_id");
	        			}else if(mID.toString().equalsIgnoreCase("")){
	        				dateCheck =new StringBuffer("Please fill MODULE_ID");
	        			}else if(vDate.toString().equalsIgnoreCase("")){
	        				dateCheck =new StringBuffer("Please fill VOUCHER_DATE");
	        			}else if(vType.toString().equalsIgnoreCase("")){
	        				dateCheck =new StringBuffer("Please fill VOUCHER_TYPE");
	        			}else if(narration.toString().equalsIgnoreCase("")){
	        				dateCheck =new StringBuffer("Please fill NARRATION");
	        			}else if(offBalance.toString().equalsIgnoreCase("")){
	        				dateCheck =new StringBuffer("Please fill off_balance");
	        			}else if(mVoucher.toString().equalsIgnoreCase("")){
	        				dateCheck =new StringBuffer("Please fill Manual_Voucher");
	        			}else if(ledgerId.toString().equalsIgnoreCase("")){
	        				dateCheck =new StringBuffer("Please fill LEDGER_ID");
	        			}else if(seqNo.toString().equalsIgnoreCase("")){
	        				dateCheck =new StringBuffer("Please fill SEQUENCE_NO");
	        			}else if(debit.toString().equalsIgnoreCase("")){
	        				dateCheck=new StringBuffer("Please fill all DR_AMOUNT");
	        			}else if(credit.toString().equalsIgnoreCase("")){
	        				dateCheck=new StringBuffer("Please fill all CR_AMOUNT");
	        			}else if((!mVoucher.toString().equalsIgnoreCase("") && !mVoucher.toString().equalsIgnoreCase("Y"))){
	        				dateCheck=new StringBuffer("Please provide in Manual_Voucher only 'Y' flag");
	        			}
	        			status=false;
	        			break;
	        		}else{
	        		
	        		if (vDate.toString().trim().length() != dateFormatCheck.toPattern().length()){
	        			dateCheck =new StringBuffer("In-valid Date,Provide date in dd/mm/yyyy Format.");
	        			status=false;
	        			break;
	        		}else{
	        			//logger.info("valid date");
	        	
	    				 ptmt.setString(1, sNo.toString().trim());
	    				 ptmt.setString(2, vType.toString().trim());
	    				 ptmt.setString(3, vDate.toString().trim());
	    				 ptmt.setString(4, narration.toString().trim());
	    				 ptmt.setString(5, mID.toString().trim());
	    				 ptmt.setString(6, refereneNo.toString().trim());
	    				 ptmt.setString(7, chequeInNo.toString().trim());
	    				 ptmt.setString(8, dID.toString().trim());
	    				 ptmt.setString(9, pID.toString().trim());
	    				 ptmt.setString(10, mVoucher.toString().trim());
	    				 ptmt.setString(11, sID.toString().trim());
	    				 ptmt.setString(12, offBalance.toString().trim());
	    				 ptmt.setString(13, debit.toString().trim());
	    				 ptmt.setString(14, credit.toString().trim());
	    				 ptmt.setString(15, ledgerId.toString().trim());
	    				 ptmt.setString(16, subLedgerId.toString().trim());
	    				 ptmt.setString(17, seqNo.toString().trim());
	    				 ptmt.setString(18, makerID.toString().trim());
	    				 ptmt.setString(19, session.toString().trim());
	    				 ptmt.setString(20, bID.toString().trim());	
	    				 ptmt.setString(21, reciptNO.toString().trim());
	    				 ptmt.setString(22, ledgerRemarks.toString().trim());
	    				 ptmt.addBatch();
	    				 
//	    				 flag=ptmt.executeUpdate();
//	 					 
//	 					 if(flag>0){
//	 		        		status=true;
//	 		        	}
	        		}
	        		} 
	            }
	        	if(sNo!=null){
	        	 sNo.setLength(0);
	    	     bID.setLength(0);
	    	     mID.setLength(0);
	    	     vDate.setLength(0);
	    	     vType.setLength(0);
	    	     pID.setLength(0);
	    	     dID.setLength(0);
	    	     chequeInNo.setLength(0);
	    	     refereneNo.setLength(0);
	    	     narration.setLength(0);
	    	     sID.setLength(0);
	    	     offBalance.setLength(0);
	    	     mVoucher.setLength(0);
	    	     ledgerId.setLength(0);
	    	     subLedgerId.setLength(0);
	    	     seqNo.setLength(0);
	    	     debit.setLength(0);
	    	     credit.setLength(0);
	    	     reciptNO.setLength(0);
	    	     ledgerRemarks.setLength(0);
	    	     query=null;
	        	}
	        }
	         
	          if(dateCheck==null && CommonFunction.checkNull(dateCheck).equalsIgnoreCase("")){
        	  
	        	int[] rows=ptmt.executeBatch();
	        	 if(rows.length>0){
				 		status=true; 
			    	 }
	         }
			 	
	       }
	      catch(Exception e){
				e.printStackTrace();
				dateCheck =new StringBuffer("Some problem in excel sheet at"+e.getMessage());
				logger.error(e);
				
			}finally {
				query=null;
				strPath=null;
				ConnectionDAO.closeConnection(con, ptmt);
				
			}
	      }
	    }
	catch(Exception e)
	{
			e.printStackTrace();
		 logger.error(e);
	}
	finally
	{
		
		 if(dateCheck!=null && !CommonFunction.checkNull(dateCheck).equalsIgnoreCase("")){
			 	request.setAttribute("dateCheck", CommonFunction.checkNull(dateCheck.toString()));
			 //	dateCheck.setLength(0);
		 }
		makerID.setLength(0);
		debit.setLength(0);
		credit.setLength(0);
		ptmt=null;	
		alDeleteQuery.clear();
		alDeleteQuery=null;
		dataList.clear();
		dataList=null;
		arr.clear();
		arr=null;
		subList.clear();
		subList=null;
		credit=null;
		sheetName=null;
		sNo=null;
		bID=null;
		mID=null;
		vDate=null;
		vType=null;
		pID=null;
		dID=null;
		chequeInNo=null;
		refereneNo=null;
		narration=null;
		sID=null;
		offBalance=null;
		mVoucher=null;
		ledgerId=null;
		subLedgerId=null;
		seqNo=null;
		debit=null;
		reciptNO.setLength(0);
 	    ledgerRemarks.setLength(0);
 	    reciptNO=null;
 	   	ledgerRemarks=null;
		ConnectionDAO.closeConnection(con, stmt, rs);
	}

	return status;
   }

public String saveVoucher(HttpServletRequest request,CreditMgmtExcelSheetUploadVoForm excelForm) {
	String flag=null;
	 con = ConnectionDAO.getConnection();	
	   rs=null;
	 
	   String userId=CommonFunction.checkNull(excelForm.getMakerId());
	   String comId=CommonFunction.checkNull(excelForm.getCompanyId());
	   String sessionId=CommonFunction.checkNull(excelForm.getSessionId());
	   String branchId=CommonFunction.checkNull(excelForm.getBranchId());
	   String businessDate=CommonFunction.checkNull(excelForm.getBusinessDate());

	try { 
		//con.setAutoCommit(true);
		 
		
		csmt = (CallableStatement) con.prepareCall("{CALL GL_EXCELSHEET_VOUCHER_VALIDATION(?,?,STR_TO_DATE(?,'"+dateFormat+"'),?,?,?,?,?)}");
		csmt.setString(1,comId);
		csmt.setString(2,userId);
		csmt.setString(3,businessDate);
		csmt.setString(4,branchId);
		csmt.setString(5,sessionId);
		csmt.registerOutParameter(6, java.sql.Types.VARCHAR);
		csmt.registerOutParameter(7, java.sql.Types.VARCHAR);
		csmt.registerOutParameter(8, java.sql.Types.VARCHAR);
	
		rs = csmt.executeQuery();
		
		 flag = csmt.getString(6);
		 String s2 = csmt.getString(7);
		 String voucherNo=csmt.getString(8);
		 logger.info("flag:----"+flag);
		 logger.info("s2:----"+s2);
		 logger.info("voucherNo:----"+voucherNo);
		 request.setAttribute("errorMsg", s2);		 
//		if (flag.equals("S")) {
//			try {
//				con.commit();
//			} catch (SQLException ex) {
//				ex.printStackTrace(); 
//			}
//		} else {
//			try {
//				con.rollback();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}

	} catch (SQLException exp) {

		exp.printStackTrace();

	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		   userId=null;
		   comId=null;
		   sessionId=null;
		   branchId=null;
		   businessDate=null;
		   ConnectionDAO.closeConnection(con,csmt,rs);
			
	}
	return flag;
}

public ArrayList reportAdHoc(CreditMgmtExcelSheetUploadVoForm excelForm, String functionId) {
	logger.info("in reportAdHoc() of DumpDownLoadDAOImpl");
	ArrayList resultList=new ArrayList();
	StringBuffer sb = new StringBuffer();
	//add by Ashish
		if(functionId.equalsIgnoreCase("10000606")) {
			 logger.info("reportAdHoc for STATIONARY.... "+functionId);
			 sb.append(" SELECT STATIONARY_TYPE,BANK_ID,BOOK_NO,NO_OF_INSTRUMENTS_RECEIPTS,INSTRUMENT_RECEIPT_FORM,RECEIPT_INSTRUMENT_TO,ADDITION_DATE");
			 sb.append(" STATUS,MAKER_ID,REJECT_FLAG,REJECT_REASON ");
			 sb.append(" FROM GL_STATIONARY_UPLOAD_TMP WHERE MAKER_ID='"+excelForm.getMakerId()+"'");			 
			 
		 }
		else if(functionId.equalsIgnoreCase("10000817")) {
			 logger.info("reportAdHoc for Recovery Executive.... "+functionId);
			 sb.append("SELECT loan_id,recovery_executive_id,source_type,source_description,user_id,rec_status,error_msg FROM RECOVERY_EXECUTIVE_TEMP WHERE  user_id='"+excelForm.getMakerId()+"'");
		 }
		/*else if(functionId.equalsIgnoreCase("10000602")){
			 logger.info("reportAdHoc for Boucne Realization.... "+functionId);
			 sb.append(" SELECT LOAN_NO,INSTRUMENT_TYPE,INSTRUMENT_MODE,INSTRUMENT_NO,INSTRUMENT_DATE,INSTRUMENT_AMOUNT,");
			 sb.append(" REC_STATUS,VALUE_DATE,REASON_ID,MAKER_ID,UPLOADED_FLAG,REJECT_REASON");
	 	     sb.append(" FROM CHEQUE_STATUS_UPLOAD_TMP WHERE MAKER_ID='"+excelForm.getMakerId()+"'");

		 }*/
		 // end by Ashish
		//add by raj for recipt upload and bounce upload 
		else if(functionId.equalsIgnoreCase("10000601")) {
			 logger.info("reportAdHoc for Receipt Upload.... "+functionId);
			 sb.append(" SELECT LOAN_NO,RECEIPT_MODE,INSTRUMENT_NO,BANK_ID,BRANCH_ID,BANK_ACCOUNT,RECEIPT_DATE,INSTRUMENT_DATE,RECEIPT_AMOUNT,TDS_AMOUNT,");
			 sb.append(" RECEIPT_NO,DEFAULT_BRANCH,MAKER_REMARKS,MAKER_ID,UPLOADED_FLAG,REJECT_REASON");
			 sb.append(" FROM CR_INSTRUMENT_DTL_TEMP WHERE MAKER_ID='"+excelForm.getMakerId()+"'");
		}
		else if(functionId.equalsIgnoreCase("10000602")){
			 logger.info("reportAdHoc for Boucne Realization.... "+functionId);
			 sb.append(" SELECT LOAN_NO,INSTRUMENT_TYPE,INSTRUMENT_MODE,INSTRUMENT_NO,INSTRUMENT_DATE,INSTRUMENT_AMOUNT,BANK_ID,BRANCH_ID,BANK_ACCOUNT");
			 sb.append(" REC_STATUS,VALUE_DATE,REASON_ID,MAKER_ID,UPLOADED_FLAG,REJECT_REASON");
	 	     sb.append(" FROM CHEQUE_STATUS_UPLOAD_TMP WHERE MAKER_ID='"+excelForm.getMakerId()+"'");

		 }
		//space end by raj 
		//Changes Start for Allocation Process
		 else if(functionId.equalsIgnoreCase("10000625")) {
			 logger.info("reportAdHoc for Allocation Process Upload.... "+functionId);
			 sb.append("SELECT LOAN_NO,USER_REPORTING_TO,UPLOAD_FLAG,REJECT_REASON,MAKER_ID,MAKER_DATE FROM temp_allocation_process_upload WHERE  Maker_id='"+excelForm.getMakerId()+"'");
		 }
		 else if(functionId.equalsIgnoreCase("10000691")) {
			 logger.info("reportAdHoc for CMS UPLOAD.... "+functionId);
			 sb.append("SELECT INSTRUMENT_ID,INSTRUMENT_NO,UPLOADED_FLAG,REJECT_REASON,MAKER_ID,MAKER_DATE FROM CMS_UPLOAD_TEMP WHERE  MAKER_ID='"+excelForm.getMakerId()+"'");
		 }
		//Changes End for Allocation Process
		 else
		 {
			sb.append("select Serial_no,BRANCH_ID,MODULE_ID,VOUCHER_DATE,VOUCHER_TYPE,PRODUCT_ID,if(department=0,'',department) as department,cheque_invoice_no,REFERENCE_NO,NARRATION,");
			sb.append(" CONCAT(STAGE_ID,'') as Transaction,off_balance,Manual_Voucher,RECIPT_NO,LEDGER_ID,SUBLEDGER_ID,LEDGER_REMARKS,DR_AMOUNT,CR_AMOUNT,SEQUENCE_NO,ERROR_FLAG,ERROR_MSG,VOUCHER_NO,");
			sb.append(" UPLOAD_ID,BATCH_NO " );
			sb.append(" from temp_gl_voucher_upload where user_id='"+excelForm.getMakerId()+"' ");	
			logger.info("Query is... "+ sb.toString());	
		 }
		try
		{
			 resultList = ConnectionDAO.sqlColumnWithResult(sb.toString());
			 
		}
		catch(Exception e)
		{e.printStackTrace();}
		finally{
			sb=null;
		}
		logger.info("size"+resultList.size());
		return resultList;
}


public boolean docUploadForExcel(HttpServletRequest request, FormFile docFile) {
	boolean status=false;
	String fileName="";
	String filePath="";
	String message="";
	String filePathWithName="";
	try{
		String query="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='BOUNCE_UPLOAD_PATH'";
		String rpt=ConnectionDAO.singleReturn(query);
		query=null;
		File directory = new File(rpt+"/");
		boolean isDirectory = directory.isDirectory();
		
		String query1="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='GL_CSV_EXCELUPLOAD'";
		String rpt1=ConnectionDAO.singleReturn(query1);
		query1=null;
		
		if(isDirectory){
			fileName    = docFile.getFileName();
			logger.info("fileName is --->"+fileName);
			filePath = directory.getAbsolutePath();
			filePathWithName=filePath.concat("\\").concat(fileName);
				if(!fileName.equals("")){  
						logger.info("Server path: filePath:==>>" +filePath);
						//Create file
						File fileToCreate = new File(filePath, fileName);
						int fileSize = docFile.getFileSize(); //26314400 bytes = 25 MB
						logger.info("docUpload :Size of file==>> "+fileSize);
						logger.info("docUpload :KTR Path ==>> "+rpt1);
							if(fileSize<26314400){
								FileOutputStream fileOutStream = new FileOutputStream(fileToCreate);
								fileOutStream.write(docFile.getFileData());
								fileOutStream.flush();
								fileOutStream.close();
								message="O";
								
								Writer output = new BufferedWriter(new FileWriter(rpt1.concat("\\File_path\\filePath.txt")));
							    try {
							      //FileWriter always assumes default encoding is OK!
							      output.write( filePathWithName );
							    }finally {
							    	output.flush();
							    	output.close();
							    }
								status=true;	        		
							}else{
								message="E";
								logger.info("File size exceeds the upper limit of 25 MB.");
								status=false;
							}
				}else{
					message="S";
					//logger.info("Please select a File.");
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
	request.setAttribute("fileName", fileName);
	request.setAttribute("filePath", filePath);
	request.setAttribute("message",message);
	request.setAttribute("filePathWithName", filePathWithName);
	fileName=null;
	filePath=null;
	message=null;
	filePathWithName=null;
	return status;		
}
//sapce start by raj 
public String saveReceipt(HttpServletRequest request, CreditMgmtExcelSheetUploadVoForm excelForm) {
    String flag = null;
    this.con = ConnectionUploadDAO.getConnection();
    this.rs = null;

    String businessDate = CommonFunction.checkNull(excelForm.getBusinessDate());
    String s1 = null;
    String s2 = null;
    try
    {
      ArrayList in = new ArrayList();
      ArrayList out = new ArrayList();
      ArrayList outMessages = new ArrayList();
      int noOfRejectedRecord = 0;
      int noOfUploadedRecord = 0;
      businessDate = CommonFunction.changeFormat(CommonFunction.checkNull(businessDate).trim());

      in.add(CommonFunction.checkNull(Integer.valueOf(excelForm.getCompanyId())));
      in.add(businessDate);
      in.add("");
      in.add(this.dateFormat);
      in.add(CommonFunction.checkNull(excelForm.getMakerId()));

      out.add(Integer.valueOf(noOfRejectedRecord));
      out.add(Integer.valueOf(noOfUploadedRecord));
      out.add(s1);
      out.add(s2);

      logger.info("RECEIPT_BULK_UPLOAD_INSTRUMENT(" + in.toString() + "," + out.toString() + ")");
      outMessages = (ArrayList)ConnectionUploadDAO.callSP("RECEIPT_BULK_UPLOAD_INSTRUMENT", in, out);
      in.clear();
      in = null;
      out.clear();
      out = null;
      noOfRejectedRecord = Integer.parseInt((String)outMessages.get(0));
      noOfUploadedRecord = Integer.parseInt((String)outMessages.get(1));
      s1 = (String)outMessages.get(2);
      s2 = (String)outMessages.get(3);
      flag = s1;

      logger.info("In saveReceipt() flag:----" + flag);
      logger.info("In saveReceipt() s2:----" + s2);

      request.setAttribute("errorMsg", s2);

      outMessages.clear();
      outMessages = null;
    }
    catch (SQLException exp)
    {
      exp.printStackTrace();
    }
    catch (Exception e) {
      e.printStackTrace();
    } finally {
      s1 = null;
      s2 = null;
      businessDate = null;
      ConnectionUploadDAO.closeConnection(this.con, this.csmt, this.rs);
    }

    return flag;
  }

public boolean checkPreviousFiles(String filename)
{
  int count = 0;
  boolean flag = true;
  try
  {
    this.con = ConnectionUploadDAO.getConnection();
    PreparedStatement ps = this.con.prepareStatement("select count(UPLOADED_FILE_NAME) from gl_excel_uploading_m where UPLOADED_FILE_NAME = ?");
    logger.info("select count(UPLOADED_FILE_NAME) from gl_excel_uploading_m where UPLOADED_FILE_NAME = ?");
    logger.info(filename);
    ps.setString(1, filename);
    ResultSet result = ps.executeQuery();

    while (result.next())
    {
      count = Integer.parseInt(result.getString(1));
    }

    result.close();
    this.con.close();

    if (count == 0)
    {
      return false;
    }

    return true;
  }
  catch (Exception e)
  {
    logger.info("problem in checkPreviousFiles()");
    e.printStackTrace();
  }

  return flag;
}
//space end by raj 
//raj start for recept upload
public boolean uploadCsv_Receipt(HttpServletRequest request, HttpServletResponse response, String strFileName, CreditMgmtExcelSheetUploadVoForm excelForm)
{
  ArrayList alDeleteQuery = new ArrayList(1);
  StringBuffer makerID = null;
  boolean status = false;
  String fileNameWithPath = excelForm.getFilePathWithName();
  try {
    makerID = new StringBuffer(CommonFunction.checkNull(excelForm.getMakerId()));
    String query1 = "select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='GL_CSV_EXCELUPLOAD'";
    String rpt1 = ConnectionUploadDAO.singleReturn(query1);
    query1 = null;

    String strDeleteQuery = "DELETE FROM CR_INSTRUMENT_DTL_TEMP WHERE MAKER_ID = '" + makerID + "' ";
    alDeleteQuery.add(strDeleteQuery);
    boolean status1 = ConnectionUploadDAO.sqlInsUpdDelete(alDeleteQuery);

    if (status1)
      logger.info("In uploadCsv_Receipt() Rows is deleted....");
    else {
      logger.info("In uploadCsv_Receipt Row is not deleted......");
    }

    KettleEnvironment.init(false);
    EnvUtil.environmentInit();
    TransMeta transMeta = new TransMeta(rpt1.concat("\\receipt-excel-uploading.ktr"));
    Trans trans = new Trans(transMeta);

    trans.setParameterValue("filePathWithName", fileNameWithPath);
    trans.setParameterValue("userID", makerID.toString());

    trans.execute(null);
    trans.waitUntilFinished();

    if (trans.getErrors() > 0)
    {
      status = false;
      throw new RuntimeException("There were errors during transformation execution." + trans.getErrors());
    }
    status = true;

    transMeta.clearCaches();
    transMeta.clear();
    rpt1 = null;
    trans.clearParameters();
    trans.cleanup();
    strDeleteQuery = null;
  }
  catch (Exception e) {
    logger.info("In uploadCsv_Receipt() Problem is ---->" + e.getMessage());
  } finally {
    makerID = null;
    ConnectionUploadDAO.closeConnection(this.con, this.csmt, this.rs);
  }

  return status;
}

// raj end 
public boolean uploadCsv(HttpServletRequest request,HttpServletResponse response,String strFileName,CreditMgmtExcelSheetUploadVoForm  excelForm)
{
	   con = ConnectionDAO.getConnection();	
	   ArrayList     alDeleteQuery  = new ArrayList(1);
	   StringBuffer makerID=null;
	   boolean status=false;
	   try { 
		   	makerID=new StringBuffer(CommonFunction.checkNull(excelForm.getMakerId()));
		   	String query1="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='GL_CSV_EXCELUPLOAD'";
			String rpt1=ConnectionDAO.singleReturn(query1);
			query1=null;
			
		   	String strDeleteQuery = "DELETE FROM temp_gl_voucher_upload WHERE user_id = '"+makerID+"' ";
		   	alDeleteQuery.add(strDeleteQuery);
		   	boolean status1=ConnectionDAO.sqlInsUpdDelete(alDeleteQuery);
			   	
	     	  if(status1) {
	     		  logger.info("Row is deleted....");
	     	  }else{
	     		  logger.info("Row is not deleted......");
	     	  }
	     	
	     	  //Code for calling Kettle Transformation by Ankit
	     	  
	     	  KettleEnvironment.init(false); 
	     	  EnvUtil.environmentInit(); 
	     	  TransMeta transMeta = new TransMeta(rpt1.concat("\\voucher-excel-uploading.ktr")); 
	     	  Trans trans = new Trans(transMeta); 

	     	  trans.execute(null); // You can pass arguments instead of null. 
	     	  trans.waitUntilFinished(); 
	     	 
	     	  if ( trans.getErrors() > 0 )  { 
	     		  status=false;
	     		  throw new RuntimeException( "There were errors during transformation execution."+trans.getErrors() ); 
	     	  } else{
	     		 status=true;
	     	  }
		   
	     	 //Code for calling kettle job 
	     	
	     	 //  //JobEntryLoader.init();
	     	 //  //StepLoader.init();
	     	 //  //StepLoader stepLoader = StepLoader.getInstance();
	     	 //  //LogWriter log = LogWriter.getInstance(LogWriter.LOG_LEVEL_BASIC);
	     	 //	 KettleEnvironment.init(false); 
	     	 //	 EnvUtil.environmentInit();
	     	 //	// KettleEnvironment stepLoader = KettleEnvironment.init(false);
	     	 //        JobMeta jobMeta = new JobMeta(null, "D:\\Users\\Ankit\\Desktop\\UPLOAD FILE June-2013\\voucher-excel.kjb", null, null);
			 //        Job job = new Job(null, jobMeta);
			 //        job.setVariable("testvar", "testvalue");
			 //        job.initializeVariablesFrom(null);
			 //        job.getJobMeta().setInternalKettleVariables(job);
			 //        job.execute(0, null);
			 //        job.waitUntilFinished();
			 //        if ( job.getErrors() > 0 ){
			 //            throw new RuntimeException( "There were errors during job execution." );
			 //        }
	     	transMeta.clearCaches();
	     	transMeta.clear();
	     	rpt1=null;
	     	strDeleteQuery=null;
	     	
      } 
      catch (Exception e) {
    	  logger.info("Problem is ---->"+e.getMessage());
	}finally {
			makerID=null;
			ConnectionDAO.closeConnection(con,csmt,rs);
			
	} 

	return status;
}



public boolean docUploadForExcelForBankUpload(HttpServletRequest request, FormFile docFile) {
	boolean status=false;
	String fileName="";
	String filePath="";
	String message="";
	String filePathWithName="";
	String fileNameChange="";
	HttpSession session=request.getSession(false);
	UserObject sessUser = (UserObject) session.getAttribute("userobject");
	String user_Id="";
	try{
		if(sessUser!=null){
			user_Id = sessUser.getUserId();
		}
		String query="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='UPLOAD_PATH'";
		String rpt=ConnectionUploadDAO.singleReturn(query);
		query=null;
		File directory = new File(rpt+"/");
		boolean isDirectory = directory.isDirectory();
		
//		String query1="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='GL_CSV_EXCELUPLOAD'";
//		String rpt1=ConnectionUploadDAO.singleReturn(query1);
//		query1=null;
		
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
						//Create file
						File fileToCreate = new File(filePath, fileName);
						int fileSize = docFile.getFileSize(); //26314400 bytes = 25 MB
						logger.info("docUpload :Size of file==>> "+fileSize);
					//	logger.info("docUpload :KTR Path ==>> "+rpt1);
							if(fileSize<26314400){
								FileOutputStream fileOutStream = new FileOutputStream(fileToCreate);
								fileOutStream.write(docFile.getFileData());
								fileOutStream.flush();
								fileOutStream.close();
								message="O";
								/* comment by Ankit for file path parameterized changes
								 
								Writer output = new BufferedWriter(new FileWriter(rpt1.concat("\\File_path\\filePath.txt")));
							    try {
							      //FileWriter always assumes default encoding is OK!
							      output.write( filePathWithName );
							    }finally {
							    	output.flush();
							    	output.close();
							    }
							    */
								status=true;	        		
							}else{
								message="E";
								logger.info("File size exceeds the upper limit of 25 MB.");
								status=false;
							}
				}else{
					message="S";
					//logger.info("Please select a File.");
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
	request.setAttribute("fileName", fileName);
	request.setAttribute("filePath", filePath);
	request.setAttribute("message",message);
	request.setAttribute("filePathWithName", filePathWithName);
	fileName=null;
	filePath=null;
	message=null;
	filePathWithName=null;
	fileNameChange=null;
	return status;		
}

//add by Ashish
public boolean uploadCsv_recoveryUpload(HttpServletRequest request,HttpServletResponse response,String strFileName,CreditMgmtExcelSheetUploadVoForm  excelForm)
{

	 //  con = ConnectionUploadDAO.getConnection();	
	   ArrayList alDeleteQuery  = new ArrayList(1);
	   StringBuffer makerID=null;
	   boolean status=false;
	  String filePath=request.getAttribute("filePath").toString();
	  String fileName=request.getAttribute("fileName").toString();
	  String fileNameWithPath= filePath.concat("\\"+fileName);
	  
	   
	   try { 
		   	makerID=new StringBuffer(CommonFunction.checkNull(excelForm.getMakerId()));
			String query1="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='UPLOAD_PATH'";
			String rpt1=ConnectionUploadDAO.singleReturn(query1);
			query1=null;
			String businessDate=CommonFunction.checkNull(excelForm.getBusinessDate());
		   	String strDeleteQuery = "DELETE FROM recovery_executive_temp where user_id='"+makerID+"' ";
		   	alDeleteQuery.add(strDeleteQuery);
		   	boolean status1=ConnectionUploadDAO.sqlInsUpdDelete(alDeleteQuery);
			   	
	     	  if(status1) {
	     		  logger.info("IN uploadCsv_cmsUpload() Row is deleted....");
	     	  }else{
	     		  logger.info("IN uploadCsv_cmsUpload() Row is not deleted......");
	     	  }
	     	
	     	  
	     	  KettleEnvironment.init(false); 
	     	  EnvUtil.environmentInit(); 
	     	  TransMeta transMeta = new TransMeta(rpt1.concat("\\Recovery-Executive-Upload.ktr")); 
	     	  Trans trans = new Trans(transMeta); 
	     	 // set parameter. same can be used inside steps in transformation 
		     	 trans.setParameterValue("filePathWithName",fileNameWithPath);
		     	 trans.setParameterValue("userID",makerID.toString());
		     	// trans.setParameterValue("businessDate",businessDate.toString());
	     	  trans.execute(null); // You can pass arguments instead of null. 
	     	  trans.waitUntilFinished(); 
	     	 
	     	  if ( trans.getErrors() > 0 )  { 
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
		     	strDeleteQuery=null;
	     	
  } 
  catch (Exception e) {
	  logger.info("In uploadCsv_notepadUpload() Problem is ---->"+e.getMessage());
	}finally {
			makerID=null;
			ConnectionUploadDAO.closeConnection(con,csmt,rs);
			
	} 

	return status;
	
}
public String recoveryUploadSave(HttpServletRequest request,CreditMgmtExcelSheetUploadVoForm excelForm) {
	
	String flag=null;
	   con = ConnectionUploadDAO.getConnection();	
	   rs=null;
	   String s2 =null;
	   StringBuffer	makerID=new StringBuffer(CommonFunction.checkNull(excelForm.getMakerId()));
	try { 	 
		logger.info("dateFormat... "+dateFormat);
		con.setAutoCommit(false);
		csmt = (CallableStatement) con.prepareCall("{CALL RECOVERY_EXECUTIVE(?,?,?)}");
/*		csmt.setString(1,CommonFunction.checkNull(excelForm.getCompanyId()));
		csmt.setString(2,CommonFunction.checkNull(excelForm.getBusinessDate()));
		csmt.setString(3,"");
		csmt.setString(4,dateFormat);		
		csmt.setString(5,CommonFunction.checkNull(excelForm.getMakerId()));
		
		csmt.registerOutParameter(6, java.sql.Types.VARCHAR);
		csmt.registerOutParameter(7, java.sql.Types.VARCHAR);*/
		csmt.setString(1,makerID.toString());
		csmt.registerOutParameter(2, java.sql.Types.VARCHAR);
		csmt.registerOutParameter(3, java.sql.Types.VARCHAR);
	
		rs = csmt.executeQuery();
		
		flag = csmt.getString(2);
		s2 = csmt.getString(3);
		
		 logger.info("In saveBounce() flag:----"+flag);
		 logger.info("In saveBounce() s2:----"+s2);
		 con.commit();
		 request.setAttribute("errorMsg", s2);		 

	} catch (SQLException exp) {

		exp.printStackTrace();

	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		   s2=null;
		   ConnectionUploadDAO.closeConnection(con,csmt,rs);			
	}
	return flag;
}

		public boolean uploadCsv_Stationary(HttpServletRequest request,HttpServletResponse response,String strFileName,CreditMgmtExcelSheetUploadVoForm  excelForm)
		{
			  // con = ConnectionUploadDAO.getConnection();	
			   ArrayList     alDeleteQuery  = new ArrayList(1);
			   StringBuffer makerID=null;
			   boolean status=false;
			   String filePath=request.getAttribute("filePath").toString();
			   String fileName=request.getAttribute("fileName").toString();
			   String fileNameWithPath= filePath.concat("\\"+fileName);
			   try { 
				   	makerID=new StringBuffer(CommonFunction.checkNull(excelForm.getMakerId()));
					String query1="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='GL_CSV_EXCELUPLOAD'";
					String rpt1=ConnectionUploadDAO.singleReturn(query1);
					query1=null;
					
				   	String strDeleteQuery = "DELETE FROM GL_STATIONARY_UPLOAD_TMP WHERE MAKER_ID = '"+makerID+"' ";
				   	alDeleteQuery.add(strDeleteQuery);
				   	boolean status1=ConnectionUploadDAO.sqlInsUpdDelete(alDeleteQuery);
					   	
			     	  if(status1) {
			     		  logger.info("In uploadCsv_Stationary() Row is deleted....");
			     	  }else{
			     		  logger.info("In uploadCsv_Stationary() Row is not deleted......");
			     	  }
			     	
			     	  
			     	  KettleEnvironment.init(false); 
			     	  EnvUtil.environmentInit(); 
			     	  TransMeta transMeta = new TransMeta(rpt1.concat("\\stationary-excel-uploading.ktr")); 
			     	  Trans trans = new Trans(transMeta); 
		
			     	 // set parameter. same can be used inside steps in transformation 
				     	 trans.setParameterValue("filePathWithName",fileNameWithPath);
				     	 trans.setParameterValue("userID",makerID.toString());
			     	  trans.execute(null); // You can pass arguments instead of null. 
			     	  trans.waitUntilFinished(); 
			     	 
			     	  if ( trans.getErrors() > 0 )  { 
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
				     	strDeleteQuery=null;
			     	
		   } 
		      catch (Exception e) {
		    	  logger.info("In uploadCsv_Stationary() Problem is ---->"+e.getMessage());
			}finally {
					makerID=null;
					ConnectionUploadDAO.closeConnection(con,csmt,rs);
					
			} 
		
			return status;
		}
		
		public String saveStationary(HttpServletRequest request,CreditMgmtExcelSheetUploadVoForm excelForm) {
			   String flag=null;
			   con = ConnectionUploadDAO.getConnection();	
			   rs=null;
			 
			   String s2=null;
			   
			try { 	 
				con.setAutoCommit(false);
				csmt = (CallableStatement) con.prepareCall("{CALL STATIONARY_UPLOAD(?,STR_TO_DATE(?,'"+dateFormat+"'),?,?)}");
				csmt.setString(1,CommonFunction.checkNull(excelForm.getMakerId()));
				csmt.setString(2,CommonFunction.checkNull(excelForm.getBusinessDate()));
		
				csmt.registerOutParameter(3, java.sql.Types.VARCHAR);
				csmt.registerOutParameter(4, java.sql.Types.VARCHAR);
			
				rs = csmt.executeQuery();
				
				flag = csmt.getString(3);
				s2 = csmt.getString(4);
		
				 logger.info("In saveStationary() flag:----"+flag);
				 logger.info("In saveStationary() s2:----"+s2);
				 
				 request.setAttribute("errorMsg", s2);		 
				 con.commit();
			} catch (SQLException exp) {
		
				exp.printStackTrace();
		
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				   s2=null;
				   ConnectionUploadDAO.closeConnection(con,csmt,rs);			
			}
			return flag;
		}

//		@Override
//		public ArrayList reportAdHoc(CreditMgmtExcelSheetUploadVoForm excelForm) {
//			// TODO Auto-generated method stub
//			return null;
//		}

				
//end by Ashish
		//comment open by raj
		public String saveBounce(HttpServletRequest request,CreditMgmtExcelSheetUploadVoForm excelForm) {
			   String flag=null;
			   con = ConnectionUploadDAO.getConnection();	
			   rs=null;
			   String s2 =null;
			   
			try { 	 
				logger.info("dateFormat... "+dateFormat);
				con.setAutoCommit(false);
				csmt = (CallableStatement) con.prepareCall("{CALL Bulk_Upload_Instrument_Dtl(?,STR_TO_DATE(?,'"+dateFormat+"'),?,?,?,?,?,?,?)}");
				csmt.setString(1,CommonFunction.checkNull(excelForm.getCompanyId()));
				csmt.setString(2,CommonFunction.checkNull(excelForm.getBusinessDate()));
				csmt.setString(3,"");
				csmt.setString(4,dateFormat);		
				csmt.setString(5,CommonFunction.checkNull(excelForm.getMakerId()));
				
				csmt.registerOutParameter(6, java.sql.Types.VARCHAR);
				csmt.registerOutParameter(7, java.sql.Types.VARCHAR);
				csmt.registerOutParameter(8, java.sql.Types.VARCHAR);
				csmt.registerOutParameter(9, java.sql.Types.VARCHAR);
			
				rs = csmt.executeQuery();
				
				flag = csmt.getString(8);
				s2 = csmt.getString(9);
				
				 logger.info("In saveBounce() flag:----"+flag);
				 logger.info("In saveBounce() s2:----"+s2);
				 con.commit();
				 request.setAttribute("errorMsg", s2);		 

			} catch (SQLException exp) {

				exp.printStackTrace();

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				   s2=null;
				   ConnectionUploadDAO.closeConnection(con,csmt,rs);			
			}
			return flag;
		}
		//comment open end by raj 
		public boolean uploadCsv_Bounce(HttpServletRequest request,HttpServletResponse response,String strFileName,CreditMgmtExcelSheetUploadVoForm  excelForm)
		{
			 //  con = ConnectionUploadDAO.getConnection();	
			   ArrayList  alDeleteQuery  = new ArrayList(1);
			   StringBuffer makerID=null;
			   String fileNameWithPath=excelForm.getFilePathWithName();

			   boolean status=false;
			   try { 
				   	makerID=new StringBuffer(CommonFunction.checkNull(excelForm.getMakerId()));
				   	String query1="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='GL_CSV_EXCELUPLOAD'";
					String rpt1=ConnectionUploadDAO.singleReturn(query1);
					query1=null;
					
				   	String strDeleteQuery = "DELETE FROM CHEQUE_STATUS_UPLOAD_TMP WHERE MAKER_ID = '"+makerID+"' ";
				   	alDeleteQuery.add(strDeleteQuery);
				   	boolean status1=ConnectionUploadDAO.sqlInsUpdDelete(alDeleteQuery);
					   	
			     	  if(status1) {
			     		  logger.info("IN uploadCsv_Bounce() Row is deleted....");
			     	  }else{
			     		  logger.info("In uploadCsv_Bounce() Row is not deleted......");
			     	  }
			     	 logger.info("1111111111111111111"); 	  
			     	  KettleEnvironment.init(false); 
			     	  EnvUtil.environmentInit(); 
			     	 logger.info("22222222222222222"+rpt1); 	  
			     	  TransMeta transMeta = new TransMeta(rpt1.concat("\\bounce-excel-uploading.ktr")); 
			     	  Trans trans = new Trans(transMeta); 
			     	 logger.info("333333333333333333"+fileNameWithPath); 	  
			     	// set parameter. same can be used inside steps in transformation 
				     	 trans.setParameterValue("filePathWithName",fileNameWithPath);
				     	 trans.setParameterValue("userID",makerID.toString());
				     	 logger.info("444444444444444444444"); 	  
			     	  trans.execute(null); // You can pass arguments instead of null. 
			     	  trans.waitUntilFinished(); 
			     	 
			     	  if (trans.getErrors() > 0 ){ 
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
				     	strDeleteQuery=null;
			     	
		      } 
		      catch (Exception e) {
		    	  logger.info("In uploadCsv_Bounce() Problem is ---->"+e.getMessage());
			}finally {
					makerID=null;
					ConnectionUploadDAO.closeConnection(con,csmt,rs);
					
			} 

			return status;
		}
		
		//Changes Start for Allocation Process
		public boolean uploadCsv_AllocationUpload(HttpServletRequest request,HttpServletResponse response,String strFileName,CreditMgmtExcelSheetUploadVoForm  excelForm)
		{

			 //  con = ConnectionUploadDAO.getConnection();	
			   ArrayList alDeleteQuery  = new ArrayList(1);
			   StringBuffer makerID=null;
			   boolean status=false;
			   String fileNameWithPath=excelForm.getFilePathWithName();
			   
			   try { 
				   	makerID=new StringBuffer(CommonFunction.checkNull(excelForm.getMakerId()));
					String query1="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='GL_CSV_EXCELUPLOAD'";
					String rpt1=ConnectionUploadDAO.singleReturn(query1);
					query1=null;
					String businessDate=CommonFunction.checkNull(excelForm.getBusinessDate());
				   	String strDeleteQuery = "DELETE FROM temp_allocation_process_upload where maker_id='"+makerID+"' ";
				   	alDeleteQuery.add(strDeleteQuery);
				   	boolean status1=ConnectionUploadDAO.sqlInsUpdDelete(alDeleteQuery);
					   	
			     	  if(status1) {
			     		  logger.info("IN uploadCsv_AllocationUpload() Row is deleted....");
			     	  }else{
			     		  logger.info("IN uploadCsv_AllocationUpload() Row is not deleted......");
			     	  }
			     	
			     	  
			     	  KettleEnvironment.init(false); 
			     	  EnvUtil.environmentInit(); 
			     	  TransMeta transMeta = new TransMeta(rpt1.concat("\\Allocation-Process-Upload.ktr")); 
			     	  Trans trans = new Trans(transMeta); 
			     	 // set parameter. same can be used inside steps in transformation 
				     	 trans.setParameterValue("filePathWithName",fileNameWithPath);
				     	 trans.setParameterValue("userID",makerID.toString());
				     	// trans.setParameterValue("businessDate",businessDate.toString());
			     	  trans.execute(null); // You can pass arguments instead of null. 
			     	  trans.waitUntilFinished(); 
			     	 
			     	  if ( trans.getErrors() > 0 )  { 
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
				     	strDeleteQuery=null;
			     	
		     } 
		     catch (Exception e) {
		   	  logger.info("In uploadCsv_AllocationUpload() Problem is ---->"+e.getMessage());
			}finally {
					makerID=null;
					ConnectionUploadDAO.closeConnection(con,csmt,rs);
					
			} 

			return status;
			
		}

		public String AllocationProcessUploadSave(HttpServletRequest request,CreditMgmtExcelSheetUploadVoForm excelForm) {
				  
			String s1=null;
			String rejectFlg = null;
			String uplodFlg = null;
			   con = ConnectionUploadDAO.getConnection();	
			   rs=null;
			   String s2 =null;
				   
			try { 	 
				logger.info("dateFormat... "+dateFormat);
				con.setAutoCommit(false);
				csmt = (CallableStatement) con.prepareCall("{CALL Allocation_Process_upl(?,STR_TO_DATE(?,'"+dateFormat+"'),?,?,?,?)}");
				
				logger.info("csmt:"+csmt);
				csmt.setString(1,CommonFunction.checkNull(excelForm.getMakerId()));
				csmt.setString(2,CommonFunction.checkNull(excelForm.getBusinessDate()));
			   
				csmt.registerOutParameter(3, java.sql.Types.INTEGER);
				csmt.registerOutParameter(4, java.sql.Types.INTEGER);
				csmt.registerOutParameter(5, java.sql.Types.VARCHAR);
				csmt.registerOutParameter(6, java.sql.Types.VARCHAR);
				rs = csmt.executeQuery();
				rejectFlg=csmt.getString(3);
				uplodFlg=csmt.getString(4);
				s1 = csmt.getString(5);
				s2 = csmt.getString(6);
			    
				
				 logger.info("In AllocationProcessUploadSave() rejectFlg:----"+rejectFlg);
				 logger.info("In AllocationProcessUploadSave() uplodFlg:----"+uplodFlg);
				 logger.info("In AllocationProcessUploadSave() s1:----"+s1);
				 logger.info("In AllocationProcessUploadSave() s2:----"+s2);
				 con.commit();
				 request.setAttribute("errorMsg", s2);		 

			} catch (SQLException exp) {

				exp.printStackTrace();

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				   s2=null;
				   rejectFlg=null;
				   uplodFlg=null;
				   ConnectionUploadDAO.closeConnection(con,csmt,rs);			
			}
			return s1;
		}
		//Changes End for Allocation Process
		public boolean uploadCsv_cmsUpload(HttpServletRequest request,HttpServletResponse response,String strFileName,CreditMgmtExcelSheetUploadVoForm  excelForm)
		{
			 //  con = ConnectionUploadDAO.getConnection();	
			   ArrayList alDeleteQuery  = new ArrayList(1);
			   StringBuffer makerID=null;
			   boolean status=false;
			   String fileNameWithPath=excelForm.getFilePathWithName();
			   
			   try { 
				   	makerID=new StringBuffer(CommonFunction.checkNull(excelForm.getMakerId()));
					String query1="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='GL_CSV_EXCELUPLOAD'";
					String rpt1=ConnectionUploadDAO.singleReturn(query1);
					query1=null;
					String businessDate=CommonFunction.checkNull(excelForm.getBusinessDate());
				   	String strDeleteQuery = "DELETE FROM CMS_UPLOAD_TEMP WHERE MAKER_ID = '"+makerID+"' ";
				   	alDeleteQuery.add(strDeleteQuery);
				   	boolean status1=ConnectionUploadDAO.sqlInsUpdDelete(alDeleteQuery);
					   	
			     	  if(status1) {
			     		  logger.info("IN uploadCsv_cmsUpload() Row is deleted....");
			     	  }else{
			     		  logger.info("IN uploadCsv_cmsUpload() Row is not deleted......");
			     	  }
			     	
			     	  
			     	  KettleEnvironment.init(false); 
			     	  EnvUtil.environmentInit(); 
			     	  TransMeta transMeta = new TransMeta(rpt1.concat("\\CMS_UPLOAD.ktr")); 
			     	  Trans trans = new Trans(transMeta); 
			     	 // set parameter. same can be used inside steps in transformation 
				     	 trans.setParameterValue("filePathWithName",fileNameWithPath);
				     	 trans.setParameterValue("userID",makerID.toString());
				     	 trans.setParameterValue("businessDate",businessDate.toString());
			     	  trans.execute(null); // You can pass arguments instead of null. 
			     	  trans.waitUntilFinished(); 
			     	 
			     	  if ( trans.getErrors() > 0 )  { 
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
				     	strDeleteQuery=null;
			     	
		      } 
		      catch (Exception e) {
		    	  logger.info("In uploadCsv_notepadUpload() Problem is ---->"+e.getMessage());
			}finally {
					makerID=null;
					ConnectionUploadDAO.closeConnection(con,csmt,rs);
					
			} 

			return status;
		}


		public String save_CmsUpload(HttpServletRequest request,CreditMgmtExcelSheetUploadVoForm excelForm) {
			
			String flag=null;
			   con = ConnectionUploadDAO.getConnection();	
			   rs=null;
			   String s2 =null;
			   
			try { 	 
				logger.info("dateFormat... "+dateFormat);
				con.setAutoCommit(false);
				csmt = (CallableStatement) con.prepareCall("{CALL CMS_UPLOAD_PROC(?,STR_TO_DATE(?,'"+dateFormat+"'),?,?,?,?,?,?,?)}");
				csmt.setString(1,CommonFunction.checkNull(excelForm.getCompanyId()));
				csmt.setString(2,CommonFunction.checkNull(excelForm.getBusinessDate()));
				csmt.setString(3,"");
				csmt.setString(4,dateFormat);		
				csmt.setString(5,CommonFunction.checkNull(excelForm.getMakerId()));
				
				csmt.registerOutParameter(6, java.sql.Types.VARCHAR);
				csmt.registerOutParameter(7, java.sql.Types.VARCHAR);
				csmt.registerOutParameter(8, java.sql.Types.VARCHAR);
				csmt.registerOutParameter(9, java.sql.Types.VARCHAR);
			
				rs = csmt.executeQuery();
				
				flag = csmt.getString(8);
				s2 = csmt.getString(9);
				
				 logger.info("In saveBounce() flag:----"+flag);
				 logger.info("In saveBounce() s2:----"+s2);
				 con.commit();
				 request.setAttribute("errorMsg", s2);		 

			} catch (SQLException exp) {

				exp.printStackTrace();

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				   s2=null;
				   ConnectionUploadDAO.closeConnection(con,csmt,rs);			
			}
			return flag;
		}
}