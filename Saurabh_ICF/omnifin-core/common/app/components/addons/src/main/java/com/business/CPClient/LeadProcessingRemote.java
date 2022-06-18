package com.business.CPClient;

import com.cp.vo.CreditProcessingLeadDetailDataVo;
import com.cp.vo.LeadCaptureVo;
import java.util.ArrayList;
import javax.ejb.Remote;

@Remote
public abstract interface LeadProcessingRemote
{
  public static final String REMOTE_IDENTITY = "LEADPROCESSINGBUSSINESSMASTERREMOTE";

  public abstract ArrayList<LeadCaptureVo> getData(Object paramObject);

  public abstract ArrayList<Object> getexistingData(LeadCaptureVo paramLeadCaptureVo, String paramString);

  public abstract ArrayList getLeadCapturingDetailsList(String paramString);

  public abstract boolean saveLeadDetailData(Object paramObject, String paramString);

  public abstract ArrayList getLeadDetailList();

  public abstract ArrayList<Object> getRmDetail(String paramString);

  public abstract ArrayList existingUser(CreditProcessingLeadDetailDataVo paramCreditProcessingLeadDetailDataVo, String paramString);

  public abstract boolean saveAllocation(CreditProcessingLeadDetailDataVo paramCreditProcessingLeadDetailDataVo);

  public abstract ArrayList<Object> getAllocationDetail(String paramString);

  public abstract boolean saveTrackingDetails(CreditProcessingLeadDetailDataVo paramCreditProcessingLeadDetailDataVo);

  public abstract ArrayList<Object> getTrackingDetail(String paramString);

  public abstract ArrayList<Object> getMaxLeadId(String paramString);

  public abstract ArrayList getLeadNoteCode();

  public abstract ArrayList getLeadNotepadData(String paramString1, String paramString2);

  public abstract boolean saveLeadNotepadData(Object paramObject);

  public abstract String saveNewLead(Object paramObject);

  public abstract ArrayList<Object> getCommonLeadData(LeadCaptureVo paramLeadCaptureVo, String paramString);

  public abstract ArrayList getSourceDetailList(String paramString);

  public abstract ArrayList defaultcountry();

  public abstract boolean deletelead(String paramString);

  public abstract ArrayList<Object> getCommonLeadDatafromjsp(LeadCaptureVo paramLeadCaptureVo, String paramString);

  public abstract ArrayList addresstype();

  public abstract ArrayList<Object> getContitutionList();

  public abstract ArrayList<Object> getBusinessSegmentList();

  public abstract ArrayList<Object> getEduDetailList();

  public abstract ArrayList<CreditProcessingLeadDetailDataVo> CustomerDetailsList(String paramString1, String paramString2, String paramString3);

  public abstract ArrayList<Object> getCorContitutionList();

  public abstract ArrayList<Object> getIndContitutionList();

  public abstract ArrayList<Object> getLeadEntryList(String paramString);

  public abstract int saveCPLeadEntry(Object paramObject);

  public abstract ArrayList searchCPGrid(LeadCaptureVo paramLeadCaptureVo, String paramString);

  public abstract ArrayList<Object> getDecisionList(String paramString);

  public abstract ArrayList getLoanTypeList();

  public abstract boolean updateLeadStatus(String paramString);

  public abstract ArrayList getSectorList();
  
  public abstract String getPincodeFlag();
  
  public abstract String getEmailMandatoryFlag();
}