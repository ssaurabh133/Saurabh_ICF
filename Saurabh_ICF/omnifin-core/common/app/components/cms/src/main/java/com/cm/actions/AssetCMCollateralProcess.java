
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
import com.cm.dao.CreditManagementDAO;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

/** 
 * Creation date: 05-19-2011
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class AssetCMCollateralProcess extends Action {
	private static final Logger logger = Logger.getLogger(AssetCMCollateralProcess.class.getName());
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
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in execute method of  AssetCMCollateralProcess action the session is out----------------");
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
		String source="NE";
		String functionId=(String)session.getAttribute("functionId");
		int funid=Integer.parseInt(functionId);		
		if(funid==4000122 || funid==4000123)
			source="ED";
		//neeraj space end
		
		String loanId = "";

		if (session.getAttribute("loanId") != null) {

			loanId = session.getAttribute("loanId").toString();
		} else if (session.getAttribute("maxId") != null) {
			loanId = session.getAttribute("maxId").toString();
		}

		logger.info("In SecurityDepositAction loan id: " + loanId);
//		LoanInitiationDAO dao = new LoanInitiationDAOImpl();
		String[] assetId = request.getParameterValues("chk");
		//change by sachin
		CreditManagementDAO dao=(CreditManagementDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditManagementDAO.IDENTITY);
	     logger.info("Implementation class: "+dao.getClass());

		//end by sachin
//		CreditManagementDAO dao = new CreditManagementDAOImpl();
		boolean status = dao.deleteAsset(assetId,loanId);
		String sms1="";
		if(status)
		{
			//String loanId="2";
			ArrayList asset = dao.selectAsset(loanId,source);
			sms1="S";
			request.setAttribute("asset", asset);
		}
		else
		{
			sms1="E";
		}
		request.setAttribute("sms1", sms1);
		return mapping.getInputForward();
	}
}