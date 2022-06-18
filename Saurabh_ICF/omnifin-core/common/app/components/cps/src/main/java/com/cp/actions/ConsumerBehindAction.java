package com.cp.actions;

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

import com.cibil.dao.CibilVerificationDAO;
import com.cibil.vo.CibilVerificationVO;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.CreditProcessingDAO;
import com.cp.vo.CommonDealVo;
import com.cp.vo.ConsumerVo;
import com.gcd.dao.CorporateDAO;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

/**
 * MyEclipse Struts Creation date: 04-29-2011
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class ConsumerBehindAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(ConsumerBehindAction.class.getName());
	public ActionForward cibilReportLoad(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		logger.info("In ConsumerDispatchAction  cibilReportLoad id: ");
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in cibilReportLoad method of ConsumerBehindAction action the session is out----------------");
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
		CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
		CibilVerificationDAO dao=(CibilVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(CibilVerificationDAO.IDENTITY);
		CorporateDAO dao1=(CorporateDAO)DaoImplInstanceFactory.getDaoImplInstance(CorporateDAO.IDENTITY);
        
        logger.info("Implementation class: "+service.getClass()); 			// changed by asesh
        String dealId = request.getParameter("dealId");
		 String functionId=CommonFunction.checkNull((String)session.getAttribute("functionId")).trim();
			if(CommonFunction.checkNull(functionId).trim().equalsIgnoreCase("7000114"))
			{
				String loanId = request.getParameter("loanId");
				if(!CommonFunction.checkNull(loanId).equalsIgnoreCase("")){
					dealId=service.getDealId(loanId);
					request.setAttribute("contactRecording","contactRecording");
				}
			}
		
		if(!CommonFunction.checkNull(dealId).equalsIgnoreCase(""))
		{
			logger.info("In ConsumerDispatchAction  execute id: " + dealId);
	        session.setAttribute("dealId", dealId);
		}
		else if(session.getAttribute("dealId")!=null)
		{
			dealId=session.getAttribute("dealId").toString();
		}
		
		ConsumerVo vo = new ConsumerVo();
		vo.setDealId(dealId);
		session.setAttribute("dealID", dealId);

		//CreditProcessingDAO service = new CreditProcessingDAOImpl();
		String cibilRepotFlag = dao.getCibilRepotFlag();
		request.setAttribute("cibilRepotFlag", cibilRepotFlag);
		ArrayList<ConsumerVo> detailList = service.getCibilData(vo);
		ArrayList dealHeader = service.getDealHeader(dealId);
		session.setAttribute("dealHeader", dealHeader);
		request.setAttribute("list", detailList);
//Anurag Changes Starts for cibil
         CibilVerificationVO dvo = new CibilVerificationVO();
		 dvo.setDealID(dealId);
		   ArrayList gridList=dao.getCblViewCustomerGridList(dvo);
	        request.setAttribute("gridList",gridList);
	        //ArrayList<Object> roleList = dao1.getRoleListCorp(dealId);
	        ArrayList<Object> roleList = dao1.getRoleList(dealId);
			request.setAttribute("roleList", roleList);
//Anurag Changes end for cibil
		

		if(!CommonFunction.checkNull(dealId).equals(""))
		{			
			ArrayList uploadedDocList = service.getUploadCibilData(dealId);
			if(uploadedDocList.size()>0){
				request.setAttribute("uploadedDocList", uploadedDocList);
			}
		}
	
		return mapping.findForward("cibilReportLoad");
	}
	
	
	public ActionForward cibilReportSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		logger.info("In ConsumerBehindAction(cibilReportSearch)");
		HttpSession session = request.getSession();
		String userId="";
		String branch="";
		session.removeAttribute("strFlagDV");
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branch=userobj.getBranchId();
		}else{
			logger.info("here in cibilReportSearch method of ConsumerBehindAction action the session is out----------------");
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
		session.removeAttribute("viewBlackList");
		session.removeAttribute("dealHeader");
		session.removeAttribute("dealId");
		DynaValidatorForm CommonDealDynaValidatorForm = (DynaValidatorForm) form;// TODO
																					// Auto-generated
																					// method
																					// stub

		ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
		String dateFormat = resource.getString("lbl.dateFormat(dd-mm-yyyy)");
		CommonDealVo vo = new CommonDealVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,CommonDealDynaValidatorForm);
		String stage = "F";
		logger.info("stage: " + stage);
		vo.setStage(stage);
		vo.setBranchId(branch);
		if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
			vo.setReportingToUserId(userId);
		logger.info("user Id:::::"+vo.getReportingToUserId());
		//vo.setUserId(userobj.getUserName());
		
		if (vo.getApplicationdate().equalsIgnoreCase(dateFormat)) 
		{
			vo.setApplicationdate("");
		}
		CreditProcessingDAO creditDAO=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+creditDAO.getClass()); 			// changed by asesh
		//CreditProcessingDAO creditDAO = new CreditProcessingDAOImpl();
		ArrayList dealdetails=new ArrayList();
		
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
		dealdetails= creditDAO.getDealWithCibilReport(vo, request);

	    logger.info("In searchDealCapturing....list: "+dealdetails.size());
		
	    request.setAttribute("list", dealdetails);
		
		logger.info("list.isEmpty(): "+dealdetails.size());
		
		request.setAttribute("dealdetails", dealdetails);
		
		return mapping.findForward("cibilReportSearch");
	}
}
