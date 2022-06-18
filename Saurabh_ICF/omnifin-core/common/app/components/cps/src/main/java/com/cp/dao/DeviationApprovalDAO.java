package com.cp.dao;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.cp.vo.CommonDealVo;
import com.cp.vo.DeviationApprovalVo;

public interface DeviationApprovalDAO {
	String IDENTITY = "DA";

	public ArrayList<CommonDealVo> deviationApprovalAllDetails(CommonDealVo vo);

	public ArrayList getDeviationApprovalDetails(DeviationApprovalVo Vo,
			String dealId);

	boolean forwardDeviationApproval(Object ob);

	// --------manisha manual Deviation Maker code starts----------
	public ArrayList<CommonDealVo> manualDeviationMakerSearch(CommonDealVo vo);

	public ArrayList<DeviationApprovalVo> manualDeviationM(
			DeviationApprovalVo Vo, String dealId);

	String saveManualDeviation(DeviationApprovalVo vo,
			ArrayList<String> manualId, ArrayList<String> remarks,
			ArrayList<String> approvalLevel);

	public ArrayList<CommonDealVo> getDealHeader(String dealId);

	public ArrayList<DeviationApprovalVo> manualDevList(String dealId);

	String forwardManualDeviation(DeviationApprovalVo vo,
			ArrayList<String> manualId, ArrayList<String> remarks,
			ArrayList<String> approvalLevel);

	String saveDeviationApproval(DeviationApprovalVo vo);

	public ArrayList<DeviationApprovalVo> getLoanAllDeviation(String loanId,
			String userId, String functionId);

	public String saveLoanDeviation(DeviationApprovalVo vo);

	boolean loanForwardDeviationApproval(Object ob);

	public boolean saveLoanManualDeviationFromMaster(String loanId,
			String userId, String bDate);

	ArrayList manualDeviationUND(DeviationApprovalVo Vo, String dealId);

	boolean loansendBackDeviation(Object ob);

	public String countApproveDeviation(String dealId);

	String deleteDeviationUND(DeviationApprovalVo vo, String[] deleteIdFin);
}
