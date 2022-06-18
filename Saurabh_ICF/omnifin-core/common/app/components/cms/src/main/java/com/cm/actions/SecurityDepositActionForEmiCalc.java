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
import com.cm.vo.SecurityDepositVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.cp.dao.CreditProcessingDAO;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.tabDependencyCheck.RefreshFlagVo;
import com.connect.DaoImplInstanceFactory;

/** 
 * MyEclipse Struts
 * Creation date: 04-25-2011
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class SecurityDepositActionForEmiCalc extends Action {
	private static final Logger logger = Logger.getLogger(SecurityDepositActionForEmiCalc.class.getName());
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
				logger.info("here in execute method of SecurityDepositActionForEmiCalc action the session is out----------------");
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
		} 
	
			String sms="";
			SecurityDepositVO vo = new SecurityDepositVO();
			org.apache.commons.beanutils.BeanUtils.copyProperties(vo, SecurityDepositDynaValidatorForm);
			vo.setDealId(dealId);
			vo.setUserId(userId);
			vo.setBussinessDate(bDate);
			//CreditProcessingDAO detail1 = new CreditProcessingDAOImpl();
			CreditProcessingDAO detail1=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
            logger.info("Implementation class: "+detail1.getClass()); 		//changed by anil
			boolean status = detail1.saveSecurityDepositInDealForEmiCalc(vo);
			if(status)
			 {
				sms="S";
				request.setAttribute("status", "Insertion is Completed Successfully!!!");
				
				RefreshFlagVo vo1 = new RefreshFlagVo();
				vo1.setTabIndex(7);
				    		
	    		String repayQ="select DEAL_REPAYMENT_TYPE from cr_deal_loan_dtl where DEAL_ID="+dealId;
			    logger.info("Repayment Query: "+repayQ);
			    String repayType=ConnectionDAO.singleReturn(repayQ);
				logger.info("Repayment Type:"+repayType);
				 
			 } 
			else
			{
				sms="E";
			}
		
			ArrayList tenureAmount=detail1.getTenureAmountInDealForEmiCalc(dealId);
			logger.info("dealId::::::::::::: ");
			String functionId=CommonFunction.checkNull(session.getAttribute("functionId")).trim();
			ArrayList<Object> briefSecurity = detail1.getSecurityDetailAllInDeal(dealId,functionId);//deal Id as 1
			request.setAttribute("briefSecurity", briefSecurity);
			request.setAttribute("tenureAmount", tenureAmount);
			request.setAttribute("sms", sms);
			return mapping.getInputForward();		
		
	    
	}
}