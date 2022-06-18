package com.masters.daoImplMSSQL;

import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.connect.CommonFunction;
import com.connect.ConnectionDAOforEJB;
import com.connect.PrepStmtObject;
import com.masters.dao.ManufacturerSupplierMappingMasterDAO;
import com.masters.vo.ManufacturerSupplierMappingVO;


public class MSSQLManufacturerSupplierMappingMasterDAOIMPL implements ManufacturerSupplierMappingMasterDAO {
	static final Logger logger = Logger.getLogger(MSSQLManufacturerSupplierMappingMasterDAOIMPL.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime = resource.getString("lbl.dateWithTimeInDao");
	String dbType=resource.getString("lbl.dbType");
	String dbo=resource.getString("lbl.dbPrefix");
	//String selectFrom = resource.getString("lbl.selectFrom");
	
	int no = Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	
	
	public ArrayList<ManufacturerSupplierMappingVO> getManufacturerSupplierMappingRecords(ManufacturerSupplierMappingVO vo, int currentPageLink) 
	{
		logger.info("In ManufacturerSupplierMappingMasterDAOIMPL ::::::::::::::::::::");
		ArrayList list=new ArrayList();
		StringBuilder bufInsSql=new StringBuilder();
		StringBuilder bufInsSqlTempCount = new StringBuilder();	
		try
		{
			ArrayList header=null;
			int count=0;
			int startRecordIndex=0;
			int endRecordIndex = no;
			bufInsSql.append("SELECT DISTINCT MANUFACTURE_ID,MANUFACTURE_ID MANUFACTURE_ID1,MANUFACTURE_DESC,case when REC_STATUS='A' then 'Active' else 'Inactive' end as REC_STATUS 	");
			bufInsSql.append(" FROM cr_manufacture_supplier_mapping");
		    bufInsSqlTempCount.append("SELECT COUNT(distinct MANUFACTURE_ID) AS COUNT FROM cr_manufacture_supplier_mapping ");
		    
		    if ((!(CommonFunction.checkNull(vo.getManufacturerId()).equalsIgnoreCase(""))) && (!CommonFunction.checkNull(vo.getManufacturerDesc()).equalsIgnoreCase(""))) {
				bufInsSql.append(" WHERE MANUFACTURE_ID="+vo.getManufacturerId()+" AND MANUFACTURE_DESC like '%" + vo.getManufacturerDesc()+ "%' ");
				bufInsSqlTempCount.append("WHERE MANUFACTURE_ID="+vo.getManufacturerId()+" AND MANUFACTURE_DESC like '%"+ vo.getManufacturerDesc() +"%' ");
			}
		    
		    else if(!CommonFunction.checkNull(vo.getManufacturerId()).equalsIgnoreCase("")){
		    	bufInsSql.append(" WHERE MANUFACTURE_ID="+vo.getManufacturerId()+" ");
		    	bufInsSqlTempCount.append(" WHERE MANUFACTURE_ID="+vo.getManufacturerId()+" ");
		    }
		    
		    else if(!CommonFunction.checkNull(vo.getManufacturerDesc()).equalsIgnoreCase("")){
		    	bufInsSql.append(" WHERE MANUFACTURE_DESC like'%"+vo.getManufacturerDesc()+"%' ");
		    	bufInsSqlTempCount.append(" WHERE MANUFACTURE_DESC like'%"+vo.getManufacturerDesc()+"%' ");
		    }
		    count=Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));	
			
			logger.info("current PAge Link no .................... "+currentPageLink);
			if(currentPageLink>1)			
			{
				startRecordIndex = (currentPageLink-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
				bufInsSql.append(" ORDER BY MANUFACTURE_ID OFFSET ");
				bufInsSql.append(startRecordIndex);
				bufInsSql.append(" ROWS FETCH next ");
				bufInsSql.append(endRecordIndex);
				bufInsSql.append(" ROWS ONLY ");
				//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));		
								
			}
			logger.info("query ::::::::::::::::::::::::::::"+bufInsSql);
		    header = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());						
			for(int i=0;i<header.size();i++)
			{
				ArrayList header1=(ArrayList)header.get(i);
				if(header1!=null && header1.size()>0)
				{					
					ManufacturerSupplierMappingVO Vo = new ManufacturerSupplierMappingVO();					
					Vo.setMappingId("<a href=mfrSupplMappingDispatchAction.do?method=openMfrSupplMappingRecords&mappingId="+(CommonFunction.checkNull(header1.get(0))).trim()+" >"+(CommonFunction.checkNull(header1.get(0))).trim()+"</a>");
					Vo.setLbxmachineManufact((CommonFunction.checkNull(header1.get(1))).toString());	
					Vo.setManufacturerDesc((CommonFunction.checkNull(header1.get(2))).toString());
					Vo.setRecStatus((CommonFunction.checkNull(header1.get(3))).toString());		
					Vo.setTotalRecordSize(count);					
					list.add(Vo);
					
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



	public boolean saveMfrSuppMappingRecord(ManufacturerSupplierMappingVO vo, String[] supplier) {
		logger.info("In saveMfrSuppMappingRecord.........................");
		ArrayList qryList = new ArrayList();
		boolean status = false;	
		String findCount=null;
		String stat ="";
		StringBuffer bufInsSql = new StringBuffer();
		PrepStmtObject insertPrepStmtObject = null;			 
		String query="SELECT COUNT(1) FROM cr_manufacture_supplier_mapping WHERE MANUFACTURE_ID="+vo.getLbxmachineManufact();		
		logger.info("saveMfrSuppMappingRecord:::::::::::::::::::::::"+query);
		int count=Integer.parseInt(ConnectionDAOforEJB.singleReturn(query.toString()));
		 logger.info("count........................."+count);		   
			
		try {
			logger.info("getRecStatus........................."+vo.getRecStatus());
			if (count>0) {
				logger.info("DATA ALREADY EXIST......................");
				logger.info("DATA ALREADY EXIST......................");
			}else{
				logger.info("INSERT DATA......................");
				if (vo.getRecStatus()!= null && vo.getRecStatus().equals("on")) {
					stat = "A";
				} else {
					stat = "X";
				}	
				logger.info("Supplier...length......................"+supplier.length);
				for(int k=0;k<supplier.length;k++){
				insertPrepStmtObject=null;	
				insertPrepStmtObject = new PrepStmtObject();
				bufInsSql = new StringBuffer();
				bufInsSql.append(" insert into cr_manufacture_supplier_mapping(MANUFACTURE_ID,MANUFACTURE_DESC,DEALER_ID,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE) values(");
				bufInsSql.append(" ?,");// MANUFACTURE_ID
				bufInsSql.append(" ?,");// MANUFACTURE_DESC
				bufInsSql.append(" ?,");// DEALER_ID
				bufInsSql.append(" ?,");//REC_STATUS 	
				bufInsSql.append(" ?,");// MAKER_ID
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" ?,");// AUTHOR_ID
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
								
				if (CommonFunction.checkNull(vo.getLbxmachineManufact()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxmachineManufact().trim());
				
				if (CommonFunction.checkNull(vo.getManufacturerDesc()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getManufacturerDesc());

				if (CommonFunction.checkNull(supplier[k]).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(supplier[k]);

				if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(stat);
						
				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId().trim());

				if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate().toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getAuthorId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getAuthorId().trim());

				if (CommonFunction.checkNull(vo.getAuthorDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getAuthorDate().toUpperCase().trim());
				
				insertPrepStmtObject.setSql(bufInsSql.toString());
				qryList.add(insertPrepStmtObject);
				}
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);

				logger.info("In save/update-saveMfrSuppMappingRecord:::::::::::::::::::::::::"+ status);
				logger.info("save/update-saveMfrSuppMappingRecord:::::::::::::::"+ insertPrepStmtObject.printQuery());
			
		}
		}
		 catch (Exception e) {
			e.printStackTrace();
		} finally {
			bufInsSql = null;
			insertPrepStmtObject = null;
			qryList = null;
		}

		return status;
	}
	
	
	
	public boolean updateMfrSuppMappingRecord(ManufacturerSupplierMappingVO vo, String[] supplier) {
		logger.info("In updateMfrSuppMappingRecord.........................");
		ArrayList qryList = new ArrayList();
		boolean status = false;	
		String findCount=null;
		String stat ="";
		StringBuffer bufInsSql = new StringBuffer();
		PrepStmtObject insertPrepStmtObject = null;			    
			
		try {
			logger.info("getRecStatus........................."+vo.getRecStatus());
			logger.info("DELETE INSERT DATA......................");
			if (vo.getRecStatus()!= null && vo.getRecStatus().equals("on")) {
				stat = "A";
			} else {
				stat = "X";
			}	
			logger.info("Supplier...length......................"+supplier.length);
			
		
		    insertPrepStmtObject = new PrepStmtObject();	 
			String sqlQuery ="DELETE FROM cr_manufacture_supplier_mapping WHERE MANUFACTURE_ID="+vo.getManufacturerId();
			logger.info("In ........sqlQuery............."+ sqlQuery);
			insertPrepStmtObject.setSql(sqlQuery);
			qryList.add(insertPrepStmtObject);
				
			for(int k=0;k<supplier.length;k++){
			insertPrepStmtObject=null;	
			insertPrepStmtObject = new PrepStmtObject();
			bufInsSql = new StringBuffer();
			bufInsSql.append(" insert into cr_manufacture_supplier_mapping(MANUFACTURE_ID,MANUFACTURE_DESC,DEALER_ID,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE) values(");
			bufInsSql.append(" ?,");// MANUFACTURE_ID
			bufInsSql.append(" ?,");// MANUFACTURE_DESC
			bufInsSql.append(" ?,");// DEALER_ID
			bufInsSql.append(" ?,");//REC_STATUS 	
			bufInsSql.append(" ?,");// MAKER_ID
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
			bufInsSql.append(" ?,");// AUTHOR_ID
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
						
			if (CommonFunction.checkNull(vo.getLbxmachineManufact()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getLbxmachineManufact().trim());
			
			if (CommonFunction.checkNull(vo.getManufacturerDesc()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getManufacturerDesc());

			if (CommonFunction.checkNull(supplier[k]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(supplier[k]);

			if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(stat);
					
			if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerId().trim());

			if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerDate().toUpperCase().trim());
			
			if (CommonFunction.checkNull(vo.getAuthorId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getAuthorId().trim());

			if (CommonFunction.checkNull(vo.getAuthorDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getAuthorDate().toUpperCase().trim());
			
			insertPrepStmtObject.setSql(bufInsSql.toString());
			qryList.add(insertPrepStmtObject);
			}
			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);

			logger.info("In save/update-saveMfrSuppMappingRecord:::::::::::::::::::::::::"+ status);
			logger.info("save/update-saveMfrSuppMappingRecord:::::::::::::::"+ insertPrepStmtObject.printQuery());

			
		}
		 catch (Exception e) {
			e.printStackTrace();
		} finally {
			bufInsSql = null;
			insertPrepStmtObject = null;
			qryList = null;
		}

		return status;
	
	}
	
	
	
	




	
	
	
	 public ArrayList<ManufacturerSupplierMappingVO> searchManufacturerSupplierMappingList(ManufacturerSupplierMappingVO vo)
	{
		logger.info("In searchManufacturerSupplierMappingList::::::::::::::");
		ArrayList list=new ArrayList();
		StringBuilder bufInsSql=new StringBuilder();
		StringBuilder bufInsSqlTempCount = new StringBuilder();	
		try
		{
			ArrayList header=null;
					
			bufInsSql.append(" SELECT MANUFACTURE_ID,MANUFACTURE_ID,MANUFACTURE_DESC,case when REC_STATUS='A' then 'Active' else 'Inactive' end as REC_STATUS ");					
			bufInsSql.append(" FROM cr_manufacture_supplier_mapping	 WHERE MANUFACTURE_ID="+vo.getMappingId()+" ");
					
			logger.info("searchManufacturerSupplierMappingList:::::::::::::"+bufInsSql);
		    header = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());						
			for(int i=0;i<header.size();i++)
			{
				ArrayList header1=(ArrayList)header.get(i);
				if(header1!=null && header1.size()>0)
				{					
					ManufacturerSupplierMappingVO fieldVo = new ManufacturerSupplierMappingVO();					
					fieldVo.setManufacturerId((CommonFunction.checkNull(header1.get(0))).trim());	
					fieldVo.setLbxmachineManufact((CommonFunction.checkNull(header1.get(1))).trim());
					fieldVo.setManufacturerDesc((CommonFunction.checkNull(header1.get(2))).trim());
					fieldVo.setRecStatus((CommonFunction.checkNull(header1.get(3))).trim());								
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
	 
	 



	 public ArrayList<ManufacturerSupplierMappingVO> searchSupplierDescEdit(String mappingId) {
			ArrayList searchlist = new ArrayList();
			ManufacturerSupplierMappingVO userMasterVo = new ManufacturerSupplierMappingVO();
			ArrayList<ManufacturerSupplierMappingVO> supplierDescList = new ArrayList<ManufacturerSupplierMappingVO>();

			try {
				logger.info("In searchSupplierDescEdit().....................................Dao Impl");

				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql.append(" SELECT DISTINCT d.DEALER_ID,d.DEALER_DESC FROM CR_MANUFACTURE_SUPPLIER_MAPPING M JOIN CR_DSA_DEALER_M D ON D.DEALER_ID=M.DEALER_ID  WHERE m.MANUFACTURE_ID="+mappingId );
				
				logger.info("search Query==================================" + bufInsSql);
				searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
				logger.info("IN searchSupplierDescEdit() search query1 ===================="+ bufInsSql.toString());
				logger.info("searchSupplierDescEdit ==================" + searchlist.size());
				for (int i = 0; i < searchlist.size(); i++) {
					logger.info("branchDescList::::::::::::::::::::::::::" + searchlist.get(i).toString());
					ArrayList data = (ArrayList) searchlist.get(i);
					if (data.size() > 0) {
						ManufacturerSupplierMappingVO userMVo = new ManufacturerSupplierMappingVO();
						userMVo.setSupplierId((CommonFunction.checkNull(data.get(0)).toString()));
						userMVo.setSupplierDesc((CommonFunction.checkNull(data.get(1)).toString()));
						supplierDescList.add(userMVo);

					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return supplierDescList;
		}
	
	
		
}
