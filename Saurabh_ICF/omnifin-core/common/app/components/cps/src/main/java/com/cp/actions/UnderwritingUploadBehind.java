package com.cp.actions;

import java.util.ArrayList;
import org.apache.commons.lang.StringUtils;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.CreditProcessingDAO;
import com.cp.vo.PartnerCaptureVO;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

/**
 * MyEclipse Struts Creation date: 05-05-2011
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class UnderwritingUploadBehind extends Action {
	private static final Logger logger = Logger.getLogger(UnderwritingUploadBehind.class.getName());
	/*
	 * Generated Methods
	 */

	/**
	 * Method execute
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		 logger.info("section1 value--------------------------------------===");

	     logger.info("Inside UnderwritingUploadBehind(execute)");
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in execute method of UnderwritingUploadBehind action the session is out----------------");
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
		if(!"".equalsIgnoreCase(strFlag))
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
		//sachin
		String flag1=null;
		if(session.getAttribute("cmdocupload")!=null)
		{
		  flag1=session.getAttribute("cmdocupload").toString();
		}
		if(request.getParameter("cmdocupload")!=null)
		{
		  flag1=request.getParameter("cmdocupload").toString();
		  session.setAttribute("cmdocupload", flag1);
		}
		
		//pooja code starts 
		String loanDisbursalId=null;
		if(request.getParameter("loanDisbursalId")!=null)
		{
			loanDisbursalId=request.getParameter("loanDisbursalId").toString();
		  session.setAttribute("loanDisbursalId", loanDisbursalId);
		}
		//pooja code end 
		
		
		
		//sachin
		String dealId = null;
		String loan_id="";
		//sachin
		String functionId = (String)session.getAttribute("functionId");
		
		//added By Rahul Papneja|14112017|PaymentMaker DRF reciept Upload
		
		String instrumentId=null;
		if(session.getAttribute("instrumentId")!=null)
		{
			instrumentId=session.getAttribute("instrumentId").toString();
		}
		if(request.getParameter("instrumentId")!=null)
		{
			instrumentId=request.getParameter("instrumentId").toString();
		  session.setAttribute("instrumentId", instrumentId);
		}
		
		
		
		
		// End
		if(!(CommonFunction.checkNull(flag1).equalsIgnoreCase(""))){
		if (session.getAttribute("loanId") != null) {

			dealId = session.getAttribute("loanId").toString();
		} else if (session.getAttribute("maxId") != null) {
			dealId = session.getAttribute("maxId").toString();
		}
		if(request.getParameter("loanId")!=null)
		{
			dealId=request.getParameter("loanId");
		//	logger.info("dealID: "+dealId);
			session.setAttribute("dealId", dealId);
		}
		}
		
		//end by sachin
		else{
		if (session.getAttribute("dealId") != null) {

			dealId = session.getAttribute("dealId").toString();
		} else if (session.getAttribute("maxId") != null) {
			dealId = session.getAttribute("maxId").toString();
		}
		if(request.getParameter("dealId")!=null)
		{
			dealId=request.getParameter("dealId");
			//logger.info("dealID: "+dealId);
			session.setAttribute("dealId", dealId);
		}
		}
		session.setAttribute("loan_id", dealId);
		logger.info("Inside UnderwritingUploadBehind(execute) dealId "
				+ dealId);
		CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+service.getClass()); 	//changed by asesh
		//CreditProcessingDAO service = new CreditProcessingDAOImpl();
		//sachin
		ArrayList dealHeader=new ArrayList();
		ArrayList uploadedDocList=new ArrayList();
		ArrayList uploadDocListForCp = new ArrayList();
		if(CommonFunction.checkNull(flag1).equalsIgnoreCase("")){
	     dealHeader = service.getDealHeader(dealId);
	     uploadedDocList = service.getUploadUnderwritingData(dealId);
		}
		else{
			if(CommonFunction.checkNull(flag1).equalsIgnoreCase("paymentMaker")){
				dealHeader = service.getDealHeaderForCm(dealId);
				logger.info("Inside UnderwritingUploadBehind(execute) dealId test ");
			//	uploadedDocList = service.getUploadUnderwritingDataForCmPaymentMaker(dealId,instrumentId);
				session.setAttribute("paymentMaker", "paymentMaker"); // Test PaymentMaker
				} 
			else
			{
		
			dealHeader = service.getDealHeaderForCm(dealId);
			uploadedDocList = service.getUploadUnderwritingDataForCm(dealId);
			
			if(!CommonFunction.checkNull(flag1).equalsIgnoreCase("Disbursal")){
			uploadDocListForCp = service.getUploadUnderwritingDataForCmCp(dealId);
			}
		
			}
		}
		session.setAttribute("dealHeader", dealHeader);
	   // uploadedDocList = service.getUploadUnderwritingData(dealId);
		request.setAttribute("uploadedDocList", uploadedDocList);
		logger.info("Size : "+uploadDocListForCp.size() );
		if(uploadDocListForCp.size() > 0)
		{
			request.setAttribute("uploadDocListForCp", uploadDocListForCp);
		}
		////end by sachin	
		service=null;
		flag1=null;
		strFlag=null;
		String flag = "";
	    if (StringUtils.equals("4000116", functionId))
	    {
	      flag = "InternalSuccess";
	      logger.info("flag value match--------------------------------------===" + functionId);
	    }
	    else
	    {
	      flag = "success";
	      logger.info("flag value not match--------------------------------------===" + functionId);
	    }

	    return mapping.findForward(flag);
	}
	
}
