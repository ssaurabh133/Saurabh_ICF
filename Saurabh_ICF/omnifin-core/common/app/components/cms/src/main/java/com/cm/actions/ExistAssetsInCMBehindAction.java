
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
import com.cm.dao.LoanInitiationDAO;
import com.connect.DaoImplInstanceFactory;
import com.cp.vo.CollateralVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

	public class ExistAssetsInCMBehindAction extends DispatchAction {
		private static final Logger logger = Logger.getLogger(ExistAssetsInCMBehindAction.class.getName());
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
		public ActionForward openExistingAsset(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			logger.info("Inside ExistAssetsBehindAction(openExistingAsset)");
			HttpSession session = request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here in openExistingAsset method of ExistAssetsInCMBehindAction action the session is out----------------");
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
			CollateralVo collateralVo = new CollateralVo(); 
			DynaValidatorForm ExistingAssetCollateralDynaValidatorForm = (DynaValidatorForm) form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(collateralVo,
					ExistingAssetCollateralDynaValidatorForm);
			LoanInitiationDAO dao=(LoanInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(LoanInitiationDAO.IDENTITY);
			logger.info("Implementation class: "+dao.getClass()); 
			String loanId = request.getParameter("loanId");
		
			ArrayList<CollateralVo> existassetList =dao.getcolletralDetail(collateralVo,loanId) ;
			request.setAttribute("existassetList", existassetList);
			logger.info("searchListGrid    Size:---"+existassetList.size());
			return mapping.findForward("success");
		}
	}
