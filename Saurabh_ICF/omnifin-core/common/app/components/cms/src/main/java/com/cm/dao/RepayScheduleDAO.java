/*
 Author: Vishal SIngh 
 Date:27-03-2012 
 Purpose:Repay Schedule Maker/Author 
 
 */

package com.cm.dao;

import java.util.ArrayList;
import com.cm.vo.LoanInitAuthorVo;
import com.cm.vo.UpdateRepayscheduleSearchVO;
import com.cp.vo.RepayScheduleVo;

public interface RepayScheduleDAO 
{
	String IDENTITY="REPAYSCHD";
	ArrayList<UpdateRepayscheduleSearchVO> searchRepaySchedule(UpdateRepayscheduleSearchVO vo, String type);
	ArrayList<UpdateRepayscheduleSearchVO> retriveRepayScheduleValues(String lbxLoanNoHID);
	public String updateRepayScheduleData(UpdateRepayscheduleSearchVO vo);
	ArrayList<UpdateRepayscheduleSearchVO> getRepayScheduleData(String reschId,String lbxLoanNoHID);
	ArrayList getCycleDateList();
	String getMakerDate(String reschId);
	public boolean updateAndFarwordRepayScheduleData(UpdateRepayscheduleSearchVO vo);
	ArrayList<RepayScheduleVo> getNewRepayScheduleDueDate(RepayScheduleVo vo,
			String loanId, String reschId);
	String saveDueDateAuthor(LoanInitAuthorVo vo);
	boolean deleteDueDateData(String loanId, String reschId);
	String getBillFlag(LoanInitAuthorVo vo);
	
	
	
}
