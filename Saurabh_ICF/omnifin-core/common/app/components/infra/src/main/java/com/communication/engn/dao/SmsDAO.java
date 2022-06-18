package com.communication.engn.dao;

import java.util.ArrayList;

import com.communication.engn.vo.EmailVO;
import com.communication.engn.vo.SmsVO;

 public interface SmsDAO {
	 String IDENTITY="COMMSMS";
	 String callProcCommEngnProcess();
	 ArrayList<SmsVO> getSmsData(String eventQuery);
	 void writeToFile(ArrayList<SmsVO> vo, String eventName);
	ArrayList getSmsEventList();
	void proForSmsEmailBOD(String userId,String bDate);
	}
