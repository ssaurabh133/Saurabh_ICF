package com.masters.dao;

import java.util.ArrayList;

import com.masters.vo.UserApprovalMatrixVo;


public interface UserApprovalMatrixDAO
{
	String IDENTITY="USERAMD";
	boolean saveUserApprovalMatrix(UserApprovalMatrixVo vo,String flag);

	ArrayList<UserApprovalMatrixVo> getApprovedUser(int currentPageLink, String flag);

	ArrayList getDetail(String userId, String userRole,String flag);

	boolean updateUserApprovedRecords(UserApprovalMatrixVo vo,String flag);

	ArrayList<UserApprovalMatrixVo> getsearchApprovedUser(UserApprovalMatrixVo vo, String flag, String recStatus,String makerAuthorFlag);

	int checkUserId(String userId, String role);

	ArrayList<UserApprovalMatrixVo> getBranches(String userId);

	ArrayList<UserApprovalMatrixVo> getProducts(String userId);
	String makerAuthorFlag();
	boolean forwardUserApprovedRecords(UserApprovalMatrixVo fieldVo);
	ArrayList<UserApprovalMatrixVo> getAuthorSearchUser(int currentPageLink,String userId,String userRole);
	ArrayList getAuthorDetail(String userId, String userRole);
	String saveUserApprovalAuthor(Object ob,String userId,String role);
}
