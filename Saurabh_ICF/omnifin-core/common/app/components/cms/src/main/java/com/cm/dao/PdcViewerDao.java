package com.cm.dao;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.cm.vo.InstructionCapMakerVO;
import com.cm.vo.PaymentMakerForCMVO;
import com.cm.vo.ReceiptMakerVO;
import com.cm.vo.PdcViewerVo;

public interface PdcViewerDao {

	String IDENTITY="PDCVIEWERD";
	ArrayList<PdcViewerVo> searchPDCViewerData(PdcViewerVo vo);

	ArrayList calculatePDC(String loanId);
	
}
