package com.cm.dao;

import java.util.ArrayList;

import com.cm.vo.InstructionCapMakerVO;

public interface DeleteInstrumentDAO {
	String IDENTITY="DID";
	ArrayList<InstructionCapMakerVO> searchDeleteMaker(
			InstructionCapMakerVO instructionCapMakerVO);

	ArrayList<InstructionCapMakerVO> searchDeleteAuthor(
			InstructionCapMakerVO instructionCapMakerVO);



	ArrayList<InstructionCapMakerVO> getValuesforDeleteInstrument(int id);

	ArrayList<InstructionCapMakerVO> searchIndiDeleteInstrument(int id,
			InstructionCapMakerVO instructionCapMakerVO);

	boolean updateIndiDeleteInstrument(String[] checkedholdList,
			String[] checkedStatusList, String[] instrumentidList,
			String[] newStatusList, InstructionCapMakerVO instructionCapMakerVO);

	ArrayList<InstructionCapMakerVO> getValuesforDeleteInstrumentAuthor(int id);

	boolean updateCommentNDecisionforDeleteIns(
			InstructionCapMakerVO instructionCapMakerVO, String[] instrumentID);

}
