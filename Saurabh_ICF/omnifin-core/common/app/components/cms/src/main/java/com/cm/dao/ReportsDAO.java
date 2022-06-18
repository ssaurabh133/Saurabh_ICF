package com.cm.dao;

import com.cm.actionform.ReportsForm;
import com.cm.vo.ChequeAllocationVo;
import java.util.ArrayList;

public abstract interface ReportsDAO
{
  public static final String IDENTITY = "REPORTD";

  public abstract ArrayList<ReportsForm> getReportFormat();

  public abstract ArrayList<ReportsForm> getReportFormat_NEW();

  public abstract ArrayList<ReportsForm> getCompanyAddress(int paramInt);

  public abstract ArrayList<Object> getCibilReport(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7);

  public abstract boolean checkNoOfLoan(String paramString1, String paramString2);

  public abstract int getMaxLoanLimit();

  public abstract ArrayList<ReportsForm> getReportName(int paramInt1, int paramInt2, String paramString);

  public abstract ArrayList<ReportsForm> getReportNameForCp(int paramInt1, int paramInt2, String paramString);

  public abstract String getCustomerName(String paramString);

  public abstract String getCoApplicantName(String paramString);

  public abstract String getGaurantorName(String paramString);

  public abstract String getStartingDate(String paramString);

  public abstract String getAppServerPath();

  public abstract String getEndDate();

  public abstract String getLastMonthDate(String paramString);

  public abstract ArrayList<ReportsForm> getSponsorCode();

  public abstract ArrayList<ReportsForm> getfinanceYear();

  public abstract String getMaxDefaultDate();

  public abstract String getcutOffDate();

  public abstract ArrayList<ReportsForm> getScheme();

  public abstract ArrayList<ReportsForm> getProductName();

  public abstract ArrayList<ReportsForm> getLoanClassification();

  public abstract ArrayList getDONo(String paramString1, String paramString2, String paramString3);

  public abstract String getCAMReport(String paramString);

  public abstract String getCutoffDateForIncipient();

  public abstract ArrayList generateSOA(int paramInt, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7);

  public abstract ArrayList generateSOAForCancelLoan(int paramInt, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7);

  public abstract String getDateRangeLimit();

  public abstract String getDateRangeLimitSpecial();

  public abstract String checkLinkedLoan(String paramString);

  public abstract String checkLinkLoanFlag();

  public abstract ArrayList getNocCount(String paramString1, String paramString2, String paramString3);

  public abstract boolean getEligibilityRecord(String paramString1, String paramString2, String paramString3);

  public abstract String getDefaultFormateSOA();

  public abstract ArrayList getChargesDetail(String paramString);

  public abstract ArrayList getReportData(int paramInt, ChequeAllocationVo paramChequeAllocationVo);

  public abstract String getReceiptReportName();

  public abstract String getEarlyClosureReport(String paramString);

  public abstract boolean deleteUserOldRecord(String paramString);

  public abstract String getEarlyClosureType();

  public abstract boolean getLoanDetails(String paramString1, String paramString2);

  public abstract String getcamTemplatePath();

  public abstract String getDvReport(String paramString);

  public abstract ArrayList getCmsDownLoadReport(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9);

  public abstract String getCAMReportAtLoan(String paramString);

  public abstract String getDealId(String paramString);

  public abstract String getWelcomeReportQry(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6);

  public abstract String getForeClosureReportQry();

  public abstract String getWlGuaranterReportQry();

  public abstract boolean updateReportGenerationFlag();

  public abstract boolean checkReportGeneration();

  public abstract boolean checkUpdateFlag();

  public abstract void saveFunctionLogData(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8);

  public abstract String getSanctionReport(String paramString);

  public abstract ArrayList getForClosureLoanId();

  public abstract boolean deleteTerminationDtl(String paramString);

  public abstract String getSnoForGoldLoanReport(String paramString1, String paramString2, String paramString3);

  public abstract String CibilIndividualFileGenerator(String paramString1, String paramString2);

  public abstract String CibilCorporateFileGenerator(String paramString1, String paramString2);

  public abstract String CibilCorporateLCFileGenerator(String paramString1, String paramString2);

  public abstract String CibilFileNameGenerator(String paramString);

  public abstract ArrayList NeslFileGenerator(String paramString1, String paramString2);
  
  public abstract ArrayList NeslLCFileGenerator(String paramString1, String paramString2);

  public abstract ArrayList fetchInstlDate(String paramString1, String paramString2);

  public abstract ArrayList fetchLoanList(String paramString1, String paramString2);

  public abstract ArrayList fetchPrinAmtList(String paramString, ArrayList paramArrayList);

  public abstract ArrayList fetchIntAmtList(String paramString, ArrayList paramArrayList);

  public abstract ArrayList fetchAmtList(String paramString1, String paramString2, String paramString3);
}