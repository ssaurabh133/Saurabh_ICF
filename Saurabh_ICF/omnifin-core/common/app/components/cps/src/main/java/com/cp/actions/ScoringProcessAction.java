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
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.CreditProcessingDAO;
import com.cp.vo.ScoringVO;
import com.lockRecord.action.LockRecordCheck;
import com.logger.LoggerMsg;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class ScoringProcessAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(ScoringProcessAction.class.getName());
	public ActionForward searchForScoring(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		LoggerMsg.info("In ScoringProcessAction searchForScoring() ");	
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();			
		}else{
			logger.info("here in searchForScoring method of ScoringProcessAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		CreditProcessingDAO dao=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass()); 		//changed by asesh
		//CreditProcessingDAO dao =new CreditProcessingDAOImpl();		
		ScoringVO scoringVo = new ScoringVO();			
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
		
		scoringVo.setCurrentPageLink(currentPageLink);
		
		DynaValidatorForm ScoringDynaValidatorForm = (DynaValidatorForm) form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(scoringVo, ScoringDynaValidatorForm);
		
		if(CommonFunction.checkNull(scoringVo.getReportingToUserId()).equalsIgnoreCase(""))
		{ 
			scoringVo.setReportingToUserId(userId);
		   //logger.info("When user id is not selected by the user:::::"+userId);
		}
		logger.info("user Id:::::"+scoringVo.getReportingToUserId());
//		String userId=request.getParameter("userId");
//		scoringVo.setUserId(userId);
//		if(userId.trim().length()==0)
//		{
//			userId=userobj.getUserName();
//			scoringVo.setUserId(userId);
//		}
			
		ArrayList<ScoringVO>dealdetails= dao.scoringSearchGrid(scoringVo);

	    logger.info("In searchDealCapturing....list: "+dealdetails.size());
		
	    request.setAttribute("list", dealdetails);
		
		logger.info("list.isEmpty(): "+dealdetails.size());
		
		request.setAttribute("dealdetails", dealdetails);
	
		return mapping.findForward("search");
}
	
	public ActionForward savedScoringData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		logger.info("IN savedScoringData...");
		HttpSession session = request.getSession();
	
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
	String userId="";
		
		if(userobj!=null)
		{
				userId=userobj.getUserId();				
		}else{
			logger.info("here in savedScoringData method of ScoringProcessAction action the session is out----------------");
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
		 ScoringVO scoringVo = new ScoringVO();
		 CreditProcessingDAO dao=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
	        logger.info("Implementation class: "+dao.getClass()); 		//changed by asesh
		 String lbxDealNo=request.getParameter("lbxDealNo");
		 scoringVo.setLbxDealNo(request.getParameter("lbxDealNo"));
		 logger.info("In savedScoringData setLbxDealNo:-"+lbxDealNo); 

		logger.info("function id is ........................................"+session.getAttribute("functionId").toString());
		String functionId="";
	
		if(session.getAttribute("functionId")!=null)
		{
			functionId=session.getAttribute("functionId").toString();
		}
		
		
		//ServletContext context=getServlet().getServletContext();
		if(context!=null)
		{
			flag = LockRecordCheck.lockCheck(userId,functionId,lbxDealNo,context);
			logger.info("Flag ........................................ "+flag);
			if(!flag)
			{
				logger.info("Record is Locked");			
				request.setAttribute("msg", "Locked");
				request.setAttribute("recordId", lbxDealNo);
				request.setAttribute("userId", userId);
				return mapping.findForward("success");
			}
			
		}
		String viewMode = request.getParameter("viewMode");
		logger.info("viewMode" + viewMode);
		if(CommonFunction.checkNull(viewMode).equalsIgnoreCase("VS"))
		{
			request.setAttribute("viewMode", "VS");
		}
		ArrayList<ScoringVO> scoringList= dao.getScoringList(scoringVo);
		request.setAttribute("scoringList", scoringList);
		logger.info("scoringList    Size:---"+scoringList.size());
		
		return mapping.findForward("score");
	}
	
	public ActionForward onSaveScore(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		logger.info("in onSaveScore..........");
		
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in onSaveScore method of ScoringProcessAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		String sessionId = session.getAttribute("sessionID").toString();
		CreditProcessingDAO dao=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass()); 		//changed by asesh
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
		
		DynaValidatorForm ScoringDynaValidatorForm = (DynaValidatorForm)form;
		 ScoringVO scoringVo = new ScoringVO();	
		org.apache.commons.beanutils.BeanUtils.copyProperties(scoringVo, ScoringDynaValidatorForm);
		String scoringId=(request.getParameter(CommonFunction.checkNull("scoringID")));
		logger.info("in onSaveScore scoringId"+scoringId);
	     scoringVo.setScoringID(scoringId);
		 boolean result=false;
		 String msg="";
		 result=dao.updateOnSaveScore(scoringVo);
		 logger.info("In updateOnSaveScore,,,,,"+result);
		 if(result){
			 msg="S";
			 logger.info("00msg"+msg);
	
			 ArrayList<ScoringVO> scoringList= dao.getScoringList(scoringVo);
	    	 scoringList.add(scoringVo);
			 request.setAttribute("scoringList", scoringList);
		 }
	else{
		msg="E";
	
	    logger.info("msg1"+msg);
	   
	}
	request.setAttribute("msg", msg);
	logger.info("result"+result);
		
		
		
	return mapping.findForward("updateOnSaveScore");
}        
}