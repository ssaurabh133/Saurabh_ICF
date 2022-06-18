package com.customerService.dao;

import java.util.ArrayList;

import com.customerService.vo.CustomerServiceVo;



public interface CustomerServiceDAO 
{
	String IDENTITY="CUSTOMERSERVICE";

	ArrayList<CustomerServiceVo> loanSummaryViewer(CustomerServiceVo vo);

	ArrayList<CustomerServiceVo> caseMarkingDetail(CustomerServiceVo vo);

	ArrayList<CustomerServiceVo> secuitizationDetail(CustomerServiceVo vo);

	ArrayList<CustomerServiceVo> reshcedulingDetail(CustomerServiceVo vo);

	ArrayList<CustomerServiceVo> closureDetail(CustomerServiceVo vo);

	ArrayList<CustomerServiceVo> customerExposureListViewer(CustomerServiceVo vo);
	
	ArrayList<CustomerServiceVo> vehicleDetails(CustomerServiceVo ob);

	ArrayList<CustomerServiceVo> notePadListLoanSummaryDetail(CustomerServiceVo vo);	
}

