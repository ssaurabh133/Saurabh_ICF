package com.cm.actions;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.cm.vo.AssetForCMVO;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class AssetAuthorBehindAction extends Action{
	private static final Logger logger = Logger.getLogger(AssetAuthorBehindAction.class.getName());
	public ActionForward execute(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		HttpSession session=request.getSession();
	
		//boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here execute method of AssetAuthorBehindAction  action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		session.removeAttribute("pParentGroup");
		session.removeAttribute("assetID");
		session.removeAttribute("lbxAssetID");
		session.removeAttribute("datatoapproveList");
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
		AssetForCMVO assetMakervo = new AssetForCMVO(); 

		logger.info("In AssetAuthorBehindAction  ");
		request.setAttribute("fromAuthor", "fromAuthor");
		 return mapping.findForward("success");
	}

}
