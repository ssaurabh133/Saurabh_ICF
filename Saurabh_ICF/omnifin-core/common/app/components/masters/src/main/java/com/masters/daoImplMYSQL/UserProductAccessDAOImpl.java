package com.masters.daoImplMYSQL;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;

import com.masters.dao.UserProductAccessDAO;
import com.masters.vo.UserProductAccessVo;
import com.connect.CommonFunction;
import com.connect.ConnectionDAOforEJB;
import com.connect.PrepStmtObject;



public class UserProductAccessDAOImpl implements UserProductAccessDAO{

	static final Logger logger = Logger.getLogger(UserProductAccessDAOImpl.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime = resource.getString("lbl.dateWithTimeInDao");
	String dateFormat = resource.getString("lbl.dateInDao");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));

	public boolean saveUserProductAccess(UserProductAccessVo vo, String [] productId) 
	{
		logger.info("## In saveUserProductAccess():");
		boolean status = false;
		
		ArrayList qryList = new ArrayList();
		StringBuffer bufInsSql=null;
		PrepStmtObject insertPrepStmtObject = null;
		String sta="X";
		try {
			
			if(vo.getRecStatus() != null && vo.getRecStatus().equalsIgnoreCase("on")){
				sta="A";
			}
			
				logger.info("## In saveUserProductAccess(): sta ==>> "+sta);
				String userId=vo.getLbxUserId();
				logger.info("## In saveUserProductAccess : userId==>>"+userId);
				String deleteUser=ConnectionDAOforEJB.singleReturn("select count(1) from cr_user_product_mapping_m where USER_ID='"+CommonFunction.checkNull(userId).trim()+"'");
				logger.info("## In saveUserProductAccess : deleteUser==>>"+deleteUser);
				if(!deleteUser.equalsIgnoreCase("0"))
				{
					logger.info("## In saveUserProductAccess :inside delete query");
					insertPrepStmtObject = new PrepStmtObject();
					bufInsSql = new StringBuffer();
					
					bufInsSql.append("delete from cr_user_product_mapping_m where USER_ID='"+CommonFunction.checkNull(userId).trim()+"'");
					
					insertPrepStmtObject.setSql(bufInsSql.toString());
					
					logger.info("## In saveUserProductAccess(): delete query ### "+ insertPrepStmtObject.printQuery());
					
					qryList.add(insertPrepStmtObject);
					
					insertPrepStmtObject =null;
					bufInsSql=null;
					
				}
				
				insertPrepStmtObject = new PrepStmtObject();
				bufInsSql = new StringBuffer();
				
				bufInsSql.append("UPDATE sec_user_m SET PRODUCT_ACCESS=? , MAKER_ID=?, MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),AUTHOR_ID=?,AUTHOR_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) WHERE USER_ID=?");
				
				if (CommonFunction.checkNull(vo.getRadioSelection()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getRadioSelection());
				
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
				
				if (CommonFunction.checkNull(userId).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(userId.trim());
				
				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("## In saveUserProductAccess : UPDATE query  ### "+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				
				insertPrepStmtObject =null;
				bufInsSql=null;
				
				
				if(vo.getRadioSelection().equalsIgnoreCase("S"))
				{
					logger.info("## In saveUserProductAccess : inside selective if block");
				for (int i = 0; i < productId.length; i++) {
					insertPrepStmtObject = new PrepStmtObject();
					bufInsSql = new StringBuffer();
					
					bufInsSql.append("INSERT INTO cr_user_product_mapping_m(USER_ID,PRODUCT_ID,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
					bufInsSql.append(" values ( ");
					bufInsSql.append(" ?,");//userid
					bufInsSql.append(" ?,");//productid
					bufInsSql.append(" ?,");//recstatus
					bufInsSql.append(" ?,");//makerid
					bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) ,");//makerdate
					bufInsSql.append(" ?,");//authorid
					bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) )");//authordate
					

					if (CommonFunction.checkNull(vo.getLbxUserId())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getLbxUserId()
								.toUpperCase());

					if (CommonFunction.checkNull(productId[i])
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(productId[i].toUpperCase());
					
					if (CommonFunction.checkNull(sta).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(sta.toUpperCase());

					if (CommonFunction.checkNull(vo.getMakerId())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo
								.getMakerId());

					if (CommonFunction.checkNull(vo.getMakerDate())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo
								.getMakerDate());
					if (CommonFunction.checkNull(vo.getAuthorId())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo
								.getAuthorId());

					if (CommonFunction.checkNull(vo.getAuthorDate())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo
								.getAuthorDate());

					insertPrepStmtObject.setSql(bufInsSql.toString());
					logger.info("## In saveUserProductAccess : insert query for selective ==>> "+ insertPrepStmtObject.printQuery());
					qryList.add(insertPrepStmtObject);
					
					insertPrepStmtObject =null;
					bufInsSql=null;
				}
						

				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("## In saveUserProductAccess : status ==>>"+ status);
				}
				else
				{
				//String DeleteBranchQuery="delete from SEC_USER_BRANCH_DTL where USER_ID='"+userMasterVo.getUserId() +"'";
//				logger.info("Delete Query for New User Against Branch Id:"+DeleteBranchQuery);
//				Connection con=ConnectionDAOforEJB.getConnection();
//				Statement st1=con.createStatement();
//				int a=st1.executeUpdate(DeleteBranchQuery)	;
//				ArrayList updateList=new ArrayList();
				logger.info("## In saveUserProductAccess : inside All else block");
				ArrayList <Object> allProductId=ConnectionDAOforEJB.sqlSelect("select PRODUCT_ID from cr_product_m");
				logger.info("## In saveUserProductAccess : allProductId"+allProductId);
				logger.info("## In saveUserProductAccess : allProductId.size()"+allProductId.size());
				if(allProductId.size()>0)
				{
				
					for (int i = 0; i < allProductId.size(); i++) {
						insertPrepStmtObject = new PrepStmtObject();
						bufInsSql = new StringBuffer();
						
						bufInsSql.append("INSERT INTO cr_user_product_mapping_m(USER_ID,PRODUCT_ID,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
						bufInsSql.append(" values ( ");
						bufInsSql.append(" ?,");//userid
						bufInsSql.append(" ?,");//productid
						bufInsSql.append(" ?,");//recstatus
						bufInsSql.append(" ?,");//makerid
						bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) ,");//makerdate
						bufInsSql.append(" ?,");//authorid
						bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) )");//authordate
						
						if (CommonFunction.checkNull(vo.getLbxUserId())
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(vo.getLbxUserId()
									.toUpperCase());

						if (CommonFunction.checkNull(((ArrayList<Object>) allProductId.get(i)).get(0)).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(((ArrayList<Object>) allProductId.get(i)).get(0).toString());
						
						if (CommonFunction.checkNull(sta).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(sta.toUpperCase());

						if (CommonFunction.checkNull(vo.getMakerId())
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(vo
									.getMakerId());

						if (CommonFunction.checkNull(vo.getMakerDate())
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(vo
									.getMakerDate());
						if (CommonFunction.checkNull(vo.getAuthorId())
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(vo
									.getAuthorId());

						if (CommonFunction.checkNull(vo.getAuthorDate())
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(vo
									.getAuthorDate());

						insertPrepStmtObject.setSql(bufInsSql.toString());
						logger.info("## In saveUserProductAccess : insert query for all product ### "+ insertPrepStmtObject.printQuery());
						qryList.add(insertPrepStmtObject);
						
						insertPrepStmtObject =null;
						bufInsSql=null;

						
					}
					
					
					status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
					logger.info("## In saveUserProductAccess : status ==>"	+ status);
					
				}
				
				}
			 
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}
	
	public ArrayList<UserProductAccessVo> getRecordAtSearch(int currentPageLink) 
	{
		logger.info("## In getRecordAtSearch :");
		ArrayList list=new ArrayList();
		StringBuilder bufInsSql=new StringBuilder();
		StringBuilder bufInsSqlTempCount = new StringBuilder();	
		try
		{
			ArrayList header=null;
			int count=0;
			int startRecordIndex=0;
			int endRecordIndex = no;				
			//bufInsSql.append(" select distinct a.USER_ID,  b.USER_NAME,case when a.REC_STATUS='X' then 'Inactive' when a.REC_STATUS='A' then 'Active' end from cr_user_product_mapping_m a, sec_user_m b where a.USER_ID=b.USER_ID");
			//bufInsSqlTempCount.append(" select count(1) from (select distinct a.USER_ID,  b.USER_NAME,case when a.REC_STATUS='X' then 'Inactive' when a.REC_STATUS='A' then 'Active' end from cr_user_product_mapping_m a, sec_user_m b where a.USER_ID=b.USER_ID) as b");
			
			 
			bufInsSql.append(" select  a.USER_ID,  b.USER_NAME, a.PRODUCT_ID, c.PRODUCT_DESC,case when a.REC_STATUS='A' then 'Active' when a.REC_STATUS='X' then 'Inactive' end as status from cr_user_product_mapping_m a, sec_user_m b,cr_product_m c where a.USER_ID=b.USER_ID and a.product_id=c.product_id");
			bufInsSqlTempCount.append(" select count(1) from (select  a.USER_ID,  b.USER_NAME, a.PRODUCT_ID, c.PRODUCT_DESC,case when a.REC_STATUS='A' then 'Active' when a.REC_STATUS='X' then 'Inactive' end as status from cr_user_product_mapping_m a, sec_user_m b,cr_product_m c where a.USER_ID=b.USER_ID and a.product_id=c.product_id) as b");
			//if()
			logger.info("## In getRecordAtSearch : select query ==>"+bufInsSql.toString());
			logger.info("## In getRecordAtSearch : count() query ==>"+bufInsSqlTempCount.toString());
			count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
			logger.info("## In getRecordAtSearch : count ==>> "+count);
			logger.info("## In getRecordAtSearch : currentPageLink ==>> "+currentPageLink);
			if(currentPageLink>1)
			{
				startRecordIndex = (currentPageLink-1)*no;
				endRecordIndex = no;
				logger.info("## In getRecordAtSearch : startRecordIndex ==>>"+startRecordIndex);
				logger.info("## In getRecordAtSearch : endRecordIndex ==>> "+endRecordIndex);
				bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
			}
			logger.info("query : "+bufInsSql);
		    header = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());						
			for(int i=0;i<header.size();i++)
			{
				ArrayList header1=(ArrayList)header.get(i);
				if(header1!=null && header1.size()>0)
				{					
					UserProductAccessVo fieldVo = new UserProductAccessVo();
					//fieldVo.setManufacturerId((CommonFunction.checkNull(header1.get(0))).trim());
					fieldVo.setShowUserDescSearch("<a href=openAddUserProductAccess.do?method=updateUserProductAccess&lbxUserId="+(CommonFunction.checkNull(header1.get(0))).trim()+" >"+(CommonFunction.checkNull(header1.get(1))).trim()+"</a>");
					fieldVo.setLbxProductId((CommonFunction.checkNull(header1.get(2))).trim());
					fieldVo.setShowProductDescSearch((CommonFunction.checkNull(header1.get(3))).trim());
					fieldVo.setRecStatus((CommonFunction.checkNull(header1.get(4))).trim());
							
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
	
	public ArrayList<UserProductAccessVo> getRecordForUpdate(String userId) 
	{
		logger.info("## In getRecordForUpdate(): ");
		ArrayList list=new ArrayList();
		StringBuilder bufInsSql=new StringBuilder();
		try
		{
			ArrayList header=null;
					
			bufInsSql.append(" select distinct a.USER_ID,  b.USER_NAME, a.REC_STATUS ,b.PRODUCT_ACCESS from cr_user_product_mapping_m a, sec_user_m b where a.USER_ID=b.USER_ID and a.USER_ID='"+(CommonFunction.checkNull(userId)).trim()+" '");
			logger.info("## In getRecordForUpdate(): select query ==> "+bufInsSql.toString());
		    header = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());	
		    
		    int size=header.size();
		   	for(int i=0;i<size;i++)
			{
				ArrayList header1=(ArrayList)header.get(i);
				if(header1!=null && header1.size()>0)
				{	
						logger.info("In header1 count::"+i);
					UserProductAccessVo fieldVo = new UserProductAccessVo();
					fieldVo.setLbxUserId((CommonFunction.checkNull(header1.get(0))).trim());
					fieldVo.setShowUserDesc((CommonFunction.checkNull(header1.get(1))).trim());
					fieldVo.setRecStatus((CommonFunction.checkNull(header1.get(2))).trim());
					fieldVo.setRadioSelection((CommonFunction.checkNull(header1.get(3))).trim());
							
					list.add(fieldVo);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
		finally
		{bufInsSql=null;}
			
				return list;
	}
	
	public ArrayList<UserProductAccessVo> searchUserProductRecord(UserProductAccessVo vo)
	{
		logger.info("## In searchUserProductRecord():");
		ArrayList list=new ArrayList();
		StringBuilder bufInsSql=new StringBuilder();
		StringBuilder bufInsSqlTempCount = new StringBuilder();	
		try
		{
			ArrayList header=null;
			int count=0;
			int startRecordIndex=0;
			int endRecordIndex = no;				
			bufInsSql.append(" select  a.USER_ID,  b.USER_NAME, a.PRODUCT_ID, c.PRODUCT_DESC, case when a.REC_STATUS='A' then 'Active' when a.REC_STATUS='X' then 'Inactive' end as status from cr_user_product_mapping_m a, sec_user_m b,cr_product_m c where a.USER_ID=b.USER_ID and a.product_id=c.product_id ");
			bufInsSqlTempCount.append(" select count(1) from (select  a.USER_ID,  b.USER_NAME, a.PRODUCT_ID, c.PRODUCT_DESC, case when a.REC_STATUS='A' then 'Active' when a.REC_STATUS='X' then 'Inactive' end as status from cr_user_product_mapping_m a, sec_user_m b,cr_product_m c where a.USER_ID=b.USER_ID and a.product_id=c.product_id) as b where true ");
			logger.info("In searchUserProductRecord : vo.getLbxProductId() "+vo.getLbxProductId());
			logger.info("In searchUserProductRecord : vo.getLbxUserId() "+vo.getLbxUserId());
			
			if(!(CommonFunction.checkNull(vo.getLbxProductId()).equalsIgnoreCase("")))
			{
				bufInsSql.append("and a.PRODUCT_ID='"+(CommonFunction.checkNull(vo.getLbxProductId())).trim()+"' ");
				bufInsSqlTempCount.append(" and PRODUCT_ID='"+(CommonFunction.checkNull(vo.getLbxProductId())).trim()+"' "); 
			}
			if(!(CommonFunction.checkNull(vo.getLbxUserId()).equalsIgnoreCase("")))
			{
				bufInsSql.append(" and a.USER_ID='"+(CommonFunction.checkNull(vo.getLbxUserId())).trim()+"'");
				bufInsSqlTempCount.append(" and USER_ID='"+(CommonFunction.checkNull(vo.getLbxUserId())).trim()+"'"); 
			}
			
			//bufInsSqlTempCount.append(")as f");
			
			logger.info("In searchUserProductRecord : select query ==>>"+bufInsSql.toString());
			logger.info("In searchUserProductRecord : count query ==>>"+bufInsSqlTempCount.toString());
			count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
			logger.info("In searchUserProductRecord : count ==>>"+count);
			logger.info("current PAge Link no .................... "+vo.getCurrentPageLink());
			if(vo.getCurrentPageLink()>1)
			{
				startRecordIndex = (vo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
				bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
			}
			logger.info("## In searchUserProductRecord(): select query ==>> "+bufInsSql.toString());
			logger.info("## In searchUserProductRecord(): count() query ==>"+bufInsSqlTempCount.toString());
		    header = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());	
		    logger.info("## In searchUserProductRecord(): header.size() ==> "+header.size());
//			for(int i=0;i<header.size();i++)
//			{
//				ArrayList header1=(ArrayList)header.get(i);
//				if(header1!=null && header1.size()>0)
//				{					
//					MakeModelmasterVO fieldVo = new MakeModelmasterVO();
//					//fieldVo.setManufacturerId((CommonFunction.checkNull(header1.get(0))).trim());
//					fieldVo.setManufacturerId("<a href=updateMakeModel.do?method=updateMakeModelMaster&manufacturerId="+(CommonFunction.checkNull(header1.get(0))).trim()+"&make="+(CommonFunction.checkNull(header1.get(2))).trim()+"&model="+(CommonFunction.checkNull(header1.get(3))).trim()+">"+(CommonFunction.checkNull(header1.get(0))).trim()+"</a>");
//					fieldVo.setMenufacturer((CommonFunction.checkNull(header1.get(1))).trim());
//					fieldVo.setMake((CommonFunction.checkNull(header1.get(2))).trim());
//					fieldVo.setModel((CommonFunction.checkNull(header1.get(3))).trim());
//					fieldVo.setLtv((CommonFunction.checkNull(header1.get(4))).trim());
//					fieldVo.setRecStatus((CommonFunction.checkNull(header1.get(5))).trim());			
//					fieldVo.setTotalRecordSize(count);					
//					list.add(fieldVo);
//				}
//			}
		    for(int i=0;i<header.size();i++)
			{
				ArrayList header1=(ArrayList)header.get(i);
				if(header1!=null && header1.size()>0)
				{					
					UserProductAccessVo fieldVo = new UserProductAccessVo();
					//fieldVo.setManufacturerId((CommonFunction.checkNull(header1.get(0))).trim());
					fieldVo.setShowUserDescSearch("<a href=openAddUserProductAccess.do?method=updateUserProductAccess&lbxUserId="+(CommonFunction.checkNull(header1.get(0))).trim()+" >"+(CommonFunction.checkNull(header1.get(1))).trim()+"</a>");
					fieldVo.setLbxProductId((CommonFunction.checkNull(header1.get(2))).trim());
					fieldVo.setShowProductDescSearch((CommonFunction.checkNull(header1.get(3))).trim());
					fieldVo.setRecStatus((CommonFunction.checkNull(header1.get(4))).trim());
							
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
	
	public ArrayList<UserProductAccessVo> getProductRecordForUpdate(String userId) 
	{
		logger.info("## In getProductRecordForUpdate(): ");
		 
		ArrayList<UserProductAccessVo> productList =new  ArrayList<UserProductAccessVo>();
		
		ArrayList searchList=new ArrayList();
		ArrayList StageList=new ArrayList();
		UserProductAccessVo VO=null;
		try{
              String query="select a.PRODUCT_ID,b.PRODUCT_DESC  from cr_user_product_mapping_m a , cr_product_m b where a.PRODUCT_ID=b.PRODUCT_ID and a.USER_ID='"+userId+"'";
              logger.info("In npastage......Dao Impl"+query);
				
              searchList=ConnectionDAOforEJB.sqlSelect(query);
              logger.info("query::::::::::::::::::"+query);
              int size = searchList.size();
              logger.info("size:::::::"+size);
              for(int i=0; i<size; i++){
            	  StageList=(ArrayList)searchList.get(i);
            	  if(StageList.size()>0){
            		  VO=new UserProductAccessVo();
            		  
            		  VO.setLbxProductId(CommonFunction.checkNull(StageList.get(0)));
					  VO.setShowProductDesc(CommonFunction.checkNull(StageList.get(1)));
					  productList.add(VO);
					  logger.info("In getProductRecordForUpdate : productList==>"+productList);
					  logger.info("productList size:::::::::::::"+productList.size());
            	  }
              }
            	  
              }
				catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			 searchList=null;
			 StageList=null;
			 VO=null;
		}
		return productList;

	}

}
