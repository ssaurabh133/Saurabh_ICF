package com.cm.actions;

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
import com.cm.dao.ManualnpaMovementDAO;
import com.cm.vo.ManualnpaMovementVO;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class ManualNpaAuthorProcessAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(ManualNpaAuthorProcessAction.class.getName());
	//ManualnpaMovementDAO dao = new ManualnpaMovementDAOImpl();
	ManualnpaMovementDAO dao=(ManualnpaMovementDAO)DaoImplInstanceFactory.getDaoImplInstance(ManualnpaMovementDAO.IDENTITY);
	 
	
	public ActionForward viewJsp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		logger.info("Implementation class: "+dao.getClass());
		HttpSession session =  request.getSession(); 
		String manualNpaId= session.getAttribute("manualNpaId").toString();
		
		String loanId= session.getAttribute("loanId").toString();
		ArrayList<ManualnpaMovementVO> list = dao.selectManualNpa(manualNpaId,loanId);
		request.setAttribute("author", "author");
		request.setAttribute("list", list);
		return mapping.findForward("success");
	}
	public ActionForward authorScreen(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		HttpSession session =  request.getSession(); 
		
		session.getAttribute("manualNpaId");

		return mapping.findForward("authorScreen");
	}
	public ActionForward saveManualNpaAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Inside EarlyClosureDispatchAction...........saveClosureDetails");
		
		HttpSession session =  request.getSession();
		
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String makerId="";		
		String bDate ="";
		int companyId;
		if(userobj!=null){
			companyId=userobj.getCompanyId();
			makerId= userobj.getUserId();
			bDate=userobj.getBusinessdate();
		}else{
			logger.info("here in saveManualNpaAuthor method ----");
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

		DynaValidatorForm ManualNpaMovementAuthorDynaValidatorForm=(DynaValidatorForm)form;
		ManualnpaMovementVO vo = new ManualnpaMovementVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, ManualNpaMovementAuthorDynaValidatorForm);
		
		String manualNpaId= session.getAttribute("manualNpaId").toString();
		String loanId= session.getAttribute("loanId").toString();
		String npaflag= session.getAttribute("npaflag").toString();
		vo.setCompanyId(companyId);
		vo.setAuthorId(makerId);
		vo.setAuthorDate(bDate);
		vo.setManualnpaId(manualNpaId);
		vo.setLbxLoanNo(loanId);
		vo.setCurrNpaStage(npaflag);
		String result= dao.saveManualNpaAuthor(vo);
		String message="";
		logger.info("result---->"+result);
		if(!result.equalsIgnoreCase("S"))
		{
			request.setAttribute("procval", result);
			logger.info("in if");
		}
		else{
			logger.info("Data Saved.");
			message="S";
			request.setAttribute("message",message);
			
		}
		
		return mapping.findForward("authorScreen1");
	}
	

}
