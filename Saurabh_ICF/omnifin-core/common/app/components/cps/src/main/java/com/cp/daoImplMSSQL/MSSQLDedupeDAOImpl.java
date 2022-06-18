package com.cp.daoImplMSSQL;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Logger;

import com.cm.actionform.ReportsForm;
import com.cm.vo.DedupeVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;
import com.cp.dao.DedupeDAO;
import com.cm.vo.DedupeVO;

public class MSSQLDedupeDAOImpl implements DedupeDAO 
{
	private static final Logger logger = Logger.getLogger(MSSQLDedupeDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	public ArrayList getDupRecord(DedupeVO vo) 
	{
		String customerID=CommonFunction.checkNull(vo.getCustomerID()).trim();
		String customerName=CommonFunction.checkNull(vo.getCustomerName()).trim();
		String panNo=CommonFunction.checkNull(vo.getPanNo()).trim();
		String regNo=CommonFunction.checkNull(vo.getRegNo()).trim();
		String dlNo=CommonFunction.checkNull(vo.getDlNo()).trim();
		String passportNo=CommonFunction.checkNull(vo.getPassportNo()).trim();
		String voterID=CommonFunction.checkNull(vo.getVoterID()).trim();
		String groupName=CommonFunction.checkNull(vo.getGroupName()).trim();
		String phNo=CommonFunction.checkNull(vo.getPhNo());
		if(!phNo.trim().equalsIgnoreCase(""))
		{
			if(phNo.contains(","))
				phNo=phNo.replace(",","|");
			phNo=phNo+"|";
		}
			
		
	
		String dealCustomer="";
		String loanCustomer="";
		String s1="";
		String s2="";
		ArrayList resultList =new ArrayList<Object>();
		ArrayList list =new ArrayList<Object>();
		
		logger.info("In getDupRecord()");
		ArrayList<Object> in =new ArrayList<Object>();
		ArrayList<Object> out =new ArrayList<Object>();
		ArrayList outMessages = new ArrayList();
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		StringBuilder bufInsSql =	new StringBuilder();
		StringBuffer bufInsSqlTempCount = new StringBuffer();
		
		try
		{
			in.add(customerID);
			in.add(customerName);
			in.add(panNo);
			in.add(regNo);
			in.add(dlNo);
			in.add(passportNo);
			in.add(voterID);
			in.add(phNo);
			in.add(groupName);
			out.add(dealCustomer);
			out.add(loanCustomer);			
			out.add(s1);
			out.add(s2);
			logger.info("deDupe('"+customerID+"','"+customerName+"','"+customerName+"','"+panNo+"','"+regNo+"','"+dlNo+"','"+passportNo+"','"+voterID+"','"+phNo+"','"+groupName+"','"+dealCustomer+"','"+loanCustomer+"','"+s1+"','"+s2+"'");
			
			outMessages=(ArrayList) ConnectionDAO.callSP("deDupe",in,out);
			dealCustomer=CommonFunction.checkNull(outMessages.get(0));
			loanCustomer=CommonFunction.checkNull(outMessages.get(1));
			if(dealCustomer.trim().length()==0)
			{
				dealCustomer="0";
			}
			if(loanCustomer.trim().length()==0)
			{
				loanCustomer="0";
			}
			s1=CommonFunction.checkNull(outMessages.get(2));
			s2=CommonFunction.checkNull(outMessages.get(3));
			logger.info("dealCustomer      : "+dealCustomer);
			logger.info("loanCustomer      : "+loanCustomer);
	        logger.info("s1          : "+s1);
	        logger.info("s2          : "+s2);
	        
	        if(dealCustomer.trim().length()>0 || loanCustomer.trim().length()>0)
	        {
	        	char dealCustLastChar=dealCustomer.charAt(dealCustomer.length()-1);
	        	char loanCustLastChar=loanCustomer.charAt(loanCustomer.length()-1);
	            if(dealCustLastChar==',')
	               {
	        	   dealCustomer=dealCustomer.substring(0,dealCustomer.length()-1);
	                }
	            if(loanCustLastChar==',')
	               {
	        	   loanCustomer=loanCustomer.substring(0,loanCustomer.length()-1);
	               }
	        }
	        logger.info("dealCustomer   After  subString::"+dealCustomer);
			logger.info("loanCustomer   After  subString::"+loanCustomer);
			
	        if(s1.equalsIgnoreCase("E"))
	           	resultList.add(s2);
	        else
	        {
	        	resultList.add("");
	        	bufInsSql.append("select DISTINCT  a.deal_id,b.deal_no,dl.DEAL_LOAN_AMOUNT ,case b.REC_STATUS when 'A' " +
	        			" then 'APPROVED ' when 'X' then iif((select count(1) from cr_deal_movement_dtl where REC_STATUS='A' " +
	        			" and deal_id=b.DEAL_ID)>0,'REJECTED ','CANCELLED ')when 'P' then 'PENDING 'when 'F' then 'FORWARDED 'end as deal_STATUS," +
	        			" CASE a.DEAL_CUSTOMER_ROLE_TYPE WHEN 'PRAPPL' THEN 'APPLICANT' WHEN 'COAPPL' THEN 'CO-APPLICANT' WHEN 'GUARANTOR' THEN 'GUARANTOR' END AS CUST_TYPE," +
	        			" a.deal_customer_id,c.customer_name,CUSTMER_PAN,CUSTOMER_REGISTRATION_NO,DRIVING_LICENSE,PASSPORT_NUMBER,VOTER_ID," +
	        			" (SELECT STUFF((SELECT ',' + convert(varchar(10),primary_phone) FROM cr_deal_address_m where bpid=c.customer_id FOR XML PATH ('')),1,1,''))phone," +
	        			" l.loan_no,l.loan_loan_amount,dbo.date_format(l.LOAN_REPAY_EFF_DATE,'%d-%m-%Y')LOAN_REPAY_EFF_DATE,dbo.date_format(l.LOAN_MATURITY_DATE,'%d-%m-%Y')LOAN_MATURITY_DATE,case l.rec_status when 'P' then 'PENDING' when 'A' then 'ACTIVE' when 'L' then 'CANCELLED' when 'X' then 'REJECTED' when 'C' then 'CLOSED' end as loan_status," +
	        			" case l.DISBURSAL_STATUS when 'P' then 'PARTIALLY DISBURSED' when 'F' then 'FULLY DISBURSED' when 'N' then 'NOT DISBURSED' END AS DISBURSAL_STATUS,LOAN_BALANCE_PRINCIPAL,LOAN_OVERDUE_PRINCIPAL,LOAN_DPD,gr.GROUP_DESC_PRINT " +
	        			" from cr_deal_customer_role a  " +
	        			" join cr_deal_dtl b on(a.deal_id=b.deal_id) " +
	        			" join cr_deal_loan_dtl dl on(dl.deal_id=b.deal_id) " +
	        			" join cr_deal_customer_m c on(c.customer_id=a.deal_customer_id) " +
	        			" LEFT JOIN (SELECT GROUP_ID,GROUP_DESC GROUP_DESC_PRINT,REPLACE(GROUP_DESC,' ','')GROUP_DESC FROM gcd_group_m)gr ON(c.CUSTOMER_GROUP_ID IS NOT NULL AND c.CUSTOMER_GROUP_ID=gr.GROUP_ID)"+
	        			" LEFT JOIN cr_loan_dtl l ON(l.loan_deal_id=b.deal_id)" +
	        			" where a.deal_customer_id in("+dealCustomer+")");
//	        			"order by deal_id OFFSET 0 ROWS FETCH next 10 ROWS ONLY " +
//	        			" where a.deal_customer_id in("+dealCustomer+")");
	        	if(customerID.trim().equalsIgnoreCase(""))
	        	{
	        		bufInsSql.append("   union  " + 
	        		        " select DISTINCT  b.deal_id,b.deal_no,dl.DEAL_LOAN_AMOUNT ,case b.REC_STATUS when 'A'  then 'APPROVED ' when 'X' then iif((select count(1) " +
	        		        " from cr_deal_movement_dtl where REC_STATUS='A'  and deal_id=b.DEAL_ID)>0,'REJECTED ','CANCELLED ')when 'P' then 'PENDING 'when" +
	        		        " 'F' then 'FORWARDED 'end as deal_STATUS,CASE a.LOAN_CUSTOMER_ROLE_TYPE WHEN 'PRAPPL' THEN 'APPLICANT' WHEN 'COAPPL' THEN" +
	        		        " 'CO-APPLICANT' WHEN 'GUARANTOR' THEN 'GUARANTOR' END AS CUST_TYPE, a.gcd_id,c.customer_name,CUSTMER_PAN,CUSTOMER_REGISTRATION_NO," +
	        		        " DRIVING_LICENSE,PASSPORT_NUMBER,VOTER_ID, (SELECT STUFF((SELECT ',' + convert(varchar(10),primary_phone) FROM cr_deal_address_m where bpid=c.customer_id FOR XML PATH ('')),1,1,''))phone," +
	        		        " l.loan_no,l.loan_loan_amount,dbo.date_format(l.LOAN_REPAY_EFF_DATE,'%d-%m-%Y')LOAN_REPAY_EFF_DATE,dbo.date_format(l.LOAN_MATURITY_DATE,'%d-%m-%Y')LOAN_MATURITY_DATE," +
	        		        " case l.rec_status when 'P' then 'PENDING' when 'A' then 'ACTIVE' when 'L' then 'CANCELLED' when 'X' then 'REJECTED' when 'C' then 'CLOSED' end as loan_status ," +
	        		        " case l.DISBURSAL_STATUS when 'P' then 'PARTIALLY DISBURSED' when 'F' then 'FULLY DISBURSED' when 'N' then 'NOT DISBURSED' END AS DISBURSAL_STATUS,LOAN_BALANCE_PRINCIPAL,LOAN_OVERDUE_PRINCIPAL,LOAN_DPD,gr.GROUP_DESC_PRINT  " +
		        			" from cr_loan_customer_role a" +  
		        			" JOIN cr_loan_dtl l ON(l.loan_id=a.loan_id)" + 
		        			" join cr_deal_dtl b on(l.loan_deal_id=b.deal_id)" +  
		        			" join cr_deal_loan_dtl dl on(dl.deal_id=b.deal_id)" + 
		        			" join gcd_customer_m c on(c.customer_id=a.gcd_id)" + 
		        			" LEFT JOIN (SELECT GROUP_ID,GROUP_DESC GROUP_DESC_PRINT,REPLACE(GROUP_DESC,' ','')GROUP_DESC FROM gcd_group_m)gr ON(c.GROUP_ID IS NOT NULL AND c.GROUP_ID=gr.GROUP_ID)"+
		        			" where a.gcd_id in("+loanCustomer+")");
	        		
        	}
	        	bufInsSql.append(" order by deal_id,deal_customer_id");
	        			 
	           	bufInsSqlTempCount.append("select count(1) from ( " +
	           			"select DISTINCT  a.deal_id,b.deal_no,dl.DEAL_LOAN_AMOUNT ,case b.REC_STATUS when 'A' " +
	        			" then 'APPROVED ' when 'X' then iif((select count(1) from cr_deal_movement_dtl where REC_STATUS='A' " +
	        			" and deal_id=b.DEAL_ID)>0,'REJECTED ','CANCELLED ')when 'P' then 'PENDING 'when 'F' then 'FORWARDED 'end as deal_STATUS," +
	        			" CASE a.DEAL_CUSTOMER_ROLE_TYPE WHEN 'PRAPPL' THEN 'APPLICANT' WHEN 'COAPPL' THEN 'CO-APPLICANT' WHEN 'GUARANTOR' THEN 'GUARANTOR' END AS CUST_TYPE," +
	        			" a.deal_customer_id,c.customer_name,CUSTMER_PAN,CUSTOMER_REGISTRATION_NO,DRIVING_LICENSE,PASSPORT_NUMBER,VOTER_ID," +
	        			" (SELECT STUFF((SELECT ',' + convert(varchar(10),primary_phone) FROM cr_deal_address_m where bpid=c.customer_id FOR XML PATH ('')),1,1,''))phone," +
	        			" l.loan_no,l.loan_loan_amount,dbo.date_format(l.LOAN_REPAY_EFF_DATE,'%d-%m-%Y')LOAN_REPAY_EFF_DATE,dbo.date_format(l.LOAN_MATURITY_DATE,'%d-%m-%Y')LOAN_MATURITY_DATE,case l.rec_status when 'P' then 'PENDING' when 'A' then 'ACTIVE' when 'L' then 'CANCELLED' when 'X' then 'REJECTED' when 'C' then 'CLOSED' end as loan_status," +
	        			" case l.DISBURSAL_STATUS when 'P' then 'PARTIALLY DISBURSED' when 'F' then 'FULLY DISBURSED' when 'N' then 'NOT DISBURSED' END AS DISBURSAL_STATUS,LOAN_BALANCE_PRINCIPAL,LOAN_OVERDUE_PRINCIPAL,LOAN_DPD,gr.GROUP_DESC_PRINT " +
	        			" from cr_deal_customer_role a  " +
	        			" join cr_deal_dtl b on(a.deal_id=b.deal_id) " +
	        			" join cr_deal_loan_dtl dl on(dl.deal_id=b.deal_id) " +
	        			" join cr_deal_customer_m c on(c.customer_id=a.deal_customer_id) " +
	        			" LEFT JOIN (SELECT GROUP_ID,GROUP_DESC GROUP_DESC_PRINT,REPLACE(GROUP_DESC,' ','')GROUP_DESC FROM gcd_group_m)gr ON(c.CUSTOMER_GROUP_ID IS NOT NULL AND c.CUSTOMER_GROUP_ID=gr.GROUP_ID)"+
	        			" LEFT JOIN cr_loan_dtl l ON(l.loan_deal_id=b.deal_id)" +
	        			" where a.deal_customer_id in("+dealCustomer+")");
	        	if(customerID.trim().equalsIgnoreCase(""))
	        	{
	        		bufInsSqlTempCount.append("   union  " + 
	        				    " select DISTINCT  b.deal_id,b.deal_no,dl.DEAL_LOAN_AMOUNT ,case b.REC_STATUS when 'A'  then 'APPROVED ' when 'X' then iif((select count(1) " +
		        		        " from cr_deal_movement_dtl where REC_STATUS='A'  and deal_id=b.DEAL_ID)>0,'REJECTED ','CANCELLED ')when 'P' then 'PENDING 'when" +
		        		        " 'F' then 'FORWARDED 'end as deal_STATUS,CASE a.LOAN_CUSTOMER_ROLE_TYPE WHEN 'PRAPPL' THEN 'APPLICANT' WHEN 'COAPPL' THEN" +
		        		        " 'CO-APPLICANT' WHEN 'GUARANTOR' THEN 'GUARANTOR' END AS CUST_TYPE, a.gcd_id,c.customer_name,CUSTMER_PAN,CUSTOMER_REGISTRATION_NO," +
		        		        " DRIVING_LICENSE,PASSPORT_NUMBER,VOTER_ID, (SELECT STUFF((SELECT ',' + convert(varchar(10),primary_phone) FROM cr_deal_address_m where bpid=c.customer_id FOR XML PATH ('')),1,1,''))phone," +
		        		        " l.loan_no,l.loan_loan_amount,dbo.date_format(l.LOAN_REPAY_EFF_DATE,'%d-%m-%Y')LOAN_REPAY_EFF_DATE,dbo.date_format(l.LOAN_MATURITY_DATE,'%d-%m-%Y')LOAN_MATURITY_DATE," +
		        		        " case l.rec_status when 'P' then 'PENDING' when 'A' then 'ACTIVE' when 'L' then 'CANCELLED' when 'X' then 'REJECTED' when 'C' then 'CLOSED' end as loan_status ," +
		        		        " case l.DISBURSAL_STATUS when 'P' then 'PARTIALLY DISBURSED' when 'F' then 'FULLY DISBURSED' when 'N' then 'NOT DISBURSED' END AS DISBURSAL_STATUS,LOAN_BALANCE_PRINCIPAL,LOAN_OVERDUE_PRINCIPAL,LOAN_DPD,gr.GROUP_DESC_PRINT  " +
			        			" from cr_loan_customer_role a" +  
			        			" JOIN cr_loan_dtl l ON(l.loan_id=a.loan_id)" + 
			        			" join cr_deal_dtl b on(l.loan_deal_id=b.deal_id)" +  
			        			" join cr_deal_loan_dtl dl on(dl.deal_id=b.deal_id)" + 
			        			" join gcd_customer_m c on(c.customer_id=a.gcd_id)" + 
			        			" LEFT JOIN (SELECT GROUP_ID,GROUP_DESC GROUP_DESC_PRINT,REPLACE(GROUP_DESC,' ','')GROUP_DESC FROM gcd_group_m)gr ON(c.GROUP_ID IS NOT NULL AND c.GROUP_ID=gr.GROUP_ID)"+
			        			" where a.gcd_id in("+loanCustomer+")");
	        			
	        	}
	        	bufInsSqlTempCount.append(" )b ");    	        		        	
	        	
	        	logger.info("current PAge Link no .................... "+vo.getCurrentPageLink());
				if(vo.getCurrentPageLink()>1)
				{
					startRecordIndex = (vo.getCurrentPageLink()-1)*no;
					endRecordIndex = no;
					logger.info("startRecordIndex .................... "+startRecordIndex);
					logger.info("endRecordIndex .................... "+endRecordIndex);
				}
				bufInsSql.append(" OFFSET  "+startRecordIndex+" ROWS FETCH next "+endRecordIndex+" ROWS ONLY");
				
				logger.info("in getDupRecord() Search Query  :  "+bufInsSql.toString());
				logger.info("in getDupRecord() Count Query  :  "+bufInsSqlTempCount.toString());			
				count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
			    ArrayList formatlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
                for (int i=0;i<formatlist.size();i++)
                {
					ArrayList data = (ArrayList) formatlist.get(i);
					DedupeVO dvo= new DedupeVO();   
					if(customerID.trim().equalsIgnoreCase(""))
						dvo.setRadioButton("<input type='radio' name='chk' id='chk' disabled='disabled' value="+CommonFunction.checkNull(data.get(5)).toString()+" />");
					else
						dvo.setRadioButton("<input type='radio' name='chk' id='chk' value="+CommonFunction.checkNull(data.get(5)).toString()+" />");
					
					dvo.setDupDealID(CommonFunction.checkNull(data.get(0)).toString());
					dvo.setDupDealNO(CommonFunction.checkNull(data.get(1)).toString());
					if(!(CommonFunction.checkNull(data.get(2))).trim().equalsIgnoreCase(""))	
					{
						Number reconNum =myFormatter.parse((CommonFunction.checkNull(data.get(2))).trim());
						dvo.setDealAmount(myFormatter.format(reconNum));
					}
					else
						dvo.setDealAmount("0.00");
					dvo.setDealStatus(CommonFunction.checkNull(data.get(3)).toString());
					dvo.setDupCustomerRole(CommonFunction.checkNull(data.get(4)).toString());
					dvo.setDupCustomerID(CommonFunction.checkNull(data.get(5)).toString());
					dvo.setDupCustomerName(CommonFunction.checkNull(data.get(6)).toString());
					dvo.setDupPanNo(CommonFunction.checkNull(data.get(7)).toString());
					dvo.setDupRegNo(CommonFunction.checkNull(data.get(8)).toString());
					dvo.setDupDlNo(CommonFunction.checkNull(data.get(9)).toString());
					dvo.setDupPassportNo(CommonFunction.checkNull(data.get(10)).toString());
					dvo.setDupVoterID(CommonFunction.checkNull(data.get(11)).toString());
					dvo.setDupPhNo(CommonFunction.checkNull(data.get(12)).toString());
					dvo.setLoanNo(CommonFunction.checkNull(data.get(13)).toString());
					if(!(CommonFunction.checkNull(data.get(14))).trim().equalsIgnoreCase(""))	
					{
						Number reconNum =myFormatter.parse((CommonFunction.checkNull(data.get(14))).trim());
						dvo.setLoanAmount(myFormatter.format(reconNum));
					}
					else
						dvo.setLoanAmount("");
					dvo.setLoanEffDate(CommonFunction.checkNull(data.get(15)).toString());
					dvo.setLoanEndDate(CommonFunction.checkNull(data.get(16)).toString());
					dvo.setLoanStatus(CommonFunction.checkNull(data.get(17)).toString());
					dvo.setDisbursalStatus(CommonFunction.checkNull(data.get(18)).toString());
					if(!(CommonFunction.checkNull(data.get(19))).trim().equalsIgnoreCase(""))	
					{
						Number reconNum =myFormatter.parse((CommonFunction.checkNull(data.get(19))).trim());
						dvo.setBlsPrincipal(myFormatter.format(reconNum));
					}
					else
						dvo.setBlsPrincipal("");
					if(!(CommonFunction.checkNull(data.get(20))).trim().equalsIgnoreCase(""))	
					{
						Number reconNum =myFormatter.parse((CommonFunction.checkNull(data.get(20))).trim());
						dvo.setOverduePrincipal(myFormatter.format(reconNum));
					}
					else
						dvo.setOverduePrincipal("");
					dvo.setLoanDPD(CommonFunction.checkNull(data.get(21)).toString());	
					dvo.setGroupName(CommonFunction.checkNull(data.get(22)).toString());
					dvo.setTotalRecordSize(count);
					list.add(dvo);				
				}
                resultList.add(list);
	        }
	    }
		catch (Exception e) 
		{
			resultList.add("Some Exception occur,Please contact Administrator....");
			e.printStackTrace();
		}			
		return resultList;
	}
	public ArrayList getDefltCustDtl(String customerID) 
	{
		logger.info("In getDefltCustDtl()");
		ArrayList list = new ArrayList();
		try 
		{		
			String query ="SELECT CUSTMER_PAN ,CUSTOMER_REGISTRATION_NO,DRIVING_LICENSE,PASSPORT_NUMBER,VOTER_ID,(SELECT STUFF((SELECT ',' + convert(varchar(10),primary_phone) FROM cr_deal_address_m where bpid=customer_id FOR XML PATH ('')),1,1,''))phone,B.GROUP_DESC_PRINT FROM cr_deal_customer_m A LEFT JOIN (SELECT GROUP_ID,GROUP_DESC GROUP_DESC_PRINT,REPLACE(GROUP_DESC,' ','')GROUP_DESC FROM gcd_group_m)B ON(A.CUSTOMER_GROUP_ID IS NOT NULL AND A.CUSTOMER_GROUP_ID=B.GROUP_ID) WHERE CUSTOMER_ID="+customerID;		
			logger.info("In getDefltCustDtl() Query    :   "+query);		
			ArrayList formatlist = ConnectionDAO.sqlSelect(query.toString());
			ArrayList data = (ArrayList) formatlist.get(0);
			DedupeVO vo= new DedupeVO();
			vo.setPanNo(CommonFunction.checkNull(data.get(0)).toString());
			vo.setRegNo(CommonFunction.checkNull(data.get(1)).toString());
			vo.setDlNo(CommonFunction.checkNull(data.get(2)).toString());
			vo.setPassportNo(CommonFunction.checkNull(data.get(3)).toString());
			vo.setVoterID(CommonFunction.checkNull(data.get(4)).toString());
			vo.setPhNo(CommonFunction.checkNull(data.get(5)).toString());
			vo.setGroupName(CommonFunction.checkNull(data.get(6)).toString());
			list.add(vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public boolean replaceCustomerID(DedupeVO vo) 
	{
		logger.info("in replaceCustomerID().");
		boolean status = false;
		ArrayList qryList = new ArrayList();
		StringBuilder queryUpdate=new StringBuilder();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String dealID=vo.getDealID();
		String customerRole=vo.getCustomerRole();
		String dupCustomerID=vo.getDupCustomerID();
		if(CommonFunction.checkNull(customerRole).trim().equalsIgnoreCase("APPLICANT"))
			customerRole="PRAPPL";
		else if(CommonFunction.checkNull(customerRole).trim().equalsIgnoreCase("CO-APPLICANT"))
			customerRole="COAPPL";
		else if(CommonFunction.checkNull(customerRole).trim().equalsIgnoreCase("GUARANTOR"))
			customerRole="GUARANTOR";
		if(CommonFunction.checkNull(customerRole).trim().equalsIgnoreCase("PRAPPL")){
			//queryUpdate.append("update cr_deal_dtl a,cr_deal_customer_role b set a.DEAL_CUSTOMER_ID='"+dupCustomerID+"',b.DEAL_CUSTOMER_ID='"+dupCustomerID+"' where a.deal_id=b.deal_id and a.DEAL_CUSTOMER_ID=b.DEAL_CUSTOMER_ID and DEAL_CUSTOMER_ROLE_TYPE='PRAPPL' and a.deal_id='"+dealID+"'");
			queryUpdate.append(" update cr_deal_dtl set DEAL_CUSTOMER_ID='"+dupCustomerID+"' where   deal_id='"+dealID+"' ");
		    queryUpdate.append(" update cr_deal_customer_role set DEAL_CUSTOMER_ID='"+dupCustomerID+"' where  DEAL_CUSTOMER_ROLE_TYPE='PRAPPL' and deal_id='"+dealID+"' ");
		}
		else
			queryUpdate.append("update cr_deal_customer_role set deal_customer_id='"+dupCustomerID+"' where DEAL_CUSTOMER_ROLE_TYPE='"+CommonFunction.checkNull(customerRole).trim()+"' and deal_id='"+dealID+"'");
		
		insertPrepStmtObject.setSql(queryUpdate.toString());
		logger.info("update query   :  "+insertPrepStmtObject.printQuery());
		qryList.add(insertPrepStmtObject);
		try
		{
			status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			logger.info("Update status    :    "+status);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return status;		
	}
}
