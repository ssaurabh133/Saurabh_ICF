package com.cm.daoImplMSSQL;

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

public class MSSQLLoanDetailUploadDAOImpl  implements LoanDetailUploadDAO {
	private static final Logger logger=Logger.getLogger(MSSQLLoanDetailUploadDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	String dateFormanualUpload=resource.getString("lbl.dateFormanualUpload");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
    String dbType=resource.getString("lbl.dbType");
	String dbo=resource.getString("lbl.dbPrefix");
	
	
	public String getUploadManualData(String userId){
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
public boolean readExcelforManualUpload(HttpServletRequest request,HttpServletResponse response,String strFileName){

    logger.info("In readExcelforManualUpload() method ");
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
    String  bpType=null;
    String  bpId=null;
    String  adviceDate=null;
    String  chargeCode=null;
    String  adviceType=null;
    String  chargeAmount=null;
    String  taxAmount1=null;
    String  taxAmount2=null;
    String  tdsAmount=null;
    String  adviceAmount=null;
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

      String query="SELECT  PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='MANUAL_ADVICE_UPLOAD_PATH'";
      logger.info("In readExcelforManualUpload(): query:==>>"+query);
      
	  String strPath=ConnectionDAO.singleReturn(query);
      strPath     = strPath + "/" + strFileName;
      objFile1    = new File(strPath);
      query=null;

      try {
 
    	  
    	  String strCountDeleteQuery =ConnectionDAO.singleReturn("SELECT COUNT(1) FROM cr_manual_advice_TMP WHERE MAKER_ID = '"+userId+"' ");
          if(!strCountDeleteQuery.equalsIgnoreCase("0")){     	  
			    	  String strDeleteQuery = "DELETE FROM cr_manual_advice_TMP WHERE MAKER_ID = '"+userId+"' ";
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
    				  bpType=CommonFunction.checkNull(subList.get(1));
    				  bpId=CommonFunction.checkNull(subList.get(2));
    				  adviceDate=CommonFunction.checkNull(subList.get(3));
    				  chargeCode=CommonFunction.checkNull(subList.get(4));
    				  adviceType=CommonFunction.checkNull(subList.get(5));
    				  chargeAmount=CommonFunction.checkNull(subList.get(6));
    				  taxAmount1=CommonFunction.checkNull(subList.get(7));
    				  taxAmount2=CommonFunction.checkNull(subList.get(8));
    				  tdsAmount=CommonFunction.checkNull(subList.get(9));
    				  adviceAmount=CommonFunction.checkNull(subList.get(10));
    				  makerRemarks=CommonFunction.checkNull(subList.get(11));
    		         
    				 
    				 StringBuffer bufInsSql = new StringBuffer();
    				 insertPrepStmtObject = new PrepStmtObject();
    				 
    				 	bufInsSql.append("INSERT INTO cr_manual_advice_TMP(SR_NO,LOAN_NO,BPTYPE,BPID,ADVICE_DATE,CHARGE_CODE,ADVICE_TYPE,CHARGE_AMOUNT,");
    				 	bufInsSql.append(" TAX_AMOUNT1,TAX_AMOUNT2,TDS_AMOUNT,ADVICE_AMOUNT,MAKER_REMARKS,MAKER_ID,MAKER_DATE)");
    					bufInsSql.append("  VALUES ( ");
    					bufInsSql.append(" ?," );//SR_NO
    					bufInsSql.append(" ?," );//loan_no
    					bufInsSql.append(" ?," ); //BPTYPE
    					bufInsSql.append(" ?," ); //BPID    		
    					bufInsSql.append("STR_TO_DATE(?,'"+dateFormanualUpload+"'), " ); // advice date
    					bufInsSql.append(" ?," ); //CHARGE_CODE
    					bufInsSql.append(" ?," ); //ADVICE_TYPE
    					bufInsSql.append(" ?," );//CHARGE_AMOUNT
    					bufInsSql.append(" ?," ); //TAX_AMOUNT1
    					bufInsSql.append(" ?," ); //TAX_AMOUNT2
    					bufInsSql.append(" ?," ); //TDS_AMOUNT
    					bufInsSql.append(" ?," ); //ADVICE_AMOUNT
    					bufInsSql.append(" ?," ); //MAKER_REMARKS    				 
    					bufInsSql.append(" ?," ); //MAKER_ID
    					bufInsSql.append(dbo); //MAKER_ID
    					bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') " ); //MAKER_DATE  
    					bufInsSql.append(" )" ); 
    				 
    					
    					insertPrepStmtObject.addInt(i);
    					
    					if(CommonFunction.checkNull(loanNo).equalsIgnoreCase(""))
    						insertPrepStmtObject.addNull();
    					else
    						insertPrepStmtObject.addString(loanNo);	
    		         
    						if(CommonFunction.checkNull(bpType).equalsIgnoreCase(""))
    							insertPrepStmtObject.addNull();
    						else
    							insertPrepStmtObject.addString(bpType);
    						
    						if(CommonFunction.checkNull(bpId).equalsIgnoreCase(""))
    							insertPrepStmtObject.addNull();
    						else
    							insertPrepStmtObject.addString(bpId);
    						
    						
    						if(CommonFunction.checkNull(adviceDate).equalsIgnoreCase(""))
    							insertPrepStmtObject.addNull();
    						else
    							insertPrepStmtObject.addString(adviceDate);
    						
    						if(CommonFunction.checkNull(chargeCode).equalsIgnoreCase(""))
    							insertPrepStmtObject.addNull();
    						else
    							insertPrepStmtObject.addString(chargeCode);
    						
    						
    						if(CommonFunction.checkNull(adviceType).equalsIgnoreCase(""))
    							insertPrepStmtObject.addNull();
    						else
    							insertPrepStmtObject.addString(adviceType);
    						
    						if(CommonFunction.checkNull(chargeAmount).equalsIgnoreCase(""))
    							insertPrepStmtObject.addNull();
    						else
    							insertPrepStmtObject.addString(chargeAmount);
    						
    						if(CommonFunction.checkNull(taxAmount1).equalsIgnoreCase(""))
    							insertPrepStmtObject.addNull();
    						else
    							insertPrepStmtObject.addString(taxAmount1);
    						
    						if(CommonFunction.checkNull(taxAmount2).equalsIgnoreCase(""))
    							insertPrepStmtObject.addNull();
    						else
    							insertPrepStmtObject.addString(taxAmount2);
    						
    						if(CommonFunction.checkNull(tdsAmount).equalsIgnoreCase(""))
    							insertPrepStmtObject.addNull();
    						else
    							insertPrepStmtObject.addString(tdsAmount);
    						
    						if(CommonFunction.checkNull(adviceAmount).equalsIgnoreCase(""))
    							insertPrepStmtObject.addNull();
    						else
    							insertPrepStmtObject.addString(adviceAmount);				
    						
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
    						logger.info("IN readExcelforBounceUpload() insert query1 ###:::::::::::: "+ insertPrepStmtObject.printQuery());
    						alFinalQry.add(insertPrepStmtObject);
    						
    						insertPrepStmtObject=null;
    						bufInsSql=null;    						
    			}   
    			  loanNo=null;
				  bpType=null;
				  bpId=null;
				  adviceDate=null;
				  chargeCode=null;
				  adviceType=null;
				  chargeAmount=null;
				  taxAmount1=null;
				  taxAmount2=null;
				  tdsAmount=null;
				  adviceAmount=null;
				  makerRemarks=null;
        }
        
        
         
        insertStatus=ConnectionDAO.sqlInsUpdDeletePrepStmt(alFinalQry); 
        logger.info("status::::::::::::::::::::::"+status);    
        logger.info(" Before Procedure Call ");     
       
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
 		
	    logger.info(" Manual_Advice_Upload("+in.toString()+","+out.toString()+")");  
        outMessages=(ArrayList) ConnectionDAO.callSP("Manual_Advice_Upload",in,out); 
        
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
 	   
 	   logger.info("Manual_Advice_Upload OUT s1: "+s1);
 	   logger.info("Manual_Advice_Upload OUT s2: "+s2);
 	   logger.info("Manual_Advice_Upload OUT noOfRejectedRecord: "+noOfRejectedRecord);
 	   logger.info("Manual_Advice_Upload OUT noOfUploadedRecord: "+noOfUploadedRecord);
	 
		if((s1==null || s1.equals("S")) && insertStatus)
		{
			status=true;
			for(int i=1;i<dataList.size();i++)
	        {		
	     	   subList=(ArrayList)dataList.get(i);
	  			logger.info("In insert into readExcelforBounceUpload... size: 2222==>>"+subList.size());
	    		if(subList.size()>0){ 
					logger.info("IN csv() Loan No ### "+ subList.get(0));
					 String checkErrorQ="select UPLOADED_FLAG from cr_manual_advice_TMP where LOAN_NO='"+ subList.get(0)+"'  and maker_id='"+userId+"' ";			
					 String checkErrorStatus=ConnectionDAO.singleReturn(checkErrorQ);
					 logger.info("checkErrorStatus: "+checkErrorStatus);
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
    					bufInsSql.append(dbo); //MAKER_ID
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
@Override
public String getUploadLoanDetailData(String userId) {
	// TODO Auto-generated method stub
	return null;
}
@Override
public boolean readExcelforLoanDetailUpload(HttpServletRequest request,
		HttpServletResponse response, String file) {
	// TODO Auto-generated method stub
	return false;
}
}
