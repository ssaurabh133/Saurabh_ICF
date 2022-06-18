package com.cm.daoImplMYSQL;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.cm.dao.RepoBillingApprovalMakerDAO;
import com.cm.vo.RepoBillingApprovalMakerVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;

public class RepoBillingApprovalMakerDAOImpl implements RepoBillingApprovalMakerDAO {

	private static final Logger logger = Logger.getLogger(RepoBillingApprovalMakerDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	
	public ArrayList<RepoBillingApprovalMakerVO> searchRepoBillingApprovalMaker(
			RepoBillingApprovalMakerVO vo) {
		
		logger.info("In searchRepoBillingApprovalMaker method of RepoBillingApprovalMakerDAOImpl");
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
	              
	            bufInsSql.append("SELECT CLRAD.LOAN_ID,CLD.LOAN_NO,GCM.CUSTOMER_NAME,CLD.LOAN_LOAN_AMOUNT,CLD.LOAN_BALANCE_PRINCIPAL FROM CR_LOAN_REPO_APPROVAL_DTL CLRAD JOIN CR_LOAN_DTL CLD ON CLD.LOAN_ID=CLRAD.LOAN_ID JOIN GCD_CUSTOMER_M GCM ON GCM.CUSTOMER_ID=CLD.LOAN_CUSTOMER_ID WHERE CLRAD.REC_STATUS='P'and CLRAD.maker_id='"+vo.getMakerId()+"'");
	            bufInsSqlTempCount.append("SELECT COUNT(1) FROM CR_LOAN_REPO_APPROVAL_DTL CLRAD JOIN CR_LOAN_DTL CLD ON CLD.LOAN_ID=CLRAD.LOAN_ID JOIN GCD_CUSTOMER_M GCM ON GCM.CUSTOMER_ID=CLD.LOAN_CUSTOMER_ID WHERE CLRAD.REC_STATUS='P'and CLRAD.maker_id='"+vo.getMakerId()+"' ");
				 
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
	        	  repoBillingApprovalMakerVO.setLoanNo("<a href=repoBillingApprovalMakerDispatch.do?method=openEditRepoBillingMaker&loanId="
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

	public boolean saveRepoBillingApprovalDetails(Object ob) {
		RepoBillingApprovalMakerVO vo = (RepoBillingApprovalMakerVO) ob;
		boolean status = false;
		
		logger.info("In saveRepoBillingApprovalDetails.........inside ..........Dao Impl"
						+ vo.getRecStatus());
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String billingFlag = "X";
		String interestFlag = "X";
		String sd_interestFlag = "X";

		String query = "select REPO_FLAG from CR_LOAN_DTL where LOAN_ID='"+ StringEscapeUtils.escapeSql(vo.getLbxDealNo().trim()) + "'AND REPO_FLAG='A'";
		logger.info("In In saveRepoBillingApprovalDetails.........inside ..........Dao Impl"
						+ query);
		String st = ConnectionDAO.singleReturn(query);
		
		String query1 = "select count(1) from CR_LOAN_REPO_APPROVAL_DTL where LOAN_ID='"+ StringEscapeUtils.escapeSql(vo.getLbxDealNo().trim()) + "'AND REC_STATUS='A'";
		logger.info("In In saveRepoBillingApprovalDetails.........inside ..........Dao Impl"
						+ query1);
		String str = ConnectionDAO.singleReturn(query1);
		try {

			if (CommonFunction.checkNull(st).equalsIgnoreCase("A"))
			{
				if(CommonFunction.checkNull(str).equalsIgnoreCase("0"))
				{
					if (vo.getBillingStopped() != null
						&& vo.getBillingStopped().equals("on")) {
					billingFlag = "A";
				} else {
					billingFlag = "X";

				}
				if (vo.getInterestStopped() != null
						&& vo.getInterestStopped().equals("on")) {
					interestFlag = "A";
				} else {
					interestFlag = "X";

				}
				
				if (vo.getSDInterest() != null
						&& vo.getSDInterest().equals("on")) {
					sd_interestFlag = "A";
				} else {
					sd_interestFlag = "X";

				}
				logger.info("In insert Case Type master");
				StringBuilder bufInsSql = new StringBuilder();
				bufInsSql.append("insert into CR_LOAN_REPO_APPROVAL_DTL(LOAN_ID,REPO_BILLING_FLAG,REPO_INTEREST_FLAG,SD_INTEREST_FLAG,REC_STATUS,MAKER_ID,MAKER_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,"); //LOAN_ID
				bufInsSql.append(" ?,"); //REPO_BILLING_FLAG
				bufInsSql.append(" ?,"); //REPO_INTEREST_FLAG
				bufInsSql.append(" ?,"); //SD_INTEREST_FLAG
				bufInsSql.append(" ?,"); //REC_STATUS
				bufInsSql.append(" ?,"); //MAKER_ID
				bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND)) ");
			
			
				if (CommonFunction.checkNull(vo.getLbxDealNo()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxDealNo().toUpperCase().trim());
				
				if (CommonFunction.checkNull(billingFlag).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(billingFlag);
				
				if (CommonFunction.checkNull(interestFlag).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(interestFlag);
				
				if (CommonFunction.checkNull(sd_interestFlag).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(sd_interestFlag);
				

				if (CommonFunction.checkNull("P").equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString("P");
				

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
				
			}
			else
			{

				if (vo.getBillingStopped() != null
							&& vo.getBillingStopped().equals("on")) {
						billingFlag = "A";
					} else {
						billingFlag = "X";

					}
					if (vo.getInterestStopped() != null
							&& vo.getInterestStopped().equals("on")) {
						interestFlag = "A";
					} else {
						interestFlag = "X";

					}
					if (vo.getSDInterest() != null
							&& vo.getSDInterest().equals("on")) {
						sd_interestFlag = "A";
					} else {
						sd_interestFlag = "X";

					}

					logger.info("In insert Case Type master");
					StringBuilder bufInsSql = new StringBuilder();
					bufInsSql.append("UPDATE CR_LOAN_REPO_APPROVAL_DTL SET REPO_BILLING_FLAG=?,REPO_INTEREST_FLAG=?,SD_INTEREST_FLAG=?,REC_STATUS=?,MAKER_ID=?,MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) WHERE LOAN_ID='"+vo.getLbxDealNo() +"'");
			
					if (CommonFunction.checkNull(billingFlag).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(billingFlag);
					
					if (CommonFunction.checkNull(interestFlag).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(interestFlag);
					
					if (CommonFunction.checkNull(sd_interestFlag).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(sd_interestFlag);
					

					if (CommonFunction.checkNull("P").equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString("P");
					

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
	
public boolean forwardRepoBillingApprovalMarkingDetails(Object ob) {
	RepoBillingApprovalMakerVO vo = (RepoBillingApprovalMakerVO) ob;
	boolean status = false;
	
	logger.info("In saveRepoBillingApprovalDetails.........inside ..........Dao Impl"
					+ vo.getRecStatus());
	ArrayList qryList = new ArrayList();
	PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
	String billingFlag = "X";
	String interestFlag = "X";

	String query = "select REPO_FLAG from CR_LOAN_DTL where LOAN_ID='"+ StringEscapeUtils.escapeSql(vo.getLbxDealNo().trim()) + "'AND REPO_FLAG='A'";
	logger.info("In In saveRepoBillingApprovalDetails.........inside ..........Dao Impl"
					+ query);
	String st = ConnectionDAO.singleReturn(query);
	try {

		if (CommonFunction.checkNull(st).equalsIgnoreCase("A")) {
		if (vo.getBillingStopped() != null
					&& vo.getBillingStopped().equals("on")) {
				billingFlag = "A";
			} else {
				billingFlag = "X";

			}
			if (vo.getInterestStopped() != null
					&& vo.getInterestStopped().equals("on")) {
				interestFlag = "A";
			} else {
				interestFlag = "X";

			}


			logger.info("In insert Case Type master");
			StringBuilder bufInsSql = new StringBuilder();
			bufInsSql.append("UPDATE CR_LOAN_REPO_APPROVAL_DTL SET REC_STATUS=?,MAKER_ID=?,MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) WHERE LOAN_ID='"+ vo.getLbxDealNo()+"' ");
			
	
			if (CommonFunction.checkNull("F").equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString("F");
			

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

public ArrayList<RepoBillingApprovalMakerVO> openEditRepoBillingMaker(RepoBillingApprovalMakerVO vo) {

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

public boolean updateRepoBillingApprovalDetails(Object ob) {
	RepoBillingApprovalMakerVO vo = (RepoBillingApprovalMakerVO) ob;
	boolean status = false;
	
	logger.info("In saveRepoBillingApprovalDetails.........inside ..........Dao Impl"
					+ vo.getRecStatus());
	ArrayList qryList = new ArrayList();
	ArrayList updatelist = new ArrayList();
	PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
	String billingFlag = "X";
	String interestFlag = "X";
	String sd_interestFlag="X";

	String query = "select REPO_FLAG from CR_LOAN_DTL where LOAN_ID='"+ StringEscapeUtils.escapeSql(vo.getLbxDealNo().trim()) + "'AND REPO_FLAG='A'";
	logger.info("In In saveRepoBillingApprovalDetails.........inside ..........Dao Impl"
					+ query);
	String st = ConnectionDAO.singleReturn(query);
	try {

		if (CommonFunction.checkNull(st).equalsIgnoreCase("A")) {
		if (vo.getBillingStopped() != null
					&& vo.getBillingStopped().equals("on")) {
				billingFlag = "A";
			} else {
				billingFlag = "X";

			}
			if (vo.getInterestStopped() != null
					&& vo.getInterestStopped().equals("on")) {
				interestFlag = "A";
			} else {
				interestFlag = "X";

			}
			if (vo.getSDInterest() != null
					&& vo.getSDInterest().equals("on")) {
				sd_interestFlag = "A";
			} else {
				sd_interestFlag = "X";

			}

			logger.info("In insert Case Type master");
			StringBuilder bufInsSql = new StringBuilder();
			bufInsSql.append("UPDATE CR_LOAN_REPO_APPROVAL_DTL SET REPO_BILLING_FLAG=?,REPO_INTEREST_FLAG=?,SD_INTEREST_FLAG=?,REC_STATUS=?,MAKER_ID=?,MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) WHERE LOAN_ID='"+vo.getLbxDealNo() +"'");
	
			if (CommonFunction.checkNull(billingFlag).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(billingFlag);
			
			if (CommonFunction.checkNull(interestFlag).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(interestFlag);
			
			if (CommonFunction.checkNull(sd_interestFlag).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(sd_interestFlag);
			

			if (CommonFunction.checkNull("P").equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString("P");
			

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
	
	}
	}catch (Exception e) {
		e.printStackTrace();
	}
	finally{
		qryList.clear();
		qryList = null;
		
	         }
	return status;

}

public String deleteRepoBillingApproval(String repoBillingApprovalLoanId) {
	
	boolean status=false;
	boolean status1=false;
	String returnValue=null;
	logger.info("deleteCaseMarking");
	ArrayList updatelist=new ArrayList();
	
	try {
		StringBuilder query=new StringBuilder();
		query.append(" delete from CR_LOAN_REPO_APPROVAL_DTL where LOAN_ID='"+CommonFunction.checkNull(repoBillingApprovalLoanId)+"'");
		logger.info(" delete query: "+query.toString());
		ArrayList list=new ArrayList();
		list.add(query);
		status=ConnectionDAO.sqlInsUpdDelete(list);
		if(status)
		{
			PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
			String billingFlag = "X";
			String interestFlag = "X";
			String sd_interestFlag="X";
			query = new StringBuilder();
			
			query.append(" UPDATE CR_LOAN_DTL SET REPO_BILLING_FLAG=?,REPO_INTEREST_FLAG=?,SD_INTEREST_FLAG=? where LOAN_ID=? ");
			
			if((CommonFunction.checkNull(billingFlag).trim().equalsIgnoreCase("")))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((CommonFunction.checkNull(billingFlag).trim()));
		
			if((CommonFunction.checkNull(interestFlag).trim().equalsIgnoreCase("")))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((CommonFunction.checkNull(interestFlag).trim()));
			
			if((CommonFunction.checkNull(sd_interestFlag).trim().equalsIgnoreCase("")))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((CommonFunction.checkNull(sd_interestFlag).trim()));

			if (CommonFunction.checkNull(repoBillingApprovalLoanId).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(repoBillingApprovalLoanId);
			insertPrepStmtObject.setSql(query.toString());
			
			logger.info("IN updateCaseMarkingMaker() insert query1 ### "+ insertPrepStmtObject.printQuery());
		
			updatelist.add(insertPrepStmtObject);
			
			try {
				status1 = ConnectionDAO.sqlInsUpdDeletePrepStmt(updatelist);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	if(status)
    {
    	returnValue="Y";
    }
    else
    {
    	returnValue="N";
    }
	// TODO Auto-generated method stub
	return returnValue;
}


public ArrayList<RepoBillingApprovalMakerVO> getRepoBillingApprovalValues(Object obj) {
	RepoBillingApprovalMakerVO vo = (RepoBillingApprovalMakerVO) obj;
	ArrayList<RepoBillingApprovalMakerVO> updateVehicleList = new ArrayList<RepoBillingApprovalMakerVO>();
	logger.info("In getUpdateAssetVehical()--->>>: ");
	ArrayList mainlist=new ArrayList();
	ArrayList subList=new ArrayList();
	boolean status = false;
	
	String lbxLoanId=vo.getLbxDealNo();
	
	try{
            
		logger.info("In getRepoBillingApprovalValues().....................................Dao Impl");
        StringBuffer bufInsSql =    new StringBuffer();
        
        bufInsSql.append("select DISTINCT cld.loan_id,CLD.loan_no,GCM.customer_name,CLD.repo_billing_flag,CLD.repo_interest_flag,CLD.sd_interest_flag from CR_LOAN_DTL CLD   LEFT JOIN GCD_CUSTOMER_M GCM ON GCM.CUSTOMER_ID=CLD.LOAN_CUSTOMER_ID WHERE CLD.LOAN_ID='"+StringEscapeUtils.escapeSql(vo.getLbxDealNo())+"' ");
       logger.info("SELECT Query----->>"+bufInsSql.toString());
       	mainlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
      // 	logger.info("SIZE Query----->>"+mainlist.size());
       //	UpdateAssetVO updateAssetVO=new UpdateAssetVO();
       	for(int i=0;i<mainlist.size();i++){

			subList=(ArrayList)mainlist.get(i);
			if(subList.size()>0){
				
				
				vo.setLbxDealNo(CommonFunction.checkNull(subList.get(0)).toString());
				vo.setSearchLoanNo(CommonFunction.checkNull(subList.get(1)).toString());
				vo.setSearchCustomerName(CommonFunction.checkNull(subList.get(2)).toString());
				vo.setBillingStopped(CommonFunction.checkNull(subList.get(3)).toString());
				vo.setInterestStopped(CommonFunction.checkNull(subList.get(4)).toString());
				vo.setSDInterest(CommonFunction.checkNull(subList.get(5)).toString());
				
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

	}