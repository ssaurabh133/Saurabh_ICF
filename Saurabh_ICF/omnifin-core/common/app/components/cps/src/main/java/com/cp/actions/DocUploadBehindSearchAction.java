/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.cp.actions;

import java.util.ArrayList;

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
import com.cp.vo.CommonDealVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

/** 
 * MyEclipse Struts
 * Creation date: 01-04-2012
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class DocUploadBehindSearchAction extends Action {
	/*
	 * Generated Methods
	 */
	private static final Logger logger = Logger.getLogger(DocUploadBehindSearchAction.class.getName());
	/** 
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {


		logger.info("In CommondealAction(searchDealCapturing)");
		HttpSession session = request.getSession();
		String userId=null;
		String branch=null;
		String callFrom=null;
		UserObject userobj=(UserObject)session.getAttribute("userobject");	
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branch=userobj.getBranchId();
		}else{
			logger.info("here in execute method of DocUploadBehindSearchAction action the session is out----------------");
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
		String flag=null;
		if(session.getAttribute("cmdocupload")!=null)
		{
		  flag=session.getAttribute("cmdocupload").toString();
		}
		//sachin
		//Yogesh start
		String functionId=(String)session.getAttribute("functionId");
		if(CommonFunction.checkNull(functionId).trim().equalsIgnoreCase(""))
			functionId="0";
		int id=Integer.parseInt(functionId);
		logger.info("In  DocUploadBehindSearchAction: "+id); 
		if(id==9000160)
		{
			callFrom="Legal";
		}
		//Yogesh end
		
		DynaValidatorForm CommonDealDynaValidatorForm = (DynaValidatorForm) form;
		CommonDealVo vo = new CommonDealVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,CommonDealDynaValidatorForm);
		vo.setBranchId(branch);
		if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
		{ 
			vo.setReportingToUserId(userId);
		   //logger.info("When user id is not selected by the user:::::"+userId);
		}
	
		CreditProcessingDAO creditDAO=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
       		// changed by asesh
		//CreditProcessingDAO creditDAO = new CreditProcessingDAOImpl();
		ArrayList docType=creditDAO.getDocTypeList();
	    session.setAttribute("docType", docType);
		
		ArrayList dealdetails=new ArrayList();
		
		
		
		int currentPageLink = 0;
		if(request.getParameter("d-49520-p")==null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
		{
			currentPageLink=1;
		}
		else
		{
			currentPageLink =Integer.parseInt(request.getParameter("d-49520-p"));
		}
		
	
	
	//sachin	
		if(CommonFunction.checkNull(flag).equalsIgnoreCase("")){
		
		vo.setCurrentPageLink(currentPageLink);
           if("Legal".equalsIgnoreCase(callFrom)){
			dealdetails= creditDAO.docUploadedDetailInSearchForLegal(vo, request);
		    }
          else{
		   dealdetails= creditDAO.docUploadedDetailInSearch(vo, request);
		}
	}
	else{
		vo.setCurrentPageLink(currentPageLink);
	//Yogesh Start	
		if("Legal".equalsIgnoreCase(callFrom)){
			
			dealdetails= creditDAO.docUploadedDetailInSearchForLegal(vo, request);
		}else{
		dealdetails= creditDAO.docUploadedDetailInSearchForCm(vo, request);
		}
		//Yogesh end
	}
		//sachin
	    request.setAttribute("list", dealdetails);
		
	    creditDAO=null;
	    vo=null;
	    functionId=null;
	    flag=null;
	    callFrom=null;
	    userId=null;
	    branch=null;
	    strFlag=null;
		form.reset(mapping, request);
		return mapping.findForward("success");
		
	}
}