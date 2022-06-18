/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.cm.actions;

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
import com.cm.dao.ManualAdviceDAO;
import com.cm.vo.ManualAdviceCreationVo;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.lockRecord.action.LockRecordCheck;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class ManualAdviceSearchProcessAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(ManualAdviceSearchProcessAction.class.getName());	
	public ActionForward searchDetail(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws Exception {
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String branchId="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}else{
			logger.info(" in searchDetail method of  ManualAdviceSearchProcessAction action the session is out----------------");
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
		logger.info("current page link .......... "+request.getParameter("d-49520-p"));
		
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
		ManualAdviceCreationVo vo = new ManualAdviceCreationVo();
		
		vo.setCurrentPageLink(currentPageLink);
		logger.info("in ManualAdviceSearchProcessAction...............................");
		
		ManualAdviceDAO dao=(ManualAdviceDAO)DaoImplInstanceFactory.getDaoImplInstance(ManualAdviceDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass()); 

		String author="";
		DynaValidatorForm manualAdviceCreationDynaValidatorForm = (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, manualAdviceCreationDynaValidatorForm);
		if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
		{ 
			vo.setReportingToUserId(userId);
		   //logger.info("When user id is not selected by the user:::::"+userId);
		}
		logger.info("user Id:::::"+vo.getReportingToUserId());
		vo.setBranchId(branchId);
		vo.setUserId(userId);
		if(session.getAttribute("manualApprove")!=null)
		{
			author=session.getAttribute("manualApprove").toString();
			logger.info("manualApprove .................................."+session.getAttribute("manualApprove").toString());
		}
		String type = CommonFunction.checkNull(session.getAttribute("manualApprove"));
		vo.setStage(type);
		ArrayList<ManualAdviceCreationVo> detailListGrid = dao.ManualAdviceMakerSearchDetail(vo,author);
		request.setAttribute("true","true");
		request.setAttribute("list", detailListGrid);
		
		logger.info("detailListGrid    Size:---"+detailListGrid.size());
		
		if(session.getAttribute("pParentGroup")!=null)
		{
			session.removeAttribute("pParentGroup");
		}
		if(session.getAttribute("strParentOption")!=null)
		{
			session.removeAttribute("strParentOption");
		}
		if(detailListGrid.size()==0)
		{
			request.setAttribute("msg", "N");
		//	return mapping.getInputForward();
		}

		return mapping.findForward("success");	
	
	}


public ActionForward EditManualAdviceMaker(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
throws Exception {
	
	HttpSession session = request.getSession();
	boolean flag=false;
	UserObject userobj=(UserObject)session.getAttribute("userobject");
	String userId ="";
	if(userobj!=null)
	{
		userId=userobj.getUserId();
	}else{
		logger.info(" in EditManualAdviceMaker methods of ManualAdviceSearchProcessActionaction the session is out----------------");
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
	session.removeAttribute("pParentGroup");
	//session.removeAttribute("loanID");
	session.removeAttribute("manaulId");

	logger.info("In EditManualAdviceMaker---status---- by getpara-"+request.getParameter("manualId"));  
	
	ManualAdviceDAO dao=(ManualAdviceDAO)DaoImplInstanceFactory.getDaoImplInstance(ManualAdviceDAO.IDENTITY);
	logger.info("Implementation class: "+dao.getClass()); 
	String manualId=request.getParameter("manualId");
	
	logger.info("function id is ........................................"+session.getAttribute("functionId").toString());
	String functionId="";

	
	if(session.getAttribute("functionId")!=null)
	{
		functionId=session.getAttribute("functionId").toString();
	}
	
	
	//ServletContext context=getServlet().getServletContext();
	if(context!=null)
	{
	flag = LockRecordCheck.lockCheck(userId,functionId,manualId,context);
	logger.info("Flag ........................................ "+flag);
	if(!flag)
	{
		logger.info("Record is Locked");			
		request.setAttribute("msg", "Locked");
		request.setAttribute("recordId", manualId);
		//request.setAttribute("userId", userId);
		return mapping.findForward("success");
	}
	}
	ArrayList<ManualAdviceCreationVo> manualAdviceList= dao.getManualAdviceCreationMakerDetail(request.getParameter("manualId"));
	logger.info("payableList Size:---"+manualAdviceList.size());
	//request.setAttribute("canForward", "canForward");
	session.setAttribute("adviceUpdate", "adviceUpdate");
	session.setAttribute("manualAdviceList",manualAdviceList);
	if(session.getAttribute("manualApprove")!=null)
	{
		session.setAttribute("manualId", request.getParameter("manualId"));
		return mapping.findForward("approveManual");
	}
	else
	{
		return mapping.findForward("editManual");
	}
	
}


public ActionForward manualAdviceAuthor(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
throws Exception {
	
		logger.info("in manualAdviceAuthor..........");
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String AuthorId ="";
		String businessDate ="";
		int compid =0;
		if(userobj!=null){
			AuthorId = userobj.getUserId();
				businessDate=userobj.getBusinessdate();
				compid=userobj.getCompanyId();
		}else{
			logger.info(" in manualAdviceAuthor method of ManualAdviceSearchProcessAction action the session is out----------------");
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
		
		DynaValidatorForm PaymentCMDynaValidatorForm = (DynaValidatorForm)form;
		ManualAdviceCreationVo manualAdviceCreationVo=new ManualAdviceCreationVo();	
	
		String manualId="";
		if(session.getAttribute("manualId")!=null)
		{
			manualId=session.getAttribute("manualId").toString();
			manualAdviceCreationVo.setManaulId(manualId);
		}
		
		org.apache.commons.beanutils.BeanUtils.copyProperties(manualAdviceCreationVo, PaymentCMDynaValidatorForm);

		
		manualAdviceCreationVo.setAutherDate(businessDate);
		
		manualAdviceCreationVo.setAutherId(AuthorId);
		manualAdviceCreationVo.setCompanyId(compid);
		String decision="";
		String comments="";
		decision=request.getParameter("decision").trim();
		request.setAttribute("deci", request.getParameter("decision").trim());
		logger.info("decision.......... "+request.getParameter("decision"));
		comments=request.getParameter("comments").trim();
		manualAdviceCreationVo.setDecision(decision);
		manualAdviceCreationVo.setRemarks(comments);
		logger.info("remakrs .................................. "+comments);
		ManualAdviceDAO dao=(ManualAdviceDAO)DaoImplInstanceFactory.getDaoImplInstance(ManualAdviceDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass()); 
		 String update="";
		update = dao.manualAdviceAuthorDecision(manualAdviceCreationVo);
				if(update.equalsIgnoreCase("S"))
				{
					logger.info("true.....................");
					request.setAttribute("sms", "A");
				}
				else
				{
					logger.info("update....................."+update);
					request.setAttribute("sms", "E");
					request.setAttribute("errorMsg", update.toString());
				}
			return mapping.findForward("authorFlag");
	} 


}