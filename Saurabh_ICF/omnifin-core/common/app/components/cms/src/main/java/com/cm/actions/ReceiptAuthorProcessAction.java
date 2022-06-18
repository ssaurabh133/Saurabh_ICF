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
import com.cm.dao.ReceiptDAO;
import com.cm.vo.ReceiptMakerVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.lockRecord.action.LockRecordCheck;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class ReceiptAuthorProcessAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(ReceiptAuthorProcessAction.class.getName());	
	public ActionForward receiptAuthorSearchDetail(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws Exception {
	
		logger.info("In Receipt author search Detail  ");
		
		HttpSession session = request.getSession();

		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId=null;
		String branchId=null;
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}else{
			logger.info(" in  receiptAuthorSearchDetail method of ReceiptAuthorProcessAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		session.removeAttribute("pParentGroup");
		session.removeAttribute("loanID");
		session.removeAttribute("instrumentID");
		session.removeAttribute("businessPartnerType");
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
		ReceiptMakerVO  vo = new ReceiptMakerVO();
		logger.info("current page link .......... "+request.getParameter("d-49520-p"));
		
		int currentPageLink = 0;
		if(request.getParameter("d-1344872-p")==null || request.getParameter("d-1344872-p").equalsIgnoreCase("0"))
		{
			currentPageLink=1;
		}
		else
		{
			currentPageLink =Integer.parseInt(request.getParameter("d-1344872-p"));
		}
		
		logger.info("current page link ................ "+request.getParameter("d-1344872-p"));
		
		vo.setCurrentPageLink(currentPageLink);
		
		//PaymentReceiptBusinessBeanRemote dao = (PaymentReceiptBusinessBeanRemote)LookUpInstanceFactory.getLookUpInstance(PaymentReceiptBusinessBeanRemote.REMOTE_IDENTITY, request);//added by rajani kant
		ReceiptDAO dao=(ReceiptDAO)DaoImplInstanceFactory.getDaoImplInstance(ReceiptDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());
	
		DynaValidatorForm ReceiptMakerDynaValidatorForm = (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, ReceiptMakerDynaValidatorForm);
		if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
		{ 
			vo.setReportingToUserId(userId);
		   
		}
		
		vo.setBranchId(branchId);
		vo.setUserId(userId);

		ArrayList<ReceiptMakerVO> authordetailList = dao.receiptAuthorGrid(vo);
	
		request.setAttribute("authordetailList", authordetailList);
		request.setAttribute("fromAuthor", "fromAuthor");
		vo=null;
		dao=null;
		form.reset(mapping, request);
		return mapping.findForward("receiptAuthorSearch");	
	
	}
	public ActionForward getReceipttoApprove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		HttpSession session=request.getSession();
				
		logger.info("In getReceipttoApprove");
		
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId = null;
		if(userobj!=null){
			userId= userobj.getUserId();
		}else{
			logger.info(" in getReceipttoApprove method of ReceiptAuthorProcessAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		session.removeAttribute("pParentGroup");
		request.setAttribute("fromAuthor", "fromAuthor");
		
		String sessionId = session.getAttribute("sessionID").toString();
		
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
		ReceiptMakerVO  vo = new ReceiptMakerVO();
		String lbxLoanNoHID=request.getParameter("lbxLoanNoHID");
		String lbxBPType=request.getParameter("lbxBPType");
		vo.setLbxLoanNoHID(request.getParameter("lbxLoanNoHID"));
		vo.setInstrumentID(request.getParameter("instrumentID"));
		vo.setLbxBPType(request.getParameter("lbxBPType"));

		//PaymentReceiptBusinessBeanRemote dao = (PaymentReceiptBusinessBeanRemote)LookUpInstanceFactory.getLookUpInstance(PaymentReceiptBusinessBeanRemote.REMOTE_IDENTITY, request);//added by rajani kant
		
		ReceiptDAO dao=(ReceiptDAO)DaoImplInstanceFactory.getDaoImplInstance(ReceiptDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());
		

		
		logger.info("function id is ........................................"+session.getAttribute("functionId").toString());
		String functionId="";

		if(session.getAttribute("functionId")!=null)
		{
			functionId=session.getAttribute("functionId").toString();
		}
	
		if(context!=null)
		{
		boolean flag = LockRecordCheck.lockCheck(userId,functionId,lbxLoanNoHID,context);
		logger.info("Flag ........................................ "+flag);
		if(!flag)
		{
			logger.info("Record is Locked");			
			request.setAttribute("msg", "Locked");
			request.setAttribute("recordId", lbxLoanNoHID);
			request.setAttribute("fromAuthor", "fromAuthor");
			return mapping.findForward("receiptAuthorSearch");
		}
		}
		
		ArrayList<ReceiptMakerVO> receiptApproveList= dao.receiptdatatoApprove(vo);
		
		session.setAttribute("datatoapproveList", receiptApproveList);
		
	//saurabh code starts
		if(receiptApproveList.size()>0){
			//ArrayList receiptApproveList1=(ArrayList)receiptApproveList;
			ReceiptMakerVO rec=(ReceiptMakerVO)receiptApproveList.get(0);			
			logger.info("value of receipt mode :::::::::::"+rec.getReceiptMode());
			String receiptMode=rec.getReceiptMode();
			session.setAttribute("receiptCheck", receiptMode);
				}
	//saurabh code ends
	
		
		 String totalRecevableAmount=dao.getTotalRec(lbxLoanNoHID,lbxBPType);
		
		 session.setAttribute("totalRecevableAmount",totalRecevableAmount);
		
		session.setAttribute("loanID", request.getParameter("lbxLoanNoHID"));
		session.setAttribute("instrumentID", request.getParameter("instrumentID"));
		session.setAttribute("businessPartnerType", request.getParameter("lbxBPType"));
		session.removeAttribute("charges");
		session.removeAttribute("otherAddCharges");
		String chargeFlagQuery=ConnectionDAO.singleReturn("SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='ALLOCATION_GRID_RECEIPT'");
		if(CommonFunction.checkNull(chargeFlagQuery).equalsIgnoreCase("Y"))
		{
			//Nishant space starts for charges
			ArrayList charges= new ArrayList();
			charges = dao.getchargesDetailOnReceipt(vo);
			session.setAttribute("charges",charges);
			ArrayList otherAddCharges = dao.getOtherAddChargesDetailOnReceipt(vo);
			session.setAttribute("otherAddCharges",otherAddCharges);
			ArrayList showTotal = dao.getshowTotalOnReceipt(vo);
			session.setAttribute("showTotal",showTotal);
		}
		String allocationGridReceipt=dao.getAllocationGridReceipt();
		session.setAttribute("allocationGridReceipt",allocationGridReceipt);
		
		String tdsreceiptStatus=dao.getTDSreceiptStatus();
		session.setAttribute("tdsreceiptStatus",tdsreceiptStatus);
		vo=null;
		dao=null;
		
		return mapping.findForward("authorDetail");
	}
	public ActionForward receiptAuthorScreen(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info(" in receiptAuthorScreen method of ReceiptAuthorProcessAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		String sessionId = session.getAttribute("sessionID").toString();	
		
		String strFlag="";	
		
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
		
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
			return mapping.findForward("logout");
		}

		ArrayList<ReceiptMakerVO> approvalList = null;
		if(session.getAttribute("datatoapproveList")!=null)
		{
			approvalList= (ArrayList<ReceiptMakerVO>)session.getAttribute("datatoapproveList");
		}
		if(approvalList.size()>0)
		{
			request.setAttribute("loanRecStatus", CommonFunction.checkNull(approvalList.get(0).getLoanRecStatus()).trim());
		}
		
	
		return mapping.findForward("authorScreen");
	}
	public ActionForward viewAllocatedReceivable(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws Exception {
	
		logger.info("In viewAllocatedReceivable  ");
		
		HttpSession session = request.getSession();
	
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		  String userId=null;
			if(userobj!=null){
				userId= userobj.getUserId();
			}
			else{
				logger.info(" in viewAllocatedReceivable method of ReceiptAuthorProcessAction action the session is out----------------");
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

	    ReceiptMakerVO receiptVO = new ReceiptMakerVO();
	    //PaymentReceiptBusinessBeanRemote dao = (PaymentReceiptBusinessBeanRemote)LookUpInstanceFactory.getLookUpInstance(PaymentReceiptBusinessBeanRemote.REMOTE_IDENTITY, request);//added by rajani kant
		
	    ReceiptDAO dao=(ReceiptDAO)DaoImplInstanceFactory.getDaoImplInstance(ReceiptDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());
		int loanId=0;
		if(!CommonFunction.checkNull(request.getParameter("loanId")).equalsIgnoreCase("")){
			loanId=Integer.parseInt(CommonFunction.checkNull(request.getParameter("loanId")));
		}			
		logger.info("In onAllocatedReceivable---loanId-"+loanId); 
		String bpType=(CommonFunction.checkNull(request.getParameter("bpType")));
		logger.info("In onAllocatedReceivable---bpType-"+bpType); 
		int instrumentId=0;
		if(!CommonFunction.checkNull(request.getParameter("instrumentID")).equalsIgnoreCase("")){
		instrumentId=Integer.parseInt(CommonFunction.checkNull(request.getParameter("instrumentID")));
	     }	
		ArrayList<ReceiptMakerVO> allocatedReceivableList= dao.onAllocatedReceivable(receiptVO,loanId, bpType,instrumentId);
		request.setAttribute("allocatedReceivableList", allocatedReceivableList);
		logger.info("allocatedReceivableList"+allocatedReceivableList.size());
		receiptVO=null;
		dao=null;
		form.reset(mapping, request);
		return mapping.findForward("allocatedReceivable");
		
		
	}
	public ActionForward onSaveReceiptAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		    logger.info("in onSaveAuthor..........");
		    
		    HttpSession session = request.getSession();
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			logger.info("in onSaveAuthor userobject.........."+userobj);
		String userId="";
		String businessDate="";
		if(userobj== null){
				logger.info(" in onSaveReceiptAuthor method of ReceiptAuthorProcessAction action the session is out----------------");
				return mapping.findForward("sessionOut");		
			
		}
		else
		{
			userId=userobj.getUserId();
			businessDate=userobj.getBusinessdate();			
		}
			Object sessionId = session.getAttribute("sessionID");
			
			
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
			
			DynaValidatorForm PaymentCMDynaValidatorForm = (DynaValidatorForm)form;
			ReceiptMakerVO receiptVO = new ReceiptMakerVO();
		
			
			org.apache.commons.beanutils.BeanUtils.copyProperties(receiptVO, PaymentCMDynaValidatorForm);
			receiptVO.setMakerId(userId);
			receiptVO.setBusinessDate(businessDate);
			
			logger.info("In onSaveAuthor,,,,,"+receiptVO.getInstrumentID());
			//PaymentReceiptBusinessBeanRemote dao = (PaymentReceiptBusinessBeanRemote)LookUpInstanceFactory.getLookUpInstance(PaymentReceiptBusinessBeanRemote.REMOTE_IDENTITY, request);//added by rajani kant
			
			ReceiptDAO dao=(ReceiptDAO)DaoImplInstanceFactory.getDaoImplInstance(ReceiptDAO.IDENTITY);
			logger.info("Implementation class: "+dao.getClass());
			
			 String msg="";
			 String resultproc="";
			 
			    String chargeFlagQuery=ConnectionDAO.singleReturn("SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='ALLOCATION_GRID_RECEIPT'");
				if(CommonFunction.checkNull(chargeFlagQuery).equalsIgnoreCase("Y"))
				{
			        resultproc=dao.saveAuthorAllocationReceipt(receiptVO);
				}
				else
				{
				   resultproc=dao.onSaveofReceiptAuthor(receiptVO);
				}
			 logger.info("In onSaveAuthor-------resultproc-->"+resultproc+" decision: "+receiptVO.getDecision());
			 receiptVO.setCompanyId(userobj.getCompanyId());
			 if(CommonFunction.checkNull(resultproc).equalsIgnoreCase("NONE") ){
				 
				 String receiptModeQuery="SELECT INSTRUMENT_MODE FROM CR_INSTRUMENT_DTL WHERE INSTRUMENT_ID='"+receiptVO.getInstrumentID()+"' ";
				 String receiptMode=ConnectionDAO.singleReturn(receiptModeQuery);
				 logger.info("receiptModeQuery: "+receiptModeQuery+" receiptMode: "+receiptMode);
				// Prashant change here
				 String cashAutoDepositQuery="select PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='CASH_AUTO_DEPOSIT'";
				 String cashAutoDepositValue=ConnectionDAO.singleReturn(cashAutoDepositQuery);
				 logger.info("cashAutoDepositQuery: "+cashAutoDepositQuery+" cashAutoDepositValue:  "+cashAutoDepositValue+" receiptMode: "+receiptMode);
				 // Prashant change here
				 String nonCashAutoDepositQuery="select PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='NON_CASH_AUTO_DEPOSIT'";
				 String nonCashAutoDepositValue=ConnectionDAO.singleReturn(nonCashAutoDepositQuery);
				 logger.info("nonCashAutoDepositQuery: "+nonCashAutoDepositQuery+" nonCashAutoDepositValue:  "+nonCashAutoDepositValue+" receiptMode: "+receiptMode);
				 
				 if(CommonFunction.checkNull(cashAutoDepositValue).equalsIgnoreCase("Y") && CommonFunction.checkNull(receiptMode).equalsIgnoreCase("C") && CommonFunction.checkNull(receiptVO.getDecision()).equalsIgnoreCase("A"))
					{
						  logger.info("In receiptForward cashAutoDepositValue"+cashAutoDepositValue);
						
							 	
							    String resultFromCheque = dao.updateDepositChequeFromReceipt(receiptVO);
				            	if(resultFromCheque.equalsIgnoreCase("S"))			    	
				            		request.setAttribute("DSS", "S");
				            	else
				            		request.setAttribute("PROCVAL", resultFromCheque);	
						
					}
					else if(CommonFunction.checkNull(nonCashAutoDepositValue).equalsIgnoreCase("Y") && !CommonFunction.checkNull(receiptMode).equalsIgnoreCase("C") && CommonFunction.checkNull(receiptVO.getDecision()).equalsIgnoreCase("A"))
					{
							   String resultFromCheque = dao.updateDepositChequeFromReceipt(receiptVO);
				            	if(resultFromCheque.equalsIgnoreCase("S"))			    	
				            		request.setAttribute("DSS", "S");
				            	else
				            		request.setAttribute("PROCVAL", resultFromCheque);	
						 
					}
				 
				 msg="S";
				if(receiptVO.getDecision().equalsIgnoreCase("A"))
				  request.setAttribute("messval", "A");
				else if(receiptVO.getDecision().equalsIgnoreCase("X"))
				 request.setAttribute("messval", "X");
				else if(receiptVO.getDecision().equalsIgnoreCase("P"))
				 request.setAttribute("messval", "P");
				 logger.info("00msg"+msg);
				 session.removeAttribute("pParentGroup");
					session.removeAttribute("loanID");
					session.removeAttribute("instrumentID");
					session.removeAttribute("businessPartnerType");
					receiptModeQuery=null;
					cashAutoDepositQuery=null;
					nonCashAutoDepositQuery=null;
			 }
			 else{
				 request.setAttribute("procval", resultproc);
				 msg="E";
				 logger.info("msg1"+msg);
			 }
			 
		request.setAttribute("msg", msg);
		logger.info("In onSaveReceiptAuthor-------loanID-->"+ session.getAttribute("loanID"));
		receiptVO=null;
		dao=null;
		form.reset(mapping, request);
		return mapping.findForward("authorFlag");
	}           

}
