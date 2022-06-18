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

import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.CreditProcessingDAO;
import com.cp.vo.CommonDealVo;
import com.lockRecord.action.ReleaseRecordFromObject;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class QualityCheckAction extends DispatchAction {

	private static final Logger logger = Logger
			.getLogger(QualityCheckAction.class.getName());

	public ActionForward searchDealForQualityCheck(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger.info("In QualityCheckAction (searchDealForQualityCheck)");
		HttpSession session = request.getSession();
		String userId = "";
		String branch = "";
		session.removeAttribute("strFlagDV");
		UserObject userobj = (UserObject) session.getAttribute("userobject");

		if (userobj != null) {
			userId = userobj.getUserId();
			branch = userobj.getBranchId();
		} else {
			logger.info("here in searchDealForQualityCheck method of QualityCheckAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		// for check User session start
		ServletContext context = getServlet().getServletContext();
		String strFlag = "";
		if (sessionId != null) {
			strFlag = UserSessionCheck.checkSameUserSession(userobj, sessionId.toString(), "", request);
		}
		logger.info("strFlag .............. " + strFlag);
		if (!strFlag.equalsIgnoreCase("")) {
			if (strFlag.equalsIgnoreCase("sameUserSession")) {
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			} else if (strFlag.equalsIgnoreCase("BODCheck")) {
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}
		session.setAttribute("userId", userId);// For lov
		DynaValidatorForm CommonDealDynaValidatorForm = (DynaValidatorForm) form;
		ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
		String dateFormat = resource.getString("lbl.dateFormat(dd-mm-yyyy)");
		CommonDealVo vo = new CommonDealVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,CommonDealDynaValidatorForm);
		String stage = CommonFunction.checkNull(session.getAttribute("stage"));
		logger.info("stage: " + stage);
		vo.setStage(stage);
		vo.setBranchId(branch);
		if (CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
			vo.setReportingToUserId(userId);
		logger.info("user Id:::::" + vo.getReportingToUserId());
		// vo.setUserId(userobj.getUserName());

		if (vo.getApplicationdate().equalsIgnoreCase(dateFormat)) {
			vo.setApplicationdate("");
		}
		CreditProcessingDAO creditDAO=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+creditDAO.getClass()); 			// changed by asesh
		//CreditProcessingDAO creditDAO = new CreditProcessingDAOImpl();
		ArrayList<CommonDealVo> dealdetails = new ArrayList<CommonDealVo>();

		logger.info("current page link .......... "+ request.getParameter("d-49520-p"));
		String funcId=CommonFunction.checkNull(session.getAttribute("functionId")).trim();
		
		int currentPageLink = 0;
		if (request.getParameter("d-49520-p") == null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
		{
			currentPageLink = 1;
		}
		else
		{
			currentPageLink = Integer.parseInt(request.getParameter("d-49520-p"));
		}

		 logger.info("current page link ................ "+ request.getParameter("d-49520-p"));
		int funId = 0;
		String qualityCheckStatus="N";
		if(session.getAttribute("functionId")!=null)
		{
			String functionId=CommonFunction.checkNull(session.getAttribute("functionId")).trim();
			funId = Integer.parseInt(functionId);
		}
		if(funId!=4000103)
		{
			 String qualityCheckStatusQuery="SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='EDITABLE_QUALITY_CHECK_DEAL'";
			 logger.info("In searchDealForQualityCheck()  :  "+ qualityCheckStatusQuery);
			 qualityCheckStatus=ConnectionDAO.singleReturn(qualityCheckStatusQuery);
		}		
	    if(qualityCheckStatus.equalsIgnoreCase("")||qualityCheckStatus.equalsIgnoreCase("N"))
	    {
	    	
	    	vo.setQualityCheckStatus("N");
	    	vo.setFunctionId(funcId);
	    	vo.setCurrentPageLink(currentPageLink);
	 		dealdetails = creditDAO.fetchQualityCheckDetail(vo);
	 		logger.info("In searchDealForQualityCheck....list: "+ dealdetails.size());
	 		request.setAttribute("list", dealdetails);
	 		session.setAttribute("strFlagQ", "strFlagQ");
	 		session.setAttribute("strFlagDV", "strFlagDV");
	     }
	     else
	     {
	    	
	    	    vo.setQualityCheckStatus("Y");
	    	    vo.setCurrentPageLink(currentPageLink);
	    	    dealdetails = creditDAO.fetchQualityCheckDetail(vo);
		 		logger.info("In searchDealForQualityCheck....list: "+ dealdetails.size());
		 		request.setAttribute("list", dealdetails);
		 		  session.removeAttribute("asd");
		 		  session.setAttribute("groupTypeActivated","groupTypeActivated");
				  session.removeAttribute("corporate");
				  session.setAttribute("viewDeviation","viewDeviation");
				  session.removeAttribute("viewDeviationUND");
				  session.removeAttribute("searchLoan");
				  session.setAttribute("DealCap", "DC");
                  session.removeAttribute("loanInit");
                  session.removeAttribute("loanId");
                  session.removeAttribute("viewDeal");
                  session.removeAttribute("underWriter");
                  session.removeAttribute("underWriterViewData");
                  session.removeAttribute("cmAuthor");		
                  session.removeAttribute("queryResponse");
             	// session.setAttribute("underWriter", "underWriter");
                  session.removeAttribute("underWriter");
				if (request.getParameter("hideId") != null && request.getParameter("operation") != null) {
					session.setAttribute("customerId", request.getParameter("hideId"));
					session.setAttribute("operation", request.getParameter("operation"));
				} else {
					session.removeAttribute("maxIdInCM");
					session.removeAttribute("dealId");
					session.removeAttribute("customerId");
					session.removeAttribute("loanInitDocs");
				
					session.removeAttribute("operation");
					session.removeAttribute("customerInfo");
					
				}
		 		
	     }
		
       
		//session.removeAttribute("stage");
		return mapping.findForward("success");
	}
	
	public ActionForward qualityCheckSaveDeal(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger.info("In QualityCheckAction (qualityCheckSaveDeal)");
		HttpSession session = request.getSession();
		String userId = "";
		String branch = "";
		String bDate = "";
		String companyId = "";
		session.removeAttribute("strFlagDV");
		UserObject userobj = (UserObject) session.getAttribute("userobject");

		if (userobj != null) {
			userId = userobj.getUserId();
			branch = userobj.getBranchId();
			bDate = userobj.getBusinessdate();
			companyId = ""+userobj.getCompanyId();
		} else {
			logger.info("here in qualityCheckSaveDeal method of QualityCheckAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		// for check User session start
		ServletContext context = getServlet().getServletContext();
		String strFlag = "";
		if (sessionId != null) {
			strFlag = UserSessionCheck.checkSameUserSession(userobj, sessionId.toString(), "", request);
		}
		logger.info("strFlag .............. " + strFlag);
		if (!strFlag.equalsIgnoreCase("")) {
			if (strFlag.equalsIgnoreCase("sameUserSession")) {
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			} else if (strFlag.equalsIgnoreCase("BODCheck")) {
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}
		DynaValidatorForm CommonDealDynaValidatorForm = (DynaValidatorForm) form;

		CommonDealVo vo = new CommonDealVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,CommonDealDynaValidatorForm);
		String stage = CommonFunction.checkNull(request.getParameter("stage"));
		logger.info("stage: " + stage);
		String txnType="";
		if(stage.equalsIgnoreCase("F"))
			txnType = "DC";
		if(stage.equalsIgnoreCase("A"))
			txnType = "LIM";
		vo.setStage(stage);
		vo.setBranchId(branch);
		vo.setReportingToUserId(userId);
		vo.setBusinessdate(bDate);
		vo.setCompanyId(companyId);
		logger.info("user Id:::::" + vo.getUserId());

		CreditProcessingDAO creditDAO=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+creditDAO.getClass()); 			// changed by asesh
		

		logger.info("current page link .......... "+ request.getParameter("d-49520-p"));

		int currentPageLink = 0;
		if (request.getParameter("d-49520-p") == null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
		{
			currentPageLink = 1;
		}
		else
		{
			currentPageLink = Integer.parseInt(request.getParameter("d-49520-p"));
		}

		logger.info("current page link ................ "+ request.getParameter("d-49520-p"));
		vo.setCurrentPageLink(currentPageLink);
		boolean flag=false;
		String remarks = request.getParameter("remarks");
	    String dealId = request.getParameter("dealId");
	    String decision = request.getParameter("decision");
	    
	    String[] remarksArr = remarks.split("@"); 
	    String[] dealIdArr = dealId.split("@");	
	    String[] decisionArr = decision.split("@");
	    String dealSperate="";
	    for (int i = 0; i < dealIdArr.length; i++) {
			
			dealSperate+=","+dealIdArr[i];
	   }
	    dealSperate=dealSperate+",0";
 	    String collectDealId= dealSperate.substring(1, dealSperate.length());
	    logger.info("In collectDealId sub: "+ collectDealId);
	    int funId = 0;
		String qualityCheckStatus="N";
		if(session.getAttribute("functionId")!=null)
		{
			String functionId=CommonFunction.checkNull(session.getAttribute("functionId")).trim();
			funId = Integer.parseInt(functionId);
		}
		if(funId!=4000103)
		{
			 String qualityCheckStatusQuery="SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='EDITABLE_QUALITY_CHECK_DEAL'";
			 logger.info("In searchDealForQualityCheck()  :  "+ qualityCheckStatusQuery);
			 qualityCheckStatus=ConnectionDAO.singleReturn(qualityCheckStatusQuery);
		}	
		
	   String checkConfirmDealQuery="SELECT COUNT(DEAL_ID)  FROM CR_DEAL_DTL WHERE  QUALITY_CHECK_DC_CONFIRM IS NULL AND DEAL_ID IN ("+collectDealId+")";
	    logger.info("checkConfirmDealQuery: "+checkConfirmDealQuery);
	    String checkConfirmDeal=ConnectionDAO.singleReturn(checkConfirmDealQuery);
	    if(qualityCheckStatus.equalsIgnoreCase("Y"))
	    {
	    	  if(checkConfirmDeal.equalsIgnoreCase("0") )
	  		    flag = creditDAO.saveQualityCheckDetails(remarksArr,dealIdArr,decisionArr,txnType,vo);
	  	    else
	  	    	request.setAttribute("sms","CE");
	    }
	    else
	    {
	    	 flag = creditDAO.saveQualityCheckDetails(remarksArr,dealIdArr,decisionArr,txnType,vo);
	    }
		
		if(flag)
		{
			ArrayList<CommonDealVo> dealdetails = new ArrayList<CommonDealVo>();
			dealdetails = creditDAO.fetchQualityCheckDetail(vo);
			logger.info("In searchDealForQualityCheck....list: "+ dealdetails.size());
			request.setAttribute("list", dealdetails);
			request.setAttribute("sms","S");
		}
	
		return mapping.findForward("success");
	
	}
	public ActionForward confirmForwardDeal(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger.info("In QualityCheckAction (confirmForwardDeal)");
		HttpSession session = request.getSession();
		String userId = "";
		String branch = "";
		String bDate = "";
		String companyId = "";
		session.removeAttribute("strFlagDV");
		UserObject userobj = (UserObject) session.getAttribute("userobject");

		if (userobj != null) {
			userId = userobj.getUserId();
			branch = userobj.getBranchId();
			bDate = userobj.getBusinessdate();
			companyId = ""+userobj.getCompanyId();
		} else {
			logger.info("here in confirmForwardDeal method of QualityCheckAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		// for check User session start
		ServletContext context = getServlet().getServletContext();
		String strFlag = "";
		if (sessionId != null) {
			strFlag = UserSessionCheck.checkSameUserSession(userobj, sessionId.toString(), "", request);
		}
		logger.info("strFlag .............. " + strFlag);
		if (!strFlag.equalsIgnoreCase("")) {
			if (strFlag.equalsIgnoreCase("sameUserSession")) {
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			} else if (strFlag.equalsIgnoreCase("BODCheck")) {
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}
		CreditProcessingDAO dao=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass()); 	//changed by asesh
		//CreditProcessingDAO dao = new CreditProcessingDAOImpl();
		String dealId = "";
		String status = "";
		boolean satge = false;
		String sms = "";
		String product="";
		if (session.getAttribute("dealId") != null) 
			dealId = session.getAttribute("dealId").toString();
		else if (session.getAttribute("maxId") != null) 
			dealId = session.getAttribute("maxId").toString();
		
		if (dealId != null && !dealId.equalsIgnoreCase("")) 
		{
					
			logger.info("QualityCheckAction (confirmForwardDeal) dealid "+ dealId);
			String under = request.getParameter("status");
			logger.info("QualityCheckAction (confirmForwardDeal) " + under);
			if ((dealId != null && !dealId.equalsIgnoreCase(""))&& (under == null)) 
			{
				status = dao.checkStage(dealId);
				logger.info("QualityCheckAction (confirmForwardDeal) Status of stage: "+ status);
				if (status != null && status.equals("")) 
				{						
					ArrayList list = dao.getAssetLoanDetailAmount(dealId);
					if(list.size()>0)
					{
						ArrayList sublist=(ArrayList)list.get(0);
						String repyType=CommonFunction.checkNull((String)sublist.get(0));
						String astFlag=CommonFunction.checkNull((String)sublist.get(1));
						logger.info("repyType     :    "+repyType);
						logger.info("astFlag      :    "+astFlag);						
						if(repyType.trim().equalsIgnoreCase("I") && astFlag.trim().equalsIgnoreCase("A"))
						{
							StringBuilder query=new StringBuilder();						
							query.append("select count(1) from cr_deal_collateral_m a join cr_asset_collateral_m b on(b.ASSET_ID=a.ASSETID) where b.ASSET_TYPE='ASSET' and b.ASSET_COLLATERAL_CLASS='VEHICLE' and a.DEAL_ID="+dealId.trim());
							logger.info("In execute()  query   :   "+ query.toString());
							String ct=ConnectionDAO.singleReturn(query.toString());
							logger.info("In execute()  count   :   "+ ct);
							int value=0;
							if(ct == null)
								value=0;
							else
								value=Integer.parseInt(ct);
							logger.info("value      :    "+value);
							if(value>0)//this deal have some asset of vichel type
							{
								String astcstLD=(String)sublist.get(2);
								String lnamtLD=(String)sublist.get(3);					
								String astcstAD=(String)sublist.get(4);					
								String lnamtAD=(String)sublist.get(5);							
								double asstcstLD;
								double loanamtLD;
								double asstcstAD;
								double loanamtAD;							
								if(astcstLD ==null)
									asstcstLD=0.0;
								else
									asstcstLD=Double.parseDouble(astcstLD);							
								if(lnamtLD ==null)
									loanamtLD=0.0;
								else
									loanamtLD=Double.parseDouble(lnamtLD);							
								if(astcstAD ==null)
									asstcstAD=0.0;
								else
									asstcstAD=Double.parseDouble(astcstAD);							
								if(lnamtAD ==null)
									loanamtAD=0.0;
								else
									loanamtAD=Double.parseDouble(lnamtAD);
								
								logger.info("asstcstLD    :    "+asstcstLD);
								logger.info("loanamtLD    :    "+loanamtLD);
								logger.info("asstcstAD    :    "+asstcstAD);
								logger.info("loanamtAD    :    "+loanamtAD);
								
								int val=0;
								if(asstcstLD != asstcstAD && loanamtLD == loanamtAD)
									val=1;
								if(asstcstLD == asstcstAD && loanamtLD != loanamtAD)
									val=2;
								if(asstcstLD != asstcstAD && loanamtLD != loanamtAD)
									val=3;
								
								logger.info("val    :    "+val);
								if(val>0)
								{
									request.setAttribute("astLonError","astLonError");
									request.setAttribute("val",val);
									request.setAttribute("asstcstAD",asstcstAD);
									request.setAttribute("loanamtAD",loanamtAD);
									return mapping.getInputForward();
								}							
							}		
						}
					}
					String sqlQuery="SELECT REFRESH_FLAG FROM cr_deal_dtl WHERE DEAL_ID='"+dealId+"'";					
					String refreshFlagValue = ConnectionDAO.singleReturn(sqlQuery);
					boolean flag1=false;
					for(int i=0;i<15;i++)
					{						
						if(refreshFlagValue!="" && refreshFlagValue.charAt(i)=='Y')
						{
							int j= i+1;
							request.setAttribute("status", "CP"+j);
							logger.info("refresh value from request set ...................... "+request.getAttribute("status"));
							flag1= true;
							break;							
						}						
					}
					if(flag1)
					{
						return mapping.findForward("confirmForwardDeal");
					}
					// String checkStageM=CommonFunction.stageMovement(companyId, "DC","F",dealId, "DC", bDate,userId);
					// logger.info("checkStageM : "+checkStageM);		
					
					// if(CommonFunction.checkNull(checkStageM).equalsIgnoreCase("S"))
					// {	
						 	product=dao.getProductType(dealId);
							int count=dao.getViabilityCount(dealId);
							if(!product.equalsIgnoreCase("CV")&& count>0)
							{
								boolean status1 =dao.deleteViabilityDtl(dealId);
								session.setAttribute("asd", "asd");
								satge = dao.updateConfirmDeal(dealId);	
							}								       
							else 
							{
								boolean viabilityStatus =dao.checkEMIAmountInViability(dealId);	
								logger.info("In Side if Block::viabilityStatus:::::::::::::"+viabilityStatus);
								if(viabilityStatus)
								{
									status="VS";
									request.setAttribute("sms", "");
								}
								else{
									session.setAttribute("asd", "asd");
									satge = dao.updateConfirmDeal(dealId);	
								}
							}
							/*}
					 else
					 {
						request.setAttribute("checkStageM", checkStageM); 
						status="CSM";
					 }*/					 
				}
				if (satge) 
				{					
					if(context!=null)
					{
						boolean Lflag = ReleaseRecordFromObject.releaselockedRecord(userId, context);
					}
					sms = "S";
					request.setAttribute("sms", sms);
				}
				
				request.setAttribute("status", status);
			}
			// Start By Prashant
		    if(CommonFunction.checkNull(status).equalsIgnoreCase("CUSTREF"))
		    {
		    	String referenceCountParaQuery="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='APPLICANT_REFERENCE_COUNT'";
		    	String referenceCountPara = ConnectionDAO.singleReturn(referenceCountParaQuery);
		    	logger.info("From Pararmeter_mst referenceCountParaQuery "+referenceCountParaQuery+"referenceCountPara: "+referenceCountPara);
		    	request.setAttribute("referenceCountPara", referenceCountPara);
		    }
		   // End By Prashant
		}
		return mapping.findForward("confirmForwardDeal");
	}


}
