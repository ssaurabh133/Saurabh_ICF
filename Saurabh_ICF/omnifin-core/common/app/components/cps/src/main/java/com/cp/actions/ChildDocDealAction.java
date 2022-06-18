package com.cp.actions;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.CreditProcessingDAO;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class ChildDocDealAction extends Action {
	private static final Logger logger = Logger.getLogger(ChildDocAction.class.getName());
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
		logger.info("In ChildDocAction(execute)");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in execute method of ChildDocAction action the session is out----------------");
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
		String stage = "";
		String txnType = "";
		
		logger.info("session value of loan id is :---------------------------"+session.getAttribute("loanId"));
		logger.info("session value of deal id is :---------------------------"+session.getAttribute("dealID"));
		logger.info("session value of max id is :---------------------------"+session.getAttribute("maxIdInCM"));
		
		if (session.getAttribute("loanId") != null) {
			stage = "PRS";
			txnType = "DC";
			commonId = session.getAttribute("loanId").toString();
		} else if (session.getAttribute("maxIdInCM") != null) {
			stage = "PRS";
			txnType = "DC";
			commonId = session.getAttribute("maxIdInCM").toString();
		}

		
		logger.info("In ChildDocDealAction(execute) loan/deal id: " + commonId
				+ ", stage: " + stage + ",Txn Type: " + txnType);

		
		String entityType = request.getParameter("entityType");
		String rowCount = request.getParameter("rowCount");
		String docId = request.getParameter("docId");
		String entityId = request.getParameter("entityId");
		
		
		logger.info("docID: " + docId + "rowCount: " + rowCount+"entityType: "+entityType+" entityId: "+entityId);
		CreditProcessingDAO detail=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+detail.getClass()); 			// changed by asesh
		//CreditProcessingDAO detail = new CreditProcessingDAOImpl();
		String collectedDocsQ = "select DISTINCT B.DOC_CHILD_IDS from cr_document_child_m A,cr_document_dtl B where A.REC_STATUS='A' AND A.DOC_ID=B.DOC_ID and  A.DOC_ID="
				+ docId
				+ " AND B.DOC_CHILD_IDS<>'' AND TXN_TYPE='"
				+ txnType
				+ "' AND STAGE_ID='" + stage + "' AND TXNID=(select loan_deal_id from cr_loan_dtl where loan_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(commonId)).trim()+"')  AND DOC_TYPE='"+entityType+"' AND ENTITY_ID='"+entityId+"'" ;
		logger.info("In ChildDocAction(execute) collectedDocsQ: "
				+ collectedDocsQ);
		String collectedDocs = ConnectionDAO.singleReturn(collectedDocsQ);
		logger.info("In ChildDocAction(execute) collectedDocs: "
				+ CommonFunction.checkNull(collectedDocs));
		if (!CommonFunction.checkNull(collectedDocs).equalsIgnoreCase("")) {
			String[] collects = collectedDocs.split("\\|");
			logger.info("In ChildDocAction(execute) collectedDocs: "
					+ collects);
			for (int i = 0; i < collects.length; i++) {
				logger.info("In ChildDocAction(execute) collectedDocs: "
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