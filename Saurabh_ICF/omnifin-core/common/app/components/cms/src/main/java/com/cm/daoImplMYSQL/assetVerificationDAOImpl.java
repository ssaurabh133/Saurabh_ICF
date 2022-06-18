package com.cm.daoImplMYSQL;


import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;


import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.cm.dao.assetVerificationDAO;
import com.cm.vo.*;

import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;
import com.logger.LoggerMsg;


public class assetVerificationDAOImpl implements assetVerificationDAO {
	
	private static final Logger logger = Logger.getLogger(assetVerificationDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));

public String modifyAssetVerification(AssetVerificationVO assetVerificationVO,String assetVerInitId,String loanId,String[] assetIDList,String appraisertype,String Appraiserid,String Appraiser) {
	ArrayList deleteList = new ArrayList();
					ArrayList qryList =  new ArrayList();
					boolean status1=false;
					boolean status=false;
					ArrayList assetList =  new ArrayList();
					ArrayList list =  new ArrayList();
					String val="";
					String arr1="";
					String[] arr=null;
					String result="";
					String appName="";
					StringBuffer bufInsSql =null;
					PrepStmtObject insertPrepStmtObject = null;	
					int maxId=0;
					logger.info("In AssetVerification...Dao Impl.");
					String exInId="";
					
						exInId=assetVerificationVO.getLbxextApprHID();
					
					logger.info("exInId------------->"+exInId);
					
					if(assetVerificationVO.getInternalAppraiser()!=null && !assetVerificationVO.getInternalAppraiser().equalsIgnoreCase(""))
					{
						appName=assetVerificationVO.getInternalAppraiser();
					}
					else
					{
						appName=assetVerificationVO.getExternalAppraiser() ;
					}
					logger.info("appName---.getInternalAppraiser()---getExternalAppraiser()------->"+appName);
			
					try{
						for(int i=0;i<assetIDList.length;i++)
						{
						String query="delete from cr_asset_verification_dtl where LOAN_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+"'"+
								 " and APPRAISER_TYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(appraisertype).trim())+"'"+
								 " and APPRAISER_NAME='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(Appraiserid).trim())+"'"+
								 " and ASSET_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(assetIDList[i]).trim())+"' AND REC_STATUS='P'";
						logger.info("delete query--->"+query.toString());
						deleteList.add(query.toString());
						
						status = ConnectionDAO.sqlInsUpdDelete(deleteList);
						}
						for(int p=0;p<assetIDList.length;p++)
						{
								String query="SELECT DISTINCT  ASSET_ID FROM  cr_asset_verification_dtl WHERE LOAN_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+"' AND (REC_STATUS='P' OR REC_STATUS='I') "+
								"AND APPRAISER_NAME='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(Appraiserid).trim())+"' AND APPRAISER_TYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(appraisertype).trim())+"'";
						
								logger.info("query--->"+query);
								assetList= ConnectionDAO.sqlSelect(query);
						}
								
								logger.info("assetList--size---->"+assetList.size());
								if(assetList.size()>0){
									for(int j=0;j<assetList.size();j++)
									{
										list=(ArrayList) assetList.get(j);
										logger.info("list.get(0)--->"+ list.get(0));
										arr1=arr1+list.get(0)+"/";
										logger.info("arr1--->"+ arr1);
										arr=arr1.split("/");
										logger.info("arr--->"+ arr);
									}
									
								}

								if(CommonFunction.checkNull(arr).length()>0){
								for(int j=0;j<assetIDList.length;j++)
								{
									/*if(list.size()>0){*/
										
									/*	logger.info("list.get(0)------>"+list.get(0));*/
										for(int k=0;k<arr.length;k++){
											logger.info("assetIDList[j]--->"+assetIDList[j]);
											logger.info("arr[k]--->"+arr[k]);
										if(Integer.parseInt(assetIDList[j])==Integer.parseInt(arr[k])){
											val="assetsnotsaved";
											if(result==""){
												result=arr[k]+" ";
											}else
											{
											result=arr[k]+","+result;
											}
											logger.info("val----->"+val);
											logger.info("result--->"+result);
											break;
										}
										logger.info("in forr-------db---arr---------");
										}
										logger.info("in forr-----screen-------assetIDList-------");
									}
								}
								
						logger.info("result----------->"+val);
						for(int i=0;i<assetIDList.length;i++)
						{
						if(val.equalsIgnoreCase("")){
							logger.info("iN INSERT BLOCK----------------->");
							logger.info("iN INSERT BLOCK----------------->");
						 bufInsSql =	new StringBuffer();
						 insertPrepStmtObject = new PrepStmtObject();
						 
						
	  
							 bufInsSql.append("insert into cr_asset_verification_dtl(ASSET_ID,LOAN_ID,INITIATION_DATE,APPRAISER_TYPE,APPRAISER_ID,APPRAISER_NAME," + 
						 		"  REC_STATUS,MAKER_ID,MAKER_DATE)");
							 
						
								 bufInsSql.append(" values ( ");
								 bufInsSql.append(" ?," );
								 bufInsSql.append(" ?," );
								 bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"')," );
								 bufInsSql.append(" ?," );
								 bufInsSql.append(" ?," );
								 bufInsSql.append(" ?," );
								 bufInsSql.append(" 'P'," );
								 bufInsSql.append(" ?," );
								  
								 bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) )" );
								 
								 logger.info("assetIDList--------->"+assetIDList[i]);
								 if(CommonFunction.checkNull(assetIDList[i]).equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString((CommonFunction.checkNull(assetIDList[i]).trim()));
								
								 if(CommonFunction.checkNull(loanId).equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
								else
										insertPrepStmtObject.addString((CommonFunction.checkNull(loanId).trim()));
								 
								 if(CommonFunction.checkNull(assetVerificationVO.getMakerDate()).equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
								else
										insertPrepStmtObject.addString((CommonFunction.checkNull(assetVerificationVO.getMakerDate()).trim()));
							 
								if(CommonFunction.checkNull(appraisertype).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString((CommonFunction.checkNull(appraisertype).trim()));
								
								if(CommonFunction.checkNull(Appraiser).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
							    else
								insertPrepStmtObject.addString((CommonFunction.checkNull(Appraiser).trim()));
								
								if(CommonFunction.checkNull(Appraiserid).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
							    else
								insertPrepStmtObject.addString((CommonFunction.checkNull(Appraiserid).trim()));

								
								if(CommonFunction.checkNull(assetVerificationVO.getMakerID()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString((CommonFunction.checkNull(assetVerificationVO.getMakerID()).trim()));
								
								if(CommonFunction.checkNull(assetVerificationVO.getMakerDate()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString((CommonFunction.checkNull(assetVerificationVO.getMakerDate()).trim()));
									
									insertPrepStmtObject.setSql(bufInsSql.toString());
									qryList.add(insertPrepStmtObject);
							           logger.info("In bufInsSql............."+bufInsSql.toString());
									
						}}
						if(qryList.size()>0){
						    status1=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				              logger.info("In saveAssetVerification......................"+status1);
				               
				              if(status1)
				  			{
				            		StringBuilder query3= new StringBuilder();
				            		StringBuilder id= new StringBuilder();
				            		
				            	  query3.append("Select distinct max(ASSET_VERIFICATION_ID) from cr_asset_verification_dtl for update");
				  				 id.append(ConnectionDAO.singleReturn(query3.toString()));
				  				 maxId=Integer.parseInt(id.toString());
				  				 logger.info("maxId : "+maxId);
				  				
				  				 query3 = null;
				  				 id = null;
				  				result="saved";
				  			}
				  		    
				  		  else
				  		  {
				  			  maxId=0;
				  			  result="datanotsaved";
				  		  }
				  		
						}
					}catch(Exception e){
						e.printStackTrace();
					}
					
					logger.info("result---->"+result);
					return result;
					 
				}		

public boolean modifyForwaAssetVerification(AssetVerificationVO assetVerificationVO,String appraiserId,String loanId,String[] assetIdlist, String appraiser,String appr) {
	
	ArrayList qryList =  new ArrayList();
	boolean status1=false;
	
	StringBuffer bufInsSql =null;
	PrepStmtObject insertPrepStmtObject = null;	
	

	logger.info("In AssetVerification...Dao Impl.");
	logger.info("In modifyForwaAssetVerification...");
	

	  
	try{
		logger.info("In appr IN....modifyForwaAssetVerification().Dao Impl."+appr);
		logger.info("appraiserId---------->"+appraiserId);
		 for(int i=0;i<assetIdlist.length;i++){
			 bufInsSql =	new StringBuffer();
			 insertPrepStmtObject = new PrepStmtObject();
		 bufInsSql.append("update cr_asset_verification_dtl set ASSET_ID=?,LOAN_ID=?,APPRAISER_TYPE=?,APPRAISER_ID=?,"+
		                   " APPRAISER_NAME=?,MAKER_ID=?,MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),REC_STATUS='I' " +
		                   		"where ASSET_ID = '"+assetIdlist[i]+"' and LOAN_ID = '"+loanId+"' and APPRAISER_TYPE='"+appraiser+"' and APPRAISER_ID='"+appraiserId+"' and REC_STATUS='P' ");


			 if(CommonFunction.checkNull(assetIdlist[i]).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((CommonFunction.checkNull(assetIdlist[i]).trim()));
			 
			 if(CommonFunction.checkNull(loanId).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((CommonFunction.checkNull(loanId)));
			 
			 if(CommonFunction.checkNull(assetVerificationVO.getAppraiser()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((CommonFunction.checkNull(assetVerificationVO.getAppraiser()).trim()));
		 
						
			if(CommonFunction.checkNull(appraiserId).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
		    else
			insertPrepStmtObject.addString((CommonFunction.checkNull(appraiserId).trim()));
			
									
			if(CommonFunction.checkNull(appr).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
		    else
			insertPrepStmtObject.addString((CommonFunction.checkNull(appr).trim()));
			//---------------------------------------------------------
			if ((CommonFunction.checkNull(assetVerificationVO.getMakerID())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((assetVerificationVO.getMakerID()).trim());
			if ((CommonFunction.checkNull(assetVerificationVO.getMakerDate()).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(assetVerificationVO.getMakerDate()).trim());
			//---------------------------------------------------------

									
		      logger.info("In modifyAssetVerification......bufInsSql.toString()....update forward......."+bufInsSql.toString());
					
			insertPrepStmtObject.setSql(bufInsSql.toString());
			qryList.add(insertPrepStmtObject);

		
		    
               

	} status1=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);

    logger.info("In modifyAssetVerification......................"+status1);	 
	}catch(Exception e){
		e.printStackTrace();
	}
	
	
	return status1;
	 
}	

public String saveAssetVerification(String[] loanIDList
		, String[] assetIDList,
		String[] assetDescriptionList,
		AssetVerificationVO assetVerificationVO) {
	String val="";   
	ArrayList qryList =  new ArrayList();
	ArrayList assetList=new ArrayList ();
	ArrayList list=new ArrayList ();
	ArrayList arrrList =  new ArrayList();
	String[] assetIDnewList=null;
	String assetID="";
	String result="";
	String[] assetnewarray=null;
	String arr1="";
	String[] arr=null;
	boolean status1=false;
	int maxId=0;
	logger.info("In AssetVerification...Dao Impl.");
	StringBuffer bufInsSql =null;
	PrepStmtObject insertPrepStmtObject = null;	
	String exInId="";
	String appName="";
	if(assetVerificationVO.getLbxUserId()!=null && !assetVerificationVO.getLbxUserId().equalsIgnoreCase(""))
	{
		exInId=assetVerificationVO.getLbxUserId();
	}
	else
	{
		exInId=assetVerificationVO.getLbxextApprHID();
	}
	if(assetVerificationVO.getInternalAppraiser()!=null && !assetVerificationVO.getInternalAppraiser().equalsIgnoreCase(""))
	{
		appName=assetVerificationVO.getInternalAppraiser();
	}
	else
	{
		appName=assetVerificationVO.getExternalAppraiser() ;
	}
	logger.info("In saveAssetVerification..exInId id Impl."+exInId);
	
	//logger.info("In saveAssetVerification..userId.Dao Impl."+assetVerificationVO.getLbxUserId());
	
	logger.info("arr1-----value--->"+arr1);
	try{
		
		for(int p=0;p<assetIDList.length;p++)
		{
				String query="SELECT DISTINCT  ASSET_ID FROM  cr_asset_verification_dtl WHERE LOAN_ID='"+loanIDList[p]+"' AND (REC_STATUS='P' OR REC_STATUS='I') "+
				"AND APPRAISER_ID='"+exInId+"' AND APPRAISER_TYPE='"+assetVerificationVO.getAppraiser()+"'";
		
				logger.info("query--->"+query);
				assetList= ConnectionDAO.sqlSelect(query);
		}
				
				logger.info("assetList--size---->"+assetList.size());
				if(assetList.size()>0){
					for(int j=0;j<assetList.size();j++)
					{
						list=(ArrayList) assetList.get(j);
						logger.info("list.get(0)--->"+ list.get(0));
						arr1=arr1+list.get(0)+"/";
						logger.info("arr1--->"+ arr1);
						arr=arr1.split("/");
						logger.info("arr--->"+ arr);
					}
					
				}

				if(CommonFunction.checkNull(arr).length()>0){
				for(int j=0;j<assetIDList.length;j++)
				{
					/*if(list.size()>0){*/
						
					/*	logger.info("list.get(0)------>"+list.get(0));*/
						for(int k=0;k<arr.length;k++){
							logger.info("assetIDList[j]--->"+assetIDList[j]);
							logger.info("arr[k]--->"+arr[k]);
						if(Integer.parseInt(assetIDList[j])==Integer.parseInt(arr[k])){
							val="assetsnotsaved";
							if(result==""){
								result=arr[k]+" ";
							}else
							{
							result=arr[k]+","+result;
							}
							logger.info("val----->"+val);
							logger.info("result--->"+result);
							break;
						}
						logger.info("in forr-------db---arr---------");
						}
						logger.info("in forr-----screen-------assetIDList-------");
					}
				}
				
		logger.info("result----------->"+val);
		for(int i=0;i<assetIDList.length;i++)
		{
		if(val.equalsIgnoreCase("")){
			logger.info("iN INSERT BLOCK----------------->");
		 bufInsSql =	new StringBuffer();
		 insertPrepStmtObject = new PrepStmtObject();
	 
			 bufInsSql.append("insert into cr_asset_verification_dtl(ASSET_ID,LOAN_ID,INITIATION_DATE,APPRAISER_TYPE,APPRAISER_ID,APPRAISER_NAME," + 
		 		"  REC_STATUS,MAKER_ID,MAKER_DATE)");
			 
		
				 bufInsSql.append(" values ( ");
				 bufInsSql.append(" ?," );
				 bufInsSql.append(" ?," );
				 bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"')," );
				 bufInsSql.append(" ?," );
				 bufInsSql.append(" ?," );
				 bufInsSql.append(" ?," );
				 bufInsSql.append(" 'P'," );
				 bufInsSql.append(" ?," );
				  
				 bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) )" );
				 
				 logger.info("assetIDList--------->"+assetIDList[i]);
				 if(CommonFunction.checkNull(assetIDList[i]).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(assetIDList[i]).trim()));
				
				 if(CommonFunction.checkNull(loanIDList[i]).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
				else
						insertPrepStmtObject.addString((CommonFunction.checkNull(loanIDList[i]).trim()));
				 
				 if(CommonFunction.checkNull(assetVerificationVO.getMakerDate()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
				else
						insertPrepStmtObject.addString((CommonFunction.checkNull(assetVerificationVO.getMakerDate()).trim()));
			 
				if(CommonFunction.checkNull(assetVerificationVO.getAppraiser()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(assetVerificationVO.getAppraiser()).trim()));
				
				if(CommonFunction.checkNull(exInId).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			    else
				insertPrepStmtObject.addString((CommonFunction.checkNull(exInId).trim()));
				
				if(CommonFunction.checkNull(appName).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			    else
				insertPrepStmtObject.addString((CommonFunction.checkNull(appName).trim()));

				
				if(CommonFunction.checkNull(assetVerificationVO.getMakerID()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(assetVerificationVO.getMakerID()).trim()));
				
				if(CommonFunction.checkNull(assetVerificationVO.getMakerDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(assetVerificationVO.getMakerDate()).trim()));
					
					insertPrepStmtObject.setSql(bufInsSql.toString());
					qryList.add(insertPrepStmtObject);

					
		}}
		if(qryList.size()>0){
		    status1=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
              logger.info("In saveAssetVerification......................"+status1);
               
              if(status1)
  			{
            		StringBuilder query3= new StringBuilder();
            		StringBuilder id= new StringBuilder();
            		
            	  query3.append("Select distinct max(ASSET_VERIFICATION_ID) from cr_asset_verification_dtl update");
  				 id.append(ConnectionDAO.singleReturn(query3.toString()));
  				 maxId=Integer.parseInt(id.toString());
  				 logger.info("maxId : "+maxId);
  				
  				 query3 = null;
  				 id = null;
  				 result="saved";
  			}
  		    
  		  else
  		  {
  			  maxId=0;
  			  result="datanotsaved";
  		  }
  		
		}
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		arr1=null;
		arr=null;
	}

	return result;
	
 
}

public boolean forwardAssetVerification(String[] loanIDList,
		String[] loanAccNoList, String[] assetIDList,
		String[] assetDescriptionList,
		AssetVerificationVO assetVerificationVO) {
	
	
	ArrayList qryList =  new ArrayList();
	boolean status1=false;
	
	StringBuffer bufInsSql =null;
	PrepStmtObject insertPrepStmtObject = null;	
	

	logger.info("In AssetVerification...Dao Impl.");
	logger.info("In forwardAssetVerification...");
	 
	  
	try{
		for(int i=0;i<loanIDList.length;i++)
		{
			
			
		 bufInsSql =	new StringBuffer();
		 insertPrepStmtObject = new PrepStmtObject();
	 
			 bufInsSql.append("insert into cr_asset_verification_dtl(ASSET_ID,LOAN_ID,INITIATION_DATE,APPRAISER_TYPE,APPRAISER_ID,APPRAISER_NAME," + 
		 		"  REC_STATUS,MAKER_ID,MAKER_DATE)");
			 
		
				 bufInsSql.append(" values ( ");
				 bufInsSql.append(" ?," );
				 bufInsSql.append(" ?," );
				 bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"')," );
				 bufInsSql.append(" ?," );
				 bufInsSql.append(" ?," );
				 bufInsSql.append(" ?," );
				 bufInsSql.append(" 'I'," );
				 bufInsSql.append(" ?," );
				  
				 bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) )" );
				 

				 if(CommonFunction.checkNull(assetIDList[i]).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(assetIDList[i]).trim()));
				 
				 if(CommonFunction.checkNull(loanIDList[i]).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanIDList[i]).trim()));
				 
				 if(CommonFunction.checkNull(assetVerificationVO.getMakerDate()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
				else
						insertPrepStmtObject.addString((CommonFunction.checkNull(assetVerificationVO.getMakerDate()).trim()));
			 
			
				if(CommonFunction.checkNull(assetVerificationVO.getAppraiser()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(assetVerificationVO.getAppraiser()).trim()));

				if(CommonFunction.checkNull(assetVerificationVO.getLbxextApprHID()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			    else
				insertPrepStmtObject.addString((CommonFunction.checkNull(assetVerificationVO.getLbxextApprHID()).trim()));
				
				if(CommonFunction.checkNull(assetVerificationVO.getInternalAppraiser()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			    else
				insertPrepStmtObject.addString((CommonFunction.checkNull(assetVerificationVO.getInternalAppraiser()).trim()));
				 

				if(CommonFunction.checkNull(assetVerificationVO.getMakerID()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(assetVerificationVO.getMakerID()).trim()));
				
				if(CommonFunction.checkNull(assetVerificationVO.getMakerDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(assetVerificationVO.getMakerDate()).trim()));
					
					insertPrepStmtObject.setSql(bufInsSql.toString());
					qryList.add(insertPrepStmtObject);

					
		}
		    status1=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
              logger.info("In forwardAssetVerification......................"+status1);
               

	}catch(Exception e){
		e.printStackTrace();
	}
	
	
	return status1;
	
 
}

public ArrayList assetVerifcationSearch(AssetVerificationVO assetVerificationVO,HttpServletRequest request) {
	
	ArrayList<AssetVerificationVO> assetList = new ArrayList<AssetVerificationVO>();
	logger.info("In assetVerifcationSearch: ");
	StringBuilder loanNo= new StringBuilder();
	StringBuilder assetID= new StringBuilder();
	StringBuilder customerName= new StringBuilder();
	
//	 String loanNo="";
//	 String assetID="";
//	 String customerName="";
//	 String userName="";
	 int count=0;
	 int startRecordIndex=0;
	 int endRecordIndex = no;
	 logger.info("In assetVerifcationSearch...Dao Impl.");
		logger.info("here userid++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+assetVerificationVO.getReportingToUserId());
//			try{
//				String userNameQ="select USER_NAME from sec_user_m where REC_STATUS='A' and USER_ID='"+assetVerificationVO.getReportingToUserId()+"'";
//				userName=ConnectionDAO.singleReturn(userNameQ);
//				logger.info("userNameQ: "+userNameQ+" userName: "+userName);
//			}
//			catch(Exception e)
//			{
//				e.printStackTrace();
//			}

    ArrayList mainlist=new ArrayList();
	  ArrayList subList=new ArrayList();
	try{
		

		loanNo.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(assetVerificationVO.getLbxLoanNo()).trim()));
		assetID.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(assetVerificationVO.getLbxAssetID()).trim()));
		customerName.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(assetVerificationVO.getCustomerName()).trim()));
		               logger.info("In assetVerifcationSearch().....................................Dao Impl"+loanNo+"sdfghjk"+assetID);

       	               StringBuffer bufInsSqlTempCount = new StringBuffer();
       	               StringBuffer bufInsSql =    new StringBuffer();

//       	         bufInsSql.append("select distinct B.LOAN_NO,A.CUSTOMER_NAME,cr_product_m.PRODUCT_DESC,cr_scheme_m.SCHEME_DESC,if(C.APPRAISER_TYPE='EA','External','Internal')as APPRAISER_TYPE, ifnull((select AGENCY_NAME from com_agency_m where AGENCY_CODE=C.APPRAISER_ID),C.APPRAISER_NAME) AS APPRAISER_NAME,ASSET_VERIFICATION_ID, (SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=C.MAKER_ID) " +
//       	         		"MAKER_ID from cr_loan_dtl B left outer join gcd_customer_m A on(B.LOAN_CUSTOMER_ID=A.CUSTOMER_ID)  " +
//       	         		"join cr_asset_verification_dtl C on(C.LOAN_ID=B.LOAN_ID and c.REC_STATUS='P')left outer join  cr_product_m on(B.LOAN_PRODUCT=cr_product_m.PRODUCT_ID)" +
//       	         		"left outer join cr_scheme_m on(B.LOAN_SCHEME=cr_scheme_m.SCHEME_ID)where B.LOAN_REPAYMENT_TYPE='I' AND B.LOAN_BRANCH='"+assetVerificationVO.getBranchId()+"'");
       	            bufInsSql.append("select distinct B.LOAN_NO,A.CUSTOMER_NAME,cr_product_m.PRODUCT_DESC,cr_scheme_m.SCHEME_DESC," +
       	            		"if(C.APPRAISER_TYPE='EA','External','Internal')as APPRAISER_TYPE, ifnull((select AGENCY_NAME from com_agency_m " +
       	            		"where AGENCY_CODE=C.APPRAISER_ID),C.APPRAISER_NAME) AS APPRAISER_NAME,(SELECT USER_NAME FROM SEC_USER_M WHERE " +
       	            		"USER_ID=C.MAKER_ID) MAKER_ID,B.LOAN_ID,C.APPRAISER_ID from cr_loan_dtl B left outer join gcd_customer_m A on(B.LOAN_CUSTOMER_ID=A.CUSTOMER_ID) " +
       	            		" join cr_asset_verification_dtl C on(C.LOAN_ID=B.LOAN_ID and c.REC_STATUS='P')left outer join  cr_product_m " +
       	            		"on(B.LOAN_PRODUCT=cr_product_m.PRODUCT_ID)left outer join cr_scheme_m on(B.LOAN_SCHEME=cr_scheme_m.SCHEME_ID)where" +
       	            		" B.LOAN_REPAYMENT_TYPE='I' AND B.LOAN_BRANCH='"+assetVerificationVO.getBranchId()+"'"); 
       	               
/*       	               bufInsSqlTempCount.append("select count(1) from(select distinct B.LOAN_NO,A.CUSTOMER_NAME,cr_product_m.PRODUCT_DESC," +
       	               		"cr_scheme_m.SCHEME_DESC,if(C.APPRAISER_TYPE='EA','External','Internal')as APPRAISER_TYPE, ifnull((select AGENCY_NAME " +
       	               		"from com_agency_m where AGENCY_CODE=C.APPRAISER_ID),C.APPRAISER_NAME) AS APPRAISER_NAME,ASSET_VERIFICATION_ID, (SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=C.MAKER_ID) MAKER_ID from cr_loan_dtl B left outer join gcd_customer_m A on(B.LOAN_CUSTOMER_ID=A.CUSTOMER_ID)  join cr_asset_verification_dtl C on(C.LOAN_ID=B.LOAN_ID and c.REC_STATUS='P')left outer join  cr_product_m on(B.LOAN_PRODUCT=cr_product_m.PRODUCT_ID)left outer join cr_scheme_m on(B.LOAN_SCHEME=cr_scheme_m.SCHEME_ID)where B.LOAN_REPAYMENT_TYPE='I' AND B.LOAN_BRANCH='"+assetVerificationVO.getBranchId()+"'");	         
       	         */
       	            bufInsSqlTempCount.append("select count(1) from(select distinct B.LOAN_NO,A.CUSTOMER_NAME,cr_product_m.PRODUCT_DESC,cr_scheme_m.SCHEME_DESC," +
       	            		"if(C.APPRAISER_TYPE='EA','External','Internal')as APPRAISER_TYPE, ifnull((select AGENCY_NAME from com_agency_m " +
       	            		"where AGENCY_CODE=C.APPRAISER_ID),C.APPRAISER_NAME) AS APPRAISER_NAME,(SELECT USER_NAME FROM SEC_USER_M WHERE " +
       	            		"USER_ID=C.MAKER_ID) MAKER_ID,B.LOAN_ID,C.APPRAISER_ID from cr_loan_dtl B left outer join gcd_customer_m A on(B.LOAN_CUSTOMER_ID=A.CUSTOMER_ID) " +
       	            		" join cr_asset_verification_dtl C on(C.LOAN_ID=B.LOAN_ID and c.REC_STATUS='P')left outer join  cr_product_m " +
       	            		"on(B.LOAN_PRODUCT=cr_product_m.PRODUCT_ID)left outer join cr_scheme_m on(B.LOAN_SCHEME=cr_scheme_m.SCHEME_ID)where" +
       	            		" B.LOAN_REPAYMENT_TYPE='I' AND B.LOAN_BRANCH='"+assetVerificationVO.getBranchId()+"'");
       	         
       	         
       	         if(!((loanNo.toString()).equalsIgnoreCase(""))){
       	        	bufInsSql.append(" AND C.LOAN_ID='"+loanNo+"' ");	
       	        	bufInsSqlTempCount.append(" AND C.LOAN_ID='"+loanNo+"' ");	
       	        }
       	        
       	     if(!((customerName.toString()).equalsIgnoreCase(""))){
       	        	bufInsSql.append(" AND A.CUSTOMER_NAME like '%"+customerName+"%' ");	
       	        	bufInsSqlTempCount.append(" AND A.CUSTOMER_NAME like '%"+customerName+"%' ");
       	        }
       	         
       	     if(!((assetID.toString()).equalsIgnoreCase(""))){
       	        	bufInsSql.append(" AND C.ASSET_ID='"+assetID+"'");	
       	        	bufInsSqlTempCount.append(" AND C.ASSET_ID='"+assetID+"'");
       	        }
       	  if (((StringEscapeUtils.escapeSql(CommonFunction.checkNull(assetVerificationVO.getReportingToUserId())).trim().equalsIgnoreCase(""))))
			{
       		bufInsSql.append(" AND a.MAKER_ID='"+StringEscapeUtils.escapeSql(assetVerificationVO.getUserId()).trim()+"' ");	
       		bufInsSqlTempCount.append(" AND d.MAKER_ID='"+StringEscapeUtils.escapeSql(assetVerificationVO.getUserId()).trim()+"'");			
			}
			else{
				bufInsSql.append(" AND C.MAKER_ID='"+StringEscapeUtils.escapeSql(assetVerificationVO.getReportingToUserId()).trim()+"' ");	
				bufInsSqlTempCount.append(" AND C.MAKER_ID='"+StringEscapeUtils.escapeSql(assetVerificationVO.getReportingToUserId()).trim()+"'");
			    }

       	  bufInsSqlTempCount.append(")as f");
//                if((!(loanNo.equalsIgnoreCase(""))) && (!(assetID.equalsIgnoreCase(""))) ) {
// 	                  bufInsSql.append(" AND C.LOAN_ID='"+loanNo+"' AND C.ASSET_ID='"+assetID+"'");
//
// 	               }


       	             logger.info("Query-----"+bufInsSql.toString());
      	            mainlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
      	          count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));

      	        if(((loanNo.toString()).trim()==null && (customerName.toString()).trim()==null && (assetID.toString()).trim()==null) || ((loanNo.toString()).trim().equalsIgnoreCase("") && (customerName.toString()).trim().equalsIgnoreCase("") && (assetID.toString()).trim().equalsIgnoreCase("")) || assetVerificationVO.getCurrentPageLink()>1)
    			{
    			
    			LoggerMsg.info("current PAge Link no .................... "+assetVerificationVO.getCurrentPageLink());
    			if(assetVerificationVO.getCurrentPageLink()>1)
    			{
    				startRecordIndex = (assetVerificationVO.getCurrentPageLink()-1)*no;
    				endRecordIndex = no;
    				LoggerMsg.info("startRecordIndex .................... "+startRecordIndex);
    				LoggerMsg.info("endRecordIndex .................... "+endRecordIndex);
    			}
    			
    			bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
    			}
      	          
	for(int i=0;i<mainlist.size();i++){

		subList=(ArrayList)mainlist.get(i);
		logger.info("In assetVerifcationSearch..."+subList.size());
		logger.info("In assetVerifcationSearch..."+subList);
		if(subList.size()>0){
			
			AssetVerificationVO assetVerificationSearchVo=new AssetVerificationVO();
			
			assetVerificationSearchVo.setVerCodeModify("<a href=assetVerificationInit.do?method=modifyasset&loanid="+
					(CommonFunction.checkNull(subList.get(7)).trim())+"&appraisaltype="+(CommonFunction.checkNull(subList.get(4)).trim())+
					"&Appraiserid="+(CommonFunction.checkNull(subList.get(8)).trim())
					+ ">"
					+ CommonFunction.checkNull(subList.get(0)).toString() + "</a>");
			
			assetVerificationSearchVo.setLoanNo((CommonFunction.checkNull(subList.get(0)).trim()));
			assetVerificationSearchVo.setCustomerName((CommonFunction.checkNull(subList.get(1)).trim()));
			assetVerificationSearchVo.setLoanProduct((CommonFunction.checkNull(subList.get(2)).trim()));
			assetVerificationSearchVo.setLoanScheme((CommonFunction.checkNull(subList.get(3)).trim()));
			assetVerificationSearchVo.setAppraiserType((CommonFunction.checkNull(subList.get(4)).trim()));
			assetVerificationSearchVo.setAppraiserName((CommonFunction.checkNull(subList.get(5)).trim()));
			assetVerificationSearchVo.setReportingToUserId((CommonFunction.checkNull(subList.get(6)).trim()));
			assetVerificationSearchVo.setLoanID((CommonFunction.checkNull(subList.get(7)).trim()));
			if((CommonFunction.checkNull(subList.get(4)).trim()).equals("Internal")){
				assetVerificationSearchVo.setLbxUserId((CommonFunction.checkNull(subList.get(8)).trim()));
			}else{
				assetVerificationSearchVo.setLbxextApprHID((CommonFunction.checkNull(subList.get(8)).trim()));
			}
			assetVerificationSearchVo.setTotalRecordSize(count);
			assetList.add(assetVerificationSearchVo);
		}
}
	}catch(Exception e){
		e.printStackTrace();
	}
	finally
	{
		loanNo = null;
		assetID = null;
		customerName = null;
	}

	
	return assetList;
	 
}

public ArrayList searchAssetVerRepCap(AssetVerificationVO assetVerificationVO) {
	
	ArrayList<AssetVerificationVO> assetList = new ArrayList<AssetVerificationVO>();
	logger.info("In searchAssetVerRepCap: ");
	logger.info("In AssetVerification...Dao Impl.");
	int count=0;
	int startRecordIndex=0;
	int endRecordIndex = no;
	StringBuilder lbxLoanNoHID= new StringBuilder();
	StringBuilder assetID= new StringBuilder();
	StringBuilder customerName= new StringBuilder();
//	 String lbxLoanNoHID="";
//	 String assetID="";
//	 String customerName="";
	 String userName="";
	 ArrayList mainlist=new ArrayList();
	 ArrayList subList=new ArrayList();

		logger.info("here userid++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+assetVerificationVO.getReportingToUserId());
			try{
				String userNameQ="select USER_NAME from sec_user_m where REC_STATUS='A' and USER_ID='"+assetVerificationVO.getReportingToUserId()+"'";
				userName=ConnectionDAO.singleReturn(userNameQ);
				logger.info("userNameQ: "+userNameQ+" userName: "+userName);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		
	try{
		
		lbxLoanNoHID.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(assetVerificationVO.getLbxLoanNoHID()).trim()));
		assetID.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(assetVerificationVO.getAssetID()).trim()));
		customerName.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(assetVerificationVO.getCustomerName()).trim()));
	
		logger.info("customerName----"+customerName);
		logger.info("In searchAssetVerRepCap().....................................Dao Impl");

       	               StringBuffer bufInsSqlTempCount=new StringBuffer();
       	               StringBuffer bufInsSql =    new StringBuffer();

                bufInsSql.append( "select a.asset_verification_id,a.loan_id,a.asset_id,b.loan_no,c.ASSET_COLLATERAL_DESC,a.INITIATION_DATE,a.APPRAISER_NAME" +
                		" from cr_asset_collateral_m c,cr_loan_dtl b,gcd_customer_m d,cr_asset_verification_dtl a" +
                		" left join com_agency_m ag on a.APPRAISER_ID=ag.AGENCY_CODE " +  
                		" left join com_agency_user_mapping agm on ag.AGENCY_CODE=agm.AGENCY_CODE " +  
                		" where b.LOAN_customer_id=d.CUSTOMER_ID and a.loan_id = b.loan_id and a.asset_id = c.asset_id AND B.LOAN_REPAYMENT_TYPE='I' AND A.REC_STATUS='I' " +
                		" AND B.LOAN_BRANCH='"+assetVerificationVO.getBranchId()+"' and (a.APPRAISER_ID = '"+assetVerificationVO.getReportingToUserId()+"' or agm.USER_ID = '"+assetVerificationVO.getReportingToUserId()+"') ");

                bufInsSqlTempCount.append( "select distinct COUNT(1) from cr_asset_collateral_m c,cr_loan_dtl b,gcd_customer_m d,cr_asset_verification_dtl a" +
                		" left join com_agency_m ag on a.APPRAISER_ID=ag.AGENCY_CODE " +  
                		" left join com_agency_user_mapping agm on ag.AGENCY_CODE=agm.AGENCY_CODE " + 
                		" where b.LOAN_customer_id=d.CUSTOMER_ID and a.loan_id = b.loan_id and a.asset_id = c.asset_id AND B.LOAN_REPAYMENT_TYPE='I' AND A.REC_STATUS='I' " +
                		" AND B.LOAN_BRANCH='"+assetVerificationVO.getBranchId()+"' and (a.APPRAISER_ID = '"+assetVerificationVO.getReportingToUserId()+"' or agm.USER_ID = '"+assetVerificationVO.getReportingToUserId()+"') ");
                
                if((!((lbxLoanNoHID.toString()).equalsIgnoreCase("")))) {
 	                  bufInsSql.append(" AND a.LOAN_ID='"+lbxLoanNoHID+"'");
 	                 bufInsSqlTempCount.append(" AND a.LOAN_ID='"+lbxLoanNoHID+"'");

 	               }
                
                if(!((customerName.toString()).equalsIgnoreCase(""))){
       	        	bufInsSql.append(" AND d.customer_name like '%"+customerName+"%' ");	
       	        	bufInsSqlTempCount.append(" AND d.customer_name like '%"+customerName+"%' ");	
       	        }

 	               if((!((assetID.toString()).equalsIgnoreCase("")))) {
 	                   bufInsSql.append("  AND a.ASSET_ID='"+assetID+"'");
 	                  bufInsSqlTempCount.append("  AND a.ASSET_ID='"+assetID+"'");

 	               }

       	             logger.info("Query-----"+bufInsSql.toString());
      	            mainlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
      	          count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));

      	        if(((lbxLoanNoHID.toString()).trim()==null && (customerName.toString()).trim()==null && (assetID.toString()).trim()==null) || ((lbxLoanNoHID.toString()).trim().equalsIgnoreCase("") && (customerName.toString()).trim().equalsIgnoreCase("") && (assetID.toString()).trim().equalsIgnoreCase(""))|| assetVerificationVO.getCurrentPageLink()>1)
					{
					
					LoggerMsg.info("current PAge Link no .................... "+assetVerificationVO.getCurrentPageLink());
					if(assetVerificationVO.getCurrentPageLink()>1)
					{
						startRecordIndex = (assetVerificationVO.getCurrentPageLink()-1)*no;
						endRecordIndex = no;
						LoggerMsg.info("startRecordIndex .................... "+startRecordIndex);
						LoggerMsg.info("endRecordIndex .................... "+endRecordIndex);
					}
					
					bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
					
					}

	for(int i=0;i<mainlist.size();i++){

		subList=(ArrayList)mainlist.get(i);
		logger.info("In searchAssetVerRepCap..."+subList.size());
		
		if(subList.size()>0){
			
			AssetVerificationVO assetVerificationVO1=new AssetVerificationVO();
			
			assetVerificationVO1.setModifyID("<a href=assetVerificationInit.do?method=repCapAsset&assetVerificationID="
					+ CommonFunction.checkNull(subList.get(0)).toString()
					+ ">"
					+ CommonFunction.checkNull(subList.get(2)).toString() + "</a>");

			assetVerificationVO1.setAssetVerificationID((CommonFunction.checkNull(subList.get(0)).trim()));
			assetVerificationVO1.setLoanID((CommonFunction.checkNull(subList.get(1)).trim()));
			assetVerificationVO1.setAssetID((CommonFunction.checkNull(subList.get(2)).trim()));
			assetVerificationVO1.setLoanAccNo((CommonFunction.checkNull(subList.get(3)).trim()));
			assetVerificationVO1.setAssetDescription((CommonFunction.checkNull(subList.get(4)).trim()));
			assetVerificationVO1.setInitDate((CommonFunction.checkNull(subList.get(5)).trim()));
			assetVerificationVO1.setAppName((CommonFunction.checkNull(subList.get(6)).trim()));
			assetVerificationVO1.setReportingToUserId(userName);
			assetVerificationVO1.setTotalRecordSize(count);
			
			    assetList.add(assetVerificationVO1);
		}
}
	}catch(Exception e){
		e.printStackTrace();
	}
	finally
	{
		lbxLoanNoHID = null;
		assetID = null;
		customerName = null;
	}

	return assetList;
	 
}

public ArrayList repCapAsset(AssetVerificationVO assetVerificationVO,
		String assetVerificationID,String makerID ) {
	
	ArrayList<AssetVerificationVO> assetList = new ArrayList<AssetVerificationVO>(); 
	//AssetVerificationVO vo=new AssetVerificationVO();
    ArrayList mainlist=new ArrayList();
	  ArrayList subList=new ArrayList();
	try{
		logger.info("In AssetVerification...Dao Impl.");
                logger.info("In repCapAsset()......................");

       	               StringBuffer sbAppendToSQLCount=new StringBuffer();
       	               StringBuffer bufInsSql =    new StringBuffer();

                bufInsSql.append( "select a.asset_verification_id,a.loan_id,b.loan_no,c.customer_name,f.PRODUCT_DESC,e.SCHEME_DESC,b.LOAN_LOAN_AMOUNT,DATE_FORMAT(b.LOAN_APPROVAL_DATE,'"+dateFormat+"'),d.ASSET_COLLATERAL_DESC," +
                		" a.APPRAISER_TYPE,ifnull((select AGENCY_NAME from com_agency_m where AGENCY_CODE=a.APPRAISER_ID),A.APPRAISER_NAME) AS APPRAISER_NAME,"+
                		" a.ASSET_LOCATION,DATE_FORMAT(a.VISIT_DATE,'"+dateFormat+"'),a.ASSET_CONDITION,a.INVOICE_COLLECTED,a.INVOICE_NO,a.VISIT_REMARKS,a.MAKER_REMARKS,a.APPROVAL_REMARKS " +
                		" from cr_asset_verification_dtl a,cr_loan_dtl b,gcd_customer_m c,cr_asset_collateral_m d,cr_scheme_m e,cr_product_m f " +
                		" where a.LOAN_ID = b.loan_id and b.LOAN_CUSTOMER_ID = c.CUSTOMER_ID and a.ASSET_ID = d.asset_id and a.rec_status = 'I' and e.SCHEME_ID=b.LOAN_SCHEME and f.PRODUCT_ID=b.LOAN_PRODUCT");

                if((!(assetVerificationID.equalsIgnoreCase("")))) {
	                  bufInsSql.append(" AND a.asset_verification_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(assetVerificationID).trim())+"'");

	               }
                
       	             logger.info("Query-----"+bufInsSql.toString());
      	            mainlist = ConnectionDAO.sqlSelect(bufInsSql.toString());


	for(int i=0;i<mainlist.size();i++){

		subList=(ArrayList)mainlist.get(i);
		logger.info("In searchAssetVerRepCap..."+subList.size());
		
		if(subList.size()>0){
			
			AssetVerificationVO assetVerificationVO1=new AssetVerificationVO();
			assetVerificationVO1.setAssetVerificationID((CommonFunction.checkNull(subList.get(0)).trim()));
			assetVerificationVO1.setLoanID((CommonFunction.checkNull(subList.get(1)).trim()));
			assetVerificationVO1.setLoanAccNo((CommonFunction.checkNull(subList.get(2)).trim()));
			assetVerificationVO1.setCustomerName((CommonFunction.checkNull(subList.get(3)).trim()));
			assetVerificationVO1.setLoanProduct((CommonFunction.checkNull(subList.get(4)).trim()));
			assetVerificationVO1.setLoanScheme((CommonFunction.checkNull(subList.get(5)).trim()));
			
			Number loanAmount =myFormatter.parse((CommonFunction.checkNull(subList.get(6))).trim());
			
			assetVerificationVO1.setLoanAmount(myFormatter.format(loanAmount));
			
			//assetVerificationVO1.setLoanAmount((CommonFunction.checkNull(subList.get(6)).trim()));
			assetVerificationVO1.setLoanApprovalDate((CommonFunction.checkNull(subList.get(7)).trim()));
			assetVerificationVO1.setAssetDescription((CommonFunction.checkNull(subList.get(8)).trim()));
			assetVerificationVO1.setAppraiser((CommonFunction.checkNull(subList.get(9)).trim()));
			assetVerificationVO1.setAppraiserName((CommonFunction.checkNull(subList.get(10)).trim()));
			assetVerificationVO1.setAssetLocation((CommonFunction.checkNull(subList.get(11)).trim()));
			assetVerificationVO1.setVisitDate((CommonFunction.checkNull(subList.get(12)).trim()));
			assetVerificationVO1.setAssetCondition((CommonFunction.checkNull(subList.get(13)).trim()));
			assetVerificationVO1.setInvoiceCollected((CommonFunction.checkNull(subList.get(14)).trim()));    
			assetVerificationVO1.setInvoiceNumber((CommonFunction.checkNull(subList.get(15)).trim()));
			assetVerificationVO1.setComments((CommonFunction.checkNull(subList.get(16)).trim()));
			assetVerificationVO1.setMakerRemarks((CommonFunction.checkNull(subList.get(17)).trim()));
			assetVerificationVO1.setAuthorRemarks((CommonFunction.checkNull(subList.get(18)).trim()));

			assetList.add(assetVerificationVO1);
		}
}
	}catch(Exception e){
		e.printStackTrace();
	}

	return assetList;

}


public boolean saveAssetRepCap(
		AssetVerificationVO assetVerificationVO,
		String assetVerificationID) {
	
	ArrayList qryList =  new ArrayList();
	boolean status1=false;
	
	StringBuffer bufInsSql =null;
	PrepStmtObject insertPrepStmtObject = null;	
	
	logger.info("In AssetVerification...Dao Impl.");
	
	logger.info("In saveAssetRepCap...");
	 
	  
	try{
			
		 bufInsSql =	new StringBuffer();
		 insertPrepStmtObject = new PrepStmtObject();
	 
			 bufInsSql.append("update cr_asset_verification_dtl set ASSET_LOCATION=? ,VISIT_DATE=STR_TO_DATE(?,'"+dateFormatWithTime+"') , ASSET_CONDITION=?," +
			 		" INVOICE_COLLECTED=?,INVOICE_NO=?,VISIT_REMARKS=?,REC_STATUS='I',MAKER_REMARKS=?,MAKER_ID=?,MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) where ASSET_VERIFICATION_ID = ?");
			 
		

				 if(CommonFunction.checkNull(assetVerificationVO.getAssetLocation()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(assetVerificationVO.getAssetLocation()).trim()));
				 
				 if(CommonFunction.checkNull(assetVerificationVO.getVisitDate()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(assetVerificationVO.getVisitDate()).trim()));
				 
				 if(CommonFunction.checkNull(assetVerificationVO.getAssetCondition()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
				else
						insertPrepStmtObject.addString((CommonFunction.checkNull(assetVerificationVO.getAssetCondition()).trim()));
			 
			
				if(CommonFunction.checkNull(assetVerificationVO.getInvoiceCollected()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(assetVerificationVO.getInvoiceCollected()).trim()));
				
				
				if(CommonFunction.checkNull(assetVerificationVO.getInvoiceNumber()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			    else
				insertPrepStmtObject.addString((CommonFunction.checkNull(assetVerificationVO.getInvoiceNumber()).trim()));
				 
				 
					
				if(CommonFunction.checkNull(assetVerificationVO.getComments()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			    else
				insertPrepStmtObject.addString((CommonFunction.checkNull(assetVerificationVO.getComments()).trim()));
				
				if(CommonFunction.checkNull(assetVerificationVO.getMakerRemarks()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			    else
				insertPrepStmtObject.addString((CommonFunction.checkNull(assetVerificationVO.getMakerRemarks()).trim()));
				//---------------------------------------------------------
				if ((CommonFunction.checkNull(assetVerificationVO.getMakerID())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((assetVerificationVO.getMakerID()).trim());
				if ((CommonFunction.checkNull(assetVerificationVO.getMakerDate()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(CommonFunction.checkNull(assetVerificationVO.getMakerDate()).trim());
				//---------------------------------------------------------
				
				if(CommonFunction.checkNull(assetVerificationID).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			    else
				insertPrepStmtObject.addString((CommonFunction.checkNull(assetVerificationID).trim()));
					
					insertPrepStmtObject.setSql(bufInsSql.toString());
					qryList.add(insertPrepStmtObject);

		
		     status1=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
              logger.info("In saveAssetVerification......................"+status1);
               

	}catch(Exception e){
		e.printStackTrace();
	}
	
	
	return status1;
	 
}

public ArrayList searchAssetVerRepComplete(
		AssetVerificationVO assetVerificationVO) {
	
	ArrayList<AssetVerificationVO> assetList = new ArrayList<AssetVerificationVO>();
	logger.info("In searchAssetVerRepComplete: ");
	logger.info("In AssetVerification...Dao Impl.");
	int count=0;
	int startRecordIndex=0;
	int endRecordIndex = no;
	StringBuilder lbxLoanNoHID= new StringBuilder();
	StringBuilder assetID= new StringBuilder();
	StringBuilder customerName= new StringBuilder();
//	 String lbxLoanNoHID="";
//	 String assetID="";
//	 String customerName="";
//	 String userName="";
	 
	 ArrayList mainlist=new ArrayList();
	 ArrayList subList=new ArrayList();

		logger.info("here userid++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+assetVerificationVO.getReportingToUserId());
//			try{
//				String userNameQ="select USER_NAME from sec_user_m where REC_STATUS='A' and USER_ID='"+assetVerificationVO.getReportingToUserId()+"'";
//				userName=ConnectionDAO.singleReturn(userNameQ);
//				logger.info("userNameQ: "+userNameQ+" userName: "+userName);
//			}
//			catch(Exception e)
//			{
//				e.printStackTrace();
//			}
	try{
		

		lbxLoanNoHID.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(assetVerificationVO.getLbxLoanNoHID()).trim()));
		assetID.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(assetVerificationVO.getAssetID()).trim()));
		customerName.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(assetVerificationVO.getCustomerName()).trim()));
		
                logger.info("In searchAssetVerRepComplete().....................................Dao Impl");

       	               StringBuffer bufInsSqlTempCount=new StringBuffer();
       	               StringBuffer bufInsSql =    new StringBuffer();

                bufInsSql.append( "select a.asset_verification_id,a.loan_id,a.asset_id,b.loan_no,c.ASSET_COLLATERAL_DESC,a.INITIATION_DATE,a.APPRAISER_NAME, (SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=A.MAKER_ID) MAKER_ID" +
                		" from cr_asset_verification_dtl a,cr_asset_collateral_m c,cr_loan_dtl b,gcd_customer_m d " +
                		"where b.LOAN_customer_id=d.CUSTOMER_ID and a.loan_id = b.loan_id and a.asset_id = c.asset_id " +
                		"AND A.REC_STATUS='V' AND B.LOAN_REPAYMENT_TYPE='I' AND B.LOAN_BRANCH='"+assetVerificationVO.getBranchId()+"'AND A.MAKER_ID!='"+assetVerificationVO.getUserId()+"'");

                bufInsSqlTempCount.append( "select distinct COUNT(1) from cr_asset_verification_dtl a,cr_asset_collateral_m c,cr_loan_dtl b,gcd_customer_m d " +
                		"where b.LOAN_customer_id=d.CUSTOMER_ID and a.loan_id = b.loan_id and a.asset_id = c.asset_id " +
                		"AND A.REC_STATUS='V' AND B.LOAN_REPAYMENT_TYPE='I' AND B.LOAN_BRANCH='"+assetVerificationVO.getBranchId()+"'AND A.MAKER_ID!='"+assetVerificationVO.getUserId()+"'");
                
                if((!((lbxLoanNoHID.toString()).equalsIgnoreCase("")))) {
 	                  bufInsSql.append(" AND a.LOAN_ID='"+lbxLoanNoHID+"'");
 	                 bufInsSqlTempCount.append(" AND a.LOAN_ID='"+lbxLoanNoHID+"'");

 	               }

                
                if(!((customerName.toString()).equalsIgnoreCase(""))){
       	        	bufInsSql.append(" AND d.customer_name like '%"+customerName+"%' ");	
       	        	bufInsSqlTempCount.append(" AND d.customer_name like '%"+customerName+"%' ");	
       	        }

 	               if((!((assetID.toString()).equalsIgnoreCase("")))) {
 	                   bufInsSql.append("  AND a.ASSET_ID='"+assetID+"'");
 	                  bufInsSqlTempCount.append("  AND a.ASSET_ID='"+assetID+"'");

 	               }
 	              if((!(assetVerificationVO.getLbxUserId().equalsIgnoreCase("")))) {
 	           		bufInsSql.append(" AND A.MAKER_ID='"+StringEscapeUtils.escapeSql(assetVerificationVO.getLbxUserId()).trim()+"' ");	
 	     			bufInsSqlTempCount.append(" AND A.MAKER_ID='"+StringEscapeUtils.escapeSql(assetVerificationVO.getLbxUserId()).trim()+"'");
 	     	      }
 	             
                
       	             logger.info("Query-----"+bufInsSql.toString());
      	            mainlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
      	            count=Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
      	            
      	          if(((lbxLoanNoHID.toString()).trim()==null && (customerName.toString()).trim()==null && (assetID.toString()).trim()==null) || ((lbxLoanNoHID.toString()).trim().equalsIgnoreCase("") && (customerName.toString()).trim().equalsIgnoreCase("") && (assetID.toString()).trim().equalsIgnoreCase(""))|| assetVerificationVO.getCurrentPageLink()>1)
					{
					
					LoggerMsg.info("current PAge Link no .................... "+assetVerificationVO.getCurrentPageLink());
					if(assetVerificationVO.getCurrentPageLink()>1)
					{
						startRecordIndex = (assetVerificationVO.getCurrentPageLink()-1)*no;
						endRecordIndex = no;
						LoggerMsg.info("startRecordIndex .................... "+startRecordIndex);
						LoggerMsg.info("endRecordIndex .................... "+endRecordIndex);
					}
					
					bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
					
					}

	for(int i=0;i<mainlist.size();i++){

		subList=(ArrayList)mainlist.get(i);
		logger.info("In searchAssetVerRepCap..."+subList.size());
		
		if(subList.size()>0){
			
			AssetVerificationVO assetVerificationVO1=new AssetVerificationVO();
			assetVerificationVO1.setModifyID("<a href=assetVerificationInit.do?method=repCapAssetComplete&assetVerificationID="
					+ CommonFunction.checkNull(subList.get(0)).toString()
					+ ">"
					+ CommonFunction.checkNull(subList.get(2)).toString() + "</a>");
			assetVerificationVO1.setAssetVerificationID((CommonFunction.checkNull(subList.get(0)).trim()));
			assetVerificationVO1.setLoanID((CommonFunction.checkNull(subList.get(1)).trim()));
			assetVerificationVO1.setAssetID((CommonFunction.checkNull(subList.get(2)).trim()));
			assetVerificationVO1.setLoanAccNo((CommonFunction.checkNull(subList.get(3)).trim()));
			assetVerificationVO1.setAssetDescription((CommonFunction.checkNull(subList.get(4)).trim()));
			assetVerificationVO1.setInitDate((CommonFunction.checkNull(subList.get(5)).trim()));
			assetVerificationVO1.setAppName((CommonFunction.checkNull(subList.get(6)).trim()));
			assetVerificationVO1.setReportingToUserId((CommonFunction.checkNull(subList.get(7)).trim()));
			assetVerificationVO1.setTotalRecordSize(count);
			
			    assetList.add(assetVerificationVO1);
		}
}
	}catch(Exception e){
		e.printStackTrace();
	}
	finally
	{
		lbxLoanNoHID = null;
		assetID = null;
		customerName = null;
	}

	return assetList;
	 
}


public boolean forwardAssetRepCap(
		AssetVerificationVO assetVerificationVO,
		String assetVerificationID) {
	logger.info("In AssetVerification...Dao Impl.");
	ArrayList qryList =  new ArrayList();
	boolean status1=false;
	
	StringBuffer bufInsSql =null;
	PrepStmtObject insertPrepStmtObject = null;	
	
	
	logger.info("In forwardAssetRepCap...");
	 
	  
	try{
			
		 bufInsSql =	new StringBuffer();
		 insertPrepStmtObject = new PrepStmtObject();
	 
			 bufInsSql.append("update cr_asset_verification_dtl set ASSET_LOCATION=? ,VISIT_DATE=STR_TO_DATE(?,'"+dateFormatWithTime+"') , ASSET_CONDITION=?," +
			 		" INVOICE_COLLECTED=?,INVOICE_NO=?,VISIT_REMARKS=?,MAKER_ID=?,MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),REC_STATUS='V' where ASSET_VERIFICATION_ID = ?");

				 if(CommonFunction.checkNull(assetVerificationVO.getAssetLocation()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(assetVerificationVO.getAssetLocation()).trim()));
				 
				 if(CommonFunction.checkNull(assetVerificationVO.getVisitDate()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(assetVerificationVO.getVisitDate()).trim()));
				 
				 if(CommonFunction.checkNull(assetVerificationVO.getAssetCondition()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
				else
						insertPrepStmtObject.addString((CommonFunction.checkNull(assetVerificationVO.getAssetCondition()).trim()));
			 
			
				if(CommonFunction.checkNull(assetVerificationVO.getInvoiceCollected()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(assetVerificationVO.getInvoiceCollected()).trim()));
				
				
				if(CommonFunction.checkNull(assetVerificationVO.getInvoiceNumber()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			    else
				insertPrepStmtObject.addString((CommonFunction.checkNull(assetVerificationVO.getInvoiceNumber()).trim()));
				 
				 
					
				if(CommonFunction.checkNull(assetVerificationVO.getComments()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			    else
				insertPrepStmtObject.addString((CommonFunction.checkNull(assetVerificationVO.getComments()).trim()));
				
				//---------------------------------------------------------
				if ((CommonFunction.checkNull(assetVerificationVO.getMakerID())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((assetVerificationVO.getMakerID()).trim());
				if ((CommonFunction.checkNull(assetVerificationVO.getMakerDate()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(CommonFunction.checkNull(assetVerificationVO.getMakerDate()).trim());
				//---------------------------------------------------------
					
				if(CommonFunction.checkNull(assetVerificationID).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			    else
				insertPrepStmtObject.addString((CommonFunction.checkNull(assetVerificationID).trim()));
					
					insertPrepStmtObject.setSql(bufInsSql.toString());
					qryList.add(insertPrepStmtObject);

		
		     status1=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
              logger.info("In forwardAssetRepCap......................"+status1);
               

	}catch(Exception e){
		e.printStackTrace();
	}
	
	
	return status1;
	 
}

public ArrayList repCapAssetComplete(AssetVerificationVO assetVerificationVO,
		String assetVerificationID) {
	logger.info("In AssetVerification...Dao Impl.");
	ArrayList<AssetVerificationVO> assetList = new ArrayList<AssetVerificationVO>(); 
    ArrayList mainlist=new ArrayList();
	  ArrayList subList=new ArrayList();
	try{
		
                logger.info("In repCapAssetComplete()...................................");

       	               StringBuffer sbAppendToSQLCount=new StringBuffer();
       	               StringBuffer bufInsSql =    new StringBuffer();

                bufInsSql.append( "select a.asset_verification_id,a.loan_id,b.loan_no,c.customer_name,f.PRODUCT_DESC,e.SCHEME_DESC,b.LOAN_LOAN_AMOUNT,DATE_FORMAT(b.LOAN_APPROVAL_DATE,'"+dateFormat+"'),d.ASSET_COLLATERAL_DESC," +
                		" a.APPRAISER_TYPE,ifnull((select AGENCY_NAME from com_agency_m where AGENCY_CODE=a.APPRAISER_ID),A.APPRAISER_NAME) AS APPRAISER_NAME," + 
                		" a.ASSET_LOCATION,a.ASSET_CONDITION,a.INVOICE_COLLECTED,a.INVOICE_NO,DATE_FORMAT(a.VISIT_DATE,'"+dateFormat+"'),a.VISIT_REMARKS,a.MAKER_REMARKS,a.APPROVAL_REMARKS " +
                		" from cr_asset_verification_dtl a,cr_loan_dtl b,gcd_customer_m c,cr_asset_collateral_m d,cr_scheme_m e,cr_product_m f" +
                		" where a.LOAN_ID = b.loan_id and b.LOAN_CUSTOMER_ID = c.CUSTOMER_ID and a.ASSET_ID = d.asset_id and a.rec_status = 'V' and e.SCHEME_ID=b.LOAN_SCHEME and f.PRODUCT_ID=b.LOAN_PRODUCT");

                if((!(assetVerificationID.equalsIgnoreCase("")))) {
	                  bufInsSql.append(" AND a.asset_verification_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(assetVerificationID).trim())+"'");

	               }
                
       	             logger.info("Query-----"+bufInsSql.toString());
      	            mainlist = ConnectionDAO.sqlSelect(bufInsSql.toString());


	for(int i=0;i<mainlist.size();i++){

		subList=(ArrayList)mainlist.get(i);
		logger.info("In repCapAssetComplete..."+subList.size());
		
		if(subList.size()>0){
			
			AssetVerificationVO assetVerificationVO1=new AssetVerificationVO();
			assetVerificationVO1.setAssetVerificationID((CommonFunction.checkNull(subList.get(0)).trim()));
			assetVerificationVO1.setLoanID((CommonFunction.checkNull(subList.get(1)).trim()));
			assetVerificationVO1.setLoanAccNo((CommonFunction.checkNull(subList.get(2)).trim()));
			assetVerificationVO1.setCustomerName((CommonFunction.checkNull(subList.get(3)).trim()));
			assetVerificationVO1.setLoanProduct((CommonFunction.checkNull(subList.get(4)).trim()));
			assetVerificationVO1.setLoanScheme((CommonFunction.checkNull(subList.get(5)).trim()));
			
            Number loanAmount =myFormatter.parse((CommonFunction.checkNull(subList.get(6))).trim());
			
			assetVerificationVO1.setLoanAmount(myFormatter.format(loanAmount));
			//assetVerificationVO1.setLoanAmount((CommonFunction.checkNull(subList.get(6)).trim()));
			assetVerificationVO1.setLoanApprovalDate((CommonFunction.checkNull(subList.get(7)).trim()));
			assetVerificationVO1.setAssetDescription((CommonFunction.checkNull(subList.get(8)).trim()));
			assetVerificationVO1.setAppraiser((CommonFunction.checkNull(subList.get(9)).trim()));
			assetVerificationVO1.setAppraiserName((CommonFunction.checkNull(subList.get(10)).trim()));
			
			assetVerificationVO1.setAssetLocation((CommonFunction.checkNull(subList.get(11)).trim()));
			assetVerificationVO1.setAssetCondition((CommonFunction.checkNull(subList.get(12)).trim()));
			assetVerificationVO1.setInvoiceCollected((CommonFunction.checkNull(subList.get(13)).trim()));
			assetVerificationVO1.setInvoiceNumber((CommonFunction.checkNull(subList.get(14)).trim()));
			assetVerificationVO1.setVisitDate((CommonFunction.checkNull(subList.get(15)).trim()));
			assetVerificationVO1.setComments((CommonFunction.checkNull(subList.get(16)).trim()));  
			assetVerificationVO1.setMakerRemarks((CommonFunction.checkNull(subList.get(17)).trim()));
			assetVerificationVO1.setAuthorRemarks((CommonFunction.checkNull(subList.get(18)).trim()));
			    assetList.add(assetVerificationVO1);
		}
}
	}catch(Exception e){
		e.printStackTrace();
	}

	return assetList;

}

public boolean saveAssetCapRepAuthor(
		AssetVerificationVO assetVerificationVO,
		String assetVerificationID) {
	ArrayList qryList =  new ArrayList();
	boolean status1=false;
	
	StringBuffer bufInsSql =null;
	PrepStmtObject insertPrepStmtObject = null;	
	logger.info("In AssetVerification...Dao Impl.");

	
	logger.info("In saveAssetCapRepAuthor...");
	 
	  
	try{
			
		 bufInsSql =	new StringBuffer();
		 insertPrepStmtObject = new PrepStmtObject();
	 
			 bufInsSql.append("update cr_asset_verification_dtl set APPROVAL_REMARKS=?,REC_STATUS=?,MAKER_ID=?,MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) where ASSET_VERIFICATION_ID = ?");
			 
		

				if(CommonFunction.checkNull(assetVerificationVO.getComments()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			    else
				insertPrepStmtObject.addString((CommonFunction.checkNull(assetVerificationVO.getComments()).trim()));

				
				if(CommonFunction.checkNull(assetVerificationVO.getDecision()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			    else
				insertPrepStmtObject.addString((CommonFunction.checkNull(assetVerificationVO.getDecision()).trim()));
				//---------------------------------------------------------
				if ((CommonFunction.checkNull(assetVerificationVO.getMakerID())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((assetVerificationVO.getMakerID()).trim());
				if ((CommonFunction.checkNull(assetVerificationVO.getMakerDate()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(CommonFunction.checkNull(assetVerificationVO.getMakerDate()).trim());
				//---------------------------------------------------------

				
				if(CommonFunction.checkNull(assetVerificationID).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			    else
				insertPrepStmtObject.addString((CommonFunction.checkNull(assetVerificationID).trim()));
					
					insertPrepStmtObject.setSql(bufInsSql.toString());
					qryList.add(insertPrepStmtObject);

		
		     status1=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
              logger.info("In saveAssetCapRepAuthor......................"+status1);
               

	}catch(Exception e){
		e.printStackTrace();
	}
	
	
	return status1;

}

public ArrayList searchAssetVerModifyData(AssetVerificationVO assetVerificationVO) {
	
	ArrayList<AssetVerificationVO> assetList = new ArrayList<AssetVerificationVO>();
	logger.info("In searchAssetVerModifyData: ");
	logger.info("In searchAssetVerModifyData...Dao Impl.");
	StringBuilder assetVarId= new StringBuilder();
//	 String assetVarId="";
	
	 
    ArrayList mainlist=new ArrayList();
	  ArrayList subList=new ArrayList();
	  String appraiserID="";
	try{
		

		assetVarId.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(assetVerificationVO.getAssetVerId()).trim()));
		
								
                logger.info("In searchAssetVerModifyData().....................................Dao Impl"+assetVarId);

       	         StringBuffer sbAppendToSQLCount=new StringBuffer();
       	         StringBuffer bufInsSql = new StringBuffer();
       	      logger.info("assetVerificationVO.getAppraiserName()" +assetVerificationVO.getAppraiserName());
       	    		logger.info("assetVerificationVO.getAppraiserType()" +assetVerificationVO.getAppraiserType());
       	    				logger.info("assetVerificationVO.getLoanID()"+assetVerificationVO.getLoanID());
       	    			String appraiserType=assetVerificationVO.getAppraiserType();
       	    				if(appraiserType.equalsIgnoreCase("I")){
       	 					
       	 					appraiserID=assetVerificationVO.getLbxUserId();
       	 				}else{
       	 					
       	 				appraiserID=assetVerificationVO.getLbxextApprHID();
       	 				}	
       	    				 
       	 /*  bufInsSql.append("SELECT D.LOAN_NO,B.CUSTOMER_NAME,A.APPRAISER_TYPE,A.APPRAISER_NAME,");
       	   bufInsSql.append(" (SELECT C.AGENCY_NAME FROM com_agency_m C WHERE A.APPRAISER_ID=C.AGENCY_CODE)AS EXTRNAL_APPRAISER,"); 
       	   bufInsSql.append(" A.APPRAISER_ID,A.LOAN_ID,A.ASSET_ID,E.ASSET_COLLATERAL_DESC,E.ASSET_COLLATERAL_VALUE,E.ASSET_MANUFATURER_DESC,E.ASSET_SUPPLIER_DESC,A.REC_STATUS,D.LOAN_NO ");
       	   bufInsSql.append(" FROM cr_asset_verification_dtl A,gcd_customer_m B,cr_loan_dtl D,cr_asset_collateral_m E  WHERE B.CUSTOMER_ID=D.LOAN_CUSTOMER_ID AND A.LOAN_ID=D.LOAN_ID and A.asset_id = E.asset_id");
       	  bufInsSql.append(" AND A.loan_id='4' AND A.APPRAISER_ID='ADITI' and A.APPRAISER_TYPE='I' ");   */  
       	      bufInsSql.append("SELECT DISTINCT A.LOAN_ID,D.LOAN_NO,B.CUSTOMER_NAME,IF(A.APPRAISER_TYPE='I','Internal','External') as Appraiser,A.APPRAISER_NAME, ");
/*       	   bufInsSql.append("(SELECT C.AGENCY_NAME FROM com_agency_m C WHERE A.APPRAISER_ID=C.AGENCY_CODE)AS EXTRNAL_APPRAISER, ");*/
       	 bufInsSql.append(" A.APPRAISER_ID FROM cr_asset_verification_dtl A,gcd_customer_m B,cr_loan_dtl D,cr_asset_collateral_m E "); 
       	 bufInsSql.append(" WHERE B.CUSTOMER_ID=D.LOAN_CUSTOMER_ID AND A.LOAN_ID=D.LOAN_ID and A.asset_id = E.asset_id AND A.loan_id='"+CommonFunction.checkNull(assetVerificationVO.getLoanID()).trim()+"' AND ");
       	 bufInsSql.append(" A.APPRAISER_ID='"+CommonFunction.checkNull(appraiserID).trim() +"' and A.APPRAISER_TYPE='"+CommonFunction.checkNull(assetVerificationVO.getAppraiserType()).trim()+"' AND A.REC_STATUS='P' "); 
             


       	             logger.info("Query-----"+bufInsSql.toString());
      	            mainlist = ConnectionDAO.sqlSelect(bufInsSql.toString());

       
	for(int i=0;i<mainlist.size();i++){

		subList=(ArrayList)mainlist.get(i);
		logger.info("In searchAssetVerModifyData..."+subList.size());
		logger.info("In searchAssetVerModifyData..."+subList);
		if(subList.size()>0){
			
			AssetVerificationVO assetVerificationSearchVo=new AssetVerificationVO();
			assetVerificationSearchVo.setLoanID((CommonFunction.checkNull(subList.get(0)).trim()));
			assetVerificationSearchVo.setLbxLoanNo((CommonFunction.checkNull(subList.get(0)).trim()));
			assetVerificationSearchVo.setLoanNo((CommonFunction.checkNull(subList.get(1)).trim()));
			assetVerificationSearchVo.setCustomerName((CommonFunction.checkNull(subList.get(2)).trim()));
			
			if(subList.get(3).equals("Internal")){
			assetVerificationSearchVo.setAppraiser((CommonFunction.checkNull(subList.get(3)).trim()));
			}else{
			assetVerificationSearchVo.setAppraiser((CommonFunction.checkNull(subList.get(3)).trim()));
			}
			if(subList.get(3).equals("Internal")){
				assetVerificationSearchVo.setInternalAppraiser((CommonFunction.checkNull(subList.get(4)).trim()));
				}else{
				assetVerificationSearchVo.setExternalAppraiser((CommonFunction.checkNull(subList.get(4)).trim()));
				}
			if(subList.get(3).equals("Internal")){
				assetVerificationSearchVo.setLbxUserId((CommonFunction.checkNull(subList.get(5)).trim()));
				}else{
				assetVerificationSearchVo.setLbxextApprHID((CommonFunction.checkNull(subList.get(5)).trim()));
				}
			
	
		
			assetList.add(assetVerificationSearchVo);
		}
}
	}catch(Exception e){
		e.printStackTrace();
	}
	finally
	{
		assetVarId = null;
	}
	return assetList;
	 
}
public ArrayList<AssetVerificationVO> getValueForAssetVerInit(String lbxLoanID) {
	
	ArrayList<AssetVerificationVO> assetList = new ArrayList<AssetVerificationVO>();
	logger.info("In getValueForAssetVerInit: ");
	
	 
    ArrayList mainlist=new ArrayList();
	  ArrayList subList=new ArrayList();
	try{



                logger.info("In getValueForAssetVerInit().....................................Dao Impl");

       	               StringBuffer sbAppendToSQLCount=new StringBuffer();
       	               StringBuffer bufInsSql =    new StringBuffer();

                bufInsSql.append( "select a.loan_id,a.assetid,b.loan_no,c.ASSET_COLLATERAL_DESC,c.ASSET_COLLATERAL_VALUE,c.ASSET_MANUFATURER_DESC,c.ASSET_SUPPLIER_DESC from cr_loan_collateral_m a, cr_loan_dtl b, cr_asset_collateral_m c" +
                		" where a.loan_id = b.loan_id and a.assetid = c.asset_id and a.rec_status = 'A' and c.asset_type = 'ASSET' and a.loan_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(lbxLoanID).trim())+"'");


       	               logger.info("Query-----"+bufInsSql.toString());
      	            mainlist = ConnectionDAO.sqlSelect(bufInsSql.toString());


	for(int i=0;i<mainlist.size();i++){

		subList=(ArrayList)mainlist.get(i);
		logger.info("In getAssetList..."+subList.size());
		
		if(subList.size()>0){
			
			AssetVerificationVO assetVerificationVO=new AssetVerificationVO();
			assetVerificationVO.setLoanID((CommonFunction.checkNull(subList.get(0)).trim()));
			assetVerificationVO.setAssetID((CommonFunction.checkNull(subList.get(1)).trim()));
			assetVerificationVO.setLoanAccNo((CommonFunction.checkNull(subList.get(2)).trim()));
			assetVerificationVO.setAssetDescription((CommonFunction.checkNull(subList.get(3)).trim()));
			assetVerificationVO.setAssetCost((CommonFunction.checkNull(subList.get(4)).trim()));
			assetVerificationVO.setAssetManufaturer((CommonFunction.checkNull(subList.get(5)).trim()));    
			assetVerificationVO.setAssetSupplier((CommonFunction.checkNull(subList.get(6)).trim()));
			assetList.add(assetVerificationVO);
		}
}
	}catch(Exception e){
		e.printStackTrace();
	}

	return assetList;
}
public ArrayList<AssetVerificationVO> getAssetdatagridaftersave(AssetVerificationVO assetVerificationVO){
	ArrayList<AssetVerificationVO> assetList = new ArrayList<AssetVerificationVO>();
	logger.info("In getAssetdatagridaftersave: .........Dao Impl---------->");
	
	String apprId="";
    ArrayList mainlist=new ArrayList();
	  ArrayList subList=new ArrayList();
	  String appraisertype="";
	try{
		logger.info(".....assetVerificationVO.getAppraiser()...Dao Impl"+assetVerificationVO.getAppraiser());

		appraisertype=CommonFunction.checkNull(assetVerificationVO.getAppraiser()).trim();
		
	
		if(assetVerificationVO.getLbxUserId()!=null && !assetVerificationVO.getLbxUserId().equalsIgnoreCase(""))
		{
			apprId=assetVerificationVO.getLbxUserId();
		}
		else
		{
			apprId=assetVerificationVO.getLbxextApprHID();
		}
                logger.info(".....assetVerificationVO.getLbxLoanNo().........Dao Impl"+assetVerificationVO.getLbxLoanNo());
		 logger.info(".....assetVerificationVO.getLbxextApprHID()........Dao Impl"+assetVerificationVO.getLbxextApprHID());
       	               StringBuffer sbAppendToSQLCount=new StringBuffer();
       	               StringBuffer bufInsSql =    new StringBuffer();

               bufInsSql.append( "select v.loan_id,v.ASSET_ID,b.loan_no,c.ASSET_COLLATERAL_DESC,c.ASSET_COLLATERAL_VALUE,c.ASSET_MANUFATURER_DESC,c.ASSET_SUPPLIER_DESC " +
                		"from  cr_loan_dtl b, cr_asset_collateral_m c,cr_asset_verification_dtl v" +
                		" where v.loan_id = b.loan_id and v.asset_id = c.asset_id and v.rec_status = 'P'  and v.loan_id='"+CommonFunction.checkNull(assetVerificationVO.getLbxLoanNo()).trim()+"'"+
                		" and v.APPRAISER_ID='"+CommonFunction.checkNull(apprId).trim()+"' and v.APPRAISER_TYPE='"+CommonFunction.checkNull(appraisertype).trim()+"' ");


       	               logger.info("Query-----"+bufInsSql.toString());
      	            mainlist = ConnectionDAO.sqlSelect(bufInsSql.toString());


	for(int i=0;i<mainlist.size();i++){

		subList=(ArrayList)mainlist.get(i);
		logger.info("In getAssetList..."+subList.size());
		
		if(subList.size()>0){
			
			 assetVerificationVO=new AssetVerificationVO();
			assetVerificationVO.setLoanID((CommonFunction.checkNull(subList.get(0)).trim()));
			assetVerificationVO.setAssetID((CommonFunction.checkNull(subList.get(1)).trim()));
			assetVerificationVO.setLoanAccNo((CommonFunction.checkNull(subList.get(2)).trim()));
			assetVerificationVO.setAssetDescription((CommonFunction.checkNull(subList.get(3)).trim()));
			assetVerificationVO.setAssetCost((CommonFunction.checkNull(subList.get(4)).trim()));
			assetVerificationVO.setAssetManufaturer((CommonFunction.checkNull(subList.get(5)).trim()));    
			assetVerificationVO.setAssetSupplier((CommonFunction.checkNull(subList.get(6)).trim()));
			assetList.add(assetVerificationVO);
		}
}
	}catch(Exception e){
		e.printStackTrace();
	}

	return assetList;
}
public String assetnotinserted(String[] loanIDList,
		String[] loanAccNoList, String[] assetIDList,
		String[] assetDescriptionList,
		AssetVerificationVO assetVerificationVO) throws SQLException{
	String exInId="";
	String asset="";
	String assets="";
	if(assetVerificationVO.getLbxUserId()!=null && !assetVerificationVO.getLbxUserId().equalsIgnoreCase(""))
	{
		exInId=assetVerificationVO.getLbxUserId();
	}
	else
	{
		exInId=assetVerificationVO.getLbxextApprHID();
	}
	for(int i=0;i<assetIDList.length;i++)
	{
		String query="SELECT ASSET_ID FROM  cr_asset_verification_dtl WHERE LOAN_ID='"+loanIDList[i]+"' AND REC_STATUS='P'"+
		"AND APPRAISER_ID='"+exInId+"' AND APPRAISER_TYPE='"+assetVerificationVO.getAppraiser()+"' AND ASSET_ID='"+assetIDList[i]+"'";
		logger.info("query----->"+query);
		asset= ConnectionDAO.singleReturn(query);
		assets=assets+"/"+asset;
	}
	return assets;
}
public boolean uniqueassetcount(String[] loanIDList,
		String[] loanAccNoList, String[] assetIDList,
		String[] assetDescriptionList,
		AssetVerificationVO assetVerificationVO){
	String exInId="";
	boolean result=false;
	if(assetVerificationVO.getLbxUserId()!=null && !assetVerificationVO.getLbxUserId().equalsIgnoreCase(""))
	{
		exInId=assetVerificationVO.getLbxUserId();
	}
	else
	{
		exInId=assetVerificationVO.getLbxextApprHID();
	}
	for(int i=0;i<loanIDList.length;i++)
	{
		String query="SELECT COUNT(1) FROM  cr_asset_verification_dtl WHERE LOAN_ID='"+loanIDList[i]+"' AND REC_STATUS='P'"+
		"AND APPRAISER_ID='"+exInId+"' AND APPRAISER_TYPE='"+assetVerificationVO.getAppraiser()+"' AND ASSET_ID='"+assetIDList[i]+"'";
		logger.info("query----->"+query);
		result= ConnectionDAO.checkStatus(query);
	}
	return result;
}
public ArrayList AssetDataGrid(AssetVerificationVO assetVerificationVO) {
	
	ArrayList<ShowAssetVerificationVO> assetList = new ArrayList<ShowAssetVerificationVO>();
	logger.info("In AssetDataGrid: ");
	logger.info("In AssetDataGrid...Dao Impl.");
	StringBuilder assetVarId= new StringBuilder();
//	 String assetVarId="";
	ShowAssetVerificationVO vo =null;
	 
    ArrayList mainlist=new ArrayList();
	  ArrayList subList=new ArrayList();
	try{
		

		assetVarId.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(assetVerificationVO.getAssetVerId()).trim()));
		
								
                logger.info("In AssetDataGrid().....................................Dao Impl"+assetVarId);

       	         StringBuffer sbAppendToSQLCount=new StringBuffer();
       	         StringBuffer bufInsSql = new StringBuffer();
       	      String appraiserID="";
       	      String appraiserType=assetVerificationVO.getAppraiserType();
       	  	if(appraiserType.equalsIgnoreCase("I")){
					
					appraiserID=assetVerificationVO.getLbxUserId();
				}else{
					
				appraiserID=assetVerificationVO.getLbxextApprHID();
				}	
       	 /*  bufInsSql.append("SELECT D.LOAN_NO,B.CUSTOMER_NAME,A.APPRAISER_TYPE,A.APPRAISER_NAME,");
       	   bufInsSql.append(" (SELECT C.AGENCY_NAME FROM com_agency_m C WHERE A.APPRAISER_ID=C.AGENCY_CODE)AS EXTRNAL_APPRAISER,"); 
       	   bufInsSql.append(" A.APPRAISER_ID,A.LOAN_ID,A.ASSET_ID,E.ASSET_COLLATERAL_DESC,E.ASSET_COLLATERAL_VALUE,E.ASSET_MANUFATURER_DESC,E.ASSET_SUPPLIER_DESC,A.REC_STATUS,D.LOAN_NO ");
       	   bufInsSql.append(" FROM cr_asset_verification_dtl A,gcd_customer_m B,cr_loan_dtl D,cr_asset_collateral_m E  WHERE B.CUSTOMER_ID=D.LOAN_CUSTOMER_ID AND A.LOAN_ID=D.LOAN_ID and A.asset_id = E.asset_id");
       	  bufInsSql.append(" AND A.loan_id='4' AND A.APPRAISER_ID='ADITI' and A.APPRAISER_TYPE='I' ");   */  
       	     bufInsSql.append("SELECT A.ASSET_ID,E.ASSET_COLLATERAL_DESC,E.ASSET_COLLATERAL_VALUE,E.ASSET_MANUFATURER_DESC,E.ASSET_SUPPLIER_DESC,A.REC_STATUS,D.LOAN_NO,A.LOAN_ID ");
       	    bufInsSql.append("FROM cr_asset_verification_dtl A,gcd_customer_m B,cr_loan_dtl D,cr_asset_collateral_m E  WHERE B.CUSTOMER_ID=D.LOAN_CUSTOMER_ID AND ");
       	    bufInsSql.append("A.LOAN_ID=D.LOAN_ID and A.asset_id = E.asset_id AND A.loan_id='"+assetVerificationVO.getLoanID()+"' AND A.APPRAISER_ID='"+appraiserID+"' and ");
       	    bufInsSql.append("A.APPRAISER_TYPE='"+assetVerificationVO.getAppraiserType()+"' AND A.REC_STATUS='P' "); 
                if((!((assetVarId.toString()).equalsIgnoreCase(""))))  {
 	                  bufInsSql.append(" AND A.ASSET_VERIFICATION_ID='"+assetVarId+"'");

 	               }


       	             logger.info("Query-----"+bufInsSql.toString());
      	            mainlist = ConnectionDAO.sqlSelect(bufInsSql.toString());

       
	for(int i=0;i<mainlist.size();i++){

		subList=(ArrayList)mainlist.get(i);
		logger.info("In AssetDataGrid..."+subList.size());
		logger.info("In AssetDataGrid..."+subList);
		if(subList.size()>0){
			
			 vo =new ShowAssetVerificationVO();
			vo.setAssetID((CommonFunction.checkNull(subList.get(0)).trim()));
			vo.setAssetDescription((CommonFunction.checkNull(subList.get(1)).trim()));
			vo.setAssetCost((CommonFunction.checkNull(subList.get(2)).trim()));
			vo.setAssetManufaturer((CommonFunction.checkNull(subList.get(3)).trim()));
			vo.setAssetSupplier((CommonFunction.checkNull(subList.get(4)).trim()));
			vo.setRecStatus((CommonFunction.checkNull(subList.get(5)).trim()));
			vo.setLoanAccNo((CommonFunction.checkNull(subList.get(6)).trim()));
			vo.setLoanID((CommonFunction.checkNull(subList.get(7)).trim()));
			assetList.add(vo);
		}
}
	}catch(Exception e){
		e.printStackTrace();
	}
	finally
	{
		assetVarId = null;
	}
	return assetList;
	 
}
public boolean deleteassetid(String[] id,String loanid,String appraiserid,String apprtype) throws RemoteException, SQLException {
	logger.info("Inside deleteassetinsuranceid().........DAOImpl");
	ArrayList list=new ArrayList();
	boolean status=false;
	StringBuilder  bufInsSql=null;
	
	 Connection con = null; //holds the connection object
     Statement stmt = null;
     
	try
	{
		con = ConnectionDAO.getConnection(); //fetch the connection object
	     stmt = con.createStatement();
	for (int i=0;i<id.length;i++){
		bufInsSql=null;
		bufInsSql=new StringBuilder();
	bufInsSql.append("delete  from cr_asset_verification_dtl  where ASSET_ID = '"+CommonFunction.checkNull(id[i]).trim()+"' and LOAN_ID='"+CommonFunction.checkNull(loanid).trim()+"' " +
			" and APPRAISER_ID='"+CommonFunction.checkNull(appraiserid).trim()+"' and APPRAISER_TYPE='"+CommonFunction.checkNull(apprtype).trim()+"' and REC_STATUS='P'");
	
	logger.info("In deleteassetinsuranceid() : "+bufInsSql.toString());
	//list.add(bufInsSql.toString());
	stmt.addBatch(bufInsSql.toString());
	
	}
	
	/*  for (int i = 0; i < list.size(); i++)
	 int[] status=stmt.executeBatch(list);
	 for (int i = 0; i < status.length; i++) {
         if (status[i] == 0) {
             flag = false;

             break;
         }
     }*/
	int[] rows=stmt.executeBatch();
	if(rows.length>0){
		status=true;
	}
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		con.close();
	}
	
	return status;
}



}