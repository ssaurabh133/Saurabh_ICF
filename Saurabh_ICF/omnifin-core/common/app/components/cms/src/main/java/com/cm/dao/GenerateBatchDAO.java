package com.cm.dao;

import java.util.ArrayList;

import com.cm.vo.GenerateBatchVO;

public interface GenerateBatchDAO {
	String IDENTITY="GBATCH";

	String generateBatch(GenerateBatchVO vo);

	ArrayList<GenerateBatchVO> viewBatch(String batchNo, int currentPageLink);

	boolean finalizedBatch(String batchNo);

	boolean deleteBatch(String batchIdList);

	String presentationProcess(GenerateBatchVO vo);

	boolean saveDepositBank(GenerateBatchVO vo);

	ArrayList getBankDetail(String batchId);

	String getTotalInstrumentAmount(String retVal);

	String getTotalInstrument(String retVal);
	boolean generateMultipleBatch(String prestDate, String instrumentMode,
			String clearingType, String bankId, String userId,
			String businessDate);
}
