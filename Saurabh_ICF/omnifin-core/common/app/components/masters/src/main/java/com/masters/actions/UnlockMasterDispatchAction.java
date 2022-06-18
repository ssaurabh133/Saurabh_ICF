//Author Name : Kanika Maheshwari-->
//Date of Creation : 26/12/2011-->
//Purpose  : Dispatch Action For Unlock master-->
//Documentation : -->

package com.masters.actions;



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
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.dao.MasterDAO;


import com.masters.vo.UnlockUserVo;



public class UnlockMasterDispatchAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(UnlockMasterDispatchAction.class.getName());


	public ActionForward updateUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("In UnlockMasterDispatchAction updateUser().......");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
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
		UnlockUserVo vo=new UnlockUserVo(); 
		DynaValidatorForm AgencyMasterAddDynaValidatorForm= (DynaValidatorForm)form;
		  
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, AgencyMasterAddDynaValidatorForm);	
		String userId="";
		String bDate="";
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
				
		}
		vo.setMakerId(userId);
		vo.setMakerDate(bDate);
		String[] users=request.getParameter("hidCheck").split("/");
		
		
			 
			for(int i=0;i<users.length;i++){
		    logger.info("user   list.:====------"+users[i]);
			 }

			MasterDAO masterDAO=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
			logger.info("Implementation class: "+masterDAO.getClass());
			//MasterDAO masterDAO = new MasterDAOImpl();
        boolean status=masterDAO.updateUnlockuser(vo,users);
        String sms="";
        if(status){
			sms="M";
			request.setAttribute("sms",sms);
			 
		
		}
		else{
			sms="E";
			
			
			request.setAttribute("sms",sms);
			//request.setAttribute("editVal", "editVal");
		}
       
        return mapping.findForward("success");
       
		
	}

}