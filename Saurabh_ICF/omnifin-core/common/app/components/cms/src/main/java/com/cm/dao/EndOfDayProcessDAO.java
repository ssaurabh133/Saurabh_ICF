package com.cm.dao;

import java.util.ArrayList;

import com.cm.vo.InstallmentPlanForCMVO;
import com.cm.vo.processVO;

public interface EndOfDayProcessDAO {
	String IDENTITY="EOD";
	ArrayList showEodBodData(String businessDate);

	 ArrayList showProcessData(processVO vo);
	 ArrayList showErrorData(String ProcessName);
	 public String getProcessStatus();
	 public void updateUserLoginStatus(processVO vo);

	 ArrayList showProcessDataAfterConfirm(processVO vo);
	
	 String getEodRunningFlag();
	
     boolean  updateEodRunningFlag(String flagValue,String userId,String businessDate);
	
	

	 public String getSmtpHost();
	 public String getSmtpPort();
	 public String getSmtpMail();
	 public String getSmtpPwd();
	 public ArrayList<String> getFileAttachment(String mailTo,String businessDate);
	 public ArrayList<String> getMailToDetail();
	 public boolean checkBdateStatus(String businessDate);
}
