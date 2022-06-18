package com.masters.dao;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.masters.vo.VehicleApprovalGridVo;

public interface VehicleDAO {

	String IDENTITY="VD"; 
		
	public boolean insertVehicleApprovalGrid (Object ob);
	public ArrayList  getVehicleApprovalSearchGrid(Object ob);
	public boolean updateVehicleApprovalGrid(Object ob);	
	String getNoOfYearAtUsedVehicle();
	ArrayList<VehicleApprovalGridVo> selectVehicleApprovalGrid(String vehicleApprovalID);
		
}

