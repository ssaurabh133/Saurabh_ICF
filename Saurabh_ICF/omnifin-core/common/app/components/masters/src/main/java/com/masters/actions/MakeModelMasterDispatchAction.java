//Author Name : neeraj kumar Tripathi-->



package com.masters.actions;

import java.util.ArrayList;
import java.util.Iterator;
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
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.vo.MakeModelmasterVO;


public class MakeModelMasterDispatchAction extends DispatchAction
{
	static final Logger logger = Logger.getLogger(MakeModelMasterDispatchAction.class.getName());
	
	public ActionForward addNewMakeModel(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ServletContext context = getServlet().getServletContext();
		logger.info(" in addNewMakeModel()......................................");		
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
			
			//Abhimanyu newly added on 18/07/2012
			session.removeAttribute("search");
			
			CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
			
	        ArrayList list=cpm.getProductCategory();
	        if(list.size()>0)
	        {
	        	request.setAttribute("ProductCategory", list);
	        }
	        //MRADUL starts
	        ArrayList list1=cpm.getVehicleSegment();
	        if(list.size()>0)
	        {
	        	request.setAttribute("VehicleCategory", list1);
	        }
	        //mradul ends
	      //Abhimanyu newly added on 18/07/2012
			
			
			return mapping.findForward("success");
	}
	
	public ActionForward saveMakeModelrecord(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ServletContext context = getServlet().getServletContext();
		logger.info(" in saveMakeModelrecord()......................................");		
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
		MakeModelmasterVO vo=new MakeModelmasterVO();
		DynaValidatorForm formbeen= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, formbeen);		
		
		CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		String makerId="";
		String makerDate="";
		String authorId="";
		String authorDate="";
		String ProductCategory=vo.getProductCategory();
		//String make=vo.getMake();
		//String model=vo.getModel();
		
		String state=CommonFunction.checkNull(request.getParameter("state"));
		String state1=state.replace("|",",");
		//int a=state1.lastIndexOf(",");
		state1=state1.substring(0,state1.lastIndexOf(","));
		vo.setStateId(state1);
		// String stateid=vo.getStateId();
		
		//logger.info("saurabh"+state+"singh");
		String recStatus=request.getParameter("status");
		if(recStatus!=null)
		 vo.setRecStatus("A");
		else
		 vo.setRecStatus("X");
		
		boolean exist=false;			
		  exist=cpm.checkRecord(vo);		
		if(exist)
		{			
			request.setAttribute("exist","exist");	
			int makemodelid=cpm.getMakeModelID(vo);
			logger.info("makemodelid###############"+makemodelid);
			if(makemodelid>=1)
			{

			 //ArrayList<MakeModelmasterVO> list= cpm.getParticularRecord(String.valueOf(makemodelid));
				
				ArrayList<MakeModelmasterVO> list=new ArrayList<MakeModelmasterVO>();
				//logger.info("check box value "+vo.getRecStatus());
				if(vo.getRecStatus().equalsIgnoreCase("A")){
					request.setAttribute("A","A");
				}
				else{
					request.setAttribute("X","X");
				}
			 list.add(vo);
			 
			 request.setAttribute("list",list);
			 
//				if(list.size()>0){
//					MakeModelmasterVO newVo=list.get(0);
//					String status=newVo.getRecStatus().trim();
//					if(status.trim().equals("X"))
//					request.setAttribute("X","X");
//					if(status.trim().equals("A"))
//					request.setAttribute("A","A");			
//					request.setAttribute("list",list);					
//				}		
				    ArrayList list1=cpm.getProductCategory();
			        if(list1.size()>0)
			        {
			        	request.setAttribute("ProductCategory", list1);
			        }		
			
			request.setAttribute("makeModelId",makemodelid);
			}
			
		}
		
		else
		{
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
			
			boolean status=false;
			status=cpm.saveMakeModelRecord(vo,state);
			if(status){
				request.setAttribute("save","save");
			}
			else{
				request.setAttribute("notSave","notSave");
			}
			logger.info("value of drop down "+vo.getVct());
		
		}
		return mapping.findForward("success");
	}
	public ActionForward searchMakeModelRecords(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		logger.info(" in searchMakeModelRecords()......................................");		
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
		logger.info("current page link ................ "+currentPageLink);
		MakeModelmasterVO vo=new MakeModelmasterVO();
		DynaValidatorForm formbeen= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, formbeen);
		
		CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		vo.setCurrentPageLink(currentPageLink);
		ArrayList<MakeModelmasterVO> list= cpm.searchMakeModelRecords(vo);
		request.setAttribute("list",list);
		request.setAttribute("search","search");
		
		ArrayList list1=cpm.getProductCategory();
        if(list1.size()>0)
        {
        	 request.setAttribute("ProductCategory", list1);	
        }
       
		return mapping.findForward("success");
	}
	
	public ActionForward updateMakeModelMaster(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{ 
		logger.info(" in updateMakeModelMaster()......................................");		
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
		String makeModelId=request.getParameter("makeModelId");
		logger.info("makeModelId: "+makeModelId);
		
		CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
        ArrayList<MakeModelmasterVO> list= cpm.getParticularRecord(makeModelId);
		if(list.size()>0){
			MakeModelmasterVO newVo=list.get(0);
			String status=newVo.getRecStatus().trim();
			if(status.trim().equals("X"))
			request.setAttribute("X","X");
			if(status.trim().equals("A"))
			request.setAttribute("A","A");			
			request.setAttribute("list",list);
			request.setAttribute("search","search");
		}	
		ArrayList<MakeModelmasterVO>   sublist=new ArrayList<MakeModelmasterVO>();
		
		for(int j=1;j<list.size();j++){
			if(list.size()>1){
				
				sublist.add(list.get(j));
				}			
		}

		request.setAttribute("mapp",sublist);
		request.setAttribute("makeModelId",makeModelId);
		String stateDesc ="";
		
		

		Iterator<MakeModelmasterVO> it= sublist.iterator();
		
		while(it.hasNext())
		{
			MakeModelmasterVO  vo1 = (MakeModelmasterVO) it.next();
			logger.info("vo1.getStateId()---"+vo1.getState());
			stateDesc=stateDesc+vo1.getState()+"|";
		
		}
		if(!stateDesc.equalsIgnoreCase(""))
			stateDesc = stateDesc.substring(0,stateDesc.length()-1);
		logger.info("userDesc--2--"+stateDesc);
		
		logger.info("userDesc String ............................... "+stateDesc);

		request.setAttribute("stateIds", stateDesc);
		
			
		
		    ArrayList list1=cpm.getProductCategory();
	        if(list1.size()>0)
	        {
	        	request.setAttribute("ProductCategory", list1);
	        }
	        //MRADUL starts
	        ArrayList vehicle=cpm.getVehicleSegment();
	        if(list.size()>0)
	        {
	        	request.setAttribute("VehicleCategory", vehicle);
	        }
	        //mradul ends
		return mapping.findForward("success");
	}
	
	public ActionForward updateMakeModelRecord(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		logger.info(" in updateMakeModelRecord()......................................");	
		ServletContext context = getServlet().getServletContext();
		HttpSession session =  request.getSession();
		boolean flag=false;
		String makeModelId=request.getParameter("makemodelIData");
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
		MakeModelmasterVO vo=new MakeModelmasterVO();
		DynaValidatorForm formbeen= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, formbeen);
		
		CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		String makerId="";
		String makerDate="";
		String authorId="";
		String authorDate="";
		if(userobj != null)
		{
			makerId=userobj.getUserId();
			makerDate=userobj.getBusinessdate();
			authorId=makerId;
			authorDate=makerDate;
		}
		logger.info("makeModelId"+makeModelId);
		vo.setMakeModelId(makeModelId);
		vo.setMakerId(makerId);
		vo.setMakerDate(makerDate);
		vo.setAuthorId(authorId);
		vo.setAuthorDate(authorDate);
		String recStatus=request.getParameter("status");
		String[] stateId=null;
		if(!CommonFunction.checkNull(request.getParameter("stateArr")).equalsIgnoreCase("")){
			String stateArr=CommonFunction.checkNull(request.getParameter("stateArr"));
			
			logger.info("user........"+stateArr);
			stateId = stateArr.split("/");
			 
			for(int i=0;i<stateId.length;i++){
		    logger.info("userName[i]----------->"+stateId[i]);
			 }
			}
		
		
		
		if(recStatus!=null)
			vo.setRecStatus("A");
		else
			vo.setRecStatus("X");
		
		boolean exist=false;			
		  exist=cpm.checkRecordforUpdate(vo);
		  logger.info("exittttttttttt"+exist);
		if(exist)
		{
//			 ArrayList<MakeModelmasterVO> list= cpm.getParticularRecord(makeModelId);
//				if(list.size()>0){
//					MakeModelmasterVO newVo=list.get(0);
//					String status=newVo.getRecStatus().trim();
//					if(status.trim().equals("X"))
//					request.setAttribute("X","X");
//					if(status.trim().equals("A"))
//					request.setAttribute("A","A");	
				ArrayList<MakeModelmasterVO> list=new ArrayList<MakeModelmasterVO>();
				if(vo.getRecStatus().equalsIgnoreCase("A")){
					request.setAttribute("A","A");
				}
				else{
					request.setAttribute("X","X");
				}
			    list.add(vo);
			
					request.setAttribute("list",list);
					// request.setAttribute("search","search");
					session.setAttribute("search","search");
				
				    ArrayList list1=cpm.getProductCategory();
			        if(list1.size()>0)
			        {
			        	request.setAttribute("ProductCategory", list1);
			        }
			
			request.setAttribute("existsameforupdate","exist");
			request.setAttribute("makeModelId",makeModelId);
			//formbeen.reset(mapping, request);			
		}
		else
		{		
		String  status="";
		status=cpm.updateMakeModelRecord(vo,stateId);
		if(CommonFunction.checkNull(status).equalsIgnoreCase("saved"))
				request.setAttribute("update","update");
		}
		
		return mapping.findForward("success");
	}
}
