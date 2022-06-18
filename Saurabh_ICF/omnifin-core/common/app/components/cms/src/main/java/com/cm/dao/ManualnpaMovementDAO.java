package com.cm.dao;

import java.util.ArrayList;
import com.cm.vo.ManualnpaMovementVO;

public interface ManualnpaMovementDAO {

	String IDENTITY="MANUALMD";
	ArrayList insertManualNPA(ManualnpaMovementVO vo);

	ArrayList<ManualnpaMovementVO> ManualNpaGetDetails(String loanId);

	ArrayList<ManualnpaMovementVO> selectManualNpa(String manualNpaId,String loanId);

	String updateManualNPA(ManualnpaMovementVO vo);

	ArrayList<ManualnpaMovementVO> searchManualNpa(ManualnpaMovementVO vo);

	ArrayList<ManualnpaMovementVO> searchManualNpaAuthor(ManualnpaMovementVO vo);

	String saveManualNpaAuthor(ManualnpaMovementVO vo);

	boolean forwardManualNPA(ManualnpaMovementVO vo);

	boolean deleteManualNPA(ManualnpaMovementVO vo);

	ArrayList<ManualnpaMovementVO> detailsForLoan(String loanId);
//Ritu
	ArrayList getNpaStage();
	
	
}
