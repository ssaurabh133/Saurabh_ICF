package com.cp.actions;

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

import com.communication.engn.daoImplMySql.SmsDAOImpl;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.DeviationApprovalDAO;
import com.cp.vo.CommonDealVo;
import com.cp.vo.DeviationApprovalVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class manualDeviationMakerAction extends DispatchAction{

private static final Logger logger = Logger.getLogger(manualDeviationMakerAction.class.getName());
	
	public ActionForward manualDeviationM(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws Exception {
	
		logger.info("In manualDeviationM  ");
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String branchId="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}else{
			logger.info("here in execute method of manualDeviationM action the session is out----------------");
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
		
		DeviationApprovalVo vo = new DeviationApprovalVo();
		DynaValidatorForm CommonDealDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, CommonDealDynaValidatorForm);
		/* changed by asesh */
		DeviationApprovalDAO dao=(DeviationApprovalDAO)DaoImplInstanceFactory.getDaoImplInstance(DeviationApprovalDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass());
		/* End asesh */
        	String dealId = "";				
			dealId = CommonFunction.checkNull(request.getParameter("dealId"));
			logger.info("dealId .............. "+dealId);
			if(dealId!=null && !dealId.equalsIgnoreCase(""))
			{
				ArrayList dealHeader = dao.getDealHeader(dealId);
				session.setAttribute("dealHeader", dealHeader);
			
				 ArrayList manualDevList = dao.manualDevList(dealId);
				
					if(manualDevList.size()>0)
					{ 
						String checkDeviationQuery="Select Count(MANUAL_DEVIATION_ID) from cr_manual_deviation_dtl where deal_id='"+dealId+"'";
						logger.info("checkDeviationQuery: "+checkDeviationQuery);
						String checkDeviation=ConnectionDAO.singleReturn(checkDeviationQuery);
						checkDeviationQuery=null;
						if(!CommonFunction.checkNull(checkDeviation).equalsIgnoreCase("0") && !CommonFunction.checkNull(checkDeviation).equalsIgnoreCase(""))
						{
							 request.setAttribute("waveoffStage", "waveoffStage");
						}
						checkDeviation=null;
						logger.info("manualDevList....list: "+manualDevList.size());
					    request.setAttribute("manualDevList", manualDevList);
					}
			}
					else
					{
						request.setAttribute("sms","N");
					}
			
		session.removeAttribute("pParentGroup");
	
		return mapping.findForward("deviationMaker");	
	
	}
	public ActionForward saveManualDeviationMaker(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ServletContext context = getServlet().getServletContext();
		logger.info("In manualDeviationM..saveManualDeviationMaker()...");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
		/* changed by asesh */
		DeviationApprovalDAO dao=(DeviationApprovalDAO)DaoImplInstanceFactory.getDaoImplInstance(DeviationApprovalDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass());
		/* End asesh */
		//for check User session start
		String userId="";
		String bDate="";
		String companyId="";
		if(userobj!=null)
		{
			userId	=userobj.getUserId();
			bDate=userobj.getBusinessdate();
			companyId=""+userobj.getCompanyId();
		}else{
			logger.info("here in saveDeviationApproval method of DeviationApprovalAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
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

		    DeviationApprovalVo vo = new DeviationApprovalVo();		
		    DynaValidatorForm CommonDynaValidatorForm= (DynaValidatorForm)form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(vo, CommonDynaValidatorForm);				
							
				
			vo.setMakerId(userobj.getUserId());
			vo.setMakerDate(userobj.getBusinessdate());		
			String chkValue=CommonFunction.checkNull(request.getParameter("chkValue"));
			String chk[]=null;
			ArrayList<String> manualId=new ArrayList<String>();
			ArrayList<String> remarks=new ArrayList<String>();
			ArrayList<String> approvalLevel=new ArrayList<String>();
			String sms="";
			if(!chkValue.equalsIgnoreCase("")){
				chk=chkValue.split(",");
			}
			int length =chk.length;
			String dealId=CommonFunction.checkNull(request.getParameter("dealId"));
			vo.setDealId(dealId);
			logger.info("dealId======"+dealId);
			for(int i=0;i<length;i++){
				manualId.add(CommonFunction.checkNull(request.getParameter("manualId"+chk[i])));
				remarks.add(CommonFunction.checkNull(request.getParameter("remarks"+chk[i])));
				approvalLevel.add(CommonFunction.checkNull(request.getParameter("approvalLevel"+chk[i])));
				logger.info("approvalLevel======"+(CommonFunction.checkNull(request.getParameter("approvalLevel"+chk[i]))));
			}
			
			 String status=dao.saveManualDeviation(vo, manualId, remarks,approvalLevel);	
			 
				if(dealId!=null && !dealId.equalsIgnoreCase(""))
				{
					ArrayList dealHeader = dao.getDealHeader(dealId);
					session.setAttribute("dealHeader", dealHeader);
				}
			 ArrayList manualDevList = dao.manualDevList(dealId);
				request.setAttribute("manualDevList", manualDevList);
			
				String checkDeviationQuery="Select Count(MANUAL_DEVIATION_ID) from cr_manual_deviation_dtl where deal_id='"+dealId+"'";
				logger.info("checkDeviationQuery: "+checkDeviationQuery);
				String checkDeviation=ConnectionDAO.singleReturn(checkDeviationQuery);
				checkDeviationQuery=null;
				if(!CommonFunction.checkNull(checkDeviation).equalsIgnoreCase("0") && !CommonFunction.checkNull(checkDeviation).equalsIgnoreCase(""))
				{
					 request.setAttribute("waveoffStage", "waveoffStage");
				}
				checkDeviation=null;
			
			if(status.equalsIgnoreCase("Saved")){
	 			 
				 sms="S";
			 			 }
				 else if(status.equalsIgnoreCase("notSaved")){
				 
					 sms="X";
			 } 
			 request.setAttribute("sms",sms );
			
		
	        return mapping.findForward("saveManualDev");
		}
       
	public ActionForward forwardManualDeviationMaker(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ServletContext context = getServlet().getServletContext();
		logger.info("In manualDeviationM..forwardManualDeviationMaker()...");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
		/* changed by asesh */
		DeviationApprovalDAO dao=(DeviationApprovalDAO)DaoImplInstanceFactory.getDaoImplInstance(DeviationApprovalDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass());
		/* End asesh */
		//for check User session start
		String strFlag="";	
		String userId="";
		String bDate="";
		String companyId="";
		if(userobj!=null)
		{
			userId	=userobj.getUserId();
			bDate=userobj.getBusinessdate();
			companyId=""+userobj.getCompanyId();
		}else{
			logger.info("here in saveDeviationApproval method of DeviationApprovalAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
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
	
		String branchId="";
		
		    DeviationApprovalVo vo = new DeviationApprovalVo();	
		    CommonDealVo Cvo= new CommonDealVo();	
		    if(CommonFunction.checkNull(Cvo.getReportingToUserId()).equalsIgnoreCase(""))
			{ 
		    	Cvo.setReportingToUserId(userId);
			}else{
				logger.info("here in forward method of manualDeviationMakerAction action the session is out----------------");
				return mapping.findForward("sessionOut");
			}
		    //sachin
		    Cvo.setBranchId(userobj.getBranchId());
		    
		    //sachin
		    DynaValidatorForm CommonDynaValidatorForm= (DynaValidatorForm)form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(vo, CommonDynaValidatorForm);				
							
				
			vo.setMakerId(userobj.getUserId());
			vo.setMakerDate(userobj.getBusinessdate());		
			String chkValue=CommonFunction.checkNull(request.getParameter("chkValue"));
			String chk[]=null;
			ArrayList<String> manualId=new ArrayList<String>();
			ArrayList<String> remarks=new ArrayList<String>();
			ArrayList<String> approvalLevel=new ArrayList<String>();
			String sms="";	
			if(!chkValue.equalsIgnoreCase("")){
				chk=chkValue.split(",");
			}
			int length =chk.length;
			String dealId=CommonFunction.checkNull(request.getParameter("dealId"));
			vo.setDealId(dealId);
			logger.info("dealId======"+dealId);
			for(int i=0;i<length;i++){
				manualId.add(CommonFunction.checkNull(request.getParameter("manualId"+chk[i])));
				remarks.add(CommonFunction.checkNull(request.getParameter("remarks"+chk[i])));
				approvalLevel.add(CommonFunction.checkNull(request.getParameter("approvalLevel"+chk[i])));
				logger.info("approvalLevel======"+approvalLevel);
			}
			
			 String status=dao.forwardManualDeviation(vo, manualId, remarks,approvalLevel);	
			 
			 sms = "E";
			 String query1="Select count(1) from comm_event_list_m where event_name='MANUAL_DEVIATION' and REC_STATUS='A' ";
				logger.info("query1_MANUAL_DEVIATION::::"+query1.toString());
				String recStatus1 =ConnectionDAO.singleReturn(query1.toString());
				if(!recStatus1.equalsIgnoreCase("0"))
				{
						String EventName="MANUAL_DEVIATION";
						
							boolean stats=new SmsDAOImpl().getEmailDetails(dealId,bDate,EventName);
							logger.info("Email Send on event MANUAL_DEVIATION::: "+stats);
						
					
				}
				else
				logger.info("Email is not active at event 'MANUAL_DEVIATION' from comm_event_list_m....");
			 
				ArrayList<CommonDealVo> manualDeviationMakerSearch = dao.manualDeviationMakerSearch(Cvo);
				if(manualDeviationMakerSearch.size()>0)
				{
					
					logger.info("manualDeviationMakerSearch....list: "+manualDeviationMakerSearch.size());
				  
				}
				else
				{
					sms="N";
					
				}
				 if(status.equalsIgnoreCase("Forwarded")){
		 			 
					 sms="F";
				 request.setAttribute("list", manualDeviationMakerSearch);
				 			 }
					 else if(status.equalsIgnoreCase("notForwarded")){
					 
						 sms="X";
				 } 
				 request.setAttribute("sms",sms );
				
			
		
	        return mapping.findForward("forwardManualDev");
		}

	public ActionForward byPassManualDeviation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		HttpSession session=request.getSession(false);
		//UserObject userobj=new UserObject();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String bDate="";
		String companyId="";
		if(userobj!=null)
		{
			userId	=userobj.getUserId();
			bDate=userobj.getBusinessdate();
			companyId=""+userobj.getCompanyId();
		}else{
			logger.info("here in byPassManualDeviation method of DeviationApprovalAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		DynaValidatorForm DeviationDynaValidatorForm= (DynaValidatorForm)form;
		
		String dealId=CommonFunction.checkNull(request.getParameter("dealId"));

		logger.info("In byPassManualDeviation(execute) dealid: " + dealId);
		
		boolean flag=false;
		
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
		
	
		DeviationApprovalVo vo = new DeviationApprovalVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, DeviationDynaValidatorForm);	
		
		DeviationApprovalDAO dao=(DeviationApprovalDAO)DaoImplInstanceFactory.getDaoImplInstance(DeviationApprovalDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass()); 
			
		String checkStageM=CommonFunction.stageMovement(companyId, "DC","F",dealId, "POC", bDate,userId);
		logger.info("checkStageM : "+checkStageM);
		if(CommonFunction.checkNull(checkStageM).equalsIgnoreCase("S"))
		{
			request.setAttribute("sms","F");
		}
		else
		{
			request.setAttribute("sms","E");
			request.setAttribute("status",checkStageM);
		}
		DeviationDynaValidatorForm.reset(mapping, request);
	    logger.info("status : " + checkStageM);
	    
	    return mapping.findForward("forwardManualDev");
	}

}
