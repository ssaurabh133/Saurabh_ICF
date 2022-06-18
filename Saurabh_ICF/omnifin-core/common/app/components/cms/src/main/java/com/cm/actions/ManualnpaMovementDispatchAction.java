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

import com.cm.dao.ManualnpaMovementDAO;

import com.cm.vo.ManualnpaMovementVO;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;

import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class ManualnpaMovementDispatchAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(ManualnpaMovementDispatchAction.class.getName());
	//ManualnpaMovementDAO dao = new ManualnpaMovementDAOImpl();
	ManualnpaMovementDAO dao=(ManualnpaMovementDAO)DaoImplInstanceFactory.getDaoImplInstance(ManualnpaMovementDAO.IDENTITY);


	//KANIKA FOR NEW MANUL NPA MAKER
	public ActionForward openNewManualNpa(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Implementation class: "+dao.getClass()); 

		logger.info("Inside openNewManualNpa...........");
		logger.info("::::::::::::::::In EarlyClosureDAOImpl");


		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in openNewManualNpa ()---------------");
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

//		String type2= CommonFunction.checkNull(request.getAttribute("type2"));
		ArrayList list = dao.getNpaStage();
		request.setAttribute("npaStage", list);
		request.setAttribute("maker", "maker");
		request.setAttribute("statusflag", "save");
		
		return mapping.findForward("success");
	}

	public ActionForward retriveDetailsforLoan(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		logger.info("In retriveDetailsforLoan()------------>");

		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String companyId="";
		if(userobj!=null)
		{
			companyId=userobj.getCompanyId()+"";
		}else{
			logger.info("here in retriveDetailsforLoan ()--------------");
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


		String loanId = CommonFunction.checkNull(request.getParameter("loanId"));

		logger.info("Inside retriveDetailsforLoan... loan ID: "+loanId);

		ArrayList<ManualnpaMovementVO> list= dao.detailsForLoan(loanId);
		logger.info("Inside retriveDetailsforLoan()------size"+list.size());
		request.setAttribute("list", list);
		return mapping.findForward("loanajaxsuccess");
	}


	//KANIKA FOR INSERT IN MANUAL NPA MOVEMENT MAKER
	public ActionForward saveManualNPA(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Inside EarlyClosureDispatchAction...........saveClosureDetails");

		HttpSession session =  request.getSession();

		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String makerId="";
		String bDate ="";
		String nparesult="";
		String val="";
		String message="";
		if(userobj!=null){
			makerId= userobj.getUserId();
			bDate=userobj.getBusinessdate();
		}else{
			logger.info("here in saveClosureDetails method of EarlyClosureDispatchAction action the session is out----------------");
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

		DynaValidatorForm ManualNpaMovementAddDynaValidatorForm=(DynaValidatorForm)form;
		String loanNo=ManualNpaMovementAddDynaValidatorForm.getString("loanNo");
		String lbxLoanNo=ManualNpaMovementAddDynaValidatorForm.getString("lbxLoanNo");
		String customerName1=ManualNpaMovementAddDynaValidatorForm.getString("customerName");
		String product=ManualNpaMovementAddDynaValidatorForm.getString("product");
		String scheme=ManualNpaMovementAddDynaValidatorForm.getString("scheme");
		
		ManualnpaMovementVO vo = new ManualnpaMovementVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, ManualNpaMovementAddDynaValidatorForm);
		logger.info(vo.getLbxLoanNo());
		logger.info("customer name : "+request.getParameter("customerName"));
		logger.info("vo.getLoanNo()........."+vo.getLoanNo());
		
		logger.info("scheme"+request.getParameter("scheme"));
		logger.info("Product"+vo.getProduct());

		
		vo.setMakerId(makerId);
		vo.setMakerDate(bDate);
		
		
		
		String loanId=vo.getLbxLoanNo();
		String customerName=vo.getCustomerName();
		logger.info("-------getApprovalDate------------>"+vo.getApprovalDate());
		ArrayList manualNpaIdlist= dao.insertManualNPA(vo);		
		if(manualNpaIdlist.size()>0)
		{
			nparesult=CommonFunction.checkNull(manualNpaIdlist.get(0));
			val=CommonFunction.checkNull(manualNpaIdlist.get(1));
		
		logger.info("manualNpaId......."+val);
		
		ArrayList list1 = dao.getNpaStage();
		request.setAttribute("npaStage", list1);
		
		if(nparesult.equalsIgnoreCase("saved"))
		{
			logger.info("Data Saved Successfuly-------------manualNpaId-->"+val);
			request.setAttribute("manualNpaId", val);
			ArrayList<ManualnpaMovementVO> list = dao.selectManualNpa(val,loanId);
			request.setAttribute("list", list);
			request.setAttribute("statusflag", "update");
			request.setAttribute("forwardflag", "forward");
			message="S";
			request.setAttribute("getdetailsval", "yes");

		}else if(nparesult.equalsIgnoreCase("error")){
			logger.info("Data Not Saved.");
			message="E";
			request.setAttribute("statusflag", "save");
			ArrayList<ManualnpaMovementVO> list=(ArrayList<ManualnpaMovementVO>) manualNpaIdlist.get(2);
		request.setAttribute("list", list);
		}
		else{
			logger.info("else.........");
			//request.setAttribute("loanId", loanId);
			request.setAttribute("manualNpaMsg", nparesult);
			ArrayList<ManualnpaMovementVO> list = new ArrayList();
//			vo.setLbxLoanNo(request.getParameter("lbxLoanNo"));
//			vo.setCustomerName(request.getParameter("customerName"));
//			vo.setProduct(request.getParameter("product"));
//			vo.setScheme(request.getParameter("scheme"));
			
			list.add(vo);
			//String lbxLoanNo = request.getParameter("lbxLoanNo");
		
			request.setAttribute("maker", "maker");
			request.setAttribute("list", list);
			request.setAttribute("getdetailsval", "yes");
			request.setAttribute("statusflag", "save");
		}
		}
		else
		{
			message="E";
		}

	request.setAttribute("maker", "maker");
	request.setAttribute("message",message);
	return mapping.findForward("success");
	}
	public ActionForward updateManualNPA(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Inside updateManualNPA..........");

		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String makerId="";

		String bDate ="";
		if(userobj!=null){
			makerId= userobj.getUserId();
			bDate=userobj.getBusinessdate();
		}else{
			logger.info("here in updateManualNPA method of the session is out----------------");
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


		DynaValidatorForm ManualNpaMovementAddDynaValidatorForm=(DynaValidatorForm)form;
		ManualnpaMovementVO vo = new ManualnpaMovementVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, ManualNpaMovementAddDynaValidatorForm);

		vo.setMakerId(makerId);
		vo.setMakerDate(bDate);
		String manualNpaId=CommonFunction.checkNull(request.getParameter("manualNpaId"));
		boolean status=false;
		String message="";
		vo.setManualnpaId(manualNpaId);
		String loanId=vo.getLbxLoanNo();
		request.setAttribute("loanId", loanId);
		request.setAttribute("manualNpaId", manualNpaId);
			String stageresult= dao.updateManualNPA(vo);
			ArrayList list1 =dao.getNpaStage();
			request.setAttribute("npaStage", list1);
			 if	(stageresult.equalsIgnoreCase("saved"))
			{
				logger.info("Data Saved.");
				message="M";
				request.setAttribute("manualNpaId", manualNpaId);
				request.setAttribute("loanId", loanId);
				ArrayList<ManualnpaMovementVO> list = dao.selectManualNpa(manualNpaId,loanId);
				request.setAttribute("maker", "maker");
				request.setAttribute("list", list);
				request.setAttribute("statusflag", "update");
				request.setAttribute("forwardflag", "forward");
				request.setAttribute("getdetailsval", "yes");
				request.setAttribute("message", message);
			}else if(stageresult.equalsIgnoreCase("notsaved")){
				message="E";
				request.setAttribute("manualNpaId", manualNpaId);
				request.setAttribute("loanId", loanId);
				ArrayList<ManualnpaMovementVO> list = dao.selectManualNpa(manualNpaId,loanId);
				request.setAttribute("maker", "maker");
				request.setAttribute("list", list);
				request.setAttribute("statusflag", "update");
				request.setAttribute("message", message);
			}else{
				logger.info("-----manualNpaId-->"+manualNpaId);
				logger.info("loanId-->"+loanId);
				request.setAttribute("loanId", loanId);
				request.setAttribute("manualNpaId", manualNpaId);
				request.setAttribute("stageresult", stageresult);
				ArrayList<ManualnpaMovementVO> list = dao.selectManualNpa(manualNpaId,loanId);
				request.setAttribute("maker", "maker");
				request.setAttribute("list", list);
				request.setAttribute("statusflag", "update");
			}
		
	
		return mapping.findForward("success");
	}
	
	public ActionForward deleteManualNPA(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	logger.info("Inside updateManualNPA..........");

	HttpSession session =  request.getSession();
	boolean flag=false;
	UserObject userobj=(UserObject)session.getAttribute("userobject");
	String makerId="";

	String bDate ="";
	if(userobj!=null){
		makerId= userobj.getUserId();
		bDate=userobj.getBusinessdate();
	}else{
		logger.info("here in updateManualNPA method of the session is out----------------");
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


	DynaValidatorForm ManualNpaMovementAddDynaValidatorForm=(DynaValidatorForm)form;
	ManualnpaMovementVO vo = new ManualnpaMovementVO();
	org.apache.commons.beanutils.BeanUtils.copyProperties(vo, ManualNpaMovementAddDynaValidatorForm);

	vo.setMakerId(makerId);
	vo.setMakerDate(bDate);
	String manualNpaId=CommonFunction.checkNull(request.getParameter("manualNpaId"));
	boolean status=false;
	String message="";
	vo.setManualnpaId(manualNpaId);
		 status= dao.deleteManualNPA(vo);
		if(status)
		{
		logger.info("Data deleted.");
			message="D";
		}else{
			message="DE";
		}

		request.setAttribute("maker", "maker");

		request.setAttribute("statusflag", "update");


	request.setAttribute("message", message);
	return mapping.findForward("success");
	}
	public ActionForward forwardManualNPA(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Inside forwardManualNPA..........");

		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String makerId="";

		String bDate ="";
		if(userobj!=null){
			makerId= userobj.getUserId();
			bDate=userobj.getBusinessdate();
		}else{
			logger.info("here in forwardManualNPA method of the session is out----------------");
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


		DynaValidatorForm ManualNpaMovementAddDynaValidatorForm=(DynaValidatorForm)form;
		ManualnpaMovementVO vo = new ManualnpaMovementVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, ManualNpaMovementAddDynaValidatorForm);

		vo.setMakerId(makerId);
		vo.setMakerDate(bDate);
		String manualNpaId=CommonFunction.checkNull(request.getParameter("manualNpaId"));
		boolean status=false;
		String message="";
		vo.setManualnpaId(manualNpaId);
		String loanId=vo.getLbxLoanNo();
			status= dao.forwardManualNPA(vo);
			if(status)
			{
				logger.info("-----manualNpaId-->"+manualNpaId);
				logger.info("loanId-->"+loanId);
				ArrayList<ManualnpaMovementVO> list = dao.selectManualNpa(manualNpaId,loanId);
				request.setAttribute("maker", "maker");
				request.setAttribute("list", list);
				message="F";

			}
			else{
				logger.info("Data Not Saved.");
				message="E";
			}
			request.setAttribute("message",message);
		return mapping.findForward("success");
	}
	public ActionForward editjsp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String manualNpaId=request.getParameter("manualNpaId");
		String loanId=request.getParameter("loanId");
		request.setAttribute("manualNpaId", manualNpaId);
	
		ArrayList<ManualnpaMovementVO> list = dao.selectManualNpa(manualNpaId,loanId);
		request.setAttribute("maker", "maker");
		request.setAttribute("statusflag", "update");
		request.setAttribute("forwardflag", "forward");
		request.setAttribute("list", list);
		request.setAttribute("getdetailsval", "yes");
		//Ritu
		ArrayList list1 = dao.getNpaStage();
		request.setAttribute("npaStage", list1);
		return mapping.findForward("success");
		
	}
	public ActionForward retriveGetDetailsValues(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		logger.info("In retriveGetDetailsValues()------------>");

		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String companyId="";
		if(userobj!=null)
		{
			companyId=userobj.getCompanyId()+"";
		}else{
			logger.info("here in retriveGetDetailsValues ()--------------");
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
		


		DynaValidatorForm ManualNpaMovementAddDynaValidatorForm=(DynaValidatorForm)form;
				String loanNo=ManualNpaMovementAddDynaValidatorForm.getString("loanNo");
				String lbxLoanNo=ManualNpaMovementAddDynaValidatorForm.getString("lbxLoanNo");
				String customerName1=ManualNpaMovementAddDynaValidatorForm.getString("customerName");
				String product=ManualNpaMovementAddDynaValidatorForm.getString("product");
				String scheme=ManualNpaMovementAddDynaValidatorForm.getString("scheme");

		String loanId = CommonFunction.checkNull(request.getParameter("loanId"));
		logger.info("loanNo"+request.getParameter("loanNo"));
		logger.info("lbxLoanNo"+request.getParameter("lbxLoanNo"));
		logger.info("customerName"+request.getParameter("customerName"));
		logger.info("Inside retriveGetDetailsValues... loan ID: "+loanId);

		ArrayList<ManualnpaMovementVO> list= dao.ManualNpaGetDetails(loanId);
		logger.info("Inside retriveGetDetailsValues()------size"+list.size());
		request.setAttribute("list", list);

		return mapping.findForward("ajaxsuccess");
	}


}
