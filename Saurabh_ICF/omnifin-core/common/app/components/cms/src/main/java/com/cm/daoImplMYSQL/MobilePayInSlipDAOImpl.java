package com.cm.daoImplMYSQL;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;





import com.cm.dao.MobilePayInSlipDAO;
import com.cm.vo.MobilePayInSlipVo;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.ConnectionDAOforEJB;
import com.connect.PrepStmtObject;


public class MobilePayInSlipDAOImpl implements MobilePayInSlipDAO {

	private static final Logger logger = Logger.getLogger(MobilePayInSlipDAOImpl.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime = resource.getString("lbl.dateWithTimeInDao");
	//String dateFormat = resource.getString("lbl.dateInDao"); 
	String dateFormat = "%d-%M-%Y";
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
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			StringBuilder query=new StringBuilder();
			query.append("SELECT PAY.ID,PAY.PAY_IN_SLIP_NO,GM.DESCRIPTION, M.USER_NAME,DATE_FORMAT(PAY.MAKER_DATE,'"+ dateFormat +"') AS MAKER_DATE FROM STAGE_PAY_IN_SLIP_DTL PAY"
					+" JOIN SEC_USER_M M ON (M.USER_ID=PAY.MAKER_ID AND )"
					+" JOIN GENERIC_MASTER GM ON (GM.GENERIC_KEY='MOB_PAYMENT_MODE' AND PAY.RECEIPT_MODE=GM.VALUE AND GM.PARENT_VALUE='COLL')"
					+" WHERE GM.GENERIC_KEY='MOB_PAYMENT_MODE' AND PAY.MAKER_ID='"+CommonFunction.checkNull(vo.getMakerId())+"'");
			
			bufInsSqlTempCount.append("SELECT COUNT(1) FROM STAGE_PAY_IN_SLIP_DTL PAY"
					+" JOIN SEC_USER_M M ON (M.USER_ID=PAY.MAKER_ID)"
					+" JOIN GENERIC_MASTER GM ON (GM.GENERIC_KEY='MOB_PAYMENT_MODE' AND PAY.RECEIPT_MODE=GM.VALUE AND GM.PARENT_VALUE='COLL')"
					+" WHERE GM.GENERIC_KEY='MOB_PAYMENT_MODE' AND PAY.MAKER_ID='"+CommonFunction.checkNull(vo.getMakerId())+"'");
			
			logger.info("searDESCRIPTIONch Query...." + query);
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
				mob=null;
				ArrayList mobilSlipDtl1=(ArrayList)mobilSlipDtl.get(i);
				if(mobilSlipDtl1!=null && mobilSlipDtl1.size()>0)
				{
					mob = new MobilePayInSlipVo();
					mob.setMobilePayInSlipId((CommonFunction.checkNull(mobilSlipDtl1.get(0))).trim());
					mob.setPayInSlipNo("<a href=\"#\" onclick=\"openMobileInstruementDtl('"+((CommonFunction.checkNull(mobilSlipDtl1.get(0))).trim())+"');\" >"+((CommonFunction.checkNull(mobilSlipDtl1.get(1))).trim())+"</a>");
					mob.setRecipetMode((CommonFunction.checkNull(mobilSlipDtl1.get(2))).trim());
					mob.setMakerName((CommonFunction.checkNull(mobilSlipDtl1.get(3))).trim());
					mob.setMakerDate((CommonFunction.checkNull(mobilSlipDtl1.get(4))).trim());
					mob.setImageLink("<a href=\"#\" onclick=\"openMobilePayInSlipPhotos('"+((CommonFunction.checkNull(mobilSlipDtl1.get(0))).trim())+"');\" ><img style=\"cursor:pointer\" src=\""+path+"/images/theme1/invoiceDownload1.png\" width=\"18\" height=\"18\">"+"</a>");
					mob.setTotalRecordSize(count);
					list.add(mob);
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
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		ArrayList list=new ArrayList();
		try
		{
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			StringBuilder query=new StringBuilder();
			query.append("SELECT PAY.ID,PAY.PAY_IN_SLIP_NO,GM.DESCRIPTION, M.USER_NAME,DATE_FORMAT(PAY.MAKER_DATE,'"+ dateFormat +"') AS MAKER_DATE"
					+" FROM STAGE_PAY_IN_SLIP_DTL PAY"
					+" JOIN GENERIC_MASTER GM ON (GM.GENERIC_KEY='MOB_PAYMENT_MODE' AND PAY.RECEIPT_MODE=GM.VALUE AND GM.PARENT_VALUE='COLL')"
					+" JOIN SEC_USER_M M ON (M.USER_ID=PAY.MAKER_ID) WHERE GM.GENERIC_KEY='MOB_PAYMENT_MODE'");
					
					if(!StringUtils.isBlank(vo.getPayFromDate())){
						query.append(" AND DATE(PAY.MAKER_DATE)<='"+vo.getPayFromDate()+"'");
					}
					if(!StringUtils.isBlank(vo.getPayToDate())){
						query.append(" AND DATE(PAY.MAKER_DATE)>='"+vo.getPayToDate()+"'");
					}
					if(!StringUtils.isBlank(vo.getLbxUserId())){
						query.append(" and PAY.MAKER_ID='"+vo.getLbxUserId()+"'");
					}
				
			bufInsSqlTempCount.append("SELECT COUNT(1) FROM STAGE_PAY_IN_SLIP_DTL PAY"
					+" JOIN GENERIC_MASTER GM ON (GM.GENERIC_KEY='MOB_PAYMENT_MODE' AND PAY.RECEIPT_MODE=GM.VALUE AND GM.PARENT_VALUE='COLL')"
					+" JOIN SEC_USER_M M ON (M.USER_ID=PAY.MAKER_ID) WHERE GM.GENERIC_KEY='MOB_PAYMENT_MODE'");
					
					if(!StringUtils.isBlank(vo.getPayFromDate())){
						bufInsSqlTempCount.append(" AND DATE(PAY.MAKER_DATE)<='"+vo.getPayFromDate()+"'");
					}
					if(!StringUtils.isBlank(vo.getPayToDate())){
						bufInsSqlTempCount.append(" AND DATE(PAY.MAKER_DATE)>='"+vo.getPayToDate()+"'");
					}
					if(!StringUtils.isBlank(vo.getLbxUserId())){
						bufInsSqlTempCount.append(" and PAY.MAKER_ID='"+vo.getLbxUserId()+"'");
					}
			
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
				mob=null;
				ArrayList mobilSlipDtl1=(ArrayList)mobilSlipDtl.get(i);
				if(mobilSlipDtl1!=null && mobilSlipDtl1.size()>0)
				{
					mob = new MobilePayInSlipVo();
					mob.setMobilePayInSlipId((CommonFunction.checkNull(mobilSlipDtl1.get(0))).trim());
					mob.setPayInSlipNo("<a href=\"#\" onclick=\"openMobileInstruementDtl('"+((CommonFunction.checkNull(mobilSlipDtl1.get(0))).trim())+"');\" >"+((CommonFunction.checkNull(mobilSlipDtl1.get(1))).trim())+"</a>");
					mob.setRecipetMode((CommonFunction.checkNull(mobilSlipDtl1.get(2))).trim());
					mob.setMakerName((CommonFunction.checkNull(mobilSlipDtl1.get(3))).trim());
					mob.setMakerDate((CommonFunction.checkNull(mobilSlipDtl1.get(4))).trim());
					//mob.setImageLink("<a href=\"#\" onclick=\"openMobilePayInSlipPhotos('"+((CommonFunction.checkNull(mobilSlipDtl1.get(0))).trim())+"');\" >"+((CommonFunction.checkNull(mobilSlipDtl1.get(0))).trim())+"</a>");
					mob.setImageLink("<a href=\"#\" onclick=\"openMobilePayInSlipPhotos('"+((CommonFunction.checkNull(mobilSlipDtl1.get(0))).trim())+"');\" ><img style=\"cursor:pointer\" src=\""+path+"/images/theme1/invoiceDownload1.png\" width=\"18\" height=\"18\">"+"</a>");
					mob.setTotalRecordSize(count);
					list.add(mob);
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

	public ArrayList<MobilePayInSlipVo> mobileInstruementDtl(String mobileId)
	{
		ArrayList list=new ArrayList();
		try 
		{
			StringBuilder query=new StringBuilder();
			query.append("SELECT PAY.PAY_IN_SLIP_NO, INSTRUMENT_ID, GM.DESCRIPTION, DATE_FORMAT(DTL.RECEIVED_DATE,'"+ dateFormat +"') AS RECEIVED_DATE,"
					+" DATE_FORMAT(DTL.INSTRUMENT_DATE,'"+ dateFormat +"') AS INSTRUMENT_DATE,"
					+" ROUND(INSTRUMENT_AMOUNT,2) AS INSTRUMENT_AMOUNT, M.USER_NAME, DATE_FORMAT(DTL.MAKER_DATE,'"+ dateFormat +"') AS MAKER_DATE" 
					+" FROM STAGE_INSTRUMENT_DTL DTL JOIN STAGE_PAY_IN_SLIP_DTL PAY ON ( INSTR(CONCAT('|',PAY.RECEIPT_IDS,'|'),CONCAT('|',DTL.INSTRUMENT_ID,'|'))>0 AND INSTRUMENT_TYPE='R')" 
					+" JOIN GENERIC_MASTER GM ON (GM.GENERIC_KEY='MOB_PAYMENT_MODE' AND DTL.INSTRUMENT_MODE=GM.VALUE AND GM.PARENT_VALUE='COLL')"
					+" LEFT JOIN SEC_USER_M M ON (M.USER_ID=DTL.MAKER_ID)  WHERE GM.GENERIC_KEY='MOB_PAYMENT_MODE' AND PAY.ID='"+CommonFunction.checkNull(mobileId)+"'");
			logger.info("search Query...." + query);
			MobilePayInSlipVo mob= null;
			ArrayList mobilSlipDtl = ConnectionDAO.sqlSelect(query.toString());
			
			query=null;
			int size=mobilSlipDtl.size();
			for(int i=0;i<size;i++){
				mob=null;
				ArrayList mobilSlipDtl1=(ArrayList)mobilSlipDtl.get(i);
				if(mobilSlipDtl1!=null && mobilSlipDtl1.size()>0)
				{
					mob = new MobilePayInSlipVo();
					mob.setPayInSlipId((CommonFunction.checkNull(mobilSlipDtl1.get(0))).trim());
					mob.setInstrumentID((CommonFunction.checkNull(mobilSlipDtl1.get(1))).trim());
					mob.setInstrumentMode((CommonFunction.checkNull(mobilSlipDtl1.get(2))).trim());
					mob.setReceivedDate((CommonFunction.checkNull(mobilSlipDtl1.get(3))).trim());
					mob.setInstrumentDate((CommonFunction.checkNull(mobilSlipDtl1.get(4))).trim());
					mob.setInstrumentAmount((CommonFunction.checkNull(mobilSlipDtl1.get(5))).trim());
					mob.setMakerName((CommonFunction.checkNull(mobilSlipDtl1.get(6))).trim());
					mob.setMakerDate((CommonFunction.checkNull(mobilSlipDtl1.get(7))).trim());
					list.add(mob);
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
	public ArrayList<MobilePayInSlipVo> mobilePayInSlipPicture(String mobileId)
	{
		ArrayList list=new ArrayList();
		try
		{
			StringBuilder query=new StringBuilder();
			query.append("SELECT Id, M.USER_NAME, RECEIPT_IMAGE" 
					+" FROM STAGE_PAY_IN_SLIP_DTL PAY" 
					+" LEFT JOIN SEC_USER_M M ON (M.USER_ID=PAY.MAKER_ID)  WHERE PAY.ID='"+CommonFunction.checkNull(mobileId)+"'");
			logger.info("search Query...." + query);
			MobilePayInSlipVo mob= null;
			ArrayList mobilSlipDtl = ConnectionDAO.sqlSelect(query.toString());
			
			query=null;
			int size=mobilSlipDtl.size();
			for(int i=0;i<size;i++){
				mob=null;
				ArrayList mobilSlipDtl1=(ArrayList)mobilSlipDtl.get(i);
				if(mobilSlipDtl1!=null && mobilSlipDtl1.size()>0)
				{
					mob = new MobilePayInSlipVo();
					mob.setMobilePayInSlipId((CommonFunction.checkNull(mobilSlipDtl1.get(0))).trim());
					mob.setMakerName((CommonFunction.checkNull(mobilSlipDtl1.get(1))).trim());
					mob.setPhotoStream((CommonFunction.checkNull(mobilSlipDtl1.get(2))).trim());
					list.add(mob);
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
}
