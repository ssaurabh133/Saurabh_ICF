package com.cp.actions;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.connect.DaoImplInstanceFactory;
import com.cp.dao.DeviationApprovalDAO;
import com.cp.dao.SalesDashBoardDAO;
import com.cp.vo.SalesDashBoardVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.webservice.cp.pdvo.UserHierarchyRequest;
import com.webservice.cp.pdvo.UserHierarchyResponse;
import com.webservice.cp.pdvo.UserCredentials;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.connect.CommonFunction;
import com.connect.OmniFinRestClient;

public class SalesDashBoardAction extends DispatchAction
{
	private static final String String = null;
	final Logger logger = Logger.getLogger(SalesDashBoardAction.class.getName());
	
	public void fetchSalesDashboardData(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception{
		logger.info("In fetchSalesDashboardData. ");
		StringBuilder jsonStr = new StringBuilder();
		HttpSession session = request.getSession();
		boolean dataStatus=false;
		//MANAGE SELECETED BRANCH
		String userId=request.getParameter("userId");
		String queue=request.getParameter("queue");
		String source=request.getParameter("source");
		String selectedBranchIds=request.getParameter("selectedBranchIds");
		String selectedUserIds=request.getParameter("selectedUserIds");
		SalesDashBoardDAO dao=(SalesDashBoardDAO)DaoImplInstanceFactory.getDaoImplInstance(SalesDashBoardDAO.IDENTITY);
		if(StringUtils.isNotBlank(selectedUserIds)){
			dataStatus=dao.manageSelectedUser(userId,source,selectedUserIds);
			session.setAttribute("seleteUserHierarchy", "Y");
		}else{
			String seleteUserHierarchy=(String)session.getAttribute("seleteUserHierarchy");
			if(StringUtils.equalsIgnoreCase(seleteUserHierarchy, "Y")){
				UserHierarchyResponse userHierarchyResponse=(UserHierarchyResponse)session.getAttribute("userHierarchyResponse");
				if(userHierarchyResponse!=null){
					String userList=userHierarchyResponse.getUserList();
					dataStatus=dao.manageSelectedUser(userId,source,userList);
				}
			}else{
				dataStatus=true;
			}
		}
		String showAll="N";
		if(StringUtils.equalsIgnoreCase(queue, "R") && StringUtils.isBlank(selectedBranchIds) && StringUtils.isBlank(selectedUserIds)){
			showAll="Y";
		}
		Map<String,String> salesData=null;
		if(dataStatus){
			salesData=dao.fetchSalesDashboardData(userId,source,showAll);
		}
		if(salesData!=null){
			jsonStr.append("{");
			jsonStr.append("\"OPERATION_STATUS\":\"1\"");
			jsonStr.append(",\"OPERATION_MESSAGE\":\"Done\"");
			jsonStr.append(",\"LD_PD_SB\":" +salesData.get("LD_PD_SB"));
			jsonStr.append(",\"PRE_LGN_DL\":" +salesData.get("PRE_LGN_DL"));
			jsonStr.append(",\"PRE_LGM_QR_DL\":" +salesData.get("PRE_LGM_QR_DL"));
			jsonStr.append(",\"OPEN_DEAL\":" +salesData.get("OPEN_DEAL"));
			jsonStr.append(",\"LD_LGM_MNTH\":" +salesData.get("LD_LGM_MNTH"));
			jsonStr.append(",\"LGD_QRY_DL\":" +salesData.get("LGD_QRY_DL"));
			jsonStr.append(",\"LD_RJCT_CNCLD\":" +salesData.get("LD_RJCT_CNCLD"));
			jsonStr.append(",\"DL_IN_CP\":" +salesData.get("DL_IN_CP"));
			jsonStr.append(",\"LD_SNCD\":" +salesData.get("LD_SNCD"));
			jsonStr.append(",\"DL_SNCD_UN_DSBSD\":" +salesData.get("DL_SNCD_UN_DSBSD"));
			jsonStr.append(",\"LD_DSBSD\":" +salesData.get("LD_DSBSD"));
			jsonStr.append("}");
		}else{
			jsonStr.append("{");
			jsonStr.append("\"OPERATION_STATUS\":\"0\"");
			jsonStr.append(",\"OPERATION_MESSAGE\":\"Some Error Occurred. Please contact to Admin...\"");
			jsonStr.append("}");
		}
		response.getWriter().write(jsonStr.toString());
	}

	public void refreshSelectedBranch(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception{
		logger.info("In refreshSelectedBranch. ");
		StringBuilder jsonStr = new StringBuilder();
		//MANAGE SELECETED BRANCH
		String userId=request.getParameter("userId");
		String source=request.getParameter("source");
		String selectedBranchIds=request.getParameter("selectedBranchIds");
		SalesDashBoardDAO dao=(SalesDashBoardDAO)DaoImplInstanceFactory.getDaoImplInstance(SalesDashBoardDAO.IDENTITY);
		boolean branchStatus=dao.manageSelectedBranch(userId,source,selectedBranchIds);
		//FIND FORM NUMBER
		Map<String,String> webserviceCredential=CommonFunction.getOmniFinServiceCredential(); 
		String serviceUrl=webserviceCredential.get("SERVICE_URL");
		//Creating request
		UserHierarchyResponse userHierarchyResponse=null;
		String userList="";
		UserHierarchyRequest userHierarchyRequest=new UserHierarchyRequest();
		UserCredentials userCredentials=new UserCredentials();
		userCredentials.setUserId(webserviceCredential.get("SERVICE_USER_ID"));
		userCredentials.setUserPassword(webserviceCredential.get("SERVICE_USER_PASSWORD"));
		userCredentials.setSource(source);
		userCredentials.setUserId(userId);
		userCredentials.setSourceId(userId);
		userCredentials.setInitiatedBy(userId);
		userHierarchyRequest.setUserCredentials(userCredentials);
		userHierarchyRequest.setUserId(userId);
		userHierarchyRequest.setUserName(userId);
		try{
			userHierarchyResponse=OmniFinRestClient.fetchUserHierarchy(serviceUrl,userHierarchyRequest);
		}catch(Exception e){
			e.printStackTrace();
		}
		logger.info("callUserHierarchyWebservice Response: "+userHierarchyResponse);
		JsonConfig jsonConfig = new JsonConfig();
		if(userHierarchyResponse!=null){
			HttpSession session = request.getSession();
			session.setAttribute("userHierarchyResponse", userHierarchyResponse);
			//
			userList=userHierarchyResponse.getUserList();
			boolean userStatus=dao.manageSelectedUser(userId,source,userList);
			List<UserHierarchyResponse>userHierarchyList=new ArrayList<UserHierarchyResponse>();
			userHierarchyList.add(userHierarchyResponse);
			JSONArray jsonObject = JSONArray.fromObject(userHierarchyList, jsonConfig);
			jsonStr.append("{");
			jsonStr.append("\"OPERATION_STATUS\":\"1\"");
			jsonStr.append(",\"OPERATION_MESSAGE\":\"Done\"");
			jsonStr.append(",\"USER_HIERARCHY\":" +jsonObject.toString());
			jsonStr.append("}");
		}else{
			jsonStr.append("{");
			jsonStr.append("\"OPERATION_STATUS\":\"0\"");
			jsonStr.append(",\"OPERATION_MESSAGE\":\"Some Error Occurred. Please contact to Admin...\"");
			jsonStr.append("}");
		}
		response.getWriter().write(jsonStr.toString());
	}
	
	public void callUserHierarchyWebservice(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception{
		logger.info("In callUserHierarchyWebservice. ");
		StringBuilder jsonStr = new StringBuilder();
		String userId=request.getParameter("userId");
		String source=request.getParameter("source");
		SalesDashBoardDAO dao=(SalesDashBoardDAO)DaoImplInstanceFactory.getDaoImplInstance(SalesDashBoardDAO.IDENTITY);
		//FIND FORM NUMBER
		Map<String,String> webserviceCredential=CommonFunction.getOmniFinServiceCredential(); 
		String serviceUrl=webserviceCredential.get("SERVICE_URL");
		//Creating request
		UserHierarchyResponse userHierarchyResponse=null;
		UserHierarchyRequest userHierarchyRequest=new UserHierarchyRequest();
		UserCredentials userCredentials=new UserCredentials();
		userCredentials.setUserId(webserviceCredential.get("SERVICE_USER_ID"));
		userCredentials.setUserPassword(webserviceCredential.get("SERVICE_USER_PASSWORD"));
		userCredentials.setSource(source);
		userCredentials.setUserId(userId);
		userCredentials.setSourceId(userId);
		userCredentials.setInitiatedBy(userId);
		userHierarchyRequest.setUserCredentials(userCredentials);
		userHierarchyRequest.setUserId(userId);
		userHierarchyRequest.setUserName(userId);
		try{
			userHierarchyResponse=OmniFinRestClient.fetchUserHierarchy(serviceUrl,userHierarchyRequest);
		}catch(Exception e){
			e.printStackTrace();
		}
		logger.info("callUserHierarchyWebservice Response: "+userHierarchyResponse);
		JsonConfig jsonConfig = new JsonConfig();
		if(userHierarchyResponse!=null){
			HttpSession session = request.getSession();
			session.setAttribute("userHierarchyResponse", userHierarchyResponse);
			//
			String userList=userHierarchyResponse.getUserList();
			boolean userStatus=dao.manageSelectedUser(userId,source,userList);
			List<UserHierarchyResponse>userHierarchyList=new ArrayList<UserHierarchyResponse>();
			userHierarchyList.add(userHierarchyResponse);
			JSONArray jsonObject = JSONArray.fromObject(userHierarchyList, jsonConfig);
			jsonStr.append("{");
			jsonStr.append("\"OPERATION_STATUS\":\"1\"");
			jsonStr.append(",\"OPERATION_MESSAGE\":\"Done\"");
			jsonStr.append(",\"USER_HIERARCHY\":" +jsonObject.toString());
			jsonStr.append("}");
		}else{
			jsonStr.append("{");
			jsonStr.append("\"OPERATION_STATUS\":\"0\"");
			jsonStr.append(",\"OPERATION_MESSAGE\":\"Some Error Occurred. Please contact to Admin...\"");
			jsonStr.append("}");
		}
		response.getWriter().write(jsonStr.toString());
	}
	public ActionForward showDefaultView(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception {
		String state="success";
		String userId="";
		String loginUserId="";
		String source=request.getParameter("source");
		String queue=request.getParameter("queue");
		if(StringUtils.isBlank(queue)){
			queue="";
		}
		queue=queue.trim();
		SalesDashBoardDAO dao=(SalesDashBoardDAO)DaoImplInstanceFactory.getDaoImplInstance(SalesDashBoardDAO.IDENTITY);
		if(StringUtils.isBlank(queue)){
			if(StringUtils.equalsIgnoreCase("WEB", source))
			{
				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				String branchId="";
				if(userobj!=null)
				{
					userId=userobj.getUserId();
					branchId=userobj.getBranchId();
					loginUserId=userobj.getUserId();
				}else{
					logger.info("here in execute method of showDefaultView action the session is out----------------");
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
				state="success";
			}else{
				userId=request.getParameter("userId");
				loginUserId=request.getParameter("userId");
				String userPassword=request.getParameter("userPassword");
				boolean status=dao.isUserExist(userId,userPassword);
				if(status)
				{
					state="success";
				}
				else
				{
					state="error";
				}
			}
			queue="O";
			String rootUserId=dao.getDashBoardRootUserId();//rootUserId
			if(StringUtils.equalsIgnoreCase(rootUserId, userId)){
				queue="R";
			}
		}else{
			loginUserId=request.getParameter("loginUserId");
			if(StringUtils.equalsIgnoreCase(queue,"R")){
				userId=dao.getDashBoardRootUserId();//rootUserId
			}
			if(StringUtils.equalsIgnoreCase(queue,"O")){
				userId=loginUserId;
			}
		}
		
		boolean switchAccess=dao.checkSwitchAccess(userId);
		if(switchAccess){
			request.setAttribute("switchAccess", "");
		}
		
		String selectedBranchIds="";
		String selectedUserIds="";
		request.setAttribute("loginUserId", loginUserId);
		request.setAttribute("userId", userId);
		request.setAttribute("source", source);
		request.setAttribute("queue", queue);
		List<SalesDashBoardVo>branchList=dao.getBranchList(userId,source,queue);
		request.setAttribute("branchList", branchList);
		//boolean dataStatus=dao.createDefaultDashboardData(userId,source,selectedUserIds,selectedBranchIds,"Y");
		return mapping.findForward(state);
	}
	public ActionForward salesDashBoardItemDtl(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception{
		String source=request.getParameter("source");
		String userId=request.getParameter("userId");
		String showAll=request.getParameter("showAll");
		SalesDashBoardDAO dao=(SalesDashBoardDAO)DaoImplInstanceFactory.getDaoImplInstance(SalesDashBoardDAO.IDENTITY);
		String dashBoard=request.getParameter("dashBoard");
		List<SalesDashBoardVo> dashBoardDetail =dao.salesDashBoardItemDtl(dashBoard,source,userId,showAll);
		request.setAttribute("dashBoardDetail", dashBoardDetail);
		request.setAttribute("dashBoard", dashBoard);
		return mapping.findForward("dashBoardDtl");
	}
	public ActionForward openDashboardQueryDtl(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception
	{
		SalesDashBoardDAO dao=(SalesDashBoardDAO)DaoImplInstanceFactory.getDaoImplInstance(SalesDashBoardDAO.IDENTITY);
		String dealId=request.getParameter("dealId");
		ArrayList wipQueryDtl=dao.openDashboardQueryDtl(dealId);
		request.setAttribute("wipQueryDtl", wipQueryDtl);
		return mapping.findForward("dashBoardDtl");
	}
}
