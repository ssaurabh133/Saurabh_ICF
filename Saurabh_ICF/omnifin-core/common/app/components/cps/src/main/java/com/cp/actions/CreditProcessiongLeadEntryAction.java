/*

 * Template path: templates/java/JavaClass.vtl
 */
package com.cp.actions;

import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.CreditProcessingDAO;
import com.cp.vo.CreditProcessingLeadEntryVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

/**
 * MyEclipse Struts Creation date: 03-03-2011
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class CreditProcessiongLeadEntryAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(CreditProcessiongLeadEntryAction.class.getName());
	CreditProcessingDAO creditProcessing=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
	//CreditProcessingDAO creditProcessing = new CreditProcessingDAOImpl();
	
	public ActionForward leadEntryCapturing(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception

	{
		logger.info("In CreditProcessiongLeadEntryAction(leadEntryCapturing)");
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		String userId="";
		if(userobj!=null){
			//Asesh space starts
			session.setAttribute("userId", userobj.getUserId());
			session.setAttribute("userName", userobj.getUserName());
			session.setAttribute("branchId", userobj.getBranchId());
			userId=userobj.getUserId();
			//Asesh space end
		}
		else
		{
			logger.info(" in leadEntryCapturing method of CreditProcessiongLeadEntryAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		session.setAttribute("userId",userId);
		logger.info("userIDDDDDD------>>>>>>>" +userId);
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
		session.removeAttribute("child");
		String dealId = "";
		String dealStatus = request.getParameter("dealStatus");
		logger.info("In CreditProcessiongLeadEntryAction(leadEntryCapturing) dealStatus "+ session.getAttribute("maxId"));
		String status = CommonFunction.checkNull(request.getParameter("status"));
		logger.info("In CreditProcessiongLeadEntryAction(leadEntryCapturing) status -------------->"+ status);		
		//Nishant space starts
		if((dealStatus == null && status.isEmpty()) && CommonFunction.checkNull(session.getAttribute("asd")).equals("asd"))
		{
			session.removeAttribute("dealId");
		}
		//Nishant space end		
		if (dealStatus != null && dealStatus.equalsIgnoreCase("NEW")) 
		{
			session.removeAttribute("maxId");
			session.removeAttribute("dealId");
			session.removeAttribute("dealHeader");
		}
	
		if (session.getAttribute("dealId") != null) 
		{

			dealId = session.getAttribute("dealId").toString();
		
		} 
		else if (request.getParameter("dealId") != null	&& !request.getParameter("dealId").equalsIgnoreCase("")) 
		{
			session.removeAttribute("dealId");
			session.removeAttribute("loanId");
			
			if((dealStatus == null && status.isEmpty()) && CommonFunction.checkNull(session.getAttribute("asd")).equals("asd"))
			{
				dealId = "";
				
				session.removeAttribute("asd");
			}
			else
			{
				dealId = request.getParameter("dealId");
				
			}
		} 
		else if (session.getAttribute("maxId") != null) 
		{
			dealId = session.getAttribute("maxId").toString();
		}	
		
		ArrayList<CreditProcessingLeadEntryVo> list1 = new ArrayList<CreditProcessingLeadEntryVo>();
		String fromCM=CommonFunction.checkNull(request.getParameter("fromCM"));
	    if(fromCM.equalsIgnoreCase("cm"))
        	 dealId=CommonFunction.checkNull(request.getParameter("dealId"));
	    
        logger.info("In fromCM  (leadEntryCapturing) dealId------------>"+ dealId);
		if ((dealId != null && !dealId.equals(""))&& (status == null || status.equals(""))) 
		{
			logger.info("Implementation class: "+creditProcessing.getClass()); 			// changed by asesh
			ArrayList<Object> leadInfo = creditProcessing.getLeadEntryList(dealId);
			Iterator<Object> it = leadInfo.iterator();
			int i=0;
			while(i<leadInfo.size()-1)
			{
				list1.add((CreditProcessingLeadEntryVo) it.next());
				i++;
			}

			CreditProcessingLeadEntryVo  tb1 = (CreditProcessingLeadEntryVo) it.next();
			logger.info("............ "+tb1.getLeadNo());
			session.setAttribute("leadNo", tb1.getLeadNo());	
			logger.info("Implementation class: "+creditProcessing.getClass()); 			// changed by asesh
			ArrayList dealHeader = creditProcessing.getDealHeader(dealId);
			session.setAttribute("dealHeader", dealHeader);
			session.setAttribute("dealId", dealId);
			logger.info("Size of header: " + dealHeader.size());
			logger.info("Size of leadInfo: " + leadInfo.size());
			session.setAttribute("leadInfo", leadInfo);
			session.removeAttribute("viewDeal");
		}
		else if ((dealId != null && !dealId.equals(""))&& (status != null && status.equals("UWA")))
		{
			logger.info(" CreditProcessiongLeadEntryAction in leadEntryCapturing For view and set viewDeal in session");
			logger.info("Implementation class: "+creditProcessing.getClass()); 			// changed by asesh
			ArrayList<Object> leadInfo = creditProcessing.getLeadEntryList(dealId);
			Iterator<Object> it = leadInfo.iterator();
			int i=0;
			while(i<leadInfo.size()-1)
			{
				list1.add((CreditProcessingLeadEntryVo) it.next());
				i++;
			}
			CreditProcessingLeadEntryVo  tb1 = (CreditProcessingLeadEntryVo) it.next();
			logger.info("............ "+tb1.getLeadNo());
			session.setAttribute("leadNo", tb1.getLeadNo());	
			logger.info("Implementation class: "+creditProcessing.getClass()); 			// changed by asesh
			ArrayList dealHeader = creditProcessing.getDealHeader(dealId);
			session.setAttribute("dealHeader", dealHeader);
			session.setAttribute("dealId", dealId);
			session.setAttribute("leadInfo", leadInfo);
			session.setAttribute("viewDeal", "UWA");

		} 
		else 
		{
			session.removeAttribute("viewDeal");
			session.removeAttribute("leadInfo");
			session.removeAttribute("dealId");
			session.removeAttribute("dealHeader");
			session.removeAttribute("subIndustryList");
			session.removeAttribute("relationalManagerList");
		}
		String leadMQ="select PARAMETER_VALUE from parameter_mst WHERE PARAMETER_KEY='LEAD_MANDATORY'";
		String leadMValue=ConnectionDAO.singleReturn(leadMQ);
		logger.info("leadMValue: .......     " +leadMValue);
		logger.info("Implementation class: "+creditProcessing.getClass()); 			// changed by asesh
		ArrayList<Object> sourceTypeList = creditProcessing.getsourceTypeList();
		ArrayList<Object> dealCatList = creditProcessing.getDealCatList();
		session.setAttribute("dealCatList", dealCatList);
		session.setAttribute("sourceTypeList", sourceTypeList);
		CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+service.getClass()); 			// changed by asesh
		//CreditProcessingDAO service = new CreditProcessingDAOImpl();
		// Add By Anil
		ArrayList checkLoginUserLevel = service.getLoginUserLevel(userobj
				.getUserId(), userobj.getUserName());
		if (checkLoginUserLevel.size() > 0) {
			session.setAttribute("checkLoginUserLevel", checkLoginUserLevel);
		}
		//
		ArrayList creditApprovalList = service.getMakerData(dealId);
		session.setAttribute("creditApprovalList", creditApprovalList);
		session.setAttribute("leadMValue", leadMValue);
		String bsflag = request.getParameter("fId");
		session.setAttribute("bsflag", bsflag);
		saveToken(request);// Save Token Before Loading jsp in any case
		session.removeAttribute("custEntryU");
		return mapping.findForward("success");
	}
	public ActionForward showDealLimitEnhancement(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception

	{
		logger.info("In showDealLimitEnhancement()---------------->");
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
				session.setAttribute("branchId", userobj.getBranchId());
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
		session.removeAttribute("child");
		String dealId = "";

		String dealStatus = request.getParameter("dealStatus");

		logger.info("In CreditProcessiongLeadEntryAction(showDealLimitEnhancement) dealStatus "+ session.getAttribute("maxId"));

		if (dealStatus != null && dealStatus.equalsIgnoreCase("NEW")) {
			session.removeAttribute("maxId");
			session.removeAttribute("dealId");
			session.removeAttribute("dealHeader");
		}
	
		if (session.getAttribute("dealId") != null) {

			dealId = session.getAttribute("dealId").toString();
		
		} else if (request.getParameter("dealId") != null	&& !request.getParameter("dealId").equalsIgnoreCase("")) {
			dealId = request.getParameter("dealId");
			
		} else if (session.getAttribute("maxId") != null) {
			dealId = session.getAttribute("maxId").toString();
		}
		
		ArrayList<CreditProcessingLeadEntryVo> list1 = new ArrayList<CreditProcessingLeadEntryVo>();

		
        	 dealId=CommonFunction.checkNull(request.getParameter("dealId"));
        logger.info("In showDealLimitEnhancement dealId------------>"+ dealId);

		

		

		 if (dealId != null && !dealId.equals("") ){
			logger.info(" CreditProcessiongLeadEntryAction in showDealLimitEnhancement For view and set viewDeal in session");
			ArrayList<Object> leadInfo = creditProcessing.getLeadEntryList(dealId);
			Iterator<Object> it = leadInfo.iterator();
			int i=0;
			while(i<leadInfo.size()-1)
			{
				list1.add((CreditProcessingLeadEntryVo) it.next());
				i++;
			}

			CreditProcessingLeadEntryVo  tb1 = (CreditProcessingLeadEntryVo) it.next();
			logger.info("............ "+tb1.getLeadNo());
			session.setAttribute("leadNo", tb1.getLeadNo());
			
			ArrayList dealHeader = creditProcessing.getDealHeader(dealId);
			session.setAttribute("dealHeader", dealHeader);
			session.setAttribute("dealId", dealId);
			session.setAttribute("leadInfo", leadInfo);
			session.setAttribute("viewDeal", "UWA");

		} else {
			session.removeAttribute("viewDeal");
			session.removeAttribute("leadInfo");
			session.removeAttribute("dealId");
			session.removeAttribute("dealHeader");
			session.removeAttribute("subIndustryList");
			session.removeAttribute("relationalManagerList");
		}
//		String functionId="";
//		String userId = userobj.getUserId();
//		if(session.getAttribute("functionId")!=null)
//		{
//			functionId=session.getAttribute("functionId").toString();
//		}
//		
//		ServletContext context=getServlet().getServletContext();
//		flag = LockRecordCheck.lockCheck(userId,functionId,dealId,context);
//		logger.info("Flag ........................................ "+flag);
//		if(!flag)
//		{
//			logger.info("Record is Locked");			
//			request.setAttribute("sms", "Locked");
//			request.setAttribute("recordId", dealId);
//			//request.setAttribute("userId", userId);
//			return mapping.getInputForward();
//		}
		String leadMQ="select PARAMETER_VALUE from parameter_mst WHERE PARAMETER_KEY='LEAD_MANDATORY'";
		String leadMValue=ConnectionDAO.singleReturn(leadMQ);
		logger.info("leadMValue: .......     " +leadMValue);
		ArrayList<Object> sourceTypeList = creditProcessing.getsourceTypeList();
		ArrayList<Object> dealCatList = creditProcessing.getDealCatList();
		session.setAttribute("dealCatList", dealCatList);
		session.setAttribute("sourceTypeList", sourceTypeList);
		CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+service.getClass()); 			// changed by asesh
		//CreditProcessingDAO service = new CreditProcessingDAOImpl();
		ArrayList creditApprovalList = service.getMakerData(dealId);
		session.setAttribute("creditApprovalList", creditApprovalList);
		session.setAttribute("leadMValue", leadMValue);
		String bsflag = request.getParameter("fId");
		session.setAttribute("bsflag", bsflag);
		logger.info("dealId: .......     " +dealId);
		return mapping.findForward("success");
	}
}