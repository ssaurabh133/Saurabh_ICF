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
import com.cm.dao.CancellationDAO;
import com.cm.dao.EarlyClosureDAO;
import com.cm.vo.CancellationVO;
import com.cm.vo.ClosureVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class EarlyClosureDispatchAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(EarlyClosureDispatchAction.class.getName());	
	
	public ActionForward saveClosureDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Inside EarlyClosureDispatchAction...........saveClosureDetails");
		
		HttpSession session =  request.getSession();
		
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String makerId="";		
		String bDate ="";
		if(userobj!=null){
			makerId= userobj.getUserId();
			bDate=userobj.getBusinessdate();
		}else{
			logger.info("here in saveClosureDetails method of EarlyClosureDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		session.removeAttribute("closureNew");
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

		DynaValidatorForm ClosureDynaValidatorForm=(DynaValidatorForm)form;
		ClosureVO closureVo = new ClosureVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(closureVo, ClosureDynaValidatorForm);

		String type=closureVo.getClosureType();
		

		closureVo.setMakerId(makerId);
		closureVo.setMakerDate(bDate);

		String terminationId="";
		//CreditManagementDAO dao = new CreditManagementDAOImpl();
		EarlyClosureDAO dao=(EarlyClosureDAO)DaoImplInstanceFactory.getDaoImplInstance(EarlyClosureDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass()); 
		
		String checkPendingDataQuery="";
		
		checkPendingDataQuery="select count(*) from cr_termination_dtl where REC_STATUS in ('P','F') and TERMINATION_TYPE in ('C','T') and LOAN_ID="+closureVo.getLbxLoanNoHID();

		logger.info("check Pending Data Detail Query: "+checkPendingDataQuery);
		String checkPendingLoanStatus=ConnectionDAO.singleReturn(checkPendingDataQuery);
		String message="";
		if(CommonFunction.checkNull(checkPendingLoanStatus).equalsIgnoreCase("0"))
		{
			terminationId= dao.insertClosureData(closureVo);

			if(!terminationId.equalsIgnoreCase(""))
			{
				logger.info("Closure Data Saved Successfuly.");
				ArrayList<ClosureVO> closureData = dao.selectClosureData(closureVo.getLbxLoanNoHID(),terminationId);
				request.setAttribute("closureData", closureData);
				//Nishant Space Starts
				String netReceiveablePayable=dao.netReceivablePayableFlag();
				request.setAttribute("netReceiveablePayableF", netReceiveablePayable);
				//Nishant Space ends
				message="S";
				if(closureVo.getClosureType().equalsIgnoreCase("T"))
					request.setAttribute("earlyClosureLabel","earlyClosureLabel");
				if(closureVo.getClosureType().equalsIgnoreCase("C"))
					request.setAttribute("maturityClosureLabel","maturityClosureLabel");
			}else{
				logger.info("Closure Data Not Saved.");
				message="E";
			}
		}else{
			if(type.equals("T"))
			{
				session.removeAttribute("maturityClosureLabel");
				session.removeAttribute("closureDataDisabled");
				session.removeAttribute("closureData");
				session.removeAttribute("cancellationDataDisabled");
				session.setAttribute("closureNew","closureNew");
				request.setAttribute("earlyClosureLabel","earlyClosureLabel");
			}
			if(type.equals("C"))
			{
				session.removeAttribute("earlyClosureLabel");
				session.removeAttribute("closureDataDisabled");
				session.removeAttribute("closureData");
				session.removeAttribute("cancellationDataDisabled");
				session.setAttribute("closureNew","closureNew");
				request.setAttribute("maturityClosureLabel","maturityClosureLabel");
			}
			logger.info( "Data Already Exist.");
			message="EX";
		}
		request.setAttribute("message",message);
		logger.info( " Before findForward message::::::::::"+request.getAttribute("message"));
		return mapping.findForward("saveClosureDetails");
	}
	
	public ActionForward saveCancellationDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Inside EarlyClosureDispatchAction...........saveCancellationDetails");
		
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String makerId="";
		String bDate ="";
		if(userobj!=null){
			makerId= userobj.getUserId();
			bDate=userobj.getBusinessdate();
		}else{
			logger.info("here in saveCancellationDetails method of EarlyClosureDispatchAction action the session is out----------------");
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
		
		DynaValidatorForm CancellationMakerDynaValidatorForm=(DynaValidatorForm)form;
		CancellationVO vo = new CancellationVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, CancellationMakerDynaValidatorForm);
		
		//change by sachin
		CancellationDAO dao=(CancellationDAO)DaoImplInstanceFactory.getDaoImplInstance(CancellationDAO.IDENTITY);
	    logger.info("Implementation class: "+dao.getClass());
	    //end by sachin	
//		CancellationDAO dao = new CancellationDAOImpl();
		String checkFlag1=dao.earlyClosureFlag();
		session.setAttribute("checkFlag",checkFlag1);
		session.setAttribute("businessDate",bDate);
		session.setAttribute("makerDate",bDate);
		vo.setMakerId(makerId);
		vo.setMakerDate(bDate);

		String terminationId="";
		//String loanId = (String)session.getAttribute("loanId");
		//CreditManagementDAO dao = new CreditManagementDAOImpl();
		if(!dao.checkCancellationPaymentReceipt(vo.getLbxLoanNoHID()))
		{
			terminationId= dao.insertCancellationData(vo);
			String sms="";
			if(!terminationId.equalsIgnoreCase(""))
			{
				logger.info("Cancellation Data Saved Successfuly.");
				sms="S";
				ArrayList<CancellationVO> cancellationData = dao.selectCancellationData(vo.getLbxLoanNoHID(),terminationId);
				request.setAttribute("cancellationData", cancellationData);
				logger.info("Inside EarlyClosureDispatchAction........SaveCancellation for Cancellation");
			}
			else{
				logger.info("Cancellation Data Not Saved.");
				sms="E";
			}
			request.setAttribute("sms",sms);
		}
		else
		{
			request.setAttribute("sms","AP");
		}
		
		return mapping.findForward("saveCancellationDetails");
	}
	
	public ActionForward updateClosureDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Inside EarlyClosureDispatchAction...........updateClosureDetails");
		
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String makerId="";
		String bDate ="";
		if(userobj!=null){
			makerId= userobj.getUserId();
			bDate=userobj.getBusinessdate();
		}else{
			logger.info("here in updateClosureDetails method of EarlyClosureDispatchAction action the session is out----------------");
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

		String type=CommonFunction.checkNull(request.getParameter("type"));
		DynaValidatorForm ClosureDynaValidatorForm=(DynaValidatorForm)form;
		ClosureVO closureVo = new ClosureVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(closureVo, ClosureDynaValidatorForm);

		
		closureVo.setMakerId(makerId);
		closureVo.setMakerDate(bDate);
		
		String retStr="";

		boolean status=false;
		//CreditManagementDAO dao = new CreditManagementDAOImpl();
		request.setAttribute("closureStatus","closureStatus");
		EarlyClosureDAO dao=(EarlyClosureDAO)DaoImplInstanceFactory.getDaoImplInstance(EarlyClosureDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass()); 
		if(dao.checkClosureAmtInProces(closureVo.getLbxLoanNoHID()).equalsIgnoreCase("AmtInProcess"))
		{
			request.setAttribute("closureStatus","AmtInProcess");
			ArrayList<ClosureVO> closureData = dao.selectClosureData(closureVo.getLbxLoanNoHID(),closureVo.getTerminationId());
			request.setAttribute("closureData",closureData);
			if(closureVo.getClosureType().equalsIgnoreCase("T"))
			{
				request.setAttribute("earlyClosureLabel","maturityClosureLabel");
                //code added by neeraj tripathi
				request.setAttribute("saveCompleted","N");
				request.setAttribute("changeWaiveOff","N");
                //tripathi's space end
			}
			if(closureVo.getClosureType().equalsIgnoreCase("C"))
				request.setAttribute("maturityClosureLabel","earlyClosureLabel");
			retStr="updateClosureDataFailed";
		}
		else
			status= dao.insertUpdateClosureData(closureVo,type);
		
		logger.info("status: "+status+" type: "+type);
		
		String message="";
		if(status && type.equalsIgnoreCase("P"))
		{
			logger.info("Closure Data updated Successfuly.");
			ArrayList<ClosureVO> closureData = dao.selectClosureData(closureVo.getLbxLoanNoHID(),closureVo.getTerminationId());
			request.setAttribute("closureData", closureData);
			//Nishant Space Starts
			String netReceiveablePayable=dao.netReceivablePayableFlag();
			request.setAttribute("netReceiveablePayableF", netReceiveablePayable);
			//Nishant Space end
			message="S";
			if(closureVo.getClosureType().equalsIgnoreCase("T"))
				request.setAttribute("earlyClosureLabel","earlyClosureLabel");
			if(closureVo.getClosureType().equalsIgnoreCase("C"))
				request.setAttribute("maturityClosureLabel","maturityClosureLabel");
			retStr="saveClosureDetails";
		}
		if(status && type.equalsIgnoreCase("F"))
		{
			ArrayList<ClosureVO> closureData = dao.selectClosureData(closureVo.getLbxLoanNoHID(),closureVo.getTerminationId());
			request.setAttribute("closureData", closureData);
			logger.info("Closure Data updated Successfuly.");
			session.removeAttribute("checkFlag");
			session.removeAttribute("realizeFlag");
			message="U";
			retStr="updateClosureDetails";
		}
		else if((!retStr.equalsIgnoreCase("updateClosureDataFailed") && status==false && type.equalsIgnoreCase("F"))||(!retStr.equalsIgnoreCase("updateClosureDataFailed") && status==false && type.equalsIgnoreCase("P")))
		{
			logger.info("Closure Data Not updated.");
			message="E";
			retStr="updateClosureDetails";
		}
		logger.info("message: "+message+" retStr: "+retStr);
		request.setAttribute("message",message);
		return mapping.findForward(retStr);
	}
	
	//method added by neeraj
	public ActionForward allocateWaiveOff(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception
	{
		logger.info("Inside allocateWaiveOff.");		
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String businessDate ="";
		if(userobj!=null){
			userId= userobj.getUserId();
			businessDate=userobj.getBusinessdate();
		}else{
			logger.info("here in allocateWaiveOff method of EarlyClosureDispatchAction action the session is out----------------");
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
		EarlyClosureDAO dao=(EarlyClosureDAO)DaoImplInstanceFactory.getDaoImplInstance(EarlyClosureDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass()); 
		String loanId=request.getParameter("loanId");
		String change=request.getParameter("change");
		String terminationId=request.getParameter("terminationId");
		String waiveOffAmount=request.getParameter("waiveOffAmount");
		String approvedBY=dao.getApprovedBY(loanId,terminationId);
		
		logger.info("Change Status         :   "+change);
		logger.info("terminationId         :   "+terminationId);		
		logger.info("loanId                :   "+loanId);
		logger.info("waiveOffAmount        :   "+waiveOffAmount);
		logger.info("userId                :   "+userId);
		logger.info("businessDate          :   "+businessDate);
		logger.info("approvedBY            :   "+approvedBY);
		ArrayList<String>inList=new ArrayList();
		inList.add(change);
		inList.add(terminationId);
		inList.add(userId);
		inList.add(businessDate);
							
		ArrayList allocatList=dao.getWaiveOffList(inList);
		if(allocatList.size()>0)
		{
			ClosureVO vo=(ClosureVO)allocatList.get(0);
			String errorFlag=vo.getErrorFlag();
			String msg=vo.getErrorMsg();
			if(errorFlag.trim().equalsIgnoreCase("E"))
			{
				request.setAttribute("error",msg);
				return mapping.findForward("success");
			}
			else
			{
				ArrayList totalList=dao.getTotalList(terminationId);
				request.setAttribute("totalList",totalList);
				request.setAttribute("allocatList",allocatList);
			}
		}
		else
			request.setAttribute("error","Some SQL Exception! Contact System Administrator.");
		request.setAttribute("orgWaiveAmt",waiveOffAmount);
		request.setAttribute("loanId",loanId);
		request.setAttribute("terminationId",terminationId);	
		session.setAttribute("approvedBY",approvedBY);
		return mapping.findForward("success");
	}
	//method added by neeraj
	public ActionForward saveWaiveOffData(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception
	{
		logger.info("Inside saveWaiveOffData.");		
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String businessDate ="";
		if(userobj!=null){
			userId= userobj.getUserId();
			businessDate=userobj.getBusinessdate();
		}else{
			logger.info("here in saveWaiveOffData method of EarlyClosureDispatchAction action the session is out----------------");
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
		String chargeStr=request.getParameter("chargeList");
		String waiveAmtStr=request.getParameter("waiveAmtList");
		String balAmtStr=request.getParameter("balAmtList");
		String recordStr=request.getParameter("recordList");
		
		String loanId=request.getParameter("loanId");
		String terminationId=request.getParameter("terminationId");
		String lbxapprovedBy=request.getParameter("lbxapprovedBy");
		String approvedBY=request.getParameter("approvedBY");
		logger.info("In action --approvedby"+approvedBY);
		//String []chargeList=chargeStr.split(",");
		//String []waiveAmtList=waiveAmtStr.split(",");
		//String []balAmtList=balAmtStr.split(",");
		//String []recordList=recordStr.split(",");
		
		EarlyClosureDAO dao=(EarlyClosureDAO)DaoImplInstanceFactory.getDaoImplInstance(EarlyClosureDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass()); 
		ArrayList status =dao.saveWaiveOffData(terminationId,chargeStr,waiveAmtStr,balAmtStr,recordStr,loanId,userId,businessDate,lbxapprovedBy);
		ClosureVO vo=(ClosureVO) status.get(0);
		logger.info("vo.getErrorFlag() "+vo.getErrorFlag());
		if(CommonFunction.checkNull(vo.getErrorFlag()).equalsIgnoreCase("S"))
		    request.setAttribute("save","save");
		else
			request.setAttribute("notSave","notSave");
		ArrayList<String>inList=new ArrayList();
		inList.add("N");
		inList.add(terminationId);
		inList.add(userId);		
		inList.add(businessDate);
		
		ArrayList allocatList=dao.getWaiveOffList(inList);
		if(allocatList.size()>0)
		{
			 vo=(ClosureVO)allocatList.get(0);
			String errorFlag=vo.getErrorFlag();
			String msg=vo.getErrorMsg();
			if(errorFlag.trim().equalsIgnoreCase("E"))
			{
				request.setAttribute("error",msg);
				return mapping.findForward("success");
			}
			else
			{
				ArrayList totalList=dao.getTotalList(terminationId);
				request.setAttribute("totalList",totalList);
				request.setAttribute("allocatList",allocatList);					
			}
		}
		else
			request.setAttribute("error","Some SQL Exception! Contact System Administrator.");
		String orgWaiveAmt=request.getParameter("orgWaiveAmt");
		request.setAttribute("orgWaiveAmt",orgWaiveAmt);
		request.setAttribute("loanId",loanId);
		request.setAttribute("terminationId",terminationId);	
		request.setAttribute("approvedBY",approvedBY);	
		request.setAttribute("lbxapprovedBy",lbxapprovedBy);	
		return mapping.findForward("success");
	}
	
	public ActionForward deleteClosureDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Inside EarlyClosureDispatchAction...........deleteClosureDetails");
		
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String makerId="";
		String bDate ="";
		if(userobj!=null){
			makerId= userobj.getUserId();
			bDate=userobj.getBusinessdate();
		}else{
			logger.info("here in deleteClosureDetails method of EarlyClosureDispatchAction action the session is out----------------");
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

		DynaValidatorForm ClosureDynaValidatorForm=(DynaValidatorForm)form;
		ClosureVO closureVo = new ClosureVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(closureVo, ClosureDynaValidatorForm);

		String type=closureVo.getClosureType();
		closureVo.setMakerId(makerId);
		closureVo.setMakerDate(bDate);
		
		boolean status=false;
		request.setAttribute("closureStatus","closureStatus");
		EarlyClosureDAO dao=(EarlyClosureDAO)DaoImplInstanceFactory.getDaoImplInstance(EarlyClosureDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass()); 
		status= dao.deleteClosureData(closureVo);
		String message="";
		String retStr="";
		if(status)
		{
			if(type.equals("T"))
			{
				session.removeAttribute("maturityClosureLabel");
				session.removeAttribute("closureDataDisabled");
				session.removeAttribute("closureData");
				session.removeAttribute("cancellationDataDisabled");
				session.setAttribute("closureNew","closureNew");
				request.setAttribute("earlyClosureLabel","earlyClosureLabel");
				message="D";
				retStr="deleteClosureDetails";
			}
			if(type.equals("C"))
			{
				session.removeAttribute("earlyClosureLabel");
				session.removeAttribute("closureDataDisabled");
				session.removeAttribute("closureData");
				session.removeAttribute("cancellationDataDisabled");
				session.setAttribute("closureNew","closureNew");
				request.setAttribute("maturityClosureLabel","maturityClosureLabel");
				message="D";
				retStr="deleteClosureDetails";
			}

		}			
		else if(!status)
		{
			ArrayList<ClosureVO> closureData = dao.selectClosureData(closureVo.getLbxLoanNoHID(),closureVo.getTerminationId());
			request.setAttribute("closureData", closureData);
			if(closureVo.getClosureType().equalsIgnoreCase("T"))
			request.setAttribute("earlyClosureLabel","earlyClosureLabel");
			if(closureVo.getClosureType().equalsIgnoreCase("C"))
			request.setAttribute("maturityClosureLabel","maturityClosureLabel");
			message="E";
			retStr="saveClosureDetails";
		}
		request.setAttribute("message",message);
		return mapping.findForward(retStr);
}
		
	
	public ActionForward updateCancellationDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Inside EarlyClosureDispatchAction...........updateCancellationDetails");
		
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String makerId="";
		
		String bDate ="";
		if(userobj!=null){
			makerId= userobj.getUserId();
			bDate=userobj.getBusinessdate();
		}else{
			logger.info("here in updateCancellationDetails method of EarlyClosureDispatchAction  action the session is out----------------");
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

		String type=CommonFunction.checkNull(request.getParameter("type"));
		DynaValidatorForm CancellationMakerDynaValidatorForm=(DynaValidatorForm)form;
		CancellationVO vo = new CancellationVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, CancellationMakerDynaValidatorForm);
		//change by sachin
		CancellationDAO dao=(CancellationDAO)DaoImplInstanceFactory.getDaoImplInstance(CancellationDAO.IDENTITY);
	    logger.info("Implementation class: "+dao.getClass());
	    //end by sachin	
//		CancellationDAO dao = new CancellationDAOImpl();
		vo.setMakerId(makerId);
		vo.setMakerDate(bDate);

		boolean status=false;
		String retStr="";
		//CreditManagementDAO dao = new CreditManagementDAOImpl();
		if(!dao.checkCancellationPaymentReceipt(vo.getLbxLoanNoHID()))
		{
			status= dao.updateCancellationData(vo,type);
			String sms="";
			
			if(status && type.equalsIgnoreCase("P"))
			{
				logger.info("Cancellation Data updated Successfuly.");
				sms="S";
				ArrayList<CancellationVO> cancellationData = dao.selectCancellationData(vo.getLbxLoanNoHID(),vo.getTerminationId());
				request.setAttribute("cancellationData", cancellationData);
				logger.info("Inside EarlyClosureDispatchAction........SaveCancellation for Cancellation");
				retStr="saveCancellationDetails";
			}
			else if(status && type.equalsIgnoreCase("F"))
			{
				logger.info("Cancellation Data updated Successfuly.");
				session.removeAttribute("businessDate");
				session.removeAttribute("makerDate");
				session.removeAttribute("checkFlag");
				session.removeAttribute("realizeFlag");
				sms="U";
				ArrayList<CancellationVO> cancellationData = dao.selectCancellationData(vo.getLbxLoanNoHID(),vo.getTerminationId());
				request.setAttribute("cancellationData", cancellationData);
				logger.info("Inside EarlyClosureDispatchAction........SaveCancellation for Cancellation");
				retStr="updateCancellationDetails";
			}
			else if(status==false)
			{
				logger.info("Closure Data Not updated.");
				sms="E";
				retStr="updateCancellationDetails";
			}
			request.setAttribute("sms",sms);
		}
		else
		{
			request.setAttribute("sms","AP");
			retStr="updateCancellationDetails";
		}
		return mapping.findForward(retStr);
	}
	
	public ActionForward deleteCancellationDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Inside EarlyClosureDispatchAction...........deleteCancellationDetails");
		
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String makerId="";
		
		String bDate ="";
		if(userobj!=null){
			makerId= userobj.getUserId();
			bDate=userobj.getBusinessdate();
		}else{
			logger.info("here in deleteCancellationDetails method of EarlyClosureDispatchAction  action the session is out----------------");
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

		DynaValidatorForm CancellationMakerDynaValidatorForm=(DynaValidatorForm)form;
		CancellationVO vo = new CancellationVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, CancellationMakerDynaValidatorForm);
		//change by sachin
		CancellationDAO dao=(CancellationDAO)DaoImplInstanceFactory.getDaoImplInstance(CancellationDAO.IDENTITY);
	    logger.info("Implementation class: "+dao.getClass());
	    //end by sachin	
//		CancellationDAO dao = new CancellationDAOImpl();
		vo.setMakerId(makerId);	
		vo.setMakerDate(bDate);
		
		boolean status=false;
		String retStr="";
		
		status=dao.deleteCancellationDetails(vo);
		
		if(status)
		{
			session.removeAttribute("earlyClosureLabel");
			session.removeAttribute("maturityClosureLabel");
			session.removeAttribute("closureDataDisabled");
			session.removeAttribute("closureNew");
			session.removeAttribute("cancellationDataDisabled");
			request.setAttribute("cancellationNew","cancellationNew");
			request.setAttribute("sms","D");
			retStr="deleteCancellationDetails";
		}
		else if(!status)
		{
			ArrayList<CancellationVO> cancellationData = dao.selectCancellationData(vo.getLbxLoanNoHID(),vo.getTerminationId());
			request.setAttribute("cancellationData", cancellationData);
			request.setAttribute("message","E");
			retStr="saveCancellationDetails";
		}
		
		return mapping.findForward(retStr);
		
	}
	
}
