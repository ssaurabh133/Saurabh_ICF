/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
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
import com.connect.CommonFunction;
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
public class ChargeBehindAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(ChargeBehindAction.class.getName());
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
	public ActionForward chargeInDeal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub

		logger.info("In ChargeBehindAction(chargeInDeal)");
		HttpSession session = request.getSession();

		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in chargeInDeal method of ChargeBehindAction action the session is out----------------");
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
		CreditProcessingDAO dao=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass()); 			// changed by asesh
		//CreditProcessingDAO dao = new CreditProcessingDAOImpl();

		String dealId = "";

		if (session.getAttribute("dealId") != null) {

			dealId = session.getAttribute("dealId").toString();
		} else if (session.getAttribute("maxId") != null) {
			dealId = session.getAttribute("maxId").toString();
		}

		logger.info("In ChargeBehindAction(chargeInDeal) dealId: "+dealId);
		if ((dealId != null && !dealId.equalsIgnoreCase(""))) 
		{
			String message=dao.callProcedure("DC", dealId);
			//neeraj kumar tripathi 
			
			if(message!=null && !CommonFunction.checkNull(message).equalsIgnoreCase("S"))
			{
				
				request.setAttribute("message",message);
			}
			logger.info("message  : "+message);	
			// Start By Prashant
				String roundTypeQuery="SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY = 'GEN_ROUND_TYPE'";
				logger.info("roundTypeQuery  : "+roundTypeQuery);	
				String roundType=ConnectionDAO.singleReturn(roundTypeQuery);
				logger.info("roundType  : "+roundType);	
				request.setAttribute("roundType", roundType);
				String getRoundParaQuery="SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY = 'GEN_ROUND_PARA'";
				logger.info("getRoundParaQuery  : "+getRoundParaQuery);	
				String getRoundPara=ConnectionDAO.singleReturn(getRoundParaQuery);
				logger.info("getRoundPara  : "+getRoundPara);
				request.setAttribute("roundPara", getRoundPara);
			// End By Prashant		
			ArrayList charges = dao.getchargesDetail("DC", dealId); //dealCapturing,deal
			String customerCharge=null;
			String customerFinalCharge=null;
			if(charges.size()>0){
				ChargeVo vo = (ChargeVo) charges.get(0);
				customerCharge = vo.getCustomerCharge();
				customerFinalCharge=vo.getCustomerFinalCharge();
				logger.info("In ChargeBehindAction(chargeInDeal) charges  "+ charges +"customerCharge:::::::"+customerCharge+"customerFinalCharge::::::"+customerFinalCharge);
			}
			request.setAttribute("customerCharge",customerCharge);
			request.setAttribute("customerFinalCharge",customerFinalCharge);
			request.setAttribute("charges",charges);
			request.setAttribute("source","N");			
			return mapping.findForward("success");
		}

		else {
			logger.info("In ChargeBehindAction(chargeInDeal) Back ");
			request.setAttribute("back", "B");
			return mapping.findForward("backToFirst");
		}
	}

	public ActionForward refreshCharge(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub

		logger.info("In ChargeBehindAction(refreshCharge) in Deal ");
		HttpSession session = request.getSession();
		//boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in refreshCharge method of ChargeBehindAction action the session is out----------------");
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
				context.setAttribute("msg", "B");
			return mapping.findForward("logout");
		}
		CreditProcessingDAO dao=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass()); 			// changed by asesh
		String dealId ="";
		if (session.getAttribute("dealId") != null) 
			dealId = session.getAttribute("dealId").toString();
		else if (session.getAttribute("maxId") != null) 
			dealId = session.getAttribute("maxId").toString();
		logger.info("In ChargeBehindAction refreshCharge dealId: " + dealId);
		if ((dealId != null && !dealId.equalsIgnoreCase(""))) 
		{
			String message=dao.callRefreshChargesDetailPro("DC", dealId);
			if(message!=null && !CommonFunction.checkNull(message).equalsIgnoreCase("S"))
				request.setAttribute("message",message);
			logger.info("message  : "+message);				
			ArrayList charges =dao.refreshchargesDetail("DC",dealId);//dealCapturing,deal					
			RefreshFlagVo vo1 = new RefreshFlagVo();
			vo1.setRecordId(Integer.parseInt(dealId));
	    	vo1.setTabIndex(6);
	    	vo1.setModuleName("CP");
    		logger.info("In ChargeBehindAction refreshCharge charges  "+ charges);
			String customerCharge=null;
			String customerFinalCharge=null;
			if(charges.size()>0){
				ChargeVo vo = (ChargeVo) charges.get(0);
				customerCharge = vo.getCustomerCharge();
				customerFinalCharge=vo.getCustomerFinalCharge();
				logger.info("In ChargeBehindAction(refreshCharge) charges  "+ charges +"customerCharge:::::::"+customerCharge+"customerFinalCharge::::::"+customerFinalCharge);
			}
			request.setAttribute("customerCharge",customerCharge);
			request.setAttribute("customerFinalCharge",customerFinalCharge);
			request.setAttribute("charges", charges);			
			String repayQ="select DEAL_REPAYMENT_TYPE from cr_deal_loan_dtl where DEAL_ID="+dealId;
		    logger.info("Repayment Query: "+repayQ);
		    String repayType=ConnectionDAO.singleReturn(repayQ);
			logger.info("Repayment Type:"+repayType);			
			//if(repayType!=null && repayType.equalsIgnoreCase("N"))
			//{
			    RefreshFlagVo vo = new RefreshFlagVo();
				vo.setRecordId(Integer.parseInt(dealId));
				vo.setModuleName("CP");
				vo.setTabIndex(6);
				vo.setNonInstallment(repayType);
				RefreshFlagValueInsert.updateRefreshFlag(vo);
			//}	
			// Start By Prashant
			String roundTypeQuery="SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY = 'GEN_ROUND_TYPE'";
			logger.info("roundTypeQuery  : "+roundTypeQuery);	
			String roundType=ConnectionDAO.singleReturn(roundTypeQuery);
			logger.info("roundType  : "+roundType);	
			request.setAttribute("roundType", roundType);
			String getRoundParaQuery="SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY = 'GEN_ROUND_PARA'";
			logger.info("getRoundParaQuery  : "+getRoundParaQuery);	
			String getRoundPara=ConnectionDAO.singleReturn(getRoundParaQuery);
			logger.info("getRoundPara  : "+getRoundPara);
			request.setAttribute("roundPara", getRoundPara);
			request.setAttribute("source", "Y");
			
		// End By Prashant	
			return mapping.findForward("success");
		}
		else 
		{
			logger.info("In ChargeBehindAction refreshCharge Back ");
			request.setAttribute("back","B");
			return mapping.findForward("backToFirst");
		}
	}
	
//Start by Anil	
	public ActionForward chargeForEmiCalc(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{

	logger.info("In chargeForEmiCalc");
	HttpSession session = request.getSession();

	boolean flag=false;
	UserObject userobj=(UserObject)session.getAttribute("userobject");
	if(userobj==null){
		logger.info("here in chargeForEmiCalc method of ChargeBehindAction action the session is out----------------");
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
	//CreditProcessingDAO dao = new CreditProcessingDAOImpl();
    CreditProcessingDAO dao=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
    logger.info("Implementation class: "+dao.getClass()); 			// changed by anil

	String dealId = "";

	if (session.getAttribute("dealId") != null) {

		dealId = session.getAttribute("dealId").toString();
	} 
	logger.info("In ChargeBehindAction(chargeForEmiCalc) dealId: "+dealId);
	if ((dealId != null && !dealId.equalsIgnoreCase(""))) 
	{
		//String message=dao.callProcedure("DC", dealId);
		String message=dao.callProcForEmiCalc("DC", dealId);
		if(message!=null && !CommonFunction.checkNull(message).equalsIgnoreCase("S"))
		{
			
			request.setAttribute("message",message);
		}
			String roundTypeQuery="SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY = 'GEN_ROUND_TYPE'";
			logger.info("roundTypeQuery  : "+roundTypeQuery);	
			String roundType=ConnectionDAO.singleReturn(roundTypeQuery);
			logger.info("roundType  : "+roundType);	
			request.setAttribute("roundType", roundType);
			String getRoundParaQuery="SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY = 'GEN_ROUND_PARA'";
			logger.info("getRoundParaQuery  : "+getRoundParaQuery);	
			String getRoundPara=ConnectionDAO.singleReturn(getRoundParaQuery);
			logger.info("getRoundPara  : "+getRoundPara);
			request.setAttribute("roundPara", getRoundPara);
		ArrayList charges = dao.getchargesDtlForEmiCalc("DC", dealId); //dealCapturing,deal
		logger.info("In ChargeBehindAction(chargeInDeal) charges  "+ charges);
		request.setAttribute("charges",charges);
		return mapping.findForward("successForEmiCalc");
		
	}

	else {
		logger.info("In ChargeBehindAction(chargeForEmiCalc) Back ");
		request.setAttribute("back", "B");
		return mapping.findForward("backToLoanDetailForEmiCalc");
	}

}
	
	
	public ActionForward refreshChargeForEmiCalc(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

	logger.info("In ChargeBehindAction(refreshchargeForEmiCalc) in Deal ");
	HttpSession session = request.getSession();
	//boolean flag=false;
	UserObject userobj=(UserObject)session.getAttribute("userobject");
	if(userobj==null){
		logger.info("here in refreshchargeForEmiCalc method of ChargeBehindAction action the session is out----------------");
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
			context.setAttribute("msg", "B");
		return mapping.findForward("logout");
	}
	//CreditProcessingDAO dao = new CreditProcessingDAOImpl();
    CreditProcessingDAO dao=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
    logger.info("Implementation class: "+dao.getClass()); 			// changed by anil
	String dealId ="";
	if (session.getAttribute("dealId") != null) 
		dealId = session.getAttribute("dealId").toString();

	logger.info("In ChargeBehindAction refreshchargeForEmiCalc dealId: " + dealId);
	if ((dealId != null && !dealId.equalsIgnoreCase(""))) 
	{
		String message=dao.callRefreshChargesDetailProForEmiCalc("DC", dealId);
		if(message!=null && !CommonFunction.checkNull(message).equalsIgnoreCase("S"))
		request.setAttribute("message",message);
		logger.info("message  : "+message);				
		ArrayList charges =dao.refreshchargesDetailForEmiCalc("DC",dealId);		
		logger.info("In ChargeBehindAction refreshchargeForEmiCalc charges  "+ charges);
		request.setAttribute("charges", charges);			
		String repayQ="select DEAL_REPAYMENT_TYPE from cr_deal_loan_dtl where DEAL_ID="+dealId;
	    logger.info("Repayment Query: "+repayQ);
	    String repayType=ConnectionDAO.singleReturn(repayQ);
		logger.info("Repayment Type:"+repayType);			
		String roundTypeQuery="SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY = 'GEN_ROUND_TYPE'";
		logger.info("roundTypeQuery  : "+roundTypeQuery);	
		String roundType=ConnectionDAO.singleReturn(roundTypeQuery);
		logger.info("roundType  : "+roundType);	
		request.setAttribute("roundType", roundType);
		String getRoundParaQuery="SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY = 'GEN_ROUND_PARA'";
		logger.info("getRoundParaQuery  : "+getRoundParaQuery);	
		String getRoundPara=ConnectionDAO.singleReturn(getRoundParaQuery);
		logger.info("getRoundPara  : "+getRoundPara);
		request.setAttribute("roundPara", getRoundPara);
		return mapping.findForward("successForEmiCalc");
	}
	else 
	{
		logger.info("In ChargeBehindAction refreshchargeForEmiCalc Back ");
		request.setAttribute("back","B");
		return mapping.findForward("backToLoanDetailForEmiCalc");
	}
}

	//End by Anil
	
}