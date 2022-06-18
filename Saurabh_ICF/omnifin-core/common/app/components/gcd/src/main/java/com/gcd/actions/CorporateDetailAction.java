package com.gcd.actions;

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

import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.gcd.VO.CorporateDetailsVO;
import com.gcd.dao.CorporateDAO;
import com.lockRecord.action.ReleaseRecordFromObject;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class CorporateDetailAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(CorporateDetailAction.class.getName());
	
	public ActionForward displayCorporateDetails(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
	{		
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
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
		
		String statusCase="";
		String updateFlag="";
		String updateInMaker="";
		
		logger.info("In CorporateDetailAction displayCorporateDetails() Corporate Id: "+session.getAttribute("corporateId"));
		if(session.getAttribute("new")==null && session.getAttribute("pageStatuss")==null)
		{
			session.setAttribute("update", "update");
			session.setAttribute("operation", "update");
		}
		CorporateDAO corporateDao=(CorporateDAO)DaoImplInstanceFactory.getDaoImplInstance(CorporateDAO.IDENTITY);
		logger.info("Implementation class: "+corporateDao.getClass());
		ArrayList<Object> customerCategorylist=corporateDao.getCustomerCategoryList();
		ArrayList<Object> constitutionlist=corporateDao.getContitutionList();
		ArrayList<Object> registrationTypeList=corporateDao.getRegistrationTypeList();
		ArrayList<Object> industryList = corporateDao.getIndustryList();
		ArrayList<Object> businessSegmentList = corporateDao.getBusinessSegmentList();
		ArrayList<Object> natureOfBusList = corporateDao.getNatureOfBusinessList();
		ArrayList  riskCategoryList= corporateDao.getriskCategoryList();
		session.setAttribute("riskCategoryList", riskCategoryList);
		
		if(session.getAttribute("corporateId")!=null || session.getAttribute("approve")!=null)
		{
			if(session.getAttribute("statusCase")!=null)
				statusCase = session.getAttribute("statusCase").toString();
			if(session.getAttribute("updateFlag")!=null)
				updateFlag = (String)session.getAttribute("updateFlag");
			if(session.getAttribute("updateInMaker")!=null)
				updateInMaker = session.getAttribute("updateInMaker").toString();
			Object pageStatus = session.getAttribute("approve");
			int cId=0;
			if(session.getAttribute("corporateId")!=null && session.getAttribute("corporateId").toString().length()!=0)
				 cId =Integer.parseInt(session.getAttribute("corporateId").toString());
			String pageStatuss="";
			if(session.getAttribute("pageStatuss")!=null)
				 pageStatuss = session.getAttribute("pageStatuss").toString();
			String gcdReq = (String)session.getAttribute("gcdReq");
			Object pageStatus1=pageStatus;
			String updateFlag1 =updateFlag;
			if(gcdReq!=null)
			{
			  pageStatus=null;
			  updateFlag=null;
			}
			//Shashank Space starts	
			String tableName="";
			if (((pageStatuss != null) && (pageStatuss.equals("fromLeads"))) || ((updateFlag != null) && (updateFlag.equals("updateFlag"))) || ((updateFlag != null) && (updateFlag.equals("notEdit"))))
		    {
				tableName = "cr_deal_customer_m";
		    } else {
		        if (((pageStatus != null) && (pageStatus.equals("approve"))) || ((updateInMaker != null) && (updateInMaker.equals("updateInMaker"))) || (statusCase.equalsIgnoreCase("UnApproved"))) {
		            tableName = "gcd_customer_m_temp";
		          }
		          else {
		            tableName = "gcd_customer_m";
		            if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
		              tableName = "gcd_customer_m_edit";
		          }
		    }
			  String codeId =session.getAttribute("corporateId").toString();
				String query = "Select EmailVerificationFlag from "+tableName+" where customer_id="+codeId;
				logger.info("Query for checking record is verified or not  :  "+query);
				String EmailVerificationFlag = ConnectionDAO.singleReturn(query);
			  logger.info("EmailVerificationFlag: "+EmailVerificationFlag);
			  request.setAttribute("EmailVerificationFlag",EmailVerificationFlag);
			//Shashank Space Ends
			ArrayList<Object> detailList = corporateDao.getCorporateDetailAll(cId,pageStatus,statusCase,updateInMaker,updateFlag,pageStatuss,source);
			pageStatus=pageStatus1;
			updateFlag=updateFlag1;			
			session.setAttribute("detailList",detailList);
			
			if(detailList.size()>0)
			{
				CorporateDetailsVO show=(CorporateDetailsVO)detailList.get(0);
				logger.info("other Relationship : "+show.getOtherRelationShip());
				request.setAttribute("otherRelationShip", show.getOtherRelationShip());
			}
		}
	 
		session.setAttribute("industryList",industryList);
		session.setAttribute("customerCategory",customerCategorylist);
		session.setAttribute("constitutionlist",constitutionlist);
		session.setAttribute("registrationTypeList",registrationTypeList);
		session.setAttribute("businessSegmentList",businessSegmentList);
		session.setAttribute("natureOfBusList",natureOfBusList);
		session.removeAttribute("subIndustryList");
		if(session.getAttribute("pParentGroup")!=null)
			session.removeAttribute("pParentGroup");
		if(session.getAttribute("strParentOption")!=null)
			session.removeAttribute("strParentOption");
		//Nishant Space starts
		String emailFlag = corporateDao.getEmailMandatoryFlag();
		request.setAttribute("emailMandatoryFlag", emailFlag);
		emailFlag = null;
		//Nishant space end
		return mapping.findForward("displaySuccess");		
	}
	

	public ActionForward saveCorporateDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		return mapping.findForward("success");
	}
	
	
	public ActionForward saveAndForwardCorpDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		CorporateDAO corporateDao=(CorporateDAO)DaoImplInstanceFactory.getDaoImplInstance(CorporateDAO.IDENTITY);
		logger.info("Implementation class: "+corporateDao.getClass());
		    HttpSession session = request.getSession();
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			
			Object sessionId = session.getAttribute("sessionID");
			ServletContext context = getServlet().getServletContext();
			String strFlag="";	
			
			String userId="";
			String businessDate="";
			if(userobj!=null)
			{
					userId=userobj.getUserId();
					businessDate=userobj.getBusinessdate();
			}
			
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
		int cId=0;
		
		logger.info("In CorporateDetailAction saveAndForwardCorpDetails()............................................. ");
	
		 String addCheck =null;
		 String stackCheck=null;
		 int status=0;
		 String statusCase="";
		 String addressTableName="";
		 String stackTableName="";
		 ArrayList  riskCategoryList= corporateDao.getriskCategoryList();
		 session.setAttribute("riskCategoryList", riskCategoryList);
		 if(session.getAttribute("statusCase")!=null)
		    {
		    	statusCase=session.getAttribute("statusCase").toString();
		    		
		    }
		 
		if(session.getAttribute("corporateId")!=null)
		{
			cId = Integer.parseInt(session.getAttribute("corporateId").toString());
			
			if(statusCase.equalsIgnoreCase("UnApproved") || statusCase.equalsIgnoreCase("Approved"))
			{
				addressTableName="com_address_m_temp";
				stackTableName="customer_stakeholder_m_temp";
			}
			else
			{
				addressTableName="com_address_m";
				stackTableName="customer_stakeholder_m";
			}
			String comAddrQ= "Select COMMUNICATION_ADDRESS from "+addressTableName+" where BPID="+cId+" AND COMMUNICATION_ADDRESS='Y'";
			logger.info("For checking COMMUNICATION_ADDRESS exist or not  Query: "+comAddrQ);
			String stackHoldQ= "Select STAKEHOLDER_ID from "+stackTableName+" where CUSTOMER_ID="+cId+"";
			logger.info("For checking Management Details is captured or not Query: "+stackHoldQ);
			addCheck = ConnectionDAO.singleReturn(comAddrQ);
			stackCheck = ConnectionDAO.singleReturn(stackHoldQ);  
			if(CommonFunction.checkNull(addCheck).trim().equalsIgnoreCase(""))
				addCheck="N";
			logger.info("addCheck: "+addCheck+" stackCheck: "+stackCheck);
			
			/*//Shashank Staris Here
			String checkPrimaryApplicantDealQuery="Select count(1) from cr_deal_dtl where deal_customer_id="+cId+" ";
			String checkPrimaryApplicantLoanQuery="Select count(1) from cr_loan_dtl where loan_customer_id="+cId+" ";
			int checkPrimaryApplicantDeal=Integer.parseInt(ConnectionDAO.singleReturn(checkPrimaryApplicantDealQuery));
			int checkPrimaryApplicantLoan=Integer.parseInt(ConnectionDAO.singleReturn(checkPrimaryApplicantLoanQuery));
			
			String mobileEnablKeyQuery="SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='MOBILE_VERIFICATION' "; 
			String emailEnablKeyQuery="SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='EMAIL_VERIFICATION'  ";
			String mobileEnablKey=ConnectionDAO.singleReturn(mobileEnablKeyQuery);
			String emailEnablKey=ConnectionDAO.singleReturn(emailEnablKeyQuery);
			if(checkPrimaryApplicantDeal!=0 || checkPrimaryApplicantLoan!=0){
			String mobile= "Select MobileVerificationFlag from com_address_m where BPID="+cId+" ";
			logger.info("For checking Mobile Verification  Query: "+mobile);
			String email= "Select EmailVerificationFlag from gcd_customer_m where CUSTOMER_ID="+cId+" ";
			logger.info("For checking Email Verification  Query: "+email);
			String mobileCheck = ConnectionDAO.singleReturn(mobile);
			String emailCheck = ConnectionDAO.singleReturn(email);  
			logger.info("For checking Mobile Verification  mobileCheck: "+mobileCheck);
			logger.info("For checking Email Verification  emailCheck: "+emailCheck);
			
			if(mobileCheck.equalsIgnoreCase("N") && CommonFunction.checkNull(mobileEnablKey).equalsIgnoreCase("Y"))
			{
				request.setAttribute("sms", "mobileVerify");
				return mapping.getInputForward();
			}
			if(emailCheck.equalsIgnoreCase("N") && CommonFunction.checkNull(emailEnablKey).equalsIgnoreCase("Y"))
			{
				request.setAttribute("sms", "emailVerify");
				return mapping.getInputForward();
			}
			}
			//Shashank Ends Here
*/			if(session.getAttribute("updateInMaker")==null && (statusCase!=null && statusCase.length()>0))
			{
				String query = "Select customer_id from gcd_customer_m_temp where customer_id="+cId;
				String custCheckInTemp = ConnectionDAO.singleReturn(query);
				logger.info("custCheckInTemp *?********************************** "+custCheckInTemp);
				if(custCheckInTemp==null || custCheckInTemp.length()<=0)
				{
					request.setAttribute("sms", "saveFirst");
					return mapping.getInputForward();
				}
			}
			if(addCheck==null ||(addCheck != null && addCheck.equals("N")))
			{
				logger.info("For checking COMMUNICATION_ADDRESS exist or not  Query: "+comAddrQ);
				request.setAttribute("sms", "P");
				return mapping.getInputForward();
			}
//			if(stackCheck==null)
//			{
//				logger.info("For checking Management Details is captured or not Query: "+stackHoldQ);
//				request.setAttribute("sms", "MD");
//				return mapping.getInputForward();
//			}	
			String q1="select count(1) from "+addressTableName+" where bpid='"+cId+"' and bptype='CS' and RELATIONSHIP_FLAG='N' ";
			int count=Integer.parseInt(ConnectionDAO.singleReturn(q1));
			if(count>0){
				logger.info("For checking Relation exist or not  Query: "+q1);
				request.setAttribute("sms", "Relation");
				return mapping.getInputForward();
			}
			if(addCheck!=null)
			{
				if(addCheck.equals("Y"))
					status=corporateDao.setApproveStatus(cId,statusCase,userId,businessDate);
			}			
			if(session.getAttribute("updateInMaker")==null && (statusCase!=null && statusCase.length()>0))
			{
				String query = "Select customer_id from gcd_customer_m_temp where customer_id="+cId;
				String custCheckInTemp = ConnectionDAO.singleReturn(query);
				logger.info("custCheckInTemp *?********************************** "+custCheckInTemp);
				if(custCheckInTemp==null || custCheckInTemp.length()<=0)
				{
					request.setAttribute("sms", "saveFirst");
				}
				else
				{
					if(status>0)
					{
						boolean Lflag = ReleaseRecordFromObject.releaselockedRecord(userId, context);						
						request.setAttribute("status", "Customer is ready for Approved Successfully!!!");
						request.setAttribute("forwardFromUPMaker","forwardFromUPMaker");						
						return mapping.findForward("success");
					}
					else
					{	
						request.setAttribute("sms", "N");
					}
				}
			}
			else
			{
				if(status>0)
				{
					boolean Lflag = ReleaseRecordFromObject.releaselockedRecord(userId, context);
					request.setAttribute("status", "Customer is ready for Approved Successfully!!!");
					if(session.getAttribute("updateInMaker") != null)
						request.setAttribute("forwardFromUPMaker","forwardFromUPMaker");
					else						
					request.setAttribute("Corporate","Corporate");
					return mapping.findForward("success");
				}
				else
				{	
					request.setAttribute("sms", "N");
				}
			}
		}
		else
		{
			request.setAttribute("sms", "N");
			
		}
		return mapping.getInputForward();
	}
}
