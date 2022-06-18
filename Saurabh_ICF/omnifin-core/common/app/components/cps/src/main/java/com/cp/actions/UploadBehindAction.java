/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.cp.actions;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.logging.Logger;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.DynaValidatorForm;

import com.business.ejbClient.CreditProcessingMasterBussinessSessionBeanRemote;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.connect.LookUpInstanceFactory;
import com.cp.actions.UploadBehindAction;
import com.cp.dao.FileUplaodDao;

import com.cp.dao.OcrDAO;
/*import com.cp.process.BankDetailsReadFileProcess;
import com.cp.process.FileUploadProcessTemplete;*/
import com.cp.vo.CommonDealVo;
import com.cp.vo.FinancialAnalysisVo;
import com.cp.vo.UnderwritingDocUploadVo;
import com.cp.vo.UploadTypeVo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

/** 
 * MyEclipse Struts
 * Creation date: 10-15-2011
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class UploadBehindAction extends Action {
	private static final Logger logger = Logger.getLogger(UploadBehindAction.class.getName());
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ServletContext context = getServlet().getServletContext();
		logger.info("In UploadBehindAction.........");
		HttpSession session = request.getSession();
		boolean flag=false;
		String userId="";
		String branchId="";
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null)
		{
			logger.info("here in searchCustomer method of UploadBehindAction  action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		else
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
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
		UnderwritingDocUploadVo vo = new UnderwritingDocUploadVo();
	//    DynaValidatorForm UploadForm= (DynaValidatorForm)form;
	  
	    
	   // org.apache.commons.beanutils.BeanUtils.copyProperties(vo, UploadForm);
	 		
			vo.setMakerId(userId);
			vo.setBranchId(branchId);
			int currentPageLink = 0;
			if(request.getParameter("d-49520-p")==null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
			{
				currentPageLink=1;
			}
			else
			{
				currentPageLink =Integer.parseInt(request.getParameter("d-49520-p"));
			}
			
			logger.info("current page link ................ "+request.getParameter("d-49520-p"));
			vo.setCurrentPageLink(currentPageLink);
			session.setAttribute("rejectUserIdLOv",userId);
			OcrDAO service=(OcrDAO)DaoImplInstanceFactory.getDaoImplInstance(OcrDAO.IDENTITY);
			ArrayList dealHeader=new ArrayList();
			ArrayList  uploadedDocList=new ArrayList();
		
			
			String dealId = "";
			
		 
		   
		     uploadedDocList = service.makerSearch(vo);
		     logger.info("uploadedDocList===="+uploadedDocList.size());
		     if(uploadedDocList.size() > 0)
		     {
		    	 logger.info("uploadedDocList.size()>0.........................");
		    	 request.setAttribute("uploadedDocList", uploadedDocList);
		    	 CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		    	 ArrayList list1 = cpm.getEntity("DUM");
		    	 request.setAttribute("docEntity", list1);
		    	 ArrayList<UploadTypeVo> doctype=  service.getDocType();
		    	 request.setAttribute("docType", doctype);
		    	 return mapping.findForward("success");
		     }

		     else
		     {
		    	 logger.info("!uploadedDocList.size()>0.........................");
		    	 CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		    	 ArrayList list1 = cpm.getEntity("DUM");
		    	 request.setAttribute("docEntity", list1);
		    	 ArrayList<UploadTypeVo> doctype=  service.getDocType();
		    	 request.setAttribute("docType", doctype);
		    	 return mapping.findForward("success");
		     }
				 			 			   
			
			


}

}