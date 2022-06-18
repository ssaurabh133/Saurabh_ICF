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
import com.cm.vo.AssetVerificationVO;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class AssetVerificationRepCapBehindAction extends Action {
	private static final Logger logger = Logger.getLogger(AssetVerificationRepCapBehindAction.class.getName());
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
			logger.info(" In the AssetVerificationRepCapBehindAction----------");
			
			HttpSession session =  request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			String makerID ="";
			String bDate ="";
			if(userobj!=null){
				makerID = userobj.getUserId();
				bDate=userobj.getBusinessdate();
			}else{
				logger.info("here in execute method of AssetVerificationRepCapBehindActionaction the session is out----------------");
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
			//UserObject userObj=(UserObject)session.getAttribute("userobject");
	
			
			AssetVerificationVO  assetVerificationVO = new AssetVerificationVO();	
			assetVerificationVO.setMakerID(makerID);
			
			logger.info("makerID...."+makerID);
			assetVerificationVO.setMakerDate(bDate);
			
			String assetVerificationID = request.getParameter("assetVerificationID");
            logger.info("assetVerificationID"+assetVerificationID);
 			
            request.setAttribute("makerID", makerID);
 			session.removeAttribute("list");
 			//session.removeAttribute("tab");
 			
//			PaginationUtill pageutill=new PaginationUtill();
//			CreditManagementDAO dao = new CreditManagementDAOImpl();
//			
//			 int maxEntriesPerPage = 5;
//			
//
//		        int page = 1;
//		        try {
//		            page = Integer.parseInt(request.getParameter("page"));
//		        } catch (NumberFormatException e) {
//		        }
//		        int offset = maxEntriesPerPage * (page - 1);
//		        pageutill.setOffset(offset);
//		        pageutill.setLength(maxEntriesPerPage);
//		        pageutill.getPaginationListForAssetVerification();
//		        
//		        request.setAttribute("list", pageutill.getListByOffsetAndLength());
//		        request.setAttribute("pageDetails", pageutill.getPages());
//			
////			ArrayList<AssetVerificationVO> assetDescriptionList = dao.getAssetList();
////			request.setAttribute("assetDescriptionList", assetDescriptionList);
			   
			return mapping.findForward("success");
		}

}
