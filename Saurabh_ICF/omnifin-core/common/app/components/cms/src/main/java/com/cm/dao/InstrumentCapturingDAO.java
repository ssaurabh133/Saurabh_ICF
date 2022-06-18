package com.cm.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import com.cm.vo.InstructionCapMakerVO;

public interface InstrumentCapturingDAO {
	String IDENTITY="INSTRCD";
	boolean updateFlag(int id);
	ArrayList<InstructionCapMakerVO> searchInstrument(
			InstructionCapMakerVO instructionCapMakerVO) throws SQLException;

	ArrayList<InstructionCapMakerVO> searchInstrumentAuthor(InstructionCapMakerVO instructionCapMakerVO);

	ArrayList instrumentPurposeList();

	ArrayList<InstructionCapMakerVO> getValuesforInstrumentforloanViewer(int id);
	

	ArrayList instrumentecsTransactionCodeList();

	ArrayList instrumentcustomeracTypeList();

	ArrayList instrumentspnserBnkBrncCodeList();

	ArrayList instrumentutilityNoList();

	String pdcPartialForward();
	String getInstallmentDiff ();


	ArrayList<InstructionCapMakerVO> getValuesforUpdate(int id);

	ArrayList insNonIns(int id);

	ArrayList<InstructionCapMakerVO> getLoanAccountList();

	boolean deleteInstrument(String string);

	ArrayList<InstructionCapMakerVO> getValuesforAuthorUpdate(int id);

	boolean updateFlag(int id, InstructionCapMakerVO vo, String[] checkedIDArr,	String[] checkedDateArr);

	boolean saveForLinkedLan(String[] loanIdList, String[] dateList,
			String[] installmentNoList, String[] installmentAmountMainList,
			String[] allotedAmountList, String instrumentID,
			InstructionCapMakerVO instructionCapMakerVO);



	
	
	boolean updateCommentNDecision(InstructionCapMakerVO instructionCapMakerVO,
			int loanID);

	ArrayList<InstructionCapMakerVO> insertListToGeneratePDC(InstructionCapMakerVO instructionCapMakerVO);

	//Ravindra
	ArrayList<InstructionCapMakerVO> searchInstrumentToUpdate(InstructionCapMakerVO instructionCapMakerVO) throws SQLException;

	ArrayList<InstructionCapMakerVO> OpenInstrumentForEdit(int id,String fromIns,String toIns,String InsMode);
	
	boolean UpdateListToGeneratePDC(InstructionCapMakerVO instructionCapMakerVO);
	
	boolean insertInstrumentIntoTempForUpdate(InstructionCapMakerVO instructionCapMakerVO);
	
	boolean forwardInstruments(int id, InstructionCapMakerVO vo);
	
	boolean deleteInstrumentFromTemp(String string);
	
	ArrayList<InstructionCapMakerVO> searchInstrumentForEdit(int id,String fromIns,String toIns,String InsMode,String readyToForard,String purpose);
	
	ArrayList<InstructionCapMakerVO> searchUpdateInstrumentAuthor(InstructionCapMakerVO instructionCapMakerVO);
	
	ArrayList<InstructionCapMakerVO> getValueForUpdateAuthor(int id);
	
	boolean authorUpdateInstruments(InstructionCapMakerVO instructionCapMakerVO,int loanID);
	//method added by neeraj tripathi
	ArrayList<InstructionCapMakerVO> getClearingType();
	//method added by Anil Kumar
	ArrayList getNameOfPDCGiven(int loanId, String submitBy);
}
