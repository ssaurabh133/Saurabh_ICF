package com.cp.actions;

import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;

import com.cm.dao.LoanInitiationDAO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.CreditProcessingDAO;
import com.cp.dao.FieldVerificationDAO;
import com.cp.vo.FieldVerificationVo;
import com.lockRecord.action.LockRecordCheck;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class FieldVerificationSaveAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(FieldVerificationSaveAction.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dbType=resource.getString("lbl.dbType");
	public ActionForward saveFieldVerInitiation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		logger.info("Inside saveFieldVerInitiation......insertFieldVerInitiation");
		
		HttpSession session=request.getSession(false);
		
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		String userId="";
		String bDate="";
		String companyId="";
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
				companyId=""+userobj.getCompanyId();
				
		}else{
			logger.info("here in saveFieldVerInitiation method of FieldVerificationSaveAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}

		boolean flag=false;
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
		 if (isTokenValid(request, true)) {
		      FieldVerificationVo vo = new FieldVerificationVo();
		      DynaValidatorForm FieldVarificationCheckDynaValidatorForm = (DynaValidatorForm)form;
		      BeanUtils.copyProperties(vo, FieldVarificationCheckDynaValidatorForm);
		      vo.setMakerId(userId);
		      vo.setMakerDate(bDate);

		      String dealId = CommonFunction.checkNull(session.getAttribute("dealId"));

		      logger.info("deal id is....................." + dealId);
		      vo.setDealId(dealId);

		      FieldVerificationDAO filedDAO = (FieldVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance("FV");
		      logger.info("Implementation class: " + filedDAO.getClass());

		      String[] action = vo.getVerificationAction();
		      String[] entityId = vo.getEntityId();
		      String[] externalAppUserId = vo.getExternalAppUserId();
		      String[] internalAppUserId = vo.getInternalAppUserId();
		      String[] appraiser = vo.getAppraiser();
		      String[] entSubType = vo.getEntSubType();
		      String checkQuery = "";
		      String validateApprisalName = null;
		      String checkInitiatedQuery = "";
		      String checkAppraiserQuery = "";
		      logger.info("entityId.length: " + entityId.length);
		      for (int i = 0; i < entityId.length; i++)	  
		      {
		        if (CommonFunction.checkNull(action[i]).equalsIgnoreCase("I")) {
		          if ((CommonFunction.checkNull(appraiser[i]).equalsIgnoreCase("INTERNAL")) && (CommonFunction.checkNull(internalAppUserId[i]).equalsIgnoreCase(""))) {
		            validateApprisalName = "blank";
		          }
		          if ((CommonFunction.checkNull(appraiser[i]).equalsIgnoreCase("EXTERNAL")) && (CommonFunction.checkNull(externalAppUserId[i]).equalsIgnoreCase(""))) {
		            validateApprisalName = "blank";
		          }
		        }
		        if (CommonFunction.checkNull(validateApprisalName).equalsIgnoreCase("")) {														  
		          if ((entSubType[i].equalsIgnoreCase("BUSINESS")) || (entSubType[i].equalsIgnoreCase("MARKET")))
		          {
		            checkInitiatedQuery = "SELECT COUNT(1) FROM cr_deal_verification_dtl WHERE VERIFICATION_ACTION='I' AND ENTITY_SUB_TYPE='" + entSubType[i] + "' AND REC_STATUS<>'C' AND STAGE='DC' AND DEAL_ID='" + dealId + "'";
												   
		          }
		          else
		          {
		            checkInitiatedQuery = "SELECT COUNT(1) FROM cr_deal_verification_dtl WHERE VERIFICATION_ACTION='I' AND ENTITY_ID='" + entityId[i] + "' AND REC_STATUS<>'C' AND STAGE='DC' AND DEAL_ID='" + dealId + "'";
		          }

		          logger.info("checkInitiatedQuery:  " + checkInitiatedQuery);
		          String checkInitiatedCount = ConnectionDAO.singleReturn(checkInitiatedQuery);
		          logger.info("checkInitiatedCount:  " + checkInitiatedCount);
		          if ((!CommonFunction.checkNull(checkInitiatedCount).equalsIgnoreCase("0")) && (CommonFunction.checkNull(action[i]).equalsIgnoreCase("W")))
		          {
		            request.setAttribute("alreadyInitiated", "alreadyInitiated");
		            return mapping.findForward("save");
		          }

		          if (appraiser[i].equalsIgnoreCase("INTERNAL"))
		          {
		            if ((entSubType[i].equalsIgnoreCase("BUSINESS")) || (entSubType[i].equalsIgnoreCase("MARKET")))
		            {
		              checkQuery = "SELECT COUNT(1) FROM cr_deal_verification_dtl WHERE APPRAISER_TYPE ='INTERNAL' AND INTERNAL_APPRAISER ='" + internalAppUserId[i] + "' AND VERIFICATION_ACTION='I' AND ENTITY_SUB_TYPE='" + entSubType[i] + "' AND REC_STATUS<>'C' AND STAGE='DC' AND DEAL_ID='" + dealId + "'";
		              logger.info("Internal Entity sub type is BUSINESS or MARKET " + checkQuery);
		            }
		            else
		            {
		              checkQuery = "SELECT COUNT(1) FROM cr_deal_verification_dtl WHERE APPRAISER_TYPE ='INTERNAL' AND INTERNAL_APPRAISER ='" + internalAppUserId[i] + "' AND VERIFICATION_ACTION='I' AND ENTITY_ID='" + entityId[i] + "' AND REC_STATUS<>'C' AND STAGE='DC' AND DEAL_ID='" + dealId + "'";
		            }

		          }
		          else if ((entSubType[i].equalsIgnoreCase("BUSINESS")) || (entSubType[i].equalsIgnoreCase("MARKET")))
		          {
		            checkQuery = "SELECT COUNT(1) FROM cr_deal_verification_dtl WHERE APPRAISER_TYPE ='EXTERNAL' AND EXTERNAL_APPRAISER='" + externalAppUserId[i] + "' AND VERIFICATION_ACTION='I' AND ENTITY_SUB_TYPE='" + entSubType[i] + "' AND REC_STATUS<>'C' AND STAGE='DC' AND DEAL_ID='" + dealId + "'";
		            logger.info("External Entity sub type is BUSINESS or MARKET " + checkQuery);
		          }
		          else
		          {
		            checkQuery = "SELECT COUNT(1) FROM cr_deal_verification_dtl WHERE APPRAISER_TYPE ='EXTERNAL' AND EXTERNAL_APPRAISER='" + externalAppUserId[i] + "' AND VERIFICATION_ACTION='I' AND ENTITY_ID='" + entityId[i] + "' AND REC_STATUS<>'C' AND STAGE='DC' AND DEAL_ID='" + dealId + "'";
		          }

		          logger.info("checkQuery:  " + checkQuery);
		          String checkCount = ConnectionDAO.singleReturn(checkQuery);
		          logger.info("checkCount:  " + checkCount);
		          if (!CommonFunction.checkNull(checkCount).equalsIgnoreCase("0"))
		          {
		            request.setAttribute("dublicate", "dublicate");
		            return mapping.findForward("save");
		          }
		        }
		        else
		        {
		          request.setAttribute("blank", "blank");
		          return mapping.findForward("save");
		        }																																																																							
		      }

		      boolean status = filedDAO.insertFieldVerInitiation(vo);
		      if (status)										   
		      {
		        String completeState = "Y";
		        logger.info("entityId.length: " + entityId.length);
		        for (int i = 0; i < entityId.length; i++)
		        {
		          logger.info("Action: " + action[i]);
		          if ((action[i].equalsIgnoreCase("P")) || (action[i].equalsIgnoreCase("I")))
		          {
		            completeState = "N";
		          }
		        }
		        if (completeState.equalsIgnoreCase("Y"))
			      {
		          String checkStageM = CommonFunction.stageMovement(companyId, "DC", "F", dealId, "FVI", bDate, userId);
		          logger.info("checkStageM : " + checkStageM);
		          if (checkStageM.equalsIgnoreCase("S"))
		          {
		            vo.setDealId(dealId);
		            vo.setDecison("W");
		            vo.setTextarea("Waived  all");
		            status = filedDAO.authorAllWavedVerifInit(vo);
		          }

		          request.setAttribute("sms", "C");
		        }
		        else
		        {
		          request.setAttribute("sms", "S");
		        }
									 
		      }
		      else
		      {
		        request.setAttribute("sms", "E");
		      }

		    }
		 	saveToken(request);
		    return mapping.findForward("save");
		  }

	public ActionForward openFieldVerification(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
	
				logger.info(" in openFieldVerification......()");
				HttpSession session = request.getSession();
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				String userId="";
				String bDate="";
				String companyId="";
				if(userobj!=null)
				{
						userId=userobj.getUserId();
						bDate=userobj.getBusinessdate();
						companyId=""+userobj.getCompanyId();
						
						
				}else{
					logger.info("here in openFieldVerification method of FieldVerificationSaveAction action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
				Object sessionId = session.getAttribute("sessionID");
				boolean flag=false;
				
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
				    saveToken(request);
				    String dealId="";
				    if( session.getAttribute("dealId")!=null)
				    {
				    	dealId=session.getAttribute("dealId").toString();
				    }
				    else
				    {
				    	dealId=request.getParameter("dealId");
				    }
				    logger.info("dealId: "+dealId);
					if(session.getAttribute("functionId")!=null)
					{
						String functionId=session.getAttribute("functionId").toString();
						flag = LockRecordCheck.lockCheck(userId,functionId,dealId,context);
						logger.info("Flag ........................................ "+flag);
					}
			
					

				if(flag)
				{
					
					FieldVerificationVo vo=new FieldVerificationVo(); 
					CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
			        logger.info("Implementation class: "+service.getClass()); 			// changed by asesh
					//CreditProcessingDAO service = new CreditProcessingDAOImpl();
			        /*Changed by asesh*/
					FieldVerificationDAO filedDAO=(FieldVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(FieldVerificationDAO.IDENTITY);
			        logger.info("Implementation class: "+filedDAO.getClass()); 
					/*Changed by asesh*/
					DynaValidatorForm FieldVarificationSaveDynaValidatorForm= (DynaValidatorForm)form;
					org.apache.commons.beanutils.BeanUtils.copyProperties(vo, FieldVarificationSaveDynaValidatorForm);
					ArrayList dealHeader = service.getDealHeader(dealId);
					session.setAttribute("dealHeader", dealHeader);
					ArrayList verificationList = filedDAO.getVerificationList(dealId);
					logger.info("verificationList size: "+verificationList.size());
					boolean dealStatus=filedDAO.getDealStatus(dealId);
					if(verificationList.size()==0 && dealStatus)
					{
						 String checkStageM=CommonFunction.stageMovement(companyId, "DC","F",dealId, "FVI", bDate,userId);
						 logger.info("checkStageM: "+checkStageM);
					}
//					String initiatedCountQuery="SELECT COUNT(1) FROM cr_deal_verification_dtl WHERE REC_STATUS<>'C' AND VERIFICATION_ACTION='I' AND DEAL_ID='"+dealId+"'";
//					logger.info("initiatedCountQuery: "+initiatedCountQuery);
//					String initiatedCount=ConnectionDAO.singleReturn(initiatedCountQuery);
//					logger.info("initiatedCount: "+initiatedCount);
//					session.setAttribute("initiatedCount", initiatedCount);
					// Sanjog 
					
					String stage = request.getParameter("txntype");
					logger.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+stage);
					ArrayList uploadedDocList=new ArrayList();
					//uploadedDocList = service.getUploadDocForFVC(dealId,stage);
					request.setAttribute("uploadedDocList", uploadedDocList);
					session.setAttribute("verificationList", verificationList);
					session.setAttribute("dealId",dealId);
					return mapping.findForward("newFieldVerification");
				
					
				}else
				{
					logger.info("Record is Locked");			
					request.setAttribute("sms", "Locked");
					request.setAttribute("recordId", dealId);
					session.setAttribute("trackId", dealId);
					request.setAttribute("userId", userId);
					return mapping.findForward("locked");
				}
			}
	
	
	public ActionForward openTabVerification(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
	
				logger.info(" in openTabVerification......()");
				HttpSession session = request.getSession();
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				String userId="";
				
				if(userobj!=null)
				{
						userId=userobj.getUserId();
						
				}else{
					logger.info("here in openTabVerification method of FieldVerificationSaveAction action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
				Object sessionId = session.getAttribute("sessionID");
				boolean loginflag=false;
				boolean flag=false;
				
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
				 saveToken(request);
				String dealId="";
				if(session.getAttribute("dealId")!=null)
				{
					dealId=session.getAttribute("dealId").toString();
											
					FieldVerificationVo vo=new FieldVerificationVo(); 
					CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
			        logger.info("Implementation class: "+service.getClass()); 			// changed by asesh
			        /*Changed by asesh*/
					FieldVerificationDAO filedDAO=(FieldVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(FieldVerificationDAO.IDENTITY);
			        logger.info("Implementation class: "+filedDAO.getClass()); 
					/*Changed by asesh*/
					DynaValidatorForm FieldVarificationSaveDynaValidatorForm= (DynaValidatorForm)form;
					org.apache.commons.beanutils.BeanUtils.copyProperties(vo, FieldVarificationSaveDynaValidatorForm);
					ArrayList dealHeader = service.getDealHeader(dealId);
					session.setAttribute("dealHeader", dealHeader);
					ArrayList verificationList = filedDAO.getVerificationList(dealId);
					session.setAttribute("verificationList", verificationList);
				}	
					
					return mapping.findForward("openTabVerification");
				
									
			}
	
	public ActionForward showTabVerification(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
	
				logger.info(" in showTabVerification......()");
				HttpSession session = request.getSession();
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				String userId="";
				
				if(userobj!=null)
				{
						userId=userobj.getUserId();
						
				}else{
					logger.info("here in openTabVerification method of FieldVerificationSaveAction action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
				Object sessionId = session.getAttribute("sessionID");
				boolean loginflag=false;
				boolean flag=false;
				
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
				String dealId="";
				if(session.getAttribute("dealId")!=null)
				{
					dealId=session.getAttribute("dealId").toString();
											
					FieldVerificationVo vo=new FieldVerificationVo(); 
					CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
			        logger.info("Implementation class: "+service.getClass()); 			// changed by asesh
			        /*Changed by asesh*/
					FieldVerificationDAO filedDAO=(FieldVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(FieldVerificationDAO.IDENTITY);
			        logger.info("Implementation class: "+filedDAO.getClass()); 
					/*Changed by asesh*/
					DynaValidatorForm FieldVarificationSaveDynaValidatorForm= (DynaValidatorForm)form;
					org.apache.commons.beanutils.BeanUtils.copyProperties(vo, FieldVarificationSaveDynaValidatorForm);
					ArrayList dealHeader = service.getDealHeader(dealId);
					session.setAttribute("dealHeader", dealHeader);
					ArrayList showList = filedDAO.showVerificationList(dealId);
					session.setAttribute("showList", showList);
				}	
					
					return mapping.findForward("showVerification");
				
									
	}
	
	public ActionForward saveFieldVerInitiationAtCM(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		logger.info("Inside saveFieldVerInitiationAtCM......insertFieldVerInitiation");
		
		HttpSession session=request.getSession(false);
		
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		String userId="";
		String bDate="";
		String companyId="";
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
				companyId=""+userobj.getCompanyId();
				
		}else{
			logger.info("here in saveFieldVerInitiationAtCM method of FieldVerificationSaveAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}

		boolean flag=false;
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
		FieldVerificationVo vo = new FieldVerificationVo();
		DynaValidatorForm FieldVarificationCheckDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, FieldVarificationCheckDynaValidatorForm);	
		vo.setMakerId(userId);
		vo.setMakerDate(bDate);
	
		String loanId= CommonFunction.checkNull(session.getAttribute("loanId"));
			        
        logger.info("loanId is....................."+loanId);
        vo.setLbxLoanNoHID(loanId);
        String dealIdQuery="select LOAN_DEAL_ID from cr_loan_dtl where loan_id='"+loanId+"' limit 1";
        if(dbType.equalsIgnoreCase("MSSQL"))
        {
        	dealIdQuery="select top 1 LOAN_DEAL_ID from cr_loan_dtl where loan_id='"+loanId+"' ";
        }
        String dealId=ConnectionDAO.singleReturn(dealIdQuery);
        logger.info("dealIdQuery: "+dealIdQuery+" dealId: "+dealId);
        vo.setDealId(dealId);
        /*Changed by asesh*/
		FieldVerificationDAO filedDAO=(FieldVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(FieldVerificationDAO.IDENTITY);
        logger.info("Implementation class: "+filedDAO.getClass()); 
		/*Changed by asesh*/
		
		String[] action=vo.getVerificationAction();
		String[] entityId=vo.getEntityId();
		String[] externalAppUserId=vo.getExternalAppUserId();
		String[] internalAppUserId=vo.getInternalAppUserId();
		String[] appraiser=vo.getAppraiser();
		String[] entSubType=vo.getEntSubType();
		String checkQuery="";
		String checkInitiatedQuery="";
		logger.info("entityId.length: "+entityId.length);
		for (int i = 0; i < entityId.length; i++) {
			
			
			if(entSubType[i].equalsIgnoreCase("BUSINESS")||entSubType[i].equalsIgnoreCase("MARKET"))
			{
					checkInitiatedQuery="SELECT COUNT(1) FROM cr_deal_verification_dtl WHERE VERIFICATION_ACTION='I' AND ENTITY_SUB_TYPE='"+entSubType[i]+"' AND STAGE='LIM' AND LOAN_ID="+loanId;
			}
			else
			{
				   checkInitiatedQuery="SELECT COUNT(1) FROM cr_deal_verification_dtl WHERE VERIFICATION_ACTION='I' AND ENTITY_ID='"+entityId[i]+"' AND STAGE='LIM' AND LOAN_ID="+loanId;
			}
			
			logger.info("checkInitiatedQuery:  "+checkInitiatedQuery);
			String checkInitiatedCount=ConnectionDAO.singleReturn(checkInitiatedQuery);
			logger.info("checkInitiatedCount:  "+checkInitiatedCount);
			if(!CommonFunction.checkNull(checkInitiatedCount).equalsIgnoreCase("0") && CommonFunction.checkNull(action[i]).equalsIgnoreCase("W"))
			{
				request.setAttribute("alreadyInitiated", "alreadyInitiated");
				return mapping.findForward("save");
			}
			
			if(appraiser[i].equalsIgnoreCase("INTERNAL"))
			{
				if(entSubType[i].equalsIgnoreCase("BUSINESS")||entSubType[i].equalsIgnoreCase("MARKET"))
				{
					checkQuery="SELECT COUNT(1) FROM cr_deal_verification_dtl WHERE APPRAISER_TYPE ='INTERNAL' AND INTERNAL_APPRAISER ='"+internalAppUserId[i]+"' AND VERIFICATION_ACTION='I' AND ENTITY_SUB_TYPE='"+entSubType[i]+"' AND REC_STATUS<>'C' AND STAGE='LIM' AND LOAN_ID="+loanId;
					logger.info("Internal Entity sub type is BUSINESS or MARKET "+checkQuery);
				}
				else
				{
					checkQuery="SELECT COUNT(1) FROM cr_deal_verification_dtl WHERE APPRAISER_TYPE ='INTERNAL' AND INTERNAL_APPRAISER ='"+internalAppUserId[i]+"' AND VERIFICATION_ACTION='I' AND ENTITY_ID='"+entityId[i]+"' AND REC_STATUS<>'C' AND STAGE='LIM' AND LOAN_ID="+loanId;
				}
				 
			}
			else
			{
				if(entSubType[i].equalsIgnoreCase("BUSINESS")||entSubType[i].equalsIgnoreCase("MARKET"))
				{
					
					checkQuery="SELECT COUNT(1) FROM cr_deal_verification_dtl WHERE APPRAISER_TYPE ='EXTERNAL' AND EXTERNAL_APPRAISER='"+externalAppUserId[i]+"' AND VERIFICATION_ACTION='I' AND ENTITY_SUB_TYPE='"+entSubType[i]+"' AND REC_STATUS<>'C' AND STAGE='LIM' AND LOAN_ID="+loanId;
					logger.info("External Entity sub type is BUSINESS or MARKET "+checkQuery);
				}
				else
				{
					checkQuery="SELECT COUNT(1) FROM cr_deal_verification_dtl WHERE APPRAISER_TYPE ='EXTERNAL' AND EXTERNAL_APPRAISER='"+externalAppUserId[i]+"' AND VERIFICATION_ACTION='I' AND ENTITY_ID='"+entityId[i]+"' AND REC_STATUS<>'C' AND STAGE='LIM' AND LOAN_ID="+loanId;
				}
				 
			}
			
			logger.info("checkQuery:  "+checkQuery);
			String checkCount=ConnectionDAO.singleReturn(checkQuery);
			logger.info("checkCount:  "+checkCount);
			if(!CommonFunction.checkNull(checkCount).equalsIgnoreCase("0"))
			{
				request.setAttribute("dublicate", "dublicate");
				return mapping.findForward("save");
			}
		}
				
		boolean status = filedDAO.insertFieldVerInitiationAtCM(vo);
		

		if(status){
			
			String completeState="Y";
			logger.info("entityId.length: "+entityId.length);
			for (int i = 0; i < entityId.length; i++) {
				
				logger.info("Action: "+action[i]);
				if(action[i].equalsIgnoreCase("P")||action[i].equalsIgnoreCase("I"))
				{
					completeState="N";
				}
				
			}
			if(completeState.equalsIgnoreCase("Y"))
			{
					 // String checkStageM=CommonFunction.stageMovement(companyId, "DC","F",dealId, "FVI", bDate,userId);
					 // logger.info("checkStageM : "+checkStageM);
					 // if(checkStageM.equalsIgnoreCase("S")){
						  
						  vo.setLbxLoanNoHID(loanId);
						  vo.setDecison("W");
						  vo.setTextarea("Waived  all");
						  status = filedDAO.authorAllWavedVerifInitAtCM(vo);
						  
					  //}
					  
					  request.setAttribute("sms","C");
					
		    }
			else
			{
				request.setAttribute("sms","S");
			}
			
			
			
		}
		else{
			
			request.setAttribute("sms","E");
			
		}
		return mapping.findForward("save");
	}
	
	public ActionForward openLinkVerificationInitAtCm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
	
				logger.info(" in openLinkVerificationInitAtCm......()");
				HttpSession session = request.getSession();
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				String userId="";
				String bDate="";
				String companyId="";
				if(userobj!=null)
				{
						userId=userobj.getUserId();
						bDate=userobj.getBusinessdate();
						companyId=""+userobj.getCompanyId();
						
						
				}else{
					logger.info("here in openLinkVerificationInitAtCm method of FieldVerificationSaveAction action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
				Object sessionId = session.getAttribute("sessionID");
				boolean flag=false;
				
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
				
					String loanId="";
					loanId=CommonFunction.checkNull(request.getParameter("loanId"));
					if(CommonFunction.checkNull(loanId).trim().equalsIgnoreCase(""))
						loanId=(String)session.getAttribute("loanId");
					else
						session.removeAttribute("loanId");
					
				    logger.info("loanId: "+loanId);
					if(session.getAttribute("functionId")!=null)
					{
						String functionId=session.getAttribute("functionId").toString();
						flag = LockRecordCheck.lockCheck(userId,functionId,loanId,context);
						logger.info("Flag ........................................ "+flag);
					}
			
					

				if(flag)
				{
					
					FieldVerificationVo vo=new FieldVerificationVo(); 
					CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
			        logger.info("Implementation class: "+service.getClass()); 			// changed by asesh
					//CreditProcessingDAO service = new CreditProcessingDAOImpl();
			        /*Changed by asesh*/
					FieldVerificationDAO filedDAO=(FieldVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(FieldVerificationDAO.IDENTITY);
			        logger.info("Implementation class: "+filedDAO.getClass()); 
			        
			        LoanInitiationDAO dao=(LoanInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(LoanInitiationDAO.IDENTITY);
					logger.info("Implementation class: "+dao.getClass()); 
					/*Changed by asesh*/
					DynaValidatorForm FieldVarificationSaveDynaValidatorForm= (DynaValidatorForm)form;
					org.apache.commons.beanutils.BeanUtils.copyProperties(vo, FieldVarificationSaveDynaValidatorForm);
					ArrayList loanHeader = dao.getLoanHeader(loanId);
					session.setAttribute("loanHeader", loanHeader);
					ArrayList verificationList = filedDAO.getVerificationListAtCM(loanId);
					logger.info("verificationList size: "+verificationList.size());
					
					/*if(verificationList.size()==0)
					{
						 String checkStageM=CommonFunction.stageMovement(companyId, "DC","F",dealId, "FVI", bDate,userId);
						 logger.info("checkStageM: "+checkStageM);
					}*/
//					String initiatedCountQuery="SELECT COUNT(1) FROM cr_deal_verification_dtl WHERE REC_STATUS<>'C' AND VERIFICATION_ACTION='I' AND DEAL_ID='"+dealId+"'";
//					logger.info("initiatedCountQuery: "+initiatedCountQuery);
//					String initiatedCount=ConnectionDAO.singleReturn(initiatedCountQuery);
//					logger.info("initiatedCount: "+initiatedCount);
//					session.setAttribute("initiatedCount", initiatedCount);
					// Sanjog 
					
					String stage = request.getParameter("txntype");
					logger.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+stage);
					ArrayList uploadedDocList=new ArrayList();
					//uploadedDocList = service.getUploadDocForFVC(dealId,stage);
					request.setAttribute("uploadedDocList", uploadedDocList);
					session.setAttribute("verificationList", verificationList);
					session.setAttribute("loanId",loanId);
					return mapping.findForward("newFieldVerification");
				
					
				}else
				{
					logger.info("Record is Locked");			
					request.setAttribute("sms", "Locked");
					request.setAttribute("recordId", loanId);
					session.setAttribute("trackId", loanId);
					request.setAttribute("userId", userId);
					return mapping.findForward("locked");
				}
			}
	
	public ActionForward openTabVerificationAtCM(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
	
				logger.info(" in openTabVerificationAtCM......()");
				HttpSession session = request.getSession();
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				String userId="";
				
				if(userobj!=null)
				{
						userId=userobj.getUserId();
						
				}else{
					logger.info("here in openTabVerificationAtCM method of FieldVerificationSaveAction action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
				Object sessionId = session.getAttribute("sessionID");
				boolean loginflag=false;
				boolean flag=false;
				
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
				String loanId="";
				if(session.getAttribute("loanId")!=null)
				{
					loanId=session.getAttribute("loanId").toString();
											
					FieldVerificationVo vo=new FieldVerificationVo(); 
					CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
			        logger.info("Implementation class: "+service.getClass()); 			// changed by asesh
			        /*Changed by asesh*/
					FieldVerificationDAO filedDAO=(FieldVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(FieldVerificationDAO.IDENTITY);
			        logger.info("Implementation class: "+filedDAO.getClass()); 
					/*Changed by asesh*/
					DynaValidatorForm FieldVarificationSaveDynaValidatorForm= (DynaValidatorForm)form;
					org.apache.commons.beanutils.BeanUtils.copyProperties(vo, FieldVarificationSaveDynaValidatorForm);
					//ArrayList dealHeader = service.getDealHeader(loanId);
					//session.setAttribute("dealHeader", dealHeader);
					
					ArrayList verificationList = filedDAO.getVerificationListAtCM(loanId);
					session.setAttribute("verificationList", verificationList);
				}	
					
					return mapping.findForward("openTabVerification");
				
									
			}
	
	public ActionForward showTabVerificationAtCM(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
	
				logger.info(" in showTabVerificationAtCM......()");
				HttpSession session = request.getSession();
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				String userId="";
				
				if(userobj!=null)
				{
						userId=userobj.getUserId();
						
				}else{
					logger.info("here in showTabVerificationAtCM method of FieldVerificationSaveAction action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
				Object sessionId = session.getAttribute("sessionID");
				boolean loginflag=false;
				boolean flag=false;
				
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
				String loanId="";
				if(session.getAttribute("loanId")!=null)
				{
					loanId=session.getAttribute("loanId").toString();
											
					FieldVerificationVo vo=new FieldVerificationVo(); 
					CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
			        logger.info("Implementation class: "+service.getClass()); 			// changed by asesh
			        /*Changed by asesh*/
					FieldVerificationDAO filedDAO=(FieldVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(FieldVerificationDAO.IDENTITY);
			        logger.info("Implementation class: "+filedDAO.getClass()); 
					/*Changed by asesh*/
					DynaValidatorForm FieldVarificationSaveDynaValidatorForm= (DynaValidatorForm)form;
					org.apache.commons.beanutils.BeanUtils.copyProperties(vo, FieldVarificationSaveDynaValidatorForm);
					//ArrayList dealHeader = service.getDealHeader(dealId);
					//session.setAttribute("dealHeader", dealHeader);
					ArrayList showList = filedDAO.showVerificationListAtCM(loanId);
					session.setAttribute("showList", showList);
				}	
					
					return mapping.findForward("showVerification");
				
									
	}
	
	public ActionForward openVerificationCompletionModule(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
	
				logger.info(" in openVerificationCompletionModule......()");
				HttpSession session = request.getSession();
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				String userId="";
				
				if(userobj!=null)
				{
						userId=userobj.getUserId();
						
				}else{
					logger.info("here in openVerificationCompletionModule method of FieldVerificationSaveAction action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
				Object sessionId = session.getAttribute("sessionID");
				boolean loginflag=false;
				boolean flag=false;
				
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
				String dealId="";
				if(request.getParameter("dealId")!=null)
				{
					dealId=request.getParameter("dealId");
					 logger.info("In dealId: "+dealId); 						
					FieldVerificationVo vo=new FieldVerificationVo(); 
					CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
			        logger.info("Implementation class: "+service.getClass()); 			// changed by asesh
			        /*Changed by asesh*/
					FieldVerificationDAO filedDAO=(FieldVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(FieldVerificationDAO.IDENTITY);
			        logger.info("Implementation class: "+filedDAO.getClass()); 
					/*Changed by asesh*/
					DynaValidatorForm FieldVarificationSaveDynaValidatorForm= (DynaValidatorForm)form;
					org.apache.commons.beanutils.BeanUtils.copyProperties(vo, FieldVarificationSaveDynaValidatorForm);
					ArrayList dealHeader = service.getDealHeader(dealId);
					session.setAttribute("dealHeader", dealHeader);
					ArrayList verificationList = filedDAO.getVerificationList(dealId);
					session.setAttribute("verificationList", verificationList);
					session.setAttribute("dealId", dealId);
				}	
					
					return mapping.findForward("openVerificationCompletionModule");
				
									
			}
	
	public ActionForward saveVarifComplFieldVerification(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
		    throws Exception
		  {
		    logger.info("Inside saveVarifComplFieldVerification......");

		    HttpSession session = request.getSession(false);

		    UserObject userobj = (UserObject)session.getAttribute("userobject");

		    String userId = null;
		    String bDate = null;
		    String companyId = null;
		    if (userobj != null)
		    {
		      userId = userobj.getUserId();
		      bDate = userobj.getBusinessdate();
		      companyId = "" + userobj.getCompanyId();
		    }
		    else {
		      logger.info("here in saveVarifComplFieldVerification method of FieldVerificationSaveAction action the session is out----------------");
		      return mapping.findForward("sessionOut");
		    }

		    boolean flag = false;
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
		    FieldVerificationVo vo = new FieldVerificationVo();
		    DynaValidatorForm FieldVarificationCheckDynaValidatorForm = (DynaValidatorForm)form;
		    BeanUtils.copyProperties(vo, FieldVarificationCheckDynaValidatorForm);
		    vo.setMakerId(userId);
		    vo.setMakerDate(bDate);

		    String dealId = CommonFunction.checkNull(session.getAttribute("dealId"));

		    logger.info("deal id is....................." + dealId);
		    vo.setDealId(dealId);

		    FieldVerificationDAO filedDAO = (FieldVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance("FV");
		    logger.info("Implementation class: " + filedDAO.getClass());

		    String[] action = vo.getVerificationAction();
		    String[] entityId = vo.getEntityId();
		    String[] externalAppUserId = vo.getExternalAppUserId();
		    String[] internalAppUserId = vo.getInternalAppUserId();
		    String[] appraiser = vo.getAppraiser();
		    String[] entSubType = vo.getEntSubType();
		    String checkQuery = "";
		    String validateApprisalName = null;
		    String checkInitiatedQuery = "";
		    logger.info("entityId.length: " + entityId.length);
		    for (int i = 0; i < entityId.length; i++) {
		      if (CommonFunction.checkNull(action[i]).equalsIgnoreCase("I")) {
		        if ((CommonFunction.checkNull(appraiser[i]).equalsIgnoreCase("INTERNAL")) && (CommonFunction.checkNull(internalAppUserId[i]).equalsIgnoreCase(""))) {
		          validateApprisalName = "blank";
		        }
		        if ((CommonFunction.checkNull(appraiser[i]).equalsIgnoreCase("EXTERNAL")) && (CommonFunction.checkNull(externalAppUserId[i]).equalsIgnoreCase(""))) {
		          validateApprisalName = "blank";
		        }
		      }

		      if (CommonFunction.checkNull(validateApprisalName).equalsIgnoreCase(""))
		      {
		        if ((entSubType[i].equalsIgnoreCase("BUSINESS")) || (entSubType[i].equalsIgnoreCase("MARKET")))
		        {
		          checkInitiatedQuery = "SELECT COUNT(1) FROM cr_deal_verification_dtl WHERE VERIFICATION_ACTION='I' AND ENTITY_SUB_TYPE='" + entSubType[i] + "' AND REC_STATUS<>'C' AND STAGE='DC' AND DEAL_ID='" + dealId + "'";
		        }
		        else
		        {
		          checkInitiatedQuery = "SELECT COUNT(1) FROM cr_deal_verification_dtl WHERE VERIFICATION_ACTION='I' AND ENTITY_ID='" + entityId[i] + "' AND REC_STATUS<>'C' AND STAGE='DC' AND DEAL_ID='" + dealId + "'";
		        }

		        logger.info("checkInitiatedQuery:  " + checkInitiatedQuery);
		        String checkInitiatedCount = ConnectionDAO.singleReturn(checkInitiatedQuery);

		        if ((!CommonFunction.checkNull(checkInitiatedCount).equalsIgnoreCase("0")) && (CommonFunction.checkNull(action[i]).equalsIgnoreCase("W")))
		        {
		          checkInitiatedCount = null;
		          request.setAttribute("alreadyInitiated", "alreadyInitiated");
		          return mapping.findForward("save");
		        }

		        if (appraiser[i].equalsIgnoreCase("INTERNAL"))
			   
																											   
		        {
		          if ((entSubType[i].equalsIgnoreCase("BUSINESS")) || (entSubType[i].equalsIgnoreCase("MARKET")))
		          {
		            checkQuery = "SELECT COUNT(1) FROM cr_deal_verification_dtl WHERE APPRAISER_TYPE ='INTERNAL' AND INTERNAL_APPRAISER ='" + internalAppUserId[i] + "' AND VERIFICATION_ACTION='I' AND ENTITY_SUB_TYPE='" + entSubType[i] + "' AND REC_STATUS<>'C' AND STAGE='DC' AND DEAL_ID='" + dealId + "'";
		          }
		          else
		          {
		            checkQuery = "SELECT COUNT(1) FROM cr_deal_verification_dtl WHERE APPRAISER_TYPE ='INTERNAL' AND INTERNAL_APPRAISER ='" + internalAppUserId[i] + "' AND VERIFICATION_ACTION='I' AND ENTITY_ID='" + entityId[i] + "' AND REC_STATUS<>'C' AND STAGE='DC' AND DEAL_ID='" + dealId + "'";
		          }

		        }
		        else if ((entSubType[i].equalsIgnoreCase("BUSINESS")) || (entSubType[i].equalsIgnoreCase("MARKET")))
		        {
		          checkQuery = "SELECT COUNT(1) FROM cr_deal_verification_dtl WHERE APPRAISER_TYPE ='EXTERNAL' AND EXTERNAL_APPRAISER='" + externalAppUserId[i] + "' AND VERIFICATION_ACTION='I' AND ENTITY_SUB_TYPE='" + entSubType[i] + "' AND REC_STATUS<>'C' AND STAGE='DC' AND DEAL_ID='" + dealId + "'";
		        }
		        else
		        {
		          checkQuery = "SELECT COUNT(1) FROM cr_deal_verification_dtl WHERE APPRAISER_TYPE ='EXTERNAL' AND EXTERNAL_APPRAISER='" + externalAppUserId[i] + "' AND VERIFICATION_ACTION='I' AND ENTITY_ID='" + entityId[i] + "' AND REC_STATUS<>'C' AND STAGE='DC' AND DEAL_ID='" + dealId + "'";
		        }

		        logger.info("checkQuery:  " + checkQuery);
		        String checkCount = ConnectionDAO.singleReturn(checkQuery);

		        if (!CommonFunction.checkNull(checkCount).equalsIgnoreCase("0"))
		        {
		          checkCount = null;
		          request.setAttribute("dublicate", "dublicate");
		          return mapping.findForward("save");
		        }
		      }
		      else
		      {  
								  
		        request.setAttribute("blank", "blank");
		        return mapping.findForward("save");
		      }
		    }

		    boolean status = filedDAO.insertVerCompletionModule(vo);

		    if (status)
		    {
		      request.setAttribute("sms", "S");
		    }
		    else
		    {
		      request.setAttribute("sms", "E");
		    }

		    logger.info("sms : " + request.getAttribute("sms"));
		    checkQuery = null;
		    checkInitiatedQuery = null;
		    action = null;
		    entityId = null;
		    externalAppUserId = null;
		    internalAppUserId = null;
		    appraiser = null;
		    entSubType = null;
		    vo = null;
		    userId = null;
		    bDate = null;
		    companyId = null;
		    FieldVarificationCheckDynaValidatorForm.reset(mapping, request);
		    return mapping.findForward("save");
		  }
	

	public ActionForward openTabVerifCompletionModule(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
	
				logger.info(" in openTabVerifCompletionModule......()");
				HttpSession session = request.getSession();
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				String userId="";
				
				if(userobj!=null)
				{
						userId=userobj.getUserId();
						
				}else{
					logger.info("here in openTabVerifCompletionModule method of FieldVerificationSaveAction action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
				Object sessionId = session.getAttribute("sessionID");
				boolean loginflag=false;
				boolean flag=false;
				
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
				String dealId="";
				if(request.getParameter("dealId")!=null)
				{
					dealId=request.getParameter("dealId");
					 logger.info("In dealId: "+dealId); 						
					FieldVerificationVo vo=new FieldVerificationVo(); 
					CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
			        logger.info("Implementation class: "+service.getClass()); 			// changed by asesh
			        /*Changed by asesh*/
					FieldVerificationDAO filedDAO=(FieldVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(FieldVerificationDAO.IDENTITY);
			        logger.info("Implementation class: "+filedDAO.getClass()); 
					/*Changed by asesh*/
					DynaValidatorForm FieldVarificationSaveDynaValidatorForm= (DynaValidatorForm)form;
					org.apache.commons.beanutils.BeanUtils.copyProperties(vo, FieldVarificationSaveDynaValidatorForm);
					ArrayList dealHeader = service.getDealHeader(dealId);
					session.setAttribute("dealHeader", dealHeader);
					ArrayList verificationList = filedDAO.getVerificationList(dealId);
					session.setAttribute("verificationList", verificationList);
					session.setAttribute("dealId", dealId);
				}	
					
					return mapping.findForward("openTabVerifCompletionModule");
				
									
			}
	
	public ActionForward showTabVerificationCompletionModule(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
	
				logger.info(" in showTabVerificationCompletionModule......()");
				HttpSession session = request.getSession();
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				String userId="";
				
				if(userobj!=null)
				{
						userId=userobj.getUserId();
						
				}else{
					logger.info("here in showTabVerificationCompletionModule method of FieldVerificationSaveAction action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
				Object sessionId = session.getAttribute("sessionID");
				boolean loginflag=false;
				boolean flag=false;
				
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
				String dealId="";
				if(session.getAttribute("dealId")!=null)
				{
					dealId=session.getAttribute("dealId").toString();
											
					FieldVerificationVo vo=new FieldVerificationVo(); 
					CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
			        logger.info("Implementation class: "+service.getClass()); 			// changed by asesh
			        /*Changed by asesh*/
					FieldVerificationDAO filedDAO=(FieldVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(FieldVerificationDAO.IDENTITY);
			        logger.info("Implementation class: "+filedDAO.getClass()); 
					/*Changed by asesh*/
					DynaValidatorForm FieldVarificationSaveDynaValidatorForm= (DynaValidatorForm)form;
					org.apache.commons.beanutils.BeanUtils.copyProperties(vo, FieldVarificationSaveDynaValidatorForm);
					ArrayList dealHeader = service.getDealHeader(dealId);
					session.setAttribute("dealHeader", dealHeader);
					ArrayList showList = filedDAO.showVerificationListCompletionModule(dealId);
					session.setAttribute("showList", showList);
				}	
					
					return mapping.findForward("showTabVerificationCompletionModule");
				
									
	}
	
	public ActionForward forwardVarifComplFieldVerification(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		logger.info("Inside forwardVarifComplFieldVerification......");
		
		HttpSession session=request.getSession(false);
		
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		String userId=null;
		String bDate=null;
		String companyId=null;
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
				companyId=""+userobj.getCompanyId();
				
		}else{
			logger.info("here in forwardVarifComplFieldVerification method of FieldVerificationSaveAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}

		boolean flag=false;
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
		FieldVerificationVo vo = new FieldVerificationVo();
		DynaValidatorForm FieldVarificationCheckDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, FieldVarificationCheckDynaValidatorForm);	
		vo.setMakerId(userId);
		vo.setMakerDate(bDate);
	
		String dealId= CommonFunction.checkNull(session.getAttribute("dealId"));
			        
        logger.info("deal id is....................."+dealId);
        vo.setDealId(dealId);
        /*Changed by asesh*/
		FieldVerificationDAO filedDAO=(FieldVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(FieldVerificationDAO.IDENTITY);
        logger.info("Implementation class: "+filedDAO.getClass()); 
		/*Changed by asesh*/
		
	
		String[] entityId=vo.getEntityId();
		
		logger.info("entityId.length: "+entityId.length);
		boolean status=false;
			
					  String checkStageM=CommonFunction.stageMovement(companyId, "DC","F",dealId, "FVI", bDate,userId);
					  logger.info("checkStageM : "+checkStageM);
					  if(checkStageM.equalsIgnoreCase("S")){
						  
						  vo.setDealId(dealId);
						  vo.setTextarea("Completed From Verification Module");
						  status = filedDAO.verifCompletionModule(vo);
						  if(status)
						  {
							  request.setAttribute("sms","FS");
						  }
						  else
						  {
							  request.setAttribute("sms","NF");
						  }
						  
					  }
					  else
					  {
						  request.setAttribute("sms","N");
						  request.setAttribute("checkStageM",checkStageM);
					  }
					 
		 logger.info("sms : "+request.getAttribute("sms"));
		
		 entityId=null;
		 vo=null;
		 userId=null;
		 bDate=null;
		 companyId=null;
		 FieldVarificationCheckDynaValidatorForm.reset(mapping, request);
		return mapping.findForward("forwardCompletion");
	}
	
	
}

