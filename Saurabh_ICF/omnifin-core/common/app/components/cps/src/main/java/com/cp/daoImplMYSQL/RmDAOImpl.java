package com.cp.daoImplMYSQL;

import java.util.ArrayList;
import java.util.ResourceBundle;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;
import com.cp.dao.RmDAO;
import com.cp.vo.RmChangeVo;

public class RmDAOImpl implements RmDAO {
	
	private static final Logger logger = Logger.getLogger(RmDAOImpl.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	
	public ArrayList<RmChangeVo> searchRmData(Object ob) {
		
		String rmName=null;
		String roName=null;
		String makerName=null;
		ArrayList searchlist = new ArrayList();
		RmChangeVo rmChangeVo = (RmChangeVo) ob;
		ArrayList<RmChangeVo> detailList = new ArrayList<RmChangeVo>();
		ArrayList data=null;
		String deaId=null;
		String rm=null;
		String maker=null;
		String ro=null;

		try {
			logger.info("In searchRmData().....................................Dao Impl");
			 deaId = rmChangeVo.getLbxDealNo();
			
			 rm = rmChangeVo.getRm();
			
			if(rm.isEmpty())
				rmChangeVo.setLbxRM("");
			
			rmName = rmChangeVo.getLbxRM();
			
			//Existing RM
			 ro = rmChangeVo.getRo();
			
			if(ro.isEmpty())
				rmChangeVo.setLbxRO("");
			
			roName = rmChangeVo.getLbxRO();
			 maker = rmChangeVo.getMaker();
			
			if(maker.isEmpty())
				rmChangeVo.setLbxMaker("");
			
			makerName = rmChangeVo.getLbxMaker();
			
			StringBuffer bufInsSql = new StringBuffer();
			bufInsSql.append("select a.DEAL_NO,d.CUSTOMER_NAME,c.USER_NAME ,a.DEAL_ID,b.DEAL_ID,ifnull((select user_name from SEC_USER_M where user_id=b.NEW_RM),(select user_name from SEC_USER_M where user_id=b.EXISTING_RM))as new_rm,P.product_desc,S.scheme_desc,b.REC_STATUS, sm.USER_NAME,st.USER_NAME,ifnull((select user_name from SEC_USER_M where user_id=b.NEW_RO),(select user_name from SEC_USER_M where user_id=b.EXISTING_RO))as new_ro,ifnull((select user_name from SEC_USER_M where user_id=b.NEW_MAKER),(select user_name from SEC_USER_M where user_id=b.EXISTING_MAKER))as new_maker from cr_deal_dtl a ");
			bufInsSql.append("left outer join cr_deal_loan_dtl e on(a.DEAL_ID = e.DEAL_ID) left outer join cr_product_m P on(P.product_id = e.DEAL_PRODUCT) left outer join cr_scheme_m S on(S.scheme_id = e.DEAL_SCHEME) ");
			bufInsSql.append("left join cr_rm_change_dtl b on (a.DEAL_ID=b.DEAL_ID) left join cr_deal_customer_m d on (a.DEAL_CUSTOMER_ID=d.CUSTOMER_ID) left join SEC_USER_M c on (a.DEAL_RM=c.USER_ID) left join SEC_USER_M sm on (a.DEAL_RO=sm.USER_ID)left join SEC_USER_M st on (a.MAKER_ID=st.USER_ID)  where a.REC_STATUS='A' and a.DEAL_ID not in(select DEAL_ID from cr_rm_change_dtl where REC_STATUS='F')");

			if (!CommonFunction.checkNull(deaId).trim().equalsIgnoreCase(""))
				bufInsSql.append(" and  a.DEAL_ID='"+deaId.trim()+"'");
			if (!CommonFunction.checkNull(rmName).trim().equalsIgnoreCase(""))
				bufInsSql.append(" and  a.DEAL_RM='"+rmName.trim()+"'");
			if (!CommonFunction.checkNull(roName).trim().equalsIgnoreCase(""))
				bufInsSql.append(" and  a.DEAL_RO='"+roName.trim()+"'");
			if (!CommonFunction.checkNull(makerName).trim().equalsIgnoreCase(""))
				bufInsSql.append(" and  a.MAKER_ID='"+makerName.trim()+"'");
			
			logger.info("IN searchRmData() search query1 ### "+ bufInsSql.toString());
			searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
			bufInsSql=null;
			
			int totalRecordSize = searchlist.size();
			
			rmChangeVo.setTotalRecordSize(totalRecordSize);
			for (int i = 0; i < totalRecordSize; i++) {
				 data = (ArrayList) searchlist.get(i);
				
				if (data.size() > 0) {
					 rmChangeVo = new RmChangeVo();
					 rmChangeVo.setDealNo("<a href=# onclick="+"showPopUp("+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(CommonFunction.checkNull(data.get(3)))).trim()+")"+" >"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(0))) +"</a>");
					 rmChangeVo.setCustomerName(CommonFunction.checkNull(data.get(1)).toString());
					 rmChangeVo.setRmName(CommonFunction.checkNull(data.get(2)).toString());
					 rmChangeVo.setDealId(CommonFunction.checkNull(data.get(3)).toString());
					 rmChangeVo.setDealIdForCheckBox(CommonFunction.checkNull(data.get(4)).toString());
					if((CommonFunction.checkNull(data.get(8)).toString()).equalsIgnoreCase("A"))
					{
						rmChangeVo.setDealIdForCheckBox("");
						rmChangeVo.setNewRmName("");
					}
						else
					{
							rmChangeVo.setDealIdForCheckBox(CommonFunction.checkNull(data.get(4)).toString());
							rmChangeVo.setNewRmName(CommonFunction.checkNull(data.get(5)).toString());
					}
						rmChangeVo.setProduct(CommonFunction.checkNull(data.get(6)).toString());
						rmChangeVo.setScheme(CommonFunction.checkNull(data.get(7)).toString());
						rmChangeVo.setRoName(CommonFunction.checkNull(data.get(9)).toString());
					if((CommonFunction.checkNull(data.get(8)).toString()).equalsIgnoreCase("A"))
					{
						rmChangeVo.setDealIdForCheckBox("");
						rmChangeVo.setNewRoName("");
					}
						else
					{
							rmChangeVo.setDealIdForCheckBox(CommonFunction.checkNull(data.get(4)).toString());
							rmChangeVo.setNewRoName(CommonFunction.checkNull(data.get(11)).toString());
					}
						rmChangeVo.setUserName(CommonFunction.checkNull(data.get(10)).toString());
						if((CommonFunction.checkNull(data.get(8)).toString()).equalsIgnoreCase("A"))
						{
							rmChangeVo.setDealIdForCheckBox("");
							rmChangeVo.setNewMaker("");
						}
							else
						{
								rmChangeVo.setDealIdForCheckBox(CommonFunction.checkNull(data.get(4)).toString());
								rmChangeVo.setNewMaker(CommonFunction.checkNull(data.get(12)).toString());
						}
						rmChangeVo.setTotalRecordSize(totalRecordSize);
						detailList.add(rmChangeVo);
					

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		finally{
			deaId=null;
			ro=null;
			rm=null;
			maker=null;
			searchlist.clear();
			searchlist=null;
			roName=null;
			rmName=null;
			makerName=null;
			ob=null;
			rmChangeVo=null;
			data.clear();
			data=null;
			
		}
		
		return detailList;
	}
	
	public ArrayList<RmChangeVo> searchRmMakerAuthor(Object ob, String recStatus) {
		String rmName=null;
		String rmName1=null;
		ArrayList searchlist = new ArrayList();
		RmChangeVo rmChangeVo =(RmChangeVo) ob;
		ArrayList<RmChangeVo> detailList = new ArrayList<RmChangeVo>();

		try {
			logger.info("In searchRmMakerAuthor().....................................Dao Impl");
			String deaId = rmChangeVo.getLbxDealNo();
			
			if("P".equalsIgnoreCase(recStatus))
			{
				rmName = rmChangeVo.getLbxRM(); //Existing RM
				rmName1 = rmChangeVo.getLbxRelationship(); //New RM
			}
			else
			{
				rmName1 = rmChangeVo.getLbxRM(); //New RM
			}
			StringBuffer bufInsSql = new StringBuffer();
			bufInsSql.append("select a.DEAL_NO,d.CUSTOMER_NAME,(select user_name from SEC_USER_M where user_id=b.EXISTING_RM)rm_name,(select user_name from SEC_USER_M where user_id=b.EXISTING_RO)ro_name,(select user_name from SEC_USER_M where user_id=b.EXISTING_MAKER)maker_name,a.DEAL_ID,b.DEAL_ID,c.USER_NAME,q.USER_NAME,r.USER_NAME,P.product_desc,S.scheme_desc  from cr_deal_dtl a ");
			bufInsSql.append("left outer join cr_deal_loan_dtl e on(a.DEAL_ID = e.DEAL_ID) left outer join cr_product_m P on(P.product_id = e.DEAL_PRODUCT) left outer join cr_scheme_m S on(S.scheme_id = e.DEAL_SCHEME) ");
			bufInsSql.append("left join cr_rm_change_dtl b on (a.DEAL_ID=b.DEAL_ID) left join cr_deal_customer_m d on (a.DEAL_CUSTOMER_ID=d.CUSTOMER_ID) left join SEC_USER_M c on (b.NEW_RM=c.USER_ID) left join SEC_USER_M q on (b.NEW_RO=q.USER_ID)left join SEC_USER_M r on (b.NEW_MAKER=r.USER_ID) where b.REC_STATUS ='"+StringEscapeUtils.escapeSql(recStatus)+"' ");

			if(recStatus.equalsIgnoreCase("F"))
				bufInsSql.append(" and b.MAKER_ID!='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(rmChangeVo.getMakerId()))+"' ");
			if (!CommonFunction.checkNull(deaId).trim().equalsIgnoreCase(""))
				bufInsSql.append(" and  a.DEAL_ID='"+deaId.trim()+"'");
			if (!CommonFunction.checkNull(rmName).trim().equalsIgnoreCase(""))
				bufInsSql.append(" and  a.DEAL_RM='"+rmName.trim()+"'");
			if (!CommonFunction.checkNull(rmName1).trim().equalsIgnoreCase(""))
				bufInsSql.append(" and  b.NEW_RM='"+rmName1.trim()+"'");
			
				
			logger.info("search Query...." + bufInsSql.toString());
			searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
			
			int totalRecordSize = searchlist.size();
			
			rmChangeVo.setTotalRecordSize(totalRecordSize);
			for (int i = 0; i < totalRecordSize; i++) {
				ArrayList data = (ArrayList) searchlist.get(i);
				
				if (data.size() > 0) {
					 rmChangeVo = new RmChangeVo();
					 rmChangeVo.setDealNo("<a href=# onclick="+"showPopUp("+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(CommonFunction.checkNull(data.get(3)))).trim()+")"+" >"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(0))) +"</a>");
					 rmChangeVo.setCustomerName(CommonFunction.checkNull(data.get(1)).toString());
					 rmChangeVo.setRmName(CommonFunction.checkNull(data.get(2)).toString());
					 rmChangeVo.setRoName(CommonFunction.checkNull(data.get(3)).toString());
					 rmChangeVo.setUserName(CommonFunction.checkNull(data.get(4)).toString());
					 rmChangeVo.setDealId(CommonFunction.checkNull(data.get(5)).toString());
					 rmChangeVo.setDealIdForCheckBox(CommonFunction.checkNull(data.get(6)).toString());
					 rmChangeVo.setNewRmName(CommonFunction.checkNull(data.get(7)).toString());
					 rmChangeVo.setNewRoName(CommonFunction.checkNull(data.get(8)).toString());
					 rmChangeVo.setNewMaker(CommonFunction.checkNull(data.get(9)).toString());
					 rmChangeVo.setProduct(CommonFunction.checkNull(data.get(10)).toString());
					 rmChangeVo.setScheme(CommonFunction.checkNull(data.get(11)).toString());
					 rmChangeVo.setTotalRecordSize(totalRecordSize);
					detailList.add(rmChangeVo);
				
					data.clear();
					data=null;
				}
			}
					bufInsSql=null;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			
			 	rmName=null;
			 	rmName1=null;
			 	ob=null;
			 	recStatus=null;
			 	searchlist.clear();
			 	searchlist=null;
			 	rmChangeVo=null;
		}
		
			return detailList;
	}
	
	public boolean insertRmChangeData(Object ob, String[] checkbox) {

		ArrayList searchlist = new ArrayList();
		ArrayList qryList = null;
		ArrayList insertList = null;
		StringBuffer bufInsSql=null;
		String makerQuery =null;
		String rmQuery=null;
		String rmQuery1=null;
		String dealRm=null;
		String dealRm1=null;
		String dealRm2=null;
		PrepStmtObject insertPrepStmtObject = null;
		
		boolean status = false;
		try {
			
			
			logger.info("In insertRmChangeData..............");
			RmChangeVo rmChangeVo  = (RmChangeVo) ob;
			insertList = new ArrayList();
			qryList = new ArrayList();
			rmQuery = "select DEAL_RM from cr_deal_dtl where deal_id='"+checkbox[0]+"'";
			rmQuery1 = "select DEAL_RO from cr_deal_dtl where deal_id='"+checkbox[0]+"'";
		    makerQuery = "select MAKER_ID from cr_deal_dtl where deal_id='"+checkbox[0]+"'";
			dealRm = ConnectionDAO.singleReturn(rmQuery);
			dealRm1 = ConnectionDAO.singleReturn(rmQuery1);
			dealRm2 = ConnectionDAO.singleReturn(makerQuery);
			
				
				for (int j = 0; j < checkbox.length; j++) {
					bufInsSql = new StringBuffer();
					String query = "DELETE FROM cr_rm_change_dtl WHERE DEAL_ID='"+ checkbox[j] + "'";
					//logger.info("Delete query : " + query);
					qryList.add(query);
					query=null;
					boolean st = ConnectionDAO.sqlInsUpdDelete(qryList);

					if (st) {				
					insertPrepStmtObject = new PrepStmtObject();
				
					bufInsSql.append("insert into cr_rm_change_dtl(DEAL_ID,LOAN_ID,");
					bufInsSql.append(" EXISTING_RM,NEW_RM,EXISTING_RO,NEW_RO,EXISTING_MAKER,NEW_MAKER,REC_STATUS,MAKER_ID,MAKER_DATE)");
					bufInsSql.append(" values ( ");
					bufInsSql.append(" ?,");//DEAL_ID
					bufInsSql.append(" ?,");//LOAN_ID
					bufInsSql.append(" ?,");//EXISTING_RM
					bufInsSql.append(" ?,");//NEW_RM
					bufInsSql.append(" ?,");//EXISTING_RO
					bufInsSql.append(" ?,");//NEW_RO
					bufInsSql.append(" ?,");//EXISTING_MAKER
					bufInsSql.append(" ?,");//NEW_MAKER
					bufInsSql.append(" ?,");//REC_STATUS
					bufInsSql.append(" ?,");//MAKER_ID
					bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND))");//MAKER_DATE
				
					if (CommonFunction.checkNull(checkbox[j]).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(checkbox[j]);
					
					if (CommonFunction.checkNull(rmChangeVo.getLoanNo()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(rmChangeVo.getLoanNo());
					
					if(rmChangeVo.getLbxDealNo().isEmpty())
					{
						if (CommonFunction.checkNull(rmChangeVo.getLbxRM()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(rmChangeVo.getLbxRM());
					}
					else if(rmChangeVo.getLbxDealNo().length() != 0)
					{
						if (CommonFunction.checkNull(dealRm).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(dealRm);
					}
					
					if (CommonFunction.checkNull(rmChangeVo.getLbxRelationship()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(rmChangeVo.getLbxRelationship());
					
					////////
					if(rmChangeVo.getLbxDealNo().isEmpty())
					{
						if (CommonFunction.checkNull(rmChangeVo.getLbxRO()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						
						else
							insertPrepStmtObject.addString(rmChangeVo.getLbxRO());
					}
					else if(rmChangeVo.getLbxDealNo().length() != 0)
					{
						if (CommonFunction.checkNull(dealRm1).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(dealRm1);
					}
					if (CommonFunction.checkNull(rmChangeVo.getLbxRelationshipO()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(rmChangeVo.getLbxRelationshipO());
					
					//changes for maker_id
					if(rmChangeVo.getLbxDealNo().isEmpty())
					{
						if (CommonFunction.checkNull(rmChangeVo.getLbxMaker()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(rmChangeVo.getLbxMaker());
					}
					else if(rmChangeVo.getLbxDealNo().length() != 0)
					{
						if (CommonFunction.checkNull(dealRm2).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(dealRm2);
					}
					if (CommonFunction.checkNull(rmChangeVo.getLbxUserId()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(rmChangeVo.getLbxUserId());
					//changes end
					
					if (CommonFunction.checkNull("P").equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString("P");
					
					if (CommonFunction.checkNull(rmChangeVo.getMakerId()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(rmChangeVo.getMakerId());

					if (CommonFunction.checkNull(rmChangeVo.getMakerDate()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(rmChangeVo.getMakerDate());

					insertPrepStmtObject.setSql(bufInsSql.toString());
					insertList.add(insertPrepStmtObject);
				}
				}
				logger.info("IN insertRmChangeData insert query1 ### "+ insertPrepStmtObject.printQuery());

				status = ConnectionDAO.sqlInsUpdDeletePrepStmt(insertList);
				rmChangeVo=null;
				
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			
			searchlist.clear();
			searchlist=null;
			insertList.clear();
			insertList=null;
			qryList.clear();
			qryList=null;
			insertPrepStmtObject=null;	
			bufInsSql=null;
			checkbox=null;
			ob=null;
			rmQuery=null;
			rmQuery1=null;
			makerQuery=null;
			dealRm2=null;
			dealRm1=null;
			dealRm=null;
		}

		return status;
	}

public boolean forwardRM(Object ob, String[] checkbox) {
		
	RmChangeVo vo = (RmChangeVo) ob;
	boolean status = false;
	ArrayList qryList = null;
	StringBuffer bufInsSql = null;
	PrepStmtObject insertPrepStmtObject = null;
	int length=checkbox.length;
		
	try {
			logger.info("In forwardRM cr_rm_change_dtl.................." + checkbox.length);
			qryList = new ArrayList();
			
			for (int j = 0; j <length ; j++) 
			{
				bufInsSql = new StringBuffer();
				insertPrepStmtObject = new PrepStmtObject();
				bufInsSql.append("UPDATE cr_rm_change_dtl SET REC_STATUS='F', " +
								" MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) WHERE DEAL_ID=? AND REC_STATUS='P'");

				if((CommonFunction.checkNull(vo.getMakerDate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getMakerDate()).trim());
				
				if (CommonFunction.checkNull(checkbox[j]).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(checkbox[j]);
				
				insertPrepStmtObject.setSql(bufInsSql.toString());
				qryList.add(insertPrepStmtObject);
				
				//Richa Changes
				if(vo.getLbxRelationship()!="")
				{
				
					bufInsSql=null;
					insertPrepStmtObject=null;
					bufInsSql = new StringBuffer();
					insertPrepStmtObject = new PrepStmtObject();
					String userReportingTo = ConnectionDAO.singleReturn("select USER_REPORTING_TO from sec_user_m where user_id='"+ vo.getLbxRelationship()+"'");
					bufInsSql.append("update cr_deal_dtl set DEAL_PM='"+CommonFunction.checkNull(userReportingTo)+"' where deal_id= ? ");
				
					if (CommonFunction.checkNull(checkbox[j]).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
					else
					insertPrepStmtObject.addString(checkbox[j]);
					insertPrepStmtObject.setSql(bufInsSql.toString());
					qryList.add(insertPrepStmtObject);
				}
		}
					logger.info("IN forwardRM() update query1 ### "+ insertPrepStmtObject.printQuery());
					status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			
				
		} 
			catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			qryList.clear();
			qryList=null;
			insertPrepStmtObject=null;
			bufInsSql=null;
			vo=null;
			checkbox=null;
			ob=null;
		}

		return status;

	}
public boolean saveRmChangeAuthor(Object ob, String[] checkbox) {

	
	ArrayList updateList = null;
	ArrayList updateList1 = null;
	ArrayList updateList2 = null;
	ArrayList insertList = null;
	StringBuffer bufInsSql=null;
	StringBuffer bufInsSql1=null;
	StringBuffer bufInsSql2=null;
	StringBuffer bufInsSql3=null;
	PrepStmtObject insertPrepStmtObject = null;
	PrepStmtObject updatePrepStmtObject = null;
	PrepStmtObject updatePrepStmtObject1 = null;
	PrepStmtObject updatePrepStmtObject2 = null;
	int length=checkbox.length;
	
	boolean status = false;
	boolean status1 = false;
	boolean status2 = false;
	boolean status3 = false;
	try {
		RmChangeVo rmChangeVo  = (RmChangeVo) ob;
		insertList = new ArrayList();
		updateList = new ArrayList();
		updateList1 = new ArrayList();
		updateList2 = new ArrayList();
		String decesion	= rmChangeVo.getDecison();
		String rmQuery = "select NEW_RM from cr_rm_change_dtl where deal_id='"+checkbox[0]+"'";
		String dealRm = ConnectionDAO.singleReturn(rmQuery);
		String rmexisting = "select EXISTING_RM from cr_rm_change_dtl where deal_id='"+checkbox[0]+"'";
		String dealRmexisting = ConnectionDAO.singleReturn(rmexisting);
		rmexisting=null;
		rmQuery=null;
		String roQuery = "select NEW_RO from cr_rm_change_dtl where deal_id='"+checkbox[0]+"'";
		String dealRo = ConnectionDAO.singleReturn(roQuery);
		String existro = "select EXISTING_RO from cr_rm_change_dtl where deal_id='"+checkbox[0]+"'";
		String dealRoexist = ConnectionDAO.singleReturn(existro);
		roQuery=null;
		existro=null;
		String makerQuery = "select NEW_MAKER from cr_rm_change_dtl where deal_id='"+checkbox[0]+"'";
		String dealmaker = ConnectionDAO.singleReturn(makerQuery);
		String makerExisting = "select EXISTING_MAKER from cr_rm_change_dtl where deal_id='"+checkbox[0]+"'";
		String dealmakerexisting = ConnectionDAO.singleReturn(makerExisting);
		makerExisting=null;
		makerQuery=null;
		
		for (int j = 0; j <length ; j++) {
				
				bufInsSql = new StringBuffer();
				bufInsSql1 = new StringBuffer();
				bufInsSql2 = new StringBuffer();
				bufInsSql3 = new StringBuffer();
				if (decesion.equalsIgnoreCase("A")) {				
					
					insertPrepStmtObject = new PrepStmtObject();
					updatePrepStmtObject = new PrepStmtObject();	
					updatePrepStmtObject1 = new PrepStmtObject();
					updatePrepStmtObject2 = new PrepStmtObject();
					bufInsSql.append("UPDATE cr_rm_change_dtl SET REC_STATUS='A',AUTHOR_REMARKS=? ,AUTHOR_ID=?," +
						" AUTHOR_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) WHERE DEAL_ID=? AND REC_STATUS='F'");
					
					if(!CommonFunction.checkNull(dealRm).equalsIgnoreCase(""))
					{
						bufInsSql1.append("UPDATE cr_deal_dtl SET DEAL_RM='"+dealRm+"' WHERE DEAL_ID='"+checkbox[j]+"'");
					}
					else
					{
						bufInsSql1.append("UPDATE cr_deal_dtl SET DEAL_RM='"+dealRmexisting+"' WHERE DEAL_ID='"+checkbox[j]+"'");
					}
					
					if(!CommonFunction.checkNull(dealRo).equalsIgnoreCase(""))
					{
						bufInsSql2.append("UPDATE cr_deal_dtl SET DEAL_RO='"+dealRo+"' WHERE DEAL_ID='"+checkbox[j]+"'");
					}
					else
					{
						bufInsSql2.append("UPDATE cr_deal_dtl SET DEAL_RO='"+dealRoexist+"' WHERE DEAL_ID='"+checkbox[j]+"'");
					}
					
					if(!CommonFunction.checkNull(dealmaker).equalsIgnoreCase(""))
					{
						bufInsSql3.append("UPDATE cr_deal_dtl SET MAKER_ID='"+dealmaker+"' WHERE DEAL_ID='"+checkbox[j]+"'");
					}
					else
					{
						bufInsSql3.append("UPDATE cr_deal_dtl SET MAKER_ID='"+dealmakerexisting+"' WHERE DEAL_ID='"+checkbox[j]+"'");
					}
					
					if((CommonFunction.checkNull(rmChangeVo.getTextArea())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((rmChangeVo.getTextArea()).trim());
					
					if((CommonFunction.checkNull(rmChangeVo.getMakerId())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((rmChangeVo.getMakerId()).trim());
				
					if((CommonFunction.checkNull(rmChangeVo.getMakerDate())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((rmChangeVo.getMakerDate()).trim());
				
					if (CommonFunction.checkNull(checkbox[j]).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(checkbox[j]);

					updatePrepStmtObject.setSql(bufInsSql1.toString());
					updateList.add(updatePrepStmtObject);
					updatePrepStmtObject1.setSql(bufInsSql2.toString());
					updateList1.add(updatePrepStmtObject1);
					updatePrepStmtObject2.setSql(bufInsSql3.toString());
					updateList2.add(updatePrepStmtObject2);
				}
				else
				{
					insertPrepStmtObject = new PrepStmtObject();

					bufInsSql.append("delete from cr_rm_change_dtl where DEAL_ID=?");
				
					if (CommonFunction.checkNull(checkbox[j]).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(checkbox[j]);
				}
				insertPrepStmtObject.setSql(bufInsSql.toString());
				insertList.add(insertPrepStmtObject);
				
			}
			logger.info("IN saveRmChangeAuthor update query1 ### "+ insertPrepStmtObject.printQuery());
			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(insertList);
			if (decesion.equalsIgnoreCase("A")) {
				logger.info("IN saveRmChangeAuthor update query2 ### "+ updatePrepStmtObject.printQuery());
				logger.info("IN saveRmChangeAuthor update query3 ### "+ updatePrepStmtObject1.printQuery());
				logger.info("IN saveRmChangeAuthor update query4 ### "+ updatePrepStmtObject2.printQuery());
				status1 = ConnectionDAO.sqlInsUpdDeletePrepStmt(updateList);
				status2 = ConnectionDAO.sqlInsUpdDeletePrepStmt(updateList1);
				status3 = ConnectionDAO.sqlInsUpdDeletePrepStmt(updateList2);
			}
				rmChangeVo=null;
				dealRm=null;
				dealRo=null;
				dealmaker=null;
				dealRmexisting=null;
				dealmakerexisting=null;
				dealRoexist=null;
	} 
			catch (Exception e) {
				e.printStackTrace();
	}
			finally{
				updateList.clear();
				updateList = null;
				updateList1.clear();
				updateList1 = null;
				updateList2.clear();
				updateList2 = null;
				insertList.clear();
				insertList = null;
				bufInsSql=null;
				bufInsSql1=null;
				bufInsSql2=null;
				bufInsSql3=null;
				insertPrepStmtObject = null;
				updatePrepStmtObject = null;
				updatePrepStmtObject1 = null;
				updatePrepStmtObject2 = null;
}

	return status;
}
}
