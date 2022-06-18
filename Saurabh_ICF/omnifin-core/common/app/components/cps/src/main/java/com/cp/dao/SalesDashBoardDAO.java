package com.cp.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cp.vo.DeviationApprovalVo;
import com.cp.vo.SalesDashBoardVo;

public interface SalesDashBoardDAO 
{
	String IDENTITY="SDB";
	public boolean isUserExist(String userName, String userPassword);
	public List<SalesDashBoardVo> salesDashBoardItemDtl(String panId, String source, String userId, String showAll);
	public ArrayList openDashboardQueryDtl(String dealId);
	public List<SalesDashBoardVo> getBranchList(String userId, String source, String queue);
	public boolean manageSelectedBranch(String userId, String source, String selectedBranchIds);
	public boolean createDefaultDashboardData(String userId,String source, String selectedUserIds, String selectedBranchIds, String isRoot);
	public Map<String, String> fetchSalesDashboardData(String userId, String source,String selectedBranchIds);
	public java.lang.String getDashBoardRootUserId();
	public boolean manageSelectedUser(java.lang.String userId,java.lang.String source, java.lang.String userList);
	public boolean checkSwitchAccess(java.lang.String userId);
	
}
