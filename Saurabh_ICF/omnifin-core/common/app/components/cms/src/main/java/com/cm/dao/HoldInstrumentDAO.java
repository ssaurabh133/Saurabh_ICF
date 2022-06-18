package com.cm.dao;

import java.util.ArrayList;

import com.cm.vo.InstructionCapMakerVO;

public interface HoldInstrumentDAO {

	String IDENTITY="HOLD";
	ArrayList<InstructionCapMakerVO> searchHoldInstrumentMaker(
			InstructionCapMakerVO instructionCapMakerVO);



	ArrayList<InstructionCapMakerVO> searchHoldInstrumentAuthor(
			InstructionCapMakerVO instructionCapMakerVO);



	

	ArrayList<InstructionCapMakerVO> getValuesforHoldInstrument(int id);


	ArrayList<InstructionCapMakerVO> getValuesforIndiHoldInstrument(int id,
			InstructionCapMakerVO instructionCapMakerVO);


	boolean updateIndiHoldInstrument(String[] checkedholdList,
			String[] checkedStatusList, String[] instrumentidList,
			String[] newStatusList, InstructionCapMakerVO instructionCapMakerVO);

	
	ArrayList<InstructionCapMakerVO> getValuesforHoldInstrumentAuthor(int id);

	boolean updateCommentNDecisionforHoldIns(
			InstructionCapMakerVO instructionCapMakerVO, String[] instrumentID);

	

}
