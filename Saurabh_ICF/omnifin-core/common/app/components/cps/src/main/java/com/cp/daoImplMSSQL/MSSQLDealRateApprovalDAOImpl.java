package com.cp.daoImplMSSQL;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;
import com.cp.dao.DealRateApprovalDAO;
import com.cp.vo.CollateralVo;
import com.cp.vo.DealRateApprovalVO;

public class MSSQLDealRateApprovalDAOImpl implements DealRateApprovalDAO 
{
	private static final Logger logger = Logger.getLogger(MSSQLDealRateApprovalDAOImpl.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	String dbType=resource.getString("lbl.dbType");
	String dbo=resource.getString("lbl.dbPrefix");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00");
	
	public ArrayList fetchDealRateApprovalMakerDetail(DealRateApprovalVO vo) 
	{
		logger.info("In fetchDealRateApprovalMakerDetail() of MSSQLDealRateApprovalDAOImpl.");		
 		ArrayList list=new ArrayList();
 		try
 		{
 			ArrayList header=null;
 			int count=0;
 			int startRecordIndex=0;
 			int endRecordIndex = no;
 			DealRateApprovalVO fetchVo=null;
 			StringBuffer bufInsSql=new StringBuffer();
 			StringBuffer bufInsSqlTempCount = new StringBuffer(); 			
 			
 			bufInsSql.append("select d.DEAL_ID,d.DEAL_NO,DEAL_APPLICATION_FORM_NO,");
 			bufInsSql.append("dbo.DATE_FORMAT(DEAL_DATE,'"+dateFormat+"') AS DEAL_DATE,deal.CUSTOMER_NAME,p.PRODUCT_DESC,s.SCHEME_DESC,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=d.MAKER_ID) MAKER_ID ");
 			bufInsSql.append(" from cr_deal_dtl d"); 			
 			bufInsSql.append(" join cr_deal_loan_dtl n on d.DEAL_ID=n.DEAL_ID  ");
 			bufInsSql.append(" join cr_product_m p on n.DEAL_PRODUCT=p.PRODUCT_ID  ");
 			bufInsSql.append(" join cr_deal_customer_m deal on deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID ");
 			bufInsSql.append(" join cr_scheme_m s on n.DEAL_SCHEME=s.SCHEME_ID  "); 			
 			bufInsSql.append(" INNER join cr_deal_movement_dtl dm on dm.DEAL_ID=d.DEAL_ID AND DM.DEAL_RECEIVED IS NOT NULL AND DM.DEAL_FORWARDED IS NULL AND DM.DEAL_STAGE_ID='RAL' AND DM.REC_STATUS='A' ");
 			bufInsSql.append(" where d.rec_status='F' and d.deal_id not in(select deal_id from cr_rate_approval_dtl where rec_status='F' ) and d.DEAL_BRANCH='"+CommonFunction.checkNull(vo.getBranchId()).trim()+"' ");
 			
 			bufInsSqlTempCount.append(" select count(1)");
 			bufInsSqlTempCount.append(" from cr_deal_dtl d"); 			
 			bufInsSqlTempCount.append(" join cr_deal_loan_dtl n on d.DEAL_ID=n.DEAL_ID  ");
 			bufInsSqlTempCount.append(" join cr_product_m p on n.DEAL_PRODUCT=p.PRODUCT_ID  ");
 			bufInsSqlTempCount.append(" join cr_deal_customer_m deal on deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID ");
 			bufInsSqlTempCount.append(" join cr_scheme_m s on n.DEAL_SCHEME=s.SCHEME_ID   ");
 			bufInsSqlTempCount.append(" INNER join cr_deal_movement_dtl dm on dm.DEAL_ID=d.DEAL_ID AND DM.DEAL_RECEIVED IS NOT NULL AND DM.DEAL_FORWARDED IS NULL AND DM.DEAL_STAGE_ID='RAL' AND DM.REC_STATUS='A' ");
 			bufInsSqlTempCount.append(" where d.rec_status='F' and d.deal_id not in(select deal_id from cr_rate_approval_dtl where rec_status='F' ) and d.DEAL_BRANCH='"+CommonFunction.checkNull(vo.getBranchId()).trim()+"'");
 			
 			if(!CommonFunction.checkNull(vo.getDealId()).trim().equalsIgnoreCase(""))
 			{
 				bufInsSql.append(" and d.deal_id='"+CommonFunction.checkNull(vo.getDealId()).trim()+"'");
 				bufInsSqlTempCount.append(" and d.deal_id='"+CommonFunction.checkNull(vo.getDealId()).trim()+"'");
 			}
 			if(!CommonFunction.checkNull(vo.getCustomerName()).trim().equalsIgnoreCase(""))
 			{
 				bufInsSql.append(" and deal.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName()).trim()).trim()+"%' ");
 				bufInsSqlTempCount.append(" and deal.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName()).trim()).trim()+"%' ");
 			}
 						
 			logger.info("In fetchDealRateApprovalMakerDetail() of MSSQLDealRateApprovalDAOImpl Count Qery :  "+bufInsSqlTempCount.toString());
 			String queryCnt=ConnectionDAO.singleReturn(bufInsSqlTempCount.toString());
 			if(CommonFunction.checkNull(queryCnt).equalsIgnoreCase(""))
 				queryCnt="0";
 			count =Integer.parseInt(queryCnt);
 			
 		    logger.info("current PAge Link no .................... "+vo.getCurrentPageLink());
  			if(vo.getCurrentPageLink()>1)
  			{
  				startRecordIndex = (vo.getCurrentPageLink()-1)*no;
  				endRecordIndex = no;
  				 logger.info("startRecordIndex .................... "+startRecordIndex);
  				 logger.info("endRecordIndex .................... "+endRecordIndex);
  			}
			bufInsSql.append(" ORDER BY d.DEAL_ID OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
			logger.info("In fetchDealRateApprovalMakerDetail() of MSSQLDealRateApprovalDAOImpl Search Qery : " + bufInsSql.toString());
			header = ConnectionDAO.sqlSelect(bufInsSql.toString());
  		  	for(int i=0;i<header.size();i++)
  		  	{
  				ArrayList header1=(ArrayList)header.get(i);
  				if(header1!=null && header1.size()>0)
  				{
  					fetchVo = new DealRateApprovalVO();
  					fetchVo.setGridDealNo("<a href=rateApprovalMaker.do?method=fetchRecordDetail&dealId="+(CommonFunction.checkNull(CommonFunction.checkNull(header1.get(0)))).trim()+">"+(CommonFunction.checkNull(header1.get(1)))+"</a>");
  					fetchVo.setApplicationno((CommonFunction.checkNull(header1.get(2))).trim());
  					fetchVo.setApplicationdate((CommonFunction.checkNull(header1.get(3))).trim());
  					fetchVo.setGridCustName((CommonFunction.checkNull(header1.get(4))).trim());
   					fetchVo.setProduct((CommonFunction.checkNull(header1.get(5))).trim());
  					fetchVo.setScheme((CommonFunction.checkNull(header1.get(6))).trim());
  					fetchVo.setTotalRecordSize(count);
  					list.add(fetchVo);
  				}
  			}
    		  bufInsSql=null;
      		  bufInsSqlTempCount=null;
 		}
 		catch(Exception e)
 		{
 			e.printStackTrace();
 		}
 		return list;
 	
	}
	public ArrayList fetchRecordDetail(DealRateApprovalVO vo) 
	{
		logger.info("In fetchRecordDetail() of MSSQLDealRateApprovalDAOImpl.");		
 		ArrayList resultList=new ArrayList();
 		DealRateApprovalVO fetchVo=new DealRateApprovalVO();
 		StringBuffer bufInsSql=null;
		ArrayList header=null;
		// For customer Detail's
 		try
 		{
 			bufInsSql=new StringBuffer(); 
 			bufInsSql.append(" select concat(upper(c.customer_name),' ')as customer_name,");
 			bufInsSql.append(" concat(");
 			bufInsSql.append(" IIF(ADDRESS_LINE1 is null,IIF(ADDRESS_LINE2 is null,IIF(ADDRESS_LINE3 is null,' ',concat(upper(ADDRESS_LINE3),' ')), IIF(ADDRESS_LINE3 is null,concat(upper(ADDRESS_LINE2),' '),concat(concat(upper(ADDRESS_LINE2),','),concat(upper(ADDRESS_LINE3),' ')))), IIF(ADDRESS_LINE2 is null,IIF(ADDRESS_LINE3 is null,concat(upper(ADDRESS_LINE1),' '),concat(concat(upper(ADDRESS_LINE1),','), concat(upper(ADDRESS_LINE3),' '))),IIF(ADDRESS_LINE3 is null,concat(concat(upper(ADDRESS_LINE1),','),concat(upper(ADDRESS_LINE2),' ')), concat(concat(concat(upper(ADDRESS_LINE1),','),concat(upper(ADDRESS_LINE2),',')),concat(upper(ADDRESS_LINE3),' '))))),'\n',");
 			bufInsSql.append(" concat(IIF(DISTRICT_DESC is null,IIF(STATE_DESC is null,IIF(COUNTRY_DESC is null,' ',concat('(',upper(COUNTRY_DESC),') ')), IIF(COUNTRY_DESC is null,concat(upper(STATE_DESC),' '),concat(concat(upper(STATE_DESC),','),concat('(',upper(COUNTRY_DESC),') ')))), IIF(STATE_DESC is null,IIF(COUNTRY_DESC is null,concat(upper(DISTRICT_DESC),' '),concat(concat(upper(DISTRICT_DESC),','), concat('(',upper(COUNTRY_DESC),') '))),IIF(COUNTRY_DESC is null,concat(concat(upper(DISTRICT_DESC),','),concat(upper(STATE_DESC),' ')), concat(concat(concat(upper(DISTRICT_DESC),','),concat(upper(STATE_DESC),',')),concat('(',upper(COUNTRY_DESC),') '))))), IIF(PINCODE is null,' ',concat('- ',PINCODE))),'\n',");
 			bufInsSql.append(" IIF(PRIMARY_PHONE is null,IIF(ALTERNATE_PHONE is null,' ', concat('PHONE NO.-',concat(ALTERNATE_PHONE,' '))),IIF(ALTERNATE_PHONE is null,concat('PHONE NO.-',concat(PRIMARY_PHONE, ' ')),concat('PHONE NO.-',concat(concat(PRIMARY_PHONE,','),concat(ALTERNATE_PHONE,' ')))))) location,");
 			bufInsSql.append(" gm.DESCRIPTION CUST_CONSTITUTION,(SELECT DESCRIPTION from generic_master g where GENERIC_KEY='SECTOR_TYPE' and REC_STATUS='A' and VALUE=b.DEAL_SECTOR_TYPE)as PSL "); 
 			bufInsSql.append(" from  cr_deal_dtl a");
 			bufInsSql.append(" join cr_deal_loan_dtl b on(a.deal_id=b.deal_id)");
 			bufInsSql.append(" join cr_deal_customer_m c on(a.deal_customer_id=c.customer_id)"); 
 			bufInsSql.append(" left join CR_DEAL_ADDRESS_M  on((CR_DEAL_ADDRESS_M.BPID=c.CUSTOMER_ID) and(CR_DEAL_ADDRESS_M.COMMUNICATION_ADDRESS='Y'))");  
 			bufInsSql.append(" left join com_country_m on(com_country_m.COUNTRY_ID=CR_DEAL_ADDRESS_M.COUNTRY)");
 			bufInsSql.append(" left join com_state_m  on((com_state_m.COUNTRY_ID=CR_DEAL_ADDRESS_M.COUNTRY)and(com_state_m.STATE_ID=CR_DEAL_ADDRESS_M.STATE))");  
 			bufInsSql.append(" left join com_district_m on(com_district_m.STATE_ID=CR_DEAL_ADDRESS_M.STATE)and(com_district_m.DISTRICT_ID=CR_DEAL_ADDRESS_M.DISTRICT)");
 			bufInsSql.append(" left join GENERIC_MASTER gm on(gm.VALUE=c.CUSTOMER_CONSTITUTION and gm.rec_status='A')");
 			bufInsSql.append(" where a.deal_id='"+CommonFunction.checkNull(vo.getDealId()).trim()+"' ");
 			
 			logger.info("In fetchRecordDetail() of MSSQLDealRateApprovalDAOImpl Customer Detail Qery : " + bufInsSql.toString());
			header = ConnectionDAO.sqlSelect(bufInsSql.toString());
  		  	ArrayList header1=(ArrayList)header.get(0);
  		  	
  		    fetchVo.setCustomerName((CommonFunction.checkNull(header1.get(0))).trim());
  		    fetchVo.setLocation((CommonFunction.checkNull(header1.get(1))).trim());
  			fetchVo.setCustConstitution((CommonFunction.checkNull(header1.get(2))).trim());
  			fetchVo.setPsl((CommonFunction.checkNull(header1.get(3))).trim());
  			bufInsSql=null;
  			header=null;
  			header1=null;
  			
  		}
 		catch(Exception e)
 		{
 			e.printStackTrace();
 		}
 		// For Loan Detail's
 		try
 		{
 			bufInsSql=new StringBuffer(); 
 			bufInsSql.append(" select a.deal_no,a.DEAL_APPLICATION_FORM_NO,c.PRODUCT_DESC,d.SCHEME_DESC,b.deal_loan_amount,b.DEAL_EFF_RATE,b.DEAL_FLAT_RATE,");
 			bufInsSql.append(" case b.DEAL_RATE_METHOD when 'F' then 'Fixed' when 'L' then 'Floating' else 'NA' end as roiType,");
 			bufInsSql.append(" (((isnull(pro.DEAL_CHARGE_CALCULATED_AMOUNT,0)+isnull(refundable.DEAL_CHARGE_CALCULATED_AMOUNT,0))/b.deal_loan_amount)*100) processingFeePer,(isnull(pro.DEAL_CHARGE_CALCULATED_AMOUNT,0)+isnull(refundable.DEAL_CHARGE_CALCULATED_AMOUNT,0)) processingFee,");
 			bufInsSql.append(" (isnull(pro.DEAL_CHARGE_TAX_AMOUNT1,0)+isnull(refundable.DEAL_CHARGE_TAX_AMOUNT1,0)) taxOnProcFee,(isnull(pro.DEAL_CHARGE_NET_AMOUNT,0)+isnull(refundable.DEAL_CHARGE_NET_AMOUNT,0))totalProcFee,");
 			bufInsSql.append(" b.DEAL_TENURE,((isnull(seq.SD_AMOUNT,0)/b.deal_loan_amount)*100) MMD_ICD_AMT_PER,isnull(seq.SD_AMOUNT,0)MMD_ICD_AMT,");
 			bufInsSql.append(" isnull(seq.SD_INTEREST_RATE,0)INTEREST_ON_MMD_ICD,");
 			bufInsSql.append(" (isnull(b.deal_loan_amount,0)-isnull(seq.SD_AMOUNT,0))NET_LOAN_AMT,isnull(b.deal_irr1,0)net_acc_irr,isnull(b.deal_irr2,0)gross_acc_irr,A.DEAL_ID ");
 			bufInsSql.append(",(select DESCRIPTION from generic_master  where GENERIC_KEY='SOURCE_TYPE' and rec_status='A' and VALUE=a.DEAL_SOURCE_TYPE), a.DEAL_SOURCE_NAME, 0.00 as PAYOUT_APPLICABLE,0.00 AS PAYOUT_DIFF_FROM_ABOVE_CELL, ");
 			bufInsSql.append(" ((b.DEAL_LOAN_AMOUNT/b.DEAL_ASSET_COST)*100)LtvOnAsset,");
 		 	bufInsSql.append(" iif(LTVONASSET.ASSET_COLLATERAL_VALUE is null,0.00,((b.DEAL_LOAN_AMOUNT/LTVONASSET.ASSET_COLLATERAL_VALUE)*100))LtvOnCombined ,");
 		 	bufInsSql.append(" iif(b.DEAL_ASSET_COST is null,0.00,(((b.DEAL_LOAN_AMOUNT-isnull(seq.SD_AMOUNT,0))/b.DEAL_ASSET_COST)*100))NetLtvOnAsset ,");
 		 	bufInsSql.append(" iif(LTVONASSET.ASSET_COLLATERAL_VALUE is null,0.00,(((b.DEAL_LOAN_AMOUNT-isnull(seq.SD_AMOUNT,0))/LTVONASSET.ASSET_COLLATERAL_VALUE)*100))NetLtvOnCombined   ");
 			bufInsSql.append(" from cr_deal_dtl a");
 			bufInsSql.append(" join cr_deal_loan_dtl b on(a.deal_id=b.deal_id)");
 			bufInsSql.append(" join CR_PRODUCT_M c on(c.PRODUCT_ID=b.DEAL_PRODUCT)");
 			bufInsSql.append(" join CR_SCHEME_M d on(d.SCHEME_ID=b.DEAL_SCHEME)");
 			bufInsSql.append(" left join"); 
 			bufInsSql.append(" (");
 			bufInsSql.append(" 		select DEAL_ID,DEAL_CHARGE_CALCULATED_AMOUNT,DEAL_CHARGE_FINAL_AMOUNT,DEAL_CHARGE_TAX_AMOUNT1,DEAL_CHARGE_NET_AMOUNT "); 
 			bufInsSql.append(" 		from CR_DEAL_TXNCHARGES_DTL"); 
 			bufInsSql.append(" 		where DEAL_CHARGE_CODE=106 and DEAL_ID='"+CommonFunction.checkNull(vo.getDealId()).trim()+"' ");
 			bufInsSql.append(" )pro on(pro.deal_id=a.DEAL_ID)");
 			bufInsSql.append(" left join"); 
 			bufInsSql.append(" (");
 			bufInsSql.append(" 		select DEAL_ID,SD_AMOUNT,SD_INTEREST_RATE from CR_DEAL_SD_M");
 			bufInsSql.append(" 		where  DEAL_ID='"+CommonFunction.checkNull(vo.getDealId()).trim()+"' ");
 			bufInsSql.append(" )seq on(seq.DEAL_ID=a.DEAL_ID)");
 			bufInsSql.append(" left join"); 
 			bufInsSql.append(" (");
 			bufInsSql.append(" 		select DEAL_ID,DEAL_CHARGE_CALCULATED_AMOUNT,DEAL_CHARGE_FINAL_AMOUNT,DEAL_CHARGE_TAX_AMOUNT1,DEAL_CHARGE_NET_AMOUNT ");
 			bufInsSql.append(" 		from CR_DEAL_TXNCHARGES_DTL"); 
 			bufInsSql.append(" 		where DEAL_CHARGE_CODE=151 and DEAL_ID='"+CommonFunction.checkNull(vo.getDealId()).trim()+"' ");
 			bufInsSql.append(" )refundable on(refundable.deal_id=a.DEAL_ID)");
 			bufInsSql.append("left join ");
 			bufInsSql.append("  ( ");
 			bufInsSql.append("  select deal_id,sum(ASSET_COLLATERAL_VALUE) ASSET_COLLATERAL_VALUE ");
 			bufInsSql.append("  from CR_asset_COLLATERAL_M y ");
 			bufInsSql.append("	join CR_DEAL_COLLATERAL_M z on(y.ASSET_ID=z.ASSETID)  where z.deal_id='"+CommonFunction.checkNull(vo.getDealId()).trim()+"' and z.REC_STATUS='F' ");
 			bufInsSql.append("  group by  deal_id ");
 			bufInsSql.append("  )LTVONASSET on(LTVONASSET.DEAL_ID=a.DEAL_ID) "); 
 			bufInsSql.append(" where a.deal_id='"+CommonFunction.checkNull(vo.getDealId()).trim()+"' "); 
 			
 			logger.info("In fetchRecordDetail() of MSSQLDealRateApprovalDAOImpl Loan Detail Qery : " + bufInsSql.toString());
			header = ConnectionDAO.sqlSelect(bufInsSql.toString());
  		  	ArrayList header1=(ArrayList)header.get(0);
  		  	
  		  	fetchVo.setDealNo((CommonFunction.checkNull(header1.get(0))).trim());
		    fetchVo.setFormNo((CommonFunction.checkNull(header1.get(1))).trim());  		  
		    fetchVo.setProduct((CommonFunction.checkNull(header1.get(2))).trim());
		    fetchVo.setScheme((CommonFunction.checkNull(header1.get(3))).trim());
		    if(!CommonFunction.checkNull(header1.get(4)).equalsIgnoreCase(""))
    		{
    		Number reconNum=0.0000;
			reconNum = myFormatter.parse((CommonFunction.checkNull(header1.get(4))).trim());
			fetchVo.setLoanAmt(myFormatter.format(reconNum));
    		}
    		else
    		{
    			fetchVo.setLoanAmt("0.00");	
    		}
		    
		    if(!CommonFunction.checkNull(header1.get(5)).equalsIgnoreCase(""))
    		{
    		Number reconNum=0.0000000;
			reconNum = myFormatter.parse((CommonFunction.checkNull(header1.get(5))).trim());
    		fetchVo.setEffRate(myFormatter.format(reconNum));
    		}
	    	
    		else
    		{
    			fetchVo.setEffRate("0.00");	
    		}
	    	
    		if(!CommonFunction.checkNull(header1.get(6)).equalsIgnoreCase(""))
    		{
    		Number reconNum=0.0000000;
			reconNum = myFormatter.parse((CommonFunction.checkNull(header1.get(6))).trim());
    		fetchVo.setFlatRate(myFormatter.format(reconNum));
    		}
			
    		else
    		{
    			fetchVo.setFlatRate("0.00");	
    		}
    		
    		fetchVo.setRoiType((CommonFunction.checkNull(header1.get(7))).trim());
    		
    		if(!CommonFunction.checkNull(header1.get(8)).equalsIgnoreCase(""))
    		{
    		Number reconNum=0.0000;
			reconNum = myFormatter.parse((CommonFunction.checkNull(header1.get(8))).trim());
    		fetchVo.setProcessFeePer(myFormatter.format(reconNum));
    		}
			
    		else
    		{
    			fetchVo.setProcessFeePer("0.00");	
    		}
    		
    		if(!CommonFunction.checkNull(header1.get(9)).equalsIgnoreCase(""))
    		{
    		Number reconNum=0.0000;
			reconNum = myFormatter.parse((CommonFunction.checkNull(header1.get(9))).trim());
    		fetchVo.setProcessFeeAmt(myFormatter.format(reconNum));
    		}
    		else
    		{
    			fetchVo.setProcessFeeAmt("0.00");	
    		}
    		
    		if(!CommonFunction.checkNull(header1.get(10)).equalsIgnoreCase(""))
    		{
    		Number reconNum=0.0000;
			reconNum = myFormatter.parse((CommonFunction.checkNull(header1.get(10))).trim());
    		fetchVo.setTaxOnProcessFee(myFormatter.format(reconNum));
    		}
    		else
    		{
    			fetchVo.setTaxOnProcessFee("0.00");	
    		}
    		
    		if(!CommonFunction.checkNull(header1.get(11)).equalsIgnoreCase(""))
    		{
    		Number reconNum=0.0000;
			reconNum = myFormatter.parse((CommonFunction.checkNull(header1.get(11))).trim());
    		fetchVo.setTotalProcessFee(myFormatter.format(reconNum));
    		}
    		else
    		{
    			fetchVo.setTotalProcessFee("0.00");	
    		}
    		
    		if(!CommonFunction.checkNull(header1.get(12)).equalsIgnoreCase(""))
    		{
    		fetchVo.setDealTenure((CommonFunction.checkNull(header1.get(12)).trim()));

    		}
    		else
    		{
    			fetchVo.setDealTenure("0");	
    		}
    		
    		if(!CommonFunction.checkNull(header1.get(13)).equalsIgnoreCase(""))
    		{
    		Number reconNum=0.0000;
			reconNum = myFormatter.parse((CommonFunction.checkNull(header1.get(13))).trim());
    		fetchVo.setMmdIcdPer(myFormatter.format(reconNum));
    		}
    		else
    		{
    			fetchVo.setMmdIcdPer("0.00");	
    		}
    		
    		if(!CommonFunction.checkNull(header1.get(14)).equalsIgnoreCase(""))
    		{
    		Number reconNum=0.0000;
			reconNum = myFormatter.parse((CommonFunction.checkNull(header1.get(14))).trim());
    		fetchVo.setMmdIcdAmt(myFormatter.format(reconNum));
    		}
    		else
    		{
    			fetchVo.setMmdIcdAmt("0.00");	
    		}
    		if(!CommonFunction.checkNull(header1.get(15)).equalsIgnoreCase(""))
    		{
    		Number reconNum=0.0000000;
			reconNum = myFormatter.parse((CommonFunction.checkNull(header1.get(15))).trim());
    		fetchVo.setInterestOnMmdIcd(myFormatter.format(reconNum));
    		}
			
    		else
    		{
    			fetchVo.setInterestOnMmdIcd("0.0000000");	
    		}
			
    		if(!CommonFunction.checkNull(header1.get(16)).equalsIgnoreCase(""))
    		{
    		Number reconNum=0.0000;
			reconNum = myFormatter.parse((CommonFunction.checkNull(header1.get(16))).trim());
    		fetchVo.setNetLoanAmt(myFormatter.format(reconNum));
    		}
    		else
    		{
    			fetchVo.setNetLoanAmt("0.00");	
    		}
    		
    		if(!CommonFunction.checkNull(header1.get(17)).equalsIgnoreCase(""))
    		{
    		Number reconNum=0.0000000;
			reconNum = myFormatter.parse((CommonFunction.checkNull(header1.get(17))).trim());
    		fetchVo.setNetAccIRR(myFormatter.format(reconNum));
    		}
    		else
    		{
    			fetchVo.setNetAccIRR("0.00");	
    		}
    		
    		if(!CommonFunction.checkNull(header1.get(18)).equalsIgnoreCase(""))
    		{
    		Number reconNum=0.0000000;
			reconNum = myFormatter.parse((CommonFunction.checkNull(header1.get(18))).trim());
    		fetchVo.setGrossAccIRR(myFormatter.format(reconNum));
    		}
    		else
    		{
    			fetchVo.setGrossAccIRR("0.00");	
    		}   	    		
 
		    fetchVo.setDealId((CommonFunction.checkNull(header1.get(19))).trim());
		    fetchVo.setChannelType((CommonFunction.checkNull(header1.get(20))).trim());
		    fetchVo.setChannelName((CommonFunction.checkNull(header1.get(21))).trim());
		    
//		    if(!CommonFunction.checkNull(header1.get(22)).equalsIgnoreCase(""))
//    		{
//    		Number reconNum=0.0000;
//			reconNum = myFormatter.parse((CommonFunction.checkNull(header1.get(22))).trim());
//			fetchVo.setPayoutApplication(myFormatter.format(reconNum));
//    		}
//    		else
//    		{
//    			fetchVo.setPayoutApplication("0.0000");	
//    		}
//	    		
//	    		if(!CommonFunction.checkNull(header1.get(23)).equalsIgnoreCase(""))
//    		{
//    		Number reconNum=0.0000;
//			reconNum = myFormatter.parse((CommonFunction.checkNull(header1.get(23))).trim());
//			fetchVo.setPayoutBeing(myFormatter.format(reconNum));
//    		}
//    		else
//    		{
//    			fetchVo.setPayoutBeing("0.0000");	
//    		}
			if(!CommonFunction.checkNull(header1.get(24)).equalsIgnoreCase(""))	
			{
				Number reconNum =myFormatter.parse((CommonFunction.checkNull(header1.get(24))).trim());
				fetchVo.setLtvPercentageOnAsset(myFormatter.format(reconNum));
			}
			else
			{
				fetchVo.setLtvPercentageOnAsset("0.00");
			}
			if(!CommonFunction.checkNull(header1.get(25)).equalsIgnoreCase(""))	
			{
				Number reconNum =myFormatter.parse((CommonFunction.checkNull(header1.get(25))).trim());
				fetchVo.setLtvPercentageOnCombined(myFormatter.format(reconNum));
			}
			else
			{
				fetchVo.setLtvPercentageOnCombined("0.00");
			}
			if(!CommonFunction.checkNull(header1.get(26)).equalsIgnoreCase(""))	
			{
				Number reconNum =myFormatter.parse((CommonFunction.checkNull(header1.get(26))).trim());
				fetchVo.setNetLtvPercentageOnAsset(myFormatter.format(reconNum));
			}
			else
			{
				fetchVo.setNetLtvPercentageOnAsset("0.00");
			}
			if(!CommonFunction.checkNull(header1.get(27)).equalsIgnoreCase(""))	
			{
				Number reconNum =myFormatter.parse((CommonFunction.checkNull(header1.get(27))).trim());
				fetchVo.setNetLtvPercentageOnCombined(myFormatter.format(reconNum));
			}
			else
			{
				fetchVo.setNetLtvPercentageOnCombined("0.00");
			}
		    
		    
  			bufInsSql=null;
  			header=null;
  			header1=null;
  		}
 		catch(Exception e)
 		{
 			e.printStackTrace();
 		}
 		// For Other Charges Detail's
 		try
 		{
 			bufInsSql=new StringBuffer(); 
 			bufInsSql.append(" select isnull(legal.DEAL_CHARGE_CALCULATED_AMOUNT,0)legalCharges,isnull(legal.DEAL_CHARGE_FINAL_AMOUNT,0)total_leagal,");
 			bufInsSql.append(" isnull(roc.DEAL_CHARGE_CALCULATED_AMOUNT,0)rocCharges,isnull(roc.DEAL_CHARGE_FINAL_AMOUNT,0)total_roc,");
 			bufInsSql.append(" isnull(valuation.DEAL_CHARGE_CALCULATED_AMOUNT,0)valuationCharges,isnull(valuation.DEAL_CHARGE_FINAL_AMOUNT,0)total_valuation,");
 			bufInsSql.append(" isnull(stamp.DEAL_CHARGE_CALCULATED_AMOUNT,0)stampCharges,isnull(stamp.DEAL_CHARGE_FINAL_AMOUNT,0)total_stamp,");
 			bufInsSql.append(" isnull(other.DEAL_CHARGE_CALCULATED_AMOUNT,0)otherCharges,isnull(other.DEAL_CHARGE_FINAL_AMOUNT,0)total_other");
 			bufInsSql.append(" from"); 
 			bufInsSql.append(" cr_deal_dtl a");
 			bufInsSql.append(" left join");
 			bufInsSql.append(" (");
 			bufInsSql.append(" 		select deal_id,DEAL_CHARGE_FINAL_AMOUNT,DEAL_CHARGE_CALCULATED_AMOUNT ");// legal
 			bufInsSql.append(" 		from CR_DEAL_TXNCHARGES_DTL"); 
 			bufInsSql.append(" 		where DEAL_CHARGE_CODE=123 and DEAL_ID='"+CommonFunction.checkNull(vo.getDealId()).trim()+"' "); 
 			bufInsSql.append(" )legal on(a.deal_id=legal.deal_id)");
 			bufInsSql.append(" left join");
 			bufInsSql.append(" (");
 			bufInsSql.append(" 		select deal_id,DEAL_CHARGE_FINAL_AMOUNT,DEAL_CHARGE_CALCULATED_AMOUNT ");//roc
 			bufInsSql.append(" 		from CR_DEAL_TXNCHARGES_DTL"); 
 			bufInsSql.append(" 		where DEAL_CHARGE_CODE=128 and DEAL_ID='"+CommonFunction.checkNull(vo.getDealId()).trim()+"' "); 
 			bufInsSql.append(" )roc on(a.deal_id=roc.deal_id)");
 			bufInsSql.append(" left join");
 			bufInsSql.append(" (");
 			bufInsSql.append(" 		select deal_id,DEAL_CHARGE_FINAL_AMOUNT,DEAL_CHARGE_CALCULATED_AMOUNT ");//valuation
 			bufInsSql.append(" 		from CR_DEAL_TXNCHARGES_DTL"); 
 			bufInsSql.append(" 		where DEAL_CHARGE_CODE=122 and DEAL_ID='"+CommonFunction.checkNull(vo.getDealId()).trim()+"' "); 
 			bufInsSql.append(" )valuation on(a.deal_id=valuation.deal_id)");
 			bufInsSql.append(" left join");
 			bufInsSql.append(" (");
 			bufInsSql.append(" 		select deal_id,DEAL_CHARGE_FINAL_AMOUNT,DEAL_CHARGE_CALCULATED_AMOUNT ");//stamp duty
 			bufInsSql.append(" 		from CR_DEAL_TXNCHARGES_DTL"); 
 			bufInsSql.append(" 		where DEAL_CHARGE_CODE=120 and DEAL_ID='"+CommonFunction.checkNull(vo.getDealId()).trim()+"' "); 
 			bufInsSql.append(" )stamp on(a.deal_id=stamp.deal_id)");
 			bufInsSql.append(" left join");
 			bufInsSql.append(" (");
 			bufInsSql.append(" 		select deal_id,DEAL_CHARGE_FINAL_AMOUNT,DEAL_CHARGE_CALCULATED_AMOUNT ");//other
 			bufInsSql.append(" 		from CR_DEAL_TXNCHARGES_DTL"); 
 			bufInsSql.append(" 		where DEAL_CHARGE_CODE=132 and DEAL_ID='"+CommonFunction.checkNull(vo.getDealId()).trim()+"' "); 
 			bufInsSql.append(" )other on(a.deal_id=other.deal_id)");
 			bufInsSql.append(" where a.deal_id='"+CommonFunction.checkNull(vo.getDealId()).trim()+"' ");
 			 			
 			logger.info("In fetchRecordDetail() of MSSQLDealRateApprovalDAOImpl Other Charges Detail Qery : " + bufInsSql.toString());
			header = ConnectionDAO.sqlSelect(bufInsSql.toString());
  		  	ArrayList header1=(ArrayList)header.get(0);
  		  	
  		if(!CommonFunction.checkNull(header1.get(0)).equalsIgnoreCase(""))
  		{
  		Number reconNum=0.0000;
		reconNum = myFormatter.parse((CommonFunction.checkNull(header1.get(0))).trim());
  		fetchVo.setLegalCharges(myFormatter.format(reconNum));
  		}
  		else
  		{
  			fetchVo.setLegalCharges("0.0000");	
  		}
  		
  		if(!CommonFunction.checkNull(header1.get(1)).equalsIgnoreCase(""))
  		{
  		Number reconNum=0.0000;
			reconNum = myFormatter.parse((CommonFunction.checkNull(header1.get(1))).trim());
			fetchVo.setTotalLeagalCharges(myFormatter.format(reconNum));
  		}
  		else
  		{
  			fetchVo.setTotalLeagalCharges("0.0000");	
  		}
  		
  		if(!CommonFunction.checkNull(header1.get(2)).equalsIgnoreCase(""))
  		{
  			Number reconNum=0.0000;
			reconNum = myFormatter.parse((CommonFunction.checkNull(header1.get(2))).trim());
			fetchVo.setRocCharges(myFormatter.format(reconNum));
  		}
  		else
  		{
  			fetchVo.setRocCharges("0.0000");	
  		}
  		
  		if(!CommonFunction.checkNull(header1.get(3)).equalsIgnoreCase(""))
  		{
  			Number reconNum=0.0000;

			reconNum = myFormatter.parse((CommonFunction.checkNull(header1.get(3))).trim());
			fetchVo.setTotalRocCharges(myFormatter.format(reconNum));
  		}
  		else
  		{
  			fetchVo.setTotalRocCharges("0.0000");	
  		}
  		
  		if(!CommonFunction.checkNull(header1.get(4)).equalsIgnoreCase(""))
  		{
  		Number reconNum=0.0000;
		reconNum = myFormatter.parse((CommonFunction.checkNull(header1.get(4))).trim());
  		fetchVo.setValuationCharges(myFormatter.format(reconNum));
  		}
  		else
  		{
  			fetchVo.setValuationCharges("0.0000");	
  		}
  		
  		if(!CommonFunction.checkNull(header1.get(5)).equalsIgnoreCase(""))
  		{
  			Number reconNum=0.0000;
			reconNum = myFormatter.parse((CommonFunction.checkNull(header1.get(5))).trim());
			fetchVo.setTotalValuationCharges(myFormatter.format(reconNum));
  		}
  		else
  		{
  			fetchVo.setTotalValuationCharges("0.0000");	
  		}
  		
  		if(!CommonFunction.checkNull(header1.get(6)).equalsIgnoreCase(""))
  		{
  			Number reconNum=0.0000;
			reconNum = myFormatter.parse((CommonFunction.checkNull(header1.get(6))).trim());
			fetchVo.setStampCharges(myFormatter.format(reconNum));
  
  		}
  		else
  		{
  			fetchVo.setStampCharges("0.0000");	
  		}
  		
  		if(!CommonFunction.checkNull(header1.get(7)).equalsIgnoreCase(""))
  		{
  		Number reconNum=0.0000;
		reconNum = myFormatter.parse((CommonFunction.checkNull(header1.get(7))).trim());
  		fetchVo.setTotalStampCharges(myFormatter.format(reconNum));
  		}
  		else
  		{
  			fetchVo.setTotalStampCharges("0.0000");	
  		}
  		
  		if(!CommonFunction.checkNull(header1.get(8)).equalsIgnoreCase(""))
  		{
  		Number reconNum=0.0000;
		reconNum = myFormatter.parse((CommonFunction.checkNull(header1.get(8))).trim());
  		fetchVo.setOtherCharges(myFormatter.format(reconNum));

  		}
  		else
  		{
  			fetchVo.setOtherCharges("0.0000");	
  		}
  		
  		if(!CommonFunction.checkNull(header1.get(9)).equalsIgnoreCase(""))
  		{
  		Number reconNum=0.0000;
		reconNum = myFormatter.parse((CommonFunction.checkNull(header1.get(9))).trim());
  		fetchVo.setTotalOtherCharges(myFormatter.format(reconNum));
  		}
  		else
  		{
  			fetchVo.setTotalOtherCharges("0.0000");	
  		}
  		

  			bufInsSql=null;
  			header=null;
  			header1=null;
  		}
 		catch(Exception e)
 		{
 			e.printStackTrace();
 		}
 		//for subvention
 		try
 		{
 			bufInsSql=new StringBuffer(); 
 			
 			bufInsSql.append("select isnull(dealer.DEAL_CHARGE_FINAL_AMOUNT,0)dealerCharges,");
 			bufInsSql.append("isnull(manufacture.DEAL_CHARGE_FINAL_AMOUNT,0)+isnull(dealer.DEAL_CHARGE_FINAL_AMOUNT,0)manufactureCharges ");
 			bufInsSql.append("from "); 
 			bufInsSql.append("cr_deal_dtl a ");
 			bufInsSql.append("left join ");
 			bufInsSql.append("(");
 			bufInsSql.append("select deal_id,DEAL_CHARGE_FINAL_AMOUNT,DEAL_CHARGE_TAX_AMOUNT1  ");// dealer
 			bufInsSql.append("from CR_DEAL_TXNCHARGES_DTL ");
 			bufInsSql.append("where DEAL_CHARGE_CODE=111 and DEAL_ID='"+CommonFunction.checkNull(vo.getDealId()).trim()+"' ");
 			bufInsSql.append(")dealer on(a.deal_id=dealer.deal_id) ");
 			bufInsSql.append("left join ");
 			bufInsSql.append("(");
 			bufInsSql.append("select deal_id,DEAL_CHARGE_FINAL_AMOUNT,DEAL_CHARGE_TAX_AMOUNT1  ");// manufacture
 			bufInsSql.append("from CR_DEAL_TXNCHARGES_DTL ");
 			bufInsSql.append("where DEAL_CHARGE_CODE=252 and DEAL_ID='"+CommonFunction.checkNull(vo.getDealId()).trim()+"' ");
 			bufInsSql.append(")manufacture on(a.deal_id=manufacture.deal_id) ");
 			bufInsSql.append(" where a.deal_id='"+CommonFunction.checkNull(vo.getDealId()).trim()+"' ");
 			
 			
 			 			
 			logger.info("In fetchRecordDetail() of MSSQLDealRateApprovalDAOImpl subvention Detail Qery : " + bufInsSql.toString());
			header = ConnectionDAO.sqlSelect(bufInsSql.toString());
  		  	ArrayList header1=(ArrayList)header.get(0);
  		  	
  		  if(!CommonFunction.checkNull(header1.get(0)).equalsIgnoreCase(""))
  		{
  		Number reconNum=0.0000;
  		try{
			reconNum = myFormatter.parse((CommonFunction.checkNull(header1.get(0))).trim());
			fetchVo.setDealerSubvention(myFormatter.format(reconNum));
  		}
  		catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
  		}
  		else
  		{
  			fetchVo.setDealerSubvention("0.0000");	
  		}
  		
  		if(!CommonFunction.checkNull(header1.get(1)).equalsIgnoreCase(""))
  		{
  		Number reconNum=0.0000;
  		try{
			reconNum = myFormatter.parse((CommonFunction.checkNull(header1.get(1))).trim());
			fetchVo.setManufactureSubvention(myFormatter.format(reconNum));
  		}
  		catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
  		}
  		else
  		{
  			fetchVo.setManufactureSubvention("0.0000");	
  		}
			  		  

  			bufInsSql=null;
  			header=null;
  			header1=null;
  		}
 		catch(Exception e)
 		{
 			e.printStackTrace();
 		}

 		//for subvention
 		try
 		{
 			bufInsSql=new StringBuffer(); 
 			
 			bufInsSql.append("SELECT ISNULL(A.RACK_PROCESSING_FEE,0),ISNULL(A.RACK_RATE,0) from cr_deal_loan_dtl B ");  
 			bufInsSql.append("LEFT join CR_RATE_MATRIX_M A on (A.PRODUCT_ID=B.DEAL_PRODUCT AND A.SCHEME_ID=B.DEAL_SCHEME AND  A.REC_STATUS='A')  ");
 			bufInsSql.append(" where  b.deal_id='"+CommonFunction.checkNull(vo.getDealId()).trim()+"' ");
 			
 			 						
 			logger.info("In fetchRecordDetail() of MSSQLDealRateApprovalDAOImpl cross sell-life insurance Detail Qery : " + bufInsSql.toString());
			header = ConnectionDAO.sqlSelect(bufInsSql.toString());
  		  	ArrayList header1=(ArrayList)header.get(0);
  		  	
  		  		    		
	    	if(!CommonFunction.checkNull(header1.get(0)).equalsIgnoreCase(""))
    		{
    		Number reconNum=0.0000;
			reconNum = myFormatter.parse((CommonFunction.checkNull(header1.get(0))).trim());
    		fetchVo.setRackProcessingFee(myFormatter.format(reconNum));
    		}
    		else
    		{
    			fetchVo.setRackProcessingFee("0.0000");	
    		}
	    		
	    	if(!CommonFunction.checkNull(header1.get(1)).equalsIgnoreCase(""))
    		{
    		Number reconNum=0.0000000;
			reconNum = myFormatter.parse((CommonFunction.checkNull(header1.get(1))).trim());
    		fetchVo.setRackRate(myFormatter.format(reconNum));
    		}
    		else
    		{
    			fetchVo.setRackRate("0.0000000");	
    		}
		    

  			bufInsSql=null;
  			header=null;
  			header1=null;
  		}
 		catch(Exception e)
 		{
 			e.printStackTrace();
 		}

 		
 		resultList.add(fetchVo);
 		return resultList; 	
	}
	@Override
	public boolean insertRecordDetail(DealRateApprovalVO ob) {
		DealRateApprovalVO vo = (DealRateApprovalVO)ob;
		
		logger.info(" In insertRecordDetail ");
		boolean status = false;
		StringBuffer bufInsUpdSql = new StringBuffer();
		ArrayList qryListB = new ArrayList();
		PrepStmtObject  insertPrepStmtObject =  new PrepStmtObject();
		try {

			bufInsUpdSql.append("insert into CR_RATE_APPROVAL_DTL(DEAL_ID ,DEAL_NO ,BRANCH_ID ,CUSTOMER_LOCATION ,CUSTMER_NAME ,CUTOMER_CONSTITUTION ,");
			bufInsUpdSql.append("APPLICATION_NO ,PRODUCT_DESC ,SCHEME_DESC ,LOAN_AMOUNT ,EFFECTIVE_CUSTOMER_ROI_REDUCING_BAL ,EFFECTIVE_CUSTOMER_ROI_FLAT_RATE ,PROCESSING_FEE_ST ,");
			bufInsUpdSql.append("PROCESSING_FEE ,SERVICE_TAX_ON_PROCCES_FEE ,TOTAL_PROCESS_FEE ,TENURE ,MMD_ICD_AMT_OF_LOAN_AMT ,MMD_ICD_AMT ,INTEREST_ON_MMD_ICD , NET_LOAN_AMT ,NET_ACCOUNT_IRR ,");
			bufInsUpdSql.append("GROSS_ACCOUNT_IRR ,LEGAL_CHARGES ,LEGAL_CHARGES_ST ,ROC_CHARGES ,ROC_CHARGES_ST ,VALUATION_CHARGES ,VALUATION_CHARGES_ST ,STAMPING_CHARGES ,");
			bufInsUpdSql.append("STAMPING_CHARGES_ST ,OTHER_CHARGES ,OTHER_CHARGES_ST ,DISCOUNT_AMT ,DEALER_MANUFACTURE ,CREDIT_INSURANCE_COVER ,TYPE_OF_COVERAGE ,");
			bufInsUpdSql.append("DATE_OF_BIRTH ,SUM_ASSURED_LOAN_AMT ,CREDIT_INSURANCE_TENOR ,CSLI_PREMIUM_AMOUNT ,GENERAL_INSURANCE ,CSGI_PREMIUM_AMOUNT ,OTHER_INSURANCE_SOLD ,");
			bufInsUpdSql.append("PRODUCT_SOLD ,CSOI_PREMIUM_AMOUNT ,TRANSACTION_FEE ,RACK_PROCESSING_FEE ,RACK_RATE ,APPROVAL_BY ,REC_STATUS ,MAKER_ID ,MAKER_DATE,ROI_TYPE,CHANNEL_TYPE,CHANNEL_NAME,PAYOUT_APPLICABLE,PAYOUT_DIFF_FROM_ABOVE_CELL,PSL,IS_THIS_A_BALANCE_TRANSFER,EXISTING_ROI_OF_THE_CUSTOMER,LTV_PERCENTAGE_ON_ASSET,LTV_PERCENTAGE_ON_COMBINED,NET_LTV_PERCENTAGE_ON_ASSET,NET_LTV_PERCENTAGE_ON_COMBINED  )");
			 
			  bufInsUpdSql.append(" values( ");
			  bufInsUpdSql.append(" ?," );//DEAL_ID
		      bufInsUpdSql.append(" ?," );//DEAL_NO
		      bufInsUpdSql.append(" ?," );//BRANCH_ID
			  bufInsUpdSql.append(" ?," );//CUSTOMER_LOCATION
			  bufInsUpdSql.append(" ?," );//CUSTMER_NAME
			  bufInsUpdSql.append(" ?," );//CUTOMER_CONSTITUTION
			  bufInsUpdSql.append(" ?," );//APPLICATION_NO
			  bufInsUpdSql.append(" ?," );//PRODUCT_DESC
			  bufInsUpdSql.append(" ?," );//SCHEME_DESC
			  bufInsUpdSql.append(" ?," );//LOAN_AMOUNT
			  bufInsUpdSql.append(" ?," );//EFFECTIVE_CUSTOMER_ROI_REDUCING_BAL
		      bufInsUpdSql.append(" ?," );//EFFECTIVE_CUSTOMER_ROI_FLAT_RATE
		      bufInsUpdSql.append(" ?," );//PROCESSING_FEE_ST
			  bufInsUpdSql.append(" ?," );//PROCESSING_FEE
			  bufInsUpdSql.append(" ?," );//SERVICE_TAX_ON_PROCCES_FEE
			  bufInsUpdSql.append(" ?," );//TOTAL_PROCESS_FEE
			  bufInsUpdSql.append(" ?," );//TENURE
			  bufInsUpdSql.append(" ?," );//MMD_ICD_AMT_OF_LOAN_AMT
			  bufInsUpdSql.append(" ?," );//MMD_ICD_AMT
			  bufInsUpdSql.append(" ?," );//INTEREST_ON_MMD_ICD
			  bufInsUpdSql.append(" ?," );//NET_LOAN_AMT
		      bufInsUpdSql.append(" ?," );//NET_ACCOUNT_IRR
		      bufInsUpdSql.append(" ?," );//GROSS_ACCOUNT_IRR
			  bufInsUpdSql.append(" ?," );//LEGAL_CHARGES
			  bufInsUpdSql.append(" ?," );//LEGAL_CHARGES_ST
			  bufInsUpdSql.append(" ?," );//ROC_CHARGES
			  bufInsUpdSql.append(" ?," );//ROC_CHARGES_ST
			  bufInsUpdSql.append(" ?," );//VALUATION_CHARGES
			  bufInsUpdSql.append(" ?," );//VALUATION_CHARGES_ST
			  bufInsUpdSql.append(" ?," );//STAMPING_CHARGES
			  bufInsUpdSql.append(" ?," );//STAMPING_CHARGES_ST
		      bufInsUpdSql.append(" ?," );//OTHER_CHARGES
		      bufInsUpdSql.append(" ?," );//OTHER_CHARGES_ST
			  bufInsUpdSql.append(" ?," );//DISCOUNT_AMT
			  bufInsUpdSql.append(" ?," );//DEALER_MANUFACTURE
			  bufInsUpdSql.append(" ?," );//CREDIT_INSURANCE_COVER
			  bufInsUpdSql.append(" ?," );//TYPE_OF_COVERAGE
			  bufInsUpdSql.append(dbo);
			  bufInsUpdSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"')+ ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");//DATE_OF_BIRTH
			  bufInsUpdSql.append(" ?," );//SUM_ASSURED_LOAN_AMT
			  bufInsUpdSql.append(" ?," );//CREDIT_INSURANCE_TENOR
			  bufInsUpdSql.append(" ?," );//CSLI_PREMIUM_AMOUNT
		      bufInsUpdSql.append(" ?," );//GENERAL_INSURANCE
		      bufInsUpdSql.append(" ?," );//CSGI_PREMIUM_AMOUNT
			  bufInsUpdSql.append(" ?," );//OTHER_INSURANCE_SOLD
			  bufInsUpdSql.append(" ?," );//PRODUCT_SOLD
			  bufInsUpdSql.append(" ?," );//CSOI_PREMIUM_AMOUNT
			  bufInsUpdSql.append(" ?," );//TRANSACTION_FEE
			  bufInsUpdSql.append(" ?," );//RACK_PROCESSING_FEE
			  bufInsUpdSql.append(" ?," );//RACK_RATE
			  bufInsUpdSql.append(" ?," );//APPROVAL_BY
			  bufInsUpdSql.append(" 'F'," );//REC_STATUS 
			  bufInsUpdSql.append(" ?," );//MAKER_ID
			  bufInsUpdSql.append(dbo);
			  bufInsUpdSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"')+ ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");//MAKER_DATE
			  bufInsUpdSql.append(" ?," );//ROI_TYPE
			  bufInsUpdSql.append(" ?," );//CHANNEL_TYPE 
			  bufInsUpdSql.append(" ?," );//CHANNEL_NAME
			  bufInsUpdSql.append(" ?," );//PAYOUT_APPLICABLE
			  bufInsUpdSql.append(" ?," );//PAYOUT_DIFF_FROM_ABOVE_CELL
			  bufInsUpdSql.append(" ?," );//PSL   
			  bufInsUpdSql.append(" ?," );//IS_THIS_A_BALANCE_TRANSFER
			  bufInsUpdSql.append(" ?," );//EXISTING_ROI_OF_THE_CUSTOMER
			  bufInsUpdSql.append(" ?," );//LTV_PERCENTAGE_ON_ASSET
			  bufInsUpdSql.append(" ?," );//LTV_PERCENTAGE_ON_COMBINED
			  bufInsUpdSql.append(" ?," );//NET_LTV_PERCENTAGE_ON_ASSET
			  bufInsUpdSql.append(" ?)" );//NET_LTV_PERCENTAGE_ON_COMBINED
			  
			  	if(CommonFunction.checkNull((vo.getDealId()).trim()).equalsIgnoreCase(""))
			  		insertPrepStmtObject.addNull();
			  	else
			  		insertPrepStmtObject.addString((vo.getDealId().trim()));
			       
			 
			  	if(CommonFunction.checkNull((vo.getDealNo()).trim()).equalsIgnoreCase(""))
			      insertPrepStmtObject.addNull();
			    else
			      insertPrepStmtObject.addString((vo.getDealNo().trim()));


			  	if(CommonFunction.checkNull((vo.getBranchId()).trim()).equalsIgnoreCase(""))
			      insertPrepStmtObject.addNull();
			    else
			      insertPrepStmtObject.addString((vo.getBranchId().trim()));


			  	if(CommonFunction.checkNull((vo.getLocation()).trim()).equalsIgnoreCase(""))
			  		insertPrepStmtObject.addString((""));
				  else
				  insertPrepStmtObject.addString((vo.getLocation().trim()));


			  if(CommonFunction.checkNull((vo.getCustomerName()).trim()).equalsIgnoreCase(""))
				  insertPrepStmtObject.addNull();
				  else
				  insertPrepStmtObject.addString((vo.getCustomerName().trim()));


			  if(CommonFunction.checkNull((vo.getCustConstitution()).trim()).equalsIgnoreCase(""))
				  insertPrepStmtObject.addNull();
			  else
				  insertPrepStmtObject.addString((vo.getCustConstitution().trim()));

			  if(CommonFunction.checkNull((vo.getFormNo()).trim()).equalsIgnoreCase(""))
				  insertPrepStmtObject.addNull();
			  else
				 insertPrepStmtObject.addString((vo.getFormNo().trim()));

			  if(CommonFunction.checkNull((vo.getProduct()).trim()).equalsIgnoreCase(""))
				  insertPrepStmtObject.addNull();
			  else
			  insertPrepStmtObject.addString((vo.getProduct().trim()));


			  if(CommonFunction.checkNull((vo.getScheme()).trim()).equalsIgnoreCase(""))
				   insertPrepStmtObject.addNull();
			  else
				   insertPrepStmtObject.addString((vo.getScheme().trim()));

			if((CommonFunction.checkNull(vo.getLoanAmt())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addString("0.0000");
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getLoanAmt()).trim()).toString());
			
			if((CommonFunction.checkNull(vo.getEffRate())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addString("0.0000000");
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getEffRate()).trim()).toString());
			
			if((CommonFunction.checkNull(vo.getFlatRate())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addString("0.0000000");
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getFlatRate()).trim()).toString());
			
			if((CommonFunction.checkNull(vo.getProcessFeePer())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addString("0.0000");
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getProcessFeePer()).trim()).toString());
			
			if((CommonFunction.checkNull(vo.getProcessFeeAmt())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addString("0.0000");
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getProcessFeeAmt()).trim()).toString());
			
			if((CommonFunction.checkNull(vo.getTaxOnProcessFee())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addString("0.0000");
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getTaxOnProcessFee()).trim()).toString());
			
			if((CommonFunction.checkNull(vo.getTotalProcessFee())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addString("0.0000");
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getTotalProcessFee()).trim()).toString());
			
			if((CommonFunction.checkNull(vo.getDealTenure())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addString("0");
			else
				insertPrepStmtObject.addString((vo.getDealTenure().trim()));
			
			if((CommonFunction.checkNull(vo.getMmdIcdPer())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addString("0.0000");
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getMmdIcdPer()).trim()).toString());
			
			if((CommonFunction.checkNull(vo.getMmdIcdAmt())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addString("0.0000");
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getMmdIcdAmt()).trim()).toString());
			
			  
			  if((CommonFunction.checkNull(vo.getInterestOnMmdIcd())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addString("0.0000000");
			  else
					insertPrepStmtObject.addString(myFormatter.parse((vo.getInterestOnMmdIcd()).trim()).toString());
			
			
			if((CommonFunction.checkNull(vo.getNetLoanAmt())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addString("0.0000");
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getNetLoanAmt()).trim()).toString());
			
			if((CommonFunction.checkNull(vo.getNetAccIRR())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addString("0.0000000");
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getNetAccIRR()).trim()).toString());
			
			
			if((CommonFunction.checkNull(vo.getGrossAccIRR())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addString("0.0000000");
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getGrossAccIRR()).trim()).toString());
			
			if((CommonFunction.checkNull(vo.getLegalCharges())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addString("0.0000");
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getLegalCharges()).trim()).toString());
			
			if((CommonFunction.checkNull(vo.getTotalLeagalCharges())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addString("0.0000");
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getTotalLeagalCharges()).trim()).toString());
			
			if((CommonFunction.checkNull(vo.getRocCharges())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addString("0.0000");
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getRocCharges()).trim()).toString());
			
			if((CommonFunction.checkNull(vo.getTotalRocCharges())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addString("0.0000");
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getTotalRocCharges()).trim()).toString());
			
			if((CommonFunction.checkNull(vo.getValuationCharges())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addString("0.0000");
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getValuationCharges()).trim()).toString());
			
			if((CommonFunction.checkNull(vo.getTotalValuationCharges())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addString("0.0000");
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getTotalValuationCharges()).trim()).toString());
			
			if((CommonFunction.checkNull(vo.getStampCharges())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addString("0.0000");
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getStampCharges()).trim()).toString());
			
			if((CommonFunction.checkNull(vo.getTotalStampCharges())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addString("0.0000");
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getTotalStampCharges()).trim()).toString());
			
			if((CommonFunction.checkNull(vo.getOtherCharges())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addString("0.0000");
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getOtherCharges()).trim()).toString());
			
			if((CommonFunction.checkNull(vo.getTotalOtherCharges())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addString("0.0000");
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getTotalOtherCharges()).trim()).toString());
			
			if((CommonFunction.checkNull(vo.getDealerSubvention())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addString("0.0000");
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getDealerSubvention()).trim()).toString());
			
			if((CommonFunction.checkNull(vo.getManufactureSubvention())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addString("0.0000");
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getManufactureSubvention()).trim()).toString());
			
			  if(CommonFunction.checkNull((vo.getCreditInsuranceCover()).trim()).equalsIgnoreCase(""))
				  insertPrepStmtObject.addNull();
			  else
			  insertPrepStmtObject.addString((vo.getCreditInsuranceCover().trim()));
			  
			  if(CommonFunction.checkNull((vo.getTypeOfCoverage()).trim()).equalsIgnoreCase(""))
				  insertPrepStmtObject.addNull();
			  else
			  insertPrepStmtObject.addString((vo.getTypeOfCoverage().trim()));
			
			  if(CommonFunction.checkNull((vo.getDateOfBirth())).equalsIgnoreCase(""))
					 insertPrepStmtObject.addNull();
			  else
			        insertPrepStmtObject.addString((vo.getDateOfBirth()));
			   
			   if((CommonFunction.checkNull(vo.getSumAsurLoanAmt())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addString("0.0000");
				else
					insertPrepStmtObject.addString(myFormatter.parse((vo.getSumAsurLoanAmt()).trim()).toString());
			  
			   if((CommonFunction.checkNull(vo.getCreditInsuranceTenor())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addString("0");
				else
					insertPrepStmtObject.addString((vo.getCreditInsuranceTenor().trim()));
			   
			   if((CommonFunction.checkNull(vo.getCsliPremiumAmt())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addString("0.0000");
				else
					insertPrepStmtObject.addString(myFormatter.parse((vo.getCsliPremiumAmt()).trim()).toString());
			  
			   
			   if((CommonFunction.checkNull(vo.getGeneralInsurance())).trim().equalsIgnoreCase(""))
					  insertPrepStmtObject.addNull();
			   else
					  insertPrepStmtObject.addString((vo.getGeneralInsurance()).trim());
			   
			   if((CommonFunction.checkNull(vo.getCsgiPremiumAmt())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addString("0.0000");
				else
					insertPrepStmtObject.addString(myFormatter.parse((vo.getCsgiPremiumAmt()).trim()).toString());
			   
			   if((CommonFunction.checkNull(vo.getOtherInsuranceSold())).trim().equalsIgnoreCase(""))
					  insertPrepStmtObject.addNull();
			   else
					  insertPrepStmtObject.addString((vo.getOtherInsuranceSold()).trim());
			   
			   if((CommonFunction.checkNull(vo.getProductSold())).trim().equalsIgnoreCase(""))
					  insertPrepStmtObject.addNull();
			   else
					  insertPrepStmtObject.addString((vo.getProductSold()).trim());
			   
			   if((CommonFunction.checkNull(vo.getCsoiPremiumAmt())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addString("0.0000");
				else
					insertPrepStmtObject.addString(myFormatter.parse((vo.getCsoiPremiumAmt()).trim()).toString());
			   
			   if((CommonFunction.checkNull(vo.getTransactionFee())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addString("0.0000");
				else
					insertPrepStmtObject.addString(myFormatter.parse((vo.getTransactionFee()).trim()).toString());
			   
			   if((CommonFunction.checkNull(vo.getRackProcessingFee())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addString("0.0000");
				else
					insertPrepStmtObject.addString(myFormatter.parse((vo.getRackProcessingFee()).trim()).toString());
			   
			   if((CommonFunction.checkNull(vo.getRackRate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addString("0.0000000");
				else
					insertPrepStmtObject.addString(myFormatter.parse((vo.getRackRate()).trim()).toString());
			   
			   if(CommonFunction.checkNull((vo.getAppUserId()).trim()).equalsIgnoreCase(""))
				  insertPrepStmtObject.addNull();
			  else
				   insertPrepStmtObject.addString((vo.getAppUserId().trim()));
			  
			  
			  if((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
				  insertPrepStmtObject.addNull();
			      else
			    	  insertPrepStmtObject.addString((vo.getMakerId()).trim());
			  
			  if(CommonFunction.checkNull((vo.getMakerDate())).equalsIgnoreCase(""))
					 insertPrepStmtObject.addNull();
					 else
			        insertPrepStmtObject.addString((vo.getMakerDate()));
			   if(CommonFunction.checkNull((vo.getRoiType()).trim()).equalsIgnoreCase(""))
					  insertPrepStmtObject.addNull();
				  else
					   insertPrepStmtObject.addString((vo.getRoiType().trim()));
			   
			   if(CommonFunction.checkNull((vo.getChannelType()).trim()).equalsIgnoreCase(""))
					  insertPrepStmtObject.addNull();
				  else
					   insertPrepStmtObject.addString((vo.getChannelType().trim()));
			   
			   if(CommonFunction.checkNull((vo.getChannelName()).trim()).equalsIgnoreCase(""))
					  insertPrepStmtObject.addNull();
				  else
					   insertPrepStmtObject.addString((vo.getChannelName().trim()));
			   
			   if((CommonFunction.checkNull(vo.getPayoutApplication())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addString("0.0000");
				else
				{
					String amt=CommonFunction.checkNull(vo.getPayoutApplication()).trim().replace(",","");
					insertPrepStmtObject.addString(amt);
				}
				if((CommonFunction.checkNull(vo.getPayoutBeing())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addString("0.0000");
				else
				{
					String amt=CommonFunction.checkNull(vo.getPayoutBeing()).trim().replace(",","");
					insertPrepStmtObject.addString(amt);
				}
				if(CommonFunction.checkNull((vo.getPsl()).trim()).equalsIgnoreCase(""))
					 insertPrepStmtObject.addNull();
				 else
					 insertPrepStmtObject.addString((vo.getPsl().trim()));
			   
			   if(CommonFunction.checkNull((vo.getIsThisABalanceTransfer()).trim()).equalsIgnoreCase(""))
					 insertPrepStmtObject.addNull();
				 else
					 insertPrepStmtObject.addString((vo.getIsThisABalanceTransfer().trim()));
			   
				if((CommonFunction.checkNull(vo.getIfBt())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addString("0.0000000");
				else
					insertPrepStmtObject.addString(myFormatter.parse((vo.getIfBt()).trim()).toString());
				
				if((CommonFunction.checkNull(vo.getLtvPercentageOnAsset())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addString("0.0000000");
				else
					insertPrepStmtObject.addString(myFormatter.parse((vo.getLtvPercentageOnAsset()).trim()).toString());
				
				if((CommonFunction.checkNull(vo.getLtvPercentageOnCombined())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addString("0.0000000");
				else
					insertPrepStmtObject.addString(myFormatter.parse((vo.getLtvPercentageOnCombined()).trim()).toString());
				
				if((CommonFunction.checkNull(vo.getNetLtvPercentageOnAsset())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addString("0.0000000");
				else
					insertPrepStmtObject.addString(myFormatter.parse((vo.getNetLtvPercentageOnAsset()).trim()).toString());
				
				if((CommonFunction.checkNull(vo.getNetLtvPercentageOnCombined())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addString("0.0000000");
				else
					insertPrepStmtObject.addString(myFormatter.parse((vo.getNetLtvPercentageOnCombined()).trim()).toString());
			  


			
				insertPrepStmtObject.setSql(bufInsUpdSql.toString());

		        logger.info("IN insertRecordDetail  query1 ### "+insertPrepStmtObject.printQuery());
		        qryListB.add(insertPrepStmtObject);
		        status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryListB);
	    	    
	 		   }catch(Exception e){
	 		   e.printStackTrace();
		       }
	 		   finally
	 		   {
	 			  vo=null;
	 			  qryListB=null;
	 			  bufInsUpdSql=null;
	 			  insertPrepStmtObject=null;
	 		   }
	      	return status;

	}

	public ArrayList fetchDealRateApprovalAuthorDetail(DealRateApprovalVO vo) 
	{
		logger.info("In fetchDealRateApprovalMakerDetail() of MSSQLDealRateApprovalDAOImpl.");		
 		ArrayList list=new ArrayList();
 		try
 		{
 			ArrayList header=null;
 			int count=0;
 			int startRecordIndex=0;
 			int endRecordIndex = no;
 			DealRateApprovalVO fetchVo=null;
 			StringBuffer bufInsSql=new StringBuffer();
 			StringBuffer bufInsSqlTempCount = new StringBuffer(); 			
 			
 	 		


 						bufInsSql.append("select d.DEAL_ID,d.DEAL_NO,DEAL_APPLICATION_FORM_NO,");
 			 			bufInsSql.append("dbo.DATE_FORMAT(n.DEAL_DATE,'"+dateFormat+"') AS DEAL_DATE,d.CUSTMER_NAME,d.PRODUCT_DESC,d.SCHEME_DESC,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=n.MAKER_ID) MAKER_ID ");
 			 			bufInsSql.append(" from CR_RATE_APPROVAL_DTL d "); 			
 			 			bufInsSql.append(" join cr_deal_dtl n on d.DEAL_ID=n.DEAL_ID  ");
 			 			bufInsSql.append("  where d.rec_status='F'  ");
 			 			bufInsSql.append(" and d.BRANCH_ID='"+CommonFunction.checkNull(vo.getBranchId()).trim()+"'  ");
 			 			bufInsSql.append(" and d.APPROVAL_BY='"+CommonFunction.checkNull(vo.getMakerId()).trim()+"'  and d.MAKER_ID!='"+CommonFunction.checkNull(vo.getMakerId()).trim()+"' "); 			
 			 			
 			 			bufInsSqlTempCount.append(" select count(1)");
 			 			bufInsSqlTempCount.append(" from CR_RATE_APPROVAL_DTL d "); 			
 			 			bufInsSqlTempCount.append(" join cr_deal_dtl n on d.DEAL_ID=n.DEAL_ID  ");
 			 			bufInsSqlTempCount.append(" where d.rec_status='F'   ");
 			 			bufInsSqlTempCount.append(" and d.BRANCH_ID='"+CommonFunction.checkNull(vo.getBranchId()).trim()+"'  ");
 			 			bufInsSqlTempCount.append(" and d.APPROVAL_BY='"+CommonFunction.checkNull(vo.getMakerId()).trim()+"'  and d.MAKER_ID!='"+CommonFunction.checkNull(vo.getMakerId()).trim()+"' ");
 			 			 		
 			 			
 			if(!CommonFunction.checkNull(vo.getDealId()).trim().equalsIgnoreCase(""))
 			{
 				bufInsSql.append(" and d.deal_id='"+CommonFunction.checkNull(vo.getDealId()).trim()+"'");
 				bufInsSqlTempCount.append(" and d.deal_id='"+CommonFunction.checkNull(vo.getDealId()).trim()+"'");
 			}
 			if(!CommonFunction.checkNull(vo.getCustomerName()).trim().equalsIgnoreCase(""))
 			{
 				bufInsSql.append(" and d.CUSTMER_NAME like'%"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName()).trim()).trim()+"%' ");
 				bufInsSqlTempCount.append(" and d.CUSTMER_NAME like'%"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName()).trim()).trim()+"%' ");
 			}
 						
 			logger.info("In fetchDealRateApprovalMakerDetail() of MSSQLDealRateApprovalDAOImpl Count Qery :  "+bufInsSqlTempCount.toString());
 			String queryCnt=ConnectionDAO.singleReturn(bufInsSqlTempCount.toString());
 			if(CommonFunction.checkNull(queryCnt).equalsIgnoreCase(""))
 				queryCnt="0";
 			count =Integer.parseInt(queryCnt);
 			
 		    logger.info("current PAge Link no .................... "+vo.getCurrentPageLink());
  			if(vo.getCurrentPageLink()>1)
  			{
  				startRecordIndex = (vo.getCurrentPageLink()-1)*no;
  				endRecordIndex = no;
  				 logger.info("startRecordIndex .................... "+startRecordIndex);
  				 logger.info("endRecordIndex .................... "+endRecordIndex);
  			}
			bufInsSql.append(" ORDER BY d.DEAL_ID OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
			logger.info("In fetchDealRateApprovalMakerDetail() of MSSQLDealRateApprovalDAOImpl Search Qery : " + bufInsSql.toString());
			header = ConnectionDAO.sqlSelect(bufInsSql.toString());
  		  	for(int i=0;i<header.size();i++)
  		  	{
  				ArrayList header1=(ArrayList)header.get(i);
  				if(header1!=null && header1.size()>0)
  				{
  					fetchVo = new DealRateApprovalVO();
  					fetchVo.setGridDealNo("<a href=rateApprovalChecker.do?method=fetchRecordDetailForAuthor&author=authorDetail&dealId="+(CommonFunction.checkNull(CommonFunction.checkNull(header1.get(0)))).trim()+">"+(CommonFunction.checkNull(header1.get(1)))+"</a>");
  					fetchVo.setApplicationno((CommonFunction.checkNull(header1.get(2))).trim());
  					fetchVo.setApplicationdate((CommonFunction.checkNull(header1.get(3))).trim());
  					fetchVo.setGridCustName((CommonFunction.checkNull(header1.get(4))).trim());
   					fetchVo.setProduct((CommonFunction.checkNull(header1.get(5))).trim());
  					fetchVo.setScheme((CommonFunction.checkNull(header1.get(6))).trim());
  					fetchVo.setTotalRecordSize(count);
  					list.add(fetchVo);
  				}
  			}
  		  bufInsSql=null;
  		  bufInsSqlTempCount=null;
 		}
 		catch(Exception e)
 		{
 			e.printStackTrace();
 		}
 		return list;
 	
	}
	
	public boolean saveDealRateAuthorDetail(DealRateApprovalVO vo) 
	{
		boolean status=false;
		ArrayList qryList=new ArrayList();
		StringBuilder query=new StringBuilder();
		DealRateApprovalVO dvo = (DealRateApprovalVO)vo;
		String result=CommonFunction.checkNull(dvo.getDecision()).trim();
			
		 query.append("update CR_RATE_APPROVAL_DTL set REC_STATUS=?,AUTHOR_ID=?,AUTHOR_DATE=" );
		 query.append(dbo);
		 query.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"')+ ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9), ");
		 query.append("AUTHOR_REMARKS=?");
		 query.append(" where DEAL_ID=?");
		 PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		 if(result.equalsIgnoreCase("A")){
			insertPrepStmtObject.addString(("A").trim());
		 }
		 if(result.equalsIgnoreCase("P")){
			insertPrepStmtObject.addString(("P").trim());
		 }
        
        if((CommonFunction.checkNull(dvo.getAuthorId())).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((dvo.getAuthorId()).trim());
        
        if((CommonFunction.checkNull(dvo.getAuthorDate())).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((dvo.getAuthorDate()).trim());
        
        if((CommonFunction.checkNull(dvo.getComments())).trim().equalsIgnoreCase(""))
        	insertPrepStmtObject.addString((""));
		else
			insertPrepStmtObject.addString((dvo.getComments()).trim());
        	
        insertPrepStmtObject.addString((dvo.getDealId()).trim());
        
        insertPrepStmtObject.setSql(query.toString());
        logger.info("IN saveDealRateAuthorDetail() update query1 ### 1...."+insertPrepStmtObject.printQuery());
		qryList.add(insertPrepStmtObject);
		try {
			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			query=null;
			qryList=null;
			insertPrepStmtObject=null;
		}
		 qryList=new ArrayList();
		
		 if(result.equalsIgnoreCase("A") && status)
		  {
			  query=new StringBuilder();
			  String checkStageM=CommonFunction.stageMovement("1", "DC","F",vo.getDealId(), "RAL", vo.getAuthorDate(),vo.getAuthorId());
			  logger.info("checkStageM : "+checkStageM);
			  if(CommonFunction.checkNull(checkStageM).equalsIgnoreCase("S"))
			  {
				  status=true;
			  }
			  else
			  {
				     query.append("update CR_RATE_APPROVAL_DTL set REC_STATUS=?,AUTHOR_ID=?,AUTHOR_DATE=" );
					 query.append(dbo);
					 query.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"')+ ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9), ");
					 query.append("AUTHOR_REMARKS=?");
					 query.append(" where DEAL_ID=?");
					 insertPrepStmtObject = new PrepStmtObject();
					
					 insertPrepStmtObject.addString("F");
							        
			        if((CommonFunction.checkNull(dvo.getAuthorId())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((dvo.getAuthorId()).trim());
			        
			        if((CommonFunction.checkNull(dvo.getAuthorDate())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((dvo.getAuthorDate()).trim());
			        
			        if((CommonFunction.checkNull(dvo.getComments())).trim().equalsIgnoreCase(""))
			        	insertPrepStmtObject.addString((""));
					else
						insertPrepStmtObject.addString((dvo.getComments()).trim());
			        	
			        insertPrepStmtObject.addString((dvo.getDealId()).trim());
			        
			        insertPrepStmtObject.setSql(query.toString());
			        logger.info("IN saveDealRateAuthorDetail() update query1 ### 1...."+insertPrepStmtObject.printQuery());
					qryList.add(insertPrepStmtObject);
					try {
						status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
					} catch (Exception e) {
						e.printStackTrace();
					}
					finally
					{
						query=null;
						qryList=null;
						insertPrepStmtObject=null;
					}
				  status=false;
			  }
		  }
		 else if(result.equalsIgnoreCase("P") && status){
			
			query=new StringBuilder();
			query.append("update CR_DEAL_DTL set REC_STATUS=?,RATE_APPROVAL_REMARKS=? ");
			query.append(" where DEAL_ID=?");
			PrepStmtObject insertPrepStmtObject1 = new PrepStmtObject();

			insertPrepStmtObject1.addString(("P").trim());
	        
	        if((CommonFunction.checkNull(dvo.getComments())).trim().equalsIgnoreCase(""))
	        	insertPrepStmtObject1.addString((""));
			else
				insertPrepStmtObject1.addString((dvo.getComments()).trim());
	        	
	        insertPrepStmtObject1.addString((dvo.getDealId()).trim());
	        
	        insertPrepStmtObject1.setSql(query.toString());
	        logger.info("IN saveDealRateAuthorDetail() update query2 ### 2...."+insertPrepStmtObject1.printQuery());
			qryList.add(insertPrepStmtObject1);
					
			query=new StringBuilder();
			query.append("update CR_DEAL_LOAN_DTL set REC_STATUS=? ");
			query.append(" where DEAL_ID=?");
			PrepStmtObject insertPrepStmtObject2 = new PrepStmtObject();

			insertPrepStmtObject2.addString(("P").trim());
	        	
	        insertPrepStmtObject2.addString((dvo.getDealId()).trim());
	        
	        insertPrepStmtObject2.setSql(query.toString());
	        logger.info("IN saveDealRateAuthorDetail() update query2 ### 3...."+insertPrepStmtObject2.printQuery());
			qryList.add(insertPrepStmtObject2);
			
			query=new StringBuilder();
			query.append("update CR_DEAL_MOVEMENT_DTL set REC_STATUS=?,DEAL_FORWARDED= ");
			query.append(dbo);
			query.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"')+ ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),DEAL_FORWARD_USER=? ");
			query.append(" where DEAL_ID=? AND REC_STATUS=? AND DEAL_STAGE_ID=? ");
			PrepStmtObject insertPrepStmtObject3 = new PrepStmtObject();

			 insertPrepStmtObject3.addString(("X").trim());
	        if((CommonFunction.checkNull(dvo.getAuthorDate())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject3.addNull();
			else
				insertPrepStmtObject3.addString((dvo.getAuthorDate()).trim());
	        if((CommonFunction.checkNull(dvo.getAuthorId())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject3.addNull();
			else
				insertPrepStmtObject3.addString((dvo.getAuthorId()).trim());
	        	
	        insertPrepStmtObject3.addString((dvo.getDealId()).trim());
	        insertPrepStmtObject3.addString(("A").trim());
	        insertPrepStmtObject3.addString(("DC").trim());

	        
	        insertPrepStmtObject3.setSql(query.toString());
	        logger.info("IN saveDealRateAuthorDetail() update query2 ### 4...."+insertPrepStmtObject3.printQuery());
			qryList.add(insertPrepStmtObject3);
		
			StringBuffer bufInsUpdSql = new StringBuffer();
			PrepStmtObject  insertPrepStmtObject4 =  new PrepStmtObject ();
			try {

			bufInsUpdSql.append("INSERT INTO CR_DEAL_MOVEMENT_DTL(DEAL_ID, DEAL_STAGE_ID,DEAL_ACTION, DEAL_RECEIVED,DEAL_RECEIVED_USER,REC_STATUS)");
				  bufInsUpdSql.append(" values( ");
				  bufInsUpdSql.append(" ?," );//DEAL_ID
			      bufInsUpdSql.append(" ?," );//DEAL_STAGE_ID
			      bufInsUpdSql.append(" ?," );//DEAL_ACTION
			      bufInsUpdSql.append(dbo);
				  bufInsUpdSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"')+ ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");//DEAL_RECEIVED
				  bufInsUpdSql.append(" ?," );//DEAL_RECEIVED_USER
				  bufInsUpdSql.append(" ?)" );//REC_STATUS
                  
				 if(CommonFunction.checkNull((vo.getDealId()).trim()).equalsIgnoreCase(""))
		         insertPrepStmtObject4.addNull();
		          else
		          insertPrepStmtObject4.addString((vo.getDealId().trim()));
				 
				  insertPrepStmtObject4.addString("DC");
				  
				  insertPrepStmtObject4.addString("I");
			        
			        if((CommonFunction.checkNull(dvo.getAuthorDate())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject4.addNull();
					else
						insertPrepStmtObject4.addString((dvo.getAuthorDate()).trim());
					  if((CommonFunction.checkNull(dvo.getAuthorId())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject4.addNull();
					  else
							insertPrepStmtObject4.addString((dvo.getAuthorId()).trim());
			        
					  insertPrepStmtObject4.addString("A");

					   insertPrepStmtObject4.setSql(bufInsUpdSql.toString());

			        logger.info("IN saveDealRateAuthorDetail() insert query1 ### "+insertPrepStmtObject4.printQuery());
			        qryList.add(insertPrepStmtObject4);
			        status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			        logger.info("In saveDealRateAuthorDetail......................"+status);
			        qryList.clear();
			        qryList=null;
			        insertPrepStmtObject4=null;
			        bufInsUpdSql=null;
			        insertPrepStmtObject3=null;
			        insertPrepStmtObject2=null;
			        insertPrepStmtObject1=null;
			        query=null;
		 		   }catch(Exception e){
		 		   e.printStackTrace();
			       }
		 		  			
		}
		

		return status;
	}
	public ArrayList getRateApprovalAuthorDetail(String DealId)
	{
		DealRateApprovalVO voa=null;
		ArrayList branchList=new ArrayList();
		ArrayList data=new ArrayList();
		StringBuilder returnquery=new StringBuilder();
		returnquery.append("SELECT DEAL_ID ,DEAL_NO ,BRANCH_ID ,CUSTOMER_LOCATION ,CUSTMER_NAME ,CUTOMER_CONSTITUTION ,");
		returnquery.append("APPLICATION_NO ,PRODUCT_DESC ,SCHEME_DESC ,LOAN_AMOUNT ,EFFECTIVE_CUSTOMER_ROI_REDUCING_BAL ,EFFECTIVE_CUSTOMER_ROI_FLAT_RATE ,PROCESSING_FEE_ST ,");
		returnquery.append("PROCESSING_FEE ,SERVICE_TAX_ON_PROCCES_FEE ,TOTAL_PROCESS_FEE ,TENURE ,MMD_ICD_AMT_OF_LOAN_AMT ,MMD_ICD_AMT ,INTEREST_ON_MMD_ICD , NET_LOAN_AMT ,NET_ACCOUNT_IRR ,");
		returnquery.append("GROSS_ACCOUNT_IRR ,LEGAL_CHARGES ,LEGAL_CHARGES_ST ,ROC_CHARGES ,ROC_CHARGES_ST ,VALUATION_CHARGES ,VALUATION_CHARGES_ST ,STAMPING_CHARGES ,");
		returnquery.append("STAMPING_CHARGES_ST ,OTHER_CHARGES ,OTHER_CHARGES_ST ,DISCOUNT_AMT ,DEALER_MANUFACTURE ,CREDIT_INSURANCE_COVER ,TYPE_OF_COVERAGE ,");
		returnquery.append(dbo);
		returnquery.append("DATE_FORMAT(DATE_OF_BIRTH,'"+dateFormatWithTime+"'),");
		returnquery.append("SUM_ASSURED_LOAN_AMT ,CREDIT_INSURANCE_TENOR ,CSLI_PREMIUM_AMOUNT ,GENERAL_INSURANCE ,CSGI_PREMIUM_AMOUNT ,OTHER_INSURANCE_SOLD ,");
		returnquery.append("PRODUCT_SOLD ,CSOI_PREMIUM_AMOUNT ,TRANSACTION_FEE ,RACK_PROCESSING_FEE ,RACK_RATE,ROI_TYPE,CHANNEL_TYPE,CHANNEL_NAME,PAYOUT_APPLICABLE,PAYOUT_DIFF_FROM_ABOVE_CELL,PSL ,IS_THIS_A_BALANCE_TRANSFER,EXISTING_ROI_OF_THE_CUSTOMER,LTV_PERCENTAGE_ON_ASSET,LTV_PERCENTAGE_ON_COMBINED,NET_LTV_PERCENTAGE_ON_ASSET,NET_LTV_PERCENTAGE_ON_COMBINED  FROM CR_RATE_APPROVAL_DTL where DEAL_ID='"+DealId+"' and REC_STATUS='F'");		
		try {
			logger.info(" In......getRateApprovalAuthorDetail()"+returnquery.toString());
			branchList = ConnectionDAO.sqlSelect(returnquery.toString());
	        for(int i=0;i<branchList.size();i++)
	    	    {	    	    
	    	    	ArrayList bdetails1=(ArrayList)branchList.get(i);
	    	    	if(bdetails1.size()>0)
	    			{
	    	    		voa=new DealRateApprovalVO();    	    		
	    	    		voa.setDealId((CommonFunction.checkNull(bdetails1.get(0)).trim()));
	    	    		voa.setDealNo((CommonFunction.checkNull(bdetails1.get(1)).trim()));
	    	    		voa.setBranchId((CommonFunction.checkNull(bdetails1.get(2)).trim()));
	    	    		voa.setLocation((CommonFunction.checkNull(bdetails1.get(3)).trim()));
	    	    		voa.setCustomerName((CommonFunction.checkNull(bdetails1.get(4)).trim()));
	    	    		voa.setCustConstitution((CommonFunction.checkNull(bdetails1.get(5)).trim()));
	    	    		voa.setFormNo((CommonFunction.checkNull(bdetails1.get(6)).trim()));
	    	    		voa.setProduct((CommonFunction.checkNull(bdetails1.get(7)).trim()));
	    	    		voa.setScheme((CommonFunction.checkNull(bdetails1.get(8)).trim()));
	    	    		
	    	    		if(!CommonFunction.checkNull(bdetails1.get(9)).equalsIgnoreCase(""))
	    	    		{
	    	    		Number reconNum=0.0000;
	    	    		try {
						reconNum = myFormatter.parse((CommonFunction.checkNull(bdetails1.get(9))).trim());
	    	    		}catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	    	    		voa.setLoanAmt(myFormatter.format(reconNum));
	    	    		}
	    	    		else
	    	    		{
	    	    			voa.setLoanAmt("0.0000");	
	    	    		}
	    	    	
	    	    	if(!CommonFunction.checkNull(bdetails1.get(10)).equalsIgnoreCase(""))
    	    		{
    	    		Number reconNum=0.0000000;
    	    		try {
					reconNum = myFormatter.parse((CommonFunction.checkNull(bdetails1.get(10))).trim());
    	    		}catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    	    		voa.setEffRate(myFormatter.format(reconNum));
    	    		}
	    	    	
    	    		else
    	    		{
    	    			voa.setEffRate("0.0000000");	
    	    		}
	    	    	
    	    		if(!CommonFunction.checkNull(bdetails1.get(11)).equalsIgnoreCase(""))
    	    		{
    	    		Number reconNum=0.0000000;
    	    		try {
					reconNum = myFormatter.parse((CommonFunction.checkNull(bdetails1.get(11))).trim());
    	    		}catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    	    		voa.setFlatRate(myFormatter.format(reconNum));
    	    		}
	    			
    	    		else
    	    		{
    	    			voa.setFlatRate("0.0000000");	
    	    		}
    	    		
    	    		if(!CommonFunction.checkNull(bdetails1.get(12)).equalsIgnoreCase(""))
    	    		{
    	    		Number reconNum=0.0000;
    	    		try{
					reconNum = myFormatter.parse((CommonFunction.checkNull(bdetails1.get(12))).trim());
    	    		}catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    	    		voa.setProcessFeePer(myFormatter.format(reconNum));
    	    		}
	    			
    	    		else
    	    		{
    	    			voa.setProcessFeePer("0.0000");	
    	    		}
    	    		
    	    		if(!CommonFunction.checkNull(bdetails1.get(13)).equalsIgnoreCase(""))
    	    		{
    	    		Number reconNum=0.0000;
    	    		try{
					reconNum = myFormatter.parse((CommonFunction.checkNull(bdetails1.get(13))).trim());
    	    		voa.setProcessFeeAmt(myFormatter.format(reconNum));
    	    		}
    	    		catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    	    		}
    	    		else
    	    		{
    	    			voa.setProcessFeeAmt("0.0000");	
    	    		}
    	    		
    	    		if(!CommonFunction.checkNull(bdetails1.get(14)).equalsIgnoreCase(""))
    	    		{
    	    		Number reconNum=0.0000;
    	    		try{
					reconNum = myFormatter.parse((CommonFunction.checkNull(bdetails1.get(14))).trim());
    	    		voa.setTaxOnProcessFee(myFormatter.format(reconNum));
    	    		}
    	    		catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    	    		}
    	    		else
    	    		{
    	    			voa.setTaxOnProcessFee("0.0000");	
    	    		}
    	    		
    	    		if(!CommonFunction.checkNull(bdetails1.get(15)).equalsIgnoreCase(""))
    	    		{
    	    		Number reconNum=0.0000;
    	    		try{
					reconNum = myFormatter.parse((CommonFunction.checkNull(bdetails1.get(15))).trim());
    	    		voa.setTotalProcessFee(myFormatter.format(reconNum));
    	    		}
    	    		catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    	    		}
    	    		else
    	    		{
    	    			voa.setTotalProcessFee("0.0000");	
    	    		}
    	    		
    	    		if(!CommonFunction.checkNull(bdetails1.get(16)).equalsIgnoreCase(""))
    	    		{
    	    		voa.setDealTenure((CommonFunction.checkNull(bdetails1.get(16)).trim()));

    	    		}
    	    		else
    	    		{
    	    			voa.setDealTenure("0");	
    	    		}
    	    		
    	    		if(!CommonFunction.checkNull(bdetails1.get(17)).equalsIgnoreCase(""))
    	    		{
    	    		Number reconNum=0.0000;
    	    		try{
					reconNum = myFormatter.parse((CommonFunction.checkNull(bdetails1.get(17))).trim());
    	    		voa.setMmdIcdPer(myFormatter.format(reconNum));
    	    		}
    	    		catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    	    		}
    	    		else
    	    		{
    	    			voa.setMmdIcdPer("0.0000");	
    	    		}
    	    		
    	    		if(!CommonFunction.checkNull(bdetails1.get(18)).equalsIgnoreCase(""))
    	    		{
    	    		Number reconNum=0.0000;
    	    		try{
					reconNum = myFormatter.parse((CommonFunction.checkNull(bdetails1.get(18))).trim());
    	    		voa.setMmdIcdAmt(myFormatter.format(reconNum));
    	    		}
    	    		catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    	    		}
    	    		else
    	    		{
    	    			voa.setMmdIcdAmt("0.0000");	
    	    		}
    	    		
    	    		if(!CommonFunction.checkNull(bdetails1.get(19)).equalsIgnoreCase(""))
    	    		{
    	    		Number reconNum=0.0000000;
    	    		try{
					reconNum = myFormatter.parse((CommonFunction.checkNull(bdetails1.get(19))).trim());
    	    		voa.setInterestOnMmdIcd(myFormatter.format(reconNum));
    	    		}
    	    		catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    	    		}
    	    		else
    	    		{
    	    			voa.setInterestOnMmdIcd("0.00");	
    	    		}

	    			
    	    		if(!CommonFunction.checkNull(bdetails1.get(20)).equalsIgnoreCase(""))
    	    		{
    	    		Number reconNum=0.0000;
    	    		try{
					reconNum = myFormatter.parse((CommonFunction.checkNull(bdetails1.get(20))).trim());
    	    		voa.setNetLoanAmt(myFormatter.format(reconNum));
    	    		}
    	    		catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    	    		}
    	    		else
    	    		{
    	    			voa.setNetLoanAmt("0.0000");	
    	    		}
    	    		
    	    		if(!CommonFunction.checkNull(bdetails1.get(21)).equalsIgnoreCase(""))
    	    		{
    	    		Number reconNum=0.0000000;
    	    		try{
					reconNum = myFormatter.parse((CommonFunction.checkNull(bdetails1.get(21))).trim());
    	    		voa.setNetAccIRR(myFormatter.format(reconNum));
    	    		}
    	    		catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    	    		}
    	    		else
    	    		{
    	    			voa.setNetAccIRR("0.0000000");	
    	    		}
    	    		
    	    		if(!CommonFunction.checkNull(bdetails1.get(22)).equalsIgnoreCase(""))
    	    		{
    	    		Number reconNum=0.0000000;
    	    		try{
					reconNum = myFormatter.parse((CommonFunction.checkNull(bdetails1.get(22))).trim());
    	    		voa.setGrossAccIRR(myFormatter.format(reconNum));
    	    		}
    	    		catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    	    		}
    	    		else
    	    		{
    	    			voa.setGrossAccIRR("0.0000000");	
    	    		}   	    		
    	    		
    	    		
    	    		if(!CommonFunction.checkNull(bdetails1.get(23)).equalsIgnoreCase(""))
    	    		{
    	    		Number reconNum=0.0000;
    	    		try{
					reconNum = myFormatter.parse((CommonFunction.checkNull(bdetails1.get(23))).trim());
    	    		voa.setLegalCharges(myFormatter.format(reconNum));
    	    		}
    	    		catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    	    		}
    	    		else
    	    		{
    	    			voa.setLegalCharges("0.0000");	
    	    		}
    	    		
    	    		if(!CommonFunction.checkNull(bdetails1.get(24)).equalsIgnoreCase(""))
    	    		{
    	    		Number reconNum=0.0000;
    	    		try{
					reconNum = myFormatter.parse((CommonFunction.checkNull(bdetails1.get(24))).trim());
    	    		voa.setTotalLeagalCharges(myFormatter.format(reconNum));
    	    		}
    	    		catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    	    		}
    	    		else
    	    		{
    	    			voa.setTotalLeagalCharges("0.0000");	
    	    		}
    	    		
    	    		if(!CommonFunction.checkNull(bdetails1.get(25)).equalsIgnoreCase(""))
    	    		{
    	    		Number reconNum=0.0000;
    	    		try{
					reconNum = myFormatter.parse((CommonFunction.checkNull(bdetails1.get(25))).trim());
    	    		voa.setRocCharges(myFormatter.format(reconNum));
    	    		}
    	    		catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    	    		}
    	    		else
    	    		{
    	    			voa.setRocCharges("0.0000");	
    	    		}
    	    		
    	    		if(!CommonFunction.checkNull(bdetails1.get(26)).equalsIgnoreCase(""))
    	    		{
    	    		Number reconNum=0.0000;
    	    		try{
					reconNum = myFormatter.parse((CommonFunction.checkNull(bdetails1.get(26))).trim());
    	    		voa.setTotalRocCharges(myFormatter.format(reconNum));
    	    		}
    	    		catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    	    		}
    	    		else
    	    		{
    	    			voa.setTotalRocCharges("0.0000");	
    	    		}
    	    		
    	    		if(!CommonFunction.checkNull(bdetails1.get(27)).equalsIgnoreCase(""))
    	    		{
    	    		Number reconNum=0.0000;
    	    		try{
					reconNum = myFormatter.parse((CommonFunction.checkNull(bdetails1.get(27))).trim());
    	    		voa.setValuationCharges(myFormatter.format(reconNum));
    	    		}
    	    		catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    	    		}
    	    		else
    	    		{
    	    			voa.setValuationCharges("0.0000");	
    	    		}
    	    		
    	    		if(!CommonFunction.checkNull(bdetails1.get(28)).equalsIgnoreCase(""))
    	    		{
    	    		Number reconNum=0.0000;
    	    		try{
					reconNum = myFormatter.parse((CommonFunction.checkNull(bdetails1.get(28))).trim());
    	    		voa.setTotalValuationCharges(myFormatter.format(reconNum));
    	    		}
    	    		catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    	    		}
    	    		else
    	    		{
    	    			voa.setTotalValuationCharges("0.0000");	
    	    		}
    	    		
    	    		if(!CommonFunction.checkNull(bdetails1.get(29)).equalsIgnoreCase(""))
    	    		{
    	    		Number reconNum=0.0000;
    	    		try{
					reconNum = myFormatter.parse((CommonFunction.checkNull(bdetails1.get(29))).trim());
    	    		voa.setStampCharges(myFormatter.format(reconNum));
    	    		}
    	    		catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    	    		}
    	    		else
    	    		{
    	    			voa.setStampCharges("0.0000");	
    	    		}
    	    		
    	    		if(!CommonFunction.checkNull(bdetails1.get(30)).equalsIgnoreCase(""))
    	    		{
    	    		Number reconNum=0.0000;
    	    		try{
					reconNum = myFormatter.parse((CommonFunction.checkNull(bdetails1.get(30))).trim());
    	    		voa.setTotalStampCharges(myFormatter.format(reconNum));
    	    		}
    	    		catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    	    		}
    	    		else
    	    		{
    	    			voa.setTotalStampCharges("0.0000");	
    	    		}
    	    		
    	    		if(!CommonFunction.checkNull(bdetails1.get(31)).equalsIgnoreCase(""))
    	    		{
    	    		Number reconNum=0.0000;
    	    		try{
					reconNum = myFormatter.parse((CommonFunction.checkNull(bdetails1.get(31))).trim());
    	    		voa.setOtherCharges(myFormatter.format(reconNum));
    	    		}
    	    		catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    	    		}
    	    		else
    	    		{
    	    			voa.setOtherCharges("0.0000");	
    	    		}
    	    		
    	    		if(!CommonFunction.checkNull(bdetails1.get(32)).equalsIgnoreCase(""))
    	    		{
    	    		Number reconNum=0.0000;
    	    		try{
					reconNum = myFormatter.parse((CommonFunction.checkNull(bdetails1.get(32))).trim());
    	    		voa.setTotalOtherCharges(myFormatter.format(reconNum));
    	    		}
    	    		catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    	    		}
    	    		else
    	    		{
    	    			voa.setTotalOtherCharges("0.0000");	
    	    		}
    	    		
    	    		if(!CommonFunction.checkNull(bdetails1.get(33)).equalsIgnoreCase(""))
    	    		{
    	    		Number reconNum=0.0000;
    	    		try{
					reconNum = myFormatter.parse((CommonFunction.checkNull(bdetails1.get(33))).trim());
    	    		voa.setDealerSubvention(myFormatter.format(reconNum));
    	    		}
    	    		catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    	    		}
    	    		else
    	    		{
    	    			voa.setDealerSubvention("0.0000");	
    	    		}
    	    		
    	    		if(!CommonFunction.checkNull(bdetails1.get(34)).equalsIgnoreCase(""))
    	    		{
    	    		Number reconNum=0.0000;
    	    		try{
					reconNum = myFormatter.parse((CommonFunction.checkNull(bdetails1.get(34))).trim());
    	    		voa.setManufactureSubvention(myFormatter.format(reconNum));
    	    		}
    	    		catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    	    		}
    	    		else
    	    		{
    	    			voa.setManufactureSubvention("0.0000");	
    	    		}
	    			
       	    		voa.setCreditInsuranceCover((CommonFunction.checkNull(bdetails1.get(35)).trim()));
       	    		voa.setTypeOfCoverage((CommonFunction.checkNull(bdetails1.get(36)).trim()));
       	    		voa.setDateOfBirth((CommonFunction.checkNull(bdetails1.get(37)).trim()));
       	    		
       	    		if(!CommonFunction.checkNull(bdetails1.get(38)).equalsIgnoreCase(""))
    	    		{
    	    		Number reconNum=0.0000;
    	    		try{
					reconNum = myFormatter.parse((CommonFunction.checkNull(bdetails1.get(38))).trim());
    	    		voa.setSumAsurLoanAmt(myFormatter.format(reconNum));
    	    		}
       	    		catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    	    		}
    	    		else
    	    		{
    	    			voa.setSumAsurLoanAmt("0.0000");	
    	    		}
       	    		
       	    		if(!CommonFunction.checkNull(bdetails1.get(39)).equalsIgnoreCase(""))
    	    		{
					voa.setCreditInsuranceTenor((CommonFunction.checkNull(bdetails1.get(39)).trim()));
    	    		}
    	    		else
    	    		{
    	    			voa.setCreditInsuranceTenor("0");	
    	    		}
       	    		
       	    		if(!CommonFunction.checkNull(bdetails1.get(40)).equalsIgnoreCase(""))
    	    		{
    	    		Number reconNum=0.0000;
    	    		try{
					reconNum = myFormatter.parse((CommonFunction.checkNull(bdetails1.get(40))).trim());
    	    		voa.setCsliPremiumAmt(myFormatter.format(reconNum));
    	    		}
       	    		catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    	    		}
    	    		else
    	    		{
    	    			voa.setCsliPremiumAmt("0.0000");	
    	    		}
       	    		
       	    		
       	    		voa.setGeneralInsurance((CommonFunction.checkNull(bdetails1.get(41)).trim()));
       	    		
       	    		if(!CommonFunction.checkNull(bdetails1.get(42)).equalsIgnoreCase(""))
    	    		{
    	    		Number reconNum=0.0000;
    	    		try{
					reconNum = myFormatter.parse((CommonFunction.checkNull(bdetails1.get(42))).trim());
    	    		voa.setCsgiPremiumAmt(myFormatter.format(reconNum));
    	    		}
       	    		catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    	    		}
    	    		else
    	    		{
    	    			voa.setCsgiPremiumAmt("0.0000");	
    	    		}
       	    		
       	    		voa.setOtherInsuranceSold((CommonFunction.checkNull(bdetails1.get(43)).trim()));
       	    		
       	    		voa.setProductSold((CommonFunction.checkNull(bdetails1.get(44)).trim()));
       	    		
       	    		if(!CommonFunction.checkNull(bdetails1.get(45)).equalsIgnoreCase(""))
    	    		{
    	    		Number reconNum=0.0000;
    	    		try{
					reconNum = myFormatter.parse((CommonFunction.checkNull(bdetails1.get(45))).trim());
    	    		voa.setCsoiPremiumAmt(myFormatter.format(reconNum));
    	    		}
       	    		catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    	    		}
    	    		else
    	    		{
    	    			voa.setCsoiPremiumAmt("0.0000");	
    	    		}
       	    		
       	    		if(!CommonFunction.checkNull(bdetails1.get(46)).equalsIgnoreCase(""))
    	    		{
    	    		Number reconNum=0.0000;
    	    		try{
					reconNum = myFormatter.parse((CommonFunction.checkNull(bdetails1.get(46))).trim());
    	    		voa.setTransactionFee(myFormatter.format(reconNum));
    	    		}
       	    		catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    	    		}
    	    		else
    	    		{
    	    			voa.setTransactionFee("0.0000");	
    	    		}
       	    		
       	    		if(!CommonFunction.checkNull(bdetails1.get(47)).equalsIgnoreCase(""))
    	    		{
    	    		Number reconNum=0.0000;
    	    		try{
					reconNum = myFormatter.parse((CommonFunction.checkNull(bdetails1.get(47))).trim());
    	    		voa.setRackProcessingFee(myFormatter.format(reconNum));
    	    		}
    	    		catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    	    		}
    	    		else
    	    		{
    	    			voa.setRackProcessingFee("0.0000");	
    	    		}
       	    		
       	    		if(!CommonFunction.checkNull(bdetails1.get(48)).equalsIgnoreCase(""))
    	    		{
    	    		Number reconNum=0.0000000;
    	    		try{
					reconNum = myFormatter.parse((CommonFunction.checkNull(bdetails1.get(48))).trim());
    	    		voa.setRackRate(myFormatter.format(reconNum));
    	    		}
       	    		catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    	    		}
    	    		else
    	    		{
    	    			voa.setRackRate("0.0000000");	
    	    		}
       	    		voa.setRoiType((CommonFunction.checkNull(bdetails1.get(49)).trim()));
       	    		
       	    		voa.setChannelType((CommonFunction.checkNull(bdetails1.get(50)).trim()));
       	    		
       	    		voa.setChannelName((CommonFunction.checkNull(bdetails1.get(51)).trim()));
       	    		
       	    		if(CommonFunction.checkNull(bdetails1.get(52)).equalsIgnoreCase(""))
       	    			voa.setPayoutApplication("0.00");
       	    		else
       	    			voa.setPayoutApplication((String)bdetails1.get(52));
    	    		       	    		
       	    		if(CommonFunction.checkNull(bdetails1.get(53)).equalsIgnoreCase(""))
       	    			voa.setPayoutBeing("0.00");	
       	    		else
       	    			voa.setPayoutBeing((String)bdetails1.get(53));
    	    		
       	    		voa.setPsl((CommonFunction.checkNull(bdetails1.get(54)).trim()));
       	    		voa.setIsThisABalanceTransfer((CommonFunction.checkNull(bdetails1.get(55)).trim()));
       	    		if(!CommonFunction.checkNull(bdetails1.get(56)).equalsIgnoreCase(""))
    	    		{
    	    		Number reconNum=0.0000000;
    	    		try{
					reconNum = myFormatter.parse((CommonFunction.checkNull(bdetails1.get(56))).trim());
    	    		voa.setIfBt(myFormatter.format(reconNum));
    	    		}
    	    		catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    	    		}
    	    		else
    	    		{
    	    			voa.setIfBt("0.0000000");	
    	    		}
       	    		if(!CommonFunction.checkNull(bdetails1.get(57)).equalsIgnoreCase(""))
    	    		{
    	    		Number reconNum=0.0000000;
    	    		try{
					reconNum = myFormatter.parse((CommonFunction.checkNull(bdetails1.get(57))).trim());
    	    		voa.setLtvPercentageOnAsset(myFormatter.format(reconNum));
    	    		}
    	    		catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    	    		}
    	    		else
    	    		{
    	    			voa.setLtvPercentageOnAsset("0.00");	
    	    		}
       	    		if(!CommonFunction.checkNull(bdetails1.get(58)).equalsIgnoreCase(""))
    	    		{
    	    		Number reconNum=0.0000000;
    	    		try{
					reconNum = myFormatter.parse((CommonFunction.checkNull(bdetails1.get(58))).trim());
    	    		voa.setLtvPercentageOnCombined(myFormatter.format(reconNum));
    	    		}
    	    		catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    	    		}
    	    		else
    	    		{
    	    			voa.setLtvPercentageOnCombined("0.00");	
    	    		}
       	    		if(!CommonFunction.checkNull(bdetails1.get(59)).equalsIgnoreCase(""))
    	    		{
    	    		Number reconNum=0.0000000;
    	    		try{
					reconNum = myFormatter.parse((CommonFunction.checkNull(bdetails1.get(59))).trim());
    	    		voa.setNetLtvPercentageOnAsset(myFormatter.format(reconNum));
    	    		}
    	    		catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    	    		}
    	    		else
    	    		{
    	    			voa.setNetLtvPercentageOnAsset("0.00");	
    	    		}
       	    		if(!CommonFunction.checkNull(bdetails1.get(60)).equalsIgnoreCase(""))
    	    		{
    	    		Number reconNum=0.0000000;
    	    		try{
					reconNum = myFormatter.parse((CommonFunction.checkNull(bdetails1.get(60))).trim());
    	    		voa.setNetLtvPercentageOnCombined(myFormatter.format(reconNum));
    	    		}
    	    		catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    	    		}
    	    		else
    	    		{
    	    			voa.setNetLtvPercentageOnCombined("0.00");	
    	    		}
       				
       				

       				
	    	    		data.add(voa);
	    	    		
	    	    		
	    			}
	    	    returnquery=null;
	    	}
			
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return data;
				
	}
	public ArrayList<Object> getCollateralDetailsAllForRateApproval(String dealId,String assetOrCollateralResult)
    {
		CollateralVo vo=null;
        ArrayList<Object> list=new ArrayList<Object>();
	    StringBuilder query=new StringBuilder();
	    ArrayList bdetails=null;
	    ArrayList bdetails1=null;
      try
     {
       query.append("SELECT c.ASSET_ID,c.ASSET_COLLATERAL_DESC,c.ASSET_COLLATERAL_VALUE,c.ASSET_TYPE, ASSET_COLLATERAL_CLASS from cr_asset_collateral_m c left join cr_deal_collateral_m d on c.ASSET_ID=d.ASSETID where  ASSET_COLLATERAL_CLASS='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(assetOrCollateralResult)).trim()+"' AND d.DEAL_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()+"'");

      logger.info("in getCollateralDetailsAllForRateApproval()"+query.toString());
       bdetails = ConnectionDAO.sqlSelect(query.toString());
    	int size=bdetails.size();
	    for(int i=0;i< size;i++)
	    {
	    	 bdetails1=(ArrayList)bdetails.get(i);
	    	if(bdetails1.size()>0)
			{
	    	   vo =  new CollateralVo();
	    		vo.setAssetsId((CommonFunction.checkNull(bdetails1.get(0))).trim());
	    		vo.setAssetsCollateralDesc((CommonFunction.checkNull(bdetails1.get(1))).trim());
	    		if(!(CommonFunction.checkNull(bdetails1.get(2))).equalsIgnoreCase(""))
	    		{
	    			Number reconNum =myFormatter.parse((CommonFunction.checkNull(bdetails1.get(2))).trim());
	    			vo.setAssetsCollateralValue(myFormatter.format(reconNum));
	    		}
	    		
	    		vo.setColltype2((CommonFunction.checkNull(bdetails1.get(3))).trim());
	    		vo.setColltype1((CommonFunction.checkNull(bdetails1.get(4))).trim());
			}

			list.add(vo);
		
		}
		logger.info("list"+list.size());
		}catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
		    query=null;	
		    vo=null;
		    bdetails=null;
		    bdetails1=null;
		    dealId=null;
		    assetOrCollateralResult=null;
		}

		return list;

	}

	@Override
	public String isItMachineOrProperty(String dealId) {
		String assetClass="";
		StringBuilder query1=new StringBuilder();
		StringBuilder query2=new StringBuilder();
	      try
	     {
		    logger.info("in...isItMachineOrProperty()...................."+dealId);
			query1.append("SELECT COUNT(1) from cr_asset_collateral_m c left join cr_deal_collateral_m d on c.ASSET_ID=d.ASSETID where  ASSET_COLLATERAL_CLASS='MACHINE' AND d.DEAL_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()+"'");
			logger.info("In isItAssetOrCollateral"+query1.toString());
			assetClass=(String)ConnectionDAO.singleReturn(query1.toString());
			if(!CommonFunction.checkNull(assetClass).equalsIgnoreCase("0"))
			{
				assetClass="MACHINE";
			}
			else{
				
				query2.append("SELECT COUNT(1) from cr_asset_collateral_m c left join cr_deal_collateral_m d on c.ASSET_ID=d.ASSETID where  ASSET_COLLATERAL_CLASS='PROPERTY' AND d.DEAL_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()+"'");
				logger.info("In isItAssetOrCollateral"+query2.toString());
				assetClass=(String)ConnectionDAO.singleReturn(query2.toString());
				if(!CommonFunction.checkNull(assetClass).equalsIgnoreCase("0"))
				{
					assetClass="PROPERTY";
				}
				else
				{
					assetClass="";
				}
			}

			}catch(Exception e){
				e.printStackTrace();
			}
			finally
			{
				query1=null;
				query2=null;
				dealId=null;
			}

			return assetClass;

	}
	
	
}
