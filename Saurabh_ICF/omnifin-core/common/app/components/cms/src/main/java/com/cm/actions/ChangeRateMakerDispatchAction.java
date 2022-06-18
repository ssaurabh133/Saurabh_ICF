package com.cm.actions;


import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;
import com.cm.dao.ChangeRateDAO;
import com.cm.vo.ChangeRateVo;
import com.connect.DaoImplInstanceFactory;

import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

import org.apache.log4j.Logger;
             
public class ChangeRateMakerDispatchAction extends DispatchAction{
	
	private static final Logger logger = Logger.getLogger(ChangeRateMakerDispatchAction.class.getName());

	/*  Open New Screen For Maker */

	public ActionForward OpenNonEMILoanMaker(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{

		HttpSession session=request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		
		if(userobj!=null)
		{
			userId=userobj.getUserId();
		}else{
			logger.info("here in execute  method of NonEMIBasedMakerBehindActionthe session is out----------------");
			return mapping.findForward("sessionOut");
		}
		
		ChangeRateVo changeRateVo = new ChangeRateVo(); 
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
		
		changeRateVo.setCurrentPageLink(currentPageLink);
		//change by sachin
	    ChangeRateDAO dao=(ChangeRateDAO)DaoImplInstanceFactory.getDaoImplInstance(ChangeRateDAO.IDENTITY);
	    logger.info("Implementation class: "+dao.getClass());
	    //end by sachin			
//		ChangeRateDAO dao= new ChangeRateDAOImpl();
		DynaValidatorForm changeRateDynaValidatorForm = (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(changeRateVo, changeRateDynaValidatorForm);
		request.setAttribute("Maker","Maker");
		return mapping.findForward("OpenMakerScreen");
			
	}
	
	
	/*   Record Saved By Maker */
	
	public ActionForward onSaveNonEMIBasedMaker(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		logger.info("In ChangeRateMakerDispatchAction:::::::::::onSaveNonEMIBasedMaker");
		HttpSession session=request.getSession(false);
		//UserObject userobj=new UserObject();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String bDate="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			bDate=userobj.getBusinessdate();
		}else{
			logger.info("here onSaveNonEMIBasedMaker method of  ChangeRateMakerDispatchAction action the session is out-----------");
			return mapping.findForward("sessionOut");
		}

		session.removeAttribute("queueid");
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
		DynaValidatorForm changeRateDynaValidatorForm= (DynaValidatorForm)form;
		ChangeRateVo changeRateVo = new ChangeRateVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(changeRateVo, changeRateDynaValidatorForm);
        changeRateVo.setMakerId(userId);
        changeRateVo.setMakerDate(bDate);
      //change by sachin
		ChangeRateDAO dao=(ChangeRateDAO)DaoImplInstanceFactory.getDaoImplInstance(ChangeRateDAO.IDENTITY);
	    logger.info("Implementation class: "+dao.getClass());
	   //end by sachin	        
//      ChangeRateDAO dao= new ChangeRateDAOImpl();		
		String sms="";
		boolean status=false;
		String lbxLoanNo=request.getParameter("lbxLoanNo");
		String loanRateType=request.getParameter("loanRateType");
		changeRateVo.setLbxLoanNo(lbxLoanNo);
		changeRateVo.setLoanRateType(loanRateType);
		boolean checkStatus=dao.checkLoanStatus(lbxLoanNo);
		
		if(checkStatus){
			status=dao.updateMakerData(changeRateVo);
		}
		else
		{
			status = dao.insertMakerData(changeRateVo);
		}	
		if(status){
				sms="S";
				request.setAttribute("sms",sms);
				request.setAttribute("Maker","Maker");
				ArrayList list = new ArrayList();
				list.add(changeRateVo);
				request.setAttribute("list",list);
	
			}
		else {
				sms="N";
				request.setAttribute("Maker","Maker");
				request.setAttribute("defaultVal",changeRateVo);	
				request.setAttribute("sms",sms);
			
			}
			  
	return mapping.findForward("OpenMakerScreen");
	
	  
	}
	
	
	/*   Open Screen In Edit Mode With Data For Maker*/
	
	public ActionForward EditNonEMILoanMaker(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		logger.info("In ChangeRateMakerDispatchAction:::::::::::OpenNonEMILoanMaker");
		HttpSession session=request.getSession(false);
		//UserObject userobj=new UserObject();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String bDate="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			bDate=userobj.getBusinessdate();
		}else{
			logger.info("here OpenNonEMILoanMaker the session is out-----------");
			return mapping.findForward("sessionOut");
		}

		session.removeAttribute("queueid");
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
		DynaValidatorForm changeRateDynaValidatorForm= (DynaValidatorForm)form;
		ChangeRateVo changeRateVo = new ChangeRateVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(changeRateVo, changeRateDynaValidatorForm);
        changeRateVo.setMakerId(userId);
        changeRateVo.setMakerDate(bDate);
      //change by sachin
	    ChangeRateDAO dao=(ChangeRateDAO)DaoImplInstanceFactory.getDaoImplInstance(ChangeRateDAO.IDENTITY);
	    logger.info("Implementation class: "+dao.getClass());
	       //end by sachin	       
//        ChangeRateDAO dao= new ChangeRateDAOImpl();		
		String sms="";
		String LoanId=request.getParameter("lbxLoanNo");
		changeRateVo.setLbxLoanNo(LoanId);		
		ArrayList<ChangeRateVo> searchListGrid = dao.nonEmiBasedGrid(changeRateVo);
		request.setAttribute("list", searchListGrid);
		request.setAttribute("Maker", "Maker");

	    return mapping.findForward("OpenMakerScreen");
	
  
	}
	
	/*   Update Record By Maker */
	
	public ActionForward UpdateNonEMILoanMaker(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		logger.info("In ChangeRateMakerDispatchAction:::::::::::onSaveNonEMIBasedMaker");
		HttpSession session=request.getSession(false);
		//UserObject userobj=new UserObject();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String bDate="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			bDate=userobj.getBusinessdate();
		}else{
			logger.info("here onSaveNonEMIBasedMaker method of  ChangeRateMakerDispatchAction action the session is out-----------");
			return mapping.findForward("sessionOut");
		}

		session.removeAttribute("queueid");
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
		DynaValidatorForm changeRateDynaValidatorForm= (DynaValidatorForm)form;
		ChangeRateVo changeRateVo = new ChangeRateVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(changeRateVo, changeRateDynaValidatorForm);
        changeRateVo.setMakerId(userId);
        changeRateVo.setMakerDate(bDate);
      //change by sachin
		ChangeRateDAO dao=(ChangeRateDAO)DaoImplInstanceFactory.getDaoImplInstance(ChangeRateDAO.IDENTITY);
	    logger.info("Implementation class: "+dao.getClass());
	       //end by sachin	    
//      ChangeRateDAO dao= new ChangeRateDAOImpl();
		String sms="";
		String LoanId=request.getParameter("lbxLoanNo");
		changeRateVo.setLbxLoanNo(LoanId);
				
		boolean status = dao.updateMakerData(changeRateVo);
     	logger.info("status::::::::::::::::"+status);
     	ArrayList list = new ArrayList();
		list.add(changeRateVo);

	    if(status){
			sms="S";
			request.setAttribute("sms",sms);
			request.setAttribute("Maker", "Maker");	
			request.setAttribute("list", list);
		}
		else {
			sms="N";
			request.setAttribute("list", list);
			request.setAttribute("Maker", "Maker");
			request.setAttribute("sms",sms);
		
		}
	    
	    return mapping.findForward("OpenMakerScreen");
	
  
	}
	
	/*  Record Forwarded To Author By Maker */
	
	public ActionForward ForwardSaveNonEMILoanData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		logger.info("In ChangeRateMakerDispatchAction:::::::::::ForwardSaveNonEMILoanData");
		HttpSession session=request.getSession(false);
		//UserObject userobj=new UserObject();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String bDate="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			bDate=userobj.getBusinessdate();
		}else{
			logger.info("here ForwardSaveNonEMILoanData the session is out-----------");
			return mapping.findForward("sessionOut");
		}

		session.removeAttribute("queueid");
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
		DynaValidatorForm changeRateDynaValidatorForm= (DynaValidatorForm)form;
		ChangeRateVo changeRateVo = new ChangeRateVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(changeRateVo, changeRateDynaValidatorForm);
        changeRateVo.setMakerId(userId);
        changeRateVo.setMakerDate(bDate);
      //change by sachin
		ChangeRateDAO dao=(ChangeRateDAO)DaoImplInstanceFactory.getDaoImplInstance(ChangeRateDAO.IDENTITY);
	    logger.info("Implementation class: "+dao.getClass());
   //end by sachin	        
//      ChangeRateDAO dao= new ChangeRateDAOImpl();		
		String sms="";
		String LoanId=request.getParameter("lbxLoanNo");
		changeRateVo.setLbxLoanNo(LoanId);
		logger.info("LoanId ......:::::::::........ "+LoanId);
		
		boolean status=dao.forwardNonEMILoanData(changeRateVo);
		
		
		   if(status){
				sms="F";
				request.setAttribute("defaultVal", changeRateVo);
				request.setAttribute("Maker","Maker");
				request.setAttribute("sms",sms);
			}
			else {
				sms="E";
				request.setAttribute("sms",sms);
				request.setAttribute("Maker", "Maker");
				ArrayList<ChangeRateVo> searchListGrid = dao.nonEmiBasedGrid(changeRateVo);
				request.setAttribute("list", searchListGrid);
			
			}
		    return mapping.findForward("OpenMakerScreen");
	
	}
	
}
