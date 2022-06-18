package com.customerService.actions;

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

import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.customerService.dao.CustomerServiceDAO;
import com.customerService.vo.CustomerServiceVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class ViewCustomerExposureBehindAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(ViewCustomerExposureBehindAction.class.getName());
	
	public ActionForward openViewCustomerExposure(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws Exception {
		
		logger.info("In ViewCustomerExposureBehindAction ......openViewCustomerExposure() ");
		HttpSession session = request.getSession();
	
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		//String branchId="";
		if(userobj!=null)
		{	
			//branchId=userobj.getBranchId();
		}else{
			logger.info(" in openViewCustomerExposure method of ViewCustomerExposureBehindAction action the session is out----------------");
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
		logger.info("before: "); 
		CustomerServiceDAO dao=(CustomerServiceDAO)DaoImplInstanceFactory.getDaoImplInstance(CustomerServiceDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass()); 
		
		String txnId=CommonFunction.checkNull(request.getParameter("txnId"));
		String txnType=CommonFunction.checkNull(request.getParameter("txnType"));
		CustomerServiceVo vo = new CustomerServiceVo();
	    vo.setTxnId(txnId);
	    vo.setTxnType(txnType);
			
		ArrayList<CustomerServiceVo> customerExposureList = dao.customerExposureListViewer(vo);
		request.setAttribute("customerExposureList", customerExposureList);
		
		dao=null;	
		vo=null;
		txnId=null;
		txnType=null;
		return mapping.findForward("customerExposureViewer");	
	
	}
}
