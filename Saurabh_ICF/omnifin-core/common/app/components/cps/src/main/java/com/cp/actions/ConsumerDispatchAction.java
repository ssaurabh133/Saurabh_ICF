package com.cp.actions;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;

import com.cibil.dao.CibilVerificationDAO;
import com.cibil.vo.CibilVerificationVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.connect.UploadDocument;
import com.cp.dao.CreditProcessingDAO;
import com.cp.vo.ConsumerVo;
import com.gcd.dao.CorporateDAO;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class ConsumerDispatchAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(ConsumerDispatchAction.class.getName());
	public ActionForward saveDocDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		logger.info("In ConsumerDispatchAction saveDocDetails().... ");
		ArrayList dispData=new ArrayList();
		HttpSession session = request.getSession();
		String userId="";
		String makerDate="";
		String companyId = "";
		int fileOne=0;
		int fileTwo=0;
		int fileThree=0;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			makerDate=userobj.getBusinessdate();
			companyId = ""+userobj.getCompanyId();
		}else{
			logger.info("here in saveDocDetails method of ConsumerDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
		
		logger.info("strFlag .............. "+strFlag);
		if(!strFlag.equalsIgnoreCase(""))
		{
			if(strFlag.equalsIgnoreCase("sameUserSession"))
			{
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			}
			else if(strFlag.equalsIgnoreCase("BODCheck"))
			{
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}
		
		String dealId = "";

		if (session.getAttribute("dealId") != null) {

			dealId = session.getAttribute("dealId").toString();
		} else if (session.getAttribute("maxId") != null) {
			dealId = session.getAttribute("maxId").toString();
		}

		logger.info("In ConsumerDispatchAction  execute id: " + dealId);

		
		ConsumerVo consumerVo = new ConsumerVo();
		DynaValidatorForm CibilReportDynaValidatorForm = (DynaValidatorForm) form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(consumerVo,CibilReportDynaValidatorForm);
		consumerVo.setDealId(dealId);
		consumerVo.setUserName(userId);
		consumerVo.setMakerDate(makerDate);
		consumerVo.setCompanyId(companyId);
		String msg = "";
		CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
		CibilVerificationDAO dao=(CibilVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(CibilVerificationDAO.IDENTITY);
		CorporateDAO dao1=(CorporateDAO)DaoImplInstanceFactory.getDaoImplInstance(CorporateDAO.IDENTITY);
        logger.info("Implementation class: "+service.getClass()); 			// changed by asesh
        logger.info("Implementation class: "+dao.getClass()); 	
		//CreditProcessingDAO service = new CreditProcessingDAOImpl();
		logger.info("File 1 : "+consumerVo.getCibilReportFileOne()+"File 2 : "+consumerVo.getCibilReportFileTwo()+"File 3 : "+consumerVo.getCibilReportFileThree());
		String checkDocsQuery="select count(*) from cr_uploaded_documents d inner join cr_cibil_dtl c on c.CIBIL_ID=d.TXN_ID and d.TXN_TYPE='CBL' and DEAL_ID='"+dealId+"'  where FILE_NAME IN ('"+consumerVo.getCibilReportFileOne()+"','"+consumerVo.getCibilReportFileTwo()+"','"+consumerVo.getCibilReportFileThree()+"')";
		String checkDocsCount=ConnectionDAO.singleReturn(checkDocsQuery);
		//add by saorabh
		String cibilRepotFlag = dao.getCibilRepotFlag();
		request.setAttribute("cibilRepotFlag", cibilRepotFlag);
		CibilVerificationVO dvo = new CibilVerificationVO();
		dvo.setDealID(dealId);
		 ArrayList gridList=dao.getCblViewCustomerGridList(dvo);
	     request.setAttribute("gridList",gridList);
	     ArrayList<Object> roleList = dao1.getRoleList(dealId);
			request.setAttribute("roleList", roleList);
	     //end by saorabh
		if(CommonFunction.checkNull(checkDocsCount).equalsIgnoreCase("0"))
		{
			int civilId = service.saveCibilData(consumerVo);
			boolean status=false;
			
			consumerVo.setCivilId(""+civilId);
			if(!CommonFunction.checkNull(consumerVo.getCibilReportFileOne()).equalsIgnoreCase(""))
			{
				status=UploadDocument.docUpload(request, consumerVo.getCibilReportFileOne(), dealId);
				if(status)
				{
					consumerVo.setFileName(request.getAttribute("fileName").toString());
					consumerVo.setDocPath(request.getAttribute("filePath").toString());	
					service.uploadCibilReportData(consumerVo);
					fileOne=1;
					
				}
				else
				{
					fileOne=3;
				}
				
				
			}
			if(!CommonFunction.checkNull(consumerVo.getCibilReportFileTwo()).equalsIgnoreCase(""))
			{
				status=UploadDocument.docUpload(request, consumerVo.getCibilReportFileTwo(), dealId);
				if(status)
				{
					consumerVo.setFileName(request.getAttribute("fileName").toString());
					consumerVo.setDocPath(request.getAttribute("filePath").toString());	
					service.uploadCibilReportData(consumerVo);	
					fileTwo=1;
					
				}
				else
				{
					fileTwo=3;
				}
				
			}			
			if(!CommonFunction.checkNull(consumerVo.getCibilReportFileThree()).equalsIgnoreCase(""))
			{
				status=UploadDocument.docUpload(request, consumerVo.getCibilReportFileThree(), dealId);
				if(status)
				{
					consumerVo.setFileName(request.getAttribute("fileName").toString());
					consumerVo.setDocPath(request.getAttribute("filePath").toString());	
					service.uploadCibilReportData(consumerVo);	
					fileThree=1;
					
				}
				else
				{
					fileThree=3;
				}
				
			}
			
			logger.info("for checking data............................................."+ civilId);
			if (civilId>0) {
				msg = "M";
				request.setAttribute("msg", msg);
			} else {
				msg = "N";
				dispData.add(consumerVo);
				request.setAttribute("dispData", dispData);
				request.setAttribute("msg", msg);
			}
			logger.info("civilId: " + civilId);
		}
		else
		{
			msg = "UD";
			dispData.add(consumerVo);
			request.setAttribute("dispData", dispData);
			request.setAttribute("msg", msg);
		}
		if(fileOne==3||fileTwo==3||fileThree==3)
		{
			ArrayList<ConsumerVo> detailList = service.getCibilData(consumerVo);
			request.setAttribute("list", detailList);
			ArrayList uploadedDocList = service.getUploadCibilData(dealId);
			if(uploadedDocList.size()>0){
				request.setAttribute("uploadedDocList", uploadedDocList);
			}
			return mapping.findForward("saved");
		}
		ArrayList uploadedDocList = service.getUploadCibilData(dealId);
		if(uploadedDocList.size()>0){
			request.setAttribute("uploadedDocList", uploadedDocList);
		}
		ArrayList<ConsumerVo> detailList = service.getCibilData(consumerVo);
		request.setAttribute("list", detailList);
		return mapping.findForward("saved");
	}

	public ActionForward DeleteDocDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger.info("In ConsumerDispatchAction DeleteDocDetails().... ");
		
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in DeleteDocDetails method of ConsumerDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		boolean flag=false;
		ConsumerVo consumerVo = new ConsumerVo();
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
		
		logger.info("strFlag .............. "+strFlag);
		if(!strFlag.equalsIgnoreCase(""))
		{
			if(strFlag.equalsIgnoreCase("sameUserSession"))
			{
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			}
			else if(strFlag.equalsIgnoreCase("BODCheck"))
			{
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}

		String dealId = "";

		if (session.getAttribute("dealId") != null) {

			dealId = session.getAttribute("dealId").toString();
		} else if (session.getAttribute("maxId") != null) {
			dealId = session.getAttribute("maxId").toString();
		}

		logger.info("In ConsumerDispatchAction DeleteDocDetails() id: "
				+ dealId);

		consumerVo.setDealId(dealId);

		String s = CommonFunction.checkNull(request.getParameter("list"));
		logger.info("In ConsumerDispatchAction DeleteDocDetails() id: " + s);
		int k = s.length();

		String[] s1 = new String[k];
		s1 = s.split("/");
		logger.info("In ConsumerDispatchAction: " + s1[0]);

		String msg = "";
		CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
            logger.info("Implementation class: "+service.getClass()); 	
		CorporateDAO dao1=(CorporateDAO)DaoImplInstanceFactory.getDaoImplInstance(CorporateDAO.IDENTITY);
		CibilVerificationDAO dao=(CibilVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(CibilVerificationDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass()); 	
		logger.info("Implementation class: "+dao.getClass()); // changed by asesh

		boolean result = service.deleteCibilEntry(s1,dealId);
		
		if (result) {
			msg = "S";
			request.setAttribute("msg", msg);
		} else {
			msg = "E";
			request.setAttribute("msg", msg);
		}
		logger.info("In ConsumerDispatchAction DeleteDocDetails() result"
				+ result);
		ArrayList uploadedDocList = service.getUploadCibilData(dealId);
		if(uploadedDocList.size()>0){
			request.setAttribute("uploadedDocList", uploadedDocList);
		}
		ArrayList<ConsumerVo> detailList = service.getCibilData(consumerVo);
		//add by saorabh
		String cibilRepotFlag = dao.getCibilRepotFlag();
		request.setAttribute("cibilRepotFlag", cibilRepotFlag);
		CibilVerificationVO dvo = new CibilVerificationVO();
		dvo.setDealID(dealId);
		 ArrayList gridList=dao.getCblViewCustomerGridList(dvo);
	     request.setAttribute("gridList",gridList);
	     ArrayList<Object> roleList = dao1.getRoleList(dealId);
			request.setAttribute("roleList", roleList);
	     // end by saorabh
		request.setAttribute("list", detailList);
		return mapping.findForward("deleted");
	}
	
	
	
	public ActionForward downloadCibilReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String downloadPath = "";
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here downloadCibilReport method of UnderwritingUploadProcessing  action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
		
		logger.info("strFlag .............. "+strFlag);
		if(!strFlag.equalsIgnoreCase(""))
		{
			if(strFlag.equalsIgnoreCase("sameUserSession"))
			{
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			}
			else if(strFlag.equalsIgnoreCase("BODCheck"))
			{
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}

		String dealId = (String) session.getAttribute("dealId");
		String fileName = request.getParameter("fileName");
		String cibilId = request.getParameter("cibilId");
		
//		String getCibilIdQuery="select CIBIL_ID from cr_cibil_dtl where deal_id="+dealId+" limit 1 ";
//		String getCibilId=ConnectionDAO.singleReturn(getCibilIdQuery);
		logger.info(" getCibilId: "+cibilId);
		//downloadPath = request.getParameter("docPath");
		CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+service.getClass()); 			// changed by asesh
		downloadPath = service.getFileInfoOFCibilReport(cibilId, fileName);
		logger.info("File Name downloadPath: "+downloadPath+" fileName: "+fileName);
		if (!CommonFunction.checkNull(downloadPath).equals("")) {
			downloadPath = downloadPath + "\\" + fileName;
			logger.info("Download Path of File is: " + downloadPath);
			String fileType = fileName.substring(fileName.lastIndexOf(".") + 1,
					fileName.length());
			logger.info("File Type: " + fileType);
			logger.info("File Name: " + fileName);
			

			if (fileType.trim().equalsIgnoreCase("xls")) {
				logger.info("File Type in xls: " + fileType);
				response.setContentType("application/vnd.ms-excel");
			} else if (fileType.trim().equalsIgnoreCase("xlsx")) {
				logger.info("File Type in xlsx: " + fileType);
				response.setContentType("application/vnd.ms-excel");
			} else if (fileType.trim().equalsIgnoreCase("pdf")) {
				logger.info("File Type in pdf: " + fileType);
				response.setContentType("application/pdf");
			}
			else if (fileType.trim().equalsIgnoreCase("doc")) {
				logger.info("File Type in doc: " + fileType);
				response.setContentType("application/msword");
			} else if (fileType.trim().equalsIgnoreCase("docx")) {
				logger.info("File Type in docx: " + fileType);
				response.setContentType("application/msword");
			} else if (fileType.trim().equalsIgnoreCase("jpg")) {
				logger.info("File Type in jpg: " + fileType);
				response.setContentType("image/jpeg");
			}
		    else if (fileType.trim().equalsIgnoreCase("jpeg")) {
			logger.info("File Type in jpeg: " + fileType);
			response.setContentType("image/jpeg");
		  }
			else if (fileType.trim().equalsIgnoreCase("zip")||fileType.trim().equalsIgnoreCase("rar")) {
				logger.info("File Type in zip/rar: " + fileType);
				response.setContentType("application/x-zip-compressed");
			} 
			
			else if (fileType.trim().equalsIgnoreCase("rtf")) {
				logger.info("File Type in rtf: " + fileType);
				response.setContentType("application/rtf");
			} 
			else if (fileType.trim().equalsIgnoreCase("csv")) {
				logger.info("File Type in csv: " + fileType);
				response.setContentType("text/comma-separated-values");
			} 
			else if (fileType.trim().equalsIgnoreCase("pptx")) {
				logger.info("File Type in pptx: " + fileType);
				response.setContentType("application/mspowerpoint");
			} 
			else if (fileType.trim().equalsIgnoreCase("ppt")) {
				logger.info("File Type in ppt: " + fileType);
				response.setContentType("application/mspowerpoint");
			} 
			
			else {
				logger.info("File Type in default: " + fileType);
				response.setContentType("application/octet-stream");
			}
			response.setHeader("Content-Disposition", "attachment;fileName="
					+ fileName);

			logger.info("File downloadPath: " + downloadPath);
			// executing download function
			UploadDocument.downloadFile(request, response, downloadPath);

		}
		return null;
	}
	

}
