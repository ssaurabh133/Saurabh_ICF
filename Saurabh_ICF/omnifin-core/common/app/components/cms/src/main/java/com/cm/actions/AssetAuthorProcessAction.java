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
import com.cm.dao.assetInsuranceDAO;
import com.cm.vo.AssetForCMVO;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.CreditProcessingDAO;

import com.lockRecord.action.LockRecordCheck;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class AssetAuthorProcessAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(AssetAuthorProcessAction.class.getName());
	public ActionForward authorSearchDetail(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws Exception {
		
		HttpSession session =  request.getSession();
		//boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		String userId="";
		String branchId="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}else{
			logger.info("here in authorSearchDetail method of  AssetAuthorProcessAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		session.removeAttribute("pParentGroup");
		session.removeAttribute("assetID");
		session.removeAttribute("lbxAssetID");
		session.removeAttribute("datatoapproveList");
		session.removeAttribute("loanNo");
		session.removeAttribute("cusName");
		session.removeAttribute("loanId");
		//for check User session start
		AssetForCMVO assetMakervo = new AssetForCMVO();
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
        logger.info("current page link .......... "+request.getParameter("d-1555168-p"));
		
		int currentPageLink = 0;
		if(request.getParameter("d-1555168-p")==null || request.getParameter("d-1555168-p").equalsIgnoreCase("0"))
		{
			currentPageLink=1;
		}
		else
		{
			currentPageLink =Integer.parseInt(request.getParameter("d-1555168-p"));
		}
		
		logger.info("current page link ................ "+request.getParameter("d-1555168-p"));
		logger.info("In authorsearchDetail  ");
		//change by sachin
		assetInsuranceDAO dao=(assetInsuranceDAO)DaoImplInstanceFactory.getDaoImplInstance(assetInsuranceDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass());
        //end by sachin
//		assetInsuranceDAO dao = new assetInsuranceDAOImpl();
        DynaValidatorForm AssetMakerDynaValidatorForm = (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(assetMakervo, AssetMakerDynaValidatorForm);
		if(CommonFunction.checkNull(assetMakervo.getReportingToUserId()).equalsIgnoreCase(""))
		{ 
			assetMakervo.setReportingToUserId(userId);
		   //logger.info("When user id is not selected by the user:::::"+userId);
		}
		logger.info("user Id:::::"+assetMakervo.getReportingToUserId());
		assetMakervo.setCurrentPageLink(currentPageLink);
		assetMakervo.setUserId(userId);
		assetMakervo.setBranchId(branchId);
		ArrayList<AssetForCMVO> authordetailList = dao.assetAuthorGrid(assetMakervo);
		
		request.setAttribute("authordetailList", authordetailList);
		request.setAttribute("fromAuthor", "fromAuthor");
		
		logger.info("authordetailList    Size:---"+authordetailList.size());
		
		return mapping.findForward("authorSearch");	
	}
	
	public ActionForward getDatatoApprove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		logger.info("In getDatatoApprove  ");
		HttpSession session =  request.getSession();		
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		if(userobj!=null)
		{
			userId = userobj.getUserId();
		}else{
			logger.info("here in getDatatoApprove method of AssetAuthorProcessAction  action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		session.removeAttribute("pParentGroup");
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
		request.setAttribute("fromAuthor", "fromAuthor");
		
		session.removeAttribute("assetInsuranceId");
		session.removeAttribute("datatoapproveList");
		session.removeAttribute("insuranceDoneByList");
		session.removeAttribute("showInsuranceRelWithNominee");
		
		AssetForCMVO assetMakervo = new AssetForCMVO(); 
		assetInsuranceDAO dao=(assetInsuranceDAO)DaoImplInstanceFactory.getDaoImplInstance(assetInsuranceDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass());
        		
        String loanId = CommonFunction.checkNull(request.getParameter("loanId"));
        String assetInsuranceId= CommonFunction.checkNull(request.getParameter("assetInsuranceId"));
        String entityType = CommonFunction.checkNull(request.getParameter("entityType"));
        logger.info("assetInsuranceId---"+assetInsuranceId);
		assetMakervo.setAssetInsuranceId(assetInsuranceId);
		assetMakervo.setLoanId(loanId);
		assetMakervo.setEntityType(entityType);
		session.setAttribute("assetInsuranceId", assetInsuranceId);
		request.setAttribute("loanId", loanId);
		
		logger.info("function id is ........................................"+session.getAttribute("functionId").toString());
		String functionId="";

		if(session.getAttribute("functionId")!=null)
		{
			functionId=session.getAttribute("functionId").toString();
		}
		if(context!=null)
		{
		flag = LockRecordCheck.lockCheck(userId,functionId,loanId,context);
		logger.info("Flag ........................................ "+flag);
		if(!flag)
		{
			logger.info("Record is Locked");			
			request.setAttribute("msg", "Locked");
			request.setAttribute("recordId", loanId);
			//request.setAttribute("userId", userId);
			return mapping.getInputForward();
		}
		}
		ArrayList<AssetForCMVO> datatoapproveList= dao.savedDatatoApprove(assetMakervo);
		session.setAttribute("datatoapproveList", datatoapproveList);
		ArrayList<Object> insuranceDoneByList=dao.getInsuranceDoneByList();
		session.setAttribute("insuranceDoneByList", insuranceDoneByList);
		CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
		ArrayList showInsuranceRelWithNominee = service.showInsuranceRelWithNominee();
		session.setAttribute("showInsuranceRelWithNominee", showInsuranceRelWithNominee);
		logger.info("insuranceDoneByList:::::::::::::::::nuppppp"+insuranceDoneByList);
	    return mapping.findForward("authorDetail");
	}
	
	public ActionForward authorScreen(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		logger.info("In authorScreen  ");
		
		HttpSession session =  request.getSession();
	
		//boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in authorScreen method of AssetAuthorProcessAction action the session is out----------------");
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
		assetInsuranceDAO dao=(assetInsuranceDAO)DaoImplInstanceFactory.getDaoImplInstance(assetInsuranceDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass());
		ArrayList<Object> insuranceDoneByList=dao.getInsuranceDoneByList();
		request.setAttribute("insuranceDoneByList", insuranceDoneByList);
		logger.info("insuranceDoneByList:::::::::::::::::yesss"+insuranceDoneByList);
		return mapping.findForward("authorScreen");
	}

	public ActionForward onSaveAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		HttpSession session =  request.getSession();
		//boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in onSaveAuthor method of AssetAuthorProcessAction action the session is out----------------");
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
		    UserObject sessUser=(UserObject)session.getAttribute("userobject");
			logger.info("in onSaveAuthor..........");
			DynaValidatorForm PaymentAuthorDynaValidatorForm = (DynaValidatorForm)form;
			AssetForCMVO assetMakervo = new AssetForCMVO(); 
		
			
			org.apache.commons.beanutils.BeanUtils.copyProperties(assetMakervo, PaymentAuthorDynaValidatorForm);
			
			//change by sachin
			assetInsuranceDAO dao=(assetInsuranceDAO)DaoImplInstanceFactory.getDaoImplInstance(assetInsuranceDAO.IDENTITY);
	        logger.info("Implementation class: "+dao.getClass());
	        //end by sachin
//			assetInsuranceDAO dao = new assetInsuranceDAOImpl();
			String userId="";
			String currDate="";

		    int companyId=0;
			if(sessUser!=null)
			{
				userId=sessUser.getUserId();
				currDate=sessUser.getBusinessdate();
				companyId=sessUser.getCompanyId();
			}
			assetMakervo.setCompanyId(companyId);
		
			assetMakervo.setMakerId(userId);
			assetMakervo.setBusinessDate(currDate);
	
		
		    String status=CommonFunction.checkNull(assetMakervo.getDecision());
		   
		    
		    String comments=CommonFunction.checkNull(assetMakervo.getComments());
		  
		    String result="";
			String msg="";
		    String assetInsuranceId=CommonFunction.checkNull(session.getAttribute("assetInsuranceId"));    
		    logger.info("In onSaveAuthor..assetInsuranceId.."+assetInsuranceId);
			 result=dao.approveByAuthor(assetMakervo,userId,companyId,currDate,assetInsuranceId,comments,status);
			 logger.info("In onSaveAuthor,,,,,"+result);
		   
				 if(result.equalsIgnoreCase("S")){
					 msg="S";
					 logger.info("00msg"+msg);
					 
			
				 }
					else{
						msg="E";
					request.setAttribute("procval", result);
					    logger.info("msg1"+msg);
					   
					}
					request.setAttribute("msg", msg);
					logger.info("procval"+result);
					
		return mapping.findForward("authorFlag");
	}        
	


}
