package com.scz.dao;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.upload.FormFile;

import com.scz.vo.PoolIdMakerVO;

public interface PoolIDDAO {
	
	String IDENTITY="SCZ_POOLD";
	ArrayList<PoolIdMakerVO> searchPoolIdMakerGrid(PoolIdMakerVO poolIdMakerVO);	
	String getPoolNo();	
	ArrayList downLoadPoolErrorLog(HttpServletRequest request,HttpServletResponse response, String userId, String poolId);
	ArrayList<PoolIdMakerVO> searchPoolIdAuthorGrid(PoolIdMakerVO poolIdMakerVO);	
	boolean forwardPoolIdMaker(int poolID);
	boolean deletePoolID(int poolID);
	ArrayList getPoolData(int poolID,Object poolIdMakerVO);
	ArrayList<PoolIdMakerVO> savedPoolIdMakerData(PoolIdMakerVO poolIdMakerVO,int poolID);
	boolean insertLoanforPoolId(PoolIdMakerVO poolIdMakerVO, int poolID);
	ArrayList retriveValueByLoanforPool(String lbxLoanNoHID);
	ArrayList<PoolIdMakerVO> savedPoolIdAuthorData(PoolIdMakerVO poolIdMakerVO,int poolID);
	ArrayList<PoolIdMakerVO> getAuthorPoolData(PoolIdMakerVO poolIdMakerVO,int poolID);
	String  onSaveOfPoolIdAuthor(PoolIdMakerVO poolIdMakerVO,int companyId);
	boolean uploadCsv_Securitization(HttpServletRequest request,HttpServletResponse response,String strFileName,PoolIdMakerVO  excelForm);
	boolean uploadCsv_Repayment(HttpServletRequest request,HttpServletResponse response,String strFileName,PoolIdMakerVO  excelForm);
	boolean uploadCsv_BankPool(HttpServletRequest request,HttpServletResponse response,String strFileName,PoolIdMakerVO  excelForm);
	boolean docUploadForExcel(HttpServletRequest request, FormFile docFile);
	String saveSecuritization(HttpServletRequest request, PoolIdMakerVO poolIdMakerVO);
	String saveSecuritizationRepayment(HttpServletRequest request, PoolIdMakerVO poolIdMakerVO);
	String saveSecuritizationBankUpload(HttpServletRequest request, PoolIdMakerVO poolIdMakerVO);
	boolean deletePoolIDEdit(PoolIdMakerVO poolIdMakerVO,String[] loanID,String[] poolID,int companyId);
	 ArrayList<PoolIdMakerVO> savedPoolIdEditData(PoolIdMakerVO poolIdMakerVO,int poolID);
	 ArrayList getPoolAddEditData(int poolID);
	 ArrayList getUploadDetails(int poolID);
	 
	 public HSSFWorkbook getBankUploadFormat(PoolIdMakerVO aa);
	 public String getCutOffDate(String date);
}
