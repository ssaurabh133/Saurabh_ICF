package com.commonFunction.dao;

import java.util.ArrayList;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.commonFunction.vo.RefreshFlagVo;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;

public class RefreshFlagValueInsert {
	
	public static final Logger logger = Logger.getLogger(RefreshFlagValueInsert.class.getClass());
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
		{
			sqlQuery="SELECT REFRESH_FLAG FROM cr_loan_dtl WHERE LOAN_ID='"+vo.getRecordId()+"'";
		}
		else
		{
			sqlQuery="SELECT REFRESH_FLAG FROM cr_deal_dtl WHERE DEAL_ID='"+vo.getRecordId()+"'";
		}
		String refreshFlagValue = ConnectionDAO.singleReturn(sqlQuery);
		//logger.info("refreshFlagValue........................................"+refreshFlagValue);
		///logger.info("start index ............................... "+vo.getTabIndex());
				
		String [] str = new String[15];
		for(int i=0;i<refreshFlagValue.length();i++)
		{
			str[i]=refreshFlagValue.charAt(i)+"";
		}
	//logger.info("refreshFlagValue........................................"+str.toString());
		if(vo.getModuleName().equalsIgnoreCase("CP") && vo.getTabIndex()==11)
		{
			logger.info("8th CP tab.................................");
			str[10]="N";
			
		}
		else if(vo.getModuleName().equalsIgnoreCase("CP") && vo.getTabIndex()==2)
		{
			logger.info("2nd CP tab.................................");
			str[1]="N";
			str[5]="Y";
			str[10]="Y";
		}
		else if(vo.getModuleName().equalsIgnoreCase("CP") && vo.getTabIndex()==6)
		{
			if(vo.getNonInstallment()!=null && vo.getNonInstallment().equalsIgnoreCase("N"))
			{
			
			logger.info("6th CP tab.................................");
			str[5]="N";
			str[8]="N";
			}
			else
			{
				logger.info("6th CP tab.................................");
				str[5]="N";
				str[8]="Y";
			}
		}
		else if(vo.getModuleName().equalsIgnoreCase("CP") && vo.getTabIndex()==10)
		{
			logger.info("10th CP tab.................................");
			str[9]="N";
			str[10]="Y";
		}
		else if(vo.getModuleName().equalsIgnoreCase("CP") && vo.getTabIndex()==5)
		{
			if(vo.getNonInstallment()!=null && vo.getNonInstallment().equalsIgnoreCase("N"))
			{
				logger.info("5th CP tab.................................");
				str[4]="N";
				str[5]="Y";
				str[7]="N";
				str[8]="N";
			}
			else
			{
				logger.info("5th CP tab.................................");
				str[4]="N";
				str[5]="Y";
				str[7]="Y";
				str[8]="Y";
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
		else if(vo.getModuleName().equalsIgnoreCase("CM") && vo.getTabIndex()==2)
		{
			logger.info("2nd CM tab.................................");
			str[1]="N";
			str[2]="Y";
			str[6]="Y";
		}
		else if(vo.getModuleName().equalsIgnoreCase("CM") && vo.getTabIndex()==3)
		{
			if(vo.getNonInstallment()!=null && vo.getNonInstallment().equalsIgnoreCase("N"))
			{
			logger.info("3rd CM tab.................................");
			str[2]="N";
			str[5]="N";
			}
			else
			{
				logger.info("3rd CM tab.................................");
				str[2]="N";
				str[5]="Y";
			}
		}
		else if(vo.getModuleName().equalsIgnoreCase("CM") && vo.getTabIndex()==1)
		{
			if(vo.getNonInstallment()!=null && vo.getNonInstallment().equalsIgnoreCase("N"))
			{
				logger.info("1st CM tab.................................");
				str[0]="N";
				str[2]="N";
				str[4]="N";
				str[5]="N";
				str[7]="N";
			}
			else
			{
				logger.info("1st CM tab.................................");
				str[0]="N";
				str[2]="Y";
				str[4]="Y";
				str[5]="Y";
				str[7]="Y";
			}
			
		}
		else if(vo.getModuleName().equalsIgnoreCase("CM") && vo.getTabIndex()==4)
		{
			if(vo.getNonInstallment()!=null && vo.getNonInstallment().equalsIgnoreCase("N"))
			{
			logger.info("1st CM tab.................................");
			str[3]="N";
			str[5]="N";
			}
			else
			{
				logger.info("1st CM tab.................................");
				str[3]="N";
				str[5]="Y";
			}
		}
		else if(vo.getModuleName().equalsIgnoreCase("CM") && vo.getTabIndex()==5)
		{
			logger.info("5th CM tab.................................");
			str[4]="N";
			str[5]="Y";
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
		else if(vo.getModuleName().equalsIgnoreCase("CM") && vo.getTabIndex()==6)
		{
			logger.info("6th CM tab.................................");
			str[5]="N";
			
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
