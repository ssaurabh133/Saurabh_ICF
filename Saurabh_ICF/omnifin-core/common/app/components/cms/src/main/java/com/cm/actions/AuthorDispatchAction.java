/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.cm.actions;

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
import com.cm.dao.EarlyClosureDAO;
//import com.cm.dao.EarlyClosureDAOImpl;
import com.cm.vo.ClosureAuthorVO;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.communication.engn.daoImplMySql.SmsDAOImpl;
/** 
 * MyEclipse Struts
 * Creation date: 03-15-2011
 * 
 * XDoclet definition:
 * @struts.action path="/author" name="Author" input="/JSP/CMJSP/author.jsp" scope="request" validate="true"
 */
public class AuthorDispatchAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(AuthorDispatchAction.class.getName());
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
	public ActionForward saveClosureAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId ="";
		String bDate ="";
		int cID =0;
		if(userobj!=null){
			userId = userobj.getUserId();
			bDate=userobj.getBusinessdate();
			cID=userobj.getCompanyId();
		}else{
			logger.info("here in saveClosureAuthor method of AuthorDispatchAction action the session is out----------------");
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
		DynaValidatorForm AuthorDynaValidatorForm = (DynaValidatorForm) form;
		ClosureAuthorVO vo = new ClosureAuthorVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, AuthorDynaValidatorForm);
		logger.info("Inside Author Dispatch Action");
		String loanId= vo.getLoanId();
		logger.info("Inside Author Dispatch Action.........loan id: "+loanId);

		vo.setCompanyId(cID);
		vo.setAuthorId(userId);
		vo.setAuthorDate(bDate);
		logger.info("Inside Author Dispatch Action.........Author id: "+vo.getAuthorId());
		logger.info("Inside Author Dispatch Action.........Decision: "+vo.getDecision());
		//CreditManagementDAO service = new CreditManagementDAOImpl();
		EarlyClosureDAO service=(EarlyClosureDAO)DaoImplInstanceFactory.getDaoImplInstance(EarlyClosureDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());
		String status="";
		status=service.saveClosureAuthor(vo);
		logger.info("Status from AuthorDispatchAction: "+status);
		if(status.equalsIgnoreCase("S"))
		{
			request.setAttribute("message","S");
			//Hina Changes for SMS & EMAIL
			
			if(vo.getClosureStatus().equalsIgnoreCase("T") || vo.getClosureStatus().equalsIgnoreCase("C")){
				//First msg for Loan Closure starts
				String EventName="Loan_Closure1";
				boolean communicationStatus=false;
				String query="Select count(1) from comm_event_list_m where event_Name='"+EventName+"' and rec_status='A' ";
				int count=Integer.parseInt(ConnectionDAO.singleReturn(query));
				if(count!=0)
				{
				 communicationStatus=new SmsDAOImpl().getEmailDetails(vo.getLoanId(),bDate,EventName);
				}
				else
				{
					logger.info("SMS & EMAIL Event not Active on Loan_Closure1 ");
				}
				logger.info("Loan_Closure1:::"+communicationStatus);
				//First msg for Loan Closure end
				//Second msg for Loan Closure Starts
				String EventName1="Loan_Closure2";
				boolean communicationStatus1=false;
				String query1="Select count(1) from comm_event_list_m where event_Name='"+EventName1+"' and rec_status='A' ";
				int count1=Integer.parseInt(ConnectionDAO.singleReturn(query1));
				if(count1!=0)
				{
				 communicationStatus1=new SmsDAOImpl().getEmailDetails(vo.getLoanId(),bDate,EventName1);
				}
				else
				{
					logger.info("SMS & EMAIL Event not Active on Loan_Closure2 ");
				}
				logger.info("Loan_Closure2:::"+communicationStatus1);
				//Second msg for Loan Closure Starts
				
				String EventName2="Loan_Closure_Internal";
				boolean communicationStatus2=false;
				String query2="Select count(1) from comm_event_list_m where event_Name='"+EventName2+"' and rec_status='A' ";
				int count2=Integer.parseInt(ConnectionDAO.singleReturn(query2));
				if(count2!=0)
				{
				 communicationStatus2=new SmsDAOImpl().getEmailDetails(vo.getLoanId(),bDate,EventName2);
				}
				else
				{
					logger.info("SMS & EMAIL Event not Active on Loan_Closure_Internal ");
				}
				logger.info("Loan_Closure1:::"+communicationStatus);
					//Hina Changes end for SMS & EMAIL
		}
			
			}
		else
		{
			request.setAttribute("message","E");
			request.setAttribute("status",status);
		}
		return mapping.findForward("closureAuthorSaved");
	}

}