package com.cm.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import com.cm.vo.ManualIntCalcVO;

public interface ManualIntCalcDAO {
	String IDENTITY="MANUALINTCD";
	ArrayList<ManualIntCalcVO> getMICValues(int lbxLoanNoHID, String businessDate);
	ArrayList<ManualIntCalcVO> getDetails(ManualIntCalcVO vo) throws SQLException;
	String generateAdvice(ManualIntCalcVO vo);

	
	
	
}
