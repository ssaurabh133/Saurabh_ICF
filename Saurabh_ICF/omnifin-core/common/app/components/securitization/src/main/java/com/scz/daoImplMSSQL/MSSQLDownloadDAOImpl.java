package com.scz.daoImplMSSQL;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.connect.CommonFunction;
import com.connect.ConnectionReportDumpsDAO;
import com.scz.dao.DownloadDAO;
import com.scz.vo.PoolCreationForCMVO;
import com.scz.DateFormator;

public class MSSQLDownloadDAOImpl implements DownloadDAO{
	
	private static final Logger logger = Logger.getLogger(MSSQLDownloadDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.utill");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	String dbType=resource.getString("lbl.dbType");
	String dbo=resource.getString("lbl.dbPrefix");

	
	public ArrayList generatePoolReport(PoolCreationForCMVO poolvo,String Userid)
	{		
		logger.info("In generatePoolReport().");		
	     StringBuilder loanAccountNumber=new StringBuilder();
	     StringBuilder customerName=new StringBuilder();
	     StringBuilder customerCategory=new StringBuilder();
	     StringBuilder customerSegment=new StringBuilder();
	     StringBuilder constitution=new StringBuilder();
	     StringBuilder assetCategory=new StringBuilder();
	     StringBuilder industry=new StringBuilder();
	     StringBuilder subIndustry=new StringBuilder();
	     StringBuilder product=new StringBuilder();
	     StringBuilder scheme=new StringBuilder();
	     StringBuilder disbursalDate1=new StringBuilder();
	     StringBuilder disbursalDate2=new StringBuilder();	        
	     StringBuilder maturityDate2=new StringBuilder();
	     StringBuilder maturityDate1=new StringBuilder();
	     StringBuilder loanAmount1=new StringBuilder();
	     StringBuilder loanAmount2=new StringBuilder();
	     StringBuilder installmentAmount1=new StringBuilder();
	     StringBuilder installmentAmount2=new StringBuilder();
	     StringBuilder interestRate1=new StringBuilder();
	     StringBuilder interestRate2=new StringBuilder();
	     StringBuilder rateType=new StringBuilder();
	     StringBuilder amountOutstanding1=new StringBuilder();
	     StringBuilder amountOutstanding2=new StringBuilder();
	     StringBuilder totalInstallments1=new StringBuilder();	        
	     StringBuilder totalInstallments2=new StringBuilder();
	     StringBuilder installmentReceived1=new StringBuilder();
	     StringBuilder installmentReceived2=new StringBuilder();
	     StringBuilder overDueAmount1=new StringBuilder();
	     StringBuilder overDueAmount2=new StringBuilder();
	     StringBuilder DPD1=new StringBuilder();
	     StringBuilder DPD2=new StringBuilder();
	     StringBuilder lbxValue=new StringBuilder();
	     StringBuilder lbxAssetCollId=new StringBuilder();
	     StringBuilder lbxIndustry=new StringBuilder();
	     StringBuilder lbxSubIndustry=new StringBuilder();
	     StringBuilder lbxProductID=new StringBuilder();
	     StringBuilder lbxScheme=new StringBuilder();
	     StringBuilder branchDesc=new StringBuilder();
	     StringBuilder lbxBranchIds=new StringBuilder(); 

	     ArrayList arrList = new ArrayList();
	     ArrayList mainList = new ArrayList();
	     ArrayList subList = new ArrayList();
	     try
	     {	             
			  loanAccountNumber.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getLoanAccountNumber()).trim()));
			  logger.info("  loanAccountNumber"+loanAccountNumber);
			  customerName.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getCustomerName()).trim()));
			  logger.info("  customerName"+customerName);  
			  customerCategory.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getCustomerCategory()).trim()));
			  logger.info("  customerCategory"+customerCategory);
			  customerSegment.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getCustomerSegment()).trim()));
			  logger.info("  customerSegment"+customerSegment);
			  constitution.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getConstitution()).trim()));
			  logger.info("  constitution"+constitution);
			  assetCategory.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getAssetCategory()).trim()));
			  logger.info("  assetCategory"+assetCategory);
			  industry.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getIndustry()).trim()));
			  logger.info("  industry"+industry);
			  subIndustry.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getSubIndustry()).trim()));
			  logger.info("  subIndustry"+subIndustry);
			  product.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getProduct()).trim()));
			  logger.info("  product"+product);
			  scheme.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getScheme()).trim()));
			  logger.info("  scheme"+scheme);
			  disbursalDate1.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getDisbursalDate1()).trim()));
			  logger.info("  disbursalDate1"+disbursalDate1);
			  disbursalDate2.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getDisbursalDate2()).trim()));
			  logger.info("  disbursalDate2"+disbursalDate2);
			  maturityDate1.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getMaturityDate1()).trim()));
			  logger.info("  maturityDate2"+maturityDate2);
			  maturityDate2.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getMaturityDate2()).trim()));
			  logger.info("  maturityDate1"+maturityDate1);
			  loanAmount1.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getLoanAmount1()).trim()));
			  logger.info("  loanAmount1"+loanAmount1);
			  loanAmount2.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getLoanAmount2()).trim()));
			  logger.info("  loanAmount2"+loanAmount2);
			  installmentAmount1.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getInstallmentAmount1()).trim()));
			  logger.info("  installmentAmount1"+installmentAmount1);
			  installmentAmount2.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getInstallmentAmount2()).trim()));
			  logger.info("  installmentAmount2"+installmentAmount2);
			  interestRate1.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getInterestRate1()).trim()));
			  logger.info("  interestRate1"+interestRate1);
			  interestRate2.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getInterestRate2()).trim()));
			  logger.info("  interestRate2"+interestRate2);
			  rateType.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getRateType()).trim()));
			  logger.info("  rateType"+rateType);
			  amountOutstanding1.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getAmountOutstanding1()).trim()));
			  logger.info("  amountOutstanding1"+amountOutstanding1);
			  amountOutstanding2.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getAmountOutstanding2()).trim()));
			  logger.info("  amountOutstanding2"+amountOutstanding2);
			  totalInstallments1.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getTotalInstallments1()).trim()));   
			  logger.info("  totalInstallments1"+totalInstallments1);  
			  totalInstallments2.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getTotalInstallments2()).trim()));
			  logger.info("  totalInstallments2"+totalInstallments2);
			  installmentReceived1.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getInstallmentReceived1()).trim()));
			  logger.info("  installmentReceived1"+installmentReceived1);
			  installmentReceived2.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getInstallmentReceived2()).trim()));
			  logger.info("  installmentReceived2"+installmentReceived2);
			  overDueAmount1.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getOverDueAmount1()).trim()));
			  logger.info("  overDueAmount1"+overDueAmount1);
			  overDueAmount2.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getOverDueAmount2()).trim()));
			  logger.info("  overDueAmount2"+overDueAmount2);
			  DPD1.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getDPD1()).trim()));
			  logger.info("  DPD1"+DPD1);
			  DPD2.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getDPD2()).trim()));
			  logger.info("  DPD2"+DPD2);
			  lbxValue.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getLbxValue()).trim()));
			  logger.info("  lbxValue"+lbxValue);
			  lbxAssetCollId.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getLbxAssetCollId()).trim()));
			  logger.info("  lbxAssetCollId"+lbxAssetCollId);
			  lbxIndustry.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getLbxIndustry()).trim()));
			  logger.info("  lbxIndustry"+lbxIndustry);
			  lbxSubIndustry.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getLbxSubIndustry()).trim()));
			  logger.info("  lbxSubIndustry"+lbxSubIndustry);
			  lbxProductID.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getLbxProductID()).trim()));
			  logger.info("  lbxProductID"+lbxProductID);
			  lbxScheme.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getLbxScheme()).trim()));
			  logger.info("  lbxScheme"+lbxScheme);
			  String legacyLoan= CommonFunction.checkNull(poolvo.getLegacyLoan());
			  logger.info("  legacyLoan  :  "+legacyLoan);
			 
			  branchDesc.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getBranchDesc()).trim()));
			  logger.info("  branchDesc"+branchDesc);
			  lbxBranchIds.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getLbxBranchIds()).trim()));
			  logger.info("  lbxBranchIds :" +lbxBranchIds);
			
			  String cutOffDate=CommonFunction.checkNull(poolvo.getCutOffDate());   
			  logger.info("  cutOffDate    :   "+cutOffDate);
			  String[] constitutionValue = lbxValue.toString().split("/");
			  String[] assetCollValue = lbxAssetCollId.toString().split("/");
			  String[] industryValue = lbxIndustry.toString().split("/");
			  String[] subIndustryValue = lbxSubIndustry.toString().split("/");
			  String[] productValue = lbxProductID.toString().split("/");
			  String[] schemeValue = lbxScheme.toString().split("/");
			  String[] branchValue = lbxBranchIds.toString().split("/");
			  
			  
			  String stateId=CommonFunction.checkNull(poolvo.getTxtStateCode());
			  logger.info("  stateId  :  "+stateId);
			  
			  String instMode=CommonFunction.checkNull(poolvo.getInstMode());
			  logger.info("  instMode  :  "+instMode);
			  
			  String consValue="";
			  String assetValue="";
			  String indsValue="";
			  String subIndsValue="";
			  String prodValue="";
			  String schValue="";			
			  String branValue="";	
			  
			  boolean appendSQL=false;
			  StringBuilder sbAppendToSQLCount=new StringBuilder();
			  StringBuilder bufInsSql =    new StringBuilder();	
			  logger.info("  cutOffDate    :   "+cutOffDate);
			  if(cutOffDate.equalsIgnoreCase("0000-00-00"))
			  {
				  bufInsSql.append(" select distinct A.LOAN_ID, A.LOAN_NO,a.LOAN_REFERENCE_NO,ISNULL(e.CUSTOMER_NAME,'')CUSTOMER_NAME,concat(");
				  bufInsSql.append("ISNULL(b.ADDRESS_LINE1,''),' ',");
				  bufInsSql.append("ISNULL(b.ADDRESS_LINE2,''), ' ' , ");
				  bufInsSql.append("ISNULL(b.ADDRESS_LINE3,'')) as address, ");
				  bufInsSql.append(" collt.ASSET_COLLATERAL_CLASS,collt.ASSET_COLLATERAL_DESC,collt.MANUFATURER_DESC,collt.ASSET_NEW_OLD, ");
				  bufInsSql.append(" case a.LOAN_REPAYMENT_MODE when 'P' then 'PDC' WHEN 'O' THEN 'OTC' WHEN 'DIR' THEN 'DIRECT DEBIT' WHEN 'E' THEN 'ECS' END AS REPAYMENT_MODE, ");
				  bufInsSql.append("ISNULL(p.PDC,'') PDC,");
				  bufInsSql.append("ISNULL(a.LOAN_TENURE,0)as LOAN_TENURE,CASE a.LOAN_INSTALLMENT_TYPE WHEN 'E' THEN 'EQUATED INSTALLMENT' WHEN 'G' THEN 'GRADED INSTALLMENT' when 'P' then 'EQUATED PRINCIPAL' when 'Q' then 'GRADED PRINCIPAL1' when 'R' then 'GRADED PRINCIPAL2' END AS INSTALLMENT_TYPE, ");
				  bufInsSql.append(" FIN.APPL_FIN_DOC,FIN.GUR_FIN_DOC, a.LOAN_SECTOR_TYPE, appl.APPL_ADDRESS_PROOF,appl.APPL_ID_PROOF,appl.APPL_SIGNATURE_PROOF,gur.GUR_ADDRESS_PROOF,gur.GUR_ID_PROOF,gur.GUR_SIGNATURE_PROOF,");
				  bufInsSql.append("ISNULL(a.LOAN_LOAN_AMOUNT,0.00)as LOAN_LOAN_AMOUNT,");
				  bufInsSql.append("ISNULL(disbDate.disDate,'')'Disbursal Date',");
				 // bufInsSql.append("iif(a.LOAN_INSTALLMENT_MODE='A',firEmi2.fdate,firEmi1.fdate)as firstEMIDate,emi.last_emi,iif(a.LOAN_INSTALLMENT_MODE='A',firEmi2.first_emi_month,firEmi1.first_emi_month)as first_emi_month,emi.last_emi_month, ");
				  bufInsSql.append("Case when a.LOAN_INSTALLMENT_MODE='A' then firEmi2.fdate else firEmi1.fdate End as firstEMIDate ,emi.last_emi,Case when a.LOAN_INSTALLMENT_MODE='A' then firEmi2.first_emi_month else firEmi1.first_emi_month  End as first_emi_month ,emi.last_emi_month, ");
				  bufInsSql.append("ISNULL(instAmount.INSTL_AMOUNT,0.00)as INSTL_AMOUNT,");
				  //Need Help
				  bufInsSql.append("iif(LOAN_REPAYMENT_FREQ='M',iif(a.LOAN_INSTALLMENT_MODE='A',(ISNULL(soning.Seasoning,1)-1)*1,(ISNULL(soning.Seasoning,0))*1),iif(LOAN_REPAYMENT_FREQ='B',iif(a.LOAN_INSTALLMENT_MODE='A',(ISNULL(soning.Seasoning,1)-1)*2,(ISNULL(soning.Seasoning,0))*2),iif(LOAN_REPAYMENT_FREQ='Q',iif(a.LOAN_INSTALLMENT_MODE='A',(ISNULL(soning.Seasoning,1)-1)*3,(ISNULL(soning.Seasoning,0))*3),iif(LOAN_REPAYMENT_FREQ='H',iif(a.LOAN_INSTALLMENT_MODE='A',(ISNULL(soning.Seasoning,1)-1)*6,(ISNULL(soning.Seasoning,0))*6),iif(LOAN_REPAYMENT_FREQ='Y',iif(a.LOAN_INSTALLMENT_MODE='A',(ISNULL(soning.Seasoning,1)-1)*12,(ISNULL(soning.Seasoning,0))*12),0)))))Seasoning, ");
				  bufInsSql.append(" case LOAN_REPAYMENT_FREQ when 'M' then (LOAN_NO_OF_INSTALLMENT-LOAN_BILLED_INSTL_NUM)*1 when 'B' then (LOAN_NO_OF_INSTALLMENT-LOAN_BILLED_INSTL_NUM)*2 when 'Q' then (LOAN_NO_OF_INSTALLMENT-LOAN_BILLED_INSTL_NUM)*3 when 'H' then (LOAN_NO_OF_INSTALLMENT-LOAN_BILLED_INSTL_NUM)*6 when 'Y' then (LOAN_NO_OF_INSTALLMENT-LOAN_BILLED_INSTL_NUM)*12 end as REMAINING_TENURE, ");
				  bufInsSql.append("ISNULL(a.LOAN_EMI_AMOUNT,0.00)as LOAN_EMI_AMOUNT,");
				  bufInsSql.append("ISNULL(A.LOAN_EFF_RATE,0.00)as LOAN_EFF_RATE,PR.PRODUCT_DESC,SC.SCHEME_DESC,gm.description, e.CUSTOMER_BUSINESS_SEGMENT, ");
				  bufInsSql.append("ISNULL(I.INDUSTRY_DESC,''),");
				  bufInsSql.append("ISNULL(SI.SUB_INDUSTRY_DESC,''),  ");
				  bufInsSql.append(dbo);
				  bufInsSql.append("DATE_FORMAT(A.LOAN_MATURITY_DATE,'%d-%m-%Y'), ");
				  bufInsSql.append("ISNULL(a.LOAN_BILLED_INSTL_NUM,0)as LOAN_NO_OF_INSTALLMENT, ");
				  //bufInsSql.append(" if(a.LOAN_INSTALLMENT_MODE='A',ISNULL(A.LOAN_ADVANCE_INSTL,0)+1,ISNULL(A.LOAN_ADVANCE_INSTL,0))as LOAN_ADVANCE_INSTL, ");
				  bufInsSql.append(" Case when a.LOAN_INSTALLMENT_MODE='A' then ");
				  bufInsSql.append("ISNULL(A.LOAN_ADVANCE_INSTL,0)+1 else ");
				  bufInsSql.append("ISNULL(A.LOAN_ADVANCE_INSTL,0)  End as LOAN_ADVANCE_INSTL,"); 
				  bufInsSql.append("ISNULL(A.LOAN_FINAL_RATE,0.00)as LOAN_FINAL_RATE,CASE A.DISBURSAL_STATUS WHEN 'N' THEN 'NO DISBURSAL' WHEN 'P' THEN 'PARTIAL' WHEN 'F' THEN 'FULL' END AS DISBURSAL_STATUS, ");
				  bufInsSql.append(" A.NPA_FLAG,Case when A.REC_STATUS='A' then 'APPROVE' else '' End as LOAN_STATUS ,");
				  bufInsSql.append("ISNULL(A.LOAN_DPD,0)as LOAN_DPD,A.LOAN_DPD_STRING,");
				  bufInsSql.append("ISNULL(EMIAmount.LoanAdvanceEMIAmount,0.00)as LoanAdvanceEMIAmount, ");
				  bufInsSql.append("ISNULL(a.LOAN_BALANCE_PRINCIPAL,0)as LOAN_BALANCE_PRINCIPAL,");
				  bufInsSql.append("ISNULL(a.LOAN_OVERDUE_PRINCIPAL,0)as LOAN_OVERDUE_PRINCIPAL,");
				  bufInsSql.append("ISNULL(a.LOAN_RECEIVED_PRINCIPAL,0)as LOAN_RECEIVED_PRINCIPAL, ");
				  bufInsSql.append("ISNULL(a.LOAN_OVERDUE_INSTL_NUM,0)as LOAN_OVERDUE_INSTL_NUM,");
				  bufInsSql.append("ISNULL(a.LOAN_OVERDUE_AMOUNT,0)as LOAN_OVERDUE_AMOUNT,(LOAN_NO_OF_INSTALLMENT-LOAN_BILLED_INSTL_NUM) as LOAN_BALANCE_INSTL_NO,");
				  bufInsSql.append("ISNULL(a.LOAN_BALANCE_INSTL_AMOUNT,0)as LOAN_BALANCE_INSTL_AMOUNT,com_branch_m.BRANCH_DESC,Case when a.LOAN_INSTALLMENT_MODE='A' then 'Yes' else 'No'  End as advanceFlg,");
				  bufInsSql.append("ISNULL(sd.SD_AMOUNT,0)SDAMT,");
				  bufInsSql.append("ISNULL(sd.PENDING_SD_AMOUNT,0) RECEIVED_SDAMT, ");
				  bufInsSql.append("ISNULL(UNDISAMT.UNDIS_AMT,0)UNDISBURSED,");
				  bufInsSql.append("ISNULL(INTCOMP.AMT,0)INTEREST_COMPONENT,(LOAN_RECEIVED_INSTL_AMOUNT-LOAN_RECEIVED_PRINCIPAL)RECEIVED_INTEREST_AMT ");
				  bufInsSql.append(" from  cr_loan_dtl a ");
				  bufInsSql.append(" JOIN cr_product_m PR ON(PR.PRODUCT_ID=A.LOAN_PRODUCT)");
				  bufInsSql.append(" JOIN cr_scheme_m SC ON(SC.SCHEME_ID=A.LOAN_SCHEME)");
				  bufInsSql.append(" left outer join (SELECT  SUM(ADVICE_AMOUNT) SD_AMOUNT,(SUM(ADVICE_AMOUNT-TXN_ADJUSTED_AMOUNT)) PENDING_SD_AMOUNT,LOAN_ID FROM cr_txnadvice_dtl WHERE CHARGE_CODE_ID=103 GROUP BY LOAN_ID)sd on(sd.loan_id=a.loan_id) "); 
				  bufInsSql.append(" LEFT OUTER JOIN (SELECT LOAN_ID,(SUM(");
				  bufInsSql.append("ISNULL(ADVICE_AMOUNT,0))-SUM(");
				  bufInsSql.append("ISNULL(TXN_ADJUSTED_AMOUNT,0)))UNDIS_AMT FROM cr_txnadvice_dtl WHERE CHARGE_CODE_ID=2 AND TXN_TYPE='DIA' GROUP BY LOAN_ID)UNDISAMT ON(UNDISAMT.LOAN_ID=A.LOAN_ID) ");
				  bufInsSql.append(" left outer join (select SUM(INSTL_AMOUNT)AMT,LOAN_ID FROM cr_repaysch_dtl WHERE REC_STATUS<>'X' GROUP BY LOAN_ID)INTCOMP ON(INTCOMP.loan_id=a.loan_id) ");
				  bufInsSql.append(" JOIN com_branch_m on(com_branch_m.BRANCH_ID=a.LOAN_BRANCH) ");
				  bufInsSql.append(" LEFT OUTER JOIN com_industry_m I on(I.INDUSTRY_ID=A.LOAN_INDUSTRY_ID) "); 
				  bufInsSql.append(" LEFT OUTER JOIN com_sub_industry_m SI on(SI.sub_industry_id=A.LOAN_SUB_INDUSTRY_ID ) "); 
				  bufInsSql.append(" JOIN com_address_m b on(b.BPID = a.LOAN_CUSTOMER_ID and COMMUNICATION_ADDRESS='Y') "); 
				  bufInsSql.append(" JOIN gcd_customer_m e on(a.LOAN_CUSTOMER_ID = e.CUSTOMER_ID) "); 
				  bufInsSql.append(" LEFT OUTER JOIN generic_master gm on(e.CUSTOMER_CONSTITUTION=gm.value and gm.GENERIC_KEY='CUST_CONSTITUTION') "); 
				  bufInsSql.append(" LEFT OUTER JOIN (SELECT A.LOAN_ID,STUFF((SELECT '|' + B.ASSET_COLLATERAL_CLASS FROM cr_asset_collateral_m  FOR XML PATH ('')), 1, 1, '')as ASSET_COLLATERAL_CLASS,STUFF((SELECT '|' + B.ASSET_COLLATERAL_DESC FROM cr_asset_collateral_m FOR XML PATH ('')), 1, 1, '')as ASSET_COLLATERAL_DESC,STUFF((SELECT '|' + B.ASSET_MANUFATURER_DESC FROM cr_asset_collateral_m FOR XML PATH ('')), 1, 1, '')as MANUFATURER_DESC,STUFF((SELECT '|' + Case when B.ASSET_NEW_OLD='N' then 'New' else 'Old'  End FROM cr_asset_collateral_m FOR XML PATH ('')), 1, 1, '')as ASSET_NEW_OLD,");
				  bufInsSql.append("sum(ISNULL(B.ASSET_COLLATERAL_VALUE,0))as ASSET_COLLATERAL_VALUE FROM cr_loan_collateral_m A JOIN cr_asset_collateral_m B ON(A.ASSETID=B.ASSET_ID) GROUP BY A.LOAN_ID,B.ASSET_COLLATERAL_CLASS,B.ASSET_COLLATERAL_DESC,B.ASSET_MANUFATURER_DESC,B.ASSET_NEW_OLD) collt on(collt.loan_id=a.LOAN_ID) "); 
				  bufInsSql.append(" left outer join (SELECT PDC_LOAN_ID,COUNT(1) as PDC FROM cr_pdc_instrument_dtl pd WHERE pd.pdc_status='A' group by PDC_LOAN_ID ) as p on(p.PDC_LOAN_ID=a.LOAN_ID) "); 
				  bufInsSql.append(" left outer join (select loan_id,");
				  bufInsSql.append(dbo);
				  bufInsSql.append("DATE_FORMAT(max(DISBURSAL_DATE),'%d-%m-%Y')disDate from cr_loan_disbursal_dtl where rec_status='A' group by loan_id)disbDate on(disbDate.loan_id=a.LOAN_ID) ");
				  bufInsSql.append(" left outer join (select loan_id,");
				  bufInsSql.append(dbo);
				  bufInsSql.append("DATE_FORMAT(instl_date,'%d-%m-%Y')fdate,DATENAME(month, instl_date)first_emi_month from cr_repaysch_dtl where instl_no=1 )firEmi1 on(firEmi1.loan_id=a.LOAN_ID) ");
				  bufInsSql.append(" left outer join (select loan_id,");
				  bufInsSql.append(dbo);
				  bufInsSql.append("DATE_FORMAT(instl_date,'%d-%m-%Y')fdate,DATENAME(month, instl_date)first_emi_month from cr_repaysch_dtl where instl_no=2 )firEmi2 on(firEmi2.loan_id=a.LOAN_ID) ");
				  bufInsSql.append(" LEFT OUTER JOIN (select loan_id,");
				  bufInsSql.append(dbo);
				  bufInsSql.append("DATE_FORMAT(max(instl_date),'%d-%m-%Y')as last_emi,DATENAME(month, max(INSTL_DATE))as last_emi_month from cr_repaysch_dtl group by loan_id )emi on(emi.loan_id=a.LOAN_ID) "); 
				  
				  bufInsSql.append(" LEFT OUTER JOIN (select a.loan_id,CASE WHEN a.LOAN_INSTALLMENT_MODE='A' THEN ISNULL(b.INSTL_AMOUNT1,0)+ISNULL(c.INSTL_AMOUNT2,0) ELSE ISNULL(c.INSTL_AMOUNT2,0) END as INSTL_AMOUNT from cr_loan_dtl a left join(select loan_id,ISNULL(INSTL_AMOUNT,0)as INSTL_AMOUNT1 from cr_repaysch_dtl  where INSTL_NO = '1' group by loan_id,INSTL_AMOUNT)b on(a.loan_id=b.loan_id)left join(select sum(ISNULL(INSTL_AMOUNT,0))as INSTL_AMOUNT2,loan_id from cr_repaysch_dtl where cr_repaysch_dtl.ADV_FLAG='Y' group by loan_id)c on(c.loan_id=b.loan_id)) instAmount on(instAmount.loan_id=a.LOAN_ID) ");
				  bufInsSql.append(" LEFT OUTER JOIN (select loan_id,count(1)as Seasoning from cr_repaysch_dtl where bill_flag = 'Y' and ADV_FLAG='N'  and REC_STATUS<>'X' group by loan_id )as soning on(soning.loan_id=a.LOAN_ID) "); 
				  bufInsSql.append(" LEFT OUTER JOIN (select A.LOAN_ID,SUM(");
				  bufInsSql.append("ISNULL(INSTL_AMOUNT,0))as LoanAdvanceEMIAmount from cr_repaysch_dtl A,cr_loan_dtl B WHERE  A.LOAN_ID=B.LOAN_ID  AND (ADV_FLAG='Y' OR (INSTL_NO=1 AND B.LOAN_INSTALLMENT_MODE='A')) group by A.loan_id,INSTL_AMOUNT )AS EMIAmount on(EMIAmount.loan_id=a.LOAN_ID) "); 
				  bufInsSql.append(" LEFT OUTER JOIN (SELECT LOAN_ID,");
				  bufInsSql.append("ISNULL(C.APPL_FIN_DOC,'NA')APPL_FIN_DOC,");
				  bufInsSql.append("ISNULL(D.GUR_FIN_DOC,'NA')GUR_FIN_DOC FROM cr_loan_dtl A JOIN cr_deal_dtl B ON(A.LOAN_DEAL_ID=B.DEAL_ID) ");
				  bufInsSql.append(" LEFT OUTER JOIN (SELECT STUFF((SELECT '|' + B.DOC_DESC FROM cr_document_child_m  FOR XML PATH ('')), 1, 1, '')as APPL_FIN_DOC,A.TXNID FROM cr_document_dtl A JOIN cr_document_child_m B ON('A'='A') WHERE CHARINDEX( concat('|',B.DOC_CHILD_ID,'|'), concat( '|',A.DOC_CHILD_IDS)) > 0 AND A.DOC_TYPE='PRAPPL' and A.DOC_ID in(13,14) AND TXN_TYPE='DC' GROUP BY A.TXNID,B.DOC_DESC)C ON(C.TXNID=B.DEAL_ID) "); 
				  bufInsSql.append(" LEFT OUTER JOIN (SELECT STUFF((SELECT '|' + B.DOC_DESC FROM cr_document_child_m  FOR XML PATH ('')), 1, 1, '')as GUR_FIN_DOC,A.TXNID FROM cr_document_dtl A JOIN cr_document_child_m B ON('A'='A') WHERE CHARINDEX( concat('|',B.DOC_CHILD_ID,'|'), concat( '|',A.DOC_CHILD_IDS)) > 0 AND A.DOC_TYPE='GUARANTOR' and A.DOC_ID in(13,14) AND TXN_TYPE='DC' GROUP BY A.TXNID,B.DOC_DESC)D ON(D.TXNID=B.DEAL_ID))FIN on(FIN.LOAN_ID=a.LOAN_ID) "); 
				  bufInsSql.append(" LEFT OUTER JOIN (SELECT LOAN_ID,");
				  bufInsSql.append("ISNULL(C.APPL_ADDRESS_PROOF,'NA')APPL_ADDRESS_PROOF,");
				  bufInsSql.append("ISNULL(D.APPL_ID_PROOF,'NA')APPL_ID_PROOF,");
				  bufInsSql.append("ISNULL(E.APPL_SIGNATURE_PROOF,'NA')APPL_SIGNATURE_PROOF FROM cr_loan_dtl A JOIN cr_deal_dtl B ON(A.LOAN_DEAL_ID=B.DEAL_ID) ");
				  bufInsSql.append(" LEFT OUTER JOIN (SELECT STUFF((SELECT '|' + B.DOC_DESC FROM cr_document_child_m  FOR XML PATH ('')), 1, 1, '')as APPL_ADDRESS_PROOF,A.TXNID FROM cr_document_dtl A JOIN cr_document_child_m B ON('A'='A') WHERE CHARINDEX( concat('|',B.DOC_CHILD_ID,'|'), concat( '|',A.DOC_CHILD_IDS)) > 0 AND A.DOC_TYPE='PRAPPL' and A.DOC_ID=5 AND TXN_TYPE='DC' GROUP BY A.TXNID,B.DOC_DESC)C ON(C.TXNID=B.DEAL_ID) "); 
				  bufInsSql.append(" LEFT OUTER JOIN (SELECT STUFF((SELECT '|' + B.DOC_DESC FROM cr_document_child_m  FOR XML PATH ('')), 1, 1, '')as APPL_ID_PROOF,A.TXNID FROM cr_document_dtl A JOIN cr_document_child_m B ON('A'='A') WHERE CHARINDEX( concat('|',B.DOC_CHILD_ID,'|'), concat( '|',A.DOC_CHILD_IDS)) > 0 AND A.DOC_TYPE='PRAPPL' and A.DOC_ID IN(6,2)AND TXN_TYPE='DC' GROUP BY A.TXNID,B.DOC_DESC)D ON(D.TXNID=B.DEAL_ID) "); 
				  bufInsSql.append(" LEFT OUTER JOIN (SELECT STUFF((SELECT '|' + B.DOC_DESC FROM cr_document_child_m  FOR XML PATH ('')), 1, 1, '')as APPL_SIGNATURE_PROOF,A.TXNID FROM cr_document_dtl A JOIN cr_document_child_m B ON('A'='A') WHERE CHARINDEX( concat('|',B.DOC_CHILD_ID,'|'), concat( '|',A.DOC_CHILD_IDS)) > 0 AND A.DOC_TYPE='PRAPPL' and A.DOC_ID=22 AND TXN_TYPE='DC' GROUP BY A.TXNID,B.DOC_DESC)E ON(E.TXNID=B.DEAL_ID))appl on(appl.LOAN_ID=a.LOAN_ID) "); 
				  bufInsSql.append(" LEFT OUTER JOIN (SELECT LOAN_ID,");
				  bufInsSql.append("ISNULL(C.GUR_ADDRESS_PROOF,'NA')GUR_ADDRESS_PROOF,");
				  bufInsSql.append("ISNULL(D.GUR_ID_PROOF,'NA')GUR_ID_PROOF,");
				  bufInsSql.append("ISNULL(E.GUR_SIGNATURE_PROOF,'NA')GUR_SIGNATURE_PROOF FROM cr_loan_dtl A JOIN cr_deal_dtl B ON(A.LOAN_DEAL_ID=B.DEAL_ID) "); 
				  bufInsSql.append(" LEFT OUTER JOIN (select STUFF((SELECT '|' + B.DOC_DESC FROM cr_document_child_m  FOR XML PATH ('')), 1, 1, '')as GUR_ADDRESS_PROOF,A.txnid from (select STUFF((SELECT '' + DOC_child_ids FROM cr_document_dtl  FOR XML PATH ('')), 1, 1, '')as childIds,txnid from cr_document_dtl where DOC_TYPE='GUARANTOR' and DOC_ID=5 AND TXN_TYPE='DC' group by txnid )A join cr_document_child_m B ON('A'='A') WHERE CHARINDEX( concat('|',B.DOC_CHILD_ID,'|'), concat( '|',A.childIds))>0  group by A.txnid,B.DOC_DESC)C ON(C.TXNID=B.DEAL_ID) ");  
				  bufInsSql.append(" LEFT OUTER JOIN (select STUFF((SELECT '|' + B.DOC_DESC FROM cr_document_child_m  FOR XML PATH ('')), 1, 1, '')as GUR_ID_PROOF,A.txnid from (select STUFF((SELECT '' + DOC_child_ids FROM cr_document_dtl  FOR XML PATH ('')), 1, 1, '')as childIds,txnid from cr_document_dtl where DOC_TYPE='GUARANTOR' and DOC_ID IN(6,2) group by txnid )A join cr_document_child_m B ON('A'='A') WHERE CHARINDEX( concat('|',B.DOC_CHILD_ID,'|'), concat( '|',A.childIds)) > 0 group by A.txnid,B.DOC_DESC)D ON(D.TXNID=B.DEAL_ID) ");
				  bufInsSql.append(" LEFT OUTER JOIN (select STUFF((SELECT '|' + B.DOC_DESC FROM cr_document_child_m  FOR XML PATH ('')), 1, 1, '')as GUR_SIGNATURE_PROOF,A.txnid from (select STUFF((SELECT '' + DOC_child_ids FROM cr_document_dtl  FOR XML PATH ('')), 1, 1, '')as childIds,txnid from cr_document_dtl where DOC_TYPE='GUARANTOR' and DOC_ID=22 AND TXN_TYPE='DC' group by txnid )A join cr_document_child_m B ON('A'='A') WHERE CHARINDEX( concat('|',B.DOC_CHILD_ID,'|'), concat( '|',A.childIds)) > 0 group by A.txnid,B.DOC_DESC)E ON(E.TXNID=B.DEAL_ID))gur on(gur.LOAN_ID=a.LOAN_ID) ");
				  bufInsSql.append(" JOIN com_branch_m C ON  C.branch_id=a.loan_branch LEFT JOIN  com_state_m D on C.state_id=D.state_id LEFT JOIN cr_securitization_pool_dtl Z ON Z.LOAN_ID=A.LOAN_ID ");
				  bufInsSql.append(" where a.rec_status='A' and a.DISBURSAL_STATUS='F'  AND ISNULL(Z.REC_STATUS,' ') in('P','F') ");
				  
			  }
			  else
			  {
				  bufInsSql.append(" select distinct A.LOAN_ID, A.LOAN_NO,a.LOAN_REFERENCE_NO,");
				  bufInsSql.append("ISNULL(e.CUSTOMER_NAME,'')CUSTOMER_NAME,concat(");
				  bufInsSql.append("ISNULL(b.ADDRESS_LINE1,''),' ',");
				  bufInsSql.append("ISNULL(b.ADDRESS_LINE2,''), ' ' , ");
				  bufInsSql.append("ISNULL(b.ADDRESS_LINE3,'')) as address, "); 
				  bufInsSql.append(" collt.ASSET_COLLATERAL_CLASS,collt.ASSET_COLLATERAL_DESC,collt.MANUFATURER_DESC,collt.ASSET_NEW_OLD, "); 
				  bufInsSql.append(" case a.LOAN_REPAYMENT_MODE when 'P' then 'PDC' WHEN 'O' THEN 'OTC' WHEN 'DIR' THEN 'DIRECT DEBIT' WHEN 'E' THEN 'ECS' END AS REPAYMENT_MODE, "); 
				  bufInsSql.append("ISNULL(p.PDC,'') PDC,");
				  bufInsSql.append("ISNULL(a.LOAN_TENURE,0)as LOAN_TENURE,CASE a.LOAN_INSTALLMENT_TYPE WHEN 'E' THEN 'EQUATED INSTALLMENT' WHEN 'G' THEN 'GRADED INSTALLMENT' when 'P' then 'EQUATED PRINCIPAL' when 'Q' then 'GRADED PRINCIPAL1' when 'R' then 'GRADED PRINCIPAL2' END AS INSTALLMENT_TYPE, "); 
				  bufInsSql.append(" FIN.APPL_FIN_DOC,FIN.GUR_FIN_DOC, a.LOAN_SECTOR_TYPE, appl.APPL_ADDRESS_PROOF,appl.APPL_ID_PROOF,appl.APPL_SIGNATURE_PROOF,gur.GUR_ADDRESS_PROOF,gur.GUR_ID_PROOF,gur.GUR_SIGNATURE_PROOF,");
				  bufInsSql.append("ISNULL(a.LOAN_LOAN_AMOUNT,0.00)as LOAN_LOAN_AMOUNT,");
				  bufInsSql.append("ISNULL(disbDate.disDate,'')'Disbursal Date',Case when a.LOAN_INSTALLMENT_MODE='A' then firEmi2.fdate else firEmi1.fdate End as firstEMIDate,emi.last_emi,Case when a.LOAN_INSTALLMENT_MODE='A' then firEmi2.first_emi_month else firEmi1.first_emi_month  End as first_emi_month ,emi.last_emi_month, "); 
				  bufInsSql.append("ISNULL(instAmount.INSTL_AMOUNT,0.00)as INSTL_AMOUNT,iif(LOAN_REPAYMENT_FREQ='M',iif(a.LOAN_INSTALLMENT_MODE='A',(ISNULL(soning.Seasoning,1)-1)*1,(ISNULL(soning.Seasoning,0))*1),iif(LOAN_REPAYMENT_FREQ='B',iif(a.LOAN_INSTALLMENT_MODE='A',(ISNULL(soning.Seasoning,1)-1)*2,(ISNULL(soning.Seasoning,0))*2),iif(LOAN_REPAYMENT_FREQ='Q',iif(a.LOAN_INSTALLMENT_MODE='A',(ISNULL(soning.Seasoning,1)-1)*3,(ISNULL(soning.Seasoning,0))*3),iif(LOAN_REPAYMENT_FREQ='H',iif(a.LOAN_INSTALLMENT_MODE='A',(ISNULL(soning.Seasoning,1)-1)*6,(ISNULL(soning.Seasoning,0))*6),iif(LOAN_REPAYMENT_FREQ='Y',iif(a.LOAN_INSTALLMENT_MODE='A',(ISNULL(soning.Seasoning,1)-1)*12,(ISNULL(soning.Seasoning,0))*12),0)))))Seasoning, "); 
				  bufInsSql.append(" case LOAN_REPAYMENT_FREQ when 'M' then (eod.LOAN_NO_OF_INSTALLMENT-eod.LOAN_BILLED_INSTL_NUM)*1 when 'B' then (eod.LOAN_NO_OF_INSTALLMENT-eod.LOAN_BILLED_INSTL_NUM)*2 when 'Q' then (eod.LOAN_NO_OF_INSTALLMENT-eod.LOAN_BILLED_INSTL_NUM)*3 when 'H' then (eod.LOAN_NO_OF_INSTALLMENT-eod.LOAN_BILLED_INSTL_NUM)*6 when 'Y' then (eod.LOAN_NO_OF_INSTALLMENT-eod.LOAN_BILLED_INSTL_NUM)*12 end as REMAINING_TENURE, "); 
				  bufInsSql.append("ISNULL(a.LOAN_EMI_AMOUNT,0.00)as LOAN_EMI_AMOUNT,");
				  bufInsSql.append("ISNULL(A.LOAN_EFF_RATE,0.00)as LOAN_EFF_RATE,PR.PRODUCT_DESC,SC.SCHEME_DESC,gm.description, e.CUSTOMER_BUSINESS_SEGMENT, "); 
				  bufInsSql.append("ISNULL(I.INDUSTRY_DESC,''),");
				  bufInsSql.append("ISNULL(SI.SUB_INDUSTRY_DESC,''),  ");
				  bufInsSql.append(dbo);
				  bufInsSql.append("DATE_FORMAT(A.LOAN_MATURITY_DATE,'%d-%m-%Y'), ");
				  bufInsSql.append("ISNULL(eod.LOAN_BILLED_INSTL_NUM,0)as LOAN_NO_OF_INSTALLMENT, "); 
				  //bufInsSql.append(" if(a.LOAN_INSTALLMENT_MODE='A',ISNULL(A.LOAN_ADVANCE_INSTL,0)+1,ISNULL(A.LOAN_ADVANCE_INSTL,0))as LOAN_ADVANCE_INSTL, ");
				  bufInsSql.append("Case when a.LOAN_INSTALLMENT_MODE='A' then ");
				  bufInsSql.append("ISNULL(A.LOAN_ADVANCE_INSTL,0)+1 else ");
				  bufInsSql.append("ISNULL(A.LOAN_ADVANCE_INSTL,0)  End as LOAN_ADVANCE_INSTL ,");
				  bufInsSql.append("ISNULL(A.LOAN_FINAL_RATE,0.00)as LOAN_FINAL_RATE,CASE A.DISBURSAL_STATUS WHEN 'N' THEN 'NO DISBURSAL' WHEN 'P' THEN 'PARTIAL' WHEN 'F' THEN 'FULL' END AS DISBURSAL_STATUS, "); 
				  bufInsSql.append(" A.NPA_FLAG,Case when A.REC_STATUS='A' then 'APPROVE' else ''  End as LOAN_STATUS,");
				  bufInsSql.append("ISNULL(eod.LOAN_DPD,0)as LOAN_DPD,eod.LOAN_DPD_STRING,");
				  bufInsSql.append("ISNULL(EMIAmount.LoanAdvanceEMIAmount,0.00)as LoanAdvanceEMIAmount, "); 
				  bufInsSql.append("ISNULL(eod.LOAN_BALANCE_PRINCIPAL,0)as LOAN_BALANCE_PRINCIPAL,");
				  bufInsSql.append("ISNULL(eod.LOAN_OVERDUE_PRINCIPAL,0)as LOAN_OVERDUE_PRINCIPAL,");
				  bufInsSql.append("ISNULL(eod.LOAN_RECEIVED_PRINCIPAL,0)as LOAN_RECEIVED_PRINCIPAL, "); 
				  bufInsSql.append("ISNULL(eod.LOAN_OVERDUE_INSTL_NUM,0)as LOAN_OVERDUE_INSTL_NUM,");
				  bufInsSql.append("ISNULL(eod.LOAN_OVERDUE_AMOUNT,0)as LOAN_OVERDUE_AMOUNT,(eod.LOAN_NO_OF_INSTALLMENT-eod.LOAN_BILLED_INSTL_NUM) as LOAN_BALANCE_INSTL_NO,");
				  bufInsSql.append("ISNULL(a.LOAN_BALANCE_INSTL_AMOUNT,0)as LOAN_BALANCE_INSTL_AMOUNT,com_branch_m.BRANCH_DESC,Case when a.LOAN_INSTALLMENT_MODE='A' then 'Yes' else 'No'  End as advanceFlg,");
				  bufInsSql.append("ISNULL(sd.SD_AMOUNT,0)SDAMT,");
				  bufInsSql.append("ISNULL(sd.PENDING_SD_AMOUNT,0) RECEIVED_SDAMT, "); 
				  bufInsSql.append("ISNULL(UNDISAMT.UNDIS_AMT,0)UNDISBURSED,");
				  bufInsSql.append("ISNULL(INTCOMP.AMT,0)INTEREST_COMPONENT,(eod.LOAN_RECEIVED_INSTL_AMOUNT-eod.LOAN_RECEIVED_PRINCIPAL)RECEIVED_INTEREST_AMT "); 
				  bufInsSql.append(" from  cr_loan_dtl a "); 
				  bufInsSql.append(" LEFT JOIN cr_product_m PR ON(PR.PRODUCT_ID=A.LOAN_PRODUCT) ");
				  bufInsSql.append(" join cr_loan_dtl_eod eod on(eod.loan_id=a.loan_id and EOD.mvmt_date='"+cutOffDate+"') ");
				  bufInsSql.append(" JOIN cr_scheme_m SC ON(SC.SCHEME_ID=A.LOAN_SCHEME) ");
				  bufInsSql.append(" left outer join (SELECT  SUM(ADVICE_AMOUNT) SD_AMOUNT,(SUM(ADVICE_AMOUNT-TXN_ADJUSTED_AMOUNT)) PENDING_SD_AMOUNT,LOAN_ID FROM cr_txnadvice_dtl WHERE CHARGE_CODE_ID=103 GROUP BY LOAN_ID)sd on(sd.loan_id=a.loan_id) "); 
				  bufInsSql.append(" LEFT OUTER JOIN (SELECT LOAN_ID,(SUM(");
				  bufInsSql.append("ISNULL(ADVICE_AMOUNT,0))-SUM(");
				  bufInsSql.append("ISNULL(TXN_ADJUSTED_AMOUNT,0)))UNDIS_AMT FROM cr_txnadvice_dtl WHERE CHARGE_CODE_ID=2 AND TXN_TYPE='DIA' GROUP BY LOAN_ID)UNDISAMT ON(UNDISAMT.LOAN_ID=A.LOAN_ID) "); 
				  bufInsSql.append(" left outer join (select SUM(INSTL_AMOUNT)AMT,LOAN_ID FROM cr_repaysch_dtl WHERE REC_STATUS<>'X' GROUP BY LOAN_ID)INTCOMP ON(INTCOMP.loan_id=a.loan_id) "); 
				  bufInsSql.append(" JOIN com_branch_m on(com_branch_m.BRANCH_ID=a.LOAN_BRANCH) "); 
				  bufInsSql.append(" LEFT OUTER JOIN com_industry_m I on(I.INDUSTRY_ID=A.LOAN_INDUSTRY_ID) ");  
				  bufInsSql.append(" LEFT OUTER JOIN com_sub_industry_m SI on(SI.sub_industry_id=A.LOAN_SUB_INDUSTRY_ID ) ");  
				  bufInsSql.append(" JOIN com_address_m b on(b.BPID = a.LOAN_CUSTOMER_ID and COMMUNICATION_ADDRESS='Y') ");
				  bufInsSql.append(" JOIN gcd_customer_m e on(a.LOAN_CUSTOMER_ID = e.CUSTOMER_ID) ");  
				  bufInsSql.append(" LEFT OUTER JOIN generic_master gm on(e.CUSTOMER_CONSTITUTION=gm.value and gm.GENERIC_KEY='CUST_CONSTITUTION') ");  
				  bufInsSql.append(" LEFT OUTER JOIN (SELECT A.LOAN_ID,STUFF((SELECT '|' + B.ASSET_COLLATERAL_CLASS FROM cr_asset_collateral_m  FOR XML PATH ('')), 1, 1, '')as ASSET_COLLATERAL_CLASS,STUFF((SELECT '|' + B.ASSET_COLLATERAL_DESC FROM cr_asset_collateral_m FOR XML PATH ('')), 1, 1, '')as ASSET_COLLATERAL_DESC,STUFF((SELECT '|' + B.ASSET_MANUFATURER_DESC FROM cr_asset_collateral_m FOR XML PATH ('')), 1, 1, '')as MANUFATURER_DESC,STUFF((SELECT '|' + Case when B.ASSET_NEW_OLD='N' then 'New' else 'Old'  End FROM cr_asset_collateral_m FOR XML PATH ('')), 1, 1, '')as ASSET_NEW_OLD,sum(");
				  bufInsSql.append("ISNULL(B.ASSET_COLLATERAL_VALUE,0))as ASSET_COLLATERAL_VALUE FROM cr_loan_collateral_m A JOIN cr_asset_collateral_m B ON(A.ASSETID=B.ASSET_ID) GROUP BY A.LOAN_ID,B.ASSET_COLLATERAL_CLASS,B.ASSET_COLLATERAL_DESC,B.ASSET_MANUFATURER_DESC,B.ASSET_NEW_OLD) collt on(collt.loan_id=a.LOAN_ID) ");  
				  bufInsSql.append(" left outer join (SELECT PDC_LOAN_ID,COUNT(1) as PDC FROM cr_pdc_instrument_dtl pd WHERE pd.pdc_status='A' group by PDC_LOAN_ID ) as p on(p.PDC_LOAN_ID=a.LOAN_ID) ");  
				  bufInsSql.append(" left outer join (select loan_id,");
				  bufInsSql.append(dbo);
				  bufInsSql.append("DATE_FORMAT(max(DISBURSAL_DATE),'%d-%m-%Y')disDate from cr_loan_disbursal_dtl where rec_status='A' and DISBURSAL_DATE<='"+cutOffDate+"' group by loan_id)disbDate on(disbDate.loan_id=a.LOAN_ID) "); 
				  bufInsSql.append(" left outer join (select loan_id,");
				  bufInsSql.append(dbo);
				  bufInsSql.append("DATE_FORMAT(instl_date,'%d-%m-%Y')fdate,DATENAME(month, instl_date)first_emi_month from cr_repaysch_dtl where instl_no=1 )firEmi1 on(firEmi1.loan_id=a.LOAN_ID) "); 
				  bufInsSql.append(" left outer join (select loan_id,");
				  bufInsSql.append(dbo);
				  bufInsSql.append("DATE_FORMAT(instl_date,'%d-%m-%Y')fdate,DATENAME(month, instl_date)first_emi_month from cr_repaysch_dtl where instl_no=2 )firEmi2 on(firEmi2.loan_id=a.LOAN_ID) "); 
				  bufInsSql.append(" LEFT OUTER JOIN (select loan_id,");
				  bufInsSql.append(dbo);
				  bufInsSql.append("DATE_FORMAT(max(instl_date),'%d-%m-%Y')as last_emi,DATENAME(month, max(INSTL_DATE))as last_emi_month from cr_repaysch_dtl group by loan_id )emi on(emi.loan_id=a.LOAN_ID) ");  
				  bufInsSql.append(" LEFT OUTER JOIN (select a.loan_id,iif(a.LOAN_INSTALLMENT_MODE='A',");
				  bufInsSql.append("ISNULL(b.INSTL_AMOUNT1,0)+");
				  bufInsSql.append("ISNULL(c.INSTL_AMOUNT2,0),");
				  bufInsSql.append("ISNULL(c.INSTL_AMOUNT2,0))as INSTL_AMOUNT from cr_loan_dtl a left join(select loan_id,");
				  bufInsSql.append("ISNULL(INSTL_AMOUNT,0)as INSTL_AMOUNT1 from cr_repaysch_dtl  where INSTL_NO = '1' group by loan_id,INSTL_AMOUNT)b on(a.loan_id=b.loan_id)left join(select sum(");
				  bufInsSql.append("ISNULL(INSTL_AMOUNT,0))as INSTL_AMOUNT2,loan_id from cr_repaysch_dtl where cr_repaysch_dtl.ADV_FLAG='Y' group by loan_id)c on(c.loan_id=b.loan_id)) instAmount on(instAmount.loan_id=a.LOAN_ID) "); 
				  bufInsSql.append(" LEFT OUTER JOIN (select loan_id,count(1)as Seasoning from cr_repaysch_dtl where bill_flag = 'Y' and ADV_FLAG='N'  and REC_STATUS<>'X' and INSTL_DATE<='"+cutOffDate+"' group by loan_id )as soning on(soning.loan_id=a.LOAN_ID) ");  
				  bufInsSql.append(" LEFT OUTER JOIN (select A.LOAN_ID,SUM(");
				  bufInsSql.append("ISNULL(INSTL_AMOUNT,0))as LoanAdvanceEMIAmount from cr_repaysch_dtl A,cr_loan_dtl B WHERE  A.LOAN_ID=B.LOAN_ID  AND (ADV_FLAG='Y' OR (INSTL_NO=1 AND B.LOAN_INSTALLMENT_MODE='A')) group by A.loan_id, INSTL_AMOUNT )AS EMIAmount on(EMIAmount.loan_id=a.LOAN_ID) ");  
				  bufInsSql.append(" LEFT OUTER JOIN (SELECT LOAN_ID,");
				  bufInsSql.append("ISNULL(C.APPL_FIN_DOC,'NA')APPL_FIN_DOC,");
				  bufInsSql.append("ISNULL(D.GUR_FIN_DOC,'NA')GUR_FIN_DOC FROM cr_loan_dtl A JOIN cr_deal_dtl B ON(A.LOAN_DEAL_ID=B.DEAL_ID) ");
				  bufInsSql.append(" LEFT OUTER JOIN (SELECT STUFF((SELECT '|' + B.DOC_DESC FROM cr_document_child_m  FOR XML PATH ('')), 1, 1, '')as APPL_FIN_DOC,A.TXNID FROM cr_document_dtl A JOIN cr_document_child_m B ON('A'='A') WHERE CHARINDEX( concat('|',B.DOC_CHILD_ID,'|'), concat( '|',A.DOC_CHILD_IDS)) > 0 AND A.DOC_TYPE='PRAPPL' and A.DOC_ID in(13,14) AND TXN_TYPE='DC' GROUP BY A.TXNID,B.DOC_DESC)C ON(C.TXNID=B.DEAL_ID) ");  
				  bufInsSql.append(" LEFT OUTER JOIN (SELECT STUFF((SELECT '|' + B.DOC_DESC FROM cr_document_child_m  FOR XML PATH ('')), 1, 1, '')as GUR_FIN_DOC,A.TXNID FROM cr_document_dtl A JOIN cr_document_child_m B ON('A'='A') WHERE CHARINDEX( concat('|',B.DOC_CHILD_ID,'|'), concat( '|',A.DOC_CHILD_IDS)) > 0 AND A.DOC_TYPE='GUARANTOR' and A.DOC_ID in(13,14) AND TXN_TYPE='DC' GROUP BY A.TXNID,B.DOC_DESC)D ON(D.TXNID=B.DEAL_ID))FIN on(FIN.LOAN_ID=a.LOAN_ID) ");  
				  bufInsSql.append(" LEFT OUTER JOIN (SELECT LOAN_ID,");
				  bufInsSql.append("ISNULL(C.APPL_ADDRESS_PROOF,'NA')APPL_ADDRESS_PROOF,");
				  bufInsSql.append("ISNULL(D.APPL_ID_PROOF,'NA')APPL_ID_PROOF,");
				  bufInsSql.append("ISNULL(E.APPL_SIGNATURE_PROOF,'NA')APPL_SIGNATURE_PROOF FROM cr_loan_dtl A JOIN cr_deal_dtl B ON(A.LOAN_DEAL_ID=B.DEAL_ID) "); 
				  bufInsSql.append(" LEFT OUTER JOIN (SELECT STUFF((SELECT '|' + B.DOC_DESC FROM cr_document_child_m  FOR XML PATH ('')), 1, 1, '')as APPL_ADDRESS_PROOF,A.TXNID FROM cr_document_dtl A JOIN cr_document_child_m B ON('A'='A') WHERE CHARINDEX( concat('|',B.DOC_CHILD_ID,'|'), concat( '|',A.DOC_CHILD_IDS)) > 0 AND A.DOC_TYPE='PRAPPL' and A.DOC_ID=5 AND TXN_TYPE='DC' GROUP BY A.TXNID,B.DOC_DESC)C ON(C.TXNID=B.DEAL_ID) ");  
				  bufInsSql.append(" LEFT OUTER JOIN (SELECT STUFF((SELECT '|' + B.DOC_DESC FROM cr_document_child_m  FOR XML PATH ('')), 1, 1, '')as APPL_ID_PROOF,A.TXNID FROM cr_document_dtl A JOIN cr_document_child_m B ON('A'='A') WHERE CHARINDEX( concat('|',B.DOC_CHILD_ID,'|'), concat( '|',A.DOC_CHILD_IDS)) > 0 AND A.DOC_TYPE='PRAPPL' and A.DOC_ID IN(6,2)AND TXN_TYPE='DC' GROUP BY A.TXNID,B.DOC_DESC)D ON(D.TXNID=B.DEAL_ID) ");  
				  bufInsSql.append(" LEFT OUTER JOIN (SELECT STUFF((SELECT '|' + B.DOC_DESC FROM cr_document_child_m  FOR XML PATH ('')), 1, 1, '')as APPL_SIGNATURE_PROOF,A.TXNID FROM cr_document_dtl A JOIN cr_document_child_m B ON('A'='A') WHERE CHARINDEX( concat('|',B.DOC_CHILD_ID,'|'), concat( '|',A.DOC_CHILD_IDS)) > 0 AND A.DOC_TYPE='PRAPPL' and A.DOC_ID=22 AND TXN_TYPE='DC' GROUP BY A.TXNID,B.DOC_DESC)E ON(E.TXNID=B.DEAL_ID))appl on(appl.LOAN_ID=a.LOAN_ID) ");  
				  bufInsSql.append(" LEFT OUTER JOIN (SELECT LOAN_ID,");
				  bufInsSql.append("ISNULL(C.GUR_ADDRESS_PROOF,'NA')GUR_ADDRESS_PROOF,");
				  bufInsSql.append("ISNULL(D.GUR_ID_PROOF,'NA')GUR_ID_PROOF,");
				  bufInsSql.append("ISNULL(E.GUR_SIGNATURE_PROOF,'NA')GUR_SIGNATURE_PROOF FROM cr_loan_dtl A JOIN cr_deal_dtl B ON(A.LOAN_DEAL_ID=B.DEAL_ID) ");  
				  bufInsSql.append(" LEFT OUTER JOIN (select STUFF((SELECT '|' + B.DOC_DESC FROM cr_document_child_m  FOR XML PATH ('')), 1, 1, '')as GUR_ADDRESS_PROOF,A.txnid from (select STUFF((SELECT '' + DOC_child_ids FROM cr_document_dtl  FOR XML PATH ('')), 1, 1, '')as childIds,txnid from cr_document_dtl where DOC_TYPE='GUARANTOR' and DOC_ID=5 AND TXN_TYPE='DC' group by txnid )A join cr_document_child_m B ON('A'='A') WHERE CHARINDEX( concat('|',B.DOC_CHILD_ID,'|'), concat( '|',A.childIds)) > 0 group by A.txnid,B.DOC_DESC)C ON(C.TXNID=B.DEAL_ID) ");   
				  bufInsSql.append(" LEFT OUTER JOIN (select STUFF((SELECT '|' + B.DOC_DESC FROM cr_document_child_m  FOR XML PATH ('')), 1, 1, '')as GUR_ID_PROOF,A.txnid from (select STUFF((SELECT '' + DOC_child_ids FROM cr_document_dtl  FOR XML PATH ('')), 1, 1, '')as childIds,txnid from cr_document_dtl where DOC_TYPE='GUARANTOR' and DOC_ID IN(6,2) group by txnid )A join cr_document_child_m B ON('A'='A') WHERE CHARINDEX( concat('|',B.DOC_CHILD_ID,'|'), concat( '|',A.childIds)) > 0 group by A.txnid,B.DOC_DESC)D ON(D.TXNID=B.DEAL_ID) "); 
				  bufInsSql.append(" LEFT OUTER JOIN (select STUFF((SELECT '|' + B.DOC_DESC FROM cr_document_child_m  FOR XML PATH ('')), 1, 1, '')as GUR_SIGNATURE_PROOF,A.txnid from (select STUFF((SELECT '' + DOC_child_ids FROM cr_document_dtl  FOR XML PATH ('')), 1, 1, '')as childIds,txnid from cr_document_dtl where DOC_TYPE='GUARANTOR' and DOC_ID=22 AND TXN_TYPE='DC' group by txnid )A join cr_document_child_m B ON('A'='A') WHERE CHARINDEX( concat('|',B.DOC_CHILD_ID,'|'), concat( '|',A.childIds)) > 0 group by A.txnid,B.DOC_DESC)E ON(E.TXNID=B.DEAL_ID))gur on(gur.LOAN_ID=a.LOAN_ID) "); 
				  bufInsSql.append(" JOIN com_branch_m C ON  C.branch_id=a.loan_branch LEFT JOIN  com_state_m D on C.state_id=D.state_id LEFT JOIN cr_securitization_pool_dtl Z ON Z.LOAN_ID=A.LOAN_ID ");
				  bufInsSql.append(" where a.rec_status='A' and a.DISBURSAL_STATUS='F'  AND ISNULL(Z.REC_STATUS,' ') in('P','F') ");
				   }
	 			 
			  bufInsSql.append(" AND ISNULL(a.SALE_OFF_FLAG,' ') not in('R','S')");
			  if(!legacyLoan.equalsIgnoreCase("")) 
			        bufInsSql.append(" AND a.LOAN_REFERENCE_NO ='"+legacyLoan+"'");
			
			   if((!(loanAccountNumber.toString().equalsIgnoreCase("")))) 
			        bufInsSql.append(" AND a.LOAN_NO ='"+loanAccountNumber+"'");
			   if((!(customerName.toString().equalsIgnoreCase("")))) 
			        bufInsSql.append(" AND e.CUSTOMER_NAME like '%"+customerName+"%'");
			   if((!(customerCategory.toString().equalsIgnoreCase("")))) 
			       bufInsSql.append(" AND E.CUSTOMER_CATEGORY ='"+customerCategory+"'");
			   if((!(customerSegment.toString().equalsIgnoreCase("")))) 
			       bufInsSql.append(" AND E.CUSTOMER_BUSINESS_SEGMENT ='"+customerSegment+"'");
			     
			   if((!(disbursalDate1.toString().equalsIgnoreCase(""))))
			        bufInsSql.append(" AND disbDate.disDate >='"+disbursalDate1+"'");
			   if((!(disbursalDate2.toString().equalsIgnoreCase("")))) 
			   	   bufInsSql.append(" AND disbDate.disDate <='"+disbursalDate2+"'");
			   if((!(maturityDate1.toString().equalsIgnoreCase(""))))
			   { 
			      bufInsSql.append(" AND A.LOAN_MATURITY_DATE >= ");
			   	  bufInsSql.append(dbo);
			      bufInsSql.append("str_to_date('"+maturityDate1+"','"+dateFormatWithTime+"')");
			   }
			   if((!(maturityDate2.toString().equalsIgnoreCase(""))))
			   {
			       bufInsSql.append(" AND A.LOAN_MATURITY_DATE <=");
			       bufInsSql.append(dbo);
			       bufInsSql.append("str_to_date('"+maturityDate2+"','"+dateFormatWithTime+"')");
			   }
			   
			   if((!(loanAmount1.toString().equalsIgnoreCase("")))) 
			   	   bufInsSql.append(" AND A.LOAN_LOAN_AMOUNT >="+CommonFunction.decimalNumberConvert(CommonFunction.checkNull(loanAmount1).trim()));
			  
			   if((!(loanAmount2.toString().equalsIgnoreCase(""))))
			       bufInsSql.append(" AND A.LOAN_LOAN_AMOUNT <="+CommonFunction.decimalNumberConvert(CommonFunction.checkNull(loanAmount2).trim()));
			   
			   if((!(installmentAmount1.toString().equalsIgnoreCase(""))))
			      bufInsSql.append(" AND instAmount.INSTL_AMOUNT >="+CommonFunction.decimalNumberConvert(CommonFunction.checkNull(installmentAmount1).trim()));
			   if((!(installmentAmount2.toString().equalsIgnoreCase(""))))
			        bufInsSql.append(" AND instAmount.INSTL_AMOUNT <="+CommonFunction.decimalNumberConvert(CommonFunction.checkNull(installmentAmount2).trim()));
			    
			   if((!(interestRate1.toString().equalsIgnoreCase(""))))
			        bufInsSql.append(" AND A.LOAN_FINAL_RATE >="+CommonFunction.decimalNumberConvert(CommonFunction.checkNull(interestRate1).trim()));
			   if((!(interestRate2.toString().equalsIgnoreCase(""))))
			       bufInsSql.append(" AND A.LOAN_FINAL_RATE <="+CommonFunction.decimalNumberConvert(CommonFunction.checkNull(interestRate2).trim()));			     
			
			   if((!(rateType.toString().equalsIgnoreCase(""))))
			       bufInsSql.append(" AND A.LOAN_RATE_TYPE ='"+rateType+"'");
			    
			   if((!(amountOutstanding1.toString().equalsIgnoreCase(""))))
			   {
				   if(cutOffDate.equalsIgnoreCase("0000-00-00"))
					   bufInsSql.append(" AND a.LOAN_BALANCE_PRINCIPAL >="+CommonFunction.decimalNumberConvert(CommonFunction.checkNull(amountOutstanding1).trim()));
				   else
					   bufInsSql.append(" AND eod.LOAN_BALANCE_PRINCIPAL >="+CommonFunction.decimalNumberConvert(CommonFunction.checkNull(amountOutstanding1).trim()));
			   }
			   if((!(amountOutstanding2.toString().toString().equalsIgnoreCase(""))))
			   {
				   if(cutOffDate.equalsIgnoreCase("0000-00-00"))
					   bufInsSql.append(" AND a.LOAN_BALANCE_PRINCIPAL <="+CommonFunction.decimalNumberConvert(CommonFunction.checkNull(amountOutstanding2).trim()));
				   else
					   bufInsSql.append(" AND eod.LOAN_BALANCE_PRINCIPAL <="+CommonFunction.decimalNumberConvert(CommonFunction.checkNull(amountOutstanding2).trim()));
			   }
			   
			   if((!(totalInstallments1.toString().equalsIgnoreCase(""))))
			   {
				   if(cutOffDate.equalsIgnoreCase("0000-00-00"))
					   bufInsSql.append(" AND A.LOAN_NO_OF_INSTALLMENT >="+CommonFunction.decimalNumberConvert(CommonFunction.checkNull(totalInstallments1).trim()));
				   else
					   bufInsSql.append(" AND eod.LOAN_NO_OF_INSTALLMENT >="+CommonFunction.decimalNumberConvert(CommonFunction.checkNull(totalInstallments1).trim()));
			   }
			   if((!(totalInstallments2.toString().equalsIgnoreCase(""))))
			   {
				   if(cutOffDate.equalsIgnoreCase("0000-00-00"))
					   bufInsSql.append(" AND A.LOAN_NO_OF_INSTALLMENT <="+CommonFunction.decimalNumberConvert(CommonFunction.checkNull(totalInstallments2).trim()));
				   else
					   bufInsSql.append(" AND eod.LOAN_NO_OF_INSTALLMENT <="+CommonFunction.decimalNumberConvert(CommonFunction.checkNull(totalInstallments2).trim()));
			   }  
			   if((!(installmentReceived1.toString().equalsIgnoreCase(""))))
			   {
				   if(cutOffDate.equalsIgnoreCase("0000-00-00"))
					   bufInsSql.append(" AND a.LOAN_RECEIVED_PRINCIPAL >="+CommonFunction.decimalNumberConvert(CommonFunction.checkNull(installmentReceived1).trim()));
				   else
					   bufInsSql.append(" AND eod.LOAN_RECEIVED_PRINCIPAL >="+CommonFunction.decimalNumberConvert(CommonFunction.checkNull(installmentReceived1).trim()));
			   }
			   if((!(installmentReceived2.toString().equalsIgnoreCase(""))))
			   {
				   if(cutOffDate.equalsIgnoreCase("0000-00-00"))
					   bufInsSql.append(" AND a.LOAN_RECEIVED_PRINCIPAL <="+CommonFunction.decimalNumberConvert(CommonFunction.checkNull(installmentReceived2).trim()));
				   else
					   bufInsSql.append(" AND eod.LOAN_RECEIVED_PRINCIPAL <="+CommonFunction.decimalNumberConvert(CommonFunction.checkNull(installmentReceived2).trim()));
			   }
			     
			   if((!(overDueAmount1.toString().equalsIgnoreCase(""))))
			   {
				   if(cutOffDate.equalsIgnoreCase("0000-00-00"))
					   bufInsSql.append(" AND a.LOAN_OVERDUE_PRINCIPAL >="+CommonFunction.decimalNumberConvert(CommonFunction.checkNull(overDueAmount1).trim()));
				   else
					   bufInsSql.append(" AND eod.LOAN_OVERDUE_PRINCIPAL >="+CommonFunction.decimalNumberConvert(CommonFunction.checkNull(overDueAmount1).trim()));
			   }
			   if((!(overDueAmount2.toString().equalsIgnoreCase(""))))
			   {
				   if(cutOffDate.equalsIgnoreCase("0000-00-00"))
					   bufInsSql.append(" AND a.LOAN_OVERDUE_PRINCIPAL <="+CommonFunction.decimalNumberConvert(CommonFunction.checkNull(overDueAmount2).trim()));
				   else
					   bufInsSql.append(" AND eod.LOAN_OVERDUE_PRINCIPAL <="+CommonFunction.decimalNumberConvert(CommonFunction.checkNull(overDueAmount2).trim()));
			   }
			   if((!(DPD1.toString().equalsIgnoreCase("")))) 
			   {
				   if(cutOffDate.equalsIgnoreCase("0000-00-00"))
					   bufInsSql.append(" AND A.LOAN_DPD >="+CommonFunction.decimalNumberConvert(CommonFunction.checkNull(DPD1).trim()));
				   else
					   bufInsSql.append(" AND eod.LOAN_DPD >="+CommonFunction.decimalNumberConvert(CommonFunction.checkNull(DPD1).trim()));
			   }
			   if((!(DPD2.toString().equalsIgnoreCase(""))))
			   {
				   if(cutOffDate.equalsIgnoreCase("0000-00-00"))
					   bufInsSql.append(" AND A.LOAN_DPD <="+CommonFunction.decimalNumberConvert(CommonFunction.checkNull(DPD2).trim()));
				   else
					   bufInsSql.append(" AND eod.LOAN_DPD <="+CommonFunction.decimalNumberConvert(CommonFunction.checkNull(DPD2).trim()));
			   }
    			   if((!(stateId.toString().equalsIgnoreCase("")))) 
			        bufInsSql.append(" AND z.state_id ='"+stateId+"'");
    
			   
			   if((!(instMode.toString().equalsIgnoreCase("")))) 
			        bufInsSql.append(" AND a.LOAN_INSTALLMENT_MODE ='"+instMode+"'");
			   
			   
			   
 
			 if(!(lbxValue.toString().equalsIgnoreCase("")))
			 {	 
				 for(int i=0;i<constitutionValue.length;i++)
					 consValue = consValue + "'" + constitutionValue[i] + "',";  
				 String  tmp = consValue.toString();
			     tmp = (tmp).substring(0,(tmp.length()-1));
			     bufInsSql.append(" and E.CUSTOMER_CONSTITUTION in ("+tmp+")");
			     tmp=null;
				 consValue=null;
			 }   
			 if(!(lbxAssetCollId.toString().equalsIgnoreCase("")))
			 {	 
				 for(int i=0;i<assetCollValue.length;i++)
					 assetValue = assetValue  + "'" +assetCollValue[i] + "',";  
				
			     String  tmp = assetValue.toString();
			     tmp = (tmp).substring(0,(tmp.length()-1));
			     bufInsSql.append(" and ASSET_COLLATERAL_CLASS in ("+tmp+")");
			     tmp=null;
				 assetValue=null; 
			 }
			 if(!(lbxIndustry.toString().equalsIgnoreCase("")))
			 {	 
				 for(int i=0;i<industryValue.length;i++)
					 indsValue = indsValue + "'" + industryValue[i] + "',";  
				 String  tmp = indsValue.toString();
			     tmp = (tmp).substring(0,(tmp.length()-1));
			     bufInsSql.append(" and A.LOAN_INDUSTRY_ID in ("+tmp+")");
			     tmp=null;
				 indsValue=null;
			 }
			 if(!(lbxSubIndustry.toString().equalsIgnoreCase("")))
			 {	 
				 for(int i=0;i<subIndustryValue.length;i++)
					 subIndsValue = subIndsValue  + "'" +subIndustryValue[i] + "',";  
				 String  tmp = subIndsValue.toString();
			     tmp = (tmp).substring(0,(tmp.length()-1));
			     bufInsSql.append(" and A.LOAN_SUB_INDUSTRY_ID in ("+tmp+")");
			     tmp=null;
				 subIndsValue=null;
			 } 
			if(!(lbxProductID.toString().equalsIgnoreCase("")))
			{	 
				 for(int i=0;i<productValue.length;i++)
					 prodValue = prodValue  + "'" +productValue[i] + "',";  
				String  tmp = prodValue.toString();
			    tmp = (tmp).substring(0,(tmp.length()-1));
			    bufInsSql.append(" and A.LOAN_PRODUCT in ("+tmp+")");
			    tmp=null;
			    prodValue=null;
			}
			if(!(lbxScheme.toString().equalsIgnoreCase("")))
			{	 
				 for(int i=0;i<schemeValue.length;i++)
					schValue = schValue  + "'" +schemeValue[i] + "',";  
				String  tmp = schValue.toString();
			   tmp = (tmp).substring(0,(tmp.length()-1));
			   bufInsSql.append(" and A.LOAN_SCHEME in ("+tmp+")");
			   schValue=null;
			   tmp=null;
			}
		 if(!(lbxBranchIds.toString().equalsIgnoreCase("")))
			 {	 
				 for(int i=0;i<branchValue.length;i++)
					 branValue = branValue + "'" + branchValue[i] + "',";  
				 String  tmp = branValue.toString();
			     tmp = (tmp).substring(0,(tmp.length()-1));
			     bufInsSql.append(" and A.loan_branch in ("+tmp+")");
			     tmp=null;
				 consValue=null;
			 } 
		bufInsSql.append("  order by a.LOAN_ID");
			logger.info("Query  : "+bufInsSql.toString());
			
			mainList = ConnectionReportDumpsDAO.sqlSelect(bufInsSql.toString());
	        sbAppendToSQLCount=null;
	               
	  }catch(Exception e)
	  {e.printStackTrace();}
	  finally
	  {
    	  loanAccountNumber=null;
    	  customerName=null;
    	  customerCategory=null;
	      customerSegment=null;
	      constitution=null;
	      assetCategory=null;
	      industry=null;
	      subIndustry=null;
	      product=null;
	      scheme=null;
	      disbursalDate1=null;
	      disbursalDate2=null;
	      maturityDate2=null;
	      maturityDate1=null;
	      loanAmount1=null;
	      loanAmount2=null;
	      installmentAmount1=null;
	      installmentAmount2=null;
	      interestRate1=null;
	      interestRate2=null;
	      rateType=null;
	      amountOutstanding1=null;
	      amountOutstanding2=null;
	      totalInstallments1=null;   
	      totalInstallments2=null;
	      installmentReceived1=null;
	      installmentReceived2=null;
	      overDueAmount1=null;
	      overDueAmount2=null;
	      DPD1=null;
	      DPD2=null;
	      lbxValue=null;
	      lbxAssetCollId=null;
	      lbxIndustry=null;
	      lbxSubIndustry=null;
	      lbxProductID=null;
	      lbxScheme=null;
	  }
	  return mainList;		
	}
	public String getCutOffDate(String date) 
	{
		 date = DateFormator.decreaseMonth(date);
		 date = DateFormator.lastDateOfMonth(date);
		 return date;
	}
	
	public String getCutoffDate() 
	{
		StringBuilder bufInsSql =    new StringBuilder();	 
		/*bufInsSql.append(" select group_concat(distinct ");
		bufInsSql.append(dbo);
		bufInsSql.append("DATE_FORMAT(MVMT_DATE,'%d-%m-%Y') separator '|')as result from cr_loan_dtl_eod order by MVMT_DATE");*/
		bufInsSql.append(" SELECT STUFF((SELECT '|' + ");
		bufInsSql.append(dbo);
		bufInsSql.append("DATE_FORMAT(MVMT_DATE,'%d-%m-%Y') FROM cr_loan_dtl_eod FOR XML PATH ('')), 1, 1, '')");
	    logger.info("Query  to get list off cut_off_date   : "+bufInsSql.toString());
	    String cutOffDate="";
	    try
	    {
	    	cutOffDate=ConnectionReportDumpsDAO.singleReturn(bufInsSql.toString());
	    }
	    catch(Exception e)
		{e.printStackTrace();}	
	    logger.info("cutOffDate   :   "+cutOffDate);
		return cutOffDate;
	}
}
