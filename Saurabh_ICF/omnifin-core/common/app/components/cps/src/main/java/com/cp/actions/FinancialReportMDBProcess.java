package com.cp.actions;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

import com.cp.vo.FinancialReportVo;
import com.cp.vo.UnderwritingDocUploadVo;
import com.business.utils.async.LMSMessagingClient;
import com.business.utils.async.LMSMessagingConstants;
public class FinancialReportMDBProcess {
	private static final Logger logger = Logger
			.getLogger(FinancialReportMDBProcess.class.getName());
	public static void sendToOCRMDB(ActionMapping mapping, HttpServletRequest request, HttpServletResponse responsef, ActionForm form,Object ob)
	{
		FinancialReportVo vo = (FinancialReportVo)ob;
		logger.info("sendToOCRMDB--------------");
		DynaValidatorForm SourcingProcessDynaValidatorForm = (DynaValidatorForm) form;
		Map detailMap =SourcingProcessDynaValidatorForm.getMap();
		HttpSession session = request.getSession(false);
		
		 sendToMessageQueue(vo, session);

	}
	
	
	private static void sendToMessageQueue(FinancialReportVo vo, HttpSession session ){
		LMSMessagingClient messagingClient = new LMSMessagingClient();
		try {
			messagingClient.sendObjectMessage(vo, LMSMessagingConstants.OCR_QUEUE_REPORT);
		} catch (Exception e) {
			logger.info("error in sendToOCRMDB(UnderwritingDocUploadVo vo)");
			e.printStackTrace();
		}
	}
}
