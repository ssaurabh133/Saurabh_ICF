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

import com.business.ejbClient.CommonMasterBussinessSessionBeanRemote;
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.vo.SubDealerMasterVo;

public class SubDealerMasterSearchAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(SubDealerMasterSearchAction.class.getName());
		public ActionForward openAddSubDealer(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
			ServletContext context = getServlet().getServletContext();
			logger.info(" in SubDealerMasterSearchAction ----> openAddDealer()");
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
			
			request.setAttribute("save", "save");
			return mapping.findForward("openAdd");
	}
		
		public ActionForward saveSubDealerDetails(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
			ServletContext context = getServlet().getServletContext();
			HttpSession session=request.getSession(false);
			UserObject userobj=new UserObject();
			userobj=(UserObject)session.getAttribute("userobject");
			String userId="";
			String bDate="";
			
			String status="";
			
			if(userobj!=null)
			{
					userId=userobj.getUserId();
					bDate=userobj.getBusinessdate();
			}
			boolean flag=false;
			
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
				DynaValidatorForm SubDealerMasterAddDyanavalidatiorForm= (DynaValidatorForm)form;
				SubDealerMasterVo vo = new SubDealerMasterVo();
				org.apache.commons.beanutils.BeanUtils.copyProperties(vo, SubDealerMasterAddDyanavalidatiorForm);
				
				CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
				
				vo.setMakerId(userId);
				vo.setMakerDate(bDate);
				
				String sms="";			
				
				
				
				status=bp.insertSubDealerMaster(vo);
				//if(result){
//				if(status){
//					sms="S";
//					request.setAttribute("sms",sms);
//				}
//				else{
//					sms="E";
//					request.setAttribute("sms",sms);
//				}
//			    logger.info("status"+status);
				if((status.equalsIgnoreCase("datasaved"))){
					sms="S";
					request.setAttribute("sms",sms);
					request.setAttribute("save", "save");
					// request.setAttribute("procval", procval);
				}else if((status.equalsIgnoreCase("dataExist"))){
					sms="DE";
					request.setAttribute("sms",sms);
					request.setAttribute("save", "save");
					
				}else{
					sms="E";
					request.setAttribute("sms",sms);
					request.setAttribute("save", "save");
					
				}

				return mapping.getInputForward();

		}
		

		
			public ActionForward openEditSubDealer(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)	throws Exception {
				SubDealerMasterVo subDealerMasterVo=new SubDealerMasterVo(); 
				ServletContext context = getServlet().getServletContext();
					logger.info("In openEditSubDealer");
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
					//String userSearchId=request.getParameter("subDealerCode");
					logger.info("In Action subDealerID -"+request.getParameter("subDealerID"));  
					String userSearchId=request.getParameter("subDealerID");
					subDealerMasterVo.setSubDealerCode(request.getParameter("subDealerCode"));
					session.setAttribute("subDealerCode",request.getParameter("subDealerCode"));
					
					logger.info("In openEditSubDealer---subDealerCode---- by getpara-"+request.getParameter("subDealerCode"));  
					logger.info("In openEditSubDealer---status---- by getpara by vo-"+subDealerMasterVo.getSubDealerCode());
					
					CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
					
			      //  ArrayList<SubDealerMasterVo> list = bp.searchSubDealerData(subDealerMasterVo);
			        ArrayList<SubDealerMasterVo> list = bp.searchSubDealerUserEdit(userSearchId);
					logger.info("In openEditSubDealer SubdealerMasterVo list"+list.size());
					request.setAttribute("list", list);
//					ArrayList<SubDealerMasterVo> user = bp.searchSubDealerUserEdit(userSearchId);
//					logger.info("In openEditSubDealer dealerMasterVo list"+list.size());
//					request.setAttribute("levelNameList", user);
				
					String userDesc ="";
			
					String branchSelection ="";
//		
//					Iterator<SubDealerMasterVo> it= user.iterator();
//					
//					while(it.hasNext())
//					{
//						SubDealerMasterVo  vo1 = (SubDealerMasterVo) it.next();
//						logger.info("vo1.getUserId()---"+vo1.getUserId());
//						userDesc=userDesc+vo1.getUserId()+"|";
//					
//					}
					if(!userDesc.equalsIgnoreCase(""))
						userDesc = userDesc.substring(0,userDesc.length()-1);
					logger.info("userDesc--2--"+userDesc);
					
					logger.info("userDesc String ............................... "+userDesc);
			
					request.setAttribute("userIds", userDesc);
					String recStatus="";
					if(list.size()>0)
					{
						SubDealerMasterVo vo = (SubDealerMasterVo)list.get(0);
						recStatus=vo.getSubDealerStatus();
					}
					logger.info("In recStatus "+recStatus);
					
					logger.info("In openEditUser userMasterVo list"+list.size());
					request.setAttribute("list", list);
					//request.setAttribute("levelNameList", user);
				/*	SubDealerMasterVo docVo=new SubDealerMasterVo();
					docVo=list.get(0);*/
					//logger.info("In openEditSubDealer---status---- by   getpara by vo-"+subDealerMasterVo.getSubDealerStatus());
					request.setAttribute("status",recStatus);
					request.setAttribute("editVal", "editVal");
				   return mapping.findForward("editSubDealer");	
				}
		
		
		public ActionForward updateSubDealer(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
			ServletContext context = getServlet().getServletContext();
			logger.info("In updateSubDealerMaster.......");
			HttpSession session = request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			Object sessionId = session.getAttribute("sessionID");
			
			
			userobj=(UserObject)session.getAttribute("userobject");
			String userId="";
			String bDate="";
			
			String procval="";
			
			if(userobj!=null)
			{
					userId=userobj.getUserId();
					bDate=userobj.getBusinessdate();
			}
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
			
			SubDealerMasterVo vo=new SubDealerMasterVo(); 
			DynaValidatorForm SubDealerMasterAddDyanavalidatiorForm= (DynaValidatorForm)form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(vo, SubDealerMasterAddDyanavalidatiorForm);	

			CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
			
			logger.info("In updateSubDealerDetails---status by form- dealerId----"+SubDealerMasterAddDyanavalidatiorForm.getString("subDealerCode"));  
			vo.setSubDealerStatus(request.getParameter("subDealerStatus"));
			logger.info("In ---status-----"+vo.getSubDealerStatus());
			vo.setSubDealerDes(request.getParameter("subDealerDes"));

			logger.info("In updateSubDealerDetails---status---- by getpara-"+request.getParameter("subDealerStatus"));  
			logger.info("In updateSubDealerDetails---status---- by getpara by vo-"+vo.getSubDealerStatus());
			logger.info("In updateSubDealerDetails---------");    
			vo.setMakerId(userId);
			vo.setMakerDate(bDate);			
	        String resultStatus=bp.updateSubDealerData(vo);
	        String sms="";
	        if(resultStatus.equalsIgnoreCase("saved")){
	        	sms="M";
				request.setAttribute("sms",sms);
				request.setAttribute("editValUpdate", "editValUpdate");
			}
			else if(resultStatus.equalsIgnoreCase("notsaved")){
				sms="E";
				request.setAttribute("sms",sms);
				ArrayList<SubDealerMasterVo> list =new ArrayList<SubDealerMasterVo>();
				list.add(vo);
				logger.info("In updateSubDealerDetails list"+ list.size());
				
				request.setAttribute("editValUpdate", "editValUpdate");
				request.setAttribute("list", list);
				request.setAttribute("status", vo.getSubDealerStatus());
			}else if(resultStatus.equalsIgnoreCase("dataExist")){
				sms="DE";
				request.setAttribute("sms",sms);
				ArrayList<SubDealerMasterVo> list =new ArrayList<SubDealerMasterVo>();
				list.add(vo);
				logger.info("In updateSubDealerDetails list"+ list.size());
				
				request.setAttribute("editValUpdate", "editValUpdate");
				request.setAttribute("list", list);
				request.setAttribute("status", vo.getSubDealerStatus());
			}
	                      
	        return mapping.getInputForward();
	      
			
		}

}