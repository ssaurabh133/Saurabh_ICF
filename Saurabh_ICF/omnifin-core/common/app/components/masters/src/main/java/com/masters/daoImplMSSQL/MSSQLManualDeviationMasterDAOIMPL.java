package com.masters.daoImplMSSQL;

import java.util.ArrayList;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;
import com.masters.dao.ManualDeviationMasterDAO;
import com.masters.vo.ManulaDeviationVO;

public class MSSQLManualDeviationMasterDAOIMPL implements ManualDeviationMasterDAO {
	static final Logger logger = Logger.getLogger(MSSQLManualDeviationMasterDAOIMPL.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime = resource.getString("lbl.dateWithTimeInDao");
	String dbType=resource.getString("lbl.dbType");
	String dbo=resource.getString("lbl.dbPrefix");
	int no = Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	
	public boolean saveManualDeviation(ManulaDeviationVO vo) {
		ArrayList qryList = new ArrayList();
		boolean status = false;	
		StringBuilder bufInsSql = new StringBuilder();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		try {
			if(CommonFunction.checkNull(vo.getManualid()).equalsIgnoreCase(""))
			{
			logger.info("In saveManualDeviation........ManualDeviationMasterDAOIMPL.............................");
			bufInsSql.append(" insert into cr_manual_deviation_m(PRODUCT_ID,SCHEME_ID,STAGE_ID,RULE_DESC,SUB_RULE_TYPE,DEVIATION_TYPE,RULE_ACTION,APPROVAL_LEVEL,REC_STATUS,MAKER_ID,MAKER_DATE)values(");
			bufInsSql.append(" ?,");// PRODUCT_ID
			bufInsSql.append(" ?,");// SCHEME_ID
			bufInsSql.append(" ?,");// STAGE_ID
			bufInsSql.append(" ?,");// RULE_DESC
			bufInsSql.append(" ?,");// SUB_RULE_TYPE
			bufInsSql.append(" 'G',");// DEVIATION_TYPE
			bufInsSql.append(" 'D',");// RULE_ACTION
			bufInsSql.append(" ?,");// APPROVAL_LEVEL
			bufInsSql.append(" ?,");// REC_STATUS			
			bufInsSql.append(" ?,");// MAKER_ID
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
			if (CommonFunction.checkNull(vo.getLbxProductID()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getLbxProductID().trim());
			

			if (CommonFunction.checkNull(vo.getLbxSchemeID()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getLbxSchemeID());

			if (CommonFunction.checkNull(vo.getLbxPCDStage()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getLbxPCDStage());

			if (CommonFunction.checkNull(vo.getRuleDescription()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getRuleDescription().toUpperCase().trim());
			
			if (CommonFunction.checkNull(vo.getSubRuleType()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getSubRuleType().toUpperCase().trim());

			if (CommonFunction.checkNull(vo.getApprovalLevel()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getApprovalLevel().trim());

			if (CommonFunction.checkNull(vo.getStatus()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getStatus().trim());
			
			if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerId().trim());

			if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerDate().toUpperCase().trim());
			}
			else
			{
				logger.info("In update........ManualDeviationMasterDAOIMPL.............................");
				bufInsSql.append("update cr_manual_deviation_m set PRODUCT_ID=?,SCHEME_ID=?,STAGE_ID=?,RULE_DESC=?,SUB_RULE_TYPE=?,APPROVAL_LEVEL=?,REC_STATUS=?,MAKER_ID=?,MAKER_DATE=");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
				bufInsSql.append(" where MANUAL_DEVIATION_ID=?");
				if (CommonFunction.checkNull(vo.getLbxProductID()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxProductID().trim());
				

				if (CommonFunction.checkNull(vo.getLbxSchemeID()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxSchemeID());

				if (CommonFunction.checkNull(vo.getLbxPCDStage()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxPCDStage());

				if (CommonFunction.checkNull(vo.getRuleDescription()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getRuleDescription().toUpperCase().trim());

				if (CommonFunction.checkNull(vo.getSubRuleType()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getSubRuleType().toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getApprovalLevel()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getApprovalLevel().toUpperCase().trim());


				if (CommonFunction.checkNull(vo.getStatus()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getStatus().trim());
				
				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId().trim());

				if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate().trim());
				
				if (CommonFunction.checkNull(vo.getManualid()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getManualid().trim());			
			}			
			
			insertPrepStmtObject.setSql(bufInsSql.toString());
			qryList.add(insertPrepStmtObject);
			logger.info("IN saveManualDeviation() insert/update query1 ### "+ insertPrepStmtObject.printQuery());
			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			logger.info("In save/update-ManualDeviation......................"+ status);
			logger.info("save/update-ManualDeviation...."+ insertPrepStmtObject.printQuery());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			bufInsSql = null;
			insertPrepStmtObject = null;
			qryList = null;
		}

		return status;
	}
	public ArrayList<ManulaDeviationVO> getManualDeviationRecords(int currentPageLink) 
	{
		logger.info("In getManualDeviationRecords after saving data");
		ArrayList list=new ArrayList();
		StringBuilder bufInsSql=new StringBuilder();
		StringBuilder bufInsSqlTempCount = new StringBuilder();	
		try
		{
			ArrayList header=null;
			int count=0;
			int startRecordIndex=0;
			int endRecordIndex = no;				
			bufInsSql.append("select m.MANUAL_DEVIATION_ID,m.PRODUCT_ID,p.PRODUCT_DESC,m.SCHEME_ID,s.SCHEME_DESC,m.STAGE_ID,st.STAGE_DESC,m.APPROVAL_LEVEL,m.RULE_DESC," +
					" case when m.REC_STATUS='A' then 'Active' else 'Inactive' end as status from cr_manual_deviation_m m" +
					" left outer join cr_product_m p on m.PRODUCT_ID=p.PRODUCT_ID" +
					" left outer join cr_scheme_m s on m.SCHEME_ID=s.SCHEME_ID" +
					" left outer join cr_stage_m st on m.STAGE_ID=st.STAGE_ID");						
			     logger.info("current PAge Link no .................... "+currentPageLink);			    
			     bufInsSqlTempCount.append("select count(0) from cr_manual_deviation_m");
			     count=Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));			     
			if(currentPageLink>1)			
			{
				startRecordIndex = (currentPageLink-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
				bufInsSql.append(" ORDER BY m.MANUAL_DEVIATION_ID OFFSET ");
				bufInsSql.append(startRecordIndex);
				bufInsSql.append(" ROWS FETCH next ");
				bufInsSql.append(endRecordIndex);
				//bufInsSql.append(" ROWS ONLY ");bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
			}
			logger.info("query : "+bufInsSql);
		    header = ConnectionDAO.sqlSelect(bufInsSql.toString());						
			for(int i=0;i<header.size();i++)
			{
				ArrayList header1=(ArrayList)header.get(i);
				if(header1!=null && header1.size()>0)
				{					
					ManulaDeviationVO fieldVo = new ManulaDeviationVO();					
					fieldVo.setManualid("<a href=manualDeviationAddProcessAction.do?method=updateManualDeviationMaster&manualid="+(CommonFunction.checkNull(header1.get(0))).trim()+" >"+(CommonFunction.checkNull(header1.get(0))).trim()+"</a>");
					fieldVo.setLbxProductID((CommonFunction.checkNull(header1.get(1))).trim());					
					fieldVo.setProduct((CommonFunction.checkNull(header1.get(2))).trim());
					fieldVo.setLbxSchemeID((CommonFunction.checkNull(header1.get(3))).trim());					
					fieldVo.setScheme((CommonFunction.checkNull(header1.get(4))).trim());
					fieldVo.setLbxPCDStage((CommonFunction.checkNull(header1.get(5))).trim());					
					fieldVo.setStage((CommonFunction.checkNull(header1.get(6))).trim());
					fieldVo.setApprovalLevel((CommonFunction.checkNull(header1.get(7))).trim());
					fieldVo.setRuleDescription((CommonFunction.checkNull(header1.get(8))).trim());					
					fieldVo.setStatus((CommonFunction.checkNull(header1.get(9))).trim());
				
					fieldVo.setTotalRecordSize(count);					
					list.add(fieldVo);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
		finally
		{
			bufInsSql=null;
			bufInsSqlTempCount=null;
		}
		return list;
	}
	public ArrayList<ManulaDeviationVO> searchManualDeviationList(ManulaDeviationVO vo)
	{
		logger.info("In searchManualDeviationList");
		ArrayList list=new ArrayList();
		StringBuilder bufInsSql=new StringBuilder();
		StringBuilder bufInsSqlTempCount = new StringBuilder();	
		try
		{
			ArrayList header=null;
			int count=0;
			int startRecordIndex=0;
			int endRecordIndex = no;				
			bufInsSql.append("select m.MANUAL_DEVIATION_ID,m.PRODUCT_ID,p.PRODUCT_DESC,m.SCHEME_ID,s.SCHEME_DESC,m.STAGE_ID,st.STAGE_DESC,m.RULE_DESC," +
					" case when m.REC_STATUS='A' then 'Active' else 'Inactive' end as status from cr_manual_deviation_m m" +
					" left outer join cr_product_m p on m.PRODUCT_ID=p.PRODUCT_ID" +
					" left outer join cr_scheme_m s on m.SCHEME_ID=s.SCHEME_ID" +
					" left outer join cr_stage_m st on m.STAGE_ID=st.STAGE_ID where 'a'='a'");	
			   if(vo.getLbxProductID()==null || vo.getLbxProductID().equalsIgnoreCase("") )
			   {
				   
			   }
			   else
			   {
				   bufInsSql.append(" and m.PRODUCT_ID= '"+vo.getLbxProductID()+"'");  
			   }
			   
			   if(vo.getLbxSchemeID()==null || vo.getLbxSchemeID().equalsIgnoreCase("") )
			   {
				   
			   }
			   else
			   {
				   bufInsSql.append(" and m.SCHEME_ID= '"+vo.getLbxSchemeID()+"'");  
			   }
			   if(vo.getLbxPCDStage()==null || vo.getLbxPCDStage().equalsIgnoreCase("") )				   
			   {
				   
			   }
			   else
			   {
				   bufInsSql.append(" and m.STAGE_ID= '"+vo.getLbxPCDStage()+"'");  
			   }
			  		   
			    
			     bufInsSqlTempCount.append("select count(0) from cr_manual_deviation_m where 'a'='a'");
			     if(vo.getLbxProductID()==null || vo.getLbxProductID().equalsIgnoreCase("") )
				   {
					   
				   }
				   else
				   {
					   bufInsSqlTempCount.append(" and PRODUCT_ID= '"+vo.getLbxProductID()+"'");  
				   }
				   
				   if(vo.getLbxSchemeID()==null || vo.getLbxSchemeID().equalsIgnoreCase("") )
				   {
					   
				   }
				   else
				   {
					   bufInsSqlTempCount.append(" and SCHEME_ID= '"+vo.getLbxSchemeID()+"'");  
				   }
				   if(vo.getLbxPCDStage()==null || vo.getLbxPCDStage().equalsIgnoreCase("") )				   
				   {
					   
				   }
				   else
				   {
					   bufInsSqlTempCount.append(" and STAGE_ID= '"+vo.getLbxPCDStage()+"'");  
				   }
			     
			     
			     
			     count=Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
		
			logger.info("searchManualDeviationList query for search : "+bufInsSql);
		    header = ConnectionDAO.sqlSelect(bufInsSql.toString());						
			for(int i=0;i<header.size();i++)
			{
				ArrayList header1=(ArrayList)header.get(i);
				if(header1!=null && header1.size()>0)
				{					
					ManulaDeviationVO fieldVo = new ManulaDeviationVO();					
					fieldVo.setManualid("<a href=manualDeviationAddProcessAction.do?method=updateManualDeviationMaster&manualid="+(CommonFunction.checkNull(header1.get(0))).trim()+" >"+(CommonFunction.checkNull(header1.get(0))).trim()+"</a>");
					fieldVo.setLbxProductID((CommonFunction.checkNull(header1.get(1))).trim());					
					fieldVo.setProduct((CommonFunction.checkNull(header1.get(2))).trim());
					fieldVo.setLbxSchemeID((CommonFunction.checkNull(header1.get(3))).trim());					
					fieldVo.setScheme((CommonFunction.checkNull(header1.get(4))).trim());
					fieldVo.setLbxPCDStage((CommonFunction.checkNull(header1.get(5))).trim());					
					fieldVo.setStage((CommonFunction.checkNull(header1.get(6))).trim());
					fieldVo.setRuleDescription((CommonFunction.checkNull(header1.get(7))).trim());
					fieldVo.setStatus((CommonFunction.checkNull(header1.get(8))).trim());							
					fieldVo.setTotalRecordSize(count);					
					list.add(fieldVo);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
		finally
		{
			bufInsSql=null;
			bufInsSqlTempCount=null;
		}
		return list;
	}
	
	public ArrayList<ManulaDeviationVO> getSingleRecord(String manualid)
	{
		logger.info("In getSingleRecord...........");
		ArrayList list=new ArrayList();
		StringBuilder bufInsSql=new StringBuilder();		
		try
		{
			ArrayList header=null;	
			bufInsSql.append("select m.MANUAL_DEVIATION_ID,m.PRODUCT_ID,p.PRODUCT_DESC,m.SCHEME_ID,s.SCHEME_DESC,m.STAGE_ID,st.STAGE_DESC,m.APPROVAL_LEVEL,m.RULE_DESC," +
					" m.REC_STATUS,m.SUB_RULE_TYPE from cr_manual_deviation_m m" +
					" left outer join cr_product_m p on m.PRODUCT_ID=p.PRODUCT_ID" +
					" left outer join cr_scheme_m s on m.SCHEME_ID=s.SCHEME_ID" +
					" left outer join cr_stage_m st on m.STAGE_ID=st.STAGE_ID where m.MANUAL_DEVIATION_ID="+manualid);			   
		
			logger.info("getSingleRecord query for search : "+bufInsSql);
		    header = ConnectionDAO.sqlSelect(bufInsSql.toString());						
			for(int i=0;i<header.size();i++)
			{
				ArrayList header1=(ArrayList)header.get(i);
				if(header1!=null && header1.size()>0)
				{					
					ManulaDeviationVO fieldVo = new ManulaDeviationVO();					
					fieldVo.setManualid(CommonFunction.checkNull(header1.get(0)));
					fieldVo.setLbxProductID((CommonFunction.checkNull(header1.get(1))).trim());					
					fieldVo.setProduct((CommonFunction.checkNull(header1.get(2))).trim());
					fieldVo.setLbxSchemeID((CommonFunction.checkNull(header1.get(3))).trim());					
					fieldVo.setScheme((CommonFunction.checkNull(header1.get(4))).trim());
					fieldVo.setLbxPCDStage((CommonFunction.checkNull(header1.get(5))).trim());					
					fieldVo.setStage((CommonFunction.checkNull(header1.get(6))).trim());
					fieldVo.setApprovalLevel((CommonFunction.checkNull(header1.get(7))).trim());
					fieldVo.setRuleDescription((CommonFunction.checkNull(header1.get(8))).trim());					
					fieldVo.setStatus((CommonFunction.checkNull(header1.get(9))).trim());	
					fieldVo.setSubRuleType((CommonFunction.checkNull(header1.get(10))).trim());	
					
					list.add(fieldVo);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
		finally
		{
			bufInsSql=null;		
		}
		return list;
	}
	
}
