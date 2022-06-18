package com.masters.actions;

import java.util.ArrayList;
import java.util.Iterator;

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
import com.masters.vo.ManufacturerSupplierMappingVO;


public class ManufacturerSupplierMappingDispatchAction extends DispatchAction
{
	static final Logger logger = Logger.getLogger(MakeModelMasterDispatchAction.class.getName());
	
	public ActionForward openMfrSupplMappingScreen(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		logger.info(" in openMfrSupplMappingScreen()......................................");
		ServletContext context = getServlet().getServletContext();				
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
		String strFlag="";	
		if(sessionId != null && userobj != null)
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
					context.setAttribute("msg","B");
				}
				return mapping.findForward("logout");
			}
			
			//session.removeAttribute("search");
			CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
			
			return mapping.findForward("openMfsupplierMapping");
	}
	
	public ActionForward saveMfrSupplMappingRecord(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		logger.info(" in saveMfrSupplMappingRecord......................................");
		ServletContext context = getServlet().getServletContext();				
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
		String strFlag="";	
		if(sessionId!=null && userobj != null)
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		logger.info("strFlag .............. "+strFlag);
		if(!strFlag.equalsIgnoreCase(""))
		{
				if(strFlag.equalsIgnoreCase("sameUserSession"))
				{
					context.removeAttribute("msg");
					context.removeAttribute("msg1");
				}
				else if(strFlag.equalsIgnoreCase("BODCheck"))
					context.setAttribute("msg","B");
				return mapping.findForward("logout");
		}
		session.removeAttribute("search");
		ManufacturerSupplierMappingVO vo=new ManufacturerSupplierMappingVO();
		DynaValidatorForm formBean= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, formBean);		
		
		CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		
		String makerId="";
		String makerDate="";
		String authorId="";
		String authorDate="";
				
		String supplier=CommonFunction.checkNull(request.getParameter("supplier"));
		logger.info("supplier:::::::::::::"+supplier.length());
		String[] supplierId=supplier.split(",");
		for(int i=0;i<supplierId.length;i++){
			logger.info("supplierId:::::::::::::"+supplierId[i]);
		}
		
        		if(userobj != null)
			{
				makerId=userobj.getUserId();
				makerDate=userobj.getBusinessdate();
				authorId=makerId;
				authorDate=makerDate;
			}
			vo.setMakerId(makerId);
			vo.setMakerDate(makerDate);
			vo.setAuthorId(authorId);
			vo.setAuthorDate(authorDate);
			ArrayList<ManufacturerSupplierMappingVO> list=new ArrayList<ManufacturerSupplierMappingVO>();
			list.add(vo);
			boolean status=false;
			status=cpm.saveMfrSuppMappingRecord(vo,supplierId);
			if(status){
				request.setAttribute("save","save");
				request.setAttribute("list",list);
			}
			else{
				request.setAttribute("list",list);
				request.setAttribute("dataExist","dataExist");
			}
				
		return mapping.findForward("saveMfsupplierMapping");
	}
	
	
	public ActionForward openMfrSupplMappingRecords(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		logger.info(" in openMfrSupplMappingRecords......................................");		
		ServletContext context = getServlet().getServletContext();
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
		String strFlag="";	
		if(sessionId!=null && userobj != null)
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		logger.info("strFlag .............. "+strFlag);
		if(!strFlag.equalsIgnoreCase(""))
		{
				if(strFlag.equalsIgnoreCase("sameUserSession"))
				{
					context.removeAttribute("msg");
					context.removeAttribute("msg1");
				}
				else if(strFlag.equalsIgnoreCase("BODCheck"))
					context.setAttribute("msg","B");
				return mapping.findForward("logout");
		}
		session.removeAttribute("search");
		logger.info("current page link .......... "+request.getParameter("d-49520-p"));			
		int currentPageLink = 0;
		if(request.getParameter("d-49520-p")==null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
			currentPageLink=1;
		else
			currentPageLink =Integer.parseInt(request.getParameter("d-49520-p"));
		
		ManufacturerSupplierMappingVO vo=new ManufacturerSupplierMappingVO();
		DynaValidatorForm formbeen= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, formbeen);
		
		CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		vo.setCurrentPageLink(currentPageLink);
		String mappingId=request.getParameter("mappingId");
		logger.info("mappingId::::::::::::::::::::::::::::"+mappingId);
		
		vo.setMappingId(mappingId);
		ArrayList<ManufacturerSupplierMappingVO> list= cpm.searchManufacturerSupplierMappingList(vo);
		logger.info("list size:::::::::::"+list.size());
		
		
		if(list.size()>0){
			ManufacturerSupplierMappingVO newVo=list.get(0);
			String status=newVo.getRecStatus().trim();
			logger.info("status==========="+status);
			if(status.trim().equalsIgnoreCase("Active")){
				logger.info("In Side If Block==========="+status);
				request.setAttribute("Active","Active");	
			}else{
			logger.info("Out Side If Block==========="+status);
			request.setAttribute("InActive","InActive");
			    }
			ArrayList<ManufacturerSupplierMappingVO> supplierDescList = cpm.searchSupplierDescEdit(mappingId);
			String supplierDesc ="";
			Iterator<ManufacturerSupplierMappingVO> it= supplierDescList.iterator();
			
			while(it.hasNext())
			{
				ManufacturerSupplierMappingVO  vo1 = (ManufacturerSupplierMappingVO) it.next();
				logger.info("vo1.getlbxSupplierId()==========="+vo1.getSupplierId());
				supplierDesc=supplierDesc+vo1.getSupplierId()+"|";
				logger.info("supplierDesc================"+supplierDesc);	
			}
			if(!supplierDesc.equalsIgnoreCase(""))
				supplierDesc = supplierDesc.substring(0,supplierDesc.length()-1);
			logger.info("supplierDesc--2---------------------"+supplierDesc);			
			logger.info("supplierDesc Desc String ............................... "+supplierDesc);
			request.setAttribute("supplierDescIds", supplierDesc);
			
			
			request.setAttribute("list",list);
			request.setAttribute("supplierDescList", supplierDescList);		   				
			request.setAttribute("edit","edit");
		}	
				
		
				     
		return mapping.findForward("openEditMfsupplierMapping");
	}
	
	public ActionForward updateMfrSupplMappingRecords(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		logger.info(" in updateMfrSupplMappingRecords......................................");	
		ServletContext context = getServlet().getServletContext();
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
		String strFlag="";	
		if(sessionId!=null && userobj != null)
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		logger.info("strFlag .............. "+strFlag);
		if(!strFlag.equalsIgnoreCase(""))
		{
				if(strFlag.equalsIgnoreCase("sameUserSession"))
				{
					context.removeAttribute("msg");
					context.removeAttribute("msg1");
				}
				else if(strFlag.equalsIgnoreCase("BODCheck"))
					context.setAttribute("msg","B");
				return mapping.findForward("logout");
		}
		session.removeAttribute("search");
		ManufacturerSupplierMappingVO vo=new ManufacturerSupplierMappingVO();
		DynaValidatorForm formBean= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, formBean);		
		
		CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		String makerId="";
		String makerDate="";
		String authorId="";
		String authorDate="";
		ArrayList<ManufacturerSupplierMappingVO> list=new ArrayList<ManufacturerSupplierMappingVO>();		
		String supplier=CommonFunction.checkNull(request.getParameter("supplier"));
		logger.info("supplier::in Update:::::::::::"+supplier.length());
		String[] supplierId=supplier.split(",");
		for(int i=0;i<supplierId.length;i++){
			logger.info("supplierId:::in Update::::::::::"+supplierId[i]);
		}
		
        		if(userobj != null)
			{
				makerId=userobj.getUserId();
				makerDate=userobj.getBusinessdate();
				authorId=makerId;
				authorDate=makerDate;
			}
			vo.setMakerId(makerId);
			vo.setMakerDate(makerDate);
			vo.setAuthorId(authorId);
			vo.setAuthorDate(authorDate);
			list.add(vo);
			boolean status=false;
			status=cpm.updateMfrSuppMappingRecord(vo,supplierId);
			if(status){
				request.setAttribute("update","update");
				request.setAttribute("edit","edit");
				
				request.setAttribute("list",list);
			}
			else{
				request.setAttribute("list",list);
				request.setAttribute("edit","edit");
				request.setAttribute("notSave","notSave");
			}
                  
		return mapping.findForward("saveMfsupplierMapping");
	}
	
	
}
