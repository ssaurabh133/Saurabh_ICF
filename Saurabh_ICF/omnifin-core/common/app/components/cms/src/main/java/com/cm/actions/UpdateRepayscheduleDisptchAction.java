/*
Author Name :- Vishal Singh
Date of Creation :26-03-2012
Purpose :-  Repay Schedule Maker
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
import com.cm.dao.EarlyClosureDAO;
import com.cm.dao.RepayScheduleDAO;
import com.cm.vo.UpdateRepayscheduleSearchVO;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.cp.vo.RepayScheduleVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;


public class UpdateRepayscheduleDisptchAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(UpdateRepayscheduleDisptchAction.class.getName());
	
	
	
	public ActionForward openNewRepaySchedule(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		logger.info("Inside openNewRepaySchedule");
		
		HttpSession session =  request.getSession();
		session.removeAttribute("repayScheduleList");
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in openNewRepaySchedule method of UpdateRepayscheduleDisptchAction action the session is out----------------");
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
		
		// FOR DUE DAY DROP DOWN
		RepayScheduleDAO service=(RepayScheduleDAO)DaoImplInstanceFactory.getDaoImplInstance(RepayScheduleDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());
		
		
		ArrayList cycle = service.getCycleDateList();
		
		
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
	
		session.removeAttribute("reschId");
		session.removeAttribute("loanId");
		session.removeAttribute("baseRateList");
		
		session.setAttribute("cycle", cycle);
		return mapping.findForward("openNewRepaySchedule");
	}
	
	
	public ActionForward updateRepayScheduleData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		logger.info("In updateRepayScheduleData---------");
		HttpSession session = request.getSession();
		boolean flag=false;
		 int maxId=0;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String bDate ="";
		if(userobj!=null){
			userId= userobj.getUserId();
			bDate=userobj.getBusinessdate();
		}else{
			logger.info("here in updateRepayScheduleData method of UpdateRepayscheduleDisptchAction action the session is out----------------");
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
		
		UpdateRepayscheduleSearchVO vo = new UpdateRepayscheduleSearchVO();
		
		
		
		DynaValidatorForm repayScheduleActionDynaValidatorForm = (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,repayScheduleActionDynaValidatorForm);
		
		vo.setreschType("U");
		vo.setreschDate(bDate);
		vo.setresStatus("P");
		vo.setmakerDate(bDate);
		vo.setMakerId(userId);
		
		logger.info("********************** resch ID ************************ "+vo.getReschId());
		
		RepayScheduleDAO service=(RepayScheduleDAO)DaoImplInstanceFactory.getDaoImplInstance(RepayScheduleDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());
			
		String reschId=service.updateRepayScheduleData(vo);
		
		
		
		String lbxLoanNoHID= (String)request.getParameter("lbxLoanNoHID");
		
		
		
			ArrayList<UpdateRepayscheduleSearchVO> list=service.getRepayScheduleData(reschId,lbxLoanNoHID);
			
			
			request.setAttribute("repayScheduleList", list);
			 request.setAttribute("message", "S");
			
		
		
		return mapping.findForward("updateRepayScheduleData");
		
		
		
	}
	public ActionForward deleteRepayScheduleData(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		
		logger.info("In deleteRepayScheduleData---------");
		HttpSession session = request.getSession();
		boolean flag=false;
		 int maxId=0;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String bDate ="";
		if(userobj!=null){
			userId= userobj.getUserId();
			bDate=userobj.getBusinessdate();
		}else{
			logger.info("here in deleteRepayScheduleData method of UpdateRepayscheduleDisptchAction action the session is out----------------");
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
		
		UpdateRepayscheduleSearchVO vo = new UpdateRepayscheduleSearchVO();		
		DynaValidatorForm repayScheduleActionDynaValidatorForm = (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,repayScheduleActionDynaValidatorForm);
		String loanId=CommonFunction.checkNull( vo.getLbxLoanNoHID());
		String reschId=CommonFunction.checkNull(vo.getReschId());
		logger.info("In deleteRepayScheduleData()  reschId  :  "+reschId);
		logger.info("In deleteRepayScheduleData()  loanId  :  "+loanId);
		
		RepayScheduleDAO service=(RepayScheduleDAO)DaoImplInstanceFactory.getDaoImplInstance(RepayScheduleDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());
		
		ArrayList<UpdateRepayscheduleSearchVO> list=new ArrayList();
		//list=service.getRepayScheduleData(reschId,loanId);
		request.setAttribute("repayScheduleList", list);
		boolean status=false;
		if(loanId.trim().length()!=0 && reschId.trim().length()!=0)
		status=service.deleteDueDateData(loanId,reschId);
		
		if(status)
			request.setAttribute("delete","delete");
		else
			request.setAttribute("notDelete","notDelete");
		
		return mapping.findForward("updateRepayScheduleData");
		
	}
	
	public ActionForward farwordRepaySchedule(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		logger.info("In farwordRepaySchedule---------");
		HttpSession session = request.getSession();
		boolean flag=false;
		 int maxId=0;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String bDate ="";
		if(userobj!=null){
			userId= userobj.getUserId();
			bDate=userobj.getBusinessdate();
		}else{
			logger.info("here in farwordRepaySchedule method of UpdateRepayscheduleDisptchAction action the session is out----------------");
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
		
		UpdateRepayscheduleSearchVO vo = new UpdateRepayscheduleSearchVO();
		
		
		
		DynaValidatorForm repayScheduleActionDynaValidatorForm = (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,repayScheduleActionDynaValidatorForm);
		
		String reschId=(String)request.getParameter("reschId");
		
		vo.setresStatus("F");
		vo.setmakerDate(bDate);
		vo.setMakerId(userId);
		vo.setReschId(reschId);
		
		RepayScheduleDAO service=(RepayScheduleDAO)DaoImplInstanceFactory.getDaoImplInstance(RepayScheduleDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());
			
		boolean repayScheduleListUpdate=service.updateAndFarwordRepayScheduleData(vo);
		
	         if(repayScheduleListUpdate)
	         {
	        	 ArrayList<UpdateRepayscheduleSearchVO> list=service.getRepayScheduleData(reschId,vo.getLbxLoanNoHID());	 			
	 			 request.setAttribute("repayScheduleList", list);
	        	 request.setAttribute("msg", "F");
	         }else{
	        	 request.setAttribute("msg", "E");
	         }
	         
			
			return mapping.findForward("updateAndFarwordRepayScheduleData");
			
		
		
		
	}
	

	public ActionForward editRepayScheduleData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		logger.info("In editRepayScheduleData---------");
		HttpSession session = request.getSession();
		session.removeAttribute("repayScheduleList");
		boolean flag=false;
		 int maxId=0;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String bDate ="";
		if(userobj!=null){
			userId= userobj.getUserId();
			bDate=userobj.getBusinessdate();
		}else{
			logger.info("here in editRepayScheduleData method of UpdateRepayscheduleDisptchAction action the session is out----------------");
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
		
		UpdateRepayscheduleSearchVO vo = new UpdateRepayscheduleSearchVO();
		
		
		
		DynaValidatorForm repayScheduleActionDynaValidatorForm = (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,repayScheduleActionDynaValidatorForm);
		
		String loanId= (String)request.getParameter("loanId");
		String reschId=(String)request.getParameter("reschId");
		
		
		RepayScheduleDAO service=(RepayScheduleDAO)DaoImplInstanceFactory.getDaoImplInstance(RepayScheduleDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());
		
		
		ArrayList cycle = service.getCycleDateList();
		session.setAttribute("cycle", cycle);
		
		ArrayList<UpdateRepayscheduleSearchVO> list=service.getRepayScheduleData(reschId,loanId);	
		
		logger.info("due day ******************************** .............. "+vo.getDueDay());
		
		request.setAttribute("repayScheduleList", list);
		String makerDate=service.getMakerDate(reschId);
		session.setAttribute("businessDate",bDate);
		session.setAttribute("makerDate",makerDate);
           String viewDueDate=(String)session.getAttribute("viewDueDate");
		
		if(viewDueDate!=null && !viewDueDate.equals("null") && !viewDueDate.equals("") && viewDueDate.equals("viewDueDate")){		
			session.setAttribute("loanId", loanId);
			session.setAttribute("reschId", reschId);
		return mapping.findForward("editRepayScheduleDataForAuthor");
		}else{
			session.removeAttribute("loanId");
			session.removeAttribute("reschId");
			return mapping.findForward("editRepayScheduleData");
		}
		
		
    }
	
	public ActionForward newRepayScheduleDueDate(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws Exception {
		logger.info(" In newRepayScheduleDueDate ");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId ="";
		String businessDate ="";
		int compid =0;
		if(userobj!=null){
			userId = userobj.getUserId();
				businessDate=userobj.getBusinessdate();
				compid=userobj.getCompanyId();
		}else{
			logger.info("here in here in newRepayScheduleDueDate method of UpdateRepayscheduleDisptchAction action the session is out----------------");
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

		
		RepayScheduleVo vo = new RepayScheduleVo();
		vo.setCompanyId(compid);
		vo.setAuthorId(userId);
		vo.setAuthorDate(businessDate);
		//EarlyClosureDAO edao = new EarlyClosureDAOImpl();
		EarlyClosureDAO edao=(EarlyClosureDAO)DaoImplInstanceFactory.getDaoImplInstance(EarlyClosureDAO.IDENTITY);
		logger.info("Implementation class: "+edao.getClass()); 
		RepayScheduleDAO dao=(RepayScheduleDAO)DaoImplInstanceFactory.getDaoImplInstance(RepayScheduleDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());
		
		String loanId = CommonFunction.checkNull(request.getParameter("loanId"));
		String reschId = CommonFunction.checkNull(request.getParameter("reschId"));
		logger.info("In newRepaymentScheduleRepricing---loanId-"+loanId);  
		//boolean checkOldNewTenureFlag=dao.checkOldNewTenureAreEqual(loanId, reschId);
		//logger.info("checkOldNewTenureFlag:----"+checkOldNewTenureFlag);
		//if(checkOldNewTenureFlag){
//		    ArrayList<RepayScheduleVo> fromloanDtl=edao.getRepaySchFieldsDetail(loanId);
//		    request.setAttribute("fromloanDtl", fromloanDtl);
			ArrayList<RepayScheduleVo> repShedule=dao.getNewRepayScheduleDueDate(vo,loanId,reschId);
			request.setAttribute("repShedule", repShedule);
			logger.info("repShedule:   "+repShedule.size());
	//		if(repShedule.size()==0)
	//			request.setAttribute("noData","noData");	
		//}else{
			//request.setAttribute("newOldTenure","noData");	
		//}
		if(repShedule.size()>0){
		if(repShedule.get(0).getProcvalstatus().equalsIgnoreCase("E")){
			request.setAttribute("procStatus",repShedule.get(0).getProcval());
		}
}
		return mapping.findForward("newDueDateRepay");
	}	
	
	
	
	
}
	


