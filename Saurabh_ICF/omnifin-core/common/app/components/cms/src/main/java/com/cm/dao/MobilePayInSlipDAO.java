package com.cm.dao;

import java.util.ArrayList;
import com.cm.vo.MobilePayInSlipVo;

public interface MobilePayInSlipDAO {
	String IDENTITY="MOBILEPAYINSLIP";
	public ArrayList<MobilePayInSlipVo> mobilePayInSlipSearchDtl(MobilePayInSlipVo vo,String path);
	public ArrayList<MobilePayInSlipVo> mobilePayInSlipSearch(MobilePayInSlipVo vo, String path);
	public ArrayList<MobilePayInSlipVo> mobileInstruementDtl(String mobileId);
	public ArrayList<MobilePayInSlipVo> mobilePayInSlipPicture(String mobileId);
	
}
