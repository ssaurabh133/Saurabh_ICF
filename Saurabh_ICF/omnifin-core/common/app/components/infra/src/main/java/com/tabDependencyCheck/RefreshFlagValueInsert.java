package com.tabDependencyCheck;

import java.util.ArrayList;


import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.cm.dao.CreditManagementDAO;
import com.cm.dao.LoanInitiationDAO;

import com.connect.DaoImplInstanceFactory;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;


public class RefreshFlagValueInsert {
	
	private static final Logger logger = Logger.getLogger(RefreshFlagValueInsert.class.getName());
	
	public static boolean insertRefreshFlag(RefreshFlagVo vo)
	{
		logger.info("RefreshFlagValueInsert, insertRefreshFlag method ...");
		boolean status=false;
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject=new PrepStmtObject();
		String qryUpdate="";
		if( vo.getModuleName()!=null && vo.getModuleName().equalsIgnoreCase("CM"))
		{
			logger.info("in CM Module...................................");
			qryUpdate = "Update cr_loan_dtl set REFRESH_FLAG=? WHERE LOAN_ID=?";
		}
		else
		{
			logger.info("in CP Module...................................");
			qryUpdate = "Update cr_deal_dtl set REFRESH_FLAG=? WHERE DEAL_ID=?";
		}
		
		if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getFlagValue())).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getFlagValue()).trim());
		
		if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getRecordId())).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getRecordId()+"").trim());
		
		insertPrepStmtObject.setSql(qryUpdate);
		qryList.add(insertPrepStmtObject);
		try
		{
			logger.info("query : "+ insertPrepStmtObject.printQuery());
			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return status;
	}
	
	public static boolean updateRefreshFlag(RefreshFlagVo vo)
	{
		logger.info("RefreshFlagValueInsert, updateRefreshFlag method ...");
		String sqlQuery="";
		if( vo.getModuleName()!=null && vo.getModuleName().equalsIgnoreCase("CM"))
			sqlQuery="SELECT REFRESH_FLAG FROM cr_loan_dtl WHERE LOAN_ID='"+vo.getRecordId()+"'";
		else
			sqlQuery="SELECT REFRESH_FLAG FROM cr_deal_dtl WHERE DEAL_ID='"+vo.getRecordId()+"'";
		
		String refreshFlagValue = ConnectionDAO.singleReturn(sqlQuery);
		String [] str = new String[16];
		for(int i=0;i<refreshFlagValue.length();i++)
			str[i]=refreshFlagValue.charAt(i)+"";
		
		if(vo.getModuleName().equalsIgnoreCase("CP") && vo.getTabIndex()==11)
		{
			logger.info("8th CP tab.................................");
			str[10]="N";
			
		}
		else if(vo.getModuleName().equalsIgnoreCase("CP") && vo.getTabIndex()==2)
		{
			logger.info("2nd CP tab.................................");
			str[1]="N";
			String costomerID=vo.getCostomerID();
			String custType=vo.getCustomerType();
			if(CommonFunction.checkNull(custType).trim().equalsIgnoreCase("PRAPPL"))
			{
				int c1=0;
				int c2=0;
				int c3=0;
				String q1="select count(1) from cr_deal_loan_dtl l,com_charges_m p where l.DEAL_PRODUCT=p.PRODUCT_ID and l.DEAL_SCHEME=p.scheme_id and DEF_STAGE_ID='DC'  and p.rec_status='A' and l.deal_id='"+vo.getRecordId()+"'";
				logger.info(" Query To find charges defined on product scheme combination or not : "+q1.toString());
				c1 = Integer.parseInt(ConnectionDAO.singleReturn(q1.toString()));
				
				String q3="select count(1) from cr_insurance_dtl a join cr_deal_dtl b on b.deal_id=a.deal_id join cr_deal_customer_m c on b.deal_customer_id= c.customer_id where a.deal_id= '"+vo.getRecordId()+"'";
				logger.info(" Query To find details of deal_id in insurance table corresponding to loan table : "+q3.toString());
				c3 = Integer.parseInt(ConnectionDAO.singleReturn(q3.toString()));
				if(c1==0) 
				{
					String q2="select count(*) from cr_deal_loan_dtl l,com_charges_m p where l.DEAL_PRODUCT=p.PRODUCT_ID  and p.rec_status='A' AND DEF_STAGE_ID='DC' and l.deal_id='"+vo.getRecordId()+"'";
					logger.info(" Query To find charges defined on product  : "+q2.toString());
					c2 = Integer.parseInt(ConnectionDAO.singleReturn(q2.toString()));
				}
	 			if(c1 !=0 || c2 != 0)	
				{
	 				str[5]="Y";//for charges
				}
	 			if (c3==0)
				{
	 				str[15]="N";
				}
	 			else {

					str[15]="Y";
					str[5]="Y";
	 			}
			}
			//Logic for Documents
			String deleteFlag=vo.getDeleteFlag();
			if(!CommonFunction.checkNull(deleteFlag).trim().equalsIgnoreCase("Y"))
			{
				String query="select CUSTOMER_CONSTITUTION from cr_deal_customer_m where CUSTOMER_ID='"+costomerID+"'";
				logger.info(" Query To fetch CUSTOMER_CONSTITUTION  : "+query);
				ArrayList list=new ArrayList();
				ArrayList subList=new ArrayList();
				int ct=0;
				try
				{
					list =ConnectionDAO.sqlSelect(query);
					for(int i=0;i<list.size();i++)
					{
						subList=(ArrayList)list.get(i);
						String constitution=(String)subList.get(0);
						String docChQuery="select count(1) from cr_document_checklist_m where DOC_SATGE='PRS' and DOC_ENTITY_TYPE='"+CommonFunction.checkNull(custType).trim()+"' and REC_STATUS='A' and DOC_CONSTITUTION='"+constitution+"'";
						logger.info(" Document check query  : "+docChQuery);
						ct=Integer.parseInt(ConnectionDAO.singleReturn(docChQuery));
						if(ct>0)
						{
							str[10]="Y";
							break;
						} 					
					} 						
				}
				catch (Exception e) 
				{e.printStackTrace();}
			}
		}
		else if(vo.getModuleName().equalsIgnoreCase("CP") && vo.getTabIndex()==6)
		{
			logger.info("6th CP tab.................................");
			str[5]="N";
			if(vo.getNonInstallment()!=null && vo.getNonInstallment().equalsIgnoreCase("N"))
				str[8]="N";
			else
				str[8]="Y";
		}
		else if(vo.getModuleName().equalsIgnoreCase("CP") && vo.getTabIndex()==10)
		{
			logger.info("10th CP tab.................................");
			str[9]="N";
			int c1=0;
			int c2=0;
			String q1="select count(1) from cr_deal_loan_dtl l,com_charges_m p where l.DEAL_PRODUCT=p.PRODUCT_ID and l.DEAL_SCHEME=p.scheme_id and DEF_STAGE_ID='DC'  and p.rec_status='A' and l.deal_id='"+vo.getRecordId()+"'";
			logger.info(" Query To find charges defined on product scheme combination or not : "+q1.toString());
			c1 = Integer.parseInt(ConnectionDAO.singleReturn(q1.toString()));
			if(c1==0) 
			{
				String q2="select count(*) from cr_deal_loan_dtl l,com_charges_m p where l.DEAL_PRODUCT=p.PRODUCT_ID  and p.rec_status='A' AND DEF_STAGE_ID='DC' and l.deal_id='"+vo.getRecordId()+"'";
				logger.info(" Query To find charges defined on product  : "+q2.toString());
				c2 = Integer.parseInt(ConnectionDAO.singleReturn(q2.toString()));
			}
 			if(c1 !=0 || c2 != 0)	
				str[5]="Y";//for charges
 			
 			//logic for asset
 			String query="select ASSET_TYPE,ASSET_COLLATERAL_CLASS from cr_asset_collateral_m where ASSET_ID in(select ASSETID from cr_deal_collateral_m where DEAL_ID='"+vo.getRecordId()+"')";
 			logger.info(" Query To fetch ASSET_TYPE,ASSET_COLLATERAL_CLASS  : "+query);
 			ArrayList list=new ArrayList();
 			ArrayList subList=new ArrayList();
 			int ct=0;
 			try
 			{
 				list =ConnectionDAO.sqlSelect(query);
 				for(int i=0;i<list.size();i++)
 				{
 					subList=(ArrayList)list.get(i);
 					String type=(String)subList.get(0);
 					String aClass=(String)subList.get(1);
 					String docChQuery="select count(1) from cr_document_checklist_m where DOC_SATGE='PRS' and DOC_ENTITY_TYPE='"+type.trim()+"' and DOC_ASSET_CLASS='"+aClass.trim()+"' and REC_STATUS='A'";
 					logger.info(" Document check query  : "+docChQuery);
 					ct=Integer.parseInt(ConnectionDAO.singleReturn(docChQuery));
 					if(ct>0)
 					{
 						str[10]="Y";
 						break;
 					} 					
 				} 						
 			}
 			catch (Exception e) 
 			{e.printStackTrace();}
			
		}
		else if (vo.getModuleName().equalsIgnoreCase("CP") && vo.getTabIndex()==16)
		{
			logger.info("16th CP tab........");
			str[15]="N";//for insurance
			str[5]="Y";
			
		}
		else if(vo.getModuleName().equalsIgnoreCase("CP") && vo.getTabIndex()==5)
		{
			logger.info("5th CP tab.................................");
			str[4]="N";
			int c1=0;
			int c2=0;
			int c3=0;
			String q1="select count(1) from cr_deal_loan_dtl l,com_charges_m p where l.DEAL_PRODUCT=p.PRODUCT_ID and l.DEAL_SCHEME=p.scheme_id and DEF_STAGE_ID='DC'  and p.rec_status='A' and l.deal_id='"+vo.getRecordId()+"'";
			logger.info(" Query To find charges defined on product scheme combination or not : "+q1.toString());
			c1 = Integer.parseInt(ConnectionDAO.singleReturn(q1.toString()));
			String q3="select count(1) from cr_insurance_dtl a left join cr_deal_dtl b on b.deal_id=a.deal_id where a.deal_id='"+vo.getRecordId()+"'";
			logger.info(" Query To find details of deal_id in insurance table corresponding to loan table : "+q3.toString());
			c3 = Integer.parseInt(ConnectionDAO.singleReturn(q3.toString()));

			if(c1==0) 
			{
				String q2="select count(*) from cr_deal_loan_dtl l,com_charges_m p where l.DEAL_PRODUCT=p.PRODUCT_ID  and p.rec_status='A' AND DEF_STAGE_ID='DC' and l.deal_id='"+vo.getRecordId()+"'";
				logger.info(" Query To find charges defined on product  : "+q2.toString());
				c2 = Integer.parseInt(ConnectionDAO.singleReturn(q2.toString()));
			}
 			if(c1 !=0 || c2 != 0)	
			{
 				str[5]="Y";//for charges
			}
			if(vo.getNonInstallment()!=null && vo.getNonInstallment().equalsIgnoreCase("N"))
			{
				
				
				str[7]="N";
				str[8]="N";
			}
			else
			{
				str[7]="Y";
				str[8]="Y";
			}

			if (c3==0){
				str[15]="N";
				str[5]="Y";
			}
			else
			{
				str[15]="Y";
				str[5]="Y";
			}
			
		}
		
		else if(vo.getModuleName().equalsIgnoreCase("CP") && vo.getTabIndex()==8)
		{
			logger.info("8th CP tab.................................");
			str[7]="N";
			str[8]="Y";
		}
		else if(vo.getModuleName().equalsIgnoreCase("CP") && vo.getTabIndex()==9)
		{
			logger.info("9th CP tab.................................");
			str[8]="N";
		}
		else if(vo.getModuleName().equalsIgnoreCase("CP") && vo.getTabIndex()==7)
		{
			if(vo.getNonInstallment()!=null && vo.getNonInstallment().equalsIgnoreCase("N"))
			{
			logger.info("7th CP tab.................................");
			str[6]="N";
			str[8]="N";
			}
			else
			{
				logger.info("7th CP tab.................................");
				str[6]="N";
				str[8]="Y";
			}
		}
		else if(vo.getModuleName().equalsIgnoreCase("CM") && vo.getTabIndex()==1)
		{
			//aman starts
			int c3=0;
			
			String q3="select count(1) from cr_insurance_dtl a left join cr_loan_dtl b on b.loan_id=a.loan_id where a.loan_id='"+vo.getRecordId()+"'";
			logger.info(" Query To find details of loan_id in insurance table corresponding to loan table : "+q3.toString());
			c3 = Integer.parseInt(ConnectionDAO.singleReturn(q3.toString()));
			
			if (c3==0){
				str[15]="N";
				
			}
			else
			{
				str[15]="Y";
				str[5]="Y";
			}
			
			
			//aman ends
			logger.info("1st CM tab.................................");
			str[0]="N";
			int ch=0,sc=0;
			
			String q="select count(1) from cr_txncharges_dtl where txn_type='LIM' and txn_id="+vo.getRecordId();
			logger.info("query to find Charges  :   "+q);
			ch=Integer.parseInt(ConnectionDAO.singleReturn(q));			
			if(ch>0)
			{
				String f=CommonFunction.editableFlag();
                if(f!=null && f.equalsIgnoreCase("Y"))
                      str[2]="Y";
                else
                      str[2]="N";

			}
			  
			else
				 str[2]="N";
			
			String securityQuery="select count(1) from cr_deal_sd_m where deal_id=(select LOAN_DEAL_ID from cr_loan_dtl where loan_id='"+vo.getRecordId()+"')";
			logger.info("Security check query  :  "+securityQuery);
			sc=Integer.parseInt(ConnectionDAO.singleReturn(securityQuery));
			if(sc>0){
				LoanInitiationDAO dao=(LoanInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(LoanInitiationDAO.IDENTITY);
			     logger.info("Implementation class: "+dao.getClass());
				//LoanInitiationDAO dao = new LoanInitiationDAOImpl();
				ArrayList tenureAmount=dao.getTenureAmount(""+vo.getRecordId());
		    	logger.info("Size of tenureAmount: "+tenureAmount.size());			
			  if(tenureAmount!=null && tenureAmount.size()!=0)
				    str[3]="Y";
			  else
					str[3]="N";
			}
			else
				str[3]="N";
			
			
			if(vo.getNonInstallment()!=null && vo.getNonInstallment().equalsIgnoreCase("I"))
			{
				str[4]="Y";
				str[5]="Y";
				String f=CommonFunction.editableDisbursalScheduleFlag();
                if(f!=null && CommonFunction.checkNull(f).equalsIgnoreCase("Y"))
                      str[7]="Y";
                else
                      str[7]="N";
						
			}
			else
			{
				str[4]="N";
				str[5]="N";
				str[7]="N";
			}			
		}
		else if(vo.getModuleName().equalsIgnoreCase("CM") && vo.getTabIndex()==2)
		{
			logger.info("2nd CM tab.................................");
			str[1]="N";
			str[6]="Y";
			int ch=0;			
			String q="select count(1) from cr_txncharges_dtl where txn_type='LIM' and txn_id="+vo.getRecordId();
			logger.info("query to find Charges  :   "+q);
			ch=Integer.parseInt(ConnectionDAO.singleReturn(q));			
			if(ch>0)
			{
				String f=CommonFunction.editableFlag();
                if(f!=null && f.equalsIgnoreCase("Y"))
                      str[2]="Y";
                else
                      str[2]="N";

			}
			else
				 str[2]="N";
		}
		else if(vo.getModuleName().equalsIgnoreCase("CM") && vo.getTabIndex()==3)
		{
			logger.info("3rd CM tab.................................");
			str[2]="N";
			int sc=0;
			String securityQuery="select count(1) from cr_deal_sd_m where deal_id=(select LOAN_DEAL_ID from cr_loan_dtl where loan_id='"+vo.getRecordId()+"')";
			logger.info("Security check query  :  "+securityQuery);
			sc=Integer.parseInt(ConnectionDAO.singleReturn(securityQuery));
			if(sc>0){
				LoanInitiationDAO dao=(LoanInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(LoanInitiationDAO.IDENTITY);
			     logger.info("Implementation class: "+dao.getClass());
				 // LoanInitiationDAO dao = new LoanInitiationDAOImpl();
			     ArrayList tenureAmount=dao.getTenureAmount(""+vo.getRecordId());
	    	     logger.info("Size of tenureAmount: "+tenureAmount.size());			
		           if(tenureAmount!=null && tenureAmount.size()!=0)
			          str[3]="Y";
		               else
				       str[3]="N";
			}
			else
				str[3]="N";
			
			if(vo.getNonInstallment()!=null && vo.getNonInstallment().equalsIgnoreCase("I"))
				str[5]="Y";
			else
				str[5]="N";
		}
		else if(vo.getModuleName().equalsIgnoreCase("CM") && vo.getTabIndex()==4)
		{
			logger.info("1st CM tab.................................");
			str[3]="N";		
			if(vo.getNonInstallment()!=null && vo.getNonInstallment().equalsIgnoreCase("I"))
				str[5]="Y";			
		}
		else if (vo.getModuleName().equalsIgnoreCase("CM") && vo.getTabIndex()==16)
		{
			logger.info("16th CM tab........");
			str[15]="N";
			str[2]="Y";
			str[7]="Y";
			
		}
		else if(vo.getModuleName().equalsIgnoreCase("CM") && vo.getTabIndex()==5)
		{
			logger.info("5th CM tab.................................");
			str[4]="N";	
			str[5]="Y";
		}
		else if(vo.getModuleName().equalsIgnoreCase("CM") && vo.getTabIndex()==6)
		{
			logger.info("6th CM tab.................................");
			str[5]="N";
		}
		else if(vo.getModuleName().equalsIgnoreCase("CM") && vo.getTabIndex()==7)
		{
			logger.info("7th CM tab.................................");
			str[6]="N";			
		}
		else if(vo.getModuleName().equalsIgnoreCase("CM") && vo.getTabIndex()==8)
		{
			logger.info("8th CM tab.................................");
			str[7]="N";			
		}
		else if(vo.getModuleName().equalsIgnoreCase("CM") && vo.getTabIndex()==10)
		{
			logger.info("10th CM tab.................................");
			String costomerID=vo.getCostomerID();
			str[9]="N";
			String custType=vo.getCustomerType();
			if(CommonFunction.checkNull(custType).trim().equalsIgnoreCase("PRAPPL"))
			{
				int c3=0;
				String q3="select count(1) from cr_insurance_dtl a join CR_LOAN_DTL b on b.loan_id=a.loan_id join GCD_CUSTOMER_M c  on c.CUSTOMER_ID= b.LOAN_CUSTOMER_ID  where a.loan_id='"+vo.getRecordId()+"'";
				logger.info(" Query To find details of loan_id in insurance table corresponding to loan table : "+q3.toString());
				c3 = Integer.parseInt(ConnectionDAO.singleReturn(q3.toString()));

				int c1=0;
				int c2=0;
				String q1="select count(1) from cr_loan_dtl l,com_charges_m p where l.LOAN_PRODUCT=p.PRODUCT_ID and l.LOAN_SCHEME=p.scheme_id and DEF_STAGE_ID='LIM'  and p.rec_status='A' and l.loan_id='"+vo.getRecordId()+"'";
				logger.info(" Query To find charges defined on product scheme combination or not : "+q1.toString());
				c1 = Integer.parseInt(ConnectionDAO.singleReturn(q1.toString()));
				if(c1==0) 
				{
					String q2="select count(*) from cr_loan_dtl l,com_charges_m p where l.LOAN_PRODUCT=p.PRODUCT_ID  and p.rec_status='A' AND DEF_STAGE_ID='LIM' and l.loan_id='"+vo.getRecordId()+"'";
					logger.info(" Query To find charges defined on product  : "+q2.toString());
					c2 = Integer.parseInt(ConnectionDAO.singleReturn(q2.toString()));
				}
	 			if(c1 !=0 || c2 != 0)	
				{
	 				String f=CommonFunction.editableFlag();
                    if(f!=null && f.equalsIgnoreCase("Y"))
                          str[2]="Y";
                    else
                          str[2]="N";

				}	
	 			if (c3==0)
				{
	 				str[15]="N";
				}
	 			else {

					str[15]="Y";
					str[2]="Y";
	 			}
			}
			//Logic for Documents
			String query="select CUSTOMER_CONSTITUTION from gcd_customer_m where CUSTOMER_ID='"+costomerID+"'";
 			logger.info(" Query To fetch CUSTOMER_CONSTITUTION  : "+query);
 			ArrayList list=new ArrayList();
 			ArrayList subList=new ArrayList();
 			int ct=0;
 			try
 			{
 				list =ConnectionDAO.sqlSelect(query);
 				for(int i=0;i<list.size();i++)
 				{
 					subList=(ArrayList)list.get(i);
 					String constitution=(String)subList.get(0);
 					String docChQuery="select count(1) from cr_document_checklist_m where DOC_SATGE='PRD' and DOC_ENTITY_TYPE='"+CommonFunction.checkNull(custType).trim()+"' and REC_STATUS='A' and DOC_CONSTITUTION='"+constitution+"'";
 					logger.info(" Document check query  : "+docChQuery);
 					ct=Integer.parseInt(ConnectionDAO.singleReturn(docChQuery));
 					if(ct>0)
 					{
 						str[6]="Y";
 						break;
 					} 					
 				} 						
 			}
 			catch (Exception e) 
 			{e.printStackTrace();}
		}
		refreshFlagValue="";
		for(int i=0;i<str.length;i++)
		{
			refreshFlagValue=refreshFlagValue+str[i];
		}
		logger.info("flag value after change .........................."+refreshFlagValue);
		vo.setFlagValue(refreshFlagValue);
		vo.setRecordId(vo.getRecordId());
		boolean status = insertRefreshFlag(vo);
		return status;
	}
	
	
}
