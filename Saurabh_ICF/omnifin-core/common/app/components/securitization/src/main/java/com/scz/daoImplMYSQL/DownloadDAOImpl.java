package com.scz.daoImplMYSQL;

import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.scz.dao.DownloadDAO;
import com.scz.vo.PoolCreationForCMVO;
import com.connect.CommonFunction;
import com.connect.ConnectionReportDumpsDAO;
import com.scz.hibernateUtil.HibernateSessionFactory;
import com.scz.DateFormator;
import com.scz.vo.PoolDownloadVO;

public class DownloadDAOImpl implements DownloadDAO{
	
	private static final Logger logger = Logger.getLogger(DownloadDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.utill");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	
	public ArrayList generatePoolReport(PoolCreationForCMVO poolvo,String Userid)
	{		
		
		logger.info("In generatePoolReport().");		
	     StringBuilder loanAccountNumber=new StringBuilder();
	     StringBuilder customerName=new StringBuilder();
	     StringBuilder customerCategory=new StringBuilder();
	     StringBuilder customerSegment=new StringBuilder();
	     StringBuilder constitution=new StringBuilder();
	     //StringBuilder assetCategory=new StringBuilder();
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
	     
	     
	     /*.......................ADDED BY RUDRA START....................*/
	     
	     StringBuilder lbxBranchId=new StringBuilder();
	     StringBuilder instalmentMode = new StringBuilder();
	     StringBuilder repoStock = new StringBuilder();
	     StringBuilder cFilter = new StringBuilder();
	     StringBuilder deffralCases = new StringBuilder();
	     StringBuilder ltv1 = new StringBuilder();
	     StringBuilder ltv2 = new StringBuilder();
	     
	     StringBuilder sectorType = new StringBuilder();
	     StringBuilder remainingTenure = new StringBuilder();
	     StringBuilder frequency = new StringBuilder();
	     
	     StringBuilder assetCost1 = new StringBuilder();
	     StringBuilder assetCost2 = new StringBuilder();
	     
	     StringBuilder assetCategory=new StringBuilder();
	     
	     StringBuilder branchList=new StringBuilder();
	     StringBuilder stateList=new StringBuilder();
	     
	     StringBuilder lbxStateIds=new StringBuilder();
	     StringBuilder lbxBranchIds=new StringBuilder();
	     
	     /*.......................ADDED BY RUDRA E....................*/

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
			  
			  branchList.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getBranchList()).trim()));
			  logger.info("  branchList"+branchList);
			  
			  stateList.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getStateList()).trim()));
			  logger.info("  stateList"+stateList);
			  
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
			  
			  lbxStateIds.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getLbxStateIds()).trim()));
			  logger.info("  lbxStateIds"+lbxStateIds);
			  
			  lbxBranchIds.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getLbxBranchIds()).trim()));
			  logger.info("  lbxBranchIds"+lbxBranchIds);
			  
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
			 
			 
			  /*...........................ADDED BY RUDRA STARTS.............................*/
			  
			  lbxBranchId.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getLbxBranchId()).trim()));
			  logger.info("  lbxProductID"+lbxBranchId);
			  
			  instalmentMode.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getInstalmentMode()).trim()));
			  logger.info("  lbxScheme"+instalmentMode);
			  
			  repoStock.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getRepoStock()).trim()));
			  logger.info("  lbxScheme"+repoStock);
			  
			  cFilter.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getClassificationFilter()).trim()));
			  logger.info("  lbxScheme"+cFilter);
			  
			  /*deffralCases.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getDeffralCase()).trim()));
			  logger.info("  lbxScheme"+deffralCases);*/
			  
			  ltv1.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getLtv1()).trim()));
			  logger.info("  lbxScheme"+ltv1);
			  
			  ltv2.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getLtv2()).trim()));
			  logger.info("  lbxScheme"+ltv2);
			  
			  sectorType.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getSectorType()).trim()));
			  logger.info("  lbxScheme"+sectorType);
			  
			  remainingTenure.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getRemainingTenure()).trim()));
			  logger.info("  lbxScheme"+remainingTenure);
			  
			  frequency.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getFrequency()).trim()));
			  logger.info("  lbxScheme"+frequency);
			  
			  assetCost1.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getAssetCost1()).trim()));
			  logger.info("  lbxScheme"+assetCost1);
			  
			  assetCost2.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getAssetCost2()).trim()));
			  logger.info("  lbxScheme"+assetCost2);
			  
			  /*...........................ADDED BY RUDRA ENDs.............................*/
			  
			  
			  String cutOffDate=CommonFunction.checkNull(poolvo.getCutOffDate());   
			  logger.info("  cutOffDate    :   "+cutOffDate);
			  String[] constitutionValue = lbxValue.toString().split("/");
			  String[] assetCollValue = lbxAssetCollId.toString().split("/");
			  
			  String[] stateCollValue = lbxStateIds.toString().split("/");
			  String[] branchCollValue = lbxBranchIds.toString().split("/");
			  
			  String[] industryValue = lbxIndustry.toString().split("/");
			  String[] subIndustryValue = lbxSubIndustry.toString().split("/");
			  String[] productValue = lbxProductID.toString().split("/");
			  String[] schemeValue = lbxScheme.toString().split("/");
			  String[] branchValue = lbxBranchId.toString().split("/");
			  
			  String stateId=CommonFunction.checkNull(poolvo.getTxtStateCode());
			  logger.info("  stateId  :  "+stateId);
			  
			  String instMode=CommonFunction.checkNull(poolvo.getInstMode());
			  logger.info("  instMode  :  "+instMode);
			  
			  String consValue="";
			  String assetValue="";
			  String stateValue="";
			  String branchValueList="";
			  String indsValue="";
			  String subIndsValue="";
			  String prodValue="";
			  String schValue="";			
			  String branValue="";	
			  
			  boolean appendSQL=false;
			  StringBuilder sbAppendToSQLCount=new StringBuilder();
			  StringBuilder bufInsSql =    new StringBuilder();	
			  logger.info("  cutOffDate    :   "+cutOffDate);
			  //Sidharth Change the QUERY INTO PROCEDURE BEGIN 
			  String loan_ref_no="";
			  String branchid="";
			  String Loan_no="";
			  String custName="";
			  String custCategory="";
			  String custSegment="";
			  String disdateFrom="";
			  String disdateTo="";
			  String matuDateFrom="";
			  String matuDateTo="";
			  String LoanAmtFrom="";
			  String LoanAmtTo="";
			  String InstAmtFrom="";
			  String InstAmtTo="";
			  String IntrestRateFrom="";
			  String IntrestRateTo="";
			  String LoanRateType="";
			  String LoanBalPrinFrom="";
			  String LoanBalPrinTo="";
			  String LoanNoOfInstFrom="";
			  String LoanNoOfInstTo="";
			  String LoanRecPrinFrom="";
			  String LoanRecPrinTo="";
			  String LoanOverPrinFrom="";
			  String LoanOverPrinTo="";
			  String LoanDPDFrom="";
			  String LoanDPDTo="";
			  String CustConst="";
			  String AssetcolClass="";
			  String LoanIndus="";
			  String LoanSubIndus="";
			  String LoanProdu="";
			  String LoanScheme="";
			  String Branch="";
			  
			  String StatecolClass="";
			  String BranchcolClass="";
			  
			   /*..........................ADDED BY RUDRA...............................*/
				  String State="";
				  String InstallmentMode="";
				  String RepoStock="";
				  String CFilter="";
				  //String DeffralCases ="";
				  String Ltv1 ="";
				  String Ltv2 ="";
				  
				  String SectorType ="";
				  String RemainingTenure ="";
				  String Frequency ="";
				  String AssetCost1 ="";
				  String AssetCost2 ="";
			    
			   	if((instalmentMode.toString().equalsIgnoreCase(""))){
					InstallmentMode="A";
				}else{
					InstallmentMode=instalmentMode.toString();
				}
			    
			    if((repoStock.toString().equalsIgnoreCase(""))){
			    	RepoStock="A";
				}else{
					RepoStock=repoStock.toString();
				}
			    
			    if((cFilter.toString().equalsIgnoreCase(""))){
			    	CFilter="A";
				}else{
					CFilter=cFilter.toString();
				}
			    
			   /* if((deffralCases.toString().equalsIgnoreCase(""))){
			    	DeffralCases="A";
				}else{
					DeffralCases=deffralCases.toString();
				}*/
			    
			    if((ltv1.toString().equalsIgnoreCase(""))){
			    	Ltv1="A";
				}else{
					Ltv1=ltv1.toString();
				}
			    
			    if((ltv2.toString().equalsIgnoreCase(""))){
			    	Ltv2="A";
				}else{
					Ltv2=ltv2.toString();
				}
			    
			    
			    
			    if((sectorType.toString().equalsIgnoreCase(""))){
			    	SectorType="A";
				}else{
					SectorType=sectorType.toString();
				}
			    
			    if((remainingTenure.toString().equalsIgnoreCase(""))){
			    	RemainingTenure="A";
				}else{
					RemainingTenure=remainingTenure.toString();
				}
			    
			    if((frequency.toString().equalsIgnoreCase(""))){
			    	Frequency="A";
				}else{
					Frequency=frequency.toString();
				}
			    
			    if((assetCost1.toString().equalsIgnoreCase(""))){
			    	AssetCost1="A";
				}else{
					AssetCost1=assetCost1.toString();
				}
			    
			    if((assetCost2.toString().equalsIgnoreCase(""))){
			    	AssetCost2="A";
				}else{
					AssetCost2=assetCost2.toString();
				}
			    
			    /*..........................ADDED BY RUDRA.........................*/
			    
					if(legacyLoan.equalsIgnoreCase(""))  {
					loan_ref_no ="A"; }
					else {
					loan_ref_no =legacyLoan;  }
					if((loanAccountNumber.toString().equalsIgnoreCase(""))) {
					Loan_no="A"; }
					else {
					Loan_no=loanAccountNumber.toString();  
					}
					if((customerName.toString().equalsIgnoreCase(""))){
					custName="1";  }
					else {
					custName=customerName.toString();
					}
					if((customerCategory.toString().equalsIgnoreCase(""))) {
					custCategory="A";	 
					}else{
					custCategory=customerCategory.toString();
					}
					if((customerSegment.toString().equalsIgnoreCase(""))){
					custSegment="A";  
					}else {
					custSegment=customerSegment.toString();
					}
					if((disbursalDate1.toString().equalsIgnoreCase(""))){
					disdateFrom="A";  	   
					}else{
					disdateFrom=disbursalDate1.toString();
					}
					if((disbursalDate2.toString().equalsIgnoreCase(""))){
					disdateTo="A";   
					}else{
					disdateTo=disbursalDate2.toString();
					}
					if((maturityDate1.toString().equalsIgnoreCase(""))){
					matuDateFrom="A";
					}else{
					matuDateFrom=maturityDate1.toString();
					}
					if((maturityDate2.toString().equalsIgnoreCase(""))){
					matuDateTo="A";
					}else{
					matuDateTo=maturityDate2.toString();
					}
					if((loanAmount1.toString().equalsIgnoreCase(""))){
					LoanAmtFrom="A";
					}else{
					Number abc =CommonFunction.decimalNumberConvert(CommonFunction.checkNull(loanAmount1).trim());
					LoanAmtFrom=abc.toString();
					}
					if((loanAmount2.toString().equalsIgnoreCase(""))){
					LoanAmtTo="A";
					}else{
					Number bcd=CommonFunction.decimalNumberConvert(CommonFunction.checkNull(loanAmount2).trim());
					LoanAmtTo=bcd.toString();
					}
					if((installmentAmount1.toString().equalsIgnoreCase(""))){
					InstAmtFrom="A";
					}else{
					Number abc=CommonFunction.decimalNumberConvert(CommonFunction.checkNull(installmentAmount1).trim());
					InstAmtFrom=abc.toString();
					}
					if((installmentAmount2.toString().equalsIgnoreCase(""))){
					InstAmtTo="A";
					}else{
					Number abc=CommonFunction.decimalNumberConvert(CommonFunction.checkNull(installmentAmount2).trim());
					InstAmtTo=abc.toString();
					}
					if((interestRate1.toString().equalsIgnoreCase(""))){
					IntrestRateFrom="A";
					}else{
					Number abc=CommonFunction.decimalNumberConvert(CommonFunction.checkNull(interestRate1).trim());
					IntrestRateFrom=abc.toString();
					}
					if((interestRate2.toString().equalsIgnoreCase(""))){
					IntrestRateTo="A";
					}else{
					Number abc=CommonFunction.decimalNumberConvert(CommonFunction.checkNull(interestRate2).trim());
					IntrestRateTo=abc.toString();
					}
					if((rateType.toString().equalsIgnoreCase(""))){
					LoanRateType="A";
					}else{
					LoanRateType=rateType.toString();
					}
					if((amountOutstanding1.toString().equalsIgnoreCase("")))
					{
					LoanBalPrinFrom="A";
					}else {		
					Number abc=CommonFunction.decimalNumberConvert(CommonFunction.checkNull(amountOutstanding1).trim());
					LoanBalPrinFrom=abc.toString();
					}

					if((amountOutstanding2.toString().toString().equalsIgnoreCase("")))
					{
					LoanBalPrinTo="A";
					}else{
					Number abc=CommonFunction.decimalNumberConvert(CommonFunction.checkNull(amountOutstanding2).trim());
					LoanBalPrinTo=abc.toString();
					}

					if((totalInstallments1.toString().equalsIgnoreCase(""))){
					LoanNoOfInstFrom="A";
					}else{
					Number abc=CommonFunction.decimalNumberConvert(CommonFunction.checkNull(totalInstallments1).trim());
					LoanNoOfInstFrom=abc.toString();
					}

					if((totalInstallments2.toString().equalsIgnoreCase(""))){
					LoanNoOfInstTo="A";
					}else{
					Number abc=CommonFunction.decimalNumberConvert(CommonFunction.checkNull(totalInstallments2).trim());
					LoanNoOfInstTo=abc.toString();
					}

					if((installmentReceived1.toString().equalsIgnoreCase(""))){
					LoanRecPrinFrom="A";
					}else{
					Number abc=CommonFunction.decimalNumberConvert(CommonFunction.checkNull(installmentReceived1).trim());
					LoanRecPrinFrom=abc.toString();
					}

					if((installmentReceived2.toString().equalsIgnoreCase(""))){
					LoanRecPrinTo="A";
					}else{
					Number abc=CommonFunction.decimalNumberConvert(CommonFunction.checkNull(installmentReceived2).trim());
					LoanRecPrinTo=abc.toString();
					}

					if((overDueAmount1.toString().equalsIgnoreCase(""))){
					LoanOverPrinFrom="A";
					}else{
					Number abc=CommonFunction.decimalNumberConvert(CommonFunction.checkNull(overDueAmount1).trim());
					LoanOverPrinFrom=abc.toString();
					}

					if((overDueAmount2.toString().equalsIgnoreCase(""))){
					LoanOverPrinTo="A";
					}else{
					Number abc=CommonFunction.decimalNumberConvert(CommonFunction.checkNull(overDueAmount2).trim());
					LoanOverPrinTo=abc.toString();
					}

					if((DPD1.toString().equalsIgnoreCase(""))){
					LoanDPDFrom="A";
					}else{
					Number abc=CommonFunction.decimalNumberConvert(CommonFunction.checkNull(DPD1).trim());
					LoanDPDFrom=abc.toString();
					}

					if((DPD2.toString().equalsIgnoreCase(""))){
					LoanDPDTo="A";
					}else{
					Number abc=CommonFunction.decimalNumberConvert(CommonFunction.checkNull(DPD2).trim());
					LoanDPDTo=abc.toString();
					}


					if(lbxValue.toString().equalsIgnoreCase("")){	
					CustConst="A";
					}else{
					for(int i=0;i<constitutionValue.length;i++)
					//consValue = consValue + "'" + constitutionValue[i] + "',";  
					consValue = consValue + constitutionValue[i] + ","; 	
					String  tmp = consValue.toString();
					tmp = (tmp).substring(0,(tmp.length()-1));
					CustConst=tmp;
					tmp=null;
					consValue=null;
					}
					
					if(lbxAssetCollId.toString().equalsIgnoreCase("")){	 
					AssetcolClass="A";
					}else{
					for(int i=0;i<assetCollValue.length;i++)
					assetValue = assetValue + assetCollValue[i] + ",";  

					String  tmp = assetValue.toString();
					tmp = (tmp).substring(0,(tmp.length()-1));
					AssetcolClass=tmp;
					tmp=null;
					assetValue=null; 
					}
					
					if(lbxStateIds.toString().equalsIgnoreCase("")){	 
						StatecolClass="A";
						}else{
						for(int i=0;i<stateCollValue.length;i++)
							stateValue = stateValue + stateCollValue[i] + ",";  

						String  tmp = stateValue.toString();
						tmp = (tmp).substring(0,(tmp.length()-1));
						StatecolClass=tmp;
						tmp=null;
						stateValue=null; 
						}
					
					if(lbxBranchIds.toString().equalsIgnoreCase("")){	 
						BranchcolClass="A";
						}else{
						for(int i=0;i<branchCollValue.length;i++)
						branchValueList = branchValueList + branchCollValue[i] + ",";  

						String  tmp = branchValueList.toString();
						tmp = (tmp).substring(0,(tmp.length()-1));
						BranchcolClass=tmp;
						tmp=null;
						branchValueList=null; 
						}
					
					if(lbxIndustry.toString().equalsIgnoreCase("")){	 
					LoanIndus="A"; 
					}else{
					for(int i=0;i<industryValue.length;i++)
					indsValue = indsValue + industryValue[i] + ",";  
					String  tmp = indsValue.toString();
					tmp = (tmp).substring(0,(tmp.length()-1));
					LoanIndus=tmp;
					tmp=null;
					indsValue=null;
					}
					if(lbxSubIndustry.toString().equalsIgnoreCase("")){
					LoanSubIndus="A";
					}else{
					for(int i=0;i<subIndustryValue.length;i++)
					subIndsValue = subIndsValue  + subIndustryValue[i] + ",";  
					String  tmp = subIndsValue.toString();
					tmp = (tmp).substring(0,(tmp.length()-1));
					LoanSubIndus=tmp;
					tmp=null;
					subIndsValue=null;
					} 
					if(lbxProductID.toString().equalsIgnoreCase("")){
					LoanProdu="A";
					}else{
					for(int i=0;i<productValue.length;i++)
					prodValue = prodValue + productValue[i] + ",";  
					String  tmp = prodValue.toString();
					tmp = (tmp).substring(0,(tmp.length()-1));
					LoanProdu=tmp;
					tmp=null;
					prodValue=null;
					}
					if(lbxScheme.toString().equalsIgnoreCase("")){
					LoanScheme="A";
					}else{
					for(int i=0;i<schemeValue.length;i++)
					schValue = schValue +schemeValue[i] + ",";  
					String  tmp = schValue.toString();
					tmp = (tmp).substring(0,(tmp.length()-1));
					LoanScheme=tmp;
					schValue=null;
					tmp=null;
					}
					
					boolean status = false;
				if(cutOffDate.equalsIgnoreCase("0000-00-00")){
					cutOffDate="A";
				/*if(stateId.equalsIgnoreCase(""))  {
					stateid ="A"; }
					else {
						stateid =stateId;  }*/
				
				}
				if(lbxBranchId.toString().equalsIgnoreCase("")){	 
					Branch="A";
					}else{
					for(int i=0;i<branchValue.length;i++)
					branValue = branValue + branchValue[i] + ",";  

					String  tmp = branValue.toString();
					tmp = (tmp).substring(0,(tmp.length()-1));
					Branch=tmp;
					tmp=null;
					branValue=null; 
					}
				
				ArrayList<Object> in =new ArrayList<Object>();
				ArrayList<Object> out =new ArrayList<Object>();
				ArrayList outMessages =new ArrayList();
				String s1="";
				String s2="";
				String procval="";
				  
				in.add("'"+Branch+"'");
				in.add("'"+loan_ref_no+"'");
				in.add("'"+Loan_no+"'");
				in.add("'"+custName+"'");
				in.add("'"+custCategory+"'");
				
				in.add("'"+custSegment+"'");
				in.add("'"+disdateFrom+"'");
				in.add("'"+disdateTo+"'");
				in.add("'"+matuDateFrom+"'");
				in.add("'"+matuDateTo+"'");
				
				in.add("'"+LoanAmtFrom+"'");
				in.add("'"+LoanAmtTo+"'");
				in.add("'"+InstAmtFrom+"'");
				in.add("'"+InstAmtTo+"'");
				in.add("'"+IntrestRateFrom+"'");
				
				in.add("'"+IntrestRateTo+"'");
				in.add("'"+LoanRateType+"'");
				in.add("'"+LoanBalPrinFrom+"'");
				in.add("'"+LoanBalPrinTo+"'");
				in.add("'"+LoanNoOfInstFrom+"'");
				
				in.add("'"+LoanNoOfInstTo+"'");
				in.add("'"+LoanRecPrinFrom+"'");
				in.add("'"+LoanRecPrinTo+"'");
				in.add("'"+LoanOverPrinFrom+"'");
				in.add("'"+LoanOverPrinTo+"'");
				
				in.add("'"+LoanDPDFrom+"'");
				in.add("'"+LoanDPDTo+"'"); 
				in.add("'"+CustConst+"'");
				in.add("'"+AssetcolClass+"'"); 
				in.add("'"+LoanIndus+"'");
				
				in.add("'"+LoanSubIndus+"'");
				in.add("'"+LoanProdu+"'");
				in.add("'"+LoanScheme+"'");
				in.add("'"+cutOffDate+"'");
				in.add("'"+Userid+"'");
				
				// Added by Rudra....
				in.add("'"+InstallmentMode+"'");
				in.add("'"+RepoStock+"'");
				in.add("'"+CFilter+"'");

				in.add("'"+Ltv1+"'");
				in.add("'"+Ltv2+"'");
				
				in.add("'"+SectorType+"'");			
				in.add("'"+RemainingTenure+"'");
				in.add("'"+Frequency+"'");
				in.add("'"+AssetCost1+"'");
				in.add("'"+AssetCost2+"'");
				
				in.add("'"+BranchcolClass+"'"); 
				in.add("'"+StatecolClass+"'"); 
				
				//out.add(s1);
				//out.add(s2);
				
				Date date = new Date(20);
				
				Session session = HibernateSessionFactory.currentSession();
				Transaction transaction = session.beginTransaction();
				
				PoolDownloadVO poolDownloadVO = new PoolDownloadVO();
				poolDownloadVO.setParameters(in.toString());
				poolDownloadVO.setType("Securitization");
				poolDownloadVO.setRequestTime(DateFormator.dmyToSQL(poolvo.getBusinessDate()));
				poolDownloadVO.setProcessStartTime(date);
				poolDownloadVO.setProcessEndTime(date);
				poolDownloadVO.setStatus("P");
				poolDownloadVO.setMakerID(Userid);
				
				session.save(poolDownloadVO);
				transaction.commit();
				HibernateSessionFactory.closeSession();
				mainList.add("success");
				
				/*try
				{
					logger.info("GENERATE_POOL_DOWNLOAD ("+in.toString()+","+out.toString()+")");
					
					outMessages=(ArrayList) ConnectionReportDumpsDAO.callSPForHiberNate("GENERATE_POOL_DOWNLOAD",in,out);
					
					s1=CommonFunction.checkNull(outMessages.get(0));
					s2=CommonFunction.checkNull(outMessages.get(1));
					logger.info("After calling GENERATE_POOL_DOWNLOAD proc S1 and S2 "+s1+" - "+s2);
				}
				     
				catch (Exception e) {
					e.printStackTrace();
				}
				procval=s2;
	            if(s1.equalsIgnoreCase("S"))
	            {
	            	status=true;
	                procval=s1;
	            }
	            else
	            {
	            	status=false;
	            	procval=s2;
	            }  
	           				  
	           
		          
			  if(status==true){
				  Criteria criteria =  session.createCriteria(PoolDownloadTempVO.class);
				  criteria.add(Restrictions.eq("user_id", Userid));
				  criteria.addOrder(Order.asc("loan_id"));
				  List l = criteria.list();
				  mainList = new ArrayList(l);
				  
				  bufInsSql.append("SELECT LOAN_ID, LOAN_NO, LOAN_REFERENCE_NO, CUSTOMER_NAME, address, ASSET_COLLATERAL_CLASS,");
		          bufInsSql.append(" ASSET_COLLATERAL_DESC, MANUFATURER_DESC, 	ASSET_NEW_OLD, REPAYMENT_MODE, PDC, LOAN_TENURE, INSTALLMENT_TYPE, APPL_FIN_DOC,");
		          bufInsSql.append("GUR_FIN_DOC, LOAN_SECTOR_TYPE,APPL_ADDRESS_PROOF, 	APPL_ID_PROOF, 	APPL_SIGNATURE_PROOF, 	GUR_ADDRESS_PROOF, GUR_ID_PROOF,");
		          bufInsSql.append(" GUR_SIGNATURE_PROOF, LOAN_LOAN_AMOUNT,Disbursal_Date,	firstEMIDate, last_emi, first_emi_month, last_emi_month, ");
		          bufInsSql.append("INSTL_AMOUNT, Seasoning,REMAINING_TENURE,LOAN_EMI_AMOUNT, LOAN_EFF_RATE, PRODUCT_DESC, SCHEME_DESC, description,");
		          bufInsSql.append(" CUSTOMER_BUSINESS_SEGMENT, INDUSTRY_DESC,SUB_INDUSTRY_DESC,LOAN_MATURITY_DATE, LOAN_NO_OF_INSTALLMENT, LOAN_ADVANCE_INSTL,");
		          bufInsSql.append("LOAN_FINAL_RATE, DISBURSAL_STATUS, NPA_FLAG,LOAN_STATUS, LOAN_DPD, LOAN_DPD_STRING,LoanAdvanceEMIAmount, LOAN_BALANCE_PRINCIPAL,");
		          bufInsSql.append("LOAN_OVERDUE_PRINCIPAL, LOAN_RECEIVED_PRINCIPAL,LOAN_OVERDUE_INSTL_NUM, LOAN_OVERDUE_AMOUNT, LOAN_BALANCE_INSTL_NO, ");
		          bufInsSql.append("LOAN_BALANCE_INSTL_AMOUNT, BRANCH_DESC, advanceFlg, SDAMT, RECEIVED_SDAMT, UNDISBURSED, INTEREST_COMPONENT,");
		          bufInsSql.append("RECEIVED_INTEREST_AMT 	FROM rpt_pool_download_temp WHERE USER_ID='"+Userid+"' ORDER BY LOAN_ID");
		          
			  }
		  sbAppendToSQLCount=null;*/
	      	  
	     
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
	      branchList=null;
	      lbxBranchId=null;
	      
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
		bufInsSql.append(" select group_concat(distinct DATE_FORMAT(MVMT_DATE,'%d-%m-%Y') separator '|')as result from cr_loan_dtl_eod order by MVMT_DATE");
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
