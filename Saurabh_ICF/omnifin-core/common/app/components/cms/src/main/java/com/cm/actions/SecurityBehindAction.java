/*
 *<%--
      Author Name-Kaustuv Ranjan 
      Date of creation -26/04/2011
      Purpose-   Providing User Interface for Deposition of Security       
      Documentation-     
      
 --%>
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
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.CreditProcessingDAO;
import com.cm.dao.LoanInitiationDAO;
import com.cm.vo.CompoundFrequencyVO;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

/**
 * MyEclipse Struts Creation date: 04-28-2011
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class SecurityBehindAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(SecurityBehindAction.class.getName());
	/*
	 * Generated Methods
	 */

	/**
	 * Method execute
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward securityBehind(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in securityBehind method of SecurityBehindAction action the session is out----------------");
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
		//code added by neeraj 
		String functionId=(String)session.getAttribute("functionId");
		if(CommonFunction.checkNull(functionId).trim().equalsIgnoreCase(""))
			functionId="0";
		int id=Integer.parseInt(functionId);
		if(id==4000122 || id==4000123)
		{
			session.setAttribute("cmAuthor","cmAuthor");
			session.setAttribute("viewLoan","viewLoan");
		}
		if(id==4000106 || id==4000122)
		{
			session.removeAttribute("underWriterViewData");
			session.removeAttribute("leadNo");
			session.removeAttribute("dealHeader");
			session.removeAttribute("dealId");
			session.removeAttribute("leadInfo");
			session.removeAttribute("viewDeal");
			session.removeAttribute("dealCatList");
			session.removeAttribute("sourceTypeList");
			session.removeAttribute("checkLoginUserLevel");
			session.removeAttribute("creditApprovalList");
			session.removeAttribute("leadMValue");
			session.removeAttribute("bsflag");
	
		}
		//neeraj space end 
		String dealId = "";
		if (session.getAttribute("dealId") != null) 
			dealId = session.getAttribute("dealId").toString();
		else if (session.getAttribute("maxId") != null) 
			dealId = session.getAttribute("maxId").toString();
	
		String loanId = "";
		if (session.getAttribute("loanId") != null) 
			loanId = session.getAttribute("loanId").toString();
		else if (session.getAttribute("maxIdInCM") != null)
			loanId = session.getAttribute("maxIdInCM").toString();
		
		LoanInitiationDAO detail=(LoanInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(LoanInitiationDAO.IDENTITY);
		logger.info("Implementation class: "+detail.getClass()); 
		if ((loanId != null && !loanId.equalsIgnoreCase(""))) 
		{
			String f=CommonFunction.editableFlag();
	    	if(f!=null && f.equalsIgnoreCase("N"))
			{
				request.setAttribute("cmAuthor", "cmAuthor");
				boolean flag1 = CommonFunction.insertsecurity(loanId);
			}
			ArrayList tenureAmount=detail.getTenureAmount(loanId);
			logger.info("Size of tenureAmount: "+tenureAmount.size());			
			if(tenureAmount!=null && tenureAmount.size()!=0)
			{
				ArrayList<CompoundFrequencyVO> freqlist = detail.getCompFrequencyList();
				ArrayList<Object> briefSecurity = detail.getSecurityDetailAll(loanId);//Loan Id as
				request.setAttribute("briefSecurity", briefSecurity);
				request.setAttribute("freqlist", freqlist);
				request.setAttribute("tenureAmount", tenureAmount);
				return mapping.findForward("success");
			}		
			else
			{				
				request.setAttribute("saveCharge", "C");
				return mapping.findForward("chargeSuccess");
			}			
		} 
		else if(loanId.equalsIgnoreCase("") && dealId.equalsIgnoreCase(""))
		{
			request.setAttribute("back", "back");
			return mapping.findForward("backSuccess");
		}
		else if((dealId != null && !dealId.equalsIgnoreCase("")))
		{
			if(session.getAttribute("viewDeal")!=null && session.getAttribute("viewDeal").equals("UWA"))
			{
				request.setAttribute("cmAuthor", "cmAuthor");
			}
			CreditProcessingDAO detail1=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
	        logger.info("Implementation class: "+detail1.getClass()); 		//changed by asesh
			//CreditProcessingDAO detail1 = new CreditProcessingDAOImpl();
			ArrayList tenureAmount=detail1.getTenureAmountInDeal(dealId);
			logger.info("Size of tenureAmount: "+tenureAmount.size());
			
			
				if(tenureAmount!=null && tenureAmount.size()!=0)
				{
					ArrayList<CompoundFrequencyVO> freqlist = detail.getCompFrequencyList();
					ArrayList<Object> briefSecurity = detail1.getSecurityDetailAllInDeal(dealId,functionId);//Deal Id as
					request.setAttribute("briefSecurity", briefSecurity);
					request.setAttribute("freqlist", freqlist);
					request.setAttribute("tenureAmount", tenureAmount);
					return mapping.findForward("success");
				}		
				else
				{
					request.setAttribute("saveCharge", "C");
					return mapping.findForward("chargeDealSuccess");
				}
		}
		else 
		{
			request.setAttribute("back", "back");
			return mapping.findForward("backDealSuccess");
		}		
	}

	public ActionForward deleteSecurity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		logger.info("In SecurityBehindAction in deleteSecurity");
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in deleteSecurity method of SecurityBehindAction action the session is out----------------");
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
		String dealId = "";

		if (session.getAttribute("dealId") != null) {

			dealId = session.getAttribute("dealId").toString();
		} else if (session.getAttribute("maxId") != null) {
			dealId = session.getAttribute("maxId").toString();
		}

		logger.info("In SecurityBehindAction(deleteSecurity) dealId: " + dealId);
		
		String loanId = "";
        String deleteS="";
		if (session.getAttribute("loanId") != null) {

			loanId = session.getAttribute("loanId").toString();
		} else if (session.getAttribute("maxId") != null) {
			loanId = session.getAttribute("maxId").toString();
		}
		boolean status = false;
		if(loanId!=null && !loanId.equalsIgnoreCase(""))
		{
			LoanInitiationDAO detail=(LoanInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(LoanInitiationDAO.IDENTITY);
			logger.info("Implementation class: "+detail.getClass()); 
			String id[] = request.getParameterValues("chk");
			
			status = detail.deleteSecurityDeposit(id);
			if(status)
			{
				deleteS="S";
			}
			else
			{
				deleteS="E";
			}
			request.setAttribute("deleteS", deleteS);
			ArrayList<Object> briefSecurity = detail.getSecurityDetailAll(loanId);// Loan
																				// 1
			request.setAttribute("briefSecurity", briefSecurity);
		}
		else
		{
			CreditProcessingDAO detail1=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
	        logger.info("Implementation class: "+detail1.getClass()); 		//changed by asesh
			String id[] = request.getParameterValues("chk");
			
			status = detail1.deleteSecurityDepositInDeal(id);
			if(status)
			{
				deleteS="S";
			}
			else
			{
				deleteS="E";
			}
			request.setAttribute("deleteS", deleteS);
			String functionId=CommonFunction.checkNull(session.getAttribute("functionId")).trim();
			ArrayList<Object> briefSecurity = detail1.getSecurityDetailAllInDeal(dealId,functionId);// Loan
																				// 1
			request.setAttribute("briefSecurity", briefSecurity);
		}
		       
		return mapping.findForward("delete");

	}
	
	
	public ActionForward securityBehindForEmiCalc(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response)throws Exception{

		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in securityBehindForEmiCalc method of SecurityBehindAction action the session is out----------------");
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
		String dealId = "";

		if (session.getAttribute("dealId") != null) {

			dealId = session.getAttribute("dealId").toString();
		} else if (session.getAttribute("maxId") != null) {
			dealId = session.getAttribute("maxId").toString();
		}

		logger.info("In SecurityBehindAction(securityBehindForEmiCalc) dealId: " + dealId);
		//LoanInitiationDAO detail = new LoanInitiationDAOImpl();
		LoanInitiationDAO detail=(LoanInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(LoanInitiationDAO.IDENTITY);
		 if((dealId != null && !dealId.equalsIgnoreCase("")))
		{
			//CreditProcessingDAO detail1 = new CreditProcessingDAOImpl();
			CreditProcessingDAO detail1=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
            logger.info("Implementation class: "+detail1.getClass()); 		//changed by anil
			ArrayList tenureAmount=detail1.getTenureAmountInDealForEmiCalc(dealId);
			logger.info("Size of tenureAmount: "+tenureAmount.size());
			
			
				if(tenureAmount!=null && tenureAmount.size()!=0)
				{
					ArrayList<CompoundFrequencyVO> freqlist = detail.getCompFrequencyList();
					ArrayList<Object> briefSecurity = detail1.getSecurityDetailAllInDealForEmiCalc(dealId);//Deal Id as
					request.setAttribute("briefSecurity", briefSecurity);
					request.setAttribute("freqlist", freqlist);
	                request.setAttribute("tenureAmount", tenureAmount);
					return mapping.findForward("securityForEmiCalc");
		 }
		
		else
			{
				request.setAttribute("saveCharge", "C");
				return mapping.findForward("backChargeForEmiCalc");
			}
		
		}
				request.setAttribute("back", "back");
				return mapping.findForward("backsecurityToLoanDetails");
			
		}

	}
