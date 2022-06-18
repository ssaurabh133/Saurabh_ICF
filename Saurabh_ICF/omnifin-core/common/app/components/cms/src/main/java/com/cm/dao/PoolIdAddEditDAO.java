package com.cm.dao;

import java.util.ArrayList;

import com.cm.vo.PoolIdMakerVO;

public interface PoolIdAddEditDAO {
	
	String IDENTITY="POOLEDITD";
	ArrayList<PoolIdMakerVO> searchPoolIdAddEditGrid(PoolIdMakerVO poolIdMakerVO);
	 ArrayList<PoolIdMakerVO> savedPoolIdEditData(PoolIdMakerVO poolIdMakerVO,String poolID);
	 ArrayList getPoolAddEditData(String poolID);
	 boolean insertLoanforPoolIdEdit(PoolIdMakerVO poolIdMakerVO, int companyId);
	 boolean deletePoolIDEdit(PoolIdMakerVO poolIdMakerVO,String[] loanID,String[] poolID,int companyId);

}
