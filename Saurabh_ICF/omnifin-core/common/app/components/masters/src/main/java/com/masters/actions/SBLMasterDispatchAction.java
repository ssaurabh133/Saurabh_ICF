/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.masters.actions;

import java.util.ArrayList;
import java.util.Properties;

import javax.naming.InitialContext;
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
import com.logger.LoggerMsg;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.vo.ProductMasterVo;


public class SBLMasterDispatchAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(SBLMasterDispatchAction.class.getName());
	
	public ActionForward openAddProduct(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		ServletContext context = getServlet().getServletContext();
			LoggerMsg.info("in Product openAddProduct()");
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
			
			ArrayList list = cpm.getProductCategory();
			request.setAttribute("productCategory", list);
			request.setAttribute("save", "save");
			return mapping.findForward("openAdd");
	}
	

	
	public ActionForward savesblDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse resopnse) throws Exception{
		ServletContext context = getServlet().getServletContext();
		HttpSession session=request.getSession(false);
		UserObject userobj=new UserObject();
		userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String bDate="";
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
		DynaValidatorForm GBLSBLMasterAddDyanavalidatiorForm= (DynaValidatorForm)form;
		ProductMasterVo vo = new ProductMasterVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, GBLSBLMasterAddDyanavalidatiorForm);	
		
		vo.setMakerId(userId);
		vo.setMakerDate(bDate);
		if(vo.getRecStatus().equalsIgnoreCase("on")){
			vo.setRecStatus("A");
		}else{
			vo.setRecStatus("X");	
		}
		CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		String sms="";
	
		boolean status = cpm.insertSblMaster(vo);
		logger.info("Inside Product Master Action.....displaying status...."+status);
		if(status){
			sms="S";
			request.setAttribute("sms",sms);
			request.setAttribute("save", "save");
		}
		else{
			sms="E";
			request.setAttribute("sms",sms);
			ArrayList list = cpm.getProductCategory();
			request.setAttribute("productCategory", list);
			request.setAttribute("save", "save");
		}
		logger.info("status"+status);
		return mapping.getInputForward();
	}
	
		
	
		public ActionForward openEditProduct(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)	throws Exception {
			ServletContext context = getServlet().getServletContext();
							ProductMasterVo productMasterVo=new ProductMasterVo(); 
							logger.info("In openEditProduct");
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
							productMasterVo.setProductSearchId(request.getParameter("ProductSearchId"));

							CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
							
					        
							ArrayList<ProductMasterVo> list = cpm.searchSblData(productMasterVo);
							logger.info("In openEditProduct ProductMasterVo list"+list.size());
							request.setAttribute("list", list);
							request.setAttribute("recStatus",list.get(0).getRecStatus());
							request.setAttribute("editVal", "editVal");
							session.setAttribute("productSearchId", productMasterVo.getProductSearchId());
						   return mapping.findForward("editProduct");	
						}
				
				
				public ActionForward updateSBL(ActionMapping mapping, ActionForm form,
						HttpServletRequest request, HttpServletResponse response) throws Exception {
					ServletContext context = getServlet().getServletContext();
					logger.info("In updateSBLMaster.......");
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
					ProductMasterVo vo=new ProductMasterVo(); 
					DynaValidatorForm GBLSBLMasterAddDyanavalidatiorForm= (DynaValidatorForm)form;
					org.apache.commons.beanutils.BeanUtils.copyProperties(vo, GBLSBLMasterAddDyanavalidatiorForm);	
					vo.setMakerId(userId);
					vo.setMakerDate(bDate);
					if(vo.getRecStatus().equalsIgnoreCase("on")){
						vo.setRecStatus("A");
					}else{
						vo.setRecStatus("X");	
					}
					/*logger.info("In updateProducttDetails---status by form- ProductId----"+GBLSBLMasterAddDyanavalidatiorForm.getString("productId"));  
					vo.setRecStatus(request.getParameter("recStatus"));
					logger.info("In updateProducttDetails---status-----"+vo.getRecStatus());
					logger.info("In updateProducttDetails---module Type-----"+vo.getModuleType());*/
					/*vo.setProductDes(request.getParameter("productDes"));*/

					CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
					String productSearchId=(String)session.getAttribute("productSearchId");
					vo.setProductSearchId(productSearchId);
			        boolean status=cpm.updateSBLData(vo);
			        String sms="";
			        if(status){
			        	sms="M";
						request.setAttribute("sms",sms);
						request.setAttribute("editValUpdate", "editValUpdate");
					}
					else{
						sms="E";
						request.setAttribute("sms",sms);
						ArrayList<ProductMasterVo> list =new ArrayList<ProductMasterVo>();
						list.add(vo);
						/*ArrayList list1 = cpm.getProductCategory();
						request.setAttribute("productCategory", list1);
						logger.info("In openEditProduct list"+ list.size());*/
						
						request.setAttribute("editValUpdate", "editValUpdate");
						request.setAttribute("list", list);
						//request.setAttribute("status", vo.getRecStatus());
					}
			        	                      
			        return mapping.getInputForward();
			      
					
				}
				
				
				
				
	
}