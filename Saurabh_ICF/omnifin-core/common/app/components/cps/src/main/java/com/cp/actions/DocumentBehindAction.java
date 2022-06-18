/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.cp.actions;

import java.io.PrintWriter;
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
import com.connect.ConnectionDAO;

import java.util.*;

import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

/**
 * MyEclipse Struts Creation date: 04-27-2011
 * 
 * XDoclet definition:
 * 
 * @struts.action parameter="method" validate="true"
 */
public class DocumentBehindAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(DocumentBehindAction.class.getName());
	CreditProcessingDAO dao=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
	//CreditProcessingDAO dao = new CreditProcessingDAOImpl();

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
	public ActionForward applicationDocs(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		logger.info("In DocumentBehindAction(applicationDocs)");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in applicationDocs method of DocumentBehindAction action the session is out----------------");
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
		String loanFlag="";
		if(session.getAttribute("loanFlag")!=null){
			 loanFlag=session.getAttribute("loanFlag").toString();
		}
		String source="NE";
		String functionId=(String)session.getAttribute("functionId");
		int funid=Integer.parseInt(functionId);		
		if(funid==4000122 || funid==4000123)
		{
			source="ED";
			session.removeAttribute("dealId");
		}
		if(funid==4000106 || funid==4000122 || funid==4000111)
		{
			if(!CommonFunction.checkNull(loanFlag).equalsIgnoreCase("UWA")){
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
			}else{
				session.setAttribute("underWriterViewData","underWriterViewData");
				session.setAttribute("viewDeal","viewDeal");
			}
	
		}
		//neeraj space end
		String commonId = "";
		String stage = "";
		String txnType = "";
		if (session.getAttribute("loanId") != null) {
			stage = "PRD";
			txnType = "LIM";
			commonId = session.getAttribute("loanId").toString();
		} else if (session.getAttribute("maxIdInCM") != null) {
			stage = "PRD";
			txnType = "LIM";
			commonId = session.getAttribute("maxIdInCM").toString();
		}

		if (session.getAttribute("dealId") != null) {
			stage = "PRS";
			txnType = "DC";
			commonId = session.getAttribute("dealId").toString();
		} else if (session.getAttribute("maxId") != null) {
			stage = "PRS";
			txnType = "DC";
			commonId = session.getAttribute("maxId").toString();
		}

		logger.info("In DocumentBehindAction(applicationDocs) loan/deal id: "+ commonId + ", stage: " + stage + ",Txn Type: "+ txnType);

		if (session.getAttribute("cmAuthor") != null
				&& !session.getAttribute("cmAuthor").equals("")) {
			request.setAttribute("viewDeal", "cmAuthor");
		}
		String entityType = request.getParameter("entityType");
		logger.info("In DocumentBehindAction(applicationDocs) entityType: "+ entityType);
		logger.info("Implementation class: "+dao.getClass()); 			// changed by asesh
		ArrayList documents = dao.getApplicationDocuments(entityType, commonId,stage,txnType,source);
		logger.info("documents Size: " + documents.size());
		request.setAttribute("calDoc", documents.size());
		if(documents.size()>0){
			request.setAttribute("dataFound","dataFound");
		}
		request.setAttribute("documents", documents);
		
		//Changes By Amit Starts
		logger.info("Implementation class: "+dao.getClass()); 			// changed by asesh
		ArrayList additionalDocuments = dao.getAdditionalDocs(entityType, commonId,stage, txnType,source);
		request.setAttribute("psize",additionalDocuments.size());
		if(additionalDocuments.size()>0)
		{
			request.setAttribute("additionalDocuments",additionalDocuments);
		}
		//Changes By Amit Ends
		
		String procval="";
		if(session.getAttribute("procvalue")!=null && !CommonFunction.checkNull(session.getAttribute("procvalue")).equalsIgnoreCase(""))
		{
			procval=CommonFunction.checkNull(session.getAttribute("procvalue").toString());
		}
		
		  if(!(procval.equalsIgnoreCase("S")))
			{
			  request.setAttribute("procval", procval);
			}
		  session.removeAttribute("InternalDocs");
			String int_extQuery="select parameter_value from parameter_mst where parameter_key='INTERNAL_DOCUMENT'";
			logger.info("Query    :-   "+int_extQuery);
			String int_ext= ConnectionDAO.singleReturn(int_extQuery);
			session.setAttribute("InternalDocs", int_ext);			
			session.removeAttribute("procvalue");
		return mapping.findForward("applicationDocs");
	}

	public ActionForward applicantDocs(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		logger.info("In DocumentBehindAction(applicantDocs)");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in applicantDocs method of DocumentBehindAction action the session is out----------------");
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
		String commonId = "";
		String stage = "";
		String txnType = "";
		if (session.getAttribute("loanId") != null) {
			stage = "PRD";
			txnType = "LIM";
			commonId = session.getAttribute("loanId").toString();
		} else if (session.getAttribute("maxIdInCM") != null) {
			stage = "PRD";
			txnType = "LIM";
			commonId = session.getAttribute("maxIdInCM").toString();
		}

		if (session.getAttribute("dealId") != null) {
			stage = "PRS";
			txnType = "DC";
			commonId = session.getAttribute("dealId").toString();
		} else if (session.getAttribute("maxId") != null) {
			stage = "PRS";
			txnType = "DC";
			commonId = session.getAttribute("maxId").toString();
		}

		logger.info("In DocumentBehindAction(applicantDocs) loan/deal id: "
				+ commonId + ", stage: " + stage + ",Txn Type: " + txnType);

		if (session.getAttribute("cmAuthor") != null
				&& !session.getAttribute("cmAuthor").equals("")) {
			request.setAttribute("viewDeal", "cmAuthor");
		}
		String entityType = request.getParameter("entityType");
		logger.info("In DocumentBehindAction(applicantDocs) entityType: "
				+ entityType);
		logger.info("Implementation class: "+dao.getClass()); 			// changed by asesh
		ArrayList documents = dao.getAllApplicantDocs(entityType, commonId,stage, txnType,source);
		logger.info("documents Size: " + documents.size());
		request.setAttribute("calDoc", documents.size());
		if(documents.size()>0){
			request.setAttribute("dataFound","dataFound");
		}
		request.setAttribute("documents", documents);

		//Changes By Amit Starts
		logger.info("Implementation class: "+dao.getClass()); 			// changed by asesh
		ArrayList additionalDocuments = dao.getAdditionalDocs(entityType, commonId,stage, txnType,source);
		request.setAttribute("psize",additionalDocuments.size());
		if(additionalDocuments.size()>0)
		{
			request.setAttribute("additionalDocuments",additionalDocuments);
		}
		//Changes By Amit Ends
		
		return mapping.findForward("applicantDocs");
	}

	public ActionForward coApplicantDocs(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		logger.info("In DocumentBehindAction(coApplicantDocs)");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in coApplicantDocs method of DocumentBehindAction action the session is out----------------");
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
		String commonId = "";
		String stage = "";
		String txnType = "";
		if (session.getAttribute("loanId") != null) {
			stage = "PRD";
			txnType = "LIM";
			commonId = session.getAttribute("loanId").toString();
		} else if (session.getAttribute("maxIdInCM") != null) {
			stage = "PRD";
			txnType = "LIM";
			commonId = session.getAttribute("maxIdInCM").toString();
		}

		if (session.getAttribute("dealId") != null) {
			stage = "PRS";
			txnType = "DC";
			commonId = session.getAttribute("dealId").toString();
		} else if (session.getAttribute("maxId") != null) {
			stage = "PRS";
			txnType = "DC";
			commonId = session.getAttribute("maxId").toString();
		}

		logger
				.info("In DocumentBehindAction(coApplicantDocs) loan/deal id: "
						+ commonId + ", stage: " + stage + ",Txn Type: "
						+ txnType);

		if (session.getAttribute("cmAuthor") != null
				&& !session.getAttribute("cmAuthor").equals("")) {
			request.setAttribute("viewDeal", "cmAuthor");
		}
		String entityType = request.getParameter("entityType");
		logger.info("In DocumentBehindAction(coApplicantDocs) entityType: "
				+ entityType);
		logger.info("Implementation class: "+dao.getClass()); 			// changed by asesh
		ArrayList documents = dao.getAllApplicantDocs(entityType, commonId,stage,txnType,source);
		logger.info("documents Size: " + documents.size());
		request.setAttribute("calDoc", documents.size());
		if(documents.size()>0){
			request.setAttribute("dataFound","dataFound");
		}
		request.setAttribute("documents", documents);

		
		//Changes By Amit Starts
		logger.info("Implementation class: "+dao.getClass()); 			// changed by asesh
		ArrayList additionalDocuments = dao.getAdditionalDocs(entityType, commonId,stage, txnType,source);
		request.setAttribute("psize",additionalDocuments.size());
		if(additionalDocuments.size()>0)
		{
			request.setAttribute("additionalDocuments",additionalDocuments);
		}
		//Changes By Amit Ends
		
		return mapping.findForward("coApplicantDocs");
	}

	public ActionForward guarantorDocs(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		logger.info("In DocumentBehindAction(guarantorDocs)");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in guarantorDocs method of DocumentBehindAction action the session is out----------------");
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
		String commonId = "";
		String stage = "";
		String txnType = "";
		if (session.getAttribute("loanId") != null) {
			stage = "PRD";
			txnType = "LIM";
			commonId = session.getAttribute("loanId").toString();
		} else if (session.getAttribute("maxIdInCM") != null) {
			stage = "PRD";
			txnType = "LIM";
			commonId = session.getAttribute("maxIdInCM").toString();
		}

		if (session.getAttribute("dealId") != null) {
			stage = "PRS";
			txnType = "DC";
			commonId = session.getAttribute("dealId").toString();
		} else if (session.getAttribute("maxId") != null) {
			stage = "PRS";
			txnType = "DC";
			commonId = session.getAttribute("maxId").toString();
		}

		logger.info("In DocumentBehindAction(guarantorDocs) loan/deal id: "
				+ commonId + ", stage: " + stage + ",Txn Type: " + txnType);

		if (session.getAttribute("cmAuthor") != null
				&& !session.getAttribute("cmAuthor").equals("")) {
			request.setAttribute("viewDeal", "cmAuthor");
		}
		String entityType = request.getParameter("entityType");
		logger.info("In DocumentBehindAction(guarantorDocs) entityType: "
				+ entityType);
		logger.info("Implementation class: "+dao.getClass()); 			// changed by asesh
		ArrayList documents = dao.getAllApplicantDocs(entityType, commonId,stage,txnType,source);
		logger.info("documents Size: " + documents.size());
		request.setAttribute("calDoc", documents.size());
		if(documents.size()>0){
			request.setAttribute("dataFound","dataFound");
		}
		request.setAttribute("documents", documents);
		
		//Changes By Amit Starts
		logger.info("Implementation class: "+dao.getClass()); 			// changed by asesh
		ArrayList additionalDocuments = dao.getAdditionalDocs(entityType, commonId,stage, txnType,source);
		request.setAttribute("psize",additionalDocuments.size());
		if(additionalDocuments.size()>0)
		{
			request.setAttribute("additionalDocuments",additionalDocuments);
		}
		//Changes By Amit Ends
		
		return mapping.findForward("guarantorDocs");
	}

	public ActionForward collateralDocs(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		logger.info("In DocumentBehindAction(collateralDocs)");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in collateralDocs method of DocumentBehindAction action the session is out----------------");
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
		String commonId = "";
		String stage = "";
		String txnType = "";
		if (session.getAttribute("loanId") != null) {
			stage = "PRD";
			txnType = "LIM";
			commonId = session.getAttribute("loanId").toString();
		} else if (session.getAttribute("maxIdInCM") != null) {
			stage = "PRD";
			txnType = "LIM";
			commonId = session.getAttribute("maxIdInCM").toString();
		}

		if (session.getAttribute("dealId") != null) {
			stage = "PRS";
			txnType = "DC";
			commonId = session.getAttribute("dealId").toString();
		} else if (session.getAttribute("maxId") != null) {
			stage = "PRS";
			txnType = "DC";
			commonId = session.getAttribute("maxId").toString();
		}

		logger.info("In DocumentBehindAction(collateralDocs) loan/deal id: "
				+ commonId + ", stage: " + stage + ",Txn Type: " + txnType);

		if (session.getAttribute("cmAuthor") != null
				&& !session.getAttribute("cmAuthor").equals("")) {
			request.setAttribute("viewDeal", "cmAuthor");
		}
		String entityType = request.getParameter("entityType");
		logger.info("In DocumentBehindAction(collateralDocs) entityType: "
				+ entityType);
		logger.info("Implementation class: "+dao.getClass()); 			// changed by asesh
		ArrayList documents = dao.getAssetCollateralDocuments(entityType,commonId, stage, txnType,source);
		logger
				.info("In DocumentBehindAction(collateralDocs) documents Size: "
						+ documents.size());
		request.setAttribute("calDoc", documents.size());
		if(documents.size()>0){
			request.setAttribute("dataFound","dataFound");
		}
		request.setAttribute("documents", documents);

		//Changes By Amit Starts
		logger.info("Implementation class: "+dao.getClass()); 			// changed by asesh
		ArrayList additionalDocuments = dao.getAdditionalDocs(entityType, commonId,stage, txnType,source);
		request.setAttribute("psize",additionalDocuments.size());
		if(additionalDocuments.size()>0)
		{
			request.setAttribute("additionalDocuments",additionalDocuments);
		}
		//Changes By Amit Ends
		
		return mapping.findForward("collateralDocs");
	}

	public ActionForward assetDocs(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		logger.info("In DocumentBehindAction(assetDocs)");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in assetDocs method of DocumentBehindAction action the session is out----------------");
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
		String commonId = "";
		String stage = "";
		String txnType = "";
		if (session.getAttribute("loanId") != null) {
			stage = "PRD";
			txnType = "LIM";
			commonId = session.getAttribute("loanId").toString();
		} else if (session.getAttribute("maxIdInCM") != null) {
			stage = "PRD";
			txnType = "LIM";
			commonId = session.getAttribute("maxIdInCM").toString();
		}

		if (session.getAttribute("dealId") != null) {
			stage = "PRS";
			txnType = "DC";
			commonId = session.getAttribute("dealId").toString();
		} else if (session.getAttribute("maxId") != null) {
			stage = "PRS";
			txnType = "DC";
			commonId = session.getAttribute("maxId").toString();
		}

		logger.info("In DocumentBehindAction(assetDocs) loan/deal id: "
				+ commonId + ", stage: " + stage + ",Txn Type: " + txnType);

		if (session.getAttribute("cmAuthor") != null
				&& !session.getAttribute("cmAuthor").equals("")) {
			request.setAttribute("viewDeal", "cmAuthor");
		}
		String entityType = request.getParameter("entityType");
		logger.info("In DocumentBehindAction(assetDocs) entityType: "
				+ entityType);
		logger.info("Implementation class: "+dao.getClass()); 			// changed by asesh
		ArrayList documents = dao.getAssetCollateralDocuments(entityType,commonId, stage, txnType,source);
		logger.info("In DocumentBehindAction(assetDocs) documents Size: "
				+ documents.size());
		request.setAttribute("calDoc", documents.size());
		if(documents.size()>0){
			request.setAttribute("dataFound","dataFound");
		}
		request.setAttribute("documents", documents);

		//Changes By Amit Starts
		logger.info("Implementation class: "+dao.getClass()); 			// changed by asesh
		ArrayList additionalDocuments = dao.getAdditionalDocs(entityType, commonId,stage, txnType,source);
		request.setAttribute("psize",additionalDocuments.size());
		if(additionalDocuments.size()>0)
		{
			request.setAttribute("additionalDocuments",additionalDocuments);
		}
		//Changes By Amit Ends
		
		return mapping.findForward("assetDocs");
	}
	public ActionForward PODDocs(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		logger.info("In DocumentBehindAction(PODDocs)");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in assetDocs method of DocumentBehindAction action the session is out----------------");
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
		String commonId = "";
		String stage = "";
		String txnType = "";
		if (session.getAttribute("loanId") != null) {
			stage = "POD";
			txnType = "LIM";
			commonId = session.getAttribute("loanId").toString();
		} else if (session.getAttribute("maxIdInCM") != null) {
			stage = "POD";
			txnType = "LIM";
			commonId = session.getAttribute("maxIdInCM").toString();
		}

		

		logger.info("In DocumentBehindAction(assetDocs) loan/deal id: "
				+ commonId + ", stage: " + stage + ",Txn Type: " + txnType);

		if (session.getAttribute("cmAuthor") != null
				&& !session.getAttribute("cmAuthor").equals("")) {
			request.setAttribute("viewDeal", "cmAuthor");
		}
		
		logger.info("Implementation class: "+dao.getClass()); 			// changed by asesh
		ArrayList documents = dao.getPODDocuments(commonId, stage, txnType,source);
		logger.info("In DocumentBehindAction(assetDocs) documents Size: "
				+ documents.size());
		request.setAttribute("calDoc", documents.size());
		if(documents.size()>0){
			request.setAttribute("dataFound","dataFound");
		}
		request.setAttribute("documents", documents);

		//Changes By Amit Starts
		logger.info("Implementation class: "+dao.getClass()); 			// changed by asesh
		ArrayList additionalDocuments = dao.getAdditionalDocsPOD(commonId,stage, txnType,source);
		request.setAttribute("psize",additionalDocuments.size());
		if(additionalDocuments.size()>0)
		{
			request.setAttribute("additionalDocuments",additionalDocuments);
		}
		//Changes By Amit Ends
		
		return mapping.findForward("PODDocs");
	}
	
}