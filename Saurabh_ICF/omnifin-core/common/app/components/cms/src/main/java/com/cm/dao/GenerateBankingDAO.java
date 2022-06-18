package com.cm.dao;

import java.util.ArrayList;

import com.cm.vo.GenerateReportVO;

public interface GenerateBankingDAO {

	String IDENTITY="GBD";
	ArrayList generateReportDao(GenerateReportVO generateReportVO);

}
