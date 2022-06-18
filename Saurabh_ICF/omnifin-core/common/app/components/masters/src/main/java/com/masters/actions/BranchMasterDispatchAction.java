//Author Name : Ritu Jindal-->
//Date of Creation : -->
//Purpose  : Dispatch Action For Branch Master-->
//Documentation : -->

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

import com.business.ejbClient.CommonMasterBussinessSessionBeanRemote;
import com.connect.CommonFunction;
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.vo.BranchMasterAreaCodeVo;
import com.masters.vo.BranchMasterVo;


public class BranchMasterDispatchAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(BranchMasterDispatchAction.class.getName());
	public ActionForward openAddBranch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		ServletContext context = getServlet().getServletContext();
				logger.info(" in openAddBranch()");
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
				
				CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		        /*KANIKA CODE*/
		        ArrayList areaCodelist=bp.getAreaCode();
		        /*END KANIKA*/
		        ArrayList defaultcountry=bp.defaultCountry();
				request.setAttribute("defaultcountry", defaultcountry);
				session.setAttribute("branchID",userobj.getBranchId());
				  /*KANIKA CODE*/
				request.setAttribute("areaCodelist", areaCodelist);
				/*END KANIKA*/
				request.setAttribute("save", "save");
			    return mapping.findForward("openAdd");	
			}
	
	
	public ActionForward openEditBranch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
			
			BranchMasterVo branchMasterVo=new BranchMasterVo(); 
			ServletContext context = getServlet().getServletContext();
			logger.info("In openEditBranch");
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
				String branchId=CommonFunction.checkNull(request.getParameter("branchId"));
				branchMasterVo.setBranchId(branchId);
				logger.info("branchId-"+request.getParameter("branchId"));  
				logger.info("branchId vo-"+branchMasterVo.getBranchId());
				
				CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
				
		        ArrayList list = bp.searchBranchData(branchMasterVo);
		        logger.info("branchId---------------->"+branchId);
		       
		        ArrayList<BranchMasterAreaCodeVo> areaCodeList = bp.searchAreaCodeBranchEdit(branchId);
		        String areaDesc ="";
		    	Iterator<BranchMasterAreaCodeVo> it= areaCodeList.iterator();
		        while(it.hasNext())
				{
		        	BranchMasterAreaCodeVo  vo1 = (BranchMasterAreaCodeVo) it.next();
				
		        	areaDesc=areaDesc+vo1.getAreaCode()+"|";
		        	logger.info("branchDesc---"+areaDesc);
					
				}
				if(!areaDesc.equalsIgnoreCase(""))
					areaDesc = areaDesc.substring(0,areaDesc.length()-1);
			
				 branchMasterVo=(BranchMasterVo) list.get(0);
				 
				logger.info("In openEditBranch branchMasterVo list"+list.size());
				session.setAttribute("branchID",userobj.getBranchId());
				request.setAttribute("list", list);
				request.setAttribute("areaDescIds", areaDesc);
				request.setAttribute("areaCodeList", areaCodeList);
				request.setAttribute("status", branchMasterVo.getBranchStatus());
				request.setAttribute("editVal", "editVal");
			   return mapping.findForward("openAdd");	
			}
	


	public ActionForward saveBranchDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
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
		
		userobj=(UserObject)session.getAttribute("userobject");
		
		String userId="";
		String bDate="";
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
		}
		
		DynaValidatorForm BranchMasterAddDynaValidatorForm= (DynaValidatorForm)form;
		BranchMasterVo branchMasterVo = new BranchMasterVo();
		
		branchMasterVo.setMakerId(userId);
		branchMasterVo.setMakerDate(bDate);
		
		org.apache.commons.beanutils.BeanUtils.copyProperties(branchMasterVo, BranchMasterAddDynaValidatorForm);	
		
		CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
        String sms="";
       
        String[] areaCode=null;
        logger.info("request.getParameter(areaCode)111111111"+CommonFunction.checkNull(request.getParameter("areaCode")));
        String ar=CommonFunction.checkNull(request.getParameter("areaCode"));
        logger.info("ar:-"+ar);
        
        if(!ar.equals("")){
          areaCode=ar.split("/");
        }
    
		String result  = bp.insertBranchMaster(branchMasterVo);
		
		
		logger.info("Inside Branch Master Action.....displaying status...."+CommonFunction.checkNull(result));
		if(!result.equalsIgnoreCase("")){
			branchMasterVo.setBranchId(result);
			
			boolean status2 = bp.insertBranchAreaCode(branchMasterVo,areaCode);
		
			if(status2){
			sms="S";
			request.setAttribute("sms",sms);
			request.setAttribute("save", "save");
			}else if(!result.equals("")){
				sms="S";
				request.setAttribute("sms",sms);
				request.setAttribute("save", "save");
				}
			else{
				sms="E";
				request.setAttribute("sms",sms);
				request.setAttribute("save", "save");
			}
			 logger.info("status2"+status2);
			
		   
		}
		else{
		
						sms="E";
						request.setAttribute("sms",sms);
						request.setAttribute("save", "save");
					
		}
	    logger.info("result"+result);

		return mapping.getInputForward();
	}
	
		public ActionForward updateBranch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
			ServletContext context = getServlet().getServletContext();
		logger.info("In updateBranch.......");
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
		
		userobj=(UserObject)session.getAttribute("userobject");
		
		String userId="";
		String bDate="";
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
		}
		
		BranchMasterVo branchMasterVo=new BranchMasterVo(); 
		DynaValidatorForm BranchMasterAddDynaValidatorForm= (DynaValidatorForm)form;
		
		branchMasterVo.setMakerId(userId);
		branchMasterVo.setMakerDate(bDate);
		  
		org.apache.commons.beanutils.BeanUtils.copyProperties(branchMasterVo, BranchMasterAddDynaValidatorForm);	
		
		logger.info("In updateBranch---status-----"+branchMasterVo.getBranchStatus());
		//branchMasterVo.setBranchStatus(request.getParameter("branchStatus"));
		//logger.info("In updateLoanDetails---status---- by getpara-"+request.getParameter("branchStatus"));
		   
		CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
        String[] areaCode=null;
        logger.info("request.getParameter(areaCode)111111111"+CommonFunction.checkNull(request.getParameter("areaCode")));
        String ar=CommonFunction.checkNull(request.getParameter("areaCode"));
        logger.info("ar:-"+ar);
        
        if(!ar.equals("")){
          areaCode=ar.split("/");
        }
    
       
        boolean status=bp.updateBranchData(branchMasterVo);
        String sms="";
        if(status){
        	
        	boolean status2 = bp.insertBranchAreaCode(branchMasterVo,areaCode);
        	if(status2){
			sms="M";
			request.setAttribute("sms",sms);
			request.setAttribute("editValUpdate", "editValUpdate");
        	}else if(status){
        		sms="M";
        		request.setAttribute("sms",sms);
    			request.setAttribute("editValUpdate", "editValUpdate");
        		}
        	else{
        		sms="E";
        		request.setAttribute("sms",sms);
        		request.setAttribute("save", "save");
					
        	}
		}
		else{
			sms="E";
			request.setAttribute("sms",sms);
			ArrayList<BranchMasterVo> list =new ArrayList<BranchMasterVo>();
			list.add(branchMasterVo);
			logger.info("In openEditBranch list"+ list.size());
			
			request.setAttribute("editValUpdate", "editValUpdate");
			request.setAttribute("list", list);
			request.setAttribute("status", branchMasterVo.getBranchStatus());
			
		}
        branchMasterVo=new BranchMasterVo(); 
        return mapping.getInputForward();
       
		
	}

}