package com.cm.dao;

import java.util.ArrayList;

import com.cm.vo.ClosureSearchVO;
import com.cm.vo.ClosureVO;

public interface MaturityClosureDAO {
	String IDENTITY="MATURITYCD";
	ArrayList<ClosureVO> selectClosureData(String loanId,String terminationId);
	ArrayList<ClosureSearchVO> searchClosureData(Object ob, String status, String type);
	
}
