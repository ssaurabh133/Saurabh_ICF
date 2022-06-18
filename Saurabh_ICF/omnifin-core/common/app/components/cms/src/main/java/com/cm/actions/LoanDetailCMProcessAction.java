package com.cm.actions;
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

import com.cm.dao.LoanInitiationDAO;
import com.cm.vo.LoanDetailForCMVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.ImdDAO;
import com.cp.vo.ImdMakerVO;
import com.cp.vo.LoanDetailVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class LoanDetailCMProcessAction  extends DispatchAction
{
	private static final Logger logger = Logger.getLogger(LoanDetailCMProcessAction.class.getName());	
	static ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	static String dbType=resource.getString("lbl.dbType");
	public ActionForward updateLoanDetails(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		logger.info("In updateLoanDetails---------");
		HttpSession session = request.getSession();
		//boolean flag=false;
		int maxId=0;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId=null;
		String bDate =null;
		if(userobj!=null)
		{
			userId= userobj.getUserId();
			bDate=userobj.getBusinessdate();
		}
		else
		{
			logger.info("here in updateLoanDetails method of LoanDetailCMProcessAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
	    Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag=null;			
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
		DynaValidatorForm LoanDetailsCMDynaValidatorForm = (DynaValidatorForm)form;		
		LoanDetailForCMVO loanDetailForCMVO=new LoanDetailForCMVO();		
		org.apache.commons.beanutils.BeanUtils.copyProperties(loanDetailForCMVO, LoanDetailsCMDynaValidatorForm);
		String loanS="E";
		loanDetailForCMVO.setUserId(""+userId);
		loanDetailForCMVO.setBussinessDate(bDate);
		if(LoanDetailsCMDynaValidatorForm.getString("typeOfDisbursal").equalsIgnoreCase("S"))
			loanDetailForCMVO.setNoOfDisbursal(LoanDetailsCMDynaValidatorForm.getString("noOfDisbursalText"));
		LoanInitiationDAO dao=(LoanInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(LoanInitiationDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass()); 
		logger.info("getRepaymentType In updateLoanDetails: " +LoanDetailsCMDynaValidatorForm.getString("repaymentType"));
		String checkPendingLoanQuery="";
		
		if(CommonFunction.checkNull(dbType).equalsIgnoreCase("MSSQL")){
			checkPendingLoanQuery="select count(1) from cr_loan_dtl where ISNULL(REC_STATUS,'P') in ('P','F') and LOAN_DEAL_ID="+loanDetailForCMVO.getLbxDealNo();
		}else{
			checkPendingLoanQuery="select count(1) from cr_loan_dtl where IFNULL(REC_STATUS,'P') in ('P','F') and LOAN_DEAL_ID="+loanDetailForCMVO.getLbxDealNo();
		}
		if(CommonFunction.checkNull(loanDetailForCMVO.getGrossBlock()).equalsIgnoreCase("")){
			if(request.getParameter("grossBlock")!=null){
				loanDetailForCMVO.setGrossBlock(request.getParameter("grossBlock").toString());
			}
		}
		if(CommonFunction.checkNull(loanDetailForCMVO.getNetBlock()).equalsIgnoreCase("")){
			if(request.getParameter("netBlock")!=null){
				loanDetailForCMVO.setNetBlock(request.getParameter("netBlock").toString());
			}
		}
		logger.info("check Pending Loan Detail Query: "+checkPendingLoanQuery);
		String checkPendingLoanStatus=ConnectionDAO.singleReturn(checkPendingLoanQuery);
		String recStatus=null;
		String errorMsg=null;
		String id=null;
		String checkAvailableAmtLimit="S";
		if(!CommonFunction.checkNull(loanDetailForCMVO.getLoanId()).equalsIgnoreCase("")){
		 checkAvailableAmtLimit = dao.checkDealSanctionLimit(CommonFunction.checkNull
					(loanDetailForCMVO.getLbxDealNo()),loanDetailForCMVO.getLoanId(), CommonFunction.checkNull(loanDetailForCMVO.getProduct()), CommonFunction.checkNull(loanDetailForCMVO.getLoanAmount()));
		}
		
		if((CommonFunction.checkNull(checkPendingLoanStatus).equalsIgnoreCase("0") || !CommonFunction.checkNull(loanDetailForCMVO.getLoanId()).equalsIgnoreCase(""))
				&& CommonFunction.checkNull(checkAvailableAmtLimit).equalsIgnoreCase("S"))
		{
			//code added by saurabh
			if(CommonFunction.checkNull(loanDetailForCMVO.getRequestedLoamt()).equalsIgnoreCase(""))
				loanDetailForCMVO.setRequestedLoamt(CommonFunction.checkNull(request.getParameter("requestedLoamt")));
			
			if(CommonFunction.checkNull(loanDetailForCMVO.getInsurancePremium()).equalsIgnoreCase(""))
				loanDetailForCMVO.setInsurancePremium(CommonFunction.checkNull(request.getParameter("insurancePremium")));
			//code end 
			ArrayList result=dao.updateListOfvalue(loanDetailForCMVO);
			if(result.size()>0)
			{
				recStatus=CommonFunction.checkNull(result.get(0)).trim();
				if(CommonFunction.checkNull(recStatus).trim().equalsIgnoreCase("S"))
				{
					Boolean deleteBusinessPartnr=dao.deleteBusinessPartnr(loanDetailForCMVO.getLoanId(),loanDetailForCMVO.getBusinessId(),loanDetailForCMVO.getBusinessType());
					logger.info("Implementation deleteBusinessPartnr: "+deleteBusinessPartnr);
					id=CommonFunction.checkNull(result.get(1)).trim();	
					if(CommonFunction.checkNull(id).trim().equalsIgnoreCase(""))
						id="0";
					maxId=Integer.parseInt(id);
				}
				else
				{
					errorMsg=CommonFunction.checkNull(result.get(1)).trim();
					maxId=0;
				}
			}
			else
			{
				recStatus="E";
				errorMsg="Some Error Occur,Please contact Administrator.";
				maxId=0;
			}			
		}
		else if(CommonFunction.checkNull(checkAvailableAmtLimit).equalsIgnoreCase("E"))
			request.setAttribute("loanS","LimitCross");
		else
			request.setAttribute("loanS","X");
		
		if(!CommonFunction.checkNull(recStatus).trim().equalsIgnoreCase(""))
		{
			if(CommonFunction.checkNull(recStatus).trim().equalsIgnoreCase("E"))
				request.setAttribute("insertError", errorMsg);
			else 		
				request.setAttribute("loanS","S");
		}
		
	//	logger.info("maxId In updateLoanDetails: " +maxId);
	    String f=CommonFunction.editableFlag();
	    if(maxId>0)
	    {  
	    	session.setAttribute("maxIdInCM", maxId);
	    	session.setAttribute("loanId", maxId);	    	
		    loanS="S";
            loanDetailForCMVO.setLoanId(maxId+""); //Ravi
            session.setAttribute("dealId", loanDetailForCMVO.getLbxDealNo());
		}		
		session.setAttribute("oneDealOneLoan",loanDetailForCMVO.getOneDealOneLoan());
		ArrayList loanHeader = dao.getLoanHeader(""+maxId);
		session.setAttribute("loanHeader", loanHeader);
		ArrayList loanList = dao.getloanListInLoan(""+maxId);
		ArrayList<LoanDetailVo> loanClassificationList = dao.getresultForLoan(""+maxId);
		session.setAttribute("loanClassificationList", loanClassificationList);
		if(!CommonFunction.checkNull(checkAvailableAmtLimit).equalsIgnoreCase("E"))
		request.setAttribute("loanList", loanList);
//ravi
		logger.info("insertUpdateSupManfCust in action: ");
        logger.info("loan id  :::::::::::::::::::::: "+loanDetailForCMVO.getLoanId());
        if(maxId>0)
	    { 
		 boolean status=dao.insertUpdateSupManfCust(loanDetailForCMVO);
		 logger.info("status : "+status);
	    }
		
		form.reset(mapping, request);
		loanDetailForCMVO=null;
		loanS=null;
		recStatus=null;
		userId=null;
		errorMsg=null;
		strFlag=null;
		id=null;
		bDate=null;
		f=null;
		dao=null;
		return mapping.getInputForward();
	}
	
	//started by richa
	public ActionForward openSectorType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception
			{
				logger.info(" in openSectorType()");
				ServletContext context = getServlet().getServletContext();
				HttpSession session = request.getSession();
				LoanDetailForCMVO loanDetailForCMVO=new LoanDetailForCMVO();	
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				Object sessionId = session.getAttribute("sessionID");
				//for check User session start
				String strFlag=null;
				if(sessionId!=null)
				{
					strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
				}
				
				String val="";
				val=request.getParameter("val");
				logger.info("request.getParameter " + val);
				if ((CommonFunction.checkNull(val)).trim().equalsIgnoreCase("Y")) {
					logger.info("value in if" );
					request.setAttribute("edit", "edit");
				} 
				else
				{
					logger.info("value in else " );
					request.setAttribute("deal", "deal");
				}
				String dealId = "";
				//dealId=loanDetailForCMVO.getLoanDealId();

				dealId = request.getParameter("dealId");
				logger.info("In openSectorType dealId " + dealId);
				loanDetailForCMVO.setLoanDealId(dealId);
				session.setAttribute("dealId", dealId);	    
				
				String loanId = "";

				if (session.getAttribute("loanId") != null) {

					loanId = session.getAttribute("loanId").toString();
				} 
				logger.info("In openSectorType loanId " + loanId);
				loanDetailForCMVO.setLoanId(loanId);
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
				LoanInitiationDAO dao=(LoanInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(LoanInitiationDAO.IDENTITY);
				ArrayList list = dao.editSectorTypeDetails(loanDetailForCMVO);
				logger.info("In openEditCaseTypeMaster CaseTypeMasterVo list"+list.size());
				  ArrayList<Object> agriDocsList = dao.getAgriDocsList();
					session.setAttribute("agriDocsList", agriDocsList);
				request.setAttribute("list", list);
				if(list.size()>0)
				{
				
				loanDetailForCMVO=(LoanDetailForCMVO) list.get(0);
				LoanDetailForCMVO docVo=new LoanDetailForCMVO();
				docVo=(LoanDetailForCMVO) list.get(0);
				request.setAttribute("status",docVo.getRecStatus());
				}
				request.setAttribute("inLoan", "inLoan");
				request.setAttribute("save", "save");
				
			    return mapping.findForward("openAdd");	
			}
	
	public ActionForward saveSectorTypeDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse resopnse) throws Exception{
			ServletContext context = getServlet().getServletContext();
			LoanDetailForCMVO loanDetailForCMVO=new LoanDetailForCMVO();	
			HttpSession session = request.getSession();
		
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			Object sessionId = session.getAttribute("sessionID");
			//for check User session start
			String strFlag=null;
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
			String dealId = "";

			dealId = request.getParameter("dealId");
			logger.info("In openSectorType dealId " + dealId);
			loanDetailForCMVO.setLoanDealId(dealId);
			session.setAttribute("dealId", dealId);
			String loanId = "";

			if (session.getAttribute("loanId") != null) {

				loanId = session.getAttribute("loanId").toString();
			} 
			logger.info("In openSectorType loanId " + loanId);
			loanDetailForCMVO.setLoanId(loanId);
			String userId=null;
			String bDate=null;
			if(userobj!=null)
			{
					userId=userobj.getUserId();
					bDate=userobj.getBusinessdate();
			}
		DynaValidatorForm loanDetailForm= (DynaValidatorForm)form;
		
		org.apache.commons.beanutils.BeanUtils.copyProperties(loanDetailForCMVO, loanDetailForm);
		

		loanDetailForCMVO.setMakerId(userId);
		loanDetailForCMVO.setMakerDate(bDate);
		LoanInitiationDAO dao=(LoanInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(LoanInitiationDAO.IDENTITY);
		String sms=null;
		String recStatus=null;
		boolean status = dao.saveSectorTypeDetails(loanDetailForCMVO,dealId,loanId);
		logger.info("Inside Country Master Action.....displaying status...."+status);
		loanDetailForCMVO.setLoanDealId(dealId);
		ArrayList list = dao.editSectorTypeDetails(loanDetailForCMVO);
		logger.info("In openEditCaseTypeMaster CaseTypeMasterVo list"+list.size());
		 ArrayList<Object> agriDocsList = dao.getAgriDocsList();
		session.setAttribute("agriDocsList", agriDocsList);
		request.setAttribute("list", list);
		request.setAttribute("save", "save");
		request.setAttribute("deal", "deal");
		if(CommonFunction.checkNull(loanDetailForCMVO.getRecStatus()).equalsIgnoreCase("on"))
		{
			recStatus="A";
		}
		else
		{
			recStatus="X";
		}
		request.setAttribute("status",recStatus);
		logger.info("vo.getRecStatus()::::::::::"+recStatus);
		
		if(status){
			sms="S";
			logger.info("sms::::::::::"+sms);
			request.setAttribute("sms",sms);
			request.setAttribute("save", "save");
			//request.setAttribute("list", list);
		}
		else{
			sms="E";
			request.setAttribute("sms",sms);
			request.setAttribute("list", list);
			//request.setAttribute("save", "save");
		}
		logger.info("status"+status);
		return mapping.findForward("save");	
	}
	
	public ActionForward imdDetailsGrid(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws Exception {
	
		logger.info("In searchDetail  ");
		
		HttpSession session = request.getSession();		
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String branchId="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}else{
			logger.info(" in Execute method of  ReceiptMakerSearch action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		session.removeAttribute("pParentGroup");
		session.removeAttribute("loanID");
		session.removeAttribute("instrumentID");
		session.removeAttribute("businessPartnerType");
		session.removeAttribute("datatoapproveList");
		session.removeAttribute("receiptNoFlag");
		session.setAttribute("userId", userId);// for lov
		session.setAttribute("branchId", branchId);//for lov
		//boolean flag=false;
		Object sessionId = session.getAttribute("sessionID");

		ImdMakerVO vo = new ImdMakerVO();
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
		
		ImdDAO dao=(ImdDAO)DaoImplInstanceFactory.getDaoImplInstance(ImdDAO.IDENTITY);
		
		logger.info("Implementation class: "+dao.getClass());
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
		
		
		
	//	ArrayList<PaymentMakerForCMVO> bussinessPartnerList= dao.getbussinessPartner();
		//request.setAttribute("bussinessPartnerList", bussinessPartnerList);
		/*DynaValidatorForm ImdMakerDynaValidatorForm = (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, ImdMakerDynaValidatorForm);
		*/if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
		{ 
			vo.setReportingToUserId(userId);
		   //logger.info("When user id is not selected by the user:::::"+userId);
		}
		String dealId="";
		
		logger.info("user Id:::::"+vo.getReportingToUserId());
		vo.setBranchId(branchId);
		vo.setUserId(userId);
		vo.setCurrentPageLink(currentPageLink);
		String loanId = "";
		logger.info("In openSectorType loanId " + session.getAttribute("loanId"));
		logger.info("In openSectorType dealId aman " + session.getAttribute("dealId"));
		if (session.getAttribute("loanId") != null) {

			loanId = session.getAttribute("loanId").toString();
			LoanInitiationDAO service=(LoanInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(LoanInitiationDAO.IDENTITY);
			ArrayList loanHeader = service.getLoanHeader(loanId);
			session.setAttribute("loanHeader", loanHeader);
			/*String loan= service.getDeal(loanId);
			session.setAttribute("dealId", loan);*/
			//dealNo = request.getParameter("dealId");
			
			logger.info("the value of dealId::"+session.getAttribute("dealId"));
			if (session.getAttribute("dealId") != null) {

				//dealId = request.getParameter("dealId");
				dealId = session.getAttribute("dealId").toString();
				logger.info("amandeep::"+dealId);
			}
			else
			{
				loanId = session.getAttribute("loanId").toString();
				String loan= service.getDeal(loanId);
				session.setAttribute("dealId", loan);
				logger.info("amandeep1111::"+loan);
				
			}
		} 
		
		dealId = CommonFunction.checkNull(session.getAttribute("dealId"));
		logger.info("In openSectorType dealId ::" + dealId);
		vo.setDealNo(dealId);
		session.setAttribute("dealId", dealId);
		
		
		//dealNo = session.getAttribute("loanDealId").toString();
		logger.info("aman test dealNo::::"+dealId);
		ArrayList<ImdMakerVO> imdDetailsGrid = dao.imdDetailsGrid(vo,dealId);
		request.setAttribute("list1", imdDetailsGrid);
		request.setAttribute("imdDetailsGrid","imdDetailsGrid");
		
		
//Neeraj Tripathi start
		//receiptMakerSearch.do?forward=forward&frdLoanID="+frdLoanID+"&frdInstrumentID="+frdInstrumentID;" +
		String forward=request.getParameter("forward");
		String search=request.getParameter("search");
		logger.info(" forward    "+forward);
		
		String frdLoanID="";
		String frdInstrumentID="";
		if(forward==null)
		{
			forward="No";
			frdLoanID="0";
			frdInstrumentID="0";
		}
		else
		{
			forward="Yes";
			frdLoanID=request.getParameter("frdLoanID");
			frdInstrumentID=request.getParameter("frdInstrumentID");
		}
		if(search!=null)
		{
			forward="No";
			frdLoanID="0";
			frdInstrumentID="0";
		}
		
		request.setAttribute("forward",forward);
		request.setAttribute("frdLoanID",frdLoanID);
		request.setAttribute("frdInstrumentID",frdInstrumentID);
//Neeraj Tripathi end
		return mapping.findForward("imdMakerSearch");	
	
	}
	
	public ActionForward openPartnerDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception
			{
				logger.info(" in openSectorType()");
				ServletContext context = getServlet().getServletContext();
				HttpSession session = request.getSession();
				LoanDetailForCMVO loanDetailForCMVO=new LoanDetailForCMVO();	
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				Object sessionId = session.getAttribute("sessionID");
				//for check User session start
				String strFlag=null;
				if(sessionId!=null)
				{
					strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
				}
				String userId="";
				String businessDate="";
				if(userobj!=null)
				{
					userId=userobj.getUserId();
					businessDate=userobj.getBusinessdate();
				}else{
					logger.info(" in Execute method of  ReceiptMakerSearch action the session is out----------------");
					return mapping.findForward("sessionOut");
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
				String loanId="";
				String businessType="";
				LoanInitiationDAO dao=(LoanInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(LoanInitiationDAO.IDENTITY);
				String functionId=(String)session.getAttribute("functionId");
				int funid=Integer.parseInt(functionId);	
				logger.info("function_id is  : "+funid);
				businessType=request.getParameter("businessType");
				logger.info("businessType is :  "+businessType);
				session.setAttribute("businessType",businessType);
				if(funid==4000106)
					request.setAttribute("partner","partner");
				logger.info("In partner Details loanId " + session.getAttribute("loanId"));
				
				
				
				if (session.getAttribute("loanId") != null) {
					loanId = session.getAttribute("loanId").toString();
					boolean status=dao.saveDefaultValue(loanId,userId,businessDate,businessType);
					logger.info("In partner Details loanId " + session.getAttribute("loanId"));
					ArrayList loanHeader = dao.getLoanHeader(loanId);
					session.setAttribute("loanHeader", loanHeader);
					ArrayList<LoanDetailForCMVO> getPartnerDetails = dao.getPartnerDetailsforPopUp(loanDetailForCMVO,loanId,businessType);
					request.setAttribute("partnerDetails", getPartnerDetails);
				}
				
				
				
//				ArrayList<LoanDetailForCMVO> getPartnerDetails = dao.getPartnerDetails(loanDetailForCMVO,loanId,businessType);
//				request.setAttribute("partnerDetails", getPartnerDetails);
				
			    return mapping.findForward("openPartnerDetails");	
			}
	
	public ActionForward savePartnerDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse resopnse) throws Exception{
			ServletContext context = getServlet().getServletContext();
			LoanDetailForCMVO loanDetailForCMVO=new LoanDetailForCMVO();	
			HttpSession session = request.getSession();
		
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			Object sessionId = session.getAttribute("sessionID");
			//for check User session start
			String strFlag=null;
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
			String businessType="";
			String id = "";
			String msg="";
			String leadPartnerFlag="";
			leadPartnerFlag=CommonFunction.checkNull(request.getParameter("leadPartnerFlag"));
			id = request.getParameter("id");
			logger.info("In business partner  id " + id);
			loanDetailForCMVO.setId(id);
			String loanId = "";

			if (session.getAttribute("loanId") != null) {

				loanId = session.getAttribute("loanId").toString();
			} 
			logger.info("In business partner loanId " + loanId);
			if (session.getAttribute("businessType") != null) {

				businessType = session.getAttribute("businessType").toString();
			} 
			logger.info("In business partner businessType " + businessType);
			//loanDetailForCMVO.setBusinessType(businessType);
			loanDetailForCMVO.setLoanId(loanId);
			String userId=null;
			String bDate=null;
			if(userobj!=null)
			{
					userId=userobj.getUserId();
					bDate=userobj.getBusinessdate();
			}
		DynaValidatorForm loanDetailForm= (DynaValidatorForm)form;
		
		org.apache.commons.beanutils.BeanUtils.copyProperties(loanDetailForCMVO, loanDetailForm);
		

		loanDetailForCMVO.setMakerId(userId);
		loanDetailForCMVO.setMakerDate(bDate);
		loanDetailForCMVO.setLeadPartnerFlag(leadPartnerFlag);
		LoanInitiationDAO dao=(LoanInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(LoanInitiationDAO.IDENTITY);
		String sms=null;
		String recStatus=null;
	// parvez for loan amount	
//	Double loanAmount = Double.parseDouble(loanDetailForCMVO.getLoanAmount());   //////////////////sachin
//	Double pertPerc= Double.parseDouble(loanDetailForCMVO.getPartnerPercentage());
//	double partnerAmountD = (loanAmount * pertPerc)/100;
		
//	loanDetailForCMVO.setPartnerAmount(partnerAmountD+"");
	//end parvez 
		String result = dao.savePartnerDetails(loanDetailForCMVO,id,businessType);
		
		logger.info("result"+result);
		if(CommonFunction.checkNull(result).equalsIgnoreCase("S"))
			msg="S";
		else if(CommonFunction.checkNull(result).equalsIgnoreCase("M"))
			msg="M";
		else if(CommonFunction.checkNull(result).equalsIgnoreCase("NS"))
			msg="NS";
		else if(CommonFunction.checkNull(result).equalsIgnoreCase("NM"))
			msg="NM";
		else if(CommonFunction.checkNull(result).equalsIgnoreCase("EX"))
			msg="EX";
		else if(CommonFunction.checkNull(result).equalsIgnoreCase("prevent"))
			msg="prevent";
		else if(CommonFunction.checkNull(result).equalsIgnoreCase("contributionAmount"))
			msg="contributionAmount";
		else if(CommonFunction.checkNull(result).equalsIgnoreCase("leadPartnerExist"))
		msg="leadPartnerExist";
		request.setAttribute("msg", msg);
		ArrayList loanHeader = dao.getLoanHeader(loanId);
		session.setAttribute("loanHeader", loanHeader);
		ArrayList<LoanDetailForCMVO> getPartnerDetails = dao.getPartnerDetails(loanDetailForCMVO,loanId,businessType);
		request.setAttribute("partnerDetails", getPartnerDetails);
		String functionId=(String)session.getAttribute("functionId");
		int funid=Integer.parseInt(functionId);	
		logger.info("function_id is  : "+funid);
		if(funid==4000106)
		request.setAttribute("partner","partner");
		
		return mapping.findForward("savePartner");	
	}
	
	public ActionForward getPartnerDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse resopnse) throws Exception{
			ServletContext context = getServlet().getServletContext();
			LoanDetailForCMVO loanDetailForCMVO=new LoanDetailForCMVO();	
			HttpSession session = request.getSession();
		
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			Object sessionId = session.getAttribute("sessionID");
			//for check User session start
			String strFlag=null;
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
			String id = "";
			String businessType="";
			id = request.getParameter("id");
			logger.info("In business partner  id " + id);
			loanDetailForCMVO.setId(id);
			session.setAttribute("id", id);
			String loanId = "";

			if (session.getAttribute("loanId") != null) {

				loanId = session.getAttribute("loanId").toString();
			} 
			logger.info("In business partner loanId " + loanId);
			loanDetailForCMVO.setLoanId(loanId);
			
		DynaValidatorForm loanDetailForm= (DynaValidatorForm)form;
		
		org.apache.commons.beanutils.BeanUtils.copyProperties(loanDetailForCMVO, loanDetailForm);
		
		LoanInitiationDAO dao=(LoanInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(LoanInitiationDAO.IDENTITY);
		if (session.getAttribute("businessType") != null) {

			businessType = session.getAttribute("businessType").toString();
		} 
		logger.info("In business partner businessType " + businessType);
		
		ArrayList<LoanDetailForCMVO> result = dao.getPartnerBusDetails(loanDetailForCMVO,id,loanId,businessType);
		request.setAttribute("list", result);
		String servicingPartnerflagStatus = ((LoanDetailForCMVO)result.get(0)).getServicingPartnerFlag();
		if(CommonFunction.checkNull(servicingPartnerflagStatus).equalsIgnoreCase("Yes"))
		request.setAttribute("servicingPartnerflagStatus", servicingPartnerflagStatus);
		String leadPartnerFlagStatus = ((LoanDetailForCMVO)result.get(0)).getLeadPartnerFlag();
		if(CommonFunction.checkNull(leadPartnerFlagStatus).equalsIgnoreCase("Yes"))
		request.setAttribute("leadPartnerFlagStatus", leadPartnerFlagStatus);
		ArrayList loanHeader = dao.getLoanHeader(loanId);
		session.setAttribute("loanHeader", loanHeader);
		ArrayList<LoanDetailForCMVO> getPartnerDetails = dao.getPartnerDetails(loanDetailForCMVO,loanId,businessType);
		request.setAttribute("partnerDetails", getPartnerDetails);
		String functionId=(String)session.getAttribute("functionId");
		int funid=Integer.parseInt(functionId);	
		logger.info("function_id is  : "+funid);
		if(funid==4000106)
		request.setAttribute("partner","partner");
		
		return mapping.findForward("savePartner");	
	}
	
	public ActionForward deletePartnerDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		LoanDetailForCMVO loanDetailForCMVO=new LoanDetailForCMVO();
		 logger.info("In deletePartnerDetails");
	
		    boolean flag =false;
		    HttpSession session = request.getSession();
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here in deletePartnerDetails action the session is out----------------");
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
			
		 int status=0;
		String businessType="";
		 DynaValidatorForm loanDetailForm= (DynaValidatorForm)form;
			
			org.apache.commons.beanutils.BeanUtils.copyProperties(loanDetailForCMVO, loanDetailForm);
			
			LoanInitiationDAO dao=(LoanInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(LoanInitiationDAO.IDENTITY);
			
			logger.info("Implementation class: "+dao.getClass()); 
		
		 String partnerDtl[] = request.getParameterValues("chk");
		String loanId="";
		
		 if (session.getAttribute("loanId") != null) {

				loanId = session.getAttribute("loanId").toString();
			} 
			logger.info("In business partner loanId " + loanId);
			loanDetailForCMVO.setLoanId(loanId);
			if (session.getAttribute("businessType") != null) {

				businessType = session.getAttribute("businessType").toString();
			} 
			logger.info("In business partner businessType " + businessType);
		    for(int k=0;k<partnerDtl.length;k++)
			 {
		    	 logger.info("the value of financIncome "+partnerDtl[k]);
		 status = dao.deletePartnerDtl(partnerDtl[k],loanId,businessType);
			 }
		 if(status>0)
		 {
			 request.setAttribute("msg", "Del"); 
			
  	  }
		  else
		  {
				 request.setAttribute("msg", "DE"); 
		  }
		 
		 ArrayList loanHeader = dao.getLoanHeader(loanId);
			session.setAttribute("loanHeader", loanHeader);
			ArrayList<LoanDetailForCMVO> getPartnerDetails = dao.getPartnerDetails(loanDetailForCMVO,loanId,businessType);
			request.setAttribute("partnerDetails", getPartnerDetails);
			String functionId=(String)session.getAttribute("functionId");
			int funid=Integer.parseInt(functionId);	
			logger.info("function_id is  : "+funid);
			if(funid==4000106)
			request.setAttribute("partner","partner");

			return mapping.findForward("savePartner");	
	}
}
