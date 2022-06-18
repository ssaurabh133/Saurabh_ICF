package com.masters.actions;

import java.util.ArrayList;

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
import com.masters.vo.UserProductAccessVo;

public class UserProductAccessDispatchAction extends DispatchAction {
	
static final Logger logger = Logger.getLogger(UserProductAccessDispatchAction.class.getName());
	
	public ActionForward openAddUserProduct(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ServletContext context = getServlet().getServletContext();
		logger.info("## In openAddUserProduct() :");		
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
	
			logger.info("## In openAddUserProduct() : strFlag ==>>"+strFlag);
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
			
			
			request.setAttribute("save", "save");
			request.setAttribute("refresh", "refresh");
			
			return mapping.findForward("openAddJSP");
	}
	
	public ActionForward saveUserProduct(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		//HttpSession session=request.getSession(false);
		
		logger.info("In saveUserProduct() : ");
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String sessionId = session.getAttribute("sessionID").toString();
		boolean status = false;
		
		
		ServletContext context = getServlet().getServletContext();
		boolean flag=false;
		
		//for check User session start
		String strFlag="";	
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
		
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
		String userId="";
		String makerDate="";
		 
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				makerDate=userobj.getBusinessdate();
				
		}
	
		DynaValidatorForm upadvf= (DynaValidatorForm)form;
		UserProductAccessVo upavo = new UserProductAccessVo();
		
		org.apache.commons.beanutils.BeanUtils.copyProperties(upavo, upadvf);
		
		CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		logger.info("getShowProductDesc()"+upavo.getShowProductDesc());
		logger.info("getShowProductDesc()"+upavo.getShowProductDesc());
		logger.info("getShowProductDesc()"+upavo.getShowProductDesc());
		logger.info("getShowProductDesc()"+upavo.getShowProductDesc());
		String[] productId = upavo.getLbxProductId().split("[|]");
		logger.info("## In saveUserProduct() : product ==>>"+upavo.getLbxProductId());
		for(int i=0;i<productId.length;i++)
		{
			logger.info("## productId[]"+productId[i]);
		}
		upavo.setMakerId(userId);
		upavo.setMakerDate(makerDate);
		upavo.setAuthorId(userId);
		upavo.setAuthorDate(makerDate);
		
		String sms="";
		
			status = cpm.saveUserProductAccess(upavo,productId);
			
		
			
		if(status){
			
			if(CommonFunction.checkNull(request.getParameter("modifyRecord")).equalsIgnoreCase("M"))
			{
				sms="M";
				request.setAttribute("sms",sms);
			}
			else
			{
				sms="S";
				request.setAttribute("sms",sms);
			}
			//request.setAttribute("save", "save");
		}
		else 
		{
			sms="N";
			request.setAttribute("sms",sms);
			logger.info("getShowProductDesc()"+upavo.getShowProductDesc());
			logger.info("etLbxProductId(()"+upavo.getLbxProductId());
		
			request.setAttribute("upavo", upavo);
		}
		return mapping.findForward("openAddJSP");
	}
	
	
	public ActionForward updateUserProductAccess(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		logger.info("## In updateMakeModelMaster() : ");		
		ServletContext context = getServlet().getServletContext();
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
		String strFlag="";	
		if(sessionId!=null && userobj != null)
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
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
		String userId=request.getParameter("lbxUserId");
		
		CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		ArrayList<UserProductAccessVo> list= cpm.getRecordForUpdate(userId);
		ArrayList<UserProductAccessVo> productList= cpm.getProductRecordForUpdate(userId);
		logger.info("## In updateUserProductAccess() : userId ==>> "+list.get(0).getRecStatus());
		logger.info("## In updateUserProductAccess() : productList ==>>"+productList);
		logger.info("## In updateUserProductAccess() : productList.size() ==>>"+productList.size());

		String s1[]= new String [productList.size()];
		
		for(int i=0;i<productList.size();i++)
		{
			 s1[i]=productList.get(i).getLbxProductId();
			 logger.info("s1[i]"+s1[i]);
		}
		String productId=s1[0];
		logger.info("productId=="+productId);
		if(s1.length>1)
		{
				
			for(int i=1;i<s1.length;i++)
			{
				productId=productId+"|";
				productId=productId+s1[i];
				
			}
		}
		list.get(0).setLbxProductId(productId);
		logger.info("## In updateUserProductAccess() : productId ==>>"+productId);
		if(list.get(0).getRadioSelection().equalsIgnoreCase("S"))
		{
			logger.info("In updateUserProductAccess : inside if condition for radio ");
			request.setAttribute("selectedProduct", "selectedProduct");
		}
		request.setAttribute("productList", productList);
		request.setAttribute("status",list.get(0).getRecStatus());
		request.setAttribute("editVal", "editVal");
		request.setAttribute("list",list);
		return mapping.findForward("jspForUpdate");
	}
	
	public ActionForward searchUserProduct(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		logger.info("## In searchUserProduct() :");		
		ServletContext context = getServlet().getServletContext();
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
		String strFlag="";	
		if(sessionId!=null && userobj != null)
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		logger.info("## In searchUserProduct() : strFlag ==>> "+strFlag);
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
		logger.info("current page link .......... "+request.getParameter("d-49520-p"));			
		int currentPageLink = 0;
		if(request.getParameter("d-49520-p")==null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
			currentPageLink=1;
		else
			currentPageLink =Integer.parseInt(request.getParameter("d-49520-p"));
		logger.info("## In searchUserProduct() : current page link ==>>"+currentPageLink);
		UserProductAccessVo vo=new UserProductAccessVo();
		DynaValidatorForm formbeen= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, formbeen);
		
		CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		vo.setCurrentPageLink(currentPageLink);
		ArrayList list= cpm.searchUserProductRecord(vo);
		logger.info("## In searchUserProduct : list ==>>"+list);
		request.setAttribute("list",list);
		request.setAttribute("search","search");
		return mapping.findForward("searchedRecord");
	}

}
