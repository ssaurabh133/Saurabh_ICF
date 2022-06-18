package com.masters.dao;

import java.util.ArrayList;

import com.masters.vo.MakeModelmasterVO;
import com.masters.vo.ManufacturerSupplierMappingVO;


public interface ManufacturerSupplierMappingMasterDAO 
{

	String IDENTITY = "MSM";
	boolean saveMfrSuppMappingRecord(ManufacturerSupplierMappingVO vo, String[] supplier);
	ArrayList<ManufacturerSupplierMappingVO> getManufacturerSupplierMappingRecords(ManufacturerSupplierMappingVO vo, int linksize);
	ArrayList<ManufacturerSupplierMappingVO> searchManufacturerSupplierMappingList(ManufacturerSupplierMappingVO vo);
	ArrayList <ManufacturerSupplierMappingVO> searchSupplierDescEdit(String mappingId);
	boolean updateMfrSuppMappingRecord(ManufacturerSupplierMappingVO vo, String[] supplier);
		
	
}
