/*
 * <%--
      Author Name-  Kaustuv Ranjan    
      Date of creation -26/04/2011
      Purpose-   Providing User Interface for Deposition of Security       
      Documentation-     
   --%>
 * 
 */

package com.cm.actions;


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
import com.cm.dao.LoanInitiationDAO;
import com.cm.vo.SecurityDepositVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.CreditProcessingDAO;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.tabDependencyCheck.RefreshFlagValueInsert;
import com.tabDependencyCheck.RefreshFlagVo;

/** 
 * MyEclipse Struts
 * Creation date: 04-25-2011
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class SecurityDepositAction extends Action {
	private static final Logger logger = Logger.getLogger(SecurityDepositAction.class.getName());
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
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("In CustomerEntryAction in saveSecurityDeposit........................................");
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String bDate =""; 
	    String userId="";
	   
		 if(userobj!=null)
			{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
			}else{
				logger.info("here in execute method of SecurityDepositAction action the session is out----------------");
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
		DynaValidatorForm SecurityDepositDynaValidatorForm= (DynaValidatorForm)form;
		String dealId = "";

		if (session.getAttribute("dealId") != null) {

			dealId = session.getAttribute("dealId").toString();
		} else if (session.getAttribute("maxId") != null) {
			dealId = session.getAttribute("maxId").toString();
		}
		logger.info("In SecurityDepositAction DealId "+ dealId);
		
		String loanId = "";

		if (session.getAttribute("loanId") != null) {

			loanId = session.getAttribute("loanId").toString();
		} else if (session.getAttribute("maxIdInCM") != null) {
			loanId = session.getAttribute("maxIdInCM").toString();
		}
		
		logger.info("In SecurityDepositAction loan id: " + loanId);
		if(loanId!=null && !loanId.equalsIgnoreCase(""))
		{
			String sms="";
			
		    SecurityDepositVO vo = new SecurityDepositVO();
			org.apache.commons.beanutils.BeanUtils.copyProperties(vo, SecurityDepositDynaValidatorForm);
			vo.setLoanId(loanId);
			vo.setUserId(userId);
			vo.setTxnType("LIM");
			vo.setBussinessDate(bDate);
			LoanInitiationDAO detail=(LoanInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(LoanInitiationDAO.IDENTITY);
			logger.info("Implementation class: "+detail.getClass()); 
			boolean status = detail.saveSecurityDeposit(vo);
			if(status)
			 {
				sms="S";
				//ArrayList<CompoundFrequencyVO> freqlist = detail.getCompFrequencyList();
				//request.setAttribute("freqlist",freqlist );			
				request.setAttribute("status", "Insertion is Completed Successfully!!!");
				
				RefreshFlagVo vo1 = new RefreshFlagVo();
				vo1.setTabIndex(4);
				if(loanId!=null && !loanId.trim().equalsIgnoreCase(""))
					vo1.setRecordId(Integer.parseInt(loanId.trim()));
	        		vo1.setModuleName("CM");
	    		RefreshFlagValueInsert.updateRefreshFlag(vo1);
	    		
	    		String repayQ="select LOAN_REPAYMENT_TYPE from cr_loan_dtl where LOAN_ID="+loanId;
			    logger.info("Repayment Query: "+repayQ);
			    String repayType=ConnectionDAO.singleReturn(repayQ);
				logger.info("Repayment Type:"+repayType);
				RefreshFlagVo vo3 = new RefreshFlagVo();
				if(loanId!=null && !loanId.trim().equalsIgnoreCase(""))
				   	vo3.setRecordId(Integer.parseInt(loanId.trim()));
				vo3.setModuleName("CM");
				vo3.setTabIndex(4);
				vo3.setNonInstallment(repayType);
				RefreshFlagValueInsert.updateRefreshFlag(vo3);
				
				 
			 } 
			else
			{
				sms="E";
			}
			ArrayList tenureAmount=detail.getTenureAmount(loanId);
			 logger.info("loanId::::::::::::: ");
			ArrayList<Object> briefSecurity = detail.getSecurityDetailAll(loanId);//Loan Id as 1
			request.setAttribute("briefSecurity", briefSecurity);
			request.setAttribute("tenureAmount", tenureAmount);
			request.setAttribute("sms", sms);
			return mapping.getInputForward();		
		}
		else
		{
			String sms="";
			   
		 
		    
		    SecurityDepositVO vo = new SecurityDepositVO();
			org.apache.commons.beanutils.BeanUtils.copyProperties(vo, SecurityDepositDynaValidatorForm);
			vo.setDealId(dealId);
			vo.setUserId(userId);
			//vo.setTxnType("DC");
			vo.setBussinessDate(bDate);
			CreditProcessingDAO detail1=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
	        logger.info("Implementation class: "+detail1.getClass()); 		//changed by asesh
			//CreditProcessingDAO detail1 = new CreditProcessingDAOImpl();
			boolean status = detail1.saveSecurityDepositInDeal(vo);
			if(status)
			 {
				sms="S";
				//ArrayList<CompoundFrequencyVO> freqlist = detail.getCompFrequencyList();
				//request.setAttribute("freqlist",freqlist );			
				request.setAttribute("status", "Insertion is Completed Successfully!!!");
				
				RefreshFlagVo vo1 = new RefreshFlagVo();
				vo1.setTabIndex(7);
				if(dealId!=null && !dealId.trim().equalsIgnoreCase(""))
					vo1.setRecordId(Integer.parseInt(dealId.trim()));
	        		vo1.setModuleName("CP");
	    		RefreshFlagValueInsert.updateRefreshFlag(vo1);
	    		
	    		String repayQ="select DEAL_REPAYMENT_TYPE from cr_deal_loan_dtl where DEAL_ID="+dealId;
			    logger.info("Repayment Query: "+repayQ);
			    String repayType=ConnectionDAO.singleReturn(repayQ);
				logger.info("Repayment Type:"+repayType);
				
				//if(repayType!=null && repayType.equalsIgnoreCase("N"))
				//{
				    RefreshFlagVo vo2 = new RefreshFlagVo();
				    if(dealId!=null && !dealId.trim().equalsIgnoreCase(""))
				    	vo2.setRecordId(Integer.parseInt(dealId.trim()));
					vo2.setModuleName("CP");
					vo2.setTabIndex(7);
					vo2.setNonInstallment(repayType);
					RefreshFlagValueInsert.updateRefreshFlag(vo2);
				//}
				 
			 } 
			else
			{
				sms="E";
			}
		
			ArrayList tenureAmount=detail1.getTenureAmountInDeal(dealId);
			 logger.info("dealId::::::::::::: ");
			 String functionId=CommonFunction.checkNull(session.getAttribute("functionId")).trim();
			 ArrayList<Object> briefSecurity = detail1.getSecurityDetailAllInDeal(dealId,functionId);//deal Id as 1
			request.setAttribute("briefSecurity", briefSecurity);
			request.setAttribute("tenureAmount", tenureAmount);
			request.setAttribute("sms", sms);
			return mapping.getInputForward();		
		}
	    
	}
}