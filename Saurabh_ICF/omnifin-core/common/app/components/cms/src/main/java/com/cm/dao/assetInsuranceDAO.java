package com.cm.dao;


import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.upload.FormFile;

import com.cm.vo.*;

public interface assetInsuranceDAO {
	String IDENTITY="ASSETD";

	public String getSaveAssetMaker(Object ob);
	public String getAssetInsuranceId();
	ArrayList<AssetForCMVO> searchAssetData(AssetForCMVO assetMakervo);
	public String  saveForwardDataUpdate(AssetForCMVO assetMakervo);
	String approveByAuthor(AssetForCMVO assetMakervo,String userId,int companyId,String currDate,String assetInsuranceID,String comments,String status);
	ArrayList<AssetForCMVO> assetAuthorGrid(AssetForCMVO assetMakervo);
	ArrayList<AssetForCMVO> savedDatatoApprove(AssetForCMVO assetMakervo);
	boolean cancelAssets(AssetForCMVO assetMakervo);
	public ArrayList<Object> getInsuranceDoneByList();
	ArrayList<AssetForCMVO> assetMakerGrid(AssetForCMVO assetMakervo);
	public ArrayList<AssetForCMVO> getAssetInsuranceViewer(AssetForCMVO assetMakervo);
	public ArrayList<AssetForCMVO> getAssetInsuranceViewerMaker(AssetForCMVO assetMakervo);
	public ArrayList<AssetForCMVO> onInsurancePolicyViewer(AssetForCMVO assetMakervo);
	public ArrayList<AssetForCMVO> assetVehicleDetails(AssetForCMVO assetMakervo, String lbxEntity);
	public ArrayList<AssetForCMVO> showAssetDataInGrid(AssetForCMVO assetMakervo);		
	public ArrayList<AssetForCMVO> getMaxYearNo(String loanId);
	boolean uploadCersaiReport(String fileName, String UserId);
	boolean docUploadExcel(HttpServletRequest request,FormFile myFile);
	
}
