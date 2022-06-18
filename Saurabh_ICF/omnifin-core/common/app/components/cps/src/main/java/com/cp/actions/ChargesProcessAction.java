/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.cp.actions;
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
import com.cm.dao.CreditManagementDAO;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.CreditProcessingDAO;
import com.cp.vo.ChargeVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.tabDependencyCheck.RefreshFlagValueInsert;
import com.tabDependencyCheck.RefreshFlagVo;

/**
 * MyEclipse Struts Creation date: 05-05-2011
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class ChargesProcessAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(ChargesProcessAction.class.getName());
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
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		logger.info("In ChargesProcessAction(execute) ");

		HttpSession session = request.getSession();

		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String bDate="";
		
		if(userobj!=null)
		{		userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();				
		}else{
			logger.info("here in execute  method of ChargesProcessAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		DynaValidatorForm ChargesDynaValidatorForm = (DynaValidatorForm) form;
		ChargeVo vo = new ChargeVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,ChargesDynaValidatorForm);
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
		logger.info("In ChargesProcessAction dealId id: " + dealId);

		String loanId = "";

		if (session.getAttribute("loanId") != null) {

			loanId = session.getAttribute("loanId").toString();
		} else if (session.getAttribute("maxIdInCM") != null) {
			loanId = session.getAttribute("maxIdInCM").toString();
		}

		logger.info("In ChargesProcessAction loanId: " + loanId);


	
		vo.setUserId(userId);
		vo.setLoanId(loanId);
		vo.setDealId(dealId);
		vo.setBussinessDate(bDate);
			
		

		if (loanId != null && !loanId.equalsIgnoreCase("")) {
			//change by sachin
			CreditManagementDAO dao=(CreditManagementDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditManagementDAO.IDENTITY);
		     logger.info("Implementation class: "+dao.getClass());

			//end by sachin
			
//			CreditManagementDAO dao = new CreditManagementDAOImpl();
			boolean status = dao.saveChargesInCm(vo);
			String sms = "";
			if (status) {
				sms = "S";
				RefreshFlagVo vo1 = new RefreshFlagVo();
				if(session.getAttribute("dealId")!=null && !session.getAttribute("dealId").toString().trim().equalsIgnoreCase(""))
	    		{
					
					if(dealId!=null && !dealId.trim().equalsIgnoreCase(""))
					vo1.setRecordId(Integer.parseInt(dealId.trim()));
		    		vo1.setTabIndex(6);
		    		vo1.setModuleName("CP");
                    //code added by neeraj tripathi
		    		String repayTypeQuery="select DEAL_REPAYMENT_TYPE from cr_deal_loan_dtl where deal_id="+dealId;
		    		String repayType=ConnectionDAO.singleReturn(repayTypeQuery);
		    		vo1.setNonInstallment(repayType);
                    //tripathi's space end
	    		}
				else
				{
					if(loanId!=null && !loanId.trim().equalsIgnoreCase(""))
					vo1.setRecordId(Integer.parseInt(loanId.trim()));
		    		vo1.setTabIndex(3);
		    		vo1.setModuleName("CM");
                    //code added by neeraj tripathi
		    		String repayTypeQuery="select LOAN_REPAYMENT_TYPE from cr_loan_dtl where loan_id="+loanId;
		    		String repayType=ConnectionDAO.singleReturn(repayTypeQuery);
		    		vo1.setNonInstallment(repayType);
                    //tripathi's space end
				}
	    		RefreshFlagValueInsert.updateRefreshFlag(vo1);
			} else {
				sms = "E";
			}
			request.setAttribute("sms", sms);
			logger.info("status: " + status);
			return mapping.findForward("successInCM");
		} else
		{
			CreditProcessingDAO detail=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
	        logger.info("Implementation class: "+detail.getClass()); 			// changed by asesh
			//CreditProcessingDAO detail = new CreditProcessingDAOImpl();
			logger.info("DURING DEAL:::::::");
			//logger.info("Charge []Id :    "+vo.getChargeIdDtl());
			boolean status = detail.saveCharges(vo);
			String sms = "";
			if (status) {
				
				sms = "S";
				
				boolean status1 = detail.updatesecuritydeposit(vo,dealId);
				logger.info("status for table cr_del_sdm"+status1);
				
			} else {
				sms = "E";
			}
			request.setAttribute("sms", sms);
			logger.info("In ChargesProcessAction(execute) status: " + status);
			return mapping.findForward("success");
		}
		// ArrayList dealHeader = dao.getDealHeader(dealId);
		// request.setAttribute("dealHeader",dealHeader);

	}
}