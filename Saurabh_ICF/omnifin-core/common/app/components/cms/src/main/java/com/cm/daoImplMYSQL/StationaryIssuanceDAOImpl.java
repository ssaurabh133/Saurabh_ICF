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

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import com.cm.dao.StationaryIssuanceDAO;
import com.cm.actionform.StationaryMasterForm;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.cp.vo.CodeDescVo;
 

public class StationaryIssuanceDAOImpl implements StationaryIssuanceDAO
{
			private static final Logger logger=Logger.getLogger(StationaryIssuanceDAOImpl.class.getName());
			StationaryMasterForm stationaryForm = null;
			ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
			    String dateFormatWithTime = resource.getString("lbl.dateWithTimeInDao");
			    String dateFormat = resource.getString("lbl.dateInDao");
			    String dbType=resource.getString("lbl.dbType");
				String dbo=resource.getString("lbl.dbPrefix");
			    Connection con = null;
			    PreparedStatement ptmt = null;
			    ResultSet rs = null;
			    java.sql.Statement stmt = null;
			    CallableStatement csmt = null;
	
	public boolean getsaveIssue(StationaryMasterForm stationaryMasterForm) 
	{

		int flag = 0;
		int message = 0;
		boolean status = false;
		String Status1 = "X";
		String AllBranch="N";
		

		String BookType = stationaryMasterForm.getDesc();
		String BookIssue = stationaryMasterForm.getBookIssue();
		String IssueBranch = stationaryMasterForm.getLbxBranchId();
		String IssueUser = stationaryMasterForm.getLbxUserId();
		String IssueDate = stationaryMasterForm.getAdditionDate();
		String procval = "";
		StringBuilder s1 = new StringBuilder();
		StringBuilder s2 = new StringBuilder();
		ArrayList<Object> out = new ArrayList<Object>();
		ArrayList outMessages = new ArrayList();
		String stationaryType="";
		stationaryType=(CommonFunction.checkNull(stationaryMasterForm.getDesc()).trim());
		ArrayList<Object> in = new ArrayList<Object>();
		

		logger.info("StationaryType.." + stationaryMasterForm.getDesc());
		
		if(stationaryMasterForm.getDesc().equalsIgnoreCase("RECEIPT BOOK")||stationaryMasterForm.getDesc().equalsIgnoreCase("R")){
			stationaryType="R";
			
		}else if (stationaryMasterForm.getDesc().equalsIgnoreCase("Cheque Book")||stationaryMasterForm.getDesc().equalsIgnoreCase("C")){
			stationaryType="C";
		}
		
		
		String BookNoL = stationaryMasterForm.getBookNoL();
		
		if(CommonFunction.checkNull(BookNoL).equalsIgnoreCase("")){
			BookNoL="N";
		}
		if(stationaryMasterForm.getStatus1()!=null &&
				  stationaryMasterForm.getStatus1().equalsIgnoreCase("on")){
				 Status1="A"; 
				 }
		else if(stationaryMasterForm.getStatus1()!=null &&
				  stationaryMasterForm.getStatus1().equalsIgnoreCase("NA")){
			
			Status1="NA"; 
			
		}
		else{
				Status1="X"; 
			}
		if(CommonFunction.checkNull(stationaryMasterForm.getHoAllocationFlag()).equalsIgnoreCase("A"))
		{
			if(stationaryMasterForm.getAllBranch()!=null && stationaryMasterForm.getAllBranch().equalsIgnoreCase("on")){
					 AllBranch="Y"; 
					 }
			else{
					AllBranch="N"; 
				}
		}
		 String date=CommonFunction.changeFormat(CommonFunction.checkNull(stationaryMasterForm.getAdditionDate()).trim());
		 String makerDate=CommonFunction.changeFormat(CommonFunction.checkNull(stationaryMasterForm.getMakerDate()).trim());
	 
		try {

			in.add(((CommonFunction.checkNull(stationaryMasterForm
					.getBookIssue().trim()))));
			in.add(stationaryType);
			in.add(((CommonFunction.checkNull(stationaryMasterForm
					.getLbxBranchId()).trim())));
			in.add(((CommonFunction.checkNull(stationaryMasterForm
					.getLbxUserId()).trim())));
			in.add(date);
			in.add(((CommonFunction.checkNull(Status1).trim())));
			in.add(((CommonFunction.checkNull(AllBranch).trim())));
			in.add(((CommonFunction.checkNull(stationaryMasterForm.getMakerId()
					).trim())));
			in.add(makerDate);
			in.add(((CommonFunction.checkNull(BookNoL).trim())));
			in.add(CommonFunction.checkNull(stationaryMasterForm.getHoAllocationFlag()).trim());
			in.add(((CommonFunction.checkNull(stationaryMasterForm.getHoRemarks()).trim())));
			in.add(((CommonFunction.checkNull(stationaryMasterForm.getStationaryBookType()).trim())));
			out.add(s1.toString());
			out.add(s2.toString());
			logger.info("BEFORE Proc: ");
			outMessages = (ArrayList) ConnectionDAO.callSP("INSERT_CR_STATIONARY_STATUS_DTL", in, out);
			s1.append(CommonFunction.checkNull(outMessages.get(0)));
			s2.append(CommonFunction.checkNull(outMessages.get(1)));
			logger.info("s1: " + s1);
			logger.info("s2: " + s2);

			logger.info("After Proc: ");
			procval = (s2.toString());
			if ((s1.toString()).equalsIgnoreCase("S")) {
				logger.info("After proc call..commit.error message." + s2);

			} else {
				logger.info("After proc call..rollback.error message." + s2);

			}
			if (!(s1.toString()).equalsIgnoreCase("S")) {
				logger.info("After Proc inside If ");
				status = false;
			} else
				status = true;

		} catch (Exception ex) {
			
			ex.printStackTrace();
		} finally {
			s1 = null;
			s2 = null;
			
		}
		logger.info("Status in daoImpl: " + status);
		return status;
	}
	    
    
    public List<StationaryMasterForm> getSearch(StationaryMasterForm stationaryMasterForm) {
		  logger.info("in --> ");
		  con = ConnectionDAO.getConnection();
		  ArrayList<StationaryMasterForm> list = new ArrayList<StationaryMasterForm>();
		  
		  	try {		
		  			String query=("select csa.BOOK_NO,CASE csa.STATIONARY_TYPE WHEN 'R' THEN 'RECEIPT BOOK' WHEN 'C' THEN 'CHEQUE BOOK' END STATIONARY_TYPE,BRANCH_DESC,USER_NAME,date_format(ISSUING_DATE,'"+dateFormat+"') ISSUING_DATE,CASE csa.REC_STATUS WHEN 'A' THEN 'ACTIVE' ELSE 'INACTIVE' END REC_STATUS,csd.INSTRUMENT_FROM,csd.INSTRUMENT_TO from cr_stationary_allocation_dtl csa INNER JOIN cr_stationary_dtl CSD ON CSD.Book_No=csa.Book_No  LEFT join com_branch_m cb on csa.ISSUING_BRANCH=cb.BRANCH_ID LEFT JOIN sec_user_m sm ON csa.ISSUING_USER=sm.USER_ID WHERE csa.HO_REC_STATUS='A' AND (CSA.ALL_BRANCH='Y' OR csa.ISSUING_BRANCH= '"+CommonFunction.checkNull(stationaryMasterForm.getBranchid())+"') AND IFNULL(CSD.RETURN_TO_HO_FLAG,'')<>'R' and (BRANCH_ACKNOWLEDGEMENT_DATE is not null OR BRANCH_ACKNOWLEDGEMENT_DATE<>'0000-00-00 00:00:00' )   group by csa.BOOK_NO");
		  			logger.info("query: "+query);
		  			stmt = con.createStatement();
					rs = stmt.executeQuery(query.toString());
			
			while (rs.next())
			{     
				  StationaryMasterForm formStationary=new StationaryMasterForm();	  
				  
				  formStationary.setBookIssue("<a href=StationaryIssuanceDispatchActionAtCM.do?method=openEdit&bookNo="+ CommonFunction.checkNull(rs.getString(1))+">"+ CommonFunction.checkNull(rs.getString(1)).toString()+"</a>");
				  formStationary.setDesc(rs.getString(2));
				  formStationary.setBranchid(rs.getString(3));
				  formStationary.setUserIssue(rs.getString(4));
				  formStationary.setIssuedate(rs.getString(5));
				  formStationary.setStatus(rs.getString(6));
				  formStationary.setInstruFrom1(rs.getString(7));
				  formStationary.setInstruTo1(rs.getString(8));
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
    
    public List<StationaryMasterForm> getSrchData(StationaryMasterForm stationaryMasterForm) {
		  logger.info("in --> ");
		  con = ConnectionDAO.getConnection();
		  ArrayList<StationaryMasterForm> list = new ArrayList<StationaryMasterForm>();
		  
		  String St=stationaryMasterForm.getDesc();
		  logger.info("St"+St);
	
		  
		  	try {		
		  		    StringBuilder searchQuery =new StringBuilder();
		  		    searchQuery.append(" SELECT csa.BOOK_NO,CASE csa.STATIONARY_TYPE WHEN 'R' THEN 'RECEIPT BOOK' WHEN 'C' THEN 'CHEQUE BOOK' END STATIONARY_TYPE,BRANCH_DESC,USER_NAME,date_format(ISSUING_DATE,'"+dateFormat+"') ISSUING_DATE,CASE csa.REC_STATUS WHEN 'A' THEN 'ACTIVE' ELSE 'INACTIVE' END REC_STATUS ,csd.INSTRUMENT_FROM,csd.INSTRUMENT_TO from cr_stationary_allocation_dtl csa INNER JOIN cr_stationary_dtl CSD ON CSD.Book_No=csa.Book_No LEFT join com_branch_m cb on csa.ISSUING_BRANCH=cb.BRANCH_ID LEFT JOIN sec_user_m sm ON csa.ISSUING_USER=sm.USER_ID WHERE IFNULL(CSD.RETURN_TO_HO_FLAG,'')<>'R' AND (CSA.ALL_BRANCH='Y' OR csa.ISSUING_BRANCH= '"+CommonFunction.checkNull(stationaryMasterForm.getBranchid())+"')");
		  		    if(!CommonFunction.checkNull(St).equalsIgnoreCase(""))
		  		    {
		  		    	searchQuery.append(" AND csa.STATIONARY_TYPE='"+CommonFunction.checkNull(St)+"'");
		  		    }
		  		  if(!CommonFunction.checkNull(stationaryMasterForm.getBookIssue()).equalsIgnoreCase(""))
		  		    {
		  		    	searchQuery.append(" AND csa.BOOK_NO='"+CommonFunction.checkNull(stationaryMasterForm.getBookIssue())+"'");
		  		    }
		  		  searchQuery.append(" group by csa.BOOK_NO ");
		  			logger.info("getSrchData searchQuery: "+searchQuery.toString());
		  			stmt = con.createStatement();
					rs = stmt.executeQuery(searchQuery.toString());
			
			while (rs.next())
			{     
				  StationaryMasterForm formStationary=new StationaryMasterForm();	 
				  formStationary.setBookIssue("<a href=StationaryIssuanceDispatchActionAtCM.do?method=openEdit&bookNo="+ CommonFunction.checkNull(rs.getString(1))+">"+ CommonFunction.checkNull(rs.getString(1)).toString()+"</a>");
				  formStationary.setDesc(rs.getString(2));
				  formStationary.setBranchid(rs.getString(3));
				  formStationary.setUserIssue(rs.getString(4));
				  formStationary.setIssuedate(rs.getString(5));
				  formStationary.setStatus(rs.getString(6));
				  formStationary.setInstruFrom1(rs.getString(7));
				  formStationary.setInstruTo1(rs.getString(8));
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
    
    
    public List<StationaryMasterForm> getEditData(StationaryMasterForm formStationary) {
			int count=0;
			String bookNo = "";
			ArrayList searchlist = new ArrayList();
			ArrayList<StationaryMasterForm> list = new ArrayList<StationaryMasterForm>();		
			con = ConnectionDAO.getConnection();
			ArrayList<StationaryMasterForm> detailList = new ArrayList<StationaryMasterForm>();
			
	    try{
			 bookNo = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(formStationary.getBookIssue())).trim());
			 logger.info("bookNo ********* : "+bookNo);
				
				StringBuffer bufInsSql = new StringBuffer();
				StringBuffer bufInsSqlTempCount = new StringBuffer();
				
				bufInsSql.append("select csa.BOOK_NO,CASE csa.STATIONARY_TYPE WHEN 'R' THEN 'RECEIPT BOOK' WHEN 'C' THEN 'CHEQUE BOOK' END STATIONARY_TYPE,CSA.ISSUING_BRANCH,csa.issuing_user,csa.REC_STATUS,date_format(ISSUING_DATE,'%d-%m-%Y') ISSUING_DATE,BRANCH_DESC,USER_NAME ,csd.INSTRUMENT_FROM,csd.INSTRUMENT_TO,csa.ALL_BRANCH,date_format(csa.HO_ISSUING_DATE,'%d-%m-%Y') HO_ISSUING_DATE,DATE_FORMAT( csd.return_to_ho_date,'%d-%m-%Y') return_to_ho_date,(select ifnull(date_format(max(used_date),'%d-%m-%Y'),(select date_format(BRANCH_ACKNOWLEDGEMENT_DATE,'%d-%m-%Y') from cr_stationary_dtl where book_no='" + bookNo + "')) ");
				bufInsSql.append(" from CR_STATIONARY_STATUS_DTL where book_no='" + bookNo + "'  and STATUS='U') as last_receipt_date,ifnull(DATE_FORMAT( HO_STATIONARY_DATE,'%d-%m-%Y'),''),ifnull(HO_STATIONARY_REMARKS,'') from cr_stationary_allocation_dtl csa INNER JOIN cr_stationary_dtl CSD ON CSD.Book_No=csa.Book_No LEFT join com_branch_m cb on csa.ISSUING_BRANCH=cb.BRANCH_ID LEFT JOIN sec_user_m sm ON csa.ISSUING_USER=sm.USER_ID");			
				bufInsSqlTempCount.append("select count(1) from cr_stationary_allocation_dtl csa INNER JOIN cr_stationary_dtl CSD ON CSD.Book_No=csa.Book_No LEFT join com_branch_m cb on csa.ISSUING_BRANCH=cb.BRANCH_ID LEFT JOIN sec_user_m sm ON csa.ISSUING_USER=sm.USER_ID");      

			if (!(bookNo.equals(""))) {
				bufInsSql.append(" WHERE csa.BOOK_NO = '" + bookNo + "'" );
				bufInsSqlTempCount.append("  WHERE csa.BOOK_NO = '" + bookNo + "'" );
			}
			bufInsSql.append(" ORDER BY BOOK_NO");
			logger.info("search Query...." + bufInsSql);
			logger.info("bufInsSqlTempCount ************* : "+bufInsSqlTempCount.toString());
			
			count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
			searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
			logger.info("searchlist..... :" + searchlist);
			stmt = con.createStatement();
			rs = stmt.executeQuery(bufInsSql.toString());
					
			
			 while (rs.next())
			  {     
				  formStationary.setBookIssue(rs.getString(1));
				  formStationary.setDesc(rs.getString(2));
				  formStationary.setLbxBranchId(rs.getString(3));
				  formStationary.setLbxUserId(rs.getString(4));
				  formStationary.setStatus1(rs.getString(5));
				  formStationary.setAdditionDate(rs.getString(6));
				  formStationary.setBranchid(rs.getString(7));
				  formStationary.setUserIssue(rs.getString(8));
				  formStationary.setInstruFrom1(rs.getString(9));
				  formStationary.setInstruTo1(rs.getString(10));
				  formStationary.setAllBranch(rs.getString(11));
				  formStationary.setIssuedate(rs.getString(12));
				  formStationary.setReturnToHODate(rs.getString(13));
				  formStationary.setIssuedate(rs.getString(6));
				  formStationary.setAdditionDate(rs.getString(15));
				  formStationary.setRemarks(rs.getString(16));
				
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


	@Override
	public List<StationaryMasterForm> getHoAllocationSearch(
			StationaryMasterForm stationaryMasterForm) {
		
		logger.info("getHoAllocationSearch --> ");
		  con = ConnectionDAO.getConnection();
		  ArrayList<StationaryMasterForm> list = new ArrayList<StationaryMasterForm>();
		  
		  String St=stationaryMasterForm.getDesc();
		  logger.info("St"+St);
	
		  
		  	try {		
		  			StringBuilder query= new StringBuilder();
		  			query.append("SELECT csa.BOOK_NO,CASE csa.STATIONARY_TYPE WHEN 'R' THEN 'RECEIPT BOOK' WHEN 'C' THEN 'CHEQUE BOOK' END STATIONARY_TYPE,BRANCH_DESC,'',date_format(HO_ISSUING_DATE,'"+dateFormat+"') HO_ISSUING_DATE,CASE csa.HO_REC_STATUS WHEN 'A' THEN 'ACTIVE' ELSE 'INACTIVE' END HO_REC_STATUS,csd.INSTRUMENT_FROM,csd.INSTRUMENT_TO FROM cr_stationary_allocation_dtl csa INNER JOIN cr_stationary_dtl CSD ON CSD.Book_No=csa.Book_No LEFT join com_branch_m cb on csa.HO_ISSUING_BRANCH=cb.BRANCH_ID WHERE  IFNULL(CSA.HO_REC_STATUS,'')='' ");
		  			
		  			if(!CommonFunction.checkNull(St).equalsIgnoreCase(""))
		  			{
		  				query.append(" AND csa.STATIONARY_TYPE='"+St+"' ");
		  			}
		  			if(!CommonFunction.checkNull(stationaryMasterForm.getBookIssue()).equalsIgnoreCase(""))
		  			{
		  				query.append(" AND csa.BOOK_NO ='"+CommonFunction.checkNull(stationaryMasterForm.getBookIssue())+"'");
		  			}
		  			logger.info("query: "+query.toString());
		  			stmt = con.createStatement();
		  			
					rs = stmt.executeQuery(query.toString());
			
			while (rs.next())
			{     
				  StationaryMasterForm formStationary=new StationaryMasterForm();	 
				  formStationary.setBookIssue("<a href=hoAllocationDispatchActionAtCM.do?method=openEdit&bookNo="+ CommonFunction.checkNull(rs.getString(1))+">"+ CommonFunction.checkNull(rs.getString(1)).toString()+"</a>");
				  formStationary.setDesc(rs.getString(2));
				  formStationary.setBranchid(rs.getString(3));
				  formStationary.setUserIssue(rs.getString(4));
				  formStationary.setIssuedate(rs.getString(5));
				  formStationary.setStatus(rs.getString(6));
				  formStationary.setInstruFrom1(rs.getString(7));
				  formStationary.setInstruTo1(rs.getString(8));
				  list.add(formStationary);
				 				  
				}
						logger.info("in -->>>>>>>> getHoAllocationSearch inside query...."+ list);
			 }	
		   catch(SQLException e) {
		  	 	e.printStackTrace();
			}finally {
					ConnectionDAO.closeConnection(con, stmt, rs);
		  }
			
			return list;		
	}


	@Override
	public List<StationaryMasterForm> getHoAllocationEdit(StationaryMasterForm formStationary) {
		
		int count=0;
		String bookNo = "";
		ArrayList searchlist = new ArrayList();
		ArrayList<StationaryMasterForm> list = new ArrayList<StationaryMasterForm>();		
		con = ConnectionDAO.getConnection();
		ArrayList<StationaryMasterForm> detailList = new ArrayList<StationaryMasterForm>();
		
    try{
		 bookNo = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(formStationary.getBookIssue())).trim());
		 logger.info("getHoAllocationEdit bookNo ********* : "+bookNo);
			
			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			
			bufInsSql.append("select csa.BOOK_NO,CASE csa.STATIONARY_TYPE WHEN 'R' THEN 'RECEIPT BOOK' WHEN 'C' THEN 'CHEQUE BOOK' END STATIONARY_TYPE,CSA.HO_ISSUING_BRANCH,csa.HO_REC_STATUS,date_format(HO_ISSUING_DATE,'%d-%m-%Y') HO_ISSUING_DATE,BRANCH_DESC,HO_REMARKS ,csd.INSTRUMENT_FROM,csd.INSTRUMENT_TO,date_format(csd.addition_date,'%d-%m-%Y') addition_date  FROM cr_stationary_allocation_dtl csa INNER JOIN cr_stationary_dtl CSD ON CSD.Book_No=csa.Book_No left join com_branch_m cb on csa.HO_ISSUING_BRANCH=cb.BRANCH_ID ");			
			bufInsSqlTempCount.append("select COUNT(1) FROM cr_stationary_allocation_dtl csa INNER JOIN cr_stationary_dtl CSD ON CSD.Book_No=csa.Book_No left join com_branch_m cb on csa.HO_ISSUING_BRANCH=cb.BRANCH_ID ");      

		if (!(bookNo.equals(""))) {
			bufInsSql.append(" WHERE csa.BOOK_NO = '" + bookNo + "'" );
			bufInsSqlTempCount.append("  WHERE csa.BOOK_NO = '" + bookNo + "'" );
		}
		bufInsSql.append(" ORDER BY BOOK_NO");
		logger.info("getHoAllocationEdit search Query...." + bufInsSql);
		logger.info("bufInsSqlTempCount ************* : "+bufInsSqlTempCount.toString());
		
		count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
		searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
		logger.info("getHoAllocationEdit searchlist..... :" + searchlist);
		stmt = con.createStatement();
		rs = stmt.executeQuery(bufInsSql.toString());
				
		
		 while (rs.next())
		  {     
			  formStationary.setBookIssue(rs.getString(1));
			  formStationary.setDesc(rs.getString(2));
			  formStationary.setLbxBranchId(rs.getString(3));
			  formStationary.setStatus1(rs.getString(4));
			  formStationary.setAdditionDate(rs.getString(5));
			  formStationary.setBranchid(rs.getString(6));
			  formStationary.setHoRemarks(rs.getString(7));
			  formStationary.setInstruFrom1(rs.getString(8));
			  formStationary.setInstruTo1(rs.getString(9));
			  formStationary.setIssuedate(rs.getString(10));
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


	@Override
	public boolean saveForwardCancelReceipt(StationaryMasterForm stationaryMasterForm) {
		
		
		 logger.info("saveForwardCancelReceipt ");
		  Connection con = null;
	      PreparedStatement pstm = null;
	      int message=0;
		  boolean status=false;
		  try
		  {
			  
		    con = ConnectionDAO.getConnection();
		   //String []receiptNo=stationaryMasterForm.getReceiptNo().split(",");
		 //  for(int i=0;i<receiptNo.length;i++){
			   
			  //logger.info("receiptNo[i] "+receiptNo[i]);

			  String sql3=" update cr_stationary_status_dtl set STATUS=?,REMARKS=?,USED_BY =?,USED_DATE=STR_TO_DATE(?, '"+dateFormatWithTime+"') where STATUS='A' AND RECEPT_CHEQUE_NO ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(stationaryMasterForm.getReceiptNo()))+"'";

				
			  pstm = con.prepareStatement(sql3);	
			
			  pstm.setString(1,CommonFunction.checkNull(stationaryMasterForm.getStatus()));
			  pstm.setString(2,CommonFunction.checkNull(stationaryMasterForm.getRemarks()));
			  pstm.setString(3,CommonFunction.checkNull(stationaryMasterForm.getMakerId()));
			  pstm.setString(4,CommonFunction.checkNull(stationaryMasterForm.getMakerDate()));
//			  pstm.setString(5,CommonFunction.checkNull(stationaryMasterForm.getMakerId()));
					  																									
			  logger.info("update query:---   mradul "+ sql3);
			  message = pstm.executeUpdate();
			  pstm=null;
			    if(message>0)
			    {
			    	status=true;
			    }
		 // }
		}
		  catch (Exception e) {
				e.printStackTrace();	
		}
		  finally
		  {
			  ConnectionDAO.closeConnection(con, pstm, rs);
		  }
		return status;
	}


	@Override
	public String checkReceiptNoUsedCanceled(StationaryMasterForm stationaryMasterForm) {
		
		String status="N";
		
		try
		{
			    String receiptNo=stationaryMasterForm.getReceiptNo();
//			    String bookQuery ="select book_no from cr_stationary_status_dtl where  RECEPT_CHEQUE_NO='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(receiptNo))+"'" ;
//				logger.info("Get book no corresponding Receipt No book Query: "+ bookQuery);
//				String bookNo=ConnectionDAO.singleReturn(bookQuery);
				
				String query ="select IFNULL(STATUS,'') from cr_stationary_status_dtl where RECEPT_CHEQUE_NO='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(stationaryMasterForm.getReceiptNo()))+"'" ;
				logger.info("Check Current Receipt No query: "+ query);
				String st=ConnectionDAO.singleReturn(query);
				
				if((CommonFunction.checkNull(st).equals("")||CommonFunction.checkNull(st).equals("U")||CommonFunction.checkNull(st).equals("X")))
				{
					status="Y";
					
				}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		logger.info("STATUS: "+ status);
		return status;
	 
	}


	@Override
	public String prevCheckReceiptNoUsedCanceled(
			StationaryMasterForm stationaryMasterForm) {
		
	    String status="N";
		
		try
		{       int previousReceiptNo=1;
			    String receiptNo=stationaryMasterForm.getReceiptNo();
			    String bookQuery ="select book_no from cr_stationary_status_dtl where  RECEPT_CHEQUE_NO='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(receiptNo))+"'" ;
				logger.info("Get book no corresponding Receipt No book Query: "+ bookQuery);
				String bookNo=ConnectionDAO.singleReturn(bookQuery);
			    if(!CommonFunction.checkNull(receiptNo).equalsIgnoreCase("") && !CommonFunction.checkNull(receiptNo).equalsIgnoreCase("0") && !CommonFunction.checkNull(receiptNo).equalsIgnoreCase("1"))
			    {
			    	previousReceiptNo=Integer.parseInt(receiptNo)-1;
			    }
			    if (!CommonFunction.checkNull(receiptNo).equalsIgnoreCase("1"))
			    {
			    	String sqlQuery ="select IFNULL(STATUS,'') from cr_stationary_status_dtl where  BOOK_NO='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(bookNo))+"' AND RECEPT_CHEQUE_NO='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(previousReceiptNo))+"'" ;
					logger.info("Check Previous Receipt No query: "+ sqlQuery);
					String st=ConnectionDAO.singleReturn(sqlQuery);
					
					if((CommonFunction.checkNull(st).equals("")||CommonFunction.checkNull(st).equals("U")||CommonFunction.checkNull(st).equals("X")))
					{
						status="Y";
					}
			    }
			    else
			    {
			    	status="Y";
			    }
				

		}catch(Exception e)
		{
			e.printStackTrace();
		}
		logger.info("STATUS: "+ status);
		return status;
	  }
	
	public boolean saveForwardUsedReceipt(StationaryMasterForm stationaryMasterForm) {
		
		
		 logger.info("saveForwardUsedReceipt ");
		  Connection con = null;
	      PreparedStatement pstm = null;
	      int message=0;
		  boolean status=false;
		  try
		  {
		    con = ConnectionDAO.getConnection();
		   //String []receiptNo=stationaryMasterForm.getReceiptNo().split(",");
		 //  for(int i=0;i<receiptNo.length;i++){
			   
			  //logger.info("receiptNo[i] "+receiptNo[i]);
		    
		      String bookNoCheckUsed=(" SELECT BOOK_NO FROM CR_STATIONARY_STATUS_DTL WHERE RECEPT_CHEQUE_NO='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(stationaryMasterForm.getReceiptNo()))+"' AND STATUS='A'");
  			  logger.info("bookNoCheckUsed::"+bookNoCheckUsed);
		      String bookNo=ConnectionDAO.singleReturn(bookNoCheckUsed);

			  String sql3=" update cr_stationary_status_dtl set STATUS=?,REMARKS=?,USED_BY =?,USED_DATE=STR_TO_DATE(?, '"+dateFormatWithTime+"') where STATUS='A' AND RECEPT_CHEQUE_NO ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(stationaryMasterForm.getReceiptNo()))+"' AND BOOK_NO='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(bookNo))+"'";
				
			  pstm = con.prepareStatement(sql3);	
			
			  pstm.setString(1,CommonFunction.checkNull(stationaryMasterForm.getStatus()));
			  pstm.setString(2,CommonFunction.checkNull(stationaryMasterForm.getRemarks()));
			  pstm.setString(3,CommonFunction.checkNull(stationaryMasterForm.getMakerId()));
			  pstm.setString(4,CommonFunction.checkNull(stationaryMasterForm.getMakerDate()));
//			  pstm.setString(5,CommonFunction.checkNull(stationaryMasterForm.getMakerId()));
					  																									
			  logger.info("update query:---   mradul "+ sql3);
			  message = pstm.executeUpdate();
			  pstm=null;
			    if(message>0)
			    {
			    	status=true;
			    }
		 // }
		}
		  catch (Exception e) {
				e.printStackTrace();	
		}
		  finally
		  {
			  ConnectionDAO.closeConnection(con, pstm, rs);
		  }
		return status;
	}
	
	public String checkReceiptSelf(
			StationaryMasterForm stationaryMasterForm) {
		
	    String status="N";
		
		try
		{     			
			    	String sqlQuery ="select count(1) from cr_stationary_status_dtl where IFNULL(STATUS,'')<>'' AND RECEPT_CHEQUE_NO='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(stationaryMasterForm.getReceiptNo()))+"' " +
			    	" AND (ALL_BRANCH ='Y' OR (ISSUING_BRANCH IN (SELECT BRANCH_ID FROM SEC_USER_BRANCH_DTL WHERE USER_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(stationaryMasterForm.getMakerId()))+"'))) " ;
					logger.info("checkReceiptSelf Receipt No query: "+ sqlQuery);
					String st=ConnectionDAO.singleReturn(sqlQuery);
					sqlQuery=null;
					
					if(!(CommonFunction.checkNull(st).equals("0")))
					{
						status="Y";
					}
			    				

		}catch(Exception e)
		{
			e.printStackTrace();
		}
		logger.info("STATUS: "+ status);
		return status;
	  }
	
	public ArrayList getBookTypeGeneric() {
		ArrayList list=new ArrayList();
		try{
			StringBuilder query=new StringBuilder();
		 query.append("SELECT VALUE,DESCRIPTION FROM generic_master WHERE GENERIC_KEY='STATIONARY_BOOK_TYPE' AND REC_STATUS='A'");
		logger.info("getBookTypeGeneric");
		CodeDescVo branchVo=null;
		ArrayList bookType = ConnectionDAO.sqlSelect(query.toString());
		
		query=null;
		logger.info("getBookTypeGeneric"+bookType.size());
		for(int i=0;i<bookType.size();i++){

	
			ArrayList bookType1=(ArrayList)bookType.get(i);
			if(bookType1.size()>0)
			{
			    branchVo = new CodeDescVo();
				branchVo.setId((CommonFunction.checkNull(bookType1.get(0))));
				branchVo.setName((CommonFunction.checkNull(bookType1.get(1))).trim());
				list.add(branchVo);
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}

	public List<StationaryMasterForm> getAcknowledgement(StationaryMasterForm stationaryMasterForm) {
		
			  logger.info("in --> getAcknowledgement");
			  con = ConnectionDAO.getConnection();			  
			  ArrayList<StationaryMasterForm> list = new ArrayList<StationaryMasterForm>();
			  StationaryMasterForm formStationary=null;
			  StringBuilder query=new StringBuilder();
			  
			try {		
			  			query.append("select csa.BOOK_NO,CASE csa.STATIONARY_TYPE WHEN 'R' THEN 'RECEIPT BOOK' WHEN 'C' THEN 'CHEQUE BOOK' END STATIONARY_TYPE,BRANCH_DESC,USER_NAME,date_format(ISSUING_DATE,'"+dateFormat+"') ISSUING_DATE,CASE csa.REC_STATUS WHEN 'A' THEN 'ACTIVE' ELSE 'INACTIVE' END REC_STATUS,csd.INSTRUMENT_FROM,csd.INSTRUMENT_TO from cr_stationary_allocation_dtl csa INNER JOIN cr_stationary_dtl CSD ON CSD.Book_No=csa.Book_No  LEFT join com_branch_m cb on csa.ISSUING_BRANCH=cb.BRANCH_ID LEFT JOIN sec_user_m sm ON csa.ISSUING_USER=sm.USER_ID WHERE csa.HO_REC_STATUS='A' AND IFNULL(CSD.RETURN_TO_HO_FLAG,'')<>'R' AND (CSA.ALL_BRANCH='Y' OR csa.ISSUING_BRANCH= '"+CommonFunction.checkNull(stationaryMasterForm.getBranchid())+"') and (BRANCH_ACKNOWLEDGEMENT_DATE is null OR BRANCH_ACKNOWLEDGEMENT_DATE='0000-00-00 00:00:00' )  ");
			  			logger.info("query: "+query.toString());
			  			stmt = con.createStatement();
						rs = stmt.executeQuery(query.toString());
				
				while (rs.next())
				{    
					   formStationary=new StationaryMasterForm();	  
					  
					  formStationary.setBookIssue("<a href=StationaryIssuanceDispatchActionAtCM.do?method=openEditAcknowledgement&bookNo="+ CommonFunction.checkNull(rs.getString(1))+">"+ CommonFunction.checkNull(rs.getString(1)).toString()+"</a>");
					  formStationary.setDesc(rs.getString(2));
					  formStationary.setBranchid(rs.getString(3));
					  formStationary.setUserIssue(rs.getString(4));
					  formStationary.setIssuedate(rs.getString(5));
					  formStationary.setStatus(rs.getString(6));
					  formStationary.setInstruFrom1(rs.getString(7));
					  formStationary.setInstruTo1(rs.getString(8));
					  list.add(formStationary);					  
					}
				 }	
			   catch(SQLException e) {
			  	 	e.printStackTrace();
			  	 	e=null;
				}finally {
						ConnectionDAO.closeConnection(con, stmt, rs);
						query=null;
						stationaryMasterForm=null;
						formStationary=null;
			  }				
				return list;		
		}	
	
	public boolean getSaveAcknow(StationaryMasterForm stationaryMasterForm) {
		  logger.info("in --> get Save Acknow");
		  con = ConnectionDAO.getConnection();
		  PreparedStatement pstm = null;
		  StringBuilder sql3=new StringBuilder();

		  int message=0;
		  boolean status=false;
		  	  
		try {						
				sql3.append(" update cr_stationary_dtl set BRANCH_ACKNOWLEDGEMENT_DATE=STR_TO_DATE(?, '"+dateFormatWithTime+"')," +
						"BRANCH_ACKNOWLEDGEMENT_REMARKS=? where BOOK_NO ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(stationaryMasterForm.getBookIssue()))+"'");
			
				  pstm = con.prepareStatement(sql3.toString());
				  pstm.setString(1,CommonFunction.checkNull(stationaryMasterForm.getAdditionDate()));
				  pstm.setString(2,CommonFunction.checkNull(stationaryMasterForm.getRemarks()));
				  					  																									
				  logger.info("update query:---   mradul "+ sql3.toString());
				  message = pstm.executeUpdate();
				  pstm=null;
				  
				  sql3=new StringBuilder();
				  sql3.append(" update CR_STATIONARY_ALLOCATION_DTL set ISSUING_DATE=STR_TO_DATE(?, '"+dateFormatWithTime+"')" +
							" where BOOK_NO ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(stationaryMasterForm.getBookIssue()))+"'");
				
					  pstm = con.prepareStatement(sql3.toString());
					  pstm.setString(1,CommonFunction.checkNull(stationaryMasterForm.getAdditionDate()));
					 
					  					  																									
					  logger.info("update query:---   mradul "+ sql3.toString());
					  message = pstm.executeUpdate();
					  pstm=null;
					  
				    if(message>0)
				    {
				    	status=true;
				    }		
			}
		  	catch (Exception e) {
					e.printStackTrace();
					e=null;
			  		}
			  	finally
			  	{
				  ConnectionDAO.closeConnection(con, pstm, rs);
				  stationaryMasterForm=null;
				  sql3=null;
			  	}
		return status;
	}
	
	public List<StationaryMasterForm> getSrchAcknow(StationaryMasterForm stationaryMasterForm) {
		logger.info("in -->getSrchAcknow ");
		  con = ConnectionDAO.getConnection();
		  String St=stationaryMasterForm.getDesc();		 
		  ArrayList<StationaryMasterForm> list = new ArrayList<StationaryMasterForm>();
		  StringBuilder searchQuery =new StringBuilder();
		  StationaryMasterForm formStationary=null;
		  
		  	try {		  		
	  		    searchQuery.append(" SELECT csa.BOOK_NO,CASE csa.STATIONARY_TYPE WHEN 'R' THEN 'RECEIPT BOOK' WHEN 'C' THEN 'CHEQUE BOOK' END STATIONARY_TYPE,BRANCH_DESC,USER_NAME,date_format(ISSUING_DATE,'"+dateFormat+"') ISSUING_DATE,CASE csa.REC_STATUS WHEN 'A' THEN 'ACTIVE' ELSE 'INACTIVE' END REC_STATUS ,csd.INSTRUMENT_FROM,csd.INSTRUMENT_TO from cr_stationary_allocation_dtl csa INNER JOIN cr_stationary_dtl CSD ON CSD.Book_No=csa.Book_No LEFT join com_branch_m cb on csa.ISSUING_BRANCH=cb.BRANCH_ID LEFT JOIN sec_user_m sm ON csa.ISSUING_USER=sm.USER_ID WHERE (CSA.ALL_BRANCH='Y' OR csa.ISSUING_BRANCH= '"+CommonFunction.checkNull(stationaryMasterForm.getBranchid())+"') AND IFNULL(CSD.RETURN_TO_HO_FLAG,'')<>'R' ");
	  		    if(!CommonFunction.checkNull(St).equalsIgnoreCase(""))
	  		    	{
	  		    	searchQuery.append(" AND csa.STATIONARY_TYPE='"+CommonFunction.checkNull(St)+"'");
	  		    	}
	  		  if(!CommonFunction.checkNull(stationaryMasterForm.getBookIssue()).equalsIgnoreCase(""))
	  		  		{
	  		    	searchQuery.append(" AND csa.BOOK_NO='"+CommonFunction.checkNull(stationaryMasterForm.getBookIssue())+"'");
	  		  		}
	  			
	  			logger.info("getSrchData searchQuery: "+searchQuery.toString());
	  			stmt = con.createStatement();
				rs = stmt.executeQuery(searchQuery.toString());
			
			while (rs.next())
			{     
				  formStationary=new StationaryMasterForm();
				  
				  formStationary.setBookIssue("<a href=StationaryIssuanceDispatchActionAtCM.do?method=openEditAcknowledgement&bookNo="+
						  CommonFunction.checkNull(rs.getString(1))+">"+ CommonFunction.checkNull(rs.getString(1)).toString()+"</a>");
				  formStationary.setDesc(rs.getString(2));
				  formStationary.setBranchid(rs.getString(3));
				  formStationary.setUserIssue(rs.getString(4));
				  formStationary.setIssuedate(rs.getString(5));
				  formStationary.setStatus(rs.getString(6));
				  formStationary.setInstruFrom1(rs.getString(7));
				  formStationary.setInstruTo1(rs.getString(8));
				  list.add(formStationary);				  
				}
			 }	
		   catch(SQLException e) {
		  	 	e.printStackTrace();
		  	 	e=null;
			}finally {
					ConnectionDAO.closeConnection(con, stmt, rs);
					stationaryMasterForm=null;
					formStationary=null;
					searchQuery=null;
					St=null;
		  }			
			return list;		
	}

	public List<StationaryMasterForm> getSrchHo(StationaryMasterForm stationaryMasterForm) {
		logger.info("in -->getSrchHo ");
		  con = ConnectionDAO.getConnection();
		  String St=stationaryMasterForm.getDesc();
		  StringBuilder searchQuery =new StringBuilder();
		  ArrayList<StationaryMasterForm> list = new ArrayList<StationaryMasterForm>();
		  StationaryMasterForm formStationary=null;
		  
		  	try {		  		
	  		    searchQuery.append(" SELECT csa.BOOK_NO,CASE csa.STATIONARY_TYPE WHEN 'R' THEN 'RECEIPT BOOK' WHEN 'C' THEN 'CHEQUE BOOK' END STATIONARY_TYPE,BRANCH_DESC,USER_NAME,date_format(ISSUING_DATE,'"+dateFormat+"') ISSUING_DATE,CASE csa.REC_STATUS WHEN 'A' THEN 'ACTIVE' ELSE 'INACTIVE' END REC_STATUS ,csd.INSTRUMENT_FROM,csd.INSTRUMENT_TO from cr_stationary_allocation_dtl csa INNER JOIN cr_stationary_dtl CSD ON CSD.Book_No=csa.Book_No LEFT join com_branch_m cb on csa.ISSUING_BRANCH=cb.BRANCH_ID LEFT JOIN sec_user_m sm ON csa.ISSUING_USER=sm.USER_ID WHERE csa.ISSUING_BRANCH= '"+CommonFunction.checkNull(stationaryMasterForm.getBranchid())+"' AND CSA.REC_STATUS='A' AND IFNULL(CSD.RETURN_TO_HO_FLAG,'')<>'R' ");
	  		    if(!CommonFunction.checkNull(St).equalsIgnoreCase(""))
	  		    {
	  		    	searchQuery.append(" AND csa.STATIONARY_TYPE='"+CommonFunction.checkNull(St)+"'");
	  		    }
	  		  if(!CommonFunction.checkNull(stationaryMasterForm.getBookIssue()).equalsIgnoreCase(""))
	  		    {
	  		    	searchQuery.append(" AND csa.BOOK_NO='"+CommonFunction.checkNull(stationaryMasterForm.getBookIssue())+"'");
	  		    }	  			
			searchQuery.append("group by csa.BOOK_NO ");
	  			logger.info("getSrchHo searchQuery: "+searchQuery.toString());
	  			stmt = con.createStatement();
				rs = stmt.executeQuery(searchQuery.toString());
			
			while (rs.next())
			{     
				  formStationary=new StationaryMasterForm();
				  
				  formStationary.setBookIssue("<a href=StationaryIssuanceDispatchActionAtCM.do?method=openEditHo&bookNo="+
						  CommonFunction.checkNull(rs.getString(1))+">"+ CommonFunction.checkNull(rs.getString(1)).toString()+"</a>");
				  formStationary.setDesc(rs.getString(2));
				  formStationary.setBranchid(rs.getString(3));
				  formStationary.setUserIssue(rs.getString(4));
				  formStationary.setIssuedate(rs.getString(5));
				  formStationary.setStatus(rs.getString(6));
				  formStationary.setInstruFrom1(rs.getString(7));
				  formStationary.setInstruTo1(rs.getString(8));
				  list.add(formStationary);	
				}
					//	logger.info("in -->>>>>>>> getSrchHo inside query...."+ list);
			 }	
		   catch(SQLException e) {
		  	 	e.printStackTrace();
		  	 	e=null;
			}finally {
					ConnectionDAO.closeConnection(con, stmt, rs);
					stationaryMasterForm=null;
					formStationary=null;
					searchQuery=null;
					St=null;
		  }			
			return list;		
	}


	public boolean getSaveAcknowHo(StationaryMasterForm stationaryMasterForm) {
		logger.info("in --> get Save Acknow");
		  con = ConnectionDAO.getConnection();
		  PreparedStatement pstm = null;		  
		  int message=0;
		  StringBuilder sql3=new StringBuilder();
		  boolean status=false;
		  	  
		try {
				sql3.append(" update cr_stationary_dtl set RETURN_TO_HO_FLAG='R',RETURN_TO_HO_DATE=STR_TO_DATE(?, '"+dateFormatWithTime+"')," +
						"RETURN_TO_HO_REMARKS=?,RETURN_BY=? where BOOK_NO ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(stationaryMasterForm.getBookIssue()))+"'");
			
				  pstm = con.prepareStatement(sql3.toString());
				  pstm.setString(1,CommonFunction.checkNull(stationaryMasterForm.getAdditionDate()));
				  pstm.setString(2,CommonFunction.checkNull(stationaryMasterForm.getRemarks()));
				  pstm.setString(3,CommonFunction.checkNull(stationaryMasterForm.getReturnBy()));
				  					  																									
				  logger.info("update query:---   mradul "+ sql3.toString());
				  message = pstm.executeUpdate();
				  pstm=null;
				    if(message>0)
				    {
				    	status=true;
				    }		
			}
		  	catch (Exception e) {
					e.printStackTrace();	
					e=null;
			  			}
			  	finally
			  	{
				  ConnectionDAO.closeConnection(con, pstm, rs);
				  stationaryMasterForm=null;
				  sql3=null;
			  	}
		return status;
	}

	public List<StationaryMasterForm> getSrchAcknowHo(StationaryMasterForm stationaryMasterForm) {
		logger.info("in -->getSrchAcknowHo ");
		  con = ConnectionDAO.getConnection();
		  String St=stationaryMasterForm.getDesc();
		  StringBuilder searchQuery =new StringBuilder();
		  ArrayList<StationaryMasterForm> list = new ArrayList<StationaryMasterForm>();
		  StationaryMasterForm formStationary=null;
		  
		  	try {		
		  		
	  		    searchQuery.append(" SELECT csa.BOOK_NO,CASE csa.STATIONARY_TYPE WHEN 'R' THEN 'RECEIPT BOOK' WHEN 'C' THEN 'CHEQUE BOOK' END STATIONARY_TYPE,BRANCH_DESC,USER_NAME,date_format(ISSUING_DATE,'"+dateFormat+"') ISSUING_DATE,CASE csa.REC_STATUS WHEN 'A' THEN 'ACTIVE' ELSE 'INACTIVE' END REC_STATUS ,csd.INSTRUMENT_FROM,csd.INSTRUMENT_TO from cr_stationary_allocation_dtl csa INNER JOIN cr_stationary_dtl CSD ON CSD.Book_No=csa.Book_No LEFT join com_branch_m cb on csa.ISSUING_BRANCH=cb.BRANCH_ID LEFT JOIN sec_user_m sm ON csa.ISSUING_USER=sm.USER_ID WHERE csa.ISSUING_BRANCH IN (SELECT BRANCH_ID FROM SEC_USER_BRANCH_DTL WHERE REC_STATUS='A' AND USER_ID='"+CommonFunction.checkNull(stationaryMasterForm.getLbxUserId())+"') AND  CSD.RETURN_TO_HO_FLAG='R'");
	  		    if(!CommonFunction.checkNull(St).equalsIgnoreCase(""))
	  		    {
	  		    	searchQuery.append(" AND csa.STATIONARY_TYPE='"+CommonFunction.checkNull(St)+"'");
	  		    }
	  		  if(!CommonFunction.checkNull(stationaryMasterForm.getBookIssue()).equalsIgnoreCase(""))
	  		    {
	  		    	searchQuery.append(" AND csa.BOOK_NO='"+CommonFunction.checkNull(stationaryMasterForm.getBookIssue())+"'");
	  		    }
	  		  	searchQuery.append(" group by csa.BOOK_NO ");
	  			logger.info("getSrchAcknowHogetSrchAcknowHo searchQuery: "+searchQuery.toString());
	  			stmt = con.createStatement();
				rs = stmt.executeQuery(searchQuery.toString());
			
			while (rs.next())
			{     
				formStationary=new StationaryMasterForm();
			
				  formStationary.setBookIssue("<a href=StationaryIssuanceDispatchActionAtCM.do?method=openEditHo&bookNo="+
						  CommonFunction.checkNull(rs.getString(1))+">"+ CommonFunction.checkNull(rs.getString(1)).toString()+"</a>");
				  formStationary.setDesc(rs.getString(2));
				  formStationary.setBranchid(rs.getString(3));
				  formStationary.setUserIssue(rs.getString(4));
				  formStationary.setIssuedate(rs.getString(5));
				  formStationary.setStatus(rs.getString(6));
				  formStationary.setInstruFrom1(rs.getString(7));
				  formStationary.setInstruTo1(rs.getString(8));
				  list.add(formStationary);				  
				}						
			 }	
		   catch(SQLException e) {
		  	 	e.printStackTrace();
		  	 	e=null;
			}finally {
					ConnectionDAO.closeConnection(con, stmt, rs);
					stationaryMasterForm=null;
					formStationary=null;
			   		searchQuery=null;
			   		St=null;
		  }			
			return list;		
	}


	public List<StationaryMasterForm> getSearchHoStationaryAcknow(StationaryMasterForm stationaryMasterForm) {
		logger.info("in -->getSearchHoStationaryAcknow ");
		  con = ConnectionDAO.getConnection();
		  String St=stationaryMasterForm.getDesc();			  		  
		  ArrayList<StationaryMasterForm> list = new ArrayList<StationaryMasterForm>();
		  StringBuilder searchQuery =new StringBuilder();
		  StationaryMasterForm formStationary=null;
		  
		  	try {		  		
	  		    searchQuery.append(" SELECT csa.BOOK_NO,CASE csa.STATIONARY_TYPE WHEN 'R' THEN 'RECEIPT BOOK' WHEN 'C' THEN 'CHEQUE BOOK' END STATIONARY_TYPE,BRANCH_DESC,USER_NAME,date_format(ISSUING_DATE,'"+dateFormat+"') ISSUING_DATE,CASE csa.REC_STATUS WHEN 'A' THEN 'ACTIVE' ELSE 'INACTIVE' END REC_STATUS ,csd.INSTRUMENT_FROM,csd.INSTRUMENT_TO,date_format(HO_STATIONARY_DATE,'%d-%m-%Y') from cr_stationary_allocation_dtl csa INNER JOIN cr_stationary_dtl CSD ON CSD.Book_No=csa.Book_No LEFT join com_branch_m cb on csa.ISSUING_BRANCH=cb.BRANCH_ID LEFT JOIN sec_user_m sm ON csa.ISSUING_USER=sm.USER_ID WHERE csa.ISSUING_BRANCH IN (SELECT BRANCH_ID FROM SEC_USER_BRANCH_DTL WHERE REC_STATUS='A' AND USER_ID='"+CommonFunction.checkNull(stationaryMasterForm.getLbxUserId())+"') AND  CSD.RETURN_TO_HO_FLAG='R' ");
	  		    if(!CommonFunction.checkNull(St).equalsIgnoreCase(""))
	  		    {
	  		    	searchQuery.append(" AND csa.STATIONARY_TYPE='"+CommonFunction.checkNull(St)+"'");
	  		    }
	  		  if(!CommonFunction.checkNull(stationaryMasterForm.getBookIssue()).equalsIgnoreCase(""))
	  		    {
	  		    	searchQuery.append(" AND csa.BOOK_NO='"+CommonFunction.checkNull(stationaryMasterForm.getBookIssue())+"'");
	  		    }
	  			
	  			logger.info("getSearchHoStationaryAcknow searchQuery: "+searchQuery.toString());
	  			stmt = con.createStatement();
				rs = stmt.executeQuery(searchQuery.toString());
			
			while (rs.next())
			{    			
				formStationary=new StationaryMasterForm();
				  formStationary.setBookIssue("<a href=StationaryIssuanceDispatchActionAtCM.do?method=HoStationaryAcknow&bookNo="+
						  CommonFunction.checkNull(rs.getString(1))+">"+ CommonFunction.checkNull(rs.getString(1)).toString()+"</a>");
				  formStationary.setDesc(rs.getString(2));
				  formStationary.setBranchid(rs.getString(3));
				  formStationary.setUserIssue(rs.getString(4));
				  formStationary.setIssuedate(rs.getString(5));
				  formStationary.setStatus(rs.getString(6));
				  formStationary.setInstruFrom1(rs.getString(7));
				  formStationary.setInstruTo1(rs.getString(8));
				  formStationary.setAdditionDate1(rs.getString(8));
				  list.add(formStationary);				  
				}
						logger.info("in -->>>>>>>> getSearchHoStationaryAcknow inside query...."+ list);
			 }	
		   catch(SQLException e) 
		   	{
			   e.printStackTrace();
			   e=null;
		   	}
		   	finally 
		   	{
		   		ConnectionDAO.closeConnection(con, stmt, rs);
		   		stationaryMasterForm=null;
		   		formStationary=null;
		   		searchQuery=null;
		   		St=null;
			}			
			return list;		
	}
	
	public List<StationaryMasterForm> SrchAcknowStationaryHo(StationaryMasterForm stationaryMasterForm) {
		
		 	  logger.info("in -->SrchAcknowStationaryHo ");
			  con = ConnectionDAO.getConnection();
			  String St=stationaryMasterForm.getDesc();
			  ArrayList<StationaryMasterForm> list = new ArrayList<StationaryMasterForm>();
	  		  StringBuilder searchQuery =new StringBuilder();
	  	 	  StationaryMasterForm formStationary=null;

		  	try {		
	  		    searchQuery.append(" SELECT csa.BOOK_NO,CASE csa.STATIONARY_TYPE WHEN 'R' THEN 'RECEIPT BOOK' WHEN 'C' THEN 'CHEQUE BOOK' END STATIONARY_TYPE,BRANCH_DESC,USER_NAME,date_format(ISSUING_DATE,'"+dateFormat+"') ISSUING_DATE,CASE csa.REC_STATUS WHEN 'A' THEN 'ACTIVE' ELSE 'INACTIVE' END REC_STATUS ,csd.INSTRUMENT_FROM,csd.INSTRUMENT_TO from cr_stationary_allocation_dtl csa INNER JOIN cr_stationary_dtl CSD ON CSD.Book_No=csa.Book_No LEFT join com_branch_m cb on csa.ISSUING_BRANCH=cb.BRANCH_ID LEFT JOIN sec_user_m sm ON csa.ISSUING_USER=sm.USER_ID WHERE csa.ISSUING_BRANCH= '"+CommonFunction.checkNull(stationaryMasterForm.getBranchid())+"'");
	  		    if(!CommonFunction.checkNull(St).equalsIgnoreCase(""))
	  		    {
	  		    	searchQuery.append(" AND csa.STATIONARY_TYPE='"+CommonFunction.checkNull(St)+"'");
	  		    }
	  		  if(!CommonFunction.checkNull(stationaryMasterForm.getBookIssue()).equalsIgnoreCase(""))
	  		    {
	  		    	searchQuery.append(" AND csa.BOOK_NO='"+CommonFunction.checkNull(stationaryMasterForm.getBookIssue())+"'");
	  		    }	  			
	  			logger.info("SrchAcknowStationaryHo searchQuery: "+searchQuery.toString());
	  			stmt = con.createStatement();
				rs = stmt.executeQuery(searchQuery.toString());
			
			while (rs.next())
			{     				  
				formStationary=new StationaryMasterForm();
				  formStationary.setBookIssue("<a href=StationaryIssuanceDispatchActionAtCM.do?method=stationaryHoAcknowledge&bookNo="+
						  CommonFunction.checkNull(rs.getString(1))+">"+ CommonFunction.checkNull(rs.getString(1)).toString()+"</a>");
				  formStationary.setDesc(rs.getString(2));
				  formStationary.setBranchid(rs.getString(3));
				  formStationary.setUserIssue(rs.getString(4));
				  formStationary.setIssuedate(rs.getString(5));
				  formStationary.setStatus(rs.getString(6));
				  formStationary.setInstruFrom1(rs.getString(7));
				  formStationary.setInstruTo1(rs.getString(8));
				  list.add(formStationary);				  
				}
						//logger.info("in -->>>>>>>> SrchAcknowStationaryHo inside query...."+ list);
			 }	
		   catch(SQLException e) {
		  	 	e.printStackTrace();
		  	 	e=null;
			}finally {
					St=null;
					searchQuery=null;
					formStationary=null;
					stationaryMasterForm=null;
					ConnectionDAO.closeConnection(con, stmt, rs);
		  }			
			return list;		
	}


	public boolean SaveStationaryAcknowHo(StationaryMasterForm stationaryMasterForm) {
		logger.info("in --> get SaveStationaryAcknowHo ");
		  con = ConnectionDAO.getConnection();
		  PreparedStatement pstm = null;
		  StringBuilder sql3=new StringBuilder();
		  int message=0;
		  boolean status=false;
		  	  
		try {		
				 sql3.append(" update cr_stationary_dtl set HO_STATIONARY_DATE=STR_TO_DATE(?, '"+dateFormatWithTime+"')," +
						"HO_STATIONARY_REMARKS=? where BOOK_NO =? ");
			
				  pstm = con.prepareStatement(sql3.toString());
				  pstm.setString(1,CommonFunction.checkNull(stationaryMasterForm.getAdditionDate()));
				  pstm.setString(2,CommonFunction.checkNull(stationaryMasterForm.getRemarks()));
				  pstm.setString(3,CommonFunction.checkNull(stationaryMasterForm.getBookIssue()));
				  
				  logger.info("update query SaveStationaryAcknowHo:---   mradul "+ sql3.toString());
				  message = pstm.executeUpdate();
				  pstm=null;
				    if(message>0)
				    {
				    	status=true;
				    }		
			}
		  	catch (Exception e) {
					e.printStackTrace();
					e=null;
			  			}
		  	finally
			  	{
				  ConnectionDAO.closeConnection(con, pstm, rs);
				  stationaryMasterForm=null;
				  sql3=null;
			  	}
		return status;
		}
public List<StationaryMasterForm> getstationarybranchgchangeEdit(StationaryMasterForm formStationary) {
		
		int count=0;
		String bookNo = "";
		ArrayList searchlist = new ArrayList();
		ArrayList<StationaryMasterForm> list = new ArrayList<StationaryMasterForm>();		
		con = ConnectionDAO.getConnection();
		ArrayList<StationaryMasterForm> detailList = new ArrayList<StationaryMasterForm>();
		
    try{
		 bookNo = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(formStationary.getBookIssue())).trim());
		 logger.info("getHoAllocationEdit bookNo ********* : "+bookNo);
			
			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			
			bufInsSql.append("select csa.BOOK_NO,CSA.HO_ISSUING_BRANCH  FROM cr_stationary_allocation_dtl csa INNER JOIN cr_stationary_dtl CSD ON CSD.Book_No=csa.Book_No left join com_branch_m cb on csa.HO_ISSUING_BRANCH=cb.BRANCH_ID ");			
			bufInsSqlTempCount.append("select COUNT(1) FROM cr_stationary_allocation_dtl csa INNER JOIN cr_stationary_dtl CSD ON CSD.Book_No=csa.Book_No left join com_branch_m cb on csa.HO_ISSUING_BRANCH=cb.BRANCH_ID ");      

		if (!(bookNo.equals(""))) {
			bufInsSql.append(" WHERE csa.BOOK_NO = '" + bookNo + "'" );
			bufInsSqlTempCount.append("  WHERE csa.BOOK_NO = '" + bookNo + "'" );
		}
		bufInsSql.append(" ORDER BY BOOK_NO");
		logger.info("getHoAllocationEdit search Query...." + bufInsSql);
		logger.info("bufInsSqlTempCount ************* : "+bufInsSqlTempCount.toString());
		
		count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
		searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
		logger.info("getHoAllocationEdit searchlist..... :" + searchlist);
		stmt = con.createStatement();
		rs = stmt.executeQuery(bufInsSql.toString());
				
		
		 while (rs.next())
		  {     
			  formStationary.setBookIssue(rs.getString(1));
			  formStationary.setDesc(rs.getString(2));
			  formStationary.setLbxBranchId(rs.getString(3));


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
	public List<StationaryMasterForm> getStationaryBranchChangeSearch(
			StationaryMasterForm stationaryMasterForm) {
		
		logger.info("getStationaryBranchChangeSearch --> ");
		con = ConnectionDAO.getConnection();
		ArrayList<StationaryMasterForm> list = new ArrayList<StationaryMasterForm>();  
		String st=stationaryMasterForm.getDesc();
		StationaryMasterForm formStationary=null;
		StringBuilder query= new StringBuilder();
		StringBuilder tempQuery= new StringBuilder();
		  	try {		
		  		    query= new StringBuilder();
		  		    tempQuery= new StringBuilder();
		  			query.append("SELECT csa.BOOK_NO,CASE csa.STATIONARY_TYPE WHEN 'R' THEN 'RECEIPT BOOK' WHEN 'C' THEN 'CHEQUE BOOK' END STATIONARY_TYPE,BRANCH_DESC,'',date_format(HO_ISSUING_DATE,'"+dateFormat+"') HO_ISSUING_DATE,CASE csa.HO_REC_STATUS WHEN 'A' THEN 'ACTIVE' ELSE 'INACTIVE' END HO_REC_STATUS,csd.INSTRUMENT_FROM,csd.INSTRUMENT_TO FROM cr_stationary_allocation_dtl csa INNER JOIN cr_stationary_dtl CSD ON CSD.Book_No=csa.Book_No LEFT join com_branch_m cb on csa.ISSUING_BRANCH=cb.BRANCH_ID WHERE   CSA.ho_rec_status not in ('U','X','NA') ");
		  			tempQuery.append("SELECT count(1) FROM cr_stationary_allocation_dtl csa INNER JOIN cr_stationary_dtl CSD ON CSD.Book_No=csa.Book_No LEFT join com_branch_m cb on csa.ISSUING_BRANCH=cb.BRANCH_ID WHERE   CSA.ho_rec_status not in ('U','X','NA') ");
		  			if(!CommonFunction.checkNull(st).equalsIgnoreCase(""))
		  			{
		  				query.append(" AND csa.STATIONARY_TYPE='"+st+"' ");
		  				tempQuery.append(" AND csa.STATIONARY_TYPE='"+st+"' ");
		  			}
		  			 if(!(CommonFunction.checkNull(stationaryMasterForm.getBookIssue()).equalsIgnoreCase("")))
		  			{
		  				query.append(" AND csa.BOOK_NO ='"+CommonFunction.checkNull(stationaryMasterForm.getBookIssue())+"'");
		  				tempQuery.append(" AND csa.BOOK_NO ='"+CommonFunction.checkNull(stationaryMasterForm.getBookIssue())+"'");
		  			}
		  			logger.info("query: "+query.toString());
		  			logger.info("count query : "+tempQuery.toString());
		  			stmt = con.createStatement();
		  			
					rs = stmt.executeQuery(query.toString());
			
			while (rs.next())
			{     
				  formStationary=new StationaryMasterForm();	 
				  formStationary.setBookIssue("<a href=StationaryBranchgChangeDispatchActionAtCM.do?method=openEditBranchChange&bookNo="+ CommonFunction.checkNull(rs.getString(1))+">"+ CommonFunction.checkNull(rs.getString(1)).toString()+"</a>");
				  formStationary.setDesc(rs.getString(2));
				  formStationary.setBranchid(rs.getString(3));
				  formStationary.setUserIssue(rs.getString(4));
				  formStationary.setIssuedate(rs.getString(5));
				  formStationary.setStatus(rs.getString(6));
				  formStationary.setInstruFrom1(rs.getString(7));
				  formStationary.setInstruTo1(rs.getString(8));
				  list.add(formStationary);
				 				  
				}
						
			 }	
		   catch(SQLException e) {
		  	 	e.printStackTrace();
			}finally {
					ConnectionDAO.closeConnection(con, stmt, rs);
					formStationary=null;
					query=null;
					tempQuery=null;
					st=null;
					stationaryMasterForm=null;
		  }
			
			return list;		
	}



	public List<StationaryMasterForm> getstatnarybranchgchangeEdit(
			StationaryMasterForm formStationary) {
		
		int count=0;
		String bookNo = "";
		ArrayList searchlist = new ArrayList();
		ArrayList<StationaryMasterForm> list = new ArrayList<StationaryMasterForm>();		
		con = ConnectionDAO.getConnection();
		ArrayList<StationaryMasterForm> detailList = new ArrayList<StationaryMasterForm>();
		
    try{
		 bookNo = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(formStationary.getBookIssue())).trim());
		 logger.info("openEditBranchChange bookNo ********* : "+bookNo);
			
			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			
			bufInsSql.append("select csa.BOOK_NO,cb.branch_desc  FROM cr_stationary_allocation_dtl csa INNER JOIN cr_stationary_dtl CSD ON CSD.Book_No=csa.Book_No left join com_branch_m cb on csa.ISSUING_BRANCH=cb.BRANCH_ID ");			
			bufInsSqlTempCount.append("select COUNT(1) FROM cr_stationary_allocation_dtl csa INNER JOIN cr_stationary_dtl CSD ON CSD.Book_No=csa.Book_No left join com_branch_m cb on csa.ISSUING_BRANCH=cb.BRANCH_ID ");      

		if (!(bookNo.equals(""))) {
			bufInsSql.append(" WHERE csa.BOOK_NO = '" + bookNo + "'" );
			bufInsSqlTempCount.append("  WHERE csa.BOOK_NO = '" + bookNo + "'" );
		}
		bufInsSql.append(" ORDER BY BOOK_NO");
		logger.info("getHoAllocationEdit search Query...." + bufInsSql);
		logger.info("bufInsSqlTempCount ************* : "+bufInsSqlTempCount.toString());
		
		count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
		searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
		logger.info("getHoAllocationEdit searchlist..... :" + searchlist);
		stmt = con.createStatement();
		rs = stmt.executeQuery(bufInsSql.toString());
				
		
		 while (rs.next())
		  {     
			  formStationary.setBookIssue(rs.getString(1));
			  
			  formStationary.setAllocatedBranch(rs.getString(2));
//			  formStationary.setLbxBranchId(rs.getString(3));
//			  formStationary.setStatus1(rs.getString(4));
//			  formStationary.setAdditionDate(rs.getString(5));
//			  formStationary.setBranchid(rs.getString(6));
//			  formStationary.setHoRemarks(rs.getString(7));
//			  formStationary.setInstruFrom1(rs.getString(8));
//			  formStationary.setInstruTo1(rs.getString(9));
//			  formStationary.setIssuedate(rs.getString(10));
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
	
	
	
	public boolean saveStationarybranchChange(StationaryMasterForm stationaryMasterForm) {
		
		
		 logger.info("saveForwardCancelReceipt ");
		  Connection con = null;
	      PreparedStatement pstm = null;
	      PreparedStatement pstm1 = null;
	      int message=0;
	      int message1=0;
		  boolean status=false;
		  StringBuilder sql3 = new StringBuilder();
		  StringBuilder sql4 = new StringBuilder();
		  try
		  {
			  
		    con = ConnectionDAO.getConnection();
		   
			sql3.append(" update cr_stationary_allocation_dtl set ISSUING_BRANCH=?,REMARKS=? where BOOK_NO ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(stationaryMasterForm.getBookIssue()))+"'" );
			sql4.append("update cr_stationary_status_dtl set issuing_branch= ? where book_no='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(stationaryMasterForm.getBookIssue()))+"'");
              pstm = con.prepareStatement(sql3.toString());
              pstm1 = con.prepareStatement(sql4.toString());
			  pstm.setString(1,CommonFunction.checkNull(stationaryMasterForm.getLbxBranchId()));
			  pstm.setString(2,CommonFunction.checkNull(stationaryMasterForm.getHoRemarks()));
			  pstm1.setString(1,CommonFunction.checkNull(stationaryMasterForm.getLbxBranchId()));
			  logger.info("update query:---   mradul sql3 "+ sql3.toString());
			  logger.info("update query:---   mradul sql4 "+ sql4.toString());
			  message = pstm.executeUpdate();
			  message1 = pstm1.executeUpdate();
			    if(message>0 && message1>0)
			    {
			    	status=true;
			    }
		 // }
		}
		  catch (Exception e) {
				e.printStackTrace();	
		}
		  finally
		  {
			  ConnectionDAO.closeConnection(con, pstm, rs);
			  sql3=null;
			  pstm=null;
			  sql4=null;
			  pstm1=null;
			  stationaryMasterForm=null;
		  }
		return status;
	}


/*		@Override
	public List<StationaryMasterForm> getstatnarybranchgchangeEdit(
			StationaryMasterForm stationaryMasterForm) {
		// TODO Auto-generated method stub
			return null;
	} */
}


