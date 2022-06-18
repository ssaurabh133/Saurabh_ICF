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

import com.cm.dao.assetInsuranceDAO;
import com.cm.vo.AssetForCMVO;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.CreditProcessingDAO;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class AssetMakerForNewAction extends Action{
	private static final Logger logger = Logger.getLogger(AssetMakerForNewAction.class.getName());		
		public ActionForward execute(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)throws Exception {
			logger.info(" in assetMakerForNewAction----------");
			
			HttpSession session =  request.getSession();
			session.removeAttribute("assetInsuranceId");
			session.removeAttribute("datatoapproveList");
			session.removeAttribute("insuranceDoneByList");
			session.removeAttribute("showInsuranceRelWithNominee");
			session.removeAttribute("loanNo");
			session.removeAttribute("cusName");
			session.removeAttribute("loanId");
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here in execute  method of AssetMakerForNewAction action the session is out----------------");
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
			AssetForCMVO vo = new AssetForCMVO();
//			CreditManagementDAO dao = new CreditManagementDAOImpl();
			assetInsuranceDAO dao=(assetInsuranceDAO)DaoImplInstanceFactory.getDaoImplInstance(assetInsuranceDAO.IDENTITY);
	        logger.info("Implementation class: "+dao.getClass());
			ArrayList<Object> insuranceDoneByList=dao.getInsuranceDoneByList();
			request.setAttribute("insuranceDoneByList",insuranceDoneByList);
			CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
			ArrayList showInsuranceRelWithNominee = service.showInsuranceRelWithNominee();
			request.setAttribute("showInsuranceRelWithNominee",showInsuranceRelWithNominee);
			return mapping.findForward("new");
			}
		}
