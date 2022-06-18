package com.masters.actions;

import java.util.ArrayList;
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

import com.business.ejbClient.CreditProcessingMasterBussinessSessionBeanRemote;
import com.connect.CommonFunction;
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.vo.pcdMasterVo;



public class policyChecklistMasterDispatchAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(DocumentChecklistMasterDispatchAction.class.getName());
	
	public ActionForward savePolicyChekDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		{
			ServletContext context = getServlet().getServletContext();
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
			pcdMasterVo vo = new pcdMasterVo();
			String userId="";
			String bDate="";
			if(userobj!=null)
			{
					userId=userobj.getUserId();
					bDate=userobj.getBusinessdate();
			}
			logger.info("Inside Document CheckList Master Action..........");
			
		DynaValidatorForm PolicyChecklistDefDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, PolicyChecklistDefDynaValidatorForm);
		
		CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		
		logger.info("insertPolicyCheckListMaster Action.....displaying status....2");
		
		vo.setMakerId(userId);
		vo.setMakerDate(bDate);
		
        logger.info("*************************************************"+vo.getPsize());
//		String[] docIdList = vo.getLbxProductID();	
		String sms="";
		
		
//		if((vo.getDocEntity().equalsIgnoreCase("ASSET")) ||(vo.getDocEntity().equalsIgnoreCase("COLLATERAL")) ||(vo.getDocEntity().equalsIgnoreCase("APPL")) )
//		{
//			vo.setDocConstitution("");
//		}
		
		vo.setMakerId(userId);
		vo.setMakerDate(bDate);
		
		String ruleCode  = request.getParameter("ruleCodeList");
        String[] ruleCodeList= ruleCode.split("/");
        logger.info("ruleCode "+ruleCode);
     
        String appLevel  = request.getParameter("appLevelList"); 			
        String[] appLevelList= appLevel.split("/");      
        logger.info("appLevel "+appLevel);
        
        String action  = request.getParameter("actionList");                    			
        String[] actionList= action.split("/"); 
        logger.info("action "+action);

//        
        String pcdCheckId  = request.getParameter("pcdCheckIdList");                     			
        String[] pcdCheckIdList= pcdCheckId.split("/");
        logger.info("pcdCheckIdList "+pcdCheckId);
        
        String ruleDesc  = request.getParameter("ruleDescList");                     			
        String[] ruleDescList= ruleDesc.split("/");
        logger.info("ruleDesc "+ruleDesc);
		
        String function = request.getParameter("function");
        if(function.equalsIgnoreCase("Save")){
        	logger.info("--------------inside the insert----------------------- "+ruleDesc);
        	boolean status = cpm.insertPolicyCheckListMaster(ruleCodeList,ruleDescList,actionList,appLevelList,vo);
        	if(status){
    			sms="S";
    			request.setAttribute("sms",sms);
    		}
    		else{
    			sms="E";
    			request.setAttribute("sms",sms);
    		}
    		logger.info("status"+status);
        }else if(function.equalsIgnoreCase("Update")){
        	logger.info("--------------inside the update----------------------- ");
        	boolean status = cpm.updatePolicyCheckListMaster(pcdCheckIdList,ruleCodeList,ruleDescList,actionList,appLevelList,vo);
        	if(status){
    			sms="S";
    			request.setAttribute("sms",sms);
    		}
    		else{
    			sms="E";
    			request.setAttribute("sms",sms);
    		}
    		logger.info("status"+status);
        }
//		logger.info("Inside Document CheckList Master Action.....displaying status...."+status);
		
		return mapping.findForward("save");
		}
}
	


	public ActionForward searchPolicyChekDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ServletContext context = getServlet().getServletContext();
		pcdMasterVo vo = new pcdMasterVo();
		DynaValidatorForm PolicyChecklistDefDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, PolicyChecklistDefDynaValidatorForm);
		
		logger.info("In search docchecklist Action.....");
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
		CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
						
			ArrayList<pcdMasterVo> detailList = cpm.searchPolicyListData(vo);
			request.setAttribute("list", detailList);
//			request.setAttribute("documentChecklistMasterVo", vo);

			String sms="";
			if(detailList.isEmpty()){
				sms="No";
				request.setAttribute("sms",sms);
				
			}
			
			logger.info("Outside the LOOP");
//			request.setAttribute("editVal", "editVal");
			return mapping.findForward("success");
	}
	
	public ActionForward openForEditting(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ServletContext context = getServlet().getServletContext();
		pcdMasterVo vo = new pcdMasterVo();
		DynaValidatorForm PolicyChecklistDefDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, PolicyChecklistDefDynaValidatorForm);
		
		CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		logger.info("In search docchecklist Action.....");
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
		
			String product = request.getParameter("productId");
			String scheme = request.getParameter("schemeId");
			String stageId = request.getParameter("stage");
			ArrayList<pcdMasterVo> detailList = cpm.searchPolicyListDataForEditting(vo,product,scheme,stageId);
			int list1  = cpm.getApprovalfromPmst();
			logger.info("-----------------------------------------1111111111--"+list1);
			if(list1>2 && list1<10){
				request.setAttribute("pmstSize", list1);					
			}else if(list1<3 || list1>9){
				request.setAttribute("pmstSize", 3);
			}else {
				request.setAttribute("pmstSize", 3);
			}
			request.setAttribute("list", detailList);
//			request.setAttribute("documentChecklistMasterVo", vo);

			String sms="";
			if(detailList.isEmpty()){
				sms="No";
				request.setAttribute("sms",sms);
				
			}
			
			logger.info("Outside the LOOP");
			request.setAttribute("editVal", "editVal");
			return mapping.findForward("success");
	}
	
	
//	public ActionForward updateDcCheklist(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response) throws Exception {
//		HttpSession session=request.getSession(false);
//		UserObject userobj=new UserObject();
//		userobj=(UserObject)session.getAttribute("userobject");
//		String userId=userobj.getUserId();
//		
//		boolean flag=false;
//		
//		Object sessionId = session.getAttribute("sessionID");
//		//for check User session start
//		if(sessionId!=null)
//		{
//			flag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString());
//		}
//		
//		if(flag)
//		{
//			logger.info("logout in action");
//			return mapping.findForward("logout");
//		}
//		logger.info("In update DocChecklist Action.......");
//		pcdMasterVo Vo=new pcdMasterVo(); 
//		DynaValidatorForm DocumentCheckListMasterAddDyanavalidatiorForm= (DynaValidatorForm)form;
//		org.apache.commons.beanutils.BeanUtils.copyProperties(Vo, DocumentCheckListMasterAddDyanavalidatiorForm);	
//		
//		org.apache.commons.beanutils.BeanUtils.copyProperties(Vo, DocumentCheckListMasterAddDyanavalidatiorForm);
//		//logger.info("Inside Document CheckList Master Action.....displaying status....2");
//
//		Vo.setMakerId(userId);
//		Vo.setMakerDate(userobj.getBusinessdate());
//		
//		String docId  = request.getParameter("docIdList");
//        String[] docIdList= docId.split("/");
//        logger.info("docId..............."+docId);
//     
//        String docCheckId= request.getParameter("docCheckIdList");
//        String[] docCheckIdList= docCheckId.split("/"); 
//        
//        logger.info("docCheckId................"+docCheckId);
//        
//        String docMandatory  = request.getParameter("docMandatoryList"); 			
//        String[] docMandatoryList= docMandatory.split("/");      
//        logger.info("docMandatory....................."+docMandatory);
//        
//        String docOriginal  = request.getParameter("docOriginalList");                    			
//        String[] docOriginalList= docOriginal.split("/"); 
//        logger.info(docOriginal);
//
//        
//        String docExpFlag  = request.getParameter("docExpiryFlagList");                     			
//        String[] docExpiryFlagList= docExpFlag.split("/");
//        
//        String stats  = request.getParameter("statusList");                     			
//        String[] statusList= stats.split("/");
//	
//		logger.info("In update DocCheckList Data-------------------");    
//        MasterDAO masterDAO = new MasterDAOImpl();
//        boolean status=masterDAO.updatedocCheckListData(docIdList,docCheckIdList,docMandatoryList,docOriginalList,docExpiryFlagList,statusList,Vo);
//       
//        String sms="";
//        if(status){
//        	sms="M";
//			request.setAttribute("sms",sms);
//		}
//		else{
//			sms="E";
//			request.setAttribute("sms",sms);
//		}
//        
//		        
//        return mapping.findForward("update");
//      
//		
//	}
	
}


