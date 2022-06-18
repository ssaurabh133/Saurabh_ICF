package com.genericUpload.daoImplMYSQL;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.connect.ConnectionDAO;
import com.genericUpload.dao.ToDoCaseUploadDAO;
import com.genericUpload.vo.GenericUploadVO;
import com.ibm.icu.text.DecimalFormat;


public class ToDoCaseUploadDAOImpl implements ToDoCaseUploadDAO 
{
	private static final Logger logger = Logger.getLogger(ToDoCaseUploadDAOImpl.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.utill");
	String dateFormatWithTime = resource.getString("lbl.dateWithTimeInDao");
	String dateFormat = resource.getString("lbl.dateInDao");
	SimpleDateFormat dateFormatCheck = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat fileNameFormat = new SimpleDateFormat ("yyyy-MM-dd'_'hh-mm-ss");
	String dbType=resource.getString("lbl.dbType");
	DecimalFormat myFormatter = new DecimalFormat("###,###.####");
	
	Connection con = null;
	PreparedStatement ptmt = null;
	ResultSet rs = null;
	Statement stmt = null;
	CallableStatement csmt = null;


	
private static String getCustomCellValue(Cell cell){
	String cellValue="";
	if(cell!=null){
		try{
			if(cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
				switch(cell.getCachedFormulaResultType()){
					case Cell.CELL_TYPE_STRING:
						cellValue=cell.getStringCellValue();
						break;
					case Cell.CELL_TYPE_NUMERIC:
						if (DateUtil.isCellDateFormatted(cell)) {
							SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
							cellValue=dateFormat.format(cell.getDateCellValue());
						} else {
							cellValue=""+cell.getNumericCellValue();
							BigDecimal bd = new BigDecimal(cellValue);
							cellValue=bd.longValue()+"";
						}
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						cellValue=""+cell.getBooleanCellValue();
						break;
					default:
				}
			}else{
				switch(cell.getCellType()){
					case Cell.CELL_TYPE_STRING:
						cellValue=cell.getStringCellValue();
						break;
					case Cell.CELL_TYPE_NUMERIC:
						if (DateUtil.isCellDateFormatted(cell)) {
							SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
							cellValue=dateFormat.format(cell.getDateCellValue());
						} else {
							cellValue=""+cell.getNumericCellValue();
							BigDecimal bd = new BigDecimal(cellValue);
							cellValue=bd.longValue()+"";
						}
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						cellValue=""+cell.getBooleanCellValue();
						break;
					default:
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	if(StringUtils.isBlank(cellValue)){
		cellValue="";
	}
	cellValue=cellValue.trim();
	return cellValue;
}
public boolean isDateValid(String dateString){
	String pattern="dd-MM-yyyy";
	try
	{
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		if(sdf.format(sdf.parse(dateString)).equals(dateString))
			return true;
	}
	catch (Exception pe) {}
	return false;
}

@Override
public ArrayList<GenericUploadVO> getToDoCaseUploadDetails(String makerId) {
	ArrayList<GenericUploadVO> list=null;
	Connection inrConnection = null;
	PreparedStatement ps=null;
	ResultSet result=null;
	try{
		inrConnection = ConnectionDAO.getConnection();
		String fetchQry="SELECT TODO.ID,IFNULL(TODO.LOAN_ID,'')LOAN_ID,IFNULL(TODO.USER_ID,'')USER_ID,IFNULL(TODO.EXPIRED_ON,'')EXPIRED_ON,"
				+"TODO.CREATED_BY,IFNULL(TODO.CREATED_ON,'')CREATED_ON,"
				+" TODO.OPERATION_DATE"
				+" FROM COLL_TO_DO_CASE_DTL TODO"
				+" JOIN SEC_USER_M MK ON(MK.USER_ID=TODO.CREATED_BY)";
		
		logger.info("Upload Grid query : "+fetchQry);
		
		ps = inrConnection.prepareStatement(fetchQry);
		//ps.setString(1,makerId);
		result = ps.executeQuery();
		while(result.next()){
			if(list==null){
				list = new ArrayList<GenericUploadVO>();
			}
			GenericUploadVO vo = new GenericUploadVO();
		
			vo.setBatch_id(result.getString("ID"));
			vo.setLoan_id(result.getString("LOAN_ID"));
			vo.setMaker_id(result.getString("CREATED_BY"));
			vo.setMaker_date(result.getString("CREATED_ON"));
			vo.setNo_of_records(result.getString("USER_ID"));
			vo.setOperation_date(result.getString("OPERATION_DATE"));
			list.add(vo);
		}
		ps.close();
		result.close();
		inrConnection.close();
	}catch (Exception e){
		e.printStackTrace(); 
	}finally{
		try{
			if(result!=null){
				result.close();
			}
			if(ps!=null){
				ps.close();
			}
			if(inrConnection!=null){
				inrConnection.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	return list;
}


@Override
public int saveToDoCaseUpload(String maker_id, String maker_date) {
	int toDoCaseId  = 0;
	Connection inrConnection = null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	try{
		inrConnection = ConnectionDAO.getConnection();
		ps = inrConnection.prepareStatement("INSERT INTO COLL_TO_DO_UPLOAD_DTL(UPLODED_BY,UPLOADED_ON,REC_STATUS)" +
		"VALUES('"+maker_id+"',DATE_ADD(STR_TO_DATE('"+maker_date+"','"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),'I')");
		int output=ps.executeUpdate();
		if(output>0){
			toDoCaseId  = 0;
			String maxQry="SELECT MAX(ID)ID FROM COLL_TO_DO_UPLOAD_DTL WHERE UPLODED_BY='"+maker_id+"'";
			ps.close();
			ps=inrConnection.prepareStatement(maxQry);
			rs = ps.executeQuery();
			while (rs.next()){
				toDoCaseId=rs.getInt("ID");
			}
		}
	}
	catch (Exception e){
		e.printStackTrace();
		toDoCaseId  = 0;
	}finally{
		try{
			if(rs!=null){
				rs.close();
			}
			if(ps!=null){
				ps.close();
			}
			if(inrConnection!=null){
				inrConnection.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	return toDoCaseId;
}


public boolean updateToDoCaseUpload(Map<String, String> parameter) {
	boolean status=false;
	Connection inrConnection = null;
	PreparedStatement ps=null;
	String userId=parameter.get("userId");
	String bussinessDate=parameter.get("bussinessDate");
	String fileName=parameter.get("fileName");
	String filePath=parameter.get("filePath");
	String noOfRecords=parameter.get("noOfRecords");
	String operationMessage=parameter.get("operationMessage");
	String recStatus=parameter.get("recStatus");
	String uploadId=parameter.get("uploadId");
	try{
		inrConnection = ConnectionDAO.getConnection();
		if(StringUtils.equalsIgnoreCase(recStatus,"F")){
			ps = inrConnection.prepareStatement("UPDATE COLL_TO_DO_UPLOAD_DTL SET REC_STATUS=? WHERE ID=?");
			ps.setString(1, recStatus);
			ps.setString(2, uploadId);
		}else{
			ps = inrConnection.prepareStatement("UPDATE COLL_TO_DO_UPLOAD_DTL SET FILE_NAME=?,FILE_PATH=?,NO_OF_RECORDS=?,OPERATION_MESSAGE=?,REC_STATUS=? WHERE ID=?");
			ps.setString(1, fileName);
			ps.setString(2, filePath);
			ps.setString(3, noOfRecords);
			ps.setString(4, operationMessage);
			ps.setString(5, recStatus);
			ps.setString(6, uploadId);
		}
		int val=ps.executeUpdate();
		if(val>0){
			status=true;
		}
	}
	catch (Exception e){
		e.printStackTrace();
		status=false;
	}finally{
		try{
			if(ps!=null){
				ps.close();
			}
			if(inrConnection!=null){
				inrConnection.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	return status;
}

public void dublicateCheckLoanId(String LOAN_ID,Integer isSrlNoError,String srlNoMessage){
	if(isSrlNoError==0) {
		String duplicateLoanIdQuery="SELECT LOAN_ID FROM COLL_TO_DO_CASE_DTL WHERE LOAN_ID="+LOAN_ID;
		String duplicateLoanId=ConnectionDAO.singleReturn(duplicateLoanIdQuery);
		if(StringUtils.isBlank(duplicateLoanId)){
			duplicateLoanId="0";
		}
	    duplicateLoanId=duplicateLoanId.trim();
		if(duplicateLoanId.equalsIgnoreCase(LOAN_ID)){
			isSrlNoError=1;
			srlNoMessage="This Loan_Id is already exists.";
		}
	}
}

public Map<String,String> fetchSystemOutputSheetToDoCase(String batchId,String makerId,String makerDate,String fileName,String filePath,String branch){
	logger.info("Inside ToDoCaseUploadDAOImpl::fetchSystemOutputSheet()");
	File objFile1 = null;
	Map<String,String>operationMap=new HashMap<String,String>();
	String operationStatus="0";
	String operationMessage="Unable to read excel file , Please contact to admin";
	int rowCount=0;
	try 
	{
		String d1=makerDate;
		String d2="";
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Calendar c=Calendar.getInstance();
		try{
			c.setTime(sdf.parse(d1));
		}catch(ParseException e){
			e.printStackTrace();
		}
		c.add(Calendar.DAY_OF_MONTH,2);
		d2 = sdf.format(c.getTime());
		System.out.println("Initial Date : "+d1);
		System.out.println("Date after Addition: "+d2);
		
		Date fromDate=new SimpleDateFormat("dd-MM-yyyy").parse(d1);
		Date toDate=new SimpleDateFormat("dd-MM-yyyy").parse(d2);
		
		PreparedStatement ptmt = null;
		PreparedStatement ptmtDlt = null;
		CallableStatement csmt = null;
		Connection inrConnection = null;
		String strPath=filePath+File.separator+fileName;
		logger.info("Uploaded File Location :"+strPath);
		objFile1 = new File(strPath);
		String ext=strPath.substring(strPath.lastIndexOf(".")+1);
		InputStream ExcelFileToRead = null;
		XSSFWorkbook  wb = null;
		FormulaEvaluator evaluator = null;
		XSSFSheet sheet = null;
		try{
			String LOAN_ID="";
			String USER_ID="";
			String EXPIRED_ON="";
			String ACTION="";
			
			inrConnection= ConnectionDAO.getConnection();
			inrConnection.setAutoCommit(false);
			String inserQry="INSERT INTO COLL_TO_DO_CASE_DTL(LOAN_ID,USER_ID,EXPIRED_ON,CREATED_BY,CREATED_ON,BATCH_ID,OPERATION_DATE)"
			+ " VALUES ("
			+ "?,"//LOAN_ID
			+ "?,"//USER_ID
			+" STR_TO_DATE(?, '"+dateFormat+"')," //EXPIRED_ON
			+ "?,"//CREATED_BY
			+" DATE_ADD(STR_TO_DATE(?,'"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND)," //CREATED_ON
			+ "?,"//BATCH_ID
			+ "NOW())";//OPERATION_DATE
			ptmt=inrConnection.prepareStatement(inserQry);
			
			String deleteQry="DELETE FROM COLL_TO_DO_CASE_DTL WHERE LOAN_ID=? AND USER_ID=?";
			ptmtDlt=inrConnection.prepareStatement(deleteQry);
					
			
			int isError=0;
			String errorMessage="";
			int rowTotal=0;
			Set<String> SERIAL_NO_LIST = new HashSet<String>();
			Set<String> LOAN_NO_LIST = new HashSet<String>();
			if(StringUtils.equalsIgnoreCase(ext,"XLSX")){
				ExcelFileToRead = new FileInputStream(strPath);
				wb = new XSSFWorkbook(ExcelFileToRead);
				evaluator = wb.getCreationHelper().createFormulaEvaluator();
				XSSFFormulaEvaluator.evaluateAllFormulaCells(wb);
				sheet = wb.getSheetAt(0);
				rowTotal = sheet.getLastRowNum();
				String sheetName=sheet.getSheetName();
				int deletecount=0;
				int insertCount=0;
				for(int j=1;j<=rowTotal;j++){
					LOAN_ID="";USER_ID="";EXPIRED_ON="";ACTION="";
					int isSrlNoError=0;
					String srlNoMessage="";
					Row header = sheet.getRow(j);
					if(header==null){
						continue;
					}
					
					if(isSrlNoError==0) {
						LOAN_ID=getCustomCellValue(header.getCell(0));
						LOAN_ID=LOAN_ID.replace(".0","");
						if(StringUtils.isBlank(LOAN_ID)){
							isSrlNoError=1;
							srlNoMessage="LOAN_ID can not be blank";
						}else{
							try{
								Integer i=Integer.parseInt(LOAN_ID);
							}catch(Exception e){
								isSrlNoError=1;
								srlNoMessage="Invalid LOAN_ID("+LOAN_ID+") found.";
							}
						}
					}
					
					dublicateCheckLoanId(LOAN_ID, isSrlNoError, srlNoMessage);
					
					if(isSrlNoError==0) {
						ACTION=getCustomCellValue(header.getCell(3));
						ACTION=ACTION.replace(".0","");
						
						if(StringUtils.isBlank(ACTION)){
							isSrlNoError=1;
							srlNoMessage="ACTION is blank for LOAN_ID :"+LOAN_ID;
						}else{
							if(!(StringUtils.equals(ACTION,"N") || StringUtils.equals(ACTION,"D"))) {
								isSrlNoError=1;
								srlNoMessage="Invalid value found of ACTION("+ACTION+") column for LOAN_ID :"+LOAN_ID+". It should be in (D/N) only";
							}
						}
					}
					if(isSrlNoError==0) {
						boolean dupStatus=LOAN_NO_LIST.add(LOAN_ID+"_"+ACTION);
						if(!dupStatus){
							isSrlNoError=1;
							srlNoMessage="Duplicate values are not allowed in LOAN_ID column. Duplicate value("+LOAN_ID+") found for ACTION : "+ACTION;
						}
					}
					
					//AVAILABLITY CHECK OF LOAN_ID
					
					if(isSrlNoError==0) {
						USER_ID=getCustomCellValue(header.getCell(1));
						USER_ID=USER_ID.replace(".0","");
						if(StringUtils.isBlank(USER_ID)){
							isSrlNoError=1;
							srlNoMessage="USER_ID is blank for LOAN_ID :"+LOAN_ID;
						}
					}
					
					if(isSrlNoError==0 && StringUtils.equals(ACTION,"N")) {
						EXPIRED_ON=getCustomCellValue(header.getCell(2));
						EXPIRED_ON=EXPIRED_ON.replace(".0","");
						
						if(StringUtils.isBlank(EXPIRED_ON)){
							isSrlNoError=1;
							srlNoMessage="EXPIRED_ON is blank for LOAN_ID :"+LOAN_ID;
						}else{
							if(EXPIRED_ON.length()!=10){
								isSrlNoError=1;
								srlNoMessage="Invalid value found in EXPIRED_ON("+EXPIRED_ON+") column for LOAN_ID :"+LOAN_ID;
							}
							if(!isDateValid(EXPIRED_ON)){
								isSrlNoError=1;
								srlNoMessage="Invalid date format used for EXPIRED_ON("+EXPIRED_ON+") column for LOAN_ID :"+LOAN_ID+". It should be in dd-MM-yyyy format only";
							}
						}
						if(isSrlNoError==0){
							Date curDate=new SimpleDateFormat("dd-MM-yyyy").parse(EXPIRED_ON);
							if(!(curDate.compareTo(fromDate)>=0 && curDate.compareTo(toDate)<=0)){
								isSrlNoError=1;
								srlNoMessage="Value of EXPIRED_ON("+EXPIRED_ON+") column is out of range for LOAN_ID :"+LOAN_ID+". It should be in between "+d1+" and "+d2+". ";
							}
						}
					}
					if(isSrlNoError==0) {
						if(StringUtils.equals(ACTION,"N")) {
							ptmt.setString(1,LOAN_ID);
							ptmt.setString(2,USER_ID);
							ptmt.setString(3,EXPIRED_ON);
							ptmt.setString(4,makerId);
							ptmt.setString(5,makerDate);
							ptmt.setString(6,batchId);
							ptmt.addBatch();
							insertCount++;
						}else {
							ptmtDlt.setString(1,LOAN_ID);
							ptmtDlt.setString(2,USER_ID);
							ptmtDlt.addBatch();
							deletecount++;
						}
						rowCount++;
					}else {
						isError=1;
						errorMessage=srlNoMessage;
						break;
					}
				}//end of loop
				if(isError==0 && (insertCount>0 || deletecount>0)) {
					if(insertCount>0) {
						ptmt.executeBatch();
					}
					if(deletecount>0) {
						ptmtDlt.executeBatch();
					}
					inrConnection.commit();
					operationStatus="1";
					operationMessage="Operation completed successfully.";
				}else {
					inrConnection.rollback();
					operationStatus="0";
					if(StringUtils.isBlank(errorMessage)){
						errorMessage="There is nothing for this operation";
					}
					operationMessage=errorMessage;
				}
			}else {
				operationStatus="0";
				operationMessage="Invalid format of excel it should only be xlsx";
			}
		}
		catch(IOException e){
			e.printStackTrace();
			operationStatus="0";
			operationMessage="Some unknown error occurred, Please contact to admin : "+e.getMessage();
			rowCount=0;
		}finally{
			if(ptmt!=null){
				ptmt.close();
			}
			if(csmt!=null){
				csmt.close();
			}
			if(inrConnection!=null){
				inrConnection.close();
			}
			if(ExcelFileToRead!=null){
				ExcelFileToRead.close();
			}
			wb=null;
			sheet=null;
			evaluator=null;
		}
	}
	catch(Exception e){
		e.printStackTrace();
		operationStatus="0";
		operationMessage="Some unknown error occurred(1), Please contact to admin : "+e.getMessage();
		rowCount=0;
	}finally{
		
	}
	operationMap.put("operationStatus",operationStatus);
	operationMap.put("operationMessage",operationMessage);
	operationMap.put("rowCount",rowCount+"");
	return operationMap;
}
}
