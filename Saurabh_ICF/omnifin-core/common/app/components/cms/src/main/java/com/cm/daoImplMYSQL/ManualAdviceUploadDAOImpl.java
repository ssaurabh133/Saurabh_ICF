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

import com.cm.dao.ManualAdviceUploadDAO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;
import com.login.roleManager.UserObject;

public class ManualAdviceUploadDAOImpl  implements ManualAdviceUploadDAO {
	private static final Logger logger=Logger.getLogger(ManualAdviceUploadDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dateFormanualUpload=resource.getString("lbl.dateFormanualUpload");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	
	
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
public boolean readExcelforManualUpload(HttpServletRequest request,HttpServletResponse response,String strFileName)
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
    		  String strCountDeleteQuery =ConnectionDAO.singleReturn("SELECT COUNT(1) FROM cr_manual_advice_TMP WHERE MAKER_ID = '"+userId+"' ");
    		  if(!strCountDeleteQuery.equalsIgnoreCase("0"))
    		  {     	  
			   	  String strDeleteQuery = "DELETE FROM cr_manual_advice_TMP WHERE MAKER_ID = '"+userId+"' ";
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
        StringBuffer bufInsSql = new StringBuffer();
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
		bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') " ); //MAKER_DATE  
		bufInsSql.append(" )" );         
        for(int i=1;i<dataList.size();i++)
        {	     	  
     	   subList=(ArrayList)dataList.get(i);
    	   if(subList.size()>0)
    	   { 
    		   //loanNo=CommonFunction.checkNull(subList.get(0));
			   //bpType=CommonFunction.checkNull(subList.get(1));
//			   bpId=CommonFunction.checkNull(subList.get(2));
//			   adviceDate=CommonFunction.checkNull(subList.get(3));
//			   chargeCode=CommonFunction.checkNull(subList.get(4));
//			   adviceType=CommonFunction.checkNull(subList.get(5));
//			   chargeAmount=CommonFunction.checkNull(subList.get(6));
//			   taxAmount1=CommonFunction.checkNull(subList.get(7));
//			   taxAmount2=CommonFunction.checkNull(subList.get(8));
//			   tdsAmount=CommonFunction.checkNull(subList.get(9));
//			   adviceAmount=CommonFunction.checkNull(subList.get(10));
//			   makerRemarks=CommonFunction.checkNull(subList.get(11));
    		          				 
    		   insertPrepStmtObject = new PrepStmtObject();
    				    
    		   insertPrepStmtObject.addInt(i);
    					
    		   if(CommonFunction.checkNull(CommonFunction.checkNull(subList.get(0))).equalsIgnoreCase(""))
    					insertPrepStmtObject.addNull();
    			else
    					insertPrepStmtObject.addString(CommonFunction.checkNull(subList.get(0)));	
    		       
    			if(CommonFunction.checkNull(CommonFunction.checkNull(subList.get(1))).equalsIgnoreCase(""))
    					insertPrepStmtObject.addNull();
    			else
    					insertPrepStmtObject.addString(CommonFunction.checkNull(subList.get(1)));
    					
    			if(CommonFunction.checkNull(CommonFunction.checkNull(subList.get(2))).equalsIgnoreCase(""))
    					insertPrepStmtObject.addNull();
    			else
    					insertPrepStmtObject.addString(CommonFunction.checkNull(subList.get(2)));
    						
    						
    			if(CommonFunction.checkNull(CommonFunction.checkNull(subList.get(3))).equalsIgnoreCase(""))
    					insertPrepStmtObject.addNull();
    			else
    					insertPrepStmtObject.addString(CommonFunction.checkNull(subList.get(3)));
    					
    			if(CommonFunction.checkNull(CommonFunction.checkNull(subList.get(4))).equalsIgnoreCase(""))
    					insertPrepStmtObject.addNull();
    			else
    					insertPrepStmtObject.addString(CommonFunction.checkNull(subList.get(4)));
    					
    			if(CommonFunction.checkNull(CommonFunction.checkNull(subList.get(5))).equalsIgnoreCase(""))
    					insertPrepStmtObject.addNull();
    			else
    					insertPrepStmtObject.addString(CommonFunction.checkNull(subList.get(5)));
    					
    			if(CommonFunction.checkNull(CommonFunction.checkNull(subList.get(6))).equalsIgnoreCase(""))
    					insertPrepStmtObject.addNull();
    			else
    					insertPrepStmtObject.addString(CommonFunction.checkNull(subList.get(6)));
    					
    			if(CommonFunction.checkNull(CommonFunction.checkNull(subList.get(7))).equalsIgnoreCase(""))
    					insertPrepStmtObject.addNull();
    			else
    					insertPrepStmtObject.addString(CommonFunction.checkNull(subList.get(7)));
    					
    			if(CommonFunction.checkNull(CommonFunction.checkNull(subList.get(8))).equalsIgnoreCase(""))
    					insertPrepStmtObject.addNull();
    			else
    					insertPrepStmtObject.addString(CommonFunction.checkNull(subList.get(8)));
    					
    			if(CommonFunction.checkNull(CommonFunction.checkNull(subList.get(9))).equalsIgnoreCase(""))
    					insertPrepStmtObject.addNull();
    			else
    					insertPrepStmtObject.addString(CommonFunction.checkNull(subList.get(9)));
    					
    			if(CommonFunction.checkNull(CommonFunction.checkNull(subList.get(10))).equalsIgnoreCase(""))
    					insertPrepStmtObject.addNull();
    			else
    					insertPrepStmtObject.addString(CommonFunction.checkNull(subList.get(10)));				
    						
    			if(CommonFunction.checkNull(CommonFunction.checkNull(subList.get(11))).equalsIgnoreCase(""))
    					insertPrepStmtObject.addNull();
    			else
    					insertPrepStmtObject.addString(CommonFunction.checkNull(subList.get(11)));			
    					
    			if(CommonFunction.checkNull(userId).equalsIgnoreCase(""))
    					insertPrepStmtObject.addNull();
    			else
    					insertPrepStmtObject.addString(userId);	
    			
    			if(CommonFunction.checkNull(bDate).equalsIgnoreCase(""))
    					insertPrepStmtObject.addNull();
    			else
    					insertPrepStmtObject.addString(bDate);
       					
   				insertPrepStmtObject.setSql(bufInsSql.toString());
   				logger.info("IN readExcelforBounceUpload() insert query1 : "+ insertPrepStmtObject.printQuery());
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
	  			if(subList.size()>0){ 
					 String checkErrorQ="select UPLOADED_FLAG from cr_manual_advice_TMP where LOAN_NO='"+ subList.get(0)+"'  and maker_id='"+userId+"' ";			
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
  subList.clear();
  subList=null;
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
@Override
public boolean readExcelforGenerateBacthUpload(HttpServletRequest request,
		HttpServletResponse response, String strFileName,String presentaionDate) {
    logger.info("In readExcelforGenerateBacthUpload() method ");
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
    	  logger.info("In readExcelforGenerateBacthUpload(): query:==>>"+query);
      
    	  strPath=ConnectionDAO.singleReturn(query);
    	  strPath=strPath + "/" + strFileName;
    	  objFile1=new File(strPath);
    	  query=null;    	  
    	  try 
    	  {    	  
    		  String strCountDeleteQuery =ConnectionDAO.singleReturn("SELECT COUNT(1) FROM TEMP_GENERATE_BATCH WHERE MAKER_ID = '"+userId+"' ");
    		  if(!strCountDeleteQuery.equalsIgnoreCase("0"))
    		  {     	  
			   	  String strDeleteQuery = "DELETE FROM TEMP_GENERATE_BATCH ";
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
        StringBuffer bufInsSql = new StringBuffer();
	 	bufInsSql.append("INSERT INTO TEMP_GENERATE_BATCH(SR_NO,LOAN_NO,BANK_ID,STATUS,PRESENTATION_DATE,MAKER_ID,MAKER_DATE)");
		bufInsSql.append("  VALUES ( ");
		bufInsSql.append(" ?," );//SR_NO
		bufInsSql.append(" ?," );//loan_no
		bufInsSql.append(" ?," );//BANK_ID
		bufInsSql.append(" ?," ); //STATUS 
		bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"'), " ); //PRESENTATION_DATE  
		bufInsSql.append(" ?," ); //MAKER_ID
		bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') " ); //MAKER_DATE  
		bufInsSql.append(" )" );         
        for(int i=1;i<dataList.size();i++)
        {	     	  
     	   subList=(ArrayList)dataList.get(i);
    	   if(subList.size()>0)
    	   { 
    		          				 
    		   insertPrepStmtObject = new PrepStmtObject();
    				    
    		   insertPrepStmtObject.addInt(i);
    					
    		   if(CommonFunction.checkNull(CommonFunction.checkNull(subList.get(0))).equalsIgnoreCase(""))
    					insertPrepStmtObject.addNull();
    			else
    					insertPrepStmtObject.addString(CommonFunction.checkNull(subList.get(0)));	
    		       
    			if(CommonFunction.checkNull(CommonFunction.checkNull(subList.get(1))).equalsIgnoreCase(""))
    					insertPrepStmtObject.addNull();
    			else
    					insertPrepStmtObject.addString(CommonFunction.checkNull(subList.get(1)));
    					
    			if(CommonFunction.checkNull(CommonFunction.checkNull(subList.get(2))).equalsIgnoreCase(""))
    					insertPrepStmtObject.addNull();
    			else
    					insertPrepStmtObject.addString(CommonFunction.checkNull(subList.get(2)));
    				
    			if(CommonFunction.checkNull(presentaionDate).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
    			else
					insertPrepStmtObject.addString(presentaionDate);	
    			
    			if(CommonFunction.checkNull(userId).equalsIgnoreCase(""))
    					insertPrepStmtObject.addNull();
    			else
    					insertPrepStmtObject.addString(userId);	
    			
    			if(CommonFunction.checkNull(bDate).equalsIgnoreCase(""))
    					insertPrepStmtObject.addNull();
    			else
    					insertPrepStmtObject.addString(bDate);
       					
   				insertPrepStmtObject.setSql(bufInsSql.toString());
   				logger.info("IN readExcelforGenerateBacthUpload() insert query1 : "+ insertPrepStmtObject.printQuery());
   				alFinalQry.add(insertPrepStmtObject);    						
   				    							
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
 		out.add(noOfRejectedRecord);
		out.add(noOfUploadedRecord);
		out.add(s1);
	    out.add(s2); 		
	    logger.info(" GENERATE_BATCH_MULTIPLE_BATCH_UPLOAD("+in.toString()+","+out.toString()+")");  
        outMessages=(ArrayList) ConnectionDAO.callSP("GENERATE_BATCH_MULTIPLE_BATCH_UPLOAD",in,out); 
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
 	   
 	   logger.info("GENERATE_BATCH_MULTIPLE_BATCH_UPLOAD OUT s1: "+s1);
 	   logger.info("GENERATE_BATCH_MULTIPLE_BATCH_UPLOAD OUT s2: "+s2);
 	   logger.info("GENERATE_BATCH_MULTIPLE_BATCH_UPLOAD OUT noOfRejectedRecord: "+noOfRejectedRecord);
 	   logger.info("GENERATE_BATCH_MULTIPLE_BATCH_UPLOAD OUT noOfUploadedRecord: "+noOfUploadedRecord);
	 
		if((s1==null || s1.equals("S")) && insertStatus)
		{
			/*if(CommonFunction.checkNull(noOfRejectedRecord).equalsIgnoreCase("0") && !CommonFunction.checkNull(noOfUploadedRecord).equalsIgnoreCase("0")){
				
				
				ArrayList list=new ArrayList();
				String batchQuery="select distinct a.pdc_instrument_mode,a.pdc_clearing_type,b.bank_id from cr_pdc_instrument_dtl a join cr_loan_dtl l on l.loan_id=a.PDC_LOAN_ID "
						+ "join TEMP_GENERATE_BATCH b on l.loan_no=b.loan_no "
						+ "where a.PDC_INSTRUMENT_DATE=STR_TO_DATE('"+presentaionDate+"','"+dateFormat+"') and a.PRESENTATION_DATE is null AND  l.REC_STATUS = 'A' AND l.DISBURSAL_STATUS in ('F','P') "
						+ "and a.PDC_STATUS = 'A' AND a.PDC_PURPOSE IN ('PRE EMI','INSTALLMENT') ";
				list=ConnectionDAO.sqlSelect(batchQuery);
				for(int i=0;i<list.size();i++)
	    	    {	    	    
					ArrayList subList1=(ArrayList)list.get(i);
	    	    	if(subList1.size()>0)
	    			{
            		String instrumentMode=CommonFunction.checkNull(subList1.get(0)).trim();
            		String clearingType=CommonFunction.checkNull(subList1.get(1)).trim();
            		String bankId=CommonFunction.checkNull(subList1.get(2)).trim();
            		boolean st=generateBatch(presentaionDate,instrumentMode,clearingType,bankId,userId,bDate);
            	}
	    	    }
			}*/
			status=true;
			for(int i=1;i<dataList.size();i++)
	        {		
	     	   subList=(ArrayList)dataList.get(i);
	  			if(subList.size()>0){ 
					 String checkErrorQ="select UPLOADED_FLAG from TEMP_GENERATE_BATCH where LOAN_NO='"+ subList.get(0)+"'  and maker_id='"+userId+"' ";			
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
  subList.clear();
  subList=null;
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
private boolean generateBatch(String presentaionDate,String instrumentMode,String clearingType,String bankId,String userId,String makerDate) {
	
	   
	logger.info("in generateBatch() of GenerateBatchDAOImpl.");
	boolean retVal = false;
	ArrayList list=new ArrayList();
	StringBuilder bufInsSql =	new StringBuilder();
	try
	{
		String bankNameQuery="SELECT BANK_NAME FROM COM_BANK_M WHERE BANK_ID='"+bankId+"' ";
		String bankName=ConnectionDAO.singleReturn(bankNameQuery);
		bufInsSql.append("( SELECT  distinct a.PDC_LOAN_ID,l.LOAN_NO,a.PDC_INSTRUMENT_ID,a.PDC_INSTRUMENT_AMOUNT,a.pdc_instrument_mode,a.pdc_clearing_type " +
				" FROM CR_PDC_INSTRUMENT_DTL a " +
				" JOIN CR_LOAN_DTL l ON A.PDC_LOAN_ID = l.LOAN_ID AND l.REC_STATUS = 'A' AND l.DISBURSAL_STATUS = 'F' "+
				" JOIN TEMP_GENERATE_BATCH temp on l.loan_no=temp.loan_no AND TEMP.BANK_ID='"+bankId+"'  " +
				" JOIN CR_REPAYSCH_DTL re ON re.LOAN_ID = A.PDC_LOAN_ID AND re.INSTL_NO = A.PDC_INSTL_NO AND re.REC_STATUS = 'A' AND re.INSTL_DATE = STR_TO_DATE('"+presentaionDate.trim()+"','"+dateFormat+"') " +
				" JOIN cr_deal_dtl d ON d.DEAL_ID=l.LOAN_DEAL_ID " +
				" LEFT JOIN CR_GENERATE_BATCH_DTL GB ON(GB.LOAN_ID=A.PDC_LOAN_ID AND GB.INSTRUMENT_ID=PDC_INSTRUMENT_ID)"+
				" WHERE PDC_STATUS = 'A' AND A.PDC_PURPOSE IN ('INSTALLMENT') " +						
				" AND A.PRESENTATION_DATE IS NULL AND IFNULL(GB.REC_STATUS,'N') NOT IN('P','F')" +
				" AND PDC_INSTRUMENT_DATE = STR_TO_DATE('"+presentaionDate.trim()+"','"+dateFormat+"') ");
		
		if(!instrumentMode.trim().equalsIgnoreCase(""))
			bufInsSql.append(" AND a.pdc_instrument_mode='"+instrumentMode.trim()+"'");
		if(!clearingType.trim().equalsIgnoreCase(""))
			bufInsSql.append(" AND a.pdc_clearing_type='"+clearingType.trim()+"'");
		
		
		bufInsSql.append(")    UNION    (");
		bufInsSql.append(" SELECT  distinct a.PDC_LOAN_ID,l.LOAN_NO,a.PDC_INSTRUMENT_ID,a.PDC_INSTRUMENT_AMOUNT,a.pdc_instrument_mode,a.pdc_clearing_type " +
				" FROM CR_PDC_INSTRUMENT_DTL a " +
				" JOIN CR_LOAN_DTL l ON A.PDC_LOAN_ID = l.LOAN_ID AND l.REC_STATUS = 'A' AND l.DISBURSAL_STATUS IN('P', 'F') " +
				" JOIN TEMP_GENERATE_BATCH temp on l.loan_no=temp.loan_no  AND TEMP.BANK_ID='"+bankId+"' " +
				" JOIN cr_deal_dtl d ON d.DEAL_ID=l.LOAN_DEAL_ID " +
				" LEFT JOIN CR_GENERATE_BATCH_DTL GB ON(GB.LOAN_ID=A.PDC_LOAN_ID AND GB.INSTRUMENT_ID=PDC_INSTRUMENT_ID)"+
				" WHERE PDC_STATUS = 'A' AND A.PDC_PURPOSE IN ('PRE EMI') " +
				" AND A.PRESENTATION_DATE IS NULL AND IFNULL(GB.REC_STATUS,'N') NOT IN('P','F')" +
				" AND PDC_INSTRUMENT_DATE = STR_TO_DATE('"+presentaionDate.trim()+"','"+dateFormat+"') ");
		
		if(!instrumentMode.trim().equalsIgnoreCase(""))
			bufInsSql.append(" AND a.pdc_instrument_mode='"+instrumentMode.trim()+"'");
		if(!clearingType.trim().equalsIgnoreCase(""))
			bufInsSql.append(" AND a.pdc_clearing_type='"+clearingType.trim()+"'");
		
		
		bufInsSql.append(" )");
		logger.info("in generateBatch() Query  :  "+bufInsSql.toString());
		list = ConnectionDAO.sqlSelect(bufInsSql.toString());
		int size = list.size();
        logger.info("size: "+size);
        if(size==0)
        	retVal=false;
        if(size>0)
        {
        	String batch_no="";
        	
        	String mode=instrumentMode;	            	
        	if(mode.trim().equalsIgnoreCase("Q"))
        		batch_no="CHQ";
        	else if(mode.trim().equalsIgnoreCase("DIR"))
        		batch_no="DIR";
        	else if(mode.trim().equalsIgnoreCase("E"))
        		batch_no="ECS";
        	else if(mode.trim().equalsIgnoreCase("H"))
        		batch_no="ACH";
        
        	if(clearingType.trim().equalsIgnoreCase("I"))
        		batch_no=batch_no+"INTER";
        	else if(clearingType.trim().equalsIgnoreCase("L"))
        		batch_no=batch_no+"LOCAL";
        	else if(clearingType.trim().equalsIgnoreCase("O"))
        		batch_no=batch_no+"OUTSN";
        	else if(clearingType.trim().equalsIgnoreCase("C"))
        		batch_no=batch_no+"CLEAN";
        	else if(clearingType.trim().equalsIgnoreCase("E"))
        		batch_no=batch_no+"EXPRE";
        	else if(clearingType.trim().equalsIgnoreCase("N"))
        		batch_no=batch_no+"NEWCS";
        	else if(clearingType.trim().equalsIgnoreCase("M"))
        		batch_no=batch_no+"NMMCS";
        	else if(clearingType.trim().equalsIgnoreCase("T"))
        		batch_no=batch_no+"TRENS";
        	else if(clearingType.trim().equalsIgnoreCase("S"))
        		batch_no=batch_no+"CTSCS";
        	else if(clearingType.trim().equalsIgnoreCase("R"))
        		batch_no=batch_no+"BLOCA";
        	
        	
        	String date=presentaionDate;
        	date=date.replace("-","_");
        	batch_no=batch_no+date;
        	batch_no=batch_no+"_S_"+bankId;
        	logger.info("batch_no   :  "+batch_no);
        	String query="select BATCH_NO from cr_generate_batch_dtl where BATCH_ID =(SELECT MAX(BATCH_ID) FROM cr_generate_batch_dtl WHERE BATCH_NO LIKE '%"+batch_no+"%')";
        	
        	logger.info("in generateBatch() Query for find max batch  :  "+bufInsSql.toString());
        	String maxBatch=ConnectionDAO.singleReturn(query);
        	logger.info("in generateBatch() maxBatch  :  "+maxBatch);
        	if(CommonFunction.checkNull(maxBatch).equalsIgnoreCase(""))
        		batch_no=batch_no+"_1";
        	else
        	{
        		int val=Integer.parseInt(maxBatch.substring(19));
        		val++;
        		batch_no=batch_no+"_"+val;	            		
        	}
        	logger.info("in generateBatch() final BatchNO  :  "+batch_no);
        	
        	ArrayList qryList=new ArrayList();
        	for(int i=0;i<size;i++)
        	{
        		ArrayList subList=(ArrayList)list.get(i);
        		StringBuilder insertQuery =	new StringBuilder();
        		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
        		insertQuery.append("insert into cr_generate_batch_dtl (BATCH_NO,LOAN_ID,LOAN_NO,INSTRUMENT_ID,PRESENTATION_DATE,INSTRUMENT_AMOUNT,REC_STATUS,BANK_ID,BANK_NAME,MAKER_ID,MAKER_DATE)values(?,?,?,?,STR_TO_DATE(?,'"+dateFormat+"'),?,?,?,?,?,DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND))");
        		
        		
        			insertPrepStmtObject.addString((batch_no).trim());   // BATCH_No
        		
        		if(CommonFunction.checkNull(subList.get(0)).trim().equalsIgnoreCase(""))// loan Id
   				 	insertPrepStmtObject.addNull();
   			 	else
   			 		insertPrepStmtObject.addInt(Integer.parseInt((String)subList.get(0)));   
        		
        		if(CommonFunction.checkNull(subList.get(1)).trim().equalsIgnoreCase("")) // loan No
       				insertPrepStmtObject.addNull();
       			else
       				insertPrepStmtObject.addString(((String)subList.get(1)).trim());  
        		
        		if(CommonFunction.checkNull(subList.get(2)).trim().equalsIgnoreCase(""))//instrument_id
        			insertPrepStmtObject.addNull();
       			else
       			    insertPrepStmtObject.addInt(Integer.parseInt((String)subList.get(2)));
        		
        		insertPrepStmtObject.addString(presentaionDate.trim());//PRESENTATION_DATE
        		
        		if(CommonFunction.checkNull(subList.get(3)).trim().equalsIgnoreCase(""))//PDC_INSTRUMENT_AMOUNT
        			insertPrepStmtObject.addNull();
       			else
       			    insertPrepStmtObject.addString(CommonFunction.checkNull(subList.get(3)));
        		
        		
        		insertPrepStmtObject.addString(("P").trim());   // Rec_Status
        		insertPrepStmtObject.addString((bankId).trim());// bankId
        		insertPrepStmtObject.addString((bankName).trim());// bankName
        		// mradul
        		insertPrepStmtObject.addString((userId).trim());// maker_id
        		insertPrepStmtObject.addString((makerDate).trim());// maker date
        		
        		insertPrepStmtObject.setSql(insertQuery.toString());
        		qryList.add(insertPrepStmtObject);
        		/*logger.info("+++++++++++-----"+insertQuery);
         		logger.info("+++++++++++-----"+qryList);
        		logger.info("+++++++++++-----"+insertPrepStmtObject);*/
        		subList=null;
        		insertQuery=null;
        		insertPrepStmtObject=null;	            		
        	}
        	boolean status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
        	if(status)
        		retVal=true;
        	else
        		retVal=false;
        		
        }

	}catch(Exception e){
		retVal=false;
		e.printStackTrace();
	}
	finally
	{
		bufInsSql=null;
		list=null;
	}

return retVal;
}
}
