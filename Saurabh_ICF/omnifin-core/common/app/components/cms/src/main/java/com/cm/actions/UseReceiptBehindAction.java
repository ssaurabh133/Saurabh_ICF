//AUTHOR NAME:  Mradul Agarwal
//CREATION DATE:17-Jan-2013 


package com.cm.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.login.roleManager.UserObject;

public class UseReceiptBehindAction extends Action
{
	Logger logger = Logger.getLogger(UseReceiptBehindAction.class.getName());
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception 
	{
		logger.info(" In UseReceiptBehindAction execute");
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		
		if(userobj!=null)
		{
				userId=userobj.getUserId();				
		}
		String userNameQuery="SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID='"+CommonFunction.checkNull(userId)+"'";
		String userName=ConnectionDAO.singleReturn(userNameQuery);
		request.setAttribute("userName", userName);
		userName=null;
		userNameQuery=null;
		userId=null;
		userobj=null;
		session=null;
		form.reset(mapping, request);
		return mapping.findForward("Success");
    }
}
		