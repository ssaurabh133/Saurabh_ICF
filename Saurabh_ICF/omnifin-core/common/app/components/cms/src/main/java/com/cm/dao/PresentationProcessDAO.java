package com.cm.dao;

import com.cm.vo.PresentationProcessVO;

public interface PresentationProcessDAO {

	String IDENTITY="PPROCESSD";
	String generateProcess(PresentationProcessVO presentationProcessVO);

	String getMaxBatchId(String maker_id);

}
