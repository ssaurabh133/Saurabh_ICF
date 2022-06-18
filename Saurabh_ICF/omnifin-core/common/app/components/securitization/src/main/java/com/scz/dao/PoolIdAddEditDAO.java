package com.scz.dao;

import java.util.ArrayList;

import com.scz.vo.PoolIdMakerVO;

public interface PoolIdAddEditDAO {
	
	String IDENTITY="SCZ_POOLEDITD";
	ArrayList<PoolIdMakerVO> searchPoolIdAddEditGrid(PoolIdMakerVO poolIdMakerVO);
	 ArrayList<PoolIdMakerVO> savedPoolIdEditData(PoolIdMakerVO poolIdMakerVO,int poolID);
	 ArrayList getPoolAddEditData(int poolID);
	 boolean insertLoanforPoolIdEdit(PoolIdMakerVO poolIdMakerVO, int companyId);
	 boolean deletePoolIDEdit(PoolIdMakerVO poolIdMakerVO,String[] loanID,String[] poolID,int companyId);

}
