package com.cm.daoImplMYSQL;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

import com.cm.dao.LoanDetailUploadDAO;
import com.cm.dao.ManualAdviceUploadDAO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;
import com.login.roleManager.UserObject;

public class LoanDetailUploadDAOImpl  implements LoanDetailUploadDAO {
	private static final Logger logger=Logger.getLogger(LoanDetailUploadDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dateFormanualUpload=resource.getString("lbl.dateFormanualUpload");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	
	
	public String getUploadLoanDetailData(String userId){
		logger.info("In getUploadManualData method of  ManualDeviationUploadDAOImpl::::::::::::::::");
		
		Connection conn=null;
		Statement stmt =null;
		StringBuffer data= new StringBuffer();
		String fileName="";
		try
		{
		if(userId!=null)
		{
		String query= "SELECT  file_name FROM upload_SUMMARY WHERE MAKER_ID='"+userId +"' AND MAKER_DATE=CURRENT_DATE";
		logger.info("In getUploadData()...............query...........DAOImpl "+query);
		conn= ConnectionDAO.getConnection();
		stmt= conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		while(rs.next())
		{
			data.append("~");
			data.append(rs.getString(1).toString());
	    }
	
		}

		}
		catch (Exception e) {
			e.printStackTrace();
		}

		finally
				{
					try {
						// conn.commit();
						stmt.close();
						conn.close();						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}//checking how many value have come from database..
		
		logger.info(" ..getUploadData()..File Name..is...."+data);

		 fileName=data.toString();
		return fileName; 

	}
public boolean  whereToUpload(HttpServletRequest request,FormFile file){
	boolean status=false;
	String fileName="";
	String filePath="";
	String message="";
	String rpt="";
	try{
		String query="SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='MANUAL_ADVICE_UPLOAD_PATH'";
		logger.info("In docUpload(): query:==>>"+query);
		
		 rpt=ConnectionDAO.singleReturn(query);
		query=null;
		logger.info("The name you have entered is a file : "  +file);
		File directory = new File(rpt+"/");
		logger.info("vinod:docUpload:directory:==>"+directory);
		boolean isDirectory = directory.isDirectory();
		logger.info("vinod:docUpload:isDirectory:==>"+isDirectory);
        fileName    = file.getFileName();
    //Get the servers upload directory real path name
        filePath = directory.getAbsolutePath();
    /* Save file on the server */
        if(!fileName.equals("")){  
        	logger.info("Server path: filePath:==>>" +filePath);
        	//Create file
        	File fileToCreate = new File(filePath, fileName);
        		int fileSize = file.getFileSize(); //1048576 bytes = 1 MB
        		logger.info("docUpload :Size of file==>> "+fileSize);
        		if(fileSize<1048576)
        		{
	        			FileOutputStream fileOutStream = new FileOutputStream(fileToCreate);
        				fileOutStream.write(file.getFileData());
        				fileOutStream.flush();
        				fileOutStream.close();
        				message="O";
        				status=true;	        		
        		}
        		else
        		{
        			message="E";
        			//logger.info("File size exceeds the upper limit of 1 MB.");
		        	status=false;
        		}
        }
        else
        {
        	message="S";
        	//logger.info("Please select a File.");
        	status=false;
        }
        
	}catch(Exception e){
		e.printStackTrace();
	}
	finally{
		rpt=null;
	}
	request.setAttribute("fileName", fileName);
	request.setAttribute("filePath", filePath);
	request.setAttribute("message",message);
	fileName=null;
	filePath=null;
	message=null;
	return status;		
}
public int countLine(String fileName){
 	logger.info("In countLine() method ");
	 int rowTotalNum=0;
	 String query="SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='MANUAL_ADVICE_UPLOAD_PATH'";				
	 String rpt=ConnectionDAO.singleReturn(query);		
	 File directory = new File(rpt+"/"+fileName);
	 logger.info("File Name......... ==>>"+directory);
	 query=null;
 try
	{ 
	 //logger.info("in method of file.....................++++++++++++++++"+directory.exists());
	 if(directory.exists())
      	{
		// logger.info("in method of file....................."+directory.length());
		 if(directory.length()>0){
	 Workbook workbook = Workbook.getWorkbook(directory);
	 
	// logger.info("in method of file.....................");
	 Sheet sheet;
	  sheet = workbook.getSheet(0);
	  rowTotalNum=sheet.getRows();
	  logger.info("Total Lines............. ==>>"+rowTotalNum);
		 }
		 else{
			 rowTotalNum=-1;
			// logger.info("singh singh +++++++++++............. ==>>"+rowTotalNum);
		 }
     
    	
      	}
      else
      {
    	  rowTotalNum=-2;
    	  logger.info("File not exitxt..");

      }
	}
	catch (Exception e) 
	{
		e.printStackTrace();
	}
	  return rowTotalNum;	
}
public boolean readExcelforLoanDetailUpload(HttpServletRequest request,HttpServletResponse response,String strFileName)
{
    logger.info("In readExcelforManualUpload() method ");
    HttpSession   session= null;
    ArrayList alFinalQry= new ArrayList();
    ArrayList alDeleteQuery= new ArrayList(1);
    ArrayList dataList=null;
    ArrayList arr=null;
    ArrayList subList= null;    
    int noOfRejectedRecord =0;
	int noOfUploadedRecord=0;
    int rowTotalNum = 0;
    int row=0;
    int col=0;
    String msg="";    
    boolean singleObject;
    File objFile1=null;
    boolean status=false;
    String userId="";
    String bDate="";    
    boolean insertStatus=false;	  
    PrepStmtObject insertPrepStmtObject=null;
    String query=null;
    String strPath=null;
    Workbook workbook =null;
    try 
    {      
      session     = request.getSession(false);
      UserObject userobj=(UserObject)session.getAttribute("userobject");	    
	  if(userobj!=null)
	  {
		  userId=userobj.getUserId();
		  bDate=userobj.getBusinessdate();
	  }
      if(!strFileName.equals(""))
      {
    	  query="SELECT  PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='MANUAL_ADVICE_UPLOAD_PATH'";
    	  logger.info("In readExcelforManualUpload(): query:==>>"+query);
      
    	  strPath=ConnectionDAO.singleReturn(query);
    	  strPath=strPath + "/" + strFileName;
    	  objFile1=new File(strPath);
    	  query=null;    	  
    	  try 
    	  {    	  
    		  String strCountDeleteQuery =ConnectionDAO.singleReturn("SELECT COUNT(1) FROM loan_detail_upload_tmp WHERE MAKER_ID = '"+userId+"' ");
    		  if(!strCountDeleteQuery.equalsIgnoreCase("0"))
    		  {     	  
			   	  String strDeleteQuery = "DELETE FROM loan_detail_upload_tmp WHERE MAKER_ID = '"+userId+"' ";
			      logger.info(" Delete query strDeleteQuery"+strDeleteQuery);
			      alDeleteQuery.add(strDeleteQuery);
			      boolean status1=ConnectionDAO.sqlInsUpdDelete(alDeleteQuery);
			      strDeleteQuery=null;
			      logger.info(" Deleting query is..... ==>>"+status1);
          	   }

    		  dataList=new ArrayList();
              workbook = Workbook.getWorkbook(new File(strPath));
              String sheetName[]=workbook.getSheetNames();
              Sheet sheet;
              Cell xlsCell;
              Cell[] cell;
              for(int p=0; p<sheetName.length; p++)
              {
            	  sheet = workbook.getSheet(p);
            	  for(int i=0; i<sheet.getRows(); i++)
            	  {
            		  cell = sheet.getRow(0);
            		  arr = new ArrayList();
                  	  for(int j=0; j<cell.length; j++)
            		  {                 	
            			  xlsCell = sheet.getCell(j,i);
                      	  arr.add(xlsCell.getContents().toString());
            		  } 
            		  dataList.add(arr);	 
            	  }
              }
        }
        catch(Exception e) 
        {e.printStackTrace();}
    	  
    	StringBuilder queryBatch=new StringBuilder();
    	queryBatch.append("SELECT IFNULL(MAX(UPLOAD_BATCH_ID),0)+1 NEXT_BATCH_ID FROM LOAN_DETAIL_UPLOAD_TMP ");
  		int batchId = Integer.parseInt((String)ConnectionDAO.singleReturn(queryBatch.toString()));
    	  
        StringBuffer bufInsSql = new StringBuffer();
	 	bufInsSql.append("INSERT INTO LOAN_DETAIL_UPLOAD_TMP " +
	 			"(UPLOAD_BATCH_ID, LOAN_NO,  " +
	 			"DEAL_SOURCE_TYPE, DEAL_SOURCE_NAME, LOAN_REPAYMENT_MODE, MAKER_ID, MAKER_DATE) VALUES ( ");
		bufInsSql.append(" ?," );//UPLOAD_BATCH_ID
		bufInsSql.append(" ?," );//LOAN_ID
		bufInsSql.append(" ?," ); //LOAN_REPAYMENT_MODE
		bufInsSql.append(" ?," ); //DEAL_SOURCE_TYPE
		bufInsSql.append(" ?," ); //DEAL_SOURCE_NAME
		bufInsSql.append(" ?," ); //MAKER_ID
		bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') " ); //MAKER_DATE  
		bufInsSql.append(" )" );         
        for(int i=1;i<dataList.size();i++)
        {	     	  
     	   subList=(ArrayList)dataList.get(i);
    	   if(subList.size()>0)
    	   {          				 
    		   insertPrepStmtObject = new PrepStmtObject();
    				    
    		   insertPrepStmtObject.addInt(batchId);
    					
    		   if(CommonFunction.checkNull(CommonFunction.checkNull(subList.get(0))).equalsIgnoreCase(""))
    					insertPrepStmtObject.addNull();
    			else
    					insertPrepStmtObject.addString(CommonFunction.checkNull(subList.get(0)).trim());	

    		   if(CommonFunction.checkNull(CommonFunction.checkNull(subList.get(1))).equalsIgnoreCase(""))
    					insertPrepStmtObject.addNull();
    			else
    					insertPrepStmtObject.addString(CommonFunction.checkNull(subList.get(1)).trim());	

    		   if(CommonFunction.checkNull(CommonFunction.checkNull(subList.get(2))).equalsIgnoreCase(""))
    					insertPrepStmtObject.addNull();
    			else
    					insertPrepStmtObject.addString(CommonFunction.checkNull(subList.get(2)).trim());	

    		   if(CommonFunction.checkNull(CommonFunction.checkNull(subList.get(3))).equalsIgnoreCase(""))
    					insertPrepStmtObject.addNull();
    			else
    					insertPrepStmtObject.addString(CommonFunction.checkNull(subList.get(3)).trim());	

    		   
    			if(CommonFunction.checkNull(userId).equalsIgnoreCase(""))
    					insertPrepStmtObject.addNull();
    			else
    					insertPrepStmtObject.addString(userId);	
    			
    			if(CommonFunction.checkNull(bDate).equalsIgnoreCase(""))
    					insertPrepStmtObject.addNull();
    			else
    					insertPrepStmtObject.addString(bDate);
       					
   				insertPrepStmtObject.setSql(bufInsSql.toString());
   				logger.info("IN readExcelforLoanDetailUpload() insert query1 : "+ insertPrepStmtObject.printQuery());
   				alFinalQry.add(insertPrepStmtObject);    						
   				insertPrepStmtObject=null;    							
   			}     			  
        }
        insertStatus=ConnectionDAO.sqlInsUpdDeletePrepStmt(alFinalQry); 
        logger.info("status::::::::::::::::::::::"+status);    
           
        ArrayList<Object> in =new ArrayList<Object>();
 		ArrayList<Object> out =new ArrayList<Object>();
 		ArrayList outMessages = new ArrayList();
 		String s1 = "";
 		String s2 = ""; 		
 		in.add(userId);
 		String makerDate="";
 		makerDate=CommonFunction.changeFormat(CommonFunction.checkNull(bDate).trim());
 		in.add(makerDate);
 		in.add(strFileName);
 		in.add(batchId);
 		out.add(noOfRejectedRecord);
		out.add(noOfUploadedRecord);
		out.add(s1);
	    out.add(s2); 		
	    logger.info(" Loan_Detail_Upload("+in.toString()+","+out.toString()+")");  
        outMessages=(ArrayList) ConnectionDAO.callSP("Loan_Detail_Upload",in,out); 
        in.clear();
        in=null;
        out.clear();
        out=null;        
        noOfRejectedRecord =Integer.parseInt((String)outMessages.get(0));
 	    noOfUploadedRecord=Integer.parseInt((String)outMessages.get(1));
        s1 = (String)outMessages.get(2);
 	    s2 = (String)outMessages.get(3);
 	    
 	   outMessages.clear();
 	   outMessages=null;
 	   
 	   logger.info("Loan_Detail_Upload OUT s1: "+s1);
 	   logger.info("Loan_Detail_Upload OUT s2: "+s2);
 	   logger.info("Loan_Detail_Upload OUT noOfRejectedRecord: "+noOfRejectedRecord);
 	   logger.info("Loan_Detail_Upload OUT noOfUploadedRecord: "+noOfUploadedRecord);
	 
		if((s1==null || s1.equals("S")) && insertStatus)
		{
			status=true;
			for(int i=1;i<dataList.size();i++)
	        {		
	     	   subList=(ArrayList)dataList.get(i);
	  			if(subList.size()>0){ 
					 String checkErrorQ="SELECT UPLOADED_FLAG FROM LOAN_DETAIL_UPLOAD_TMP WHERE LOAN_NO='"+ subList.get(0)+"'  AND MAKER_ID='"+userId+"' ";			
					 String checkErrorStatus=ConnectionDAO.singleReturn(checkErrorQ);
					 if(checkErrorStatus.equalsIgnoreCase("N"))
					 {
						 status=false;
						 break;
					 }
		          }
		        }
			}
			else
			{
				status=false;
			}
		s1=null;
		s2=null;
      logger.info("after procedure:status==>>"+status);
    
      }
      else
      {
     	 msg="NF";
      }   
      
  }
catch(Exception e)
{
	 logger.error(e.getMessage());
	 logger.error(e.getStackTrace());
	 request.setAttribute("exceptoinInUpload","Y");
}
finally 
{	 
  request.setAttribute("msg", msg);
  request.setAttribute("noOfRejectedRecord", noOfRejectedRecord);
  request.setAttribute("noOfUploadedRecord", noOfUploadedRecord);
  
  

  alFinalQry.clear();
  alFinalQry=null;
  alDeleteQuery.clear();
  alDeleteQuery=null;
  dataList.clear();
  dataList=null;
  arr.clear();
  arr=null;
  if (null != subList){
  subList.clear();
  subList=null;
  }
  objFile1=null;
   userId=null;
   bDate=null;  
 insertPrepStmtObject=null;
   query=null;
   strPath=null;
   workbook =null;
}
return status;
}



public boolean  whereToUploadNHB(HttpServletRequest request,FormFile file){
	boolean status=false;
	String fileName="";
	String filePath="";
	String message="";
	String rpt="";
	try{
		String query="SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='NHB_PARTPREPAY_UPLOAD_PATH'";
		logger.info("In docUpload(): query:==>>"+query);
		
		 rpt=ConnectionDAO.singleReturn(query);
		query=null;
		logger.info("The name you have entered is a file : "  +file);
		File directory = new File(rpt+"/");
		logger.info("vinod:docUpload:directory:==>"+directory);
		boolean isDirectory = directory.isDirectory();
		logger.info("vinod:docUpload:isDirectory:==>"+isDirectory);
        fileName    = file.getFileName();
    //Get the servers upload directory real path name
        filePath = directory.getAbsolutePath();
    /* Save file on the server */
        if(!fileName.equals("")){  
        	logger.info("Server path: filePath:==>>" +filePath);
        	//Create file
        	File fileToCreate = new File(filePath, fileName);
        		int fileSize = file.getFileSize(); //1048576 bytes = 1 MB
        		logger.info("docUpload :Size of file==>> "+fileSize);
        		if(fileSize<1048576)
        		{
	        			FileOutputStream fileOutStream = new FileOutputStream(fileToCreate);
        				fileOutStream.write(file.getFileData());
        				fileOutStream.flush();
        				fileOutStream.close();
        				message="O";
        				status=true;	        		
        		}
        		else
        		{
        			message="E";
        			//logger.info("File size exceeds the upper limit of 1 MB.");
		        	status=false;
        		}
        }
        else
        {
        	message="S";
        	//logger.info("Please select a File.");
        	status=false;
        }
        
	}catch(Exception e){
		e.printStackTrace();
	}
	finally{
		rpt=null;
	}
	request.setAttribute("fileName", fileName);
	request.setAttribute("filePath", filePath);
	request.setAttribute("message",message);
	fileName=null;
	filePath=null;
	message=null;
	return status;		
}
public int countLineForNHB(String fileName){
 	logger.info("In countLine() method ");
	 int rowTotalNum=0;
	 String query="SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='NHB_PARTPREPAY_UPLOAD_PATH'";				
	 String rpt=ConnectionDAO.singleReturn(query);		
	 File directory = new File(rpt+"/"+fileName);
	 logger.info("File Name......... ==>>"+directory);
	 query=null;
 try
	{ 
	 //logger.info("in method of file.....................++++++++++++++++"+directory.exists());
	 if(directory.exists())
      	{
		// logger.info("in method of file....................."+directory.length());
		 if(directory.length()>0){
	 Workbook workbook = Workbook.getWorkbook(directory);
	 
	// logger.info("in method of file.....................");
	 Sheet sheet;
	  sheet = workbook.getSheet(0);
	  rowTotalNum=sheet.getRows();
	  logger.info("Total Lines............. ==>>"+rowTotalNum);
		 }
		 else{
			 rowTotalNum=-1;
			// logger.info("singh singh +++++++++++............. ==>>"+rowTotalNum);
		 }
     
    	
      	}
      else
      {
    	  rowTotalNum=-2;
    	  logger.info("File not exitxt..");

      }
	}
	catch (Exception e) 
	{
		e.printStackTrace();
	}
	  return rowTotalNum;	
}

public boolean readExcelforNHBPartPrePayUpload(HttpServletRequest request,HttpServletResponse response,String strFileName){

    logger.info("In readExcelforNHBPartPrePayUpload() method ");
    HttpSession   session		  = null;
    String        makerID	 		= null;
    ArrayList     alFinalQry  = new ArrayList();
    ArrayList     alDeleteQuery  = new ArrayList(1);
    ArrayList     dataList  =null;
    ArrayList     arr  =null;
    ArrayList     subList  = null;    
    int noOfRejectedRecord =0;
	int noOfUploadedRecord=0;
    
    int           rowTotalNum = 0;
    int           row         = 0;
    int           col         = 0;
    String        msg         = "";    
    boolean singleObject;
    File          objFile1		= null;      

    boolean status=false;
    int intRem=0;
    int companyId=0;
    String userId="";
    String branchId="";
    String bDate="";
    
    String  loanNo=null;
    String  adviceDate=null;
    String  chargeCode=null;
    String  chargeAmount=null;
    String  makerRemarks=null;
    
    boolean insertStatus=false;
	  
    try {      
      PrepStmtObject insertPrepStmtObject=null;
      session     = request.getSession(false);
      UserObject userobj=(UserObject)session.getAttribute("userobject");	    

	     if(userobj!=null)
	     {
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
			companyId=userobj.getCompanyId();
			bDate=userobj.getBusinessdate();
		}

      if(!strFileName.equals(""))
      {

      String query="SELECT  PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='NHB_PARTPREPAY_UPLOAD_PATH'";
      logger.info("In readExcelforNHBPartPrePayUpload(): query:==>>"+query);
      
	  String strPath=ConnectionDAO.singleReturn(query);
      strPath     = strPath + "/" + strFileName;
      objFile1    = new File(strPath);
      query=null;

      try {
 
    	  
    	  String strCountDeleteQuery =ConnectionDAO.singleReturn("SELECT COUNT(1) FROM cr_NHB_SUBSIDY_TMP WHERE MAKER_ID = '"+userId+"' ");
          if(!strCountDeleteQuery.equalsIgnoreCase("0")){     	  
			    	  String strDeleteQuery = "DELETE FROM cr_NHB_SUBSIDY_TMP WHERE MAKER_ID = '"+userId+"' ";
			           logger.info(" Delete query strDeleteQuery"+strDeleteQuery);
			           alDeleteQuery.add(strDeleteQuery);
			           boolean status1=ConnectionDAO.sqlInsUpdDelete(alDeleteQuery);
			           strDeleteQuery=null;
			           logger.info(" Deleting query is..... ==>>"+status1);
          
          	}
          
           dataList=new ArrayList();
          
           Workbook workbook = Workbook.getWorkbook(new File(strPath));
           String sheetName[]=workbook.getSheetNames();
           Sheet sheet;
           Cell xlsCell;
           Cell[] cell;
       
          
       

           for(int p=0; p<sheetName.length; p++)
           {
               sheet = workbook.getSheet(p);
               for(int i=0; i<sheet.getRows(); i++)
               {
             	  cell = sheet.getRow(0);
             	  arr = new ArrayList();
                  // logger.info("readExcelforAdviceUpload :cell.length"+cell.length);
                   for(int j=0; j<cell.length; j++)
                   {                 	
                       xlsCell = sheet.getCell(j,i);
                      // logger.info("xlsCell.getContents().toString() :"+xlsCell.getContents().toString());
                       arr.add(xlsCell.getContents().toString());
                    } 
                   dataList.add(arr);	 
               }
           }
       
      }
        catch(Exception e) 
        {
        	e.printStackTrace();
        }

        
        for(int i=1;i<dataList.size();i++)
        {		
     	  
     	   subList=(ArrayList)dataList.get(i);
    			if(subList.size()>0){ 
    				  loanNo=CommonFunction.checkNull(subList.get(0));
    				  adviceDate=CommonFunction.checkNull(subList.get(1));
    				  chargeCode=CommonFunction.checkNull(subList.get(2));
    				  chargeAmount=CommonFunction.checkNull(subList.get(3));
    				  makerRemarks=CommonFunction.checkNull(subList.get(4));
    		         
    				 
    				 StringBuffer bufInsSql = new StringBuffer();
    				 insertPrepStmtObject = new PrepStmtObject();
    				 
    				 	bufInsSql.append("INSERT INTO cr_NHB_SUBSIDY_TMP(SR_NO,LOAN_NO,ADVICE_DATE,CHARGE_CODE,CHARGE_AMOUNT,");
    				 	bufInsSql.append(" MAKER_REMARKS,MAKER_ID,MAKER_DATE)");
    					bufInsSql.append("  VALUES ( ");
    					bufInsSql.append(" ?," );//SR_NO
    					bufInsSql.append(" ?," );//loan_no    		
    					bufInsSql.append(" ?," ); //ADVICE_DATE
    					bufInsSql.append(" ?," ); //CHARGE_CODE    		
    					bufInsSql.append(" ?," );//CHARGE_AMOUNT		
    					bufInsSql.append(" ?," ); //MAKER_REMARKS    				 
    					bufInsSql.append(" ?," ); //MAKER_ID
    					bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') " ); //MAKER_DATE  
    					bufInsSql.append(" )" ); 
    				 
    					
	    					insertPrepStmtObject.addInt(i);
	    					
	    					if(CommonFunction.checkNull(loanNo).equalsIgnoreCase(""))
	    						insertPrepStmtObject.addNull();
	    					else
	    						insertPrepStmtObject.addString(loanNo);	
    						
    						
    						if(CommonFunction.checkNull(adviceDate).equalsIgnoreCase(""))
    							insertPrepStmtObject.addNull();
    						else
    							insertPrepStmtObject.addString(adviceDate);
    						
    						if(CommonFunction.checkNull(chargeCode).equalsIgnoreCase(""))
    							insertPrepStmtObject.addNull();
    						else
    							insertPrepStmtObject.addString(chargeCode);
    						
    						
    						if(CommonFunction.checkNull(chargeAmount).equalsIgnoreCase(""))
    							insertPrepStmtObject.addNull();
    						else
    							insertPrepStmtObject.addString(chargeAmount);
    						
			
    						
    						if(CommonFunction.checkNull(makerRemarks).equalsIgnoreCase(""))
    							insertPrepStmtObject.addNull();
    						else
    							insertPrepStmtObject.addString(makerRemarks);			
    						
    						if(CommonFunction.checkNull(userId).equalsIgnoreCase(""))
    							insertPrepStmtObject.addNull();
    						else
    							insertPrepStmtObject.addString(userId);	
    			
    						if(CommonFunction.checkNull(bDate).equalsIgnoreCase(""))
    							insertPrepStmtObject.addNull();
    						else
    							insertPrepStmtObject.addString(bDate);
   
    					
    						
    						insertPrepStmtObject.setSql(bufInsSql.toString());
    						logger.info("IN readExcelforNHBPartPrePayUpload() insert query1 ###:::::::::::: "+ insertPrepStmtObject.printQuery());
    						alFinalQry.add(insertPrepStmtObject);
    						
    						insertPrepStmtObject=null;
    						bufInsSql=null;    						
    			}   
    			  loanNo=null;
				  adviceDate=null;
				  chargeCode=null;			
				  chargeAmount=null;
				  makerRemarks=null;
        }
        
        
         
        insertStatus=ConnectionDAO.sqlInsUpdDeletePrepStmt(alFinalQry); 
        logger.info("status::::::::::::::::::::::"+status);    
        logger.info(" Before Procedure Call NHB_SUBSIDY ");     
       
        ArrayList<Object> in =new ArrayList<Object>();
 		ArrayList<Object> out =new ArrayList<Object>();
 		ArrayList outMessages = new ArrayList();
 		String s1 = "";
 		String s2 = "";
 		
 		in.add(userId);
 		String makerDate="";
 		makerDate=CommonFunction.changeFormat(CommonFunction.checkNull(bDate).trim());
 		in.add(makerDate);
 		in.add(strFileName);
 		out.add(noOfRejectedRecord);
		out.add(noOfUploadedRecord);
		out.add(s1);
	    out.add(s2);
 		
	    logger.info(" NHB_SUBSIDY("+in.toString()+","+out.toString()+")");  
        outMessages=(ArrayList) ConnectionDAO.callSP("NHB_SUBSIDY",in,out); 
        
        in.clear();
        in=null;
        out.clear();
        out=null;
        
        noOfRejectedRecord =Integer.parseInt((String)outMessages.get(0));
 	    noOfUploadedRecord=Integer.parseInt((String)outMessages.get(1));
        s1 = (String)outMessages.get(2);
 	    s2 = (String)outMessages.get(3);
 	    
 	   outMessages.clear();
 	   outMessages=null;
 	   
 	   logger.info("NHB_SUBSIDY OUT s1: "+s1);
 	   logger.info("NHB_SUBSIDY OUT s2: "+s2);
 	   logger.info("NHB_SUBSIDY OUT noOfRejectedRecord: "+noOfRejectedRecord);
 	   logger.info("NHB_SUBSIDY OUT noOfUploadedRecord: "+noOfUploadedRecord);
	 
		if((s1==null || s1.equals("S")) && insertStatus)
		{
			status=true;
			for(int i=1;i<dataList.size();i++)
	        {		
	     	   subList=(ArrayList)dataList.get(i);
	  			logger.info("In insert into readExcelfornhbUpload... size: 2222==>>"+subList.size());
	    		if(subList.size()>0){ 
					logger.info("IN csv() Loan No ### "+ subList.get(0));
					 String checkErrorQ="select UPLOADED_FLAG from cr_NHB_SUBSIDY_TMP where LOAN_NO='"+ subList.get(0)+"'  and maker_id='"+userId+"' ";			
					 String checkErrorStatus=ConnectionDAO.singleReturn(checkErrorQ);
					 logger.info("checkErrorStatus: "+checkErrorStatus);
					 checkErrorQ=null;
					 if(checkErrorStatus.equalsIgnoreCase("N"))
					 {
						 status=false;
						 break;
					 }
		          }
		        }
			}
			else
			{
				status=false;
			}
		s1=null;
		s2=null;
      logger.info("after procedure:status==>>"+status);
    
      }
      else
      {
     	 msg="NF";
      }   
      
  }
catch(Exception e)
{
	 logger.error(e.getMessage());
}
finally 
{	 

  request.setAttribute("msg", msg);
  request.setAttribute("noOfRejectedRecord", noOfRejectedRecord);
  request.setAttribute("noOfUploadedRecord", noOfUploadedRecord);
  makerID=null;
  alFinalQry.clear();
  alFinalQry=null;
  alDeleteQuery.clear();
  alDeleteQuery=null;
  dataList.clear();
  dataList=null;
  arr.clear();
  arr=null;
  msg=null;
  userId=null;
  branchId=null;
  bDate=null;
  subList.clear();
  subList=null;  
}
return status;
}

}
