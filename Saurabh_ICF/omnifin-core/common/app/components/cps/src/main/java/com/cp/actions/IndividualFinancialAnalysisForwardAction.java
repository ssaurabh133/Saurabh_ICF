/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.cp.actions;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
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

import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.FieldVerificationDAO;

import com.cp.dao.IndividualFinancialAnalysisDAO;

import com.cp.vo.FinancialAnalysisVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.communication.engn.daoImplMySql.SmsDAOImpl;
import com.connect.ConnectionDAO;
/** 
 * MyEclipse Struts
 * Creation date: 11-18-2011
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class IndividualFinancialAnalysisForwardAction extends Action {
	private static final Logger logger = Logger.getLogger(IndividualFinancialAnalysisForwardAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		logger.info("In IndividualFinancialAnalysisForwardAction (execute)");
		HttpSession session = request.getSession();
		boolean flag=false;
		String bDate="";
		String companyId="";
		String userId="";
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		if(userobj!=null)
		{
			bDate=userobj.getBusinessdate();
			companyId=""+userobj.getCompanyId();
			userId=userobj.getUserId();
		}else{
			logger.info("here in execute IndividualFinancialAnalysisForwardAction  method of action the session is out----------------");
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
		
		if(flag)
		{
			logger.info("logout in action");
			return mapping.findForward("logout");
		}
		DynaValidatorForm FinancialAnalysisDynaValidatorForm = (DynaValidatorForm) form;
		FinancialAnalysisVo vo = new FinancialAnalysisVo();
	    try {
			org.apache.commons.beanutils.BeanUtils.copyProperties(vo, FinancialAnalysisDynaValidatorForm);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		IndividualFinancialAnalysisDAO dao=(IndividualFinancialAnalysisDAO)DaoImplInstanceFactory.getDaoImplInstance(IndividualFinancialAnalysisDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass()); 	// changed by asesh
		//IndividualFinancialAnalysisDAO dao = new IndividualFinancialAnalysisDAOImpl();
		String dealId = "";
		String checkStageM = "";
		boolean satge = false;
		boolean ratioStatus = false;
		boolean financialStatus = false;
		 if(session.getAttribute("financialDealId")!=null)
		 {
			 dealId = session.getAttribute("financialDealId").toString();
		 }
		if (dealId != null && !dealId.equalsIgnoreCase("")) {
			//logger.info("In IndividualFinancialAnalysisForwardAction (execute) dealid "	+ dealId);
				ratioStatus = dao.individualRatioAnalysisUpdate(dealId);
				financialStatus=dao.individualFinancialAnalysisUpdate(dealId);
				if(ratioStatus)
				{
					checkStageM=CommonFunction.stageMovement(companyId, "DC","F",dealId, "FAC", bDate,userId);
					logger.info("checkStageM : "+checkStageM);
				}
				if(checkStageM.equalsIgnoreCase("S")){
			 
					satge = dao.individualFinancialAnalysisForward(dealId);
					  //Rohit Changes for SMS & Email
					  String emailcheckQuery="SELECT COUNT(1) FROM CR_DEAL_MOVEMENT_DTL WHERE DEAL_FORWARDED='0000-00-00 00:00:00' AND IFNULL(DEAL_FORWARD_USER,'')='' AND DEAL_STAGE_ID<>'UNC' AND DEAL_ID='"+dealId+"' ";
					  String res=ConnectionDAO.singleReturn(emailcheckQuery);
					  String EventName="";
						String rec="";
						int cont=0;
					  if(res.equalsIgnoreCase("0")){
						  EventName = "UNDERWRITER_QUEUE";
							 rec = "Select count(1) from comm_event_list_m where Event_name='"
									+ EventName + "' and rec_status='A' ";
							 cont = Integer.parseInt(ConnectionDAO
									.singleReturn(rec));
							if (cont != 0) {
								boolean stats = new SmsDAOImpl().getEmailDetails(
										dealId, bDate, EventName);
							}
					  }
					  //Rohit end
				}else{
				    request.setAttribute("checkStageM", checkStageM);
				}
				if (satge) {
					
					request.setAttribute("sms", "F");
					
					session.removeAttribute("financialDetails");
				}
				else if(!financialStatus)
				{
					request.setAttribute("sms", "B");
					session.removeAttribute("incomeDetailsByDeal");
				}
				else if(financialStatus && !ratioStatus)
				{
					request.setAttribute("sms", "BB");
					logger.info("ratioStatusFlag::::::::"+request.getAttribute("sms").toString());
					session.removeAttribute("incomeDetailsByDeal");
				}

				request.setAttribute("status", satge);
		} else {
			request.setAttribute("notForward", "B");
		}
		  //ArrayList paramList = dao.getParamDetailDetails("I");
	    ArrayList customerTypeList = dao.getCustomerTypeList(dealId);
	    ArrayList sourceTypeList = dao.getIncomeSourceTypeList();
	    ArrayList customerNameList = dao.getCustomerName(dealId, "PRAPPL");
	    
	    request.setAttribute("customerNameList", customerNameList);
	    request.setAttribute("customerTypeList", customerTypeList);
	    request.setAttribute("sourceTypeList", sourceTypeList);
	    FinancialAnalysisVo fvo=new FinancialAnalysisVo();
	    vo.setLbxDealNo(dealId);
	    ArrayList incomeDetailList=dao.getIncomeDetailList(fvo);
	    request.setAttribute("incomeDetailList",incomeDetailList);

		FieldVerificationDAO fieldVerificationdao=(FieldVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(FieldVerificationDAO.IDENTITY);
        logger.info("Implementation class: "+fieldVerificationdao.getClass()); 	// changed by asesh
	    //FieldVerificationDAO fieldVerificationdao=new FieldVerificationDAOImpl();	
	    ArrayList verifMethodList = fieldVerificationdao.getVerifMethodListList();
	    request.setAttribute("verifMethodList", verifMethodList);
		return mapping.getInputForward();

	}
}