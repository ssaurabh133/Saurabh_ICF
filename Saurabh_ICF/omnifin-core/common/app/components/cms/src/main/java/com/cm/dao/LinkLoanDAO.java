package com.cm.dao;

import java.util.ArrayList;

import com.cm.vo.LinkLoanVo;
import com.cm.vo.UpdateAssetVO;
import com.cp.vo.CollateralVo;

public interface LinkLoanDAO {
	
	String IDENTITY="LLD";
	
	ArrayList getLinkLoans(String loanId);
	ArrayList<LinkLoanVo> getLinkLoanDetails(String loanId,String  loanIdToadd);
	ArrayList<LinkLoanVo> getLoanDetails(LinkLoanVo linkLoanVo);
	boolean saveLinkLoanDetail(LinkLoanVo linkLoanVo);
	ArrayList<LinkLoanVo> getAfterSaveData(LinkLoanVo linkLoanVo,String searchFlag);
	boolean forwardLinkLoanData(String loanId);
	ArrayList<LinkLoanVo> getAuthorLoanDetails(LinkLoanVo linkLoanVo);
	ArrayList<LinkLoanVo> getEditData(String loanId);
	String saveLinkLoanAuthor(LinkLoanVo linkLoanVo);
	
	ArrayList<UpdateAssetVO> getLoanDetails(UpdateAssetVO updateAssetVO);
	ArrayList<UpdateAssetVO> openEditVehical(UpdateAssetVO updateAssetVO);
	public boolean updateAsset(Object ob);
	String checkRegNo(UpdateAssetVO updateAssetVO);
	ArrayList<UpdateAssetVO> getLoanDetailsAuthor(UpdateAssetVO updateAssetVO);
	ArrayList<UpdateAssetVO> getAuthorScreenAssetVehical(UpdateAssetVO updateAssetVO);
	public boolean saveUpdateAsset(Object updateAssetVO);
	//ArrayList<UpdateAssetVO> getUpdateAssetVehical(String lbxLoanId,String lbxAssetId);
	ArrayList<UpdateAssetVO> getUpdateAssetVehical(Object ob);
	 String checkChesisNoVehicle(UpdateAssetVO updateAssetVO);
		String checkEnginNoVehicle(UpdateAssetVO updateAssetVO);
		String checkRegNoVehicle(UpdateAssetVO updateAssetVO);
		ArrayList<UpdateAssetVO> fetchPDDUpdateData(Object ob);
		boolean deletePddAsset(String loanId,String assetId);
		public abstract ArrayList<UpdateAssetVO> getUpdatedVehical(String paramString);
	}
