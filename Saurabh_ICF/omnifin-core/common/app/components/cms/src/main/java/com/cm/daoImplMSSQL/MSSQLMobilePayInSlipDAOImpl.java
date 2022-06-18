package com.cm.daoImplMSSQL;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;



import com.cm.dao.MobilePayInSlipDAO;
import com.cm.daoImplMYSQL.MobilePayInSlipDAOImpl;
import com.cm.vo.MobilePayInSlipVo;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.ConnectionDAOforEJB;
import com.connect.PrepStmtObject;

public class MSSQLMobilePayInSlipDAOImpl implements MobilePayInSlipDAO {
	private static final Logger logger = Logger.getLogger(MobilePayInSlipDAOImpl.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime = resource.getString("lbl.dateWithTimeInDao");
	String dateFormat = resource.getString("lbl.dateInDao");
	String dbo=resource.getString("lbl.dbPrefix");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	Connection con = null;
	PreparedStatement ptmt = null;
	ResultSet rs = null;
	Statement stmt = null;
	CallableStatement csmt = null;
	@Override
	public ArrayList<MobilePayInSlipVo> mobilePayInSlipSearchDtl(MobilePayInSlipVo vo, String path)
	{
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		ArrayList list=new ArrayList();
		try
		{
			//String dealloanId = CommonFunction.minDealLoanId(loanId);
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			StringBuilder query=new StringBuilder();
			query.append("SELECT pay.ID, dtl.INSTRUMENT_ID, m.user_name,pay.LATITUDE,pay.LONGITUDE from stage_pay_in_slip_dtl pay"
					+" join stage_instrument_dtl dtl on ( INSTR(CONCAT('|',pay.RECEIPT_IDS,'|'),CONCAT('|',dtl.INSTRUMENT_ID,'|'))>0 and INSTRUMENT_TYPE='R') left join sec_user_m m on (m.user_id=dtl.MAKER_ID) "
					+" where dtl.DEFAULT_BRANCH='"+vo.getBranchId()+"'");
			
			bufInsSqlTempCount.append("SELECT count(1) from stage_pay_in_slip_dtl pay "
					+ "join stage_instrument_dtl dtl on ( INSTR(CONCAT('|',pay.RECEIPT_IDS,'|'),CONCAT('|',dtl.INSTRUMENT_ID,'|'))>0 and INSTRUMENT_TYPE='R') "
					+ "left join sec_user_m m on (m.user_id=dtl.MAKER_ID) where dtl.DEFAULT_BRANCH='"+vo.getBranchId()+"'");
			
			logger.info("search Query...." + query);
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
			
			count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));

			
			logger.info("current PAge Link no .................... "+vo.getCurrentPageLink());
			if(vo.getCurrentPageLink()>1)
			{
				startRecordIndex = (vo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			query.append(" limit "+startRecordIndex+","+endRecordIndex);
			
			logger.info("query : "+query);
			
			MobilePayInSlipVo mob= null;
			ArrayList mobilSlipDtl = ConnectionDAO.sqlSelect(query.toString());
			
			query=null;
			int size=mobilSlipDtl.size();
			for(int i=0;i<size;i++){
				
				ArrayList mobilSlipDtl1=(ArrayList)mobilSlipDtl.get(i);
				if(mobilSlipDtl1!=null && mobilSlipDtl1.size()>0)
				{
					mob = new MobilePayInSlipVo();
					mob.setMobilePayInSlipId((CommonFunction.checkNull(mobilSlipDtl1.get(0))).trim());
					mob.setInstrumentID((CommonFunction.checkNull(mobilSlipDtl1.get(1))).trim());
					mob.setMakerId((CommonFunction.checkNull(mobilSlipDtl1.get(2))).trim());
					mob.setLatitude((CommonFunction.checkNull(mobilSlipDtl1.get(3))).trim());
					mob.setLongitude((CommonFunction.checkNull(mobilSlipDtl1.get(4))).trim());
					list.add(mob);
					mob=null;
				}
				mobilSlipDtl1.clear();
				mobilSlipDtl1=null;
			}
			mobilSlipDtl.clear();
			mobilSlipDtl=null;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return list;
	}
	public ArrayList<MobilePayInSlipVo> mobilePayInSlipSearch(MobilePayInSlipVo vo, String path)
	{
		return null;
	}
	public ArrayList<MobilePayInSlipVo> mobileInstruementDtl(String mobileId)
	{
		return null;
	}
	public ArrayList<MobilePayInSlipVo> mobilePayInSlipPicture(String mobileId)
	{
		return null;
	}
}
