package com.cp.daoImplMSSQL;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cp.dao.SalesDashBoardDAO;
import com.cp.vo.SalesDashBoardVo;

public class MSSQLSalesDashBoardDAOImpl implements SalesDashBoardDAO {

	@Override
	public boolean isUserExist(String userName, String userPassword) {
		// TODO Auto-generated method stub
		return false;
	}
	public List<SalesDashBoardVo> salesDashBoardItemDtl(String dashBoard,String source, String userId, String showAll)
	{
		return null;
	}
	public ArrayList openDashboardQueryDtl(String dealId)
	{
		return null;
	}
	@Override
	public List<SalesDashBoardVo> getBranchList(String userId, String source, String queue) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean manageSelectedBranch(String userId, String source,String selectedBranchIds) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Map<String, String> fetchSalesDashboardData(String userId,
			String source, String selectedBranchIds) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean createDefaultDashboardData(String userId,String source, String selectedUserIds, String selectedBranchIds, String isRoot) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public String getDashBoardRootUserId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean manageSelectedUser(String userId, String source,
			String userList) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean checkSwitchAccess(String userId) {
		// TODO Auto-generated method stub
		return false;
	}

}
