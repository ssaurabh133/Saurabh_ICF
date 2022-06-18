package com.cm.dao;

import java.util.ArrayList;

import com.cm.vo.InstructionCapMakerVO;

public interface ReleaseInstrumentDAO {

	String IDENTITY="RELEASEINTD";
	ArrayList<InstructionCapMakerVO> searchReleaseInstrumentMaker(
			InstructionCapMakerVO instructionCapMakerVO);

	ArrayList<InstructionCapMakerVO> searchReleaseInstrumentAuthor(
			InstructionCapMakerVO instructionCapMakerVO);

	

	ArrayList<InstructionCapMakerVO> getValuesforReleaseInstrument(int id);

	ArrayList<InstructionCapMakerVO> getValuesforIndiReleaseInstrument(int id,
			InstructionCapMakerVO instructionCapMakerVO);

	boolean updateIndiReleaseInstrument(String[] checkedholdList,
			String[] checkedStatusList, String[] instrumentidList,
			String[] newStatusList, InstructionCapMakerVO instructionCapMakerVO);

	ArrayList<InstructionCapMakerVO> getValuesforReleaseInstrumentAuthor(int id);

	boolean updateCommentNDecisionforReleaseIns(
			InstructionCapMakerVO instructionCapMakerVO, String[] instrumentID);

}
