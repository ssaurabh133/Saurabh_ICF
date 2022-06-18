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
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.CreditProcessingDAO;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

/**
 * MyEclipse Struts Creation date: 07-14-2011
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class ChildDocActionPOD extends Action {
	private static final Logger logger = Logger.getLogger(ChildDocActionPOD.class.getName());
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
		// TODO Auto-generated method stub
		logger.info("In ChildDocActionPOD(execute)");
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in execute method of ChildDocActionPOD action the session is out----------------");
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

		String commonId = "";
		String stage = CommonFunction.checkNull(session.getAttribute("stage"));
		String txnType = "";
		String deaId="";
		
		logger.info("session value of loan id is :---------------------------"+session.getAttribute("loanId"));
		logger.info("session value of deal id is :---------------------------"+session.getAttribute("dealID"));
		
		if (session.getAttribute("loanId") != null) {
			
			txnType = "LIM";
			commonId = session.getAttribute("loanId").toString();
		} 

		if (session.getAttribute("dealId") != null) {
			
			txnType = "DC";
			commonId = session.getAttribute("dealId").toString();
		} 
		
		logger.info("In ChildDocActionPOD(execute) loan/deal id: " + commonId
				+ ", stage: " + stage + ",Txn Type: " + txnType);
		
		//start by sachin
		String functionId=CommonFunction.checkNull((String)session.getAttribute("functionId")).trim();
		if((CommonFunction.checkNull(functionId).trim().equalsIgnoreCase("7000114")) && (!CommonFunction.checkNull((String)session.getAttribute("loanId")).trim().equalsIgnoreCase("")))
		{
			String deal=ConnectionDAO.singleReturn("select LOAN_DEAL_ID from cr_loan_dtl where LOAN_ID='"+commonId+"'");
			commonId=commonId+","+deal; 
		}
		//end by sachin
		String entityType = request.getParameter("entityType");
		String rowCount = request.getParameter("rowCount");
		String docId = request.getParameter("docId");
		String entityId = request.getParameter("entityId");
		logger.info("docID: " + docId + "rowCount: " + rowCount+"entityType: "+entityType+" entityId: "+entityId);
		CreditProcessingDAO detail=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);	// changed by asesh for mssql
        logger.info("Implementation class: "+detail.getClass()); 
		//CreditProcessingDAO detail = new CreditProcessingDAOImpl();
        CreditManagementDAO cmdetail=(CreditManagementDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditManagementDAO.IDENTITY);
	     logger.info("Implementation class: "+detail.getClass());
	     String source=null;
        if((CommonFunction.checkNull(functionId).trim().equalsIgnoreCase("4000146")) || (CommonFunction.checkNull(functionId).trim().equalsIgnoreCase("4000151")))
        {
        	String searchStatus=CommonFunction.checkNull(session.getAttribute("searchStatus"));
        	if(CommonFunction.checkNull(searchStatus).trim().equalsIgnoreCase("A"))
        	{
        	String result=cmdetail.getDocFromTempOrNotAtDocCollection(entityType,entityId, stage, txnType);
        	if(!CommonFunction.checkNull(result).equalsIgnoreCase("0"))
        	{
        		source="cr_document_dtl_temp B";
        	}
        	else
        	{
        		source="cr_document_dtl B";
        	}
        	}
        	if(CommonFunction.checkNull(searchStatus).trim().equalsIgnoreCase("P") || CommonFunction.checkNull(searchStatus).trim().equalsIgnoreCase("F"))
        	{
        		source="cr_document_dtl_temp B";
        	}
        }
        else
        {
    		source="cr_document_dtl B";
        }
		String collectedDocsQ = "select DISTINCT B.DOC_CHILD_IDS from cr_document_child_m A,"+source+" where A.REC_STATUS='A' AND A.DOC_ID=B.DOC_ID and  A.DOC_ID="
				+ docId
				+ " AND B.DOC_CHILD_IDS<>''" ;
		if(!CommonFunction.checkNull(stage).trim().equalsIgnoreCase(""))
			collectedDocsQ=collectedDocsQ+ " AND TXN_TYPE='"+ txnType+"' AND STAGE_ID='" + stage+"'";
		collectedDocsQ=collectedDocsQ+ " AND TXNID in("+commonId+") ";
			collectedDocsQ=collectedDocsQ+" AND DOC_TYPE='"+entityType+"' AND ENTITY_ID='"+entityId+"'" ;
		logger.info("In ChildDocActionPOD(execute) collectedDocsQ: "
				+ collectedDocsQ);
		String collectedDocs = ConnectionDAO.singleReturn(collectedDocsQ);
		logger.info("In ChildDocActionPOD(execute) collectedDocs: "
				+ CommonFunction.checkNull(collectedDocs));
		if (!CommonFunction.checkNull(collectedDocs).equalsIgnoreCase("")) {
			String[] collects = collectedDocs.split("\\|");
			logger.info("In ChildDocActionPOD(execute) collectedDocs: "
					+ collects);
			for (int i = 0; i < collects.length; i++) {
				logger.info("In ChildDocActionPOD(execute) collectedDocs: "
						+ collects[i]);
			}
		    session.setAttribute("collects", collects);
		}
		ArrayList collectChildDocs = detail.getChildDocs(docId);
		session.setAttribute("rowCount", rowCount);
		session.setAttribute("child", collectChildDocs);
		return mapping.findForward("success");
	}
}