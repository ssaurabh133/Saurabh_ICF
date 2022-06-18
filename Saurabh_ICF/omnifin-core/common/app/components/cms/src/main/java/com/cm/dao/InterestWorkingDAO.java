/**
 * 
 */
package com.cm.dao;

import java.util.ArrayList;

import com.cm.vo.InterestWorkingVO;

/**
 * @author pranaya.gajpure
 *
 */
public interface InterestWorkingDAO {
	
	String IDENTITY="IWD";
	ArrayList generateInterestWorkingDao(InterestWorkingVO interestVO);
	//public int generateInterestWorkingCount(InterestWorkingVO ivo);
	ArrayList getInterestCustomerList(InterestWorkingVO interestVO);
	ArrayList reportledgerDump();
}
