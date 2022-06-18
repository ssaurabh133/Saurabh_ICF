package com.scz.actions;

import java.util.ArrayList;
import java.util.ResourceBundle;

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

import com.connect.CommonFunction;
import com.connect.ConnectionReportDumpsDAO;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.scz.DateFormator;
import com.scz.vo.PaymentAllocationReportVO;

public class MonthlyProcessAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(MonthlyProcessAction.class.getName());
	static ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.utill");
	
	
	public ActionForward generateMonthlyProcess(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
	 logger.info("In generateMonthlyProcess of MonthlyProcessAction  ");
	 	HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId ="";
		String businessDate ="";
		String p_company_name ="";
		int compid =0;
		String p_printed_by="";
		if(userobj!=null)
		{
			userId = userobj.getUserId();
			businessDate=userobj.getBusinessdate();
			compid=userobj.getCompanyId();
			p_company_name=userobj.getConpanyName();
			p_printed_by=userobj.getUserName();
			logger.info("company id is :: "+compid);
		}
		else{
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
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
		
		logger.info("generateMonthlyProcess .............. Rudra ");
		
		PaymentAllocationReportVO paymentAllocationVO = new PaymentAllocationReportVO();
		DynaValidatorForm MonthlyProcessDynaValidatorFormBean=(DynaValidatorForm)form;	
		org.apache.commons.beanutils.BeanUtils.copyProperties(paymentAllocationVO, MonthlyProcessDynaValidatorFormBean);
		
		logger.info("generateMonthlyProcess .............. Rudra "+ paymentAllocationVO.getPoolID());
		
		ArrayList<Object> in =new ArrayList<Object>();
		ArrayList<Object> out =new ArrayList<Object>();
		ArrayList outMessages =new ArrayList();
		ArrayList mainList =new ArrayList();
		String s1="";
		String s2="";
		logger.info("month  ...."+paymentAllocationVO.getMonth()+"       "+DateFormator.dmyToYMD(paymentAllocationVO.getMonth()));
		in.add(paymentAllocationVO.getPoolID());
		in.add(paymentAllocationVO.getPoolName());
		in.add(DateFormator.dmyToYMD(paymentAllocationVO.getPoolCreationDate()));
		String date = DateFormator.dmyToYMD(paymentAllocationVO.getMonth());
		in.add(date);
		in.add(userId);
		in.add(DateFormator.dmyToYMD(businessDate));
		in.add(paymentAllocationVO.getMonthType());
		out.add(s1);
		out.add(s2);
		String sms = "";
		logger.info("The IN Parameter " +in.toString());
		try
		{
			outMessages=(ArrayList) ConnectionReportDumpsDAO.callSPForHiberNate("SCZ_PAYMENT_ALLOCATION_PROCESS",in,out);
			s1=CommonFunction.checkNull(outMessages.get(0));
			s2=CommonFunction.checkNull(outMessages.get(1));
			logger.info("After calling SCZ_PAYMENT_ALLOCATION_PROCESS Procedure S1 and S2 "+s1+" - "+s2);
			if(s1.equalsIgnoreCase("S"))
			{
				sms="S";
			}
			else
				sms="E";
			
		}catch (Exception e) {
			logger.info("An Error..."+e);
		}
		
		request.setAttribute("sms", sms);
        return  mapping.findForward("success");
 }
	
	
	public ActionForward checkValid(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
	 logger.info("In check Valid of PaymentAllocationProcessAction  ");
	 	HttpSession session = request.getSession();
	 	UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId ="";
		String businessDate ="";
		if(userobj!=null)
		{
			userId = userobj.getUserId();
			businessDate=userobj.getBusinessdate();
		}
		else{
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
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
		
		PaymentAllocationReportVO paymentAllocationVO = new PaymentAllocationReportVO();
		DynaValidatorForm PaymentAllocationDynaValidatorFormBean=(DynaValidatorForm)form;	
		org.apache.commons.beanutils.BeanUtils.copyProperties(paymentAllocationVO, PaymentAllocationDynaValidatorFormBean);
		
		ArrayList<Object> in =new ArrayList<Object>();
		ArrayList<Object> out =new ArrayList<Object>();
		ArrayList outMessages =new ArrayList();
		ArrayList mainList =new ArrayList();
		String s1="";
		String s2=""; 
		
		in.add(paymentAllocationVO.getPoolID());
		in.add(DateFormator.dmyToYMD(paymentAllocationVO.getPoolCreationDate()));
		in.add(DateFormator.dmyToYMD(paymentAllocationVO.getMonth()));
		in.add("Monthly Process");
		out.add(s1);
		out.add(s2);
		logger.info("The IN Parameter " +in.toString());
		outMessages=(ArrayList) ConnectionReportDumpsDAO.callSPForHiberNate("scz_date_difference",in,out);
		s1=CommonFunction.checkNull(outMessages.get(0));
		s2=CommonFunction.checkNull(outMessages.get(1));
		logger.info("After scz_date_difference Procedure S1 and S2 "+s1+" - "+s2);
		
		if(s1.equals("S")||s1.equals("F")){
			request.setAttribute("msg1", s2);
			request.setAttribute("s1", s1);
		}else if(s1.equals("R")){
			request.setAttribute("ref", s2);
			request.setAttribute("s1", s1);
			request.setAttribute("refresh", "R");
		}else if(s1.equals("X")) {
			request.setAttribute("ref", s2);
			request.setAttribute("s1", "F");
		}else{
			request.setAttribute("msg", s2);
		}
		return mapping.findForward("success");
 }
}
