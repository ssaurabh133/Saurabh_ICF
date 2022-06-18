package com.cp.dao;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.cp.vo.DealSanctionLetterVo;

public interface DealSanctionLetterDAO {
	String IDENTITY="DSL"; 
	boolean saveLoanSanctionLetter(DealSanctionLetterVo vo);
	ArrayList getUploadUnderwritingDataForSanctionLetter(String dealId);
	}
