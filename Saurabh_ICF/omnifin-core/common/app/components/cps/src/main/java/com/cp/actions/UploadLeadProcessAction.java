package com.cp.actions;
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
import com.cm.vo.uploadVO;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class UploadLeadProcessAction extends DispatchAction
{
	private static final Logger logger = Logger.getLogger(UploadLeadProcessAction.class.getName());
	
	public ActionForward uploadData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		boolean flag1=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("in  uploadData method of  UploadLeadProcessAction action the session is out----------------");
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
		
		session.removeAttribute("Processfile");
		logger.info("Inside Processing Action of Doc Upload");
		uploadVO uwDocVo = new uploadVO();
	    DynaValidatorForm UploadDynaValidatorForm=(DynaValidatorForm)form;	    
		logger.info("Copying form data to Vo");
	    org.apache.commons.beanutils.BeanUtils.copyProperties(uwDocVo, UploadDynaValidatorForm);
//	    CreditManagementDAO service = new CreditManagementDAOImpl();
		String sms="";
		boolean uploadStatus=false;	
			uploadStatus=UploadLeadDocuments.docUpload(request,uwDocVo.getDocFile());			
			uwDocVo.setFileName(request.getAttribute("fileName").toString());
			uwDocVo.setDocPath(request.getAttribute("filePath").toString());
			String flag=(String)request.getAttribute("message");
			if(uploadStatus)
			{
				int count=UploadLeadDocuments.countLine(uwDocVo.getDocFile().toString());
				if(count>1000)
				{
					 request.setAttribute("maxCount","");	
					 logger.info("....In UploadLeadProcessAction..Total Line.."+count);
				}
				else
				{
					//for start process file name ..
				session.setAttribute("Processfile",uwDocVo.getDocFile().toString());
				logger.info("....In UploadLeadProcessAction..Total Line.."+count);
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
			    
		   
		return mapping.findForward("success");
	}
	
	public ActionForward startProcessLead(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) throws Exception 
{		
		logger.info("In startProcessLead():");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null)
		{
			logger.info("in  startProcessLead method of  UploadLeadProcessAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		if(sessionId!=null && userobj != null)
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		
		logger.info("In startProcessLead(): strFlag ==>> "+strFlag);
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
		
		String file=(String)session.getAttribute("Processfile");
		logger.info("In startProcessLead(): file ==>> "+file);

		int count=UploadLeadDocuments.countLine(file);
		logger.info("In startProcessLead(): count:==>>"+count);
		if(count>1000)
		{
			 request.setAttribute("maxCount","");				 
		}		
		if(count==0)
		{
			 request.setAttribute("zeroCount","");				
		}
		else
		{
			boolean status=UploadLeadDocuments.readExcelforLeadUpload(request, response,file);
			logger.info("In startProcessLead(): status :==>> "+status);
			if(status)
			{		
				logger.info("In startProcessLead():if condition");
				request.setAttribute("inserted", "Done");				
			}
			else
			{
				logger.info("In startProcessLead():else condition");
				request.setAttribute("noinserted", "Done");				
			}
		}
		session.removeAttribute("Processfile");
		return mapping.findForward("success");
} 	
}