package com.caps.action;

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
import com.caps.VO.CollUploadDataVO;
import com.caps.dao.CollUoloadDataDAO;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;



public class CollUploadDataAction extends DispatchAction{
private static final Logger logger = Logger.getLogger(CollUploadDataAction.class.getName());
	
	public ActionForward openJsp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		logger.info("in CollUploadDataAction openJsp method:0---");
		//
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here openJsp method of CollUploadDataAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");

		ServletContext context = getServlet().getServletContext();
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
		
		return mapping.findForward("success");
	}
	public ActionForward submitUpload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		logger.info("in CollUploadDataAction submitUpload method:0---");
		//submitUpload
		HttpSession session = request.getSession();
		//boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here submitUpload method of  CollUploadDataAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");

		ServletContext context = getServlet().getServletContext();
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
		CollUploadDataVO collUploadDataVo = new CollUploadDataVO();
		DynaValidatorForm CollUploadDataForm=(DynaValidatorForm)form;	
		org.apache.commons.beanutils.BeanUtils.copyProperties(collUploadDataVo, CollUploadDataForm);
		
		String makerID = userobj.getUserId();
		collUploadDataVo.setMakerId(makerID);
		collUploadDataVo.setMakerDate(userobj.getBusinessdate());
		
		CollUoloadDataDAO collUploadDao=(CollUoloadDataDAO)DaoImplInstanceFactory.getDaoImplInstance(CollUoloadDataDAO.IDENTITY);
		logger.info("Implementation class: "+collUploadDao.getClass());
		
		boolean uploadStatus=false;	
		uploadStatus=collUploadDao.uploadData(request,collUploadDataVo.getUploadFileLoan());			
		collUploadDataVo.setFileName(request.getAttribute("fileName").toString());
		collUploadDataVo.setDocPath(request.getAttribute("filePath").toString());
		String flag=(String)request.getAttribute("message");
			if(uploadStatus)
			{
				int count=collUploadDao.countUploadDataLine(collUploadDataVo.getUploadFileLoan().toString());
				if(count>20000)
				{
					 request.setAttribute("maxCount","");	
					 logger.info("....In CollUploadDataAction submitUpload..Total Line.."+count);
				}
				else
			{
					//for start process file name ..
			//session.setAttribute("Processfile",collUploadDataVo.getUploadFileLoan().toString());
			session.setAttribute("uploadFileLoan",collUploadDataVo.getUploadFileLoan().toString());
			logger.info("....In CollUploadDataAction submitUpload..Total Line.."+count);
			}
			}
			
		 if(flag=="O")
		 {
			    request.setAttribute("sms","");
		 }
			    if(flag=="E")
			    {
					request.setAttribute("smsno","");
			    }
			

					int count=collUploadDao.countUploadDataLine(collUploadDataVo.getUploadFileLoan().toString());
					if(count>20000)
					{
						 request.setAttribute("maxCount","");	
						 logger.info("....In CollUploadDataAction submitUpload..Total Line.."+count);
					}
					if(count==0)
					{
						 request.setAttribute("zeroCount","");	
						 logger.info("....In CollUploadDataAction submitUpload..Total Line.."+count);	
					}
				
				else
				{    boolean status=false;
					 status=collUploadDao.csvRead(request, response,collUploadDataVo,collUploadDataVo.getUploadFileLoan().toString());
							if(status==true){							 
						    request.setAttribute("inserted", "Done");	
							 //ArrayList mainList  = service.getPoolData(poolID);
							 //request.setAttribute("mainList", mainList);
							 //ArrayList<PoolIdMakerVO> poolIdSavedDataList = service.savedPoolIdMakerData(poolIdMakerVO,poolID);			
						     //request.setAttribute("poolIdSavedDataList", poolIdSavedDataList);
										
						     //request.setAttribute("poolIdMaker", "poolIdMaker");		
						     //request.setAttribute("poolNo", poolID);
							
					 }
				else{
					request.setAttribute("noinserted", "Done");
				   
				}
			
				logger.info("status"+status);
					
				}
			
		return mapping.findForward("success");
	}
}
