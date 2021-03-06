/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.legal.actions;

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

import com.business.legalMasterBussiness.LegalMasterBusinessSessionBeanRemote;
import com.connect.CommonFunction;
import com.connect.LookUpInstanceFactory;
import com.legal.vo.POAMasterVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class POAMasterDispatchAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(POAMasterDispatchAction.class.getName());
	public ActionForward openAddPOA(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception
			{
				logger.info(" in openAddPOA()");
				ServletContext context = getServlet().getServletContext();
				HttpSession session = request.getSession();
				
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				Object sessionId = session.getAttribute("sessionID");
				//for check User session start
				String strFlag=null;
			
				
				if(sessionId!=null)
				{
					strFlag = CommonFunction.checkNull(UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request));
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
	
	
	public ActionForward savePOADetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse resopnse) throws Exception{
			ServletContext context = getServlet().getServletContext();
			//HttpSession session=request.getSession(false);
			HttpSession session = request.getSession();
		
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			Object sessionId = session.getAttribute("sessionID");
			//for check User session start
			String strFlag=null;
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
			
			String userId=null;
			String bDate=null;
			if(userobj!=null)
			{
					userId=userobj.getUserId();
					bDate=userobj.getBusinessdate();
			}
	
		DynaValidatorForm caseTyeMasterAddDyanavalidatiorForm= (DynaValidatorForm)form;
		POAMasterVo vo = new POAMasterVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, caseTyeMasterAddDyanavalidatiorForm);
		

		vo.setMakerId(userId);
		vo.setMakerDate(bDate);
		
		LegalMasterBusinessSessionBeanRemote bp = (LegalMasterBusinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(LegalMasterBusinessSessionBeanRemote.REMOTE_IDENTITY, request);
	
		String sms=null;
	
		String courtname=CommonFunction.checkNull(request.getParameter("lbxCourtNameCode"));
		
		logger.info("branchs........"+courtname);
		String[] courtName = courtname.split("\\|");
		 
		for(int i=0;i<courtName.length;i++){
	    logger.info("value is:::::::::::::::::::::::::::"+courtName[i]);
		 }
		
		boolean status = bp.insertPOAMaster(vo,courtName);
		
		logger.info("Inside Country Master Action.....displaying status...."+status);
		if(status){
			sms="S";
			request.setAttribute("sms",sms);
			request.setAttribute("save", "save");
		}
		else{
			sms="E";
			request.setAttribute("sms",sms);
			request.setAttribute("save", "save");
		}
		logger.info("status"+status);
		return mapping.getInputForward();
	}
	
	
	public ActionForward openEditPOAMaster(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception { 
		ServletContext context = getServlet().getServletContext();
						POAMasterVo vo=new POAMasterVo(); 
						logger.info("In openEditPOAMaster");
						
						HttpSession session = request.getSession();
					
						UserObject userobj=(UserObject)session.getAttribute("userobject");
						Object sessionId = session.getAttribute("sessionID");
						//for check User session start
						String strFlag=null;
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
						String poaCode =CommonFunction.checkNull(request.getParameter("poaCode"));
						vo.setPoaCode(poaCode);
						logger.info("In openEditPOAMaster---status---- by getpara-"+poaCode);  
						logger.info("In openEditPOAMaster---status---- by getpara by vo-"+vo.getPoaCode());
						
						LegalMasterBusinessSessionBeanRemote bp = (LegalMasterBusinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(LegalMasterBusinessSessionBeanRemote.REMOTE_IDENTITY, request);
						
						ArrayList list = bp.openEditPOAData(vo);
					
						ArrayList<POAMasterVo> courtList = bp.getcourtNameForPOaEdit(poaCode);
						
						String courtNameIds = null;
						Iterator<POAMasterVo> it= courtList.iterator();
						
						while(it.hasNext())
						{
							POAMasterVo  vo1 = (POAMasterVo) it.next();
							logger.info("vo1.getBranchId()---"+vo1.getLbxCourtNameCode());
							courtNameIds=CommonFunction.checkNull(courtNameIds)+vo1.getCourtName()+"|";
							logger.info("courtNameIds---"+courtNameIds);
							
						}
						if(!CommonFunction.checkNull(courtNameIds).equalsIgnoreCase(""))
							courtNameIds = CommonFunction.checkNull(courtNameIds).substring(0,courtNameIds.length()-1);
						logger.info("branchDesc--2--"+courtNameIds);
						
						
						logger.info("In openEditPOAMaster POAMasterVo list"+list.size());
						logger.info("In openEditPOAMaster() : courtNameDes : ==>> "+courtNameIds);
						request.setAttribute("list", list);
						request.setAttribute("courtNameID", courtNameIds);
						request.setAttribute("courtNameList", courtList);
						
						vo=(POAMasterVo) list.get(0);
						POAMasterVo docVo=new POAMasterVo();
						docVo=(POAMasterVo) list.get(0);
						logger.info("In openEditPOAMaster---status---- by getpara by vo-"+docVo.getRecStatus());
						
					
						request.setAttribute("status", vo.getRecStatus());
						request.setAttribute("editVal", "editVal");
					   return mapping.findForward("openEditPOA");	
					}
			
	public ActionForward updatePOA(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ServletContext context = getServlet().getServletContext();
		logger.info("In updatePOAMaster.......");
		HttpSession session = request.getSession();
		
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
		String strFlag=null;
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
		
		String userId=null;
		String bDate=null;
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
		}
		
		POAMasterVo poaMastervo =new POAMasterVo(); 
		DynaValidatorForm CountryMasterAddDyanavalidatiorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(poaMastervo, CountryMasterAddDyanavalidatiorForm);	

		logger.info("In updatePOADetails---------");  
		poaMastervo.setMakerId(CommonFunction.checkNull(userId));
		poaMastervo.setMakerDate(CommonFunction.checkNull(bDate));
		
		
		LegalMasterBusinessSessionBeanRemote bp = (LegalMasterBusinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(LegalMasterBusinessSessionBeanRemote.REMOTE_IDENTITY, request);
        
        String court=CommonFunction.checkNull(request.getParameter("lbxCourtNameCode"));
		
		logger.info("caseType........"+court);
		String[] courtName = court.split("\\|");
		 
		for(int i=0;i<courtName.length;i++){
	    logger.info("## In updatePOA(): courtName[i] : ==>> "+courtName[i]);
		 }
	
    boolean status=bp.updatePOAData(poaMastervo,courtName);
        
        String sms=null;
        if(status){
			sms="M";
			request.setAttribute("sms",sms);
			request.setAttribute("editVal", "editVal");
		}
		else{
			sms="E";
			request.setAttribute("sms",sms);
			ArrayList<POAMasterVo> list =new ArrayList<POAMasterVo>();
			list.add(poaMastervo);
			logger.info("In openEditPOA list"+ list.size());
			
			request.setAttribute("editVal", "editVal");
			request.setAttribute("list", list);
			request.setAttribute("status", poaMastervo.getRecStatus());
			
		}
       // countryMastervo.setCountryId(request.getParameter("CountryId"));
		logger.info("In updateCountrytDetails---status-----"+poaMastervo.getRecStatus());
		logger.info("in updateCountryDetails ------description-------"+poaMastervo.getPoaDesc());
		        
        return mapping.getInputForward();
      
		
	}
	
}