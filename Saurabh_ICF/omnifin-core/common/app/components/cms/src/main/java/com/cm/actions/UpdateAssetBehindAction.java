package com.cm.actions;


import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

import com.cm.dao.LinkLoanDAO;
import com.cm.vo.UpdateAssetVO;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class UpdateAssetBehindAction extends Action {
	private static final Logger logger=Logger.getLogger(UpdateAssetBehindAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping,ActionForm form,
								HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		logger.info("in execute method of UpdateAssetBehindAction:::::::::::::::::");
		
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId =null;
		String branchId =null;
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
			
		}else{
			logger.info("here in execute method of UpdateAssetBehindAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
			
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

	
		
		int currentPageLink = 0;
		if(request.getParameter("d-49520-p")==null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
		{
			currentPageLink=1;
		}
		else
		{
			currentPageLink =Integer.parseInt(request.getParameter("d-49520-p"));
		}
		
	
		
		UpdateAssetVO updateAssetVO=new UpdateAssetVO();

		DynaValidatorForm updateVehicleDynaValidatorForm = (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(updateAssetVO, updateVehicleDynaValidatorForm);		
		updateAssetVO.setCurrentPageLink(currentPageLink);
		updateAssetVO.setUserId(userId);
		updateAssetVO.setBranchId(branchId);
		LinkLoanDAO dao=(LinkLoanDAO)DaoImplInstanceFactory.getDaoImplInstance(LinkLoanDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());
		
		ArrayList<UpdateAssetVO> searchList= dao.getLoanDetails(updateAssetVO);		
		
		if((searchList.size())> 0)
		{
			request.setAttribute("list", searchList);
		}
		else{
			request.setAttribute("list", "");
		}
		session.setAttribute("userId",userId);// for lov
		session.setAttribute("branchId", branchId);// for lov
		form.reset(mapping, request);
		dao=null;
		updateAssetVO=null;
	return mapping.findForward("success");
	}
	

}
