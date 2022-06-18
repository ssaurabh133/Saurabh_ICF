package com.masters.dao;


import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.masters.vo.HolidayMasterVo;


public interface HolidayMasterDAO {	
	
	String IDENTITY = "HDM";
	public boolean insertHolidayMaster(HolidayMasterVo vo);
	public ArrayList searchHolidayData(HolidayMasterVo ob);
	public boolean updateHolidyData(HolidayMasterVo ob) ;
	public boolean searchForHolidayMaster(String holidayDate);

}
