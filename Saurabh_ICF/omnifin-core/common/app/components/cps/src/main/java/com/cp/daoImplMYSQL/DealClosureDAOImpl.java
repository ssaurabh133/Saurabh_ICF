package com.cp.daoImplMYSQL;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;
import com.cp.dao.DealClosureDAO;
import com.cp.vo.DealCancellationVo;

public class DealClosureDAOImpl implements DealClosureDAO 
{
	private static final Logger logger = Logger.getLogger(DealClosureDAOImpl.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	
	public ArrayList fetchDealCanData(String dealId) 
	{
		logger.info("in fetchDealCanData() of DealClosureDAOImpl.");
		ArrayList searchlist=new ArrayList();
		StringBuilder bufInsSql = new StringBuilder();
		ArrayList data= null;
		DealCancellationVo vo= null;		
		ArrayList<DealCancellationVo> detailList=new ArrayList();
	    try{
			bufInsSql.append("select DATE_FORMAT(a.DEAL_DATE,'%d-%m-%Y'),a.DEAL_APPLICATION_FORM_NO,c.PRODUCT_DESC, d.SCHEME_DESC, b.DEAL_ASSET_COST, b.DEAL_LOAN_AMOUNT, b.DEAL_MARGIN_RATE, b.DEAL_MARGIN_AMOUNT," +
					"case b.DEAL_RATE_TYPE when 'E' then 'EFFECTIVE RATE' when 'F' then 'Flat Rate' end as DEAL_RATE_TYPE, b.Deal_TENURE," +
					"case a.REC_STATUS when 'P' then 'Pending' when 'F' then 'Forwarded' end as REC_STATUS," +
					"case b.DEAL_REPAYMENT_FREQ when 'M' then 'MONTHLY' when 'Q' then 'QUARTERLY' when 'Y' then 'YEARLY' when 'B' then 'BIMONTHLY' when 'H' then 'HALFYEARLY' end as DEAL_REPAYMENT_FREQ, c.PRODUCT_ID,d.SCHEME_ID " +
					"from cr_deal_dtl a  join cr_deal_loan_dtl b on(a.DEAL_ID=b.DEAL_ID)left join cr_product_m c on(c.PRODUCT_ID=b.DEAL_PRODUCT)" +
					"left join cr_scheme_m d on(d.SCHEME_ID=b.DEAL_SCHEME)where a.REC_STATUS in('P','F') and a.deal_id="+dealId.trim());
			logger.info("in fetchDealCanData() of DealClosureDAOImpl Query..."+bufInsSql.toString());
			searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
			int size = searchlist.size();
            logger.info("size: "+size);
            for(int i=0;i<size;i++)
            {
               data=(ArrayList)searchlist.get(i);
               if(data.size()>0)
               {
            	 vo= new DealCancellationVo();
            	 vo.setDealDate((CommonFunction.checkNull(data.get(0)).trim()));
            	 vo.setAppFormNumber((CommonFunction.checkNull(data.get(1)).trim()));
            	 vo.setProduct((CommonFunction.checkNull(data.get(2)).trim()));
            	 vo.setScheme((CommonFunction.checkNull(data.get(3)).trim()));
            	 vo.setAssetCost((CommonFunction.checkNull(data.get(4)).trim()));
            	 vo.setLoanAmount((CommonFunction.checkNull(data.get(5)).trim()));
            	 vo.setMarginPercentage((CommonFunction.checkNull(data.get(6)).trim()));
            	 vo.setMarginAmount((CommonFunction.checkNull(data.get(7)).trim()));            	 
            	 vo.setRate((CommonFunction.checkNull(data.get(8)).trim()));
            	 vo.setTenure((CommonFunction.checkNull(data.get(9)).trim()));
            	 vo.setDealStatus((CommonFunction.checkNull(data.get(10)).trim()));
            	 vo.setFrequency((CommonFunction.checkNull(data.get(11)).trim())); 
            	 vo.setLbxProduct((CommonFunction.checkNull(data.get(12)).trim())); 
            	 vo.setLbxScheme((CommonFunction.checkNull(data.get(13)).trim())); 
            	 detailList.add(vo);
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
			vo= null;
		}
		return detailList;
	}
	
	public boolean insertDealCancellation(Object ob) {
		DealCancellationVo vo = (DealCancellationVo) ob;
		boolean status = false;
		logger.info("In insertDealCancellation.....................................DealClosureDAOImpl ");
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		logger.info("In get product id : " + vo.getProduct());
		try {

				logger.info("In insert query...");
				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql.append("insert into cr_deal_termination_dtl(DEAL_ID,CUSTOMER_NAME,DEAL_APPLICATION_FORM_NUMBER,DEAL_DATE,PRODUCT_ID,SCHEME_ID,MARGIN_PERCENTAGE,MARGIN_AMOUNT,ASSET_COST,LOAN_AMOUNT,TENURE,RATE,FREQUENCY,STATUS,REASON_FOR_CLOSURE,REC_STATUS,MAKER_ID,MAKER_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,"); //DEAL_ID
				bufInsSql.append(" ?,");//CUSTOMER_NAME                     
				bufInsSql.append(" ?,");//DEAL_APPLICATION_FORM_NUMBER           
				bufInsSql.append(" STR_TO_DATE(?,'" + dateFormat+ "'),");//DEAL_DATE                              
				bufInsSql.append(" ?,");//PRODUCT                               
				bufInsSql.append(" ?,");//SCHEME                                 
				bufInsSql.append(" ?,");//MARGIN_PERCENTAGE                      
				bufInsSql.append(" ?,");//MARGIN_AMOUNT                         
				bufInsSql.append(" ?,");//ASSET_COST                            
				bufInsSql.append(" ?,");//LOAN_AMOUNT                          
				bufInsSql.append(" ?,");//TENURE                                
				bufInsSql.append(" ?,");//RATE                                   
				bufInsSql.append(" ?,");//FREQUENCY                              
				bufInsSql.append(" ?,");//STATUS                                 
				bufInsSql.append(" ?,");//REASON_FOR_CLOSURE
				bufInsSql.append(" 'P',"); //REC_STATUS
				bufInsSql.append(" ?,"); //MAKER_ID
				bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND))"); //MAKER_DATE

				if((CommonFunction.checkNull(vo.getLbxDealNo())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getLbxDealNo()).trim());
				
				if((CommonFunction.checkNull(vo.getCustomerName())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getCustomerName()).trim());
				
				if((CommonFunction.checkNull(vo.getAppFormNumber())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getAppFormNumber()).trim());
				
				if((CommonFunction.checkNull(vo.getDealDate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getDealDate()).trim());
				
				if((CommonFunction.checkNull(vo.getLbxProduct())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getLbxProduct()).trim());
				
				if((CommonFunction.checkNull(vo.getLbxScheme())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getLbxScheme()).trim());
				
				if((CommonFunction.checkNull(vo.getMarginPercentage())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getMarginPercentage()).trim());
				
				if((CommonFunction.checkNull(vo.getMarginAmount())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getMarginAmount()).trim());
				
				if((CommonFunction.checkNull(vo.getAssetCost())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getAssetCost()).trim());
				
				if((CommonFunction.checkNull(vo.getLoanAmount())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getLoanAmount()).trim());
				
				if((CommonFunction.checkNull(vo.getTenure())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getTenure()).trim());
				if(vo.getRate().equalsIgnoreCase("EFFECTIVE RATE") || vo.getRate().equalsIgnoreCase("Flat Rate"))
				{
					if(vo.getRate().equalsIgnoreCase("EFFECTIVE RATE"))
						vo.setRate("E");
					if(vo.getRate().equalsIgnoreCase("Flat Rate"))
						vo.setRate("F");
					if((CommonFunction.checkNull(vo.getRate())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getRate()).trim());
				}
				if(vo.getFrequency().equalsIgnoreCase("MONTHLY") || vo.getFrequency().equalsIgnoreCase("QUARTERLY") || vo.getFrequency().equalsIgnoreCase("YEARLY") || vo.getFrequency().equalsIgnoreCase("BIMONTHLY") || vo.getFrequency().equalsIgnoreCase("HALFYEARLY"))
				{
					if(vo.getFrequency().equalsIgnoreCase("MONTHLY"))
						vo.setFrequency("M");
					if(vo.getFrequency().equalsIgnoreCase("QUARTERLY"))
						vo.setFrequency("Q");
					if(vo.getFrequency().equalsIgnoreCase("YEARLY"))
						vo.setFrequency("Y");
					if(vo.getFrequency().equalsIgnoreCase("BIMONTHLY"))
						vo.setFrequency("B");
					if(vo.getFrequency().equalsIgnoreCase("HALFYEARLY"))
						vo.setFrequency("H");
					if((CommonFunction.checkNull(vo.getFrequency())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getFrequency()).trim());
				}
				if(vo.getDealStatus().equalsIgnoreCase("Forwarded") || vo.getDealStatus().equalsIgnoreCase("Pending"))
				{
					if(vo.getDealStatus().equalsIgnoreCase("Forwarded"))
						vo.setDealStatus("F");
					if(vo.getDealStatus().equalsIgnoreCase("Pending"))
						vo.setDealStatus("P");
					if((CommonFunction.checkNull(vo.getDealStatus())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getDealStatus()).trim());
				}
				if((CommonFunction.checkNull(vo.getReasonForClosure())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getReasonForClosure()).trim());
				
				if((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getMakerId()).trim());
				
				if((CommonFunction.checkNull(vo.getMakerDate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getMakerDate()).trim());
						
			
				insertPrepStmtObject.setSql(bufInsSql.toString());
				
				logger.info("IN insertDealCancellation() insert query1 ### "+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In insertDealCancellation......................"+ status);

				bufInsSql=null;
	
		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}
	
	public ArrayList<DealCancellationVo> searchDealCancellation(DealCancellationVo vo,
			String recStatus, HttpServletRequest request) {
		
		ArrayList<DealCancellationVo> list = new ArrayList<DealCancellationVo>();
		DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
			logger.info("Date Format: "+dateFormat);
			ArrayList searchlist=null;

			int count=0;
			int startRecordIndex=0;
			int endRecordIndex = no;
			DealCancellationVo dealVo= (DealCancellationVo) vo;
			boolean appendSQL=false;
			try
			{
			StringBuffer bufInsSql=new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			
			String dealId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo())).trim());
			String productId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getProductId())).trim());
			String schemeId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getSchemeId())).trim());
			String custName = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName())).trim());
		
			logger.info("dealID:----"+dealId );
			logger.info("productId:----"+productId );
			logger.info("schemeId:----"+schemeId );
			logger.info("Customer Name :----"+custName );
		
				bufInsSql.append("select b.DEAL_NO, a.CUSTOMER_NAME, c.PRODUCT_DESC, d.SCHEME_DESC, a.LOAN_AMOUNT, DATE_FORMAT(a.DEAL_DATE,'%d-%m-%Y'), a.MAKER_ID, b.DEAL_ID  ");
				bufInsSql.append(" from cr_deal_termination_dtl a left join cr_deal_dtl b on(a.DEAL_ID=b.DEAL_ID) ");
				bufInsSql.append(" left join cr_product_m c on(a.PRODUCT_ID=c.PRODUCT_ID) left join cr_scheme_m d on(a.SCHEME_ID=d.SCHEME_ID) where a.REC_STATUS ='"+StringEscapeUtils.escapeSql(recStatus)+"' ");
				bufInsSqlTempCount.append(" select count(*) from cr_deal_termination_dtl a left join cr_deal_dtl b on(a.DEAL_ID=b.DEAL_ID)  left join cr_product_m c on(a.PRODUCT_ID=c.PRODUCT_ID)  ");
				bufInsSqlTempCount.append(" left join cr_scheme_m d on(a.SCHEME_ID=d.SCHEME_ID) where a.REC_STATUS ='"+StringEscapeUtils.escapeSql(recStatus)+"' ");
			
				if(recStatus.equalsIgnoreCase("F")){
					bufInsSql.append(" and a.MAKER_ID!='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()))+"' ");
					bufInsSqlTempCount.append(" and a.MAKER_ID!='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()))+"' ");
				}
				
				if (!dealId.equals("")) 
				{
					bufInsSql.append(" and a.DEAL_ID = '" + dealId + "' ");
					bufInsSqlTempCount.append(" and a.DEAL_ID = '" + dealId + "' ");
				}
				if (!custName.equals("")) {
					bufInsSql.append(" and a.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(custName).trim()+"%' ");
					bufInsSqlTempCount.append(" and a.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(custName).trim()+"%' ");
				}
				if (!productId.equals("")) {
					bufInsSql.append(" and a.PRODUCT_ID = '" + productId + "' ");
					bufInsSqlTempCount.append(" and a.PRODUCT_ID = '" + productId + "' ");
				}
				if (!schemeId.equals("")) {
					
					bufInsSql.append(" and a.SCHEME_ID = '" + schemeId + "' ");
					bufInsSqlTempCount.append(" and a.SCHEME_ID = '" + schemeId + "' ");
				}
				
			count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
			logger.info("bufInsSqlTempCount : "+bufInsSqlTempCount.toString());
			
			if(dealId.trim()==null || dealId.trim().equalsIgnoreCase("")  || dealVo.getCurrentPageLink()>1)
			{
			
				logger.info("current PAge Link no .................... "+dealVo.getCurrentPageLink());
			if(dealVo.getCurrentPageLink()>1)
			{
				startRecordIndex = (dealVo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			
			bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
			}
			logger.info("query : "+bufInsSql);
		     
			searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
			logger.info("searchlist" + searchlist.size());	
			
			bufInsSql=null;
			
			for(int i=0;i<searchlist.size();i++){
				ArrayList data=(ArrayList)searchlist.get(i);
				if(data!=null && data.size()>0)
				{
					dealVo = new DealCancellationVo();
					if(recStatus.equalsIgnoreCase("P"))	
						dealVo.setLbxDealNo("<a href=dealCancellationMaker.do?method=openModifyDeal&deaId="+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(CommonFunction.checkNull(data.get(7)))).trim()+ ">"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(0)))+"</a>");
					if(recStatus.equalsIgnoreCase("F"))
						dealVo.setLbxDealNo("<a href=dealCancellationMaker.do?method=openModifyDealForAuthor&deaId="+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(CommonFunction.checkNull(data.get(7)))).trim()+ ">"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(0)))+"</a>");
					dealVo.setCustomerName(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(1))).trim());
					dealVo.setProduct(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(2))).trim());
					dealVo.setScheme(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(3))).trim());
					//dealVo.setLoanAmount(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(4))).trim());
					if(!(CommonFunction.checkNull(data.get(4))).trim().equalsIgnoreCase(""))	
					{
						Number reconNum =myFormatter.parse((CommonFunction.checkNull(data.get(4))).trim());
						dealVo.setLoanAmount(myFormatter.format(reconNum));
					}
					dealVo.setDealDate(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(5))).trim());
					dealVo.setMakerId(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(6))).trim());
					dealVo.setTotalRecordSize(count);
					list.add(dealVo);
				}
			}
			logger.info("In getTotalRecordSize()...." + dealVo.getTotalRecordSize()+dealVo.getLoanAmount());
			
			dealId=null;
			
		}
			
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return list;
	}

	public ArrayList dealCancellationValues(String dealId,String Status) {
		ArrayList list=new ArrayList();
		logger.info("In dealCancellationValues..." + dealId + Status);
		DealCancellationVo vo= null;
			try
			{
				StringBuilder query=new StringBuilder();
				
				query.append("select b.DEAL_NO, a.CUSTOMER_NAME, DATE_FORMAT(a.DEAL_DATE,'%d-%m-%Y'), a.DEAL_APPLICATION_FORM_NUMBER, c.PRODUCT_DESC, d.SCHEME_DESC, a.ASSET_COST, a.LOAN_AMOUNT, " +
								" a.MARGIN_PERCENTAGE, a.MARGIN_AMOUNT, case a.RATE when 'E' then 'EFFECTIVE RATE' when 'F' then 'Flat Rate' end as RATE, a.TENURE, case a.STATUS when 'P' then 'Pending' when 'F' then 'Forwarded' end as STATUS, case a.FREQUENCY when 'M' then 'MONTHLY' when 'Q' then 'QUARTERLY' when 'Y' then 'YEARLY' when 'B' then 'BIMONTHLY' when 'H' then 'HALFYEARLY' end as FREQUENCY, a.REASON_FOR_CLOSURE, a.DEAL_ID from cr_deal_termination_dtl a " +
								" left join cr_deal_dtl b on(a.DEAL_ID=b.DEAL_ID)  left join cr_product_m c on(a.PRODUCT_ID=c.PRODUCT_ID) left join cr_scheme_m d on(a.SCHEME_ID=d.SCHEME_ID) " +
								" WHERE a.DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()+" AND a.REC_STATUS='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(Status)).trim()+"' ");
		
				logger.info("get dealCancellationValues Query: "+query);
				
				
				ArrayList list1 = ConnectionDAO.sqlSelect(query.toString());
				
				query=null;
				
				for(int i=0;i<list1.size();i++){
					//logger.info("list1"+list1.size());
					ArrayList header1=(ArrayList)list1.get(i);
					if(header1!=null && header1.size()>0)
					{
						vo = new DealCancellationVo();
						
						vo.setLbxDealNo((CommonFunction.checkNull(header1.get(0))).trim());
						vo.setCustomerName((CommonFunction.checkNull(header1.get(1))).trim());
						vo.setDealDate((CommonFunction.checkNull(header1.get(2))).trim());
						vo.setAppFormNumber((CommonFunction.checkNull(header1.get(3))).trim());
						vo.setLbxProduct((CommonFunction.checkNull(header1.get(4))).trim());
						vo.setLbxScheme((CommonFunction.checkNull(header1.get(5))).trim());
						vo.setAssetCost((CommonFunction.checkNull(header1.get(6))).trim());
						vo.setLoanAmount((CommonFunction.checkNull(header1.get(7))).trim());
						vo.setMarginPercentage((CommonFunction.checkNull(header1.get(8))).trim());
						vo.setMarginAmount((CommonFunction.checkNull(header1.get(9))).trim());
						vo.setRate((CommonFunction.checkNull(header1.get(10))).trim());
						vo.setTenure((CommonFunction.checkNull(header1.get(11))).trim());
						vo.setDealStatus((CommonFunction.checkNull(header1.get(12))).trim());
						vo.setFrequency((CommonFunction.checkNull(header1.get(13))).trim());
						vo.setReasonForClosure((CommonFunction.checkNull(header1.get(14))).trim());
						vo.setDealId((CommonFunction.checkNull(header1.get(15))).trim());
						list.add(vo);
					}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}

			return list;
		}

	public boolean modifySaveDeal(Object ob) {
		
		DealCancellationVo vo = (DealCancellationVo) ob;
		boolean status = false;
		
		logger.info("In modifySaveDeal.....................................Dao Impl ");
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		
		try {

				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql.append("UPDATE cr_deal_termination_dtl SET REASON_FOR_CLOSURE=?,MAKER_ID=?,MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) where DEAL_ID=? AND REC_STATUS='P'");

				
				if((CommonFunction.checkNull(vo.getReasonForClosure())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getReasonForClosure()).trim());
				
				if((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getMakerId()).trim());
				
				if((CommonFunction.checkNull(vo.getMakerDate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getMakerDate()).trim());
				
				if((CommonFunction.checkNull(vo.getDealId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getDealId()).trim());
								
				insertPrepStmtObject.setSql(bufInsSql.toString());
				
				logger.info("IN insert deal_Cancellation() insert query1 ### "+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In modifyDealCancellation......................"+ status);
			
				bufInsSql=null;
				
		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;

	}

	public boolean modifyDealForward(Object ob) {
		
		DealCancellationVo vo = (DealCancellationVo) ob;
		boolean status = false;
		
		logger.info("In modifyDealForward.....................................Dao Impl ");
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		
		try {

				logger.info("In insert deal_Cancellation");
				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql.append("UPDATE cr_deal_termination_dtl SET REASON_FOR_CLOSURE=?,REC_STATUS='F', MAKER_ID=?," +
								" MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) WHERE DEAL_ID=? AND REC_STATUS='P'");

				if((CommonFunction.checkNull(vo.getReasonForClosure())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getReasonForClosure()).trim());
				
				if((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getMakerId()).trim());
				
				if((CommonFunction.checkNull(vo.getMakerDate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getMakerDate()).trim());
				
				if((CommonFunction.checkNull(vo.getDealId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getDealId()).trim());
				
								
				insertPrepStmtObject.setSql(bufInsSql.toString());
				
				logger.info("IN modifyDealForward() insert query1 ### "+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In modifyDealForward......................"+ status);
			
				bufInsSql=null;
				
		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;

	}

	public String modifyDealForAuthor(Object ob,String dealID) {
		
		DealCancellationVo vo = (DealCancellationVo) ob;
		String status = "";
		logger.info("In modifyDealForAuthor.....................................Dao Impl ");
		
		String decision="";
		String s1= "";
		String s2 = "";
		String s3 = "";
		ArrayList<Object> in =new ArrayList<Object>();
		ArrayList<Object> out =new ArrayList<Object>();
		ArrayList outMessages = new ArrayList();
		decision=CommonFunction.checkNull(vo.getDecison());
		try {

				logger.info("In modifyLimitForAuthor......decision , dealID : " + decision + dealID);
				StringBuffer bufInsSql = new StringBuffer();
				
					logger.info("In PROCEDURE..#####.. Deal_Cancellation_Author..#####.....");
					try {
						  //in.add(vo.getDealId());
						  in.add(Integer.parseInt(CommonFunction.checkNull (dealID).trim()));
						  String date=CommonFunction.changeFormat(CommonFunction.checkNull (vo.getMakerDate()).trim());
						  if(date != null)
			        	    	in.add(date);
						  in.add(CommonFunction.checkNull (vo.getMakerId()).trim());
			        	  in.add(CommonFunction.checkNull (vo.getDecison()).trim());
			        	  in.add(CommonFunction.checkNull (vo.getTextarea()).trim());
			        	    
			        	    out.add(s1);
			        	    out.add(s2);
			        	    out.add(s3);
			        	    
						 outMessages=(ArrayList) ConnectionDAO.callSP("Deal_Cancellation_Author",in,out);
						    s1=CommonFunction.checkNull(outMessages.get(0));
			        	    s2=CommonFunction.checkNull(outMessages.get(1));
			        	    s3=CommonFunction.checkNull(outMessages.get(2));

						if(s2!=null && s2.equalsIgnoreCase("S"))
						{
							status=s2;	
						}
						else if(s2!=null && s2.equalsIgnoreCase("E"))
						{
							status=s3;	
						}
						else if(s2!=null && s2.equalsIgnoreCase("C"))
						{
							status=s1;	
						}
						
						logger.info("status-------------1: "+s1);
						logger.info("s2-----------------1: "+s2);
						logger.info("s3-----------------1: "+s3);
					} catch (Exception e) {
						e.printStackTrace();
					}	

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("In modifyDealForAuthor................. status:----"+status);
		
	
		return status;

	}
	
}
