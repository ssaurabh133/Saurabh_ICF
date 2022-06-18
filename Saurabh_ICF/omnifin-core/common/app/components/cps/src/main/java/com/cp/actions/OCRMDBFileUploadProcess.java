package com.cp.actions;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

import com.login.roleManager.UserObject;
import com.cp.vo.CommonDealVo;
import com.cp.vo.MDBVO;
import com.cp.vo.UnderwritingDocUploadVo;
import com.business.utils.async.LMSMessagingClient;
import com.business.utils.async.LMSMessagingConstants;


public class OCRMDBFileUploadProcess {
	private static final Logger logger = Logger
			.getLogger(OCRMDBFileUploadProcess.class.getName());
	public static void sendToUploadOCRMDB(ActionMapping mapping, HttpServletRequest request, HttpServletResponse responsef, ActionForm form,Object ob)
	{
		MDBVO vo = (MDBVO)ob;
		logger.info("sendToOCRMDB--------------");
		DynaValidatorForm SourcingProcessDynaValidatorForm = (DynaValidatorForm) form;
		Map detailMap =SourcingProcessDynaValidatorForm.getMap();
		HttpSession session = request.getSession(false);
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		
		 sendToMessageUploadQueue(vo, session);

	}
	
	
	private static void sendToMessageUploadQueue(MDBVO vo, HttpSession session ){
		LMSMessagingClient messagingClient = new LMSMessagingClient();
		//MDBVO mdbvo =vo.getMdbvo(vo);
		try {
			messagingClient.sendObjectMessage(vo, LMSMessagingConstants.OCR_QUEUE_UPLOAD);
		} catch (Exception e) {
			logger.info("error in sendToOCRMDB(CommonDealVo vo)");
			e.printStackTrace();
		}
	}
}
