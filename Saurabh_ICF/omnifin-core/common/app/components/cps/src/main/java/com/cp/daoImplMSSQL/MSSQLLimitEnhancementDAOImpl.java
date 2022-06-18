package com.cp.daoImplMSSQL;

import java.sql.*;
import java.text.DecimalFormat;
import java.util.*;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import com.connect.PrepStmtObject;
import com.cp.dao.LimitEnhancementDAO;
import com.cp.vo.LimitEnhancementVo;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

public class MSSQLLimitEnhancementDAOImpl implements LimitEnhancementDAO {
	private static final Logger logger = Logger.getLogger(MSSQLLimitEnhancementDAOImpl.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	// asesh space start
	String dbType=resource.getString("lbl.dbType");
	String dbo=resource.getString("lbl.dbPrefix");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	//String selectFrom = resource.getString("lbl.selectFrom");
	Connection con=null;
	Statement stmt=null;
	PreparedStatement pstmt=null;
	CallableStatement cstm=null;
	ResultSet rs=null;
	StringBuffer bufInsUpdSql = null;
	ArrayList qryList = null;
	ArrayList qryList1 = null;
	CallableStatement cs = null;
	PrepStmtObject  delPrepStmtObject = null;
	DecimalFormat myFormatter = new DecimalFormat("###,###.####");
	//DecimalFormat myFormatter = new DecimalFormat("###.##");
	
	ArrayList qryListB = null;
	ArrayList qryListS = null;

	
	public boolean insertLimitEnhancement(Object ob) {
		LimitEnhancementVo vo = (LimitEnhancementVo) ob;
		boolean status = false;
		logger.info("In insertLimitEnhancement.....................................LimitEnhancementDAOImpl ");
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		logger.info("DB Type : " + dbType);
		try {
				StringBuffer bufInsSql = new StringBuffer();
				StringBuilder q=new StringBuilder();
				q.append("select DEAL_ID from cr_deal_enhancement where DEAL_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo())).trim()+"'");
				boolean st=false;
				st=ConnectionDAO.checkStatus(q.toString());
				if(!st)
				{
				logger.info("In insert query");
				bufInsSql.append("insert into cr_deal_enhancement(DEAL_LOAN_ID,DEAL_ID,OLD_DEAL_SANCTION_AMOUNT,OLD_DEAL_SANCTION_DATE,OLD_DEAL_SANCTION_VALID_TILL,OLD_DEAL_UTILIZED_AMOUNT,DEAL_SANCTION_DATE,DEAL_SANCTION_VALID_TILL,ADDITIONAL_SANCTION_AMOUNT,MAKER_REMARKS,REC_STATUS,MAKER_ID,MAKER_DATE,LOAN_ID,ADDITIONAL_LOAN_AMOUNT,OLD_LOAN_AMOUNT,NEW_LOAN_TYPE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,"); //DEAL_LOAN_ID
				bufInsSql.append(" ?,"); //DEAL_ID
				bufInsSql.append(" ?,"); //OLD_DEAL_SANCTION_AMOUNT
				bufInsSql.append(dbo);
				bufInsSql.append(" STR_TO_DATE(?,'" + dateFormat+ "'),"); //OLD_DEAL_SANCTION_DATE
				bufInsSql.append(dbo);
				bufInsSql.append(" STR_TO_DATE(?,'" + dateFormat+ "'),"); //OLD_DEAL_SANCTION_VALID_TILL
				bufInsSql.append(" ?,"); //OLD_DEAL_UTILIZED_AMOUNT
				bufInsSql.append(dbo);
				bufInsSql.append(" STR_TO_DATE(?,'" + dateFormat+ "'),"); //DEAL_SANCTION_DATE
				bufInsSql.append(dbo);
				bufInsSql.append(" STR_TO_DATE(?,'" + dateFormat+ "'),"); //DEAL_SANCTION_VALID_TILL
				bufInsSql.append(" ?,"); //ADDITIONAL_SANCTION_AMOUNT
				bufInsSql.append(" ?,"); //MAKER_REMARKS
				bufInsSql.append(" 'P',"); //REC_STATUS
				bufInsSql.append(" ?,"); //MAKER_ID
				bufInsSql.append(dbo);
				bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");//MAKER_DATE
				bufInsSql.append(" ?,"); //LOAN_ID
				bufInsSql.append(" ? , "); //ADDITIONAL_LOAN_AMOUNT
				bufInsSql.append(" ? ,");//OLD_LOAN_AMOUNT
				bufInsSql.append(" ? )");//NEW_LOAN_TYPE
				if((CommonFunction.checkNull(vo.getLbxLoanNo())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getLbxLoanNo()).trim());
				
				if((CommonFunction.checkNull(vo.getLbxDealNo())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getLbxDealNo()).trim());
				
				if((CommonFunction.checkNull(vo.getOldSenAmount())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((vo.getOldSenAmount()).trim()).toString());
				
				if((CommonFunction.checkNull(vo.getOldSenDate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getOldSenDate()).trim());
				
				if((CommonFunction.checkNull(vo.getOldSenValidTill())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getOldSenValidTill()).trim());
				
				if((CommonFunction.checkNull(vo.getUtilAmount())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((vo.getUtilAmount()).trim()).toString());
				
				if((CommonFunction.checkNull(vo.getNewSenDate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getNewSenDate()).trim());
				
				if((CommonFunction.checkNull(vo.getApplicationdate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getApplicationdate()).trim());
				
				if((CommonFunction.checkNull(vo.getAddSenAmount())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((vo.getAddSenAmount()).trim()).toString());
				
				if((CommonFunction.checkNull(vo.getMakeRemarks())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getMakeRemarks()).trim().toString());
				
				if((CommonFunction.checkNull(vo.getMakerId()).trim()).equalsIgnoreCase(""))
				    insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getMakerId()).trim().toString());
				
				if((CommonFunction.checkNull(vo.getMakerDate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getMakerDate()).trim().toString());
				//LOAN_ID
				if((CommonFunction.checkNull(vo.getLbxLoanNo())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getLbxLoanNo()).trim());
				//NEW_LOAN_AMOUNT
				if((CommonFunction.checkNull(vo.getNewLoanAmount())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((vo.getNewLoanAmount()).trim()).toString());
				//OLD_LOAN_AMOUNT
				if((CommonFunction.checkNull(vo.getOldLoanAmount())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((vo.getOldLoanAmount()).trim()).toString());
				
				if((CommonFunction.checkNull(vo.getLoanAmountType())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getLoanAmountType()).trim());
				}
				else
				{
					logger.info("In update query");
					bufInsSql.append("update cr_deal_enhancement set DEAL_LOAN_ID=?,OLD_DEAL_SANCTION_AMOUNT=?,OLD_DEAL_SANCTION_DATE=");
					bufInsSql.append(dbo);
					bufInsSql.append("STR_TO_DATE(?,'" + dateFormat+ "'),OLD_DEAL_SANCTION_VALID_TILL=");
					bufInsSql.append(dbo);
					bufInsSql.append("STR_TO_DATE(?,'" + dateFormat+ "'),OLD_DEAL_UTILIZED_AMOUNT=?,DEAL_SANCTION_DATE=");
					bufInsSql.append(dbo);
					bufInsSql.append("STR_TO_DATE(?,'" + dateFormat+ "'),DEAL_SANCTION_VALID_TILL=");
					bufInsSql.append(dbo);
					bufInsSql.append("STR_TO_DATE(?,'" + dateFormat+ "')," +"ADDITIONAL_SANCTION_AMOUNT=?,MAKER_REMARKS=?,REC_STATUS='P',MAKER_ID=?,MAKER_DATE=");
					bufInsSql.append(dbo);
					bufInsSql.append("STR_TO_DATE(?,'" + dateFormat+ "'),LOAN_ID=?,ADDITIONAL_LOAN_AMOUNT=?,OLD_LOAN_AMOUNT=?,NEW_LOAN_TYPE=? where DEAL_ID=?");

					if((CommonFunction.checkNull(vo.getLbxLoanNo())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getLbxLoanNo()).trim());
										
					if((CommonFunction.checkNull(vo.getOldSenAmount())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(myFormatter.parse((vo.getOldSenAmount()).trim()).toString());
					
					if((CommonFunction.checkNull(vo.getOldSenDate())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getOldSenDate()).trim());
					
					if((CommonFunction.checkNull(vo.getOldSenValidTill())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getOldSenValidTill()).trim());
					
					if((CommonFunction.checkNull(vo.getUtilAmount())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(myFormatter.parse((vo.getUtilAmount()).trim()).toString());
					
					if((CommonFunction.checkNull(vo.getNewSenDate())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getNewSenDate()).trim());
					
					if((CommonFunction.checkNull(vo.getApplicationdate())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getApplicationdate()).trim());
					
					if((CommonFunction.checkNull(vo.getAddSenAmount())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(myFormatter.parse((vo.getAddSenAmount()).trim()).toString());
					
					if((CommonFunction.checkNull(vo.getMakeRemarks())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getMakeRemarks()).trim().toString());
					
					if((CommonFunction.checkNull(vo.getMakerId()).trim()).equalsIgnoreCase(""))
					    insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getMakerId()).trim().toString());
					
					if((CommonFunction.checkNull(vo.getMakerDate())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getMakerDate()).trim().toString());
					
				//LOAN_ID
					if((CommonFunction.checkNull(vo.getLbxLoanNo())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getLbxLoanNo()).trim());
				//NEW_LOAN_AMOUNT
					if((CommonFunction.checkNull(vo.getNewLoanAmount())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(myFormatter.parse((vo.getNewLoanAmount()).trim()).toString());
					//OLD_LOAN_AMOUNT
					if((CommonFunction.checkNull(vo.getOldLoanAmount())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(myFormatter.parse((vo.getOldLoanAmount()).trim()).toString());
					//NEW_LOAN_TYPE
					if((CommonFunction.checkNull(vo.getLoanAmountType())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getLoanAmountType()).trim());
					
					if((CommonFunction.checkNull(vo.getLbxDealNo())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getLbxDealNo()).trim());
				}
				insertPrepStmtObject.setSql(bufInsSql.toString());
				
				logger.info("IN insert insertLimitEnhancement() insert query1 ### "+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In insertLimitEnhancement......................"+ status);

				bufInsSql=null;
	
		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;

	}

	public ArrayList limitEnhancmentValues(String dealId,String loanId,String Status) {
		ArrayList list=new ArrayList();
		LimitEnhancementVo vo= null;
			try
			{
				StringBuilder query=new StringBuilder();
				
				query.append("select DISTINCT D.DEAL_NO,DE.DEAL_ID,DE.OLD_DEAL_SANCTION_AMOUNT,");
				query.append(dbo);
				query.append("DATE_FORMAT(DE.OLD_DEAL_SANCTION_DATE,'%d-%m-%Y'),");
				query.append(dbo);
				query.append("DATE_FORMAT(DE.OLD_DEAL_SANCTION_VALID_TILL,'%d-%m-%Y')," +
								" DE.OLD_DEAL_UTILIZED_AMOUNT,");
				query.append(dbo);
				query.append("DATE_FORMAT(DE.DEAL_SANCTION_DATE,'%d-%m-%Y'),");
				query.append(dbo);
				query.append("DATE_FORMAT(DE.DEAL_SANCTION_VALID_TILL,'%d-%m-%Y'),DE.ADDITIONAL_SANCTION_AMOUNT,DE.MAKER_REMARKS,DE.AUTHOR_REMARKS ,DE.LOAN_ID,DE.ADDITIONAL_LOAN_AMOUNT ,CDCM.CUSTOMER_NAME,CLD.LOAN_NO,DE.NEW_LOAN_TYPE" +
						     " FROM cr_deal_enhancement DE INNER JOIN cr_deal_dtl D ON DE.DEAL_ID=D.DEAL_ID " +
						     " JOIN CR_DEAL_CUSTOMER_M CDCM ON CDCM.CUSTOMER_ID=D.DEAL_CUSTOMER_ID LEFT JOIN CR_LOAN_DTL CLD ON CLD.LOAN_ID=DE.LOAN_ID"+
								" WHERE DE.DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()+" AND DE.REC_STATUS='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(Status)).trim()+"' ");
			//Nishant space starts
//				if(!(CommonFunction.checkNull(loanId).trim().equalsIgnoreCase("")))
//				{
//					query.append("AND DE.DEAL_LOAN_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()+"");
//				}
			//Nishant space end
				logger.info("get limitEnhancmentValues Query: "+query);
				
				
				ArrayList list1 = ConnectionDAO.sqlSelect(query.toString());
				
				query=null;
				
				for(int i=0;i<list1.size();i++){
					//logger.info("list1"+list1.size());
					ArrayList header1=(ArrayList)list1.get(i);
					if(header1!=null && header1.size()>0)
					{
						vo = new LimitEnhancementVo();
						
						vo.setDealNo((CommonFunction.checkNull(header1.get(0))).trim());
//					//Nishant Space starts
//						if(!(CommonFunction.checkNull(loanId).trim().equalsIgnoreCase("")))
//							vo.setLoanNo((CommonFunction.checkNull(header1.get(1))).trim());
//						if(!(CommonFunction.checkNull(loanId).trim().equalsIgnoreCase("")))
//							vo.setLbxLoanNo((CommonFunction.checkNull(header1.get(2))).trim());
//					//Nishant Space end	
						vo.setLbxDealNo((CommonFunction.checkNull(header1.get(1))).trim());
					
						if(!CommonFunction.checkNull(header1.get(2)).equalsIgnoreCase(""))
						{
							Number oldSenAmount = myFormatter.parse((CommonFunction.checkNull(header1.get(2))).trim());
							vo.setOldSenAmount(myFormatter.format(oldSenAmount));
						}
						
						vo.setOldSenDate((CommonFunction.checkNull(header1.get(3))).trim());
						vo.setOldSenValidTill((CommonFunction.checkNull(header1.get(4))).trim());
						
						if(!CommonFunction.checkNull(header1.get(5)).equalsIgnoreCase(""))
						{
							Number utilized = myFormatter.parse((CommonFunction.checkNull(header1.get(5))).trim());
							vo.setUtilAmount(myFormatter.format(utilized));
						}
						
						vo.setNewSenDate((CommonFunction.checkNull(header1.get(6))).trim());
						vo.setApplicationdate((CommonFunction.checkNull(header1.get(7))).trim());
						
						if(!CommonFunction.checkNull(header1.get(8)).equalsIgnoreCase(""))
						{
							Number newSenAmount = myFormatter.parse((CommonFunction.checkNull(header1.get(8))).trim());
							vo.setAddSenAmount(myFormatter.format(newSenAmount));
						}
						vo.setMakeRemarks((CommonFunction.checkNull(header1.get(9))).trim());
						vo.setAuthorRemarks((CommonFunction.checkNull(header1.get(10))).trim());
						//MANISH
						if(!(CommonFunction.checkNull(loanId).trim().equalsIgnoreCase("")))
							vo.setLbxLoanNo((CommonFunction.checkNull(header1.get(11))).trim());
						//ADDITIONAL_LOAN_AMOUNT
						if(!CommonFunction.checkNull(header1.get(12)).equalsIgnoreCase(""))
						{
							Number newLoanAmount = myFormatter.parse((CommonFunction.checkNull(header1.get(12))).trim());
							vo.setNewLoanAmount(myFormatter.format(newLoanAmount));
						}
						vo.setCustomerName((CommonFunction.checkNull(header1.get(13))).trim());
						vo.setLoanNo((CommonFunction.checkNull(header1.get(14))).trim());
						vo.setLoanAmountType((CommonFunction.checkNull(header1.get(15))).trim());
						list.add(vo);
					}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}

			return list;
		}	
	
	public ArrayList<LimitEnhancementVo> getValueForLimitEnhancement(String lbxLoanID,String lbxDealNo) {
		
		ArrayList<LimitEnhancementVo> assetList = new ArrayList<LimitEnhancementVo>();
		logger.info("In getValueForLimitEnhancement: ");

	    ArrayList mainlist=new ArrayList();
		ArrayList subList=new ArrayList();
		
		try{
            logger.info("In getValueForLimitEnhancement().....................................Dao Impl");

   	               StringBuffer sbAppendToSQLCount=new StringBuffer();
   	               StringBuffer bufInsSql =    new StringBuffer();
			//Nishant Space starts
   	            bufInsSql.append("SELECT DISTINCT DL.DEAL_SANCTION_AMOUNT,");
   	            bufInsSql.append(dbo);
   	            bufInsSql.append("DATE_FORMAT(DL.DEAL_SANCTION_DATE,'%d-%m-%Y'),DL.DEAL_UTILIZED_AMOUNT,");
   	            bufInsSql.append(dbo);
   	            bufInsSql.append("DATE_FORMAT(DL.DEAL_SANCTION_VALID_TILL,'%d-%m-%Y') ,ISNULL(a.ADDITIONAL_SANCTION_AMOUNT,0)as ADDITIONAL_SANCTION_AMOUNT ,dbo.DATE_FORMAT(a.DEAL_SANCTION_VALID_TILL,'%d-%m-%Y') from cr_deal_loan_dtl DL left join cr_deal_enhancement a on a.DEAL_ID=dl.DEAL_ID " +
	            			" WHERE DL.DEAL_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(lbxDealNo).trim())+"'");
//   	            if(!(CommonFunction.checkNull(lbxDealNo).trim().equalsIgnoreCase("")) && (CommonFunction.checkNull(lbxLoanID).trim().equalsIgnoreCase(""))) //(lbxDealNo!="" && lbxLoanID=="")
//   	            {
//   	            	bufInsSql.append("SELECT DISTINCT DL.DEAL_SANCTION_AMOUNT,DATE_FORMAT(DL.DEAL_SANCTION_DATE,'%d-%m-%Y'),DL.DEAL_UTILIZED_AMOUNT,DATE_FORMAT(DL.DEAL_SANCTION_VALID_TILL,'%d-%m-%Y') FROM cr_loan_dtl L LEFT JOIN cr_deal_loan_dtl DL ON L.LOAN_DEAL_ID=DL.DEAL_ID " +
//   	            			" WHERE L.LOAN_DEAL_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(lbxDealNo).trim())+"'");
//   	            }
//   	            else
//   	            {
//   	            	bufInsSql.append( "SELECT DISTINCT DL.DEAL_SANCTION_AMOUNT,DATE_FORMAT(DL.DEAL_SANCTION_DATE,'%d-%m-%Y'),DL.DEAL_UTILIZED_AMOUNT,DATE_FORMAT(DL.DEAL_SANCTION_VALID_TILL,'%d-%m-%Y') FROM cr_loan_dtl L LEFT JOIN cr_deal_loan_dtl DL ON L.LOAN_DEAL_LOAN_ID=DL.DEAL_LOAN_ID "+
//                					" WHERE L.REC_STATUS='A'  and L.loan_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(lbxLoanID).trim())+"'");
//   	            }
			//Nishant Space end
       	               logger.info("Query-----"+bufInsSql.toString());
      	            mainlist = ConnectionDAO.sqlSelect(bufInsSql.toString());


		for(int i=0;i<mainlist.size();i++){

			subList=(ArrayList)mainlist.get(i);
			logger.info("In getValueForLimitEnhancement..."+subList.size());
			
			if(subList.size()>0){
				
				LimitEnhancementVo limitEnhVo=new LimitEnhancementVo();
			//Nishant Space starts
				if(!(CommonFunction.checkNull(subList.get(0))).trim().equalsIgnoreCase(""))	
				{
					Number oldSenAmount =myFormatter.parse((CommonFunction.checkNull(subList.get(0))).trim());
					limitEnhVo.setOldSenAmount(myFormatter.format(oldSenAmount));
					//limitEnhVo.setOldSenAmount((CommonFunction.checkNull(subList.get(0)).trim()));
				}
				limitEnhVo.setOldSenDate((CommonFunction.checkNull(subList.get(1)).trim()));
				if(!(CommonFunction.checkNull(subList.get(2))).trim().equalsIgnoreCase(""))	
				{
					Number utilAmount =myFormatter.parse((CommonFunction.checkNull(subList.get(2))).trim());
					limitEnhVo.setUtilAmount(myFormatter.format(utilAmount));
					//limitEnhVo.setUtilAmount((CommonFunction.checkNull(subList.get(2)).trim()));
				}
			//Nishant Space end	
				limitEnhVo.setOldSenValidTill((CommonFunction.checkNull(subList.get(3)).trim()));
				limitEnhVo.setOldSenValidTill((CommonFunction.checkNull(subList.get(3)).trim()));
				if(!(CommonFunction.checkNull(subList.get(4))).trim().equalsIgnoreCase(""))	
				{
					Number 

					addSenAmount =myFormatter.parse((CommonFunction.checkNull(subList.get(4))).trim());
					limitEnhVo.setAddSenAmount(myFormatter.format(addSenAmount));
				
				}
				limitEnhVo.setApplicationdate((CommonFunction.checkNull(subList.get(5)).trim()));
				assetList.add(limitEnhVo);
			}
	}
		}catch(Exception e){
			e.printStackTrace();
		}

		return assetList;
	}
	//Manish starts Limit Loan Enhancement
	public ArrayList<LimitEnhancementVo> getValueForLimitLoanEnhancement(String lbxLoanID,String lbxDealNo) {
			
			ArrayList<LimitEnhancementVo> assetList = new ArrayList<LimitEnhancementVo>();
			logger.info("In getValueForLimitLoanEnhancement: ");

		    ArrayList mainlist=new ArrayList();
			ArrayList subList=new ArrayList();
			
			try{
	            logger.info("In getValueForLimitLoanEnhancement().....................................Dao Impl");

	   	               StringBuffer sbAppendToSQLCount=new StringBuffer();
	   	               StringBuffer bufInsSql =    new StringBuffer();
				
	   	            bufInsSql.append("SELECT A.LOAN_LOAN_AMOUNT ,ISNULL(B.DISBURSAL_AMOUNT,0),A.LOAN_REPAYMENT_TYPE FROM CR_LOAN_DTL A ");
	   	            bufInsSql.append("LEFT JOIN ( SELECT LOAN_ID,SUM(ISNULL(ADVICE_AMOUNT,0)-ISNULL(TXN_ADJUSTED_AMOUNT,0))DISBURSAL_AMOUNT FROM CR_TXNADVICE_DTL " );
	   	            bufInsSql.append("WHERE LOAN_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(lbxLoanID).trim())+"' AND CHARGE_CODE_ID=8 GROUP BY LOAN_ID )B ON A.LOAN_ID=B.LOAN_ID ");
	   	            bufInsSql.append(" WHERE A.LOAN_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(lbxLoanID).trim())+"'");
	   	            
	       	            logger.info("Query-----"+bufInsSql.toString());
	      	            mainlist = ConnectionDAO.sqlSelect(bufInsSql.toString());


			for(int i=0;i<mainlist.size();i++){

				subList=(ArrayList)mainlist.get(i);
				logger.info("In getValueForLimitLoanEnhancement..."+subList.size());
				
				if(subList.size()>0){
					
					LimitEnhancementVo limitEnhVo=new LimitEnhancementVo();
				
					if(!(CommonFunction.checkNull(subList.get(0))).trim().equalsIgnoreCase(""))	
					{
						Number oldLoanAmount =myFormatter.parse((CommonFunction.checkNull(subList.get(0))).trim());
						limitEnhVo.setOldLoanAmount(myFormatter.format(oldLoanAmount));
					}
									
					if(!(CommonFunction.checkNull(subList.get(1))).trim().equalsIgnoreCase(""))	
					{
						Number disbursalAmount =myFormatter.parse((CommonFunction.checkNull(subList.get(1))).trim());
						limitEnhVo.setTotalDisbursedAmount(myFormatter.format(disbursalAmount));
					}
					limitEnhVo.setLoanInsType((CommonFunction.checkNull(subList.get(2))).trim());
									
					assetList.add(limitEnhVo);
				}
		}
			mainlist.clear();
			mainlist=null;
			subList.clear();
			subList=null;
			
			}catch(Exception e){
				e.printStackTrace();
			}

			return assetList;
		}
	//manish Ends
public boolean modifyLimitEnhancement(Object ob) {
		
		LimitEnhancementVo vo = (LimitEnhancementVo) ob;
		boolean status = false;
		
		logger.info("In modifyLimitEnhancement.....................................Dao Impl ");
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		
		try {

				logger.info("In modifyLimitEnhancement");
				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql.append("UPDATE cr_deal_enhancement SET ADDITIONAL_SANCTION_AMOUNT=?,DEAL_SANCTION_VALID_TILL=");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'" + dateFormat+ "'),MAKER_REMARKS=?,REC_STATUS='F'," +
								" MAKER_ID=?,MAKER_DATE=");
				// asesh space start1
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?, '"+dateFormatWithTime+"') + '' + substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
				bufInsSql.append(" WHERE DEAL_ID=? AND REC_STATUS='P'");

				
				if((CommonFunction.checkNull(vo.getAddSenAmount())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((vo.getAddSenAmount()).trim()).toString());
				
				if((CommonFunction.checkNull(vo.getApplicationdate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getApplicationdate()).trim());
				
				if((CommonFunction.checkNull(vo.getMakeRemarks())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getMakeRemarks()).trim());
				
				if((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getMakerId()).trim());
				
				if((CommonFunction.checkNull(vo.getMakerDate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getMakerDate()).trim());
				
			//Change start by Nishant					
//				if((CommonFunction.checkNull(vo.getLbxLoanNo())).trim().equalsIgnoreCase(""))
//					insertPrepStmtObject.addNull();
//				else
//					insertPrepStmtObject.addString((vo.getLbxLoanNo()).trim());
			//Change end by Nishant	
				if((CommonFunction.checkNull(vo.getLbxDealNo())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getLbxDealNo()).trim());
								
				insertPrepStmtObject.setSql(bufInsSql.toString());
				
				logger.info("IN modifyLimitEnhancement insert query1 ### "+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In modifyLimitEnhancement......................"+ status);
			
				bufInsSql=null;
				
		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;

	}

public ArrayList<LimitEnhancementVo> searchLimitMaker(LimitEnhancementVo vo,
		String recStatus, HttpServletRequest request) {
	
	ArrayList<LimitEnhancementVo> list = new ArrayList<LimitEnhancementVo>();
	
		logger.info("in searchLimitMaker Date Format: "+dateFormat);
		ArrayList searchlist=null;

		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		LimitEnhancementVo limitVo= (LimitEnhancementVo) vo;
		boolean appendSQL=false;
		try
		{
		StringBuffer bufInsSql=new StringBuffer();
		StringBuffer bufInsSqlTempCount = new StringBuffer();
		
		String dealId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo())).trim());
		String loanId= (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNo())).trim());
		logger.info("dealID:----"+dealId+"loanID:----"+loanId);

			bufInsSql.append("SELECT DE.LOAN_ID,DE.DEAL_ID,D.DEAL_NO,C.CUSTOMER_NAME,OLD_DEAL_SANCTION_AMOUNT,ADDITIONAL_SANCTION_AMOUNT,(select user_name from sec_user_m where user_id=DE.MAKER_ID),DE.NEW_LOAN_TYPE ");
			bufInsSql.append(" FROM cr_deal_enhancement DE INNER JOIN cr_deal_dtl D ON DE.DEAL_ID=D.DEAL_ID ");
			bufInsSql.append(" LEFT JOIN CR_DEAL_CUSTOMER_M C ON D.DEAL_CUSTOMER_ID =C.CUSTOMER_ID WHERE DE.REC_STATUS='"+StringEscapeUtils.escapeSql(recStatus)+"' ");
			bufInsSqlTempCount.append(" select count(*) FROM cr_deal_enhancement DE INNER JOIN cr_deal_dtl D ON DE.DEAL_ID=D.DEAL_ID ");
			bufInsSqlTempCount.append(" LEFT JOIN CR_DEAL_CUSTOMER_M C ON D.DEAL_CUSTOMER_ID =C.CUSTOMER_ID WHERE DE.REC_STATUS='"+StringEscapeUtils.escapeSql(recStatus)+"' ");
			
			if(recStatus.equalsIgnoreCase("P")){
				bufInsSql.append(" and DE.MAKER_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()))+"' and D.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getBranchId()))+"'");
				bufInsSqlTempCount.append(" and DE.MAKER_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()))+"'  and D.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getBranchId()))+"'");
			}else if (recStatus.equalsIgnoreCase("F")){
				bufInsSql.append(" and DE.MAKER_ID!='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()))+"' and D.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getBranchId()))+"'");
				bufInsSqlTempCount.append(" and DE.MAKER_ID!='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()))+"' and D.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getBranchId()))+"'");
			}

			if (!dealId.equals("") && !loanId.equals("")) {
				bufInsSql.append(" and DE.DEAL_ID = '" + dealId + "' AND DE.DEAL_LOAN_ID='" + loanId + "' ");
				bufInsSqlTempCount.append(" and DE.DEAL_ID = '" + dealId + "' AND DE.DEAL_LOAN_ID='" + loanId + "' ");
			}
			else if (!dealId.equals("")) {
				bufInsSql.append(" and DE.DEAL_ID = '" + dealId + "' ");
				bufInsSqlTempCount.append(" and DE.DEAL_ID = '" + dealId  + "' ");
			} 
			else if (!loanId.equals("")) {
				bufInsSql.append(" AND DE.DEAL_LOAN_ID='" + loanId + "' ");
				bufInsSqlTempCount.append(" AND DE.DEAL_LOAN_ID='" + loanId + "' ");
			}
			
		count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
		logger.info("bufInsSqlTempCount : "+bufInsSqlTempCount.toString());
		
//	if((dealId.trim()==null && loanId.trim()==null) || (dealId.trim().equalsIgnoreCase("") && loanId.trim().equalsIgnoreCase("")) || limitVo.getCurrentPageLink()>1)
//		{
		
			logger.info("current PAge Link no .................... "+limitVo.getCurrentPageLink());
		if(limitVo.getCurrentPageLink()>1)
		{
			startRecordIndex = (limitVo.getCurrentPageLink()-1)*no;
			endRecordIndex = no;
			logger.info("startRecordIndex .................... "+startRecordIndex);
			logger.info("endRecordIndex .................... "+endRecordIndex);
		}
		
		bufInsSql.append(" ORDER BY DE.DEAL_ID OFFSET ");
		bufInsSql.append(startRecordIndex);
		bufInsSql.append(" ROWS FETCH next ");
		bufInsSql.append(endRecordIndex);
		bufInsSql.append(" ROWS ONLY ");
		logger.info("Search searchLimitMaker query for SQL SERVER : " + bufInsSql.toString());

		//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));		
//		}
		logger.info("query : "+bufInsSql);
	     
		searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
		logger.info("searchlist" + searchlist.size());	
		
		bufInsSql=null;
		
		for(int i=0;i<searchlist.size();i++){
			ArrayList data=(ArrayList)searchlist.get(i);
			if(data!=null && data.size()>0)
			{
				
				limitVo = new LimitEnhancementVo();
				if(recStatus.equalsIgnoreCase("P"))	

					limitVo.setDealNo("<a href=newLimitEnhancement.do?method=openModifyLimit&loanId="+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(CommonFunction.checkNull(data.get(0)))).trim()
							+"&deaId="+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(CommonFunction.checkNull(data.get(1)))).trim()+ ">"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(2)))+"</a>");
				if(recStatus.equalsIgnoreCase("F"))
					limitVo.setDealNo("<a href=newLimitEnhancement.do?method=openModifyLimitForAuthor&loanId="+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(CommonFunction.checkNull(data.get(0)))).trim()
							+"&deaId="+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(CommonFunction.checkNull(data.get(1)))).trim() 
							+"&loanType="+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(CommonFunction.checkNull(data.get(7)))).trim()+ ">"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(2)))+"</a>");
				//limitVo.setLoanNo(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(3))).trim());
				limitVo.setCustomerName(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(3))).trim());
			//Nishant change starts
				if(!(CommonFunction.checkNull(data.get(4))).trim().equalsIgnoreCase(""))	
				{
					Number oldSenAmount =myFormatter.parse((CommonFunction.checkNull(data.get(4))).trim());
					limitVo.setOldSenAmount(myFormatter.format(oldSenAmount));
					//limitVo.setOldSenAmount(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(4))).trim());
				}
				if(!(CommonFunction.checkNull(data.get(5))).trim().equalsIgnoreCase(""))	
				{
					Number addSenAmount =myFormatter.parse((CommonFunction.checkNull(data.get(5))).trim());
					limitVo.setAddSenAmount(myFormatter.format(addSenAmount));
					//limitVo.setAddSenAmount(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(5))).trim());
				}
			//Nishant Change end
				limitVo.setMakerId((CommonFunction.checkNull(data.get(6)).trim()));
				limitVo.setTotalRecordSize(count);
				list.add(limitVo);
			}
		}
		
		
		dealId=null;
		loanId=null;
		
	}
		
	catch(Exception e)
	{
		e.printStackTrace();
	}

	return list;
}

public boolean modifyLimitForAuthor(Object ob,String DealID,String loanId,String loanType) {
	
	LimitEnhancementVo vo = (LimitEnhancementVo) ob;
	boolean status = false;
	logger.info("In modifyLimitForAuthor.....................................Dao Impl ");
	ArrayList qryList = new ArrayList();
	ArrayList qryList1 = new ArrayList();
	ArrayList qryList2 = new ArrayList();
	PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
	PrepStmtObject insertPrepStmtObject1 = new PrepStmtObject();
	PrepStmtObject insertPrepStmtObject2 = new PrepStmtObject();
	String Decision="";
	Decision=CommonFunction.checkNull(vo.getDecison());
	try {

			logger.info("In modifyLimitForAuthor");
			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSql1 = new StringBuffer();
			if(CommonFunction.checkNull(vo.getDecison()).equalsIgnoreCase("A"))
			{
				bufInsSql.append("UPDATE cr_deal_enhancement SET  AUTHOR_REMARKS=?,REC_STATUS=?,AUTHOR_ID=?,AUTHOR_DATE=");
				bufInsSql.append(dbo);
				bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append("DEAL_SANCTION_AMOUNT=(ISNULL(OLD_DEAL_SANCTION_AMOUNT,0)+ISNULL(ADDITIONAL_SANCTION_AMOUNT,0)),DEAL_SANCTION_VALID_TILL=ISNULL(DEAL_SANCTION_VALID_TILL,OLD_DEAL_SANCTION_VALID_TILL) ");
				bufInsSql.append(" WHERE DEAL_ID=? AND REC_STATUS='F' ");
			}else{
				bufInsSql.append("UPDATE cr_deal_enhancement SET  AUTHOR_REMARKS=?,REC_STATUS=?,AUTHOR_ID=?,AUTHOR_DATE=");
				bufInsSql.append(dbo);
				bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
				bufInsSql.append(" WHERE DEAL_ID=? AND REC_STATUS='F' ");
			}

			if((CommonFunction.checkNull(vo.getTextarea())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getTextarea()).trim());
			
			if((CommonFunction.checkNull(vo.getDecison())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getDecison()).trim());
			
			if((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getMakerId()).trim());
			
			if((CommonFunction.checkNull(vo.getMakerDate())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getMakerDate()).trim());
		//Nishant change starts
					
//			if((CommonFunction.checkNull(LoanID)).trim().equalsIgnoreCase(""))
//				insertPrepStmtObject.addNull();
//			else
//				insertPrepStmtObject.addString((LoanID).trim());
		//Nishant change end	
			if((CommonFunction.checkNull(DealID)).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((DealID).trim());
							
			insertPrepStmtObject.setSql(bufInsSql.toString());
			logger.info("IN insert modifyLimitForAuthor() insert query1 ### "+ insertPrepStmtObject.printQuery());
			qryList.add(insertPrepStmtObject);
			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			logger.info("In modifyLimitForAuthor......................"+ status);
			//Manish Change for New Loan Amount
			if(!CommonFunction.checkNull(loanId).equalsIgnoreCase("") && CommonFunction.checkNull(vo.getDecison()).equalsIgnoreCase("A"))
			{ 
				insertPrepStmtObject = new PrepStmtObject();
				bufInsSql=null;
				bufInsSql = new StringBuffer();
				if(CommonFunction.checkNull(vo.getDecison()).equalsIgnoreCase("A"))
				{
					if(CommonFunction.checkNull(loanType).equalsIgnoreCase("I"))   
					{
						bufInsSql.append("UPDATE cr_deal_enhancement SET  AUTHOR_REMARKS=?,REC_STATUS=?,AUTHOR_ID=?,AUTHOR_DATE= ");
						bufInsSql.append(dbo);
						bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
						bufInsSql.append("NEW_LOAN_AMOUNT=(ISNULL(OLD_LOAN_AMOUNT,0)+ISNULL(ADDITIONAL_LOAN_AMOUNT,0)),DEAL_SANCTION_VALID_TILL=ISNULL(DEAL_SANCTION_VALID_TILL,OLD_DEAL_SANCTION_VALID_TILL)" );
						bufInsSql.append(" WHERE LOAN_ID=? AND REC_STATUS='A' ");
					}
					else
					{
						bufInsSql.append("UPDATE cr_deal_enhancement SET  AUTHOR_REMARKS=?,REC_STATUS=?,AUTHOR_ID=?,AUTHOR_DATE= ");
						bufInsSql.append(dbo);
						bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
						bufInsSql.append("NEW_LOAN_AMOUNT=(ISNULL(OLD_LOAN_AMOUNT,0)-ISNULL(ADDITIONAL_LOAN_AMOUNT,0)),DEAL_SANCTION_VALID_TILL=ISNULL(DEAL_SANCTION_VALID_TILL,OLD_DEAL_SANCTION_VALID_TILL)" );
						bufInsSql.append(" WHERE LOAN_ID=? AND REC_STATUS='A' ");
					}
				}
					else{
						bufInsSql.append("UPDATE cr_deal_enhancement SET  AUTHOR_REMARKS=?,REC_STATUS=?,AUTHOR_ID=?,AUTHOR_DATE= ");
						bufInsSql.append(dbo);
						bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
						
						bufInsSql.append("UPDATE cr_deal_enhancement SET  AUTHOR_REMARKS=?,AUTHOR_ID=?,AUTHOR_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND)");
						bufInsSql.append("WHERE LOAN_ID=? AND REC_STATUS='F' ");
				}
					if((CommonFunction.checkNull(vo.getTextarea())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
					else
					insertPrepStmtObject.addString((vo.getTextarea()).trim());
					
					if((CommonFunction.checkNull(vo.getDecison())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getDecison()).trim());
					
					if((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getMakerId()).trim());
					
					if((CommonFunction.checkNull(vo.getMakerDate())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getMakerDate()).trim());
			
					if((CommonFunction.checkNull(loanId)).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((loanId).trim());
									
					insertPrepStmtObject.setSql(bufInsSql.toString());
					
					logger.info("IN insert modifyLimitForAuthor() insert query1 >>> "+ insertPrepStmtObject.printQuery());
					qryList1.add(insertPrepStmtObject);
					status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList1);
					logger.info("In modifyLimitForAuthor......................"+ status);
					String amtQry="SELECT NEW_LOAN_AMOUNT FROM cr_deal_enhancement WHERE LOAN_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()+"'";
					String newLoanamount = ConnectionDAO.singleReturn(amtQry);		
					bufInsSql1=null;
					bufInsSql1 = new StringBuffer();
					if(!CommonFunction.checkNull(newLoanamount).equalsIgnoreCase("") && !CommonFunction.checkNull(newLoanamount).equalsIgnoreCase("0"))
					{
						bufInsSql1.append("UPDATE cr_loan_dtl SET  LOAN_LOAN_AMOUNT=? WHERE LOAN_ID=?");					
						if((CommonFunction.checkNull(newLoanamount)).trim().equalsIgnoreCase(""))
							insertPrepStmtObject1.addNull();
						else
							insertPrepStmtObject1.addString((newLoanamount).trim());
						
						if((CommonFunction.checkNull(loanId)).trim().equalsIgnoreCase(""))
							insertPrepStmtObject1.addNull();
						else
							insertPrepStmtObject1.addString((loanId).trim());
						
						insertPrepStmtObject1.setSql(bufInsSql1.toString());
						logger.info("IN update modifyLimitForAuthor() update query1 >>>> "+ insertPrepStmtObject1.printQuery());
						qryList1.add(insertPrepStmtObject1);
					}
					StringBuffer bufInsSql2=null;
					bufInsSql2= new StringBuffer();
					
					if(CommonFunction.checkNull(vo.getDecison()).equalsIgnoreCase("A"))
					{
						bufInsSql2.append("UPDATE cr_deal_enhancement SET REC_STATUS=?  WHERE DEAL_ID=? AND REC_STATUS='F' ");
					
					
					if((CommonFunction.checkNull(vo.getDecison())).trim().equalsIgnoreCase("")) 
						insertPrepStmtObject2.addNull();
					else
						insertPrepStmtObject2.addString((vo.getDecison()).trim());
					
					if((CommonFunction.checkNull(DealID)).trim().equalsIgnoreCase(""))
						insertPrepStmtObject2.addNull();
					else
						insertPrepStmtObject2.addString((DealID).trim());
									
					insertPrepStmtObject2.setSql(bufInsSql2.toString());
					
					logger.info("IN  update modifyLimitForAuthor() update query1 !!!> "+ insertPrepStmtObject2.printQuery());
					qryList2.add(insertPrepStmtObject2);
					
					status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList2);
					logger.info("In modifyLimitForAuthor.Status....................."+ status);
						
					bufInsSql2=null;
				}
			}
			//Manish Ends
			bufInsSql=null;
			bufInsSql1=null;
		
			
	}  catch (Exception e) {
		e.printStackTrace();
	}

	logger.info("status:----"+status);
	if(CommonFunction.checkNull(vo.getDecison()).equalsIgnoreCase("A") && status!=false){
		try{
			StringBuffer bufInsSql1=new StringBuffer();
			StringBuffer bufInsSql2=new StringBuffer();
		//Nishant change starts
			bufInsSql1.append("select DEAL_SANCTION_DATE,DEAL_SANCTION_VALID_TILL,DEAL_SANCTION_AMOUNT from cr_deal_enhancement where DEAL_ID="+DealID+" ");
		//Nishant change end
			logger.info("get modifyLimitForAuthor Query: "+bufInsSql1);
			
			
			ArrayList list1 = ConnectionDAO.sqlSelect(bufInsSql1.toString());
			
			bufInsSql1=null;
			String dealSanDate= null;
			String dealSanValidTill= null;
			String addSanAmount = null;
			for(int i=0;i<list1.size();i++){
				//logger.info("list1"+list1.size());
				ArrayList header1=(ArrayList)list1.get(i);
				if(header1!=null && header1.size()>0)
				{
					dealSanDate=CommonFunction.checkNull(header1.get(0)).trim();
					dealSanValidTill=CommonFunction.checkNull(header1.get(1)).trim();
					addSanAmount=CommonFunction.checkNull(header1.get(2)).trim();
				}
	
				}
			
			logger.info("dealSanDate:----"+dealSanDate);
			logger.info("dealSanValidTill:----"+dealSanValidTill);
			logger.info("addSanAmount:-----"+addSanAmount);
			insertPrepStmtObject1 = null;
			insertPrepStmtObject1 =  new PrepStmtObject();
			
			bufInsSql2.append("UPDATE cr_deal_loan_dtl SET DEAL_SANCTION_AMOUNT=?,DEAL_SANCTION_DATE=?,DEAL_SANCTION_VALID_TILL=? WHERE DEAL_ID=?");
		//Nishant change starts
//			if((CommonFunction.checkNull(LoanID).trim().equalsIgnoreCase("")))
//			{
//				bufInsSql2.append("UPDATE cr_deal_loan_dtl SET DEAL_SANCTION_AMOUNT=?,DEAL_SANCTION_DATE=?,DEAL_SANCTION_VALID_TILL=? WHERE DEAL_ID=?");
//			}
//			else
//			{
//				bufInsSql2.append("UPDATE cr_deal_loan_dtl SET DEAL_SANCTION_AMOUNT=?,DEAL_SANCTION_DATE=?,DEAL_SANCTION_VALID_TILL=? WHERE DEAL_LOAN_ID=?");
//			}
		//Nishant change end	
			if((CommonFunction.checkNull(addSanAmount)).trim().equalsIgnoreCase(""))
				insertPrepStmtObject1.addNull();
			else
				insertPrepStmtObject1.addString(addSanAmount);
			
			if((CommonFunction.checkNull(dealSanDate)).trim().equalsIgnoreCase(""))
				insertPrepStmtObject1.addNull();
			else
				insertPrepStmtObject1.addString((dealSanDate).trim());
			
			if((CommonFunction.checkNull(dealSanValidTill)).trim().equalsIgnoreCase(""))
				insertPrepStmtObject1.addNull();
			else
				insertPrepStmtObject1.addString((dealSanValidTill).trim());
			
			if((CommonFunction.checkNull(DealID)).trim().equalsIgnoreCase(""))
				insertPrepStmtObject1.addNull();
			else
				insertPrepStmtObject1.addString((DealID).trim());
			
			insertPrepStmtObject1.setSql(bufInsSql2.toString());
			
			logger.info("IN insert modifyLimitForAuthor() insert query1 ### "+ insertPrepStmtObject1.printQuery());
			qryList1.add(insertPrepStmtObject1);
			
			ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList1);
		
			bufInsSql2=null;
			insertPrepStmtObject1=null;
			
	} catch (Exception e) {
		e.printStackTrace();
	}
}
	return status;

}

public boolean modifySaveLimit(Object ob) {
	
	LimitEnhancementVo vo = (LimitEnhancementVo) ob;
	boolean status = false;
	
	logger.info("In modifySaveLimit.....................................Dao Impl ");
	ArrayList qryList = new ArrayList();
	PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
	
	try {

			logger.info("In insert modifySaveLimit");
			StringBuffer bufInsSql = new StringBuffer();
			bufInsSql.append("UPDATE cr_deal_enhancement SET ADDITIONAL_SANCTION_AMOUNT=?,DEAL_SANCTION_VALID_TILL=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'" + dateFormat+ "'),MAKER_REMARKS=?,ADDITIONAL_LOAN_AMOUNT=?,NEW_LOAN_TYPE=? WHERE DEAL_ID=? AND REC_STATUS='P'");

			
			if((CommonFunction.checkNull(vo.getAddSenAmount())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getAddSenAmount()).trim()).toString());
			
			if((CommonFunction.checkNull(vo.getApplicationdate())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getApplicationdate()).trim());
			
			if((CommonFunction.checkNull(vo.getMakeRemarks())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getMakeRemarks()).trim());
			
									
//			if((CommonFunction.checkNull(vo.getLbxLoanNo())).trim().equalsIgnoreCase(""))
//				insertPrepStmtObject.addNull();
//			else
//				insertPrepStmtObject.addString((vo.getLbxLoanNo()).trim());
		//MANISH STARTS
			if((CommonFunction.checkNull(vo.getNewLoanAmount())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getNewLoanAmount()).trim()).toString());
			
			if((CommonFunction.checkNull(vo.getLoanAmountType())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getLoanAmountType()).trim());
		//Ends	
			if((CommonFunction.checkNull(vo.getLbxDealNo())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getLbxDealNo()).trim());
							
			insertPrepStmtObject.setSql(bufInsSql.toString());
			
			logger.info("IN insert modifySaveLimit() insert query1 ### "+ insertPrepStmtObject.printQuery());
			qryList.add(insertPrepStmtObject);
			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			logger.info("In modifySaveLimit......................"+ status);
		
			bufInsSql=null;
			
	} catch (Exception e) {
		e.printStackTrace();
	}

	return status;

}
@Override
public String getCheckDisbursalLoan(String lbxLoanNo) {
	
	logger.info("getCheckDisbursalLoan");
	StringBuilder query=new StringBuilder();
	query.append("SELECT COUNT(LOAN_ID) FROM CR_LOAN_DISBURSAL_DTL_TEMP WHERE LOAN_ID='"+lbxLoanNo+"' AND  REC_STATUS = 'P'");
	String status=ConnectionDAO.singleReturn(query.toString());
	query=null;
	if(CommonFunction.checkNull(status).equalsIgnoreCase("0") || CommonFunction.checkNull(status).equalsIgnoreCase(""))
	{
		query=new StringBuilder();
		query.append("SELECT COUNT(LOAN_ID) FROM CR_LOAN_DISBURSAL_DTL WHERE LOAN_ID='"+lbxLoanNo+"' AND  REC_STATUS  = 'F'");
		status=ConnectionDAO.singleReturn(query.toString());
		query=null;
	}
	lbxLoanNo=null;
	return  status;
}

@Override
public String getCheckTerminationLoan(String lbxLoanNo) {
	
	logger.info("getCheckTerminationLoan");
	StringBuilder query=new StringBuilder();
	query.append("SELECT COUNT(LOAN_ID) FROM CR_TERMINATION_DTL WHERE LOAN_ID='"+lbxLoanNo+"' AND  REC_STATUS = 'P'");
	String status=ConnectionDAO.singleReturn(query.toString());
	query=null;
	lbxLoanNo=null;
	return  status;
}
}	