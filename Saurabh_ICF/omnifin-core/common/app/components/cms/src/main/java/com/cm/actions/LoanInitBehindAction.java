package com.cm.actions;
import com.cm.dao.LoanInitiationDAO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.CreditProcessingDAO;
import com.cp.vo.CodeDescVo;
import com.cp.vo.CreditProcessingLeadEntryVo;
import com.cp.vo.LoanDetailVo;
import com.lockRecord.action.LockRecordCheck;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

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
import org.apache.struts.action.ActionServlet;
import org.apache.struts.actions.DispatchAction;

public class LoanInitBehindAction extends DispatchAction {
	
	private static final Logger logger = Logger.getLogger(LoanInitBehindAction.class.getName());
	
	public ActionForward loanInitMakerAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		logger.info(" In the loanInitMakerAuthor----------");
		
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId ="";
		String businessDate ="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			businessDate = userobj.getBusinessdate();
		}else{
			logger.info("here in loanInitMakerAuthor method of LoanInitBehindAction action the session is out----------------");
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
		CreditProcessingDAO creditProcessing=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+creditProcessing.getClass()); 		// changed by asesh
		String diffDayQuery="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='REPY_DATE_AFTER'";
		String diffDayCount=ConnectionDAO.singleReturn(diffDayQuery);
		logger.info("diffDayQuery: "+diffDayQuery+" diffDayCount: "+diffDayCount);
		session.setAttribute("diffDayCount", diffDayCount);
		ArrayList installmentTypeList=creditProcessing.getInstallmentTypeList();
		session.setAttribute("installmentTypeList", installmentTypeList);
		ArrayList sector = creditProcessing.getSectorList();
        session.setAttribute("sector",sector);
      //amit space starts
      		ArrayList businessList = creditProcessing.getbusinessList();
      		request.setAttribute("getBusiness", businessList);
      		session.setAttribute("getBusiness", businessList);
      		//amit space ends
      		
      		//ajay space starts
      		ArrayList<CodeDescVo> intCal = creditProcessing.getGenericMasterList("INTEREST_CAL");
      		session.setAttribute("intCal", intCal);
      		request.setAttribute("intCal", intCal);
      		//ajay end
      		
      		
		if(CommonFunction.checkNull(request.getParameter("loanStatus")).equalsIgnoreCase("NEW") )
		{
			session.removeAttribute("maxIdInCM");
			session.removeAttribute("loanHeader");
		}
		LoanInitiationDAO dao=(LoanInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(LoanInitiationDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass()); 
		String loanId = "";
		//amandeep starts
		ArrayList list1 = creditProcessing.getPaymentModes();
		session.setAttribute("paymentModes", list1);
		if(CommonFunction.checkNull(request.getParameter("loanId")) != null) {
			loanId = request.getParameter("loanId");
		} else if (session.getAttribute("maxIdInCM") != null) {
			loanId = session.getAttribute("maxIdInCM").toString();
		}
		
		logger.info(" In the LoanInitBehindAction-aman-----loanId----"+loanId);
		
		ArrayList cycle = creditProcessing.getCycleDateList(loanId,"LIM");
		session.setAttribute("cycle", cycle);
		logger.info(" In the loanInitMakerAuthor---cycle-------"+cycle);
		
		logger.info("function id is ........................................"+session.getAttribute("functionId").toString());
		String functionId="";

	
		if(session.getAttribute("functionId")!=null)
		{
			functionId=session.getAttribute("functionId").toString();
		}
		
		
		//ServletContext context=getServlet().getServletContext();
		
		if(context!=null)
		{
			flag = LockRecordCheck.lockCheck(userId,functionId,loanId,context);
			logger.info("Flag ........................................ "+flag);
			if(!flag)
			{
				logger.info("Record is Locked");			
				request.setAttribute("sms", "Locked");
				request.setAttribute("recordId", loanId);
				//request.setAttribute("userId", userId);
				return mapping.getInputForward();
			}
		}
		
		boolean sanctionValid=false;
		//code added by neeraj 
		int id=Integer.parseInt(functionId);
	
			
		if((id == 4000122) || (id == 4000123))
		{
			session.setAttribute("mis", "mis");
			sanctionValid=true;
			//session.setAttribute("Edit_Loan_Id", loanId);
			ArrayList statusList=new ArrayList();
			String eFlag="";
			String eMsg="";	
			String caseType= request.getParameter("caseType");
			if(CommonFunction.checkNull(caseType).trim().equalsIgnoreCase("N"))
			{
				if(id==4000122)
					statusList=dao.copyData(loanId,userId,businessDate);
				if(statusList.size()==2)
				{
					eFlag=(String)statusList.get(0);
					eMsg=(String)statusList.get(1);
				}
				if(CommonFunction.checkNull(eFlag).trim().equalsIgnoreCase("E"))
				{
					request.setAttribute("errorFlag",eFlag);
					request.setAttribute("errorMsg",eMsg);
					return mapping.findForward("editError");
				}
			}
		}
		else if (id == 4000111) {
		      sanctionValid = true;
		    } 
		else {
			sanctionValid = dao.checkLoanSanctionVaildTill(loanId,businessDate,Integer.parseInt(functionId));
			}
		//}
		//neeraj space end
		
		logger.info("Sanction Valid Status: "+sanctionValid);
		String plan=(String)session.getAttribute("planCheck");	
		String repayment=(String)session.getAttribute("repaymentCheck");
		if(plan != null)
		{
			if(plan.trim().equals("Y"))
				session.setAttribute("plan","Y");
			else
				session.setAttribute("plan","N");
		}
		else
		{
			session.setAttribute("plan","N");
		}
		if(repayment != null)
		{
			if(repayment.trim().equals("Y"))
				session.setAttribute("repayment","Y");
			else
				session.setAttribute("repayment","N");
		}
		else
		{
			session.setAttribute("repayment","N");
		}
		if(sanctionValid)
		{
			ArrayList loanList =null;
			String editLoanMaker=(String)session.getAttribute("editLoanMaker");
			String editLoanAuthor=(String)session.getAttribute("editLoanAuthor");
			if(CommonFunction.checkNull(editLoanMaker).trim().equalsIgnoreCase("editLoanMaker") || CommonFunction.checkNull(editLoanAuthor).trim().equalsIgnoreCase("editLoanAuthor"))
			{
				loanList=dao.getloanListInLoanForEdit(loanId);
			}
			else{
			loanList=dao.getloanListInLoan(loanId);
			}
			session.setAttribute("editUserId",userId);
			ArrayList<LoanDetailVo> loanClassificationList = dao.getresultForLoan(loanId);
			session.setAttribute("loanClassificationList", loanClassificationList);
			String dealId="";
			//dealId= loanList.get(0).toString();
			dealId = request.getParameter("dealId");
			session.setAttribute("dealId", dealId);
			
			ArrayList dateList=dao.getDate(loanId);
			session.setAttribute("dateList",dateList);
			//amandeep starts
			ArrayList list3 = creditProcessing.getPaymentModes();
			session.setAttribute("paymentModes", list3);
			//amandeep ends
			if((loanId!=null) && !(loanId.equalsIgnoreCase("")))
			{
				ArrayList loanHeader = dao.getLoanHeader(loanId);
				session.setAttribute("loanHeader", loanHeader);
				logger.info("Size: "+loanList.size());
				session.setAttribute("loanList", loanList);
				session.setAttribute("loanId",loanId.trim());
			}
			else
			{
				session.removeAttribute("loanList");
				session.removeAttribute("loanId");
			}
			ArrayList baseRateList = creditProcessing.getBaseRateList(businessDate);
			session.setAttribute("baseRateList", baseRateList);
			return mapping.findForward("success");
		}
		else
		{
			//String status="";
			if(session.getAttribute("cmAuthor")!=null)
			{
				request.setAttribute("sms","E");
				return mapping.findForward("authorScreen");
			}
			else
			{
				request.setAttribute("sms","E");
				return mapping.findForward("makerscreen");
			}			
		}		
	}

public ActionForward openNewLoan(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)throws Exception {
	
	logger.info(" In the openNewLoan----------");
	
	HttpSession session = request.getSession();
	UserObject userobj=(UserObject)session.getAttribute("userobject");
	String businessDate=null;
	if(userobj==null){
		logger.info("here in openNewLoan method of LoanInitBehindAction action the session is out----------------");
		return mapping.findForward("sessionOut");
	}
	else
	{
		businessDate = userobj.getBusinessdate();
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
	//code added by neeraj
	CreditProcessingDAO creditProcessing=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
    logger.info("Implementation class: "+creditProcessing.getClass()); 		// changed by asesh
    ArrayList installmentTypeList=creditProcessing.getInstallmentTypeList();
	session.setAttribute("installmentTypeList", installmentTypeList);
	ArrayList sector = creditProcessing.getSectorList();
    session.setAttribute("sector",sector);
	//neeraj space end
    ArrayList baseRateList = creditProcessing.getBaseRateList(businessDate);
    session.setAttribute("baseRateList", baseRateList);
  //amit space starts
		ArrayList businessList = creditProcessing.getbusinessList();
		request.setAttribute("getBusiness", businessList);
		session.setAttribute("getBusiness", businessList);
		//amit space ends

	//amandeep starts
	ArrayList list3 = creditProcessing.getPaymentModes();
	session.setAttribute("paymentModes", list3);

	//amandeep ends
	
	String loanId = "";
	
	if(CommonFunction.checkNull(request.getParameter("loanId")) != null) {
		loanId = request.getParameter("loanId");
	} else if (session.getAttribute("maxIdInCM") != null) {
		loanId = session.getAttribute("maxIdInCM").toString();
	}
	
	logger.info(" In the LoanInitBehindAction------loanId----"+loanId);
	ArrayList cycle = creditProcessing.getCycleDateList(loanId,"LIM");
	session.setAttribute("cycle", cycle);
	logger.info(" In the openNewLoan---cycle-------"+cycle);
	ArrayList<CodeDescVo> intFreq = creditProcessing.getGenericMasterList("INTEREST_FREQ");
	ArrayList<CodeDescVo> intCal = creditProcessing.getGenericMasterList("INTEREST_CAL");
	ArrayList<CodeDescVo> intComFreq = creditProcessing.getGenericMasterList("INTEREST_COM_FREQ");
	
	session.setAttribute("intFreq", intFreq);
	session.setAttribute("intCal", intCal);
	request.setAttribute("intCal", intCal);
	session.setAttribute("intComFreq", intComFreq);
	
	
	/*
	if(CommonFunction.checkNull(request.getParameter("loanStatus")).equalsIgnoreCase("NEW") )
	{
		session.removeAttribute("maxIdInCM");
		session.removeAttribute("loanHeader");
	}
	
	LoanInitiationDAO dao = new LoanInitiationDAOImpl();
	String loanId = "";
	
	if(CommonFunction.checkNull(request.getParameter("loanId")) != null) {
		loanId = request.getParameter("loanId");
	} else if (session.getAttribute("maxIdInCM") != null) {
		loanId = session.getAttribute("maxIdInCM").toString();
	}
	
	logger.info(" In the LoanInitBehindAction------loanId----"+loanId);
	
	logger.info("function id is ........................................"+session.getAttribute("functionId"));

	
	if(loanId!=null && !loanId.equalsIgnoreCase(""))
	{
		//CreditProcessingDAO creditProcessing = new CreditProcessingDAOImpl();
		ArrayList cycle = creditProcessing.getCycleDateList();
		session.setAttribute("cycle", cycle);
		logger.info(" In the openNewLoan---cycle-------"+cycle);
		
		ArrayList loanList = dao.getloanListInLoan(loanId);
		ArrayList loanHeader = dao.getLoanHeader(loanId);
		session.setAttribute("loanHeader", loanHeader);
		logger.info("Size: "+loanList.size());
		session.setAttribute("loanList", loanList);
		session.setAttribute("loanId",loanId);
	
	}
	else
	{
		session.removeAttribute("loanList");
		session.removeAttribute("loanId");
	}
	*/
	session.removeAttribute("loanHeader");
	session.removeAttribute("maxIdInCM");
	session.removeAttribute("loanList");
	session.removeAttribute("loanId");

	// Start By Prashant
	String diffDayQuery="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='REPY_DATE_AFTER'";
	String diffDayCount=ConnectionDAO.singleReturn(diffDayQuery);
	logger.info("diffDayQuery: "+diffDayQuery+" diffDayCount: "+diffDayCount);
	session.setAttribute("diffDayCount", diffDayCount);
	// End By Prashant
	String plan=(String)session.getAttribute("planCheck");
	session.removeAttribute("plan");
	if(plan != null)
	{
		if(plan.trim().equals("Y"))
		{
			session.setAttribute("plan","Y");
		}
		else
		{
			session.setAttribute("plan","N");
		}
	}
	else
	{
		session.setAttribute("plan","N");
	}
	
	
	
	
	return mapping.findForward("success");
	
}
public ActionForward viewDealLoanInitiation(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception

{
	logger.info("In (viewDealLoanInitiation)");
	HttpSession session = request.getSession();
	UserObject userobj=(UserObject)session.getAttribute("userobject");
	
	if(userobj!=null){
		//Asesh space starts
		session.setAttribute("userId", userobj.getUserId());
		session.setAttribute("userName", userobj.getUserName());
		session.setAttribute("branchId", userobj.getBranchId());
		//Asesh space end
	}
	else
	{
		logger.info(" in viewDealLoanInitiation method of  action the session is out----------------");
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

	
	 session.removeAttribute("dealId");
	 session.removeAttribute("DealCap");
	 session.setAttribute("underWriterViewData","underWriterViewData");
	
	CreditProcessingDAO creditProcessing=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
	
	ArrayList<CreditProcessingLeadEntryVo> list1 = new ArrayList<CreditProcessingLeadEntryVo>();
	
    String	 dealId=CommonFunction.checkNull(request.getParameter("dealId"));
    
    logger.info("In viewDealLoanInitiation dealId------------>"+ dealId);
  //amit space starts
		ArrayList businessList = creditProcessing.getbusinessList();
		request.setAttribute("getBusiness", businessList);
		session.setAttribute("getBusiness", businessList);
		//amit space ends

	 if (!CommonFunction.checkNull(dealId).equals(""))
	{
	
		ArrayList<Object> leadInfo = creditProcessing.getLeadEntryList(dealId);
		Iterator<Object> it = leadInfo.iterator();
		int i=0;
		while(i<leadInfo.size()-1)
		{
			list1.add((CreditProcessingLeadEntryVo) it.next());
			i++;
		}
		CreditProcessingLeadEntryVo  tb1 = (CreditProcessingLeadEntryVo) it.next();
		logger.info("............ "+tb1.getLeadNo());
		session.setAttribute("leadNo", tb1.getLeadNo());	
		logger.info("Implementation class: "+creditProcessing.getClass()); 			// changed by asesh
		ArrayList dealHeader = creditProcessing.getDealHeader(dealId);
		session.setAttribute("dealHeader", dealHeader);
		session.setAttribute("dealId", dealId);
		session.setAttribute("leadInfo", leadInfo);
		session.setAttribute("viewDeal", "UWA");

	} 
	
	String leadMQ="select PARAMETER_VALUE from parameter_mst WHERE PARAMETER_KEY='LEAD_MANDATORY'";
	String leadMValue=ConnectionDAO.singleReturn(leadMQ);
	
	ArrayList<Object> sourceTypeList = creditProcessing.getsourceTypeList();
	ArrayList<Object> dealCatList = creditProcessing.getDealCatList();
	session.setAttribute("dealCatList", dealCatList);
	session.setAttribute("sourceTypeList", sourceTypeList);
	CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
  
	ArrayList checkLoginUserLevel = service.getLoginUserLevel(userobj
			.getUserId(), userobj.getUserName());
	if (checkLoginUserLevel.size() > 0) {
		session.setAttribute("checkLoginUserLevel", checkLoginUserLevel);
	}
	//
	ArrayList creditApprovalList = service.getMakerData(dealId);
	session.setAttribute("creditApprovalList", creditApprovalList);
	session.setAttribute("leadMValue", leadMValue);
	String bsflag = request.getParameter("fId");
	session.setAttribute("bsflag", bsflag);
	session.removeAttribute("custEntryU");
	
	//amandeep starts
	ArrayList list4 = creditProcessing.getPaymentModes();
	session.setAttribute("paymentModes", list4);
	//amandeep ends
	
	ArrayList<CodeDescVo> intFreq = creditProcessing.getGenericMasterList("INTEREST_FREQ");
	ArrayList<CodeDescVo> intCal = creditProcessing.getGenericMasterList("INTEREST_CAL");
	ArrayList<CodeDescVo> intComFreq = creditProcessing.getGenericMasterList("INTEREST_COM_FREQ");
	
	session.setAttribute("intFreq", intFreq);
	session.setAttribute("intCal", intCal);
	session.setAttribute("intComFreq", intComFreq);
	return mapping.findForward("viewDealLoanInitiation");
}

}