package com.cp.actions;

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

import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.CreditProcessingDAO;
import com.cp.dao.DealRateApprovalDAO;
import com.cp.vo.DealRateApprovalVO;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;


public class DealRateApproval extends DispatchAction 
{
	private static final Logger logger = Logger.getLogger(DealRateApproval.class.getName());
	public ActionForward defaultRateApproval(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("here in defaultRateApproval()  of DealRateApproval ");
		HttpSession session =  request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null)
		{
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
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
		DynaValidatorForm CommonDealDynaValidatorForm = (DynaValidatorForm) form;
		DealRateApprovalVO vo = new DealRateApprovalVO();
		String source=CommonFunction.checkNull(request.getParameter("source"));
		if(!source.equalsIgnoreCase("D")){
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,CommonDealDynaValidatorForm);
		}
		
		logger.info("current page link .......... "+request.getParameter("d-49520-p"));		
		int currentPageLink = 0;
		if(request.getParameter("d-49520-p")==null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
				currentPageLink=1;
		else
				currentPageLink =Integer.parseInt(request.getParameter("d-49520-p"));
		logger.info("current page link ................ "+request.getParameter("d-49520-p"));		
		vo.setCurrentPageLink(currentPageLink);
		vo.setBranchId(CommonFunction.checkNull(userobj.getBranchId()).trim());
		
		DealRateApprovalDAO dao=(DealRateApprovalDAO)DaoImplInstanceFactory.getDaoImplInstance(DealRateApprovalDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass()); 
        ArrayList list=new ArrayList();
		list= dao.fetchDealRateApprovalMakerDetail(vo);
		request.setAttribute("list",list);
		form.reset(mapping, request);
		dao=null;
		source=null;
		return mapping.findForward("success");
	}	
	public ActionForward defaultRateChecker(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("here in defaultRateChecker()  of DealRateApproval ");
		HttpSession session =  request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null)
		{
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
		DynaValidatorForm CommonDealDynaValidatorForm = (DynaValidatorForm) form;
		DealRateApprovalVO vo = new DealRateApprovalVO();
		String source=CommonFunction.checkNull(request.getParameter("source"));
		if(!source.equalsIgnoreCase("D")){
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,CommonDealDynaValidatorForm);
		}
		request.setAttribute("loginUserId",CommonFunction.checkNull(userobj.getUserId()).trim()); 
		request.setAttribute("loginUserName",CommonFunction.checkNull(userobj.getUserName()).trim()); 
		
		logger.info("current page link .......... "+request.getParameter("d-49520-p"));		
		int currentPageLink = 0;
		if(request.getParameter("d-49520-p")==null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
				currentPageLink=1;
		else
				currentPageLink =Integer.parseInt(request.getParameter("d-49520-p"));
		logger.info("current page link ................ "+request.getParameter("d-49520-p"));		
		vo.setCurrentPageLink(currentPageLink);
		vo.setBranchId(CommonFunction.checkNull(userobj.getBranchId()).trim());
		vo.setMakerId(CommonFunction.checkNull(userobj.getUserId()).trim());
		
		DealRateApprovalDAO dao=(DealRateApprovalDAO)DaoImplInstanceFactory.getDaoImplInstance(DealRateApprovalDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass()); 
        ArrayList list=new ArrayList();
		list= dao.fetchDealRateApprovalAuthorDetail(vo);
		request.setAttribute("list",list);
		source=null;
		vo=null;
		dao=null;
		form.reset(mapping, request);
		return mapping.findForward("success");	
		}
	
	
	public ActionForward fetchRecordDetail(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("here in fetchRecordDetail()  of DealRateApproval ");
		HttpSession session =  request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null)
		{
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
		request.setAttribute("loginUserId",CommonFunction.checkNull(userobj.getUserId()).trim()); 
		request.setAttribute("loginUserName",CommonFunction.checkNull(userobj.getUserName()).trim()); 
		DealRateApprovalVO vo = new DealRateApprovalVO();
		vo.setBranchId(CommonFunction.checkNull(userobj.getBranchId()).trim());
		String dealId=CommonFunction.checkNull(request.getParameter("dealId").trim());
		session.setAttribute("dealId",dealId);  // for asset 
		session.setAttribute("viewDeal","viewDeal");	
		vo.setDealId(dealId);
		CreditProcessingDAO creditProcessingDAO=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+creditProcessingDAO.getClass());  
        
		ArrayList dealHeader = creditProcessingDAO.getDealHeader(dealId);
		request.setAttribute("dealHeader", dealHeader);
		DealRateApprovalDAO dao=(DealRateApprovalDAO)DaoImplInstanceFactory.getDaoImplInstance(DealRateApprovalDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass()); 
        CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY); // for asset 
        logger.info("Implementation class: "+service.getClass()); // for asset 
        
        String  productCat = service.getProductType(dealId); // for asset 
		request.setAttribute("productCat", productCat); // for asset
		if(CommonFunction.checkNull(productCat).trim().equalsIgnoreCase("EL"))
		{
			request.setAttribute("ELCATEGORY","ELCATEGORY");   	
		}		
        ArrayList resultList=new ArrayList();
        resultList=dao.fetchRecordDetail(vo);
        request.setAttribute("resultList",resultList);         
        String assetOrCollateralResult=dao.isItMachineOrProperty(dealId);
        
        if(CommonFunction.checkNull(assetOrCollateralResult).equalsIgnoreCase("MACHINE"))
        {
        	request.setAttribute("MACHINE",assetOrCollateralResult );       	
        }
        if(CommonFunction.checkNull(assetOrCollateralResult).equalsIgnoreCase("PROPERTY"))
        {
        	request.setAttribute("PROPERTY",assetOrCollateralResult );       	
        }
        ArrayList<Object> showCollateralDetails = dao.getCollateralDetailsAllForRateApproval(dealId,assetOrCollateralResult); // for asset 
		request.setAttribute("showCollateralDetails",showCollateralDetails); // for asset 
		
		String loanAmount="select DEAL_LOAN_AMOUNT from cr_deal_loan_dtl where DEAL_ID="+dealId;
	    String loanAmt=ConnectionDAO.singleReturn(loanAmount);
	     	     				        
	    session.setAttribute("loanAmount", loanAmt);

		
	    dao=null;
	    service=null;
	    vo=null;
		return mapping.findForward("detail");
	}
	public ActionForward saveRateApprovalData(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("here in saveRateApprovalData()  of DealRateApproval ");
		HttpSession session =  request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null)
		{
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
		request.setAttribute("loginUserId",CommonFunction.checkNull(userobj.getUserId()).trim()); 
		request.setAttribute("loginUserName",CommonFunction.checkNull(userobj.getUserName()).trim()); 
		DynaValidatorForm CommonDealDynaValidatorForm = (DynaValidatorForm) form;
		DealRateApprovalVO vo = new DealRateApprovalVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,CommonDealDynaValidatorForm);	
		
		vo.setBranchId(CommonFunction.checkNull(userobj.getBranchId()).trim());
		vo.setMakerId(CommonFunction.checkNull(userobj.getUserId()).trim());
		vo.setMakerDate(CommonFunction.checkNull(userobj.getBusinessdate()).trim());
		String dealId=CommonFunction.checkNull(request.getParameter("dealId").trim());
		session.setAttribute("dealId",dealId);
		session.setAttribute("viewDeal","viewDeal");		
		vo.setDealId(dealId);
		
		DealRateApprovalDAO dao=(DealRateApprovalDAO)DaoImplInstanceFactory.getDaoImplInstance(DealRateApprovalDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass()); 
        CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+service.getClass());
       
        // need to write insert code
        boolean result=dao.insertRecordDetail(vo);
        String sms=null;
        ArrayList resultList=new ArrayList();
        resultList=dao.fetchRecordDetail(vo);
 
        String  productCat = service.getProductType(dealId);
		request.setAttribute("productCat", productCat);
		if(CommonFunction.checkNull(productCat).trim().equalsIgnoreCase("EL"))
		{
			request.setAttribute("ELCATEGORY","ELCATEGORY");   	
		}
        String assetOrCollateralResult=dao.isItMachineOrProperty(dealId);  
        if(CommonFunction.checkNull(assetOrCollateralResult).equalsIgnoreCase("MACHINE"))
        {
        	request.setAttribute("MACHINE",assetOrCollateralResult );       	
        }
        if(CommonFunction.checkNull(assetOrCollateralResult).equalsIgnoreCase("PROPERTY"))
        {
        	request.setAttribute("PROPERTY",assetOrCollateralResult );       	
        }
        ArrayList<Object> showCollateralDetails = dao.getCollateralDetailsAllForRateApproval(dealId,assetOrCollateralResult);
        
		request.setAttribute("showCollateralDetails",showCollateralDetails);
		if(result){
			 sms="S";
			request.setAttribute("SMS",sms);
			session.removeAttribute("dealId");
			vo.setDealId("");
			vo.setBranchId("");
			vo.setCustomerName("");
			resultList.add(vo);	
		}
		else{
			sms="E";
			request.setAttribute("SMS",sms);
		}
		request.setAttribute("resultList",resultList); 
		form.reset(mapping, request);
		vo=null;
		dao=null;
		service=null;
		return mapping.findForward("detail");
	}	
	
	public ActionForward fetchRecordDetailForAuthor(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("here in fetchRecordDetailForAuthor()  of DealRateApproval ");
		HttpSession session =  request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null)
		{
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
		request.setAttribute("loginUserId",CommonFunction.checkNull(userobj.getUserId()).trim()); 
		request.setAttribute("loginUserName",CommonFunction.checkNull(userobj.getUserName()).trim()); 
		DealRateApprovalVO vo = new DealRateApprovalVO();
		vo.setBranchId(CommonFunction.checkNull(userobj.getBranchId()).trim());
		String dealId=CommonFunction.checkNull(request.getParameter("dealId").trim());
		String author=CommonFunction.checkNull(request.getParameter("author").trim());
		session.setAttribute("dealId",dealId);  // for asset 
		session.setAttribute("viewDeal","viewDeal");
		session.setAttribute("author",author);// for author detail	
		vo.setDealId(dealId);
		CreditProcessingDAO creditProcessingDAO=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+creditProcessingDAO.getClass());  
        
		ArrayList dealHeader = creditProcessingDAO.getDealHeader(dealId);
		session.setAttribute("dealHeader", dealHeader);
		DealRateApprovalDAO dao=(DealRateApprovalDAO)DaoImplInstanceFactory.getDaoImplInstance(DealRateApprovalDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass()); 
        CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY); // for asset 
        logger.info("Implementation class: "+service.getClass()); // for asset 
        
        String  productCat = service.getProductType(dealId); // for asset 
		session.setAttribute("productCat", productCat); // for asset 
		if(CommonFunction.checkNull(productCat).trim().equalsIgnoreCase("EL"))
		{
			session.setAttribute("ELCATEGORY","ELCATEGORY");   	
		}
		
        ArrayList resultList=new ArrayList();
        resultList=dao.getRateApprovalAuthorDetail(dealId);
        session.setAttribute("resultList",resultList);         
        String assetOrCollateralResult=dao.isItMachineOrProperty(dealId);
        if(CommonFunction.checkNull(assetOrCollateralResult).equalsIgnoreCase("MACHINE"))
        {
        	session.setAttribute("MACHINE",assetOrCollateralResult );       	
        }
        if(CommonFunction.checkNull(assetOrCollateralResult).equalsIgnoreCase("PROPERTY"))
        {
        	session.setAttribute("PROPERTY",assetOrCollateralResult );       	
        }
        ArrayList<Object> showCollateralDetails = dao.getCollateralDetailsAllForRateApproval(dealId,assetOrCollateralResult); // for asset 
		session.setAttribute("showCollateralDetails",showCollateralDetails); // for asset
		String loanAmount="select DEAL_LOAN_AMOUNT from cr_deal_loan_dtl where DEAL_ID="+dealId;
	    String loanAmt=ConnectionDAO.singleReturn(loanAmount);
	     	     				        
	    session.setAttribute("loanAmount", loanAmt);
		vo=null;
		dao=null;
		service=null;
		return mapping.findForward("detail");
	}
	
	public ActionForward openRateChecker(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("here in openRateChecker()  of DealRateApproval ");
		HttpSession session =  request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null)
		{
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
		return mapping.findForward("openRateChecker");	
		}
	public ActionForward saveRateApprovalAuthor(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("here in saveRateApprovalAuthor()  of DealRateApproval ");
		HttpSession session =  request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null)
		{
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
		DynaValidatorForm CommonDealDynaValidatorForm = (DynaValidatorForm) form;
		DealRateApprovalVO vo = new DealRateApprovalVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,CommonDealDynaValidatorForm);
				
		vo.setDealId(CommonFunction.checkNull(session.getAttribute("dealId").toString()).trim());
		vo.setAuthorId(CommonFunction.checkNull(userobj.getUserId()).trim());
		vo.setAuthorDate(CommonFunction.checkNull(userobj.getBusinessdate()).trim());
		
		DealRateApprovalDAO dao=(DealRateApprovalDAO)DaoImplInstanceFactory.getDaoImplInstance(DealRateApprovalDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass()); 
        boolean procResult=dao.saveDealRateAuthorDetail(vo);
        
        String sms=null;
        
        if(procResult){
			sms="S";
			request.setAttribute("message",sms);
			vo.setDealId("");
			vo.setCustomerName("");
			session.removeAttribute("dealId");
			session.removeAttribute("resultList");
		}
		else{
			sms="E";
			request.setAttribute("message",sms);
		}
	    form.reset(mapping, request);
	    vo=null;
	    dao=null;
		return mapping.findForward("saveSuccessfully");	
		}
	
}