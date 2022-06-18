package com.caps.dao;

import java.util.ArrayList;

import com.caps.VO.CollectionSummaryVO;
import com.caps.VO.ReallocationMasterVo;



public interface CollReportDAO {
	
	String IDENTITY="COLLREPORTDAO";
	
	   ArrayList<ReallocationMasterVo> custCategoryReport();
	   ArrayList<ReallocationMasterVo> npaStageReport();
	   ArrayList<ReallocationMasterVo> productReport();
	   ArrayList<ReallocationMasterVo> getProductName();
	   ArrayList<ReallocationMasterVo> getLoanClassification();
	   CollectionSummaryVO getCollectionSummary(CollectionSummaryVO collectionSummaryVO);
	
}
