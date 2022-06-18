package com.cm.daoImplMYSQL;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.upload.FormFile;

import com.cm.dao.LosUploadDAO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.ConnectionReportDumpsDAO;
import com.connect.PrepStmtObject;
import com.login.roleManager.UserObject;

public class LosUploadDAOImpl implements LosUploadDAO {
	private static final Logger logger=Logger.getLogger(LosUploadDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	String dateFormatForLosUpload=resource.getString("lbl.dateFormatForLosUpload");

	public String getUploadLOSData(String userId){
		logger.info("In getUploadManualData method of  ManualDeviationUploadDAOImpl::::::::::::::::");

		Connection conn=null;
		Statement stmt =null;
		StringBuffer data= new StringBuffer();
		String fileName="";
		try
		{
			if(userId!=null)
			{
				String query= "SELECT  file_name FROM cr_IndiLends_Loan_upload_summary WHERE MAKER_ID='"+userId +"' ";
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
			String query="SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='LOS_UPLOAD_PATH'";
			logger.info("In LosUpload(): query:==>>"+query);

			rpt=ConnectionDAO.singleReturn(query);
			query=null;

			logger.info("The name you have entered is a file : "  +file);
			File directory = new File(rpt+"/");
			logger.info("losUpload:directory:==>"+directory);
			boolean isDirectory = directory.isDirectory();
			logger.info("losUpload:Isdirectory:==>"+isDirectory);
			fileName    = file.getFileName();

			filePath = directory.getAbsolutePath();
			/* Save file on the server */
			if(!fileName.equals("")){  
				logger.info("Server path: filePath:==>>" +filePath);
				//Create file
				File fileToCreate = new File(filePath, fileName);
				int fileSize = file.getFileSize(); //10485760 bytes = 10 MB
				logger.info("docUpload :Size of file==>> "+fileSize);
				if(fileSize<10485760)
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
					status=false;
				}
			}
			else
			{
				message="S"; 	
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
		String query="SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='LOS_UPLOAD_PATH'";				
		String rpt=ConnectionDAO.singleReturn(query);		
		File directory = new File(rpt+"/"+fileName);
		query=null;
		try
		{ 
			if(directory.exists())
			{
				if(directory.length()>0){
					Workbook workbook = Workbook.getWorkbook(directory);
					Sheet sheet;
					sheet = workbook.getSheet(0);
					rowTotalNum=sheet.getRows();
					rowTotalNum-=1;
					logger.info("Total Lines............. ==>>"+rowTotalNum);
				}
				else{
					rowTotalNum=-1;
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
	
	//Virender Code for Validation
		@Override
		public String validateDataLOSUpload(HttpServletRequest request,	HttpServletResponse response, String file_name, String user_id,String bus_date) {

			logger.info("In validateDataLOSUpload() method ");
			HttpSession   session= null;
			ArrayList dataList=null;
			String msg="S";    
			boolean singleObject;
			File objFile1=null;
			boolean status=false;
			String query="";
			String strPath=null;
			Workbook workbook =null;
			Map workSheets=new HashMap();
			ArrayList arr=null;
			ArrayList subList= null; 
			int p=0,i=0,HeaderCount=0;
			String errorPos="";
			ArrayList size_Error=new ArrayList();
			ArrayList type_Error=new ArrayList();
			ArrayList blank_Error=new ArrayList();
			ArrayList data_Error=new ArrayList();
			ArrayList fill_Error=new ArrayList();
			ArrayList mainAppForm=null;
			ArrayList loanRefForm=null;
			ArrayList qltyChkForm=null;
			ArrayList appFormNo=null;
			ArrayList panNo=null;
			ArrayList aadharNo=null;

			boolean ValidateFlag=false;
			StringBuffer validateMsg=new StringBuffer();
			String customer_type=null;
			String newMsg="";
			StringBuffer sheetInfoMsg=null;

			try 
			{      
				if(!file_name.equals(""))
				{
					query="SELECT  PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='LOS_UPLOAD_PATH'";
					strPath=ConnectionDAO.singleReturn(query);

					strPath=strPath + "/" + file_name;
					objFile1=new File(strPath);
					query=null;    	  
					try 
					{  
						dataList=new ArrayList();
						workbook = Workbook.getWorkbook(new File(strPath));
						String sheetName[]=workbook.getSheetNames();
						Sheet sheet;
						Cell xlsCell;
						Cell[] cell;

						for(p=0; p<sheetName.length; p++)//for every sheet
						{
							sheet = workbook.getSheet(p);
							dataList= new ArrayList();
							HeaderCount=sheet.getRow(0).length;

							for(i=1;i<sheet.getRows();i++)//for every row
							{
								cell = sheet.getRow(i);
								arr = new ArrayList();
								for(int j=0; j<HeaderCount; j++)//for every field(column)
								{                 	
									xlsCell = sheet.getCell(j,i);
									if(!CommonFunction.checkNull(sheet.getCell(j,i).getContents().toString()).equalsIgnoreCase(""))
									{
										arr.add(xlsCell.getContents().toString());
									} 
									else
									{
										arr.add("");
									}
								} 
								dataList.add(arr);	 
							}
							workSheets.put(String.valueOf(p), dataList);
						}
					}
					catch(Exception e) 
					{e.printStackTrace();}

					try {
						session = request.getSession();
						String functionId=(String)session.getAttribute("functionId");


						if(CommonFunction.checkNull(functionId).equalsIgnoreCase("10000827")){
							//loop over sheets
							for(p=0;p<workSheets.size();p++){

								dataList=(ArrayList)workSheets.get(String.valueOf(p));
								StringBuffer bufInsSql = new StringBuffer();
								ArrayList vst=new ArrayList();
								mainAppForm=new ArrayList();
								String appFormQry = "select DEAL_APPLICATION_FORM_NO from cr_Deal_dtl where DEAL_APPLICATION_FORM_NO is not null and DEAL_APPLICATION_FORM_NO<>''";
								mainAppForm = ConnectionReportDumpsDAO.sqlSelect(appFormQry);


								for(int r=0;r<mainAppForm.size();r++){
									vst.add(mainAppForm.get(r).toString().replace("[","").replace("]",""));
								}

								//Lead Entry detail( sheet-0 )
								if(p==0 && dataList.size()>0)
								{ 
									appFormNo=new ArrayList();
									for(i=0;i<dataList.size();i++)//rows loop
									{	     	  
										subList=(ArrayList)dataList.get(i);//for fetching all cols fields
										if(subList.size()>0)
										{

											//Application_Form_No - varchar(20)
											if(!CommonFunction.checkNull(subList.get(0)).equalsIgnoreCase("")){
												if(appFormNo.contains(subList.get(0))||vst.contains(subList.get(0))){
													errorPos="("+(i+1)+",1)";
													validateMsg.append("\nApplication_Form_No Already Exists at '"+errorPos+"'");
													data_Error.add(errorPos);
													ValidateFlag=true;

												}else{
													if(CommonFunction.checkNull(subList.get(0)).length() > 20){
														errorPos="("+(i+1)+",1)";
														validateMsg.append("\nApplication_Form_No size exceeds max length 20 '"+errorPos+"'");
														size_Error.add(errorPos);
														ValidateFlag=true;
													}
													else{
														appFormNo.add(subList.get(0));
													}
												}
											}
											else{
												errorPos="("+(i+1)+",1)";
												validateMsg.append("\nApplication_Form_No Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}

											//Relationship Manager - varchar(10)
											if(!CommonFunction.checkNull(subList.get(1)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(1)).length() > 10){
													errorPos="("+(i+1)+",2)";
													validateMsg.append("\nRelationship Manager size exceeds max length 10 '"+errorPos+"'");
													size_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",2)";
												validateMsg.append("\nRelationship Manager Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}

											//Source Type Desc- varchar(10)
											if(!CommonFunction.checkNull(subList.get(2)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(2)).length() > 10){
													errorPos="("+(i+1)+",3)";
													validateMsg.append("\nSource Description size exceeds max length 10 '"+errorPos+"'");
													size_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",3)";
												validateMsg.append("\nSource Type Desc Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}

											//Source Description- varchar(50)
											if(!CommonFunction.checkNull(subList.get(3)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(3)).length() > 50){
													errorPos="("+(i+1)+",4)";
													validateMsg.append("\nSource Description size exceeds max length 50 '"+errorPos+"'");
													size_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",4)";
												validateMsg.append("\nSource Description Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}

											//Deal Category- varchar(10)
											if(!CommonFunction.checkNull(subList.get(4)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(4)).equalsIgnoreCase("MAN")||CommonFunction.checkNull(subList.get(4)).equalsIgnoreCase("MANUFACTURING")||
														CommonFunction.checkNull(subList.get(4)).equalsIgnoreCase("NA")||CommonFunction.checkNull(subList.get(4)).equalsIgnoreCase("NOT_APPLICABLE")||
														CommonFunction.checkNull(subList.get(4)).equalsIgnoreCase("SER")||CommonFunction.checkNull(subList.get(4)).equalsIgnoreCase("SERVICES")||
														CommonFunction.checkNull(subList.get(4)).equalsIgnoreCase("TRA")||CommonFunction.checkNull(subList.get(4)).equalsIgnoreCase("	")){

												}
												else{
													errorPos="("+(i+1)+",5)";
													validateMsg.append("\nDeal_Category can be either \"MAN/MANUFACTURING\" or \"NA/NOT APPLICABLE\" or \"SER/SER\" or \"TRA/TRADING\" at '"+errorPos+"'");
													data_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",5)";
												validateMsg.append("\nDeal_Category Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}

											//Branch_ID,int(10)
											if(!CommonFunction.checkNull(subList.get(5)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(5)).length() > 10){
													errorPos="("+(i+1)+",6)";
													validateMsg.append("\nBranch_ID size exceeds max length 10 '"+errorPos+"'");
													size_Error.add(errorPos);
													ValidateFlag=true;
												}
												if(!StringUtils.isNumeric(CommonFunction.checkNull(subList.get(5)))){
													errorPos="("+(i+1)+",6)";
													validateMsg.append("\nBranch_ID should be a Number '"+errorPos+"'");
													type_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",6)";
												validateMsg.append("\nBranch_ID Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}

										}
									}  

									appFormNo=null;

									//checking if error occured
									if(ValidateFlag){
										msg="F";
										sheetInfoMsg=new StringBuffer();
										sheetInfoMsg.append("\nFailed at 'Lead Entry Detail' Sheet");
										if(blank_Error.size()>0)
											sheetInfoMsg.append("\nFields Should not Be blank at '"+blank_Error+"' ");
										if(size_Error.size()>0)
											sheetInfoMsg.append("\nFields Exceeding max size at '"+size_Error+"' ");
										if(type_Error.size()>0)
											sheetInfoMsg.append("\nFields of Incorrect DataType at '"+type_Error+"' ");
										if(data_Error.size()>0)
											sheetInfoMsg.append("\nFields Having Invalid Data at '"+data_Error+"' ");
										sheetInfoMsg.append("\nFor more information kindly check Error Log");

										/*request.setAttribute("ValMsg",validateMsg);
								session.setAttribute("InfoMsg",sheetInfoMsg);*/

										break;
									}

								}

								//Cust Details
								if(p==1 && dataList.size()>0)
								{ 
									panNo=new ArrayList();
									aadharNo=new ArrayList();

									for(i=0;i<dataList.size();i++)//rows loop
									{	     	  
										subList=(ArrayList)dataList.get(i);//for fetching all cols fields
										if(subList.size()>0)
										{
											//Application_Form_No - varchar(20)					
											if(!CommonFunction.checkNull(subList.get(0)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(0)).length() > 20){
													errorPos="("+(i+1)+",1)";
													validateMsg.append("\nApplication_Form_No size exceeds max length 20 '"+errorPos+"'");
													size_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",1)";
												validateMsg.append("\nApplication_Form_No Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}

											//Customer_No,int(10)
											if(!CommonFunction.checkNull(subList.get(1)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(1)).length() > 10){
													errorPos="("+(i+1)+",2)";
													validateMsg.append("\nCustomer_No size exceeds max length 10 '"+errorPos+"'");
													size_Error.add(errorPos);
													ValidateFlag=true;
												}
												if(!StringUtils.isNumeric(CommonFunction.checkNull(subList.get(1)))){
													errorPos="("+(i+1)+",2)";
													validateMsg.append("\nCustomer_No should be a Number '"+errorPos+"'");
													type_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",2)";
												validateMsg.append("\nCustomer_No Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}

											//Customer_type,varchar(10)
											if(!CommonFunction.checkNull(subList.get(2)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(2)).equalsIgnoreCase("PRAPPL")||
														CommonFunction.checkNull(subList.get(2)).equalsIgnoreCase("COAPPL")||
														CommonFunction.checkNull(subList.get(2)).equalsIgnoreCase("GUAR")){

												}
												else{
													errorPos="("+(i+1)+",3)";
													validateMsg.append("\nCustomer_Type can be either \"PRAPPL(APPLICANT)\" or \"COAPPL(COAPPLICANT)\" or \"GUAR(GUARANTOR)\" at '"+errorPos+"'");
													data_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",3)";
												validateMsg.append("\nCustomer_Type Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}

											//CUSTOMER_CATEGORY,varchar(10)
											if(!CommonFunction.checkNull(subList.get(3)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(3)).equalsIgnoreCase("I")||CommonFunction.checkNull(subList.get(3)).equalsIgnoreCase("INDIVIDUAL")){
													customer_type="I";
												}
												else if(CommonFunction.checkNull(subList.get(3)).equalsIgnoreCase("C")||CommonFunction.checkNull(subList.get(3)).equalsIgnoreCase("CORPORATE")){
													customer_type="C";
												}
												else{
													errorPos="("+(i+1)+",4)";
													validateMsg.append("\nCustomer_Category can be either \"INDIVIDUAL/I\" or \"CORPORATE/C\" '"+errorPos+"'");
													data_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",4)";
												validateMsg.append("\nCustomer_Category Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}

											//CUSTOMER_FNAME,varchar(155)
											if(!CommonFunction.checkNull(subList.get(4)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(4)).length() >155){
													errorPos="("+(i+1)+",5)";
													validateMsg.append("\nCUSTOMER_FNAME size exceeds max length 155 '"+errorPos+"'");
													size_Error.add(errorPos);
													ValidateFlag=true;
												}

											}
											else{
												errorPos="("+(i+1)+",5)";
												validateMsg.append("\nCUSTOMER_FNAME Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}


											//Customer category (7)
											if(!CommonFunction.checkNull(subList.get(7)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(7)).equalsIgnoreCase("CORPORATE")||
														CommonFunction.checkNull(subList.get(7)).equalsIgnoreCase("SAL")||
														CommonFunction.checkNull(subList.get(7)).equalsIgnoreCase("SE")||
														CommonFunction.checkNull(subList.get(7)).equalsIgnoreCase("SEP")){

												}
												else{
													errorPos="("+(i+1)+",8)";
													validateMsg.append("\nCustomer category can be either \"CORPORATE\" or \"SAL(SALARIED)\" or \"SE(SELF EMPLOYED)\" or \"SEP(SELF EMPLOYED PROFESSIONAL)\" at '"+errorPos+"'");
													data_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",8)";
												validateMsg.append("\nCustomer category Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}

											if(customer_type.equalsIgnoreCase("I")){
												//CUSTOMER_LNAME,varchar(50)
												if(!CommonFunction.checkNull(subList.get(5)).equalsIgnoreCase("")){
													if(CommonFunction.checkNull(subList.get(5)).length() > 50){
														errorPos="("+(i+1)+",6)";
														validateMsg.append("\nCUSTOMER_LNAME size exceeds max length 50 '"+errorPos+"'");
														size_Error.add(errorPos);
														ValidateFlag=true;
													}
												}
												else{
													errorPos="("+(i+1)+",6)";
													validateMsg.append("\nCUSTOMER_LNAME Can not be Left Blank '"+errorPos+"'");
													blank_Error.add(errorPos);
													ValidateFlag=true;
												}

												//GENDER,varchar(10)
												if(!CommonFunction.checkNull(subList.get(6)).equalsIgnoreCase("")){
													if(CommonFunction.checkNull(subList.get(6)).equalsIgnoreCase("MALE")||CommonFunction.checkNull(subList.get(6)).equalsIgnoreCase("M")||
															CommonFunction.checkNull(subList.get(6)).equalsIgnoreCase("FEMALE")||CommonFunction.checkNull(subList.get(6)).equalsIgnoreCase("F")||
															CommonFunction.checkNull(subList.get(6)).equalsIgnoreCase("OTHER")||CommonFunction.checkNull(subList.get(6)).equalsIgnoreCase("O")){

													}
													else{
														errorPos="("+(i+1)+",7)";
														validateMsg.append("\nGENDER can be either \"MALE/M\" or \"FEMALE/F\" or \"OTHER/O\" at '"+errorPos+"'");
														data_Error.add(errorPos);
														ValidateFlag=true;
													}
												}
												else{
													errorPos="("+(i+1)+",7)";
													validateMsg.append("\nGENDER Can not be Left Blank '"+errorPos+"'");
													blank_Error.add(errorPos);
													ValidateFlag=true;
												}



												//CUSTOMER_CONSTITUTION,varchar(10)
												if(!CommonFunction.checkNull(subList.get(8)).equalsIgnoreCase("")){
													if(CommonFunction.checkNull(subList.get(8)).equalsIgnoreCase("INDIVIDUAL")||
															CommonFunction.checkNull(subList.get(8)).equalsIgnoreCase("SALARIED")||CommonFunction.checkNull(subList.get(8)).equalsIgnoreCase("NINDV")){

													}
													else{
														errorPos="("+(i+1)+",9)";
														validateMsg.append("\nCUSTOMER_CONSTITUTION can be either \"INDIVIDUAL\" or \"SALARIED\"  or \"NINDV(NON FIANCIAL INDIVIDUAL)\" at '"+errorPos+"'");
														data_Error.add(errorPos);
														ValidateFlag=true;
													}
												}
												else{
													errorPos="("+(i+1)+",9)";
													validateMsg.append("\nCUSTOMER_CONSTITUTION Desc Can not be Left Blank '"+errorPos+"'");
													blank_Error.add(errorPos);
													ValidateFlag=true;
												}

												//CUSTOMER_DOB,date
												if(!CommonFunction.checkNull(subList.get(9)).equalsIgnoreCase("")){
													if(isDateValid(subList.get(9).toString())){
														if(DateComparator(subList.get(9).toString(),bus_date)>=0){
															errorPos="("+(i+1)+",10)";
															validateMsg.append("\nCUSTOMER_DOB can not be greater than Buisness Date At '"+errorPos+"'");
															data_Error.add(errorPos);
															ValidateFlag=true;
														}
													}
													else{
														errorPos="("+(i+1)+",10)";
														validateMsg.append("\nInvalid DateFormat At '"+errorPos+"' Date Should be in (DD-MM-YYYY) Format");
														data_Error.add(errorPos);
														ValidateFlag=true;
													}
												}
												else{
													errorPos="("+(i+1)+",10)";
													validateMsg.append("\nCUSTOMER_DOB Can not be Left Blank '"+errorPos+"'");
													blank_Error.add(errorPos);
													ValidateFlag=true;
												}

												//Caste Category,varchar(10)
												if(!CommonFunction.checkNull(subList.get(10)).equalsIgnoreCase("")){
													if(CommonFunction.checkNull(subList.get(10)).equalsIgnoreCase("OBC")||CommonFunction.checkNull(subList.get(10)).equalsIgnoreCase("GENERAL")||
															CommonFunction.checkNull(subList.get(10)).equalsIgnoreCase("SC")||CommonFunction.checkNull(subList.get(10)).equalsIgnoreCase("ST")||
															CommonFunction.checkNull(subList.get(10)).equalsIgnoreCase("OTHER")){

													}
													else{
														errorPos="("+(i+1)+",11)";
														validateMsg.append("\nCustomer_Type can be either \"GENERAL\" or \"OBC\" or \"SC\" or \"ST\" or \"OTHER\"   '"+errorPos+"'");
														data_Error.add(errorPos);
														ValidateFlag=true;
													}
												}

												//FATHER_HUSBAND_NAME,varchar(50)
												if(!CommonFunction.checkNull(subList.get(11)).equalsIgnoreCase("")){
													if(CommonFunction.checkNull(subList.get(11)).length() > 50){
														errorPos="("+(i+1)+",12)";
														validateMsg.append("\nFATHER_HUSBAND_NAME size exceeds max length 50 '"+errorPos+"'");
														size_Error.add(errorPos);
														ValidateFlag=true;
													}
													if(!StringUtils.isAlphanumeric(CommonFunction.checkNull(subList.get(11)))){

													}
												}
												else{
													errorPos="("+(i+1)+",12)";
													validateMsg.append("\nFATHER_HUSBAND_NAME Can not be Left Blank '"+errorPos+"'");
													blank_Error.add(errorPos);
													ValidateFlag=true;
												}

												//MARITAL_STATUS,varchar(1)
												if(!CommonFunction.checkNull(subList.get(12)).equalsIgnoreCase("")){
													if(CommonFunction.checkNull(subList.get(12)).equalsIgnoreCase("M")||CommonFunction.checkNull(subList.get(12)).equalsIgnoreCase("Married")||
															CommonFunction.checkNull(subList.get(12)).equalsIgnoreCase("U")||CommonFunction.checkNull(subList.get(12)).equalsIgnoreCase("UnMarried")){

													}
													else{
														errorPos="("+(i+1)+",13)";
														validateMsg.append("\nMARITAL_STATUS can be either \"Married/M\" or \"UnMarried/U\" at '"+errorPos+"'");
														data_Error.add(errorPos);
														ValidateFlag=true;
													}
												}
												else{
													errorPos="("+(i+1)+",13)";
													validateMsg.append("\nMARITAL_STATUS Can not be Left Blank '"+errorPos+"'");
													blank_Error.add(errorPos);
													ValidateFlag=true;
												}


												//CUSTMER_PAN,varchar(25)
												if(!CommonFunction.checkNull(subList.get(14)).equalsIgnoreCase("")){
													if(panNo.contains(subList.get(14))){
														errorPos="("+(i+1)+",15)";
														validateMsg.append("\nCUSTMER_PAN Already Exists '"+errorPos+"'");
														data_Error.add(errorPos);
														ValidateFlag=true;

													}else{
														if(CommonFunction.checkNull(subList.get(14)).length() > 25){
															errorPos="("+(i+1)+",15)";
															validateMsg.append("CUSTMER_PAN size exceeds max length 25 '"+errorPos+"'");
															size_Error.add(errorPos);
															ValidateFlag=true;
														}
														else{
															panNo.add(subList.get(14));
														}
													}
												}
												else{
													errorPos="("+(i+1)+",15)";
													validateMsg.append("CUSTMER_PAN Can not be Left Blank '"+errorPos+"'");
													blank_Error.add(errorPos);
													ValidateFlag=true;
												}

												//Education_Details varchar(20)
												if(!CommonFunction.checkNull(subList.get(15)).equalsIgnoreCase("")){
													if(CommonFunction.checkNull(subList.get(15)).length() > 20){
														errorPos="("+(i+1)+",16)";
														validateMsg.append("\nEducation_Details size exceeds max length 20 '"+errorPos+"'");
														size_Error.add(errorPos);
														ValidateFlag=true;
													}
												}
												else{
													errorPos="("+(i+1)+",16)";
													validateMsg.append("\nEducation_Details Desc Can not be Left Blank '"+errorPos+"'");
													blank_Error.add(errorPos);
													ValidateFlag=true;
												}

												//put adharNo(16)-varchar(12)
												if(!CommonFunction.checkNull(subList.get(16)).equalsIgnoreCase("")){

													if(aadharNo.contains(subList.get(16))){
														errorPos="("+(i+1)+",17)";
														validateMsg.append("\nAadharNo Already Exists '"+errorPos+"'");
														data_Error.add(errorPos);
														ValidateFlag=true;

													}else{
														if(CommonFunction.checkNull(subList.get(16)).length() > 12){
															errorPos="("+(i+1)+",17)";
															validateMsg.append("\nAadharNo size exceeds max length 12 '"+errorPos+"'");
															size_Error.add(errorPos);
															ValidateFlag=true;
														}
														else{
															aadharNo.add(subList.get(16));
														}
													}

												}
												else{
													errorPos="("+(i+1)+",17)";
													validateMsg.append("\nAadharNo Can not be Left Blank '"+errorPos+"'");
													blank_Error.add(errorPos);
													ValidateFlag=true;
												}	


												//GROUP_NAME,varchar(50)
												if(!CommonFunction.checkNull(subList.get(17)).equalsIgnoreCase("")){
													errorPos="("+(i+1)+",18)";
													validateMsg.append("\nCan not Fill GROUP_NAME for Individual  at '"+errorPos+"'");
													fill_Error.add(errorPos);
													ValidateFlag=true;
												}


												//CUSTOMER_REGISTRATION_NO,varchar(25)
												if(!CommonFunction.checkNull(subList.get(18)).equalsIgnoreCase("")){
													errorPos="("+(i+1)+",19)";
													validateMsg.append("\nCan not Fill REGISTRATION_NO for Individual at '"+errorPos+"'");
													fill_Error.add(errorPos);
													ValidateFlag=true;
												}


												//CUSTOMER_BUSINESS_SEGMENT,varchar(10)
												if(!CommonFunction.checkNull(subList.get(19)).equalsIgnoreCase("")){
													errorPos="("+(i+1)+",20)";
													validateMsg.append("\nCan not Fill Business_segment for Individual  at '"+errorPos+"'");
													fill_Error.add(errorPos);
													ValidateFlag=true;
												}

												//Nature_of_business ,varchar(50)
												if(!CommonFunction.checkNull(subList.get(22)).equalsIgnoreCase("")){
													errorPos="("+(i+1)+",23)";
													validateMsg.append("\nCan not Fill Nature_of_business for Individual  at '"+errorPos+"'");
													fill_Error.add(errorPos);
													ValidateFlag=true;
												}

											}
											if(customer_type.equalsIgnoreCase("C")){

												//CUSTOMER_LNAME,varchar(50)
												if(!CommonFunction.checkNull(subList.get(5)).equalsIgnoreCase("")){
													errorPos="("+(i+1)+",6)";
													validateMsg.append("\nCan not Fill CUSTOMER_LNAME for Corporate at '"+errorPos+"'");
													fill_Error.add(errorPos);
													ValidateFlag=true;
												}

												//GENDER,varchar(10)
												if(!CommonFunction.checkNull(subList.get(6)).equalsIgnoreCase("")){
													errorPos="("+(i+1)+",7)";
													validateMsg.append("\nCan not Fill GENDER for Corporate at '"+errorPos+"'");
													fill_Error.add(errorPos);
													ValidateFlag=true;
												}

												//CUSTOMER_CONSTITUTION,varchar(8)
												if(!CommonFunction.checkNull(subList.get(8)).equalsIgnoreCase("")){
													if(CommonFunction.checkNull(subList.get(8)).equalsIgnoreCase("LIMITED LIABILITY")||CommonFunction.checkNull(subList.get(8)).equalsIgnoreCase("LLC")||
															CommonFunction.checkNull(subList.get(8)).equalsIgnoreCase("LIMITED LIABILITY(LC)")||CommonFunction.checkNull(subList.get(8)).equalsIgnoreCase("LLCLC")||						
															CommonFunction.checkNull(subList.get(8)).equalsIgnoreCase("PARTNERSHIP")||CommonFunction.checkNull(subList.get(8)).equalsIgnoreCase("PARTNER")||
															CommonFunction.checkNull(subList.get(8)).equalsIgnoreCase("PARTNERSHIP(LC)")||CommonFunction.checkNull(subList.get(8)).equalsIgnoreCase("PARTNERLC")||
															CommonFunction.checkNull(subList.get(8)).equalsIgnoreCase("PRIVATE LIMITED")||CommonFunction.checkNull(subList.get(8)).equalsIgnoreCase("PRIVATE")||
															CommonFunction.checkNull(subList.get(8)).equalsIgnoreCase("PRIVATE-C(LC)")||CommonFunction.checkNull(subList.get(8)).equalsIgnoreCase("PRIVATELC")||									
															CommonFunction.checkNull(subList.get(8)).equalsIgnoreCase("SOLE PROPRIETORSHIP")||CommonFunction.checkNull(subList.get(8)).equalsIgnoreCase("PROPRIETOR")||
															CommonFunction.checkNull(subList.get(8)).equalsIgnoreCase("PUBLIC LIMITED")||CommonFunction.checkNull(subList.get(8)).equalsIgnoreCase("PUBLIC")||											
															CommonFunction.checkNull(subList.get(8)).equalsIgnoreCase("PUBLIC-KYC A(LC)")||CommonFunction.checkNull(subList.get(8)).equalsIgnoreCase("PUBLICLC1")||
															CommonFunction.checkNull(subList.get(8)).equalsIgnoreCase("PUBLIC-KYC B(LC)")||CommonFunction.checkNull(subList.get(8)).equalsIgnoreCase("PUBLICLC2")||											
															CommonFunction.checkNull(subList.get(8)).equalsIgnoreCase("PUBLIC-KYC C(LC)")||CommonFunction.checkNull(subList.get(8)).equalsIgnoreCase("PUBLICLC3")||
															CommonFunction.checkNull(subList.get(8)).equalsIgnoreCase("SELF EMPLOYED PROFESSIONAL")||CommonFunction.checkNull(subList.get(8)).equalsIgnoreCase("SEP")||											
															CommonFunction.checkNull(subList.get(8)).equalsIgnoreCase("SOCIETY AND TRUST")||CommonFunction.checkNull(subList.get(8)).equalsIgnoreCase("SOCIETY")||
															CommonFunction.checkNull(subList.get(8)).equalsIgnoreCase("TRUST")||CommonFunction.checkNull(subList.get(8)).equalsIgnoreCase("TRUST")){												
													}
													else{
														errorPos="("+(i+1)+",9)";
														validateMsg.append("\nCUSTOMER_CONSTITUTION can be either "
																+ "\"LIMITED LIABILITY/LLC\" or \"LIMITED LIABILITY(LC)/LLCLC\" or "												
																+ "\"PARTNERSHIP/PARTNER\" or \"PARTNERSHIP(LC)/PARTNERLC\" or "														
																+ "\"PRIVATE LIMITED/PRIVATE\" or \"PRIVATE-C(LC)/PRIVATELC\" or "														
																+ "\"SOLE PROPRIETORSHIP/PROPRIETOR\" or \"PUBLIC LIMITED/PUBLIC\" or "														
																+ "\"PUBLIC-KYC A(LC)/PUBLICLC1\" or \"PUBLIC-KYC B(LC)/PUBLICLC2\" or "														
																+ "\"PUBLIC-KYC C(LC)/PUBLICLC3\" or \"SELF EMPLOYED PROFESSIONAL/SEP\" or "														
																+ "\"SOCIETY AND TRUST/SOCIETY\" or \"TRUST/TRUST\"  "
																+ "at '"+errorPos+"'");
														data_Error.add(errorPos);
														ValidateFlag=true;
													}
												}
												else{
													errorPos="("+(i+1)+",9)";
													validateMsg.append("\nCUSTOMER_CONSTITUTION Desc Can not be Left Blank '"+errorPos+"'");
													blank_Error.add(errorPos);
													ValidateFlag=true;
												}

												//CUSTOMER_DOB,date 
												if(!CommonFunction.checkNull(subList.get(9)).equalsIgnoreCase("")){
													if(isDateValid(subList.get(9).toString())){
														if(DateComparator(subList.get(9).toString(),bus_date)>=0){
															errorPos="("+(i+1)+",10)";
															validateMsg.append("\nCUSTOMER_DOB can not be greater than Buisness Date At '"+errorPos+"'");
															data_Error.add(errorPos);
															ValidateFlag=true;
														}
													}
													else{
														errorPos="("+(i+1)+",10)";
														validateMsg.append("\nInvalid DateFormat At '"+errorPos+"' Date Should be in (DD-MM-YYYY) Format");
														data_Error.add(errorPos);
														ValidateFlag=true;
													}
												}
												else{}

												//Caste Category,varchar(?)
												if(!CommonFunction.checkNull(subList.get(10)).equalsIgnoreCase("")){
													errorPos="("+(i+1)+",11)";
													validateMsg.append("\nCan not Fill Caste_Category for Corporate  at '"+errorPos+"'");
													fill_Error.add(errorPos);
													ValidateFlag=true;
												}

												//FATHER_HUSBAND_NAME,varchar(50)
												if(!CommonFunction.checkNull(subList.get(11)).equalsIgnoreCase("")){
													errorPos="("+(i+1)+",12)";
													validateMsg.append("\nCan not Fill FATHER_HUSBAND_NAME for Corporate at '"+errorPos+"'");
													fill_Error.add(errorPos);
													ValidateFlag=true;
												}


												//MARITAL_STATUS,varchar(1)
												if(!CommonFunction.checkNull(subList.get(12)).equalsIgnoreCase("")){
													errorPos="("+(i+1)+",13)";
													validateMsg.append("\nCan not Fill MARITAL_STATUS for Corporate at '"+errorPos+"'");
													fill_Error.add(errorPos);
													ValidateFlag=true;
												}


												//CUSTMER_PAN,varchar(25)-- remove it
												if(!CommonFunction.checkNull(subList.get(14)).equalsIgnoreCase("")){
													if(CommonFunction.checkNull(subList.get(14)).length() > 25){
														errorPos="("+(i+1)+",15)";
														validateMsg.append("CUSTMER_PAN size exceeds max length 25 '"+errorPos+"'");
														size_Error.add(errorPos);
														ValidateFlag=true;
													}
												}
												else{
													errorPos="("+(i+1)+",15)";
													validateMsg.append("CUSTMER_PAN Can not be Left Blank '"+errorPos+"'");
													blank_Error.add(errorPos);
													ValidateFlag=true;
												}

												//Education_Details varchar(20)
												if(!CommonFunction.checkNull(subList.get(15)).equalsIgnoreCase("")){
													errorPos="("+(i+1)+",16)";
													validateMsg.append("\nCan not Fill Education_Details for Corporate at '"+errorPos+"'");
													fill_Error.add(errorPos);
													ValidateFlag=true;
												}

												//put adharNO(16)
												if(!CommonFunction.checkNull(subList.get(16)).equalsIgnoreCase("")){
													errorPos="("+(i+1)+",17)";
													validateMsg.append("\nCan not Fill AadharNo for Corporate at '"+errorPos+"'");
													fill_Error.add(errorPos);
													ValidateFlag=true;
												}

												//GROUP_NAME,varchar(50)
												if(!CommonFunction.checkNull(subList.get(17)).equalsIgnoreCase("")){
													if(CommonFunction.checkNull(subList.get(17)).length() > 50){
														errorPos="("+(i+1)+",18)";
														validateMsg.append("\nGROUP_NAME size exceeds max length 50 '"+errorPos+"'");
														size_Error.add(errorPos);
														ValidateFlag=true;
													}
												}
												else{
													errorPos="("+(i+1)+",18)";
													validateMsg.append("\nGROUP_NAME Can not be Left Blank '"+errorPos+"'");
													blank_Error.add(errorPos);
													ValidateFlag=true;
												}

												//CUSTOMER_REGISTRATION_NO,varchar(25)
												if(!CommonFunction.checkNull(subList.get(18)).equalsIgnoreCase("")){
													if(CommonFunction.checkNull(subList.get(18)).length() >25){
														errorPos="("+(i+1)+",19)";
														validateMsg.append("\nCUSTOMER_REGISTRATION_NO size exceeds max length 25 '"+errorPos+"'");
														size_Error.add(errorPos);
														ValidateFlag=true;
													}
												}
												else{
													errorPos="("+(i+1)+",19)";
													validateMsg.append("\nCUSTOMER_REGISTRATION_NO Can not be Left Blank '"+errorPos+"'");
													blank_Error.add(errorPos);
													ValidateFlag=true;
												}


												//CUSTOMER_BUSINESS_SEGMENT,varchar(10)
												if(!CommonFunction.checkNull(subList.get(19)).equalsIgnoreCase("")){
													if(CommonFunction.checkNull(subList.get(19)).equalsIgnoreCase("LSI")||CommonFunction.checkNull(subList.get(19)).equalsIgnoreCase("LARGE SCALE")||
															CommonFunction.checkNull(subList.get(19)).equalsIgnoreCase("MSI")||CommonFunction.checkNull(subList.get(19)).equalsIgnoreCase("MEDIUM SCALE")||
															CommonFunction.checkNull(subList.get(19)).equalsIgnoreCase("SME")||CommonFunction.checkNull(subList.get(19)).equalsIgnoreCase("SMALL AND MEDIUM ENTERPRISE")||
															CommonFunction.checkNull(subList.get(19)).equalsIgnoreCase("SSI")||CommonFunction.checkNull(subList.get(19)).equalsIgnoreCase("SMALL SCALE")){

													}
													else{
														errorPos="("+(i+1)+",20)";
														validateMsg.append("\nCUSTOMER_BUSINESS_SEGMENT can be either \"LARGE SCALE/LSI\" or \"MEDIUM SCALE/MSI\" or \"SMALL AND MEDIUM ENTERPRISE/SME\" or \"SMALL SCALE/SSI\" at '"+errorPos+"'");
														data_Error.add(errorPos);
														ValidateFlag=true;
													}
												}
												else{
													errorPos="("+(i+1)+",20)";
													validateMsg.append("\nCUSTOMER_BUSINESS_SEGMENT Can not be Left Blank '"+errorPos+"'");
													blank_Error.add(errorPos);
													ValidateFlag=true;
												}

												//Nature_of_business ,varchar(50)
												if(!CommonFunction.checkNull(subList.get(22)).equalsIgnoreCase("")){
													if(CommonFunction.checkNull(subList.get(22)).length() > 50){
														errorPos="("+(i+1)+",23)";
														validateMsg.append("\nNature_of_business size exceeds max length 50 '"+errorPos+"'");
														size_Error.add(errorPos);
														ValidateFlag=true;
													}
												}
												else{
													errorPos="("+(i+1)+",23)";
													validateMsg.append("\nNature_of_business Can not be Left Blank '"+errorPos+"'");
													blank_Error.add(errorPos);
													ValidateFlag=true;
												}
											}


											//put emailID here varchar(100)
											if(!CommonFunction.checkNull(subList.get(13)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(13)).length() > 100){
													errorPos="("+(i+1)+",14)";
													validateMsg.append("\nEmail ID  size exceeds max length 100 '"+errorPos+"'");
													size_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",14)";
												validateMsg.append("\nEmail ID Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}

											//CUSTOMER_INDUSTRY,int(10)
											if(!CommonFunction.checkNull(subList.get(20)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(20)).length() > 10){
													errorPos="("+(i+1)+",21)";
													validateMsg.append("\nCUSTOMER_INDUSTRY size exceeds max length 10 '"+errorPos+"'");
													size_Error.add(errorPos);
													ValidateFlag=true;
												}
												if(!StringUtils.isNumeric(CommonFunction.checkNull(subList.get(20)))){
													errorPos="("+(i+1)+",21)";
													validateMsg.append("\nCUSTOMER_INDUSTRY should be a Number '"+errorPos+"'");
													type_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",21)";
												validateMsg.append("\nCUSTOMER_INDUSTRY Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}

											//CUSTOMER_SUB_INDUSTRY,int(10)
											if(!CommonFunction.checkNull(subList.get(21)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(21)).length() >10){
													errorPos="("+(i+1)+",22)";
													validateMsg.append("\nCUSTOMER_SUB_INDUSTRY size exceeds max length 10 '"+errorPos+"'");
													size_Error.add(errorPos);
													ValidateFlag=true;
												}
												if(!StringUtils.isNumeric(CommonFunction.checkNull(subList.get(21)))){
													errorPos="("+(i+1)+",22)";
													validateMsg.append("\nCUSTOMER_SUB_INDUSTRY should be a Number '"+errorPos+"'");
													type_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",22)";
												validateMsg.append("\nCUSTOMER_SUB_INDUSTRY Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}

											//OTHOR_RELATIONSHIP_TYPE varchar(3)
											if(!CommonFunction.checkNull(subList.get(23)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(23)).equalsIgnoreCase("CS")||CommonFunction.checkNull(subList.get(23)).equalsIgnoreCase("Customer")||
														CommonFunction.checkNull(subList.get(23)).equalsIgnoreCase("SU")||CommonFunction.checkNull(subList.get(23)).equalsIgnoreCase("Supplier")||
														CommonFunction.checkNull(subList.get(23)).equalsIgnoreCase("MF")||CommonFunction.checkNull(subList.get(23)).equalsIgnoreCase("Manufacturer")){

												}
												else{
													errorPos="("+(i+1)+",24)";
													validateMsg.append("\nOTHOR_RELATIONSHIP_TYPE can be either \"Customer/CS\" or \"Supplier/SU\" or \"Manufacturer/MF\" at '"+errorPos+"'");
													data_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",24)";
												validateMsg.append("\nOTHOR_RELATIONSHIP_TYPE Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}

											//Bank_name
											if(!CommonFunction.checkNull(subList.get(24)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(24)).length() > 10){
													errorPos="("+(i+1)+",25)";
													validateMsg.append("\nBank_name size exceeds max length 10 '"+errorPos+"'");
													size_Error.add(errorPos);
													ValidateFlag=true;
												}
												if(!StringUtils.isNumeric(CommonFunction.checkNull(subList.get(24)))){
													errorPos="("+(i+1)+",25)";
													validateMsg.append("\nBank_name should be a Number '"+errorPos+"'");
													type_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
										

											//Bank_branch
											if(!CommonFunction.checkNull(subList.get(25)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(25)).length() > 10){
													errorPos="("+(i+1)+",26)";
													validateMsg.append("\nBank_branch size exceeds max length 10 '"+errorPos+"'");
													size_Error.add(errorPos);
													ValidateFlag=true;
												}
												if(!StringUtils.isNumeric(CommonFunction.checkNull(subList.get(25)))){
													errorPos="("+(i+1)+",26)";
													validateMsg.append("\nBank_branch should be a Number '"+errorPos+"'");
													type_Error.add(errorPos);
													ValidateFlag=true;
												}

											}
										

											//Account_No
											if(!CommonFunction.checkNull(subList.get(26)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(26)).length() > 20){
													errorPos="("+(i+1)+",27)";
													validateMsg.append("\nAccount_No size exceeds max length 20 '"+errorPos+"'");
													size_Error.add(errorPos);
													ValidateFlag=true;
												}
												if(!StringUtils.isNumeric(CommonFunction.checkNull(subList.get(26)))){
													errorPos="("+(i+1)+",27)";
													validateMsg.append("\nAccount_No should be a Number '"+errorPos+"'");
													type_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											

											//Account_Type
											if(!CommonFunction.checkNull(subList.get(27)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(27)).equalsIgnoreCase("1")||CommonFunction.checkNull(subList.get(27)).equalsIgnoreCase("2")||
														CommonFunction.checkNull(subList.get(27)).equalsIgnoreCase("3")||CommonFunction.checkNull(subList.get(27)).equalsIgnoreCase("4")){

												}
												else{
													errorPos="("+(i+1)+",28)";
													validateMsg.append("\nAccount_Type can be either \"1(CC/OD)\" or \"2(Current)\" or \"3(Saving)\" or \"4(Others)\" at '"+errorPos+"'");
													data_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
										
										}
									}  

									panNo=null;
									aadharNo=null;

									//checking if error occured
									if(ValidateFlag){
										msg="F";
										sheetInfoMsg=new StringBuffer();
										sheetInfoMsg.append("\nFailed at 'Cust Details' Sheet");
										if(blank_Error.size()>0)
											sheetInfoMsg.append("\nFields Should not Be blank at '"+blank_Error+"' ");
										if(fill_Error.size()>0)
											sheetInfoMsg.append("\nFields filled for inappropriate CustomerType at '"+fill_Error+"' ");
										if(size_Error.size()>0)
											sheetInfoMsg.append("\nFields Exceeding max size at '"+size_Error+"' ");
										if(type_Error.size()>0)
											sheetInfoMsg.append("\nFields of Incorrect DataType at '"+type_Error+"' ");
										if(data_Error.size()>0)
											sheetInfoMsg.append("\nFields Having Invalid Data at '"+data_Error+"' ");
										sheetInfoMsg.append("\nFor more information kindly check Error Log");

										/*request.setAttribute("ValMsg",validateMsg);
								session.setAttribute("InfoMsg",sheetInfoMsg);*/
										break;
									}

								}
								// Address Details
								if(p==2 && dataList.size()>0)
								{ 
									for(i=0;i<dataList.size();i++)//rows loop
									{	     	  
										subList=(ArrayList)dataList.get(i);//for fetching all cols fields
										if(subList.size()>0)
										{
											//Application_Form_No - varchar(20)					
											if(!CommonFunction.checkNull(subList.get(0)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(0)).length() > 20){
													errorPos="("+(i+1)+",1)";
													validateMsg.append("\nApplication_Form_No size exceeds max length 20 '"+errorPos+"'");
													size_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",1)";
												validateMsg.append("\nApplication_Form_No Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}

											//Customer_No,int(10)
											if(!CommonFunction.checkNull(subList.get(1)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(1)).length() > 10){
													errorPos="("+(i+1)+",2)";
													validateMsg.append("\nCustomer_No size exceeds max length 10 '"+errorPos+"'");
													size_Error.add(errorPos);
													ValidateFlag=true;
												}
												if(!StringUtils.isNumeric(CommonFunction.checkNull(subList.get(1)))){
													errorPos="("+(i+1)+",2)";
													validateMsg.append("\nCustomer_No should be a Number '"+errorPos+"'");
													type_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",2)";
												validateMsg.append("\nCustomer_No Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}

											//ADDRESS_TYPE,varchar(10)
											if(!CommonFunction.checkNull(subList.get(2)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(2)).equalsIgnoreCase("OFFICE")||CommonFunction.checkNull(subList.get(2)).equalsIgnoreCase("REGISTERED OFFICE")||CommonFunction.checkNull(subList.get(2)).equalsIgnoreCase("REGOFF")||
														CommonFunction.checkNull(subList.get(2)).equalsIgnoreCase("OTHER")||CommonFunction.checkNull(subList.get(2)).equalsIgnoreCase("RESIDENCE")||CommonFunction.checkNull(subList.get(2)).equalsIgnoreCase("REI")||
														CommonFunction.checkNull(subList.get(2)).equalsIgnoreCase("PERMANENT")||CommonFunction.checkNull(subList.get(2)).equalsIgnoreCase("RESI CUM OFFICE")||CommonFunction.checkNull(subList.get(2)).equalsIgnoreCase("RES")||
														CommonFunction.checkNull(subList.get(2)).equalsIgnoreCase("THIRD PARTY")||CommonFunction.checkNull(subList.get(2)).equalsIgnoreCase("TP")){

												}
												else{
													errorPos="("+(i+1)+",3)";
													validateMsg.append("\nCustomer_Type can be either \"OFFICE\" or \"OTHER\" or \"PERMANENT\" or"
															+ "\"REGISTERED_OFFICE/REGOFF\" or \"RESIDENCE/REI\" or \"RESI_CUM_OFFICE/RES\" or \"THIRD_PARTY/TP\" at '"+errorPos+"'");
													data_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",3)";
												validateMsg.append("\nADDRESS_TYPE Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}

											//ADDRESS_LINE1,varchar(50)
											if(!CommonFunction.checkNull(subList.get(3)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(3)).length() > 50){
													errorPos="("+(i+1)+",4)";
													validateMsg.append("\nADDRESS_LINE1 size exceeds max length 50 '"+errorPos+"'");
													size_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",4)";
												validateMsg.append("\nADDRESS_LINE1 Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}

											//ADDRESS_LINE2,varchar(50)
											if(!CommonFunction.checkNull(subList.get(4)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(4)).length() > 50){
													errorPos="("+(i+1)+",5)";
													validateMsg.append("\nADDRESS_LINE2 size exceeds max length 50 '"+errorPos+"'");
													size_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",5)";
												validateMsg.append("\nADDRESS_LINE2 Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}

											//STATE,int(10)
											if(!CommonFunction.checkNull(subList.get(5)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(5)).length() >10){
													errorPos="("+(i+1)+",6)";
													validateMsg.append("\nSTATE exceeds max length 10 '"+errorPos+"'");
													size_Error.add(errorPos);
													ValidateFlag=true;
												}
												if(!StringUtils.isNumeric(CommonFunction.checkNull(subList.get(5)))){
													errorPos="("+(i+1)+",6)";
													validateMsg.append("\nPlease Provide State Master Code(Numeric) at '"+errorPos+"'");
													type_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",6)";
												validateMsg.append("\nSTATE Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}

											//DISTRICT,int(10)
											if(!CommonFunction.checkNull(subList.get(6)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(6)).length() > 10){
													errorPos="("+(i+1)+",7)";
													validateMsg.append("\nDISTRICT size exceeds max length 10 '"+errorPos+"'");
													size_Error.add(errorPos);
													ValidateFlag=true;
												}
												if(!StringUtils.isNumeric(CommonFunction.checkNull(subList.get(6)))){
													errorPos="("+(i+1)+",7)";
													validateMsg.append("\nPlease Provide DISTRICT Master Code(Numeric) at '"+errorPos+"'");
													type_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",7)";
												validateMsg.append("\nDISTRICT Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}

											//COUNTRY,int(10)
											if(!CommonFunction.checkNull(subList.get(7)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(7)).length() > 50){
													errorPos="("+(i+1)+",8)";
													validateMsg.append("\nCOUNTRY size exceeds max length 50 '"+errorPos+"'");
													size_Error.add(errorPos);
													ValidateFlag=true;
												}
												if(!StringUtils.isNumeric(CommonFunction.checkNull(subList.get(7)))){
													errorPos="("+(i+1)+",8)";
													validateMsg.append("\nPlease Provide COUNTRY Master Code(Numeric) at '"+errorPos+"'");
													type_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",8)";
												validateMsg.append("\nCOUNTRY Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}

											//PINCODE,varchar(10)
											if(!CommonFunction.checkNull(subList.get(8)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(8)).length() >10){
													errorPos="("+(i+1)+",9)";
													validateMsg.append("\nPINCODE size exceeds max length 10 '"+errorPos+"'");
													size_Error.add(errorPos);
													ValidateFlag=true;
												}
												if(!StringUtils.isNumeric(CommonFunction.checkNull(subList.get(8)))){
													errorPos="("+(i+1)+",9)";
													validateMsg.append("\nPINCODE should be a Number '"+errorPos+"'");
													type_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",9)";
												validateMsg.append("\nPINCODE Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}

											//PRIMARY_PHONE,varhcar(25)
											if(!CommonFunction.checkNull(subList.get(9)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(9)).length() >10){
													errorPos="("+(i+1)+",10)";
													validateMsg.append("\nPRIMARY_PHONE size exceeds max length 10 '"+errorPos+"'");
													size_Error.add(errorPos);
													ValidateFlag=true;
												}
												if(!StringUtils.isNumeric(CommonFunction.checkNull(subList.get(9)))){
													errorPos="("+(i+1)+",10)";
													validateMsg.append("\nPRIMARY_PHONE should be a Number '"+errorPos+"'");
													type_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",10)";
												validateMsg.append("\nPRIMARY_PHONE Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}

											//Landmark(9) varchar(25)
											if(!CommonFunction.checkNull(subList.get(10)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(10)).length() > 25){
													errorPos="("+(i+1)+",11)";
													validateMsg.append("\nLandmark size exceeds max length 25 '"+errorPos+"'");
													size_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
/*											else{
												errorPos="("+(i+1)+",11)";
												validateMsg.append("\nLandmark Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}*/

											//NO_OF_YEARS int(3)
											if(!CommonFunction.checkNull(subList.get(11)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(11)).length() >3){
													errorPos="("+(i+1)+",12)";
													validateMsg.append("\nNO_OF_YEARS size exceeds max length 3 '"+errorPos+"'");
													size_Error.add(errorPos);
													ValidateFlag=true;
												}
												if(!StringUtils.isNumeric(CommonFunction.checkNull(subList.get(11)))){
													errorPos="("+(i+1)+",12)";
													validateMsg.append("\nNO_OF_YEARS should be a Number '"+errorPos+"'");
													type_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",12)";
												validateMsg.append("\nNO_OF_YEARS Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}

											//NO_OF_Months int(3)
											if(!CommonFunction.checkNull(subList.get(12)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(12)).length() >3){
													errorPos="("+(i+1)+",13)";
													validateMsg.append("\nNO_OF_Months size exceeds max length 3 '"+errorPos+"'");
													size_Error.add(errorPos);
													ValidateFlag=true;
												}
												if(!StringUtils.isNumeric(CommonFunction.checkNull(subList.get(12)))){
													errorPos="("+(i+1)+",13)";
													validateMsg.append("\nNO_OF_Months should be a Number '"+errorPos+"'");
													type_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",13)";
												validateMsg.append("\nNO_OF_Months Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}

										}
									}  

									//checking if error occured
									if(ValidateFlag){
										msg="F";
										sheetInfoMsg=new StringBuffer();
										sheetInfoMsg.append("\nFailed at 'Address Details' Sheet");
										if(blank_Error.size()>0)
											sheetInfoMsg.append("\nFields Should not Be blank at '"+blank_Error+"' ");
										if(size_Error.size()>0)
											sheetInfoMsg.append("\nFields Exceeding max size at '"+size_Error+"' ");
										if(type_Error.size()>0)
											sheetInfoMsg.append("\nFields of Incorrect DataType at '"+type_Error+"' ");
										if(data_Error.size()>0)
											sheetInfoMsg.append("\nFields Having Invalid Data at '"+data_Error+"' ");
										sheetInfoMsg.append("\nFor more information kindly check Error Log");

										/*request.setAttribute("ValMsg",validateMsg);
								session.setAttribute("InfoMsg",sheetInfoMsg);*/
										break;
									}

								}

								// Loan Details
								if(p==3 && dataList.size()>0)
								{ 
									appFormNo=new ArrayList();
									for(i=0;i<dataList.size();i++)//rows loop
									{	     	  
										subList=(ArrayList)dataList.get(i);//for fetching all cols fields
										if(subList.size()>0)
										{

											//Application_Form_No - varchar(20)
											if(!CommonFunction.checkNull(subList.get(0)).equalsIgnoreCase("")){

												if(appFormNo.contains(subList.get(0))||mainAppForm.contains(subList.get(0))){
													errorPos="("+(i+1)+",1)";
													validateMsg.append("\nApplication_Form_No Already Exists at '"+errorPos+"'");
													data_Error.add(errorPos);
													ValidateFlag=true;

												}else{
													if(CommonFunction.checkNull(subList.get(0)).length() > 20){
														errorPos="("+(i+1)+",1)";
														validateMsg.append("\nApplication_Form_No size exceeds max length 20 '"+errorPos+"'");
														size_Error.add(errorPos);
														ValidateFlag=true;
													}
													else{
														appFormNo.add(subList.get(0));
													}
												}
											}
											else{
												errorPos="("+(i+1)+",1)";
												validateMsg.append("\nApplication_Form_No Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}

											//DEAL_PRODUCT,varchar(10)
											if(!CommonFunction.checkNull(subList.get(1)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(1)).length() > 10){
													errorPos="("+(i+1)+",2)";
													validateMsg.append("\nDEAL_PRODUCT size exceeds max length 10 '"+errorPos+"'");
													size_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",2)";
												validateMsg.append("\nDEAL_PRODUCT Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}

											//DEAL_SCHEME,int(10)
											if(!CommonFunction.checkNull(subList.get(2)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(2)).length() > 10){
													errorPos="("+(i+1)+",3)";
													validateMsg.append("\nDEAL_SCHEME size exceeds max length 10 '"+errorPos+"'");
													size_Error.add(errorPos);
													ValidateFlag=true;
												}
												if(!StringUtils.isNumeric(CommonFunction.checkNull(subList.get(2)))){
													errorPos="("+(i+1)+",3)";
													validateMsg.append("\nDEAL_SCHEME should be a Number '"+errorPos+"'");
													type_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",3)";
												validateMsg.append("\nDEAL_SCHEME Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}

											//DEAL_TENURE,int(5)
											if(!CommonFunction.checkNull(subList.get(3)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(3)).length() > 5){
													errorPos="("+(i+1)+",4)";
													validateMsg.append("\nDEAL_TENURE size exceeds max length 5 '"+errorPos+"'");
													size_Error.add(errorPos);
													ValidateFlag=true;
												}
												if(!StringUtils.isNumeric(CommonFunction.checkNull(subList.get(3)))){
													errorPos="("+(i+1)+",4)";
													validateMsg.append("\nDEAL_TENURE should be a Number '"+errorPos+"'");
													type_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",4)";
												validateMsg.append("\nDEAL_TENURE Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}

											//DEAL_LOAN_AMOUNT,decimal(22,4)
											if(!CommonFunction.checkNull(subList.get(4)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(4)).length() >10){
													errorPos="("+(i+1)+",5)";
													validateMsg.append("\nDEAL_LOAN_AMOUNT size exceeds max length 10 '"+errorPos+"'");
													size_Error.add(errorPos);
													ValidateFlag=true;
												}
												if(!StringUtils.isNumeric(CommonFunction.checkNull(subList.get(4)))){
													errorPos="("+(i+1)+",5)";
													validateMsg.append("\nDEAL_LOAN_AMOUNT should be a Number '"+errorPos+"'");
													type_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",5)";
												validateMsg.append("\nDEAL_LOAN_AMOUNT Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}

											//DEAL_RATE_TYPE varchar(1)
											if(!CommonFunction.checkNull(subList.get(5)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(5)).equalsIgnoreCase("E")||CommonFunction.checkNull(subList.get(5)).equalsIgnoreCase("F")){

												}
												else{
													errorPos="("+(i+1)+",6)";
													validateMsg.append("\nDEAL_RATE_TYPE can be either \"Effective Rate=>'E'\" or \"Flat Rate=>'F'\" at '"+errorPos+"'");
													data_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",6)";
												validateMsg.append("\nDEAL_RATE_TYPE Can not be Left Blank at '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}

											//DEAL_RATE_METHOD,varchar(1)
											if(!CommonFunction.checkNull(subList.get(6)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(6)).equalsIgnoreCase("F")||CommonFunction.checkNull(subList.get(6)).equalsIgnoreCase("L")){

												}
												else{
													errorPos="("+(i+1)+",7)";
													validateMsg.append("\nDEAL_RATE_METHOD can be either \"Fixed=>'F'\" or \"Floating=>'L'\" at '"+errorPos+"'");
													data_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",7)";
												validateMsg.append("\nDEAL_RATE_METHOD Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}

											//DEAL_BASE_RATE_TYPE,varchar(10)
											if(!CommonFunction.checkNull(subList.get(7)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(7)).equalsIgnoreCase("AXIS")||CommonFunction.checkNull(subList.get(7)).equalsIgnoreCase("ICF")||
														CommonFunction.checkNull(subList.get(7)).equalsIgnoreCase("ICF_PLR")||CommonFunction.checkNull(subList.get(7)).equalsIgnoreCase("INDUSIND")||
														CommonFunction.checkNull(subList.get(7)).equalsIgnoreCase("KOTAK")||CommonFunction.checkNull(subList.get(7)).equalsIgnoreCase("SBI")||
														CommonFunction.checkNull(subList.get(7)).equalsIgnoreCase("OTHERS")){

												}
												else{
													errorPos="("+(i+1)+",8)";
													validateMsg.append("\nDEAL_BASE_RATE_TYPE can be either \"AXIS\" or \"ICF\" or \"ICF_PLR\" or \"INDUSIND\" or \"KOTAK\" or \"SBI\" or \"OTHERS\" at '"+errorPos+"'");
													data_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",8)";
												validateMsg.append("\nDEAL_BASE_RATE_TYPE Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}

											//DEAL_FINAL_RATE,decimal(10,7)
											if(!CommonFunction.checkNull(subList.get(8)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(8)).length() > 10){
													errorPos="("+(i+1)+",9)";
													validateMsg.append("\nDEAL_FINAL_RATE size exceeds max length 10 '"+errorPos+"'");
													size_Error.add(errorPos);
													ValidateFlag=true;
												}
												if(!StringUtils.isNumeric(CommonFunction.checkNull(subList.get(8)))){
													errorPos="("+(i+1)+",9)";
													validateMsg.append("\nDEAL_FINAL_RATE should be a Number '"+errorPos+"'");
													type_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",9)";
												validateMsg.append("\nDEAL_FINAL_RATE Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}


											//DEAL_REPAYMENT_TYPE,varchar(1)
											if(!CommonFunction.checkNull(subList.get(9)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(9)).length() > 1){
													errorPos="("+(i+1)+",10)";
													validateMsg.append("\nDEAL_REPAYMENT_TYPE size exceeds max length 1 '"+errorPos+"'");
													size_Error.add(errorPos);
													ValidateFlag=true;
												}
												if(!StringUtils.isAlphanumeric(CommonFunction.checkNull(subList.get(0)))){

												}
											}
											else{
												errorPos="("+(i+1)+",10)";
												validateMsg.append("\nDEAL_REPAYMENT_TYPE Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}

											//DEAL_INSTALLMENT_TYPE,varchar(1)
											if(!CommonFunction.checkNull(subList.get(10)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(10)).equalsIgnoreCase("E")||CommonFunction.checkNull(subList.get(10)).equalsIgnoreCase("G")||
														CommonFunction.checkNull(subList.get(10)).equalsIgnoreCase("P")||CommonFunction.checkNull(subList.get(10)).equalsIgnoreCase("Q")||
														CommonFunction.checkNull(subList.get(10)).equalsIgnoreCase("S")){

												}
												else{
													errorPos="("+(i+1)+",11)";
													validateMsg.append("\nDEAL_INSTALLMENT_TYPE can be either \"Eq. INSTALLMENT=>'E'\" or \"Gr. INSTALLMENT=>'G'\" "
															+ "or \"Eq. PRINCIPAL=>'P'\" or \"Gr. PRINCIPAL1=>'Q'\" or \"SEPARATE PRINCIPAL & INTEREST=>'S'\" at '"+errorPos+"'");
													data_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",11)";
												validateMsg.append("\nDEAL_INSTALLMENT_TYPE Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}

											//DEAL_INSTALLMENT_MODE,varchar(1)
											if(!CommonFunction.checkNull(subList.get(11)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(11)).equalsIgnoreCase("A")||CommonFunction.checkNull(subList.get(11)).equalsIgnoreCase("R")){

												}
												else{
													errorPos="("+(i+1)+",12)";
													validateMsg.append("\nDEAL_INSTALLMENT_MODE can be either \"Advance=>'A'\" or \"Arrear=>'R'\" at '"+errorPos+"'");
													data_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",12)";
												validateMsg.append("\nDEAL_INSTALLMENT_MODE Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}

											//frequency 
											if(!CommonFunction.checkNull(subList.get(12)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(12)).equalsIgnoreCase("M")||CommonFunction.checkNull(subList.get(12)).equalsIgnoreCase("B")||
														CommonFunction.checkNull(subList.get(12)).equalsIgnoreCase("Q")||CommonFunction.checkNull(subList.get(12)).equalsIgnoreCase("H")||
														CommonFunction.checkNull(subList.get(12)).equalsIgnoreCase("Y")){

												}
												else{
													errorPos="("+(i+1)+",13)";
													validateMsg.append("\nfrequency can be either \"MONTHLY=>'M'\" or \"BIMONTHLY=>'B'\" "
															+ "or \"QUARTERLY=>'Q'\" or \"HALFYERALY=>'H'\" or \"	=>'Y'\" at '"+errorPos+"'");
													data_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",13)";
												validateMsg.append("\nfrequency Can not be Left Blank at '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}

											//INT_COMP_FREQ,varchar(1)
											if(!CommonFunction.checkNull(subList.get(13)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(13)).equalsIgnoreCase("M")||CommonFunction.checkNull(subList.get(13)).equalsIgnoreCase("B")||
														CommonFunction.checkNull(subList.get(13)).equalsIgnoreCase("Q")||CommonFunction.checkNull(subList.get(13)).equalsIgnoreCase("H")||
														CommonFunction.checkNull(subList.get(13)).equalsIgnoreCase("Y")){

												}
												else{
													errorPos="("+(i+1)+",14)";
													validateMsg.append("\nINT_COMP_FREQ can be either \"MONTHLY=>'M'\" or \"BIMONTHLY=>'B'\" "
															+ "or \"QUARTERLY=>'Q'\" or \"HALFYERALY=>'H'\" or \"YEARLY=>'Y'\" at '"+errorPos+"'");
													data_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",14)";
												validateMsg.append("\nINT_COMP_FREQ Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}

											//DEAL_INT_CALC_FROM,varchar(1)
											if(!CommonFunction.checkNull(subList.get(14)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(14)).length() > 1){
													errorPos="("+(i+1)+",15)";
													validateMsg.append("\nDEAL_INT_CALC_FROM size exceeds max length 1 '"+errorPos+"'");
													size_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",15)";
												validateMsg.append("\nDEAL_INT_CALC_FROM Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}


											//DEAL_REPAYMENT_MODE,varchar(1)
											if(!CommonFunction.checkNull(subList.get(15)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(15)).equalsIgnoreCase("A")||CommonFunction.checkNull(subList.get(15)).equalsIgnoreCase("E")||
														CommonFunction.checkNull(subList.get(15)).equalsIgnoreCase("H")||CommonFunction.checkNull(subList.get(15)).equalsIgnoreCase("N")||
														CommonFunction.checkNull(subList.get(15)).equalsIgnoreCase("O")||CommonFunction.checkNull(subList.get(15)).equalsIgnoreCase("P")||
														CommonFunction.checkNull(subList.get(15)).equalsIgnoreCase("R")){

												}
												else{
													errorPos="("+(i+1)+",16)";
													validateMsg.append("\nDEAL_REPAYMENT_MODE can be either \"ESCROW A/C=>'A'\" or \"ECS=>'E'\" \"NACH A/C=>'H'\" or \"NPDC=>'N'\" "
															+ "or \"OTC=>'O'\" or \"PDC=>'P'\" or \"RPDC=>'R'\" at '"+errorPos+"'");
													data_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",16)";
												validateMsg.append("\nDEAL_REPAYMENT_MODE Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}

											//DEAL_REPAY_EFF_DATE,date   #30-09-2017 brajesh sir
											if(!CommonFunction.checkNull(subList.get(16)).equalsIgnoreCase("")){
												if(isDateValid(subList.get(16).toString())){
													if(DateComparator(subList.get(16).toString(),bus_date)<0){
														errorPos="("+(i+1)+",17)";
														validateMsg.append("\nDEAL_REPAY_EFF_DATE can not be less than Buisness Date At '"+errorPos+"'");
														data_Error.add(errorPos);
														ValidateFlag=true;
													}
												}
												else{
													errorPos="("+(i+1)+",17)";
													validateMsg.append("\nInvalid DateFormat At '"+errorPos+"' Date Should be in (DD-MM-YYYY) Format");
													data_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",17)";
												validateMsg.append("\nDEAL_REPAY_EFF_DATE Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}

											//DEAL_DUE_DAY,int(2)
											if(!CommonFunction.checkNull(subList.get(17)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(17)).length() >2){
													errorPos="("+(i+1)+",18)";
													validateMsg.append("\nDEAL_DUE_DAY exceeds max length 10 '"+errorPos+"'");
													size_Error.add(errorPos);
													ValidateFlag=true;
												}
												if(!StringUtils.isNumeric(CommonFunction.checkNull(subList.get(17)))){
													errorPos="("+(i+1)+",18)";
													validateMsg.append("\nDEAL_DUE_DAY should be a Number '"+errorPos+"'");
													type_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",18)";
												validateMsg.append("\nDEAL_DUE_DAY Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}

											//DEAL_LOAN_PURPOSE,varchar(50)
											if(!CommonFunction.checkNull(subList.get(18)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(18)).length() >10){
													errorPos="("+(i+1)+",19)";
													validateMsg.append("\nDEAL_LOAN_PURPOSE size exceeds max length 10 '"+errorPos+"'");
													size_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",19)";
												validateMsg.append("\nDEAL_LOAN_PURPOSE Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}

											//DEAL_SECTOR_TYPE varchar(10)
											if(!CommonFunction.checkNull(subList.get(19)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(19)).equalsIgnoreCase("PSL")||CommonFunction.checkNull(subList.get(19)).equalsIgnoreCase("NPSL")){

												}
												else{
													errorPos="("+(i+1)+",20)";
													validateMsg.append("\nDEAL_SECTOR_TYPE can be either \"PSL (PRIORITY SECTOR LENDING)=>'PSL'\" or \"NPSL (NON PRIORITY SECTOR LENDING)=>'NPSL'\" at '"+errorPos+"'");
													data_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",20)";
												validateMsg.append("\nDEAL_SECTOR_TYPE Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}
										}
									}  

									appFormNo=null;

									//checking if error occured
									if(ValidateFlag){
										msg="F";
										sheetInfoMsg=new StringBuffer();
										sheetInfoMsg.append("\nFailed at 'Loan Details' Sheet");
										if(blank_Error.size()>0)
											sheetInfoMsg.append("\nFields Should not Be blank at '"+blank_Error+"' ");
										if(size_Error.size()>0)
											sheetInfoMsg.append("\nFields Exceeding max size at '"+size_Error+"' ");
										if(type_Error.size()>0)
											sheetInfoMsg.append("\nFields of Incorrect DataType at '"+type_Error+"' ");
										if(data_Error.size()>0)
											sheetInfoMsg.append("\nFields Having Invalid Data at '"+data_Error+"' ");
										sheetInfoMsg.append("\nFor more information kindly check Error Log");

										/*request.setAttribute("ValMsg",validateMsg);
								session.setAttribute("InfoMsg",sheetInfoMsg);*/
										break;
									}

								}

								//Financial Analysis
								if(p==4 && dataList.size()>0)
								{ 
									for(i=0;i<dataList.size();i++)//rows loop
									{	     	  
										subList=(ArrayList)dataList.get(i);//for fetching all cols fields
										if(subList.size()>0)
										{

											//Application_Form_No - varchar(20)					
											if(!CommonFunction.checkNull(subList.get(0)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(0)).length() > 20){
													errorPos="("+(i+1)+",1)";
													validateMsg.append("\nApplication_Form_No size exceeds max length 20 '"+errorPos+"'");
													size_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",1)";
												validateMsg.append("\nApplication_Form_No Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}

											//Customer_No,int(10)
											if(!CommonFunction.checkNull(subList.get(1)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(1)).length() > 10){
													errorPos="("+(i+1)+",2)";
													validateMsg.append("\nCustomer_No size exceeds max length 10 '"+errorPos+"'");
													size_Error.add(errorPos);
													ValidateFlag=true;
												}
												if(!StringUtils.isNumeric(CommonFunction.checkNull(subList.get(1)))){
													errorPos="("+(i+1)+",2)";
													validateMsg.append("\nCustomer_No should be a Number '"+errorPos+"'");
													type_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",2)";
												validateMsg.append("\nCustomer_No Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}

											//Monthly_Inhand_Salary
											if(!CommonFunction.checkNull(subList.get(2)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(2)).length() > 10){
													errorPos="("+(i+1)+",3)";
													validateMsg.append("\nMonthly_Inhand_Salary exceeds max length 10 '"+errorPos+"'");
													size_Error.add(errorPos);
													ValidateFlag=true;
												}
												if(!StringUtils.isNumeric(CommonFunction.checkNull(subList.get(2)))){
													errorPos="("+(i+1)+",3)";
													validateMsg.append("\nMonthly_Inhand_Salary should be a Number '"+errorPos+"'");
													type_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",3)";
												validateMsg.append("\nMonthly_Inhand_Salary Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}

											//Verification_method
											if(!CommonFunction.checkNull(subList.get(3)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(3)).equalsIgnoreCase("DOCUMENT")||
														CommonFunction.checkNull(subList.get(3)).equalsIgnoreCase("INSPECTION")){

												}
												else{
													errorPos="("+(i+1)+",4)";
													validateMsg.append("\nVerification_method can be either \"DOCUMENT\" or \"INSPECTION\" at '"+errorPos+"'");
													data_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",4)";
												validateMsg.append("\nVerification_method Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}

											//Source_Type
											if(!CommonFunction.checkNull(subList.get(4)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(4)).length() >10){
													errorPos="("+(i+1)+",5)";
													validateMsg.append("\nSource_Type size exceeds max length 10 '"+errorPos+"'");
													size_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",5)";
												validateMsg.append("\nSource_Type Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}

										}
									}  

									//checking if error occured
									if(ValidateFlag){
										msg="F";
										sheetInfoMsg=new StringBuffer();
										sheetInfoMsg.append("\nFailed at 'Financial Analysis' Sheet");
										if(blank_Error.size()>0)
											sheetInfoMsg.append("\nFields Should not Be blank at '"+blank_Error+"' ");
										if(size_Error.size()>0)
											sheetInfoMsg.append("\nFields Exceeding max size at '"+size_Error+"' ");
										if(type_Error.size()>0)
											sheetInfoMsg.append("\nFields of Incorrect DataType at '"+type_Error+"' ");
										if(data_Error.size()>0)
											sheetInfoMsg.append("\nFields Having Invalid Data at '"+data_Error+"' ");
										sheetInfoMsg.append("\nFor more information kindly check Error Log");

										/*request.setAttribute("ValMsg",validateMsg);
								requsest.setAttribute("InfoMsg",sheetInfoMsg);*/
										break;
									}

								}


								//Installment Plan
								if(p==5 && dataList.size()>0)
								{ 
									appFormNo=new ArrayList();
									for(i=0;i<dataList.size();i++)//rows loop
									{	     	  
										subList=(ArrayList)dataList.get(i);//for fetching all cols fields
										if(subList.size()>0)
										{

											//Application_Form_No - varchar(20)					
											if(!CommonFunction.checkNull(subList.get(0)).equalsIgnoreCase("")){

												if(appFormNo.contains(subList.get(0))||mainAppForm.contains(subList.get(0))){
													errorPos="("+(i+1)+",1)";
													validateMsg.append("\nApplication_Form_No Already Exists at '"+errorPos+"'");
													data_Error.add(errorPos);
													ValidateFlag=true;

												}else{
													if(CommonFunction.checkNull(subList.get(0)).length() > 20){
														errorPos="("+(i+1)+",1)";
														validateMsg.append("\nApplication_Form_No size exceeds max length 20 '"+errorPos+"'");
														size_Error.add(errorPos);
														ValidateFlag=true;
													}
													else{
														appFormNo.add(subList.get(0));
													}
												}
											}
											else{
												errorPos="("+(i+1)+",1)";
												validateMsg.append("\nApplication_Form_No Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}

											//RECOVERY_TYPE- varchar(1)
											if(!CommonFunction.checkNull(subList.get(1)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(1)).equalsIgnoreCase("P")||
														CommonFunction.checkNull(subList.get(1)).equalsIgnoreCase("F")){

												}
												else{
													errorPos="("+(i+1)+",2)";
													validateMsg.append("\nRECOVERY_TYPE can be either \"P\" or \"F\" at '"+errorPos+"'");
													data_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",2)";
												validateMsg.append("\nRECOVERY_TYPE Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}

											//FROM_INSTL_NO- int(3)
											if(!CommonFunction.checkNull(subList.get(2)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(2)).length() > 3){
													errorPos="("+(i+1)+",3)";
													validateMsg.append("\nFROM_INSTL_NO size exceeds max length 3 '"+errorPos+"'");
													size_Error.add(errorPos);
													ValidateFlag=true;
												}
												if(!StringUtils.isNumeric(CommonFunction.checkNull(subList.get(2)))){
													errorPos="("+(i+1)+",3)";
													validateMsg.append("\nFROM_INSTL_NO should be a Number '"+errorPos+"'");
													type_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",3)";
												validateMsg.append("\nFROM_INSTL_NO Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}

											//TO_INSTL_NO- int(3)
											if(!CommonFunction.checkNull(subList.get(3)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(3)).length() > 3){
													errorPos="("+(i+1)+",4)";
													validateMsg.append("\nTO_INSTL_NO size exceeds max length 3 '"+errorPos+"'");
													size_Error.add(errorPos);
													ValidateFlag=true;
												}
												if(!StringUtils.isNumeric(CommonFunction.checkNull(subList.get(3)))){
													errorPos="("+(i+1)+",4)";
													validateMsg.append("\nTO_INSTL_NO should be a Number '"+errorPos+"'");
													type_Error.add(errorPos);
													ValidateFlag=true;
												}

											}
											else{
												errorPos="("+(i+1)+",4)";
												validateMsg.append("\nTO_INSTL_NO Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}



											if(CommonFunction.checkNull(subList.get(1)).equalsIgnoreCase("P")){

												//RECOVERY_PERCENT-decimal(7,4)
												if(!CommonFunction.checkNull(subList.get(4)).equalsIgnoreCase("")){
													if(Integer.parseInt(CommonFunction.checkNull(subList.get(4))) > 100){
														errorPos="("+(i+1)+",5)";
														validateMsg.append("\nRECOVERY_PERCENT can be max 100% at '"+errorPos+"'");
														size_Error.add(errorPos);
														ValidateFlag=true;
													}
													if(!StringUtils.isNumeric(CommonFunction.checkNull(subList.get(4)))){
														errorPos="("+(i+1)+",5)";
														validateMsg.append("\nRECOVERY_PERCENT should be a Number '"+errorPos+"'");
														type_Error.add(errorPos);
														ValidateFlag=true;
													}
												}
												else{
													errorPos="("+(i+1)+",5)";
													validateMsg.append("\nRECOVERY_PERCENT Can not be Left Blank '"+errorPos+"'");
													blank_Error.add(errorPos);
													ValidateFlag=true;
												}

												//PRINCIPAL_AMOUNT-decimal(22,4)			

												if(!CommonFunction.checkNull(subList.get(5)).equalsIgnoreCase("")){
													errorPos="("+(i+1)+",6)";
													validateMsg.append("\nCan not fill Principal Amount for RecoveryType=>P at '"+errorPos+"'");
													fill_Error.add(errorPos);
													ValidateFlag=true;
												}

											}
											if(CommonFunction.checkNull(subList.get(1)).equalsIgnoreCase("F")){

												//RECOVERY_PERCENT-decimal(7,4)
												if(!CommonFunction.checkNull(subList.get(4)).equalsIgnoreCase("")){
													errorPos="("+(i+1)+",5)";
													validateMsg.append("\nCan not fill RECOVERY_PERCENT for RecoveryType=>F at '"+errorPos+"'");
													fill_Error.add(errorPos);
													ValidateFlag=true;
												}


												//PRINCIPAL_AMOUNT-decimal(22,4)
												if(!CommonFunction.checkNull(subList.get(5)).equalsIgnoreCase("")){
													if(CommonFunction.checkNull(subList.get(5)).length() >10){
														errorPos="("+(i+1)+",6)";
														validateMsg.append("\nPRINCIPAL_AMOUNT size exceeds max length 10 '"+errorPos+"'");
														size_Error.add(errorPos);
														ValidateFlag=true;
													}
													if(!StringUtils.isNumeric(CommonFunction.checkNull(subList.get(5)))){
														errorPos="("+(i+1)+",6)";
														validateMsg.append("\nPRINCIPAL_AMOUNT should be a Number '"+errorPos+"'");
														type_Error.add(errorPos);
														ValidateFlag=true;
													}
												}
												else{
													errorPos="("+(i+1)+",6)";
													validateMsg.append("\nPRINCIPAL_AMOUNT Can not be Left Blank '"+errorPos+"'");
													blank_Error.add(errorPos);
													ValidateFlag=true;
												}

											}						

										}
									}  

									appFormNo=null;

									//checking if error occured
									if(ValidateFlag){
										msg="F";
										sheetInfoMsg=new StringBuffer();
										sheetInfoMsg.append("\nFailed at 'Installment Plan' Sheet");
										if(blank_Error.size()>0)
											sheetInfoMsg.append("\nFields Should not Be blank at '"+blank_Error+"' ");
										if(size_Error.size()>0)
											sheetInfoMsg.append("\nFields Exceeding max size at '"+size_Error+"' ");
										if(type_Error.size()>0)
											sheetInfoMsg.append("\nFields of Incorrect DataType at '"+type_Error+"' ");
										if(data_Error.size()>0)
											sheetInfoMsg.append("\nFields Having Invalid Data at '"+data_Error+"' ");
										sheetInfoMsg.append("\nFor more information kindly check Error Log");

										/*request.setAttribute("ValMsg",validateMsg);
								request.setAttribute("InfoMsg",sheetInfoMsg);*/
										break;
									}
								}

							}
						}
						//#########################################################################
						else if(CommonFunction.checkNull(functionId).equalsIgnoreCase("10000828")){
							ArrayList tempLoan=new ArrayList();
							int ijk=0;
							for(p=0;p<workSheets.size();p++){
								ijk++;
								dataList=(ArrayList)workSheets.get(String.valueOf(p));
								StringBuffer bufInsSql = new StringBuffer();
								ArrayList vst=new ArrayList();
								ArrayList bst=new ArrayList();
								ArrayList qst=new ArrayList();
								mainAppForm=new ArrayList();
								loanRefForm=new ArrayList();
								qltyChkForm=new ArrayList();
								String appFormQry = "select DEAL_APPLICATION_FORM_NO from cr_Deal_dtl where DEAL_APPLICATION_FORM_NO is not null and DEAL_APPLICATION_FORM_NO<>''";
								String loanRefQry = "select LOAN_REFERENCE_NO from cr_loan_dtl where LOAN_REFERENCE_NO is not null and LOAN_REFERENCE_NO<>''";
								String qualityChkQry ="SELECT DISTINCT D.DEAL_APPLICATION_FORM_NO FROM CR_DEAL_DTL D "
										+ " INNER JOIN cr_deal_loan_dtl DL ON D.DEAL_ID=DL.DEAL_ID AND DL.DEAL_SANCTION_AMOUNT>ifnull(DL.DEAL_UTILIZED_AMOUNT, 0) "
										+ " INNER JOIN GCD_CUSTOMER_M G ON G.CUSTOMER_ID=D.GCD_CUSTOMER_ID "
										+ " INNER JOIN CR_PRODUCT_M P ON P.PRODUCT_ID=DL.DEAL_PRODUCT AND (P.ONE_DEAL_ONE_LOAN='N' OR (P.ONE_DEAL_ONE_LOAN='Y' "
										+ " AND D.DEAL_ID NOT IN (SELECT LOAN_DEAL_ID FROM CR_LOAN_DTL L WHERE L.LOAN_DEAL_ID=D.DEAL_ID AND L.REC_STATUS NOT IN ('X','L')))) "
										+ " WHERE D.REC_STATUS='A'" ;
								
								mainAppForm = ConnectionReportDumpsDAO.sqlSelect(appFormQry);
								loanRefForm = ConnectionReportDumpsDAO.sqlSelect(loanRefQry);
								qltyChkForm = ConnectionReportDumpsDAO.sqlSelect(qualityChkQry);
								
								for(int r=0;r<mainAppForm.size();r++){
									vst.add(mainAppForm.get(r).toString().replace("[","").replace("]",""));
								}
								
								for(int r=0;r<loanRefForm.size();r++){
									bst.add(loanRefForm.get(r).toString().replace("[","").replace("]",""));
								}
								
								for(int r=0;r<qltyChkForm.size();r++){
									qst.add(qltyChkForm.get(r).toString().replace("[","").replace("]",""));
								}

								//Loan Details
								if(p==0 && dataList.size()>0)
								{ 
									appFormNo=new ArrayList();
									for(i=0;i<dataList.size();i++)//rows loop
									{	     	  
										subList=(ArrayList)dataList.get(i);//for fetching all cols fields
										if(subList.size()>0)
										{

											//LOAN_REFERENCE_NO - varchar(20)
											if(!CommonFunction.checkNull(subList.get(0)).equalsIgnoreCase("")){
												if(appFormNo.contains(subList.get(0))||bst.contains(subList.get(0))){
													errorPos="("+(i+1)+",1)";
													validateMsg.append("\nLOAN_REFERENCE_NO Already Exists at '"+errorPos+"'");
													data_Error.add(errorPos);
													ValidateFlag=true;

												}else if(!vst.contains(subList.get(0))){
													errorPos="("+(i+1)+",1)";
													validateMsg.append("\nNo Deal captured for LOAN_REFERENCE_NO at '"+errorPos+"'");
													data_Error.add(errorPos);
													ValidateFlag=true;
												}else if(!qst.contains(subList.get(0))){
													errorPos="("+(i+1)+",1)";
													validateMsg.append("\nDeal have not Passed all Required stages '"+errorPos+"'");
													data_Error.add(errorPos);
													ValidateFlag=true;
												}	
												else{
													if(CommonFunction.checkNull(subList.get(0)).length() > 20){
														errorPos="("+(i+1)+",1)";
														validateMsg.append("\nLOAN_REFERENCE_NO size exceeds max length 20 '"+errorPos+"'");
														size_Error.add(errorPos);
														ValidateFlag=true;
													}
													else{
														appFormNo.add(subList.get(0));
													}
												}
											}
											else{
												errorPos="("+(i+1)+",1)";
												validateMsg.append("\nLOAN_REFERENCE_NO Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											} 

											//CUSTOMER_NAME varchar(155) 
											if(!CommonFunction.checkNull(subList.get(1)).equalsIgnoreCase("")){
												
												String custNameQry = "select CUSTOMER_NAME from gcd_customer_m where Customer_id=(select max(deal_customer_id) from cr_deal_dtl "
														+ "where DEAL_APPLICATION_FORM_NO='"+subList.get(0)+"')";
												String custName=ConnectionReportDumpsDAO.singleReturn(custNameQry);
												
												if(!CommonFunction.checkNull(subList.get(1)).equalsIgnoreCase(custName)){
													errorPos="("+(i+1)+",1)";
													validateMsg.append("\nCustomerName at Deal and Loan Should be same '"+errorPos+"'");
													data_Error.add(errorPos);
													ValidateFlag=true;
												}
												else{
													if(CommonFunction.checkNull(subList.get(1)).length() > 155){
														errorPos="("+(i+1)+",2)";
														validateMsg.append("\nCUSTOMER_NAME size exceeds max length 155 '"+errorPos+"'");
														size_Error.add(errorPos);
														ValidateFlag=true;
													}
												}
											}
											else{
												errorPos="("+(i+1)+",2)";
												validateMsg.append("\nCUSTOMER_NAME Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}


											//LOAN_LOAN_AMOUNT decimal(22,4)
											if(!CommonFunction.checkNull(subList.get(2)).equalsIgnoreCase("")){
												
												String SancAmtQry = "select DEAL_SANCTION_AMOUNT from cr_deal_loan_dtl where deal_id=(select deaL_id from cr_deaL_dtl "
														+ "where DEAL_APPLICATION_FORM_NO='"+subList.get(0)+"')";
												String SancAmt=ConnectionReportDumpsDAO.singleReturn(SancAmtQry);
												
												Double Deal_Sanction_amount=Double.parseDouble(SancAmt);
												int y=(int)Math.round(Deal_Sanction_amount);

												if(y<Integer.parseInt(subList.get(2).toString())){
													errorPos="("+(i+1)+",3)";
													validateMsg.append("\nLoan_Sanction_Amount can't be greater than Deal_sanction_Amount '"+errorPos+"'");
													data_Error.add(errorPos);
													ValidateFlag=true;
												}
												else{
													if(CommonFunction.checkNull(subList.get(2)).length() >10){
														errorPos="("+(i+1)+",3)";
														validateMsg.append("\nLOAN_LOAN_AMOUNT size exceeds max length 10 '"+errorPos+"'");
														size_Error.add(errorPos);
														ValidateFlag=true;
													}
													else if(!StringUtils.isNumeric(CommonFunction.checkNull(subList.get(2)))){
														errorPos="("+(i+1)+",3)";
														validateMsg.append("\nLOAN_LOAN_AMOUNT should be a Number '"+errorPos+"'");
														type_Error.add(errorPos);
														ValidateFlag=true;
													}
													else{
														tempLoan.add(Integer.parseInt((subList.get(2)).toString()));
													}
												}
											}
											else{
												errorPos="("+(i+1)+",3)";
												validateMsg.append("\nLOAN_LOAN_AMOUNT Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}
											
											logger.info(ijk+", LOAN_LOAN_AMT::"+subList.get(2)+", tempLoan::"+tempLoan);
											
											//LOAN_TENURE int(5)
											if(!CommonFunction.checkNull(subList.get(3)).equalsIgnoreCase("")){
												
												String TenureQry = "select DEAL_TENURE from cr_deal_loan_dtl where deal_id=(select deaL_id from cr_deaL_dtl "
														+ "where DEAL_APPLICATION_FORM_NO='"+subList.get(0)+"')";
												String SancTenure=ConnectionReportDumpsDAO.singleReturn(TenureQry);
												
												int DealTenure=Integer.parseInt(SancTenure);
												if(DealTenure<Integer.parseInt(subList.get(3).toString())){
													errorPos="("+(i+1)+",4)";
													validateMsg.append("\nLoan_Tenure can't be greater than Deal_Tenure '"+errorPos+"'");
													data_Error.add(errorPos);
													ValidateFlag=true;
												}
												else{
													if(CommonFunction.checkNull(subList.get(3)).length() > 5){
														errorPos="("+(i+1)+",4)";
														validateMsg.append("\nLOAN_TENURE size exceeds max length 5 '"+errorPos+"'");
														size_Error.add(errorPos);
														ValidateFlag=true;
													}
													if(!StringUtils.isNumeric(CommonFunction.checkNull(subList.get(3)))){
														errorPos="("+(i+1)+",4)";
														validateMsg.append("\nLOAN_TENURE should be a Number '"+errorPos+"'");
														type_Error.add(errorPos);
														ValidateFlag=true;
													}
												}
											}
											else{
												errorPos="("+(i+1)+",4)";
												validateMsg.append("\nDEAL_TENURE Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}


										}
									}  

									appFormNo=null;

									//checking if error occured
									if(ValidateFlag){
										msg="F";
										sheetInfoMsg=new StringBuffer();
										sheetInfoMsg.append("\nFailed at 'Loan Detail' Sheet");
										if(blank_Error.size()>0)
											sheetInfoMsg.append("\nFields Should not Be blank at '"+blank_Error+"' ");
										if(size_Error.size()>0)
											sheetInfoMsg.append("\nFields Exceeding max size at '"+size_Error+"' ");
										if(type_Error.size()>0)
											sheetInfoMsg.append("\nFields of Incorrect DataType at '"+type_Error+"' ");
										if(data_Error.size()>0)
											sheetInfoMsg.append("\nFields Having Invalid Data at '"+data_Error+"' ");
										sheetInfoMsg.append("\nFor more information kindly check Error Log");

										/*request.setAttribute("ValMsg",validateMsg);
								session.setAttribute("InfoMsg",sheetInfoMsg);*/

										break;
									}

								}

								//Charge Details
								if(p==1 && dataList.size()>0)
								{ 
									int rv=0;
									panNo=new ArrayList();
									aadharNo=new ArrayList();

									for(i=0;i<dataList.size();i++)//rows loop
									{	     	  
										subList=(ArrayList)dataList.get(i);//for fetching all cols fields
										if(subList.size()>0)
										{
											//LOAN_REFERENCE_NO - varchar(20)					
											if(!CommonFunction.checkNull(subList.get(0)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(0)).length() > 20){
													errorPos="("+(i+1)+",1)";
													validateMsg.append("\nLOAN_REFERENCE_NO size exceeds max length 20 '"+errorPos+"'");
													size_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",1)";
												validateMsg.append("\nLOAN_REFERENCE_NO Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}
											
											logger.info(rv+", tempLoan::"+tempLoan.get(rv));
											
											
											//Processing_Fees decimal(22,4)
											if(!CommonFunction.checkNull(subList.get(1)).equalsIgnoreCase("")){
												String chkLoanAmtQry = "select ceil(((((c.charge_amount)*'"+tempLoan.get(rv)+"')/100)+(((c.charge_amount)*'"+tempLoan.get(rv)+"')/100)*(c.TAX_RATE1+c.TAX_RATE2)/100)) from cr_deal_dtl a"
														+ " INNER join cr_deal_loan_dtl b on a.deal_id=b.deal_id"
														+ " INNER join com_charges_m c on b.deaL_product=c.product_id "
														+ " where a.deal_application_form_no='"+subList.get(0)+"' and c.charge_code='106' limit 1";
												String chkLoanAmt=ConnectionReportDumpsDAO.singleReturn(chkLoanAmtQry);
												//logger.info("Query::"+chkLoanAmtQry+", chkLoanAmt::"+chkLoanAmt);

												if(!chkLoanAmt.equalsIgnoreCase((subList.get(1).toString()))){
													errorPos="("+(i+1)+",2)";
													validateMsg.append("\nnProcessing_Fees should be equal to '"+chkLoanAmt+"' at '"+errorPos+"'");
													data_Error.add(errorPos);
													ValidateFlag=true;
												}
												else{/*

													if(CommonFunction.checkNull(subList.get(1)).length() > 10){
														errorPos="("+(i+1)+",2)";
														validateMsg.append("\nProcessing_Fees size exceeds max length 10 '"+errorPos+"'");
														size_Error.add(errorPos);
														ValidateFlag=true;
													}
													if(!StringUtils.isNumeric(CommonFunction.checkNull(subList.get(1)))){
														errorPos="("+(i+1)+",2)";
														validateMsg.append("\nProcessing_Fees should be a Number '"+errorPos+"'");
														type_Error.add(errorPos);
														ValidateFlag=true;
													}
												*/}
											}
											else{
												errorPos="("+(i+1)+",2)";
												validateMsg.append("\nProcessing_Fees Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}
											
											//Stamping_Charge
											if(!CommonFunction.checkNull(subList.get(2)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(2)).length() > 10){
													errorPos="("+(i+1)+",3)";
													validateMsg.append("\nStamping_Charge size exceeds max length 10 '"+errorPos+"'");
													size_Error.add(errorPos);
													ValidateFlag=true;
												}
												if(!StringUtils.isNumeric(CommonFunction.checkNull(subList.get(2)))){
													errorPos="("+(i+1)+",3)";
													validateMsg.append("\nStamping_Charge should be a Number '"+errorPos+"'");
													type_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",3)";
												validateMsg.append("\nStamping_Charge Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}
											

										}
									rv++;
									}  

									panNo=null;
									aadharNo=null;

									//checking if error occured
									if(ValidateFlag){
										msg="F";
										sheetInfoMsg=new StringBuffer();
										sheetInfoMsg.append("\nFailed at 'Charges Details' Sheet");
										if(blank_Error.size()>0)
											sheetInfoMsg.append("\nFields Should not Be blank at '"+blank_Error+"' ");
										if(fill_Error.size()>0)
											sheetInfoMsg.append("\nFields filled for inappropriate CustomerType at '"+fill_Error+"' ");
										if(size_Error.size()>0)
											sheetInfoMsg.append("\nFields Exceeding max size at '"+size_Error+"' ");
										if(type_Error.size()>0)
											sheetInfoMsg.append("\nFields of Incorrect DataType at '"+type_Error+"' ");
										if(data_Error.size()>0)
											sheetInfoMsg.append("\nFields Having Invalid Data at '"+data_Error+"' ");
										sheetInfoMsg.append("\nFor more information kindly check Error Log");

										/*request.setAttribute("ValMsg",validateMsg);
								session.setAttribute("InfoMsg",sheetInfoMsg);*/
										break;
									}

								}

								// Disbursal Details
								if(p==2 && dataList.size()>0)
								{ 
									appFormNo=new ArrayList();
									for(i=0;i<dataList.size();i++)//rows loop
									{	     	  
										subList=(ArrayList)dataList.get(i);//for fetching all cols fields
										if(subList.size()>0)
										{
											//Application_Form_No - varchar(20)					
											if(!CommonFunction.checkNull(subList.get(0)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(0)).length() > 20){
													errorPos="("+(i+1)+",1)";
													validateMsg.append("\nLOAN_REFERENCE_NO size exceeds max length 20 '"+errorPos+"'");
													size_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",1)";
												validateMsg.append("\nLOAN_REFERENCE_NO Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}
											
											//payee_bank int(10)
											if(!CommonFunction.checkNull(subList.get(1)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(1)).length() > 10){
													errorPos="("+(i+1)+",2)";
													validateMsg.append("\npayee_bank size exceeds max length 10 '"+errorPos+"'");
													size_Error.add(errorPos);
													ValidateFlag=true;
												}
												if(!StringUtils.isNumeric(CommonFunction.checkNull(subList.get(1)))){
													errorPos="("+(i+1)+",2)";
													validateMsg.append("\npayee_bank should be a Number '"+errorPos+"'");
													type_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",2)";
												validateMsg.append("\npayee_bank Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}
											
											//payee_branch int(10)
											if(!CommonFunction.checkNull(subList.get(2)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(2)).length() > 10){
													errorPos="("+(i+1)+",3)";
													validateMsg.append("\npayee_branch size exceeds max length 10 '"+errorPos+"'");
													size_Error.add(errorPos);
													ValidateFlag=true;
												}
												if(!StringUtils.isNumeric(CommonFunction.checkNull(subList.get(2)))){
													errorPos="("+(i+1)+",3)";
													validateMsg.append("\npayee_branch should be a Number '"+errorPos+"'");
													type_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",3)";
												validateMsg.append("\npayee_branch Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}
											
											//payee_acc_no varchar(50)
											if(!CommonFunction.checkNull(subList.get(3)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(3)).length() > 50){
													errorPos="("+(i+1)+",4)";
													validateMsg.append("\npayee_acc_no size exceeds max length 50 '"+errorPos+"'");
													size_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",4)";
												validateMsg.append("\npayee_acc_no Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}
											
											//payee_ifsc varchar(20)
											if(!CommonFunction.checkNull(subList.get(4)).equalsIgnoreCase("")){
												if(CommonFunction.checkNull(subList.get(4)).length() > 20){
													errorPos="("+(i+1)+",5)";
													validateMsg.append("\npayee_ifsc size exceeds max length 20 '"+errorPos+"'");
													size_Error.add(errorPos);
													ValidateFlag=true;
												}
											}
											else{
												errorPos="("+(i+1)+",5)";
												validateMsg.append("\npayee_ifsc Can not be Left Blank '"+errorPos+"'");
												blank_Error.add(errorPos);
												ValidateFlag=true;
											}
										}
									}  

									appFormNo=null;

									//checking if error occured
									if(ValidateFlag){
										msg="F";
										sheetInfoMsg=new StringBuffer();
										sheetInfoMsg.append("\nFailed at 'Disbursal Details' Sheet");
										if(blank_Error.size()>0)
											sheetInfoMsg.append("\nFields Should not Be blank at '"+blank_Error+"' ");
										if(size_Error.size()>0)
											sheetInfoMsg.append("\nFields Exceeding max size at '"+size_Error+"' ");
										if(type_Error.size()>0)
											sheetInfoMsg.append("\nFields of Incorrect DataType at '"+type_Error+"' ");
										if(data_Error.size()>0)
											sheetInfoMsg.append("\nFields Having Invalid Data at '"+data_Error+"' ");
										sheetInfoMsg.append("\nFor more information kindly check Error Log");

										/*request.setAttribute("ValMsg",validateMsg);
								session.setAttribute("InfoMsg",sheetInfoMsg);*/
										break;
									}
								}

							}

						}
						else{
							logger.info("Virender:::Function id is not in correct");
						}

					}
					catch (Exception ex) {
						System.out.println(ex);
						msg="F";
						sheetInfoMsg=new StringBuffer();
						
					/*	sheetInfoMsg.deleteCharAt(0);
						validateMsg.deleteCharAt(0);
						*/
						sheetInfoMsg.append("\nError Occured");
						//validateMsg.append("\nSome Error Occured due to invalid sheet uploaded.\nKindly Upload A Valid Sheet");
					}
				}
			}
			catch(Exception e)  {
				e.printStackTrace();
			}
			if(!CommonFunction.checkNull(sheetInfoMsg).equalsIgnoreCase(""))
				newMsg=msg+"~"+sheetInfoMsg+"~"+validateMsg;
			else
				newMsg=msg;
			return newMsg;
			//return msg;
		}

	//Virender Code for Storing Data
	public boolean readExcelforLOSUpload(HttpServletRequest request,HttpServletResponse response,String strFileName,String userID,String makerDate,String BranchId)
	{
		logger.info("In readExcelforLOSUpload() method ");
		HttpSession   session= null;
		ArrayList alFinalQry= new ArrayList();
		ArrayList dataList=null;
		ArrayList arr=null;
		ArrayList subList= null;    
		int noOfRejectedRecord =0;
		int noOfUploadedRecord=0;
		int rowTotalNum = 0;
		String msg="";    
		File objFile1=null;
		boolean status=false;
		String userId="";
		String bDate=""; 
		String userBranch="";
		boolean insertStatus=false;	  
		PrepStmtObject insertPrepStmtObject=null;
		String query=null;
		String strPath=null;
		Workbook workbook =null;
		Map workSheets=new HashMap();
		String batchId="0";
		int totColls=0;
		try 
		{      
			session     = request.getSession(false);
			UserObject userobj=(UserObject)session.getAttribute("userobject");

			rowTotalNum=countLine(strFileName);
			if(userobj!=null)
			{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
				userBranch=userobj.getBranchId();
			}
			
			session.removeAttribute("vresult");
			if(!strFileName.equals(""))
			{
				query="SELECT  PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='LOS_UPLOAD_PATH'";

				strPath=ConnectionDAO.singleReturn(query);
				strPath=strPath + "/" + strFileName;
				objFile1=new File(strPath);
				query=null;    	  
				try 
				{  
					StringBuffer bufBatchInsSql = new StringBuffer();
					bufBatchInsSql.append("INSERT INTO cr_IndiLends_Loan_upload_summary(file_name,Total_no_of_records,maker_id,maker_date)");
					bufBatchInsSql.append("  VALUES ( ");
					bufBatchInsSql.append(" ?," );//file name
					bufBatchInsSql.append(" ?," );//Total_no_of_records
					bufBatchInsSql.append(" ?," );//maker_id
					bufBatchInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') " ); //MAKER_DATE 
					bufBatchInsSql.append(" )" );     
					insertPrepStmtObject = new PrepStmtObject();
					insertPrepStmtObject.addString(strFileName);
					insertPrepStmtObject.addInt(rowTotalNum);
					insertPrepStmtObject.addString(userId);

					if(CommonFunction.checkNull(bDate).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(bDate);

					insertPrepStmtObject.setSql(bufBatchInsSql.toString());
					alFinalQry.add(insertPrepStmtObject);    						
					insertPrepStmtObject=null;    							

					insertStatus=ConnectionDAO.sqlInsUpdDeletePrepStmt(alFinalQry); 

					String vqry="select max(IL_ID) from  cr_IndiLends_Loan_upload_summary where file_name='"+strFileName+"' and maker_id='"+userID+"' ";			
					batchId=ConnectionDAO.singleReturn(vqry);

					workbook = Workbook.getWorkbook(new File(strPath));
					String sheetName[]=workbook.getSheetNames();
					Sheet sheet;
					Cell xlsCell;
					Cell[] cell;

					for(int p=0; p<sheetName.length; p++)
					{
						sheet = workbook.getSheet(p);
						dataList= new ArrayList();

						totColls=sheet.getRow(0).length;

						for(int i=1; i<sheet.getRows(); i++)
						{
							cell = sheet.getRow(i);
							arr = new ArrayList();
							for(int j=0; j<totColls; j++)
							{                 	
								xlsCell = sheet.getCell(j,i);
								//arr.add(xlsCell.getContents().toString());
								if(!CommonFunction.checkNull(sheet.getCell(j,i).getContents().toString()).equalsIgnoreCase(""))
								{
									arr.add(xlsCell.getContents().toString());
								} 
								else
								{
									arr.add("");
								}
							} 
							dataList.add(arr);	 
						}
						workSheets.put(String.valueOf(p), dataList);
					}
				}

				catch(Exception e) 
				{e.printStackTrace();}
				
				session = request.getSession();
				String functionId=(String)session.getAttribute("functionId");
				
				if(CommonFunction.checkNull(functionId).equalsIgnoreCase("10000827")){

					for(int p=0;p<workSheets.size();p++)
					{	 
						int subListLen;
						dataList=(ArrayList)workSheets.get(String.valueOf(p));
						StringBuffer bufInsSql = new StringBuffer();

						// Lead Entry detail
						if(p==0 && dataList.size()>0)
						{ 
							logger.info("Virender Lead Entry detail");
							bufInsSql=new StringBuffer();
							bufInsSql.append("insert into cr_indiaLends_deal_dtl(DEAL_APPLICATION_FORM_NO,DEAL_RM,DEAL_SOURCE_TYPE,DEAL_SOURCE_NAME,DEAL_CATEGORY,DEAL_BRANCH,"
									+ "DEAL_DATE,DEAL_ENCODED_DATE,DEAL_INITIATION_DATE,DEAL_FORWARDED_DATE,REC_STATUS,REFRESH_FLAG,MAKER_ID,MAKER_DATE)");
							bufInsSql.append("  VALUES ( ");
							bufInsSql.append(" ?," );//DEAL_APPLICATION_FORM_NO
							bufInsSql.append(" ?," );//DEAL_RM
							bufInsSql.append(" ?," );//DEAL_SOURCE_TYPE
							bufInsSql.append(" ?," );//DEAL_SOURCE_NAME
							bufInsSql.append(" ?," );//DEAL_CATEGORY
							bufInsSql.append(" ?," );//DEAL_BRANCH
							bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"'), " );//DEAL_DATE
							bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"'), " );//DEAL_ENCODED_DATE	
							bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"')," );//DEAL_INITIATION_DATE
							bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"')," );//DEAL_FORWARDED_DATE
							bufInsSql.append(" ?," );//REC_STATUS
							bufInsSql.append(" ?," );//REFRESH_FLAG
							bufInsSql.append(" ?," );//MAKER_ID
							bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') " );//MAKER_DATE
							bufInsSql.append(" )" );  
							for(int i=0;i<dataList.size();i++)
							{	     	  
								subList=(ArrayList)dataList.get(i);
								subListLen=subList.size();
								insertPrepStmtObject = new PrepStmtObject();

								for(int k=0;k<subListLen;k++){

									logger.info("subList.get(k) ==> "+ subList.get(k));
									if(CommonFunction.checkNull(CommonFunction.checkNull(subList.get(k))).equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
									else
										insertPrepStmtObject.addString(CommonFunction.checkNull(subList.get(k)));	

								}

								insertPrepStmtObject.addString(bDate);//DEAL_DATE
								insertPrepStmtObject.addString(bDate);//DEAL_ENCODED_DATE
								insertPrepStmtObject.addString(bDate);//DEAL_INITIATION_DATE
								insertPrepStmtObject.addString(bDate);//DEAL_FORWARDED_DATE
								insertPrepStmtObject.addString("F");//REC_STATUS
								insertPrepStmtObject.addString("NNNNNNNNNNNNNNN");//REFRESH_FLAG
								insertPrepStmtObject.addString(userId);//MAKER_ID
								insertPrepStmtObject.addString(bDate);//MAKER_DATE

								insertPrepStmtObject.setSql(bufInsSql.toString());
								logger.info("IN readExcelforBounceUpload() insert query1 : "+ insertPrepStmtObject.printQuery());
								alFinalQry.add(insertPrepStmtObject);    						
								insertPrepStmtObject=null; 
							}  
						}


						//Cust Details
						if(p==1 && dataList.size()>0)
						{ 
							logger.info("Virender Cust Details");
							bufInsSql = new StringBuffer();
							bufInsSql.append("Insert into cr_indiaLends_deal_customer_m (DEAL_APPLICATION_FORM_NO, CUSTOMER_ID, DEAL_CUSTOMER_ROLE_TYPE, CUSTOMER_TYPE, CUSTOMER_FNAME,"
									+ " CUSTOMER_LNAME, GENDER, CUSTOMER_CATEGORY, CUSTOMER_CONSTITUTION, CUSTOMER_DOB, CASTE_CATEGORY, FATHER_HUSBAND_NAME, MARITAL_STATUS, "
									+ "CUSTOMER_EMAIL, CUSTMER_PAN, EDU_DETAIL, UID_NO, CUSTOMER_GROUP_DESC, CUSTOMER_REGISTRATION_NO, CUSTOMER_BUSINESS_SEGMENT, CUSTOMER_INDUSTRY,"
									+ " CUSTOMER_SUB_INDUSTRY, NATURE_OF_BUSINESS, OTHOR_RELATIONSHIP_TYPE, BANK_NAME, BANK_BRANCH, ACCOUNT_NO, ACCOUNT_TYPE, REC_STATUS, MAKER_ID, MAKER_DATE)");
							bufInsSql.append(" values (");
							bufInsSql.append("?, ");//DEAL_APPLICATION_FORM_NO
							bufInsSql.append("?, ");//CUSTOMER_ID
							bufInsSql.append("?, ");//DEAL_CUSTOMER_ROLE_TYPE
							bufInsSql.append("?, ");//CUSTOMER_TYPE
							bufInsSql.append("?, ");//CUSTOMER_FNAME
							bufInsSql.append("?, ");//CUSTOMER_LNAME
							bufInsSql.append("?, ");//GENDER
							bufInsSql.append("?, ");//CUSTOMER_CATEGORY
							bufInsSql.append(" ?," );//CUSTOMER_CONSTITUTION
							bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"'), " );//CUSTOMER_DOB
							bufInsSql.append("?, ");//CASTE_CATEGORY
							bufInsSql.append("?, ");//FATHER_HUSBAND_NAME
							bufInsSql.append("?, ");//MARITAL_STATUS
							bufInsSql.append("?, ");//Email ID
							bufInsSql.append("?, ");//CUSTMER_PAN
							bufInsSql.append("?, ");//EDU_DETAIL
							bufInsSql.append("?, ");//UID NO
							bufInsSql.append("?, ");//CUSTOMER_GROUP_DESC
							bufInsSql.append("?, ");//CUSTOMER_REGISTRATION_NO	
							bufInsSql.append("?, ");//CUSTOMER_BUSINESS_SEGMENT
							bufInsSql.append("?, ");//CUSTOMER_INDUSTRY
							bufInsSql.append("?, ");//CUSTOMER_SUB_INDUSTRY	
							bufInsSql.append("?, ");//NATURE_OF_BUSINESS
							bufInsSql.append("?, ");//OTHOR_RELATIONSHIP_TYPE	
							bufInsSql.append("?," );//BANK_NAME
							bufInsSql.append("?," );//BANK_BRANCH
							bufInsSql.append("?," );//ACCOUNT_NO
							bufInsSql.append("?," );//ACCOUNT_TYPE
							bufInsSql.append("?, ");//REC_STATUS
							bufInsSql.append("?, ");//MAKER_ID
							bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"')" );//MAKER_DATE
							bufInsSql.append(" )" ); 
							for(int i=0;i<dataList.size();i++)
							{	     	  
								subList=(ArrayList)dataList.get(i);
								subListLen=subList.size();
								insertPrepStmtObject = new PrepStmtObject();

								for(int k=0;k<subListLen;k++){

									logger.info("subList.get(k) ==> "+ subList.get(k));
									if(CommonFunction.checkNull(CommonFunction.checkNull(subList.get(k))).equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
									else
										insertPrepStmtObject.addString(CommonFunction.checkNull(subList.get(k)));	

								}
								insertPrepStmtObject.addString("A");//REC_STATUS
								insertPrepStmtObject.addString(userId);//MAKER_ID
								insertPrepStmtObject.addString(bDate);//MAKER_DATE

								insertPrepStmtObject.setSql(bufInsSql.toString());
								logger.info("IN readExcelforBounceUpload() insert query1 : "+ insertPrepStmtObject.printQuery());
								alFinalQry.add(insertPrepStmtObject);    						
								insertPrepStmtObject=null; 
							}  

						}

						//Address Details
						if(p==2 && dataList.size()>0)
						{  
							logger.info("Virender Address Details");
							bufInsSql =	new StringBuffer();
							bufInsSql.append("insert into cr_indiaLends_deal_address_m(DEAL_APPLICATION_FORM_NO,BPID,ADDRESS_TYPE,ADDRESS_LINE1,ADDRESS_LINE2,STATE,DISTRICT,COUNTRY,PINCODE,PRIMARY_PHONE,LANDMARK,NO_OF_YEARS,NO_OF_MONTHS"
									+ ",REC_STATUS,MAKER_ID,MAKER_DATE)");
							bufInsSql.append(" values ( ");
							bufInsSql.append(" ?," );//DEAL_APPLICATION_FORM_NO
							bufInsSql.append(" ?," );//BPID
							bufInsSql.append(" ?," );//ADDRESS_TYPE
							bufInsSql.append(" ?," );//ADDRESS_LINE1
							bufInsSql.append(" ?," );//ADDRESS_LINE2
							bufInsSql.append(" ?," );//STATE
							bufInsSql.append(" ?," );//DISTRICT
							bufInsSql.append(" ?," );//COUNTRY
							bufInsSql.append(" ?," );//PINCODE
							bufInsSql.append(" ?," );//PRIMARY_PHONE
							bufInsSql.append(" ?," );//Landmark
							bufInsSql.append(" ?," );//NO_OF_YEARS
							bufInsSql.append(" ?," );//NO_OF_Months
							bufInsSql.append(" ?," );//REC_STATUS
							bufInsSql.append(" ?," );//MAKER_ID
							bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"')" );//MAKER_DATE
							bufInsSql.append(" )" ); 
							for(int i=0;i<dataList.size();i++)
							{	     	  
								subList=(ArrayList)dataList.get(i);
								subListLen=subList.size();
								insertPrepStmtObject = new PrepStmtObject();

								for(int k=0;k<subListLen;k++){

									logger.info("subList.get(k) ==> "+ subList.get(k));
									if(CommonFunction.checkNull(CommonFunction.checkNull(subList.get(k))).equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
									else
										insertPrepStmtObject.addString(CommonFunction.checkNull(subList.get(k)));	

								}
								insertPrepStmtObject.addString("P");//REC_STATUS
								insertPrepStmtObject.addString(userId);//MAKER_ID
								insertPrepStmtObject.addString(bDate);//MAKER_DATE

								insertPrepStmtObject.setSql(bufInsSql.toString());
								logger.info("IN readExcelforBounceUpload() insert query1 : "+ insertPrepStmtObject.printQuery());
								alFinalQry.add(insertPrepStmtObject);    						
								insertPrepStmtObject=null; 
							}  
						}

						//Loan Details
						if(p==3 && dataList.size()>0)
						{ 
							logger.info("Virender Loan Details");
							bufInsSql=new StringBuffer();
							/*bufInsSql.append("insert into cr_indiLends_deal_loan_dtl(DEAL_APPLICATION_FORM_NO,DEAL_PRODUCT,DEAL_SCHEME,DEAL_TENURE,DEAL_LOAN_AMOUNT,DEAL_RATE_TYPE"
								+ ",DEAL_RATE_METHOD,DEAL_BASE_RATE_TYPE,DEAL_BASE_RATE,DEAL_FINAL_RATE,DEAL_REPAYMENT_TYPE,DEAL_INSTALLMENT_TYPE,DEAL_INSTALLMENT_MODE,DEAL_REPAYMENT_FREQ"
								+ ",DEAL_INT_CALC_FROM,INT_COMP_FREQ,DEAL_CREDIT_PERIOD,DEAL_REPAYMENT_MODE,DEAL_REPAY_EFF_DATE,DEAL_DUE_DAY,NEXT_DUE_DATE,DEAL_LOAN_PURPOSE,DEAL_SECTOR_TYPE"
								+ ",REC_STATUS,MAKER_ID,MAKER_DATE )");*/

							bufInsSql.append("insert into cr_indiaLends_deal_loan_dtl(DEAL_APPLICATION_FORM_NO,DEAL_PRODUCT,DEAL_SCHEME,DEAL_TENURE,DEAL_LOAN_AMOUNT,DEAL_RATE_TYPE"
									+ ",DEAL_RATE_METHOD,DEAL_BASE_RATE_TYPE,DEAL_FINAL_RATE,DEAL_REPAYMENT_TYPE,DEAL_INSTALLMENT_TYPE,DEAL_INSTALLMENT_MODE,DEAL_REPAYMENT_FREQ"
									+ ",INT_COMP_FREQ,INT_METHOD,DEAL_REPAYMENT_MODE,DEAL_REPAY_EFF_DATE,DEAL_DUE_DAY,DEAL_LOAN_PURPOSE,DEAL_SECTOR_TYPE"
									+ ",REC_STATUS,MAKER_ID,MAKER_DATE )");
							bufInsSql.append(" values ( ");
							bufInsSql.append(" ?," );//DEAL_APPLICATION_FORM_NO
							bufInsSql.append(" ?," );//DEAL_PRODUCT
							bufInsSql.append(" ?," );//DEAL_SCHEME
							bufInsSql.append(" ?," );//DEAL_TENURE
							bufInsSql.append(" ?," );//DEAL_LOAN_AMOUNT
							bufInsSql.append(" ?," );//DEAL_RATE_TYPE
							bufInsSql.append(" ?," );//DEAL_RATE_METHOD
							bufInsSql.append(" ?," );//DEAL_BASE_RATE_TYPE
							bufInsSql.append(" ?," );//DEAL_FINAL_RATE
							bufInsSql.append(" ?," );//DEAL_REPAYMENT_TYPE
							bufInsSql.append(" ?," );//DEAL_INSTALLMENT_TYPE
							bufInsSql.append(" ?," );//DEAL_INSTALLMENT_MODE
							bufInsSql.append(" ?," );//DEAL_REPAYMENT_FREQ
							bufInsSql.append(" ?," );//INT_COMP_FREQ
							bufInsSql.append(" ?," );//INT_METHOD
							bufInsSql.append(" ?," );//DEAL_REPAYMENT_MODE
							bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"'), " );//DEAL_REPAY_EFF_DATE
							bufInsSql.append(" ?," );//DEAL_DUE_DAY
							bufInsSql.append(" ?," );//DEAL_LOAN_PURPOSE
							bufInsSql.append(" ?," );//DEAL_SECTOR_TYPE
							bufInsSql.append(" ?," );//REC_STATUS
							bufInsSql.append(" ?," );//MAKER_ID
							bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') " );//MAKER_DATE
							bufInsSql.append(")" );       
							for(int i=0;i<dataList.size();i++)
							{	     	  
								subList=(ArrayList)dataList.get(i);
								subListLen=subList.size();
								insertPrepStmtObject = new PrepStmtObject();

								for(int k=0;k<subListLen;k++){

									logger.info("subList.get(k) ==> "+ subList.get(k));
									if(CommonFunction.checkNull(CommonFunction.checkNull(subList.get(k))).equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
									else
										insertPrepStmtObject.addString(CommonFunction.checkNull(subList.get(k)));	

								}
								insertPrepStmtObject.addString("P");//REC_STATUS
								insertPrepStmtObject.addString(userId);//MAKER_ID
								insertPrepStmtObject.addString(bDate);//MAKER_DATE

								insertPrepStmtObject.setSql(bufInsSql.toString());
								logger.info("IN readExcelforBounceUpload() insert query1 : "+ insertPrepStmtObject.printQuery());
								alFinalQry.add(insertPrepStmtObject);    						
								insertPrepStmtObject=null; 
							}  
						}

						//Financial Analysis
						if(p==4 && dataList.size()>0)
						{  
							logger.info("Virender Address Details");
							bufInsSql =	new StringBuffer();
							bufInsSql.append("insert into cr_indiaLends_deal_financial_dtl(DEAL_APPLICATION_FORM_NO,DEAL_CUSTOMER_ID,FINANCIAL_MONTH,VERIFICATION_METHOD,"
									+ "SOURCE_TYPE,REC_STATUS,MAKER_ID,MAKER_DATE)");
							bufInsSql.append(" values ( ");
							bufInsSql.append(" ?," );//DEAL_APPLICATION_FORM_NO
							bufInsSql.append(" ?," );//DEAL_CUSTOMER_ID
							bufInsSql.append(" ?," );//FINANCIAL_MONTH
							bufInsSql.append(" ?," );//VERIFICATION_METHOD
							bufInsSql.append(" ?," );//SOURCE_TYPE						
							bufInsSql.append(" ?," );//REC_STATUS
							bufInsSql.append(" ?," );//MAKER_ID
							bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') " );//MAKER_DATE
							bufInsSql.append(" )" ); 
							for(int i=0;i<dataList.size();i++)
							{	     	  
								subList=(ArrayList)dataList.get(i);
								subListLen=subList.size();
								insertPrepStmtObject = new PrepStmtObject();

								for(int k=0;k<subListLen;k++){

									logger.info("subList.get(k) ==> "+ subList.get(k));
									if(CommonFunction.checkNull(CommonFunction.checkNull(subList.get(k))).equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
									else
										insertPrepStmtObject.addString(CommonFunction.checkNull(subList.get(k)));	

								}
								insertPrepStmtObject.addString("P");//REC_STATUS
								insertPrepStmtObject.addString(userId);//MAKER_ID
								insertPrepStmtObject.addString(bDate);//MAKER_DATE

								insertPrepStmtObject.setSql(bufInsSql.toString());
								logger.info("IN readExcelforBounceUpload() insert query1 : "+ insertPrepStmtObject.printQuery());
								alFinalQry.add(insertPrepStmtObject);    						
								insertPrepStmtObject=null; 
							}  

						}

						//IFund Flow
						/*				if(p==5 && dataList.size()>0)
					{	  
						logger.info("Virender Address Details");
						bufInsSql =	new StringBuffer();
						bufInsSql.append("insert into cr_indiaLends_deal_fund_flow_dtl(DEAL_APPLICATION_FORM_NO,DEAL_CUSTOMER_ID,BANK_NAME,BANK_BRANCH,ACCOUNT_NO,ACCOUNT_TYPE,"
								+ "STATEMENT_MONTH,STATEMENT_YEAR,REC_STATUS,MAKER_ID,MAKER_DATE)");
						bufInsSql.append(" values ( ");
						bufInsSql.append(" ?," );//DEAL_APPLICATION_FORM_NO
						bufInsSql.append(" ?," );//DEAL_CUSTOMER_ID
						bufInsSql.append(" ?," );//BANK_NAME
						bufInsSql.append(" ?," );//BANK_BRANCH
						bufInsSql.append(" ?," );//ACCOUNT_NO
						bufInsSql.append(" ?," );//ACCOUNT_TYPE
						bufInsSql.append(" ?," );//STATEMENT_MONTH
						bufInsSql.append(" ?," );//STATEMENT_YEAR
						bufInsSql.append(" ?," );//REC_STATUS
						bufInsSql.append(" ?," );//MAKER_ID
						bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') " );//MAKER_DATE
						bufInsSql.append(" )" ); 
						for(int i=0;i<dataList.size();i++)
						{	     	  
							subList=(ArrayList)dataList.get(i);
							subListLen=subList.size();
							insertPrepStmtObject = new PrepStmtObject();

							for(int k=0;k<subListLen;k++){

								logger.info("subList.get(k) ==> "+ subList.get(k));
								if(CommonFunction.checkNull(CommonFunction.checkNull(subList.get(k))).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(CommonFunction.checkNull(subList.get(k)));	

							}
							insertPrepStmtObject.addString("P");//REC_STATUS
							insertPrepStmtObject.addString(userId);//MAKER_ID
							insertPrepStmtObject.addString(bDate);//MAKER_DATE

							insertPrepStmtObject.setSql(bufInsSql.toString());
							logger.info("IN readExcelforBounceUpload() insert query1 : "+ insertPrepStmtObject.printQuery());
							alFinalQry.add(insertPrepStmtObject);    						
							insertPrepStmtObject=null; 
						}  

					}
						 */
						//Installment Plan
						if(p==5 && dataList.size()>0)
						{	  
							logger.info("Virender Installment Plan");
							bufInsSql =	new StringBuffer();
							bufInsSql.append("insert into cr_indiaLends_deal_installment_plan(DEAL_APPLICATION_FORM_NO, RECOVERY_TYPE, FROM_INSTL_NO, TO_INSTL_NO,"
									+ " RECOVERY_PERCENT, PRINCIPAL_AMOUNT)");
							bufInsSql.append(" values ( ");
							bufInsSql.append(" ?," );//DEAL_APPLICATION_FORM_NO
							bufInsSql.append(" ?," );//RECOVERY_TYPE
							bufInsSql.append(" ?," );//FROM_INSTL_NO
							bufInsSql.append(" ?," );//TO_INSTL_NO
							bufInsSql.append(" ?," );//RECOVERY_PERCENT
							bufInsSql.append(" ?" );//PRINCIPAL_AMOUNT
							bufInsSql.append(" )" ); 
							for(int i=0;i<dataList.size();i++)
							{	     	  
								subList=(ArrayList)dataList.get(i);
								subListLen=subList.size();
								insertPrepStmtObject = new PrepStmtObject();

								for(int k=0;k<subListLen;k++){

									logger.info("subList.get(k) ==> "+ subList.get(k));
									if(CommonFunction.checkNull(CommonFunction.checkNull(subList.get(k))).equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
									else
										insertPrepStmtObject.addString(CommonFunction.checkNull(subList.get(k)));	
								}

								insertPrepStmtObject.setSql(bufInsSql.toString());
								logger.info("IN readExcelforBounceUpload() insert query1 : "+ insertPrepStmtObject.printQuery());
								alFinalQry.add(insertPrepStmtObject);    						
								insertPrepStmtObject=null; 
							}  

						}

						insertStatus=ConnectionDAO.sqlInsUpdDeletePrepStmt(alFinalQry); 

					}

				}
				else if(CommonFunction.checkNull(functionId).equalsIgnoreCase("10000828")){

					for(int p=0;p<workSheets.size();p++)
					{	 
						int subListLen;
						dataList=(ArrayList)workSheets.get(String.valueOf(p));
						StringBuffer bufInsSql = new StringBuffer();

						// Loan Initiation
						if(p==0 && dataList.size()>0)
						{ 
							logger.info("Virender Lead Entry detail");
							bufInsSql=new StringBuffer();
							bufInsSql.append("insert into cr_indiaLend_Loan_Inititation(LOAN_REFERENCE_NO,CUSTOMER_NAME,LOAN_LOAN_AMOUNT,LOAN_TENURE,"
									+ "MAKER_ID,MAKER_DATE)");
							bufInsSql.append("  VALUES ( ");
							bufInsSql.append(" ?," );//LOAN_REFERENCE_NO
							bufInsSql.append(" ?," );//CUSTOMER_NAME
							bufInsSql.append(" ?," );//LOAN_LOAN_AMOUNT
							bufInsSql.append(" ?," );//LOAN_TENURE
							bufInsSql.append(" ?," );//MAKER_ID
							bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') " );//MAKER_DATE
							bufInsSql.append(" )" );  
							for(int i=0;i<dataList.size();i++)
							{	     	  
								subList=(ArrayList)dataList.get(i);
								subListLen=subList.size();
								insertPrepStmtObject = new PrepStmtObject();

								for(int k=0;k<subListLen;k++){

									logger.info("subList.get(k) ==> "+ subList.get(k));
									if(CommonFunction.checkNull(CommonFunction.checkNull(subList.get(k))).equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
									else
										insertPrepStmtObject.addString(CommonFunction.checkNull(subList.get(k)));	

								}
								insertPrepStmtObject.addString(userId);//MAKER_ID
								insertPrepStmtObject.addString(bDate);//MAKER_DATE

								insertPrepStmtObject.setSql(bufInsSql.toString());
								logger.info("IN readExcelforBounceUpload() insert query1 : "+ insertPrepStmtObject.printQuery());
								alFinalQry.add(insertPrepStmtObject);    						
								insertPrepStmtObject=null; 
							}  
						}


						//Charges
						if(p==1 && dataList.size()>0)
						{ 
							logger.info("Virender Cust Details");
							bufInsSql = new StringBuffer();
							bufInsSql.append("Insert into cr_indiaLend_txncharges_dtl (LOAN_REFERENCE_NO,DEAL_CHARGE_FINAL_AMOUNT_PROCESSING,DEAL_CHARGE_FINAL_AMOUNT_STAMPING)");
							bufInsSql.append(" values (");
							bufInsSql.append("?, ");//LOAN_REFERENCE_NO
							bufInsSql.append("?, ");//DEAL_CHARGE_FINAL_AMOUNT_PROCESSING
							bufInsSql.append("? ");//DEAL_CHARGE_FINAL_AMOUNT_STAMPING
							bufInsSql.append(" )" ); 
							for(int i=0;i<dataList.size();i++)
							{	     	  
								subList=(ArrayList)dataList.get(i);
								subListLen=subList.size();
								insertPrepStmtObject = new PrepStmtObject();

								for(int k=0;k<subListLen;k++){

									logger.info("subList.get(k) ==> "+ subList.get(k));
									if(CommonFunction.checkNull(CommonFunction.checkNull(subList.get(k))).equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
									else
										insertPrepStmtObject.addString(CommonFunction.checkNull(subList.get(k)));	

								}

								insertPrepStmtObject.setSql(bufInsSql.toString());
								logger.info("IN readExcelforBounceUpload() insert query1 : "+ insertPrepStmtObject.printQuery());
								alFinalQry.add(insertPrepStmtObject);    						
								insertPrepStmtObject=null; 
							}  

						}

						//Disbursal Initiation
						if(p==2 && dataList.size()>0)
						{  
							logger.info("Virender Address Details");
							bufInsSql =	new StringBuffer();
							bufInsSql.append("insert into cr_indiaLend_Disbursal_Inititation(LOAN_REFERENCE_NO, payee_bank, payee_branch, payee_acc_no, payee_ifsc)");
							bufInsSql.append(" values ( ");
							bufInsSql.append(" ?," );//LOAN_REFERENCE_NO
							bufInsSql.append(" ?," );//payee_bank
							bufInsSql.append(" ?," );//payee_branch
							bufInsSql.append(" ?," );//payee_acc_no
							bufInsSql.append(" ?" );//payee_ifsc

							bufInsSql.append(" )" ); 
							for(int i=0;i<dataList.size();i++)
							{	     	  
								subList=(ArrayList)dataList.get(i);
								subListLen=subList.size();
								insertPrepStmtObject = new PrepStmtObject();

								for(int k=0;k<subListLen;k++){

									logger.info("subList.get(k) ==> "+ subList.get(k));
									if(CommonFunction.checkNull(CommonFunction.checkNull(subList.get(k))).equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
									else
										insertPrepStmtObject.addString(CommonFunction.checkNull(subList.get(k)));	

								}

								insertPrepStmtObject.setSql(bufInsSql.toString());
								logger.info("IN readExcelforBounceUpload() insert query1 : "+ insertPrepStmtObject.printQuery());
								alFinalQry.add(insertPrepStmtObject);    						
								insertPrepStmtObject=null; 
							}  
						}
						insertStatus=ConnectionDAO.sqlInsUpdDeletePrepStmt(alFinalQry); 
					}

				}
				else{logger.info("wrong function id at storing in DB");}
				
				String result="";
				ArrayList<Object> in =new ArrayList<Object>();
				ArrayList<Object> out =new ArrayList<Object>();
				ArrayList outMessages = new ArrayList();
				StringBuilder s1=new StringBuilder();
				StringBuilder s2=new StringBuilder();
				
				in.add("IndiaLend");
				out.add(s1);
				out.add(s2);

				if(CommonFunction.checkNull(functionId).equalsIgnoreCase("10000827")){
					outMessages=(ArrayList) ConnectionDAO.callSP("IndiaLend_Deal_Upload",in,out);
				}
				else if(CommonFunction.checkNull(functionId).equalsIgnoreCase("10000828")){
					outMessages=(ArrayList) ConnectionDAO.callSP("IndiaLend_Loan_Upload",in,out);
				}
				else{logger.info("wrong function id at proc");}
				
				if (outMessages != null && outMessages.size() > 0) {
					s1.append(CommonFunction.checkNull(outMessages.get(0)));
					if (s1 != null && s1.toString().equalsIgnoreCase("S")) {
						result = s1.toString();
						insertStatus=true;
						logger.info("Procedure s1----" + s1);
					} else if (s1 != null && s1.toString().equalsIgnoreCase("E")) {
						s2.append(CommonFunction.checkNull(outMessages.get(1)));
						logger.info("Procedure Error Message----" + s2);
						result = s2.toString();
						request.setAttribute("vresult", result);
						insertStatus=false;
						
						ArrayList ILDeleteQuery=new ArrayList();
						
						String tempDeleteQuery1="delete from cr_indiaLends_deal_dtl";
						String tempDeleteQuery2="delete from cr_indiaLends_deal_customer_m";
						String tempDeleteQuery3= "delete from cr_indiaLends_deal_address_m";
						String tempDeleteQuery4= "delete from cr_indiaLends_deal_loan_dtl";
						String tempDeleteQuery5= "delete from cr_indiaLends_deal_financial_dtl";
						String tempDeleteQuery6= "delete from cr_indiaLends_deal_fund_flow_dtl";
						String tempDeleteQuery7= "delete from cr_indiaLends_deal_installment_plan ";
					 	 
						 
						PrepStmtObject insertPrepStmtObject1=new PrepStmtObject();
					 	insertPrepStmtObject1.setSql(tempDeleteQuery1);
						ILDeleteQuery.add(insertPrepStmtObject1);
						
						PrepStmtObject insertPrepStmtObject2=new PrepStmtObject();
						insertPrepStmtObject2.setSql(tempDeleteQuery2);
						ILDeleteQuery.add(insertPrepStmtObject2);
						
						PrepStmtObject insertPrepStmtObject3=new PrepStmtObject();
						insertPrepStmtObject3.setSql(tempDeleteQuery3);
						ILDeleteQuery.add(insertPrepStmtObject3);
						
						PrepStmtObject insertPrepStmtObject4=new PrepStmtObject();
						insertPrepStmtObject4.setSql(tempDeleteQuery4);
						ILDeleteQuery.add(insertPrepStmtObject4);
						
						PrepStmtObject insertPrepStmtObject5=new PrepStmtObject();
						insertPrepStmtObject5.setSql(tempDeleteQuery5);
						ILDeleteQuery.add(insertPrepStmtObject5);
						
						PrepStmtObject insertPrepStmtObject6=new PrepStmtObject();
						insertPrepStmtObject6.setSql(tempDeleteQuery6);
						ILDeleteQuery.add(insertPrepStmtObject6);

						PrepStmtObject insertPrepStmtObject7=new PrepStmtObject();
						insertPrepStmtObject7.setSql(tempDeleteQuery7);
						ILDeleteQuery.add(insertPrepStmtObject7);
						
						boolean deleteStatus = ConnectionReportDumpsDAO.sqlInsUpdDeletePrepStmt(ILDeleteQuery);
						
						insertPrepStmtObject1=null;
						insertPrepStmtObject2=null;
						insertPrepStmtObject3=null;
						insertPrepStmtObject4=null;
						insertPrepStmtObject5=null;
						insertPrepStmtObject6=null;
						insertPrepStmtObject7=null;
						ILDeleteQuery=null;
						tempDeleteQuery1=null;
						tempDeleteQuery2=null;
						tempDeleteQuery3=null;
						tempDeleteQuery4=null;
						tempDeleteQuery5=null;
						tempDeleteQuery6=null;
						tempDeleteQuery7=null;

					}

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
			request.setAttribute("msg", msg);

		}
		return insertStatus;
	}

	@Override
	public int getBatchIdForErrorlog(String userID) {
		int batch_id=0;
		try{
			String batchIdQuery="select max(batch_id) from  Loan_upload_summary where maker_id='"+userID+"'";			
			String batch_id1=ConnectionDAO.singleReturn(batchIdQuery);
			if(!CommonFunction.checkNull(batch_id1).equalsIgnoreCase("")){
				batch_id=Integer.parseInt(batch_id1);
			}
			else{
				batch_id=0;
			}
			logger.info("Query for Batch_id+"+batchIdQuery);
			logger.info("Batch_id:"+batch_id);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return batch_id;
	}

	
	@SuppressWarnings("deprecation")
	@Override
	public String downloadExcelFile(HttpServletRequest request,	HttpServletResponse response, String filePath) {
		try {
			HttpSession session = request.getSession();
			String functionId=(String)session.getAttribute("functionId");

			String filename=null;
			HSSFWorkbook workbook = new HSSFWorkbook();

			if(CommonFunction.checkNull(functionId).equalsIgnoreCase("10000827")){
				filename = "Deal_upload_template.xls";

				HSSFSheet leadDetailsSheet = workbook.createSheet("Lead Entry detail");
				HSSFRow leadDetailsRow = leadDetailsSheet.createRow((short) 0);
				leadDetailsRow.createCell((short) 0).setCellValue("Application form No");
				leadDetailsRow.createCell((short) 1).setCellValue("Relationship Manager");
				leadDetailsRow.createCell((short) 2).setCellValue("Source Type Desc");
				leadDetailsRow.createCell((short) 3).setCellValue("Source Description");
				leadDetailsRow.createCell((short) 4).setCellValue("Deal Category");
				leadDetailsRow.createCell((short) 5).setCellValue("Branch ID");

				HSSFSheet custDetailSheet = workbook.createSheet("Cust Details");
				HSSFRow custDetailsRow = custDetailSheet.createRow((short) 0);
				custDetailsRow.createCell((short) 0).setCellValue("Application form No");
				custDetailsRow.createCell((short) 1).setCellValue("Customer_No");
				custDetailsRow.createCell((short) 2).setCellValue("Applicant_type");
				custDetailsRow.createCell((short) 3).setCellValue("Applicant_category");
				custDetailsRow.createCell((short) 4).setCellValue("First Name");
				custDetailsRow.createCell((short) 5).setCellValue("Last Name");
				custDetailsRow.createCell((short) 6).setCellValue("Gender");
				custDetailsRow.createCell((short) 7).setCellValue("Customer Category");
				custDetailsRow.createCell((short) 8).setCellValue("Constitution");
				custDetailsRow.createCell((short) 9).setCellValue("DOB (DD-MM-YYYY)");
				custDetailsRow.createCell((short) 10).setCellValue("Caste Category");
				custDetailsRow.createCell((short) 11).setCellValue("Father/Husband Name");
				custDetailsRow.createCell((short) 12).setCellValue("Marital Status");
				custDetailsRow.createCell((short) 13).setCellValue("EmailID");
				custDetailsRow.createCell((short) 14).setCellValue("PAN");
				custDetailsRow.createCell((short) 15).setCellValue("Education Details");
				custDetailsRow.createCell((short) 16).setCellValue("AdharNO");
				custDetailsRow.createCell((short) 17).setCellValue("Group Name");
				custDetailsRow.createCell((short) 18).setCellValue("Registration No");
				custDetailsRow.createCell((short) 19).setCellValue("Business Segment");
				custDetailsRow.createCell((short) 20).setCellValue("Industry");
				custDetailsRow.createCell((short) 21).setCellValue("Sub-industry");
				custDetailsRow.createCell((short) 22).setCellValue("Nature of Business");
				custDetailsRow.createCell((short) 23).setCellValue("Other Relationship ");
				custDetailsRow.createCell((short) 24).setCellValue("Bank name Code");	
				custDetailsRow.createCell((short) 25).setCellValue("Bank Branch Code");
				custDetailsRow.createCell((short) 26).setCellValue("Account No");	
				custDetailsRow.createCell((short) 27).setCellValue("Account Type");

				HSSFSheet addressDetailsSheet = workbook.createSheet("Address Details");
				HSSFRow addressDetilsRow = addressDetailsSheet.createRow((short) 0);
				addressDetilsRow.createCell((short) 0).setCellValue("Application form No");
				addressDetilsRow.createCell((short) 1).setCellValue("Customer_No");
				addressDetilsRow.createCell((short) 2).setCellValue("Address Type");
				addressDetilsRow.createCell((short) 3).setCellValue("Address Line1");
				addressDetilsRow.createCell((short) 4).setCellValue("Address Line2");
				addressDetilsRow.createCell((short) 5).setCellValue("State");
				addressDetilsRow.createCell((short) 6).setCellValue("District");
				addressDetilsRow.createCell((short) 7).setCellValue("Country");
				addressDetilsRow.createCell((short) 8).setCellValue("Pincode");
				addressDetilsRow.createCell((short) 9).setCellValue("Mobile No");
				addressDetilsRow.createCell((short) 10).setCellValue("Landmark");
				addressDetilsRow.createCell((short) 11).setCellValue("No of years");
				addressDetilsRow.createCell((short) 12).setCellValue("No of Months");

				HSSFSheet loanDetailSheet = workbook.createSheet("Loan Details");
				HSSFRow loanDetailsRow = loanDetailSheet.createRow((short) 0);
				loanDetailsRow.createCell((short) 0).setCellValue("Application form No");
				loanDetailsRow.createCell((short) 1).setCellValue("Product");
				loanDetailsRow.createCell((short) 2).setCellValue("Scheme");
				loanDetailsRow.createCell((short) 3).setCellValue("Tenure (In Months)");
				loanDetailsRow.createCell((short) 4).setCellValue("Requested Loan Amount");
				loanDetailsRow.createCell((short) 5).setCellValue("Rate Type");
				loanDetailsRow.createCell((short) 6).setCellValue("Rate Method");
				loanDetailsRow.createCell((short) 7).setCellValue("Base Rate Type");
				loanDetailsRow.createCell((short) 8).setCellValue("Final Rate");
				loanDetailsRow.createCell((short) 9).setCellValue("Repayment Type");
				loanDetailsRow.createCell((short) 10).setCellValue("Installment Type");
				loanDetailsRow.createCell((short) 11).setCellValue("Installment Mode");
				loanDetailsRow.createCell((short) 12).setCellValue("Frequency");
				loanDetailsRow.createCell((short) 13).setCellValue("Interest Compounding Frequency");
				loanDetailsRow.createCell((short) 14).setCellValue("Interest Calculation Method");
				//loanDetailsRow.createCell((short) 15).setCellValue("Interest Frequency");
				loanDetailsRow.createCell((short) 15).setCellValue("Re-Payment Mode");
				loanDetailsRow.createCell((short) 16).setCellValue("Repayment Effective Date(DD-MM-YYYY)");
				loanDetailsRow.createCell((short) 17).setCellValue("Due Day");
				loanDetailsRow.createCell((short) 18).setCellValue("Purpose");
				loanDetailsRow.createCell((short) 19).setCellValue("Sector Type");

				HSSFSheet financialAnalysisSheet = workbook.createSheet("Financial Analysis");
				HSSFRow financialAnalysisRow = financialAnalysisSheet.createRow((short) 0);
				financialAnalysisRow.createCell((short) 0).setCellValue("Application form No");
				financialAnalysisRow.createCell((short) 1).setCellValue("Customer_No");
				financialAnalysisRow.createCell((short) 2).setCellValue("Monthly Inhand Salary");
				financialAnalysisRow.createCell((short) 3).setCellValue("Verification method");
				financialAnalysisRow.createCell((short) 4).setCellValue("Source Type");

/*				HSSFSheet fundFlowSheet = workbook.createSheet("Fund Flow");
				HSSFRow fundFlowRow = fundFlowSheet.createRow((short) 0);
				fundFlowRow.createCell((short) 0).setCellValue("Application form No");
				fundFlowRow.createCell((short) 1).setCellValue("Customer_No");
				fundFlowRow.createCell((short) 2).setCellValue("Bank name");	
				fundFlowRow.createCell((short) 3).setCellValue("Bank Branch");
				fundFlowRow.createCell((short) 4).setCellValue("Account No");	
				fundFlowRow.createCell((short) 5).setCellValue("Account Type");
				fundFlowRow.createCell((short) 6).setCellValue("Statement Month");
				fundFlowRow.createCell((short) 7).setCellValue("Statement Year");*/
				
				HSSFSheet InstallmentPlanSheet = workbook.createSheet("Installment Plan");
				HSSFRow InstallmentPlanRow = InstallmentPlanSheet.createRow((short) 0);
				InstallmentPlanRow.createCell((short) 0).setCellValue("Application form No");
				InstallmentPlanRow.createCell((short) 1).setCellValue("Recovery Type");
				InstallmentPlanRow.createCell((short) 2).setCellValue("From Installment");	
				InstallmentPlanRow.createCell((short) 3).setCellValue("To Installment");
				InstallmentPlanRow.createCell((short) 4).setCellValue("Recovery Percentage");	
				InstallmentPlanRow.createCell((short) 5).setCellValue("Principal Amount");
			}

			else if(CommonFunction.checkNull(functionId).equalsIgnoreCase("10000828")){
				filename = "Loan_upload_template.xls";

				HSSFSheet loanDetailSheet = workbook.createSheet("Loan Details");
				HSSFRow loanDetailsRow = loanDetailSheet.createRow((short) 0);
				loanDetailsRow.createCell((short) 0).setCellValue("LOAN_REFERENCE_NO");
				loanDetailsRow.createCell((short) 1).setCellValue("Customer Name");
				loanDetailsRow.createCell((short) 2).setCellValue("Loan Amount");
				loanDetailsRow.createCell((short) 3).setCellValue("Tenure (In Months)");
//				loanDetailsRow.createCell((short) 31).setCellValue("Next Due Date*");
//				loanDetailsRow.createCell((short) 33).setCellValue("Repayment Effective Date(DD-MM-YYYY)*");

				HSSFSheet chargeDetailSheet = workbook.createSheet("Charge Details");
				HSSFRow chargeDetailRow = chargeDetailSheet.createRow((short) 0);
				chargeDetailRow.createCell((short) 0).setCellValue("LOAN_REFERENCE_NO");
				chargeDetailRow.createCell((short) 1).setCellValue("Processing Fees");
				chargeDetailRow.createCell((short) 2).setCellValue("Stamping Charge");
			
				HSSFSheet disbursalDetailSheet = workbook.createSheet("Disbursal Details");
				HSSFRow disbursalDetailsRow = disbursalDetailSheet.createRow((short) 0);
				disbursalDetailsRow.createCell((short) 0).setCellValue("LOAN_REFERENCE_NO");
				disbursalDetailsRow.createCell((short) 1).setCellValue("Beneficiary Bank Name");
				disbursalDetailsRow.createCell((short) 2).setCellValue("Beneficiary Bank Branch Name");
				disbursalDetailsRow.createCell((short) 3).setCellValue("Beneficiary Account Number");
				disbursalDetailsRow.createCell((short) 4).setCellValue("Beneficiary Bank IFSC Code");

			}
			else{
				filename = "error.xls";
				HSSFSheet customerData = workbook.createSheet("Error Info");
				HSSFRow rowCustomerData = customerData.createRow((short) 0);
				rowCustomerData.createCell((short) 0).setCellValue("Some Error Ocurred");
			}

			response.setContentType("application/vnd.ms-excel");
			response.setHeader("content-disposition", "attachment; filename="+filename);
			OutputStream fileOut = response.getOutputStream();
			workbook.write(fileOut);
			fileOut.close();
			filePath="Your excel file has been generated!";
		}catch (Exception ex) {
			System.out.println(ex);
			filePath="";
		}
		return filePath;
	}

	
	public boolean isDateValid(String argsDate){
		boolean dateValidFlag=true;
		try{//30-09-2017
		argsDate=argsDate.trim();
		String vDate[]=argsDate.split(" ");
		String dt[]=vDate[0].split("-");
		int date=Integer.parseInt(dt[0]);
		int month=Integer.parseInt(dt[1]);
		int year=Integer.parseInt(dt[2]);
		
		if(year>=1900 && year<=2100)
		{
			if(month>=1 && month<=12){
				if(month==1||month==3||month==5||month==7||month==8||month==10||month==12){
					if(date>=1 && date<=31){}
					else{
						dateValidFlag=false;
					}
				}
				else if(month==4||month==6||month==9||month==11){
					if(date>=1 && date<=30){}
					else{
						dateValidFlag=false;
					}
				}
				else{
					if(year%4==0){
						if(date>=1 && date<=29){}
						else{
							dateValidFlag=false;
						}
					}
					else{
						if(date>=1 && date<=28){}
						else{
							dateValidFlag=false;
						}
					}
				}
			}
			else{
				dateValidFlag=false;
			}
		}
		else{
			dateValidFlag=false;
		}	
		return dateValidFlag;
		}
		catch(Exception e){
			return dateValidFlag=false;
		}
	}
	
	public int DateComparator(String argsDate,String busDate){	
		int DateCompFlag=0;
		argsDate=argsDate.trim();
		String vDate[]=argsDate.split(" ");
		String dt[]=vDate[0].split("-");
		int date=Integer.parseInt(dt[0]);
		int month=Integer.parseInt(dt[1]);
		int year=Integer.parseInt(dt[2]);
		
		String bs_dt[]=busDate.split("-");
		int bs_date=Integer.parseInt(bs_dt[0]);
		int bs_month=Integer.parseInt(bs_dt[1]);
		int bs_year=Integer.parseInt(bs_dt[2]);
		
		if(bs_year<year){//30-09-2017
			DateCompFlag=1;
		}
		else if(bs_year>year){
			DateCompFlag=-1;
		}
		else{
			if(bs_month<month){
				DateCompFlag=1;
			}
			else if(bs_month>month){
				DateCompFlag=-1;
			}
			else{
				if(bs_date<date){
					DateCompFlag=1;
				}
				else if(bs_date>date){
					DateCompFlag=-1;
				}
				else{
					DateCompFlag=0;
				}
			}
		}
		
		return DateCompFlag;
	}

	
}
