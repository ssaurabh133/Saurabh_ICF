package com.masters.actions;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;

import com.business.ejbClient.CreditProcessingMasterBussinessSessionBeanRemote;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.LookUpInstanceFactory;
import com.ibm.icu.text.DecimalFormat;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.vo.ApprovalLevelDefVo;

public class ApprovalLevelDefDispatchAction extends DispatchAction{
	
	private static final Logger logger = Logger.getLogger(ApprovalLevelDefDispatchAction.class.getName());

	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	
	
	public ActionForward SaveApprovalLevelDef(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
	
				logger.info(" in saveApprovalLevelDef:::::::::::::");
				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				Object sessionId = session.getAttribute("sessionID");
				
				String userId="";
				String bDate="";
				if(userobj!=null)
				{
						userId=userobj.getUserId();
						bDate=userobj.getBusinessdate();
				}
				DynaValidatorForm ApprovalLevelDefDynaValidatorForm= (DynaValidatorForm)form;
				ApprovalLevelDefVo Vo=new ApprovalLevelDefVo();
			
				org.apache.commons.beanutils.BeanUtils.copyProperties(Vo, ApprovalLevelDefDynaValidatorForm);	
				
				CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
				
				Vo.setMakerId(userId);
				Vo.setMakerDate(bDate);
				String sms="";
				
			
				ServletContext context = getServlet().getServletContext();
				//	request.removeAttribute("list");
					
					//for check User session start
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
				String productID = CommonFunction.checkNull(Vo.getLbxProductID());
				logger.info("dealId is "+productID);
				
				boolean status=false;
				String check ="";
				 int approveCheck=0;
				 String mcFlag="";
				 
				 if(!(CommonFunction.checkNull(request.getParameter("makerFlag")).equalsIgnoreCase("")))
					 mcFlag=request.getParameter("makerFlag");

				Number amountF=myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(Vo.getAmountFrom())));
				
				Number amountT=myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(Vo.getAmountTo())));
				
				  String prodId=CommonFunction.checkNull(Vo.getLbxProductID());
				  String checkQuery=null;
				  if(CommonFunction.checkNull(mcFlag).equalsIgnoreCase("Y"))					  
					  checkQuery="select count(1) from cr_approval_level_m_temp where (('"+amountF+"'<=AMOUNT_FROM and AMOUNT_TO<='"+amountT+"')or('"+amountF+"'>AMOUNT_FROM and AMOUNT_TO>'"+amountT+"')) AND PRODUCT_ID='"+prodId+"' and SCHEME_ID='"+CommonFunction.checkNull(Vo.getLbxSchemeId())+"' and REC_STATUS='A' ";
				  else
					  checkQuery="select count(1) from cr_approval_level_m where (('"+amountF+"'<=AMOUNT_FROM and AMOUNT_TO<='"+amountT+"')or('"+amountF+"'>AMOUNT_FROM and AMOUNT_TO>'"+amountT+"')) AND PRODUCT_ID='"+prodId+"' and SCHEME_ID='"+CommonFunction.checkNull(Vo.getLbxSchemeId())+"' and REC_STATUS='A' ";
				  
				  logger.info("checkQuery: "+checkQuery);
				  check=ConnectionDAO.singleReturn(checkQuery);
				  checkQuery=null;
				  if(!CommonFunction.checkNull(check).equalsIgnoreCase(""))
				  {
				      approveCheck=Integer.parseInt(check);
			     
				  }
				  
				  int list1  = cpm.getApprovalfromPmst();
					
					if(list1>2 && list1<10){
						request.setAttribute("pmstSize", list1);					
					}else if(list1<3 || list1>9){
						request.setAttribute("pmstSize", 3);
					}else {
						request.setAttribute("pmstSize", 3);
					}
					
				  if(approveCheck>0)
			     {
					ArrayList list=new ArrayList();
					logger.info("approveCheck value "+approveCheck);
					sms="check";
					request.setAttribute("sms", sms);
					list.add(Vo);
					request.setAttribute("list", list);
					
			     }
				 else
				 {
				     logger.info("before save");
					 status= cpm.saveApprovalLevelDef(Vo,mcFlag);
					
					if(status)
					{
						sms="S";
						request.setAttribute("Vo",Vo);
						request.setAttribute("sms",sms);
					}
					else
					{
						sms="N";
						request.setAttribute("Vo",Vo);
						request.setAttribute("sms",sms);
					}
				
				 }
				 logger.info("value of :"+mcFlag);
				if(CommonFunction.checkNull(mcFlag).equalsIgnoreCase("Y")){
					request.setAttribute("makerCheckerFlag","makerCheckerFlag");
					ArrayList<ApprovalLevelDefVo> list=null;
					  if(approveCheck>0)
					     {
							list=new ArrayList();
							logger.info("approveCheck value "+approveCheck);
							sms="check";
							request.setAttribute("sms", sms);
							list.add(Vo);
							request.setAttribute("list", list);
							
					     }
					  else
					  {
						    String productModify=cpm.getApprovalId();
							logger.info("productModify :"+productModify);
							list  = cpm.editApprovalLevelDef(productModify,mcFlag);
				               
							request.setAttribute("status", list.get(0).getStatus());
							request.setAttribute("editVal", "editVal");
							request.setAttribute("list", list);
							request.setAttribute("productModify", productModify);
					  }
					
					ArrayList<ApprovalLevelDefVo> levelList = new ArrayList();
					ArrayList appList=new ArrayList();
					appList.add(list.get(0).getLevel1());
					appList.add(list.get(0).getLevel2());
					appList.add(list.get(0).getLevel3());
					appList.add(list.get(0).getLevel4());
					appList.add(list.get(0).getLevel5());
					appList.add(list.get(0).getLevel6());
					appList.add(list.get(0).getLevel7());
					appList.add(list.get(0).getLevel8());
					appList.add(list.get(0).getLevel9());
					
					ArrayList LbxUserSearchList=new ArrayList();
					LbxUserSearchList.add(list.get(0).getLbxUserSearchId11());
					LbxUserSearchList.add(list.get(0).getLbxUserSearchId12());
					LbxUserSearchList.add(list.get(0).getLbxUserSearchId13());
					LbxUserSearchList.add(list.get(0).getLbxUserSearchId21());
					LbxUserSearchList.add(list.get(0).getLbxUserSearchId22());
					LbxUserSearchList.add(list.get(0).getLbxUserSearchId23());
					LbxUserSearchList.add(list.get(0).getLbxUserSearchId31());
					LbxUserSearchList.add(list.get(0).getLbxUserSearchId32());
					LbxUserSearchList.add(list.get(0).getLbxUserSearchId33());
					LbxUserSearchList.add(list.get(0).getLbxUserSearchId41());
					LbxUserSearchList.add(list.get(0).getLbxUserSearchId42());
					LbxUserSearchList.add(list.get(0).getLbxUserSearchId43());
					LbxUserSearchList.add(list.get(0).getLbxUserSearchId51());
					LbxUserSearchList.add(list.get(0).getLbxUserSearchId52());
					LbxUserSearchList.add(list.get(0).getLbxUserSearchId53());
					LbxUserSearchList.add(list.get(0).getLbxUserSearchId61());
					LbxUserSearchList.add(list.get(0).getLbxUserSearchId62());
					LbxUserSearchList.add(list.get(0).getLbxUserSearchId63());
					LbxUserSearchList.add(list.get(0).getLbxUserSearchId71());
					LbxUserSearchList.add(list.get(0).getLbxUserSearchId72());
					LbxUserSearchList.add(list.get(0).getLbxUserSearchId73());
					LbxUserSearchList.add(list.get(0).getLbxUserSearchId81());
					LbxUserSearchList.add(list.get(0).getLbxUserSearchId82());
					LbxUserSearchList.add(list.get(0).getLbxUserSearchId83());
					LbxUserSearchList.add(list.get(0).getLbxUserSearchId91());
					LbxUserSearchList.add(list.get(0).getLbxUserSearchId92());
					LbxUserSearchList.add(list.get(0).getLbxUserSearchId93());
					
					ArrayList UserList=new ArrayList();
					UserList.add(list.get(0).getUser11());
					UserList.add(list.get(0).getUser12());
					UserList.add(list.get(0).getUser13());
					UserList.add(list.get(0).getUser21());
					UserList.add(list.get(0).getUser22());
					UserList.add(list.get(0).getUser23());
					UserList.add(list.get(0).getUser31());
					UserList.add(list.get(0).getUser32());
					UserList.add(list.get(0).getUser33());
					UserList.add(list.get(0).getUser41());
					UserList.add(list.get(0).getUser42());
					UserList.add(list.get(0).getUser43());
					UserList.add(list.get(0).getUser51());
					UserList.add(list.get(0).getUser52());
					UserList.add(list.get(0).getUser53());
					UserList.add(list.get(0).getUser61());
					UserList.add(list.get(0).getUser62());
					UserList.add(list.get(0).getUser63());
					UserList.add(list.get(0).getUser71());
					UserList.add(list.get(0).getUser72());
					UserList.add(list.get(0).getUser73());
					UserList.add(list.get(0).getUser81());
					UserList.add(list.get(0).getUser82());
					UserList.add(list.get(0).getUser83());
					UserList.add(list.get(0).getUser91());
					UserList.add(list.get(0).getUser92());
					UserList.add(list.get(0).getUser93());
					request.setAttribute("appList",appList );
					request.setAttribute("UserList",UserList );
					request.setAttribute("LbxUserSearchList",LbxUserSearchList );

						
					
					request.setAttribute("decideSearchJsp","Y");
				}
				else{
				
					request.setAttribute("decideSearchJsp","N");
				}
	       
			    return mapping.findForward("SaveApprovalLevel");	
	}
	

	public ActionForward UpdateApprovalLevelDef(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {

		logger.info(" in UpdateApprovalLevelDef:::::::::::::");
		HttpSession session = request.getSession();
		//boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
		String userId="";
		String bDate="";
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
		}
		//for check User session start
		ServletContext context = getServlet().getServletContext();
		//	request.removeAttribute("list");
			
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

			DynaValidatorForm ApprovalLevelDefDynaValidatorForm= (DynaValidatorForm)form;
			ApprovalLevelDefVo Vo=new ApprovalLevelDefVo();		
			org.apache.commons.beanutils.BeanUtils.copyProperties(Vo, ApprovalLevelDefDynaValidatorForm);				
			CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
	        Vo.setMakerId(userId);
			Vo.setMakerDate(bDate);
			
			int list1  = cpm.getApprovalfromPmst();
			logger.info("-----------------------------------------1111111111--"+list1);
			request.setAttribute("pmstSize", 3);
			if(list1>2 && list1<10)
				request.setAttribute("pmstSize", list1);			
			
		
		String productModify=request.getParameter("productModify");
		String decideUpdate=request.getParameter("decideUpdate");
		Vo.setDecideUpdate(decideUpdate);
		String mcFlag="";
		if(!(CommonFunction.checkNull(request.getParameter("makerFlag")).equalsIgnoreCase("")))
			 mcFlag=request.getParameter("makerFlag");
		String check ="";
		 int approveCheck=0;
		 String sms="";
		 
		 String AmountFrom=CommonFunction.checkNull(Vo.getAmountFrom()).trim();
		 if(CommonFunction.checkNull(AmountFrom).trim().equalsIgnoreCase(""))
			 AmountFrom="0.00";
		 String AmountTo=CommonFunction.checkNull(Vo.getAmountTo()).trim();
		 if(CommonFunction.checkNull(AmountTo).trim().equalsIgnoreCase(""))
			 AmountTo="0.00";
		Number amountF=myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(Vo.getAmountFrom())));
		AmountFrom=amountF.toString();
		logger.info("getting Amount from JSP "+AmountFrom);
		Number amountT=myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(Vo.getAmountTo())));
		AmountTo=amountT.toString();
		logger.info("getting Amount To JSP "+AmountTo);
		
		
		ArrayList<ApprovalLevelDefVo> list =new ArrayList<ApprovalLevelDefVo>();
		list.add(Vo);
		request.setAttribute("list", list);
		request.setAttribute("editVal", "editVal");
		request.setAttribute("FindApprovalLevel", Vo.getFindApprovalLevel());
		ArrayList<ApprovalLevelDefVo> levelList = new ArrayList();
		ArrayList appList=new ArrayList();
		appList.add(list.get(0).getLevel1());
		appList.add(list.get(0).getLevel2());
		appList.add(list.get(0).getLevel3());
		appList.add(list.get(0).getLevel4());
		appList.add(list.get(0).getLevel5());
		appList.add(list.get(0).getLevel6());
		appList.add(list.get(0).getLevel7());
		appList.add(list.get(0).getLevel8());
		appList.add(list.get(0).getLevel9());
		
		ArrayList LbxUserSearchList=new ArrayList();
		LbxUserSearchList.add(list.get(0).getLbxUserSearchId11());
		LbxUserSearchList.add(list.get(0).getLbxUserSearchId12());
		LbxUserSearchList.add(list.get(0).getLbxUserSearchId13());
		LbxUserSearchList.add(list.get(0).getLbxUserSearchId21());
		LbxUserSearchList.add(list.get(0).getLbxUserSearchId22());
		LbxUserSearchList.add(list.get(0).getLbxUserSearchId23());
		LbxUserSearchList.add(list.get(0).getLbxUserSearchId31());
		LbxUserSearchList.add(list.get(0).getLbxUserSearchId32());
		LbxUserSearchList.add(list.get(0).getLbxUserSearchId33());
		LbxUserSearchList.add(list.get(0).getLbxUserSearchId41());
		LbxUserSearchList.add(list.get(0).getLbxUserSearchId42());
		LbxUserSearchList.add(list.get(0).getLbxUserSearchId43());
		LbxUserSearchList.add(list.get(0).getLbxUserSearchId51());
		LbxUserSearchList.add(list.get(0).getLbxUserSearchId52());
		LbxUserSearchList.add(list.get(0).getLbxUserSearchId53());
		LbxUserSearchList.add(list.get(0).getLbxUserSearchId61());
		LbxUserSearchList.add(list.get(0).getLbxUserSearchId62());
		LbxUserSearchList.add(list.get(0).getLbxUserSearchId63());
		LbxUserSearchList.add(list.get(0).getLbxUserSearchId71());
		LbxUserSearchList.add(list.get(0).getLbxUserSearchId72());
		LbxUserSearchList.add(list.get(0).getLbxUserSearchId73());
		LbxUserSearchList.add(list.get(0).getLbxUserSearchId81());
		LbxUserSearchList.add(list.get(0).getLbxUserSearchId82());
		LbxUserSearchList.add(list.get(0).getLbxUserSearchId83());
		LbxUserSearchList.add(list.get(0).getLbxUserSearchId91());
		LbxUserSearchList.add(list.get(0).getLbxUserSearchId92());
		LbxUserSearchList.add(list.get(0).getLbxUserSearchId93());
		
		ArrayList UserList=new ArrayList();
		UserList.add(list.get(0).getUser11());
		UserList.add(list.get(0).getUser12());
		UserList.add(list.get(0).getUser13());
		UserList.add(list.get(0).getUser21());
		UserList.add(list.get(0).getUser22());
		UserList.add(list.get(0).getUser23());
		UserList.add(list.get(0).getUser31());
		UserList.add(list.get(0).getUser32());
		UserList.add(list.get(0).getUser33());
		UserList.add(list.get(0).getUser41());
		UserList.add(list.get(0).getUser42());
		UserList.add(list.get(0).getUser43());
		UserList.add(list.get(0).getUser51());
		UserList.add(list.get(0).getUser52());
		UserList.add(list.get(0).getUser53());
		UserList.add(list.get(0).getUser61());
		UserList.add(list.get(0).getUser62());
		UserList.add(list.get(0).getUser63());
		UserList.add(list.get(0).getUser71());
		UserList.add(list.get(0).getUser72());
		UserList.add(list.get(0).getUser73());
		UserList.add(list.get(0).getUser81());
		UserList.add(list.get(0).getUser82());
		UserList.add(list.get(0).getUser83());
		UserList.add(list.get(0).getUser91());
		UserList.add(list.get(0).getUser92());
		UserList.add(list.get(0).getUser93());
		request.setAttribute("appList",appList );
		request.setAttribute("UserList",UserList );
		request.setAttribute("LbxUserSearchList",LbxUserSearchList );
		
		String prodId=CommonFunction.checkNull(Vo.getLbxProductID());
		if(!(CommonFunction.checkNull(mcFlag).equalsIgnoreCase("Y")))
		{
		  check=ConnectionDAO.singleReturn("select count(1) from cr_approval_level_m where (('"+AmountFrom+"'<=AMOUNT_FROM and AMOUNT_TO<='"+AmountTo+"')or('"+AmountFrom+"'>AMOUNT_FROM and AMOUNT_TO>'"+AmountTo+"'))and APPROVAL_LEVEL_ID <> '"+productModify+"' AND PRODUCT_ID='"+prodId+"' and SCHEME_ID='"+CommonFunction.checkNull(Vo.getLbxSchemeId())+"' and REC_STATUS='A' ");
		  if(check!=null)
		  {
			  approveCheck=Integer.parseInt(check);
			  logger.info("tradecheck"+approveCheck);   
		  }
		}
		if(approveCheck > 0)
		{
			sms="check";
			logger.info("in if condition for check lopping");
			request.setAttribute("sms", sms);
			String stat="";
			if (Vo.getStatus() != null && Vo.getStatus().equals("on")) 
				stat = "Active";
			else 
				stat = "Inactive";
			request.setAttribute("status", stat);
			request.setAttribute("productModify", productModify);			
		}
		else
		{
			String  status = cpm.updateApprovalLevelDef(Vo,productModify,mcFlag);
			if(status.equalsIgnoreCase("S"))
			{
				sms="M";
				request.setAttribute("sms",sms);
			}
			else if(status.equalsIgnoreCase("N"))
			{
				sms="N";
				request.setAttribute("sms",sms);
			}		
			else
			{
				sms="E";
				request.setAttribute("sms",sms);
				String stat="";
				if (Vo.getStatus() != null && Vo.getStatus().equals("on")) 
					stat = "Active";
				else 
					stat = "Inactive";				
				request.setAttribute("status", stat);
				request.setAttribute("productModify", productModify);				
			}
		}	
		if(CommonFunction.checkNull(mcFlag).equalsIgnoreCase("Y"))
		{
			request.setAttribute("makerCheckerFlag","makerCheckerFlag");
			request.setAttribute("editVal","editVal");
			productModify=cpm.getApprovalId();
			logger.info("value of ::::::::::::max id::::::saurabh:::::"+productModify);
			request.setAttribute("productModify", productModify);
			request.setAttribute("decideSearchJsp","Y");
		}
		else
			 request.setAttribute("decideSearchJsp","N");
		return mapping.findForward("UpdateApprovalLevel");	
	}
	
	public ActionForward forwardApprovalLevel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		logger.info(" in forwardApprovalLevel:::::::::::::");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
		String userId="";
		String bDate="";
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
		}
		
		DynaValidatorForm ApprovalLevelDefDynaValidatorForm= (DynaValidatorForm)form;
		ApprovalLevelDefVo Vo=new ApprovalLevelDefVo();
	
		org.apache.commons.beanutils.BeanUtils.copyProperties(Vo, ApprovalLevelDefDynaValidatorForm);	
		
		CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
        Vo.setMakerId(userId);
		Vo.setMakerDate(bDate);
		
		
	
		//for check User session start
		ServletContext context = getServlet().getServletContext();
		//	request.removeAttribute("list");
			
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
			
			String productModify=request.getParameter("productModify");

			 String  status = cpm.forwardApprovalLevel(productModify);
			 String sms="";
			 logger.info("value of status::::::::::::::::::"+status);
				if(CommonFunction.checkNull(status).equalsIgnoreCase("S")){
					sms="F";
					request.setAttribute("Vo",Vo);
					request.setAttribute("smsForward",sms);
				}
			 else{
					sms="N";
					request.setAttribute("Vo",Vo);
					request.setAttribute("sms",sms);
				}
				
		   int list1  = cpm.getApprovalfromPmst();
			logger.info("-----------------------------------------1111111111--"+list1);
			
			request.setAttribute("makerCheckerFlag","makerCheckerFlag");
			request.setAttribute("editVal","editVal");
		
			//String productModify=cpm.getApprovalId();
			logger.info("value of ::::::::::::max id::::::saurabh:::::"+productModify);
			String mcFlag="Y";
			ArrayList<ApprovalLevelDefVo> list  = cpm.editApprovalLevelDef(productModify,mcFlag);
               
			request.setAttribute("status", list.get(0).getStatus());
			request.setAttribute("editVal", "editVal");
			request.setAttribute("list", list);
			ArrayList<ApprovalLevelDefVo> levelList = new ArrayList();
			ArrayList appList=new ArrayList();
			appList.add(list.get(0).getLevel1());
			appList.add(list.get(0).getLevel2());
			appList.add(list.get(0).getLevel3());
			appList.add(list.get(0).getLevel4());
			appList.add(list.get(0).getLevel5());
			appList.add(list.get(0).getLevel6());
			appList.add(list.get(0).getLevel7());
			appList.add(list.get(0).getLevel8());
			appList.add(list.get(0).getLevel9());
			
			ArrayList LbxUserSearchList=new ArrayList();
			LbxUserSearchList.add(list.get(0).getLbxUserSearchId11());
			LbxUserSearchList.add(list.get(0).getLbxUserSearchId12());
			LbxUserSearchList.add(list.get(0).getLbxUserSearchId13());
			LbxUserSearchList.add(list.get(0).getLbxUserSearchId21());
			LbxUserSearchList.add(list.get(0).getLbxUserSearchId22());
			LbxUserSearchList.add(list.get(0).getLbxUserSearchId23());
			LbxUserSearchList.add(list.get(0).getLbxUserSearchId31());
			LbxUserSearchList.add(list.get(0).getLbxUserSearchId32());
			LbxUserSearchList.add(list.get(0).getLbxUserSearchId33());
			LbxUserSearchList.add(list.get(0).getLbxUserSearchId41());
			LbxUserSearchList.add(list.get(0).getLbxUserSearchId42());
			LbxUserSearchList.add(list.get(0).getLbxUserSearchId43());
			LbxUserSearchList.add(list.get(0).getLbxUserSearchId51());
			LbxUserSearchList.add(list.get(0).getLbxUserSearchId52());
			LbxUserSearchList.add(list.get(0).getLbxUserSearchId53());
			LbxUserSearchList.add(list.get(0).getLbxUserSearchId61());
			LbxUserSearchList.add(list.get(0).getLbxUserSearchId62());
			LbxUserSearchList.add(list.get(0).getLbxUserSearchId63());
			LbxUserSearchList.add(list.get(0).getLbxUserSearchId71());
			LbxUserSearchList.add(list.get(0).getLbxUserSearchId72());
			LbxUserSearchList.add(list.get(0).getLbxUserSearchId73());
			LbxUserSearchList.add(list.get(0).getLbxUserSearchId81());
			LbxUserSearchList.add(list.get(0).getLbxUserSearchId82());
			LbxUserSearchList.add(list.get(0).getLbxUserSearchId83());
			LbxUserSearchList.add(list.get(0).getLbxUserSearchId91());
			LbxUserSearchList.add(list.get(0).getLbxUserSearchId92());
			LbxUserSearchList.add(list.get(0).getLbxUserSearchId93());
			
			ArrayList UserList=new ArrayList();
			UserList.add(list.get(0).getUser11());
			UserList.add(list.get(0).getUser12());
			UserList.add(list.get(0).getUser13());
			UserList.add(list.get(0).getUser21());
			UserList.add(list.get(0).getUser22());
			UserList.add(list.get(0).getUser23());
			UserList.add(list.get(0).getUser31());
			UserList.add(list.get(0).getUser32());
			UserList.add(list.get(0).getUser33());
			UserList.add(list.get(0).getUser41());
			UserList.add(list.get(0).getUser42());
			UserList.add(list.get(0).getUser43());
			UserList.add(list.get(0).getUser51());
			UserList.add(list.get(0).getUser52());
			UserList.add(list.get(0).getUser53());
			UserList.add(list.get(0).getUser61());
			UserList.add(list.get(0).getUser62());
			UserList.add(list.get(0).getUser63());
			UserList.add(list.get(0).getUser71());
			UserList.add(list.get(0).getUser72());
			UserList.add(list.get(0).getUser73());
			UserList.add(list.get(0).getUser81());
			UserList.add(list.get(0).getUser82());
			UserList.add(list.get(0).getUser83());
			UserList.add(list.get(0).getUser91());
			UserList.add(list.get(0).getUser92());
			UserList.add(list.get(0).getUser93());
			request.setAttribute("appList",appList );
			request.setAttribute("UserList",UserList );
			request.setAttribute("LbxUserSearchList",LbxUserSearchList );

			request.setAttribute("productModify", productModify);
			ApprovalLevelDefVo av=new ApprovalLevelDefVo();
			//int list1  = cpm.getApprovalfromPmst();
			
			logger.info("----------------EditapprovalLevelDef--"+list1);
			if(list1>2 && list1<10){
				request.setAttribute("pmstSize", list1);					
			}else if(list1<3 || list1>9){
				request.setAttribute("pmstSize", 3);
			}else {
				request.setAttribute("pmstSize", 3);
			}	
					
		
		return mapping.findForward("forwardApproval");
	}

}
