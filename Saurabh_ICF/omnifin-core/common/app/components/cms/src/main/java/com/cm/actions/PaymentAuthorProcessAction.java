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
import com.cm.dao.PaymentDAO;
import com.cm.vo.PaymentMakerForCMVO;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.lockRecord.action.LockRecordCheck;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class PaymentAuthorProcessAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(PaymentAuthorProcessAction.class.getName());
	public ActionForward authorsearchDetail(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws Exception {
	
		logger.info("In authorsearchDetail  ");
		
		HttpSession session = request.getSession();
	//	boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		String userId="";
		String branchId="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}else{
			logger.info(" in  authorsearchDetail method of PaymentAuthorProcessAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		session.removeAttribute("pParentGroup");
		session.removeAttribute("loanID");
		session.removeAttribute("instrumentID");
		session.removeAttribute("businessPartnerType");
		request.setAttribute("fromAuthor", "fromAuthor");
		
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
			
			
			PaymentDAO dao=(PaymentDAO)DaoImplInstanceFactory.getDaoImplInstance(PaymentDAO.IDENTITY);
			//Manish
			//PaymentReceiptBusinessBeanRemote dao=(PaymentReceiptBusinessBeanRemote) LookUpInstanceFactory.getLookUpInstance(PaymentReceiptBusinessBeanRemote.REMOTE_IDENTITY, request);
			logger.info("Implementation class: "+dao.getClass());

		DynaValidatorForm PaymentCMDynaValidatorForm = (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, PaymentCMDynaValidatorForm);
		
		if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
		{ 
			vo.setReportingToUserId(userId);
		  
		}
		logger.info("user Id:::::"+vo.getReportingToUserId());
		vo.setBranchId(branchId);
		vo.setUserId(userId);
		ArrayList<PaymentMakerForCMVO> authordetailList = dao.paymentAuthorGrid(vo);
		
		request.setAttribute("list", authordetailList);
		if((authordetailList.size())==0)
		{
			request.setAttribute("datalist","datalist");
		}
		request.setAttribute("fromAuthor", "fromAuthor");
		form.reset(mapping, request);		
		dao=null;
		vo=null;
		return mapping.findForward("success");		
	}
	public ActionForward getDatatoApprove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		HttpSession session = request.getSession();
		logger.info("In getDatatoApprove...");
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId = "";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
		}else{
			logger.info(" in getDatatoApprove metyhod of PaymentAuthorProcessAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		session.removeAttribute("pParentGroup");
		session.removeAttribute("taStatus");
		session.removeAttribute("loanID");
		session.removeAttribute("businessPartnerType");
		session.removeAttribute("instrumentID");
		boolean flag=false;
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
	
		PaymentMakerForCMVO paymentMakerForCMVO=new PaymentMakerForCMVO();	
		String lbxLoanNoHID=request.getParameter("lbxLoanNoHID");
		paymentMakerForCMVO.setLbxLoanNoHID(request.getParameter("lbxLoanNoHID"));
		paymentMakerForCMVO.setInstrumentID(request.getParameter("instrumentID"));
		paymentMakerForCMVO.setLbxBPType(request.getParameter("lbxBPType"));		
		
		//PaymentReceiptBusinessBeanRemote dao=(PaymentReceiptBusinessBeanRemote) LookUpInstanceFactory.getLookUpInstance(PaymentReceiptBusinessBeanRemote.REMOTE_IDENTITY, request);
		PaymentDAO dao=(PaymentDAO)DaoImplInstanceFactory.getDaoImplInstance(PaymentDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());		
		logger.info("function id is ........................................"+session.getAttribute("functionId").toString());
		String functionId="";
	
		if(session.getAttribute("functionId")!=null)
		{
			functionId=session.getAttribute("functionId").toString();
		}		
		//ServletContext context=getServlet().getServletContext();
		if(context!=null)
		{
		flag = LockRecordCheck.lockCheck(userId,functionId,lbxLoanNoHID,context);
		logger.info("Flag ........................................ "+flag);
		if(!flag)
		{
			logger.info("Record is Locked");	
			request.setAttribute("msg", "Locked");
			request.setAttribute("recordId", lbxLoanNoHID);
			request.setAttribute("fromAuthor", "fromAuthor");
			return mapping.findForward("success");
		}
		}
		ArrayList<PaymentMakerForCMVO> datatoapproveList= dao.saveddatatoApprove(paymentMakerForCMVO);
		session.setAttribute("datatoapproveList", datatoapproveList);			
		session.setAttribute("loanID", request.getParameter("lbxLoanNoHID"));
		session.setAttribute("instrumentID", request.getParameter("instrumentID"));
		session.setAttribute("businessPartnerType", request.getParameter("lbxBPType"));

		if(CommonFunction.checkNull(datatoapproveList.get(0).getTaStatus()).equalsIgnoreCase("Y"))
		{
				 session.setAttribute("taStatus","Y");	
		}
		form.reset(mapping, request);
		paymentMakerForCMVO=null;
		dao=null;
		return mapping.findForward("authorDetail");
	}
	public ActionForward authorScreen(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		logger.info("In authorScreen...");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info(" in authorScreen method of PaymentAuthorProcessAction  action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");

		String strFlag="";	
		
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
		
		logger.info("strFlag .............. "+strFlag);
		//for check User session start
		ServletContext context = getServlet().getServletContext();
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
		
		ArrayList<PaymentMakerForCMVO> approvalList = null;
		if(session.getAttribute("datatoapproveList")!=null)
		{
			approvalList= (ArrayList<PaymentMakerForCMVO>)session.getAttribute("datatoapproveList");
		}
		if(approvalList.size()>0)
		{
			request.setAttribute("loanRecStatus", CommonFunction.checkNull(approvalList.get(0).getLoanRecStatus()).trim());
		}
		form.reset(mapping, request);	
		return mapping.findForward("authorScreen");
	}
		public ActionForward viewAllocatedPayable(ActionMapping mapping,ActionForm form,
				HttpServletRequest request,HttpServletResponse response)
		throws Exception {
		
			logger.info("In viewAllocatedPayable  ");
			
			HttpSession session = request.getSession();			
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			String userId="";
			if(userobj!=null)
			{
				userId=userobj.getUserId();
			}else{
				logger.info(" in viewAllocatedPayable method of PaymentAuthorProcessAction action the session is out----------------");
				return mapping.findForward("sessionOut");
			}
			boolean flag=false;
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
		  
		    PaymentMakerForCMVO paymentMakerForCMVO=new PaymentMakerForCMVO();	
		    //PaymentReceiptBusinessBeanRemote dao=(PaymentReceiptBusinessBeanRemote) LookUpInstanceFactory.getLookUpInstance(PaymentReceiptBusinessBeanRemote.REMOTE_IDENTITY, request);
		    PaymentDAO dao=(PaymentDAO)DaoImplInstanceFactory.getDaoImplInstance(PaymentDAO.IDENTITY);
			logger.info("Implementation class: "+dao.getClass());
			int loanId=0;
			if(!CommonFunction.checkNull(request.getParameter("loanId")).equalsIgnoreCase("")){
				loanId=Integer.parseInt(CommonFunction.checkNull(request.getParameter("loanId")));
			}			
			
			String bpType=(CommonFunction.checkNull(request.getParameter("bpType")));
			
			int instrumentId=0;
			if(!CommonFunction.checkNull(request.getParameter("instrumentID")).equalsIgnoreCase("")){
			instrumentId=Integer.parseInt(CommonFunction.checkNull(request.getParameter("instrumentID")));
		     }	
			
			ArrayList<PaymentMakerForCMVO> allocatedPayableList= dao.onAllocatedPayable(paymentMakerForCMVO,loanId, bpType,instrumentId);
			request.setAttribute("allocatedPayableList", allocatedPayableList);
			form.reset(mapping, request);
			
			bpType=null;
			dao=null;
			paymentMakerForCMVO=null;
			return mapping.findForward("allocatedPayable");
			
			
		}
	public ActionForward onSaveAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		    logger.info("in onSaveAuthor..........");
		    
		    HttpSession session = request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			String userId="";
			String businessDate="";
			if(userobj==null){
				logger.info(" in onSaveAuthor ,ethod of PaymentAuthorProcessAction action the session is out----------------");
				return mapping.findForward("sessionOut");
			}
			else
			{
				userId=userobj.getUserId();
				businessDate=userobj.getBusinessdate();
			}
			Object sessionId = session.getAttribute("sessionID");
			//for check User session start
			
			String strFlag="";	
			
			if(sessionId!=null)
			{
				strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
			}
			logger.info("strFlag .............. "+strFlag);
			ServletContext context = getServlet().getServletContext();
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
			DynaValidatorForm PaymentAuthorDynaValidatorForm = (DynaValidatorForm)form;
			PaymentMakerForCMVO paymentMakerForCMVO=new PaymentMakerForCMVO();	
		
			
			org.apache.commons.beanutils.BeanUtils.copyProperties(paymentMakerForCMVO, PaymentAuthorDynaValidatorForm);
			
			//PaymentReceiptBusinessBeanRemote dao=(PaymentReceiptBusinessBeanRemote) LookUpInstanceFactory.getLookUpInstance(PaymentReceiptBusinessBeanRemote.REMOTE_IDENTITY, request);
			PaymentDAO dao=(PaymentDAO)DaoImplInstanceFactory.getDaoImplInstance(PaymentDAO.IDENTITY);
			logger.info("Implementation class: "+dao.getClass());
			paymentMakerForCMVO.setUserId(userId);
			paymentMakerForCMVO.setBusinessDate(businessDate);
			
			 boolean result=false;
			 String msg="";
			 String procval="";
			 procval=dao.updateFlagByPaymentAuthor(paymentMakerForCMVO);
			 logger.info("In onSaveAuthor......"+procval);

			 if((procval.equals(""))|| procval.equalsIgnoreCase("NONE"))
			 {
				 msg="S";
			 }
			 else{
					request.setAttribute("procval", procval);					
			 }
		request.setAttribute("msg", msg);
        form.reset(mapping, request);
        procval=null;
        paymentMakerForCMVO=null;       
        userId=null;
        businessDate=null;
		return mapping.findForward("authorFlag");
		}        
	}   

