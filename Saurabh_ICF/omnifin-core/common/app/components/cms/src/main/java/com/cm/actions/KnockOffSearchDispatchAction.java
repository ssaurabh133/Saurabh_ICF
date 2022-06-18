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
import com.cm.dao.KnockOffDAO;
import com.cm.vo.KnockOffMakerVO;
import com.cm.vo.KnockOffSearchVO;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.lockRecord.action.LockRecordCheck;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

/** 
 * MyEclipse Struts
 * Creation date: 07-13-2011
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class KnockOffSearchDispatchAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(KnockOffSearchDispatchAction.class.getName());
	/*
	 * Generated Methods
	 */

	/** 
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward openNew(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		logger.info("Inside KnockOffSearchDispatchAction,........openNew");
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in openNew mnethod of  KnockOffSearchDispatchAction action the session is out----------------");
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
		session.removeAttribute("knockOffAuthorValues");
		session.removeAttribute("loanDataListR");
		session.removeAttribute("loanDataListP");
		session.removeAttribute("knockOffSearchList");
		session.removeAttribute("loanId");
		session.removeAttribute("knockOffID");
		session.removeAttribute("totalR");
	    session.removeAttribute("totalP");
	    session.removeAttribute("showdetails");
	    
	    //ArrayList<Object> showdetails = service.getBusinessPartnerTypeList();
	    //request.setAttribute("showdetails", showdetails);
	    
		request.setAttribute("knockOffNew", "knockOffNew");
		
		return mapping.findForward("openNew");
	}
	
	public ActionForward searchKnockOffData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("Inside KnockOffSearchDispatchAction,........searchKnockOffData");
		
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
			logger.info("in searchKnockOffData method of  KnockOffSearchDispatchAction action the session is out----------------");
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
		KnockOffSearchVO vo= new KnockOffSearchVO();
		
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
		
		String type=CommonFunction.checkNull(request.getParameter("type"));
		DynaValidatorForm KnockOffSearchDynaValidatorForm = (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,KnockOffSearchDynaValidatorForm);
		if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
		{ 
			vo.setReportingToUserId(userId);
		   //logger.info("When user id is not selected by the user:::::"+userId);
		}
		logger.info("user Id:::::"+vo.getReportingToUserId());
		vo.setStage(type);
		vo.setBranchId(branchId);
		vo.setUserId(userId);
		KnockOffDAO service=(KnockOffDAO)DaoImplInstanceFactory.getDaoImplInstance(KnockOffDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass()); 
	    ArrayList<KnockOffSearchVO> knockOffSearchList = service.searchKnockOffData(vo,type);
	    
	    if(knockOffSearchList.size()==0)
		{
			request.setAttribute("message","N");
			if(type.equalsIgnoreCase("P"))
			{
				request.setAttribute("knockOffMakerSearch","knockOffMakerSearch");
				request.setAttribute("knockOffSearchList","knockOffSearchList");
			}
			
			else if(type.equalsIgnoreCase("F"))
			{
				request.setAttribute("knockOffAuthorSearch","knockOffAuthorSearch");
				request.setAttribute("knockOffSearchList", "knockOffSearchList");
			}
		}
		else
		{
			if(type.equalsIgnoreCase("P"))
			{
				request.setAttribute("knockOffMakerSearch","knockOffMakerSearch");
				request.setAttribute("knockOffSearchList","knockOffSearchList");
				request.setAttribute("list", knockOffSearchList);
			}
			
			else if(type.equalsIgnoreCase("F"))
			{
				request.setAttribute("knockOffAuthorSearch","knockOffAuthorSearch");
				request.setAttribute("knockOffSearchList", "knockOffSearchList");
				request.setAttribute("list", knockOffSearchList);
				
			}
		}
		return mapping.findForward("searchKnockOffData");
	}
	
	public ActionForward openKnockOffValues(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("Inside KnockOffSearchDispatchAction........openKnockOffValues");
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId ="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
		}else{
			logger.info("in openKnockOffValues method of KnockOffSearchDispatchAction  action the session is out----------------");
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

		String loanId = CommonFunction.checkNull(request.getParameter("loanId"));
	
		String knockOffId = CommonFunction.checkNull(request.getParameter("knockOffId"));
//		String knockOffIdR = CommonFunction.checkNull(request.getParameter("knockOffIdR"));
//	    logger.info("knockOffIdR........"+knockOffIdR);
		session.removeAttribute("knockOffAuthorValues");
		session.removeAttribute("loanDataListR");
		session.removeAttribute("loanDataListP");
		session.removeAttribute("knockOffSearchList");
		session.removeAttribute("loanId");
		session.removeAttribute("knockOffID");
		session.removeAttribute("totalR");
	    session.removeAttribute("totalP");
	    session.removeAttribute("showdetails");
	    
		KnockOffDAO service=(KnockOffDAO)DaoImplInstanceFactory.getDaoImplInstance(KnockOffDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass()); 
		
		logger.info("function id is ........................................"+session.getAttribute("functionId").toString());
		String functionId="";

	
		if(session.getAttribute("functionId")!=null)
		{
			functionId=session.getAttribute("functionId").toString();
		}
		
		
		//ServletContext context=getServlet().getServletContext();
		if(context!=null)
		{
		flag = LockRecordCheck.lockCheck(userId,functionId,loanId,context);
		logger.info("Flag ........................................ "+flag);
		if(!flag)
		{
			logger.info("Record is Locked");			
			request.setAttribute("sms", "Locked");
			request.setAttribute("recordId", loanId);
			request.setAttribute("knockOffMakerSearch","knockOffMakerSearch");
			return mapping.findForward("searchKnockOffData");
		}
		}
		//ArrayList<Object> showdetails = service.getBusinessPartnerTypeList();
		//request.setAttribute("showdetails", showdetails);
		
		ArrayList<KnockOffMakerVO> knockOffSearchList = service.getKnockOffData(knockOffId,"P");
	    request.setAttribute("knockOffSearchList", knockOffSearchList);	
	    
		ArrayList<Object> loanDataListR = service.getKnockOffDetailsDataMaker(knockOffId,"R","P");
	    request.setAttribute("loanDataListR", loanDataListR);
	    
	    ArrayList<Object> loanDataListP = service.getKnockOffDetailsDataMaker(knockOffId,"P","P");		
	    request.setAttribute("loanDataListP", loanDataListP);
	    
	    ArrayList<Object> totalR = service.getTotalReceivableR(knockOffId,"P");
	    ArrayList<Object> totalP = service.getTotalReceivableP(knockOffId,"P");
	    request.setAttribute("totalR", totalR);
	    request.setAttribute("totalP", totalP);
	    
	    request.setAttribute("knockOffId", knockOffId);
	    request.setAttribute("knockOffMakerValues", "knockOffMakerValues");	
	    
		
	    //request.setAttribute("knockOffIdR", knockOffIdR);
	    
		return mapping.findForward("showKnockOffDataMaker");
	}
	
	public ActionForward openKnockOffValuesAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("Inside KnockOffSearchDispatchAction........openKnockOffValuesAuthor");
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId ="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
		}else{
			logger.info("in openKnockOffValuesAuthor method of  KnockOffSearchDispatchAction action the session is out----------------");
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

		String loanId = CommonFunction.checkNull(request.getParameter("loanId"));
		String knockOffId = CommonFunction.checkNull(request.getParameter("knockOffId"));
		
		session.setAttribute("loanId",loanId);
		KnockOffDAO service=(KnockOffDAO)DaoImplInstanceFactory.getDaoImplInstance(KnockOffDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass()); 
		
		logger.info("function id is ........................................"+session.getAttribute("functionId").toString());
		String functionId="";

		
		if(session.getAttribute("functionId")!=null)
		{
			functionId=session.getAttribute("functionId").toString();
		}
		
		
		//ServletContext context=getServlet().getServletContext();
		if(context!=null)
		{
		flag = LockRecordCheck.lockCheck(userId,functionId,loanId,context);
		logger.info("Flag ........................................ "+flag);
		if(!flag)
		{
			logger.info("Record is Locked");			
			request.setAttribute("sms", "Locked");
			request.setAttribute("recordId", loanId);
			request.setAttribute("knockOffAuthorSearch","knockOffAuthorSearch");
			return mapping.findForward("searchKnockOffData");
		}
		}
		//ArrayList<Object> showdetails = service.getBusinessPartnerTypeList();
		//session.setAttribute("showdetails", showdetails);
		
		ArrayList<KnockOffMakerVO> knockOffSearchList = service.getKnockOffData(knockOffId,"F");
	    session.setAttribute("knockOffSearchList", knockOffSearchList);
	    
	    ArrayList<Object> loanDataListR = service.getKnockOffDetailsDataMaker(knockOffId,"R","F");
	    session.setAttribute("loanDataListR", loanDataListR);
	    
	    ArrayList<Object> loanDataListP = service.getKnockOffDetailsDataMaker(knockOffId,"P","F"); 	    		
	    session.setAttribute("loanDataListP", loanDataListP);
	    
	    ArrayList<Object> totalR = service.getTotalReceivableR(knockOffId,"F");
	    ArrayList<Object> totalP = service.getTotalReceivableP(knockOffId,"F");
	    session.setAttribute("totalR", totalR);
	    session.setAttribute("totalP", totalP);
	    
	    session.setAttribute("knockOffId", knockOffId);
	    session.setAttribute("knockOffAuthorValues", "knockOffAuthorValues");	
	    
	    String loanRecStatus="";
		if(knockOffSearchList.size()>0)
		{
			loanRecStatus=knockOffSearchList.get(0).getLoanRecStatus();
			logger.info("loanRecStatus :::::::::::::::::::::::::::: "+loanRecStatus);
		}
		session.setAttribute("loanRecStatus", loanRecStatus);
	    
		return mapping.findForward("showKnockOffDataAuthor");
	}
}