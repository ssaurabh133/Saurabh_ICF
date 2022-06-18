package com.communication.engn.dao;

import java.util.ArrayList;

import com.communication.engn.vo.EmailVO;

 public interface EmailDAO
 {
	String IDENTITY="COMMEMAIL";
	
	String variableReplaceInFile(EmailVO vo);
	EmailVO getSmtpParameter();	
	ArrayList getEmailList();
	void updateEmailFlag();
	ArrayList getEmailListRejection();
}
