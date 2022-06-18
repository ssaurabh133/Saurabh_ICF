package com.cm.daoImplMSSQL;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.cm.dao.ChangeRateDAO;
import com.cm.vo.ChangeRateVo;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;

public class MSSQLChangeRateDAOImpl implements ChangeRateDAO{
	private static final Logger logger = Logger.getLogger(MSSQLChangeRateDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	String dbType=resource.getString("lbl.dbType");
	String dbo=resource.getString("lbl.dbPrefix");
	//String selectFrom = resource.getString("lbl.selectFrom");
		public ArrayList<ChangeRateVo> nonEmiBasedMakerGrid(Object ob, String type) 
	{
			ChangeRateVo nonEmiLoanvo =  (ChangeRateVo)ob; 
	
			int count = 0;
			int startRecordIndex = 0;
			int endRecordIndex = no;
			String modifyLoanId=null;
			String customer=null;
			String rateType=null;
			
			ArrayList searchlist = new ArrayList();
			ArrayList data= new ArrayList();
			StringBuilder bufInsSql = new StringBuilder();
			StringBuilder bufInsSqlTempCount = new StringBuilder();
		
			ChangeRateVo nonEmivo= new ChangeRateVo();
			
			ArrayList<ChangeRateVo> detailList = new ArrayList<ChangeRateVo>();
			try {
				modifyLoanId= CommonFunction.checkNull(nonEmiLoanvo.getLbxLoanNo());
				customer=CommonFunction.checkNull(nonEmiLoanvo.getCustomerName());
				rateType=CommonFunction.checkNull(nonEmiLoanvo.getCurrentRateType());
				logger.info("In nonEmiBasedMakerGrid..............ModifyLoanId......................"+modifyLoanId);
				
				
				bufInsSql.append(" SELECT LOAN_ID,LOAN_NO,CUSTOMER_NAME,LOAN_AMOUNT,BALANCE_AMOUNT,LOAN_FINAL_RATE,CASE LOAN_RATE_TYPE ");  
				bufInsSql.append(" WHEN 'E' THEN 'EFFECTIVE' WHEN 'F' THEN 'FLAT' WHEN 'P' THEN 'PTPM' end AS LOAN_RATE_TYPE, ");
				bufInsSql.append(" NEW_LOAN_FINAL_RATE  ");

				bufInsSql.append(" FROM cr_non_emi_based_loan_dtl WHERE REC_STATUS='"+type+"'   ");
				
				bufInsSqlTempCount.append("SELECT COUNT(*) FROM cr_non_emi_based_loan_dtl WHERE REC_STATUS='"+type+"' ");
				
			    
				if(!modifyLoanId.equalsIgnoreCase(""))
				  {
					bufInsSql.append("AND LOAN_ID='"+modifyLoanId+"' " );	
					bufInsSqlTempCount.append("AND LOAN_ID='"+modifyLoanId+"' " );
					
					}
				
				if(!customer.equalsIgnoreCase(""))
				  {
					bufInsSql.append(" AND CUSTOMER_NAME like '%" + customer	+ "%' " );	
					bufInsSqlTempCount.append(" AND CUSTOMER_NAME like '%" + customer	+ "%' " );
					
					}
				
				if(!rateType.equalsIgnoreCase(""))
				  {
					bufInsSql.append("AND LOAN_RATE_TYPE='"+rateType+"' " );	
					bufInsSqlTempCount.append("AND LOAN_RATE_TYPE='"+rateType+"' " );
					
					}
				
					if(type.equalsIgnoreCase("F"))
					  {
						bufInsSql.append("AND MAKER_ID!='"+nonEmiLoanvo.getUserId()+"' " );	
						bufInsSqlTempCount.append("AND MAKER_ID!='"+nonEmiLoanvo.getUserId()+"' " );
						
						}
					if(type.equalsIgnoreCase("P"))
					  {
						bufInsSql.append("AND MAKER_ID='"+nonEmiLoanvo.getUserId()+"' " );	
						bufInsSqlTempCount.append("AND MAKER_ID='"+nonEmiLoanvo.getUserId()+"' " );
						
						}
					
				count = Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
				
				bufInsSql.append(" ORDER BY LOAN_ID OFFSET ");
				bufInsSql.append(startRecordIndex);
				bufInsSql.append(" ROWS FETCH next ");
				bufInsSql.append(endRecordIndex);
				bufInsSql.append(" ROWS ONLY ");
				//logger.info("Search searchBankBranchDao query for SQL SERVER : " + bufInsSql.toString());
				//	bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));		
				
				
				logger.info("query :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: " + bufInsSql);
				searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
				
				if (nonEmivo.getCurrentPageLink() > 1) {
					startRecordIndex = (nonEmivo.getCurrentPageLink() - 1) * no;
					endRecordIndex = no;
					logger.info("startRecordIndex .................... "+ startRecordIndex);
					logger.info("endRecordIndex .................... "+ endRecordIndex);
			     }

				logger.info("IN nonEmiBasedMakerGrid() search query1 ### "+ bufInsSql.toString());
				logger.info("nonEmiBasedMakerGrid:::::::::search:::::: " + searchlist.size());
				int size = searchlist.size();
				for (int i = 0; i < size; i++) {
					data = (ArrayList) searchlist.get(i);
					if (data.size() > 0) {
						nonEmivo = new ChangeRateVo();
                    if(type.equalsIgnoreCase("P")){
						nonEmivo.setModifyLoan("<a href=changeRateMakerNewAction.do?method=EditNonEMILoanMaker&lbxLoanNo="
										+ CommonFunction.checkNull(data.get(0)).toString()+ ">"
										+ CommonFunction.checkNull(data.get(1)).toString() + "</a>");
                     }
						else{
						nonEmivo.setModifyLoan("<a href=changeRateAuthor.do?method=ViewChangeRateDataToAuthor&lbxLoanNo="
								+ CommonFunction.checkNull(data.get(0)).toString()+ ">"
								+ CommonFunction.checkNull(data.get(1)).toString() + "</a>");
						}
						
						nonEmivo.setLbxLoanNo(CommonFunction.checkNull(data.get(0)).toString());
						nonEmivo.setLoanNo(CommonFunction.checkNull(data.get(1)).toString());
						nonEmivo.setCustomerName(CommonFunction.checkNull(data.get(2)).toString());
						nonEmivo.setLoanAmount(CommonFunction.checkNull(data.get(3)).toString());
						nonEmivo.setBalanceAmount(CommonFunction.checkNull(data.get(4)).toString());
						nonEmivo.setLoanFinalRate(myFormatter.format(myFormatter.parse(CommonFunction.checkNull(data.get(5)))));
						nonEmivo.setCurrentRateType(CommonFunction.checkNull(data.get(6)).toString());
						nonEmivo.setNewEffectRate(myFormatter.format(myFormatter.parse(CommonFunction.checkNull(data.get(7)))));
							
						nonEmivo.setTotalRecordSize(count);
						detailList.add(nonEmivo);
						
				
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			finally
			{
				bufInsSql=null;
			}

			return detailList;
		
	}	
		
		
		
		public ArrayList<ChangeRateVo> getValueForChangeRate(String lbxLoanID) {
			
			ArrayList<ChangeRateVo> detailList = new ArrayList<ChangeRateVo>();
			logger.info("In getValueForChangeRate::::::::::::::::::::::::::: ");
			
			 
		    ArrayList mainlist=new ArrayList();
			ArrayList subList=new ArrayList();
			try{
                StringBuffer sbAppendToSQLCount=new StringBuffer();
		       	StringBuffer bufInsSql =    new StringBuffer();

		        bufInsSql.append(" SELECT C.LOAN_LOAN_AMOUNT,C.LOAN_BALANCE_PRINCIPAL,ISNULL(C.LOAN_FINAL_RATE,0)," +
		        		"CASE C.LOAN_RATE_TYPE WHEN 'E' THEN 'EFFECTIVE' WHEN 'F' THEN 'FLAT' WHEN 'P' THEN 'PTPM'end AS " +
		        		"LOAN_RATE_TYPE,C.LOAN_RATE_TYPE  from cr_loan_dtl C join gcd_customer_m G on C.LOAN_CUSTOMER_ID=G.CUSTOMER_ID " +
		        		"where C.LOAN_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(lbxLoanID).trim())+"'");

                 logger.info("Query-----------------------------"+bufInsSql.toString());
		      	 mainlist = ConnectionDAO.sqlSelect(bufInsSql.toString());


			for(int i=0;i<mainlist.size();i++){

				subList=(ArrayList)mainlist.get(i);
				logger.info("In getAssetList..."+subList.size());
				
				if(subList.size()>0){
					
					ChangeRateVo vo=new ChangeRateVo();
					vo.setLoanAmount(CommonFunction.checkNull(subList.get(0)).trim());
					vo.setBalanceAmount(CommonFunction.checkNull(subList.get(1)).trim());
					vo.setLoanFinalRate(myFormatter.format(myFormatter.parse(CommonFunction.checkNull(subList.get(2)))));
					vo.setCurrentRateType(CommonFunction.checkNull(subList.get(3)).trim());
					vo.setLoanRateType(CommonFunction.checkNull(subList.get(4)).trim());
							
					detailList.add(vo);
				}
		}
			}catch(Exception e){
				e.printStackTrace();
			}
			

			return detailList;
		}
		
		
		public boolean insertMakerData(Object ob){
			ChangeRateVo NoEmiBasedVo =  (ChangeRateVo)ob; 
			ArrayList<ChangeRateVo> getDataList=new ArrayList<ChangeRateVo>();
//			PrepStmtObject insertPrepStmtObject = new PrepStmtObject();	
			boolean status=false;	
			logger.info("In ChangeRateDAOImpl...Dao Impl.");
			logger.info("In insertMakerData......................");
			 ArrayList qryList = new ArrayList();
			 PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
			 PrepStmtObject updatePrepStmtObject = new PrepStmtObject();
			
				try{
					
				    StringBuffer bufInsSql =	new StringBuffer();
				    bufInsSql.append(" INSERT INTO cr_non_emi_based_loan_dtl (LOAN_ID,LOAN_NO,CUSTOMER_NAME,LOAN_AMOUNT,BALANCE_AMOUNT,");
					bufInsSql.append(" LOAN_FINAL_RATE,LOAN_RATE_TYPE,NEW_LOAN_FINAL_RATE,MAKER_ID,MAKER_DATE,REC_STATUS)");
					bufInsSql.append(" values ( ");
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(dbo);
					bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"')," );
					//bufInsSql.append(" ?," );
					//bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"')," );
					bufInsSql.append(" 'P' )" );
					
					if(CommonFunction.checkNull(NoEmiBasedVo.getLbxLoanNo()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((NoEmiBasedVo.getLbxLoanNo()).trim());

					if (CommonFunction.checkNull(NoEmiBasedVo.getLoanNo()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((NoEmiBasedVo.getLoanNo()).trim());
					
					if (CommonFunction.checkNull(NoEmiBasedVo.getCustomerName()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((NoEmiBasedVo.getCustomerName()).trim());
				
					if(CommonFunction.checkNull(NoEmiBasedVo.getLoanAmount()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(myFormatter.parse((NoEmiBasedVo.getLoanAmount()).trim()).toString());
					
					if (CommonFunction.checkNull((NoEmiBasedVo.getBalanceAmount()).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(myFormatter.parse((NoEmiBasedVo.getBalanceAmount()).trim()).toString());
						
					if(CommonFunction.checkNull(NoEmiBasedVo.getLoanFinalRate()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((NoEmiBasedVo.getLoanFinalRate()).trim());
					
					if(CommonFunction.checkNull(NoEmiBasedVo.getLoanRateType()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((NoEmiBasedVo.getLoanRateType()).trim());
										
					if(CommonFunction.checkNull(NoEmiBasedVo.getNewEffectRate()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((NoEmiBasedVo.getNewEffectRate()).trim());
					
					if(CommonFunction.checkNull(NoEmiBasedVo.getMakerId()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((NoEmiBasedVo.getMakerId()).trim());
					if(CommonFunction.checkNull(NoEmiBasedVo.getMakerDate()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((NoEmiBasedVo.getMakerDate()).trim());
					
//					if(CommonFunction.checkNull(NoEmiBasedVo.getMakerId()).equalsIgnoreCase(""))
//						insertPrepStmtObject.addNull();
//					else
//						insertPrepStmtObject.addString((NoEmiBasedVo.getMakerId()).trim());
//					
//					if(CommonFunction.checkNull(NoEmiBasedVo.getMakerDate()).equalsIgnoreCase(""))
//						insertPrepStmtObject.addNull();
//					else
//						insertPrepStmtObject.addString((NoEmiBasedVo.getMakerDate()).trim());
							
											
					insertPrepStmtObject.setSql(bufInsSql.toString());
					
					qryList.add(insertPrepStmtObject);
					logger.info("IN  insertMakerData query1 ### "+insertPrepStmtObject.printQuery());	
					status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
					logger.info("In insertMakerData ....................."+status);
		
			}
			
				catch(Exception e){
					e.printStackTrace();
				}
		finally{
			qryList=null;
		      }
			
					 
				return status;	
		}



	
		public boolean updateMakerData(Object ob) {

			ChangeRateVo NoEmiBasedVo =  (ChangeRateVo)ob; 
			ArrayList<ChangeRateVo> getDataList=new ArrayList<ChangeRateVo>();
//			PrepStmtObject insertPrepStmtObject = new PrepStmtObject();	
			boolean status=false;
			ArrayList queryList=new ArrayList();
			
			logger.info("In ChangeRateDAOImpl...Dao Impl.");
			logger.info("In updateMakerData......................");
			 ArrayList qryList = new ArrayList();
			  
			
			
				try{					
					PrepStmtObject updatePrepStmtObject = new PrepStmtObject();
					StringBuffer UpdatedQry =	new StringBuffer();
					UpdatedQry.append(" UPDATE cr_non_emi_based_loan_dtl SET LOAN_FINAL_RATE=?,NEW_LOAN_FINAL_RATE=?,MAKER_ID=?,MAKER_DATE=");
					UpdatedQry.append(dbo);
					UpdatedQry.append("STR_TO_DATE(?,'"+dateFormat+"'),REC_STATUS='P' ");
			        UpdatedQry.append(" WHERE LOAN_ID="+NoEmiBasedVo.getLbxLoanNo()+" ");

			           if(CommonFunction.checkNull(NoEmiBasedVo.getLoanFinalRate()).equalsIgnoreCase(""))
						    updatePrepStmtObject.addNull();
					    else
						    updatePrepStmtObject.addString((NoEmiBasedVo.getLoanFinalRate()).trim());
			        
			            if(CommonFunction.checkNull(NoEmiBasedVo.getNewEffectRate()).equalsIgnoreCase(""))
							updatePrepStmtObject.addNull();
						else
							updatePrepStmtObject.addString((NoEmiBasedVo.getNewEffectRate()).trim());

						if (CommonFunction.checkNull(NoEmiBasedVo.getLbxLoanNo()).equalsIgnoreCase(""))
							updatePrepStmtObject.addNull();
						else
							
						if(CommonFunction.checkNull(NoEmiBasedVo.getMakerId()).equalsIgnoreCase(""))
							updatePrepStmtObject.addNull();
						else
							updatePrepStmtObject.addString((NoEmiBasedVo.getMakerId()).trim());
						
						if(CommonFunction.checkNull(NoEmiBasedVo.getMakerDate()).equalsIgnoreCase(""))
							updatePrepStmtObject.addNull();
						else
							updatePrepStmtObject.addString((NoEmiBasedVo.getMakerDate()).trim());
						
//						if(CommonFunction.checkNull(NoEmiBasedVo.getMakerId()).equalsIgnoreCase(""))
//							updatePrepStmtObject.addNull();
//						else
//							updatePrepStmtObject.addString((NoEmiBasedVo.getMakerId()).trim());
//						
//						if(CommonFunction.checkNull(NoEmiBasedVo.getMakerDate()).equalsIgnoreCase(""))
//							updatePrepStmtObject.addNull();
//						else
//							updatePrepStmtObject.addString((NoEmiBasedVo.getMakerDate()).trim());
					
						updatePrepStmtObject.setSql(UpdatedQry.toString());
						
						queryList.add(updatePrepStmtObject);
						
						logger.info("IN updateMakerData query1 ### "+updatePrepStmtObject.printQuery());
						status =ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);
						logger.info("In updateMakerData....................."+status);
		
			}
		 
				catch(Exception e){
					e.printStackTrace();
				}
				finally
				{
					queryList=null;
				}
				
				return status;	
		
		}


		
		public boolean forwardNonEMILoanData(Object ob) 
		{
			ChangeRateVo vo =  (ChangeRateVo)ob; 
			ArrayList qryList=new ArrayList();
			boolean fwdStatus=false;
			StringBuilder query1=new StringBuilder();
			query1.append("update cr_non_emi_based_loan_dtl set REC_STATUS='F' where LOAN_ID='"+CommonFunction.checkNull(vo.getLbxLoanNo())+"' ");
			logger.info("query1:::::::::::::::::::::::::: "+query1.toString());
			qryList.add(query1);
	        try {
	        	fwdStatus = ConnectionDAO.sqlInsUpdDelete(qryList);
				logger.info("In forwardNonEMILoanData..................fwdStatus:::::= "+fwdStatus);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finally
			{
				query1=null;
			}
			return fwdStatus;
		}




		public ArrayList<ChangeRateVo> nonEmiBasedGrid(Object ob) 
		{
        	ChangeRateVo nonEmiLoanvo =  (ChangeRateVo)ob; 
	
			int startRecordIndex = 0;
			int endRecordIndex = no;
			String LoanId=null;
			
			ArrayList searchlist = new ArrayList();
			ArrayList data= new ArrayList();
			StringBuilder bufInsSql = new StringBuilder();
		
			ChangeRateVo nonEmivo= new ChangeRateVo();
			
			ArrayList<ChangeRateVo> detailList = new ArrayList<ChangeRateVo>();
			try {
				LoanId= CommonFunction.checkNull(nonEmiLoanvo.getLbxLoanNo());
				logger.info("In nonEmiBasedGrid..............ModifyLoanId......................"+LoanId);
				
				bufInsSql.append(" SELECT LOAN_ID,LOAN_NO,CUSTOMER_NAME,LOAN_AMOUNT,BALANCE_AMOUNT,LOAN_FINAL_RATE,CASE LOAN_RATE_TYPE ");  
				bufInsSql.append(" WHEN 'E' THEN 'EFFECTIVE' WHEN 'F' THEN 'FLAT' WHEN 'P' THEN 'PTPM' end AS LOAN_RATE_TYPE, ");
				bufInsSql.append(" NEW_LOAN_FINAL_RATE FROM cr_non_emi_based_loan_dtl WHERE LOAN_ID='"+nonEmiLoanvo.getLbxLoanNo()+"' ");
				
				searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
				
				logger.info("IN nonEmiBasedGrid() search query1 ### "+ bufInsSql.toString());
				logger.info("nonEmiBasedGrid:::::::::search:::::: " + searchlist.size());
				int size = searchlist.size();
				for (int i = 0; i < size; i++) {
					data = (ArrayList) searchlist.get(i);
					if (data.size() > 0) {
						nonEmivo = new ChangeRateVo();

						nonEmivo.setLbxLoanNo(CommonFunction.checkNull(data.get(0)).toString());
						nonEmivo.setLoanNo(CommonFunction.checkNull(data.get(1)).toString());
						nonEmivo.setCustomerName(CommonFunction.checkNull(data.get(2)).toString());
						nonEmivo.setLoanAmount(CommonFunction.checkNull(data.get(3)).toString());
						nonEmivo.setBalanceAmount(CommonFunction.checkNull(data.get(4)).toString());
						nonEmivo.setLoanFinalRate(myFormatter.format(myFormatter.parse(CommonFunction.checkNull(data.get(5)))));
						nonEmivo.setCurrentRateType(CommonFunction.checkNull(data.get(6)).toString());
						nonEmivo.setNewEffectRate(myFormatter.format(myFormatter.parse(CommonFunction.checkNull(data.get(7)))));
						detailList.add(nonEmivo);
						
							}

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			finally
			{
				bufInsSql=null;
			}

			return detailList;
		
	
		}
		
		
		
		public boolean saveAuthorDecision(Object ob) 
		{
			ChangeRateVo vo =  (ChangeRateVo)ob; 
			ArrayList qryList=new ArrayList();
			ArrayList qryList1=new ArrayList();
			boolean fwdStatus=false;
			boolean Status=false;
			
			StringBuilder query1=new StringBuilder();
			StringBuilder query2=new StringBuilder();
			query1.append("update cr_non_emi_based_loan_dtl set REC_STATUS='"+vo.getDecision()+"',AUTHOR_ID='"+vo.getMakerId()+"',AUTHOR_DATE=");
			query1.append(dbo);
			query1.append("STR_TO_DATE('"+vo.getMakerDate()+"','"+dateFormatWithTime+"') ,AUTHOR_COMMENTS='"+vo.getComments()+"' where LOAN_ID='"+CommonFunction.checkNull(vo.getLbxLoanNo())+"' ");
			logger.info("query1:::::::::::::::::::::::::: "+query1.toString());
			qryList.add(query1);
	        try {
	        	fwdStatus = ConnectionDAO.sqlInsUpdDelete(qryList);
				logger.info("In saveAuthorDecision..................:::::= "+fwdStatus);
				if((vo.getDecision().equalsIgnoreCase("A")) && (fwdStatus==true)){
					
				query2.append(" UPDATE cr_loan_dtl SET LOAN_FINAL_RATE=(SELECT NEW_LOAN_FINAL_RATE FROM " +
				" cr_non_emi_based_loan_dtl d WHERE d.LOAN_ID='"+CommonFunction.checkNull(vo.getLbxLoanNo())+"'),LOAN_MARKUP=(SELECT NEW_LOAN_FINAL_RATE FROM " +
				" cr_non_emi_based_loan_dtl d WHERE d.LOAN_ID='"+CommonFunction.checkNull(vo.getLbxLoanNo())+"')-ISNULL(LOAN_BASE_RATE ,0)," +
						"LOAN_EFF_RATE=(SELECT NEW_LOAN_FINAL_RATE FROM cr_non_emi_based_loan_dtl d WHERE d.LOAN_ID='"+CommonFunction.checkNull(vo.getLbxLoanNo())+"')  WHERE LOAN_ID='"+CommonFunction.checkNull(vo.getLbxLoanNo())+"' ");
				
				qryList1.add(query2);
				logger.info("query2:::::::::::::::::::::::::: "+query2.toString());
				Status = ConnectionDAO.sqlInsUpdDelete(qryList1);
				if(Status)
				{
						fwdStatus=Status;	
				}else
				{
					fwdStatus=Status;
				}
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			finally
			{
				query1=null;
				query2=null;
				qryList.clear();
				qryList=null;
				qryList1.clear();
				qryList1=null;
			}
			return fwdStatus;
		}



	
		public boolean checkStatus(String lbxLoanNo) {
			boolean Status=true;
			String bufInsSql=null;
			int count=0;
		
			bufInsSql="SELECT count(*) FROM cr_non_emi_based_loan_dtl WHERE LOAN_ID='"+lbxLoanNo+"' AND REC_STATUS IN ('P','F') ";
			logger.info("query1:::::::::::::::::::::::::: "+bufInsSql);
					
	        try {
	        	logger.info("query1:::::::::::::::::::::::::: "+bufInsSql); 	
	        	count = Integer.parseInt(ConnectionDAO.singleReturn(bufInsSql));
	        	logger.info("In checkStatus.........Count.........:::::= "+count);
	        	if(count>0){
	        		Status=false;
	        	}
	        	
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally
			{
				bufInsSql=null;
			}
			return Status;
		
		}

		public boolean checkLoanStatus(String lbxLoanNo) {
			boolean Status=false;
			String bufInsSql=null;
			int count=0;
		
			bufInsSql="SELECT count(*) FROM cr_non_emi_based_loan_dtl WHERE LOAN_ID='"+lbxLoanNo+"' AND REC_STATUS IN ('A','X') ";
			logger.info("query1:::::::::::::::::::::::::: "+bufInsSql);
					
	        try {
	        	logger.info("query1:::::::::::::::::::::::::: "+bufInsSql); 	
	        	count = Integer.parseInt(ConnectionDAO.singleReturn(bufInsSql));
	        	logger.info("In checkLoanStatus.........Count.........:::::= "+count);
	        	if(count>0){
	        		Status=true;
	        	}
	        	
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally
			{
				bufInsSql=null;
			}
			return Status;
		
		}
		
		public String getMakerDate(String loanId) 
		{
			String makeDate="";
			StringBuilder query = new StringBuilder();
			query.append("select ");
			query.append(dbo);
			query.append("date_format(MAKER_DATE,'%d-%m-%Y') from cr_non_emi_based_loan_dtl where LOAN_ID="+loanId.trim());
			logger.info("Query for getting Maker Date of Rate Change  :  "+query.toString());
			try
			{
				makeDate = ConnectionDAO.singleReturn(query.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return makeDate;
		}

}
