package com.cp.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

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
import com.cp.vo.CollateralVo;
import com.cp.vo.FieldVerificationVo;
import com.gcd.VO.ShowCustomerDetailVo;
import com.gcd.dao.CorporateDAO;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.communication.engn.daoImplMySql.SmsDAOImpl;


public class VerificationCapturingDispatchAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(VerificationCapturingDispatchAction.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dbType=resource.getString("lbl.dbType");
		
	public ActionForward openComPopup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
				logger.info(" in openComPopup......()");
				HttpSession session = request.getSession();
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				if(userobj==null){
					logger.info("here in openComPopup method of fieldVerificationDispatchAction action the session is out----------------");
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
				//code added by neeraj
				String source="NE";
				String module="GCD";
				String functionId=(String)session.getAttribute("functionId");
				int funid=Integer.parseInt(functionId);		
				if(funid==4000122 || funid==4000123)
					source="ED";
				else
					module="DEAL";
				//neeraj space end
								
				String dealId = "";
				if (session.getAttribute("dealId") != null) {

					dealId = session.getAttribute("dealId").toString();
				} else if (session.getAttribute("maxId") != null) {
					dealId = session.getAttribute("maxId").toString();
				}
				String entityId=CommonFunction.checkNull(request.getParameter("entityId"));
				String verificationId=CommonFunction.checkNull(request.getParameter("verificationId"));
				
				String verificationTypeQuery="Select VERIFICATION_TYPE from cr_deal_verification_dtl where VERIFICATION_ID='"+verificationId+"'";
				logger.info("verificationTypeQuery: "+verificationTypeQuery);
				String verificationType=ConnectionDAO.singleReturn(verificationTypeQuery);
				logger.info("verificationType: "+verificationType);
				
				String verificationSubTypeQuery="Select VERIFICATION_SUBTYPE from cr_deal_verification_dtl where VERIFICATION_ID='"+verificationId+"'";
				logger.info("verificationSubTypeQuery: "+verificationSubTypeQuery);
				String verificationSubType=ConnectionDAO.singleReturn(verificationSubTypeQuery);
				logger.info("verificationSubType: "+verificationSubType);
				String entityTypeQuery="Select ENTITY_TYPE from cr_deal_verification_dtl where VERIFICATION_ID='"+verificationId+"'";
				logger.info("entityTypeQuery: "+entityTypeQuery);
				String entityType=ConnectionDAO.singleReturn(entityTypeQuery);
				logger.info("entityType: "+entityType);
				
				String entitySubTypeQuery="Select ENTITY_SUB_TYPE from cr_deal_verification_dtl where VERIFICATION_ID='"+verificationId+"'";
				logger.info("entitySubTypeQuery: "+entitySubTypeQuery);
				String entitySubType=ConnectionDAO.singleReturn(entitySubTypeQuery);
				logger.info("entitySubType: "+entitySubType);
				
				
				CorporateDAO fieldDao=(CorporateDAO)DaoImplInstanceFactory.getDaoImplInstance(CorporateDAO.IDENTITY);
				logger.info("Implementation class: "+fieldDao.getClass());
				CreditProcessingDAO details=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
		        logger.info("Implementation class: "+details.getClass()); 	//changed by asesh
		        
		        FieldVerificationDAO verifDao=(FieldVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(FieldVerificationDAO.IDENTITY);
		        logger.info("Implementation class: "+details.getClass()); 
		        
				//CreditProcessingDAO details = new CreditProcessingDAOImpl();
				if(CommonFunction.checkNull(verificationType).equalsIgnoreCase("CONTACT VERIFICATION") && (CommonFunction.checkNull(entityType).equalsIgnoreCase("PRAPPL")||CommonFunction.checkNull(entityType).equalsIgnoreCase("COAPPL")||CommonFunction.checkNull(entityType).equalsIgnoreCase("GUARANTOR"))||(CommonFunction.checkNull(verificationType).equalsIgnoreCase("TVR")))
				{
					ArrayList addrList=fieldDao.getAddressList();
					ArrayList addressList=fieldDao.copyAddress(entityId,module);
					request.setAttribute("approve", "approve");
					request.setAttribute("verificationType", "verificationType");
					request.setAttribute("customerList", addressList);
					request.setAttribute("addrList", addrList);
					return mapping.findForward("openAddress");	
				}
				else if(CommonFunction.checkNull(entityType).equalsIgnoreCase("PROPERTY"))
				{
					logger.info("I am in property");
					 ArrayList<Object> propertyOwnerList=null;
					 ArrayList<Object> fetchCollateralDetails = details.fetchCollateralDetailsAll(entityId,"PROPERTY",source);
			    	 request.setAttribute("fetchCollateralDetails", fetchCollateralDetails);
			    	 ArrayList showPropertyStatusDetails = details.getPropertyStatus();
					 ArrayList showPropertyTitleDetails = details.getPropertytTitle();
					 request.setAttribute("propertyTitle",showPropertyTitleDetails);
					 CollateralVo ownerList =(CollateralVo)fetchCollateralDetails.get(0);
					 String propertyOwner=ownerList.getPropertyOwner();
					 if(propertyOwner!=null && !propertyOwner.equalsIgnoreCase(""))
					 {
					 ownerList.setPropertyOwner(propertyOwner);
					 propertyOwner="'"+propertyOwner.replace("|","','")+"'";
					 
					 propertyOwnerList=details.getPropertyOwnerForDeal(propertyOwner,dealId);
					 }
					 request.setAttribute("dealAsset","dealAsset");
					 request.setAttribute("propertyOwnerList",propertyOwnerList);
					 request.setAttribute("propertyStatus",showPropertyStatusDetails);
			    	 request.setAttribute("viewDeal", "viewDeal");
					return mapping.findForward("openProperty");	
				}
				else if(CommonFunction.checkNull(entityType).equalsIgnoreCase("MACHINE"))
				{
					 ArrayList<Object> fetchCollateralDetails = details.fetchCollateralDetailsAll(entityId,"MACHINE",source);
			    	 request.setAttribute("fetchCollateralDetails", fetchCollateralDetails);
			    	 request.setAttribute("viewDeal", "viewDeal");
				
					return mapping.findForward("openMachine");	
				}
				else if(CommonFunction.checkNull(entityType).equalsIgnoreCase("VEHICLE"))
				{
					 ArrayList<Object> fetchCollateralDetails = details.fetchCollateralDetailsAll(entityId,"VEHICLE",source);
			    	 request.setAttribute("fetchCollateralDetails", fetchCollateralDetails);
			    	 request.setAttribute("viewDeal", "viewDeal");
					return mapping.findForward("openVehicle");	
				}
				else if(CommonFunction.checkNull(entityType).equalsIgnoreCase("OTHERS"))
				{
					logger.info("entityType: "+entityType);
					 ArrayList<Object> fetchCollateralDetails = details.fetchCollateralDetailsAll(entityId,"OTHERS",source);
			    	 request.setAttribute("fetchCollateralDetails", fetchCollateralDetails);
			    	 request.setAttribute("viewDeal", "viewDeal");
					return mapping.findForward("openOthers");	
				}
				else if(CommonFunction.checkNull(entityType).equalsIgnoreCase("BG"))
				{
					 ArrayList<Object> fetchCollateralDetails = details.fetchCollateralDetailsAll(entityId,"BG",source);
			    	 request.setAttribute("fetchCollateralDetails", fetchCollateralDetails);
			    	 request.setAttribute("viewDeal", "viewDeal");
					return mapping.findForward("openBg");	
				}
				else if(CommonFunction.checkNull(entityType).equalsIgnoreCase("DEBTOR"))
				{
					 ArrayList<Object> fetchCollateralDetails = details.fetchCollateralDetailsAll(entityId,"DEBTOR",source);
			    	 request.setAttribute("fetchCollateralDetails", fetchCollateralDetails);
			    	 request.setAttribute("viewDeal", "viewDeal");
					return mapping.findForward("openDebtor");	
				}
				else if(CommonFunction.checkNull(entityType).equalsIgnoreCase("FD"))
				{
					 ArrayList<Object> fetchCollateralDetails = details.fetchCollateralDetailsAll(entityId,"FD",source);
			    	 request.setAttribute("fetchCollateralDetails", fetchCollateralDetails);
			    	 request.setAttribute("viewDeal", "viewDeal");
					return mapping.findForward("openFd");	
				}
				else if(CommonFunction.checkNull(entityType).equalsIgnoreCase("SBLC"))
				{
					 ArrayList<Object> fetchCollateralDetails = details.fetchCollateralDetailsAll(entityId,"SBLC",source);
			    	 request.setAttribute("fetchCollateralDetails", fetchCollateralDetails);
			    	 request.setAttribute("viewDeal", "viewDeal");
					return mapping.findForward("openSblc");	
				}
				else if(CommonFunction.checkNull(entityType).equalsIgnoreCase("SECURITIES"))
				{
					 ArrayList<Object> fetchCollateralDetails = details.fetchCollateralDetailsAll(entityId,"SECURITIES",source);
			    	 request.setAttribute("fetchCollateralDetails", fetchCollateralDetails);
			    	 request.setAttribute("viewDeal", "viewDeal");
					return mapping.findForward("openSecurity");	
				}
				else if(CommonFunction.checkNull(entityType).equalsIgnoreCase("STOCK"))
				{
					 ArrayList<Object> fetchCollateralDetails = details.fetchCollateralDetailsAll(entityId,"STOCK",source);
			    	 request.setAttribute("fetchCollateralDetails", fetchCollateralDetails);
			    	 request.setAttribute("viewDeal", "viewDeal");
					return mapping.findForward("openStock");	
				}
				
				else if(CommonFunction.checkNull(entityType).equalsIgnoreCase("BUYER"))
				{
					
					ArrayList<Object> buyerList = details.modifyBuyerDetailsAll("B", entityId);
					request.setAttribute("buyerList", buyerList);
					request.setAttribute("viewDeal", "viewDeal");
					request.setAttribute("verificationType", "verificationType");
					return mapping.findForward("openBuyer");	
				}
				else if(CommonFunction.checkNull(entityType).equalsIgnoreCase("SUPPLIER"))
				{
					
					
					ArrayList<Object> supplierList = details.modifySupplierDetailsAll("S", entityId);
					request.setAttribute("supplierList", supplierList);
					request.setAttribute("viewDeal", "viewDeal");
					request.setAttribute("verificationType", "verificationType");
					return mapping.findForward("openSupplier");	
				}
				else if(CommonFunction.checkNull(entityType).equalsIgnoreCase("INCOME"))
				{
					
					CorporateDAO service=(CorporateDAO)DaoImplInstanceFactory.getDaoImplInstance(CorporateDAO.IDENTITY);
					logger.info("Implementation class: "+service.getClass());
					ArrayList<Object> individualInfo = service.getIndividualDetails(entityId,"fromLeads","","","updateFlag","",source);
					request.setAttribute("individualInfo", individualInfo);
					if(individualInfo.size()>0)
					{
						ShowCustomerDetailVo show=(ShowCustomerDetailVo)individualInfo.get(0);
						
						
							logger.info("other Relationship : "+show.getOtherRelationShip());
							request.setAttribute("otherRelationShip", show.getOtherRelationShip());
						
				
					}
					ArrayList<Object> individualCategorylist=service.getCustomerCategoryList();
					request.setAttribute("individualCategory",individualCategorylist);
					ArrayList<Object> CastCategory=service.getCastCategoryList();
					request.setAttribute("CastCategory",CastCategory);
					
					ArrayList<Object> genderIndiv=service.getGenderList();
					request.setAttribute("GenderCategory",genderIndiv);
					
					ArrayList<Object> constitutionlist=service.getIndividualContitutionList();
					request.setAttribute("constitutionlist",constitutionlist);
					request.setAttribute("viewDeal", "viewDeal");
					request.setAttribute("pageStatuss", "pageStatuss");
					request.setAttribute("approve", "approve");
					return mapping.findForward("openIndivCustomer");	
				}
				else if(CommonFunction.checkNull(entityType).equalsIgnoreCase("INSURANCE"))
				{
					 ArrayList<Object> fetchCollateralDetails = details.fetchCollateralDetailsAll(entityId,"INSURANCE",source);
					 ArrayList showInsuranceRelWithNominee = details.showInsuranceRelWithNominee();
					 request.setAttribute("showInsuranceRelWithNominee",showInsuranceRelWithNominee);
			    	 request.setAttribute("fetchCollateralDetails", fetchCollateralDetails);
			    	 request.setAttribute("viewDeal", "viewDeal");
					return mapping.findForward("openInsurance");	
				}
				else if(CommonFunction.checkNull(verificationType).equalsIgnoreCase("DOCUMENT VERIFICATION"))
				{
					 ArrayList<Object> fetchChildDocsDetails = verifDao.fetchChildDocs(entityId,dealId,"DC");
			    	 request.setAttribute("fetchChildDocsDetails", fetchChildDocsDetails);
			    	 request.setAttribute("viewDeal", "viewDeal");
					 return mapping.findForward("openChildDocs");	
				}
				if(CommonFunction.checkNull(verificationType).equalsIgnoreCase("CONSTITUTION VERIFICATION") && CommonFunction.checkNull(verificationSubType).equalsIgnoreCase("INDIVIDUAL CONSTITUTION") && (CommonFunction.checkNull(entityType).equalsIgnoreCase("PRAPPL")||CommonFunction.checkNull(entityType).equalsIgnoreCase("COAPPL")||CommonFunction.checkNull(entityType).equalsIgnoreCase("GUARANTOR")))
				{
					CorporateDAO corporateDao=(CorporateDAO)DaoImplInstanceFactory.getDaoImplInstance(CorporateDAO.IDENTITY);
					logger.info("Implementation class: "+corporateDao.getClass());
					ArrayList<Object> individualCategorylist=corporateDao.getCustomerCategoryList();
					request.setAttribute("individualCategory",individualCategorylist);
					ArrayList<Object> CastCategory=corporateDao.getCastCategoryList();
					request.setAttribute("CastCategory",CastCategory);
					
					ArrayList<Object> eduDetail=corporateDao.getEduDetail();
					request.setAttribute("eduDetail",eduDetail);
					
					ArrayList<Object> genderIndiv=corporateDao.getGenderList();
					request.setAttribute("GenderCategory",genderIndiv);
					
					ArrayList<Object> constitutionlist=corporateDao.getIndividualContitutionList();
					session.setAttribute("constitutionlist",constitutionlist);
					ArrayList<Object> individualInfo = corporateDao.getIndividualDetails(entityId,"","","","updateFlag","fromLeads","");
					request.setAttribute("individualInfo",individualInfo);	
					request.setAttribute("approve","approve");	
					request.setAttribute("underWriter","underWriter");	
					return mapping.findForward("openIndividualCustomer");	
				}
				if(CommonFunction.checkNull(verificationType).equalsIgnoreCase("CONSTITUTION VERIFICATION") && CommonFunction.checkNull(verificationSubType).equalsIgnoreCase("CORPORATE CONSTITUTION") && (CommonFunction.checkNull(entityType).equalsIgnoreCase("PRAPPL")||CommonFunction.checkNull(entityType).equalsIgnoreCase("COAPPL")||CommonFunction.checkNull(entityType).equalsIgnoreCase("GUARANTOR")))
				{
					CorporateDAO corporateDao=(CorporateDAO)DaoImplInstanceFactory.getDaoImplInstance(CorporateDAO.IDENTITY);
					logger.info("Implementation class: "+corporateDao.getClass());
					ArrayList<Object> customerCategorylist=corporateDao.getCustomerCategoryList();
					ArrayList<Object> constitutionlist=corporateDao.getContitutionList();
					ArrayList<Object> registrationTypeList=corporateDao.getRegistrationTypeList();
					ArrayList<Object> industryList = corporateDao.getIndustryList();
					ArrayList<Object> businessSegmentList = corporateDao.getBusinessSegmentList();
					ArrayList<Object> natureOfBusList = corporateDao.getNatureOfBusinessList();
					request.setAttribute("industryList",industryList);
					request.setAttribute("customerCategory",customerCategorylist);
					request.setAttribute("constitutionlist",constitutionlist);
					request.setAttribute("registrationTypeList",registrationTypeList);
					request.setAttribute("businessSegmentList",businessSegmentList);
					request.setAttribute("natureOfBusList",natureOfBusList);
					int custId=0;
					if(!CommonFunction.checkNull(entityId).equalsIgnoreCase(""))
					{
						custId=Integer.parseInt(entityId);
					}
					ArrayList<Object> detailList = corporateDao.getCorporateDetailAll(custId,"","","","updateFlag","fromLeads","");
					request.setAttribute("detailList",detailList);	
					request.setAttribute("approve","approve");	
					request.setAttribute("underWriter","underWriter");	
					
			    	return mapping.findForward("openCorporateCustomer");	
				}
				else 
				{
					
					return mapping.findForward("openPopupDetailCapturing");	
				}
				

									
	}

	public ActionForward saveFieldVarCap(ActionMapping mapping, ActionForm form,
					HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		logger.info(" in saveFieldVarCap......()");	
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
		String userId="";
		String businessDate="";
		if(userobj !=null)
		{
			userId=userobj.getUserId();
			businessDate =userobj.getBusinessdate();
		}else{
			logger.info("here in saveFieldVarCap method of fieldVerificationDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		String dealId=CommonFunction.checkNull(session.getAttribute("dealId"));
		FieldVerificationVo vo=new FieldVerificationVo(); 		
		DynaValidatorForm formbeen= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, formbeen);

		FieldVerificationDAO fieldDao=(FieldVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(FieldVerificationDAO.IDENTITY);
        logger.info("Implementation class: "+fieldDao.getClass()); 	// changed by asesh
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
		String verificationId="";
		if(session.getAttribute("verificationId")!=null)
		{
			verificationId=CommonFunction.checkNull(session.getAttribute("verificationId"));
		}
		session.removeAttribute("viewDeal");
		logger.info("verificationId: "+verificationId);
		vo.setDealId(dealId);
		vo.setMakerId(userId);
		vo.setMakerDate(businessDate);
		vo.setVerificationStatus("P");
		vo.setVerificationId(verificationId);
		boolean status=false;
		int fieldVerificationUniqueId=0;
		boolean questionStatus=false;
		//amandeep start for RCU
				boolean RCUDOCStatus=false;
				String hidRcuStatusString = CommonFunction.checkNull(request.getParameter("hidRcuStatusString"));
				String hidRcuStatusStringValue=hidRcuStatusString.replace("~",",");
				String hidRcuCommentString = vo.getHidRcuCommentString().toString(); //CommonFunction.checkNull(request.getParameter("hidRcuCommentString"));
				//String hidRcuCommentStringValue = hidRcuCommentString.replace("~",",");
				String hidDOCIDString = CommonFunction.checkNull(request.getParameter("hidDOCIDString"));
				String hidRcuChildIDString = CommonFunction.checkNull(request.getParameter("hidChildIDString"));
				String hidRcuChildIDStringValue = hidRcuChildIDString.replace("~",",");
				logger.info("hidRcuStatusString::"+hidRcuStatusString);
				logger.info("hidRcuCommentString::"+hidRcuCommentString);
				logger.info("hidDOCIDString::"+hidDOCIDString);
				//amandeep ends for RCU
		//if(vo.getFieldVerificationUniqueId().equalsIgnoreCase(""))
		//{
			fieldVerificationUniqueId = fieldDao.insertFieldVerCapture(vo);
			logger.info("Captured Id at verification capturing: "+fieldVerificationUniqueId);
			String verificationType = CommonFunction.checkNull(vo.getVarificationType());
			logger.info("verificationType::RCU"+verificationType);
			 //Rohit Chnages Starts
			String verificationSubType = CommonFunction.checkNull(vo.getVerificationSubType());
				logger.info("verificationType:::::"+verificationSubType);
				request.setAttribute("verificationType", verificationType);
				request.setAttribute("verificationSubType", verificationSubType);
		        //rohit end
			if(fieldVerificationUniqueId>0 && verificationType.contains("RCU REPORT"))
			{
				if(!hidDOCIDString.equals("") && !hidDOCIDString.equals("null"))
				{
					RCUDOCStatus=fieldDao.updateRCUDocs(hidRcuStatusStringValue,hidRcuCommentString,hidDOCIDString,hidRcuChildIDStringValue,vo);
				}
			}
			else if(fieldVerificationUniqueId>0)
			{
				vo.setFieldVerificationUniqueId(""+fieldVerificationUniqueId);
				questionStatus=fieldDao.insertQuestionDetails(vo);
				logger.info("dealId: "+vo.getDealId()+" verification Id: "+vo.getVerificationId()); 		
			}
		//}
		/*else
		{
            status = fieldDao.updateFieldVerCapture(vo);
			
			if(status)
			{
			  questionStatus=fieldDao.updateQuestionDetails(vo);
						    
			}
		}*/
		logger.info("status: "+status+"questionStatus: "+questionStatus);
		if((status) ||(fieldVerificationUniqueId>0))
		{
			session.setAttribute("fieldVerificationUniqueId", fieldVerificationUniqueId);
			request.setAttribute("sms", "S");
		}
		else
		{
			request.setAttribute("sms", "N");
		}
		return  mapping.findForward("success");		
	}
	
	public ActionForward addFieldCapture(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		
		
		HttpSession session = request.getSession();

		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		String userID="";
		
		if(userobj!=null)
		{
			userID=userobj.getUserId();
				
		}else{
			logger.info("here in addFieldCapture method of fieldVerificationDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		FieldVerificationVo vo=new FieldVerificationVo(); 		
		DynaValidatorForm FieldVarificationDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, FieldVarificationDynaValidatorForm);					
	
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
		FieldVerificationDAO dao=(FieldVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(FieldVerificationDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass()); 	// changed by asesh
		//FieldVerificationDAO dao=new FieldVerificationDAOImpl();	
		String dealId = "";	
		String stage = request.getParameter("txntype");
		dealId = CommonFunction.checkNull(request.getParameter("dealId"));
		String entityId = CommonFunction.checkNull(request.getParameter("entityId"));
		String verificationId= CommonFunction.checkNull(request.getParameter("verificationId"));
		if(session.getAttribute("dealId")!=null)
		{
			dealId=session.getAttribute("dealId").toString();
		}
		if(session.getAttribute("entityId")!=null)
		{
			entityId=session.getAttribute("entityId").toString();
		}
		if(session.getAttribute("verificationId")!=null)
		{
			verificationId=session.getAttribute("verificationId").toString();
		}
		
		if(dealId!=null && !dealId.equalsIgnoreCase("") && !CommonFunction.checkNull(verificationId).equalsIgnoreCase(""))
		{
			CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
	        logger.info("Implementation class: "+service.getClass()); 	//changed by asesh
			//CreditProcessingDAO service=new CreditProcessingDAOImpl();
			ArrayList dealHeader = service.getDealHeader(dealId);
			session.setAttribute("dealHeader", dealHeader);
			
			String appraiserType = service.getAppraiserType(dealId,verificationId);
			logger.info("appraiserType::::::::::"+appraiserType);
			if(appraiserType.equalsIgnoreCase("EXTERNAL"))
				request.setAttribute("appraiserType", appraiserType); 
			
			ArrayList verifList = dao.getVerifList(dealId,entityId,verificationId);
			request.setAttribute("verifList", verifList);
			ArrayList commonList = dao.getCommonListList(dealId,entityId,verificationId);
			request.setAttribute("commonList", commonList);
			ArrayList questList = dao.getQuestList(dealId,entityId,verificationId);
			request.setAttribute("questList", questList);
			ArrayList verifMethodList = dao.getVerifMethodListList();
			request.setAttribute("verifMethodList", verifMethodList);
			// Remove at Behind action(FieldVerificationCapturingBehindAction)
			session.setAttribute("entityId", entityId);
			session.setAttribute("dealId", dealId);
			session.setAttribute("verificationId", verificationId);
			String verificationType ="";
			String verificationSubType ="";
			String entitySubType ="";
			String entityType ="";
			if(verifList.size()>0)
			{
			 verificationType = CommonFunction.checkNull(((FieldVerificationVo)(verifList.get(0))).getVerificationType());
			 verificationSubType = CommonFunction.checkNull(((FieldVerificationVo)(verifList.get(0))).getVerificationSubType());
			 entitySubType = CommonFunction.checkNull(((FieldVerificationVo)(verifList.get(0))).getEntitySubType());
			 entityType = CommonFunction.checkNull(((FieldVerificationVo)(verifList.get(0))).getEntityType());
			logger.info("verificationType:::::"+verificationType);
			logger.info("entitySubType:::::"+entitySubType);
			request.setAttribute("verificationType", verificationType);
			request.setAttribute("verificationSubType", verificationSubType);
			request.setAttribute("entitySubType", entitySubType);
			if(CommonFunction.checkNull(entityType).contains("APPLICANT/CO")){
				request.setAttribute("textbox", "textbox");
			}
			}
			if(verificationType.contains("RCU REPORT")){
				request.setAttribute("RCUDisabled", "RCUDisabled");
			ArrayList RCUDocs = dao.getReceivedDocs(dealId,"DC");
			ArrayList RCUDocsAfterSave=dao.getReceivedDocsSaved(dealId,"DC");
			//String id = CommonFunction.checkNull(((FieldVerificationVo)(RCUDocsAfterSave.get(0))).getId());
			//String name = CommonFunction.checkNull(((FieldVerificationVo)(RCUDocsAfterSave.get(0))).getName());
			ArrayList verifMethodListRCU=new ArrayList();
			
			if(RCUDocs.size()>0 && commonList.size()==0 )
			{
				request.setAttribute("RCUDocs", RCUDocs);
				 verifMethodListRCU = dao.getVerifMethodListRCU();
				if(verifMethodListRCU.size()>0)
				{
					request.setAttribute("verifMethodListRCU", verifMethodListRCU);
				}
			 }
			else if (RCUDocsAfterSave.size()>0 && commonList.size()>0)
			{
				verifMethodListRCU = dao.getVerifMethodListRCU();
				 if(verifMethodListRCU.size()>0)
				 {
					 request.setAttribute("verifMethodListRCU", verifMethodListRCU);
				 }
				 request.setAttribute("RCUDocs", RCUDocsAfterSave);
			}
		
			}	
			// Sanjog 
			/*ArrayList uploadedDocList=new ArrayList();
			uploadedDocList = service.getUploadDocForFVC(dealId,"FVI");
			request.setAttribute("uploadedDocList", uploadedDocList);*/
			
		}
		    return mapping.findForward("newFieldCapture");	
	}
	
	public ActionForward forwardVerificationCap(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
				
		logger.info(" in forwardVerificationCap......()");	
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
		String userId="";
		String businessDate="";
		if(userobj !=null)
		{
			userId=userobj.getUserId();
			businessDate =userobj.getBusinessdate();
		}else{
			logger.info("here in forwardVerificationCap method of fieldVerificationDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		String dealId=CommonFunction.checkNull(session.getAttribute("dealId"));
		FieldVerificationVo vo=new FieldVerificationVo(); 		
		DynaValidatorForm formbeen= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, formbeen);					
		FieldVerificationDAO fieldDao=(FieldVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(FieldVerificationDAO.IDENTITY);
        logger.info("Implementation class: "+fieldDao.getClass()); 	// changed by asesh
				
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
		String verificationId="";
		if(session.getAttribute("verificationId")!=null)
		{
			verificationId=CommonFunction.checkNull(session.getAttribute("verificationId"));
		}
		logger.info("verificationId: "+verificationId);
		vo.setDealId(dealId);
		vo.setMakerId(userId);
		vo.setMakerDate(businessDate);
		vo.setVerificationStatus("F");
		vo.setVerificationId(verificationId);
		boolean status=false;
		boolean questionStatus=false;
		
            status = fieldDao.updateFieldVerCapture(vo);
			
			if(status)
			{
			  questionStatus=fieldDao.updateQuestionDetails(vo);
						    
			}
	
		 boolean st=fieldDao.modifyFieldRemarks(vo, dealId);
			logger.info("Update cr_deal_verification_dtl with R st"+st);
		logger.info("status: "+status+"questionStatus: "+questionStatus);
		if(status)
		{
			
			request.setAttribute("forwardsms", "F");
		}
		else
		{
			request.setAttribute("forwardsms", "N");
		}
		return mapping.findForward("success");	
	}
	public ActionForward downloadVarificationForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {

		logger.info("In downloadVarificationForm");
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String p_printed_by="";
		String p_printed_date="";
		String p_company_name="";		
		if(userobj==null){
			logger.info("here in downloadVarificationForm method of fieldVerificationDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		else
		{
			p_printed_by=userobj.getUserName()+" ";
			p_printed_date=userobj.getBusinessdate();
			p_company_name=userobj.getConpanyName();
		}
		Object sessionId = session.getAttribute("sessionID");
		FieldVerificationVo vo = new FieldVerificationVo();

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
		
		
		String entityId = CommonFunction.checkNull(request.getParameter("entityId"));
		String verificationId= CommonFunction.checkNull(request.getParameter("verificationId"));
		
		if(session.getAttribute("entityId")!=null)
		{
			entityId=session.getAttribute("entityId").toString();
		}
		if(session.getAttribute("verificationId")!=null)
		{
			verificationId=session.getAttribute("verificationId").toString();
		}
		
		//char source='A';
		//String sou=CommonFunction.checkNull(request.getParameter("source"));	
		//if(sou.length()>0)
		//	source=sou.charAt(0);
		char source='P';
		String sou="P";
		String reportPath="/reports/";
		String reportName="verificationCapturingReport";
		String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports/logo.bmp";
		String checkImage=getServlet().getServletContext().getRealPath("/")+"reports/CheckCheckBox.bmp";
		String uncheckImage=getServlet().getServletContext().getRealPath("/")+"reports/UnCheckCheckBox.bmp";		
		//String verificationId=request.getParameter("verificationId");
	//	String entityId=request.getParameter("entId");
		String dealId=(String)session.getAttribute("dealId");
		String SUBREPORT_DIR=getServlet().getServletContext().getRealPath("/")+"reports\\";
		if(dbType.equalsIgnoreCase("MSSQL"))
		{
			reportPath=reportPath+"MSSQLREPORTS/";
			SUBREPORT_DIR=SUBREPORT_DIR+"MSSQLREPORTS\\";
		}
		else
		{
			reportPath=reportPath+"MYSQLREPORTS/";
			SUBREPORT_DIR=SUBREPORT_DIR+"MYSQLREPORTS\\";
		}
		
		String query="select d.deal_id, deal_no,d.DEAL_CUSTOMER_ID,deal.CUSTOMER_NAME,deal_date Deal_Date ,p.PRODUCT_DESC,s.SCHEME_DESC," +
				" concat(l.DEAL_PRODUCT_CATEGORY,' ')DEAL_PRODUCT_CATEGORY,d.REC_STATUS, grid.APPRAISER_NAME as APPRAISER_NAME," +
				" date_format(grid.APPRAISAL_DATE,'%d-%m-%Y') as APPRAISAL_DATE,case grid.VERIFICATION_MODE when 'P' then 'PHONE' when 'F' then 'FIELD VISIT' when 'W' then 'WEBSITE' end as VERIFICATION_MODE,"+
				" grid.PERSON_MET as PERSON_MET,grid.RELATION as RELATION, ifnull(grid.PHONE1,'') as PHONE1,ifnull(grid.PHONE2,'') as PHONE2,grid.E_MAIL as E_MAIL,"+
				" case grid.FV_STATUS when 'P' then 'POSITIVE' when 'N' then 'NEGATIVE' when 'R' then 'REFER' end as FV_STATUS," +
				" grid.FV_REMARKS as FV_REMARKS, grid.QUESTION_ID,grid.QUESTION_SEQ_NO,grid.QUESTION,grid.QUESTION_REMARKS," +
				" grid.VERIFICATION_METHOD,grid.VERIFICATION_REQD " +
				" from cr_deal_dtl d " +
				" join cr_deal_loan_dtl l on d.DEAL_ID=l.DEAL_ID " +
				" join cr_product_m p on l.DEAL_PRODUCT=p.PRODUCT_ID " +
				" join cr_scheme_m s on l.DEAL_SCHEME=s.SCHEME_ID " +
				" join cr_deal_customer_m deal on deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID " +
				" left join" +
				" (" +
				" 		SELECT v.VERIFICATION_ID,q.QUESTION_ID,q.QUESTION_SEQ_NO,concat(q.QUESTION,' ')QUESTION,tq.QUESTION_REMARKS as QUESTION_REMARKS,tq.VERIFICATION_METHOD as VERIFICATION_METHOD," +
				" 		CASE WHEN q.VERIFICATION_REQD='Y' THEN 'YES ' ELSE 'NO ' END  VERIFICATION_REQD, cfvd.APPRAISER_NAME as APPRAISER_NAME,cfvd.APPRAISAL_DATE as APPRAISAL_DATE,cfvd.VERIFICATION_MODE as VERIFICATION_MODE,cfvd.PERSON_MET as PERSON_MET,cfvd.RELATION as RELATION, cfvd.PHONE1 as PHONE1,cfvd.PHONE2 as PHONE2,cfvd.E_MAIL as E_MAIL,cfvd.FV_STATUS as FV_STATUS,cfvd.FV_REMARKS as FV_REMARKS " +
				" 		FROM cr_deal_verification_question_m q 	" +
				" 		inner join cr_deal_verification_dtl v on q.VERIFICATION_TYPE=v.VERIFICATION_TYPE and q.VERIFICATION_SUB_TYPE=v.VERIFICATION_SUBTYPE   AND q.ENTITY_TYPE=V.ENTITY_TYPE AND q.ENTITY_SUB_TYPE=V.ENTITY_SUB_TYPE " +
				" 		left join cr_deal_question_verification_dtl tq on tq.QUESTION_ID=q.QUESTION_ID AND TQ.VERIFICATION_ID=v.VERIFICATION_ID  left join CR_FIELD_VERIFICATION_DTL cfvd on cfvd.VERIFICATION_ID= v.VERIFICATION_ID and cfvd.verification_type= v.VERIFICATION_TYPE and cfvd.VERIFICATION_SUB_TYPE=v.VERIFICATION_SUBTYPE  and cfvd.ENTITY_TYPE=V.ENTITY_TYPE AND cfvd.ENTITY_SUB_TYPE=V.ENTITY_SUB_TYPE and cfvd.entity_id=v.entity_id " +
				" 		WHERE q.REC_STATUS='A' and v.VERIFICATION_ID="+verificationId+" AND ((q.VERIFICATION_TYPE='CONTACT VERIFICATION' and q.ADDRESS_TYPE=v.ADDRESS_TYPE) or (q.VERIFICATION_TYPE<>'CONTACT VERIFICATION' and 'a'='a'))"+
				" )grid on('a'='a')" +
				" where d.DEAL_ID="+dealId;
		String subQuery="";
		switch(source)
		{					
			case 'A': subQuery="select concat(a.ADDRESS_TYPE,' ')ADDRESS_TYPE,concat(a.ADDRESS_LINE1,' ')ADDRESS_LINE1," +
							" concat(a.ADDRESS_LINE2,' ')ADDRESS_LINE2,concat(a.ADDRESS_LINE3,' ')ADDRESS_LINE3,concat(b.COUNTRY_DESC,' ')COUNTRY_DESC," +
							" concat(c.STATE_DESC,' ')STATE_DESC,concat(a.TAHSIL,' ')TAHSIL,concat(d.DISTRICT_DESC,' ')DISTRICT_DESC ,a.PINCODE,a.PRIMARY_PHONE," +
							" a.ALTERNATE_PHONE, a.TOLLFREE_NUMBER,a.FAX,concat(a.LANDMARK,' ')LANDMARK,a.NO_OF_YEARS,a.NO_OF_MONTHS,a.BRANCH_DISTANCE,a.COMMUNICATION_ADDRESS," +
							" case a.ADDRESS_DETAIL when 'O' then 'Owned ' when 'R' then 'Rented' else 'Company Provided'  end as addressDetail,dtl.VERIFICATION_TYPE," +
							" dtl.VERIFICATION_SUBTYPE,dtl.ENTITY_TYPE,dtl.ENTITY_SUB_TYPE,dtl.ENTITY_DESC,dtl.EXTERNAL_APPRAISER " +
							" from cr_deal_address_m a " +
							" join com_country_m b on(b.COUNTRY_ID=a.COUNTRY)" +
							" join com_state_m c on(c.STATE_ID=a.STATE)" +
							" join com_district_m d on(d.DISTRICT_ID=a.DISTRICT)" +
							" left join " +
							" (" +
							" 		SELECT concat(VERIFICATION_TYPE,' ')VERIFICATION_TYPE,concat(VERIFICATION_SUBTYPE,' ')VERIFICATION_SUBTYPE," +
							" 		case ENTITY_TYPE when 'PRAPPL' then 'APPLICANT ' when 'COAPPL' then 'COAPPLICANT ' else concat(ENTITY_TYPE,' ') end as ENTITY_TYPE," +
							" 		concat(ENTITY_SUB_TYPE,' ')ENTITY_SUB_TYPE,concat(ENTITY_DESC,' ')ENTITY_DESC,(SELECT concat(AGENCY_NAME,' ') FROM COM_AGENCY_M  WHERE AGENCY_CODE = EXTERNAL_APPRAISER) as EXTERNAL_APPRAISER " +
							" 		FROM cr_deal_verification_dtl V " +
							"		WHERE DEAL_ID= "+dealId+" and VERIFICATION_ID="+verificationId+" " +
							" )dtl on('a'='a')" +
							" where a.ADDRESS_ID="+entityId;
						break;				
			case 'B': subQuery="Select c.ASSET_NEW_OLD,concat(c.ASSET_COLLATERAL_DESC,' ')PROPERTY_DESC,CONCAT(c.PROPERTY_TYPE,' ')PROPERTY_TYPE," +
							" CONCAT(c.LAWYER_NAME,' ')LAWYER_NAME,CONCAT(c.VALUER_NAME,' ')VALUER_NAME, CONCAT(c.PROPERTY_OWNER,' ')PROPERTY_OWNER,c.PROPERTY_CONSTRUCTION," +
							" c.PROPERTY_AREA  Construted_Area,c.BUILT_UP_AREA 'BUILT_UP_AREA(sqft)',c.BUILDUPAREASQMTR,c.SUPER_BUILDUP_AREA,case c.PROPERTY_DIRECTION when 'E' " +
							" then 'East ' when 'W' then 'West ' when 'N' then 'North ' when 'S' then 'South ' when 'NE' then 'North-East ' when 'NW' then 'North-West ' when 'SW' " +
							" then 'South-West ' when 'SE' then 'South-East '  end PROPERTY_DIRECTION ,CONCAT(c.CARPET_AREA,' ')CARPET_AREA,c.ASSET_COLLATERAL_VALUE," +
							"  c.COLLATERAL_SECURITY_MARGIN,c.TECH_VERIFICATION TECHNICAL_VAL1,((dtl.DEAL_LOAN_AMOUNT/c.TECH_VERIFICATION)*100)tv1LTV,c.TECHNICAL_VAL1 TECHNICAL_VAL2," +
							" ((dtl.DEAL_LOAN_AMOUNT/c.TECHNICAL_VAL1)*100)tv2LTV,c.TECHNICAL_VAL2 TECHNICAL_VAL3,((dtl.DEAL_LOAN_AMOUNT/c.TECHNICAL_VAL2)*100)tv3LTV," +
							" (SELECT CONCAT(DESCRIPTION,' ') FROM GENERIC_MASTER WHERE GENERIC_KEY='VALUATION_METHOD' AND REC_STATUS='A' and VALUE=c.VALUATION_METHOD_ID)VALUATION_METHOD," +
							" c.VALUATION_AMOUNT,c.DOCUMENT_VALUE,((dtl.DEAL_LOAN_AMOUNT/c.DOCUMENT_VALUE)*100)document_valueLTV,c.ADDITIONAL_CONSTRUCTION," +
							" ((dtl.DEAL_LOAN_AMOUNT/c.ADDITIONAL_CONSTRUCTION)*100)ADDITIONAL_CONSTRUCTIONLTV,c.PROPERTY_ADDRESS,c.ADDRESS_LINE2,c.ADDRESS_LINE3," +
							" (select concat(country_desc,' ') from com_country_m where country_id=c.COUNTRY)COUNTRY,(SELECT concat(STATE_DESC,' ') from com_state_m WHERE STATE_ID=c.STATE)STATE," +
							" (select concat(district_desc,' ') from com_district_m where district_id=c.DISTRICT)DISTRICT,c.TEHSIL,c.PINCODE," +
							" case c.SECURITY when 'A' then 'Additional ' when 'H' then 'Hypothecation ' when 'P' then 'Prime ' end as SECURITY,c.ASSET_LEVEL," +
							" case c.PROPERTY_STATUS when 'LOR' then 'Leased OR Rented ' when 'OBF' then 'Occupied by Family or Friend ' when 'SO' then " +
							" 'Self Occupied ' when 'UC' then 'Under Construction ' when 'VA' then 'Vacant ' end as  PROPERTY_STATUS,case c.PROPERTY_TITLE when 'FH' " +
							" then 'Free Hold ' when 'LH' then 'Lease Hold ' when 'OT' then 'Other ' end as PROPERTY_TITLE,concat(c.VILLAGE_NAME_LANDMARK,' ')VILLAGE_NAME_LANDMARK," +
							" dtl.VERIFICATION_TYPE,dtl.VERIFICATION_SUBTYPE,dtl.ENTITY_TYPE,dtl.ENTITY_SUB_TYPE,dtl.ENTITY_DESC,dtl.EXTERNAL_APPRAISER " +
							" from cr_asset_collateral_m c " +
							" left join cr_deal_collateral_m d on c.ASSET_ID=d.ASSETID " +
							" left join " +
							" (" +
							" 		SELECT L.DEAL_LOAN_AMOUNT,concat(VERIFICATION_TYPE,' ')VERIFICATION_TYPE,concat(VERIFICATION_SUBTYPE,' ')VERIFICATION_SUBTYPE," +
							" 		case ENTITY_TYPE when 'PRAPPL' then 'APPLICANT ' when 'COAPPL' then 'COAPPLICANT ' else concat(ENTITY_TYPE,' ') end as ENTITY_TYPE," +
							" 		concat(ENTITY_SUB_TYPE,' ')ENTITY_SUB_TYPE,concat(ENTITY_DESC,' ')ENTITY_DESC,	(SELECT concat(AGENCY_NAME,' ') FROM COM_AGENCY_M  WHERE AGENCY_CODE = EXTERNAL_APPRAISER) as EXTERNAL_APPRAISER " +
							" 		FROM cr_deal_verification_dtl V " +
							" 		JOIN CR_DEAL_LOAN_DTL L ON(V.DEAL_ID=L.DEAL_ID)	" +
							" 		WHERE V.DEAL_ID= "+dealId+" and VERIFICATION_ID="+verificationId+" " +
							" )dtl on('a'='a')" +
							" where c.ASSET_ID="+entityId+" and c.ASSET_COLLATERAL_CLASS='PROPERTY'";
						break;							
			case 'C': subQuery="Select case c.ASSET_NEW_OLD when 'N' then 'New ' else 'Yes ' end as ASSET_NEW_OLD,concat(c.ASSET_COLLATERAL_DESC,' ')ASSET_COLLATERAL_DESC," +
							" concat(c.VEHICLE_MAKE,' ')VEHICLE_MAKE,concat(c.VEHICLE_MODEL,' ')VEHICLE_MODEL,concat(c.VEHICLE_ASSET_USES_TYPE,' ')VEHICLE_ASSET_USES_TYPE," +
							" (SELECT CONCAT(STATE_DESC,' ') FROM com_state_m WHERE STATE_ID=c.STATE)STATE,c.DEFAULT_LTV,C.COLLATERAL_SECURITY_MARGIN," +
							" c.LOAN_AMOUNT,C.VEHICLE_VALUE,C.VEHICLE_DISCOUNT,C.ASSET_COLLATERAL_VALUE,concat(C.VEHICLE_REGISTRATION_NO,' ')VEHICLE_REGISTRATION_NO," +
							" C.VEHICLE_REGISTRATION_DATE,concat(C.ENGINE_NUMBER,' ')ENGINE_NUMBER,concat(C.VEHICLE_CHASIS_NUMBER,' ')VEHICLE_CHASIS_NUMBER," +
							" concat(C.VEHICLE_OWNER,' ')VEHICLE_OWNER,concat(C.VEHICLE_MANUFACTURING_YEAR,' ')VEHICLE_MANUFACTURING_YEAR," +
							" concat(C.VEHICLE_Insurer,' ')VEHICLE_Insurer,C.VEHICLE_INSURED_DATE,case C.SECURITY when 'A' then 'Additional ' when 'H' then " +
							" 'Hypothecation ' when 'P' then 'Prime ' end as SECURITY,dtl.VERIFICATION_TYPE,dtl.VERIFICATION_SUBTYPE,dtl.ENTITY_TYPE" +
							" ,dtl.ENTITY_SUB_TYPE,dtl.ENTITY_DESC,dtl.EXTERNAL_APPRAISER " +
							" from cr_asset_collateral_m c " +
							" left join " +
							" (" +
							" 		SELECT L.DEAL_LOAN_AMOUNT,concat(VERIFICATION_TYPE,' ')VERIFICATION_TYPE,concat(VERIFICATION_SUBTYPE,' ')VERIFICATION_SUBTYPE," +
							" 		case ENTITY_TYPE when 'PRAPPL' then 'APPLICANT ' when 'COAPPL' then 'COAPPLICANT ' else concat(ENTITY_TYPE,' ') end as ENTITY_TYPE," +
							" 		concat(ENTITY_SUB_TYPE,' ')ENTITY_SUB_TYPE,concat(ENTITY_DESC,' ')ENTITY_DESC,(SELECT concat(AGENCY_NAME,' ') FROM COM_AGENCY_M  WHERE AGENCY_CODE = EXTERNAL_APPRAISER) as EXTERNAL_APPRAISER " +
							" 		FROM cr_deal_verification_dtl V  " +
							" 		JOIN CR_DEAL_LOAN_DTL L ON(V.DEAL_ID=L.DEAL_ID)	" +
							" 		WHERE V.DEAL_ID= "+dealId+" and VERIFICATION_ID="+verificationId+" " +
							" )dtl on('a'='a') " +
							" where c.ASSET_ID="+entityId+" and c.ASSET_COLLATERAL_CLASS='VEHICLE'";
						break;						
			case 'D': subQuery="Select concat(c.ASSET_COLLATERAL_DESC,' ')ASSET_COLLATERAL_DESC,concat(c.MACHINE_VALUE,' ')MACHINE_VALUE,c.MACHINE_DISCOUNT,c.ASSET_COLLATERAL_VALUE," +
							" c.COLLATERAL_SECURITY_MARGIN,concat(c.MACHINE_MAKE,' ')MACHINE_MAKE,concat(c.MACHINE_MODEL,' ')MACHINE_MODEL ," +
							" case c.MACHINE_TYPE when 'L' then 'Local ' else 'Imported ' end as MACHINE_TYPE,concat(c.MACHINE_OWNER,' ')MACHINE_OWNER,c.MACHINE_MANUFACTURING_YEAR," +
							" concat(c.MACHINE_IDENTIFICATION_NO,' ')MACHINE_IDENTIFICATION_NO,case c.ASSET_NEW_OLD when 'N' then 'New ' else 'Old 'end as  ASSET_NEW_OLD," +
							" concat(c.ASSET_MANUFATURER_DESC,' ')ASSET_MANUFATURER_DESC,concat(c.ASSET_SUPPLIER_DESC,' ')ASSET_SUPPLIER_DESC,c.invoice_date," +
							" case c.SECURITY when 'A' then 'Additional ' when 'H' then 'Hypothecation ' else 'Prime' end as SECURITY,c.asset_standard," +
							" dtl.VERIFICATION_TYPE,dtl.VERIFICATION_SUBTYPE,dtl.ENTITY_TYPE,dtl.ENTITY_SUB_TYPE,dtl.ENTITY_DESC,dtl.EXTERNAL_APPRAISER " +
							" from cr_asset_collateral_m c " +
							" left join" +
							" (" +
							" 		SELECT L.DEAL_LOAN_AMOUNT,concat(VERIFICATION_TYPE,' ')VERIFICATION_TYPE,concat(VERIFICATION_SUBTYPE,' ')VERIFICATION_SUBTYPE," +
							" 		case ENTITY_TYPE when 'PRAPPL' then 'APPLICANT ' when 'COAPPL' then 'COAPPLICANT ' else concat(ENTITY_TYPE,' ') end as ENTITY_TYPE," +
							" 		concat(ENTITY_SUB_TYPE,' ')ENTITY_SUB_TYPE,concat(ENTITY_DESC,' ')ENTITY_DESC,(SELECT concat(AGENCY_NAME,' ') FROM COM_AGENCY_M  WHERE AGENCY_CODE = EXTERNAL_APPRAISER) as EXTERNAL_APPRAISER " +
							" 		FROM cr_deal_verification_dtl V " +
							" 		JOIN CR_DEAL_LOAN_DTL L ON(V.DEAL_ID=L.DEAL_ID)	" +
							" 		WHERE V.DEAL_ID= "+dealId+" and VERIFICATION_ID="+verificationId+" " +
							" )dtl on('a'='a')" +
							" where c.ASSET_ID="+entityId+" and c.ASSET_COLLATERAL_CLASS='MACHINE'";
						break;					
			case 'E': subQuery="Select concat(c.ASSET_COLLATERAL_DESC,' ')ASSET_COLLATERAL_DESC, c.ASSET_COLLATERAL_VALUE ,dtl.VERIFICATION_TYPE," +
							" dtl.VERIFICATION_SUBTYPE,dtl.ENTITY_TYPE,dtl.ENTITY_SUB_TYPE,dtl.ENTITY_DESC,dtl.EXTERNAL_APPRAISER " +
							" from cr_asset_collateral_m c " +
							" left join " +
							" (" +
							" 		SELECT L.DEAL_LOAN_AMOUNT,concat(VERIFICATION_TYPE,' ')VERIFICATION_TYPE,concat(VERIFICATION_SUBTYPE,' ')VERIFICATION_SUBTYPE," +
							" 		case ENTITY_TYPE when 'PRAPPL' then 'APPLICANT ' when 'COAPPL' then 'COAPPLICANT ' else concat(ENTITY_TYPE,' ') end as ENTITY_TYPE," +
							" 		concat(ENTITY_SUB_TYPE,' ')ENTITY_SUB_TYPE,concat(ENTITY_DESC,' ')ENTITY_DESC,(SELECT concat(AGENCY_NAME,' ') FROM COM_AGENCY_M  WHERE AGENCY_CODE = EXTERNAL_APPRAISER) as EXTERNAL_APPRAISER " +
							" 		FROM cr_deal_verification_dtl V " +
							" 		JOIN CR_DEAL_LOAN_DTL L ON(V.DEAL_ID=L.DEAL_ID)	" +
							" 		WHERE V.DEAL_ID= "+dealId+" and VERIFICATION_ID="+verificationId+" " +
							" )dtl on('a'='a')" +
							" where c.ASSET_ID="+entityId+" and c.ASSET_COLLATERAL_CLASS='OTHERS'";
						break;					
			case 'F': subQuery="Select case c.BG_TYPE when 'F' then 'Financial ' when 'P' then 'Performance ' end as  BG_TYPE,c.ASSET_COLLATERAL_VALUE," +
							" c.BG_ISSUE_DATE,c.BG_VALIDITY_DATE,concat(c.BG_ISSUING_BANK,' '),dtl.VERIFICATION_TYPE,dtl.VERIFICATION_SUBTYPE,dtl.ENTITY_TYPE," +
							" dtl.ENTITY_SUB_TYPE,dtl.ENTITY_DESC,dtl.EXTERNAL_APPRAISER " +
							" from cr_asset_collateral_m c " +
							" left join " +
							" (" +
							" 		SELECT L.DEAL_LOAN_AMOUNT," +
							" 		concat(VERIFICATION_TYPE,' ')VERIFICATION_TYPE,concat(VERIFICATION_SUBTYPE,' ')VERIFICATION_SUBTYPE,case ENTITY_TYPE when 'PRAPPL' then 'APPLICANT ' " +
							" 		when 'COAPPL' then 'COAPPLICANT ' else concat(ENTITY_TYPE,' ') end as ENTITY_TYPE,concat(ENTITY_SUB_TYPE,' ')ENTITY_SUB_TYPE,concat(ENTITY_DESC,' ')ENTITY_DESC," +
							" 		(SELECT concat(AGENCY_NAME,' ') FROM COM_AGENCY_M  WHERE AGENCY_CODE = EXTERNAL_APPRAISER) as EXTERNAL_APPRAISER " +
							" 		FROM cr_deal_verification_dtl V " +
							" 		JOIN CR_DEAL_LOAN_DTL L ON(V.DEAL_ID=L.DEAL_ID)	" +
							" 		WHERE V.DEAL_ID= "+dealId+" and VERIFICATION_ID="+verificationId+" " +
							" ) dtl on('a'='a')" +
							" where c.ASSET_ID="+entityId+" and c.ASSET_COLLATERAL_CLASS='BG'";
						break;					
			case 'G': subQuery="Select concat(c.ASSET_COLLATERAL_DESC,' ')ASSET_COLLATERAL_DESC,c.ASSET_COLLATERAL_VALUE,c.COLLATERAL_SECURITY_MARGIN," +
							" c.DEBTOR_TOTAL_OUTSTANDING,case c.DEBTOR_TYPE when '30' then '<30 Days '  when '31' then '31-60 Days ' when '61' then '61-90 Days ' when '91' then '91> Days ' end as DEBTOR_TYPE ," +
							" dtl.VERIFICATION_TYPE,dtl.VERIFICATION_SUBTYPE,dtl.ENTITY_TYPE,dtl.ENTITY_SUB_TYPE,dtl.ENTITY_DESC,dtl.EXTERNAL_APPRAISER " +
							" from cr_asset_collateral_m c " +
							" left join " +
							" (" +
							" 		SELECT L.DEAL_LOAN_AMOUNT,concat(VERIFICATION_TYPE,' ')VERIFICATION_TYPE,concat(VERIFICATION_SUBTYPE,' ')VERIFICATION_SUBTYPE," +
							" 		case ENTITY_TYPE when 'PRAPPL' then 'APPLICANT ' when 'COAPPL' then 'COAPPLICANT ' else concat(ENTITY_TYPE,' ') end as ENTITY_TYPE," +
							" 		concat(ENTITY_SUB_TYPE,' ')ENTITY_SUB_TYPE,concat(ENTITY_DESC,' ')ENTITY_DESC,(SELECT concat(AGENCY_NAME,' ') FROM COM_AGENCY_M  WHERE AGENCY_CODE = EXTERNAL_APPRAISER) as EXTERNAL_APPRAISER " +
							" 		FROM cr_deal_verification_dtl V " +
							" 		JOIN CR_DEAL_LOAN_DTL L ON(V.DEAL_ID=L.DEAL_ID)	" +
							" 		WHERE V.DEAL_ID= "+dealId+" and VERIFICATION_ID="+verificationId+" " +
							" )dtl on('a'='a')" +
							" where c.ASSET_ID="+entityId+" and c.ASSET_COLLATERAL_CLASS='DEBTOR'";
						break;					
			case 'H': subQuery="Select concat(c.ASSET_COLLATERAL_DESC,' ')ASSET_COLLATERAL_DESC, c.ASSET_COLLATERAL_VALUE,c.COLLATERAL_SECURITY_MARGIN,c.FD_AMOUNT, " +
							" c.FD_TENURE, c.FD_RATE,c.FD_BOOK_DATE,c.FD_MATURITY_DATE,CONCAT(C.FD_AGENCY_NAME,' ')FD_AGENCY_NAME,CONCAT(C.FD_AGENCY_RATING,' ')FD_AGENCY_RATING," +
							" (SELECT CONCAT(BANK_NAME,' ') FROM com_bank_m where BANK_ID=c.ISSUEING_BANK_ID)BANK_NAME,(SELECT CONCAT(BANK_BRANCH_NAME,' ') FROM com_bankbranch_m where BANK_BRANCH_ID=c.ISSUEING_BRANCH_ID)BRANCHNAME," +
							" CONCAT(c.FD_APPLICANT,' ')FD_APPLICANT,dtl.VERIFICATION_TYPE,dtl.VERIFICATION_SUBTYPE,dtl.ENTITY_TYPE,dtl.ENTITY_SUB_TYPE,dtl.ENTITY_DESC,dtl.EXTERNAL_APPRAISER " +
							" from cr_asset_collateral_m c " +
							" left join " +
							"(" +
							" 		SELECT L.DEAL_LOAN_AMOUNT,concat(VERIFICATION_TYPE,' ')VERIFICATION_TYPE,concat(VERIFICATION_SUBTYPE,' ')VERIFICATION_SUBTYPE," +
							" 		case ENTITY_TYPE when 'PRAPPL' then 'APPLICANT ' when 'COAPPL' then 'COAPPLICANT ' else concat(ENTITY_TYPE,' ') end as ENTITY_TYPE," +
							" 		concat(ENTITY_SUB_TYPE,' ')ENTITY_SUB_TYPE,concat(ENTITY_DESC,' ')ENTITY_DESC,(SELECT concat(AGENCY_NAME,' ') FROM COM_AGENCY_M  WHERE AGENCY_CODE = EXTERNAL_APPRAISER) as EXTERNAL_APPRAISER " +
							" 		FROM cr_deal_verification_dtl V " +
							" 		JOIN CR_DEAL_LOAN_DTL L ON(V.DEAL_ID=L.DEAL_ID)	" +
							" 		WHERE V.DEAL_ID= "+dealId+" and VERIFICATION_ID="+verificationId+" " +
							" )dtl on('a'='a')" +
							" where c.ASSET_ID="+entityId+" and c.ASSET_COLLATERAL_CLASS='FD'";
						break;				
			case 'I':subQuery="Select CONCAT(c.ASSET_COLLATERAL_DESC,' ')ASSET_COLLATERAL_DESC,c.ASSET_COLLATERAL_VALUE,c.COLLATERAL_SECURITY_MARGIN," +
							" c.SBLC_AMOUNT,c.SBLC_VALIDITY,c.SBLC_ISSUING_DATE,CONCAT(c.SBLC_PARENT_COMPANY,' ')SBLC_PARENT_COMPANY,dtl.VERIFICATION_TYPE," +
							" dtl.VERIFICATION_SUBTYPE,dtl.ENTITY_TYPE,dtl.ENTITY_SUB_TYPE,dtl.ENTITY_DESC,dtl.EXTERNAL_APPRAISER " +
							" from cr_asset_collateral_m c " +
							" left join" +
							" (" +
							" 		SELECT L.DEAL_LOAN_AMOUNT,concat(VERIFICATION_TYPE,' ')VERIFICATION_TYPE,concat(VERIFICATION_SUBTYPE,' ')VERIFICATION_SUBTYPE," +
							" 		case ENTITY_TYPE when 'PRAPPL' then 'APPLICANT ' when 'COAPPL' then 'COAPPLICANT ' else concat(ENTITY_TYPE,' ') end as ENTITY_TYPE," +
							" 		concat(ENTITY_SUB_TYPE,' ')ENTITY_SUB_TYPE,concat(ENTITY_DESC,' ')ENTITY_DESC,(SELECT concat(AGENCY_NAME,' ') FROM COM_AGENCY_M  WHERE AGENCY_CODE = EXTERNAL_APPRAISER) as EXTERNAL_APPRAISER " +
							" 		FROM cr_deal_verification_dtl V " +
							" 		JOIN CR_DEAL_LOAN_DTL L ON(V.DEAL_ID=L.DEAL_ID)	" +
							" 		WHERE V.DEAL_ID= "+dealId+" and VERIFICATION_ID="+verificationId+" " +
							" )dtl on('a'='a')" +
							" where c.ASSET_ID="+entityId+" and c.ASSET_COLLATERAL_CLASS='SBLC'";
						break;					
			case 'J': subQuery="Select CONCAT(c.ASSET_COLLATERAL_DESC,' ')ASSET_COLLATERAL_DESC,c.ASSET_COLLATERAL_VALUE,c.COLLATERAL_SECURITY_MARGIN ," +
							" CASE c.SECURITY_TYPE WHEN 'm' THEN 'Mutual Funds ' WHEN 's' THEN 'Shares and Debentures ' WHEN 'b' THEN 'Bonds ' END AS SECURITY_TYPE," +
							" c.SECURITY_CATEGORY, c.SECURITY_MARKET_VALUE,dtl.VERIFICATION_TYPE,dtl.VERIFICATION_SUBTYPE,dtl.ENTITY_TYPE,dtl.ENTITY_SUB_TYPE,dtl.ENTITY_DESC,dtl.EXTERNAL_APPRAISER " +
							" from cr_asset_collateral_m c " +
							" left join" +
							" (" +
							" 		SELECT L.DEAL_LOAN_AMOUNT,concat(VERIFICATION_TYPE,' ')VERIFICATION_TYPE,concat(VERIFICATION_SUBTYPE,' ')VERIFICATION_SUBTYPE," +
							" 		case ENTITY_TYPE when 'PRAPPL' then 'APPLICANT ' when 'COAPPL' then 'COAPPLICANT ' else concat(ENTITY_TYPE,' ') end as ENTITY_TYPE,concat(ENTITY_SUB_TYPE,' ')ENTITY_SUB_TYPE," +
							" 		concat(ENTITY_DESC,' ')ENTITY_DESC,(SELECT concat(AGENCY_NAME,' ') FROM COM_AGENCY_M  WHERE AGENCY_CODE = EXTERNAL_APPRAISER) as EXTERNAL_APPRAISER " +
							" 		FROM cr_deal_verification_dtl V " +
							" 		JOIN CR_DEAL_LOAN_DTL L ON(V.DEAL_ID=L.DEAL_ID)	" +
							" 		WHERE V.DEAL_ID= "+dealId+" and VERIFICATION_ID="+verificationId+" " +
							" )dtl on('a'='a')" +
							" where c.ASSET_ID="+entityId+" and c.ASSET_COLLATERAL_CLASS='SECURITIES'";
						break;					
			case 'K':subQuery="Select concat(c.ASSET_COLLATERAL_DESC,' ')ASSET_COLLATERAL_DESC, c.ASSET_COLLATERAL_VALUE,  c.COLLATERAL_SECURITY_MARGIN," +
							" case c.STOCK_TYPE when 'Master' then 'Semi Finished ' when 'Master' then 'Finished ' end as STOCK_TYPE," +
							" case c.STOCK_NATURE when 'Master' then 'Perishable ' when 'Master' then 'Non Perishable ' end as STOCK_NATURE,concat(c.STOCK_GODOWN_ADDRESS,' ')STOCK_GODOWN_ADDRESS," +
							" concat(c.STOCK_INVENTORY_CYCLE,' ')STOCK_INVENTORY_CYCLE,dtl.VERIFICATION_TYPE,dtl.VERIFICATION_SUBTYPE,dtl.ENTITY_TYPE,dtl.ENTITY_SUB_TYPE,dtl.ENTITY_DESC," +
							" dtl.EXTERNAL_APPRAISER " +
							" from cr_asset_collateral_m c " +
							" left join" +
							" (" +
							" 		SELECT L.DEAL_LOAN_AMOUNT,concat(VERIFICATION_TYPE,' ')VERIFICATION_TYPE,concat(VERIFICATION_SUBTYPE,' ')VERIFICATION_SUBTYPE," +
							" 		case ENTITY_TYPE when 'PRAPPL' then 'APPLICANT ' when 'COAPPL' then 'COAPPLICANT ' else concat(ENTITY_TYPE,' ') end as ENTITY_TYPE,concat(ENTITY_SUB_TYPE,' ')ENTITY_SUB_TYPE," +
							" 		concat(ENTITY_DESC,' ')ENTITY_DESC,(SELECT concat(AGENCY_NAME,' ') FROM COM_AGENCY_M  WHERE AGENCY_CODE = EXTERNAL_APPRAISER) as EXTERNAL_APPRAISER " +
							" 		FROM cr_deal_verification_dtl V " +
							" 		JOIN CR_DEAL_LOAN_DTL L ON(V.DEAL_ID=L.DEAL_ID)	" +
							" 		WHERE V.DEAL_ID= "+dealId+" and VERIFICATION_ID="+verificationId+" " +
							" )dtl on('a'='a')" +
							" where c.ASSET_ID="+entityId+" and c.ASSET_COLLATERAL_CLASS='STOCK'";
						break;					
			case 'L': subQuery="Select (select concat(AGENCY_NAME,' ') from com_agency_m where agency_type='INS' and REC_STATUS='A' AND agency_code=C.INSURANCE_AGENCY)INSURANCE_AGENCY_NAME," +
							" concat(c.ASSET_COLLATERAL_DESC,' ')ASSET_COLLATERAL_DESC,concat(C.COVER_NOTE,' ')COVER_NOTE,concat(C.INSURANCE_POLICY_NO,' ')INSURANCE_POLICY_NO,C.INSURANCE_POLICY_START_DATE" +
							" ,C.INSURANCE_MATURITY_DATE,C.SUM_ASSURED ,c.ASSET_COLLATERAL_VALUE,C.INSURANCE_PREMIUM_AMOUNT,case C.INSURANCE_PREMIUM_FREQUENCY when 'MONTHLY' then 'MONTHLY ' when 'QUARTERLY' " +
							" then 'QUARTERLY ' when 'HALFYEARLY' then 'HALF YEARLY ' when 'ANNUALY' then 'ANNUALY ' end as INSURANCE_PREMIUM_FREQUENCY,C.INSURANCE_NOMINEE,case C.INSURANCE_RELATION_WITH_NOMINEE " +
							" when 'SIS' then 'SISTER ' else 'OTHER '  end as INSURANCE_RELATION_WITH_NOMINEE,C.INSURANCE_TENURE,dtl.VERIFICATION_TYPE,dtl.VERIFICATION_SUBTYPE," +
							" dtl.ENTITY_TYPE,dtl.ENTITY_SUB_TYPE,dtl.ENTITY_DESC,dtl.EXTERNAL_APPRAISER " +
							" from cr_asset_collateral_m c " +
							" left join" +
							" (" +
							" 		SELECT L.DEAL_LOAN_AMOUNT,concat(VERIFICATION_TYPE,' ')VERIFICATION_TYPE,concat(VERIFICATION_SUBTYPE,' ')VERIFICATION_SUBTYPE" +
							" 		,case ENTITY_TYPE when 'PRAPPL' then 'APPLICANT ' when 'COAPPL' then 'COAPPLICANT ' else concat(ENTITY_TYPE,' ') end as ENTITY_TYPE," +
							" 		concat(ENTITY_SUB_TYPE,' ')ENTITY_SUB_TYPE,concat(ENTITY_DESC,' ')ENTITY_DESC,(SELECT concat(AGENCY_NAME,' ') FROM COM_AGENCY_M  WHERE AGENCY_CODE = EXTERNAL_APPRAISER) as EXTERNAL_APPRAISER " +
							" 		FROM cr_deal_verification_dtl V " +
							" 		JOIN CR_DEAL_LOAN_DTL L ON(V.DEAL_ID=L.DEAL_ID)	" +
							" 		WHERE V.DEAL_ID= "+dealId+" and VERIFICATION_ID="+verificationId+" " +
							" )dtl on('a'='a')" +
							" where c.ASSET_ID="+entityId+" and c.ASSET_COLLATERAL_CLASS='INSURANCE'";
			            break;		
			case 'M': subQuery="Select "+
							" dtl.VERIFICATION_TYPE,dtl.VERIFICATION_SUBTYPE,dtl.ENTITY_TYPE,dtl.ENTITY_SUB_TYPE,dtl.ENTITY_DESC,dtl.EXTERNAL_APPRAISER " +
							" from "+
							" ( " +
							" 		SELECT L.DEAL_LOAN_AMOUNT,concat(VERIFICATION_TYPE,' ')VERIFICATION_TYPE,concat(VERIFICATION_SUBTYPE,' ')VERIFICATION_SUBTYPE" +
							" 		,case ENTITY_TYPE when 'PRAPPL' then 'APPLICANT ' when 'COAPPL' then 'COAPPLICANT ' else concat(ENTITY_TYPE,' ') end as ENTITY_TYPE," +
							" 		concat(ENTITY_SUB_TYPE,' ')ENTITY_SUB_TYPE,concat(ENTITY_DESC,' ')ENTITY_DESC,(SELECT concat(AGENCY_NAME,' ') FROM COM_AGENCY_M  WHERE AGENCY_CODE = EXTERNAL_APPRAISER) as EXTERNAL_APPRAISER " +
							" 		FROM cr_deal_verification_dtl V " +
							" 		JOIN CR_DEAL_LOAN_DTL L ON(V.DEAL_ID=L.DEAL_ID)	" +
							" 		WHERE V.DEAL_ID= "+dealId+" and VERIFICATION_ID="+verificationId+" " +
							" ) dtl ";
						break;	
			case 'N': subQuery="select CUSTOMER_ID,concat(CUSTOMER_FNAME,' ')CUSTOMER_FNAME,concat(CUSTOMER_MNAME,' ')CUSTOMER_MNAME,concat(CUSTOMER_LNAME,' ')CUSTOMER_LNAME," +
							" CASE GENDER WHEN 'F' THEN 'FEMALE ' WHEN 'M' THEN 'MALE ' END AS GENDER,concat(CUSTOMER_CATEGORY,' ')CUSTOMER_CATEGORY,concat(CUSTOMER_CONSTITUTION,' ')CUSTOMER_CONSTITUTION," +
							" concat(FATHER_HUSBAND_NAME,' ')FATHER_HUSBAND_NAME,CUSTOMER_DOB,CASE CASTE_CATEGORY WHEN 'G' THEN 'General ' WHEN 'OBC' THEN 'Other Backward Cast(OBC) ' WHEN 'SC' THEN 'Scheduled Caste(SC)' " +
							" WHEN 'ST' THEN 'Schedule tribe(ST)' END AS CASTE_CATEGORY,case MARITAL_STATUS when 'M' then 'Married ' when 'U' then 'Un-Married ' end as MARITAL_STATUS,concat(CUSTOMER_EMAIL,' ')CUSTOMER_EMAIL," +
							" concat(C.CUSTMER_PAN,' ')CUSTMER_PAN,concat(C.DRIVING_LICENSE,' ')DRIVING_LICENSE,concat(C.VOTER_ID,' ')VOTER_ID,concat(C.PASSPORT_NUMBER,' ')PASSPORT_NUMBER,concat(C.UID_NO,' ')UID_NO,concat(C.OTHER_NO,' ')OTHER_NO," +
							" case C.OTHOR_RELATIONSHIP_TYPE when 'CS' then 'Customer ' when 'SU' then 'Supplier ' when 'MF' then 'Manufacturer ' end as OTHOR_RELATIONSHIP_TYPE,case C.EDU_DETAIL when 'GRAD' then 'GRADUATE ' when 'OTH' then 'OTHER ' " +
							" when 'PG' then 'POST GRADUATE ' when 'PRO' then 'PROFESSIONAL ' when 'UG' then 'UNDER GRADUATE ' end as EDU_DETAIL," +
							" dtl.VERIFICATION_TYPE,dtl.VERIFICATION_SUBTYPE,dtl.ENTITY_TYPE,dtl.ENTITY_SUB_TYPE,dtl.ENTITY_DESC," +
							" dtl.EXTERNAL_APPRAISER " +
							" from cr_deal_customer_m c " +
							" left join " +
							" (" +
							" 		SELECT L.DEAL_LOAN_AMOUNT,concat(VERIFICATION_TYPE,' ')VERIFICATION_TYPE,concat(VERIFICATION_SUBTYPE,' ')VERIFICATION_SUBTYPE,case ENTITY_TYPE when 'PRAPPL' then 'APPLICANT ' " +
							" 		when 'COAPPL' then 'COAPPLICANT ' else concat(ENTITY_TYPE,' ') end as ENTITY_TYPE,concat(ENTITY_SUB_TYPE,' ')ENTITY_SUB_TYPE,concat(ENTITY_DESC,' ')ENTITY_DESC,(SELECT concat(AGENCY_NAME,' ') FROM COM_AGENCY_M  WHERE AGENCY_CODE = EXTERNAL_APPRAISER) as EXTERNAL_APPRAISER " +
							" 		FROM cr_deal_verification_dtl V " +
							" 		JOIN CR_DEAL_LOAN_DTL L ON(V.DEAL_ID=L.DEAL_ID)	" +
							" 		WHERE V.DEAL_ID= "+dealId+" and VERIFICATION_ID="+verificationId+" " +
							" )dtl on('a'='a')where CUSTOMER_ID="+entityId+" ";
						break;
			case 'Q' :
						if(dbType.equalsIgnoreCase("MSSQL"))
						{
							subQuery="select CUSTOMER_ID, concat(CUSTOMER_FNAME,' ')CUSTOMER_FNAME,concat(CUSTOMER_MNAME,' ')CUSTOMER_MNAME,concat(CUSTOMER_LNAME,' ')CUSTOMER_LNAME,CASE GENDER WHEN 'F' THEN 'FEMALE ' WHEN 'M' THEN 'MALE ' END AS GENDER,concat(CUSTOMER_CATEGORY,' ')CUSTOMER_CATEGORY,concat(CUSTOMER_CONSTITUTION,' ')CUSTOMER_CONSTITUTION," +
							"concat(FATHER_HUSBAND_NAME,' ')FATHER_HUSBAND_NAME,dbo.DATE_FORMAT(CUSTOMER_DOB,'%d-%m-%Y') as CUSTOMER_DOB,concat(DATEDIFF(year,CUSTOMER_DOB,dbo.STR_TO_DATE('"+p_printed_date+"','%d-%m-%Y %H:%i')),' Years ') as CURRENT_AGE," +
							"CASE CASTE_CATEGORY WHEN 'G' THEN 'General ' WHEN 'OBC' THEN 'Other Backward Cast(OBC) ' WHEN 'SC' THEN 'Scheduled Caste(SC)' WHEN 'ST' THEN 'Schedule tribe(ST)' END AS CASTE_CATEGORY," +
							"case MARITAL_STATUS when 'M' then 'Married ' when 'U' then 'Un-Married ' end as MARITAL_STATUS,concat(CUSTOMER_EMAIL,' ')CUSTOMER_EMAIL,concat(CUSTMER_PAN,' ')CUSTMER_PAN,concat(C.DRIVING_LICENSE,' ')DRIVING_LICENSE,concat(C.VOTER_ID,' ')VOTER_ID, concat(C.PASSPORT_NUMBER,' ')PASSPORT_NUMBER,concat(C.UID_NO,' ')UID_NO,concat(C.OTHER_NO,' ')OTHER_NO," +
							"case OTHOR_RELATIONSHIP_TYPE when 'CS' then 'Customer ' when 'SU' then 'Supplier ' when 'MF' then 'Manufacturer ' end as OTHOR_RELATIONSHIP_TYPE,case C.EDU_DETAIL when 'GRAD' then 'GRADUATE ' when 'OTH' then 'OTHER ' " +
							" when 'PG' then 'POST GRADUATE ' when 'PRO' then 'PROFESSIONAL ' when 'UG' then 'UNDER GRADUATE ' end as EDU_DETAIL," +
							" dtl.VERIFICATION_TYPE,dtl.VERIFICATION_SUBTYPE,dtl.ENTITY_TYPE,dtl.ENTITY_SUB_TYPE,dtl.ENTITY_DESC," +
							" dtl.EXTERNAL_APPRAISER " +
							" from cr_deal_customer_m c " +
							" left join " +
							" (" +
							" 		SELECT L.DEAL_LOAN_AMOUNT,concat(VERIFICATION_TYPE,' ')VERIFICATION_TYPE,concat(VERIFICATION_SUBTYPE,' ')VERIFICATION_SUBTYPE,case ENTITY_TYPE when 'PRAPPL' then 'APPLICANT ' " +
							" 		when 'COAPPL' then 'COAPPLICANT ' else concat(ENTITY_TYPE,' ') end as ENTITY_TYPE,concat(ENTITY_SUB_TYPE,' ')ENTITY_SUB_TYPE,concat(ENTITY_DESC,' ')ENTITY_DESC,(SELECT concat(AGENCY_NAME,' ') FROM COM_AGENCY_M  WHERE AGENCY_CODE = EXTERNAL_APPRAISER) as EXTERNAL_APPRAISER " +
							" 		FROM cr_deal_verification_dtl V " +
							" 		JOIN CR_DEAL_LOAN_DTL L ON(V.DEAL_ID=L.DEAL_ID)	" +
							" 		WHERE V.DEAL_ID= "+dealId+" and VERIFICATION_ID="+verificationId+" " +
							" )dtl on('a'='a')where CUSTOMER_ID="+entityId+" ";
						}
						else
						{
								subQuery="select CUSTOMER_ID, concat(CUSTOMER_FNAME,' ')CUSTOMER_FNAME,concat(CUSTOMER_MNAME,' ')CUSTOMER_MNAME,concat(CUSTOMER_LNAME,' ')CUSTOMER_LNAME,CASE GENDER WHEN 'F' THEN 'FEMALE ' WHEN 'M' THEN 'MALE ' END AS GENDER,concat(CUSTOMER_CATEGORY,' ')CUSTOMER_CATEGORY,concat(CUSTOMER_CONSTITUTION,' ')CUSTOMER_CONSTITUTION," +
										"concat(FATHER_HUSBAND_NAME,' ')FATHER_HUSBAND_NAME,DATE_FORMAT(CUSTOMER_DOB,'%d-%m-%Y') as CUSTOMER_DOB,concat(FLOOR(DATEDIFF(STR_TO_DATE('"+p_printed_date+"','%d-%m-%Y %H:%i'),CUSTOMER_DOB)/365),' Years ') as CURRENT_AGE," +
										"CASE CASTE_CATEGORY WHEN 'G' THEN 'General ' WHEN 'OBC' THEN 'Other Backward Cast(OBC) ' WHEN 'SC' THEN 'Scheduled Caste(SC)' WHEN 'ST' THEN 'Schedule tribe(ST)' END AS CASTE_CATEGORY," +
										"case MARITAL_STATUS when 'M' then 'Married ' when 'U' then 'Un-Married ' end as MARITAL_STATUS,concat(CUSTOMER_EMAIL,' ')CUSTOMER_EMAIL,concat(CUSTMER_PAN,' ')CUSTMER_PAN,concat(C.DRIVING_LICENSE,' ')DRIVING_LICENSE,concat(C.VOTER_ID,' ')VOTER_ID, concat(C.PASSPORT_NUMBER,' ')PASSPORT_NUMBER,concat(C.UID_NO,' ')UID_NO,concat(C.OTHER_NO,' ')OTHER_NO," +
										"case OTHOR_RELATIONSHIP_TYPE when 'CS' then 'Customer ' when 'SU' then 'Supplier ' when 'MF' then 'Manufacturer ' end as OTHOR_RELATIONSHIP_TYPE,case C.EDU_DETAIL when 'GRAD' then 'GRADUATE ' when 'OTH' then 'OTHER ' " +
										" when 'PG' then 'POST GRADUATE ' when 'PRO' then 'PROFESSIONAL ' when 'UG' then 'UNDER GRADUATE ' end as EDU_DETAIL," +
										" dtl.VERIFICATION_TYPE,dtl.VERIFICATION_SUBTYPE,dtl.ENTITY_TYPE,dtl.ENTITY_SUB_TYPE,dtl.ENTITY_DESC," +
										" dtl.EXTERNAL_APPRAISER " +
										" from cr_deal_customer_m c " +
										" left join " +
										" (" +
										" 		SELECT L.DEAL_LOAN_AMOUNT,concat(VERIFICATION_TYPE,' ')VERIFICATION_TYPE,concat(VERIFICATION_SUBTYPE,' ')VERIFICATION_SUBTYPE,case ENTITY_TYPE when 'PRAPPL' then 'APPLICANT ' " +
										" 		when 'COAPPL' then 'COAPPLICANT ' else concat(ENTITY_TYPE,' ') end as ENTITY_TYPE,concat(ENTITY_SUB_TYPE,' ')ENTITY_SUB_TYPE,concat(ENTITY_DESC,' ')ENTITY_DESC,(SELECT concat(AGENCY_NAME,' ') FROM COM_AGENCY_M  WHERE AGENCY_CODE = EXTERNAL_APPRAISER) as EXTERNAL_APPRAISER " +
										" 		FROM cr_deal_verification_dtl V " +
										" 		JOIN CR_DEAL_LOAN_DTL L ON(V.DEAL_ID=L.DEAL_ID)	" +
										" 		WHERE V.DEAL_ID= "+dealId+" and VERIFICATION_ID="+verificationId+" " +
										" )dtl on('a'='a')where CUSTOMER_ID="+entityId+" ";
						}
						break;
						
			case 'R' :
					if(dbType.equalsIgnoreCase("MSSQL"))
					{
						subQuery="select c.CUSTOMER_ID,case CUSTOMER_GROUP_TYPE when 'E' then 'Existing ' else 'New ' end as CUSTOMER_GROUP_TYPE,concat(g.GROUP_DESC,' ')GROUP_DESC,concat(c.CUSTOMER_NAME,' ')CUSTOMER_NAME,concat((select DESCRIPTION FROM  generic_master WHERE GENERIC_KEY='CUST_CATEGORY' and rec_status ='A' and VALUE=c.CUSTOMER_CATEGORY),' ')CUSTOMER_CATEGORY,dbo.DATE_FORMAT(c.CUSTOMER_DOB,'%d-%m-%Y') as DATE_OF_INCORPORATION,concat((select DESCRIPTION FROM  generic_master WHERE GENERIC_KEY='CUST_CONSTITUTION'  and PARENT_VALUE='CORP' and rec_status ='A' and VALUE=c.CUSTOMER_CONSTITUTION),' ')CUSTOMER_CONSTITUTION," +
								"concat(c.CUSTOMER_REGISTRATION_NO,' ')CUSTOMER_REGISTRATION_NO,concat(c.CUSTMER_PAN,' ')CUSTMER_PAN,concat(c.CUSTOMER_VAT_NO,' ')CUSTOMER_VAT_NO,concat((select DESCRIPTION FROM  generic_master WHERE GENERIC_KEY='CUST_BUS_SEGMENT' and rec_status ='A' and VALUE=CUSTOMER_BUSINESS_SEGMENT),' ')CUSTOMER_BUSINESS_SEGMENT,concat(i.INDUSTRY_DESC,' ')INDUSTRY_DESC,concat(si.SUB_INDUSTRY_DESC,' ')SUB_INDUSTRY_DESC,concat(c.CUSTOMER_EMAIL,' ')CUSTOMER_EMAIL,concat(c.CUSTOMER_WEBSITE,' ')CUSTOMER_WEBSITE," +
								"concat((select DESCRIPTION FROM  generic_master WHERE GENERIC_KEY='NATURE_OF_BUSINESS' and rec_status ='A' and VALUE=c.NATURE_OF_BUSINESS),' ')NATURE_OF_BUSINESS,concat(c.YEAR_OF_ESTBLISHMENT,' ')YEAR_OF_ESTBLISHMENT,concat(c.SHOP_ESTABLISHMENT_NO,' ')SHOP_ESTABLISHMENT_NO,concat(c.SALES_TAX_TIN_NO,' ')SALES_TAX_TIN_NO,concat(c.DGFT_NO,' ')DGFT_NO," +
								"concat(c.NO_BV_YEARS,' Year ',c.NO_BV_MONTHS,' Month ')as BUSINESS_VINTAGE,case OTHOR_RELATIONSHIP_TYPE when 'CS' then 'Customer ' when 'SU' then 'Supplier ' when 'MF' then 'Manufacturer ' end as OTHOR_RELATIONSHIP_TYPE,c.CUSTOMER_REFERENCE," +
								"dtl.VERIFICATION_TYPE,dtl.VERIFICATION_SUBTYPE,dtl.ENTITY_TYPE,dtl.ENTITY_SUB_TYPE,dtl.ENTITY_DESC,dtl.EXTERNAL_APPRAISER  " +
								"from cr_deal_customer_m c " +
								"left join gcd_group_m g on c.CUSTOMER_GROUP_ID=g.GROUP_ID " +
								"inner join com_industry_m i on c.CUSTOMER_INDUSTRY=i.INDUSTRY_ID " +
								"inner join com_sub_industry_m si on si.sub_industry_id=c.CUSTOMER_SUB_INDUSTRY " +
								" left join " +
								" (" +
								" 		SELECT L.DEAL_LOAN_AMOUNT,concat(VERIFICATION_TYPE,' ')VERIFICATION_TYPE,concat(VERIFICATION_SUBTYPE,' ')VERIFICATION_SUBTYPE,case ENTITY_TYPE when 'PRAPPL' then 'APPLICANT ' " +
								" 		when 'COAPPL' then 'COAPPLICANT ' else concat(ENTITY_TYPE,' ') end as ENTITY_TYPE,concat(ENTITY_SUB_TYPE,' ')ENTITY_SUB_TYPE,concat(ENTITY_DESC,' ')ENTITY_DESC,(SELECT concat(AGENCY_NAME,' ') FROM COM_AGENCY_M  WHERE AGENCY_CODE = EXTERNAL_APPRAISER) as EXTERNAL_APPRAISER " +
								" 		FROM cr_deal_verification_dtl V " +
								" 		JOIN CR_DEAL_LOAN_DTL L ON(V.DEAL_ID=L.DEAL_ID)	" +
								" 		WHERE V.DEAL_ID= "+dealId+" and VERIFICATION_ID="+verificationId+" " +
								" )dtl on('a'='a')where CUSTOMER_ID="+entityId+" ";
					}
					else
					{
						subQuery="select c.CUSTOMER_ID,case CUSTOMER_GROUP_TYPE when 'E' then 'Existing ' else 'New ' end as CUSTOMER_GROUP_TYPE,concat(g.GROUP_DESC,' ')GROUP_DESC,concat(c.CUSTOMER_NAME,' ')CUSTOMER_NAME,concat((select DESCRIPTION FROM  generic_master WHERE GENERIC_KEY='CUST_CATEGORY' and rec_status ='A' and VALUE=c.CUSTOMER_CATEGORY),' ')CUSTOMER_CATEGORY,DATE_FORMAT(c.CUSTOMER_DOB,'%d-%m-%Y') as DATE_OF_INCORPORATION,concat((select DESCRIPTION FROM  generic_master WHERE GENERIC_KEY='CUST_CONSTITUTION'  and PARENT_VALUE='CORP' and rec_status ='A' and VALUE=c.CUSTOMER_CONSTITUTION),' ')CUSTOMER_CONSTITUTION," +
						"concat(c.CUSTOMER_REGISTRATION_NO,' ')CUSTOMER_REGISTRATION_NO,concat(c.CUSTMER_PAN,' ')CUSTMER_PAN,concat(c.CUSTOMER_VAT_NO,' ')CUSTOMER_VAT_NO,concat((select DESCRIPTION FROM  generic_master WHERE GENERIC_KEY='CUST_BUS_SEGMENT' and rec_status ='A' and VALUE=CUSTOMER_BUSINESS_SEGMENT),' ')CUSTOMER_BUSINESS_SEGMENT,concat(i.INDUSTRY_DESC,' ')INDUSTRY_DESC,concat(si.SUB_INDUSTRY_DESC,' ')SUB_INDUSTRY_DESC,concat(c.CUSTOMER_EMAIL,' ')CUSTOMER_EMAIL,concat(c.CUSTOMER_WEBSITE,' ')CUSTOMER_WEBSITE," +
						"concat((select DESCRIPTION FROM  generic_master WHERE GENERIC_KEY='NATURE_OF_BUSINESS' and rec_status ='A' and VALUE=c.NATURE_OF_BUSINESS),' ')NATURE_OF_BUSINESS,concat(c.YEAR_OF_ESTBLISHMENT,' ')YEAR_OF_ESTBLISHMENT,concat(c.SHOP_ESTABLISHMENT_NO,' ')SHOP_ESTABLISHMENT_NO,concat(c.SALES_TAX_TIN_NO,' ')SALES_TAX_TIN_NO,concat(c.DGFT_NO,' ')DGFT_NO," +
						"concat(c.NO_BV_YEARS,' Year ',c.NO_BV_MONTHS,' Month ')as BUSINESS_VINTAGE,case OTHOR_RELATIONSHIP_TYPE when 'CS' then 'Customer ' when 'SU' then 'Supplier ' when 'MF' then 'Manufacturer ' end as OTHOR_RELATIONSHIP_TYPE,c.CUSTOMER_REFERENCE," +
						"dtl.VERIFICATION_TYPE,dtl.VERIFICATION_SUBTYPE,dtl.ENTITY_TYPE,dtl.ENTITY_SUB_TYPE,dtl.ENTITY_DESC,dtl.EXTERNAL_APPRAISER  " +
						"from cr_deal_customer_m c " +
						"left join gcd_group_m g on c.CUSTOMER_GROUP_ID=g.GROUP_ID " +
						"inner join com_industry_m i on c.CUSTOMER_INDUSTRY=i.INDUSTRY_ID " +
						"inner join com_sub_industry_m si on si.sub_industry_id=c.CUSTOMER_SUB_INDUSTRY " +
						" left join " +
						" (" +
						" 		SELECT L.DEAL_LOAN_AMOUNT,concat(VERIFICATION_TYPE,' ')VERIFICATION_TYPE,concat(VERIFICATION_SUBTYPE,' ')VERIFICATION_SUBTYPE,case ENTITY_TYPE when 'PRAPPL' then 'APPLICANT ' " +
						" 		when 'COAPPL' then 'COAPPLICANT ' else concat(ENTITY_TYPE,' ') end as ENTITY_TYPE,concat(ENTITY_SUB_TYPE,' ')ENTITY_SUB_TYPE,concat(ENTITY_DESC,' ')ENTITY_DESC,(SELECT concat(AGENCY_NAME,' ') FROM COM_AGENCY_M  WHERE AGENCY_CODE = EXTERNAL_APPRAISER) as EXTERNAL_APPRAISER " +
						" 		FROM cr_deal_verification_dtl V " +
						" 		JOIN CR_DEAL_LOAN_DTL L ON(V.DEAL_ID=L.DEAL_ID)	" +
						" 		WHERE V.DEAL_ID= "+dealId+" and VERIFICATION_ID="+verificationId+" " +
						" )dtl on('a'='a')where CUSTOMER_ID="+entityId+" ";
					}
						break;
			case 'P': 
						{
							String collectedDocsQ = "select DISTINCT DOC_CHILD_IDS from cr_document_dtl where TXN_DOC_ID= '"+entityId+"' ";
							logger.info("In downloadVarificationForm(String txnDocId, String txnId,String txnType) query AT CP: "+ collectedDocsQ);
						    String collectedDocs = CommonFunction.checkNull(ConnectionDAO.singleReturn(collectedDocsQ)).trim();
						    collectedDocsQ=null;
						    String coll="";
						    if(CommonFunction.checkNull(collectedDocs).trim().length()!=0)
						    {
						    	String[] collects = collectedDocs.split("\\|");								
						    	for (int i = 0; i < collects.length; i++) 
								coll+=","+collects[i];
						    }
							coll=coll+",0";
							String docChildId= coll.substring(1, coll.length());
							subQuery="select case when (DOC_CHILD_ID is null) then 'N' else 'Y' end as present,DOC_CHILD_ID,concat(DOC_DESC,' ')DOC_DESC, " +
									 " dtl.VERIFICATION_TYPE,dtl.VERIFICATION_SUBTYPE,dtl.ENTITY_TYPE,dtl.ENTITY_SUB_TYPE,dtl.ENTITY_DESC," +
									 " dtl.EXTERNAL_APPRAISER " +
									 "FROM " +
									 " (" +
									 " 		SELECT L.DEAL_LOAN_AMOUNT,concat(VERIFICATION_TYPE,' ')VERIFICATION_TYPE,concat(VERIFICATION_SUBTYPE,' ')VERIFICATION_SUBTYPE,case ENTITY_TYPE when 'PRAPPL' then 'APPLICANT ' " +
									 " 		when 'COAPPL' then 'COAPPLICANT ' else concat(ENTITY_TYPE,' ') end as ENTITY_TYPE,concat(ENTITY_SUB_TYPE,' ')ENTITY_SUB_TYPE,concat(ENTITY_DESC,' ')ENTITY_DESC,(SELECT concat(AGENCY_NAME,' ') FROM COM_AGENCY_M  WHERE AGENCY_CODE = EXTERNAL_APPRAISER) as EXTERNAL_APPRAISER " +
									 " 		FROM cr_deal_verification_dtl V " +
									 " 		JOIN CR_DEAL_LOAN_DTL L ON(V.DEAL_ID=L.DEAL_ID)	" +
									 " 		WHERE V.DEAL_ID= "+dealId+" and VERIFICATION_ID="+verificationId+" " +
									 " )dtl " +
									 "left join CR_DOCUMENT_CHILD_M a on (1=1 and DOC_CHILD_ID IN ("+docChildId+") )";
					}
					
		}//end of switch
		
		logger.info("query            :  "+query);
		logger.info("subQuery         :  "+subQuery);
		Connection connectDatabase = ConnectionDAO.getConnection();		
		Map<Object,Object> hashMap = new HashMap<Object,Object>();
		hashMap.put("query",query);
		hashMap.put("subQuery",subQuery);
		hashMap.put("root","Deal");
		hashMap.put("p_company_logo",p_company_logo);
		hashMap.put("p_printed_by",p_printed_by);
		hashMap.put("p_printed_date",p_printed_date);
		hashMap.put("p_company_name",p_company_name);
		hashMap.put("checkImage",checkImage);
		hashMap.put("uncheckImage",uncheckImage);
		hashMap.put("SUBREPORT_DIR",SUBREPORT_DIR);
		hashMap.put("source",sou);
		hashMap.put("IS_IGNORE_PAGINATION",true);
		
		logger.info("entityId         :  "+entityId);
		logger.info("dealId           :  "+dealId);
		logger.info("verificationId   :  "+verificationId);
		logger.info("p_company_logo   :  "+p_company_logo);
		logger.info("p_printed_by     :  "+p_printed_by);
		logger.info("p_printed_date   :  "+p_printed_date);
		logger.info("p_company_name   :  "+p_company_name);
		logger.info("uncheckImage     :  "+uncheckImage);
		logger.info("SUBREPORT_DIR    :  "+SUBREPORT_DIR);
		logger.info("source           :  "+sou);	
		
							
		
		InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath+reportName+".jasper");
		JasperPrint jasperPrint = null;
		try
		{
			jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
			JasperExportManager.exportReportToPdfFile(jasperPrint,request.getRealPath("/reports") + "/" +reportName+".pdf");
			File f=new File(request.getRealPath("/reports") + "/" +reportName+".pdf");
			FileInputStream fin = new FileInputStream(f);
			ServletOutputStream outStream = response.getOutputStream();
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment;filename='"+reportName+"'.pdf");
			byte[] buffer = new byte[1024];
			int n = 0;
			while ((n = fin.read(buffer)) != -1) 
				outStream.write(buffer, 0, n);			
			outStream.flush();
			fin.close();
			outStream.close();
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			ConnectionDAO.closeConnection(connectDatabase, null);
			hashMap.clear();
			
		}
		return null;				
		
	}

	

	public ActionForward searchFieldCapture(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {

		logger.info("In searchVerificationCapture(searchFieldCapture).....");
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in searchFieldCapture method of fieldVerificationDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		FieldVerificationVo vo = new FieldVerificationVo();

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

		DynaValidatorForm CommonDealDynaValidatorForm = (DynaValidatorForm) form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,CommonDealDynaValidatorForm);

		String userName=request.getParameter("userId");
		vo.setUserName(userName);
		if(userName.trim().length()==0)
		{
			vo.setUserName(userobj.getUserName());
		}
		ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
		String dateFormat = resource.getString("lbl.dateFormat(dd-mm-yyyy)");
		
		String userId=vo.getReportingToUserId();
		if(userobj != null)
		{
			vo.setBranchId(userobj.getBranchId());
			if(userId.trim().length()==0)
				userId=userobj.getUserId();				
		}
		vo.setUserId(userId);
		
		if (vo.getApplicationDate().equalsIgnoreCase(dateFormat))
			vo.setApplicationDate("");
		
		FieldVerificationDAO fieldDao=(FieldVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(FieldVerificationDAO.IDENTITY);
        logger.info("Implementation class: "+fieldDao.getClass()); 	// changed by asesh	
		ArrayList capturedetails=new ArrayList();		
		logger.info("current page link .......... "+request.getParameter("d-49520-p"));		
		int currentPageLink = 0;
		if(request.getParameter("d-49520-p")==null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
			currentPageLink=1;
		else
			currentPageLink =Integer.parseInt(request.getParameter("d-49520-p"));
		logger.info("current page link ................ "+request.getParameter("d-49520-p"));
		vo.setCurrentPageLink(currentPageLink);
		String functionId="";
		String ID="";
		if(session.getAttribute("functionId")!=null)
		{
			functionId=session.getAttribute("functionId").toString();
		}
		logger.info("functionId: "+functionId);
		vo.setFunctionId(functionId);
		capturedetails= fieldDao.searchDealForCapture(vo);
		logger.info("In searchDealCapturing....list: "+capturedetails.size());
		logger.info("list.isEmpty(): "+capturedetails.size());
		request.setAttribute("defaultlist", capturedetails);		
		return mapping.findForward("captureSearch");
	}
	
	
	public ActionForward searchFieldComCapture(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception 
	{
		logger.info("In searchVerificationCompletion(searchFieldComCapture).....");
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String branchId="";
		if(userobj != null){
			branchId=userobj.getBranchId();
			userId=userobj.getUserId();
		}else{
			logger.info("here in searchFieldComCapture method of fieldVerificationDispatchAction action the session is out----------------");
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
		DynaValidatorForm CommonDealDynaValidatorForm = (DynaValidatorForm) form;
		ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
		String dateFormat = resource.getString("lbl.dateFormat(dd-mm-yyyy)");
		FieldVerificationVo vo = new FieldVerificationVo();	
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,CommonDealDynaValidatorForm);
		
		
	
		vo.setUserId(userId);
		vo.setBranchId(branchId);
		if (vo.getApplicationDate().equalsIgnoreCase(dateFormat))
		{
			vo.setApplicationDate("");
		}
		FieldVerificationDAO fieldDao=(FieldVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(FieldVerificationDAO.IDENTITY);
        logger.info("Implementation class: "+fieldDao.getClass()); 	// changed by asesh
		ArrayList capturedetails=new ArrayList();		
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
		String functionId="";
		if(session.getAttribute("functionId")!=null)
		{
			functionId=session.getAttribute("functionId").toString();
		}
		logger.info("functionId: "+functionId);
		vo.setFunctionId(functionId);
		capturedetails= fieldDao.searchDealForComCapture(vo, request);		
	    logger.info("In searchDealCapturing....list: "+capturedetails.size());		
	   	logger.info("list.isEmpty(): "+capturedetails.size());
		request.setAttribute("list", capturedetails);
		request.setAttribute("search","search");
		
		return mapping.findForward("search_complet");
	}	

	public ActionForward addFieldComCapture(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		
		
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		String userID="";
		
		if(userobj!=null)
		{
			userID=userobj.getUserId();
				
		}else{
			logger.info("here in addFieldComCapture method of fieldVerificationDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		FieldVerificationVo vo=new FieldVerificationVo(); 		
		DynaValidatorForm FieldVarificationDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, FieldVarificationDynaValidatorForm);					
	
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
		FieldVerificationDAO dao=(FieldVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(FieldVerificationDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass()); 	// changed by asesh
		//FieldVerificationDAO dao=new FieldVerificationDAOImpl();	
		String dealId = "";				
		dealId = CommonFunction.checkNull(request.getParameter("dealId"));
		
		if(session.getAttribute("dealId")!=null)
		{
			dealId=session.getAttribute("dealId").toString();
		}
		
		if(dealId!=null && !dealId.equalsIgnoreCase(""))
		{
			CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
	        logger.info("Implementation class: "+service.getClass()); 	//changed by asesh
			//CreditProcessingDAO service=new CreditProcessingDAOImpl();
			ArrayList dealHeader = service.getDealHeader(dealId);
			session.setAttribute("dealHeader", dealHeader);
			String functionId="";
			String VerificationCapId="";
			String ID="";
			if(session.getAttribute("functionId")!=null)
			{
				functionId=session.getAttribute("functionId").toString();
			}
			logger.info("functionId: "+functionId);
			
			ArrayList completionList = dao.getCompletionList(dealId,functionId);
			Iterator<FieldVerificationVo> it= completionList.iterator();
			String flag="";
			while(it.hasNext())
			{
				FieldVerificationVo  vo1 = (FieldVerificationVo) it.next();
				logger.info("vo1.getVerificaionType()---"+vo1.getVerificationType());
				session.setAttribute("verificationType", vo1.getVerificationType());
				logger.info("vo1.getEntitySubType()---"+vo1.getEntitySubType());
				session.setAttribute("entitySubType", vo1.getEntitySubType());
				
				ID=vo1.getFieldVerificationUniqueId();
				if(!CommonFunction.checkNull(VerificationCapId).equalsIgnoreCase(""))
				VerificationCapId=VerificationCapId+ID+'/';
				else
					VerificationCapId=ID+'/';
				
			
			}
			if(functionId.equalsIgnoreCase("8000303") ||functionId.equalsIgnoreCase("8000306") || functionId.equalsIgnoreCase("10000831")  || functionId.equalsIgnoreCase("3000226")){
				session.setAttribute("Verif", "Verif");
				if(CommonFunction.checkNull(flag).equalsIgnoreCase("N")){
					
				session.setAttribute("verifFlag", "verifFlag");
			//	session.setAttribute("viewDeal", "viewDeal");
				}
			else{
				
				session.removeAttribute("verifFlag");
			//	session.removeAttribute("viewDeal");
			}
			}else{
				session.removeAttribute("Verif");
			}
			session.setAttribute("VerificationCapId", VerificationCapId);			
			session.setAttribute("completionList", completionList);				
			session.setAttribute("dealId", dealId);
			
			
		}
		    return mapping.findForward("newFieldCompletion");	
	}
	
	public ActionForward viewCompletionTab(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		
		
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		String userID="";
		
		if(userobj!=null)
		{
			userID=userobj.getUserId();
				
		}else{
			logger.info("here in addFieldComCapture method of fieldVerificationDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		FieldVerificationVo vo=new FieldVerificationVo(); 		
		DynaValidatorForm FieldVarificationDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, FieldVarificationDynaValidatorForm);					
	
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
		String decision=request.getParameter("decision");
		logger.info("decision: "+decision);
		FieldVerificationDAO dao=(FieldVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(FieldVerificationDAO.IDENTITY);
		CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass()); 	// changed by asesh	
		String dealId = "";				
		dealId = CommonFunction.checkNull(request.getParameter("dealId"));
		String bpType=CommonFunction.checkNull(request.getParameter("bpType"));
		if(bpType!="")
		{
			session.setAttribute("bpType", bpType);
		}
		else
		{
			bpType=CommonFunction.checkNull((String)session.getAttribute("bpType"));
		}
		
		dealId = CommonFunction.checkNull(request.getParameter("dealId"));
		if(CommonFunction.checkNull(dealId).equalsIgnoreCase("")){
			dealId = CommonFunction.checkNull(session.getAttribute("dealId").toString());
		}
		String functionId="";
		
	        	ArrayList dealHeader = service.getDealHeader(dealId);
	        	session.setAttribute("dealHeader", dealHeader);
	      
		if(session.getAttribute("functionId")!=null)
		{
			functionId=session.getAttribute("functionId").toString();
		}
		logger.info("functionId: "+functionId);
		if(session.getAttribute("dealId")!=null)
		{
			dealId=session.getAttribute("dealId").toString();
		}
		
		if(dealId!=null && !dealId.equalsIgnoreCase(""))
		{
		
			if(functionId.equalsIgnoreCase("8000303") ||functionId.equalsIgnoreCase("8000306") || functionId.equalsIgnoreCase("10000831")  || functionId.equalsIgnoreCase("3000226")){
				session.setAttribute("Verif", "Verif");
			}else{
				session.removeAttribute("Verif");
			}
			ArrayList completionList = dao.getCompletionList(dealId,functionId);
			session.setAttribute("completionList", completionList);
			if(CommonFunction.checkNull(decision).equalsIgnoreCase("S"))
			{
				request.setAttribute("decision", "S");	
			}
			
			session.setAttribute("verifCPS", "verifCPS");
			session.removeAttribute("verifCMS");
			session.setAttribute("dealId", dealId);
			
			
		}
		    return mapping.findForward("viewCompletionTab");	
	}
	public ActionForward completionAuthorVerification(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{		
		logger.info("open--jsp--completionAuthorVerification........... ");
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in completionAuthorVerification method of fieldVerificationDispatchAction action the session is out----------------");
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
		
		return mapping.findForward("completionAuthorVerification");	
	}
	
	public ActionForward openPopupViewCapturedVerif(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		logger.info(" in openPopupViewCapturedVerif......()");
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in openPopupViewCapturedVerif method of fieldVerificationDispatchAction action the session is out----------------");
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
		
		
			String fieldVerificationUniqueId=CommonFunction.checkNull(request.getParameter("fieldVerificationId"));
			if(session.getAttribute("fieldVerificationUniqueId")!=null)
			{
				session.removeAttribute("fieldVerificationUniqueId");
				session.setAttribute("fieldVerificationUniqueId", fieldVerificationUniqueId);
			}
			else
			{
				session.setAttribute("fieldVerificationUniqueId", fieldVerificationUniqueId);
			}
			
			String functionId="";
			
			if(session.getAttribute("functionId")!=null)
			{
				functionId=session.getAttribute("functionId").toString();
			}
			logger.info("fieldVerificationUniqueId: "+fieldVerificationUniqueId);
			String dealId=(String)session.getAttribute("dealId");
			String bpType=CommonFunction.checkNull(session.getAttribute("bpType"));									
			FieldVerificationDAO dao=(FieldVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(FieldVerificationDAO.IDENTITY);
	        logger.info("Implementation class: "+dao.getClass()); 	// changed by asesh	
			ArrayList verifMethodList = dao.getVerifMethodListList();
			request.setAttribute("verifMethodList", verifMethodList);
			ArrayList commonList = dao.getViewCommonListList(dealId,fieldVerificationUniqueId);
			String entityId = CommonFunction.checkNull(((FieldVerificationVo)(commonList.get(0))).getEntId());
			session.setAttribute("entityId", entityId);
			request.setAttribute("commonList", commonList);
			ArrayList questList = dao.getViewQuestList(dealId,fieldVerificationUniqueId);
			request.setAttribute("questList", questList);
			request.setAttribute("viewDeal", "viewDeal");
			request.setAttribute("functionId", functionId);
			// Sanjog 
			CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
	        logger.info("Implementation class: "+service.getClass()); 
	      
	      
	        	ArrayList dealHeader = service.getDealHeader(dealId);
	        	session.setAttribute("dealHeader", dealHeader);
	        			        
	        //amandeep changes for RCU DOCS

	        String verificationType = CommonFunction.checkNull(((FieldVerificationVo)(commonList.get(0))).getVerificationType());
	        logger.info("verificationType:::::"+verificationType);
	        if(CommonFunction.checkNull(verificationType).equalsIgnoreCase("TECHNICAL VERIFICATION")){
	        	request.setAttribute("verification", "Technical Verification Completion");
	        }else if(CommonFunction.checkNull(verificationType).equalsIgnoreCase("RCU REPORT")){
	        	request.setAttribute("verification", "RCU Verification Completion");
	        	
	        }else if(CommonFunction.checkNull(verificationType).equalsIgnoreCase("LEGAL VERIFICATION")){
	        	request.setAttribute("verification", "Legal Verification Completion");
	        	
	        }else{
	        	request.setAttribute("verification", "Verification Completion");
	        }
	        //Rohit Chnages Starts
		String verificationSubType = CommonFunction.checkNull(((FieldVerificationVo)(commonList.get(0))).getVerificationSubType());
		String entitySubType = CommonFunction.checkNull(((FieldVerificationVo)(commonList.get(0))).getEntitySubType());
			logger.info("verificationType:::::"+verificationSubType);
			request.setAttribute("verificationType", verificationType);
			request.setAttribute("verificationSubType", verificationSubType);
	        //rohit end
			request.setAttribute("entitySubType", entitySubType);
	        ArrayList verifMethodListRCU=new ArrayList();
	        if(verificationType.contains("RCU REPORT"))
	        {
	        	 request.setAttribute("RCUDisabled", "RCUDisabled");
	        	ArrayList RCUDocsAfterSave=dao.getReceivedDocsSaved(dealId,bpType);
	        	 if (RCUDocsAfterSave.size()>0)
	        	 {
	        		 verifMethodListRCU = dao.getVerifMethodListRCU();
					 if(verifMethodListRCU.size()>0)
					 {
						 request.setAttribute("verifMethodListRCU", verifMethodListRCU);
					 }
					 request.setAttribute("RCUDocs", RCUDocsAfterSave);
	        	 }
	        }
	        //amandeep changes end for RCU DOCS
			ArrayList uploadedDocList=new ArrayList();
			String stage = "FVI";
			
		//	String functionId=(String)session.getAttribute("functionId");
			if(CommonFunction.checkNull(stage).equalsIgnoreCase("FVI") || CommonFunction.checkNull(stage).equalsIgnoreCase("FVILM")){
				if(CommonFunction.checkNull(verificationType).equalsIgnoreCase("RCU REPORT")  ){
					stage="RVI";
					
				}
				else if(CommonFunction.checkNull(verificationType).equalsIgnoreCase("TECHNICAL VERIFICATION")  ){
					stage="TVI";
				}else if(CommonFunction.checkNull(verificationType).equalsIgnoreCase("LEGAL VERIFICATION")){
					stage="LVI";
				}else{
					stage = "FVI";
				}
				}
			uploadedDocList = service.getUploadDocForFVC(fieldVerificationUniqueId,stage, dealId);
			request.setAttribute("verificationType",verificationType );
			request.setAttribute("uploadedDocList", uploadedDocList);
			request.setAttribute("stage", "FVI");
			return mapping.findForward("openPopupViewCapturedVerif");	
		
}
	
	public ActionForward updateFieldCheck(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info(" in updateFieldCheck.....updateStatus()");
		HttpSession session=request.getSession(false);
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
			logger.info("here in updateFieldCheck method of fieldVerificationDispatchAction action the session is out----------------");
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
		
		FieldVerificationVo vo = new FieldVerificationVo();
		
		DynaValidatorForm FieldVarificationDynaValidatorForm = (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, FieldVarificationDynaValidatorForm);
		
		String dealId=(String)session.getAttribute("dealId");
		logger.info("dealId:----------------------"+dealId);
		vo.setMakerId(userId);
		vo.setMakerDate(bDate);
		vo.setDealId(dealId);
		boolean status=false;
		FieldVerificationDAO fieldDao=(FieldVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(FieldVerificationDAO.IDENTITY);
        logger.info("Implementation class: "+fieldDao.getClass()); 	// changed by asesh
    	String functionId="";
		String ID="";
		if(session.getAttribute("functionId")!=null)
		{
			functionId=session.getAttribute("functionId").toString();
		}
		logger.info("functionId: "+functionId);
		vo.setFunctionId(functionId);
		String checkStageM="S";
		if(!CommonFunction.checkNull(vo.getFunctionId()).equalsIgnoreCase("10000831")){
		   checkStageM=CommonFunction.stageMovement(companyId, "DC","F",dealId, "FVI", bDate,userId);
		  logger.info("checkStageM : "+checkStageM);
		}
		  if(checkStageM.equalsIgnoreCase("S")){
			  status = fieldDao.completionAuthorVerf(vo);
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
		  } else{
			  request.setAttribute("checkStageM", checkStageM);
		  }
		    if(status && !CommonFunction.checkNull(vo.getDecison()).equalsIgnoreCase("S")){
		    	
		    	request.setAttribute("sms", "S");
		    }
		    else if(status && CommonFunction.checkNull(vo.getDecison()).equalsIgnoreCase("S")){
		    	
		    	request.setAttribute("sms", "SB");
		    }
       
        else
        {
        	request.setAttribute("sms", "N");
        }
		
        return mapping.findForward("saveFieldRemarks");
	}
	
	public ActionForward addFieldCaptureAtCM(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		
		
		HttpSession session = request.getSession();

		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		String userID="";
		
		if(userobj!=null)
		{
			userID=userobj.getUserId();
				
		}else{
			logger.info("here in addFieldCaptureAtCM method of fieldVerificationDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		FieldVerificationVo vo=new FieldVerificationVo(); 		
		DynaValidatorForm FieldVarificationDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, FieldVarificationDynaValidatorForm);					
	
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
		String functionId=(String)session.getAttribute("functionId");
		request.setAttribute("functionId", functionId);
		FieldVerificationDAO dao=(FieldVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(FieldVerificationDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass()); 	// changed by asesh
		//FieldVerificationDAO dao=new FieldVerificationDAOImpl();	
		String loanId = null;	
//		String stage = request.getParameter("txntype");
		loanId = CommonFunction.checkNull(request.getParameter("loanId"));
		if(CommonFunction.checkNull(loanId).trim().equalsIgnoreCase(""))
			loanId=(String)session.getAttribute("loanId");
		else
			session.removeAttribute("loanId");
		
		String entityId = CommonFunction.checkNull(request.getParameter("entityId"));
		if(CommonFunction.checkNull(entityId).trim().equalsIgnoreCase(""))
			entityId=(String)session.getAttribute("entityId");
		else
			session.removeAttribute("entityId");		
		String verificationId= CommonFunction.checkNull(request.getParameter("verificationId"));
		if(CommonFunction.checkNull(verificationId).trim().equalsIgnoreCase(""))
			verificationId=(String)session.getAttribute("verificationId");
		else
			session.removeAttribute("verificationId");
				
		if(loanId!=null && !loanId.equalsIgnoreCase("") && !CommonFunction.checkNull(verificationId).equalsIgnoreCase(""))
		{
			CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
	        logger.info("Implementation class: "+service.getClass()); 	//changed by asesh
	        
	        LoanInitiationDAO daoheader=(LoanInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(LoanInitiationDAO.IDENTITY);
	        logger.info("Implementation class: "+service.getClass()); 
			//CreditProcessingDAO service=new CreditProcessingDAOImpl();
			ArrayList loanHeader = daoheader.getLoanHeader(loanId);
			session.setAttribute("loanHeader", loanHeader);
			
			String appraiserType = service.getAppraiserTypeAtCM(loanId,verificationId);
			if(CommonFunction.checkNull(appraiserType).trim().equalsIgnoreCase("EXTERNAL"))
				request.setAttribute("appraiserType", appraiserType); 
			
			ArrayList verifList = dao.getVerifListAtCM(loanId,entityId,verificationId);
			request.setAttribute("verifList", verifList);
			ArrayList commonList = dao.getCommonListListAtCM(loanId,entityId,verificationId);
			request.setAttribute("commonList", commonList);
			ArrayList questList = dao.getQuestListAtCM(loanId,entityId,verificationId);
			request.setAttribute("questList", questList);
			ArrayList verifMethodList = dao.getVerifMethodListList();
			request.setAttribute("verifMethodList", verifMethodList);
			// Remove at Behind action(FieldVerificationCapturingBehindAction)
			session.setAttribute("entityId", entityId);
			session.setAttribute("loanId", loanId);
			session.setAttribute("verificationId", verificationId);
		//Rohit Chnages Starts
			String verificationType= CommonFunction.checkNull(((FieldVerificationVo)(verifList.get(0))).getVerificationType());
			String verificationSubType = CommonFunction.checkNull(((FieldVerificationVo)(verifList.get(0))).getVerificationSubType());
			String entitySubType = CommonFunction.checkNull(((FieldVerificationVo)(verifList.get(0))).getEntitySubType());
				logger.info("verificationType:::::"+verificationSubType);
				request.setAttribute("verificationType", verificationType);
				request.setAttribute("verificationSubType", verificationSubType);
				request.setAttribute("entitySubType", entitySubType);
		        //rohit end
				if(verificationType.contains("RCU REPORT")){
					request.setAttribute("RCUDisabled", "RCUDisabled");
				ArrayList RCUDocs = dao.getReceivedDocsAtLoan(loanId,"LIM");
				ArrayList RCUDocsAfterSave=dao.getReceivedDocsSavedAtLoan(loanId,"LIM");
				//String id = CommonFunction.checkNull(((FieldVerificationVo)(RCUDocsAfterSave.get(0))).getId());
				//String name = CommonFunction.checkNull(((FieldVerificationVo)(RCUDocsAfterSave.get(0))).getName());
				ArrayList verifMethodListRCU=new ArrayList();
				
				if(RCUDocs.size()>0 && commonList.size()==0 )
				{
					request.setAttribute("RCUDocs", RCUDocs);
					 verifMethodListRCU = dao.getVerifMethodListRCU();
					if(verifMethodListRCU.size()>0)
					{
						request.setAttribute("verifMethodListRCU", verifMethodListRCU);
					}
				 }
				else if (RCUDocsAfterSave.size()>0 && commonList.size()>0)
				{
					verifMethodListRCU = dao.getVerifMethodListRCU();
					 if(verifMethodListRCU.size()>0)
					 {
						 request.setAttribute("verifMethodListRCU", verifMethodListRCU);
					 }
					 request.setAttribute("RCUDocs", RCUDocsAfterSave);
				}
			
				}	
			// Sanjog 
			/*ArrayList uploadedDocList=new ArrayList();
			uploadedDocList = service.getUploadDocForFVC(loanId,"FVILM");
			request.setAttribute("uploadedDocList", uploadedDocList);*/
			service=null;
			daoheader=null;
			
		}
			dao=null;	
		    return mapping.findForward("newFieldCapture");	
	}

     public ActionForward saveFieldVarCapAtCM(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
     {
			logger.info(" in saveFieldVarCapAtCM......()");	
			HttpSession session = request.getSession();
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			Object sessionId = session.getAttribute("sessionID");
			String userId="";
			String businessDate="";
			if(userobj !=null)
			{
				userId=userobj.getUserId();
				businessDate =userobj.getBusinessdate();
			}else{
				logger.info("here in saveFieldVarCapAtCM method of fieldVerificationDispatchAction action the session is out----------------");
				return mapping.findForward("sessionOut");
			}
			String loanId=CommonFunction.checkNull(session.getAttribute("loanId"));
			FieldVerificationVo vo=new FieldVerificationVo(); 		
			DynaValidatorForm formbeen= (DynaValidatorForm)form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(vo, formbeen);
			
			FieldVerificationDAO fieldDao=(FieldVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(FieldVerificationDAO.IDENTITY);
			logger.info("Implementation class: "+fieldDao.getClass()); 	// changed by asesh
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
			String verificationId=null;
			if(session.getAttribute("verificationId")!=null)
			{
				verificationId=CommonFunction.checkNull(session.getAttribute("verificationId"));
			}
			StringBuilder dealIdQuery=new StringBuilder();
			
		        if(dbType.equalsIgnoreCase("MSSQL"))
		        {
		        	dealIdQuery.append("select top 1 LOAN_DEAL_ID from cr_loan_dtl where loan_id='"+loanId+"' ");
		        }
		        else
		        {
		        	dealIdQuery.append("select LOAN_DEAL_ID from cr_loan_dtl where loan_id='"+loanId+"' limit 1");
		        }
				session.removeAttribute("viewDeal");
		    String dealId=ConnectionDAO.singleReturn(dealIdQuery.toString());
	        vo.setLbxLoanNoHID(loanId);
			vo.setDealId(dealId);
			vo.setMakerId(userId);
			vo.setMakerDate(businessDate);
			vo.setVerificationStatus("P");
			vo.setVerificationId((CommonFunction.checkNull(verificationId)).trim());
			boolean status=false;
			int fieldVerificationUniqueId=0;
			boolean questionStatus=false;
			//amandeep start for RCU
			boolean RCUDOCStatus=false;
			String hidRcuStatusString = CommonFunction.checkNull(request.getParameter("hidRcuStatusString"));
			String hidRcuStatusStringValue=hidRcuStatusString.replace("~",",");
			String hidRcuCommentString = vo.getHidRcuCommentString().toString();   //CommonFunction.checkNull(request.getParameter("hidRcuCommentString"));
			//String hidRcuCommentStringValue = hidRcuCommentString.replace("~",",");
			String hidDOCIDString = CommonFunction.checkNull(request.getParameter("hidDOCIDString"));
			String hidRcuChildIDString = CommonFunction.checkNull(request.getParameter("hidChildIDString"));
			String hidRcuChildIDStringValue = hidRcuChildIDString.replace("~",",");
			logger.info("hidRcuStatusString::"+hidRcuStatusString);
			logger.info("hidRcuCommentString::"+hidRcuCommentString);
			logger.info("hidDOCIDString::"+hidDOCIDString);
			//amandeep ends for RCU
			//if(vo.getFieldVerificationUniqueId().equalsIgnoreCase(""))
			//{
				fieldVerificationUniqueId = fieldDao.insertFieldVerCaptureAtCM(vo);
				logger.info("Captured Id at verification capturing: "+fieldVerificationUniqueId);			
				String verificationType = CommonFunction.checkNull(vo.getVarificationType());
				logger.info("verificationType::RCU"+verificationType);
				 //Rohit Chnages Starts
				String verificationSubType = CommonFunction.checkNull(vo.getVerificationSubType());
					logger.info("verificationType:::::"+verificationSubType);
					request.setAttribute("verificationType", verificationType);
					request.setAttribute("verificationSubType", verificationSubType);
			        //rohit end
				if(fieldVerificationUniqueId>0 && verificationType.contains("RCU REPORT"))
				{
					if(!hidDOCIDString.equals("") && !hidDOCIDString.equals("null"))
						{
							RCUDOCStatus=fieldDao.updateRCUDocs(hidRcuStatusStringValue,hidRcuCommentString,hidDOCIDString,hidRcuChildIDStringValue,vo);

						}
				}
				else if(fieldVerificationUniqueId>0)
				{
					vo.setFieldVerificationUniqueId(""+fieldVerificationUniqueId);
					questionStatus=fieldDao.insertQuestionDetailsAtCM(vo);
					logger.info("dealId: "+vo.getDealId()+" verification Id: "+vo.getVerificationId()); 		

				}
			//}
			/*else
			{
	            status = fieldDao.updateFieldVerCapture(vo);
				
				if(status)

				{
				  questionStatus=fieldDao.updateQuestionDetails(vo);
							    

				}
			}*/
			logger.info("status: "+status+"questionStatus: "+questionStatus);
			if((status) ||(fieldVerificationUniqueId>0))
			{
				session.setAttribute("fieldVerificationUniqueId", fieldVerificationUniqueId);
				request.setAttribute("sms", "S");
			}
			else
			{
				request.setAttribute("sms", "N");
			}
			dealIdQuery=null;
			fieldDao=null;
			dealId=null;
			vo=null;
			return  mapping.findForward("successAtCM");		
       }
     public ActionForward openComPopupAtCM(ActionMapping mapping, ActionForm form,
 			HttpServletRequest request, HttpServletResponse response)	throws Exception 
 	{
 				logger.info(" in openComPopupAtCM......()");
 				HttpSession session = request.getSession();
 				UserObject userobj=(UserObject)session.getAttribute("userobject");
 				if(userobj==null){
 					logger.info("here in openComPopupAtCM method of fieldVerificationDispatchAction action the session is out----------------");
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

 				//code added by neeraj
 				String source="NE";
 				String functionId=(String)session.getAttribute("functionId");
 				int funid=Integer.parseInt(functionId);		
 				if(funid==4000122 || funid==4000123)
 					source="ED";
 				//neeraj space end
 				
 				String loanId = "";
 				
 				if(session.getAttribute("loanId") != null) {
 					loanId = session.getAttribute("loanId").toString();
 				} else if (session.getAttribute("maxIdInCM") != null) {
 					loanId = session.getAttribute("maxIdInCM").toString();
 				}
 				
 				logger.info("In openComPopupAtCM: " + loanId);
 				String entityId=CommonFunction.checkNull(request.getParameter("entityId"));
 				String verificationId=CommonFunction.checkNull(request.getParameter("verificationId"));
 				
 				String verificationTypeQuery="Select VERIFICATION_TYPE from cr_deal_verification_dtl where VERIFICATION_ID='"+verificationId+"'";
				logger.info("verificationTypeQuery: "+verificationTypeQuery);
				String verificationType=ConnectionDAO.singleReturn(verificationTypeQuery);
				logger.info("verificationType: "+verificationType);
 				
 				String verificationSubTypeQuery="Select VERIFICATION_SUBTYPE from cr_deal_verification_dtl where VERIFICATION_ID='"+verificationId+"'";
 				logger.info("verificationSubTypeQuery: "+verificationSubTypeQuery);
 				String verificationSubType=ConnectionDAO.singleReturn(verificationSubTypeQuery);
 				logger.info("verificationSubType: "+verificationSubType);
 				String entityTypeQuery="Select ENTITY_TYPE from cr_deal_verification_dtl where VERIFICATION_ID='"+verificationId+"'";
 				logger.info("entityTypeQuery: "+entityTypeQuery);
 				String entityType=ConnectionDAO.singleReturn(entityTypeQuery);
 				logger.info("entityType: "+entityType);
				String entitySubTypeQuery="Select ENTITY_SUB_TYPE from cr_deal_verification_dtl where VERIFICATION_ID='"+verificationId+"'";
				logger.info("entitySubTypeQuery: "+entitySubTypeQuery);
				String entitySubType=ConnectionDAO.singleReturn(entitySubTypeQuery);
				logger.info("entitySubType: "+entitySubType); 				CorporateDAO fieldDao=(CorporateDAO)DaoImplInstanceFactory.getDaoImplInstance(CorporateDAO.IDENTITY);
 				logger.info("Implementation class: "+fieldDao.getClass());
 				CreditProcessingDAO details=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
 		        logger.info("Implementation class: "+details.getClass()); 	//changed by asesh
 		        FieldVerificationDAO verifDao=(FieldVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(FieldVerificationDAO.IDENTITY);
		        logger.info("Implementation class: "+details.getClass()); 	
 		        
 				//CreditProcessingDAO details = new CreditProcessingDAOImpl();
 		       if(CommonFunction.checkNull(verificationType).equalsIgnoreCase("CONTACT VERIFICATION") && (CommonFunction.checkNull(entityType).equalsIgnoreCase("PRAPPL")||CommonFunction.checkNull(entityType).equalsIgnoreCase("COAPPL")||CommonFunction.checkNull(entityType).equalsIgnoreCase("GUARANTOR")))
 				{
 					ArrayList addrList=fieldDao.getAddressList();
 					ArrayList addressList=fieldDao.copyAddressAtCM(entityId);
 					request.setAttribute("approve", "approve");
 					request.setAttribute("verificationType", "verificationType");
 					request.setAttribute("customerList", addressList);
 					request.setAttribute("addrList", addrList);
 					return mapping.findForward("openAddress");	
 				}
 				else if(CommonFunction.checkNull(entityType).equalsIgnoreCase("PROPERTY"))
 				{
 					 ArrayList<Object> fetchCollateralDetails = details.fetchCollateralDetailsAllAtCM(entityId,"PROPERTY");
 			    	 request.setAttribute("fetchCollateralDetails", fetchCollateralDetails);
 			    	 request.setAttribute("viewDeal", "viewDeal");
 					return mapping.findForward("openProperty");	
 				}
 				else if(CommonFunction.checkNull(entityType).equalsIgnoreCase("MACHINE"))
 				{
 					 ArrayList<Object> fetchCollateralDetails = details.fetchCollateralDetailsAllAtCM(entityId,"MACHINE");
 			    	 request.setAttribute("fetchCollateralDetails", fetchCollateralDetails);
 			    	 request.setAttribute("viewDeal", "viewDeal");
 				
 					return mapping.findForward("openMachine");	
 				}
 				else if(CommonFunction.checkNull(entityType).equalsIgnoreCase("VEHICLE"))
 				{
 					 ArrayList<Object> fetchCollateralDetails = details.fetchCollateralDetailsAllAtCM(entityId,"VEHICLE");
 			    	 request.setAttribute("fetchCollateralDetails", fetchCollateralDetails);
 			    	 request.setAttribute("viewDeal", "viewDeal");
 					return mapping.findForward("openVehicle");	
 				}
 				else if(CommonFunction.checkNull(entityType).equalsIgnoreCase("OTHERS"))
 				{
 					logger.info("entityType: "+entityType);
 					 ArrayList<Object> fetchCollateralDetails = details.fetchCollateralDetailsAllAtCM(entityId,"OTHERS");
 			    	 request.setAttribute("fetchCollateralDetails", fetchCollateralDetails);
 			    	 request.setAttribute("viewDeal", "viewDeal");
 					return mapping.findForward("openOthers");	
 				}
 				else if(CommonFunction.checkNull(entityType).equalsIgnoreCase("BG"))
 				{
 					 ArrayList<Object> fetchCollateralDetails = details.fetchCollateralDetailsAllAtCM(entityId,"BG");
 			    	 request.setAttribute("fetchCollateralDetails", fetchCollateralDetails);
 			    	 request.setAttribute("viewDeal", "viewDeal");
 					return mapping.findForward("openBg");	
 				}
 				else if(CommonFunction.checkNull(entityType).equalsIgnoreCase("DEBTOR"))
 				{
 					 ArrayList<Object> fetchCollateralDetails = details.fetchCollateralDetailsAllAtCM(entityId,"DEBTOR");
 			    	 request.setAttribute("fetchCollateralDetails", fetchCollateralDetails);
 			    	 request.setAttribute("viewDeal", "viewDeal");
 					return mapping.findForward("openDebtor");	
 				}
 				else if(CommonFunction.checkNull(entityType).equalsIgnoreCase("FD"))
 				{
 					 ArrayList<Object> fetchCollateralDetails = details.fetchCollateralDetailsAllAtCM(entityId,"FD");
 			    	 request.setAttribute("fetchCollateralDetails", fetchCollateralDetails);
 			    	 request.setAttribute("viewDeal", "viewDeal");
 					return mapping.findForward("openFd");	
 				}
 				else if(CommonFunction.checkNull(entityType).equalsIgnoreCase("SBLC"))
 				{
 					 ArrayList<Object> fetchCollateralDetails = details.fetchCollateralDetailsAllAtCM(entityId,"SBLC");
 			    	 request.setAttribute("fetchCollateralDetails", fetchCollateralDetails);
 			    	 request.setAttribute("viewDeal", "viewDeal");
 					return mapping.findForward("openSblc");	
 				}
 				else if(CommonFunction.checkNull(entityType).equalsIgnoreCase("SECURITIES"))
 				{
 					 ArrayList<Object> fetchCollateralDetails = details.fetchCollateralDetailsAllAtCM(entityId,"SECURITIES");
 			    	 request.setAttribute("fetchCollateralDetails", fetchCollateralDetails);
 			    	 request.setAttribute("viewDeal", "viewDeal");
 					return mapping.findForward("openSecurity");	
 				}
 				else if(CommonFunction.checkNull(entityType).equalsIgnoreCase("STOCK"))
 				{
 					 ArrayList<Object> fetchCollateralDetails = details.fetchCollateralDetailsAllAtCM(entityId,"STOCK");
 			    	 request.setAttribute("fetchCollateralDetails", fetchCollateralDetails);
 			    	 request.setAttribute("viewDeal", "viewDeal");
 					return mapping.findForward("openStock");	
 				}
 				
 			/*	else if(CommonFunction.checkNull(entityType).equalsIgnoreCase("BUYER"))
 				{
 					
 					ArrayList<Object> buyerList = details.modifyBuyerDetailsAll("B", entityId);
 					request.setAttribute("buyerList", buyerList);
 					request.setAttribute("viewDeal", "viewDeal");
 					request.setAttribute("verificationType", "verificationType");
 					return mapping.findForward("openBuyer");	
 				}
 				else if(CommonFunction.checkNull(entityType).equalsIgnoreCase("SUPPLIER"))
 				{
 					
 					
 					ArrayList<Object> supplierList = details.modifySupplierDetailsAll("S", entityId);
 					request.setAttribute("supplierList", supplierList);
 					request.setAttribute("viewDeal", "viewDeal");
 					request.setAttribute("verificationType", "verificationType");
 					return mapping.findForward("openSupplier");	
 				}*/
 				else if(CommonFunction.checkNull(entityType).equalsIgnoreCase("INCOME"))
 				{
 					
 					CorporateDAO service=(CorporateDAO)DaoImplInstanceFactory.getDaoImplInstance(CorporateDAO.IDENTITY);
 					logger.info("Implementation class: "+service.getClass());
 					ArrayList<Object> individualInfo = service.getIndividualDetails(entityId,"","","","","",source);
 					request.setAttribute("individualInfo", individualInfo);
 					if(individualInfo.size()>0)
 					{
 						ShowCustomerDetailVo show=(ShowCustomerDetailVo)individualInfo.get(0);
 						
 						
 							logger.info("other Relationship : "+show.getOtherRelationShip());
 							request.setAttribute("otherRelationShip", show.getOtherRelationShip());
 						
 				
 					}
 					ArrayList<Object> individualCategorylist=service.getCustomerCategoryList();
 					request.setAttribute("individualCategory",individualCategorylist);
 					ArrayList<Object> CastCategory=service.getCastCategoryList();
 					request.setAttribute("CastCategory",CastCategory);
 					
 					ArrayList<Object> genderIndiv=service.getGenderList();
 					request.setAttribute("GenderCategory",genderIndiv);
 					
 					ArrayList<Object> constitutionlist=service.getIndividualContitutionList();
 					request.setAttribute("constitutionlist",constitutionlist);
 					request.setAttribute("viewDeal", "viewDeal");
 					request.setAttribute("pageStatuss", "pageStatuss");
 					request.setAttribute("approve", "approve");
 					return mapping.findForward("openIndivCustomer");	
 				}
 				else if(CommonFunction.checkNull(verificationType).equalsIgnoreCase("DOCUMENT VERIFICATION"))
				{
					 ArrayList<Object> fetchChildDocsDetails = verifDao.fetchChildDocs(entityId,loanId,"LIM");
			    	 request.setAttribute("fetchChildDocsDetails", fetchChildDocsDetails);
			    	 request.setAttribute("viewDeal", "viewDeal");
					 return mapping.findForward("openChildDocs");	
				}
 		      if(CommonFunction.checkNull(verificationType).equalsIgnoreCase("CONSTITUTION VERIFICATION") && CommonFunction.checkNull(verificationSubType).equalsIgnoreCase("INDIVIDUAL CONSTITUTION") && (CommonFunction.checkNull(entityType).equalsIgnoreCase("PRAPPL")||CommonFunction.checkNull(entityType).equalsIgnoreCase("COAPPL")||CommonFunction.checkNull(entityType).equalsIgnoreCase("GUARANTOR")))
				{
					CorporateDAO corporateDao=(CorporateDAO)DaoImplInstanceFactory.getDaoImplInstance(CorporateDAO.IDENTITY);
					logger.info("Implementation class: "+corporateDao.getClass());
					ArrayList<Object> individualCategorylist=corporateDao.getCustomerCategoryList();
					request.setAttribute("individualCategory",individualCategorylist);
					ArrayList<Object> CastCategory=corporateDao.getCastCategoryList();
					request.setAttribute("CastCategory",CastCategory);
					
					ArrayList<Object> eduDetail=corporateDao.getEduDetail();
					request.setAttribute("eduDetail",eduDetail);
					
					ArrayList<Object> genderIndiv=corporateDao.getGenderList();
					request.setAttribute("GenderCategory",genderIndiv);
					
					ArrayList<Object> constitutionlist=corporateDao.getIndividualContitutionList();
					session.setAttribute("constitutionlist",constitutionlist);
					ArrayList<Object> individualInfo = corporateDao.getIndividualDetails(entityId,"UnApproved","","","","","");
					request.setAttribute("individualInfo",individualInfo);	
					request.setAttribute("approve","approve");	
					request.setAttribute("underWriter","underWriter");	
					return mapping.findForward("openIndividualCustomer");	
				}
				if(CommonFunction.checkNull(verificationType).equalsIgnoreCase("CONSTITUTION VERIFICATION") && CommonFunction.checkNull(verificationSubType).equalsIgnoreCase("CORPORATE CONSTITUTION") && (CommonFunction.checkNull(entityType).equalsIgnoreCase("PRAPPL")||CommonFunction.checkNull(entityType).equalsIgnoreCase("COAPPL")||CommonFunction.checkNull(entityType).equalsIgnoreCase("GUARANTOR")))
				{
					CorporateDAO corporateDao=(CorporateDAO)DaoImplInstanceFactory.getDaoImplInstance(CorporateDAO.IDENTITY);
					logger.info("Implementation class: "+corporateDao.getClass());
					ArrayList<Object> customerCategorylist=corporateDao.getCustomerCategoryList();
					ArrayList<Object> constitutionlist=corporateDao.getContitutionList();
					ArrayList<Object> registrationTypeList=corporateDao.getRegistrationTypeList();
					ArrayList<Object> industryList = corporateDao.getIndustryList();
					ArrayList<Object> businessSegmentList = corporateDao.getBusinessSegmentList();
					ArrayList<Object> natureOfBusList = corporateDao.getNatureOfBusinessList();
					request.setAttribute("industryList",industryList);
					request.setAttribute("customerCategory",customerCategorylist);
					request.setAttribute("constitutionlist",constitutionlist);
					request.setAttribute("registrationTypeList",registrationTypeList);
					request.setAttribute("businessSegmentList",businessSegmentList);
					request.setAttribute("natureOfBusList",natureOfBusList);
					int custId=0;
					if(!CommonFunction.checkNull(entityId).equalsIgnoreCase(""))
					{
						custId=Integer.parseInt(entityId);
					}
					ArrayList<Object> detailList = corporateDao.getCorporateDetailAll(custId,"UnApproved","","","","","");
					request.setAttribute("detailList",detailList);	
					request.setAttribute("approve","approve");	
					request.setAttribute("underWriter","underWriter");	
					
			    	return mapping.findForward("openCorporateCustomer");	
				}

 				else 
 				{
 					
 					return mapping.findForward("openPopupDetailCapturing");	
 				}
 				

 									
 	}
     public ActionForward forwardVerificationCapAtCM(ActionMapping mapping, ActionForm form,
 			HttpServletRequest request, HttpServletResponse response)	throws Exception 
 	{
 				
 		logger.info(" in forwardVerificationCapAtCM......()");	
 		HttpSession session = request.getSession();
 		UserObject userobj=(UserObject)session.getAttribute("userobject");
 		Object sessionId = session.getAttribute("sessionID");
 		String userId="";
 		String businessDate="";
 		if(userobj !=null)
 		{
 			userId=userobj.getUserId();
 			businessDate =userobj.getBusinessdate();
 		}else{
 			logger.info("here in forwardVerificationCapAtCM method of fieldVerificationDispatchAction action the session is out----------------");
 			return mapping.findForward("sessionOut");
 		}
 		String loanId=CommonFunction.checkNull(session.getAttribute("loanId"));
 		FieldVerificationVo vo=new FieldVerificationVo(); 		
 		DynaValidatorForm formbeen= (DynaValidatorForm)form;
 		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, formbeen);					
 		FieldVerificationDAO fieldDao=(FieldVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(FieldVerificationDAO.IDENTITY);
         logger.info("Implementation class: "+fieldDao.getClass()); 	// changed by asesh
 				
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
 		String verificationId="";
 		if(session.getAttribute("verificationId")!=null)
 		{
 			verificationId=CommonFunction.checkNull(session.getAttribute("verificationId"));
 		}
 		logger.info("verificationId: "+verificationId);
 		vo.setLbxLoanNoHID(loanId);
 		vo.setMakerId(userId);
 		vo.setMakerDate(businessDate);
 		vo.setVerificationStatus("F");
 		vo.setVerificationId(verificationId);
 		boolean status=false;
 		boolean questionStatus=false;
 		
             status = fieldDao.updateFieldVerCapture(vo);
 			
 			if(status)
 			{
 			  questionStatus=fieldDao.updateQuestionDetailsAtCM(vo);
 						    
 			}
 	
 		 boolean st=fieldDao.modifyFieldRemarksAtCM(vo, loanId);
 			logger.info("Update cr_deal_verification_dtl with R st"+st);
 		logger.info("status: "+status+"questionStatus: "+questionStatus);
 		if(status)
 		{
 			
 			request.setAttribute("forwardsms", "F");
 		}
 		else
 		{
 			request.setAttribute("forwardsms", "N");
 		}
 		
 		return mapping.findForward("success");	
 	}
     
     public ActionForward downloadVarificationFormAtCM(ActionMapping mapping,
 			ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {

 		logger.info("In downloadVarificationFormAtCM");
 		HttpSession session = request.getSession();
 		UserObject userobj=(UserObject)session.getAttribute("userobject");
 		String p_printed_by="";
 		String p_printed_date="";
 		String p_company_name="";		
 		if(userobj==null){
 			logger.info("here in downloadVarificationFormAtCM method of fieldVerificationDispatchAction action the session is out----------------");
 			return mapping.findForward("sessionOut");
 		}
 		else
 		{
 			p_printed_by=userobj.getUserName()+" ";
 			p_printed_date=userobj.getBusinessdate();
 			p_company_name=userobj.getConpanyName();
 		}
 		Object sessionId = session.getAttribute("sessionID");
 		FieldVerificationVo vo = new FieldVerificationVo();

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
 		char source='A';
 		String sou=CommonFunction.checkNull(request.getParameter("source"));	
 		if(sou.length()>0)
 			source=sou.charAt(0);
 		
 		String reportName="verificationCapturingReport";
 		String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports/logo.bmp";
 		String checkImage=getServlet().getServletContext().getRealPath("/")+"reports/CheckCheckBox.bmp";
 		String uncheckImage=getServlet().getServletContext().getRealPath("/")+"reports/UnCheckCheckBox.bmp";		
 		String verificationId=request.getParameter("verificationId");
 		String entityId=request.getParameter("entId");
 		String loanId=(String)session.getAttribute("loanId");
 		String SUBREPORT_DIR=getServlet().getServletContext().getRealPath("/")+"reports\\";
 		String reportPath="/reports/";
 		if(dbType.equalsIgnoreCase("MSSQL"))
		{
			reportPath=reportPath+"MSSQLREPORTS/";
			SUBREPORT_DIR=SUBREPORT_DIR+"MSSQLREPORTS\\";
		}
		else
		{
			reportPath=reportPath+"MYSQLREPORTS/";
			SUBREPORT_DIR=SUBREPORT_DIR+"MYSQLREPORTS\\";
		}
 		
 		String query="select d.loan_id deal_id,d.LOAN_NO deal_no,d.LOAN_CUSTOMER_ID DEAL_CUSTOMER_ID,deal.CUSTOMER_NAME,d.MAKER_DATE Deal_Date ,p.PRODUCT_DESC,s.SCHEME_DESC," +
 				" concat(d.LOAN_PRODUCT_CATEGORY,' ') DEAL_PRODUCT_CATEGORY,d.REC_STATUS, ' 'APPRAISER_NAME,' 'APPRAISAL_DATE,' ' VERIFICATION_MODE,' 'PERSON_MET,' 'RELATION," +
 				" ' 'PHONE1,' 'PHONE2,' 'E_MAIL,' ' FV_STATUS,' ' FV_REMARKS,grid.QUESTION_ID,grid.QUESTION_SEQ_NO,grid.QUESTION,grid.QUESTION_REMARKS," +
 				" grid.VERIFICATION_METHOD,grid.VERIFICATION_REQD " +
 				" from cr_loan_dtl d  " +
 				" join cr_product_m p on d.LOAN_PRODUCT=p.PRODUCT_ID  " +
 				" join cr_scheme_m s on d.LOAN_SCHEME=s.SCHEME_ID  " +
 				" join gcd_customer_m deal on deal.CUSTOMER_ID=d.LOAN_CUSTOMER_ID  " +
 				" left join" +
 				" (" +
 				" 		SELECT v.VERIFICATION_ID,q.QUESTION_ID,q.QUESTION_SEQ_NO,concat(q.QUESTION,' ')QUESTION,' 'QUESTION_REMARKS,' 'VERIFICATION_METHOD," +
 				" 		CASE WHEN q.VERIFICATION_REQD='Y' THEN 'YES ' ELSE 'NO ' END  VERIFICATION_REQD " +
 				" 		FROM cr_deal_verification_question_m q 	" +
 				" 		inner join cr_deal_verification_dtl v on q.VERIFICATION_TYPE=v.VERIFICATION_TYPE and q.VERIFICATION_SUB_TYPE=v.VERIFICATION_SUBTYPE   AND q.ENTITY_TYPE=V.ENTITY_TYPE AND q.ENTITY_SUB_TYPE=V.ENTITY_SUB_TYPE " +
 				" 		left join cr_deal_question_verification_dtl tq on tq.QUESTION_ID=q.QUESTION_ID AND TQ.VERIFICATION_ID=v.VERIFICATION_ID	" +
 				" 		WHERE q.REC_STATUS='A' and v.VERIFICATION_ID="+verificationId+" "+
 				" )grid on('a'='a')" +
 				" where d.LOAN_ID="+loanId;
 		String subQuery="";
 		switch(source)
 		{					
 			case 'A': subQuery="select concat(a.ADDRESS_TYPE,' ')ADDRESS_TYPE,concat(a.ADDRESS_LINE1,' ')ADDRESS_LINE1," +
 							" concat(a.ADDRESS_LINE2,' ')ADDRESS_LINE2,concat(a.ADDRESS_LINE3,' ')ADDRESS_LINE3,concat(b.COUNTRY_DESC,' ')COUNTRY_DESC," +
 							" concat(c.STATE_DESC,' ')STATE_DESC,concat(a.TAHSIL,' ')TAHSIL,concat(d.DISTRICT_DESC,' ')DISTRICT_DESC ,a.PINCODE,a.PRIMARY_PHONE," +
 							" a.ALTERNATE_PHONE, a.TOLLFREE_NUMBER,a.FAX,concat(a.LANDMARK,' ')LANDMARK,a.NO_OF_YEARS,a.NO_OF_MONTHS,a.BRANCH_DISTANCE,a.COMMUNICATION_ADDRESS," +
 							" case a.ADDRESS_DETAIL when 'O' then 'Owned ' when 'R' then 'Rented' else 'Company Provided'  end as addressDetail,dtl.VERIFICATION_TYPE," +
 							" dtl.VERIFICATION_SUBTYPE,dtl.ENTITY_TYPE,dtl.ENTITY_SUB_TYPE,dtl.ENTITY_DESC,dtl.EXTERNAL_APPRAISER " +
 							" from com_address_m a " +
 							" join com_country_m b on(b.COUNTRY_ID=a.COUNTRY)" +
 							" join com_state_m c on(c.STATE_ID=a.STATE)" +
 							" join com_district_m d on(d.DISTRICT_ID=a.DISTRICT)" +
 							" left join " +
 							" (" +
 							" 		SELECT concat(VERIFICATION_TYPE,' ')VERIFICATION_TYPE,concat(VERIFICATION_SUBTYPE,' ')VERIFICATION_SUBTYPE," +
 							" 		case ENTITY_TYPE when 'PRAPPL' then 'APPLICANT ' when 'COAPPL' then 'COAPPLICANT ' else concat(ENTITY_TYPE,' ') end as ENTITY_TYPE," +
 							" 		concat(ENTITY_SUB_TYPE,' ')ENTITY_SUB_TYPE,concat(ENTITY_DESC,' ')ENTITY_DESC,(SELECT concat(AGENCY_NAME,' ') FROM COM_AGENCY_M  WHERE AGENCY_CODE = EXTERNAL_APPRAISER) as EXTERNAL_APPRAISER " +
 							" 		FROM cr_deal_verification_dtl V " +
 							"		WHERE LOAN_ID= "+loanId+" and VERIFICATION_ID="+verificationId+" " +
 							" )dtl on('a'='a')" +
 							" where a.ADDRESS_ID="+entityId;
 						break;				
 			case 'B': subQuery="Select c.ASSET_NEW_OLD,concat(c.ASSET_COLLATERAL_DESC,' ')PROPERTY_DESC,CONCAT(c.PROPERTY_TYPE,' ')PROPERTY_TYPE," +
 							" CONCAT(c.LAWYER_NAME,' ')LAWYER_NAME,CONCAT(c.VALUER_NAME,' ')VALUER_NAME, CONCAT(c.PROPERTY_OWNER,' ')PROPERTY_OWNER,c.PROPERTY_CONSTRUCTION," +
 							" c.PROPERTY_AREA  Construted_Area,c.BUILT_UP_AREA 'BUILT_UP_AREA(sqft)',c.BUILDUPAREASQMTR,c.SUPER_BUILDUP_AREA,case c.PROPERTY_DIRECTION when 'E' " +
 							" then 'East ' when 'W' then 'West ' when 'N' then 'North ' when 'S' then 'South ' when 'NE' then 'North-East ' when 'NW' then 'North-West ' when 'SW' " +
 							" then 'South-West ' when 'SE' then 'South-East '  end PROPERTY_DIRECTION ,CONCAT(c.CARPET_AREA,' ')CARPET_AREA,c.ASSET_COLLATERAL_VALUE," +
 							"  c.COLLATERAL_SECURITY_MARGIN,c.TECH_VERIFICATION TECHNICAL_VAL1,((dtl.DEAL_LOAN_AMOUNT/c.TECH_VERIFICATION)*100)tv1LTV,c.TECHNICAL_VAL1 TECHNICAL_VAL2," +
 							" ((dtl.DEAL_LOAN_AMOUNT/c.TECHNICAL_VAL1)*100)tv2LTV,c.TECHNICAL_VAL2 TECHNICAL_VAL3,((dtl.DEAL_LOAN_AMOUNT/c.TECHNICAL_VAL2)*100)tv3LTV," +
 							" (SELECT CONCAT(DESCRIPTION,' ') FROM GENERIC_MASTER WHERE GENERIC_KEY='VALUATION_METHOD' AND REC_STATUS='A' and VALUE=c.VALUATION_METHOD_ID)VALUATION_METHOD," +
 							" c.VALUATION_AMOUNT,c.DOCUMENT_VALUE,((dtl.DEAL_LOAN_AMOUNT/c.DOCUMENT_VALUE)*100)document_valueLTV,c.ADDITIONAL_CONSTRUCTION," +
 							" ((dtl.DEAL_LOAN_AMOUNT/c.ADDITIONAL_CONSTRUCTION)*100)ADDITIONAL_CONSTRUCTIONLTV,c.PROPERTY_ADDRESS,c.ADDRESS_LINE2,c.ADDRESS_LINE3," +
 							" (select concat(country_desc,' ') from com_country_m where country_id=c.COUNTRY)COUNTRY,(SELECT concat(STATE_DESC,' ') from com_state_m WHERE STATE_ID=c.STATE)STATE," +
 							" (select concat(district_desc,' ') from com_district_m where district_id=c.DISTRICT)DISTRICT,c.TEHSIL,c.PINCODE," +
 							" case c.SECURITY when 'A' then 'Additional ' when 'H' then 'Hypothecation ' when 'P' then 'Prime ' end as SECURITY,c.ASSET_LEVEL," +
 							" case c.PROPERTY_STATUS when 'LOR' then 'Leased OR Rented ' when 'OBF' then 'Occupied by Family or Friend ' when 'SO' then " +
 							" 'Self Occupied ' when 'UC' then 'Under Construction ' when 'VA' then 'Vacant ' end as  PROPERTY_STATUS,case c.PROPERTY_TITLE when 'FH' " +
 							" then 'Free Hold ' when 'LH' then 'Lease Hold ' when 'OT' then 'Other ' end as PROPERTY_TITLE,concat(c.VILLAGE_NAME_LANDMARK,' ')VILLAGE_NAME_LANDMARK," +
 							" dtl.VERIFICATION_TYPE,dtl.VERIFICATION_SUBTYPE,dtl.ENTITY_TYPE,dtl.ENTITY_SUB_TYPE,dtl.ENTITY_DESC,dtl.EXTERNAL_APPRAISER " +
 							" from cr_asset_collateral_m c " +
 							" left join cr_loan_collateral_m d on c.ASSET_ID=d.ASSETID " +
 							" left join " +
 							" (" +
 							" 		SELECT L.LOAN_LOAN_AMOUNT DEAL_LOAN_AMOUNT,concat(VERIFICATION_TYPE,' ')VERIFICATION_TYPE,concat(VERIFICATION_SUBTYPE,' ')VERIFICATION_SUBTYPE," +
 							" 		case ENTITY_TYPE when 'PRAPPL' then 'APPLICANT ' when 'COAPPL' then 'COAPPLICANT ' else concat(ENTITY_TYPE,' ') end as ENTITY_TYPE," +
 							" 		concat(ENTITY_SUB_TYPE,' ')ENTITY_SUB_TYPE,concat(ENTITY_DESC,' ')ENTITY_DESC,	(SELECT concat(AGENCY_NAME,' ') FROM COM_AGENCY_M  WHERE AGENCY_CODE = EXTERNAL_APPRAISER) as EXTERNAL_APPRAISER " +
 							" 		FROM cr_deal_verification_dtl V " +
 							" 		JOIN CR_LOAN_DTL L ON(V.LOAN_ID=L.LOAN_ID)	" +
 							" 		WHERE V.LOAN_ID= "+loanId+" and VERIFICATION_ID="+verificationId+" " +
 							" )dtl on('a'='a')" +
 							" where c.ASSET_ID="+entityId+" and c.ASSET_COLLATERAL_CLASS='PROPERTY'";
 						break;							
 			case 'C': subQuery="Select case c.ASSET_NEW_OLD when 'N' then 'New ' else 'Yes ' end as ASSET_NEW_OLD,concat(c.ASSET_COLLATERAL_DESC,' ')ASSET_COLLATERAL_DESC," +
 							" concat(c.VEHICLE_MAKE,' ')VEHICLE_MAKE,concat(c.VEHICLE_MODEL,' ')VEHICLE_MODEL,concat(c.VEHICLE_ASSET_USES_TYPE,' ')VEHICLE_ASSET_USES_TYPE," +
 							" (SELECT CONCAT(STATE_DESC,' ') FROM com_state_m WHERE STATE_ID=c.STATE)STATE,c.DEFAULT_LTV,C.COLLATERAL_SECURITY_MARGIN," +
 							" c.LOAN_AMOUNT,C.VEHICLE_VALUE,C.VEHICLE_DISCOUNT,C.ASSET_COLLATERAL_VALUE,concat(C.VEHICLE_REGISTRATION_NO,' ')VEHICLE_REGISTRATION_NO," +
 							" C.VEHICLE_REGISTRATION_DATE,concat(C.ENGINE_NUMBER,' ')ENGINE_NUMBER,concat(C.VEHICLE_CHASIS_NUMBER,' ')VEHICLE_CHASIS_NUMBER," +
 							" concat(C.VEHICLE_OWNER,' ')VEHICLE_OWNER,concat(C.VEHICLE_MANUFACTURING_YEAR,' ')VEHICLE_MANUFACTURING_YEAR," +
 							" concat(C.VEHICLE_Insurer,' ')VEHICLE_Insurer,C.VEHICLE_INSURED_DATE,case C.SECURITY when 'A' then 'Additional ' when 'H' then " +
 							" 'Hypothecation ' when 'P' then 'Prime ' end as SECURITY,dtl.VERIFICATION_TYPE,dtl.VERIFICATION_SUBTYPE,dtl.ENTITY_TYPE" +
 							" ,dtl.ENTITY_SUB_TYPE,dtl.ENTITY_DESC,dtl.EXTERNAL_APPRAISER " +
 							" from cr_asset_collateral_m c " +
 							" left join " +
 							" (" +
 							" 		SELECT L.LOAN_LOAN_AMOUNT DEAL_LOAN_AMOUNT,concat(VERIFICATION_TYPE,' ')VERIFICATION_TYPE,concat(VERIFICATION_SUBTYPE,' ')VERIFICATION_SUBTYPE," +
 							" 		case ENTITY_TYPE when 'PRAPPL' then 'APPLICANT ' when 'COAPPL' then 'COAPPLICANT ' else concat(ENTITY_TYPE,' ') end as ENTITY_TYPE," +
 							" 		concat(ENTITY_SUB_TYPE,' ')ENTITY_SUB_TYPE,concat(ENTITY_DESC,' ')ENTITY_DESC,	(SELECT concat(AGENCY_NAME,' ') FROM COM_AGENCY_M  WHERE AGENCY_CODE = EXTERNAL_APPRAISER) as EXTERNAL_APPRAISER " +
 							" 		FROM cr_deal_verification_dtl V " +
 							" 		JOIN CR_LOAN_DTL L ON(V.LOAN_ID=L.LOAN_ID)	" +
 							" 		WHERE V.LOAN_ID= "+loanId+" and VERIFICATION_ID="+verificationId+" " +
 							" )dtl on('a'='a') " +
 							" where c.ASSET_ID="+entityId+" and c.ASSET_COLLATERAL_CLASS='VEHICLE'";
 						break;						
 			case 'D': subQuery="Select concat(c.ASSET_COLLATERAL_DESC,' ')ASSET_COLLATERAL_DESC,concat(c.MACHINE_VALUE,' ')MACHINE_VALUE,c.MACHINE_DISCOUNT,c.ASSET_COLLATERAL_VALUE," +
 							" c.COLLATERAL_SECURITY_MARGIN,concat(c.MACHINE_MAKE,' ')MACHINE_MAKE,concat(c.MACHINE_MODEL,' ')MACHINE_MODEL ," +
 							" case c.MACHINE_TYPE when 'L' then 'Local ' else 'Imported ' end as MACHINE_TYPE,concat(c.MACHINE_OWNER,' ')MACHINE_OWNER,c.MACHINE_MANUFACTURING_YEAR," +
 							" concat(c.MACHINE_IDENTIFICATION_NO,' ')MACHINE_IDENTIFICATION_NO,case c.ASSET_NEW_OLD when 'N' then 'New ' else 'Old 'end as  ASSET_NEW_OLD," +
 							" concat(c.ASSET_MANUFATURER_DESC,' ')ASSET_MANUFATURER_DESC,concat(c.ASSET_SUPPLIER_DESC,' ')ASSET_SUPPLIER_DESC,c.invoice_date," +
 							" case c.SECURITY when 'A' then 'Additional ' when 'H' then 'Hypothecation ' else 'Prime' end as SECURITY,c.asset_standard," +
 							" dtl.VERIFICATION_TYPE,dtl.VERIFICATION_SUBTYPE,dtl.ENTITY_TYPE,dtl.ENTITY_SUB_TYPE,dtl.ENTITY_DESC,dtl.EXTERNAL_APPRAISER " +
 							" from cr_asset_collateral_m c " +
 							" left join" +
 							" (" +
 							" 		SELECT L.LOAN_LOAN_AMOUNT DEAL_LOAN_AMOUNT,concat(VERIFICATION_TYPE,' ')VERIFICATION_TYPE,concat(VERIFICATION_SUBTYPE,' ')VERIFICATION_SUBTYPE," +
 							" 		case ENTITY_TYPE when 'PRAPPL' then 'APPLICANT ' when 'COAPPL' then 'COAPPLICANT ' else concat(ENTITY_TYPE,' ') end as ENTITY_TYPE," +
 							" 		concat(ENTITY_SUB_TYPE,' ')ENTITY_SUB_TYPE,concat(ENTITY_DESC,' ')ENTITY_DESC,	(SELECT concat(AGENCY_NAME,' ') FROM COM_AGENCY_M  WHERE AGENCY_CODE = EXTERNAL_APPRAISER) as EXTERNAL_APPRAISER " +
 							" 		FROM cr_deal_verification_dtl V " +
 							" 		JOIN CR_LOAN_DTL L ON(V.LOAN_ID=L.LOAN_ID)	" +
 							" 		WHERE V.LOAN_ID= "+loanId+" and VERIFICATION_ID="+verificationId+" " +
 							" )dtl on('a'='a')" +
 							" where c.ASSET_ID="+entityId+" and c.ASSET_COLLATERAL_CLASS='MACHINE'";
 						break;					
 			case 'E': subQuery="Select concat(c.ASSET_COLLATERAL_DESC,' ')ASSET_COLLATERAL_DESC, c.ASSET_COLLATERAL_VALUE ,dtl.VERIFICATION_TYPE," +
 							" dtl.VERIFICATION_SUBTYPE,dtl.ENTITY_TYPE,dtl.ENTITY_SUB_TYPE,dtl.ENTITY_DESC,dtl.EXTERNAL_APPRAISER " +
 							" from cr_asset_collateral_m c " +
 							" left join " +
 							" (" +
 							" 		SELECT L.LOAN_LOAN_AMOUNT DEAL_LOAN_AMOUNT,concat(VERIFICATION_TYPE,' ')VERIFICATION_TYPE,concat(VERIFICATION_SUBTYPE,' ')VERIFICATION_SUBTYPE," +
 							" 		case ENTITY_TYPE when 'PRAPPL' then 'APPLICANT ' when 'COAPPL' then 'COAPPLICANT ' else concat(ENTITY_TYPE,' ') end as ENTITY_TYPE," +
 							" 		concat(ENTITY_SUB_TYPE,' ')ENTITY_SUB_TYPE,concat(ENTITY_DESC,' ')ENTITY_DESC,	(SELECT concat(AGENCY_NAME,' ') FROM COM_AGENCY_M  WHERE AGENCY_CODE = EXTERNAL_APPRAISER) as EXTERNAL_APPRAISER " +
 							" 		FROM cr_deal_verification_dtl V " +
 							" 		JOIN CR_LOAN_DTL L ON(V.LOAN_ID=L.LOAN_ID)	" +
 							" 		WHERE V.LOAN_ID= "+loanId+" and VERIFICATION_ID="+verificationId+" " +
 							" )dtl on('a'='a')" +
 							" where c.ASSET_ID="+entityId+" and c.ASSET_COLLATERAL_CLASS='OTHERS'";
 						break;					
 			case 'F': subQuery="Select case c.BG_TYPE when 'F' then 'Financial ' when 'P' then 'Performance ' end as  BG_TYPE,c.ASSET_COLLATERAL_VALUE," +
 							" c.BG_ISSUE_DATE,c.BG_VALIDITY_DATE,concat(c.BG_ISSUING_BANK,' '),dtl.VERIFICATION_TYPE,dtl.VERIFICATION_SUBTYPE,dtl.ENTITY_TYPE," +
 							" dtl.ENTITY_SUB_TYPE,dtl.ENTITY_DESC,dtl.EXTERNAL_APPRAISER " +
 							" from cr_asset_collateral_m c " +
 							" left join " +
 							" (" +
 							" 		SELECT L.LOAN_LOAN_AMOUNT DEAL_LOAN_AMOUNT,concat(VERIFICATION_TYPE,' ')VERIFICATION_TYPE,concat(VERIFICATION_SUBTYPE,' ')VERIFICATION_SUBTYPE," +
 							" 		case ENTITY_TYPE when 'PRAPPL' then 'APPLICANT ' when 'COAPPL' then 'COAPPLICANT ' else concat(ENTITY_TYPE,' ') end as ENTITY_TYPE," +
 							" 		concat(ENTITY_SUB_TYPE,' ')ENTITY_SUB_TYPE,concat(ENTITY_DESC,' ')ENTITY_DESC,	(SELECT concat(AGENCY_NAME,' ') FROM COM_AGENCY_M  WHERE AGENCY_CODE = EXTERNAL_APPRAISER) as EXTERNAL_APPRAISER " +
 							" 		FROM cr_deal_verification_dtl V " +
 							" 		JOIN CR_LOAN_DTL L ON(V.LOAN_ID=L.LOAN_ID)	" +
 							" 		WHERE V.LOAN_ID= "+loanId+" and VERIFICATION_ID="+verificationId+" " +
 							" ) dtl on('a'='a')" +
 							" where c.ASSET_ID="+entityId+" and c.ASSET_COLLATERAL_CLASS='BG'";
 						break;					
 			case 'G': subQuery="Select concat(c.ASSET_COLLATERAL_DESC,' ')ASSET_COLLATERAL_DESC,c.ASSET_COLLATERAL_VALUE,c.COLLATERAL_SECURITY_MARGIN," +
 							" c.DEBTOR_TOTAL_OUTSTANDING,case c.DEBTOR_TYPE when '30' then '<30 Days '  when '31' then '31-60 Days ' when '61' then '61-90 Days ' when '91' then '91> Days ' end as DEBTOR_TYPE ," +
 							" dtl.VERIFICATION_TYPE,dtl.VERIFICATION_SUBTYPE,dtl.ENTITY_TYPE,dtl.ENTITY_SUB_TYPE,dtl.ENTITY_DESC,dtl.EXTERNAL_APPRAISER " +
 							" from cr_asset_collateral_m c " +
 							" left join " +
 							" (" +
 							" 		SELECT L.LOAN_LOAN_AMOUNT DEAL_LOAN_AMOUNT,concat(VERIFICATION_TYPE,' ')VERIFICATION_TYPE,concat(VERIFICATION_SUBTYPE,' ')VERIFICATION_SUBTYPE," +
 							" 		case ENTITY_TYPE when 'PRAPPL' then 'APPLICANT ' when 'COAPPL' then 'COAPPLICANT ' else concat(ENTITY_TYPE,' ') end as ENTITY_TYPE," +
 							" 		concat(ENTITY_SUB_TYPE,' ')ENTITY_SUB_TYPE,concat(ENTITY_DESC,' ')ENTITY_DESC,	(SELECT concat(AGENCY_NAME,' ') FROM COM_AGENCY_M  WHERE AGENCY_CODE = EXTERNAL_APPRAISER) as EXTERNAL_APPRAISER " +
 							" 		FROM cr_deal_verification_dtl V " +
 							" 		JOIN CR_LOAN_DTL L ON(V.LOAN_ID=L.LOAN_ID)	" +
 							" 		WHERE V.LOAN_ID= "+loanId+" and VERIFICATION_ID="+verificationId+" " +
 							" )dtl on('a'='a')" +
 							" where c.ASSET_ID="+entityId+" and c.ASSET_COLLATERAL_CLASS='DEBTOR'";
 						break;					
 			case 'H': subQuery="Select concat(c.ASSET_COLLATERAL_DESC,' ')ASSET_COLLATERAL_DESC, c.ASSET_COLLATERAL_VALUE,c.COLLATERAL_SECURITY_MARGIN,c.FD_AMOUNT, " +
 							" c.FD_TENURE, c.FD_RATE,c.FD_BOOK_DATE,c.FD_MATURITY_DATE,CONCAT(C.FD_AGENCY_NAME,' ')FD_AGENCY_NAME,CONCAT(C.FD_AGENCY_RATING,' ')FD_AGENCY_RATING," +
 							" (SELECT CONCAT(BANK_NAME,' ') FROM com_bank_m where BANK_ID=c.ISSUEING_BANK_ID)BANK_NAME,(SELECT CONCAT(BANK_BRANCH_NAME,' ') FROM com_bankbranch_m where BANK_BRANCH_ID=c.ISSUEING_BRANCH_ID)BRANCHNAME," +
 							" CONCAT(c.FD_APPLICANT,' ')FD_APPLICANT,dtl.VERIFICATION_TYPE,dtl.VERIFICATION_SUBTYPE,dtl.ENTITY_TYPE,dtl.ENTITY_SUB_TYPE,dtl.ENTITY_DESC,dtl.EXTERNAL_APPRAISER " +
 							" from cr_asset_collateral_m c " +
 							" left join " +
 							"(" +
 							" 		SELECT L.LOAN_LOAN_AMOUNT DEAL_LOAN_AMOUNT,concat(VERIFICATION_TYPE,' ')VERIFICATION_TYPE,concat(VERIFICATION_SUBTYPE,' ')VERIFICATION_SUBTYPE," +
 							" 		case ENTITY_TYPE when 'PRAPPL' then 'APPLICANT ' when 'COAPPL' then 'COAPPLICANT ' else concat(ENTITY_TYPE,' ') end as ENTITY_TYPE," +
 							" 		concat(ENTITY_SUB_TYPE,' ')ENTITY_SUB_TYPE,concat(ENTITY_DESC,' ')ENTITY_DESC,	(SELECT concat(AGENCY_NAME,' ') FROM COM_AGENCY_M  WHERE AGENCY_CODE = EXTERNAL_APPRAISER) as EXTERNAL_APPRAISER " +
 							" 		FROM cr_deal_verification_dtl V " +
 							" 		JOIN CR_LOAN_DTL L ON(V.LOAN_ID=L.LOAN_ID)	" +
 							" 		WHERE V.LOAN_ID= "+loanId+" and VERIFICATION_ID="+verificationId+" " +
 							" )dtl on('a'='a')" +
 							" where c.ASSET_ID="+entityId+" and c.ASSET_COLLATERAL_CLASS='FD'";
 						break;				
 			case 'I':subQuery="Select CONCAT(c.ASSET_COLLATERAL_DESC,' ')ASSET_COLLATERAL_DESC,c.ASSET_COLLATERAL_VALUE,c.COLLATERAL_SECURITY_MARGIN," +
 							" c.SBLC_AMOUNT,c.SBLC_VALIDITY,c.SBLC_ISSUING_DATE,CONCAT(c.SBLC_PARENT_COMPANY,' ')SBLC_PARENT_COMPANY,dtl.VERIFICATION_TYPE," +
 							" dtl.VERIFICATION_SUBTYPE,dtl.ENTITY_TYPE,dtl.ENTITY_SUB_TYPE,dtl.ENTITY_DESC,dtl.EXTERNAL_APPRAISER " +
 							" from cr_asset_collateral_m c " +
 							" left join" +
 							" (" +
 							" 		SELECT L.LOAN_LOAN_AMOUNT DEAL_LOAN_AMOUNT,concat(VERIFICATION_TYPE,' ')VERIFICATION_TYPE,concat(VERIFICATION_SUBTYPE,' ')VERIFICATION_SUBTYPE," +
 							" 		case ENTITY_TYPE when 'PRAPPL' then 'APPLICANT ' when 'COAPPL' then 'COAPPLICANT ' else concat(ENTITY_TYPE,' ') end as ENTITY_TYPE," +
 							" 		concat(ENTITY_SUB_TYPE,' ')ENTITY_SUB_TYPE,concat(ENTITY_DESC,' ')ENTITY_DESC,	(SELECT concat(AGENCY_NAME,' ') FROM COM_AGENCY_M  WHERE AGENCY_CODE = EXTERNAL_APPRAISER) as EXTERNAL_APPRAISER " +
 							" 		FROM cr_deal_verification_dtl V " +
 							" 		JOIN CR_LOAN_DTL L ON(V.LOAN_ID=L.LOAN_ID)	" +
 							" 		WHERE V.LOAN_ID= "+loanId+" and VERIFICATION_ID="+verificationId+" " +
 							" )dtl on('a'='a')" +
 							" where c.ASSET_ID="+entityId+" and c.ASSET_COLLATERAL_CLASS='SBLC'";
 						break;					
 			case 'J': subQuery="Select CONCAT(c.ASSET_COLLATERAL_DESC,' ')ASSET_COLLATERAL_DESC,c.ASSET_COLLATERAL_VALUE,c.COLLATERAL_SECURITY_MARGIN ," +
 							" CASE c.SECURITY_TYPE WHEN 'm' THEN 'Mutual Funds ' WHEN 's' THEN 'Shares and Debentures ' WHEN 'b' THEN 'Bonds ' END AS SECURITY_TYPE," +
 							" c.SECURITY_CATEGORY, c.SECURITY_MARKET_VALUE,dtl.VERIFICATION_TYPE,dtl.VERIFICATION_SUBTYPE,dtl.ENTITY_TYPE,dtl.ENTITY_SUB_TYPE,dtl.ENTITY_DESC,dtl.EXTERNAL_APPRAISER " +
 							" from cr_asset_collateral_m c " +
 							" left join" +
 							" (" +
 							" 		SELECT L.LOAN_LOAN_AMOUNT DEAL_LOAN_AMOUNT,concat(VERIFICATION_TYPE,' ')VERIFICATION_TYPE,concat(VERIFICATION_SUBTYPE,' ')VERIFICATION_SUBTYPE," +
 							" 		case ENTITY_TYPE when 'PRAPPL' then 'APPLICANT ' when 'COAPPL' then 'COAPPLICANT ' else concat(ENTITY_TYPE,' ') end as ENTITY_TYPE," +
 							" 		concat(ENTITY_SUB_TYPE,' ')ENTITY_SUB_TYPE,concat(ENTITY_DESC,' ')ENTITY_DESC,	(SELECT concat(AGENCY_NAME,' ') FROM COM_AGENCY_M  WHERE AGENCY_CODE = EXTERNAL_APPRAISER) as EXTERNAL_APPRAISER " +
 							" 		FROM cr_deal_verification_dtl V " +
 							" 		JOIN CR_LOAN_DTL L ON(V.LOAN_ID=L.LOAN_ID)	" +
 							" 		WHERE V.LOAN_ID= "+loanId+" and VERIFICATION_ID="+verificationId+" " +
 							" )dtl on('a'='a')" +
 							" where c.ASSET_ID="+entityId+" and c.ASSET_COLLATERAL_CLASS='SECURITIES'";
 						break;					
 			case 'K':subQuery="Select concat(c.ASSET_COLLATERAL_DESC,' ')ASSET_COLLATERAL_DESC, c.ASSET_COLLATERAL_VALUE,  c.COLLATERAL_SECURITY_MARGIN," +
 							" case c.STOCK_TYPE when 'Master' then 'Semi Finished ' when 'Master' then 'Finished ' end as STOCK_TYPE," +
 							" case c.STOCK_NATURE when 'Master' then 'Perishable ' when 'Master' then 'Non Perishable ' end as STOCK_NATURE,concat(c.STOCK_GODOWN_ADDRESS,' ')STOCK_GODOWN_ADDRESS," +
 							" concat(c.STOCK_INVENTORY_CYCLE,' ')STOCK_INVENTORY_CYCLE,dtl.VERIFICATION_TYPE,dtl.VERIFICATION_SUBTYPE,dtl.ENTITY_TYPE,dtl.ENTITY_SUB_TYPE,dtl.ENTITY_DESC," +
 							" dtl.EXTERNAL_APPRAISER " +
 							" from cr_asset_collateral_m c " +
 							" left join" +
 							" (" +
 							" 		SELECT L.LOAN_LOAN_AMOUNT DEAL_LOAN_AMOUNT,concat(VERIFICATION_TYPE,' ')VERIFICATION_TYPE,concat(VERIFICATION_SUBTYPE,' ')VERIFICATION_SUBTYPE," +
 							" 		case ENTITY_TYPE when 'PRAPPL' then 'APPLICANT ' when 'COAPPL' then 'COAPPLICANT ' else concat(ENTITY_TYPE,' ') end as ENTITY_TYPE," +
 							" 		concat(ENTITY_SUB_TYPE,' ')ENTITY_SUB_TYPE,concat(ENTITY_DESC,' ')ENTITY_DESC,	(SELECT concat(AGENCY_NAME,' ') FROM COM_AGENCY_M  WHERE AGENCY_CODE = EXTERNAL_APPRAISER) as EXTERNAL_APPRAISER " +
 							" 		FROM cr_deal_verification_dtl V " +
 							" 		JOIN CR_LOAN_DTL L ON(V.LOAN_ID=L.LOAN_ID)	" +
 							" 		WHERE V.LOAN_ID= "+loanId+" and VERIFICATION_ID="+verificationId+" " +
 							" )dtl on('a'='a')" +
 							" where c.ASSET_ID="+entityId+" and c.ASSET_COLLATERAL_CLASS='STOCK'";
 						break;					
 			case 'L': subQuery="Select (select concat(AGENCY_NAME,' ') from com_agency_m where agency_type='INS' and REC_STATUS='A' AND agency_code=C.INSURANCE_AGENCY)INSURANCE_AGENCY_NAME," +
 							" concat(c.ASSET_COLLATERAL_DESC,' ')ASSET_COLLATERAL_DESC,concat(C.COVER_NOTE,' ')COVER_NOTE,concat(C.INSURANCE_POLICY_NO,' ')INSURANCE_POLICY_NO,C.INSURANCE_POLICY_START_DATE" +
 							" ,C.INSURANCE_MATURITY_DATE,C.SUM_ASSURED ,c.ASSET_COLLATERAL_VALUE,C.INSURANCE_PREMIUM_AMOUNT,case C.INSURANCE_PREMIUM_FREQUENCY when 'MONTHLY' then 'MONTHLY ' when 'QUARTERLY' " +
 							" then 'QUARTERLY ' when 'HALFYEARLY' then 'HALF YEARLY ' when 'ANNUALY' then 'ANNUALY ' end as INSURANCE_PREMIUM_FREQUENCY,C.INSURANCE_NOMINEE,case C.INSURANCE_RELATION_WITH_NOMINEE " +
 							" when 'SIS' then 'SISTER ' else 'OTHER '  end as INSURANCE_RELATION_WITH_NOMINEE,C.INSURANCE_TENURE,dtl.VERIFICATION_TYPE,dtl.VERIFICATION_SUBTYPE," +
 							" dtl.ENTITY_TYPE,dtl.ENTITY_SUB_TYPE,dtl.ENTITY_DESC,dtl.EXTERNAL_APPRAISER " +
 							" from cr_asset_collateral_m c " +
 							" left join" +
 							" (" +
 							" 		SELECT L.LOAN_LOAN_AMOUNT DEAL_LOAN_AMOUNT,concat(VERIFICATION_TYPE,' ')VERIFICATION_TYPE,concat(VERIFICATION_SUBTYPE,' ')VERIFICATION_SUBTYPE," +
 							" 		case ENTITY_TYPE when 'PRAPPL' then 'APPLICANT ' when 'COAPPL' then 'COAPPLICANT ' else concat(ENTITY_TYPE,' ') end as ENTITY_TYPE," +
 							" 		concat(ENTITY_SUB_TYPE,' ')ENTITY_SUB_TYPE,concat(ENTITY_DESC,' ')ENTITY_DESC,	(SELECT concat(AGENCY_NAME,' ') FROM COM_AGENCY_M  WHERE AGENCY_CODE = EXTERNAL_APPRAISER) as EXTERNAL_APPRAISER " +
 							" 		FROM cr_deal_verification_dtl V " +
 							" 		JOIN CR_LOAN_DTL L ON(V.LOAN_ID=L.LOAN_ID)	" +
 							" 		WHERE V.LOAN_ID= "+loanId+" and VERIFICATION_ID="+verificationId+" " +
 							" )dtl on('a'='a')" +
 							" where c.ASSET_ID="+entityId+" and c.ASSET_COLLATERAL_CLASS='INSURANCE'";
 						break;	
 			case 'M': subQuery="Select "+
 							" dtl.VERIFICATION_TYPE,dtl.VERIFICATION_SUBTYPE,dtl.ENTITY_TYPE,dtl.ENTITY_SUB_TYPE,dtl.ENTITY_DESC,dtl.EXTERNAL_APPRAISER " +
 							" from "+
 							" ( " +
 							" 		SELECT L.DEAL_LOAN_AMOUNT,concat(VERIFICATION_TYPE,' ')VERIFICATION_TYPE,concat(VERIFICATION_SUBTYPE,' ')VERIFICATION_SUBTYPE" +
 							" 		,case ENTITY_TYPE when 'PRAPPL' then 'APPLICANT ' when 'COAPPL' then 'COAPPLICANT ' else concat(ENTITY_TYPE,' ') end as ENTITY_TYPE," +
 							" 		concat(ENTITY_SUB_TYPE,' ')ENTITY_SUB_TYPE,concat(ENTITY_DESC,' ')ENTITY_DESC,(SELECT concat(AGENCY_NAME,' ') FROM COM_AGENCY_M  WHERE AGENCY_CODE = EXTERNAL_APPRAISER) as EXTERNAL_APPRAISER " +
 							" 		FROM cr_deal_verification_dtl V " +
 							" 		JOIN CR_DEAL_LOAN_DTL L ON(V.DEAL_ID=L.DEAL_ID)	" +
 							" 		WHERE V.LOAN_ID= "+loanId+" and VERIFICATION_ID="+verificationId+" " +
 							" ) dtl ";
 						break;	
 			case 'N': subQuery="select CUSTOMER_ID,concat(CUSTOMER_FNAME,' ')CUSTOMER_FNAME,concat(CUSTOMER_MNAME,' ')CUSTOMER_MNAME,concat(CUSTOMER_LNAME,' ')CUSTOMER_LNAME," +
 							" CASE GENDER WHEN 'F' THEN 'FEMALE ' WHEN 'M' THEN 'MALE ' END AS GENDER,concat(CUSTOMER_CATEGORY,' ')CUSTOMER_CATEGORY,concat(CUSTOMER_CONSTITUTION,' ')CUSTOMER_CONSTITUTION," +
 							" concat(FATHER_HUSBAND_NAME,' ')FATHER_HUSBAND_NAME,CUSTOMER_DOB,CASE CASTE_CATEGORY WHEN 'G' THEN 'General ' WHEN 'OBC' THEN 'Other Backward Cast(OBC) ' WHEN 'SC' THEN 'Scheduled Caste(SC)' " +
 							" WHEN 'ST' THEN 'Schedule tribe(ST)' END AS CASTE_CATEGORY,case MARITAL_STATUS when 'M' then 'Married ' when 'U' then 'Un-Married ' end as MARITAL_STATUS,concat(CUSTOMER_EMAIL,' ')CUSTOMER_EMAIL," +
 							" concat(C.CUSTMER_PAN,' ')CUSTMER_PAN,concat(C.DRIVING_LICENSE,' ')DRIVING_LICENSE,concat(C.VOTER_ID,' ')VOTER_ID,concat(C.PASSPORT_NUMBER,' ')PASSPORT_NUMBER,concat(C.UID_NO,' ')UID_NO,concat(C.OTHER_NO,' ')OTHER_NO," +
 							" case C.OTHOR_RELATIONSHIP_TYPE when 'CS' then 'Customer ' when 'SU' then 'Supplier ' when 'MF' then 'Manufacturer ' end as OTHOR_RELATIONSHIP_TYPE,case C.EDU_DETAIL when 'GRAD' then 'GRADUATE ' when 'OTH' then 'OTHER ' " +
 							" when 'PG' then 'POST GRADUATE ' when 'PRO' then 'PROFESSIONAL ' when 'UG' then 'UNDER GRADUATE ' end as EDU_DETAIL," +
 							" dtl.VERIFICATION_TYPE,dtl.VERIFICATION_SUBTYPE,dtl.ENTITY_TYPE,dtl.ENTITY_SUB_TYPE,dtl.ENTITY_DESC," +
 							" dtl.EXTERNAL_APPRAISER " +
 							" from cr_deal_customer_m c " +
 							" left join " +
 							" (" +
 							" 		SELECT L.DEAL_LOAN_AMOUNT,concat(VERIFICATION_TYPE,' ')VERIFICATION_TYPE,concat(VERIFICATION_SUBTYPE,' ')VERIFICATION_SUBTYPE,case ENTITY_TYPE when 'PRAPPL' then 'APPLICANT ' " +
 							" 		when 'COAPPL' then 'COAPPLICANT ' else concat(ENTITY_TYPE,' ') end as ENTITY_TYPE,concat(ENTITY_SUB_TYPE,' ')ENTITY_SUB_TYPE,concat(ENTITY_DESC,' ')ENTITY_DESC,(SELECT concat(AGENCY_NAME,' ') FROM COM_AGENCY_M  WHERE AGENCY_CODE = EXTERNAL_APPRAISER) as EXTERNAL_APPRAISER " +
 							" 		FROM cr_deal_verification_dtl V " +
 							" 		JOIN CR_DEAL_LOAN_DTL L ON(V.DEAL_ID=L.DEAL_ID)	" +
 							" 		WHERE V.LOAN_ID= "+loanId+" and VERIFICATION_ID="+verificationId+" " +
 							" )dtl on('a'='a')where CUSTOMER_ID="+entityId+" ";
 							break;
			case 'Q' :
					if(dbType.equalsIgnoreCase("MSSQL"))
					{
						subQuery="select CUSTOMER_ID, concat(CUSTOMER_FNAME,' ')CUSTOMER_FNAME,concat(CUSTOMER_MNAME,' ')CUSTOMER_MNAME,concat(CUSTOMER_LNAME,' ')CUSTOMER_LNAME,CASE GENDER WHEN 'F' THEN 'FEMALE ' WHEN 'M' THEN 'MALE ' END AS GENDER,concat(CUSTOMER_CATEGORY,' ')CUSTOMER_CATEGORY,concat(CUSTOMER_CONSTITUTION,' ')CUSTOMER_CONSTITUTION," +
						"concat(FATHER_HUSBAND_NAME,' ')FATHER_HUSBAND_NAME,dbo.DATE_FORMAT(CUSTOMER_DOB,'%d-%m-%Y') as CUSTOMER_DOB,concat(DATEDIFF(year,CUSTOMER_DOB,dbo.STR_TO_DATE('"+p_printed_date+"','%d-%m-%Y %H:%i')),' Years ') as CURRENT_AGE," +
						"CASE CASTE_CATEGORY WHEN 'G' THEN 'General ' WHEN 'OBC' THEN 'Other Backward Cast(OBC) ' WHEN 'SC' THEN 'Scheduled Caste(SC)' WHEN 'ST' THEN 'Schedule tribe(ST)' END AS CASTE_CATEGORY," +
						"case MARITAL_STATUS when 'M' then 'Married ' when 'U' then 'Un-Married ' end as MARITAL_STATUS,concat(CUSTOMER_EMAIL,' ')CUSTOMER_EMAIL,concat(CUSTMER_PAN,' ')CUSTMER_PAN,concat(C.DRIVING_LICENSE,' ')DRIVING_LICENSE,concat(C.VOTER_ID,' ')VOTER_ID, concat(C.PASSPORT_NUMBER,' ')PASSPORT_NUMBER,concat(C.UID_NO,' ')UID_NO,concat(C.OTHER_NO,' ')OTHER_NO," +
						"case OTHOR_RELATIONSHIP_TYPE when 'CS' then 'Customer ' when 'SU' then 'Supplier ' when 'MF' then 'Manufacturer ' end as OTHOR_RELATIONSHIP_TYPE,case C.EDU_DETAIL when 'GRAD' then 'GRADUATE ' when 'OTH' then 'OTHER ' " +
						" when 'PG' then 'POST GRADUATE ' when 'PRO' then 'PROFESSIONAL ' when 'UG' then 'UNDER GRADUATE ' end as EDU_DETAIL," +
						" dtl.VERIFICATION_TYPE,dtl.VERIFICATION_SUBTYPE,dtl.ENTITY_TYPE,dtl.ENTITY_SUB_TYPE,dtl.ENTITY_DESC," +
						" dtl.EXTERNAL_APPRAISER " +
						" from cr_deal_customer_m c " +
						" left join " +
							" (" +
							" 		SELECT L.DEAL_LOAN_AMOUNT,concat(VERIFICATION_TYPE,' ')VERIFICATION_TYPE,concat(VERIFICATION_SUBTYPE,' ')VERIFICATION_SUBTYPE,case ENTITY_TYPE when 'PRAPPL' then 'APPLICANT ' " +
							" 		when 'COAPPL' then 'COAPPLICANT ' else concat(ENTITY_TYPE,' ') end as ENTITY_TYPE,concat(ENTITY_SUB_TYPE,' ')ENTITY_SUB_TYPE,concat(ENTITY_DESC,' ')ENTITY_DESC,(SELECT concat(AGENCY_NAME,' ') FROM COM_AGENCY_M  WHERE AGENCY_CODE = EXTERNAL_APPRAISER) as EXTERNAL_APPRAISER " +
							" 		FROM cr_deal_verification_dtl V " +
							" 		JOIN CR_DEAL_LOAN_DTL L ON(V.DEAL_ID=L.DEAL_ID)	" +
							" 		WHERE V.LOAN_ID= "+loanId+" and VERIFICATION_ID="+verificationId+" " +
							" )dtl on('a'='a')where CUSTOMER_ID="+entityId+" ";
					}
					else
					{
							subQuery="select CUSTOMER_ID, concat(CUSTOMER_FNAME,' ')CUSTOMER_FNAME,concat(CUSTOMER_MNAME,' ')CUSTOMER_MNAME,concat(CUSTOMER_LNAME,' ')CUSTOMER_LNAME,CASE GENDER WHEN 'F' THEN 'FEMALE ' WHEN 'M' THEN 'MALE ' END AS GENDER,concat(CUSTOMER_CATEGORY,' ')CUSTOMER_CATEGORY,concat(CUSTOMER_CONSTITUTION,' ')CUSTOMER_CONSTITUTION," +
									"concat(FATHER_HUSBAND_NAME,' ')FATHER_HUSBAND_NAME,DATE_FORMAT(CUSTOMER_DOB,'%d-%m-%Y') as CUSTOMER_DOB,concat(FLOOR(DATEDIFF(STR_TO_DATE('"+p_printed_date+"','%d-%m-%Y %H:%i'),CUSTOMER_DOB)/365),' Years ') as CURRENT_AGE," +
									"CASE CASTE_CATEGORY WHEN 'G' THEN 'General ' WHEN 'OBC' THEN 'Other Backward Cast(OBC) ' WHEN 'SC' THEN 'Scheduled Caste(SC)' WHEN 'ST' THEN 'Schedule tribe(ST)' END AS CASTE_CATEGORY," +
									"case MARITAL_STATUS when 'M' then 'Married ' when 'U' then 'Un-Married ' end as MARITAL_STATUS,concat(CUSTOMER_EMAIL,' ')CUSTOMER_EMAIL,concat(CUSTMER_PAN,' ')CUSTMER_PAN,concat(C.DRIVING_LICENSE,' ')DRIVING_LICENSE,concat(C.VOTER_ID,' ')VOTER_ID, concat(C.PASSPORT_NUMBER,' ')PASSPORT_NUMBER,concat(C.UID_NO,' ')UID_NO,concat(C.OTHER_NO,' ')OTHER_NO," +
									"case OTHOR_RELATIONSHIP_TYPE when 'CS' then 'Customer ' when 'SU' then 'Supplier ' when 'MF' then 'Manufacturer ' end as OTHOR_RELATIONSHIP_TYPE,case C.EDU_DETAIL when 'GRAD' then 'GRADUATE ' when 'OTH' then 'OTHER ' " +
									" when 'PG' then 'POST GRADUATE ' when 'PRO' then 'PROFESSIONAL ' when 'UG' then 'UNDER GRADUATE ' end as EDU_DETAIL," +
									" dtl.VERIFICATION_TYPE,dtl.VERIFICATION_SUBTYPE,dtl.ENTITY_TYPE,dtl.ENTITY_SUB_TYPE,dtl.ENTITY_DESC," +
									" dtl.EXTERNAL_APPRAISER " +
									" from cr_deal_customer_m c " +
									" left join " +
		 							" (" +
		 							" 		SELECT L.DEAL_LOAN_AMOUNT,concat(VERIFICATION_TYPE,' ')VERIFICATION_TYPE,concat(VERIFICATION_SUBTYPE,' ')VERIFICATION_SUBTYPE,case ENTITY_TYPE when 'PRAPPL' then 'APPLICANT ' " +
		 							" 		when 'COAPPL' then 'COAPPLICANT ' else concat(ENTITY_TYPE,' ') end as ENTITY_TYPE,concat(ENTITY_SUB_TYPE,' ')ENTITY_SUB_TYPE,concat(ENTITY_DESC,' ')ENTITY_DESC,(SELECT concat(AGENCY_NAME,' ') FROM COM_AGENCY_M  WHERE AGENCY_CODE = EXTERNAL_APPRAISER) as EXTERNAL_APPRAISER " +
		 							" 		FROM cr_deal_verification_dtl V " +
		 							" 		JOIN CR_DEAL_LOAN_DTL L ON(V.DEAL_ID=L.DEAL_ID)	" +
		 							" 		WHERE V.LOAN_ID= "+loanId+" and VERIFICATION_ID="+verificationId+" " +
		 							" )dtl on('a'='a')where CUSTOMER_ID="+entityId+" ";
					}
					break;
				
			case 'R' :
				if(dbType.equalsIgnoreCase("MSSQL"))
				{
					subQuery="select c.CUSTOMER_ID,case CUSTOMER_GROUP_TYPE when 'E' then 'Existing ' else 'New ' end as CUSTOMER_GROUP_TYPE,concat(g.GROUP_DESC,' ')GROUP_DESC,concat(c.CUSTOMER_NAME,' ')CUSTOMER_NAME,concat((select DESCRIPTION FROM  generic_master WHERE GENERIC_KEY='CUST_CATEGORY' and rec_status ='A' and VALUE=c.CUSTOMER_CATEGORY),' ')CUSTOMER_CATEGORY,dbo.DATE_FORMAT(c.CUSTOMER_DOB,'%d-%m-%Y') as DATE_OF_INCORPORATION,concat((select DESCRIPTION FROM  generic_master WHERE GENERIC_KEY='CUST_CONSTITUTION'  and PARENT_VALUE='CORP' and rec_status ='A' and VALUE=c.CUSTOMER_CONSTITUTION),' ')CUSTOMER_CONSTITUTION," +
							"concat(c.CUSTOMER_REGISTRATION_NO,' ')CUSTOMER_REGISTRATION_NO,concat(c.CUSTMER_PAN,' ')CUSTMER_PAN,concat(c.CUSTOMER_VAT_NO,' ')CUSTOMER_VAT_NO,concat((select DESCRIPTION FROM  generic_master WHERE GENERIC_KEY='CUST_BUS_SEGMENT' and rec_status ='A' and VALUE=CUSTOMER_BUSINESS_SEGMENT),' ')CUSTOMER_BUSINESS_SEGMENT,concat(i.INDUSTRY_DESC,' ')INDUSTRY_DESC,concat(si.SUB_INDUSTRY_DESC,' ')SUB_INDUSTRY_DESC,concat(c.CUSTOMER_EMAIL,' ')CUSTOMER_EMAIL,concat(c.CUSTOMER_WEBSITE,' ')CUSTOMER_WEBSITE," +
							"concat((select DESCRIPTION FROM  generic_master WHERE GENERIC_KEY='NATURE_OF_BUSINESS' and rec_status ='A' and VALUE=c.NATURE_OF_BUSINESS),' ')NATURE_OF_BUSINESS,concat(c.YEAR_OF_ESTBLISHMENT,' ')YEAR_OF_ESTBLISHMENT,concat(c.SHOP_ESTABLISHMENT_NO,' ')SHOP_ESTABLISHMENT_NO,concat(c.SALES_TAX_TIN_NO,' ')SALES_TAX_TIN_NO,concat(c.DGFT_NO,' ')DGFT_NO," +
							"concat(c.NO_BV_YEARS,' Year ',c.NO_BV_MONTHS,' Month ')as BUSINESS_VINTAGE,case OTHOR_RELATIONSHIP_TYPE when 'CS' then 'Customer ' when 'SU' then 'Supplier ' when 'MF' then 'Manufacturer ' end as OTHOR_RELATIONSHIP_TYPE,c.CUSTOMER_REFERENCE," +
							"dtl.VERIFICATION_TYPE,dtl.VERIFICATION_SUBTYPE,dtl.ENTITY_TYPE,dtl.ENTITY_SUB_TYPE,dtl.ENTITY_DESC,dtl.EXTERNAL_APPRAISER  " +
							"from cr_deal_customer_m c " +
							"left join gcd_group_m g on c.CUSTOMER_GROUP_ID=g.GROUP_ID " +
							"inner join com_industry_m i on c.CUSTOMER_INDUSTRY=i.INDUSTRY_ID " +
							"inner join com_sub_industry_m si on si.sub_industry_id=c.CUSTOMER_SUB_INDUSTRY " +
							" left join " +
 							" (" +
 							" 		SELECT L.DEAL_LOAN_AMOUNT,concat(VERIFICATION_TYPE,' ')VERIFICATION_TYPE,concat(VERIFICATION_SUBTYPE,' ')VERIFICATION_SUBTYPE,case ENTITY_TYPE when 'PRAPPL' then 'APPLICANT ' " +
 							" 		when 'COAPPL' then 'COAPPLICANT ' else concat(ENTITY_TYPE,' ') end as ENTITY_TYPE,concat(ENTITY_SUB_TYPE,' ')ENTITY_SUB_TYPE,concat(ENTITY_DESC,' ')ENTITY_DESC,(SELECT concat(AGENCY_NAME,' ') FROM COM_AGENCY_M  WHERE AGENCY_CODE = EXTERNAL_APPRAISER) as EXTERNAL_APPRAISER " +
 							" 		FROM cr_deal_verification_dtl V " +
 							" 		JOIN CR_DEAL_LOAN_DTL L ON(V.DEAL_ID=L.DEAL_ID)	" +
 							" 		WHERE V.LOAN_ID= "+loanId+" and VERIFICATION_ID="+verificationId+" " +
 							" )dtl on('a'='a')where CUSTOMER_ID="+entityId+" ";
				}
				else
				{
					subQuery="select c.CUSTOMER_ID,case CUSTOMER_GROUP_TYPE when 'E' then 'Existing ' else 'New ' end as CUSTOMER_GROUP_TYPE,concat(g.GROUP_DESC,' ')GROUP_DESC,concat(c.CUSTOMER_NAME,' ')CUSTOMER_NAME,concat((select DESCRIPTION FROM  generic_master WHERE GENERIC_KEY='CUST_CATEGORY' and rec_status ='A' and VALUE=c.CUSTOMER_CATEGORY),' ')CUSTOMER_CATEGORY,DATE_FORMAT(c.CUSTOMER_DOB,'%d-%m-%Y') as DATE_OF_INCORPORATION,concat((select DESCRIPTION FROM  generic_master WHERE GENERIC_KEY='CUST_CONSTITUTION'  and PARENT_VALUE='CORP' and rec_status ='A' and VALUE=c.CUSTOMER_CONSTITUTION),' ')CUSTOMER_CONSTITUTION," +
					"concat(c.CUSTOMER_REGISTRATION_NO,' ')CUSTOMER_REGISTRATION_NO,concat(c.CUSTMER_PAN,' ')CUSTMER_PAN,concat(c.CUSTOMER_VAT_NO,' ')CUSTOMER_VAT_NO,concat((select DESCRIPTION FROM  generic_master WHERE GENERIC_KEY='CUST_BUS_SEGMENT' and rec_status ='A' and VALUE=CUSTOMER_BUSINESS_SEGMENT),' ')CUSTOMER_BUSINESS_SEGMENT,concat(i.INDUSTRY_DESC,' ')INDUSTRY_DESC,concat(si.SUB_INDUSTRY_DESC,' ')SUB_INDUSTRY_DESC,concat(c.CUSTOMER_EMAIL,' ')CUSTOMER_EMAIL,concat(c.CUSTOMER_WEBSITE,' ')CUSTOMER_WEBSITE," +
					"concat((select DESCRIPTION FROM  generic_master WHERE GENERIC_KEY='NATURE_OF_BUSINESS' and rec_status ='A' and VALUE=c.NATURE_OF_BUSINESS),' ')NATURE_OF_BUSINESS,concat(c.YEAR_OF_ESTBLISHMENT,' ')YEAR_OF_ESTBLISHMENT,concat(c.SHOP_ESTABLISHMENT_NO,' ')SHOP_ESTABLISHMENT_NO,concat(c.SALES_TAX_TIN_NO,' ')SALES_TAX_TIN_NO,concat(c.DGFT_NO,' ')DGFT_NO," +
					"concat(c.NO_BV_YEARS,' Year ',c.NO_BV_MONTHS,' Month ')as BUSINESS_VINTAGE,case OTHOR_RELATIONSHIP_TYPE when 'CS' then 'Customer ' when 'SU' then 'Supplier ' when 'MF' then 'Manufacturer ' end as OTHOR_RELATIONSHIP_TYPE,c.CUSTOMER_REFERENCE," +
					"dtl.VERIFICATION_TYPE,dtl.VERIFICATION_SUBTYPE,dtl.ENTITY_TYPE,dtl.ENTITY_SUB_TYPE,dtl.ENTITY_DESC,dtl.EXTERNAL_APPRAISER  " +
					"from cr_deal_customer_m c " +
					"left join gcd_group_m g on c.CUSTOMER_GROUP_ID=g.GROUP_ID " +
					"inner join com_industry_m i on c.CUSTOMER_INDUSTRY=i.INDUSTRY_ID " +
					"inner join com_sub_industry_m si on si.sub_industry_id=c.CUSTOMER_SUB_INDUSTRY " +
					" left join " +
					" (" +
					" 	SELECT L.DEAL_LOAN_AMOUNT,concat(VERIFICATION_TYPE,' ')VERIFICATION_TYPE,concat(VERIFICATION_SUBTYPE,' ')VERIFICATION_SUBTYPE,case ENTITY_TYPE when 'PRAPPL' then 'APPLICANT ' " +
					" 	when 'COAPPL' then 'COAPPLICANT ' else concat(ENTITY_TYPE,' ') end as ENTITY_TYPE,concat(ENTITY_SUB_TYPE,' ')ENTITY_SUB_TYPE,concat(ENTITY_DESC,' ')ENTITY_DESC,(SELECT concat(AGENCY_NAME,' ') FROM COM_AGENCY_M  WHERE AGENCY_CODE = EXTERNAL_APPRAISER) as EXTERNAL_APPRAISER " +
					" 	FROM cr_deal_verification_dtl V " +
					" 	JOIN CR_DEAL_LOAN_DTL L ON(V.DEAL_ID=L.DEAL_ID)	" +
					"	WHERE V.LOAN_ID= "+loanId+" and VERIFICATION_ID="+verificationId+" " +
					" )dtl on('a'='a')where CUSTOMER_ID="+entityId+" ";
				}
					break;
 			case 'P': 
						{
							String collectedDocsQ = "select DISTINCT DOC_CHILD_IDS from cr_document_dtl where TXN_DOC_ID= '"+entityId+"' ";
							logger.info("In downloadVarificationForm(String txnDocId, String txnId,String txnType) query AT CM: "+ collectedDocsQ);
						    String collectedDocs = CommonFunction.checkNull(ConnectionDAO.singleReturn(collectedDocsQ)).trim();
						    collectedDocsQ=null;
						    String coll="";
						    if(CommonFunction.checkNull(collectedDocs).trim().length()!=0)
						    {
						    	String[] collects = collectedDocs.split("\\|");								
						    	for (int i = 0; i < collects.length; i++) 
								coll+=","+collects[i];
						    }
							coll=coll+",0";
							String docChildId= coll.substring(1, coll.length());
							subQuery="select case when (DOC_CHILD_ID is null) then 'N' else 'Y' end as present,DOC_CHILD_ID,concat(DOC_DESC,' ')DOC_DESC, " +
									 " dtl.VERIFICATION_TYPE,dtl.VERIFICATION_SUBTYPE,dtl.ENTITY_TYPE,dtl.ENTITY_SUB_TYPE,dtl.ENTITY_DESC," +
									 " dtl.EXTERNAL_APPRAISER " +
									 "FROM " +
									 " (" +
									 " 		SELECT L.DEAL_LOAN_AMOUNT,concat(VERIFICATION_TYPE,' ')VERIFICATION_TYPE,concat(VERIFICATION_SUBTYPE,' ')VERIFICATION_SUBTYPE,case ENTITY_TYPE when 'PRAPPL' then 'APPLICANT ' " +
			 						 " 		when 'COAPPL' then 'COAPPLICANT ' else concat(ENTITY_TYPE,' ') end as ENTITY_TYPE,concat(ENTITY_SUB_TYPE,' ')ENTITY_SUB_TYPE,concat(ENTITY_DESC,' ')ENTITY_DESC,(SELECT concat(AGENCY_NAME,' ') FROM COM_AGENCY_M  WHERE AGENCY_CODE = EXTERNAL_APPRAISER) as EXTERNAL_APPRAISER " +
			 						 " 		FROM cr_deal_verification_dtl V " +
			 						 " 		JOIN CR_DEAL_LOAN_DTL L ON(V.DEAL_ID=L.DEAL_ID)	" +
			 						 " 		WHERE V.LOAN_ID= "+loanId+" and VERIFICATION_ID="+verificationId+" " +
									 " )dtl " +
									 "left join CR_DOCUMENT_CHILD_M a on (1=1 and DOC_CHILD_ID IN ("+docChildId+") )";
					}
 			}
 		
 		logger.info("query            :  "+query);
 		logger.info("subQuery         :  "+subQuery);
 		Connection connectDatabase = ConnectionDAO.getConnection();		
 		Map<Object,Object> hashMap = new HashMap<Object,Object>();
 		hashMap.put("query",query);
 		hashMap.put("subQuery",subQuery);
 		hashMap.put("root","Loan");
 		hashMap.put("p_company_logo",p_company_logo);
 		hashMap.put("p_printed_by",p_printed_by);
 		hashMap.put("p_printed_date",p_printed_date);
 		hashMap.put("p_company_name",p_company_name);
 		hashMap.put("checkImage",checkImage);
 		hashMap.put("uncheckImage",uncheckImage);
 		hashMap.put("SUBREPORT_DIR",SUBREPORT_DIR);
 		hashMap.put("source",sou);
 		hashMap.put("IS_IGNORE_PAGINATION",true);
 		
 		logger.info("entityId         :  "+entityId);
 		logger.info("loanId           :  "+loanId);
 		logger.info("verificationId   :  "+verificationId);
 		logger.info("p_company_logo   :  "+p_company_logo);
 		logger.info("p_printed_by     :  "+p_printed_by);
 		logger.info("p_printed_date   :  "+p_printed_date);
 		logger.info("p_company_name   :  "+p_company_name);
 		logger.info("uncheckImage     :  "+uncheckImage);
 		logger.info("SUBREPORT_DIR    :  "+SUBREPORT_DIR);
 		logger.info("source           :  "+sou);	
 		
 							
 		
 		InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath + reportName + ".jasper");
 		JasperPrint jasperPrint = null;
 		try
 		{
 			jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
 			JasperExportManager.exportReportToPdfFile(jasperPrint,request.getRealPath("/reports") + "/" +reportName+".pdf");
 			File f=new File(request.getRealPath("/reports") + "/" +reportName+".pdf");
 			FileInputStream fin = new FileInputStream(f);
 			ServletOutputStream outStream = response.getOutputStream();
 			response.setContentType("application/pdf");
 			response.setHeader("Content-Disposition", "attachment;filename='"+reportName+"'.pdf");
 			byte[] buffer = new byte[1024];
 			int n = 0;
 			while ((n = fin.read(buffer)) != -1) 
 				outStream.write(buffer, 0, n);			
 			outStream.flush();
 			fin.close();
 			outStream.close();
 		
 		}
 		catch(Exception e)
 		{
 			e.printStackTrace();
 		}
 		finally
 		{
 			ConnectionDAO.closeConnection(connectDatabase, null);
 			hashMap.clear();
 			
 		}
 		return null;				
 		
 	}
     
     public ActionForward searchFieldCaptureAtCM(ActionMapping mapping,
 			ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {

 		logger.info("In searchFieldCaptureAtCM..");
 		HttpSession session = request.getSession();
 		UserObject userobj=(UserObject)session.getAttribute("userobject");
 		if(userobj==null){
 			logger.info("here in searchFieldCaptureAtCM method of VerificationCapturingDispatchAction action the session is out----------------");
 			return mapping.findForward("sessionOut");
 		}
 		Object sessionId = session.getAttribute("sessionID");
 		FieldVerificationVo vo = new FieldVerificationVo();

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

 		DynaValidatorForm CommonDealDynaValidatorForm = (DynaValidatorForm) form;
 		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,CommonDealDynaValidatorForm);

 		String userName=request.getParameter("userId");
 		vo.setUserName(userName);
 		if(userName.trim().length()==0)
 		{
 			vo.setUserName(userobj.getUserName());
 		}
 		ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
 		String dateFormat = resource.getString("lbl.dateFormat(dd-mm-yyyy)");
 		
 		String userId=vo.getReportingToUserId();
 		if(userobj != null)
 		{
 			vo.setBranchId(userobj.getBranchId());
 			if(userId.trim().length()==0)
 				userId=userobj.getUserId();				
 		}
 		vo.setUserId(userId);
 		
 		if (vo.getApplicationDate().equalsIgnoreCase(dateFormat))
 			vo.setApplicationDate("");
 		
 		FieldVerificationDAO fieldDao=(FieldVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(FieldVerificationDAO.IDENTITY);
         logger.info("Implementation class: "+fieldDao.getClass()); 	// changed by asesh	
 		ArrayList capturedetails=new ArrayList();		
 		logger.info("current page link .......... "+request.getParameter("d-49520-p"));		
 		int currentPageLink = 0;
 		if(request.getParameter("d-49520-p")==null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
 			currentPageLink=1;
 		else
 			currentPageLink =Integer.parseInt(request.getParameter("d-49520-p"));
 		logger.info("current page link ................ "+request.getParameter("d-49520-p"));
 		vo.setCurrentPageLink(currentPageLink);
 		String functionId="";
		String ID="";
		if(session.getAttribute("functionId")!=null)
		{
			functionId=session.getAttribute("functionId").toString();
		}
		logger.info("functionId: "+functionId);
		vo.setFunctionId(functionId);
 		capturedetails= fieldDao.searchDealForCaptureAtCM(vo);
 		logger.info("In searchDealCapturing....list: "+capturedetails.size());
 		logger.info("list.isEmpty(): "+capturedetails.size());
 		
 		request.setAttribute("defaultlist", capturedetails);		
 		return mapping.findForward("captureSearch");
 	}
     
     public ActionForward searchVerificationCompletionAtCM(ActionMapping mapping,
 			ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception 
 	{
 		logger.info("In searchVerificationCompletion(searchVerificationCompletionAtCM).....");
 		HttpSession session = request.getSession();
 		UserObject userobj=(UserObject)session.getAttribute("userobject");
 		String userId="";
 		String branchId="";
 		if(userobj != null){
 			branchId=userobj.getBranchId();
 			userId=userobj.getUserId();
 		}else{
 			logger.info("here in searchVerificationCompletionAtCM method of fieldVerificationDispatchAction action the session is out----------------");
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
 		DynaValidatorForm CommonDealDynaValidatorForm = (DynaValidatorForm) form;
 		ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
 		String dateFormat = resource.getString("lbl.dateFormat(dd-mm-yyyy)");
 		FieldVerificationVo vo = new FieldVerificationVo();	
 		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,CommonDealDynaValidatorForm);
 		
 		
 	
 		vo.setUserId(userId);
 		vo.setBranchId(branchId);
 		if (vo.getApplicationDate().equalsIgnoreCase(dateFormat))
 		{
 			vo.setApplicationDate("");
 		}
 		FieldVerificationDAO fieldDao=(FieldVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(FieldVerificationDAO.IDENTITY);
         logger.info("Implementation class: "+fieldDao.getClass()); 	// changed by asesh
 		ArrayList capturedetails=new ArrayList();		
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
		String functionId="";
		if(session.getAttribute("functionId")!=null)
		{
			functionId=session.getAttribute("functionId").toString();
		}
		logger.info("functionId: "+functionId);
		vo.setFunctionId(functionId);
 		capturedetails= fieldDao.searchVerificationCompletionAtCM(vo);		
 	    logger.info("In searchDealCapturing....list: "+capturedetails.size());		
 	   	logger.info("list.isEmpty(): "+capturedetails.size());
 	   	request.setAttribute("list", capturedetails);
 		request.setAttribute("search","search");
 		
 		return mapping.findForward("search_complet");
 	}	
	
     public ActionForward addFieldCompletionAtCM(ActionMapping mapping, ActionForm form,
 			HttpServletRequest request, HttpServletResponse response)	throws Exception 
 	{
 		
 		
 		HttpSession session = request.getSession();
 		UserObject userobj=(UserObject)session.getAttribute("userobject");
 		
 		String userID="";
 		
 		if(userobj!=null)
 		{
 			userID=userobj.getUserId();
 				
 		}else{
 			logger.info("here in addFieldCompletionAtCM method of fieldVerificationDispatchAction action the session is out----------------");
 			return mapping.findForward("sessionOut");
 		}
 		FieldVerificationVo vo=new FieldVerificationVo(); 		
 		DynaValidatorForm FieldVarificationDynaValidatorForm= (DynaValidatorForm)form;
 		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, FieldVarificationDynaValidatorForm);					
 	
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
 		FieldVerificationDAO dao=(FieldVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(FieldVerificationDAO.IDENTITY);
         logger.info("Implementation class: "+dao.getClass()); 	// changed by asesh
 		//FieldVerificationDAO dao=new FieldVerificationDAOImpl();	
 		String loanId = "";
 		loanId = CommonFunction.checkNull(request.getParameter("loanId"));
 		if(CommonFunction.checkNull(loanId).trim().equalsIgnoreCase(""))
 			loanId=(String)session.getAttribute("loanId");
 		else
 			session.removeAttribute("loanId");
 		
 		if(loanId!=null && !loanId.equalsIgnoreCase(""))
 		{
 			CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
 	        logger.info("Implementation class: "+service.getClass()); 	//changed by asesh
 	       LoanInitiationDAO daoHeader=(LoanInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(LoanInitiationDAO.IDENTITY);
	        logger.info("Implementation class: "+service.getClass()); 	//changed by asesh
	        
 			//CreditProcessingDAO service=new CreditProcessingDAOImpl();
 			ArrayList loanHeader = daoHeader.getLoanHeader(loanId);
 			session.setAttribute("loanHeader", loanHeader);
 			String functionId="";
 			String VerificationCapId="";
			String ID="";
 			if(session.getAttribute("functionId")!=null)
 			{
 				functionId=session.getAttribute("functionId").toString();
 			}
 			logger.info("functionId: "+functionId);
 			if(functionId.equals("8000238"))
 			{
 				request.setAttribute("funId","legal");
 			}
 			else if(functionId.equals("8000240"))
 			{
 				request.setAttribute("funId","technical");
 			}
 			//amandeep start for RCU New Link
 			else if(functionId.equals("8000242"))
 			{
 				request.setAttribute("funId","RCU");
 			}
 			//amandeep work ends for RCU new link
 			else
 			{
 				request.setAttribute("funId","verfication");
 			}
 			
 			ArrayList completionList = dao.getCompletionListAtCM(loanId);
	Iterator<FieldVerificationVo> it= completionList.iterator();
			String flag="";
			while(it.hasNext())
			{
				FieldVerificationVo  vo1 = (FieldVerificationVo) it.next();
				logger.info("vo1.getVerificaionType()---"+vo1.getVerificationType());
				session.setAttribute("verificationType", vo1.getVerificationType());
				logger.info("vo1.getEntitySubType()---"+vo1.getEntitySubType());
				session.setAttribute("entitySubType", vo1.getEntitySubType());
			
				ID=vo1.getFieldVerificationUniqueId();
				if(!CommonFunction.checkNull(VerificationCapId).equalsIgnoreCase(""))
				VerificationCapId=VerificationCapId+ID+'/';
				else
					VerificationCapId=ID+'/';
			
			}
			if(functionId.equalsIgnoreCase("4000134") ||functionId.equalsIgnoreCase("8000238") || functionId.equalsIgnoreCase("8000240")  || functionId.equalsIgnoreCase("8000242")){
				session.setAttribute("Verif", "Verif");
				if(CommonFunction.checkNull(flag).equalsIgnoreCase("N")){
				session.setAttribute("verifFlag", "verifFlag");
				
				
			}else{
				
				session.removeAttribute("verifFlag");
				//session.removeAttribute("viewDeal");
			}
			}else{
				session.removeAttribute("Verif");
				
			}
			session.setAttribute("VerificationCapId", VerificationCapId);
 			session.setAttribute("completionList", completionList);
 			session.setAttribute("loanId", loanId);
 			
 			
 		}
 		    return mapping.findForward("newFieldCompletion");	
 	}
     
     public ActionForward openPopupViewCapturedVerifAtCM(ActionMapping mapping, ActionForm form,
 			HttpServletRequest request, HttpServletResponse response)	throws Exception 
 	{
 				logger.info(" in openPopupViewCapturedVerifAtCM......()");
 				HttpSession session = request.getSession();
 				UserObject userobj=(UserObject)session.getAttribute("userobject");
 				if(userobj==null){
 					logger.info("here in openPopupViewCapturedVerifAtCM method of fieldVerificationDispatchAction action the session is out----------------");
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
 				
 				
 					String fieldVerificationUniqueId=CommonFunction.checkNull(request.getParameter("fieldVerificationId"));
 					if(session.getAttribute("fieldVerificationUniqueId")!=null)
 					{
 						session.removeAttribute("fieldVerificationUniqueId");
 						session.setAttribute("fieldVerificationUniqueId", fieldVerificationUniqueId);
 					}
 					else
 					{
 						session.setAttribute("fieldVerificationUniqueId", fieldVerificationUniqueId);
 					}
 					
 					String loanId=(String)session.getAttribute("loanId");
 					FieldVerificationDAO dao=(FieldVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(FieldVerificationDAO.IDENTITY);
 			        logger.info("Implementation class: "+dao.getClass()); 	// changed by asesh	
 					ArrayList verifMethodList = dao.getVerifMethodListList();
 					request.setAttribute("verifMethodList", verifMethodList);
 					ArrayList commonList = dao.getViewCommonListListAtCM(loanId,fieldVerificationUniqueId);
 					request.setAttribute("commonList", commonList);
 					ArrayList questList = dao.getViewQuestList(loanId,fieldVerificationUniqueId);
 					request.setAttribute("questList", questList);
 					request.setAttribute("viewDeal", "viewDeal");
 				
 					// Sanjog 
 					CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
 			        logger.info("Implementation class: "+service.getClass()); 	
 					ArrayList uploadedDocList=new ArrayList();
 					uploadedDocList = service.getUploadDocForFVC(fieldVerificationUniqueId,"FVILM",loanId);
 					request.setAttribute("uploadedDocList", uploadedDocList);
 					request.setAttribute("stage", "FVILM");
 					service=null;
 					dao=null;
 					loanId=null;
 					fieldVerificationUniqueId=null;
 					return mapping.findForward("openPopupViewCapturedVerif");	
 				
 	}
 	
     public ActionForward completionAuthorVerificationAtCM(ActionMapping mapping, ActionForm form,
 			HttpServletRequest request, HttpServletResponse response)	throws Exception 
 	{		
 		logger.info("open--jsp--completionAuthorVerificationAtCM........... ");
 		HttpSession session = request.getSession();
 		UserObject userobj=(UserObject)session.getAttribute("userobject");
 		if(userobj==null){
 			logger.info("here in completionAuthorVerificationAtCM method of fieldVerificationDispatchAction action the session is out----------------");
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
 	
 		return mapping.findForward("completionAuthorVerification");	
 	}
 	
     public ActionForward updateFieldCheckAtCM(ActionMapping mapping, ActionForm form,
 			HttpServletRequest request, HttpServletResponse response) throws Exception
 	{
 		logger.info(" in updateFieldCheckAtCM.....updateStatus()");
 		HttpSession session=request.getSession(false);
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
 			logger.info("here in updateFieldCheckAtCM method of fieldVerificationDispatchAction action the session is out----------------");
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
 		
 		FieldVerificationVo vo = new FieldVerificationVo();
 		
 		DynaValidatorForm FieldVarificationDynaValidatorForm = (DynaValidatorForm)form;
 		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, FieldVarificationDynaValidatorForm);
 		
 		String loanId=(String)session.getAttribute("loanId");
 		logger.info("loanId:----------------------"+loanId);
 		vo.setMakerId(userId);
 		vo.setMakerDate(bDate);
 		vo.setLbxLoanNoHID(loanId);
 		boolean status=false;
 		FieldVerificationDAO fieldDao=(FieldVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(FieldVerificationDAO.IDENTITY);
         logger.info("Implementation class: "+fieldDao.getClass()); 	// changed by asesh
 		//  String checkStageM=CommonFunction.stageMovement(companyId, "DC","F",dealId, "FVI", bDate,userId);
 		  //logger.info("checkStageM : "+checkStageM);
 		 // if(checkStageM.equalsIgnoreCase("S"))
 			  status = fieldDao.completionAuthorVerfAtCM(vo);
 		 // else
 			//  request.setAttribute("checkStageM", checkStageM);
         	
 		    if(status && !CommonFunction.checkNull(vo.getDecison()).equalsIgnoreCase("S")){
 		    	
 		    	request.setAttribute("sms", "S");
 		    }
 		    else if(status && CommonFunction.checkNull(vo.getDecison()).equalsIgnoreCase("S")){
 		    	
 		    	request.setAttribute("sms", "SB");
 		    }
        
         else
         {
         	request.setAttribute("sms", "N");
         }
 		
         return mapping.findForward("saveFieldRemarks");
 	}
     
     public ActionForward viewCompletionTabAtCM(ActionMapping mapping, ActionForm form,
 			HttpServletRequest request, HttpServletResponse response)	throws Exception 
 	{
 		
 		
 		HttpSession session = request.getSession();
 		UserObject userobj=(UserObject)session.getAttribute("userobject");
 		
 		String userID="";
 		
 		if(userobj!=null)
 		{
 			userID=userobj.getUserId();
 				
 		}else{
 			logger.info("here in viewCompletionTabAtCM method of fieldVerificationDispatchAction action the session is out----------------");
 			return mapping.findForward("sessionOut");
 		}
 		FieldVerificationVo vo=new FieldVerificationVo(); 		
 		DynaValidatorForm FieldVarificationDynaValidatorForm= (DynaValidatorForm)form;
 		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, FieldVarificationDynaValidatorForm);					
 	
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
 		String decision=request.getParameter("decision");
 		logger.info("decision: "+decision);
 		FieldVerificationDAO dao=(FieldVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(FieldVerificationDAO.IDENTITY);
         logger.info("Implementation class: "+dao.getClass()); 	// changed by asesh	
 		String loanId = "";				
 		loanId = CommonFunction.checkNull(request.getParameter("loanId"));
 		
 		if(session.getAttribute("loanId")!=null)
 		{
 			loanId=session.getAttribute("loanId").toString();
 		}
 		LoanInitiationDAO daoheader=(LoanInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(LoanInitiationDAO.IDENTITY);
        //CreditProcessingDAO service=new CreditProcessingDAOImpl();
 		ArrayList loanHeader = daoheader.getLoanHeader(loanId);
 		session.setAttribute("loanHeader", loanHeader);
 		String functionId="";
		if(session.getAttribute("functionId")!=null)
		{
			functionId=session.getAttribute("functionId").toString();
		}
		logger.info("functionId: "+functionId);
		if(functionId.equals("8000302"))
		{
			request.setAttribute("funId","legal");
		}
		else if(functionId.equals("8000305"))
		{
			request.setAttribute("funId","technical");
		}
		//amandeep start for RCU New Link
				else if(functionId.equals("10000830"))
				{
					request.setAttribute("funId","RCU");
				}
				//amandeep work ends for RCU new link
		else
		{
			request.setAttribute("funId","verfication");
		}
		
 		if(loanId!=null && !loanId.equalsIgnoreCase(""))
 		{
 		
 			if(functionId.equalsIgnoreCase("4000134") ||functionId.equalsIgnoreCase("8000238") || functionId.equalsIgnoreCase("8000240")  || functionId.equalsIgnoreCase("8000242")){
				session.setAttribute("Verif", "Verif");
			}else{
				session.removeAttribute("Verif");
			}
 			ArrayList completionList = dao.getCompletionListAtCM(loanId);
 			session.setAttribute("completionList", completionList);
 			if(CommonFunction.checkNull(decision).equalsIgnoreCase("S"))
 			{
 				
 				request.setAttribute("decision", "S");	
 			}
 			
 			session.setAttribute("loanId", loanId);
 			
 			
 		}
		session.setAttribute("verifCMS", "verifCMS");
 		session.removeAttribute("verifCPS"); 		    return mapping.findForward("viewCompletionTab");	
 	}
 	
}
