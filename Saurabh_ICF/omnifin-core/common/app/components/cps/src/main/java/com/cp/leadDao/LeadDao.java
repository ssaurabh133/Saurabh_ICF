package com.cp.leadDao;

import java.util.ArrayList;

import com.cp.vo.CreditProcessingLeadDetailDataVo;
import com.cp.vo.LeadCaptureVo;

public interface LeadDao {

	String IDENTITY="LEAD";
	 // Lead By Sanjog
	 	ArrayList getData(Object ob);
	 	
	 	ArrayList<Object> getexistingData(LeadCaptureVo ob, String tracking);
		
	 	ArrayList getLeadCapturingDetailsList(String leadId);
		
	 	boolean saveLeadDetailData(Object vo,String param);
		
	 	public ArrayList getLeadDetailList();
		
	 	ArrayList<Object> getRmDetail(String leadId);
		
	 	ArrayList existingUser(CreditProcessingLeadDetailDataVo cpLeadDetailVo,	String userId);
		
	 	boolean saveAllocation(CreditProcessingLeadDetailDataVo leadIdVo);
		
	 	ArrayList<Object> getAllocationDetail(String leadId);
		
		boolean saveTrackingDetails(CreditProcessingLeadDetailDataVo leadIdVo);
		
	 	ArrayList<Object> getTrackingDetail(String leadId);
		
	 	ArrayList<Object> getMaxLeadId(String userName);
		
	 	ArrayList getLeadNoteCode();
		
	 	ArrayList getLeadNotepadData(String txnid,String txnType);
		
	 	boolean saveLeadNotepadData(Object ob);
		
	 	String saveNewLead(Object ob);
		
	 	ArrayList<Object> getCommonLeadData(LeadCaptureVo ob,String tracking);
		
	 	public ArrayList getSourceDetailList(String source);
		
	 	ArrayList defaultcountry();

		boolean deletelead(String leadId);

		ArrayList<Object> getCommonLeadDatafromjsp(LeadCaptureVo ob, String tracking);

		ArrayList addresstype();

		ArrayList<Object> getContitutionList();

		ArrayList<Object> getBusinessSegmentList();
		
		ArrayList<Object> getEduDetailList();

		ArrayList CustomerDetailsList(String customerId, String addressId, String bDate);

		ArrayList<Object> getCorContitutionList();

		ArrayList<Object> getIndContitutionList();
		
		
		
		
		ArrayList<Object> getLeadEntryList(String leadId);
		 int saveCPLeadEntry(Object vo);

		ArrayList searchCPGrid(LeadCaptureVo vo, String leadNo);
		
		ArrayList<Object> getDecisionList(String leadId);
		
		public ArrayList getLoanTypeList();
		
		boolean updateLeadStatus(String leadId);
		ArrayList getSectorList();
		String getPincodeFlag();
		String getEmailMandatoryFlag();
}
