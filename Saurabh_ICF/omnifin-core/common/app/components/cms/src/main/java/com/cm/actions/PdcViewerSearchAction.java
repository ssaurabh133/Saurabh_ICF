
package com.cm.actions;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.*;
import org.apache.struts.validator.DynaValidatorForm;
import com.cm.dao.PdcViewerDao;
import com.cm.vo.PdcViewerVo;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class PdcViewerSearchAction extends Action
{
	

	private static final Logger logger = Logger.getLogger(PdcViewerSearchAction.class.getName());
	public ActionForward execute(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws Exception {
		logger.info("In  PdcViewerSearchAction-------------------->"); 
		HttpSession session = request.getSession();
		
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
	    String userId="";
	    if(userobj!=null){
	    	userId= userobj.getUserId();
	    }else{
			logger.info(" in execute method of PdcViewerSearchAction action the session is out----------------");
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
		//CODE ADD BY NEERAJ TRIPATHI START
		logger.info("current page link .......... "+request.getParameter("d-1337771-p"));				
		int currentPageLink = 0;
		if(request.getParameter("d-1337771-p")==null || request.getParameter("d-1337771-p").equalsIgnoreCase("0"))
			currentPageLink=1;
		else
			currentPageLink =Integer.parseInt(request.getParameter("d-1337771-p"));
		logger.info("current page link ................ "+request.getParameter("d-1337771-p"));
		//CODE ADD BY NEERAJ TRIPATHI END
		
		DynaValidatorForm PdcViewerDynaValidatorForm = (DynaValidatorForm) form;
		PdcViewerVo vo = new PdcViewerVo();
		vo.setCurrentPageLink(currentPageLink);
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,PdcViewerDynaValidatorForm);
	
		PdcViewerDao service=(PdcViewerDao)DaoImplInstanceFactory.getDaoImplInstance(PdcViewerDao.IDENTITY);
		logger.info("Implementation class: "+service.getClass());
	
	
		ArrayList<PdcViewerVo> pdcList=service.searchPDCViewerData(vo);
		request.setAttribute("pdcList",pdcList);
		String loanId=vo.getLbxLoanNo();
		ArrayList list = service.calculatePDC(loanId);		
		String presented=(String)list.get(0);
		String toBePresented=(String)list.get(1);	
		logger.info("In execute()  presented      :  "+ presented);
		logger.info("In execute()  toBePresented  :  "+ toBePresented);
		request.setAttribute("calculatePDCList","calculatePDCList");
		request.setAttribute("presented",presented);
		request.setAttribute("toBePresented",toBePresented);
		return mapping.findForward("success");	
	
	}
	
	
	
}