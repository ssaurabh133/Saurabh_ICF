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

import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.cm.dao.LoanInitiationDAO;
import com.cm.vo.CommonLoanVo;
import com.connect.CommonFunction;
import com.cp.dao.CreditProcessingDAO;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

import org.apache.log4j.Logger;
public class EditLoanDetailAction extends DispatchAction 
{
	private static final Logger logger = Logger.getLogger(EditLoanDetailAction.class.getName());
	public ActionForward defaultEditLoanMaker(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("here in defaultEditLoanMaker");
		HttpSession session =  request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId ="";
		String branch ="";
		if(userobj!=null)
		{
			userId = userobj.getUserId();	
			branch=userobj.getBranchId();
		}
		else
		{
			logger.info("here in defaultEditLoanMaker");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
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
		DynaValidatorForm DynaForm = (DynaValidatorForm) form;
		CommonLoanVo vo = new CommonLoanVo();
		logger.info("current page link .......... "+request.getParameter("d-49520-p"));		
		int currentPageLink = 0;
		if(request.getParameter("d-49520-p")==null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
			currentPageLink=1;
		else
			currentPageLink =Integer.parseInt(request.getParameter("d-49520-p"));
		logger.info("current page link ................ "+request.getParameter("d-49520-p"));
		vo.setCurrentPageLink(currentPageLink);
		vo.setEditStatus("P");
		vo.setEditData("O");
		vo.setUserId(userId);
		vo.setBranch(branch);
		vo.setCaseType("O");
		LoanInitiationDAO dao=(LoanInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(LoanInitiationDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass()); 

		ArrayList<Object> loandetails=dao.forwardedEditLoan(vo);
		request.setAttribute("list", loandetails);	
		session.removeAttribute("new");
		session.setAttribute("new","N");	
		session.setAttribute("cmAuthor","cmAuthor");
		session.setAttribute("viewLoan","viewLoan");
		session.setAttribute("editLoanMaker","editLoanMaker");
		session.setAttribute("DealCap","DealCap");
		session.removeAttribute("viewMachineInCM");	
		 
		session.removeAttribute("searchLoan");				
		return mapping.findForward("success");
	}	
	public ActionForward defaultEditLoanAuthor(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("here in defaultEditLoanAuthor");
		HttpSession session =  request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId ="";
		String branch ="";
		
		if(userobj!=null)
		{
			userId = userobj.getUserId();	
			branch=userobj.getBranchId();
		}
		else
		{
			logger.info("here in defaultEditLoanAuthor");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
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
		DynaValidatorForm DynaForm = (DynaValidatorForm) form;
		CommonLoanVo vo = new CommonLoanVo();
		logger.info("current page link .......... "+request.getParameter("d-49520-p"));		
		int currentPageLink = 0;
		if(request.getParameter("d-49520-p")==null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
			currentPageLink=1;
		else
			currentPageLink =Integer.parseInt(request.getParameter("d-49520-p"));
		logger.info("current page link ................ "+request.getParameter("d-49520-p"));
		vo.setCurrentPageLink(currentPageLink);
		vo.setEditStatus("F");
		vo.setEditData("O");
		vo.setUserId(userId);
		vo.setBranch(branch);
		vo.setCaseType("O");
		LoanInitiationDAO dao=(LoanInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(LoanInitiationDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass()); 
		ArrayList<Object> loandetails=dao.forwardedEditLoan(vo);
		request.setAttribute("list", loandetails);	
		session.setAttribute("cmAuthor","cmAuthor");
		session.setAttribute("misAuthor","misAuthor");
		//session.setAttribute("viewLoan","viewLoan");
		session.setAttribute("editLoanAuthor","editLoanAuthor");
		session.removeAttribute("searchLoan");	
		session.removeAttribute("viewDeviation");			
		session.setAttribute("viewMachineInCM","viewMachineInCM");
		session.removeAttribute("DealCap");	
		session.removeAttribute("new");
		session.setAttribute("new","N");	
		return mapping.findForward("success");
	}
	public ActionForward newEditLoan(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("here in newEditLoan");
		HttpSession session =  request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId ="";
		String branch="";
		if(userobj!=null)
		{
			userId = userobj.getUserId();	
			branch=userobj.getBranchId();
		}
		else
		{
			logger.info("here in newEditLoan");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
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
		DynaValidatorForm DynaForm = (DynaValidatorForm) form;
		CommonLoanVo vo = new CommonLoanVo();
		logger.info("current page link .......... "+request.getParameter("d-49520-p"));		
		int currentPageLink = 0;
		if(request.getParameter("d-49520-p")==null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
			currentPageLink=1;
		else
			currentPageLink =Integer.parseInt(request.getParameter("d-49520-p"));
		logger.info("current page link ................ "+request.getParameter("d-49520-p"));
		vo.setCurrentPageLink(currentPageLink);
		//vo.setEditStatus("F");
		//vo.setEditData("N");
		vo.setEditStatus("P");
		vo.setEditData("O");
		vo.setUserId(userId);
		vo.setBranch(branch);
		//vo.setCaseType("N");
		vo.setCaseType("O");
		LoanInitiationDAO dao=(LoanInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(LoanInitiationDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass()); 
		ArrayList<Object> loandetails=dao.forwardedEditLoan(vo);
		request.setAttribute("list", loandetails);	
		request.setAttribute("newCase","newCase");	
		session.setAttribute("editLoanMaker","editLoanMaker");
		session.setAttribute("cmAuthor","cmAuthor");
		session.setAttribute("viewLoan","viewLoan");
		session.setAttribute("DealCap","DealCap");
		session.removeAttribute("viewMachineInCM");	
		session.setAttribute("userId",userId); 
		session.removeAttribute("searchLoan");
		session.removeAttribute("new");
		session.setAttribute("new","Y");	
		String errorFlag=CommonFunction.checkNull(request.getAttribute("errorFlag")).trim();
		String errorMsg=CommonFunction.checkNull(request.getAttribute("errorMsg")).trim();
		if(errorFlag.equalsIgnoreCase("E"))
			request.setAttribute("errorMsg",errorMsg);
		return mapping.findForward("success");
	}
	public ActionForward editLoanForward(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("here in editLoanForward");
		HttpSession session =  request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId ="";
		String bDate ="";
		
		if(userobj!=null)
		{
			userId = userobj.getUserId();	
			bDate= userobj.getBusinessdate();	
		}
		else
		{
			logger.info("here in newEditLoan");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
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
		DynaValidatorForm DynaForm = (DynaValidatorForm) form;
		CommonLoanVo vo = new CommonLoanVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, DynaForm);
		String loanId=session.getAttribute("loanId").toString();
		LoanInitiationDAO dao=(LoanInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(LoanInitiationDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass()); 
		vo.setLbxLoanNoHID(loanId);
		vo.setUserId(userId);
		vo.setbDate(bDate);
		vo.setCaseType("O");
		String queryCheckRelation="select COUNT(1) from cr_loan_customer_role_edit cr join com_address_m_edit cm on cm.bpid=cr.gcd_id WHERE cr.loan_id='"+loanId+"' and RELATIONSHIP_FLAG='N'  ";
		int relationCount=Integer.parseInt(ConnectionDAO.singleReturn(queryCheckRelation));
		if(relationCount<1){
		ArrayList<Object> result=dao.forwardEditLoan(vo);
		String status=(String)result.get(0);
		String msg=(String)result.get(1);
		if(CommonFunction.checkNull(status).trim().equalsIgnoreCase("E"))
			request.setAttribute("editError",msg);
		else
			request.setAttribute("editSuccess",msg);
		}else{
			String msg="Please capture Relationship Details in Customer Address Details Tab";
			request.setAttribute("editError",msg);
		}
		return mapping.findForward("forward");				
	}	
	public ActionForward searchEditLoan(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("here in searchEditLoan");
		HttpSession session =  request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId ="";
		String branch="";
		if(userobj!=null)
		{
			userId = userobj.getUserId();	
			branch=userobj.getBranchId();
		}
		else
		{
			logger.info("here in newEditLoan");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
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
		DynaValidatorForm DynaForm = (DynaValidatorForm) form;
		CommonLoanVo vo = new CommonLoanVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, DynaForm);
		logger.info("current page link .......... "+request.getParameter("d-49520-p"));		
		int currentPageLink = 0;
		if(request.getParameter("d-49520-p")==null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
			currentPageLink=1;
		else
			currentPageLink =Integer.parseInt(request.getParameter("d-49520-p"));
		logger.info("current page link ................ "+request.getParameter("d-49520-p"));
		vo.setCurrentPageLink(currentPageLink);
		String editLoanAuthor=(String)session.getAttribute("editLoanAuthor");
		String editLoanMaker=(String)session.getAttribute("editLoanMaker");
		String newv=(String)session.getAttribute("new");
		if(!CommonFunction.checkNull(editLoanAuthor).trim().equalsIgnoreCase(""))
		{
			vo.setEditStatus("F");
			vo.setEditData("O");
			vo.setCaseType("O");
		}
		
	/*	if(!CommonFunction.checkNull(editLoanMaker).trim().equalsIgnoreCase("")&& CommonFunction.checkNull(newv).trim().equalsIgnoreCase("N"))
		{
				vo.setEditStatus("P");
				vo.setEditData("O");
				vo.setCaseType("O");
		} */
		if(!CommonFunction.checkNull(editLoanMaker).trim().equalsIgnoreCase("")&& CommonFunction.checkNull(newv).trim().equalsIgnoreCase("Y"))
		{
			vo.setEditStatus("F");
			vo.setEditData("N");
			vo.setCaseType("N");
			request.setAttribute("newCase","newCase");	
		}
		vo.setUserId(userId);
		vo.setBranch(branch);
		LoanInitiationDAO dao=(LoanInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(LoanInitiationDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass()); 
		ArrayList<Object> loandetails=dao.forwardedEditLoan(vo);
		request.setAttribute("list", loandetails);	
		return mapping.findForward("success");
	}
	
	public ActionForward editLoanSave(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("here in editLoanSave");
		HttpSession session =  request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId ="";
		String bDate ="";
		
		if(userobj!=null)
		{
			userId = userobj.getUserId();	
			bDate= userobj.getBusinessdate();	
		}
		else
		{
			logger.info("here in newEditLoan");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
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
		DynaValidatorForm DynaForm = (DynaValidatorForm) form;
		CommonLoanVo vo = new CommonLoanVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, DynaForm);
		String loanId=session.getAttribute("loanId").toString();
		LoanInitiationDAO dao=(LoanInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(LoanInitiationDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass()); 
		vo.setLbxLoanNoHID(loanId);
		vo.setUserId(userId);
		vo.setbDate(bDate);
		boolean result=dao.saveEditLoan(vo);
		session.removeAttribute("loanList");
		ArrayList loanList=dao.getloanListInLoanForEdit(loanId);
		session.setAttribute("loanList", loanList);
		if(result)
		{
			request.setAttribute("editSms","S");
		}
		else
		{
			request.setAttribute("editSms","E");
		}
		return mapping.findForward("save");				
	}			
	
}