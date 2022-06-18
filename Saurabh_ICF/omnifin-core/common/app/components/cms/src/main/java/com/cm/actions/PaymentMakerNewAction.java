package com.cm.actions;



import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.cm.dao.PaymentDAO;
import com.cm.vo.PaymentMakerForCMVO;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class PaymentMakerNewAction extends Action{
	private static final Logger logger = Logger.getLogger(PaymentMakerNewAction.class.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		logger.info(" in PaymentMakerNewAction----------");
		
		HttpSession session = request.getSession();
		//boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info(" in execute method of PaymentMakerNewAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		
		Object sessionId = session.getAttribute("sessionID");
		
		String strFlag="";	
		
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
		//for check User session start	
		ServletContext context = getServlet().getServletContext();
		
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
			strFlag=null;
			return mapping.findForward("logout");
		}
		PaymentDAO dao=(PaymentDAO)DaoImplInstanceFactory.getDaoImplInstance(PaymentDAO.IDENTITY);
		//PaymentReceiptBusinessBeanRemote dao=(PaymentReceiptBusinessBeanRemote) LookUpInstanceFactory.getLookUpInstance(PaymentReceiptBusinessBeanRemote.REMOTE_IDENTITY, request);
		logger.info("Implementation class: "+dao.getClass());
		String allocatePayFlag=dao.allocatePayableCheckFlag();
		session.setAttribute("allocatePayFlag",allocatePayFlag);
		//Nishant space starts
		String paymentLimit=dao.cashPaymentLimit();
		session.setAttribute("cashPaymentLimit", paymentLimit);
		//Nishant space ends
		session.removeAttribute("pParentGroup");
		session.removeAttribute("loanID");
		session.removeAttribute("instrumentID");
		session.removeAttribute("datatoapproveList");
		session.removeAttribute("businessPartnerType");
		PaymentMakerForCMVO vo = new PaymentMakerForCMVO();
//		CreditManagementDAO dao = new CreditManagementDAOImpl();
	//	ArrayList<PaymentMakerForCMVO> bussinessPartnerList= dao.getbussinessPartner();
	//	request.setAttribute("bussinessPartnerList", bussinessPartnerList);
		logger.info(" In the PaymentCMProcessAction NEW----------");
		strFlag=null;
		return mapping.findForward("new");
		}

}
