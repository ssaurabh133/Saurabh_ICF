package com.popup.dao;

import java.util.ArrayList;
import com.popup.vo.PopupVo;


public interface popupDao {
	String IDENTITY = "POPUPD";
	public ArrayList<Object> fetchData(String hdnLOVId,String pParentGroup,String strParentOption,String branchId,int currentPageLink,String method,ArrayList childLOVParam,int lovCount);
	
	public ArrayList<Object> fetchDataByParameter(String lovCode, String lovDesc , String lovId,String strParentOption,int currentPageLink,String branchId, String branchId2, ArrayList childLOVParam,int lovCount);
	
	public ArrayList<Object> MultiSelectfetchDataByParameter(String lovCode, String lovDesc , String lovId,String strParentOption,int currentPageLink,String method, String LovListItemsIds,String branchId, ArrayList childLOVParam);

	public ArrayList<Object> multiSelectFetchData(String parameter,	String pParentGroup, String strParentOption, String branchId,String lovListItemsIds, ArrayList childLOVParam);
	

}
