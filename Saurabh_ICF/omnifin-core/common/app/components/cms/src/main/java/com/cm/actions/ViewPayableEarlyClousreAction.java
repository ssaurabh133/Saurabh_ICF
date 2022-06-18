package com.cm.actions;

import java.util.ArrayList;
import java.math.BigDecimal;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


import com.cm.dao.EarlyClosureDAO;
//import com.cm.dao.EarlyClosureDAOImpl;
import com.cm.vo.PaymentMakerForCMVO;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class ViewPayableEarlyClousreAction extends Action {
	private static final Logger logger = Logger.getLogger(ViewPayableEarlyClousreAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws Exception {
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in execute method of ViewPayableEarlyClousreAction action the session is out----------------");
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
		EarlyClosureDAO dao=(EarlyClosureDAO)DaoImplInstanceFactory.getDaoImplInstance(EarlyClosureDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass()); 
		
		int loanId=Integer.parseInt(CommonFunction.checkNull(request.getParameter("loanId")));
		logger.info("In ViewPayableEarlyClousreAction---loanId-"+loanId);  
		ArrayList<PaymentMakerForCMVO> getPayableList= dao.viewPayableForEarlyClousre(loanId);
		request.setAttribute("viewPayableList", getPayableList);
		logger.info("viewPayableList"+getPayableList.size());

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
		return mapping.findForward("viewPayableEarly");
		
		
	}
	}


