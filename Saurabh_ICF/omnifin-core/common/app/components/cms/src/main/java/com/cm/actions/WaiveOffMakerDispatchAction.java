package com.cm.actions;

import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.*;
import org.apache.struts.actions.*;
import org.apache.struts.validator.DynaValidatorForm;
import com.cm.dao.WaiveOffDAO;
import com.cm.vo.WaiveOffVO;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.logger.LoggerMsg;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;


public class  WaiveOffMakerDispatchAction extends DispatchAction
{
	private static final Logger logger = Logger.getLogger(WaiveOffMakerDispatchAction.class.getName());
	
	public ActionForward waiveOffMakerCSESave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		logger.info("in WaiveOffMakerCSESave.......Implementation.... ");
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String bDate ="";
		if(userobj!=null){
			userId= userobj.getUserId();
			bDate=userobj.getBusinessdate();
		}else{
			logger.info("in  WaiveOffMakerCSESave method of WaiveOffMakerDispatchAction action the session is out----------------");
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
		if(request.getParameter("waveOffId")!=null || !request.getParameter("waveOffId").equalsIgnoreCase(""))
		{
			vo.setWaveOffId(request.getParameter("waveOffId"));
		}
		if(request.getParameter("htxnAdviceId")!=null || !request.getParameter("htxnAdviceId").equalsIgnoreCase(""))
		{
			vo.setTxnAdviceId(request.getParameter("htxnAdviceId"));
			session.setAttribute("txnId",request.getParameter("htxnAdviceId"));
		}
		String ne="";
		if(request.getParameter("new")!=null)
		{
			ne=request.getParameter("new");
		}
		vo.setN(ne);

	    
	    userId=CommonFunction.checkNull(userId);
		vo.setMakerID(""+userId);
		vo.setUserId(userId);
		vo.setMakerDate(bDate);
		logger.info("maker date in WaiveOffMakerDispatchAction .................... "+bDate);
		ArrayList temp= service.saveWaiveOffMaker(vo,request);	
		
		LoggerMsg.info("temp.********************.size /////.................... "+temp.size());
//		LoggerMsg.info("temp............size /////.................... "+temp.get(0).toString());
//		
		request.setAttribute("save", "save");
		String msg="";
		
		//LoggerMsg.info("temovo flag ......................... "+tempVo.getFlag());
		if(temp.size()>0)
		{
		if(temp.get(0).toString().equalsIgnoreCase("I"))
		{
			String loanId = CommonFunction.checkNull(request.getParameter("loanId"));
			String bp_id = CommonFunction.checkNull(request.getParameter("bpid"));
			String waveOffId = CommonFunction.checkNull(request.getParameter("waveOffId"));
			request.setAttribute("msg", "S");
			ArrayList WaiveOffData = service.selectWaiveOffData(vo.getLbxLoanNoHID(),vo.getBusinessPartnerType(),temp.get(1).toString());
		    
			logger.info("List Size is .................... "+WaiveOffData.size());
		   
			
		    request.setAttribute("WaiveOffData", WaiveOffData);	
	
		}
		else if(temp.get(0).toString().equalsIgnoreCase("U"))
		{
			String loanId = CommonFunction.checkNull(request.getParameter("loanId"));
			String bp_id = CommonFunction.checkNull(request.getParameter("bpid"));
			String waveOffId = CommonFunction.checkNull(request.getParameter("waveOffId"));
			request.setAttribute("msg", "S");
			ArrayList WaiveOffData = service.selectWaiveOffData(vo.getLbxLoanNoHID(),vo.getBusinessPartnerType(),temp.get(1).toString());
		    
			request.setAttribute("msg", "U");
			request.setAttribute("WaiveOffData", WaiveOffData);
			
		}
		else if(temp.get(0).toString().equalsIgnoreCase("N"))
		{
			request.setAttribute("msg", "N");
		}
		else
		{
			request.setAttribute("msg", "P");
		
		}
		}
		if(session.getAttribute("txnId")!=null)
		{
			 logger.info("session .txnId................... "+session.getAttribute("txnId").toString());
			request.setAttribute("txnId", session.getAttribute("txnId").toString());
			session.removeAttribute("txnId");
		}
		//return mapping.findForward("waiveOffMakerCSEImplSave");
		return mapping.getInputForward();
	}
		
	
	public ActionForward waiveOffMakerCSESaveForword(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		logger.info("in WaiveOffMakerCSESaveAndForword.......Implementation.... ");
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
	    String userId="";
	    String lastLtime ="";
		if(userobj!=null){
			userId= userobj.getUserId();
			lastLtime=userobj.getLastLoginTime();
		}else{
			logger.info("in WaiveOffMakerCSESaveAndForword method of  WaiveOffMakerDispatchAction action the session is out----------------");
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
		
//		logger.info("total waive off ******************************************** "+vo.getTotalWaveOff());
//		logger.info("total waive off ******************************************** "+vo.getTotalWaiveOffAmt());
//		logger.info("total waive off ******************************************** "+vo.getTotalWaiveOffAmt());
		if(request.getParameter("waveOffId")!=null)
		{
			vo.setWaveOffId(request.getParameter("waveOffId"));
		}
		//logger.info("txn advice id .................)))))))))))))))))))))))) "+request.getParameter("htxnAdviceId"));
		if(request.getParameter("htxnAdviceId")!=null)
		{
			vo.setTxnAdviceId(request.getParameter("htxnAdviceId"));
		}
		

		vo.setMakerID(""+userId);
		vo.setMakerDate(lastLtime);
		boolean save= service.saveAndForwordWaiveOffMaker(vo);	
		
		request.setAttribute("save", save);	
        String msg="";		
		if(save)
		{
			request.setAttribute("msg", "SF");
			logger.info("........save..And..forword....");
		}
		else
		{
			request.setAttribute("msg", "E");
			logger.info("...Not.....Save And Forword........");
		}
		
		return mapping.findForward("waiveOffMakerCSEImplSaveandforword");
	}
	//Ritu
	public ActionForward deleteWaiveoff(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		logger.info("In WaiveOffMakerBehindAction deleteWaiveOff().... ");
		HttpSession session = request.getSession();
		UserObject userobj = (UserObject) session.getAttribute("userobject");
		String userId="";
		String bDate="";
		String branchId="";
		//String result="success";
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
				branchId=userobj.getBranchId();
		}else{
			logger.info("here in deleteWaiveOff () of WaiveOffMakerBehindAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		String sessionId = session.getAttribute("sessionID").toString();

		// String cond = request.getParameter("saveForward");
		// logger.info("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+cond);

		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
		
	
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

		String waveOffId = "";

		waveOffId = request.getParameter("waveOffId");
		
		logger.info("In   deleteWaiveOff ----1-------------->waveOffId " + waveOffId);
		//logger.info("In ConsumerDispatchAction  execute id: " + userobj.getBranchId());

		
		//PaymentDAO service = new PaymentDAOImpl();
		WaiveOffDAO service=(WaiveOffDAO)DaoImplInstanceFactory.getDaoImplInstance(WaiveOffDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());	

		
		boolean status = service.deleteWaiveoff(waveOffId);
		String msg="";
		if(status){
        	msg="DS";
			request.setAttribute("msg",msg);
			 
		}
		else{
			msg="DN";
			request.setAttribute("msg",msg);
		}
       
		return mapping.findForward("waiveOffMakerCSEImplSave");
	}
	
}
