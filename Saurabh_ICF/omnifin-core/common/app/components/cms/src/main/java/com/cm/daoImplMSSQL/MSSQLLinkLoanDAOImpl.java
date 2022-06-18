package com.cm.daoImplMSSQL;



import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.cm.dao.LinkLoanDAO;
import com.cm.vo.LinkLoanVo;
import com.cm.vo.UpdateAssetVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
//import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;

public class MSSQLLinkLoanDAOImpl implements LinkLoanDAO {
	private static final Logger logger=Logger.getLogger(MSSQLLinkLoanDAOImpl.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	String dbType=resource.getString("lbl.dbType");
	String dbo=resource.getString("lbl.dbPrefix");
	
	public ArrayList getLinkLoans(String loanId){
		logger.info("In getLinkLoans method of LinkLoanDAOImpl:::::::::::");
		ArrayList mainlist = new ArrayList();
		ArrayList subList = new ArrayList();
		ArrayList getLoanNo = new ArrayList();
		StringBuilder query = new StringBuilder();
		LinkLoanVo linkLoanVo = null;
		try {
			query.append("SELECT LOAN_LOAN_AMOUNT,LOAN_TENURE FROM CR_LOAN_DTL WHERE LOAN_ID='"+loanId+"'  ");
			mainlist = ConnectionDAO.sqlSelect(query.toString());
			logger.info("checkFileStatus............................"+query.toString());
			
			int size = mainlist.size();
			for (int i = 0; i < size; i++) {
				subList = (ArrayList) mainlist.get(i);
				if (subList.size() > 0) {
					linkLoanVo=new LinkLoanVo();
					Number loanAmount=0;
					if(!(CommonFunction.checkNull(subList.get(0)).equalsIgnoreCase("")))
						loanAmount=myFormatter.parse(CommonFunction.checkNull(subList.get(0)).trim());
					linkLoanVo.setLoanAmount(myFormatter.format(loanAmount));
					linkLoanVo.setLoanTenure(CommonFunction.checkNull(subList.get(1)).trim());
					getLoanNo.add(linkLoanVo);
					linkLoanVo=null;
				 }
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			query=null;	
			mainlist.clear();
			mainlist=null;
			subList.clear();
			subList=null;
			
		}
	
	return getLoanNo;
	}
	
	public ArrayList<LinkLoanVo> getLinkLoanDetails(String loanId,String  loanIdToadd){
		logger.info("in getLinkLoanDetails method of LinkLoanDAOImpl:::::::::::::");
		ArrayList mainlist = new ArrayList();
		ArrayList subList = new ArrayList();
		ArrayList<LinkLoanVo> loanDetail = new ArrayList<LinkLoanVo>();
		StringBuilder query = new StringBuilder();
		StringBuilder queryTempCount = new StringBuilder();
		LinkLoanVo linkLoanVo = null;
		int count=0;
		
		try {
			

			query.append("SELECT DISTINCT (SELECT LOAN_NO FROM CR_LOAN_DTL WHERE LOAN_ID=B.PRIMARY_LOAN) AS NUMBER,LOAN_NO,CUSTOMER_NAME,LOAN_LOAN_AMOUNT,LOAN_TENURE,CASE A.REC_STATUS WHEN 'A' THEN 'APPROVED' WHEN 'C' THEN 'CLOSED'");  
			query.append("  WHEN 'P' THEN 'PENDING' WHEN 'F' THEN 'FORWARDED' WHEN 'X' THEN 'REJECTED' END AS STATUS,");
			query.append(" LOAN_BALANCE_PRINCIPAL, LINK_LOAN,PRIMARY_LOAN");
			query.append(" FROM  CR_LOAN_DTL A ");
			query.append(" JOIN  GCD_CUSTOMER_M GCD ON A.LOAN_CUSTOMER_ID=GCD.CUSTOMER_ID"); 
			query.append(" JOIN ( ");
			query.append(" SELECT LINK_ID,PRIMARY_LOAN,LINK_LOAN FROM CR_LINK_LOAN_DTL WHERE PRIMARY_LOAN='"+loanId+"' OR LINK_LOAN='"+loanId+"' ");
			query.append(" )B ON A.LOAN_ID=B.LINK_LOAN ");

			if(!(CommonFunction.checkNull(loanIdToadd).equalsIgnoreCase(""))){
				
		    query.append("  UNION (");
			query.append(" SELECT (SELECT LOAN_NO FROM CR_LOAN_DTL WHERE LOAN_ID='"+loanId+"') AS NUMBER,LOAN_NO,CUSTOMER_NAME,LOAN_LOAN_AMOUNT,LOAN_TENURE,CASE REC_STATUS WHEN 'A' THEN 'APPROVED' WHEN 'C' THEN 'CLOSED' ");
			query.append(" WHEN 'P' THEN 'PENDING' WHEN 'F' THEN 'FORWARDED' WHEN 'X' THEN 'REJECTED' END AS STATUS,LOAN_BALANCE_PRINCIPAL,"+loanIdToadd+","+loanId+" ");
			query.append(" FROM CR_LOAN_DTL CLD ");
			query.append(" JOIN  GCD_CUSTOMER_M GCD ON CLD.LOAN_CUSTOMER_ID=GCD.CUSTOMER_ID AND LOAN_ID='"+loanIdToadd+"' )"); 
				}
						
			queryTempCount.append(" SELECT COUNT(1) FROM ( ");
			queryTempCount.append(" SELECT DISTINCT (SELECT LOAN_NO FROM CR_LOAN_DTL WHERE LOAN_ID=B.PRIMARY_LOAN) AS NUMBER,LOAN_NO,CUSTOMER_NAME,LOAN_LOAN_AMOUNT,LOAN_TENURE,CASE A.REC_STATUS WHEN 'A' THEN 'APPROVED' WHEN 'C' THEN 'CLOSED'");  
			queryTempCount.append("  WHEN 'P' THEN 'PENDING' WHEN 'F' THEN 'FORWARDED' WHEN 'X' THEN 'REJECTED' END AS STATUS,");
			queryTempCount.append(" LOAN_BALANCE_PRINCIPAL, LINK_LOAN,PRIMARY_LOAN");
			queryTempCount.append(" FROM  CR_LOAN_DTL A ");
			queryTempCount.append(" JOIN  GCD_CUSTOMER_M GCD ON A.LOAN_CUSTOMER_ID=GCD.CUSTOMER_ID"); 
			queryTempCount.append(" JOIN ( ");
			queryTempCount.append(" SELECT LINK_ID,PRIMARY_LOAN,LINK_LOAN FROM CR_LINK_LOAN_DTL WHERE PRIMARY_LOAN='"+loanId+"' OR LINK_LOAN='"+loanId+"' ");
			queryTempCount.append(" )B ON A.LOAN_ID=B.LINK_LOAN ");
			
			if(!(CommonFunction.checkNull(loanIdToadd).equalsIgnoreCase(""))){				
				queryTempCount.append("  UNION (");
				queryTempCount.append(" SELECT (SELECT LOAN_NO FROM CR_LOAN_DTL WHERE LOAN_ID='"+loanId+"') AS NUMBER,LOAN_NO,CUSTOMER_NAME,LOAN_LOAN_AMOUNT,LOAN_TENURE,CASE REC_STATUS WHEN 'A' THEN 'APPROVED' WHEN 'C' THEN 'CLOSED' ");
				queryTempCount.append(" WHEN 'P' THEN 'PENDING' WHEN 'F' THEN 'FORWARDED' WHEN 'X' THEN 'REJECTED' END AS STATUS,LOAN_BALANCE_PRINCIPAL,"+loanIdToadd+","+loanId+" ");
				queryTempCount.append(" FROM CR_LOAN_DTL CLD ");
				queryTempCount.append(" JOIN  GCD_CUSTOMER_M GCD ON CLD.LOAN_CUSTOMER_ID=GCD.CUSTOMER_ID AND LOAN_ID='"+loanIdToadd+"' )) ABC"); 
					}
			else{
				queryTempCount.append(" ) ABC ");
			}
				
			logger.info("query for link ............sss."+query.toString());
			
			logger.info("query for link .............sss"+queryTempCount.toString());
						
			mainlist = ConnectionDAO.sqlSelect(query.toString());

			String dataCount=ConnectionDAO.singleReturn(queryTempCount.toString());
			if(!(CommonFunction.checkNull(dataCount).equalsIgnoreCase("0")))
				count=Integer.parseInt(dataCount);
			
			int size = mainlist.size();
			logger.info("size id ::::::::::::::"+size);
			logger.info("size id ::::::::::::::"+dataCount);
			for (int i = 0; i < size; i++) {
				subList = (ArrayList) mainlist.get(i);
				if (subList.size() > 0) {					
					linkLoanVo=new LinkLoanVo();
					linkLoanVo.setCheckBoxLink("<input type="+"checkbox"+" name=checkId id=checkId"+i+"  checked=checked value="+CommonFunction.checkNull(subList.get(7)).trim()+"  /> " +
					"<input type="+"hidden"+" name=primaryLoanNumberHid id=primaryLoanNumberHid"+i+"  value="+CommonFunction.checkNull(subList.get(8)).trim()+"  />");
					linkLoanVo.setPrimaryLoanNumber(CommonFunction.checkNull(subList.get(0)).trim());
					linkLoanVo.setLoanNumber(CommonFunction.checkNull(subList.get(1)).trim());			
					
					linkLoanVo.setCustName(CommonFunction.checkNull(subList.get(2)).trim());
					Number loanAmt=0;
					if(!(CommonFunction.checkNull(subList.get(3)).equalsIgnoreCase("")))
						loanAmt=myFormatter.parse(CommonFunction.checkNull(subList.get(3)).trim());
					linkLoanVo.setLoanAmt(myFormatter.format(loanAmt));
					linkLoanVo.setLoanTenureTable(CommonFunction.checkNull(subList.get(4)).trim());
					linkLoanVo.setLoanStatus(CommonFunction.checkNull(subList.get(5)).trim());
					Number loanBalPrin=0;
					if(!(CommonFunction.checkNull(subList.get(6)).equalsIgnoreCase("")))
						loanBalPrin=myFormatter.parse(CommonFunction.checkNull(subList.get(6)).trim());
					linkLoanVo.setLoanBalPrin(myFormatter.format(loanBalPrin));
					linkLoanVo.setTotalRecordSize(count);
					loanDetail.add(linkLoanVo);
					linkLoanVo=null;
					
					
				 }
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			query=null;	
			mainlist.clear();
			mainlist=null;
			subList.clear();
			subList=null;
			
		}		
		return loanDetail;
	}
	
public ArrayList<LinkLoanVo> getLoanDetails(LinkLoanVo linkLoanVo){
	
	logger.info("in getLoanDetails method of LinkLoanDAOImpl:::::::::::::");
	ArrayList mainlist = new ArrayList();
	ArrayList subList = new ArrayList();
	ArrayList<LinkLoanVo> loanDetail = new ArrayList<LinkLoanVo>();
	StringBuilder query = new StringBuilder();
	StringBuilder queryTempCount = new StringBuilder();
	int count=0;
	try {
		query.append("SELECT CLD.LOAN_NO,GCD.CUSTOMER_NAME,CLD.LOAN_LOAN_AMOUNT,");
		query.append("CASE CLD.REC_STATUS WHEN 'A' THEN 'APPROVED' WHEN 'P' THEN 'PENDING' WHEN 'C' THEN 'CLOSED' WHEN 'L' THEN 'CANCELED'");
		query.append("WHEN 'X' THEN 'REJECTED' WHEN 'F' THEN 'FORWARDED' END AS REC_STATUS,CLD.LOAN_ID ");
		query.append("	FROM (SELECT DISTINCT LINK_LOAN_ID FROM  CR_POST_LOAN_DTL_TEMP WHERE REC_STATUS='P')CPT");
		query.append(" JOIN CR_LOAN_DTL CLD ON CPT.LINK_LOAN_ID=CLD.LOAN_ID");
		query.append(" JOIN GCD_CUSTOMER_M GCD ON CLD.LOAN_CUSTOMER_ID=GCD.CUSTOMER_ID WHERE 1=1 " );
														
		if(!(CommonFunction.checkNull(linkLoanVo.getLbxLoanNoHID()).equalsIgnoreCase(""))) 
			query.append(" AND LOAN_ID='"+CommonFunction.checkNull(linkLoanVo.getLbxLoanNoHID())+"' ");
		if(!(CommonFunction.checkNull(linkLoanVo.getCustomerName()).equalsIgnoreCase(""))) 
			query.append(" AND CUSTOMER_NAME  LIKE '%"+linkLoanVo.getCustomerName()+"%' ");
		
		queryTempCount.append(" SELECT COUNT(1) ");
		queryTempCount.append("	FROM (SELECT DISTINCT LINK_LOAN_ID FROM  CR_POST_LOAN_DTL_TEMP WHERE REC_STATUS='P')CPT");
		queryTempCount.append(" JOIN CR_LOAN_DTL CLD ON CPT.LINK_LOAN_ID=CLD.LOAN_ID");
		queryTempCount.append(" JOIN GCD_CUSTOMER_M GCD ON CLD.LOAN_CUSTOMER_ID=GCD.CUSTOMER_ID WHERE 1=1 " );
		
		
		if(!(CommonFunction.checkNull(linkLoanVo.getLbxLoanNoHID()).equalsIgnoreCase(""))) 
			queryTempCount.append("  AND LOAN_ID='"+CommonFunction.checkNull(linkLoanVo.getLbxLoanNoHID())+"'");
		if(!(CommonFunction.checkNull(linkLoanVo.getCustomerName()).equalsIgnoreCase(""))) 
			queryTempCount.append(" AND CUSTOMER_NAME  LIKE '%"+linkLoanVo.getCustomerName()+"%' ");
		
		mainlist = ConnectionDAO.sqlSelect(query.toString());
		logger.info("query for link ............."+query.toString());
		
		logger.info("query for link ............."+queryTempCount.toString());
		String dataCount=ConnectionDAO.singleReturn(queryTempCount.toString());
		if(!(CommonFunction.checkNull(dataCount).equalsIgnoreCase("0")))
			count=Integer.parseInt(dataCount);
		
		int size = mainlist.size();
		for (int i = 0; i < size; i++) {
			subList = (ArrayList) mainlist.get(i);
			if (subList.size() > 0) {
				linkLoanVo=new LinkLoanVo();
				//linkLoanVo.setLoanNumber(CommonFunction.checkNull(subList.get(0)).trim());
				linkLoanVo.setLoanNumber("<a href=linkLoanMakerAdd.do?method=editLinkLoanDeatil&lbxLoanNoHID="
					+ CommonFunction.checkNull(subList.get(4)).toString() +">"
					+ CommonFunction.checkNull(subList.get(0)).toString() + "</a>");

				linkLoanVo.setCustName(CommonFunction.checkNull(subList.get(1)).trim());
				Number loanAmount=0;
				if(!CommonFunction.checkNull(subList.get(2)).equals(""))
					loanAmount=myFormatter.parse(CommonFunction.checkNull(subList.get(2)).trim());				
				linkLoanVo.setLoanAmt(myFormatter.format(loanAmount));
				linkLoanVo.setLoanStatus(CommonFunction.checkNull(subList.get(3)).trim());
				linkLoanVo.setTotalRecordSize(count);
				loanDetail.add(linkLoanVo);
				linkLoanVo=null;
			 }
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally
	{
		query=null;	
		mainlist.clear();
		mainlist=null;
		subList.clear();
		subList=null;
		
	}		
	return loanDetail;
}

  public boolean saveLinkLoanDetail(LinkLoanVo linkLoanVo){
	  logger.info("in saveLinkLoanDetail method of LinkLoanDAOImpl:::::::::::::");	  
	  String allLoanid=linkLoanVo.getAllLoanIds();	  
	  PrepStmtObject insertPrepStmtObject = null;
	  StringBuffer bufInsSql =null;
	  ArrayList queryList=new ArrayList();
	  boolean status=false;
	 

	  try{
		  
		  String deleteQuery="SELECT COUNT(1) FROM cr_post_loan_dtl_temp WHERE LINK_LOAN_ID='" +CommonFunction.checkNull(linkLoanVo.getLoanNoSave()).trim()+ "'";
		  logger.info("In saveLinkLoanDetail..deleteQuery. "+deleteQuery);
		  String deleteQueryCount=ConnectionDAO.singleReturn(deleteQuery);
		  logger.info("In saveLinkLoanDetail... deleteQueryCount"+deleteQueryCount);
		 
		  if(!(deleteQueryCount).equalsIgnoreCase("0")){
			  
            insertPrepStmtObject =  new PrepStmtObject();
            bufInsSql = new StringBuffer();
            bufInsSql.append(" DELETE FROM cr_post_loan_dtl_temp WHERE LINK_LOAN_ID='"+CommonFunction.checkNull(linkLoanVo.getLoanNoSave()).trim()+ "'");
            logger.info("### bufInsSql delete PMNT Query #### "+bufInsSql.toString());
            insertPrepStmtObject.setSql(bufInsSql.toString());
            bufInsSql=null;
            logger.info("query for delete is ::::"+insertPrepStmtObject.printQuery());
            queryList.add(insertPrepStmtObject);  
            insertPrepStmtObject=null;
            
        }
		  
		  String[] allLinkLoan=linkLoanVo.getLoanIds();
		  String[] primaryLoans=linkLoanVo.getPrimaryLoanIds();
		  logger.info("value of allLinkLoan ::::"+allLinkLoan.length);
		  logger.info("value of primaryLoans ::::"+primaryLoans.length);
		  
		  
		  	for(int i=0;i< allLinkLoan.length;i++)
		  	{	
	            logger.info("value of primary ::::"+primaryLoans[i]);
	            logger.info("value of allLinkLoan ::::"+allLinkLoan[i]);
		  		
	   			  	insertPrepStmtObject =  new PrepStmtObject();
	   			  	bufInsSql = new StringBuffer();		  
	   			    bufInsSql.append("INSERT INTO cr_post_loan_dtl_temp (LINK_LOAN_ID,PRIMARY_LOAN,LINK_LOAN");
	   			    bufInsSql.append(",REC_STATUS,REMARKS,MAKER_ID,MAKER_DATE)");
	   			  
	   			    bufInsSql.append(" VALUES ( ");
	   				bufInsSql.append(" ?,");//LINK_LOAN_ID
	   				bufInsSql.append(" ?,");//PRIMARY_LOAN
	   				bufInsSql.append(" ?,");//LINK_LOAN
	   				bufInsSql.append(" ?,");//REC_STATUS
	   				bufInsSql.append(" ?,");//REMARKS
	   				bufInsSql.append(" ?,");//MAKER_ID
	   				bufInsSql.append(dbo);//MAKER_ID
	   				// bufInsSql.append(" DATE_ADD(dbo.STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND))");//MAKER_DATE
	   				 bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"')+ ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");//MAKER_DATE
				 
	   				
	   				
	   				
	   				if (CommonFunction.checkNull(linkLoanVo.getLoanNoSave()).equalsIgnoreCase(""))
	   					insertPrepStmtObject.addNull();
	   				else 
	   					insertPrepStmtObject.addString(linkLoanVo.getLoanNoSave().trim());
	   				
	   				if (CommonFunction.checkNull(primaryLoans[i]).equalsIgnoreCase(""))
	   					insertPrepStmtObject.addNull();
	   				else 
	   					insertPrepStmtObject.addString(primaryLoans[i].trim());
	   				
	   				if (CommonFunction.checkNull(allLinkLoan[i]).equalsIgnoreCase(""))
	   					insertPrepStmtObject.addNull();
	   				else 
	   					insertPrepStmtObject.addString(allLinkLoan[i].trim());
	   				
	   				   		
	   					insertPrepStmtObject.addString("P");
	   				
   					if (CommonFunction.checkNull(linkLoanVo.getMakerRemarks()).equalsIgnoreCase(""))
	   					insertPrepStmtObject.addNull();
	   				else 
	   					insertPrepStmtObject.addString(linkLoanVo.getMakerRemarks().trim());
	   				
	   					
	   				if (CommonFunction.checkNull(linkLoanVo.getUserId()).equalsIgnoreCase(""))
	   					insertPrepStmtObject.addNull();
	   				else 
	   					insertPrepStmtObject.addString(linkLoanVo.getUserId().trim());
	   				
	   				if (CommonFunction.checkNull(linkLoanVo.getMakerdDate()).equalsIgnoreCase(""))
	   					insertPrepStmtObject.addNull();
	   				else 
	   					insertPrepStmtObject.addString(linkLoanVo.getMakerdDate().trim());
	   				
	   				
	   		     insertPrepStmtObject.setSql(bufInsSql.toString());
	   		     logger.info("query for insert is ::::"+insertPrepStmtObject.printQuery());
	   		     queryList.add(insertPrepStmtObject);
	   		     insertPrepStmtObject=null;
	   		     bufInsSql=null;
	
		  	}    

			

	  
	     status=ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);
	     
	     logger.info("query for status is ::::"+status);  
	  }
	 catch(Exception e){
		 e.printStackTrace();
	 }
	finally{
		queryList.clear();
		queryList=null;
	}
	  return status;
  }
  public ArrayList<LinkLoanVo> getAfterSaveData(LinkLoanVo linkLoanvo,String searchFlag){
	  logger.info("in getAfterSaveData method of LinkLoan DAO Iml:::::::::::::::: ");
	  	ArrayList mainlist = new ArrayList();
		ArrayList subList = new ArrayList();
		ArrayList<LinkLoanVo> loanDetail = new ArrayList<LinkLoanVo>();
		StringBuilder query = new StringBuilder();
		StringBuilder queryTempCount = new StringBuilder();
		LinkLoanVo linkLoanVo = null;
		String checkBoxFlag="";
		int count=0;
		


		try {
			
				StringBuffer countQuery=new StringBuffer();
				
				countQuery.append("SELECT COUNT(1) FROM cr_post_loan_dtl_temp WHERE LINK_LOAN='"+CommonFunction.checkNull(linkLoanvo.getLbxLoanNoHIDAdd())+"' OR PRIMARY_LOAN='"+CommonFunction.checkNull(linkLoanvo.getLbxLoanNoHIDAdd())+"'");
				logger.info("count query is:::::::::::::::::"+countQuery.toString());
				String countResult=ConnectionDAO.singleReturn(countQuery.toString());
				
				countQuery=null;			
				
			 checkBoxFlag=linkLoanvo.getCheckBoxDisable();
			 logger.info("value of disbale :::::::::"+checkBoxFlag);
			
				query.append("SELECT DISTINCT (SELECT LOAN_NO FROM CR_LOAN_DTL WHERE LOAN_ID=B.PRIMARY_LOAN) AS NUMBER,LOAN_NO,CUSTOMER_NAME,LOAN_LOAN_AMOUNT,LOAN_TENURE,CASE A.REC_STATUS WHEN 'A' THEN 'APPROVED' WHEN 'C' THEN 'CLOSED'");  
				query.append("  WHEN 'P' THEN 'PENDING' WHEN 'F' THEN 'FORWARDED' WHEN 'X' THEN 'REJECTED' END AS STATUS,");
				query.append(" LOAN_BALANCE_PRINCIPAL, LINK_LOAN,PRIMARY_LOAN,REMARKS");
				query.append(" FROM  CR_LOAN_DTL A ");
				query.append(" JOIN  GCD_CUSTOMER_M GCD ON A.LOAN_CUSTOMER_ID=GCD.CUSTOMER_ID"); 
				query.append(" JOIN ( ");
				query.append("  SELECT PRIMARY_LOAN,LINK_LOAN,REMARKS FROM cr_post_loan_dtl_temp WHERE LINK_LOAN_ID='"+CommonFunction.checkNull(linkLoanvo.getLbxLoanNoHID())+"'");
				query.append(" )B ON A.LOAN_ID=B.LINK_LOAN ");
	
			
			if(!(CommonFunction.checkNull(linkLoanvo.getLbxLoanNoHIDAdd()).equalsIgnoreCase("")) ){
				if(CommonFunction.checkNull(searchFlag).equalsIgnoreCase("Y")){
					logger.info("loan id to add:::::::::::::::::"+linkLoanvo.getLbxLoanNoHIDAdd());
					
					if(CommonFunction.checkNull(countResult).equalsIgnoreCase("0")){
					  query.append("  UNION (");
						query.append(" SELECT (SELECT LOAN_NO FROM CR_LOAN_DTL WHERE LOAN_ID='"+linkLoanvo.getLoanNoSave()+"') AS NUMBER,LOAN_NO,CUSTOMER_NAME,LOAN_LOAN_AMOUNT,LOAN_TENURE,CASE REC_STATUS WHEN 'A' THEN 'APPROVED' WHEN 'C' THEN 'CLOSED' ");
						query.append(" WHEN 'P' THEN 'PENDING' WHEN 'F' THEN 'FORWARDED' WHEN 'X' THEN 'REJECTED' END AS STATUS,LOAN_BALANCE_PRINCIPAL,"+CommonFunction.checkNull(linkLoanvo.getLbxLoanNoHIDAdd())+","+linkLoanvo.getLoanNoSave()+"");
						query.append(" FROM CR_LOAN_DTL CLD ");
						query.append(" JOIN  GCD_CUSTOMER_M GCD ON CLD.LOAN_CUSTOMER_ID=GCD.CUSTOMER_ID AND LOAN_ID='"+CommonFunction.checkNull(linkLoanvo.getLbxLoanNoHIDAdd())+"' )");
						linkLoanvo.setMessage("S");
						
					}
					else{
						linkLoanvo.setMessage("E");						
					}
				}
			}

			queryTempCount.append(" SELECT COUNT(1)  FROM ( ");
			queryTempCount.append(" SELECT DISTINCT (SELECT LOAN_NO FROM CR_LOAN_DTL WHERE LOAN_ID=B.PRIMARY_LOAN) AS NUMBER,LOAN_NO,CUSTOMER_NAME,LOAN_LOAN_AMOUNT,LOAN_TENURE,CASE A.REC_STATUS WHEN 'A' THEN 'APPROVED' WHEN 'C' THEN 'CLOSED'");  
			queryTempCount.append("  WHEN 'P' THEN 'PENDING' WHEN 'F' THEN 'FORWARDED' WHEN 'X' THEN 'REJECTED' END AS STATUS,");
			queryTempCount.append(" LOAN_BALANCE_PRINCIPAL, LINK_LOAN,PRIMARY_LOAN");
			queryTempCount.append(" FROM  CR_LOAN_DTL A ");
			queryTempCount.append(" JOIN  GCD_CUSTOMER_M GCD ON A.LOAN_CUSTOMER_ID=GCD.CUSTOMER_ID"); 
			queryTempCount.append(" JOIN ( ");
			queryTempCount.append("  SELECT PRIMARY_LOAN,LINK_LOAN FROM cr_post_loan_dtl_temp WHERE LINK_LOAN_ID='"+CommonFunction.checkNull(linkLoanvo.getLbxLoanNoHID())+"'");
			queryTempCount.append(" )B ON A.LOAN_ID=B.LINK_LOAN ");
		
			
			
			if(!(CommonFunction.checkNull(linkLoanvo.getLbxLoanNoHIDAdd()).equalsIgnoreCase(""))){
				if(CommonFunction.checkNull(searchFlag).equalsIgnoreCase("Y")){
				queryTempCount.append("	UNION (");
				queryTempCount.append(" SELECT (SELECT LOAN_NO FROM CR_LOAN_DTL WHERE LOAN_ID='"+linkLoanvo.getLoanNoSave()+"') AS NUMBER,LOAN_NO,CUSTOMER_NAME,LOAN_LOAN_AMOUNT,LOAN_TENURE,CASE REC_STATUS WHEN 'A' THEN 'APPROVED' WHEN 'C' THEN 'CLOSED' ");
				queryTempCount.append(" WHEN 'P' THEN 'PENDING' WHEN 'F' THEN 'FORWARDED' WHEN 'X' THEN 'REJECTED' END AS STATUS,LOAN_BALANCE_PRINCIPAL,"+linkLoanvo.getLbxLoanNoHIDAdd()+" ,"+linkLoanvo.getLoanNoSave()+" ");
				queryTempCount.append(" FROM CR_LOAN_DTL CLD ");
				queryTempCount.append("	JOIN GCD_CUSTOMER_M GCM ON  CLD.LOAN_CUSTOMER_ID=GCM.CUSTOMER_ID AND LOAN_ID='"+linkLoanvo.getLbxLoanNoHIDAdd()+"')");
					}
				}
			
			queryTempCount.append(" ) ABC ");
					
			
			logger.info("query for link ............sss."+query.toString());
			
			logger.info("query for link .............sss"+queryTempCount.toString());
						
			mainlist = ConnectionDAO.sqlSelect(query.toString());

			String dataCount=ConnectionDAO.singleReturn(queryTempCount.toString());
			if(!(CommonFunction.checkNull(dataCount).equalsIgnoreCase("0")))
				count=Integer.parseInt(dataCount);
			
			int size = mainlist.size();
			logger.info("size id ::::::::::::::"+size);
			logger.info("size id ::::::::::::::"+dataCount);
			for (int i = 0; i < size; i++) {
				subList = (ArrayList) mainlist.get(i);
				if (subList.size() > 0) {					
					linkLoanVo=new LinkLoanVo();
					if(CommonFunction.checkNull(checkBoxFlag).equalsIgnoreCase("Y")){
					linkLoanVo.setCheckBoxLink("<input type="+"checkbox"+" name=checkId id=checkId"+i+"  checked=checked disabled="+"true"+" value="+CommonFunction.checkNull(subList.get(7)).trim()+"  /> " +
					"<input type="+"hidden"+" name=primaryLoanNumberHid id=primaryLoanNumberHid"+i+"  value="+CommonFunction.checkNull(subList.get(8)).trim()+"  />");
					}
					else{
						linkLoanVo.setCheckBoxLink("<input type="+"checkbox"+" name=checkId id=checkId"+i+"  checked=checked value="+CommonFunction.checkNull(subList.get(7)).trim()+"  /> " +
						"<input type="+"hidden"+" name=primaryLoanNumberHid id=primaryLoanNumberHid"+i+"  value="+CommonFunction.checkNull(subList.get(8)).trim()+"  />");
					}
					linkLoanVo.setPrimaryLoanNumber(CommonFunction.checkNull(subList.get(0)).trim());
					linkLoanVo.setLoanNumber(CommonFunction.checkNull(subList.get(1)).trim());			
					
					linkLoanVo.setCustName(CommonFunction.checkNull(subList.get(2)).trim());
					Number loanAmt=0;
					if(!(CommonFunction.checkNull(subList.get(3)).equalsIgnoreCase("")))
						loanAmt=myFormatter.parse(CommonFunction.checkNull(subList.get(3)).trim());
					linkLoanVo.setLoanAmt(myFormatter.format(loanAmt));
					linkLoanVo.setLoanTenureTable(CommonFunction.checkNull(subList.get(4)).trim());
					linkLoanVo.setLoanStatus(CommonFunction.checkNull(subList.get(5)).trim());
					Number loanBalPrin=0;
					if(!(CommonFunction.checkNull(subList.get(6)).equalsIgnoreCase("")))
						loanBalPrin=myFormatter.parse(CommonFunction.checkNull(subList.get(6)).trim());
					linkLoanVo.setLoanBalPrin(myFormatter.format(loanBalPrin));
					linkLoanVo.setMakerRemarks(CommonFunction.checkNull(subList.get(9)).trim());
					linkLoanVo.setTotalRecordSize(count);
					loanDetail.add(linkLoanVo);
					linkLoanVo=null;
					
					
				 }
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			query=null;	
			mainlist.clear();
			mainlist=null;
			subList.clear();
			subList=null;
			
		}		

		return loanDetail;
  }
  public boolean forwardLinkLoanData(String loanId){
	  logger.info("in forwardLinkLoanData method of LinkLoanDAOImpl:::::::::::::::: "+loanId);
	  PrepStmtObject insertPrepStmtObject =  new PrepStmtObject();
	  StringBuffer bufInsSql = new StringBuffer();
	  ArrayList queryList=new ArrayList();
	  boolean status=false;

	  try{
		 bufInsSql.append("UPDATE cr_post_loan_dtl_temp SET REC_STATUS='F' WHERE LINK_LOAN_ID='"+loanId+"' ");
	     insertPrepStmtObject.setSql(bufInsSql.toString());
	     logger.info("query for UPDATE is ::::"+insertPrepStmtObject.printQuery());
	     queryList.add(insertPrepStmtObject);
	     insertPrepStmtObject=null;
	     bufInsSql=null;
	  
	     status=ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);	     
	     logger.info("query for status is ::::"+status);  
	  }
	 catch(Exception e){
		 e.printStackTrace();
	 }
	finally{
		queryList.clear();
		queryList=null;
	}
	  return status;
  }
  public ArrayList<LinkLoanVo> getAuthorLoanDetails(LinkLoanVo linkLoanVo){
	  logger.info("in getAuthorLoanDetails method of LinkLoanDAOImpl:::::::::::::::: ");
	    ArrayList mainlist = new ArrayList();
		ArrayList subList = new ArrayList();
		ArrayList<LinkLoanVo> loanDetail = new ArrayList<LinkLoanVo>();
		StringBuilder query = new StringBuilder();
		StringBuilder queryTempCount = new StringBuilder();
		int count=0;
		try {
			query.append("SELECT CLD.LOAN_NO,GCD.CUSTOMER_NAME,CLD.LOAN_LOAN_AMOUNT,");
			query.append("CASE CLD.REC_STATUS WHEN 'A' THEN 'APPROVED' WHEN 'P' THEN 'PENDING' WHEN 'C' THEN 'CLOSED' WHEN 'L' THEN 'CANCELED'");
			query.append("WHEN 'X' THEN 'REJECTED' WHEN 'F' THEN 'FORWARDED' END AS REC_STATUS,CLD.LOAN_ID,CPT.MAKER_ID ");
			query.append("	FROM (SELECT DISTINCT LINK_LOAN_ID,MAKER_ID FROM  CR_POST_LOAN_DTL_TEMP WHERE REC_STATUS='F' AND MAKER_ID <> '"+CommonFunction.checkNull(linkLoanVo.getUserId())+"')CPT");
			query.append(" JOIN CR_LOAN_DTL CLD ON CPT.LINK_LOAN_ID=CLD.LOAN_ID");
			query.append(" JOIN GCD_CUSTOMER_M GCD ON CLD.LOAN_CUSTOMER_ID=GCD.CUSTOMER_ID WHERE 1=1 " );
													
			if(!(CommonFunction.checkNull(linkLoanVo.getLbxLoanNoHID()).equalsIgnoreCase(""))) 
				query.append(" AND LOAN_ID='"+CommonFunction.checkNull(linkLoanVo.getLbxLoanNoHID())+"' ");
			if(!(CommonFunction.checkNull(linkLoanVo.getCustomerName()).equalsIgnoreCase(""))) 
				query.append(" AND CUSTOMER_NAME  LIKE '%"+linkLoanVo.getCustomerName()+"%' ");
		
			queryTempCount.append(" SELECT COUNT(1) ");
			queryTempCount.append("	FROM (SELECT DISTINCT LINK_LOAN_ID FROM  CR_POST_LOAN_DTL_TEMP WHERE REC_STATUS='F' AND  MAKER_ID <> '"+CommonFunction.checkNull(linkLoanVo.getUserId())+"')CPT");
			queryTempCount.append(" JOIN CR_LOAN_DTL CLD ON CPT.LINK_LOAN_ID=CLD.LOAN_ID");
			queryTempCount.append(" JOIN GCD_CUSTOMER_M GCD ON CLD.LOAN_CUSTOMER_ID=GCD.CUSTOMER_ID WHERE 1=1 " );
			
		
			if(!(CommonFunction.checkNull(linkLoanVo.getLbxLoanNoHID()).equalsIgnoreCase(""))) 
				queryTempCount.append("  AND LOAN_ID='"+CommonFunction.checkNull(linkLoanVo.getLbxLoanNoHID())+"'");
			if(!(CommonFunction.checkNull(linkLoanVo.getCustomerName()).equalsIgnoreCase(""))) 
				queryTempCount.append(" AND CUSTOMER_NAME  LIKE '%"+linkLoanVo.getCustomerName()+"%' ");
		
			mainlist = ConnectionDAO.sqlSelect(query.toString());
			logger.info("query for link ............."+query.toString());
			
			logger.info("query for link ............."+queryTempCount.toString());
			String dataCount=ConnectionDAO.singleReturn(queryTempCount.toString());
			if(!(CommonFunction.checkNull(dataCount).equalsIgnoreCase("0")))
				count=Integer.parseInt(dataCount);
			
			int size = mainlist.size();
			for (int i = 0; i < size; i++) {
				subList = (ArrayList) mainlist.get(i);
				if (subList.size() > 0) {
					linkLoanVo=new LinkLoanVo();
					linkLoanVo.setLoanNumber("<a href=linkLoanAuthorApprove.do?method=linkLoanAuthorShow&lbxLoanNoHID="
						+ CommonFunction.checkNull(subList.get(4)).toString() +"&makerName="+CommonFunction.checkNull(subList.get(5)).toString()+">"
						+ CommonFunction.checkNull(subList.get(0)).toString() + "</a>");

					linkLoanVo.setCustName(CommonFunction.checkNull(subList.get(1)).trim());
					Number loanAmount=0;
					if(!CommonFunction.checkNull(subList.get(2)).equals(""))
						loanAmount=myFormatter.parse(CommonFunction.checkNull(subList.get(2)).trim());				
					linkLoanVo.setLoanAmt(myFormatter.format(loanAmount));
					linkLoanVo.setLoanStatus(CommonFunction.checkNull(subList.get(3)).trim());
					linkLoanVo.setMakerName(CommonFunction.checkNull(subList.get(5)).trim());
					linkLoanVo.setTotalRecordSize(count);
					loanDetail.add(linkLoanVo);
					linkLoanVo=null;
				 }
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			query=null;	
			mainlist.clear();
			mainlist=null;
			subList.clear();
			subList=null;
			
		}		
		return loanDetail;

  }
  
  public ArrayList<LinkLoanVo> getEditData(String loanId){
	  logger.info("in getEditData method of LinkLoan DAO Iml:::::::::::::::: "+loanId);
	  ArrayList mainlist = new ArrayList();
		ArrayList subList = new ArrayList();
		ArrayList<LinkLoanVo> loanDetail = new ArrayList<LinkLoanVo>();
		StringBuilder query = new StringBuilder();
		LinkLoanVo linkLoanVo = null;
		int count=0;

		try {
			
			query.append("SELECT LOAN_NO,CUSTOMER_NAME,LOAN_LOAN_AMOUNT,LOAN_TENURE,LOAN_ID ");
			query.append("FROM CR_LOAN_DTL CLD ");
			query.append("JOIN GCD_CUSTOMER_M GCD ON CLD.LOAN_CUSTOMER_ID=GCD.CUSTOMER_ID AND LOAN_ID='"+loanId+"' ");			

		
			mainlist = ConnectionDAO.sqlSelect(query.toString());
			logger.info("query for link ............."+query.toString());			
			
			int size = mainlist.size();
			for (int i = 0; i < size; i++) {
				subList = (ArrayList) mainlist.get(i);
				if (subList.size() > 0) {
					linkLoanVo=new LinkLoanVo();

					
					linkLoanVo.setLoanNo(CommonFunction.checkNull(subList.get(0)).trim());
					linkLoanVo.setCustomerName(CommonFunction.checkNull(subList.get(1)).trim());
					Number loanAmt=0;
					if(!(CommonFunction.checkNull(subList.get(2)).equalsIgnoreCase("")))
						loanAmt=myFormatter.parse(CommonFunction.checkNull(subList.get(2)).trim());
					linkLoanVo.setLoanAmount(myFormatter.format(loanAmt));
					linkLoanVo.setLoanTenure(CommonFunction.checkNull(subList.get(3)).trim());
					linkLoanVo.setLbxLoanNoHID(CommonFunction.checkNull(subList.get(4)).trim());
					loanDetail.add(linkLoanVo);
					linkLoanVo=null;
				 }
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			query=null;	
			mainlist.clear();
			mainlist=null;
			subList.clear();
			subList=null;
			
		}		
		return loanDetail;
  }
  public String saveLinkLoanAuthor(LinkLoanVo linkLoanVo){
	  logger.info("in saveLinkLoanAuthor method of LinkLoanDAOIml:::::::::::::::: ");	 
		 
		boolean status=false;
		String procval=null;
	    ArrayList<Object> in =new ArrayList<Object>();
		ArrayList<Object> out =new ArrayList<Object>();
		ArrayList outMessages = new ArrayList();
		StringBuilder s1 = new StringBuilder();
		StringBuilder s2 = new StringBuilder();
		
		try{			
		logger.info(" In  saveLinkLoanAuthor BeforeProc: ");
					
		in.add(linkLoanVo.getLbxLoanNoHID());		
		in.add(linkLoanVo.getDecision());
		in.add(linkLoanVo.getComments());
		in.add(linkLoanVo.getUserId());
		String date=CommonFunction.changeFormat(linkLoanVo.getMakerdDate());
		in.add(date);
		in.add(linkLoanVo.getMakerName());
		out.add(s1.toString());
	    out.add(s2.toString());

		    outMessages=(ArrayList) ConnectionDAO.callSP("LINK_LOAN_AUTHOR",in,out);
		    s1.append(CommonFunction.checkNull(outMessages.get(0)));
		    s2.append(CommonFunction.checkNull(outMessages.get(1)));
		    outMessages.clear();
		    outMessages=null;
		    in.clear();
		    in=null;
		    out.clear();
		    out=null;
		logger.info("s1: "+s1.toString());
		logger.info("s2: "+s2.toString());
		procval=(s2.toString());
		if(!(s1.toString()).equalsIgnoreCase("S"))		
			status=false;
		else
			status=true;  	

		logger.info("In saveLinkLoanAuthor:::::::::::::::::::::::::"+status);

		}catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			s1 = null;
			s2 = null;
		}
		
		return procval;	
		
  }

  public ArrayList<UpdateAssetVO> getLoanDetails(UpdateAssetVO updateAssetVO) {
		logger.info("in getLoanDetails () of MSSQLLinkLoanDAOImpl:::::::::::::");
		
		ArrayList mainlist = new ArrayList();
		ArrayList subList = new ArrayList();
		ArrayList<UpdateAssetVO> loanDetail = new ArrayList<UpdateAssetVO>();
		StringBuilder query = new StringBuilder();
		StringBuilder queryTempCount = new StringBuilder();
		int count=0;
		try {
		
			query.append(" SELECT CLD.loan_no,GCM.customer_name,CACM.asset_collateral_desc,CACM.asset_collateral_value ,CLUAD.loan_id,CLUAD.asset_id ");
	  		query.append(" FROM cr_loan_update_asset_dtl CLUAD ");
	  		query.append(" JOIN CR_LOAN_DTL CLD ON (CLD.LOAN_ID = CLUAD.LOAN_ID )");
	  		query.append(" JOIN gcd_customer_m GCM ON (GCM.CUSTOMER_ID = CLD.LOAN_CUSTOMER_ID )");
			query.append("  JOIN sec_user_branch_dtl S ON(S.USER_ID='"+CommonFunction.checkNull(updateAssetVO.getUserId())+"' AND S.REC_STATUS='A' AND CLD.LOAN_BRANCH=S.BRANCH_ID ) " );
	  		query.append("  JOIN cr_asset_collateral_m CACM ON (CACM.ASSET_ID = CLUAD.ASSET_ID )");
	  		query.append(" WHERE CLUAD.REC_STATUS = 'P' AND CLUAD.MAKER_ID='"+CommonFunction.checkNull(updateAssetVO.getUserId())+"' ");
	  		
	  											
	  		if(!(CommonFunction.checkNull(updateAssetVO.getLbxLoanId()).equalsIgnoreCase(""))) 
	  			query.append(" AND CLUAD.LOAN_ID='"+CommonFunction.checkNull(updateAssetVO.getLbxLoanId())+"' ");
	  		if(!(CommonFunction.checkNull(updateAssetVO.getAsset()).equalsIgnoreCase(""))) 
	  			query.append(" AND CACM.ASSET_COLLATERAL_DESC  LIKE '%"+updateAssetVO.getAsset()+"%' ");
	  	
	  		queryTempCount.append(" SELECT COUNT(1) ");
	  		queryTempCount.append(" FROM cr_loan_update_asset_dtl CLUAD ");
	  		queryTempCount.append(" JOIN CR_LOAN_DTL CLD ON (CLD.LOAN_ID = CLUAD.LOAN_ID )");
	  		queryTempCount.append(" JOIN gcd_customer_m GCM ON (GCM.CUSTOMER_ID = CLD.LOAN_CUSTOMER_ID )");
	  		queryTempCount.append("  JOIN sec_user_branch_dtl S ON(S.USER_ID='"+CommonFunction.checkNull(updateAssetVO.getUserId())+"' AND S.REC_STATUS='A' AND CLD.LOAN_BRANCH=S.BRANCH_ID ) " );
	  		queryTempCount.append("  JOIN cr_asset_collateral_m CACM ON (CACM.ASSET_ID = CLUAD.ASSET_ID )");
	  		queryTempCount.append(" WHERE CLUAD.REC_STATUS = 'P' AND CLUAD.MAKER_ID='"+CommonFunction.checkNull(updateAssetVO.getUserId())+"'");		
			
			
			if(!(CommonFunction.checkNull(updateAssetVO.getLbxLoanId()).equalsIgnoreCase(""))) 
				queryTempCount.append(" AND CLUAD.LOAN_ID='"+CommonFunction.checkNull(updateAssetVO.getLbxLoanId())+"' ");
			if(!(CommonFunction.checkNull(updateAssetVO.getAsset()).equalsIgnoreCase(""))) 
				queryTempCount.append(" AND CACM.ASSET_COLLATERAL_DESC  LIKE '%"+updateAssetVO.getAsset()+"%' ");
	
			
			mainlist = ConnectionDAO.sqlSelect(query.toString());
			logger.info("Select query for link getLoanDetails()............."+query.toString());
			
			logger.info("Count query for link getLoanDetails............."+queryTempCount.toString());
			String dataCount=ConnectionDAO.singleReturn(queryTempCount.toString());
			if(!(CommonFunction.checkNull(dataCount).equalsIgnoreCase("0")))
				count=Integer.parseInt(dataCount);
		
			
			
			int size = mainlist.size();
			for (int i = 0; i < size; i++) {
				subList = (ArrayList) mainlist.get(i);
				if (subList.size() > 0) {
					updateAssetVO=new UpdateAssetVO();
					//linkLoanVo.setLoanNumber(CommonFunction.checkNull(subList.get(0)).trim());
					updateAssetVO.setLoanNo("<a href=updateAssetEdit.do?method=openEditVehical&loanId="+ CommonFunction.checkNull(subList.get(4)).toString()+"&assetId="+ CommonFunction.checkNull(subList.get(5)).toString()+">"
							+ CommonFunction.checkNull(subList.get(0)).toString() + "</a>");

					updateAssetVO.setCustomerName(CommonFunction.checkNull(subList.get(1)).trim());
					updateAssetVO.setAsset(CommonFunction.checkNull(subList.get(2)).trim());
					updateAssetVO.setAssetValue(CommonFunction.checkNull(subList.get(3)).trim());
					
					updateAssetVO.setLbxLoanId(CommonFunction.checkNull(subList.get(4)).trim());
					updateAssetVO.setAssetId(CommonFunction.checkNull(subList.get(5)).trim());
					
					updateAssetVO.setTotalRecordSize(count);
					loanDetail.add(updateAssetVO);
					updateAssetVO=null;
				 }
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			query=null;	
			mainlist.clear();
			mainlist=null;
			subList.clear();
			subList=null;
			
		}		
		return loanDetail;
	}


//Manish Space Starts For getLoanDetailsAuthor
  //method for first clik of link
  @Override
  public ArrayList<UpdateAssetVO> getLoanDetailsAuthor(UpdateAssetVO updateAssetVO) {
  	logger.info("in getLoanDetailsAuthor method of LinkLoanDAOImpl:::::::::::::");
  	
  	ArrayList mainlist = new ArrayList();
  	ArrayList subList = new ArrayList();
  	ArrayList<UpdateAssetVO> loanDetail = new ArrayList<UpdateAssetVO>();
  	StringBuilder query = new StringBuilder();
  	StringBuilder queryTempCount = new StringBuilder();
  	int count=0;

  	try {
  		query.append(" SELECT CLD.loan_no,GCM.customer_name,CACM.asset_collateral_desc,CACM.asset_collateral_value ,CLUAD.loan_id,CLUAD.asset_id ");
  		query.append(" FROM cr_loan_update_asset_dtl CLUAD ");
  		query.append(" JOIN CR_LOAN_DTL CLD ON (CLD.LOAN_ID = CLUAD.LOAN_ID )");
  		query.append(" JOIN gcd_customer_m GCM ON (GCM.CUSTOMER_ID = CLD.LOAN_CUSTOMER_ID )");
		query.append("  JOIN sec_user_branch_dtl S ON(S.USER_ID='"+CommonFunction.checkNull(updateAssetVO.getUserId())+"' AND S.REC_STATUS='A' AND CLD.LOAN_BRANCH=S.BRANCH_ID ) " );
  		query.append("  JOIN cr_asset_collateral_m CACM ON (CACM.ASSET_ID = CLUAD.ASSET_ID )");
  		query.append(" WHERE CLUAD.REC_STATUS = 'F' AND CLUAD.MAKER_ID<>'"+CommonFunction.checkNull(updateAssetVO.getUserId())+"' ");
  		
  		if(!(CommonFunction.checkNull(updateAssetVO.getLbxLoanId()).equalsIgnoreCase(""))) 
  			query.append(" AND CLUAD.LOAN_ID='"+CommonFunction.checkNull(updateAssetVO.getLbxLoanId())+"' ");
  		if(!(CommonFunction.checkNull(updateAssetVO.getAsset()).equalsIgnoreCase(""))) 
  			query.append(" AND CACM.ASSET_COLLATERAL_DESC  LIKE '%"+updateAssetVO.getAsset()+"%' ");
  		
  		
  
  		queryTempCount.append(" SELECT COUNT(1) ");
  		queryTempCount.append(" FROM cr_loan_update_asset_dtl CLUAD ");
  		queryTempCount.append(" JOIN CR_LOAN_DTL CLD ON (CLD.LOAN_ID = CLUAD.LOAN_ID )");
  		queryTempCount.append(" JOIN gcd_customer_m GCM ON (GCM.CUSTOMER_ID = CLD.LOAN_CUSTOMER_ID )");
  		queryTempCount.append("  JOIN sec_user_branch_dtl S ON(S.USER_ID='"+CommonFunction.checkNull(updateAssetVO.getUserId())+"' AND S.REC_STATUS='A' AND CLD.LOAN_BRANCH=S.BRANCH_ID ) " );
  		queryTempCount.append("  JOIN cr_asset_collateral_m CACM ON (CACM.ASSET_ID = CLUAD.ASSET_ID )");
  		queryTempCount.append(" WHERE CLUAD.REC_STATUS = 'F' ");
  		

  		if(!(CommonFunction.checkNull(updateAssetVO.getLbxLoanId()).equalsIgnoreCase(""))) 
  			queryTempCount.append(" AND CLUAD.LOAN_ID='"+CommonFunction.checkNull(updateAssetVO.getLbxLoanId())+"' ");
  		if(!(CommonFunction.checkNull(updateAssetVO.getAsset()).equalsIgnoreCase(""))) 
  			queryTempCount.append(" AND CACM.ASSET_COLLATERAL_DESC  LIKE '%"+updateAssetVO.getAsset()+"%' ");
  		
  		logger.info("query for link ............."+query.toString());
  		logger.info("query for link ............."+queryTempCount.toString());
  		
  		mainlist = ConnectionDAO.sqlSelect(query.toString());
  		String dataCount=ConnectionDAO.singleReturn(queryTempCount.toString());
  		if(!(CommonFunction.checkNull(dataCount).equalsIgnoreCase("0")))
  			count=Integer.parseInt(dataCount);
  		
  		
  		int size = mainlist.size();
  		for (int i = 0; i < size; i++) {
  			subList = (ArrayList) mainlist.get(i);
  			if (subList.size() > 0) {
  				updateAssetVO=new UpdateAssetVO();
  				
  				updateAssetVO.setLoanNo("<a href=updateAssetAuthorFrame.do?method=openAuthorVehical&loanId="+ CommonFunction.checkNull(subList.get(4)).toString()+"&assetId="+ CommonFunction.checkNull(subList.get(5)).toString()+">"
  						+ CommonFunction.checkNull(subList.get(0)).toString() + "</a>");

  				updateAssetVO.setCustomerName(CommonFunction.checkNull(subList.get(1)).trim());
  				updateAssetVO.setAsset(CommonFunction.checkNull(subList.get(2)).trim());
  				updateAssetVO.setAssetValue(CommonFunction.checkNull(subList.get(3)).trim());
  		
  				updateAssetVO.setLbxLoanId(CommonFunction.checkNull(subList.get(4)).trim());
  				updateAssetVO.setAssetId(CommonFunction.checkNull(subList.get(5)).trim());
  				
  				updateAssetVO.setTotalRecordSize(count);
  				loanDetail.add(updateAssetVO);
  				updateAssetVO=null;
  			 }
  		}
  		
  	} catch (Exception e) {
  		e.printStackTrace();
  	}
  	finally
  	{
  		query=null;	
  		mainlist.clear();
  		mainlist=null;
  		subList.clear();
  		subList=null;
  		
  	}		
  	return loanDetail;
  }
  
  //Manish Space Starts for get update Asset Author Screen.

  
  
  public ArrayList<UpdateAssetVO> getAuthorScreenAssetVehical(
			UpdateAssetVO updateAssetVO) {

		ArrayList searchlist = new ArrayList();
		
		ArrayList<UpdateAssetVO> list = new ArrayList<UpdateAssetVO>();
		logger.info("getAuthorScreenAssetVehical***************************** = "+updateAssetVO.getLbxLoanId());
		
		try {
			StringBuilder bufInsSql = new StringBuilder();
			bufInsSql.append(" select top 1 c.loan_id,a.asset_id,IIF (A.ASSET_NEW_OLD='N','New','Old') as asset_new_old ,A.ASSET_COLLATERAL_DESC,A.VEHICLE_MAKE, ") ;
			bufInsSql.append(" A.VEHICLE_MODEL, cluad.VEHICLE_MANUFACTURING_YEAR, cluad.VEHICLE_REGISTRATION_NO, dbo.DATE_FORMAT(cluad.VEHICLE_REGISTRATION_DATE,'"+ dateFormat +"'), ");
			bufInsSql.append(" cluad.VEHICLE_CHASIS_NUMBER, cluad.VEHICLE_Insurer, dbo.DATE_FORMAT(cluad.VEHICLE_INSURED_DATE,'"+ dateFormat +"'), A.VEHICLE_COLLATERAL_COST,cluad.VEHICLE_OWNER , ");
			bufInsSql.append(" a.ASSET_SUPPLIER_DESC,a.ASSET_MANUFATURER_DESC,a.LOAN_AMOUNT,a.DEFAULT_LTV,cluad.ENGINE_NUMBER,a.VEHICLE_DISCOUNT,ISNULL(cluad.VEHICLE_IDV,'0.00') as VEHICLE_IDV, ");
			bufInsSql.append(" a.vehicle_grid_value,dbo.DATE_FORMAT(cluad.vechicle_invoice_date,'"+ dateFormat +"'),cluad.invoice_no,a.vehicle_valuation_amount, ");
			bufInsSql.append(" cluad.rc_received,dbo.DATE_FORMAT(cluad.rc_received_date,'"+ dateFormat +"') ,a.VEHICLE_ASSET_USES_TYPE ,a.COLLATERAL_SECURITY_MARGIN,s.state_desc, ");
			bufInsSql.append("  a.VEHICLE_VALUATION_AMOUNT,a.VEHICLE_VALUE,a.asset_standard,a.asset_collateral_value,g.DESCRIPTION,cluad.INVOICE_AMOUNT ,cluad.PERMIT_RECEIVED, ");
			bufInsSql.append(" dbo.DATE_FORMAT(cluad.PERMIT_RECEIVED_DATE,'"+ dateFormat +"' ),c.loan_no,gcd.customer_name,INVOICE_UPDATE_FLAG,RC_UPDATE_FLAG,INSURANCE_UPDATE_FLAG ") ;
			bufInsSql.append(" from cr_loan_update_asset_dtl cluad  ");
			bufInsSql.append("  join cr_asset_collateral_m a on (cluad.ASSET_ID = a.ASSET_ID and a.ASSET_TYPE='ASSET' and a.asset_collateral_class='VEHICLE') ");
			bufInsSql.append("  join cr_loan_collateral_m b on b.assetid=a.asset_id and b.loan_id=cluad.loan_id ");
			bufInsSql.append("  join generic_master g on g.VALUE=a.SECURITY and g.GENERIC_KEY='SECURITY_TYPE'");  
			bufInsSql.append("  join cr_loan_dtl c on c.loan_id=b.loan_id   ");
			bufInsSql.append("  join gcd_customer_m gcd on gcd.customer_id=c.loan_customer_id  ");
			bufInsSql.append("  join com_state_m s on  s.state_id= a.STATE ");
			bufInsSql.append(" where  cluad.loan_id='"+StringEscapeUtils.escapeSql(updateAssetVO.getLbxLoanId())+"' ");
			bufInsSql.append(" and cluad.asset_id='"+StringEscapeUtils.escapeSql(updateAssetVO.getAssetId())+"' ");
			logger.info("IN getAuthorScreenAssetVehical()  query1 ### "+ bufInsSql.toString());
			
			searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
			
			for (int i = 0; i < searchlist.size(); i++) {
			
				ArrayList data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					updateAssetVO = new UpdateAssetVO();
					updateAssetVO.setLbxLoanId(CommonFunction.checkNull(data.get(0)));
					updateAssetVO.setAssetId(CommonFunction.checkNull(data.get(1)));
					updateAssetVO.setAssetNature(CommonFunction.checkNull(data.get(2)));
					updateAssetVO.setAssetsCollateralDesc(CommonFunction.checkNull(data.get(3)));
					updateAssetVO.setVehicleMake(CommonFunction.checkNull(data.get(4)));
					updateAssetVO.setVehicleModel(CommonFunction.checkNull(data.get(5)));
					updateAssetVO.setVehicleYearOfManufact(CommonFunction.checkNull(data.get(6)));
					updateAssetVO.setVehicleRegNo(CommonFunction.checkNull(data.get(7)));
					updateAssetVO.setVehicleRegDate(CommonFunction.checkNull(data.get(8)));
					updateAssetVO.setVehicleChesisNo(CommonFunction.checkNull(data.get(9)));
					updateAssetVO.setVehicleInsurer(CommonFunction.checkNull(data.get(10)));
					updateAssetVO.setVehicleInsureDate(CommonFunction.checkNull(data.get(11)));
					updateAssetVO.setVehicleCost(CommonFunction.checkNull(data.get(12)));
					updateAssetVO.setVehicleOwner(CommonFunction.checkNull(data.get(13)));
					updateAssetVO.setMachineSupplier(CommonFunction.checkNull(data.get(14)));
					updateAssetVO.setAssetManufact(CommonFunction.checkNull(data.get(15)));
					updateAssetVO.setLoanAmount(CommonFunction.checkNull(data.get(16)));
					updateAssetVO.setCollateralSecurityMarginDF(CommonFunction.checkNull(data.get(17)));
					updateAssetVO.setEngineNumber(CommonFunction.checkNull(data.get(18)));
					updateAssetVO.setVehicleDiscount(CommonFunction.checkNull(data.get(19)));
					updateAssetVO.setIdv(CommonFunction.checkNull(data.get(20)));
					updateAssetVO.setGridValue(CommonFunction.checkNull(data.get(21)));
					updateAssetVO.setVehicleInvoiceDate(CommonFunction.checkNull(data.get(22)));
					updateAssetVO.setInvoiceNumber(CommonFunction.checkNull(data.get(23)));
					updateAssetVO.setValuationCost(CommonFunction.checkNull(data.get(24)));
					updateAssetVO.setRcReceived(CommonFunction.checkNull(data.get(25)));
					updateAssetVO.setRcReceivedDate(CommonFunction.checkNull(data.get(26)));
					updateAssetVO.setUsageType(CommonFunction.checkNull(data.get(27)));
					updateAssetVO.setCollateralSecurityMargin(CommonFunction.checkNull(data.get(28)));
					updateAssetVO.setTxtStateCode(CommonFunction.checkNull(data.get(29)));
					updateAssetVO.setValuationCost(CommonFunction.checkNull(data.get(30)));
					updateAssetVO.setVehicleCost(CommonFunction.checkNull(data.get(31)));
					updateAssetVO.setAssetStandard(CommonFunction.checkNull(data.get(32)));
					updateAssetVO.setAssetsCollateralValue(CommonFunction.checkNull(data.get(33)));
					updateAssetVO.setSecurityTypes(CommonFunction.checkNull(data.get(34)));
					updateAssetVO.setInvoiceAmount(CommonFunction.checkNull(data.get(35)));	
					updateAssetVO.setPermitReceived(CommonFunction.checkNull(data.get(36)));
					updateAssetVO.setPermitReceivedDate(CommonFunction.checkNull(data.get(37)));	
					updateAssetVO.setLoanNo(CommonFunction.checkNull(data.get(38)));
					updateAssetVO.setCustomerName(CommonFunction.checkNull(data.get(39)));
					
					updateAssetVO.setInvoiceUpdateCheckBox(CommonFunction.checkNull(data.get(40)));	
					updateAssetVO.setRcUpdateCheckBox(CommonFunction.checkNull(data.get(41)));
					updateAssetVO.setInsuranceUpdateCheckBox(CommonFunction.checkNull(data.get(42)));
					
					
					list.add(updateAssetVO);
					updateAssetVO=null;
					data.clear();
					data=null;

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			
			searchlist.clear();
			searchlist=null;
		}

		return list;
	}
//Manish Ends for Loan Details for Author
   	@Override
	public ArrayList<UpdateAssetVO> openEditVehical(
			UpdateAssetVO updateAssetVO) {

		ArrayList searchlist = new ArrayList();
		
		ArrayList<UpdateAssetVO> list = new ArrayList<UpdateAssetVO>();
		logger.info("Loan_id openEditVehical ## >> = "+updateAssetVO.getLbxLoanId());

		try {
			StringBuilder bufInsSql = new StringBuilder();
		
			bufInsSql.append(" select top 1 c.loan_id,a.asset_id,IIF (A.ASSET_NEW_OLD='N','New','Old') as asset_new_old ,A.ASSET_COLLATERAL_DESC,A.VEHICLE_MAKE, ") ;
			bufInsSql.append(" A.VEHICLE_MODEL, cluad.VEHICLE_MANUFACTURING_YEAR, cluad.VEHICLE_REGISTRATION_NO, dbo.DATE_FORMAT(cluad.VEHICLE_REGISTRATION_DATE,'"+ dateFormat +"'), ");
			bufInsSql.append(" cluad.VEHICLE_CHASIS_NUMBER, cluad.VEHICLE_Insurer, dbo.DATE_FORMAT(cluad.VEHICLE_INSURED_DATE,'"+ dateFormat +"'), A.VEHICLE_COLLATERAL_COST,cluad.VEHICLE_OWNER , ");
			bufInsSql.append(" a.ASSET_SUPPLIER_DESC,a.ASSET_MANUFATURER_DESC,a.LOAN_AMOUNT,a.DEFAULT_LTV,cluad.ENGINE_NUMBER,a.VEHICLE_DISCOUNT,ISNULL(cluad.VEHICLE_IDV,'0.00') as VEHICLE_IDV, ");
			bufInsSql.append(" a.vehicle_grid_value,dbo.DATE_FORMAT(cluad.vechicle_invoice_date,'"+ dateFormat +"'),cluad.invoice_no,a.vehicle_valuation_amount, ");
			bufInsSql.append(" cluad.rc_received,dbo.DATE_FORMAT(cluad.rc_received_date,'"+ dateFormat +"') ,a.VEHICLE_ASSET_USES_TYPE ,a.COLLATERAL_SECURITY_MARGIN,s.state_desc, ");
			bufInsSql.append("  a.VEHICLE_VALUATION_AMOUNT,a.VEHICLE_VALUE,a.asset_standard,a.asset_collateral_value,g.DESCRIPTION,cluad.INVOICE_AMOUNT ,cluad.PERMIT_RECEIVED, ");
			bufInsSql.append(" dbo.DATE_FORMAT(cluad.PERMIT_RECEIVED_DATE,'"+ dateFormat +"' ),c.loan_no,gcd.customer_name,INVOICE_UPDATE_FLAG,RC_UPDATE_FLAG,INSURANCE_UPDATE_FLAG ") ;
			bufInsSql.append(" from cr_loan_update_asset_dtl cluad  ");
			bufInsSql.append("  join cr_asset_collateral_m a on (cluad.ASSET_ID = a.ASSET_ID and a.ASSET_TYPE='ASSET' and a.asset_collateral_class='VEHICLE') ");
			bufInsSql.append("  join cr_loan_collateral_m b on b.assetid=a.asset_id and b.loan_id=cluad.loan_id ");
			bufInsSql.append("  join generic_master g on g.VALUE=a.SECURITY and g.GENERIC_KEY='SECURITY_TYPE'");  
			bufInsSql.append("  join cr_loan_dtl c on c.loan_id=b.loan_id   ");
			bufInsSql.append("  join gcd_customer_m gcd on gcd.customer_id=c.loan_customer_id  ");
			bufInsSql.append("  join com_state_m s on  s.state_id= a.STATE ");
			bufInsSql.append(" where   cluad.loan_id='"+StringEscapeUtils.escapeSql(updateAssetVO.getLbxLoanId())+"' ");
			bufInsSql.append(" and cluad.asset_id='"+StringEscapeUtils.escapeSql(updateAssetVO.getAssetId())+"' ");
			logger.info("IN openEditVehical() search query1 ### "+ bufInsSql.toString());
			
			
			searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());

			for (int i = 0; i < searchlist.size(); i++) {
			
				ArrayList data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					updateAssetVO = new UpdateAssetVO();
					updateAssetVO.setLbxLoanId(CommonFunction.checkNull(data.get(0)));
					updateAssetVO.setAssetId(CommonFunction.checkNull(data.get(1)));
					updateAssetVO.setAssetNature(CommonFunction.checkNull(data.get(2)));
					updateAssetVO.setAssetsCollateralDesc(CommonFunction.checkNull(data.get(3)));
					updateAssetVO.setVehicleMake(CommonFunction.checkNull(data.get(4)));
					updateAssetVO.setVehicleModel(CommonFunction.checkNull(data.get(5)));
					updateAssetVO.setVehicleYearOfManufact(CommonFunction.checkNull(data.get(6)));
					updateAssetVO.setVehicleRegNo(CommonFunction.checkNull(data.get(7)));
					updateAssetVO.setVehicleRegDate(CommonFunction.checkNull(data.get(8)));
					updateAssetVO.setVehicleChesisNo(CommonFunction.checkNull(data.get(9)));
					updateAssetVO.setVehicleInsurer(CommonFunction.checkNull(data.get(10)));
					updateAssetVO.setVehicleInsureDate(CommonFunction.checkNull(data.get(11)));
					updateAssetVO.setVehicleCost(CommonFunction.checkNull(data.get(12)));
					updateAssetVO.setVehicleOwner(CommonFunction.checkNull(data.get(13)));
					updateAssetVO.setMachineSupplier(CommonFunction.checkNull(data.get(14)));
					updateAssetVO.setAssetManufact(CommonFunction.checkNull(data.get(15)));
					updateAssetVO.setLoanAmount(CommonFunction.checkNull(data.get(16)));
					updateAssetVO.setCollateralSecurityMarginDF(CommonFunction.checkNull(data.get(17)));
					updateAssetVO.setEngineNumber(CommonFunction.checkNull(data.get(18)));
					updateAssetVO.setVehicleDiscount(CommonFunction.checkNull(data.get(19)));
					updateAssetVO.setIdv(CommonFunction.checkNull(data.get(20)));
					updateAssetVO.setGridValue(CommonFunction.checkNull(data.get(21)));
					updateAssetVO.setVehicleInvoiceDate(CommonFunction.checkNull(data.get(22)));
					updateAssetVO.setInvoiceNumber(CommonFunction.checkNull(data.get(23)));
					updateAssetVO.setValuationCost(CommonFunction.checkNull(data.get(24)));
					updateAssetVO.setRcReceived(CommonFunction.checkNull(data.get(25)));
					updateAssetVO.setRcReceivedDate(CommonFunction.checkNull(data.get(26)));
					updateAssetVO.setUsageType(CommonFunction.checkNull(data.get(27)));
					updateAssetVO.setCollateralSecurityMargin(CommonFunction.checkNull(data.get(28)));
					updateAssetVO.setTxtStateCode(CommonFunction.checkNull(data.get(29)));
					updateAssetVO.setValuationCost(CommonFunction.checkNull(data.get(30)));
					updateAssetVO.setVehicleCost(CommonFunction.checkNull(data.get(31)));
					updateAssetVO.setAssetStandard(CommonFunction.checkNull(data.get(32)));
					updateAssetVO.setAssetsCollateralValue(CommonFunction.checkNull(data.get(33)));
					updateAssetVO.setSecurityTypes(CommonFunction.checkNull(data.get(34)));
					updateAssetVO.setInvoiceAmount(CommonFunction.checkNull(data.get(35)));	
					updateAssetVO.setPermitReceived(CommonFunction.checkNull(data.get(36)));
					updateAssetVO.setPermitReceivedDate(CommonFunction.checkNull(data.get(37)));	
					updateAssetVO.setLoanNo(CommonFunction.checkNull(data.get(38)));
					updateAssetVO.setCustomerName(CommonFunction.checkNull(data.get(39)));
					updateAssetVO.setInvoiceUpdateCheckBox(CommonFunction.checkNull(data.get(40)));	
					updateAssetVO.setRcUpdateCheckBox(CommonFunction.checkNull(data.get(41)));
					updateAssetVO.setInsuranceUpdateCheckBox(CommonFunction.checkNull(data.get(42)));
					list.add(updateAssetVO);
					updateAssetVO=null;
					data.clear();
					data=null;

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			
			searchlist.clear();
			searchlist=null;
		}

		return list;
	}

   	
   
   	public boolean updateAsset(Object ob) {
   		UpdateAssetVO vo = (UpdateAssetVO) ob;
   		PrepStmtObject insertPrepStmtObject = null;
   		StringBuilder bufInsSql = null;
   		ArrayList insertlist = new ArrayList();
   		boolean status = false;
   	     String invoiceUpdateCheckBox="N";
		 String rcUpdateCheckBox="N";
		 String insuranceUpdateCheckBox="N";
   		
   		try {
   	
   			String countQry="SELECT COUNT(1) FROM cr_loan_update_asset_dtl WHERE LOAN_ID='"+CommonFunction.checkNull(vo.getLbxLoanId())+"' AND ASSET_ID='"+CommonFunction.checkNull(vo.getAssetId())+"' ";
   			String countStatus=ConnectionDAO.singleReturn(countQry);
   			if(!CommonFunction.checkNull(countStatus).equals("0"))
   			{
   				bufInsSql = new StringBuilder();
   				insertPrepStmtObject = new PrepStmtObject();
   				bufInsSql.append("DELETE FROM cr_loan_update_asset_dtl WHERE LOAN_ID='"+CommonFunction.checkNull(vo.getLbxLoanId())+"' AND ASSET_ID='"+CommonFunction.checkNull(vo.getAssetId())+"' ");
   				insertPrepStmtObject.setSql(bufInsSql.toString());
   				logger.info("## In updateAsset() : Delete Query of cr_loan_update_asset_dtl : ==> "+insertPrepStmtObject.printQuery());
   				insertlist.add(insertPrepStmtObject);
   				bufInsSql=null;
   				insertPrepStmtObject=null;
   				
   			}
   		
   		 if(CommonFunction.checkNull(vo.getInvoiceUpdateCheckBox()).equalsIgnoreCase("on"))
   		 {
   			 invoiceUpdateCheckBox="Y";
   		 }
   		 if(CommonFunction.checkNull(vo.getRcUpdateCheckBox()).equalsIgnoreCase("on"))
   		 {
   			 rcUpdateCheckBox="Y";
   		 }
   		 if(CommonFunction.checkNull(vo.getInsuranceUpdateCheckBox()).equalsIgnoreCase("on"))
   		 {
   			 insuranceUpdateCheckBox="Y";
   		 }
   			bufInsSql = new StringBuilder();
   			insertPrepStmtObject = new PrepStmtObject();
   			
   			bufInsSql.append("INSERT INTO cr_loan_update_asset_dtl (LOAN_ID,ASSET_ID,VEHICLE_MANUFACTURING_YEAR,VEHICLE_REGISTRATION_NO,");
   			bufInsSql.append("VEHICLE_REGISTRATION_DATE,VEHICLE_CHASIS_NUMBER,VEHICLE_INSURER,");
   			bufInsSql.append(" VEHICLE_INSURED_DATE,VEHICLE_OWNER,ENGINE_NUMBER,VEHICLE_IDV,VECHICLE_INVOICE_DATE,INVOICE_NO,RC_RECEIVED,RC_RECEIVED_DATE,");
   			bufInsSql.append(" INVOICE_AMOUNT,PERMIT_RECEIVED,PERMIT_RECEIVED_DATE,REC_STATUS,MAKER_ID,MAKER_DATE,INVOICE_UPDATE_FLAG,RC_UPDATE_FLAG,INSURANCE_UPDATE_FLAG)");
   			 
   				bufInsSql.append(" VALUES ( ");
   				bufInsSql.append(" ?,");//LOAN_ID
   				bufInsSql.append(" ?,");//ASSET_ID
   				bufInsSql.append(" ?,");//VEHICLE_MANUFACTURING_YEAR
   				bufInsSql.append(" ?,");//VEHICLE_REGISTRATION_NO
   				bufInsSql.append(dbo);
				bufInsSql.append(" STR_TO_DATE(?,'" + dateFormat+ "'),"); //VEHICLE_REGISTRATION_DATE
   				
   				bufInsSql.append(" ?,");//VEHICLE_CHASIS_NUMBER
   				bufInsSql.append(" ?,");//VEHICLE_INSURER
   			//	bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),");
   				bufInsSql.append(dbo);
				bufInsSql.append(" STR_TO_DATE(?,'" + dateFormat+ "'),");//VEHICLE_INSURED_DATE
   				bufInsSql.append(" ?,");//VEHICLE_OWNER
   				bufInsSql.append(" ?,");//ENGINE_NUMBER
   				bufInsSql.append(" ?,");//VEHICLE_IDV
   			//	bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),");
   				bufInsSql.append(dbo);
				bufInsSql.append(" STR_TO_DATE(?,'" + dateFormat+ "'),"); //VECHICLE_INVOICE_DATE
   				bufInsSql.append(" ?,");//INVOICE_NO
   				bufInsSql.append(" ?,");//RC_RECEIVED
   			//	bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),");
   				bufInsSql.append(dbo);
				bufInsSql.append(" STR_TO_DATE(?,'" + dateFormat+ "'),"); //RC_RECEIVED_DATE
   				bufInsSql.append(" ?,");//INVOICE_AMOUNT
   				bufInsSql.append(" ?,");//PERMIT_RECEIVED
   			//	bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),");
   				bufInsSql.append(dbo);
				bufInsSql.append(" STR_TO_DATE(?,'" + dateFormat+ "'),"); //PERMIT_RECEIVED_DATE
   				bufInsSql.append(" ?,");//REC_STATUS
   				bufInsSql.append(" ?,");//MAKER_ID
   			//	bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND))");
   				bufInsSql.append(dbo);
				bufInsSql.append(" STR_TO_DATE(?,'" + dateFormat+ "'),"); //MAKER_DATE
				bufInsSql.append(" ?,");//INVOICE_UPDATE_FLAG 
				bufInsSql.append(" ?,");//RC_UPDATE_FLAG 
				bufInsSql.append(" ? ) ");//INSURANCE_UPDATE_FLAG
   				
   			if (CommonFunction.checkNull(vo.getLbxLoanId()).equalsIgnoreCase("")) //LOAN_ID
   				insertPrepStmtObject.addNull();
   			else
   				insertPrepStmtObject.addString(vo.getLbxLoanId().toUpperCase().trim());
   				
   			if (CommonFunction.checkNull(vo.getAssetId()).equalsIgnoreCase("")) //ASSET_ID
   				insertPrepStmtObject.addNull();
   			else
   				insertPrepStmtObject.addString(vo.getAssetId().toUpperCase().trim());
   				
   			
   			if (CommonFunction.checkNull(vo.getVehicleYearOfManufact()).equalsIgnoreCase("")) //VEHICLE_MANUFACTURING_YEAR
   				insertPrepStmtObject.addNull();
   			else
   				insertPrepStmtObject.addString(vo.getVehicleYearOfManufact().toUpperCase().trim());

   			if (CommonFunction.checkNull(vo.getVehicleRegNo()).equalsIgnoreCase(""))  //VEHICLE_REGISTRATION_NO
   				insertPrepStmtObject.addNull();
   			else
   				insertPrepStmtObject.addString(vo.getVehicleRegNo().toUpperCase().trim());
   			
   			if (CommonFunction.checkNull(vo.getVehicleRegDate()).equalsIgnoreCase(""))  //VEHICLE_REGISTRATION_DATE
   				insertPrepStmtObject.addNull();
   			else
   				insertPrepStmtObject.addString(vo.getVehicleRegDate().toUpperCase().trim());
   			
   			if (CommonFunction.checkNull(vo.getVehicleChesisNo()).equalsIgnoreCase(""))  //VEHICLE_CHASIS_NUMBER
   				insertPrepStmtObject.addNull();
   			else
   				insertPrepStmtObject.addString(vo.getVehicleChesisNo().toUpperCase().trim());
   			
   			if (CommonFunction.checkNull(vo.getVehicleInsurer()).equalsIgnoreCase(""))   //VEHICLE_INSURER
   				insertPrepStmtObject.addNull();
   			else
   				insertPrepStmtObject.addString(vo.getVehicleInsurer().toUpperCase().trim());
   			
   			if (CommonFunction.checkNull(vo.getVehicleInsureDate()).equalsIgnoreCase(""))  //VEHICLE_INSURED_DATE
   				insertPrepStmtObject.addNull();
   			else
   				insertPrepStmtObject.addString(vo.getVehicleInsureDate().toUpperCase().trim());
   			
   			if (CommonFunction.checkNull(vo.getVehicleOwner()).equalsIgnoreCase(""))  //VEHICLE_OWNER
   				insertPrepStmtObject.addNull();
   			else
   				insertPrepStmtObject.addString(vo.getVehicleOwner().toUpperCase().trim());
   			
   			if (CommonFunction.checkNull(vo.getEngineNumber()).equalsIgnoreCase(""))   //ENGINE_NUMBER
   				insertPrepStmtObject.addNull();
   			else
   				insertPrepStmtObject.addString(vo.getEngineNumber().toUpperCase().trim());
   			
   			if (CommonFunction.checkNull(vo.getIdv()).equalsIgnoreCase(""))   //VEHICLE_IDV
   				insertPrepStmtObject.addNull();
   			else
   				insertPrepStmtObject.addString(vo.getIdv().toUpperCase().trim());
   			
   			if (CommonFunction.checkNull(vo.getVehicleInvoiceDate()).equalsIgnoreCase(""))   //VECHICLE_INVOICE_DATE
   				insertPrepStmtObject.addNull();
   			else
   				insertPrepStmtObject.addString(vo.getVehicleInvoiceDate().toUpperCase().trim());
   			
   			if (CommonFunction.checkNull(vo.getInvoiceNumber()).equalsIgnoreCase(""))   //INVOICE_NO
   				insertPrepStmtObject.addNull();
   			else
   				insertPrepStmtObject.addString(vo.getInvoiceNumber().toUpperCase().trim());
   			
   			if (CommonFunction.checkNull(vo.getRcReceived()).equalsIgnoreCase(""))   //RC_RECEIVED
   				insertPrepStmtObject.addNull();
   			else
   				insertPrepStmtObject.addString(vo.getRcReceived().toUpperCase().trim());
   			
   			if (CommonFunction.checkNull(vo.getRcReceivedDate()).equalsIgnoreCase(""))      //RC_RECEIVED_DATE
   				insertPrepStmtObject.addNull();
   			else
   				insertPrepStmtObject.addString(vo.getRcReceivedDate().toUpperCase().trim());
   			
   			if (CommonFunction.checkNull(vo.getInvoiceAmount()).equalsIgnoreCase(""))        //INVOICE_AMOUNT
   				insertPrepStmtObject.addNull();
   			else
   				insertPrepStmtObject.addString(vo.getInvoiceAmount());
   			
   			if (CommonFunction.checkNull(vo.getPermitReceived()).equalsIgnoreCase(""))      //PERMIT_RECEIVED
   				insertPrepStmtObject.addNull();
   			else
   				insertPrepStmtObject.addString(vo.getPermitReceived().toUpperCase().trim());
   			
   			if (CommonFunction.checkNull(vo.getPermitReceivedDate()).equalsIgnoreCase(""))   //PERMIT_RECEIVED_DATE
   				insertPrepStmtObject.addNull();
   			else
   				insertPrepStmtObject.addString(vo.getPermitReceivedDate().toUpperCase().trim());
   			
   			
   				insertPrepStmtObject.addString(vo.getStatusFlag()); //REC_STATUS
   				
   						
   			if (CommonFunction.checkNull(vo.getUserId()).equalsIgnoreCase(""))   //MAKER_ID
   				insertPrepStmtObject.addNull();
   			else
   				insertPrepStmtObject.addString(vo.getUserId().toUpperCase().trim());
   			
   			if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))   //MAKER_DATE
   				insertPrepStmtObject.addNull();
   			else
   				insertPrepStmtObject.addString(vo.getMakerDate().toUpperCase().trim());
   			
   			if (CommonFunction.checkNull(invoiceUpdateCheckBox).equalsIgnoreCase(""))   //INVOICE_UPDATE_FLAG
   				insertPrepStmtObject.addNull();
   			else
   				insertPrepStmtObject.addString(invoiceUpdateCheckBox);
   			
   			
   			if (CommonFunction.checkNull(rcUpdateCheckBox).equalsIgnoreCase(""))   //RC_UPDATE_FLAG
   				insertPrepStmtObject.addNull();
   			else
   				insertPrepStmtObject.addString(rcUpdateCheckBox);
   			
   			if (CommonFunction.checkNull(insuranceUpdateCheckBox).equalsIgnoreCase(""))   //INSURANCE_UPDATE_FLAG
   				insertPrepStmtObject.addNull();
   			else
   				insertPrepStmtObject.addString(insuranceUpdateCheckBox);
   			
   			  insertPrepStmtObject.setSql(bufInsSql.toString());
   			  logger.info("Query For Insert is ::::"+insertPrepStmtObject.printQuery());
   			  insertlist.add(insertPrepStmtObject);
   			  status = ConnectionDAO.sqlInsUpdDeletePrepStmt(insertlist);
   			  insertPrepStmtObject=null;                   
   			  bufInsSql=null;	                    
   					     
   				
   		
   		} catch (Exception e) {
   			e.printStackTrace();
   		}
   		
   		finally{
   			insertlist.clear();
   			insertlist = null;
   			
   		         }
   		return status;
   	}
	@Override
	public String checkRegNo(UpdateAssetVO updateAssetVO) {
		
		logger.info("checkRegNo");
		StringBuilder query=new StringBuilder();
		query.append("select count(1) from cr_loan_update_asset_dtl a inner join cr_asset_collateral_m b on b.asset_id=a.asset_id where a.asset_id<>'"+updateAssetVO.getAssetId()+"' and a.VEHICLE_REGISTRATION_NO='"+updateAssetVO.getVehicleRegNo()+"' and b.asset_collateral_class='VEHICLE'");
		logger.info("checkRegNo: "+query.toString());
		String count=ConnectionDAO.singleReturn(query.toString());
		query=null;
		return count;
	}

	@Override
	public boolean saveUpdateAsset(Object updateAssetVO) { 
		logger.info("saveUpdateAsset");

		UpdateAssetVO vo = (UpdateAssetVO) updateAssetVO;
		PrepStmtObject insertPrepStmtObject = null;
		StringBuilder bufInsSql = null;
		ArrayList insertlist = new ArrayList();
		boolean status = false;
		
		
		try {
			
			bufInsSql = new StringBuilder();
			insertPrepStmtObject = new PrepStmtObject();
			bufInsSql.append(" UPDATE  cr_loan_update_asset_dtl SET REC_STATUS=?,REMARKS=? ,AUTHOR_ID=?,AUTHOR_DATE= ");
			bufInsSql.append(dbo);
			bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) ");
			bufInsSql.append(" WHERE LOAN_ID='"+vo.getLbxLoanId()+"' AND ASSET_ID='"+vo.getAssetId()+"' ");
			
			if (CommonFunction.checkNull(vo.getAuthorDecission()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getAuthorDecission().toUpperCase().trim());
			
			if (CommonFunction.checkNull(vo.getComments()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getComments().toUpperCase().trim());
			
			if (CommonFunction.checkNull(vo.getUserId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getUserId().toUpperCase().trim());
			
			if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerDate().toUpperCase().trim());
			
			 insertPrepStmtObject.setSql(bufInsSql.toString());
			  logger.info("Query For UPDATE AUTHOR DECISSION is ::::>>"+insertPrepStmtObject.printQuery());
			  insertlist.add(insertPrepStmtObject);
			 
			  insertPrepStmtObject=null;                   
			  bufInsSql=null;	                    
			
			if(CommonFunction.checkNull(vo.getAuthorDecission()).equalsIgnoreCase("A"))
			{
			bufInsSql = new StringBuilder();
			insertPrepStmtObject = new PrepStmtObject();
			
					
			bufInsSql.append(" MERGE INTO CR_ASSET_COLLATERAL_M CACM ");
			bufInsSql.append(" USING (SELECT ASSET_ID,VEHICLE_MANUFACTURING_YEAR,VEHICLE_REGISTRATION_NO, ");
			bufInsSql.append(" VEHICLE_REGISTRATION_DATE, ");
			bufInsSql.append(" VEHICLE_CHASIS_NUMBER,VEHICLE_INSURER,  VEHICLE_INSURED_DATE,VEHICLE_OWNER,ENGINE_NUMBER,VEHICLE_IDV, ");
			bufInsSql.append(" VECHICLE_INVOICE_DATE ,INVOICE_NO, RC_RECEIVED , RC_RECEIVED_DATE, INVOICE_AMOUNT,PERMIT_RECEIVED , PERMIT_RECEIVED_DATE  ");
			bufInsSql.append(" FROM cr_loan_update_asset_dtl WHERE LOAN_ID='"+vo.getLbxLoanId()+"' AND ASSET_ID = '"+vo.getAssetId()+"') A  ");
			bufInsSql.append(" ON (CACM.ASSET_ID = A.ASSET_ID AND CACM.asset_collateral_class = 'VEHICLE') WHEN MATCHED ");		
			bufInsSql.append(" THEN	UPDATE ");
			bufInsSql.append(" SET CACM.VEHICLE_MANUFACTURING_YEAR = A.VEHICLE_MANUFACTURING_YEAR,CACM.VEHICLE_REGISTRATION_NO = A.VEHICLE_REGISTRATION_NO, ");
			bufInsSql.append(" CACM.VEHICLE_REGISTRATION_DATE=A.VEHICLE_REGISTRATION_DATE,CACM.VEHICLE_CHASIS_NUMBER=A.VEHICLE_CHASIS_NUMBER, ");
			bufInsSql.append(" CACM.VEHICLE_INSURER=A.VEHICLE_INSURER, CACM.VEHICLE_INSURED_DATE=A.VEHICLE_INSURED_DATE,CACM.VEHICLE_OWNER=A.VEHICLE_OWNER, ");
			bufInsSql.append(" CACM.ENGINE_NUMBER=A.ENGINE_NUMBER,CACM.VEHICLE_IDV=A.VEHICLE_IDV,CACM.VECHICLE_INVOICE_DATE=A.VECHICLE_INVOICE_DATE,CACM.INVOICE_NO=A.INVOICE_NO, ");
			bufInsSql.append(" CACM.RC_RECEIVED=A.RC_RECEIVED,CACM.RC_RECEIVED_DATE=A.RC_RECEIVED_DATE, CACM.INVOICE_AMOUNT=A.INVOICE_AMOUNT,CACM.PERMIT_RECEIVED=A.PERMIT_RECEIVED, ");
			bufInsSql.append(" CACM.PERMIT_RECEIVED_DATE=A.PERMIT_RECEIVED_DATE ; ");
			
			
					insertPrepStmtObject.setSql(bufInsSql.toString());
					bufInsSql=null;
					logger.info("Query For UPDATE CR_ASSET_COLLATERAL_M ::::>>"+insertPrepStmtObject.printQuery());
					insertlist.add(insertPrepStmtObject);
					insertPrepStmtObject=null;

			}
			 status = ConnectionDAO.sqlInsUpdDeletePrepStmt(insertlist);
			 if(status)
			 {
				 if(CommonFunction.checkNull(vo.getAuthorDecission()).equalsIgnoreCase("A") || CommonFunction.checkNull(vo.getAuthorDecission()).equalsIgnoreCase("X"))
				 {
					 logger.info("Deleted Pdd in case of Approval and Rejection. ");
					    boolean deleteSt = false;
					    ArrayList list=new ArrayList();
						StringBuilder query=new StringBuilder();
						try{
							query.append ("delete from cr_loan_update_asset_dtl where loan_id='"+vo.getLbxLoanId()+"' and asset_id='"+vo.getAssetId()+"' ");
							list.add(query.toString());
							logger.info("deletePddAsset query------------------" + query.toString());
							deleteSt =ConnectionDAO.sqlInsUpdDelete(list);
							logger.info("Delete Pdd status : " + deleteSt);
							
						}
						catch (Exception e) {
							e.printStackTrace();
						}
						finally{
						  query=null;
						  list.clear();
						  list=null;
					  }
				 }
			 }
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		finally{
			insertlist.clear();
			insertlist = null;
			vo=null;
			
		         }
		return status;
	}

	

	@Override
	public ArrayList<UpdateAssetVO> getUpdateAssetVehical(Object obj) {
		UpdateAssetVO vo = (UpdateAssetVO) obj;
		ArrayList<UpdateAssetVO> updateVehicleList = new ArrayList<UpdateAssetVO>();
		logger.info("In getUpdateAssetVehical()--->>>: ");
		ArrayList mainlist=new ArrayList();
		ArrayList subList=new ArrayList();
		boolean status = false;
		
		String lbxLoanId=vo.getLbxLoanId();
		String lbxAssetId=vo.getLbxAssetId();
		
			
		try{
	            
			logger.info("In getUpdateAssetVehical().....................................Dao Impl");
	        StringBuffer bufInsSql =    new StringBuffer();
	        
	        bufInsSql.append(" select  CLD.LOAN_ID,IIF (CACM.ASSET_NEW_OLD='N','New','Old') as ASSET_NEW_OLD,CACM.ASSET_COLLATERAL_DESC, ");
	        bufInsSql.append(" CACM.VEHICLE_MAKE ,CACM.VEHICLE_MODEL,CACM.VEHICLE_COLLATERAL_COST,CACM.ASSET_SUPPLIER_DESC,CACM.ASSET_MANUFATURER_DESC,CACM.LOAN_AMOUNT,CACM.DEFAULT_LTV,CACM.VEHICLE_DISCOUNT, ");
	        bufInsSql.append("  CACM.vehicle_grid_value,CACM.vehicle_valuation_amount,CACM.VEHICLE_ASSET_USES_TYPE,CACM.COLLATERAL_SECURITY_MARGIN,CSM.state_desc, ");
	        bufInsSql.append(" CACM.VEHICLE_VALUATION_AMOUNT,CACM.VEHICLE_VALUE,CACM.asset_standard,CACM.asset_collateral_value,GM.DESCRIPTION ,CACM.asset_id, ");
	        bufInsSql.append(" CACM.VEHICLE_MANUFACTURING_YEAR,CACM.VEHICLE_REGISTRATION_NO,"+dbo+"DATE_FORMAT(CACM.VEHICLE_REGISTRATION_DATE,'"+dateFormat+"'),CACM.VEHICLE_CHASIS_NUMBER,CACM.VEHICLE_INSURER," );
	        bufInsSql.append(" "+dbo+"DATE_FORMAT(CACM.VEHICLE_INSURED_DATE,'"+dateFormat+"'),CACM.VEHICLE_OWNER,CACM.ENGINE_NUMBER,CACM.VEHICLE_IDV,"+dbo+"DATE_FORMAT(CACM.VECHICLE_INVOICE_DATE,'"+dateFormat+"'),CACM.INVOICE_NO,CACM.RC_RECEIVED," );
	        bufInsSql.append(" "+dbo+"DATE_FORMAT(CACM.RC_RECEIVED_DATE,'"+dateFormat+"'),CACM.INVOICE_AMOUNT,CACM.PERMIT_RECEIVED,"+dbo+"DATE_FORMAT(CACM.PERMIT_RECEIVED_DATE,'"+dateFormat+"'),t.INVOICE_UPDATE_FLAG,t.RC_UPDATE_FLAG,t.INSURANCE_UPDATE_FLAG " );
	        bufInsSql.append(" from CR_LOAN_COLLATERAL_M CLCM  ");
	        bufInsSql.append("  JOIN cr_asset_collateral_m CACM ON CLCM.ASSETID=CACM.ASSET_ID ");
	        bufInsSql.append("  join generic_master GM on GM.VALUE=CACM.SECURITY  and gm.GENERIC_KEY='SECURITY_TYPE' ");
	        bufInsSql.append("  join cr_loan_dtl CLD on CLD.loan_id=CLCM.loan_id  ");
	        bufInsSql.append("  join com_state_m CSM on  CSM.state_id= CACM.STATE ");
	        bufInsSql.append("  left join cr_loan_update_asset_dtl t on t.loan_id=CLD.LOAN_ID and t.ASSET_ID=CLCM.ASSETID ");
	        bufInsSql.append(" WHERE CLD.LOAN_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(lbxLoanId).trim())+"' ");
	   	    bufInsSql.append(" AND CLCM.ASSETID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(lbxAssetId).trim())+"' ");
	       	logger.info("SELECT Query----->>"+bufInsSql.toString());
	       	mainlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
	       	logger.info("SIZE Query----->>"+mainlist.size());
	       //	UpdateAssetVO updateAssetVO=new UpdateAssetVO();
	       	for(int i=0;i<mainlist.size();i++){

				subList=(ArrayList)mainlist.get(i);
				if(subList.size()>0){
					
					
					vo.setLbxLoanId(CommonFunction.checkNull(subList.get(0))); 
					vo.setAssetNature(CommonFunction.checkNull(subList.get(1)));
					vo.setAssetsCollateralDesc(CommonFunction.checkNull(subList.get(2)));
					vo.setVehicleMake(CommonFunction.checkNull(subList.get(3)));
					vo.setVehicleModel(CommonFunction.checkNull(subList.get(4)));
					vo.setVehicleCost(CommonFunction.checkNull(subList.get(5)));
					vo.setMachineSupplier(CommonFunction.checkNull(subList.get(6)));
					vo.setAssetManufact(CommonFunction.checkNull(subList.get(7)));
					vo.setLoanAmount(CommonFunction.checkNull(subList.get(8)));
					vo.setCollateralSecurityMarginDF(CommonFunction.checkNull(subList.get(9)));
					vo.setVehicleDiscount(CommonFunction.checkNull(subList.get(10)));
					vo.setGridValue(CommonFunction.checkNull(subList.get(11)));
					vo.setValuationCost(CommonFunction.checkNull(subList.get(12)));
					vo.setUsageType(CommonFunction.checkNull(subList.get(13)));
					vo.setCollateralSecurityMargin(CommonFunction.checkNull(subList.get(14)));
					vo.setTxtStateCode(CommonFunction.checkNull(subList.get(15)));
					vo.setValuationCost(CommonFunction.checkNull(subList.get(16)));
					vo.setVehicleCost(CommonFunction.checkNull(subList.get(17)));
					vo.setAssetStandard(CommonFunction.checkNull(subList.get(18)));
					vo.setAssetsCollateralValue(CommonFunction.checkNull(subList.get(19)));
					vo.setSecurityTypes(CommonFunction.checkNull(subList.get(20)));
					vo.setAssetId(CommonFunction.checkNull(subList.get(21)));
					
					vo.setVehicleYearOfManufact(CommonFunction.checkNull(subList.get(22)));
					vo.setVehicleRegNo(CommonFunction.checkNull(subList.get(23)));
					vo.setVehicleRegDate(CommonFunction.checkNull(subList.get(24)));
					vo.setVehicleChesisNo(CommonFunction.checkNull(subList.get(25)));
					vo.setVehicleInsurer(CommonFunction.checkNull(subList.get(26)));
					vo.setVehicleInsureDate(CommonFunction.checkNull(subList.get(27)));
					vo.setVehicleOwner(CommonFunction.checkNull(subList.get(28)));
					vo.setEngineNumber(CommonFunction.checkNull(subList.get(29)));
					vo.setIdv(CommonFunction.checkNull(subList.get(30)));
					vo.setVehicleInvoiceDate(CommonFunction.checkNull(subList.get(31)));
					vo.setInvoiceNumber(CommonFunction.checkNull(subList.get(32)));
				
					vo.setRcReceived(CommonFunction.checkNull(subList.get(33)));
					vo.setRcReceivedDate(CommonFunction.checkNull(subList.get(34)));
					vo.setInvoiceAmount(CommonFunction.checkNull(subList.get(35)));
					vo.setPermitReceived(CommonFunction.checkNull(subList.get(36)));
					vo.setPermitReceivedDate(CommonFunction.checkNull(subList.get(37)));
					
					vo.setInvoiceUpdateCheckBox(CommonFunction.checkNull(subList.get(38)));
					vo.setRcUpdateCheckBox(CommonFunction.checkNull(subList.get(39)));
					vo.setInsuranceUpdateCheckBox(CommonFunction.checkNull(subList.get(40)));
					
					updateVehicleList.add(vo);
			
				}
				
		  }		
		} catch(Exception e){
		  e.printStackTrace();
		}
		finally
		{
			mainlist.clear();
			mainlist=null;
			subList.clear();
			subList=null;
			vo=null;
		}
		return updateVehicleList;
		
	}

	//richa changes here 
	public String checkChesisNoVehicle(UpdateAssetVO updateAssetVO) {
		String list=null;


			logger.info("In checkChesisNoVehicle..........................DAOImpl"+updateAssetVO.getAssetId());
		StringBuilder query=new StringBuilder();
			try
			{
			if(!CommonFunction.checkNull(updateAssetVO.getAssetId()).equalsIgnoreCase(""))
			{
			 query.append("SELECT COUNT(1) FROM cr_loan_update_asset_dtl  where asset_id!='"+updateAssetVO.getAssetId()+"' and VEHICLE_CHASIS_NUMBER='"+CommonFunction.checkNull(updateAssetVO.getVehicleChesisNo()).trim()+"'");
			}
			else
			{
				 query.append("SELECT COUNT(1) FROM cr_loan_update_asset_dtl where  VEHICLE_CHASIS_NUMBER='"+CommonFunction.checkNull(updateAssetVO.getVehicleChesisNo()).trim()+"'");
			}
			
			logger.info("In getApprovalRecommend...............query...........DAOImpl"+query);
			UpdateAssetVO noteVO=null;
			 list = ConnectionDAO.singleReturn(query.toString()); 
			}
			catch(Exception e){
				e.printStackTrace();
			}
			finally{
				query=null;
			}
			
		return list;
	}

	public String checkEnginNoVehicle(UpdateAssetVO updateAssetVO) {
		String list=null;
		
		
		logger.info("In checkEnginNoVehicle..........................DAOImpl"+updateAssetVO.getAssetId());
		StringBuilder query=new StringBuilder();
		try
		{
		if(!CommonFunction.checkNull(updateAssetVO.getAssetId()).equalsIgnoreCase(""))
		{
		 query.append("SELECT COUNT(1) FROM cr_loan_update_asset_dtl  where asset_id!='"+updateAssetVO.getAssetId()+"' and ENGINE_NUMBER='"+CommonFunction.checkNull(updateAssetVO.getEngineNumber()).trim()+"'");
		}
		else
		{
			 query.append("SELECT COUNT(1) FROM cr_loan_update_asset_dtl where  ENGINE_NUMBER='"+CommonFunction.checkNull(updateAssetVO.getEngineNumber()).trim()+"'");
		}
		
		logger.info("In getApprovalRecommend...............query...........DAOImpl"+query);
		UpdateAssetVO noteVO=null;
		 list = ConnectionDAO.singleReturn(query.toString()); 
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			query=null;
		}
		
	return list;
	}

	public String checkRegNoVehicle(UpdateAssetVO updateAssetVO) {
		String list=null;
		
		
		logger.info("In checkRegNoVehicle..........................DAOImpl"+updateAssetVO.getAssetId());
	StringBuilder query=new StringBuilder();
		try
		{
		if(!CommonFunction.checkNull(updateAssetVO.getAssetId()).equalsIgnoreCase(""))
		{
		 query.append("SELECT COUNT(1) FROM cr_loan_update_asset_dtl  where asset_id!='"+updateAssetVO.getAssetId()+"' and VEHICLE_REGISTRATION_NO='"+CommonFunction.checkNull(updateAssetVO.getVehicleRegNo()).trim()+"'");
		}
		else
		{
			 query.append("SELECT COUNT(1) FROM cr_loan_update_asset_dtl where  VEHICLE_REGISTRATION_NO='"+CommonFunction.checkNull(updateAssetVO.getVehicleRegNo()).trim()+"'");
		}
		
		logger.info("In getApprovalRecommend...............query...........DAOImpl"+query);
		UpdateAssetVO noteVO=null;
		 list = ConnectionDAO.singleReturn(query.toString()); 
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			query=null;
		}
		
	return list;
	}
	//Nishant space starts
	public ArrayList<UpdateAssetVO> fetchPDDUpdateData(Object obj) {
		UpdateAssetVO vo = (UpdateAssetVO) obj;
		ArrayList<UpdateAssetVO> pddUpdateList = new ArrayList<UpdateAssetVO>();
		logger.info("In fetchPDDUpdateData()--->>>: ");
		ArrayList mainlist=new ArrayList();
		ArrayList subList=new ArrayList();
		boolean status = false;
		
		String lbxLoanId=vo.getLbxLoanId();
		String lbxAssetId=vo.getLbxAssetId();
		String tableName = null;
			
		try{
	            
			logger.info("In fetchPDDUpdateData().....................................Dao Impl");
			if("N".equals(vo.getUpdateFlag()))
				tableName="CR_ASSET_COLLATERAL_M";
			else
				tableName="CR_LOAN_UPDATE_ASSET_DTL";
			
	        StringBuffer bufInsSql =    new StringBuffer();
	        bufInsSql.append(" SELECT INVOICE_NO,dbo.DATE_FORMAT(VECHICLE_INVOICE_DATE,'%d-%m-%Y') AS VECHICLE_INVOICE_DATE,INVOICE_AMOUNT,VEHICLE_CHASIS_NUMBER,ENGINE_NUMBER, ");
	        bufInsSql.append(" RC_RECEIVED,dbo.DATE_FORMAT(RC_RECEIVED_DATE,'%d-%m-%Y') AS RC_RECEIVED_DATE,VEHICLE_REGISTRATION_NO,dbo.DATE_FORMAT(VEHICLE_REGISTRATION_DATE,'%d-%m-%Y') AS VEHICLE_REGISTRATION_DATE,VEHICLE_MANUFACTURING_YEAR,VEHICLE_OWNER,PERMIT_RECEIVED,dbo.DATE_FORMAT(PERMIT_RECEIVED_DATE,'%d-%m-%Y') AS PERMIT_RECEIVED_DATE, ");
	        bufInsSql.append(" VEHICLE_INSURER,dbo.DATE_FORMAT(VEHICLE_INSURED_DATE,'%d-%m-%Y') AS VEHICLE_INSURED_DATE,VEHICLE_IDV ");
	        bufInsSql.append(" FROM "+tableName+" ");
	        bufInsSql.append(" WHERE ASSET_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(lbxAssetId).trim())+"' ");

	       	logger.info("SELECT Query----->>"+bufInsSql.toString());
	       	mainlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
	       	logger.info("SIZE Query----->>"+mainlist.size());
	       //	UpdateAssetVO updateAssetVO=new UpdateAssetVO();
	       	for(int i=0;i<mainlist.size();i++){

				subList=(ArrayList)mainlist.get(i);
				if(subList.size()>0){
					
					
					vo.setInvoiceNumber(CommonFunction.checkNull(subList.get(0))); 			//INVOICE_NO
					vo.setVehicleInvoiceDate(CommonFunction.checkNull(subList.get(1)));		//VECHICLE_INVOICE_DATE
					vo.setInvoiceAmount(CommonFunction.checkNull(subList.get(2)));			//INVOICE_AMOUNT
					vo.setVehicleChesisNo(CommonFunction.checkNull(subList.get(3)));		//VEHICLE_CHASIS_NUMBER
					vo.setEngineNumber(CommonFunction.checkNull(subList.get(4)));			//ENGINE_NUMBER
					vo.setRcReceived(CommonFunction.checkNull(subList.get(5)));				//RC_RECEIVED
					vo.setRcReceivedDate(CommonFunction.checkNull(subList.get(6)));			//RC_RECEIVED_DATE
					vo.setVehicleRegNo(CommonFunction.checkNull(subList.get(7)));			//VEHICLE_REGISTRATION_NO
					vo.setVehicleRegDate(CommonFunction.checkNull(subList.get(8)));			//VEHICLE_REGISTRATION_DATE
					vo.setVehicleYearOfManufact(CommonFunction.checkNull(subList.get(9)));  //VEHICLE_MANUFACTURING_YEAR
					vo.setVehicleOwner(CommonFunction.checkNull(subList.get(10)));			//VEHICLE_OWNER
					vo.setPermitReceived(CommonFunction.checkNull(subList.get(11)));		//PERMIT_RECEIVED
					vo.setPermitReceivedDate(CommonFunction.checkNull(subList.get(12)));	//PERMIT_RECEIVED_DATE
					vo.setVehicleInsurer(CommonFunction.checkNull(subList.get(13)));		//VEHICLE_INSURER
					vo.setVehicleInsureDate(CommonFunction.checkNull(subList.get(14)));		//VEHICLE_INSURED_DATE
					vo.setIdv(CommonFunction.checkNull(subList.get(15)));					//VEHICLE_IDV
					//vo.setUpdateFlag(CommonFunction.checkNull(vo.getUpdateFlag()));					
					pddUpdateList.add(vo);
			
				}
				
		  }		
		} catch(Exception e){
		  e.printStackTrace();
		}
		finally
		{
			mainlist.clear();
			mainlist=null;
			subList.clear();
			subList=null;
			vo=null;
		}
		return pddUpdateList;
		
	}
	public boolean deletePddAsset(String loanId,String assetId) {
		
		logger.info("In deletePddAsset(String loanId,String assetId) ");
		boolean status=false;
		ArrayList list=new ArrayList();
		StringBuilder query=new StringBuilder();
		try{
			query.append ("delete from cr_loan_update_asset_dtl where loan_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+"' and asset_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(assetId).trim())+"' ");
			list.add(query.toString());
			logger.info("deletePddAsset query------------------" + query.toString());
			status =ConnectionDAO.sqlInsUpdDelete(list);
			
		}
	  catch (Exception e) {
			e.printStackTrace();
		}
	  finally{
		  loanId=null;
		  assetId=null;
		  query=null;
		  list.clear();
		  list=null;
	  }
	  return status;

	}
	//Nishant space ends
	public  ArrayList<UpdateAssetVO> getUpdatedVehical(String paramString)
	{
		return null;
	}
	}
