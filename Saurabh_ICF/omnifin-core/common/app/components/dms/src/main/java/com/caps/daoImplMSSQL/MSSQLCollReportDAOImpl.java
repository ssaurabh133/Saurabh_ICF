package com.caps.daoImplMSSQL;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import com.connect.CommonFunction;
import com.connect.ConnectionReportDumpsDAO;
import com.caps.dao.CollReportDAO;
import com.caps.VO.CollectionSummaryVO;
import com.caps.VO.ReallocationMasterVo;


public class MSSQLCollReportDAOImpl implements CollReportDAO{
	
	private static final Logger logger = Logger.getLogger(MSSQLCollReportDAOImpl.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime = resource.getString("lbl.dateWithTimeInDao");
	String dateFormat = resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	String dbType=resource.getString("lbl.dbType");
	String dbo=resource.getString("lbl.dbPrefix");

	
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	
	public ArrayList<ReallocationMasterVo> npaStageReport(){

		  ArrayList<ReallocationMasterVo> npaStageList =new  ArrayList<ReallocationMasterVo>();
		  
			ArrayList mainList=new ArrayList();
			ArrayList subList=new ArrayList();
			String query="";
			ReallocationMasterVo reallocVo=null;
			try{
				 
	              query="select  NPA_STAGE from cr_npa_m where REC_STATUS='A'";
	              logger.info("In npastage......Dao Impl"+query);
	              mainList = ConnectionReportDumpsDAO.sqlSelect(query);
	              int size = mainList.size();
	              for(int i=0;i<size;i++){
	               
	                subList=(ArrayList) mainList.get(i);

	               if(subList.size()>0){
						reallocVo=new ReallocationMasterVo();
					
						reallocVo.setNpastageid(subList.get(0).toString());
						reallocVo.setNpastageval(subList.get(0).toString());
						npaStageList.add(reallocVo);
					}
			}
					  logger.info("In npastage......Dao Impl"+npaStageList.size());
				  
			}catch(Exception e){
				e.printStackTrace();
			}
			finally
			{
				mainList=null;
				subList=null;
				query=null;
				reallocVo=null;
			}
			return npaStageList;
	}

		
		public ArrayList<ReallocationMasterVo> custCategoryReport(){

		int count=0;
		ArrayList mainList=new ArrayList();
		ArrayList subList=new ArrayList();
		String query="";
		ReallocationMasterVo reallocVo=null;
		
		 ArrayList<ReallocationMasterVo> custcatList=new  ArrayList<ReallocationMasterVo>();
			try{	
	              query="select VALUE,DESCRIPTION from generic_master where GENERIC_KEY='CUST_CATEGORY' and rec_status ='A'";              		
				  logger.info("In custcategory......Dao Impl"+query);
				    mainList = ConnectionReportDumpsDAO.sqlSelect(query);
				    int size = mainList.size();
				  for(int i=0;i<size;i++){
				   subList=(ArrayList) mainList.get(i);
				   if(subList.size()>0){
						reallocVo=new ReallocationMasterVo();
						reallocVo.setCstcat(subList.get(0).toString());
						reallocVo.setCstcatval(subList.get(1).toString());
						custcatList.add(reallocVo);
					}
				  }
			}catch(Exception e){
				e.printStackTrace();
			}
			finally
			{
				mainList=null;
				subList=null;
				query=null;
				reallocVo=null;
			}
			return custcatList;
	}
		
		public ArrayList<ReallocationMasterVo>  productReport(){

		 ArrayList<ReallocationMasterVo> productList=new  ArrayList<ReallocationMasterVo>();
		 
		 ArrayList mainList=new ArrayList();
			ArrayList subList=new ArrayList();
			 String query="";
			 ReallocationMasterVo reallocVo=null;
			try{
	              query="select PRODUCT_ID,PRODUCT_DESC from cr_product_m where REC_STATUS='A'";	
				  logger.info("In product......Dao Impl"+query);
				  mainList = ConnectionReportDumpsDAO.sqlSelect(query);
				  for(int i=0;i<mainList.size();i++){
					  subList=(ArrayList) mainList.get(i);
					  if(subList.size()>0){
						reallocVo=new ReallocationMasterVo();
					
						reallocVo.setProductid(subList.get(0).toString());
						reallocVo.setProductval(subList.get(1).toString());
						productList.add(reallocVo);
					}
				  }
			}catch(Exception e){
				e.printStackTrace();
			}
			finally
			{
				 mainList=null;
					subList=null;
					 query=null;
					 reallocVo=null;
			}
			return productList;
	}
		
		public CollectionSummaryVO getCollectionSummary(CollectionSummaryVO collectionSummaryVO){
			
			StringBuilder qryTotalAllocated=new StringBuilder();
			StringBuilder qryfollowUpToday=new StringBuilder();
			StringBuilder qryEscalatedCase=new StringBuilder();
			StringBuilder qryEscalatedReceive=new StringBuilder();
			StringBuilder qrytotalPtpToday=new StringBuilder();
			CollectionSummaryVO collectionSummary =new CollectionSummaryVO();
			try{
				qryTotalAllocated.append("select count(1) from coll_case_dtl where user_id='"+CommonFunction.checkNull(collectionSummaryVO.getUserId())+"' AND DELINQUENCY_FLAG='Y'");
				
				qryfollowUpToday.append("select count(1) from coll_case_dtl a  join coll_follow_up_trails b  on a.LOAN_ID=b.LOAN_ID " );
				qryfollowUpToday.append(" where a.user_id='"+CommonFunction.checkNull(collectionSummaryVO.getUserId())+"'  AND DELINQUENCY_FLAG='Y' AND " );
				qryfollowUpToday.append(dbo);
				qryfollowUpToday.append(" DATE_FORMAT(b.NEXT_ACTION_DATE,'"+dateFormat+"')='"+collectionSummaryVO.getBusinessDate()+"' ");
			 	//qryfollowUpToday.append(dbo);
			 	//qryfollowUpToday.append("str_to_date('"+collectionSummaryVO.getBusinessDate()+"','"+dateFormat+"');");
				
				qryEscalatedCase.append("select count(1) from coll_case_dtl a  join coll_follow_up_trails b  on a.LOAN_ID=b.LOAN_ID " );
				qryEscalatedCase.append(" where user_id='"+CommonFunction.checkNull(collectionSummaryVO.getUserId())+"' AND DELINQUENCY_FLAG='Y'AND b.action_code='EC' AND ");
				qryEscalatedCase.append(dbo);
				qryEscalatedCase.append(" DATE_FORMAT(a.ESCLATION_DATE,'"+dateFormat+"')='"+collectionSummaryVO.getBusinessDate()+"' ");
				//qryEscalatedCase.append(dbo);
			 	//qryEscalatedCase.append("str_to_date('"+collectionSummaryVO.getBusinessDate()+"','"+dateFormat+"');");
				
				qryEscalatedReceive.append("select count(1) from coll_case_dtl a where user_id in (select USER_ID from sec_user_m WHERE USER_REPORTING_TO ='"+CommonFunction.checkNull(collectionSummaryVO.getUserId())+"' ) " );
				qryEscalatedReceive.append(" and ESCALATION_FLAG='Y'AND DELINQUENCY_FLAG='Y' AND ");
				qryEscalatedReceive.append(dbo);
				qryEscalatedReceive.append(" DATE_FORMAT(ESCLATION_DATE,'"+dateFormat+"')='"+collectionSummaryVO.getBusinessDate()+"' ");
				
				//qryEscalatedReceive.append(dbo);
			 	//qryEscalatedReceive.append("str_to_date('"+collectionSummaryVO.getBusinessDate()+"','"+dateFormat+"');");
				
				qrytotalPtpToday.append("select count(1) from coll_case_dtl a  join coll_follow_up_trails b  on a.LOAN_ID=b.LOAN_ID " );
				qrytotalPtpToday.append(" where user_id='"+CommonFunction.checkNull(collectionSummaryVO.getUserId())+"' AND DELINQUENCY_FLAG='Y' AND b.action_code='PTP' and ");
				qrytotalPtpToday.append(dbo);
				qrytotalPtpToday.append(" DATE_FORMAT(b.ACTION_DATE,'"+dateFormat+"')='"+collectionSummaryVO.getBusinessDate()+"' ");
				
				//qrytotalPtpToday.append(dbo);
			 	//qrytotalPtpToday.append("str_to_date('"+collectionSummaryVO.getBusinessDate()+"','"+dateFormat+"');");
			
				String totalAllocated=ConnectionReportDumpsDAO.singleReturn(qryTotalAllocated.toString());
				String followUpToday=ConnectionReportDumpsDAO.singleReturn(qryfollowUpToday.toString());
				String escalatedCase=ConnectionReportDumpsDAO.singleReturn(qryEscalatedCase.toString());
				String escalatedReceive=ConnectionReportDumpsDAO.singleReturn(qryEscalatedReceive.toString());
				
				String totalPtpToday=ConnectionReportDumpsDAO.singleReturn(qrytotalPtpToday.toString());
				
				collectionSummary.setTotalAllocatedCase(totalAllocated);
				collectionSummary.setFollowupDueToday(followUpToday);
				collectionSummary.setEscalatedCases(escalatedCase);
				collectionSummary.setEscalatedRecive(escalatedReceive);
				collectionSummary.setTotalPTPDoneToday(totalPtpToday);
			}catch(Exception e){
				e.printStackTrace();
			}
			return collectionSummary;
		}
		public ArrayList<ReallocationMasterVo> getProductName() {
			ArrayList productlist = new ArrayList();
			ReallocationMasterVo reportName = null;
			try {
				logger.info("In getProductName..........................DAOImpl");
				String query ="select PRODUCT_CATEGORY,PRODUCT_CATEGORY_DESC from CR_PRODUCTCATEGORY_M";
				
				logger.info("query....... "+query);		
				ArrayList formatlist = ConnectionReportDumpsDAO.sqlSelect(query.toString());
				logger.info("getProductName()----> " + formatlist.size());
				for (int i = 0; i < formatlist.size(); i++) {
					
					ArrayList data = (ArrayList) formatlist.get(i);
					if (data.size()>0) {
						
						reportName = new ReallocationMasterVo();
						reportName.setProducTCategory(( data.get(0).toString()));
						reportName.setProducTCategoryID((data.get(1).toString()));
						productlist.add(reportName);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return productlist;
			
	}
		
		public ArrayList<ReallocationMasterVo> getLoanClassification() {
			ArrayList productlist = new ArrayList();
			ReallocationMasterVo reportName = null;
			try {
				logger.info("In getProductName..........................DAOImpl");
				String query ="select value,description from generic_master where generic_key='sale_off_flag' ";
				
				logger.info("query....... "+query);		
				ArrayList formatlist = ConnectionReportDumpsDAO.sqlSelect(query.toString());
				logger.info("getProductName()----> " + formatlist.size());
				for (int i = 0; i < formatlist.size(); i++) {
					
					ArrayList data = (ArrayList) formatlist.get(i);
					if (data.size()>0) {
						
						reportName = new ReallocationMasterVo();
						reportName.setProducTCategory(( data.get(0).toString()));
						reportName.setProducTCategoryID((data.get(1).toString()));
						productlist.add(reportName);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return productlist;
			
	}

}