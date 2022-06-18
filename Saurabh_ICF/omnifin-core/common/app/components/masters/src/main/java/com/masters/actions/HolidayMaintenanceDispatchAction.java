/* This class  is the Dispatch Action class for the Holiday Master
 * @author Vishal Singh
 * @Date  31 March 2012
 * 
 */
package com.masters.actions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

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
import com.masters.vo.HolidayMasterVo;

public class HolidayMaintenanceDispatchAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(HolidayMaintenanceDispatchAction.class.getName());
	 public ActionForward openAddHoliday(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception
			{
				
				
				logger.info("******** Inside openAddHoliday mehtod of HolidayMaintenanceDispatchAction *****");
				ServletContext context = getServlet().getServletContext();
				HttpSession session = request.getSession();
			
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
				
				//for check User session end
			
			    return mapping.findForward("openAdd");	
			}
	 

		public ActionForward saveHolidayDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse resopnse) throws Exception{
			ServletContext context = getServlet().getServletContext();
			logger.info("******** Inside saveHolidayDetails mehtod of HolidayMaintenanceDispatchAction *****");			
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
			
			//for check User session end			
			String userId="";
			String bDate="";
			if(userobj!=null)
			{
					userId=userobj.getUserId();
					bDate=userobj.getBusinessdate();
			}
	
		DynaValidatorForm CommonDynaValidatorForm= (DynaValidatorForm)form;
		HolidayMasterVo vo = new HolidayMasterVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, CommonDynaValidatorForm);
		
		CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);

		vo.setMakerId(userId);
		vo.setMakerDate(bDate);
				
		String sms="";
		
		boolean status=false;		
				
		/** for Weekly Holiday date Add */
		if(!CommonFunction.checkNull(vo.getHolidayType())
				.equalsIgnoreCase("") &&  !vo.getHolidayType().equals("") && vo.getHolidayType().equals("Weekly")){

		String []week=request.getParameterValues("week");		
		 ArrayList weeklyList=new ArrayList();
		 ArrayList mainList=new ArrayList();
		 HolidayWeekendFinder hwf;
		  ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
		  String dateFormat = resource.getString("lbl.dateForDisbursal");
		  SimpleDateFormat sdf = new SimpleDateFormat(dateFormat); 
		
		  if(week!=null){
		for(int i=0;i<week.length;i++){
		
		if(!CommonFunction.checkNull(vo.getHolidayDay())
				.equalsIgnoreCase("") && !vo.getHolidayDay().equals("") && vo.getHolidayDay().equals("SAT")){
			hwf=new HolidayWeekendFinder(bDate);
		weeklyList =hwf.findWeekendsSaturday(Integer.valueOf(week[i]));
		mainList.add(weeklyList);
		}else if(!CommonFunction.checkNull(vo.getHolidayDay())
				.equalsIgnoreCase("") && !vo.getHolidayDay().equals("") && vo.getHolidayDay().equals("SUN")){
			hwf=new HolidayWeekendFinder(bDate);
			weeklyList =hwf.findWeekendsSunday(Integer.valueOf(week[i]));
			mainList.add(weeklyList);
		}		
	  }
    }	
		if(mainList!=null){
		for(int j=0;j<mainList.size();j++){
		for(int i=0;i<((ArrayList) mainList.get(j)).size();i++){
			if(vo.getHolidayDay().equals("SAT")){
				vo.setHolidayDes("Saturday");
			}
             if(vo.getHolidayDay().equals("SUN")){
            	 vo.setHolidayDes("Sunday");	
			}
			vo.setHolidayDate(sdf.format((Date)((ArrayList) mainList.get(j)).get(i)));			
			status = bp.insertHolidayMaster(vo);			
			
		   }		
		  }	
		 }
		}else if( !CommonFunction.checkNull(vo.getHolidayType())
				.equalsIgnoreCase("") && !vo.getHolidayType().equals("") && vo.getHolidayType().equals("Daily")){	
		 status = bp.insertHolidayMaster(vo);
		}	 		  
		 
		/* end here */
		
		if(status){
			sms="S";
			request.setAttribute("sms",sms);			
		}
		else{
			sms="E";
			request.setAttribute("sms",sms);		
		}		
		//vo.reset(mapping,request);
		logger.info("status"+status);
		
		request.setAttribute("holidayType",request.getParameter("holidayType")!=null?request.getParameter("holidayType"):"");
		
		return mapping.getInputForward();
		
		}
	
	
	
		public ActionForward openEditHoliday(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)	throws Exception {
			ServletContext context = getServlet().getServletContext();
							HolidayMasterVo holidayMasterVo=new HolidayMasterVo(); 
							logger.info("******** Inside openEditHoliday mehtod of HolidayMaintenanceDispatchAction *****");	
							
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
							
							//for check User session end
							
							holidayMasterVo.setHolidayId(request.getParameter("holidayId"));							
							
							CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
							
							ArrayList<HolidayMasterVo> list = bp.searchHolidayData(holidayMasterVo);							
							request.setAttribute("list", list);					
							holidayMasterVo=list.get(0);
							HolidayMasterVo docVo=new HolidayMasterVo();
							docVo=list.get(0);													
							request.setAttribute("status", holidayMasterVo.getHolidayStatus());							
						   return mapping.findForward("editHoliday");	
						}
				
				
				public ActionForward updateHoliday(ActionMapping mapping, ActionForm form,
						HttpServletRequest request, HttpServletResponse response) throws Exception {
					ServletContext context = getServlet().getServletContext();
					logger.info("******** Inside updateHoliday mehtod of HolidayMaintenanceDispatchAction *****");
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
					
					//for check User session end
					String userId="";
					String bDate="";
					if(userobj!=null)
					{
							userId=userobj.getUserId();
							bDate=userobj.getBusinessdate();
					}
					
					HolidayMasterVo holidayMasterVo=new HolidayMasterVo(); 
					DynaValidatorForm CommonDynaValidatorForm= (DynaValidatorForm)form;
					org.apache.commons.beanutils.BeanUtils.copyProperties(holidayMasterVo, CommonDynaValidatorForm);	
					
					CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
					
					holidayMasterVo.setMakerId(userId);
					holidayMasterVo.setMakerDate(bDate);
			        boolean status=bp.updateHolidyData(holidayMasterVo);
			        String sms="";
			        if(status){
						sms="M";
						request.setAttribute("sms",sms);
						request.setAttribute("editVal", "editVal");
					}
					else{
						sms="E";
						request.setAttribute("sms",sms);
						ArrayList<HolidayMasterVo> list =new ArrayList<HolidayMasterVo>();
						list.add(holidayMasterVo);
											
						request.setAttribute("editVal", "editVal");
						request.setAttribute("list", list);
						request.setAttribute("status", holidayMasterVo.getHolidayStatus());
						
					}
			       					        
			        return mapping.getInputForward();
			      
					
				}
				
}