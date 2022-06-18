package com.cm.daoImplMSSQL;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import com.cm.dao.RepoBillingApprovalAuthorDAO;
import com.cm.vo.RepoBillingApprovalMakerVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;

public class MSSQLRepoBillingApprovalAuthorDAOImpl implements RepoBillingApprovalAuthorDAO {

	private static final Logger logger = Logger.getLogger(MSSQLRepoBillingApprovalAuthorDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	String dbType=resource.getString("lbl.dbType");
	String dbo=resource.getString("lbl.dbPrefix");
	
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	
	public ArrayList<RepoBillingApprovalMakerVO> searchRepoBillingApprovalAuthor(
			RepoBillingApprovalMakerVO vo) {
		
		logger.info("In searchRepoBillingApprovalMaker method of searchRepoBillingApprovalAuthor");
		StringBuilder userName=new StringBuilder();
		
		int count=0;
		ArrayList searchlist=null;
		ArrayList<RepoBillingApprovalMakerVO> detailList=new ArrayList<RepoBillingApprovalMakerVO>();
		StringBuilder bufInsSql =null;
        StringBuilder bufInsSqlTempCount = null;
        ArrayList data=null;
        RepoBillingApprovalMakerVO repoBillingApprovalMakerVO=new RepoBillingApprovalMakerVO();;
		logger.info("here loanid++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+vo.getLoanId());
		try{
			/*userNameQ="select LOAN_ID from CR_LOAN_DTL where REC_STATUS='A' and LOAN_ID='"+vo.getLoanId()+"'";
			userName.append(ConnectionDAO.singleReturn(userNameQ));
			logger.info("userNameQ: "+userNameQ.toString()+" userName: "+userName.toString());
			userNameQ=null;	*/		
	              
				bufInsSql =	new StringBuilder();
				bufInsSqlTempCount = new StringBuilder();
	              
	            bufInsSql.append("SELECT CLRAD.LOAN_ID,CLD.LOAN_NO,GCM.CUSTOMER_NAME,CLD.LOAN_LOAN_AMOUNT,CLD.LOAN_BALANCE_PRINCIPAL FROM CR_LOAN_REPO_APPROVAL_DTL CLRAD JOIN CR_LOAN_DTL CLD ON CLD.LOAN_ID=CLRAD.LOAN_ID JOIN GCD_CUSTOMER_M GCM ON GCM.CUSTOMER_ID=CLD.LOAN_CUSTOMER_ID WHERE CLRAD.REC_STATUS='F' and clrad.maker_id!='"+vo.getUserId() +"'");
	            bufInsSqlTempCount.append("SELECT COUNT(1) FROM CR_LOAN_REPO_APPROVAL_DTL CLRAD JOIN CR_LOAN_DTL CLD ON CLD.LOAN_ID=CLRAD.LOAN_ID JOIN GCD_CUSTOMER_M GCM ON GCM.CUSTOMER_ID=CLD.LOAN_CUSTOMER_ID WHERE CLRAD.REC_STATUS='F' and clrad.maker_id!='"+vo.getUserId() +"'");
				 
				if(vo.getLbxDealNo()!=null)
					{
				 if(!(CommonFunction.checkNull(vo.getLbxDealNo()).equalsIgnoreCase("")))
					{
						bufInsSql.append(" and CLRAD.LOAN_ID='"+(CommonFunction.checkNull(vo.getLbxDealNo())).trim()+"'");
						bufInsSqlTempCount.append(" and CLRAD.LOAN_ID='"+(CommonFunction.checkNull(vo.getLbxDealNo())).trim()+"'"); 
					}
					
			String tmp = bufInsSql.toString();
					String tmp1 = bufInsSqlTempCount.toString();
	          logger.info("Search Query."+tmp.toString());
	          logger.info("Count Query....."+tmp1.toString());
	             
	            searchlist = ConnectionDAO.sqlSelect(tmp);
	            tmp=null;
	            count =Integer.parseInt(ConnectionDAO.singleReturn(tmp1.toString()));
	            tmp1=null;
	            
	          logger.info("searchlist SIZE: "+searchlist.size());
	          int size=searchlist.size();
	          for(int i=0;i<size;i++){
	       
	          data=(ArrayList)searchlist.get(i);
	          if(data.size()>0){
	        	  repoBillingApprovalMakerVO= new RepoBillingApprovalMakerVO();
//	        	  detailList=new ArrayList<CaseMarkingMakerVO>();
	        	  repoBillingApprovalMakerVO.setLoanId((CommonFunction.checkNull(data.get(0)).trim()));
	        	  repoBillingApprovalMakerVO.setLoanNo("<a href=repoBillingApprovalAuthorDispatch.do?method=openEditRepoBillingApprovalAuthor&loanId="
							+ CommonFunction.checkNull(data.get(0)).toString()+">"
							+ CommonFunction.checkNull(data.get(1)).toString() + "</a>");
	        	  repoBillingApprovalMakerVO.setCustomerName((CommonFunction.checkNull(data.get(2)).trim()));
	        	  repoBillingApprovalMakerVO.setLoanAmount((CommonFunction.checkNull(data.get(3)).trim()));
	        	  repoBillingApprovalMakerVO.setBalancePrincipal((CommonFunction.checkNull(data.get(4)).trim()));
	        	  repoBillingApprovalMakerVO.setTotalRecordSize(count);
	        	  detailList.add(repoBillingApprovalMakerVO);
	        }
		}
	         }}catch(Exception e){
			e.printStackTrace();
		}
	       finally
		{
	    	searchlist=null;
	   		bufInsSql =null;
	        bufInsSqlTempCount = null;
	       // data=null;
	      vo=null;
			userName=null;
		}
	       return detailList;
	}
	
	public ArrayList<RepoBillingApprovalMakerVO> openEditRepoBillingApprovalAuthor(RepoBillingApprovalMakerVO vo) {

		ArrayList searchlist = new ArrayList();
		ArrayList<RepoBillingApprovalMakerVO> caseTypeList = new ArrayList<RepoBillingApprovalMakerVO>();
		StringBuilder bufInsSql = new StringBuilder();
		RepoBillingApprovalMakerVO caseTypeDataVo = null;
		ArrayList data = null;
		logger.info("ratioid in searchBenchMarkRatioEdit &***************************** = "+vo.getLoanId());
		try {
			
	     
			
			bufInsSql.append(" select DISTINCT CLRAD.loan_id,CLD.loan_no,GCM.customer_name,CLRAD.repo_billing_flag,CLRAD.repo_interest_flag,CLRAD.sd_interest_flag from CR_LOAN_REPO_APPROVAL_DTL CLRAD LEFT JOIN CR_LOAN_DTL CLD ON CLD.LOAN_ID=CLRAD.LOAN_ID  LEFT JOIN GCD_CUSTOMER_M GCM ON GCM.CUSTOMER_ID=CLD.LOAN_CUSTOMER_ID WHERE CLRAD.LOAN_ID='"+StringEscapeUtils.escapeSql(vo.getLbxDealNo())+"'");
			logger.info("search Query...." + bufInsSql.toString());
			
			searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
			
			
			int size= searchlist.size();
			for (int i = 0; i <size; i++) 
			{
							
					data = (ArrayList) searchlist.get(i);

					caseTypeDataVo = new RepoBillingApprovalMakerVO();
					
					caseTypeDataVo.setLbxDealNo(CommonFunction.checkNull(data.get(0)).toString());
					caseTypeDataVo.setSearchLoanNo(CommonFunction.checkNull(data.get(1)).toString());
					caseTypeDataVo.setSearchCustomerName(CommonFunction.checkNull(data.get(2)).toString());
					caseTypeDataVo.setBillingStopped(CommonFunction.checkNull(data.get(3)).toString());
					caseTypeDataVo.setInterestStopped(CommonFunction.checkNull(data.get(4)).toString());
					caseTypeDataVo.setSDInterest(CommonFunction.checkNull(data.get(5)).toString());
					
					
					caseTypeList.add(caseTypeDataVo);
					
					
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			searchlist.clear();
			searchlist = null;
			bufInsSql=null;
			caseTypeDataVo=null;
			data=null;
			
		 }
		return caseTypeList;
	}	
	public boolean saveRepoBillingApprovalChecker(Object ob) {
		RepoBillingApprovalMakerVO vo = (RepoBillingApprovalMakerVO) ob;
		boolean status = false;
		ArrayList updatelist = new ArrayList();
		logger.info("In saveRepoBillingApprovalDetails.........inside ..........Dao Impl"+ vo.getRecStatus());
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
	
		String query = "select REPO_FLAG from CR_LOAN_DTL where LOAN_ID='"+ CommonFunction.checkNull(vo.getLbxDealNo()) + "'AND REPO_FLAG='A'";
		logger.info("In In saveRepoBillingApprovalDetails.........inside ..........Dao Impl"
						+ query);
		String st = ConnectionDAO.singleReturn(query);
		try {
			

			if (CommonFunction.checkNull(st).equalsIgnoreCase("A")) {
				

				logger.info("In insert Case Type master");
				StringBuilder bufInsSql = new StringBuilder();
				bufInsSql.append("UPDATE CR_LOAN_REPO_APPROVAL_DTL SET REC_STATUS=?,AUTHOR_ID=?,AUTHOR_DATE=");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) ");
				bufInsSql.append("WHERE LOAN_ID='"+ vo.getLbxDealNo()+"' ");
				
			
				if (CommonFunction.checkNull(vo.getDecision()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getDecision());
				

				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());
				
				
				if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());
				
			
				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN insertCaseTypeMaster() insert query1 ### "+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In saveCountryData......................"+ status);
				insertPrepStmtObject=null;
				bufInsSql=null;
				if(CommonFunction.checkNull(vo.getDecision()).equalsIgnoreCase("A"))
				{
				insertPrepStmtObject = new PrepStmtObject();
				bufInsSql = new StringBuilder();
				
				bufInsSql.append(" UPDATE CR_LOAN_DTL SET REPO_BILLING_FLAG=?,REPO_INTEREST_FLAG=?,SD_INTEREST_FLAG=? where LOAN_ID=? ");
				
				if((CommonFunction.checkNull(vo.getBillingStopped()).trim().equalsIgnoreCase("")))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getBillingStopped()).trim()));
			
				if((CommonFunction.checkNull(vo.getInterestStopped()).trim().equalsIgnoreCase("")))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getInterestStopped()).trim()));
				
				if((CommonFunction.checkNull(vo.getSDInterest()).trim().equalsIgnoreCase("")))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getSDInterest()).trim()));

				if (CommonFunction.checkNull(vo.getLbxDealNo()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxDealNo().toUpperCase());
				insertPrepStmtObject.setSql(bufInsSql.toString());
				
				logger.info("IN updateCaseMarkingMaker() insert query1 ### "+ insertPrepStmtObject.printQuery());
			
				updatelist.add(insertPrepStmtObject);
				
				status = ConnectionDAO.sqlInsUpdDeletePrepStmt(updatelist);
			}
			}


		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			qryList.clear();
			qryList = null;
			
		         }
		return status;

	}

	}
