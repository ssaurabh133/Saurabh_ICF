package com.cm.dao;

import java.rmi.RemoteException;
import java.sql.SQLException;


import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.upload.FormFile;

import com.cm.vo.*;
import com.cp.vo.ChargeVo;
import com.cp.vo.CodeDescVo;
import com.cp.vo.DocumentsVo;
import com.cp.vo.CollateralVo;
import com.cp.vo.RepayScheduleVo;


public interface assetVerificationDAO {
	//change by sachin
	 String IDENTITY="ASSETVD";
	//end by sachin
	String modifyAssetVerification(AssetVerificationVO assetVerificationVO,String assetVerInitId,String loanId,String[] assetIDList, String appraisertype, String appraiserid, String appraiser);
	boolean modifyForwaAssetVerification(AssetVerificationVO assetVerificationVO,String appraiserId,String loanId,String[] assetIDList, String appraiser, String appr);
	String saveAssetVerification(String[] loanIDList, String[] assetIDList,String[] assetDescriptionList,
			AssetVerificationVO assetVerificationVO);
	boolean forwardAssetVerification(String[] loanIDList,String[] loanAccNoList, String[] assetIDList,String[] assetDescriptionList,
			AssetVerificationVO assetVerificationVO);
	
	ArrayList assetVerifcationSearch(AssetVerificationVO assetVerificationVO,HttpServletRequest request);
	
	ArrayList searchAssetVerRepCap(AssetVerificationVO assetVerificationVO);

	ArrayList repCapAsset(AssetVerificationVO assetVerificationVO,String assetVerificationID,String makerID);
	
	boolean saveAssetRepCap(AssetVerificationVO assetVerificationVO,String assetVerificationID);

	ArrayList searchAssetVerRepComplete(AssetVerificationVO assetVerificationVO);
	
	boolean forwardAssetRepCap(AssetVerificationVO assetVerificationVO,String assetVerificationID);
	
	ArrayList repCapAssetComplete(AssetVerificationVO assetVerificationVO,String assetVerificationID);

	boolean saveAssetCapRepAuthor(AssetVerificationVO assetVerificationVO,String assetVerificationID);
	ArrayList<AssetVerificationVO> getValueForAssetVerInit(String lbxLoanID);
	ArrayList searchAssetVerModifyData(AssetVerificationVO assetVerificationVO);
	String assetnotinserted(String[] loanIDList, String[] loanAccNoList,
			String[] assetIDList, String[] assetDescriptionList,
			AssetVerificationVO assetVerificationVO) throws SQLException;
	boolean uniqueassetcount(String[] loanIDList, String[] loanAccNoList,
			String[] assetIDList, String[] assetDescriptionList,
			AssetVerificationVO assetVerificationVO);

	ArrayList AssetDataGrid(AssetVerificationVO assetVerificationVO);
	ArrayList<AssetVerificationVO> getAssetdatagridaftersave(
			AssetVerificationVO assetVerificationVO);
	
	boolean deleteassetid(String[] assetIDList, String loanid, String appraiserid,
			String apprtype) throws RemoteException, SQLException;
}