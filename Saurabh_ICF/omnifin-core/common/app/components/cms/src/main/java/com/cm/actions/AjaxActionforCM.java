package com.cm.actions;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.VO.CustomerSaveVo;
import com.cm.dao.AdditionalDisbursalDAO;
import com.cm.dao.CancellationDAO;
import com.cm.dao.ChangeRateDAO;
import com.cm.dao.CreditManagementDAO;
import com.cm.dao.DisbCancellationDAO;
import com.cm.dao.DisbursalInitiationDAO;
import com.cm.dao.EarlyClosureDAO;
import com.cm.dao.FileTrackingDAO;
import com.cm.dao.HandSightingDAO;
import com.cm.dao.LinkLoanDAO;
import com.cm.dao.LoanInitiationDAO;
import com.cm.dao.ManualAdviceDAO;
import com.cm.dao.ManualIntCalcDAO;
import com.cm.dao.PartPrePaymentDAO;
import com.cm.dao.PaymentDAO;
import com.cm.dao.PdcViewerDao;
import com.cm.dao.ReceiptDAO;
import com.cm.dao.RepayScheduleDAO;
import com.cm.dao.RepoBillingApprovalMakerDAO;
import com.cm.dao.ReportsDAO;
import com.cm.dao.RepricingDAO;
import com.cm.dao.SDLiquidationDAO;
import com.cm.dao.WaiveOffDAO;
import com.cm.dao.assetVerificationDAO;
import com.cm.vo.AdditionalDisbursalProcessVO;
import com.cm.vo.AssetVerificationVO;
import com.cm.vo.CancellationVO;
import com.cm.vo.ChangeRateVo;
import com.cm.vo.ClosureVO;
import com.cm.vo.DeferralMakerVo;
import com.cm.vo.DisbCancellationVO;
import com.cm.vo.DisbursalMakerVO;
import com.cm.vo.InstructionCapMakerVO;
import com.cm.vo.LiquidationMakerVO;
import com.cm.vo.LoanDetailForCMVO;
import com.cm.vo.ManualAdviceCreationVo;
import com.cm.vo.ManualIntCalcVO;
import com.cm.vo.PartPrePaymentMakerVO;
import com.cm.vo.PaymentMakerForCMVO;
import com.cm.vo.ReceiptMakerVO;
import com.cm.vo.RepoBillingApprovalMakerVO;
import com.cm.vo.RepricingMakerVo;
import com.cm.vo.UpdateAssetVO;
import com.cm.vo.UpdateRepayscheduleSearchVO;
import com.cm.vo.WaiveOffVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.CreditProcessingDAO;
import com.cp.dao.DealClosureDAO;
import com.cp.dao.OcrDAO;
// import com.cp.dao.OcrDAO;//add by Abhishek 
import com.cp.vo.CodeDescVo;
import com.cp.vo.DealCancellationVo;
import com.gcd.dao.CorporateDAO;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class AjaxActionforCM extends DispatchAction {
	private static final Logger logger = Logger.getLogger(AjaxActionforCM.class.getName());
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	public ActionForward chequeDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session =  request.getSession();
		//boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		if(userobj==null){			
				logger.info("here in chequeDetails method of AjaxActionforCM action the session is out----------------");
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
		//PaymentMakerForCMVO paymentMakerForCMVO=new PaymentMakerForCMVO();	
		String accountType =request.getParameter("accountType");
		logger.info("chequeDetail accountType---"+accountType);
		
	    String bankAccountId=(CommonFunction.checkNull(request.getParameter("bankAccountId")));		
		logger.info("chequeDetail bankAccountId---"+request.getParameter("bankAccountId"));
		PaymentDAO dao=(PaymentDAO)DaoImplInstanceFactory.getDaoImplInstance(PaymentDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());
		
		ArrayList<PaymentMakerForCMVO> getChequeList= dao.getchequeDetail(bankAccountId,accountType);
	    request.setAttribute("getChequeList", getChequeList);
		logger.info("getChequeList    Size:---"+getChequeList.size());
		return mapping.findForward("chequeDetail");
	}
	
	public ActionForward getTotalReceivable(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("in getTotalReceivable()");
		HttpSession session =  request.getSession();
		//boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){			
			logger.info("here in getTotalReceivable mathod of action the session is out----------------");
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
		String loanId=request.getParameter("loanId");
		String BPType=request.getParameter("BPType");
		logger.info("loanId  :  "+loanId);
		logger.info("BPType  :  "+BPType);
		ReceiptDAO dao=(ReceiptDAO)DaoImplInstanceFactory.getDaoImplInstance(ReceiptDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());
	
		String amount=dao.getTotalRec(loanId,BPType);
		logger.info("Total Receivable   :  "+amount);
		request.setAttribute("amount",amount);
		return mapping.findForward("checkAllocation");
	}
	
	public ActionForward chequeDetailR(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session =  request.getSession();
		//boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){			
			logger.info("here in chequeDetailR mathod of action the session is out----------------");
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
		String accountType =request.getParameter("accountType");
		logger.info(" chequeDetailR accountType---"+accountType);
		 String bankAccount=(CommonFunction.checkNull(request.getParameter("BankAccount")));
		logger.info(" chequeDetailR BankAccount######"+request.getParameter("BankAccount"));
		ReceiptDAO dao=(ReceiptDAO)DaoImplInstanceFactory.getDaoImplInstance(ReceiptDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());
		ArrayList<ReceiptMakerVO> getChequeListR= dao.getchequeDetailR(bankAccount,accountType);
		request.setAttribute("getChequeListR", getChequeListR);
		logger.info("getChequeListR    Size:---"+getChequeListR.size());
		return mapping.findForward("chequeDetailR");
	}
	public ActionForward retriveCutInsValues(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		logger.info("In retriveCutInsValues Method---------");
		
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){			
			logger.info("here in retriveCutInsValues  method of  AjaxActionforCM action the session is out----------------");
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
		int lbxLoanNoHID=Integer.parseInt(request.getParameter("lbxLoanNoHID"));

		logger.info(" In retriveCutInsValues the retriveCutInsValues---"+lbxLoanNoHID);
		//change by sachin
		CreditManagementDAO dao=(CreditManagementDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditManagementDAO.IDENTITY);
	     logger.info("Implementation class: "+dao.getClass());

		//end by sachin
//		CreditManagementDAO dao = new CreditManagementDAOImpl();
		ArrayList<InstructionCapMakerVO> retriveValues= dao.getretriveCutInsValues(lbxLoanNoHID);

		request.setAttribute("retriveValues", retriveValues);
		logger.info("loanDetailList    Size:111---"+retriveValues.size());
		return mapping.findForward("showretriveCutIns");
	
	}


	public ActionForward retriveClosureValues(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		throws Exception {

			logger.info("In AjaxActionforCM retriveClosureValues()---------");
			
			HttpSession session =  request.getSession();
			//boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			String bDate="";
			if(userobj!=null)
			{
				bDate=userobj.getBusinessdate();
			}else{
				logger.info("here in retriveClosureValues method of AjaxActionforCM  action the session is out----------------");
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
			int lbxLoanNoHID=Integer.parseInt(request.getParameter("lbxLoanNoHID"));

			String businessDate = CommonFunction.checkNull(bDate);
			logger.info(" In retriveClosureValues the retriveClosureValues---"+lbxLoanNoHID);
			//EarlyClosureDAO dao = new EarlyClosureDAOImpl();
			EarlyClosureDAO dao=(EarlyClosureDAO)DaoImplInstanceFactory.getDaoImplInstance(EarlyClosureDAO.IDENTITY);
			logger.info("Implementation class: "+dao.getClass());
			ArrayList<ClosureVO> retriveValues= dao.getClosureValues(lbxLoanNoHID,businessDate);

			request.setAttribute("retriveValues", retriveValues);
			logger.info("loanDetailList    Size:222---"+retriveValues.size());

			return mapping.findForward("showretriveClosureValues");
		}

	public ActionForward retriveDuesRefundsValues(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		logger.info("In AjaxActionforCM Class---------retriveDuesRefundsValues");
		
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String companyId="";
		if(userobj!=null)
		{
			companyId=userobj.getCompanyId()+"";
		}else{
			logger.info("here in retriveDuesRefundsValues  method of AjaxActionforCM action the session is out----------------");
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
	
		
		String loanId = CommonFunction.checkNull(request.getParameter("loanId"));
		String effectiveDate = CommonFunction.checkNull(request.getParameter("effectiveDate"));
		String closureType = CommonFunction.checkNull(request.getParameter("closureType"));
		logger.info("Inside AjaxActionforCM... loan ID: "+loanId+" effectiveDate: "+effectiveDate+" closureType: "+closureType);
		//CreditManagementDAO dao = new CreditManagementDAOImpl();
		EarlyClosureDAO dao=(EarlyClosureDAO)DaoImplInstanceFactory.getDaoImplInstance(EarlyClosureDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());
		ArrayList<ClosureVO> duesRefundsList= dao.getDuesRefundsList(companyId,loanId,effectiveDate,closureType,"closure");
		request.setAttribute("duesRefundsList", duesRefundsList);
		return mapping.findForward("showDuesRefund");
	}

	
		public ActionForward getDefaultWaiveOffDetail(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)throws Exception
		{
			
			HttpSession session =  request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here in getDefaultWaiveOffDetail method of AjaxActionforCM  action the session is out----------------");
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
			
			try
			{
			logger.info("In getDefaultWaiveOffDetail ......... ");
			
			String chargeCode = request.getParameter("chargeCode");
			
			String[]arr=chargeCode.split(",");
			chargeCode=arr[0].trim();
			String loanid=arr[1].trim();
			String bp_type=arr[2].trim();
			String txn_Advice_Id=arr[3].trim();
			
			logger.info("chargeCode: "+chargeCode);
			logger.info("loanid: "+loanid);
			logger.info("bp_type: "+bp_type);
			logger.info("txn_Advice_Id: "+txn_Advice_Id);
			
			WaiveOffVO vo= new WaiveOffVO();
			//CreditManagementDAO creditMgmtDao= new CreditManagementDAOImpl();
			WaiveOffDAO creditMgmtDao=(WaiveOffDAO)DaoImplInstanceFactory.getDaoImplInstance(WaiveOffDAO.IDENTITY);
			logger.info("Implementation class: "+creditMgmtDao.getClass());
			
			ArrayList getDefaultWaiveOffDetail = creditMgmtDao.getWaiveOffDetailScheme(chargeCode,loanid,bp_type,txn_Advice_Id);
			WaiveOffVO vo1= new WaiveOffVO();
			vo1=(WaiveOffVO)getDefaultWaiveOffDetail.get(0);
		//	ArrayList list = (ArrayList)getDefaultWaiveOffDetail.get(0);
			logger.info("In getDefaultWaiveOffDetail Action..size.."+getDefaultWaiveOffDetail.size());
			request.setAttribute("getDefaultWaiveOffDetail", getDefaultWaiveOffDetail);
			logger.info("In getDefaultWaiveOffDetail Amount In Process"+vo1.getAmountInProcess());
			}
			catch(Exception e)
			{
			e.printStackTrace();
			}
			return mapping.findForward("waiveOffSuccess");
		}
		
		public ActionForward retriveDisbursalValues(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		throws Exception {

			logger.info("In retriveDisbursalValues Class---------");
			
			HttpSession session =  request.getSession();
			//boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			String strFlag="";	

				String userId="";
					String bDate="";
					if(userobj!=null)
					{
						userId=userobj.getUserId();
						bDate=userobj.getBusinessdate();
					}else{
						logger.info("here in retriveDisbursalValues AjaxActionforCM method of  action the session is out----------------");
						return mapping.findForward("sessionOut");
					}
			Object sessionId = session.getAttribute("sessionID");
			//for check User session start
			ServletContext context = getServlet().getServletContext();
	

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
			int lbxLoanNoHID=Integer.parseInt(request.getParameter("lbxLoanNoHID"));

			logger.info(" In retriveDisbursalValues ---"+lbxLoanNoHID);
			//DisbursalInitiationDAO dao = new DisbursalInitiationDAOImpl();
			DisbursalInitiationDAO dao=(DisbursalInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(DisbursalInitiationDAO.IDENTITY);
			logger.info("Implementation class: "+dao.getClass());
			ArrayList<DisbursalMakerVO> retriveDisbursalValues= dao.getDisbursalValues(lbxLoanNoHID,userId,bDate);
			String amountInProcessLoan=dao.getAmountInProcessLoan(lbxLoanNoHID,userId,bDate);
			// add by saorabh 
			String recStatusForPartPayment=dao.getRecStatusForPartPayment(lbxLoanNoHID,userId,bDate);
			request.setAttribute("recStatusForPartPayment", recStatusForPartPayment);
			// end by saorabh
		
			logger.info("amountInProcessLoan----------"+amountInProcessLoan);
			request.setAttribute("amountInProcessLoan", amountInProcessLoan);
			request.setAttribute("retriveDisbursalValues", retriveDisbursalValues);
			
			logger.info("loanDetailList    Size:333---"+retriveDisbursalValues.size());
			//add by ajay
			DisbursalMakerVO disbursalMakerVO = retriveDisbursalValues.get(0);
			String interestDueDate=disbursalMakerVO.getInterestDueDate();
			logger.info("interestDueDate----"+interestDueDate);			
			logger.info("VO Object fetched: "+ disbursalMakerVO.getEditDueDate() + "----------" + disbursalMakerVO.getOldRepayEffDate());
			request.setAttribute("editDueDate", disbursalMakerVO.getEditDueDate());
			request.setAttribute("loanTenure", disbursalMakerVO.getLoanTenure()); //added by Brijesh Pathak
			request.setAttribute("InstallmentType", disbursalMakerVO.getInstallmentType()); //added by Brijesh Pathak
			logger.info("Installment type    Size:333---"+ disbursalMakerVO.getInstallmentType());
			logger.info("loanTenure    Size:444---"+ disbursalMakerVO.getLoanTenure());
			request.setAttribute("oldMaturityDate", disbursalMakerVO.getOldRepayEffDate());
			
			request.setAttribute("interestDueDate", interestDueDate);
			//add by ajay
			return mapping.findForward("showretriveDisbursalValues");
		}
		//neeraj
		public ActionForward validateFormNo(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
		{

			logger.info("In validateFormNo() of AjaxActionforCM Class---------");			
			HttpSession session =  request.getSession();
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			String strFlag="";	
			String userId="";
			String bDate="";
			if(userobj!=null)
			{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
			}
			else
			{
				logger.info("here in validateFormNo AjaxActionforCM method of  action the session is out----------------");
				return mapping.findForward("sessionOut");
			}
			Object sessionId = session.getAttribute("sessionID");
			ServletContext context = getServlet().getServletContext();
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
			String loanDealId=request.getParameter("loanDealId");
			String formNo=request.getParameter("formNo");
			
			logger.info("loanDealId           :   "+loanDealId);
			logger.info("formNo               :   "+formNo);
			LoanInitiationDAO dao=(LoanInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(LoanInitiationDAO.IDENTITY);
			logger.info("Implementation class: "+dao.getClass()); 
			String validateFormNo= dao.validateFormNo(loanDealId,formNo);
			request.setAttribute("validateFormNo",validateFormNo);
			return mapping.findForward("showretriveDisbursalValues");
		}		
		public ActionForward getPayeeName(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
		{

			logger.info("In getPayeeName() of AjaxActionforCM Class---------");			
			HttpSession session =  request.getSession();
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			String strFlag="";	
			if(userobj==null)
			{
				logger.info("here in validateFormNo AjaxActionforCM method of  action the session is out----------------");
				return mapping.findForward("sessionOut");
			}
			Object sessionId = session.getAttribute("sessionID");
			ServletContext context = getServlet().getServletContext();
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
			String lbxLoanNoHID=request.getParameter("lbxLoanNoHID");
						
			logger.info("lbxLoanNoHID           :   "+lbxLoanNoHID);
			LoanInitiationDAO dao=(LoanInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(LoanInitiationDAO.IDENTITY);
			logger.info("Implementation class: "+dao.getClass()); 
			ArrayList PayeeList= dao.getPayeeName(lbxLoanNoHID);
			request.setAttribute("PayeeList",PayeeList);
			return mapping.findForward("ajaxDealClosure");
		}
		public ActionForward calculateEMI(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
		{

			logger.info("In calculateEMI() of AjaxActionforCM Class---------");			
			HttpSession session =  request.getSession();
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			String strFlag="";	
			String userId="";
			String bDate="";
			if(userobj!=null)
			{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
			}
			else
			{
				logger.info("here in retriveDisbursalValues AjaxActionforCM method of  action the session is out----------------");
				return mapping.findForward("sessionOut");
			}
			Object sessionId = session.getAttribute("sessionID");
			ServletContext context = getServlet().getServletContext();
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
			String loan_id=request.getParameter("lbxLoanNoHID");
			String disbursalAmount=request.getParameter("disbursalAmount");
			String disbursalDate=request.getParameter("disbursalDate");
			String repayEffDate=request.getParameter("repayEffDate");
			String disbursalStatus=request.getParameter("disbursalStatus");
			
			logger.info("loan_id           :   "+loan_id);
			logger.info("disbursalAmount   :   "+disbursalAmount);
			logger.info("disbursalDate     :   "+disbursalDate);
			logger.info("bDate             :   "+bDate);
			logger.info("userId            :   "+userId);
			logger.info("repayEffDate      :   "+repayEffDate);
			logger.info("disbursalStatus   :   "+disbursalStatus);
			
			
			int lbxLoanNoHID=Integer.parseInt(loan_id);

			logger.info("In calculateEMI() of AjaxActionforC  Loan ID  :  "+lbxLoanNoHID);
			DisbursalInitiationDAO dao=(DisbursalInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(DisbursalInitiationDAO.IDENTITY);
			logger.info("Implementation class: "+dao.getClass());
			ArrayList<DisbursalMakerVO> EMIList= dao.calculateEMI(disbursalAmount,disbursalDate,loan_id,bDate,userId,repayEffDate,disbursalStatus);
			request.setAttribute("calculatedEMI",EMIList);
			return mapping.findForward("showretriveDisbursalValues");
		}
		
		public ActionForward openPopForProduct(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			
			HttpSession session =  request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here in openPopForProduct method of AjaxActionforCM  action the session is out----------------");
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
			int dealNo=Integer.parseInt(request.getParameter("dealNo"));
			//LoanInitiationDAO dao = new LoanInitiationDAOImpl();
			LoanInitiationDAO dao=(LoanInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(LoanInitiationDAO.IDENTITY);
			logger.info("Implementation class: "+dao.getClass()); 
			ArrayList<LoanDetailForCMVO> loanList= dao.getLoanListOfProduct(dealNo);
			logger.info("loanList : "+loanList);
			request.setAttribute("loanList", loanList);
			ArrayList list1 = dao.getPaymentModes();
			session.setAttribute("paymentModes", list1);
			return mapping.findForward("openPop");
		}
		public ActionForward retriveValueByProduct(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			
			HttpSession session =  request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			String businessDate="";
			if(userobj != null){
				businessDate=userobj.getBusinessdate();
			}	else{
				logger.info("here in openPopForProduct method of AjaxActionforCM  action the session is out----------------");
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
		
			
			int dealLoanId=Integer.parseInt(request.getParameter("dealLoanId"));
			logger.info(" In retriveValueByProduct the retriveValueByProduct---"+dealLoanId);
			LoanInitiationDAO dao=(LoanInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(LoanInitiationDAO.IDENTITY);
			logger.info("Implementation class: "+dao.getClass()); 
			ArrayList<LoanDetailForCMVO> loanDetailList= dao.getListOfValues(dealLoanId,businessDate);
			
			request.setAttribute("loanDetailList", loanDetailList);
			logger.info("loanDetailList    Size:444---"+loanDetailList.size());
			
			CreditProcessingDAO creditDao=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
	        logger.info("Implementation class: "+creditDao.getClass());
			ArrayList cycleDueDay = creditDao.getCycleDueDay(""+dealLoanId,"LIM");
			request.setAttribute("cycleDueDay", cycleDueDay);
			session.setAttribute("cycle", cycleDueDay);
			ArrayList baseRateList = creditDao.getBaseRateList(businessDate);
			request.setAttribute("baseRateList", baseRateList);
			//amandeep starts
			ArrayList list1 = creditDao.getPaymentModes();
			session.setAttribute("paymentModes", list1);
			//amandeep ends
			return mapping.findForward("showLoanDetail");
		}

		
		public ActionForward getDefaultManualDetail(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		{
			try
			{
				HttpSession session =  request.getSession();
			logger.info("In getDefaultManualDetail ......... ");
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here in getDefaultManualDetail method of AjaxActionforCM  action the session is out----------------");
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
			
			 String chargeCode=CommonFunction.checkNull(request.getParameter("chargeCode"));
			 String lbxProductId=CommonFunction.checkNull(request.getParameter("lbxProductId"));
			 String lbxSchemeId=CommonFunction.checkNull(request.getParameter("lbxSchemeId"));
			 String lbxLoanNoHID=CommonFunction.checkNull(request.getParameter("lbxLoanNoHID"));
			  String lbxCalculatedOn=CommonFunction.checkNull(request.getParameter("lbxCalculatedOn"));
			
			logger.info("chargeCode: "+chargeCode);
			logger.info("lbxProductId: "+lbxProductId);
			logger.info("lbxSchemeId: "+lbxSchemeId);
			logger.info("Loan ID::::::::: "+lbxLoanNoHID);
			logger.info("lbxCalculatedOn::::::::: "+lbxCalculatedOn);
			
			String loanAmountQuery="SELECT sum(advice_amount) FROM cr_txnadvice_dtl WHERE LOAN_ID='"+lbxLoanNoHID+"' and charge_code_id=(select calculated_on from com_charges_m where SCHEME_ID='"+lbxSchemeId+"' and PRODUCT_ID='"+lbxProductId+"' and charge_code='"+chargeCode+"' )and rec_status='A' ";
			String loanAmount=ConnectionDAO.singleReturn(loanAmountQuery);
			logger.info("loanAmountQuery: "+loanAmountQuery+"laonAmount: "+loanAmount);
			logger.info("Loan Amount::::::::: "+loanAmount);
			ManualAdviceCreationVo vo= new ManualAdviceCreationVo();
			//CreditManagementDAO creditMgmtDao= new CreditManagementDAOImpl();
			//ManualAdviceDAO creditMgmtDao=new ManualAdviceDAOImpl();
			ManualAdviceDAO creditMgmtDao=(ManualAdviceDAO)DaoImplInstanceFactory.getDaoImplInstance(ManualAdviceDAO.IDENTITY);
			logger.info("Implementation class: "+creditMgmtDao.getClass()); 
			ArrayList manualDefaultDetail = creditMgmtDao.getManualAdviceDetailScheme(chargeCode,lbxProductId,lbxSchemeId,loanAmount);
			logger.info("In getDefaultManualDetail Action..size.."+manualDefaultDetail.size());
			
			//vo=(ManualAdviceCreationVo) manualDefaultDetail.get(0);
			
			//int chargeAmount=Integer.parseInt(vo.getChargeAmount());

			request.setAttribute("manualDefaultDetail", manualDefaultDetail);
			}
			catch(Exception e)
			{
			e.printStackTrace();
			}
			return mapping.findForward("manualAdviceSuccess");
		}

		
	
		
		public ActionForward retriveLoanForLiquidationValues(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		throws Exception {

			logger.info("In AjaxActionforCM Class---------retriveLoanForLiquidationValues");
			
			HttpSession session =  request.getSession();
			
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here in retriveLoanForLiquidationValues method of AjaxActionforCM  action the session is out----------------");
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
			String lbxLoanNoHID=CommonFunction.checkNull(request.getParameter("lbxLoanNoHID"));

			logger.info(" In retriveClosureValues the retriveLoanForLiquidationValues---"+lbxLoanNoHID);
			SDLiquidationDAO dao=(SDLiquidationDAO)DaoImplInstanceFactory.getDaoImplInstance(SDLiquidationDAO.IDENTITY);
			logger.info("Implementation class: "+dao.getClass());
			
			ArrayList<LiquidationMakerVO> retriveLoanForLiquidationValues= dao.getLoanForLiquidationValues(lbxLoanNoHID);

			request.setAttribute("retriveLoanForLiquidationValues", retriveLoanForLiquidationValues);
			logger.info("loanDetailList    Size:555---"+retriveLoanForLiquidationValues.size());

			return mapping.findForward("showretriveLoanForLiquidationValues");
		}
		
		public ActionForward retriveLiquidationValues(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		throws Exception {

			logger.info("In AjaxActionforCM Class---------retriveLiquidationValues");
			
			HttpSession session =  request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here in retriveLiquidationValues method of AjaxActionforCM  action the session is out----------------");
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
			String lbxLoanNoHID=CommonFunction.checkNull(request.getParameter("lbxLoanNoHID"));
			String lbxSdNoHid=CommonFunction.checkNull(request.getParameter("lbxSdNoHid"));
			SDLiquidationDAO dao=(SDLiquidationDAO)DaoImplInstanceFactory.getDaoImplInstance(SDLiquidationDAO.IDENTITY);
			logger.info("Implementation class: "+dao.getClass());
			ArrayList<LiquidationMakerVO> retriveLiquidationValues= dao.getLiquidationValues(lbxLoanNoHID,lbxSdNoHid);
			request.setAttribute("retriveLiquidationValues", retriveLiquidationValues);
			logger.info("loanDetailList    Size:666---"+retriveLiquidationValues.size());

			return mapping.findForward("showretriveLiquidationValues");
		}
		
		public ActionForward retriveCancellationValues(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		throws Exception {

			logger.info("In AjaxActionforCM Class---------retriveCancellationValues");
			
			HttpSession session =  request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here in retriveCancellationValues method of AjaxActionforCM  action the session is out----------------");
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
			int lbxLoanNoHID=Integer.parseInt(request.getParameter("lbxLoanNoHID"));
			String businessDate= CommonFunction.checkNull(request.getParameter("businessDate"));
			logger.info(" In retriveCancellationValues the retriveCancellationValues---"+lbxLoanNoHID);
//change by sachin
			CancellationDAO dao=(CancellationDAO)DaoImplInstanceFactory.getDaoImplInstance(CancellationDAO.IDENTITY);
		    logger.info("Implementation class: "+dao.getClass());
//end by sachin			
	//	    CancellationDAO dao = new CancellationDAOImpl();
			ArrayList<CancellationVO> cancellationValues= dao.getCancellationValues(lbxLoanNoHID,businessDate);

			request.setAttribute("cancellationValues", cancellationValues);
			logger.info("loanDetailList    Size:777---"+cancellationValues.size());

			return mapping.findForward("showretriveCancellationValues");
		}

		public ActionForward retriveAssetInitValues(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		throws Exception {

			logger.info("In AjaxActionforCM Class---------retriveAssetInitValues");
			
			HttpSession session =  request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here in retriveAssetInitValues method of AjaxActionforCM  action the session is out----------------");
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
			String lbxLoanNo=CommonFunction.checkNull(request.getParameter("lbxLoanNo"));
			logger.info(" In retriveAssetInitValues the retriveAssetVerificationInitValues---"+lbxLoanNo);
//change by sachin
			assetVerificationDAO dao=(assetVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(assetVerificationDAO.IDENTITY);
		    logger.info("Implementation class: "+dao.getClass()); 
//end by sachin
//		    assetVerificationDAO dao = new assetVerificationDAOImpl();
			ArrayList<AssetVerificationVO> assetList= dao.getValueForAssetVerInit(lbxLoanNo);

			request.setAttribute("assetList", assetList);

			logger.info("retriveAssetInitValues    Size:---"+assetList.size());
			return mapping.findForward("assetVerInitList");
		}
		
		public ActionForward checkDealSanctionVaildTill(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		throws Exception {

			logger.info("In AjaxActionforCM Class---------checkDealSanctionVaildTill");
			
			HttpSession session =  request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here in checkDealSanctionVaildTill method of AjaxActionforCM  action the session is out----------------");
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
			String dealId=CommonFunction.checkNull(request.getParameter("dealId"));
			String businessDate = CommonFunction.checkNull(request.getParameter("businessDate"));
			logger.info(" In checkDealSanctionVaildTill the dealId---"+dealId);
			LoanInitiationDAO dao=(LoanInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(LoanInitiationDAO.IDENTITY);
			logger.info("Implementation class: "+dao.getClass()); 
			String sanctionStatus = dao.checkDealSanctionVaildTill(dealId,businessDate);
			logger.info("sanctionStatus from Action: "+sanctionStatus);
			request.setAttribute("checkDealValidTill", sanctionStatus);
			return mapping.findForward("checkDealSanctionVaildTill");
		}
		
		public ActionForward generateSDAccrual(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		throws Exception {

			logger.info("In AjaxActionforCM Class---------generateSDAccrual");
			
			HttpSession session =  request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			String userId="";
			String bDate="";
			int companyId =0;
			if(userobj!=null)
			{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
				companyId= userobj.getCompanyId();
			}else{
				logger.info("here in generateSDAccrual method of AjaxActionforCM action the session is out----------------");
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



	
			String loanId=CommonFunction.checkNull(request.getParameter("lbxLoanNoHID"));
			String sdNo=CommonFunction.checkNull(request.getParameter("sdNo"));
			
			logger.info(" In generateSDAccrual the loanId---"+loanId);
			logger.info(" In generateSDAccrual the sdNo---"+sdNo);
			
			SDLiquidationDAO dao=(SDLiquidationDAO)DaoImplInstanceFactory.getDaoImplInstance(SDLiquidationDAO.IDENTITY);
			logger.info("Implementation class: "+dao.getClass());
			ArrayList<LiquidationMakerVO> sdAccrualValues = dao.generateSDAccrualValues(bDate,companyId,userId,loanId,sdNo);
			request.setAttribute("sdAccrualValues",sdAccrualValues);
			return mapping.findForward("generateSDAccrual");
		}
		
		public ActionForward retriveCycleDate(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		throws Exception {

			logger.info("In AjaxActionforCM Class---------retriveCycleDate");
			
			HttpSession session =  request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here in retriveCycleDate method of AjaxActionforCM action the session is out----------------");
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
//			CreditManagementDAO dao = new CreditManagementDAOImpl();
			DisbursalInitiationDAO dao1=(DisbursalInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(DisbursalInitiationDAO.IDENTITY);
			logger.info("Implementation class: "+dao1.getClass());
			String lbxLoanNoHID = CommonFunction.checkNull(request.getParameter("lbxLoanNoHID"));
			//ArrayList<DisbursalMakerVO> cycleDate = dao1.getCycleDateList();
			CreditProcessingDAO creditProcessing=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
	        logger.info("Implementation class: "+creditProcessing.getClass()); 	
			ArrayList cycleDate = creditProcessing.getCycleDateList(""+lbxLoanNoHID,"LIM");
			request.setAttribute("cycleDate", cycleDate);
			logger.info(" In the retriveCycleDate---cycleDate-------"+cycleDate);
			ArrayList<DisbursalMakerVO> loanDueDay = dao1.getLoanDueDay(lbxLoanNoHID);
			request.setAttribute("loanDueDay",loanDueDay);
			return mapping.findForward("retriveCycleDate");
		}
		
		public ActionForward retrivePartPrePaymentValues(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		throws Exception {

			logger.info("In AjaxActionforCM Class---------retrivePartPrePaymentValues");
			
			HttpSession session =  request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here in retriveCycleDate method of AjaxActionforCM action the session is out----------------");
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
			PartPrePaymentDAO dao=(PartPrePaymentDAO)DaoImplInstanceFactory.getDaoImplInstance(PartPrePaymentDAO.IDENTITY);
			logger.info("Implementation class: "+dao.getClass());
			String lbxLoanNoHID = CommonFunction.checkNull(request.getParameter("lbxLoanNoHID"));
			
			ArrayList<PartPrePaymentMakerVO> partPrePaymetList = dao.retrievePartPrePaymentValues(lbxLoanNoHID);
			request.setAttribute("partPrePaymetList",partPrePaymetList);
			
			// add by saorabh 
			String recStatusForDisbursal=dao.getRecStatusForDisbursal(lbxLoanNoHID);
			request.setAttribute("recStatusForDisbursal", recStatusForDisbursal);
			// end by saorabh
			

		    PartPrePaymentMakerVO processVO=null;
			String instType = "";
			if(partPrePaymetList!=null && partPrePaymetList.size()>0)
			{
			 processVO = partPrePaymetList.get(0);
			 instType = CommonFunction.checkNull(processVO.getInstType());
			 ArrayList genericMasterList =null;
			 if(instType.equalsIgnoreCase("E") ||instType.equalsIgnoreCase("R") || instType.equalsIgnoreCase("G"))
			  genericMasterList = dao.getGenericMasterInfo("RESCHEDULE_TYPE1");
			 else if(instType.equalsIgnoreCase("P") ||instType.equalsIgnoreCase("Q") || instType.equalsIgnoreCase("S"))
				 genericMasterList = dao.getGenericMasterInfo("RESCHEDULE_TYPE2");
			 
			request.setAttribute("genericMasterList", genericMasterList);
			}
			return mapping.findForward("retrivePartPrePaymentValues");
		}
		
		public ActionForward retriveDeferralValues(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		throws Exception {

			logger.info("In AjaxActionforCM Class---------retriveDeferralValues");
			
			HttpSession session =  request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			String bDate="";
			if(userobj!=null)
			{
				bDate=userobj.getBusinessdate();
			}else{
				logger.info("here retriveDeferralValues method AjaxActionforCM action the session is out----------------");
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
			
			//change by sachin
			CreditManagementDAO dao=(CreditManagementDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditManagementDAO.IDENTITY);
		    logger.info("Implementation class: "+dao.getClass());

			//end by sachin
//			CreditManagementDAO dao = new CreditManagementDAOImpl();
			String lbxLoanNoHID = CommonFunction.checkNull(request.getParameter("lbxLoanNoHID"));

			String deferralFeasibility = dao.deferralMakerFeasibility(lbxLoanNoHID,bDate);
			logger.info("deferralFeasibility: "+deferralFeasibility);
			if(deferralFeasibility.equalsIgnoreCase("deferralAllowedYear"))
			{
				request.setAttribute("feasibility","deferralAllowedYear");
			}
			if(deferralFeasibility.equalsIgnoreCase("minGapSuccDeferralValid"))
			{
				request.setAttribute("feasibility","minGapSuccDeferralValid");
			}
			if(deferralFeasibility.equalsIgnoreCase("totalDeferralAllowedExceeds"))
			{
				request.setAttribute("feasibility","totalDeferralAllowedExceeds");
			}
			if(deferralFeasibility.equalsIgnoreCase("lockinPeriod"))
			{
				request.setAttribute("feasibility","lockinPeriod");
			}
			if(deferralFeasibility.equalsIgnoreCase(""))
			{
				ArrayList<DeferralMakerVo> deferralList = dao.retriveDeferralValues(lbxLoanNoHID);
				request.setAttribute("deferralList",deferralList);
			}
			return mapping.findForward("retriveDeferralValues");
		}
		
		public ActionForward generateReschCharges(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		throws Exception {

			logger.info("In AjaxActionforCM Class---------generateReschCharges");
			
			HttpSession session =  request.getSession();
			//boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			int companyId =0;
			if(userobj!=null)
			{
			companyId = userobj.getCompanyId();
			}else{
				logger.info("here generateReschCharges  method of AjaxActionforCM  action the session is out----------------");
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
		
			String functionId="";
			if(session.getAttribute("functionId")!=null)
			{
				functionId=session.getAttribute("functionId").toString();
			}
			
			//change by sachin
			CreditManagementDAO dao=(CreditManagementDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditManagementDAO.IDENTITY);
		    logger.info("Implementation class: "+dao.getClass());

			//end by sachin
//			CreditManagementDAO dao = new CreditManagementDAOImpl();
			String lbxLoanNoHID = CommonFunction.checkNull(request.getParameter("lbxLoanNoHID"));
			String reschDate = CommonFunction.checkNull(request.getParameter("reschDate"));
			String partPaymentAmt = "";
			if(CommonFunction.checkNull(request.getParameter("partPaymentAmt")).equalsIgnoreCase(""))
				partPaymentAmt="0.0";
			else
				partPaymentAmt = CommonFunction.checkNull(request.getParameter("partPaymentAmt"));
			
			ArrayList reschCharges = dao.generateReschCharges(lbxLoanNoHID,Double.parseDouble(partPaymentAmt),reschDate,Integer.parseInt(functionId),companyId);
			request.setAttribute("reschCharges",reschCharges);
			
			return mapping.findForward("generateReschCharges");
		}
		
		public ActionForward retriveRepricingValues(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		throws Exception {

			logger.info("In AjaxActionforCM Class---------retriveRepricingValues");
			
			HttpSession session =  request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			
			//for check User session start
			String bDate="";
			if(userobj!=null)
			{
				bDate=userobj.getBusinessdate();
			}else{
				logger.info("here retriveRepricingValues method of AjaxActionforCM  action the session is out----------------");
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
			RepricingDAO dao=(RepricingDAO)DaoImplInstanceFactory.getDaoImplInstance(RepricingDAO.IDENTITY);
			logger.info("Implementation class: "+dao.getClass());
			
			String lbxLoanNoHID = CommonFunction.checkNull(request.getParameter("lbxLoanNoHID"));
			String rePricingFeasibility = dao.repricingMakerFeasibility(lbxLoanNoHID,bDate);
			logger.info("rePricingFeasibility: "+rePricingFeasibility);
			if(rePricingFeasibility.equalsIgnoreCase("repricingAllowedYear"))
			{
				request.setAttribute("feasibility","repricingAllowedYear");
			}
			if(rePricingFeasibility.equalsIgnoreCase("minGapSuccRepricingValid"))
			{
				request.setAttribute("feasibility","minGapSuccRepricingValid");
			}
			if(rePricingFeasibility.equalsIgnoreCase("totalRepricingAllowedExceeds"))
			{
				request.setAttribute("feasibility","totalRepricingAllowedExceeds");
			}
			if(rePricingFeasibility.equalsIgnoreCase("lockinPeriod"))
			{
				request.setAttribute("feasibility","lockinPeriod");
			}
			if(rePricingFeasibility.equalsIgnoreCase(""))
			{
				ArrayList<RepricingMakerVo> rePricingList = dao.retriveRepricingValues(lbxLoanNoHID);
				request.setAttribute("rePricingList",rePricingList);
				ArrayList<CodeDescVo> baseRateList = dao.getBaseRateList(bDate);
				request.setAttribute("baseRateList",baseRateList);
			}
			return mapping.findForward("retriveRepricingValues");
		}
		
		
		public ActionForward checkAllocation(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			HttpSession session =  request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here checkAllocation method of AjaxActionforCM  action the session is out----------------");
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
			
		    String instrumentID=(CommonFunction.checkNull(request.getParameter("instrumentID")));    
			
			logger.info("checkAllocation instrumentID---"+request.getParameter("instrumentID"));
			
            String TDS=(CommonFunction.checkNull(request.getParameter("TDS")));			
			logger.info("checkAllocation TDS---"+request.getParameter("TDS"));
			receiptVO.setInstrumentID(instrumentID);
			receiptVO.setTdsAmount(TDS);
			//change by sachin
			CreditManagementDAO dao=(CreditManagementDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditManagementDAO.IDENTITY);
		     logger.info("Implementation class: "+dao.getClass());

			//end by sachin
//			CreditManagementDAO dao = new CreditManagementDAOImpl();
			int result=dao.checkAllocation(receiptVO);
			
			if(result > 0){
				request.setAttribute("allocated", "Y");
			}else{
				request.setAttribute("allocated", "N");
			}
			return mapping.findForward("checkAllocation");
		}
		
		
		public ActionForward checkingForAllocation(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			HttpSession session =  request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here checkingForAllocation method of AjaxActionforCM  action the session is out----------------");
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
			
		    String instrumentID=(CommonFunction.checkNull(request.getParameter("instrumentID")));    
			
			logger.info("checkAllocation instrumentID---"+request.getParameter("instrumentID"));
			
            String TDS=(CommonFunction.checkNull(request.getParameter("TDS")));			
			logger.info("checkAllocation TDS---"+request.getParameter("TDS"));
			receiptVO.setInstrumentID(instrumentID);
			receiptVO.setTdsAmount(TDS);
			//change by sachin
			CreditManagementDAO dao=(CreditManagementDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditManagementDAO.IDENTITY);
		     logger.info("Implementation class: "+dao.getClass());

			//end by sachin
//			CreditManagementDAO dao = new CreditManagementDAOImpl();
			//ArrayList<ReceiptMakerVO> checkFesiblityOnForward=dao.checkFesiblityOnForward(receiptVO);
			
		//	request.setAttribute("checkAllocation",checkFesiblityOnForward);
			
			return mapping.findForward("checkAllocationForward");
		}

		
		public ActionForward retriveAddDisbValues(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		throws Exception {

			logger.info("In AjaxActionforCM Class---------retriveAddDisbValues");
			
			HttpSession session =  request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here in retriveAddDisbValues method of AjaxActionforCM action the session is out----------------");
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
			//change by sachin
			 AdditionalDisbursalDAO dao=(AdditionalDisbursalDAO)DaoImplInstanceFactory.getDaoImplInstance(AdditionalDisbursalDAO.IDENTITY);
		    logger.info("Implementation class: "+dao.getClass());

			//end by sachin
			
//			AdditionalDisbursalDAO dao = new AdditionalDisbursalDAOImpl();
			String lbxLoanNoHID = CommonFunction.checkNull(request.getParameter("lbxLoanNoHID"));
			
			ArrayList<AdditionalDisbursalProcessVO> addDisb = dao.retrieveAdditionDisbValues(lbxLoanNoHID);
			request.setAttribute("addDisb",addDisb);
			String instType = "";
			AdditionalDisbursalProcessVO processVO=null;
			if(addDisb!=null && addDisb.size()>0)
			{
				logger.info("........ADD_DIS_RES_TYPE.........");
			 processVO = addDisb.get(0);
			 instType = CommonFunction.checkNull(processVO.getInstType());
			 logger.info("getInstallmentType........"+instType);
			 ArrayList genericMasterList =null;
			 if(instType.equalsIgnoreCase("E") ||instType.equalsIgnoreCase("R") || instType.equalsIgnoreCase("G"))
			  genericMasterList = dao.getGenericMasterInfo("RESCHEDULE_TYPE1");
			 else if(instType.equalsIgnoreCase("P") ||instType.equalsIgnoreCase("Q") || instType.equalsIgnoreCase("S"))
				 genericMasterList = dao.getGenericMasterInfo("ADD_DIS_RES_TYPE");
			 logger.info("genericMasterList---------->>>>>>>>"+genericMasterList.size());
			session.setAttribute("genericMasterList", genericMasterList);
			}
			return mapping.findForward("retriveAddDisbValues");
		}
		
		public ActionForward retriveRepayScheduleValues(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		throws Exception {

			logger.info("In AjaxActionforCM Class---------retriveRepayScheduleValues");
			
			HttpSession session =  request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			
			//for check User session start
			String bDate="";
			if(userobj!=null)
			{
				bDate=userobj.getBusinessdate();
			}else{
				logger.info("here retriveRepayScheduleValues method of AjaxActionforCM  action the session is out----------------");
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
			RepayScheduleDAO dao=(RepayScheduleDAO)DaoImplInstanceFactory.getDaoImplInstance(RepayScheduleDAO.IDENTITY);
			logger.info("Implementation class: "+dao.getClass());
			
			String lbxLoanNoHID = CommonFunction.checkNull(request.getParameter("lbxLoanNoHID"));
		
			
			   
				ArrayList<UpdateRepayscheduleSearchVO> repayScheduleList = dao.retriveRepayScheduleValues(lbxLoanNoHID);
		
				request.setAttribute("repayScheduleList",repayScheduleList);
				
			return mapping.findForward("retriveRepayScheduleValues");
		}
		public ActionForward copyAddress(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
		{

			logger.info("In copyAddress of AjaxActionforCM");			
			HttpSession session =  request.getSession();
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			//for check User session start
			if(userobj==null)
			{
				logger.info("here retriveRepayScheduleValues method of AjaxActionforCM  action the session is out----------------");
				return mapping.findForward("sessionOut");
			}
			Object sessionId = session.getAttribute("sessionID");
			ServletContext context = getServlet().getServletContext();
			String strFlag="";	
			if(sessionId!=null)
				strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
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
			
			//code added by neeraj 
			String functionId=(String)session.getAttribute("functionId");
			if(CommonFunction.checkNull(functionId).trim().equalsIgnoreCase(""))
				functionId="0";
			int id=Integer.parseInt(functionId);
			String source="GCD";
			if(id==3000206)
				source="DEAL";
			//neeraj space end 
			
			CorporateDAO dao=(CorporateDAO)DaoImplInstanceFactory.getDaoImplInstance(CorporateDAO.IDENTITY);
			logger.info("Implementation class: "+dao.getClass());
			String addressId = CommonFunction.checkNull(request.getParameter("addressId"));
			logger.info("In copyAddress()  addressId  :  "+addressId);			   
			ArrayList<CustomerSaveVo> copyAddressList = dao.copyAddress(addressId,source);		
			request.setAttribute("copyAddressList",copyAddressList);				
			
			 if(session.getAttribute("corporateId")!=null )
			 {
			 ArrayList<Object> list2 = dao.getRelationShipFlagCorporate();
			 request.setAttribute("relationType", list2); 
			 }
			 if((session!=null && session.getAttribute("idividualId")!=null))
			 {
			 ArrayList<Object> list3 = dao.getRelationShipFlagIndividual();
				request.setAttribute("relationType", list3);
			 }
			return mapping.findForward("copyAddress");
		}
		
		public ActionForward calculatePDC(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
		{

			logger.info("In calculatePDC of AjaxActionforCM");			
			HttpSession session =  request.getSession();
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			//for check User session start
			if(userobj==null)
			{
				logger.info("here calculatePDC method of AjaxActionforCM  action the session is out----------------");
				return mapping.findForward("sessionOut");
			}
			Object sessionId = session.getAttribute("sessionID");
			ServletContext context = getServlet().getServletContext();
			String strFlag="";	
			if(sessionId!=null)
				strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
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
			PdcViewerDao dao=(PdcViewerDao)DaoImplInstanceFactory.getDaoImplInstance(PdcViewerDao.IDENTITY);
	        logger.info("Implementation class: "+dao.getClass());
			//PdcViewerDao dao = new PdcViewerDAOImpl();
			String loanId = CommonFunction.checkNull(request.getParameter("loanId"));
			logger.info("In calculatePDC()  loanId  :  "+loanId);			   
			ArrayList list = dao.calculatePDC(loanId);	
			dao=null;
			loanId=null;
			String presented=(String)list.get(0);
			String toBePresented=(String)list.get(1);	
			list.clear();
			list=null;
			request.setAttribute("calculatePDCList","calculatePDCList");
			request.setAttribute("presented",presented);
			presented=null;
			request.setAttribute("toBePresented",toBePresented);
			toBePresented=null;
			return mapping.findForward("checkAllocation");
		}
		public ActionForward getChargesDetail(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
		{

			logger.info("In getChargesDetail of AjaxActionforCM");			
			HttpSession session =  request.getSession();
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			//for check User session start
			if(userobj==null)
			{
				logger.info("here getChargesDetail method of AjaxActionforCM  action the session is out----------------");
				return mapping.findForward("sessionOut");
			}
			Object sessionId = session.getAttribute("sessionID");
			ServletContext context = getServlet().getServletContext();
			String strFlag="";	
			if(sessionId!=null)
				strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
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
			ReportsDAO dao = (ReportsDAO)DaoImplInstanceFactory.getDaoImplInstance(ReportsDAO.IDENTITY);
			logger.info("Implementation class: "+dao.getClass());
			
			String loanId = CommonFunction.checkNull(request.getParameter("loanId"));
			logger.info("In getChargesDetail()  loanId  :  "+loanId);			   
			ArrayList list = dao.getChargesDetail(loanId);	
			dao=null;
			loanId=null;
			request.setAttribute("chargeDetail",list);
			return mapping.findForward("fcChargeDetail");
		}
		//method added by Nishant
		public ActionForward fetchDealCanData(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
		{

			logger.info("In fetchDealCanData of AjaxActionforCM");			
			HttpSession session =  request.getSession();
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			//for check User session start
			if(userobj==null)
			{
				logger.info("here fetchDealCanData method of AjaxActionforCM  action the session is out----------------");
				return mapping.findForward("sessionOut");
			}
			Object sessionId = session.getAttribute("sessionID");
			ServletContext context = getServlet().getServletContext();
			String strFlag="";	
			if(sessionId!=null)
				strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
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
			String dealId = CommonFunction.checkNull(request.getParameter("dealId"));
			logger.info("In fetchDealCanData()  dealId  :  "+dealId);
			DealClosureDAO dao=(DealClosureDAO)DaoImplInstanceFactory.getDaoImplInstance(DealClosureDAO.IDENTITY);
	        logger.info("Implementation class: "+dao.getClass()); 	//changed by asesh
			//DealClosureDAO dao = new DealClosureDAOImpl();
			ArrayList<DealCancellationVo> list = dao.fetchDealCanData(dealId);	
			logger.info("list.size  : "+list.size());
			request.setAttribute("closureList",list);
			return mapping.findForward("ajaxDealClosure");
		}
		
		
		
	
		
		
//		public ActionForward loanViewerClosureCheck(ActionMapping mapping, ActionForm form,
//				HttpServletRequest request, HttpServletResponse response)
//		throws Exception {
//
//			logger.info("In AjaxActionforCM Class---------loanViewerClosureCheck");
//			
//			HttpSession session =  request.getSession();
//			boolean flag=false;
//			UserObject userobj=(UserObject)session.getAttribute("userobject");
//			Object sessionId = session.getAttribute("sessionID");
//			//for check User session start
//			if(sessionId!=null)
//			{
//				flag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString());
//			}
//			
//			if(flag)
//			{
//				logger.info("logout in action");
//				return mapping.findForward("logout");
//			}
//			
//			CreditManagementDAO dao = new CreditManagementDAOImpl();
//			String loanIDMain = CommonFunction.checkNull(request.getParameter("loanIDMain"));
//			
//			String feasibility = dao.loanViewerClosureCheck(loanIDMain);
//			//logger.info("feasibility  : "+feasibility);
//			String loanId="";
//			String status="";
//			if(feasibility.charAt(0)=='T')
//			{
//				status = "T";
//				loanId = feasibility.substring(1);
//				request.setAttribute("status",status);
//				request.setAttribute("loanId",loanId);
//			}
//			else
//			{
//				status = "F";
//				request.setAttribute("status",status);
//			}
//			logger.info("loanId : "+loanId);
//			return mapping.findForward("loanViewerClosureCheck");
//		}
//method added by Anil	
		
		public ActionForward getChangeRateLoanDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		logger.info("In AjaxActionforCM Class---------getChangeRateLoanDetail");
		
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in getChangeRateLoanDetail method of AjaxActionforCM  action the session is out----------------");
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
		String lbxLoanNo=CommonFunction.checkNull(request.getParameter("loanId"));
		logger.info(" In getChangeRateLoanDetail the retriveAssetVerificationInitValues---"+lbxLoanNo);
//change by sachin
		   ChangeRateDAO dao=(ChangeRateDAO)DaoImplInstanceFactory.getDaoImplInstance(ChangeRateDAO.IDENTITY);
	       logger.info("Implementation class: "+dao.getClass());
//end by sachin	
//	    ChangeRateDAO dao= new ChangeRateDAOImpl();
		boolean status=dao.checkStatus(lbxLoanNo);
		
			ArrayList<ChangeRateVo> detailList= dao.getValueForChangeRate(lbxLoanNo);
			request.setAttribute("rateDetailList", detailList);
			request.setAttribute("status", status);
			logger.info("getChangeRateLoanDetail    Size:---"+detailList.size());	
		
		
		return mapping.findForward("ChangeRateLoanList");
}
		public ActionForward getProductManualDetail(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
			throws Exception {

			logger.info("In AjaxActionforCM Class---------getProductManualDetail");
			
			HttpSession session =  request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here in getProductManualDetail method of AjaxActionforCM  action the session is out----------------");
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
			 String lbxLoanNoHID=CommonFunction.checkNull(request.getParameter("lbxLoanNoHID"));
			 logger.info(" In getProductManualDetail the retriveAssetVerificationInitValues---"+lbxLoanNoHID);
					
				String productQuery="SELECT LOAN_PRODUCT FROM cr_loan_dtl WHERE LOAN_ID='"+lbxLoanNoHID+"'";
				String productId=ConnectionDAO.singleReturn(productQuery);
				logger.info("productQuery: "+productQuery+"productId: "+productId);
				request.setAttribute("productId", productId);
			
				logger.info("getProductManualDetail  productId:---"+productId);	
			
			
			return mapping.findForward("productManualDetail");
	}
			
public ActionForward getShortPayOnDisbursalTo(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		throws Exception {

			logger.info("In AjaxActionforCM Class getShortPayOnDisbursalTo()- --------");
			
			HttpSession session =  request.getSession();
			//boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			String strFlag="";	

				String userId="";
					String bDate="";
					if(userobj!=null)
					{
						userId=userobj.getUserId();
						bDate=userobj.getBusinessdate();
					}else{
						logger.info("here in getShortPayOnDisbursalTo method of  action the session is out----------------");
						return mapping.findForward("sessionOut");
					}
			Object sessionId = session.getAttribute("sessionID");
			//for check User session start
			ServletContext context = getServlet().getServletContext();
	

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
			int lbxLoanNoHID=Integer.parseInt(request.getParameter("lbxLoanNoHID"));
			String disbursalTo = request.getParameter("disbursalTo");
			
			logger.info(" In getShortPayOnDisbursalTo---"+lbxLoanNoHID);
			
			try{
				
				DisbursalInitiationDAO dao=(DisbursalInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(DisbursalInitiationDAO.IDENTITY);
				logger.info("Implementation class: "+dao.getClass());
			ArrayList<DisbursalMakerVO> retriveDisbursalValues= dao.getShortpayOnDisbursalTo(lbxLoanNoHID,userId,bDate,disbursalTo);
			DisbursalMakerVO vo = retriveDisbursalValues.get(0);
			logger.info("adjustedShortPayAmount :---"+vo.getAdjustedShortPayAmount());
			logger.info("proposedShortPayAmount :---"+vo.getProposedShortPayAmount());
			request.setAttribute("adjustedShortPayAmount", vo.getAdjustedShortPayAmount());
			request.setAttribute("proposedShortPayAmount", vo.getProposedShortPayAmount());
			
			session.setAttribute("disbursalTo", disbursalTo);
			logger.info("disbursalTo ,,,,,,,,,,,,, "+disbursalTo);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			return mapping.findForward("showretriveShortPayValues");
		}
			

		public ActionForward setSuppManufIdInSess(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
			logger.info("In AjaxActionforCM Class setSuppManufIdInSess()- --------");
			
			HttpSession session =  request.getSession();
		
			String bpId = CommonFunction.checkNull(request.getParameter("bpId"));
			String bpType = CommonFunction.checkNull(request.getParameter("bpType"));
			logger.info(" In supManufId---"+bpId);
			logger.info(" In bpType---"+bpType);
			
			session.setAttribute("supManufId", bpId);
			session.setAttribute("bpType", bpType);
			return mapping.findForward("supManufId");
		}
	
// Surendra Code goes here.....		
		public ActionForward retriveManualIntCalcValues(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		throws Exception {
			logger.info("In AjaxActionforCM retriveManualIntCalcValues()---------");			
			HttpSession session =  request.getSession();
			//boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			String bDate="";
			if(userobj!=null)
			{
				bDate=userobj.getBusinessdate();
			}else{
				logger.info("here in retriveManualIntCalcValues method of AjaxActionforCM  action the session is out----------------");
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
			int lbxLoanNoHID=Integer.parseInt(request.getParameter("lbxLoanNoHID"));

			String businessDate = CommonFunction.checkNull(bDate);
			logger.info(" In retriveManualIntCalcValues the retriveManualIntCalcValues---"+lbxLoanNoHID);
			//ManualIntCalcDAO dao = new ManualIntCalcDAOImpl();
			ManualIntCalcDAO dao=(ManualIntCalcDAO)DaoImplInstanceFactory.getDaoImplInstance(ManualIntCalcDAO.IDENTITY);
			logger.info("Implementation class: "+dao.getClass()); 
			ArrayList<ManualIntCalcVO> retriveValues= dao.getMICValues(lbxLoanNoHID,businessDate);

			request.setAttribute("retriveValues", retriveValues);
			logger.info("loanDetailList    Size:888---"+retriveValues.size());

			return mapping.findForward("retriveMICValues");
		}
				
		
		
		public ActionForward getDetails(ActionMapping mapping, ActionForm form,

				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			
			HttpSession session =  request.getSession();
			
			//boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			String userId="";		
			String bDate ="";
			String companyId="";
			String calledFrom="ECM";
			if(userobj!=null){
				userId= userobj.getUserId();
				bDate=userobj.getBusinessdate();
				companyId=userobj.getCompanyId()+"";
			}else{
				logger.info("here in generateAdvice method of ManualIntCalcDispatchAction action the session is out----------------");
				return mapping.findForward("sessionOut");
			}	
			logger.info("companyId .............. "+companyId);		
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
			
			
			String loanId = CommonFunction.checkNull(request.getParameter("loanId"));
			ManualIntCalcVO vo =new ManualIntCalcVO();
			vo.setLbxLoanNoHID(loanId);
			vo.setMakerId(userId);
			vo.setDate(bDate);
	        vo.setCompanyId(companyId);
	        vo.setCalledFrom(calledFrom);
			String status="";	
			ManualIntCalcDAO dao=(ManualIntCalcDAO)DaoImplInstanceFactory.getDaoImplInstance(ManualIntCalcDAO.IDENTITY);
			logger.info("Implementation class: "+dao.getClass()); 
			
            ArrayList<ManualIntCalcVO> getDetails= dao.getDetails(vo);			
			request.setAttribute("getDetails", getDetails);
			return mapping.findForward("getDetailsSuccess");	
		}
		public ActionForward retriveDisbCancellationValues(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		throws Exception {

			logger.info("In AjaxActionforCM Class---------retriveDisbCancellationValues");
			
			HttpSession session =  request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here in retriveDisbCancellationValues method of AjaxActionforCM  action the session is out----------------");
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
			int lbxLoanNoHID=Integer.parseInt(request.getParameter("lbxLoanNoHID"));			
			logger.info(" In retriveDisbCancellationValues the retriveCancellationValues---"+lbxLoanNoHID);
			DisbCancellationDAO dao=(DisbCancellationDAO)DaoImplInstanceFactory.getDaoImplInstance(DisbCancellationDAO.IDENTITY);
			logger.info("Implementation class: "+dao.getClass()); 
			//DisbCancellationDAO dao = new DisbCancellationDAOImpl();
			ArrayList<DisbCancellationVO> disbCancellationValues= dao.getCancellationValues(lbxLoanNoHID);

			request.setAttribute("disbCancellationValues", disbCancellationValues);
			logger.info("loanDetailList    Size:999---"+disbCancellationValues.size());

			return mapping.findForward("showretriveDisbCancellationValues");
		}
		
		//Surendra Code End here
		
		
		//Anil Code Start here
		
		public ActionForward getDealNumberValues(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		throws Exception {

			logger.info("In AjaxActionforCM Class---------getDealNumberValues");
			
			HttpSession session =  request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here in getDealNumberValues method of AjaxActionforCM  action the session is out----------------");
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
			String lbxLoanNoHID=CommonFunction.checkNull(request.getParameter("lbxLoanNoHID"));			
			logger.info(" In getDealNumberValues the getDealNumberValues---"+lbxLoanNoHID);
			String handSightingFlag=null;
			if(!(CommonFunction.checkNull(session.getAttribute("handSighting")).equalsIgnoreCase("")))
				handSightingFlag= session.getAttribute("handSighting").toString();
			logger.info("handSightingFlag : " + handSightingFlag);
			ArrayList dealNumberValues;
			if(CommonFunction.checkNull(handSightingFlag).equalsIgnoreCase(""))
			{
				FileTrackingDAO dao=(FileTrackingDAO)DaoImplInstanceFactory.getDaoImplInstance(FileTrackingDAO.IDENTITY);
				logger.info("Implementation class: "+dao.getClass()); 
				dealNumberValues = dao.getDealNumberValues(lbxLoanNoHID);
			}
			else
			{
				HandSightingDAO dao = (HandSightingDAO) DaoImplInstanceFactory.getDaoImplInstance(HandSightingDAO.IDENTITY);
				logger.info("Implementation class: "+dao.getClass()); 
				dealNumberValues= dao.getDealNumberValues(lbxLoanNoHID);
			}
			
			if(dealNumberValues.size()>0){
			logger.info("dealNumberValues::::::::::: "+dealNumberValues); 
			request.setAttribute("dealNumberValues", dealNumberValues);
			}
			return mapping.findForward("getDealNumberValues");
		}
		
		
		public ActionForward checkFileStatus(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		throws Exception {

			logger.info("In AjaxActionforCM Class---------checkFileStatus");
			
			HttpSession session =  request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here in checkFileStatus method of AjaxActionforCM  action the session is out----------------");
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
			String lbxLoanNoHID=CommonFunction.checkNull(request.getParameter("lbxLoanNoHID"));			
			logger.info(" In checkFileStatus the checkFileStatus---"+lbxLoanNoHID);
			FileTrackingDAO dao=(FileTrackingDAO)DaoImplInstanceFactory.getDaoImplInstance(FileTrackingDAO.IDENTITY);
			logger.info("Implementation class: "+dao.getClass()); 
			ArrayList checkFileStatus= dao.checkFileStatus(lbxLoanNoHID);
			if(checkFileStatus.size()>0){
			logger.info("checkFileStatus::::::::::: "+checkFileStatus); 
			request.setAttribute("checkFileStatus", checkFileStatus);
			}
			return mapping.findForward("checkFileTrackingStatus");
		}
		
		//Anil Code End here
		//saurabh changes starts
		public ActionForward retriveLinkLoanValues(ActionMapping mapping,ActionForm form,
										HttpServletRequest request,HttpServletResponse response)throws Exception{			
			logger.info("in retriveLinkLoanValues of ajaxaction :::::::::::::::::::::::::");
			HttpSession session =  request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here in checkFileStatus method of AjaxActionforCM  action the session is out----------------");
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
			
			String loanId=CommonFunction.checkNull(request.getParameter("lbxLoanNoHID"));
			logger.info(" In retriveLinkLoanValues the loan id is:::::::---"+loanId);
			LinkLoanDAO dao=(LinkLoanDAO)DaoImplInstanceFactory.getDaoImplInstance(LinkLoanDAO.IDENTITY);
			logger.info("Implementation class: "+dao.getClass()); 
			ArrayList linkLoanList= dao.getLinkLoans(loanId);
			if(linkLoanList.size()>0){
			logger.info("linkLoanList::::::::::: "+linkLoanList); 
			request.setAttribute("linkLoanList", linkLoanList);
			}
			
			return mapping.findForward("retriveLinkLoan");
		}
		//saurabh changes ends
		
		public ActionForward getBranchIdMicrReceipt(ActionMapping mapping,ActionForm form,
				HttpServletRequest request,HttpServletResponse response)throws Exception{			
		logger.info("in getBranchIdMicrReceipt of ajaxaction :::::::::::::::::::::::::");
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
		logger.info("here in getBranchIdMicrReceipt method of AjaxActionforCM  action the session is out----------------");
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
		
		String depositBankAccount=CommonFunction.checkNull(request.getParameter("depositBankAccount"));
		String depositBankID=CommonFunction.checkNull(request.getParameter("depositBankID"));
		String depositIfscCode=CommonFunction.checkNull(request.getParameter("depositIfscCode"));
		String receiptMode=CommonFunction.checkNull(request.getParameter("receiptMode"));
		
		logger.info("depositBankAccount : "+depositBankAccount+" depositBankID : " + depositBankID + " depositIfscCode : " + depositIfscCode + " receiptMode : " + receiptMode);
		ReceiptDAO dao=(ReceiptDAO)DaoImplInstanceFactory.getDaoImplInstance(ReceiptDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass()); 
		ArrayList receiptBranchMicrList= dao.getBranchIdMicr(depositBankAccount,depositBankID,depositIfscCode,receiptMode);
		if(receiptBranchMicrList.size()>0){
		logger.info("linkLoanList::::::::::: "+receiptBranchMicrList); 
		request.setAttribute("receiptBranchMicrList", receiptBranchMicrList);
		}
		
		return mapping.findForward("retriveBranchMicr");
		}
		
		public ActionForward getDefaultBusinessPartnerTypeReceipt(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			HttpSession session =  request.getSession();
			//boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			
			if(userobj==null){			
					logger.info("here in getDefaultBusinessPartnerTypeReceipt method of AjaxActionforCM action the session is out----------------");
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
			//PaymentMakerForCMVO paymentMakerForCMVO=new PaymentMakerForCMVO();	
			String lbxLoanNoHID =request.getParameter("lbxLoanNoHID");
			logger.info("getDefaultBusinessPartnerTypeReceipt lbxLoanNoHID---"+lbxLoanNoHID);
			
		
			ReceiptDAO dao=(ReceiptDAO)DaoImplInstanceFactory.getDaoImplInstance(ReceiptDAO.IDENTITY);
			logger.info("Implementation class: "+dao.getClass());
			
			ArrayList bpList= dao.getDefaultBusinessPartnerTypeReceipt(lbxLoanNoHID, "CS");
		    request.setAttribute("bpList", bpList);
			logger.info("getDefaultBusinessPartnerTypeReceipt    Size:---"+bpList.size());
			String amount=dao.getTotalRec(lbxLoanNoHID,"CS");
			logger.info("Total Receivable   :  "+amount);
			request.setAttribute("amount",amount);
			return mapping.findForward("getDefaultBusinessPartnerTypeReceipt");
		}

		public ActionForward getCashDepositAccount(ActionMapping mapping,ActionForm form,
				HttpServletRequest request,HttpServletResponse response)throws Exception{			
		logger.info("in getCashDepositAccount of ajaxaction :::::::::::::::::::::::::");
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
		logger.info("here in getCashDepositAccount method of AjaxActionforCM  action the session is out----------------");
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
		
		ReceiptDAO dao=(ReceiptDAO)DaoImplInstanceFactory.getDaoImplInstance(ReceiptDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass()); 
		ArrayList depositAccount= dao.getCashDepositAccount();
		if(depositAccount.size()>0){
		logger.info("linkLoanList::::::::::: "+depositAccount); 
		request.setAttribute("depositAccountList", depositAccount);
		}
		
		return mapping.findForward("cashDepositAccount");
		}
	//Manish Space Starts for Update Asset Vehicle	
		public ActionForward getUpdateVehicleValues(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {

			logger.info("In AjaxActionforCM getUpdateVehicleValues()--------->>>");
			
			HttpSession session =  request.getSession();
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			String bDate="";
			if(userobj!=null)
			{
				bDate=userobj.getBusinessdate();
			}else{
				logger.info("here in getUpdateVehicleValues method of AjaxActionforCM  action the session is out----------------");
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
			
			UpdateAssetVO updateAssetVO = new UpdateAssetVO();
			
					
			String lbxLoanId=CommonFunction.checkNull(request.getParameter("loanId"));
			String lbxAssetId=CommonFunction.checkNull(request.getParameter("assetId"));
		
			updateAssetVO.setLbxLoanId(lbxLoanId);
			updateAssetVO.setLbxAssetId(lbxAssetId);
				
			LinkLoanDAO dao=(LinkLoanDAO)DaoImplInstanceFactory.getDaoImplInstance(LinkLoanDAO.IDENTITY);
			logger.info("Implementation class: "+dao.getClass());

			ArrayList<UpdateAssetVO> updateAssetList= dao.getUpdateAssetVehical(updateAssetVO);
			
			request.setAttribute("updateAssetList", updateAssetList);
			logger.info("updateAssetList    Size:***>>---"+updateAssetList.size());
			//UpdateAssetVO vo=updateAssetList.get(0);
			return mapping.findForward("updateAssetListValues");
		}
		
	//Nishant space starts
		public ActionForward getPDDUpdateData(ActionMapping mapping,ActionForm form,
				HttpServletRequest request,HttpServletResponse response)throws Exception{			
		logger.info("in getPDDUpdateData of ajaxaction :::::::::::::::::::::::::");
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
		logger.info("here in getPDDUpdateData method of AjaxActionforCM  action the session is out----------------");
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
		
		String lbxAssetId=CommonFunction.checkNull(request.getParameter("assetId"));
		String updateFlag=CommonFunction.checkNull(request.getParameter("updateFlag"));
		String updateVal=CommonFunction.checkNull(request.getParameter("updateVal"));
		logger.info("updateFlag : : updateVal :: " + updateFlag + " / " + updateVal);
		UpdateAssetVO updateAssetVO = new UpdateAssetVO();
		updateAssetVO.setLbxAssetId(lbxAssetId);
		updateAssetVO.setUpdateFlag(updateFlag);
		updateAssetVO.setUpdateVal(updateVal);
		
		LinkLoanDAO dao=(LinkLoanDAO)DaoImplInstanceFactory.getDaoImplInstance(LinkLoanDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass()); 
		ArrayList pddUpdateList= dao.fetchPDDUpdateData(updateAssetVO);
		if(pddUpdateList.size()>0){
		logger.info("linkLoanList::::::::::: "+pddUpdateList); 
		request.setAttribute("pddUpdateList", pddUpdateList);
		}
		
		return mapping.findForward("pddUpdateData");
	}
		
	//Nishant space ends
		
		
		//Richa Space Starts for Repo Billing	
		public ActionForward getRepoBillingApprovalValues(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {

			logger.info("In AjaxActionforCM getRepoBillingApprovalValues()--------->>>");
			
			HttpSession session =  request.getSession();
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			String bDate="";
			if(userobj!=null)
			{
				bDate=userobj.getBusinessdate();
			}else{
				logger.info("here in getRepoBillingApprovalValues method of AjaxActionforCM  action the session is out----------------");
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
			
			RepoBillingApprovalMakerVO vo = new RepoBillingApprovalMakerVO();
			
					
			String lbxLoanId=CommonFunction.checkNull(request.getParameter("loanId"));
			vo.setLbxDealNo(lbxLoanId);
			RepoBillingApprovalMakerDAO dao=(RepoBillingApprovalMakerDAO)DaoImplInstanceFactory.getDaoImplInstance(RepoBillingApprovalMakerDAO.IDENTITY);
			logger.info("Implementation class: "+dao.getClass());

			ArrayList<RepoBillingApprovalMakerVO> repoList= dao.getRepoBillingApprovalValues(vo);
			
			request.setAttribute("repoList", repoList);
			logger.info("updateAssetList    Size:***>>---"+repoList.size());
			//UpdateAssetVO vo=updateAssetList.get(0);
			return mapping.findForward("repoBillingApprovalValues");
		}
		
		
		public ActionForward getSiRdName(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {

			logger.info("In AjaxActionforCM getSiRdName()--------->>>");
			
			HttpSession session =  request.getSession();
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			String bDate="";
			if(userobj!=null)
			{
				bDate=userobj.getBusinessdate();
			}else{
				logger.info("here in getSiRdName method of AjaxActionforCM  action the session is out----------------");
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
	        logger.info("Implementation class: "+service.getClass()); 

			ArrayList siRdName= service.getSiRdName();
			
			request.setAttribute("siRdName", siRdName);
			logger.info("updateAssetList    Size:***>>---"+siRdName.size());
			//UpdateAssetVO vo=updateAssetList.get(0);
			return mapping.findForward("siRdNameValues");
		}		
		
		   public ActionForward getOtrherLoanDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
				    throws Exception
				   {
				     logger.info("In getOtrherLoanDetails Class---------");
				 
				     HttpSession session = request.getSession();
				
				    UserObject userobj = (UserObject)session.getAttribute("userobject");
				     String strFlag = "";
				
				     String userId = "";
				     String bDate = "";
				     if (userobj != null)
				     {
				       userId = userobj.getUserId();
				       bDate = userobj.getBusinessdate();
				     } else {
				       logger.info("here in getOtrherLoanDetails AjaxActionforCM method of  action the session is out----------------");
				       return mapping.findForward("sessionOut");
				     }
				     Object sessionId = session.getAttribute("sessionID");
			 
				     ServletContext context = getServlet().getServletContext();
			 
				     if (sessionId != null)
				     {
				       strFlag = UserSessionCheck.checkSameUserSession(userobj, sessionId.toString(), "", request);
				     }
				 
				     logger.info("strFlag .............. " + strFlag);
				     if (!strFlag.equalsIgnoreCase(""))
			     {
				       if (strFlag.equalsIgnoreCase("sameUserSession"))
				       {
				         context.removeAttribute("msg");
				         context.removeAttribute("msg1");
				       }
				       else if (strFlag.equalsIgnoreCase("BODCheck"))
				       {
				         context.setAttribute("msg", "B");
				       }
				       return mapping.findForward("logout");
				     }
				     int lbxLoanNoHID = Integer.parseInt(request.getParameter("lbxLoanNoHID"));
				 
				     logger.info(" In getOtrherLoanDetails ---" + lbxLoanNoHID);
				 
				     DisbursalInitiationDAO dao = (DisbursalInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance("DISB");
				     logger.info("Implementation class: " + dao.getClass());
				     ArrayList otherLoanDetails = dao.selectOtherLoanDetails(lbxLoanNoHID);
				     request.setAttribute("otherLoanDetails", otherLoanDetails);
				     logger.info("loanDetailList    Size:333---" + otherLoanDetails.size());
				     return mapping.findForward("showOtherLoanDetails");
				 }
		/*public ActionForward getUpdateLOANValues(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
		  {
		     logger.info("In AjaxActionforCM getUpdateLOANValues()--------->>>");
		     HttpSession session = request.getSession();
		     UserObject userobj = (UserObject)session.getAttribute("userobject");
		     String bDate = "";
		    if (userobj != null)
		     {
		      bDate = userobj.getBusinessdate();
		     } else {
		      logger.info("here in getUpdateLOANValues method of AjaxActionforCM  action the session is out----------------");
		      return mapping.findForward("sessionOut");
		     }
		     Object sessionId = session.getAttribute("sessionID");
		 
		     ServletContext context = getServlet().getServletContext();
		     String strFlag = "";
		     if (sessionId != null)
		     {
		       strFlag = UserSessionCheck.checkSameUserSession(userobj, sessionId.toString(), "", request);
		     }
		 
		     logger.info("strFlag .............. " + strFlag);
		     if (!strFlag.equalsIgnoreCase(""))
		    {
		       if (strFlag.equalsIgnoreCase("sameUserSession"))
		       {
		         context.removeAttribute("msg");
		         context.removeAttribute("msg1");
		       }
		      else if (strFlag.equalsIgnoreCase("BODCheck"))
		      {
		        context.setAttribute("msg", "B");
		       }
		       return mapping.findForward("logout");
		    }
		    UpdateAssetVO updateAssetVO = new UpdateAssetVO();
		    ArrayList list = new ArrayList();
		    String lbxLoanId = CommonFunction.checkNull(request.getParameter("loanId"));
		     logger.info("loan_id::::::::::" + lbxLoanId);
		 
		     updateAssetVO.setLbxLoanId(lbxLoanId);
		 
		    LinkLoanDAO dao = (LinkLoanDAO)DaoImplInstanceFactory.getDaoImplInstance("LLD");
		     logger.info("Implementation class: " + dao.getClass());
		     ArrayList assetlist = dao.getUpdatedVehical(updateAssetVO.getLbxLoanId());
		     String loanNoQuery = "select loan_no from cr_loan_dtl where loan_id=" + lbxLoanId + "";
		     String customerNameQuery = "SELECT DISTINCT CUSTOMER_NAME FROM CR_LOAN_DTL C  JOIN GCD_CUSTOMER_M G ON C.LOAN_CUSTOMER_ID=G.CUSTOMER_ID WHERE C.LOAN_ID=" + lbxLoanId + "";
		    String loanNo1 = ConnectionDAO.singleReturn(loanNoQuery);
		     String cusName = ConnectionDAO.singleReturn(customerNameQuery);
		     list.add(updateAssetVO);
		     updateAssetVO.setLoanNo(loanNo1);
		     updateAssetVO.setCustomerName(cusName);
		     loanNoQuery = null;
		    customerNameQuery = null;
		 
		    logger.info("assetlist    Size:***>>---" + assetlist.size());
		     session.setAttribute("assetlist", assetlist);
		    request.setAttribute("list", list);
		     request.setAttribute("newMode", "newMode");
		   return mapping.findForward("updateAssetEditPage");
		   }*/
			//Abhishek Start
			public ActionForward   getCustomerNameForEntityType(ActionMapping mapping, ActionForm form,
					HttpServletRequest request, HttpServletResponse response)
					throws Exception {
				ServletContext context = getServlet().getServletContext();
				logger.info("In AjaxActionForOCR.........");
				HttpSession session = request.getSession();
				boolean flag=false;
				String userId="";
				String branchId="";
				
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				
				if(userobj==null)
						{
							logger.info("here in getCustomerNameForEntityType method of AjaxActionForOCR  action the session is out----------------");
							return mapping.findForward("sessionOut");
}
						else
						{
							userId=userobj.getUserId();
							branchId=userobj.getBranchId();
						}
				Object sessionId = session.getAttribute("sessionID");
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
				String caseId = CommonFunction.checkNull(request.getParameter("caseId"));
				String entityType =  CommonFunction.checkNull(request.getParameter("entityType"));
				logger.info("caseId-----"+caseId);
				logger.info("entityType-----"+entityType);
				OcrDAO dao =(OcrDAO)DaoImplInstanceFactory.getDaoImplInstance(OcrDAO.IDENTITY);

				 ArrayList<CodeDescVo> customerList = dao.getCustomerList(caseId,entityType);
			 	request.setAttribute("customerList", customerList);
				if(CommonFunction.checkNull(session.getAttribute("functionId")).equalsIgnoreCase("3000276"))
					return mapping.findForward("financialCustomer");
				return mapping.findForward("customer");
			}
 public ActionForward getIndustryPercent(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
					    throws Exception
					   {
					     logger.info("In getIndustryPercent Class---------");
					 
					     HttpSession session = request.getSession();
					
					    UserObject userobj = (UserObject)session.getAttribute("userobject");
					     String strFlag = "";
					
					     String userId = "";
					     String bDate = "";
					     if (userobj != null)
					     {
					       userId = userobj.getUserId();
					       bDate = userobj.getBusinessdate();
					     } else {
					       logger.info("here in getIndustryPercent AjaxActionforCM method of  action the session is out----------------");
					       return mapping.findForward("sessionOut");
					     }
					     Object sessionId = session.getAttribute("sessionID");
				 
					     ServletContext context = getServlet().getServletContext();
				 
					     if (sessionId != null)
					     {
					       strFlag = UserSessionCheck.checkSameUserSession(userobj, sessionId.toString(), "", request);
					     }
					 
					     logger.info("strFlag .............. " + strFlag);
					     if (!strFlag.equalsIgnoreCase(""))
				     {
					       if (strFlag.equalsIgnoreCase("sameUserSession"))
					       {
					         context.removeAttribute("msg");
					         context.removeAttribute("msg1");
					       }
					       else if (strFlag.equalsIgnoreCase("BODCheck"))
					       {
					         context.setAttribute("msg", "B");
					       }
					       return mapping.findForward("logout");
					     }
					     String natureOfBus =request.getParameter("natureOfBus").toString();
					     String lbxIndustry =request.getParameter("lbxIndustry").toString();
					 
					     logger.info(" In getIndustryPercent ---" + natureOfBus);
					 
					     CreditManagementDAO dao=(CreditManagementDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditManagementDAO.IDENTITY);
					     logger.info("Implementation class: "+dao.getClass());
					     ArrayList otherLoanDetails = dao.selectIndustryDetails(natureOfBus,lbxIndustry);
					     request.setAttribute("otherLoanDetails", otherLoanDetails);
					     logger.info("loanDetailList    Size:333---" + otherLoanDetails.size());
					     return mapping.findForward("showindustryDetails");
					 }}


