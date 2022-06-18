package com.cp.actions;
import java.io.BufferedReader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;
import com.cm.vo.uploadVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;
import com.login.roleManager.UserObject;

public class UploadLeadDocuments 
{
	private static final Logger logger = Logger.getLogger(UploadLeadDocuments.class.getName()); 
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	static int count=0;
	public static boolean docUpload(HttpServletRequest request,FormFile myFile) {
		boolean status=false;
		String fileName="";
		String filePath="";
		String message="";
		try{
			String query="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='BOUNCE_UPLOAD_PATH'";
			logger.info("In docUpload(): query:==>>"+query);
			
			String rpt=ConnectionDAO.singleReturn(query);
			logger.info("The name you have entered is a file : "  +myFile);
			File directory = new File(rpt+"/");
			logger.info("mradul:docUpload:directory:==>"+directory);
			boolean isDirectory = directory.isDirectory();
			logger.info("mradul:docUpload:directory:==>"+isDirectory);
	        fileName    = myFile.getFileName();
	    //Get the servers upload directory real path name
	        filePath = directory.getAbsolutePath();
	    /* Save file on the server */
	        if(!fileName.equals("")){  
	        	logger.info("Server path: filePath:==>>" +filePath);
	        	//Create file
	        	File fileToCreate = new File(filePath, fileName);
	        		int fileSize = myFile.getFileSize(); //1048576 bytes = 1 MB
	        		logger.info("docUpload :Size of file==>> "+fileSize);
	        		if(fileSize<1048576)
	        		{
		        			FileOutputStream fileOutStream = new FileOutputStream(fileToCreate);
	        				fileOutStream.write(myFile.getFileData());
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
		request.setAttribute("fileName", fileName);
		request.setAttribute("filePath", filePath);
		request.setAttribute("message",message);
		return status;		
	}
	public static int countLine(String fileName)
	{
		 logger.info("In countLine() method ");
		 int rowTotalNum=0;
		 String query="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='BOUNCE_UPLOAD_PATH'";				
		 String rpt=ConnectionDAO.singleReturn(query);		
		 File directory = new File(rpt+"/"+fileName);
		 logger.info("File Name......... ==>>"+directory);
		 query=null;
	 try
		{ 
		 logger.info("in method of file.....................++++++++++++++++"+directory.exists());
		 if(directory.exists())
	      	{
			 logger.info("in method of file....................."+directory.length());
			 if(directory.length()>0){
		 Workbook workbook = Workbook.getWorkbook(directory);
		 
		 logger.info("in method of file.....................");
		 Sheet sheet;
		  sheet = workbook.getSheet(0);
    	  rowTotalNum=sheet.getRows();
    	  logger.info("Total Lines............. ==>>"+rowTotalNum);
			 }
			 else{
				 rowTotalNum=-1;
				 logger.info("sheet+++++++++++............. ==>>"+rowTotalNum);
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
		
public static String getPropertiesValue(String keyName)
{
	String value="";
	try
	{
		ResourceBundle resource=ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
		value=resource.getString(keyName);
	}
	catch (Exception e) {
		logger.error("In..getPropertiesValue UploadLeadDocuments.java file"+e.getMessage());
	}
	return value;
}


public static boolean readExcelforLeadUpload(HttpServletRequest request, HttpServletResponse response,String strFileName)throws Exception 
{

    logger.info("In readExcelforLeadUpload() method of UploadLeadDocuments. ");
    CallableStatement cst=null;
    HttpSession   session		  = null;
    String        makerID	 		= null;
    ArrayList     alFinalQry  = new ArrayList();
    ArrayList     alDeleteQuery  = new ArrayList(1);
    ArrayList     dataList  = new ArrayList();
    ArrayList     arr  = new ArrayList();
    ArrayList     subList  = new ArrayList();
    int           rowTotalNum = 0;
    int           row         = 0;
    int           col         = 0;
    String[][]    rowVal      = new String[1000][30];
    String        line        = null;
    String        msg         = "";
    String errorMsgSpace      = null;
    String errorMsgHeader     = null;
    String excep              = "";     
    boolean singleObject;
    File          objFile1		= null;      
    uploadVO vo=new uploadVO();
    BufferedReader bufRdr;
    boolean status=false;
    int intRem=0;
    int noOfRejectedRecord =0;
	int noOfUploadedRecord=0;        
   
    String message="";
    Connection utilConn =ConnectionDAO.getConnection();
    try 
    {
      
      PrepStmtObject insertPrepStmtObject=null;
      session     = request.getSession(false);
      UserObject userobj=(UserObject)session.getAttribute("userobject");	    
	  int companyId=0;
	  String userId="";
	  String branchId="";
	  String bDate="";
	  if(userobj!=null)
	  {
		userId=userobj.getUserId();
		branchId=userobj.getBranchId();
		companyId=userobj.getCompanyId();
		bDate=userobj.getBusinessdate();
	  }
	  makerID=CommonFunction.checkNull(userId);
	  logger.info("In readExcelforLeadUpload(): userId:==>>"+userId);
	  logger.info("In readExcelforLeadUpload(): branchId:==>>"+branchId);
	  logger.info("In readExcelforLeadUpload(): companyId:==>>"+companyId);
	  logger.info("In readExcelforLeadUpload(): bDate:==>>"+bDate);
	  logger.info("In readExcelforLeadUpload(): makerID:==>>"+makerID);
	  vo.setMakerId(""+userId);
	  vo.setMakerDate(bDate);
      if(!strFileName.equals(""))
      {
    	  logger.info("In readExcelforLeadUpload(): strFileName ==>>"+strFileName);
    	  String query="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='BOUNCE_UPLOAD_PATH'";		
		  String strPath=ConnectionDAO.singleReturn(query);
		  strPath     = strPath + "/" + strFileName;
		  objFile1    = new File(strPath);
          try 
          {     
        	  String strCountDeleteQuery = ConnectionDAO.singleReturn("SELECT COUNT(1) FROM cr_lead_dtl_temp  ");
        	  
        	  if(!strCountDeleteQuery.equalsIgnoreCase("0"))
        	{
        	  String strDeleteQuery = "DELETE FROM cr_lead_dtl_temp  ";
        	  logger.info("In readExcelforLeadUpload(): delete query of cr_lead_dtl_temp:==>>"+strDeleteQuery);
        	  alDeleteQuery.add(strDeleteQuery);
        	  boolean status1=ConnectionDAO.sqlInsUpdDelete(alDeleteQuery);
        	  logger.info("In readExcelforLeadUpload(): status1 ==>>"+status1);
        	  if(status1)
        	  {
        		  logger.info("Row is deleted....");
        	  }
        	  else
        	  {
        		  logger.info("Row is not deleted......");
        	  }
        	  
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
        			  cell = sheet.getRow(i);
        			  arr = new ArrayList();
        			  logger.info("In readExcelforLeadUpload(): :cell.length"+cell.length);
        			  for(int j=0; j<cell.length; j++)
        			  {
        				  xlsCell = sheet.getCell(j,i);
        				  logger.info("In readExcelforLeadUpload():xlsCell.getContents().toString() :"+xlsCell.getContents().toString());
        				  arr.add(xlsCell.getContents().toString());
                      }  
        			  dataList.add(arr);
        		 }
              }       
         }
         catch(IOException e) 
         { 
        	 e.printStackTrace();
         }
         logger.info("####### readExcelforBounceUpload() dataList.size() ###### "+dataList.size());
         
         
         for(int i=0;i<dataList.size();i++)
         {		
     	    subList=(ArrayList)dataList.get(i);
    		logger.info("In insert into readExcelforBounceUpload... size: ==>>"+subList.size());
        	if(subList.size()==38)
        	{ 
        		
        		String LEAD_ID               = CommonFunction.checkNull(subList.get(0)); 
        		String EXISTING_CUSTOMER     = CommonFunction.checkNull(subList.get(1)); 
        		String CUSTOMER_NAME         = CommonFunction.checkNull(subList.get(2)); 
        		String CONTACT_PERSON        = CommonFunction.checkNull(subList.get(3));  
        		String PERSON_DESIGNATION    = CommonFunction.checkNull(subList.get(4)); 
        		String ADDRESS_LINE1         = CommonFunction.checkNull(subList.get(5)); 
        		String COUNTRY               = CommonFunction.checkNull(subList.get(6)); 
        		String STATE                 = CommonFunction.checkNull(subList.get(7)); 
        		String DISTRICT              = CommonFunction.checkNull(subList.get(8)); 
        		String PINCODE               = CommonFunction.checkNull(subList.get(9)); 
        		String PRIMARY_PHONE         = CommonFunction.checkNull(subList.get(10)); 
        		String ALTERNATE_PHONE       = CommonFunction.checkNull(subList.get(11)); 
        		String EMAIL_ID              = CommonFunction.checkNull(subList.get(12)); 
        		String LANDMARK              = CommonFunction.checkNull(subList.get(13)); 
        		String SOURCE                = CommonFunction.checkNull(subList.get(14)); 
        		String SOURCE_DESCRIPTION    = CommonFunction.checkNull(subList.get(15)); 
        		String BRANCH_ID             = CommonFunction.checkNull(subList.get(16)); 
        		String LEAD_GENERATION_CITY  = CommonFunction.checkNull(subList.get(17)); 
        		String PRODUCT               = CommonFunction.checkNull(subList.get(18)); 
        		String SCHEME                = CommonFunction.checkNull(subList.get(19)); 
        		String REMARK                = CommonFunction.checkNull(subList.get(20)); 
        		String LEAD_GENERATION_DATE  = CommonFunction.checkNull(subList.get(21)); 
        		String LEAD_GENERATED_RM     = CommonFunction.checkNull(subList.get(22)); 
        		String MAKER_DATE            = CommonFunction.checkNull(subList.get(23)); 
        		String AUTHOR_DATE           = CommonFunction.checkNull(subList.get(24)); 
        		String TURNOVER              = CommonFunction.checkNull(subList.get(25)); 
        		String REC_STATUS            = CommonFunction.checkNull(subList.get(26)); 
        		String ALLOCATED_BRANCH      = CommonFunction.checkNull(subList.get(27)); 
        		String CUSTOMER_TYPE         = CommonFunction.checkNull(subList.get(28)); 
        		String INDUSTRY_ID           = CommonFunction.checkNull(subList.get(29)); 
        		String SUB_INDUSTRY_ID       = CommonFunction.checkNull(subList.get(30)); 
        		String AMOUNT_REQUIRED       = CommonFunction.checkNull(subList.get(31)); 
        		String LOAN_TENURE           = CommonFunction.checkNull(subList.get(32)); 
        		String GROUP_DESC            = CommonFunction.checkNull(subList.get(33)); 
        		String GROUP_TYPE            = CommonFunction.checkNull(subList.get(34)); 
        		String SOURCE_TYPE           = CommonFunction.checkNull(subList.get(35)); 
        		String SOURCE_CODE           = CommonFunction.checkNull(subList.get(36)); 
        		String ADDRESS_TYPE          = CommonFunction.checkNull(subList.get(37));    				 
    						         
    		         Integer seq=new Integer(i);			 
    				 
    				 logger.info("In readExcelforBounceUpload(): LOAN_NO==>>"+LEAD_ID);
    				 logger.info("In readExcelforBounceUpload():RECEIPT_MODE==>> "+EXISTING_CUSTOMER);
    				 logger.info("In readExcelforBounceUpload(): INSTRUMENT_NO==>>"+CUSTOMER_NAME);
    				   
    				 
    				 StringBuffer bufInsSql = new StringBuffer();
    				 insertPrepStmtObject = new PrepStmtObject();   
    				 
    				 bufInsSql.append("insert into cr_lead_dtl_temp(LEAD_ID,EXISTING_CUSTOMER,CUSTOMER_NAME,CONTACT_PERSON,PERSON_DESIGNATION,ADDRESS_LINE1,COUNTRY,STATE,DISTRICT,PINCODE,PRIMARY_PHONE,ALTERNATE_PHONE,EMAIL_ID,LANDMARK,SOURCE,SOURCE_DESCRIPTION,BRANCH_ID,LEAD_GENERATION_CITY,PRODUCT,SCHEME,REMARK,LEAD_GENERATION_DATE,LEAD_GENERATED_RM,MAKER_DATE,AUTHOR_DATE,TURNOVER,REC_STATUS,ALLOCATED_BRANCH,CUSTOMER_TYPE,INDUSTRY_ID,SUB_INDUSTRY_ID,AMOUNT_REQUIRED,LOAN_TENURE,GROUP_DESC,GROUP_TYPE,SOURCE_TYPE,SOURCE_CODE,ADDRESS_TYPE)");
 	  				 bufInsSql.append(" values ( "); 	  				
 	  			 bufInsSql.append(" ?," );//LEAD_ID               
 	  			 bufInsSql.append(" ?," );//EXISTING_CUSTOMER     
 	  			 bufInsSql.append(" ?," );//CUSTOMER_NAME         
 	  			 bufInsSql.append(" ?," );//CONTACT_PERSON        
 	  			 bufInsSql.append(" ?," );//PERSON_DESIGNATION    
 	  			 bufInsSql.append(" ?," );//ADDRESS_LINE1         
 	  			 bufInsSql.append(" ?," );//COUNTRY               
 	  			 bufInsSql.append(" ?," );//STATE                 
 	  			 bufInsSql.append(" ?," );//DISTRICT              
 	  			 bufInsSql.append(" ?," );//PINCODE               
 	  			 bufInsSql.append(" ?," );//PRIMARY_PHONE         
 	  			 bufInsSql.append(" ?," );//ALTERNATE_PHONE       
 	  			 bufInsSql.append(" ?," );//EMAIL_ID              
 	  			 bufInsSql.append(" ?," );//LANDMARK              
 	  			 bufInsSql.append(" ?," );//SOURCE                
 	  			 bufInsSql.append(" ?," );//SOURCE_DESCRIPTION    
 	  			 bufInsSql.append(" ?," );//BRANCH_ID             
 	  			 bufInsSql.append(" ?," );//LEAD_GENERATION_CITY  
 	  			 bufInsSql.append(" ?," );//PRODUCT               
 	  			 bufInsSql.append(" ?," );//SCHEME                
 	  			 bufInsSql.append(" ?," );//REMARK                
 	  			 bufInsSql.append(" ?," );//LEAD_GENERATION_DATE  
 	  			 bufInsSql.append(" ?," );//LEAD_GENERATED_RM     
 	  			 bufInsSql.append(" ?," );//MAKER_DATE            
 	  			 bufInsSql.append(" ?," );//AUTHOR_DATE           
 	  			 bufInsSql.append(" ?," );//TURNOVER              
 	  			 bufInsSql.append(" ?," );//REC_STATUS            
 	  			 bufInsSql.append(" ?," );//ALLOCATED_BRANCH      
 	  			 bufInsSql.append(" ?," );//CUSTOMER_TYPE         
 	  			 bufInsSql.append(" ?," );//INDUSTRY_ID           
 	  			 bufInsSql.append(" ?," );//SUB_INDUSTRY_ID       
 	  			 bufInsSql.append(" ?," );//AMOUNT_REQUIRED       
 	  			 bufInsSql.append(" ?," );//LOAN_TENURE           
 	  			 bufInsSql.append(" ?," );//GROUP_DESC            
 	  			 bufInsSql.append(" ?," );//GROUP_TYPE            
 	  			 bufInsSql.append(" ?," );//SOURCE_TYPE           
 	  			 bufInsSql.append(" ?," );//SOURCE_CODE           
 	  			 bufInsSql.append(" ?" );//ADDRESS_TYPE   
 	  				 bufInsSql.append(" )" );     				 
    					 
						 if(CommonFunction.checkNull(LEAD_ID).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						 else
							insertPrepStmtObject.addString(LEAD_ID);	
						 
						 if(CommonFunction.checkNull(EXISTING_CUSTOMER).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						 else
							insertPrepStmtObject.addString(EXISTING_CUSTOMER);
						 
						 if(CommonFunction.checkNull(CUSTOMER_NAME).equalsIgnoreCase(""))
						    insertPrepStmtObject.addNull();
						    else
						    insertPrepStmtObject.addString(CUSTOMER_NAME);
						
						 if(CommonFunction.checkNull(CONTACT_PERSON).equalsIgnoreCase(""))
						     insertPrepStmtObject.addNull();
							 else
							 insertPrepStmtObject.addString(CONTACT_PERSON);
						 
						 if(CommonFunction.checkNull(PERSON_DESIGNATION).equalsIgnoreCase(""))
							 insertPrepStmtObject.addNull();
						 else
							 insertPrepStmtObject.addString(PERSON_DESIGNATION);
 					 	
						 if(CommonFunction.checkNull(ADDRESS_LINE1).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							 else
								insertPrepStmtObject.addString(ADDRESS_LINE1);	
							 
							 if(CommonFunction.checkNull(COUNTRY).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							 else
								insertPrepStmtObject.addString(COUNTRY);
							 
							 if(CommonFunction.checkNull(STATE).equalsIgnoreCase(""))
							    insertPrepStmtObject.addNull();
							    else
							    insertPrepStmtObject.addString(STATE);
							
							 if(CommonFunction.checkNull(DISTRICT).equalsIgnoreCase(""))
							     insertPrepStmtObject.addNull();
								 else
								 insertPrepStmtObject.addString(DISTRICT);
							 
							 if(CommonFunction.checkNull(PINCODE).equalsIgnoreCase(""))
								 insertPrepStmtObject.addNull();
							 else
								 insertPrepStmtObject.addString(PINCODE);
							 if(CommonFunction.checkNull(PRIMARY_PHONE).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								 else
									insertPrepStmtObject.addString(PRIMARY_PHONE);	
								 
								 if(CommonFunction.checkNull(ALTERNATE_PHONE).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								 else
									insertPrepStmtObject.addString(ALTERNATE_PHONE);
								 
								 if(CommonFunction.checkNull(EMAIL_ID).equalsIgnoreCase(""))
								    insertPrepStmtObject.addNull();
								    else
								    insertPrepStmtObject.addString(EMAIL_ID);
								
								 if(CommonFunction.checkNull(LANDMARK).equalsIgnoreCase(""))
								     insertPrepStmtObject.addNull();
									 else
									 insertPrepStmtObject.addString(LANDMARK);
								 
								 if(CommonFunction.checkNull(SOURCE).equalsIgnoreCase(""))
									 insertPrepStmtObject.addNull();
								 else
									 insertPrepStmtObject.addString(SOURCE);
								 if(CommonFunction.checkNull(SOURCE_DESCRIPTION).equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
									 else
										insertPrepStmtObject.addString(SOURCE_DESCRIPTION);	
									 
									 if(CommonFunction.checkNull(BRANCH_ID).equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
									 else
										insertPrepStmtObject.addString(BRANCH_ID);
									 
									 if(CommonFunction.checkNull(LEAD_GENERATION_CITY).equalsIgnoreCase(""))
									    insertPrepStmtObject.addNull();
									    else
									    insertPrepStmtObject.addString(LEAD_GENERATION_CITY);
									
									 if(CommonFunction.checkNull(PRODUCT).equalsIgnoreCase(""))
									     insertPrepStmtObject.addNull();
										 else
										 insertPrepStmtObject.addString(PRODUCT);
									 
									 if(CommonFunction.checkNull(SCHEME).equalsIgnoreCase(""))
										 insertPrepStmtObject.addNull();
									 else
										 insertPrepStmtObject.addString(SCHEME);
									 if(CommonFunction.checkNull(REMARK).equalsIgnoreCase(""))
											insertPrepStmtObject.addNull();
										 else
											insertPrepStmtObject.addString(REMARK);	
										 
										 if(CommonFunction.checkNull(LEAD_GENERATION_DATE).equalsIgnoreCase(""))
											insertPrepStmtObject.addNull();
										 else
											insertPrepStmtObject.addString(LEAD_GENERATION_DATE);
										 
										 if(CommonFunction.checkNull(LEAD_GENERATED_RM).equalsIgnoreCase(""))
										    insertPrepStmtObject.addNull();
										    else
										    insertPrepStmtObject.addString(LEAD_GENERATED_RM);
										
										 if(CommonFunction.checkNull(MAKER_DATE).equalsIgnoreCase(""))
										     insertPrepStmtObject.addNull();
											 else
											 insertPrepStmtObject.addString(MAKER_DATE);
										 
										 if(CommonFunction.checkNull(AUTHOR_DATE).equalsIgnoreCase(""))
											 insertPrepStmtObject.addNull();
										 else
											 insertPrepStmtObject.addString(AUTHOR_DATE);
										 if(CommonFunction.checkNull(TURNOVER).equalsIgnoreCase(""))
												insertPrepStmtObject.addNull();
											 else
												insertPrepStmtObject.addString(TURNOVER);	
											 
											 if(CommonFunction.checkNull(REC_STATUS).equalsIgnoreCase(""))
												insertPrepStmtObject.addNull();
											 else
												insertPrepStmtObject.addString(REC_STATUS);
											 
											 if(CommonFunction.checkNull(ALLOCATED_BRANCH).equalsIgnoreCase(""))
											    insertPrepStmtObject.addNull();
											    else
											    insertPrepStmtObject.addString(ALLOCATED_BRANCH);
											
											 if(CommonFunction.checkNull(CUSTOMER_TYPE).equalsIgnoreCase(""))
											     insertPrepStmtObject.addNull();
												 else
												 insertPrepStmtObject.addString(CUSTOMER_TYPE);
											 
											 if(CommonFunction.checkNull(INDUSTRY_ID).equalsIgnoreCase(""))
												 insertPrepStmtObject.addNull();
											 else
												 insertPrepStmtObject.addString(INDUSTRY_ID);
											 if(CommonFunction.checkNull(SUB_INDUSTRY_ID).equalsIgnoreCase(""))
												    insertPrepStmtObject.addNull();
												    else
												    insertPrepStmtObject.addString(SUB_INDUSTRY_ID);
												
												 if(CommonFunction.checkNull(AMOUNT_REQUIRED).equalsIgnoreCase(""))
												     insertPrepStmtObject.addNull();
													 else
													 insertPrepStmtObject.addString(AMOUNT_REQUIRED);
												 
												 if(CommonFunction.checkNull(LOAN_TENURE).equalsIgnoreCase(""))
													 insertPrepStmtObject.addNull();
												 else
													 insertPrepStmtObject.addString(LOAN_TENURE);
 						                       
												 if(CommonFunction.checkNull(GROUP_DESC).equalsIgnoreCase(""))
													 insertPrepStmtObject.addNull();
												 else
													 insertPrepStmtObject.addString(GROUP_DESC);
 						
												 if(CommonFunction.checkNull(GROUP_TYPE).equalsIgnoreCase(""))
													 insertPrepStmtObject.addNull();
												 else
													 insertPrepStmtObject.addString(GROUP_TYPE);
 						
												 if(CommonFunction.checkNull(SOURCE_TYPE).equalsIgnoreCase(""))
													 insertPrepStmtObject.addNull();
												 else
													 insertPrepStmtObject.addString(SOURCE_TYPE);
 						
												 if(CommonFunction.checkNull(SOURCE_CODE).equalsIgnoreCase(""))
													 insertPrepStmtObject.addNull();
												 else
													 insertPrepStmtObject.addString(SOURCE_CODE);
 						
												 if(CommonFunction.checkNull(ADDRESS_TYPE).equalsIgnoreCase(""))
													 insertPrepStmtObject.addNull();
												 else
													 insertPrepStmtObject.addString(ADDRESS_TYPE);
 						
    						
    				insertPrepStmtObject.setSql(bufInsSql.toString());
    				logger.info("IN readExcelforBounceUpload() insert query1 ### "+ insertPrepStmtObject.printQuery());
    				alFinalQry.add(insertPrepStmtObject);
    				insertPrepStmtObject=null;
    				bufInsSql=null;
    			}    			 
        }
        boolean insertStatus=false;
        insertStatus=ConnectionDAO.sqlInsUpdDeletePrepStmt(alFinalQry);
        //neeraj
    
       logger.info(" Before Procedure Call       vinod .............. "+insertStatus);         
     ArrayList<Object> in =new ArrayList<Object>();
	   ArrayList<Object> out =new ArrayList<Object>();
	   ArrayList outMessages = new ArrayList(); 
	       
       in.add(companyId);
   
	   String s1 = "";
	   String s2 = "";
	   
	 //  out.add(noOfRejectedRecord);
	   //out.add(noOfUploadedRecord);
	   out.add(s1);
	   out.add(s2);	   
	   
	   logger.info(" BULK_UPLOAD_LEAD_DTL("+in.toString()+","+out.toString()+")");  
       outMessages=(ArrayList) ConnectionDAO.callSP("BULK_UPLOAD_LEAD_DTL",in,out);        
      
       //noOfRejectedRecord =Integer.parseInt((String)outMessages.get(0));
	   //noOfUploadedRecord=Integer.parseInt((String)outMessages.get(1));
       s1 = (String)outMessages.get(0);
	   s2 = (String)outMessages.get(1);
	   
	   logger.info("BULK_UPLOAD_LEAD_DTL OUT s1: "+s1);
	   logger.info("BULK_UPLOAD_LEAD_DTL OUT s2: "+s2);
	             
		if(s1.equals("S")&& insertStatus)
		{
			status=true;
			/*for(int i=1;i<dataList.size();i++)
		    {		
		   	   subList=(ArrayList)dataList.get(i);
		       logger.info("In insert into readExcelforBounceUpload()... size: ==>>"+subList.size());
		       if(subList.size()>0)
		       {   
		    		logger.info("IN csv() Loan No ### "+ subList.get(0));
					String checkErrorQ="select UPLOADED_FLAG from cr_instrument_dtl_temp where MAKER_ID='"+vo.getMakerId()+"' AND SEQUENCE_NO='"+i+"' ";			
					String checkErrorStatus=ConnectionDAO.singleReturn(checkErrorQ);
					logger.info("checkErrorStatus: "+checkErrorStatus);
					if(checkErrorStatus.equalsIgnoreCase("N"))
					{
						 logger.info("some data is not uploaded");
						 status=false;
						 break;
					}
		    	}
		    }*/
		}
		else
		{
			logger.info("out parameter of procedure is not 'S'");
			status=false;
		}   
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
  //objFile1.delete();
  request.setAttribute("noOfRejectedRecord", noOfRejectedRecord);
  request.setAttribute("noOfUploadedRecord", noOfUploadedRecord);
  utilConn.close();
}
return status;
}

}


