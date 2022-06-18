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

import com.cm.dao.RepricingDAO;
import com.cm.vo.PartPrePaymentAuthorVO;
import com.connect.DaoImplInstanceFactory;
import com.logger.LoggerMsg;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.communication.engn.daoImplMySql.SmsDAOImpl;

public class RepricingAuthorDispatchAction extends DispatchAction{

	private static final Logger logger = Logger.getLogger(RepricingAuthorDispatchAction.class.getName());
	
	public ActionForward showRepricingDataAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		LoggerMsg.info("Inside ........showRepricingDataAuthor");
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in showRepricingDataAuthor method of RepricingAuthorDispatchAction action the session is out----------------");
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
		session.getAttribute("rePricingDataAuthor");
		session.getAttribute("loanId");
		session.getAttribute("reschId");
		return mapping.findForward("showRepricingDataAuthor");
	}
	
	public ActionForward openRepricingAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("Inside ........openRepricingAuthor");
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId ="";
		String businessDate ="";
		int compid =0;
		if(userobj!=null){
			userId = userobj.getUserId();
				businessDate=userobj.getBusinessdate();
				compid=userobj.getCompanyId();
		}else{
			logger.info("here in openRepricingAuthor method of RepricingAuthorDispatchAction action the session is out----------------");
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
		RepricingDAO dao=(RepricingDAO)DaoImplInstanceFactory.getDaoImplInstance(RepricingDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());
		String reschId=(session.getAttribute("reschId")).toString();
		String makerDate=dao.getMakerDate(reschId);
		request.setAttribute("businessDate",businessDate);
		request.setAttribute("makerDate",makerDate);
		return mapping.findForward("openRepricingAuthor");
	}
	
	public ActionForward saveRepricingAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("Inside ........saveRepricingAuthor");
		
		HttpSession session =  request.getSession();
		
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId ="";
		String businessDate ="";
		int compid =0;
		if(userobj!=null){
			userId = userobj.getUserId();
				businessDate=userobj.getBusinessdate();
				compid=userobj.getCompanyId();
		}else{
			logger.info("here in saveRepricingAuthor method of RepricingAuthorDispatchAction action the session is out----------------");
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
		
		session.getAttribute("loanId");
		session.getAttribute("reschId");
		
		String status = "";
		DynaValidatorForm PartPrePaymentAuthorDynaValidatorForm = (DynaValidatorForm)form;
		PartPrePaymentAuthorVO vo = new PartPrePaymentAuthorVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,PartPrePaymentAuthorDynaValidatorForm);

		
		vo.setAuthorId(userId);
		vo.setAuthorDate(businessDate);
		vo.setCompanyId(compid);
		RepricingDAO dao=(RepricingDAO)DaoImplInstanceFactory.getDaoImplInstance(RepricingDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());
		status = dao.saveRepricingAuthor(vo);
		if(status.equalsIgnoreCase("S"))
		{
			request.setAttribute("message","S");
			//Hina Changes Starts for SMS & EMAIL
			String qur1="SELECT count(1) FROM CR_LOAN_DTL where loan_id='"+vo.getLoanId()+"' ";
			int cnt1 = Integer.parseInt(ConnectionDAO.singleReturn(qur1));
			if(cnt1!=0)
			{
			String qur="SELECT count(1) FROM CR_RESCH_DTL where NEW_RATE<>OLD_RATE and loan_id='"+vo.getLoanId()+"' ";
			int cnt = Integer.parseInt(ConnectionDAO.singleReturn(qur));
			if(cnt!=0)
			{
			String EventName="Interest_Rate_Change";
			boolean communicationStatus=false;
			String query="Select count(1) from comm_event_list_m where event_Name='"+EventName+"' and rec_status='A' ";
			int count=Integer.parseInt(ConnectionDAO.singleReturn(query));
			if(count!=0)
			{
			 communicationStatus=new SmsDAOImpl().getEmailDetails(vo.getLoanId(),businessDate,EventName);
			}
			else
			{
				logger.info("SMS & EMAIL Event not Active on Interest_Rate_Change ");
			}
			logger.info("communicationStatus:::"+communicationStatus);
		}
			}
			//Hina Changes end for SMS & EMAIL
		}
		
		else
		{
			request.setAttribute("message","E");
			request.setAttribute("status",status);
		}
		return mapping.findForward("saveRepricingAuthor");
	}
}
