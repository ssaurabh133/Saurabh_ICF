/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.masters.actions;

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
import org.apache.struts.validator.DynaValidatorForm;

import com.business.ejbClient.CreditProcessingMasterBussinessSessionBeanRemote;
import com.connect.CommonFunction;
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.vo.VerificationQuestionVo;

/** 
 * MyEclipse Struts
 * Creation date: 09-18-2012
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class VerificationQuestProcessingMasterAction extends DispatchAction {
	
	private static final Logger logger = Logger.getLogger(VerificationQuestProcessingMasterAction.class.getName());
	 public ActionForward openAddVerificationQuest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception
			{
				logger.info(" in openAddVerificationQuest()");
				ServletContext context = getServlet().getServletContext();
				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				Object sessionId = session.getAttribute("sessionID");
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
				request.setAttribute("openForSave", "openForSave");		
				//code added by manish 
				VerificationQuestionVo vo = new VerificationQuestionVo();
				
				CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		        
		        ArrayList<Object>addrList = cpm.getAddressList();
		        logger.info("addrList size   : "+addrList.size());
		        request.setAttribute("addrList",addrList);
		        //manish space end 
			    return mapping.findForward("openAdd");	
			}
	 

		public ActionForward saveVerificationQuestDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse resopnse) throws Exception{
			ServletContext context = getServlet().getServletContext();
			//HttpSession session=request.getSession(false);
			HttpSession session = request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			Object sessionId = session.getAttribute("sessionID");
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
			
			String userId="";
			String bDate="";
			if(userobj!=null)
			{
					userId=userobj.getUserId();
					bDate=userobj.getBusinessdate();
			}
	
		DynaValidatorForm CountryMasterAddDyanavalidatiorForm= (DynaValidatorForm)form;
		VerificationQuestionVo vo = new VerificationQuestionVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, CountryMasterAddDyanavalidatiorForm);
		

		vo.setMakerId(userId);
		vo.setMakerDate(bDate);
		  String[] productId=null;
	        logger.info("request.getParameter(areaCode)111111111"+CommonFunction.checkNull(request.getParameter("areaCode")));
	        String ar=CommonFunction.checkNull(request.getParameter("productId"));
	        logger.info("ar:-"+ar);
	        
	        if(!ar.equals("")){
	        	productId=ar.split("/");
	        }
		CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
    //code added by manish 
      ArrayList<Object>addrList = cpm.getAddressList();
      logger.info("addrList size   : "+addrList.size());
      request.setAttribute("addrList",addrList);
      //manish space end 
		String sms="";
		boolean status=false;
		String check="";
		int count=cpm.countVerificationCombination(vo);
		if(count==0)
		{
			
		    if(CommonFunction.checkNull(vo.getVerificationQuestId()).equalsIgnoreCase(""))
		    {
		    	check="S";
		    	status = cpm.insertVerificationQuestMaster(vo,productId,check);
		    	
		    	
		    	logger.info("Inside saveVerificationMappingDetails....displaying status...."+status);
		    	
		    	if(status){
					sms="S";
					
				}
				else{
					sms="E";
					
				
				}
		    }
		    else
		    {
		    	check="U";
		    	status = cpm.updateVerificationQuestMaster(vo,productId,check);
		    	
		    	
		       
		    	request.setAttribute("verificationSubType",CommonFunction.checkNull(vo.getVerificationSubType()));
		    	request.setAttribute("verificationType",vo.getVerificationType());
		    	logger.info("Inside saveVerificationMappingDetails....displaying status...."+status);
		    	
		    	if(status){
					sms="S";
					
				}
				else{
					sms="E";
							
				}
		    }
		}
		else
		{
			sms="DUBLICATE";
		}
		
		logger.info("verificationType: "+vo.getVerificationType()+" verificationSubType: "+CommonFunction.checkNull(vo.getVerificationSubType()));
	    ArrayList list = cpm.getGridInEditVerificationQuest(vo);
    	request.setAttribute("grideList", list);
		request.setAttribute("sms",sms);
		logger.info(" Inside saveVerificationMappingDetails....end of method status....status"+status);
		return mapping.getInputForward();
	}
	
	
	
		public ActionForward openEditVerificationQuest(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)	throws Exception { 
			ServletContext context = getServlet().getServletContext();
			             VerificationQuestionVo vo = new VerificationQuestionVo();
							logger.info("In openEditVerificationQuest");
							
							HttpSession session = request.getSession();
							boolean flag=false;
							UserObject userobj=(UserObject)session.getAttribute("userobject");
							Object sessionId = session.getAttribute("sessionID");
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
							
							
							CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
							
							String verificationQuestId=request.getParameter("verificationQuestId");
							String questionId=request.getParameter("verificationQuestId");
							if(!CommonFunction.checkNull(verificationQuestId).equalsIgnoreCase(""))
							{
								ArrayList list = cpm.getVerificationQuestData(verificationQuestId);
								logger.info("In openEditVerificationQuestion  list"+list.size());
								if(list.size()>0)
								{
									vo=(VerificationQuestionVo) list.get(0);
									logger.info("In  list"+list.size());
									request.setAttribute("list", list);
									request.setAttribute("status", vo.getQuestStatus());
																	
								}
								ArrayList grideList=cpm.getGridInEditVerificationQuest(vo);
								
								logger.info("In openEditVerificationQuestion  grideList"+grideList.size());
								
								if(grideList.size()>0)
								{
									request.setAttribute("grideList", grideList);
								}
							}
							//code added by manish 
						      ArrayList<Object>addrList = cpm.getAddressList();
						      logger.info("addrList size   : "+addrList.size());
						      request.setAttribute("addrList",addrList);
						      //manish space end 
							   ArrayList<Object> productList = cpm.getProductList(questionId);
								request.setAttribute("productList", productList);
						   return mapping.findForward("editVerificationQuest");	
						}
		
		public ActionForward openEditFromSearchWithGridVerificationQuest(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)	throws Exception
				{ 
			ServletContext context = getServlet().getServletContext();
            VerificationQuestionVo vo = new VerificationQuestionVo();
				logger.info("## In openEditFromSearchWithGridVerificationQuest :");
				
				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				Object sessionId = session.getAttribute("sessionID");
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
				
				CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		      
		        // Code added by manish
		        ArrayList<Object>addrList = cpm.getAddressList();
		        logger.info("addrList size   : "+addrList.size());
		        request.setAttribute("addrList",addrList);
		        // manish code end
		        
		        int currentPageLink = 0;
				if(request.getParameter("d-49520-p")==null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
				{
					currentPageLink=1;
				}
				else
				{
					currentPageLink =Integer.parseInt(request.getParameter("d-49520-p"));
				}
				
				logger.info("## In openEditFromSearchWithGridVerificationQuest : current page link ................ "+request.getParameter("d-49520-p"));
				
				vo.setCurrentPageLink(currentPageLink);
				
		        String verificationType=CommonFunction.checkNull(request.getParameter("verificationType")).trim();
		        String verificationSubType=CommonFunction.checkNull(request.getParameter("verificationSubType")).trim();
		          String questionId=CommonFunction.checkNull(request.getParameter("questionId")).trim();
		        logger.info("## In openEditFromSearchWithGridVerificationQuest : verificationType : ==>>"+verificationType);
		        logger.info("## In openEditFromSearchWithGridVerificationQuest : verificationSubType : ==>>"+verificationSubType);
		         logger.info("## In openEditFromSearchWithGridVerificationQuest : questionId : ==>>"+questionId);

		        ArrayList<Object> productList = cpm.getProductList(questionId);
		        request.setAttribute("productList", productList);
		        
		        vo.setVerificationType(CommonFunction.checkNull(verificationType));
		        vo.setVerificationSubType(CommonFunction.checkNull(verificationSubType));
		        
		        
				if(!CommonFunction.checkNull(verificationType).equalsIgnoreCase("")&&!CommonFunction.checkNull(verificationSubType).equalsIgnoreCase(""))
				{
			
					ArrayList list = cpm.getGridInEditVerificationQuest(vo);
					logger.info("## In openEditFromSearchWithGridVerificationQuest :  list"+list.size());
					if(list.size()>0)
					{
						vo=(VerificationQuestionVo) list.get(0);
						request.setAttribute("grideList", list);
						request.setAttribute("status", vo.getQuestStatus());
						}
				}
				
			   return mapping.findForward("gridInAddVerificationQuest");	
			
			
				}
		
		 public ActionForward getGridAtVerificationSelect(ActionMapping mapping, ActionForm form,
					HttpServletRequest request, HttpServletResponse response)	throws Exception
					{
						logger.info(" in getGridAtVerificationSelect()");
						ServletContext context = getServlet().getServletContext();
						HttpSession session = request.getSession();
						boolean flag=false;
						UserObject userobj=(UserObject)session.getAttribute("userobject");
						Object sessionId = session.getAttribute("sessionID");
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
						String verificationType=request.getParameter("verificationType");
						String verificationSubType=request.getParameter("verificationSubType");
						request.setAttribute("openForSave", "openForSave");
						
						CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
				        
				        VerificationQuestionVo vo = new VerificationQuestionVo();
				        vo.setVerificationType(verificationType);
				        vo.setVerificationSubType(verificationSubType);
				        ArrayList list = cpm.getGridInEditVerificationQuest(vo);
				    	request.setAttribute("grideList", list);
					    return mapping.findForward("gridInAddVerificationQuest");	
					}
			 
				
				
}