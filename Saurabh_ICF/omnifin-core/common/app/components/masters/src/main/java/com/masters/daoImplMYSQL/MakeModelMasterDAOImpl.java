package com.masters.daoImplMYSQL;

import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.connect.CommonFunction;
import com.connect.ConnectionDAOforEJB;
import com.connect.PrepStmtObject;
import com.masters.dao.MakeModelMasterDAO;
import com.masters.vo.MakeModelmasterVO;

public class MakeModelMasterDAOImpl implements MakeModelMasterDAO
{
	static final Logger logger = Logger.getLogger(MakeModelMasterDAOImpl.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	

	public boolean saveMakeModelRecord(MakeModelmasterVO vo,String state) 
	{
		//ArrayList qryList = new ArrayList();
		String proValue=null;
		boolean status = false;
		logger.info("In saveMakeModelRecord........MakeModelMasterDAOImpl..............saurabh..............."+state);
		//StringBuilder bufInsSql = new StringBuilder();
		//PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		ArrayList<Object> in =new ArrayList<Object>();
		ArrayList<Object> out =new ArrayList<Object>();
		ArrayList outMessages = new ArrayList();
		String s1="";
		String s2="";
		String	date=CommonFunction.changeFormat(vo.getMakerDate());
		
		try 
		{
			


			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getProductCategory()).trim())); // DEALER_DESC
			in.add(state); // BP_TYPE
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMake()).trim())); // make 
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getModel()).trim())); // make 
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getType()).trim())); // make 
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLtv()).trim())); // make 
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getUsesTypeId()).trim()));
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getRecStatus()).trim())); // make 
			
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()));// MAKER_ID
			in.add(date);// MAKER_DATE
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getAuthorId()).trim()));// AUTHOR_ID
			in.add(date);// AUTHOR_DATE
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getManufactId()).trim())); // manufacturer ID
			//mradul add
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getVct()).trim())); // vehicle category
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxProductID()).trim())); // PRODUCT_ID
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxSchemeId()).trim())); // SCHEME_ID
			
			out.add(s1);
			out.add(s2);
			logger.info("gggggggggggggg"+CommonFunction.checkNull(vo.getStateId()).trim());
			logger.info("vehicle category :::::::"+CommonFunction.checkNull(vo.getVct()).trim());
			logger.info("make model _SAVE("+in.toString()+","+out.toString()+")");
			logger.info("before call***************");
			outMessages=(ArrayList) ConnectionDAOforEJB.callSP("MAKE_MODEL_SAVE",in,out);
			s1=CommonFunction.checkNull(outMessages.get(0));
			s2=CommonFunction.checkNull(outMessages.get(1));
			logger.info("after  call***************");
			
//			bufInsSql.append(" insert into cr_make_model_master (PRODUCT_CATEGORY,STATE_ID,MAKE,MODEL,MAKE_TYPE,LTV,USES_TYPE,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)values(");
//			bufInsSql.append(" ?,");//PRODUCT_CATEGORY
//			bufInsSql.append(" ?,");//STATE_ID
//			bufInsSql.append(" ?,");//MAKE
//			bufInsSql.append(" ?,");//MODEL
//			bufInsSql.append(" ?,");//MAKE_TYPE
//			bufInsSql.append(" ?,");//LTV
//			bufInsSql.append(" ?,");//USES_TYPE
//			bufInsSql.append(" ?,");//REC_STATUS
//			bufInsSql.append(" ?,");//MAKER_ID
//			bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),");//MAKER_DATE
//			bufInsSql.append(" ?,");//AUTHOR_ID
//			bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND))");//AUTHOR_DATE	
//			
//			if (CommonFunction.checkNull(vo.getProductCategory()).equalsIgnoreCase(""))
//				insertPrepStmtObject.addNull();
//			else
//				insertPrepStmtObject.addString(vo.getProductCategory().trim());
//			
//			
//			if (CommonFunction.checkNull(vo.getStateId()).equalsIgnoreCase(""))
//				insertPrepStmtObject.addNull();
//			else
//				insertPrepStmtObject.addString(vo.getStateId().trim());
//			
//			if (CommonFunction.checkNull(vo.getMake()).equalsIgnoreCase(""))
//				insertPrepStmtObject.addNull();
//			else
//				insertPrepStmtObject.addString(vo.getMake().toUpperCase().trim());
//			
//			if (CommonFunction.checkNull(vo.getModel()).equalsIgnoreCase(""))
//				insertPrepStmtObject.addNull();
//			else
//				insertPrepStmtObject.addString(vo.getModel().toUpperCase().trim());
//			
//			if (CommonFunction.checkNull(vo.getType()).equalsIgnoreCase(""))
//				insertPrepStmtObject.addNull();
//			else
//				insertPrepStmtObject.addString(vo.getType().trim());
//			
//			
//			if (CommonFunction.checkNull(vo.getLtv()).equalsIgnoreCase(""))
//				insertPrepStmtObject.addNull();
//			else
//				insertPrepStmtObject.addString(vo.getLtv().toUpperCase().trim());
//			
//			if (CommonFunction.checkNull(vo.getUsesTypeId()).equalsIgnoreCase(""))
//				insertPrepStmtObject.addNull();
//			else
//				insertPrepStmtObject.addString(vo.getUsesTypeId().trim());
//			
//			
//			if (CommonFunction.checkNull(vo.getRecStatus()).equalsIgnoreCase(""))
//				insertPrepStmtObject.addNull();
//			else
//				insertPrepStmtObject.addString(vo.getRecStatus().toUpperCase().trim());
//			
//			if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
//				insertPrepStmtObject.addNull();
//			else
//				insertPrepStmtObject.addString(vo.getMakerId().toUpperCase().trim());
//			
//			if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
//				insertPrepStmtObject.addNull();
//			else
//				insertPrepStmtObject.addString(vo.getMakerDate().toUpperCase().trim());
//			
//			if (CommonFunction.checkNull(vo.getAuthorId()).equalsIgnoreCase(""))
//				insertPrepStmtObject.addNull();
//			else
//				insertPrepStmtObject.addString(vo.getAuthorId().toUpperCase().trim());
//			
//			if (CommonFunction.checkNull(vo.getAuthorDate()).equalsIgnoreCase(""))
//				insertPrepStmtObject.addNull();
//			else
//				insertPrepStmtObject.addString(vo.getAuthorDate().toUpperCase().trim());			
//			
//			insertPrepStmtObject.setSql(bufInsSql.toString());
//			qryList.add(insertPrepStmtObject);
//			logger.info("IN saveMakeModelRecord() insert query1 ### "+ insertPrepStmtObject.printQuery());
//			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
//			logger.info("In saveMakeModelRecord......................"+ status);
//			logger.info("saveMakeModelRecord...."+insertPrepStmtObject.printQuery());

	} 
		catch (Exception e) {
		e.printStackTrace();
	}
		
		
        if(s1.equalsIgnoreCase("S"))
        {
        	status=true;
        	proValue=s1;
        }
        else{
        	status=false;
          proValue=s2;
		} 
	
		
	
	
	return status;

	}
	
//changed by abhimanyu on 18/07/2012
	public boolean checkRecord(MakeModelmasterVO vo) 
	{
		logger.info("In checkRecord");
		ArrayList list=new ArrayList();
		int count=0;
		String findCount;
		boolean status = false;
		try
		{				
			StringBuffer bufInsSql=new StringBuffer();			
			bufInsSql.append("select count(1) from cr_make_model_master a " +
					" inner join cr_make_model_master_state_mapping b on a.make_model_id=b.make_model_id" +
					" where PRODUCT_CATEGORY='"+vo.getProductCategory()+"' " +
					" and MAKE='"+vo.getMake()+"' and MODEL='"+vo.getModel()+"' and MAKE_TYPE='"+vo.getType()+"' and " +
					" USES_TYPE='"+vo.getUsesTypeId()+"' and MANUFACTURER_ID='"+vo.getManufactId()+"' and b.state_id in ("+vo.getStateId()+");");
			logger.info("query ok: "+bufInsSql);
			findCount=ConnectionDAOforEJB.singleReturn(bufInsSql.toString());
			count=Integer.parseInt(findCount);		    
			logger.info("In checkRecord    count : "+count);		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
		if(count>0)
			status=true;
		logger.info("In checkRecord    status : "+status);
		return status;
	}
	public int getMakeModelID(MakeModelmasterVO vo) 
	{
		logger.info("In getMakeModelID");
		ArrayList list=new ArrayList();
		int count=0;
		String findCount;
		boolean status = false;
		try
		{				
			StringBuffer bufInsSql=new StringBuffer();			
			bufInsSql.append("select a.MAKE_MODEL_ID from cr_make_model_master a " +
					" inner join cr_make_model_master_state_mapping b on a.make_model_id=b.make_model_id " +
					" where PRODUCT_CATEGORY='"+vo.getProductCategory()+"' and" +
					" MAKE='"+vo.getMake()+"' and MODEL='"+vo.getModel()+"' and MAKE_TYPE='"+vo.getType()+"'and USES_TYPE='"+vo.getUsesTypeId()+"'" +
					" and b.state_id in ("+vo.getStateId()+");");
			logger.info("query return make model id : "+bufInsSql);
			findCount=ConnectionDAOforEJB.singleReturn(bufInsSql.toString());
			count=Integer.parseInt(findCount);   
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
		
		return count;
	}
	
	public boolean checkRecordforUpdate(MakeModelmasterVO vo) 
	{
		logger.info("In checkRecordforUpdate");
		ArrayList list=new ArrayList();
		int count=0;
		String findCount;
		boolean status = false;
		try
		{				
			StringBuffer bufInsSql=new StringBuffer();			
			bufInsSql.append("select count(1) from cr_make_model_master where PRODUCT_CATEGORY='"+vo.getProductCategory()+"' " +
					"  and MAKE='"+vo.getMake()+"' and MODEL='"+vo.getModel()+"' " +
					"  and MAKE_TYPE='"+vo.getType()+"'" +
					"  and USES_TYPE='"+vo.getUsesTypeId()+"' and MANUFACTURER_ID='"+vo.getManufactId()+"' and MAKE_MODEL_ID <> '"+vo.getMakeModelId()+"' AND PRODUCT_ID='" +vo.getLbxProductID()+"'");
			logger.info("query : checkRecordforUpdate"+bufInsSql);
			findCount=ConnectionDAOforEJB.singleReturn(bufInsSql.toString());
			count=Integer.parseInt(findCount);		    
			logger.info("In checkRecordforUpdate  count : "+count);		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
		if(count>0)
			status=false;
		logger.info("In checkRecordforUpdate  status : "+status);
		return status;
	}
	
	
	//changed by abhimanyu on 18/07/2012

	public ArrayList<MakeModelmasterVO> getMakeModelrecords(int currentPageLink) 
	{
		logger.info("In getMakeModelrecords");
		ArrayList list=new ArrayList();
		StringBuilder bufInsSql=new StringBuilder();
		StringBuilder bufInsSqlTempCount = new StringBuilder();	
		try
		{
			ArrayList header=null;
			int count=0;
			int startRecordIndex=0;
			int endRecordIndex = no;				
			bufInsSql.append("select cr_make_model_master.MAKE_MODEL_ID, cr_make_model_master.PRODUCT_CATEGORY," +
					"  cr_make_model_master.MAKE,cr_make_model_master.MODEL," +
					" cr_make_model_master.LTV, case when cr_make_model_master.MAKE_TYPE='N' then 'NEW'" +
					" when cr_make_model_master.MAKE_TYPE='O' then 'OLD' end as MAKE_TYPE ," +
					" cr_make_model_master.USES_TYPE,(select generic_master.DESCRIPTION from generic_master where cr_make_model_master.USES_TYPE=generic_master.VALUE AND cr_make_model_master.PRODUCT_CATEGORY=generic_master.PARENT_VALUE )UsesDecription," +
					" case when cr_make_model_master.REC_STATUS='A' then 'Active'" +
					" when cr_make_model_master.REC_STATUS='X' then 'Inactive'" +
					" end as Status from cr_make_model_master" );
											
			     logger.info("current PAge Link no .................... "+currentPageLink);
			    
			     bufInsSqlTempCount.append("select count(0) from cr_make_model_master");
			     count=Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
			     
			if(currentPageLink>1)			{
				startRecordIndex = (currentPageLink-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
				bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
			}
			logger.info("query : "+bufInsSql);
		    header = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());						
			for(int i=0;i<header.size();i++)
			{
				ArrayList header1=(ArrayList)header.get(i);
				if(header1!=null && header1.size()>0)
				{					
					MakeModelmasterVO fieldVo = new MakeModelmasterVO();
					//fieldVo.setManufacturerId((CommonFunction.checkNull(header1.get(0))).trim());
					fieldVo.setMakeModelId("<a href=updateMakeModel.do?method=updateMakeModelMaster&makeModelId="+(CommonFunction.checkNull(header1.get(0))).trim()+" >"+(CommonFunction.checkNull(header1.get(0))).trim()+"</a>");
					fieldVo.setProductCategory((CommonFunction.checkNull(header1.get(1))).trim());							
					fieldVo.setMake((CommonFunction.checkNull(header1.get(2))).trim());
					fieldVo.setModel((CommonFunction.checkNull(header1.get(3))).trim());					
					fieldVo.setLtv((CommonFunction.checkNull(header1.get(4))).trim());
					fieldVo.setType((CommonFunction.checkNull(header1.get(5))).trim());
					fieldVo.setUsesTypeId((CommonFunction.checkNull(header1.get(6))).trim());
					fieldVo.setUsesType((CommonFunction.checkNull(header1.get(7))).trim());					
					fieldVo.setRecStatus((CommonFunction.checkNull(header1.get(8))).trim());			
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
	

	public ArrayList<MakeModelmasterVO> searchMakeModelRecords(MakeModelmasterVO vo)
	{
		logger.info("In searchMakeModelRecords");
		ArrayList list=new ArrayList();
		StringBuilder bufInsSql=new StringBuilder();
		StringBuilder bufInsSqlTempCount = new StringBuilder();	
		try
		{
			ArrayList header=null;
			int count=0;
			int startRecordIndex=0;
			int endRecordIndex = no;				
			bufInsSql.append("select cr_make_model_master.MAKE_MODEL_ID, cr_make_model_master.PRODUCT_CATEGORY," +
					"  cr_make_model_master.MAKE,cr_make_model_master.MODEL," +
					" cr_make_model_master.LTV, case when cr_make_model_master.MAKE_TYPE='N' then 'NEW'" +
					" when cr_make_model_master.MAKE_TYPE='O' then 'OLD' end as MAKE_TYPE ," +
					" cr_make_model_master.USES_TYPE,(select generic_master.DESCRIPTION from generic_master where cr_make_model_master.USES_TYPE=generic_master.VALUE AND cr_make_model_master.PRODUCT_CATEGORY=generic_master.PARENT_VALUE )UsesDecription," +
					" case when cr_make_model_master.REC_STATUS='A' then 'Active'" +
					" when cr_make_model_master.REC_STATUS='X' then 'Inactive'" +
					" end as Status from cr_make_model_master" +
					"  where 'a'='a'");	
			   if(vo.getProductCategory()==null || vo.getProductCategory().equalsIgnoreCase("") )
			   {
				   
			   }
			   else
			   {
				   bufInsSql.append(" and PRODUCT_CATEGORY= '"+vo.getProductCategory()+"'");  
			   }
			   
			   if(vo.getMake()==null || vo.getMake().equalsIgnoreCase("") )
			   {
				   
			   }
			   else
			   {
				   bufInsSql.append(" and MAKE= '"+vo.getMake()+"'");  
			   }
			   if(vo.getModel()==null || vo.getModel().equalsIgnoreCase("") )				   
			   {
				   
			   }
			   else
			   {
				   bufInsSql.append(" and MODEL= '"+vo.getModel()+"'");  
			   }
			   if(vo.getUsesTypeId()==null || vo.getUsesTypeId().equalsIgnoreCase("") )
			   {
				   
			   }
			   else
			   {
				   bufInsSql.append(" and USES_TYPE= '"+vo.getUsesTypeId()+"'");  
			   }
			   
			    
			     bufInsSqlTempCount.append("select count(0) from cr_make_model_master where 'a'='a'");
			     if(vo.getProductCategory()==null || vo.getProductCategory().equalsIgnoreCase("") )
				   {
					   
				   }
				   else
				   {
					   bufInsSqlTempCount.append(" and PRODUCT_CATEGORY= '"+vo.getProductCategory()+"'");  
				   }
				   
				   if(vo.getMake()==null || vo.getMake().equalsIgnoreCase("") )
				   {
					   
				   }
				   else
				   {
					   bufInsSqlTempCount.append(" and MAKE= '"+vo.getMake()+"'");  
				   }
				   if(vo.getModel()==null || vo.getModel().equalsIgnoreCase("") )				   
				   {
					   
				   }
				   else
				   {
					   bufInsSqlTempCount.append(" and MODEL= '"+vo.getModel()+"'");  
				   }
				   if(vo.getUsesTypeId()==null || vo.getUsesTypeId().equalsIgnoreCase("") )
				   {
					   
				   }
				   else
				   {
					   bufInsSqlTempCount.append(" and USES_TYPE= '"+vo.getUsesTypeId()+"'");  
				   }
			     
			     
			     
			     count=Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
		
			logger.info("searchMakeModelRecords query for search : "+bufInsSql);
		    header = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());						
			for(int i=0;i<header.size();i++)
			{
				ArrayList header1=(ArrayList)header.get(i);
				if(header1!=null && header1.size()>0)
				{					
					MakeModelmasterVO fieldVo = new MakeModelmasterVO();
					//fieldVo.setManufacturerId((CommonFunction.checkNull(header1.get(0))).trim());
					fieldVo.setMakeModelId("<a href=updateMakeModel.do?method=updateMakeModelMaster&makeModelId="+(CommonFunction.checkNull(header1.get(0))).trim()+" >"+(CommonFunction.checkNull(header1.get(0))).trim()+"</a>");
					fieldVo.setProductCategory((CommonFunction.checkNull(header1.get(1))).trim());										
					fieldVo.setMake((CommonFunction.checkNull(header1.get(2))).trim());
					fieldVo.setModel((CommonFunction.checkNull(header1.get(3))).trim());					
					fieldVo.setLtv((CommonFunction.checkNull(header1.get(4))).trim());
					fieldVo.setType((CommonFunction.checkNull(header1.get(5))).trim());
					fieldVo.setUsesTypeId((CommonFunction.checkNull(header1.get(6))).trim());
					fieldVo.setUsesType((CommonFunction.checkNull(header1.get(7))).trim());					
					fieldVo.setRecStatus((CommonFunction.checkNull(header1.get(8))).trim());			
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
	

	public ArrayList<MakeModelmasterVO> getParticularRecord(String makeModelId) 
	{
		logger.info("In getParticularRecord "+makeModelId);
		ArrayList list=new ArrayList();	
		//ArrayList listMap=new ArrayList();
		StringBuilder bufInsSql=new StringBuilder();
		StringBuilder bufInsSql1=new StringBuilder();
		try
		{
			ArrayList header=null;
			ArrayList hdr=null;
					
			bufInsSql.append("select a.PRODUCT_CATEGORY," +
					" a.MAKE,a.MODEL," +
					" a.LTV, case when a.MAKE_TYPE='N' then 'N'" +
					" when a.MAKE_TYPE='O' then 'O' end as MAKE_TYPE ," +
					" a.USES_TYPE,(select generic_master.DESCRIPTION from generic_master where a.USES_TYPE=generic_master.VALUE AND a.PRODUCT_CATEGORY=generic_master.PARENT_VALUE )UsesDecription," +
					" a.REC_STATUS,b.DEALER_DESC,a.MANUFACTURER_ID,a.VEHICLE_CATEGORY,a.PRODUCT_ID,c.PRODUCT_DESC,a.SCHEME_ID,d.SCHEME_DESC from cr_make_model_master a " +
					" left outer join cr_dsa_dealer_m b on b.DEALER_ID=a.MANUFACTURER_ID " +	
					" LEFT JOIN cr_product_m c on c.PRODUCT_ID=a.PRODUCT_ID " +
					" LEFT JOIN cr_scheme_m d on d.SCHEME_ID=a.SCHEME_ID " +
					" where a.MAKE_MODEL_ID='"+makeModelId+"'");	
			
			bufInsSql1.append("SELECT M.STATE_ID,S.STATE_DESC FROM cr_make_model_master_state_mapping  M LEFT OUTER JOIN " +
					" com_state_m S ON M.STATE_ID=S.STATE_ID WHERE M.MAKE_MODEL_ID='"+makeModelId+"'");
			
			

			logger.info("query : "+bufInsSql.toString());  
			logger.info("query FOR MAPPING : "+bufInsSql1.toString());
		    header = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
		    hdr=ConnectionDAOforEJB.sqlSelect(bufInsSql1.toString());
		    int size=header.size();
		     int size1=hdr.size();
		    logger.info("size of "+size);
		    logger.info("size of my list"+size1);
			for(int i=0;i<header.size();i++)
			{
				
				ArrayList header1=(ArrayList)header.get(i);
				if(header1!=null && header1.size()>0)
				{					
					MakeModelmasterVO fieldVo = new MakeModelmasterVO();				
					fieldVo.setProductCategory((CommonFunction.checkNull(header1.get(0))).trim());			
					fieldVo.setMake((CommonFunction.checkNull(header1.get(1))).trim());
					fieldVo.setModel((CommonFunction.checkNull(header1.get(2))).trim());					
					fieldVo.setLtv((CommonFunction.checkNull(header1.get(3))).trim());
					fieldVo.setType((CommonFunction.checkNull(header1.get(4))).trim());
					fieldVo.setUsesTypeId((CommonFunction.checkNull(header1.get(5))).trim());
					fieldVo.setUsesType((CommonFunction.checkNull(header1.get(6))).trim());					
					fieldVo.setRecStatus((CommonFunction.checkNull(header1.get(7))).trim());										
					fieldVo.setManufact((CommonFunction.checkNull(header1.get(8))).trim());
					fieldVo.setManufactId((CommonFunction.checkNull(header1.get(9))).trim());
					fieldVo.setVct((CommonFunction.checkNull(header1.get(10))).trim());
					fieldVo.setLbxProductID((CommonFunction.checkNull(header1.get(11))).trim());
					fieldVo.setProduct((CommonFunction.checkNull(header1.get(12))).trim());
					fieldVo.setLbxSchemeId((CommonFunction.checkNull(header1.get(13))).trim());
					fieldVo.setScheme((CommonFunction.checkNull(header1.get(14))).trim());
					list.add(fieldVo);
				}
			}
			//logger.info("okkkkkkkkkkk");
			
			for(int i=0;i<hdr.size();i++)
			{
				//logger.info("okkkkkkkkkkk111111111111111111111111111111");
				ArrayList hdr1=(ArrayList)hdr.get(i);
				if(hdr1!=null && hdr1.size()>0)
				{					
					MakeModelmasterVO fieldVo1 = new MakeModelmasterVO();	
					//logger.info("value of statai *****"+hdr1.get(0));
					//logger.info("value of statai *****"+hdr1.get(1));
					
					fieldVo1.setState((CommonFunction.checkNull(hdr1.get(0))).trim());
					fieldVo1.setStateId((CommonFunction.checkNull(hdr1.get(1))).trim());
					list.add(fieldVo1);
				}
			}
			
			//logger.info("okkkkkkkkkkk222222222222222");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
		finally
		{bufInsSql=null;}
			
				return list;
	}
	

	public String updateMakeModelRecord(MakeModelmasterVO vo,String[] stateId)
	{
		logger.info("In updateMakeModelRecord");	
		boolean status=false;
		ArrayList qryList = new ArrayList();
		String result=null;
		String ltv=vo.getLtv();
	   //String makeModelId=""; 

		PrepStmtObject insertPrepStmtObject=null;
		try 
		{
			logger.info("value of car:::"+vo.getVct());
				insertPrepStmtObject = new PrepStmtObject();						
				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql.append(" update cr_make_model_master set PRODUCT_CATEGORY=?,MAKE=?,MODEL=? , MAKE_TYPE=?, LTV=?, USES_TYPE=?,REC_STATUS=?,MAKER_ID=?,MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),AUTHOR_ID=?,AUTHOR_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),MANUFACTURER_ID=?,VEHICLE_CATEGORY=?,PRODUCT_ID=?,SCHEME_ID=? where MAKE_MODEL_ID=?");
				
				if (CommonFunction.checkNull(vo.getProductCategory()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getProductCategory());
				
								
				if (CommonFunction.checkNull(vo.getMake()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMake().trim());
				
				if (CommonFunction.checkNull(vo.getModel()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getModel().trim());
				
				if (CommonFunction.checkNull(vo.getType()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getType().trim());
				
				
				if (CommonFunction.checkNull(vo.getLtv()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addFloat(Float.parseFloat(vo.getLtv()));
				
				if (CommonFunction.checkNull(vo.getUsesTypeId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getUsesTypeId().trim());
				
				
				if (CommonFunction.checkNull(vo.getRecStatus()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getRecStatus().trim());
				
				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId().trim());
				
				if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate().trim());
				
				if (CommonFunction.checkNull(vo.getAuthorId()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getAuthorId().trim());
				
				if (CommonFunction.checkNull(vo.getAuthorDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getAuthorDate().trim());
				//Nishant space starts
				if (CommonFunction.checkNull(vo.getManufactId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getManufactId().trim());
				//Nishant space end
				
				if (CommonFunction.checkNull(vo.getVct()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getVct().trim());
				
				if (CommonFunction.checkNull(vo.getLbxProductID()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxProductID().trim());
				
				if (CommonFunction.checkNull(vo.getLbxSchemeId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxSchemeId().trim());
				
				if (CommonFunction.checkNull(vo.getMakeModelId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakeModelId().trim());		
				
				String makeModelId=vo.getMakeModelId();
				
				logger.info("make model id**************"+makeModelId);
				
				
				
				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("make model id**************"+vo.getMakeModelId());
				logger.info("IN updateMakeModelRecord() update query ::::::: "+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);	
            // status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				
				insertPrepStmtObject=null;
				insertPrepStmtObject = new PrepStmtObject();
				//bufInsSql=null;
				bufInsSql = new StringBuffer();
				
				
				 
				String query = "DELETE FROM cr_make_model_master_state_mapping WHERE MAKE_MODEL_ID='"+vo.getMakeModelId()+ "'";
				logger.info("In insertUserBranchMaster.....................................Dao Impl"+ query);
				insertPrepStmtObject.setSql(query);
				qryList.add(insertPrepStmtObject);
				//status = ConnectionDAOforEJB.sqlInsUpdDelete(updatelist);
				
//				String stat=null;
//
//				if (vo.getRecStatus()!= null
//						&& vo.getRecStatus().equals("on")) {
//					stat = "A";
//				} else {
//					stat = "X";
//
//				}

				if(stateId!=null){
					if(stateId.length>0){
				for (int i = 0; i < stateId.length; i++) {
					logger.info("userName[i]"+stateId[i]);
				
					insertPrepStmtObject=null;
					insertPrepStmtObject = new PrepStmtObject();
					bufInsSql=null;
					bufInsSql = new StringBuffer();
					bufInsSql.append("INSERT INTO cr_make_model_master_state_mapping(MAKE_MODEL_ID,STATE_ID,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
					bufInsSql.append(" values ( ");
					bufInsSql.append(" ?,");
					bufInsSql.append(" ?,");
					bufInsSql.append(" ?,");
					bufInsSql.append(" ?,");
					bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),");
					bufInsSql.append(" ?,");
					bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND))");
					logger.info("make model ******"+vo.getMakeModelId());

					if (CommonFunction.checkNull(vo.getMakeModelId()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getMakeModelId());

					if (CommonFunction.checkNull(stateId[i]).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(stateId[i]);
					
				/*	if (CommonFunction.checkNull(userMasterVo.getAllselection()[0]).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(userMasterVo.getAllselection()[0]);
					*/
					

					if (CommonFunction.checkNull(vo.getRecStatus()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getRecStatus().trim());
					//----------------------------------
					if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getMakerId());
					if (CommonFunction.checkNull(vo.getMakerDate())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getMakerDate());
					
					if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getMakerId());
					if (CommonFunction.checkNull(vo.getMakerDate())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getMakerDate());
					//----------------------------------

					insertPrepStmtObject.setSql(bufInsSql.toString());
					logger.info("IN insertUserMaster() insert query1 ### saurabh "+ insertPrepStmtObject.printQuery());
					qryList.add(insertPrepStmtObject);
				}
				//status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(insertList);
				
				}
				}		
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
						
				if(status){
					result="saved";
				}else{
					result="notsaved";
				}
				
				

			} 
		catch (Exception e) 
		{e.printStackTrace();}
		return result;
	}
}