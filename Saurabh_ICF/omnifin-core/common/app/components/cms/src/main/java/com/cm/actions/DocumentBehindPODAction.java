/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.cm.actions;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.log4j.Logger;
import com.cm.dao.CreditManagementDAO;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.cp.vo.DocumentsVo;
import com.lockRecord.action.LockRecordCheck;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

/**
 * MyEclipse Struts Creation date: 04-27-2011
 * 
 * XDoclet definition:
 * 
 * @struts.action parameter="method" validate="true"
 */
public class DocumentBehindPODAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(DocumentBehindPODAction.class.getName());

	//change by sachin
	CreditManagementDAO dao=(CreditManagementDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditManagementDAO.IDENTITY);
     

	//end by sachin


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
		logger.info("Inside DocumentBehindPODAction........applicationDocs");

		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();

		}else{
			logger.info("here in applicationDocs method of DocumentBehindPODAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		
		//Changes by Amit Starts
		session.removeAttribute("entityType");
		//Changes by Amit Ends
		
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

		String recStatus = CommonFunction.checkNull(session.getAttribute("recStatus"));
		String id = null;
		String stage = CommonFunction.checkNull(session.getAttribute("stage"));
		String txnType = CommonFunction.checkNull(session.getAttribute("txnType"));

		logger.info("txnType in document Behind Pod Action..........."+txnType);
		
		if (session.getAttribute("id") != null) 
		{
			id = session.getAttribute("id").toString();
		} 
		else if (session.getAttribute("maxIdInCM") != null) 
		{
			id = session.getAttribute("maxIdInCM").toString();
		}

		logger.info("In DocumentBehindPODAction loan/deal id: " + id
				+ ", stage: " + stage + ",Txn Type: " + txnType
				+ ", Rec Status: " + recStatus);

		if (session.getAttribute("cmAuthor") != null && !session.getAttribute("cmAuthor").equals("")) 
		{
			request.setAttribute("viewDeal", "cmAuthor");
		}
		String entityType = request.getParameter("entityType");
		logger.info("In applicantDocs entityType: " + entityType);
		
		
		
		
		//ServletContext context=getServlet().getServletContext();
		if(context!=null)
		{
			
		flag = LockRecordCheck.lockCheck(userId,functionId,id,context);
		logger.info("Flag ........................................ "+flag);
		if(!flag)
		{
			logger.info("Record is Locked");			
			request.setAttribute("message", "Locked");
			request.setAttribute("recordId", id);
			request.setAttribute("disbursalAuthor","disbursalAuthor");
			return mapping.getInputForward();
		}
		}
		logger.info("Implementation class: "+dao.getClass());
		String result=null;
		ArrayList<DocumentsVo> documents=null;
		if(funid==4000146)
		{
			String searchStatus=CommonFunction.checkNull((String)session.getAttribute("searchStatus")).trim();
			if(CommonFunction.checkNull(searchStatus).equalsIgnoreCase("A"))
			{
			result=dao.getDocFromTempOrNotAtDocCollection(entityType, id, stage, txnType);
			if(!CommonFunction.checkNull(result).equalsIgnoreCase("0"))
			{
				source="CR_DOCUMENT_DTL_TEMP";
				recStatus="P";
			}
			else
			{
				source="CR_DOCUMENT_DTL";
				recStatus="A";
			}
				documents=dao.getDocumentsForAllApplicationDocAtDocCollection(entityType, id, stage, txnType, recStatus, source);
			}
			else if(CommonFunction.checkNull(searchStatus).equalsIgnoreCase("P"))
			{
				source="CR_DOCUMENT_DTL_TEMP";
				recStatus="P";
				documents=dao.getDocumentsForAllApplicationDocAtDocCollection(entityType, id, stage, txnType, recStatus, source);
			}
			
		}
		else if(funid==4000151)
		{
			source="CR_DOCUMENT_DTL_TEMP ";
			recStatus="F";
			documents=dao.getDocumentsForAllApplicationDocAtDocCollection(entityType, id, stage, txnType, recStatus, source);
		}
		else
		{
		 documents = dao.getApplicationDocuments(entityType, id,stage, txnType, recStatus,source);
		}
		logger.info("documents Size: " + documents.size());
		//start by sachin
//	    String functionId=CommonFunction.checkNull((String)session.getAttribute("functionId")).trim();
	    if(CommonFunction.checkNull(functionId).trim().equalsIgnoreCase("7000114")||CommonFunction.checkNull(functionId).trim().equalsIgnoreCase("8000219"))
	    {
		ArrayList<DocumentsVo> documentOfDc = dao.getApplicationDocumentsForDeal(entityType, id,"DC",recStatus);
		if(documentOfDc.size()>0){
			for(int i=0;i<documentOfDc.size();i++){
				documents.add(documentOfDc.get(i));
			}
		}
		request.setAttribute("viewDeal", "viewDeal");
	    }
		
		//end by sachin
		if(documents.size()==0){
			logger.info("documents Size: " + documents.size());
			request.setAttribute("noDocument","noDocument");
		}
		request.setAttribute("calDoc", documents.size());

		if(txnType.equals("DC"))
		{
			session.removeAttribute("loanId");
			session.setAttribute("dealId", id);
		}
		else{
			session.removeAttribute("dealId");
			session.setAttribute("loanId", id);
		}

		//Changes By Amit Starts
		logger.info("Implementation class: "+dao.getClass());
		ArrayList additionalDocuments=null;
		if(funid==4000146 || funid==4000151)
		{

			additionalDocuments=dao.getAdditionalDocsForAllDocTypeAtDocCollection(entityType, id,stage, txnType, recStatus, source);
			
		}
		else
		{
		 additionalDocuments = dao.getAdditionalDocs(entityType, id,stage, txnType, recStatus);
		}
		//start by sachin
		logger.info("additionalDocuments Size1  :  "+additionalDocuments.size());
		if(CommonFunction.checkNull(functionId).trim().equalsIgnoreCase("7000114")||CommonFunction.checkNull(functionId).trim().equalsIgnoreCase("8000219"))
	    {
			ArrayList additionalDocumentsForDeal = dao.getAdditionalDocsForDeal(entityType, id,"DC", recStatus);
			if(additionalDocumentsForDeal.size()>0)
			{
			for(int i=0;i<additionalDocumentsForDeal.size();i++)
			{
			additionalDocuments.add(additionalDocumentsForDeal.get(i));	
			}
			}
	    }
		logger.info("additionalDocuments Size2  :  "+additionalDocuments.size());
		//end by sachin
		
		request.setAttribute("psize",additionalDocuments.size());
		if(documents.size()>0)
		{
			request.setAttribute("dataFound","dataFound");
		}
		
		request.setAttribute("documents",documents);	
		
		if(additionalDocuments.size()>0)
		{
			request.setAttribute("additionalDocuments",additionalDocuments);
		}
		session.setAttribute("entityType",entityType);
		//Changes By Amit Ends
		return mapping.findForward("applicationDocs");
	}

	public ActionForward applicantDocs(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Inside DocumentBehindPODAction........applicantDocs");

		HttpSession session = request.getSession();
		
		//Changes by Amit Starts
		
		session.removeAttribute("entityType");
		//Changes by Amit Ends
		
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in applicantDocs method of DocumentBehindPODAction action the session is out----------------");
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

		String recStatus = CommonFunction.checkNull(session.getAttribute("recStatus"));
		String id = null;
		String stage = CommonFunction.checkNull(session.getAttribute("stage"));
		String txnType = CommonFunction.checkNull(session.getAttribute("txnType"));
		if (session.getAttribute("id") != null) 
		{
			id = session.getAttribute("id").toString();
		} 
		else if (session.getAttribute("maxIdInCM") != null) 
		{
			id = session.getAttribute("maxIdInCM").toString();
		}

		logger.info("In DocumentBehindPODAction loan/deal id: " + id
				+ ", stage: " + stage + ",Txn Type: " + txnType);

		if (session.getAttribute("cmAuthor") != null
				&& !session.getAttribute("cmAuthor").equals("")) {
			request.setAttribute("viewDeal", "cmAuthor");
		}
		String entityType = request.getParameter("entityType");
		logger.info("In applicantDocs entityType: " + entityType);

		logger.info("Implementation class: "+dao.getClass());
		String functionId=CommonFunction.checkNull((String)session.getAttribute("functionId")).trim();
		ArrayList<DocumentsVo> documents=null;
		String result=null;
		String source=null;
		if(CommonFunction.checkNull(functionId).trim().equalsIgnoreCase("4000146"))
		{
			String searchStatus=CommonFunction.checkNull((String)session.getAttribute("searchStatus")).trim();
			if(CommonFunction.checkNull(searchStatus).equalsIgnoreCase("A"))
			{
				result=dao.getDocFromTempOrNotAtDocCollection(entityType, id, stage, txnType);
				if(!CommonFunction.checkNull(result).equalsIgnoreCase("0"))
				{
					source="CR_DOCUMENT_DTL_TEMP d";
					recStatus="P";
					documents=dao.getAllApplicantDocsForTempAtDocCollection(entityType, id, stage, txnType, recStatus, source);
				}
				else
				{
					recStatus="A";
					source="CR_DOCUMENT_DTL d";
					documents=dao.getAllApplicantDocsForTempAtDocCollection(entityType, id, stage, txnType, recStatus, source);
				}
			}
			else if(CommonFunction.checkNull(searchStatus).equalsIgnoreCase("P"))
			{
				source="CR_DOCUMENT_DTL_TEMP d";
				recStatus="P";
				documents=dao.getAllApplicantDocsForTempAtDocCollection(entityType, id, stage, txnType, recStatus, source);	
			}
			
		}
		else if(CommonFunction.checkNull(functionId).trim().equalsIgnoreCase("4000151"))
		{
			source="CR_DOCUMENT_DTL_TEMP d";
			recStatus="F";
			documents=dao.getAllApplicantDocsForTempAtDocCollection(entityType, id, stage, txnType, recStatus, source);
		}
		else
		{
		documents = dao.getAllApplicantDocs(entityType, id,
				stage, txnType, recStatus);
		}
		logger.info("documents Size: " + documents.size());

		if(CommonFunction.checkNull(functionId).trim().equalsIgnoreCase("7000114")||CommonFunction.checkNull(functionId).trim().equalsIgnoreCase("8000219"))
		{
			ArrayList<DocumentsVo> documentOfDc = dao.getAllApplicantDocsForDeal(entityType, id,"DC",recStatus);
			if(documentOfDc.size()>0)
				for(int i=0;i<documentOfDc.size();i++)
					documents.add(documentOfDc.get(i));		
			request.setAttribute("viewDeal", "viewDeal");  
		}
		if(documents.size()==0){
			logger.info("documents Size: " + documents.size());
			request.setAttribute("noDocument","noDocument");
		}
		request.setAttribute("calDoc", documents.size());
		
		//Changes By Amit Starts
		logger.info("Implementation class: "+dao.getClass());
		ArrayList additionalDocuments=null;
		if(CommonFunction.checkNull(functionId).trim().equalsIgnoreCase("4000146") || CommonFunction.checkNull(functionId).trim().equalsIgnoreCase("4000151"))
		{

			additionalDocuments=dao.getAdditionalDocsForAllDocTypeAtDocCollection(entityType, id,stage, txnType, recStatus, source);
			
		}
		else
		{
		 additionalDocuments = dao.getAdditionalDocs(entityType, id,stage, txnType, recStatus);
		}
		request.setAttribute("psize",additionalDocuments.size());

		if(CommonFunction.checkNull(functionId).trim().equalsIgnoreCase("7000114")||CommonFunction.checkNull(functionId).trim().equalsIgnoreCase("8000219"))
			    {
					ArrayList additionalDocumentsForDeal = dao.getAdditionalDocsForDeal(entityType, id,"DC", recStatus);
					if(additionalDocumentsForDeal.size()>0){
					for(int i=0;i<additionalDocumentsForDeal.size();i++){
						additionalDocuments.add(additionalDocumentsForDeal.get(i));
					}
					}  
			    }
		if(documents.size()>0)
		{
			request.setAttribute("dataFound","dataFound");
		}
		
		request.setAttribute("documents",documents);
		
		if(additionalDocuments.size()>0)
		{
			request.setAttribute("additionalDocuments",additionalDocuments);
		}
		session.setAttribute("entityType",entityType);
		//Changes By Amit Ends
		return mapping.findForward("applicantDocs");
	}

	public ActionForward coApplicantDocs(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("Inside DocumentBehindPODAction........coApplicantDocs");

		HttpSession session = request.getSession();
		
		//Changes by Amit Starts
		session.removeAttribute("entityType");
		//Changes by Amit Ends
		
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in coApplicantDocs method of DocumentBehindPODAction action the session is out----------------");
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
		String recStatus = CommonFunction.checkNull(session.getAttribute("recStatus"));
		String id = null;
		String stage = CommonFunction.checkNull(session.getAttribute("stage"));
		String txnType = CommonFunction.checkNull(session.getAttribute("txnType"));
		if (session.getAttribute("id") != null) 
		{
			id = session.getAttribute("id").toString();
		} 
		else if (session.getAttribute("maxIdInCM") != null) 
		{
			id = session.getAttribute("maxIdInCM").toString();
		}
		logger.info("In DocumentBehindPODAction loan/deal id: " + id
				+ ", stage: " + stage + ",Txn Type: " + txnType);

		if (session.getAttribute("cmAuthor") != null
				&& !session.getAttribute("cmAuthor").equals("")) {
			request.setAttribute("viewDeal", "cmAuthor");
		}
		String entityType = request.getParameter("entityType");
		logger.info("In coApplicantDocs entityType: " + entityType);
		logger.info("Implementation class: "+dao.getClass());
		String functionId=CommonFunction.checkNull((String)session.getAttribute("functionId")).trim();
		
		ArrayList<DocumentsVo> documents=null;
		String result=null;
		String source=null;
		if(CommonFunction.checkNull(functionId).trim().equalsIgnoreCase("4000146"))
		{
			String searchStatus=CommonFunction.checkNull((String)session.getAttribute("searchStatus")).trim();
			if(CommonFunction.checkNull(searchStatus).equalsIgnoreCase("A"))
			{
			result=dao.getDocFromTempOrNotAtDocCollection(entityType, id, stage, txnType);
			if(!CommonFunction.checkNull(result).equalsIgnoreCase("0"))
			{
				source="CR_DOCUMENT_DTL_TEMP d";
				recStatus="P";
				documents=dao.getAllApplicantDocsForTempAtDocCollection(entityType, id, stage, txnType, recStatus, source);
			}
			else
			{
				recStatus="A";
				source="CR_DOCUMENT_DTL d";
				documents=dao.getAllApplicantDocsForTempAtDocCollection(entityType, id, stage, txnType, recStatus, source);
			}
			}
			else if(CommonFunction.checkNull(searchStatus).equalsIgnoreCase("P"))
			{
				source="CR_DOCUMENT_DTL_TEMP d";
				recStatus="P";
				documents=dao.getAllApplicantDocsForTempAtDocCollection(entityType, id, stage, txnType, recStatus, source);	
			}
		}
		else if(CommonFunction.checkNull(functionId).trim().equalsIgnoreCase("4000151"))
		{
			source="CR_DOCUMENT_DTL_TEMP d";
			recStatus="F";
			documents=dao.getAllApplicantDocsForTempAtDocCollection(entityType, id, stage, txnType, recStatus, source);
		}
		else
		{
			documents = dao.getAllApplicantDocs(entityType, id,stage, txnType, recStatus);
		}
		logger.info("documents Size: " + documents.size());

		  if(CommonFunction.checkNull(functionId).trim().equalsIgnoreCase("7000114")||CommonFunction.checkNull(functionId).trim().equalsIgnoreCase("8000219"))
			    {
				ArrayList<DocumentsVo> documentOfDc = dao.getAllApplicantDocsForDeal(entityType, id,"DC",recStatus);
				if(documentOfDc.size()>0){
					for(int i=0;i<documentOfDc.size();i++){
						documents.add(documentOfDc.get(i));	

					}
				}
				request.setAttribute("viewDeal", "viewDeal");
			    }

		
		if(documents.size()==0){
			logger.info("documents Size: " + documents.size());
			request.setAttribute("noDocument","noDocument");
		}
		request.setAttribute("calDoc", documents.size());
		
		//Changes By Amit Starts
		logger.info("Implementation class: "+dao.getClass());
		
		ArrayList additionalDocuments=null;
		if(CommonFunction.checkNull(functionId).trim().equalsIgnoreCase("4000146") || CommonFunction.checkNull(functionId).trim().equalsIgnoreCase("4000151"))
		{
			additionalDocuments=dao.getAdditionalDocsForAllDocTypeAtDocCollection(entityType, id,stage, txnType, recStatus, source);			
		}
		else
		{
			additionalDocuments = dao.getAdditionalDocs(entityType, id,stage, txnType, recStatus);
		}
		request.setAttribute("psize",additionalDocuments.size());

		if(CommonFunction.checkNull(functionId).trim().equalsIgnoreCase("7000114")||CommonFunction.checkNull(functionId).trim().equalsIgnoreCase("8000219"))
			    {
					ArrayList additionalDocumentsForDeal = dao.getAdditionalDocsForDeal(entityType, id,"DC", recStatus);
					if(additionalDocumentsForDeal.size()>0){
					for(int i=0;i<additionalDocumentsForDeal.size();i++){
						additionalDocuments.add(additionalDocumentsForDeal.get(i));
					}
					}
			    }
		if(documents.size()>0)
		{
			request.setAttribute("dataFound","dataFound");
		}
		
		request.setAttribute("documents",documents);
				
		if(additionalDocuments.size()>0)
		{
			request.setAttribute("additionalDocuments",additionalDocuments);
		}
		session.setAttribute("entityType",entityType);
		//Changes By Amit Ends

		return mapping.findForward("coApplicantDocs");
	}

	public ActionForward guarantorDocs(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Inside DocumentBehindPODAction........guarantorDocs");

		HttpSession session = request.getSession();
		
		//Changes by Amit Starts
		session.removeAttribute("entityType");
		//Changes by Amit Ends
		
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in guarantorDocs method of DocumentBehindPODAction action the session is out----------------");
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

		String recStatus = CommonFunction.checkNull(session.getAttribute("recStatus"));
		String id = null;
		String stage = CommonFunction.checkNull(session.getAttribute("stage"));
		String txnType = CommonFunction.checkNull(session.getAttribute("txnType"));
		
		if (session.getAttribute("id") != null) 
		{
			id = session.getAttribute("id").toString();
		} 
		else if (session.getAttribute("maxIdInCM") != null) 
		{
			id = session.getAttribute("maxIdInCM").toString();
		}

		logger.info("In DocumentBehindPODAction loan/deal id: " + id
				+ ", stage: " + stage + ",Txn Type: " + txnType);

		if (session.getAttribute("cmAuthor") != null
				&& !session.getAttribute("cmAuthor").equals("")) {
			request.setAttribute("viewDeal", "cmAuthor");
		}
		String entityType = request.getParameter("entityType");
		logger.info("In guarantorDocs entityType: " + entityType);
		logger.info("Implementation class: "+dao.getClass());
		 String functionId=CommonFunction.checkNull((String)session.getAttribute("functionId")).trim();
		
			ArrayList<DocumentsVo> documents=null;
			String result=null;
			String source=null;
			if(CommonFunction.checkNull(functionId).trim().equalsIgnoreCase("4000146"))
			{

				String searchStatus=CommonFunction.checkNull((String)session.getAttribute("searchStatus")).trim();
				if(CommonFunction.checkNull(searchStatus).equalsIgnoreCase("A"))
				{
				result=dao.getDocFromTempOrNotAtDocCollection(entityType, id, stage, txnType);
				if(!CommonFunction.checkNull(result).equalsIgnoreCase("0"))
				{
					source="CR_DOCUMENT_DTL_TEMP d";
					recStatus="P";
					documents=dao.getAllApplicantDocsForTempAtDocCollection(entityType, id, stage, txnType, recStatus, source);
				}
				else
				{
					recStatus="A";
					source="CR_DOCUMENT_DTL d";
					documents=dao.getAllApplicantDocsForTempAtDocCollection(entityType, id, stage, txnType, recStatus, source);
				}
				}
				else if(CommonFunction.checkNull(searchStatus).equalsIgnoreCase("P"))
				{
					source="CR_DOCUMENT_DTL_TEMP d";
					recStatus="P";
					documents=dao.getAllApplicantDocsForTempAtDocCollection(entityType, id, stage, txnType, recStatus, source);	
				}
				
			}
			else if(CommonFunction.checkNull(functionId).trim().equalsIgnoreCase("4000151"))
			{
				source="CR_DOCUMENT_DTL_TEMP d";
				recStatus="F";
				documents=dao.getAllApplicantDocsForTempAtDocCollection(entityType, id, stage, txnType, recStatus, source);
			}
			else
			{
				documents = dao.getAllApplicantDocs(entityType, id,stage, txnType, recStatus);
			}
		logger.info("documents Size: " + documents.size());
		  if(CommonFunction.checkNull(functionId).trim().equalsIgnoreCase("7000114")||CommonFunction.checkNull(functionId).trim().equalsIgnoreCase("8000219"))
			    {
				ArrayList<DocumentsVo> documentOfDc = dao.getAllApplicantDocsForDeal(entityType, id,"DC",recStatus);
				if(documentOfDc.size()>0){
					for(int i=0;i<documentOfDc.size();i++){
						documents.add(documentOfDc.get(i));
					}    
				}
				request.setAttribute("viewDeal", "viewDeal");
			    }
		if(documents.size()==0){
			logger.info("documents Size: " + documents.size());
			request.setAttribute("noDocument","noDocument");
		}
		request.setAttribute("calDoc", documents.size());
		
		//Changes By Amit Starts
		logger.info("Implementation class: "+dao.getClass());
		ArrayList additionalDocuments=null;

		if(CommonFunction.checkNull(functionId).trim().equalsIgnoreCase("4000146") || CommonFunction.checkNull(functionId).trim().equalsIgnoreCase("4000151"))
		{
			additionalDocuments=dao.getAdditionalDocsForAllDocTypeAtDocCollection(entityType, id,stage, txnType, recStatus, source);
					
		}
		else
		{
			additionalDocuments = dao.getAdditionalDocs(entityType, id,stage, txnType, recStatus);
		}

		request.setAttribute("psize",additionalDocuments.size());

		if(CommonFunction.checkNull(functionId).trim().equalsIgnoreCase("7000114")||CommonFunction.checkNull(functionId).trim().equalsIgnoreCase("8000219"))
			    {
					ArrayList additionalDocumentsForDeal = dao.getAdditionalDocsForDeal(entityType, id,"DC", recStatus);
					if(additionalDocumentsForDeal.size()>0){
					for(int i=0;i<additionalDocumentsForDeal.size();i++){
						additionalDocuments.add(additionalDocumentsForDeal.get(i));
					}
					}
			    }
		if(documents.size()>0)
		{
			request.setAttribute("dataFound","dataFound");
		}

		request.setAttribute("documents",documents);
				
		if(additionalDocuments.size()>0)
		{
			request.setAttribute("additionalDocuments",additionalDocuments);
		}
		session.setAttribute("entityType",entityType);
		//Changes By Amit Ends
		
		return mapping.findForward("guarantorDocs");
	}

	public ActionForward collateralDocs(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Inside DocumentBehindPODAction........collateralDocs");

		HttpSession session = request.getSession();
		
		//Changes by Amit Starts
		session.removeAttribute("entityType");
		//Changes by Amit Ends
		
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in collateralDocs method of DocumentBehindPODAction action the session is out----------------");
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

		String recStatus = CommonFunction.checkNull(session.getAttribute("recStatus"));
		String id = null;
		String stage = CommonFunction.checkNull(session.getAttribute("stage"));
		String txnType = CommonFunction.checkNull(session.getAttribute("txnType"));
		
		if (session.getAttribute("id") != null) 
		{
			id = session.getAttribute("id").toString();
		} 
		else if (session.getAttribute("maxIdInCM") != null) 
		{
			id = session.getAttribute("maxIdInCM").toString();
		}

		logger.info("In DocumentBehindPODAction loan/deal id: " + id
				+ ", stage: " + stage + ",Txn Type: " + txnType);

		if (session.getAttribute("cmAuthor") != null
				&& !session.getAttribute("cmAuthor").equals("")) {
			request.setAttribute("viewDeal", "cmAuthor");
		}
		String entityType = request.getParameter("entityType");
		logger.info("In collateralDocs entityType: " + entityType);
		logger.info("Implementation class: "+dao.getClass());
		String functionId=CommonFunction.checkNull((String)session.getAttribute("functionId")).trim();
		ArrayList<DocumentsVo> documents=null;
		String result=null;
		String source=null;
		if(CommonFunction.checkNull(functionId).trim().equalsIgnoreCase("4000146"))
		{

			String searchStatus=CommonFunction.checkNull((String)session.getAttribute("searchStatus")).trim();
			if(CommonFunction.checkNull(searchStatus).equalsIgnoreCase("A"))
			{
			result=dao.getDocFromTempOrNotAtDocCollection(entityType, id, stage, txnType);
			if(!CommonFunction.checkNull(result).equalsIgnoreCase("0"))
			{
				source="CR_DOCUMENT_DTL_TEMP d";
				recStatus="P";
				documents=dao.getAssetCollateralDocumentsForTempAtDocCollection(entityType, id, stage, txnType, recStatus, source);
			}
			else
			{
				recStatus="A";
				source="CR_DOCUMENT_DTL d";
				documents=dao.getAssetCollateralDocumentsForTempAtDocCollection(entityType, id, stage, txnType, recStatus, source);
			}
			}
			else if(CommonFunction.checkNull(searchStatus).equalsIgnoreCase("P"))
			{
				source="CR_DOCUMENT_DTL_TEMP d";
				recStatus="P";
				documents=dao.getAssetCollateralDocumentsForTempAtDocCollection(entityType, id, stage, txnType, recStatus, source);	
			}
			
		}
		else if(CommonFunction.checkNull(functionId).trim().equalsIgnoreCase("4000151"))
		{
			source="CR_DOCUMENT_DTL_TEMP d";
			recStatus="F";
			documents=dao.getAssetCollateralDocumentsForTempAtDocCollection(entityType, id, stage, txnType, recStatus, source);	
		}
		else
		{
			documents = dao.getAssetCollateralDocuments(entityType,id, stage, txnType, recStatus);
		}
		logger.info("documents Size: " + documents.size());
		  if(CommonFunction.checkNull(functionId).trim().equalsIgnoreCase("7000114")||CommonFunction.checkNull(functionId).trim().equalsIgnoreCase("8000219"))
			    {
				ArrayList<DocumentsVo> documentOfDc = dao.getAssetCollateralDocumentsForDeal(entityType, id,"DC",recStatus);
				if(documentOfDc.size()>0){
					for(int i=0;i<documentOfDc.size();i++)
					{
					documents.add(documentOfDc.get(i));	
					
					}
				}
				request.setAttribute("viewDeal", "viewDeal");
			    }
		if(documents.size()==0){
			logger.info("documents Size: " + documents.size());
			request.setAttribute("noDocument","noDocument");
		}
		request.setAttribute("calDoc", documents.size());
		
		//Changes By Amit Starts
		logger.info("Implementation class: "+dao.getClass());
		
		ArrayList additionalDocuments=null;

		if(CommonFunction.checkNull(functionId).trim().equalsIgnoreCase("4000146") || CommonFunction.checkNull(functionId).trim().equalsIgnoreCase("4000151"))
		{

			additionalDocuments=dao.getAdditionalDocsForAllDocTypeAtDocCollection(entityType, id,stage, txnType, recStatus, source);
					
		}
		else
		{
			 additionalDocuments = dao.getAdditionalDocs(entityType, id,stage, txnType, recStatus);
		}
		request.setAttribute("psize",additionalDocuments.size());

		if(CommonFunction.checkNull(functionId).trim().equalsIgnoreCase("7000114")||CommonFunction.checkNull(functionId).trim().equalsIgnoreCase("8000219"))
			    {
					ArrayList additionalDocumentsForDeal = dao.getAdditionalDocsForDeal(entityType, id,"DC", recStatus);
					if(additionalDocumentsForDeal.size()>0){
					for(int i=0;i<additionalDocumentsForDeal.size();i++){
						additionalDocuments.add(additionalDocumentsForDeal.get(i));
					}
					}
			    }
		if(documents.size()>0)
		{
			request.setAttribute("dataFound","dataFound");
		}
		
		request.setAttribute("documents",documents);
				
		if(additionalDocuments.size()>0)
		{
			request.setAttribute("additionalDocuments",additionalDocuments);
		}
		session.setAttribute("entityType",entityType);
		//Changes By Amit Ends
		
		return mapping.findForward("collateralDocs");
	}

	public ActionForward assetDocs(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Inside DocumentBehindPODAction........assetDocs");

		HttpSession session = request.getSession();
		
		//Changes by Amit Starts
		session.removeAttribute("entityType");
		//Changes by Amit Ends
		
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in assetDocs method of DocumentBehindPODAction action the session is out----------------");
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

		String recStatus = CommonFunction.checkNull(session.getAttribute("recStatus"));
		String id = null;
		String stage = CommonFunction.checkNull(session.getAttribute("stage"));
		String txnType = CommonFunction.checkNull(session.getAttribute("txnType"));
		
		if (session.getAttribute("id") != null) 
		{
			id = session.getAttribute("id").toString();
		} 
		else if (session.getAttribute("maxIdInCM") != null) 
		{
			id = session.getAttribute("maxIdInCM").toString();
		}

		logger.info("In DocumentBehindPODAction loan/deal id: " + id
				+ ", stage: " + stage + ",Txn Type: " + txnType);

		if (session.getAttribute("cmAuthor") != null
				&& !session.getAttribute("cmAuthor").equals("")) {
			request.setAttribute("viewDeal", "cmAuthor");
		}
		String entityType = request.getParameter("entityType");
		logger.info("In assetDocs entityType: " + entityType);
		logger.info("Implementation class: "+dao.getClass());
		String functionId=CommonFunction.checkNull((String)session.getAttribute("functionId")).trim();
		ArrayList<DocumentsVo> documents=null;
		String result=null;
		String source=null;
		if(CommonFunction.checkNull(functionId).trim().equalsIgnoreCase("4000146"))
		{
			String searchStatus=CommonFunction.checkNull((String)session.getAttribute("searchStatus")).trim();
			if(CommonFunction.checkNull(searchStatus).equalsIgnoreCase("A"))
			{
			result=dao.getDocFromTempOrNotAtDocCollection(entityType, id, stage, txnType);
			if(!CommonFunction.checkNull(result).equalsIgnoreCase("0"))
			{
				source="CR_DOCUMENT_DTL_TEMP d";
				recStatus="P";
				documents=dao.getAssetCollateralDocumentsForTempAtDocCollection(entityType, id, stage, txnType, recStatus, source);
			}
			else
			{
				recStatus="A";
				source="CR_DOCUMENT_DTL d";
				documents=dao.getAssetCollateralDocumentsForTempAtDocCollection(entityType, id, stage, txnType, recStatus, source);
			}
			}
			else if(CommonFunction.checkNull(searchStatus).equalsIgnoreCase("P"))
			{
				source="CR_DOCUMENT_DTL_TEMP d";
				recStatus="P";
				documents=dao.getAssetCollateralDocumentsForTempAtDocCollection(entityType, id, stage, txnType, recStatus, source);	
			}
			
		}
		else if(CommonFunction.checkNull(functionId).trim().equalsIgnoreCase("4000151"))
		{
			source="CR_DOCUMENT_DTL_TEMP d";
			recStatus="F";
			documents=dao.getAssetCollateralDocumentsForTempAtDocCollection(entityType, id, stage, txnType, recStatus, source);	
		}
		else
		{
			documents = dao.getAssetCollateralDocuments(entityType,id, stage, txnType, recStatus);
		}
		logger.info("documents Size: " + documents.size());

		  if(CommonFunction.checkNull(functionId).trim().equalsIgnoreCase("7000114")||CommonFunction.checkNull(functionId).trim().equalsIgnoreCase("8000219"))
			    {
				ArrayList<DocumentsVo> documentOfDc = dao.getAssetCollateralDocumentsForDeal(entityType, id,"DC",recStatus);
				if(documentOfDc.size()>0){
					for(int i=0;i<documentOfDc.size();i++){
						documents.add(documentOfDc.get(i));
					}  
				}
				request.setAttribute("viewDeal", "viewDeal");  
			    }
		if(documents.size()==0){
			logger.info("documents Size: " + documents.size());
			request.setAttribute("noDocument","noDocument");
		}
		request.setAttribute("calDoc", documents.size());
		
		//Changes By Amit Starts
		logger.info("Implementation class: "+dao.getClass());
		ArrayList additionalDocuments=null;

		if(CommonFunction.checkNull(functionId).trim().equalsIgnoreCase("4000146") || CommonFunction.checkNull(functionId).trim().equalsIgnoreCase("4000151"))
		{

			additionalDocuments=dao.getAdditionalDocsForAllDocTypeAtDocCollection(entityType, id,stage, txnType, recStatus, source);
					
		}
		else
		{
			 additionalDocuments = dao.getAdditionalDocs(entityType, id,stage, txnType, recStatus);
		}
		request.setAttribute("psize",additionalDocuments.size());

		if(CommonFunction.checkNull(functionId).trim().equalsIgnoreCase("7000114")||CommonFunction.checkNull(functionId).trim().equalsIgnoreCase("8000219"))
			    {
					ArrayList additionalDocumentsForDeal = dao.getAdditionalDocsForDeal(entityType, id,"DC", recStatus);
					if(additionalDocumentsForDeal.size()>0){
					for(int i=0;i<additionalDocumentsForDeal.size();i++){
						additionalDocuments.add(additionalDocumentsForDeal.get(i));
					}
					}
			    }
		if(documents.size()>0)
		{
			request.setAttribute("dataFound","dataFound");
		}
		
		request.setAttribute("documents",documents);
				
		if(additionalDocuments.size()>0)
		{
			request.setAttribute("additionalDocuments",additionalDocuments);
		}
		session.setAttribute("entityType",entityType);
		//Changes By Amit Ends
		
		return mapping.findForward("assetDocs");
	}

	public ActionForward postDisbursalDocumentAuthor(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("Inside DocumentBehindPODAction............postDisbursalDocumentAuthor");

		HttpSession session = request.getSession();
		
		//Changes by Amit Starts
		session.removeAttribute("entityType");
		//Changes by Amit Ends
		
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in postDisbursalDocumentAuthor method of DocumentBehindPODAction action the session is out----------------");
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
		return mapping.findForward("podAuthor");
	}
}