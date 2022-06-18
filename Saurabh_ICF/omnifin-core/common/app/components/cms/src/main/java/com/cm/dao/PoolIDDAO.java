package com.cm.dao;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.upload.FormFile;

import com.cm.vo.PoolIdMakerVO;

public interface PoolIDDAO {
	
	String IDENTITY="POOLD";
	ArrayList<PoolIdMakerVO> searchPoolIdMakerGrid(PoolIdMakerVO poolIdMakerVO);	
	String getPoolNo();	
	ArrayList downLoadPoolErrorLog(HttpServletRequest request,HttpServletResponse response, String userId);
	ArrayList<PoolIdMakerVO> searchPoolIdAuthorGrid(PoolIdMakerVO poolIdMakerVO);	
	boolean forwardPoolIdMaker(String poolID);
	boolean deletePoolID(String[] loanID, String[] poolID);
	ArrayList getPoolData(String poolID,Object poolIdMakerVO);
	ArrayList<PoolIdMakerVO> savedPoolIdMakerData(PoolIdMakerVO poolIdMakerVO,String poolID);
	boolean insertLoanforPoolId(PoolIdMakerVO poolIdMakerVO, String poolID);
	ArrayList retriveValueByLoanforPool(String lbxLoanNoHID);
	ArrayList<PoolIdMakerVO> savedPoolIdAuthorData(PoolIdMakerVO poolIdMakerVO,String poolID);
	ArrayList<PoolIdMakerVO> getAuthorPoolData(PoolIdMakerVO poolIdMakerVO,String poolID);
	String  onSaveOfPoolIdAuthor(PoolIdMakerVO poolIdMakerVO,int companyId);
	boolean uploadCsv_Securitization(HttpServletRequest request,HttpServletResponse response,String strFileName,PoolIdMakerVO  excelForm);
	boolean docUploadForExcel(HttpServletRequest request, FormFile docFile);
	String saveSecuritization(HttpServletRequest request, PoolIdMakerVO  excelForm);
	boolean deletePoolIDEdit(PoolIdMakerVO poolIdMakerVO,String[] loanID,String[] poolID,int companyId);
	 ArrayList<PoolIdMakerVO> savedPoolIdEditData(PoolIdMakerVO poolIdMakerVO,String poolID);
	 ArrayList getPoolAddEditData(String poolID);
}
