/*Ankita Start of CP level Partner Capturing*/
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

import com.cm.dao.LoanInitiationDAO;
import com.cm.vo.LoanDetailForCMVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.CovenantProposalTrackingDAO;
import com.cp.dao.ImdDAO;
import com.cp.vo.ImdMakerVO;
import com.cp.vo.LoanDetailVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class LoanDetailCPProcessAction  extends DispatchAction
{
	private static final Logger logger = Logger.getLogger(LoanDetailCPProcessAction.class.getName());	
	static ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	static String dbType=resource.getString("lbl.dbType");
	
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
				String dealId="";
				String businessType="";
				CovenantProposalTrackingDAO dao=(CovenantProposalTrackingDAO)DaoImplInstanceFactory.getDaoImplInstance(CovenantProposalTrackingDAO.IDENTITY);
				String functionId=(String)session.getAttribute("functionId");
				int funid=Integer.parseInt(functionId);	
				logger.info("function_id is  : "+funid);
				businessType=request.getParameter("businessType");
				logger.info("businessType is :  "+businessType);
				session.setAttribute("businessType",businessType);
				if(funid==3000206)
					request.setAttribute("partner","partner");
				
				if(!CommonFunction.checkNull((String)request.getParameter("dealId")).equalsIgnoreCase("")){
					dealId = CommonFunction.checkNull((String)request.getParameter("dealId"));
				}else{
					dealId = CommonFunction.checkNull((String)session.getAttribute("dealId"));
				}
				
				if (!CommonFunction.checkNull(dealId).equalsIgnoreCase("")) {
					//boolean status=dao.saveDefaultValue(dealId,userId,businessDate,businessType);
					//logger.info("In partner Details loanId " + session.getAttribute("dealId"));
					ArrayList loanHeader = dao.getLoanHeader(dealId);
					session.setAttribute("loanHeader", loanHeader);
					ArrayList<LoanDetailForCMVO> getPartnerDetails = dao.getPartnerDetailsforPopUp(loanDetailForCMVO,dealId,businessType);
					request.setAttribute("partnerDetails", getPartnerDetails);
				}
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
			}else{
				loanId=CommonFunction.checkNull(request.getParameter("loanId"));
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
		CovenantProposalTrackingDAO dao=(CovenantProposalTrackingDAO)DaoImplInstanceFactory.getDaoImplInstance(CovenantProposalTrackingDAO.IDENTITY);
		String sms=null;
		String recStatus=null;
		String result = dao.savePartnerDetails(loanDetailForCMVO,id,businessType);
		
		logger.info("result=============>>>>>>>>>>."+result);
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
		if(funid==3000206)
		request.setAttribute("partner","partner");
		
		return mapping.findForward("savePartner");	
	}
         
// pooja code starts
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
     			}else{
    				loanId=CommonFunction.checkNull(request.getParameter("loanId"));
    			} 
     			logger.info("In business partner loanId " + loanId);
     			loanDetailForCMVO.setLoanId(loanId);
     			
     		DynaValidatorForm loanDetailForm= (DynaValidatorForm)form;
     		
     		org.apache.commons.beanutils.BeanUtils.copyProperties(loanDetailForCMVO, loanDetailForm);
     		
     		CovenantProposalTrackingDAO dao=(CovenantProposalTrackingDAO)DaoImplInstanceFactory.getDaoImplInstance(CovenantProposalTrackingDAO.IDENTITY);
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
     		if(funid==3000206)
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
     			
     			CovenantProposalTrackingDAO dao=(CovenantProposalTrackingDAO)DaoImplInstanceFactory.getDaoImplInstance(CovenantProposalTrackingDAO.IDENTITY);
     			
     			logger.info("Implementation class: "+dao.getClass()); 
     		
     		 String partnerDtl[] = request.getParameterValues("chk");
     		String loanId="";
     		
     		 if (session.getAttribute("loanId") != null) {

     				loanId = session.getAttribute("loanId").toString();
     			} else{
    				loanId=CommonFunction.checkNull(request.getParameter("loanId"));
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
     			if(funid==3000206)
     			request.setAttribute("partner","partner");

     			return mapping.findForward("savePartner");	
     	}
// pooja code end

}
