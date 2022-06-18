/**
 * This class  is the DAO Impl for the Holiday Master
 * @author Vishal Singh
 * @Date  31 March 2012
 * 
 */
package com.masters.daoImplMYSQL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.masters.dao.HolidayMasterDAO;
import com.masters.vo.HolidayMasterVo;
import com.connect.*;
import java.text.DecimalFormat;
import org.apache.commons.lang.StringEscapeUtils;

import java.util.logging.Logger;

public class HolidayMasterDAOImpl implements HolidayMasterDAO  {
	private static final Logger logger = Logger.getLogger(HolidayMasterDAOImpl.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime = resource.getString("lbl.dateWithTimeInDao");
	String dateFormat = resource.getString("lbl.dateInDao");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	
	public boolean insertHolidayMaster(HolidayMasterVo ob) {
		HolidayMasterVo vo = (HolidayMasterVo) ob;
		boolean status = false;
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "A";
		logger
		.info("In insertholiday method of HolidayMasterDAOImpl--- status "
				+ vo.getHolidayStatus());
		try{
		String query = "select HOLIDAY_DATE from com_holiday_m where date(HOLIDAY_DATE)=STR_TO_DATE('"+StringEscapeUtils.escapeSql(vo.getHolidayDate()).trim()+"','"+dateFormat+"')";
	logger
			.info("In insertHolidayMaster............inside ejb server file..........Dao Impl"
					+ query);
	
	
	
		boolean st = ConnectionDAOforEJB.checkStatus(query);
	if(!st){
//		if (vo.getHolidayStatus() != null
//				&& vo.getHolidayStatus().equals("on")) {
//			stat = "A";
//		} else {
//			stat = "X";
//
//		}
		
		logger.info("In insert Holiday master");
		StringBuffer bufInsSql = new StringBuffer();
		bufInsSql
		.append("insert into com_holiday_m(HOLIDAY_DATE,HOLIDAY_DESC,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE,HOLIDAY_TYPE)");
		bufInsSql.append(" values ( ");
		bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),");
		bufInsSql.append(" ?,");
		bufInsSql.append(" ?,");		
		bufInsSql.append(" ?,");
		bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),");
		bufInsSql.append(" ?,");
		bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),");
		bufInsSql.append(" ?)");
		
		if (CommonFunction.checkNull(vo.getHolidayDate())
				.equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getHolidayDate()
					.toUpperCase().trim());

		if (CommonFunction.checkNull(vo.getHolidayDes())
				.equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getHolidayDes()
					.toUpperCase().trim());
		
//		if (CommonFunction.checkNull(vo.getHolidayStatus())
//				.equalsIgnoreCase(""))
//			insertPrepStmtObject.addNull();		
//		else
			insertPrepStmtObject.addString(stat);
		
		if (CommonFunction.checkNull(vo.getMakerId())
				.equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();		
		else
			insertPrepStmtObject.addString(vo.getMakerId()
					.toUpperCase().trim());
		
		if (CommonFunction.checkNull(vo.getMakerDate())
				.equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();		
		else
			insertPrepStmtObject.addString(vo.getMakerDate()
					.toUpperCase().trim());
		
		if (CommonFunction.checkNull(vo.getAuthorId())
				.equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();		
		else
			insertPrepStmtObject.addString(vo.getAuthorId()
					.toUpperCase().trim());
		
		if (CommonFunction.checkNull(vo.getAuthorDate())
				.equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();		
		else
			insertPrepStmtObject.addString(vo.getAuthorDate()
					.toUpperCase().trim());
		
		if (CommonFunction.checkNull(vo.getHolidayType())
				.equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();		
		else
			insertPrepStmtObject.addString(vo.getHolidayType()
					.toUpperCase().trim());
		
		insertPrepStmtObject.setSql(bufInsSql.toString());
		logger.info("IN insertHolidayMaster() insert query1 ### "
				+ insertPrepStmtObject.printQuery());
		qryList.add(insertPrepStmtObject);
		status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
		logger.info("In insertHolidayMaster  status......................"
				+ status);
		
	   }
	 }catch (Exception e) {
		e.printStackTrace();
	  }
	 return status;
	}
	
	
	public ArrayList searchHolidayData(HolidayMasterVo ob) {
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		String holidaySearchDate = "";
		String holidaySearchDes = "";
		String holidayId="";
		String holidayTypeSearch="";
		logger
		.info("In searchHolidayData method of HolidayMasterDAOImpl---  ");
		ArrayList searchlist = new ArrayList();
		HolidayMasterVo holidayMasterVo = (HolidayMasterVo) ob;
		ArrayList<HolidayMasterVo> detailList = new ArrayList<HolidayMasterVo>();
		try{
			holidayId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(holidayMasterVo.getHolidayId())).trim());
			holidaySearchDate = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(holidayMasterVo.getholidaySearchDate())).trim());
			holidaySearchDes = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(holidayMasterVo.getholidaySearchDes())).trim());
			
			holidayTypeSearch = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(holidayMasterVo.getHolidayTypeSearch())).trim());
			
			
			logger.info("***************** Value of Holiday Type **************** "+holidayTypeSearch);
			
			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			
			bufInsSql.append("SELECT HOLIDAY_ID,DATE_FORMAT(HOLIDAY_DATE,'"+dateFormat+"'),HOLIDAY_DESC,");
			bufInsSql.append("if(REC_STATUS='A','Active','Inactive')as REC_STATUS , HOLIDAY_TYPE");
			bufInsSql.append(" FROM com_holiday_m ");
			
			bufInsSqlTempCount.append("SELECT COUNT(1) FROM com_holiday_m ");
			
			if (!(holidaySearchDate.equals("")) && !(holidaySearchDes.equals("")) && !(holidayTypeSearch.equals(""))) {
				bufInsSql.append(" WHERE date(HOLIDAY_DATE)=STR_TO_DATE('"+holidaySearchDate+"','"+dateFormat+"')" +" AND HOLIDAY_DESC like '%" + holidaySearchDes + "%' " +"AND HOLIDAY_TYPE='"+holidayTypeSearch+"'");
				bufInsSqlTempCount.append(" WHERE date(HOLIDAY_DATE)=STR_TO_DATE('"+holidaySearchDate+"','"+dateFormat+"')"  + " AND HOLIDAY_DESC like '%" + holidaySearchDes + "%' "+"AND HOLIDAY_TYPE='"+holidayTypeSearch+"'");
			}
			
			else if (!holidayId.equals("")) {
				bufInsSql.append(" WHERE HOLIDAY_ID = '" + holidayId + "' ");
				bufInsSqlTempCount.append(" WHERE HOLIDAY_ID = '" + holidayId + "' ");
			}
			
			else if (!(holidaySearchDate.equals("")) && !(holidayTypeSearch.equals(""))) {
				bufInsSql.append(" WHERE date(HOLIDAY_DATE)=STR_TO_DATE('"+holidaySearchDate+"','"+dateFormat+"')" +" AND HOLIDAY_TYPE ='" + holidayTypeSearch +"'");
				bufInsSqlTempCount.append(" WHERE date(HOLIDAY_DATE)=STR_TO_DATE('"+holidaySearchDate+"','"+dateFormat+"')"  + "AND HOLIDAY_TYPE ='" + holidayTypeSearch +"'");
			}
			
			else if (!(holidaySearchDate.equals("")) && !(holidaySearchDes.equals(""))) {
				bufInsSql.append(" WHERE date(HOLIDAY_DATE)=STR_TO_DATE('"+holidaySearchDate+"','"+dateFormat+"')" +" AND HOLIDAY_DESC like '%" + holidaySearchDes + "%' ");
				bufInsSqlTempCount.append(" WHERE date(HOLIDAY_DATE)=STR_TO_DATE('"+holidaySearchDate+"','"+dateFormat+"')"  + " AND HOLIDAY_DESC like '%" + holidaySearchDes + "%' ");
			}
			
			else if (!(holidayTypeSearch.equals("")) && !(holidaySearchDes.equals(""))) {
				bufInsSql.append(" WHERE HOLIDAY_TYPE ='" + holidayTypeSearch +"' AND HOLIDAY_DESC like '%" + holidaySearchDes + "%' ");
				bufInsSqlTempCount.append(" WHERE HOLIDAY_TYPE ='" + holidayTypeSearch +"' AND HOLIDAY_DESC like '%" + holidaySearchDes + "%' ");
			}
			

			else if (!holidaySearchDate.equals("")) {
				bufInsSql.append("  WHERE date(HOLIDAY_DATE)=STR_TO_DATE('"+holidaySearchDate+"','"+dateFormat+"')");
				bufInsSqlTempCount.append(" WHERE date(HOLIDAY_DATE)=STR_TO_DATE('"+holidaySearchDate+"','"+dateFormat+"')");
			}
			else if (!holidaySearchDes.equals("")) {
				bufInsSql.append(" WHERE HOLIDAY_DESC like '%" + holidaySearchDes + "%' ");
				bufInsSqlTempCount.append(" WHERE HOLIDAY_DESC like '%" + holidaySearchDes + "%' ");
			}
			
			else if (!holidayTypeSearch.equals("")) {
				bufInsSql.append(" WHERE HOLIDAY_TYPE ='" + holidayTypeSearch + "' ");
				bufInsSqlTempCount.append("WHERE HOLIDAY_TYPE ='" + holidayTypeSearch + "'");
			}
				
				bufInsSql.append("ORDER BY HOLIDAY_ID");
				logger.info("search Query....*********************" + bufInsSql);
				logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
	            
				count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
				
				if((holidaySearchDate.trim()==null && holidaySearchDes.trim()==null) || (holidaySearchDate.trim().equalsIgnoreCase("") && holidaySearchDes.trim().equalsIgnoreCase("")) || holidayMasterVo.getCurrentPageLink()>1)
				{
				
				logger.info("current PAge Link no .................... "+holidayMasterVo.getCurrentPageLink());
				if(holidayMasterVo.getCurrentPageLink()>1)
				{
					startRecordIndex = (holidayMasterVo.getCurrentPageLink()-1)*no;
					endRecordIndex = no;
					logger.info("startRecordIndex .................... "+startRecordIndex);
					logger.info("endRecordIndex .................... "+endRecordIndex);
				}
				 bufInsSql.append(" limit "+startRecordIndex+","+ endRecordIndex);
				
				}
				logger.info("query : "+bufInsSql);

				searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
				
				logger.info("IN searchCountryData() search query1 ### "+ bufInsSql.toString());
				logger.info("In searchCountryData............inside ejb server file..........Dao Impl");
				logger.info("searchCountryData " + searchlist.size());
				
				for (int i = 0; i < searchlist.size(); i++) {
					// logger.info("searchCountryDataList "+searchlist.get(i).toString());

					ArrayList data = (ArrayList) searchlist.get(i);

					if (data.size() > 0) {
						HolidayMasterVo deptMVO = new HolidayMasterVo();
//						deptMVO.setHolidayIdModify("<a href=holidayMaintenance.do?method=openEditHoliday&holidayId="
//										+ CommonFunction.checkNull(data.get(0)).toString()
//										+ ">"
//										+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");
						deptMVO.setHolidayId(CommonFunction.checkNull(data.get(0)).toString());
						deptMVO.setHolidayDate(CommonFunction.checkNull(data.get(1)).toString());
						deptMVO.setHolidayDateModify("<a href=holidayMaintenance.do?method=openEditHoliday&holidayId="
								   + CommonFunction.checkNull(data.get(0)).toString()
								   + ">"
								   + CommonFunction.checkNull(data.get(1)).toString() + "</a>");
								   
						deptMVO.setHolidayDes(CommonFunction.checkNull(data.get(2)).toString());
						deptMVO.setHolidayStatus(CommonFunction.checkNull(data.get(3)).toString());
						deptMVO.setHolidayType(CommonFunction.checkNull(data.get(4)).toString());
						deptMVO.setTotalRecordSize(count);
						detailList.add(deptMVO);
					}

				}
			}catch(Exception e){
			e.printStackTrace();
		}
		
//		if(searchlist.size()==0)
//		{
//			HolidayMasterVo deptMVO = new HolidayMasterVo();
//			deptMVO.setTotalRecordSize(count);
//			detailList.add(deptMVO);
//			request.setAttribute("flag","yes");
//			logger.info("Detail List when searchList is null: "+detailList.size());
//		}
		
		return detailList;
	}
	
	public boolean updateHolidyData(HolidayMasterVo ob) {
	HolidayMasterVo vo = (HolidayMasterVo) ob;
	String holidayId = (String) vo.getHolidayId();
	logger.info("In updateHolidyData method of HolidayMasterDAOImpl--- holiday Status" + vo.getHolidayStatus());
	PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
	ArrayList updatelist = new ArrayList();
	//String selquery="select HOLIDAY_DATE from com_holiday_m where date(HOLIDAY_DATE)=STR_TO_DATE('"+vo.getHolidayDate()+"','"+dateFormat+"')";
	String selquery="select HOLIDAY_DATE from com_holiday_m where HOLIDAY_ID='"+vo.getHolidayId()+"'";
	 logger.info("In updateHolidayMaster...........inside ejb server file..........Dao Impl "+selquery);
	 boolean st = ConnectionDAOforEJB.checkStatus(selquery);
	boolean status = false;
	String stat = "";
	try {
		if(st){
			if (vo.getHolidayStatus() != null
					&& vo.getHolidayStatus().equals("on")) {
				
				stat = "A";
				
			} else {
				stat = "X";
				
			}
			
		
		
		StringBuffer bufInsSql = new StringBuffer();
		logger
				.info("In updateHolidayData...........inside ejb server file..........Dao Impl");
//		bufInsSql.append(" UPDATE com_holiday_m set HOLIDAY_DESC=?,");			
//		bufInsSql.append(" REC_STATUS=?,"
//				+ "HOLIDAY_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),MAKER_ID=?,MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) where HOLIDAY_ID=?");
		
		bufInsSql.append(" UPDATE com_holiday_m set REC_STATUS=?,");			
		bufInsSql.append("MAKER_ID=?,MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) where HOLIDAY_ID=?");
		
	/*	if (CommonFunction.checkNull(vo.getHolidayDes()).equalsIgnoreCase(""))
	       insertPrepStmtObject.addNull();
         else
	       insertPrepStmtObject.addString(vo.getHolidayDes().toUpperCase()
			.trim());*/
		
		
		if (CommonFunction.checkNull(vo.getHolidayStatus()).equalsIgnoreCase(""))
	       insertPrepStmtObject.addNull();
        else
	       insertPrepStmtObject.addString(stat);
		
		
		/* if (CommonFunction.checkNull(vo.getHolidayDate()).equalsIgnoreCase(
		""))
	       insertPrepStmtObject.addNull();
        else
	       insertPrepStmtObject.addString(vo.getHolidayDate().toUpperCase()
	   			.trim()); */
		
		
		if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
		""))
	       insertPrepStmtObject.addNull();
        else
	       insertPrepStmtObject.addString(vo.getMakerId().toUpperCase()
		   			.trim());
		
		
		if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(
		""))
	       insertPrepStmtObject.addNull();
        else
	       insertPrepStmtObject.addString(vo.getMakerDate().toUpperCase()
		   			.trim());
		
		
		
		if (CommonFunction.checkNull(vo.getHolidayId()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getHolidayId());
		
		
		
		insertPrepStmtObject.setSql(bufInsSql.toString());

		updatelist.add(insertPrepStmtObject);
		logger.info("In getListOfValues" + bufInsSql.toString());
		status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);
		
		}
		
	}catch(Exception e){
		e.printStackTrace();
	}
	
	return status;
	
	}
	
	
	public boolean searchForHolidayMaster(String holidayDate){		
		boolean flag=false;	
		String selquery="select HOLIDAY_DATE from com_holiday_m where date(HOLIDAY_DATE)=STR_TO_DATE('"+holidayDate+"','"+dateFormat+"')";
		 logger.info("In SearchHolidayMethod.....................................Dao "+selquery);
		 
		 try{
		  flag = ConnectionDAOforEJB.checkStatus(selquery);
		 }catch(Exception e){
			 logger.info("******* Exception in Holiday Search Query *****************"+selquery);
			 
		 }
		 return flag;
	}


	
	

}
	
	

	

