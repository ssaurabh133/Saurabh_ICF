/*   Author Name-      Abhishek Mathur 
     Date of creation -17/04/2017
     Purpose-         Convenant Tracking
 */
package com.cp.dao;

import java.util.ArrayList;

import com.cm.vo.LoanDetailForCMVO;
import com.cp.vo.CovenantProposalTrackingVO;
import com.cp.vo.UnderwriterApprovalVo;



public interface CovenantProposalTrackingDAO {

	String IDENTITY="COVENANTTRACKINGPROPOSAL";

	ArrayList getCovenantTrackingData(CovenantProposalTrackingVO covTrackVo);

	boolean saveCovenantTracking(CovenantProposalTrackingVO covTrackVo);

	boolean updateCovenantTracking(CovenantProposalTrackingVO covTrackVo);

	ArrayList getCovenantTrackingDataSearch(CovenantProposalTrackingVO covTrackVo);

	boolean deleteCovenantTrackingData(String recordId,String loanId);

	boolean forwardCovenantTrackingData(CovenantProposalTrackingVO covTrackVo);

	ArrayList getCovenantTrackingSearchAuthor(CovenantProposalTrackingVO covTrackVo);

	ArrayList getCovenantTrackingDataSearchAuthor(CovenantProposalTrackingVO covTrackVo);

	boolean getCovTrackDecision(CovenantProposalTrackingVO covTrackVo);

	ArrayList getCovenantTrackingDataAuthor(CovenantProposalTrackingVO covTrackVo);

	ArrayList getCovenantTrackingSearchDetails(CovenantProposalTrackingVO covTrackVo);

	ArrayList getCovenantTrackingDetailData(CovenantProposalTrackingVO covTrackVo);

	boolean saveCovenantDetails(CovenantProposalTrackingVO covTrackVo);

	ArrayList getCovenantTrackingDetailDataOpen(CovenantProposalTrackingVO covTrackVo);

	ArrayList getCovenantTrackingDetailDataComplied(CovenantProposalTrackingVO covTrackVo);
	
	boolean saveCovenantUserMapping(CovenantProposalTrackingVO covTrackVo);

	boolean deleteCovenantUserMappingData(String covenantId, String dealId);
	public ArrayList<CovenantProposalTrackingVO> searchCovenentUserMappingData(String covenentId);
	
	boolean saveDefaultValue(String loanId,String userId,String businessDate,String businessType);
	ArrayList getLoanHeader(String dealId);
	ArrayList<LoanDetailForCMVO> getPartnerDetailsforPopUp(LoanDetailForCMVO loanDetailForCMVO, String loanId,String businessType);
	String savePartnerDetails(Object ob, String id,String businessType);
	ArrayList<LoanDetailForCMVO> getPartnerDetails(LoanDetailForCMVO loanDetailForCMVO, String loanId,String businessType);
	ArrayList<LoanDetailForCMVO> getPartnerBusDetails(LoanDetailForCMVO loanDetailForCMVO,String id, String loanId,String businessType);
	int deletePartnerDtl(String partnerDtl, String loanId,String businessType);

	boolean insertcolndngDtl(UnderwriterApprovalVo cr);
	String updateApprovalData(Object ob);


}
