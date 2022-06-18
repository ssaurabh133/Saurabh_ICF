package com.cm.actions;

import java.lang.reflect.InvocationTargetException;
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
import com.cm.dao.InstrumentCapturingDAO;
import com.cm.vo.InstructionCapMakerVO;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;


public class InstrumentCapturingAuthor  extends DispatchAction{
	private static final Logger logger = Logger.getLogger(InstrumentCapturingAuthor.class.getName());

	
	public ActionForward saveAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws IllegalAccessException, InvocationTargetException,Exception {
		
				logger.info("In saveAuthor Method---------");
				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				String userId="";
				String bDate ="";
				if(userobj!=null){
					userId= userobj.getUserId();
					bDate=userobj.getBusinessdate();
				}else{
					logger.info("here in saveAuthor method of action the session is out----------------");
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
		        DynaValidatorForm InstrumentCapturingAuthorValidatorForm = (DynaValidatorForm)form;
				InstructionCapMakerVO instructionCapMakerVO=new InstructionCapMakerVO();
				int loanID=Integer.parseInt(request.getParameter("loanID"));
				logger.info("loanID"+loanID);
				org.apache.commons.beanutils.BeanUtils.copyProperties(instructionCapMakerVO, InstrumentCapturingAuthorValidatorForm);
		
				instructionCapMakerVO.setUserID(userId);
				instructionCapMakerVO.setMakerDate(bDate);
				InstrumentCapturingDAO dao=(InstrumentCapturingDAO)DaoImplInstanceFactory.getDaoImplInstance(InstrumentCapturingDAO.IDENTITY);
				logger.info("Implementation class: "+dao.getClass()); 
			   flag=dao.updateCommentNDecision(instructionCapMakerVO,loanID);
			     
			    if(flag){
			    	 request.setAttribute("savedSuccessfully", "S"); 
			     }else{
			    	 request.setAttribute("savedSuccessfully", "N");
			     }
		
			     request.setAttribute("author", "author");
			     
				return mapping.findForward("success");

	}
	
	
	
}