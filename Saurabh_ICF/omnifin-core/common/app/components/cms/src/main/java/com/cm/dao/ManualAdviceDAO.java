package com.cm.dao;

import java.util.ArrayList;

import com.cm.vo.ManualAdviceCreationVo;

public interface ManualAdviceDAO {
	String IDENTITY="MANUALAD";
	public String saveManualAdviceCreationDetail(Object manualAdviceCreationVo);
	public ArrayList<ManualAdviceCreationVo> getManualAdviceCreationMakerDetail(String id);
	public boolean updateManualAdviceCreationDetail(ManualAdviceCreationVo manualAdviceCreationVo,String manaulId,String forward);			
	public ArrayList<ManualAdviceCreationVo> ManualAdviceMakerSearchDetail(ManualAdviceCreationVo vo,String author);
	public String manualAdviceAuthorDecision(Object ob);
//	public boolean rejectManualAdviceByAuthor(Object ob);
	
	ArrayList getManualAdviceDetailScheme(String str, String str1, String str2,String str3);
	boolean deleteManualAdvice(String manualId);
}
