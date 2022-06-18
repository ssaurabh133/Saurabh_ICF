package com.scz.actions;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.scz.dao.PoolIDDAO;
import com.scz.daoImplMYSQL.PoolIDDAOImpl;
import com.connect.DaoImplInstanceFactory;
import com.login.roleManager.UserObject;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
public class Tab1BehindAction extends DispatchAction {
	private static final Logger logger = Logger
			.getLogger(PoolIdMakerProcessAction.class.getName());

	public ActionForward downloadUploadedData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		
				logger.info(" In the newPoolIdMaker-");
				HttpSession session = request.getSession();
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				if(userobj==null){
					logger.info(" in newPoolIdMaker method of PoolIdMakerProcessAction action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
				//session.removeAttribute("authorPoolIdSavedList");
				session.removeAttribute("List");
				session.removeAttribute("poolID");
				request.setAttribute("poolIdMaker", "poolIdMaker");
				request.setAttribute("forNew","");
			
				// PoolIDDAO dao=(PoolIDDAO)DaoImplInstanceFactory.getDaoImplInstance(PoolIDDAO.IDENTITY);
				PoolIDDAO dao= new PoolIDDAOImpl();
				logger.info("Implementation class: "+dao.getClass());
				String temp = String.valueOf(session.getAttribute("temp"));
				int poolNo=0;
				if(temp==null){
					poolNo = Integer.parseInt(dao.getPoolNo());
				}else{
					poolNo=Integer.parseInt(temp.trim());
				}
				request.setAttribute("poolID", poolNo);
				
				return mapping.findForward("download");
		}
	
	public ActionForward newPoolIdMaker(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		
				logger.info(" In the newPoolIdMaker-");
				HttpSession session = request.getSession();
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				if(userobj==null){
					logger.info(" in newPoolIdMaker method of PoolIdMakerProcessAction action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
				session.removeAttribute("authorPoolIdSavedList");
				session.removeAttribute("List");
				session.removeAttribute("poolID");
				request.setAttribute("poolIdMaker", "poolIdMaker");
				request.setAttribute("forNew","");
			
				// PoolIDDAO dao=(PoolIDDAO)DaoImplInstanceFactory.getDaoImplInstance(PoolIDDAO.IDENTITY);
				PoolIDDAO dao= new PoolIDDAOImpl();
				logger.info("Implementation class: "+dao.getClass());
				String temp = String.valueOf(session.getAttribute("temp"));
				int poolNo=0;
				if(temp==null){
					poolNo = Integer.parseInt(dao.getPoolNo());
				}else{
					poolNo=Integer.parseInt(temp.trim());
				}
				request.setAttribute("poolNo", poolNo);
				String investmentRatio = "";
			    String investmentQuery = "select sum(INVESTMENT_RATIO) from TAB2_DATA where pool_ID='" + poolNo + "'";
			    investmentRatio = CommonFunction.checkNull(ConnectionDAO.singleReturn(investmentQuery));
			    logger.info(" investmentQuery :: " + investmentRatio);
			    request.setAttribute("investmentRatio", investmentRatio);
			    logger.info("investmentRatio::::" + investmentRatio);
				
				
				return mapping.findForward("poolIdMakerOpen");
		}
	
	public static ActionForward tab1OpenAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" In the newPoolIdMaker-");
		HttpSession session1 = request.getSession();
		UserObject userobj = (UserObject) session1.getAttribute("userobject");
		if (userobj == null) {
			logger.info(" in newPoolIdMaker method of PoolIdMakerProcessAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		
		// PoolIDDAO dao = (PoolIDDAO) DaoImplInstanceFactory.getDaoImplInstance(PoolIDDAO.IDENTITY);
		PoolIDDAO dao= new PoolIDDAOImpl();
		logger.info("Implementation class: " + dao.getClass());
		String temp = String.valueOf(session1.getAttribute("temp"));
		int poolNo=0;
		if(temp==null){
			poolNo = Integer.parseInt(dao.getPoolNo());
		}else{
			poolNo=Integer.parseInt(temp.trim());
		}
		
		ArrayList mainList = Tab1ProcessAction.getGrid(poolNo);
		  request.setAttribute("poolNo", poolNo);
		  request.setAttribute("gridList", mainList);	  
		return mapping.findForward("tab1");
	}
	
	public static ActionForward tab2OpenAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		logger.info(" In the newPoolIdMaker-");
		HttpSession session1 = request.getSession();
		UserObject userobj = (UserObject) session1.getAttribute("userobject");
		if (userobj == null) {
			logger.info(" in newPoolIdMaker method of PoolIdMakerProcessAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}

		// PoolIDDAO dao = (PoolIDDAO) DaoImplInstanceFactory.getDaoImplInstance(PoolIDDAO.IDENTITY);
		PoolIDDAO dao= new PoolIDDAOImpl();
		logger.info("Implementation class: " + dao.getClass());
		String temp = String.valueOf(session1.getAttribute("temp"));
		int poolNo=0;
		if(temp==null){
			poolNo = Integer.parseInt(dao.getPoolNo());
		}else{
			poolNo=Integer.parseInt(temp.trim());
		}

		ArrayList mainList = Tab2ProcessAction.getGrid(poolNo);
		  request.setAttribute("poolNo", poolNo);
		  request.setAttribute("gridList", mainList);
			//seema
		  String investmentRatio = "";
		    String investmentQuery = "select sum(INVESTMENT_RATIO) from TAB2_DATA where pool_ID='" + poolNo + "'";
		    investmentRatio = CommonFunction.checkNull(ConnectionDAO.singleReturn(investmentQuery));
		    logger.info(" investmentQuery :: " + investmentRatio);
		    request.setAttribute("investmentRatio", investmentRatio);
		    logger.info("investmentRatio::::" + investmentRatio);
		return mapping.findForward("tab2");
	}
	public static ActionForward poolIdAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		logger.info(" In the poolIdAuthor-");
		HttpSession session1 = request.getSession();
		UserObject userobj = (UserObject) session1.getAttribute("userobject");
		if (userobj == null) {
			logger.info(" in poolIdAuthor method of PoolIdMakerProcessAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}

		
		String temp = String.valueOf(session1.getAttribute("temp"));
		request.setAttribute("poolID", temp);
		logger.info("poolId " + temp);
		
		  
		return mapping.findForward("poolIdAuthor");
	}
	
    }