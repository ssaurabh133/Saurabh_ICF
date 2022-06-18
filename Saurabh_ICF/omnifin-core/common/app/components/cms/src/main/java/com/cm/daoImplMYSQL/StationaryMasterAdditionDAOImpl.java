//<!--Author Name :- Mradul Agarwal-->
//<!--Date of Creation : 01_March_2013-->
//<!--Purpose :-  Stationary Addition Screen-->

package com.cm.daoImplMYSQL;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;


import com.cm.dao.StationaryMasterDAO;
import com.cm.actionform.StationaryMasterForm;

import com.connect.CommonFunction;
import com.connect.ConnectionDAO;



import org.apache.commons.lang.StringEscapeUtils;

public class StationaryMasterAdditionDAOImpl implements StationaryMasterDAO
{
	private static final Logger logger=Logger.getLogger(StationaryMasterAdditionDAOImpl.class.getName());
	StationaryMasterForm stationaryForm = null;
	 ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	    String dateFormatWithTime = resource.getString("lbl.dateWithTimeInDao");
	    String dateFormat = resource.getString("lbl.dateInDao");
	    Connection con = null;
	    PreparedStatement ptmt = null;
	    ResultSet rs = null;
	    java.sql.Statement stmt = null;
	    CallableStatement csmt = null;
	    
	    
	    public int checkBookNo(StationaryMasterForm stationaryMasterForm) {
		{
			int count=0;
			String[] bookNO=stationaryMasterForm.getBookNo();
			String bookNoL=stationaryMasterForm.getBookNoL();
			String []stationaryArrayId=stationaryMasterForm.getStationaryArrayId();
			logger.info("bookNoL "+bookNoL);
			try
			{
				for(int i=0;i<bookNO.length;i++){
					
					
					String query=ConnectionDAO.singleReturn(" select count(1) from cr_stationary_dtl where Book_No='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(bookNO[i]))+"' ");
					if(CommonFunction.checkNull(bookNoL).equalsIgnoreCase("L"))
					{
						if(!CommonFunction.checkNull(stationaryArrayId[i]).equalsIgnoreCase(""))
						{
							query=ConnectionDAO.singleReturn(" select count(1) from cr_stationary_dtl where Book_No='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(bookNO[i]))+"' and stationary_id<>'"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(stationaryArrayId[i]))+"' ");
						}
					}
					logger.info("query"+ query);
					if(!(CommonFunction.checkNull(query).equals("0")))
					{
				    	count=1;
						break;
					}
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			logger.info("count"+ count);
			return count;
		}
	    }
	
	public List<StationaryMasterForm> getBankStatement(StationaryMasterForm stationaryForm) {
				logger.info("in --> StationaryMasterAdditionDAOImpl");
				con = ConnectionDAO.getConnection();
		   List<StationaryMasterForm> bankList = new ArrayList<StationaryMasterForm>();
		   try {	
				String query="SELECT Distinct cbm.bank_id,bank_name FROM COM_BANK_ACCOUNTS_M cbm LEFT JOIN COM_BANK_M cm on cbm.BANK_ID=cm.BANK_ID where account_type<>'S'";
				stmt = con.createStatement();
				rs = stmt.executeQuery(query);
		   while (rs.next()) {

				StationaryMasterForm form = new StationaryMasterForm();
				form.setBankId(rs.getString("bank_id"));
				form.setBankName(rs.getString("bank_name"));
				bankList.add(form);
			}
				logger.info("in StationaryMasterAdditionDAOImpl return banklist...."+ bankList);
	   }	
	     catch(SQLException e) {
	    	 	e.printStackTrace();
		}finally {
				ConnectionDAO.closeConnection(con, stmt, rs);
	    }return bankList;
	 }

	public boolean getsaveBankData(StationaryMasterForm stationaryMasterForm) 
	{	
		  logger.info("getsaveBankData: ");
		  Connection con = null;
	      PreparedStatement pstm = null;
	      int flag=0;
		  int message=0;
		  boolean status=false;
		  String RecStatus="";
		
		  String[] bookNo =stationaryMasterForm.getBookNo();
		  String[] instrumentNo = stationaryMasterForm.getInstruNo();
		  String[] instrumentFrom = stationaryMasterForm.getInstruFrom();
		  String[] instrumentTo = stationaryMasterForm.getInstruTo();
		  String BookNoL = stationaryMasterForm.getBookNoL();
		 	
		  logger.info("bookNoL " + stationaryMasterForm.getBookNoL());
		  logger.info("INSTRUMENT_NO.." + stationaryMasterForm.getInstruNo());
		  logger.info("InstruFrom " + stationaryMasterForm.getInstruFrom());
		  logger.info("InstruTo " + stationaryMasterForm.getInstruTo());
		  logger.info("BANK_NAME " + stationaryMasterForm.getBankName());
		  logger.info("BANK_NAME " + stationaryMasterForm.getStatus());
		  if(stationaryMasterForm.getStatus()!=null &&
				  stationaryMasterForm.getStatus().equalsIgnoreCase("on")){
			  RecStatus="A"; 
				 }
		else{
			RecStatus="X"; 
			}
		  logger.info("RecStatus " + RecStatus);
		 

		  if( !CommonFunction.checkNull(BookNoL).equals("L")) 
		  {		  
	   try {
	    	  int stationaryID=0;
		    	  con = ConnectionDAO.getConnection();
				
	    	  	  String sql = "insert into cr_stationary_m(COMPANY_ID,ADDITION_DATE, BOOK_TYPE, BANK_NAME)"
                                   + "values(?,STR_TO_DATE(?, '"+dateFormatWithTime+"'),?,?)";

	    	  	    pstm = con.prepareStatement(sql);
	    	  	  	pstm.setString(1, CommonFunction.checkNull(stationaryMasterForm.getCompanyID()));
	    	  	  	pstm.setString(2, CommonFunction.checkNull(stationaryMasterForm.getAdditionDate()));
	    	  	  	pstm.setString(3, CommonFunction.checkNull(stationaryMasterForm.getCheckType()));
	    	  	  	pstm.setString(4, CommonFunction.checkNull(stationaryMasterForm.getBankName()));	    	
	    	  	    logger.info("bookNo.." + stationaryMasterForm.getBookNo());
	    	  	    flag = pstm.executeUpdate();
					logger.info("flag:----"+flag); 
		  if(flag>0){
				  	status=true;
				  	stmt=con.createStatement();
			        logger.info("flag >0:--"+flag);
			  		rs=stmt.executeQuery("select LAST_INSERT_ID() from cr_stationary_m");
		 while(rs.next()){
			  		stationaryID=rs.getInt(1);			  		
			  	}
		
		 for(int i=0;i<bookNo.length;i++){
				  
	     			  String sql2 ="insert into cr_stationary_dtl ( BOOK_NO,STATIONARY_ID,ADDITION_DATE, INSTRUMENT_NO,INSTRUMENT_FROM,INSTRUMENT_TO,REC_STATUS,MAKER_ID,MAKER_DATE,ALLOCATED_TO,ALLOCATION_DATE  )"+ 
					  				"values(?,?,STR_TO_DATE(?, '"+dateFormatWithTime+"'),?,?,?,?,?,STR_TO_DATE(?, '"+dateFormatWithTime+"'),?,STR_TO_DATE(?, '"+dateFormatWithTime+"'))";
					 
					  logger.info("bookNo length" + bookNo.length);
					  pstm = con.prepareStatement(sql2);			
					  pstm.setString(1,bookNo[i]);
					  pstm.setInt(2,stationaryID);
					  pstm.setString(3,CommonFunction.checkNull(stationaryMasterForm.getAdditionDate()));
					  if(instrumentNo[i].equals("") || instrumentNo[i] == null)
						  pstm.setString(4,null);
					  else
						  pstm.setString(4,instrumentNo[i]);
					  if(instrumentFrom[i].equals("") || instrumentFrom[i] == null)
						  pstm.setString(5,null);
					  else
						  pstm.setString(5,instrumentFrom[i]);
					  if(instrumentTo[i].equals("") || instrumentTo[i] == null)
						  pstm.setString(6,null);
					  else					
					  pstm.setString(6,instrumentTo[i]);
					  pstm.setString(7,RecStatus);
					  pstm.setString(8,CommonFunction.checkNull(stationaryMasterForm.getMakerId()));
					  pstm.setString(9,CommonFunction.checkNull(stationaryMasterForm.getMakerDate()));
					  pstm.setString(10,CommonFunction.checkNull(stationaryMasterForm.getAllocto()));
					  pstm.setString(11,CommonFunction.checkNull(stationaryMasterForm.getAllocdate()));
					  																									
					  logger.info("query:---   mradul "+ sql2.toString());
					  message = pstm.executeUpdate();
					  if(message>0)
					  {
						  status=true;
					  }
			  	}
		       
		      }
		    }catch (SQLException ex){
		    		ex.printStackTrace();	
			}finally{
					ConnectionDAO.closeConnection(con, pstm, rs);
			}
					logger.info("Status in daoImpl: " + status);
					return status;
		}
		else{			
	
		
		try {	
			    String[] bookNo2 =stationaryMasterForm.getBookNo();
			    String[] InstruNo2 	= stationaryMasterForm.getInstruNo();
			    String[] InstruFrom2 =stationaryMasterForm.getInstruFrom();
			    String[] InstruTo2   = stationaryMasterForm.getInstruTo();
			   // String Status2 	= stationaryMasterForm.getStatus();		    
			    
				logger.info("book no 2:::::"  + bookNo2.toString());
				//logger.info("Status::::::::"  + Status2.toString());
				logger.info("InstruNo2:::::"  + InstruNo2.toString());
				logger.info("InstruFrom2::::"  + InstruFrom2.toString());
				logger.info("InstruNo2::::::"  + InstruTo2.toString());
			
				con = ConnectionDAO.getConnection();	
				
				 for(int i=0;i<bookNo.length;i++){			
				
			 String sql3= (" update cr_stationary_dtl set INSTRUMENT_NO=?,INSTRUMENT_FROM=?, INSTRUMENT_TO=?, REC_STATUS=?,MAKER_ID =?,MAKER_DATE=STR_TO_DATE(?, '"+dateFormatWithTime+"'), ALLOCATED_TO=? ,ALLOCATION_DATE=STR_TO_DATE(?, '"+dateFormatWithTime+"') where BOOK_NO='"+bookNo2[i]+"'");
				
				  pstm = con.prepareStatement(sql3);	
			      pstm.setString(1,CommonFunction.checkNull(InstruNo2[i]));
				  pstm.setString(2,CommonFunction.checkNull(InstruFrom2[i]));
				  pstm.setString(3,CommonFunction.checkNull(InstruTo2[i]));
				  pstm.setString(4,CommonFunction.checkNull(RecStatus));
				  pstm.setString(5,CommonFunction.checkNull(stationaryMasterForm.getMakerId()));
				  pstm.setString(6,CommonFunction.checkNull(stationaryMasterForm.getMakerDate()));
				  pstm.setString(7,CommonFunction.checkNull(stationaryMasterForm.getAllocto()));
				  pstm.setString(8,CommonFunction.checkNull(stationaryMasterForm.getAllocdate()));
				  																									
				  logger.info("update query:---   mradul "+ sql3);
				  message = pstm.executeUpdate();
				 
				  if(message>0)
				  {
					  status=true;
				  }
			   }
		  	}
	    catch (SQLException ex){
	    		ex.printStackTrace();	
		}finally{			 
				ConnectionDAO.closeConnection(con, pstm, rs);
		}
				logger.info("Status in daoImpl: " + status);
				return status;
	}		  
	}
	
	public boolean deleteBankData(StationaryMasterForm stationaryMasterForm) 
	{
		logger.info("deleteBankData::::: ");
		boolean status=false;
		ArrayList<StringBuilder> qryList = new ArrayList<StringBuilder>();
		StringBuilder deleteStationaryM=new StringBuilder();
		StringBuilder deleteStationaryDtl=new StringBuilder();
		String[] bookNo =stationaryMasterForm.getBookNo();
		try{
			
			deleteStationaryM.append("DELETE FROM cr_stationary_m WHERE STATIONARY_ID=(SELECT STATIONARY_ID FROM cr_stationary_dtl WHERE BOOK_NO='"+CommonFunction.checkNull(bookNo[0]).trim()+"' )");
			logger.info("delete query:delete from cr_stationary_m "+deleteStationaryM);
			qryList.add(deleteStationaryM);
			deleteStationaryM=null;
			
			for(int i=0;i<bookNo.length;i++)
			{
				deleteStationaryDtl.append("DELETE FROM cr_stationary_dtl WHERE BOOK_NO='"+CommonFunction.checkNull(bookNo[i]).trim()+"'");
				logger.info("delete query: delete from cr_stationary_dtl :::"+deleteStationaryDtl);
				qryList.add(deleteStationaryDtl);
				deleteStationaryDtl=null;
			}		
				status=ConnectionDAO.sqlInsUpdDelete(qryList);
				logger.info("Status of deleteBankData is ="+status);
		}catch(Exception e)
    	{
			e.printStackTrace();
		}
		return status;
	}

	public List<StationaryMasterForm> getList(StationaryMasterForm stationaryMasterForm) {
			  logger.info("in --> getList");
			  con = ConnectionDAO.getConnection();
			  String bookNo="";
			  ArrayList<StationaryMasterForm> list = new ArrayList<StationaryMasterForm>();
			  
	  try {		
		  String query="select cd.BOOK_NO,cb.BANK_NAME,CASE BOOK_TYPE WHEN 'R' THEN 'RECEIPT BOOK' WHEN 'C' THEN 'CHEQUE BOOK' END BOOK_TYPE,INSTRUMENT_NO,INSTRUMENT_FROM,INSTRUMENT_TO,CASE cd.REC_STATUS WHEN 'A' THEN 'ACTIVE' WHEN 'X' THEN 'INACTIVE' END AS REC_STATUS,ALLOCATED_TO,sa.HO_REC_STATUS FROM cr_stationary_dtl cd LEFT JOIN cr_stationary_m cm ON cd.STATIONARY_ID=cm.STATIONARY_ID LEFT JOIN COM_BANK_M cb ON cm.bank_name=cb.bank_id LEFT JOIN cr_stationary_allocation_dtl sa ON sa.book_no=cd.book_no";
		         
		        logger.info("query getList: "+query);
				stmt = con.createStatement();
				rs = stmt.executeQuery(query.toString());
				
	  while (rs.next())
	  {     
		  StationaryMasterForm formStationary=new StationaryMasterForm();	
		 
		  if(CommonFunction.checkNull(rs.getString(9)).equalsIgnoreCase("A")){
			  formStationary.setBookNo1("<a href=StationaryAdditionMasterDispatchActionAtCM.do?method=openViewStationary&bookNo="+ CommonFunction.checkNull(rs.getString(1)).toString()+"&alloctoParameter="+CommonFunction.checkNull(stationaryMasterForm.getAllocto()).trim()+">"+ CommonFunction.checkNull(rs.getString(1)) + "</a>");
		  }else{
			  formStationary.setBookNo1("<a href=StationaryAdditionMasterDispatchActionAtCM.do?method=openEditStationary&bookNo="+ CommonFunction.checkNull(rs.getString(1)).toString()+"&alloctoParameter="+CommonFunction.checkNull(stationaryMasterForm.getAllocto()).trim()+">"+ CommonFunction.checkNull(rs.getString(1)) + "</a>"); 
		  }
		  formStationary.setBankList(rs.getString(2));
		  formStationary.setCheckType(rs.getString(3));
		  formStationary.setInstruNo1(rs.getString(4));
		  formStationary.setInstruFrom1(rs.getString(5));
		  formStationary.setInstruTo1(rs.getString(6));
		  formStationary.setStatus(rs.getString(7));
		  formStationary.setAllocto(rs.getString(8));
		  formStationary.setAllocdate(rs.getString(9));
		  list.add(formStationary);
			}
				logger.info("in -->>>>>>>> StationaryMasterAdditionDAOImpl>> getList.."+ list);
	   }	
	     catch(SQLException e) {
	    	 	e.printStackTrace();
		}finally {
				ConnectionDAO.closeConnection(con, stmt, rs);
	    }
		
		return list;
	
	 }
	
	public List<StationaryMasterForm> getdatalist(StationaryMasterForm formStationary) {
		int count=0;
		String bookNo = "";

		ArrayList searchlist = new ArrayList();
		ArrayList<StationaryMasterForm> list = new ArrayList<StationaryMasterForm>();
		
		con = ConnectionDAO.getConnection();
		ArrayList<StationaryMasterForm> detailList = new ArrayList<StationaryMasterForm>();
	 try{
			bookNo = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(formStationary.getBookNo1())).trim());
			
			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			
			bufInsSql.append("select BOOK_NO,cb.BANK_NAME,CASE BOOK_TYPE WHEN 'R' THEN 'RECEIPT BOOK' WHEN 'C' THEN 'CHEQUE BOOK' END BOOK_TYPE,INSTRUMENT_NO,INSTRUMENT_FROM,INSTRUMENT_TO,cd.REC_STATUS,date_format(cd.ADDITION_DATE,'%d-%m-%Y') ADDITION_DATE,ALLOCATED_TO,date_format(allocation_date,'"+dateFormat+"') ALLOCATION_DATE,cd.STATIONARY_ID,BOOK_TYPE,cb.bank_id FROM cr_stationary_dtl cd  JOIN cr_stationary_m cm ON cd.STATIONARY_ID=cm.STATIONARY_ID LEFT JOIN COM_BANK_M cb ON cm.bank_name=cb.bank_id");			
			bufInsSqlTempCount.append("SELECT COUNT(1) FROM cr_stationary_dtl ");      

			if (!(bookNo.equals(""))) {
				bufInsSql.append(" WHERE BOOK_NO = '" + bookNo + "'" );
				bufInsSqlTempCount.append("  WHERE BOOK_NO = '" + bookNo + "'" );
			}
			bufInsSql.append(" ORDER BY BOOK_NO");
			logger.info("search Query...." + bufInsSql);
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
			
			count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
			searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
			logger.info("searchlist..... :" + searchlist);
			stmt = con.createStatement();
			rs = stmt.executeQuery(bufInsSql.toString());
			
			 while (rs.next())
			  {     
				  formStationary.setBookNo1(rs.getString(1));
				  formStationary.setBankName(rs.getString(2));
				  formStationary.setCheckType(rs.getString(3));
				  formStationary.setInstruNo1(rs.getString(4));
				  formStationary.setInstruFrom1(rs.getString(5));
				  formStationary.setInstruTo1(rs.getString(6));
				  formStationary.setStatus(rs.getString(7));
				  formStationary.setAdditionDate(rs.getString(8));
				  formStationary.setAllocto(rs.getString(9));
				  formStationary.setAllocdate(rs.getString(10));
				  formStationary.setStationaryId(rs.getString(11));
				  formStationary.setCheckTypeValue(rs.getString(12));
				  formStationary.setLbxBranchId(rs.getString(13));
				  list.add(formStationary);
		
	} 
	 }	
     catch(SQLException e) {
    	 	e.printStackTrace();
	}finally {
			ConnectionDAO.closeConnection(con, stmt, rs);
    }
	
	return list;

 }

	public List<StationaryMasterForm> getSearch(StationaryMasterForm stationaryMasterForm) {
		  logger.info("in --> ");
		  con = ConnectionDAO.getConnection();
		  ArrayList<StationaryMasterForm> list = new ArrayList<StationaryMasterForm>();
		  
		  String St=stationaryMasterForm.getDesc();
		  logger.info("St"+St);
		  
		  	try {		
		  			String query="select cd.BOOK_NO,CASE BOOK_TYPE WHEN 'R' THEN 'RECEIPT BOOK' WHEN 'C' THEN 'CHEQUE BOOK' END BOOK_TYPE,INSTRUMENT_NO,INSTRUMENT_FROM,INSTRUMENT_TO,CASE cd.REC_STATUS WHEN 'A' THEN 'ACTIVE' WHEN 'X' THEN 'INACTIVE' END AS REC_STATUS,ALLOCATED_TO,SA.HO_REC_STATUS from cr_stationary_dtl cd left join cr_stationary_m on cd.STATIONARY_ID=cr_stationary_m.STATIONARY_ID LEFT JOIN cr_stationary_allocation_dtl sa ON sa.book_no=cd.book_no WHERE book_type='"+St+"'";
		  			logger.info("query: "+query);
		  			stmt = con.createStatement();
					rs = stmt.executeQuery(query.toString());
					
			while (rs.next())
			{    		
				StationaryMasterForm formStationary=new StationaryMasterForm();
				if(CommonFunction.checkNull(rs.getString(8)).equalsIgnoreCase("A")){
					  formStationary.setBookNo1("<a href=StationaryAdditionMasterDispatchActionAtCM.do?method=openViewStationary&bookNo="+ CommonFunction.checkNull(rs.getString(1)).toString()+"&alloctoParameter="+CommonFunction.checkNull(stationaryMasterForm.getAllocto()).trim()+">"+ CommonFunction.checkNull(rs.getString(1)).toString() + "</a>");
				  }else{
					  formStationary.setBookNo1("<a href=StationaryAdditionMasterDispatchActionAtCM.do?method=openEditStationary&bookNo="+ CommonFunction.checkNull(rs.getString(1)).toString()+"&alloctoParameter="+CommonFunction.checkNull(stationaryMasterForm.getAllocto()).trim()+">"+ CommonFunction.checkNull(rs.getString(1)).toString() + "</a>"); 
				  }
				formStationary.setCheckType(rs.getString(2));
				formStationary.setInstruNo1(rs.getString(3));
				formStationary.setInstruFrom1(rs.getString(4));
				formStationary.setInstruTo1(rs.getString(5));
				formStationary.setStatus(rs.getString(6));
				formStationary.setAllocto(rs.getString(7));
				formStationary.setAllocdate(rs.getString(8));
				list.add(formStationary);
				  
				}
						logger.info("in -->>>>>>>> StationaryIssuanceDAOImpl inside query...."+ list);
			 }	
		   catch(SQLException e) {
		  	 	e.printStackTrace();
			}finally {
					ConnectionDAO.closeConnection(con, stmt, rs);
		  }
			
			return list;		
	}

	@Override
	public int checkDublicateReceiptNo(StationaryMasterForm stationaryMasterForm) {
		
		
		int count=0;
		String paramQuery ="SELECT IFNULL(PARAMETER_VALUE,'N') FROM PARAMETER_MST WHERE PARAMETER_KEY='BOOK_NO_NON_MANDATORY'";
		logger.info("paramQuery: "+ paramQuery);
		String paramValue=ConnectionDAO.singleReturn(paramQuery);
		if(CommonFunction.checkNull(paramValue).equalsIgnoreCase("Y"))
		{
			String[] bookNO=stationaryMasterForm.getBookNo();
			String[] instruFrom=stationaryMasterForm.getInstruFrom();
			String[] instruTo=stationaryMasterForm.getInstruTo();
			String bookNoL=stationaryMasterForm.getBookNoL();
			String []stationaryArrayId=stationaryMasterForm.getStationaryArrayId();
			logger.info("bookNoL: "+bookNoL);
			try
			{
				for(int i=0;i<bookNO.length;i++){
					
					
					String receiptNoQuery =" SELECT COUNT(1) FROM cr_stationary_status_dtl   WHERE RECEPT_CHEQUE_NO >= "+StringEscapeUtils.escapeSql(CommonFunction.checkNull(instruFrom[i]))+" AND RECEPT_CHEQUE_NO <="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(instruTo[i]))+" ";
					if(CommonFunction.checkNull(bookNoL).equalsIgnoreCase("L"))
					{
						if(!CommonFunction.checkNull(stationaryArrayId[i]).equalsIgnoreCase(""))
						{
							receiptNoQuery =" SELECT COUNT(1) FROM cr_stationary_status_dtl WHERE Book_No<>'"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(bookNO[i]))+"'  AND RECEPT_CHEQUE_NO >= "+StringEscapeUtils.escapeSql(CommonFunction.checkNull(instruFrom[i]))+" AND RECEPT_CHEQUE_NO <="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(instruTo[i]))+" ";
						}
					}
					logger.info("receiptNoQuery: "+ receiptNoQuery);
					String receiptNoCount=ConnectionDAO.singleReturn(receiptNoQuery);
					if(!(CommonFunction.checkNull(receiptNoCount).equals("0")))
					{
				    	count=1;
						break;
					}
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		logger.info("count"+ count);
		return count;
	  
	   }
    

	}