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

import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.DeviationApprovalDAO;
import com.cp.vo.DeviationApprovalVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

/** 
 * MyEclipse Struts
 * Creation date: 09-12-2012
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class SaveLoanDeviationProcessAction extends DispatchAction {
	/*
	 * Generated Methods
	 */
	private static final Logger logger = Logger.getLogger(SaveLoanDeviationProcessAction.class.getName());
	/** 
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward saveLoanDeviationDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		logger.info("Inside  saveLoanDeviationDetails action");
		HttpSession session=request.getSession(false);
		//UserObject userobj=new UserObject();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String bDate="";
		String companyId="";
		if(userobj!=null)
		{
			userId	=userobj.getUserId();
			bDate=userobj.getBusinessdate();
			companyId=""+userobj.getCompanyId();
		}else{
			logger.info("here in saveLoanDeviationDetails method of SaveLoanDeviationProcessAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		boolean flag=false;		
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
		String loanId = "";		
		if(session.getAttribute("loanId") != null) {
			loanId = session.getAttribute("loanId").toString();
		} else if (session.getAttribute("maxIdInCM") != null) {
			loanId = session.getAttribute("maxIdInCM").toString();
		}
		String deviationId=CommonFunction.checkNull(request.getParameter("deviationId")).trim();
		String manualRemark=CommonFunction.checkNull(request.getParameter("manualRemark")).trim();
		String deviationIdList[]=deviationId.split(",");
		String manualRemarkList[]=manualRemark.split(",");
		String functionId=(String)session.getAttribute("functionId");			
		DeviationApprovalVo vo = new DeviationApprovalVo();
		vo.setMakerId(userId);
		vo.setMakerDate(bDate);
		vo.setLoanId(loanId);
		vo.setLoanDeviationId(deviationIdList);
		vo.setManualRemark(manualRemarkList);
		
		/* changed by asesh */
		DeviationApprovalDAO dao=(DeviationApprovalDAO)DaoImplInstanceFactory.getDaoImplInstance(DeviationApprovalDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass()); 	//changed by asesh
		String status = dao.saveLoanDeviation(vo);
		logger.info("Inside saveLoanDeviationDetails.....displaying status...."+status);
		
		ArrayList<DeviationApprovalVo> devList = dao.getLoanAllDeviation(loanId,"",functionId);	
		request.setAttribute("manualDevList", devList);
		request.setAttribute("sms",status);
		return mapping.findForward("success");
		
	    
	}
	
	public ActionForward forwardDeviationApproval(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		HttpSession session=request.getSession(false);
		//UserObject userobj=new UserObject();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String bDate="";
		String companyId="";
		if(userobj!=null)
		{
			userId	=userobj.getUserId();
			bDate=userobj.getBusinessdate();
			companyId=""+userobj.getCompanyId();
		}else{
			logger.info("here in forwardDeviationApproval method of SaveLoanDeviationProcessAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		DynaValidatorForm DeviationDynaValidatorForm= (DynaValidatorForm)form;
		String loanId = "";
		
		if(session.getAttribute("loanId") != null) {
			loanId = session.getAttribute("loanId").toString();
		} else if (session.getAttribute("maxIdInCM") != null) {
			loanId = session.getAttribute("maxIdInCM").toString();
		}
		
		logger.info("In SaveLoanDeviationProcessAction(execute) loanId: " + loanId);
		
		boolean flag=false;
		
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
		
	
		DeviationApprovalVo vo = new DeviationApprovalVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, DeviationDynaValidatorForm);	
		//String st=request.getParameter("status");
		//vo.setRecStatus(request.getParameter("status"));
		//vo.setManRecStat(request.getParameter("manualchk"));
		vo.setMakerId(userId);
		vo.setMakerDate(bDate);
		/* changed by asesh */
		DeviationApprovalDAO dao=(DeviationApprovalDAO)DaoImplInstanceFactory.getDaoImplInstance(DeviationApprovalDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass()); 	//changed by asesh
		//DeviationApprovalDAO dao = new DeviationApprovalDAOImpl();
		String sms="";
		boolean status = dao.loanForwardDeviationApproval(vo);
		logger.info("Inside forwardDeviationApproval.....displaying status...."+status);
		if(status){
			
			sms="A";
		}
		else{
			sms="E";
			
		}
		request.setAttribute("sms",sms);
	    logger.info("status"+status);
	    
	    return mapping.findForward("success");
	}
	public ActionForward sendBackDeviation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		HttpSession session=request.getSession(false);
		//UserObject userobj=new UserObject();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String bDate="";
		String companyId="";
		if(userobj!=null)
		{
			userId	=userobj.getUserId();
			bDate=userobj.getBusinessdate();
			companyId=""+userobj.getCompanyId();
		}else{
			logger.info("here in sendBackDeviation method of SaveLoanDeviationProcessAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		DynaValidatorForm DeviationDynaValidatorForm= (DynaValidatorForm)form;
        String loanId = "";
		
		if(session.getAttribute("loanId") != null) {
			loanId = session.getAttribute("loanId").toString();
		} else if (session.getAttribute("maxIdInCM") != null) {
			loanId = session.getAttribute("maxIdInCM").toString();
		}
		
		logger.info("In SaveLoanDeviationProcessAction(execute) loanId: " + loanId);
		
		boolean flag=false;
		
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
		
	
		DeviationApprovalVo vo = new DeviationApprovalVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, DeviationDynaValidatorForm);	
		//String st=request.getParameter("status");
		//vo.setRecStatus(request.getParameter("status"));
		//vo.setManRecStat(request.getParameter("manualchk"));
		vo.setMakerId(userId);
		vo.setMakerDate(bDate);
		vo.setLoanId(loanId);
		DeviationApprovalDAO dao=(DeviationApprovalDAO)DaoImplInstanceFactory.getDaoImplInstance(DeviationApprovalDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass()); 	
		//DeviationApprovalDAO dao = new DeviationApprovalDAOImpl();
		String sms="";
		boolean status = dao.loansendBackDeviation(vo);
		logger.info("Inside sendBackDeviation.....displaying status...."+status);
		if(status){
			
			sms="A";
		}
		else{
			sms="E";
			
		}
		request.setAttribute("sms",sms);
	    logger.info("status"+status);
	    
	    return mapping.findForward("success");
	}
}