package com.cm.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;
import com.cm.dao.PaymentDAO;
import com.cm.vo.PaymentMakerForCMVO;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class PaymentMakerSearch extends Action{
	private static final Logger logger = Logger.getLogger(PaymentMakerSearch.class.getName());

	public ActionForward execute(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws Exception {
		
		HttpSession session = request.getSession();

		logger.info("In searchDetail  ");		
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String branchId="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}else{
			logger.info("Inside execute method of  PaymentMakerSearch action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		session.removeAttribute("pParentGroup");
		session.removeAttribute("loanID");
		session.removeAttribute("instrumentID");
		session.removeAttribute("datatoapproveList");
		session.removeAttribute("businessPartnerType");
		session.removeAttribute("allocatePayFlag");
		//session.removeAttribute("lbxLoanNoHID");
		//boolean flag=false;
		Object sessionId = session.getAttribute("sessionID");
		PaymentMakerForCMVO vo = new PaymentMakerForCMVO();
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
			strFlag=null;
			return mapping.findForward("logout");
		}
         logger.info("current page link .......... "+request.getParameter("d-49520-p"));
		
		int currentPageLink = 0;
		if(request.getParameter("d-49520-p")==null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
		{
			currentPageLink=1;
		}
		else
		{
			currentPageLink =Integer.parseInt(request.getParameter("d-49520-p"));
		}
		
		logger.info("current page link ................ "+request.getParameter("d-49520-p"));
		
		vo.setCurrentPageLink(currentPageLink);
		//PaymentReceiptBusinessBeanRemote dao=(PaymentReceiptBusinessBeanRemote) LookUpInstanceFactory.getLookUpInstance(PaymentReceiptBusinessBeanRemote.REMOTE_IDENTITY, request);
		PaymentDAO dao=(PaymentDAO)DaoImplInstanceFactory.getDaoImplInstance(PaymentDAO.IDENTITY);
	    //logger.info("Implementation class: "+dao.getClass());
		//ArrayList<PaymentMakerForCMVO> bussinessPartnerList= dao.getbussinessPartner();
		//request.setAttribute("bussinessPartnerList", bussinessPartnerList);
		DynaValidatorForm PaymentCMDynaValidatorForm = (DynaValidatorForm)form;		
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, PaymentCMDynaValidatorForm);

		if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
		{ 
			vo.setReportingToUserId(userId);
		   //logger.info("When user id is not selected by the user:::::"+userId);
		}
		//logger.info("user Id:::::"+vo.getReportingToUserId());
		vo.setBranchId(branchId);
		vo.setUserId(userId);
//		vo.setLbxBPNID("");
//		vo.setLbxBPType("");
		//CreditManagementDAO dao = new CreditManagementDAOImpl();
		
		Map  map = dao.paymentMakerGrid(vo);
		ArrayList<PaymentMakerForCMVO> list = (ArrayList<PaymentMakerForCMVO>)map.get("detailListGrid");
		ArrayList searchlist = (ArrayList)map.get("searchlist");
		if( searchlist.size()==0)
		{
			//PaymentMakerForCMVO payMVo = new PaymentMakerForCMVO();
			//payMVo.setTotalRecordSize(count);
			//detailListGrid.add(payMVo);
			//request.setAttribute("flag","yes");
			request.setAttribute("nullVal","yes");//Manish
			
			//LoggerMsg.info("getTotalRecordSize : "+payMVo.getTotalRecordSize());
		}
	
		if(request.getAttribute("nullVal") == "yes"){
			String msg = "";
			msg = "N";
			request.setAttribute("msg", msg);
		}
//Neeraj Tripathi start
		String forward = request.getParameter("forward");
		String frdLoanID="";
		if(forward==null)
		{
			forward="No";
			frdLoanID="0";
		}
		else
		{
			forward="Yes";
			frdLoanID= request.getParameter("frdLoanID");
		}
		//logger.info("forward   :   "+forward);
		//logger.info("frdLoanID   :   "+frdLoanID);
		request.setAttribute("forward",forward);
		request.setAttribute("frdLoanID",frdLoanID);
//Neeraj Tripathi end
		request.setAttribute("list", list);
		request.setAttribute("paymentSearchlist", "paymentSearchlist");
		userId=null;
		branchId=null;
		strFlag=null;
		PaymentCMDynaValidatorForm.reset(mapping, request);
		return mapping.findForward("success");	
	
	}
	

}
