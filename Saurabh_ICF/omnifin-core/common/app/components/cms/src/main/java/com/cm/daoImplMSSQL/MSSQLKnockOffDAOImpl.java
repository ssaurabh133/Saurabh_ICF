package com.cm.daoImplMSSQL;

import java.text.DecimalFormat;
import java.util.ArrayList;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.cm.dao.KnockOffDAO;
import com.cm.vo.KnockOffAuthorVO;
import com.cm.vo.KnockOffMakerVO;
import com.cm.vo.KnockOffSearchVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;

public class MSSQLKnockOffDAOImpl implements KnockOffDAO {
	
	private static final Logger logger = Logger.getLogger(MSSQLKnockOffDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dbType=resource.getString("lbl.dbType");
	String dbo=resource.getString("lbl.dbPrefix");
	//String selectFrom = resource.getString("lbl.selectFrom");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	public ArrayList<KnockOffSearchVO> searchKnockOffData(KnockOffSearchVO vo, String type) 
	{
		StringBuilder loanId = new StringBuilder();
		StringBuilder loanNo = new StringBuilder();
		StringBuilder customerName = new StringBuilder();
		StringBuilder businessPartnerId = new StringBuilder();
		StringBuilder businessPartnerName = new StringBuilder();
		
//		String loanId="";
//		String loanNo="";
//		String customerName="";
//		String businessPartnerId="";
//		String businessPartnerName="";
//		String userName="";
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		ArrayList searchlist=new ArrayList();
		StringBuilder bufInsSql =	new StringBuilder();
        StringBuilder bufInsSqlTempCount = new StringBuilder();
        ArrayList data = null;
      	KnockOffSearchVO vo1= null;
        
		ArrayList<KnockOffSearchVO> detailList=new ArrayList();
		logger.info("here userid++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+vo.getReportingToUserId());
//		try{
//			String userNameQ="select USER_NAME from sec_user_m where REC_STATUS='A' and USER_ID='"+vo.getReportingToUserId()+"'";
//			userName=ConnectionDAO.singleReturn(userNameQ);
//			logger.info("userNameQ: "+userNameQ+" userName: "+userName);
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//		}
	       try{
	              logger.info("In searchKnockOffData().....................................Dao Impl");
	              
	              loanId.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID()).trim()));
	              loanNo.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLoanNo()).trim()));
	              customerName.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName()).trim()));
	              businessPartnerId.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBPNID()).trim()));
	              businessPartnerName.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getBusinessPartnerName()).trim()));
	              boolean appendSQL=false;
	            bufInsSql.append("select a.loan_no, b.customer_name,e.bp_type,e.bp_name, f.loan_id, f.knockoff_id,f.KNOCKOFF_AMOUNT_RECEIVABLE, (SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=F.MAKER_ID) MAKER_ID  " +
	            	 " ");
	            		
	            	bufInsSql.append("from cr_loan_dtl a,gcd_customer_m b, business_partner_view e,cr_knockoff_m f" +
					 " where a.loan_id=f.loan_id and b.customer_id = a.loan_customer_id and e.BP_TYPE=f.BP_TYPE and e.BP_ID=f.BP_ID " +
					 " and e.LOAN_ID=f.LOAN_ID and f.rec_status='"+type+"'" +
					 " AND A.LOAN_BRANCH='"+vo.getBranchId()+"' ");
	              bufInsSqlTempCount.append("select count(1) " +
	            	 " from cr_loan_dtl a,gcd_customer_m b, business_partner_view e,cr_knockoff_m f" +
	            	 " where a.loan_id=f.loan_id and b.customer_id = a.loan_customer_id and e.BP_TYPE=f.BP_TYPE and e.BP_ID=f.BP_ID " +
	            	 " and e.LOAN_ID=f.LOAN_ID and f.rec_status='"+type+"'" +
					 " AND A.LOAN_BRANCH='"+vo.getBranchId()+"' ");
				  if(vo.getStage()!=null && !vo.getStage().equalsIgnoreCase("F"))
					{
					  bufInsSql.append(" AND F.MAKER_ID='"+vo.getReportingToUserId()+"' ");
					  bufInsSqlTempCount.append(" AND F.MAKER_ID='"+vo.getReportingToUserId()+"' ");
					}
				  else if(vo.getStage()!=null && vo.getStage().equalsIgnoreCase("F"))
					{
					  bufInsSql.append(" AND F.MAKER_ID!='"+vo.getUserId()+"' ");
					  bufInsSqlTempCount.append(" AND F.MAKER_ID!='"+vo.getUserId()+"' ");
					}
	            
					if((!((loanId.toString()).equalsIgnoreCase("")))&&(!((customerName.toString()).equalsIgnoreCase("")))&&(!((businessPartnerId.toString()).equalsIgnoreCase("")))&&(!((businessPartnerName.toString()).equalsIgnoreCase(""))))
					{
	    	    	  bufInsSql.append(" AND a.loan_id='"+loanId+"' AND b.Customer_Name like'%"+customerName+"%' AND e.bp_id='"+businessPartnerId+"' AND e.bp_name ='"+businessPartnerName+"'");
	    	    	  bufInsSqlTempCount.append(" AND a.loan_id='"+loanId+"' AND b.Customer_Name like'%"+customerName+"%' AND e.bp_id='"+businessPartnerId+"' AND e.bp_name ='"+businessPartnerName+"'");
	    	      }
					if(!((loanId.toString()).equalsIgnoreCase("")) || !((customerName.toString()).equalsIgnoreCase("")) || !((businessPartnerId.toString()).equalsIgnoreCase("")) || !((businessPartnerName.toString()).equalsIgnoreCase(""))|| !(vo.getLbxUserId().equalsIgnoreCase(""))){
						appendSQL=true;
					}
					
					if(appendSQL){
						bufInsSql.append(" AND");
						bufInsSqlTempCount.append(" AND");
	              }

	        	 if((!((loanId.toString()).equalsIgnoreCase("")))) {
	    	         bufInsSql.append(" a.loan_id='"+loanId+"' AND");
	    	         bufInsSqlTempCount.append(" a.loan_id='"+loanId+"' AND");
	    	    	 appendSQL=true;
	    	    	  
	    	      }
				if((!((customerName.toString()).equalsIgnoreCase("")))) {
	    	    	  bufInsSql.append(" b.Customer_Name like'%"+customerName+"%' AND");
	    	    	  bufInsSqlTempCount.append(" b.Customer_Name like'%"+customerName+"%' AND");
	    	    	  appendSQL=true;
	    	      }
				if((!((businessPartnerId.toString()).equalsIgnoreCase("")))) {
	    	    	  bufInsSql.append(" e.bp_id='"+businessPartnerId+"' AND");
	    	    	  bufInsSqlTempCount.append(" e.bp_id='"+businessPartnerId+"' AND");
	    	    	  appendSQL=true;
	    	      }
				if((!((businessPartnerName.toString()).equalsIgnoreCase("")))) {
	    	    	  bufInsSql.append(" e.bp_name ='"+businessPartnerName+"' AND");
	    	    	  bufInsSqlTempCount.append(" e.bp_name ='"+businessPartnerName+"' AND");
	    	    	  appendSQL=true;
	    	      }
				if((!(CommonFunction.checkNull(vo.getLbxUserId()).equalsIgnoreCase("")))&& vo.getStage().equalsIgnoreCase("F")) {
	    	    	  bufInsSql.append(" F.MAKER_ID ='"+vo.getLbxUserId()+"' AND");
	    	    	  bufInsSqlTempCount.append(" F.MAKER_ID ='"+vo.getLbxUserId()+"' AND");
	    	    	  appendSQL=true;
	    	      }
				
				logger.info("In appendSQL true---- "+appendSQL);
				
				if(appendSQL)
				{					
					String  tmp = bufInsSql.toString();
					String tmp1 = bufInsSqlTempCount.toString();
					logger.info("In searchKnockOffData() ## tmp ## "+tmp);
					logger.info("In searchKnockOffData() ## tmp1 ## "+tmp1);
					logger.info("In appendSQL true----  in check index Of tmp"+tmp.lastIndexOf("AND") +"------"+(tmp.length()-3));
					logger.info("In appendSQL true----  in check index Of tmp1"+tmp1.lastIndexOf("AND") +"------"+(tmp1.length()-3));
	                if(tmp.lastIndexOf("AND") == (tmp.length()-3) && tmp1.lastIndexOf("AND") == (tmp1.length()-3))
	                {
		                logger.info("In appendSQL true----  in check index Of");
		                tmp = (tmp).substring(0,(tmp.length()-4)).trim();
		                tmp1 = (tmp1).substring(0,(tmp1.length()-4)).trim();
		                logger.info("search Query...tmp. "+tmp);
		                searchlist = ConnectionDAO.sqlSelect(tmp);
		                count =Integer.parseInt(ConnectionDAO.singleReturn(tmp1.toString()));
	                }
	                else
	                {
	                	  logger.info("search Query...tmp."+tmp);
	                      searchlist = ConnectionDAO.sqlSelect(tmp);
	                      count =Integer.parseInt(ConnectionDAO.singleReturn(tmp1.toString()));
	                }
				}
				else
				{
				  logger.info("search Query...else-------."+bufInsSql);
				  count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
//				  if((loanId==null && customerName==null && businessPartnerId==null && businessPartnerName==null) || ((loanId.toString()).equalsIgnoreCase("") 
//						  && (customerName.toString()).equalsIgnoreCase("") && (businessPartnerId.toString()).equalsIgnoreCase("") && (businessPartnerName.toString()).equalsIgnoreCase(""))
//						  || vo.getCurrentPageLink()>1)
//				  {
					  logger.info("current PAge Link no .................... "+vo.getCurrentPageLink());
					  if(vo.getCurrentPageLink()>1)
					  {
						  startRecordIndex = (vo.getCurrentPageLink()-1)*no;
						  endRecordIndex = no;
						  logger.info("startRecordIndex .................... "+startRecordIndex);
						  logger.info("endRecordIndex .................... "+endRecordIndex);
					  }
					  //bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));		
					  bufInsSql.append(" ORDER BY a.loan_id OFFSET ");
			            bufInsSql.append(startRecordIndex);
			            bufInsSql.append(" ROWS FETCH next ");
			            bufInsSql.append(endRecordIndex);
			            bufInsSql.append(" ROWS ONLY ");
//				  }
				  logger.info("query : "+bufInsSql);
				  searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
				}
				int size = searchlist.size();
              logger.info("searchKnockOffData: "+size);
              for(int i=0;i<size;i++){
              logger.info("searchKnockOffData: "+searchlist.get(i).toString());
              data=(ArrayList)searchlist.get(i);
              if(data.size()>0){
            	  vo1= new KnockOffSearchVO();
            	  if(type.equalsIgnoreCase("P"))
            	  {
            		  vo1.setLoanNo("<a href=knockOffSearch.do?method=openKnockOffValues&loanId="
      					+ (CommonFunction.checkNull(data.get(4)).trim())
      					+ "&knockOffId="+(CommonFunction.checkNull(data.get(5)).trim())+">"
      					+ (CommonFunction.checkNull(data.get(0)).trim()) + "</a>");
            	  }
            	  if(type.equalsIgnoreCase("F"))
            	  {
            		  vo1.setLoanNo("<a href=knockOffSearch.do?method=openKnockOffValuesAuthor&loanId="
  	      					+ (CommonFunction.checkNull(data.get(4)).trim())
  	      					+ "&knockOffId="+(CommonFunction.checkNull(data.get(5)).trim())+">"
  	      					+ (CommonFunction.checkNull(data.get(0)).trim()) + "</a>");
            	  }
            	  vo1.setCustomerName((CommonFunction.checkNull(data.get(1)).toString().trim()));
            	  if((CommonFunction.checkNull(data.get(2)).trim()).equalsIgnoreCase("CS"))
            		  vo1.setBusinessPartnerType("CUSTOMER");
            	  if((CommonFunction.checkNull(data.get(2)).trim()).equalsIgnoreCase("DS"))
            		  vo1.setBusinessPartnerType("BROKER/DSA");
            	  if((CommonFunction.checkNull(data.get(2)).trim()).equalsIgnoreCase("MF"))
            		  vo1.setBusinessPartnerType("MANUFACTURER");
            	  if((CommonFunction.checkNull(data.get(2)).trim()).equalsIgnoreCase("SU"))
            		  vo1.setBusinessPartnerType("SUPPLIER/DELER/VENDOR");
            	  vo1.setBusinessPartnerName((CommonFunction.checkNull(data.get(3)).toString().trim()));
            	  vo1.setLbxLoanNoHID((CommonFunction.checkNull(data.get(4)).toString().trim()));
            	  vo1.setKnockOffId((CommonFunction.checkNull(data.get(5)).toString().trim()));
            	  if((CommonFunction.checkNull(data.get(6)).trim()).equalsIgnoreCase(""))
					{
						vo1.setKnockOffAmount("0");
					}
					else
					{
						Number knockOffamount = myFormatter.parse((CommonFunction.checkNull(data.get(6)).trim()));
						vo1.setKnockOffAmount(myFormatter.format(knockOffamount));
					}
            	  vo1.setReportingToUserId((CommonFunction.checkNull(data.get(7)).toString().trim()));
            	  vo1.setTotalRecordSize(count);
            	  detailList.add(vo1);
            }

		}

		}catch(Exception e){
			e.printStackTrace();
		}

		finally
		{
			loanId= null;
			loanNo= null;
			customerName= null;
			businessPartnerId= null;
			businessPartnerName= null;
			searchlist= null;
			bufInsSql = null;
            bufInsSqlTempCount = null;
            data = null;
          	vo1= null;
		}
		return detailList;
}
	
	public ArrayList<KnockOffMakerVO> getKnockOffData(String knockOffId,String type) 
	{
		logger.info("CreditManagementDAOImpl...............getKnockOffData");
		ArrayList searchlist=new ArrayList();
		StringBuilder bufInsSql =	new StringBuilder();
		ArrayList data= null;
		KnockOffMakerVO vo1= null;
		
		ArrayList<KnockOffMakerVO> detailList=new ArrayList();
	    try{
			bufInsSql.append("select distinct a.loan_no, b.customer_name,e.bp_type,e.bp_name, f.bp_id,f.remarks,f.loan_id,F.MAKER_REMARKS,a.REC_STATUS,");
			bufInsSql.append(dbo);
			bufInsSql.append("DATE_FORMAT(f.KNOCKOFF_DATE,'"+dateFormat+"')"+
					 " from cr_loan_dtl a,gcd_customer_m b, cr_knockoff_dtl d, business_partner_view e,cr_knockoff_m f" +
					 "  where a.loan_id=f.loan_id and b.customer_id = a.loan_customer_id and e.BP_TYPE=f.BP_TYPE and e.BP_ID=f.BP_ID "+
					 " and d.KNOCKOFF_ID=f.KNOCKOFF_ID and f.rec_status='"+type+"' AND f.knockoff_id='"+knockOffId+"'");
			logger.info("Query..."+bufInsSql.toString());
			searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
			int size = searchlist.size();
            logger.info("getKnockOffData: "+size);
            for(int i=0;i<size;i++){
            logger.info("getKnockOffData: "+searchlist.get(i).toString());
            data=(ArrayList)searchlist.get(i);
            if(data.size()>0){
            	vo1= new KnockOffMakerVO();
            	vo1.setLoanNumber((CommonFunction.checkNull(data.get(0)).trim()));
            	vo1.setCustomerName((CommonFunction.checkNull(data.get(1)).trim()));
            	if((CommonFunction.checkNull(data.get(2)).trim()).equalsIgnoreCase("CS"))
            		vo1.setBusinessPartnerType("CUSTOMER");
            	if((CommonFunction.checkNull(data.get(2)).trim()).equalsIgnoreCase("DS"))
            		vo1.setBusinessPartnerType("BROKER/DSA");
            	if((CommonFunction.checkNull(data.get(2)).trim()).equalsIgnoreCase("MF"))
            		vo1.setBusinessPartnerType("MANUFACTURER");
            	if((CommonFunction.checkNull(data.get(2)).trim()).equalsIgnoreCase("SU"))
            		vo1.setBusinessPartnerType("SUPPLIER/DELER/VENDOR");
            	vo1.setBusinessPartnerName((CommonFunction.checkNull(data.get(3)).trim()));
            	vo1.setLbxBusinessPartnearHID((CommonFunction.checkNull(data.get(4)).trim()));
            	vo1.setAuthorRemarks((CommonFunction.checkNull(data.get(5)).trim()));
            	vo1.setLbxLoanNoHID((CommonFunction.checkNull(data.get(6)).trim()));
            	vo1.setRemarks((CommonFunction.checkNull(data.get(7)).trim()));
            	vo1.setLoanRecStatus((CommonFunction.checkNull(data.get(8)).trim()));
				vo1.setValueDate((CommonFunction.checkNull(data.get(9)).trim()));
            	detailList.add(vo1);
            }
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			searchlist= null;
			bufInsSql = null;
			data= null;
			vo1= null;
		}
		return detailList;
}
	public ArrayList<KnockOffMakerVO> getKnockOffCancellationData(String knockOffId,String type,String canFlag) 
	{
		logger.info("in getKnockOffCancellationData() of KnockOffDAOImpl.");
		ArrayList searchlist=new ArrayList();
		StringBuilder bufInsSql =	new StringBuilder();
		ArrayList data= null;
		KnockOffMakerVO vo1= null;
		
		ArrayList<KnockOffMakerVO> detailList=new ArrayList();
	    try{
			bufInsSql.append("select b.LOAN_NO ,c.customer_name,a.BP_TYPE,d.bp_name,a.BP_ID,a.CAN_AUTHOR_REMARKS,a.LOAN_ID,a.CAN_MAKER_REMARKS,a.KNOCKOFF_ID " +
					"from cr_knockoff_m a join cr_loan_dtl b on(b.LOAN_ID=a.LOAN_ID) join gcd_customer_m c on(c.customer_id=b.loan_customer_id) " +
					"join business_partner_view d on(d.BP_TYPE=a.BP_TYPE and d.BP_TYPE=a.BP_TYPE and d.LOAN_ID=b.LOAN_ID) where a.REC_STATUS='A'" +
					" and a.CANCELLATION_FLAG='"+canFlag.trim()+"' and a.KNOCKOFF_ID='"+knockOffId+"'");
					
			logger.info("in getKnockOffCancellationData() of KnockOffDAOImpl.    Query  :  "+bufInsSql.toString());
			searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
			int size = searchlist.size();
            logger.info("getKnockOffData: "+size);
            for(int i=0;i<size;i++){
            logger.info("getKnockOffData: "+searchlist.get(i).toString());
            data=(ArrayList)searchlist.get(i);
            if(data.size()>0){
            	vo1= new KnockOffMakerVO();
            	vo1.setLoanNumber((CommonFunction.checkNull(data.get(0)).trim()));
            	vo1.setCustomerName((CommonFunction.checkNull(data.get(1)).trim()));
            	if((CommonFunction.checkNull(data.get(2)).trim()).equalsIgnoreCase("CS"))
            		vo1.setBusinessPartnerType("CUSTOMER");
            	if((CommonFunction.checkNull(data.get(2)).trim()).equalsIgnoreCase("DS"))
            		vo1.setBusinessPartnerType("BROKER/DSA");
            	if((CommonFunction.checkNull(data.get(2)).trim()).equalsIgnoreCase("MF"))
            		vo1.setBusinessPartnerType("MANUFACTURER");
            	if((CommonFunction.checkNull(data.get(2)).trim()).equalsIgnoreCase("SU"))
            		vo1.setBusinessPartnerType("SUPPLIER/DELER/VENDOR");
            	vo1.setBusinessPartnerName((CommonFunction.checkNull(data.get(3)).trim()));
            	vo1.setLbxBusinessPartnearHID((CommonFunction.checkNull(data.get(4)).trim()));
            	vo1.setAuthorRemarks((CommonFunction.checkNull(data.get(5)).trim()));
            	vo1.setLbxLoanNoHID((CommonFunction.checkNull(data.get(6)).trim()));
            	vo1.setRemarks((CommonFunction.checkNull(data.get(7)).trim()));
            	vo1.setKnockOffId((CommonFunction.checkNull(data.get(8)).trim()));
            	detailList.add(vo1);
            }
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			searchlist= null;
			bufInsSql = null;
			data= null;
			vo1= null;
		}
		return detailList;
}
	
	public ArrayList<KnockOffMakerVO> searchKOCData(String knockOffId,int currentPageLink,String canFlag,String userId) 
	{
		logger.info("in searchKOCData() of KnockOffDAOImpl.");
		ArrayList searchlist=new ArrayList();
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		StringBuilder bufInsSql =	new StringBuilder();
		StringBuffer bufInsSqlTempCount = new StringBuffer();
		ArrayList data= null;
		KnockOffMakerVO vo1= null;
		
		ArrayList<KnockOffMakerVO> detailList=new ArrayList();
	    try
	    {
	    	if(canFlag.trim().equals("P")){
	    	bufInsSql.append("select a.KNOCKOFF_ID ,b.LOAN_NO ,c.customer_name,a.BP_TYPE,d.bp_name,a.KNOCKOFF_AMOUNT_RECEIVABLE,a.KNOCKOFF_AMOUNT_PAYABLE,a.LOAN_ID ");
			bufInsSql.append("from cr_knockoff_m a join cr_loan_dtl b on(b.LOAN_ID=a.LOAN_ID) join gcd_customer_m c on(c.customer_id=b.loan_customer_id)" +
					" join business_partner_view d on(d.BP_TYPE=a.BP_TYPE and d.BP_TYPE=a.BP_TYPE and d.LOAN_ID=b.LOAN_ID) where a.REC_STATUS='A'" +
					"  and a.CANCELLATION_FLAG='"+canFlag.trim()+"' AND A.CAN_MAKER_ID='"+userId+"'");
	    }if(canFlag.trim().equals("F")){
	   
			bufInsSql.append("select a.KNOCKOFF_ID ,b.LOAN_NO ,c.customer_name,a.BP_TYPE,d.bp_name,a.KNOCKOFF_AMOUNT_RECEIVABLE,a.KNOCKOFF_AMOUNT_PAYABLE,a.LOAN_ID " +
					" from cr_knockoff_m a join cr_loan_dtl b on(b.LOAN_ID=a.LOAN_ID) join gcd_customer_m c on(c.customer_id=b.loan_customer_id)" +
					" join business_partner_view d on(d.BP_TYPE=a.BP_TYPE and d.BP_TYPE=a.BP_TYPE and d.LOAN_ID=b.LOAN_ID) where a.REC_STATUS='A'" +
					"  and a.CANCELLATION_FLAG='"+canFlag.trim()+"' AND A.CAN_MAKER_ID!='"+userId+"'");
	    }if(canFlag.trim().equals("P")){
			bufInsSqlTempCount.append("select count(1) from (select a.KNOCKOFF_ID ,b.LOAN_NO ,c.customer_name,a.BP_TYPE,d.bp_name,a.KNOCKOFF_AMOUNT_RECEIVABLE ,a.KNOCKOFF_AMOUNT_PAYABLE,a.LOAN_ID " +
					" from cr_knockoff_m a join cr_loan_dtl b on(b.LOAN_ID=a.LOAN_ID) join gcd_customer_m c on(c.customer_id=b.loan_customer_id)" +
					" join business_partner_view d on(d.BP_TYPE=a.BP_TYPE and d.BP_TYPE=a.BP_TYPE and d.LOAN_ID=b.LOAN_ID) where a.REC_STATUS='A'" +
					"  and a.CANCELLATION_FLAG='"+canFlag.trim()+"' AND A.CAN_MAKER_ID='"+userId+"'");
	    }if(canFlag.trim().equals("F")){
			bufInsSqlTempCount.append("select count(1) from (select a.KNOCKOFF_ID ,b.LOAN_NO ,c.customer_name,a.BP_TYPE,d.bp_name,a.KNOCKOFF_AMOUNT_RECEIVABLE ,a.KNOCKOFF_AMOUNT_PAYABLE,a.LOAN_ID " +
					" from cr_knockoff_m a join cr_loan_dtl b on(b.LOAN_ID=a.LOAN_ID) join gcd_customer_m c on(c.customer_id=b.loan_customer_id)" +
					" join business_partner_view d on(d.BP_TYPE=a.BP_TYPE and d.BP_TYPE=a.BP_TYPE and d.LOAN_ID=b.LOAN_ID) where a.REC_STATUS='A'" +
					"  and a.CANCELLATION_FLAG='"+canFlag.trim()+"' AND A.CAN_MAKER_ID!='"+userId+"'");
	    }
			if(! knockOffId.trim().equals(""))
			{
				bufInsSql.append(" and a.KNOCKOFF_ID='"+knockOffId.trim()+"'");
				bufInsSqlTempCount.append(" and a.KNOCKOFF_ID='"+knockOffId.trim()+"'");
			}
			bufInsSqlTempCount.append(" )as b ");
			
			count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
			searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
			logger.info("current PAge Link no .................... "+currentPageLink);
			if(currentPageLink>1)
			{
				startRecordIndex = (currentPageLink-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));		
			bufInsSql.append(" ORDER BY a.KNOCKOFF_ID OFFSET ");
            bufInsSql.append(startRecordIndex);
            bufInsSql.append(" ROWS FETCH next ");
            bufInsSql.append(endRecordIndex);
            bufInsSql.append(" ROWS ONLY ");
            
			logger.info("in searchKOCData() of KnockOffDAOImpl.    Search Query  :  "+bufInsSql.toString());
			logger.info("in searchKOCData() of KnockOffDAOImpl.    Count Query  :  "+bufInsSqlTempCount.toString());
			
		
			int size = searchlist.size();
            logger.info("getKnockOffData: "+size);
            for(int i=0;i<size;i++)
            {
               data=(ArrayList)searchlist.get(i);
               if(data.size()>0)
               {
            	   vo1= new KnockOffMakerVO(); 
            	   if(canFlag.trim().equals("P"))
            		   vo1.setKnockOffId("<a href=knockOffCancellationDispatchAction.do?method=openKnockOffCancellationValues&knockOffId="+(CommonFunction.checkNull(data.get(0)).trim())+"&loanId="+(CommonFunction.checkNull(data.get(7)).trim())+"&val=P>"+(CommonFunction.checkNull(data.get(0)).trim())+"</a>");
            	   if(canFlag.trim().equals("F"))
            		   vo1.setKnockOffId("<a href=knockOffCancellationDispatchAction.do?method=openKnockOffCancellationAuthorValues&knockOffId="+(CommonFunction.checkNull(data.get(0)).trim())+"&loanId="+(CommonFunction.checkNull(data.get(7)).trim())+">"+(CommonFunction.checkNull(data.get(0)).trim())+"</a>");
            	   //vo1.setKnockOffId((CommonFunction.checkNull(data.get(0)).trim()));
            	   vo1.setLoanNumber((CommonFunction.checkNull(data.get(1)).trim()));
            	   vo1.setCustomerName((CommonFunction.checkNull(data.get(2)).trim()));
            	   if((CommonFunction.checkNull(data.get(3)).trim()).equalsIgnoreCase("CS"))
            		   vo1.setBusinessPartnerType("CUSTOMER");
            	   if((CommonFunction.checkNull(data.get(3)).trim()).equalsIgnoreCase("DS"))
            		   vo1.setBusinessPartnerType("BROKER/DSA");
            	   if((CommonFunction.checkNull(data.get(3)).trim()).equalsIgnoreCase("MF"))
            		   vo1.setBusinessPartnerType("MANUFACTURER");
            	   if((CommonFunction.checkNull(data.get(3)).trim()).equalsIgnoreCase("SU"))
            		   vo1.setBusinessPartnerType("SUPPLIER/DELER/VENDOR");
            	   
            	   vo1.setBusinessPartnerName((CommonFunction.checkNull(data.get(4)).trim()));
            	             	              	   
            	   if((CommonFunction.checkNull(data.get(5)).trim()).equalsIgnoreCase(""))
						vo1.setKnockOffAmountR("0.00");
				   else
				   {
						Number knockOffamount = myFormatter.parse((CommonFunction.checkNull(data.get(5)).trim()));
						vo1.setKnockOffAmountR(myFormatter.format(knockOffamount));
				   }
            	   if((CommonFunction.checkNull(data.get(6)).trim()).equalsIgnoreCase(""))
						vo1.setKnockOffAmountP("0.00");
				   else
				   {
						Number knockOffamount = myFormatter.parse((CommonFunction.checkNull(data.get(6)).trim()));
						vo1.setKnockOffAmountP(myFormatter.format(knockOffamount));
				   }
            	   vo1.setTotalRecordSize(count);
            	   detailList.add(vo1);
               }
            }
		}catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			searchlist= null;
			bufInsSql = null;
			data= null;
			vo1= null;
		}
		return detailList;
}
	

public	boolean saveKnockOffCancelData(String knockOffId, String makerRemarks,String userId,String makerDate,String canStatus)
{
	logger.info("In saveKnockOffCancelData() of KnockOffDAOImpl.");
    boolean status=false;
	StringBuilder queryUpdate = new StringBuilder();
	PrepStmtObject insertPrepStmtObject = null;
	ArrayList qryList =new ArrayList();
	queryUpdate.append("update cr_knockoff_m set CANCELLATION_FLAG='"+canStatus.trim()+"',CAN_MAKER_REMARKS='"+makerRemarks.trim()+"',CAN_MAKER_DATE=");
	//queryUpdate.append("DATE_ADD(STR_TO_DATE('"+makerDate.trim()+"', '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND)");
	//Saurabh space starts
	if(dbType.equalsIgnoreCase("MSSQL"))
	{
		queryUpdate.append("dbo.STR_TO_DATE('"+makerDate.trim()+"','"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
	}
	else
	{
		queryUpdate.append("DATE_ADD(STR_TO_DATE('"+makerDate.trim()+"', '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND)");
	}
	//Saurabh space ends

	queryUpdate.append(",CAN_MAKER_ID='"+userId.trim()+"' where knockoff_id='"+knockOffId.trim()+"'");
				
	insertPrepStmtObject = new PrepStmtObject();
	try
	{	
		insertPrepStmtObject.setSql(queryUpdate.toString());			
		qryList.add(insertPrepStmtObject);
		logger.info("In saveKnockOffDetails ........ update query: "+queryUpdate.toString());			
		status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
		logger.info("In saveKnockOffDetails.........update status: "+status);
		queryUpdate = null;
	}
	catch (Exception e) {
			e.printStackTrace();
	}
	return status;
}
	public ArrayList<Object> getKnockOffDetailsDataMaker(String knockOffId,String adviceType,String type)
	{			
		ArrayList<Object> loandetailList = new ArrayList<Object>();
		KnockOffMakerVO vo = null;
		StringBuilder query = new StringBuilder();
		ArrayList bdetails1= null;
		ArrayList bdetails = null;
		try
	       {		    	  
			query.append("select ld.LOAN_ID,ld.LOAN_NO,cc.CHARGE_DESC,ko.TXN_ADVICE_ID,ko.ADVICE_AMOUNT,");
			query.append("ISNULL((ta.ADVICE_AMOUNT-ta.TXN_ADJUSTED_AMOUNT-ta.AMOUNT_IN_PROCESS),0) as BALANCE_AMOUNT,ko.AMOUNT_IN_PROCESS,ko.KNOCKOFF_ID,ko.KNOCKOFF_AMOUNT,");
			query.append(dbo);
			query.append("DATE_FORMAT(ta.ADVICE_DATE,'"+dateFormat+"') ADVICE_DATE"+
						" from cr_knockoff_dtl ko, cr_loan_dtl ld, cr_txnadvice_dtl ta, com_charge_code_m cc,cr_knockoff_m km "+
						" where ld.LOAN_ID=km.loan_id and ta.charge_code_id=cc.charge_code and ta.txnadvice_id=ko.txn_advice_id and"+ 
						" ko.knockoff_id=km.knockoff_id and ko.advice_type='"+adviceType+"' and km.knockoff_id='"+knockOffId+"' and ko.rec_status=km.rec_status and km.rec_status='"+type+"'");
			logger.info("query getKnockOffDetailsDataMaker:-adviceType:-"+adviceType+":---"+query);	               
            bdetails = ConnectionDAO.sqlSelect(query.toString());
            int size = bdetails.size();
	    	logger.info("getReceivable Advice Data: "+size);	
	    	if(adviceType.equalsIgnoreCase("R"))
	    	{
		    	for(int i=0;i<size;i++)
		    	  {
		    	  logger.info("getPayable Advice Data: " +bdetails.get(i).toString());
		    	  bdetails1=(ArrayList)bdetails.get(i);
		    	    	if(bdetails1.size()>0)
		    			{
		    	    	   vo =  new KnockOffMakerVO();	
		    	    	   vo.setLoanId((CommonFunction.checkNull(bdetails1.get(0)).trim()));
		    	    		vo.setLoanNumber((CommonFunction.checkNull(bdetails1.get(1)).trim()));	    	    		
		    	    		vo.setCharges((CommonFunction.checkNull(bdetails1.get(2)).trim()));
		    	    		vo.setTxnAdviceIdR((CommonFunction.checkNull(bdetails1.get(3)).trim()));
		    	    		if((CommonFunction.checkNull(bdetails1.get(4)).trim()).equalsIgnoreCase(""))
							{
								vo.setOriginalAmountR("0");
							}
							else
							{
								Number originalAmount = myFormatter.parse((CommonFunction.checkNull(bdetails1.get(4)).trim()));
								vo.setOriginalAmountR(myFormatter.format(originalAmount));
							}
		    	    		
		    	    		if((CommonFunction.checkNull(bdetails1.get(5)).trim()).equalsIgnoreCase(""))
							{
								vo.setBalanceAmountR("0");
							}
							else
							{
								Number balanceAmount = myFormatter.parse((CommonFunction.checkNull(bdetails1.get(5)).trim()));
								vo.setBalanceAmountR(myFormatter.format(balanceAmount));
							}
		    	    	    
		    	    		if((CommonFunction.checkNull(bdetails1.get(6)).trim()).equalsIgnoreCase(""))
							{
								vo.setAmountInProcessR("0");
							}
							else
							{
								Number amountInProcess = myFormatter.parse((CommonFunction.checkNull(bdetails1.get(6)).trim()));
								vo.setAmountInProcessR(myFormatter.format(amountInProcess));
							}
		    	    		
		    	    	    vo.setKnockOffIdR((CommonFunction.checkNull(bdetails1.get(7)).trim()));
		    	    	    
		    	    	    if((CommonFunction.checkNull(bdetails1.get(8)).trim()).equalsIgnoreCase(""))
							{
								vo.setKnockOffAmountR("0");
							}
							else
							{
								Number knockOffamountR = myFormatter.parse((CommonFunction.checkNull(bdetails1.get(8)).trim()));
								vo.setKnockOffAmountR(myFormatter.format(knockOffamountR));
							}
		    	    	    vo.setAdviceDateR((CommonFunction.checkNull(bdetails1.get(9)).trim()));
		    	    	    loandetailList.add(vo);
		    			}
		    		}
	    	}
	    	if(adviceType.equalsIgnoreCase("P"))
	    	{
		    	for(int i=0;i<size;i++)
		    	  {
		    	  logger.info("getPayable Advice Data: " +bdetails.get(i).toString());
		    	  bdetails1=(ArrayList)bdetails.get(i);
		    	    	if(bdetails1.size()>0)
		    			{
		    	    	   vo =  new KnockOffMakerVO();	
		    	    	   vo.setLoanId((CommonFunction.checkNull(bdetails1.get(0)).trim()));
		    	    		vo.setLoanNumber((CommonFunction.checkNull(bdetails1.get(1)).trim()));	    	    		
		    	    		vo.setCharges((CommonFunction.checkNull(bdetails1.get(2)).trim()));
		    	    		vo.setTxnAdviceIdP((CommonFunction.checkNull(bdetails1.get(3)).trim()));
		    	    		
		    	    		if((CommonFunction.checkNull(bdetails1.get(4)).trim()).equalsIgnoreCase(""))
							{
								vo.setOriginalAmountP("0");
							}
							else
							{
								Number originalAmount = myFormatter.parse((CommonFunction.checkNull(bdetails1.get(4)).trim()));
								vo.setOriginalAmountP(myFormatter.format(originalAmount));
							}
		    	    		
		    	    		if((CommonFunction.checkNull(bdetails1.get(5)).trim()).equalsIgnoreCase(""))
							{
								vo.setBalanceAmountP("0");
							}
							else
							{
								Number balanceAmount = myFormatter.parse((CommonFunction.checkNull(bdetails1.get(5)).trim()));
								vo.setBalanceAmountP(myFormatter.format(balanceAmount));
							}
		    	    	    
		    	    		if((CommonFunction.checkNull(bdetails1.get(6)).trim()).equalsIgnoreCase(""))
							{
								vo.setAmountInProcessP("0");
							}
							else
							{
								Number amountInProcess = myFormatter.parse((CommonFunction.checkNull(bdetails1.get(6)).trim()));
								vo.setAmountInProcessP(myFormatter.format(amountInProcess));
							}
		    	    		
		    	    	    vo.setKnockOffIdP((CommonFunction.checkNull(bdetails1.get(7)).trim()));
		    	    	    
		    	    	    if((CommonFunction.checkNull(bdetails1.get(8)).trim()).equalsIgnoreCase(""))
							{
								vo.setKnockOffAmountP("0");
							}
							else
							{
								Number knockOffamountP = myFormatter.parse((CommonFunction.checkNull(bdetails1.get(8)).trim()));
								vo.setKnockOffAmountP(myFormatter.format(knockOffamountP));
							}
		    	    	    vo.setAdviceDateP((CommonFunction.checkNull(bdetails1.get(9)).trim()));
		    	    	    loandetailList.add(vo);
		    			}
		    		}
	    	}
    		}catch(Exception e){
			e.printStackTrace();
    		}
    		finally
    		{
    			vo = null;
    			query = null;
    			bdetails1= null;
    			bdetails=null;
    		}
    		logger.info("Value of List is in dao is="+loandetailList);
    		return loandetailList;				
    	}
	public ArrayList<Object> getTotalReceivableR(String knockOffId, String type)
    {
	   logger.info("Inside getTotalReceivableR........DAOImpl");
      ArrayList<Object> loandetailListR = new ArrayList<Object>();
      StringBuilder query = new StringBuilder();
      KnockOffMakerVO vo = null;
//      String query="";
      ArrayList bdetails = null;
      ArrayList bdetails1=null;
      try
       {	
    	  query.append("Select SUM(KNOCKOFF_AMOUNT_RECEIVABLE) from cr_knockoff_m where KNOCKOFF_ID='"+knockOffId+"' and REC_STATUS='"+type+"'");
			  logger.info("query"+query);	               
          bdetails = ConnectionDAO.sqlSelect(query.toString());
          int size = bdetails.size();
	      logger.info("getReceivable Advice Data: "+size);	   	    
	      for(int i=0;i<size;i++)
	        {
	         logger.info("getPayable Advice Data: " +bdetails.get(i).toString());
	         bdetails1=(ArrayList)bdetails.get(i);
	    	 if(bdetails1.size()>0)
			  {
	    	   vo =  new KnockOffMakerVO();
	    	   if((CommonFunction.checkNull(bdetails1.get(0)).trim()).equalsIgnoreCase(""))
	    	   {
	    		   vo.setTotalReveivable("0");
	    	   }
	    	   else
	    	   {
	    		   Number totalReceivable = myFormatter.parse((CommonFunction.checkNull(bdetails1.get(0)).trim()));
	    		   vo.setTotalReveivable(myFormatter.format(totalReceivable));
	    	   }	    	    	    
			}		    			
	    	loandetailListR.add(vo);
		    }
		}catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			vo = null;
		    query= null;
		    bdetails = null;
		    bdetails1=null;
		}
		logger.info("Value of Total Receivable dao is="+loandetailListR);
		return loandetailListR;				
	}
	public ArrayList<Object> getTotalReceivableP(String knockOffId, String type){
		
    	logger.info("Inside getTotalReceivableP...............DAOImpl");
      ArrayList<Object> loandetailListP = new ArrayList<Object>();
      StringBuilder query = new StringBuilder();
      KnockOffMakerVO vo = null;
//      String query="";
      ArrayList bdetails = null;
      ArrayList bdetails1=null;
      try
       {	
    	  query.append("Select SUM(KNOCKOFF_AMOUNT_PAYABLE) from cr_knockoff_m where KNOCKOFF_ID='"+knockOffId+"' and REC_STATUS='"+type+"'");
		  logger.info("query"+query);	               
          bdetails = ConnectionDAO.sqlSelect(query.toString());
          int size = bdetails.size();
	      logger.info("getReceivable Advice Data: "+size);	   	    
	      for(int i=0;i<size;i++)
	        {
	         logger.info("getPayable Advice Data: " +bdetails.get(i).toString());
	         bdetails1=(ArrayList)bdetails.get(i);
	    	 if(bdetails1.size()>0)
			  {
	    	   vo =  new KnockOffMakerVO();
	    	   if((CommonFunction.checkNull(bdetails1.get(0)).trim()).equalsIgnoreCase(""))
	    	   {
	    		   vo.setTotalPayable("0");
	    	   }
	    	   else
	    	   {
	    		   Number totalPayable = myFormatter.parse((CommonFunction.checkNull(bdetails1.get(0)).trim()));
	    		   vo.setTotalPayable(myFormatter.format(totalPayable));
	    	   }	    	    	    
			}		    			
	    	 loandetailListP.add(vo);
		    }
		}catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			vo = null;
		    query = null;
		    bdetails = null;
		    bdetails1=null;
		}
		logger.info("Value of Total Payable in dao is="+loandetailListP);
		return loandetailListP;				
	}



	public String saveKnockOffDetails(Object ob, String sum, String sum1 ,String[] RknockOffAmount, String[] PknockOffAmount,String loanIdNo,String[] ROriginalAmount, String[] RBalancelAmount,String[] POriginalAmount,String[] PBalancelAmount,String[] txnAdviceIdR, String[] txnAdviceIdP,String[] ProcessAmountR, String[] ProcessAmountP,String recStatus, String[] KnockOffId_R, String[] KnockOffId_P) 
	     {
	    	logger.info("In saveKnockOffDetails.......DAOImpl");
			KnockOffMakerVO vo = (KnockOffMakerVO)ob;
			boolean status = false;
//			CallableStatement cst=null;
			boolean knockOffMStatus = false;
			boolean txnstatus = false;
			boolean knockOffDtlStatusR = false;
			boolean knockOffDtlStatusP = false;
			String knockOffId="";
			StringBuilder bufInsSql = null;
			PrepStmtObject insertPrepStmtObject = null;
			StringBuilder queryUpdate = null;
//			Connection con=ConnectionDAO.getConnection();
//			String query1 = "";
//			String query2 = "";
			ArrayList qryList = null;
			StringBuilder updateQuery = null;
			PrepStmtObject insPrepStmtObject = null;
			ArrayList<Object> in =new ArrayList<Object>();
			ArrayList<Object> out =new ArrayList<Object>();
			ArrayList outMessages = new ArrayList();
			StringBuilder s2 = new StringBuilder();
			StringBuilder s3 = new StringBuilder();
			StringBuilder date = new StringBuilder();
			StringBuilder date1 = new StringBuilder();

//			String s1="";
//			String s2="";
			int s1=0;
			//****************   knock off m part**************************//
			
			if(CommonFunction.checkNull(vo.getKnockOffId()).equalsIgnoreCase(""))
			{
				
				try
				{

			       // con.setAutoCommit(false);

					logger.info("In Credit_Knockoff_dtl Procedure");
				       // cst=con.prepareCall("call Credit_Knockoff_dtl(?,?,?,STR_TO_DATE(?,'"+dateFormat+"'),?,?,?,?,?,STR_TO_DATE(?,'"+dateFormatWithTime+"'),?,?,?)");
					  	in.add(Integer.parseInt(CommonFunction.checkNull(vo.getLbxLoanNoHID()).trim()));
					  	in.add((CommonFunction.checkNull(vo.gethBPType()).trim()));
					  	in.add(Integer.parseInt(CommonFunction.checkNull(Integer.parseInt(vo.getLbxBusinessPartnearHID())).trim()));
					    logger.info("Value Date :::::::::::::::::::::::: "+CommonFunction.checkNull(vo.getValueDate()).trim());
					  	
					  	date.append(CommonFunction.changeFormat(CommonFunction.checkNull(vo.getValueDate()).trim()));
		        	    if(date.toString() != null)
		        	    	in.add(date.toString());
//				        cst.setString(4, (CommonFunction.checkNull(vo.getMakerDate()).trim()));
		        	    in.add(myFormatter.parse(CommonFunction.checkNull(vo.getTotalReveivable()).trim()).doubleValue());
		        	    in.add(myFormatter.parse(CommonFunction.checkNull(vo.getTotalPayable()).trim()).doubleValue());
		        	    in.add((CommonFunction.checkNull(recStatus).trim()));
		        	    in.add((CommonFunction.checkNull(vo.getRemarks()).trim()));
		        	    in.add((CommonFunction.checkNull(vo.getUserId()).trim()));
				        date1.append(CommonFunction.changeFormat(CommonFunction.checkNull(vo.getMakerDate()).trim()));
		        	    if(date1.toString() != null)
		        	    	in.add(date1.toString());
//				        cst.setString(10, (CommonFunction.checkNull(vo.getMakerDate()).trim()));
				        out.add(s1);
		        	    out.add(s2);
		        	    out.add(s3);
		        	    outMessages=(ArrayList) ConnectionDAO.callSP("Credit_Knockoff_dtl",in,out);
		        	    
		        	    s1=Integer.parseInt(CommonFunction.checkNull(outMessages.get(0)));
		        	    s2.append(CommonFunction.checkNull(outMessages.get(1)));
		        	    s3.append(CommonFunction.checkNull(outMessages.get(2)));
		        	    
			            logger.info("s1: "+s1);
			            logger.info("s2: "+s2);
			            logger.info("s3: "+s3);
			            knockOffId = Integer.toString(s1);
//		            int s3 = cst.getInt(13);

		            logger.info("s1: "+s1);
		            logger.info("s2: "+s2);
		            logger.info("s3: "+s3);
		    
		            if((s2.toString()).equalsIgnoreCase("S"))
		            {
		            	knockOffMStatus=true;
		            	
		            }
		           
		            logger.info("After proc call....");
				}
				catch (Exception e) {
					e.printStackTrace();
				
				}
				
			
			}
			else if(!CommonFunction.checkNull(vo.getKnockOffId()).equalsIgnoreCase(""))
			{
				qryList = new ArrayList();
				try
				{
					knockOffId=CommonFunction.checkNull(vo.getKnockOffId());
					queryUpdate = new StringBuilder();
					queryUpdate.append("update cr_knockoff_m set knockoff_date=");
					queryUpdate.append(dbo);
					queryUpdate.append("STR_TO_DATE(?,'"+dateFormatWithTime+"'), knockoff_amount_receivable=?, knockoff_amount_payable=?, rec_status=?," +
							" MAKER_REMARKS=?, maker_id=?, maker_date=");
					queryUpdate.append(dbo);
					queryUpdate.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') where loan_id=? and knockoff_id=?");
					insertPrepStmtObject = new PrepStmtObject();
					
					if((CommonFunction.checkNull(vo.getValueDate())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getValueDate()).trim());
					
					if((CommonFunction.checkNull(vo.getTotalReveivable())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getTotalReveivable()).trim())).toString());
					
					if((CommonFunction.checkNull(vo.getTotalPayable())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getTotalPayable()).trim())).toString());
					
					if((CommonFunction.checkNull(recStatus)).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((recStatus).trim());
					
					if((CommonFunction.checkNull(vo.getRemarks())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getRemarks()).trim()));
					
					if((CommonFunction.checkNull(vo.getUserId())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getUserId()).trim()));
					
					if((CommonFunction.checkNull(vo.getMakerDate())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerDate()).trim()));
					
					if((CommonFunction.checkNull(loanIdNo)).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((CommonFunction.checkNull(loanIdNo).trim()));
					
					if((CommonFunction.checkNull(vo.getKnockOffId())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getKnockOffId()).trim()));
					
					insertPrepStmtObject.setSql(queryUpdate.toString());
					logger.info("IN saveKnockOffDetails() update query1 when knockoff id is not null### "+insertPrepStmtObject.printQuery());
					qryList.add(insertPrepStmtObject);
					logger.info("In saveKnockOffDetails ........ update query: "+queryUpdate);
					
					knockOffMStatus =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
					logger.info("In saveKnockOffDetails.........update status: "+status);
					queryUpdate = null;
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			//****************   knock off detail part**************************// 
			logger.info("RknockOffAmount.length before For loop for receivable part;--------------------"+RknockOffAmount.length);
			try 
			   {
				if(knockOffId.equalsIgnoreCase("")){
					knockOffId=CommonFunction.checkNull(vo.getKnockOffId());
				}
				 for(int i=0;i<RknockOffAmount.length;i++)  // For loop for receivable part
				 {
					 StringBuilder query1 = new StringBuilder();
					 query1.append("select txn_advice_id from cr_knockoff_dtl where txn_advice_id='"+txnAdviceIdR[i]+"' and seq_no="+(i+1)+" and knockoff_id='"+knockOffId+"'");
					 boolean qry1Status = ConnectionDAO.checkStatus(query1.toString());
					 logger.info("qry1Status:0----------"+qry1Status);
					 qryList = new ArrayList();
					 query1 = null;
					 if(!qry1Status)
					 {
						 bufInsSql = new StringBuilder();
						 insertPrepStmtObject = new PrepStmtObject();
							try
							{
								bufInsSql.append("insert into cr_knockoff_dtl(seq_no,knockoff_id, txn_advice_id, advice_type, advice_amount, balance_amount,");
								bufInsSql.append(" knockoff_amount, amount_in_process, rec_status, maker_id, maker_date, author_id, author_date)");
								bufInsSql.append(" values ( ");
								bufInsSql.append(" ?,"); // seq_no
								bufInsSql.append(" ?,"); // knockoff_id
								bufInsSql.append(" ?,"); // txn_advice_id
								bufInsSql.append(" ?,"); // advice_type
								bufInsSql.append(" ?,"); // advice_amount
								bufInsSql.append(" ?,"); // balance_amount
								bufInsSql.append(" ?,"); // knockoff_amount
								bufInsSql.append(" ?,"); // amount_in_process
								bufInsSql.append(" ?,"); // rec_status
								bufInsSql.append(" ?,"); // maker_id
								bufInsSql.append(dbo); 
								bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"'),"); // maker_date
								bufInsSql.append(" ?,"); // author_id
								bufInsSql.append(dbo);
								bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"'))"); // author_date
								
								insertPrepStmtObject.addInt(i+1);
								
								if (CommonFunction.checkNull(knockOffId).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString((CommonFunction.checkNull(knockOffId).trim()));
								
								if (CommonFunction.checkNull(txnAdviceIdR[i]).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString((CommonFunction.checkNull(txnAdviceIdR[i]).trim()));
								
								insertPrepStmtObject.addString((CommonFunction.checkNull("R").trim()));
								
								if (CommonFunction.checkNull(ROriginalAmount[i]).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(ROriginalAmount[i]).trim())).toString());
								
								if (CommonFunction.checkNull(RBalancelAmount[i]).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(RBalancelAmount[i]).trim())).toString());
								
								if (CommonFunction.checkNull(RknockOffAmount[i]).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(RknockOffAmount[i]).trim())).toString());
								
								if (CommonFunction.checkNull(ProcessAmountR[i]).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(ProcessAmountR[i]).trim())).toString());
								
								if (CommonFunction.checkNull(recStatus).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString((CommonFunction.checkNull(recStatus).trim()));
								
								if (CommonFunction.checkNull(vo.getUserId()).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getUserId()).trim()));
								
								if (CommonFunction.checkNull(vo.getMakerDate()).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerDate()).trim()));
								
								if (CommonFunction.checkNull(vo.getAuthorId()).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getAuthorId()).trim()));
								
								if (CommonFunction.checkNull(vo.getAuthorDate()).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getAuthorDate()).trim()));
								
								insertPrepStmtObject.setSql(bufInsSql.toString());
								logger.info("IN saveKnockOffDetails() insert query for knockoff_dtl receivable when txnId is null### "+insertPrepStmtObject.printQuery());
								qryList.add(insertPrepStmtObject);
								knockOffDtlStatusR=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
							    logger.info("In saveKnockOffDetails...............knockOffDtlStatusR receivable: "+status);
							    bufInsSql = null;
							}   catch (Exception e) {
								
								e.printStackTrace();
							}
					 }
					 
					 else if(qry1Status)
					 {
						 if(knockOffId.equalsIgnoreCase(""))
						 {
							 knockOffId=vo.getKnockOffId();
						 }
						 updateQuery = new StringBuilder();
						 insertPrepStmtObject = new PrepStmtObject();
						 
						 updateQuery.append("update cr_knockoff_dtl set knockoff_id=?, knockoff_amount=?, rec_status=?, maker_id=?, maker_date=");
						 updateQuery.append(dbo);
						 updateQuery.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') where txn_advice_id=? and seq_no=? and knockoff_id='"+knockOffId+"'");
						 insertPrepStmtObject = new PrepStmtObject();
						 
						 if(CommonFunction.checkNull(knockOffId).trim().equalsIgnoreCase(""))
							 insertPrepStmtObject.addNull();
						 else
								insertPrepStmtObject.addString(CommonFunction.checkNull(knockOffId).trim());
						 
						 if((CommonFunction.checkNull(RknockOffAmount[i])).trim().equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(RknockOffAmount[i]).trim())).toString());
						 
						 if((CommonFunction.checkNull(recStatus)).trim().equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(recStatus).trim()));
						 
						 if((CommonFunction.checkNull(vo.getUserId())).trim().equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getUserId()).trim()));
						 
						 if((CommonFunction.checkNull(vo.getMakerDate())).trim().equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerDate()).trim()));
						 
						 if((CommonFunction.checkNull(txnAdviceIdR[i])).trim().equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(txnAdviceIdR[i]).trim()));
						 
						 insertPrepStmtObject.addInt(i+1);
					
						insertPrepStmtObject.setSql(updateQuery.toString());
						logger.info("IN saveKnockOffDetails() update query1 for knockoff_dtl receivable when txnId is not null### "+insertPrepStmtObject.printQuery());
						qryList.add(insertPrepStmtObject);
						logger.info("In saveKnockOffDetails ........ update query: "+updateQuery);
						updateQuery = null;
						try
						{
							knockOffDtlStatusR =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
							logger.info("In saveCPLeadEntry.........update knockOffDtlStatusR receivable: "+status);
						}
						catch(Exception e){
							e.printStackTrace();
						}
					 }
					 
					// **************************** txn advice part ***********************//
					   
					   if(CommonFunction.checkNull(recStatus).equalsIgnoreCase("F"))
					   {
						   StringBuilder updateTxn = new StringBuilder();
						   updateTxn.append("update cr_txnadvice_dtl set amount_in_process=amount_in_process + ? where txnadvice_id=?");
						   insPrepStmtObject = new PrepStmtObject();
						   
						   if((CommonFunction.checkNull(RknockOffAmount[i])).trim().equalsIgnoreCase(""))
							   insPrepStmtObject.addNull();
							else
								insPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(RknockOffAmount[i]).trim())).toString());
						   
						   if((CommonFunction.checkNull(txnAdviceIdR[i])).trim().equalsIgnoreCase(""))
							   insPrepStmtObject.addNull();
							else
								insPrepStmtObject.addString((CommonFunction.checkNull(txnAdviceIdR[i]).trim()));
						   insPrepStmtObject.setSql(updateTxn.toString());
						   logger.info("IN saveKnockOffDetails() update query1 in txnAdvice_dtl receivable### "+insPrepStmtObject.printQuery());
						   qryList.add(insPrepStmtObject);
						   logger.info("In saveKnockOffDetails ........ update query: "+updateTxn);
						   updateTxn = null;
						   try
							{
							   txnstatus =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
							   logger.info("In saveCPLeadEntry.........update status: "+status);
							}
						   catch(Exception e){
								e.printStackTrace();
							}
					   }
				 }
				 logger.info("Length of Payable Array: "+PknockOffAmount.length);
				 for(int i=0;i<PknockOffAmount.length;i++) //  For Loop For PAYABLE part
				 {
					 if(knockOffId.equalsIgnoreCase("")){
							knockOffId=CommonFunction.checkNull(vo.getKnockOffId());
						}
					 int seqNo = RknockOffAmount.length+i;
					 logger.info(" PknockOffAmount seqNo:----------"+seqNo);
					 StringBuilder query2 = new StringBuilder();
					 query2.append("select txn_advice_id from cr_knockoff_dtl where txn_advice_id='"+txnAdviceIdP[i]+"' and seq_no="+(seqNo+1)+" and knockoff_id='"+knockOffId+"'");
					 boolean qry1Status = ConnectionDAO.checkStatus(query2.toString());
					// String qry1StatusSingalReturn = ConnectionDAO.singleReturn(query2);
					// logger.info(" PknockOffAmount qry1Status query2:---"+query2);
					// logger.info(" PknockOffAmount qry1Status:---"+qry1Status);
					// logger.info(" PknockOffAmount qry1StatusSingalReturn query2:---"+qry1StatusSingalReturn);
					 qryList = new ArrayList();
					 query2 = null;
					 if(!qry1Status)
					 {
						 bufInsSql = new StringBuilder();
							insertPrepStmtObject = new PrepStmtObject();
							try
							{
								bufInsSql.append("insert into cr_knockoff_dtl(seq_no,knockoff_id, txn_advice_id, advice_type, advice_amount, balance_amount,");
								bufInsSql.append(" knockoff_amount, amount_in_process, rec_status, maker_id, maker_date, author_id, author_date)");
								bufInsSql.append(" values ( ");
								bufInsSql.append(" ?,"); // seq_no
								bufInsSql.append(" ?,"); // knockoff_id
								bufInsSql.append(" ?,"); // txn_advice_id
								bufInsSql.append(" ?,"); // advice_type
								bufInsSql.append(" ?,"); // advice_amount
								bufInsSql.append(" ?,"); // balance_amount
								bufInsSql.append(" ?,"); // knockoff_amount
								bufInsSql.append(" ?,"); // amount_in_process
								bufInsSql.append(" ?,"); // rec_status
								bufInsSql.append(" ?,"); // maker_id
								bufInsSql.append(dbo);
								bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"'),"); // maker_date
								bufInsSql.append(" ?,"); // author_id
								bufInsSql.append(dbo);
								bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"'))"); // author_date
								
								insertPrepStmtObject.addInt(seqNo+1);
								
								if (CommonFunction.checkNull(knockOffId).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString((CommonFunction.checkNull(knockOffId).trim()));
								
								if (CommonFunction.checkNull(txnAdviceIdP[i]).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString((CommonFunction.checkNull(txnAdviceIdP[i]).trim()));
								
								insertPrepStmtObject.addString((CommonFunction.checkNull("P").trim()));
								
								if (CommonFunction.checkNull(POriginalAmount[i]).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(POriginalAmount[i]).trim())).toString());
								
								if (CommonFunction.checkNull(PBalancelAmount[i]).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(PBalancelAmount[i]).trim())).toString());
								
								if (CommonFunction.checkNull(PknockOffAmount[i]).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(PknockOffAmount[i]).trim())).toString());
								
								if (CommonFunction.checkNull(ProcessAmountP[i]).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(ProcessAmountP[i]).trim())).toString());
								
								if (CommonFunction.checkNull(recStatus).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString((CommonFunction.checkNull(recStatus).trim()));
								
								if (CommonFunction.checkNull(vo.getUserId()).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getUserId()).trim()));
								
								if (CommonFunction.checkNull(vo.getMakerDate()).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerDate()).trim()));
								
								if (CommonFunction.checkNull(vo.getAuthorId()).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getAuthorId()).trim()));
								
								if (CommonFunction.checkNull(vo.getAuthorDate()).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getAuthorDate()).trim()));
								
								insertPrepStmtObject.setSql(bufInsSql.toString());
								logger.info("IN saveKnockOffDetails() insert query for knockoff_dtl payable when txnId is null### "+insertPrepStmtObject.printQuery());
								qryList.add(insertPrepStmtObject);
								knockOffDtlStatusP=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
							    logger.info("In saveKnockOffDetails...............knockOffDtlStatusP Payable: "+status);
							}   catch (Exception e) {
								
								e.printStackTrace();
							}
					 }
					 
					 else if(qry1Status)
					 {
						 updateQuery=new StringBuilder();
						 updateQuery.append("update cr_knockoff_dtl set knockoff_id=?, knockoff_amount=?, rec_status=?, maker_id=?, maker_date=");
						 updateQuery.append(dbo);
						 updateQuery.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') where txn_advice_id=? and seq_no=? and knockoff_id='"+knockOffId+"'");
						 insertPrepStmtObject = new PrepStmtObject();
						 
						 if (CommonFunction.checkNull(knockOffId).trim().equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString((CommonFunction.checkNull(knockOffId).trim()));
						 
						 if((CommonFunction.checkNull(PknockOffAmount[i])).trim().equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(PknockOffAmount[i]).trim())).toString());
						 
						 if((CommonFunction.checkNull(recStatus)).trim().equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(recStatus).trim()));
						 
						 if((CommonFunction.checkNull(vo.getUserId())).trim().equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getUserId()).trim()));
						 
						 if((CommonFunction.checkNull(vo.getMakerDate())).trim().equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerDate()).trim()));
						 
						 if((CommonFunction.checkNull(txnAdviceIdP[i])).trim().equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(txnAdviceIdP[i]).trim()));
						 
						 insertPrepStmtObject.addInt(seqNo+1);
						 
						insertPrepStmtObject.setSql(updateQuery.toString());
						logger.info("IN saveKnockOffDetails() update query1 knockoff_dtl payable when txnId is not null### "+insertPrepStmtObject.printQuery());
						qryList.add(insertPrepStmtObject);
						logger.info("In saveKnockOffDetails ........ update query: "+updateQuery);
						try
						{
							knockOffDtlStatusP =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
							logger.info("In saveCPLeadEntry.........update knockOffDtlStatusP Payable: "+status);
						}
						catch(Exception e){
							e.printStackTrace();
						}
					 }
					 
					// **************************** txn advice part ***********************//
					   
					 if(CommonFunction.checkNull(recStatus).equalsIgnoreCase("F"))
					   {
						 StringBuilder updateTxn = new StringBuilder();  
						 updateTxn.append("update cr_txnadvice_dtl set amount_in_process=amount_in_process + ? where txnadvice_id=?");
						   insPrepStmtObject = new PrepStmtObject();
						   
						   if((CommonFunction.checkNull(PknockOffAmount[i])).trim().equalsIgnoreCase(""))
							   insPrepStmtObject.addNull();
							else
								insPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(PknockOffAmount[i]).trim())).toString());
						   
						   if((CommonFunction.checkNull(txnAdviceIdP[i])).trim().equalsIgnoreCase(""))
							   insPrepStmtObject.addNull();
							else
								insPrepStmtObject.addString((CommonFunction.checkNull(txnAdviceIdP[i]).trim()));
						   insPrepStmtObject.setSql(updateTxn.toString());
						   logger.info("IN saveKnockOffDetails() update query1 txnadvice_dtl payable### "+insPrepStmtObject.printQuery());
						   qryList.add(insPrepStmtObject);
						   logger.info("In saveKnockOffDetails ........ update query: "+updateTxn);
						   updateTxn = null;
						   try
							{
								txnstatus =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
								logger.info("In saveKnockOffDetails.........update status: "+status);
							}
						   catch(Exception e){
								e.printStackTrace();
							}
					   }
				 }
			   }   catch (Exception e) {
				   e.printStackTrace();
				   }
			   finally
			   {
				    vo =null;
					//cst=null;
					bufInsSql = null;
					insertPrepStmtObject = null;
					queryUpdate = null;
					//con=null;
					qryList = null;
					updateQuery = null;
					insPrepStmtObject = null;
					s1 = 0;
					s2 = null;
					date = null;
					date1 = null;
			  }
			if(knockOffMStatus && knockOffDtlStatusP && knockOffDtlStatusR)
				status=true;
			return knockOffId;
		}



	public String saveKnockOffAuthor(KnockOffAuthorVO vo)
	    {
	    	logger.info("Inside saveKnockOffAuthor .................. DAOImpl");
	    	String status="";
	    //	CallableStatement cst=null;
	      //  Connection con=ConnectionDAO.getConnection();
	        ArrayList qryList = new ArrayList();
	        StringBuilder s1 = new StringBuilder();
	        StringBuilder s2 = new StringBuilder();
	        StringBuilder date = new StringBuilder();
//	        String s1= "";
//            String s2 = "";
            ArrayList<Object> in =new ArrayList<Object>();
			ArrayList<Object> out =new ArrayList<Object>();
			ArrayList outMessages = new ArrayList();
	        logger.info("KnockOff Id: "+vo.getKnockOffId());
	    	try
			{

//	    		con.setAutoCommit(false);
//		        cst=con.prepareCall("call Knock_Off_Author(?,?,STR_TO_DATE(?,'"+dateFormat+"'),?,?,?,?,?)");
		        in.add(Integer.parseInt(CommonFunction.checkNull(vo.getCompanyId()).trim()));
		        in.add(Integer.parseInt(CommonFunction.checkNull(vo.getKnockOffId()).trim()));
		        date.append(CommonFunction.changeFormat(CommonFunction.checkNull(vo.getAuthorDate()).trim()));
         	    if(date.toString() != null)
         	    	in.add(date.toString());
//		        in.add((CommonFunction.checkNull(vo.getAuthorDate()).trim()));
		        in.add((CommonFunction.checkNull(vo.getAuthorId()).trim()));
		        in.add((CommonFunction.checkNull(vo.getDecision()).trim()));
		        in.add((CommonFunction.checkNull(vo.getComments()).trim()));
		        out.add(s1.toString());
        	    out.add(s2.toString());
        	    outMessages=(ArrayList) ConnectionDAO.callSP("Knock_Off_Author",in,out);
        	    s1.append(CommonFunction.checkNull(outMessages.get(0)));
        	    s2.append(CommonFunction.checkNull(outMessages.get(1)));

	            logger.info("s1: "+s1);
	            logger.info("s2: "+s2);
	            if((s1.toString()).equalsIgnoreCase("S"))
	            {
					//con.commit();
	            	status=(s1.toString());
	            	//con.close();
	            }
	            else if((s1.toString()).equalsIgnoreCase("E"))
	            {
	            	//con.rollback();
	            	status = (s2.toString());
	            	//con.close();
	            }
			}
			catch (Exception e) {
				
				e.printStackTrace();
			}
			finally
			{
				
		        qryList = null;
		        s1= null;
	            s2 = null;
	            date = null;
			}
		return status;
	}
	public ArrayList<Object> getKnockOffDetailsDataNew(String loanNumber,String bpId, String adviceType,String bDate){
		
		logger.info("Inside getKnockOffDetailsDataNew..................DAOImpl");
		ArrayList<Object> loandetailList = new ArrayList<Object>();
		
		KnockOffMakerVO vo = null;
		StringBuilder query= new StringBuilder();
		ArrayList bdetails = null;
		ArrayList bdetails1= null;
		try
	       {
			query.append("Select l.LOAN_ID,");
			query.append(dbo);
			query.append("DATE_FORMAT(c.ADVICE_DATE,'"+dateFormat+"'),d.CHARGE_DESC,c.TXNADVICE_ID,c.ADVICE_AMOUNT,");
			query.append("ISNULL((c.ADVICE_AMOUNT-c.TXN_ADJUSTED_AMOUNT-c.AMOUNT_IN_PROCESS),0) as BALANCE_AMOUNT,c.AMOUNT_IN_PROCESS"
				+" from cr_txnadvice_dtl c left join cr_loan_dtl l on l.LOAN_ID=c.LOAN_ID left join com_charge_code_m d on c.CHARGE_CODE_ID=d.CHARGE_CODE "
				+" where c.ADVICE_TYPE='"+adviceType+"' and l.LOAN_ID='"+loanNumber+"' and c.bp_id='"+bpId+"' and c.REC_STATUS='A' and ");
			query.append("ISNULL((c.ADVICE_AMOUNT-c.TXN_ADJUSTED_AMOUNT-c.AMOUNT_IN_PROCESS),0)>0 AND c.ADVICE_DATE<=");
			query.append(dbo);
			query.append("STR_TO_DATE('"+bDate+"', '"+dateFormat+"') order by c.ADVICE_DATE asc");
			logger.info("query"+query.toString());	               
            bdetails = ConnectionDAO.sqlSelect(query.toString());
            int size = bdetails.size();
	    	logger.info("getReceivable Advice Data: "+size);	
	    	if(adviceType.equalsIgnoreCase("R"))
	    	{
		    	for(int i=0;i<size;i++)
		    	  {
		    	  logger.info("getPayable Advice Data: " +bdetails.get(i).toString());
		    	  bdetails1=(ArrayList)bdetails.get(i);
		    	    	if(bdetails1.size()>0)
		    			{
		    	    	   vo =  new KnockOffMakerVO();	
		    	    	   vo.setLoanId((CommonFunction.checkNull(bdetails1.get(0)).trim()));
		    	    		vo.setLoanNumber((CommonFunction.checkNull(bdetails1.get(1)).trim()));	    	    		
		    	    		vo.setCharges((CommonFunction.checkNull(bdetails1.get(2)).trim()));
		    	    		vo.setTxnAdviceIdR((CommonFunction.checkNull(bdetails1.get(3)).trim()));
		    	    		if((CommonFunction.checkNull(bdetails1.get(4)).trim()).equalsIgnoreCase(""))
							{
								vo.setOriginalAmountR("0");
							}
							else
							{
								Number originalAmount = myFormatter.parse((CommonFunction.checkNull(bdetails1.get(4)).trim()));
								vo.setOriginalAmountR(myFormatter.format(originalAmount));
							}
		    	    		
		    	    		if((CommonFunction.checkNull(bdetails1.get(5)).trim()).equalsIgnoreCase(""))
							{
								vo.setBalanceAmountR("0");
							}
							else
							{
								Number balanceAmount = myFormatter.parse((CommonFunction.checkNull(bdetails1.get(5)).trim()));
								vo.setBalanceAmountR(myFormatter.format(balanceAmount));
							}
		    	    	    
		    	    		if((CommonFunction.checkNull(bdetails1.get(6)).trim()).equalsIgnoreCase(""))
							{
								vo.setAmountInProcessR("0");
							}
							else
							{
								Number amountInProcess = myFormatter.parse((CommonFunction.checkNull(bdetails1.get(6)).trim()));
								vo.setAmountInProcessR(myFormatter.format(amountInProcess));
							}
		    	    	    loandetailList.add(vo);
		    			}
		    		}
	    	}
	    	
	    	if(adviceType.equalsIgnoreCase("P"))
	    	{
		    	for(int i=0;i<size;i++)
		    	  {
		    	  logger.info("getPayable Advice Data: " +bdetails.get(i).toString());
		    	  bdetails1=(ArrayList)bdetails.get(i);
		    	    	if(bdetails1.size()>0)
		    			{
		    	    	   vo =  new KnockOffMakerVO();	
		    	    	   vo.setLoanId((CommonFunction.checkNull(bdetails1.get(0)).trim()));
		    	    		vo.setLoanNumber((CommonFunction.checkNull(bdetails1.get(1)).trim()));	    	    		
		    	    		vo.setCharges((CommonFunction.checkNull(bdetails1.get(2)).trim()));
		    	    		vo.setTxnAdviceIdP((CommonFunction.checkNull(bdetails1.get(3)).trim()));
		    	    		if((CommonFunction.checkNull(bdetails1.get(4)).trim()).equalsIgnoreCase(""))
							{
								vo.setOriginalAmountP("0");
							}
							else
							{
								Number originalAmount = myFormatter.parse((CommonFunction.checkNull(bdetails1.get(4)).trim()));
								vo.setOriginalAmountP(myFormatter.format(originalAmount));
							}
		    	    		
		    	    		if((CommonFunction.checkNull(bdetails1.get(5)).trim()).equalsIgnoreCase(""))
							{
								vo.setBalanceAmountP("0");
							}
							else
							{
								Number balanceAmount = myFormatter.parse((CommonFunction.checkNull(bdetails1.get(5)).trim()));
								vo.setBalanceAmountP(myFormatter.format(balanceAmount));
							}
		    	    	    
		    	    		if((CommonFunction.checkNull(bdetails1.get(6)).trim()).equalsIgnoreCase(""))
							{
								vo.setAmountInProcessP("0");
							}
							else
							{
								Number amountInProcess = myFormatter.parse((CommonFunction.checkNull(bdetails1.get(6)).trim()));
								vo.setAmountInProcessP(myFormatter.format(amountInProcess));
							}
		    	    	    
		    	    	    loandetailList.add(vo);
		    			}
		    		}
	    	}
	    	}catch(Exception e){
	    		e.printStackTrace();
	    		}
	    	finally
	    	{
	    		vo = null;
				query= null;
				bdetails = null;
				bdetails1= null;
	    	}
	    	logger.info("Value of List is in dao is="+loandetailList);
	    	return loandetailList;
	    	}
	
	//Ritu
	public boolean deleteKnockOFF(String id) {
		
		boolean status=false;
		boolean status1=false;
		ArrayList list=new ArrayList();
		ArrayList list1=new ArrayList();
		StringBuilder query=new StringBuilder();
		StringBuilder query1=new StringBuilder();
		try{
		query.append ("delete from cr_knockoff_dtl where KNOCKOFF_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(id).trim())+"'");
		list.add(query.toString());
		logger.info("deleteKnockOFF()     query------------------" + query.toString());
		status =ConnectionDAO.sqlInsUpdDelete(list);
		
		query1.append ("delete from cr_knockoff_m where KNOCKOFF_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(id).trim())+"' ");
		list1.add(query1.toString());
		logger.info("deleteKnockOFF()     query------------------" + query.toString());
		status1 =ConnectionDAO.sqlInsUpdDelete(list1);
		}
	  catch (Exception e) {
			e.printStackTrace();
		}
	  return status;

	}

	public boolean deleteKnockOffCancellation(String id) {
		
		boolean status=false;
		boolean status1=false;
		ArrayList list=new ArrayList();
		ArrayList list1=new ArrayList();
		StringBuilder query=new StringBuilder();
		StringBuilder query1=new StringBuilder();
		try{
			query.append ("update cr_knockoff_m set CANCELLATION_FLAG='N',CAN_MAKER_REMARKS='',CAN_AUTHOR_REMARKS=''  where KNOCKOFF_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(id).trim())+"'");
					
			
		list.add(query.toString());
		logger.info("deleteKnockOffCancellation()     query------------------" + query.toString());
		status =ConnectionDAO.sqlInsUpdDelete(list);
//		
//		query1.append ("delete from cr_knockoff_m where KNOCKOFF_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(id).trim())+"' ");
//		list1.add(query1.toString());
//		logger.info("deleteKnockOffCancellation()     query------------------" + query.toString());
//		status1 =ConnectionDAO.sqlInsUpdDelete(list1);
		}
	  catch (Exception e) {
			e.printStackTrace();
		}
	  return status;

	}



}
