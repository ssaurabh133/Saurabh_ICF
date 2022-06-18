package com.cm.actions;

import java.util.ArrayList;
import java.math.BigDecimal;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import com.cm.dao.WaiveOffDAO;
import com.cm.vo.PaymentMakerForCMVO;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class WaiveOffProcessAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(WaiveOffProcessAction.class.getName());
	public ActionForward viewPaybleWaiveOff(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws Exception {
			
		logger.info("In  WaiveOffProcessAction viewPayble()----------------------------");
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
	    String userId="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
		}else{
			logger.info("in  viewPaybleWaiveOff method of  WaiveOffProcessAction action the session is out----------------");
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

	  
		//CreditManagementDAO dao = new CreditManagementDAOImpl();
		WaiveOffDAO dao=(WaiveOffDAO)DaoImplInstanceFactory.getDaoImplInstance(WaiveOffDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());
		int loanId=0;
		if(!CommonFunction.checkNull(request.getParameter("loanId")).equalsIgnoreCase("")){
			loanId=Integer.parseInt(CommonFunction.checkNull(request.getParameter("loanId")));
		}
		logger.info("In viewPayble---loanId-"+loanId); 
		String bpType=(CommonFunction.checkNull(request.getParameter("bpType")));
		logger.info("In viewPayble---loanId-"+bpType); 
		int bpId=Integer.parseInt(CommonFunction.checkNull(request.getParameter("bpId")));
		logger.info("bpId...."+bpId);
		ArrayList<PaymentMakerForCMVO> getPayableList= dao.viewPayableForWaiveOff(loanId, bpType,bpId);
		request.setAttribute("viewPayableList", getPayableList);
		logger.info("viewPayableList:----"+getPayableList.size());

		//  Code by Rajesh Kumar - Start
		
		double totalOriginalAmount=0;
		double totalWaivedOffAmount=0;
		double totalAdviceAmount=0;
		double totalAdjustedAmount=0;
		double totalAmountInProcess=0;
		double totalBalanceAmount=0;

		for (PaymentMakerForCMVO paybleList : getPayableList) {
			totalOriginalAmount += Double.valueOf(CommonFunction.checkNull(paybleList.getOriginalAmount()).replaceAll(",",""));
			totalWaivedOffAmount +=Double.valueOf(CommonFunction.checkNull(paybleList.getWaiveOffAmount()).replaceAll(",",""));
			totalAdviceAmount += Double.valueOf(CommonFunction.checkNull(paybleList.getAdviceAmount()).replaceAll(",",""));
			totalAdjustedAmount += Double.valueOf(CommonFunction.checkNull(paybleList.getAdjustedAmount()).replaceAll(",",""));
			totalAmountInProcess += Double.valueOf(CommonFunction.checkNull(paybleList.getAmountInProcess()).replaceAll(",",""));
			totalBalanceAmount += Double.valueOf(CommonFunction.checkNull(paybleList.getBalanceAmount()).replaceAll(",",""));
		}
		
		
		request.setAttribute("printTotalAmounts", "printTotalAmounts");
		request.setAttribute("totalOriginalAmount", BigDecimal.valueOf(totalOriginalAmount).toPlainString());
		request.setAttribute("totalWaivedOffAmount", BigDecimal.valueOf(totalWaivedOffAmount).toPlainString());
		request.setAttribute("totalAdviceAmount", BigDecimal.valueOf(totalAdviceAmount).toPlainString());
		request.setAttribute("totalAdjustedAmount",BigDecimal.valueOf(totalAdjustedAmount).toPlainString());
		request.setAttribute("totalAmountInProcess",BigDecimal.valueOf( totalAmountInProcess).toPlainString());
		request.setAttribute("totalBalanceAmount",BigDecimal.valueOf(totalBalanceAmount).toPlainString());
		
		//  Code by Rajesh Kumar - End
		
		return mapping.findForward("payableWaiveOff");
			
	}
	
	public ActionForward ViewReceivableWaiveOff(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws Exception {
			
		logger.info("In  WaiveOffProcessAction ViewReceivableWaiveOff()----------------------------");
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
	    String userId="";
	    if(userobj!=null)
		{
	    	userId=userobj.getUserId();
		}else{
			logger.info("in  ViewReceivableWaiveOff method of WaiveOffProcessAction  action the session is out----------------");
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
		

		//CreditManagementDAO dao = new CreditManagementDAOImpl();
		WaiveOffDAO dao=(WaiveOffDAO)DaoImplInstanceFactory.getDaoImplInstance(WaiveOffDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());
		int loanId=0;
		if(!CommonFunction.checkNull(request.getParameter("loanId")).equalsIgnoreCase("")){
			loanId=Integer.parseInt(CommonFunction.checkNull(request.getParameter("loanId")));
		}
		logger.info("In ViewReceivableWaiveOff---loanId-"+loanId); 
		String bpType=(CommonFunction.checkNull(request.getParameter("bpType")));
		logger.info("In ViewReceivableWaiveOff---loanId-"+bpType); 
		int bpId=Integer.parseInt(CommonFunction.checkNull(request.getParameter("bpId")));
		logger.info("bpId...."+bpId);
		ArrayList<PaymentMakerForCMVO> ReceivableList= dao.viewReceivableForWaiveOff(loanId, bpType,bpId);
		request.setAttribute("ReceivableList", ReceivableList);
		logger.info("ReceivableList"+ReceivableList.size());
		
		//  Code by Rajesh Kumar - Start

		double totalOriginalAmount=0;
		double totalWaivedOffAmount=0;
		double totalAdviceAmount=0;
		double totalAdjustedAmount=0;
		double totalAmountInProcess=0;
		double totalBalanceAmount=0;

		for (PaymentMakerForCMVO paybleList : ReceivableList) {
			totalOriginalAmount += Double.valueOf(CommonFunction.checkNull(paybleList.getOriginalAmount()).replaceAll(",",""));
			totalWaivedOffAmount +=Double.valueOf(CommonFunction.checkNull(paybleList.getWaiveOffAmount()).replaceAll(",",""));
			totalAdviceAmount += Double.valueOf(CommonFunction.checkNull(paybleList.getAdviceAmount()).replaceAll(",",""));
			totalAdjustedAmount += Double.valueOf(CommonFunction.checkNull(paybleList.getAdjustedAmount()).replaceAll(",",""));
			totalAmountInProcess += Double.valueOf(CommonFunction.checkNull(paybleList.getAmountInProcess()).replaceAll(",",""));
			totalBalanceAmount += Double.valueOf(CommonFunction.checkNull(paybleList.getBalanceAmount()).replaceAll(",",""));
		}


		request.setAttribute("printTotalAmounts", "printTotalAmounts");
		request.setAttribute("totalOriginalAmount", BigDecimal.valueOf(totalOriginalAmount).toPlainString());
		request.setAttribute("totalWaivedOffAmount", BigDecimal.valueOf(totalWaivedOffAmount).toPlainString());
		request.setAttribute("totalAdviceAmount", BigDecimal.valueOf(totalAdviceAmount).toPlainString());
		request.setAttribute("totalAdjustedAmount",BigDecimal.valueOf(totalAdjustedAmount).toPlainString());
		request.setAttribute("totalAmountInProcess",BigDecimal.valueOf( totalAmountInProcess).toPlainString());
		request.setAttribute("totalBalanceAmount",BigDecimal.valueOf(totalBalanceAmount).toPlainString());

		//  Code by Rajesh Kumar - End
		
		return mapping.findForward("ViewReceivableWaiveOff");
	
	}

}
