package com.cm.daoImplMYSQL;


import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.cm.actions.UploadDocuments;
import com.cm.dao.CreditManagementDAO;
import com.cm.vo.*;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;
import com.cp.vo.ChargeVo;
import com.cp.vo.CollateralVo;
import com.cp.vo.DocumentsVo;
import com.cp.vo.RepayScheduleVo;
import com.gcd.VO.CorporateDetailsVO;
import com.connect.ConnectionDAOforEJB;
import com.cp.util.ValidationUtility;
import com.masters.vo.ProductMasterVo;

public class CreditManagementDAOImpl implements CreditManagementDAO {
	
	private static final Logger logger = Logger.getLogger(CreditManagementDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));

	//Amit Code Starts
	
	public ArrayList selectAsset(String loanId,String source) 
	{
		logger.info("in selectAsset() of CreditManagementDAOImpl.");
		CollateralVo vo = null;
		ArrayList<Object> list = new ArrayList<Object>();
		StringBuilder query = new StringBuilder(); 
		ArrayList bdetails = new ArrayList();
		ArrayList bdetails1 = new ArrayList();
		String assetTable="cr_asset_collateral_m";
		String loanTable="cr_loan_collateral_m";
		String invoiceTable="cr_asset_invoice_dtl";
		if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
		{
			assetTable="cr_asset_collateral_m_edit";
			loanTable="cr_loan_collateral_m_edit";
			invoiceTable="cr_asset_invoice_dtl_edit";
		}
		query.append("SELECT c.ASSET_ID,if(ASSET_COLLATERAL_CLASS='INVOICE','INVOICE',if(ASSET_COLLATERAL_CLASS='GOLD',c.ornament_type,c.ASSET_COLLATERAL_DESC)),sum(if(ASSET_COLLATERAL_CLASS='INVOICE',ifnull(e.INVOICE_AMT,0),c.ASSET_COLLATERAL_VALUE))ASSETCOLLATERALVALUE,c.ASSET_TYPE, ASSET_COLLATERAL_CLASS " +
				" from "+assetTable+" c " +
				" left join "+loanTable+" d on c.ASSET_ID=d.ASSETID " +
				" left join "+invoiceTable+" e on e.ASSET_ID=c.ASSET_ID " +
				" where d.LOAN_ID='"+ loanId + "' group by c.asset_id ");
		logger.info("in selectAsset() of CreditManagementDAOImpl.  Asset query  :  " + query.toString());				
		try {
			bdetails = ConnectionDAO.sqlSelect(query.toString());
			int size = bdetails.size();
			logger.info("CollateralDetailsAll" + size);
			for (int i = 0; i < size; i++) {
				bdetails1 = (ArrayList) bdetails.get(i);
				if (bdetails1.size() > 0) {
					vo = new CollateralVo();
					vo.setAssetsId((CommonFunction.checkNull(bdetails1.get(0))
							.trim()));
					vo.setAssetsCollateralDesc((CommonFunction.checkNull(
							bdetails1.get(1)).trim()));
					vo.setAssetsCollateralValue((CommonFunction.checkNull(
							bdetails1.get(2)).trim()));
					vo.setColltype2((CommonFunction.checkNull(bdetails1.get(3))
							.trim()));
					vo.setColltype1((CommonFunction.checkNull(bdetails1.get(4))
							.trim()));
				}

				list.add(vo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			vo = null;
			query = null; 
			bdetails= null;
			bdetails1 = null;
			loanId = null;
			assetTable= null;
			loanTable= null;
			source = null;
		}
		return list;
	}

	
	
	



		public String getGroupExposureLimitCheck(DisbursalMakerVO vo)
		{
			logger.info("Inside getGroupExposureLimitCheck .....................DAOImpl");
			String balancePrincipalStr[]=null;
			String overduePrincipalStr[]=null;
			BigDecimal balPrin = null;
			BigDecimal overdueprin=null;
			BigDecimal balancePrincipal=new BigDecimal("0.00");
			BigDecimal overduePrincipal=new BigDecimal("0.00");
			StringBuilder query1 = new StringBuilder();
			StringBuilder groupExposureLimitStr = new StringBuilder();
			BigDecimal groupExposureLimit = null;
			StringBuilder query2 = new StringBuilder();
			StringBuilder groupId = new StringBuilder();
			//String groupId = "";
			//String query3 = "";
			StringBuilder query3 = new StringBuilder();
			ArrayList customerId = null;
			String[] custId= null;
			ArrayList data= null;
			ArrayList detailList = null;
			String status="";
			try
			{
				query1.append("select group_exposure_limit from gcd_group_m where group_id="+
									"(select group_id from gcd_customer_m where customer_id="+
											"(select loan_customer_id from cr_loan_dtl where loan_id=" +
													"'"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID()))+"'))");
				groupExposureLimitStr.append(ConnectionDAO.singleReturn(query1.toString()));
				groupExposureLimit = new BigDecimal(groupExposureLimitStr.toString());
				logger.info("group Exposure Limit: "+groupExposureLimit);
				query2.append("select group_id from gcd_customer_m where customer_id="+
								"(select loan_customer_id from cr_loan_dtl where loan_id=" +
									"'"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID()))+"')");
				groupId.append(ConnectionDAO.singleReturn(query2.toString()));
				
				query3.append("select customer_id from gcd_customer_m where GROUP_ID='"+groupId+"' and customer_status='A'");
				customerId = ConnectionDAO.sqlSelect(query3.toString());
				custId=new String[customerId.size()];
				int size = customerId.size();
				for(int i=0;i<size;i++)
				{
		            data=(ArrayList)customerId.get(i);
		            if(data.size()>0)
		            {
		            	custId[i]=data.get(0).toString();
		            	
		            }
		        }
				
				int length = custId.length;;
				for(int p=0; p<length;p++)
				{
					StringBuilder query4 = new StringBuilder();
					
					query4.append("select LOAN_BALANCE_PRINCIPAL,LOAN_OVERDUE_PRINCIPAL from cr_loan_dtl where LOAN_CUSTOMER_ID='"+custId[p]+"'" +
							" and rec_status='A'");
					logger.info("query4: "+query4.toString());
					detailList = ConnectionDAO.sqlSelect(query4.toString());
				
					query4 = null;
					size = detailList.size();
					for(int j=0;j<size;j++)
					{
						balancePrincipalStr= new String[detailList.size()];
						overduePrincipalStr= new String[detailList.size()];
			            data=(ArrayList)detailList.get(j);
			            if(data.size()>0)
			            {
			            	balancePrincipalStr[j] = (CommonFunction.checkNull(data.get(0)).toString().trim());
			            	if(balancePrincipalStr[j].equalsIgnoreCase(""))
			            	{
			            		balPrin = new BigDecimal("0.00");
			            	}
			            	else
			            		balPrin = new BigDecimal(balancePrincipalStr[j]);
			            	overduePrincipalStr[j] = (CommonFunction.checkNull(data.get(1)).toString().trim());
			            	if(overduePrincipalStr[j].equalsIgnoreCase(""))
			            	{
			            		overdueprin = new BigDecimal("0.00");
			            	}
			            	else
			            		overdueprin = new BigDecimal(overduePrincipalStr[j]);
			            	balancePrincipal = balancePrincipal.add(balPrin);
			            	overduePrincipal = overduePrincipal.add(overdueprin);
			            }
			            
					}
				}
				BigDecimal principalOutstanding =  balancePrincipal.add(overduePrincipal);
				BigDecimal disbursalAmt = new BigDecimal(myFormatter.parse(CommonFunction.checkNull(vo.getDisbursalAmount())).toString());
				
				int res = principalOutstanding.add(disbursalAmt).compareTo(groupExposureLimit);
//				logger.info("principalOutstanding: "+principalOutstanding);
//				logger.info("disbursalAmt: "+disbursalAmt);
//				logger.info("groupExposureLimit: "+groupExposureLimit);
//				logger.info("res: "+res);
				if(res>0)
				{
					status="groupExposure";
				}
				logger.info("Group Exposure Limit Status: "+status);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				balPrin = null;
				overdueprin=null;
				balancePrincipal= null;
				overduePrincipal= null;
				query1 = null;
				groupExposureLimitStr = null;
				groupExposureLimit = null;
				query2 = null;
				groupId = null;
				query3 = null;
				customerId = null;
				custId= null;
				data= null;
				detailList = null;
				vo = null;
				balancePrincipalStr =null;
				overduePrincipalStr =null;
			}
			
			return status;
		}
		
		public String getCustomerExposureLimitCheck(DisbursalMakerVO vo)
		{
			logger.info("Inside getCustomerExposureLimitCheck .....................DAOImpl");
			String balancePrincipalStr[]=null;
			String overduePrincipalStr[]=null;
			BigDecimal balPrin=null;
			BigDecimal overdueprin=null;
			BigDecimal balancePrincipal=new BigDecimal("0.00");
			BigDecimal overduePrincipal=new BigDecimal("0.00");
			StringBuilder query1 = new StringBuilder();
			StringBuilder customerExposureLimitStr = new StringBuilder();
			//String customerExposureLimitStr = "";
			//String query2 = "";
			StringBuilder query2 = new StringBuilder();
			StringBuilder customerId = new StringBuilder();
			//String customerId ="";
			//String query3 ="";
			StringBuilder query3 = new StringBuilder();
			StringBuilder schemeId = new StringBuilder();
			//String schemeId = "";
			//String query4 = "";
			StringBuilder query4 = new StringBuilder();
			StringBuilder productId = new StringBuilder();
			//String productId = "";
			//String query5 = "";
			StringBuilder query5 = new StringBuilder();
			ArrayList loanList = null;
			ArrayList data= null;
			BigDecimal principalOutstanding =  null;
			BigDecimal disbursalAmt = null;
			
			String status="";
			try
			{
				query1.append("select customer_exposure_limit from cr_scheme_m where scheme_id="+
								"(select loan_scheme from cr_loan_dtl where loan_id=" +
									"'"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID()))+"')");
				customerExposureLimitStr.append(ConnectionDAO.singleReturn(query1.toString()));
				BigDecimal customerExposureLimit = new BigDecimal(customerExposureLimitStr.toString());
				
				query2.append("select loan_customer_id from cr_loan_dtl where loan_id=" +
									"'"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID()))+"'");
				customerId.append(ConnectionDAO.singleReturn(query2.toString()));
				
				query3.append("select loan_scheme from cr_loan_dtl where loan_id="+
									"'"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID()))+"'");
				schemeId.append(ConnectionDAO.singleReturn(query3.toString()));
				
				query4.append("select loan_product from cr_loan_dtl where loan_id="+
									"'"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID()))+"'");
				productId.append(ConnectionDAO.singleReturn(query4.toString()));
				
				query5.append("select LOAN_BALANCE_PRINCIPAL,LOAN_OVERDUE_PRINCIPAL from cr_loan_dtl where loan_customer_id='"+customerId+"'" +
						" and loan_scheme='"+schemeId+"' and loan_product='"+productId+"' and rec_status='A'");
				loanList = ConnectionDAO.sqlSelect(query5.toString());
				int size = loanList.size();
				for(int j=0;j<size;j++)
				{
		            data=(ArrayList)loanList.get(j);
		            balancePrincipalStr= new String[loanList.size()];
					overduePrincipalStr= new String[loanList.size()];
		            if(data.size()>0)
		            {
		            	balancePrincipalStr[j] = (CommonFunction.checkNull(data.get(0)).toString().trim());
		            	if(balancePrincipalStr[j].equalsIgnoreCase(""))
		            		balPrin = new BigDecimal("0.00");
		            	else
		            		balPrin = new BigDecimal(balancePrincipalStr[j]);
		            	overduePrincipalStr[j] = (CommonFunction.checkNull(data.get(1)).toString().trim());
		            	if(overduePrincipalStr[j].equalsIgnoreCase(""))
		            		overdueprin = new BigDecimal("0.00");
		            	else
		            		overdueprin = new BigDecimal(overduePrincipalStr[j]);
		            	balancePrincipal = balancePrincipal.add(balPrin);
		            	overduePrincipal = overduePrincipal.add(overdueprin);
		            }
				}
				principalOutstanding =  balancePrincipal.add(overduePrincipal);
				disbursalAmt = new BigDecimal(myFormatter.parse(CommonFunction.checkNull(vo.getDisbursalAmount())).toString());
				
				int res = principalOutstanding.add(disbursalAmt).compareTo(customerExposureLimit);
//				logger.info("principalOutstanding: "+principalOutstanding);
//				logger.info("disbursalAmt: "+disbursalAmt);
//				logger.info("customerExposureLimit: "+customerExposureLimit);
//				logger.info("res: "+res);
				if(res>0)
				{
					status="customerExposure";
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				balPrin=null;
				overdueprin=null;
				balancePrincipal = null;
				overduePrincipal=  null;
				query1 =  null;
				customerExposureLimitStr =  null;
				query2 =  null;
				customerId =  null;
				query3 =  null;
				schemeId =  null;
				query4 =  null;
				query5 =  null;
				productId =  null;
				loanList = null;
				data= null;
				principalOutstanding =  null;
				disbursalAmt = null;
				vo = null;
				balancePrincipalStr =null;
				overduePrincipalStr =null;
			}
			return status;
		}

//public ArrayList<Object> getBusinessPartnerTypeList()
//	    {
//			logger.info("Inside getBusinessPartnerTypeList.................DAOImpl");
//	    	ArrayList<Object> list=new ArrayList<Object>();
//			try{
//			String query="select g.VALUE,g.DESCRIPTION from generic_master g,generic_master_keys h where h.GENERIC_KEY='BPTYPE' and g.GENERIC_KEY=h.GENERIC_KEY";
//			ArrayList businessPartnerTypeList = ConnectionDAO.sqlSelect(query);
//			logger.info("getBusinessPartnerTypeList "+businessPartnerTypeList.size());
//			for(int i=0;i<businessPartnerTypeList.size();i++){
//				logger.info("getBusinessPartnerTypeList...Outer FOR loop "+CommonFunction.checkNull(businessPartnerTypeList.get(i)).toString());
//				ArrayList data=(ArrayList)businessPartnerTypeList.get(i);
//				if(data.size()>0)	{
//				CodeDescVo buyerVo=new CodeDescVo();
//				buyerVo.setId((CommonFunction.checkNull(data.get(0))).trim());
//				buyerVo.setName((CommonFunction.checkNull(data.get(1))).trim());
//				list.add(buyerVo);
//			}
//			}
//			}catch(Exception e){
//				e.printStackTrace();
//			}
//			
//			return list;
//		}

public ArrayList<RepayScheduleVo> getNewRepaySchedulePartPayment(RepayScheduleVo vo, String loanId, String reschId) 
{
	logger.info("in getNewRepaySchedulePartPayment() method of CreditManagementDAOImpl");
	ArrayList mainlist=new ArrayList();
	ArrayList list=new ArrayList();
	ArrayList subList=null;
	StringBuilder bussIrrQ = new StringBuilder();
	StringBuilder bussIrr = new StringBuilder();
	StringBuilder query = new StringBuilder(); 
	RepayScheduleVo repvo = null;
	try
	{
		bussIrrQ.append("select deal_irr2 from cr_deal_loan_dtl where DEAL_LOAN_ID=(select LOAN_DEAL_LOAN_ID from cr_loan_dtl where LOAN_ID="+loanId+")");
		logger.info("Query in getRepaySchFieldsDetail--DEAL_IRR2---" + bussIrrQ);
		bussIrr.append(ConnectionDAO.singleReturn(bussIrrQ.toString()));
		query.append("select R_Seq_No,DATE_FORMAT(R_Due_Date,'"+dateFormat+"'),R_Instl_Amount,R_Prin_Comp,R_Int_Comp,R_EXCESS_Int_Comp," +
				" if(R_ADV_FLAG='Y','YES','NO'),R_Prin_OS from Repay_Temp where R_LOAN_ID="+loanId+" order by R_DUE_DATE,R_ORG_SEQ_NO");
		logger.info("Query in getNewRepayScheduleDeferral-----" + query.toString());
		mainlist = ConnectionDAO.sqlSelect(query.toString());
		logger.info("mainlist size: "+mainlist.size());
		for (int i = 0; i < mainlist.size(); i++) 
		{
			subList = (ArrayList) mainlist.get(i);
			repvo = new RepayScheduleVo();
			if (subList.size() > 0)
			{
				repvo.setInstNo((CommonFunction.checkNull(subList.get(0)).trim()));
				repvo.setDueDate((CommonFunction.checkNull(subList.get(1)).trim()));
				if(!CommonFunction.checkNull(subList.get(2)).trim().equalsIgnoreCase(""))
		    	{
			    	Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(2))).trim());  
			    	repvo.setInstAmount(myFormatter.format(reconNum));
		    	}
				if(!CommonFunction.checkNull(subList.get(3)).trim().equalsIgnoreCase(""))
		    	{
			    	Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(3))).trim());  
			    	repvo.setPrinciple(myFormatter.format(reconNum));
		    	}
				if(!CommonFunction.checkNull(subList.get(4)).trim().equalsIgnoreCase(""))
		   		{
		    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(4))).trim());  
		    		repvo.setInstCom(myFormatter.format(reconNum));
		   		}
				if(!CommonFunction.checkNull(subList.get(5)).trim().equalsIgnoreCase(""))
		   		{
		    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(5))).trim());  
		    		repvo.setExcess(myFormatter.format(reconNum));
		   		}
			}
			repvo.setAdvFlag((CommonFunction.checkNull(subList.get(6)).trim()));
			if(!CommonFunction.checkNull(subList.get(7)).trim().equalsIgnoreCase(""))
			{
				Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(7))).trim());  
				repvo.setPrinOS(myFormatter.format(reconNum));
			}
			repvo.setDealIRR2(bussIrr.toString());
			list.add(repvo);
		}		
	}
	catch (Exception e) 
	{e.printStackTrace();}
	finally
	{
		bussIrrQ = null;
		query = null;
		mainlist.clear();
		mainlist= null;
		bussIrr= null;
		loanId= null;
		reschId= null;
		repvo= null;
		subList.clear();
		subList=null;
		vo=null;
	}
	return list;
}

//
//public ArrayList<RepayScheduleVo> getNewRepaySchedulePartPayment(RepayScheduleVo vo, String loanId, String reschId) 
//{
//	logger.info("In getNewRepaySchedulePartPayment DAOImpl");
//	String status="";
////	CallableStatement cst=null;
//    Connection con=ConnectionDAO.getConnection();
//	ArrayList<RepayScheduleVo> list = new ArrayList<RepayScheduleVo>();
//	ArrayList mainlist = new ArrayList();
//	ArrayList subList = new ArrayList();
//	 ArrayList qryList = new ArrayList();
//	    ArrayList<Object> in =new ArrayList<Object>();
//		ArrayList<Object> out =new ArrayList<Object>();
//		ArrayList outMessages = new ArrayList();
//		String s1="";
//		String s2="";
//		String s3="";
//		String s4="";
//		String s5="";
//		String s6="";
////	String chkQuery = "select count(INSTALLMENT_PLAN_ID) from cr_resch_installment_plan where " +
////	" resch_id="+(CommonFunction.checkNull(reschId))+" and" +
////	" loan_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId))+"";
////	String chkStatusStr = ConnectionDAO.singleReturn(chkQuery);
////	int chkStatus = Integer.parseInt(chkStatusStr);
////	logger.info("chkStatus: "+chkStatus);
////	if(chkStatus>0)
////	{
//		try
//		{
//
////			con.setAutoCommit(false);
////	        cst=con.prepareCall("call Part_Prepayment_Author(?,?,STR_TO_DATE(?,'"+dateFormat+"'),?,?,?,?,?,?,?,?,?)");
//			in.add(Integer.parseInt(CommonFunction.checkNull(vo.getCompanyId()).trim()));
//			in.add(Integer.parseInt(CommonFunction.checkNull(reschId).trim()));
//	        String date=CommonFunction.changeFormat(CommonFunction.checkNull(vo.getAuthorDate()).trim());
//		    if(date != null)
//		    	in.add(date);
////	        cst.setString(3, CommonFunction.checkNull(vo.getAuthorDate()).trim());
//		    in.add(CommonFunction.checkNull(vo.getAuthorId()).trim());
//		    in.add("T");
//		    in.add("");
////	        cst.registerOutParameter(7, Types.CHAR );
////	        cst.registerOutParameter(8, Types.CHAR );
////	        cst.registerOutParameter(9, Types.CHAR );
////	        cst.registerOutParameter(10, Types.CHAR );
////	        cst.registerOutParameter(11, Types.CHAR );
////	        cst.registerOutParameter(12, Types.CHAR );
////	        cst.executeUpdate();
////	        String s1= cst.getString(11);
////	        String s2 = cst.getString(12);
//	        out.add(s1);
//		    out.add(s2);
//		    out.add(s3);
//		    out.add(s4);
//		    out.add(s5);
//		    out.add(s6);
//		    outMessages=(ArrayList) ConnectionDAO.callSP("Part_Prepayment_Author",in,out);
//		    s3=CommonFunction.checkNull(outMessages.get(0));//7
//		    s4=CommonFunction.checkNull(outMessages.get(1));//8
//		    s5=CommonFunction.checkNull(outMessages.get(2));//9
//		    s6=CommonFunction.checkNull(outMessages.get(3));//10
//		    s1=CommonFunction.checkNull(outMessages.get(4));//11
//		    s2=CommonFunction.checkNull(outMessages.get(5));//12
//		  
//
//	        logger.info("s1: "+s1);
//	        logger.info("s2: "+s2);
//	        if(s1.equalsIgnoreCase("S"))
//	        {
//	        	status=s1;
//	        	
//				String bussIrrQ="select deal_irr2 from cr_deal_loan_dtl where DEAL_ID=(select LOAN_DEAL_ID from cr_loan_dtl where LOAN_ID="+loanId+")";
//				logger.info("Query in getRepaySchFieldsDetail--DEAL_IRR2---" + bussIrrQ);
//				String bussIrr = ConnectionDAO.singleReturn(bussIrrQ);
//				
//				String query="select R_Seq_No,DATE_FORMAT(R_Due_Date,'"+dateFormat+"'),R_Instl_Amount,R_Prin_Comp,R_Int_Comp,R_EXCESS_Int_Comp," +
//				" if(R_ADV_FLAG='Y','YES','NO'),R_Prin_OS from Repay_Temp where R_LOAN_ID="+loanId+" order by R_DUE_DATE,R_ORG_SEQ_NO";
//				logger.info("Query in getNewRepayScheduleDeferral-----" + query);
//				int j=1;
//				Statement st = con.createStatement();
//				ResultSet rs = st.executeQuery(query);
//	
//	            //getting the no of fields selected
//	            int noOfFields = (rs.getMetaData()).getColumnCount();
//	
//	            //counter which will be incremented for the no of fields
//	            //check whether the records have been returned
//	            
//	
//	            while (rs.next()) {
//	                int counter = 1; //this will restart the count every time from 1
//	
//	                //change made ..arraylist to beinitialized within the rs.next()
//	                ArrayList records = new ArrayList();
//	
//	                while (counter <= noOfFields) {
//	                    //adding the column values in the arraylist
//	                    records.add(rs.getString(counter));
//	                    counter++;
//	                }
//	
//	                //adding the arraylist to the vector
//	                mainlist.add(records);
//	                
//	            } //end of rs.next()
//				logger.info("mainlist size: "+mainlist.size());
//				
//				for (int i = 0; i < mainlist.size(); i++) 
//				{
//					
//					subList = (ArrayList) mainlist.get(i);
//					
//					RepayScheduleVo repvo = new RepayScheduleVo();
//					if (subList.size() > 0)
//					{
//						repvo.setInstNo((CommonFunction.checkNull(subList.get(0)).trim()));
//						repvo.setDueDate((CommonFunction.checkNull(subList.get(1)).trim()));
//						
//						if(!CommonFunction.checkNull(subList.get(2)).trim().equalsIgnoreCase(""))
//			    		{
//				    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(2))).trim());  
//				    		repvo.setInstAmount(myFormatter.format(reconNum));
//			    		}
//						
//						if(!CommonFunction.checkNull(subList.get(3)).trim().equalsIgnoreCase(""))
//			    		{
//				    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(3))).trim());  
//				    		repvo.setPrinciple(myFormatter.format(reconNum));
//			    		}
//			
//						if(!CommonFunction.checkNull(subList.get(4)).trim().equalsIgnoreCase(""))
//			    		{
//				    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(4))).trim());  
//				    		repvo.setInstCom(myFormatter.format(reconNum));
//			    		}
//			
//						if(!CommonFunction.checkNull(subList.get(5)).trim().equalsIgnoreCase(""))
//			    		{
//				    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(5))).trim());  
//				    		repvo.setExcess(myFormatter.format(reconNum));
//			    		}
//					}
//						repvo.setAdvFlag((CommonFunction.checkNull(subList.get(6)).trim()));
//						if(!CommonFunction.checkNull(subList.get(7)).trim().equalsIgnoreCase(""))
//			    		{
//				    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(7))).trim());  
//				    		repvo.setPrinOS(myFormatter.format(reconNum));
//			    		}
//						repvo.setDealIRR2(bussIrr);
//						
//						
//						if(!CommonFunction.checkNull(s3).trim().equalsIgnoreCase(""))
//	    	    		{
//
//		    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(s3)).trim());  
//
//		    	    		repvo.setFinalRate(myFormatter.format(reconNum));
//	    	    		}
//						
//						if(!CommonFunction.checkNull(s4).trim().equalsIgnoreCase(""))
//	    	    		{
//
//		    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(s4)).trim());  
//
//		    	    		repvo.setEffectiveRate(myFormatter.format(reconNum));
//	    	    		}
//						
//						if(!CommonFunction.checkNull(s5).trim().equalsIgnoreCase(""))
//	    	    		{
//
//		    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(s5)).trim());  
//
//		    	    		repvo.setMktIRR1(myFormatter.format(reconNum));
//	    	    		}
//						
//						if(!CommonFunction.checkNull(s6).trim().equalsIgnoreCase(""))
//	    	    		{
//
//		    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(s6)).trim());  
//
//		    	    		repvo.setMktIRR2(myFormatter.format(reconNum));
//	    	    		}
//						
//					list.add(repvo);
//				}
//				con.commit();
//	        }
//	        else if(s1.equalsIgnoreCase("E"))
//	        {
//	        	con.rollback();
//	        	status = s2;
//	        }
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		finally
//		{
//			try {
//				con.close();
////				cst.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//			
//		}
////	}
//	
//	return list;
//}

public ArrayList generateReschCharges(String lbxLoanNoHID, double partPaymentAmt,String reschDate, int functionId, int companyId)
{
	ArrayList<Object> in =new ArrayList<Object>();
	ArrayList<Object> out =new ArrayList<Object>();
	ArrayList outMessages = new ArrayList();
	//String s1="";
	//String s2="";
	//String s3="";
	//String s4="";
	StringBuilder s1 = new StringBuilder();
	StringBuilder s2 = new StringBuilder();
	StringBuilder s3 = new StringBuilder();
	StringBuilder s4 = new StringBuilder();
	logger.info("Inside generateReschCharges.......CMDaoImpl");
//	CallableStatement cst=null;
//    Connection con=ConnectionDAO.getConnection();
    String reschType="U";
    ArrayList qryList = new ArrayList();
    if(functionId==4000806)
    {
    	reschType="R";
    }
    if(functionId==4000816)
    {
    	reschType="D";
    }
    if(functionId==4000826)
    {
    	reschType="P";
    }
	try
	{

//		con.setAutoCommit(false);
//      cst=con.prepareCall("call Get_Resch_Charges(?,?,?,STR_TO_DATE(?,'"+dateFormat+"'),?,?,?,?,?)");
		in.add(companyId);
		in.add(Integer.parseInt(CommonFunction.checkNull(lbxLoanNoHID).trim()));
		in.add(CommonFunction.checkNull(reschType).trim());
		 String date=CommonFunction.changeFormat(CommonFunction.checkNull(reschDate).trim());
  	    if(date != null)
  	    	in.add(date);
//		in.add(CommonFunction.checkNull(reschDate).trim());
		in.add(partPaymentAmt);
        out.add(s1.toString());
	    out.add(s2.toString());
	    out.add(s3.toString());
	    out.add(s4.toString());
	    
        outMessages=(ArrayList) ConnectionDAO.callSP("Get_Resch_Charges",in,out);

        String reschCh =CommonFunction.checkNull(outMessages.get(0));
        String gapInt = CommonFunction.checkNull(outMessages.get(1));
        s1.append(CommonFunction.checkNull(outMessages.get(2)));
        s2.append(CommonFunction.checkNull(outMessages.get(3)));

//        logger.info("In generateReschCharges : partPaymentAmt:==>> "+partPaymentAmt);
//        logger.info("In generateReschCharges : s1:==>> "+s1);
//        logger.info("In generateReschCharges : s2:==>> "+s2);
//        logger.info("In generateReschCharges : reschCh:==>> "+reschCh);
//        logger.info("In generateReschCharges : gapInt:==>> "+gapInt);
//        logger.info("In generateReschCharges : reschType:==>>"+reschType);
        if((s1.toString()).equalsIgnoreCase("S"))
        {
//			con.commit();
        	logger.info("Peoc Status: Successful");
        	// For Part Prepayment
        	if(reschType.equalsIgnoreCase("P"))
            {
        		PartPrePaymentMakerVO vo = new PartPrePaymentMakerVO();

    	        if((CommonFunction.checkNull(reschCh).trim()).equalsIgnoreCase(""))
    	        {
    	        	logger.info("In generateReschCharges : reschedule charges has no value ");
    				vo.setReschCharges("0");
    				
    	        }
    			else
    			{
    				logger.info("In generateReschCharges : reschedule charges has  value ");
    				Number reschCharges = myFormatter.parse((CommonFunction.checkNull(reschCh).trim()));

    				vo.setReschCharges(myFormatter.format(reschCharges));
    			}
    	        

    	        if((CommonFunction.checkNull(gapInt).trim()).equalsIgnoreCase(""))

    				vo.setInterestForGapPeriod("0");
    			else
    			{

    				Number gapInterest = myFormatter.parse((CommonFunction.checkNull(gapInt).trim()));

    				vo.setInterestForGapPeriod(myFormatter.format(gapInterest));
    			}
    	        qryList.add(vo);
    	        vo = null;
            }
        	
        	// For Deferral
        	if(reschType.equalsIgnoreCase("D"))
            {
        		DeferralMakerVo vo = new DeferralMakerVo();

    	        if((CommonFunction.checkNull(reschCh).trim()).equalsIgnoreCase(""))
    				vo.setReschCharges("0");
    			else
    			{

    				Number reschCharges = myFormatter.parse((CommonFunction.checkNull(reschCh).trim()));

    				vo.setReschCharges(myFormatter.format(reschCharges));
    			}
    	        

    	        if((CommonFunction.checkNull(gapInt).trim()).equalsIgnoreCase(""))

    				vo.setInterestForGapPeriod("0");
    			else
    			{

    				Number gapInterest = myFormatter.parse((CommonFunction.checkNull(gapInt).trim()));

    				vo.setInterestForGapPeriod(myFormatter.format(gapInterest));
    			}
    	        qryList.add(vo);
    	        vo = null;
            }
        	
        	// For RePricing
        	if(reschType.equalsIgnoreCase("R"))
            {
        		RepricingMakerVo vo = new RepricingMakerVo();

    	        if((CommonFunction.checkNull(reschCh).trim()).equalsIgnoreCase(""))

    				vo.setReschCharges("0");
    			else
    			{

    				Number reschCharges = myFormatter.parse((CommonFunction.checkNull(reschCh).trim()));

    				vo.setReschCharges(myFormatter.format(reschCharges));
    			}
    	        

    	        if((CommonFunction.checkNull(gapInt).trim()).equalsIgnoreCase(""))

    				vo.setInterestForGapPeriod("0");
    			else
    			{

    				Number gapInterest = myFormatter.parse((CommonFunction.checkNull(gapInt).trim()));

    				vo.setInterestForGapPeriod(myFormatter.format(gapInterest));
    			}
    	        qryList.add(vo);
    	        vo = null;
            }
        	
        	// For Due Date
        	if(reschType.equalsIgnoreCase("U"))
            {
        		RepricingMakerVo vo = new RepricingMakerVo();

    	        if((CommonFunction.checkNull(reschCh).trim()).equalsIgnoreCase(""))

    				vo.setReschCharges("0.00");
    			else
    			{

    				Number reschCharges = myFormatter.parse((CommonFunction.checkNull(reschCh).trim()));

    				vo.setReschCharges(myFormatter.format(reschCharges));
    			}
    	        

    	        if((CommonFunction.checkNull(gapInt).trim()).equalsIgnoreCase(""))

    				vo.setInterestForGapPeriod("0.00");
    			else
    			{

    				Number gapInterest = myFormatter.parse((CommonFunction.checkNull(gapInt).trim()));

    				vo.setInterestForGapPeriod(myFormatter.format(gapInterest));
    			}
    	        qryList.add(vo);
    	        vo = null;
            }
        }
        else if((s1.toString()).equalsIgnoreCase("E"))
        {
//        	con.rollback();
        	logger.info("Proc Status: "+s2);
        }
        date = null;
        reschCh = null;
        gapInt = null;
	}
	catch (Exception e) {
//		try {
//			con.rollback();
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
		e.printStackTrace();
	}
	finally
	{
		s1 = null;
		s2 = null;
		s3 = null;
		s4 = null;
		in = null;
		out = null;
		outMessages = null;
		reschType = null;
		lbxLoanNoHID = null;
		reschDate = null;
	}
	
return qryList;
}

// *********************** Deferral Starts *******************************

public String deferralMakerFeasibility(String lbxLoanNoHID, String effBusinessDate)
{
	String retStr = "";
	String loanId = StringEscapeUtils.escapeSql(lbxLoanNoHID);
	StringBuilder query1 = new StringBuilder();
	StringBuilder query2 = new StringBuilder();
	StringBuilder query3 = new StringBuilder();
	StringBuilder query4 = new StringBuilder();
	StringBuilder query5 = new StringBuilder();
	StringBuilder query6 = new StringBuilder();
	StringBuilder lockinPeriodValid = new StringBuilder();
	StringBuilder totalDeferralAllowed = new StringBuilder();
	StringBuilder minGapSuccDeferralValid = new StringBuilder();
	StringBuilder businessYearStr = new StringBuilder();
	StringBuilder numDeferralAllowedYrStr = new StringBuilder();

	query1.append("select if (DATE_ADD(ld.loan_disbursal_date,INTERVAL s.defr_lockin_period MONTH)" +
			" >= STR_TO_DATE('"+StringEscapeUtils.escapeSql(effBusinessDate)+"','%d-%m-%Y') , 'yes', 'no') as lockin_period_valid" +
			" from cr_scheme_m s, cr_loan_dtl ld left join cr_resch_dtl r on ld.loan_id=r.loan_id and r.resch_type='D'" +
			" where ld.loan_scheme=s.scheme_id and ld.loan_id="+loanId+" ");
	lockinPeriodValid.append(CommonFunction.checkNull(ConnectionDAO.singleReturn(query1.toString())));
	logger.info("lockinPeriodValid: "+query1.toString());
	query2.append("select if(s.number_defr_allowed_total> (select count(loan_id) from cr_resch_dtl where" +
			" loan_id="+loanId+" and resch_type='D' and rec_status='A'), 'yes', 'no') as" +
			" total_deferral_allowed_loan from cr_scheme_m s, cr_loan_dtl ld" +
			" left join cr_resch_dtl r on ld.loan_id=r.loan_id and r.resch_type='D'" +
			" where ld.loan_scheme=s.scheme_id" +
			" and ld.loan_id="+loanId+" ");
	logger.info("totalDeferralAllowed: "+query2.toString());
	totalDeferralAllowed.append(CommonFunction.checkNull(ConnectionDAO.singleReturn(query2.toString())));
	
	query3.append("select case  when (select ifnull(max(RESCH_EFF_DATE),0) from cr_resch_dtl where" +
			" loan_id="+loanId+" and RESCH_TYPE='D' and rec_status='A')=0 then 'yes'" +
			" when (select ifnull(max(RESCH_EFF_DATE),0) from cr_resch_dtl where loan_id="+loanId+"" +
			" and RESCH_TYPE='D' and rec_status='A')<> 0 then if(DATE_ADD((select max(RESCH_EFF_DATE) from cr_resch_dtl" +
			" where loan_id="+loanId+" and RESCH_TYPE='D' and rec_status='A'),INTERVAL s.minimum_gap_defr MONTH) <=" +
			" STR_TO_DATE('"+StringEscapeUtils.escapeSql(effBusinessDate)+"','%d-%m-%Y'), 'yes', 'no')" +
			" end as min_gap_succ_defr_valid from cr_scheme_m s, cr_loan_dtl ld" +
			" left join cr_resch_dtl r on ld.loan_id=r.loan_id and r.resch_type='D'" +
			" where ld.loan_scheme=s.scheme_id and ld.loan_id="+loanId+" ");
	logger.info("minGapSuccDeferralValid: "+query3.toString());
	minGapSuccDeferralValid.append(CommonFunction.checkNull(ConnectionDAO.singleReturn(query3.toString())));
	
	query4.append("select extract(year from str_to_date('"+StringEscapeUtils.escapeSql(effBusinessDate)+"','"+dateFormat+"'))");
	businessYearStr.append(CommonFunction.checkNull(ConnectionDAO.singleReturn(query4.toString())));
	int businessYear = 0;
	if(!(businessYearStr.toString()).equalsIgnoreCase(""))
		businessYear = Integer.parseInt(businessYearStr.toString());
	
	query5.append("select extract(year from resch_eff_date) from cr_resch_dtl where" +
	" resch_type='D' and rec_status='A' and loan_id="+loanId);
	logger.info("query5: "+query5.toString());
	
	int count=0;
	try
	{
		ArrayList yearList = ConnectionDAO.sqlSelect(query5.toString());
		ArrayList data= null;
		for(int i=0;i<yearList.size();i++){
			
	        data=(ArrayList)yearList.get(i);
	        Iterator itr = data.iterator();
	        while(itr.hasNext())
	        {
	        	if(itr.next().toString().equalsIgnoreCase(businessYearStr.toString()))
	        		count++;
	        }
		}
		yearList.clear();
		data.clear();
		data = null;
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}

	logger.info("Count Of Years: "+count);
	query6.append("select s.number_defr_allowed_year from cr_scheme_m s, cr_loan_dtl ld left join " +
			" cr_resch_dtl r on ld.loan_id=r.loan_id and r.resch_type='D' and r.REC_STATUS='A' where" +
			" ld.loan_scheme=s.scheme_id and ld.loan_id="+loanId+" ");
	numDeferralAllowedYrStr.append(CommonFunction.checkNull(ConnectionDAO.singleReturn(query6.toString())));
	int numDeferralAllowedYr = 0;
	if(!(numDeferralAllowedYrStr.toString()).equalsIgnoreCase(""))
		numDeferralAllowedYr = Integer.parseInt(numDeferralAllowedYrStr.toString());
	logger.info("numDeferralAllowedYr: "+numDeferralAllowedYr);
	if(numDeferralAllowedYr<=count && numDeferralAllowedYr>0)
	{
		retStr="deferralAllowedYear";
		logger.info("Total Deferral Allowed in a single year Over");
	}
	
	if((minGapSuccDeferralValid.toString()).equalsIgnoreCase("no"))
	{
		retStr="minGapSuccDeferralValid";
		logger.info("Minimum Gap between Successive Deferral Valid");
	}
	
	if((totalDeferralAllowed.toString()).equalsIgnoreCase("no"))
	{
		retStr="totalDeferralAllowedExceeds";
		logger.info("totalDeferralAllowedExceeds");
	}
	
	if((lockinPeriodValid.toString()).equalsIgnoreCase("yes"))
	{
		retStr="lockinPeriod";
		logger.info("Lockin period Valid");
	}
	
	query1 = null;
	query2 = null;
	query3 = null;
	query4 = null;
	query5 = null;
	query6 = null;
	lockinPeriodValid = null;
	totalDeferralAllowed = null;
	minGapSuccDeferralValid = null;
	businessYearStr = null;
	numDeferralAllowedYrStr = null;
	lbxLoanNoHID = null;
	effBusinessDate = null;
	loanId = null;
	return retStr;
}

public ArrayList<DeferralMakerVo> retriveDeferralValues(String lbxLoanNoHID)
{
	logger.info("In retriveDeferralValues.....DAOImpl");
	ArrayList<DeferralMakerVo> deferralList = new ArrayList();
	StringBuilder queryCheck = new StringBuilder();
	StringBuilder query = new StringBuilder();
	StringBuilder tempCount = new StringBuilder();
	DeferralMakerVo vo = null;
	queryCheck.append("Select count(resch_id) from cr_resch_dtl where loan_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(lbxLoanNoHID).trim())+"'" +
	" and rec_status in ('P','F') and resch_type='D'");
	tempCount.append(ConnectionDAO.singleReturn(queryCheck.toString()));
	int count = Integer.parseInt(tempCount.toString());
	if(count==0)
	{
		try{
	
			  ArrayList mainlist=new ArrayList();
			  ArrayList subList=new ArrayList();
			  query.append("select a.loan_id,a.loan_no, b.customer_name, c.product_desc,  d.scheme_desc, " +
			  	 " (select ifnull(sum(disbursal_amount),0) from cr_loan_disbursal_dtl where loan_id='"+lbxLoanNoHID+"' and rec_status='A')," +
			  	 " a.loan_balance_principal, " +
			  	 " (select count(loan_id) from cr_resch_dtl where loan_id='"+lbxLoanNoHID+"' and RESCH_TYPE='D' and rec_status='A')," +
			  	 " DATE_FORMAT((select max(RESCH_EFF_DATE) from cr_resch_dtl where loan_id='"+lbxLoanNoHID+"' and RESCH_TYPE='D' and rec_status='A'),'"+dateFormat+"')," +
			  	 " a.LOAN_RATE_TYPE, a.LOAN_BASE_RATE_TYPE, a.LOAN_BASE_RATE, a.LOAN_REPAYMENT_FREQ,"+
			  	 " a.LOAN_INSTALLMENT_TYPE, a.LOAN_EMI_AMOUNT, a.LOAN_TENURE, date_format(a.LOAN_MATURITY_DATE,'"+dateFormat+"'),"+
			  	 " DATE_FORMAT((select min(instl_date) from cr_repaysch_dtl where loan_id='"+lbxLoanNoHID+"' and bill_flag='N'),'"+dateFormat+"')" +
				 " from cr_loan_dtl a,gcd_customer_m b,cr_product_m c, cr_scheme_m d" +
				 " where b.customer_id = a.loan_customer_id and c.product_id = a.loan_product"+
				 " and d.scheme_id = a.loan_scheme and a.loan_id='"+lbxLoanNoHID+"'");
	
			 logger.info("In retriveDeferralValues : "+query.toString());
			 mainlist=ConnectionDAO.sqlSelect(query.toString());
			 logger.info("In retriveDeferralValues.....mainlist size: "+mainlist.size());
			if(mainlist.size()!=0)
			{
				
			 for(int i=0;i<mainlist.size();i++){
	
				subList=(ArrayList)mainlist.get(i);
				//logger.info("In retriveDeferralValues......sublist size: "+subList.size());
				if(subList.size()>0){
					vo = new DeferralMakerVo();
					vo.setLbxLoanNoHID((CommonFunction.checkNull(subList.get(0)).trim()));
					vo.setLoanNo((CommonFunction.checkNull(subList.get(1)).trim()));
					vo.setCustomerName((CommonFunction.checkNull(subList.get(2)).trim()));
					vo.setProduct((CommonFunction.checkNull(subList.get(3)).trim()));
					vo.setScheme((CommonFunction.checkNull(subList.get(4)).trim()));
					
					if((CommonFunction.checkNull(subList.get(5))).trim().equalsIgnoreCase(""))
						vo.setDisbursedAmount("0");
					else
					{
						Number disbursedAmt = myFormatter.parse((CommonFunction.checkNull(subList.get(5))).trim());
						vo.setDisbursedAmount(myFormatter.format(disbursedAmt));
					}
					
					if((CommonFunction.checkNull(subList.get(6))).trim().equalsIgnoreCase(""))
						vo.setOutstandingLoanAmount("0");
					else
					{
						Number outstandingLoanAmt = myFormatter.parse((CommonFunction.checkNull(subList.get(6))).trim());
						vo.setOutstandingLoanAmount(myFormatter.format(outstandingLoanAmt));
					}
					
					vo.setDeferralsSinceDsibursal((CommonFunction.checkNull(subList.get(7)).trim()));
					vo.setLastDeferralDate((CommonFunction.checkNull(subList.get(8)).trim()));
					
					if((CommonFunction.checkNull(subList.get(9)).trim()).equalsIgnoreCase("E"))
						vo.setInterestRateType("Equated");
					if((CommonFunction.checkNull(subList.get(9)).trim()).equalsIgnoreCase("F"))
						vo.setInterestRateType("Flat");
					if((CommonFunction.checkNull(subList.get(9)).trim()).equalsIgnoreCase("P"))
						vo.setInterestRateType("PTPM");
					
					vo.setBaseRateType((CommonFunction.checkNull(subList.get(10)).trim()));
					
					if((CommonFunction.checkNull(subList.get(11))).trim().equalsIgnoreCase(""))
						vo.setBaseRate("0");
					else
					{
						Number baseRate = myFormatter.parse((CommonFunction.checkNull(subList.get(11))).trim());
						vo.setBaseRate(myFormatter.format(baseRate));
					}
					
					if((CommonFunction.checkNull(subList.get(12)).trim()).equalsIgnoreCase("M"))
						vo.setInstallmentFrequency("Monthly");
					if((CommonFunction.checkNull(subList.get(12)).trim()).equalsIgnoreCase("B"))
						vo.setInstallmentFrequency("Bimonthly");
					if((CommonFunction.checkNull(subList.get(12)).trim()).equalsIgnoreCase("Q"))
						vo.setInstallmentFrequency("Quarterly");
					if((CommonFunction.checkNull(subList.get(12)).trim()).equalsIgnoreCase("H"))
						vo.setInstallmentFrequency("Half Yearly");
					if((CommonFunction.checkNull(subList.get(12)).trim()).equalsIgnoreCase("Y"))
						vo.setInstallmentFrequency("Yearly");
					
					if((CommonFunction.checkNull(subList.get(13)).trim()).equalsIgnoreCase("E"))
						vo.setInstallmentType("Eq. Installment");
					if((CommonFunction.checkNull(subList.get(13)).trim()).equalsIgnoreCase("G"))
						vo.setInstallmentType("Gr. Installment");
					if((CommonFunction.checkNull(subList.get(13)).trim()).equalsIgnoreCase("P"))
						vo.setInstallmentType("Eq. Principal");
					if((CommonFunction.checkNull(subList.get(13)).trim()).equalsIgnoreCase("Q"))
						vo.setInstallmentType("Gr. Principal1");
					if((CommonFunction.checkNull(subList.get(13)).trim()).equalsIgnoreCase("S"))
						vo.setInstallmentType("Gr. Principal2");
					
					if((CommonFunction.checkNull(subList.get(14))).trim().equalsIgnoreCase(""))
						vo.setEmi("0");
					else
					{
						Number emi = myFormatter.parse((CommonFunction.checkNull(subList.get(14))).trim());
						vo.setEmi(myFormatter.format(emi));
					}
					
					vo.setTenure((CommonFunction.checkNull(subList.get(15)).trim()));
					vo.setMaturityDate((CommonFunction.checkNull(subList.get(16)).trim()));
					vo.setNextDueDate((CommonFunction.checkNull(subList.get(17)).trim()));
					deferralList.add(vo);
				}
			  }
			}
				mainlist.clear();
				subList.clear();
		}catch(Exception e){
		e.printStackTrace();
		}
		finally
		{
			queryCheck = null;
			query = null;
			tempCount = null;
			lbxLoanNoHID = null;
			vo = null;
		}
	}
	else
		logger.info("Size of deferralList: "+deferralList.size());
	return deferralList;
}




public ArrayList<RepayScheduleVo> getNewRepayScheduleDeferral(RepayScheduleVo vo, String loanId, String reschId) 
{
	ArrayList mainlist =new ArrayList();
	ArrayList subList =null; 
	ArrayList list =new ArrayList();
	StringBuilder bussIrrQ = new StringBuilder(); 
	StringBuilder bussIrr = new StringBuilder(); 
	StringBuilder query = new StringBuilder();  
	RepayScheduleVo repvo = null;
	try
	{
		logger.info("In getNewRepayScheduleDeferral DAOImpl");
		bussIrrQ.append("select deal_irr2 from cr_deal_loan_dtl where DEAL_LOAN_ID=(select LOAN_DEAL_LOAN_ID from cr_loan_dtl where LOAN_ID="+loanId+")");
		logger.info("Query in getRepaySchFieldsDetail--DEAL_IRR2---" + bussIrrQ);
		bussIrr.append(ConnectionDAO.singleReturn(bussIrrQ.toString()));
		query.append("select R_Seq_No,DATE_FORMAT(R_Due_Date,'"+dateFormat+"'),R_Instl_Amount,R_Prin_Comp,R_Int_Comp,R_EXCESS_Int_Comp," +
			" if(R_ADV_FLAG='Y','YES','NO'),R_Prin_OS from Repay_Temp where R_LOAN_ID="+loanId);
		logger.info("Query in getNewRepayScheduleDeferral-----" + query);
		mainlist = ConnectionDAO.sqlSelect(query.toString());
		logger.info("mainlist size: "+mainlist.size());
		for (int i = 0; i < mainlist.size(); i++) 
		{
			subList = (ArrayList) mainlist.get(i);
			repvo = new RepayScheduleVo();
			if (subList.size() > 0)
			{
				repvo.setInstNo((CommonFunction.checkNull(subList.get(0)).trim()));
				repvo.setDueDate((CommonFunction.checkNull(subList.get(1)).trim()));
				if(!CommonFunction.checkNull(subList.get(2)).trim().equalsIgnoreCase(""))
				{
			   		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(2))).trim());  
			   		repvo.setInstAmount(myFormatter.format(reconNum));
				}
						
				if(!CommonFunction.checkNull(subList.get(3)).trim().equalsIgnoreCase(""))
	    		{
		    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(3))).trim());  
		    		repvo.setPrinciple(myFormatter.format(reconNum));
	    		}
	
				if(!CommonFunction.checkNull(subList.get(4)).trim().equalsIgnoreCase(""))
	    		{
		    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(4))).trim());  
		    		repvo.setInstCom(myFormatter.format(reconNum));
	    		}
	
				if(!CommonFunction.checkNull(subList.get(5)).trim().equalsIgnoreCase(""))
	    		{
		    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(5))).trim());  
		    		repvo.setExcess(myFormatter.format(reconNum));
	    		}
			}
			repvo.setAdvFlag((CommonFunction.checkNull(subList.get(6)).trim()));
			if(!CommonFunction.checkNull(subList.get(7)).trim().equalsIgnoreCase(""))
			{
	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(7))).trim());  
	    		repvo.setPrinOS(myFormatter.format(reconNum));
			}
			repvo.setDealIRR2(bussIrr.toString());
			list.add(repvo);
		}			
	} 
	catch (Exception e)
	{e.printStackTrace();}
	finally
	{
		bussIrrQ = null;
		bussIrr = null;
		query = null;
		vo = null;
		loanId = null;
		reschId = null;
		mainlist.clear();
		mainlist = null;
		subList.clear();
		subList=null;
		repvo = null;
	}
	return list;
}


// ******************************* Deferral Ends ***************************************

// ******************************* Re Pricing Starts ***********************************




public ArrayList<InstallmentPlanForCMVO> viewNewInstallmentPlanRepricing(String loanId, String reschId) 
{
	logger.info("Inside viewNewInstallmentPlanRepricing DAOImpl");
	ArrayList<InstallmentPlanForCMVO> list = new ArrayList<InstallmentPlanForCMVO>();
	ArrayList mainList = new ArrayList();
	ArrayList subList = new ArrayList();
	//String query = "";
	StringBuilder query3 = new StringBuilder();
	StringBuilder query = new StringBuilder();
	StringBuilder countStr = new StringBuilder();
	InstallmentPlanForCMVO ipVo = null;
	try {
		query3.append("select count(loan_id) from cr_resch_installment_plan where resch_id="+reschId+"" +
				" and loan_id="+loanId+"");
		countStr.append(CommonFunction.checkNull(ConnectionDAO.singleReturn(query3.toString())));
		int count=0;
		if(!(countStr.toString()).equalsIgnoreCase(""))
			count=Integer.parseInt(countStr.toString());
		logger.info("Count of rows in instl_plan: "+count);
//		query = " select distinct D.FROM_INSTL_NO,D.TO_INSTL_NO,D.RECOVERY_PERCENT,L.LOAN_INSTALLMENT_TYPE,"
//			+ " L.LOAN_NO_OF_INSTALLMENT,D.PRINCIPAL_AMOUNT,D.INSTALLMENT_AMOUNT,L.LOAN_RATE_TYPE,L.LOAN_LOAN_AMOUNT,"
//			+ " D.RECOVERY_TYPE "
//			+ " from cr_loan_dtl L, cr_resch_dtl R left JOIN cr_resch_installment_plan D on R.LOAN_ID=D.LOAN_ID"
//			+ " and R.RESCH_ID=D.RESCH_ID "
//			+ " where l.LOAN_ID=r.LOAN_ID and "
//			+ " L.LOAN_ID=" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId))+""
//			+ " and  R.resch_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(reschId))+"";
		if(count>0)
		{
			query.append(" select distinct D.FROM_INSTL_NO,D.TO_INSTL_NO,D.RECOVERY_PERCENT,R.NEW_INSTALLMENT_TYPE,"
				+ " R.NEW_INSTL_NO,D.PRINCIPAL_AMOUNT,D.INSTALLMENT_AMOUNT,L.LOAN_RATE_TYPE,L.LOAN_BALANCE_PRINCIPAL,"
				+ " D.RECOVERY_TYPE,L.EDIT_DUE_DATE,date_format(R.NEW_MATURITY_DATE,'"+dateFormat+"'),R.NEW_FREQUENCY,date_format(D.DUE_DATE,'"+dateFormat+"')"
				+ " from cr_loan_dtl L, cr_resch_dtl R left JOIN cr_resch_installment_plan D on R.LOAN_ID=D.LOAN_ID"
				+ " and R.RESCH_ID=D.RESCH_ID "
				+ " where l.LOAN_ID=r.LOAN_ID and "
				+ " L.LOAN_ID=" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId))+""
				+ " and  R.resch_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(reschId))+"");
		}
		else
		{
			query.append(" select distinct R.DEFERAL_FROM_INSTL,D.TO_INSTL_NO,D.RECOVERY_PERCENT,R.NEW_INSTALLMENT_TYPE,"
				+ " R.NEW_INSTL_NO,D.PRINCIPAL_AMOUNT,D.INSTALLMENT_AMOUNT,L.LOAN_RATE_TYPE,L.LOAN_BALANCE_PRINCIPAL,"
				+ " D.RECOVERY_TYPE, L.EDIT_DUE_DATE,date_format(R.NEW_MATURITY_DATE,'"+dateFormat+"'),R.NEW_FREQUENCY,date_format(D.DUE_DATE,'"+dateFormat+"')"
				+ " from cr_loan_dtl L, cr_resch_dtl R left JOIN cr_resch_installment_plan D on R.LOAN_ID=D.LOAN_ID"
				+ " and R.RESCH_ID=D.RESCH_ID "
				+ " where l.LOAN_ID=r.LOAN_ID and "
				+ " L.LOAN_ID=" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId))+""
				+ " and  R.resch_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(reschId))+"");
		}

		logger.info("viewNewInstallmentPlanRepricing Queryl: " + query);
		mainList = ConnectionDAO.sqlSelect(query.toString());
		for (int i = 0; i < mainList.size(); i++)
		{
			subList = (ArrayList) mainList.get(i);
			if (subList.size() > 0)
			{
				ipVo = new InstallmentPlanForCMVO();
				if ((CommonFunction.checkNull(subList.get(3)).equalsIgnoreCase("E")
						|| CommonFunction.checkNull(subList.get(3)).equalsIgnoreCase("P")) && CommonFunction.checkNull(subList.get(10)).equalsIgnoreCase("N")) 
				{
					if(count>0)
						ipVo.setFromInstallment((CommonFunction.checkNull(subList.get(0))).trim());
					else
						ipVo.setFromInstallment("1"); 
					//ipVo.setToInstallment((CommonFunction.checkNull(subList.get(4))).trim());
					// These two lines and the query(commented now) were used prior to the changes made when rescheduling was released 
					//ipVo.setFromInstallment((CommonFunction.checkNull(subList.get(0))).trim());
					ipVo.setToInstallment((CommonFunction.checkNull(subList.get(1))).trim());
					if (!CommonFunction.checkNull(subList.get(2)).equalsIgnoreCase(""))
					{
						Number RecoveryPercen = myFormatter.parse((CommonFunction.checkNull(subList.get(2))).trim());
						ipVo.setRecoveryPercen(myFormatter.format(RecoveryPercen));
					}
					else 
					{
						ipVo.setRecoveryPercen("0");
					}
					ipVo.setInstallmentType((CommonFunction.checkNull(subList.get(3))).trim());
					ipVo.setTotalInstallment((CommonFunction.checkNull(subList.get(4))).trim());
				} 
				else 
				{
					if(count>0)
						ipVo.setFromInstallment((CommonFunction.checkNull(subList.get(0))).trim());
					else
						ipVo.setFromInstallment("1"); 
					//ipVo.setFromInstallment((CommonFunction.checkNull(subList.get(0))).trim());
					ipVo.setToInstallment((CommonFunction.checkNull(subList.get(1))).trim());
					if (!CommonFunction.checkNull(subList.get(2)).equalsIgnoreCase("")) {
						Number RecoveryPercen = myFormatter.parse((CommonFunction.checkNull(subList.get(2))).trim());
						ipVo.setRecoveryPercen(myFormatter.format(RecoveryPercen));
					} 
					else
					{
						ipVo.setRecoveryPercen("0");
					}
					ipVo.setInstallmentType((CommonFunction.checkNull(subList.get(3))).trim());
					ipVo.setTotalInstallment((CommonFunction.checkNull(subList.get(4))).trim());

				}
				ipVo.setRateType((CommonFunction.checkNull(subList.get(7))).trim());

				if (!CommonFunction.checkNull(subList.get(5)).equalsIgnoreCase("")) {
					Number princam = myFormatter.parse((CommonFunction.checkNull(subList.get(5))).trim());
					ipVo.setPrinAm(myFormatter.format(princam));
				}
				else 
				{
					ipVo.setPrinAm("0.00");
				}
				
				if (!CommonFunction.checkNull(subList.get(6)).equalsIgnoreCase(""))
				{
					Number instam = myFormatter.parse((CommonFunction.checkNull(subList.get(6))).trim());
					ipVo.setInstalAm(myFormatter.format(instam));
				}
				else 
				{
					ipVo.setInstalAm("0.00");
				}

				if (!CommonFunction.checkNull(subList.get(8)).equalsIgnoreCase("")) {
					Number instam = myFormatter.parse((CommonFunction.checkNull(subList.get(8))).trim());
					ipVo.setLoanAmount(myFormatter.format(instam));
				} 
				else 
				{
					ipVo.setLoanAmount("0.00");
				}
				ipVo.setRecoveryType((CommonFunction.checkNull(subList.get(9))).trim());
				ipVo.setEditDueDate((CommonFunction.checkNull(subList.get(10))).trim());
				ipVo.setMaxDate((CommonFunction.checkNull(subList.get(11))).trim());
				ipVo.setFrequency((CommonFunction.checkNull(subList.get(12))).trim());
				ipVo.setDueDatee((CommonFunction.checkNull(subList.get(13))).trim());
				
				
				list.add(ipVo);
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally
	{
		query3 = null;
		mainList.clear();
		mainList = null;
		countStr = null;
		loanId = null;
		reschId = null;
		ipVo = null;
		subList.clear();
		subList=null;
	}
	return list;
}

public ArrayList<InstallmentPlanForCMVO> viewNewInstallmentPlanPartPayment(String loanId, String reschId,String installNo) 
{
	logger.info("Inside viewNewInstallmentPlanPartPayment DAOImpl installNo  "+installNo );
	ArrayList<InstallmentPlanForCMVO> list = new ArrayList<InstallmentPlanForCMVO>();
	ArrayList mainList = new ArrayList();
	ArrayList subList = new ArrayList();
	StringBuilder query = new StringBuilder(); 
	
	try {
			query.append(" select distinct D.FROM_INSTL_NO,D.TO_INSTL_NO,D.RECOVERY_PERCENT,L.LOAN_INSTALLMENT_TYPE,"
			+ "  (SELECT COUNT(*)  FROM CR_REPAYSCH_DTL WHERE LOAN_ID=" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId))+" AND INSTL_NO > " + StringEscapeUtils.escapeSql(CommonFunction.checkNull(installNo))+") NO_OF_INSTALLMENT_loan,D.PRINCIPAL_AMOUNT,D.INSTALLMENT_AMOUNT,L.LOAN_RATE_TYPE,L.LOAN_LOAN_AMOUNT,"
			+ " D.RECOVERY_TYPE "
			+ " from cr_loan_dtl L, cr_resch_dtl R left JOIN cr_resch_installment_plan D on R.LOAN_ID=D.LOAN_ID"
			+ " and R.RESCH_ID=D.RESCH_ID "
			+ " where l.LOAN_ID=r.LOAN_ID and "
			+ " L.LOAN_ID=" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId))+""
			+ " and  R.resch_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(reschId))+"");
		
		logger.info("viewNewInstallmentPlanPartPayment Queryl: " + query.toString());
		mainList = ConnectionDAO.sqlSelect(query.toString());
		for (int i = 0; i < mainList.size(); i++)
		{
			subList = (ArrayList) mainList.get(i);
			if (subList.size() > 0)
			{
				InstallmentPlanForCMVO ipVo = new InstallmentPlanForCMVO();
				if (CommonFunction.checkNull(subList.get(3)).equalsIgnoreCase("E")
						|| CommonFunction.checkNull(subList.get(3)).equalsIgnoreCase("P")) 
				{
					//ipVo.setFromInstallment("1"); 
					//ipVo.setToInstallment((CommonFunction.checkNull(subList.get(4))).trim());
					// These two lines were used prior to the changes made when rescheduling was released
					ipVo.setFromInstallment((CommonFunction.checkNull(subList.get(0))).trim());
					ipVo.setToInstallment((CommonFunction.checkNull(subList.get(1))).trim());
					if (!CommonFunction.checkNull(subList.get(2)).equalsIgnoreCase(""))
					{
						Number RecoveryPercen = myFormatter.parse((CommonFunction.checkNull(subList.get(2))).trim());
						ipVo.setRecoveryPercen(myFormatter.format(RecoveryPercen));
					}
					else 
					{
						ipVo.setRecoveryPercen("0");
					}
					ipVo.setInstallmentType((CommonFunction.checkNull(subList.get(3))).trim());
					ipVo.setTotalInstallment((CommonFunction.checkNull(subList.get(4))).trim());
				} 
				else 
				{
					ipVo.setFromInstallment((CommonFunction.checkNull(subList.get(0))).trim());
					ipVo.setToInstallment((CommonFunction.checkNull(subList.get(1))).trim());
					if (!CommonFunction.checkNull(subList.get(2)).equalsIgnoreCase("")) {
						Number RecoveryPercen = myFormatter.parse((CommonFunction.checkNull(subList.get(2))).trim());
						ipVo.setRecoveryPercen(myFormatter.format(RecoveryPercen));
					} 
					else
					{
						ipVo.setRecoveryPercen("0");
					}
					ipVo.setInstallmentType((CommonFunction.checkNull(subList.get(3))).trim());
					ipVo.setTotalInstallment((CommonFunction.checkNull(subList.get(4))).trim());

				}
				ipVo.setRateType((CommonFunction.checkNull(subList.get(7))).trim());

				if (!CommonFunction.checkNull(subList.get(5)).equalsIgnoreCase("")) {
					Number princam = myFormatter.parse((CommonFunction.checkNull(subList.get(5))).trim());
					ipVo.setPrinAm(myFormatter.format(princam));
				}
				else 
				{
					ipVo.setPrinAm("0.00");
				}
				
				if (!CommonFunction.checkNull(subList.get(6)).equalsIgnoreCase(""))
				{
					Number instam = myFormatter.parse((CommonFunction.checkNull(subList.get(6))).trim());
					ipVo.setInstalAm(myFormatter.format(instam));
				}
				else 
				{
					ipVo.setInstalAm("0.00");
				}

				if (!CommonFunction.checkNull(subList.get(8)).equalsIgnoreCase("")) {
					Number instam = myFormatter.parse((CommonFunction.checkNull(subList.get(8))).trim());
					ipVo.setLoanAmount(myFormatter.format(instam));
				} 
				else 
				{
					ipVo.setLoanAmount("0.00");
				}
				ipVo.setRecoveryType((CommonFunction.checkNull(subList.get(9))).trim());
				list.add(ipVo);
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally
	{
		query = null;
		mainList.clear();
		mainList = null;
		subList.clear();
		subList = null;
		loanId = null;
		reschId = null;
		installNo = null;
	}
	return list;
}
public boolean saveNewInstallPlan(InstallmentPlanForCMVO ipvo) {
	 
	logger.info("In saveNewInstallPlan DAOImpl");
	String FromInstallment[] = ipvo.getFromInstall();
	logger.info("In saveNewInstallPlan.....length: "+FromInstallment.length);
	String ToInstallment[] = ipvo.getToInstall();
	String RecoveryPercen[] = ipvo.getRecoveryPer();
	String prinAmount[] = ipvo.getPrincipalAmount();
	String instalAmount[] = ipvo.getInstallmentAmount();	
	String toInstallment = ipvo.getToInstallment();	
			String dueDate[] = ipvo.getDueDate();
			// Code Added for Edit Due date| Rahul papneja |29012018
			String editDueDate = ipvo.getEditDueDate();
			// Ends Here| Rahul papneja

	
	String InstallmentType= ipvo.getInstallmentType();
	logger.info("In saveNewInstallPlan..................getInstallmentType: "+ipvo.getInstallmentType());
	String TotalInstallment=ipvo.getTotalInstallment();
	logger.info("In saveNewInstallPlan..................getTotalInstallment: "+ipvo.getTotalInstallment());
	//String toInstallment = ipvo.getToInstallment();	
	logger.info("In saveNewInstallPlan toInstallment: "+toInstallment);
	ArrayList qryList=new ArrayList();
	StringBuilder checkQ = new StringBuilder();
	StringBuilder qry = new StringBuilder();
	StringBuilder count = new StringBuilder();
	StringBuffer bufInsSql = null;
	PrepStmtObject insertPrepStmtObject = null;
	boolean status=false;
	try 
	{
		 insertPrepStmtObject = new PrepStmtObject();
		 bufInsSql =	new StringBuffer();
		 	checkQ.append("select count(INSTALLMENT_PLAN_ID) from cr_resch_installment_plan where" +
		 	" LOAN_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(ipvo.getLoanId()).trim())+"" +
		 	" and resch_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(ipvo.getReschId())));
		 	logger.info("checkQ::"+ checkQ.toString());
		  count.append(ConnectionDAO.singleReturn(checkQ.toString()));
	   
		 if(!(count.toString()).equalsIgnoreCase("0"))
		 { 
			 insertPrepStmtObject = new PrepStmtObject();
			 qry.append("DELETE FROM cr_resch_installment_plan WHERE" +
			 	" LOAN_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(ipvo.getLoanId()).trim())+ "" +
			 	" and resch_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(ipvo.getReschId())));

		     insertPrepStmtObject.setSql(qry.toString());
		     qryList.add(insertPrepStmtObject);		  
		 }
	
		 for(int k=0;k<FromInstallment.length;k++)  
		 {
			 bufInsSql =	new StringBuffer();
			 insertPrepStmtObject = new PrepStmtObject();
		  
			 insertPrepStmtObject = new PrepStmtObject();


  if(CommonFunction.checkNull(editDueDate).equalsIgnoreCase("Y"))
				  {

			 bufInsSql.append("insert into cr_resch_installment_plan (RESCH_ID,LOAN_ID,FROM_INSTL_NO,TO_INSTL_NO,RECOVERY_PERCENT,PRINCIPAL_AMOUNT,INSTALLMENT_AMOUNT,REC_STATUS,SEQ_NO,RECOVERY_TYPE,MAKER_ID,MAKER_DATE,DUE_DATE) values(?,?,?,?,?,?,?,?,?,?,?,DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND))");
			 
				  }
				  else
				  {
					  bufInsSql.append("insert into cr_resch_installment_plan (RESCH_ID,LOAN_ID,FROM_INSTL_NO,TO_INSTL_NO,RECOVERY_PERCENT,PRINCIPAL_AMOUNT,INSTALLMENT_AMOUNT,REC_STATUS,SEQ_NO,RECOVERY_TYPE,MAKER_ID,MAKER_DATE) values(?,?,?,?,?,?,?,?,?,?,?,DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND))");
				  }

			 if(CommonFunction.checkNull(ipvo.getReschId()).trim().equalsIgnoreCase(""))
				 insertPrepStmtObject.addNull();
			 else
				 insertPrepStmtObject.addString((ipvo.getReschId()).trim());   // loan Id
			 
			 if(CommonFunction.checkNull(ipvo.getLoanId()).trim().equalsIgnoreCase(""))
				 insertPrepStmtObject.addNull();
			 else
				 insertPrepStmtObject.addString((ipvo.getLoanId()).trim());   // loan Id
			
			 if(CommonFunction.checkNull(FromInstallment[k]).trim().equalsIgnoreCase(""))
				 insertPrepStmtObject.addNull();
			 else
				 insertPrepStmtObject.addString((FromInstallment[k]).trim()); //From Installment
			
			 if(CommonFunction.checkNull(ToInstallment[k]).trim().equalsIgnoreCase(""))
				 insertPrepStmtObject.addNull();
			 else
				 insertPrepStmtObject.addString((ToInstallment[k]).trim());// To Installment 
	    	
	    	 if((CommonFunction.checkNull(RecoveryPercen[k])).trim().equalsIgnoreCase(""))
	        		insertPrepStmtObject.addString("0.00");
	    	 else
	    		 insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(RecoveryPercen[k]).trim())).toString());
	    	 
	    	 if((CommonFunction.checkNull(prinAmount[k])).trim().equalsIgnoreCase(""))
	    		 insertPrepStmtObject.addString("0.00");
	    	 else
	    		 insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(prinAmount[k]).trim())).toString());// PRINCIPAL_AMOUNT
	    	 
	    	 if((CommonFunction.checkNull(instalAmount[k])).trim().equalsIgnoreCase(""))
	    		 insertPrepStmtObject.addString("0.00");
	    	 else
	    		 insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(instalAmount[k]).trim())).toString());// INSTALLMENT_AMOUNT	
	    	insertPrepStmtObject.addString("P");// REC_STATUS   					
	    	insertPrepStmtObject.addString(""+(k+1));// SEQ_NO    
	    	if(CommonFunction.checkNull(InstallmentType).equalsIgnoreCase("I"))
			    	{
			    		insertPrepStmtObject.addString("F");
			    	}
			    	else
			    	{

if(CommonFunction.checkNull(ipvo.getRecoveryType()).trim().equalsIgnoreCase(""))
	    		insertPrepStmtObject.addNull();
	    	else
	    		insertPrepStmtObject.addString((ipvo.getRecoveryType()).trim());// RECOVERY_TYPE
}
	    	//---------------------------------------------------------
	    	if ((CommonFunction.checkNull(ipvo.getMakerId())).trim().equalsIgnoreCase(""))
	    		insertPrepStmtObject.addNull();
	    	else
	    		insertPrepStmtObject.addString((ipvo.getMakerId()).trim());
	    	if ((CommonFunction.checkNull(ipvo.getMakerDate()).trim()).equalsIgnoreCase(""))
	    		insertPrepStmtObject.addNull();
	    	else
	    		insertPrepStmtObject.addString(CommonFunction.checkNull(ipvo.getMakerDate()).trim());
	    	//---------------------------------------------------------
	  
// Code added for Edit Due Date| Rahul papneja| 30012018
			 // Vishal changes start
				if(CommonFunction.checkNull(editDueDate).equalsIgnoreCase("Y"))
				{
					if(CommonFunction.checkNull(dueDate[k]).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
					insertPrepStmtObject.addString(dueDate[k].trim());// Due Date  	
				}
				// Vishal changes end
				else
				{
			 	if(CommonFunction.checkNull(InstallmentType).equalsIgnoreCase("I")||CommonFunction.checkNull(InstallmentType).equalsIgnoreCase("J"))
			 	// Ends Here| Rahul papneja
			 	
			 	{
			 	if(CommonFunction.checkNull(dueDate[k]).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
				insertPrepStmtObject.addString(dueDate[k].trim());// To duedate 
			 	}
				

}
		  
		 	insertPrepStmtObject.setSql(bufInsSql.toString());
			logger.info("IN saveInstallPlan() insert query1 ### here --- "+insertPrepStmtObject.printQuery());
			qryList.add(insertPrepStmtObject);
		}
		
		
		status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally
	{
		checkQ = null;
		qry = null;
		count = null;
		ipvo = null;
		FromInstallment = null;
		ToInstallment = null;
		RecoveryPercen = null;
		prinAmount = null;
		instalAmount = null;
		InstallmentType = null;
		TotalInstallment = null;
		toInstallment = null;	
		qryList.clear();
		qryList=null;
		checkQ = null;
		qry  = null;
		count = null;
		bufInsSql = null;
		insertPrepStmtObject = null;
		
	}
    logger.info("In saveNewInstallPlan......................"+status);
	return status;
}

// ******************************* Re Pricing Ends ***********************************
		
		// amit's data end
		public ArrayList getDealCharges(String loanId)
		{
		
			ArrayList list =new ArrayList();
			StringBuilder query = new StringBuilder(); 
			ArrayList subcharges= null;
			ArrayList charges = null;
			try {
						
				ChargeVo vo =null;
					query.append("select DEAL_CHARGE_DTL_ID,DEAL_CHARGE_TYPE,DEAL_CHARGE_CODE,CHARGE_DESC," +
						" DEAL_CHARGE_BP_TYPE,v.DEALER_DESC,DEAL_CHARGE_CALCULATED_AMOUNT,DEAL_CHARGE_FINAL_AMOUNT,g.CUSTOMER_NAME from cr_txncharges_dtl t " +
						" left join com_charge_code_m c on t.DEAL_CHARGE_CODE=c.CHARGE_CODE " +
						" left join cr_dsa_dealer_m v on v.DEALER_ID=t.DEAL_CHARGE_BP_ID and v.BP_TYPE=t.DEAL_CHARGE_BP_TYPE and v.REC_STATUS='A' " +
						" left join gcd_customer_m g on g.CUSTOMER_ID=t.DEAL_CHARGE_BP_ID "+
						" where TXN_ID=(select LOAN_DEAL_ID from cr_loan_dtl where LOAN_ID="+loanId+") and TXN_TYPE='DC'");
				
				logger.info("getDealCharges for  query "+query.toString());
				
				charges = ConnectionDAO.sqlSelect(query.toString());
				logger.info("getchargesDetail size "+charges.size());
				
				for(int i=0;i<charges.size();i++){
					//logger.info("showUnderwritingQueryData...FOR loop "+CommonFunction.checkNull(product.get(i)).toString());
					subcharges=(ArrayList)charges.get(i);
					if(subcharges.size()>0)	{
						vo = new ChargeVo();
						vo.setChargeId((CommonFunction.checkNull(subcharges.get(0)).trim()));
						if(CommonFunction.checkNull(subcharges.get(1)).toString().equals("R"))
						{
							vo.setChargeType("Receivable");
						}
						else
						{
							vo.setChargeType("Payable");
						}
						vo.setChargeCode((CommonFunction.checkNull(subcharges.get(2)).trim()));
						vo.setChargeDesc((CommonFunction.checkNull(subcharges.get(3)).trim()));
						if(CommonFunction.checkNull(subcharges.get(4)).toString().equals("CS"))
						{
							vo.setChargeBPType("Customer");
							vo.setChargeBPId((CommonFunction.checkNull(subcharges.get(8)).trim()));
						}
						else
						{
							vo.setChargeBPId((CommonFunction.checkNull(subcharges.get(5)).trim()));
						}

					
						vo.setChargeCal((CommonFunction.checkNull(subcharges.get(6)).trim()));
						logger.info("Calculated Amount: "+(CommonFunction.checkNull(subcharges.get(6)).trim()));
						vo.setChargeFinal((CommonFunction.checkNull(subcharges.get(7)).trim()));
						list.add(vo);
			            }
					}
			}
					catch (Exception e) {
				
				e.printStackTrace();
			}
					finally
					{
						query = null;
						loanId = null;
						subcharges.clear();
						charges.clear();
						charges=null;
						subcharges=null;
					}
			
			return list;
		}

		public ArrayList<Object> getAssetsAll() {
			CollateralVo vo=null;
	         ArrayList<Object> list=new ArrayList<Object>();
	         StringBuilder query = new StringBuilder(); 
	         ArrayList bdetails = null;
	         ArrayList bdetails1= null;
           try
          {
   	    logger.info("Value of Assets Id in dao is....getAssetsAll................");
	        query.append("SELECT c.ASSET_ID,c.ASSET_COLLATERAL_DESC,c.ASSET_COLLATERAL_VALUE,c.ASSET_TYPE, ASSET_COLLATERAL_CLASS from cr_asset_collateral_m c");

	        logger.info("getAssetsAll "+query.toString());
	        bdetails = ConnectionDAO.sqlSelect(query.toString());
   	    logger.info("getAssetsAll"+bdetails.size());
   	    for(int i=0;i< bdetails.size();i++)
   	    {
//   	    	logger.info("Collateral"+bdetails.get(i).toString());
   	    	bdetails1=(ArrayList)bdetails.get(i);
   	    	if(bdetails1.size()>0)
   			{
   	    	   vo =  new CollateralVo();
   	    		vo.setAssetsId((CommonFunction.checkNull(bdetails1.get(0)).trim()));
   	    		vo.setAssetsCollateralDesc((CommonFunction.checkNull(bdetails1.get(1)).trim()));
   	    		vo.setAssetsCollateralValue((CommonFunction.checkNull(bdetails1.get(2)).trim()));
   	    		vo.setColltype2((CommonFunction.checkNull(bdetails1.get(3)).trim()));
   	    		vo.setColltype1((CommonFunction.checkNull(bdetails1.get(4)).trim()));
   			}

   			list.add(vo);
   		}
   		}catch(Exception e){
   			e.printStackTrace();
   		}
   		finally
   		{
   			query = null;
   			vo = null;
   			bdetails.clear();
   			bdetails = null;
   			bdetails1.clear();
   			bdetails1= null;
   		}

   		return list;
		}
		
		public boolean generatePlaning(String loanId,String userId,String bgDate) 
		{
			
		logger.info("In generatePlaning...Dao Impl.");
		ArrayList qryList = new ArrayList();
		boolean status = true;
		StringBuffer bufInsSql = null;
		PrepStmtObject insertPrepStmtObject = null;
		StringBuilder q1 = new StringBuilder(); 
		StringBuilder checkQuery = new StringBuilder();
		StringBuilder totalInstallment = new StringBuilder();

		try {
			    checkQuery.append("select distinct LOAN_ID from cr_installment_plan where LOAN_ID="+loanId);
			    boolean checkSt=ConnectionDAO.checkStatus(checkQuery.toString());
				  if(!checkSt)  
				  {
					   	q1.append("select LOAN_NO_OF_INSTALLMENT from cr_loan_dtl where LOAN_ID="+loanId);
					    totalInstallment.append(ConnectionDAO.singleReturn(q1.toString()));
					    logger.info("In generatePlaning totalInstallment "+totalInstallment);
						bufInsSql = new StringBuffer();
						insertPrepStmtObject = new PrepStmtObject();

						bufInsSql.append("insert into cr_installment_plan (LOAN_ID,SEQ_NO,FROM_INSTL_NO,TO_INSTL_NO,RECOVERY_PERCENT,PRINCIPAL_AMOUNT,INSTALLMENT_AMOUNT,MAKER_ID,MAKER_DATE)");

						bufInsSql.append(" values ( ");
						bufInsSql.append(" ?,");//LOAN_ID
						bufInsSql.append(" 1,");//SEQ_NO
						bufInsSql.append(" 1,");//FROM_INSTL_NO
						bufInsSql.append(" ?,");//TO_INSTL_NO
						bufInsSql.append(" 100,");//RECOVERY_PERCENT
						bufInsSql.append(" 0,");//PRINCIPAL_AMOUNT
						bufInsSql.append(" 0,");//INSTALLMENT_AMOUNT
						bufInsSql.append(" ?,");//MAKER_ID
						bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND))");//MAKER_DATE

						
						if (CommonFunction.checkNull(loanId).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else 
							insertPrepStmtObject.addString((loanId).trim());

						if (CommonFunction.checkNull(totalInstallment).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((totalInstallment.toString()).trim());

						
						if (CommonFunction.checkNull(userId).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((userId).trim());

						if (CommonFunction.checkNull(bgDate).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((bgDate).trim());

						insertPrepStmtObject.setSql(bufInsSql.toString());
						qryList.add(insertPrepStmtObject);

					
					status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				  }
				logger.info("In generatePlaning." + status);

		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			q1 = null;
			checkQuery = null;
			totalInstallment = null;
			loanId= null;
			userId= null;
			bgDate= null;
			qryList.clear();
			qryList=null;
			bufInsSql= null;
			insertPrepStmtObject= null;
		}
		
		return status;
	}
		
//abhimanyu upload...
		public ArrayList getUploadSummary(String makerId, HttpServletRequest request)
		{
			HttpSession session=request.getSession();
			logger.info("in getUploadSummary... DAOI ");
			ArrayList list= new ArrayList();
			//Connection conn1=null;
			//Statement stmt,stmt1 =null;
			StringBuffer data= new StringBuffer();
			StringBuilder query = new StringBuilder(); 
			StringBuilder queryForTMP = new StringBuilder();
			StringBuilder total = new StringBuilder();
			try
			{
			if(makerId!=null)
			{
				// select batch_id ,maker_date,no_of_records_rejected,no_of_records_uploaded from upload_summary where maker_id=1 and batch_id=( select max(batch_id) from upload_summary)
			query.append("select batch_id ,maker_date,no_of_records_rejected,no_of_records_uploaded from upload_summary where maker_id= '"+ makerId+ "' and batch_id=( select max(batch_id) from upload_summary)");
			logger.info("In getUploadData()...............query...........DAOImpl "+query.toString());
			list=ConnectionDAO.sqlSelect(query.toString());
			logger.info("In getUploadData()...............list...........DAOImpl "+list);
			queryForTMP.append("Select count('total') from cheque_status_upload_tmp where uploaded_flag='N' or uploaded_flag='Y' and maker_id='"+makerId+"'"); 
		
			logger.info("In..getUploadSummary()...Query"+queryForTMP.toString());
			
			total.append(ConnectionDAO.singleReturn(queryForTMP.toString()));		
			logger.info("in getUploadSummary()..in daoi..total is."+total);
			session.setAttribute("TotalUploadandReject", total);
			logger.info("Total uploaded and rejected.."+total);
			}
			else
			{
				logger.info("In.....getUploadData()...DAOIMPL...MakerId..."+makerId);
				
			}
		
			}
			catch (Exception e) {
				logger.error(e.getMessage());
				
			}
			finally
			{
				query = null;
				queryForTMP = null;
				total = null;
				makerId= null;
				data = null;
				query = null;
				queryForTMP = null;
				total = null;
			}
			
			return list;
		}
		
		
		public boolean downLoadErrorLog(HttpServletRequest request,HttpServletResponse response,String makerid)  
		{
			logger.info("in..downLoadErrorLog().DAOIM.");
			boolean status=false;
		    HttpSession session = null;	
		    //String userID = "";
	        StringBuffer txtReport = new StringBuffer();	       
	        ArrayList dataList = null;
	        ArrayList arrBatchID = null;
	        ArrayList alSearchResults = null;
	        ArrayList downloadResults = null;
	        int count = 0;	
			ArrayList list= new ArrayList();
			StringBuilder query = new StringBuilder(); 
			
			query.append("select LOAN_NO,INSTRUMENT_TYPE,INSTRUMENT_NO,INSTRUMENT_DATE,INSTRUMENT_AMOUNT,BANK_ID,BRANCH_ID,BANK_ACCOUNT,rec_STATUS,REASON_ID,reject_reason from cheque_status_upload_tmp where MAKER_ID='"+makerid+"' and uploaded_flag='N'");
			logger.info("In getUploadError()...............query...........DAOImpl "+query.toString());
			try
			{		
			list=ConnectionDAO.sqlSelect(query.toString());
		   
		          ArrayList alRecord = null;
		          count = list.size();
		            response.setContentType("text/plain");
		            response.setHeader("Content-Disposition", "attachment;filename=ErrorLogUploadFile.csv");	
		            logger.info("Checking header...");
		            txtReport.append("Cheque Bounce/Realization Upload Error \n");	  
		            txtReport.append(UploadDocuments.getPropertiesValue("lbl.LOAN_NO")+"|");
		            txtReport.append(UploadDocuments.getPropertiesValue("lbl.INSTRUMENT_TYPE")+"|");
		            txtReport.append(UploadDocuments.getPropertiesValue("lbl.INSTRUMENT_NO")+"|");
		            txtReport.append(UploadDocuments.getPropertiesValue("lbl.INSTRUMENT_DATE")+"|");
		            txtReport.append(UploadDocuments.getPropertiesValue("lbl.INSTRUMENT_AMOUNT")+"|");
		            txtReport.append(UploadDocuments.getPropertiesValue("lbl.BANK_ID")+"|");
		            txtReport.append(UploadDocuments.getPropertiesValue("lbl.BRANCH_ID")+"|");
		            txtReport.append(UploadDocuments.getPropertiesValue("lbl.BANK_ACCOUNT")+"|");
		            txtReport.append(UploadDocuments.getPropertiesValue("lbl.STATUS")+"|");
		            txtReport.append(UploadDocuments.getPropertiesValue("lbl.REASON_ID")+"|");
		            txtReport.append(UploadDocuments.getPropertiesValue("lbl.Reject_Reason")+"\n");
	  
		            logger.info("Size is:"+count);
		            if(count>0)
		            {
		              for(int i=0; i<count; i++) 
		              {	 
		              alRecord = (ArrayList) list.get(i);
		                      txtReport.append(((CommonFunction.checkNull(alRecord.get(0))).toString() + "|").trim());
		                      txtReport.append(((CommonFunction.checkNull(alRecord.get(1))).toString() + "|").trim());
		                      txtReport.append(((CommonFunction.checkNull(alRecord.get(2))).toString() + "|").trim());		                     
		                      txtReport.append(((CommonFunction.checkNull(alRecord.get(3))).toString() + "|").trim());
		                      txtReport.append(((CommonFunction.checkNull(alRecord.get(4))).toString() + "|").trim());
		                      txtReport.append(((CommonFunction.checkNull(alRecord.get(5))).toString() + "|").trim());
		                      txtReport.append(((CommonFunction.checkNull(alRecord.get(6))).toString() + "|").trim());
		                      txtReport.append(((CommonFunction.checkNull(alRecord.get(7))).toString() + "|").trim());
		                      txtReport.append(((CommonFunction.checkNull(alRecord.get(8))).toString() + "|").trim());
		                      txtReport.append(((CommonFunction.checkNull(alRecord.get(9))).toString() + "|").trim());
		                      txtReport.append(((CommonFunction.checkNull(alRecord.get(10))).toString() + "\n").trim());                 
		                    
		                }
		            }
		            else
		            {
		            	 txtReport.append("No Record Found");
		            }
		         
		    	    ServletOutputStream  out=response.getOutputStream();	     
		            out.print(txtReport.toString());
		    	    out.close();
		            out.flush();	           
		            txtReport = null;
		            status=true;	 
		          }
		          catch (Exception e) 
		          {
		        	  logger.debug("IOException In downLoadErrorLog() ==>> "+e.getMessage());
		          }	
		          finally
		          {
		        	  query = null;
		        	  makerid = null;
		        	  list.clear();
		        	  list=null;
		          }
		       
		          return status;
		}
		




 //code by manisha for paymentMaker search grid data




	




	 

	
	
	
		public String getTxnAdviceId(){
			String adviceId="";
			StringBuilder txnId = new StringBuilder(); 
			try{
					txnId.append("SELECT max(TXNADVICE_ID) from cr_txnadvice_dtl");
				
					adviceId=ConnectionDAO.singleReturn(txnId.toString());
					
		  		logger.info("IN adviceId ### "+txnId);
		    }
		  	   catch(Exception e){
						e.printStackTrace();
					}
		  	   finally
		  	   {
		  		 txnId = null;
		  	   }

					return adviceId;
				}
		
		
		
		
		

		
		
		
	
		
		
		

		
		
		

		public ArrayList <PaymentMakerForCMVO> saveddatatoApprove(PaymentMakerForCMVO paymentMakerForCMVO)
		{

			
		  ArrayList<PaymentMakerForCMVO> datatoapproveList=new ArrayList<PaymentMakerForCMVO>();
		  StringBuffer bufInsSql =	new StringBuffer();
		  ArrayList searchlist=new ArrayList();
		  ArrayList data= null;
		  try{
		    logger.info("In searchAuthorData....................");

		    /*
		      bufInsSql.append("SELECT CLD.LOAN_ID,CLD.LOAN_NO,(SELECT CUSTOMER_NAME FROM gcd_customer_m WHERE  CLD.LOAN_CUSTOMER_ID=CUSTOMER_ID)CUSTOMER_Name," +
		  		" CID.BPTYPE," +
		  		" (select DESCRIPTION from generic_master where GENERIC_KEY='BPTYPE' and PARENT_VALUE='NULL' AND VALUE=CID.BPTYPE)bp_type_DESC," +
		  		" CID.BPID," +
		  		"  (SELECT BP_NAME FROM business_partner_view WHERE  BP_ID=CID.BPID and BP_TYPE=CID.BPTYPE)BPNAME," +
		  		"  CID.INSTRUMENT_MODE,date_format(CID.RECEIVED_DATE,'%d-%m-%Y'),CID.INSTRUMENT_NO,date_format(CID.INSTRUMENT_DATE,'%d-%m-%Y'),CID.ISSUEING_BANK_ACCOUNT,CID.ISSUEING_BANK_ID,(SELECT BANK_NAME FROM com_bank_m where BANK_ID=CID.ISSUEING_BANK_ID)BANK_NAME,CID.ISSUEING_BRANCH_ID" +
		  		"  ,(SELECT BANK_BRANCH_NAME FROM com_bankbranch_m where BANK_BRANCH_ID=CID.ISSUEING_BRANCH_ID)BANK_NAME,CID.ISSUING_MICR_CODE,CID.ISSUING_IFSC_CODE,CID.INSTRUMENT_AMOUNT,CID.TDS_AMOUNT ,CID.REMARKS,CID.INSTRUMENT_ID" +
		  		"  from cr_instrument_dtl CID," +
		  		" cr_loan_dtl CLD" +
		  		" where CID.TXNID=CLD.LOAN_ID" +
		  		" AND CID.REC_STATUS='P'" +
		  		" AND CID.TXNID='"+paymentMakerForCMVO.getLbxLoanNoHID()+"' "+
		        " AND CID.INSTRUMENT_ID='"+paymentMakerForCMVO.getInstrumentID()+"' ");
		  
		    */
		    bufInsSql.append(" SELECT DISTINCT CLD.LOAN_ID,CLD.LOAN_NO,GCM.CUSTOMER_NAME,CID.BPTYPE, " +		  
			  		"   GM.DESCRIPTION,CID.BPID ,BPV.BP_NAME,  " +	
			  		"  CID.INSTRUMENT_MODE,date_format(CID.RECEIVED_DATE,'"+dateFormat+"'),CID.INSTRUMENT_NO,date_format(CID.INSTRUMENT_DATE,'"+dateFormat+"')," +
			  		" CID.ISSUEING_BANK_ACCOUNT,CID.ISSUEING_BANK_ID,(SELECT BANK_NAME FROM com_bank_m where BANK_ID=CID.ISSUEING_BANK_ID)BANK_NAME,CID.ISSUEING_BRANCH_ID" +
			  		"  ,(SELECT BANK_BRANCH_NAME FROM com_bankbranch_m where BANK_BRANCH_ID=CID.ISSUEING_BRANCH_ID)BANK_NAME," +
			  		" CID.ISSUING_MICR_CODE,CID.ISSUING_IFSC_CODE,CID.INSTRUMENT_AMOUNT,CID.TDS_AMOUNT ,CID.REMARKS,CID.INSTRUMENT_ID,CID.MAKER_REMARKS,CID.PAY_TO,CID.PAYEE_NAME" +
			  		"  from cr_instrument_dtl CID,gcd_customer_m GCM ," +
			  		" cr_loan_dtl CLD, business_partner_view BPV,generic_master  GM" +
			  		" where CID.TXNID=CLD.LOAN_ID and ifnull(CID.TXN_TYPE,'')='LIM' " +
			  		"  AND BPV.BP_ID=CID.BPID " +
			        "  AND GM.VALUE=CID.BPTYPE  and CLD.LOAN_CUSTOMER_ID=GCM.CUSTOMER_ID" +
			        "  AND GM.GENERIC_KEY='BPTYPE' " +
			        "  and INSTRUMENT_TYPE='P'AND CID.REC_STATUS='F'" +
			  		" AND CID.TXNID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(paymentMakerForCMVO.getLbxLoanNoHID()).trim())+"' "+
			        " AND CID.INSTRUMENT_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentID()).trim())+"' ");
			  		
			  		     
			  logger.info("In searchAuthorData......... query..........."+bufInsSql.toString());
		    searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
		    logger.info("searchAuthorData search Data size is...."+searchlist.size());

		    for(int i=0;i<searchlist.size();i++){
		   // logger.info("paymentMasearchAuthorDataker search List "+searchlist.get(i).toString());
		    data=(ArrayList)searchlist.get(i);

		    if(data.size()>0){ 	  
			  PaymentMakerForCMVO paymentVO = new PaymentMakerForCMVO();
			  paymentVO.setLbxLoanNoHID((CommonFunction.checkNull(data.get(0)).trim()));
			  paymentVO.setLoanAccountNumber((CommonFunction.checkNull(data.get(1)).trim()));
			  paymentVO.setCustomerName((CommonFunction.checkNull(data.get(2)).trim()));
			  logger.info(" searchPaymentData setCustomerName:-------------"+data.get(2));
			  paymentVO.setLbxBPType((CommonFunction.checkNull(data.get(3)).trim()));
			  paymentVO.setBusinessPartnerType((CommonFunction.checkNull(data.get(4)).trim()));
			  paymentVO.setLbxBPNID((CommonFunction.checkNull(data.get(5)).trim()));
			  paymentVO.setBusinessPartnerName((CommonFunction.checkNull(data.get(6)).trim()));
			  paymentVO.setPaymentMode((CommonFunction.checkNull(data.get(7)).trim()));
			  paymentVO.setPaymentDate((CommonFunction.checkNull(data.get(8)).trim()));
			  paymentVO.setInstrumentNumber((CommonFunction.checkNull(data.get(9)).trim()));
			  paymentVO.setInstrumentDate((CommonFunction.checkNull(data.get(10)).trim()));
			  paymentVO.setBankAccount((CommonFunction.checkNull(data.get(11)).trim()));
			  logger.info(" searchPaymentData setBankAccount:-------------"+data.get(11));
			  paymentVO.setLbxBankID((CommonFunction.checkNull(data.get(12)).trim()));
			  paymentVO.setBank((CommonFunction.checkNull(data.get(13)).trim()));
			  paymentVO.setLbxBranchID((CommonFunction.checkNull(data.get(14)).trim()));
			  paymentVO.setBranch((CommonFunction.checkNull(data.get(15)).trim()));
			  paymentVO.setMicr((CommonFunction.checkNull(data.get(16)).trim()));
			  paymentVO.setIfsCode((CommonFunction.checkNull(data.get(17)).trim()));
			  Number PaymentAmount = myFormatter.parse((CommonFunction.checkNull(data.get(18))).trim());
			  logger.info("PaymentAmount: "+PaymentAmount);			
	    	  paymentVO.setPaymentAmount(myFormatter.format(PaymentAmount));
	    	  if(!CommonFunction.checkNull(data.get(19)).equalsIgnoreCase(""))
	    	  {
	    	  Number TdsAmount = myFormatter.parse((CommonFunction.checkNull(data.get(19))).trim());
			  logger.info("TdsAmount: "+TdsAmount);			
	    	  paymentVO.setTdsAmount(myFormatter.format(TdsAmount));
	    	  }
			  paymentVO.setAuthorRemarks((CommonFunction.checkNull(data.get(20)).trim()));
			  paymentVO.setInstrumentID((CommonFunction.checkNull(data.get(21)).trim()));
			  paymentVO.setRemarks((CommonFunction.checkNull(data.get(22)).trim()));
			  if((CommonFunction.checkNull(data.get(23)).trim()).equalsIgnoreCase("CS"))
			  {
				  paymentVO.setPayTo("CUSTOMER");
			  }
			  else if((CommonFunction.checkNull(data.get(23)).trim()).equalsIgnoreCase("MF"))
			  {
				  paymentVO.setPayTo("MANUFACTURER");
			  }
			  else if((CommonFunction.checkNull(data.get(23)).trim()).equalsIgnoreCase("SU"))
			  {
				  paymentVO.setPayTo("SUPPLIER");
			  }
			/*  else
			  {
				  paymentVO.setPayTo("OTHERS");
			  }*/
			  paymentVO.setPayeeName((CommonFunction.checkNull(data.get(24)).trim()));
			  logger.info(" searchPaymentData setInstrumentID:-------------"+data.get(21));
		  	 

			  datatoapproveList.add(paymentVO);	
		     }

		    }

		}catch(Exception e){
			e.printStackTrace();
				}
		finally{
			paymentMakerForCMVO=null;
			bufInsSql = null;
			searchlist.clear();
			searchlist=null;
			data.clear();
			data= null;
		}
		return  datatoapproveList;	
		}
		
		
		
		
		//code by manisha for Receipt Maker search grid data
		

		
		
	
		 
		
		
		 
		
		

		
		

		
	
		 public  int checkAllocation(ReceiptMakerVO receiptVO){
	  	  int result=0;
	  	StringBuilder existCount = new StringBuilder(); 
	  	StringBuilder res = new StringBuilder(); 

	  	   try{
	  	 existCount.append(" SELECT COUNT(1) FROM cr_pmnt_dtl cid WHERE INSTRUMENT_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(receiptVO.getInstrumentID()).trim())+"'");
	  	logger.info("IN checkAllocation  ### "+existCount.toString());
	  	res.append(ConnectionDAO.singleReturn(existCount.toString()));
	  	logger.info("res-----"+res);
	    if(!(CommonFunction.checkNull(res)).equalsIgnoreCase("")){
	    	
	    	result=Integer.parseInt(res.toString());
	    }
	  		logger.info("IN checkAllocation 1  ### "+existCount.toString());
	    }
	  	   catch(Exception e){
					e.printStackTrace();
				}
	  	   finally
	  	   {
	  		 existCount = null;
	  		 res = null;
	  		 receiptVO=null;
	  	   }

				return result;
			}
	 
		 public String  checkFesiblityOnForward(ReceiptMakerVO receiptVO) {
		// ReceiptMakerVO vo= new ReceiptMakerVO();
	  	 // String query="";
	  	 // String query1="";
	  	StringBuilder query = new StringBuilder(); 
	  	StringBuilder query1 = new StringBuilder(); 
	  	  String status="";
	  	ArrayList<ReceiptMakerVO> List=new ArrayList ();
	  	String tdsAmount= null;
	  	String AllocatedAmount= null;
	  	String TdsAllocatedAmount = null;
         String receiptAmount=  StringEscapeUtils.escapeSql(CommonFunction.checkNull(receiptVO.getReceiptAmount()).trim()); 
         logger.info("In receiptAuthorGrid......receiptAmount  "+receiptAmount);
         double receiptAmnt=0.0;
         double tdsAmnt=0.0;
         try{ 
        	 if(receiptAmount==""){
        	 receiptAmount="0";  
         }else{
        	 receiptAmount= myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(receiptVO.getReceiptAmount()))).toString();
         }
         receiptAmnt=Double.parseDouble(receiptAmount);
         tdsAmount=  StringEscapeUtils.escapeSql(CommonFunction.checkNull(receiptVO.getTdsAmount()).trim()); 
         logger.info("In receiptAuthorGrid......tdsAmount  "+tdsAmount);
         if(tdsAmount==""){
        	 tdsAmount="0";  
         }
         else{
        	 tdsAmount= myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(receiptVO.getTdsAmount()))).toString();
         }
         tdsAmnt=Double.parseDouble(tdsAmount);
         double Amount=(receiptAmnt + tdsAmnt);
         logger.info("In receiptAuthorGrid......Amount  "+Amount);
      
        	 query.append(" SELECT ifnull(SUM(PMNT_AMOUNT),0) FROM cr_pmnt_dtl WHERE INSTRUMENT_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(receiptVO.getInstrumentID()).trim())+"'");
	  	  logger.info("IN checkAllocation  ### "+query);
	     
	      AllocatedAmount=CommonFunction.checkNull(ConnectionDAO.singleReturn(query.toString()));
	      double VAllocatedAmount=0.0;
	      if(AllocatedAmount==""){
	    	  AllocatedAmount="0";
	      }
	    	  VAllocatedAmount=Double.parseDouble(CommonFunction.checkNull(AllocatedAmount));

	  	  logger.info("VAllocatedAmount----"+VAllocatedAmount);
	  	  
	  	 query1.append(" SELECT ifnull(SUM(TDS_AMOUNT),0) FROM cr_pmnt_dtl WHERE INSTRUMENT_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(receiptVO.getInstrumentID()).trim())+"'");
	   		logger.info("IN checkAllocation 1  ### "+query1.toString());
	  		TdsAllocatedAmount =ConnectionDAO.singleReturn(query1.toString());  		 
	  		
	  		double VTdsAllocatedAmount=0.0;
		      if(TdsAllocatedAmount==""){
		    	  TdsAllocatedAmount="0";
		      }
		      VTdsAllocatedAmount=Double.parseDouble(CommonFunction.checkNull(TdsAllocatedAmount));
	     	logger.info("VTdsAllocatedAmount----"+VTdsAllocatedAmount);
		 
	  		if( VAllocatedAmount == 0.0000 ){
	  			status ="DA";
			  }
	  		else  if((Amount>=VAllocatedAmount) && (tdsAmnt==VTdsAllocatedAmount)){
			  
			  status ="A";
		  }
		  else if((Amount < VAllocatedAmount) || (tdsAmnt != VTdsAllocatedAmount)){
			 
			  status ="NA";
	    }
         }
	  	   catch(Exception e){
					e.printStackTrace();
				}
	  	   finally
	  	   {
	  		 query = null;
	  		 query1 = null;
	  		 receiptVO = null;
	  		 List.clear();
	  		 List=null;
	  		 receiptAmount=null;
	  		 tdsAmount= null;
		  	 AllocatedAmount= null;
		  	 TdsAllocatedAmount = null;
	  	   }

				return status ;
			}
	 
	   
		//code by manisha for receipt Author search grid data

		 
	// MANISHA Insurance Asset Maker Code Start

			public boolean deleteAsset(String[] assetId, String loanId) {

				ArrayList qryList = new ArrayList();
				boolean status = false;
				//String q = "";
				StringBuilder q = null;
				 
				logger.info("In deleteAsset.....................................Dao Impl....111");
				try {
					int length = assetId.length;
					for (int i = 0; i < length; i++) {

						q = new StringBuilder();
						
						q.append("delete from  cr_loan_collateral_m where ASSETID='"
								+ assetId[i] + "' and LOAN_ID=" + loanId);
						qryList.add(q.toString());
						q = null;
					}
					status = ConnectionDAO.sqlInsUpdDelete(qryList);
					logger.info("In deleteAsset......................" + status);

				} catch (Exception e) {
					e.printStackTrace();
				}
				finally{
					assetId = null;
					loanId = null;
					q = null;
				}

				return status;
			}
public boolean deleteAssetInsuranceID(String[]LoanID,String[]AssetID) {
	 
	
	ArrayList qryList =  new ArrayList();
	boolean status =false;
	
	StringBuffer bufInsSql =null;
	PrepStmtObject insertPrepStmtObject = null;	
	

	
	logger.info("In deleteAssetInsuranceID...Dao Impl.");
	 
	  
	try{
		
		for(int i =0;i<LoanID.length;i++){
		 bufInsSql =	new StringBuffer();
		 insertPrepStmtObject = new PrepStmtObject();
	 
			 bufInsSql.append("delete from cr_asset_insurance_dtl where ASSET_ID = ? and LOAN_ID = ?");

				 if(CommonFunction.checkNull(AssetID[i]).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(AssetID[i]).trim()));
				 
				 if(CommonFunction.checkNull(LoanID[i]).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(LoanID[i]).trim()));
				 
					
					insertPrepStmtObject.setSql(bufInsSql.toString());
					qryList.add(insertPrepStmtObject);
	}
		    logger.info("delete:-"+insertPrepStmtObject.printQuery());
		     status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
	
	
	
}catch(Exception e){
	logger.debug("IOException In deleteAssetInsuranceID() ==>> "+e.getMessage());
}
finally{
	LoanID = null;
	AssetID = null;
	qryList.clear();
	qryList=null;
}

return status;
}

      // MANISHA Insurance Asset Maker Code End
           
         //MANISHA Insurance Asset Author Code Start 
//		bufInsSql.append("update cr_asset_insurance_dtl set REC_STATUS=? ,REMARKS=? WHERE ASSET_ID = ? AND ASSET_INSURANCE_ID=? and REC_STATUS ='F'");	
//		
//		 if(CommonFunction.checkNull(assetMakervo.getDecision()).equalsIgnoreCase(""))
//				updatePrepStmtObject.addNull();
//			else
//				updatePrepStmtObject.addString(StringEscapeUtils.escapeSql(assetMakervo.getDecision()).trim());
//		 if(CommonFunction.checkNull(assetMakervo.getComments()).equalsIgnoreCase(""))
//				updatePrepStmtObject.addNull();
//			else
//				updatePrepStmtObject.addString(StringEscapeUtils.escapeSql(assetMakervo.getComments()).trim());
//		 
//		 if(CommonFunction.checkNull(assetMakervo.getAssetID()).equalsIgnoreCase(""))
//				updatePrepStmtObject.addNull();
//			else
//				updatePrepStmtObject.addString(StringEscapeUtils.escapeSql(assetMakervo.getAssetID()).trim());
//			   
//		 if(CommonFunction.checkNull(assetMakervo.getLbxAssetID()).equalsIgnoreCase(""))
//				updatePrepStmtObject.addNull();
//			else
//				updatePrepStmtObject.addString(StringEscapeUtils.escapeSql(assetMakervo.getLbxAssetID()).trim());
//		 
//			    updatePrepStmtObject.setSql(bufInsSql.toString());
//		
//			logger.info("IN approveRejByAuthor query1 ### "+updatePrepStmtObject.printQuery());
//			queryList.add(updatePrepStmtObject);
//			status =ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);
//			logger.info("In approveRejByAuthor....................."+status);
//		}
//	}catch(Exception e){
//		e.printStackTrace();
//	}
//
//	return status;
//}
//   	 public boolean rejByAuthor(AssetForCMVO assetMakervo,int assetInsuranceID) {
//	 		boolean status=false;
//	 		 
//	 		CallableStatement cst=null;
//	 		 int statusProc=0;
//	 		 Connection con=ConnectionDAO.getConnection();
//	 		
//	 		try{
//	 	    StringBuffer bufInsSql =	new StringBuffer();
//	 		ArrayList queryList=new ArrayList();
//	 		PrepStmtObject updatePrepStmtObject = new PrepStmtObject();
//	 		
//			bufInsSql.append("update cr_asset_insurance_dtl set REC_STATUS=? ,REMARKS=? WHERE ASSET_ID = ? AND ASSET_INSURANCE_ID=? and REC_STATUS ='F'");	
//			
//			 if(CommonFunction.checkNull(assetMakervo.getDecision()).equalsIgnoreCase(""))
//					updatePrepStmtObject.addNull();
//				else
//					updatePrepStmtObject.addString(StringEscapeUtils.escapeSql(assetMakervo.getDecision()).trim());
//			 if(CommonFunction.checkNull(assetMakervo.getComments()).equalsIgnoreCase(""))
//					updatePrepStmtObject.addNull();
//				else
//					updatePrepStmtObject.addString(StringEscapeUtils.escapeSql(assetMakervo.getComments()).trim());
//			 
//			 if(CommonFunction.checkNull(assetMakervo.getAssetID()).equalsIgnoreCase(""))
//					updatePrepStmtObject.addNull();
//				else
//					updatePrepStmtObject.addString(StringEscapeUtils.escapeSql(assetMakervo.getAssetID()).trim());
//				   
//			 if(CommonFunction.checkNull(assetMakervo.getLbxAssetID()).equalsIgnoreCase(""))
//					updatePrepStmtObject.addNull();
//				else
//					updatePrepStmtObject.addString(StringEscapeUtils.escapeSql(assetMakervo.getLbxAssetID()).trim());
//			 
//				    updatePrepStmtObject.setSql(bufInsSql.toString());
//			
//				logger.info("IN approveRejByAuthor query1 ### "+updatePrepStmtObject.printQuery());
//				queryList.add(updatePrepStmtObject);
//				status =ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);
//				logger.info("In approveRejByAuthor....................."+status);
//	    
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//
//		return status;
//	}


       // manisha early clousre viewPayable

		 	//pool Creation
		    
	     
            //PoolId Add Edit Search
                                                                          
                  public boolean updateInstallPlan(InstallmentPlanForCMVO ipvo) {
                  	 
                   	logger.info("In updateInstallPlan");
            			String FromInstallment[] = ipvo.getFromInstall();
            			logger.info("In FromInstallment.................."+ipvo.getFromInstall());
            			logger.info("In FromInstallment....."+FromInstallment.length);
            			String ToInstallment[] = ipvo.getToInstall();
            			logger.info("In ToInstallment.................."+ToInstallment.length);
            			String RecoveryPercen[] = ipvo.getRecoveryPer();
            			logger.info("In RecoveryPercen.................."+RecoveryPercen.length);
            			String InstallmentType= ipvo.getInstallmentType();
            			logger.info("In saveInstallPlan.................."+ipvo.getInstallmentType());
            			String TotalInstallment=ipvo.getTotalInstallment();
            			logger.info("In saveInstallPlan.................."+ipvo.getTotalInstallment());
            			String toInstallment = ipvo.getToInstallment();	
            			logger.info("In toInstallment.................."+toInstallment);
            			ArrayList qryList=new ArrayList();
            			boolean status=false;
            			StringBuffer bufInsSql =null;
            			try {
            			
            				 for(int k=0;k<FromInstallment.length;k++)  
            				{
            				  PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
            				   bufInsSql =	new StringBuffer();
            				   
            				   bufInsSql.append("update cr_installment_plan set FROM_INSTL_NO=?,TO_INSTL_NO=?,RECOVERY_PERCENT=?,MAKER_ID=?,MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),PRINCIPAL_AMOUNT='0',INSTALLMENT_AMOUNT='0',REC_STATUS='P' where LOAN_ID=? ");
            						
            						if(CommonFunction.checkNull(FromInstallment[k]).trim().equalsIgnoreCase(""))
                						insertPrepStmtObject.addNull();
                					else
                						insertPrepStmtObject.addString(FromInstallment[k].trim()); //From Installment
                					
                			    	if(CommonFunction.checkNull(ToInstallment[k]).trim().equalsIgnoreCase(""))
                						insertPrepStmtObject.addNull();
                					else
                						insertPrepStmtObject.addString(ToInstallment[k].trim());// To Installment
                					
                			    	if((CommonFunction.checkNull(RecoveryPercen[k])).trim().equalsIgnoreCase(""))
                			    		insertPrepStmtObject.addString("0.00");
            						else
            							insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(RecoveryPercen[k]).trim())).toString());
                			    	//---------------------------------------------------------
                       			 	if ((CommonFunction.checkNull(ipvo.getMakerId())).trim().equalsIgnoreCase(""))
                       			 		insertPrepStmtObject.addNull();
                       			 	else
                       			 		insertPrepStmtObject.addString((ipvo.getMakerId()).trim());
                       			 	if ((CommonFunction.checkNull(ipvo.getMakerDate()).trim()).equalsIgnoreCase(""))
                       			 		insertPrepStmtObject.addNull();
                       			 	else
                       			 		insertPrepStmtObject.addString(CommonFunction.checkNull(ipvo.getMakerDate()).trim());
                       			 	//---------------------------------------------------------
            						
            						if(CommonFunction.checkNull(ipvo.getLoanId()).trim().equalsIgnoreCase(""))
            							insertPrepStmtObject.addNull();
            						else
            							insertPrepStmtObject.addString(ipvo.getLoanId().trim());   // loan Id
            						   						
            				
            		
            					insertPrepStmtObject.setSql(bufInsSql.toString());
            					//logger.info("IN updateInstallPlan() insert query1 ### "+insertPrepStmtObject.printQuery());
            					qryList.add(insertPrepStmtObject);
            				  
            				}
            				
            				
            					status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
            				} catch (Exception e) {
            					e.printStackTrace();
            				}
            				finally{
            					ipvo =null;
            					FromInstallment =null;
                    			ToInstallment =null;
                    			RecoveryPercen =null;
                    			InstallmentType =null;
                    			TotalInstallment =null;
                    			toInstallment =null;
                    			qryList.clear();
                    			qryList=null;
                    			bufInsSql =null;
            				}
            			    logger.info("In updateInstallPlan......................"+status);
            			return status;
            		}    
      	//manisha wiave off viewPayable
      		
                   
                  
           	 	//Neeraj Customer Service Search 
       
         
         
		public boolean saveChargesInCm(ChargeVo ob) {
			
			ChargeVo vo = (ChargeVo)ob;
			logger.info("In saveChargesInCm....saveChargesInCm...Insert mod Loan Id: "+vo.getLoanId());
		    ArrayList qryList=new ArrayList();
		    PrepStmtObject insertPrepStmtObject = null;
	    		String dId[] = vo.getChargeIdDtl();
	    		String finalAmount[] = vo.getFinalAmount();
	    		String chargeAmount[]=vo.getChargeAmount();


	    		
	    		String taxAmount1[] = vo.getDealChargeTaxAmountInConfig1();
	    		String taxAmount2[]=vo.getDealChargeTaxAmountInConfig2();
	    		String tdsAmount[] = vo.getDealChargeTdsAmountInConfig();
	    		String netAmount[]=vo.getDealChargeNetAmountInConfig();
	    		
	    		//String qcheck="select distinct TXN_ID from cr_txncharges_dtl where TXN_ID="+vo.getLoanId()+" and TXN_TYPE='LIM'";
	    		//boolean st=ConnectionDAO.checkStatus(qcheck);

	    		for(int k=0;k<vo.getChargeIdDtl().length; k++)
	    		{
	    		/* if(!st)
	    		 { 
	    			 logger.info("Charge Id in saveCharges: "+dId[k]);
		    		 query="insert into cr_txncharges_dtl(TXN_TYPE,TXN_ID,DEAL_CHARGE_ID,DEAL_CHARGE_CODE,DEAL_CHARGE_APPLICATION_STAGE,"+
		    					" DEAL_CHARGE_TYPE,DEAL_CHARGE_METHOD,DEAL_CHARGE_BP_TYPE,DEAL_CHARGE_BP_ID,DEAL_CHARGE_CALCULATED_AMOUNT,"+
		    					" DEAL_CHARGE_FINAL_AMOUNT,DEAL_CHARGE_TDS_AMOUNT,DEAL_CHARGE_NET_AMOUNT,"+
		    					" DEAL_CHARGE_TAX_APPLICABLE,DEAL_CHARGE_TAX_INCLUSIVE,DEAL_CHARGE_TAX_AMOUNT1,DEAL_CHARGE_TAX_AMOUNT2,REC_STATUS,MAKER_ID,MAKER_DATE," +
		    					" DEAL_CHARGE_CALCULATED_ON,DEAL_CHARGE_SLAB_ON,DEAL_CHARGE_TDS_APPLICABLE,DEAL_CHARGE_TDS_RATE,DEAL_CHARGE_TAX_RATE1,DEAL_CHARGE_TAX_RATE2)"+
		    					" (select 'LIM',"+CommonFunction.checkNull((vo.getLoanId()).trim())+",DEAL_CHARGE_ID,DEAL_CHARGE_CODE,DEAL_CHARGE_APPLICATION_STAGE,"+
		    						" DEAL_CHARGE_TYPE,DEAL_CHARGE_METHOD,DEAL_CHARGE_BP_TYPE,DEAL_CHARGE_BP_ID,DEAL_CHARGE_CALCULATED_AMOUNT,"+
		    						" '"+finalAmount[k]+"',DEAL_CHARGE_TDS_AMOUNT,DEAL_CHARGE_NET_AMOUNT,DEAL_CHARGE_TAX_APPLICABLE,DEAL_CHARGE_TAX_INCLUSIVE,"+
		    						" DEAL_CHARGE_TAX_AMOUNT1,DEAL_CHARGE_TAX_AMOUNT2,'P','"+CommonFunction.checkNull((vo.getUserId()).trim())+"',STR_TO_DATE('"+CommonFunction.checkNull((vo.getBussinessDate()).trim())+"','"+dateFormatWithTime+"'),DEAL_CHARGE_CALCULATED_ON," +
		    					    " DEAL_CHARGE_SLAB_ON,DEAL_CHARGE_TDS_APPLICABLE,DEAL_CHARGE_TDS_RATE,DEAL_CHARGE_TAX_RATE1,DEAL_CHARGE_TAX_RATE2 from cr_txncharges_dtl"+
		    						" where DEAL_CHARGE_DTL_ID="+dId[k]+")";
	    		 }
	    		 else
	    		 {*/
	    			  //query="update cr_txncharges_dtl set DEAL_CHARGE_FINAL_AMOUNT='"+finalAmount[k]+"',DEAL_CHARGE_CALCULATED_AMOUNT=?,,REC_STATUS='P',MAKER_ID='"+CommonFunction.checkNull((vo.getUserId()).trim())+"',MAKER_DATE=STR_TO_DATE('"+CommonFunction.checkNull((vo.getBussinessDate()).trim())+"','"+dateFormatWithTime+"') where DEAL_CHARGE_DTL_ID='"+dId[k]+"'";
	    		// }
	    			StringBuilder query = new StringBuilder(); 
	    			query.append("update cr_txncharges_dtl set DEAL_CHARGE_FINAL_AMOUNT=?,DEAL_CHARGE_CALCULATED_AMOUNT=?,DEAL_CHARGE_TAX_AMOUNT1=?,DEAL_CHARGE_TAX_AMOUNT2=?,DEAL_CHARGE_TDS_AMOUNT=?,DEAL_CHARGE_NET_AMOUNT=?,REC_STATUS=?,MAKER_ID=?,MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND)  where DEAL_CHARGE_DTL_ID=?");
	    			insertPrepStmtObject = new PrepStmtObject();
	    			
	    			if((CommonFunction.checkNull(finalAmount[k])).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						try {
							insertPrepStmtObject.addString(myFormatter.parse((finalAmount[k]).trim()).toString());
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						//insertPrepStmtObject.addString((finalAmount[k]).trim());
	    			
	    			if((CommonFunction.checkNull(chargeAmount[k])).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						try {
							insertPrepStmtObject.addString(myFormatter.parse((chargeAmount[k]).trim()).toString());
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						if((CommonFunction.checkNull(taxAmount1[k])).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							try {
								insertPrepStmtObject.addString(myFormatter.parse((taxAmount1[k]).trim()).toString());
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						
							//insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(finalAmount[k]).trim());
						
						if((CommonFunction.checkNull(taxAmount2[k])).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							try {
								insertPrepStmtObject.addString(myFormatter.parse((taxAmount2[k]).trim()).toString());
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							if((CommonFunction.checkNull(tdsAmount[k])).trim().equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								try {
									insertPrepStmtObject.addString(myFormatter.parse((tdsAmount[k]).trim()).toString());
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
						
								//insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(finalAmount[k]).trim());
							
							if((CommonFunction.checkNull(netAmount[k])).trim().equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								try {
									insertPrepStmtObject.addString(myFormatter.parse((netAmount[k]).trim()).toString());
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
						//insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(chargeAmount[k]).trim());
	    			
	    			insertPrepStmtObject.addString("P");
	    			
	    			if((CommonFunction.checkNull(vo.getUserId())).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getUserId()));
	    			
	    			if((CommonFunction.checkNull(vo.getBussinessDate())).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getBussinessDate()));
	    			
	    			if((CommonFunction.checkNull(dId[k])).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((dId[k]).trim());
	    			
	    			insertPrepStmtObject.setSql(query.toString());
					logger.info("IN saveChargesInCm() insert query1 ### "+insertPrepStmtObject.printQuery());
	    			qryList.add(insertPrepStmtObject);
	    			query = null;
	    		}
	    		//qryList.add(query);
	    		
	    	
		boolean status=false;
		try {
			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			ob =null;
			qryList.clear();
			qryList =null;
		    insertPrepStmtObject = null;
	    	dId =null;
	    	finalAmount =null;
	    	chargeAmount =null;
	    	taxAmount1 =null;
			taxAmount2 =null;
			tdsAmount =null;
			netAmount =null;
		}
	    logger.info("In saveChargesInCm......................"+status);
	    return status;
  }
	
	
public ArrayList<Object> getLoanDetailsDataR(String loanNumber)
		{			
			logger.info("Value of Loan Number in DAO Implementation is="+loanNumber);
			ArrayList<Object> loandetailList = new ArrayList<Object>();
			KnockOffMakerVO vo = null;
			StringBuilder query = new StringBuilder(); 
			try
		       {		    	  
				
				//String query="Select l.LOAN_ID,l.LOAN_NO,d.CHARGE_DESC,c.TXNADVICE_ID,c.ADVICE_AMOUNT,c.TXN_ADJUSTED_AMOUNT,c.AMOUNT_IN_PROCESS from cr_txnadvice_dtl c left join cr_loan_dtl l on l.LOAN_ID=c.LOAN_ID left join com_charge_code_m d on c.CHARGE_CODE_ID=d.CHARGE_CODE where c.ADVICE_TYPE='R' and l.LOAN_ID="+loanNumber;
				query.append("Select l.LOAN_ID,l.LOAN_NO,d.CHARGE_DESC,c.TXNADVICE_ID,c.ADVICE_AMOUNT,c.TXN_ADJUSTED_AMOUNT,c.AMOUNT_IN_PROCESS,e.KNOCKOFF_ID,e.KNOCKOFF_AMOUNT from cr_txnadvice_dtl c left join cr_loan_dtl l on l.LOAN_ID=c.LOAN_ID left join com_charge_code_m d on c.CHARGE_CODE_ID=d.CHARGE_CODE left join cr_knockoff_dtl e on c.txnadvice_id =e.txn_advice_id where c.ADVICE_TYPE='R' and l.LOAN_ID="+loanNumber);
				//String query="Select l.LOAN_ID,l.LOAN_NO,d.CHARGE_DESC,c.TXNADVICE_ID,c.ADVICE_AMOUNT,c.TXN_ADJUSTED_AMOUNT,(c.AMOUNT_IN_PROCESS-e.KNOCKOFF_AMOUNT)AMOUNT_IN_PROCESS,e.KNOCKOFF_AMOUNT from cr_txnadvice_dtl c left join cr_loan_dtl l on l.LOAN_ID=c.LOAN_ID left join com_charge_code_m d on c.CHARGE_CODE_ID=d.CHARGE_CODE right join cr_knockoff_dtl e on c.txnadvice_id =e.txn_advice_id where c.ADVICE_TYPE='R' and l.LOAN_ID="+loanNumber;
							
				logger.info("query"+query);	               
	            ArrayList bdetails = ConnectionDAO.sqlSelect(query.toString());
	            ArrayList bdetails1= null;
		    	logger.info("getReceivable Advice Data: "+bdetails.size());	   	    
		    	for(int i=0;i<bdetails.size();i++)
		    	  {
		    	//  logger.info("getPayable Advice Data: " +bdetails.get(i).toString());
		    	   bdetails1=(ArrayList)bdetails.get(i);
		    	    	if(bdetails1.size()>0)
		    			{
		    	    	   vo =  new KnockOffMakerVO();	
		    	    	   vo.setLoanId((CommonFunction.checkNull(bdetails1.get(0)).trim()));
		    	    		vo.setLoanNumber((CommonFunction.checkNull(bdetails1.get(1)).trim()));	    	    		
		    	    		vo.setCharges((CommonFunction.checkNull(bdetails1.get(2)).trim()));
		    	    		vo.setTxnAdviceIdR((CommonFunction.checkNull(bdetails1.get(3)).trim()));    	    		
		    	    		vo.setOriginalAmountR((CommonFunction.checkNull(bdetails1.get(4)).trim())); 
		    	    	    vo.setAdjustedAmount((CommonFunction.checkNull(bdetails1.get(5)).trim()));
		    	    	    double original_amount = Double.parseDouble(bdetails1.get(4).toString());		    	    	   
		    	    	    double adjusted_amount = Double.parseDouble(bdetails1.get(5).toString());
		    	    	    double balance_amount = original_amount - adjusted_amount;
		    	    	    vo.setBalanceAmountR((CommonFunction.checkNull(balance_amount).trim()));
		    	    	    vo.setAmountInProcessR((CommonFunction.checkNull(bdetails1.get(6)).trim()));
		    	    	    vo.setKnockOffIdR((CommonFunction.checkNull(bdetails1.get(7)).trim()));
		    	    	    vo.setKnockOffAmountR((CommonFunction.checkNull(bdetails1.get(8)).trim()));
		    			}
		    			
		    	    	loandetailList.add(vo);
		    		}
				    	bdetails.clear();
				    	bdetails= null;
			            bdetails1.clear();
			            bdetails1=null;
		    		}catch(Exception e){
		    			e.printStackTrace();
		    		}
		    		finally
		    		{
		    			query = null;
		    			loanNumber = null;
		    			vo=null;
		    		}
		    		logger.info("Value of List is in dao is="+loandetailList);
		    		return loandetailList;				
		    	}
		              
		
	
	
//ravindra manual Advice  starts	




public boolean rejectManualAdviceByAuthor(Object ob) {
	boolean status = false;
	ManualAdviceCreationVo vo = (ManualAdviceCreationVo) ob;
	StringBuilder query = new StringBuilder(); 
	ArrayList queryList = null;
	try {

		queryList = new ArrayList();
		query.append("update cr_manual_advice_dtl set REC_STATUS='X' where MANUAL_ADVICE_ID='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getManaulId()).trim()) + "'");

		queryList.add(query.toString());
		logger.info("In getListOfValues" + query.toString());
		status = ConnectionDAO.sqlInsUpdDelete(queryList);
		logger.info("In getListOfValues,,,,," + status);

	} catch (Exception e) {
		e.printStackTrace();
	}
	finally
	{
		query = null;
		vo= null;
		queryList.clear();
		queryList = null;
	}

	return status;
}



// ravindra data ends

		//Abhimanyu kumar Process method...
		
	
		
		
		public boolean startEodBodProcess()
		{
			boolean status=false;
			logger.info("In ..startEodBodProcess() of CM DAIO IMPL...");
			return status;
		}
		
		
		
		

		
		
		

		
		
		
		public boolean startBodProcess()
		{
			boolean status=false;
			logger.info("In ..startBodProcess() of CM DAIO IMPL...");
			return status;
		}
		
			
				
		// Abhimanyu methods start here....

		
	
		
		public boolean rejectWaiveOffAuthor(Object ob) {
			boolean status = false;
			WaiveOffVO vo = (WaiveOffVO) ob;
			StringBuilder query = new StringBuilder(); 
			ArrayList queryList = null;
			try {

				queryList = new ArrayList();
				query.append("update cr_waiveoff_dtl set REC_STATUS='X' where WAIVEOFF_ID='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getWaveOffId()).trim()) + "'");

				queryList.add(query.toString());
				logger.info("In getListOfValues" + query.toString());
				status = ConnectionDAO.sqlInsUpdDelete(queryList);
				logger.info("In getListOfValues,,,,," + status);

			} catch (Exception e) {
				e.printStackTrace();
			}
			finally
			{
				query = null;
				queryList.clear();
				queryList = null;
				vo=null;
			}

			return status;
		}
		
//		End Of abhi , Ravi code
		
		

	

		public String checkDocMatrix(DocumentsVo vo)
		{
			String status="";
			logger.info("In checkDocMatrix...");
			String dId[] = vo.getDocumentId();
			String dstatus[] = vo.getStatus();
			String drec[] = vo.getRecievedDate();
			String ddef[] = vo.getDeferedDate();
			String expDate[]=vo.getExpiryDate();
			String dremark[] = vo.getRemarks();
			for(int k=0;k<vo.getDocumentId().length; k++)
			{
				StringBuilder query = new StringBuilder(); 
				StringBuilder docStatus = new StringBuilder();
				
				query.append("select doc_status from cr_document_dtl where txn_doc_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dId[k]).trim())+"");
				//logger.info("query: "+query.toString());
				docStatus.append(CommonFunction.checkNull(ConnectionDAO.singleReturn(query.toString())));
				//logger.info("docStatus: "+docStatus);
				
//				if((docStatus.toString()).equalsIgnoreCase("P"))
//				{
//					if(dstatus[k].equalsIgnoreCase("R") || dstatus[k].equalsIgnoreCase("W") 
//							|| dstatus[k].equalsIgnoreCase("D") || dstatus[k].equalsIgnoreCase("P"))
//						status="Y";
//					else if(dstatus[k].equalsIgnoreCase("L"))
//						status="CANNOTREALEASED";
//				}
				if((docStatus.toString()).equalsIgnoreCase("R"))
				{
					if(dstatus[k].equalsIgnoreCase("L") || dstatus[k].equalsIgnoreCase("R") )
						status="Y";
					else if(dstatus[k].equalsIgnoreCase("P") || dstatus[k].equalsIgnoreCase("W")
							|| dstatus[k].equalsIgnoreCase("D"))
						status="CANNOT-P,W,D";
				}
				if((docStatus.toString()).equalsIgnoreCase("W"))
				{
					if(dstatus[k].equalsIgnoreCase("W") || dstatus[k].equalsIgnoreCase("L")
							|| dstatus[k].equalsIgnoreCase("D") || dstatus[k].equalsIgnoreCase("R"))
						status="Y";
					else
						status="ONLY-P,L,D,R";
				}
				if((docStatus.toString()).equalsIgnoreCase("D"))
				{
					if(dstatus[k].equalsIgnoreCase("W") || dstatus[k].equalsIgnoreCase("R") 
							|| dstatus[k].equalsIgnoreCase("D"))
						status="Y";
					else if(dstatus[k].equalsIgnoreCase("P") || dstatus[k].equalsIgnoreCase("L"))
						status="CANNOT-P,L";
				}
				if((docStatus.toString()).equalsIgnoreCase("L"))
				{
					if(dstatus[k].equalsIgnoreCase("R"))
						status="Y";
					else
						status="ONLYRECIEVED";
				}
				query = null;
				docStatus = null;
			}

			return status;
		}


		public boolean saveDocumentPOD(Object ob) {
			DocumentsVo vo = (DocumentsVo)ob;
			logger.info("In saveDocumentPOD()........");
			String dId[] = vo.getDocumentId();
			String dvDocType[] = vo.getvDocType();
			String dstatus[] = vo.getStatus();
			String dStatusPrev[] = vo.getStatusPrev();
			String drec[] = vo.getRecievedDate();
			String ddef[] = vo.getDeferedDate();
			String expDate[]=vo.getExpiryDate();
			String dremark[] = vo.getRemarks();
			String dDocChildIds[] = vo.getChildId();
			String chk[] = vo.getChk();
			ArrayList qryList=new ArrayList();
			
			int size=vo.getDocumentId().length;
			for(int k=0;k<size; k++)
			{
				
				StringBuilder query1 = new StringBuilder(); 
				StringBuilder query = new StringBuilder(); 
				StringBuilder docStatus= new StringBuilder(); 

				query1.append("select doc_status from cr_document_dtl_temp where TXN_DOC_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dId[k]).trim())+"");
				docStatus.append(CommonFunction.checkNull(ConnectionDAO.singleReturn(query1.toString())));
				query.append("update cr_document_dtl_temp set doc_status_prev='"+docStatus+"',MAKER_ID='"+CommonFunction.checkNull(vo.getUserId().trim())+"'");
				query.append(",MAKER_DATE=DATE_ADD(STR_TO_DATE('"+CommonFunction.checkNull(vo.getBussinessDate().trim())+"', '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND)");
				query.append(",DOC_CHILD_IDS='"+CommonFunction.checkNull(dDocChildIds[k]).trim()+"' where TXN_DOC_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dId[k]).trim())+"");
				PrepStmtObject prepStmtObject = new PrepStmtObject();
				prepStmtObject.setSql(query.toString());
				ArrayList insUpdate = new ArrayList();
				insUpdate.add(prepStmtObject);	
				query1 = null;
				query = null;
				docStatus = null;
				boolean stat = false;
				try
				{
					stat = ConnectionDAO.sqlInsUpdDeletePrepStmt(insUpdate);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				    PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
					StringBuffer bufInsSql =	new StringBuffer();
					bufInsSql.append("update cr_document_dtl_temp set DOC_STATUS=?,");
					bufInsSql.append(" DOC_RECEIVED_DATE=");
					bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"'), ");
					bufInsSql.append(" DOC_DEFFRED_DATE=");
					bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"'),DOC_EXPIRY_DATE=");
					bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"'), " ); //deal id
					bufInsSql.append(" DOC_REMARKS=?,REC_STATUS=?,MAKER_ID=?,DOCUMENT_TYPE=?,");
					bufInsSql.append(" MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND)");
					bufInsSql.append(" where TXN_DOC_ID=? and rec_status ='P'"); //query remarks
									
					if((CommonFunction.checkNull(dstatus[k]).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((CommonFunction.checkNull(dstatus[k]).trim()));

					try{

					if((CommonFunction.checkNull(drec[k]).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((CommonFunction.checkNull(drec[k]).trim()));
					}
				    catch (ArrayIndexOutOfBoundsException aie) {
				          insertPrepStmtObject.addNull();
				       }
				      try {

					if((CommonFunction.checkNull(ddef[k]).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((CommonFunction.checkNull(ddef[k]).trim()));
				      }
					catch (ArrayIndexOutOfBoundsException aie) {
				        insertPrepStmtObject.addNull();
				      }

					if((CommonFunction.checkNull(expDate[k]).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((CommonFunction.checkNull(expDate[k]).trim()));
					
					if((CommonFunction.checkNull(dremark[k]).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((CommonFunction.checkNull(dremark[k]).trim()));
					
					insertPrepStmtObject.addString("P");
					
					if((CommonFunction.checkNull(vo.getUserId()).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getUserId()).trim()));
					
					if((CommonFunction.checkNull(dvDocType[k])).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((dvDocType[k]).trim());
										if((CommonFunction.checkNull(vo.getBussinessDate()).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getBussinessDate()).trim())); //maker_date
					
					
					if((CommonFunction.checkNull(dId[k]).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((CommonFunction.checkNull(dId[k]).trim())); //maker_date
					
					insertPrepStmtObject.setSql(bufInsSql.toString());
					qryList.add(insertPrepStmtObject);

			}
			
//			Changes By Amit Starts
			StringBuffer bufInsSql2 =	new StringBuffer();
			bufInsSql2.append("select count(1) from cr_document_dtl_temp where txn_type='"+CommonFunction.checkNull(vo.getTxnType()).trim()+"'");
			bufInsSql2.append(" and txnid='"+CommonFunction.checkNull((vo.getTxnId()).trim())+"' " );
			bufInsSql2.append(" and stage_id='"+CommonFunction.checkNull((vo.getStageId()).trim())+"' " );
			bufInsSql2.append(" and doc_type='"+CommonFunction.checkNull((vo.getDocType()).trim())+"' " );
			bufInsSql2.append(" and doc_id is null ");
			String countStr = ConnectionDAO.singleReturn(bufInsSql2.toString());
			logger.info("Count before delete: "+countStr);
			
			StringBuffer bufInsSql1 =	new StringBuffer();
			PrepStmtObject insertPrepStmtObject1 = new PrepStmtObject();
			if(Integer.parseInt(CommonFunction.checkNull(countStr))>0)				
			{
				bufInsSql1.append("delete from cr_document_dtl_temp where txn_type='"+CommonFunction.checkNull(vo.getTxnType()).trim()+"'" );
				bufInsSql1.append(" and txnid='"+CommonFunction.checkNull((vo.getTxnId()).trim())+"' " );
				bufInsSql1.append(" and stage_id='"+CommonFunction.checkNull((vo.getStageId()).trim())+"' " );
				bufInsSql1.append(" and doc_type='"+CommonFunction.checkNull((vo.getDocType()).trim())+"' " );
				bufInsSql1.append(" and doc_id is null ");
				insertPrepStmtObject1.setSql(bufInsSql1.toString());
				logger.info("IN saveDocumentPOD() delete query1 ### "+insertPrepStmtObject1.printQuery());
				qryList.add(insertPrepStmtObject1);
			}
			if(chk.length>0)
			{
					StringBuffer maxIdInTemp =	new StringBuffer();
					maxIdInTemp.append("select max(TXN_DOC_ID) from cr_document_dtl_temp ");
					int maxId=Integer.parseInt(CommonFunction.checkNull(ConnectionDAO.singleReturn(maxIdInTemp.toString())));
				int sizeDoc=chk.length;
				for(int i=0;i<sizeDoc;i++,maxId++)
				{
				    PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
					StringBuffer bufInsSql =	new StringBuffer();
					
					bufInsSql.append("insert into cr_document_dtl_temp (TXN_TYPE, TXNID, STAGE_ID, DOC_TYPE, ENTITY_ID,");
					bufInsSql.append(" DOC_ID, DOC_DESC, DOC_STATUS, DOC_STATUS_PREV, DOC_RECEIVED_DATE, DOC_DEFFRED_DATE, ");
					bufInsSql.append(" DOC_EXPIRY_DATE, DOC_REMARKS, Doc_Expiry_Flag, DOC_CHILD_IDS, DOC_MANDATORY," );
					bufInsSql.append(" DOC_ORIGINAL, DOC_CHILD_FLAG, REC_STATUS, MAKER_ID,MAKER_DATE,TXN_DOC_ID,DOCUMENT_TYPE)");
					
					bufInsSql.append(" values ( ");
					bufInsSql.append(" ?," ); //TXN_TYPE
					bufInsSql.append(" ?," ); //TXNID
					bufInsSql.append(" ?," ); //STAGE_ID				
					bufInsSql.append(" ?," ); //DOC_TYPE
					bufInsSql.append(" ?," ); //ENTITY_ID
					bufInsSql.append(" ?," ); //DOC_ID
					bufInsSql.append(" ?," ); //DOC_DESC
					bufInsSql.append(" ?," ); //DOC_STATUS
					bufInsSql.append(" ?," ); //DOC_STATUS_PREV
					bufInsSql.append(" STR_TO_DATE(?, '"+dateFormat+"')," ); //DOC_RECEIVED_DATE
					bufInsSql.append(" STR_TO_DATE(?, '"+dateFormat+"')," ); //DOC_DEFFRED_DATE
					bufInsSql.append(" STR_TO_DATE(?, '"+dateFormat+"')," ); //DOC_EXPIRY_DATE
					bufInsSql.append(" ?," ); //DOC_REMARKS
					bufInsSql.append(" ?," ); //Doc_Expiry_Flag
					bufInsSql.append(" ?," ); //DOC_CHILD_IDS
					bufInsSql.append(" ?," ); //DOC_MANDATORY
					bufInsSql.append(" ?," ); //DOC_ORIGINAL
					bufInsSql.append(" ?," ); //DOC_CHILD_FLAG
					bufInsSql.append(" ?," ); //REC_STATUS
					bufInsSql.append(" ?," ); //MAKER_ID
					bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) ," );//MAKER_DATE
					bufInsSql.append(" ?," ); //TXN_DOC_ID
					bufInsSql.append(" ?)" ); //DOCUMENT_TYPE
					
					

					//bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) )" );
					
					if(CommonFunction.checkNull((vo.getTxnType()).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
			        else
			            insertPrepStmtObject.addString((vo.getTxnType().trim()));
					
					if(CommonFunction.checkNull((vo.getTxnId()).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
			        else
			            insertPrepStmtObject.addString((vo.getTxnId().trim()));
					
					if(CommonFunction.checkNull((vo.getStageId()).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
			        else
			            insertPrepStmtObject.addString((vo.getStageId().trim()));
					
					if(CommonFunction.checkNull((vo.getDocType()).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
			        else
			            insertPrepStmtObject.addString((vo.getDocType().trim()));
					
					insertPrepStmtObject.addNull();//ENTITY_ID
					
					insertPrepStmtObject.addNull();//DOC_ID
					
					if(CommonFunction.checkNull((vo.getDocNameAdditional()[i]).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
			        else
			            insertPrepStmtObject.addString((vo.getDocNameAdditional()[i].trim()));
					
					if(CommonFunction.checkNull((vo.getAdditionalDocStatus()[i]).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
			        else
			            insertPrepStmtObject.addString((vo.getAdditionalDocStatus()[i].trim()));
					
					insertPrepStmtObject.addNull();//DOC_STATUS_PREV
					
					try{//try catch added by Virender
					if(CommonFunction.checkNull((vo.getAdditionalReceivedDate()[i]).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
			        else
			            insertPrepStmtObject.addString((vo.getAdditionalReceivedDate()[i].trim()));
					}
					catch(ArrayIndexOutOfBoundsException aie){
						insertPrepStmtObject.addNull();
					}
					try{//try catch added by Virender
					if(CommonFunction.checkNull((vo.getAdditionalDeferredDate()[i]).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
			        else
			            insertPrepStmtObject.addString((vo.getAdditionalDeferredDate()[i].trim()));
					}
					catch(ArrayIndexOutOfBoundsException aie){
						insertPrepStmtObject.addNull();
					}
					try{//try catch added by Virender
					if(CommonFunction.checkNull((vo.getAdditionalExpiryDate()[i]).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
			        else
			            insertPrepStmtObject.addString((vo.getAdditionalExpiryDate()[i].trim()));
					}
					catch(ArrayIndexOutOfBoundsException aie){
						insertPrepStmtObject.addNull();
					}
					if(CommonFunction.checkNull((vo.getAdditionalRemarks()[i]).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
			        else
			            insertPrepStmtObject.addString((vo.getAdditionalRemarks()[i].trim()));
					try{//try catch added by Virender
					if(CommonFunction.checkNull((vo.getAdditionalExpiryDate()[i]).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addString("N");
			        else
			            insertPrepStmtObject.addString("Y");
					}
					catch(ArrayIndexOutOfBoundsException aie){
						insertPrepStmtObject.addNull();
					}
					insertPrepStmtObject.addNull();//DOC_CHILD_IDS
					
					if(CommonFunction.checkNull((vo.getMandatoryOrNonMandatory()[i]).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
			        else
			            insertPrepStmtObject.addString((vo.getMandatoryOrNonMandatory()[i].trim()));
					
					if(CommonFunction.checkNull((vo.getOriginalOrCopy()[i]).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
			        else
			            insertPrepStmtObject.addString((vo.getOriginalOrCopy()[i].trim()));
					
					insertPrepStmtObject.addString("N");//DOC_CHILD_FLAG
					insertPrepStmtObject.addString("P");//REC_STATUS
					
					if(CommonFunction.checkNull((vo.getUserId()).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
			        else
			            insertPrepStmtObject.addString((vo.getUserId().trim()));
					
					if(CommonFunction.checkNull((vo.getBussinessDate()).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
			        else
			            insertPrepStmtObject.addString((vo.getBussinessDate().trim()));
					insertPrepStmtObject.addInt(maxId);
					
					if(CommonFunction.checkNull((vo.getvAdditionalDocType()[i]).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
			        else
			            insertPrepStmtObject.addString((vo.getvAdditionalDocType()[i].trim()));
					
					insertPrepStmtObject.setSql(bufInsSql.toString());
					
					qryList.add(insertPrepStmtObject);
					bufInsSql=null;
					bufInsSql1=null;
					bufInsSql2=null;
					insertPrepStmtObject1=null;
					insertPrepStmtObject=null;
				}
				maxIdInTemp=null;
			}

			// Changes by Amit Ends
				boolean status=false;
				try {
					if(qryList.size()>0)
						status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					ob=null;
					countStr=null;
					qryList.clear();
					qryList = null;
					dId = null;
					dstatus = null;
					dStatusPrev = null;
					drec = null;
					ddef = null;
					expDate = null;
					dremark = null;
					dDocChildIds = null;
					chk = null;
					
					
				}
			return status;
		}

		public String savePODAuthor(PODAuthorVO vo) {
			
			logger.info("in savePODAuthor():::::");
			PODAuthorVO docVo = (PODAuthorVO)vo;
			ArrayList<Object> in =new ArrayList<Object>();
			ArrayList<Object> out =new ArrayList<Object>();
			ArrayList outMessages = new ArrayList();
					
	   		StringBuilder s1 = new StringBuilder(); 
	   		StringBuilder s2 = new StringBuilder();
			String procval=null;
			try
			 {   			
		        in.add(CommonFunction.checkNull((docVo.getId()).trim()));
			    in.add(CommonFunction.checkNull((docVo.getTxnType()).trim()));
		        in.add(CommonFunction.checkNull((docVo.getStage()).trim()));
		        String date=CommonFunction.changeFormat(CommonFunction.checkNull(docVo.getAuthorDate()).trim());
		        in.add(date);
		        in.add((CommonFunction.checkNull(docVo.getAuthorId())));
		        in.add((CommonFunction.checkNull(docVo.getComments())));
		        in.add((CommonFunction.checkNull(docVo.getDecision())));
			    out.add(s1.toString());
			    out.add(s2.toString());
			    logger.info("DOCUMENT_COLLECTION_AUTHOR("+in.toString()+","+out.toString()+")"); 
			    outMessages=(ArrayList) ConnectionDAO.callSP("DOCUMENT_COLLECTION_AUTHOR",in,out);
	     		 s1.append(CommonFunction.checkNull(outMessages.get(0)));
	       		 s2.append(CommonFunction.checkNull(outMessages.get(1)));
		        logger.info("DOCUMENT_COLLECTION_AUTHOR s1: "+s1); 
		        logger.info("DOCUMENT_COLLECTION_AUTHOR s2: "+s2);
				logger.info("After proc call...."); 
				if(CommonFunction.checkNull(s1.toString()).equalsIgnoreCase("S"))
				{
					procval="S";
				}
				else
				{
				    procval=s2.toString();
				  }
			   }
			   catch (Exception e) 
			   {
					e.printStackTrace();
			   }
			   finally
			   {
				   in.clear();
				   in=null;
				   out.clear();
				   out=null;
				   s1=null;
				   s2=null;
				   docVo=null;
				   outMessages.clear();
				   outMessages=null;
				   
			   }
			   return procval;

			}

	

		public ArrayList<DocumentsVo> getApplicationDocuments(String entityType,String commonId,String stage, String txnType,String recStatus,String source) {
			ArrayList<DocumentsVo> list=new ArrayList<DocumentsVo>();
			StringBuilder query = new StringBuilder(); 
			try{
					String table="cr_document_dtl";	
					if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
						table="cr_document_dtl_edit";	
			query.append(" SELECT TXN_DOC_ID,DOC_ID,DOC_DESC,DATE_FORMAT(DOC_RECEIVED_DATE,'"+dateFormat+"'),"+ 
						 "	DATE_FORMAT(DOC_DEFFRED_DATE,'"+dateFormat+"'),DATE_FORMAT(DOC_EXPIRY_DATE,'"+dateFormat+"'), "+
						 "	DOC_MANDATORY,DOC_ORIGINAL,DOC_STATUS,DOC_STATUS_PREV,DOC_REMARKS,Doc_Expiry_Flag,DOC_CHILD_FLAG,DOC_CHILD_IDS,ENTITY_ID,d.DOCUMENT_TYPE "+
						 "	from "+table+" d "+
						 "	where DOC_TYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(entityType)).trim()+"' " +
						 " and TXNID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(commonId)).trim()+" " +
						 " and TXN_TYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(txnType)).trim()+"' AND d.DOC_ID IS NOT NULL " );

			if(!CommonFunction.checkNull(stage).trim().equalsIgnoreCase(""))
			{
			query.append("  and STAGE_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(stage)).trim()+"' ");
			query.append(" and rec_status ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(recStatus)).trim()+"' ");
			}			
			logger.info("getDocumentsDetai Queryl: "+query.toString());
			DocumentsVo docsVo = null;
			ArrayList docsdeatail = ConnectionDAO.sqlSelect(query.toString());
			logger.info("getDocumentsDetail OUTER ARRAYLIST SIZE: "+docsdeatail.size());
			for(int i=0;i<docsdeatail.size();i++){

				//logger.info("getDocumentsDetail"+docsdeatail.get(i).toString());
				ArrayList docsdeatail1=(ArrayList)docsdeatail.get(i);
				if(docsdeatail1.size()>0)
				{
					logger.info("getDocumentsDetail INNNER ARRAYLIST SIZE: "+docsdeatail1.size());
					docsVo = new DocumentsVo();
					docsVo.setDocId((CommonFunction.checkNull(docsdeatail1.get(0))).trim());
					docsVo.setRealDocId((CommonFunction.checkNull(docsdeatail1.get(1))).trim());
					docsVo.setDocDesc((CommonFunction.checkNull(docsdeatail1.get(2))).trim());
					docsVo.setRecieveDate((CommonFunction.checkNull(docsdeatail1.get(3))).trim());
					logger.info("recieve date:"+CommonFunction.checkNull(docsdeatail1.get(3)));
					docsVo.setDeferDate((CommonFunction.checkNull(docsdeatail1.get(4))).trim());
					docsVo.setExpirDate((CommonFunction.checkNull(docsdeatail1.get(5))).trim());

					if((CommonFunction.checkNull(docsdeatail1.get(6)).trim()).equals("Y"))
					{
						docsVo.setMandatory("MANDATORY");
					}
					else if((CommonFunction.checkNull(docsdeatail1.get(6)).trim()).equals("N"))
					{
						docsVo.setMandatory("NON-MANDATORY");
					}

					if((CommonFunction.checkNull(docsdeatail1.get(7)).trim()).equals("Y"))
					{
						docsVo.setOriginal("ORIGINAL");
					}
					else if((CommonFunction.checkNull(docsdeatail1.get(7)).trim()).equals("N"))
					{
						docsVo.setOriginal("PHOTOCOPY");
					}

					docsVo.setDocStatus((CommonFunction.checkNull(docsdeatail1.get(8))).trim());
					docsVo.setDocStatusPrev((CommonFunction.checkNull(docsdeatail1.get(9))).trim());
					docsVo.setRemark((CommonFunction.checkNull(docsdeatail1.get(10))).trim());
					docsVo.setExpirFlag((CommonFunction.checkNull(docsdeatail1.get(11))).trim());
					docsVo.setDocChildFlag((CommonFunction.checkNull(docsdeatail1.get(12))).trim());
					docsVo.setDocChildId((CommonFunction.checkNull(docsdeatail1.get(13))).trim());
					docsVo.setEntityId((CommonFunction.checkNull(docsdeatail1.get(14))).trim());
					docsVo.setvDocumentType((CommonFunction.checkNull(docsdeatail1.get(15))).trim());
					list.add(docsVo);
				}
			}
				table=null;
			}catch(Exception e){
				e.printStackTrace();
			}
			finally
			{
				query = null;
				entityType = null;
				commonId = null;
				stage = null;
				txnType = null;
				recStatus = null;
				source = null;
			}
			return list;
		}

		public ArrayList<DocumentsVo> getAllApplicantDocs(String entityType,String commonId,String stage, String txnType,String recStatus) {
			ArrayList<DocumentsVo> list=new ArrayList<DocumentsVo>();
			StringBuilder query = new StringBuilder();
			try{
				//String query=""; 
				if(!stage.equalsIgnoreCase("PRS"))
				{
					query.append(" SELECT DISTINCT TXN_DOC_ID,DOC_ID,DOC_DESC,g.CUSTOMER_NAME, DATE_FORMAT(DOC_RECEIVED_DATE,'"+dateFormat+"'),"+ 
					
					 " DATE_FORMAT(DOC_DEFFRED_DATE,'"+dateFormat+"'),DATE_FORMAT(DOC_EXPIRY_DATE,'"+dateFormat+"'), "+ 
					 " DOC_MANDATORY,DOC_ORIGINAL,DOC_STATUS,DOC_STATUS_PREV,DOC_REMARKS,Doc_Expiry_Flag,DOC_CHILD_FLAG,DOC_CHILD_IDS,ENTITY_ID,d.DOCUMENT_TYPE "+ 
					 " from cr_document_dtl d, gcd_customer_m g, cr_loan_dtl L "+ 
					 " where DOC_TYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(entityType)).trim()+"' " +
					 " and TXNID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(commonId)).trim()+" " +
					 " and TXN_TYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(txnType)).trim()+"' " +
					 " AND D.TXNID = L.LOAN_ID  AND D.ENTITY_ID=g.CUSTOMER_ID AND d.DOC_ID IS NOT NULL" );
					// " R.GCD_ID=g.CUSTOMER_ID  AND" +
					// " R.DEAL_CUSTOMER_ROLE_TYPE = '"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(entityType)).trim()+"' AND d.DOC_ID IS NOT NULL ");
				     if(!CommonFunction.checkNull(stage).trim().equalsIgnoreCase(""))
				     {
				    	 query.append("  and STAGE_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(stage)).trim()+"' ");
						query.append(" and d.rec_status ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(recStatus)).trim()+"' ");
				     }
				}
				
				if(stage.equalsIgnoreCase("PRS"))
				{
					query.append(" SELECT DISTINCT TXN_DOC_ID,DOC_ID,DOC_DESC,g.CUSTOMER_NAME, DATE_FORMAT(DOC_RECEIVED_DATE,'"+dateFormat+"'),"+ 
					
					 " DATE_FORMAT(DOC_DEFFRED_DATE,'"+dateFormat+"'),DATE_FORMAT(DOC_EXPIRY_DATE,'"+dateFormat+"'), "+ 
					 " DOC_MANDATORY,DOC_ORIGINAL,DOC_STATUS,DOC_STATUS_PREV,DOC_REMARKS,Doc_Expiry_Flag,DOC_CHILD_FLAG,DOC_CHILD_IDS,ENTITY_ID,d.DOCUMENT_TYPE "+ 
					 " from cr_document_dtl d, cr_deal_customer_m g, cr_deal_dtl L,CR_DEAL_CUSTOMER_ROLE R "+ 
					 " where DOC_TYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(entityType)).trim()+"' " +
					 " and TXNID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(commonId)).trim()+" " +
					 " and TXN_TYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(txnType)).trim()+"' " +
					 " and STAGE_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(stage)).trim()+"'"+ 
					 " and d.rec_status = '"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(recStatus)).trim()+"'" +
					 " AND D.TXNID = L.DEAL_ID "+ 
					 " AND D.ENTITY_ID=R.DEAL_CUSTOMER_ID and R.DEAL_CUSTOMER_ID=g.CUSTOMER_ID " +
					 " AND R.DEAL_CUSTOMER_ROLE_TYPE = '"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(entityType)).trim()+"' AND d.DOC_ID IS NOT NULL ");
				}
			
			logger.info("getDocumentsDetai Query2: "+query.toString());
			
			DocumentsVo docsVo = null;
			ArrayList docsdeatail = ConnectionDAO.sqlSelect(query.toString());
			logger.info("getDocumentsDetail OUTER ARRAYLIST SIZE: "+docsdeatail.size());
			for(int i=0;i<docsdeatail.size();i++){

				//logger.info("getDocumentsDetail"+docsdeatail.get(i).toString());
				ArrayList docsdeatail1=(ArrayList)docsdeatail.get(i);
				//logger.info("getDocumentsDetail INNNER ARRAYLIST SIZE: "+docsdeatail1.size());
				if(docsdeatail1.size()>0)
				{
					docsVo = new DocumentsVo();
					docsVo.setDocId((CommonFunction.checkNull(docsdeatail1.get(0))).trim());
					docsVo.setRealDocId((CommonFunction.checkNull(docsdeatail1.get(1))).trim());
					docsVo.setDocDesc((CommonFunction.checkNull(docsdeatail1.get(2))).trim());
					docsVo.setApplName((CommonFunction.checkNull(docsdeatail1.get(3))).trim());
					docsVo.setRecieveDate((CommonFunction.checkNull(docsdeatail1.get(4))).trim());
					docsVo.setDeferDate((CommonFunction.checkNull(docsdeatail1.get(5))).trim());
					docsVo.setExpirDate((CommonFunction.checkNull(docsdeatail1.get(6))).trim());

					if((CommonFunction.checkNull(docsdeatail1.get(7)).trim()).equals("Y"))
					{
						docsVo.setMandatory("MANDATORY");
					}
					else 	if((CommonFunction.checkNull(docsdeatail1.get(7)).trim()).equals("N"))
					{
						docsVo.setMandatory("NON-MANDATORY");
					}

					if((CommonFunction.checkNull(docsdeatail1.get(8)).trim()).equals("Y"))
					{
						docsVo.setOriginal("ORIGINAL");
					}
					else if((CommonFunction.checkNull(docsdeatail1.get(8)).trim()).equals("N"))
					{
						docsVo.setOriginal("PHOTOCOPY");
					}

					
					docsVo.setDocStatus((CommonFunction.checkNull(docsdeatail1.get(9))).trim());
					docsVo.setDocStatusPrev((CommonFunction.checkNull(docsdeatail1.get(10))).trim());
					docsVo.setRemark((CommonFunction.checkNull(docsdeatail1.get(11))).trim());
					docsVo.setExpirFlag((CommonFunction.checkNull(docsdeatail1.get(12))).trim());
					docsVo.setDocChildFlag((CommonFunction.checkNull(docsdeatail1.get(13))).trim());
					docsVo.setDocChildId((CommonFunction.checkNull(docsdeatail1.get(14))).trim());
					docsVo.setEntityId((CommonFunction.checkNull(docsdeatail1.get(15))).trim());
					docsVo.setvDocumentType((CommonFunction.checkNull(docsdeatail1.get(16))).trim());
					list.add(docsVo);
				}
			}
			}catch(Exception e){
				e.printStackTrace();
			}
			finally
			{
				query = null;
				entityType = null;
				commonId = null;
				stage = null;
				txnType = null;
				recStatus = null;
			}
			return list;
		}

	public ArrayList<DocumentsVo> getAssetCollateralDocuments(String entityType,String commonId, String stage, String txnType, 
			String recStatus) 
	{
		ArrayList<DocumentsVo> list = new ArrayList<DocumentsVo>();
		StringBuilder query = new StringBuilder(); 
		try 
		{
			//String query="";
			if(!stage.equalsIgnoreCase("PRS"))
			{
				query.append(" SELECT DISTINCT TXN_DOC_ID,DOC_ID,DOC_DESC,lc.ASSETID,ASSET_COLLATERAL_CLASS," +
					" DATE_FORMAT(DOC_RECEIVED_DATE,'"+ dateFormat+ "'),DATE_FORMAT(DOC_DEFFRED_DATE,'"+ dateFormat+ "')," +
					" DATE_FORMAT(DOC_EXPIRY_DATE,'"+ dateFormat+ "'),DOC_MANDATORY,DOC_ORIGINAL,DOC_STATUS,DOC_STATUS_PREV,DOC_REMARKS," +
					" Doc_Expiry_Flag,DOC_CHILD_FLAG,DOC_CHILD_IDS,ENTITY_ID,d.DOCUMENT_TYPE from cr_document_dtl d, cr_loan_collateral_m lc," +
					" cr_asset_collateral_m ac" +
					" where DOC_TYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(entityType)).trim()+"' " +
					" and TXNID="+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(commonId)).trim()+ " " +
					" and TXN_TYPE='"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(txnType)).trim()+ "'" +
					" AND D.TXNID = lc.LOAN_ID" +
					" AND D.ENTITY_ID = lc.ASSETID AND lc.ASSETID=ac.ASSET_ID AND d.DOC_ID IS NOT NULL  ");
			     if(!CommonFunction.checkNull(stage).trim().equalsIgnoreCase("")){
			    	 query.append("  and STAGE_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(stage)).trim()+"' ");
					query.append(" and d.rec_status ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(recStatus)).trim()+"'  ");
			     }
			}
			if(stage.equalsIgnoreCase("PRS"))
			{
				query.append(" SELECT DISTINCT TXN_DOC_ID,DOC_ID,DOC_DESC,dc.ASSETID,ASSET_COLLATERAL_CLASS," +
					" DATE_FORMAT(DOC_RECEIVED_DATE,'"+ dateFormat+ "'),DATE_FORMAT(DOC_DEFFRED_DATE,'"+ dateFormat+ "')," +
					" DATE_FORMAT(DOC_EXPIRY_DATE,'"+ dateFormat+ "'),DOC_MANDATORY,DOC_ORIGINAL,DOC_STATUS,DOC_STATUS_PREV,DOC_REMARKS," +
					" Doc_Expiry_Flag,DOC_CHILD_FLAG,DOC_CHILD_IDS,ENTITY_ID,d.DOCUMENT_TYPE from cr_document_dtl d, cr_deal_collateral_m dc," +
					" cr_asset_collateral_m ac" +
					" where DOC_TYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(entityType)).trim()+"' " +
					" and TXNID="+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(commonId)).trim()+ " " +
					" and TXN_TYPE='"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(txnType)).trim()+ "'" +
					" and STAGE_ID='"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(stage)).trim()+ "'" +
					" AND D.TXNID = dc.DEAL_ID" +
					" and d.rec_status = '"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(recStatus)).trim()+"'" +
					" AND D.ENTITY_ID = dc.ASSETID AND dc.ASSETID=ac.ASSET_ID AND d.DOC_ID IS NOT NULL ");
			}
			logger.info("getDocumentsDetai Query3: " + query.toString());
			DocumentsVo docsVo = null;
			ArrayList docsdeatail = ConnectionDAO.sqlSelect(query.toString());
			logger.info("getDocumentsDetail OUTER ARRAYLIST SIZE: "+ docsdeatail.size());
			for (int i = 0; i < docsdeatail.size(); i++) 
			{
				//logger.info("getDocumentsDetail"+ docsdeatail.get(i).toString());
				ArrayList docsdeatail1 = (ArrayList) docsdeatail.get(i);
				//logger.info("getDocumentsDetail INNNER ARRAYLIST SIZE: "+ docsdeatail1.size());
				if (docsdeatail1.size() > 0) 
				{
					docsVo = new DocumentsVo();
					docsVo.setDocId((CommonFunction.checkNull(docsdeatail1.get(0))).trim());
					docsVo.setRealDocId((CommonFunction.checkNull(docsdeatail1.get(1))).trim());
					docsVo.setDocDesc((CommonFunction.checkNull(docsdeatail1.get(2))).trim());
					docsVo.setType((CommonFunction.checkNull(docsdeatail1.get(4))).trim());
					docsVo.setRecieveDate((CommonFunction.checkNull(docsdeatail1.get(5))).trim());
					docsVo.setDeferDate((CommonFunction.checkNull(docsdeatail1.get(6))).trim());
					docsVo.setExpirDate((CommonFunction.checkNull(docsdeatail1.get(7))).trim());

					if ((CommonFunction.checkNull(docsdeatail1.get(8)).trim()).equals("Y")) {
						docsVo.setMandatory("MANDATORY");
					} 
					else if ((CommonFunction.checkNull(docsdeatail1.get(8)).trim()).equals("N")) {
						docsVo.setMandatory("NON-MANDATORY");
					}

					if ((CommonFunction.checkNull(docsdeatail1.get(9)).trim()).equals("Y")) {
						docsVo.setOriginal("ORIGINAL");
					} 
					else if ((CommonFunction.checkNull(docsdeatail1.get(9)).trim()).equals("N")) {
						docsVo.setOriginal("PHOTOCOPY");
					}

					docsVo.setDocStatus((CommonFunction.checkNull(docsdeatail1.get(10))).trim());
					docsVo.setDocStatusPrev((CommonFunction.checkNull(docsdeatail1.get(11))).trim());
					docsVo.setRemark((CommonFunction.checkNull(docsdeatail1.get(12))).trim());
					docsVo.setExpirFlag((CommonFunction.checkNull(docsdeatail1.get(13))).trim());
					docsVo.setDocChildFlag((CommonFunction.checkNull(docsdeatail1.get(14))).trim());
					docsVo.setDocChildId((CommonFunction.checkNull(docsdeatail1.get(15))).trim());
					docsVo.setEntityId((CommonFunction.checkNull(docsdeatail1.get(16))).trim());
					docsVo.setvDocumentType((CommonFunction.checkNull(docsdeatail1.get(17))).trim());
					list.add(docsVo);
				}
			}
			docsVo = null;
			docsdeatail.clear();
			docsdeatail=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			query = null;
			entityType = null;
			commonId = null;
			stage = null;
			txnType = null; 
			recStatus = null;
		}
		return list;
	}
	
// Function Added by Amit Starts
	
	public ArrayList getAdditionalDocs(String entityType, String commonId, String stage, String txnType, String recStatus)
	{
		ArrayList<Object> list=new ArrayList<Object>();
		try{
			StringBuilder query=new StringBuilder();
			
			query.append(" SELECT DISTINCT DOC_DESC,DATE_FORMAT(DOC_RECEIVED_DATE,'"+dateFormat+"'),"+ 
				 "	DATE_FORMAT(DOC_DEFFRED_DATE,'"+dateFormat+"'),DATE_FORMAT(DOC_EXPIRY_DATE,'"+dateFormat+"'), "+ 
				 "	DOC_MANDATORY,DOC_ORIGINAL,DOC_STATUS,DOC_REMARKS,d.DOCUMENT_TYPE"+ 
				 "	from cr_document_dtl d"+ 
				 "	where DOC_TYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(entityType)).trim()+"' " +
				 "  and TXNID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(commonId)).trim()+" " +
				 "  and TXN_TYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(txnType)).trim()+"' and DOC_ID IS NULL " );

			     if(!CommonFunction.checkNull(stage).trim().equalsIgnoreCase("")){
			    	 query.append("  and STAGE_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(stage)).trim()+"' ");
					query.append(" and rec_status ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(recStatus)).trim()+"' ");
			     }
			
			
		
		logger.info("getAdditionalDocs Query: "+query.toString());
		
		DocumentsVo docsVo = null;
		ArrayList docsdeatail = ConnectionDAO.sqlSelect(query.toString());
		logger.info("getAdditionalDocs OUTER ARRAYLIST SIZE: "+docsdeatail.size());
		
		query=null;
		
		for(int i=0;i<docsdeatail.size();i++){

			//logger.info("getAdditionalDocs"+docsdeatail.get(i).toString());
			ArrayList docsdeatail1=(ArrayList)docsdeatail.get(i);
			//logger.info("getAdditionalDocs INNNER ARRAYLIST SIZE: "+docsdeatail1.size());
			if(docsdeatail1.size()>0)
			{
				docsVo = new DocumentsVo();
				docsVo.setDocNameAddn(CommonFunction.checkNull(docsdeatail1.get(0)).trim());
				docsVo.setAddnReceivedDate(CommonFunction.checkNull(docsdeatail1.get(1)).trim());
				docsVo.setAddnDeferredDate(CommonFunction.checkNull(docsdeatail1.get(2)).trim());
				docsVo.setAddnExpiryDate(CommonFunction.checkNull(docsdeatail1.get(3)).trim());
				docsVo.setMandateOrNonMandate(CommonFunction.checkNull(docsdeatail1.get(4)).trim());
				docsVo.setOrgOrCopy(CommonFunction.checkNull(docsdeatail1.get(5)).trim());
				docsVo.setAddnDocStatus(CommonFunction.checkNull(docsdeatail1.get(6)).trim());
				docsVo.setAddnRemarks(CommonFunction.checkNull(docsdeatail1.get(7)).trim());
				docsVo.setvDocumentType((CommonFunction.checkNull(docsdeatail1.get(8))).trim());
				list.add(docsVo);
			}
		}
		
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			entityType = null;
			commonId = null;
			stage = null;
			txnType = null;
			recStatus = null;
		}
		return list;
	}
	
	//Function Added by Amit Ends

		public ArrayList getNoOfDisbInloan(String loanId) {
			ArrayList<Object> list=new ArrayList<Object>();
			StringBuilder query = new StringBuilder(); 
			try{

				//String query="";
				query.append(" select DATE_FORMAT(LOAN_AGREEMENT_DATE,'"+dateFormat+"'),DATE_FORMAT(LOAN_REPAY_EFF_DATE,'"+dateFormat+"') ,LOAN_NUMBER_DISBURSAL from  cr_loan_dtl WHERE LOAN_ID="+loanId);
			
			logger.info("getNoOfDisbInloan Queryl: "+query);
			
			NoOfDisbursalVo nVo = null;
			ArrayList disbdeatail = ConnectionDAO.sqlSelect(query.toString());
			logger.info("getNoOfDisbInloan OUTER ARRAYLIST SIZE: "+disbdeatail.size());
			for(int i=0;i<disbdeatail.size();i++){

			//	logger.info("getNoOfDisbInloan"+disbdeatail.get(i).toString());
				ArrayList disbdeatail1=(ArrayList)disbdeatail.get(i);
			//	logger.info("getNoOfDisbInloan INNNER ARRAYLIST SIZE: "+disbdeatail1.size());
				if(disbdeatail1.size()>0)
				{
					nVo = new NoOfDisbursalVo();
					nVo.setLoanAgrementDate((CommonFunction.checkNull(disbdeatail1.get(0))).trim());
					nVo.setRepayEffectiveDate((CommonFunction.checkNull(disbdeatail1.get(1))).trim());
					nVo.setDisbNoInLoan((CommonFunction.checkNull(disbdeatail1.get(2))).trim());
					list.add(nVo);
				}
			}
			disbdeatail.clear();
			disbdeatail = null;
			}catch(Exception e){
				e.printStackTrace();
			}
			finally
			{
				query = null;
				loanId = null;
			}
			return list;
		}

			
		public ArrayList<InstructionCapMakerVO> getListOfValuesforAuthor(int loanID) {
			ArrayList<InstructionCapMakerVO> pdcDetailList=new ArrayList<InstructionCapMakerVO>();
			StringBuilder query = new StringBuilder(); 
			 logger.info("In getListOfValuesforAuthor: ");
				try{

					  ArrayList mainlist=new ArrayList();
					  ArrayList subList=new ArrayList();
					  query.append("select a.INSTL_DATE,b.PDC_INSTRUMENT_NO,a.INSTL_AMOUNT,b.PDC_INSTRUMENT_AMOUNT from" +
					  " cr_pdc_instrument_dtl b,cr_repaysch_dtl a where a.loan_id=b.pdc_loan_id and loan_id = '"+(""+loanID)+"' and pdc_status='F'");

						logger.info("In getListOfValuesforAuthor "+query.toString());
						mainlist=ConnectionDAO.sqlSelect(query.toString());
						logger.info("In getListOfValues,,,,,"+mainlist.size());
				
						for(int i=0;i<mainlist.size();i++){


							subList=(ArrayList)mainlist.get(i);
							//logger.info("In getListOfValuesforAuthor..."+subList.size());
							
						if(subList.size()>0){
							InstructionCapMakerVO instructionCapMakerVO=new InstructionCapMakerVO();
							instructionCapMakerVO.setDate((CommonFunction.checkNull(subList.get(0)).trim()));
							instructionCapMakerVO.setStartingChequeNo((CommonFunction.checkNull(subList.get(1)).trim()));
							
							Number installmentAmount =myFormatter.parse((CommonFunction.checkNull(subList.get(2))).trim());
							instructionCapMakerVO.setInstallmentAmount(myFormatter.format(installmentAmount));
							
			//				instructionCapMakerVO.setInstallmentAmount((CommonFunction.checkNull(subList.get(2)).trim()));
							
							Number instrumentAmount =myFormatter.parse((CommonFunction.checkNull(subList.get(3))).trim());
							instructionCapMakerVO.setInstrumentAmount(myFormatter.format(instrumentAmount));
							
						//	instructionCapMakerVO.setInstrumentAmount((CommonFunction.checkNull(subList.get(3)).trim()));

							pdcDetailList.add(instructionCapMakerVO);
				       }
			    }
						mainlist.clear();
						mainlist=null;
						subList.clear();
						subList=null;
				}catch(Exception e){
					e.printStackTrace();
				}
				finally
				{
					query  = null;
				}

				return pdcDetailList;


		}


		
		

		
		
		
		public ArrayList<InstructionCapMakerVO> insertListECS(InstructionCapMakerVO instructionCapMakerVO){
			ArrayList qryList =  new ArrayList();
//			boolean status=false;
//			StringBuffer bufInsSql =null;
//			PrepStmtObject insertPrepStmtObject = null;	
//			
//			try{
//				
//				String query = "select LOAN_CUSTOMER_ID from cr_loan_dtl where loan_id='"+instructionCapMakerVO.getLbxLoanNoHID()+"'";
//				String bpid = ConnectionDAO.singleReturn(query);
//				
//					bufInsSql =	new StringBuffer();
//					 insertPrepStmtObject = new PrepStmtObject();
//					 
//					 
//					 bufInsSql.append(" Insert into cr_pdc_instrument_dtl (PDC_LOAN_ID,PDC_BPTYPE,PDC_BPID,PDC_INSTRUMENT_MODE," +
//					 		" PDC_INSTRUMENT_TYPE,PDC_FLAG,PDC_INSTRUMENT_NO,PDC_ISSUEING_BANK_ID," +
//					 		" PDC_ISSUEING_BRANCH_ID,PDC_ISSUING_MICR_CODE,PDC_ISSUING_IFSC_CODE," +
//					 		" PDC_ISSUEING_BANK_ACCOUNT,PDC_INSTRUMENT_AMOUNT," +
//					 		" PDC_CLEARING_TYPE,PDC_BIN_NO,PDC_LOCATION,PDC_STATUS,MAKER_ID,MAKER_DATE,PDC_INSTL_NO,PDC_REMARKS)");
//					 
//							 bufInsSql.append(" values ( ");
//							 bufInsSql.append(" ?," );
//							 bufInsSql.append(" ?," );
//							 bufInsSql.append(" ?," );
//							 bufInsSql.append(" ?," );
//							 bufInsSql.append(" 'H'," );
//							 bufInsSql.append(" 'F'," );
//							 bufInsSql.append(" ?," );
//							 bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"'))" );
//							
//		
//							 if(CommonFunction.checkNull(instrumentidList[i]).trim().equalsIgnoreCase(""))
//									insertPrepStmtObject.addNull();
//								else
//									insertPrepStmtObject.addString(instrumentidList[i].trim());
//							 
//								if(CommonFunction.checkNull(newStatusList[i]).trim().equalsIgnoreCase(""))
//									insertPrepStmtObject.addNull();
//								else
//									insertPrepStmtObject.addString(newStatusList[i].trim());
//								
//								if(CommonFunction.checkNull(checkedStatusList[i]).trim().equalsIgnoreCase(""))
//									insertPrepStmtObject.addNull();
//								else
//									insertPrepStmtObject.addString(checkedStatusList[i].trim());
//								
//								if(CommonFunction.checkNull(holdRE).trim().equalsIgnoreCase(""))
//									insertPrepStmtObject.addNull();
//								else
//									insertPrepStmtObject.addString(holdRE.trim());
//								
//								if(CommonFunction.checkNull(instructionCapMakerVO.getMakerID()).trim().equalsIgnoreCase(""))
//									insertPrepStmtObject.addNull();
//								else
//									insertPrepStmtObject.addString(instructionCapMakerVO.getMakerID().trim());
//								
//								if(CommonFunction.checkNull(instructionCapMakerVO.getMakerDate()).trim().equalsIgnoreCase(""))
//									insertPrepStmtObject.addNull();
//								else
//									insertPrepStmtObject.addString(instructionCapMakerVO.getMakerDate().trim());
//								
//								insertPrepStmtObject.setSql(bufInsSql.toString());
//								qryList.add(insertPrepStmtObject);
//
//				
//			              status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
//					
//					
//					
//					
//					
////			     "+instrumentidList[i]+"','"+newStatusList[i]+"','"+checkedStatusList[i]+"','"+holdRE+"','H','F') " ;
//			
//		              logger.info("In updateIndiHoldInstrument."+status);
//
//			}catch(Exception e){
//				e.printStackTrace();
//			}
		return qryList;
		}
		
		
		
		

		
		
		public ArrayList<InstructionCapMakerVO> getListOfValuesforAuthor() {
		ArrayList<InstructionCapMakerVO> instrumentList=new ArrayList<InstructionCapMakerVO>();
		StringBuilder query = new StringBuilder(); 
		logger.info("In getListOfValuesforAuthor: ");
			try{

					  ArrayList mainlist=new ArrayList();
					  ArrayList subList=new ArrayList();
		
					  query.append("select loan_id,loan_no,loan_scheme,loan_loan_amount,loan_approval_date," +
					  		" loan_product,b.branch_desc,loan_status from cr_loan_dtl,com_branch_m b " +
					  		" where loan_branch = b.branch_id");
		
		
					logger.info(" getListOfValuesforAuthor query "+query.toString());
					mainlist=ConnectionDAO.sqlSelect(query.toString());
					logger.info(" getListOfValuesforAuthor list ,,,,,"+mainlist.size());
					
					for(int i=0;i<mainlist.size();i++){

							subList=(ArrayList)mainlist.get(i);
							//logger.info("getListOfValuesforAuthor sublist..."+subList.size());
							
							if(subList.size()>0){
					
									InstructionCapMakerVO instructionCapMakerVO=new InstructionCapMakerVO();
									instructionCapMakerVO.setLbxLoanNoHID((CommonFunction.checkNull(subList.get(0)).trim()));
									instructionCapMakerVO.setLoanAccNo((CommonFunction.checkNull(subList.get(1)).trim()));
									instructionCapMakerVO.setScheme((CommonFunction.checkNull(subList.get(2)).trim()));
									
									Number loanAmount =myFormatter.parse((CommonFunction.checkNull(subList.get(3))).trim());
									
									instructionCapMakerVO.setLoanAmount(myFormatter.format(loanAmount));

									
							//		instructionCapMakerVO.setLoanAmount((CommonFunction.checkNull(subList.get(3)).trim()));
									instructionCapMakerVO.setLoanApprovalDate((CommonFunction.checkNull(subList.get(4)).trim()));
									instructionCapMakerVO.setProduct((CommonFunction.checkNull(subList.get(5)).trim()));
									instructionCapMakerVO.setBranch((CommonFunction.checkNull(subList.get(6)).trim()));
									instructionCapMakerVO.setStatus((CommonFunction.checkNull(subList.get(7)).trim()));

									instrumentList.add(instructionCapMakerVO);
							}
					}
					mainlist.clear();
					mainlist=null;
					subList.clear();
					subList=null;
					}catch(Exception e){
						e.printStackTrace();
					}
					finally
					{
						query = null;
					}

					return instrumentList;

		}


	


		public ArrayList<InstructionCapMakerVO> getListOfValuesforInstrumentMaker() {
			
		ArrayList<InstructionCapMakerVO> instrumentList=new ArrayList<InstructionCapMakerVO>();
		StringBuilder query = new StringBuilder(); 
		logger.info("In getListOfValuesforInstrumentMaker: ");
		
			try{

					  ArrayList mainlist=new ArrayList();
					  ArrayList subList=new ArrayList();
		
					 query.append("select loan_id,loan_no,loan_scheme,loan_loan_amount,loan_approval_date," +
					  		" loan_product,b.branch_desc,loan_status from cr_loan_dtl,com_branch_m b " +
					  		" where loan_branch = b.branch_id");
		
		
					 logger.info(" getListOfValuesforInstrumentMaker query "+query.toString());
					 mainlist=ConnectionDAO.sqlSelect(query.toString());
					 logger.info(" getListOfValuesforInstrumentMaker list,,,,,"+mainlist.size());
					 
					 for(int i=0;i<mainlist.size();i++){

							subList=(ArrayList)mainlist.get(i);
							//logger.info(" getListOfValuesforInstrumentMaker sublist..."+subList.size());
				
								if(subList.size()>0){
					
										InstructionCapMakerVO instructionCapMakerVO=new InstructionCapMakerVO();
										instructionCapMakerVO.setLbxLoanNoHID((CommonFunction.checkNull(subList.get(0)).trim()));
										instructionCapMakerVO.setLoanAccNo((CommonFunction.checkNull(subList.get(1)).trim()));
										instructionCapMakerVO.setScheme((CommonFunction.checkNull(subList.get(2)).trim()));
										
										Number loanAmount =myFormatter.parse((CommonFunction.checkNull(subList.get(3))).trim());
										
										instructionCapMakerVO.setLoanAmount(myFormatter.format(loanAmount));

									//	instructionCapMakerVO.setLoanAmount((CommonFunction.checkNull(subList.get(3)).trim()));
										instructionCapMakerVO.setLoanApprovalDate((CommonFunction.checkNull(subList.get(4)).trim()));
										instructionCapMakerVO.setProduct((CommonFunction.checkNull(subList.get(5)).trim()));
										instructionCapMakerVO.setBranch((CommonFunction.checkNull(subList.get(6)).trim()));
										instructionCapMakerVO.setStatus((CommonFunction.checkNull(subList.get(7)).trim()));
					
										instrumentList.add(instructionCapMakerVO);
								}
					 		}
					 	mainlist.clear();
						mainlist=null;
						subList.clear();
						subList=null;
						}catch(Exception e){
							e.printStackTrace();
						}
						finally
						{
							query = null;
						}

			return instrumentList;

		}


			public boolean updateIndiHoldInstrumentUnChecked(String[] uncheckedValueList,
				String[] uncheckedStatusList,
				InstructionCapMakerVO instructionCapMakerVO,
				String[] uncheckedholdList) {
				
			ArrayList qryList =  new ArrayList(); 
			boolean status=false;
			logger.info("In updateIndiHoldInstrumentUnChecked");
			try{
				for(int i=0;i<uncheckedValueList.length;i++)
				{
					String holdRE=uncheckedholdList[i];
					
					if(uncheckedholdList[i].equalsIgnoreCase("test")){
						holdRE="";
					}
						StringBuilder q = new StringBuilder();
					     q.append("insert into cr_pdc_hold_reason_dtl(pdc_instrument_id,to_status,from_status,hold_reason,main_status,maker_id,maker_date) " +
					     		" values( '"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(uncheckedValueList[i]).trim())+"','A','"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(uncheckedStatusList[i]).trim())+"','"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(holdRE).trim())+"','F'," +
					     	    " '"+instructionCapMakerVO.getMakerID()+"',DATE_ADD(STR_TO_DATE('"+instructionCapMakerVO.getMakerDate()+"','"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND)  ) " );
					    logger.info(q.toString());
					    qryList.add(q.toString());
					    q = null;
					}

		              status=ConnectionDAO.sqlInsUpdDelete(qryList);
		              logger.info("In Applicant......................"+status);

			}catch(Exception e){
				e.printStackTrace();
			}finally{
				uncheckedValueList = null;
				uncheckedStatusList = null;
				instructionCapMakerVO = null;
				uncheckedholdList = null;
			}

			return status;

		}


		public boolean updateIndiReleaseInstrumentUnChecked(String[] uncheckedValueList,
				String[] uncheckedStatusList,
				InstructionCapMakerVO instructionCapMakerVO,
				String[] uncheckedholdList) {
			
			ArrayList qryList =  new ArrayList(); 
			boolean status=false;
			logger.info("In updateIndiReleaseInstrumentUnChecked.");
			StringBuffer bufInsSql =null;
			PrepStmtObject insertPrepStmtObject = null;
			try{
				for(int i=0;i<uncheckedValueList.length;i++)
				{
					StringBuilder q = new StringBuilder();
				     q.append("insert into cr_common_instrument_dtl(pdc_instrument_id,to_status,from_status,hold_reason,main_status) values('"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(uncheckedValueList[i]).trim())+"','"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(uncheckedStatusList[i]).trim())+"','"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(uncheckedStatusList[i]).trim())+"','"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(uncheckedholdList[i]).trim())+"','P') " );
				     logger.info("Query"+q.toString());
				     qryList.add(q.toString());
				     q = null;
				}

		              status=ConnectionDAO.sqlInsUpdDelete(qryList);
		              logger.info("In Applicant......................"+status);

			}catch(Exception e){
				e.printStackTrace();
			}finally{
				uncheckedValueList = null;
				uncheckedStatusList = null;
				instructionCapMakerVO = null;
				uncheckedholdList = null;
			}

			return status;

		}


			
		

			
		 
			public ArrayList<InstructionCapMakerVO> getbussinessPartnerList() {
				ArrayList<InstructionCapMakerVO> getBPList=new ArrayList<InstructionCapMakerVO>();
				StringBuilder query = new StringBuilder(); 
				 
				  try{
					  ArrayList mainList=new ArrayList ();
					  ArrayList subList =new ArrayList();
				 
					  query.append("select VALUE,DESCRIPTION from generic_master where" +
					  		" GENERIC_KEY='BPTYPE' and PARENT_VALUE='NULL'");  
					  logger.info("In getBPList:------"+query);	
						
						mainList=ConnectionDAO.sqlSelect(query.toString());
						for(int i=0;i<mainList.size();i++)
						{
							subList= (ArrayList)mainList.get(i);
							if(subList.size()>0){
								InstructionCapMakerVO instructionCapMakerVO = new InstructionCapMakerVO();
								instructionCapMakerVO.setBusinessPartnerType((String) subList.get(0));
								instructionCapMakerVO.setBusinessPartnerDesc((String) subList.get(1));
								
								getBPList.add(instructionCapMakerVO);
							}	
						}
						mainList.clear();
						mainList=null;
						subList.clear();
						subList=null;
							}catch(Exception e){
								e.printStackTrace();
				              } 
							finally
							{
								query = null;
							}
				return getBPList;			
			  

			}
			
			
			
		
			public String updateInstrumentDetailForPayment(String[] tdsAmountListList,
					String[] instrumentIDList,
					String[] checkedinstrumentNoList, String[] checkeddateList,
					String[] checkedinstrumentAmountList,
					String[] checkedstatusList,
					String[] checkedlbxBPTypeHIDList,
					String[] checkedlbxBPNIDList,
					String[] checkedlbxBankIDList,
					String[] checkedlbxBranchIDList,
					ChequeStatusVO chequeStatusVO,String status,String[] checkedlbxReasonHIDList,String[] pdcInstrumentIdList,String pdcFlag,String instrumentType,String[] depositBankIdList,
					String[] depositBranchIdList, String[] depositMicrCodeList,
					String[] depositIfscCodeList, String[] depositBankAccountList,String[] checkedvalueDateList,String[] checkedReasonRemarksList)
			{
				logger.info("updateInstrumentDetailForPayment-------->");
			
		   	    ArrayList<Object> in =new ArrayList<Object>();
           		ArrayList<Object> out =new ArrayList<Object>();
           		ArrayList outMessages = new ArrayList();
           		StringBuilder s1 = new StringBuilder(); 
           		StringBuilder s2 = new StringBuilder(); 
           		//String s1="";
           		//String s2="";
				ArrayList qryList =  new ArrayList();
				boolean status1=false;
				String procresult = "";
				StringBuffer bufInsSql =null;
				PrepStmtObject insertPrepStmtObject = null;	
				StringBuilder instrumentBatchId = new StringBuilder(); 
				StringBuilder instrumentBatchIdValue = new StringBuilder(); 
				//String instrumentBatchId="";
				//String instrumentBatchIdValue="";
				int updateBatchID;
				ArrayList updateBatchIDforQry= null;
				ArrayList updateBatchIDformainQry=null;
				StringBuilder updateBatchIDforQryString = new StringBuilder(); 
				StringBuilder updateMainQuery = new StringBuilder();  
				//String updateBatchIDforQryString="";
				//String updateMainQuery="";
				String reasonID="";
				String pdcInstrum="";
//				 Connection con=ConnectionDAO.getConnection();
//				CallableStatement cst=null;
				 instrumentBatchId.append("select function_sequence('CHEQUE_STATUS')");
				  instrumentBatchIdValue.append(ConnectionDAO.singleReturn(instrumentBatchId.toString()));
				  updateBatchID = Integer.parseInt(instrumentBatchIdValue.toString());
				  
				  
				  
//				  
//				  logger.info("updateBatchID"+updateBatchID);
//				  logger.info("checkedinstrumentNoList"+checkedinstrumentNoList.length);
				  
				  int depositBankIdLength=depositBankIdList.length;
				  int depositBranchIdLength=depositBranchIdList.length;
				  int depositMicrCodeLength=depositMicrCodeList.length;
				  int depositIfscCodeLength=depositIfscCodeList.length;
				  int depositBankAccountLength=depositBankAccountList.length;
				  logger.info("In updateInstrumentDetailForPayment..checkedstatusList.Dao Impl. depositBankIdList.length"+depositBankIdLength);
				try{
					for(int i=0;i<instrumentIDList.length;i++)
					{
						//logger.info("In updateInstrumentDetail...Dao Impl. pdcInstrumentIdList lIST......."+pdcInstrumentIdList[i]);
						reasonID=checkedlbxReasonHIDList[i];
						
						if(checkedlbxReasonHIDList[i].equalsIgnoreCase("test")){
							reasonID="";
						}
						
						
					 bufInsSql =	new StringBuffer();
					 insertPrepStmtObject = new PrepStmtObject();
					
					 bufInsSql.append("insert into cr_instrument_update_dtl(INSTRUMENT_BATCH_ID,INSTRUMENT_ID,INSTRUMENT_TYPE,PDC_FLAG,INSTRUMENT_NO,INSTRUMENT_DATE," +
					 		" INSTRUMENT_AMOUNT,TDS_AMOUNT,REASON_ID,PDC_INSTRUMENT_ID," +
					 		" CURRENT_STATUS,NEXT_STATUS,Deposit_bank_id,DEPOSIT_BRANCH_ID,DEPOSIT_MICR_CODE,DEPOSIT_IFSC_CODE,DEPOSIT_BANK_ACCOUNT,REC_STATUS,value_date,REASON_REMARKS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
					 
							 bufInsSql.append(" values ( ");
							 bufInsSql.append(" ?," );//instrument batch
							 bufInsSql.append(" ?," );//instrument id
							 bufInsSql.append(" ?," );//instrument type
							 bufInsSql.append(" ?," );//pdc flg
							 bufInsSql.append(" ?," );//inst ni
							 bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"'),");//ins date
							 bufInsSql.append(" ?," );//instr amount
							 bufInsSql.append(" ?," );//tds amount
							 bufInsSql.append(" ?," );//reason id
							 bufInsSql.append(" ?," );//pdc ins id
							 
							 bufInsSql.append(" ?," );//curren stagt
							 
							if(status.equalsIgnoreCase("STC")){
								 bufInsSql.append(" 'C'," );
							}
							if(status.equalsIgnoreCase("RBP")){
								 bufInsSql.append(" 'R'," );
							}
							if(status.equalsIgnoreCase("STP")){
								 bufInsSql.append(" 'S'," );
							}
							if(status.equalsIgnoreCase("CBP")){
								 bufInsSql.append(" 'X'," );
							}
							
							if(status.equalsIgnoreCase("BBR")){
								 bufInsSql.append(" 'B'," );
							}
							
							 bufInsSql.append(" ?," );//deposit bank
							 bufInsSql.append(" ?," );//deposit branch
							 bufInsSql.append(" ?," );//
							 bufInsSql.append(" ?," );
							 bufInsSql.append(" ?," );
							 bufInsSql.append(" 'A'," );
// Start by Prashant 	
							 bufInsSql.append(" STR_TO_DATE(?,'"+dateFormat+"'),");//VALUE_DATE
// End by Prashant 	
							 bufInsSql.append(" ?," );
							 bufInsSql.append(" ?," );
							 bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"'), " );
							 bufInsSql.append(" ?," );
							 bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"'))" );
							 
		
							 if(updateBatchID < 0)
									insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addInt(updateBatchID);
							 
							 
							 logger.info("In updateInstrumentDetailForPayment..instrumentIDList.Dao Impl."+instrumentIDList[i]);
							 
							 if(CommonFunction.checkNull(instrumentIDList[i]).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString((CommonFunction.checkNull(instrumentIDList[i]).trim()));
							
							 logger.info("In updateInstrumentDetailForPayment.getInstrumentType..Dao Impl."+chequeStatusVO.getInstrumentType());
							 
							 
							if(CommonFunction.checkNull(chequeStatusVO.getInstrumentType()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(chequeStatusVO.getInstrumentType()).trim()));
					 
							 logger.info("In updateInstrumentDetailForPayment..getPdcFlag...Dao Impl."+CommonFunction.checkNull(chequeStatusVO.getPdcFlag()));
							
							
						if(CommonFunction.checkNull(chequeStatusVO.getPdcFlag()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(chequeStatusVO.getPdcFlag()).trim()));
						
						
						 if(checkedinstrumentNoList.length>0){
								insertPrepStmtObject.addString((CommonFunction.checkNull(checkedinstrumentNoList[i]).trim()));
							}else{
							
								insertPrepStmtObject.addNull();
							}


							 if(checkeddateList.length>0){
									insertPrepStmtObject.addString((CommonFunction.checkNull(checkeddateList[i]).trim()));
								}else{
								
									insertPrepStmtObject.addNull();
								}

							 if(checkedinstrumentAmountList.length>0){
								insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(checkedinstrumentAmountList[i]).trim())).toString());
							}else{
					
								insertPrepStmtObject.addNull();
							}

							 if(tdsAmountListList.length>0){
									insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(tdsAmountListList[i]).trim())).toString());
								}else{
						
									insertPrepStmtObject.addNull();
								}
							
							logger.info("In updateInstrumentDetailForPayment.reasonID..Dao Impl."+reasonID);
							
							
							
							if(CommonFunction.checkNull(reasonID).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString((CommonFunction.checkNull(reasonID).trim()));
							
							logger.info("In updateInstrumentDetailForPayment.pdcInstrumentIdList..Dao Impl."+pdcInstrumentIdList[i]);
							
							
							if((pdcInstrumentIdList[i]).equalsIgnoreCase("qqq")){
								
								pdcInstrum = "";
							}else{
								pdcInstrum = pdcInstrumentIdList[i].trim();
							}
							logger.info("In updateInstrumentDetailForPayment.pdcInstrum..Dao Impl."+CommonFunction.checkNull(pdcInstrum));
							
							
							
							if(CommonFunction.checkNull(pdcInstrum).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString((CommonFunction.checkNull(pdcInstrum).trim()));
							
						
							logger.info("In updateInstrumentDetailForPayment..checkedstatusList.Dao Impl.    current status  :    "+checkedstatusList[i]);
							
						
							
							if(CommonFunction.checkNull(checkedstatusList[i]).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString((CommonFunction.checkNull(checkedstatusList[i]).trim()));
							
							
							logger.info("In updateInstrumentDetailForPayment..checkedstatusList.Dao Impl. value of i  :  "+i);
	//ravi start
							
							if(depositBankIdLength>0){
								if(CommonFunction.checkNull(depositBankIdList[i]).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString((CommonFunction.checkNull(depositBankIdList[i])));	
							}else{
								insertPrepStmtObject.addNull();
							}
							if(depositBranchIdLength>0)
							{
								if(CommonFunction.checkNull(depositBranchIdList[i]).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString((CommonFunction.checkNull(depositBranchIdList[i])));
							}
							else
								insertPrepStmtObject.addNull();
							
							if(depositMicrCodeLength>0)
							{
								if(CommonFunction.checkNull(depositMicrCodeList[i]).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString((CommonFunction.checkNull(depositMicrCodeList[i])));
							}
							else
								insertPrepStmtObject.addNull();
							
							if(depositIfscCodeLength>0)
							{
								if(CommonFunction.checkNull(depositIfscCodeList[i]).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString((CommonFunction.checkNull(depositIfscCodeList[i])));
							}
							else
								insertPrepStmtObject.addNull();
							
							if(depositBankAccountLength>0)
							{
								if(CommonFunction.checkNull(depositBankAccountList[i]).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString((CommonFunction.checkNull(depositBankAccountList[i])));
							}
							else
								insertPrepStmtObject.addNull();		
			
							//end 
							logger.info("In updateInstrumentDetail...Dao Impl."+chequeStatusVO.getMakerID());
// Start by Prashant 								
							if(checkedvalueDateList.length>0)
							{
								if(CommonFunction.checkNull(checkedvalueDateList[i]).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString((CommonFunction.checkNull(checkedvalueDateList[i])));
							}
							else
								insertPrepStmtObject.addNull();	
							
							if(checkedReasonRemarksList.length>0)
							{
								if((CommonFunction.checkNull(checkedReasonRemarksList[i])).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString((CommonFunction.checkNull(checkedReasonRemarksList[i])));
							}
							else
								insertPrepStmtObject.addNull();	
							
							
// End by Prashant 								
							
							if(CommonFunction.checkNull(chequeStatusVO.getMakerID()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString((CommonFunction.checkNull(chequeStatusVO.getMakerID()).trim()));
							
							logger.info("In updateInstrumentDetail...Dao Impl."+chequeStatusVO.getMakerDate());
							
							
							if(CommonFunction.checkNull(chequeStatusVO.getMakerDate()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString((CommonFunction.checkNull(chequeStatusVO.getMakerDate()).trim()));
							
							// Start By Prashant
							if(CommonFunction.checkNull(chequeStatusVO.getMakerID()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString((CommonFunction.checkNull(chequeStatusVO.getMakerID()).trim()));
							
							logger.info("In updateInstrumentDetail...Dao Impl."+chequeStatusVO.getMakerDate());
							
							
							if(CommonFunction.checkNull(chequeStatusVO.getMakerDate()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString((CommonFunction.checkNull(chequeStatusVO.getMakerDate()).trim()));
							//End By Prashant
								
							
							
								insertPrepStmtObject.setSql(bufInsSql.toString());
								logger.info("Query---"+insertPrepStmtObject.printQuery());
								qryList.add(insertPrepStmtObject);

								
					}
					status1=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
					   		              
			        if(status1)
			        {
			           	  try
			           	  {
			           		  in.add(chequeStatusVO.getCompanyID());
			           		  in.add(updateBatchID);
			           		  String date=CommonFunction.changeFormat(CommonFunction.checkNull(chequeStatusVO.getMakerDate()).trim());
			           		  in.add(date);
			           		  in.add((CommonFunction.checkNull(chequeStatusVO.getMakerID())));
			           		  in.add("SINGLE");
			           		  out.add(s1.toString());
			           		  out.add(s2.toString());
			           		  logger.info("Update_Cheque_Status("+in.toString()+","+out.toString()+")"); 
			           		  outMessages=(ArrayList) ConnectionDAO.callSP("Update_Cheque_Status",in,out);
			           		  s1.append(CommonFunction.checkNull(outMessages.get(0)));
			           		  s2.append(CommonFunction.checkNull(outMessages.get(1)));
			           		  logger.info("s1: "+s1); 
			          		  logger.info("s2: "+s2);			  		        
			           		  logger.info("After proc call...."); 
			  			            	
			           		  if((s1.toString()).equals("S"))
			            	  {
			            		  procresult=s1.toString();
			            	  }
			            	  else
			            	  {
			  						procresult=s2.toString();
			  						logger.info("Proc Exception----"+s2);
			  						StringBuffer  updateQuery =	new StringBuffer();
			  						PrepStmtObject instObject = new PrepStmtObject();			 				  
			  						updateQuery.append( "UPDATE CR_INSTRUMENT_UPDATE_DTL  SET REC_STATUS = 'E',ERROR_MSG = '"+procresult+"' WHERE INSTRUMENT_BATCH_ID = '"+updateBatchID+"'");
			  						instObject.setSql(updateQuery.toString());
			 						logger.info("Query when procedure return error   :   "+instObject.printQuery());
			 						ArrayList list =  new ArrayList();
			 						list.add(instObject);
			 						boolean flg=ConnectionDAO.sqlInsUpdDeletePrepStmt(list);	
			 						if(flg)
			 							logger.info("Query when procedure return error   Status   :   "+flg);
			  						
			  				  }
			           	  }
			              catch (Exception e) 
			              {
			            	  procresult="Exception occur During Procedure call,Please contact administrator....";
			        		  StringBuffer  updateQuery =	new StringBuffer();
			        		  PrepStmtObject instObject = new PrepStmtObject();			 				  
			        		  updateQuery.append( "UPDATE CR_INSTRUMENT_UPDATE_DTL  SET REC_STATUS = 'E',ERROR_MSG ='Exception occur During Procedure call' WHERE INSTRUMENT_BATCH_ID = '"+updateBatchID+"'");
			        		  instObject.setSql(updateQuery.toString());
			        		  logger.info("Query when procedure return exception   :   "+instObject.printQuery());
			        		  ArrayList list =  new ArrayList();
			        		  list.add(instObject);
			        		  boolean flg=ConnectionDAO.sqlInsUpdDeletePrepStmt(list);	
			        		  if(flg)
			        			logger.info("Query when procedure return error   Status   :   "+flg);
			  			   }
			         }       

				}catch (Exception e) {
					
					e.printStackTrace();
				}
				finally
				{
					in = null;
					out = null;
					outMessages = null;
					qryList = null;
					s1 = null;
					s2 = null;
					instrumentBatchId = null;
					instrumentBatchIdValue = null;
					updateBatchIDforQryString = null;
					updateMainQuery = null;
					bufInsSql = null;
					tdsAmountListList = null;
					instrumentIDList = null;
					checkedinstrumentNoList = null;
					checkeddateList = null;
					checkedinstrumentAmountList = null;
					checkedstatusList = null;
					checkedlbxBPTypeHIDList = null;
					checkedlbxBPNIDList = null;
					checkedlbxBankIDList = null;
					checkedlbxBranchIDList = null;
					status = null;
					checkedlbxReasonHIDList = null;
					pdcInstrumentIdList = null;
					pdcFlag = null;
					instrumentType = null;
					depositBankIdList = null;
					depositBranchIdList = null;
					depositMicrCodeList = null;
					depositIfscCodeList = null;
					depositBankAccountList = null;
					checkedvalueDateList = null;
					chequeStatusVO=null;
					checkedReasonRemarksList=null;
				}
				
			 
		return procresult;
	}
		

			public String updateDepositCheque(String[] tdsAmountListList,
					String[] instrumentIDList,
					String[] checkedinstrumentNoList, String[] checkeddateList,
					String[] checkedinstrumentAmountList,
					String[] checkedstatusList,
					String[] checkedlbxBPTypeHIDList,
					String[] checkedlbxBPNIDList,
					String[] checkedlbxBankIDList,
					String[] checkedlbxBranchIDList,
					ChequeStatusVO chequeStatusVO, String status,
					String[] checkedlbxReasonHIDList, String lbxBankID,
					String lbxBranchID, String micr, String ifscCode,
					String bankAccount,String[] pdcInstrumentIdList, String pdcFlag, String instrumentType,String[] checkedvalueDateList) {
//				 Connection con=ConnectionDAO.getConnection();	 
		   	    ArrayList<Object> in =new ArrayList<Object>();
           		ArrayList<Object> out =new ArrayList<Object>();
           		ArrayList outMessages = new ArrayList();
           		
           		String s1="";
           		String s2="";
           		String procval="";
				ArrayList qryList =  new ArrayList();
				boolean status1=false;
				StringBuffer bufInsSql =null;
				PrepStmtObject insertPrepStmtObject = null;	
				StringBuilder instrumentBatchId = new StringBuilder(); 
				StringBuilder instrumentBatchIdValue = new StringBuilder(); 
				//String instrumentBatchId="";
				//String instrumentBatchIdValue="";
				int updateBatchID;
				ArrayList updateBatchIDforQry= null;
				ArrayList updateBatchIDformainQry=null;
				StringBuilder updateBatchIDforQryString = new StringBuilder(); 
				StringBuilder updateMainQuery = new StringBuilder();
				//String updateBatchIDforQryString="";
				//String updateMainQuery="";
				String reasonID="";
				String pdcInstrum="";
//				CallableStatement cst=null;
				logger.info("In updateDepositCheque...Dao Impl.");
				 boolean procstatus = false;
				
				try{
					instrumentBatchId.append("select function_sequence('CHEQUE_STATUS')");
					instrumentBatchIdValue.append(ConnectionDAO.singleReturn(instrumentBatchId.toString()));
			  
					updateBatchID = Integer.parseInt(instrumentBatchIdValue.toString());
					
					
						logger.info("instrumentIDList----->"+instrumentIDList.length);
					for(int i=0;i<instrumentIDList.length;i++)
					{
						
						reasonID=checkedlbxReasonHIDList[i];
						
						if(checkedlbxReasonHIDList[i].equalsIgnoreCase("test")){
							reasonID="";
						}
						
						
					 bufInsSql =	new StringBuffer();
					 insertPrepStmtObject = new PrepStmtObject();
					 
					  
					 
					 bufInsSql.append("insert into cr_instrument_update_dtl(INSTRUMENT_BATCH_ID,INSTRUMENT_ID,INSTRUMENT_NO,INSTRUMENT_DATE," +
					 		" INSTRUMENT_AMOUNT,TDS_AMOUNT,REASON_ID,DEPOSIT_BANK_ID,DEPOSIT_BRANCH_ID,DEPOSIT_MICR_CODE,DEPOSIT_IFSC_CODE," +
					 		"DEPOSIT_BANK_ACCOUNT," +
					 		" PDC_INSTRUMENT_ID,CURRENT_STATUS,NEXT_STATUS,REC_STATUS,PDC_FLAG,INSTRUMENT_TYPE,VALUE_DATE,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
					 
							 bufInsSql.append(" values ( ");
							 bufInsSql.append(" ?," );//1
							 bufInsSql.append(" ?," );//2
							 bufInsSql.append(" ?," );//3
							 bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"'),");//4
							 bufInsSql.append(" ?," );//5
							 bufInsSql.append(" ?," );//6
							 bufInsSql.append(" ?," );//7
							 bufInsSql.append(" ?," );//8
							 bufInsSql.append(" ?," );//9
							 bufInsSql.append(" ?," );//10
							 bufInsSql.append(" ?," );//11
							 bufInsSql.append(" ?," );//12
							 bufInsSql.append(" ?," );//13
							 
							 bufInsSql.append(" ?," );//14
							 bufInsSql.append(" 'D'," );//15
				
							
							 bufInsSql.append(" 'A'," );//16
							 bufInsSql.append(" ?," );//17
							 bufInsSql.append(" ?," );//18
// Start by Prashant 	
							 bufInsSql.append(" STR_TO_DATE(?,'"+dateFormat+"'),");//19
							 bufInsSql.append(" ?," );//20
							 bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND), " );//21
							 bufInsSql.append(" ?," );//22
							 bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) )" );//23
// End by Prashant 								 
		
							 if(CommonFunction.checkNull(updateBatchID).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addInt(updateBatchID);
						 
							if(CommonFunction.checkNull(instrumentIDList[i]).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString((CommonFunction.checkNull(instrumentIDList[i]).trim()));
							
							if(checkedinstrumentNoList.length>0){
								insertPrepStmtObject.addString((CommonFunction.checkNull(checkedinstrumentNoList[i]).trim()));
							}else{
							
								insertPrepStmtObject.addNull();
							}
//							else
//								insertPrepStmtObject.addString((CommonFunction.checkNull(checkedinstrumentNoList[i]).trim()));
//							logger.info("checkeddateList()))))))"+checkeddateList.length);
							if(checkeddateList.length>0){
								insertPrepStmtObject.addString((CommonFunction.checkNull(checkeddateList[i]).trim()));
							}
							else{
								insertPrepStmtObject.addNull();
							}
							
							logger.info("1222222222222222222222222222222222222222222222222222222222----"+checkedinstrumentAmountList[i]);
							if(checkedinstrumentAmountList.length>0){
								insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(checkedinstrumentAmountList[i]).trim())).toString());
							}else{
								insertPrepStmtObject.addNull();
							}
							
							if(tdsAmountListList.length>0){
								insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(tdsAmountListList[i]).trim())).toString());
							}else{
								insertPrepStmtObject.addNull();
							}
							
							if(CommonFunction.checkNull(reasonID).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString((CommonFunction.checkNull(reasonID).trim()));
							
							
							
							if(CommonFunction.checkNull(lbxBankID).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString((CommonFunction.checkNull(lbxBankID).trim()));
							
							if(CommonFunction.checkNull(lbxBranchID).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString((CommonFunction.checkNull(lbxBranchID).trim()));
							
							if(CommonFunction.checkNull(micr).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString((CommonFunction.checkNull(micr).trim()));
							
							
							
							if(CommonFunction.checkNull(ifscCode).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString((CommonFunction.checkNull(ifscCode).trim()));
							
							
							if(CommonFunction.checkNull(bankAccount).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString((CommonFunction.checkNull(bankAccount).trim()));
							
							
							if((pdcInstrumentIdList[i]).equalsIgnoreCase("qqq")){
								
								pdcInstrum = "";
							}else{
								pdcInstrum = pdcInstrumentIdList[i].trim();
								
							}
							
							
							if(CommonFunction.checkNull(pdcInstrum).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString((CommonFunction.checkNull(pdcInstrum).trim()));
							
							if(CommonFunction.checkNull(checkedstatusList[i]).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString((CommonFunction.checkNull(checkedstatusList[i]).trim()));
							
							if(CommonFunction.checkNull(pdcFlag).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString((CommonFunction.checkNull(pdcFlag).trim()));
							
							
							if(CommonFunction.checkNull(instrumentType).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString((CommonFunction.checkNull(instrumentType).trim()));
// Start by Prashant 							
							if(checkedvalueDateList.length>0)
							{
								if(CommonFunction.checkNull(checkedvalueDateList[i]).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString((CommonFunction.checkNull(checkedvalueDateList[i])));
							}
							else
								insertPrepStmtObject.addNull();	
// End by Prashant 							
																				
							
							if(CommonFunction.checkNull(chequeStatusVO.getMakerID()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString((CommonFunction.checkNull(chequeStatusVO.getMakerID()).trim()));
							
							if(CommonFunction.checkNull(chequeStatusVO.getMakerDate()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString((CommonFunction.checkNull(chequeStatusVO.getMakerDate()).trim()));
						//Start By Prashant	
							if(CommonFunction.checkNull(chequeStatusVO.getMakerID()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString((CommonFunction.checkNull(chequeStatusVO.getMakerID()).trim()));
							
							if(CommonFunction.checkNull(chequeStatusVO.getMakerDate()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString((CommonFunction.checkNull(chequeStatusVO.getMakerDate()).trim()));
						 // End By Prashant
								
								insertPrepStmtObject.setSql(bufInsSql.toString());
								qryList.add(insertPrepStmtObject);
								
								
								
					}
					logger.info("In printquery.................."+insertPrepStmtObject.printQuery());
					status1=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			        logger.info("In updateInstrumentDetail......................"+status1);
			        if(status1)
			        {
			        	try
			        	{
			        		in.add(chequeStatusVO.getCompanyID());
			        		in.add(updateBatchID);
			        		String date=CommonFunction.changeFormat(CommonFunction.checkNull(chequeStatusVO.getMakerDate()).trim());
			        		in.add(date);
			        		in.add((CommonFunction.checkNull(chequeStatusVO.getMakerID())));
			        		in.add("SINGLE");
			        		out.add(s1.toString());
			        		out.add(s2.toString());
			        		logger.info("Update_Cheque_Status("+in.toString()+","+out.toString()+")"); 
			        		outMessages=(ArrayList) ConnectionDAO.callSP("Update_Cheque_Status",in,out);
			        		s1=CommonFunction.checkNull(outMessages.get(0));
			        		s2=CommonFunction.checkNull(outMessages.get(1));
			        		logger.info("Update_Cheque_Status s1: "+s1); 
			        		logger.info("Update_Cheque_Status s2: "+s2);
			        		logger.info("After proc call...."); 
			        		if(CommonFunction.checkNull(s1).equalsIgnoreCase("S"))
			        		{
			        			procval=s1;
			        		}
			        		else
			        		{
			        			procval=s2;
			        			logger.info("Proc Exception----"+s2);
			        			StringBuffer  updateQuery =	new StringBuffer();
			        			PrepStmtObject instObject = new PrepStmtObject();			 				  
			        			updateQuery.append( "UPDATE CR_INSTRUMENT_UPDATE_DTL  SET REC_STATUS = 'E',ERROR_MSG = '"+procval+"' WHERE INSTRUMENT_BATCH_ID = '"+updateBatchID+"'");
			        			instObject.setSql(updateQuery.toString());
			        			logger.info("Query when procedure return error   :   "+instObject.printQuery());
			        			ArrayList list =  new ArrayList();
			        			list.add(instObject);
			        			boolean flg=ConnectionDAO.sqlInsUpdDeletePrepStmt(list);	
			        			if(flg)
			        				logger.info("Query when procedure return error   Status   :   "+flg);
			        		}
			        	}
			        	catch (Exception e) 
			        	{
							procval="Exception occur During Procedure call,Please contact administrator....";
		        			StringBuffer  updateQuery =	new StringBuffer();
		        			PrepStmtObject instObject = new PrepStmtObject();			 				  
		        			updateQuery.append( "UPDATE CR_INSTRUMENT_UPDATE_DTL  SET REC_STATUS = 'E',ERROR_MSG ='Exception occur During Procedure call' WHERE INSTRUMENT_BATCH_ID = '"+updateBatchID+"'");
		        			instObject.setSql(updateQuery.toString());
		        			logger.info("Query when procedure return exception   :   "+instObject.printQuery());
		        			ArrayList list =  new ArrayList();
		        			list.add(instObject);
		        			boolean flg=ConnectionDAO.sqlInsUpdDeletePrepStmt(list);	
		        			if(flg)
		        				logger.info("Query when procedure return error   Status   :   "+flg);
						}
			  	  	}
				}catch (Exception e) {
					
					e.printStackTrace();
				}
				finally
				{
					in = null;
					out = null;
					outMessages = null;
					qryList = null;
					s1 = null;
					s2 = null;
					instrumentBatchId = null;
					instrumentBatchIdValue = null;
					updateBatchIDforQryString = null;
					updateMainQuery = null;
					bufInsSql = null;
					tdsAmountListList = null;
					instrumentIDList = null;
					checkedinstrumentNoList = null;
					checkeddateList = null;
					checkedinstrumentAmountList = null;
					checkedstatusList = null;
					checkedlbxBPTypeHIDList = null;
					checkedlbxBPNIDList = null;
					checkedlbxBankIDList = null;
					checkedlbxBranchIDList = null;
					status = null;
					checkedlbxReasonHIDList = null;
					pdcInstrumentIdList = null;
					pdcFlag = null;
					instrumentType = null;
					checkedvalueDateList = null;
					chequeStatusVO = null;
				}
				
			 
		return procval;
	}

	 
			public ArrayList<AssetVerificationVO> getAssetList() {
				
				ArrayList<AssetVerificationVO> assetList = new ArrayList<AssetVerificationVO>();
				logger.info("In getAssetList: ");
				
				 
		        ArrayList mainlist=new ArrayList();
				  ArrayList subList=new ArrayList();
  	               StringBuffer sbAppendToSQLCount= null;
   	               StringBuffer bufInsSql = null;
				try{



		                    logger.info("In getAssetList().....................................Dao Impl");

		           	               sbAppendToSQLCount=new StringBuffer();
		           	               bufInsSql =    new StringBuffer();

		                    bufInsSql.append( "select a.loan_id,a.assetid,b.loan_no,c.ASSET_COLLATERAL_DESC from cr_loan_collateral_m a, cr_loan_dtl b, cr_asset_collateral_m c" +
		                    		" where a.loan_id = b.loan_id and a.assetid = c.asset_id and a.rec_status = 'A' and c.asset_type = 'ASSET'");


		           	               logger.info("Query-----"+bufInsSql.toString());
		          	            mainlist = ConnectionDAO.sqlSelect(bufInsSql.toString());


				for(int i=0;i<mainlist.size();i++){

					subList=(ArrayList)mainlist.get(i);
					//logger.info("In getAssetList..."+subList.size());
					
					if(subList.size()>0){
						
						AssetVerificationVO assetVerificationVO=new AssetVerificationVO();
						assetVerificationVO.setLoanID((CommonFunction.checkNull(subList.get(0)).trim()));
						assetVerificationVO.setAssetID((CommonFunction.checkNull(subList.get(1)).trim()));
						assetVerificationVO.setLoanAccNo((CommonFunction.checkNull(subList.get(2)).trim()));
					  assetVerificationVO.setAssetDescription((CommonFunction.checkNull(subList.get(3)).trim()));
						    
						
						    assetList.add(assetVerificationVO);
					}
			}
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					mainlist.clear();
					mainlist = null;
					subList.clear();
					subList=null;
				}

				return assetList;
			}
			


		
		
			

			 
			  
			  
			 
			  
			  
			
				
				 
			  
			  
				//manas end



				









public boolean downLoadErrorLogReceipt(HttpServletRequest request,
		HttpServletResponse response, String userId) {
	logger.info("in..downLoadErrorLog().DAOIM.");
	boolean status=false;
    HttpSession session = null;	
    //String userID = "";
    StringBuffer txtReport = new StringBuffer();	       
    ArrayList dataList = null;
    ArrayList arrBatchID = null;
    ArrayList alSearchResults = null;
    ArrayList downloadResults = null;
    int count = 0;	
	ArrayList list= new ArrayList();
	StringBuilder query = new StringBuilder(); 
	query.append("select LOAN_NO,RECEIPT_MODE,INSTRUMENT_NO,RECEIPT_DATE,INSTRUMENT_DATE,RECEIPT_AMOUNT,BANK_ID,BRANCH_ID,BANK_ACCOUNT,TDS_AMOUNT,RECEIPT_NO,MAKER_REMARKS,reject_reason from cr_instrument_dtl_temp where MAKER_ID='"+userId+"' and uploaded_flag='N'");
	logger.info("In getUploadError()...............query...........DAOImpl "+query.toString());
	try
	{		
	list=ConnectionDAO.sqlSelect(query.toString());
   
          ArrayList alRecord = null;
          count = list.size();
            response.setContentType("text/plain");
            response.setHeader("Content-Disposition", "attachment;filename=ErrorLogUploadFile.csv");	
            logger.info("Checking header...");
            txtReport.append("Receipt Upload Error \n");	  
            txtReport.append(UploadDocuments.getPropertiesValue("lbl.LOAN_NO")+"|");
            txtReport.append(UploadDocuments.getPropertiesValue("lbl.RECEIPT_MODE")+"|");
            txtReport.append(UploadDocuments.getPropertiesValue("lbl.INSTRUMENT_NO")+"|");
            txtReport.append(UploadDocuments.getPropertiesValue("lbl.RECEIPT_DATE")+"|");
            txtReport.append(UploadDocuments.getPropertiesValue("lbl.INSTRUMENT_DATE")+"|");
            txtReport.append(UploadDocuments.getPropertiesValue("lbl.RECEIPT_AMOUNT")+"|");
            txtReport.append(UploadDocuments.getPropertiesValue("lbl.BANK_ID")+"|");
            txtReport.append(UploadDocuments.getPropertiesValue("lbl.BRANCH_ID")+"|");
            txtReport.append(UploadDocuments.getPropertiesValue("lbl.BANK_ACCOUNT")+"|");
            //txtReport.append(UploadDocuments.getPropertiesValue("lbl.STATUS")+"|");
            txtReport.append(UploadDocuments.getPropertiesValue("lbl.TDS_AMOUNT")+"|");
            txtReport.append(UploadDocuments.getPropertiesValue("lbl.RECEIPT_NO")+"|");
            txtReport.append(UploadDocuments.getPropertiesValue("lbl.MAKER_REMARKS")+"|");
            txtReport.append(UploadDocuments.getPropertiesValue("lbl.Reject_Reason")+"\n");

            logger.info("Size is:"+count);
            if(count>0)
            {
              for(int i=0; i<count; i++) 
              {	 
              alRecord = (ArrayList) list.get(i);
                      txtReport.append(((CommonFunction.checkNull(alRecord.get(0))).toString() + "|").trim());
                      txtReport.append(((CommonFunction.checkNull(alRecord.get(1))).toString() + "|").trim());
                      txtReport.append(((CommonFunction.checkNull(alRecord.get(2))).toString() + "|").trim());		                     
                      txtReport.append(((CommonFunction.checkNull(alRecord.get(3))).toString() + "|").trim());
                      txtReport.append(((CommonFunction.checkNull(alRecord.get(4))).toString() + "|").trim());
                      txtReport.append(((CommonFunction.checkNull(alRecord.get(5))).toString() + "|").trim());
                      txtReport.append(((CommonFunction.checkNull(alRecord.get(6))).toString() + "|").trim());
                      txtReport.append(((CommonFunction.checkNull(alRecord.get(7))).toString() + "|").trim());
                      txtReport.append(((CommonFunction.checkNull(alRecord.get(8))).toString() + "|").trim());
                      //txtReport.append(((CommonFunction.checkNull(alRecord.get(9))).toString() + "|").trim());
                      
                      txtReport.append(((CommonFunction.checkNull(alRecord.get(9))).toString() + "|").trim());
                      txtReport.append(((CommonFunction.checkNull(alRecord.get(10))).toString() + "|").trim());
                      txtReport.append(((CommonFunction.checkNull(alRecord.get(11))).toString() + "|").trim());
                      
                      txtReport.append(((CommonFunction.checkNull(alRecord.get(12))).toString() + "\n").trim());                 
                    
                }
            }
            else
            {
            	 txtReport.append("No Record Found");
            }
         
    	    ServletOutputStream  out=response.getOutputStream();	     
            out.print(txtReport.toString());
    	    out.close();
            out.flush();	           
            txtReport = null;
            status=true;
            alRecord.clear();
            alRecord= null;
          }
          catch (Exception e) 
          {
        	  logger.debug("IOException In downLoadErrorLog() ==>> "+e.getMessage());
          }	
          finally
          {
        	  	query = null;
        	  	list = null;
        	  	userId = null;
        	  	dataList.clear();
        	  	dataList= null;
        	  	arrBatchID.clear();
	    	    arrBatchID = null;
	    	    alSearchResults.clear();
	    	    alSearchResults = null;
	    	    downloadResults.clear();
	    	    downloadResults = null;
          }
       
          return status;
}

public ArrayList chequeStatusbyloanViewer(ChequeStatusVO chequeStatusVO) {
	
	ArrayList<ChequeStatusVO> chequeStatusList = new ArrayList<ChequeStatusVO>();
	logger.info("In chequeStatusbyloanViewer: method ");
	
	//String businessPartnerType="";
    //String businessPartnerName="";
    //String pdcFlag="";
    //String customerName="";
    //String paymentMode="";
    //String lbxBPNID="";
    //String chequeStatus="";
    //String lbxBPTypeHID="";
    //String loanAccNo="";
    //String instrumentType="";
    //String lbxLoanNoHID="";
    //String instrumentNo="";
	StringBuilder businessPartnerType = new StringBuilder(); 
	StringBuilder businessPartnerName = new StringBuilder(); 
	StringBuilder pdcFlag = new StringBuilder(); 
	StringBuilder customerName = new StringBuilder(); 
	StringBuilder paymentMode = new StringBuilder(); 
	StringBuilder lbxBPNID = new StringBuilder(); 
	StringBuilder chequeStatus = new StringBuilder(); 
	StringBuilder lbxBPTypeHID = new StringBuilder(); 
	StringBuilder loanAccNo = new StringBuilder(); 
	StringBuilder instrumentType = new StringBuilder(); 
	StringBuilder lbxLoanNoHID = new StringBuilder(); 
	StringBuilder instrumentNo = new StringBuilder(); 
    int noOfRecords;
    int count=0;
    ArrayList mainlist=new ArrayList();
	ArrayList subList=new ArrayList();
	try{



                logger.info("In chequeStatusbyloanViewer().....................................Dao Impl");
                
                businessPartnerType.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(chequeStatusVO.getBusinessPartnerType()).trim()));
                businessPartnerName.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(chequeStatusVO.getBusinessPartnerDesc()).trim()));
                pdcFlag.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(chequeStatusVO.getPdcFlag()).trim()));
                paymentMode.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(chequeStatusVO.getPaymentMode()).trim()));
                lbxBPNID.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(chequeStatusVO.getLbxBPNID()).trim()));
                chequeStatus.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(chequeStatusVO.getChequeStatus()).trim()));
                lbxBPTypeHID.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(chequeStatusVO.getLbxBPTypeHID()).trim()));
                noOfRecords=chequeStatusVO.getNoOfRecords();
                loanAccNo.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(chequeStatusVO.getLoanAccNo()).trim()));
                instrumentNo.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(chequeStatusVO.getInstrumentNo()).trim()));
                instrumentType.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(chequeStatusVO.getInstrumentType()).trim()));
                lbxLoanNoHID.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(chequeStatusVO.getLbxLoanNoHID()).trim()));
                customerName.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(chequeStatusVO.getCustomerName()).trim()));

       	               StringBuffer sbAppendToSQLCount=new StringBuffer();
       	               StringBuffer bufInsSql =    new StringBuffer();
       	            StringBuffer bufInsSqlTempCount = new StringBuffer();
       	               
       	               if((instrumentType.toString()).equalsIgnoreCase("P")){

       	            	   bufInsSql.append("select a.instrument_no,DATE_FORMAT(a.INSTRUMENT_DATE,'%d-%m-%Y'),a.INSTRUMENT_AMOUNT,a.BPID,a.BPTYPE,b.bank_name,a.ISSUEING_BANK_ID, "+
       	            			   " c.bank_branch_name,a.ISSUEING_BRANCH_ID,a.ISSUEING_BANK_ACCOUNT,a.REC_STATUS,d.Description,e.bp_name,A.INSTRUMENT_ID,a.tds_amount,"+
       	            			   " a.pdc_instrument_id, a.instrument_type,a.DEPOSIT_BANK_ID,a.DEPOSIT_BRANCH_ID,a.DEPOSIT_MICR_CODE,a.DEPOSIT_IFSC_CODE, "+
       	            			   " a.DEPOSIT_BANK_ACCOUNT,u.REASON_ID,r.REASON_DESC,z.loan_no "+
       	            			   " from cr_instrument_dtl a "+
       	            			   " left join com_bank_m b on a.ISSUEING_BANK_ID = b.BANK_ID "+
       	            			   " left join com_bankbranch_m c on a.ISSUEING_BRANCH_ID = c.bank_BRANCH_ID "+
       	            			   " left join generic_master d on a.bptype=d.value "+
       	            			   " left join business_partner_view e on a.TXNID = e.LOAN_ID and a.bpid=e.bp_id and a.bptype=e.bp_type "+
       	            			   " left join cr_instrument_update_dtl u on u.INSTRUMENT_ID=a.INSTRUMENT_ID and u.INSTRUMENT_TYPE=a.INSTRUMENT_TYPE and u.next_STATUS=a.REC_STATUS " +
       	            			   " and u.INSTRUMENT_BATCH_ID = (select max(k.INSTRUMENT_BATCH_ID) from cr_instrument_update_dtl k where k.INSTRUMENT_ID=a.INSTRUMENT_ID) "+
       	            			   " left join com_reason_m r on  r.REASON_ID=u.REASON_ID "+
       	            			   " left join cr_loan_dtl z on a.TXNID = z.loan_id "+
       	            			   " where    A.instrument_type='P' and ifnull(a.TXN_TYPE,'')='LIM' and a.rec_status not in ('P','F') "+
       	            			   " and a.INSTRUMENT_ID not in(select INSTRUMENT_ID from cr_instrument_update_dtl where REC_STATUS in ('P','F') )"); 
       	            			  
       	            	   
               /* bufInsSql.append( "select a.instrument_no,DATE_FORMAT(a.INSTRUMENT_DATE,'"+dateFormat+"'),a.INSTRUMENT_AMOUNT,a.BPID,a.BPTYPE,b.bank_name,a.ISSUEING_BANK_ID," +
                		" c.bank_branch_name,a.ISSUEING_BRANCH_ID,a.ISSUEING_BANK_ACCOUNT,a.REC_STATUS,d.Description,e.bp_name,A.INSTRUMENT_ID,a.tds_amount,a.pdc_instrument_id," +
                		" a.instrument_type,a.DEPOSIT_BANK_ID,a.DEPOSIT_BRANCH_ID,a.DEPOSIT_MICR_CODE,a.DEPOSIT_IFSC_CODE,a.DEPOSIT_BANK_ACCOUNT " +
                		" from cr_instrument_dtl a,com_bank_m b,com_bankbranch_m c,generic_master d,business_partner_view e  where" +
                		"  a.ISSUEING_BANK_ID = b.BANK_ID and a.ISSUEING_BRANCH_ID = c.bank_BRANCH_ID " +
                		" and instrument_type='P'  and a.bpid=e.bp_id and  a.bptype=d.value  and a.TXNID = e.LOAN_ID and a.bptype=e.bp_type and a.rec_status not in ('P','F') and a.INSTRUMENT_ID not in(select INSTRUMENT_ID from cr_instrument_update_dtl where REC_STATUS in ('P','F'))");*/
                bufInsSqlTempCount.append("select count(1) " +
                		" from cr_instrument_dtl a,com_bank_m b,com_bankbranch_m c,generic_master d,business_partner_view e  where" +
                		"  a.ISSUEING_BANK_ID = b.BANK_ID and a.ISSUEING_BRANCH_ID = c.bank_BRANCH_ID " +
                		" and instrument_type='P'  and a.TXNID = e.LOAN_ID and a.BPTYPE = d.value and a.bpid=e.bp_id and a.bptype=d.value and a.bptype=e.bp_type and ifnull(a.TXN_TYPE,'')='LIM' and a.rec_status not in ('P','F') and a.INSTRUMENT_ID not in(select INSTRUMENT_ID from cr_instrument_update_dtl where REC_STATUS in ('P','F') )");
       
       	               }else{
       	            	   
       	            	   
       	            	bufInsSql.append("select a.instrument_no,DATE_FORMAT(a.INSTRUMENT_DATE,'%d-%m-%Y'),a.INSTRUMENT_AMOUNT,a.BPID,a.BPTYPE,b.bank_name,a.ISSUEING_BANK_ID, "+
       	            			   " c.bank_branch_name,a.ISSUEING_BRANCH_ID,a.ISSUEING_BANK_ACCOUNT,a.REC_STATUS,d.Description,e.bp_name,A.INSTRUMENT_ID,a.tds_amount,"+
       	            			   " a.pdc_instrument_id, a.instrument_type,a.DEPOSIT_BANK_ID,a.DEPOSIT_BRANCH_ID,a.DEPOSIT_MICR_CODE,a.DEPOSIT_IFSC_CODE, "+
       	            			   " a.DEPOSIT_BANK_ACCOUNT,u.REASON_ID,r.REASON_DESC,z.loan_no "+
       	            			   " from cr_instrument_dtl a "+
       	            			   " left join com_bank_m b on a.ISSUEING_BANK_ID = b.BANK_ID "+
       	            			   " left join com_bankbranch_m c on a.ISSUEING_BRANCH_ID = c.bank_BRANCH_ID "+
       	            			   " left join generic_master d on a.bptype=d.value "+
       	            			   " left join business_partner_view e on a.TXNID = e.LOAN_ID and a.bpid=e.bp_id and a.bptype=e.bp_type "+
       	            			   " left join cr_instrument_update_dtl u on u.INSTRUMENT_ID=a.INSTRUMENT_ID and u.INSTRUMENT_TYPE=a.INSTRUMENT_TYPE and u.next_STATUS=a.REC_STATUS " +
       	            			   " and u.INSTRUMENT_BATCH_ID = (select max(k.INSTRUMENT_BATCH_ID) from cr_instrument_update_dtl k where k.INSTRUMENT_ID=a.INSTRUMENT_ID) "+
       	            			   " left join com_reason_m r on  r.REASON_ID=u.REASON_ID "+
       	            			   " left join cr_loan_dtl z on a.TXNID = z.loan_id "+
       	            			   " where    A.instrument_type='R' and ifnull(a.TXN_TYPE,'')='LIM' and a.rec_status not in ('P','F') "+
       	            			   " and a.INSTRUMENT_ID not in(select INSTRUMENT_ID from cr_instrument_update_dtl where REC_STATUS in ('P','F') ) ");  
       	            			 
       	            	   
       	            	   
	            	 /*bufInsSql.append( "select a.instrument_no,DATE_FORMAT(a.INSTRUMENT_DATE,'"+dateFormat+"'),a.INSTRUMENT_AMOUNT,a.BPID,a.BPTYPE,b.bank_name,a.ISSUEING_BANK_ID," +
                    		" c.bank_branch_name,a.ISSUEING_BRANCH_ID,a.ISSUEING_BANK_ACCOUNT,a.REC_STATUS,d.Description,e.bp_name,A.INSTRUMENT_ID,a.tds_amount,a.pdc_instrument_id," +
                    		" a.instrument_type,a.DEPOSIT_BANK_ID,a.DEPOSIT_BRANCH_ID,a.DEPOSIT_MICR_CODE,a.DEPOSIT_IFSC_CODE,a.DEPOSIT_BANK_ACCOUNT  " +
                    		" from cr_instrument_dtl a,com_bank_m b,com_bankbranch_m c,generic_master d,business_partner_view e  where" +
                    		"  a.ISSUEING_BANK_ID = b.BANK_ID and a.ISSUEING_BRANCH_ID = c.bank_BRANCH_ID " +
                    		" and instrument_type='R' and a.TXNID = e.LOAN_ID and a.bpid=e.bp_id and a.bptype=d.value and a.bptype=e.bp_type and a.rec_status not in ('P','F') and a.INSTRUMENT_ID not in(select INSTRUMENT_ID from cr_instrument_update_dtl where REC_STATUS in ('P','F') ) ");  */ 
	            	bufInsSqlTempCount.append("select count(1) " +
	            			" from cr_instrument_dtl a,com_bank_m b,com_bankbranch_m c,generic_master d,business_partner_view e  where" +
                		"  a.DEPOSIT_BANK_ID = b.BANK_ID and a.DEPOSIT_BRANCH_ID = c.bank_BRANCH_ID " +
                		" and instrument_type='R' and a.BPTYPE = d.value  and a.TXNID = e.LOAN_ID and a.bpid=e.bp_id and a.bptype=d.value and a.bptype=e.bp_type and ifnull(a.TXN_TYPE,'')='LIM' and a.rec_status not in ('P','F') and a.INSTRUMENT_ID not in(select INSTRUMENT_ID from cr_instrument_update_dtl where REC_STATUS in ('P','F') ) ");   
           	 	   
       	               }
       	               
       	            if((!((lbxLoanNoHID.toString()).equalsIgnoreCase("")))) {
       	                  bufInsSql.append(" AND a.TXNID='"+lbxLoanNoHID+"'");
       	               bufInsSqlTempCount.append(" AND a.TXNID='"+lbxLoanNoHID+"'");

       	               }	
       	            
//       	         if((!(customerName.equalsIgnoreCase("")))) {
//   	                  bufInsSql.append(" AND a.TXNID='"+lbxLoanNoHID+"'");
//
//   	               }
       	              if((!((lbxBPTypeHID.toString()).equalsIgnoreCase("")))) {
       	                  bufInsSql.append(" AND a.BPTYPE='"+lbxBPTypeHID+"'");
       	               bufInsSqlTempCount.append(" AND a.BPTYPE='"+lbxBPTypeHID+"'");
       	               }

       	               if((!((lbxBPNID.toString()).equalsIgnoreCase("")))) {
       	                   bufInsSql.append("  AND a.BPID='"+lbxBPNID+"'");
       	                bufInsSqlTempCount.append("  AND a.BPID='"+lbxBPNID+"'");
       	               }
       	              if((!((pdcFlag.toString()).equalsIgnoreCase("")))) {
       	                   bufInsSql.append("  AND a.PDC_FLAG='"+pdcFlag+"'");
       	                bufInsSqlTempCount.append("  AND a.PDC_FLAG='"+pdcFlag+"'");
       	               }
       	             if((!((instrumentNo.toString()).equalsIgnoreCase("")))) {
       	                  bufInsSql.append(" AND a.INSTRUMENT_NO='"+instrumentNo+"'");
       	               bufInsSqlTempCount.append(" AND a.INSTRUMENT_NO='"+instrumentNo+"'");
       	               }
                       if((!((chequeStatus.toString()).equalsIgnoreCase("ALL")))){
                    	   if((!((chequeStatus.toString()).equalsIgnoreCase("")))) {
           	                   bufInsSql.append("  AND a.REC_STATUS='"+chequeStatus+"'");
           	                bufInsSqlTempCount.append("  AND a.REC_STATUS='"+chequeStatus+"'");
           	               }
                       }
       	              if((!((paymentMode.toString()).equalsIgnoreCase("")))) {
       	                   bufInsSql.append("  AND a.INSTRUMENT_MODE='"+paymentMode+"'");
       	                bufInsSqlTempCount.append("  AND a.INSTRUMENT_MODE='"+paymentMode+"'");
       	               }
       	              
       	           bufInsSql.append("  order by a.INSTRUMENT_DATE");
       	           
       	               logger.info("Query-----"+bufInsSql.toString());
      	            mainlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
      	           
      	          logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
                  count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
                  
                int startRecordIndex=0;
                int endRecordIndex=0;
      			logger.info("current PAge Link no .................... "+chequeStatusVO.getCurrentPageLink());
      			if(chequeStatusVO.getCurrentPageLink()>1)
      			{
      				startRecordIndex = (chequeStatusVO.getCurrentPageLink()-1) * noOfRecords;
      				endRecordIndex = noOfRecords;
      				logger.info("startRecordIndex .................... "+startRecordIndex);
      				logger.info("endRecordIndex .................... "+endRecordIndex);
      			}
      			
      			bufInsSql.append(" limit "+startRecordIndex+","+noOfRecords);
      			//query = query +" limit "+startRecordIndex+","+endRecordIndex;
      			
      			logger.info("query : "+bufInsSql.toString());
                  logger.info("count"+count);
    	if(mainlist.size()<=0){
    		ChequeStatusVO chequeStatusVO1=new ChequeStatusVO();
    		chequeStatusVO1.setTotalRecordSize(count);
    		chequeStatusList.add(chequeStatusVO1);
    		
    		
    	}
    	else
    	{
	for(int i=0;i<mainlist.size();i++){

		subList=(ArrayList)mainlist.get(i);
		//logger.info("In chequeStatusbyloanViewer sublist..."+subList.size());
		
		if(subList.size()>0){
			
			    ChequeStatusVO chequeStatusVO1=new ChequeStatusVO();
			    chequeStatusVO1.setCheckBox("<input type='checkbox' name='chk' id='chk' value='"+(CommonFunction.checkNull(subList.get(13)).trim())+"' />");
			    
			    chequeStatusVO1.setInstrumentNo((CommonFunction.checkNull(subList.get(0)).trim()));
			    chequeStatusVO1.setDate((CommonFunction.checkNull(subList.get(1)).trim()));
			    Number instrumentAmount =myFormatter.parse((CommonFunction.checkNull(subList.get(2))).trim());
			    chequeStatusVO1.setInstrumentAmount(myFormatter.format(instrumentAmount));
			    
				chequeStatusVO1.setBank((CommonFunction.checkNull(subList.get(5)).trim()));
				chequeStatusVO1.setBranch((CommonFunction.checkNull(subList.get(7)).trim()));
				chequeStatusVO1.setBankAccount((CommonFunction.checkNull(subList.get(9)).trim()));
				chequeStatusVO1.setStatus((CommonFunction.checkNull(subList.get(10)).trim()));
				
				if((CommonFunction.checkNull(subList.get(10)).trim()).equalsIgnoreCase("A")){
					chequeStatusVO1.setStatusName("Approved");
				}
				
				if((CommonFunction.checkNull(subList.get(10)).trim()).equalsIgnoreCase("C")){
					chequeStatusVO1.setStatusName("Sent To Customer");
				}
				
				if((CommonFunction.checkNull(subList.get(10)).trim()).equalsIgnoreCase("S")){
					chequeStatusVO1.setStatusName("Stop Payment");
				}
				
				if((CommonFunction.checkNull(subList.get(10)).trim()).equalsIgnoreCase("D")){
					chequeStatusVO1.setStatusName("Deposit");
				}
				
				if((CommonFunction.checkNull(subList.get(10)).trim()).equalsIgnoreCase("R")){
					chequeStatusVO1.setStatusName("Realized");
				}
				
				if((CommonFunction.checkNull(subList.get(10)).trim()).equalsIgnoreCase("B")){
					chequeStatusVO1.setStatusName("Bounce");
				}
				
				if((CommonFunction.checkNull(subList.get(10)).trim()).equalsIgnoreCase("X")){
					chequeStatusVO1.setStatusName("Cancel");
				}
				
				chequeStatusVO1.setBusinessPartnerType((CommonFunction.checkNull(subList.get(11)).trim()));
				chequeStatusVO1.setBusinessPartnerDesc((CommonFunction.checkNull(subList.get(12)).trim()));

				 Number tdsAmount =myFormatter.parse((CommonFunction.checkNull(subList.get(14))).trim());
				  chequeStatusVO1.setTdsAmount(myFormatter.format(tdsAmount));
				
			
				chequeStatusVO1.setPdcInstrumentId((CommonFunction.checkNull(subList.get(15)).trim()));
				
				if(CommonFunction.checkNull(subList.get(16)).trim().equalsIgnoreCase("P")){
					chequeStatusVO1.setInstrumentType("Payable");
				}else if(CommonFunction.checkNull(subList.get(16)).trim().equalsIgnoreCase("R")){
					chequeStatusVO1.setInstrumentType("Receivables");
				}
				//chequeStatusVO1.setInstrumentType((CommonFunction.checkNull(subList.get(16)).trim()));
				
				chequeStatusVO1.setReasonLov("<input type='text' class='text' name='reason' id='reason"+i+"' value='"+(CommonFunction.checkNull(subList.get(23)).trim())+"' /> <input type='button' class='lovbutton' onclick=\"openLOVCommon(70,\'sourcingForm\',\'reason"+i+"\',\'\',\'\', \'\',\'\',\'\',\'lbxReasonHID"+i+"\')\"/> <input type='hidden' name='lbxReasonHID1' id='lbxReasonHID' value='"+(CommonFunction.checkNull(subList.get(22)).trim())+"'/>  <input type='hidden' name='lbxReasonHID' id='lbxReasonHID"+i+"'/> <input type='hidden' name='instrumentNo1' id='instrumentNo1' value='"+(CommonFunction.checkNull(subList.get(0)).trim())+"' /> <input type='hidden' name='date1' id='date1' value='"+(CommonFunction.checkNull(subList.get(1)).trim())+"' /> <input type='hidden' name='instrumentAmount1' id='instrumentAmount1' value='"+myFormatter.format(instrumentAmount)+"' />  <input type='hidden' name='businessPartnerDesc1' id='businessPartnerDesc1' value='"+(CommonFunction.checkNull(subList.get(12)).trim())+"' />  <input type='hidden' name='businessPartnerType1' id='businessPartnerType1' value='"+(CommonFunction.checkNull(subList.get(11)).trim())+"' /> <input type='hidden' name='bank1' id='bank1' value='"+(CommonFunction.checkNull(subList.get(5)).trim())+"' /> <input type='hidden' name='branch1' id='branch1' value='"+(CommonFunction.checkNull(subList.get(7)).trim())+"' /> <input type='hidden' name='status1' id='status1' value='"+(CommonFunction.checkNull(subList.get(10)).trim())+"' /> <input type='hidden' name='tdsAmount1' id='tdsAmount1' value='"+myFormatter.format(tdsAmount)+"' />  <input type='hidden' name='pdcInstrumentId' id='pdcInstrumentId' value='"+(CommonFunction.checkNull(subList.get(15)).trim())+"' /> <input type='hidden' name='lbxBPTypeHID1' id='lbxBPTypeHID1' value='"+(CommonFunction.checkNull(subList.get(3)).trim())+"' /> <input type='hidden' name='lbxBPNID1' id='lbxBPNID1' value='"+(CommonFunction.checkNull(subList.get(4)).trim())+"' /> <input type='hidden' name='lbxBankID1' id='lbxBankID1' value='"+(CommonFunction.checkNull(subList.get(6)).trim())+"' /> <input type='hidden' name='lbxBranchID1' id='lbxBranchID1' value='"+(CommonFunction.checkNull(subList.get(8)).trim())+"' />  <input type='hidden' name='depositBankId' id='depositBankId' value='"+(CommonFunction.checkNull(subList.get(17)).trim())+"' /> <input type='hidden' name='depositBranchId' id='depositBranchId' value='"+(CommonFunction.checkNull(subList.get(18)).trim())+"' /> <input type='hidden' name='depositMicrCode' id='depositMicrCode' value='"+(CommonFunction.checkNull(subList.get(19)).trim())+"' /> <input type='hidden' name='depositIfscCode' id='depositIfscCode' value='"+(CommonFunction.checkNull(subList.get(20)).trim())+"' />  <input type='hidden' name='depositBankAccount' id='depositBankAccount' value='"+(CommonFunction.checkNull(subList.get(21)).trim())+"' />");  
				
				chequeStatusVO1.setLoanAccNo((CommonFunction.checkNull(subList.get(24)).trim()));
				
				//Hidden fields--
//				chequeStatusVO1.setHiddenInstrumentNo("<input type='text' name='instrumentNo1' id='instrumentNo1' value='"+(CommonFunction.checkNull(subList.get(0)).trim())+"' />");
//				chequeStatusVO1.setHiddenDate("<input type='text' name='date1' id='date1' value='"+(CommonFunction.checkNull(subList.get(1)).trim())+"' />");
//				chequeStatusVO1.setHiddenInstrumentAmount("<input type='text' name='instrumentAmount1' id='instrumentAmount1' value='"+myFormatter.format(instrumentAmount)+"' />");
//				chequeStatusVO1.setHiddenBusParDesc("<input type='text' name='businessPartnerDesc1' id='businessPartnerDesc1' value='"+(CommonFunction.checkNull(subList.get(12)).trim())+"' />");
//				chequeStatusVO1.setHiddenBusParType("<input type='text' name='businessPartnerType1' id='businessPartnerType1' value='"+(CommonFunction.checkNull(subList.get(11)).trim())+"' />");
//				chequeStatusVO1.setHiddenBank("<input type='text' name='bank1' id='bank1' value='"+(CommonFunction.checkNull(subList.get(5)).trim())+"' />");
//				chequeStatusVO1.setHiddenBranch("<input type='text' name='branch1' id='branch1' value='"+(CommonFunction.checkNull(subList.get(7)).trim())+"' />");
//				chequeStatusVO1.setHiddenStatus("<input type='text' name='status1' id='status1' value='"+(CommonFunction.checkNull(subList.get(10)).trim())+"' />");
//				chequeStatusVO1.setHiddenTDS("<input type='text' name='tdsAmount1' id='tdsAmount1' value='"+myFormatter.format(tdsAmount)+"' />");
//				chequeStatusVO1.setHiddenPDCInstrumentID("<input type='text' name='pdcInstrumentId' id='pdcInstrumentId' value='"+(CommonFunction.checkNull(subList.get(15)).trim())+"' />");
//				chequeStatusVO1.setLbxBPTypeHID("<input type='text' name='lbxBPTypeHID1' id='lbxBPTypeHID1' value='"+(CommonFunction.checkNull(subList.get(3)).trim())+"' />");
//				chequeStatusVO1.setLbxBPNID("<input type='text' name='lbxBPNID1' id='lbxBPNID1' value='"+(CommonFunction.checkNull(subList.get(4)).trim())+"' />");
//				chequeStatusVO1.setLbxBankID("<input type='text' name='lbxBankID1' id='lbxBankID1' value='"+(CommonFunction.checkNull(subList.get(6)).trim())+"' />");
//				chequeStatusVO1.setLbxBranchID("<input type='text' name='lbxBranchID1' id='lbxBranchID1' value='"+(CommonFunction.checkNull(subList.get(8)).trim())+"' />");
//						
				
				chequeStatusVO1.setTotalRecordSize(count);
				chequeStatusVO1.setNoOfRecords(noOfRecords);
				chequeStatusList.add(chequeStatusVO1);
		}
}
	}
    	sbAppendToSQLCount= null;
        bufInsSql= null;
        bufInsSqlTempCount= null;
	}catch(Exception e){
		e.printStackTrace();
	}
	finally
	{
		businessPartnerType = null;
		businessPartnerName = null;
		pdcFlag = null ;
		customerName = null;
		paymentMode = null;
		lbxBPNID = null;
		chequeStatus = null;
		lbxBPTypeHID = null;
		loanAccNo = null;
		instrumentType = null;
		lbxLoanNoHID = null;
		instrumentNo = null;
	    mainlist.clear();
		subList.clear();
		mainlist= null;
		subList= null;
		chequeStatusVO=null;
	}

	return chequeStatusList;
 
}




//public String loanViewerClosureCheck(String loanIDMain)
//{
//	String retStr="";
//	String query = "select count(loan_id) from cr_termination_dtl where loan_id = '"+StringEscapeUtils.escapeSql(loanIDMain)+"' and termination_type='T'";
//	int count = Integer.parseInt(CommonFunction.checkNull(ConnectionDAO.singleReturn(query)));
//	if(count>0)
//		retStr = "T"+loanIDMain;
//	else
//		retStr="F"+"false";
//	
//	logger.info("retStr: "+retStr);
//	return retStr;
//}

	public boolean updateLoginStatus(String userId) {
		StringBuilder query = new StringBuilder(); 
		boolean status = false;
		try {
			
			ArrayList queryList = new ArrayList();
			logger.info("In updateLoginStatus %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% "+userId);
			query.append("UPDATE sec_user_log set	LOGIN_STATUS='N' WHERE USER_ID<>'"+userId+"'");
			logger.info("query : "+query);
			queryList.add(query.toString());
			logger.info("In updateLoginStatus" + query.toString());
			status = ConnectionDAO.sqlInsUpdDelete(queryList);
			logger.info("In updateLoginStatus: " + status);
			queryList.clear();
			queryList= null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			query = null;
			userId =null;
		}
	
		return status;
	
	}

	
	
	 
public ArrayList<RepayScheduleVo> getOldRepayScheduleDisbursal(String loanId) 
	{
		logger.info("In getRepaySched: ");
		ArrayList<RepayScheduleVo> list = new ArrayList<RepayScheduleVo>();
		ArrayList mainlist = new ArrayList();
		ArrayList subList = new ArrayList();
		StringBuilder query= new StringBuilder();
		RepayScheduleVo repvo = null;
		try 
		{
			query.append("select INSTL_NO,DATE_FORMAT(INSTL_DATE,'"+dateFormat+"'),INSTL_AMOUNT,PRIN_COMP,INT_COMP,EXCESS_INT, " +
						" if(ADV_FLAG='Y','YES','NO'),PRIN_OS,OTHER_CHARGES from cr_repaysch_dtl_hst where LOAN_ID="+loanId+" and  ifnull(REC_STATUS,'A') <>'X'");
			logger.info("Query in getOldRepayScheduleDisbursal-----" + query.toString());
			mainlist = ConnectionDAO.sqlSelect(query.toString());
			int size = mainlist.size();
			for (int i = 0; i <size; i++) 
			{
				subList = (ArrayList) mainlist.get(i);
				repvo = new RepayScheduleVo();
				if (subList.size() > 0) 
				{
					repvo.setInstNo((CommonFunction.checkNull(subList.get(0)).trim()));
					repvo.setDueDate((CommonFunction.checkNull(subList.get(1)).trim()));
					if(!CommonFunction.checkNull(subList.get(2)).trim().equalsIgnoreCase(""))
		    	    {
			    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(2))).trim());  
			    		repvo.setInstAmount(myFormatter.format(reconNum));
		    	    }
					if(!CommonFunction.checkNull(subList.get(3)).trim().equalsIgnoreCase(""))
		    	    {
			    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(3))).trim());  
			    		repvo.setPrinciple(myFormatter.format(reconNum));
		    	    }
					if(!CommonFunction.checkNull(subList.get(4)).trim().equalsIgnoreCase(""))
		    	    {
			    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(4))).trim());  
			    		repvo.setInstCom(myFormatter.format(reconNum));
		    	    }
					if(!CommonFunction.checkNull(subList.get(5)).trim().equalsIgnoreCase(""))
		    	    {
			    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(5))).trim());  
			    		repvo.setExcess(myFormatter.format(reconNum));
		    	    }
					
					
					repvo.setAdvFlag((CommonFunction.checkNull(subList.get(6)).trim()));
					if(!CommonFunction.checkNull(subList.get(7)).trim().equalsIgnoreCase(""))
		    	    {
			    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(7))).trim());  
			    		repvo.setPrinOS(myFormatter.format(reconNum));
		    	    }
					if(!CommonFunction.checkNull(subList.get(8)).trim().equalsIgnoreCase(""))
		    	    {
			    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(8))).trim());  
			    		repvo.setOtherCharges(myFormatter.format(reconNum));
		    	    }
					else
					{
						repvo.setOtherCharges("0.00");
					}
				}
				list.add(repvo);
			}
		} 
		catch(Exception e) 
		{e.printStackTrace();}
		finally
		{
			mainlist = null;
			subList = null;
			query= null;
			repvo = null;
			loanId = null;
		}
		return list;
	}

public ArrayList<InstructionCapMakerVO> getretriveCutInsValues(int lbxLoanNoHID) 
{
	ArrayList<InstructionCapMakerVO> loanDetailList=new ArrayList<InstructionCapMakerVO>();
	StringBuilder query = new StringBuilder(); 
	logger.info("In getretriveCutInsValues: "+lbxLoanNoHID);
		try{

	  ArrayList mainlist=new ArrayList();
	  ArrayList subList=new ArrayList();
//	  query.append("SELECT distinct D.COUNT,C.INSTL_AMOUNT,B.CUSTOMER_NAME,A.LOAN_ID,A.LOAN_NO,e.pdc_instrument_mode,e.pdc_remarks,e.MAKER_REMARKS,A.LOAN_INSTALLMENT_TYPE,SUM(C.OTHER_CHARGES),(IFNULL(SUM(C.OTHER_CHARGES),0)+C.INSTL_AMOUNT) FROM cr_pdc_instrument_dtl e,CR_LOAN_DTL A,GCD_CUSTOMER_M B,CR_REPAYSCH_DTL C," +
//	         " (SELECT LOAN_ID,count(1) COUNT FROM CR_REPAYSCH_DTL R where R.LOAN_ID='"+(CommonFunction.checkNull(lbxLoanNoHID).trim())+"' group by LOAN_ID) D " +
//	         " WHERE A.LOAN_CUSTOMER_ID=B.CUSTOMER_ID and a.loan_id = e.pdc_loan_id and A.LOAN_ID=C.LOAN_ID AND A.LOAN_ID=D.LOAN_ID and a.loan_id='"+(CommonFunction.checkNull(lbxLoanNoHID).trim())+"'");
	  
	  query.append("SELECT distinct IFNULL(D.COUNT,0),IFNULL(C.INSTL_AMOUNT,0),B.CUSTOMER_NAME,A.LOAN_ID,A.LOAN_NO,e.pdc_instrument_mode,e.pdc_remarks,e.MAKER_REMARKS,A.LOAN_INSTALLMENT_TYPE,SUM(C.OTHER_CHARGES),(IFNULL(SUM(C.OTHER_CHARGES),0)+C.INSTL_AMOUNT) "+
			  		"FROM cr_pdc_instrument_dtl e inner join CR_LOAN_DTL A on A.loan_id = e.pdc_loan_id inner join GCD_CUSTOMER_M B on A.LOAN_CUSTOMER_ID=B.CUSTOMER_ID left join CR_REPAYSCH_DTL C on A.LOAN_ID=C.LOAN_ID "+
			  		"left join (SELECT LOAN_ID,count(1) COUNT FROM CR_REPAYSCH_DTL R where R.LOAN_ID='"+(CommonFunction.checkNull(lbxLoanNoHID).trim())+"' group by LOAN_ID) D  on A.loan_id = D.LOAN_ID WHERE C.REC_TYPE NOT IN ('D','P') AND a.loan_id='"+(CommonFunction.checkNull(lbxLoanNoHID).trim())+"'");
	  
		logger.info("In getretriveCutInsValues QUERY  :  "+query.toString());
		mainlist=ConnectionDAO.sqlSelect(query.toString());
		logger.info("In getretriveCutInsValues list,,,,,"+mainlist.size());
			
			for(int i=0;i<mainlist.size();i++){
				
				subList=(ArrayList)mainlist.get(i);
				logger.info("In getListOfValues sublist..."+subList.size());
				
				if(subList.size()>0){
					
						InstructionCapMakerVO loanvo=new InstructionCapMakerVO();
					    loanvo.setTotalInstallments((CommonFunction.checkNull(subList.get(0)).trim()));
					    String val=CommonFunction.checkNull(subList.get(1)).trim();
					    if(val.equalsIgnoreCase(""))
					    	val="0";
					    Number installmentAmount =myFormatter.parse(val);
					    loanvo.setInstallmentAmount(myFormatter.format(installmentAmount));

					    
					    
						//loanvo.setInstallmentAmount((CommonFunction.checkNull(subList.get(1)).trim()));
						loanvo.setCustomerName((CommonFunction.checkNull(subList.get(2)).trim()));
						loanvo.setLbxLoanNoHID((CommonFunction.checkNull(subList.get(3)).trim()));
						loanvo.setLoanAccNo((CommonFunction.checkNull(subList.get(4)).trim()));
						loanvo.setInstrumentType((CommonFunction.checkNull(subList.get(5)).trim()));
						loanvo.setAuthorRemarks((CommonFunction.checkNull(subList.get(6)).trim()));
						loanvo.setRemarks((CommonFunction.checkNull(subList.get(7)).trim()));
						
						 if(CommonFunction.checkNull(subList.get(8)).trim().equalsIgnoreCase("E"))
						    {
							 loanvo.setInstallmentType("Eq. INSTALLMENT");
						    }
						    else if(CommonFunction.checkNull(subList.get(8)).trim().equalsIgnoreCase("G"))
						    {
						    	loanvo.setInstallmentType("Gr. INSTALLMENT");
						    }
						    else if(CommonFunction.checkNull(subList.get(8)).trim().equalsIgnoreCase("P"))
						    {
						    	loanvo.setInstallmentType("Eq. PRINCIPAL");
						    }
						    else if(CommonFunction.checkNull(subList.get(8)).trim().equalsIgnoreCase("Q"))
						    {
						    	loanvo.setInstallmentType("Gr. PRINCIPAL1");
						    }
						    else if(CommonFunction.checkNull(subList.get(8)).trim().equalsIgnoreCase("R"))
						    {
						    	loanvo.setInstallmentType("Gr. PRINCIPAL2");
						    }
						 
						  if(CommonFunction.checkNull(subList.get(9)).trim().equalsIgnoreCase(""))
						  {
							  loanvo.setOtherInstallmentCharges("0.00");
						  }
						  else
				    	    {
					    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(9))).trim());  
					    		loanvo.setOtherInstallmentCharges(myFormatter.format(reconNum));
				    	    }
						  
						  if(CommonFunction.checkNull(subList.get(10)).trim().equalsIgnoreCase(""))
						  {
							  loanvo.setTotalChargeInstallmentAmount("0.00");
						  }
						  else
				    	    {
					    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(10))).trim());  
					    		loanvo.setTotalChargeInstallmentAmount(myFormatter.format(reconNum));
				    	    }
						  
						loanDetailList.add(loanvo);
				}
			}
			mainlist.clear();
			mainlist=null;
			subList.clear();
			subList=null;
		}catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			query = null;
		}
	return loanDetailList;
}







public boolean checkExistRecord(String[] instrumentIDList,String[] checkedstatusList, String status) 
{
	boolean recordStatus=false;
	String nextStatus="";
	if(status.equalsIgnoreCase("STC"))
		nextStatus="C";
	else if(status.equalsIgnoreCase("RBP"))
		nextStatus="R";	
	else if(status.equalsIgnoreCase("STP"))
		nextStatus="S";	
	else if(status.equalsIgnoreCase("CBP"))
		nextStatus="X";
	else if(status.equalsIgnoreCase("BBR"))
		nextStatus="B";
	else if(status.equalsIgnoreCase("DBR"))
		nextStatus="D";
	String currentStatus=checkedstatusList[0];
	String query="";
	int count=0;
	for(int i=0;i<instrumentIDList.length;i++)
	{
		query="select count(1) from cr_instrument_update_dtl where INSTRUMENT_ID='"+instrumentIDList[i]+"' and CURRENT_STATUS='"+currentStatus+"' and NEXT_STATUS='"+nextStatus+"' and REC_STATUS in('A','R') and INSTRUMENT_BATCH_ID in(select max(INSTRUMENT_BATCH_ID) from cr_instrument_update_dtl where INSTRUMENT_ID='"+instrumentIDList[i]+"')";
		try
		{
			logger.info("Query to check existing recored  :  "+query);
			count=Integer.parseInt(ConnectionDAO.singleReturn(query));
			if(count>0)
			{
				recordStatus=true;
				break;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	query = null;
	return recordStatus;
}

//Prashant
public String getProductTypeInCM(String loanId)
{
	logger.info("getProductTypeInCM");
	StringBuilder query=new StringBuilder();
	query.append("select LOAN_PRODUCT_CATEGORY from cr_loan_dtl where LOAN_ID='"+loanId.trim()+"'");
	return  (String)ConnectionDAO.singleReturn(query.toString());
			
}

//Nishant space starts
public String getLoanAmount(String loanId)
{
	StringBuilder query=new StringBuilder();
	query.append("select a.DEAL_LOAN_AMOUNT from cr_loan_dtl L left join cr_deal_loan_dtl a on a.DEAL_ID=L.LOAN_DEAL_ID and l.loan_deal_loan_id=a.deal_loan_id where L.LOAN_ID='"+loanId.trim()+"'");
	return  (String)ConnectionDAO.singleReturn(query.toString());
			
}
//Nishant space end

//start by sachin


public ArrayList<DocumentsVo> getApplicationDocumentsForDeal(String entityType,String commonId,String txnType,String recStatus) {
	ArrayList<DocumentsVo> list=new ArrayList<DocumentsVo>();
	StringBuilder query = new StringBuilder(); 
	try{
	String deaId = ConnectionDAO.singleReturn("select LOAN_DEAL_ID from cr_loan_dtl where LOAN_ID='"+commonId+"'");		
	query.append(" SELECT TXN_DOC_ID,DOC_ID,DOC_DESC,DATE_FORMAT(DOC_RECEIVED_DATE,'"+dateFormat+"'),"+ 
				 "	DATE_FORMAT(DOC_DEFFRED_DATE,'"+dateFormat+"'),DATE_FORMAT(DOC_EXPIRY_DATE,'"+dateFormat+"'), "+
				 "	DOC_MANDATORY,DOC_ORIGINAL,DOC_STATUS,DOC_STATUS_PREV,DOC_REMARKS,Doc_Expiry_Flag,DOC_CHILD_FLAG,DOC_CHILD_IDS,ENTITY_ID,d.DOCUMENT_TYPE "+
				 "	from cr_document_dtl d "+
				 "	where DOC_TYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(entityType)).trim()+"' " +
				 " and TXNID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(deaId)).trim()+" " +
				 " and TXN_TYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(txnType)).trim()+"'  and DOC_ID IS NOT NULL " );
//				 " and STAGE_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(stage)).trim()+"'" +
	logger.info("getDocumentsDetai Query4: "+query.toString());
	DocumentsVo docsVo = null;
	ArrayList docsdeatail = ConnectionDAO.sqlSelect(query.toString());
	logger.info("getDocumentsDetail OUTER ARRAYLIST SIZE: "+docsdeatail.size());
	for(int i=0;i<docsdeatail.size();i++){

		//logger.info("getDocumentsDetail"+docsdeatail.get(i).toString());
		ArrayList docsdeatail1=(ArrayList)docsdeatail.get(i);
		if(docsdeatail1.size()>0)
		{
			logger.info("getDocumentsDetail INNNER ARRAYLIST SIZE: "+docsdeatail1.size());
			docsVo = new DocumentsVo();
			docsVo.setDocId((CommonFunction.checkNull(docsdeatail1.get(0))).trim());
			docsVo.setRealDocId((CommonFunction.checkNull(docsdeatail1.get(1))).trim());
			docsVo.setDocDesc((CommonFunction.checkNull(docsdeatail1.get(2))).trim());
			docsVo.setRecieveDate((CommonFunction.checkNull(docsdeatail1.get(3))).trim());
			logger.info("recieve date:"+CommonFunction.checkNull(docsdeatail1.get(3)));
			docsVo.setDeferDate((CommonFunction.checkNull(docsdeatail1.get(4))).trim());
			docsVo.setExpirDate((CommonFunction.checkNull(docsdeatail1.get(5))).trim());

			if((CommonFunction.checkNull(docsdeatail1.get(6)).trim()).equals("Y"))
			{
				docsVo.setMandatory("MANDATORY");
			}
			else if((CommonFunction.checkNull(docsdeatail1.get(6)).trim()).equals("N"))
			{
				docsVo.setMandatory("NON-MANDATORY");
			}

			if((CommonFunction.checkNull(docsdeatail1.get(7)).trim()).equals("Y"))
			{
				docsVo.setOriginal("ORIGINAL");
			}
			else if((CommonFunction.checkNull(docsdeatail1.get(7)).trim()).equals("N"))
			{
				docsVo.setOriginal("PHOTOCOPY");
			}

			docsVo.setDocStatus((CommonFunction.checkNull(docsdeatail1.get(8))).trim());
			docsVo.setDocStatusPrev((CommonFunction.checkNull(docsdeatail1.get(9))).trim());
			docsVo.setRemark((CommonFunction.checkNull(docsdeatail1.get(10))).trim());
			docsVo.setExpirFlag((CommonFunction.checkNull(docsdeatail1.get(11))).trim());
			docsVo.setDocChildFlag((CommonFunction.checkNull(docsdeatail1.get(12))).trim());
			docsVo.setDocChildId((CommonFunction.checkNull(docsdeatail1.get(13))).trim());
			docsVo.setEntityId((CommonFunction.checkNull(docsdeatail1.get(14))).trim());
			docsVo.setvDocumentType((CommonFunction.checkNull(docsdeatail1.get(15))).trim());			
			list.add(docsVo);
		}
	}
		deaId=null;
	}catch(Exception e){
		e.printStackTrace();
	}
	finally
	{
		query = null;
		entityType = null;
		commonId = null;
		txnType = null;
		recStatus = null;
	}
	return list;
}


public ArrayList getAdditionalDocsForDeal(String entityType, String commonId, String txnType, String recStatus)
{
	ArrayList<Object> list=new ArrayList<Object>();
	try{
		String deaId = ConnectionDAO.singleReturn("select LOAN_DEAL_ID from cr_loan_dtl where LOAN_ID='"+commonId+"'");	
		StringBuilder query=new StringBuilder();
		
		query.append(" SELECT DISTINCT DOC_DESC,DATE_FORMAT(DOC_RECEIVED_DATE,'"+dateFormat+"'),"+ 
			 "	DATE_FORMAT(DOC_DEFFRED_DATE,'"+dateFormat+"'),DATE_FORMAT(DOC_EXPIRY_DATE,'"+dateFormat+"'), "+ 
			 "	DOC_MANDATORY,DOC_ORIGINAL,DOC_STATUS,DOC_REMARKS,d.DOCUMENT_TYPE"+ 
			 "	from cr_document_dtl d"+ 
			 "	where DOC_TYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(entityType)).trim()+"' " +
			 "  and TXNID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(deaId)).trim()+" " +
			 "  and TXN_TYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(txnType)).trim()+"' and DOC_ID IS NULL ");
//			 "  and STAGE_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(stage)).trim()+"' " +   
	
	logger.info("getAdditionalDocs Query: "+query.toString());
	
	DocumentsVo docsVo = null;
	ArrayList docsdeatail = ConnectionDAO.sqlSelect(query.toString());
	logger.info("getAdditionalDocs OUTER ARRAYLIST SIZE: "+docsdeatail.size());
	
	query=null;
	
	for(int i=0;i<docsdeatail.size();i++){

		//logger.info("getAdditionalDocs"+docsdeatail.get(i).toString());
		ArrayList docsdeatail1=(ArrayList)docsdeatail.get(i);
		//logger.info("getAdditionalDocs INNNER ARRAYLIST SIZE: "+docsdeatail1.size());
		if(docsdeatail1.size()>0)
		{
			docsVo = new DocumentsVo();
			docsVo.setDocNameAddn(CommonFunction.checkNull(docsdeatail1.get(0)).trim());
			docsVo.setAddnReceivedDate(CommonFunction.checkNull(docsdeatail1.get(1)).trim());
			docsVo.setAddnDeferredDate(CommonFunction.checkNull(docsdeatail1.get(2)).trim());
			docsVo.setAddnExpiryDate(CommonFunction.checkNull(docsdeatail1.get(3)).trim());
			docsVo.setMandateOrNonMandate(CommonFunction.checkNull(docsdeatail1.get(4)).trim());
			docsVo.setOrgOrCopy(CommonFunction.checkNull(docsdeatail1.get(5)).trim());
			docsVo.setAddnDocStatus(CommonFunction.checkNull(docsdeatail1.get(6)).trim());
			docsVo.setAddnRemarks(CommonFunction.checkNull(docsdeatail1.get(7)).trim());
			docsVo.setvDocumentType((CommonFunction.checkNull(docsdeatail1.get(8))).trim());			
			list.add(docsVo);
		}
	}
	deaId=null;
	}catch(Exception e){
		e.printStackTrace();
	}
	finally{
		entityType= null;
		commonId=null;
		txnType= null;
		recStatus =null;
	}
	return list;
}


@Override
public ArrayList<DocumentsVo> getAssetCollateralDocumentsForDeal(String entityType,String commonId, String txnType, 
		String recStatus) 
{
	ArrayList<DocumentsVo> list = new ArrayList<DocumentsVo>();
	StringBuilder query = new StringBuilder(); 
	try 	
	{
		String deaId = ConnectionDAO.singleReturn("select LOAN_DEAL_ID from cr_loan_dtl where LOAN_ID='"+commonId+"'");	
		//String query="";
		query.append(" SELECT DISTINCT TXN_DOC_ID,DOC_ID,DOC_DESC,dc.ASSETID,ASSET_COLLATERAL_CLASS," +
				" DATE_FORMAT(DOC_RECEIVED_DATE,'"+ dateFormat+ "'),DATE_FORMAT(DOC_DEFFRED_DATE,'"+ dateFormat+ "')," +
				" DATE_FORMAT(DOC_EXPIRY_DATE,'"+ dateFormat+ "'),DOC_MANDATORY,DOC_ORIGINAL,DOC_STATUS,DOC_STATUS_PREV,DOC_REMARKS," +
				" Doc_Expiry_Flag,DOC_CHILD_FLAG,DOC_CHILD_IDS,ENTITY_ID,d.DOCUMENT_TYPE from cr_document_dtl d, cr_deal_collateral_m dc," +
				" cr_asset_collateral_m ac" +
				" where DOC_TYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(entityType)).trim()+"' " +
				" and TXNID="+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(commonId)).trim()+ " " +
				" and TXN_TYPE='"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(txnType)).trim()+ "'" +
				" AND D.TXNID = dc.DEAL_ID" +
				" AND D.ENTITY_ID = dc.ASSETID AND dc.ASSETID=ac.ASSET_ID AND d.DOC_ID IS NOT NULL ");
		logger.info("getDocumentsDetai Query5: " + query.toString());
		DocumentsVo docsVo = null;
		ArrayList docsdeatail = ConnectionDAO.sqlSelect(query.toString());
		logger.info("getDocumentsDetail OUTER ARRAYLIST SIZE: "+ docsdeatail.size());
		for (int i = 0; i < docsdeatail.size(); i++) 
		{
			//logger.info("getDocumentsDetail"+ docsdeatail.get(i).toString());
			ArrayList docsdeatail1 = (ArrayList) docsdeatail.get(i);
			//logger.info("getDocumentsDetail INNNER ARRAYLIST SIZE: "+ docsdeatail1.size());
			if (docsdeatail1.size() > 0) 
			{
				docsVo = new DocumentsVo();
				docsVo.setDocId((CommonFunction.checkNull(docsdeatail1.get(0))).trim());
				docsVo.setRealDocId((CommonFunction.checkNull(docsdeatail1.get(1))).trim());
				docsVo.setDocDesc((CommonFunction.checkNull(docsdeatail1.get(2))).trim());
				docsVo.setType((CommonFunction.checkNull(docsdeatail1.get(4))).trim());
				docsVo.setRecieveDate((CommonFunction.checkNull(docsdeatail1.get(5))).trim());
				docsVo.setDeferDate((CommonFunction.checkNull(docsdeatail1.get(6))).trim());
				docsVo.setExpirDate((CommonFunction.checkNull(docsdeatail1.get(7))).trim());

				if ((CommonFunction.checkNull(docsdeatail1.get(8)).trim()).equals("Y")) {
					docsVo.setMandatory("MANDATORY");
				} 
				else if ((CommonFunction.checkNull(docsdeatail1.get(8)).trim()).equals("N")) {
					docsVo.setMandatory("NON-MANDATORY");
				}

				if ((CommonFunction.checkNull(docsdeatail1.get(9)).trim()).equals("Y")) {
					docsVo.setOriginal("ORIGINAL");
				} 
				else if ((CommonFunction.checkNull(docsdeatail1.get(9)).trim()).equals("N")) {
					docsVo.setOriginal("PHOTOCOPY");
				}

				docsVo.setDocStatus((CommonFunction.checkNull(docsdeatail1.get(10))).trim());
				docsVo.setDocStatusPrev((CommonFunction.checkNull(docsdeatail1.get(11))).trim());
				docsVo.setRemark((CommonFunction.checkNull(docsdeatail1.get(12))).trim());
				docsVo.setExpirFlag((CommonFunction.checkNull(docsdeatail1.get(13))).trim());
				docsVo.setDocChildFlag((CommonFunction.checkNull(docsdeatail1.get(14))).trim());
				docsVo.setDocChildId((CommonFunction.checkNull(docsdeatail1.get(15))).trim());
				docsVo.setEntityId((CommonFunction.checkNull(docsdeatail1.get(16))).trim());
				docsVo.setvDocumentType((CommonFunction.checkNull(docsdeatail1.get(17))).trim());				
				list.add(docsVo);
			}
		}
		deaId=null;
		docsdeatail.clear();
		docsdeatail=null;
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally
	{
		query = null;
		entityType = null;
		commonId = null;
		txnType = null;
		recStatus = null;
	}
	return list;
}



@Override
public ArrayList<DocumentsVo> getAllApplicantDocsForDeal(String entityType,String commonId, String txnType,String recStatus) {
	ArrayList<DocumentsVo> list=new ArrayList<DocumentsVo>();
	StringBuilder query = new StringBuilder();
	try{
		//String query="";
		
			String deaId = ConnectionDAO.singleReturn("select LOAN_DEAL_ID from cr_loan_dtl where LOAN_ID='"+commonId+"'");	
			query.append(" SELECT DISTINCT TXN_DOC_ID,DOC_ID,DOC_DESC,g.CUSTOMER_NAME, DATE_FORMAT(DOC_RECEIVED_DATE,'"+dateFormat+"'),"+ 
			
			 " DATE_FORMAT(DOC_DEFFRED_DATE,'"+dateFormat+"'),DATE_FORMAT(DOC_EXPIRY_DATE,'"+dateFormat+"'), "+ 
			 " DOC_MANDATORY,DOC_ORIGINAL,DOC_STATUS,DOC_STATUS_PREV,DOC_REMARKS,Doc_Expiry_Flag,DOC_CHILD_FLAG,DOC_CHILD_IDS,ENTITY_ID,d.DOCUMENT_TYPE "+ 
			 " from cr_document_dtl d JOIN cr_deal_customer_m g ON(D.ENTITY_ID=G.CUSTOMER_ID) "+ 
			 " where DOC_TYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(entityType)).trim()+"' " +
			 " and TXNID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(deaId)).trim()+" " +
			 " and TXN_TYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(txnType)).trim()+"' and DOC_ID IS NOT NULL" );



	logger.info("getDocumentsDetai Query6: "+query.toString());
	
	DocumentsVo docsVo = null;
	ArrayList docsdeatail = ConnectionDAO.sqlSelect(query.toString());
	logger.info("getDocumentsDetail OUTER ARRAYLIST SIZE: "+docsdeatail.size());
	for(int i=0;i<docsdeatail.size();i++){

		//logger.info("getDocumentsDetail"+docsdeatail.get(i).toString());
		ArrayList docsdeatail1=(ArrayList)docsdeatail.get(i);
		//logger.info("getDocumentsDetail INNNER ARRAYLIST SIZE: "+docsdeatail1.size());
		if(docsdeatail1.size()>0)
		{
			docsVo = new DocumentsVo();
			docsVo.setDocId((CommonFunction.checkNull(docsdeatail1.get(0))).trim());
			docsVo.setRealDocId((CommonFunction.checkNull(docsdeatail1.get(1))).trim());
			docsVo.setDocDesc((CommonFunction.checkNull(docsdeatail1.get(2))).trim());
			docsVo.setApplName((CommonFunction.checkNull(docsdeatail1.get(3))).trim());
			docsVo.setRecieveDate((CommonFunction.checkNull(docsdeatail1.get(4))).trim());
			docsVo.setDeferDate((CommonFunction.checkNull(docsdeatail1.get(5))).trim());
			docsVo.setExpirDate((CommonFunction.checkNull(docsdeatail1.get(6))).trim());

			if((CommonFunction.checkNull(docsdeatail1.get(7)).trim()).equals("Y"))
			{
				docsVo.setMandatory("MANDATORY");
			}
			else 	if((CommonFunction.checkNull(docsdeatail1.get(7)).trim()).equals("N"))
			{
				docsVo.setMandatory("NON-MANDATORY");
			}

			if((CommonFunction.checkNull(docsdeatail1.get(8)).trim()).equals("Y"))
			{
				docsVo.setOriginal("ORIGINAL");
			}
			else if((CommonFunction.checkNull(docsdeatail1.get(8)).trim()).equals("N"))
			{
				docsVo.setOriginal("PHOTOCOPY");
			}

			
			docsVo.setDocStatus((CommonFunction.checkNull(docsdeatail1.get(9))).trim());
			docsVo.setDocStatusPrev((CommonFunction.checkNull(docsdeatail1.get(10))).trim());
			docsVo.setRemark((CommonFunction.checkNull(docsdeatail1.get(11))).trim());
			docsVo.setExpirFlag((CommonFunction.checkNull(docsdeatail1.get(12))).trim());
			docsVo.setDocChildFlag((CommonFunction.checkNull(docsdeatail1.get(13))).trim());
			docsVo.setDocChildId((CommonFunction.checkNull(docsdeatail1.get(14))).trim());
			docsVo.setEntityId((CommonFunction.checkNull(docsdeatail1.get(15))).trim());
			docsVo.setvDocumentType((CommonFunction.checkNull(docsdeatail1.get(15))).trim());			
			list.add(docsVo);
		}
	}
	deaId=null;
	docsdeatail.clear();
	docsdeatail=null;
	}catch(Exception e){
		e.printStackTrace();
	}
	finally
	{
		query = null;
		entityType = null;
		commonId = null;
		txnType = null;
		recStatus = null;
	}
	return list;
}

@Override
public String saveDocInTemp(Object vo) {
	logger.info("in saveDocInTemp():::");
	DocumentsVo docVo = (DocumentsVo)vo;
	ArrayList<Object> in =new ArrayList<Object>();
	ArrayList<Object> out =new ArrayList<Object>();
	ArrayList outMessages = new ArrayList();
			
	String s1="";
	String s2="";
	String procval=null;
	try
	 {   
        in.add(CommonFunction.checkNull((docVo.getTxnId()).trim()));
	    in.add(CommonFunction.checkNull((docVo.getTxnType()).trim()));
        in.add(CommonFunction.checkNull((docVo.getStageId()).trim()));
        String date=CommonFunction.changeFormat(CommonFunction.checkNull(docVo.getBussinessDate()).trim());
        in.add(date);
        in.add((CommonFunction.checkNull(docVo.getUserId())));
	    out.add(s1.toString());
	    out.add(s2.toString());
	    logger.info("DOCUMENT_COLLECTION_MAKER ("+in.toString()+","+out.toString()+")"); 
	    outMessages=(ArrayList) ConnectionDAO.callSP("DOCUMENT_COLLECTION_MAKER",in,out);
	    s1=CommonFunction.checkNull(outMessages.get(0));
	    s2=CommonFunction.checkNull(outMessages.get(1));
        logger.info("DOCUMENT_COLLECTION_MAKER s1: "+s1); 
        logger.info("DOCUMENT_COLLECTION_MAKER s2: "+s2);
		logger.info("After proc call...."); 
		if(CommonFunction.checkNull(s1).equalsIgnoreCase("S"))
		{
			procval=s1;
		}
		else
		{
		    procval=s2;
		  }
	   }
	   catch (Exception e) 
	   {
			e.printStackTrace();
	   }
	   finally
	   {
		   in.clear();
		   in=null;
		   out.clear();
		   out=null;
		   s1=null;
		   s2=null;
		   docVo=null;
		   outMessages.clear();
		   outMessages=null;
		   
	   }
	   return procval;
}

public boolean forwardDocumentPOD(Object ob) {
	DocumentsVo vo = (DocumentsVo)ob;
	logger.info("In forwardDocumentPOD()...");

	ArrayList qryList=new ArrayList();
	boolean status=false;

		    PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
			StringBuffer bufInsSql =	new StringBuffer();
			try {
			bufInsSql.append("update cr_document_dtl_temp set ");
			bufInsSql.append(" REC_STATUS=?,MAKER_ID=?,");
			bufInsSql.append(" MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) " );
			bufInsSql.append(" where  rec_status ='P' and TXN_TYPE=? and TXNID=? and STAGE_ID=?  "); //query remarks
							
			
			insertPrepStmtObject.addString("F");
			
			if((CommonFunction.checkNull(vo.getUserId()).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getUserId()).trim()));
			
			if((CommonFunction.checkNull(vo.getBussinessDate()).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getBussinessDate()).trim())); //maker_date
			
			if((CommonFunction.checkNull(vo.getTxnType()).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getTxnType()).trim())); 
			
			if((CommonFunction.checkNull(vo.getTxnId()).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getTxnId()).trim())); 
			
			if((CommonFunction.checkNull(vo.getStageId()).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getStageId()).trim())); 
			
			
			insertPrepStmtObject.setSql(bufInsSql.toString());
			qryList.add(insertPrepStmtObject);

		status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			qryList.clear();
			qryList=null;
			vo=null;
			ob=null;
			insertPrepStmtObject=null;
			bufInsSql=null;
			
		}

	return status;
}



@Override
public String getDocFromTempOrNotAtDocCollection(String entityType, String commonId,String stage, String txnType) {
	
	logger.info("IN getDocFromTempOrNot():::::");
	String result=null;
	StringBuilder query = new StringBuilder(); 
	try{
		query.append("select count(1) from CR_DOCUMENT_DTL_TEMP ");
		query.append(" where DOC_TYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(entityType)).trim()+"' " );
		query.append(" and TXNID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(commonId)).trim()+" " );
		query.append(" and TXN_TYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(txnType)).trim()+"' " );
		query.append(" and STAGE_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(stage)).trim()+"' " );
		result=ConnectionDAO.singleReturn(query.toString());
		logger.info("In getDocFromTempOrNot()::::query::"+query.toString());
    }
  	catch(Exception e)
  	{
  		e.printStackTrace();
	}
  	finally
  	{
  		entityType=null;
  		commonId=null;
  		stage=null;
  		txnType=null;
  		query=null;
  	 }
  	return result;
}


@Override
public ArrayList<DocumentsVo> getDocumentsForAllApplicationDocAtDocCollection(
		String entityType, String commonId, String stage, String txnType,
		String recStatus, String source) 
	{
	logger.info("in getDocumentsForAllApplicationDocAtDocCollection():::::");
	DocumentsVo docsVo = null;
	ArrayList<DocumentsVo> list=new ArrayList<DocumentsVo>();
	StringBuilder query = new StringBuilder(); 
	try{			
	query.append(" SELECT TXN_DOC_ID,DOC_ID,DOC_DESC,");
	query.append("DATE_FORMAT(DOC_RECEIVED_DATE,'"+dateFormat+"'),"); 
	query.append("DATE_FORMAT(DOC_DEFFRED_DATE,'"+dateFormat+"'),");
	query.append("DATE_FORMAT(DOC_EXPIRY_DATE,'"+dateFormat+"'), "+
				 "	DOC_MANDATORY,DOC_ORIGINAL,DOC_STATUS,DOC_STATUS_PREV,DOC_REMARKS,Doc_Expiry_Flag,DOC_CHILD_FLAG,DOC_CHILD_IDS,ENTITY_ID,d.document_type "+
				 "	from "+source+" d "+
				 "	where DOC_TYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(entityType)).trim()+"' " +
				 " and TXNID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(commonId)).trim()+" " +
				 " and TXN_TYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(txnType)).trim()+"' " +
				 " AND d.DOC_ID IS NOT NULL ");
	if(!CommonFunction.checkNull(stage).trim().equalsIgnoreCase(""))
	{
	query.append("  and STAGE_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(stage)).trim()+"' ");
//	query.append(" and rec_status ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(recStatus)).trim()+"'");
	}
	if(!CommonFunction.checkNull(source).trim().equalsIgnoreCase("CR_DOCUMENT_DTL_TEMP"))
	{
	query.append("  and TXNID NOT IN(SELECT TXNID FROM CR_DOCUMENT_DTL_TEMP WHERE  STAGE_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(stage)).trim()+"' ");
	query.append("	and DOC_TYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(entityType)).trim()+"' " );
	query.append(" and TXNID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(commonId)).trim()+" " );
	query.append(" and TXN_TYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(txnType)).trim()+"' " );
	query.append("  and d.DOC_ID IS NOT NULL) ");
	}
	else
	{
	query.append(" and rec_status ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(recStatus)).trim()+"'");	
	}
	logger.info("getDocumentsForAllDocTypeAtDocCollection Queryl: "+query.toString());
	ArrayList docsdeatail = ConnectionDAO.sqlSelect(query.toString());
	int size=docsdeatail.size();
	for(int i=0;i<size;i++){

		ArrayList docsdeatail1=(ArrayList)docsdeatail.get(i);
		if(docsdeatail1.size()>0)
		{
			docsVo = new DocumentsVo();
			docsVo.setDocId((CommonFunction.checkNull(docsdeatail1.get(0))).trim());
			docsVo.setRealDocId((CommonFunction.checkNull(docsdeatail1.get(1))).trim());
			docsVo.setDocDesc((CommonFunction.checkNull(docsdeatail1.get(2))).trim());
			docsVo.setRecieveDate((CommonFunction.checkNull(docsdeatail1.get(3))).trim());
			docsVo.setDeferDate((CommonFunction.checkNull(docsdeatail1.get(4))).trim());
			docsVo.setExpirDate((CommonFunction.checkNull(docsdeatail1.get(5))).trim());

			if((CommonFunction.checkNull(docsdeatail1.get(6)).trim()).equals("Y"))
			{
				docsVo.setMandatory("MANDATORY");
			}
			else if((CommonFunction.checkNull(docsdeatail1.get(6)).trim()).equals("N"))
			{
				docsVo.setMandatory("NON-MANDATORY");
			}

			if((CommonFunction.checkNull(docsdeatail1.get(7)).trim()).equals("Y"))
			{
				docsVo.setOriginal("ORIGINAL");
			}
			else if((CommonFunction.checkNull(docsdeatail1.get(7)).trim()).equals("N"))
			{
				docsVo.setOriginal("PHOTOCOPY");
			}

			docsVo.setDocStatus((CommonFunction.checkNull(docsdeatail1.get(8))).trim());
			docsVo.setDocStatusPrev((CommonFunction.checkNull(docsdeatail1.get(9))).trim());
			docsVo.setRemark((CommonFunction.checkNull(docsdeatail1.get(10))).trim());
			docsVo.setExpirFlag((CommonFunction.checkNull(docsdeatail1.get(11))).trim());
			docsVo.setDocChildFlag((CommonFunction.checkNull(docsdeatail1.get(12))).trim());
			docsVo.setDocChildId((CommonFunction.checkNull(docsdeatail1.get(13))).trim());
			docsVo.setEntityId((CommonFunction.checkNull(docsdeatail1.get(14))).trim());
			docsVo.setvDocumentType((CommonFunction.checkNull(docsdeatail1.get(15))).trim());			
			list.add(docsVo);
		}
	}
	}catch(Exception e){
		e.printStackTrace();
	}
	finally
	{
		docsVo=null;
		query = null;
		entityType = null;
		commonId = null;
		stage = null;
		txnType = null;
		recStatus = null;
		source = null;
	}
	return list;

}


@Override
public ArrayList getAdditionalDocsForAllDocTypeAtDocCollection(
		String entityType, String commonId, String stage, String txnType,
		String recStatus,String source) {
	logger.info("getAdditionalDocsForAllDocTypeAtDocCollection:::::::::");
	DocumentsVo docsVo = null;
	ArrayList<Object> list=new ArrayList<Object>();
	try{
		StringBuilder query=new StringBuilder();
		
		query.append(" SELECT DISTINCT DOC_DESC,");
		query.append("DATE_FORMAT(DOC_RECEIVED_DATE,'"+dateFormat+"'),"); 
		query.append("DATE_FORMAT(DOC_DEFFRED_DATE,'"+dateFormat+"'),");
		query.append("DATE_FORMAT(DOC_EXPIRY_DATE,'"+dateFormat+"'), "+ 
			 "	DOC_MANDATORY,DOC_ORIGINAL,DOC_STATUS,DOC_REMARKS,document_type"+ 
			 "	from "+source+""+ 
			 "	where DOC_TYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(entityType)).trim()+"' " +
			 "  and TXNID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(commonId)).trim()+" " +
			 "  and TXN_TYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(txnType)).trim()+"' " +
			 "  and DOC_ID IS NULL "); 
	     if(!CommonFunction.checkNull(stage).trim().equalsIgnoreCase("")){
	    	 query.append("  and STAGE_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(stage)).trim()+"' ");
//			query.append(" and rec_status ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(recStatus)).trim()+"'");
	     }
	     if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("CR_DOCUMENT_DTL") || CommonFunction.checkNull(source).trim().equalsIgnoreCase("CR_DOCUMENT_DTL d"))
	 	{
	 	query.append("  and TXNID NOT IN(SELECT TXNID FROM CR_DOCUMENT_DTL_TEMP WHERE  STAGE_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(stage)).trim()+"' ");
	 	query.append("	and DOC_TYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(entityType)).trim()+"' " );
	 	query.append(" and TXNID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(commonId)).trim()+" " );
	 	query.append(" and TXN_TYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(txnType)).trim()+"' " );
	 	query.append(" and DOC_ID IS NULL) ");
	 	}
	 	else
		{
		query.append(" and rec_status ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(recStatus)).trim()+"'");	
		}
	
	logger.info("getAdditionalDocsForAllDocTypeAtDocCollection Query: "+query.toString());
	

	ArrayList docsdeatail = ConnectionDAO.sqlSelect(query.toString());
	
	query=null;
	int size=docsdeatail.size();
	for(int i=0;i<size;i++){

		ArrayList docsdeatail1=(ArrayList)docsdeatail.get(i);
		if(docsdeatail1.size()>0)
		{
			docsVo = new DocumentsVo();
			docsVo.setDocNameAddn(CommonFunction.checkNull(docsdeatail1.get(0)).trim());
			docsVo.setAddnReceivedDate(CommonFunction.checkNull(docsdeatail1.get(1)).trim());
			docsVo.setAddnDeferredDate(CommonFunction.checkNull(docsdeatail1.get(2)).trim());
			docsVo.setAddnExpiryDate(CommonFunction.checkNull(docsdeatail1.get(3)).trim());
			docsVo.setMandateOrNonMandate(CommonFunction.checkNull(docsdeatail1.get(4)).trim());
			docsVo.setOrgOrCopy(CommonFunction.checkNull(docsdeatail1.get(5)).trim());
			docsVo.setAddnDocStatus(CommonFunction.checkNull(docsdeatail1.get(6)).trim());
			docsVo.setAddnRemarks(CommonFunction.checkNull(docsdeatail1.get(7)).trim());
			docsVo.setvDocumentType((CommonFunction.checkNull(docsdeatail1.get(8))).trim());			
			list.add(docsVo);
		}
		docsdeatail1=null;
	}
	}catch(Exception e){
		e.printStackTrace();
	}
	finally{
		docsVo=null;
		entityType = null;
		commonId = null;
		stage = null;
		txnType = null;
		recStatus = null;
		source=null;
	}
	return list;
}


@Override
public ArrayList<DocumentsVo> getAllApplicantDocsForTempAtDocCollection(String entityType,
		String commonId, String stage, String txnType, String recStatus,
		String source) {
	logger.info("in getAllApplicantDocsForTempAtDocCollection()::::");
	DocumentsVo docsVo = null;
	ArrayList<DocumentsVo> list=new ArrayList<DocumentsVo>();
	StringBuilder query = new StringBuilder();
	try{
		if(!stage.equalsIgnoreCase("PRS"))
		{
			query.append(" SELECT DISTINCT TXN_DOC_ID,DOC_ID,DOC_DESC,g.CUSTOMER_NAME, ");
			query.append("DATE_FORMAT(DOC_RECEIVED_DATE,'"+dateFormat+"'),"); 
			query.append("DATE_FORMAT(DOC_DEFFRED_DATE,'"+dateFormat+"'),");
			query.append("DATE_FORMAT(DOC_EXPIRY_DATE,'"+dateFormat+"'), "+ 
			 " DOC_MANDATORY,DOC_ORIGINAL,DOC_STATUS,DOC_STATUS_PREV,DOC_REMARKS,Doc_Expiry_Flag,DOC_CHILD_FLAG,DOC_CHILD_IDS,ENTITY_ID,document_type "+ 
			 " from "+source+", gcd_customer_m g, cr_loan_dtl L "+ 
			 " where DOC_TYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(entityType)).trim()+"' " +
			 " and TXNID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(commonId)).trim()+" " +
			 " and TXN_TYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(txnType)).trim()+"' " +
			 " AND D.TXNID = L.LOAN_ID  AND D.ENTITY_ID=g.CUSTOMER_ID AND d.DOC_ID IS NOT NULL" );
		     if(!CommonFunction.checkNull(stage).trim().equalsIgnoreCase(""))
		     {
		    	 query.append("  and STAGE_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(stage)).trim()+"' ");
	//			query.append(" and d.rec_status ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(recStatus)).trim()+"'");
		     }
		     if(!CommonFunction.checkNull(source).trim().equalsIgnoreCase("CR_DOCUMENT_DTL_TEMP d"))
			 	{
			 	query.append("  and d.TXNID NOT IN(SELECT TXNID FROM CR_DOCUMENT_DTL_TEMP WHERE  STAGE_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(stage)).trim()+"' ");
			 	query.append("	and DOC_TYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(entityType)).trim()+"' " );
			 	query.append(" and TXNID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(commonId)).trim()+" " );
			 	query.append(" and TXN_TYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(txnType)).trim()+"' " );
			 	query.append(" and DOC_ID IS NULL) ");
			 	}
		 	else
			{
			query.append(" and d.rec_status ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(recStatus)).trim()+"'");	
			}
		     
		}
		
		if(stage.equalsIgnoreCase("PRS"))
		{
			query.append(" SELECT DISTINCT TXN_DOC_ID,DOC_ID,DOC_DESC,g.CUSTOMER_NAME, ");
			query.append("DATE_FORMAT(DOC_RECEIVED_DATE,'"+dateFormat+"'),"); 
			query.append("DATE_FORMAT(DOC_DEFFRED_DATE,'"+dateFormat+"'),");
			query.append("DATE_FORMAT(DOC_EXPIRY_DATE,'"+dateFormat+"'), "+ 
			 " DOC_MANDATORY,DOC_ORIGINAL,DOC_STATUS,DOC_STATUS_PREV,DOC_REMARKS,Doc_Expiry_Flag,DOC_CHILD_FLAG,DOC_CHILD_IDS,ENTITY_ID,document_type "+ 
			 " from "+source+", cr_deal_customer_m g, cr_deal_dtl L,CR_DEAL_CUSTOMER_ROLE R "+ 
			 " where DOC_TYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(entityType)).trim()+"' " +
			 " and TXNID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(commonId)).trim()+" " +
			 " and TXN_TYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(txnType)).trim()+"' " +
			 " and STAGE_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(stage)).trim()+"'"+ 
	//		 " and d.rec_status = '"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(recStatus)).trim()+"'" +
			 " AND D.TXNID = L.DEAL_ID "+ 
			 " AND D.ENTITY_ID=R.DEAL_CUSTOMER_ID and R.DEAL_CUSTOMER_ID=g.CUSTOMER_ID " +
			 " AND R.DEAL_CUSTOMER_ROLE_TYPE = '"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(entityType)).trim()+"' AND d.DOC_ID IS NOT NULL ");
			 if(!CommonFunction.checkNull(source).trim().equalsIgnoreCase("CR_DOCUMENT_DTL_TEMP d"))
			 	{
			 	query.append("  and d.TXNID NOT IN(SELECT TXNID FROM CR_DOCUMENT_DTL_TEMP WHERE  STAGE_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(stage)).trim()+"' ");
			 	query.append("	and DOC_TYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(entityType)).trim()+"' " );
			 	query.append(" and TXNID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(commonId)).trim()+" " );
			 	query.append(" and TXN_TYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(txnType)).trim()+"' " );
			 	query.append(" and DOC_ID IS NULL) ");
			 	}
				else
				{
				query.append(" and d.rec_status ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(recStatus)).trim()+"'");	
				}
		}
	
	logger.info("getAllApplicantDocsForTempAtDocCollection Query2: "+query.toString());
	

	ArrayList docsdeatail = ConnectionDAO.sqlSelect(query.toString());
	logger.info("getDocumentsDetail OUTER ARRAYLIST SIZE: "+docsdeatail.size());
	int size=docsdeatail.size();
	for(int i=0;i<size;i++){

		//logger.info("getDocumentsDetail"+docsdeatail.get(i).toString());
		ArrayList docsdeatail1=(ArrayList)docsdeatail.get(i);
		//logger.info("getDocumentsDetail INNNER ARRAYLIST SIZE: "+docsdeatail1.size());
		if(docsdeatail1.size()>0)
		{
			docsVo = new DocumentsVo();
			docsVo.setDocId((CommonFunction.checkNull(docsdeatail1.get(0))).trim());
			docsVo.setRealDocId((CommonFunction.checkNull(docsdeatail1.get(1))).trim());
			docsVo.setDocDesc((CommonFunction.checkNull(docsdeatail1.get(2))).trim());
			docsVo.setApplName((CommonFunction.checkNull(docsdeatail1.get(3))).trim());
			docsVo.setRecieveDate((CommonFunction.checkNull(docsdeatail1.get(4))).trim());
			docsVo.setDeferDate((CommonFunction.checkNull(docsdeatail1.get(5))).trim());
			docsVo.setExpirDate((CommonFunction.checkNull(docsdeatail1.get(6))).trim());

			if((CommonFunction.checkNull(docsdeatail1.get(7)).trim()).equals("Y"))
			{
				docsVo.setMandatory("MANDATORY");
			}
			else 	if((CommonFunction.checkNull(docsdeatail1.get(7)).trim()).equals("N"))
			{
				docsVo.setMandatory("NON-MANDATORY");
			}

			if((CommonFunction.checkNull(docsdeatail1.get(8)).trim()).equals("Y"))
			{
				docsVo.setOriginal("ORIGINAL");
			}
			else if((CommonFunction.checkNull(docsdeatail1.get(8)).trim()).equals("N"))
			{
				docsVo.setOriginal("PHOTOCOPY");
			}

			
			docsVo.setDocStatus((CommonFunction.checkNull(docsdeatail1.get(9))).trim());
			docsVo.setDocStatusPrev((CommonFunction.checkNull(docsdeatail1.get(10))).trim());
			docsVo.setRemark((CommonFunction.checkNull(docsdeatail1.get(11))).trim());
			docsVo.setExpirFlag((CommonFunction.checkNull(docsdeatail1.get(12))).trim());
			docsVo.setDocChildFlag((CommonFunction.checkNull(docsdeatail1.get(13))).trim());
			docsVo.setDocChildId((CommonFunction.checkNull(docsdeatail1.get(14))).trim());
			docsVo.setEntityId((CommonFunction.checkNull(docsdeatail1.get(15))).trim());
			docsVo.setvDocumentType((CommonFunction.checkNull(docsdeatail1.get(16))).trim());			
			list.add(docsVo);
		}
	}
	}catch(Exception e){
		e.printStackTrace();
	}
	finally
	{
		docsVo=null;
		query = null;
		entityType = null;
		commonId = null;
		stage = null;
		txnType = null;
		recStatus = null;
		source=null;
	}
		return list;
}


@Override
public ArrayList<DocumentsVo> getAssetCollateralDocumentsForTempAtDocCollection(
		String entityType, String commonId, String stage, String txnType,
		String recStatus, String source) {
	logger.info("in getAssetCollateralDocumentsForTempAtDocCollection()::::");
	DocumentsVo docsVo = null;
	ArrayList<DocumentsVo> list = new ArrayList<DocumentsVo>();
	StringBuilder query = new StringBuilder(); 
	try 
	{
		if(!stage.equalsIgnoreCase("PRS"))
		{
			query.append(" SELECT DISTINCT TXN_DOC_ID,DOC_ID,DOC_DESC,lc.ASSETID,ASSET_COLLATERAL_CLASS," );
			query.append("DATE_FORMAT(DOC_RECEIVED_DATE,'"+ dateFormat+ "'),");
			query.append("DATE_FORMAT(DOC_DEFFRED_DATE,'"+ dateFormat+ "')," );
			query.append("DATE_FORMAT(DOC_EXPIRY_DATE,'"+ dateFormat+ "'),DOC_MANDATORY,DOC_ORIGINAL,DOC_STATUS,DOC_STATUS_PREV,DOC_REMARKS," +
				" Doc_Expiry_Flag,DOC_CHILD_FLAG,DOC_CHILD_IDS,ENTITY_ID,document_type from "+source+"" +
				", cr_loan_collateral_m lc," +
				" cr_asset_collateral_m ac" +
				" where DOC_TYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(entityType)).trim()+"' " +
				" and TXNID="+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(commonId)).trim()+ " " +
				" and TXN_TYPE='"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(txnType)).trim()+ "'" +
				" AND D.TXNID = lc.LOAN_ID" +
				" AND D.ENTITY_ID = lc.ASSETID AND lc.ASSETID=ac.ASSET_ID AND d.DOC_ID IS NOT NULL ");
		     if(!CommonFunction.checkNull(stage).trim().equalsIgnoreCase("")){
		    	 query.append("  and STAGE_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(stage)).trim()+"' ");
	//			query.append(" and d.rec_status ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(recStatus)).trim()+"'");
		     }
		     if(!CommonFunction.checkNull(source).trim().equalsIgnoreCase("CR_DOCUMENT_DTL_TEMP d"))
			 	{
			 	query.append("  and d.TXNID NOT IN(SELECT TXNID FROM CR_DOCUMENT_DTL_TEMP WHERE  STAGE_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(stage)).trim()+"' ");
			 	query.append("	and DOC_TYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(entityType)).trim()+"' " );
			 	query.append(" and TXNID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(commonId)).trim()+" " );
			 	query.append(" and TXN_TYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(txnType)).trim()+"' " );
			 	query.append(" and DOC_ID IS NULL) ");
			 	}
		 	else
			{
			query.append(" and d.rec_status ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(recStatus)).trim()+"'");	
			}
		}
		if(stage.equalsIgnoreCase("PRS"))
		{
			query.append(" SELECT DISTINCT TXN_DOC_ID,DOC_ID,DOC_DESC,dc.ASSETID,ASSET_COLLATERAL_CLASS," );
			query.append("DATE_FORMAT(DOC_RECEIVED_DATE,'"+ dateFormat+ "'),");
			query.append("DATE_FORMAT(DOC_DEFFRED_DATE,'"+ dateFormat+ "')," );
			query.append("DATE_FORMAT(DOC_EXPIRY_DATE,'"+ dateFormat+ "'),DOC_MANDATORY,DOC_ORIGINAL,DOC_STATUS,DOC_STATUS_PREV,DOC_REMARKS," +
				" Doc_Expiry_Flag,DOC_CHILD_FLAG,DOC_CHILD_IDS,ENTITY_ID,document_type from "+source+"," +
				" cr_deal_collateral_m dc," +
				" cr_asset_collateral_m ac" +
				" where DOC_TYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(entityType)).trim()+"' " +
				" and TXNID="+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(commonId)).trim()+ " " +
				" and TXN_TYPE='"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(txnType)).trim()+ "'" +
				" and STAGE_ID='"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(stage)).trim()+ "'" +
				" AND D.TXNID = dc.DEAL_ID" +
//				" and d.rec_status = '"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(recStatus)).trim()+"'" +
				" AND D.ENTITY_ID = dc.ASSETID AND dc.ASSETID=ac.ASSET_ID AND d.DOC_ID IS NOT NULL ");
			 if(!CommonFunction.checkNull(source).trim().equalsIgnoreCase("CR_DOCUMENT_DTL_TEMP d"))
			 	{
			 	query.append("  and TXNID NOT IN(SELECT TXNID FROM CR_DOCUMENT_DTL_TEMP WHERE  STAGE_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(stage)).trim()+"' ");
			 	query.append("	and DOC_TYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(entityType)).trim()+"' " );
			 	query.append(" and TXNID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(commonId)).trim()+" " );
			 	query.append(" and TXN_TYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(txnType)).trim()+"' " );
			 	query.append(" and DOC_ID IS NULL) ");
			 	}
				else
				{
				query.append(" and d.rec_status ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(recStatus)).trim()+"'");	
				}
		}
		logger.info("getDocumentsDetai Query3: " + query.toString());

		ArrayList docsdeatail = ConnectionDAO.sqlSelect(query.toString());
		logger.info("getDocumentsDetail OUTER ARRAYLIST SIZE: "+ docsdeatail.size());
		int size=docsdeatail.size();
		for (int i = 0; i < size; i++) 
		{
			//logger.info("getDocumentsDetail"+ docsdeatail.get(i).toString());
			ArrayList docsdeatail1 = (ArrayList) docsdeatail.get(i);
			//logger.info("getDocumentsDetail INNNER ARRAYLIST SIZE: "+ docsdeatail1.size());
			if (docsdeatail1.size() > 0) 
			{
				docsVo = new DocumentsVo();
				docsVo.setDocId((CommonFunction.checkNull(docsdeatail1.get(0))).trim());
				docsVo.setRealDocId((CommonFunction.checkNull(docsdeatail1.get(1))).trim());
				docsVo.setDocDesc((CommonFunction.checkNull(docsdeatail1.get(2))).trim());
				docsVo.setType((CommonFunction.checkNull(docsdeatail1.get(4))).trim());
				docsVo.setRecieveDate((CommonFunction.checkNull(docsdeatail1.get(5))).trim());
				docsVo.setDeferDate((CommonFunction.checkNull(docsdeatail1.get(6))).trim());
				docsVo.setExpirDate((CommonFunction.checkNull(docsdeatail1.get(7))).trim());

				if ((CommonFunction.checkNull(docsdeatail1.get(8)).trim()).equals("Y")) {
					docsVo.setMandatory("MANDATORY");
				} 
				else if ((CommonFunction.checkNull(docsdeatail1.get(8)).trim()).equals("N")) {
					docsVo.setMandatory("NON-MANDATORY");
				}

				if ((CommonFunction.checkNull(docsdeatail1.get(9)).trim()).equals("Y")) {
					docsVo.setOriginal("ORIGINAL");
				} 
				else if ((CommonFunction.checkNull(docsdeatail1.get(9)).trim()).equals("N")) {
					docsVo.setOriginal("PHOTOCOPY");
				}

				docsVo.setDocStatus((CommonFunction.checkNull(docsdeatail1.get(10))).trim());
				docsVo.setDocStatusPrev((CommonFunction.checkNull(docsdeatail1.get(11))).trim());
				docsVo.setRemark((CommonFunction.checkNull(docsdeatail1.get(12))).trim());
				docsVo.setExpirFlag((CommonFunction.checkNull(docsdeatail1.get(13))).trim());
				docsVo.setDocChildFlag((CommonFunction.checkNull(docsdeatail1.get(14))).trim());
				docsVo.setDocChildId((CommonFunction.checkNull(docsdeatail1.get(15))).trim());
				docsVo.setEntityId((CommonFunction.checkNull(docsdeatail1.get(16))).trim());
				docsVo.setvDocumentType((CommonFunction.checkNull(docsdeatail1.get(17))).trim());				
				list.add(docsVo);
			}
		}
		docsVo = null;
		docsdeatail.clear();
		docsdeatail=null;
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally
	{
		docsVo=null;
		query = null;
		entityType = null;
		commonId = null;
		stage = null;
		txnType = null; 
		recStatus = null;
		source=null;
	}
	return list;

}

@Override
public String documentForwardOrNotAtDocumentCollection(String commonId,String stage) {
	
	logger.info("IN getDocFromTempOrNot():::::");
	String result=null;
	StringBuilder query = new StringBuilder(); 
	try{
		query.append("select count(1) from CR_DOCUMENT_DTL_TEMP ");
		query.append(" where  " );
		query.append("  TXNID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(commonId)).trim()+" " );
		query.append(" and TXN_TYPE='LIM' " );
		query.append(" and STAGE_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(stage)).trim()+"'" );
		result=ConnectionDAO.singleReturn(query.toString());
		logger.info("In documentForwardOrNotAtDocumentCollection()::::query::"+query.toString());
  }
	catch(Exception e)
	{
		e.printStackTrace();
	}
	finally
	{
		commonId=null;
		stage=null;
		query=null;
	 }
	return result;
}
//end by sachin
//MANISH
	@Override
	public boolean updateServiceBranch(LoanInitAuthorVo vo, String loanNo) {
		

		logger.info("In updateServiceBranch.......Update loanId " + loanNo);
		boolean status = false;
		ArrayList qryList = new ArrayList();
		String query = "select loan_Id from cr_loan_dtl_edit where loan_Id = '"+vo.getLoanId()+"'";
		String queryValue = CommonFunction.checkNull(ConnectionDAO.singleReturn(query));
		try
		{
		String serviceBranch= vo.getServiceBranch();
		String loanId= vo.getLoanId();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();

		
		StringBuilder query1 = new StringBuilder();
		if(queryValue.equalsIgnoreCase(""))
		{
		query1.append("insert into cr_loan_dtl_edit (loan_Id,loan_no,service_branch)");
		query1.append(" values ( ");
		//query1.append(" values ('"+loanNo+"','"+serviceBranch+"' )");
		//query1.append('"+loanNo+"');
		//query1.append(")");
		query1.append(" ?,");
		query1.append(" ?,");
		query1.append(" ?");
		query1.append(")");
		//query1.append("update cr_loan_dtl  set SERVICE_BRANCH='"+serviceBranch+"' WHERE LOAN_NO='"+ loanNo + "' ");
		if ((CommonFunction.checkNull(vo.getLoanId())).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getLoanId()).trim());
		if ((CommonFunction.checkNull(vo.getLoanNo())).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getLoanNo()).trim());
		if ((CommonFunction.checkNull(vo.getLbxserviceBranchID())).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getLbxserviceBranchID()).trim());


		insertPrepStmtObject.setSql(query1.toString());
		logger.info("IN updateServiceBranch() insert query1 ### "
				+ insertPrepStmtObject.printQuery());
		//logger.info("IN updateServiceBranch() of  insert query1 ### "
		//		+ insertPrepStmtObject.printQuery());
		qryList.add(insertPrepStmtObject);

		}
		else
		{
			query1.append("update cr_loan_dtl_edit set service_branch = ? where loan_Id = ?");
			
			
			if ((CommonFunction.checkNull(vo.getLbxserviceBranchID())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getLbxserviceBranchID()).trim());
			
			if ((CommonFunction.checkNull(vo.getLoanId())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getLoanId()).trim());
			
			insertPrepStmtObject.setSql(query1.toString());
			logger.info("IN updateServiceBranch() insert query1 ### "
					+ insertPrepStmtObject.printQuery());
			qryList.add(insertPrepStmtObject);
			
		}
	
				
					status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				} catch (Exception e) {
					e.printStackTrace();
				}
		
			logger.info("In updateServiceBranch......................status= "+ status);


		return status;
	}
	@Override
	public ArrayList getServiceBranch(String loanId) {
		

		ArrayList<LoanInitAuthorVo> loanDetailList=new ArrayList<LoanInitAuthorVo>();
		StringBuilder query = new StringBuilder(); 
		logger.info("In getretriveCutInsValues: "+loanId);
			try{

		  ArrayList mainlist=new ArrayList();
		  ArrayList subList=new ArrayList();
		  query.append("select cld.service_branch,cbm.branch_desc from cr_loan_dtl_edit cld join com_branch_m cbm on cld.service_branch=cbm.branch_id where cld.loan_id = '"+loanId +"' ");
				  	
			logger.info("In getretriveCutInsValues QUERY  :  "+query.toString());
			mainlist=ConnectionDAO.sqlSelect(query.toString());
			if(mainlist != null && mainlist.size()==0)
			{
				String query1= ("select cld.service_branch,cbm.branch_desc from cr_loan_dtl cld join com_branch_m cbm on cld.service_branch=cbm.branch_id where cld.loan_id = '"+loanId +"' ");
			  	
				logger.info("In getretriveCutInsValues QUERY  :  "+query1.toString());
				mainlist=ConnectionDAO.sqlSelect(query1.toString());
			}
			logger.info("In getretriveCutInsValues list,,,,,"+mainlist.size());
				
				for(int i=0;i<mainlist.size();i++){
					
					subList=(ArrayList)mainlist.get(i);
					logger.info("In getListOfValues sublist..."+subList.size());
					
					if(subList.size()>0){
						
							LoanInitAuthorVo loanvo=new LoanInitAuthorVo();
						    loanvo.setLbxserviceBranchID((CommonFunction.checkNull(subList.get(0)).trim()));
						    loanvo.setServiceBranch((CommonFunction.checkNull(subList.get(1)).trim()));
						  				  
							loanDetailList.add(loanvo);
					}
				}
				mainlist.clear();
				mainlist=null;
				subList.clear();
				subList=null;
			}catch(Exception e){
				e.printStackTrace();
			}
			finally
			{
				query = null;
			}
		return loanDetailList;

	}

public boolean checkDocuments(String[] instrumentIDList,String[] checkedstatusList, String status) 
{
	boolean recordStatus=false;
	String nextStatus="";
	
	String query="";
	int count=0;
	for(int i=0;i<instrumentIDList.length;i++)
	{
		String dealId="";
		String loanId="";
		String  q1="select TXNID from CR_INSTRUMENT_DTL where INSTRUMENT_ID='"+instrumentIDList[i]+"' ";
		 loanId=ConnectionDAO.singleReturn(q1);
		
		if(!CommonFunction.checkNull(loanId).equalsIgnoreCase("")){
			String  q2="select LOAN_DEAL_ID from CR_LOAN_DTL where LOAN_ID='"+loanId+"' ";
			dealId=ConnectionDAO.singleReturn(q2);
		}
		
		query="select count(1) from cr_document_dtl where ((TXNID='"+loanId+"' and TXN_TYPE='LIM') OR (TXNID='"+dealId+"' and TXN_TYPE='DC')) and DOCUMENT_TYPE='OTC' and DOC_STATUS='D' ";
		try
		{
			logger.info("Query to check existing recored  :  "+query);
			count=Integer.parseInt(ConnectionDAO.singleReturn(query));
			if(count>0)
			{
				recordStatus=true;
				break;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	query = null;
	return recordStatus;
}


	public ArrayList selectIndustryDetails(String natureOfBus,String lbxIndustry) {
		String query="";
		ArrayList list = new ArrayList();
		 ArrayList mainlist=new ArrayList();
		  ArrayList subList=new ArrayList();
		  CorporateDetailsVO	vo=new CorporateDetailsVO();
		try{
			if(CommonFunction.checkNull(natureOfBus).equalsIgnoreCase("MANUFACTUR")){
			 query="select MANUFACTURER from  COM_INDUSTRY_M where INDUSTRY_ID='"+lbxIndustry+"' ";
			}
			if(CommonFunction.checkNull(natureOfBus).equalsIgnoreCase("TRADING") || CommonFunction.checkNull(natureOfBus).equalsIgnoreCase("WHOLESALER")){
				 query="select TRADER from  COM_INDUSTRY_M where INDUSTRY_ID='"+lbxIndustry+"' ";	 
			 }
			 if(CommonFunction.checkNull(natureOfBus).equalsIgnoreCase("SERVICES")){
				 query="select SERVICE from  COM_INDUSTRY_M where INDUSTRY_ID='"+lbxIndustry+"' ";		 
			 }
			 mainlist=ConnectionDAO.sqlSelect(query);
			 logger.info("In selectIndustryDetails list,,,,,"+mainlist.size());
				
				for(int i=0;i<mainlist.size();i++){
					
					subList=(ArrayList)mainlist.get(i);
					logger.info("In selectIndustryDetails sublist..."+subList.size());
					
					if(subList.size()>0){
						vo=new CorporateDetailsVO();
						 vo.setIndustryPercent((CommonFunction.checkNull(subList.get(0)).trim()));  
						list.add(vo);
					}
				}
				mainlist.clear();
				mainlist=null;
				subList.clear();
				subList=null;
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}


public String getEditDueDateStatus(String lbxLoanNoHID)

{
	logger.info("---------------In getEditDueDateStatus()-----------");
	String query = "select edit_Due_Date from cr_loan_dtl where loan_id = '"+lbxLoanNoHID+"'";
	String str = "";
	
		str = ConnectionDAO.singleReturn(query);
	
	
	return str;
	
	
}


public String getRepayEffDate(String lbxLoanNoHID, String reschID)

{
	logger.info("---------------In getEditDueDateStatus()-----------");
	String query = "Select date_format(RESCH_EFF_DATE,'"+dateFormat+"')  from cr_resch_dtl where loan_id='"+lbxLoanNoHID+"' and resch_id='"+reschID+"'";
	String str = "";
	
		str = ConnectionDAO.singleReturn(query);
	
	
	return str;
	
	
}


public ArrayList<LoanDetailForCMVO> selectSblGblWaiverMakerSearch(String loanId,String disbursalNo) 
{
	StringBuffer bufInsSql = new StringBuffer();
	/*StringBuffer bufInsSql1 = new StringBuffer();
	StringBuffer bufInsSql2 = new StringBuffer();*/
	ArrayList searchlist = new ArrayList();
	ArrayList detailList =new ArrayList();
	/*ArrayList detailList2=new ArrayList();*/
	try{
		
		//Pooja
				String CustId = CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT IFNULL(GCD_ID,'') FROM CR_LOAN_CUSTOMER_ROLE "
							+ " WHERE LOAN_ID = '"+loanId+"' ")); 
			
				String CustGroupId = CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT IFNULL(CUSTOMER_GROUP_ID,'') "
								+ " FROM cr_deal_customer_m WHERE CUSTOMER_ID = '"+CommonFunction.checkNull(CustId).trim()+"' "));
				
				String sbl = CommonFunction.checkNull(ConnectionDAO.singleReturn(" "
							+ " SELECT IFNULL(SINGLEBORROWERLIMIT,'0.00') FROM CR_SBL_M WHERE PRODUCT_ID = (SELECT IFNULL(LOAN_PRODUCT,'') "
							+ " FROM CR_LOAN_DTL WHERE LOAN_ID = '"+loanId+"') "));
				
				String gbl = CommonFunction.checkNull(ConnectionDAO.singleReturn(" "
							+ " SELECT IFNULL(GROUPBORROWERLIMIT,'0.00') FROM CR_SBL_M WHERE PRODUCT_ID = (SELECT IFNULL(LOAN_PRODUCT,'') "
							+ " FROM CR_LOAN_DTL WHERE LOAN_ID = '"+loanId+"') "));
				  
				String CustCurrentPos = CommonFunction.checkNull(ConnectionDAO.singleReturn("select( "
								+ " SELECT IFNULL(ROUND(SUM(LOAN_BALANCE_PRINCIPAL),2),'0.00') FROM CR_LOAN_DTL "
								+ " WHERE LOAN_ID IN (SELECT LOAN_ID FROM CR_LOAN_CUSTOMER_ROLE WHERE GCD_ID = '"+CustId+"' "
								+ " AND LOAN_CUSTOMER_ROLE_TYPE IN ('PRAPPL','COAPPL')))+(select IFNULL(round(sum(disbursal_amount),2),0.00) from cr_loan_disbursal_dtl_temp where loan_id='"+loanId+"')+(select IFNULL(round(sum(disbursal_amount),2),0.00) from cr_loan_disbursal_dtl  WHERE  REC_STATUS<>'X' AND  LOAN_ID='"+loanId+"' ) ")); 
				
				String GroupPos = CommonFunction.checkNull(ConnectionDAO.singleReturn("select( "
						+ " SELECT IFNULL(ROUND(SUM(LOAN_BALANCE_PRINCIPAL),2),'0.00') FROM CR_LOAN_CUSTOMER_ROLE R "
						+ " JOIN CR_LOAN_DTL L ON R.LOAN_ID = L.LOAN_ID JOIN CR_DEAL_CUSTOMER_M M ON R.GCD_ID = M.CUSTOMER_ID "
						+ " WHERE M.CUSTOMER_GROUP_ID = '"+CustGroupId+"' AND R.LOAN_CUSTOMER_ROLE_TYPE IN ('PRAPPL','COAPPL'))+(select IFNULL(round(sum(disbursal_amount),2),0.00) from cr_loan_disbursal_dtl_temp where loan_id='"+loanId+"')+(select IFNULL(round(sum(disbursal_amount),2),0.00) from cr_loan_disbursal_dtl  WHERE  REC_STATUS<>'X' AND LOAN_ID='"+loanId+"' ) "));
					//Pooja
					BigDecimal sblMaster = new BigDecimal(sbl.toString());
					BigDecimal gblMaster = new BigDecimal(gbl.toString());
					BigDecimal CustCurrentPosLimit = new BigDecimal(CustCurrentPos.toString());
					BigDecimal GroupPosLimit = new BigDecimal(GroupPos.toString());
					/*bufInsSql1.append("select customer_type from gcd_customer_m g,cr_loan_dtl c where c.loan_customer_id=g.customer_id and c.loan_id='"+loanId+"'");				
		logger.info("query : "+bufInsSql1);
		searchlist = ConnectionDAO.sqlSelect(bufInsSql1.toString());			
		detailList2 = (ArrayList) searchlist.get(0);
		
		if(searchlist.get(0) !=null && detailList2.get(0).toString().equalsIgnoreCase("I")){
			bufInsSql.append("select cld.loan_no,ifnull(round(csm.SingleBorrowerLimit,2),'0.00'),"
					+ "round(((select ifnull(sum(LOAN_BALANCE_PRINCIPAL),'0.00') from cr_loan_customer_role a "
					+ "join cr_loan_dtl b on a.loan_id=b.loan_id where gcd_id in (select loan_customer_id "
					+ "from cr_loan_dtl where loan_id=cld.loan_id))+ifnull(cld.loan_loan_amount,'0.00')),2),remarks "
					+ "from cr_loan_dtl cld left join gcd_customer_m g on cld.LOAN_CUSTOMER_ID=g.CUSTOMER_ID  "
					+ "left join cr_sblGbl_tmp cdld on cld.loan_no=cdld.loan_no and disb_no='"+disbursalNo+"' "
					+ "left join CR_SBL_M csm on cld.loan_product=csm.product_id and csm.rec_status='A' where cld.loan_id='"+loanId+"'");*/
	bufInsSql.append("select cld.loan_no,'"+sblMaster+"','"+gblMaster+"','"+CustCurrentPosLimit+"','"+GroupPosLimit+"',remarks "
					+ "from cr_loan_dtl cld "
					+ "left join cr_sblGbl_tmp cdld on cld.loan_no=cdld.loan_no and disb_no='"+disbursalNo+"' where cld.loan_id='"+loanId+"' ");
	logger.info("query : "+bufInsSql);
	searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());		
	
	for (int i = 0; i < searchlist.size(); i++) {

		ArrayList data = (ArrayList) searchlist.get(i);
		if (data.size() > 0) {
			LoanDetailForCMVO loanDetailForCMVO=new LoanDetailForCMVO();	
			
			loanDetailForCMVO.setLoanNo(CommonFunction.checkNull(data.get(0)).toString());
			loanDetailForCMVO.setDisbursalNo(disbursalNo);
			loanDetailForCMVO.setSblMasterLimit(CommonFunction.checkNull(data.get(1)).toString());
			loanDetailForCMVO.setGblMasterLimit(CommonFunction.checkNull(data.get(2)).toString());
			loanDetailForCMVO.setCustCurrentPos(CommonFunction.checkNull(data.get(3)).toString());
			loanDetailForCMVO.setGroupPos(CommonFunction.checkNull(data.get(4)).toString());
			/*if(data.get(2) != null && data.get(1) !=null){
			String captureLimit=data.get(2)+"".trim();
			String masterLimit=data.get(1)+"".trim();
			BigDecimal captureLimitValue=new BigDecimal("0.00");
			BigDecimal masterLimitValue=new BigDecimal("0.00");
			captureLimitValue = new BigDecimal(captureLimit);
			masterLimitValue = new BigDecimal(masterLimit);
			double captureLimitValue=Double.parseDouble(captureLimit); 
			double masterLimitValue=Double.parseDouble(masterLimit);
			BigDecimal result=new BigDecimal("0.00");
			//double result=0.0;
			int res=captureLimitValue.compareTo(masterLimitValue);
			if(res==1){
				result=captureLimitValue.subtract(masterLimitValue);
				loanDetailForCMVO.setDiff(result+"");
			}else{
				loanDetailForCMVO.setDiff("NA");
			}
		}*/
			if(CommonFunction.checkNull(data.get(5)).trim().equalsIgnoreCase("")){
				loanDetailForCMVO.setRemarks("<input type='text' class='text' name='remarks' id='remarks"+i+"' value='' maxlength='50' />");
				}else{
					loanDetailForCMVO.setRemarks("<input type='text' class='text' name='remarks' id='remarks"+i+"' value='"+CommonFunction.checkNull(data.get(5)).toString()+"' maxlength='50' />");
				}
			
		detailList.add(loanDetailForCMVO);
		}
	}
/*}else{*/
	/*bufInsSql2.append("select c.loan_no,b.GroupBorrowerLimit,a.GROUP_EXPOSURE_LIMIT,remarks from cr_loan_dtl c left join cr_sblGbl_tmp cdld on c.loan_no=cdld.loan_no and disb_no='"+disbursalNo+"' join gcd_customer_m g on c.LOAN_CUSTOMER_ID=g.CUSTOMER_ID join gcd_group_m a on  g.group_id=a.group_id join CR_SBL_M b on c.loan_product=b.product_id  and b.rec_status='A' and c.loan_id='"+loanId+"'");		*/
	/*bufInsSql2.append("select c.loan_no,ifnull(round(b.GroupBorrowerLimit,2),'0.00'),"
			+ "ifnull(round((select sum(CUSTOMER_GROUP_EXPOSURE_LIMIT) from cr_deal_customer_m "
			+ "where CUSTOMER_GROUP_ID=(select CUSTOMER_GROUP_ID from cr_deal_customer_m  "
			+ "where CUSTOMER_ID=(select loan_customer_id from cr_loan_dtl where loan_id=c.loan_id ))),2),'0.00'), "
			+ "remarks from cr_loan_dtl c left join cr_sblGbl_tmp cdld on c.loan_no=cdld.loan_no and disb_no='"+disbursalNo+"' "
			+ "join gcd_customer_m g on c.LOAN_CUSTOMER_ID=g.CUSTOMER_ID join gcd_group_m a on  g.group_id=a.group_id "
			+ "join CR_SBL_M b on c.loan_product=b.product_id  and b.rec_status='A' and c.loan_id='"+loanId+"'");				
	logger.info("query : "+bufInsSql2);
	searchlist = ConnectionDAO.sqlSelect(bufInsSql2.toString());		
	
	for (int i = 0; i < searchlist.size(); i++) {

		ArrayList data = (ArrayList) searchlist.get(i);
		if (data.size() > 0) {
			LoanDetailForCMVO loanDetailForCMVO=new LoanDetailForCMVO();	
			
				loanDetailForCMVO.setLoanNo(CommonFunction.checkNull(data.get(0)).toString());
			loanDetailForCMVO.setDisbursalNo(disbursalNo);
			loanDetailForCMVO.setSblGblMasterLimit(CommonFunction.checkNull(data.get(1)).toString());
			loanDetailForCMVO.setSblGblCapturedLimit(CommonFunction.checkNull(data.get(2)).toString());
			if(data.get(2) != null && data.get(1) !=null){
			String captureLimit=data.get(2)+"".trim();
			String masterLimit=data.get(1)+"".trim();
			BigDecimal captureLimitValue=new BigDecimal("0.00");
			BigDecimal masterLimitValue=new BigDecimal("0.00");
			captureLimitValue = new BigDecimal(captureLimit);
			masterLimitValue = new BigDecimal(masterLimit);
			BigDecimal result=new BigDecimal("0.00");
			int res=captureLimitValue.compareTo(masterLimitValue);
			double captureLimitValue=Double.parseDouble(captureLimit); 
			double masterLimitValue=Double.parseDouble(masterLimit);
			//double result=0.0;
			if(res==1){
				result=captureLimitValue.subtract(masterLimitValue);
				loanDetailForCMVO.setDiff(result+"");
			}else{
				loanDetailForCMVO.setDiff("NA");
			}
		}
			if(CommonFunction.checkNull(data.get(3)).trim().equalsIgnoreCase("")){
				loanDetailForCMVO.setRemarks("<input type='text' class='text' name='remarks' id='remarks"+i+"' value='' maxlength='50' />");
				}else{
					loanDetailForCMVO.setRemarks("<input type='text' class='text' name='remarks' id='remarks"+i+"' value='"+CommonFunction.checkNull(data.get(3)).toString()+"' maxlength='50' />");
				}
			
		detailList.add(loanDetailForCMVO);
		}
	}
}*/
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	finally
	{
		
	}
	return detailList;
}

public boolean saveSblGblWaiver(LoanDetailForCMVO vo) 
{
	StringBuffer bufInsSql = new StringBuffer();
	ArrayList qryList = new ArrayList();
	
	ArrayList detailList =new ArrayList();
	PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
	int count=Integer.parseInt(ConnectionDAO.singleReturn("Select count(1) from cr_sblGbl_tmp where loan_no='"+vo.getLoanId()+"' and disb_no='"+vo.getDisbursalNo()+"'"));	
	if(count>0){
		ArrayList qryList1 = new ArrayList();
		boolean status = false;
		StringBuilder q = null;
		 
		logger.info("In deleteAsset.....................................Dao Impl....111");
		try {
				q = new StringBuilder();
				
				q.append("delete from  cr_sblGbl_tmp where loan_no='"+vo.getLoanId()+"'and disb_no='"+vo.getDisbursalNo()+"'");
				qryList1.add(q.toString());
				q = null;
			status = ConnectionDAO.sqlInsUpdDelete(qryList1);
			logger.info("In deleteAsset......................" + status);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	boolean status=false;
	try{
		String loanId=CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT loan_id FROM CR_LOAN_dtl "
				+ " WHERE LOAN_NO = '"+vo.getLoanId()+"' ")); 
		String CustId = CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT IFNULL(GCD_ID,'') FROM CR_LOAN_CUSTOMER_ROLE "
				+ " WHERE LOAN_ID = '"+loanId+"' ")); 

	String CustGroupId = CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT IFNULL(CUSTOMER_GROUP_ID,'') "
					+ " FROM cr_deal_customer_m WHERE CUSTOMER_ID = '"+CommonFunction.checkNull(CustId).trim()+"' "));
	
	String sbl = CommonFunction.checkNull(ConnectionDAO.singleReturn(" "
				+ " SELECT IFNULL(SINGLEBORROWERLIMIT,'0.00') FROM CR_SBL_M WHERE PRODUCT_ID = (SELECT IFNULL(LOAN_PRODUCT,'') "
				+ " FROM CR_LOAN_DTL WHERE LOAN_ID = '"+loanId+"') "));
	
	String gbl = CommonFunction.checkNull(ConnectionDAO.singleReturn(" "
				+ " SELECT IFNULL(GROUPBORROWERLIMIT,'0.00') FROM CR_SBL_M WHERE PRODUCT_ID = (SELECT IFNULL(LOAN_PRODUCT,'') "
				+ " FROM CR_LOAN_DTL WHERE LOAN_ID = '"+loanId+"') "));
	  
	String CustCurrentPos = CommonFunction.checkNull(ConnectionDAO.singleReturn("select( "
					+ " SELECT IFNULL(ROUND(SUM(LOAN_BALANCE_PRINCIPAL),2),'0.00') FROM CR_LOAN_DTL "
					+ " WHERE LOAN_ID IN (SELECT LOAN_ID FROM CR_LOAN_CUSTOMER_ROLE WHERE GCD_ID = '"+CustId+"' "
					+ " AND LOAN_CUSTOMER_ROLE_TYPE IN ('PRAPPL','COAPPL')))+(select IFNULL(round(sum(disbursal_amount),2),0.00) from cr_loan_disbursal_dtl_temp where loan_id='"+loanId+"')+(select IFNULL(round(sum(disbursal_amount),2),0.00) from cr_loan_disbursal_dtl  WHERE REC_STATUS<>'X' AND  LOAN_ID='"+loanId+"' ) ")); 
	
	String GroupPos = CommonFunction.checkNull(ConnectionDAO.singleReturn("select( "
			+ " SELECT IFNULL(ROUND(SUM(LOAN_BALANCE_PRINCIPAL),2),'0.00') FROM CR_LOAN_CUSTOMER_ROLE R "
			+ " JOIN CR_LOAN_DTL L ON R.LOAN_ID = L.LOAN_ID JOIN CR_DEAL_CUSTOMER_M M ON R.GCD_ID = M.CUSTOMER_ID "
			+ " WHERE M.CUSTOMER_GROUP_ID = '"+CustGroupId+"' AND R.LOAN_CUSTOMER_ROLE_TYPE IN ('PRAPPL','COAPPL'))+(select IFNULL(round(sum(disbursal_amount),2),0.00) from cr_loan_disbursal_dtl_temp where loan_id='"+loanId+"')+(select IFNULL(round(sum(disbursal_amount),2),0.00) from cr_loan_disbursal_dtl  WHERE REC_STATUS<>'X' AND  LOAN_ID='"+loanId+"' ) "));
		//Pooja
		
		bufInsSql.append("insert into cr_sblGbl_tmp(loan_no,disb_no,sbl_master_limit,gbl_master_limit,sbl_capture_limit,gbl_capture_limit,maker_id,MAKER_DATE,rec_status,remarks)");
		bufInsSql.append(" values ( ");
		bufInsSql.append(" ?,"); //loan_no
		bufInsSql.append(" ?,"); //disb_no
		bufInsSql.append(" ?,"); //sbl_master_limit
		bufInsSql.append(" ?,"); //gbl_master_limit
		bufInsSql.append(" ?,"); //sbl_capture_limit
		bufInsSql.append(" ?,"); //gbl_capture_limit
		bufInsSql.append(" ?,"); //maker_id
		bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND)"); //MAKER_DATE
		bufInsSql.append(" ,?,"); //rec_status
		bufInsSql.append("  ?)"); // remarks
		if (CommonFunction.checkNull(vo.getLoanId()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getLoanId().toUpperCase().trim());
		
		if (CommonFunction.checkNull(vo.getDisbursalNo()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getDisbursalNo());

		if (CommonFunction.checkNull(vo.getSblMasterLimit()).equalsIgnoreCase(""))
			insertPrepStmtObject.addString(sbl);
		else
			insertPrepStmtObject.addString(vo.getSblMasterLimit().trim());
		
		if (CommonFunction.checkNull(vo.getGblMasterLimit()).equalsIgnoreCase(""))
			insertPrepStmtObject.addString(gbl);
		else
			insertPrepStmtObject.addString(vo.getGblMasterLimit().trim());

		if (vo.getCustCurrentPos()==null || vo.getCustCurrentPos().equalsIgnoreCase(""))
			insertPrepStmtObject.addString(CustCurrentPos);
		else
			insertPrepStmtObject.addString(vo.getCustCurrentPos().trim());
		
		if (vo.getGroupPos()==null || vo.getGroupPos().equalsIgnoreCase(""))
			insertPrepStmtObject.addString(GroupPos);
		else
			insertPrepStmtObject.addString(vo.getGroupPos().trim());

		/*if (vo.getDiff()==null || vo.getDiff().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getDiff().trim());*/
		
		if (vo.getMakerId()==null || vo.getMakerId().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getMakerId().trim());
		
		
		if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getMakerDate());
		
			insertPrepStmtObject.addString("F");

		if (CommonFunction.checkNull(vo.getRemarks()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getRemarks());


		insertPrepStmtObject.setSql(bufInsSql.toString());

		logger.info("IN insertSblMaster() insert query1 ### "+ insertPrepStmtObject.printQuery());

		qryList.add(insertPrepStmtObject);
		status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
		logger.info("In insertSblMaster......................"+ status);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	finally
	{
		
	}
	return status;
}
public ArrayList<LoanDetailForCMVO> searchSblAuthor(String makerId) 
{
	StringBuffer bufInsSql = new StringBuffer();
	ArrayList searchlist = new ArrayList();
	ArrayList detailList =new ArrayList();
	try{
	bufInsSql.append("select loan_no,disb_no,sbl_master_limit,gbl_master_limit,sbl_capture_limit,gbl_capture_limit,remarks from cr_sblGbl_tmp where rec_status='F' and cr_sblGbl_tmp.maker_id <> '"+makerId+"'");						
	logger.info("query : "+bufInsSql);
	searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());		
	
	for (int i = 0; i < searchlist.size(); i++) {

		ArrayList data = (ArrayList) searchlist.get(i);
		if (data.size() > 0) {
			LoanDetailForCMVO loanDetailForCMVO=new LoanDetailForCMVO();	
			
			loanDetailForCMVO.setLoanNo("<a href=sblGblMakerSearch.do?method=openSblGblValuesAuthor&loanNo="+data.get(0).toString()+"&disbursalId="+data.get(1).toString()+">"+(CommonFunction.checkNull(data.get(0)).trim()) +"</a>");
			loanDetailForCMVO.setDisbursalNo(CommonFunction.checkNull(data.get(1)).toString());
			loanDetailForCMVO.setSblMasterLimit(CommonFunction.checkNull(data.get(2)).toString());
			loanDetailForCMVO.setGblMasterLimit(CommonFunction.checkNull(data.get(3)).toString());
			loanDetailForCMVO.setCustCurrentPos(CommonFunction.checkNull(data.get(4)).toString());
			loanDetailForCMVO.setGroupPos(CommonFunction.checkNull(data.get(5)).toString());
			loanDetailForCMVO.setRemarks(CommonFunction.checkNull(data.get(6)).toString());
			
		detailList.add(loanDetailForCMVO);
		}
	}
	
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	finally
	{
		
	}
	return detailList;
}	
public boolean saveSblGblWaiverAuthor(LoanDetailForCMVO vo){
	StringBuffer bufInsSql = new StringBuffer();
	StringBuffer bufInsSql2 = new StringBuffer();
	StringBuffer bufInsSql3 = new StringBuffer();
	ArrayList qryList = new ArrayList();
	ArrayList detailList =new ArrayList();
	ArrayList searchlist =new ArrayList();
	ArrayList qryList1 = new ArrayList();
	PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
	PrepStmtObject insertPrepStmtObject1 = new PrepStmtObject();

	boolean status=false;
	try{
		if(vo.getDecision().equalsIgnoreCase("A") || vo.getDecision().equalsIgnoreCase("X")){
			
			bufInsSql2.append("select loan_no,disb_no,sbl_master_limit,gbl_master_limit,sbl_capture_limit,gbl_capture_limit,remarks,maker_id,maker_date from cr_sblGbl_tmp where loan_no='"+vo.getLoanId()+"' and disb_no='"+vo.getDisbursalId()+"'");				
			logger.info("query : "+bufInsSql2);
			searchlist = ConnectionDAO.sqlSelect(bufInsSql2.toString());			
			ArrayList data = (ArrayList) searchlist.get(0);

			
			
		bufInsSql.append("insert into cr_sblGbl_dtl(loan_no,disb_no,sbl_master_limit,gbl_master_limit,sbl_capture_limit,gbl_capture_limit,remarks,maker_id,MAKER_DATE,rec_status,author_id,author_date,comments)");
		bufInsSql.append(" values ( ");
		bufInsSql.append(" ?,");
		bufInsSql.append(" ?,");
		bufInsSql.append(" ?,");
		bufInsSql.append(" ?,");
		bufInsSql.append(" ?,");
		bufInsSql.append(" ?,");
		bufInsSql.append(" ?,");
		bufInsSql.append(" ?,");
		bufInsSql.append(" ?,");
		bufInsSql.append(" ?,");
		bufInsSql.append(" ?,");
		bufInsSql.append("  DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND)");
		bufInsSql.append(" ,?)");
		if (CommonFunction.checkNull(data.get(0).toString()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(data.get(0).toString().toUpperCase().trim());
		
		if (CommonFunction.checkNull(data.get(1).toString()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(data.get(1).toString());

		if (CommonFunction.checkNull(data.get(2).toString()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(data.get(2).toString().trim());

		if (CommonFunction.checkNull(data.get(3).toString()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(data.get(3).toString().trim());

		if (CommonFunction.checkNull(data.get(4).toString()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(data.get(4).toString().trim());
		
		if (CommonFunction.checkNull(data.get(5).toString()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(data.get(5).toString().trim());
		
		
		if (CommonFunction.checkNull(data.get(6).toString()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(data.get(6).toString());
		
		if (CommonFunction.checkNull(data.get(7).toString()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(data.get(7).toString());
		
		if (CommonFunction.checkNull(data.get(8).toString()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(data.get(8).toString());
		
		if (CommonFunction.checkNull(vo.getDecision()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getDecision());
		
		if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getMakerId().toString());
		
		
		if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getMakerDate().toString());

		if (CommonFunction.checkNull(vo.getComments()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getComments().toString());
		
		insertPrepStmtObject.setSql(bufInsSql.toString());

		logger.info("IN insertSblMaster() insert query1 ### "+ insertPrepStmtObject.printQuery());
		qryList.add(insertPrepStmtObject);
		status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
		logger.info("In insertSblMaster......................"+ status);
		
		
		bufInsSql3.append("delete from cr_sblGbl_tmp where loan_no='"+vo.getLoanId()+"' and disb_no='"+vo.getDisbursalId()+"'");		
		logger.info("query : "+bufInsSql3);
		insertPrepStmtObject1.setSql(bufInsSql3.toString());
		qryList1.add(insertPrepStmtObject1);
		status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList1);
		
		}else{
			 StringBuilder query1=new StringBuilder();
			 query1.append("update cr_sblGbl_tmp set rec_Status='"+vo.getDecision()+"',comments='"+vo.getComments()+"',author_id='"+vo.getMakerId()+"' where loan_no='"+vo.getLoanId()+"'");
			 insertPrepStmtObject.setSql(query1.toString());
			 logger.info("IN getApprovedRejectStatus() update query1 " + insertPrepStmtObject.printQuery());
			 qryList.add(insertPrepStmtObject);		
			 status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	finally
	{
		
	}
	return status;
}
public ArrayList<LoanDetailForCMVO> searchSblAuthorValues(String loanNo,String disbursalId) 
{
	StringBuffer bufInsSql = new StringBuffer();
	ArrayList searchlist = new ArrayList();
	ArrayList detailList =new ArrayList();
	try{
	bufInsSql.append("select loan_no,disb_no,sbl_master_limit,gbl_master_limit,sbl_capture_limit,gbl_capture_limit,remarks from cr_sblGbl_tmp  where loan_No='"+loanNo+"' and disb_no='"+disbursalId+"'");				
	logger.info("query : "+bufInsSql);
	searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());		
	
	for (int i = 0; i < searchlist.size(); i++) {

		ArrayList data = (ArrayList) searchlist.get(i);
		if (data.size() > 0) {
			LoanDetailForCMVO loanDetailForCMVO=new LoanDetailForCMVO();	
			
			loanDetailForCMVO.setLoanNo(CommonFunction.checkNull(data.get(0)).toString());
			loanDetailForCMVO.setDisbursalNo(CommonFunction.checkNull(data.get(1)).toString());
			loanDetailForCMVO.setSblMasterLimit(CommonFunction.checkNull(data.get(2)).toString());
			loanDetailForCMVO.setGblMasterLimit(CommonFunction.checkNull(data.get(3)).toString());
			loanDetailForCMVO.setCustCurrentPos(CommonFunction.checkNull(data.get(4)).toString());
			loanDetailForCMVO.setGroupPos(CommonFunction.checkNull(data.get(5)).toString());
			loanDetailForCMVO.setRemarks(CommonFunction.checkNull(data.get(6)).toString());
			
		detailList.add(loanDetailForCMVO);
		}
	}
	
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	finally
	{
		
	}
	return detailList;
}		
public ArrayList<LoanDetailForCMVO> searchSblGblAuthorValues(String loanId,String disbursalId) 
{
	StringBuffer bufInsSql = new StringBuffer();
	ArrayList searchlist = new ArrayList();
	ArrayList detailList =new ArrayList();
	try{
		bufInsSql.append("select loan_no,disb_no,sbl_master_limit,gbl_master_limit,sbl_capture_limit,gbl_capture_limit,remarks from cr_sblGbl_tmp where loan_no='"+loanId+"' and disb_no='"+disbursalId+"'");				
	logger.info("query : "+bufInsSql);
	searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());		
	
	for (int i = 0; i < searchlist.size(); i++) {

		ArrayList data = (ArrayList) searchlist.get(i);
		if (data.size() > 0) {
			LoanDetailForCMVO loanDetailForCMVO=new LoanDetailForCMVO();	
			
			loanDetailForCMVO.setLoanNo("<a href=sblGblMakerSearch.do?method=openSblGblValuesAuthor&loanNo="+data.get(0).toString()+"&disbursalId="+data.get(1).toString()+">"+(CommonFunction.checkNull(data.get(0)).trim()) +"</a>");
			loanDetailForCMVO.setDisbursalNo(CommonFunction.checkNull(data.get(1)).toString());
			/*loanDetailForCMVO.setSblGblMasterLimit(CommonFunction.checkNull(data.get(2)).toString());
			loanDetailForCMVO.setSblGblCapturedLimit(CommonFunction.checkNull(data.get(3)).toString());
			loanDetailForCMVO.setDiff(CommonFunction.checkNull(data.get(4)).toString());
			loanDetailForCMVO.setRemarks(CommonFunction.checkNull(data.get(5)).toString());*/
			loanDetailForCMVO.setSblMasterLimit(CommonFunction.checkNull(data.get(2)).toString());
			loanDetailForCMVO.setGblMasterLimit(CommonFunction.checkNull(data.get(3)).toString());
			loanDetailForCMVO.setCustCurrentPos(CommonFunction.checkNull(data.get(4)).toString());
			loanDetailForCMVO.setGroupPos(CommonFunction.checkNull(data.get(5)).toString());
			loanDetailForCMVO.setRemarks(CommonFunction.checkNull(data.get(6)).toString());
			
		detailList.add(loanDetailForCMVO);
		}
	}
	
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	finally
	{
		
	}
	return detailList;
}



}