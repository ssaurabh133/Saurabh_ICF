package com.cm.actions;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

import com.connect.CommonFunction;
import com.connect.ConnectionReportDumpsDAO;
import com.connect.PrepStmtObject;

public class ReportUploadDocuments 
{
	private static final Logger logger = Logger.getLogger(ReportUploadDocuments.class.getName()); 
	static ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.utill");
	static String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	static String dateFormat=resource.getString("lbl.dateInDao");
	static String dbType=resource.getString("lbl.dbType");
	static String dbo=resource.getString("lbl.dbPrefix");
		
	static int count=0;

public static boolean docUpload(HttpServletRequest request,FormFile myFile) 
	{
		boolean status=false;
		String fileName=null;
		String filePath=null;
		String message=null;
		File directory =null;
		File fileToCreate =null;
		FileOutputStream fileOutStream=null;
		StringBuilder query = new StringBuilder();
		String rpt=null;
		try
		{
			logger.info("docUpload().. ");
			query.append("select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='BOUNCE_UPLOAD_PATH'");			
			 rpt=ConnectionReportDumpsDAO.singleReturn(query.toString());
			 logger.info("fro testing the value in rpt is :: "+rpt);
			 directory = new File(rpt+"/");
			 logger.info("directory is"+directory);
			 
			 if(!directory.exists())
			 directory.mkdir();
			 
			
	        fileName=myFile.getFileName();
	        filePath = directory.getAbsolutePath();
	        if(!CommonFunction.checkNull(fileName).equals(""))
	        {  
	        	logger.info("Server path: filePath:==>>" +filePath);
	        	fileToCreate = new File(filePath, fileName);
	        	int fileSize = myFile.getFileSize(); //1048576 bytes = 1 MB
	           	if(fileSize<1048576)
	        	{
	        		fileOutStream = new FileOutputStream(fileToCreate);
	        		fileOutStream.write(myFile.getFileData());
	        		fileOutStream.flush();
	        		fileOutStream.close();
	        		message="O";
	        		status=true;	
	        	}
	        	else
	        	{
	        		message="E";
	        		status=false;
	        	}	        	
	        }
	        else
	        {
	        	message="S";
	        	status=false;
	        }	    
		}
		catch(Exception e)
		{e.printStackTrace();}
		finally
		{
			request.setAttribute("fileName", fileName);
			request.setAttribute("filePath", filePath);
			request.setAttribute("message",message);
			
			query=null;
			directory =null;
			fileToCreate =null;
			fileOutStream=null;	
			myFile=null;
			rpt=null;
		}		
		return status;		
	}

public static String readExcelBulkReport(HttpServletRequest request, HttpServletResponse response,String strFileName,String makerName,String makerDate)throws Exception
{
String result=null;
makerDate=CommonFunction.changeFormat(makerDate);
logger.info("In readExcelBulkReport() method of UploadDocuments for report bulk generation. ");
File objFile1=null;   
String query=null;
ArrayList alDeleteQuery=new ArrayList(1);
boolean deleteStatus=false;
PrepStmtObject insertPrepStmtObject=null;
ArrayList dataList=new ArrayList();
StringBuffer bufInsSql =new StringBuffer();
PrepStmtObject stmt=null; 
ArrayList     alFinalQry  = new ArrayList();
boolean insertStatus=false;
int row=0;
String wrongcolCount="Y";
String maxRowCount="Y";
String minRowCount="Y";
if(!"".equals(strFileName))
{    	
	  query="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='BOUNCE_UPLOAD_PATH'";		
	  String strPath=ConnectionReportDumpsDAO.singleReturn(query);
	  strPath=strPath + "/" + strFileName;
	  objFile1=new File(strPath);
  try 
  {     // for delete
 	 query=null;
 	 query="DELETE FROM bulk_report_generation_tmp ";
 	 insertPrepStmtObject=new PrepStmtObject();
		 insertPrepStmtObject.setSql(query);
		 logger.info("In bulk_report_generation_tmp  Delete Query   :  "+insertPrepStmtObject.printQuery());
		 alDeleteQuery.add(insertPrepStmtObject);
		 deleteStatus = ConnectionReportDumpsDAO.sqlInsUpdDeletePrepStmt(alDeleteQuery);		
		 logger.info("In bulk_report_generation_tmp  Delete Status  :  "+deleteStatus);
 	 if(deleteStatus)
 	 {
 		 
 		  Workbook workbook = Workbook.getWorkbook(new File(strPath));
 		  String sheetName[]=workbook.getSheetNames();
 		  Sheet sheet;
 		  Cell xlsCell;
 		  Cell[] cell; 
 		  sheet = workbook.getSheet(0);
 		  ArrayList arr=null;
 		  row=sheet.getRows();
 		  logger.info("the value of row is "+row);
 		  if(row>1001)
 		  {
 			  maxRowCount="Y";
 			  minRowCount="N";
 			  wrongcolCount="N";
 			  logger.info("when the value is row>1001");
 		  }        		 
 		  if(row<=1)
 		  {
 			  maxRowCount="N";
 			  minRowCount="Y";
 			  wrongcolCount="N";
 			  logger.info("row<=1");  
 		  }        		 
 		  if(1<row && row<1001)
 		  {
 			  maxRowCount="N";
 			  minRowCount="N";
 			  wrongcolCount="N";
 			  logger.info("1<row && row<1001");
 		  }
 		  if(maxRowCount=="N" && minRowCount=="N") 
 		  {
     		  for(int i=0; i<row; i++)	
     		  {
 				  	cell=sheet.getRow(i);
 				  	arr=new ArrayList();
 				  	int col=cell.length;
 				  	logger.info("the length of cell is "+col);
 				  	
 				  	if(col>2)
 				  	{
 				  		wrongcolCount="Y";
 				  		break;
 				  	}		
 				  	
     			  	for(int j=0; j<col; j++)
     				{
     			  		xlsCell = sheet.getCell(j,i);
     					arr.add(xlsCell.getContents().toString());
     				} 
     			  	
     				dataList.add(arr);
     				logger.info("hte value of datalist si  ::"+arr);
     			}
     		  logger.info("the size of data list is  ::"+dataList.size());
 		  }
     	  if(maxRowCount=="Y")
       			result="MXE";
     	  if(minRowCount=="Y")
     			result="MNE";
     	   if(wrongcolCount=="Y")
     			result="CLE";
     	   int siz=dataList.size();
			if(CommonFunction.checkNull(result).trim().equalsIgnoreCase(""))
			{ 	
				 bufInsSql.append("INSERT INTO bulk_report_generation_tmp (WL_LOAN_NO,FC_LOAN_NO,MAKER_ID,MAKER_DATE,UPLOAD_FLAG,REPORT_GENERAT_FLAG,WL_REJECT_REASON,FC_REJECT_REASON)");
				 bufInsSql.append(" values ( "); 
				 bufInsSql.append(" ?, " );//WELCOME_LETTER
				 bufInsSql.append(" ?, " );//FORE_CLOSURE
				 bufInsSql.append(" ?, " ); //MAKER_ID
				 bufInsSql.append(" ?," ); //MAKER_DATE
				 bufInsSql.append(" ?," ); //REC STATUS
				 bufInsSql.append(" ?, " );//REPORT_GENERAT_FLAG
				 bufInsSql.append(" ?, " );//WL_REJECT_REASON
				 bufInsSql.append(" ?)" ); //FC_REJECT_REASON
     	     for(int i=1;i<siz;i++)
		         {		
		        	  ArrayList subList=(ArrayList)dataList.get(i);
		        	 stmt = new PrepStmtObject();
		        	 
		        	 if(CommonFunction.checkNull(subList.get(0)).equalsIgnoreCase(""))
						 stmt.addNull();
					 else
					    	stmt.addString(CommonFunction.checkNull(subList.get(0)));						 
					 
					 if(CommonFunction.checkNull(subList.get(1)).equalsIgnoreCase(""))
						 stmt.addNull();
					 else
					    	stmt.addString(CommonFunction.checkNull(subList.get(1)));	
					 
					 
					 if(CommonFunction.checkNull(makerName).equalsIgnoreCase(""))
						 	stmt.addNull();
					 else
					    	stmt.addString(makerName); 					 
					 
					 stmt.addString(makerDate);
					 stmt.addString("Y");
					 stmt.addString("Y");
					 stmt.addString("N");
					 stmt.addString("N");
		
		 			 stmt.setSql(bufInsSql.toString());
		 			
		 			 logger.info("In bulk_report_generation_tmp()  Insert Query :  "+ stmt.printQuery());
		    		 alFinalQry.add(stmt);
		    		 stmt=null;
		    		 subList=null;
		    	}//end of for loop    			 
     	    insertStatus=ConnectionReportDumpsDAO.sqlInsUpdDeletePrepStmt(alFinalQry);
     	    logger.info("In readExcelnotepadUpload()  Insert Status :  "+insertStatus);
     	    logger.info("In readExcelnotepadUpload() makerName :  "+makerName);
     	    if(insertStatus)
     	    {
     	    	ArrayList<Object> in =new ArrayList<Object>();
     	    	ArrayList<Object> out =new ArrayList<Object>();
     	    	ArrayList outMessages = new ArrayList();         	    	
     	    	in.add(CommonFunction.checkNull(makerName));	
     	    	in.add(makerDate);
     	    	String rejectFlg = null;
     	    	String uplodFlg = null;
     	    	String s1 = null;
     	    	String s2 = null;
     	    	out.add(rejectFlg);
     	    	out.add(uplodFlg);
     	    	out.add(s1);
     	    	out.add(s2);
     	    	logger.info("bulk_report_generation_tmp("+in.toString()+","+out.toString()+")");  
     	    	outMessages=(ArrayList) ConnectionReportDumpsDAO.callSP("bulk_report_generation_tmp_pro",in,out); 
     	    	rejectFlg = (String)outMessages.get(0);
     	    	uplodFlg = (String)outMessages.get(1);	
     	    	s1 = (String)outMessages.get(2);
     	    	s2 = (String)outMessages.get(3);	
     	    	logger.info("In readExcelnotepadUpload()  OUT rejectFlg : "+rejectFlg);
     	    	logger.info("In readExcelnotepadUpload()  OUT uplodFlg  : "+uplodFlg);
     	    	logger.info("In readExcelnotepadUpload()  OUT s1        : "+s1);
     	    	logger.info("In readExcelnotepadUpload()  OUT s2        : "+s2);
     	    	if(!CommonFunction.checkNull(s2).trim().equalsIgnoreCase(""))
     	    		result="PDE"+"$"+s2;
     	    	else
     	    	{
     	    		result=resource.getString("lbl.sms");
     	    		if(CommonFunction.checkNull(rejectFlg).trim().equalsIgnoreCase(""))
     	    			rejectFlg="0";
     	    		int rej=Integer.parseInt(rejectFlg);
     	    		if(CommonFunction.checkNull(uplodFlg).trim().equalsIgnoreCase(""))
     	    			uplodFlg="0";
     	    		int up=Integer.parseInt(uplodFlg);
     	    		if(rej>0)
     	    			result=result+"Total rejected record-"+rej+"  and Total uploaded record-"+up+", Please check Error log...";
     	    	}
     	    	s1=null;
     	    	s2=null;
     	    	outMessages.clear();
     	    	outMessages=null;
     	    	in.clear();
     	    	in=null;
     	    	out.clear();
     	    	out=null;
     	    }//end of if(insertStatus)
     	     else
     	    	result="CLE";
			}
			else
				result="CLE";
 	 }
  }
  catch(Exception e)
  {
 	result="CLE";
 	e.printStackTrace();
  }
  finally 
 {
				 objFile1=null;   
				 query=null;
				 alDeleteQuery.clear();
				 alDeleteQuery=null;
				 insertPrepStmtObject=null;
				 dataList.clear();
				 dataList=null;
				 stmt=null; 
				 alFinalQry.clear();
				 alFinalQry=null;
				 wrongcolCount=null;
				 maxRowCount=null;
				 minRowCount=null;
				 strPath=null;
				 strFileName=null;
				 makerName=null;
				 makerDate=null;
 }
}
else
	result="FNE";
	logger.error("In readExcelnotepadUpload()  return Result :  "+result);
return result;
}

//work end.....


}




