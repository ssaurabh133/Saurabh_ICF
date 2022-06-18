package com.genericUpload.daoImplMYSQL;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
import javax.sql.XAConnection;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.util.EnvUtil;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;

import com.genericUpload.dao.VoucherUploadDAO;
import com.genericUpload.actionforms.ExcelSheetUploadForm;
import com.genericUpload.dto.TempVoucherProxyDTO;
import com.genericUpload.dto.VoucherUploadParametersDTO;
import com.genericUpload.vo.VoucherUploadVO;

import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.ConnectionUploadDAO;
import com.exception.OmniFinMarkForRollbackException;
import com.ibm.icu.text.DecimalFormat;
import com.login.roleManager.UserObject;

public class VoucherUploadDAOImpl implements VoucherUploadDAO
{
	private static final Logger logger = Logger.getLogger(VoucherUploadDAOImpl.class.getName());
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


	@Override
	public int deleteBatch(String batch_id, String userId) 
	{
		try
		{
			con = ConnectionUploadDAO.getConnection();
			PreparedStatement ps = con.prepareStatement("delete from all_upload_summary where maker_id = ? and batch_id = ? and status in ('R','B','EK','EP')");
			logger.info("delete from all_upload_summary where maker_id = "+userId+" and batch_id = "+batch_id+" and status in ('R','B','EK','EP')");
			ps.setString(1, userId);
			ps.setString(2, batch_id);
			int i = ps.executeUpdate();
			logger.info("row deleted from all_upload_summary ="+i);
			if( i > 0)
			{
				
				PreparedStatement ps1 = con.prepareStatement("delete from temp_gl_voucher_upload where user_id = ? and batch_id = ?");
				logger.info("delete from temp_gl_voucher_upload where user_id = "+userId+" and batch_id = "+batch_id);
				ps1.setString(1, userId);
				ps1.setString(2, batch_id);
			    int j = ps1.executeUpdate();
			    logger.info("row deleted from temp_gl_voucher_upload ="+j);
			    ps1.close();
				
			}
			
			ps.close();
			con.close();
			return i;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return 0;
	}
	
	
	@Override
	public int forwardBatch(String batch_id, String userId) 
	{
		try
		{
			con = ConnectionUploadDAO.getConnection();
			PreparedStatement ps = con.prepareStatement("update all_upload_summary set status = 'F' where maker_id = ? and batch_id = ? and status in ('R','B')");
			ps.setString(1, userId);
			ps.setString(2, batch_id);
			int i = ps.executeUpdate();
			ps.close();
			con.close();
			return i;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return 0;
	}
	   
   @Override
   	public ArrayList<VoucherUploadVO> searchUploads(String user_id) 
   	{
   		try
   		{
   			con = ConnectionUploadDAO.getConnection();
   			PreparedStatement ps = con.prepareStatement("select batch_id,upload_type,DATE_FORMAT(upload_date,'"+dateFormat+"'),maker_id,DATE_FORMAT(maker_date,'"+dateFormat+"'),no_of_records,summary1,summary2,file_name from all_upload_summary where upload_type = 'voucher upload' and status = 'f' and maker_id != ? ");
   			logger.info("select batch_id,upload_type,upload_date,maker_id,maker_date,no_of_records,summary1,summary2,file_name from all_upload_summary where upload_type = 'voucher upload' and status = 'f' and maker_id != "+user_id);
   			ps.setString(1, user_id);
   			ResultSet result = ps.executeQuery();
   			ArrayList< VoucherUploadVO> list = new ArrayList<VoucherUploadVO>();
   			while(result.next())
   			{
   				VoucherUploadVO vo = new VoucherUploadVO();
   				vo.setBatch_id(result.getString(1));
   				vo.setUpload_type(result.getString(2));
   				vo.setUpload_date(result.getString(3));
   				vo.setMaker_id(result.getString(4));
   				vo.setMaker_date(result.getString(5));
   				vo.setNo_of_vouchers(result.getString(6));
   				vo.setSummary1(result.getString(7));
   				vo.setSummary2(result.getString(8));
   				vo.setFile_name(result.getString(9));
   				list.add(vo);
   			}
   			ps.close();
   			result.close();
   			con.close();
   			return list;
   		}
   		catch (Exception e)
   		{
			e.printStackTrace();
			logger.info("error in ArrayList<VoucherUploadVO> searchUploads()");
		}
   		return null;
   	}
   
   @Override
  	public ArrayList<VoucherUploadVO> searchUploads(ExcelSheetUploadForm form) 
  	{
  		try
  		{
  			con = ConnectionUploadDAO.getConnection();
  			String query = "select batch_id,upload_type,upload_date,maker_id,maker_date,no_of_records,summary1,summary2,file_name from all_upload_summary where upload_type = 'voucher upload' and status = 'f' and maker_id != '"+form.getMakerId()+"'";
  			if(form.getBatch_id() != null && form.getBatch_id() != "")
  			{
  				query += " and batch_id = '"+form.getBatch_id()+"'";
  			}
  			
  			if(form.getMaker_id() != null && form.getMaker_id() != "")
  			{
  				query += " and maker_id = '"+form.getMaker_id()+"'";
  			}
  			
  			if(form.getMaker_date() != null && form.getMaker_date() != "")
  			{
  				String d = form.getMaker_date();
  				query += " and maker_date = str_to_date('"+d+"','%d-%m-%Y')";
  			}
  			
  			if(form.getUpload_date() != null && form.getUpload_date() != "")
  			{
  				String d = form.getUpload_date();
  				query += " and upload_date = str_to_date('"+d+"','%d-%m-%Y')";
  		    }
  			
  			if(form.getFile_name() != null && form.getFile_name() != "")
  			{
  				query += " and file_name = '"+form.getFile_name()+"'";
  			}
  			logger.info(query);
  			Statement statement = con.createStatement();
  			ResultSet result = statement.executeQuery(query);
  			ArrayList<VoucherUploadVO> list = new ArrayList<VoucherUploadVO>();
  			while(result.next())
  			{
  				VoucherUploadVO vo = new VoucherUploadVO();
   				vo.setBatch_id(result.getString(1));
   				vo.setUpload_type(result.getString(2));
   				vo.setUpload_date(result.getString(3));
   				vo.setMaker_id(result.getString(4));
   				vo.setMaker_date(result.getString(5));
   				vo.setNo_of_vouchers(result.getString(6));
   				vo.setSummary1(result.getString(7));
   				vo.setSummary2(result.getString(8));
   				vo.setFile_name(result.getString(9));
   				list.add(vo);
  			}
  			statement.close();
  			result.close();
  			con.close();
  			return list;
  		}
  		catch (Exception e)
  		{
			e.printStackTrace();
			logger.info("error in ArrayList<VoucherUploadVO> searchUploads(ExcelSheetUploadForm form)");
		}
  		return null;
  	}
   
	   
   public int saveUploadSummary(String upload_Type,String upload_date,String maker_id,String file_name)
   {
	   //SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");
	   //String dt = df.format(new Date());
	   int output  = 0;
	   //logger.info("karmveer --------------------"+dt);
	   try
	   {
		   con = ConnectionUploadDAO.getConnection();
		   PreparedStatement ps = con.prepareStatement("insert into all_upload_summary (upload_type,upload_date,maker_id,maker_date,status,file_name)" +
		   		"values(?,str_to_date(?,'%d-%m-%Y'),?,curdate(),?,?)");
		   ps.setString(1, upload_Type);
		   ps.setString(2, upload_date);
		   ps.setString(3, maker_id);
		   //ps.setString(4, dt);
		   ps.setString(4, "P");
		   ps.setString(5, file_name);
		   
		   int rows = ps.executeUpdate();
		   
		   
		   
		   if(rows > 0)
		   {
			   //con = ConnectionUploadDAO.getConnection();
			   PreparedStatement ps1 = con.prepareStatement("select max(batch_id) from all_upload_summary");
			   ResultSet result = ps1.executeQuery();
			   while(result.next())
			   {
				  output = result.getInt(1);
			   }
			   logger.info("rows ==============="+rows+"  output================="+output);
			   ps1.close();
			   result.close();
		   }
		   ps.close();
		   con.close();
		   
		   return output;
	   }
	   catch (Exception e)
	   {
		   
		   e.printStackTrace();
		   
	   }
	  
	   
	   return output;
   }
   
   public int updateUploadSummary(String user_id,String batch_id)
   {
	   int output = 0;
	   try
	   {
		   
		   String no_of_vouchers = "";
		   con = ConnectionUploadDAO.getConnection();
		   PreparedStatement ps = con.prepareStatement("select count(distinct serial_no) from temp_gl_voucher_upload where user_id = ? and batch_id = ?");
		   ps.setString(1, user_id);
		   ps.setString(2, batch_id);
		   ResultSet result = ps.executeQuery();
		   while(result.next())
		   {
			   no_of_vouchers = result.getString(1);
		   }
		   logger.info("no_of_vouchers ------------------>"+no_of_vouchers);
		   
		   PreparedStatement ps1 = con.prepareStatement("update all_upload_summary set status = ?,no_of_records = ? where batch_id = ?");
		   ps1.setString(1,"F");
		   ps1.setString(2, no_of_vouchers);
		   ps1.setString(3, batch_id);
		   output = ps1.executeUpdate();
		   
		   ps.close();
		   result.close();
		   ps1.close();
		   con.close();
		   
	   }
	   catch (Exception e)
	   {
		   e.printStackTrace();
	   }
	   
	   
	   
	   return output;
   }
   
   public String updateBatchStatus(String batch_id,String userId)
   {
	   try
	   {
		   con = ConnectionUploadDAO.getConnection();
		   PreparedStatement ps = con.prepareStatement("update all_upload_summary set author_id =  ?,author_date = curdate(),status = 'W' where batch_id = ?");
		   logger.info("query == update all_upload_summary set author_id = "+userId+",author_date = curdate(),status = 'W' where batch_id = "+batch_id);
		   ps.setString(1, userId);
		   ps.setString(2, batch_id);
		   
		   int i = ps.executeUpdate();
		   	ps.close();
		    con.close();
		    return String.valueOf(i);
	   }
	   catch (Exception e)
	   {
		   e.printStackTrace();
	   }
	   return "";
   }
   
   
   
   public int sendBack(String batch_id,String userId,String comment)
   {
	   try
	   {   logger.info("auther comment while sending back ==="+comment);	
		   con = ConnectionUploadDAO.getConnection();
		   PreparedStatement ps = con.prepareStatement("update all_upload_summary set author_id =  ?,author_date = curdate(),status = 'B',summary1 = ? where batch_id = ?");
		   logger.info("update all_upload_summary set author_id =  "+userId+",author_date = curdate(),status = 'B',summary1 = "+comment+" where batch_id = "+batch_id);
		   ps.setString(1, userId);
		   ps.setString(2, comment);
		   ps.setString(3, batch_id);
		   
		   int i = ps.executeUpdate();
		   ps.close();
		   con.close();
		   return i;
	   }
	   catch (Exception e)
	   {
		   e.printStackTrace();
	   }
	   return 0;
   }
   
   public int reject(String batch_id,String userId,String comment)
   {
	   try
	   {	 logger.info("auther comment while rejecting ==="+comment);
		   con = ConnectionUploadDAO.getConnection();
		   PreparedStatement ps = con.prepareStatement("update all_upload_summary set author_id =  ?,author_date = curdate(),status = 'R',summary1 = ? where batch_id = ?");
		   logger.info("update all_upload_summary set author_id =  "+userId+",author_date = curdate(),status = 'B',summary1 = "+comment+" where batch_id = "+batch_id);
		   ps.setString(1, userId);
		   ps.setString(2, comment);
		   ps.setString(3, batch_id);
		   
		   int i = ps.executeUpdate();
		   ps.close();
		   con.close();
		   return i;
	   }
	   catch (Exception e)
	   {
		   e.printStackTrace();
	   }
	   return 0;
   }
   
   public boolean copyExcelDataToTempTable(VoucherUploadParametersDTO dto)
   {
	   
	   boolean status = false;
   	   try
   	   {
   		
   		    KettleEnvironment.init(false); 
  	  		EnvUtil.environmentInit(); 
  	  		TransMeta transMeta = new TransMeta(dto.getKtrpath()); 
  	  		Trans trans = new Trans(transMeta); 
  	 
  	  		// set parameter. same can be used inside steps in transformation 
  	  		trans.setParameterValue("filePathWithName",dto.getExcelfilepath());
  	  		trans.setParameterValue("userID",dto.getUser_id());
  	  		trans.setParameterValue("batch_id",dto.getBatch_id());
  	  		trans.execute(null); // You can pass arguments instead of null. 
  	  		trans.waitUntilFinished(); 
  	 
  	  		if ( trans.getErrors() > 0 )
  	  		{ 
  	  			status=false;
  	  			throw new RuntimeException( "There were errors during transformation execution."+trans.getErrors() ); 
  	  		} 
  	  		else
  	  		{
  	  			status=true;
  	  		}
	   
  	  		transMeta.clearCaches();
  	  		transMeta.clear();
   	  }
   	  catch (Exception e)
   	  {
   		
			status = false;
	  }
  	
  	return status;
	   
   }
   
   
   
   
   
   
   public boolean uploadExcel(HttpServletRequest request,HttpServletResponse response,String strFileName,ExcelSheetUploadForm  excelForm)
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
			  String strPath=ConnectionUploadDAO.singleReturn(query);
			  strPath     = strPath + "/" + strFileName;
			  objFile1    = new File(strPath);
	          try 
	          {     	   
	        	 String strDeleteQuery = "DELETE FROM temp_gl_voucher_upload WHERE user_id = '"+makerID+"' ";
	        	  logger.info("In  uploadExcel(): delete query :-----"+strDeleteQuery);
	        	  alDeleteQuery.add(strDeleteQuery);
	        	  boolean status1=ConnectionUploadDAO.sqlInsUpdDelete(alDeleteQuery);
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
	         con = ConnectionUploadDAO.getConnection();
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
				ConnectionUploadDAO.closeConnection(con, ptmt);
				
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
		ConnectionUploadDAO.closeConnection(con, stmt, rs);
	}

	return status;
   }
   
   public ArrayList<VoucherUploadVO> searchData(String user_id) 
   {
	   try
	   {
		   con = ConnectionUploadDAO.getConnection();
		   PreparedStatement ps = con.prepareStatement("select batch_id,status,upload_type,DATE_FORMAT(upload_date,'"+dateFormat+"'),maker_id,DATE_FORMAT(maker_date,'"+dateFormat+"'),author_id,DATE_FORMAT(author_date,'"+dateFormat+"'),no_of_records,summary1,summary2,file_name from all_upload_summary where upload_type = 'voucher upload' and status in('P','B','R','W','EK','EP') and maker_id = ?");
		   ps.setString(1, user_id);
		   ResultSet result = ps.executeQuery();
		   ArrayList<VoucherUploadVO> list = new ArrayList<VoucherUploadVO>();
		   while(result.next())
		   {
			 VoucherUploadVO vo = new VoucherUploadVO();
			 vo.setBatch_id(result.getString(1));
			 vo.setStatus(result.getString(2));
			 vo.setUpload_type(result.getString(3));
			 vo.setUpload_date(result.getString(4));
			 vo.setMaker_id(result.getString(5));
			 vo.setMaker_date(result.getString(6));
			 vo.setAuthor_id(result.getString(7));
			 vo.setAuthor_date(result.getString(8));
			 vo.setNo_of_vouchers(result.getString(9));
			 vo.setSummary1(result.getString(10));
			 vo.setSummary2(result.getString(11));
			 vo.setFile_name(result.getString(12));
			 list.add(vo);
			   	
		   }
		   ps.close();
		   result.close();
		   con.close();
		   return list;
		   
	   }
	   catch (Exception e)
	   {
		  e.printStackTrace(); 
	   }
	   return null;
   }
   //called from MDB
   public String changeStatus(VoucherUploadParametersDTO dto)
   {
	   try
	   {
		   con = ConnectionUploadDAO.getConnection();
		   PreparedStatement ps = con.prepareStatement("update all_upload_summary set status = 'A' where batch_id = ?");
		   logger.info("update all_upload_summary set status = 'A' where batch_id = "+dto.getBatch_id());
		   ps.setString(1, dto.getBatch_id());
		   int i = ps.executeUpdate();
		   if(i > 0)
		   {
			   PreparedStatement ps1 = con.prepareStatement("delete from temp_gl_voucher_upload where batch_id = ?");
			   logger.info("delete from temp_gl_voucher_upload where batch_id ="+dto.getBatch_id());
			   ps1.setString(1, dto.getBatch_id());
			   int j =  ps1.executeUpdate();
			   ps1.close();
		   }
		   
		   ps.close();
		   con.close();
		    
		    return String.valueOf(i);
	   }
	   catch (Exception e)
	   {
		   e.printStackTrace();
	   }
	   return "";
   }
 //called from MDB
   public boolean changeStatusP_To_EK(VoucherUploadParametersDTO dto)
   {
	   try
	   {
		   con = ConnectionUploadDAO.getConnection();
		   PreparedStatement ps = con.prepareStatement("update all_upload_summary set status = 'EK' where batch_id = ?");
		   logger.info("update all_upload_summary set status = 'EK' where batch_id = "+dto.getBatch_id());
		   ps.setString(1, dto.getBatch_id());
		   int i = ps.executeUpdate();
		   
		   ps.close();	   
		   con.close();
		    return true;
	   }
	   catch (Exception e)
	   {
		   e.printStackTrace();
	   }
	   
	  return false;
   }
 //called from MDB
   public boolean changeStatusP_To_EP(VoucherUploadParametersDTO dto)
   {
	   try
	   {
		   con = ConnectionUploadDAO.getConnection();
		   PreparedStatement ps = con.prepareStatement("update all_upload_summary set status = 'EP' where batch_id = ?");
		   logger.info("update all_upload_summary set status = 'EP' where batch_id = "+dto.getBatch_id());
		   ps.setString(1, dto.getBatch_id());
		   int i = ps.executeUpdate();
		   ps.close();
		   con.close();
		   if(i > 0)
		    return true;
	   }
	   catch (Exception e)
	   {
		   e.printStackTrace();
	   }
	   
	  return false;
   }
   
   
 //called from MDB
   public boolean changeStatusF_To_EP(VoucherUploadParametersDTO dto)
   {
	   try
	   {
		   con = ConnectionUploadDAO.getConnection();
		   PreparedStatement ps = con.prepareStatement("update all_upload_summary set status = 'EP' where batch_id = ?");
		   logger.info("update all_upload_summary set status = 'EP' where batch_id = "+dto.getBatch_id());
		   ps.setString(1, dto.getBatch_id());
		   int i = ps.executeUpdate();
		   ps.close();
		   con.close();
		   if(i > 0)
		    return true;
	   }
	   catch (Exception e)
	   {
		   e.printStackTrace();
	   }
	   
	  return false;
   }
   
   public String validateVouchersInBatch(VoucherUploadParametersDTO dto) 
   {
	  
	  String filename = dto.getFilename();	
	  
	  
	  String flag=null;
	  con = ConnectionUploadDAO.getConnection();
	  rs=null;
	  String s2 =null;
	  String voucherNo =null;
	 
	try { 
		//con.setAutoCommit(false);
		csmt = (CallableStatement) con.prepareCall("{CALL GL_EXCELSHEET_VOUCHER_VALIDATION_AUTHOR(?,?,STR_TO_DATE(?,'"+dateFormat+"'),?,?,?,?,?,?,?)}");
		csmt.setString(1,CommonFunction.checkNull(dto.getCompany_id()));
		csmt.setString(2,CommonFunction.checkNull(dto.getUser_id()));
		csmt.setString(3,CommonFunction.checkNull(dto.getBusiness_date()));
		csmt.setString(4,CommonFunction.checkNull(dto.getBranch_id()));
		csmt.setString(5,CommonFunction.checkNull(""));
		csmt.setString(6,filename);
		csmt.setString(7,dto.getBatch_id());
		csmt.registerOutParameter(8, java.sql.Types.VARCHAR);
		csmt.registerOutParameter(9, java.sql.Types.VARCHAR);
		csmt.registerOutParameter(10, java.sql.Types.VARCHAR);
	
		rs = csmt.executeQuery();
		
		 flag = csmt.getString(8);
		 s2 = csmt.getString(9);
		 voucherNo=csmt.getString(10);
		 logger.info("flag:----"+flag);
		 logger.info("s2:----"+s2);
		 logger.info("voucherNo:----"+voucherNo);
		 logger.info("validation on author side GL_EXCELSHEET_VOUCHER_VALIDATION_AUTHOR");		 
		 //con.commit();
	} catch (SQLException exp) 
	{
		exp.printStackTrace();
		throw new OmniFinMarkForRollbackException(exp);
	} catch (Exception e) {
		e.printStackTrace();
		throw new OmniFinMarkForRollbackException(e);
	} finally {
		   ConnectionUploadDAO.closeConnection(con,csmt,rs);
		   voucherNo=null;
		   s2=null;
		  
	}
	return flag;
}

  public String authorAndTransfer(TempVoucherProxyDTO dto) 
  {
	  
	  logger.info("1 Company_id= "+dto.getCompany_id());
	  logger.info("2 getUser_id= "+dto.getUser_id());
	  logger.info("3 Business_date= "+dto.getBusiness_date());
	  logger.info("4 Branch_id()= "+dto.getBranch_id());
	  logger.info("5 blank ");
	  logger.info("6 Batch_id= "+dto.getBatch_id());
	  logger.info("7 SerialNo()= "+dto.getSerialNo());
	  
	  String flag = null;
	  con = ConnectionUploadDAO.getConnection();
	  
	  try { 
			//con.setAutoCommit(false);
			csmt = (CallableStatement) con.prepareCall("{CALL GL_EXCELSHEET_VOUCHER_UPLOAD_AUTHOR(?,?,STR_TO_DATE(?,'"+dateFormat+"'),?,?,?,?,?,?,?)}");
			csmt.setString(1,CommonFunction.checkNull(dto.getCompany_id()));
			csmt.setString(2,CommonFunction.checkNull(dto.getUser_id()));
			csmt.setString(3,CommonFunction.checkNull(dto.getBusiness_date()));
			csmt.setString(4,CommonFunction.checkNull(dto.getBranch_id()));
			csmt.setString(5,CommonFunction.checkNull(""));
			csmt.setString(6,dto.getBatch_id());
			csmt.setString(7,dto.getSerialNo() );
			
			csmt.registerOutParameter(8, java.sql.Types.VARCHAR);
			csmt.registerOutParameter(9, java.sql.Types.VARCHAR);
			csmt.registerOutParameter(10, java.sql.Types.VARCHAR);
		
			rs = csmt.executeQuery();
			
		    flag = csmt.getString(8);
			String s2 = csmt.getString(9);
			String voucherNo=csmt.getString(10);
			 logger.info("flag:----"+flag);
			 logger.info("s2:----"+s2);
			 logger.info("voucherNo:----"+voucherNo);
			 logger.info("inside upload in author side GL_EXCELSHEET_VOUCHER_UPLOAD_AUTHOR");		 
			 
		} catch (SQLException exp) 
		{
			exp.printStackTrace();
			throw new OmniFinMarkForRollbackException(exp);
		} catch (Exception e) {
			e.printStackTrace();
			throw new OmniFinMarkForRollbackException(e);
		} finally {
			   ConnectionUploadDAO.closeConnection(con,csmt,rs);
			   
			   
		}
	  return flag;
  }
   
  
  public ArrayList<String> getSerialNoFromTemp(VoucherUploadParametersDTO dto)
  {
	  ArrayList<String> list = new ArrayList<String>();
	  
	  con = ConnectionUploadDAO.getConnection();
	  
	  //con = ConnectionUploadDAO.getConnection();	
	  try
	  {
		  PreparedStatement ps = con.prepareStatement("select distinct serial_no from temp_gl_voucher_upload where batch_id = ?");
		  ps.setString(1, dto.getBatch_id());
		  ResultSet result = ps.executeQuery();
		  
		  while (result.next()) 
		  {
			list.add(result.getString(1));
		  }
		  result.close();
		  con.close();
	  }
	  catch (Exception e)
	  {
		e.printStackTrace();
	  }	
	 return list;
  }
  
      
   public String saveVoucherForMDB(VoucherUploadParametersDTO dto) 
   {
	  
	  String filename = dto.getFilename();	
	  
	  
	  String flag=null;
	  con = ConnectionUploadDAO.getConnection();	
	  rs=null;
	  String s2 =null;
	  String voucherNo =null;
	 
	try { 
		//con.setAutoCommit(false);
		csmt = (CallableStatement) con.prepareCall("{CALL GL_EXCELSHEET_VOUCHER_VALIDATION_MAKER(?,?,STR_TO_DATE(?,'"+dateFormat+"'),?,?,?,?,?,?,?)}");
		csmt.setString(1,CommonFunction.checkNull(dto.getCompany_id()));
		csmt.setString(2,CommonFunction.checkNull(dto.getUser_id()));
		csmt.setString(3,CommonFunction.checkNull(dto.getBusiness_date()));
		csmt.setString(4,CommonFunction.checkNull(dto.getBranch_id()));
		csmt.setString(5,CommonFunction.checkNull(""));
		csmt.setString(6,filename);
		csmt.setString(7,dto.getBatch_id());
		csmt.registerOutParameter(8, java.sql.Types.VARCHAR);
		csmt.registerOutParameter(9, java.sql.Types.VARCHAR);
		csmt.registerOutParameter(10, java.sql.Types.VARCHAR);
	
		rs = csmt.executeQuery();
		
		 flag = csmt.getString(8);
		 s2 = csmt.getString(9);
		 voucherNo=csmt.getString(10);
		 logger.info("flag:----"+flag);
		 logger.info("s2:----"+s2);
		 logger.info("voucherNo:----"+voucherNo);
				 
		 //con.commit();
	} catch (SQLException exp) 
	{
		exp.printStackTrace();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		   ConnectionUploadDAO.closeConnection(con,csmt,rs);
		   voucherNo=null;
		   s2=null;
	}
	return flag;
}

   
   
   public String saveVoucher(HttpServletRequest request,ExcelSheetUploadForm excelForm,String batch_id) 
   {
	  
	
	  String filename = ((FormFile)excelForm.getDocFile()).getFileName();	
	  
	  
	  String flag=null;
	  con = ConnectionUploadDAO.getConnection();	
	  rs=null;
	  String s2 =null;
	  String voucherNo =null;
	 
	try { 
		con.setAutoCommit(false);
		csmt = (CallableStatement) con.prepareCall("{CALL GL_EXCELSHEET_VOUCHER_VALIDATION_MAKER(?,?,STR_TO_DATE(?,'"+dateFormat+"'),?,?,?,?,?,?,?)}");
		csmt.setString(1,CommonFunction.checkNull(excelForm.getCompanyId()));
		csmt.setString(2,CommonFunction.checkNull(excelForm.getMakerId()));
		csmt.setString(3,CommonFunction.checkNull(excelForm.getBusinessDate()));
		csmt.setString(4,CommonFunction.checkNull(excelForm.getBranchId()));
		csmt.setString(5,CommonFunction.checkNull(excelForm.getSessionId()));
		csmt.setString(6,filename);
		csmt.setString(7,batch_id);
		csmt.registerOutParameter(8, java.sql.Types.VARCHAR);
		csmt.registerOutParameter(9, java.sql.Types.VARCHAR);
		csmt.registerOutParameter(10, java.sql.Types.VARCHAR);
	
		rs = csmt.executeQuery();
		
		 flag = csmt.getString(8);
		 s2 = csmt.getString(9);
		 voucherNo=csmt.getString(10);
		 logger.info("flag:----"+flag);
		 logger.info("s2:----"+s2);
		 logger.info("voucherNo:----"+voucherNo);
		 request.setAttribute("errorMsg", s2);		 
		 con.commit();
	} catch (SQLException exp) 
	{
		exp.printStackTrace();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		   ConnectionUploadDAO.closeConnection(con,csmt,rs);
		   voucherNo=null;
		   s2=null;
	}
	return flag;
}

public String saveReceipt(HttpServletRequest request,ExcelSheetUploadForm excelForm) {
	   String flag=null;
	   con = ConnectionUploadDAO.getConnection();	
	   rs=null;
	 
        String businessDate=CommonFunction.checkNull(excelForm.getBusinessDate());
		String s1 = null;
 		String s2 = null; 	

	try { 	 				    
	
		ArrayList<Object> in =new ArrayList<Object>();
 		ArrayList<Object> out =new ArrayList<Object>();
 		ArrayList outMessages = new ArrayList();
 		int noOfRejectedRecord=0;
 		int noOfUploadedRecord=0;
 		businessDate=CommonFunction.changeFormat(CommonFunction.checkNull(businessDate).trim());
 		
		in.add(CommonFunction.checkNull(excelForm.getCompanyId()));
		in.add(businessDate);
		in.add("");
		in.add(dateFormat);
		in.add(CommonFunction.checkNull(excelForm.getMakerId()));

		out.add(noOfRejectedRecord);
		out.add(noOfUploadedRecord);
		out.add(s1);
	    out.add(s2);
		
	    logger.info("RECEIPT_BULK_UPLOAD_INSTRUMENT("+in.toString()+","+out.toString()+")");  
        outMessages=(ArrayList) ConnectionUploadDAO.callSP("RECEIPT_BULK_UPLOAD_INSTRUMENT",in,out); 
        in.clear();
        in=null;
        out.clear();
        out=null;        
        noOfRejectedRecord =Integer.parseInt((String)outMessages.get(0));
 	    noOfUploadedRecord=Integer.parseInt((String)outMessages.get(1));
        s1 = (String)outMessages.get(2);
 	    s2 = (String)outMessages.get(3);
 	    flag = s1;
 	    
        logger.info("In saveReceipt() flag:----"+flag);
		logger.info("In saveReceipt() s2:----"+s2);
		 
		 request.setAttribute("errorMsg", s2);		
		 
 	     outMessages.clear();
 	     outMessages=null;
 	   
 	   
	} catch (SQLException exp) {

		exp.printStackTrace();

	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		   s1=null;
		   s2=null;
		   businessDate=null;
		   ConnectionUploadDAO.closeConnection(con,csmt,rs);
			
	}
	return flag;
}


public String saveBounce(HttpServletRequest request,ExcelSheetUploadForm excelForm) {
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

public String saveManual(HttpServletRequest request,ExcelSheetUploadForm excelForm) {
	   String flag=null;
	   con = ConnectionUploadDAO.getConnection();	
	   rs=null;
	   String s2 =null;
	try { 	 
		con.setAutoCommit(false);
		csmt = (CallableStatement) con.prepareCall("{CALL MANUAL_ADVICE_UPLOAD(?,STR_TO_DATE(?,'"+dateFormat+"'),?,?,?,?,?)}");
		
		csmt.setString(1,CommonFunction.checkNull(excelForm.getMakerId()));
		csmt.setString(2,CommonFunction.checkNull(excelForm.getBusinessDate()));		
		csmt.setString(3,CommonFunction.checkNull(excelForm.getCompanyId()));			
		
		csmt.registerOutParameter(4, java.sql.Types.VARCHAR);
		csmt.registerOutParameter(5, java.sql.Types.VARCHAR);
		csmt.registerOutParameter(6, java.sql.Types.VARCHAR);
		csmt.registerOutParameter(7, java.sql.Types.VARCHAR);
	
		rs = csmt.executeQuery();
		
		 flag = csmt.getString(6);
		 s2 = csmt.getString(7);

		 logger.info("In saveManual() flag:----"+flag);
		 logger.info("In saveManual() s2:----"+s2);
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

public String saveRate(HttpServletRequest request,ExcelSheetUploadForm excelForm) {
	   String flag=null;
	   con = ConnectionUploadDAO.getConnection();	
	   rs=null;
	   String s2 =null;

	try { 	 
		con.setAutoCommit(false);
		csmt = (CallableStatement) con.prepareCall("{CALL FLOATING_RATE_REVIEW(?,STR_TO_DATE(?,'"+dateFormat+"'),?,?)}");
		csmt.setString(1,CommonFunction.checkNull(excelForm.getMakerId()));
		csmt.setString(2,CommonFunction.checkNull(excelForm.getBusinessDate()));
		csmt.registerOutParameter(3, java.sql.Types.VARCHAR);
		csmt.registerOutParameter(4, java.sql.Types.VARCHAR);
	
		rs = csmt.executeQuery();
		
		 flag = csmt.getString(3);
		 s2 = csmt.getString(4);

		 logger.info("In saveRate() flag:----"+flag);
		 logger.info("In saveRate() s2:----"+s2);
		 
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


public String saveNHB(HttpServletRequest request,ExcelSheetUploadForm excelForm) {
	   String flag=null;
	   con = ConnectionUploadDAO.getConnection();	
	   rs=null;
	   String s2 = null;

	try { 	 
		con.setAutoCommit(false);
		csmt = (CallableStatement) con.prepareCall("{CALL NHB_SUBSIDY(?,STR_TO_DATE(?,'"+dateFormat+"'),?,?,?,?,?)}");
		csmt.setString(1,CommonFunction.checkNull(excelForm.getMakerId()));
		csmt.setString(2,CommonFunction.checkNull(excelForm.getBusinessDate()));
		csmt.setString(3,"");		

		csmt.registerOutParameter(4, java.sql.Types.VARCHAR);
		csmt.registerOutParameter(5, java.sql.Types.VARCHAR);
		csmt.registerOutParameter(6, java.sql.Types.VARCHAR);
		csmt.registerOutParameter(7, java.sql.Types.VARCHAR);
	
		rs = csmt.executeQuery();
		
		 flag = csmt.getString(6);
		 s2 = csmt.getString(7);

		 logger.info("In saveNHB() flag:----"+flag);
		 logger.info("In saveNHB() s2:----"+s2);
		 
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



public String saveStationary(HttpServletRequest request,ExcelSheetUploadForm excelForm) {
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



public String saveBankStmt(HttpServletRequest request,ExcelSheetUploadForm excelForm) {
	   String flag=null;
	   con = ConnectionUploadDAO.getConnection();	
	   rs=null;
	 
	   String userId=CommonFunction.checkNull(excelForm.getMakerId());
	   String businessDate=CommonFunction.checkNull(excelForm.getBusinessDate());

	try { 	 
		con.setAutoCommit(false);
		csmt.setString(1,userId);
		csmt.setString(2,businessDate);
		csmt.setString(3,"");		

		csmt.registerOutParameter(4, java.sql.Types.VARCHAR);
		csmt.registerOutParameter(5, java.sql.Types.VARCHAR);
		csmt.registerOutParameter(6, java.sql.Types.VARCHAR);
		csmt.registerOutParameter(7, java.sql.Types.VARCHAR);
	
		rs = csmt.executeQuery();
		
		 flag = csmt.getString(6);
		 String s2 = csmt.getString(7);

		 logger.info("In saveBankStmt() flag:----"+flag);
		 logger.info("In saveBankStmt() s2:----"+s2);
		 
		 request.setAttribute("errorMsg", s2);		 
		 con.commit();
	} catch (SQLException exp) {

		exp.printStackTrace();

	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		   userId=null;
		   businessDate=null;
		   ConnectionUploadDAO.closeConnection(con,csmt,rs);
			
	}
	return flag;
}

public ArrayList reportAdHoc(ExcelSheetUploadForm excelForm,String functionId) 
{
	logger.info("in reportAdHoc() of DumpDownLoadDAOImpl");
	ArrayList resultList=new ArrayList();
	StringBuffer sb = new StringBuffer();
	logger.info("In reportAdHoc() functionId.... "+functionId);
	
	 logger.info("reportAdHoc for GL VOUCHER UPLOAD.... "+functionId);
	 sb.append(" select Serial_no,BRANCH_ID,MODULE_ID,VOUCHER_DATE,VOUCHER_TYPE,PRODUCT_ID,if(department=0,'',department) as department,cheque_invoice_no,REFERENCE_NO,NARRATION,");
	 sb.append(" CONCAT(STAGE_ID,'') as Transaction,off_balance,Manual_Voucher,RECIPT_NO,LEDGER_ID,SUBLEDGER_ID,LEDGER_REMARKS,DR_AMOUNT,CR_AMOUNT,SEQUENCE_NO,ERROR_FLAG,ERROR_MSG,VOUCHER_NO,");
	 sb.append(" UPLOAD_ID,BATCH_ID");
	 sb.append(" from temp_gl_voucher_upload where batch_id = '"+excelForm.getBatch_id()+"'");
	 sb.append(" ");
	
	 	 
	logger.info("To Open Output in reportAdHoc() Query is... "+ sb.toString());	
	
	try
	{
		 resultList = ConnectionUploadDAO.sqlColumnWithResult(sb.toString());
		 
	}
	catch(Exception e)
	{e.printStackTrace();}
	finally{
		sb=null;
	}
	logger.info("size"+resultList.size());
	return resultList;
}

public ArrayList downloadDump(String batch)
{
	logger.info("inside downloadDump(String batch)");
	ArrayList resultList=new ArrayList();
	String query = "select h.branch_id,h.module_id,h.voucher_date,h.voucher_type,h.product_id,h.department," +
			" ifnull(h.cheque_invoice_no,'') cheque_invoice_no,ifnull(h.reference_no,'') reference_no,h.narration," +
			" h.off_balance,h.manual_voucher,ifnull(h.recipt_no,'') recipt_no,d.ledger_id," +
			" d.subledger_id, ifnull(d.ledger_remarks,'') ledger_remarks,d.dr_amount,d.cr_amount," +
			" d.sequence_no,d.voucher_no  from gl_voucher_hdr h join gl_voucher_dtl d on h.voucher_no = d.voucher_no where VOUCHER_UPLOAD_ID = '"+batch+"' "; 
	logger.info("dump query =="+query);
	try
	{
		 resultList = ConnectionUploadDAO.sqlColumnWithResult(query);
		 
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	
	logger.info("size"+resultList.size());
	return resultList;
	
	
	
}



// this method uploads voucher in csv format 
public boolean docUploadForExcel(HttpServletRequest request, FormFile docFile) {
	boolean status=false;
	String fileName="";
	String filePath="";
	String message="";
	String filePathWithName="";
	String ktrpath = "";
	String fileNameChange="";
	HttpSession session=request.getSession(false);
	UserObject sessUser = (UserObject) session.getAttribute("userobject");
	String user_Id="";
	try{
		if(sessUser!=null){
			user_Id = sessUser.getUserId();
		}
		String query="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='UPLOAD_PATH'";
		String ktrquery = "select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='GL_CSV_EXCELUPLOAD'";
		String rpt=ConnectionUploadDAO.singleReturn(query);
		ktrpath = ConnectionUploadDAO.singleReturn(ktrquery);
		ktrpath = ktrpath.concat("\\voucher-excel-uploading.ktr");
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
	request.setAttribute("ktrpath",ktrpath); 
	fileName=null;
	filePath=null;
	message=null;
	filePathWithName=null;
	fileNameChange=null;
	return status;		
}

public boolean uploadCsv(HttpServletRequest request,HttpServletResponse response,String batch_id,String strFileName,ExcelSheetUploadForm  excelForm)
{
		logger.info("batch_id ==========================================="+batch_id);
	  // con = ConnectionUploadDAO.getConnection();	
	   ArrayList     alDeleteQuery  = new ArrayList(1);
	   StringBuffer makerID=null;
	   boolean status=false;
	   String fileNameWithPath=excelForm.getFilePathWithName();
	   try { 
		   	makerID=new StringBuffer(CommonFunction.checkNull(excelForm.getMakerId()));
		  	String query1="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='GL_CSV_EXCELUPLOAD'";
			String rpt1=ConnectionUploadDAO.singleReturn(query1);
			query1=null;
		   	String strDeleteQuery = "DELETE FROM temp_gl_voucher_upload WHERE user_id = '"+makerID+"' and batch_id = '"+batch_id+"'";
		   	alDeleteQuery.add(strDeleteQuery);
		   	boolean status1=ConnectionUploadDAO.sqlInsUpdDelete(alDeleteQuery);
			   	
	     	  if(status1) {
	     		  logger.info("Row is deleted....");
	     	  }else{
	     		  logger.info("Row is not deleted......");
	     	  }
	     	
	     	 
	     	  
	     	  KettleEnvironment.init(false); 
	     	  EnvUtil.environmentInit(); 
	     	  TransMeta transMeta = new TransMeta(rpt1.concat("\\voucher-excel-uploading.ktr")); 
	     	  Trans trans = new Trans(transMeta); 
	     	 
	     	  // set parameter. same can be used inside steps in transformation 
		      trans.setParameterValue("filePathWithName",fileNameWithPath);
		      trans.setParameterValue("userID",makerID.toString());
		      trans.setParameterValue("batch_id",batch_id);
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
    	  logger.info("Problem is ---->"+e.getMessage());
	}finally {
			makerID=null;
			ConnectionUploadDAO.closeConnection(con,csmt,rs);
			
	} 

	return status;
}


public boolean uploadCsv_Receipt(HttpServletRequest request,HttpServletResponse response,String strFileName,ExcelSheetUploadForm  excelForm)
{
	  // con = ConnectionUploadDAO.getConnection();	
	   ArrayList alDeleteQuery  = new ArrayList(1);
	   StringBuffer makerID=null;
	   boolean status=false;
	   String fileNameWithPath=excelForm.getFilePathWithName();
	   try { 
		   	makerID=new StringBuffer(CommonFunction.checkNull(excelForm.getMakerId()));
		   	String query1="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='GL_CSV_EXCELUPLOAD'";
			String rpt1=ConnectionUploadDAO.singleReturn(query1);
			query1=null;
			
		   	String strDeleteQuery = "DELETE FROM CR_INSTRUMENT_DTL_TEMP WHERE MAKER_ID = '"+makerID+"' ";
		   	alDeleteQuery.add(strDeleteQuery);
		   	boolean status1=ConnectionUploadDAO.sqlInsUpdDelete(alDeleteQuery);
			   	
	     	  if(status1) {
	     		  logger.info("In uploadCsv_Receipt() Rows is deleted....");
	     	  }else{
	     		  logger.info("In uploadCsv_Receipt Row is not deleted......");
	     	  }
	     	  
	     	  KettleEnvironment.init(false); 
	     	  EnvUtil.environmentInit(); 
	     	  TransMeta transMeta = new TransMeta(rpt1.concat("\\receipt-excel-uploading.ktr")); 
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
    	  logger.info("In uploadCsv_Receipt() Problem is ---->"+e.getMessage());
	}finally {
			makerID=null;
			ConnectionUploadDAO.closeConnection(con,csmt,rs);
			
	} 

	return status;
}


public boolean uploadCsv_Bounce(HttpServletRequest request,HttpServletResponse response,String strFileName,ExcelSheetUploadForm  excelForm)
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
	     		     	  
	     	  KettleEnvironment.init(false); 
	     	  EnvUtil.environmentInit(); 
	     	  TransMeta transMeta = new TransMeta(rpt1.concat("\\bounce-excel-uploading.ktr")); 
	     	  Trans trans = new Trans(transMeta); 

	     	// set parameter. same can be used inside steps in transformation 
		     	 trans.setParameterValue("filePathWithName",fileNameWithPath);
		     	 trans.setParameterValue("userID",makerID.toString());
		     	 
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


public boolean uploadCsv_Rate(HttpServletRequest request,HttpServletResponse response,String strFileName,ExcelSheetUploadForm  excelForm)
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
			
		   	String strDeleteQuery = "DELETE FROM CR_FLOATING_RATE_TMP WHERE MAKER_ID = '"+makerID+"' ";
		   	alDeleteQuery.add(strDeleteQuery);
		   	boolean status1=ConnectionUploadDAO.sqlInsUpdDelete(alDeleteQuery);
			   	
	     	  if(status1) {
	     		  logger.info("IN uploadCsv_Rate() Row is deleted....");
	     	  }else{
	     		  logger.info("IN uploadCsv_Rate() Row is not deleted......");
	     	  }
	     	
	     	  
	     	  KettleEnvironment.init(false); 
	     	  EnvUtil.environmentInit(); 
	     	  TransMeta transMeta = new TransMeta(rpt1.concat("\\rate-excel-uploading.ktr")); 
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
    	  logger.info("In uploadCsv_Rate() Problem is ---->"+e.getMessage());
	}finally {
			makerID=null;
			ConnectionUploadDAO.closeConnection(con,csmt,rs);
			
	} 

	return status;
}



public boolean uploadCsv_Manual(HttpServletRequest request,HttpServletResponse response,String strFileName,ExcelSheetUploadForm  excelForm)
{
	  // con = ConnectionUploadDAO.getConnection();	
	   ArrayList  alDeleteQuery  = new ArrayList(1);
	   StringBuffer makerID=null;
	   boolean status=false;
	   String fileNameWithPath=excelForm.getFilePathWithName();
	   try { 
		   	makerID=new StringBuffer(CommonFunction.checkNull(excelForm.getMakerId()));
			String query1="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='GL_CSV_EXCELUPLOAD'";
			String rpt1=ConnectionUploadDAO.singleReturn(query1);
			query1=null;
			
		   	String strDeleteQuery = "DELETE FROM CR_MANUAL_ADVICE_TMP WHERE MAKER_ID = '"+makerID+"' ";
		   	alDeleteQuery.add(strDeleteQuery);
		   	boolean status1=ConnectionUploadDAO.sqlInsUpdDelete(alDeleteQuery);
			   	
	     	  if(status1) {
	     		  logger.info("IN uploadCsv_Manual() Row is deleted....");
	     	  }else{
	     		  logger.info("IN uploadCsv_Manual() Row is not deleted......");
	     	  }
	     	  
	     	  KettleEnvironment.init(false); 
	     	  EnvUtil.environmentInit(); 
	     	  TransMeta transMeta = new TransMeta(rpt1.concat("\\manual-excel-uploading.ktr")); 
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
    	  logger.info("In uploadCsv_Manual() Problem is ---->"+e.getMessage());
	}finally {
			makerID=null;
			ConnectionUploadDAO.closeConnection(con,csmt,rs);
			
	} 

	return status;
}


public boolean uploadCsv_NHB(HttpServletRequest request,HttpServletResponse response,String strFileName,ExcelSheetUploadForm  excelForm)
{
	 //  con = ConnectionUploadDAO.getConnection();	
	   ArrayList     alDeleteQuery  = new ArrayList(1);
	   StringBuffer makerID=null;
	   boolean status=false;
	   String fileNameWithPath=excelForm.getFilePathWithName();
	   try { 
		   	makerID=new StringBuffer(CommonFunction.checkNull(excelForm.getMakerId()));
			String query1="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='GL_CSV_EXCELUPLOAD'";
			String rpt1=ConnectionUploadDAO.singleReturn(query1);
			query1=null;
			
		   	String strDeleteQuery = "DELETE FROM CR_NHB_SUBSIDY_TMP WHERE MAKER_ID = '"+makerID+"' ";
		   	alDeleteQuery.add(strDeleteQuery);
		   	boolean status1=ConnectionUploadDAO.sqlInsUpdDelete(alDeleteQuery);
			   	
	     	  if(status1) {
	     		  logger.info("In uploadCsv_NHB() Row is deleted....");
	     	  }else{
	     		  logger.info("In uploadCsv_NHB() Row is not deleted......");
	     	  }
	     	
	     	  
	     	  KettleEnvironment.init(false); 
	     	  EnvUtil.environmentInit(); 
	     	  TransMeta transMeta = new TransMeta(rpt1.concat("\\nhb-excel-uploading.ktr")); 
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
    	  logger.info("In uploadCsv_NHB() Problem is ---->"+e.getMessage());
	}finally {
			makerID=null;
			ConnectionUploadDAO.closeConnection(con,csmt,rs);
			
	} 

	return status;
}


public boolean uploadCsv_Securitization(HttpServletRequest request,HttpServletResponse response,String strFileName,ExcelSheetUploadForm  excelForm)
{
	 //  con = ConnectionUploadDAO.getConnection();	
	   ArrayList     alDeleteQuery  = new ArrayList(1);
	   StringBuffer makerID=null;
	   boolean status=false;
	   String fileNameWithPath=excelForm.getFilePathWithName();
	   try { 
		   	makerID=new StringBuffer(CommonFunction.checkNull(excelForm.getMakerId()));
			String query1="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='GL_CSV_EXCELUPLOAD'";
			String rpt1=ConnectionUploadDAO.singleReturn(query1);
			query1=null;			
		   	String strDeleteQuery = "DELETE FROM POOL_CREATION_UPDATE_TMP WHERE MAKER_ID = '"+makerID+"' ";
		   	alDeleteQuery.add(strDeleteQuery);
		   	boolean status1=ConnectionUploadDAO.sqlInsUpdDelete(alDeleteQuery);
			   	
	     	  if(status1) {
	     		  logger.info("In uploadCsv_Securitization() Row is deleted....");
	     	  }else{
	     		  logger.info("In uploadCsv_Securitization() Row is not deleted......");
	     	  }
	     	
	     	  
	     	  KettleEnvironment.init(false); 
	     	  EnvUtil.environmentInit(); 
	     	  TransMeta transMeta = new TransMeta(rpt1.concat("\\securitization-excel-uploading.ktr")); 
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
    	  logger.info("In uploadCsv_Securitization() Problem is ---->"+e.getMessage());
	}finally {
			makerID=null;
			ConnectionUploadDAO.closeConnection(con,csmt,rs);
			
	} 

	return status;
}

public boolean uploadCsv_Stationary(HttpServletRequest request,HttpServletResponse response,String strFileName,ExcelSheetUploadForm  excelForm)
{
	  // con = ConnectionUploadDAO.getConnection();	
	   ArrayList     alDeleteQuery  = new ArrayList(1);
	   StringBuffer makerID=null;
	   boolean status=false;
	   String fileNameWithPath=excelForm.getFilePathWithName();
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


public boolean uploadCsv_BantStmt(HttpServletRequest request,HttpServletResponse response,String strFileName,ExcelSheetUploadForm  excelForm)
{
	//   con = ConnectionUploadDAO.getConnection();	
	   ArrayList     alDeleteQuery  = new ArrayList(1);
	   StringBuffer makerID=null;
	   boolean status=false;
	   String fileNameWithPath=excelForm.getFilePathWithName();
	   try { 
		   	makerID=new StringBuffer(CommonFunction.checkNull(excelForm.getMakerId()));
			String query1="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='GL_CSV_EXCELUPLOAD'";
			String rpt1=ConnectionUploadDAO.singleReturn(query1);
			query1=null;
			
		   	String strDeleteQuery = "DELETE FROM TEMP_GL_RECON_UPLOAD WHERE USER_ID = '"+makerID+"' ";
		   	alDeleteQuery.add(strDeleteQuery);
		   	boolean status1=ConnectionUploadDAO.sqlInsUpdDelete(alDeleteQuery);
			   	
	     	  if(status1) {
	     		  logger.info("In uploadCsv_BantStmt() Row is deleted....");
	     	  }else{
	     		  logger.info("In uploadCsv_BantStmt() Row is not deleted......");
	     	  }
	     	
	     	  
	     	  KettleEnvironment.init(false); 
	     	  EnvUtil.environmentInit(); 
	     	  TransMeta transMeta = new TransMeta(rpt1.concat("\\bankstmt-excel-uploading.ktr")); 
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
    	  logger.info("In uploadCsv_BantStmt() Problem is ---->"+e.getMessage());
	}finally {
			makerID=null;
			ConnectionUploadDAO.closeConnection(con,csmt,rs);
			
	} 

	return status;
}



public boolean uploadCsv_PresentationProcess(HttpServletRequest request,HttpServletResponse response,String strFileName,ExcelSheetUploadForm  excelForm)
{
	 //  con = ConnectionUploadDAO.getConnection();	
	   ArrayList     alDeleteQuery  = new ArrayList(1);
	   StringBuffer makerID=null;
	   boolean status=false;
	   String fileNameWithPath=excelForm.getFilePathWithName();
	   try { 
		   	makerID=new StringBuffer(CommonFunction.checkNull(excelForm.getMakerId()));
			String query1="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='GL_CSV_EXCELUPLOAD'";
			String rpt1=ConnectionUploadDAO.singleReturn(query1);
			query1=null;
			
		   	String strDeleteQuery = "DELETE FROM PRESENTATION_PROCESS_UPLOAD_TEMP WHERE MAKER_ID = '"+makerID+"' ";
		   	alDeleteQuery.add(strDeleteQuery);
		   	boolean status1=ConnectionUploadDAO.sqlInsUpdDelete(alDeleteQuery);
			   	
	     	  if(status1) {
	     		  logger.info("In uploadCsv_PresentationProcess() Row is deleted....");
	     	  }else{
	     		  logger.info("In uploadCsv_PresentationProcess() Row is not deleted......");
	     	  }	     	
	     	  
	     	  KettleEnvironment.init(false); 
	     	  EnvUtil.environmentInit(); 
	     	  TransMeta transMeta = new TransMeta(rpt1.concat("\\presentationprocess-excel-uploading.ktr")); 
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
    	  logger.info("In uploadCsv_PresentationProcess() Problem is ---->"+e.getMessage());
	}finally {
			makerID=null;
			ConnectionUploadDAO.closeConnection(con,csmt,rs);
			
	} 

	return status;
}

public boolean uploadCsv_SectorPurpose(HttpServletRequest request,HttpServletResponse response,String strFileName,ExcelSheetUploadForm  excelForm)
{
	//   con = ConnectionUploadDAO.getConnection();	
	   ArrayList     alDeleteQuery  = new ArrayList(1);
	   StringBuffer makerID=null;
	   boolean status=false;
	   String fileNameWithPath=excelForm.getFilePathWithName();
	   try { 
		   	makerID=new StringBuffer(CommonFunction.checkNull(excelForm.getMakerId()));
			String query1="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='GL_CSV_EXCELUPLOAD'";
			String rpt1=ConnectionUploadDAO.singleReturn(query1);
			query1=null;
			
		   	String strDeleteQuery = "DELETE FROM SECTOR_PURPOSE_UPLOAD_TEMP WHERE USER_ID = '"+makerID+"' ";
		   	alDeleteQuery.add(strDeleteQuery);
		   	boolean status1=ConnectionUploadDAO.sqlInsUpdDelete(alDeleteQuery);
			   	
	     	  if(status1) {
	     		  logger.info("In uploadCsv_SectorPurpose() Row is deleted....");
	     	  }else{
	     		  logger.info("In uploadCsv_SectorPurpose() Row is not deleted......");
	     	  }
	     	
	     	  
	     	  KettleEnvironment.init(false); 
	     	  EnvUtil.environmentInit(); 
	     	  TransMeta transMeta = new TransMeta(rpt1.concat("\\SECTOR_UPLOAD.ktr")); 
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
    	  logger.info("In uploadCsv_SectorPurpose() Problem is ---->"+e.getMessage());
	}finally {
			makerID=null;
			ConnectionUploadDAO.closeConnection(con,csmt,rs);
			
	} 

	return status;
}

public String saveSectorPurpose(HttpServletRequest request,ExcelSheetUploadForm excelForm) {
	
	String flag=null;
	   con = ConnectionUploadDAO.getConnection();	
	   rs=null;
	   String s2 =null;
	   
	try { 	 
		logger.info("dateFormat... "+dateFormat);
		con.setAutoCommit(false);
		csmt = (CallableStatement) con.prepareCall("{CALL MANUAL_SECTOR_PURPOSE_UPLOAD(?,STR_TO_DATE(?,'"+dateFormat+"'),?,?,?,?,?,?,?)}");
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

public boolean uploadCsv_notepadUpload(HttpServletRequest request,HttpServletResponse response,String strFileName,ExcelSheetUploadForm  excelForm)
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
		   	String strDeleteQuery = "DELETE FROM NOTEPAD_UPLOAD_TMP WHERE MAKER_ID = '"+makerID+"' ";
		   	alDeleteQuery.add(strDeleteQuery);
		   	boolean status1=ConnectionUploadDAO.sqlInsUpdDelete(alDeleteQuery);
			   	
	     	  if(status1) {
	     		  logger.info("IN uploadCsv_notepadUpload() Row is deleted....");
	     	  }else{
	     		  logger.info("IN uploadCsv_notepadUpload() Row is not deleted......");
	     	  }
	     	
	     	  
	     	  KettleEnvironment.init(false); 
	     	  EnvUtil.environmentInit(); 
	     	  TransMeta transMeta = new TransMeta(rpt1.concat("\\notepad_upload.ktr"));  
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

public String save_NotepadUpload(HttpServletRequest request,ExcelSheetUploadForm excelForm) {
	
	String s1=null;
	String rejectFlg = null;
	String uplodFlg = null;
	   con = ConnectionUploadDAO.getConnection();	
	   rs=null;
	   String s2 =null;
	   
	try { 	 
		logger.info("dateFormat... "+dateFormat);
		con.setAutoCommit(false);
		csmt = (CallableStatement) con.prepareCall("{CALL NOTEPAD_UPLOAD(?,STR_TO_DATE(?,'"+dateFormat+"'),?,?,?,?)}");
		
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
		
		
		 logger.info("In saveBounce() rejectFlg:----"+rejectFlg);
		 logger.info("In saveBounce() uplodFlg:----"+uplodFlg);
		 logger.info("In saveBounce() s1:----"+s1);
		 logger.info("In saveBounce() s2:----"+s2);
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


public boolean uploadCsv_cmsUpload(HttpServletRequest request,HttpServletResponse response,String strFileName,ExcelSheetUploadForm  excelForm)
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


public String save_CmsUpload(HttpServletRequest request,ExcelSheetUploadForm excelForm) {
	
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


//Nishant space starts for Instruments Upload
public boolean cmsInstrumentUpload(HttpServletRequest request,HttpServletResponse response,String strFileName,ExcelSheetUploadForm  excelForm)
{
	 //  con = ConnectionUploadDAO.getConnection();	
	   ArrayList alDeleteQuery  = new ArrayList(1);
	   StringBuffer makerID=null;
	   boolean status=false;
	   String fileNameWithPath=excelForm.getFilePathWithName();
	   logger.info("fileNameWithPath : " + fileNameWithPath);
	   try { 
		   	makerID=new StringBuffer(CommonFunction.checkNull(excelForm.getMakerId()));
			String query1="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='GL_CSV_EXCELUPLOAD'";
			String rpt1=ConnectionUploadDAO.singleReturn(query1);
			query1=null;
			String businessDate=CommonFunction.checkNull(excelForm.getBusinessDate());
		   	String strDeleteQuery = "DELETE FROM INSTRUMENT_MARKING_UPLOAD_TEMP WHERE MAKER_ID = '"+makerID+"' ";
		   	alDeleteQuery.add(strDeleteQuery);
		   	boolean status1=ConnectionUploadDAO.sqlInsUpdDelete(alDeleteQuery);
			   	
	     	  if(status1) {
	     		  logger.info("IN cmsInstrumentUpload() Row is deleted....");
	     	  }else{
	     		  logger.info("IN cmsInstrumentUpload() Row is not deleted......");
	     	  }
	     	
	     	  
	     	  KettleEnvironment.init(false); 
	     	  EnvUtil.environmentInit(); 
	     	  TransMeta transMeta = new TransMeta(rpt1.concat("\\instrument_marking_upload.ktr")); 
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
    	  e.printStackTrace();
    	  logger.info("In cmsInstrumentUpload() Problem is ---->"+e.getMessage());
	}finally {
			makerID=null;
			ConnectionUploadDAO.closeConnection(con,csmt,rs);
			
	} 
	logger.info("In cmsInstrumentUpload() status : " + status);
	return status;
}


public String saveCmsInstrumentUpload(HttpServletRequest request,ExcelSheetUploadForm excelForm) {
	
	String flag=null;
	   con = ConnectionUploadDAO.getConnection();	
	   rs=null;
	   String s2 =null;
	   
	try { 	 
		logger.info("dateFormat... "+dateFormat);
		con.setAutoCommit(false);
		csmt = (CallableStatement) con.prepareCall("{CALL INSTRUMENT_MARKING_UPLOAD(?,STR_TO_DATE(?,'"+dateFormat+"'),?,?,?,?,?,?,?)}");
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

//Nishant space ends for Instruments Upload

}
		  
		   
		
	   
	    

	    
   
   
   