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
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.vo.DocumentChecklistMasterVo;
public class DocumentChecklistMasterDispatchAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(DocumentChecklistMasterDispatchAction.class.getName());
	public ActionForward savedocChekDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		{ServletContext context = getServlet().getServletContext();
			//HttpSession session=request.getSession(false);
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
			String userId="";
			String bDate="";
			if(userobj!=null)
			{
					userId=userobj.getUserId();
					bDate=userobj.getBusinessdate();
			}
			
			logger.info("Inside Document CheckList Master Action..........");
		DynaValidatorForm DocumentCheckListMasterAddDyanavalidatiorForm= (DynaValidatorForm)form;
		DocumentChecklistMasterVo vo = new DocumentChecklistMasterVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, DocumentCheckListMasterAddDyanavalidatiorForm);
		//logger.info("Inside Document CheckList Master Action.....displaying status....2");
		if((vo.getDocEntity().equalsIgnoreCase("ASSET")) ||(vo.getDocEntity().equalsIgnoreCase("COLLATERAL")) ||(vo.getDocEntity().equalsIgnoreCase("APPL")) )
		{
			vo.setDocConstitution("");
		}
		vo.setMakerId(userId);
		vo.setMakerDate(bDate);
		
		String docId  = request.getParameter("docIdList");
        String[] docIdList= docId.split("/");
        //logger.info(docId);
     
        String docMandatory  = request.getParameter("docMandatoryList"); 			
        String[] docMandatoryList= docMandatory.split("/");      
       // logger.info(docMandatory);
        
        String docOriginal  = request.getParameter("docOriginalList");                    			
        String[] docOriginalList= docOriginal.split("/"); 
       // logger.info(docOriginal);

        
        String docExpFlag  = request.getParameter("docExpiryFlagList");                     			
        String[] docExpiryFlagList= docExpFlag.split("/");
        
        String stats  = request.getParameter("statusList");                     			
        String[] statusList= stats.split("/");
        
        CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
        
		String sms="";
	
		boolean status = cpm.insertDocCheckListMaster(docIdList,docMandatoryList,docOriginalList,docExpiryFlagList,statusList,vo);
		logger.info("Inside Document CheckList Master Action.....displaying status...."+status);
		if(status){
			sms="S";
			request.setAttribute("sms",sms);
		}
		else{
			sms="E";
			request.setAttribute("sms",sms);
		}
		logger.info("status"+status);
		return mapping.findForward("save");
		}
}
	


	public ActionForward searchDocChekList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		DocumentChecklistMasterVo vo = new DocumentChecklistMasterVo();
		DynaValidatorForm DocumentCheckListMasterAddDyanavalidatiorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, DocumentCheckListMasterAddDyanavalidatiorForm);
		ServletContext context = getServlet().getServletContext();
		if((vo.getDocEntity().equalsIgnoreCase("ASSET")) ||(vo.getDocEntity().equalsIgnoreCase("COLLATERAL")) ||(vo.getDocEntity().equalsIgnoreCase("APPL")) )
		{
			vo.setDocConstitution("");
		}
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
						
			ArrayList<DocumentChecklistMasterVo> detailList = cpm.searchDocCheckListData(vo);
			request.setAttribute("list", detailList);
			request.setAttribute("documentChecklistMasterVo", vo);

			String sms="";
			if(detailList.isEmpty()){
				sms="No";
				request.setAttribute("sms",sms);
			}
			
			
			request.setAttribute("editVal", "editVal");
			return mapping.findForward("search");
	}
	
	
	public ActionForward updateDcCheklist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session=request.getSession(false);
		UserObject userobj=new UserObject();
		userobj=(UserObject)session.getAttribute("userobject");
		
		String userId="";
		String bDate="";
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
		}
		
		boolean flag=false;
		ServletContext context = getServlet().getServletContext();
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
		logger.info("In update DocChecklist Action.......");
		DocumentChecklistMasterVo Vo=new DocumentChecklistMasterVo(); 
		DynaValidatorForm DocumentCheckListMasterAddDyanavalidatiorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(Vo, DocumentCheckListMasterAddDyanavalidatiorForm);	
		
		//org.apache.commons.beanutils.BeanUtils.copyProperties(Vo, DocumentCheckListMasterAddDyanavalidatiorForm);
		//logger.info("Inside Document CheckList Master Action.....displaying status....2");
		if((Vo.getDocEntity().equalsIgnoreCase("ASSET")) ||(Vo.getDocEntity().equalsIgnoreCase("COLLATERAL")) ||(Vo.getDocEntity().equalsIgnoreCase("APPL")) )
		{
			Vo.setDocConstitution("");
		}
		Vo.setMakerId(userId);
		Vo.setMakerDate(bDate);
		
		String docCheckAllIdVal  = request.getParameter("docCheckAllId");
        String[] docCheckAllIdFin= docCheckAllIdVal.split("/");
        logger.info("docId..............."+docCheckAllIdFin);
		
		String docId  = request.getParameter("docIdList");
        String[] docIdList= docId.split("/");
        logger.info("docId..............."+docId);
     
        String docCheckId= request.getParameter("docCheckIdList");
        String[] docCheckIdList= docCheckId.split("/"); 
        
        logger.info("docCheckId................"+docCheckId);
        
        String docMandatory  = request.getParameter("docMandatoryList"); 			
        String[] docMandatoryList= docMandatory.split("/");      
        logger.info("docMandatory....................."+docMandatory);
        
        String docOriginal  = request.getParameter("docOriginalList");                    			
        String[] docOriginalList= docOriginal.split("/"); 
        logger.info(docOriginal);

        
        String docExpFlag  = request.getParameter("docExpiryFlagList");                     			
        String[] docExpiryFlagList= docExpFlag.split("/");
        
        String stats  = request.getParameter("statusList");                     			
        String[] statusList= stats.split("/");
	
		logger.info("In update DocCheckList Data-------------------");    


		CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
        boolean status=cpm.updatedocCheckListData(docIdList, docCheckIdList, docMandatoryList, docOriginalList, docExpiryFlagList, statusList, Vo, docCheckAllIdFin);
       
        String sms="";
        if(status){
        	sms="M";
			request.setAttribute("sms",sms);
		}
		else{
			sms="E";
			request.setAttribute("sms",sms);
		}
        
		        
        return mapping.findForward("update");
      
		
	}
	
}


