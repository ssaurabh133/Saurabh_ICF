package com.business.legalTransactionBussiness;

import java.util.ArrayList;

import javax.ejb.Remote;
import javax.servlet.http.HttpServletRequest;

@Remote
public interface LegalTransactionBusinessSessionBeanRemote  {
	
	String REMOTE_IDENTITY="LEGALTRANSACTIONBUSSINESSMASTERREMOTE";
	
	public ArrayList searchLegalNoticeInitiationMakerData(Object ob) ;
	public boolean insertLegalNoticeInitiationMakerData(Object ob);
	public ArrayList editLegalNoticeInitiationMakerData(Object ob) ;
	public boolean updateLegalNoticeInitiationMakerData(Object ob);
	
	public ArrayList searchLegalNoticeInitiationCheckerData(Object ob) ;
	public boolean insertLegalNoticeInitiationChecker(Object ob);
	public ArrayList editLegalNoticeInitiationCheckerData(Object ob);
	
	public ArrayList searchLegalDeclineDispatchNoticeData(Object ob);
	public ArrayList openEditLegalDeclineDispatchNotice(Object ob);
	public boolean insertLegalDeclineDispatchNotice(Object ob);
	
	public ArrayList searchLegalCaseInitiationMaker(Object ob) ;
	public ArrayList openEditLegalCaseInitiationMaker(Object ob) ;
	public boolean insertLegalCaseInitiationMaker(Object ob);
	public boolean checkingLRNStatus(Object ob);
	public boolean checkingNotice138Status(Object ob);
	
	
	public ArrayList searchLegalCaseFileMaker(Object ob) ;
	public ArrayList openEditLegalCaseFileMaker(Object ob) ;
	public boolean saveLegalCaseFileMaker(Object ob);
	
	public ArrayList searchLegalCaseFileAuthor(Object ob) ;
	public ArrayList getLegalCaseFileMakerDataForAuthorView(Object ob) ;
	public boolean saveLegalCaseFileAuthor(Object ob);
	
	public ArrayList searchLegalCourtProcessingMaker(Object ob) ;
	public ArrayList openEditLegalCourtProcessingMaker(Object ob) ;
	public ArrayList getStageData(Object ob) ;
	public boolean insertCourtProcessingMakerDetails(Object ob,String [] stageList,String [] caseNoList,String [] remarkList,String [] hearingDateList,String [] poaList,String [] approvalFlagList,String [] makerDateList);
	public boolean addRowofCourtProcessingMaker(Object ob);
	
	public ArrayList searchLegalCourtProcessingAuthor(Object ob) ;
	public boolean saveCourtProcessingAuthorData(Object ob);
	
	public ArrayList searchLegalReopenClosedCase(Object ob) ;
	public boolean saveLegalReopenClosedCaseData(Object ob);
	
	public ArrayList searchAssignRejectCaseData(Object ob);
	public boolean insertAssignRejectCase(Object ob);
	public ArrayList openEditAssignRejectCaseData(Object ob);
	
	public ArrayList searchReassignCaseData(Object ob);
	public ArrayList editReassignCase(Object ob);
	public boolean insertReassignCase(Object ob);
	public boolean saveReassignCase(Object ob);
	public abstract String getNoticeId();

	  public abstract ArrayList searchReasonData(Object paramObject);

	  public abstract boolean updateReasonData(Object paramObject);

	  public abstract String insertReasonMaster(Object paramObject);
	
}
