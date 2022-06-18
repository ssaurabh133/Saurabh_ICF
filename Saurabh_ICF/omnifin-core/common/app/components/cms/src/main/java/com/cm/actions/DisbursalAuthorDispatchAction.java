
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

import com.cm.dao.DisbursalInitiationDAO;
//import com.cm.dao.DisbursalInitiationDAOImpl;
import com.cm.vo.DisbursalAuthorVO;
import com.cm.vo.DisbursalMakerVO;
import com.communication.engn.daoImplMySql.SmsDAOImpl;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;


public class DisbursalAuthorDispatchAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(DisbursalAuthorDispatchAction.class.getName());

	
	public ActionForward openDisbursalAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("Inside DisbursalAuthorDispatchAction........openDisbursalAuthor");
		
		HttpSession session =  request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		//Changes By Amit Starts
		String authorDate="";
		if(userobj!=null)
		{
			authorDate=userobj.getBusinessdate();
		}else{
			logger.info("here in  openDisbursalAuthor method of DisbursalAuthorDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		//Changes By Amit Ends
		
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
		
		//Changes By Amit Starts
		DisbursalAuthorVO vo = new DisbursalAuthorVO();
		vo.setAuthorDate(authorDate);
		String loanDisbursalId = session.getAttribute("loanDisbursalId").toString();
		vo.setLoanDisbursalId(loanDisbursalId);
		logger.info("loanDisbursalId: "+loanDisbursalId);
		//DisbursalInitiationDAO service = new DisbursalInitiationDAOImpl();
         DisbursalInitiationDAO service=(DisbursalInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(DisbursalInitiationDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass()); 	
		String disbParaAuthor = service.getDisbAuthorParameter(vo);
		request.setAttribute("disbAllowParameter",disbParaAuthor);
		//Changes By Amit Ends
		
		return mapping.findForward("openDisbursalAuthor");
	}
	
	
	public ActionForward saveDisbursalAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("Inside DisbursalAuthorDispatchAction......saveDisbursalAuthor");
		
		HttpSession session =  request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String authorId="";
		String authorDate="";
		int cID=0;
		if(userobj!=null)
		{
			authorId=userobj.getUserId();
			authorDate=userobj.getBusinessdate();
			cID=userobj.getCompanyId();
		}else{
			logger.info("here in saveDisbursalAuthor method of DisbursalAuthorDispatchAction action the session is out----------------");
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
		String status="";
	

	   
	    DynaValidatorForm DisbursedInitiationAuthorDynaValidatorForm = (DynaValidatorForm)form;
		DisbursalAuthorVO vo = new DisbursalAuthorVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,DisbursedInitiationAuthorDynaValidatorForm);
		vo.setAuthorId(authorId);
		vo.setAuthorDate(authorDate);
		vo.setCompanyId(cID);
		String loanDisbursalId = session.getAttribute("loanDisbursalId").toString();
		
		String batchId = CommonFunction.checkNull(session.getAttribute("batchId"));
		vo.setLoanDisbursalId(loanDisbursalId);
		vo.setDisbursalBatchId(batchId);
		logger.info("loanDisbursalId: "+loanDisbursalId);
		logger.info("company Id: "+vo.getCompanyId());

		//CreditManagementDAO service = new CreditManagementDAOImpl();
		
		//DisbursalInitiationDAO service = new DisbursalInitiationDAOImpl();
DisbursalInitiationDAO service=(DisbursalInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(DisbursalInitiationDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass()); 
		status = service.saveDisbursalAuthor(vo);
		if(status.equalsIgnoreCase("S"))
		{
			request.setAttribute("message","S");
			//Hina Changes Starts for SMS & EMAIL
			
			String EventName="Disbursement";
			boolean communicationStatus=false;
			String query="Select count(1) from comm_event_list_m where event_Name='"+EventName+"' and rec_status='A' ";
			int count=Integer.parseInt(ConnectionDAO.singleReturn(query));
			if(count!=0)
			{
			 communicationStatus=new SmsDAOImpl().getEmailDetails(vo.getLoanId(),authorDate,EventName);
			}
			else
			{
				logger.info("SMS & EMAIL Event not Active on Disbursement ");
			}
			logger.info("communicationStatus:::"+communicationStatus);
			
			 EventName="Disbursement_INTERNAL";
			 communicationStatus=false;
			 query="Select count(1) from comm_event_list_m where event_Name='"+EventName+"' and rec_status='A' ";
			 count=Integer.parseInt(ConnectionDAO.singleReturn(query));
			if(count!=0)
			{
			 communicationStatus=new SmsDAOImpl().getEmailDetails(vo.getLoanId(),authorDate,EventName);
			}
			else
			{
				logger.info("SMS & EMAIL Event not Active on Disbursement_INTERNAL ");
			}
			logger.info("communicationStatus:::"+communicationStatus);
			//Hina Changes end for SMS & EMAIL
			session.removeAttribute("disbursalInitionAuthor");
		}
		else
		{
			request.setAttribute("message","E");
			request.setAttribute("status",status);
		}
		session.removeAttribute("loanId");
		session.removeAttribute("disbursalNo");
		session.removeAttribute("loanDisbursalId");
		session.removeAttribute("disbursalDataAuthor");
		return mapping.findForward("saveDisbursalAuthorData");
	}
	
	public ActionForward openDisbursalValuesAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("Inside DisbursalAuthorDispatchAction........openDisbursalValuesAuthor");
		
		HttpSession session =  request.getSession();
		boolean flag=false;
// bussiness date is added by prashant
		String businessDate="";
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj!=null){
			businessDate=userobj.getBusinessdate();
		}
		else{
			logger.info("here in openDisbursalValuesAuthor method of DisbursalAuthorDispatchAction action the session is out----------------");
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
		String loanId = CommonFunction.checkNull(session.getAttribute("loanId"));
		String disbursalNo = CommonFunction.checkNull(session.getAttribute("disbursalNo"));
		

		//CreditManagementDAO service = new CreditManagementDAOImpl();

		//DisbursalInitiationDAO service = new DisbursalInitiationDAOImpl();
         DisbursalInitiationDAO service=(DisbursalInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(DisbursalInitiationDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass()); 		

        String bp_type = ConnectionDAO.singleReturn("SELECT DISBURSAL_TO FROM CR_LOAN_DISBURSAL_DTL WHERE LOAN_ID="+loanId+" AND DISBURSAL_NO="+disbursalNo+" ");
		//ArrayList<DisbursalMakerVO> disbursalDataAuthor = service.selectDisbursalData(loanId,disbursalNo,businessDate,bp_type);
		DisbursalMakerVO vo1 = new DisbursalMakerVO();
		ArrayList<DisbursalMakerVO> cycleDate = service.getCycleDateList();
		session.setAttribute("cycleDate",cycleDate);
		
		//ArrayList<DisbursalMakerVO> disbursalDataAuthor = service.selectDisbursalData(loanId,disbursalNo,bussinessDate,bp_type);
		//
		//vo1=(DisbursalMakerVO) disbursalDataAuthor.get(0);
		//logger.info("Disbursal Flag from Dispatch Action......openDisbursalValuesAuthor: "+vo1.getFinalDisbursal());
		//	logger.info("vo1.getDisbursalTo()......................... "+vo1.getDisbursalTo());
		//session.setAttribute("disbursalTo", vo1.getDisbursalTo());
		
		//session.removeAttribute("loanDisbursalId");
		session.removeAttribute("disbursalPaymentAddDtl");
		session.removeAttribute("disbursalDataAuthor");
		//session.setAttribute("fianlDisb",vo1.getFinalDisbursal());
		//session.setAttribute("disbursalDataAuthor", disbursalDataAuthor);
	     
		logger.info("loanDisbursalId: "+session.getAttribute("loanDisbursalId"));
		vo1.setLoanDisbursalId(CommonFunction.checkNull(session.getAttribute("loanDisbursalId")));
		vo1.setDisbursalBatchId(CommonFunction.checkNull(session.getAttribute("batchId"))); 
		vo1.setLbxLoanNoHID(loanId);
		ArrayList disbursalPaymentAddDtl=service.selectAddDetailsList(vo1,"A");
		session.setAttribute("disAuthor","disAuthor");
		logger.info("vo1.getDisbursalTo().............size............ "+disbursalPaymentAddDtl.size());
		if(disbursalPaymentAddDtl.size()>0){
			session.setAttribute("disbursalPaymentAddDtl", disbursalPaymentAddDtl);	
			
		}
		
		
		return mapping.findForward("showDisbursalDataAuthor");
	}
}