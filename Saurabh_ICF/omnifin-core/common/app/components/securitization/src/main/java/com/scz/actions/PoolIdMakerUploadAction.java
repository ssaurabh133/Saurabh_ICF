package com.scz.actions;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.DynaValidatorForm;

import com.scz.dao.PoolIDDAO;
import com.scz.daoImplMYSQL.PoolIDDAOImpl;
import com.scz.vo.PoolIdMakerVO;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.scz.DateFormator;

public class PoolIdMakerUploadAction extends DispatchAction{
private static final Logger logger = Logger.getLogger(PoolIdMakerUploadAction.class.getName());	

public ActionForward getPoolId(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)throws Exception {
	HttpSession session=request.getSession();
	
	String functionId=(String)session.getAttribute("functionId");
	if(CommonFunction.checkNull(functionId).trim().equalsIgnoreCase(""))
		functionId="0";
	int id=Integer.parseInt(functionId);
	logger.info("functionId===========id"+id);
	String type=request.getParameter("type");
	//String type="poolUpload";
	/*if(functionId.equalsIgnoreCase("12001149"))
	{type="repamentSchdUpload";}
	// 12001151
	if(functionId.equalsIgnoreCase("12001151"))
	{type="bankUpload";}*/
	// PoolIDDAO dao=(PoolIDDAO)DaoImplInstanceFactory.getDaoImplInstance(PoolIDDAO.IDENTITY);
	PoolIDDAO dao= new PoolIDDAOImpl();
	// logger.info("Implementation class: "+dao.getClass());
	String temp = String.valueOf(session.getAttribute("temp"));
	int poolNo=0;
	if(temp.equals("null")){
		poolNo = Integer.parseInt(dao.getPoolNo());
	}else{
		poolNo=Integer.parseInt(temp.trim());
	}
	request.setAttribute("poolNo", poolNo);
	
	request.setAttribute("type", type);
	
	return mapping.findForward("openNewWindow");
}

public ActionForward poolUploadSave(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)throws Exception {
	logger.info("Inside Processing Action of submitPoolIdUpload");
	   
	HttpSession session = request.getSession();
	UserObject userobj=(UserObject)session.getAttribute("userobject");
	
	boolean status=false;			
	boolean uploadStatus=false;	
	String makerID =null;
	String businessDate =null;
	String strFlag=null;
	int poolID = 0;
	String flag=null;
	String filePathWithName=null;
	int currentPageLink = 0;
	String procStatus = "";
	
	if(userobj!=null){
		makerID = userobj.getUserId();
		businessDate=userobj.getBusinessdate();
	}else{
		logger.info(" in submitPoolIdUpload method of PoolIdMakerProcessAction action the session is out----------------");
		return mapping.findForward("sessionOut");
	}
	Object sessionId = session.getAttribute("sessionID");
    ServletContext context = getServlet().getServletContext();
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

	logger.info("current page link .......... "+request.getParameter("d-4008017-p"));
	
	if(request.getParameter("d-4008017-p")==null || request.getParameter("d-4008017-p").equalsIgnoreCase("0"))
	{
		currentPageLink=1;
	}
	else
	{
		currentPageLink =Integer.parseInt(request.getParameter("d-4008017-p"));
	}
	logger.info("current page link ................ "+request.getParameter("d-4008017-p"));
	 
   PoolIdMakerVO poolIdMakerVO = new PoolIdMakerVO();
   DynaValidatorForm PoolIDMakerDynaValidatorForm=(DynaValidatorForm)form;	
   org.apache.commons.beanutils.BeanUtils.copyProperties(poolIdMakerVO, PoolIDMakerDynaValidatorForm);

    // PoolIDDAO dao=(PoolIDDAO)DaoImplInstanceFactory.getDaoImplInstance(PoolIDDAO.IDENTITY);
	PoolIDDAO dao= new PoolIDDAOImpl();
   
	    logger.info("Inside Processing Action of submitPoolIdUpload");
	 	poolIdMakerVO.setMakerID(makerID);
	poolIdMakerVO.setMakerDate1(DateFormator.dmyToSQL(businessDate));
	poolIdMakerVO.setMakerDate(businessDate);
	poolIdMakerVO.setCurrentPageLink(currentPageLink);
	logger.info("Implementation class: "+dao.getClass());
   
	poolID = poolIdMakerVO.getPoolID();
	logger.info("poolID in action:-"+poolID);
	
	if(!CommonFunction.checkNull(poolIdMakerVO.getDocFile()).equalsIgnoreCase(""))
	{	    	
		// Getting the file path....
		uploadStatus=dao.docUploadForExcel(request,(FormFile)poolIdMakerVO.getDocFile());
		
		filePathWithName=request.getAttribute("filePathWithName").toString();
		poolIdMakerVO.setFileName(request.getAttribute("fileName").toString());
		poolIdMakerVO.setDocPath(request.getAttribute("filePath").toString());
		poolIdMakerVO.setFilePathWithName(filePathWithName);
		
		flag=(String)request.getAttribute("message");
		logger.info("flag .. "+flag);
		
		if(CommonFunction.checkNull(flag).equalsIgnoreCase("O"))
		    request.setAttribute("sms","");
		if(CommonFunction.checkNull(flag).equalsIgnoreCase("E"))
			request.setAttribute("smsno","");

		logger.info("uploadStatus .. "+uploadStatus);
		// in this method we have to process with KTR file Rudra
			if(uploadStatus){	
				status=dao.uploadCsv_Securitization(request,response,poolIdMakerVO.getDocFile().getFileName(),poolIdMakerVO);	
				logger.info("status-----------------------"+status);
			}
			// Pool Upload
			if(status)
			{
			 procStatus=dao.saveSecuritization(request,poolIdMakerVO);
			logger.info("procStatus-----------------------"+procStatus);
	}
			else {
				request.setAttribute("fieldUpdate", "Some problem in sheet format..");
			}

	}
	request.setAttribute("poolId", String.valueOf(poolID));
	if(!procStatus.equalsIgnoreCase(""))
	{
		request.setAttribute("procStatusMSG",procStatus);
		return mapping.findForward("success");
	}
	
	if(status && uploadStatus)
		request.setAttribute("msgFlag", "S");
		else
			request.setAttribute("msgFlag","E");
	return mapping.findForward("success");
}
public ActionForward bankUploadSave(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)throws Exception {
	logger.info("Inside Processing Action of submitPoolIdUpload");
	   
	HttpSession session = request.getSession();
	UserObject userobj=(UserObject)session.getAttribute("userobject");
	
	boolean status=false;			
	boolean uploadStatus=false;	
	String makerID =null;
	String businessDate =null;
	String strFlag=null;
	int poolID = 0;
	String flag=null;
	String filePathWithName=null;
	int currentPageLink = 0;
	String procStatus="";
	
	if(userobj!=null){
		makerID = userobj.getUserId();
		businessDate=userobj.getBusinessdate();
	}else{
		logger.info(" in submitPoolIdUpload method of PoolIdMakerProcessAction action the session is out----------------");
		return mapping.findForward("sessionOut");
	}
	Object sessionId = session.getAttribute("sessionID");
    ServletContext context = getServlet().getServletContext();
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

	logger.info("current page link .......... "+request.getParameter("d-4008017-p"));
	
	if(request.getParameter("d-4008017-p")==null || request.getParameter("d-4008017-p").equalsIgnoreCase("0"))
	{
		currentPageLink=1;
	}
	else
	{
		currentPageLink =Integer.parseInt(request.getParameter("d-4008017-p"));
	}
	logger.info("current page link ................ "+request.getParameter("d-4008017-p"));
	 
   PoolIdMakerVO poolIdMakerVO = new PoolIdMakerVO();
   DynaValidatorForm PoolIDMakerDynaValidatorForm=(DynaValidatorForm)form;	
   org.apache.commons.beanutils.BeanUtils.copyProperties(poolIdMakerVO, PoolIDMakerDynaValidatorForm);

    // PoolIDDAO dao=(PoolIDDAO)DaoImplInstanceFactory.getDaoImplInstance(PoolIDDAO.IDENTITY);
	PoolIDDAO dao= new PoolIDDAOImpl();
   
	    logger.info("Inside Processing Action of submitPoolIdUpload");
	 	poolIdMakerVO.setMakerID(makerID);
	 	poolIdMakerVO.setMakerDate1(DateFormator.dmyToSQL(businessDate));
	 	poolIdMakerVO.setCurrentPageLink(currentPageLink);
	 	logger.info("Implementation class: "+dao.getClass());
   
	poolID = poolIdMakerVO.getPoolID();
	logger.info("poolID in action:-"+poolID);
	
	if(!CommonFunction.checkNull(poolIdMakerVO.getDocFile()).equalsIgnoreCase(""))
	{	    	
		// Getting the file path....
		uploadStatus=dao.docUploadForExcel(request,(FormFile)poolIdMakerVO.getDocFile());
		
		filePathWithName=request.getAttribute("filePathWithName").toString();
		poolIdMakerVO.setFileName(request.getAttribute("fileName").toString());
		poolIdMakerVO.setDocPath(request.getAttribute("filePath").toString());
		poolIdMakerVO.setFilePathWithName(filePathWithName);
		
		flag=(String)request.getAttribute("message");
		logger.info("flag .. "+flag);
		
		if(CommonFunction.checkNull(flag).equalsIgnoreCase("O"))
		    request.setAttribute("sms","");
		if(CommonFunction.checkNull(flag).equalsIgnoreCase("E"))
			request.setAttribute("smsno","");

		logger.info("uploadStatus .. "+uploadStatus);

			if(uploadStatus){	
				// in this method we have to process with KTR file Rudra
				status=dao.uploadCsv_BankPool(request,response,poolIdMakerVO.getDocFile().getFileName(),poolIdMakerVO);	
				logger.info("status-----------------------"+status);
			}

			logger.info("procStatus-----------------------"+procStatus);
			if(status){
				procStatus = dao.saveSecuritizationBankUpload(request,poolIdMakerVO);
			}
			else {
				request.setAttribute("fieldUpdate", "Some problem in sheet format..");
			}
			//logger.info("procStatus-----------------------"+procStatus);
	}
	request.setAttribute("poolId", String.valueOf(poolID));
	
	
	
	if(!procStatus.equalsIgnoreCase(""))
	{
		logger.info("procStatus "+procStatus);
		request.setAttribute("procStatus", "bank");
		request.setAttribute("procStatusMSG", procStatus);
		return mapping.findForward("success");
	}

	if(status && uploadStatus)
	request.setAttribute("msgFlag", "S");
	else
		request.setAttribute("msgFlag","E");
	return mapping.findForward("success");
}

public ActionForward repaymentScheduleUploadSave(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)throws Exception {
			
	logger.info("Inside Re-Payment Schedule Upload Data---------------------");
	  
	HttpSession session = request.getSession();
	UserObject userobj=(UserObject)session.getAttribute("userobject");
	
	boolean status=false;			
	boolean uploadStatus=false;	
	String makerID =null;
	String businessDate =null;
	String strFlag=null;
	int poolID = 0;
	String flag=null;
	String filePathWithName=null;
	int currentPageLink = 0;
	
	if(userobj!=null){
		makerID = userobj.getUserId();
		businessDate=userobj.getBusinessdate();
	}else{
		logger.info(" in repaymentScheduleUploadSave method of PoolIdMakerUploadAction action the session is out----------------");
		return mapping.findForward("sessionOut");
	}
	Object sessionId = session.getAttribute("sessionID");
    ServletContext context = getServlet().getServletContext();
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

	logger.info("current page link .......... "+request.getParameter("d-4008017-p"));
	
	if(request.getParameter("d-4008017-p")==null || request.getParameter("d-4008017-p").equalsIgnoreCase("0"))
	{
		currentPageLink=1;
	}
	else
	{
		currentPageLink =Integer.parseInt(request.getParameter("d-4008017-p"));
	}
	logger.info("current page link ................ "+request.getParameter("d-4008017-p"));
	 
   PoolIdMakerVO poolIdMakerVO = new PoolIdMakerVO();
   DynaValidatorForm PoolIDMakerUploadDynaValidatorForm=(DynaValidatorForm)form;	
   org.apache.commons.beanutils.BeanUtils.copyProperties(poolIdMakerVO, PoolIDMakerUploadDynaValidatorForm);

    // PoolIDDAO dao=(PoolIDDAO)DaoImplInstanceFactory.getDaoImplInstance(PoolIDDAO.IDENTITY);
	PoolIDDAO dao= new PoolIDDAOImpl();
   
	    logger.info("Inside Re-Payment Schedule Upload Data------------");
	 	poolIdMakerVO.setMakerID(makerID);
	poolIdMakerVO.setMakerDate1(DateFormator.dmyToSQL(businessDate));
	poolIdMakerVO.setCurrentPageLink(currentPageLink);
	logger.info("Implementation class: "+dao.getClass());
   
	poolID = poolIdMakerVO.getPoolID();
	logger.info("poolID in action:-"+poolID);
	String procStatus="";
	
	if(!CommonFunction.checkNull(poolIdMakerVO.getDocFile()).equalsIgnoreCase(""))
	{	    	
		// Getting the file path....
		uploadStatus=dao.docUploadForExcel(request,(FormFile)poolIdMakerVO.getDocFile());
		
		filePathWithName=request.getAttribute("filePathWithName").toString();
		poolIdMakerVO.setFileName(request.getAttribute("fileName").toString());
		poolIdMakerVO.setDocPath(request.getAttribute("filePath").toString());
		poolIdMakerVO.setFilePathWithName(filePathWithName);
		
		flag=(String)request.getAttribute("message");
		logger.info("flag .. "+flag);
		
		if(CommonFunction.checkNull(flag).equalsIgnoreCase("O"))
		    request.setAttribute("sms","");
		if(CommonFunction.checkNull(flag).equalsIgnoreCase("E"))
			request.setAttribute("smsno","");

		logger.info("uploadStatus .. "+uploadStatus);

			if(uploadStatus){	
				status=dao.uploadCsv_Repayment(request,response,poolIdMakerVO.getDocFile().getFileName(),poolIdMakerVO);	
				logger.info("status-----------------------"+status);
			}
			if(status)
			{
			 procStatus=dao.saveSecuritizationRepayment(request,poolIdMakerVO);
			logger.info("procStatus-----------------------"+procStatus);
			}
			else {
				logger.info("Inside error-----------------------"+status);
				
				request.setAttribute("fieldUpdate", "Some problem in sheet format..");
			}
	}
	if(!procStatus.equalsIgnoreCase(""))
	{
		logger.info("procStatus "+procStatus);
		request.setAttribute("procStatus", "bank");
		request.setAttribute("procStatusMSG", procStatus);
		return mapping.findForward("success");
	}
	request.setAttribute("poolId", String.valueOf(poolID));
	if(status && uploadStatus)
		request.setAttribute("msgFlag", "S");
	else
		request.setAttribute("msgFlag","E");
	return mapping.findForward("success");
}
	
}
