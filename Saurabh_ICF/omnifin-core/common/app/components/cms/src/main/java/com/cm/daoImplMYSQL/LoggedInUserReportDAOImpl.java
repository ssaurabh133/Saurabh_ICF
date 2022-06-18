package com.cm.daoImplMYSQL;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.connect.CommonFunction;
import com.connect.ConnectionReportDumpsDAO;
import com.cm.dao.LoggedInUserReportDAO;
import com.cm.vo.LoggedInUserReportVO;


public class LoggedInUserReportDAOImpl implements LoggedInUserReportDAO{
	private static final Logger logger = Logger.getLogger(LoggedInUserReportDAOImpl.class.getName());
	public ArrayList getLoggedUser() 
	{

		ArrayList mainList = new ArrayList();
		ArrayList list = new ArrayList();		
		ArrayList subList = new ArrayList();
		StringBuilder query = new StringBuilder(); 
		LoggedInUserReportVO vo=null;
		try {

			
			query.append("select distinct sec_user_m.USER_NAME,sec_user_m.USER_EMP_ID,com_branch_m.BRANCH_DESC as User_Branch," +
					"com_department_m.DEPARTMENT_DESC,sec_user_m.USER_REPORTING_TO,sec_user_m.USER_PHONE1 as Mobile_no," +
					"case when sec_user_log.LOGIN_STATUS='Y' then 'YES ' when sec_user_log.LOGIN_STATUS='N' then 'NO ' end as LOGIN_STATUS,sec_user_log.IP_ADDRESS " +
					"from sec_user_m join sec_user_log on (sec_user_m.USER_ID=sec_user_log.USER_ID)left outer join com_department_m on( sec_user_m.USER_DEPARTMENT=com_department_m.DEPARTMENT_ID)" +
					"left outer join com_branch_m on (sec_user_m.USER_DEF_BRANCH=com_branch_m.BRANCH_ID)where sec_user_log.LOGIN_STATUS='Y'");
			
			logger.info("In  getLoggedUser query "+query);			
			list = ConnectionReportDumpsDAO.sqlSelect(query.toString());			
			int size = list.size();			
			for (int i = 0; i < size; i++) 
			{
				subList = (ArrayList) list.get(i);
				if (subList.size() > 0) 
				{
					vo = new LoggedInUserReportVO();
					vo.setUserName((CommonFunction.checkNull(subList.get(0))).trim());
					vo.setUserEmpID((CommonFunction.checkNull(subList.get(1))).trim());
					vo.setBranchName((CommonFunction.checkNull(subList.get(2))).trim());
					vo.setDepartment((CommonFunction.checkNull(subList.get(3))).trim());
					vo.setReportingTo((CommonFunction.checkNull(subList.get(4))).trim());
					vo.setMobileNo((CommonFunction.checkNull(subList.get(5))).trim());
					vo.setLoginStatus((CommonFunction.checkNull(subList.get(6))).trim());
					vo.setIpAddress((CommonFunction.checkNull(subList.get(7))).trim());
					mainList.add(vo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			list = null;
			subList = null;
			query = null;
			vo = null;
	   }
		return mainList;
	}	
}