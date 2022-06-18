package com.cp.daoImplMYSQL;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.sql.ResultSet;

import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.cp.dao.SalesDashBoardDAO;
import com.cp.vo.SalesDashBoardVo;

public class SalesDashBoardDAOImpl implements SalesDashBoardDAO {

	final Logger logger = Logger.getLogger(SalesDashBoardDAOImpl.class.getName());
	@Override
	public boolean isUserExist(String userName, String userPassword) 
		{
			String userid="" ;
			boolean status=false;
			logger.info("Enter for DB isUserExist action");
			try {
					String user=" SELECT User_ID FROM sec_user_m WHERE User_ID='"+CommonFunction.checkNull(userName).trim()+"' "
					+"AND USER_PASSWORD='"+CommonFunction.checkNull(userPassword).trim()+"'"
					+" AND REC_STATUS='A' AND ACCOUNT_STATUS='U'";
					userid=CommonFunction.checkNull(ConnectionDAO.singleReturn(user));
					if(userid!=null && StringUtils.isNotBlank(userid)){
						status=true;
					}
				}catch(Exception e){
					e.printStackTrace();
				}
				return status;
		}
	public List<SalesDashBoardVo> salesDashBoardItemDtl(String dashBoard,String source, String userId, String showAll)
	{
		List<SalesDashBoardVo>list=null;
		SalesDashBoardVo vo=null;
		Connection con=null;
		CallableStatement callableStatement=null;
		ResultSet rs=null;
		try{
			con=ConnectionDAO.getConnection();
			callableStatement = con.prepareCall("{call SALES_DASH_BOARD_DATA(?,?,?,?,?)}");
			callableStatement.setString(1,userId);//I_LOGIN_USER_ID
			callableStatement.setString(2,source);//I_SOURCE
			callableStatement.setString(3,showAll);//I_SHOW_ALL
			callableStatement.setString(4,"D");//I_OPERATION_TYPE
			callableStatement.setString(5,dashBoard);//I_OPERATION_SUB_TYPE
			rs = callableStatement.executeQuery();
			while (rs.next()) {
				if(list==null){
					list=new ArrayList<SalesDashBoardVo>();
				}
				vo = new SalesDashBoardVo();
				vo.setDealNo(rs.getString("DEAL_NO"));
				vo.setFormNo(rs.getString("FORM_NO"));
				vo.setCustomerName(rs.getString("CUSTOMER_NAME"));
				vo.setDealIntitionDate(rs.getString("DEAL_INITIATION_DATE"));
				vo.setRoName(rs.getString("RO_NAME"));
				vo.setRmName(rs.getString("RM_NAME"));
				vo.setBranchId(rs.getString("BRANCH_ID"));
				vo.setBranchName(rs.getString("BRANCH_NAME"));
				vo.setMakerId(rs.getString("MAKER_ID"));
				vo.setMakerName(rs.getString("MAKER_NAME"));
				vo.setStatus(rs.getString("STATUS"));
				vo.setDealId(rs.getString("DEAL_ID"));
				vo.setReason(rs.getString("REASON"));
				list.add(vo);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(rs!=null){
					rs.close();
					rs=null;
				}
				if(callableStatement!=null){
					callableStatement.close();
					callableStatement=null;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(con!=null){
					con.close();
					con=null;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return list;
	}
	public ArrayList openDashboardQueryDtl(String dealId)
	{
		ArrayList list=new ArrayList();
		ArrayList querylist=new ArrayList();
		StringBuffer bufInsSql =new StringBuffer();
		try
		{
			bufInsSql.append("select dqd.QUERY_REMARKS,dqd.QUERY_DATE,dqd.MAKER_ID,dqd.USER_ID,cdm.DEPARTMENT_DESC "
					+ " from deal_query_dtl dqd "
					+ " join sec_user_m sm on dqd.USER_ID=sm.USER_ID "
					+ " left join com_department_m cdm on sm.USER_DEPARTMENT=cdm.DEPARTMENT_ID "
					+ "where dqd.deal_id='"+dealId+"' and dqd.RESOLUTION_STATUS='P'");
			logger.info(" in DashBordDtlList Query  : "+bufInsSql);
			querylist = ConnectionDAO.sqlSelect(bufInsSql.toString());
			logger.info(" in DashBordDtlList size  : "+querylist.size());
			int size=querylist.size();
			for(int i=0;i<size;i++)
			{
				ArrayList data=(ArrayList)querylist.get(i);
				if(data.size()>0)
				{	
					SalesDashBoardVo vo=new SalesDashBoardVo();
					vo.setQueryDtl((CommonFunction.checkNull(data.get(0))).trim());
					vo.setQueryDate((CommonFunction.checkNull(data.get(1))).trim());
					vo.setQueryMakerId((CommonFunction.checkNull(data.get(2))).trim());
					vo.setUserId((CommonFunction.checkNull(data.get(3))).trim());
					vo.setDepartment((CommonFunction.checkNull(data.get(4))).trim());
					list.add(vo);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public List<SalesDashBoardVo> getBranchList(String userId, String source, String queue) {
		List<SalesDashBoardVo> list=new ArrayList<SalesDashBoardVo>();
		ArrayList querylist=new ArrayList();
		String bufInsSql="";
		boolean optStatus=false;
		Connection connection=null;
		Statement stmt=null;
		try
		{
			bufInsSql="SELECT BR.BRANCH_ID,BR.BRANCH_DESC,'"+source+"' AS SOURCE,'"+userId+"' AS LOGIN_USER_ID"
			+" FROM COM_BRANCH_M BR";
			if(StringUtils.equalsIgnoreCase(queue,"O")){//own queue
				bufInsSql=bufInsSql+""
				+" JOIN SEC_USER_BRANCH_DTL MAP ON(BR.BRANCH_ID=MAP.BRANCH_ID AND BR.REC_STATUS='A' AND MAP.REC_STATUS='A')"
				+" WHERE MAP.USER_ID='"+userId+"'";
			}else{
				bufInsSql=bufInsSql+""
				+" WHERE BR.REC_STATUS='A'";
			}
			logger.info(" in getBranchList Query  : "+bufInsSql);
			querylist = ConnectionDAO.sqlSelect(bufInsSql);
			logger.info(" in DashBordDtlList size  : "+querylist.size());
			int size=querylist.size();
			for(int i=0;i<size;i++)
			{
				ArrayList data=(ArrayList)querylist.get(i);
				if(data.size()>0)
				{	
					SalesDashBoardVo vo=new SalesDashBoardVo();
					vo.setBranchId((CommonFunction.checkNull(data.get(0))).trim());
					vo.setBranchName((CommonFunction.checkNull(data.get(1))).trim());
					list.add(vo);
				}
			}
			String deleteQuery="DELETE FROM STAGE_DASHBOARD_BRANCH_FILTER_DTL WHERE SOURCE='"+source+"' AND LOGIN_USER_ID='"+userId+"'";
			String insertQuery="INSERT INTO STAGE_DASHBOARD_BRANCH_FILTER_DTL(BRANCH_ID,BRANCH_NAME,SOURCE,LOGIN_USER_ID) "+bufInsSql;
			logger.info(" in manageSelectedBranch deleteQuery  : "+deleteQuery);
			logger.info(" in manageSelectedBranch insertQuery  : "+insertQuery);
			try{
				connection=ConnectionDAO.getConnection();
				connection.setAutoCommit(false);
				stmt=connection.createStatement();
				stmt.executeUpdate(deleteQuery);
				stmt.executeUpdate(insertQuery);
				connection.commit();
				optStatus=true;
			}catch(Exception e){
				optStatus=false;
				e.printStackTrace();
			}finally{
				try{
					if(stmt!=null){
						stmt.close();
					}
					if(connection!=null){
						connection.close();
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public boolean manageSelectedBranch(String userId, String source,String selectedBranchIds) {
		boolean optStatus=false;
		Connection connection=null;
		Statement stmt=null;
		Statement stmt2=null;
		String deleteQuery="DELETE FROM STAGE_DASHBOARD_BRANCH_FILTER_DTL WHERE SOURCE='"+source+"' AND LOGIN_USER_ID='"+userId+"'";
		String insertQuery="INSERT INTO STAGE_DASHBOARD_BRANCH_FILTER_DTL(BRANCH_ID,BRANCH_NAME,SOURCE,LOGIN_USER_ID)"
			+" SELECT BR.BRANCH_ID,BR.BRANCH_DESC,'"+source+"' AS SOURCE,'"+userId+"' AS LOGIN_USER_ID"
			+" FROM COM_BRANCH_M BR";
			if(StringUtils.isBlank(selectedBranchIds)){
				insertQuery=insertQuery+""
				+" JOIN SEC_USER_BRANCH_DTL MAP ON(BR.BRANCH_ID=MAP.BRANCH_ID AND BR.REC_STATUS='A' AND MAP.REC_STATUS='A')"
				+" WHERE MAP.USER_ID='"+userId+"'";
			}else{
				insertQuery=insertQuery+""
				+" WHERE BR.REC_STATUS='A' AND INSTR(CONCAT('|','"+selectedBranchIds+"','|'),CONCAT('|',BR.BRANCH_ID,'|'))>0 ";
			}
		logger.info(" in manageSelectedBranch deleteQuery  : "+deleteQuery);
		logger.info(" in manageSelectedBranch insertQuery  : "+insertQuery);
		try{
			connection=ConnectionDAO.getConnection();
			connection.setAutoCommit(false);
			stmt=connection.createStatement();
			stmt.executeUpdate(deleteQuery);
			connection.commit();
			stmt2=connection.createStatement();
			stmt2.executeUpdate(insertQuery);
			connection.commit();
			optStatus=true;
		}catch(Exception e){
			optStatus=false;
			e.printStackTrace();
		}finally{
			try{
				if(stmt!=null){
					stmt.close();
				}
				if(stmt2!=null){
					stmt2.close();
				}
				if(connection!=null){
					connection.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return optStatus;
	}
	@Override
	public boolean createDefaultDashboardData(String userId,String source, String selectedUserIds, String selectedBranchIds, String isRoot) {
		boolean optStatus=false;
		Connection con=null;
		CallableStatement cst=null;
		try{
			con=ConnectionDAO.getConnection();
			con.setAutoCommit(false);
			cst=con.prepareCall("call SALES_DASH_BOARD(?,?,?,?,?,?)");
			cst.setString(1, userId);//I_LOGIN_USER_ID
			cst.setString(2, selectedUserIds);//I_SELECTED_USER_IDS
			cst.setString(3, source);//I_SOURCE
			cst.setString(4, isRoot);//I_IS_ROOT
			cst.registerOutParameter(5, Types.CHAR);
			cst.registerOutParameter(6, Types.CHAR);
			cst.executeUpdate();
			String s1 = cst.getString(5);
			String s2 = cst.getString(6);
			logger.info("s1: "+s1);
			logger.info("s2: "+s2);
			if(s1!=null && s1.equalsIgnoreCase("S"))
			{
				optStatus=true;
				con.commit();
			}else{
				con.rollback();
			}
			s1=null;
			s2=null;
		}catch(Exception e){
			optStatus=false;
			e.printStackTrace();
		}
		finally{
			try{
				if(cst!=null){
					cst.close();
				}
				if(con!=null){
					con.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return optStatus;
	}
	@Override
	public Map<String, String> fetchSalesDashboardData(String userId,String source, String showAll) {
		Map<String,String> salesData=new HashMap<String,String>();
		salesData.put("LD_PD_SB","0");
		salesData.put("PRE_LGN_DL","0");
		salesData.put("PRE_LGM_QR_DL","0");
		salesData.put("OPEN_DEAL","0");
		salesData.put("LD_LGM_MNTH","0");
		salesData.put("LGD_QRY_DL","0");
		salesData.put("LD_RJCT_CNCLD","0");
		salesData.put("DL_IN_CP","0");
		salesData.put("LD_SNCD","0");
		salesData.put("DL_SNCD_UN_DSBSD","0");
		salesData.put("LD_DSBSD","0");
		Connection con=null;
		CallableStatement callableStatement=null;
		ResultSet rs=null;
		try{
			con=ConnectionDAO.getConnection();
			callableStatement = con.prepareCall("{call SALES_DASH_BOARD_DATA(?,?,?,?,?)}");
			callableStatement.setString(1,userId);//I_LOGIN_USER_ID
			callableStatement.setString(2,source);//I_SOURCE
			callableStatement.setString(3,showAll);//I_SHOW_ALL
			callableStatement.setString(4,"C");//I_OPERATION_TYPE
			callableStatement.setString(5,"");//I_OPERATION_SUB_TYPE
			rs = callableStatement.executeQuery();
			while (rs.next()) {
				salesData.put("LD_PD_SB",rs.getInt("LD_PD_SB")+"");
				salesData.put("PRE_LGN_DL",rs.getInt("PRE_LGN_DL")+"");
				salesData.put("PRE_LGM_QR_DL",rs.getInt("PRE_LGM_QR_DL")+"");
				salesData.put("OPEN_DEAL",rs.getInt("OPEN_DEAL")+"");
				salesData.put("LD_LGM_MNTH",rs.getInt("LD_LGM_MNTH")+"");
				salesData.put("LGD_QRY_DL",rs.getInt("LGD_QRY_DL")+"");
				salesData.put("LD_RJCT_CNCLD",rs.getInt("LD_RJCT_CNCLD")+"");
				salesData.put("DL_IN_CP",rs.getInt("DL_IN_CP")+"");
				salesData.put("LD_SNCD",rs.getInt("LD_SNCD")+"");
				salesData.put("DL_SNCD_UN_DSBSD",rs.getInt("DL_SNCD_UN_DSBSD")+"");
				salesData.put("LD_DSBSD",rs.getInt("LD_DSBSD")+"");
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(rs!=null){
					rs.close();
					rs=null;
				}
				if(callableStatement!=null){
					callableStatement.close();
					callableStatement=null;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(con!=null){
					con.close();
					con=null;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return salesData;
	}
	@Override
	public String getDashBoardRootUserId(){
		logger.info("in getDashBoardRootUserId()");
		String rootUserId="";
		try{
			String rootUserIdQry="SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='SALES_DASH_BOARD_ROOT_USER_ID'";
			logger.info("DashBoardRootUserId : "+rootUserIdQry);
			rootUserId=ConnectionDAO.singleReturn(rootUserIdQry);
		}catch(Exception e){
			e.printStackTrace();
		}
		return rootUserId;
	}
	@Override
	public boolean manageSelectedUser(String userId, String source,String userList) {
		boolean optStatus=false;
		Connection connection=null;
		Statement stmt=null;
		String deleteQuery="DELETE FROM STAGE_DASHBOARD_USER_FILTER_DTL WHERE SOURCE='"+source+"' AND LOGIN_USER_ID='"+userId+"'";
		String insertQuery="INSERT INTO STAGE_DASHBOARD_USER_FILTER_DTL(USER_ID,USER_NAME,SOURCE,LOGIN_USER_ID,OPERATION_DATE)"
			+" SELECT USR.USER_ID,USR.USER_NAME,'"+source+"' AS SOURCE,'"+userId+"' AS LOGIN_USER_ID,NOW() OPERATION_DATE"
			+" FROM SEC_USER_M USR"
			+" WHERE USR.REC_STATUS='A' AND INSTR(CONCAT('|','"+userList+"','|'),CONCAT('|',USR.USER_ID,'|'))>0";		
		logger.info(" in manageSelectedUser deleteQuery  : "+deleteQuery);
		logger.info(" in manageSelectedUser insertQuery  : "+insertQuery);
		try{
			connection=ConnectionDAO.getConnection();
			connection.setAutoCommit(false);
			stmt=connection.createStatement();
			stmt.executeUpdate(deleteQuery);
			stmt.executeUpdate(insertQuery);
			connection.commit();
			optStatus=true;
		}catch(Exception e){
			optStatus=false;
			e.printStackTrace();
		}finally{
			try{
				if(stmt!=null){
					stmt.close();
				}
				if(connection!=null){
					connection.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return optStatus;
	}
	@Override
	public boolean checkSwitchAccess(String userId) {
		logger.info("in checkSwitchAccess()");
		boolean switchStatus=false;
		try{
			String rootUserIdQry="SELECT COUNT(1)CT FROM STAGE_DASHBOARD_ROOT_ACCESS WHERE USER_ID='"+userId+"' AND REC_STATUS='A' AND FUNCTION_NAME='SALES_DASHBOARD'";
			logger.info("checkSwitchAccess Qry : "+rootUserIdQry);
			String countStr=ConnectionDAO.singleReturn(rootUserIdQry);
			if(StringUtils.isBlank(countStr)){
				countStr="0";
			}
			countStr=countStr.trim();
			if(Integer.parseInt(countStr)>0){
				switchStatus=true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return switchStatus;
	}
	
}
