package com.caps.daoImplMYSQL;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;
import com.caps.VO.CollUploadDataVO;
import com.caps.dao.CollUoloadDataDAO;
import com.caps.VO.uploadVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;
import com.login.roleManager.UserObject;

public class CollUoloadDataDAOImpl implements CollUoloadDataDAO {
	private static final Logger logger = Logger.getLogger(CollUoloadDataDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	
	public boolean uploadData(HttpServletRequest request,FormFile myFile) {
		 logger.info("In uploadData() method of CollUoloadDataDAOImpl");
			boolean status=false;
			String fileName="";
			String filePath="";
			String message="";
			try{
				String query="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='C0ll_UPLOAD_DATA'";			
				String rpt=ConnectionDAO.singleReturn(query);
				logger.info("The name you have entered is a file : "  +myFile);
				File directory = new File(rpt+"/");
				boolean isDirectory = directory.isDirectory();
		        fileName    = myFile.getFileName();
		        logger.info("fileName--"+fileName);
		    //Get the servers upload directory real path name
		        filePath = directory.getAbsolutePath();
		        logger.info("filePath--"+filePath);
		    /* Save file on the server */
		        if(!fileName.equals("")){  
		        	logger.info("Server path:" +filePath);
		        	//Create file
		        	File fileToCreate = new File(filePath, fileName);
		        	logger.info("fileToCreate:----"+fileToCreate);
		        		int fileSize = myFile.getFileSize(); //1048576 bytes = 1 MB
		        		logger.info("fileSize:----"+fileSize);
		        		//logger.info("Size of file= "+fileSize);
		        		if(fileSize<10485760)
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
		        			logger.info("File size exceeds the upper limit of 10 MB.");
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

	public int countUploadDataLine(String fileName) {
		logger.info("In countUploadDataLine() method of CollUoloadDataDAOImpl");
		 int rowTotalNum=0;
		 String query="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='C0ll_UPLOAD_DATA'";			
		 String rpt=ConnectionDAO.singleReturn(query);		
		 File directory = new File(rpt+"/"+fileName);
		 logger.info("File Name......... ==>>"+directory);
	 try
		{
		
		 RandomAccessFile randFile = new RandomAccessFile(directory,"r");
	      long lastRec=randFile.length();	     
	      randFile.close();
	      if(directory.exists())
	      	{
	      FileReader fileRead = new FileReader(directory);
	      LineNumberReader lineRead = new LineNumberReader(fileRead);
	      lineRead.skip(lastRec);
	      rowTotalNum=lineRead.getLineNumber()-1;

	      fileRead.close();
	      lineRead.close();
	      logger.info("Total Lines............. ==>>"+rowTotalNum);
	      	}
	      else
	      {
	    	  logger.info("File not exitxt..");

	      }
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		  return rowTotalNum;
	}

	public boolean csvRead(HttpServletRequest request,
			HttpServletResponse response, CollUploadDataVO collUploadDataVo,
			String file) {
		logger.info("In csvRead() method of CollUoloadDataDAOImpl");
	    ArrayList<Object> in =new ArrayList<Object>();
		ArrayList<Object> out =new ArrayList<Object>();
		ArrayList outMessages = new ArrayList();
		 String s1="";
		String s2="";
		String s3="";
 logger.info("in CSV Read ==>> ");
//     CallableStatement cst=null;
     HttpSession   session		  = null;
     String        makerID	 		= null;
     StringBuffer  sql         = null;
     ArrayList     alFinalQry  = new ArrayList(1);
     ArrayList     alDeleteQuery  = new ArrayList(1);
     int           rowTotalNum = 0;
     int           row         = 0;
     int           col         = 0;
     String[][]    rowVal      = new String[20000][55];
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
     StringBuffer bufInsSqlLoan = null;
    StringBuilder budInsSqlCust=null;
    StringBuilder budInsSqlAsset=null;
    StringBuilder budInsSqlReference=null;
     String message="";
     ArrayList arr =null;
     ArrayList subList =new ArrayList();
// 	Connection con=ConnectionDAO.getConnection();
// 	   Connection utilConn =ConnectionDAO.getConnection();
     try {
 
//			con.setAutoCommit(false);
			
    
       PrepStmtObject insertPrepStmtObject=null;
       session     = request.getSession(false);
       UserObject userobj=(UserObject)session.getAttribute("userobject");
	      String userId=userobj.getUserId();
	      int companyId=userobj.getCompanyId();
	      makerID=CommonFunction.checkNull(userId);
		  vo.setMakerId(""+userId);
		  vo.setMakerDate(userobj.getBusinessdate());
       logger.info("File Name in Process.."+file);
    
       //logger.info("poolIdMakerVO.getPoolName()........."+ poolIdMakerVO.getPoolName());	
		//  logger.info("getPoolCreationDate()........."+ poolIdMakerVO.getPoolCreationDate());	
		//  logger.info("getCutOffDate()........."+ poolIdMakerVO.getCutOffDate());	
		//  logger.info("getPoolType()........."+ poolIdMakerVO.getPoolType());	
		//  logger.info("getLbxinstituteID()........."+ poolIdMakerVO.getLbxinstituteID());	
     
       
       if(!file.equals(""))
       {
       logger.info(" strFileName is ==>>"+file);
       String query="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='C0ll_UPLOAD_DATA'";			
		 String strPath=ConnectionDAO.singleReturn(query);
       strPath     = strPath + "/" + file;
 
       objFile1    = new File(strPath);
       logger.info(" strFile path... ==>>"+objFile1);
     //  logger.info(" strFileName < 60 ==>> "+file!=null?file:file.length()+"("+file.length()+")");
       ArrayList dataList=null;
       
	  try{
		     //Counting line......
		       RandomAccessFile randFile = new RandomAccessFile(strPath,"r");
		       long lastRec=randFile.length();
		       randFile.close();
		       FileReader fileRead = new FileReader(strPath);
		       LineNumberReader lineRead = new LineNumberReader(fileRead);
		       lineRead.skip(lastRec);
		       rowTotalNum=lineRead.getLineNumber();
		       fileRead.close();
		       lineRead.close();
		       logger.info("Total Lines ==>>"+rowTotalNum);
		       if(rowTotalNum-1>20000)
		       {
		     //  request.setAttribute("maxCount", rowTotalNum-1);
		       logger.info("Row can not be more than 20000");
		       msg="RE";
		       }
		
		      //reading value...
		        InputStreamReader is= new InputStreamReader(new FileInputStream(objFile1));
		        bufRdr  = new BufferedReader(is);
		        line    = bufRdr.readLine();
		       // logger.info("data is...."+line);            
		      //spiliting value...
		       int   colltest  = 0;
		       String token    = null;
		       String previousTokenVal = null;
		
		      
		       do {
		         previousTokenVal="|";
		         StringTokenizer st = new StringTokenizer(line,"|",true);
		         while (st.hasMoreTokens())
		         {
		            token = st.nextToken();
		           if(token.equals(previousTokenVal)) {
		             rowVal[row][col]="";
		             }
		           else {
		             if(token.equals("|")) {
		               previousTokenVal=token;
		               continue;
		               }
		             else {
		               rowVal[row][col]=token;
		               }
		             }
		           col++;
		           previousTokenVal=token;
		           }
		         col = 0;
		         intRem ++;
		         row++;
		         }
		       while((line = bufRdr.readLine()) != null && row <=rowTotalNum);   //INSTRUMENT_TYPE|INSTRUMENT_NO|INSTRUMENT_DATE|INSTRUMENT_AMOUNT|BANK_ID|BRANCH_ID|BANK_ACCOUNT|STATUS|REASON_ID
		        bufRdr.close();
		        
		       }
		     catch(IOException e) 
		     {
		   	logger.error("In Exception Total Lines ==>>"+ e.getMessage());
		     }


  //insert start.............
       
       
       
       /*try
       {
     	  
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
                  // logger.info("cell.length:----"+cell.length);
                   for(int j=0; j<cell.length; j++)
                   {
                 	
                       xlsCell = sheet.getCell(j,i);
                       
                       arr.add(xlsCell.getContents().toString());
                                              
                       
                       //dataList.add(xlsCell.getContents().toString());
                    }  dataList.add(arr);	 
               }
           }
       }
       catch(Exception exec)
       {
          logger.info("Exception:- "+exec);
       }
*/
    
      //insert start.............

      try 
      {
    	  //Deleting data..start....     
          String strDeleteQueryLoan = "DELETE FROM coll_loan_dtl_temp WHERE MAKER_ID = '"+vo.getMakerId()+"' ";
          String strDeleteQueryCust = "DELETE FROM coll_customer_dtl_temp WHERE MAKER_ID = '"+vo.getMakerId()+"' ";
          String strDeleteQueryAsset = "DELETE FROM coll_asset_collateral_temp WHERE MAKER_ID = '"+vo.getMakerId()+"' ";
          String strDeleteQueryReference = "DELETE FROM coll_reference_m_temp WHERE MAKER_ID = '"+vo.getMakerId()+"' ";
          alDeleteQuery.add(strDeleteQueryLoan);
          alDeleteQuery.add(strDeleteQueryCust);
          alDeleteQuery.add(strDeleteQueryAsset);
          alDeleteQuery.add(strDeleteQueryReference);
          boolean status1=ConnectionDAO.sqlInsUpdDelete(alDeleteQuery);
          logger.info(" Deleting query is..... ==>>"+status1);
         if(status1)
         {
         	logger.info("Row is deleted....");
         }
         else
         {
         	logger.info("Row is not deleted......");
         }
    	    String qry=" Select VALUE from generate_sequence_tbl where SEQ_KEY='COLL_BATCH_ID' ;";
     	    String batchIdStr=ConnectionDAO.singleReturn(qry);
     	    int batchId=Integer.parseInt(batchIdStr);
     	    int newbatchId=batchId+1;
     	   logger.info("batchId:----"+batchId);
     	    ArrayList updateList=new ArrayList();
     	   updateList.add("update  generate_sequence_tbl set VALUE='"+newbatchId+"' where SEQ_KEY='COLL_BATCH_ID' ");
     	    boolean res=ConnectionDAO.sqlInsUpdDelete(updateList);
     	    logger.info("result of update in generate_sequence_tbl:---"+res);
     	    
     	/*Insert for LOAN STARTS:----*/
     	bufInsSqlLoan = new StringBuffer();
     	    
		bufInsSqlLoan.append("INSERT INTO coll_loan_dtl_temp(LOAN_NO,LOAN_SCHEME_DESCRIPTION,");
		bufInsSqlLoan.append("LOAN_CUSTOMER_ID,LOAN_BRANCH_DESCRIPTION,REC_STATUS,LOAN_DELINQUENCY_DATE,LOAN_LOAN_AMOUNT,LOAN_DISBURSAL_DATE,");	
		bufInsSqlLoan.append("LOAN_EMI_AMOUNT,LOAN_NO_OF_INSTALLMENT,LOAN_BILLED_INSTL_NUM,LOAN_DPD,LOAN_BILLED_PRINCIPAL,LOAN_BALANCE_PRINCIPAL,LOAN_OVERDUE_PRINCIPAL,");
		bufInsSqlLoan.append("LOAN_RECEIVED_PRINCIPAL,LOAN_OVERDUE_INSTL_NUM,LOAN_OVERDUE_AMOUNT,LOAN_BILLED_INSTL_AMOUNT,LOAN_RECEIVED_INSTL_AMOUNT,LOAN_BALANCE_INSTL_AMOUNT,");
		bufInsSqlLoan.append("LOAN_CUSTOMER_TYPE,LOAN_TENURE,LOAN_REPAYMENT_FREQ,LOAN_REPAY_EFF_DATE,LOAN_MATURITY_DATE,");
		bufInsSqlLoan.append("LOAN_FINAL_RATE,LOAN_RATE_TYPE,LOAN_ASSET_COST,LOAN_DUE_DAY,NPA_FLAG,LOAN_DPD_STRING,LOAN_FLAT_RATE,LOAN_EFF_RATE,LOAN_IRR1,LOAN_IRR2,LOAN_LPP_AMOUNT,");
		bufInsSqlLoan.append("LAST_LPP_DATE,LOAN_APPROVAL_DATE,DISBURSAL_STATUS,LOAN_DELINQUENCY_FLAG,BATCH_ID,MAKER_ID,MAKER_DATE)");
		
		bufInsSqlLoan.append(" values ( ");
		//bufInsSqlLoan.append(" ?," ); //LOAN_ID
		bufInsSqlLoan.append(" ?," ); //LOAN_NO
		//bufInsSqlLoan.append(" ?," ); //LOAN_DEAL_ID
		//bufInsSqlLoan.append(" ?," ); //LOAN_PRODUCT
		//bufInsSqlLoan.append(" ?," );//LOAN_PRODUCT_DESCRIPTION
		//bufInsSqlLoan.append(" ?," ); //LOAN_SCHEME
		bufInsSqlLoan.append(" ?," );//LOAN_SCHEME_DESCRIPTION
		bufInsSqlLoan.append(" ?," ); //LOAN_CUSTOMER_ID
		//bufInsSqlLoan.append(" ?," ); //LOAN_BRANCH
		bufInsSqlLoan.append(" ?," ); //LOAN_BRANCH_DESCRIPTION
		bufInsSqlLoan.append(" ?," ); //REC_STATUS	 				
		bufInsSqlLoan.append(" ?," );// LOAN_DELINQUENCY_DATE
		bufInsSqlLoan.append(" ?," );// LOAN_LOAN_AMOUNT
		bufInsSqlLoan.append(" ?," );//LOAN_DISBURSAL_DATE
		bufInsSqlLoan.append(" ?," ); //LOAN_EMI_AMOUNT
		bufInsSqlLoan.append(" ?," );//LOAN_NO_OF_INSTALLMENT
		bufInsSqlLoan.append(" ?," ); //LOAN_BILLED_INSTL_NUM
		bufInsSqlLoan.append(" ?," );//LOAN_DPD
		bufInsSqlLoan.append(" ?," );//LOAN_BILLED_PRINCIPAL
		bufInsSqlLoan.append(" ?," ); //LOAN_BALANCE_PRINCIPAL
		bufInsSqlLoan.append(" ?," );//LOAN_OVERDUE_PRINCIPAL
		bufInsSqlLoan.append(" ?," );//LOAN_RECEIVED_PRINCIPAL
		bufInsSqlLoan.append(" ?," ); //LOAN_OVERDUE_INSTL_NUM
		bufInsSqlLoan.append(" ?," ); //LOAN_OVERDUE_AMOUNT
		bufInsSqlLoan.append(" ?," ); //LOAN_BILLED_INSTL_AMOUNT
		bufInsSqlLoan.append(" ?," ); //LOAN_RECEIVED_INSTL_AMOUNT
		bufInsSqlLoan.append(" ?," ); //LOAN_BALANCE_INSTL_AMOUNT
		//bufInsSqlLoan.append(" ?," );//LOAN_PRODUCT_CATEGORY
		bufInsSqlLoan.append(" ?," ); //LOAN_CUSTOMER_TYPE
		bufInsSqlLoan.append(" ?," );//LOAN_TENURE
		bufInsSqlLoan.append(" ?," ); //LOAN_REPAYMENT_FREQ
		bufInsSqlLoan.append(" ?," ); //LOAN_REPAY_EFF_DATE
		bufInsSqlLoan.append("?," ); //LOAN_MATURITY_DATE
		bufInsSqlLoan.append(" ?, " ); //LOAN_FINAL_RATE
		bufInsSqlLoan.append(" ?, " ); //LOAN_RATE_TYPE
		bufInsSqlLoan.append(" ?, " ); //LOAN_ASSET_COST
		bufInsSqlLoan.append(" ?, " ); //LOAN_DUE_DAY
		bufInsSqlLoan.append(" ?, " ); //NPA_FLAG
		bufInsSqlLoan.append(" ?, " ); //LOAN_DPD_STRING
		bufInsSqlLoan.append(" ?, " ); //LOAN_FLAT_RATE
		bufInsSqlLoan.append(" ?, " ); //LOAN_EFF_RATE
		bufInsSqlLoan.append(" ?, " ); //LOAN_IRR1
		bufInsSqlLoan.append(" ?, " ); //LOAN_IRR2
		bufInsSqlLoan.append(" ?, " ); //LOAN_LPP_AMOUNT
		bufInsSqlLoan.append(" ?," ); //LAST_LPP_DATE
		bufInsSqlLoan.append("?," ); //LOAN_APPROVAL_DATE
		bufInsSqlLoan.append(" ?, " );// DISBURSAL_STATUS
		bufInsSqlLoan.append(" ?, " );// LOAN_DELINQUENCY_FLAG
		bufInsSqlLoan.append(" ?, " );// BATCH
		bufInsSqlLoan.append(" ?, " ); //MAKER_ID
		bufInsSqlLoan.append("DATE_ADD(STR_TO_DATE(?,'"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND))" ); //MAKER_DATE
		
		/*Insert for LOAN eNDS:----*/
		
        /*Insert for cutomer:----*/
		
		budInsSqlCust=new StringBuilder();
		 
		budInsSqlCust.append("INSERT INTO coll_customer_dtl_temp(DEAL_ID,CUSTOMER_ID,CUSTOMER_NAME,CUSTOMER_ROLE_TYPE,CUSTOMER_DOB,CUSTOMER_CATEGORY,");
		budInsSqlCust.append("ADDRESS_TYPE,ADDRESS_LINE1,ADDRESS_LINE2,ADDRESS_LINE3,DISTRICT_DESC,STATE_DESC,");	
		budInsSqlCust.append("PRIMARY_PHONE,CUSTOMER_EMAIL,ALTERNATE_PHONE,FAX,PINCODE,BATCH_ID,");
		budInsSqlCust.append("MAKER_ID,MAKER_DATE)");
		
		budInsSqlCust.append(" values ( ");
		budInsSqlCust.append(" ?," ); //DEAL_ID
		budInsSqlCust.append(" ?," ); //CUSTOMER_ID
		budInsSqlCust.append(" ?," ); //CUSTOMER_NAME
		budInsSqlCust.append(" ?," ); //CUSTOMER_ROLE_TYPE
		budInsSqlCust.append(" ?," );//CUSTOMER_DOB
		budInsSqlCust.append(" ?," ); //CUSTOMER_CATEGORY
		budInsSqlCust.append(" ?," );//ADDRESS_TYPE
		budInsSqlCust.append(" ?," ); //ADDRESS_LINE1
		budInsSqlCust.append(" ?," ); //ADDRESS_LINE2
		budInsSqlCust.append(" ?," ); //ADDRESS_LINE3
		//	budInsSqlCust.append(" ?," ); //DISTRICT_ID	 				
		budInsSqlCust.append(" ?," );// DISTRICT_DESC
		//	budInsSqlCust.append(" ?," ); //STATE_ID
		budInsSqlCust.append(" ?," );//STATE_DESC
		//budInsSqlCust.append(" ?," ); //COUNTRY_ID
		//budInsSqlCust.append(" ?," );//COUNTRY_DESC
		budInsSqlCust.append(" ?," );//PRIMARY_PHONE
		budInsSqlCust.append(" ?," ); //CUSTOMER_EMAIL
		budInsSqlCust.append(" ?," );//ALTERNATE_PHONE
		budInsSqlCust.append(" ?," );//fax
		budInsSqlCust.append(" ?," );//PINCODE
		budInsSqlCust.append(" ?, " ); //BATCH_ID
		budInsSqlCust.append(" ?, " ); //MAKER_ID
		budInsSqlCust.append("DATE_ADD(STR_TO_DATE(?,'"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) )" ); //MAKER_DATE
        /*Insert for asset:----*/
		budInsSqlAsset=new StringBuilder();

		budInsSqlAsset.append("INSERT INTO coll_asset_collateral_temp(LOAN_NO,ASSET_ID,ASSET_TYPE,ASSET_COLLATERAL_DESC,ASSET_COLLATERAL_CLASS,ASSET_COLLATERAL_VALUE,");
		budInsSqlAsset.append("	VEHICLE_REGISTRATION_NO,VEHICLE_ENGINE_NO ,VEHICLE_CHASIS_NO,VEHILE_MAKE ,VEHICLE_MODEL ,VEHICLE_REGISTRATION_DATE ,");	
		budInsSqlAsset.append("BATCH_ID,MAKER_ID,MAKER_DATE)");
		
		budInsSqlAsset.append(" values ( ");
		budInsSqlAsset.append(" ?," ); //LOAN_NO
		budInsSqlAsset.append(" ?," ); //ASSET_ID
		budInsSqlAsset.append(" ?," ); //ASSET_TYPE
		budInsSqlAsset.append(" ?," ); //ASSET_COLLATERAL_DESC
		budInsSqlAsset.append(" ?," );//ASSET_COLLATERAL_CLASS
		budInsSqlAsset.append(" ?," ); //ASSET_COLLATERAL_VALUE
		budInsSqlAsset.append(" ?," );//VEHICLE_REGISTRATION_NO
		budInsSqlAsset.append(" ?," ); //VEHICLE_ENGINE_NO
		budInsSqlAsset.append(" ?," ); //VEHICLE_CHASIS_NO
		budInsSqlAsset.append(" ?," ); //VEHILE_MAKE
		budInsSqlAsset.append(" ?," ); //VEHICLE_MODEL	 				
		budInsSqlAsset.append(" ?," ); //VEHICLE_REGISTRATION_DATE	
		budInsSqlAsset.append(" ?, " ); //BATCH_ID
		budInsSqlAsset.append(" ?, " ); //MAKER_ID
		budInsSqlAsset.append("DATE_ADD(STR_TO_DATE(?,'"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) )" ); //MAKER_DATE
		
		budInsSqlReference=new StringBuilder();
		budInsSqlReference.append("INSERT INTO coll_reference_m_temp(CUSTOMER_ID,FIRST_NAME,MIDDLE_NAME,LAST_NAME,RELATIONSHIP,MOBILE_NO,LANDLINE_NO,KNOWING_SINCE,");
		budInsSqlReference.append("BATCH_ID,MAKER_ID,MAKER_DATE)");
		
		budInsSqlReference.append(" values ( ");
		budInsSqlReference.append(" ?," ); //CUSTOMER_ID
		budInsSqlReference.append(" ?," ); //FIRST_NAME
		budInsSqlReference.append(" ?," ); //MIDDLE_NAME
		budInsSqlReference.append(" ?," ); //LAST_NAME

		budInsSqlReference.append(" ?," ); //RELATIONSHIP
		budInsSqlReference.append(" ?," );//MOBILE_NO
		budInsSqlReference.append(" ?," ); //LANDLINE_NO
		budInsSqlReference.append(" ?," ); //KNOWING_SINCE
	
		budInsSqlReference.append(" ?," ); //BATCH_ID
	    budInsSqlReference.append(" ?, " ); //MAKER_ID
		budInsSqlReference.append("DATE_ADD(STR_TO_DATE(?,'"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) )" ); //MAKER_DATE
		StringBuffer dataType=null;
		
		logger.info("Before for loop... insert +row-1+:-"+row);
		for(int i=1;i<=row-1;i++)
		{
			logger.info("In for loop... insert "+i);
		   	dataType=new StringBuffer();
			dataType.append(CommonFunction.checkNull(rowVal[i][0]));
			logger.info("In for loop... insert dataType:--"+dataType);
			
	if((dataType.toString()).equalsIgnoreCase("LOAN"))
	{
          insertPrepStmtObject = new PrepStmtObject();
          
          if(CommonFunction.checkNull(rowVal[i][1]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][1]).trim());
			
			if(CommonFunction.checkNull(rowVal[i][2]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][2]).trim());
			
			if(CommonFunction.checkNull(rowVal[i][3]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][3]).trim());
			
			if(CommonFunction.checkNull(rowVal[i][4]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][4]).trim());
			
			if(CommonFunction.checkNull(rowVal[i][5]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][5]).trim());
			
			if(CommonFunction.checkNull(rowVal[i][6]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][6]).trim());
			
			if(CommonFunction.checkNull(rowVal[i][7]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][7]).trim());
			
			if(CommonFunction.checkNull(rowVal[i][8]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][8]).trim());
			
			if(CommonFunction.checkNull(rowVal[i][9]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][9]).trim());
			
			if(CommonFunction.checkNull(rowVal[i][10]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][10]).trim());
			
			if(CommonFunction.checkNull(rowVal[i][11]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][11]).trim());
			
			if(CommonFunction.checkNull(rowVal[i][12]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][12]).trim());
			
			if(CommonFunction.checkNull(rowVal[i][13]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][13]).trim());
			
			if(CommonFunction.checkNull(rowVal[i][14]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][14]).trim());
			
			if(CommonFunction.checkNull(rowVal[i][15]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][15]).trim());
			
			if(CommonFunction.checkNull(rowVal[i][16]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][16]).trim());
			
			if(CommonFunction.checkNull(rowVal[i][17]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][17]).trim());	
			
			if(CommonFunction.checkNull(rowVal[i][18]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][18]).trim());
			
			if(CommonFunction.checkNull(rowVal[i][19]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][19]).trim());
			
			if(CommonFunction.checkNull(rowVal[i][20]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][20]).trim());
			
			if(CommonFunction.checkNull(rowVal[i][21]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][21]).trim());	
			
			if(CommonFunction.checkNull(rowVal[i][22]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][22]).trim());
			
			if(CommonFunction.checkNull(rowVal[i][23]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][23]).trim());
			
			if(CommonFunction.checkNull(rowVal[i][24]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][24]).trim());	
			
			if(CommonFunction.checkNull(rowVal[i][25]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][25]).trim());
			
			if(CommonFunction.checkNull(rowVal[i][26]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][26]).trim());
			
			if(CommonFunction.checkNull(rowVal[i][27]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][27]).trim());
			
			if(CommonFunction.checkNull(rowVal[i][28]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][28]).trim());
			
			if(CommonFunction.checkNull(rowVal[i][29]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][29]).trim());
			 
			if(CommonFunction.checkNull(rowVal[i][30]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][30]).trim());
			
			if(CommonFunction.checkNull(rowVal[i][31]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][31]).trim());
			
			if(CommonFunction.checkNull(rowVal[i][32]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][32]).trim());
			
			if(CommonFunction.checkNull(rowVal[i][33]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][33]).trim());
			
			if(CommonFunction.checkNull(rowVal[i][34]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][34]).trim());
			 
			if(CommonFunction.checkNull(rowVal[i][35]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][35]).trim());
			
			if(CommonFunction.checkNull(rowVal[i][36]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][36]).trim());
		 
			if(CommonFunction.checkNull(rowVal[i][37]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][37]).trim());	
		
			if(CommonFunction.checkNull(rowVal[i][38]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][38]).trim());
			
			if(CommonFunction.checkNull(rowVal[i][39]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][39]).trim());
			 
			if(CommonFunction.checkNull(rowVal[i][40]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][40]).trim());
			

			if(CommonFunction.checkNull(rowVal[i][41]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][41]).trim());
			//Batch id
			insertPrepStmtObject.addString(CommonFunction.checkNull(batchId));
			
			if(CommonFunction.checkNull(collUploadDataVo.getMakerId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(collUploadDataVo.getMakerId()));
			
			
			if(CommonFunction.checkNull(collUploadDataVo.getMakerDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(collUploadDataVo.getMakerDate()));
			
			insertPrepStmtObject.setSql(bufInsSqlLoan.toString());
	  
 	  }else if((dataType.toString()).equalsIgnoreCase("CUST")){
          insertPrepStmtObject = new PrepStmtObject();
          
          if(CommonFunction.checkNull(rowVal[i][1]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][1]).trim());
			
			if(CommonFunction.checkNull(rowVal[i][2]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][2]).trim());
			
			if(CommonFunction.checkNull(rowVal[i][3]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][3]).trim());
			
			if(CommonFunction.checkNull(rowVal[i][4]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][4]).trim());
			
			if(CommonFunction.checkNull(rowVal[i][5]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][5]).trim());
			
			if(CommonFunction.checkNull(rowVal[i][6]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][6]).trim());
			
			if(CommonFunction.checkNull(rowVal[i][7]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][7]).trim());
			
			if(CommonFunction.checkNull(rowVal[i][8]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][8]).trim());
			
			if(CommonFunction.checkNull(rowVal[i][9]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][9]).trim());
			
			if(CommonFunction.checkNull(rowVal[i][10]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][10]).trim());
			
			if(CommonFunction.checkNull(rowVal[i][11]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][11]).trim());
			
			if(CommonFunction.checkNull(rowVal[i][12]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][12]).trim());
			
			if(CommonFunction.checkNull(rowVal[i][13]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][13]).trim());
			
			if(CommonFunction.checkNull(rowVal[i][14]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][14]).trim());
			
			if(CommonFunction.checkNull(rowVal[i][15]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][15]).trim());
			
			if(CommonFunction.checkNull(rowVal[i][16]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][16]).trim());
			
			if(CommonFunction.checkNull(rowVal[i][17]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][17]).trim());	
			
		
				
			//Batch id
			insertPrepStmtObject.addString(CommonFunction.checkNull(batchId));
			
			if(CommonFunction.checkNull(collUploadDataVo.getMakerId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(collUploadDataVo.getMakerId()));
			
			if(CommonFunction.checkNull(collUploadDataVo.getMakerDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(collUploadDataVo.getMakerDate()));
			
			insertPrepStmtObject.setSql(budInsSqlCust.toString());
			
 	    }else if((dataType.toString()).equalsIgnoreCase("ASSET")){

 	          insertPrepStmtObject = new PrepStmtObject();
 	          logger.info("In asset i is :---"+i);
 	         logger.info("In asset i is rowVal[i][1] :---"+rowVal[i][1]);
 	          if(CommonFunction.checkNull(rowVal[i][1]).equalsIgnoreCase(""))
 					insertPrepStmtObject.addNull();
 				else
 					insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][1]).trim());
 				
 				if(CommonFunction.checkNull(rowVal[i][2]).equalsIgnoreCase(""))
 					insertPrepStmtObject.addNull();
 				else
 					insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][2]).trim());
 				
 				if(CommonFunction.checkNull(rowVal[i][3]).equalsIgnoreCase(""))
 					insertPrepStmtObject.addNull();
 				else
 					insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][3]).trim());
 				
 				if(CommonFunction.checkNull(rowVal[i][4]).equalsIgnoreCase(""))
 					insertPrepStmtObject.addNull();
 				else
 					insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][4]).trim());
 				
 				if(CommonFunction.checkNull(rowVal[i][5]).equalsIgnoreCase(""))
 					insertPrepStmtObject.addNull();
 				else
 					insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][5]).trim());
 				
 				if(CommonFunction.checkNull(rowVal[i][6]).equalsIgnoreCase(""))
 					insertPrepStmtObject.addNull();
 				else
 					insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][6]).trim());
 				
 				if(CommonFunction.checkNull(rowVal[i][7]).equalsIgnoreCase(""))
 					insertPrepStmtObject.addNull();
 				else
 					insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][7]).trim());
 				
 				if(CommonFunction.checkNull(rowVal[i][8]).equalsIgnoreCase(""))
 					insertPrepStmtObject.addNull();
 				else
 					insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][8]).trim());
 				
 				if(CommonFunction.checkNull(rowVal[i][9]).equalsIgnoreCase(""))
 					insertPrepStmtObject.addNull();
 				else
 					insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][9]).trim());
 				
 				if(CommonFunction.checkNull(rowVal[i][10]).equalsIgnoreCase(""))
 					insertPrepStmtObject.addNull();
 				else
 					insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][10]).trim());
 				
 				if(CommonFunction.checkNull(rowVal[i][11]).equalsIgnoreCase(""))
 					insertPrepStmtObject.addNull();
 				else
 					insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][11]).trim());
 				
 				if(CommonFunction.checkNull(rowVal[i][12]).equalsIgnoreCase(""))
 					insertPrepStmtObject.addNull();
 				else
 					insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][12]).trim());
 				
 				
 					
 				//Batch id
 				insertPrepStmtObject.addString(CommonFunction.checkNull(batchId));
 				
 				if(CommonFunction.checkNull(collUploadDataVo.getMakerId()).equalsIgnoreCase(""))
 					insertPrepStmtObject.addNull();
 				else
 					insertPrepStmtObject.addString(CommonFunction.checkNull(collUploadDataVo.getMakerId()));
 				
 				if(CommonFunction.checkNull(collUploadDataVo.getMakerDate()).equalsIgnoreCase(""))
 					insertPrepStmtObject.addNull();
 				else
 					insertPrepStmtObject.addString(CommonFunction.checkNull(collUploadDataVo.getMakerDate()));
 				
 				insertPrepStmtObject.setSql(budInsSqlAsset.toString());
 				
 	 	    
 	    
 	    }
 	   else if((dataType.toString()).equalsIgnoreCase("REFERENCE")){
        insertPrepStmtObject = new PrepStmtObject();
     
     if(CommonFunction.checkNull(rowVal[i][1]).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][1]).trim());
		
		if(CommonFunction.checkNull(rowVal[i][2]).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][2]).trim());
		
		if(CommonFunction.checkNull(rowVal[i][3]).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][3]).trim());
		
		if(CommonFunction.checkNull(rowVal[i][4]).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][4]).trim());
		
		if(CommonFunction.checkNull(rowVal[i][5]).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][5]).trim());
		
		if(CommonFunction.checkNull(rowVal[i][6]).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][6]).trim());
		
		if(CommonFunction.checkNull(rowVal[i][7]).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][7]).trim());
		
		if(CommonFunction.checkNull(rowVal[i][8]).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(CommonFunction.checkNull(rowVal[i][8]).trim());
		
	
			//Batch id
		insertPrepStmtObject.addString(CommonFunction.checkNull(batchId));
		
		if(CommonFunction.checkNull(collUploadDataVo.getMakerId()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(CommonFunction.checkNull(collUploadDataVo.getMakerId()));
		
		if(CommonFunction.checkNull(collUploadDataVo.getMakerDate()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(CommonFunction.checkNull(collUploadDataVo.getMakerDate()));
		
		insertPrepStmtObject.setSql(budInsSqlReference.toString());
		
    

 	   }
  		
		logger.info("insert Query on Coll Upload Data Temp:- "+insertPrepStmtObject.printQuery());
		//logger.info("alFinalQry:-"+alFinalQry);
		//logger.info("insertPrepStmtObject:-"+insertPrepStmtObject);
		alFinalQry.add(insertPrepStmtObject);  
		insertPrepStmtObject=null;			
		dataType=null;

       	 				
		
	//		}//End of If
		
    } // End of loop
		logger.info("Exit from Loop:00000000000");
	  bufInsSqlLoan=null;
	  budInsSqlCust=null;
		logger.info("before Insert execure");
   	  status=ConnectionDAO.sqlInsUpdDeletePrepStmt(alFinalQry); 
   	  
   	logger.info("After Insert execure");
   	  
   	   logger.info("In Coll Upload Data ...................status:-"+status);   
   	   if(status){
   		
   		logger.info(" Before Procedure Call (insert_into_cr_loan_dtl_from_coll_temp  )"); 
   		logger.info("batchId:----"+batchId);
   		
   		in =  new ArrayList();
   		out =  new ArrayList();
   		
   		 in.add(batchId);
   		 out.add(s1);
		 out.add(s2);
		 outMessages=(ArrayList) ConnectionDAO.callSP("insert_into_cr_loan_dtl_from_coll_temp",in,out);
		 s1=CommonFunction.checkNull(outMessages.get(0));
		 s2=CommonFunction.checkNull(outMessages.get(1));
   	   }
		if(s1.equalsIgnoreCase("S"))
			{
				logger.info("After proc call..commit message."+s2);
             //	 con.commit();
             // con.close();
			}else{
				logger.info("After proc call..rollback.error message."+s2);
			// con.rollback();
			//con.close();
			}
      logger.info(" After Procedure Status "+status);
     
       } /*
     }      
  
        } /* End Inner Try */
      catch(IOException ex) {
   	   logger.error("IOException In Uploading File ==>>"+ ex.getMessage());
        }
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
    
   objFile1.delete();
   request.setAttribute("msg", msg);
   alFinalQry.clear();
   alFinalQry = null;
 }
 return status;
 
  

	}

}
