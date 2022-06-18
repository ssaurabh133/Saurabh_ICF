package com.cm.actions;

import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.*;
import org.apache.struts.actions.*;
import org.apache.struts.validator.DynaValidatorForm;
import com.cm.dao.WaiveOffDAO;
import com.cm.vo.WaiveOffVO;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;


public class waiveOffAuthorDispatchAction extends DispatchAction
{
	private static final Logger logger = Logger.getLogger(waiveOffAuthorDispatchAction.class.getName());
	public ActionForward saveWaiveOffAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		logger.info("in saveWaiveOffAuthor.......Implementation.... ");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String AuthorId ="";
		String businessDate ="";
		int compid =0;
		if(userobj!=null){
			AuthorId = userobj.getUserId();
				businessDate=userobj.getBusinessdate();
				compid=userobj.getCompanyId();
		}else{
			logger.info("in saveWaiveOffAuthor method of  waiveOffAuthorDispatchAction saveActionCodeDetails action the session is out----------------");
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
		
		DynaValidatorForm WaiveOffMakerDynaValidatorForm= (DynaValidatorForm)form;
		WaiveOffVO vo= new WaiveOffVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, WaiveOffMakerDynaValidatorForm);
		//CreditManagementDAO service=new CreditManagementDAOImpl();
		
		WaiveOffDAO service=(WaiveOffDAO)DaoImplInstanceFactory.getDaoImplInstance(WaiveOffDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());

		//vo.setAuthorDate(format.format(today));
	    vo.setAuthorDate(businessDate);
		if(session.getAttribute("waveOffId")!=null)
		{
			vo.setWaveOffId(session.getAttribute("waveOffId").toString());
			logger.info("waive Off id  in auther save............................. "+session.getAttribute("waveOffId").toString());
		}
	logger.info("waive Off id  in auther save............................. "+session.getAttribute("waveOffId").toString());
	
	   
		vo.setAuthorID(AuthorId);
		vo.setCompanyId(compid);
		String decision="";
		String remarks="";
		decision=request.getParameter("decision").trim();
		remarks=request.getParameter("remarks").trim();
		vo.setDecision(decision);
		vo.setRemarks(remarks);
		logger.info("in saveWaiveOffAuthor.......Implementation...decision. "+decision);
		String msg="";
	    String update="";
	    //boolean update1= false;
//	 	if(decision.equalsIgnoreCase("A"))
//	 	{
//	 		logger.info("Approved Decision .................................... ");
	 		update = service.saveWaiveOffAuthor(vo);
	 		session.removeAttribute("waveOffId");
	 		
			logger.info("value of update ................... "+update);
			if(update.equalsIgnoreCase("S"))
			{
				request.setAttribute("msg", "S");
				logger.info("in saveWaiveOffAuthor.......Implementation...updated. "+update);
			}
			else
			{
				request.setAttribute("msg", "E");
				request.setAttribute("errorMsg", update.toString());
				logger.info("in saveWaiveOffAuthor.......Implementation...Error Not updated. "+update);
			}
	 	//}
//	 	else
//	 	{
//	 		update1=service.rejectWaiveOffAuthor(vo);
//	 		update1 = false;
//	 		request.setAttribute("msg", "R");
//	 	}
//		
		
       
		
		return mapping.findForward("saveWaiveOffAuthorPage");
	}
	
	

	
}
